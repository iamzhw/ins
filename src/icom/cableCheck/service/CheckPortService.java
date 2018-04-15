package icom.cableCheck.service;

public interface CheckPortService {

	
	/**
	 * 获取端子列表
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String getCheckPort(String jsonStr);
	/**
	 * 获取设备的成端信息
	 * @param jsonStr
	 * @return
	 */
	public String getEqpCDInfo(String jsonStr);
	/**
	 * 获取端子具体信息
	 * 
	 * @param jsonStr
	 * @return
	 */
	
	
	public String getPortInfos(String jsonStr);
	/**
	 * 点击端子编码获取端子具体信息
	 * 
	 * @param jsonStr
	 * @return
	 */
	
	public String getPortDetail(String jsonStr);
	public String getEqpAddress(String jsonStr);
	String getFOptRoute(String jsonStr);
	

	/**
	 * OSS查询空闲端口
	 */
	String queryFreePort(String jsonStr);
	/**
	 * OSS修改端子信息
	 */
	String changePort(String jsonStr);
	/**
	 * 获取17年新增光路
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String addPortsInEqp(String jsonStr);
	
	/**
	 * 现场复查查看端子检查记录
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String checkAgain(String jsonStr);
	
	
	/**
	 * 查询端子的相关信息
	 * @param jsonStr
	 * @return
	 */
	public String getPortByOrder(String jsonStr);
	
	/**
	 * 工单端子检查页面通过切换按钮至设备端子检查页面
	 */
	public String checkEqpPorts(String jsonStr);
	
	
}
