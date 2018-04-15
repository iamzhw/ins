package com.outsite.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

public interface IOutsitePlanManageService {
	/**
	 * 查询计划列表
	 * 
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> query_outsite(HttpServletRequest request, UIPage pager);

	/** 外力点看护计划--查询计划列表--详情列表 **/
	public Map<String, Object> query_detail_plan(HttpServletRequest request, UIPage pager);

	/**
	 * 根据计划ID查询任务列表
	 */
	public Map queryTaskList(HttpServletRequest request,UIPage pager);

	/**
	 * 查询计划的时间片段
	 */
	public Map queryTimeFregment(HttpServletRequest request, UIPage pager);

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
    List<Map<String, Object>> get_alllist(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> getflag(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_para_time(Map<String, Object> para);

	// 巡线时长
    String get_max_date(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_all_people(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_no_gps(Map<String, Object> para);

	// 巡线时长
    List<Map<String, Object>> get_judge_time(Map<String, Object> para);

	// 未匹配
    List<Map<String, Object>> get_nomatch(Map<String, Object> para);

	// 在非外力施工（隐患）点的巡检点连续停留超过40分钟；
    List<Map<String, Object>> get_forty_time(Map<String, Object> para);

	/**
	 * 外力点计划插入
	 */
	public void insert_outsite_plan(HttpServletRequest request);

	/**
	 * 外力点计划修该
	 */
	public void update_outsite_plan(HttpServletRequest request);

	/**
	 * 迁移日志修该
	 */
	public void update_outsite_movelog(HttpServletRequest request);

	/**
	 * 查询单个外力点计划
	 * 
	 * @param request
	 * @return
	 */
	public Map<String,Object> query_outsite_single(HttpServletRequest request);

	/**
	 * 计划暂停
	 * 
	 * @param request
	 */
	public void stop(HttpServletRequest request);

	/**
	 * 迁移日志
	 * 
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> query_movelog(HttpServletRequest request, UIPage pager);

	/**
	 * 查询单个迁移日志
	 * 
	 * @param request
	 * @return
	 */
	public Map<String,Object> select_movelog(HttpServletRequest request);

	// 0 GPS未打开
	public void insert_invalid_time(Map<String,Object> map);

	// 查询无效时间图
    List<Map<String, Object>> list_no_picture(HttpServletRequest request);

	/**
	 * 校验看护计划时间
	 * 
	 * @param request
	 * @return
	 */
    public boolean validatePlanTime(HttpServletRequest request);

	/**
	 * 获取外力点看护轨迹
	 */
	List<Map<String, Object>> getOutSitePlanTrack(Map<String, Object> map);

	/**
	 * 查询爱巡线看护员列表
	 */
	List<Map<String, Object>> getOutSitePlanInspector(Map<String, Object> map);

	/**
	 * 根据看护员ID和看护日期查询看护计划ID及外力点ID
	 */
	List<Map<String, Object>> getOutSitePlanInfo(Map<String, Object> params);
}
