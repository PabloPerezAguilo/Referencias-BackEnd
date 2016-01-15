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
		Tecnologia nodo = new Tecnologia((Map<String,Object>)recursos.get("nodo"));
		colocarNodo(arbol,(String)recursos.get("idPadre"),nodo);
		encontrado = false;
		System.out.println(arbol);
		dao.updateTecnologia("Tecnologias",arbol);
		return arbol;	
	}
	
	public Tecnologia deleteTecnologia(String nombre) throws Exception{
		
		Tecnologia arbol = dao.getTecnologias();
		borrarNodo(arbol,nombre);
		encontrado = false;
		dao.updateTecnologia("Tecnologias",arbol);
		return arbol;
	}
	
	public Tecnologia updateTecnologia(Map<String,Object> recursos) throws Exception{
		
		Tecnologia arbol = dao.getTecnologias();
		
		if(recursos.get("idDestino")!=null){
			
			Tecnologia nodo = new Tecnologia((Map<String,Object>)recursos.get("nodo"));
			borrarNodo(arbol,nodo.getNombre());
			encontrado=false;
			colocarNodo(arbol, (String)recursos.get("idDestino"),nodo);
			encontrado=false;
				
		}else{
			modificarNodo(arbol,(String)recursos.get("idAnterior"),(Tecnologia)recursos.get("nodo"));
			encontrado=false;
			return arbol;
		}
	
				
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
			if(idPadre.equals(actual.getNombre())){
				
				encontrado = true;
				if(actual.getNodosHijos()!=null){
					actual.getNodosHijos().add(nodo);
				}else{
					System.out.println("CORRECTO");
				List<Tecnologia> primero = new ArrayList<Tecnologia>();
				primero.add(nodo);
				actual.setNodosHijos(primero);
				}
			}
			
			if(actual.getNodosHijos()!=null){
			colocarNodo(actual,idPadre,nodo);
			}
			
			
		}
		
	}
	private void borrarNodo(Tecnologia busqueda, String nombre){
		
		List<Tecnologia> hijos = busqueda.getNodosHijos();
		Iterator<Tecnologia> iteradorHijos = hijos.iterator();
		Tecnologia actual;
		while(iteradorHijos.hasNext()&& encontrado == false){
			
			actual = iteradorHijos.next();
			if(nombre.equals(actual.getNombre())){
				encontrado = true;
				hijos.remove(actual);
			}
			if(actual.getNodosHijos()!=null){
			borrarNodo(actual,nombre);
			}
			
			
		}
		
	}
	private void modificarNodo(Tecnologia busqueda, String nombre,Tecnologia nodo){
		
		List<Tecnologia> hijos = busqueda.getNodosHijos();
		Iterator<Tecnologia> iteradorHijos = hijos.iterator();
		Tecnologia actual;
		while(iteradorHijos.hasNext()&& encontrado == false){
			
			actual = iteradorHijos.next();
			if(nombre.equals(actual.getNombre())){
				encontrado = true;
				actual = nodo;
			}
			if(actual.getNodosHijos()!=null){
			borrarNodo(actual,nombre);
			}
			
			
		}
		
	}
	
}
