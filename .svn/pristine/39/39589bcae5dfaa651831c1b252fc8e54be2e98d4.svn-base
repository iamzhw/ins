package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import util.page.Query;


public interface CableStatementsService {

	List<Map<String, Object>> BaseInfoOfReport203(Map<String, Object> map);
	
	List<Map<String, Object>> report203Query(Query query);
	
	List<Map<String, Object>> report207Query(Query query);

	List<Map<String, Object>> get207collection(Query query);
	
	List<Map<String, Object>> report204Query(Query query);

	List<Map<String, Object>> get204collection(Query query);
	
	void delReport203(String str);

	void delReport204(String str);
	
	void delReport207(String str);
	
	void updReport203Info(Map<String, Object> map);

	void updReport204Info(Map<String, Object> map);

	void updReport207Info(Map<String, Object> map);
	
	List<Map<String, Object>> getFYPbyPart(Map<String, Object> map);
	
	void addReport203(Map<String, Object> map);
	
	void addReport204(Map<String, Object> map);
	
	public String importDo_Fiber(HttpServletRequest request, MultipartFile file);
}
