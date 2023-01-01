package com.nic.newspaper.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.entity.User;

import jakarta.persistence.EntityManager;

@Repository
public class ArticleDaoImpl implements ArticleDao {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Article> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
				
		Query<Article> theQuery =
						currentSession.createQuery("from Article", Article.class);

		List<Article> articles = theQuery.getResultList();
	
		return articles;
	}

}
