package com.nic.newspaper.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nic.newspaper.dao.UserDAO;
import com.nic.newspaper.entity.Role;
import com.nic.newspaper.entity.Theme;
import com.nic.newspaper.model.CrmUser;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {
	
	@Mock
	private UserDAO userDAO;
	
	@Mock
	private PasswordEncoder bcryptEncoder;
	
	@Mock
	private UserService userService;
	
	@Mock
	private ThemeService themeService;

	@InjectMocks
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Test
	void testLoadUserByUsername() {
		
		CrmUser crmUser = getCrmUser();
		com.nic.newspaper.entity.User entityUser = getUser(crmUser);
		UserDetails detailsUser = new User(entityUser.getEmail(), entityUser.getPassword(), entityUser.getRoles());
		
		Mockito.when(userDAO.findByUserEmail("email1")).thenReturn(null);
		Mockito.when(userDAO.findByUserEmail("email2")).thenReturn(getUser(getCrmUser()));
		
		UserDetails result = jwtUserDetailsService.loadUserByUsername("email2");
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {jwtUserDetailsService.loadUserByUsername("email1");});
		Assertions.assertNotNull(result);
		Assertions.assertEquals(detailsUser, result);
		
	}

	@Test
	void testSave() {

		CrmUser crmUser = getCrmUser();
		com.nic.newspaper.entity.User entityUser = getUser(crmUser);
		
		Mockito.when(userDAO.findRoleByName("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
		Mockito.when(bcryptEncoder.encode(crmUser.getPassword())).thenReturn(crmUser.getPassword());
		
		com.nic.newspaper.entity.User result = jwtUserDetailsService.save(getCrmUser());
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(entityUser, result);
	}

	@Test
	void testUpdateUser() {
		
		CrmUser crmUser = getCrmUser();
		com.nic.newspaper.entity.User entityUser = getUser(crmUser);
		
		CrmUser crmUserWhithThemes = getCrmUserWhithThemes();
		
		Mockito.when(userService.getUserByToken(any())).thenReturn(entityUser);
		Mockito.when(themeService.findThemeByName("theme1")).thenReturn(new Theme("theme1"));
		Mockito.when(themeService.findThemeByName("theme2")).thenReturn(new Theme("theme2"));
		Mockito.when(themeService.findThemeByName("theme3")).thenReturn(new Theme("theme3"));
		Mockito.when(themeService.findThemeByName("theme4")).thenReturn(new Theme("theme4"));
		Mockito.when(themeService.findThemeByName("theme5")).thenReturn(new Theme("theme5"));
		Mockito.when(bcryptEncoder.encode(crmUserWhithThemes.getPassword())).thenReturn(crmUserWhithThemes.getPassword());
		
		Assertions.assertEquals("test@mail.com", entityUser.getEmail());
		Assertions.assertEquals("testPassword", entityUser.getPassword());
		
		jwtUserDetailsService.updateUser(crmUserWhithThemes, null);
		
		Assertions.assertNotNull(entityUser);
		Assertions.assertEquals("updated@mail.com", entityUser.getEmail());
		Assertions.assertEquals("updatedPassword", entityUser.getPassword());
		Assertions.assertEquals("updatedFirstName", entityUser.getFirstName());
		Assertions.assertEquals("updatedLastName", entityUser.getLastName());
		Assertions.assertEquals("theme1", entityUser.getPrefer().get(0).getName());
		Assertions.assertEquals("theme2", entityUser.getPrefer().get(1).getName());
		Assertions.assertEquals("theme3", entityUser.getPrefer().get(2).getName());
		Assertions.assertEquals("theme4", entityUser.getForbid().get(0).getName());
		Assertions.assertEquals("theme5", entityUser.getForbid().get(1).getName());
		
		
	}
	
	private com.nic.newspaper.entity.User getUser(CrmUser crmUser){
		
		com.nic.newspaper.entity.User user = new com.nic.newspaper.entity.User();
		user.setEmail(crmUser.getEmail());
		user.setPassword(crmUser.getPassword());
		user.setRoles(List.of(new Role("ROLE_USER")));
		
		return user;
	}
	
	private CrmUser getCrmUser() {
		
		CrmUser crmUser = new CrmUser();	
		crmUser.setEmail("test@mail.com");
		crmUser.setPassword("testPassword");
		
		return crmUser;
	}
	
	private CrmUser getCrmUserWhithThemes() {
		
		CrmUser crmUserWhithThemes = new CrmUser();	
		crmUserWhithThemes.setEmail("updated@mail.com");
		crmUserWhithThemes.setPassword("updatedPassword");
		crmUserWhithThemes.setFirstName("updatedFirstName");
		crmUserWhithThemes.setLastName("updatedLastName");
		crmUserWhithThemes.setPrefer("Theme1 theme2,$theme3");
		crmUserWhithThemes.setForbid("theme4. #*thEmE5^");
		
		return crmUserWhithThemes;
	}

}
