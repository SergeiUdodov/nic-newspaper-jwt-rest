package com.nic.newspaper.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.entity.Article;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class ArticleDaoImpl implements ArticleDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Article> findAll() {
		
		TypedQuery<Article> query = entityManager.createQuery("from Article", Article.class);
        List<Article> articles = query.getResultList();
        return articles;
	}

	@Override
	public void save(Article theArticle) {

		entityManager.persist(theArticle);

	}

	@Override
	public void update(Article theArticle) {
		
		entityManager.merge(theArticle);

	}

	@Override
	public Article findArticleById(long articleId) {
		
		return entityManager.find(Article.class, articleId);
	}

	@Override
	public void deleteArticleById(long articleId) {
		
        jakarta.persistence.Query theQuery = entityManager.createQuery("delete from Article where id=:ArticleId");
        theQuery.setParameter("ArticleId", articleId);
        theQuery.executeUpdate();

	}

}
