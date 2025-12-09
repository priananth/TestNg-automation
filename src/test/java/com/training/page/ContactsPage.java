package com.training.page;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.training.base.BasePage;

public class ContactsPage extends BasePage {

    WebDriver driver;

    public ContactsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(className = "allTabsArrow")
    WebElement allTabArraw;
    
    @FindBy(xpath = "//a[contains(@class,'listRelatedObject contactBlock title')]")
    WebElement contactsTab;

    @FindBy(xpath = "//input[@value=' New ' and @name='new' ]")
    WebElement newContactBtn;

    @FindBy(xpath = "//input[@id='name_firstcon2' and @name='name_firstcon2' ]")
    WebElement firstNameInput;

    @FindBy(xpath = "//input[@id='name_lastcon2' and @name='name_lastcon2' ]")
    WebElement lastNameInput;

    @FindBy(xpath = "//input[@id='con4' and @name='con4' ]")
    WebElement accountNameInput;

    @FindBy(xpath = "//a[@id='con4_lkwgt' and @title='Account Name Lookup (New Window)' ]")
    WebElement accountNameLookupIcon;

    @FindBy(xpath = "//td[@id='bottomButtonRow']//input[@value=' Save ']")
    WebElement saveBtn;

    @FindBy(xpath = "//td[@id='bottomButtonRow']//input[@value='Save & New']")
    WebElement saveAndNewBtn;

    @FindBy(xpath = "//a[ text()='Create New View' ]")
    WebElement createNewViewLink;

    @FindBy(xpath = "//input[@id='fname' and @name='fname' ]")
    WebElement viewNameInput;

    @FindBy(xpath = "//input[@id='devname' and @name='devname' ]")
    WebElement viewUniqueNameInput;

    @FindBy(xpath = "//td[@class='pbButtonb']//input[@data-uidsfdc='4']")
    WebElement saveViewBtn;

    @FindBy(xpath = "(//input[@value='Cancel'])[2]")
    WebElement cancelViewBtn;

    @FindBy(xpath = "//select[@class='title']")
    WebElement viewDropdown;

    @FindBy(xpath = "//select[@id='fcf' and @name='fcf']")
    WebElement viewDropdownFcf;

    @FindBy(xpath = "(//div[@class='errorMsg'])[1]")
    WebElement errorMessage;

    @FindBy(xpath = "//select[@id='hotlist_mode' and @name='hotlist_mode' ]")
    WebElement recentContactsDropdown;

    public void clickOnAllTabArrow() {
    	allTabArraw.click();
    }
    
    public void clickContactsTab() {
        waitForWebElement(contactsTab, 10);
        contactsTab.click();
    }

    public void clickNewContact() {
        waitForWebElement(newContactBtn, 10);
        newContactBtn.click();
    }

    public void createContact(String firstName, String lastName, String accountName) {
        waitForWebElement(firstNameInput, 10);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);

        // Handle Account Lookup
        String parentWindowHandle = driver.getWindowHandle();
        accountNameLookupIcon.click();

        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String handle : allWindowHandles) {
            if (!handle.equals(parentWindowHandle)) {
                driver.switchTo().window(handle);
                try {
                    // Assuming we search or just pick the first one.
                    // TC26 picks "Acc1". We might need to handle frames if it's a standard lookup.
                    // Using a generic approach to find the link.
                    // TC26 uses: //tr[@class='dataRow even first']//a[text()='Acc1']
                    // We'll try to be robust.
                    driver.switchTo().frame("resultsFrame"); // Standard Salesforce lookup frame
                    driver.findElement(By.xpath("//a[contains(@class,' dataCell ')]")).click(); // Click first available
                } catch (Exception e) {
                    // Fallback or specific handling
                    try {
                        // Try without frame switch or different locator
                        driver.findElement(By.xpath("//a[text()='" + accountName + "']")).click();
                    } catch (Exception ex) {
                        System.out.println("Could not select account in lookup: " + ex.getMessage());
                    }
                }
                driver.switchTo().window(parentWindowHandle);
                break;
            }
        }

        saveBtn.click();
    }

    public void enterContactDetails(String firstName, String lastName, String accountName) {
        waitForWebElement(firstNameInput, 10);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        accountNameInput.sendKeys(accountName);
    }

    public void clickSaveAndNew() {
        waitForWebElement(saveAndNewBtn, 10);
        saveAndNewBtn.click();
    }

    public void clickCreateNewView() {
        waitForWebElement(createNewViewLink, 10);
        createNewViewLink.click();
    }

    public void createNewView(String viewName, String uniqueName) {
        waitForWebElement(viewNameInput, 10);
        viewNameInput.sendKeys(viewName);
        viewUniqueNameInput.clear(); // Ensure empty or handle auto-fill
        viewUniqueNameInput.sendKeys(uniqueName);
        saveViewBtn.click();
    }

    public void createNewViewOnlyUniqueName(String uniqueName) {
        waitForWebElement(viewUniqueNameInput, 10);
        viewUniqueNameInput.sendKeys(uniqueName);
        saveViewBtn.click();
    }

    public void enterViewDetails(String viewName, String uniqueName) {
        waitForWebElement(viewNameInput, 10);
        viewNameInput.sendKeys(viewName);
        viewUniqueNameInput.clear();
        viewUniqueNameInput.sendKeys(uniqueName);
    }

    public void clickCancelInCreateView() {
        waitForWebElement(cancelViewBtn, 10);
        cancelViewBtn.click();
    }

    public String getSelectedView() {
        // Try both locators as they might vary by page state
        try {
            if (viewDropdown.isDisplayed()) {
                Select select = new Select(viewDropdown);
                return select.getFirstSelectedOption().getText();
            }
        } catch (Exception e) {
        }

        try {
            if (viewDropdownFcf.isDisplayed()) {
                Select select = new Select(viewDropdownFcf);
                return select.getFirstSelectedOption().getText();
            }
        } catch (Exception e) {
        }

        return "";
    }

    public void selectView(String viewName) {
        waitForWebElement(viewDropdownFcf, 10);
        Select select = new Select(viewDropdownFcf);
        select.selectByVisibleText(viewName);
    }

    public void selectRecentContactsView(String viewName) {
        waitForWebElement(recentContactsDropdown, 10);
        Select select = new Select(recentContactsDropdown);
        select.selectByVisibleText(viewName);
    }

    public boolean isContactListedInRecent(String contactName) {
        // Simplified check in the recent list table
        try {
            return driver
                    .findElement(
                            By.xpath("//tr[contains(@class,'dataRow')]//th//a[contains(text(),'" + contactName + "')]"))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickContact(String contactName) {
        // Click a contact link in the table
        driver.findElement(By.xpath("//a[text()='" + contactName + "']")).click();
    }

    public String getErrorMessage() {
        waitForWebElement(errorMessage, 10);
        return errorMessage.getText();
    }

    public boolean isViewPresent(String viewName) {
        try {
            Select select = new Select(viewDropdownFcf);
            for (WebElement option : select.getOptions()) {
                if (option.getText().equals(viewName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
