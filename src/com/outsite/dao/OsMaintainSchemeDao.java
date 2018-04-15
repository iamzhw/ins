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
public interface OsMaintainSchemeDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void osMaintainSchemeSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String scheme_id);

    /**
     * @param para
     */
    void osMaintainSchemeUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void osMaintainSchemeDelete(String scheme_id);

    List<Map<String, Object>> findAll();

    /**
     * 作用： 　　*作者：
     * 
     * @return
     */
    String getNextId();

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     */
    void saveOsMaintainScheme(Map<String, Object> map);

}
