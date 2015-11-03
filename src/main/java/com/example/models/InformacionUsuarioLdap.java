package com.example.models;

/**
 * The Class Usuario.
 */
public class InformacionUsuarioLdap {

	private String mail;
	private String usuario;
	private String nick;
	
	public InformacionUsuarioLdap() {
		
	}

	public InformacionUsuarioLdap(String mail, String usuario, String nick) {
		
		this.mail = mail;
		this.usuario = usuario;
		this.nick = nick;
		
	}

	public InformacionUsuarioLdap(InformacionUsuarioLdap u) {
		
		this.mail = u.getMail();
		this.usuario = u.getUsuario();
		this.nick = u.getNick();
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
}