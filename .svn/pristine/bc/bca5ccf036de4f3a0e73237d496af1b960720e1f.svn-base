package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import util.page.Query;

import com.cableInspection.model.PointModel;

@SuppressWarnings("all")
@Repository
public interface ArrivalDao {
	List<Map> query(Query query);

	List<Map> queryExl(Map map);

	// 任务计划点
	List<PointModel> queryPoint(Map map);

	// 检查记录中上报的
	List<PointModel> queryPoint2(Map map);

	// 任务轨迹点
	List<PointModel> queryPoint1(Map map);
	
	List<PointModel> queryDeptUpLoadPoint(Map map);

	List<Map<String, Object>> getArea();

	List<Map> queryTrouble(Query query);

	Map<String, Object> queryTroubleArrival(Map map);

	List<Map> queryTroubleForExcel(Map map);

	List<Map> queryKeep(Query query);

	List<Map<String, String>> getTimePeriod(String planId);

	List<PointModel> queryForKeep(Map map);

	List<Map> queryKeepForExcel(Map map);

	List<Map<String, Object>> getPlanList(Map<String, Object> params);

	List<Map<String, Object>> getUploadPointList(Map<String, Object> params);

	void updatePointArrivaled(Map<String, Object> point);

	List<PointModel> queryPoint22(Map map2);

	List<PointModel> queryPoint11(Map map2);

	String queryGjPointCount(Map map2);

	List<PointModel> queryPlanPoints(Map map2);

	List<Map<String, Object>> queryKeyPointsArrivaledByTaskId(Map paramsMap);
	
	void addArrival(List<Map> list);
	
	void deleteArrival(Map map);
	
	List<Map> queryArrival(Map map);
	
	List<Map> queryArrivalNOC(Map map);
	
	int checkArrival(Map map);
	
	String getCountPoint(String s);
	
	void addKeyPointsdByTaskId(Map map);

	void deleteKeyPoints(Map conds);
	
	List<Map> queryKeyPointsArrivaled(Map map);
	
	List<Map> queryKeyPointsDept(Map map);
	
	List<Map> getKeepPlanPointById (String s);
	
	void addArrivalSingle(Map map);
	
	List<Map> queryArrivalRateByDate(Query query);
	List<Map> queryArrivalRateBySonArea(Query query);
	List<Map> exportArrivalRateByDate(Map m);
	List<Map> exportArrivalRateBySonArea(Map m);

	List<Map> getAreaList();
	Map<String,Object> ifArrivalPointExist(Map m);
	
	List<Map> getPhotoByKeepPlanId(String s);
	
	List<PointModel> getNotSignNormalByTaskId(Map m);
	
	List<Map> getAreaById(Map m);
	
	List<Map> getSonAreaById(Map m);
	
	void saveArrivalRateTable(Map m);
	
	List<Map> queryArrivalRateTable(Map m);
	
	void delArrivalRateTable();
	
	int getCountStaffIdByDept(Map m);
}
