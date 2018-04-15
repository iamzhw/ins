package com.webservice.unifiedno.dao; 

import java.util.Map;

import org.springframework.stereotype.Repository;

/** 
 * @author wangxiangyu
 * @date：2017年9月27日 下午4:13:01 
 * 类说明：
 */
@SuppressWarnings("all")
@Repository
public interface UnifiedNoDao {
	/**
	 * 新增
	 * @param map
	 */
	public void insertAccount(Map map);
	/**
	 * 更新
	 * @param map
	 */
	public void updateAccount(Map map);
	/**
	 * 注销
	 * @param map
	 */
	public void deleteAccount(Map map);
	/**
	 * 冻结
	 * @param map
	 */
	public void suspendAccount(Map map);
	/**
	 * 解冻
	 * @param map
	 */
	public void thawAccount(Map map);
	/**
	 * 查询
	 * @param staffId
	 * @return
	 */
	public Map findAccount(String staffId);
	/**
	 * 保存操作流水号、操作类型等
	 * @param map
	 */
	public void saveRecord(Map map);
	
}
