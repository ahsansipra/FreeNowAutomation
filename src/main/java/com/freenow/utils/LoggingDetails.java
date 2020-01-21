package com.freenow.utils;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class LoggingDetails {

	InputStream fis;

	static Logger log = Logger.getLogger(LoggingDetails.class);

	public static void loginfo(String message) {
		DOMConfigurator.configure("log4j.xml");
		log.info(message);
	}
}
