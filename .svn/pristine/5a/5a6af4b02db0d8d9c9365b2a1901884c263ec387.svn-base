/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import util.page.Query;
import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface AutoTrackService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void autoTrackSave(Map<String, Object> para);

    /**
     * @param para
     */
    void autoTrackUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void autoTrackDelete(String ids);

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
    List<Map<String, Object>> getTheTrack(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    Map<String, Object> getTheveryDayLineInfo(Map<String, Object> para);
    List<Map<String, Object>> getElsePoints(Map<String, Object> para);
    List<Map<String, Object>> getLeastFive(Map<String, Object> para);
    
    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getTheRealTimeTrack(Map<String, Object> para);

	List<Map<String, Object>> selTrackForDG(Map<String, Object> map);

	List<Map<String, Object>> getAlarmPoints(Map<String, Object> map);
	
	String getPositionOfTeam(String para);

	String selPersonOfTeam(String para);

	List<Map<String, Object>> getLineSiteByRealTime(Map<String, Object> para);

}
