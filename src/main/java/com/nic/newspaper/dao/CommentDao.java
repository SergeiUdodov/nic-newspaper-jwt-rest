package com.nic.newspaper.dao;

import com.nic.newspaper.entity.Comment;

public interface CommentDao {

	public Comment save(Comment newComment);

	public Comment findCommentById(long commentId);

	public void deleteCommentById(long commentId);

}
