package com.inspecthelper.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

/**
 * 日常巡检任务dao层接口
 *
 * @author lbhu
 * @since 2014-9-9
 *
 */
@SuppressWarnings("all")
@Repository
public interface InsTaskDao {
	
	/**
	 * 查询巡检任务列表
	 * 
	 * @param query 查询条件
	 * @return
	 */
	public List<Map> query(Query query);
	
	/**
	 * 查询巡检任务详情
	 * 
	 * @param query 查询条件
	 * @return
	 */
	public List<Map> getTaskDetailList(Query query);
	
	
	/**
	 * 根据任务ID查询任务信息
	 * @param map
	 * @return
	 */
	public Map getTaskById(Map map);
	
	/**
	 * 删除任务，将任务改成失效状态
	 * 
	 * @param map
	 */
	public void deleteTask(Map map);
	
	/**
	 * 更新任务关联资源关系
	 * 
	 * @param map
	 */
	public void updateTaskEqu(Map map);
	
	
	/**
	 * 分配日常巡检任务
	 * 
	 * @param map
	 */
	public void addTask(Map map);
	
	/**
	 * 跳转日常巡检任务
	 * 
	 * @param map
	 */
	public void modifyTask(Map map);
	
	
	/**
	 * 插入日常巡检任务和设备关联关系
	 * 
	 * @param map
	 */
	public void saveTaskEqu(Map map);
	
	/**
	 * 删除日常巡检任务和设备关联关系
	 * @param map
	 */
	public void deleteTaskEqu(Map map);
	
	/**
	 * 插入日常巡检任务
	 * 
	 * @param map
	 */
	public void saveTask(Map map);
	
	/**
	 * 获取计划表ID
	 * 
	 * @return
	 */
	public int getTaskId();
	
	/**
	 * 获取任务表ID
	 * 
	 * @return
	 */
	public int getTaskOrderId();
	
	
	/**
	 * 插入任务
	 * 
	 * @param map
	 */
	public void createTaskOrder(Map map);
	
	/**
	 * 将未到期的工单关联到新的任务
	 * 
	 * @param map
	 */
	public void updateTaskOrder(Map map);
	
	/**
	 * 删除未到期的工单
	 * 
	 * @param map
	 */
	public void deleteTaskOrder(Map map);
	
	/**
	 * 将到期的任务置为等待执行
	 * 
	 * @param map
	 */
	public void updateTaskOrderActiveState(Map map);
	
	/**
	 * 将过期未完成的工单置为已过期
	 * 
	 * @param map
	 */
	public void updateTaskOrderOverdueState(Map map);
	
	
	/**
	 * 将过期的任务置为无效
	 * 
	 * @param map
	 */
	public void updateTaskOverdueState(Map map);
}
