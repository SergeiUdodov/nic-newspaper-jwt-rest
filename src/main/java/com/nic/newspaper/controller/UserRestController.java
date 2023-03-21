package com.nic.newspaper.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.entity.Role;
import com.nic.newspaper.entity.User;
import com.nic.newspaper.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
//@CrossOrigin("http://localhost:8081/")
@CrossOrigin
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> findAll() {
		return userService.findAll();
	}

	@GetMapping("/users/{UserId}")
	public User getUser(@PathVariable int UserId) {

		User theUser = userService.findById(UserId);

		if (theUser == null) {
			throw new RuntimeException("User id not found - " + UserId);
		}

		return theUser;
	}

	@GetMapping("/userByToken")
	public User getUserByToken(HttpServletRequest request) {

		return userService.getUserByToken(request);
	}

	@GetMapping("/isUserAdmin")
	public boolean isUserAdmin(HttpServletRequest request) {
		boolean isAdmin = false;
		Collection<Role> userRoles = getUserByToken(request).getRoles();
		for (Role role : userRoles) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				isAdmin = true;
			}
		}
		return isAdmin;
	}

}
