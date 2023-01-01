package com.nic.newspaper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	public ArticleDao articleDao;
	
	@Override
	public List<Article> findAll() {
		
		return articleDao.findAll();
	}

}
