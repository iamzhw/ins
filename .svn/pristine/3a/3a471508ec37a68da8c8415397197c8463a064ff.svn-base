package com.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ParamDao {

    public List<Map<String, Object>> query(Map<String, Object> params);

    /**
     * 设置单个地市参数,需要传区域ID
     * @param map
     */
    public void save(Map<String, Object> map);
    
    
    /**
     * 修改各地市步巡段频次如果比省频次大的
     * @param map
     */
    public void upPartCirByCityCir(Map<String,Object> map);
    
    /**
     * 设置全省参数
     * @param map
     */
    public void saveAll(Map<String, Object> map);
    
    

    public Map<String, Object> getParamValue(Map<String, Object> params);

    /**
     * 根据参数名称获取参数值
     * 
     * @param map
     * @return
     */
    public String getParamValueByName(Map<String, Object> map);
    
    /**
     * 获取用户轨迹时间
     * 
     * @param map
     * @return
     */
    public List<Map<String, Object>> getAllTrackTime(Map<String, Object> map);
    
    
    /**
     * 得到轨迹匹配数量
     * @param map
     * @return
     */
    public int getTrackMatchCount(Map<String, Object> map);
    
    
    /**
     * 查询停留超过40分钟时间
     * @param map
     * @return
     */
    public List<Map<String,Object>> getInvalidSiteStayTime(Map<String, Object> map);
    
    
    /**
     * 查询无数据上传时间，连续两点间隔超过10分钟，视为点丢失
     * @param map
     * @return
     */
    public List<Map<String,Object>> getNoDataUploadTime(Map<String, Object> map);
    
    /**
     * 查询江苏省所有地市
     * 
     * @param map
     * @return
     */
    public List<Map<String,Object>> getAllAreaByJS(Map<String,Object> map);
    
    
    /**
     * 查询所有巡线人员
     * @param map
     * @return
     */
    public List<Map<String,Object>> getLineStaffs(Map<String,Object> map);
    
    /**
     * 存储无效时长
     * @param map
     */
    public void saveInValidTime(Map<String,Object> map);
    
    /**
     * 获取看护计划时间段
     * @param map
     * @return
     */
    public List<Map<String,Object>> getGuardPlanTime(Map<String, Object> map);
    
    /**
     * 获取看护轨迹时间
     * @param map
     * @return
     */
    public List<Map<String,Object>> getGuardTrackTime(Map<String, Object> map);
    
    /**
     * 获取人员巡检点
     * @param map
     * @return
     */
    public List<Map<String,Object>> getSitesByUser(Map<String, Object> map);
    
    
    /**
     * 修改点信息
     * @param map
     */
    public void updateSites(Map<String, Object> map);
    
    /**
     * 保存转换失败站点
     * @param map
     */
    public void saveInvalidSites(Map<String, Object> map);
    
	/**
	 * 插入推送消息，等待定时任务推送
	 * @param param
	 */
	public void insertPushMessage(Map<String,Object> param);
	
	
	/**
	 * 查询推送记录
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getPushMessageList(Map<String,Object> param);
	
	
	/**
	 * 删除推送记录
	 * 
	 * @param param
	 */
	public void deletePushMessage(Map<String,Object> param);
	
	
	/**
	 * 插入推送记录历史表,推送成功转历史
	 * @param param
	 */
	public void inserPushMessageHis(Map<String,Object> param);
	
	/**
	 * 更新推送消息
	 * @param param
	 */
	public void updatePushMessage(Map<String,Object> param);
	
	
	/**
	 * 日志记录
	 * 
	 * @param param
	 */
	public void saveLogInfo(Map<String,Object> param);
	
	/**
	 * 保存登录或注销记录
	 * 
	 * @param param
	 */
	public void insertLogin(Map<String,Object> param);
	
	/**
	 * 保存有效时长
	 * @param param
	 */
	public void saveValidTime(Map<String,Object> param);
	
	public List<Map<String,Object>> getTaskSitesByUser(Map<String,Object> param);
	
	public List<Map<String,Object>> getCalTrackList(Map<String,Object> param);
	
	public void deleteMatchSitebyTaskId(Map<String,Object> param);
	
	/**
	 * 查询所有到位率数据
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getInspectArrayRates(Map<String,Object> param);
	
	/**
	 * 查询单个到位率到位率
	 * @param param
	 * @return
	 */
	public int getInspectArrate(Map<String,Object> param);
	
	/**
	 * 删除巡检到位率
	 * 
	 * @param param
	 */
	public void deleteInspectArrate(Map<String,Object> param);
	
	/**
	 * 插入到位率数据
	 * @param param
	 */
	public void insertInspectArrate(Map<String,Object> param);
	
	/**`
	 * 更新到位率
	 * 
	 * @param param
	 */
	public void updateInspectArrate(Map<String,Object> param);
	
	/**
	 * 查询登录信息
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getBaseLoginInfo(Map<String,Object> param);
	
	
	/**
	 * 查询有任务的区域
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getInspectTaskAreas(Map<String,Object> param);
	
	/**
	 * 删除巡线轨迹时间
	 * 
	 * @param param
	 */
	public void deleteInpsectTime(Map<String,Object> param);
	
	
	/**
	 * 查询巡线有效时间
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getInspectTimes(Map<String,Object> param);

	public List<Map<String, Object>> getOtherLoginInfo(Map<String, Object> param);

	public List<Map<String, Object>> getOnlineLoginInfo(
			Map<String, Object> param);

	public List<Map<String, Object>> getIsLoginInfo(Map<String, Object> param);
}
