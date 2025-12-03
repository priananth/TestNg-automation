package com.training.page;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.training.base.BasePage;

public class UserMenuDropdownPage extends BasePage {
	WebDriver driver;

	public UserMenuDropdownPage(WebDriver driver) {
		super(driver);

	}

	@FindBy(id = "userNavLabel")
	WebElement userNavLabel;

	//@FindBy(id = "userNav-menuItems")
	@FindBy(xpath = "//div[@id='userNav-menuItems']//a")
	List<WebElement> userNavMenuItems;

	@FindBy(xpath = "//span[@id='idcard-identity']")
	WebElement emailField;
	
	@FindBy(xpath = "//a[contains(@title,'My Profile')]")
	WebElement userDropDownMyProfile;
	
	@FindBy(xpath = "//a[contains(@title,'My Settings')]")
	WebElement userDropDownMySettings;
	
	@FindBy(xpath = "//a[contains(@title,'Developer Console')]")
	WebElement userDropDownDeveloperConsole;
	
	@FindBy(xpath = "//a[contains(@class,'contactInfoLaunch editLink')]")
	WebElement myProfileEdit;
	
	@FindBy(id = "contactInfoContentId")
	WebElement iframe;
	
	@FindBy(xpath = "//a[contains(text(),'About')]")
	WebElement aboutTab;
	
	@FindBy(id = "lastName")
	WebElement lastName;
	
	@FindBy(xpath = "//input[contains(@value,'Save All')]")
	WebElement saveAll;
	
	@FindBy(xpath = "//span[contains(text(),'Post')]")
	WebElement post;
	
	@FindBy(xpath = "//iframe[contains(@class,'cke_wysiwyg_frame cke_reset')]")
	WebElement iframe1;
	
	@FindBy(xpath = "//body[contains(text(),'Share an update, @mention someone...')]")
	WebElement postContent;
	
	@FindBy(xpath = "//input[contains (@title,'Share')]")
	WebElement share;
	
	@FindBy(id = "cpublisherAttachContentPost")
	WebElement file;
	
	@FindBy(id = "chatterUploadFileActionPanel")
	WebElement uploadButton;
	
	@FindBy(id = "PersonalInfo_font")
	WebElement personalLink;
	
	@FindBy(id = "LoginHistory_font")
	WebElement loginHistoryLink;

	@FindBy(xpath = "//*[contains(text(),'Download login history for last six months')]")
	WebElement download;
	
	@FindBy(id = "DisplayAndLayout_font")
	WebElement displayAndLayoutLink;

	@FindBy(xpath = "//span[contains(text(),'Customize My Tabs')]")
	WebElement customizeTab;
	
	@FindBy(xpath = "//select[contains(@name,'p4')]")
	WebElement customTabDropDown;
	
	@FindBy(xpath = "//select[contains(@id,'duel_select_0')]")
	WebElement availableTabDropDown;
	
	@FindBy(className = "rightArrowIcon")
	WebElement rightArrowIcon;
	
	@FindBy(xpath = "//input[contains(@value,'Save')]")
	WebElement Save;
	
	@FindBy(id = "EmailSetup_font")
	WebElement email;
	
	@FindBy(xpath = "//span[contains(text(),'My Email Settings')]")
    WebElement emailSettingLink;

    @FindBy(xpath = "//input[contains(@id,'auto_bcc1')]")
    WebElement automaticBcc;
	
	@FindBy(id = "CalendarAndReminders_font")
	WebElement calenderAndRemainder;
	
	@FindBy(xpath = "//span[contains(text(),'Activity Reminders')]")
	WebElement activityRemainder;
	
	@FindBy(id = "testbtn")
	WebElement openaTestRemainder;
	
	@FindBy(xpath = "//select[contains(@id,'duel_select_1')]")
    WebElement selectedTabsDropDown;

    @FindBy(xpath = "//div[@id='userNav-menuItems']/a")
    List<WebElement> dropdownOptions;
    
    @FindBy(xpath = "//a[contains(@title,'Logout')]")
    WebElement logoutLink;
	
	
	public String userNavLabel() {
		return userNavLabel.getText();
	}

	public void userNavLabel1() {
		userNavLabel.click();
	}

	public String emailField() {
		return emailField.getText();
	}

	public List<WebElement> userNavMenuItems() {
		return this.userNavMenuItems;
	}
	
