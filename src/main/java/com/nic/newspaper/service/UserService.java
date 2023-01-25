package com.nic.newspaper.service;

import java.util.List;

import com.nic.newspaper.entity.User;

public interface UserService {

	public List<User> findAll();

	public User findById(int theId);

	public User getUserByToken(String userEmail);

}
