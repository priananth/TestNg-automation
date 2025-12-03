package com.training.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

	static FileInputStream fis;

	public static String getProperties(String key) throws IOException{
		// create a file with key and value in .properties file
		// 1.path
		String filepath = "/Users/priya/Documents/workspace_eclipse/TestNGFrameworkSalesforce/properties/application.properties";
		
		// 2. Convert the path to object
		
		try {
			fis = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		// 3. Convert this file object into properties object
		
		Properties  prop = new Properties();
		prop.load(fis);
		
		// 4. Prop has both key-value (hashmap) to retrieve the value
		
		String value = prop.getProperty(key);
		return value;

	}

}
