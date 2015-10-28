package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.Usuario;

public class UserDAO {

	private static UserDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "users";

	private UserDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static UserDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new UserDAO();
		}
		return singleton;
	}

	public Iterator<Usuario> getUsers() {
		return dao.find(). as(Usuario.class).iterator();
	}

	public Usuario getUser(String idUser) {
		return dao.findOne("{'_id':#}", idUser).as(Usuario.class);
	}

	public void insertUser(Usuario user) {
		dao.insert(user);
	}

	public void clearStore() {
		dao.drop();
	}
}