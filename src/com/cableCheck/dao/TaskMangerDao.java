package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface TaskMangerDao {
	/**
	 * 任务管理-查询
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> query(Query query);
	
	/**
	 * 新增任务-查询
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> addTaskQuery(Query query);
	
	
	/**
	 * 新增任务-查询无分组
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> addTaskQueryNotGroup(Query query);
	
	/**
	 * 设备信息-查询
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> equipmentQuery(Query query);
	
	/**
	 * 设备信息-查询  派发
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> equipmentQuery1(Map<String, Object> map);

	
	/**
	 * 端子信息-查询
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> terminalQuery(Query query);
		
	/**
	 * 端子信息-查询  派发
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> terminalQuery1(Map<String, Object> map);
	
	/**
	 * 查询人员列表
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryHandler(Query query);
	
	/**
	 * 派发任务
	 * @param map
	 */
	public void saveTask(Map<String, Object> map);
	
	/**
	 * 保存任务
	 * @param map
	 */
	public void doSaveTask(Map<String, Object> map);
	
	/**
	 * 保存任务详情
	 * @param map
	 */
	public void doSaveTaskDetail(Map<String, Object> map);
	
	/**
	 * 修改动态端子表状态（派发成功后修改）
	 * @param map
	 */
	public void updateDtsj(Map<String, Object> map);
	
	/**
	 * 根据设备id获取端子数量
	 * @param map
	 * @return
	 */
	public int queryDzNum(Map<String, Object> map);
	
	/**
	 * 根据设备id获取涉及光路数量
	 * @param map
	 * @return
	 */
	public int queryGlNum(Map<String, Object> map);
	
	/**
	 * 根据设备id获取H光路数量
	 * @param map
	 * @return
	 */
	public int queryHNum(Map<String, Object> map);
	
	/**
	 * 设备检查记录
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryDeviceRecords(Query query);
	
	/**
	 * 新增检查任务，分组查询时查询设备id以及端子数量，涉及光路数，h光路数
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryNumAndDeviceIdByGroup(Map<String, Object> map);
	/**
	 * 获取oss实时光路
	 * @param map
	 * @return
	 */
	public List<Map> getOssGlList(Map map);
	
	/**
	 * 获取IOM订单号
	 * @param map
	 * @return
	 */
	public List<Map> getIomOderIdList(Map map);
	
	
	/**
	 * 新增任务-查询
	 * @param paras
	 * @return
	 */
	public List<Map<String, Object>> downTaskQuery(Map<String, Object> paras);
	
	
	/**
	 * 新增任务-查询无分组
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> downTaskQueryNotGroup(Map<String, Object> paras);

	/**
	 * 新增任务所有端子导出-查询无分组
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> downPortQueryNotGroup(Map<String, Object> paras);
	/**
	 * 设备信息-导出
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> downEquipmentQuery(Map<String, Object> paras);
	/**
	 * 端子信息-导出
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> downTerminalQuery(Map<String, Object> paras);

	
	/**
	 * 所有设备所有端子信息-导出
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> downTerminalDZQuery(Map<String, Object> paras);
	

	/**
	 * 通过area_id获取areaName
	 * @param query
	 * @return
	 */
	public String getAreaName(String area_id);

	public List<Map<String, Object>> queryElectron(Query query);

	public List<Map<String, Object>> downElectron(Map<String, Object> map);

	public List<Map<String, Object>> terminalQuery2(Map<String, Object> sbMap);
	
	/**
	 * 承包人派发
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryContractEqp(Map<String, Object> map);
	
	/**
	 * 保存承包任务务
	 * @param map
	 */
	public void doSaveTaskByContract(Map<String, Object> map);
}
