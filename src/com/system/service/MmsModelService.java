/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.system.service;

import java.util.List;
import java.util.Map;

import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface MmsModelService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void mmsModelSave(Map<String, Object> para);

    /**
     * @param para
     */
    void mmsModelUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void mmsModelDelete(String ids);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);

    List<Map<String, Object>> findAll();

    /**
     * 作用： 　　*作者：
     * 
     * @return
     */
    List<Map<String, Object>> getModalTypeList();

    /**
     * 作用： 　　*作者：
     * 
     * @param areaId
     * @return
     */
    List<Map<String, Object>> getLocalKhy(String areaId);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     */
    void saveShortMessageAlarm(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param staffId
     * @return
     */
    List<Map<String, Object>> getSettings(String staffId);

}
