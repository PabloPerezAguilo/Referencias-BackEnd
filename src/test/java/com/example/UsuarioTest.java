package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.controllers.ReferenciaController;
import com.example.controllers.UsuarioController;
import com.example.dao.UsuarioDAO;
import com.example.models.Referencia;
import com.example.models.Usuario;
import com.example.utils.InitDB;

public class UsuarioTest {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceControllerTest.class);
	private static UsuarioController controller;
	private static UsuarioDAO dao;
	private static Usuario usuario;

	@Before
	public void init() throws Exception {
		
//		dao = UsuarioDAO.getInstance();
//		dao.clearStore();
//		log.info("Inicio test");
		
	}

	@AfterClass
	public static void end() throws Exception {
//		controller.dropReferencia();
//		InitDB.loadResources();
//		log.info("Storage restored");
//		log.info("End test");
	}

	@BeforeClass
	public  static void initTest() throws Exception {
		
		dao = UsuarioDAO.getInstance();
		dao.clearStore();
		log.info("Inicio de los test");
	}

	@Test
	public void datosVaciosGet() {
		log.info("Prueba inserccion de usuarios con base de datos vacia");
		try {
			
			
			List<Usuario> list = new ArrayList<Usuario>();
			list = controller.getUsuarios();
			if(!list.isEmpty()){
				fail("la BDD no esta vacia");
			}
			
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}
	
	@Test
	public void datosSet() {
		
		log.info("Prueba inserccion de usuario con base de datos vacia");
		try {
			usuario = new Usuario("pepito","ROLE_ADMIN");
			controller.createUsuario(usuario.getName(), usuario.getRole());
			
			usuario = controller.getUsuario(usuario.getName());
			if(usuario == null){
				fail("El usuario no se ha introducido");
			}
			
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
		
	}

//	@Test
//	public void createResource() {
////		log.info("Start 'Create Resource test'");
////		try {
////			Referencia r = new Referencia(0,"rbrito","GFI Centro","Banco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador");
////			controller.createReferencia(r);
////			r = controller.getReferencia(1);
////			assertEquals(r.getCliente(), "rbrito");
////		} catch (Exception e) {
////			fail("Error: " + e.getMessage());
////		}
//	}
//
//	@Test(expected = Exception.class)
//	public void createDubplicatedResource() throws Exception {
////		log.info("Start 'Create Duplicated Resource test'");
////		Referencia r = new Referencia(0,"rbrito","GFI Centro","Banco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador");
////		controller.createReferencia(r);
////		controller.createReferencia(r);
//	}

}
