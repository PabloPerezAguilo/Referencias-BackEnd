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

import com.example.controllers.ReferenciaController;
import com.example.controllers.UsuarioController;
import com.example.models.Referencia;
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
	
	@GET
	@ApiOperation(value = "Get all usuarios", notes = "Return all usuarios")
	public Response getUsuarios() {
		try {
			UsuarioController resourceController = UsuarioController.getInstance();
			out = resourceController.getUsuarios();
			log.info("Get All Usuarios: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

	@GET
	@Path("/{key}")
	@ApiOperation(value = "Get usuario by key", notes = "Get usuario by key")
	public Response getUsuario(@PathParam("key") String key) {
		try {
			UsuarioController resourceController = UsuarioController.getInstance();
			out = resourceController.getUsuario(key);
			log.info("Get Referencia by key: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

	@POST
	@ApiOperation(value = "Create a Usuario", notes = "Create a Usuario")
	public Response postUsuario(Usuario r) {
		try {
			UsuarioController resourceController = UsuarioController.getInstance();
			resourceController.createUsuario(r.getName(),r.getRole());
			log.info("Insert Usuario: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
		}
		return Response.status(status).entity(out).build();
	}
	
	@DELETE
	@Path("/{key}")
	@ApiOperation(value = "Delete a Usuario", notes = "Delete a Usuario")
	public Response postReferenciaDelete(@PathParam("key") int key){
		try{
			UsuarioController resourceController = UsuarioController.getInstance();
			/*out = resourceController.delete(key);*/
			log.info("Delete Referencia : Operation successful");
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	@PUT
	@ApiOperation(value = "Update a Usuario", notes = "Update a Usuario")
	public Response postReferenciaUpdate(Referencia r){
		
		try{
			ReferenciaController resourceController = ReferenciaController.getInstance();
			/*out = resourceController.updateReferencia(r.get_id(),r);*/
			log.info("| Update Referencia | : Operation successful");
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		
		return Response.status(status).entity(out).build();
	}
	
	
}