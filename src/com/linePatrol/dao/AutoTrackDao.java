/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.dao;

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
public interface AutoTrackDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void autoTrackSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String id);

    /**
     * @param para
     */
    void autoTrackUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void autoTrackDelete(String id);

    List<Map<String, Object>> findAll();

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getTheTrack(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<String> getTheveryDayLineInfoIds(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getTheRealTimeTrack(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<String> getPersonList(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param inspact_id
     * @return
     */
    List<Map<String, Object>> getOutsiteByInspector(String inspact_id);

	List<Map<String, Object>> getAlarmPoints(Map<String, Object> map);
	
	List<Map<String, Object>> selTrackForDG(Map<String, Object> map);
	
	List<Map<String, Object>> getPositionOfTeam(Map<String, Object> map);
	
	List<Map<String, Object>> selPersonOfTeam(Map<String, Object> map);

	List<Map<String, Object>> getLineSiteByRealTime(Map<String, Object> para);
	
	List<Map<String, Object>> getLeastFive(Map<String, Object> para);
	
	List<Map<String, Object>> getElsePoints(Map<String, Object> para);
	

}
