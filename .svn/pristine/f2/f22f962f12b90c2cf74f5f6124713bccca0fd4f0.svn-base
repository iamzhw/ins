package com.cableInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import util.page.UIPage;

public interface PointManageService {

	Map<String, Object> query(HttpServletRequest request, UIPage pager);

	Map<String, Object> index(HttpServletRequest request);

	Map<String, Object> add(HttpServletRequest request);

	JSONObject save(HttpServletRequest request);

	JSONObject delete(HttpServletRequest request);

	JSONObject importDo(HttpServletRequest request, MultipartFile file);

	List<Map<String, String>> queryExistsPoint(HttpServletRequest request);

	List<Map<String, Object>> queryPoint(HttpServletRequest request);

	Object updatePointName(HttpServletRequest request);

	Map<String, Object> queryKeyPoints(HttpServletRequest request, UIPage pager);

	List<Map<String, Object>> queryKeyPointsExl(HttpServletRequest request);

	List<Map<String, String>> getMntList();
	
	Map queryPointById(HttpServletRequest request);
	
	Map updateCoordinate(HttpServletRequest request);
	Map queryPointForcoordinate(HttpServletRequest request);
	
	JSONObject importKeyPoint(HttpServletRequest request, MultipartFile file);

	List<Map<String, String>> getEqpTypeList();

}
