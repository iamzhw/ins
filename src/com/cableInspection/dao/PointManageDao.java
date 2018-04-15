package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

@SuppressWarnings("all")
public interface PointManageDao {

	List<Map> query(Query query);

	List<Map<String, Object>> getPointTypes();

	List<Map<String, Object>> getEquipTypes();

	List<Map<String, Object>> getSonAreas(int areaId);

	int isRepeat(Map<String, Object> params);

	void insertPoint(Map<String, Object> params);

	void delete(String ids);

	List<Map<String, String>> getAreaNameById(String area_name);
	
	List<Map<String, String>> getPlanIdByPointId(String ids);
	
	void deletePlanDetailByPointId(String ids);

	void deletePlanById(String id);
	
	List<Map<String, String>> hasPlan(String ids);
	
	void deleteTaskDetailByPointId(String ids);
	
	void deleteTaskByPlanId(String id);

	List<Map<String, String>> queryExistsPoint(String sonAreaId);
	
	List<Map<String, Object>> getPointNoByPointId(Map<String, Object> params);
	
	void deleteRecordByEqpNo (Map<String, Object> params);

	List<Map<String, Object>> queryPoint(Map<String, Object> params);

	void updatePointName(Map<String, Object> params);

	List<Map> queryKeyPoints(Query query);

	List<Map<String, Object>> queryKeyPointsExl(Map<String, Object> params);
	
	List<Map<String, String>> getMntLevel();
	
	Map queryPointById(String s);
	
	void updateCoordinate(Map map);
	
	List<Map> getAreaByParentId(String s);
	
	public Map getPointInfo(String s);
	
	public Map getDeptInfo(String s);
	
	public void addPointRelationship(Map m);
	
	public void editPointDept(Map m);
	
	int ifPointDeptExists(String s);
	
	void updateNormalPoint(Map m);
	
	String getAreaIdByName(String s);

	List<Map<String, String>> getEqpTypeList();
}
