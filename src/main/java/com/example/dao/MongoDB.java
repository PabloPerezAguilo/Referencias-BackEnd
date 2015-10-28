package com.example.dao;

import com.example.utils.Config;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDB {

	private static MongoClient mongoClient;
	private static DB database;

	public MongoDB() throws Exception {

		// Get all data from .properties
		mongoClient = new MongoClient(Config.getInstance().getProperty(Config.IP_MONGO), Integer.valueOf(Config.getInstance().getProperty(Config.PUERTO_MONGO)));
		database = mongoClient.getDB(Config.getInstance().getProperty(Config.BD_MONGO));

		boolean authenticated = database.authenticate(Config.getInstance().getProperty(Config.MONGO_USER), new String(Config.getInstance().getProperty(Config.MONGO_PASS)).toCharArray());
		if (!authenticated) {
			throw new Exception("Mongo User/password wrong");
		}
	}

	/**
	 * Get a client of database
	 */
	public DB getDatabase() {
		return database;
	}
}