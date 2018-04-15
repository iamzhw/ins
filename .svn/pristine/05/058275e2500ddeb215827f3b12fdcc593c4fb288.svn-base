package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface PatrolPlanDao {

	public List<Map> planQuery(Query query);
	
	public void deletePlan(Map map);

	/**
	 * 获取计划
	 * @param map
	 * @return
	 */
	public Map getPlan(Map map);

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
	public int updatePlanIsDistributed(Map map);
	
	/**
	 * 获取承包人
	 * @param parameter
	 * @return
	 */
	List<Map<String, String>> getContractorListBySonAreaId(String SON_AREA_ID);
	
	/**
	 * 获取承包人
	 * @param parameter
	 * @return
	 */
	List<Map<String, String>> getContractorListByAreaId(String AREA_ID);

	/**
	 * 获取市的三分之一承包人
	 */
	List<Map<String,Object>> getThirdContractorListByAreaId(String AREA_ID);
	
	/**
	 * 根据承包人查询可用设备
	 * @param string
	 * @return
	 */
	public List<Map<String, Object>> getUseFullEquipmentByPlanInfo(Map<String, Object> param);

	public Integer saveTask(Map taskMap);
	/**
	 * 判断计划名称是否已存在
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getPatrolPlanByName(Map<String, Object> param);
	/**
	 * 添加计划
	 * @param param
	 * @return
	 */
	public void insertPatrolPlan(Map<String, Object> param);
	/**
	 * 根绝计划id查询计划
	 * @param param
	 * @return
	 */
	public Map<String, Object> getPatrolPlanById(Map<String, Object> param);
	
	/**
	 * 查询编辑的计划名称是否已存在
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getEditPatrolPlanByName(Map<String, Object> param);
	
	/**
	 * 修改关键点计划
	 * @param param
	 */
	public int updatePatrolPlan(Map<String, Object> param);

	public List<Map> getSpectors(Query query);

	public List<Map<String, Object>> getUseFullEquipmentForTaskByPlanInfo(Map<String, Object> param);

	public List<Map<String, Object>> getGridListByAreaId(Map<String, Object> param);

	public List<Map<String, String>> getContractorListByGridId(Integer GRID_ID);

	public List<Map<String, Object>> getGridEquipmentForTaskByContractor(Map searchMap);

	public List<Map<String, Object>> getContractorSonAreaIDListByAreaId(String AREA_ID);

	public Map<String, Object> getOneEquipmentOfOneContractorOfSonAreaBySonAreaId(String contractorSonAreaId);

	public List<Map<String, Object>> getSonAreaIDListByAreaId(String AREA_ID);
	
	public List<Map<String,Object>> getCheckedEquipmentBySonAreaId(String AREA_ID);

	public Map<String, Object> getFourthInspectorByAreaId(String AREA_ID);
	
	public Map<String, Object> getSecondInspectorByGridId(Integer GRID_ID);

	public Map<String, Object> getInspectorsByMap(Map map);

	public List<Map<String, Object>> getUseFullEquipmentForTaskByPlanMap(Map searchMap);

	public int saveEditPatrolPlanRule(Map<String, Object> param);
	
	/**
	 * 根据维护网格获取相应检查人
	 * @param whwgId
	 * @return
	 */
	public List<Map<String, Object>> getStaffListByWHWGId(String whwgId);
	
	/**
	 * 获取规则
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getRule(Map<String, Object> map);
	
	/**
	 * 根据任务计划id获取到规则
	 * @param plan_id
	 * @return
	 */
	public List<Map<String, Object>> getRuleByPlanId(String plan_id);
	
	/**
	 * 根据规则获取设备（网格检查）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> geteqListByGridRule(Map<String, Object> map);
	
	public List<Map<String, Object>> geteqListDead(Map<String, Object> map);
	
	/**
	 * 根据规则获取设备（承包人检查）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> geteqListByContactorRule(Map<String, Object> map);
	
	/**
	 * 根据规则获取设备（市县公司专业中心检查）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> geteqListByCountiesRule(Map<String, Object> map);
	
	/**
	 * 根据规则获取设备（市公司资源中心检查）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> geteqListByCityRule(Map<String, Object> map);
	
	/**
	 * 将通过规则获取的设备插入新表，用于生成任务
	 * @param map
	 */
	public void inserteqForPlanByRule(Map<String, Object> map);
	
	/**
	 * 获取生成任务界面展示数据（其他的检查）
	 * @param planId
	 * @return
	 */
	public List<Map<String, Object>> getEqByPlanAndRuleForNewTask(Query query);
	
	public String getStaffName(Map map);
	
	public String getGridName(Map map);
	
	public String getStaffId(Map map);
	
	
	/**
	 * 获取生成任务界面展示数据(承包人检查)
	 * @param planId
	 * @return
	 */
	public List<Map<String, Object>> getEqByPlanAndRuleForNewTask2(Query query);
	
	/**
	 * 根据plan_id删除设备
	 * @param plan_id
	 */
	public void deleteForPlanByRule(String plan_id);
	
	/**
	 * 根据plan_id获取任务表中任务
	 * @param plan_id
	 * @return
	 */
	public List<Map<String, Object>> queryTaskByPlanId(String plan_id);
	
	/**
	 * 根据plan_id获取设备
	 * @param plan_id
	 * @return
	 */
	public List<Map<String, Object>> queryEqListByPlanId(Map<String, Object> map);
	
	/**
	 * 根据身份证号获取staff_id
	 * @return
	 */
	public Map<String, Object> getStaffIdByIdentify(String identify);
	
	/**
	 * 更新设备状态
	 * @param map
	 */
	public void updateEqState(Map<String, Object> map);
	
	/**
	 * 获取设备
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryEq(Query query);
	
	/**
	 * 删除设备
	 * @param ids
	 */
	public void deleteEq(Map<String, Object> map);
	
	/**
	 * 查询变动量最大的设备
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryMaxBDNum(Map<String, Object> map);
	
	/**
	 * 将通过规则获取的设备插入临时表，用于预览
	 * @param map
	 */
	public void inserteqForPlanByRuleTem(Map<String, Object> map);
	
	/**
	 * 通过create_staff从临时表中获取设备
	 * @param check_staff
	 * @return
	 */
	public List<Map<String, Object>> queryEqByPlanRuleTem(Query query);
	
	public List<Map<String, Object>> getGridNum(Query query);
	
	/**
	 * 删除临时表设备
	 * @param map
	 */
	public void deleteEqTem(Map<String, Object> map);
	
	/**
	 * 删除设备（预览）
	 * @param ids
	 */
	public void deleteEqForPreview(Map<String, Object> map);
	public void addSureSelect(Map<String, Object> map);
	/**
	 * 获取设备（预览新增）
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryEqForPreview(Query query);
	
	/**
	 * 新增设备（预览）
	 * @param map
	 */
	public void addEqForPreview(Map<String, Object> map);
	
	/**
	 * 生成plan_id
	 * @return
	 */
	public String queryPlanId();
	
	/**
	 * 新增计划时将临时表中的设备插入任务用设备表
	 * @param map
	 */
	public void insertRuleByTem(Map<String, Object> map);
	
	/**
	 * 查询规则（用于页面展示）
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryRule(Query query);
	
	/**
	 * 获取规则（键值）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getRuleForAdd(Map<String, Object> map);
	
	/**
	 * 更新规则
	 * @param map
	 */
	public void updateRule(Map<String, Object> map);
	
	/**
	 * 生成task_id
	 * @return
	 */
	public String queryTaskId();
	
	/**
	 * 获取网格id下的设备数
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryEqpGroupGrid(Query query);
	
	
	public List getGridIdByRelation(Map map);
	
	public String getStaffIdByRelation(Map map);
}
