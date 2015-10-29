package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.Referencia;

public class ReferenciaDAO {

	private static ReferenciaDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "resources";

	private ReferenciaDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static ReferenciaDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ReferenciaDAO();
		}
		return singleton;
	}

	public Iterator<Referencia> getReferencias() throws Exception {
		return dao.find().as(Referencia.class).iterator();
	}

	public Referencia getReferencia(int key) {
		return dao.findOne("{'_id':#}", key).as(Referencia.class);
	}

	public void insertReferencia(Referencia r) {
		dao.insert(r);
	}
	
	public void deleteReferencia(int key){
		dao.remove("{_id:"+key+"}");
	}
	
	public void updateReferencia(int key, Referencia r){
		dao.update("{_id:"+key+"}").with(r);
	}

	public void clearStore() {
		dao.drop();
	}
}