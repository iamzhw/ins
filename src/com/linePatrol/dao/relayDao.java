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
public interface relayDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void relaySave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String relay_id);

    /**
     * @param para
     */
    void relayUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void relayDelete(String relay_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param area_id
     * @return
     */
    List<Map<String, Object>> findRelayByAreaId(String area_id);

    /**
     * 作用： 　　*作者：
     * 
     * @return
     */
    String getRelayId();

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     */
    void insertRelay2Area(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param relayId
     */
    void deleteOldRelay2Area(String relayId);

    /**
     * 作用： 　　*作者：
     * 
     * @param cable_id
     * @return
     */
    List<Map<String, Object>> getOwnArea(String cable_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param id
     * @return
     */
    String getRelayArea(String id);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getRelaysByCableId(Map<String, Object> map);

}
