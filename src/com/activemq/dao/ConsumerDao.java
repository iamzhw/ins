package com.activemq.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * JMS消息中间件
 * 消费者Dao层
 * @author wangxiangyu
 *
 */
@Repository("ConsumerDao")
public interface ConsumerDao {
	/**
	 * 保存消息到数据库
	 * @param map
	 */
	public void saveMessage(Map<String, String> map);
	/**
	 * 查询帐号是否已存在
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> isExistByIdcard(Map<String, String> map);
	/**
	 * 新增帐号
	 * @param map
	 */
	public void saveStaff(Map<String, String> map);
	/**
	 * 更新帐号
	 * @param map
	 */
	public void updateStaff(Map<String, String> map);
	/**
	 * 根据统一工号city编号查area_id
	 * @param map
	 * @return
	 */
	public Map<String, String> queryAreaByCity(String city);
}
