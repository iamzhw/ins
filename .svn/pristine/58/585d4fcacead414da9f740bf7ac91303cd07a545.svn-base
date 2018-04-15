package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
public interface CablePlanDao {
	public List<Map> getPoints();

	public void insertPlan(Map map);

	public void insertDetail(Map map);

	public List<Map> planQuery(Query query);

	public List<Map> getPlanExitCable(Integer plan_id);

	public void deletePlan(Map map);

	public Map getPlan(Map map);

	public List<Map> getPlanDetail(Integer plan_id);

	public void updatePlan(Map map);
	
	/**
	 * 
	 * @Function: com.cableInspection.dao.CablePlanDao.updatePlanIsDistributed
	 * @Description:更新计划分配状态
	 *
	 * @param map
	 *
	 * @date:2014-7-30 下午1:55:15
	 *
	 * @Modification History:
	 * @date:2014-7-30     @author:ning     create
	 */
	public void updatePlanIsDistributed(Map map);

	public void deletePlanDetail(Map map);

	public List<Map> getSpectors(Query query);

	public Integer saveTask(Map taskMap);

	public Integer saveTaskDetail(Map map);

	public List<Map> taskQuery(Query query);
	
	public List<Map> getLineByLineId(Map params);
	/**
	 * 查询关键点计划
	 * @param query
	 * @return
	 */
	public List<Map> queryPoint(Query query);
	/**
	 * 判断计划名称是否已存在
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getPointPlanByName(Map<String, Object> param);
	/**
	 * 根绝计划id查询关键点计划
	 * @param param
	 * @return
	 */
	public Map<String, Object> getPointPlanById(Map<String, Object> param);
	/**
	 * 添加缆线
	 * @param param
	 * @return
	 */
	public void insertPointLine(Map<String, Object> param);
	/**
	 * 添加线与点的关系表
	 * @param pointParam
	 */
	public void insertPointLineDetail(Map<String, Object> pointParam);
	/**
	 * 添加关键点计划
	 * @param param
	 * @return
	 */
	public void insertPointPlan(Map<String, Object> param);
	/**
	 * 添加关键点计划详情
	 * @param param
	 * @return
	 */
	public void insertPointPlanDetail(Map<String, Object> param);
	/**
	 * 查询已选择的关键点数据
	 * @param param
	 * @return
	 */
	public List<Map> searchPointData(Query query);
	/**
	 * 修改线
	 * @param param
	 * @return
	 */
	public void updatePointLine(Map<String, Object> param);
	/**
	 * 删除关键点和线的关系
	 * @param param
	 */
	public void deletePointLineDetail(Map<String, Object> param);
	/**
	 * 修改关键点计划
	 * @param param
	 */
	public void updatePointPlan(Map<String, Object> param);
	/**
	 * 查询编辑的关键点名称是否已存在
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getEditPointPlanByName(Map<String, Object> param);
	/**
	 * 查询线ID
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getPointLineId(Map<String, Object> param);

	public int ifStaffNocorrect(String s);
	
	public Map<String, Object> getSonAreaIdByPlan(String s);
	
	public List<Map> getStaffFromJYH(Map m);
	
	public String getStaffIdByNo(Map m);
	
	public List<Map> getStaffFromDEPT(Map m);
	
	public void editPlanInspector(Map m);
	
	public void editTaskInspector(Map m);
	
}
