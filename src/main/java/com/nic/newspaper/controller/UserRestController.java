package com.nic.newspaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.entity.User;
import com.nic.newspaper.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping("/userByToken")
	public User getUserByToken(HttpServletRequest request) {

		return userService.getUserByToken(request);
	}

	@GetMapping("/isUserAdmin")
	public boolean isUserAdmin(HttpServletRequest request) {
		
		return userService.isUserAdmin(request);
	}

}
