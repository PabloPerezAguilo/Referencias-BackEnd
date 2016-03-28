package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

/**
 * The Class CatalogoGerentes.
 */
public class CatalogoGerentes {
	@Id
	private String login;
	private String nombre;
	private String apellidos;
	private String tipoGerente;

	public CatalogoGerentes() {
	}

	public CatalogoGerentes(String login, String nombre, String apellidos,
			String tipoGerente) {
		super();
		this.login = login;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.tipoGerente = tipoGerente;
	}
	
	public CatalogoGerentes(String login, String nombre, String apellidos) {
		super();
		this.login = login;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipoGerente() {
		return tipoGerente;
	}

	public void setTipoGerente(String tipoGerente) {
		this.tipoGerente = tipoGerente;
	}

	@Override
	public String toString() {
		return "CatalogoGerentes [login=" + login + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", tipoGerente=" + tipoGerente
				+ "]";
	}

	

	
}