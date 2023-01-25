package com.nic.newspaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.model.CrmComment;
import com.nic.newspaper.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentRestController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/addComment")
	public Article addComment(@RequestBody CrmComment theComment) {

		return commentService.save(theComment);

	}

	@PostMapping("/addCommentOnly")
	public Comment addCommentOnly(@RequestBody CrmComment theComment) {

		return commentService.saveCommentOnly(theComment);

	}
}
