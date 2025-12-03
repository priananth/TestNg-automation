
	package com.training.base;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;

	import io.github.bonigarcia.wdm.WebDriverManager;


	public class BT {
		WebDriver driver;
		ReadOTP otpReader = new ReadOTP();
		
		
		public WebDriver launchApplication() {
			if(driver == null) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.get("https://login.salesforce.com/");
		}
		return driver;
		}

		public void closeApplication() {
			driver.close();
			driver = null;
		}
		
		@BeforeClass
	    public void setupAndLogin() {
	        System.out.println("Starting Base Setup: Initializing driver and logging in...");
	        
	        // 1. Setup Driver
	        WebDriverManager.chromedriver().setup();
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--incognito");
	        driver = new ChromeDriver(options);
	        driver.manage().window().maximize();

	        String newotp = null;
	        
	        try {
	            // 2. Perform Login and Trigger OTP
	            driver.get("https://login.salesforce.com/");
	            driver.findElement(By.id("username")).sendKeys("bhaarathipriya881@agentforce.com");
	            driver.findElement(By.id("password")).sendKeys("Priya@94");
	            driver.findElement(By.xpath("//input[contains(@class,'r4 fl mr8')]")).click();
	            driver.findElement(By.id("Login")).click();

	            // 3. Wait for and fetch OTP
	            Thread.sleep(10000); 
	            newotp = otpReader.getOTP();
	            
	            if (newotp == null) {
	                throw new Exception("OTP retrieval failed: newotp was null.");
	            }
	            System.out.printf("Retrieved and using OTP: %s\n", newotp);

	            // 4. Enter OTP and Verify
	            driver.findElement(By.id("emc")).sendKeys(newotp);
	            driver.findElement(By.id("save")).click();
	            Thread.sleep(5000); 
	            
	            // Critical check: Ensure login was successful
	            Assert.assertTrue(driver.getCurrentUrl().contains("home.jsp"), "Login verification failed in BaseTest setup.");

	        } catch (Exception e) {
	            System.err.println("FATAL ERROR in BaseTest: Login prerequisite failed. All tests will be skipped.");
	            // Throw a RuntimeException to halt TestNG execution gracefully for this class
	            throw new RuntimeException("Setup failure: Could not complete Salesforce login.", e);
	        }
	    }

	    @AfterClass
	    public void teardown() {
	        System.out.println("Completing Base Cleanup: Quitting driver.");
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}

