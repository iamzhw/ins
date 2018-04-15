/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.outsite.dao;

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
public interface PartTimeModelDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void partTimeModelSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String part_time_id);

    /**
     * @param para
     */
    void partTimeModelUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void partTimeModelDelete(String part_time_id);

    List<Map<String, Object>> findAll();

    /**
     * @return
     */
    List<Map<String, Object>> getCity();

}
