package icom.cableCheck.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface CheckTaskDao {
	
	/**
//	 * 获取区域
//	 * @param map
//	 * @return
//	 */
	
	public int getArea(Map map);
//	/**
//	 * 获取所有任务
//	 * @param map
//	 * @return
//	 */
//
//	public List<Map> getTaskList(Map map);
//	
	
	/**
	 * 获取检查类型为0时设备
	 * @param map
	 * @return
	 */
	
	public List<Map> getDtsjList(Map map);
	
	
	/**
	 * 获取动态设备
	 * @param map
	 * @return
	 */
	
	public List<Map> getClList(Map map);
	
	/**
	 * 获取检查类型为0列表
	 * @param map
	 * @return
	 */
	
	public List<Map> getEqpList(Map map);
	
	/**
	 * 获取距离范围内所有设备列表
	 * @param map
	 * @return
	 */
	
	public List<Map> getEquipList(Map map);
	
	/**
	 * 获取经纬度
	 * @param map
	 * @return
	 */
	
	public List<Map> getDistance(Map map);
	
	
	/**
	 * 获取设备的所有端子数
	 * @param map
	 * @return
	 */
	public int getAllPort(Map map);
	
	/**
	 * 获取设备的变动端子数
	 * @param map
	 * @return
	 */
	public int getChangePort(Map map);
	
	/**
	 * 获取任务列表
	 * @param map
	 * @return
	 */
	public List<Map> getTaskList(Map map);
	
	/**
	 * 获取设备编码或设备名称模糊匹配设备
	 * @param map
	 * @return
	 */
	
	
	public List<Map> getEqpByEup(Map map);
	
	/**
	 * 获取光路编号模糊匹配设备
	 * @param map
	 * @return
	 */
	
	
	public List<Map> getEqpByLight(Map map);
	
	/**
	 * 获取光路编号模糊匹配设备 动态端子表，
	 * @param map
	 * @return
	 */
	
	
	public List<Map> getEqpByDZLight(Map map);

	/**
	 * 获取设备编码精确匹配设备
	 * @param map
	 * @return
	 */
	
	
	public List<Map> getAccurateEup(Map map);
	/**
	 * 获取端子变动时间
	 * @param eqpId
	 * @return
	 */
	public String getBDSJByEqpId(String eqpId);
	/**
	 * 是否全为H光路
	 * @param map
	 * @return
	 */
	public String getIsH(String eqpId);
	
	/**
	 * @description 根据角色ID和区域查询人员信息
	 * @param m
	 * @return
	 */
	public List<Map> getStaffByRoleAndArea(Map m);
	/**
	 * 获取设备的上次检查端子数
	 * @param map
	 * @return
	 */
	public List<Map> getCheckPort(Map a);
	
	
	 /**
	  * 获取光路、全部端子数及其比率
	  * @param map
	  * @return
	  */
	public List<Map> getRateOfAllGl(Map map);
	
	
	/**
	 * 根据staffid获取角色
	 * @param map
	 * @return
	 */
	public List<Map> getRoleId(String staffId);

	public int getDelPort(Map a);
	

	
	/**
	 * @description 查询当前位置的网格单元（集团对标）
	 * @param m
	 * @return
	 */
	public List<Map> selectGrid(Map m);
	
	
	/**
	 * 获取设备列表
	 * @param map
	 * @return
	 */
	public List<Map> getEmpList();
	
	/**
	 * 获取距离内设备
	 * @param map
	 * @returngetGridQum
	 */
	
	public List<Map> getDistanceEmp(Map map);
	
	/**
	 * 归属网络设施展示（集团对标）
	 * @param map
	 * @return
	 */
	
	public List<Map> getGridQum(Map map);
	
	public List<Map> getGridQumAll(Map map);
	
	/**
	 * 归属网络设施展示---分光器（集团对标）
	 * @param map
	 * @return
	 */
	
	public List<Map> getGridQumByOBD(Map map);
	
	/**
	 * 查询所属光交/光分设施的总数
	 * @param map
	 * @return
	 */
	
	public List<Map> getGridQumCount(Map map);
	
	
	//获取网格设备所属工单
	List<Map> getEquWorkOrder(Map map);
	
	//获取分光器设备所属工单
	List<Map> getEquWorkOrderByObd(Map map);
	
	/**
	 * 通过工单编号查询端子信息
	 */
	
	public List<Map> getPortList(Map map);
	

	/**
	 * 通过设备查询所有工单端子信息
	 */
	
	public List<Map> getEqpOrderList(Map map);
	
	/**
	 * 通过网格下设备id和设备编码查询出该设备下的所有分光器
	 */
	
	public List<Map<String,String>> getObd(Map map);
	
	//查询工单详细信息
	List<Map> selectWorkOrderList(Map map);
	
	//作业计划实施-现场资源查询（集团对标）
	List<Map> selectResourcesCheck(Map map);
	
	//作业计划实施-工单查询（集团对标）
	List<Map> getWPWorkOrder(Map map);
	
	//作业计划实施-工单详细信息（集团对标）
	List<Map> selectWPWorkOrderList(Map map);
	
	//作业计划实施-错误数据修改业务单（集团对标）
	List<Map> ErrorWorkOrder(Map map);
	
	//作业计划实施-修改信息校验（集团对标）
	List<Map> updatecheck(Map map);
	
	//作业计划实施-修改痕迹记录（集团对标）
	List<Map> modifytraces(Map map);
	
	//集约化工单
	List<Map> intensificationworkoder(Map map);
	
	//集约化工单详情
	List<Map> workoderdetail(Map map);
	
	//集约化工单打分详情
	List<Map> workoderScoredetail(Map map);
	
	//集约化工单检查结果接口
	void workOderResult(Map map);
	
	//集约化工单检查结果明细
	void workOderResultDetail(Map map);
	
	
	//光网助手系统生成检查任务，主业人员检查
	List<Map> TaskCheck(Map map);
	
	//已完成的检查任务，将工作量推送给集约化平台
	List<Map> outSysDispatchTask(Map map);
	
	/**
	 * 错误信息记录(集团对标)
	 * @param map
	 * @return
	 */
	
	public void saveError(Map map);
//获取人员所在区域的网格
	public List<Map> getGrid(HashMap param);
	
	/**
	 * 通过检查员名称模糊查询最近一次检查设备
	 * @param map
	 * @return
	 */		
	public List<Map> getEqpByCheckName(Map map);
	
	//获取光路路由
	List getLightPath(Map map);
	
	/**
	 *通过分光器设备ID和设备编码查询箱子的设备信息
	 */
	
	public Map<String,String> getOuterEqp(Map map);
	
	/**
	 * 保存错误修改工单
	 */
	public void saveTroubleTask(Map map);
	

	/**
	 * 保存错误修改工单详细
	 */
	public void saveTroubleTaskDetail(Map map);
	
	/**
	 * 保存错误修改工单详细记录
	 */
	public void insertEqpRecord(Map map);
	
	/**
	 *通过工单编号查询出工单所在班组以及施工人
	 */
	
	public Map<String,String> getOrderOfBanZu(String order_no);
	
	/**
	 *通过班组id查询班组接单人
	 */
	
	public Map<String,String> getOrderReceiverOfBanZu(String order_no);
	
	/**
	 *通过任务id和端子id获取检查记录中的正确位置
	 */
	
	public String getportRightPosition(Map map);
	

	/**
	 *通过设备id和光路编号获取检查记录中的正确位置
	 */
	
	public Map<String, String> getOssPort(Map map);
	

	List<Map> getEquCSVIOMOrder(Map map);
	
	List<Map> getPortsByEqu(Map map);
	
	List<Map> getEqpBySG(Map map);
	
	public Map<String,String> getBanZuByOrderNo(String order_no);
	
	/**
	 *通过设备ID和地市查询OSS的所有端口
	 */
	
	public List<Map<String,String>> getPortsByAreaEqu(Map map);
	
	/**
	 *设备检查时间
	 */
	
	public void updateEqpCheckCompleteTime(Map map);
	
	/**
	 *工单检查时间
	 */
	
	public void updateOrderCheckTime(Map map);
	
	/**
	 * 根据设备id查询工单数量
	 * @param eqpId
	 * @return
	 */
	public Integer getCountByEquId(Map map);
	/**
	 *通过设备ID和工单编号查询出该工单的所有流程记录
	 */
	
	public List<Map<String, String>> getOrderRecords(Map map);
	
	public Map<String,String> getEquType(Map param);
	
	/**
	 * 留痕记录保留端子
	 */
	public void saveTrace(Map map);
	
	/**
	 * 留痕记录保留设备
	 */
	public void saveEqpTrace(Map map);
	
	/**
	 * 插入流程环节
	 * @param map
	 */
	public void addProcess(Map map);
	
	public String getAllEqpNum(Map map);
	
	public List<Map> getGridQumAllTask(Map map);
	
	public void addProcessNew(Map map);
	//通过设备ID和地市ID查询箱子
	public Map getEqpOut(Map map);
	
	public Map getEqpDetail(Map map);

	public void updateTaskDetail(Map map);
	
	public void updateOrder(Map map);
	
	/*判读账户是否是一级检查人员*/
	public int isOneStaff(String staffId);
	/*判读账户是否是二级检查人员*/
	public int isTwoStaff(String staffId);
	
	public String getOssInfoByGlbm(Map map);
	
}
