package com.outsite.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface IOutsitePlanManageDao {
	/**
	 * 查询计划列表
	 * 
	 * @param query
	 * @return
	 */
	public List query_outsite(Query query); 

	/** 外力点看护计划--查询计划列表--详情列表 **/
	public List query_detail_plan(Query query); 
	
	// 选择外力点列表
    List<Map<String, Object>> getout_site(Map<String, Object> para);

	// 选择看护人员列表
    List<Map<String, Object>> getkan_name(Map<String, Object> para);
    
	// 选择看护时间列表
    List<Map<String, Object>> getPartTimeList(Map<String, Object> para);
    
	// 选择巡线人员列表
    List<Map<String, Object>> getxunxian_data(Map<String, Object> para);

	// 选择监管人员列表
    List<Map<String, Object>> getjianguan_name(Map<String, Object> para);

	// 查看看护时间
    List<Map<String, Object>> look_time(Map<String, Object> para);

	// 测试巡线时长
    List<Map<String, Object>> test(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> test_con(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> getflag(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_para_time(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_all_people(Map<String, Object> para);

	// 巡线时长
    String get_max_date(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_no_gps(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_alllist(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_judge_time(Map<String, Object> para);

	// 未匹配
    List<Map<String, Object>> get_nomatch(Map<String, Object> para);

	// 在非外力施工（隐患）点的巡检点连续停留超过40分钟；
    List<Map<String, Object>> get_forty_time(Map<String, Object> para);

	// 获取计划最大ID
    Map<String, Object> getMax_id(Map<String, Object> para);

	/**
	 * 外力点计划新增
	 */
	public void insert_outsite_plan(Map<String,Object> map);

	/**
	 * 获取计划ID
	 * 
	 * @return
	 */
	public int getOutsitPlanId();

	/**
	 * 外力点计划新增--详情
	 */
	public void insert_outsite_plan_detail(Map<String,Object> map);

	/**
	 * 外力点计划修该
	 */
	public void update_outsite_plan(Map<String,Object> map);

	/**
	 * 查询单个外力点计划
	 * 
	 * @param map
	 * @return
	 */
	public Map<String,Object> query_outsite_single(Map<String,Object> map);

	/**
	 * 计划修改（暂停和开始）
	 * 
	 * @param map
	 */
	public void changePlan(Map<String,Object> map);

	public List queryPlans(Query query);

	/**
	 * 查询任务列表
	 * 
	 * @param query
	 * @return
	 */
	public List  queryTaskList(Query query);

	/**
	 * 查询时间片段
	 * 
	 * @param query
	 * @return
	 */
	public List  queryTimeFregment(Query query);

	/**
	 * 向时间模板中插入数据
	 * 
	 * @param map
	 */
	public void insert_time_model(Map<String,Object> map);

	/**
	 * 向时间模板中数据修该
	 */
	public void update_time_model(Map<String,Object> map);

	/**
	 * 查询迁移日志
	 * 
	 * @param query
	 * @return
	 */
	public List query_movelog(Query query);

	/**
	 * 查询单个迁移日志
	 * 
	 * @param map
	 * @return
	 */
	public Map<String,Object> select_movelog(Map<String,Object> map);

	/**
	 * 迁移日志修该
	 */
	public void update_outsite_movelog(Map<String,Object> map);

	// 0 GPS未打开
	public void insert_invalid_time(Map<String,Object> map);

	// 查询无效时间图
    List<Map<String, Object>> list_no_picture(Map<String, Object> params);

	/**
	 * 查询计划数量
	 * 
	 * @param param
	 * @return
	 */
    public int queryOutsitePlanCount(Map<String, Object> param);

	/**
	 * 查询爱巡线看护员列表
	 */
	List<Map<String, Object>> getOutSitePlanInspector(Map<String, Object> params);

	/**
	 * 获取外力点看护轨迹
	 */
	List<Map<String, Object>> getOutSitePlanTrack(Map<String, Object> params);

	/**
	 * 根据看护员ID和看护日期查询看护计划ID及外力点ID
	 */
	List<Map<String, Object>> getOutSitePlanInfo(Map<String, Object> params);
}
