package banorte;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.Common;

public class Inicio {
	Common obj;
	
	@FindBy(xpath="//div[@id='banorteHomeModal']//*[@id='changingImage']")
	WebElement img_esteMesPatrio;
	
	By btn_cerrarBanner= By.xpath("//div[@id='banorteHomeModal']//*[@id='closeHomeModal']");
	
	@FindBy(id="userid")
	WebElement txt_usuario;
	
	@FindBy(xpath="//*[@class='logo_login']/parent::*/parent::*")
	WebElement title_bancoEnLinea;
	
	
	@FindBy(id="btn_lgn_entrar")
	WebElement btn_entrar;
	
	@FindBy(className="logo")
	WebElement title_banorte;
	
	@FindBy(xpath="//*[@class='search_trigger']")
	WebElement btn_buscar;
	
	@FindBy(xpath="//input[@id='termino_value']")
	WebElement txt_comienzaTuBusquedaAqui;
	
	@FindBy(xpath=" //*[@id='termino_dropdown']//div[@ng-if='!matchClass']")
	List<WebElement> dd_comienzaTuBusquedaAqui;
	
	@FindBy(xpath="//*[contains(@class,'clickeable')]")
	List<WebElement> dd_resultadoBusqueda;
	
	@FindBy(xpath="//iframe[@id='GSAFrame']")
	WebElement frame_buscar;
	
	
	public Inicio(WebDriver driver) {
		obj = new Common(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void verificarBannerVisible() {
		obj.getWaitHelper().waitForElementToBeVisible(img_esteMesPatrio);
		obj.takeScreenShot();
	}
	
	public void cerrarBanner() {
		obj.click(btn_cerrarBanner,10);
		obj.getWaitHelper().waitForElementToBeVisible(txt_usuario);
	}
	
	public void validarAccesoUsuario() {
		obj.getWaitHelper().waitForElementToBeVisible(txt_usuario);
		obj.getWaitHelper().waitForElementToBeClickable(btn_entrar);
		obj.takeScreenShot();
	}
	
	public void validarTituloHomePage() {
		obj.getWaitHelper().waitForElementToBeVisible(title_banorte);
		obj.takeScreenShot();
	}
	
	public void buscarEnPagina(String busqueda) throws Exception {
		obj.click(btn_buscar);
		obj.switchToFrame(frame_buscar);
		obj.getWaitHelper().waitForElementToBeVisible(txt_comienzaTuBusquedaAqui);
		obj.type(txt_comienzaTuBusquedaAqui, busqueda);
		obj.keywordBackSpace(txt_comienzaTuBusquedaAqui);
		obj.selectOption(dd_comienzaTuBusquedaAqui, busqueda);
		obj.switchToDefaultFrame();
	}
	
	public void validarBusqueda(String busqueda) {
		obj.switchToFrame(frame_buscar);
		obj.verifyTextExistInList(dd_resultadoBusqueda, busqueda);
		obj.switchToDefaultFrame();
	}
	
	public void clickEnBusqueda(String busqueda) throws Exception {
		obj.switchToFrame(frame_buscar);
		obj.selectOption(dd_resultadoBusqueda, busqueda);
		obj.switchToDefaultFrame();
	}
	
}
