package icom.axx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface LineSiteInterfaceDao {

	/**
	 * 根据人员查询当天所有任务关联的巡线点
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getTaskSitesByUser(Map<String,Object> param);
	
	/**
	 * 根据人员查询当天所有任务关联的外力点
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getTaskOutSitesByUser(Map<String,Object> param);
	
	/**
	 * 获取轨迹ID
	 * 
	 * @return
	 */
	public int getTrackId();
	
	/**
	 * 获取签到点ID
	 * 
	 * @return
	 */
	public int getRegisterId();
	
	/**
	 * 获取站点匹配ID
	 * @return
	 */
	public int getSiteMatchId();
	
	/**
	 * 获取用户当前最大轨迹点
	 * @return
	 */
	public int getMaxTrackId(Map<String,Object> param);
	
	
	/**
	 * 获取最近的轨迹
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getMaxTrackByUser(Map<String,Object> param);
	
	/**
	 * 获取最近的有信号轨迹
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getMaxHasFlagTrackByUser(Map<String,Object> param);
	
	/**
	 * 获取最近的匹配点
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getMaxMatchSite(Map<String,Object> param);
	
	
	/**
	 * 保存自动上传轨迹
	 * 
	 * @param track
	 */
	public void saveAutoTrack(Map<String,Object> track);
	
	/**
	 * 保存匹配站点信息
	 * 
	 * @param site
	 */
	public void saveMatchSite(Map<String,Object> site);
	
	/**
	 * 保存签到点信息
	 * 
	 * @param register
	 */
	public void saveRegisterSite(Map<String,Object> register);
	
	
	/**
	 * 根据人员查询当天所有任务关联的未完成巡线点
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getUnfinishSitesByUser(Map<String,Object> param);
	
	/**
	 * 根据人员查询当天所有任务关联的未完成签到点
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getUnfinishSignSitesByUser(Map<String,Object> param);
	
	
	/**
	 * 根据人员和点查询当天的任务点
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getTaskSitesByUserAndSite(Map<String,Object> param);
	
	/**
	 * 根据人员和日期查询无效时长
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getInvalidTime(Map<String,Object> param);
	
	
	/**
	 * 获取当前计算轨迹
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getCalTrackList(Map<String,Object> param);
	
	/**
	 * 获取当天巡线轨迹
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getTrackList(Map<String,Object> param);
	
	/**
	 * 保存巡线匹配定时任务
	 * @param param
	 */
	public void saveMatchQuartzInfo(Map<String,Object> param);
	
	
	/**
	 * 根据用户ID和用户轨迹查询轨迹是否存在
	 * @param param
	 * @return
	 */
	public int getUserTrackTime(Map<String,Object> param);
	
	/**
	 * 删除巡线匹配定时任务
	 * @param param
	 */
	public void deleteMatchQuartzInfo(Map<String,Object> param);
	
	/**
	 * 更新巡线匹配定时任务
	 * @param param
	 */
	public void updateMatchQuartzInfo(Map<String,Object> param);
	
	
	/**
	 * 获取巡线匹配定时任务
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryMatchQuartzInfos(Map<String,Object> param);
	
	/**
	 * 根据任务ID和关键点ID查询是否已经签到
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryKeysiteByUser(Map<String,Object> param); 
	
	/**
	 * 根据轨迹ID和巡线点ID查询是否已经匹配
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryMatchSiteByTrack(Map<String,Object> param); 
	
	/**
	 * 获取最近的高铁轨迹
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getMaxGtTrackByUser(Map<String,Object> param);
	
	/**
	 * 根据用户ID和用户查询高铁轨迹是否存在
	 * @param param
	 * @return
	 */
	public int getUserGtTrackTime(Map<String,Object> param);
	
	/**
	 * 保存自动上传高铁轨迹
	 * 
	 * @param track
	 */
	public void saveAutoGtTrack(Map<String,Object> track);
	
	/**
	 * 获取高铁轨迹ID
	 * 
	 * @return
	 */
	public int getGtTrackId();
	
	/**
	 * 保存用户上传时间记录
	 * @param param
	 */
	public void saveUserUploadTime(Map<String, Object> param);
	
	/**
	 * 查询用户一分钟内是否做过上传
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectUserUploadTime(Map<String, Object> param);
}
