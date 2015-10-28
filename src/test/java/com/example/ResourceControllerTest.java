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
import com.example.models.Resource;
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
			List<Resource> list = controller.getResources();
			assertTrue(list.isEmpty());
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}

	@Test
	public void createResource() {
		log.info("Start 'Create Resource test'");
		try {
			Resource r = new Resource(1, "value1");
			controller.createResource(r);
			r = controller.getResource(1);
			assertEquals(r.getValue(), "value1");
		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}

	@Test(expected = Exception.class)
	public void createDubplicatedResource() throws Exception {
		log.info("Start 'Create Duplicated Resource test'");
		Resource r = new Resource(1, "value1");
		controller.createResource(r);
		controller.createResource(r);
	}
}