package com.cableInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

public interface RecordService {
	Map<String, Object> query(HttpServletRequest request, UIPage pager);

	@SuppressWarnings("unchecked")
	List<Map> queryPhoto(HttpServletRequest request);

	Map<String, Object> getListByPage(HttpServletRequest request, UIPage pager);

	Map<String, Object> getAreaList();

	Map<String, Object> queryRecordStaff(HttpServletRequest request, UIPage pager);

	List<Map> getRecordForExport(HttpServletRequest request);

	List<Map> getRecordStaffForExport(HttpServletRequest request);
}
