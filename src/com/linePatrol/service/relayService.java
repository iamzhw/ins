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
public interface relayService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void relaySave(Map<String, Object> para);

    /**
     * @param para
     */
    void relayUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void relayDelete(String ids);

    /**
     * @param id
     * @return
     */
    Map<String, Object> relayEditUI(String id);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);

    /**
     * 作用： 　　*作者：
     * 
     * @param staffAreaId
     * @return
     */
    List<Map<String, Object>> findRelayByAreaId(String area_id);

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

}
