package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

/**
 * The Class Usuario.
 */
public class UsuarioLdap {
	@Id
	private String nick;
	private String password;

	public UsuarioLdap() {
	}

	public UsuarioLdap(String name, String pass) {
		this.nick = name;
		this.password = pass;
	}

	public UsuarioLdap(UsuarioLdap u) {
		this.nick = u.getNick();
		this.password = u.getPassword();
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String name) {
		this.nick = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UsuarioLdap [name=" + nick + ", password=" + password + "]";
	}

	
}