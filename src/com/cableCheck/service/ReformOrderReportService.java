package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface ReformOrderReportService {

	Map<String, Object> query(HttpServletRequest request, UIPage pager);

	List<Map<String, Object>> selArea();

	void reformOrderReportDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> queryByCity(HttpServletRequest request, UIPage pager);

	void reformOrderReportDownloadByCity(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	
	Map<String, Object> zgEquipmentQuery(HttpServletRequest request, UIPage pager);
	
	void equipmentDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	
	Map<String, Object> portQuery(HttpServletRequest request, UIPage pager);
	
	void portDownload(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response);
	


}
