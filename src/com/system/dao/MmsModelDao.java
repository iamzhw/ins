/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.system.dao;

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
public interface MmsModelDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void mmsModelSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String mms_id);

    /**
     * @param para
     */
    void mmsModelUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void mmsModelDelete(String mms_id);

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
     * @param map
     */
    void deleteOldMessageAlarm(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     */
    void saveShortMessageAlarm(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param staffId
     * @return
     */
    List<Map<String, Object>> getSettings(String staffId);

    /**
     * 作用： 　　*作者：
     * 
     * @param created_by
     * @return
     */
    List<Map<String, Object>> getTargetModelList(String khyId);

}
