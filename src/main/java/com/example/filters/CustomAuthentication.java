package com.example.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.controllers.UsuarioController;
import com.example.models.Usuario;

@Component
public class CustomAuthentication implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(CustomAuthentication.class);

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// Take the name of header petition
		String name = authentication.getName();
		// Take the password of header petition in base64
		String password = authentication.getCredentials().toString();
		Authentication auth = null;
		String rol = "";
		try {
			UsuarioController user = UsuarioController.getInstance();
			Map<String,String> usuario = new HashMap<String,String>();
			usuario.put("nick", name);
			usuario.put("password", password);
			Usuario usu = user.loginUserLdap(usuario);
			rol = usu.getRole();
		} catch (Exception e) {
			log.error("Authentication error: ", e);
		}

		if (!rol.equalsIgnoreCase("")) {
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SwitchUserGrantedAuthority(rol, authentication));
			auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
		}
		return auth;
	}
}