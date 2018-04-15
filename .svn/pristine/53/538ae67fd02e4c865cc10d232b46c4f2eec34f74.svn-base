package com.cableCheck.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface CCStatictisService {
	public Map<String, Object> statictis(HttpServletRequest request, UIPage pager);
	void exportExcelDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response, UIPage pager);
	
	public Map<String, Object> showStatictisOrder(HttpServletRequest request, UIPage pager);
	
	void exportExcelDetail(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response, UIPage pager);
	
	public Map<String, Object> orderChangeAll(HttpServletRequest request, UIPage pager);
	public Map<String, Object> checkErrorAll(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> teamOrder(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> gridOrder(HttpServletRequest request, UIPage pager);
	public Map<String, Object> showCheckErrorOrder(HttpServletRequest request, UIPage pager);
}
