package com.cableCheck.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

@SuppressWarnings("all")
public interface PatrolPlanService {

	Map<String, Object> planQuery(HttpServletRequest request, UIPage pager);

	void deletePlan(HttpServletRequest request);

	Boolean saveTask(HttpServletRequest request);
	
	Boolean createTask(HttpServletRequest request);
	/**
	 * 保存计划
	 * @param request
	 * @return
	 */
	Map<String, Object> savePatrolPlan(HttpServletRequest request);
	
	/**
	 * 显示页面
	 * @param request 
	 * @return
	 */
	Map<String, Object> showEditPatrolPlan(HttpServletRequest request);
	/**
	 * 保存编辑计划
	 */
	Map<String, Object> saveEditPatrolPlan(HttpServletRequest request);

	/**
	 * 获取承包人
	 * @param parameter
	 * @return
	 */
	List<Map<String, String>> getContractorListBySonAreaId(String parameter);
	
	Map<String, Object> getSpectors(HttpServletRequest request, UIPage pager);

	Map<String, Object> saveEditPatrolPlanRule(HttpServletRequest request);

	/**
	 * 根据维护网格获取检查人
	 * @param whwgId
	 * @return
	 */
	public List<Map<String, Object>> getStaffListByWHWGId(String whwgId);
	
	/**
	 * 获取规则
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getRule(Map<String, Object> map);
	
	/**
	 * 生成任务
	 * @param request
	 * @return
	 */
	public void createNewTask(HttpServletRequest request);
	
	/**
	 * 获取生成任务页面数据
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> getEqByPlanAndRuleForNewTask(HttpServletRequest request, UIPage pager);
	
	/**
	 * 派发任务
	 * @param request
	 * @return
	 */
	public String distributeTask(HttpServletRequest request);
	
	/**
	 * 查询设备
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> queryEq(HttpServletRequest request, UIPage pager);
	
	/**
	 * 删除设备
	 * @param request
	 * @return
	 */
	public String deleteEq(HttpServletRequest request);
	
	/**
	 * 新增任务计划页面，设备预览
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> saveEqByPlanRule(HttpServletRequest request);
	
	/**
	 * 查询预览设备
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> queryEqByPlanRule(HttpServletRequest request,UIPage pager);
	
	
	
	public Map<String, Object> getGridNum(HttpServletRequest request,UIPage pager);
	/**
	 * 删除设备（预览）
	 * @param request
	 * @return
	 */
	public String deleteEqForPreview(HttpServletRequest request);
	public String addSureSelect(HttpServletRequest request);
	/**
	 * 查询设备(新增预览)
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> queryEqForPreview(HttpServletRequest request, UIPage pager);
	
	/**
	 * 新增设备（预览）
	 * @param request
	 * @return
	 */
	public String addEqForPreview(HttpServletRequest request);
	
	/**
	 * 查询规则（用于页面展示）
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> queryRule(HttpServletRequest request,UIPage pager);
	
	/**
	 * 获取规则（键值）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getRuleForAdd(Map<String, Object> map);
	
	/**
	 * 更新规则
	 * @param request
	 * @return
	 */
	public String updateRule(HttpServletRequest request);
	
	/**
	 * 查询计划是否已生成任务
	 * @param request
	 * @return
	 */
	public String queryIfHavaTask(HttpServletRequest request);
	
	/**
	 * 根据网格id查询设备数
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> queryEqpGroupGrid(HttpServletRequest request,UIPage pager);
}
