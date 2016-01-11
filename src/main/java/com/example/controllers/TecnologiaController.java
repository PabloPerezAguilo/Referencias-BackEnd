package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	private static boolean encontrado = false;

	private TecnologiaController() throws Exception {
		dao = TecnologiaDAO.getInstance();
	}

	public static TecnologiaController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new TecnologiaController();
		}
		return singleton;
	}

	public Tecnologia getTecnologias() throws Exception {
		
		return dao.getTecnologias();
	}
	
	public Tecnologia getTecnologia(String nombre) throws Exception {
		
		return dao.getTecnologia(nombre);
		
	}

	public Tecnologia createTecnologia(Map<String,Object> recursos) throws Exception{
		
		Tecnologia arbol = dao.getTecnologias();
		colocarNodo(arbol,(String)recursos.get("idPadre"),(Tecnologia)recursos.get("nodo"));
		
		
		return arbol;	
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
	private void colocarNodo(Tecnologia busqueda, String idPadre, Tecnologia nodo){
		
		List<Tecnologia> hijos = busqueda.getNodosHijos();
		Iterator<Tecnologia> iteradorHijos = hijos.iterator();
		Tecnologia actual;
		while(iteradorHijos.hasNext()&& encontrado == false){
			
			actual = iteradorHijos.next();
			if(actual.getNombre() == idPadre){
				
				encontrado = true;
				List<Tecnologia> nueva = actual.getNodosHijos();
				nueva.add(nodo);
			}
			
			if(actual.getNodosHijos()!=null){
			colocarNodo(actual,idPadre,nodo);
			}
			
			
		}
		
	}
}
