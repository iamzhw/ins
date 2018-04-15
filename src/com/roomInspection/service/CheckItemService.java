package com.roomInspection.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

/**
 * 
 * @ClassName: CheckItemService
 * @Description: 机房巡检检查项业务成接口
 * 
 * @author huliubing
 * @since: 2014-8-15
 *
 */
public interface CheckItemService {
	
	/**
	 * 查询检查项列表
	 * 
	 * @param request 请求消息
	 * @param pager 分页信息
	 * @return
	 */
	Map<String,Object> queryCheckItem(HttpServletRequest request,UIPage pager);
	
	/**
	 * 增加检查项
	 * 
	 * @param request 请求消息
	 */
	void insertCheckItem(HttpServletRequest request);
	
	/**
	 * 修改检查项
	 * 
	 * @param request 请求消息
	 */
	void updateCheckItem(HttpServletRequest request);
	
	/**
	 * 删除检查项
	 * 
	 * @param request 请求消息
	 */
	void deleteCheckItem(HttpServletRequest request);
	
	/**
	 * 根据检查项ID查询检查项详情
	 * 
	 * @param request 请求消息
	 * @return
	 */
	Map<String,Object> queryCheckItemById(HttpServletRequest request);
}
