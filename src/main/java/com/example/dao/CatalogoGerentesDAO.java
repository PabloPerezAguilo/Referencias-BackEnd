package com.example.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.jongo.MongoCollection;

import com.example.models.CatalogoGerentes;

public class CatalogoGerentesDAO {

	private static CatalogoGerentesDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "CatalogoGerentes";

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
	 * @return List<CatalogoGerentes>
	 * @throws Exception
	 */
	public List<CatalogoGerentes> getGerentes() throws Exception {
		Iterator<CatalogoGerentes> ite = dao.find().as(CatalogoGerentes.class).iterator();
		List<CatalogoGerentes> listGerentes = new ArrayList<CatalogoGerentes>();
		while (ite.hasNext()) {
			listGerentes.add(ite.next());
		}
		return listGerentes;
	}
	
	/**
	 * getGerentesPorTipo
	 * @param tipoGerente
	 * @return List<CatalogoGerentes>
	 * @throws Exception
	 */
	public List<CatalogoGerentes> getGerentesPorTipo(String tipoGerente) throws Exception {
		Iterator<CatalogoGerentes> ite = dao.find("{'tipoGerente':#}", tipoGerente).as(CatalogoGerentes.class).iterator();
		List<CatalogoGerentes> listGerentes = new ArrayList<CatalogoGerentes>();
		while (ite.hasNext()) {
			listGerentes.add(ite.next());
		}
		return listGerentes;
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
	
	public List<String> listaContenido(String general ) throws Exception {
		
		Iterator<CatalogoGerentes> iteradorGerentes = dao.find("{$or: [ { _id: #}, { nombre: #}, { apellidos: #}, { tipoGerente: #}  ]}", Pattern.compile(".*"+general+".*"),Pattern.compile(".*"+general+".*"),Pattern.compile(".*"+general+".*"),Pattern.compile(".*"+general+".*")).as(CatalogoGerentes.class).iterator();
		List<String> listGerentes = new ArrayList<String>();
		while (iteradorGerentes.hasNext()) {
			
			listGerentes.add(iteradorGerentes.next().getLogin());
		}
		return listGerentes;
		
	}
	public void clearStore() {
		dao.drop();
	}
//	public static void main(String[] args) throws Exception {
//		
//		System.out.println("principio");
//		singleton = new CatalogoGerentesDAO();
//		Iterator<String> aux = singleton.listaContenido("J").iterator();
//		String recorrido = null;
//		while(aux.hasNext()){
//			
//			recorrido =  aux.next();
//			System.out.println(recorrido);
//			
//		}
//		
//	}
}