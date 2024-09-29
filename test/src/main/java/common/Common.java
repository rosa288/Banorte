package common;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.checkerframework.checker.units.qual.N;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ReportUtils;
import utils.Sleep;
import utils.WaitHelper;

public class Common extends ReportUtils {
	private WaitHelper waitHelper;
	private WebDriver driver;
	private String OSName = System.getProperty("os.name");
	private String projectPath = System.getProperty("user.dir");
	public ReportUtils reporter;

	public Common(WebDriver driver) {
		this.driver = driver;
		this.waitHelper = new WaitHelper(driver);
	}// end Common

	public Common() {
	}

	private static final Logger LOG = LoggerFactory.getLogger(Common.class);

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param String, String
	 * @return WebDriver
	 ***/
	public WebDriver openBrowser(String url, String browserName) {
		Properties prop = new Properties();
		prop.setProperty("log4j.rootLogger", "WARN");
		PropertyConfigurator.configure(prop);
		Sleep.seconds(1);
		switch (browserName) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			WebDriverManager.chromedriver().cachePath("chromedriver").avoidOutputTree().setup();
			driver = new ChromeDriver(chromeOptions);
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			WebDriverManager.edgedriver().cachePath("edgedriver").avoidOutputTree().setup();
			driver = new EdgeDriver(edgeOptions);
			break;
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			WebDriverManager.firefoxdriver().clearDriverCache();
//			WebDriverManager.firefoxdriver().cachePath("firefoxdriver").avoidOutputTree().driverVersion("129.0").setup();
			WebDriverManager.firefoxdriver().cachePath("firefoxdriver").avoidOutputTree().setup();
			driver = new FirefoxDriver(firefoxOptions);
			break;
		default:

			LOG.error("Browser Not Supported");
			System.exit(0);
		}
		this.reporter = new ReportUtils();
		this.waitHelper = new WaitHelper(driver);
		driver.manage().window().maximize();
		try {
			driver.get(url);
		} catch (Exception e) {
			driver.get(url);
		}
		return driver;
	}

	public WaitHelper getWaitHelper() {
		return waitHelper;
	}

	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param WebElement
	 * @return N/A
	 ***/
	public void click(WebElement element) {
		try {
			highlighElement(element);
			element.click();
		} catch (WebDriverException e) {
			waitHelper.waitForElementToBeClickable(element);
			highlighElement(element);
			element.click();
		}
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param WebElement, int
	 * @return N/A
	 ***/
	public void click(WebElement element, int customTimeoutSeconds) {
		try {
			element.click();
		} catch (WebDriverException e) {
			waitHelper.waitForElementToBeClickable(element, customTimeoutSeconds);
			element.click();
		}
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param By
	 * @return N/A
	 ***/
	public void click(By locator) {
		try {
			highlighElement(locator);
			findElement(locator).click();
		} catch (WebDriverException e) {
			waitHelper.waitForElementToBeClickable(locator);
			highlighElement(locator);
			findElement(locator).click();
		}
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param By, int
	 * @return N/A
	 ***/
	public void click(By locator, int customTimeoutSeconds) {
		try {
			highlighElement(locator);
			driver.findElement(locator).click();
		} catch (WebDriverException e) {
			waitHelper.waitForElementToBeClickable(locator, customTimeoutSeconds);
			driver.findElement(locator).click();
		}
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param String, Object
	 * @return N/A
	 ***/
	public void click(String xpathTemplate, Object... arg) {
		By by = By.xpath(format(xpathTemplate, arg));
		click(by);
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param WebElement, String
	 * @return N/A
	 ***/
	public void type(WebElement element, String text) {
		waitHelper.waitForElementToBeClickable(element);
		typeInElement(element, text);
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param WebElement, String, int
	 * @return N/A
	 ***/
	public void type(WebElement element, String text, int customTimeoutSeconds) {
		waitHelper.waitForElementToBeClickable(element, customTimeoutSeconds);
		typeInElement(element, text);
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param By, String
	 * @return N/A
	 ***/
	public void type(By by, String text) {
		waitHelper.waitForElementToBeClickable(by);
		WebElement element = driver.findElement(by);
		typeInElement(element, text);
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param WebElement, String
	 * @return N/A
	 ***/
	public void typeInElement(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param WebElement
	 * @return String
	 ***/
	public String getAttributeValue(WebElement element) {
		waitHelper.waitForElementToBeVisible(element);
		return element.getAttribute("value");
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param WebElement
	 * @return boolean
	 ***/
	public boolean isDisplayed(By by) {
		waitHelper.waitForPageLoaded();
		return !driver.findElements(by).isEmpty();
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param WebElement
	 * @return boolean
	 ***/
	public boolean isDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param By
	 * @return boolean
	 ***/
	public void hoverOn(By by) {
		Actions actions = new Actions(driver);
		WebElement element = driver.findElement(by);
		actions.moveToElement(element).perform();
	}

	/**
	 * @author Rosa Azucena Marcelino
	 * @Date 10-Sep-2024
	 * @param By
	 * @return boolean
	 ***/
	public boolean isEnabled(By by) {
		try {
			WebElement element = driver.findElement(by);
			return element.isEnabled();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * @Description verify links in a web page
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter By
	 * @return N/A
	 *
	 **/
	@SuppressWarnings("deprecation")
	public void checkPageLinks(By locator) {
		List<WebElement> links = findElements(locator);
		String url = "";
		List<String> brokenLinks = new ArrayList<String>();
		List<String> validLinks = new ArrayList<String>();

		HttpURLConnection httpConnection = null;
		int responseCode = 200;
		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {
			url = it.next().getAttribute("href");
			if (url == null || url.isEmpty()) {
				reporter(url, " URL is not configured or is empty");
				continue;
			} // end if
			try {
				httpConnection = (HttpURLConnection) (new URL(url).openConnection());
				httpConnection.setRequestMethod("HEAD");
				httpConnection.connect();
				responseCode = httpConnection.getResponseCode();

				if (responseCode >= 400) {
					reporter("ERROR BRONKEN LINK: --> ", url);
					reporter("STATUS CODE: --> ", String.valueOf(responseCode));
					brokenLinks.add(url);
				} else {
					reporter("VALID LINK: --> ", url);
					reporter("STATUS CODE: --> ", String.valueOf(responseCode));
					validLinks.add(url);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} // end while

		reporter("VALID LINKS: --> ", String.valueOf(validLinks.size()));
		reporter("INVALID LINKS: --> ", String.valueOf(brokenLinks.size()));

		if (brokenLinks.size() > 0) {
			for (String link : brokenLinks) {
				reporter("link", String.valueOf(link));
			}
		}
	}// checkPageLinks

	/**
	 * @Description Encontrar lista de webElement por locator
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param By
	 * @return List WebElement
	 * @exception
	 **/
	public List<WebElement> findElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * @Description Encontrar webElement por locator
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param N/A
	 * @return By
	 * @exception
	 **/
	public WebElement findElement(By locator) {
		reporter("Localizar Elemento", driver.findElement(locator));
		return driver.findElement(locator);
	}

	/**
	 * @Description Obtener texto por locator
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param By
	 * @return String
	 * @exception
	 **/
	public String getText(By locator) {
		waitHelper.waitForAnyTextToBePresent(findElement(locator));
		String text = findElement(locator).getText();
		reporter("El texto obtenido es", text);
		return text;
	}

	/**
	 * @Description Obtener texto por locator
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param By
	 * @return String
	 * @exception
	 **/
	public String getText(WebElement element) {
		waitHelper.waitForAnyTextToBePresent(element);
		String text = element.getText();
		reporter("El texto obtenido es", text);
		return text;
	}

	/**
	 * @Description Escribir texto en web element
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param String, By
	 * @return N/A
	 * @exception
	 **/
	public void type(String inputText, By locator) {
		highlighElement(locator);
		findElement(locator).clear();
		findElement(locator).sendKeys(inputText);
		reporter("Fue ingresado", inputText);
	}

	/**
	 * @Description Escribir texto en web element
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param String, By
	 * @return N/A
	 * @exception
	 **/
	public void type(String inputText, WebElement element) {
		highlighElement(element);
		element.clear();
		element.sendKeys(inputText);
		reporter("Fue ingresado", inputText);
	}

	/**
	 * @Description open to URL
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param String
	 * @return N/A
	 * @exception N/A
	 **/
	public void openUrl(String url) {
		driver.get(url);
		reporter("El URL fue abierto :", url);
	}

	/**
	 * @Description Navigate to new URL
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param String
	 * @return N/A
	 * @exception N/A
	 **/
	public void navigateToUrl(String url) {
		driver.navigate().to(url);
		reporter("El URL fue abierto :", url);
	}

	/**
	 * @Description Open new tab
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param N/A
	 * @return N/A
	 * @exception N/A
	 **/
	public void openNewTab() {
		driver.switchTo().newWindow(WindowType.TAB);
	}

	/**
	 * @Description Open new window
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param N/A
	 * @return N/A
	 * @exception N/A
	 **/
	public void openNewWindow() {
		driver.switchTo().newWindow(WindowType.WINDOW);
	}

	/**
	 * @Description get Operating system name
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param N/A
	 * @return N/A
	 * @exception
	 **/
	public String getOSName() {
		if (OSName.contains("Windows")) {
			OSName = "Windows";
		} else if (OSName.contains("Mac")) {
			OSName = "Mac";
		} else if (OSName.contains("Linux")) {
			OSName = "Linux";
		}

		return OSName;
	}

	/**
	 * @Description Tomar screnshot
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param N/A
	 * @return N/A
	 * @exception IOException
	 **/
	public void takeScreenShot() {
		OSName = getOSName();
		String path = "";
		switch (OSName) {
		case "MAC":
		case "Linux":
			path = projectPath + "/test-output/screenshots/";
			break;
		case "Windows":
			path = projectPath + "\\test-output\\screenshots\\";
			break;
		}

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {

			String fullPath = path + "screen_" + formater.format(calendar.getTime()) + ".png";
			FileUtils.copyFile(srcFile, new File(fullPath));

			reporter("************EL screenshot fue guardado en [" + fullPath + " ]********************");
			reporter("<br> <img src='" + fullPath + "' height='400' width='800'/></br>");

		} catch (IOException e) {
			e.printStackTrace();

		}

	}// end screenshot

	/**
	 * @Description Verificar un texto esperado
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param String,String
	 * @return N/A
	 * @exception
	 **/
	public void validateExpectedText(String expected, String actual) {
		try {
			Assert.assertEquals(removeBlankSpaces(removeLineBreaks(expected)),
					removeBlankSpaces(removeLineBreaks(actual)));
			reporter("Expected text  [" + expected + " ] IS EQUAL TO [ " + actual + " ]");
		} catch (AssertionError e) {
			Assert.fail(
					"text are not matching <b> expeected:  [ " + expected + " ] and actaual: [ " + actual + " ] <b>");
		}
	}

	/**
	 * @throws Exception
	 * @Description Remove Line breaks from String variable
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter String
	 * @return String
	 *
	 **/
	public String removeLineBreaks(String text) {
		return text.replaceAll("\n", "");
	}

	/**
	 * @throws Exception
	 * @Description Remove blank spaces from String variable
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter String
	 * @return String
	 *
	 **/
	public String removeBlankSpaces(String text) {
		return text.replaceAll(" ", "");
	}

	/**
	 * @Description click web element si conicide con el texto esperado
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param By, String
	 * @return N/A
	 * @exception
	 **/
	public void selectElementByValue(By locator, String expectedText) {

		List<WebElement> elements = findElements(locator);

		for (int i = 0; i < elements.size(); i++) {

			if (i >= elements.size()) {
				Assert.fail("El elemento que buscas en la lista no existe: [ " + expectedText + " ]");
			}

			if (elements.get(i).getText().equals(expectedText)) {
				reporter("Element in the list was clicked", elements.get(i).getText());
				elements.get(i).click();
				break;
			}

		} // end for

	}

	/**
	 * @Description seleccionar Elemento por index
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param By, int
	 * @return N/A
	 * @exception StaleElementReferenceException, NoSuchElementException
	 **/
	public void selectDropDownByIndex(By locator, int index) {
		WebElement dropdown = findElement(locator);
		Select action = new Select(dropdown);

		try {
			action.selectByIndex(index);
			reporter("Element was selected by Index", String.valueOf(index));
		} catch (StaleElementReferenceException e) {
			Assert.fail("Cannot select element: [ " + dropdown.toString() + " ]");
		} catch (NoSuchElementException e) {
			Assert.fail("Cannot select element: [ " + dropdown.toString() + " ]");
		}

	}

	/**
	 * @Description seleccionar Elemento por Value
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param By, int
	 * @return N/A
	 * @exception StaleElementReferenceException,NoSuchElementException
	 **/
	public void selectDropDownByValue(By locator, String value) {
		WebElement dropdown = findElement(locator);
		Select action = new Select(dropdown);

		try {
			action.selectByValue(value);
			reporter("Element was selected by Value", value);
		} catch (StaleElementReferenceException e) {
			Assert.fail("Cannot select element: [ " + dropdown.toString() + " ]");
		} catch (NoSuchElementException e) {
			Assert.fail("Cannot select element: [ " + dropdown.toString() + " ]");
		}

	}

	/**
	 * @Description seleccionar Elemento por Value
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @param By, int
	 * @return N/A
	 * @exception StaleElementReferenceException, NoSuchElementException
	 **/
	public void selectDropDownByVisibleText(By locator, String expectedText) {
		WebElement dropdown = findElement(locator);
		Select action = new Select(dropdown);

		try {
			action.selectByVisibleText(expectedText);
			reporter("Element was selected by Visible text", expectedText);
		} catch (StaleElementReferenceException e) {
			Assert.fail("Cannot select element: [ " + dropdown.toString() + " ]");
		} catch (NoSuchElementException e) {
			Assert.fail("Cannot select element: [ " + dropdown.toString() + " ]");
		}

	}

	/**
	 * @throws N/A
	 * @Description quite driver session
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter N/A
	 * @return N/A
	 * @throws IOException
	 * @implNote N/A
	 */
	@SuppressWarnings("deprecation")
	public void closeBrowser() throws IOException {
		try {
			driver.quit();
		} finally {
			if (System.getProperty("os.name").contains("Windows")) {
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe /T");
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
				Runtime.getRuntime().exec("taskkill /F /IM msedgedriver.exe /T");
			}
		}
	}

	/**
	 * @throws JsonGenerationException, JsonMappingException, IOException
	 * @Description Read JSON file
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter String, String
	 * @return JsonNode
	 * @implNote nodeTree.path("fieldName").asText()
	 */
	public JsonNode readJsonFileByNode(String jsonpath, String nodeName) {
		JsonNode nodeTree = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(new File(jsonpath));
			// Get Name
			nodeTree = root.path(nodeName);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodeTree;
	}

	/**
	 * @throws JsonGenerationException, JsonMappingException, IOException
	 * @Description Read JSON file
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter String, String
	 * @return JsonNode
	 * @implNote nodeTree.path("fieldName").asText()
	 */
	public JsonNode readJsonFile(String jsonpath) {
		JsonNode root = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			root = mapper.readTree(new File(jsonpath));
			// Get Name
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return root;
	}

	/**
	 * @throws N/A
	 * @Description This method is take today date plus the amount of days that you
	 *              are give by parameter and returned
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter int
	 * @return String
	 * @implNote N/A
	 */
	public String getDate(int amountDays) {

		Date myDate = new Date();
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd");// ("YYYY-MM-dd"); MM/dd/YYYY

		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, amountDays);

		myDate = cal.getTime();

		String date = df.format(myDate);

		return date;

	}

	/**
	 * @throws Exception
	 * @Description generate random name flag true unique name, flag false name in
	 *              the list
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter Boolean
	 * @return String
	 */
	public String getRandomName(Boolean flag) {
		Date date = new Date();
		String[] people = new String[] { "Sergio", "Ivan", "John", "Marcus", "Henry", "Ismael", "Nishant", "Rakesh",
				"Carlos", "Felix", "Miriam", "Diana", "Adriana", "Alejandro", "Gaby", "Caro", "Melisa", "Aimee",
				"Nataly", "Fernando", "Thomas", "Fidel", "Javier", "Ricardo", "Monica", "Nidia", "Eddy", "Evert", "Ben",
				"Anu", "Rosa", "Azucena" };
		List<String> names = Arrays.asList(people);
		Collections.shuffle(names);
		int index = new Random().nextInt(names.size());
		String randomName = names.get(index);
		if (flag == true) {
			DateFormat hourdateFormat = new SimpleDateFormat("HHmmssddMMyyyyssss");
			randomName = randomName + hourdateFormat.format(date);
		}
		sleep(1000);
		return randomName;

	}

	/**
	 * @throws Exception
	 * @Description generate random last name flag true unique name, flag false name
	 *              in the list
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter Boolean
	 * @return String
	 */
	public String getRandomLastName(Boolean flag) {
		Date date = new Date();
		String[] people = new String[] { "Ramones", "Velez", "Flores", "Williams", "Hetfield", "Abbot", "Anderson",
				"Avalos", "Ortiz", "Serrato", "Hernandez", "Pushkarna", "Kim", "Reddy", "Paramasivam", "Molina",
				"Soria", "Heredia", "Torres", "Melchor", "Alladi", "Velazquez", "Kumar", "Montesano", "Marcelino",
				"Cruz" };
		List<String> lastNames = Arrays.asList(people);
		Collections.shuffle(lastNames);
		int index = new Random().nextInt(lastNames.size());
		String randomName = lastNames.get(index);
		if (flag == true) {
			DateFormat hourdateFormat = new SimpleDateFormat("HHmmssddMMyyyy");
			randomName = randomName + hourdateFormat.format(date);
		}

		return randomName;

	}

	/**
	 * @throws Exception
	 * @Description set text in the webElement
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter WebElement, String
	 * @return N/A
	 */
	public void keywordEnter(By locator) throws Exception {
		try {
			WebElement element = findElement(locator);
			element.sendKeys(Keys.ENTER);
			reporter("Enter was sent");
		} catch (Exception e) {
			Assert.fail("It's not possible to send Enter");
			e.printStackTrace();
		}
	}

	/**
	 * @throws N/A
	 * @Description Click webElement with JScript.
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter WebElement
	 * @return N/A
	 */
	public void clickJScript(WebElement element) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].click();", element);

			if (element.toString().contains("By.") == true) {
				reporter(
						"Web element was clicked by locatior ---> <b> " + element.toString().split("By.")[1] + "</b> ");
			} else if (element.toString().contains("->") == true) {
				reporter("Web element was clicked by locatior ---> <b> " + element.toString().split("->")[1] + "</b> ");
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			reporter("ArrayIndexOutOfBoundsException: " + element.toString());
		}

	}

	/**
	 * @throws Exception
	 * @Description scroll in to view webElement
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter WebElement
	 * @return N/A
	 */
	public void scrollJScript(WebElement element) throws Exception {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

			if (element.toString().contains("By.") == true) {
				reporter(
						"Web element was clicked by locatior ---> <b> " + element.toString().split("By.")[1] + "</b> ");
			} else if (element.toString().contains("->") == true) {
				reporter("Web element was clicked by locatior ---> <b> " + element.toString().split("->")[1] + "</b> ");
			}
		} catch (Exception e) {
			reporter("Its not posible to Scroll to the Web element by locatior ---> <b>"
					+ element.toString().split("By.")[1] + "</b>");
		}
	}// end scroll

	/**
	 * @throws Exception
	 * @Description scroll in to view webElementw with Action class
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter WebElement
	 * @return N/A
	 */
	public void scrollAction(By locator) {
		WebElement element = findElement(locator);
		try {

			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.perform();
			if (element.toString().contains("By.") == true) {
				reporter(
						"Web element was clicked by locatior ---> <b> " + element.toString().split("By.")[1] + "</b> ");
			} else if (element.toString().contains("->") == true) {
				reporter("Web element was clicked by locatior ---> <b> " + element.toString().split("->")[1] + "</b> ");
			}
		} catch (Exception e) {
			reporter("Its not posible to Scroll to the Web element by locatior ---> <b>"
					+ element.toString().split("By.")[1] + "</b>");
		}

	}// end scrollAction

	/**
	 * @Description sleep in seconds the executions
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter N/A
	 * @return N/A
	 * @throws N/A
	 */
	public void sleepSeconds(int time) {
		try {

			Thread.sleep(Duration.ofSeconds(time));
		} catch (InterruptedException e) {
			reporter("Cannot wait the millisenconds: ", String.valueOf(time));
		}
	}

	/**
	 * @Description sleep in seconds the executions
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter N/A
	 * @return N/A
	 * @throws N/A
	 */
	public void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			reporter("Cannot wait the millisenconds: ", String.valueOf(milliseconds));
		}
	}

	/**
	 * @throws Exception
	 * @Description wait until element disappear
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter WebElement
	 * @return boolean
	 *
	 **/
	public void waitUtilElementAppear(By locator, int timeout) {
		int i = 0;
		WebElement element = findElement(locator);

		while (i <= timeout) {
			try {
				element.getSize();
				break;

			} catch (NoSuchElementException e) {
				reporter("Element don't appear second: [ " + i + " ] ");
				i++;
				sleep(1000);

			} // end catch
		} // end while
	}// end method

	/**
	 * @throws Exception
	 * @Description wait until element disappear
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter WebElement
	 * @return boolean
	 *
	 **/
	public void highlighElement(By locator) {
		WebElement element = findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		sleep(500);
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
	}

	/**
	 * @throws Exception
	 * @Description wait until element disappear
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter WebElement
	 * @return boolean
	 *
	 **/
	public void highlighElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		sleep(500);
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
	}

	/**
	 * @throws Exception
	 * @Description select option from text list
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter ListWebElement, String
	 * @return N/A
	 */
	public void selectOption(List<WebElement> options, String optionToClick) throws Exception {
		waitHelper.waitForElementsToBeVisible(options);
		List<String> optionsList = new ArrayList<>();

		for (WebElement option : options) {
			optionsList.add(option.getText());
			System.out.println(option.getText());
		}

		if (optionsList.contains(optionToClick)) {
			for (WebElement option : options) {
				if (option.getText().equals(optionToClick)) {
//					option.click();
					clickJScript(option);
					break;
				}
			}
		} else {
			fail("Option is not available");
		}

	}

	/*
	 * @throws Exception
	 * 
	 * @Description select option from text list
	 * 
	 * @Author Rosa Marcelino
	 * 
	 * @Date 10-Sep-2024
	 * 
	 * @Parameter ListWebElement, String
	 * 
	 * @return N/A
	 */
	public List<String> getOptions(List<WebElement> applicationOptions) {
		List<String> options = new ArrayList<>();

		for (WebElement value : applicationOptions) {
			options.add(value.getText());
		}
		options.removeIf(String::isEmpty);
		Collections.sort(options);
		return options;

	}

	/**
	 * @throws Exception
	 * @Description select option from text list
	 * @Author Rosa Marcelino
	 * @Date 10-Sep-2024
	 * @Parameter ListWebElement, String
	 * @return N/A
	 */
	public void verifyTextExistInList(List<WebElement> options, String expectedText) {
		waitHelper.waitForElementsToBeVisible(options);
		List<String> optionsList = new ArrayList<>();

		for (WebElement option : options) {
			optionsList.add(option.getText());
			System.out.println(option.getText());
		}

		if (optionsList.contains(expectedText)) {
			for (WebElement option : options) {
				if (option.getText().equals(expectedText)) {
					reporter("El texto aparece en la lista: [" + option.getText() + " ]");
					break;
				}
			}
		} else {
			fail("Option is not available");
		}

	}

	public void switchToFrame(WebElement iframe) {
		driver.switchTo().frame(iframe);
	}

	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}

	public void cambiarVentana(int pantalla) {
		try {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(pantalla));
		} catch (Exception e) {

		}
	}

	public void keywordBackSpace(WebElement element) throws Exception {
		try {

			element.sendKeys(Keys.BACK_SPACE);
			reporter("Enter was sent");
		} catch (Exception e) {
			Assert.fail("It's not possible to send Enter");
			e.printStackTrace();
		}
	}

}// Common
