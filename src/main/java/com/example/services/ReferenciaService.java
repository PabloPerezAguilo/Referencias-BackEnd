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
import com.example.models.Referencia;
import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/referencia/")
@Api(value = "/referencia", description = "Referencias operations")
@Produces(MediaType.APPLICATION_JSON)
public class ReferenciaService extends Service{

	private static final Logger log = Logger.getLogger(ReferenciaService.class.getName());

	public ReferenciaService() {
		super();
	}
	
	/**
	 * GET Referencias
	 * @return Referencias
	 */
	@GET
	@ApiOperation(value = "Get all referencias", notes = "Return all referencias")
	public Response getReferencias() {
		try {
			ReferenciaController resourceController = ReferenciaController.getInstance();
			out = resourceController.getReferencias();
			log.info("Get All Referencias: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

	/**
	 * GET Referencia
	 * @param key
	 * @return Referencia
	 */
	@GET
	@Path("/{key}")
	@ApiOperation(value = "Get Referencia by key", notes = "Get Referencia by key")
	public Response getResource(@PathParam("key") int key) {
		try {
			ReferenciaController resourceController = ReferenciaController.getInstance();
			out = resourceController.getReferencia(key);
			log.info("Get Referencia by key: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

	/**
	 * POST Referencia
	 * @param r
	 * @return Referencia
	 */
	@POST
	@ApiOperation(value = "Create a Referencia", notes = "Create a Referencia")
	public Response postReferencia(Referencia r) {
		try {
			ReferenciaController resourceController = ReferenciaController.getInstance();
			out = resourceController.createReferencia(r);
			log.info("Insert Referencia: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * DELETE Referencia
	 * @param key
	 * @return key
	 */
	@DELETE
	@Path("/{key}")
	@ApiOperation(value = "Delete a Referencia", notes = "Delete a Referencia")
	public Response postReferenciaDelete(@PathParam("key") int key){
		try{
			ReferenciaController resourceController = ReferenciaController.getInstance();
			out = resourceController.deleteReferencia(key);
			log.info("Delete Referencia : Operation successful");
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * PUT Referencia
	 * @param r
	 * @return r
	 */
	@PUT
	@ApiOperation(value = "Update a Referencia", notes = "Update a Referencia")
	public Response postReferenciaUpdate(Referencia r){
		
		try{
			ReferenciaController resourceController = ReferenciaController.getInstance();
			out = resourceController.updateReferencia(r.get_id(),r);
			log.info("| Update Referencia | : Operation successful");
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		
		return Response.status(status).entity(out).build();
	}
	
	
}