package com.nic.newspaper.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.UserDAO;
import com.nic.newspaper.entity.Theme;
import com.nic.newspaper.model.CrmUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private ThemeService themeService;

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

		return new User(user.getEmail(), user.getPassword(), user.getRoles());

	}

	@Transactional
	public com.nic.newspaper.entity.User save(CrmUser user) {
		com.nic.newspaper.entity.User newUser = new com.nic.newspaper.entity.User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRoles(Arrays.asList(userDao.findRoleByName("ROLE_USER")));
		return userDao.save(newUser);

	}

	@Transactional
	public com.nic.newspaper.entity.User updateUser(CrmUser crmUser, HttpServletRequest request) {

		com.nic.newspaper.entity.User theUser = userService.getUserByToken(request);

		String[] preferThemesNames = crmUser.getPrefer().toLowerCase().split("[^a-zа-я0-9]+");
		String[] forbidThemesNames = crmUser.getForbid().toLowerCase().split("[^a-zа-я0-9]+");

		List<Theme> prefer = new ArrayList<>();
		List<Theme> forbid = new ArrayList<>();

		Theme temp = null;
		for (String themeName : preferThemesNames) {
			if (!"".equals(themeName)) {
				temp = themeService.findThemeByName(themeName);
				prefer.add(temp);
			}
		}
		temp = null;
		
		for (String themeName : forbidThemesNames) {
			if (!"".equals(themeName)) {
				temp = themeService.findThemeByName(themeName);
				forbid.add(temp);
			}
		}
		temp = null;

		theUser.setPrefer(prefer);
		theUser.setForbid(forbid);
		
		theUser.setEmail(crmUser.getEmail());
		theUser.setFirstName(crmUser.getFirstName());
		theUser.setLastName(crmUser.getLastName());
		if(!"".equals(crmUser.getPassword())) {
			theUser.setPassword(bcryptEncoder.encode(crmUser.getPassword()));
		}

		return userDao.updateUser(theUser);
	}
}
