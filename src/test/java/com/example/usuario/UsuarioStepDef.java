package com.example.usuario;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.example.controllers.UsuarioController;
import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UsuarioStepDef {

	private static UsuarioController controller;
	private static UsuarioDAO dao;
	private static List<Usuario> list;

	public UsuarioStepDef() throws Exception {
		controller = UsuarioController.getInstance();
		dao = UsuarioDAO.getInstance();
	}

	@Given("^BDD iniciada sin datos$")
	public void inicioBDD() throws Throwable {
		dao.clearStore();
	}

	@When("^Yo busco un valor$")
	public void getUsuario() throws Throwable {
		list = controller.getUsuarios();
		System.out.println(list);
	}

	@Then("^El resultado es vacio$")
	public void listaUsuariosVacia() throws Throwable {
		assertTrue(list.isEmpty());
		//assertFalse("prueba",list.isEmpty());
	}
	
	@When("^Yo creo un usuario con nick <key> y rol <rol>$")
	public void setUsuario(String key,String name, String rol) throws Throwable {
		controller.createUsuario(key,name, rol);
	}

	@Then("^yo puedo encontrarlo con el nick <key> y el rol <rol>$")
	public void the_result_its_empty() throws Throwable {
		List<Usuario> list = new ArrayList<Usuario>();
		list = controller.getUsuarios();
		assertTrue(list.size() == 1);
	}

//	@When("^I create a resource with key (\\d+) and value (.+)$")
////	public void i_create_a_resource_with_key_and_value(int _id, String cliente, String sociedad,
////			String sectorEmpresarial, String tipoProyecto, Object fechaInicio,
////			int duracionMeses, String denominacion, String resumenProyecto,
////			String problematicaCliente, String solucionGfi,
////			String tecnologias, int fteTotales, String imagenProyecto,
////			String certificado, int[] regPedidoAsociadoReferencia,
////			String responsableComercial, String responsableTecnico,
////			String creadorReferencia, String codigoQr, String estado) throws Throwable {
////		controller.createReferencia(new Referencia(_id,cliente,sociedad,sectorEmpresarial,tipoProyecto,fechaInicio,duracionMeses,denominacion,
////				resumenProyecto,problematicaCliente,solucionGfi,tecnologias,fteTotales,imagenProyecto,certificado,regPedidoAsociadoReferencia,responsableComercial,responsableTecnico,creadorReferencia,codigoQr,estado));
////	}
//	
//
}
