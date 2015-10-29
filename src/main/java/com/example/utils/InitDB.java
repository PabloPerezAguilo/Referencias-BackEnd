package com.example.utils;

import org.apache.log4j.Logger;

import com.example.controllers.UsuarioController;
import com.example.dao.ReferenciaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Referencia;

public class InitDB {

	private static final Logger LOG = Logger.getLogger(InitDB.class.getName());

	public static void loadResources() throws Exception {
		ReferenciaDAO resourceDAO = ReferenciaDAO.getInstance();
		resourceDAO.clearStore();
		resourceDAO.insertReferencia(new Referencia(0,"rbrito","GFI Centro","Banco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		resourceDAO.insertReferencia(new Referencia(1,"msr","GFI Centro","Telco","Desarrollo",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		resourceDAO.insertReferencia(new Referencia(2,"ogarcia","GFI Norte","SQA","Testing",null,10,"Denominacion","resumenProyecto","problematicaCliente","solucionGfi","Java",10,"http://imagen.jpg","Si",new int[]{10,11,12},"josem","pepes","rbrito","CodigoQR","Borrador"));
		LOG.info("Resources inserted in DB");
	}

	public static void loadUsers() throws Exception {
		UsuarioDAO userDAO = UsuarioDAO.getInstance();
		userDAO.clearStore();
		UsuarioController controller = UsuarioController.getInstance();
		controller.createUser("a", "ROLE_USER", "a");
		controller.createUser("test", "ROLE_ADMIN", "test");
		LOG.info("Users inserted in DB");
	}

	public static void main(String[] args) throws Exception {
		InitDB.loadUsers();
	}
}