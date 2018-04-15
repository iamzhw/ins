package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface SpecialCheckReportService {

	Map<String, Object> query(HttpServletRequest request, UIPage pager);


	Map<String, Object> queryByCity(HttpServletRequest request, UIPage pager);


	void specialCheckReportDownload(HttpServletRequest request,
			HttpServletResponse response);


	void specialCheckReportDownloadByCity(HttpServletRequest request,
			HttpServletResponse response);


	public List<Map<String, Object>> selArea();


	Map<String, Object> equipmentQuery(HttpServletRequest request, UIPage pager);


	void equipmentDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);


	Map<String, Object> portQuery(HttpServletRequest request, UIPage pager);


	void portDownload(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response);
}
