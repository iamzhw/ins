package icom.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 缆线巡检对接智能网管,提供webservice接口
 */
@WebService(serviceName = "AppFlowService")
public interface CableTaskService {

	/**
	 * 对接智能网管,获取业务人员所有待办缆线任务数量
	 * 
	 * @param appFlowRequest
	 *            xml格式请求报文
	 */
	@WebMethod(operationName = "AppFlowCount")
	public String appFlowCount(
			@WebParam(name = "appFlowCountRequest") String appFlowRequest);

	/**
	 * 对接智能网管,获取业务人员所有待办缆线任务列表(支持分页查询)
	 * 
	 * @param appFlowRequest
	 *            xml格式请求报文
	 */
	@WebMethod(operationName = "AppFlowList")
	public String appFlowList(
			@WebParam(name = "appFlowListRequest") String appFlowRequest);
}
