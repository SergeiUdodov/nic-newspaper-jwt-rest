package com.nic.newspaper.service;

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
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		com.nic.newspaper.entity.User user = userDao.findByUserEmail(userEmail);

		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + userEmail);
		}

		logger.warn(user.getRoles());

		return new User(user.getEmail(), user.getPassword(), user.getRoles());

	}

	public com.nic.newspaper.entity.User save(CrmUser user) {
		com.nic.newspaper.entity.User newUser = new com.nic.newspaper.entity.User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRoles(Arrays.asList(userDao.findRoleByName("ROLE_USER")));
		return userDao.save(newUser);

	}
}
