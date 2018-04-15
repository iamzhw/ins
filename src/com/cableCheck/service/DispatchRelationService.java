package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

public interface DispatchRelationService {

	/**
	 * 查询端子所在设备记录列表
	 */
	Map<String, Object> queryDispatchRelation(HttpServletRequest request, UIPage pager);
		
	Map<String, Object> getAreaList();
	
	/**
	 * 导出端子所在设备记录列表
	 */
	List<Map<String, Object>> exportPortsRecord(HttpServletRequest request);

	List<Map<String, String>> getGridListByAreaId(String AREA_ID);

	List<Map<String, Object>> getSonAreaListByAreaId(String AREA_ID);

	Map<String, Object> queryStaff(HttpServletRequest request, UIPage pager);

	Map<String, Object> saveDispatchRelation(HttpServletRequest request);

	void deleteRelation(HttpServletRequest request);
	
}
