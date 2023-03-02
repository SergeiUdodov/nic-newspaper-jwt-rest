package com.nic.newspaper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.dao.ThemeDao;
import com.nic.newspaper.entity.Theme;

import jakarta.transaction.Transactional;

@Service
public class ThemeServiceImpl implements ThemeService {

	@Autowired
	public ThemeDao themeDao;

	@Autowired
	public ArticleDao articleDao;

	@Override
	@Transactional
	public List<Theme> findAllThemes() {

		return themeDao.findAllThemes();
	}

	@Override
	@Transactional
	public Theme save(Theme theTheme) {
		return themeDao.save(theTheme);
	}

//	@Override
//	@Transactional
//	public List<Theme> findThemesByNames(String[] names) {
//		return themeDao.findThemesByNames(names);
//	}

	@Override
	@Transactional
	public Theme findThemeByName(String name) {
		return themeDao.findThemeByName(name);
	}

	@Override
	@Transactional
	public void deleteThemeById(long themeId) {
		themeDao.deleteThemeById(themeId);

	}

//	@Override
//	@Transactional
//	public Theme findThemeById(long themeId) {
//
//		return themeDao.findThemeById(themeId);
//	}

//	@Override
//	public Article addThemeForArticle(long articleId, CrmTheme theTheme) {
//		
//		Article theArticle = articleDao.findArticleById(articleId);
//
//		if (theArticle == null) {
//			throw new RuntimeException("Article id not found - " + articleId);
//		}
//
//		com.nic.newspaper.entity.User theUser = userDao.findByUserEmail(userEmail);
//
//		if (theUser == null) {
//			throw new RuntimeException("User email not found - " + userEmail);
//		}
//
//		Comment newComment = new Comment();
//
//		newComment.setText(theComment.getText());
//
//		Date current = new Date();
//		String currentDate = formatter.format(current);
//		newComment.setDate(currentDate);
//
//		newComment.setUser(theUser);
//		newComment = commentDao.save(newComment);
//
//		List<Comment> comments = theArticle.getComments();
//		comments.add(newComment);
//		theArticle.setComments(comments);
//
//		return articleDao.update(theArticle);	
//	}

}
