package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface CheckQualityReportService {

	Map<String, Object> query(HttpServletRequest request, UIPage pager);



	public List<Map<String, Object>> selArea();

	void checkQualityReportDownload(
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> equipmentQuery(HttpServletRequest request, UIPage pager);



	Map<String, Object> portQuery(HttpServletRequest request, UIPage pager);



	void equipmentDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);



	void portDownload(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response);



	Map<String, Object> getMyTaskEqpPhotoForZq(HttpServletRequest request);



	Map<String, Object> queryByCity(HttpServletRequest request, UIPage pager);



	void checkQualityReportDownloadByCity(HttpServletRequest request,
			HttpServletResponse response);



	Map<String, Object> equipmentNumQuery(HttpServletRequest request,
			UIPage pager);



	void dtsjDownload(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response);




}
