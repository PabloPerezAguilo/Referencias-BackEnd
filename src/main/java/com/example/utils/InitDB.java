package com.example.utils;

import org.apache.log4j.Logger;

import com.example.controllers.UserController;
import com.example.dao.ResourceDAO;
import com.example.dao.UserDAO;
import com.example.models.Referencia;

public class InitDB {

	private static final Logger LOG = Logger.getLogger(InitDB.class.getName());

	public static void loadResources() throws Exception {
		ResourceDAO resourceDAO = ResourceDAO.getInstance();
		resourceDAO.clearStore();
		resourceDAO.insertResource(new Referencia(0,"rbrito","GFI Centro","Banco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		resourceDAO.insertResource(new Referencia(1,"msr","GFI Centro","Telco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		resourceDAO.insertResource(new Referencia(2,"ogarcia","GFI Norte","SQA","Testing",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		LOG.info("Resources inserted in DB");
	}

	public static void loadUsers() throws Exception {
		UserDAO userDAO = UserDAO.getInstance();
		userDAO.clearStore();
		UserController controller = UserController.getInstance();
		controller.createUser("a", "ROLE_USER", "a");
		controller.createUser("test", "ROLE_ADMIN", "test");
		LOG.info("Users inserted in DB");
	}

	public static void main(String[] args) throws Exception {
		InitDB.loadUsers();
	}
}