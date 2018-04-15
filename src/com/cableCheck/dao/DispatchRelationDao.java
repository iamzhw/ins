package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface DispatchRelationDao {
	/**
	 * 查询端子所在设备记录列表
	 */
	public List<Map<String, Object>> queryDispatchRelation(Query query);
	
	List<Map> getAreaList();
	
	/**
	 *  查询同种端子检查记录情况
	 */
	public List<Map<String, Object>> querySamePortRecord(Query query);
	
	/**
	 * 导出端子所在设备记录列表
	 */
	public List<Map<String, Object>> exportPortsRecord(Map  map);

	public List<Map<String, String>> getGridListByAreaId(String AREA_ID);

	public List<Map<String, Object>> getSonAreaListByAreaId(String AREA_ID);

	public List<Map> queryStaff(Query query);

	public void saveDispatchRelation(Map<String, Object> param);

	public int deleteRelation(Map map);

	public List<Map<String, Object>> validateRelation(Map<String, Object> param);
	
	public String getTeamName(Map map);
}
