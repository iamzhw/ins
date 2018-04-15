package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;


@SuppressWarnings("all")
@Repository
public interface StepPartTaskDao {
	
	/**
	 * 步巡段查询
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> query(Query query);
	
	/**
	 * 查询未被分配任务的人员频次---非地标,过滤本周期之内生成的步巡段
	 * @return
	 */
	public List<Map<String,Object>> selNoLMTaskPeople(Map<String,Object> map);
	
	/**
	 * 查询未被分配任务的人员频次---非地标非路由点的
	 * @return
	 */
	public List<Map<String,Object>> selNoLMNoRouteTaskEquip(Map<String,Object> map);
	
	/**
	 * 根据地标频次去查询未分配地标任务人员,过滤本周期之内生成的步巡段
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selLMTaskPeople(Map<String,Object> map);
	
	/**
	 * 查询未分配地标任务的人员(非路由)
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selLMNoRouteTaskEquip(Map<String,Object> map);
	
	/**
	 * 查询地标类型的id
	 * @return
	 */
	public String selLMTypeId();
	
	/**
	 * 查询该步巡段是否包含地标
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selIsContainLandMark(Map<String,Object> map);
	
	/**
	 * 查询任务点表中这条步巡段中是否包含地标
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selIsContainLankMarkInEquip(Map<String,Object> map);
	
	/**
	 * 插值到步巡任务表非地标
	 * @param map
	 */
	public void insertStepPartTask(Map<String, Object> map);
		
	/**
	 * 查询该步巡段是否包含非地标点
	 * @return
	 */
	public List<Map<String,Object>> selIsContainNoLandMark(Map<String,Object> map);
	
	/**
	 * 查询任务点中这条步巡段中是否包含非地标点
	 * @return
	 */
	public List<Map<String,Object>> selIsContainNoLangMarkInEquip(Map<String,Object> map);
	
	/**
	 * 查询地标的周期
	 * @return
	 */
	public String selCircleByLandMark();
	
	/**
	 * 将多出来的地标任务点插入到历史记录表中备份
	 * @param map
	 */
	public void insertToTaskHis(Map<String,Object> map);
	
	/**
	 * 将多出来的非地标任务点插入历史记录表中备份
	 * @param map
	 */
	public void intoTaskHisNoLM(Map<String,Object> map);
	
	/**
	 * 将多出来的任务点删除掉
	 * @param map
	 */
	public void delTaskEquipLM(Map<String,Object> map);
	
	/**
	 * 将多出来的非地标任务点给删除掉
	 * @param map
	 */
	public void delTaskEquipNoLM(Map<String,Object> map);
	
	/**
	 * 将新加入的地标点给添加到设施任务关联表中去
	 * @param map
	 */
	public void intoTaskEquipAddLM(Map<String,Object> map);
	
	/**
	 * 插入设施任务表新的非地标设施点记录
	 * @param map
	 */
	public void intoTaskEquipAddNoLM(Map<String,Object> map);
	
	/**
	 * 插入地标任务
	 * @param map
	 */
	public void insertLMStepPartTask(Map<String,Object> map);
	
	/**
	 * 将删除步巡段的点先移动到历史表中去
	 */
	public void IntoHisTaskEquipDelAllot();
	
	/**
	 * 删除掉不存在的步巡段的点
	 */
	public void removeStepPartRecord();
	
	/**
	 * 根据任务去查询所有非地标的步巡段
	 * @return
	 */
	public List<Map<String,Object>> selNoLMStepPartBysTask(Map<String,Object> map);
	
	/**
	 * 根据任务去查询所有地标的步巡段
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selLMStepPartTask(Map<String,Object> map); 
	
	/**
	 * 根据步巡段查询当前人员频次底下是否有非地标的任务
	 * @param allot_id
	 * @return
	 */
	public List<Map<String,Object>> selIsTaskByAllotID(Map<String,Object> map);
	
	/**
	 * 根据步巡段查询出是否被分配地标任务
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selLMTaskByMap(Map<String,Object> map);
	
	/**
	 * 根据任务id去查询步巡段名称及相关信息
	 * @param task_id
	 * @return
	 */
	public List<Map<String,Object>> selPartNameByTaskID(String task_id);
	
	/**
	 * 根据人员id去查询有效任务
	 * @param staff_id
	 * @return
	 */
	public List<Map<String,Object>> selEffectiveTaskByStaffID(String staff_id);
	
	/**
	 * 查询各种设施类型
	 * @return
	 */
	public List<Map<String,Object>> selFacType();
	
	/**
	 * 根据设施类型id查询相对应的错误
	 * @return
	 */
	public List selErrorTypesByTypeID(String equip_type_id);
	
