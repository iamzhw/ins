/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.Query;
import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface DangerOrderService {

    /**
     * @param para
     * @return
     */
   // Map<String, Object> query(Map<String, Object> para);

    /**
     * @param para
     */
    void dangerOrderSave(Map<String, Object> para);

    /**
     * @param para
     */
    void dangerOrderUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void dangerOrderDelete(String ids);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);

    List<Map<String, Object>> findAll();

    /**
     * @return
     */
    List<Map<String, Object>> getAllDangerOrderState();

    /**
     * @return
     */

    /**
     * @return
     */
    String getDangerId();

    /**
     * @return
     */
    Map<String, Object> findHandlePerson();

    /**
     * @param para
     */
    void dangerOrderDistribute(Map<String, Object> para);

    /**
     * @param ids
     */
    void receipt(String ids);

    /**
     * @param para
     */
    void audit(Map<String, Object> para);

    /**
     * @param order_id
     * @return
     */
    Map<String, Object> findDetail(String order_id);

    /**
     * @param map
     * @param uiPage
     * @return
     */
    Map<String, Object> doStatistic(Map<String, Object> map, UIPage uiPage);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     */
    void paidan(Map<String, Object> para);

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
     * @param para
     * @param request
     * @param response
     */
    void doDownload(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    Map<String, Object> searchByAdmin(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param request
     * @param response
     */
    void downloadByAdmin(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response);

	void dataDownload(Map<String, Object> para2, HttpServletRequest request,
			HttpServletResponse response);

	void deleteDanger(int ids);

	List<Map<String, Object>> getScopeList(Map<String, Object> para);

	public List<Map<String, Object>> query(Query query);

}
