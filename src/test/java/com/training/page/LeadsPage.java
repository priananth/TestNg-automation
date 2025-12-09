package com.training.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.training.base.BasePage;

public class LeadsPage extends BasePage {

    WebDriver driver;

    public LeadsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    
    @FindBy(className = "allTabsArrow")
    WebElement allTabArraw;
    
    @FindBy(xpath = "//a[contains(@class,'listRelatedObject leadBlock title')]")
    WebElement leadsTab;

    @FindBy(xpath = "//select[@id='fcf' and @name='fcf' ]")
    WebElement leadsDropdown;

    @FindBy(xpath = "//input[@value=' Go! ' and @name='go' ]")
    WebElement goBtn;

    @FindBy(xpath = "//input[@value=' New ']")
    WebElement newLeadBtn;

    @FindBy(id = "userNavLabel")
    WebElement userNavLabel;

    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    WebElement logoutLink;

    @FindBy(xpath = "//input[@id='name_firstlea2' and @name='name_firstlea2' ]")
    WebElement firstNameInput;

    @FindBy(xpath = "//input[@id='name_lastlea2' and @name='name_lastlea2' ]")
    WebElement lastNameInput;

    @FindBy(xpath = "//input[@id='lea3' and @name='lea3' ]")
    WebElement companyNameInput;

    @FindBy(xpath = "//td[@id='bottomButtonRow']//input[@value=' Save ']")
    WebElement saveBtn;
    
    public void clickOnAllTabArrow() {
    	allTabArraw.click();
    }

    public void clickLeadsTab() {
        waitForWebElement(leadsTab, 10);
        leadsTab.click();
    }

    public List<String> getLeadsDropdownOptions() {
        waitForWebElement(leadsDropdown, 10);
        Select select = new Select(leadsDropdown); 
        List<WebElement> options = select.getOptions();
        List<String> textOptions = new ArrayList<>();
        for (WebElement option : options) {
            textOptions.add(option.getText());
        }
        return textOptions;
    }

    public void selectLeadView(String viewName) {
        waitForWebElement(leadsDropdown, 10);
        Select select = new Select(leadsDropdown);
        select.selectByVisibleText(viewName);
    }

    public String getSelectedView() {
        waitForWebElement(leadsDropdown, 10);
        Select select = new Select(leadsDropdown);
        return select.getFirstSelectedOption().getText();
    }

    public void clickGoButton() {
        waitForWebElement(goBtn, 10);
        goBtn.click();
    }

    public void clickNewLead() {
        waitForWebElement(newLeadBtn, 10);
        newLeadBtn.click();
    }

    public void logout() {
        waitForWebElement(userNavLabel, 10);
        userNavLabel.click();
        waitForWebElement(logoutLink, 10);
        logoutLink.click();
    }

    public void createLead(String firstName, String lastName, String company) {
        waitForWebElement(firstNameInput, 10);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        companyNameInput.sendKeys(company);
        saveBtn.click();
    }
}
