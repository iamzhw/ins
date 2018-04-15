/**
 * @Description: TODO
 * @date 2015-3-24
 * @param
 */
package icom.axx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * 
 */
@Repository
public interface OutSiteInterfaceDao {

    /**
     * @param plan_id
     * @return
     */
    Map<String, Object> getOsPlanCenter(String plan_id);

    /**
     * @param para
     * @return
     */
    List<Map<String, Object>> getDepthProbe(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param user_id
     * @return
     */
    List<Map<String, Object>> getLocalQrry(String user_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param plan_id
     * @return
     */
    Map<String, Object> getOutSiteByPlanId(String plan_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param line_part
     * @return
     */
    String getlineInspactSTaff(String line_part);

    /**
     * 作用： 　　*作者：
     * 
     * @param userId
     * @return
     */
    Map<String, Object> getOutCheckArrivalRate(String userId);

    /**
     * 作用： 　　*作者：
     * 
     * @param p
     * @return
     */
    List<Map<String, Object>> getTodayTask(Map<String, Object> p);

    /**
     * 作用： 　　*作者：
     * 
     * @param p
     * @return
     */
    String getActualCount(Map<String, Object> p);

    /**
     * 作用： 　　*作者：
     * 
     * @param p
     * @return
     */
    List<Map<String, Object>> getTask2Outsite(Map<String, Object> p);

    /**
     * 作用： 　　*作者：
     * 
     * @param task_id
     * @return
     */
    Map<String, Object> getTaskInfo(String task_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param user_id
     * @return
     */
    List<Map<String, Object>> getLocalSchemeMakers(String user_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param p
     */
    void insertMainOutsiteService(Map<String, Object> p);

    /**
     * 作用： 　　*作者：
     * 
     * @param p
     * @return
     */
    String getTodayOustSite(Map<String, Object> p);

    /**
     * 作用： 　　*作者：
     * 
     * @param outSiteId
     * @return
     */
    List<Map<String, Object>> getElebar(String plan_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param p
     * @return
     */
    List<Map<String, Object>> getNurseTasks(Map<String, Object> p);

    /**
     * 作用： 　　*作者：
     * 
     * @return
     */
    String getGuardInfoId();

    /**
     * 作用： 　　*作者：
     * 
     * @param p
     */
    void insertGuardInfo(Map<String, Object> p);

    /**
     * 作用： 　　*作者：
     * 
     * @param plan_id
     * @return
     */
    String getosIdByPlanid(String plan_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    Map<String, Object> isInGuardLast(Map<String, Object> map);
    
    
    /**
     * 根据人员ID查询人员
     * @param param
     * @return
     */
    Map<String,Object> getUserInfo(Map<String,Object> param);
    
    
    /**
     * 获取外力点最新流程
     * @param param
     * @return
     */
    Map<String,Object>getMaxOutSiteFlow(Map<String,Object> param);
    
    /**
     * 根据外力点查询维护方案
     * @param param
     * @return
     */
    List<Map<String,Object>> getOutSchemeBySiteId(Map<String,Object> param);
    
    /**
     * 根据流程和区域ID获取外力点
     * @param param
     * @return
     */
    List<Map<String,Object>> getOutSitesByFlow(Map<String,Object> param);
    
    /**
     * 根据用户级别和区域获取用户信息
     * @param param
     * @return
     */
    List<Map<String,Object>> getUsersByLevel(Map<String,Object> param);
    
    /**
     * 外力点流程管理
     * @param param
     */
    void addOutsiteFlow(Map<String,Object> param);
    
    void jxsUpdate(Map<String,Object> param);
    
    /**
     * 作用： 　　*作者：
     * 根据中继段id和区域获取外力点
     * @param areaId
     * @return
     */
    List<Map<String, Object>> getOutSitesByMap(Map<String,Object> map);
    
    /**
     *获取区域下外力点以及该外力点的第一张照片
     * @param map
     * @return
     */
    List<Map<String,Object>> getOutSitesAndPhoto(Map<String,Object> map);
    
    /**
	 * 根据外力点获取该外力点下所有照片
	 * @param map
	 * @return
	 */
    List<Map<String,Object>> getOutSitePhotos(Map<String,Object> map);
    
    /**
     * 根据区域获取外力点总数
     * @param map
     * @return
     */
    int selOutSiteCount(Map<String, Object> map);
    
    /**
     * 插入照片点评记录
     * @param map
     */
    void intoPhotoComment(Map<String, Object> map);
    
    /**
     * 历史照片点评查看
     * @param map
     * @return
     */
    List<Map<String,Object>> getHistoryPhotoComments(Map<String,Object> map);
    
    /**
     * 查询当前照片的详细信息
     * @param photo_id
     * @return
     */
    Map<String,Object> selPhotolByID(String photo_id);
    
    /**
     * 查询改区域的组织关系
     * @param area_id
     * @return
     */
    List<Map<String,Object>> selOrgByAreaIDs(String area_id);
    
    /**
     * 查看该外力点是否有埋深探位属性
     * @param out_site_id
     * @return
     */
    int isDepthProbe(String out_site_id);
    
}
