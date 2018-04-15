/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.outsite.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface MainOutSiteService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);
    Map<String, Object> query_recordindex(Map<String, Object> para, UIPage pager);
    
    /**
     * @param para
     */
    void mainOutSiteSave(Map<String, Object> para);

    /**
     * @param para
     */
    void mainOutSiteUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void mainOutSiteDelete(String ids);

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
    List<Map<String, Object>> getCzs(Map<String, Object> para);

    /**
     * @param map
     * @return
     */
    Map<String, Object> findJxsByid(Map<String, Object> map);

    /**
     * @param para
     */
    void jxsUpdate(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getCheckRecord(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getSignInfo(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getStayInfo(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getMstwInfo(Map<String, Object> para);

    /**
     * @param para
     */
    void mstwUpdate(Map<String, Object> para);

    /**
     * @param map
     * @return
     */
    Map<String, Object> findMstwByid(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param pointsJsons
     * @param out_site_id
     * @param staffId
     * @param ebLength
     * @param ebWidth
     * @param ebLength2
     */
    void saveElebar(String pointsJsons, String plan_id, String out_site_id, String staffId,
	    String ebWidth, String ebLength);

    /**
     * 作用： 　　*作者：
     * 
     * @param out_site_id
     * @return
     */
    List<Map<String, Object>> getElebar(String out_site_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param pointsJsons
     * @param out_site_id
     * @param staffId
     * @param ebWidth
     * @param ebLength
     * @param ebLength2
     */
    void updateElebar(String pointsJsons, String plan_id, String out_site_id, String staffId,
	    String ebWidth, String ebLength);
    
    
    /**
     * 获取外力点流程
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getFlowDetail(Map<String, Object> map);

    /**
     * 获取外力点在地图的位置
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getOuteSiteLocation(Map<String, Object> map);
    
    public List<Map<String, Object>> findConfirmTbl(String id);

	String selOrgName(Object fiber_eponsible_by);

	public List<Map<String, Object>> queryOutSiteRate(Map<String, Object> map);

	void outSiteRateDownLoad(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	
	public List<Map<String, Object>> getAllGroup(String area_id);

	public List<Map<String, Object>> depthProbeQuery(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response);
			
	void queryByDown(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response);
	
	void recordIndexDown(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response);
}
