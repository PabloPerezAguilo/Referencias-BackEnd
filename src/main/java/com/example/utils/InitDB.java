package com.example.utils;

import org.apache.log4j.Logger;

import com.example.controllers.UserController;
import com.example.dao.ResourceDAO;
import com.example.dao.UserDAO;
import com.example.models.Resource;

public class InitDB {

	private static final Logger LOG = Logger.getLogger(InitDB.class.getName());

	public static void loadResources() throws Exception {
		ResourceDAO resourceDAO = ResourceDAO.getInstance();
		resourceDAO.clearStore();
		resourceDAO.insertResource(new Resource(0, "Mongo Value0"));
		resourceDAO.insertResource(new Resource(1, "Mongo Value1"));
		resourceDAO.insertResource(new Resource(2, "Mongo Value2"));
		LOG.info("Resources inserted in DB");
	}

	public static void loadUsers() throws Exception {
		UserDAO userDAO = UserDAO.getInstance();
		userDAO.clearStore();
		UserController controller = UserController.getInstance();
		controller.createUser("a", "ROLE_USER", "a");
		controller.createUser("test", "ROLE_ADMIN", "test");
		LOG.info("Users inserted in DB");
	}

	public static void main(String[] args) throws Exception {
		InitDB.loadUsers();
	}
}