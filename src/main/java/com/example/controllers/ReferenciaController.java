package com.example.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.dao.ReferenciaDAO;
import com.example.models.Referencia;

public class ReferenciaController {

	// Singleton instances
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
	 * Get Referencias
	 * @return
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
	 * Get Referencia by Key
	 * @param key
	 * @return
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
	 * Create Referencia
	 * @param r
	 * @return
	 * @throws Exception
	 */
	public Referencia createReferencia(Referencia r) throws Exception {
		dao.insertReferencia(r);
		return r;
	}
	
	/**
	 * Delete Referencia
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int deleteReferencia(int key) throws Exception{
		dao.deleteReferencia(key);
		return key;
	}
	
	/**
	 * Update Referencia
	 * @param key
	 * @param r
	 * @return
	 */
	public Referencia updateReferencia(int key, Referencia r){
		dao.updateReferencia(key,r);
		return r;
	}

	/**
	 * Borrar Referencias
	 */
	public void dropReferencia() {
		dao.clearStore();
	}
}