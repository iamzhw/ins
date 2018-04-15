package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface LineTaskDao {

	/**
	 * 任务查询
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> query(Query query);
	
	/**
	 * 获取任务ID
	 * 
	 * @return
	 */
	public int getTaskId();
	
	
	/**
	 * 获取单个任务
	 * 
	 * @param params
	 * @return
	 */
	public Map<String,Object> getTask(Map<String,Object> params);
	
	
	/**
	 * 获取任务详情
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> getTaskItems(Map<String,Object> params);
	
	
	/**
	 * 根据区域获取巡检人
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> getInpectStaff(Map<String,Object> params);
	
	
	/**
	 * 任务新增
	 * 
	 * @param map
	 */
	public void insert(Map<String,Object> map);
	
	/**
	 * 插入任务关联的巡线段
	 * 
	 * @param map
	 */
	public void insertTaskItem(Map<String,Object> map);
	
	/**
	 *  任务更新
	 * @param map
	 */
	public void update(Map<String,Object> map);
	
	/**
	 * 任务暂停
	 * @param map
	 */
	public void stop(Map<String,Object> map);
	
	
	/**
	 * 获取光缆段
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getCableList(Map<String,Object> params);
	
	
	/**
	 * 获取中继段
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getRelayList(Map<String,Object> params);
	
	
	/**
	 * 获取巡线段
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getLineList(Map<String,Object> params);
	
	/**
	 * 获取任务详情
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getTaskDetail(Map<String,Object> params);
	
	
	/**
	 * 获取任务外力点详情
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getTaskOutSiteDetail(Map<String,Object> params);
	
}
