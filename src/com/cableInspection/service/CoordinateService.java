package com.cableInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

public interface CoordinateService {

	Map<String, Object> query(HttpServletRequest request, UIPage pager);

	Map<String, Object> getDetail(HttpServletRequest request);

	String update(HttpServletRequest request);

	String getPoints(HttpServletRequest request);

	Map<String, Object> index(HttpServletRequest request);

	void deletePoints(HttpServletRequest request);

	List<Map<String, Object>> exportPointsList(HttpServletRequest request);
	
	Map<String, Object> querySumOfCood(HttpServletRequest request, UIPage pager);
	
	List<Map<String, Object>> exportSumOfCood(HttpServletRequest request);

}
