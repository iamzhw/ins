package com.roomInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface RoomDao {

	/**
	 * 查询机房列表
	 */
	List<Map> roomQuery(Query query);

	/**
	 * 根据机房ID查询机房信息
	 * @param roomId 机房ID
	 * @return
	 */
	Map<String,Object> queryRoombyRoomId(int roomId);
	
	/**
	 * 插入机房信息
	 * @param map
	 */
	void insertRoom(Map map);
	
	/**
	 * 更新机房信息
	 * @param map
	 */
	void updateRoom(Map map);
	
	
	/**
	 * 删除机房信息
	 * @param map
	 */
	void deleteRoom(Map map);
	
	/**
	 * 获取所有计划相关的机房类型
	 * 
	 * @return
	 */
	public List<Map<String,Object>> getRoomTypes();
	
}
