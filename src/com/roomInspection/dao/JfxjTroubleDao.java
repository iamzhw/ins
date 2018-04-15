package com.roomInspection.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

/**
 * 巡检工单数据库处理接口
 * 
 * @author huliubing
 * @since 2014-07-23
 *
 */

@SuppressWarnings("all")
public interface JfxjTroubleDao {

	/**
	 * 查询工单列表
	 * @param query 查询条件
	 * @return
	 */
	public List<Map> query(Query query);
	
	/**
	 * 查询工单详细
	 * @param checkDetailId 工单ID
	 * @return
	 */
	public Map queryDetail(int checkDetailId);
	
	/**
	 * 根据工单ID查询关联的图片
	 * @param checkDetailId 工单ID
	 * @return
	 */
	public List<Map> queryPicByDetailId(int checkDetailId);
}
