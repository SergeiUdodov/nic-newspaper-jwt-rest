package com.nic.newspaper.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
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

	@Override
	public Comment findCommentById(long commentId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Comment theComment = currentSession.get(Comment.class, commentId);

		return theComment;
	}

	@Override
	public void deleteCommentById(long commentId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = currentSession.createQuery("delete from Comment where id=:CommentId");
		theQuery.setParameter("CommentId", commentId);

		theQuery.executeUpdate();

	}

}
