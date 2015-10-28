package com.example.resource;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;

import com.example.controllers.ResourceController;
import com.example.dao.ResourceDAO;
import com.example.models.Referencia;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ResourceStepDef {

	private static ResourceController controller;
	private static ResourceDAO dao;
	private static List<Referencia> list;

	public ResourceStepDef() throws Exception {
		controller = ResourceController.getInstance();
		dao = ResourceDAO.getInstance();
	}

	@Given("^A empty resource store$")
	public void a_empty_store() throws Throwable {
		dao.clearStore();
	}

	@When("^I search all resources$")
	public void i_search_all_resources() throws Throwable {
		list = controller.getResources();
		System.out.println(list);
	}

	@Then("^The result its empty$")
	public void the_result_its_empty() throws Throwable {
		assertTrue(list.isEmpty());
	}

	@When("^I create a resource with key (\\d+) and value (.+)$")
	public void i_create_a_resource_with_key_and_value(int _id, String cliente, String sociedad,
			String sectorEmpresarial, String tipoProyecto, Object fechaInicio,
			int duracionMeses, String denominacion, String resumenProyecto,
			String problematicaCliente, String solucionGfi,
			String tecnologias, int fteTotales, String imagenProyecto,
			String certificado, int[] regPedidoAsociadoReferencia,
			String responsableComercial, String responsableTecnico,
			String creadorReferencia, String codigoQr, String estado) throws Throwable {
		controller.createResource(new Referencia(_id,cliente,sociedad,sectorEmpresarial,tipoProyecto,fechaInicio,duracionMeses,denominacion,
				resumenProyecto,problematicaCliente,solucionGfi,tecnologias,fteTotales,imagenProyecto,certificado,regPedidoAsociadoReferencia,responsableComercial,responsableTecnico,creadorReferencia,codigoQr,estado));
	}

	/*@Then("^I can find it in the store with key (\\d+) and value (.*?)$")
	public void i_can_find_it_in_the_store(int key, String value) throws Exception {
		Assert.assertEquals(value, controller.getResource(key).getValue());
	}*/

	/*@Then("^I create a duplicated resource with key (\\d+) and value value (\\d+)$")
	public void i_create_a_duplicated_resource_with_key_and_value(int key, String value) {
		try {
			controller.createResource(new Referencia(key, value));
			controller.createResource(new Referencia(key, value));
			fail("The system don't may allow to insert 2 times the same assert");
		} catch (Exception e) {

		}

	}*/

}
