package com.cableCheck.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface TaskMangerNewService {
	
	/**
	 * 上月变动的工单设备
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> addTaskQuery(HttpServletRequest request, UIPage pager);
	/**
	 * 上月变动的工单设备的详细信息
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> queryEqpOrder(Map param);
	
	public Map<String, Object> queryHandler(HttpServletRequest request, UIPage pager);
	
	public String saveTask(HttpServletRequest request);
	
	public void downTaskQuery( HttpServletRequest request, HttpServletResponse response);
	
	//导出所有设备所有端子
	public void downPortQuery( HttpServletRequest request, HttpServletResponse response);

	
 }
