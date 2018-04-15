/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service;

import java.util.Map;
import util.page.UIPage;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public interface MobileInfoService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void mobileInfoSave(Map<String, Object> para);

    /**
     * @param para
     */
    void mobileInfoUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void mobileInfoDelete(String ids);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);
    
    List<Map<String, Object>> findAll();

}
