package com.nic.newspaper.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	@Override
	@Transactional
	public Article save(CrmComment theComment, String userEmail, long articleId) {

		Article theArticle = articleDao.findArticleById(articleId);

		if (theArticle == null) {
			throw new RuntimeException("Article id not found - " + articleId);
		}

		com.nic.newspaper.entity.User theUser = userDao.findByUserEmail(userEmail);

		if (theUser == null) {
			throw new RuntimeException("User email not found - " + userEmail);
		}

		Comment newComment = new Comment();

		newComment.setText(theComment.getText());

		Date current = new Date();
		String currentDate = formatter.format(current);
		newComment.setDate(currentDate);

		newComment.setUser(theUser);
		newComment = commentDao.save(newComment);

		List<Comment> comments = theArticle.getComments();
		comments.add(newComment);
		theArticle.setComments(comments);

		return articleDao.update(theArticle);
	}

	@Override
	@Transactional
	public Comment findCommentById(long commentId) {

		return commentDao.findCommentById(commentId);
	}

	@Override
	@Transactional
	public void deleteCommentById(long commentId) {

		commentDao.deleteCommentById(commentId);

	}

}
