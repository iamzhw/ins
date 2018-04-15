/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface SiteService {

    /**
     * @param para
     * @param pager
     * @return
     */
    Map<String, Object> query(Map<String, Object> para, UIPage pager);

    /**
     * @param para
     */
    void siteSave(Map<String, Object> para);

    /**
     * @param para
     */
    void siteUpdate(Map<String, Object> para);

    /**
     * @param selected
     */
    void siteDelete(String ids);

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
     * @param markersIds
     * @return
     */
    List<Map<String, Object>> getSitesByIds(String markersIds);
    
    /**
     * 根据id查找巡线点照片
     * @param id
     * @return
     */
    List<Map<String, Object>> getSitePhotoList(String id);
    
    /**
     * 根据中继段查找巡线点
     * @param id
     * @return
     */
    List<Map<String, Object>> getSitesByRelayId(String relayId);
    /**
     * 删除巡线点
     * @param ids
     * @return
     */
    String deleteSite(String ids);
    
    /**根据巡线段查询巡线点
     * @param para:SITE_TYPE,LINE_ID
     * @return
     * @
     */
    Map<String, Object> querySiteList(Map<String, Object> para, UIPage pager);
    
    /**导入巡线点from巡线段管理
     * @param request,file
     * @return
     * @
     */
    public Object importDo(HttpServletRequest request, MultipartFile file);
}
