package icom.system.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.Query;

@SuppressWarnings("all")
public interface CableInterfaceDao {

	List<Map> getAllTask(Map<String, String> params);

	List<Map> getAllPoint(String taskId);
	List<Map> getTaskPoint(String taskId);
	List<Map> pointForDistance(Map map);

	String getPhotoId();

	int insertPhoto(Map<String, Object> photoMap);

	List<Map<String, Object>> getZones(String staffId);

	List<Map<String, Object>> getTroubleTypes();

	void saveBill(Map<String, Object> billMap);

	String getPlanIdByTaskId(String taskId);

	String getAreaIdBySonAreaId(String zoneid);

	void saveBillDetail(Map<String, Object> billDetailMap);

	void saveRecord(Map<String, Object> billMap);

	List<Map<String, Object>> getConsTask(Map<String, String> params);

	List<Map<String, Object>> getBills(String staffId);

	List<Map<String, Object>> getTroubles(Map<String, String> params);

	void insertUploadPoint(Map<String, Object> pointMap);

	void updatePointType(Map<String, Object> billMap);

	void updatePoint(Map<String, Object> billMap);

	void insertPhotoRel(Map<String, Object> billMap);

	void insertPoint(Map<String, Object> billMap);

	List<Map<String, String>> getRelInfo(Map<String, Object> billMap);

	List<Map<String, Object>> getAuditorBySonAreaId(String zoneid);

	void updateDetailComplete(String taskDetailId);

	Map<String, Object> getBillByPointId(String pointId);

	void updateBill(Map<String, Object> billMap);

	void updateBillDetail(Map<String, Object> billMap);

	List<Map<String, Object>> getAuditorByAreaId(String areaId);

	void updateBillAudit(Map<String, Object> billMap);

	List<Map> getAllPoint2(String taskId);
	
	List<Map<String, Object>> getAuditorNameBySonAreaId(String zoneid);

	String getDBblinkName(String area_id);

	List<Map> getOSSEqpmentInfoByPhy(Map map);
	
	List<Map> getOSSEqpmentInfoByBse(Map map);

	int findPlanKidByTaskDetailId(String taskDetailId);

	List<Map<String, Object>> findAllDetail(String taskDetailId);

	void updateTaskComplete(String taskId);

	void updateTaskBegin(String taskId);

	Integer queryDetailFinish(String taskDetailId);

	List<Map<String, Object>> findTaskDetailByTaskId(String taskId);

	int findSignPointById(Map<String, Object> billMap);

	List<Map> getAllKeepPoint(String taskId);
	
	void updateRecordType(Map m);
	
	Map ifPointExist(Map m);
	
	void insertEqpPoint(Map m);
	
	void updateEqpPoint(Map m);
	
	int ifEqpExistOss(Map m);
	
	List<Map> queryOnLineMembers(Map m);
	
	void addKeepPoint(Map map);
	
	String ifPointCanInsert(Map map);
	
	String calculateTimeCost(Map m);
	
	List<Map> getKeepPoints(Map m);
	
	List<Map> billQuery(Map m);
	
	Map getAreaByStaffId(String s);
	
	/**
	 * 获取检查项接口
	 */
	List<Map<String, Object>> getCheckCondition();
	
	/**
	 * 获取隐患类型接口
	 */
	List<Map<String, Object>> getTroubleType();
	
	/**
	 * 获取当前登录人的子区域id
	 * @param staff_id
	 * @return
	 */
	Map<String,Object> getStaffSonAreaID(String staff_id);
	void updatePointTypeByBillId(String s);
	/**
	 * 查询光缆或光缆段是否存在
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryCableExits(Map<String, Object> map);
	
	/**
	 * 查询光缆ID或者光缆段ID
	 * @return
	 */
	String queryLineId();
	
	/**
	 * 查询点ID
	 * @return
	 */
	String queryPointId();
	
