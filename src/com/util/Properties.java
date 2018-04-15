package com.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Properties {
	
	private static final Log log = LogFactory.getLog(Properties.class);
	
	private PropertiesConfiguration pro;
	
	public Properties(String path){
		try {
			pro = new PropertiesConfiguration(path);
		} catch (ConfigurationException e) {
			log.error("File not found", e);
		}
	}
	
	public String getString(String key){
		return this.pro.getString(key, "");
	}
	
	public String getString(String key, String defaultValue){
		return this.pro.getString(key, defaultValue);
	}

}
