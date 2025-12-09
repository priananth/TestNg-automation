package com.training.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.BaseTest;
import com.training.page.RandomPage;
import com.training.utilities.ScreenShot;

public class RandomTest extends BaseTest {

    WebDriver driver;
    RandomPage randomPage;
    ScreenShot screenshot;
    String testName;

    @BeforeMethod
    public void Before() {
        driver = super.getDriver();
        randomPage = new RandomPage(driver);
        screenshot = new ScreenShot();
    }

    @Test
    public void verifyProfile_TC33() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC33: Verify Profile Page");

        randomPage.clickUserNavName();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("profile") || currentUrl.contains("User"), "Profile page not displayed.");

        System.out.println("TC33 Passed: Profile page verified.");
    }

    @Test
    public void editProfile_TC34() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC34: Edit Profile");

        randomPage.clickUserNavName();
        randomPage.editProfileLastName("ABCD");

        String userMenuName = randomPage.getUserMenuNameText();
        Assert.assertTrue(userMenuName.contains("ABCD"), "Last name update not reflected in User Menu.");

        System.out.println("TC34 Passed: Profile edited.");
    }

    @Test
    public void customizeTabs_TC35() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC35: Customize Tabs");

        randomPage.clickAllTabs();
        randomPage.clickCustomizeMyTabs();

        randomPage.removeTab("Business Brands");

        boolean isRemoved = randomPage.isTabAvailable("Business Brands");
        Assert.assertTrue(isRemoved, "Tab was not removed.");

        randomPage.addTab("Business Brands");

        System.out.println("TC35 Passed: Tab customization verified.");
    }

    @Test
    public void createEvent_TC36() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC36: Create Calendar Event");

        randomPage.clickUserNavName(); 

        randomPage.clickCurrentDateLink();
        randomPage.clickTimeSlot("8:00 PM");
        randomPage.selectSubjectCombo("Other");
        randomPage.selectEndTime("9:00 PM");
        randomPage.saveEvent();

        System.out.println("TC36 Passed: Event created.");
    }

    @Test
    public void createRecurringEvent_TC37() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC37: Create Recurring Event");

        randomPage.clickCurrentDateLink();
        randomPage.clickTimeSlot("4:00 PM");
        randomPage.selectSubjectCombo("Other");
        randomPage.selectEndTime("7:00 PM");

        randomPage.createRecurringEvent("4:00 PM", "7:00 PM", "Weekly", "1", "2025-12-31");

        System.out.println("TC37 Passed: Recurring event created.");
    }

    @AfterMethod
    public void tearDown() {
        screenshot.takeScreenShot(driver, testName);
    }
}
