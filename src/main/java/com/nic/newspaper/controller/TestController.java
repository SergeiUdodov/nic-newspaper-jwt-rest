package com.nic.newspaper.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.service.UserService;

@RestController
@RequestMapping("/test")
public class TestController {

private UserService userService;
	
	@Autowired
	public TestController(UserService theUserService) {
		userService = theUserService;
	}
	
	@GetMapping("/articles")
	public Map<Article, List<Comment>> findArticles() {
		
		Map<Article, List<Comment>> map = new HashMap<>();
		
		Article article1 = new Article("Header article1", "Content article1", "about now");
		Article article2 = new Article("Header article2", "Content article2", "about now");
		
		List<Comment> comments1 = new ArrayList<>();
		List<Comment> comments2 = new ArrayList<>();
		
		comments1.add(new Comment("First comment for article1", "about now"));
		comments1.add(new Comment("Second comment for article1", "about now"));
		comments1.add(new Comment("Third comment for article1", "about now"));
		
		comments2.add(new Comment("First comment for article2", "about now"));
		comments2.add(new Comment("Second comment for article2", "about now"));
		
		map.put(article1, comments1);
		map.put(article2, comments2);
		
		return map;
	}
}
