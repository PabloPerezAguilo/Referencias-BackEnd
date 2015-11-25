package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;

import com.example.dao.ImagenDAO;
import com.example.dao.ReferenciaDAO;
import com.example.models.ReferenciaWithAutoID;

public class ReferenciaController {

	private static ReferenciaDAO dao;
	private static ReferenciaController singleton;

	private ReferenciaController() throws Exception {
		dao = ReferenciaDAO.getInstance();
	}

	public static ReferenciaController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ReferenciaController();
		}
		return singleton;
	}

	/**
	 * getReferencias.
	 * Recoge todas las referencias de la base de datos
	 * @return List<ReferenciaWithAutoID> 
	 * @throws Exception
	 */
	public List<ReferenciaWithAutoID> getReferencias() throws Exception {
		// Transform an iterator object to a list
		// no devolvemos las imagenes para no sobrecargar
		List<ReferenciaWithAutoID> list = new ArrayList<ReferenciaWithAutoID>();
		Iterator<ReferenciaWithAutoID> i = dao.getReferencias();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}
	
	/**
	 * getReferenciasPendientes.
	 * Recoge todas las referencias de la base de datos
	 * @return List<ReferenciaWithAutoID> 
	 * @throws Exception
	 */
	public List<ReferenciaWithAutoID> getReferenciasPendientes() throws Exception {
		// Transform an iterator object to a list
		// no devolvemos las imagenes para no sobrecargar
		List<ReferenciaWithAutoID> list = new ArrayList<ReferenciaWithAutoID>();
		Iterator<ReferenciaWithAutoID> i = dao.getReferenciasPendientes();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}

	/**
	 * getReferencia
	 * Recoge la referencia de la base de datos indicada por parametro
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @return ReferenciaWithAutoID
	 * @throws Exception
	 */
	public ReferenciaWithAutoID getReferencia(int key) throws Exception {
		
		ReferenciaWithAutoID resource = null;
		try{
			resource = dao.getReferencia(key);
			if (resource == null) {
			throw new Exception("Imagen no disponible");
			}
			byte[] imagenByte = Files.readAllBytes(Paths.get("./imagenes/"+resource.get_id()+".png"));
			String imagenBase = Base64.encodeBase64(imagenByte).toString();
			resource.setImagenProyecto(imagenBase);
		}
		catch (IOException eImagen) {
			throw new Exception("Fallo al recoger la imagen del disco:"+eImagen.toString());
		}
		catch (Exception bdd) {
			throw new Exception("Fallo al recoger la referencia de la BDD:"+bdd.toString());
		}
		
		return resource;
	}

	/**
	 * createReferencia
	 * Crea una nueva referencia en la base de datos
	 * @param r | Objeto Referencia que se creara en la base de datos
	 * @return ReferenciaWithAutoID
	 * @throws Exception
	 */
	public ReferenciaWithAutoID createReferencia(ReferenciaWithAutoID r) throws Exception{
		
		//al crear la referencia borramos el campo imagen ya que la guardamos en disco
		String imagen = r.getImagenProyecto();
		r.setImagenProyecto("");
		
		// antes de crear la imagen es necesario insertar la referencia para conocer su id
		try {
			
		dao.insertReferencia(r);	
		byte[] imagenByte = DatatypeConverter.parseBase64Binary(imagen);
		//guardamos en disco la imagen usando como nombre el id de su referencia
		File archivo = new File("./imagenes/"+r.get_id()+".png");
		FileUtils.writeByteArrayToFile(archivo,imagenByte);
		
		}
		catch (IOException eImagen) {
			dao.deleteReferencia(r.get_id());
			throw new Exception("Fallo al guardar la imagen en disco:"+eImagen.toString());
		}
		catch (Exception bdd) {
			throw new Exception("Fallo al guardar la referencia en la BDD:"+bdd.toString());
		}

		return r;
	}
	
	/**
	 * deleteReferencia
	 * Borra una referencia de la base de datos
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @return key
	 * @throws Exception
	 */
	public ObjectId deleteReferencia(ObjectId key) throws Exception{
		try{
		File archivo = new File("./imagenes/"+key+".png");
		if(!archivo.delete()){
			throw new Exception("Fallo al borrar: delete no ha podido completarse");
		}
		dao.deleteReferencia(key);
		}
		catch (IOException eImagen) {
			throw new Exception("Fallo al borrar la imagen en disco:"+eImagen.toString());
		}
		catch (Exception bdd) {
			throw new Exception("Fallo al borrar la referencia en la BDD:"+bdd.toString());
		}
		return key;
	}
	
	/**
	 * updateReferencia
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @param r | Objeto Referencia que se modificará en la base de datos
	 * @return Referencia
	 * @throws Exception
	 */
	public ReferenciaWithAutoID updateReferencia(ObjectId key, ReferenciaWithAutoID r) throws Exception{
		
		//al actualizar la referencia borramos el campo imagen ya que la guardamos en disco
		String imagen = r.getImagenProyecto();
		r.setImagenProyecto("");
				
		// hacer la comprobacion de la imagen nos supone mas coste que sobreescribirla, aunque sea la misma, por tanto lo hacemos.
		try {
					
			dao.updateReferencia(key,r);	
			byte[] imagenByte = DatatypeConverter.parseBase64Binary(imagen);
			//guardamos en disco la imagen usando como nombre el id de su referencia
			File archivo = new File("./imagenes/"+key+".png");
			if(!archivo.delete()){
				throw new Exception("Fallo al actualizar: la imagen no ha podido cargarse en el servidor");
			}
			FileUtils.writeByteArrayToFile(archivo,imagenByte);
				
		}
		catch (IOException eImagen) {
			throw new Exception("Fallo de escritura, Por favor contacte con un administrador de su servidor de aplicaciones");
		}
		catch (Exception bdd) {
			throw new Exception("Fallo al actualizar la referencia en la BDD:"+bdd.toString());
		}

		return r;		
	}
	
	/**
	 * updateReferenciaEstado
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @param estado | estado al que se actualiza la referencia
	 * @return Referencia
	 * @throws Exception
	 */
	public ReferenciaWithAutoID updateReferenciaEstado(int key,String estado) throws Exception{
		
		ReferenciaWithAutoID resource = dao.getReferencia(key);
		resource.setEstado(estado);
		dao.updateReferencia(resource.get_id(),resource);
		return resource;
	}

	/**
	 * dropReferencia
	 * Borra la coleccion de Referencias.
	 */
	public void dropReferencia() {
		dao.clearStore();
	}
}