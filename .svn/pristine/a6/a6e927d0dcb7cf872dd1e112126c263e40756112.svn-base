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
public interface gldManageService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void save(Map<String, Object> para);

    /**
     * @param para
     */
    void update(Map<String, Object> para);

    /**
     * @param selected
     */
    void delete(String ids);

    /**
     * @param cable_color
     * @return
     */
    int validateCblColor(String cable_color);

    /**
     * @param id
     * @return
     */
    Map<String, Object> editUI(String id);

    /**
     * @return
     */
    List<Map<String, Object>> findAll();

    /**
     * @param staffAreaId
     * @return
     */
    List<Map<String, Object>> getGldByAreaId(String staffAreaId);
    
    /***
     * 孙敏
     * 2016/3/15
     * 根据光缆id返回中继段
     * @param cable_id
     * @return
     */
    List<Map<String, Object>> getRelayByCableId(String cable_id);
    
    

    /**
     * 作用： 　　*作者：
     * 
     * @param cable_id
     * @return
     */
    List<Map<String, Object>> getCableAreaList(String cable_id);

}
