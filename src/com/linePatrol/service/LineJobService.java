package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

public interface LineJobService {
	
	/**
	 * 计划查询
	 * 
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String,Object> query(HttpServletRequest request,UIPage pager);
	
	/**
	 * 查询单个巡线计划
	 * 
	 * @param request
	 * @return
	 */
	public Map<String,Object> queryJob(HttpServletRequest request);
	
	/**
	 * 计划插入
	 * 
	 * @param request
	 */
	public void insert(HttpServletRequest request);
	
	/**
	 * 计划更新
	 * 
	 * @param request
	 */
	public void update(HttpServletRequest request);
	
	/**
	 * 计划暂停
	 * 
	 * @param request
	 */
	public void stop(HttpServletRequest request);
	
	
	/**
	 * 根据周期查询所有计划
	 * 
	 */
	public List<Map<String,Object>> queryJobsByCycle(Map<String,Object> param);
	
	
	/**
	 * 根据计划ID、区域和干线类型查询所有无任务的人员信息
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryStaffsByJob(Map<String,Object> params);
	
	
	/**
	 * 任务新增
	 * 
	 * @param map
	 */
	public void inserTask(Map<String,Object> map);
	
	/**
	 * 看护任务新增
	 * @param map
	 */
	public void insertGuardJobs(Map<String, Object> map);

}
