package icom.axx.service;

import java.util.List;
import java.util.Map;

public interface LineSiteInterfaceService {

    /**
     * 保存自动上传轨迹
     * 
     * @param jsonStr
     */
    public String saveAutoTrack(String jsonStr);
    /**
     * 保存自动上传轨迹，不限制重复上传
     * @param jsonStr
     * @return
     */
    public String saveAutoTrack2(String jsonStr);
    
    /**
     * 保存自动上传高铁轨迹
     * 
     * @param jsonStr
     */
    public String saveAutoGtTrack(String jsonStr);

    /**
     * 保存签到点信息
     * 
     * @param jsonStr
     */
    public String saveRegisterSite(String jsonStr);

    /**
     * 获取签到点
     * 
     * @param jsonStr
     * @return
     */
    public String getSignSites(String jsonStr);

    /**
     * 获取未完成巡线点
     * 
     * @param jsonStr
     * @return
     */
    public String getTaskCondition(String jsonStr);

    /**
     * 作用： 　　*作者：
     * 
     * @param jsonStr
     * @return
     */
    public String handUploadTrack(String jsonStr);
    
    /**
     * 获取无效时长
     * @param jsonStr
     * @return
     */
    public String getInvalidLineTime(String jsonStr);
    
    
    /**
     * 计算匹配的点
     * @param params
     */
    public void calSaveMatchSites(Map<String,Object> params);
    
	/**
	 * 根据指定日期手动触发轨迹匹配
	 * @param param
	 */
	public void calAllMatchSites(Map<String,Object> param);
	
	/**
	 * 获取巡线匹配定时任务
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryMatchQuartzInfos(Map<String,Object> param);
	
	/**
	 * 更新巡线匹配定时任务
	 * @param param
	 */
	public void updateMatchQuartzInfo(Map<String,Object> param);
	
	/**
     * saveHandUploadGtTrack
     * @param jsonStr
     * @return
     */
    public String saveHandUploadGtTrack(String jsonStr);
	
}
