package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.CatalogoCoDe;

public class CatalogoCoDeDAO {

	private static CatalogoCoDeDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "CatalogoCoDe";

	private CatalogoCoDeDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static CatalogoCoDeDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new CatalogoCoDeDAO();
		}
		return singleton;
	}

	/**
	 * getCatalogoCoDe
	 * @return Iterator<CatalogoCoDe>
	 * @throws Exception
	 */
	public Iterator<CatalogoCoDe> getCatalogoCoDe() throws Exception {
		return dao.find(). as(CatalogoCoDe.class).iterator();
	}
	
	/**
	 * getCatalogoCoDePorTipo
	 * @param entidad
	 * @return Iterator<CatalogoCoDe>
	 * @throws Exception
	 */
	public Iterator<CatalogoCoDe> getGerentesPorTipo(String entidad) throws Exception {
		return dao.find("{'tipoGerente':#}", entidad).as(CatalogoCoDe.class);
	}

	/**
	 * getCatalogoCoDe
	 * @param nick
	 * @return CatalogoCoDe
	 * @throws Exception
	 */
	public CatalogoCoDe getGerente(String codigo) throws Exception {
		return dao.findOne("{'_id':#}", codigo).as(CatalogoCoDe.class);
	}

	/**
	 * insertCatalogoCoDe
	 * @param CatalogoCoDe
	 * @throws Exception
	 */
	public void insertGerente(CatalogoCoDe code) throws Exception {
		dao.insert(code);
	}
	
	/**
	 * deleteCatalogoCoDe
	 * @param codigo
	 * @throws Exception
	 */
	public void deleteUsuario(String codigo) throws Exception {
		dao.remove("{'_id':#}", codigo);
	}
	
	/**
	 * updateGerente
	 * @param codigo
	 * @param code
	 * @throws Exception
	 */
	public void updateGerente(String codigo, CatalogoCoDe code) throws Exception {
		dao.update("{'_id':#}", codigo).with(codigo);
	}

	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
}


