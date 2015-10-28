package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

/**
 * The Class Usuario.
 */
public class User {
	@Id
	private String name;
	private String role;
	private String password;

	public User() {
	}

	public User(String name, String role, String password) {
		this.name = name;
		this.role = role;
		this.password = password;
	}

	public User(User u) {
		this.name = u.getName();
		this.role = u.getRole();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return name + "//" + role + "//" + password;
	}
}