package com.training.test;

import java.time.Instant;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.BaseTest;
import com.training.page.ContactsPage;
import com.training.utilities.ScreenShot;

public class ContactsTest extends BaseTest {

    WebDriver driver;
    ContactsPage contactsPage;
    ScreenShot screenshot;
    String testName;

    @BeforeMethod
    public void Before() {
        driver = super.getDriver();
        contactsPage = new ContactsPage(driver);
        screenshot = new ScreenShot();
    }

    @Test
    public void createContact_TC25() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC25: Create New Contact");
        contactsPage.clickOnAllTabArrow();
        contactsPage.clickContactsTab();
        contactsPage.clickNewContact();

        String lastName = "chandra";
        String accountName = "Acc1"; // Assuming this account exists or is pickable

        contactsPage.createContact("Bharathy", lastName, accountName);

        // Verification: Check if contact page is displayed (title contains name)
        Assert.assertTrue(driver.getTitle().contains(lastName), "Contact was not created successfully");
        System.out.println("TC25 Passed: New Contact created.");
    }

    @Test
    public void createNewView_TC26() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC26: Create New View");
        contactsPage.clickOnAllTabArrow();
        contactsPage.clickContactsTab();
        contactsPage.clickCreateNewView();

        String uniqueSuffix = String.valueOf(Date.from(Instant.now()).getTime());
        String viewName = "csample" + uniqueSuffix;
        String uniqueName = "Unique" + uniqueSuffix;

        contactsPage.createNewView(viewName, uniqueName);

        String selectedView = contactsPage.getSelectedView();
        Assert.assertEquals(selectedView, viewName, "New view was not selected automatically.");
        System.out.println("TC26 Passed: New view created and selected.");
    }

    @Test
    public void recentlyCreatedView_TC27() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC27: Verify Recently Created View");
        contactsPage.clickOnAllTabArrow();
        contactsPage.clickContactsTab();
        contactsPage.selectRecentContactsView("Recently Created");

        boolean isListed = contactsPage.isContactListedInRecent("chandra");
        Assert.assertTrue(isListed, "Recently created contact not found in Recently Created view.");

        System.out.println("TC27 Passed: Recently Created view verified.");
    }

    @Test
    public void myContactsView_TC28() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC28: Verify My Contacts View");
        contactsPage.clickOnAllTabArrow();
        contactsPage.clickContactsTab();
        contactsPage.selectView("My Contacts");

        String selectedView = contactsPage.getSelectedView();
        Assert.assertEquals(selectedView, "My Contacts", "My Contacts view not selected.");

        System.out.println("TC28 Passed: My Contacts view verified.");
    }

    @Test
    public void viewContact_TC29() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC29: View Contact Details");
        contactsPage.clickOnAllTabArrow();
        contactsPage.clickContactsTab();
        contactsPage.selectRecentContactsView("Recently Created"); // Ensure we have a list

        String contactName = "chandra"; // Assuming exists
        contactsPage.clickContact(contactName); // Clicks 'chandra' if visible

        Assert.assertTrue(driver.getTitle().contains(contactName), "Contact details page not displayed.");
        System.out.println("TC29 Passed: Contact details verified.");
    }

    @Test
    public void createViewError_TC30() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC30: Create View Error Validation");
        contactsPage.clickOnAllTabArrow();
        contactsPage.clickContactsTab();
        contactsPage.clickCreateNewView();

        String uniqueSuffix = String.valueOf(Date.from(Instant.now()).getTime());
        contactsPage.createNewViewOnlyUniqueName("EFGH" + uniqueSuffix); // Only unique name, no view name

        String errorMessage = contactsPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Error: You must enter a value", "Error message mismatch.");

        System.out.println("TC30 Passed: Error message verified.");
    }

    @Test
    public void cancelCreateView_TC31() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC31: Cancel Create View");
        contactsPage.clickOnAllTabArrow();
        contactsPage.clickContactsTab();
        contactsPage.clickCreateNewView();

        String uniqueSuffix = String.valueOf(Date.from(Instant.now()).getTime());
        String viewName = "ABCD" + uniqueSuffix;

        contactsPage.enterViewDetails(viewName, "ABCD" + uniqueSuffix);
        contactsPage.clickCancelInCreateView();


        boolean isViewPresent = contactsPage.isViewPresent(viewName);
        Assert.assertFalse(isViewPresent, "View should not be created after cancelling.");

        System.out.println("TC31 Passed: Create View cancelled.");
    }

    @Test
    public void saveAndNewContact_TC32() {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC32: Save & New Contact");
        contactsPage.clickOnAllTabArrow();
        contactsPage.clickContactsTab();
        contactsPage.clickNewContact();

        String lastName = "Indian";
        String accountName = "Global Media";

       
        contactsPage.enterContactDetails("Bharathy", lastName, accountName);
        contactsPage.clickSaveAndNew();

        String errorMessage = contactsPage.getErrorMessage(); // This gets the top error message.
      
        Assert.assertTrue(driver.getTitle().contains("New Contact"),
                "Did not navigate to New Contact page after Save & New.");
        System.out.println("TC32 Passed: Save & New verified.");
    }
}
