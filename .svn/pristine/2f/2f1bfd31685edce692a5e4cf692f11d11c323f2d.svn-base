package icom.cableCheck.dao;

import java.util.Map;

@SuppressWarnings("all")
public interface CheckPhotoDao {

	/**
	 * 通过序列生成图片ID
	 */
	public int getPicId();
	
	/**
	 * 插入图片信息
	 * @param map
	 */
	public int insertPic(Map<String, Object> photoMap);
	
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
	 * 有任务插入图片
	 * @param map
	 */
	
	public void insertPicTask(Map map);
	
	/**
	 * 设备插入图片
	 * @param map
	 */
	public void insertPicEqp(Map map);
	
	
	public void insertPicPort(Map map);
	
	
}
