package icom.axx.dao;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface AxxInterfaceDao {

	/**
	 * 根据巡线段id查巡线点
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> findSiteByLine(Map<String, Object> map);
	
	/**
	 * 获取当前用户当天的任务及巡线点（site_type=1关键点）
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getTaskByUserId(Map<String, Object> map);
	
    /**
     * @param areaId
     * @return
     */
    List<Map<String, Object>> getRelayByArea(String areaId);

    /**
     * @param map
     */
    void saveInspInfo(Map<String, Object> map);
    
    /**
     * 从手机端获取轨迹提醒信息
     * @param map
     */
    void insertMobileWarnMsg(Map<String, Object> map);

    /**
     * @return
     */
    String getSiteId();

    /**
     * @param photoMap
     */
    void insertPic(Map<String, Object> photoMap);

    /**
     * @param p
     * @return
     */
    List<Map<String, Object>> getTodayTask(Map<String, Object> p);

    /**
     * @param task_id
     * @return
     */
    Map<String, Object> getArrivalRate(String task_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param map1
     * @return
     */
    List<Map<String, Object>> getStaffInfos(Map<String, Object> map1);

    /**
     * 作用： 巡线时长
     * 
     * @param para
     * @return
     */
    List<Map<String, Object>> getLineTimes(Map<String, Object> map1);

    /**
     * 作用： 　　*作者：
     * 
     * @param task_id
     * @return
     */
    Map<String, Object> getKeyArrivalRate(String task_id);

    /**
     * 作用： 　　*作者：
     * 
     * @return
     */
    String getNextval();

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     */
    void saveHoleopencheck(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param task_id
     * @return
     */
    List<Map<String, Object>> getLineByTaskId(String task_id);

	List<Map<String, Object>> getOutSiteByTaskId(String task_id);

	List<Map<String, Object>> getStepTaskByInspectId(String userId);

	List<Map<String, Object>> getStepEquipAllotByTaskId(String task_id);

	Map<String, Object> getStepAllotArrivalRate(String task_id);
	
	/**
	 * 根据中继段查询出外力点的数量
	 * @param relay_id
	 * @return
	 */
	String getOutSiteCount(Map<String, Object> map);
	
	
	//2016/3/31 孙敏 添加 查询 1000平方米之内的所有路由设施点
	public List<Map<String, Object>> selEquipPartByXY(Map<String, Object> map);
}
