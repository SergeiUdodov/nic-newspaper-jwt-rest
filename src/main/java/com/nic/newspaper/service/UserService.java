package com.nic.newspaper.service;

import com.nic.newspaper.entity.User;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public User getUserByToken(HttpServletRequest request);

}
