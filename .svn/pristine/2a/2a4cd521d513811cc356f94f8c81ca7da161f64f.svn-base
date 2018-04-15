package com.webservice.intelligenceNetworkM.serviceimpl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import icom.cableCheck.dao.CheckOrderDao;
import icom.cableCheck.serviceimpl.CheckPortServiceImpl;
import icom.util.JaxbUtil;
import icom.webservice.model.AppFlowCountRequest;
import icom.webservice.model.AppFlowCountResponse;
import icom.webservice.model.CountResult;
import icom.webservice.model.TaskModel;
import icom.webservice.serviceimpl.CableTaskServiceImpl;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.dataSource.SwitchDataSourceUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cableCheck.dao.CableMyTaskDao;
import com.cableCheck.dao.CheckProcessDao;

import com.cableInspection.dao.FtpResolveDao;
import com.cableInspection.model.PointModel;
import com.cableInspection.service.FtpResolveService;
import com.cableInspection.serviceimpl.FtpResolveServiceImpl;
import com.linePatrol.util.DateUtil;
import com.util.FTPUpload;
import com.webservice.intelligenceNetworkM.dao.IntelligenceNetworkMDao;
import com.webservice.intelligenceNetworkM.service.IntelligenceNetworkMService;

@WebService(serviceName = "intelligenceNetworkMService")
public class IntelligenceNetworkMImpl   implements IntelligenceNetworkMService{

	Logger logger = Logger.getLogger(CheckPortServiceImpl.class);
	@Resource
	private IntelligenceNetworkMDao intelligencenetworkmdao;
	
	@Resource
	private CheckOrderDao checkOrderDao;
	
	@Resource
	private CheckProcessDao checkProcessDao;
	
