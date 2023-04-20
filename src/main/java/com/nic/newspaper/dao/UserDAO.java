package com.nic.newspaper.dao;

import com.nic.newspaper.entity.Role;
import com.nic.newspaper.entity.User;

public interface UserDAO {

	public User findByUserEmail(String userEmail);

	public void save(User theUser);
	
	public void updateUser(User theUser);

	public Role findRoleByName(String theRoleName);

}
