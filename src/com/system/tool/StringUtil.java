package com.system.tool;

/**
 * Object工具类
 * 
 * @author 
 */
public class StringUtil {
	/**
	 * String nvl
	 * 
	 * @param obj
	 * @return
	 */
	public static String nvlStr(Object obj) {
		if (obj != null) {
			return obj.toString();
		}
		return "";
	}

	/**
	 * 空值判断
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null || obj.toString().length() <= 0) {
			return true;
		}
		return false;
	}
}
