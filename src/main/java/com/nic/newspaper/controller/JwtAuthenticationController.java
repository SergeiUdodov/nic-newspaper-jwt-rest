package com.nic.newspaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.config.JwtTokenUtil;
import com.nic.newspaper.model.CrmUser;
import com.nic.newspaper.model.JwtRequest;
import com.nic.newspaper.model.JwtResponse;
import com.nic.newspaper.service.JwtUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
//@CrossOrigin("http://localhost:8081/")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody CrmUser user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	@PutMapping("/updateUser")
	public ResponseEntity<?> updateUser(@RequestBody CrmUser crmUser, HttpServletRequest request) throws Exception {
		return ResponseEntity.ok(userDetailsService.updateUser(crmUser, request));
	}

	private void authenticate(String userEmail, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}