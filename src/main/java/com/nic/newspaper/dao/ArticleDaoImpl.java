package com.nic.newspaper.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.entity.Article;

import jakarta.persistence.EntityManager;

@Repository
public class ArticleDaoImpl implements ArticleDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Article> findAll() {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Article> theQuery = currentSession.createQuery("from Article", Article.class);

		List<Article> articles = theQuery.getResultList();

		return articles;
	}

	@Override
	public Article save(Article theArticle) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.persist(theArticle);

		return currentSession.get(Article.class, theArticle.getId());

	}

	@Override
	public Article update(Article theArticle) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theArticle);

		return currentSession.get(Article.class, theArticle.getId());

	}

	@Override
	public Article findArticleById(long articleId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Article theArticle = currentSession.get(Article.class, articleId);

		return theArticle;
	}

	@Override
	public void deleteArticleById(int articleId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = currentSession.createQuery("delete from Article where id=:ArticleId");
		theQuery.setParameter("ArticleId", articleId);

		theQuery.executeUpdate();

	}

}
