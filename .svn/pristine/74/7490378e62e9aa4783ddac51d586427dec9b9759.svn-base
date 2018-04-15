package com.cableInspection.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import util.page.UIPage;

@SuppressWarnings("all")
public interface PointService {
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	List<Map> getPoints(HttpServletRequest request);

	Boolean save(HttpServletRequest request);

	Map<String, Object> planQuery(HttpServletRequest request, UIPage pager);

	void deletePlan(HttpServletRequest request);

	Map<String, Object> editPlan(HttpServletRequest request);

	Map<String, Object> getPlanDetail(HttpServletRequest request);

	Boolean updatePlan(HttpServletRequest request);

	List<Map<String, String>> getSpectors(HttpServletRequest request);

	Boolean saveTask(HttpServletRequest request);

	Map<String, Object> taskQuery(HttpServletRequest request, UIPage pager);

	Map<String, Object> getUserArea(HttpServletRequest request);

	Boolean checkPlan(HttpServletRequest request);

	Map<String, Object> billQuery(HttpServletRequest request, UIPage pager);

	Map<String, Object> indexBill(HttpServletRequest request);

	String handleBill(HttpServletRequest request);

	Map<String, Object> selectBillStaff(HttpServletRequest request);

	Map<String, Object> billInfo(HttpServletRequest request);

	Map<String, Object> billFlow(HttpServletRequest request);
	
	/**
	 * 
	 * @Function: com.cableInspection.service.PointService.editKeepPlan
	 * @Description:看护任务编辑
	 *
	 * @param request
	 * @return
	 *
	 * @date:2014-7-30 下午5:24:09
	 *
	 * @Modification History:
	 * @date:2014-7-30     @author:Administrator     create
	 */
	Map<String, Object> editKeepPlan(HttpServletRequest request);

	Boolean updatePointKeep(HttpServletRequest request);

	String queryPlans(HttpServletRequest request);
	
	JSONObject deleteTaskById(HttpServletRequest request);

	Map<String, Object> getRole(HttpServletRequest request);

	Map<String, Object> getKeepSpectors(HttpServletRequest request, UIPage pager);

	void billExport(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	List<Map> getTroubleType();
}
