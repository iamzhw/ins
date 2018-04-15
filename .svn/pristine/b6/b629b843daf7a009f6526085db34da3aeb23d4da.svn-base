/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.outsite.dao;

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
public interface MainOutSiteDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);
    List<Map> query_recordindex(Query query);
    
    /**
     * @param para
     */
    void mainOutSiteSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String out_site_id);

    /**
     * @param para
     */
    void mainOutSiteUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void mainOutSiteDelete(String out_site_id);

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
    List<Map<String, Object>> getOuteSiteLocation(Map<String, Object> para);
    
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
     * @return
     */
    String getId();

    /**
     * @param map
     */
    void saveJxs(Map<String, Object> map);

    /**
     * @param map
     */
    void saveOsMaintainScheme(Map<String, Object> map);

    /**
     * @param map
     */
    void makesureScheme(Map<String, Object> map);

    /**
     * @param map
     */
    void saveOsInputPoints(Map<String, Object> map);

    /**
     * @param map
     */
    void saveDepthProbe(Map<String, Object> map);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getDepthProbe(Map<String, Object> para);

    /**
     * @param map
     */
    void saveOsCheckRecord(Map<String, Object> map);

    /**
     * @return
     */
    String getOscheckRecordId();

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getOsProtectTaskList(Map<String, Object> para);

    /**
     * @param map
     */
    void saveOsProtectCoordinate(Map<String, Object> map);

    /**
     * @param plan_id
     * @return
     */
    int getSequence(String plan_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param areaId
     * @return
     */
    List<Map<String, Object>> getOutSitesByAreaId(String areaId);

    /**
     * 外力点迁移
     * 
     * @param map
     */
    void saveOutSiteMoveInfo(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param userId
     * @return
     */
    List<Map<String, Object>> getOutSchemes(String userId);

    /**
     * 作用： 　　*作者：
     * 
     * @param userId
     * @return
     */
    Map<String, Object> getOutSiteByQrryid(String userId);

    /**
     * 作用： 　　*作者：
     * 
     * @param userId
     * @return
     */
    List<Map<String, Object>> getOutSchemesByJsyId(String userId);

    /**
     * 作用： 　　*作者：
     * 
     * @param out_site_id
     * @return
     */
    List<Map<String, Object>> getElebar(String plan_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param out_site_id
     */
    int delOldElebar(String out_site_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param areaId
     * @return
     */
    List<Map<String, Object>> getOutSitesNotConfirmed(String areaId);
    
    
    /**
     * 获取外力点流程
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getFlowDetail(Map<String, Object> map);

	List<Map<String, Object>> findConfirmTbl(String id);

	String selOrgName(Object fiber_eponsible_by);
	
	public List<Map<String, Object>> queryOutSiteRate(Map<String, Object> map);
	
	public List<Map<String, Object>> getAllGroup(String area_id);

	public List<Map<String, Object>> depthProbeQuery(Map<String, Object> para);
	
	List<Map<String, Object>> queryDown(Map<String, Object> para);
	
	List<Map<String, Object>> query_recordindexDown(Map<String, Object> para);
	
	int intoRecordTable(String records);
	
}

