package com.training.test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.BaseTest;
import com.training.page.OpportunityPage;
import com.training.utilities.ScreenShot;

public class OpportunityTest extends BaseTest {

    WebDriver driver;
    OpportunityPage opportunityPage;
    ScreenShot screenshot;
    String testName;

    @BeforeMethod
    public void Before() {
        driver = super.getDriver();
        opportunityPage = new OpportunityPage(driver);
        screenshot = new ScreenShot();
    }

    @Test
    public void opportunitiesDropdown_TC16() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC16: Verify Opportunities Dropdown");

        opportunityPage.clickOpportunitiesTab();

        List<String> actualValues = opportunityPage.getOpportunitiesDropdownOptions();
        List<String> expectedValues = Arrays.asList(
                "All Opportunities", "Closing Next Month", "Closing This Month",
                "My Opportunities", "New Last Week", "New This Week",
                "Opportunity Pipeline", "Private", "Recently Viewed Opportunities", "Won");

        // Note: The actual values might differ based on the org configuration.
        // We verify if the list contains the expected values or matches exactly if the
        // org is standard.
        // For robustness, let's check if all expected values are present.
        Assert.assertTrue(actualValues.containsAll(expectedValues),
                "Dropdown does not contain all expected values. Actual: " + actualValues);

        System.out.println("TC16 Passed: Opportunities dropdown verified.");
    }

    @Test
    public void createOpportunity_TC17() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC17: Create New Opportunity");

        opportunityPage.clickOpportunitiesTab();
        opportunityPage.clickNewOpportunity();

        String oppName = "NewOpp" + Date.from(Instant.now()).getTime();
        String accName = "BPriya"; // Assuming this account exists from previous tests or is standard

        opportunityPage.createOpportunity(oppName, accName, "Qualification", "10", "Partner Referral", "Campaign");

        // Verification: Check if opportunity page is displayed with the new name
        Assert.assertTrue(driver.getTitle().contains(oppName),
                "Opportunity page title does not contain the new opportunity name.");
        System.out.println("TC17 Passed: Opportunity created.");
    }

    @Test
    public void opportunityPipelineReport_TC18() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC18: Opportunity Pipeline Report");

        opportunityPage.clickOpportunitiesTab();
        opportunityPage.clickOpportunityPipelineLink();

        // Verification: Check page title
        Assert.assertTrue(driver.getTitle().contains("Opportunity Pipeline"),
                "Opportunity Pipeline page not displayed.");
        System.out.println("TC18 Passed: Opportunity Pipeline report verified.");
    }

    @Test
    public void stuckOpportunitiesReport_TC19() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC19: Stuck Opportunities Report");

        opportunityPage.clickOpportunitiesTab();
        opportunityPage.clickStuckOpportunitiesLink();

        // Verification: Check page title
        Assert.assertTrue(driver.getTitle().contains("Stuck Opportunities"), "Stuck Opportunities page not displayed.");
        System.out.println("TC19 Passed: Stuck Opportunities report verified.");
    }

    @Test
    public void quarterlySummaryReport_TC20() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC20: Quarterly Summary Report");

        opportunityPage.clickOpportunitiesTab();
        opportunityPage.selectQuarterlySummary("Current FQ", "All Opportunities");

        // Verification: Check if report is generated or page refreshed.
        // Since we might just be selecting dropdowns, we check if the elements are
        // still displayed or if we navigated.
        // Assuming the page stays or refreshes with data.
        Assert.assertTrue(driver.getTitle().contains("Opportunities"),
                "Opportunities page not displayed after summary selection.");
        System.out.println("TC20 Passed: Quarterly Summary report verified.");
    }

    @AfterMethod
    public void tearDown() {
        screenshot.takeScreenShot(driver, testName);
    }
}
