package com.inspecthelper.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.page.UIPage;

@SuppressWarnings("all")
public interface IInspectService {
	
	String getOrderCount(String staffNo);
	
	Map<String, Object> query(HttpServletRequest request, UIPage pager);

	HSSFWorkbook getWorkbook(List<Map<String,Object>> list, String[] columnNames,
			String[] columnMethods);
	
	List<Map<String, Object>> getOrderDetail(Map<String, Object> param) ;
	
	Map<String, Object> resTrouTable(HttpServletRequest request, UIPage pager);

	Map<String, Object> getEquTarget(HttpServletRequest request);
	
	String uploadPhoto(HttpServletRequest request);

	String getLastTrouPhotoPath(HttpServletRequest request);
	
	List<Map<String, Object>> getTargetQues(String str) ;

	String saveResTrou(HttpServletRequest request);

	void checkRes(HttpServletRequest request);

	Map<String, Object> getLastTrou(HttpServletRequest request);
	
	List<Map<String, Object>> cDuanTable(HttpServletRequest request);
	
	String saveResTrou_(HttpServletRequest request);

	List<Map<String, Object>> dinamicChangeTable(HttpServletRequest request);

	List<Map<String, Object>> exportODFDinamicExcel(HttpServletRequest request);

	String analysisODFDinamicExcel(HttpServletRequest request,HttpServletResponse response);

	String updateDinamicChange(HttpServletRequest request,HttpServletResponse response);

	String getODFStaffRole(String staffNo);

	boolean updateDinamicChange(Map param);

	boolean saveResTrou(String orderId, String equipmentId, String targetId,
			String type, String remarks, String staffNo, String ftth, String id);

	boolean saveResTrou(String orderId, String equipmentId, String targetId,
			String type, String remarks, String staffNo);
}
