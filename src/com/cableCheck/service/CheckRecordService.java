package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

public interface CheckRecordService {

	/**
	 * 查询端子所在设备记录列表
	 */
	Map<String, Object> queryCheckRecord(HttpServletRequest request, UIPage pager);
		
	
	/**
	 * 查询同种端子检查记录情况
	 */
	Map<String, Object> querySamePortRecord(HttpServletRequest request, UIPage pager);	
	
	/**
	 * 导出端子所在设备记录列表
	 */
	List<Map<String, Object>> exportPortsRecord(HttpServletRequest request);
	
}
