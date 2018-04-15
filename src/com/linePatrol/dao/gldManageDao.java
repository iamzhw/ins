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
public interface gldManageDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void save(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String cable_id);

    /**
     * @param para
     */
    void update(Map<String, Object> para);

    /**
     * @param id
     */
    void delete(String cable_id);

    /**
     * @param map
     * @return
     */
    int validateCblColor(Map<String, Object> map);

    /**
     * @return
     */
    List<Map<String, Object>> findAll();

    /**
     * @param staffAreaId
     * @return
     */
    List<Map<String, Object>> getGldByAreaId(String area_id);
    
    /**
     * @param staffAreaId
     * @return
     */
    List<Map<String, Object>> getRelayByCableId(String cable_id);
    
    /**
     * @param affected_fiber_ids
     * @return
     */
    List<Map<String, Object>> getCableByids(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param areaId
     * @return
     */
    List<Map<String, Object>> getLocalCableList(String areaId);

    /**
     * 作用： 　　*作者：
     * 
     * @return
     */
    String getCableId();

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     */
    void insertCable2Area(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param id
     * @return
     */
    String getOwnAreaList(String id);

    /**
     * 作用： 　　*作者：
     * 
     * @param cable_id
     */
    void deleteOldCable2Area(String cable_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param cable_id
     * @return
     */
    List<Map<String, Object>> getCableAreaList(String cable_id);

}
