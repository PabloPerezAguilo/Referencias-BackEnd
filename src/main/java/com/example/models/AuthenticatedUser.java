package com.example.models;

public class AuthenticatedUser {
	private String usuario;
	private String rol;

	public AuthenticatedUser() {
	}

	public AuthenticatedUser(String usuario, String rol) {
		this.usuario = usuario;
		this.rol = rol;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public boolean esMismoUsuario(String usuarioLogado) {
		return this.usuario.equalsIgnoreCase(usuarioLogado);
	}

	public boolean esAdmin() {
		return this.rol.equalsIgnoreCase("ROLE_ADMIN");
	}
}