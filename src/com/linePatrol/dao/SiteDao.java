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
public interface SiteDao {

    /**
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * @param para
     */
    void siteSave(Map<String, Object> para);

    /**
     * @param id
     * @return
     */
    Map<String, Object> findById(String site_id);

    /**
     * @param para
     */
    void siteUpdate(Map<String, Object> para);

    /**
     * @param id
     */
    void siteDelete(String site_id);

    List<Map<String, Object>> findAll();

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> findSiteByLine(Map<String, Object> para);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getSiteByRelayId(Map<String, Object> para);

    /**
     * @param para
     */
    void update(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> findSiteByLine2(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getLocalSitesByRelayId(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getSitesByIds(Map<String, Object> map);
    
    /**
     * 根据id查找巡线点照片
     * @param id
     * @return
     */
    List<Map<String, Object>> getSitePhotoList(String id);

    /**
     * 判断巡线点是否在巡线段中
     * @param ids
     * @return
     */
    List<Map<String, Object>> ifSiteInPart(Map map);
    /**
     * 删除巡线点
     * @param ids
     */
    public void deleteSiteById(Map map);
    
    /**
     * 根据中继段id查找巡线点
     * @param relayId
     * @return
     */
    List<Map<String, Object>> getSitesByRelayId(String relayId);
    
    /**
     * 查询巡线点列表
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> querySiteList(Map<String, Object> map);
    
    /**更新巡线点顺序
     * @param para
     */
    void updateSiteOrder(Map<String, Object> para);
    
    /**新增巡线点
     * @param para
     */
    void addSite(Map<String, Object> para);
    
    /**
     *查询巡线点序列 
     */
    int getSiteSeq();

}
