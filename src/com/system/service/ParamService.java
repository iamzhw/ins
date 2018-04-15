package com.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ParamService {

	/**
	 * 查询参数
	 * 
	 * @param request
	 * @return
	 */
	public Map<String,Object> query(HttpServletRequest request);
	
	
	/**
	 * 参数保存
	 * 
	 * @param request
	 */
	public void save(HttpServletRequest request);
	
	/**
	 * 根据键值获取值
	 * 
	 * @param paramName
	 * @param areaId
	 * @return
	 */
	public String getParamValue(String paramName,String areaId);
	
	/**
	 *  根据日期计算前一天巡线无效时长
	 *  
	 * @param queryDate
	 */
	public void calLineTime(String queryDate);
	
	/**
	 * 获取无效时长
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String,Object>> getValidTime(Map<String,Object> param);
	
	
	/**
	 * 查询停留超过40分钟是否包含GPS无信号和GPS未打开
	 * 
	 * @param invalidStayList
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    public boolean isRepeatTime(List<Map<String,Object>> invalidStayList,String startTime,String endTime);
    
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
	 * 消息推送
	 */
	public void pushMessage();
	
	/**
	 * 插入推送消息，等待定时任务推送
	 * @param param
	 */
	public void insertPushMessage(Map<String,Object> param);
	
	
	/**
	 * 日志记录方法
	 * 
	 * @param className 类名称
	 * @param methodParam 方法和参数
	 * @param logType 日志类型:DEBUG,INFO,ERROR,WARN
	 * @param logInfo 日志内容
	 */
	public void saveLogInfo(String className,String methodParam,String logType,String logInfo);
	
	
	
	/**
	 * 保存登录或注销记录
	 * 
	 * @param userId 用户ID
	 * @param sn 手机串号
	 * @param isLogin 是否登录 1登录0注销
	 * @param releaseId 是否人员ID，可为空
	 */
	public void insertLogin(String userId,String sn,String isLogin,String releaseId);
	
	
	/**
	 * 查询最后一次登录信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getBaseLoginInfo(Map<String,Object> param);
	
	
	/**
	 * 插入到位率数据
	 * @param executeDate
	 * 
	 */
	public void insertInspectArrate(String executeDate);
	
	/**
	 * 自动巡线任务
	 */
	public void autoLineTask(HttpServletRequest request);


	public Map<String, Object> getOtherLoginInfo(Map<String, Object> param);


	public Map<String, Object> getOnlineLoginInfo(Map<String, Object> param);


	public Map<String, Object> getIsLoginInfo(Map<String, Object> param);
}
