package com.nic.newspaper.service;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.model.CrmComment;

public interface CommentService {

	public Article save(CrmComment theComment, String username, long articleId);

	Comment findCommentById(long commentId);

	public void deleteCommentById(long commentId);

}
