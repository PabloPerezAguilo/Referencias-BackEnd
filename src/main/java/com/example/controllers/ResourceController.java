package com.example.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.dao.ResourceDAO;
import com.example.models.Resource;

public class ResourceController {

	// Singleton instances
	private static ResourceDAO dao;
	private static ResourceController singleton;

	private ResourceController() throws Exception {
		dao = ResourceDAO.getInstance();
	}

	public static ResourceController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ResourceController();
		}
		return singleton;
	}

	public List<Resource> getResources() throws Exception {
		// Transform an iterator object to a list
		List<Resource> list = new ArrayList<Resource>();
		Iterator<Resource> i = dao.getResources();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}

	public Resource getResource(int key) throws Exception {
		Resource resource = dao.getResource(key);
		if (resource == null) {
			throw new Exception("Resource not found");
		}
		return resource;
	}

	public Resource createResource(Resource r) throws Exception {
		dao.insertResource(r);
		return r;
	}

	public void dropResource() {
		dao.clearStore();
	}
}