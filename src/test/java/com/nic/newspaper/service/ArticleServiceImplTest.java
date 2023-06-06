package com.nic.newspaper.service;

import static org.mockito.ArgumentMatchers.any;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.nic.newspaper.entity.Theme;
import com.nic.newspaper.entity.User;
import com.nic.newspaper.model.CrmArticle;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
	
	@Mock
	public ArticleDao articleDao;

	@Mock
	private UserService userService;
	
	@Mock
	private HttpServletRequest request1;
	
	@Mock
	private HttpServletRequest request2;
	
	@Mock
	private HttpServletRequest request3;
	
	@Mock
	private ThemeService themeService;
	
	@InjectMocks
	private ArticleServiceImpl articleServiceImpl;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	@Test
	void testFindAll() {
		
		List <Article> articlesWithThemes = getArticlesWithThemes();
		User userWithoutThemes = getUserWithoutThemes();
		User userWithThemes = getUserWithThemes();
		
		
		//return articles sorted by date(token is null)
		Mockito.when(articleDao.findAll()).thenReturn(getArticlesWithThemes());
		Mockito.when(request1.getHeader(any())).thenReturn(null);
		
		List<Article> result1 = articleServiceImpl.findAll(request1);
		
		Assertions.assertNotNull(result1);
		Assertions.assertEquals(articlesWithThemes.get(0), result1.get(3));
		Assertions.assertEquals(articlesWithThemes.get(1), result1.get(4));
		Assertions.assertEquals(articlesWithThemes.get(2), result1.get(2));	
		Assertions.assertEquals(articlesWithThemes.get(3), result1.get(1));	
		Assertions.assertEquals(articlesWithThemes.get(4), result1.get(0));	
		
		
		//return articles sorted by date(user doesn't have any themes)
		Mockito.when(request2.getHeader(any())).thenReturn("Bearer ");
		Mockito.when(userService.getUserByToken(request2)).thenReturn(userWithoutThemes);
		
		List<Article> result2 = articleServiceImpl.findAll(request2);
		
		Assertions.assertNotNull(result2);
		Assertions.assertEquals(articlesWithThemes.get(0), result2.get(3));
		Assertions.assertEquals(articlesWithThemes.get(1), result2.get(4));
		Assertions.assertEquals(articlesWithThemes.get(2), result2.get(2));	
		Assertions.assertEquals(articlesWithThemes.get(3), result2.get(1));	
		Assertions.assertEquals(articlesWithThemes.get(4), result2.get(0));
		
		
		//return articles sorted by date and user preferences(user have themes)
		Mockito.when(request3.getHeader(any())).thenReturn("Bearer ");
		Mockito.when(userService.getUserByToken(request3)).thenReturn(userWithThemes);
		
		List<Article> result3 = articleServiceImpl.findAll(request3);
		
		Assertions.assertNotNull(result3);
		Assertions.assertEquals(articlesWithThemes.get(0), result3.get(1));
		Assertions.assertEquals(articlesWithThemes.get(1), result3.get(0));
		Assertions.assertEquals(articlesWithThemes.get(2), result3.get(3));	
		Assertions.assertEquals(articlesWithThemes.get(3), result3.get(2));	
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {result3.get(4);});
		
	}

	@Test
	void testSave() {
		
		Mockito.when(themeService.findThemeByName("theme1")).thenReturn(new Theme("theme1"));
		Mockito.when(themeService.findThemeByName("theme2")).thenReturn(new Theme("theme2"));
		Mockito.when(themeService.findThemeByName("theme3")).thenReturn(new Theme("theme3"));
		
		CrmArticle crmArticle = getCrmArticle();
		Article result = articleServiceImpl.save(crmArticle);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals("theme1", result.getThemes().get(0).getName());
		Assertions.assertEquals("theme2", result.getThemes().get(1).getName());
		Assertions.assertEquals("theme3", result.getThemes().get(2).getName());
		Assertions.assertEquals(3, result.getThemes().size());
		
	}

	@Test
	void testDeleteArticleById() {
		
		Mockito.when(articleServiceImpl.findArticleById(0)).thenReturn(null);
		
		Assertions.assertThrows(RuntimeException.class, () -> {articleServiceImpl.deleteArticleById(0);});
	}

	@Test
	void testUpdate() {	
		
		Mockito.when(articleServiceImpl.findArticleById(99)).thenReturn(null);
		Mockito.when(articleServiceImpl.findArticleById(0)).thenReturn(getArticlesWithThemes().get(0));
		Mockito.when(themeService.findThemeByName("theme1")).thenReturn(new Theme("theme1"));
		Mockito.when(themeService.findThemeByName("theme2")).thenReturn(new Theme("theme2"));
		Mockito.when(themeService.findThemeByName("theme3")).thenReturn(new Theme("theme3"));
		
		CrmArticle crmArticle = getCrmArticle();
		Article result = articleServiceImpl.update(0, crmArticle);
		
		Assertions.assertThrows(RuntimeException.class, () -> {articleServiceImpl.update(99, crmArticle);});
		Assertions.assertNotNull(result);
		Assertions.assertEquals("theme1", result.getThemes().get(0).getName());
		Assertions.assertEquals("theme2", result.getThemes().get(1).getName());
		Assertions.assertEquals("theme3", result.getThemes().get(2).getName());
		Assertions.assertEquals(3, result.getThemes().size());
	}

	@Test
	void testLikeArticle() {
		
		User testUser = getUserWithThemes();
		Article testArticleWithoutLikes = getArticlesWithThemes().get(0);
		
		Mockito.when(articleServiceImpl.findArticleById(99)).thenReturn(null);
		Mockito.when(articleServiceImpl.findArticleById(0)).thenReturn(testArticleWithoutLikes);
		Mockito.when(userService.getUserByToken(any())).thenReturn(testUser);
		
		Assertions.assertThrows(RuntimeException.class, () -> {articleServiceImpl.likeArticle(99, null);});
		
		Article resultWithLike = articleServiceImpl.likeArticle(0, null);
		Assertions.assertEquals(1, resultWithLike.getLikes().size());
		Assertions.assertEquals(testUser, resultWithLike.getLikes().get(0));
		
		Article resultWithoutLike = articleServiceImpl.likeArticle(0, null);
		Assertions.assertEquals(0, resultWithoutLike.getLikes().size());
		
	}

	private List<Article> getArticlesWithThemes(){
		
		List <Article> articlesWithThemes = new ArrayList<>();
		
		Article article0 = new Article("header0", "content0", getCurrentDateMinusSeconds(40_000L));
		Article article1 = new Article("header1", "content1", getCurrentDateMinusSeconds(80_000L));
		Article article2 = new Article("header2", "content2", getCurrentDateMinusSeconds(30_000L));
		Article article3 = new Article("header3", "content3", getCurrentDateMinusSeconds(20_000L));
		Article forbidden = new Article("forbidden", "article", getCurrentDateMinusSeconds(10_000L));
		
		article0.setThemes(List.of(new Theme("theme1")));
		article1.setThemes(List.of(new Theme("theme1"), new Theme("theme2")));
		article2.setThemes(new ArrayList<>());
		article3.setThemes(new ArrayList<>());
		forbidden.setThemes(List.of(new Theme("theme3")));
		
		articlesWithThemes.add(article0);
		articlesWithThemes.add(article1);
		articlesWithThemes.add(article2);
		articlesWithThemes.add(article3);
		articlesWithThemes.add(forbidden);
		
		//for testLikeArticle
		List<User> likes = new ArrayList<>();
		article0.setLikes(likes);

		return articlesWithThemes;
	}
	
	private User getUserWithoutThemes() {
		
		User userWithoutThemes = new User("user@without.themes", "user", "without", "themes");
		
		List<Theme> emptyList = new ArrayList<>();
		
		userWithoutThemes.setPrefer(emptyList);
		userWithoutThemes.setForbid(emptyList);
		
		return userWithoutThemes;
	}
	
	private User getUserWithThemes() {
		
		User userWithThemes = new User("user@with.themes", "user", "with", "themes");
		
		userWithThemes.setPrefer(List.of(new Theme("theme1"), new Theme("theme2")));
		userWithThemes.setForbid(List.of(new Theme("theme3")));
		
		return userWithThemes;
	}
	
	private CrmArticle getCrmArticle() {
		
		CrmArticle crmArticle = new CrmArticle();
		
		crmArticle.setHeader("CrmArticle");
		crmArticle.setContent("This is a test CrmArticle");
		crmArticle.setThemes("Theme1 theme2, theme1.,%^&theme3");
		
		return crmArticle;
	}
	
	private String getCurrentDateMinusSeconds(long seconds) {	
		
		return formatter.format(new Date().getTime() - seconds*1000);
	}
	
}
