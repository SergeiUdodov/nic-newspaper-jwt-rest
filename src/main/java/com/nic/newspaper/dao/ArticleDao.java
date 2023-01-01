package com.nic.newspaper.dao;

import java.util.List;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;

public interface ArticleDao {

	public List<Article> findAll();

}
