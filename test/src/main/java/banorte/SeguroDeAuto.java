package banorte;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.Common;

public class SeguroDeAuto {
	Common obj;
	
	@FindBy(xpath="//article[@class='editordetextoenriquecido']/h1")
	WebElement titulo_SeguroDeAuto;
	
	@FindBy(id="pulseChatButton")
	WebElement btn_necesitaAyuda;
	
	public SeguroDeAuto(WebDriver driver) {
		obj = new Common(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	public void validarTituloDeSeguroDeAuto(String title) {
		obj.cambiarVentana(1);
		obj.getWaitHelper().waitForElementToBeVisible(titulo_SeguroDeAuto);
		obj.takeScreenShot();
	}
	
	
	public void validarNecesitaAyuda() {

		obj.getWaitHelper().waitForElementToBeVisible(btn_necesitaAyuda);

	}
	
	
	
	
	
}
