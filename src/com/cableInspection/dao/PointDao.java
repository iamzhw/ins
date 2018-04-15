package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

@SuppressWarnings("all")
public interface PointDao {
	
	/**
	 * 
	 * @return
	 */
	public List<Map> getPoints(Map map);

	public void insertPlan(Map map);

	public void insertDetail(Map map);

	public List<Map> planQuery(Query query);

	public void deletePlan(Map map);

	public Map getPlan(Map map);

	public List<Map> getPlanDetail(Integer plan_id);

	public void updatePlan(Map map);

	public void deletePlanDetail(Map map);

	public List<Map<String, String>> getSpectors(Map map);

	public Integer saveTask(Map taskMap);

	public Integer saveTaskDetail(Map map);

	public List<Map> taskQuery(Query query);

	public Map getAreaInfo(Map Map);
	
	public Map getPointTypeByids(String ids);

	public void updatePlanDistributed(Map map);

	public void deleteOldTask(Map map);

	public List<Map> getInspector(Map map);

	public int checkPlan(Map params);

	public List<Map> billQuery(Query query);

	public List<Map<String, Object>> getAllBillStatus();

	public List<Map<String, Object>> getStatusIdsByBillIds(String ids);

	public void updateBillHandle(Map<String, Object> params);
	
	public void updatePointType(Map<String, Object> params);

	public int isAdmin(Map<String, Object> params);

	public void insertBillFlow(Map<String, Object> flowParams);

	public void saveKeepTime(Map<String, Object> keepTimeParams);

	public List<Map<String, String>> getTimeList(Integer pLAN_ID);

	public Map<String, String> billInfo(String billId);

	public List<Map<String, String>> billPhoto(Map map);

	public List<Map<String, String>> billFlow(String billId);
	
	public List<Map<String, Object>> getUserList(Map<String, String> params);

	public String getAuditorByBillId(String id);

	public String getMaintorByBillId(String id);
	
	public void deleteKeepTime(Map<String, Object> keepTimeParams);

	public List<Map<String, Object>> getRecordByBillId(
			Map<String, Object> params);

	public List<Map<String, Object>> queryPlans(Map<String, Object> params);
	
	public void deleteTaskById(String ids);
	
	public void deleteTaskDetailByTaskId(String ids);

	public List<Map> getKeepSpectors(Query query);
	
	public int ifTaskExists(Map map);
	
	public List<Map> queryTroubleBills(String s);

	public List<Map> queryTroubleBillDetail(Map<String, String> map);
	
	public List<Map> queryHandler(Map map);
	
	public Map getAreaByBillId(String s);
	
	public int getBillStatus(String s);

	public List<Map> billExport(Map<String, Object> map);
	
	public List<Map> getTroubleType();
	
	public Map getPointArea(String s);
	
	public void updatePointTypeByBillId(String s);
	
	public Map workStaffInfo(String s);
	
	public String decodeEqyTypeByBill(String s);
	
	public void add_a_post(Map m);
	
	public List<Map> queryStafFromJYH(Map m);
	
	public String getSonAreaByBillId(String s);

	public List<Map> queryHandlerFromJYH(Map map);
	
	public String getRemarksById(String s);
	
	public Map<String,String> getCoodByBillId(String s);
	
	public void addKeepLinePoints(Map m);
	
	public List<Map> queryKeepLinePoints(Map m);
	
	public String getKeepPlanIDByTask(String s);
	
	public void removeKeepLine(Map m);
}
