/*
 * All rights Reserved, Copyright (C) FUJITSU LIMITED 2012
 * FileName: JsonUtil.java
 * Version:  $Revision$
 * Modify record:
 * NO. |	Date       	|		Name		|      Content
 * 1   |  2012-6-19		|  JFTT)zhangchen  | original version
 */
package icom.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;


/**
 * class name:JsonUtil <BR>
 * class description: 处理json的工具类 <BR>
 * Remark: <BR>
 * @version 1.00 2012-7-17
 * @author JFTT)zhangchen
 */
@SuppressWarnings("rawtypes")
public class JsonUtil {

	/**
	 * Method name: getJsonString4JavaPOJO <BR>
	 * Description: 将java对象转换成json字符串 <BR>
	 * Remark: <BR>
	 * @param javaObj
	 * @return JSON字符串
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if ("callbacks".equalsIgnoreCase(arg1) || "callback".equalsIgnoreCase(arg1)) {
					return true;
				}
				return false;
			}
		});
		json = JSONObject.fromObject(javaObj, cfg);
		return json.toString();

	}
	
	/**
	 * Method name: getObject4JsonString <BR>
	 * Description: 从一个JSON 对象字符格式中得到一个java对象 <BR>
	 * Remark: <BR>
	 * @param jsonString
	 * @param pojoCalss
	 * @return Java对象
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		JSONUtils.getMorpherRegistry().registerMorpher(   
                new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));  
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}
	
    /**
     * Method name: getJsonString4JavaList <BR>
     * Description: 将一个JavaList转换成String <BR>
     * Remark: <BR>
     * @param objList
     * @return JSON字符串
     */
    public static String getJsonString4JavaList(List objList) {
    	JSONArray jsonArr;
    	JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if ("callbacks".equalsIgnoreCase(arg1) || "callback".equalsIgnoreCase(arg1)) {
					return true;
				}
				return false;
			}
		});
    	jsonArr = JSONArray.fromObject(objList, cfg);
    	return jsonArr.toString();
    }
    /**
     * Method name: getJson4JavaList <BR>
     * Description: 将一个JavaList转换成jsON <BR>
     * Remark: <BR>
     * @param objList
     * @return JSON字符串
     */
    public static JSONArray getJson4JavaList(List objList) {
    	JSONArray jsonArr;
    	JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if ("callbacks".equalsIgnoreCase(arg1) || "callback".equalsIgnoreCase(arg1)) {
					return true;
				}
				return false;
			}
		});
    	jsonArr = JSONArray.fromObject(objList, cfg);
    	return jsonArr;
    }

	/**
	 * Method name: getList4Json <BR>
	 * Description: 从json对象集合表达式中得到一个java对象列表 <BR>
	 * Remark: <BR>
	 * @param jsonString
	 * @param pojoClass
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public static List getList4Json(String jsonString, Class pojoClass) {
		JSONUtils.getMorpherRegistry().registerMorpher(   
                new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" })); 
		
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}
		return list;

	}

	/**
	 * Method name: getMap4Json <BR>
	 * Description: 从json HASH表达式中获取一个map <BR>
	 * Remark: <BR>
	 * @param jsonString
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static Map getMap4Json(String jsonString) {
		JSONUtils.getMorpherRegistry().registerMorpher(   
                new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" })); 
		
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * Method name: getObjectArray4Json <BR>
	 * Description: 从json数组中得到相应Object数组 <BR>
	 * Remark:实用性不佳,建议使用getList4Json <BR>
	 * @param jsonString
	 * @return 
	 */
	@Deprecated
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}
	
	/**
	 * Method name: getStringArray4Json <BR>
	 * Description: 从json数组中解析出java字符串数组 <BR>
	 * Remark: <BR>
	 * @param jsonString
	 * @return 
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}

		return stringArray;
	}

	/**
	 * Method name: getLongArray4Json <BR>
	 * Description: 从json数组中解析出javaLong型对象数组 <BR>
	 * Remark: <BR>
	 * @param jsonString
	 * @return 
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);
		}
		return longArray;
	}

	/**
	 * Method name: getIntegerArray4Json <BR>
	 * Description: 从json数组中解析出java Integer型对象数组 <BR>
	 * Remark: <BR>
	 * @param jsonString
	 * @return 
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);
		}
		return integerArray;
	}

	/**
	 * Method name: getDoubleArray4Json <BR>
	 * Description: 从json数组中解析出java Double型对象数组 <BR>
	 * Remark: <BR>
	 * @param jsonString
	 * @return 
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);
		}
		return doubleArray;
	}
	/**
	 * mapToJsonString
	 * @param map
	 * @return
	 */
	public static String mapToJsonString(Map<String,String> map){
		   Set<String> keys = map.keySet();
		   String key = "";
		   String value = "";
		   StringBuffer jsonBuffer = new StringBuffer();
		   jsonBuffer.append("{");    
		   for(Iterator<String> it = keys.iterator();it.hasNext();){
		       key =  (String)it.next();
		       value = map.get(key);
		       jsonBuffer.append("\""+key+"\":\""+value+"\"");
		       if(it.hasNext()){
		            jsonBuffer.append(",");
		       }
		   }
		   jsonBuffer.append("}");
		   return jsonBuffer.toString();
		}

}
