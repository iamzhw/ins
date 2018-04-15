package com.roomInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

/**
 * 
 * @ClassName: JobService
 * @Description: 巡检计划业务接口
 * 
 * @author huliubing
 * @since: 2014-8-5
 *
 */
@SuppressWarnings("all")
public interface JobService {

	/**
	 * 获取计划列表
	 * @param map 查询条件
	 * @return 查询结果
	 */
	public Map<String, Object> query(HttpServletRequest request, UIPage pager);

	/**
	 * 保存计划信息
	 * @param request 请求信息
	 * @throws Exception
	 */
	public void insert(HttpServletRequest request) throws Exception;


	/**
	 * 跳转到修改页面
	 * @param request 请求信息
	 * @return
	 */
	public Map<String, Object> edit(HttpServletRequest request);

	/**
	 * 修改计划信息
	 * @param request 请求信息
	 * @throws Exception
	 */
	public void update(HttpServletRequest request) throws Exception;

	/**
	 * 删除计划信息
	 * @param request 请求信息
	 */
	public void delete(HttpServletRequest request);
	
	/**
	 * 停止任务计划
	 * @param request
	 */
	public void stop(HttpServletRequest request);
	

	/**
	 * 获取机房类型列表
	 * @return
	 */
	public List<Map> getRoomTypes();

	/**
	 * 获取周期列表
	 * @return
	 */
	public List<Map> getCircles();
	
	/**
	 * 根据用户的区域ID获取所有子区域列表
	 * @param areaId 用户的区域ID
	 * @return
	 */
	public List<Map> getAreaList(int areaId);
	
	
	/**
	 * 通过周期类型获取计划
	 * @param circleId 周期类型
	 * @return
	 */
	public List<Map> getJobsByCircleId(int circleId);
}
