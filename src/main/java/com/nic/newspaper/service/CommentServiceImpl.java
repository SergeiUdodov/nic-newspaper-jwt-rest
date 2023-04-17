package com.nic.newspaper.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.newspaper.config.JwtTokenUtil;
import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.dao.CommentDao;
import com.nic.newspaper.dao.UserDAO;
import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.model.CrmComment;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	ArticleService articleService;
	
	@Autowired
	public ArticleDao articleDao;

	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	protected final Log logger = LogFactory.getLog(getClass());

	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	@Override
	@Transactional
	public Article save(long articleId, CrmComment theComment, HttpServletRequest request) {
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String userEmail = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				userEmail = jwtTokenUtil.getUserEmailFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token is null or does not begin with Bearer String");
		}

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
	public void deleteCommentById(long commentId) {
		
		Comment tempComment = commentService.findCommentById(commentId);

		if (tempComment == null) {
			throw new RuntimeException("Comment id not found - " + commentId);
		}

		commentDao.deleteCommentById(commentId);

	}

	@Override
	@Transactional
	public Comment findCommentById(long commentId) {

		return commentDao.findCommentById(commentId);
	}

	@Override
	public List<Comment> aritcleComments(long aritcleId) {
		
		List<Comment> comments = articleService.findArticleById(aritcleId).getComments();

		Comparator<Comment> byDate = (first, second) -> {
			try {
				return formatter.parse(second.getDate()).compareTo(formatter.parse(first.getDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0;
		};

		comments.sort(byDate);

		return comments;
	}
}
