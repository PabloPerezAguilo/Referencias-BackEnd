package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

/**
 * The Class Usuario.
 */
public class UsuarioLdap {
	@Id
	private String name;
	private String password;

	public UsuarioLdap() {
	}

	public UsuarioLdap(String name, String pass) {
		this.name = name;
		this.password = pass;
	}

	public UsuarioLdap(UsuarioLdap u) {
		this.name = u.getName();
		this.password = u.getPassword();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UsuarioLdap [name=" + name + ", password=" + password + "]";
	}

	
}