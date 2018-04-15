package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface CheckRecordDao {
	/**
	 * 查询端子所在设备记录列表
	 */
	public List<Map<String, Object>> queryCheckRecord(Query query);
	
	
	/**
	 *  查询同种端子检查记录情况
	 */
	public List<Map<String, Object>> querySamePortRecord(Query query);
	
	/**
	 * 导出端子所在设备记录列表
	 */
	public List<Map<String, Object>> exportPortsRecord(Map  map);
	
}
