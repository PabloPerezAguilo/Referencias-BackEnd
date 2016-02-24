package com.example.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.jongo.MongoCollection;

import com.example.models.CatalogoClientes;

public class CatalogoClientesDAO {

	private static CatalogoClientesDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "CatalogoClientes";


	private CatalogoClientesDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static CatalogoClientesDAO getInstance() throws Exception {
	if (singleton == null) {
		singleton = new CatalogoClientesDAO();
	}
	return singleton;
	}

	/**
	 * getCatalogoClientes
	 * @return List<CatalogoCliente>
	 * @throws Exception
	 */
	public List<CatalogoClientes> getCatalogoClientes() throws Exception {
		Iterator<CatalogoClientes> ite = dao.find().as(CatalogoClientes.class).iterator();
		List<CatalogoClientes> listClientes = new ArrayList<CatalogoClientes>();
		while (ite.hasNext()) {
			listClientes.add(ite.next());
		}
		return listClientes;
	}

	/**
	 * getCliente
	 * @param nombre
	 * @return CatalogoCliente
	 * @throws Exception
	 */
	public CatalogoClientes getCliente(String nombre) throws Exception {
		return dao.findOne("{'_id':#}", nombre).as(CatalogoClientes.class);
	}
	
	/**
	 * insertCliente
	 * @param cliente
	 * @throws Exception
	 */
	public void insertCliente(CatalogoClientes cliente) throws Exception {
		dao.insert(cliente);
	}
	
	/**
	 * deleteCliente
	 * @param nombre
	 * @throws Exception
	 */
	public void deleteCliente(String nombre) throws Exception {
		dao.remove("{'_id':#}", nombre);
	}
	
	/**
	 * updateCliente
	 * @param nombre
	 * @param cliente
	 * @throws Exception
	 */
	public void updateCliente(String nombre, CatalogoClientes cliente) throws Exception {
		dao.update("{'_id':#}", nombre).with(cliente);
	}
	
	public List<String> listaContenido(String general ) throws Exception {
		
		Iterator<CatalogoClientes> iteradorClientes = dao.find("{$or: [ { _id: #}, { alias: #}, { siglas: #} ]}", Pattern.compile(".*"+general+".*"),Pattern.compile(".*"+general+".*"),Pattern.compile(".*"+general+".*")).as(CatalogoClientes.class).iterator();
		List<String> listClientes = new ArrayList<String>();
		while (iteradorClientes.hasNext()) {
			//necesita actualizacion de front NOTA
			CatalogoClientes catalogo = iteradorClientes.next();
			listClientes.add(catalogo.getNombre()+" ("+catalogo.getSiglas()+")");
		}
		return listClientes;
		
	}
	
	public void clearStore() {
		dao.drop();
	}
//	public static void main(String[] args) throws Exception {
//		
//		System.out.println("principio");
//		singleton = new CatalogoClientesDAO();
//		Iterator<String> aux = singleton.listaContenido("A").iterator();
//		String recorrido = null;
//		while(aux.hasNext()){
//			
//			recorrido =  aux.next();
//			System.out.println(recorrido);
//			
//		}
//	}
}
