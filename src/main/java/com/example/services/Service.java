package com.example.services;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.models.AuthenticatedUser;


public class Service {

	protected AuthenticatedUser usuarioAutenticado;
	protected Status status;
	protected Object out;

	public Service() {
		usuarioAutenticado = new AuthenticatedUser();
		usuarioAutenticado.setUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
		usuarioAutenticado.setRol(SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority());
		status = Response.Status.OK;
	}

}
