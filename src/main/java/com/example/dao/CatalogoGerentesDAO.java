package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.CatalogoGerentes;

public class CatalogoGerentesDAO {

	private static CatalogoGerentesDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "catalogogerentes";

	private CatalogoGerentesDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static CatalogoGerentesDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new CatalogoGerentesDAO();
		}
		return singleton;
	}

	/**
	 * getGerentes
	 * @return Iterator<CatalogoGerentes>
	 * @throws Exception
	 */
	public Iterator<CatalogoGerentes> getGerentes() throws Exception {
		return dao.find(). as(CatalogoGerentes.class).iterator();
	}
	
	/**
	 * getGerentesPorTipo
	 * @param tipoGerente
	 * @return Iterator<CatalogoGerentes>
	 * @throws Exception
	 */
	public Iterator<CatalogoGerentes> getGerentesPorTipo(String tipoGerente) throws Exception {
		return dao.find("{'tipoGerente':#}", tipoGerente).as(CatalogoGerentes.class);
	}

	/**
	 * getGerente
	 * @param nick
	 * @return CatalogoGerente
	 * @throws Exception
	 */
	public CatalogoGerentes getGerente(String nick) throws Exception {
		return dao.findOne("{'_id':#}", nick).as(CatalogoGerentes.class);
	}

	/**
	 * insertGerente
	 * @param gerente
	 * @throws Exception
	 */
	public void insertGerente(CatalogoGerentes gerente) throws Exception {
		dao.insert(gerente);
	}
	
	/**
	 * deleteGerente
	 * @param nick
	 * @throws Exception
	 */
	public void deleteUsuario(String nick) throws Exception {
		dao.remove("{'_id':#}", nick);
	}
	
	/**
	 * updateGerente
	 * @param key
	 * @param r
	 * @throws Exception
	 */
	public void updateGerente(String nick, CatalogoGerentes r) throws Exception {
		dao.update("{'_id':#}", nick).with(r);
	}

	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
}