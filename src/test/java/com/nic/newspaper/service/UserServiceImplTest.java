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

import com.nic.newspaper.config.JwtTokenUtil;
import com.nic.newspaper.dao.UserDAO;
import com.nic.newspaper.entity.Role;
import com.nic.newspaper.entity.User;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	
	@Mock
	private HttpServletRequest request1;
	
	@Mock
	private HttpServletRequest request2;
	
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@Mock
	private UserDAO userDAO;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Test
	void testIsUserAdmin() {
		
		User simpleUser = getSimpleUser();
		User adminUser = getAdminUser();
		
		Mockito.when(request1.getHeader(any())).thenReturn("Bearer 1");
		Mockito.when(request2.getHeader(any())).thenReturn("Bearer 2");
		Mockito.when(jwtTokenUtil.getUserEmailFromToken("1")).thenReturn("email1");
		Mockito.when(jwtTokenUtil.getUserEmailFromToken("2")).thenReturn("email2");
		Mockito.when(userDAO.findByUserEmail("email1")).thenReturn(simpleUser);
		Mockito.when(userDAO.findByUserEmail("email2")).thenReturn(adminUser);
		
		//request1 -> simpleUser; request2 -> adminUser
		boolean result1 = userServiceImpl.isUserAdmin(request1);
		boolean result2 = userServiceImpl.isUserAdmin(request2);
		
		Assertions.assertEquals(false, result1);
		Assertions.assertEquals(true, result2);
		
	}
	
	private User getSimpleUser() {
		
		User simpleUser = new User();	
		simpleUser.setRoles(List.of(new Role("ROLE_USER")));
		
		return simpleUser;
	}
	
	private User getAdminUser() {
		
		User adminUser = new User();
		adminUser.setRoles(List.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN")));
		
		return adminUser;
	}

}
