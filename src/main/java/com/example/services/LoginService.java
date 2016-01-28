package com.example.services;

import java.util.HashMap;
import java.util.Map;

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

@Path("/login/")
@Api(value = "/login", description = "Login operations")
@Produces(MediaType.APPLICATION_JSON)

public class LoginService {

	private static final Logger log = Logger.getLogger(LoginService.class.getName());
	
	/**
	 * POST Login
	 * @param usuario | Objeto UsuarioLdap
	 * @return Response.status
	 */
	@POST
	@ApiOperation(value = "Make login user", notes = "Comprueba usuario/password y devuelve el rol")
	public Response login(Map<String,String> usuario) {
		
		Status status = Response.Status.BAD_REQUEST;
		Object out;

		try {
			UsuarioController userController = UsuarioController.getInstance();
			// Make login
			Usuario usu = userController.loginUserLdap(usuario);
			// Take the role and insert into a map
			HashMap<String, Object> outMap = new HashMap<String, Object>();
			outMap.put("role", usu.getRole());
			outMap.put("name", usu.getName());
			outMap.put("nick", usu.getNick());
			//outMap.put("name", );
			out = outMap;
			status = Response.Status.OK;
			log.info("Login successful from user:" + (String)usuario.get("nick"));
			
		} catch (Exception e) {
			log.error("Error in login from user " + (String)usuario.get("nick") + ": ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	} 
}