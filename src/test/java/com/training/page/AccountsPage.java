package com.training.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.training.base.BasePage;

public class AccountsPage extends BasePage {

    WebDriver driver;

    public AccountsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(xpath = "//a[contains(@class,'listRelatedObject accountBlock title')]")
    WebElement accountsTab;

    @FindBy(xpath = "//input[contains(@value,'New')]")
    WebElement newAccountBtn;

    @FindBy(xpath = "//div[contains(@class,'requiredInput')]//input[@id='acc2']")
    WebElement accountNameInput;

    @FindBy(xpath = "//select[contains(@name,'acc6')]")
    WebElement typeDropdown;

    @FindBy(xpath = "//select[contains(@name,'00NgL00001zNIzu')]")
    WebElement priorityDropdown;

    @FindBy(xpath = "//td[@id='bottomButtonRow']//input[@value=' Save ']")
    WebElement saveBtn;

    @FindBy(xpath = "//a[contains(text(),'Create New View')]")
    WebElement createNewViewLink;

    @FindBy(xpath = "//input[contains(@name,'fname')]")
    WebElement viewNameInput;

    @FindBy(xpath = "//input[contains(@name,'devname')]")
    WebElement viewUniqueNameInput;

    @FindBy(xpath = "//input[contains(@data-uidsfdc,'3')]")
    WebElement saveViewBtn;

    @FindBy(xpath = "//select[contains(@class,'title')]")
    WebElement viewDropdown;

    @FindBy(xpath = "//select[@id='fcf' and @name='fcf']")
    WebElement viewDropdownId;

    @FindBy(xpath = "//a[contains(text(),'Edit')]")
    WebElement editLink;

    @FindBy(xpath = "//select[@class='column' and @id='fcol1' ]")
    WebElement filterFieldDropdown;

    @FindBy(xpath = "//select[@class='operator' and @id='fop1' ]")
    WebElement filterOperatorDropdown;

    @FindBy(xpath = "//input[@name='fval1' and @title='Value 1' ]")
    WebElement filterValueInput;

    @FindBy(xpath = "//select[@id='colselector_select_0' and @name='colselector_select_0' ]")
    WebElement availableFieldsDropdown;

    @FindBy(xpath = "//a[@id='colselector_select_0_right']")
    WebElement addFieldBtn;

    @FindBy(xpath = "//input[@value=' Save ' and @data-uidsfdc='5' ]")
    WebElement saveEditViewBtn;

    @FindBy(xpath = "//a[contains(text(),'Merge Accounts')]")
    WebElement mergeAccountsLink;

    @FindBy(xpath = "//input[@name='srch' and @title='Find Accounts' ]")
    WebElement searchInput;

    @FindBy(xpath = "//input[@value='Find Accounts' and @title='Find Accounts' ]")
    WebElement findAccountsBtn;

    @FindBy(xpath = "//div[@class='pbBottomButtons']//input[@value=' Next ']")
    WebElement nextBtn;

    @FindBy(xpath = "//div[@class='pbTopButtons']//input[@value=' Merge ']")
    WebElement mergeBtn;

    @FindBy(xpath = "//a[contains(text(),'Accounts with last activity > 30 days')]")
    WebElement lastActivityLink;

    @FindBy(xpath = "//div[@id='ext-gen148']")
    WebElement dateFieldDropdown;

    @FindBy(xpath = "//div[@id='ext-gen275']")
    WebElement dateFieldOptionsContainer;

    @FindBy(id = "ext-gen153")
    WebElement fromDateIcon;

    @FindBy(id = "ext-gen285")
    WebElement fromDateCalendar;

    @FindBy(className = "ext-gen294")
    WebElement fromDateTodayBtn;

    @FindBy(id = "ext-gen155")
    WebElement toDateIcon;

    @FindBy(id = "ext-gen300")
    WebElement toDateCalendar;

    @FindBy(className = "ext-gen308")
    WebElement toDateTodayBtn;

    @FindBy(xpath = "//button[@id='ext-gen50' and text()='Save' ]")
    WebElement saveReportBtn;

    @FindBy(xpath = "//input[@name='reportName' and @type='text' ]")
    WebElement reportNameInput;

    @FindBy(xpath = "//input[@name='reportDevName' and @type='text' ]")
    WebElement reportUniqueNameInput;

    @FindBy(xpath = "//button[@id='ext-gen330' and text()='Save and Run Report' ]")
    WebElement saveAndRunReportBtn;

    @FindBy(xpath = "//input[@value='Remind Me Later']")
    WebElement remindMeLaterBtn;

