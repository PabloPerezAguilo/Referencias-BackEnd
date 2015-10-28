package com.example.dao;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.example.utils.Config;
import com.github.fakemongo.Fongo;
import com.mongodb.DB;

public class DataBase {

	private static DataBase singleton;
	private static DB db;

	private DataBase() throws Exception {
		// If Mock mode is enable instance a mock DAO, else instance a Mongo DAO
		if (Config.getInstance().getProperty(Config.MOCK).equalsIgnoreCase("false")) {
			db = getBDMongo();
		} else {
			db = getBDMock();
		}
	}

	public static DataBase getInstance() throws Exception {
		if (singleton == null) {
			singleton = new DataBase();
		}
		return singleton;
	}

	/**
	 * Return a specific collection using Jongo framework
	 */
	public MongoCollection getCollection(String collection) {
		Jongo jongo = new Jongo(db);
		return jongo.getCollection(collection);
	}

	/**
	 * Return a real Mongo Database
	 */
	private static DB getBDMongo() throws Exception {
		MongoDB db = new MongoDB();
		return db.getDatabase();
	}

	/**
	 * Return a mock Mongo Database using Fongo framework
	 */
	private static DB getBDMock() {
		DB db = new Fongo("Test").getDB("Database");
		return db;
	}
}
