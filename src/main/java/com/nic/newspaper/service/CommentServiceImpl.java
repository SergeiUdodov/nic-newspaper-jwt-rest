package com.nic.newspaper.service;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.dao.CommentDao;
import com.nic.newspaper.dao.UserDAO;
import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.model.CrmComment;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	public ArticleDao articleDao;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private CommentDao commentDao;

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	@Transactional
	public Article save(CrmComment theComment) {

		// Article theArticle =
		// articleDao.findArticleById(Long.parseLong(theComment.getArticleId()));
		Article theArticle = articleDao.findArticleById(5);
		// com.nic.newspaper.entity.User theUser =
		// userDao.findById(Long.parseLong(theComment.getUserId()));
		com.nic.newspaper.entity.User theUser = userDao.findById(1);

		Comment newComment = new Comment();

		newComment.setText(theComment.getText());
		newComment.setDate(theComment.getDate());
		newComment = commentDao.save(newComment);

		newComment.setUser(theUser);

		logger.warn(theUser);
		logger.warn(newComment);
		logger.warn(theArticle);

		theArticle.setComments(Arrays.asList(newComment));

		return articleDao.save(theArticle);
	}

	@Override
	public Comment saveCommentOnly(CrmComment theComment) {

		Comment newComment = new Comment();

		newComment.setText(theComment.getText());
		newComment.setDate(theComment.getDate());

		com.nic.newspaper.entity.User theUser = userDao.findById(2);
		logger.warn(theUser);

		newComment.setUser(theUser);

		return commentDao.save(newComment);
	}

}
