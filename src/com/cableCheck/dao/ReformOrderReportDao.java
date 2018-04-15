package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface ReformOrderReportDao {

	List<Map<String, Object>> query(Query query);

	List<Map<String, Object>> queryDown(Map<String, Object> paras);

	List<Map<String, Object>> queryByCity(Query query);

	List<Map<String, Object>> queryDownByCity(Map<String, Object> paras);
	
	List<Map<String, Object>> zgEquipmentQuery(Query query);
	
	List<Map<String, Object>> zgPfEquipmentQuery(Query query);
	
	List<Map<String, Object>> zgHdEquipmentQuery(Query query);
	
	List<Map<String, Object>> zgShEquipmentQuery(Query query);
	
	List<Map<String, Object>> yjEquipmentQuery(Query query);
	
	List<Map<String, Object>> yjPfEquipmentQuery(Query query);
	
	List<Map<String, Object>> yjHdEquipmentQuery(Query query);
	
	List<Map<String, Object>> yjShEquipmentQuery(Query query);
	
	List<Map<String, Object>> bygEquipmentQuery(Query query);
	
	List<Map<String, Object>> bygPfEquipmentQuery(Query query);
	
	List<Map<String, Object>> bygHdEquipmentQuery(Query query);
	
	List<Map<String, Object>> bygShEquipmentQuery(Query query);
	
	
	
	//导出
	List<Map<String, Object>> exportZgEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportZgPfEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportZgHdEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportZgShEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportYjEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportYjPfEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportYjHdEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportYjShEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportBygEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportBygPfEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportBygHdEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportBygShEquipmentQuery(Map param);
	
	Map<String, Object> isArea(String areaId);
	
	//端子
	List<Map<String, Object>> zgEquipmentPortQuery(Query query);
	
	List<Map<String, Object>> zgPfEquipmentPortQuery(Query query);
	
	List<Map<String, Object>> zgHdEquipmentPortQuery(Query query);
	
	List<Map<String, Object>> zgCgEquipmentPortQuery(Query query);
	List<Map<String, Object>> zgFxEquipmentPortQuery(Query query);
	List<Map<String, Object>> zgYjgEquipmentPortQuery(Query query);

	
	List<Map<String, Object>> exportZgEquipmentPortQuery(Map param);
	
	List<Map<String, Object>> exportZgPfEquipmentPortQuery(Map param);
	
	List<Map<String, Object>> exportZgHdEquipmentPortQuery(Map param);
	
	List<Map<String, Object>> exportZgCgEquipmentPortQuery(Map param);
	List<Map<String, Object>> exportZgFxEquipmentPortQuery(Map param);
	List<Map<String, Object>> exportZgYjgEquipmentPortQuery(Map param);
	
	//审核不通过设备查询
	List<Map<String, Object>> zgShAgainstEquipmentQuery(Query query);

	List<Map<String, Object>> yjShAgainstEquipmentQuery(Query query);
	
	List<Map<String, Object>> bygShAgainstEquipmentQuery(Query query);
	
	//审核不通过设备下载
	List<Map<String, Object>> exportZgShAgainstEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportYjShAgainstEquipmentQuery(Map param);
	
	List<Map<String, Object>> exportBygShAgainstEquipmentQuery(Map param);

}
