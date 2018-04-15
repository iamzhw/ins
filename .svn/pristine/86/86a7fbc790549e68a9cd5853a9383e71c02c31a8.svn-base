package com.cableCheck.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface TaskMangerService {
	
	public Map<String, Object> getTask(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> addTaskQuery(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> equipmentQuery(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> terminalQuery(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> queryHandler(HttpServletRequest request, UIPage pager);
	
	public String saveTask(HttpServletRequest request);
	
	public Map<String, Object> queryDeviceRecords(HttpServletRequest request, UIPage pager);

	public void downTaskQuery( HttpServletRequest request, HttpServletResponse response);
	
	//导出所有设备所有端子
	public void downPortQuery( HttpServletRequest request, HttpServletResponse response);

	public void downEquipmentQuery(Map<String, Object> para,HttpServletRequest request,
			HttpServletResponse response);

	public void downTerminalQuery(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	public Map<String, Object> queryElectron(HttpServletRequest request,
			UIPage pager);

	public void electronArchivesDownload(HttpServletRequest request,
			HttpServletResponse response);

	public String saveElectronTask(HttpServletRequest request);
	
	public String send_contract_name(HttpServletRequest request);


	
 }
