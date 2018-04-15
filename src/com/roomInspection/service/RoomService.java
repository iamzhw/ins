package com.roomInspection.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import util.page.UIPage;

@SuppressWarnings("all")
public interface RoomService {

	/**
	 * 查询机房列表
	 * @param request
	 * @param pager
	 * @return
	 */
	Map<String, Object> roomQuery(HttpServletRequest request, UIPage pager);

	/**
	 *  增加机房信息
	 * @param request
	 */
	void addRoom(HttpServletRequest request);
	
	/**
	 * 根据机房ID查询机房信息
	 * @param roomId 机房ID
	 * @return
	 */
	Map<String,Object> queryRoombyRoomId(HttpServletRequest request);
	
	
	/**
	 * 更新机房信息
	 * @param map
	 */
	void updateRoom(HttpServletRequest request);
	
	
	/**
	 * 删除机房信息
	 * @param map
	 */
	void deleteRoom(HttpServletRequest request);
	
	JSONObject importRoom(HttpServletRequest request, MultipartFile file);

}
