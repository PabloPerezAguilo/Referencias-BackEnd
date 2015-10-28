package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.Referencia;

public class ResourceDAO {

	private static ResourceDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "resources";

	private ResourceDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static ResourceDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ResourceDAO();
		}
		return singleton;
	}

	public Iterator<Referencia> getResources() throws Exception {
		return dao.find().as(Referencia.class).iterator();
	}

	public Referencia getResource(int key) {
		return dao.findOne("{'_id':#}", key).as(Referencia.class);
	}

	public void insertResource(Referencia r) {
		dao.insert(r);
	}

	public void clearStore() {
		dao.drop();
	}
}