package com.example.models;

import javax.naming.directory.Attributes;

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
	public InformacionUsuarioLdap(Attributes usuarioAtributo) {
		
			if(usuarioAtributo.get("mail") != null){
				this.mail = usuarioAtributo.get("mail").toString().replaceAll("mail: ","");
			}else{
				this.mail = "No introducido";
			}
			if(usuarioAtributo.get("cn") != null){
				this.usuario = usuarioAtributo.get("cn").toString().replaceAll("cn: ","");
			}else{
				this.usuario = "No introducido";
			}
			if(usuarioAtributo.get("uid") != null){
				this.nick =  usuarioAtributo.get("uid").toString().replaceAll("uid: ","");
			}else{
				this.nick = "No introducido";
			}
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