package com.nic.newspaper.dao;

import java.util.List;

import com.nic.newspaper.entity.Role;
import com.nic.newspaper.entity.User;

public interface UserDAO {

	public List<User> findAll();

	public User findById(int theId);

	public User findByUserName(String theUserName);

	public User save(User theUser);

	public void deleteById(int theId);

	Role findRoleByName(String theRoleName);

}
