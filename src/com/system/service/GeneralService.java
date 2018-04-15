/**
 * FileName: GeneralService.java
 * @Description: TODO(用一句话描述该文件做什么) 
 * All rights Reserved, Designed By ZBITI 
 * Copyright: Copyright(C) 2014-2015
 * Company ZBITI LTD.

 * @author: SongYuanchen
 * @version: V1.0  
 * Createdate: 2014-1-17
 *

 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-1-17      wu.zh         1.0            1.0
 * Why & What is modified: <修改原因描述>
 */
package com.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

/**
 * @ClassName: GeneralService
 * @Description: 通用的方法
 * @author: SongYuanchen
 * @date: 2014-1-17
 * 
 */
public interface GeneralService {
    public List<Map<String, String>> getAreaList();

    /**
     * @Title: getSonAreaList
     * @Description:
     * @param: @param parameter
     * @param: @return
     * @return: List<Map<String,String>>
     * @throws
     */
    public List<Map<String, String>> getSonAreaList(String parameter);
    /**
     * 获取综合化维护网格
     * @param parameter
     * @return
     */
    public List<Map<String, String>> getGridList(String areaId, String sonAreaId);

    public List<Map<String, String>> getSoftwareList();

    public Map<String, Object> getSoftwarePageList(HttpServletRequest request, UIPage pager);

    public Map<String, Object> testDB();

    /**
     * 获取所有公司
     * 
     * @param map
     * @return
     */
    public List<Map> getCompanyList(Map map);

    /**
     * 获取序列
     */
    public String getNextSeqVal(String seq);

    public List<Map<String, Object>> getAreaById(String areaId);

    /**
     * 作用： 　　*作者：
     * 
     * @param parameter
     * @return
     */
    public List<Map<String, String>> getOwnCompany(String parameter);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    public List<Map<String, Object>> getPhoto(Map<String, Object> para);
    public List<Map<String, Object>> getPhotoByType(Map<String, Object> para);
    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    public List<Map<String, Object>> getStaffByRole(Map<String, Object> map);

	public List<Map<String, String>> getDeptList(String parameter);

	public List<Map<String, String>> getMainTainCompany();

	public List<Map<String, String>> getBanzuByCompanyId(String parameter);
	public List<Map<String, String>> getBanzuByAreaId(String parameter);
	
}
