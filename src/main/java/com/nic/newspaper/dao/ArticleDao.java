package com.nic.newspaper.dao;

import java.util.List;

import com.nic.newspaper.entity.Article;

public interface ArticleDao {

	public List<Article> findAll();

	public Article save(Article theArticle);

	public Article findArticleById(long articleId);

	public void deleteArticleById(int articleId);

	Article update(Article theArticle);

}
