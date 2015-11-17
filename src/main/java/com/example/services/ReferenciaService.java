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
import com.example.models.ReferenciaWithAutoID;
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
	 * @return ReferenciaWithAutoID
	 */
	@GET
	@ApiOperation(value = "Devuelve todas las referencias", notes = "Devuelve todas las referencias")
	public Response getReferencias() {
		try {
			ReferenciaController referenciaController = ReferenciaController.getInstance();
			out = referenciaController.getReferencias();
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
	@ApiOperation(value = "Devuelve una referencia mediante parametro", notes = "Devuelve una referencia mediante parametro")
	public Response getReferencia(@PathParam("key") int key) {
		try {
			ReferenciaController referenciaController = ReferenciaController.getInstance();
			out = referenciaController.getReferencia(key);
			log.info("Get Referencia by key: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * GET ReferenciasPendientes
	 * @return Referencias
	 */
	@GET
	@Path("/pendientes")
	@ApiOperation(value = "Devuelve una referencia mediante parametro", notes = "Devuelve una referencia mediante parametro")
	public Response getReferenciasPendientes() {
		try {
			ReferenciaController referenciaController = ReferenciaController.getInstance();
			out = referenciaController.getReferenciasPendientes();
			log.info("Get Referencias Pendientes: Operation successful");
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
	@ApiOperation(value = "Crea una nueva referencia", notes = "Crea una nueva referencia")
	public Response postReferencia(ReferenciaWithAutoID r) {
		try {
			ReferenciaController referenciaController = ReferenciaController.getInstance();
			out = referenciaController.createReferencia(r);
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
	@ApiOperation(value = "Borra una Referencia", notes = "Borra una Referencia")
	public Response deleteReferencia(@PathParam("key") int key){
		try{
			ReferenciaController referenciaController = ReferenciaController.getInstance();
			out = referenciaController.deleteReferencia(key);
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
	@ApiOperation(value = "Modifica una Referencia", notes = "Modifica una Referencia")
	public Response updateReferencia(ReferenciaWithAutoID r){
		
		try{
			ReferenciaController referenciaController = ReferenciaController.getInstance();
			out = referenciaController.updateReferencia(r.get_id(),r);
			log.info("Update Referencia : Operation successful");
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		
		return Response.status(status).entity(out).build();
	}
	
	
}