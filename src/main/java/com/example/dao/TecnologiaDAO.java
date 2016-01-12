package com.example.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import com.example.models.Hoja;
import com.example.models.ReferenciaWithAutoID;
import com.example.models.Tecnologia;
import com.example.models.Usuario;

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
		
		return dao.findOne("{'_id':#}", "Tecnologias").as(Tecnologia.class);
	}

	/**
	 * getUsuario
	 * @param idUser
	 * @return Usuario
	 * @throws Exception
	 */
	public Tecnologia getTecnologia(String nombre) throws Exception {
		return dao.findOne("{'_id':#}", nombre).as(Tecnologia.class);
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
		dao.remove("{'_id':#}", nombre);
	}
	
	/**
	 * updateUsuario
	 * @param key
	 * @param r
	 * @throws Exception
	 */
	public void updateTecnologia(String nombre, Tecnologia tec) throws Exception {
		dao.update("{'_id':#}", nombre).with(tec);
	}

	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
}