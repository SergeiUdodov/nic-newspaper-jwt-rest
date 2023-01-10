package com.nic.newspaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.dao.UserDAO;
import com.nic.newspaper.entity.User;
import com.nic.newspaper.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService theUserService) {
		userService = theUserService;
	}

	@Autowired
	private UserDAO userDao;

	// expose "/Users" and return list of Users
	@GetMapping("/users")
	public List<User> findAll() {
		return userService.findAll();
	}

	// add mapping for GET /Users/{UserId}

	@GetMapping("/users/{UserId}")
	public User getUser(@PathVariable int UserId) {

		User theUser = userService.findById(UserId);

		if (theUser == null) {
			throw new RuntimeException("User id not found - " + UserId);
		}

		return theUser;
	}

//	@GetMapping("/users/{userName}")
//	public User getUserName(@PathVariable String userName) {
//
//		User theUser = userDao.findByUserName(userName);
//
//		if (theUser == null) {
//			throw new RuntimeException("User name not found - " + userName);
//		}
//
//		return theUser;
//	}

	// add mapping for POST /Users - add new User

	@PostMapping("/users")
	public User addUser(@RequestBody User theUser) {

		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update

		theUser.setId((long) 0);

		userService.save(theUser);

		return theUser;
	}

	// add mapping for PUT /Users - update existing User

	@PutMapping("/users")
	public User updateUser(@RequestBody User theUser) {

		userService.save(theUser);

		return theUser;
	}

	// add mapping for DELETE /Users/{UserId} - delete User

	@DeleteMapping("/users/{UserId}")
	public String deleteUser(@PathVariable int UserId) {

		User tempUser = userService.findById(UserId);

		// throw exception if null

		if (tempUser == null) {
			throw new RuntimeException("User id not found - " + UserId);
		}

		userService.deleteById(UserId);

		return "Deleted User id - " + UserId;
	}

}
