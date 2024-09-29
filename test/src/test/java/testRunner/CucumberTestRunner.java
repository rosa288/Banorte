package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags="", features={"src/test/resources/features/banorte.feature"},
glue= {"stepsDefinitions"},
plugin= {"pretty","html:test-output/htmlCucumberReport.html"})
public class CucumberTestRunner extends AbstractTestNGCucumberTests{
	
	
	
	
	

}
