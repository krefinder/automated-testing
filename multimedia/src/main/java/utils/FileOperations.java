package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.naming.ConfigurationException;

import org.apache.commons.configuration.PropertiesConfiguration;



public class FileOperations {
	
	/**
	 * @param filePath
	 * @param key
	 * @return
	 */
	public static String getConfigValue(String filePath, String key) {
		String keyValue = null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filePath));
			keyValue = prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keyValue;
	}
	/**
	 * @param filePath
	 * @param key
	 * @param value
	 * @throws IOException
	 * @throws ConfigurationException
	 * @throws org.apache.commons.configuration.ConfigurationException 
	 */
	public static void updateConfigValue(String filePath, String key, String value)
			throws IOException, ConfigurationException, org.apache.commons.configuration.ConfigurationException {
		
		PropertiesConfiguration config = new PropertiesConfiguration(filePath);
		config.setProperty(key, value);
		config.save();
	}

	public static void cleanDir(String dirName) {
		File directory = new File(dirName);

		// Get all files in directory
		File[] files = directory.listFiles();
		for (File file : files) {
			// Delete each file
			if (!file.delete()) {
				// Failed to delete file
				System.out.println("Failed to delete " + file);
			}
		}}	
	
	
}
	

