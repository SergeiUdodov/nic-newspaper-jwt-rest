package com.nic.newspaper.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.entity.Comment;

import jakarta.persistence.EntityManager;

@Repository
public class CommentDaoImpl implements CommentDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Comment save(Comment newComment) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(newComment);

		return currentSession.get(Comment.class, newComment.getId());
	}

}
