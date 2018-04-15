package com.linePatrol.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtil {
    public static String urlDecode(String s) {
	return urlDecode(s, "utf-8");
    }

    public static String urlDecode(String s, String enc) {
	try {
	    return URLDecoder.decode(s, enc);
	} catch (Exception e) {
	    return s;
	}
    }

    public static String urlEncode(String s) {
	return urlEncode(s, "utf-8");
    }

    public static String urlEncode(String s, String enc) {
	try {
	    return URLEncoder.encode(s, enc);
	} catch (Exception e) {
	    return s;
	}
    }

    public static boolean isNotNull(String str) {
	if (null == str) {
	    return false;
	} else if (str.equals("")) {
	    return false;
	} else if (null != str && !str.equals("")) {
	    return true;
	}
	return false;
    }

    public static boolean isNullOrEmpty(String str) {
	if (null == str || "null".equals(str) || "".equals(str.trim())) {
	    return true;
	} else {
	    return false;
	}
    }

    public static boolean isNOtNullOrEmpty(String str) {

	if (null == str || "null".equals(str) || "".equals(str.trim())) {
	    return false;
	} else {
	    return true;
	}
    }
}
