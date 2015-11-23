package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

/**
 * The Class Usuario.
 */
public class Usuario {
	@Id
	private String nick;
	private String name;
	private String role;

	public Usuario() {
	}

	public Usuario(String nick, String name, String role) {
		super();
		this.nick = nick;
		this.name = name;
		this.role = role;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
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
		return "Usuario [nick=" + nick + ", name=" + name + ", role=" + role
				+ "]";
	}

	
	
	
}