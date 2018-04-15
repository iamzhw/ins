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
package icom.system.service;

import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName: GeneralService
 * @Description: 通用的方法
 * @author: SongYuanchen
 * @date: 2014-1-17
 * 
 */
public interface InitService {
	public String getAppInfo(String json);

	public String login(String json, HttpServletRequest request);

	public String getUrl(String json);

	public String changePwd(String json);

	public String feedbackAdvice(String json);
	
	public String getSjxjUrl(HttpServletRequest request);
	
	/**
	 * 用户注销
	 * 
	 * @param jsonStr
	 * @param request
	 * 
	 * @return
	 */
	public String logoutUser(String jsonStr,HttpServletRequest request);
	
	/**
	 * 获取在线用户
	 * 
	 * @param json
	 * @return
	 */
	public String getLoginUser(String jsonStr);

	/**
	 * 
	 * @Function: icom.system.service.InitService.singleLogin
	 * @Description:单点登录
	 *
	 * @param json
	 * @param request
	 * @return
	 *
	 * @date:2016-1-11 上午10:17:00
	 *
	 * @Modification History:
	 * @date:2016-1-11     @author:Administrator     create
	 */
	String singleLogin(String json, HttpServletRequest request);

}
