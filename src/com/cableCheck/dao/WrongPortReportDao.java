package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface WrongPortReportDao {

	List<Map<String, Object>> selArea();

	List<Map<String, Object>> query(Query query);

	List<Map<String, Object>> queryDown(Map<String, Object> paras);

	List<Map<String, Object>> queryPersonalQuality(Query query);

	List<Map<String, Object>> personalCheckDown(Map<String, Object> paras);

}
