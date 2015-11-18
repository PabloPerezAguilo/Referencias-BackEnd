package com.example.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/imagen/")
@Api(value = "/imagen", description = "imagen pruebas")
@Produces(MediaType.APPLICATION_JSON)

public class PruebaImagen extends Service{

	private static final Logger log = Logger.getLogger(ReferenciaService.class.getName());

	public PruebaImagen() {
		super();
	}
	
	@POST
	@ApiOperation(value = "Recibe una imagen", notes = "imagen d eprueba")
	public Response postPruebaImagen(String r) {
		try {
			File file = new File("theimage.png");
			BufferedWriter imagen;
			imagen = new BufferedWriter(new FileWriter(file));
	        imagen.write(r);
	        imagen.close();
			out = "ok";
			log.info("Insert imagen: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected(fichero): ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

}
