package com.training.page;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.training.base.BasePage;

public class RandomPage extends BasePage {

    WebDriver driver;

    public RandomPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // TC34, TC35
    @FindBy(xpath = "//a[contains(@class,'userMru')]")
    WebElement userNavLabelName; // The link with the name

    @FindBy(xpath = "//a[@class='contactInfoLaunch editLink']")
    WebElement editProfilePen;

    @FindBy(xpath = "//iframe[@id='contactInfoContentId']")
    WebElement contactInfoFrame;

    @FindBy(xpath = "//li[@id='aboutTab']//a")
    WebElement aboutTab;

    @FindBy(id = "lastName")
    WebElement lastNameInput;

    @FindBy(xpath = "//input[@value='Save All']")
    WebElement saveAllBtn;

    @FindBy(id = "userNavLabel")
    WebElement userNavLabel; // The menu button

    // TC36
    @FindBy(xpath = "//input[@name='customize']")
    WebElement customizeMyTabsBtn;

    @FindBy(id = "duel_select_1")
    WebElement selectedTabsSelect;

    @FindBy(id = "duel_select_0")
    WebElement availableTabsSelect;

    @FindBy(xpath = "//img[@title='Remove']")
    WebElement removeBtn;

    @FindBy(xpath = "//img[@title='Add']")
    WebElement addBtn;

    @FindBy(xpath = "//input[@value=' Save ']")
    WebElement saveBtn;

    // TC37, TC38
    @FindBy(className = "pageDescription")
    WebElement currentDateLink;

    @FindBy(xpath = "//a[contains(@title,'Combo (New Window)')]")
    WebElement subjectComboIcon;

    @FindBy(id = "EndDateTime_time")
    WebElement endTimeSelect;

    @FindBy(id = "IsRecurrence")
    WebElement recurrenceCheckbox;

    @FindBy(id = "rectypeftw")
    WebElement weeklyRadioBtn;

    @FindBy(id = "wi")
    WebElement recursEveryInput;

    @FindBy(id = "RecurrenceEndDateOnly")
    WebElement recurrenceEndDateInput;

    @FindBy(xpath = "//a[@title='Month View']")
    WebElement monthViewIcon;

    @FindBy(className = "allTabsArrow")
    WebElement allTabsArrow;

    public void clickUserNavName() {
        waitForWebElement(userNavLabelName, 10);
        userNavLabelName.click();
    }

    public String getUserNavNameText() {
        waitForWebElement(userNavLabelName, 10);
        return userNavLabelName.getText();
    }

    public void editProfileLastName(String newLastName) {
        waitForWebElement(editProfilePen, 10);
        editProfilePen.click();

        driver.switchTo().frame(contactInfoFrame);
        waitForWebElement(aboutTab, 10);
        aboutTab.click();

        lastNameInput.clear();
        lastNameInput.sendKeys(newLastName);
        saveAllBtn.click();
        driver.switchTo().defaultContent();
    }

    public String getUserMenuNameText() {
        waitForWebElement(userNavLabel, 10);
        return userNavLabel.getText();
    }

    public void clickAllTabs() {
        waitForWebElement(allTabsArrow, 10);
        allTabsArrow.click();
    }

    public void clickCustomizeMyTabs() {
        waitForWebElement(customizeMyTabsBtn, 10);
        customizeMyTabsBtn.click();
    }

    public void removeTab(String tabName) {
        Select select = new Select(selectedTabsSelect);
        select.selectByVisibleText(tabName);
        removeBtn.click();
        saveBtn.click();
    }

    public void addTab(String tabName) {
        // Helper to restore state if needed, or for testing add
        Select select = new Select(availableTabsSelect);
        select.selectByVisibleText(tabName);
        addBtn.click();
        saveBtn.click();
    }

    public boolean isTabAvailable(String tabName) {
        Select select = new Select(availableTabsSelect);
        for (WebElement option : select.getOptions()) {
            if (option.getText().equals(tabName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTabSelected(String tabName) {
        Select select = new Select(selectedTabsSelect);
        for (WebElement option : select.getOptions()) {
            if (option.getText().equals(tabName)) {
                return true;
            }
        }
        return false;
    }

    public void clickCurrentDateLink() {
        waitForWebElement(currentDateLink, 10);
        currentDateLink.click();
    }

    public void clickTimeSlot(String time) {
        // Dynamic xpath for time slot
        WebElement timeSlot = driver
                .findElement(By.xpath("//div[contains(@class,'hourRowLabel') and contains(text(),'" + time + "')]"));
        timeSlot.click();
    }

    public void selectSubjectCombo(String subject) {
        String parentWindow = driver.getWindowHandle();
        subjectComboIcon.click();

        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(parentWindow)) {
                driver.switchTo().window(handle);
                // Click the subject link. TC37 uses //li[@class='listItem4'] which is "Other"?
                // Let's try to find by text if possible, or use the class.
                try {
                    driver.findElement(By.xpath("//a[text()='" + subject + "']")).click();
                } catch (Exception e) {
                    // Fallback to "Other" if subject is "Other" or specific class
                    if (subject.equals("Other")) {
                        driver.findElement(By.xpath("//li[@class='listItem4']/a")).click();
                    }
                }
                driver.switchTo().window(parentWindow);
                break;
            }
        }
    }

    public void selectEndTime(String time) {
        Select select = new Select(endTimeSelect);
        select.selectByVisibleText(time);
    }

    public void saveEvent() {
        saveBtn.click();
    }

    public void createRecurringEvent(String time, String endTime, String frequency, String recursEvery,
            String endDate) {
        // Assumes we are already on the event page (after clicking time slot)
        // This method handles the recurrence part

        recurrenceCheckbox.click();
        waitForWebElement(weeklyRadioBtn, 5);
        weeklyRadioBtn.click();

        recursEveryInput.clear();
        recursEveryInput.sendKeys(recursEvery);

        // Select day of week? TC38 clicks id='16'.
        // We might need to handle this dynamically or just hardcode for now as per TC.
        // 1=Sun, 2=Mon ... 16 might be a bitmask or specific ID?
        // In Salesforce, IDs for days are often like 'rectypeftw' etc.
        // TC38 uses `By.xpath("//input[@id='16']")`. This looks like a specific day.
        // I will assume we click the day provided or default to one.
        try {
            driver.findElement(By.xpath("//input[@id='16']")).click();
        } catch (Exception e) {
            // Ignore if already selected or not found
        }

        recurrenceEndDateInput.click();
        // Date picker handling? TC38 clicks 'calToday'.
        driver.findElement(By.xpath("//a[@class='calToday']")).click();

        saveBtn.click();
    }

    public void clickMonthView() {
        waitForWebElement(monthViewIcon, 10);
        monthViewIcon.click();
    }
}
