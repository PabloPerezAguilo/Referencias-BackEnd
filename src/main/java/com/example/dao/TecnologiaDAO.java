package com.example.dao;



import org.jongo.MongoCollection;

import com.example.models.Tecnologia;


public class TecnologiaDAO {

	private static TecnologiaDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "Tecnologias";

	private TecnologiaDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static TecnologiaDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new TecnologiaDAO();
		}
		return singleton;
	}

	/**
	 * getUsuarios
	 * @return Iterator<Usuario>
	 * @throws Exception
	 */
	public Tecnologia getTecnologias() throws Exception {
		
		return dao.findOne("{'nombre':#}", "Tecnologias").as(Tecnologia.class);
	}

	/**
	 * getUsuario
	 * @param idUser
	 * @return Usuario
	 * @throws Exception
	 */
	public Tecnologia getTecnologia(String nombre) throws Exception {
		return dao.findOne("{'nombre':#}", nombre).as(Tecnologia.class);
	}
	
	/**
	 * getUsuarioLogin
	 * @param idUser
	 * @return Usuario
	 * @throws Exception
	 */
	public void insertTecnologia(Tecnologia tecnologia) throws Exception {
		dao.insert(tecnologia);
	}
	
	/**
	 * deleteUsuario
	 * @param key
	 * @throws Exception
	 */
	public void deleteTecnologia(String nombre) throws Exception {
		dao.remove("{'nombre':#}", nombre);
	}
	
	/**
	 * updateUsuario
	 * @param key
	 * @param r
	 * @throws Exception
	 */
	public void updateTecnologia(String nombre, Tecnologia tec) throws Exception {
		dao.update("{'nombre':#}", nombre).with(tec);
	}

	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
}