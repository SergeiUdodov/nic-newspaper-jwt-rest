package com.nic.newspaper.model;

public class CrmUser {

	private String email;

	private String firstName;

	private String lastName;

	private String password;

	private String prefer;

	private String forbid;

	public CrmUser() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrefer() {
		return prefer;
	}

	public void setPrefer(String prefer) {
		this.prefer = prefer;
	}

	public String getForbid() {
		return forbid;
	}

	public void setForbid(String forbid) {
		this.forbid = forbid;
	}

}
