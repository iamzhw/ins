package icom.cableCheck.dao;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface CheckReformOrderDao {

	
	/**
	 * 根据taskId获取任务和设备信息
	 */
	public Map getMessageByTaskId(String staffId);
	
	/**
	 * 获取端子信息
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getPortMessage(Map map);
	/**
	 * 获取设备或端子照片
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getEqpOrPortPhoto(Map map);
	/**
	 * 根据端子id获取端子照片
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getPortPhoto(Map map);
	/**
	 * 根据端子id获取端子整改照片
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getzgPortPhoto(Map map);

	public Map getzgMessageByTaskId(String taskId);

	public List<Map<String, Object>> getzgEqpPhoto(Map map);

	public List<Map<String, Object>> getzgPortMessage(Map map);

	public List<Map<String, Object>> getcheckPortMessage(Map map);

	public List<Map<String, Object>> getzgPortMessageall(Map map);

	public List<Map> getGDList(Map map);
	
	/**
	 * 获取整改端子信息
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getPortMessageZg(Map map);
}
