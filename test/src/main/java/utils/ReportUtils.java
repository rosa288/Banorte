package utils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
public class ReportUtils {
	boolean logToStandardOut=false;
	
	public void reporter(String message, String value) {
		Reporter.log("<font color='green'>"+message+" </font>[ " + "<font color='black'><b> " + value + "</b></font> ]", logToStandardOut);

	}
	
	public void reporter(String message) {
		Reporter.log("<b> [ " + message + " ] </b>", true);
	}

	public void reporter(String message, WebElement element) {
		try {
			String locator = getFormattedLocator(element);
			Reporter.log("<font color='green'>"+message + " ---> </font><font color='black'>[ " + " <b> " + locator + "</b> ]</font>",logToStandardOut);

		} catch (Exception e) {
			Reporter.log("<font color='red'>Its not posible to find ---> </font><b>" + getFormattedLocator(element) + "</b>", logToStandardOut);
		}
	}

	public void reporter(String message, String value, WebElement element) {
		try {
			String locator = getFormattedLocator(element);
			Reporter.log("<font color='green'>"+message + " ---> </font><font color='red'>[</font> " + " <font color='black'><b> " + value + "</b> </font><font color='red'>]</font>"+"<font color='green'><b> With locator ---> </font><font color='red'>[ </font><font color='black'>"+locator+"</font> <font color='red'>]</b></font>", logToStandardOut);

		} catch (Exception e) {
			Reporter.log("<font color='red'>Its not posible to find ---> </font><b>" + getFormattedLocator(element) + "</b>", logToStandardOut);
		}
	}

	public String getFormattedLocator(WebElement element) {
		String locator=element.toString();
		String formattedLocator="Locator not Found";
		try {

			if (locator.contains("By.") == true) {
				formattedLocator =removeLastCharRegex(locator.split("By.")[1]);
			} else if (locator.contains("->") == true) {
				formattedLocator =removeLastCharRegex(locator.split("->")[1]);

			}
		} catch (Exception e) {
			Reporter.log("<b><font color='red'>Its not posible get locator --->"+formattedLocator+"</font></b>");

		}
		return formattedLocator;

	}

	public String removeLineBreaks(String locator) {
		 return locator.replaceAll("\n", "");
	 }

	public String removeBlankSpaces(String locator) {
		 return locator.replaceAll(" ", "");
	 }

	public String removeLastCharRegex(String s) {
	    return (s == null) ? null : s.replaceAll(".$", "");
	}

	public void fail(String message) {
		Reporter.log("<b><font color='red'>"+message+"</font></b>");
		Assert.fail(message);
	}
}
