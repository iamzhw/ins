package com.roomInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

/**
 * 
 * @ClassName: TaskService
 * @Description:巡检任务业务层接口
 * 
 * @author huliubing
 * @since: 2014-8-5
 *
 */
@SuppressWarnings("all")
public interface TaskService {

	/**
	 *  查询任务列表
	 * @param request
	 * @return
	 */
	public Map<String,Object> query(HttpServletRequest request,UIPage pager);
	
	/**
	 * 获取任务执行详细信息
	 * @param request
	 * @return
	 */
	public Map<String, Object> getTaskDetails(HttpServletRequest request,UIPage pager);
	
	/**
	 * 查询当天任务
	 * @param map 查询条件
	 * @return
	 */
	public List<Map> getDayTaskByJobId(Map map);
	
	/**
	 * 查询一个星期任务
	 * @param map 查询条件
	 * @return
	 */
	public List<Map> getWeekTaskByJobId(Map map);
	
	/**
	 * 查询一个月任务
	 * @param map 查询条件
	 * @return
	 */
	public List<Map> getMonthTaskByJobId(Map map);
	
	/**
	 * 查询半年任务
	 * @param map 查询条件
	 * @return
	 */
	public List<Map> getHalfYearTaskByJobId(Map map);
	
	/**
	 * 查询一年任务
	 * @param map 查询条件
	 * @return
	 */
	public List<Map> getYearTaskByJobId(Map map);
	
	/**
	 * 插入天任务信息
	 * @param map 插入信息
	 */
	public void insertIntoDayTask(Map map);
	
	/**
	 * 插入周任务信息
	 * @param map 插入信息
	 */
	public void insertIntoWeekTask(Map map);
	
	/**
	 * 插入月任务信息
	 * @param map 插入信息
	 */
	public void insertIntoMonthTask(Map map);
	
	/**
	 * 插入半年任务信息
	 * @param map 插入信息
	 */
	public void insertIntoHalfYearTask(Map map);
	
	
	/**
	 * 插入年任务信息
	 * @param map 插入信息
	 */
	public void insertIntoYearTask(Map map);
	
	
	/**
	 * 插入执行详情
	 * @param map 插入信息
	 */
	public void insertIntoTaskDetail(Map map);
}
