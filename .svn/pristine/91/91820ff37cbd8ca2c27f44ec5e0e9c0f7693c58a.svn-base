package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

/** 
 * @author wangxy 
 * @version 创建时间：2016年7月26日 下午5:57:04 
 * 类说明 
 */
public interface DoneTaskService {

	/**
	 * 查询已办任务
	 * @param request
	 * @param pager
	 * @return
	 */
	Map<String, Object> queryDoneTask(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> getMyTaskEqpPhotoForZq(HttpServletRequest request);
	
	public String getTaskByTaskId(String taskId);
	
	public Map<String,Object> getMyTaskEqpPhoto(HttpServletRequest request);
	/**
	 * 导出检查记录Excel
	 * @param request
	 * @return
	 */
	List<Map<String, Object>> exportExcel(HttpServletRequest request);

	List<Map<String, Object>> exportExcelByEqp(HttpServletRequest request);

	Map<String, Object> queryHandler(HttpServletRequest request, UIPage pager);

	String saveTask(HttpServletRequest request);

	Map<String, Object> querySecondTask(HttpServletRequest request, UIPage pager);

	List<Map<String, Object>> exportSecondTaskExcelByEqp(
			HttpServletRequest request);
	/**
	 * 按工单导出
	 * @param request
	 * @return
	 */
	List<Map<String, Object>> exportExcelByPOrder(HttpServletRequest request);
}
