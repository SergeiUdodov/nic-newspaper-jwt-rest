package com.nic.newspaper.service;

import java.util.List;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.model.CrmComment;

import jakarta.servlet.http.HttpServletRequest;

public interface CommentService {

	public Article save(long articleId, CrmComment theComment, HttpServletRequest request);

	public Comment findCommentById(long commentId);

	public void deleteCommentById(long commentId);
	
	public List<Comment> articleComments(long aritcleId);

}