    public void clickAccountsTab() {
        waitForWebElement(accountsTab, 10);
        accountsTab.click();
    }

    public void clickNewAccount() {
        waitForWebElement(newAccountBtn, 10);
        newAccountBtn.click();
    }

    public void createAccount(String name, String type, String priority) {
        waitForWebElement(accountNameInput, 10);
        accountNameInput.sendKeys(name);

        Select typeSelect = new Select(typeDropdown);
        typeSelect.selectByVisibleText(type);

        Select prioritySelect = new Select(priorityDropdown);
        prioritySelect.selectByVisibleText(priority);

        saveBtn.click();
    }

    public void clickCreateNewView() {
        waitForWebElement(createNewViewLink, 10);
        createNewViewLink.click();
    }

    public void createNewView(String viewName, String uniqueName) {
        waitForWebElement(viewNameInput, 10);
        viewNameInput.sendKeys(viewName);
        viewUniqueNameInput.clear(); // Ensure it's empty or handle auto-fill
        viewUniqueNameInput.sendKeys(uniqueName);
        saveViewBtn.click();
    }

    public String getSelectedView() {
        waitForWebElement(viewDropdown, 10);
        Select select = new Select(viewDropdown);
        return select.getFirstSelectedOption().getText();
    }

    public void selectView(String viewName) {
        waitForWebElement(viewDropdownId, 10);
        Select select = new Select(viewDropdownId);
        select.selectByVisibleText(viewName);
    }

    public void clickEditLink() {
        waitForWebElement(editLink, 10);
        editLink.click();
    }

    public void editView(String newViewName, String field, String operator, String value) {
        waitForWebElement(viewNameInput, 10);
        viewNameInput.clear();
        viewNameInput.sendKeys(newViewName);

        Select fieldSelect = new Select(filterFieldDropdown);
        fieldSelect.selectByVisibleText(field);

        Select operatorSelect = new Select(filterOperatorDropdown);
        operatorSelect.selectByVisibleText(operator);

        filterValueInput.clear();
        filterValueInput.sendKeys(value);

        Select availableSelect = new Select(availableFieldsDropdown);
        // Assuming "Last Activity" is always available or passed as arg, hardcoding for
        // now as per TC13
        availableSelect.selectByVisibleText("Last Activity");
        addFieldBtn.click();

        saveEditViewBtn.click();
    }

    public void clickMergeAccounts() {
        waitForWebElement(mergeAccountsLink, 10);
        mergeAccountsLink.click();
    }

    public void searchAccount(String accountName) {
        waitForWebElement(searchInput, 10);
        searchInput.sendKeys(accountName);
        findAccountsBtn.click();
    }

    public void selectAccountsToMerge() {
        // Logic to select first two checkboxes if available
        // This is a simplified implementation based on TC14 intent
        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[contains(@id,'cid')]"));
        if (checkboxes.size() >= 2) {
            if (!checkboxes.get(0).isSelected())
                checkboxes.get(0).click();
            if (!checkboxes.get(1).isSelected())
                checkboxes.get(1).click();
        }
        nextBtn.click();
    }

    public void mergeAccounts() {
        waitForWebElement(mergeBtn, 10);
        mergeBtn.click();
        driver.switchTo().alert().accept();
    }

    public void clickLastActivityReport() {
        waitForWebElement(lastActivityLink, 10);
        lastActivityLink.click();
    }

    public void handleRemindMeLater() {
        try {
            if (remindMeLaterBtn.isDisplayed()) {
                remindMeLaterBtn.click();
            }
        } catch (Exception e) {
            // Ignore if element not found
        }
    }

    public void createReport(String dateField, String reportName, String reportUniqueName) {
        handleRemindMeLater();
        waitForWebElement(dateFieldDropdown, 10);
        dateFieldDropdown.click();

        // Wait for options to be visible
        // This part might need adjustment based on actual DOM behavior for ExtJS
        // components
        WebElement option = driver
                .findElement(By.xpath("//div[contains(@class,'x-combo-list-item') and text()='" + dateField + "']"));
        option.click();

        fromDateIcon.click();
        waitForWebElement(fromDateCalendar, 5);
        fromDateTodayBtn.click();

        toDateIcon.click();
        waitForWebElement(toDateCalendar, 5);
        toDateTodayBtn.click();

        saveReportBtn.click();

        waitForWebElement(reportNameInput, 10);
        reportNameInput.sendKeys(reportName);
        reportUniqueNameInput.clear();
        reportUniqueNameInput.sendKeys(reportUniqueName);

        saveAndRunReportBtn.click();
    }
}
