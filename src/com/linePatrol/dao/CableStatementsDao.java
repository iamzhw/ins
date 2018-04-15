package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface CableStatementsDao{

	List<Map<String, Object>> BaseInfoOfReport203(Map<String, Object> map);

	List<Map<String, Object>> report203Query(Query query);

	List<Map<String, Object>> report207Query(Query query);

	List<Map<String, Object>> get207collection(Query query);

	List<Map<String, Object>> report204Query(Query query);

	List<Map<String, Object>> get204collection(Query query);

	void delReport203(Map<String, Object> map);

	void delReport204(Map<String, Object> map);

	void delReport207(Map<String, Object> map);

	void updReport203Info(Map<String, Object> map);

	void updReport204Info(Map<String, Object> map);

	void updReport207Info(Map<String, Object> map);

	List<Map<String, Object>> getFYPbyPart(Map<String, Object> map);

	void addReport203(Map<String, Object> map);

	void addReport204(Map<String, Object> map);

	List<Map<String, Object>> getCityName(Map<String, Object> map);

	List<Map<String, Object>> getCableName(Map<String, Object> map);

	List<Map<String, Object>> getRelayName(Map<String, Object> map);

}
