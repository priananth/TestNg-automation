package com.training.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.BaseTest;
import com.training.page.LeadsPage;
import com.training.utilities.ScreenShot;

public class LeadsTest extends BaseTest {

    WebDriver driver;
    LeadsPage leadsPage;
    ScreenShot screenshot;
    String testName;

    @BeforeMethod
    public void Before() {
        driver = super.getDriver();
        leadsPage = new LeadsPage(driver);
        screenshot = new ScreenShot();
    }

    @Test
    public void leadsTab_TC20() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        leadsPage.clickOnAllTabArrow();
        System.out.println("Starting TC20: Verify Leads Tab");

        leadsPage.clickLeadsTab();

        Assert.assertTrue(driver.getTitle().contains("Leads"), "Leads page not displayed");
        System.out.println("TC20 Passed: Leads tab verified.");
    }

    @Test
    public void leadsDropdown_TC21() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC21: Verify Leads Dropdown");
        leadsPage.clickOnAllTabArrow();
        leadsPage.clickLeadsTab();
        List<String> options = leadsPage.getLeadsDropdownOptions();
        Assert.assertFalse(options.isEmpty(), "Leads dropdown is empty");

        System.out.println("TC21 Passed: Leads dropdown verified.");
    }

    @Test
    public void defaultView_TC22() throws IOException, InterruptedException {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC22: Verify Default View Persistence");
        leadsPage.clickOnAllTabArrow();
        leadsPage.clickLeadsTab();
        leadsPage.selectLeadView("Today's Leads");

        leadsPage.logout();


        leadsPage.enterEmail();
        leadsPage.enterPassword();
        leadsPage.login();
        leadsPage.clickLeadsTab();
        leadsPage.clickGoButton(); 

        String selectedView = leadsPage.getSelectedView();
        Assert.assertEquals(selectedView, "Today's Leads", "View selection did not persist after logout/login.");
        System.out.println("TC22 Passed: Default view persistence verified.");
    }

    @Test
    public void todaysLeads_TC23() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC24: Verify Today's Leads View");
        leadsPage.clickOnAllTabArrow();
        leadsPage.clickLeadsTab();
        leadsPage.selectLeadView("Today's Leads");
        leadsPage.clickGoButton(); 
        String selectedView = leadsPage.getSelectedView();
        Assert.assertEquals(selectedView, "Today's Leads", "Today's Leads view not selected.");

        System.out.println("TC23 Passed: Today's Leads view verified.");
    }

    @Test
    public void createLead_TC24() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC25: Create New Lead");
        leadsPage.clickOnAllTabArrow();
        leadsPage.clickLeadsTab();
        leadsPage.clickNewLead();

        String lastName = "chandra";
        leadsPage.createLead("Bharathy", lastName, "ABCD");

        Assert.assertTrue(driver.getTitle().contains(lastName), "Lead was not created successfully");
        System.out.println("TC24 Passed: New Lead created.");
    }

    @AfterMethod
    public void tearDown() {
        screenshot.takeScreenShot(driver, testName);
    }
}
