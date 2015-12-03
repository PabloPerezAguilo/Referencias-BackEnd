package com.example.utils;


import org.apache.log4j.Logger;

import com.example.controllers.CatalogoController;
import com.example.controllers.UsuarioController;
import com.example.dao.CatalogoClientesDAO;
import com.example.dao.CatalogoCoDeDAO;
import com.example.dao.CatalogoGerentesDAO;
import com.example.dao.ReferenciaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.CatalogoClientes;
import com.example.models.CatalogoCoDe;
import com.example.models.CatalogoGerentes;
import com.example.models.ReferenciaWithAutoID;

public class InitDB {

	private static final Logger LOG = Logger.getLogger(InitDB.class.getName());
	
	public static void loadResources() throws Exception {
		ReferenciaDAO resourceDAO = ReferenciaDAO.getInstance();
		resourceDAO.clearStore();
		//resourceDAO.insertReferencia(new ReferenciaWithAutoID("rbrito","GFI Centro","Banco","Proyecto","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		//resourceDAO.insertReferencia(new ReferenciaWithAutoID("msr","GFI Centro","Telco","Proyecto","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","pendiente"));
		//resourceDAO.insertReferencia(new ReferenciaWithAutoID("ogarcia","GFI Norte","SQA","Proyecto","Calidad",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","pendiente"));
		LOG.info("Referencias inserted in DB");
	}

	public static void loadUsers() throws Exception {
		UsuarioDAO userDAO = UsuarioDAO.getInstance();
		userDAO.clearStore();
		UsuarioController controller = UsuarioController.getInstance();
		controller.createUsuario("rbrito","Ruben Brito Baldanta", "validador");
		controller.createUsuario("msroa","Maria Sanchez Roa", "mantenimiento");
		controller.createUsuario("dmonco","David Monco Jimenez", "administrador");
		controller.createUsuario("ogquevedo","Oscar Garcia Quevedo", "consultor");
		controller.createUsuario("npavila","Norberto Rastafarai Avila", "mantenimiento");
		LOG.info("Users inserted in DB");
	}
	
	public static void loadCatalogos() throws Exception {
		
		/*Clientes*/
		CatalogoClientesDAO clientesDAO = CatalogoClientesDAO.getInstance();
		clientesDAO.clearStore();
		CatalogoController controller = CatalogoController.getInstance();
		controller.createCliente(new CatalogoClientes("AXA Seguros", "AXA", true, "CompSeguros AX", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/AXA_Logo.svg/150px-AXA_Logo.svg.png"));
		controller.createCliente(new CatalogoClientes("Banco Bankia", "Bankia", true, "BancoVerde", "http://www.bankia.es/Ficheros/CMA/ficheros/CMP_IMG_h1_logo.PNG"));
		controller.createCliente(new CatalogoClientes("Banco BBVA", "BBVA", false, "BancoAzul", "http://www.bankia.es/Ficheros/CMA/ficheros/CMP_IMG_h1_logo.PNG"));
		controller.createCliente(new CatalogoClientes("Compañia Telefonica", "Telefonica", false, "Telefono", "http://www.bankia.es/Ficheros/CMA/ficheros/CMP_IMG_h1_logo.PNG"));
		
		/*Sector Empresarial*/
		CatalogoCoDeDAO codeDAO = CatalogoCoDeDAO.getInstance();
		codeDAO.clearStore();
		controller.createCoDe(new CatalogoCoDe("BANK", "Banca","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("CHEMI", "Química","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("DISTR", "Distribución","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("ENERG", "Energía","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("HEALT", "Sanidad","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("INDUS", "Industria","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("INSUR", "Seguros","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("MEDIA", "Medios de comunicacion","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("OTROS", "Otros","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("PUBLI", "Administraciones Públicas","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("SERVI", "Servicios","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("TELEC", "Telecomunicaciones","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("TRANS", "Transporte","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("UTILI", "Suministros","sectorempresarial"));
		
		/*Tipo Actividad*/
		controller.createCoDe(new CatalogoCoDe("DES", "Desarrollo","actividad"));
		controller.createCoDe(new CatalogoCoDe("INT", "Integracion","actividad"));
		controller.createCoDe(new CatalogoCoDe("SQA", "Calidad","actividad"));
		controller.createCoDe(new CatalogoCoDe("SOP", "Soporte y Explotación","actividad"));
		controller.createCoDe(new CatalogoCoDe("PRO", "Producto","actividad"));
		
		/*Tipo de Proyecto*/
		controller.createCoDe(new CatalogoCoDe("PROY", "Proyecto","proyecto"));
		controller.createCoDe(new CatalogoCoDe("LIC", "Licencias","proyecto"));
		controller.createCoDe(new CatalogoCoDe("MANT", "Mantenimiento","proyecto"));
		controller.createCoDe(new CatalogoCoDe("OUT", "Outsourcing","proyecto"));
		controller.createCoDe(new CatalogoCoDe("PS", "Prestacion de servicios","proyecto"));

		/*Tecnologias*/
		controller.createCoDe(new CatalogoCoDe("JAVA", "JAVA","tecnologia"));
		controller.createCoDe(new CatalogoCoDe("PHP", "PHP","tecnologia"));
		controller.createCoDe(new CatalogoCoDe("JS", "Javascript","tecnologia"));
		controller.createCoDe(new CatalogoCoDe("AngularJS", "AngularJS Framework Javascript","tecnologia"));
		
		/*Sociedades*/
		controller.createCoDe(new CatalogoCoDe("AST", "AST","sociedades"));
		controller.createCoDe(new CatalogoCoDe("ARCITEL", "Arcitel","sociedades"));
		controller.createCoDe(new CatalogoCoDe("GFI-L", "GFI-Levante","sociedades"));
		controller.createCoDe(new CatalogoCoDe("GFI-N", "GFI-Norte","sociedades"));
		controller.createCoDe(new CatalogoCoDe("GFI-S", "GFI-Sur","sociedades"));
		controller.createCoDe(new CatalogoCoDe("SAVAC", "SAVAC","sociedades"));
		controller.createCoDe(new CatalogoCoDe("IORGA", "IORGA","sociedades"));
		
		/*Gerentes*/
		CatalogoGerentesDAO gerentesDAO = CatalogoGerentesDAO.getInstance();
		gerentesDAO.clearStore();
		controller.createGerente(new CatalogoGerentes("pperez", "Pablo", "Perez","tecnico"));
		controller.createGerente(new CatalogoGerentes("agarcia", "Angel", "Garcia","tecnico"));
		controller.createGerente(new CatalogoGerentes("agallardo", "Alberto", "Gallardo","comercial"));
		controller.createGerente(new CatalogoGerentes("jsanchez", "Jose", "Sanchez","comercial"));
		
		
		LOG.info("Catalogos inserted in DB");
	}

	public static void main(String[] args) throws Exception {
		InitDB.loadUsers();
		InitDB.loadResources();
		InitDB.loadCatalogos();
	}
}