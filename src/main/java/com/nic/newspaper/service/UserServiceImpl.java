package com.nic.newspaper.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nic.newspaper.config.JwtTokenUtil;
import com.nic.newspaper.dao.UserDAO;
import com.nic.newspaper.entity.Role;
import com.nic.newspaper.entity.User;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public User getUserByToken(HttpServletRequest request) {

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

		return userDAO.findByUserEmail(userEmail);
	}

	@Override
	@Transactional
	public boolean isUserAdmin(HttpServletRequest request) {
		
		boolean isAdmin = false;
		List<Role> userRoles = getUserByToken(request).getRoles();
		for (Role role : userRoles) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				isAdmin = true;
			}
		}
		return isAdmin;
	}

}
