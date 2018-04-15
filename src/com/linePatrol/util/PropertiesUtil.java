package com.linePatrol.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	public static String get(String key){
		InputStream inputStream = null; 
		Properties p = new Properties();  
		 try {  
			 inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");  
		     p.load(inputStream);
		 } catch (IOException e) {  
		     e.printStackTrace();  
		 } finally{
			 try {
			    if(inputStream != null){
			    	inputStream.close();
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 return String.valueOf(p.get(key));
	}

}
