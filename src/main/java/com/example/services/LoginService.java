package com.example.services;

import java.util.HashMap;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.example.controllers.UsuarioController;
import com.example.models.Usuario;
import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/login/")
@Api(value = "/login", description = "Login operations")
@Produces(MediaType.APPLICATION_JSON)
public class LoginService {

	private static final Logger log = Logger.getLogger(LoginService.class.getName());

	@POST
	@ApiOperation(value = "Make login user", notes = "Check user/password and return their role")
	public Response login(@ApiParam(value = "Role field is not required", required = true) Usuario usuario) {
		Status status = Response.Status.BAD_REQUEST;
		Object out;
		try {
			UsuarioController userController = UsuarioController.getInstance();
			// Make login
			String role = userController.loginUser(usuario.getName(), usuario.getPassword());
			// Take the role and insert into a map
			HashMap<String, Object> outMap = new HashMap<String, Object>();
			outMap.put("role", role);
			out = outMap;
			status = Response.Status.OK;
			log.info("Login successful from user:" + usuario.getName());
		} catch (Exception e) {
			log.error("Error in login from user " + usuario.getName() + ": ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
}