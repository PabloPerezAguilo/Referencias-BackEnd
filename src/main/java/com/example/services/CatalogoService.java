package com.example.services;

import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.controllers.CatalogoController;
import com.example.controllers.ReferenciaController;
import com.example.controllers.UsuarioController;
import com.example.models.CatalogoClientes;
import com.example.models.CatalogoGerentes;
import com.example.models.ReferenciaWithAutoID;
import com.example.models.Usuario;
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
	
	
	/**
	 * POST Clientes
	 * @param r
	 * @return Clientes
	 */
	@POST
	@Path("/clientes")
	@ApiOperation(value = "Crea un nuevo cliente", notes = "Crea un nuevo cliente")
	public Response postCliente(CatalogoClientes c) {
		try {
			
			CatalogoController clienteController = CatalogoController.getInstance();			
			clienteController.createCliente(c);
			log.info("Insert Cliente: Operation successful");
			status = Response.Status.ACCEPTED;
			out = c;
			
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * POST Gerentes
	 * @param r
	 * @return Gerentes
	 */
	@POST
	@Path("/gerentes")
	@ApiOperation(value = "Crea un nuevo gerente", notes = "Crea un nuevo gerente")
	public Response postGerente(CatalogoGerentes r) {
		try {
			
			CatalogoController gerenteController = CatalogoController.getInstance();			
			gerenteController.createGerente(r);
			log.info("Insert Gerente: Operation successful");
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
	 * PUT Clientes
	 * @param r
	 * @return Clientes
	 */
	@PUT
	@Path("/clientes")
	@ApiOperation(value = "Modifica un cliente", notes = "Modifica un cliente")
	public Response putCliente(Map<String,Object>  resource) {
		try {
			
			CatalogoController clienteController = CatalogoController.getInstance();	
			out = clienteController.updateCliente(resource);
			log.info("Insert Cliente: Operation successful");
			status = Response.Status.ACCEPTED;
			
			
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * PUT Gerente
	 * @param r
	 * @return r
	 */
	@PUT
	@Path("/gerentes")
	@ApiOperation(value = "Modifica un Gerente", notes = "Modifica un Gerente")
	public Response updateGerente(CatalogoGerentes r){
		
		try{
			CatalogoController gerenteController = CatalogoController.getInstance();
			out = gerenteController.updateGerente(r.getLogin(), r);
			log.info("Update Gerente : Operation successful");
			status = Response.Status.ACCEPTED;
		}catch(Exception e){
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * GET Clientes
	 * @param r
	 * @return Clientes
	 */
	@GET
	@Path("/clientes")
	@ApiOperation(value = "Devuelve todos los clientes", notes = "Devuelve todos los clientes")
	public Response getClientes() {
		try {
			
			CatalogoController clienteController = CatalogoController.getInstance();			
			out = clienteController.getClientes();
			log.info("Insert Cliente: Operation successful");
			status = Response.Status.ACCEPTED;
			
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * GET Gerentes
	 * @return ArrayList<Gerentes>
	 */
	@GET
	@Path("/gerentes")
	@ApiOperation(value = "Devuelve todos los gerentes", notes = "Devuelve todos los gerentes")
	public Response getGerentes() {
		try {
			CatalogoController gerenteController = CatalogoController.getInstance();
			out = gerenteController.getGerentes();
			log.info("Get All Gerentes: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	
	/**
	 * DELETE Clientes
	 * @param r
	 * @return Clientes
	 */
	@DELETE
	@Path("/clientes")
	@ApiOperation(value = "Borra un cliente", notes = "Borra un cliente")
	public Response deleteCliente(String nombre) {
		try {
			
			CatalogoController clienteController = CatalogoController.getInstance();			
			clienteController.deleteCliente(nombre);
			log.info("Delete Cliente: Operation successful");
			status = Response.Status.ACCEPTED;
			out = new Message(nombre);
			
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
	/**
	 * DELETE Gerentes
	 * @param r
	 * @return Gerentes
	 */
	@DELETE
	@Path("/gerentes")
	@ApiOperation(value = "Borra un gerente", notes = "Borra un gerente")
	public Response deleteGerente(String nombre) {
		try {
			
			CatalogoController clienteController = CatalogoController.getInstance();			
			clienteController.deleteGerente(nombre);
			log.info("Delete Gerente: Operation successful");
			status = Response.Status.ACCEPTED;
			out = new Message(nombre);
			
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected: ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}
	
}