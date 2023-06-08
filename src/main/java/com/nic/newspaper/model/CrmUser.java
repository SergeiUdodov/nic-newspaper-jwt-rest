package com.nic.newspaper.model;

import lombok.Data;

@Data
public class CrmUser {

	private String email;

	private String firstName;

	private String lastName;

	private String password;

	private String prefer;

	private String forbid;

}
