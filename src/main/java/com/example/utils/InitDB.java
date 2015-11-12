package com.example.utils;

import org.apache.log4j.Logger;

import com.example.controllers.CatalogoController;
import com.example.controllers.UsuarioController;
import com.example.dao.CatalogoClientesDAO;
import com.example.dao.CatalogoCoDeDAO;
import com.example.dao.CatalogoGerentesDAO;
import com.example.dao.ReferenciaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Referencia;
import com.example.models.CatalogoClientes;
import com.example.models.CatalogoCoDe;
import com.example.models.CatalogoGerentes;

public class InitDB {

	private static final Logger LOG = Logger.getLogger(InitDB.class.getName());

	public static void loadResources() throws Exception {
		ReferenciaDAO resourceDAO = ReferenciaDAO.getInstance();
		resourceDAO.clearStore();
		resourceDAO.insertReferencia(new Referencia(0,"rbrito","GFI Centro","Banco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		resourceDAO.insertReferencia(new Referencia(1,"msr","GFI Centro","Telco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		resourceDAO.insertReferencia(new Referencia(2,"ogarcia","GFI Norte","SQA","Testing",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		LOG.info("Referencias inserted in DB");
	}

	public static void loadUsers() throws Exception {
		UsuarioDAO userDAO = UsuarioDAO.getInstance();
		userDAO.clearStore();
		UsuarioController controller = UsuarioController.getInstance();
		controller.createUsuario("rbrito", "validador");
		controller.createUsuario("msroa", "mantenimiento");
		controller.createUsuario("dmonco","administrador");
		controller.createUsuario("ogquevedo","consultor");
		LOG.info("Users inserted in DB");
	}
	
	public static void loadCatalogos() throws Exception {
		CatalogoClientesDAO clientesDAO = CatalogoClientesDAO.getInstance();
		clientesDAO.clearStore();
		CatalogoController controller = CatalogoController.getInstance();
		controller.createCliente(new CatalogoClientes("nombre", "siglas", true, "alias", "imagen"));
		
		CatalogoCoDeDAO codeDAO = CatalogoCoDeDAO.getInstance();
		codeDAO.clearStore();
		controller.createCoDe(new CatalogoCoDe("codigo1", "descripcion","sectorempresarial"));
		controller.createCoDe(new CatalogoCoDe("codigo2", "descripcion","actividad"));
		controller.createCoDe(new CatalogoCoDe("codigo3", "descripcion","proyecto"));
		controller.createCoDe(new CatalogoCoDe("codigo4", "descripcion","tecnologia"));
		controller.createCoDe(new CatalogoCoDe("codigo5", "descripcion","sociedades"));
		
		CatalogoGerentesDAO gerentesDAO = CatalogoGerentesDAO.getInstance();
		gerentesDAO.clearStore();
		controller.createGerente(new CatalogoGerentes("login1", "nombre", "apellidos",
				"comercial"));
		controller.createGerente(new CatalogoGerentes("login2", "nombre", "apellidos",
				"tecnico"));
		LOG.info("Catalogos inserted in DB");
	}

	public static void main(String[] args) throws Exception {
		InitDB.loadUsers();
		InitDB.loadResources();
		InitDB.loadCatalogos();
	}
}