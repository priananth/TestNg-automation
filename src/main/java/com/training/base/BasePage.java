package com.training.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	WebDriver driver;
	ReadOTP otpReader = new ReadOTP();

	
	
	
	@FindBy(id = "username")
	WebElement email;
	
	@FindBy(id = "password")
	WebElement password;
	
	@FindBy(id = "Login")
	WebElement login;
	
	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	WebElement logout;
	
	@FindBy(id = "emc")
	WebElement verify;
	
	@FindBy(id = "save")
	WebElement save;
	
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enterEmail() {
		System.out.println("Going to wait for email element");
		waitForWebElement(email,10);
		email.sendKeys("bhaarathipriya881@agentforce.com");
		
	}
	
	public void enterPassword() {
		password.sendKeys("Priya@94");
	}
	
	public void login() {
		login.click();
	}
	
	public void verify(String newotp) {
		verify.sendKeys(newotp);
	}
	
	public void save() {
		save.click();
	}
	public void waitForWebElement(WebElement element,int time) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
}