	/**
	 * 查询是否有光缆或光缆段
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selIsHasLine(Map<String, Object> map);
	
	/**
	 * 获取最大的顺序+1的数值作为插入条件
	 * @param line_id
	 * @return
	 */
	String getMaxOrder(String line_id);
	
	/**
	 * 插入中间表
	 * @param map
	 * @return
	 */
	int intoLinePoint(Map<String, Object> map);
	
	/**
	 * 插入点表
	 * @param map
	 * @return
	 */
	int intoPoint(Map<String, Object> map);
	
	/**
	 * 插入线表
	 * @param map
	 * @return
	 */
	int intoLine(Map<String, Object> map);
	
	/**
	 * 根据光缆段查询光缆光缆段相关信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selCableRelay(String line_id);
	
	/**
	 * 根据光缆段查询点信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selPointInfo(String line_id);
	
	List<Map<String,Object>> queryOwnEqpByDis(Map m);
	
	Map ifTaskIsCable(Map m);
	void addStaffTask(Map m);
	void deleteStaffTask(Map m);
	
	int ifLinePointGreaterTwo(String s);

	/**
	 * 去OSS查询中继光缆编码
	 * @param map
	 * @return
	 */
	List<Map> queryOSSCableByNo(Map map);
	/**
	 * 修改光缆段上的路由
	* @Title: modifyPointInSect
	* @param @param params
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 上午10:52:41
	 */
	int modifyPointInSect(Map<String, Object> params);
	
	/**
	 * 查询缆线与路由点的关联关系
	* @Title: queryLinePoint
	* @param @param map
	* @param @return    设定文件
	* @return List<Map<String,Object>>    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 上午11:31:45
	 */
	List<Map<String, Object>> queryLinePoint(Map<String, Object> map);
	/**
	 * 删除光缆段上指定路由
	* @Title: deletePointInSect
	* @param @param params
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 上午11:28:41
	 */
	int deletePointInSect(Map<String, Object> params);
	/**
	 * 插入缆线与路由点的关联关系
	* @Title: saveLinePoint
	* @param @param map
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 下午02:30:29
	 */
	int saveLinePoint(Map<String, Object> map);
	/**
	 * 查询网格列表
	* @Title: queryGridList
	* @param @param map
	* @param @return    设定文件
	* @return List<Map<String,Object>>    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 下午04:30:01
	 */
	List<Map<String, Object>> queryGridList(Map<String, Object> map);
	
	/**
	 * 
	* @Title: queryGridListForTrouble
	* @Description: 为隐患上报查询网格
	* @param @param map
	* @param @return    设定文件
	* @return List<Map<String,Object>>    返回类型
	* @date 2017-12-7 上午10:36:49
	* @throws
	 */
	List<Map<String, Object>> queryGridListForTrouble(Map<String, Object> map);
	/**
	 * 新增光缆段上的路由
	* @Title: addPointInSect
	* @param @param params
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 下午03:53:29
	 */
	int addPointInSect(Map<String, Object> params);
	/**
	* 查询缆线与路由点的关联关系(根据揽线ID与开始点联合查询便于取pointSeq)
	* @Title: queryLinePoint2
	* @param @param map
	* @param @return    设定文件
	* @return List<Map<String,Object>>    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-26 上午10:30:16
	 */
	List<Map<String, Object>> queryLinePoint2(Map<String, Object> map);
	/**
	 * 查询缆线与路由点的关联关系(根据揽线ID与结束点联合查询便于取pointSeq)
	* @Title: queryLinePoint3
	* @param @param map
	* @param @return    设定文件
	* @return List<Map<String,Object>>    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-26 上午10:31:19
	 */
	List<Map<String, Object>> queryLinePoint3(Map<String, Object> map);
	
	String checkPointExists(String s);
	
	void updateCoordinate(Map m);

	List<Map<String, Object>> queryCablesExits(Map<String, Object> param);
	
	List<Map> zhxjTask(Map map);
}
