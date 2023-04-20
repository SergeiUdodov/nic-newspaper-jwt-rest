package com.nic.newspaper.dao;

import java.util.List;

import com.nic.newspaper.entity.Article;

public interface ArticleDao {

	public List<Article> findAll();

	public void save(Article theArticle);
	
	public void update(Article theArticle);

	public Article findArticleById(long articleId);

	public void deleteArticleById(long articleId);

}
