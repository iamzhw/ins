package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

import com.cableInspection.model.EquipmentModel;

@SuppressWarnings(value = { "all" })
public interface SynchronizePointDao {

	List<Map<String, Object>> queryJndiList();

	List<EquipmentModel> queryEquipmentList(Query query);

	int isEquipmentExist(Map map);

	void saveEquipmentInfo(Map map);

	/**
	 * 查询设备点等级
	 */
	List<EquipmentModel> queryEquipmentLevelList(Query query);

	/**
	 * 更新设备点等级字段
	 */
	void updateEquipmentLevel(Map map);
	/**
	 * 管理区中间表
	 */
	void truncateTableArea(Map map);
	/**
	 * 管理区中间表
	 */
	void insertTableArea(String jndi);

	void createOssEquipment(Map map);
	
	String getAreaId();
}
