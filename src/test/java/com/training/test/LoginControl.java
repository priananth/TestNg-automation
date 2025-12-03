package com.training.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.training.base.ReadOTP;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginControl {


	static WebDriver driver;
	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		WebDriver driver = new ChromeDriver(options);

		
		try {
			login(driver);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void login(WebDriver driver) throws InterruptedException {
		
		driver.get("https://login.salesforce.com/");
		WebElement userName = driver.findElement(By.id("username"));
		userName.sendKeys("bhaarathipriya881@agentforce.com");
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("Priya@94");
		
		WebElement checkbox = driver.findElement(By.xpath("//input[contains(@class,'r4 fl mr8')]"));
		checkbox.click();
		
		//login
		WebElement login = driver.findElement(By.id("Login"));
		login.click();
		Thread.sleep(5000);
		ReadOTP read = new ReadOTP();
		String newotp = null;
		try {
			newotp = read.getOTP();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (newotp == null) {
			System.out.println("Issues in getting the otp");
			System.exit(0);
		}
		System.out.printf("OTP is %s\n", newotp);
		
		WebElement otp = driver.findElement(By.id("emc"));
		otp.sendKeys(newotp);
		
		//verify
		WebElement verify = driver.findElement(By.id("save"));
		verify.click();
		Thread.sleep(2000);
		
		
		//WebElement setup = driver.findElement(By.id("setupLink"));
		//setup.click();
		//driver.quit();
	}

}
