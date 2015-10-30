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
@Api(value = "/usuariosldap", description = "LDAP operations")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioLdapService extends Service{

	private static final Logger log = Logger.getLogger(UsuarioLdapService.class.getName());
	
	@GET
	@ApiOperation(value = "Get all users ldap", notes = "Return all users of ldap")
	public Response getUsersLdap() {
		try {
			UsuarioController resourceController = UsuarioController.getInstance();
			out = resourceController.getAllUserLdap();
			log.info("Get All Users Ldap: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	} 
}