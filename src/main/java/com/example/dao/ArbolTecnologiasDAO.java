package com.example.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import com.example.models.Hoja;
import com.example.models.Nodo;
import com.example.models.Usuario;

public class ArbolTecnologiasDAO {

	private static ArbolTecnologiasDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "ArbolTecnologias";

	private ArbolTecnologiasDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static ArbolTecnologiasDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ArbolTecnologiasDAO();
		}
		return singleton;
	}

	/**
	 * getUsuarios
	 * @return Iterator<Usuario>
	 * @throws Exception
	 */
	public void getTecnologias() throws Exception {
		
		//System.out.println(dao.find().getClass());
		Iterator<Nodo> aso = dao.find().as(Nodo.class).iterator();
		
		while (aso.hasNext()){
			System.out.println(aso.next().toString());
		}
		
		//return dao.find(). as(Nodo.class).iterator();
	}

	/**
	 * getUsuario
	 * @param idUser
	 * @return Usuario
	 * @throws Exception
	 */
	public Nodo getTecnologia(String nombre) throws Exception {
		return dao.findOne("{'_id':#}", nombre).as(Nodo.class);
	}
	
	/**
	 * getUsuarioLogin
	 * @param idUser
	 * @return Usuario
	 * @throws Exception
	 */
	
	public void insertTecnologia(Nodo tecnologia) throws Exception {
		dao.insert(tecnologia);
	}
	public void insertTecnologia(Hoja tecnologia) throws Exception {
		dao.insert(tecnologia);
	}
	
	/**
	 * deleteUsuario
	 * @param key
	 * @throws Exception
	 */
	public void deleteUsuario(String key) throws Exception {
		dao.remove("{'_id':#}", key);
	}
	
	/**
	 * updateUsuario
	 * @param key
	 * @param r
	 * @throws Exception
	 */
	public void updateUsuario(String key, Usuario r) throws Exception {
		dao.update("{'nombre':#}", key).with(r);
	}

	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
	public static void main(String[] args) throws Exception {
		
		List<Nodo> lista1 = new ArrayList<Nodo>();
		List<Nodo> lista2 = new ArrayList<Nodo>();
		List<Nodo> lista12 = new ArrayList<Nodo>();
		List<Nodo> lista13 = new ArrayList<Nodo>();
		List<Nodo> lista13b= new ArrayList<Nodo>();
		List<Nodo> lista22= new ArrayList<Nodo>();
		
		Nodo aux1 = new Nodo("primerPadre",null);
		Nodo aux2 = new Nodo("segundoPadre",null);
		
		lista1.add(aux1);
		
		Nodo aux11 = new Nodo("intermedio1.1",lista1);
		Nodo aux12 = new Nodo("intermedio1.2",lista1);
	
		lista2.add(aux2);
		
		Nodo aux21= new Nodo("intermedio2.1",lista2);
		
		lista12.add(aux1);
		lista12.add(aux11);
		Nodo aux111 = new Nodo("intermedio1.1.1",lista12);
		
		lista13.add(aux1);
		lista13.add(aux11);
		lista13.add(aux111);
		Hoja hoja1 = new Hoja("hoja 1.1.1.1",lista13,"sda","prueba",false);
		Hoja hoja2 = new Hoja("hoja 1.1.1.2",lista13,"sda","prueba",false);
		
		lista13b.add(aux1);
		lista13b.add(aux12);
		Hoja hoja3 = new Hoja("hoja 1.2.1",lista13b,"sda","prueba",false);
		
		lista22.add(aux2);
		lista22.add(aux21);
		Hoja hoja4 = new Hoja("hoja 2.1.1",lista2,"sda","prueba",false);
		
		ArbolTecnologiasDAO prueba = new ArbolTecnologiasDAO();
		
		prueba.clearStore();
		prueba.insertTecnologia(aux1);
		prueba.insertTecnologia(aux2);
		prueba.insertTecnologia(aux11);
		prueba.insertTecnologia(aux12);
		prueba.insertTecnologia(aux21);
		prueba.insertTecnologia(aux111);
		prueba.insertTecnologia(hoja1);
		prueba.insertTecnologia(hoja2);
		prueba.insertTecnologia(hoja3);
		prueba.insertTecnologia(hoja4);
		
		
		prueba.getTecnologias();
		
//		System.out.println(pepito.getClass());
//		if(pepito!=null){
//			System.out.println(pepito.toString());	
//		}
//		while(pepito.hasNext()){
//			pepito.next();
//			System.out.println(pepito.toString());	 
//		}
//		
//		System.out.println(pepito.toString());
//		
//	
	}
}