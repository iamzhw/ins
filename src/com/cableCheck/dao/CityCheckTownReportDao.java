package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface CityCheckTownReportDao {

	List<Map<String, Object>> query(Query query);

	List<Map<String, Object>> queryDown(Map<String, Object> paras);

	List<Map<String, String>> getSonAreaList(String areaId);

}
