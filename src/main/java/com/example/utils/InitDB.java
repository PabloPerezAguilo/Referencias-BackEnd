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
		controller.createUsuario("peafernandez","Pedro Antonio Fernandez Sáez", "administrador");
		controller.createUsuario("jldiaz","Jaime Lopez Diaz", "administrador");
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
		controller.createCliente(new CatalogoClientes("DGT - Dirección General de Tráfico","DGT",true,"",""));
		controller.createCliente(new CatalogoClientes("Dirección General del Catastro","Catastro",true,"",""));
		controller.createCliente(new CatalogoClientes("ECOEMBALAJES ESPAÑA, S.A.","ECOEMBES",true,"",""));
		controller.createCliente(new CatalogoClientes("ECOMMERCE BUY VIP ESPAÑA SL","AMAZON",true,"",""));
		controller.createCliente(new CatalogoClientes("EDENRED ESPAÑA","TICKET RESTAURANT",true,"",""));
		controller.createCliente(new CatalogoClientes("EMPRESA DE TRANSFORMACIÓN AGRARIA, S.A.","TRAGSA",true,"",""));
		controller.createCliente(new CatalogoClientes("Fábrica Nacional de Moneda y Timbre","FNMT",true,"",""));
		controller.createCliente(new CatalogoClientes("FONDO ESPAÑOL DE GARANTIA AGRARIA","FEGA",true,"",""));
		controller.createCliente(new CatalogoClientes("FREMAP, MUTUA DE ACCIDENTES DE TRABAJO Y ENFERMEDADES PROFESIONALES DE LA SEGURIDAD SOCIAL","FREMAP",true,"",""));
		controller.createCliente(new CatalogoClientes("FÚTBOL CLUB BARCELONA","",false,"Equipo Fútbol Liga BBVA",""));
		controller.createCliente(new CatalogoClientes("IBERDROLA Ingenieria y Construcción","IBERINCO",true,"",""));
		controller.createCliente(new CatalogoClientes("Ibermutuamur","",true,"",""));
		controller.createCliente(new CatalogoClientes("Informática y Comunicaciones de la Comunidad de Madrid","ICM",true,"",""));
		controller.createCliente(new CatalogoClientes("ING Direct","",true,"",""));
		controller.createCliente(new CatalogoClientes("ING NATIONALE-NEDERLANDEN VIDA","ING SEGUROS",true,"",""));
		controller.createCliente(new CatalogoClientes("INGENIERÍA DE SISTEMAS PARA LA DEFENSA DE ESPAÑA, S.A.","ISDEFE",true,"",""));
		controller.createCliente(new CatalogoClientes("INGENIERÍA Y ECONOMÍA DEL TRANSPORTE, S.A.","INECO",true,"",""));
		controller.createCliente(new CatalogoClientes("Intervención General de la Administración del Estado","IGAE",true,"",""));
		controller.createCliente(new CatalogoClientes("JAZZ TELECOM, S.A.","JAZZTEL",true,"",""));
		controller.createCliente(new CatalogoClientes("NUAGENETWORKS","Alcatel Lucent",true,"",""));
		controller.createCliente(new CatalogoClientes("RED.ES","",true,"",""));
		controller.createCliente(new CatalogoClientes("SANITAS, S.A. DE SEGUROS","SANITAS-GRUPO",true,"",""));
		controller.createCliente(new CatalogoClientes("SANTA LUCÍA, S.A. COMPAÑÍA DE SEGUROS","",true,"",""));
		controller.createCliente(new CatalogoClientes("Santander - Corporacion","Santander",true,"",""));
		controller.createCliente(new CatalogoClientes("Santander - Produban","Produban",true,"",""));
		controller.createCliente(new CatalogoClientes("Servicio de Salud de Castilla La Mancha","SESCAM",true,"",""));
		controller.createCliente(new CatalogoClientes("SIMOSA IT, S.A.","ABENGOA",true,"",""));
		controller.createCliente(new CatalogoClientes("SUMINISTROS IMPORTACIONES Y MANTENIMIENTOS ELECTRONICOS SERMICRO","SERMICRO",true,"",""));
		controller.createCliente(new CatalogoClientes("TINSA TASACIONES INMOBILIARIAS, S.A.U.","TINSA",true,"",""));
		controller.createCliente(new CatalogoClientes("AXA Seguros", "AXA", true, "CompSeguros AX", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/AXA_Logo.svg/150px-AXA_Logo.svg.png"));
		//controller.createCliente(new CatalogoClientes("Banco Bankia", "Bankia", false, "BancoVerde", "http://www.bankia.es/Ficheros/CMA/ficheros/CMP_IMG_h1_logo.PNG"));
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
		controller.createGerente(new CatalogoGerentes("abermejo", "Alejandro", "Bermejo Garrido","comercial"));
		controller.createGerente(new CatalogoGerentes("ajmarcos", "Antonio Jose", "Marcos Nohales","comercial"));
		controller.createGerente(new CatalogoGerentes("bpcastillo", "Blanca Paloma", "Castillo Martín","comercial"));
		controller.createGerente(new CatalogoGerentes("cbarreiro", "Clara", "Barreiro Cuiñas","comercial"));
		controller.createGerente(new CatalogoGerentes("eventura", "Enrique", "Ventura Pérez","comercial"));
		controller.createGerente(new CatalogoGerentes("fmateos", "Fernando", "Mateos Moran","comercial"));
		controller.createGerente(new CatalogoGerentes("igarcia", "Ignacio", "García Meroño","comercial"));
		controller.createGerente(new CatalogoGerentes("jdelbarrio", "Jaime", "del Barrio Pison","comercial"));
		controller.createGerente(new CatalogoGerentes("jgquevedo", "Jaime Elías", "García Quevedo","comercial"));
		controller.createGerente(new CatalogoGerentes("juriarte", "Javier", "Uriarte Martinez","comercial"));
		controller.createGerente(new CatalogoGerentes("jcoca", "Jesús", "Coca Gradín","comercial"));
		controller.createGerente(new CatalogoGerentes("jjlaseca", "Jesus Joaquin", "Laseca Cunchillos","comercial"));
		controller.createGerente(new CatalogoGerentes("jamiguel", "Jose Antonio", "de Miguel","comercial"));
		controller.createGerente(new CatalogoGerentes("javelasco", "Jose Antonio", "Velasco Peña","comercial"));
		controller.createGerente(new CatalogoGerentes("jechezarra", "Jose", "Echezarra Huguet","comercial"));
		controller.createGerente(new CatalogoGerentes("jmcorro", "José Manuel", "Corro Colina","comercial"));
		controller.createGerente(new CatalogoGerentes("lpmarzan", "Luis Pedro", "Marzán Peláez","comercial"));
		controller.createGerente(new CatalogoGerentes("mmira", "Maria", "Mira Viñas","comercial"));
		controller.createGerente(new CatalogoGerentes("pbarahona", "Pedro José", "Barahona García","comercial"));
		controller.createGerente(new CatalogoGerentes("smartin", "Sylvia", "Martín Desiderio","comercial"));
		
		controller.createGerente(new CatalogoGerentes("apascua", "Alfredo", "Pascua Garcia","tecnico"));
		controller.createGerente(new CatalogoGerentes("apuente", "Andeka", "Puente","tecnico"));
		controller.createGerente(new CatalogoGerentes("alelvira", "Angel Luis", "Elvira","tecnico"));
		controller.createGerente(new CatalogoGerentes("acortesm", "Antonio", "Cortes Moral","tecnico"));
		controller.createGerente(new CatalogoGerentes("fvicente", "Fermín", "Vicente Alonso","tecnico"));
		controller.createGerente(new CatalogoGerentes("falmarzam", "Fernando", "Almarza Montes","tecnico"));
		controller.createGerente(new CatalogoGerentes("fgomez", "Fernando", "Gomez Arnaiz","tecnico"));
		controller.createGerente(new CatalogoGerentes("iffernandez", "Isabel", "Fernández Fernández","tecnico"));
		controller.createGerente(new CatalogoGerentes("jrodriguez", "Javier", "Rodríguez Rodríguez","tecnico"));
		controller.createGerente(new CatalogoGerentes("jpfranco", "Jesús", "Pérez Franco","tecnico"));
		controller.createGerente(new CatalogoGerentes("jfrances", "Jorge", "Frances Frances","tecnico"));
		controller.createGerente(new CatalogoGerentes("jmlopez", "Jose Manuel", "Lopez Torets","tecnico"));
		controller.createGerente(new CatalogoGerentes("jspozo", "Julia", "Serrano Pozo","tecnico"));
		controller.createGerente(new CatalogoGerentes("peafernandez", "Pedro Antonio", "Fernandez Sáez","tecnico"));
		controller.createGerente(new CatalogoGerentes("rnunez", "Raúl", "Núñez Blanco","tecnico"));
		controller.createGerente(new CatalogoGerentes("svicente", "Santos", "Vicente Bermejo","tecnico"));
		controller.createGerente(new CatalogoGerentes("xcfernandez", "Xan Carlos", "Fernández Echezuria","tecnico"));
		
//		controller.createGerente(new CatalogoGerentes("pperez", "Pablo", "Perez","tecnico"));
//		controller.createGerente(new CatalogoGerentes("agarcia", "Angel", "Garcia","tecnico"));
//		controller.createGerente(new CatalogoGerentes("agallardo", "Alberto", "Gallardo","comercial"));
//		controller.createGerente(new CatalogoGerentes("jsanchez", "Jose", "Sanchez","comercial"));
		
		
		LOG.info("Catalogos inserted in DB");
	}

	public static void main(String[] args) throws Exception {
		InitDB.loadTecnologias();
		InitDB.loadUsers();
		InitDB.loadResources();
		InitDB.loadCatalogos();
	}
}