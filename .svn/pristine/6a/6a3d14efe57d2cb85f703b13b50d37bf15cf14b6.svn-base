package com.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface GeneralDao {

    public List<Map<String, String>> getAreaList();

    public List<Map> getSoftwarePageList(Query query);

    public List<Map<String, String>> getSoftwareList();

    /**
     * @Title: getSonAreaList
     * @Description:
     * @param: @return
     * @return: List<Map<String,String>>
     * @throws
     */
    public List<Map<String, String>> getSonAreaList(String area_id);
    /**
     * 获取综合化维护网格
     * @param area_id
     * @return
     */
    public List<Map<String, String>> getGridList(Map map);

    /**
     * @Title: getSonAreaListByStaffId
     * @Description:
     * @param: @param valueOf
     * @param: @return
     * @return: List<Map<String,String>>
     * @throws
     */
    public List<Map<String, String>> getSonAreaListByStaffId(String staff_id);

    /**
     * @Title: save
     * @Description:
     * @param: @param map
     * @return: void
     * @throws
     */
    public void insert(Map map);

    public String getEqpNo();

    /**
     * 获取所有部门
     * 
     * @param map
     * @return
     */
    public List<Map> getCompanyList(Map map);

    public int getSeq(Map map);

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
    
    public List<String> getDeptListByStaff(String s);
    
    public List<Map<String, String>> getDeptList(String s);
    
    public List<Map<String, String>> getMainTainCompany();
    
    public List<Map<String, String>> getBanzuByCompanyId(String companyId);
    public List<Map<String, String>> getBanzuByAreaId(String areaId);
    
    

}
