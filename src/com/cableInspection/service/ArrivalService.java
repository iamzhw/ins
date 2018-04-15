package com.cableInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

@SuppressWarnings("all")
public interface ArrivalService {

	Map<String, Object> query(HttpServletRequest request, UIPage pager);

	List<Map> queryExl(HttpServletRequest request);

	Map<String, Object> indexTrouble(HttpServletRequest request);

	Map<String, Object> queryTrouble(HttpServletRequest request, UIPage pager);

	List<Map> queryTrouble(HttpServletRequest request);

	Map<String, Object> indexKeep(HttpServletRequest request);

	Map<String, Object> queryKeep(HttpServletRequest request, UIPage pager);

	List<Map> queryKeep(HttpServletRequest request);

	void keepArrivalCheckTask();

	List<Map<String, Object>> keyPointsArrivaledStatistics(
			HttpServletRequest request);

	List<Map> query();
	
	String NOCQuery(HttpServletRequest request);
	
    void addNewArrivalRates();

	void addKeyPoints();
	
	Map queryArrivalRateByDate(HttpServletRequest request, UIPage pager);
	
	List<Map> exportArrivalRateByDate(HttpServletRequest request);

	Map<String, Object> getAreaList();
	
	List<Map> getPhotoByKeepPlanId(HttpServletRequest request);

	Map index(HttpServletRequest request);
	
	public Map getSonAreaById(HttpServletRequest request);
	
	void saveArrivalRateTable();
	
	void saveLxxjCity();
	
	void callWfworkitemflowServiceForTask(String s);
}
