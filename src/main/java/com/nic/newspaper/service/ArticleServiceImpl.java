package com.nic.newspaper.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Theme;
import com.nic.newspaper.entity.User;
import com.nic.newspaper.model.CrmArticle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	public ArticleDao articleDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ThemeService themeService;

	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	@Override
	@Transactional
	public List<Article> findAll(HttpServletRequest request) {

		List<Article> articles = articleDao.findAll();

//		Comparator<Article> byDate = (first, second) -> second.getDate().compareToIgnoreCase(first.getDate());
		Comparator<Article> byDate = (first, second) -> {
			try {
				return formatter.parse(second.getDate()).compareTo(formatter.parse(first.getDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		};

		articles.sort(byDate);

		String requestTokenHeader = request.getHeader("Authorization");

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			User currentUser = userService.getUserByToken(request);

			List<Article> articlesForRemove = new ArrayList<>();

			for (Article article : articles) {
				if (!Collections.disjoint(article.getThemes(), currentUser.getForbid())) {
					articlesForRemove.add(article);
				}
			}

			articles.removeAll(articlesForRemove);

			List<Theme> preferedArticles = currentUser.getPrefer();

			class Intersection {
				private static int compute(Article article, List<Theme> list) {
					int result = 0;
					for (Theme theme : list) {
						if (article.getThemes().contains(theme)) {
							result++;
						}
					}
					return result;
				}
			}
			Comparator<Article> byPreferThemes = (first, second) -> Intersection.compute(second, preferedArticles)
					- Intersection.compute(first, preferedArticles);
			articles.sort(byPreferThemes);
		}

		return articles;

	}

	@Override
	@Transactional
	public Article save(CrmArticle theArticle) {

		Article newArticle = new Article();

		newArticle.setHeader(theArticle.getHeader());
		newArticle.setContent(theArticle.getContent());

		Date current = new Date();
		String currentDate = formatter.format(current);
		newArticle.setDate(currentDate);

		newArticle.setImageURL(theArticle.getImageURL());

		String[] themesNames = theArticle.getThemes().toLowerCase().split("[^a-zа-я0-9]+");
		String[] uniqueNames = Arrays.stream(themesNames).distinct().toArray(String[]::new);

		List<Theme> themes = new ArrayList<>();

		Theme temp = null;
		for (String themeName : uniqueNames) {
			if (!"".equals(themeName)) {
				temp = themeService.findThemeByName(themeName);
				if (temp != null) {
					themes.add(temp);
				} else {
					temp = themeService.save(new Theme(themeName));
					themes.add(temp);
				}
			}
		}
		temp = null;
		newArticle.setThemes(themes);

		return articleDao.save(newArticle);
	}

	@Override
	@Transactional
	public Article findArticleById(long articleId) {

		return articleDao.findArticleById(articleId);
	}

	@Override
	@Transactional
	public void deleteArticleById(long articleId) {

		articleDao.deleteArticleById(articleId);

	}

	@Override
	@Transactional
	public Article update(long articleId, CrmArticle theArticle) {

		Article newArticle = articleDao.findArticleById(articleId);

		if (newArticle == null) {
			throw new RuntimeException("Article id not found - " + articleId);
		}

		newArticle.setHeader(theArticle.getHeader());
		newArticle.setContent(theArticle.getContent());

		Date current = new Date();
		String currentDate = formatter.format(current);
		newArticle.setDate(currentDate);

		newArticle.setImageURL(theArticle.getImageURL());

		String[] themesNames = theArticle.getThemes().toLowerCase().split("[^a-zа-я0-9]+");
		String[] uniqueNames = Arrays.stream(themesNames).distinct().toArray(String[]::new);

		List<Theme> themes = new ArrayList<>();

		Theme temp = null;
		for (String themeName : uniqueNames) {
			if (!"".equals(themeName)) {
				temp = themeService.findThemeByName(themeName);
				if (temp != null) {
					themes.add(temp);
				} else {
					temp = themeService.save(new Theme(themeName));
					themes.add(temp);
				}
			}
		}
		temp = null;
		newArticle.setThemes(themes);

		return articleDao.update(newArticle);
	}

	@Override
	@Transactional
	public Article likeArticle(long articleId, HttpServletRequest request) {

		User currentUser = userService.getUserByToken(request);

		Article theArticle = articleDao.findArticleById(articleId);

		if (theArticle == null) {
			throw new RuntimeException("Article id not found - " + articleId);
		}

		List<User> likes = theArticle.getLikes();

		if (likes.contains(currentUser)) {
			likes.remove(currentUser);
		} else {
			likes.add(currentUser);
		}

		theArticle.setLikes(likes);

		return articleDao.update(theArticle);
	}

}
