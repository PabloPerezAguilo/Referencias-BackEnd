package com.example.controllers;

import java.util.Iterator;
import java.util.Map;

import com.example.dao.PlantillaDAO;
import com.example.models.Plantilla;



public class PlantillaController {

	private static PlantillaDAO dao;
	private static PlantillaController singleton;
	

	private PlantillaController() throws Exception {
		dao = PlantillaDAO.getInstance();
	}

	public static PlantillaController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new PlantillaController();
		}
		return singleton;
	}
	
	/**
	 * getCatalogos
	 * @return Map<String,? extends Object>
	 * @throws Exception
	 */
	public Iterator<Plantilla> getPlantillas() throws Exception {
				
		
		return dao.getPlantillas();
	}
	
	public Plantilla createPlantilla(Plantilla p) throws Exception {
		dao.insertPlantilla(p);
		return p;
	}
	
	public Plantilla getPlantilla(String key) throws Exception {
		
		return dao.getPlantilla(key);
	}
	
	public void deletePlantilla(String key) throws Exception {
		
		dao.deletePlantilla(key);
	}
	
	public Plantilla updatePlantilla(Map<String,Object> recursos) throws Exception {
		
		String key = (String) recursos.get("idPlantillaOriginal");
		Plantilla p = (Plantilla) recursos.get("plantilla");
		dao.updateReferencia(key, p);
		return p;
	}

	public Iterator<Plantilla> getPlantillasPublicas() throws Exception {
				
		return dao.getPlantillasPublicas();
	}
		
}
