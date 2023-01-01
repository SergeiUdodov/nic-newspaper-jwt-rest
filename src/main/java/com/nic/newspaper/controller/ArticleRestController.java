package com.nic.newspaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.service.ArticleService;

@RestController
@RequestMapping("/api")
public class ArticleRestController {
	
	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/articles")
	public List<Article> findAll() {
		
		return articleService.findAll();
	}
	
}
