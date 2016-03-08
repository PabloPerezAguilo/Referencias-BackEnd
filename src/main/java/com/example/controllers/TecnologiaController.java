package com.example.controllers;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.dao.TecnologiaDAO;
import com.example.models.Tecnologia;


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
		
		Tecnologia arbol = dao.getTecnologias();
		Tecnologia aux = existeNodo(arbol,nombre);
		encontrado = false;
		return aux;
		
	}
	public List<Tecnologia> getTecnologiasFinales( ) throws Exception {
		
		Tecnologia arbol = dao.getTecnologias();
		List<Tecnologia> lista = getFinales(arbol);
		return lista;
		
	}

	public Tecnologia createTecnologia(Map<String,Object> recursos) throws Exception{
		
		Tecnologia arbol = dao.getTecnologias();
		Tecnologia nodo = new Tecnologia((Map<String,Object>)recursos.get("nodo"));
		existeNodo(arbol,nodo.getNombre());
		if(encontrado != true){
			encontrado = false;
			colocarNodo(arbol,(String)recursos.get("idPadre"),nodo);
			encontrado = false;
			dao.updateTecnologia("Tecnologias",arbol);
			return arbol;
		}else{
			encontrado = false;
			throw new Exception("Error de creacion: Ese nombre de tecnologia ya existe");	
		}
			
	}
	
	public Tecnologia createTecnologiaInvalida(Tecnologia recurso) throws Exception{
		
		Tecnologia arbol = dao.getTecnologias();
		existeNodo(arbol,recurso.getNombre());
		if(encontrado != true){
			encontrado = false;
			List<Tecnologia> origen = arbol.getNodosHijos();
			List<Tecnologia> nueva = new ArrayList<Tecnologia>();
			nueva.add(recurso);
			nueva.addAll(origen);
			arbol.setNodosHijos(nueva);
			dao.updateTecnologia("Tecnologias",arbol);
			return arbol;
		}else{
			encontrado = false;
			throw new Exception("Error de creacion: Ese nombre de tecnologia ya existe");	
		}
			
	}
	
	public Tecnologia deleteTecnologia(String nombre) throws Exception{
		
		ReferenciaController referenciaController = ReferenciaController.getInstance();
		if(referenciaController.hayReferenciaAsociada(nombre)){
			throw new Exception("Esta tecnologia tiene referencias asociadas a ella. Si acaba de modificarlas es posible que alguien haya creado una recientemente mientras modificaba las anteriores.");
		}else{
			
			Tecnologia arbol = dao.getTecnologias();
			borrarNodo(arbol,nombre,false);
			encontrado = false;
			dao.updateTecnologia("Tecnologias",arbol);
			return arbol;	
		}
	
	}
	
	public Tecnologia updateTecnologia(Map<String,Object> recursos) throws Exception{
		
		Tecnologia arbol = dao.getTecnologias();
		Tecnologia nodo = new Tecnologia((Map<String,Object>)recursos.get("nodo"));
		
		if(recursos.get("idDestino")!=null){
			try{
				borrarNodo(arbol,nodo.getNombre(),true);
				encontrado=false;
				colocarNodo(arbol, (String)recursos.get("idDestino"),nodo);
				encontrado=false;
			}catch(Exception e){
				throw new Exception("Error al mover el elemento",e);
			}
				
		}else{
			
			if(!nodo.getNombre().equals((String)recursos.get("idAnterior"))){
				existeNodo(arbol,nodo.getNombre());	
			}
			
			if(encontrado!=true){
				encontrado=false;
				modificarNodo(arbol,(String)recursos.get("idAnterior"),nodo);
				encontrado=false;
			}else{
				encontrado=false;
				throw new Exception("Error de edicion: Ese nombre de tecnologia ya existe");	
			}
			
		}
		dao.updateTecnologia("Tecnologias",arbol);
		return arbol;
	
				
	}
	public void dropReferencia() {
		dao.clearStore();
	}
	private void colocarNodo(Tecnologia busqueda, String idPadre, Tecnologia nodo){
		
		List<Tecnologia> hijos = new ArrayList<Tecnologia>();
		hijos.add(busqueda);
		Iterator<Tecnologia> iteradorHijos = hijos.iterator();
		Tecnologia actual;
		while(iteradorHijos.hasNext()&& encontrado == false){
			
			actual = iteradorHijos.next();
			if(idPadre.equals(actual.getNombre())){
				
				encontrado = true;
				if(actual.getNodosHijos()!=null){
					actual.getNodosHijos().add(nodo);
				}else{
				List<Tecnologia> primero = new ArrayList<Tecnologia>();
				primero.add(nodo);
				actual.setNodosHijos(primero);
				}
			}
			
			if(actual.getNodosHijos()!=null){
				for(int i=0; i<actual.getNodosHijos().size();i++){
					colocarNodo(actual.getNodosHijos().get(i),idPadre,nodo);
				}
			
			}
			
			
		}
		
	}
	private void borrarNodo(Tecnologia busqueda, String nombre,boolean permitir) throws Exception{
		
		List<Tecnologia> hijos = busqueda.getNodosHijos();
		Iterator<Tecnologia> iteradorHijos = hijos.iterator();
		Tecnologia actual;
		while(iteradorHijos.hasNext()&& encontrado == false){
			
			actual = iteradorHijos.next();
			if(nombre.equals(actual.getNombre())){
				encontrado = true;
				if(actual.getNodosHijos()==null || actual.getNodosHijos().isEmpty()||permitir){
				hijos.remove(actual);
				}else{
					throw new Exception("Error de borrado: El nodo contiene tecnologias asociadas a el");
				}
			}else if(actual.getNodosHijos()!=null){
			borrarNodo(actual,nombre,permitir);
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
				hijos.remove(actual);
				hijos.add(nodo);
			}
			if(actual.getNodosHijos()!=null){
			modificarNodo(actual,nombre,nodo);
			}
			
			
		}
		
	}
	private Tecnologia existeNodo(Tecnologia busqueda, String nombre){
		
		List<Tecnologia> hijos = busqueda.getNodosHijos();
		Iterator<Tecnologia> iteradorHijos = hijos.iterator();
		Tecnologia actual ;
		while(iteradorHijos.hasNext()&& encontrado == false){
			
			actual = iteradorHijos.next();
			if(nombre.equals(actual.getNombre())){
				encontrado = true;
				return actual;
			}
			if(actual.getNodosHijos()!=null){
				Tecnologia resultado = existeNodo(actual,nombre);
				if(resultado!=null){
				return resultado;
				}
			}
			
			
		}
		return null;	
	}
	private List<Tecnologia> getFinales(Tecnologia busqueda){
		
		List<Tecnologia> hojas = new ArrayList<Tecnologia>();
		Iterator<Tecnologia> iteradorHijos = busqueda.getNodosHijos().iterator();
		Tecnologia actual;
		while(iteradorHijos.hasNext()){
			
			actual = iteradorHijos.next();
			if(actual.getClase().equals("hoja")||actual.getClase().equals("hojaInvalida")){
				
				hojas.add(actual);
				
				
			}else if(actual.getNodosHijos()!= null){
				
				hojas.addAll(getFinales(actual));
				
			}
			
			
		}
		return hojas;
		
	}
	public List<String> busquedaTecnologias(String producto,List<String> tipoTecnologias) throws Exception {
		
		Tecnologia arbol = dao.getTecnologias();
		List<String> lista = busquedaTecnologiaRecursiva(producto,tipoTecnologias,arbol);
		return lista;
		
	}
	
	public List<String> busquedaTecnologiaRecursiva(String producto,List<String> tipoTecnologias, Tecnologia busqueda){
		
		List<String> resultado = new ArrayList<String>();
		Iterator<Tecnologia> iteradorHijos = busqueda.getNodosHijos().iterator();
		Tecnologia actual;
		while(iteradorHijos.hasNext()){
			
			actual = iteradorHijos.next();
			if(actual.getClase().equals("hoja")||actual.getClase().equals("hojaInvalida")){
				
				if(producto.equals("")){
				
				resultado.add(actual.getNombre());
				
				}else if(producto.equals("no")&& !actual.isProducto()){
				
				resultado.add(actual.getNombre());
				
				}else if((producto.equals("si")&& actual.isProducto())
						&&(tipoTecnologias==null||tipoTecnologias.size()==0)){
				resultado.add(actual.getNombre());
				}else if(producto.equals("si")&& actual.isProducto()
						&&tipoTecnologias!=null&&tipoTecnologias.contains(actual.getTipo())){
				resultado.add(actual.getNombre());
				}
			}	
			else if(actual.getNodosHijos()!= null){
				
				resultado.addAll(busquedaTecnologiaRecursiva(producto,tipoTecnologias, actual));
				
			}
			
			
		}
		return resultado;
		
	}
	
}
