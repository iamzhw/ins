package com.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class StringUtil {

	public static String getCurrDate() {
		return getCurrDate("yyyy-MM-dd");
	}

	public static String getCurrDate(String format) {
		String s1 = "";
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		simpledateformat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			s1 = simpledateformat.format(new Date());
		} catch (Exception exception) {
		}
		return s1;
	}

	/**
	 * 将字节数组转为十六进制字符串
	 * 
	 * @param pic
	 * @return
	 */
	public static String convertByteToHexString(byte[] src) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < src.length; i++) {
			byte temp = src[i];
			if (temp < 0)
				temp += 256;
			String hexString = Integer.toHexString(temp);
			if (hexString.length() == 1) // 对小于15的数字就行出来，补0凑成两位
				hexString = "0" + hexString;
			else
				hexString = hexString.substring(hexString.length() - 2);
			sb.append(hexString);
		}
		return sb.toString();
	}

	public static Date stringToDate(String date) {
		return stringToDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date stringToDate(String s, String format) {
		Date date = null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		simpledateformat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			simpledateformat.setLenient(false);
			date = simpledateformat.parse(s);
		} catch (Exception exception) {
			date = null;
		}
		return date;
	}

	public static String dateToString(Date date, String s) {
		String s1 = "";
		SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
		simpledateformat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			s1 = simpledateformat.format(date);
		} catch (Exception exception) {
		}
		return s1;
	}

	public static String dateToString(Date date){
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 路径转换
	 * 
	 * @param path
	 *            路径
	 * @param fix
	 *            是否补全
	 * @return
	 */
	public static String convertPath(String path, boolean fix) {
		if (isEmpty(path)) {
			return path;
		}
		path = StringUtils.replace(path, "\\", "/");
		if (fix && !path.endsWith("/")) {
			path += "/";
		}
		return path;
	}
	
	public static boolean isEmpty(String str) {
		return StringUtils.isBlank(str);
	}
	
	public static String objectToString(Object obj){
		if(obj == null){
			return "";
		}
		return ObjectUtils.toString(obj, "");
	}
	
	public static int stringToInt(String str){
		if(str == null){
			return 0;
		}
		return Integer.valueOf(str);
	}
	
	/**
	 * 判断list是否为空
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("all")
	public static boolean isEmpty(List list)
	{
		if(null != list && list.size() >0)
		{
			return false;
		}
		
		return true;
	}
	
	public static String convertStrUTF8(String str){
		String tempStr = null;
		if(!isEmpty(str)){
			try {
				tempStr = URLDecoder.decode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return tempStr;
	}
	
	/**
	 * separat a string to a list by separator
	 * 
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List<Integer> toIntegerList(String str, String separator) {
		List<Integer> list = new ArrayList<Integer>();
		String[] temp = StringUtils.split(str, separator);
		if (temp != null && temp.length > 0) {
			for (String s : temp) {
				if (!NumberUtils.isNumber(s)) {
					return null;
				}
				list.add(NumberUtils.toInt(s));
			}
		}
		return list;
	}
}
