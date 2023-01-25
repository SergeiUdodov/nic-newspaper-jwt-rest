package com.nic.newspaper.dao;

import com.nic.newspaper.entity.Comment;

public interface CommentDao {

	Comment save(Comment newComment);

	Comment findCommentById(long commentId);

	void deleteCommentById(long commentId);

}
