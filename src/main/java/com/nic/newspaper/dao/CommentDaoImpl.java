package com.nic.newspaper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.entity.Comment;

import jakarta.persistence.EntityManager;

@Repository
public class CommentDaoImpl implements CommentDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Comment findCommentById(long commentId) {
		
		return entityManager.find(Comment.class, commentId);
	}

	@Override
	public void deleteCommentById(long commentId) {
		
		jakarta.persistence.Query theQuery = entityManager.createQuery("delete from Comment where id=:CommentId");
        theQuery.setParameter("CommentId", commentId);
        theQuery.executeUpdate();

	}

}
