package com.cvmaker.resources;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AppConfig {
	public static final Properties PROPERTIES;
	public static InputStream inputStream = null;
	static {
		try {
			System.out.println("inside static try"+inputStream);
			inputStream = new FileInputStream("configuration.properties");
		}
		catch (FileNotFoundException e) {
			Logger logger = Logger.getLogger(AppConfig.class);
			logger.error(e.getMessage(), e);

		}
		PROPERTIES = new Properties();
		try {
			System.out.println("inside properties try");
			PROPERTIES.load(inputStream);
		}
		catch (IOException e) {
			Logger logger = Logger.getLogger(AppConfig.class);
			logger.error(e.getMessage(), e);

		}
	}
}
