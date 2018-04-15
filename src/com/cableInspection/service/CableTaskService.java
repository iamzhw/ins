package com.cableInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import util.page.UIPage;

@SuppressWarnings("all")
public interface CableTaskService {
	
	Map<String, Object> taskQuery(HttpServletRequest request, UIPage pager);

	JSONObject deleteTask(HttpServletRequest request);

	Map<String, Object> getRole(HttpServletRequest request);

}
