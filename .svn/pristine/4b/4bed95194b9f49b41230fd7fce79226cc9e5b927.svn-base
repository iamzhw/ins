package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import util.page.Query;
import util.page.UIPage;

public interface CutAndConnOfFiberService {
	
	public List<Map<String, Object>> query(Query query);
	
	public List<Map<String, Object>> getCityInfo(Map<String, Object> map);
	
	public List<Map<String, Object>> getCable(String areaId);
	
	public List<Map<String , Object>> getRelay(String cableId);
	
	List<Map<String, Object>> showRelayDetailInfo(Query query);
	
	List<Map<String, Object>> getCuttingRecordOfFiber(Query query);
	
	List<Map<String, Object>> getRecordOfSteps(Query query);
	
	void delTestInfo(String id);
	
	int delTestDetail(Map<String, Object> map);
	
	int updTestInfo(Map<String, Object> map);
	
	int updTestDetailInfo(Map<String, Object> map);
	
	List<Map<String, Object>> getListOfSensitiveline(Query query);
	
	void addTestInfo(Map<String, Object> map);
	
	void addTestDetailInfo(Map<String, Object> map);
	
	int delRecordOfFiber(Map<String, Object> map);

	void addRecordOfFiber(Map<String, Object> map);

	void updRecordOfFiber(Map<String, Object> map);

	void delStepData(Map<String, Object> map);

	void exportExcel(HttpServletRequest request, HttpServletResponse response,Query query);
	
	public String importDo(HttpServletRequest request, MultipartFile file);
	
	public String importDo_Fiber(HttpServletRequest request, MultipartFile file);
	
	public String importDo_Step(HttpServletRequest request, MultipartFile file);
	
	void addStepData(Map<String, Object> map);

	void updStepData(Map<String, Object> map);

	public void exportTextInfoExcel(HttpServletRequest request,
			HttpServletResponse response, Query query,Map<String, Object> para);
	
	public Object importCutAndConnOfFiber(HttpServletRequest request, MultipartFile file);

	public void exportFiberRecordExcel(HttpServletRequest request,
			HttpServletResponse response,  Map<String, Object> para);
}
