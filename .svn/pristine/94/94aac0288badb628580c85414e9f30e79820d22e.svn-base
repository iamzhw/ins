/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.outsite.service;

import java.util.List;
import java.util.Map;

import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface PartTimeModelService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void partTimeModelSave(Map<String, Object> para);

    /**
     * @param para
     */
    void partTimeModelUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void partTimeModelDelete(String ids);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);

    List<Map<String, Object>> findAll();

    /**
     * @return
     */
    List<Map<String, Object>> getCity();

}
