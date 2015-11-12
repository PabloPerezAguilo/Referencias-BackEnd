package com.example.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.controllers.CatalogoController;
import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/catalogo/")
@Api(value = "/catalogo", description = "conexion a catalogos")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogoService extends Service{

	private static final Logger log = Logger.getLogger(CatalogoService.class.getName());
	
	/**
	 * GET Catalogos
	 * @return ArrayList<UsuarioLdap>
	 */
	@GET
	@ApiOperation(value = "Devuelve todos los catalogos mapeados", notes = "Devuelve todos los catalogos mapeados")
	public Response getCatalogos() {
		try {
			CatalogoController catalogoController = CatalogoController.getInstance();
			out = catalogoController.getCatalogos();
			log.info("Get All Catalogos: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	} 
}