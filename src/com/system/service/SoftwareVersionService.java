/**
 * FileName: GeneralService.java
 * @Description: TODO(用一句话描述该文件做什么) 
 * All rights Reserved, Designed By ZBITI 
 * Copyright: Copyright(C) 2014-2015
 * Company ZBITI LTD.

 * @author: SongYuanchen
 * @version: V1.0  
 * Createdate: 2014-1-17
 *

 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-1-17      wu.zh         1.0            1.0
 * Why & What is modified: <修改原因描述>
 */
package com.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

/**
 * @ClassName: GeneralService
 * @Description: 通用的方法
 * @author: SongYuanchen
 * @date: 2014-1-17
 * 
 */
public interface SoftwareVersionService {
	/**
	 * @throws Exception
	 * @Title: save
	 * @Description:
	 * @param: @param request
	 * @param: @return
	 * @return: Boolean
	 * @throws
	 */
	public void insert(HttpServletRequest request) throws Exception;
	
	/**
	 * @Title: delete
	 * @Description:
	 * @param: @param request
	 * @return: void
	 * @throws
	 */
	public void delete(HttpServletRequest request);

	public void downLoadAPK(HttpServletRequest request,
			HttpServletResponse response, String softId);

}
