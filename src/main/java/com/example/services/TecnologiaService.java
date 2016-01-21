package com.example.services;


import java.util.Map;

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

import com.example.controllers.TecnologiaController;

import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/tecnologias")
@Api(value = "/tecnologias", description = "Gestion del arbol de tecnologias")
@Produces(MediaType.APPLICATION_JSON)
public class TecnologiaService extends Service{

	private static final Logger log = Logger.getLogger(TecnologiaService.class.getName());

	public TecnologiaService() {
		super();
	}
	
	
	@GET
	@ApiOperation(value = "arbol tecnologias", notes = "Devuelve todo el arbol de tecnologias")
	public Response getTecnologias() {
		try {
			TecnologiaController tecnologiaController = TecnologiaController.getInstance();
			out = tecnologiaController.getTecnologias();
			log.info("Get All tecnologias: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

	@GET
	@Path("/{nombre}")
	@ApiOperation(value = "Devuelve una tecnologia por su nombre", notes = "Devuelve una tecnologia por su nombre")
	public Response getReferencia(@PathParam("nombre") String nombre) {
		try {
			TecnologiaController tecnologiaController = TecnologiaController.getInstance();
			out = tecnologiaController.getTecnologia(nombre);
			log.info("Get Referencia by key: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	@GET
	@Path("/finales")
	@ApiOperation(value = "Devuelve todas las tecnologias finales", notes = "listado de tecnologias(hojas)")
	public Response getTecnologiasFinal() {
		try {
			TecnologiaController tecnologiaController = TecnologiaController.getInstance();
			out = tecnologiaController.getTecnologiasFinales();
			log.info("Get Referencia by key: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	@POST
	@ApiOperation(value = "Crea una nueva tecnologia", notes = "Crea una nueva tecnologia")
	public Response postReferencia(Map<String,Object>  recursos) {
		try {
			System.out.println(recursos.get("nodo"));
			System.out.println("-------------------------");
			TecnologiaController tecnologiaController = TecnologiaController.getInstance();
			out = tecnologiaController.createTecnologia(recursos);
			log.info("Insert Referencia: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	
	@DELETE
	@Path("/{nombre}")
	@ApiOperation(value = "Borra una tecnologia", notes = "Borra una Referencia")
	public Response deleteReferencia(@PathParam("nombre") String nombre){
		try{
			TecnologiaController tecnologiaController = TecnologiaController.getInstance();
			out = tecnologiaController.deleteTecnologia(nombre);
			log.info("Delete tecnologia : Operation successful");
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
	@ApiOperation(value = "Modifica una tecnologia", notes = "Modifica una tecnologia")
	public Response updateReferencia(Map<String,Object> recursos){
		
		try{
			TecnologiaController tecnologiaController = TecnologiaController.getInstance();
			out = tecnologiaController.updateTecnologia(recursos);
			log.info("Update Referencia : Operation successful");
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		
		return Response.status(status).entity(out).build();
	}
	
}