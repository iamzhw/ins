package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

public interface LineTaskService {

	/**
	 * 任务查询
	 * 
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String,Object> query(HttpServletRequest request,UIPage pager);
	
	
	/**
	 * 获取单个任务
	 * 
	 * @param request
	 * @return
	 */
	public Map<String,Object> getTask(HttpServletRequest request);
	
	/**
	 * 根据区域获取巡检人ID
	 * @param areaId
	 * @return
	 */
	public List<Map<String,Object>> getInpectStaff(String areaId);
	
	
	/**
	 * 任务插入
	 * 
	 * @param request
	 */
	public void insert(HttpServletRequest request);
	
	/**
	 * 任务更新
	 * 
	 * @param request
	 */
	public void update(HttpServletRequest request);
	
	/**
	 * 任务暂停
	 * 
	 * @param request
	 */
	public void stop(HttpServletRequest request);
	
	
	/**
	 * 获取光缆段
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getCableList(HttpServletRequest request);
	
	
	/**
	 * 获取中继段
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getRelayList(HttpServletRequest request);
	
	
	/**
	 * 获取巡线段
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getLineList(HttpServletRequest request);
	
	
	/**
	 * 获取任务详情
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getTaskDetail(HttpServletRequest request);
	
	
	/**
	 * 获取任务外力点详情
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getTaskOutSiteDetail(HttpServletRequest request);
	
}
