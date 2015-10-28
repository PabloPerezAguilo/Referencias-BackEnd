package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.controllers.UserController;
import com.example.dao.UserDAO;
import com.example.models.User;
import com.example.utils.InitDB;

public class UserControllerTest {

	private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
	private static UserController controller;
	private static UserDAO dao;

	@BeforeClass
	public static void init() throws Exception {
		log.info("Start test");
		controller = UserController.getInstance();
		dao = UserDAO.getInstance();
	}

	@AfterClass
	public static void end() throws Exception {
		dao.clearStore();
		InitDB.loadUsers();
		log.info("Storage restored");
		log.info("End test");
	}

	@Before
	public void initTest() throws Exception {
		dao.clearStore();
		log.info("Storage cleaned");
	}

	@Test
	public void emptyUsers() {
		log.info("Start 'Empty Users test'");
		try {
			List<User> list = controller.getUsers();
			assertTrue(list.isEmpty());
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}

	@Test(expected = Exception.class)
	public void noCreatedUserLogin() throws Exception {
		log.info("Start 'No created user login test'");
		controller.loginUser("userTest", "passwordTest");
	}

	@Test
	public void createUser() {
		try {
			log.info("Start 'Create User test'");
			controller.createUser("userTest", "roleTest", "passwordTest");
			List<User> list = controller.getUsers();
			User user = list.get(0);
			assertNotNull(user);
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}

	@Test(expected = Exception.class)
	public void loginWrongPassword() throws Exception {
		log.info("Start 'Login with wrong password test'");
		controller.createUser("userTest", "roleTest", "passwordTest");
		controller.loginUser("userTest", "passwordWrong");
	}

	@Test
	public void login() {
		try {
			log.info("Start 'Login test'");
			controller.createUser("userTest", "roleTest", "passwordTest");
			String role = controller.loginUser("userTest", "passwordTest");
			assertEquals(role, "roleTest");
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}
}