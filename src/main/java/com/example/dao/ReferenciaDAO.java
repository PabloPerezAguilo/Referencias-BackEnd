package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.Referencia;

public class ReferenciaDAO {

	private static ReferenciaDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "referencias";

	private ReferenciaDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static ReferenciaDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ReferenciaDAO();
		}
		return singleton;
	}

	/**
	 * getReferencias
	 * @return Iterator<Referencia>
	 * @throws Exception
	 */
	public Iterator<Referencia> getReferencias() throws Exception {
		return dao.find().as(Referencia.class).iterator();
	}

	/**
	 * getReferencia
	 * @param key
	 * @return Referencia
	 * @throws Exception
	 */
	public Referencia getReferencia(int key) throws Exception{
		return dao.findOne("{'_id':#}", key).as(Referencia.class);
	}

	/**
	 * insertReferencia
	 * @param r
	 * @throws Exception
	 */
	public void insertReferencia(Referencia r) throws Exception{
		dao.insert(r);
	}
	
	/**
	 * deleteReferencia
	 * @param key
	 * @throws Exception
	 */
	public void deleteReferencia(int key) throws Exception{
		dao.remove("{_id:"+key+"}");
	}
	
	/**
	 * updateReferencia
	 * @param key
	 * @param r
	 * @throws Exception
	 */
	public void updateReferencia(int key, Referencia r) throws Exception{
		dao.update("{_id:"+key+"}").with(r);
	}

	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
}