package icom.cableCheck.dao;

import java.util.List;
import java.util.Map;

/** 
 * @author wangxy
 * @version 创建时间：2016年7月27日 下午5:25:23 
 * 类说明 
 */
@SuppressWarnings("all")
public interface CheckTaskProcessDao {
	
	/**
	 * 获取流程信息
	 * @param map
	 * @return
	 */
	public List<Map> getProcess(Map map);
	
	/**
	 * 获取oldtaskid
	 */
	public String getOldTaskId(Map map);
	
	public List<Map> getProcessInfo(Map map);

}
