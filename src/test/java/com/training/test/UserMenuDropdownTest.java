package com.training.test;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.BaseTest;
import com.training.base.ReadOTP;
import com.training.page.LoginPage;
import com.training.page.UserMenuDropdownPage;
import com.training.utilities.PropertiesFile;
import com.training.utilities.ScreenShot;

public class UserMenuDropdownTest extends BaseTest {

	WebDriver driver;
	UserMenuDropdownPage userPage;
	ScreenShot screenshot;
	String testName;

	@BeforeMethod
	public void Before() {
		driver = super.getDriver();
		userPage = new UserMenuDropdownPage(driver);
	    screenshot = new ScreenShot();

	}

	//@Test
	public void userMenuForUserNameDropdown_TC05() throws IOException {
		testName = Thread.currentThread().getStackTrace()[1].getMethodName();

		System.out.print("Hello");
		// launchApplication();
		userPage.userNavLabel1();
		// Select select = new Select(userNameLabel);
		List<WebElement> optionList = userPage.userNavMenuItems();

		// List<WebElement> optionList = select.getOptions();
		List<String> dropdownValues = new ArrayList<>();
		System.out.println("Dropdown values");
		for (WebElement option : optionList) {
			String value = option.getText();
			dropdownValues.add(value);
			System.out.println(value);
		}
		List<String> expectedValues = Arrays.asList("My Profile", "My Settings", "Developer Console",
				"Switch to Lightning Experience", "Logout");


		Assert.assertEquals(dropdownValues, expectedValues);
	}

	@Test
	public void myProfileOption_TC06() {


		testName = Thread.currentThread().getStackTrace()[1].getMethodName();
		userPage.userNavLabel1();
		userPage.selectMyProfileInUserDropdown();
		userPage.editMyProfile();
		userPage.switchToIframe();
		userPage.selectAboutTab();
		userPage.clearLastNameFieldile();
		userPage.clickLastNameFieldile();
		userPage.SaveAllTheChanges();
		userPage.switchToParentFrame();
		userPage.clickPostButton();
		userPage.switchToIframe1();
		userPage.contentToPost();
		userPage.switchToParentFrame();
		userPage.shareTheContent();
		userPage.SelectFile();
		//isFileUploadInput(userPage.SelectFile());
		
		

	}
	

	public static boolean isFileUploadInput(Object object) {
	    // 1. Check if the tag is <input>
	    boolean isInputTag = ((WebElement) object).getTagName().equalsIgnoreCase("input");

	    // 2. Check if the type attribute is "file"
	    boolean isFileType = ((WebElement) object).getAttribute("type").equalsIgnoreCase("file");

	    return isInputTag && isFileType;
	}
	
	//@Test
	/*public void mySettingsOption_TC07() {
	
		testName = Thread.currentThread().getStackTrace()[1].getMethodName();
		userPage.userNavLabel1();
		userPage.selectMySettingsInUserDropdown();
		userPage.selectPersonalLink();
		userPage.clickToCheckLoginHistory();
		userPage.clickToDownloadTheLink();
		userPage.ClickDisplayAndLayoutLink();
		userPage.clickCustomizeTab();
		userPage.clickCustomTabDropDown();
		userPage.clickAvailableTabDropDown();
		userPage.selectRightArrowIcon();
		userPage.clickSave();
		userPage.clickEmail();
		userPage.ClickCalenderAndRemainder();
		userPage.selectActivityRemainder();
		userPage.openaTestRemainder();
	}*/
	
	
	//@Test
    public void SelectMySettings_TC07() throws IOException, InterruptedException {
        testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Starting TC07: Select My Settings and customize");

        userPage.userNavLabel1();
        Assert.assertTrue(driver.findElement(By.id("userNav-menuItems")).isDisplayed(), 
                "User menu dropdown is not displayed");
        userPage.selectMySettingsInUserDropdown();
        Assert.assertTrue(userPage.isMySettingsPageDisplayed(), 
                "My Settings page was not displayed.");

        userPage.selectPersonalLink();
        userPage.clickToCheckLoginHistory();
        userPage.clickToDownloadTheLink();

        System.out.println("Login history downloaded.");

        userPage.ClickDisplayAndLayoutLink();
        userPage.clickCustomizeTab();

        userPage.clickCustomTabDropDown("Salesforce Chatter");
        
        userPage.clickAvailableTabDropDown("Reports");
        Assert.assertTrue(userPage.isReportTabAdded(), 
                "Reports tab was not found in the Selected Tabs list after saving.");
        userPage.selectRightArrowIcon();
        
        userPage.clickSave();
        System.out.println("Tabs customized.");

        userPage.clickEmail();
        userPage.clickEmailSettingsLink();
        userPage.clickAutomaticBcc();
        Assert.assertTrue(userPage.isAutoBccSelected(), 
                "Automatic BCC checkbox was not saved as checked.");
        userPage.clickSave();
        System.out.println("Email settings saved.");

        userPage.ClickCalenderAndRemainder();
        userPage.selectActivityRemainder();
        userPage.openaTestRemainder();
        Assert.assertTrue(userPage.verifyReminderWindowOpen(), 
                "Activity Reminder popup window was not detected.");
        
        System.out.println("TC07 execution complete.");
    }
	
	@Test
    public void SelectDeveloperConsole_TC08() throws InterruptedException {
        System.out.println("Starting TC08: Developer Console Test");
        
        userPage.userNavLabel1();
        
        userPage.selectDeveloperConsoleInUserDropdown();
        
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        boolean isConsoleOpened = false;
        
        for (String handle : allWindowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                System.out.println("Switched to window: " + driver.getTitle());
                
                String actualTitle = driver.getTitle();
                if (actualTitle.contains("Developer Console")) {
                    isConsoleOpened = true;
                    Assert.assertTrue(true, "Developer Console window found.");
                }
                
                driver.close();
            }
        }
        
        driver.switchTo().window(mainWindowHandle);
        
        Assert.assertTrue(isConsoleOpened, "Developer Console window did not open or title did not match.");
        System.out.println("TC08 Passed");
    }

    @Test
    public void SelectLogout_TC09() throws InterruptedException {
        System.out.println("Starting TC09: Logout Test");
        
        userPage.userNavLabel1();
        
        userPage.clickLogout();
        
        Thread.sleep(2000); 
        
        String expectedTitle = "Login | Salesforce";
        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle, "Logout failed: Page title mismatch.");
        
        System.out.println("TC09 Passed: User logged out successfully.");
    }
	
	@AfterMethod()
	public void tearDown(){
		screenshot.takeScreenShot(driver ,testName);
		
	}
}
