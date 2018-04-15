package com.system.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

public class JsonUtil {
	public static String simpleMapToJsonStr(Map<String ,Object > map){  
		JSONObject jsonObj = JSONObject.fromObject(map); 
		return jsonObj.toString();
    }  
	
	public static HashMap<String, String> toHashMap(Object object)  
	   {  
	       HashMap<String, String> data = new HashMap<String, String>();  
	       // 将json字符串转换成jsonObject  
	       JSONObject jsonObject = JSONObject.fromObject(object);  
	       Iterator it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext())  
	       {  
	           String key = String.valueOf(it.next());  
	           String value = (String) jsonObject.get(key);  
	           data.put(key, value);  
	       }  
	       return data;  
	   }  
	 
	public static Map getMap(String str){  
		JSONObject  jasonObject = JSONObject.fromObject(str);
		Map map = (Map)jasonObject;
		return map;
    }  
	
	public static Map  getData(String str){
		String sb = str.substring(1, str.length()-1);
		 String[] name =  sb.split("\\\",\\\"");
		 String[] nn =null;
		 Map map = new HashMap();
		 for(int i= 0;i<name.length; i++){
			 nn = name[i].split("\\\":\\\"");
			 map.put(nn[0], nn[1]);
		 }
		return map;
	}
	
	
}