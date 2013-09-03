package dextra.angular.web;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
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

	protected WebDriver driver;

	protected WebDriverWait defaultWait;

	@After
	public void after() {
		driver.quit();
	}

	@Before
	public void before() {
		String browser = getBrowser();
		if (browser.contains("firefox")) {
			driver = setUpFirefox();
		}
		if (browser.contains("phantomjs")) {
			driver = setUpPhantomjs();
		}
		driver.navigate().to(getUrl("/reset"));
		defaultWait = new WebDriverWait(driver, 10);
	}

	public WebDriver setUpFirefox() {
		DesiredCapabilities opts = new DesiredCapabilities();
		FirefoxBinary binary = new FirefoxBinary(new File("/usr/local/bin/firefox-10.0.1-ESR"));
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages", "pt-br,en-us,en");
		profile.setPreference("network.http.phishy-userpass-length", 255);
		WebDriver driver = new FirefoxDriver(binary, profile, opts);
		driver.manage().window().setSize(new Dimension(1024, 768));
		return driver;
	}

	@Test
	public void test() {

		driver.navigate().to(getUrl("/test/runner.html"));

		defaultWait.withTimeout(1, TimeUnit.MINUTES);
		InnerHtmlExpectation expectation = new InnerHtmlExpectation("json");
		defaultWait.until(expectation);

		String error = driver.findElement(By.cssSelector(".status-error")).getText();
		String failure = driver.findElement(By.cssSelector(".status-failure")).getText();

		Assert.assertEquals(expectation.getResult(), "0 Failures", failure);
		Assert.assertEquals(expectation.getResult(), "0 Errors", error);

	}

	protected String getUrl(String path) {
		return "http://localhost:" + getHttpPort() + "/angular/" + path;
	}

	private String getBrowser() {
		return System.getProperty("browser.executable", "/usr/local/bin/firefox-10.0.1-ESR");
	}

	private String getHttpPort() {
		return System.getProperty("http.port", "8080");
	}

	private WebDriver setUpPhantomjs() {
		String browser = System.getProperty("browser.executable", "../tools/phantomjs");
		DesiredCapabilities opts = new DesiredCapabilities();
		opts.setJavascriptEnabled(true);
		opts.setCapability("takesScreenshot", false);
		opts.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, browser);
		WebDriver driver = new PhantomJSDriver(opts);
		driver.manage().window().setSize(new Dimension(1024, 768));
		return driver;
	}

}
