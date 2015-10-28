package com.example.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.dao.ResourceDAO;
import com.example.models.Referencia;

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

	public List<Referencia> getResources() throws Exception {
		// Transform an iterator object to a list
		List<Referencia> list = new ArrayList<Referencia>();
		Iterator<Referencia> i = dao.getResources();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}

	public Referencia getResource(int key) throws Exception {
		Referencia resource = dao.getResource(key);
		if (resource == null) {
			throw new Exception("Resource not found");
		}
		return resource;
	}

	public Referencia createResource(Referencia r) throws Exception {
		dao.insertResource(r);
		return r;
	}

	public void dropResource() {
		dao.clearStore();
	}
}