package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

/**
 * The Class Usuario.
 */
public class Usuario {
	@Id
	private String name;
	private String role;

	public Usuario() {
	}

	public Usuario(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public Usuario(Usuario u) {
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


	@Override
	public String toString() {
		return name + "//" + role;
	}
}