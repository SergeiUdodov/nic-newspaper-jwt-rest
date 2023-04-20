package com.nic.newspaper.service;

import static org.mockito.ArgumentMatchers.any;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.entity.Article;
import com.nic.newspaper.entity.Comment;
import com.nic.newspaper.entity.User;
import com.nic.newspaper.model.CrmComment;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
	
	@Mock
	private ArticleService articleService;
	
	@Mock
	private ArticleDao articleDao;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private CommentServiceImpl commentServiceImpl;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	@Test
	void testAritcleComments() {
		
		Article articleWithoutComments = new Article();
		Article article = getArticle();
		List<Comment> comments = article.getComments(); 
		
		Mockito.when(articleService.findArticleById(0)).thenReturn(null);
		Mockito.when(articleService.findArticleById(1)).thenReturn(articleWithoutComments);
		Mockito.when(articleService.findArticleById(2)).thenReturn(getArticle());
		
		List<Comment> commentsFromNullArticle = commentServiceImpl.articleComments(0);
		List<Comment> commentsFromArticleWithoutComments = commentServiceImpl.articleComments(1);
		List<Comment> result = commentServiceImpl.articleComments(2);
		
		Assertions.assertNull(commentsFromNullArticle);
		Assertions.assertNull(commentsFromArticleWithoutComments);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(comments.get(0), result.get(1));		
		
	}
	
	@Test
	void testSave() {
		
		Article articleWithoutComments = new Article();
		List<Comment> emptyCommentsList = new ArrayList<>();
		articleWithoutComments.setComments(emptyCommentsList);
		
		Article articleWithTwoComments = getArticle();
		
		User testUser = getUser();
		CrmComment commentForSave = getCrmComment();
		
		Mockito.when(articleService.findArticleById(0)).thenReturn(articleWithoutComments);
		Mockito.when(articleService.findArticleById(1)).thenReturn(articleWithTwoComments);
		Mockito.when(userService.getUserByToken(any())).thenReturn(testUser);
		
		Article result1 = commentServiceImpl.save(0, commentForSave, null);
		Article result2 = commentServiceImpl.save(1, commentForSave, null);
		
		Assertions.assertEquals(commentForSave.getText(), result1.getComments().get(0).getText());
		Assertions.assertEquals(commentForSave.getText(), result2.getComments().get(2).getText());
		
	}
	
	private Article getArticle() {
		
		Article article = new Article();
		Comment first = new Comment("firstComment", "20.04.2023 11:23:31");
		Comment second = new Comment("secondComment", "20.04.2023 11:24:31");
		List<Comment> comments = new ArrayList<>();
		comments.add(first);
		comments.add(second);
		article.setComments(comments);
		return article;
	}
	
	private User getUser() {
		User testUser = new User("testMail", "testFirstName", "testLastName", "testPassword");
		return testUser;
	}
	
	private CrmComment getCrmComment() {
		CrmComment commentForSave = new CrmComment();
		commentForSave.setText("save this comment");
		return commentForSave;
	}

}
