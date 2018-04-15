package com.inspecthelper.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

@SuppressWarnings("all")
public interface InsOrderTaskService {
	
	public Map query(HttpServletRequest request,UIPage pager);
	
	public int getUserRoleCount(Map<String,Object> params);
	
	public List<Map> getEquTarget(Map map);
	
	public String getPLZPCheckStaff(Map map);
	
	public List<Map> getOrderTaskList(HttpServletRequest request);
	
	/**
	 * 获取整改前照片
	 * 
	 */
	public List<Map> getLastTrouPhotoPath(Map map);
	
	/**
	 * 获取所有图片
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> findAllPhoto(Map map);
	
	/**
	 * 获取所有除图片外的文件
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> findAllFile(Map map);
	
	/**
	 * 获取整改前工单编码记录
	 * 
	 * @param map
	 * @return
	 */
	public String findTroublecode(Map map);
	
	/**
	 * 获取工单流程详情
	 * 
	 * @return
	 */
	public List<Map> getActionHistoryList(HttpServletRequest request);
	
	/**
	 * 完成工单派发
	 * 
	 * @param request
	 */
	public void completeTask(HttpServletRequest request);
	
	/**
	 * 完成回单
	 * 
	 * @param request
	 */
	public void completeHuidanTask(HttpServletRequest request);
	
	/**
	 * 审核工单
	 * 
	 * @param request
	 */
	public void completeCheckTask(HttpServletRequest request);
	
	
	/**
	 * 工单归档
	 * 
	 * @param request
	 */
	public void completeArchiveTask(HttpServletRequest request);
	
	/**
	 * 工单退单
	 * 
	 * @param request
	 */
	public void completeChargebackTask(HttpServletRequest request);
	
	/**
	 * 工单转派
	 * 
	 * @param request
	 */
	public void completeReassignmentTask(HttpServletRequest request);
	
	/**
	 * 批量转派
	 * 
	 * @param request
	 */
	public void completeBtnReassign(HttpServletRequest request);
	
	
	/**
	 * 根据公司ID获取人员信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> getAllStaffByTroubleOrderList(Map map);
}
