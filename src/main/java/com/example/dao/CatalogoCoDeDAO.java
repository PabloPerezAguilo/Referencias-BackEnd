package com.example.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

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
	 * @return List<CatalogoCoDe>
	 * @throws Exception
	 */
	public List<CatalogoCoDe> getCatalogoCoDe() throws Exception {
		Iterator<CatalogoCoDe> ite = dao.find().as(CatalogoCoDe.class).iterator();
		List<CatalogoCoDe> listCoDe = new ArrayList<CatalogoCoDe>();
		while (ite.hasNext()) {
			listCoDe.add(ite.next());
		}
		return listCoDe;
	}
	
	/**
	 * getCatalogoCoDePorTipo
	 * @param entidad
	 * @return List<CatalogoCoDe>
	 * @throws Exception
	 */
	public List<CatalogoCoDe> getCoDePorTipo(String entidad) throws Exception {
		Iterator<CatalogoCoDe> ite = dao.find("{'entidad':#}", entidad).as(CatalogoCoDe.class).iterator();
		List<CatalogoCoDe> listCoDe = new ArrayList<CatalogoCoDe>();
		while (ite.hasNext()) {
			listCoDe.add(ite.next());
		}
		return listCoDe;
	}

	/**
	 * getCatalogoCoDe
	 * @param nick
	 * @return CatalogoCoDe
	 * @throws Exception
	 */
	public CatalogoCoDe getCoDe(String codigo) throws Exception {
		return dao.findOne("{'_id':#}", codigo).as(CatalogoCoDe.class);
	}

	/**
	 * insertCatalogoCoDe
	 * @param CatalogoCoDe
	 * @throws Exception
	 */
	public void insertCoDe(CatalogoCoDe code) throws Exception {
		dao.insert(code);
	}
	
	/**
	 * deleteCatalogoCoDe
	 * @param codigo
	 * @throws Exception
	 */
	public void deleteCoDe(String codigo) throws Exception {
		dao.remove("{'_id':#}", codigo);
	}
	
	/**
	 * updateGerente
	 * @param codigo
	 * @param code
	 * @throws Exception
	 */
	public void updateCoDe(String codigo, CatalogoCoDe code) throws Exception {
		dao.update("{'_id':#}", codigo).with(codigo);
	}
	
	public List<String> listaContenido(String general ) throws Exception {
		
		Iterator<CatalogoCoDe> iteradorCoDe = dao.find("{$or: [ { _id: #}, { descripcion: #}]}", Pattern.compile(".*"+general+".*"),Pattern.compile(".*"+general+".*")).as(CatalogoCoDe.class).iterator();
		List<String> listCoDe = new ArrayList<String>();
		while (iteradorCoDe.hasNext()) {
			
			listCoDe.add(iteradorCoDe.next().getCodigo());
		}
		return listCoDe;
		
		
	}
	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
//	public static void main(String[] args) throws Exception {
//		
//		singleton = new CatalogoCoDeDAO();
//		Iterator<String> aux = singleton.listaContenido("").iterator();
//		String recorrido = null;
//		while(aux.hasNext()){
//			
//			recorrido =  aux.next();
//			
//		}
//		
//	}
}


