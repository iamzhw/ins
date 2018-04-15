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
public interface OsMaintainSchemeService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void osMaintainSchemeSave(Map<String, Object> para);

    /**
     * @param para
     */
    void osMaintainSchemeUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void osMaintainSchemeDelete(String ids);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);

    List<Map<String, Object>> findAll();

    /**
     * @param scheme_id
     * @return
     */
    Map<String, Object> findDetail(String scheme_id);

}
