package com.inspecthelper.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.Query;
import util.page.UIPage;

/**
 * 巡检设备维护业务层
 *
 * @author lbhu
 * @since 2014-9-2
 * 
 */
@SuppressWarnings("all")
public interface EquipService {

	/**
	 * 查询巡检设备列表
	 * 
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> getEquipList(HttpServletRequest request, UIPage pager);
	
	
	/**
	 * 查询设备巡检记录列表
	 * 
	 * @param query
	 * @return
	 */
	public Map<String, Object> getResInsHistory(HttpServletRequest request, UIPage pager);
	
	
	/**
	 * 获取设备巡检记录导出列表
	 * 
	 * @param query
	 * @return
	 */
	public List<Map> getResInsHistoryList(HttpServletRequest request);
	
	/**
	 * 根据ID获取设备信息
	 * 
	 * @param equipmentId 设备ID
	 * @return
	 */
	public Map getEquip(HttpServletRequest request);
	
	/**
	 * 批量设置健康
	 * 
	 * @param request
	 */
	public void btnhealthy(HttpServletRequest request);
	
	/**
	 * 修改设备信息
	 * 
	 * @param request
	 */
	public void modifyEquip(HttpServletRequest request);
	
	/**
	 * 分配设备检查人员
	 * 
	 * @param map
	 * @return
	 */
	public void allotEquip(HttpServletRequest request);
	
	/**
	 * 批量清除检查员
	 * 
	 * @param map
	 * @return
	 */
	public void btnclearallot(HttpServletRequest request);
	
	/**
	 * 获取所有部门
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> getCompanyList(Map paramMap);
	
	/**
	 * 得到FTTH端口信息
	 * @param paramMap
	 * @return
	 */
	public Map getFtthCount(Map paramMap);
}
