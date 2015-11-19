package com.example.services;

import java.io.File;

import org.apache.commons.io.FileUtils;

import javax.xml.bind.DatatypeConverter;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.dao.ImagenDAO;
import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/imagen/")
@Api(value = "/imagen", description = "imagen pruebas")
@Produces(MediaType.APPLICATION_JSON)

public class PruebaImagen extends Service{

	private static ImagenDAO daoImagen;
	private static final Logger log = Logger.getLogger(ReferenciaService.class.getName());

	public PruebaImagen() {
		super();
	}
	
	@POST
	@ApiOperation(value = "Recibe una imagen", notes = "imagen d eprueba")
	public Response postPruebaImagen(String r) {
		try {
			
			daoImagen = ImagenDAO.getInstance();
			byte[] imagenByte = DatatypeConverter.parseBase64Binary(r);
			File archivo = new File("imagenes/prueba1234.png");
			FileUtils.writeByteArrayToFile(archivo,imagenByte);
			String aux = daoImagen.insertImagen(archivo);
//			byte[] imagenByte= DatatypeConverter.parseBase64Binary(r);
//			File archivo = new File("imagenes/temporal.png");
//			//String path = archivo.getCanonicalPath();
			log.info(aux);
			if (archivo.delete()){
				log.info("El fichero ha sido borrado satisfactoriamente");
				}
			else{
				 throw new Exception("El fichero no puede ser borrado");
				}
			out = r;
			log.info("Insert imagen: Operation successful");
		} catch (Exception e) {
			status = Response.Status.BAD_REQUEST;
			log.error("Error detected(fichero): ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	}

}
