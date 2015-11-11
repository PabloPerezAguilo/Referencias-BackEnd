package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.CatalogoCliente;

public class CatalogoClienteDAO {

	private static CatalogoClienteDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "CatalogoClientes";


	private CatalogoClienteDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static CatalogoClienteDAO getInstance() throws Exception {
	if (singleton == null) {
		singleton = new CatalogoClienteDAO();
	}
	return singleton;
	}

	/**
	 * getUsuarios
	 * @return Iterator<CatalogoCliente>
	 * @throws Exception
	 */
	public Iterator<CatalogoCliente> getCatalogoCliente() throws Exception {
		return dao.find(). as(CatalogoCliente.class).iterator();
	}

	/**
	 * getUsuario
	 * @param nombre
	 * @return CatalogoCliente
	 * @throws Exception
	 */
	public CatalogoCliente getCliente(String nombre) throws Exception {
		return dao.findOne("{'_id':#}", nombre).as(CatalogoCliente.class);
	}
	
	/**
	 * insertCatalogoCliente
	 * @param cliente
	 * @throws Exception
	 */
	public void insertUsuario(CatalogoCliente cliente) throws Exception {
		dao.insert(cliente);
	}
	
	/**
	 * deleteCatalogoCliente
	 * @param nombre
	 * @throws Exception
	 */
	public void deleteUsuario(String nombre) throws Exception {
		dao.remove("{'_id':#}", nombre);
	}
	
	/**
	 * updateCatalogoCliente
	 * @param nombre
	 * @param cliente
	 * @throws Exception
	 */
	public void updateUsuario(String nombre, CatalogoCliente cliente) throws Exception {
		dao.update("{'_id':#}", nombre).with(cliente);
	}
	public void clearStore() {
		dao.drop();
	}

}
