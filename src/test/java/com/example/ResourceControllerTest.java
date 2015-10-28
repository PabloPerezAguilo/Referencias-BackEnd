package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.controllers.ResourceController;
import com.example.models.Referencia;
import com.example.utils.InitDB;

public class ResourceControllerTest {

	private static final Logger log = LoggerFactory.getLogger(ResourceControllerTest.class);
	private static ResourceController controller;

	@BeforeClass
	public static void init() throws Exception {
		log.info("Start test");
		controller = ResourceController.getInstance();
	}

	@AfterClass
	public static void end() throws Exception {
		controller.dropResource();
		InitDB.loadResources();
		log.info("Storage restored");
		log.info("End test");
	}

	@Before
	public void initTest() throws Exception {
		controller.dropResource();
		log.info("Storage cleaned");
	}

	@Test
	public void emptyResource() {
		log.info("Start 'Empty Resource test'");
		try {
			List<Referencia> list = controller.getResources();
			assertTrue(list.isEmpty());
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}

	@Test
	public void createResource() {
		log.info("Start 'Create Resource test'");
		try {
			Referencia r = new Referencia(0,"rbrito","GFI Centro","Banco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador");
			controller.createResource(r);
			r = controller.getResource(1);
			assertEquals(r.getCliente(), "rbrito");
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}

	@Test(expected = Exception.class)
	public void createDubplicatedResource() throws Exception {
		log.info("Start 'Create Duplicated Resource test'");
		Referencia r = new Referencia(0,"rbrito","GFI Centro","Banco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador");
		controller.createResource(r);
		controller.createResource(r);
	}
}