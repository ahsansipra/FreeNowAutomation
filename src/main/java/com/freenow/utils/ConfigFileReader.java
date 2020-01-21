package com.freenow.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ahsan Configuring File Reader Manager as singleton class, which will
 *         read from property file. URL, browser type etc will be configured
 *         based on property file
 */
public class ConfigFileReader {
	private Properties properties;
	InputStream inputStream;
	private static ConfigFileReader fileReaderManager;

	private ConfigFileReader() {
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
			properties = new Properties();
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found");
		}
	}

	
	public String getApplicationUrl() {
		String baseURL = properties.getProperty("baseurl");
		if (baseURL != null)
			return baseURL;
		else
			throw new RuntimeException(
					"API Url not specified in the Configuration.properties file for the Key:url");
	}
	
	public String getUserName() {
		String userName = properties.getProperty("username");
		if (userName != null)
			return userName;
		else
			throw new RuntimeException(
					"userName not specified in the Configuration.properties file for the Key:userName");
	}

	
	public static ConfigFileReader getInstance() {
		if (fileReaderManager == null) {
			fileReaderManager = new ConfigFileReader();
			return fileReaderManager;
		} else {
			return fileReaderManager;
		}
	}
}
