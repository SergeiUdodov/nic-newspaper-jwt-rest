package com.nic.newspaper.service;

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

	@Override
	public List<Article> findAll() {

		return articleDao.findAll();
	}

	@Override
	@Transactional
	public Article save(CrmArticle theArticle) {

		Article newArticle = new Article();
		newArticle.setHeader(theArticle.getHeader());
		newArticle.setContent(theArticle.getContent());
		newArticle.setDate(theArticle.getDate());

		return articleDao.save(newArticle);
	}

	@Override
	@Transactional
	public Article findArticleById(int articleId) {

		return articleDao.findArticleById(articleId);
	}

	@Override
	@Transactional
	public void deleteArticleById(int articleId) {

		articleDao.deleteArticleById(articleId);

	}

}
