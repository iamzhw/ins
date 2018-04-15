package com.cableInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("all")
public interface TrailService {
	
	List<Map> queryTrail(HttpServletRequest request);

	String getTaskCables(HttpServletRequest request);

}
