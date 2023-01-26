package com.nic.newspaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.entity.Article;
import com.nic.newspaper.model.CrmArticle;
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

	@GetMapping("/articles/{articleId}")
	public Article findArticleById(@PathVariable int articleId) {

		return articleService.findArticleById(articleId);
	}
	
	@PostMapping("/addArticle")
	public Article addArticle(@RequestBody CrmArticle theArticle) {

		return articleService.save(theArticle);

	}

	@DeleteMapping("/deleteArticle/{articleId}")
	public String deleteArticle(@PathVariable int articleId) {

		Article tempArticle = articleService.findArticleById(articleId);

		// throw exception if null

		if (tempArticle == null) {
			throw new RuntimeException("Article id not found - " + articleId);
		}

		articleService.deleteArticleById(articleId);

		return "Deleted Article id - " + articleId;
	}

	@PutMapping("/updateArticle/{articleId}")
	public Article updateArticle(@PathVariable int articleId, @RequestBody CrmArticle theArticle) {

		return articleService.update(articleId, theArticle);
	}

}
