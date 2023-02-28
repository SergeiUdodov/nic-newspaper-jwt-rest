package com.nic.newspaper.service;

import java.util.List;

import com.nic.newspaper.entity.User;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public List<User> findAll();

	public User findById(int theId);

	public User getUserByToken(HttpServletRequest request);

}
