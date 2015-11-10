package com.example.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.dao.ReferenciaDAO;
import com.example.models.Referencia;

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
	 * @return List<Referencia> 
	 * @throws Exception
	 */
	public List<Referencia> getReferencias() throws Exception {
		// Transform an iterator object to a list
		List<Referencia> list = new ArrayList<Referencia>();
		Iterator<Referencia> i = dao.getReferencias();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}

	/**
	 * getReferencia
	 * Recoge la referencia de la base de datos indicada por parametro
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @return Referencia
	 * @throws Exception
	 */
	public Referencia getReferencia(int key) throws Exception {
		Referencia resource = dao.getReferencia(key);
		if (resource == null) {
			throw new Exception("Resource not found");
		}
		return resource;
	}

	/**
	 * createReferencia
	 * Crea una nueva referencia en la base de datos
	 * @param r | Objeto Referencia que se creara en la base de datos
	 * @return Referencia
	 * @throws Exception
	 */
	public Referencia createReferencia(Referencia r) throws Exception {
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
	public Referencia updateReferencia(int key, Referencia r) throws Exception{
		dao.updateReferencia(key,r);
		return r;
	}

	/**
	 * dropReferencia
	 * Borra la coleccion de Referencias.
	 */
	public void dropReferencia() {
		dao.clearStore();
	}
}