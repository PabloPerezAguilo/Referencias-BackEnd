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
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.controllers.PlantillaController;
import com.example.models.Plantilla;
import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/plantillas/")
@Api(value = "/plantilla", description = "Plantilla operations")
@Produces(MediaType.APPLICATION_JSON)
public class PlantillaService extends Service{

	private static final Logger log = Logger.getLogger(PlantillaService.class.getName());
	public PlantillaService() {
		super();
	}
	
	@GET
	@Path("/publicas")
	@ApiOperation(value = "Devuelve todas las Plantillas publicas", notes = "Devuelve todas las Plantillas publicas")
	public Response getPlantillas() {
		try {
			PlantillaController plantillaController = PlantillaController.getInstance();
			out = plantillaController.getPlantillasPublicas();
			log.info("Get All Plantillas Publicas: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	@GET
	@ApiOperation(value = "Devuelve todas las Plantillas", notes = "Devuelve todas las Plantillas")
	public Response getPlantillasPublicas() {
		try {
			PlantillaController plantillaController = PlantillaController.getInstance();
			out = plantillaController.getPlantillas();
			log.info("Get All Plantillas : Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	@GET
	@Path("/{key}")
	@ApiOperation(value = "Devuelve una Plantilla mediante parametro", notes = "Devuelve una Plantilla mediante parametro")
	public Response getReferencia(@PathParam("key") String key) {
		try {
			PlantillaController plantillaController = PlantillaController.getInstance();
			out = plantillaController.getPlantilla(key);
			log.info("Get Plantillas  by key: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	@POST
	@ApiOperation(value = "Crea una nueva Plantilla", notes = "Crea una nueva Plantilla")
	public Response postReferencia(Plantilla p) {
		try {
			String user = SecurityContextHolder.getContext().getAuthentication().getName();
			p.setCreador(user);
			PlantillaController plantillaController = PlantillaController.getInstance();
			out = plantillaController.createPlantilla(p);
			log.info("Insert Plantillas : Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		
		return Response.status(status).entity(out).build();
	}
	
	
	@DELETE
	@Path("/{key}")
	@ApiOperation(value = "Borra una Plantilla", notes = "Borra una Plantilla")
	public Response deleteReferencia(@PathParam("key") String key){
		try{
			PlantillaController plantillaController = PlantillaController.getInstance();
			plantillaController.deletePlantilla(key);
			out = new Message("Borrado correcto");
			log.info("Delete Plantillas  : Operation successful");
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
	@ApiOperation(value = "Modifica una Plantilla", notes = "Modifica una Plantilla")
	public Response updateReferencia(Map<String,Object>  recursos){
		
		try{
			PlantillaController plantillaController = PlantillaController.getInstance();
			out = plantillaController.updatePlantilla(recursos);
			log.info("Update Plantillas : Operation successful");
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		
		return Response.status(status).entity(out).build();
	}
}