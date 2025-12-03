package com.training.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshots {

	public void takeScreenShot(WebDriver driver,String testName) {
		
	TakesScreenshot screenshot = ((TakesScreenshot)driver);
	File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
	Date currentDate = new Date();
	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(currentDate);
	String filepath = "/Users/priya/Documents/workspace_eclipse/TestNGFramework/screenshots/automationscreenshots"+testName+".png";

	//String filepath = "/Users/priya/Documents/workspace_eclipse/TestNGFramework/screenshots/automationscreenshots"+timestamp+".png";
	File destFile = new File (filepath);
	try {
		FileUtils.copyFile(srcFile, destFile);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
