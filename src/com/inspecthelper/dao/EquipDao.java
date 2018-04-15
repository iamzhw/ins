package com.inspecthelper.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

/**
 * 巡检设备维护dao层
 * 
 * @author lbhu
 * @date 2014-9-2
 *
 */
@SuppressWarnings("all")
@Repository
public interface EquipDao {
	
	/**
	 * 查询巡检设备列表
	 * 
	 * @param query
	 * @return
	 */
	public List<Map> getEquipList(Query query);
	
	/**
	 * 查询设备巡检记录列表
	 * 
	 * @param query
	 * @return
	 */
	public List<Map> getResInsHistory(Query query);
	
	/**
	 * 修改设备信息
	 * 
	 * @param map
	 */
	public void modifyEquip(Map map);
	
	/**
	 * 根据ID获取设备信息
	 * 
	 * @param equipmentId 设备ID
	 * @return
	 */
	public Map getEquip(String equipmentId);
	
	/**
	 * 分配设备检查人员
	 * 
	 * @param map
	 * @return
	 */
	public void allotEquip(Map map);
	
	/**
	 * 分配设备检查人员
	 * 
	 * @param map
	 * @return
	 */
	public void btnclearallot(Map map);
	
	/**
	 * 获取所有部门
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> getCompanyList(Map map);
	
	/**
	 * 根据资源id获取所有巡检人员
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> getDisStaffList(Map map);
	
	/**
	 * 根据资源id获取所有管控人员 
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> getControlStaffList(Map map);
	
	/**
	 * 根据巡检人员id获取所有资源ID
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> getAllResList(Map map);
	
	/**
	 * 得到ftth端口
	 * 
	 */
	public List<Map> getFtthCount(Map map);
	
}
