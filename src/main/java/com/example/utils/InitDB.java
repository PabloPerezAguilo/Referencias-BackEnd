package com.example.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.example.controllers.CatalogoController;
import com.example.controllers.TecnologiaController;
import com.example.controllers.UsuarioController;
import com.example.dao.CatalogoClientesDAO;
import com.example.dao.CatalogoCoDeDAO;
import com.example.dao.CatalogoGerentesDAO;
import com.example.dao.ReferenciaDAO;
import com.example.dao.TecnologiaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.CatalogoClientes;
import com.example.models.CatalogoCoDe;
import com.example.models.CatalogoGerentes;
import com.example.models.ReferenciaWithAutoID;
import com.example.models.Tecnologia;

public class InitDB {

	private static final Logger LOG = Logger.getLogger(InitDB.class.getName());
	
	public static void loadTecnologias() throws Exception {
		
		TecnologiaDAO tecnologiaDAO = TecnologiaDAO.getInstance();
		if(tecnologiaDAO.getTecnologia("Tecnologias")== null){
			/* borrar despues de las pruebas console.log System.out.println();*/
			Tecnologia aux1 = new Tecnologia("nodo",null,false,null,"nodo");
			Tecnologia aux2 = new Tecnologia("hoja",null,false,null,"hoja");
			List<Tecnologia> lista = new ArrayList<Tecnologia>() ;
			lista.add(aux1);
			lista.add(aux2);
			/* borrar despues de las pruebas console.log System.out.println();*/
			Tecnologia raiz = new Tecnologia("Tecnologias",lista,false,null,"raiz");
			tecnologiaDAO.insertTecnologia(raiz);
			TecnologiaController tecon = TecnologiaController.getInstance();	
			
			tecon.deleteTecnologia("hoja");
			Map<String,Object> recursos = new HashMap<String, Object>();
			recursos.put("idPadre","nodo");
			recursos.put("nodo",aux2);
			tecon.createTecnologia(recursos);
			
		}
		System.out.println("resultado final");
		System.out.println(tecnologiaDAO.getTecnologia("Tecnologias"));
		LOG.info("Raiz cargada");
		
	}
	public static void loadResources() throws Exception {
		ReferenciaDAO resourceDAO = ReferenciaDAO.getInstance();
		resourceDAO.clearStore();
		//resourceDAO.insertReferencia(new ReferenciaWithAutoID("Banco BBVA","GFI Centro","BANK","MANT","SQA",null,10,"Denominacionguyup","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new String[]{"ERICS13 JG01001OU","UTEAV14 AP01001PS","UTEAV13 ST10003PO"},"Jose","pepes","rbrito","CodigoQR","borrador",null));
		//resourceDAO.insertReferencia(new ReferenciaWithAutoID("Banco BBVA","GFI Centro","BANK","MANT","SQA",null,10,"Denominacionguyup","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new String[]{"ERICS13 JG01001OU","UTEAV14 AP01001PS","UTEAV13 ST10003PO"},"Jose","pepes","rbrito","CodigoQR","borrador",null));
		//resourceDAO.insertReferencia(new ReferenciaWithAutoID("Banco BBVA","GFI Centro","BANK","MANT","SQA",null,10,"Denominacionguyup","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new String[]{"ERICS13 JG01001OU","UTEAV14 AP01001PS","UTEAV13 ST10003PO"},"Jose","pepes","rbrito","CodigoQR","borrador",null));
		//resourceDAO.insertReferencia(new ReferenciaWithAutoID("AXA Seguros","GFI-N","BANK","MANT","SQA",null,15,"Denominacionguyup","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new String[]{"UTEUC11 ST06001OU","ERICS13 JG01001OU"},"Alberto","Pablo","rbrito","CodigoQR","pendiente",null));
		//resourceDAO.insertReferencia(new ReferenciaWithAutoID("Compañia Telefonica","ARCITEL","CHEMI","OUT","SOP",null,10,"Denominacionguyup","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new String[]{"ERICS13 JG01001OU","UTEAV14 AP01001PS","UTEUC11 ST06001OU"},"Jose","Angel","rbrito","CodigoQR","pendiente",null));
		LOG.info("Referencias inserted in DB");
	}

	public static void loadUsers() throws Exception {
		UsuarioDAO userDAO = UsuarioDAO.getInstance();
		userDAO.clearStore();
		UsuarioController controller = UsuarioController.getInstance();
		controller.createUsuario("amayor","Africa Mayor Colomina", "validador");
		controller.createUsuario("abarrero","Almudena Barrero", "mantenimiento");
		controller.createUsuario("dmonco","David Monco Jimenez", "administrador");
		controller.createUsuario("agonzalez","Alejandro Gonzalez Garcia", "consultor");
		controller.createUsuario("fgmontoro","Francisco Garcia Montoro", "mantenimiento");
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
		InitDB.loadTecnologias();
		InitDB.loadUsers();
		InitDB.loadResources();
		InitDB.loadCatalogos();
	}
}