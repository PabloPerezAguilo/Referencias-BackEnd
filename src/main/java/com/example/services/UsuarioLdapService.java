package com.example.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.controllers.UsuarioController;
import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/usuariosldap/")
@Api(value = "/usuariosldap", description = "conexion a Ldap")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioLdapService extends Service{

	private static final Logger log = Logger.getLogger(UsuarioLdapService.class.getName());
	
	/**
	 * GET UsuariosLdap
	 * @return ArrayList<UsuarioLdap>
	 */
	@GET
	@ApiOperation(value = "Devuelve todos los usuarios del Ldap", notes = "Devuelve todos los usuarios del Ldap")
	public Response getUsersLdap() {
		try {
			UsuarioController usuarioController = UsuarioController.getInstance();
			out = usuarioController.getAllUserLdap();
			log.info("Get All Users Ldap: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	} 
	@GET
	@Path("/gerentes")
	@ApiOperation(value = "Devuelve todos los usuarios del Ldap", notes = "Devuelve todos los usuarios del Ldap")
	public Response getUsersLdapGerentes() {
		try {
			UsuarioController usuarioController = UsuarioController.getInstance();
			out = usuarioController.getUserLdapGerentes();
			log.info("Get Ldap Gerentes: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	} 
}