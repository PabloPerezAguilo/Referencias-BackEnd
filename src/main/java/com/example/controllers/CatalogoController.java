package com.example.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.dao.CatalogoClientesDAO;
import com.example.dao.CatalogoCoDeDAO;
import com.example.dao.CatalogoGerentesDAO;
import com.example.models.CatalogoClientes;
import com.example.models.CatalogoCoDe;
import com.example.models.CatalogoGerentes;

public class CatalogoController {

	private static CatalogoClientesDAO daoClientes;
	private static CatalogoCoDeDAO daoCoDe;
	private static CatalogoGerentesDAO daoGerentes;
	private static CatalogoController singleton;
	

	private CatalogoController() throws Exception {
		daoClientes = CatalogoClientesDAO.getInstance();
		daoCoDe = CatalogoCoDeDAO.getInstance();
		daoGerentes = CatalogoGerentesDAO.getInstance();
	}

	public static CatalogoController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new CatalogoController();
		}
		return singleton;
	}
	
	/**
	 * getCatalogos
	 * @return Map<String,? extends Object>
	 * @throws Exception
	 */
	public Map<String,? extends Object> getCatalogos() throws Exception {
		
		Map<String,Object> m = new HashMap<String, Object>();
		List<CatalogoClientes> listaClientes = daoClientes.getCatalogoClientes();
		List<CatalogoCoDe> listaCoDeSectorEmpresarial = daoCoDe.getCoDePorTipo("sectorempresarial");
		List<CatalogoCoDe> listaCoDeActividad = daoCoDe.getCoDePorTipo("actividad");
		List<CatalogoCoDe> listaCoDeProyecto = daoCoDe.getCoDePorTipo("proyecto");
		List<CatalogoCoDe> listaCoDeTecnologia = daoCoDe.getCoDePorTipo("tecnologia");
		List<CatalogoCoDe> listaCoDeSociedades = daoCoDe.getCoDePorTipo("sociedades");
		List<CatalogoGerentes> listaGerentesComercial = daoGerentes.getGerentesPorTipo("comercial");
		List<CatalogoGerentes> listaGerentesTecnico = daoGerentes.getGerentesPorTipo("tecnico");
		m.put("clientes", listaClientes);
		m.put("sectorempresarial", listaCoDeActividad);
		m.put("actividad", listaCoDeProyecto);
		m.put("proyecto", listaCoDeTecnologia);
		m.put("tecnologia", listaCoDeSociedades);
		m.put("sociedades", listaCoDeSectorEmpresarial);
		m.put("comercial", listaGerentesComercial);
		m.put("tecnico", listaGerentesTecnico);
		
		 
		return m;
	}
	
	public CatalogoClientes createCliente(CatalogoClientes r) throws Exception {
		daoClientes.insertCliente(r);
		return r;
	}
	
	public CatalogoCoDe createCoDe(CatalogoCoDe r) throws Exception {
		daoCoDe.insertCoDe(r);
		return r;
	}
	
	public CatalogoGerentes createGerente(CatalogoGerentes r) throws Exception{
		daoGerentes.insertGerente(r);
		return r;
	}
	
	

}