	/**
	 * 根据人员id,设施类型，任务id查询出该类型总数、已完成、有问题、各种问题数量
	 * @param map
	 * @return
	 */
	public Map<String,Object> selErrorNumber(Map<String,Object> map);
	
	/**
	 * 插入更改过后的非路由点到历史表
	 * @param equip_id
	 */
	public void intoRelationTaskEquip(int equip_id);
	
	/**
	 * 根据更改过后的非路由点删除在任务点表中的关系
	 * @param equip_id
	 */
	public void delRelationTaskEquip(int equip_id);
	
	/**
	 * 将任务点表中多余的非路由任务点插入到历史表中去
	 */
	public void intoHisNoRouteTaskEquip();
	
	/**
	 * 删除任务点表中多余的非路由设施点
	 */
	public void delNoRouteTaskEquip();
	
	/**
	 * 插入新增的非地标非路由点到任务点表中按步巡段id来
	 * @param map
	 */
	public void intoTaskHisNoLMRoute(Map<String,Object> map);
	
	/**
	 * 插入新增的地标非路由点到任务点表中按步巡段id来
	 * @param map
	 */
	public void intoTaskEquipAddLMRoute(Map<String,Object> map);
	
	/**
	 * 删除不存在的路由点
	 */
	public void delLostEquip();
	
	/**
	 * 删除未挂上路由点的非路由点
	 */
	public void delLostEquipRouteEquip();
	
	/**
	 * 插入非路由非地标的任务点根据步巡段
	 * @param map
	 */
	public void intoTaskNoRouthLmEquipByAllotID(Map<String,Object> map);
	
	/**
	 * 插入非路由地标的任务点根据步巡段
	 * @param map
	 */
	public void intoTaskRouthLmEquipByAllotID(Map<String,Object> map);
	

	public List<Map<String, Object>> getTaskTime(Map<String, Object> map);
	
	/**
	 * 查询该步巡段是否包含非路由非地标点
	 * @return
	 */
	public List<Map<String,Object>> selIsContainNoRouthLandMark(Map<String,Object> map);
	
	/**
	 * 查询该步巡段是否包含非路由地标点
	 * @return
	 */
	public List<Map<String,Object>> selIsContainRouthLandMark(Map<String,Object> map);
	
	/**
	 * 查询任务id
	 * @return
	 */
	public String selTaskId();
	
	/**
	 * 查询步巡任务点中之前未过期任务的步巡点是否包含非地标
	 * @return
	 */
	public List<Map<String,Object>> selTaskEquipHasNoLM(Map<String,Object> map);
	
	/**
	 * 查询步巡任务点中之前未过期任务的步巡点是否包含地标 
	 * @return
	 */
	public List<Map<String,Object>> selTaskEquipHasLM(Map<String,Object> map);
	
    /**
     * 凭借人员周期去查找未过期任务
     * @param map
     * @return
     */
    public Map<String,Object> selTaskByPersionAndCircle(Map<String,Object> map);
	
    /**
     * 根据步巡段id和周期修改任务点表中任务id
     * @param map
     */
    public void upTaskIdFromTaskEquip(Map<String,Object> map);
    
    /**
     * 把多余的非路由地标任务点插入到历史表中
     * @param map
     */
    public void intoHisTaskLMEquipNoRouth(Map<String,Object> map);
    
    /**
     * 将刚刚备份的非路由地标点删除
     * @param map
     */
    public void delHisTaskLMEquipNoRouth(Map<String,Object> map);
    
    /**
     * 将多余的非路由非地标点备份到历史表中
     * @param map
     */
    public void intoHisTaskEquipNoRouthLM(Map<String,Object> map);
    
    /**
     * 将刚刚备份的非路由非地标任务点删除
     * @param map
     */
    public void delHisTaskEquipNoRouthLM(Map<String,Object> map);
    
    /**
     * 查询上一个周期中存不存在统计任务数据，如果存在就不统计当前干线等级的任务
     * @param map
     * @return
     */
    public List<Map<String,Object>> selStepStatisticsInLastCircle(Map<String,Object> map);
    
    /**
     * 插入步巡统计表
     * @param map
     */
    public int intoStepStatistics(Map<String,Object> map);
    
    /**
     * 插入步巡统计类型表
     * @param map
     */
    public int intoStepStatisticsType(Map<String,Object> map);
    
    /**
     * 删除当前周期的步巡错误统计数据
     * @param map
     * @return
     */
    public int delStepStatisticsType(Map<String,Object> map);
    
    /**
     * 删除当前周期的步巡统计报表数据
     * @param map
     * @return
     */
    public int delTaskStatistics(Map<String,Object> map);
    
}
