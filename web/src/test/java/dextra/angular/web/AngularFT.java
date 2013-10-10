package dextra.angular.web;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AngularFT {

	public class InnerHtmlExpectation implements ExpectedCondition<Boolean> {

		private String elementId;
		private String result;

		public InnerHtmlExpectation(String id) {
			this.elementId = id;
		}

		@Override
		public Boolean apply(WebDriver a) {
			try {
				String script = "return document.getElementById('" + elementId + "').innerHTML;";
				result = (String) ((JavascriptExecutor) driver).executeScript(script);
				return StringUtils.isNotBlank(result);
			} catch (StaleElementReferenceException e) {
				return null;
			}
		}

		public String getElementId() {
			return elementId;
		}

		public String getResult() {
			return result;
		}

	}

	private String baseUrl;
	private WebDriver driver;
	private WebDriverWait defaultWait;

	@After
	public void after() {
		driver.quit();
	}

	@Before
	public void before() {

		String port = System.getProperty("http.port", "8080");
		String browser = System.getProperty("browser.executable", "/usr/local/bin/firefox-10.0.1-ESR");

		baseUrl = "http://localhost:" + port + "/jetzt";

		DesiredCapabilities opts = new DesiredCapabilities();
		FirefoxBinary binary = new FirefoxBinary(new File(browser));
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages", "pt-br,en-us,en");
		profile.setPreference("network.http.phishy-userpass-length", 255);
		driver = new FirefoxDriver(binary, profile, opts);
		driver.manage().window().setSize(new Dimension(1024, 768));

		defaultWait = new WebDriverWait(driver, 10);

	}

	@Test
	public void test() {

		driver.navigate().to(baseUrl + "/reset");
		driver.navigate().to(baseUrl + "/test/runner.html");

		defaultWait.withTimeout(10, TimeUnit.MINUTES);
		InnerHtmlExpectation expectation = new InnerHtmlExpectation("json");
		defaultWait.until(expectation);

		JsonNode json = convertToObject(expectation);

		Object error = analyzeResult(json);

		assertEquals("There are test failures", error, false);

	}

	private boolean analyzeResult(JsonNode json) {

		boolean error = false;

		Iterator<JsonNode> tests = json.get("children").getElements();

		while (tests.hasNext()) {

			JsonNode test = tests.next();
			Iterator<JsonNode> specs = test.get("specs").getElements();

			printTest(test);

			while (specs.hasNext()) {

				JsonNode spec = specs.next();
				String specStatus = spec.get("status").asText();

				printSpec(spec);

				if (!specStatus.equalsIgnoreCase("success")) {

					Iterator<JsonNode> steps = spec.get("steps").getElements();

					while (steps.hasNext()) {
						JsonNode step = steps.next();
						printStep(step);
					}

					error = true;

				}

			}

		}

		return error;

	}

	private JsonNode convertToObject(InnerHtmlExpectation expectation) {
		try {
			return new ObjectMapper().readValue(expectation.getResult(), JsonNode.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printSpec(JsonNode spec) {

		String name = spec.get("name").asText();
		long duration = spec.get("duration").asLong();
		String status = spec.get("status").asText().toUpperCase();

		String text = String.format("%s - %s - duration %s ms", name, status, duration);
		System.out.println(text);

	}

	private void printStep(JsonNode step) {

		String name = step.get("name").asText();
		long duration = step.get("duration").asLong();
		String status = step.get("status").asText().toUpperCase();

		String text = String.format("	%s - %s - duration %s ms", name, status, duration);

		if (!status.equalsIgnoreCase("success")) {
			String expected = step.get("error").asText();
			text += String.format(" - %s ", expected);
		}

		System.out.println(text);

	}

	private void printTest(JsonNode test) {

		System.out.println("");

		String name = test.get("name").asText();

		String text = String.format("==================== %s ====================", name);

		System.out.println(text);

	}

}