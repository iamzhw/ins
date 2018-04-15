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
public interface LineInfoDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);
    
    /**
     * TODO 地图展示巡线点数据
     * @param map
     * @return
     */
    List<Map<String, Object>> queryForMap(Map map);
    /**
     * TODO 地图展示巡线段数据
     * @param map
     * @return
     */
    List<Map<String, Object>> querySitePartForMap(Map map);

    /**
     * @param para
     */
    void lineInfoSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String line_id);

    /**
     * @param para
     */
    void lineInfoUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void lineInfoDelete(String line_id);

    List<Map<String, Object>> findAll();

    /**
     * @param staffAreaId
     * @return
     */
    List<Map<String, Object>> findLineInfoByAreaId(String area_id);

    /**
     * @param map
     * @return
     */
    List<Map<String, Object>> getRelayByGl(Map<String, Object> map);

    /**
     * @param localId
     * @return
     */
    List<Map<String, Object>> getLocalInspactPerson(String localId);

    /**
     * @return
     */
    String getSiteInfoNextVal();

    /**
     * @param site2line
     */
    void insertSite2LineInfo(Map site2line);

    /**
     * @param line_id
     * @return
     */
    Map<String, Object> getGldAndRelay(String line_id);

    /**
     * @param line_id
     * @return
     */
    List<Map<String, Object>> getSelectedSiteList(String line_id);

    /**
     * @param line_id
     */
    void deleteLine2Site(String line_id);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getLocalInspactPersonWithCondition(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param map2
     * @return
     */
    List<Map<String, Object>> getLineByRelayId(Map<String, Object> map2);

    /**
     * 作用： 　　*作者：
     * 
     * @param relay_id
     * @return
     */
    List<Map<String, Object>> getLineInfoByRelay(String relay_id);
    
    String getOrgByRole(String staffId);
    
    List<Map<String, Object>> getLocalPerson(Map<String, Object> map);

	List<Map<String, Object>> getTimeList();
	List<Map<String, Object>> gaotiegetLocalInspactPersonWithCondition(Map<String, Object> para);


}
