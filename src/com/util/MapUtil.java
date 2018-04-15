package com.util;

import java.util.HashMap;
import java.util.Map;

/**
 * map 工具包
 * 
 * @author wangyan
 * 
 */
@SuppressWarnings("all")
public class MapUtil {
	/**
	 * 此方法是从servlet的parametermap转化为普通map，原因是servlet的parametermap每一个key对应一个
	 * string[],不方便处理。
	 * 
	 * @param servletParameterMap
	 * @return
	 */
	public static Map getCommonMap(Map servletParameterMap) {
		Map map = new HashMap();
		for (Object key : servletParameterMap.keySet()) {
			Object val = servletParameterMap.get(key);
			if (val instanceof String[]) {
				String[] arr = (String[]) val;
				if (arr.length > 0) {
					map.put(key, arr[0]);
				}
			} else {
				map.put(key, val);
			}
		}
		return map;
	}

	/**
	 * 当map里面某一key的值为src时，将之替换为dst。
	 * 
	 * @param map
	 * @param src
	 * @param dst
	 * @return
	 */
	public static Map replaceValueByValue(Map map, Object key, Object src,
			Object dst) {
		Object val = map.get(key);
		if (val == src || (val != null && val.equals(src))) {
			map.put(key, dst);
		}
		return map;
	}

	/**
	 * 当map里面值为src时，将之替换为dst。
	 * 
	 * @param map
	 * @param src
	 * @param dst
	 * @return
	 */
	public static Map replaceValueByValue(Map map, Object src, Object dst) {
		for (Object key : map.keySet()) {
			Object val = map.get(key);
			if (val == src || (val != null && val.equals(src))) {
				map.put(key, dst);
			}
		}
		return map;
	}
}
