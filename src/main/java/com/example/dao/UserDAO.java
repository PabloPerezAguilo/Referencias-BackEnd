package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.User;

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

	public Iterator<User> getUsers() {
		return dao.find().as(User.class).iterator();
	}

	public User getUser(String idUser) {
		return dao.findOne("{'_id':#}", idUser).as(User.class);
	}

	public void insertUser(User user) {
		dao.insert(user);
	}

	public void clearStore() {
		dao.drop();
	}
}