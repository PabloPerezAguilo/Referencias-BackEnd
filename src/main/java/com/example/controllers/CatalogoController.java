//package com.example.controllers;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import com.example.dao.CatalogoClienteDAO;
//import com.example.dao.CatalogoCoDeDAO;
//import com.example.dao.CatalogoGerentesDAO;
//import com.example.dao.ReferenciaDAO;
//import com.example.models.CatalogoCliente;
//import com.example.models.CatalogoGerentes;
//import com.example.models.Referencia;
//
//public class CatalogoController {
//
//	private static CatalogoClienteDAO daoCliente;
//	private static CatalogoCoDeDAO daoCoDe;
//	private static CatalogoGerentesDAO daoGerentes;
//	private static CatalogoController singleton;
//	
//
//	private CatalogoController() throws Exception {
//		daoCliente = CatalogoClienteDAO.getInstance();
//		daoCoDe = CatalogoCoDeDAO.getInstance();
//		daoGerentes = CatalogoGerentesDAO.getInstance();
//	}
//
//	public static CatalogoController getInstance() throws Exception {
//		if (singleton == null) {
//			singleton = new CatalogoController();
//		}
//		return singleton;
//	}
//
//	/**
//	 * getCatalogos.
//	 * Recoge todas las referencias de la base de datos
//	 * @return List<Referencia> 
//	 * @throws Exception
//	 */
//	public List getcatalogo(String entidad) throws Exception {
//		// Transform an iterator object to a list
//		
//		switch(entidad){
//		case "cliente":
//			List<CatalogoCliente> list = new ArrayList<CatalogoCliente>();
//			Iterator<CatalogoCliente> i = daoCliente.getCatalogoCliente();
//			while (i.hasNext()) {
//				list.add(i.next());
//			}
//			break;
//		case "comercial":
//			List<CatalogoGerentes> list = new ArrayList<CatalogoGerentes>();
//			Iterator<CatalogoCliente> i = daoCliente.getCatalogoCliente();
//			while (i.hasNext()) {
//				list.add(i.next());
//			}
//			break;
//		
//		}
//		List<Referencia> list = new ArrayList<Referencia>();
//		Iterator<Referencia> i = dao.getReferencias();
//		while (i.hasNext()) {
//			list.add(i.next());
//		}
//		return list;
//	}
//
//	/**
//	 * getReferencia
//	 * Recoge la referencia de la base de datos indicada por parametro
//	 * @param key | Clave para identificar la referencia en la base de datos
//	 * @return Referencia
//	 * @throws Exception
//	 */
//	public Referencia getReferencia(int key) throws Exception {
//		Referencia resource = dao.getReferencia(key);
//		if (resource == null) {
//			throw new Exception("Resource not found");
//		}
//		return resource;
//	}
//
//	/**
//	 * createReferencia
//	 * Crea una nueva referencia en la base de datos
//	 * @param r | Objeto Referencia que se creara en la base de datos
//	 * @return Referencia
//	 * @throws Exception
//	 */
//	public Referencia createReferencia(Referencia r) throws Exception {
//		dao.insertReferencia(r);
//		return r;
//	}
//	
//	/**
//	 * deleteReferencia
//	 * Borra una referencia de la base de datos
//	 * @param key | Clave para identificar la referencia en la base de datos
//	 * @return key
//	 * @throws Exception
//	 */
//	public int deleteReferencia(int key) throws Exception{
//		dao.deleteReferencia(key);
//		return key;
//	}
//	
//	/**
//	 * updateReferencia
//	 * @param key | Clave para identificar la referencia en la base de datos
//	 * @param r | Objeto Referencia que se modificará en la base de datos
//	 * @return Referencia
//	 * @throws Exception
//	 */
//	public Referencia updateReferencia(int key, Referencia r) throws Exception{
//		dao.updateReferencia(key,r);
//		return r;
//	}
//
//	/**
//	 * dropReferencia
//	 * Borra la coleccion de Referencias.
//	 */
//	public void dropReferencia() {
//		dao.clearStore();
//	}
//}
//}
