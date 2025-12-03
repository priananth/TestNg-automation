package com.training.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.training.base.BasePage;

public class LoginPage extends BasePage{

	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "username")
	WebElement email;
	
	@FindBy(id = "password")
	WebElement password;
	
	@FindBy(id="Login")
	WebElement login;
	
	@FindBy(xpath = "//div [contains(text(),'Error: Please enter your password.')]")
	WebElement error;
	
	@FindBy(id = "userNavLabel")
	WebElement userNavLabel;
	
	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	WebElement logout;
	
	@FindBy(id = "//input[@id='rememberUn']")
	WebElement remMe;
	
	@FindBy( xpath = "//span[@id='idcard-identity']")
	WebElement  emailField;
	
	@FindBy(xpath = "//input[contains(@class,'r4 fl mr8')]")
	WebElement checkBox;
	
	@FindBy(id = "forgot_password_link")
	WebElement forgetPasswordLink;
	
	@FindBy(xpath = "//input[contains(@class,'input wide mb12 mt8 username')]")
	WebElement username;
	
	@FindBy(xpath = "//input[contains(@value,'Continue')]")
	WebElement contBtn;
	
	@FindBy(xpath = "//p[contains(text(),'We’ve sent you an email with a link to finish resetting your password.')]")
	WebElement resetMsg;
	
	@FindBy(xpath = "//div[contains(text(),'Error: Please check your username and password')]")
	WebElement pwdErrorMsg;
	
	
	public void enterEmail() {
		waitForWebElement(email,10);
		email.sendKeys("bhaarathipriya881@agentforce.com");
	}
	
	public void enterPassword() {
		password.sendKeys("Priya@94");
	}
	
	public void enterWrongPassword() {
		password.sendKeys("Priya");
	}
	
	public void login() {
		login.click();
	}
	
	public String error() {
		return error.getText();
	}
	
	public String userNavLabel() {
		return userNavLabel.getText();
	}
	public void userNavLabel1() {
		userNavLabel.click();
	}

	public void logout() {
		logout.click();
	}
	
	public void rememberMe() {
		remMe.click();
	}
	
	public String emailField() {
		return emailField.getText();
	}
	
	public Boolean chechboxSelected() {
		return checkBox.isSelected();
	}
	
	public void forgetPasswordLink() {
		forgetPasswordLink.click();
	}
	
	public void reEnterEmailId() {
		username.sendKeys("bhaarathipriya881@agentforce.com");
	}
	
	public void continueButton() {
		contBtn.click();
	}
	
	public String resetPasswordLinkMessage() {
		return resetMsg.getText();
	}
	
	public String wrongEmailOrPasword() {
		return pwdErrorMsg.getText();
	}
}
