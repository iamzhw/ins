package com.roomInspection.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

/**
 * 任务数据库接口
 * 
 * @author huliubing
 * @since 2014-07-23
 *
 */
@SuppressWarnings("all")
@Repository
public interface JobDao {

	/**
	 * 查询计划
	 * 
	 * @param query
	 * @return
	 */
	public List<Map> query(Query query);
	
	/**
	 * 插入计划
	 * 
	 * @param map
	 */
	public void insert(Map map);
	

	/**
	 * 更新计划
	 * 
	 * @param map
	 */
	public void update(Map map);

	/**
	 * 删除计划
	 * 
	 * @param map
	 */
	public void delete(Map map);
	
	/**
	 * 暂停计划
	 * 
	 * @param map
	 */
	public void stop(Map map);
	
	/**
	 * 获取所有计划相关的机房类型
	 * 
	 * @return
	 */
	public List<Map> getRoomTypes();

	/**
	 * 获取所有的计划周期类型
	 * 
	 * @return
	 */
	public List<Map> getCircles();
	
	/**
	 * 获取用户所有下级区域
	 * 
	 * @param areaId 用户区域ID
	 * @return
	 */
	public List<Map> getAreaList(int areaId);
	
	/**
	 * 根据任务ID获取单个计划
	 * 
	 * @param id 计划ID
	 * @return
	 */
	public Map getJob(int id);
	
	/**
	 * 根据周期类型获取计划列表
	 * 
	 * @param circleId 周期类型ID
	 * @return
	 */
	public List<Map> getJobsByCircleId(int circleId);
	
}
