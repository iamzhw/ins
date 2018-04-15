package com.cableInspection.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

@SuppressWarnings("all")
public interface CablePlanService {
	List<Map> getPoints(HttpServletRequest request);
	
	Map addPlan(HttpServletRequest request);

	void save(HttpServletRequest request);

	String getPlanExitCable(HttpServletRequest request);

	Map<String, Object> planQuery(HttpServletRequest request, UIPage pager);

	void deletePlan(HttpServletRequest request);

	String getPlanCable(HttpServletRequest request);

	Map<String, Object> editPlan(HttpServletRequest request);

	List<Map> getPlanDetail(HttpServletRequest request);

	Boolean updatePlan(HttpServletRequest request);

	Map<String, Object> getSpectors(HttpServletRequest request,UIPage pager);

	Boolean saveTask(HttpServletRequest request);

	public List<Map> getLineByLineId(Map params);
	/**
	 * 显示增加关键点计划页面
	 * @return
	 */
	Map<String, Object> showAddPointPlan();
	/**
	 * 查询关键点
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> queryPoint(HttpServletRequest request,UIPage pager);
	/**
	 * 保存关键点计划
	 * @param request
	 * @return
	 */
	Map<String, Object> savePointPlan(HttpServletRequest request);
	/**
	 * 显示修改关键点页面
	 * @param request 
	 * @return
	 */
	Map<String, Object> showEditPointPlan(HttpServletRequest request);
	/**
	 * 查询已选择的关键点
	 */
	Map<String, Object> searchPointData(HttpServletRequest request, UIPage pager);
	/**
	 * 保存编辑关键点计划
	 */
	Map<String, Object> saveEditPointPlan(HttpServletRequest request);

	Map<String, Object> queryJYHStaff(HttpServletRequest request, UIPage pager);

	Boolean editInspector(HttpServletRequest request);
	
	
}
