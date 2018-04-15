package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

/** 
 * @author wangxy
 * @version 创建时间：2016年7月26日 下午6:19:16 
 * 类说明 
 */
public interface DoneTaskDao {
	/**
	 * 获取已办任务
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> queryDoneTask(Query query);
	/**
	 * 下载查询所有已办任务
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryDoneTaskForExcel(Map<String, Object> map);
	
	 
	
	
	
	
	/**
	 * 导出检查记录Excel
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> exportInfo(Map<String, String> map);
	
	/**
	 * 导出检查端子清单
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> exportPortInfo(Map<String, String> map);
	/**
	 * 统计派发端子数
	 * @param task_id
	 * @return
	 */
	public String countBeforeCheck(String task_id);
	/**
	 * 派发H端子数
	 * @param task_id
	 * @return
	 */
	public String countBeforeCheckH(String task_id);
	/**
	 * 检查端子数
	 * @param task_id
	 * @return
	 */
	public String countChecked(String task_id);
	/**
	 * 检查H端子数
	 * @param task_id
	 * @return
	 */
	public String countCheckedH(String task_id);
	/**
	 * 错误端子数
	 * @param task_id
	 * @return
	 */
	public String countErrorCheck(String task_id);
	/**
	 * 错误H端子数
	 * @param task_id
	 * @return
	 */
	public String countErrorCheckH(String task_id);
	/**
	  * 查询任务来源
	  * @param map
	  * @return
	  */
	public Map<String,Object> getTaskByTaskId(String taskId);
	/**
	  * 设备信息(周期性检查)
	  * @param params
	  * @return
	  */
	public List<Map<String,Object>> getMyTaskEqpPhotoForZq(Map<String, Object> map);
	/**
	  * 查设备图片(周期性检查)
	  * @param map
	  * @return
	  */
	 public List<Map<String,Object>> getEqpPhoto(Map<String,Object> map);
	 /**
	  * 根据设备id查询端子信息(周期性检查)
	  * @param eqpId
	  * @return
	  */
	public List<Map<String, Object>> queryPortDetailForZq(Map<String, Object> map);
	public List<Map<String, Object>> queryPortDetailNewForZq(Map<String, Object> map);
	/**
	 * 获取端子照片(问题上报)
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> queryPortPhoto(Map map);
	/**
	 * 查询工单流程
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryProcess(Map<String, Object> map);
	/**
	  * 设备信息(问题上报)
	  * @param params
	  * @return
	  */
	public List<Map<String,Object>> queryTroubleTaskEqp(Map<String, Object> params);
	/**
	 * 获取端子信息(问题上报)
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryTroubleTaskPort(Map map);
	List<Map<String, Object>> queryHandler(Query query);
	List<Map<String, Object>> taskQuery(Map<String, Object> map);
	void upTask(Map<String, Object> map);
	List<Map<String, Object>> terminalQuery1(Map<String, Object> Map);
	void doSaveTaskDetail(Map map);
	void updateDtsj(Map<String, Object> map);
	Map<String,Object> queryTaskInfo(Map map);
	void saveTroubleTask(Map troubleTaskMap);
	Map portChecked(Map map);
	int getIfNeedZg(String TASK_ID);
	List<Map<String, Object>> queryNoTroubleTaskPort(Map<String, Object> map);
	//直接归档获取端子
	public List<Map<String, Object>> queryTroubleTaskPortToFinish(Map map);
	List<Map<String, Object>> querySecondTask(Query query);
	List<Map<String, Object>> exportSecondTaskInfo(Map doneParamMap);
	
	/**
	 * 按工单导出
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> exportExcelByPOrder(Map<String, String> map);
}
