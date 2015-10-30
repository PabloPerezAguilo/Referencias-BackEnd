package com.example.dao;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;

import com.example.models.UsuarioLdap;
import com.example.utils.Config;

public class UsuarioLdapDAO {
	
	private String name;
	private String password;
	
	public UsuarioLdapDAO(UsuarioLdap usuario){
		
		name = usuario.getName();
		password = usuario.getPassword();
	}

	public Authentication LoginLdap() throws IOException {
		
		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(Config.getInstance().getProperty(Config.LDAP_URL));
		contextSource.setCacheEnvironmentProperties(false);
		BindAuthenticator authenticator = new BindAuthenticator(contextSource);
		String[] patterns = { Config.getInstance().getProperty(Config.LDAP_USER_DN_PATTERN) };
		authenticator.setUserDnPatterns(patterns);
		LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(authenticator);
		Authentication authentication = ldapAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(name,password));
		
		return authentication;
	}
}
