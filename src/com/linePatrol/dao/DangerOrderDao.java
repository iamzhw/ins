/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@Repository
public interface DangerOrderDao {

	/**
	 * @param query
	 * @return
	 */
	// List<Map> query(Query query);

	/**
	 * @param para
	 */
	void dangerOrderSave(Map<String, Object> para);

	/**
	 * @param id
	 * @return
	 */
	Map<String, Object> findById(String order_id);

	/**
	 * @param para
	 */
	void dangerOrderUpdate(Map<String, Object> para);

	/**
	 * @param id
	 */
	void dangerOrderDelete(String order_id);

	List<Map<String, Object>> findAll();

	/**
	 * @return
	 */
	List<Map<String, Object>> getAllDangerOrderState();

	/**
	 * @return
	 */
	String getDangerId();

	/**
	 * @param para
	 */
	void saveDanger(Map<String, Object> para);

	/**
	 * @return
	 */
	List<Map> findHandlePerson();

	/**
	 * @param order_id
	 * @return
	 */
	Map<String, Object> findDetail(String order_id);

	/**
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> getDangerOrderToBeDeal(String userId);

	/**
	 * @param map
	 * @return
	 */
	List<Map> doStatistic(Map<String, Object> map);

	/**
	 * 作用： 　　*作者：
	 * 
	 * @param order_id
	 * @return
	 */
	String getDangerIdByOrder(String order_id);

	/**
	 * 作用： 　　*作者：
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> doStatistic4(Map<String, Object> map);

	/**
	 * 作用： 　　*作者：
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> doStatistic5(Map<String, Object> map);

	/**
	 * 作用： 　　*作者：
	 * 
	 * @param para
	 * @return
	 */
	List<Map<String, Object>> searchByAdmin4(Map<String, Object> para);

	/**
	 * 作用： 　　*作者：
	 * 
	 * @param para
	 * @return
	 */
	List<Map<String, Object>> searchByAdmin5(Map<String, Object> para);

	List<Map<String, Object>> query1(Map<String, Object> para);

	void deleteDanger(int ids);

	List<Map<String, Object>> getScopeList(Map<String, Object> para);

	public List<Map<String, Object>> query(Query query);

	/**
	 * 隐患单管理页面导出
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> dataDownload(Map<String, Object> map);

}
