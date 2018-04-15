package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface LineJobDao {

	/**
	 * 计划查询
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> query(Query query);
	
	
	/**
	 * 查询单个巡线计划
	 * 
	 * @param map
	 * @return
	 */
	public Map<String,Object> queryJob(Map<String,Object> map);
	
	/**
	 * 计划新增
	 * 
	 * @param map
	 */
	public void insert(Map<String,Object> map);
	
	/**
	 *  计划更新
	 * @param map
	 */
	public void update(Map<String,Object> map);
	
	/**
	 * 计划暂停
	 * @param map
	 */
	public void stop(Map<String,Object> map);
	
	/**
	 * 根据周期查询计划
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryJobsByCycle(Map<String,Object> params);
	
	/**
	 * 根据计划ID、区域和干线类型查询所有无任务的人员信息
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryStaffsByJob(Map<String,Object> params);
	
	/**
	 * 获取任务ID
	 * 
	 * @return
	 */
	public int getTaskId();
	
	/**
	 * 插入周期性任务
	 * 
	 * @param map
	 */
	public void inserTaskByCycle(Map<String,Object> map);
	
	/**
	 * 天任务新增
	 * 
	 * @param map
	 */
	public void inserTaskByDay(Map<String,Object> map);
	
	
	/**
	 * 两天任务新增
	 * 
	 * @param map
	 */
	public void inserTaskByTwoDay(Map<String,Object> map);
	
	
	/**
	 * 插入任务项
	 * @param map
	 */
	public void insertTaskItem(Map<String,Object> map);
	
	/**
	 * 查询当天是否有外力点任务
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryOutsiteTaskByUser(Map<String,Object> map);
	
	/**
	 * 插入任务关联的外力点
	 * @param map
	 */
	public void insertTaskOutSite(Map<String,Object> map);
	
	/**
	 * 生成看护计划
	 * @param map
	 */
	public void insertGuardJobs(Map<String,Object> map);
	
	/**
	 * 查询所有轨迹人员
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryStaffsByAutoTrack(Map<String,Object> param);
	
	/**
	 * 根据人员查询未匹配的轨迹
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryAllTrackTimeExecute(Map<String,Object> param);
	
	/**
	 * 查询最近的轨迹匹配
	 * @param param
	 * @return
	 */
	public Map<String,Object>  getMaxMatchSite(Map<String,Object> param);
	
	
	/**
	 * 更新定时任务执行时间
	 * 
	 * @param param
	 */
	public void updateTaskTime(Map<String,Object> param);
	
	/**
	 * 更新轨迹
	 * 
	 * @param param
	 */
	public void updateTrack(Map<String,Object> param);
}
