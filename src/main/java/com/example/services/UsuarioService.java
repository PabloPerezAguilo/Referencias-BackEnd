package com.example.services;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.controllers.UsuarioController;
import com.example.models.Usuario;
import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/usuarios/")
@Api(value = "/usuarios", description = "Usuarios operations")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioService extends Service{

	private static final Logger log = Logger.getLogger(UsuarioService.class.getName());

	public UsuarioService() {
		super();
	}
	
	/**
	 * GET Usuarios
	 * @return ArrayList<Usuarios>
	 */
	@GET
	@ApiOperation(value = "Devuelve todos los usuarios", notes = "Devuelve todos los usuarios")
	public Response getUsuarios() {
		try {
			UsuarioController usuarioController = UsuarioController.getInstance();
			out = usuarioController.getUsuarios();
			log.info("Get All Usuarios: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

	/**
	 * GET Usuario
	 * @param key
	 * @return Usuario
	 */
	@GET
	@Path("/{key}")
	@ApiOperation(value = "Devuelve un usuario por parametro", notes = "Devuelve un usuario por parametro")
	public Response getUsuario(@PathParam("key") String key) {
		try {
			UsuarioController usuarioController = UsuarioController.getInstance();
			out = usuarioController.getUsuario(key);
			log.info("Get Referencia by key: Operation successful");
			
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

	/**
	 * POST Usuario
	 * @param r
	 * @return r
	 */
	@POST
	@ApiOperation(value = "Crear un Usuario", notes = "Crear un Usuario")
	public Response postUsuario(Usuario r) {
		try {
			
			UsuarioController usuarioController = UsuarioController.getInstance();
			if(r.getRole()== "" || r.getName().isEmpty() || r.getNick().isEmpty()){
				 throw new Exception("Nick, Nombre o Rol vacio");
			}
			usuarioController.createUsuario(r.getNick(),r.getName(),r.getRole());
			log.info("Insert Usuario: Operation successful");
			status = Response.Status.ACCEPTED;
			out = r;
			
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * DELETE Usuario
	 * @param key
	 * @return key
	 */
	@DELETE
	@Path("/{key}")
	@ApiOperation(value = "Borrar un Usuario", notes = "Borrar un Usuario")
	public Response deleteUsuario(@PathParam("key") String key){
		try{
			UsuarioController usuarioController = UsuarioController.getInstance();
			out = new Message(usuarioController.deleteUsuario(key));
			log.info("Delete usuario : Operation successful");
			status = Response.Status.ACCEPTED;
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * PUT Usuario
	 * @param r
	 * @return r
	 */
	@PUT
	@ApiOperation(value = "Modifica un Usuario", notes = "Modifica un Usuario")
	public Response updateUsuario(Usuario r){
		
		try{
			UsuarioController usuarioController = UsuarioController.getInstance();
			out = usuarioController.updateUsuario(r.getNick(), r);
			log.info("Update Referencia : Operation successful");
			status = Response.Status.ACCEPTED;
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		
		return Response.status(status).entity(out).build();
	}
	
	
}