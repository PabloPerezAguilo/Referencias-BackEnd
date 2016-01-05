package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;

import com.example.dao.ReferenciaDAO;
import com.example.dao.TecnologiaDAO;
import com.example.models.NodoOld;
import com.example.models.ReferenciaWithAutoID;
import com.example.models.Tecnologia;
import com.example.utils.Config;

public class TecnologiaController {
	
	private static TecnologiaDAO dao;
	private static TecnologiaController singleton;

	private TecnologiaController() throws Exception {
		dao = TecnologiaDAO.getInstance();
	}

	public static TecnologiaController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new TecnologiaController();
		}
		return singleton;
	}

	public List<Tecnologia> getTecnologias() throws Exception {
		
		List<Tecnologia> list = new ArrayList<>();
		Iterator<Tecnologia> i = dao.getTecnologias();
		while (i.hasNext()) {
			Tecnologia tecnologia = i.next();
			list.add(tecnologia);
		}
		return list;
	}
	
	public Tecnologia getTecnologia(String nombre) throws Exception {
		
		return dao.getTecnologia(nombre);
		
	}

	public Tecnologia createTecnologia(Tecnologia tec) throws Exception{
		
		dao.insertTecnologia(tec);	
		return tec;
	}
	
	public String deleteTecnologia(String nombre) throws Exception{
		
		dao.deleteTecnologia(nombre);
		return nombre;
	}
	
	public Tecnologia updateTecnologia(String nombre, Tecnologia tec) throws Exception{
			
		dao.updateTecnologia(nombre,tec);		
		return tec;		
	}
	public void dropReferencia() {
		dao.clearStore();
	}

}
