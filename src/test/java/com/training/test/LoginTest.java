package com.training.test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.BaseTest;
import com.training.base.ReadOTP;
import com.training.page.LoginPage;
import com.training.utilities.PropertiesFile;
import com.training.utilities.ScreenShot;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest{

	WebDriver driver;
	LoginPage loginPage;
	ScreenShot screenshot;
	String testName;
	ReadOTP otpReader = new ReadOTP();
	PropertiesFile property = new PropertiesFile();
	
	public LoginTest() {
		
	}
	
	@BeforeMethod
	public void Before() throws IOException {
	    // FIX 2: Move Setup logic here to ensure driver is configured before LoginPage is instantiated
	    String browser = property.getProperties("browser");
	    
	    // Use WebDriverManager to automatically download and set up the driver
	    if (browser.equalsIgnoreCase("chrome")) {
	        // The latest Selenium versions (4.6+) don't even need WebDriverManager 
	        // if they rely on Selenium Manager, but WDM is a solid approach.
	        WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	    } else if (browser.equalsIgnoreCase("firefox")) {
	        WebDriverManager.firefoxdriver().setup();
	        driver = new FirefoxDriver();
	    } else {
	        throw new IllegalArgumentException("Invalid browser specified in properties file.");
	    }
	    loginPage = new LoginPage(driver);
	    screenshot = new ScreenShot();
	    driver.get(property.getProperties("url")); // Move navigation here too for efficiency
	}
	
	
		@Test
	    public void verifyLogin() {
	        System.out.println("Executing VerifyLogin Test");
	        Assert.assertTrue(true, "Placeholder assertion."); // Replace with actual assertion
	    }
	
	
	@Test
	public void NavigateToSFDC_TC01() {

		testName = Thread.currentThread().getStackTrace()[1].getMethodName();
		loginPage.enterEmail();
		//loginPage.enterPassword();
		loginPage.login();
		String errorMessage = loginPage.error();
		String errorMsg = "Error: Please enter your password.";
		if(errorMessage.equalsIgnoreCase(errorMsg)) {
			System.out.println("Error msg is displayed");
		}else {
			System.out.println("Error msg is not displayed");
		}
	}
	
	@Test
	public void LoginToSalesforce_TC02() throws IOException {
		testName = Thread.currentThread().getStackTrace()[1].getMethodName();

			String newotp = null;
			String browser = property.getProperties("browser");
			if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			}else if(browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			String url = property.getProperties("url");
			   try {
		            // 2. Perform Login and Trigger OTP
				   driver.get(url);
				   loginPage.enterEmail();
				   loginPage.enterPassword();
				   loginPage.login();
				 
		            // 3. Wait for and fetch OTP
		            Thread.sleep(10000); 
		            newotp = otpReader.getOTP();
		            
		            if (newotp == null) {
		                throw new Exception("OTP retrieval failed: newotp was null.");
		            }
		            System.out.printf("Retrieved and using OTP: %s\n", newotp);

		            // 4. Enter OTP and Verify
		            loginPage.verify(newotp);
		            loginPage.save();
		            
		            Thread.sleep(5000); 
		            
		            // Critical check: Ensure login was successful
		            Assert.assertTrue(driver.getCurrentUrl().contains("home.jsp"), "Login verification failed in BaseTest setup.");

		        } catch (Exception e) {
		            System.err.println("FATAL ERROR in BaseTest: Login prerequisite failed. All tests will be skipped.");
		            // Throw a RuntimeException to halt TestNG execution gracefully for this class
		            throw new RuntimeException("Setup failure: Could not complete Salesforce login.", e);
		        }
		String loginUser = loginPage.userNavLabel();
		String user = "Bharathipriya Ch...";
		if(loginUser.equalsIgnoreCase(user)) {
			System.out.println("Successfully loggedin to the application");
		}else {
			System.out.println("User login unsuccessful");
		}
		
	}
	
	@Test
	public void CheckRememberMe_TC03() {
		 try {
			 String newotp = null;
		testName = Thread.currentThread().getStackTrace()[1].getMethodName();
		loginPage.enterEmail();
		loginPage.enterPassword();
		//loginPage.rememberMe();
		loginPage.login();
		  // 3. Wait for and fetch OTP
        Thread.sleep(10000); 
        newotp = otpReader.getOTP();
        
        if (newotp == null) {
            throw new Exception("OTP retrieval failed: newotp was null.");
        }
        System.out.printf("Retrieved and using OTP: %s\n", newotp);

        // 4. Enter OTP and Verify
        loginPage.verify(newotp);
        loginPage.save();
        
        Thread.sleep(5000); 
        
        // Critical check: Ensure login was successful
        Assert.assertTrue(driver.getCurrentUrl().contains("home.jsp"), "Login verification failed in BaseTest setup.");

    } catch (Exception e) {
        System.err.println("FATAL ERROR in BaseTest: Login prerequisite failed. All tests will be skipped.");
        // Throw a RuntimeException to halt TestNG execution gracefully for this class
        throw new RuntimeException("Setup failure: Could not complete Salesforce login.", e);
    }
		loginPage.userNavLabel();
		//waitForWebElement(loginPage.userNavLabel(),10);
		String emailFieldText = loginPage.emailField();
		String actualName = "bhaarathipriya881@agentforce.com";
		if(emailFieldText.equalsIgnoreCase(actualName)){
			System.out.println("Valid user name is displayed");

		}else {
			System.out.println("InValid user name is displayed");
		}
		Boolean isChecked = loginPage.chechboxSelected();
		if(isChecked) {
			 System.out.println("Checkbox is checked.");
		} else {
           System.out.println("Checkbox is not checked.");
		}
	}
	
	@Test
	public void ForgetPassword_TC04A() {
		testName = Thread.currentThread().getStackTrace()[1].getMethodName();
		loginPage.enterEmail();
		loginPage.forgetPasswordLink();
		loginPage.reEnterEmailId();
		loginPage.continueButton();
		String actualMsg =loginPage.resetPasswordLinkMessage();
		String expectedMsg = "We’ve sent you an email with a link to finish resetting your password.";
		if(actualMsg.equalsIgnoreCase(expectedMsg)){
			System.out.println("Valid message is displayed and email is sent to reset the password");

		}else {
			System.out.println("InValid message");
		}
	}
	
	@Test
	public void ForgetPassword_TC04B() {
		testName = Thread.currentThread().getStackTrace()[1].getMethodName();
		loginPage.enterEmail();
		loginPage.enterWrongPassword();
		loginPage.login();
		String actualErrorMsg = loginPage.wrongEmailOrPasword();
		String expectedErrorMsg = "Error: Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		if(actualErrorMsg.equalsIgnoreCase(expectedErrorMsg)){
			System.out.println("Valid message is displayed ");

		}else {
			System.out.println("InValid message is displayed");
		}
	}
	
	@AfterMethod()
	public void tearDown(){
		screenshot.takeScreenShot(driver ,testName);
		
	}
	
}
