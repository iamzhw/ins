package com.webservice.intelligenceNetworkM.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "intelligenceNetworkMService")
public interface IntelligenceNetworkMService {

//	@WebMethod(operationName = "getAgencyTaskList")
//	public String getAgencyTaskList( String jsonStr);
	
	@WebMethod(operationName = "saveRemoteCheck")
	public String saveRemoteCheck( String jsonStr);
	
	//查询工单详细信息selectAgencyTask
	@WebMethod(operationName = "selectWorkOrderList")
	public String selectWorkOrderList( String jsonStr);
	
	/*//查询代办任务列表
	@WebMethod(operationName = "selectAgencyTask")
	public String selectAgencyTask( String jsonStr);*/
	
	@WebMethod(operationName = "selectAgencyTask")
	public String selectAgencyTask(
			@WebParam(name = "appFlowCountRequest") String appFlowRequest);
	
	//获取光路施工人接口
	@WebMethod(operationName = "getLightPathPerson")
	public String getLightPathPerson( String jsonStr);
	
	//获取网格设备所属工单
	@WebMethod(operationName = "getEquWorkOrder")
	public String getEquWorkOrder( String jsonStr);
	
	//整治工单回单操作接口
	@WebMethod(operationName = "remediationBackOder")
	public String remediationBackOder( String jsonStr);
	
	//整治单退单接口
	@WebMethod(operationName = "Chargeback")
	public String Chargeback( String jsonStr);
	
}
