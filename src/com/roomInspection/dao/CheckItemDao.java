package com.roomInspection.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface CheckItemDao {
	
	/**
	 * 查询检查项列表
	 * 
	 * @param map 参数
	 * @param pager 分页信息
	 * @return
	 */
	List<Map<String,Object>> queryCheckItem(Query query);
	
	/**
	 * 增加检查项
	 * 
	 * @param map 参数
	 */
	void insertCheckItem(Map<String,Object> map);
	
	/**
	 * 修改检查项
	 * 
	 * @param map 参数
	 */
	void updateCheckItem(Map<String,Object> map);
	
	/**
	 * 删除检查项
	 * 
	 * @param map 参数
	 */
	void deleteCheckItem(Map<String,Object> map);
	
	/**
	 * 根据检查项ID查询检查项详情
	 * 
	 * @param map 参数
	 * @return
	 */
	Map<String,Object> queryCheckItemById(Map<String,Object> map);
	
	/**
	 * 获取检查项ID
	 * @return
	 */
	long getCheckItemId();
	
	/**
	 * 查询隐患类型列表
	 * 
	 * @param map 参数
	 * @return
	 */
	List<Map<String,Object>> queryTroubleTypeList(Map<String,Object> map);
	
	/**
	 * 根据隐患类型ID查询隐患类型
	 * 
	 * @param map 参数
	 * @return
	 */
	Map<String,Object> queryTroubleTypeById(Map<String,Object> map);
	
	/**
	 * 新增隐患类型，隐患类型关联检查项ID
	 * 
	 * @param map 参数
	 */
	void insertTroubleType(Map<String,Object> map);
	
	/**
	 * 修改隐患类型
	 * 
	 * @param map 参数
	 */
	void updateTroubleType(Map<String,Object> map);
	
	/**
	 * 删除隐患类型
	 * 
	 * @param map 参数
	 */
	void deleteTroubleType(Map<String,Object> map);

}
