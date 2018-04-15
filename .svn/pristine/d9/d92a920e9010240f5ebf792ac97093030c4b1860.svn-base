package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface CoordinateDao {

	List<Map<String, Object>> query(Query query);

	List<Map<String, Object>> getDetail(String recordId);

	List<Map<String, String>> getPhotoListByRecordId(String recordId);

	void updatePointCoordinate(Map<String, Object> params);

	List<Map<String, Object>> queryPointByEquipCode(Map<String, Object> params);

	void commitRecord(Map<String, Object> params);

	void deleteRecordPhoto(Map<String, Object> params);
	
	void deleteRecord(Map<String, Object> deleteMap);

	List<Map<String, Object>> exportPointsList(Map<String, Object> map);

	List<Map<String, Object>> queryRecordByEquipCode(Map<String, Object> params);

	List<Map<String, Object>> checkPointRecordList(String recordIds);
	
	List<Map<String, Object>> querySumOfCood(Map m);

}
