package com.util.sendMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.util.DateUtil;

public class PropertiesUtil
{

	/**
	 * <一句话功能简述> <功能详细描述>
	 * 
	 * @param args
	 * @see [类、类#方法、类#成员]
	 */
    static String path = "/spring/config.properties";
    
    public final static Properties properties = new Properties();

    public static void load(){
        // TODO Auto-generated method stub
        String content = null;
        File fileName =  new File(path);
        InputStream in=null;
        try {
	        if (fileName.exists()){
				System.out.println("该文件存在！");
	        }
          //in = new BufferedInputStream(new FileInputStream(fileName));
//        in = new FileInputStream(fileName);       
//            while((content = bf.readLine()) != null)
//            {
//                System.out.println(content);
//            }
			in = PropertiesUtil.class.getResourceAsStream(path); // 此方法只访问相对路径，即classes下的文件。
	        if (in == null){
	            in = Thread.currentThread()
	                    .getContextClassLoader()
	                    .getResourceAsStream(path);
	            System.out.println(path);
	        }
	        properties.load(in);
        } catch (Exception e) {
			e.printStackTrace();
		} finally{
            if (null != in){
                try{
                    in.close();
                } catch (IOException e){
                    //System.out.println(e);
                }
            }
        }    
    } 
    
    public static String getProperty(String name){
    	 return properties.getProperty(name)== null?"":properties.getProperty(name).trim();
    }
    
    public static boolean getPropertyBoolean(String name){
    	if(null!=properties.getProperty(name)&&"true".equals(properties.getProperty(name).trim()))
    	{
    		return true;
    	}
    	return false;
    }

	/**
	 * 获取指定配置项的值,如果获取不到,则取默认值
	 * 
	 * @param name
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 值
	 */
	public static boolean getPropertyBoolean(String name, boolean defaultValue) {
		
		if (StringUtils.isNotEmpty(properties.getProperty(name))){
			
			if("true".equals(properties.getProperty(name).trim())){
				return true;

			} else if ("false".equals(properties.getProperty(name).trim())) {
				return false;
			}
		}

		return defaultValue;
	}

	/**
	 * 获取指定配置项的值,如果获取不到,则取默认值
	 * 
	 * @param name
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 值
	 */
	public static String getPropertyString(String name, String defaultValue) {
		
		String value = getProperty(name);
		
		return StringUtils.isNotEmpty(value) ? value
				: defaultValue;
		
	}

	/**
	 * 获取指定配置项的值
	 * 
	 * @param name
	 *            键
	 * @return 值
	 */
	public static Integer getPropertyInt(String name) {

		Integer intVal = null;

		String value = getProperty(name);

		if (StringUtils.isNotEmpty(value)) {
			try {

				intVal = Integer.parseInt(value);
			} catch (NumberFormatException e) {

			}
		}

		return intVal;
	}

	/**
	 * 获取指定配置项的值,如果获取不到,则取默认值
	 * 
	 * @param name
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 值
	 */
	public static Integer getPropertyInt(String name, Integer defaultValue) {

		Integer value = getPropertyInt(name);

		if (value != null) {

			return value;
		} else {
			return defaultValue;
		}
	}

    public static List getPropertyToList(String name)
    {
    	List list =new ArrayList();
    	
    	if(null!=properties.getProperty(name)&& !"".equals(properties.getProperty(name).trim()))
    	{
    		String value[] = properties.getProperty(name).trim().split(";");
    		for (int i = 0; i < value.length; i++) {
    			list.add(value[i]);
			}
    	}
    	return list;
    }
    
    public static void main(String[] name)
    {
    	load();
    	System.out.println(getPropertyToList("defaultNumbers"));
    	System.out.println(DateUtil.getCurrentTime());
    }
}