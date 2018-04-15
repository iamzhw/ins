/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface UserSignService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void userSignSave(Map<String, Object> para);

    /**
     * @param para
     */
    void userSignUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void userSignDelete(String ids);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);

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
    Map getTheLineInfoForSign(Map<String, Object> para);

}
