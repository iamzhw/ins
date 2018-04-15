package com.system.constant;

import java.util.HashMap;
import java.util.Map;

public class SysSet {
	
	/**
	 * 系统参数配置集合
	 */
	public static final Map<String, Map<String, String>> CONFIG = new HashMap<String, Map<String, String>>();
	
	/**定位时间间隔*/
	public static final String LOCATE_SPAN = "LOCATE_SPAN";
	
	/**坐标上传时间间隔*/
	public static final String UPLOAD_SPAN = "UPLOAD_SPAN";
	
	/**定位误差*/
	public static final String INACCURACY = "INACCURACY";

}
