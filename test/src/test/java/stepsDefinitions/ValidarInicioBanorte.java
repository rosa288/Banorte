package stepsDefinitions;

import java.io.IOException;

import banorte.Inicio;
import banorte.SeguroDeAuto;
import common.Common;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValidarInicioBanorte {
	Common obj;

	@Given("Abrir la pagina de Banorte")
	public void abir_la_pagina_de_banorte() {
		obj = new Common();
		obj.openBrowser("https://www.banorte.com/", "chrome");
		
	}

	@When("La pagina este cargada")
	public void la_pagina_este_cargada() {
		obj.getWaitHelper().waitForPageLoaded();
	}

	@Then("Validar el pagina Inicio banorte")
	public void validar_el_pagina_Inicio_banorte() {
		Inicio banorte = new Inicio(obj.getDriver());
		banorte.validarAccesoUsuario();
	}

	@Then("Cerrar el explorador")
	public void cerrar_el_explorador() throws IOException {
		obj.getWaitHelper().waitForPageLoaded();
		obj.closeBrowser();
	}
	
	
	@Then("Validar Titulo Principal")
	public void validar_Titulo_Principal() {
		Inicio banorte = new Inicio(obj.getDriver());
		banorte.validarTituloHomePage();
	}
	
	@Then("buscar en pagina principal")
	public void buscar_en_pagina_principal() {
		Inicio banorte = new Inicio(obj.getDriver());
		banorte.validarTituloHomePage();
	}
	
	@Then("Buscar en pagina Seguro de Auto")
	public void buscar_en_pagina_seguro_de_auto() throws Exception {
		Inicio banorte = new Inicio(obj.getDriver());
		banorte.buscarEnPagina("seguro auto");
	}
	
	@Then("Validar resultado de busqueda")
	public void validar_resultado_de_busqueda() {
		Inicio banorte = new Inicio(obj.getDriver());
		banorte.validarBusqueda("Seguro de Auto");
		
	}
	@Then("Validar pagina seguros")
	public void validar_pagina_seguros() throws Exception {
		Inicio banorte = new Inicio(obj.getDriver());
		banorte.clickEnBusqueda("Seguro de Auto");
		SeguroDeAuto seguro = new SeguroDeAuto(obj.getDriver());
		seguro.validarTituloDeSeguroDeAuto("Seguro de Auto");
	}
	
	
	@Then("Valida necesita ayuda")
	public void validar_necesita_ayuda() throws Exception {
		SeguroDeAuto seguro = new SeguroDeAuto(obj.getDriver());
		seguro.validarNecesitaAyuda();
	}

}
