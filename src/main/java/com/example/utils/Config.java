package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

	private static Config singleton;
	private static final Logger LOG = LoggerFactory.getLogger(Config.class);
	private static Properties prop = new Properties();
	private static InputStream input = null;
	public static final String MOCK = "mock";
	public static final String IP_MONGO = "ip_mongo";
	public static final String PUERTO_MONGO = "puerto_mongo";
	public static final String BD_MONGO = "bd_mongo";
	public static final String MONGO_USER = "mongo_user";
	public static final String MONGO_PASS = "mongo_pass";

	private Config() throws IOException {
		String filename = "conf.properties";
		input = Config.class.getClassLoader().getResourceAsStream(filename);
		if (input == null) {
			LOG.error("File not found: " + filename);
			return;
		}
		// load a properties file from class path, inside static method
		prop.load(input);
		if (input != null) {
			input.close();
		}
	}

	public static Config getInstance() throws IOException {
		if (singleton == null) {
			singleton = new Config();
		}
		return singleton;
	}

	public String getProperty(String property) {
		return prop.getProperty(property);
	}
}
