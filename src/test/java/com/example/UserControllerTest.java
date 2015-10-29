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

import com.example.controllers.UsuarioController;
import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import com.example.utils.InitDB;

public class UserControllerTest {

	private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
	private static UsuarioController controller;
	private static UsuarioDAO dao;

	@BeforeClass
	public static void init() throws Exception {
		log.info("Start test");
		controller = UsuarioController.getInstance();
		dao = UsuarioDAO.getInstance();
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
			List<Usuario> list = controller.getUsuarios();
			assertTrue(list.isEmpty());
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}

	@Test(expected = Exception.class)
	public void noCreatedUserLogin() throws Exception {
		log.info("Start 'No created user login test'");
		/*controller.loginUserLdap("userTest", "passwordTest");*/
	}

	@Test
	public void createUser() {
		try {
			log.info("Start 'Create User test'");
			controller.createUsuario("userTest", "roleTest");
			List<Usuario> list = controller.getUsuarios();
			Usuario user = list.get(0);
			assertNotNull(user);
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}

	@Test(expected = Exception.class)
	public void loginWrongPassword() throws Exception {
		log.info("Start 'Login with wrong password test'");
		controller.createUsuario("userTest", "roleTest");
		/*controller.loginUserLdap("userTest", "passwordWrong");*/
	}

	@Test
	public void login() {
		try {
			log.info("Start 'Login test'");
			controller.createUsuario("userTest", "roleTest");
			/*String role = controller.loginUserLdap("userTest", "passwordTest");*/
			/*assertEquals(role, "roleTest");*/
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}
}