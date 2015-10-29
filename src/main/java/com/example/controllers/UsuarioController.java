package com.example.controllers;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.example.dao.UsuarioDAO;
import com.example.models.Referencia;
import com.example.models.Usuario;
import com.example.utils.Config;

public class UsuarioController {

	// Singleton instances
	private UsuarioDAO dao;
	private static UsuarioController singleton;

	private UsuarioController() throws Exception {
		dao = UsuarioDAO.getInstance();
	}

	public static UsuarioController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new UsuarioController();
		}
		return singleton;
	}
	/*
	public void loginLdapUsuario(String user, String pass) throws Exception {

		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(
				Configuracion.getInstance().getProperty(Configuracion.LDAP_URL));
		contextSource.setCacheEnvironmentProperties(false);

		BindAuthenticator authenticator = new BindAuthenticator(contextSource);
		String[] patterns = { Configuracion.getInstance().getProperty(
				Configuracion.LDAP_USER_DN_PATTERN) };
		authenticator.setUserDnPatterns(patterns);

		LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(
				authenticator);

		Authentication authentication = ldapAuthenticationProvider
				.authenticate(new UsernamePasswordAuthenticationToken(user,
						pass));

		if (authentication == null) {
			throw new Exception("El password introducido no es valido");
		}

	}

	/**
	 * Check user/password and return the role
	 */
	public String loginUser(String idUser, String pass) throws Exception {
		
		// TO DO integrar ldap
		// Get the user hashed and salted password
		Usuario user = dao.getUsuarioLogin(idUser);
		if (user == null) {
			throw new Exception("User not found");
		}
		
		// conectar ldap y comprobar si esta con su pass 
		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(
				Config.getInstance().getProperty(Config.LDAP_URL));
		contextSource.setCacheEnvironmentProperties(false);
		BindAuthenticator authenticator = new BindAuthenticator(contextSource);
		String[] patterns = { Config.getInstance().getProperty(
				Config.LDAP_USER_DN_PATTERN) };
		authenticator.setUserDnPatterns(patterns);
		LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(
				authenticator);
		Authentication authentication = ldapAuthenticationProvider
				.authenticate(new UsernamePasswordAuthenticationToken(idUser,
						pass));
		
		if (authentication == null) {
			throw new Exception("User not found");
		}
		
		// If its all right return the role
		return user.getRole();
	}

	/**
	 * Create new user
	 */
	public void createUsuario(String name, String role, String password) throws Exception {
		dao.insertUsuario(new Usuario(name, role, this.makePasswordHash(password, this.generateSalting())));
		
	}

	/**
	 * Get all users
	 */
	public List<Usuario> getUsuarios() throws Exception {
		List<Usuario> list = new ArrayList<Usuario>();
		Iterator<Usuario> i = dao.getUsuarios();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}
	
	/**
	 * Get un usuario
	 */
	public Usuario getUsuario(String id) throws Exception {
		Usuario usu = new Usuario();
		usu = dao.getUsuario(id);
		return usu;
	}
	
	public String deleteUsuario(String key) throws Exception{
		dao.deleteUsuario(key);
		return key;
	}
	
	public Usuario updateReferencia(String key, Usuario r){
		dao.updateUsuario(key,r);
		return r;
	}

	// PRIVATE METHODS TO GENERATE PASSWORDS

	/**
	 * Generate a Hash for given Password.
	 */
	private String makePasswordHash(String password, String salt) throws Exception {

		String saltedAndHashed = password + "," + salt;
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(saltedAndHashed.getBytes("UTF-8"));
		Base64 encoder = new Base64();
		byte[] hashedBytes = (new String(digest.digest(), "UTF-8")).getBytes("UTF-8");
		return new String(encoder.encode(hashedBytes), "UTF-8") + "," + salt;
	}

	/**
	 * Generate a random salting.
	 */
	private String generateSalting() {
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

}