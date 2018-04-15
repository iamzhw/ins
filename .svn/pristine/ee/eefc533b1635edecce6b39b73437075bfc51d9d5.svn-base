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
public interface GaotieAutoTrackDao {

	/**
     * 展示轨迹
     * @param para
     * @return
     */
    List<Map<String, Object>> getTheTrack(Map<String, Object> para);

    /**
     * 查看轨迹列表
     * @param map
     * @return
     */
	List<Map<String, Object>> selTrackForDG(Map<String, Object> map);
	
	/**
	 * 查看轨迹图片
	 * @param request
	 * @return
	 */
	List<Map<String, String>> getPhotoList(String trackId);

}
