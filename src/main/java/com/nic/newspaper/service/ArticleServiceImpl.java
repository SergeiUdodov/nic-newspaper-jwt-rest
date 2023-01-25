package com.nic.newspaper.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.entity.Article;
import com.nic.newspaper.model.CrmArticle;

import jakarta.transaction.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	public ArticleDao articleDao;

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	@Transactional
	public List<Article> findAll() {

		return articleDao.findAll();
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

		return articleDao.update(newArticle);
	}

}
