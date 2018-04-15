package com.linePatrol.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
 * map工具包
 * 
 * @author wangyan
 * 
 */
@SuppressWarnings("all")
public class MapUtil {
    /**
     * 从request的parametermap转换为普通map，方便dao层的调用。
     * 
     * @param request
     * @return
     */
    public static Map parameterMapToCommonMap(HttpServletRequest request) {
	try {
	    Map map = request.getParameterMap();
	    Map map1 = new HashMap();
	    for (Object key : map.keySet()) {
		String[] values = (String[]) map.get(key);
		if (values != null && values.length >= 1) {
		    map1.put(key, values[0]);
		}
	    }
	    return map1;
	} catch (Exception e) {
	    return new HashMap();
	}
    }

    public static Map parameterMapToCommonMapForChinese(HttpServletRequest request) {
	try {
	    Map map = request.getParameterMap();
	    Map map1 = new HashMap();
	    for (Object key : map.keySet()) {
		String[] values = (String[]) map.get(key);
		if (values != null && values.length >= 1) {
		    String v = new String(values[0].getBytes("iso8859-1"), "utf-8");
		    map1.put(key, v);
		}
	    }
	    return map1;
	} catch (Exception e) {
	    return new HashMap();
	}
    }

    /**
     * 拷贝map的键值对。
     */
    public static Map copy(Map src, Map dst) {
	for (Object key : src.keySet()) {
	    dst.put(key, src.get(key));
	}
	return dst;
    }

    /**
     * 从JSONObject转换为HashMap。
     */
    public static HashMap fromJSONObject(JSONObject json) {
	HashMap map = new HashMap();
	for (Object k : json.keySet()) {
	    map.put(k, json.get(k));
	}
	return map;
    }
}