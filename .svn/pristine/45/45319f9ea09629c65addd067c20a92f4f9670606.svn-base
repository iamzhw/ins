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
public interface UserSignDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void userSignSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String sign_id);

    /**
     * @param para
     */
    void userSignUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void userSignDelete(String sign_id);

    List<Map<String, Object>> findAll();

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getUserSignByUserid(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<String> getTheLineInfoForSignIds(Map<String, Object> para);

}
