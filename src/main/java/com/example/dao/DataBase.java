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
	 * getCollection
	 * Devuelve una coleccion especifica usando Jongo Framework
	 * @param collection
	 * @return MongoCollection
	 */
	public MongoCollection getCollection(String collection) {
		Jongo jongo = new Jongo(db);
		return jongo.getCollection(collection);
	}

	/**
	 * getDBMongo
	 * Devuelve una base de datos real de Mongo
	 * @return DB
	 * @throws Exception
	 */
	private static DB getBDMongo() throws Exception {
		MongoDB db = new MongoDB();
		return db.getDatabase();
	}

	/**
	 * getDBMock
	 * Devuelve una base de datos mock de Mongo
	 * @return DB
	 */
	private static DB getBDMock() {
		DB db = new Fongo("Test").getDB("Database");
		return db;
	}
	
	public static DB getbd(){
		return db;
	}
}
