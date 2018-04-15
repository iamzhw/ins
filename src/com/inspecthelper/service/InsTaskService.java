package com.inspecthelper.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

/**
 * 日常巡检任务业务层接口
 *
 * @author lbhu
 * @since 2014-9-9
 *
 */

@SuppressWarnings("all")
public interface InsTaskService {
	
	/**
	 * 查询日常任务列表
	 * 
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String,Object> getTaskList(HttpServletRequest request,UIPage pager);
	
	
	/**
	 * 批量分配任务
	 * 
	 * @param map
	 * @return
	 */
	public void allotTask(HttpServletRequest request);
	
	/**
	 * 获取日常巡检任务执行详情
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> getTaskDetailList(HttpServletRequest request,UIPage pager);
	
	/**
	 * 调整任务
	 * @param request
	 */
	public void modifyTask(HttpServletRequest request);
	
	/**
	 * 删除任务
	 * 
	 * @param request
	 */
	public void deleteTask(HttpServletRequest request);

}
