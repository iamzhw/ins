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
public interface MobileInfoDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void mobileInfoSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String mobile_id);

    /**
     * @param para
     */
    void mobileInfoUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void mobileInfoDelete(String mobile_id);
    
    List<Map<String, Object>> findAll();

}
