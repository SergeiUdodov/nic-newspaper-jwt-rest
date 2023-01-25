package com.nic.newspaper.service;

import java.util.List;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.model.CrmArticle;

public interface ArticleService {

	public List<Article> findAll();

	public Article save(CrmArticle theArticle);

	public Article findArticleById(long articleId);

	public void deleteArticleById(long articleId);

	public Article update(long articleId, CrmArticle theArticle);

}
