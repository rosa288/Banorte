package utils;

import static java.time.Duration.ofSeconds;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Colors;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelper {

	public static final int DEFAULT_TIMEOUT_SECONDS = 10;

	private final WebDriverWait wait;

	private final WebDriver driver;

	public WaitHelper(WebDriver driver) {
		wait = new WebDriverWait(driver, ofSeconds(DEFAULT_TIMEOUT_SECONDS));
		this.driver = driver;
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @param ExpectedCondition
	 * @return N/A
	 ***/
	public void waitForExpectedCondition(ExpectedCondition<?> condition) {
		wait.until(condition);
	}

	/**@author Rosa Azucena Marcelino
	 * @param WebElement
	 * @return WebElement
	 ***/
	public WebElement waitForElementToBeClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**@author Rosa Azucena Marcelino
	 * @param By
	 * @return WebElement
	 ***/
	public WebElement waitForElementToBeClickable(By by) {
		return wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param WebElement
	 * @return WebElement
	 ***/
	public WebElement waitForElementToBeVisible(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param  List<WebElement>
	 * @return List<WebElement>
	 ***/
	public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
		return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param By
	 * @return List<WebElement>
	 ***/
	public WebElement waitForElementToBeVisible(By by) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param WebElement, int
	 * @return WebElement
	 ***/
	public WebElement waitForElementToBeVisible(WebElement element, int timeToWait) {
		WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeToWait));
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param By, int
	 * @return WebElement
	 ***/
	public WebElement waitForElementToBeVisible(By elementBy, int timeToWait) {
		WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeToWait));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param String, int
	 * @return WebElement
	 ***/
	public WebElement waitForElementToBeVisible(String xpath, int timeToWait) {
		WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeToWait));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param By, int
	 * @return WebElement
	 ***/
	public WebElement waitForElementToBeClickable(By by, int timeToWait) {
		WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeToWait));
		return wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	
	/**@author Rosa Azucena Marcelino
	 * @param WebElement, int
	 * @return WebElement
	 ***/
	public WebElement waitForElementToBeClickable(WebElement element, int timeToWait) {
		WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeToWait));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param WebElement, int
	 * @return N/A
	 ***/
	public void waitForAnyTextToBePresent(WebElement element) {
		wait.until((ExpectedCondition<Boolean>) driver -> element.getText().length() != 0);
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param WebElement
	 * @return N/A
	 ***/
	public void waitForAnyValueToBePresent(WebElement element) {
		wait.until((ExpectedCondition<Boolean>) driver -> element.getAttribute("value").length() != 0);
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param By, Colors
	 * @return N/A
	 ***/
	public void waitForBackgroundColor(By by, Colors color) {
		int waitTime = DEFAULT_TIMEOUT_SECONDS;
		String actualColor;
		while (!(actualColor = getColorOrEmpty(by)).equals(color.toString()) && waitTime-- > 0) {
			Sleep.seconds(1);
		}
		Assert.assertEquals("Element not in correct color", actualColor, color.toString());
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param By
	 * @return String
	 ***/
	private String getColorOrEmpty(By by) {
		try {
			return driver.findElement(by).getCssValue("background-color");
		} catch (StaleElementReferenceException e) {
			return "";
		}
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param N/A
	 * @return N/A
	 ***/
	public void waitForPageLoaded() {
		wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
				.executeScript("return document.readyState").equals("complete"));
		Sleep.seconds(1);
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param WebElement
	 * @return N/A
	 ***/
	public void waitForElementToBeNotVisible(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	/**@author Rosa Azucena Marcelino
	 * @param By
	 * @return N/A
	 ***/
	public void waitForElementToBeNotVisible(By by) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
}
