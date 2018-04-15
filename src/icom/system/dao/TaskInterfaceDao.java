package icom.system.dao;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface TaskInterfaceDao {

	/**
	 * 获取所有任务
	 * @param map
	 * @return
	 */
	public List<Map> getAllTask(Map map);
	
	/**
	 * 获取任务关联的所有待检查机房
	 * @param map
	 * @return
	 */
	public List<Map> getRoomsByTaskId(Map map);
	
	/**
	 * 获取机房总数
	 * @param map
	 * @return
	 */
	public int getRoomsCountByTaskId(Map map);
	
	/**
	 * 根据任务获取所有检查项
	 * @param map
	 * @return
	 */
	public List<Map> getCheckItemByTaskId(Map map);
	
	/**
	 * 根据检查项ID获取检查项信息
	 * @param itemId
	 * @return
	 */
	public Map getcheckItemByItemId(int itemId);
	
	/**
	 * 根据检查项ID获取隐患类型
	 * @param map
	 * @return
	 */
	public List<Map> getTroubleTypeByCheckItemId(Map map);
	
	/**
	 * 根据任务ID和机房ID获取执行详情ID
	 * @param map
	 * @return
	 */
	public int getActionIdByTaskIdAndRoomId(Map map);
	
	/**
	 * 保存工单信息
	 * @param map
	 */
	public void saveTrouble(Map map);
	
	/**
	 * 修改任务执行详情为已执行
	 * @param map
	 */
	public void updateActionDetail(Map map);
	
	/**
	 * 通过序列生成图片ID
	 */
	public int getPicId();
	
	/**
	 * 插入图片信息
	 * @param map
	 */
	public void insertPic(Map map);
	
	/**
	 * 建立检查图片和检查文字关联关系
	 * @param map
	 */
	public void createCheckDetailPic(Map map);
	
	/**
	 * 根据检查记录ID删除检查图片
	 * @param map
	 */
	public void deleteCheckPic(Map map);
	
	/**
	 * 关闭任务
	 * 
	 * @param map
	 */
	public void closeTaskbyTaskId(Map map);
	
}
