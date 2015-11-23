package com.example;

import static org.junit.Assert.fail;

import java.util.ArrayList;
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


public class UsuarioTest {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioTest.class);
	private static UsuarioController controller;
	private static UsuarioDAO dao;
	//private static Usuario usuario;

	@Before
	public void init() throws Exception {
		
		//se ejecuta antes de cada @test
		
	}

	@AfterClass
	public static void end() throws Exception {
		
		//se ejecuta al final de todos los test

	}

	@BeforeClass
	public  static void initTest() throws Exception {
		
		//se ejecuta antes de todos los test
		dao = UsuarioDAO.getInstance();
		controller = UsuarioController.getInstance();
		dao.clearStore();
		log.info("Inicio de los test");
	}

	@Test
	public void pruebaAltaDatos() {
		
		log.info("Prueba de alta de datos de usuarios:");
		try {
			
			List<Usuario> list = new ArrayList<Usuario>();
			list = controller.getUsuarios();
			if(!list.isEmpty()){
				throw new Exception("la base de datos no esta vacia");
			}else{
				log.info("Inicio de la base de datos correcta");				
			}
		} catch (Exception e) {
			fail("Error de inicio de base de datos: " + e.getMessage());
		}
		
		try {
			
			List<Usuario> list = new ArrayList<Usuario>();
			controller = UsuarioController.getInstance();
			controller.createUsuario("rbrito","Ruben Brito Baldanta", "validador");
			list = controller.getUsuarios();
			if(list.size() != 1){
				throw new Exception("elementos distintos de los introducidos por defecto o exceso");
			}else{
				log.info("Insercion correcta");				
			}
			
		} catch (Exception e) {
			fail("Error de insercion: " + e.getMessage());
		}
		try {
			
			List<Usuario> list = new ArrayList<Usuario>();
			controller = UsuarioController.getInstance();
			controller.createUsuario("msroa","Maria Sanchez Roa", "mantenimiento");
			controller.createUsuario("dmonco","David Monco Jimenez","administrador");
			controller.createUsuario("ogquevedo","Oscar Garcia Quevedo","consultor");
			list = controller.getUsuarios();
			if(list.size() != 4){
				throw new Exception("elementos distintos de los introducidos por defecto o exceso");
			}else{
				log.info("Insercion multiple correcta");				
			}
			
		} catch (Exception e) {
			fail("Error de insercion: " + e.getMessage());
		}
	}
}