	public void selectMyProfileInUserDropdown() {
		userDropDownMyProfile.click();
	}
	
	public void selectMySettingsInUserDropdown() {
		userDropDownMySettings.click();
	}
	
	public void selectDeveloperConsoleInUserDropdown(){
		userDropDownDeveloperConsole.click();
	}
	
	public void editMyProfile() {
		myProfileEdit.click();
	}
	
	public void selectAboutTab() {
		aboutTab.click();
	}
	
	public void clearLastNameFieldile() {
		lastName.clear();
	}
	
	public void clickLastNameFieldile() {
		lastName.click();
	}
	
	public void SaveAllTheChanges() {
		saveAll.click();
	}
	
	public void clickPostButton() {
		post.click();
	}
	
	public void switchToIframe() {
		 driver.switchTo().frame(iframe);
	}
	
	public void switchToIframe1() {
		 driver.switchTo().frame(iframe1);
	}
	
	public void contentToPost() {
		postContent.sendKeys("It's good day to start learning....	");;
	}
	
	public void shareTheContent() {
		
		Actions actions2 = new Actions(driver); 
		actions2.moveToElement(share).click().build().perform();
	}
	
	public void SelectFile() {
		file.click();
	}
	
	public void clickUploadButton() {
		uploadButton.click();
	}
	
	public void switchToParentFrame() {
		driver.switchTo().parentFrame();
	}
	
	
	public void selectPersonalLink() {
		personalLink.click();
	}
	
	public void clickToCheckLoginHistory() {
		loginHistoryLink.click();
	}
	
	public void clickToDownloadTheLink() {
		download.click();
	}
	
	public void ClickDisplayAndLayoutLink() {
		displayAndLayoutLink.click();
	}
	
	public void clickCustomizeTab() {
		customizeTab.click();
	}
	
	public void clickCustomTabDropDown() {
		Select dropDown = new Select(customTabDropDown);
		dropDown.selectByVisibleText("Salesforce Chatter");
	}	
	
	public void clickAvailableTabDropDown() {
		Select dropDown1 = new Select(availableTabDropDown);
		dropDown1.selectByVisibleText("Reports");
	}
	
	public void clickCustomTabDropDown(String text) {
        Select dropDown = new Select(customTabDropDown);
        dropDown.selectByVisibleText(text);
    }

    public void clickAvailableTabDropDown(String text) {
        Select dropDown1 = new Select(availableTabDropDown);
        dropDown1.selectByVisibleText(text);
    }
	
	public void selectRightArrowIcon() {
		rightArrowIcon.click();
	}
	
	public void clickSave() {
		Save.click();
	}
	
	public void clickEmail() {
		email.click();
	}
	
	public void ClickCalenderAndRemainder() {
		calenderAndRemainder.click();
	}
	
	public void selectActivityRemainder() {
		activityRemainder.click();
	}
	
	public void openaTestRemainder() {
		openaTestRemainder.click();
	}
	
	public void clickEmailSettingsLink() {
        emailSettingLink.click();
    }

    public void clickAutomaticBcc() {
        if (!automaticBcc.isSelected()) {
            automaticBcc.click();
        } else {
            System.out.println("Auto BCC is already checked.");
        }
    }
	
    public boolean isMySettingsPageDisplayed() {
        return driver.getTitle().contains("My Settings") || 
               driver.findElement(By.xpath("//span[@id='idcard-identity']")).isDisplayed();
    }
	
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        if (dirContents != null) {
            for (File file : dirContents) {
                if (file.getName().contains(fileName)) {
                    // Optional: Delete the file after verification to keep folder clean
                    // file.delete(); 
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isReportTabAdded() {
        Select select = new Select(selectedTabsDropDown);
        List<WebElement> selectedOptions = select.getOptions();
        for (WebElement option : selectedOptions) {
            if (option.getText().equals("Reports")) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isAutoBccSelected() {
        return automaticBcc.isSelected();
    }
    
    public boolean verifyReminderWindowOpen() {
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        
        boolean windowFound = false;
        
        for (String handle : allWindowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                if (driver.getTitle().contains("Activity Reminders")) {
                    windowFound = true;
                    driver.close(); // Close the popup
                }
            }
        }
        
        driver.switchTo().window(mainWindowHandle); // Switch back to main
        return windowFound;
    }
    
    public void clickLogout() {
        logoutLink.click();
    }
    
	
}
