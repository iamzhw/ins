package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface CheckQualityReportDao {

	public List<Map<String, Object>> query(Query query);

	public List<Map<String, Object>> selArea();



	public List<Map<String, Object>> queryDown(Map<String, Object> paras);

	public List<Map<String, Object>> queryChangeEqp(Query query);

	public List<Map<String, Object>> queryPfEqp(Query query);

	public List<Map<String, Object>> queryZqEqp(Query query);

	public List<Map<String, Object>> queryYjEqp(Query query);

	public List<Map<String, Object>> queryBkEqp(Query query);

	public List<Map<String, Object>> queryCheckPort(Query query);

	public List<Map<String, Object>> queryCurrentPort(Query query);

	public List<Map<String, Object>> downChangeEqp(Map<String, Object> paras);

	public List<Map<String, Object>> downPfEqp(Map<String, Object> paras);

	public List<Map<String, Object>> downZqEqp(Map<String, Object> paras);

	public List<Map<String, Object>> downYjEqp(Map<String, Object> paras);

	public List<Map<String, Object>> downBkEqp(Map<String, Object> paras);

	public List<Map<String, Object>> downCheckPort(Map<String, Object> paras);

	public List<Map<String, Object>> downCurrentPort(Map<String, Object> paras);

	public List<Map<String, Object>> queryPromblePort(Query query);

	public List<Map<String, Object>> downPromblePort(Map<String, Object> paras);

	public List<Map<String, Object>> queryHEqp(Query query);

	public List<Map<String, Object>> downHEqp(Map<String, Object> paras);

	public List<Map<String, Object>> downPfHEqp(Map<String, Object> paras);

	public List<Map<String, Object>> queryPfHEqp(Query query);

	public List<Map<String, Object>> querySdEqp(Query query);

	public List<Map<String, Object>> downSdEqp(Map<String, Object> paras);

	public List<Map<String, Object>> getZqEqpInfo(
			Map<String, Object> map);

	public List<Map<String, Object>> getZqPortInfo(
			Map<String, Object> map);

	public List<Map<String, Object>> queryProcess(Map<String, Object> map);

	public List<Map<String, Object>> getZqSdEqpInfo(Map<String, Object> map);

	public List<Map<String, Object>> getZqSdPortInfo(Map<String, Object> map);

	public Map portChecked(Map param);

	public List<Map<String, Object>> queryByCity(Query query);

	public List<Map<String, Object>> queryDownByCity(Map<String, Object> paras);

	public List<Map<String, Object>> queryChangeEqpByCity(Query query);

	public List<Map<String, Object>> querySdEqpByCity(Query query);

	public List<Map<String, Object>> queryBkEqpByCity(Query query);

	public List<Map<String, Object>> queryHEqpByCity(Query query);

	public List<Map<String, Object>> queryPfHEqpByCity(Query query);

	public List<Map<String, Object>> queryYjEqpByCity(Query query);

	public List<Map<String, Object>> queryZqEqpByCity(Query query);

	public List<Map<String, Object>> queryPfEqpByCity(Query query);

	public List<Map<String, Object>> downChangeEqpByCity(
			Map<String, Object> paras);

	public List<Map<String, Object>> downPfEqpByCity(Map<String, Object> paras);

	public List<Map<String, Object>> downZqEqpByCity(Map<String, Object> paras);

	public List<Map<String, Object>> downYjEqpByCity(Map<String, Object> paras);

	public List<Map<String, Object>> downBkEqpByCity(Map<String, Object> paras);

	public List<Map<String, Object>> downHEqpByCity(Map<String, Object> paras);

	public List<Map<String, Object>> downPfHEqpByCity(Map<String, Object> paras);

	public List<Map<String, Object>> downSdEqpByCity(Map<String, Object> paras);

	public List<Map<String, Object>> queryCheckPortByCity(Query query);

	public List<Map<String, Object>> queryPromblePortByCity(Query query);

	public List<Map<String, Object>> downCheckPortByCity(
			Map<String, Object> paras);

	public List<Map<String, Object>> downPromblePortByCity(
			Map<String, Object> paras);

	public List<Map<String, Object>> queryCheckEqp(Query query);

	public List<Map<String, Object>> queryCheckEqpByCity(Query query);

	public List<Map<String, Object>> downCheckEqpByCity(
			Map<String, Object> paras);

	public List<Map<String, Object>> downCheckEqp(Map<String, Object> paras);



	public List<Map<String, Object>> queryChangeEqpNum(Query query);

	public List<Map<String, Object>> queryPfEqpNum(Query query);

	public List<Map<String, Object>> queryYjEqpNum(Query query);

	public List<Map<String, Object>> queryBkEqpNum(Query query);

	public List<Map<String, Object>> queryBkEqpNumByCity(Query query);

	public List<Map<String, Object>> queryYjEqpNumByCity(Query query);

	public List<Map<String, Object>> queryPfEqpNumByCity(Query query);

	public List<Map<String, Object>> queryChangeNumEqpByCity(Query query);

	public List<Map<String, Object>> queryCheckEqpNumByCity(Query query);

	public List<Map<String, Object>> queryCheckEqpNum(Query query);

	public List<Map<String, Object>> queryDtsjPortByCity(Query query);

	public List<Map<String, Object>> queryDtsjPort(Query query);

	public List<Map<String, Object>> downDtsjPort(Map<String, Object> paras);

	public List<Map<String, Object>> downDtsjPortByCity(
			Map<String, Object> paras);



	
	


}