	/**
	 * 日志服务
	 */
	private static final Logger LOGGER = Logger
			.getLogger(CableTaskServiceImpl.class);
	
	
	/**
	 * 查询每个工号的检查代办任务列表
	 */
	@SuppressWarnings("all")
	public JSONArray getAgencyTaskList(HashMap param) {
		JSONArray jsArr = new JSONArray();
		JSONObject portObject = new JSONObject();
		List<Map> agencyTaskList = null;
		try {
			
			/**
			 * 查询每个工号的代办列表
			 */
			 agencyTaskList =intelligencenetworkmdao.getAgencyTaskList(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(agencyTaskList.size()>0 && agencyTaskList != null){ 
				for (Map light : agencyTaskList) {
					    portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
						portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
						portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
						jsArr.add(portObject);
					}
	}
			return jsArr;
	}

	
	/**
	 * 查询每个工号的资源检查整改工单代办任务列表
	 */
	@SuppressWarnings("all")
	public JSONArray getHiddenDangerTaskList(HashMap param){
		JSONArray jsArr = new JSONArray();
		JSONObject portObject = new JSONObject();
		List<Map> agencyTaskList = null;
		try {
			
			/**
			 * 查询每个工号的代办列表
			 */
			 agencyTaskList =intelligencenetworkmdao.getHiddenDangerTaskList(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(agencyTaskList.size()>0 && agencyTaskList != null){
				for (Map light : agencyTaskList) {
					    portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
						portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
						portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
						jsArr.add(portObject);
					}
	}
		return jsArr;
		
	}
	
	/**
	 * 资源检查整改工单归档接
	 */
	
	@SuppressWarnings("all")
	public void saveHiddenDangerList(String jsonStr){
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpId = json.getString("eqpId");// 设备ID			
			String areaName = json.getString("areaName");//地市id	
			
			HashMap param = new HashMap();	
			param.put("eqpNo", eqpNo);
			param.put("eqpId", eqpId);
			
			intelligencenetworkmdao.saveHiddenDangerList(param);
		
		} catch (Exception e) {
			result.put("desc", "资源检查整改工单归档接失败");
			logger.info(e.toString());
		}	
	}
	
	/**
	 * 检查类工单信息传递
	 */
	@SuppressWarnings("all")
	public String sendCheckList(String jsonStr){
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpId = json.getString("eqpId");// 设备ID			
			String areaName = json.getString("areaName");//地市id	
			
			HashMap param = new HashMap();	
			param.put("eqpNo", eqpNo);
			param.put("eqpId", eqpId);
			
		} catch (Exception e) {
			result.put("desc", "查询端子失败");
			logger.info(e.toString());
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
		
	}
	
	/**
	 * 远程检查结果保存
	 */
	@SuppressWarnings("all")
	public String saveRemoteCheck(String jsonStr){
		JSONObject result = new JSONObject();
		result.put("result", "000");
	/*	 jsonStr = "{"+
		"\"eqpNo\":\"CQ.DBT/GF01524\","+
        "\"aeraName\":\"南京\","+
		"\"sonAreaName\":\"鼓楼区\","+
		"\"checkNum\":\"100\","+
	"\"errorNum\":\"30\","+
	"\"checkPersion\":\"张三\","+
		"\"checkTime\":\"2017-05-23 15:43:40\","+
	"\"portId\":\"100000\","+
		"\"portNo\":\"1/2\","+
		"\"errorDetail\":\"现场有纤，系统无纤\","+
		"\"is_check_ok\":\"0\""+
		"}";*/

		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			JSONArray innerArray =json.getJSONArray("param");
			
			if(innerArray.size()>0 && innerArray != null){
				for (int j = 0; j < innerArray.size(); j++) {
					JSONObject jsons = (JSONObject) innerArray.get(j);
					String eqpNo = jsons.getString("eqpNo");// 设备编码 
					String aeraName = jsons.getString("aeraName");// 地市			
					String sonAreaName = jsons.getString("sonAreaName");// 区域			
					String checkNum = jsons.getString("checkNum");// 检查数		
				//	int checkNum=Integer.parseInt(checkNums);
					String errorNum = jsons.getString("errorNum");// 错误数		
			//		int errorNum=Integer.parseInt(errorNums);
					String checkPersion = jsons.getString("checkPersion");// 检查人			
					String checkTime = jsons.getString("checkTime");// 检查时间			
					String portId = jsons.getString("portId");// 端子id			
				//	int portId=Integer.parseInt(portIds);		
					String portNo = jsons.getString("portNo");//端子编码
					String errorDetail = jsons.getString("errorDetail");//	错误描述
					String is_check_ok = jsons.getString("is_check_ok");//	是否合格，0不合格
					HashMap param = new HashMap();	
					param.put("eqpNo", eqpNo);
					param.put("aeraName", aeraName);
					param.put("sonAreaName", sonAreaName);
					param.put("checkNum", checkNum);
					param.put("errorNum", errorNum);
					param.put("checkPersion", checkPersion);
					param.put("checkTime", checkTime);
					param.put("portId", portId);
					param.put("portNo", portNo);
					param.put("errorDetail", errorDetail);
					param.put("is_check_ok", is_check_ok);
					
					intelligencenetworkmdao.saveRemoteCheck(param);
					result.put("msg", "接口调用成功");
					result.put("code", "001");
					}
			}
			
		/*	String eqpNo = json.getString("eqpNo");// 设备编码 
			String aeraName = json.getString("aeraName");// 地市			
			String sonAreaName = json.getString("sonAreaName");// 区域			
			String checkNum = json.getString("checkNum");// 检查数		
		//	int checkNum=Integer.parseInt(checkNums);
			String errorNum = json.getString("errorNum");// 错误数		
	//		int errorNum=Integer.parseInt(errorNums);
			String checkPersion = json.getString("checkPersion");// 检查人			
			String checkTime = json.getString("checkTime");// 检查时间			
			String portId = json.getString("portId");// 端子id			
		//	int portId=Integer.parseInt(portIds);		
			String portNo = json.getString("portNo");//端子编码
			String errorDetail = json.getString("errorDetail");//	错误描述
			String is_check_ok = json.getString("is_check_ok");//	是否合格，0不合格
	//		int is_check_ok=Integer.parseInt(is_check_oks);
			
			HashMap param = new HashMap();	
			param.put("eqpNo", eqpNo);
			param.put("aeraName", aeraName);
			param.put("sonAreaName", sonAreaName);
			param.put("checkNum", checkNum);
			param.put("errorNum", errorNum);
			param.put("checkPersion", checkPersion);
			param.put("checkTime", checkTime);
			param.put("portId", portId);
			param.put("portNo", portNo);
			param.put("errorDetail", errorDetail);
			param.put("is_check_ok", is_check_ok);
			
			intelligencenetworkmdao.saveRemoteCheck(param);
			result.put("msg", "接口调用成功");
			result.put("code", "001");*/
		} catch (Exception e) {
			result.put("msg", "接口调用失败");
			result.put("code", "002");
			logger.info(e.toString());
		}
		return result.toString();	
	}
	
	
	/**
	 * 查询工单详细信息
	 */
	@SuppressWarnings("all")
	public String selectWorkOrderList(String jsonStr){
		JSONObject result = new JSONObject();
		List<Map> TaskList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数 
			 */
			
			String task_no = json.getString("task_no");// 工单号
			
			HashMap param = new HashMap();	
			param.put("task_no", task_no);
			
			try {
			TaskList=intelligencenetworkmdao.selectWorkOrderList(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
					result.put("workOrder_no", String.valueOf(light.get("task_no")).trim());//工单号
					result.put("workOrder_type", String.valueOf(light.get("task_type")).trim());//工单类型
					result.put("sn", String.valueOf(light.get("sn")).trim());//sn号
					result.put("glbh", String.valueOf(light.get("glbh")).trim());//光路编码
					result.put("configurer", String.valueOf(light.get("configurer")).trim());//配置人
					result.put("revisionNum", String.valueOf(light.get("revisionNum")).trim());//调整工号
					result.put("constructionHill", String.valueOf(light.get("constructionHill")).trim());//施工岗
					result.put("modifyFiberSponsor", String.valueOf(light.get("modifyFiberSponsor")).trim());//更纤发起人
					result.put("revisionAction", String.valueOf(light.get("revisionAction")).trim());//调整动作
					result.put("glOperator", String.valueOf(light.get("glOperator")).trim());//光路调整操作人
					result.put("operateType", String.valueOf(light.get("operateType")).trim());//操作状态
					result.put("userName", String.valueOf(light.get("userName")).trim());//用户名
					result.put("installedAddress", String.valueOf(light.get("installedAddress")).trim());//装机地址
					result.put("glly", String.valueOf(light.get("glly")).trim());//光路路由
					result.put("glmc", String.valueOf(light.get("glmc")).trim());//光路名称
					result.put("completedTime", String.valueOf(light.get("completedTime")).trim());//竣工时间
					result.put("modifyTime", String.valueOf(light.get("modifyTime")).trim());//修改时间
					}
			}
		} catch (Exception e) {
			result.put("desc", "查询工单详细信息失败");
			logger.info(e.toString());
		}
		return result.toString();
		
	}
	
	
	/**
	 * @param args
	 * 智能网管查询代办任务
	 */
/*	
	@SuppressWarnings("all")
	public String selectAgencyTask(String appFlowRequest) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		List<Map> agencyTaskListCount = null;
		result.put("result", "000");
		try {*/
			
		//	JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			
			/*AppFlowCountResponse appFlowCountResponse = new AppFlowCountResponse();*/
			// 默认接口处理成功
			/*appFlowCountResponse.setIfResult(0);
			appFlowCountResponse.setIfResultInfo("处理成功");*/

			// 处理结果
			/*CountResult countResult = new CountResult();
			countResult.setType(3);
			countResult.setCnt(0);

			appFlowCountResponse.setCountResult(countResult);*/

			// 待办任务数量
			/*int taskSize = 0;
*/
			// 接口返回报文
			/*String appFlowCountResponseXml = null;

			try {*/

				// 将xml报文转换为请求实体
				/*AppFlowCountRequest appFlowCountRequest = JaxbUtil.convertToObject(
						appFlowRequest, AppFlowCountRequest.class);

				

				if (null == appFlowCountRequest) {
					appFlowCountResponse.setIfResultInfo("参数解析错误!");
					appFlowCountResponseXml = JaxbUtil.convertToXml(
							appFlowCountResponse, "GB2312");
				
					return appFlowCountResponseXml;
				}*/

				// 用户账号
				/*String staffNo = appFlowCountRequest.getStaff_no();
				String idCard = appFlowCountRequest.getIdCard();*/
			
			
		//	String idCard =  null;// 身份证号码			
		//	String START_TIME = json.getString("start_time");// 开始时间			
		//	String COMPLETE_TIME = json.getString("complete_time");// 结束时间			
		//	 staff_No="ning11";
		//	 idCard="340826199304023612"; 
			/*HashMap param = new HashMap();	*/
			
			
	//		param.put("START_TIME", START_TIME);
	//		param.put("COMPLETE_TIME", COMPLETE_TIME);
			
			/*if(idCard !=null && idCard!=""){
				param.put("idCard", idCard);
				List<Map>    staffId= intelligencenetworkmdao.getstaff_id(param);	
				if(staffId.size()>0 && staffId != null){
					for (Map light : staffId) {
						param.put("staff_id", String.valueOf(light.get("staff_id")).trim());
						}
					}*/
				/**
				 * 查询工号的代办任务数量
				 */
				/*try {
					agencyTaskListCount=intelligencenetworkmdao.getAgencyTaskListCount(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(agencyTaskListCount.size()>0 && agencyTaskListCount != null){
					for (Map light : agencyTaskListCount) {
						result.put("checkTaskNum", String.valueOf(light.get("checkTaskNum")).trim());
						jsArr.add(result);
						}
					}
			}else{
				param.put("staff_No", staffNo);
				List<Map>    staffId= intelligencenetworkmdao.getstaff_id(param);	
				if(staffId.size()>0 && staffId != null){
					for (Map light : staffId) {
						param.put("staff_id", String.valueOf(light.get("staff_id")).trim());
						}
					}*/
				/**
				 * 查询工号的代办任务数量
				 */
				/*try {
					agencyTaskListCount=intelligencenetworkmdao.getAgencyTaskListCount(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(agencyTaskListCount.size()>0 && agencyTaskListCount != null){
					for (Map light : agencyTaskListCount) {
						result.put("checkTaskNum", String.valueOf(light.get("checkTaskNum")).trim());
						appFlowCountResponse.setCountResult(countResult);
						return appFlowCountResponseXml;
						}
					}
				
			}
		} catch (Exception e) {
			result.put("msg", "调用接口失败");
			logger.info(e.toString());
		}
		return appFlowCountResponseXml;
	}*/
		
		
		
	/**
	 * @param args
	 * 获取光路施工人接口
	 */
	
	@SuppressWarnings("all")
	public String getLightPathPerson(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		List<Map> LightPathPersonList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			
			String equName = json.getString("equName");// 设备名称
			String equCode = json.getString("equCode");// 设备编码			
			String errorPortcode = json.getString("errorPortcode");// 错误端子编码			
			String lightNo = json.getString("lightNo");// 光路编码	
			lightNo="ning11";
			HashMap param = new HashMap();	
			param.put("lightNo", lightNo);
			
			result.put("equName", equName);
			result.put("equCode", equCode);
			try {
			LightPathPersonList=intelligencenetworkmdao.getLightPathPerson(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(LightPathPersonList.size()>0 && LightPathPersonList != null){
				for (Map light : LightPathPersonList) {
					result.put("dealManNo", String.valueOf(light.get("REPLY_PTY_NBR")).trim());//施工人工号
					result.put("dealManStaffName", String.valueOf(light.get("REPLY_PTY_NM")).trim());//施工人
					result.put("dealManStaffNo", String.valueOf(light.get("REPLY_ACCT_NO")).trim());//施工人账号
					jsArr.add(result);
					}
				}
		} catch (Exception e) {
			result.put("msg", "调用接口失败");
			logger.info(e.toString());
		}
		return jsArr.toString();
	}
	
	
	/**
	 * @param args
	 * 获取网格设备所属工单
	 */
	
	@SuppressWarnings("all")
	public String getEquWorkOrder(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		List<Map> LightPathPersonList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			
			String equName = json.getString("equName");// 设备名称
			String equCode = json.getString("equCode");// 设备编码			
			
			HashMap param = new HashMap();	
			
			result.put("equName", equName);
			result.put("equCode", equCode);
			try {
			LightPathPersonList=intelligencenetworkmdao.getEquWorkOrder(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(LightPathPersonList.size()>0 && LightPathPersonList != null){
				for (Map light : LightPathPersonList) {
					result.put("IOM_ORDER_NO", String.valueOf(light.get("IOM_ORDER_NO")).trim());//订单编号	
					result.put("gltzdz", String.valueOf(light.get("gltzdz")).trim());//光路调整动作
					result.put("gltzr", String.valueOf(light.get("gltzr")).trim());//光路调整操作人
					result.put("czzt", String.valueOf(light.get("czzt")).trim());//操作状态
					result.put("zyzy", String.valueOf(light.get("zyzy")).trim());//占用资源信息
					result.put("IOM_MF_PTY_NBR", String.valueOf(light.get("IOM_MF_PTY_NBR")).trim());//光路IOM更纤人工号
					result.put("IOM_MF_PTY_NM", String.valueOf(light.get("IOM_MF_PTY_NM")).trim());//光路IOM更纤人
					jsArr.add(result);
					}
				}
		} catch (Exception e) {
			result.put("msg", "调用接口失败");
			logger.info(e.toString());
		}
		return jsArr.toString();
	}
	
	/**
	 * 打印Info级别日志
	 * 
	 * @param message
	 *            日志内容
	 */
	private void InfoLogger(String message) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(message);
		}
	}


	@Override
	public String selectAgencyTask(String appFlowRequest) {
		List<Map> agencyTaskListCount = null;
		AppFlowCountResponse appFlowCountResponse = new AppFlowCountResponse();
		// 默认接口处理成功
		appFlowCountResponse.setIfResult(0);
		appFlowCountResponse.setIfResultInfo("处理成功");

		// 处理结果
		CountResult countResult = new CountResult();
	//	countResult.setType(3);
	//	countResult.setCnt(0);

		appFlowCountResponse.setCountResult(countResult);

		// 待办任务数量
		int taskSize = 0;

		// 接口返回报文
		String appFlowCountResponseXml = null;

		try {

			// 将xml报文转换为请求实体
			AppFlowCountRequest appFlowCountRequest = JaxbUtil.convertToObject(
					appFlowRequest, AppFlowCountRequest.class);

		

			if (null == appFlowCountRequest) {
				appFlowCountResponse.setIfResultInfo("参数解析错误!");

				
				return appFlowCountResponseXml;
			}

			// 用户账号
			String staffNo = appFlowCountRequest.getStaff_no();
			// 身份证
			String idCard = appFlowCountRequest.getIdCard();
			
			HashMap param = new HashMap();	
			HashMap params = new HashMap();	
			HashMap res = new HashMap();
		//	param.put("staffNo", staffNo);
		//	param.put("idCard", idCard);
			
			if(idCard !=null && idCard!=""){
				param.put("idCard", idCard);
				List<Map>    staffId= intelligencenetworkmdao.getstaff_id(param);	
				if(staffId.size()>0 && staffId != null){
					for (Map light : staffId) {
						param.put("staff_id", light.get("STAFF_ID"));
						agencyTaskListCount=intelligencenetworkmdao.getAgencyTaskListCount(param);
						if (CollectionUtils.isEmpty(agencyTaskListCount)) {
							appFlowCountResponse.setIfResultInfo("数据为空!");

							
							return appFlowCountResponseXml;
						}else{
								for (Map lights : agencyTaskListCount) {
//									countResult.setCheckTaskNum(Integer.parseInt(lights.get("CHECKTASKNUM").toString()));
								//	appFlowCountResponseXml = JaxbUtil.convertToXml(appFlowCountResponse, "GB2312");
								//	appFlowCountResponseXml = "[countResult="+countResult+", ifResult="+"ifResult"+", ifResultInfo="+"ifResultInfo"+"]";
									appFlowCountResponseXml = "<task>"
									+"<IfResult Note=\"接口处理结果\">0</IfResult>"+
									"<IfResultInfo Note=\"接口返回信息\">处理成功"+"</IfResultInfo>"+
									"<data>"+
									"<checkTaskNum>"+lights.get("CHECKTASKNUM")+"</checkTaskNum>"+
									"</data>"+
									"</task>";
									return appFlowCountResponseXml;
									}
								
						}
						}
					}else{
						params.put("staff_No", staffNo);
						List<Map>    staffIds= intelligencenetworkmdao.getstaff_id(params);	
						if(staffIds.size()>0 && staffIds != null){
							for (Map light : staffIds) {
								res.put("staff_id", light.get("STAFF_ID"));
								agencyTaskListCount=intelligencenetworkmdao.getAgencyTaskListCount(res);
								
								if (CollectionUtils.isEmpty(agencyTaskListCount)) {
									appFlowCountResponse.setIfResultInfo("数据为空!");

									
									return appFlowCountResponseXml;
								}else{
									for (Map lights : agencyTaskListCount) {
//										countResult.setCheckTaskNum(Integer.parseInt(lights.get("CHECKTASKNUM").toString()));
									//	appFlowCountResponseXml = JaxbUtil.convertToXml(appFlowCountResponse, "GB2312");
									//	appFlowCountResponseXml = "[countResult="+countResult+", ifResult="+"0"+", ifResultInfo="+"处理成功"+"]";
										appFlowCountResponseXml = "<task>"
										+"<IfResult Note=\"接口处理结果\">0</IfResult>"+
										"<IfResultInfo Note=\"接口返回信息\">处理成功"+"</IfResultInfo>"+
										"<data>"+
										"<checkTaskNum>"+lights.get("CHECKTASKNUM")+"</checkTaskNum>"+
										"</data>"+
										"</task>";
										return appFlowCountResponseXml;
										}
									
							}
								}
							}
						
						}
					}

				
			

			

			

		/*//	countResult.setCnt(taskSize);
			appFlowCountResponseXml = JaxbUtil.convertToXml(
					appFlowCountResponse, "GB2312");
			return appFlowCountResponseXml;*/

		} 
	
			catch (Exception e) {

			LOGGER.error(
					"CableTaskServiceImpl.AppFlowCount(). got exception. ", e);
			appFlowCountResponse.setIfResult(1);
			appFlowCountResponse.setIfResultInfo("其他错误!");

			return appFlowCountResponseXml;
		}
		return appFlowCountResponseXml;

	}
	
	
	/**
	 * @param args
	 * 整治工单回单操作接口
	 */
	
	@SuppressWarnings("all")
	public String remediationBackOder(String xmlStr) {
		System.out.println(DateUtil.getDateAndTime()+":入参信息"+xmlStr);
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		List<Map> BackOder = null;
		
		Document doc = null;
		String responseMsg = "";
		try
		{
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			Element task_id = rootElt.element("task_id");
			String task_idinfo=	task_id.getText();
			Element workManAccount = rootElt.element("workManAccount");
			String workManAccountinfo=	workManAccount.getText();
			
			Element workMan = rootElt.element("workMan");
			String workManinfo=	workMan.getText();
			
			Element content = rootElt.element("content");
			String contentinfo=	content.getText();
			
			Element createDate = rootElt.element("createDate");
			String createDateinfo=	createDate.getText();
	
			Element photoUrlForInter = rootElt.element("photoUrlForInter");
			String photoUrlForInterinfo=	photoUrlForInter.getText();
			
			Element workManUserId = rootElt.element("workManUserId");
			String workManUserIdinfo=	workManUserId.getText();
			
	/*	String [] strArray=null;
			FTPUpload t= new FTPUpload();
			strArray=photoUrlForInterinfo.split(",");
			if(strArray.length>0){
				for (int i = 0; i < strArray.length; i++) {
					if(i==0){
						String pho=strArray[0];
						//调用图片服务器接口
						File file= new File(pho);
						t.upload(file);
					}else{
						String pho="/work/ftpfile/edw/"+strArray[i];
						//调用图片服务器接口
						File file= new File(pho);
						t.upload(file);
					}
					
				}
			}*/
			
		
	//	String[] 	photoUrlForInterlsit=photoUrlForInterinfo.split(",");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
			
			/*JSONObject json = JSONObject.fromObject(jsonStr);
			*//**
			 * 接收的参数
			 *//*
			
			String taskId = json.getString("task_id");// 	线路工单ID		
			String workManAccount = json.getString("workManAccount");// 	整改人工号		
			String workMan = json.getString("workMan");// 整改人姓名			
			String content = json.getString("content");// 	整改备注		
			String createDate = json.getString("createDate");// 	整改时间		
			String photoUrlForInter = json.getString("photoUrlForInter");// 	整改后照片(内网)		
			String photoUrlForOuter = json.getString("photoUrlForOuter");// 	整改后照片(外网网)		
			*/
			HashMap param = new HashMap();	
			
		//	result.put("taskId", task_idinfo);
			
			Map map = new HashMap();
			map.put("task_id", task_idinfo);
			String statusIdStr = checkOrderDao.queryTaskByTaskId(map).get("STATUS_ID").toString();
			int curStatusId = Integer.valueOf(statusIdStr);
			if(curStatusId==7){
				result.put("result", "001");
				result.put("desc", "已回单");
			}else{
				param.put("taskId", task_idinfo);
				param.put("workManAccount", workManAccountinfo);
				param.put("workMan", workManinfo);
				param.put("content", contentinfo);
				param.put("createDate", createDateinfo);
				param.put("photoUrlForInter", photoUrlForInterinfo);
				param.put("photoUrlForOuter", photoUrlForInterinfo);
				
				
				  //插入流程环节
				param.put("task_id", task_idinfo);
				param.put("remark", "回单，待审核");
				param.put("status", "7");
				param.put("oper_staff", workManUserIdinfo);
				checkProcessDao.addProcess(param);
				
				
				//更新任务表
				Map taskMap = new HashMap();
				taskMap.put("staffId", workManUserIdinfo);
				taskMap.put("statusId", "7");
				taskMap.put("task_id", task_idinfo);
				checkOrderDao.updateTaskBack(taskMap);
				checkOrderDao.updateLastUpdateTime(taskMap);
				
			}
			result.put("result", "000");
			result.put("desc", "已回单");
		
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("msg", "调用接口失败"+e.toString());
			logger.info(e.toString());
		}
		return result.toString();
	}


	@Override
	public String Chargeback(String xmlStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			Document document=DocumentHelper.parseText(xmlStr);
			Element rootElt = document.getRootElement(); // 获取根节点
			Element task_id = rootElt.element("task_id");
			String taskId=	task_id.getText();
			//退单接口，将任务状态从6变为5，没有维护员和审核员
			intelligencenetworkmdao.chargeback(taskId);
			result.put("desc", "退单成功");			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "退单失败");
		}
		return result.toString();
	}
	
}
