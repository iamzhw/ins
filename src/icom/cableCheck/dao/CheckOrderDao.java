package icom.cableCheck.dao;

import java.util.List;
import java.util.Map;
@SuppressWarnings("all")
public interface CheckOrderDao {

	/**
	 * 插入dtsj表
	 */
	public void insertEqpAndPort(Map map);
	
	/**
	 * 设备地址和名称
	 */
	public void insertEquipment(Map map);
	
	/**
	 * 经纬度
	 */
	public void insertEqpoint(Map map);
	
	/**
	 * 设备记录
	 */
	public void insertEqpRecord(Map map);
	

	/**
	 * 设备记录
	 */
	public void insertEqpRecord_new(Map map);
	/**
	 * 设备记录
	 */
	public void insertEqpRecordNew(Map map);
	
	
	
	/**
	 * 保存任务工单
	 */
	public void saveTroubleTask(Map map);
	
	/**
	 * 保存任务详细
	 */
	public void saveTroubleTaskDetail(Map map);
	
	/**
	 * 查询人员所在区域
	 */
	public Map queryAreaByStaffId(String staffId);

	public void insertPhotoRel(Map photoMap);
	
	public int getRecordId();
	
	public void updateState(Map map);
	/**
	 * 更新现场规范
	 * @param map
	 */
	public void updateRemarks(Map map);
	/**
	 * 更新动态端子表检查时间
	 * @param map
	 */
	public void updateDtdz(Map map);
	/**
	 * 根据taskid查询task任务
	 * @param taskId
	 * @return
	 * @author wangxy 20160712
	 */
	public Map queryTaskByTaskId(Map map);
	/**
	 * 根据taskid和当前任务status更新status
	 * @param map
	 * @author wangxy 20160712
	 */
	public void updateStatus(Map map);

	public void cancelBill(Map cancelBillMap);
	public void forwardBill(Map forwardBillMap);
	public void forwardzgBill(Map forwardBillMap);
	public Map queryAreaByeqpId(Map map);
	/**
	 * 更新任务表
	 * @param map
	 */
	public void updateTask(Map map);
	/**
	 * 更新任务表，检查完成时间
	 * @param map
	 */
	public void updateTaskTime(Map map);
	
	/**
	 * 回单操作，更新任务表
	 * @param map
	 */
	public void updateTaskBack(Map map);
	
	/**
	 * 回单操作，更新任务表
	 * @param map
	 */
	public void updateTaskMaintor_status(Map map);
	/**
	 * 更新任务上次改动时间
	 * @param map
	 */
	public void updateLastUpdateTime(Map map);

	public String getTaskCompany(String taskId);
	
	/**
	 * 将采集的设备经纬度插入设备表，实质修改 longitude_inspect，latitude_inspect这两个字段的值
	 */
	public void updateLongLati(Map map);
	
	/**
	 * 修改设备经纬度更改次数
	 */
	public void updateLongLatiTimes(Map map);
	
	/**
	 * 获取设备经纬度更改次数
	 */
	public String getLongLatiTimes(Map map);

	/**
	 * 将检查完成时间插入到设备表
	 */
	public void updateCheckCompleteTime(Map map);
	
	/**
	 * 修改设备经纬度更改次数与设备经纬度,将检察人员的位置作为设备经纬度信息，修改时间，修改人员
	 */
	public void updateLongLati_times(Map map);

	
	/**
	 * 记录覆盖地址检查信息
	 */
	public void insertEqpAddress(Map adddressMap);
   
	public int geteqpAddressId();

	public String getStaffRole(String staffId);

	
	/**
	 * 获取设备类型，是分光器还是箱子
	 */
	public String getEqpType(Map map);
	
	/**
	 * 如果为分光器，则获取分光器的所属设备
	 */
	public List<Map<String,Object>> getEqp(Map map);
	
	/**
	 * 现场复查插入箱子设备端子信息
	 */
	public void insertCheckEqpRecord(Map eqpMap);
	/**
	 * 现场复查插入分光器设备端子信息
	 */
	public void insertReviewRecord(Map eqpMap);

	/**
	 * 填写设备标识
	 */
	public void insertEqpMark(Map eqpMap);

	public String getAuitor(String eqpAreaId);

	public String getContractor(String identification);

	public String getConstructorId(String constructor);

	public void updateEquipPortNUM(Map portmap);
	
	//根据staffid获取人员姓名和账号
	public Map queryByStaffId(String staffId);
	
	//根据光路编码查询施工人账号
	public List queryGlbm(String glbm);
	
	
	/**
	 *将检查人员每次采集的设备经纬度插入经纬度表，记录每次采集结果
	 */
	public void insertEqpLongLati(Map map);

	//整治对应设备承包人员账号
	public Map equCbAccount(String eqpNo);
	//光网助手匹配兜底岗相关人员
	public List PositionPersons(String areaId);
	
	public Map queryDdj(String code);
	
	public void updateResultTask(Map map);
	
	/**
	 * 一次性更新任务表
	 * @param map
	 */
	public void updateTask_once(Map map);

	public int getBatchId();

	public List<Map> getPortDetails(List<Double> portIds);

	public void insertBatchData(Map obj);

	public List<Map> getCSVGroup(int batchId);

	public List<Map> getIOMGroup(int batchId);

	public String getTeamReply(String csvTeam);

	public String getReplyId(String otherSysStaffId);

	public List<Map> getUserOreder(String staffId);
	
	public List<Map> doGetOreder(Map map);
	/**
	 * 通过设备id和端子id去获取工单信息
	 */
	public Map<String, String> getOrderInfo(Map map);
	
	public void saveTrace(Map map);
	public void saveEqpTrace(Map map);
	
	public void insertEqpRecordZg(Map photoMap);
	//获取旧的taskid
	//public String getOldTaskId(Map map);
	
	/**
	 * 保存任务工单
	 */
	public void saveTroubleTaskNew(Map map);
	
	public String getAuditorByTaskId(Map map);
	
	//通过工单查询工单所在班组
	public Map<String,String> getOrderOfBanZu(String order_no);
	
	//通过设备ID，设备编码，地市查询设备类型及所属网格
	public Map<String,String> getEqpType_Grid(Map  param);
	
	//光网助手匹配兜底岗相关人员
	public List<Map<String, String>> queryDouDiGang(String grid);
	
	//插入记录
	public void insertEqpRecordNewYy(Map map);
	
	public void updateEqu_time_num(Map map);
	
	public Map<String,String> getEqpContractNo(Map  param);
	
	//插入线路工单表
	public void insertLineWorkOrder(Map map);
	
	//插入线路工单表
	public List<Map<String, String>> queryProcessByOldTaskId(String taskId);
	
	public void insertEqpRecordPartZg(Map photoMap);
	
	/**
	 * 通过设备id和端子id去获取工单信息
	 */
	public Map<String, String> getOrderInfoNew(Map map);
	
	//通过工单查询工单所在班组
	public Map<String,String> getOrderOfBanZuNew(String order_no);
	//通过外系统账号ID查询光网助手中的人员账号ID
	public Map<String,String> getStaffIdByOther(String otherId);
	
	public void updateTaskOrder(Map map);
	
	public Map<String, String> getOuterEqpInfo(Map map);
	
	public Map<String, String> getIomStaffNo(Map map);
		
	public Map<String, String> getOrderActionType(String orderNo);
	//通过设备所属区域获取区级管理员、审核员
	public Map<String, String> getSonAreaAdmin(String sonAreaAdmin);
	
	public String getPhotoUrl(String photoIds);
}
