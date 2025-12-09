package com.training.test;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.BaseTest;
import com.training.page.AccountsPage;
import com.training.utilities.ScreenShot;

public class AccountsTest extends BaseTest {

    WebDriver driver;
    AccountsPage accountsPage;
    ScreenShot screenshot;
    String testName;

    @BeforeMethod
    public void Before() {
        driver = super.getDriver();
        accountsPage = new AccountsPage(driver);
        screenshot = new ScreenShot();
    }

    // @Test
    public void createAccount_TC10() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC10: Create Account");

        accountsPage.clickOnAllTabArrow();
        accountsPage.clickAccountsTab();
        Assert.assertTrue(driver.getTitle().contains("Accounts"), "Accounts page not displayed");

        accountsPage.clickNewAccount();
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(@class,'pageDescription')]")).getText()
                .contains("New Account"), "New Account page not displayed");

        accountsPage.createAccount("BPriya", "Technology Partner", "High");

        // Verification: Check if the new account name is displayed on the page
        Assert.assertTrue(driver.getTitle().contains("BPriya"), "Account was not created successfully");
        System.out.println("TC10 Passed: Account created.");
    }

    @Test
    public void createNewView_TC11() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC11: Create New View");

        accountsPage.clickOnAllTabArrow();
        accountsPage.clickAccountsTab();
        accountsPage.clickCreateNewView();

        String uniqueSuffix = String.valueOf(Date.from(Instant.now()).getTime());
        String viewName = "priyah" + uniqueSuffix;
        String uniqueName = "Unique" + uniqueSuffix;

        accountsPage.createNewView(viewName, uniqueName);

        String selectedView = accountsPage.getSelectedView();
        Assert.assertEquals(selectedView, viewName, "New view was not selected automatically.");
        System.out.println("TC11 Passed: New view created and selected.");
    }

    @Test
    public void editView_TC12() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC12: Edit View");

        accountsPage.clickOnAllTabArrow();
        accountsPage.clickAccountsTab();

        accountsPage.clickCreateNewView();
        String uniqueSuffix = String.valueOf(Date.from(Instant.now()).getTime());
        String viewName = "ViewToEdit" + uniqueSuffix;
        accountsPage.createNewView(viewName, "UniqueEdit" + uniqueSuffix);

        accountsPage.clickEditLink();
        Assert.assertTrue(driver.getTitle().contains("Edit View"), "Edit View page not displayed");

        String newViewName = "EditedView" + uniqueSuffix;
        accountsPage.editView(newViewName, "Account Name", "contains", "a");

        String selectedView = accountsPage.getSelectedView();
        Assert.assertEquals(selectedView, newViewName, "View name did not update after edit.");
        System.out.println("TC12 Passed: View edited.");
    }

    @Test
    public void mergeAccounts_TC13() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC13: Merge Accounts");

        accountsPage.clickOnAllTabArrow();
        accountsPage.clickAccountsTab();
        accountsPage.clickMergeAccounts();
        Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Merge My Accounts')]")).isDisplayed(),
                "Merge Accounts page not displayed");

        accountsPage.searchAccount("acc");

        accountsPage.selectAccountsToMerge();
        accountsPage.mergeAccounts();

        Assert.assertTrue(driver.getTitle().contains("Accounts"), "Did not return to Accounts page after merge");
        System.out.println("TC13 Passed: Accounts merged.");
    }

    @Test
    public void createAccountReport_TC14() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC14: Create Account Report");

        accountsPage.clickOnAllTabArrow();
        accountsPage.clickAccountsTab();
        accountsPage.clickLastActivityReport();
        Assert.assertTrue(driver.getTitle().contains("Unsaved Report"), "Report page not displayed");

        String reportName = "RR1" + Date.from(Instant.now()).getTime();
        String reportUniqueName = "UniqueReport" + Date.from(Instant.now()).getTime();

        accountsPage.createReport("Created Date", reportName, reportUniqueName);

        Assert.assertTrue(driver.getTitle().contains(reportName), "Report page title does not contain report name");
        System.out.println("TC14 Passed: Report created.");
    }

    @AfterMethod
    public void tearDown() {
        screenshot.takeScreenShot(driver, testName);
    }
}
