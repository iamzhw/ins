package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface WrongPortReportService {

	public List<Map<String, Object>> selArea();

	Map<String, Object> query(HttpServletRequest request, UIPage pager);

	public void wrongPortReportDownload(HttpServletRequest request,
			HttpServletResponse response);

	public Map<String, Object> queryPersonalQuality(HttpServletRequest request,
			UIPage pager);

	public void exportExcelPersonalCheckDownload(HttpServletRequest request,
			HttpServletResponse response);

}
