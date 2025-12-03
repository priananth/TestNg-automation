package com.training.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.training.base.BasePage;

public class OpportunityPage extends BasePage {

    WebDriver driver;

    public OpportunityPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(xpath = "//a[contains(@class,'listRelatedObject opportunityBlock title')]")
    WebElement opportunitiesTab;

    @FindBy(xpath = "//select[@id='fcf' and @name='fcf' ]")
    WebElement opportunitiesDropdown;

    @FindBy(xpath = "//input[@value=' New ' and @name='new' ]")
    WebElement newOpportunityBtn;

    @FindBy(xpath = "//input[@id='opp3' and @name='opp3' ]")
    WebElement opportunityNameInput;

    @FindBy(xpath = "//input[@id='opp4' and @name='opp4' ]")
    WebElement accountNameInput;

    @FindBy(xpath = "//select[@id='opp11' and @name='opp11' ]")
    WebElement stageDropdown;

    @FindBy(xpath = "//span[@class='dateFormat']")
    WebElement closeDateLink;

    @FindBy(xpath = "//a[@class='calToday']")
    WebElement todayLink; // Assuming clicking date format opens calendar and we pick today or similar

    @FindBy(xpath = "//input[@id='opp12' and @name='opp12' ]")
    WebElement probabilityInput;

    @FindBy(xpath = "//select[@id='opp6' and @name='opp6' ]")
    WebElement leadSourceDropdown;

    @FindBy(xpath = "//a[@id='opp17_lkwgt' and @title='Primary Campaign Source Lookup (New Window)' ]")
    WebElement primaryCampaignSourceLookup;

    @FindBy(xpath = "//td[@id='bottomButtonRow']//input[@value=' Save ']")
    WebElement saveBtn;

    @FindBy(xpath = "//a[text()='Opportunity Pipeline' ]")
    WebElement opportunityPipelineLink;

    @FindBy(xpath = "//a[text()='Stuck Opportunities' ]")
    WebElement stuckOpportunitiesLink;

    @FindBy(xpath = "//select[@id='quarter_q' and @name='quarter_q' ]")
    WebElement intervalDropdown;

    @FindBy(xpath = "//select[@id='open' and @name='open' ]")
    WebElement includeDropdown;

    @FindBy(xpath = "//input[@value='Run Report']")
    WebElement runReportBtn; // Assuming there is a button to run the report after selection

    public void clickOpportunitiesTab() {
        waitForWebElement(opportunitiesTab, 10);
        opportunitiesTab.click();
    }

    public List<String> getOpportunitiesDropdownOptions() {
        waitForWebElement(opportunitiesDropdown, 10);
        Select select = new Select(opportunitiesDropdown);
        List<WebElement> options = select.getOptions();
        List<String> textOptions = new ArrayList<>();
        for (WebElement option : options) {
            textOptions.add(option.getText());
        }
        return textOptions;
    }

    public void clickNewOpportunity() {
        waitForWebElement(newOpportunityBtn, 10);
        newOpportunityBtn.click();
    }

    public void createOpportunity(String oppName, String accName, String stage, String probability, String leadSource,
            String campaignSource) {
        waitForWebElement(opportunityNameInput, 10);
        opportunityNameInput.sendKeys(oppName);

        accountNameInput.sendKeys(accName);

        Select stageSelect = new Select(stageDropdown);
        stageSelect.selectByVisibleText(stage);

        // Handling Date - simplified to clicking the link which often defaults to today
        // or opens calendar
        closeDateLink.click();
        // If a calendar opens, we might need to select a date.
        // For now, assuming clicking the link populates it or we need to click 'Today'
        // in calendar
        try {
            if (driver.findElements(By.xpath("//a[@class='calToday']")).size() > 0) {
                driver.findElement(By.xpath("//a[@class='calToday']")).click();
            }
        } catch (Exception e) {
            // Ignore if calendar interaction varies
        }

        probabilityInput.clear();
        probabilityInput.sendKeys(probability);

        Select leadSourceSelect = new Select(leadSourceDropdown);
        leadSourceSelect.selectByVisibleText(leadSource);

        handleCampaignSourceLookup(campaignSource);

        saveBtn.click();
    }

    private void handleCampaignSourceLookup(String campaignSource) {
        String parentWindow = driver.getWindowHandle();
        primaryCampaignSourceLookup.click();

        try {
            Thread.sleep(2000); // Wait for window to open
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(parentWindow)) {
                driver.switchTo().window(handle);
                // In the lookup window, we need to select the campaign.
                // The original code clicked the first row.
                // We can try to frame this better, but sticking to original logic:
                // "//tr[@class='dataRow even last first']"
                try {
                    // Switch to frame if necessary? Usually lookup windows are top level.
                    // Search frame?
                    // Just wait for element
                    WebElement firstRow = driver.findElement(By.xpath("//a[contains(@class,' dataCell ')]")); // More
                                                                                                              // generic
                                                                                                              // locator
                                                                                                              // for
                                                                                                              // first
                                                                                                              // link in
                                                                                                              // table
                    // Or use the one from TC17
                    // WebElement SelectCampaign= driver.findElement(By.xpath("//tr[@class='dataRow
                    // even last first']"));
                    // The TC17 locator seems specific to a table row structure.
                    // Let's try to find a link inside the results frame/table

                    // Assuming we just click the first available item
                    driver.findElement(
                            By.xpath("//a[@href='javascript:top.window.opener.lookupPick2(0,\"Campaign\",\"0\");']"))
                            .click();
                    // The above is very specific. Let's stick to the TC17 locator if possible or a
                    // safer one.
                    // TC17: //tr[@class='dataRow even last first'] -> click() might not work if
                    // it's a row. usually need to click a link.
                    // Let's assume there is a link in the body.

                    // Reverting to a more standard lookup selection:
                    // Usually there's a frame "resultsFrame"
                    driver.switchTo().frame("resultsFrame");
                    driver.findElement(By.xpath("//a[contains(@class,' dataCell ')]")).click();

                } catch (Exception e) {
                    // Fallback to TC17 style if frame switch fails
                    try {
                        driver.findElement(By.xpath("//tr[contains(@class,'dataRow')]//th//a")).click();
                    } catch (Exception ex) {
                        System.out.println("Could not select campaign in lookup window: " + ex.getMessage());
                    }
                }

                driver.switchTo().window(parentWindow);
                break;
            }
        }
    }

    public void clickOpportunityPipelineLink() {
        waitForWebElement(opportunityPipelineLink, 10);
        opportunityPipelineLink.click();
    }

    public void clickStuckOpportunitiesLink() {
        waitForWebElement(stuckOpportunitiesLink, 10);
        stuckOpportunitiesLink.click();
    }

    public void selectQuarterlySummary(String interval, String include) {
        waitForWebElement(intervalDropdown, 10);
        Select intervalSelect = new Select(intervalDropdown);
        intervalSelect.selectByVisibleText(interval);

        Select includeSelect = new Select(includeDropdown);
        includeSelect.selectByVisibleText(include);

        // Check if we need to click 'Run Report' or if it auto-refreshes.
        // TC20 just selects them. Usually there is a 'Run Report' button next to it.
        // Adding a click if button exists
        try {
            if (runReportBtn.isDisplayed()) {
                runReportBtn.click();
            }
        } catch (Exception e) {
            // Button might not be there or auto-submit
        }
    }
}
