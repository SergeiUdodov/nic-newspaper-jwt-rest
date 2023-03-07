package com.nic.newspaper.controller;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.config.JwtTokenUtil;
import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.model.CrmComment;
import com.nic.newspaper.service.ArticleService;
import com.nic.newspaper.service.CommentService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:8081/")
public class CommentRestController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	ArticleService articleService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	protected final Log logger = LogFactory.getLog(getClass());

	@PostMapping("/addComment/{articleId}")
	public Article addComment(@PathVariable long articleId, @RequestBody CrmComment theComment,
			HttpServletRequest request) {

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

		return commentService.save(theComment, userEmail, articleId);

	}

	@DeleteMapping("/deleteComment/{commentId}")
	public String deleteComment(@PathVariable long commentId) {

		Comment tempComment = commentService.findCommentById(commentId);

		// throw exception if null

		if (tempComment == null) {
			throw new RuntimeException("Comment id not found - " + commentId);
		}

		commentService.deleteCommentById(commentId);

		return "Deleted Comment id - " + commentId;

	}
	
	@GetMapping("/comments/{aritcleId}")
	public List<Comment> comments(@PathVariable long aritcleId) {
		
		List<Comment> comments = articleService.findArticleById(aritcleId).getComments();
		
		Comparator<Comment> byDate = (first, second) -> second.getDate().compareToIgnoreCase(first.getDate());
		
		comments.sort(byDate);
		
		return comments;
	}

}
