package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface CutAndConnOfFiberDao {

	List<Map<String, Object>> query(Query query);

	List<Map<String, Object>> getCityInfo(Map<String, Object> map);

	List<Map<String, Object>> getCityName(Map<String, Object> map);

	List<Map<String, Object>> getCableName(Map<String, Object> map);

	List<Map<String, Object>> getRelayName(Map<String, Object> map);

	List<Map<String, Object>> getCable(String areaId);

	List<Map<String, Object>> getRelay(String cableId);

	List<Map<String, Object>> showRelayDetailInfo(Query query);

	List<Map<String, Object>> getCuttingRecordOfFiber(Query query);

	List<Map<String, Object>> getRecordOfSteps(Query query);

	int delTestDetail(Map<String, Object> map);

	int delTest(String id);

	int updTestInfo(Map<String, Object> map);

	int updTestDetailInfo(Map<String, Object> map);

	List<Map<String, Object>> getListOfSensitiveline(Query query);

	void addTestInfo(Map<String, Object> map);

	void addTestDetailInfo(Map<String, Object> map);

	int delRecordOfFiber(Map<String, Object> map);

	void addRecordOfFiber(Map<String, Object> map);

	void updRecordOfFiber(Map<String, Object> map);

	void delStepData(Map<String, Object> map);

	void addStepData(Map<String, Object> map);

	void updStepData(Map<String, Object> map);

	List<Map<String, Object>> exportTextInfoExcel(Map<String, Object> map);

	String selectRelayId(Map<String, Object> map);

	String selectLineId(Map<String, Object> map);

	String selectCityId(Map<String, Object> map);

	void insertRELAYTESTINFO(Map<String, Object> map);

	List<Map<String, Object>> exportFiberRecordExcel(Map<String, Object> para);
}
