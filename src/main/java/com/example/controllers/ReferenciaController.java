package com.example.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.bson.types.ObjectId;

import com.example.dao.ImagenDAO;
import com.example.dao.ReferenciaDAO;
import com.example.models.ReferenciaWithAutoID;

public class ReferenciaController {

	private static ReferenciaDAO dao;
	private static ImagenDAO daoImagen;
	private static ReferenciaController singleton;

	private ReferenciaController() throws Exception {
		dao = ReferenciaDAO.getInstance();
		daoImagen = ImagenDAO.getInstance();
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
		ReferenciaWithAutoID resource = dao.getReferencia(key);
		if (resource == null) {
			throw new Exception("Resource not found");
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
	public ReferenciaWithAutoID createReferencia(ReferenciaWithAutoID r) throws Exception {
		/*Creamos fichero con el nombre de la referencia y se guarda en la DB. A continuacion pasamos el id
		 * de la imagen al campo imagen de referencia*/
		
		byte[] imagenByte= DatatypeConverter.parseBase64Binary(r.getImagenProyecto());
		String aleatorio = Double.toString(Math.random()*10000);
		File archivo = new File("imagenes/"+aleatorio+".png");
		daoImagen.guardarImagen(archivo);
		dao.insertReferencia(r);
		return r;
	}
	
	/**
	 * deleteReferencia
	 * Borra una referencia de la base de datos
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @return key
	 * @throws Exception
	 */
	public int deleteReferencia(int key) throws Exception{
		dao.deleteReferencia(key);
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
		dao.updateReferencia(key,r);
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