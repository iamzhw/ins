/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@Repository
public interface ReplaceHolidayDao {

    /**
     * @param query
     * @return
     */
    List<Map<String, Object>> query(Map<String, Object> para);

    /**
     * @param para
     */
    void replaceHolidaySave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String holiday_id);

    /**
     * @param para
     */
    void replaceHolidayUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void replaceHolidayDelete(String holiday_id);

    List<Map<String, Object>> findAll();

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    List<Map<String, Object>> getAllHoliday(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param holiday_id
     * @return
     */
    List<Map<String, Object>> getAllHolidayByHid(String holiday_id);
    
    
    List<Map<String, Object>> showDetailInfo(Map<String, Object> para);

}
