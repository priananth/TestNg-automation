package com.training.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.training.utilities.PropertiesFile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	WebDriver driver;
	BasePage base;
	ReadOTP otpReader = new ReadOTP();
	PropertiesFile property = new PropertiesFile();
	
	public WebDriver getDriver() {
		return this.driver;
	}

	@BeforeClass
	public WebDriver launchApplication() throws IOException {
		if (driver == null) {
			String newotp = null;
			String browser = property.getProperties("browser");
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                
                String downloadPath = System.getProperty("user.dir") + File.separator + "downloads";
                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", downloadPath);
                chromePrefs.put("download.prompt_for_download", false);
                options.setExperimentalOption("prefs", chromePrefs);
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			base = new BasePage(driver);
			String url = property.getProperties("url");
			try {
				// 2. Perform Login and Trigger OTP
				driver.get(url);
				base.enterEmail();
				base.enterPassword();
				base.login();

				// 3. Wait for and fetch OTP
				Thread.sleep(10000);
				newotp = otpReader.getOTP();

				if (newotp == null) {
					throw new Exception("OTP retrieval failed: newotp was null.");
				}
				System.out.printf("Retrieved and using OTP: %s\n", newotp);

				// 4. Enter OTP and Verify
				base.verify(newotp);
				base.save();
				Thread.sleep(5000);

				// Critical check: Ensure login was successful
				Assert.assertTrue(driver.getCurrentUrl().contains("home.jsp"),
						"Login verification failed in BaseTest setup.");
			} catch (Exception e) {
				System.err.println("FATAL ERROR in BaseTest: Login prerequisite failed. All tests will be skipped.");
				// Throw a RuntimeException to halt TestNG execution gracefully for this class
				throw new RuntimeException("Setup failure: Could not complete Salesforce login.", e);
			}
		}
		return driver;
	}

	// @AfterClass
	public void closeApplication() {
		System.out.println("Completing Base Cleanup: Quitting driver.");
		if (driver != null) {
			driver.quit();
		}
	}
}
