/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface LineInfoService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);
    /**
     * 地图展示
     * @param map
     * @return
     */
    Map<String, Object> queryForMap(Map map);

    /**
     * @param request
     */
    void lineInfoSave(HttpServletRequest request);

    /**
     * @param request
     */
    void lineInfoUpdate(HttpServletRequest request);

    /**
     * @param selected
     */
    void lineInfoDelete(String ids);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);

    List<Map<String, Object>> findAll();

    /**
     * @param staffAreaId
     * @return
     */
    List<Map<String, Object>> findLineInfoByAreaId(String staffAreaId);

    /**
     * @param gldId
     * @param localId
     * @return
     */
    List<Map<String, Object>> getRelayByGl(String gldId, String localId);

    /**
     * @param localId
     * @return
     */
    List<Map<String, Object>> getLocalInspactPerson(String localId);
    
    List<Map<String, Object>> getLocalPerson(String localId,String orgId);

    /**
     * @param para
     * @return
     */
    Map<String, Object> findLineInfoById(Map<String, Object> para);
    /**
     * 
     * @Title: findLineInfoByIds
     * @Description:根据巡线段IDs查询巡线点信息
     * @author wangxiangyu
     */
    Map<String, Object> findLineInfoByIds(Map<String, Object> para);

    /**
     * @param line_id
     * @return
     */
    Map<String, Object> showTheLineOnMap(String line_id);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getLocalInspactPerson(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    List<Map<String, Object>> getLineInfoByRelay(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param markersIds
     * @param lat
     * @param lng
     * @return
     */
    List<Map<String, Object>> getSitesByIds(String markersIds, String lng, String lat);
    
    String getOrgByRole(String staffId);

	List<Map<String, Object>> getRelayById(String cableId, String areaId);

	List<Map<String, Object>> getTimeList();

	List<Map<String, Object>> gaotiegetLocalInspactPerson(
			Map<String, Object> para);
}
