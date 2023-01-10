package com.nic.newspaper.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.UserDAO;
import com.nic.newspaper.model.CrmUser;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	Log logger = LogFactory.getLog(getClass());

	@Autowired
	private UserDAO userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.nic.newspaper.entity.User user = userDao.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new User(user.getFirstName(), user.getPassword(), new ArrayList<>());

	}

	public com.nic.newspaper.entity.User save(CrmUser user) {
		com.nic.newspaper.entity.User newUser = new com.nic.newspaper.entity.User();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRoles(Arrays.asList(userDao.findRoleByName("ROLE_USER")));
		return userDao.save(newUser);

	}
}