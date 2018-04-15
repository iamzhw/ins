package icom.cableCheck.dao;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface CheckPortDao {
	

	/**
	 * 获取所有任务
	 * @param map
	 * @return
	 */

	public List<Map> getTaskList(Map map);
	
	/**
	 * 通过任务查询所有端子信息
	 * @param map
	 * @return
	 */

	public List<Map> getDtsjList(Map map);
	/**
	 * 任务查所有端子
	 * @param map
	 * @return
	 */
	public List<Map> getAllTaskDtsjList(Map map);
	
	/**
	 * 周期性任务查询变动端子信息
	 * @param map
	 * @return
	 */
	
	public List<Map> getBdList(Map map);
	
	/**
	 * 隐患任务查询变动端子信息
	 * @param map
	 * @return
	 */
	public List<Map> getTroubleBdList(Map map);
	
	/**
	 * 通过设备查端子
	 * @param map
	 * @return
	 */
	
	public List<Map> getPortList(Map map);
	
	/**
	 * 通过设备查光路号
	 * @param map
	 * @return
	 */
	public List<Map> getGlList(Map map);
	
	/**
	 * 通过端子查光路号
	 * @param map
	 * @return
	 */
	public List<Map> getOssGlList(Map map);
	
	/**
	 * 查端子的变动次数
	 * @param map
	 * @return
	 */
	public List<Map> getGDList(Map map);
	/**
	 * 根据设备类型Id查设备类型
	 * @param map
	 * @return
	 */
	
	 public List<Map> getParentArea(String area);
	 
	 /**
		 * 根据任务Id查任务类型
		 * @param map
		 * @return
		 */
	 
	 public int  getTaskType(String taskid);
	 
	 /**
		 * 查隐患任务下端子
		 * @param map
		 * @return
		 */
	 public List<Map>  getTroubleList(Map map);
	 
	 /**
		 * 查询设备下所有端子
		 * @param map
		 * @return
		 */
	 public List<Map> getAllList(Map map);
	 
	 /**
	  * 根据EqpId查询分光器信息
	  * @param map
	  * @return
	  */
	 public List<Map> getObdsByEqpId(Map map);
	 /**
	  * 查询分光器端子变动个数
	  * @param map
	  * @return
	  */
	 public List<Map> getChangePortNum(Map map);
	 /**
	  * 根据id查地市名称
	  * @param areaId
	  * @return
	  */
	 public String getAreaNameByAreaId(String areaId);
	 
	 /**
	  * 获取设备的成端信息
	  * @param map
	  * @return
	  */
	public List<Map> getEqpCDInfo(Map map);

	public List<Map> getOssGlDetailList(Map param);
	
	/**
	 * 获取上一次端子变动时间
	 * @param map
	 * @return
	 */

	public List<Map> getLastChangePortsList(Map map);
	
	 /**
	  * 获取设备的对端信息
	  * @param map
	  * @return
	  */
	public List<Map> getEqpDDInfo(Map map);
	
	 /**
	  * 获取设备的跳接信息
	  * @param map
	  * @return
	  */
	public List<Map> getEqpJumpInfo(Map map);

	 /**
	  * 获取地市CODE
	  * @param map
	  * @return
	  */
	public String getAreaCodeByAreaId(String areaId);

	public List<Map> getEqpAdd(Map param);

	public List<Map> getDelPortList(Map map);

	public List<Map> getDelPortNum(Map map);

	public List<Map> getIOMPortNum(Map changePortNumMap);

	public List<Map> getIOMDelPortList(Map param);
	
	/**
	 * 通过设备查17年新增光路
	 * @param map
	 * @return
	 */
	
	public List<Map> getAddPortList(Map map);
	
	 /**
	  * 通过光路编号获取施工人信息
	  * @param map
	  * @return
	  */
	public Map<String,Object> getPortSgInfo(Map map);
	 /**
	  * 通过光路编号获取更纤信息
	  * @param map
	  * @return
	  */
	public Map<String,Object> getPortGxInfoByIOM(Map map);
	
	 /**
	  * 通过光路编号获取更纤信息
	  * @param map
	  * @return
	  */
	public Map<String,Object> getPortGxInfoByIOPS(Map map);
	
	 /**
	  * 根据EqpId查询箱子或分光器端子检查记录
	  * @param map
	  * @return
	  */
	 public List<Map<String,Object>> getRecordByEqpId(Map map);
	 
	 
	 /**
	  * 根据EqpId查询箱子或分光器端子检查记录的个数
	  * @param map
	  * @return
	  */
	 public String getCheckedPortNum(Map map);
	 
	 /**
	  * 通过光路编号获取配置工号
	  * @param map
	  * @return
	  */
	public Map<String,Object> getPortPzInfo(Map map);
	/**
	 * 通过设备ID和端子ID获取端子规格ID
	 * 
	 * @param jsonStr
	 * @return
	 */
	public Map<String,String>  getEqpPortType(Map map);
	
	/**
	 * 通过设备ID获取设备规格id
	 * 
	 * @param jsonStr
	 * @return
	 */
	public Map<String,String>  getEqpPortTypeByEqpId(Map map);
	/**
	 * 插入修改端子记录表（tb_base_changeport_record）
	 */
	public void insertChangePortRecords(Map map);


	public Map<String,Object> getPortByOrder(Map map);
	
	/**
	 * 通过设备id和端子id获取施工人账号id和配置工号
	 */
	public Map<String, String> getThreeJobNumber(Map map);
	
	 /**
	  * 通过施工人账号id和配置工号获取相关信息
	  */
	 public List<Map<String, String>> getSgPzInfo(Map map);
	 
	 /**
	 * 通过工单id获取更纤人
	 */
	public Map<String, String> getGxInfo(Map map);
	//通过旧的设备ID和端子ID获取旧的设备规格ID和端子规格
	public Map<String,String>  getEqpPortType2(Map map);
	
}

