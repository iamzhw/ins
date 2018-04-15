package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import util.page.Query;
import util.page.UIPage;

public interface CableMyTaskService {

	
	Map<String, Object> getTask(HttpServletRequest request, UIPage pager);
	
	Map<String, Object> getEquip(HttpServletRequest request);
	
	String handleTask(HttpServletRequest request);
	
	Map<String, Object> queryStaffList(HttpServletRequest request, UIPage pager);
	
	public Map<String,Object> getMyTaskEqpPhoto(HttpServletRequest request);
	
	public String getTaskByTaskId(String taskId);
	
	public Map<String, Object> getMyTaskEqpPhotoForZq(HttpServletRequest request);
	
	public Map<String, Object> queryTaskDetailForAudit(HttpServletRequest request);
	
	public String taskAudit(HttpServletRequest request);

	public Map getTaskObjByTaskId(String taskId);

	void getTaskDownload(HttpServletRequest request,
			HttpServletResponse response);
	public void intoFinish(HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getReformOrder(HttpServletRequest request, UIPage pager);

	Map<String, Object> queryStaff_SHY(HttpServletRequest request, UIPage pager);

	Map<String, Object> getMyTaskEqpPhotoForNj(HttpServletRequest request);

	Map<String, Object> getPort(HttpServletRequest request, UIPage pager);

	String updatePort(HttpServletRequest request);
	
	/*
	 * 部门审核员退单
	 */
	public void cancel(HttpServletRequest request,
			HttpServletResponse response);
	/*
	 * 查询部门审核员
	 */

	Map<String, Object> searchStaff_SHY(HttpServletRequest request, UIPage pager);
	
	/*
	 * 审核员退单，检查员检查错误
	 */
	public void cancelTask(HttpServletRequest request,
			HttpServletResponse response);
	
	void getTaskDownloadByNj(HttpServletRequest request,
			HttpServletResponse response);
	
	public String taskNewAudit(HttpServletRequest request);
	
	
	public Map<String, Object>  queryDouDi(HttpServletRequest request, UIPage pager);
	
	public String updateTask(HttpServletRequest request);
 }
