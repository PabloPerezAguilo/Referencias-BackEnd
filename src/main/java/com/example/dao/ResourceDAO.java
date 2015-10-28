package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.Resource;

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

	public Iterator<Resource> getResources() throws Exception {
		return dao.find().as(Resource.class).iterator();
	}

	public Resource getResource(int key) {
		return dao.findOne("{'_id':#}", key).as(Resource.class);
	}

	public void insertResource(Resource r) {
		dao.insert(r);
	}

	public void clearStore() {
		dao.drop();
	}
}