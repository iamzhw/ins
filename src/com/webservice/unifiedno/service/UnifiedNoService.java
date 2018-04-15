package com.webservice.unifiedno.service; 

import javax.jws.WebMethod;
import javax.jws.WebService;

/** 
 * @author wangxiangyu
 * @date：2017年9月27日 下午3:53:08 
 * 类说明：统一工号管理接口
 */
@SuppressWarnings("all")
@WebService(serviceName = "unifiedNoService")
public interface UnifiedNoService {
	
	/**
	 * 账号操作接口
	 * @param jsonStr
	 * @return
	 */
	@WebMethod(operationName = "operateAccount")
	public String operateAccount(String jsonStr);

}
