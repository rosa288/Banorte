package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import common.Common;

public class Example {
WebDriver driver;
	
	
	
	@Test
	public void testOpenBrowser() {
		Common obj = new Common();
		driver = obj.openBrowser("https://www.google.com/", "chrome");
		driver.quit();
	}
	
}
