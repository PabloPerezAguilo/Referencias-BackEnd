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
		
		// este metodo es neceasario para preparar la base de datos frente a la gfestion de tecnologias
		// inserta una tecnologia raiz a partir de la cual trabajara la capa controladora
		TecnologiaDAO tecnologiaDAO = TecnologiaDAO.getInstance();
		if(tecnologiaDAO.getTecnologia("Tecnologias")== null){
			
			List<Tecnologia> lista = new ArrayList<Tecnologia>();
			Tecnologia t = new Tecnologia("hojaInvalida1",null,false,null,"hojaInvalida");
			lista.add(t);
			t = new Tecnologia("hojaInvalida2",null,false,null,"hojaInvalida");
			lista.add(t);
			t = new Tecnologia("hojaInvalida3",null,false,null,"hojaInvalida");
			lista.add(t);
			Tecnologia raiz = new Tecnologia("Tecnologias",lista,false,null,"raiz");
			tecnologiaDAO.insertTecnologia(raiz);	
		}	
	}
	public static void loadResources() throws Exception {
		ReferenciaDAO resourceDAO = ReferenciaDAO.getInstance();
		//resourceDAO.clearStore();
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
		controller.createCliente(new CatalogoClientes("AB ENERGÍA","AGUAS DE BARBASTRO ELECTRICIDAD S.A",true,"",""));
		controller.createCliente(new CatalogoClientes("AB InBev España","IBEROCERMEX (CORONITA)",true,"",""));
		controller.createCliente(new CatalogoClientes("ABBOTT HEALTHCARE, S.A.","ABBOTT",true,"",""));
		controller.createCliente(new CatalogoClientes("ADMIRAL Group","BALUMBA",true,"",""));
		controller.createCliente(new CatalogoClientes("AENA Aeropuertos","AENA",true,"",""));
		controller.createCliente(new CatalogoClientes("AGENCIA ESPAÑOLA DE COOPERACIÓN INTERNACIONAL","AECID",true,"",""));
		controller.createCliente(new CatalogoClientes("AGENCIA ESPAÑOLA DE PROTECCIÓN DE DATOS","AGPD",true,"",""));
		controller.createCliente(new CatalogoClientes("AGENCIA ESPAÑOLA DE SEGURIDAD ALIMENTARIA Y NUTRICIÓN","AESAN",true,"",""));
		controller.createCliente(new CatalogoClientes("AGENCIA ESPAÑOLA DEL MEDICAMENTO","AEMPS",true,"",""));
		controller.createCliente(new CatalogoClientes("AGENCIA ESTATAL DE METEOROLOGIA","AEMET",true,"",""));
		controller.createCliente(new CatalogoClientes("Agencia Tributaria","AEAT",true,"",""));
		controller.createCliente(new CatalogoClientes("Agrupación Española de Entidades Aseguradoras de los Seguros Agrarios Combinados S.A.","AGROSEGUROS",true,"",""));
		controller.createCliente(new CatalogoClientes("Allfunds Bank","",true,"",""));
		controller.createCliente(new CatalogoClientes("AMA SEGUROS","AMA (PREVISIÓN SANITARIA NACIONAL, AGRUPACIÓN MUTUA",true,"",""));
		controller.createCliente(new CatalogoClientes("ASEFA, S.A. SEGUROS Y REASEGUROS, S.A.","",true,"",""));
		controller.createCliente(new CatalogoClientes("ASEGURADORES AGRUPADOS SA","",true,"",""));
		controller.createCliente(new CatalogoClientes("ASISA, ASISTENCIA SANITARIA INTERPROVINCIAL DE SEGUROS, S.A.","ASISA",true,"",""));
		controller.createCliente(new CatalogoClientes("AVALORA VILLAR MIR","OHL",true,"",""));
		controller.createCliente(new CatalogoClientes("AVIS ALQUILE UN COCHE, S.A.","AVIS",true,"",""));
		controller.createCliente(new CatalogoClientes("AVIVA VIDA Y PENSIONES, S.A.","AVIVA",true,"",""));
		controller.createCliente(new CatalogoClientes("AXA","AXA",true,"",""));
		controller.createCliente(new CatalogoClientes("AXA TECH","AXA TECH",true,"",""));
		controller.createCliente(new CatalogoClientes("AYUNTAMIENTO DE ALCOBENDAS","",true,"",""));
		controller.createCliente(new CatalogoClientes("AYUNTAMIENTO DE ALCORCON","",true,"",""));
		controller.createCliente(new CatalogoClientes("Ayuntamiento de Madrid - Coordinación de la Alcaldía","",true,"",""));
		controller.createCliente(new CatalogoClientes("Ayuntamiento de Madrid - Informática","IAM",true,"",""));
		controller.createCliente(new CatalogoClientes("Banco Bilbao Vizcaya Argentaria","BBVA",true,"",""));
		controller.createCliente(new CatalogoClientes("BANCO DE ESPAÑA","",true,"",""));
		controller.createCliente(new CatalogoClientes("Banco Espirito Santo","",true,"",""));
		controller.createCliente(new CatalogoClientes("BANKIA","",false,"Entidad Financiera Top 5",""));
		controller.createCliente(new CatalogoClientes("BARCELO VIAJES","",true,"",""));
		controller.createCliente(new CatalogoClientes("BOLETÍN OFICIAL DEL ESTADO","BOE",true,"",""));
		controller.createCliente(new CatalogoClientes("CANAL DE ISABEL II","CYII",true,"",""));
		controller.createCliente(new CatalogoClientes("CASA REAL","CASA DE SU MAJESTAD EL REY",true,"",""));
		controller.createCliente(new CatalogoClientes("CASER","Compañía de Seguros y Reaseguros S.A. - CASER -",true,"",""));
		controller.createCliente(new CatalogoClientes("Centro I. Energéticas, Medioambientales y Tecnológicas","CIEMAT",true,"",""));
		controller.createCliente(new CatalogoClientes("Centro Nacional de Inteligencia","CNI",true,"",""));
		controller.createCliente(new CatalogoClientes("Cetelem (Grupo BNP Paribas)","Cetelem",true,"",""));
		controller.createCliente(new CatalogoClientes("COLT TELECOM ESPAÑA, S.A.U.","COLT",true,"",""));
		controller.createCliente(new CatalogoClientes("CONGELATS REUNITS","LA SIRENA",true,"",""));
		controller.createCliente(new CatalogoClientes("Consejería de Educación y Ciencia - Castilla la Mancha","CEC-JCCM",true,"",""));
		controller.createCliente(new CatalogoClientes("Consejeria de Fomento - Castilla la Mancha","CF-JCCM",true,"",""));
		controller.createCliente(new CatalogoClientes("Consejo General del Poder Judicial","CGPJ",true,"",""));
		controller.createCliente(new CatalogoClientes("CONSEJO SUPERIOR DE DEPORTES","CSD",true,"",""));
		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
		controller.createCliente(new CatalogoClientes("Cramway Technologies","Cramway",true,"",""));
		controller.createCliente(new CatalogoClientes("DGGC - Dirección General de la Guardia Civil","Guardia Civil",true,"",""));
		controller.createCliente(new CatalogoClientes("DGP - Dirección General de la Policía","DGP",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
//		controller.createCliente(new CatalogoClientes("CORREOS Y TELEGRAFOS","",true,"",""));
		controller.createCliente(new CatalogoClientes("AXA Seguros", "AXA", true, "CompSeguros AX", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/AXA_Logo.svg/150px-AXA_Logo.svg.png"));
		controller.createCliente(new CatalogoClientes("Banco Bankia", "Bankia", true, "BancoVerde", "http://www.bankia.es/Ficheros/CMA/ficheros/CMP_IMG_h1_logo.PNG"));
		controller.createCliente(new CatalogoClientes("Banco BBVA", "BBVA", false, "BancoAzul", "http://www.bankia.es/Ficheros/CMA/ficheros/CMP_IMG_h1_logo.PNG"));
		controller.createCliente(new CatalogoClientes("Compañia Telefonica", "Telefonica", false, "Telefono", "http://www.bankia.es/Ficheros/CMA/ficheros/CMP_IMG_h1_logo.PNG"));
		
		/*Sector Empresarial*/
		CatalogoCoDeDAO codeDAO = CatalogoCoDeDAO.getInstance();
		codeDAO.clearStore();
		controller.createCoDe(new CatalogoCoDe("PUBLI", "Administraciones Públicas","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("BANK", "Banca","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("DISTR", "Distribución","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("ENERG", "Energía","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("INDUS", "Industria","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("MEDIA", "Medios de comunicacion","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("OTROS", "Otros","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("CHEMI", "Química","sectorempresarial"));	
		controller.createCoDe(new CatalogoCoDe("HEALT", "Sanidad","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("INSUR", "Seguros","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("SERVI", "Servicios","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("UTILI", "Suministros","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("TELEC", "Telecomunicaciones","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("TRANS", "Transporte","sectorempresarial"));
		
		
		/*Tipo Actividad*/
		controller.createCoDe(new CatalogoCoDe("DES", "Desarrollo","actividad"));
		controller.createCoDe(new CatalogoCoDe("INT", "Integración","actividad"));
		controller.createCoDe(new CatalogoCoDe("SQA", "Calidad","actividad"));
		controller.createCoDe(new CatalogoCoDe("SOP", "Soporte y Explotación","actividad"));
		controller.createCoDe(new CatalogoCoDe("PRO", "Producto","actividad"));
		
		/*Tipo de Proyecto*/
		controller.createCoDe(new CatalogoCoDe("PROY", "Proyecto","proyecto"));
		controller.createCoDe(new CatalogoCoDe("LIC", "Licencias","proyecto"));
		controller.createCoDe(new CatalogoCoDe("MANT", "Mantenimiento","proyecto"));
		controller.createCoDe(new CatalogoCoDe("OUT", "Outsourcing","proyecto"));
		controller.createCoDe(new CatalogoCoDe("PS", "Prestación de servicios","proyecto"));

		/*Sociedades*/
		controller.createCoDe(new CatalogoCoDe("AST", "AST","sociedades"));
		controller.createCoDe(new CatalogoCoDe("ARCITEL", "Arcitel","sociedades"));
		controller.createCoDe(new CatalogoCoDe("Gfi-C", "Gfi-Centro","sociedades"));
		controller.createCoDe(new CatalogoCoDe("Gfi-L", "Gfi-Levante","sociedades"));
		controller.createCoDe(new CatalogoCoDe("Gfi-N", "Gfi-Norte","sociedades"));
		controller.createCoDe(new CatalogoCoDe("Gfi-S", "Gfi-Sur","sociedades"));
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