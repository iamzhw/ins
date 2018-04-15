package icom.cableCheck.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import icom.cableCheck.dao.CheckMyworkDao;
import icom.cableCheck.dao.CheckOrderDao;
import icom.cableCheck.dao.CheckTaskDao;
import icom.cableCheck.service.CheckOrderService;
import icom.system.dao.CableInterfaceDao;
import icom.util.Result;
import icom.webservice.client.WfworkitemflowLocator;
import icom.webservice.client.WfworkitemflowSoap11BindingStub;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import oracle.net.aso.i;
import oracle.net.aso.p;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import util.dataSource.SwitchDataSourceUtil;

import com.cableCheck.dao.CableMyTaskDao;
import com.cableCheck.dao.CheckProcessDao;
import com.ctc.wstx.util.StringUtil;
import com.linePatrol.util.DateUtil;
/**
 * 生成工单接口
 * 
 */
@Service
@SuppressWarnings("all")
public class CheckOrderServiceImpl implements CheckOrderService {

	Logger logger = Logger.getLogger(CheckOrderServiceImpl.class);
	@Resource
	private CheckOrderDao checkOrderDao;
	@Resource
	private CheckProcessDao checkProcessDao;
	@Resource
	private CheckMyworkDao checkMyworkDao;
	@Resource
	private CheckTaskDao checkTaskDao;
	@Resource
	private CableMyTaskDao cableMyTaskDao;
	@Resource
	private CableInterfaceDao cableInterfaceDao;

	@Override
	public String getOrder(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		String taskid="";
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			
//			String queryType = json.getString("queryType");// 查询类型
//			if (!OnlineUserListener.isLogin(staffId, sn)) {
//				result.put("result", "002");
//				result.put("photoId", "");
//				return result.toString();
//			}
			/**
			 * 传入的参数
			 */
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");
			String taskId = json.getString("taskId");
			String rwmxId = null==json.get("rwmxId")?"":json.getString("rwmxId");
			String eqpId = null==json.get("eqpId")?"":json.getString("eqpId");//设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");//设备名称
			String eqpAddress = null==json.get("eqpAddress")?"":json.getString("eqpAddress");//设备地址（临时上报隐患时，必填）
			String eqpPhotoIds = json.getString("photoId");//设备照片
			String operType = json.getString("operType");//0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报 4：二次检查

			String remarks = json.getString("remarks");//现场规范
			String info = null==json.get("info")?"":json.getString("info");//备注
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String comments = null==json.get("comments")?"":json.getString("comments");//评价
			String is_bill = json.getString("is_bill");//是否需要整改
			String data_source = json.getString("data_source");//数据来源
			String allcount = json.getString("allcount");// 全部端子数
			//String addressCheckCnt = json.getString("addressCheckCnt");// 覆盖地址检查数量
			JSONArray addressArray=null;
			int addressCheckCnt=0;
			if(json.containsKey("allEqpAddress")){
				addressArray =json.getJSONArray("allEqpAddress");
				addressCheckCnt=addressArray.size();
			} 
			String equType="";
			if(json.containsKey("eqpType")){
				equType =  json.getString("eqpType");
			}
			String PositionPersonsList="";//光网助手匹配兜底岗相关人员
			
			if(equType.equals("光交接箱")){
				equType="1";
			}else if(equType.equals("ODF")){
				equType="2";
			} else if(equType.equals("光分纤箱")){
				equType="3";
			}
			
			List<Map<String ,Object>> glbmMap=null;;
			Map portIdMap=new HashMap();
			String portIdList = "";
			
			
			/**
			 * 端子信息
			 */
			JSONArray innerArray =json.getJSONArray("port");
			/*for (int i = 0; i < innerArray.size(); i++) {
				
			}*/
			String id_numberList=""; //光网检查人匹配施工人员账号
			if(innerArray.size()>0 && innerArray != null){
				for (int j = 0; j < innerArray.size(); j++) {
					JSONObject port = (JSONObject) innerArray.get(j);
					String portId =  null== port.getString("portId")?"":port.getString("portId");
					String glbm = null==port.get("glbm")?"":port.getString("glbm");//光路编码
					portIdMap.put("portId", portId);
					portIdList+=portId+",";
					if(null!=glbm || ""!=glbm){
						glbmMap=checkOrderDao.queryGlbm(glbm);						
						if(null!=glbmMap){
							for(Map glbmMap2:glbmMap){
								String id_number=glbmMap2.get("ID_NUMBER").toString();
								if(null !=id_number ||""!=id_number){
									id_numberList+=id_number+",";
								}else{
									id_numberList+="";
								}
							}							
						}else{
							id_numberList="";
						}												
					}
				}
			}
			
			if(id_numberList.length()>2){
				id_numberList = id_numberList.substring(0,id_numberList.length()-1);
			}else{
				id_numberList=id_numberList;
			}
			
			if(portIdList.length()>2){
				portIdList = portIdList.substring(0,portIdList.length()-1);
			}else{
				portIdList=null;
			}
			
			String  sysRoute="GWZS";
			String taskType="5";
			String taskTypes="6";
			String xml = null;
			String xmls = null;
			String results = "";
			String resultss = "";
			String contract_persion_nos="";
			Map resmap=new HashMap();
			
			//根据staffid获取人员姓名和账号
		Map	maps=checkOrderDao.queryByStaffId(staffId);
		String staffName=maps.get("STAFF_NAME").toString();
		String staffNo=maps.get("STAFF_NO").toString();
			/*String staffName="1";
			String staffNo="2";*/
		
		
		//整治对应设备承包人员账号
		Map equCbAccountMap=new HashMap();
		equCbAccountMap=checkOrderDao.equCbAccount(eqpNo);
	    if(null !=equCbAccountMap){
	    	for (int i = 0; i < equCbAccountMap.size(); i++) {
	    		String contract_persion_no=	equCbAccountMap.get("CONTRACT_PERSION_NO").toString();
	    		contract_persion_nos+=contract_persion_nos+",";
			}
	    }else{
	    	contract_persion_nos =contract_persion_nos ;
	    }
	    if(contract_persion_nos.length()>2){
	    	  contract_persion_nos = contract_persion_nos.substring(0,contract_persion_nos.length()-1);
	    }else{
	    	contract_persion_nos=contract_persion_nos;
	    }
	  
			Map oedMap=new HashMap();
			oedMap.put("eqpId", eqpId);
			oedMap.put("eqpNo", eqpNo);
			String eqpMark="";
			if(json.containsKey("eqpMark")){
				eqpMark = json.getString("eqpMark");//箱子设备标识   80000302:接入层    81538172:网络层
			}						
		//	String eqpMark=json.getString("eqpMark");
			if("接入层".equals(eqpMark)){
				eqpMark="80000302";		
				oedMap.put("eqpMark", eqpMark);	
				checkOrderDao.insertEqpMark(oedMap);//修改设备标识
			}else if("网络层".equals(eqpMark)){
				eqpMark="81538172";	
				oedMap.put("eqpMark", eqpMark);
				checkOrderDao.insertEqpMark(oedMap);//修改设备标识
			}			
			String sblx="";//用来判断提交时是分光器还是其他类型
			String eqp_id="";
			String eqp_no="";
			
			/**
			 * 获取当前登录人员的区域信息
			 */
			Map areaMap = checkOrderDao.queryAreaByStaffId(staffId);
			String sonAreaId = null == areaMap.get("SON_AREA_ID")? "" : areaMap.get("SON_AREA_ID").toString();
			String areaId = null ==  areaMap.get("AREA_ID")? "" : areaMap.get("AREA_ID").toString();
			
			Map ddgMap = checkOrderDao.queryDdj(eqpNo);
			String ddg =null;
			if(ddgMap!=null){
				String GRID_ID =ddgMap.get("GRID_ID").toString();
				ddg=GRID_ID;
			}else{
				String GRID_ID =null;
				ddg=GRID_ID;
			}
		//	String ddg = null ==  ddgMap.get("GRID_ID")? "" : ddgMap.get("GRID_ID").toString();
			//光网助手匹配兜底岗相关人员
			Map PositionPersonsMap=new HashMap();
			if(null!=ddg){
		List	PositionPersonslsit=checkOrderDao.PositionPersons(ddg);
			if(null !=PositionPersonslsit || PositionPersonslsit.size()>0){
				for (int i = 0; i < PositionPersonslsit.size(); i++) {
					String id_number= PositionPersonslsit.get(i).toString();
					if(null !=id_number ||""!=id_number){
						PositionPersonsList+=id_number+",";
					}else{
						PositionPersonsList+="";
					}
				}
			}else{
				PositionPersonsList="";
			}
		
			}else{
				PositionPersonsList="";
			}
			if(PositionPersonsList.length()>2){
				PositionPersonsList = PositionPersonsList.substring(0,PositionPersonsList.length()-1);
			}else{
				PositionPersonsList=PositionPersonsList;
			}
			
			/**
			 * 根据设备获取区域信息
			 */
			Map eqpId_eqpNo_map=new HashMap();
			eqpId_eqpNo_map.put("eqpId", eqpId);
			eqpId_eqpNo_map.put("eqpNo", eqpNo);
			Map eqpareaMap = checkOrderDao.queryAreaByeqpId(eqpId_eqpNo_map);
			String parentAreaId = null == eqpareaMap.get("PARENT_AREA_ID")? "" : eqpareaMap.get("PARENT_AREA_ID").toString();
			String eqpAreaId = null ==  eqpareaMap.get("AREA_ID")? "" : eqpareaMap.get("AREA_ID").toString();
			/**
			 * 根据设备id获取设备所在的经纬度--从gis中同步过来的
			 */
			String eqpLongitude = null == eqpareaMap.get("LONGITUDE")? "" : eqpareaMap.get("LONGITUDE").toString();
			String eqpLatitude = null == eqpareaMap.get("LATITUDE")? "" : eqpareaMap.get("LATITUDE").toString();
			/**
			 * 根据设备id获取设备所在的经纬度--检查人员采集的
			 */
			String longitude_inspect = null == eqpareaMap.get("LONGITUDE_INSPECT")? "" : eqpareaMap.get("LONGITUDE_INSPECT").toString();
			String latitude_inspect = null == eqpareaMap.get("LATITUDE_INSPECT")? "" : eqpareaMap.get("LATITUDE_INSPECT").toString();
			boolean flag=true;//如果没有采集坐标，就用gis中同步过来的经纬度进行距离计算，否则用检查人员采集过来的经纬度计算
			
			/**
			 * 端子信息
			 */
//			JSONArray innerArray =json.getJSONArray("port");
			
			if(!"2".equals(operType)&&!"6".equals(operType)&&!"7".equals(operType)&&!"8".equals(operType)){			
				//一开始将检查人的当前位置与设备表中的设备经纬度计算，如果检查或整改设备与人的距离超过300m，则不允许提交工单，flag默认为true,如果超过300m,flag改为false
				if("".equals(longitude_inspect)||"".equals(latitude_inspect)){
					flag=getPermission(eqpLongitude,eqpLatitude,longitude,latitude);
				}else{
					flag=getPermission(longitude_inspect,latitude_inspect,longitude,latitude);
				}
				 
				//一旦不允许提交工单后，让检察人员重新获取设备的经纬度
				if(!flag){
					result.put("result", "112");
					result.put("desc", "生成工单失败，请采集设备坐标。");
				}
			    if(flag){
					
					/**
					 * TODO
					 * 0、周期任务巡检拍照上传(我的检查任务-->检查任务签到)
					 */
					if("0".equals(operType)||"4".equals(operType)||"5".equals(operType)){
						taskid=taskId;
						Map map = new HashMap<String, String>();
						map.put("task_id", taskId);
						map.put("eqpId", eqpId);
						map.put("eqpAddress", eqpAddress);
						map.put("eqpNo", eqpNo);
						map.put("staffId", staffId);
						map.put("eqpName", eqpName);
						map.put("remarks", remarks);//现场规范
						map.put("info", info);
						map.put("longitude", longitude);
						map.put("latitude", latitude);
						map.put("comments", comments);
						map.put("remark", remarks);
		//				map.put("record_type", "");//0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
						map.put("area_id", parentAreaId);
						map.put("son_area_id", eqpAreaId);
						map.put("record_type", operType);
						String OBJECT_TYPE = null;
						String REMARKS = null ;
						if("0".equals(operType)){
							OBJECT_TYPE="0";
							REMARKS="周期性任务";
						}else if ("4".equals(operType)){
							OBJECT_TYPE="3";
							REMARKS="二次检查";
						}else{
							OBJECT_TYPE="4";
							REMARKS="电子档案库";
						}
						/**
						 * 更新端子表
						 */
						for(int i=0;i<innerArray.size();i++){
							JSONObject port = (JSONObject)innerArray.get(i);
							String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
							String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
							String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
							String portId = port.getString("portId");
							String dtsj_id = port.getString("dtsj_id");
							String portNo = port.getString("portNo");
							String portName = port.getString("portName");
							String photoIds = port.getString("photoId");
							String reason = port.getString("reason");
							String isCheckOK = port.getString("isCheckOK");
							String glbm = null==port.get("glbm")?"":port.getString("glbm");
							String glmc = null==port.get("glmc")?"":port.getString("glmc");
							String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
							String type = null;
							String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
							if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
								type = "1";//装维
								
							}else{
								type = "0";//综维
							}
							map.put("port_no", portNo);
							map.put("port_name", portName);
							map.put("descript", reason);
							resmap.put("descript", reason);
							map.put("isCheckOK", isCheckOK);
							/**
							 * 修改端子信息tb_cablecheck_dtsj,检查时间
							 */
							map.put("port_id", portId);
							checkOrderDao.updateDtdz(map);
							/**
							 * 任务详情表在创建工单时即创建记录，设备占一条记录，不同端子各占一条记录，变动N次占N次记录(检查前的信息)
							 * 插入任务详情表(检查后的信息)
							 */
							int recordId = checkOrderDao.getRecordId();
							Map portMap = new HashMap();
							portMap.put("taskid", taskId);
							portMap.put("portNo", portNo);
							map.put("recordId", recordId);
							Map taskDetailMap = new HashMap();
							taskDetailMap.put("TASK_ID", taskId);
							taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
							taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
							taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
							taskDetailMap.put("CHECK_FLAG", 1);//0派单的，1检查的
							taskDetailMap.put("GLBM", glbm);
							taskDetailMap.put("GLMC", glmc);
							taskDetailMap.put("PORT_ID", portId);
							taskDetailMap.put("dtsj_id", dtsj_id);
							taskDetailMap.put("eqpId_port", "");
							taskDetailMap.put("eqpNo_port", "");
							taskDetailMap.put("eqpName_port", "");
							taskDetailMap.put("orderNo", "");
							taskDetailMap.put("orderMark", "");
							taskDetailMap.put("actionType", "");
							taskDetailMap.put("archive_time", "");
							checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
							/**
							 * 插入检查记录表
							 */
							String detail_id = taskDetailMap.get("DETAIL_ID").toString();
							map.put("detail_id", detail_id);
							map.put("eqpId", eqpId_port);
							map.put("eqpNo", eqpNo_port);
							map.put("eqpName", eqpName_port);
							map.put("isH", isH);
							map.put("type", type);
							checkOrderDao.insertEqpRecord(map);
							/**
							 * 保存图片关系
							 */
							if(!"".equals(photoIds) && photoIds != null){
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", taskId);
								photoMap.put("DETAIL_ID", detail_id);
								photoMap.put("OBJECT_ID", recordId);
								photoMap.put("REMARKS", REMARKS);
								photoMap.put("OBJECT_TYPE", OBJECT_TYPE);//3为二次检查
								photoMap.put("RECORD_ID", recordId);
								String[] photos = photoIds.split(",");
								for(String photo : photos){
									photoMap.put("PHOTO_ID", photo);
									checkOrderDao.insertPhotoRel(photoMap);
								}
							}
						}
						/**
						 * 更新任务表,检查完成时间
						 */
						Map taskMap = new HashMap();
						taskMap.put("task_id", taskId);
						taskMap.put("staffId", staffId);
						taskMap.put("remarks", remarks);
						taskMap.put("is_need_zg", "0");
						taskMap.put("statusId", "8");
						//checkOrderDao.updateTask(taskMap);
						//checkOrderDao.updateTaskTime(taskMap);//实际检查完成时间
						//checkOrderDao.updateLastUpdateTime(taskMap);
						//map.put("statusId", "8");
						//checkOrderDao.updateStatus(map);//更新状态
						//一次性更新任务表
						checkOrderDao.updateTask_once(taskMap);
						/**
						 * 增加工单流程流转信息,即回单
						 */
						map.put("oper_staff", staffId);
		//				map.put("status", "7");
						map.put("status", "8");
						map.put("remark", "已归档");
						checkProcessDao.addProcess(map);
						/**
						 * 插入任务详情表
						 */
						int eqpRecordId = checkOrderDao.getRecordId();
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", taskId);
						taskDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
						taskDetailMap.put("CHECK_FLAG", 1);
						taskDetailMap.put("GLBM", "");
						taskDetailMap.put("GLMC", "");
						taskDetailMap.put("PORT_ID", "");
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
						/**
						 * 插入检查记录表
						 */
						map.put("eqpId", eqpId);
						map.put("eqpNo", eqpNo);
						map.put("eqpName", eqpName);
						map.put("recordId", eqpRecordId);
						String eqpdetail_id = taskDetailMap.get("DETAIL_ID").toString();
						map.put("detail_id", eqpdetail_id);
						//插入设备信息，置空端子信息字段
						map.put("port_no", "");
						map.put("port_id", "");
						map.put("isH", "");
						map.put("type", "");
						checkOrderDao.insertEqpRecord(map);
						/**
						 * 保存设备的照片信息
						 */
						
						if(!"".equals(eqpPhotoIds) && eqpPhotoIds != null){
							Map photoMap = new HashMap();
							photoMap.put("TASK_ID", taskId);
							photoMap.put("OBJECT_ID",eqpRecordId);
							photoMap.put("DETAIL_ID", eqpdetail_id);
							photoMap.put("OBJECT_TYPE", OBJECT_TYPE);//0，周期性任务，1：隐患上报工单，2,回单操作
							photoMap.put("REMARKS", REMARKS);
							photoMap.put("RECORD_ID", eqpRecordId);
							String[] photos = eqpPhotoIds.split(",");
							for(String photo : photos){
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}
						
						
						
						
						for(int i=0;i<addressArray.size();i++){
							int id = checkOrderDao.geteqpAddressId();
							JSONObject eqp = (JSONObject)addressArray.get(i);
							
							String eqpId_add = null==eqp.get("eqpId")?"":eqp.getString("eqpId");
							String eqpNo_add = null==eqp.get("eqpNo")?"":eqp.getString("eqpNo");						
							String location_id = eqp.getString("locationId");
							String address_id = eqp.getString("addressId");
							String address_name = eqp.getString("addressName");
							String is_check_ok = eqp.getString("is_check_ok");
							String error_reason = null==eqp.get("error_reason")?"":eqp.getString("error_reason");

							Map adddressMap = new HashMap();
							adddressMap.put("id", id);
							adddressMap.put("phy_eqp_id", eqpId_add);
							adddressMap.put("phy_eqp_no", eqpNo_add);
							adddressMap.put("install_eqp_id", eqpId);
							adddressMap.put("location_id", location_id);
							adddressMap.put("address_id", address_id);
							adddressMap.put("address_name", address_name);
							adddressMap.put("is_check_ok", is_check_ok);
							adddressMap.put("error_reason", error_reason);
							adddressMap.put("task_id", taskId);
							adddressMap.put("create_staff", staffId);
							adddressMap.put("area_id", parentAreaId);
							adddressMap.put("son_area_id", eqpAreaId);
							checkOrderDao.insertEqpAddress(adddressMap);


							
							
						}
						/**
						 * TODO 需要整改
						 */
						if("1".equals(is_bill)){
							/**
							 * 插入任务表
							 */
							Map troubleTaskMap = new HashMap();
							troubleTaskMap.put("TASK_NO", eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
							troubleTaskMap.put("TASK_NAME", eqpName+ "_" + DateUtil.getDate("yyyyMMdd"));
							troubleTaskMap.put("TASK_TYPE", 3);//问题上报
							troubleTaskMap.put("STATUS_ID", 4);//问题上报
							troubleTaskMap.put("INSPECTOR", staffId);
							troubleTaskMap.put("CREATE_STAFF", staffId);
							troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
							troubleTaskMap.put("AREA_ID", parentAreaId);
							troubleTaskMap.put("ENABLE", "0");//如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
							troubleTaskMap.put("REMARK", remarks);
							troubleTaskMap.put("INFO", info);
							troubleTaskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
							
							troubleTaskMap.put("OLD_TASK_ID", taskId);//老的task_id
							troubleTaskMap.put("IS_NEED_ZG", "1");//是否整改,1需要整改
							troubleTaskMap.put("SBID", eqpId);//设备id
							
					
							checkOrderDao.saveTroubleTask(troubleTaskMap);
							String newTaskId = troubleTaskMap.get("TASK_ID").toString();
							logger.info("【需要整改添加一张新的工单taskId】"+newTaskId);
							taskid=newTaskId;
							/**
							 * 更新任务表
							 */
							Map oldTaskMap = new HashMap();
							oldTaskMap.put("task_id", taskId);
							oldTaskMap.put("remarks", remarks);
							oldTaskMap.put("staffId", staffId);
							oldTaskMap.put("is_need_zg", "1");//原来的任务，需要整改
							checkOrderDao.updateTask(oldTaskMap);
							/**
							 * 端子信息
							 */
							if(innerArray.size()>0 && innerArray != null){
								for (int j = 0; j < innerArray.size(); j++) {
									JSONObject port = (JSONObject) innerArray.get(j);
									String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
									String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
									String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
									String portId = port.getString("portId");
									String dtsj_id = port.getString("dtsj_id");
		                            
									String portNo = port.getString("portNo");
									String portName = port.getString("portName");
									String glbm = null==port.get("glbm")?"":port.getString("glbm");
									String glmc = null==port.get("glmc")?"":port.getString("glmc");
									String type = null;
									String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
									if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)) {
										type = "1";
										
									}else{
										type = "0";
									}
									String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
									/**
									 * 保存工单详细
									 */
									int portRecordId = checkOrderDao.getRecordId();
									Map taskDetailMap1 = new HashMap();
									taskDetailMap1.put("TASK_ID", newTaskId);
									taskDetailMap1.put("INSPECT_OBJECT_ID", portRecordId);
									taskDetailMap1.put("INSPECT_OBJECT_NO", portNo);
									taskDetailMap1.put("INSPECT_OBJECT_TYPE", 1);
									taskDetailMap1.put("CHECK_FLAG", "1");
									taskDetailMap1.put("GLBM", glbm);
									taskDetailMap1.put("GLMC", glmc);
									taskDetailMap1.put("PORT_ID", portId);
									taskDetailMap1.put("dtsj_id", dtsj_id);
									taskDetailMap1.put("eqpId_port", "");
									taskDetailMap1.put("eqpNo_port", "");
									taskDetailMap1.put("eqpName_port", "");
									taskDetailMap1.put("orderNo", "");
									taskDetailMap1.put("orderMark", "");
									taskDetailMap1.put("actionType", "");
									taskDetailMap1.put("archive_time", "");
									checkOrderDao.saveTroubleTaskDetail(taskDetailMap1);
									String detail_id1 = taskDetailMap1.get("DETAIL_ID").toString();
									/**
									 * 保存上报来的检查端子记录
									 */
									String portPhotoIds = port.getString("photoId");
									String reason = port.getString("reason");
									String isCheckOK = port.getString("isCheckOK");
									Map recordMap = new HashMap();
									recordMap.put("recordId", portRecordId);
									recordMap.put("task_id", newTaskId);
									recordMap.put("detail_id", detail_id1);
									recordMap.put("eqpId", eqpId_port);
									recordMap.put("eqpAddress", "");
									recordMap.put("eqpNo", eqpNo_port);
									recordMap.put("staffId", staffId);
									recordMap.put("eqpName", eqpName_port);
									recordMap.put("remarks", remarks);
									recordMap.put("info", info);
									recordMap.put("longitude", longitude);
									recordMap.put("latitude", latitude);
									recordMap.put("comments", comments);
									recordMap.put("port_id", portId);
									recordMap.put("port_no", portNo);
									recordMap.put("port_name", portName);
									recordMap.put("info", info);
									recordMap.put("descript", reason);
									resmap.put("descript", reason);
									recordMap.put("isCheckOK", isCheckOK);
									recordMap.put("record_type", operType);//0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
									recordMap.put("area_id", parentAreaId);
									recordMap.put("son_area_id", eqpAreaId);
									recordMap.put("isH", isH);
									recordMap.put("type", type);
									checkOrderDao.insertEqpRecord(recordMap);
									/**
									 * 保存端子照片关系
									 */
									Map photoMap = new HashMap();
									photoMap.put("TASK_ID", newTaskId);
									photoMap.put("DETAIL_ID", detail_id1);
									photoMap.put("OBJECT_ID", portRecordId);
									photoMap.put("REMARKS", "整改工单");
									photoMap.put("OBJECT_TYPE", 1);//0，周期性任务，1：隐患上报工单，2,回单操作
									photoMap.put("RECORD_ID", portRecordId);//0，周期性任务，1：隐患上报工单，2,回单操作
									//photoMap.put("REMARKS", photo);
									if (!"".equals(portPhotoIds)) {
										String[] photos = portPhotoIds.split(",");
										for (String photo : photos) {
											photoMap.put("PHOTO_ID", photo);
											checkOrderDao.insertPhotoRel(photoMap);
										}
									}
								}
							}
							
							int eqp_RecordId = checkOrderDao.getRecordId();
							/**
							 * 保存工单详细
							 */
							Map taskDetailMap1 = new HashMap();
							taskDetailMap1.put("TASK_ID", newTaskId);
							taskDetailMap1.put("INSPECT_OBJECT_ID", eqp_RecordId);
							taskDetailMap1.put("INSPECT_OBJECT_NO", eqpNo);
							taskDetailMap1.put("INSPECT_OBJECT_TYPE", 0);
							taskDetailMap1.put("CHECK_FLAG", "1");
							taskDetailMap1.put("GLBM", "");
							taskDetailMap1.put("GLMC", "");
							taskDetailMap1.put("PORT_ID", "");
							taskDetailMap1.put("dtsj_id", "");
							taskDetailMap1.put("eqpId_port", "");
							taskDetailMap1.put("eqpNo_port", "");
							taskDetailMap1.put("eqpName_port", "");
							taskDetailMap1.put("orderNo", "");
							taskDetailMap1.put("orderMark", "");
							taskDetailMap1.put("actionType", "");
							taskDetailMap1.put("archive_time", "");
							checkOrderDao.saveTroubleTaskDetail(taskDetailMap1);
							
							String detail_id1 = taskDetailMap1.get("DETAIL_ID").toString();
							/**
							 * 保存上报来的设备记录，端子信息为空
							 */
							Map recordMap = new HashMap();
							recordMap.put("recordId", eqp_RecordId);
							recordMap.put("task_id", newTaskId);
							recordMap.put("detail_id", detail_id1);
							recordMap.put("eqpId", eqpId);
							recordMap.put("eqpAddress", eqpAddress);
							recordMap.put("eqpNo", eqpNo);
							recordMap.put("staffId", staffId);
							recordMap.put("eqpName", eqpName);
							recordMap.put("remarks", remarks);
							recordMap.put("info", info);
							recordMap.put("longitude", longitude);
							recordMap.put("latitude", latitude);
							recordMap.put("comments", comments);
							recordMap.put("port_id", "");
							recordMap.put("port_no", "");
							recordMap.put("port_name", "");
							recordMap.put("info", info);
							recordMap.put("descript", "");
							recordMap.put("isCheckOK", "1".equals(is_bill)?1:0);//需要整改说明现场检查不通过，有问题
							recordMap.put("record_type", 0);
							recordMap.put("area_id", parentAreaId);
							recordMap.put("son_area_id", eqpAreaId);
							recordMap.put("isH", "");
							recordMap.put("type", "");
							checkOrderDao.insertEqpRecord(recordMap);
							/**
							 * 插入流程环节
							 */
							Map processMap = new HashMap();
							processMap.put("task_id", newTaskId);
							processMap.put("oper_staff", staffId);
							processMap.put("status", 4);
							processMap.put("remark", "新发起工单");
							checkProcessDao.addProcess(processMap);
						
							if (!"".equals(eqpPhotoIds)) {
								// 保存设备的照片关系
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", newTaskId);
								photoMap.put("OBJECT_ID",eqp_RecordId);
								photoMap.put("DETAIL_ID", detail_id1);
								photoMap.put("OBJECT_TYPE", 1);//0，周期性任务，1：隐患上报工单，2,回单操作
								photoMap.put("REMARKS", "新发起工单");
								photoMap.put("RECORD_ID", eqp_RecordId);
								String[] photos = eqpPhotoIds.split(",");
								for (String photo : photos) {
									photoMap.put("PHOTO_ID", photo);
									checkOrderDao.insertPhotoRel(photoMap);
								}
							}
						}
					}
					/**
					 * TODO
					 * 1、有端子检查的隐患上报(一键反馈有问题端子上报，不可预告抽查有问题端子上报)
					 * 3、无设备编码上报(一键反馈，无设备编码上报)
					 */
					if ("3".equals(operType) ||"1".equals(operType)){													
							//如果是分光器，则需要获取分光器的所属设备，不能以分光器生成任务，必须以箱子为单位生成检查任务或者整改任务						    	
							String eqp_name="";
						    String task_id ="";
						    sblx=checkOrderDao.getEqpType(eqpId_eqpNo_map);
							if("2530".equals(sblx)){
								List<Map<String,Object>> eqpList=checkOrderDao.getEqp(eqpId_eqpNo_map);
								//可能分光器外面箱子并不属于光交，光分，综合箱，ODF，所以在ins库中查不到对应箱子设备
								if(eqpList != null&& eqpList.size()>0){
									Map eqpMap=eqpList.get(0);
									eqp_id=eqpMap.get("EQUIPMENT_ID").toString();//箱子设备ID
									eqp_no=eqpMap.get("EQUIPMENT_CODE").toString();//箱子设备编码
									eqp_name=eqpMap.get("EQUIPMENT_NAME").toString();//箱子设备名称
									
									//保存先工单
									Map troubleTaskMap = new HashMap();
									troubleTaskMap.put("TASK_NO", "3".equals(operType)?eqpAddress:eqp_no + "_" + DateUtil.getDate("yyyyMMdd"));
									troubleTaskMap.put("TASK_NAME", "3".equals(operType)?eqpAddress:eqp_name+ "_" + DateUtil.getDate("yyyyMMdd"));
									troubleTaskMap.put("TASK_TYPE", data_source);//隐患上报
									troubleTaskMap.put("STATUS_ID", "0".equals(is_bill)?8:4);//需要整改为隐患上报，无需整改直接归档
									troubleTaskMap.put("INSPECTOR", staffId);
									troubleTaskMap.put("CREATE_STAFF", staffId);
									troubleTaskMap.put("SON_AREA_ID", "1".equals(operType)?eqpAreaId:sonAreaId);
									troubleTaskMap.put("AREA_ID", "1".equals(operType)?parentAreaId:areaId);
									troubleTaskMap.put("ENABLE", "0".equals(is_bill)?1:0);//如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
									troubleTaskMap.put("REMARK", remarks);
									troubleTaskMap.put("INFO", info);
									troubleTaskMap.put("NO_EQPNO_FLAG", "3".equals(operType)?1:0);//无编码上报
									troubleTaskMap.put("IS_NEED_ZG", is_bill);//是否需要整改
									troubleTaskMap.put("OLD_TASK_ID", "");//老的task_id
									troubleTaskMap.put("SBID", eqp_id);//设备id
									troubleTaskMap.put("COMPANY", "");//设备id
					
									checkOrderDao.saveTroubleTask(troubleTaskMap);
								    task_id = troubleTaskMap.get("TASK_ID").toString();
								    taskid=task_id;
								}else{
									//分光器没有找到对应的箱子，就以分光器为单位进行提交
									Map troubleTaskMap = new HashMap();
									troubleTaskMap.put("TASK_NO", "3".equals(operType)?eqpAddress:eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
									troubleTaskMap.put("TASK_NAME", "3".equals(operType)?eqpAddress:eqpName+ "_" + DateUtil.getDate("yyyyMMdd"));
									troubleTaskMap.put("TASK_TYPE", data_source);//隐患上报
									troubleTaskMap.put("STATUS_ID", "0".equals(is_bill)?8:4);//需要整改为隐患上报，无需整改直接归档
									troubleTaskMap.put("INSPECTOR", staffId);
									troubleTaskMap.put("CREATE_STAFF", staffId);
									troubleTaskMap.put("SON_AREA_ID", "1".equals(operType)?eqpAreaId:sonAreaId);
									troubleTaskMap.put("AREA_ID", "1".equals(operType)?parentAreaId:areaId);
									troubleTaskMap.put("ENABLE", "0".equals(is_bill)?1:0);//如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
									troubleTaskMap.put("REMARK", remarks);
									troubleTaskMap.put("INFO", info);
									troubleTaskMap.put("NO_EQPNO_FLAG", "3".equals(operType)?1:0);//无编码上报
									troubleTaskMap.put("IS_NEED_ZG", is_bill);//是否需要整改
									troubleTaskMap.put("OLD_TASK_ID", "");//老的task_id
									troubleTaskMap.put("SBID", eqpId);//设备id
									troubleTaskMap.put("COMPANY", "");//设备id
					
									checkOrderDao.saveTroubleTask(troubleTaskMap);
								    task_id = troubleTaskMap.get("TASK_ID").toString();		
								    taskid=task_id;
								}																							
							}else{
								//保存先工单
								Map troubleTaskMap = new HashMap();
								troubleTaskMap.put("TASK_NO", "3".equals(operType)?eqpAddress:eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
								troubleTaskMap.put("TASK_NAME", "3".equals(operType)?eqpAddress:eqpName+ "_" + DateUtil.getDate("yyyyMMdd"));
								troubleTaskMap.put("TASK_TYPE", data_source);//隐患上报
								troubleTaskMap.put("STATUS_ID", "0".equals(is_bill)?8:4);//需要整改为隐患上报，无需整改直接归档
								troubleTaskMap.put("INSPECTOR", staffId);
								troubleTaskMap.put("CREATE_STAFF", staffId);
								troubleTaskMap.put("SON_AREA_ID", "1".equals(operType)?eqpAreaId:sonAreaId);
								troubleTaskMap.put("AREA_ID", "1".equals(operType)?parentAreaId:areaId);
								troubleTaskMap.put("ENABLE", "0".equals(is_bill)?1:0);//如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
								troubleTaskMap.put("REMARK", remarks);
								troubleTaskMap.put("INFO", info);
								troubleTaskMap.put("NO_EQPNO_FLAG", "3".equals(operType)?1:0);//无编码上报
								troubleTaskMap.put("IS_NEED_ZG", is_bill);//是否需要整改
								troubleTaskMap.put("OLD_TASK_ID", "");//老的task_id
								troubleTaskMap.put("SBID", eqpId);//设备id
								troubleTaskMap.put("COMPANY", "");//设备id
				
								checkOrderDao.saveTroubleTask(troubleTaskMap);
							    task_id = troubleTaskMap.get("TASK_ID").toString();
							    taskid=task_id;
								
							}												
				//		JSONArray addressArray =json.getJSONArray("allEqpAddress");
						
						for(int i=0;i<addressArray.size();i++){
							int id = checkOrderDao.geteqpAddressId();
							JSONObject eqp = (JSONObject)addressArray.get(i);
							
							String eqpId_add = null==eqp.get("eqpId")?"":eqp.getString("eqpId");
							String eqpNo_add = null==eqp.get("eqpNo")?"":eqp.getString("eqpNo");						
							String location_id = eqp.getString("locationId");
							String address_id = eqp.getString("addressId");
							String address_name = eqp.getString("addressName");
							String is_check_ok = eqp.getString("is_check_ok");
							String error_reason = null==eqp.get("error_reason")?"":eqp.getString("error_reason");

							Map adddressMap = new HashMap();
							adddressMap.put("id", id);
							adddressMap.put("phy_eqp_id", eqpId_add);
							adddressMap.put("phy_eqp_no", eqpNo_add);
							adddressMap.put("install_eqp_id", eqpId);
							adddressMap.put("location_id", location_id);
							adddressMap.put("address_id", address_id);
							adddressMap.put("address_name", address_name);
							adddressMap.put("is_check_ok", is_check_ok);
							adddressMap.put("error_reason", error_reason);
							adddressMap.put("task_id", task_id);
							adddressMap.put("create_staff", staffId);
							adddressMap.put("area_id", parentAreaId);
							adddressMap.put("son_area_id", eqpAreaId);
							checkOrderDao.insertEqpAddress(adddressMap);
						}

						//如果是隐患上报，有端子上报
						if ("1".equals(operType)){
							for (int j = 0; j < innerArray.size(); j++){
								JSONObject port = (JSONObject) innerArray.get(j);
								String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
								String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
								String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
								String portId = port.getString("portId");
								String dtsj_id = port.getString("dtsj_id");
		
								String portNo = null==port.get("portNo")?"":port.getString("portNo");
								String portName = null==port.get("portName")?"":port.getString("portName");
								String portPhotoIds = port.getString("photoId");
								String reason = port.getString("reason");
								String isCheckOK = port.getString("isCheckOK");
								String glbm = null==port.get("glbm")?"":port.getString("glbm");
								String glmc = null==port.get("glmc")?"":port.getString("glmc");
								String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
								String type = null;
								String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
								if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
									type = "1";//E综维   F装维
									
								}else{
									type = "0";
								}
								//保存工单详细
								int recordId = checkOrderDao.getRecordId();
								Map taskDetailMap = new HashMap();
								taskDetailMap.put("TASK_ID", task_id);
								taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
								taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
								taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
								taskDetailMap.put("CHECK_FLAG", "1");
								taskDetailMap.put("GLBM", glbm);
								taskDetailMap.put("GLMC", glmc);
								taskDetailMap.put("PORT_ID", portId);
								taskDetailMap.put("dtsj_id", dtsj_id);
								taskDetailMap.put("eqpId_port", "");
								taskDetailMap.put("eqpNo_port", "");
								taskDetailMap.put("eqpName_port", "");
								taskDetailMap.put("orderNo", "");
								taskDetailMap.put("orderMark", "");
								taskDetailMap.put("actionType", "");
								taskDetailMap.put("archive_time", "");
								checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
								//保存上报来的检查端子记录
								String detail_id = taskDetailMap.get("DETAIL_ID").toString();
								Map recordMap = new HashMap();
								recordMap.put("recordId", recordId);
								recordMap.put("task_id", task_id);
								recordMap.put("detail_id", detail_id);
								recordMap.put("eqpId", eqpId_port);
								recordMap.put("eqpAddress", eqpAddress);
								recordMap.put("eqpNo", eqpNo_port);
								recordMap.put("staffId", staffId);
								recordMap.put("eqpName", eqpName_port);
								recordMap.put("info", info);
								recordMap.put("longitude", longitude);
								recordMap.put("latitude", latitude);
								recordMap.put("comments", comments);
								recordMap.put("port_id", portId);
								recordMap.put("port_no", portNo);
								recordMap.put("port_name", portName);
								recordMap.put("remark", remarks);
								recordMap.put("info", info);
								recordMap.put("descript", reason);
								resmap.put("descript", reason);
								recordMap.put("isCheckOK", isCheckOK);
								recordMap.put("record_type", "1");//0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
								recordMap.put("area_id", parentAreaId);
								recordMap.put("son_area_id", eqpAreaId);
								recordMap.put("isH", isH);
								recordMap.put("type", type);
								checkOrderDao.insertEqpRecord(recordMap);
								//保存端子照片关系
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", task_id);
								photoMap.put("DETAIL_ID", detail_id);
								photoMap.put("OBJECT_ID", recordId);
								photoMap.put("REMARKS", "隐患上报");
								photoMap.put("OBJECT_TYPE", 1);//0，周期性任务，1：隐患上报工单，2,回单操作
								photoMap.put("RECORD_ID",recordId);
								if (!"".equals(portPhotoIds)) {
									String[] photos = portPhotoIds.split(",");
									for (String photo : photos) {
										photoMap.put("PHOTO_ID", photo);
										checkOrderDao.insertPhotoRel(photoMap);
									}
								}
								
							}
						}
						//保存设备信息
						//先获取下一个设备检查记录的ID
						int recordId = checkOrderDao.getRecordId();
						//保存工单详细
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", task_id);
						taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
						if("2530".equals(sblx)&& !"".equals(eqp_no)){
							taskDetailMap.put("INSPECT_OBJECT_NO", eqp_no);
						}else{
							taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
						}												
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
						taskDetailMap.put("CHECK_FLAG", "1");
						taskDetailMap.put("GLBM", "");
						taskDetailMap.put("GLMC", "");
						taskDetailMap.put("PORT_ID", "");
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
						
						//保存上报来的设备记录，端子信息为空
						String detail_id = taskDetailMap.get("DETAIL_ID").toString();
						Map recordMap = new HashMap();
						recordMap.put("recordId", recordId);
						recordMap.put("task_id", task_id);
						recordMap.put("detail_id", detail_id);
						if("2530".equals(sblx)&& !"".equals(eqp_no)){
							recordMap.put("eqpId", eqp_id);
							recordMap.put("eqpNo", "3".equals(operType)?eqpAddress:eqp_no);
							recordMap.put("eqpName", "3".equals(operType)?eqpAddress:eqp_name);
						}else{
							recordMap.put("eqpId", eqpId);
							recordMap.put("eqpNo", "3".equals(operType)?eqpAddress:eqpNo);
							recordMap.put("eqpName", "3".equals(operType)?eqpAddress:eqpName);
						}											
						recordMap.put("eqpAddress", eqpAddress);												
						recordMap.put("staffId", staffId);						
						recordMap.put("remarks", remarks);
						recordMap.put("info", info);
						recordMap.put("longitude", longitude);
						recordMap.put("latitude", latitude);
						recordMap.put("comments", comments);
						recordMap.put("port_id", "");
						recordMap.put("port_no", "");
						recordMap.put("port_name", "");
						recordMap.put("remark", remarks);
						recordMap.put("info", info);
						recordMap.put("descript", "");
						recordMap.put("isCheckOK", "1".equals(is_bill)?1:0);//需要整改说明现场检查不通过，有问题
						recordMap.put("record_type", "3".equals(operType)?3:1);
						recordMap.put("area_id", "1".equals(operType)?parentAreaId:areaId);
						recordMap.put("son_area_id", "1".equals(operType)?eqpAreaId:sonAreaId);
						recordMap.put("isH", "");
						recordMap.put("type", "");
						checkOrderDao.insertEqpRecord(recordMap);
						checkOrderDao.updateTaskTime(recordMap);//实际检查完成时间
						checkOrderDao.updateLastUpdateTime(recordMap);
						//插入流程环节
						Map processMap = new HashMap();
						processMap.put("task_id", task_id);
						processMap.put("oper_staff", staffId);
						if("1".equals(is_bill)){
						processMap.put("status", 4);
						processMap.put("remark", "隐患上报");
						}else{
							processMap.put("status", 8);
							processMap.put("remark", "隐患上报无需整改");
						}
						checkProcessDao.addProcess(processMap);
						// 保存设备的照片关系
						if (!"".equals(eqpPhotoIds)) {
							Map photoMap = new HashMap();
							photoMap.put("TASK_ID", task_id);
							photoMap.put("OBJECT_ID",recordId);
							photoMap.put("DETAIL_ID", detail_id);
							photoMap.put("OBJECT_TYPE", 1);//0，周期性任务，1：隐患上报工单，2,回单操作
							photoMap.put("REMARKS", "隐患上报");
							photoMap.put("RECORD_ID", recordId);
							String[] photos = eqpPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}
						
					
					}
					
					
					
					String truecount = json.getString("truecount");// 全部端子数
					
					Map portmap = new HashMap();
					portmap.put("areaId", parentAreaId);
					portmap.put("sonAreaId", eqpAreaId);
					portmap.put("allcount", allcount);
					portmap.put("truecount", truecount);
					//保证每次检查完设备之后，将检查时间插入到设备表
					if("2530".equals(sblx)&& !"".equals(eqp_no)){
						portmap.put("eqpId", eqp_id);
						portmap.put("eqpNo", eqp_no);
					}else{
						portmap.put("eqpId", eqpId);
						portmap.put("eqpNo", eqpNo);
					}
					checkOrderDao.updateEquipPortNUM(portmap);
																				
					Map map = new HashMap();
					map.put("areaId", parentAreaId);
					map.put("sonAreaId", eqpAreaId);
					//保证每次检查完设备之后，将检查时间插入到设备表
					if("2530".equals(sblx)&& !"".equals(eqp_no)){
						map.put("eqpId", eqp_id);
						map.put("eqpNo", eqp_no);
					}else{
						map.put("eqpId", eqpId);
						map.put("eqpNo", eqpNo);
					}
					checkOrderDao.updateCheckCompleteTime(map);
			    }
			 }
			
			/**
			 * TODO
			 * FTTH拆机工单
			 */
			
			
			
			if ("7".equals(operType)||"8".equals(operType)){
				
					
			//保存先工单
			Map troubleTaskMap = new HashMap();
			troubleTaskMap.put("TASK_NO", eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
			troubleTaskMap.put("TASK_NAME", eqpName+ "_" + DateUtil.getDate("yyyyMMdd"));
			troubleTaskMap.put("TASK_TYPE", operType);//FTTH
			troubleTaskMap.put("STATUS_ID", "0".equals(is_bill)?8:4);//需要整改为隐患上报，无需整改直接归档
			troubleTaskMap.put("INSPECTOR", staffId);
			troubleTaskMap.put("CREATE_STAFF", staffId);
			troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
			troubleTaskMap.put("AREA_ID", parentAreaId);
			troubleTaskMap.put("ENABLE", "0".equals(is_bill)?1:0);//如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
			troubleTaskMap.put("REMARK", remarks);
			troubleTaskMap.put("INFO", info);
			troubleTaskMap.put("NO_EQPNO_FLAG", 0);//无编码上报
			troubleTaskMap.put("IS_NEED_ZG", is_bill);//是否需要整改
			troubleTaskMap.put("OLD_TASK_ID", "");//老的task_id
			troubleTaskMap.put("SBID", eqpId);//设备id
			troubleTaskMap.put("COMPANY", "");//设备id

			checkOrderDao.saveTroubleTask(troubleTaskMap);
			String task_id = troubleTaskMap.get("TASK_ID").toString();
			taskid=task_id;
			
		//	JSONArray addressArray =json.getJSONArray("allEqpAddress");
			
			for(int i=0;i<addressArray.size();i++){
				int id = checkOrderDao.geteqpAddressId();
				JSONObject eqp = (JSONObject)addressArray.get(i);
				
				String eqpId_add = null==eqp.get("eqpId")?"":eqp.getString("eqpId");
				String eqpNo_add = null==eqp.get("eqpNo")?"":eqp.getString("eqpNo");						
				String location_id = eqp.getString("locationId");
				String address_id = eqp.getString("addressId");
				String address_name = eqp.getString("addressName");
				String is_check_ok = eqp.getString("is_check_ok");
				String error_reason = null==eqp.get("error_reason")?"":eqp.getString("error_reason");

				Map adddressMap = new HashMap();
				adddressMap.put("id", id);
				adddressMap.put("phy_eqp_id", eqpId_add);
				adddressMap.put("phy_eqp_no", eqpNo_add);
				adddressMap.put("install_eqp_id", eqpId);
				adddressMap.put("location_id", location_id);
				adddressMap.put("address_id", address_id);
				adddressMap.put("address_name", address_name);
				adddressMap.put("is_check_ok", is_check_ok);
				adddressMap.put("error_reason", error_reason);
				adddressMap.put("task_id", task_id);
				adddressMap.put("create_staff", staffId);
				adddressMap.put("area_id", parentAreaId);
				adddressMap.put("son_area_id", eqpAreaId);
				checkOrderDao.insertEqpAddress(adddressMap);
			}

		
				for (int j = 0; j < innerArray.size(); j++){
					JSONObject port = (JSONObject) innerArray.get(j);
					String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
					String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
					String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
					String portId = port.getString("portId");
					String dtsj_id = port.getString("dtsj_id");

					String portNo = null==port.get("portNo")?"":port.getString("portNo");
					String portName = null==port.get("portName")?"":port.getString("portName");
					String portPhotoIds = port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					String glbm = null==port.get("glbm")?"":port.getString("glbm");
					String glmc = null==port.get("glmc")?"":port.getString("glmc");
					String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
					String type = null;
					String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
					if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
						type = "1";//E综维   F装维
						
					}else{
						type = "0";
					}
					//保存工单详细
					int recordId = checkOrderDao.getRecordId();
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", "1");
					taskDetailMap.put("GLBM", glbm);
					taskDetailMap.put("GLMC", glmc);
					taskDetailMap.put("PORT_ID", portId);
					taskDetailMap.put("dtsj_id", dtsj_id);
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					//保存上报来的检查端子记录
					String detail_id = taskDetailMap.get("DETAIL_ID").toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", task_id);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId_port);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo_port);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName_port);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", portId);
					recordMap.put("port_no", portNo);
					recordMap.put("port_name", portName);
					recordMap.put("remark", remarks);
					recordMap.put("info", info);
					recordMap.put("descript", reason);
					resmap.put("descript", reason);
					recordMap.put("isCheckOK", isCheckOK);
					recordMap.put("record_type", operType);//FTTH拆机
					recordMap.put("area_id", parentAreaId);
					recordMap.put("son_area_id", eqpAreaId);
					recordMap.put("isH", isH);
					recordMap.put("type", type);
					checkOrderDao.insertEqpRecord(recordMap);
					//保存端子照片关系
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("OBJECT_TYPE", 4);//0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID",recordId);
					if (!"".equals(portPhotoIds)) {
						String[] photos = portPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}
					
				}
			
			//保存设备信息
			//先获取下一个设备检查记录的ID
			int recordId = checkOrderDao.getRecordId();
			//保存工单详细
			Map taskDetailMap = new HashMap();
			taskDetailMap.put("TASK_ID", task_id);
			taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
			taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
			taskDetailMap.put("CHECK_FLAG", "");
			taskDetailMap.put("GLBM", "");
			taskDetailMap.put("GLMC", "");
			taskDetailMap.put("PORT_ID", "");
			taskDetailMap.put("dtsj_id", "");
			taskDetailMap.put("eqpId_port", "");
			taskDetailMap.put("eqpNo_port", "");
			taskDetailMap.put("eqpName_port", "");
			taskDetailMap.put("orderNo", "");
			taskDetailMap.put("orderMark", "");
			taskDetailMap.put("actionType", "");
			taskDetailMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
			
			//保存上报来的设备记录，端子信息为空
			String detail_id = taskDetailMap.get("DETAIL_ID").toString();
			Map recordMap = new HashMap();
			recordMap.put("recordId", recordId);
			recordMap.put("task_id", task_id);
			recordMap.put("detail_id", detail_id);
			recordMap.put("eqpId", eqpId);
			recordMap.put("eqpAddress", eqpAddress);
			recordMap.put("eqpNo", eqpNo);
			recordMap.put("staffId", staffId);
			recordMap.put("eqpName", eqpName);
			recordMap.put("remarks", remarks);
			recordMap.put("info", info);
			recordMap.put("longitude", longitude);
			recordMap.put("latitude", latitude);
			recordMap.put("comments", comments);
			recordMap.put("port_id", "");
			recordMap.put("port_no", "");
			recordMap.put("port_name", "");
			recordMap.put("remark", remarks);
			recordMap.put("info", info);
			recordMap.put("descript", "");
			recordMap.put("isCheckOK", "1".equals(is_bill)?1:0);//需要整改说明现场检查不通过，有问题
			recordMap.put("record_type", operType);
			recordMap.put("area_id", parentAreaId);
			recordMap.put("son_area_id", eqpAreaId);
			recordMap.put("isH", "");
			recordMap.put("type", "");
			checkOrderDao.insertEqpRecord(recordMap);
			checkOrderDao.updateTaskTime(recordMap);//实际检查完成时间
			checkOrderDao.updateLastUpdateTime(recordMap);
			//插入流程环节
			Map processMap = new HashMap();
			processMap.put("task_id", task_id);
			processMap.put("oper_staff", staffId);
			if("1".equals(is_bill)){
			processMap.put("status", 4);
			processMap.put("remark", "隐患上报");
			}else{
				processMap.put("status", 8);
				processMap.put("remark", "隐患上报无需整改");
			}
			checkProcessDao.addProcess(processMap);
			// 保存设备的照片关系
			if (!"".equals(eqpPhotoIds)) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", task_id);
				photoMap.put("OBJECT_ID",recordId);
				photoMap.put("DETAIL_ID", detail_id);
				photoMap.put("OBJECT_TYPE", 4);//0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("REMARKS", "隐患上报");
				photoMap.put("RECORD_ID", recordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
		
			
			
			}
			
			
			
			
			
			/**
			 * TODO
			 * 2、回单操作(我的检查任务-->整改工单回单)
			 */
			if("2".equals(operType)){
				Map map = new HashMap();
				map.put("task_id", taskId);
				String statusIdStr = checkOrderDao.queryTaskByTaskId(map).get("STATUS_ID").toString();
				int curStatusId = Integer.valueOf(statusIdStr);
				if(curStatusId==7){
					result.put("result", "001");
					result.put("desc", "已回单");
				}else{
					Map paramMap = new HashMap();
				    paramMap.put("rwmxId", rwmxId);
				    //插入流程环节
				    paramMap.put("task_id", taskId);
				    paramMap.put("remark", "回单，待审核");
				    paramMap.put("status", "7");
				    paramMap.put("oper_staff", staffId);
					checkProcessDao.addProcess(paramMap);
					//更新任务表
					Map taskMap = new HashMap();
					taskMap.put("staffId", staffId);
					taskMap.put("statusId", "7");
					taskMap.put("task_id", taskId);
					checkOrderDao.updateTaskBack(taskMap);
					checkOrderDao.updateLastUpdateTime(taskMap);
					/**
					 * 设备检查记录表中记录整改记录 注意record_type
					 */
					paramMap.put("eqpId", eqpId);
					paramMap.put("eqpNo", eqpNo);
					paramMap.put("eqpName", eqpName);
					paramMap.put("longitude", longitude);
					paramMap.put("latitude", latitude);
					paramMap.put("comments", comments);
					paramMap.put("eqpAddress", eqpAddress);
					paramMap.put("remarks", remarks);
					paramMap.put("info", info);
					paramMap.put("staffId", staffId);
					paramMap.put("record_type", "2");
					paramMap.put("area_id", areaId);
					paramMap.put("son_area_id", sonAreaId);
					paramMap.put("task_id", taskId);
					for(int i=0;i<innerArray.size();i++){
						JSONObject port = (JSONObject)innerArray.get(i);
						String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
						String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
						String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
						String portId = port.getString("portId");
						String portNo = port.getString("portNo");
						String portName = port.getString("portName");
						String portPhotoIds = null==port.get("photoId")?"":port.getString("photoId");
						String reason = port.getString("reason");
						String isCheckOK = port.getString("isCheckOK");
						String glbm = null==port.get("glbm")?"":port.getString("glbm");
						String glmc = null==port.get("glmc")?"":port.getString("glmc");
						String type = null == port.get("type")?"":port.getString("type");
						String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
						String port_info = null== port.get("port_info")?"":port.getString("port_info");
						int recordId = checkOrderDao.getRecordId();
						paramMap.put("recordId", recordId);
						paramMap.put("port_id", portId);
						paramMap.put("port_no", portNo);
						paramMap.put("port_name", portName);
						paramMap.put("descript", reason);//端子情况
						resmap.put("descript", reason);
						paramMap.put("isCheckOK", isCheckOK);
						//插入详情表
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", taskId);
						taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
						taskDetailMap.put("CHECK_FLAG", "2");//回单
						taskDetailMap.put("GLBM", glbm);
						taskDetailMap.put("GLMC", glmc);
						taskDetailMap.put("PORT_ID", portId);
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					//	插入记录表
						String detail_id = taskDetailMap.get("DETAIL_ID").toString();
						paramMap.put("detail_id", detail_id);
						paramMap.put("eqpId", eqpId_port);
						paramMap.put("eqpNo", eqpNo_port);
						paramMap.put("eqpName", eqpName_port);
						paramMap.put("isH", isH);
						paramMap.put("port_info", port_info);
						paramMap.put("type", type);
						checkOrderDao.insertEqpRecord(paramMap);
						//保存端子照片关系
						Map photoMap = new HashMap();
						photoMap.put("TASK_ID", taskId);
						photoMap.put("DETAIL_ID", "");
						photoMap.put("OBJECT_ID", recordId);
						photoMap.put("REMARKS", "已回单，待审核");
						photoMap.put("OBJECT_TYPE", 2);//0，周期性任务，1：隐患上报工单，2,回单操作
						photoMap.put("RECORD_ID",recordId);
						if (!"".equals(portPhotoIds)) {
							String[] photos = portPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}
					}
					//保存设备信息
					int recordId = checkOrderDao.getRecordId();
					//插入详情表
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", taskId);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
					taskDetailMap.put("CHECK_FLAG", "2");//回单
					taskDetailMap.put("GLBM", "");
					taskDetailMap.put("GLMC", "");
					taskDetailMap.put("PORT_ID", "");
					taskDetailMap.put("dtsj_id", "");
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					//插入记录表
					String detail_id = taskDetailMap.get("DETAIL_ID").toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", taskId);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName);
					recordMap.put("remarks", remarks);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", "");
					recordMap.put("port_no", "");
					recordMap.put("port_name", "");
					recordMap.put("remark", remarks);
					recordMap.put("descript", "");
					recordMap.put("isCheckOK", "0");
					recordMap.put("record_type", "2");
					recordMap.put("area_id", areaId);
					recordMap.put("son_area_id", sonAreaId);
					recordMap.put("isH", "");
					recordMap.put("type", "");
					checkOrderDao.insertEqpRecord(recordMap);
					//保存设备照片关系
					if (!"".equals(eqpPhotoIds)) {
						Map photoMap = new HashMap();
						photoMap.put("TASK_ID", taskId);
						photoMap.put("OBJECT_ID",recordId);
						photoMap.put("DETAIL_ID", "");
						photoMap.put("OBJECT_TYPE", 2);//0，周期性任务，1：隐患上报工单，2,回单操作
						photoMap.put("REMARKS", "回单");
						photoMap.put("RECORD_ID", recordId);
						String[] photos = eqpPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}
				}
				//保证每次检查完设备之后，将检查时间插入到设备表
				Map map2 = new HashMap();
				map2.put("areaId", parentAreaId);
				map2.put("sonAreaId", eqpAreaId);
				map2.put("eqpId", eqpId);
				map2.put("eqpNo", eqpNo);
				checkOrderDao.updateCheckCompleteTime(map2);
			 }   
			
			
			/**
			 * TODO	
			 * 2、回单操作(资源整改工单回单)
			 */
			if("6".equals(operType)){
				
				
				Map troubleTaskMap = new HashMap();
				troubleTaskMap.put("TASK_NO", eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_NAME", eqpName+ "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_TYPE", 6);//隐患上报
				troubleTaskMap.put("STATUS_ID", "0".equals(is_bill)?8:4);//需要整改为隐患上报，无需整改直接归档
				troubleTaskMap.put("INSPECTOR", staffId);
				troubleTaskMap.put("CREATE_STAFF", staffId);
				troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
				troubleTaskMap.put("AREA_ID", parentAreaId);
				troubleTaskMap.put("ENABLE", "0".equals(is_bill)?1:0);//如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
				troubleTaskMap.put("REMARK", remarks);
				troubleTaskMap.put("INFO", info);
				troubleTaskMap.put("NO_EQPNO_FLAG", 0);//无编码上报
				troubleTaskMap.put("IS_NEED_ZG", is_bill);//是否需要整改
				troubleTaskMap.put("OLD_TASK_ID", taskId);//老的task_id
				troubleTaskMap.put("SBID", eqpId);//设备id
				

				checkOrderDao.saveTroubleTask(troubleTaskMap);
				String task_id = troubleTaskMap.get("TASK_ID").toString();
				taskid=task_id;
					for(int i=0;i<innerArray.size();i++){
						JSONObject port = (JSONObject)innerArray.get(i);
						String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
						String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
						String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
						String portId = port.getString("portId");
						String portNo = port.getString("portNo");
						String portName = port.getString("portName");
						String portPhotoIds = null==port.get("photoId")?"":port.getString("photoId");
						String reason = port.getString("reason");
						String isCheckOK = port.getString("isCheckOK");
						String glbm = null==port.get("glbm")?"":port.getString("glbm");
						String glmc = null==port.get("glmc")?"":port.getString("glmc");
						String type = null == port.get("type")?"":port.getString("type");
						String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
						String port_info = null== port.get("port_info")?"":port.getString("port_info");
					
						
						
						
						
						//保存工单详细
						int recordId = checkOrderDao.getRecordId();
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", task_id);
						taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
						taskDetailMap.put("CHECK_FLAG", "3");
						taskDetailMap.put("GLBM", glbm);
						taskDetailMap.put("GLMC", glmc);
						taskDetailMap.put("PORT_ID", portId);
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
						//保存上报来的检查端子记录
						String detail_id = taskDetailMap.get("DETAIL_ID").toString();
						Map recordMap = new HashMap();
						recordMap.put("recordId", recordId);
						recordMap.put("task_id", task_id);
						recordMap.put("detail_id", detail_id);
						recordMap.put("eqpId", eqpId_port);
						recordMap.put("eqpAddress", eqpAddress);
						recordMap.put("eqpNo", eqpNo_port);
						recordMap.put("staffId", staffId);
						recordMap.put("eqpName", eqpName_port);
						recordMap.put("longitude", longitude);
						recordMap.put("latitude", latitude);
						recordMap.put("comments", comments);
						recordMap.put("port_id", portId);
						recordMap.put("port_no", portNo);
						recordMap.put("port_name", portName);
						recordMap.put("remark", remarks);
						recordMap.put("info", info);
						recordMap.put("descript", port_info);
						resmap.put("descript", port_info);
						recordMap.put("isCheckOK", isCheckOK);
						recordMap.put("record_type", "6");//资源整改
						recordMap.put("area_id", parentAreaId);
						recordMap.put("son_area_id", eqpAreaId);
						recordMap.put("isH", isH);
						recordMap.put("type", type);
						checkOrderDao.insertEqpRecord(recordMap);
						//保存端子照片关系
						Map photoMap = new HashMap();
						photoMap.put("TASK_ID", task_id);
						photoMap.put("DETAIL_ID", detail_id);
						photoMap.put("OBJECT_ID", recordId);
						photoMap.put("REMARKS", "隐患上报");
						photoMap.put("OBJECT_TYPE", 1);//0，周期性任务，1：隐患上报工单，2,回单操作
						photoMap.put("RECORD_ID",recordId);
						if (!"".equals(portPhotoIds)) {
							String[] photos = portPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}
						
					
					}
					//保存设备信息
					//先获取下一个设备检查记录的ID
					int recordId = checkOrderDao.getRecordId();
					//保存工单详细
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
					taskDetailMap.put("CHECK_FLAG", "3");//资源整改
					taskDetailMap.put("GLBM", "");
					taskDetailMap.put("GLMC", "");
					taskDetailMap.put("PORT_ID", "");
					taskDetailMap.put("dtsj_id", "");
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					
					//保存上报来的设备记录，端子信息为空
					String detail_id = taskDetailMap.get("DETAIL_ID").toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", task_id);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName);
					recordMap.put("remarks", remarks);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", "");
					recordMap.put("port_no", "");
					recordMap.put("port_name", "");
					recordMap.put("remark", remarks);
					recordMap.put("info", info);
					recordMap.put("descript", "");
					recordMap.put("isCheckOK", "1".equals(is_bill)?1:0);//需要整改说明现场检查不通过，有问题
					recordMap.put("record_type", "6");
					recordMap.put("area_id", parentAreaId);
					recordMap.put("son_area_id", eqpAreaId);
					recordMap.put("isH", "");
					recordMap.put("type", "");
					checkOrderDao.insertEqpRecord(recordMap);
					checkOrderDao.updateTaskTime(recordMap);//实际检查完成时间
					checkOrderDao.updateLastUpdateTime(recordMap);
					//插入流程环节
					Map processMap = new HashMap();
					processMap.put("task_id", task_id);
					processMap.put("oper_staff", staffId);
					if("1".equals(is_bill)){
					processMap.put("status", 4);
					processMap.put("remark", "隐患上报");
					}else{
						processMap.put("status", 8);
						processMap.put("remark", "隐患上报无需整改");
					}
					checkProcessDao.addProcess(processMap);
					// 保存设备的照片关系
					if (!"".equals(eqpPhotoIds)) {
						Map photoMap = new HashMap();
						photoMap.put("TASK_ID", task_id);
						photoMap.put("OBJECT_ID",recordId);
						photoMap.put("DETAIL_ID", detail_id);
						photoMap.put("OBJECT_TYPE", 6);//0，周期性任务，1：隐患上报工单，2,回单操作
						photoMap.put("REMARKS", "隐患上报");
						photoMap.put("RECORD_ID", recordId);
						String[] photos = eqpPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}
				
				//保证每次检查完设备之后，将检查时间插入到设备表
				Map map = new HashMap();
				map.put("areaId", areaId);
				map.put("sonAreaId", sonAreaId);
				map.put("eqpId", eqpId);
				map.put("eqpNo", eqpNo);
				checkOrderDao.updateCheckCompleteTime(map);
		    
			   
			}
			
			
			
			/**
			 * TODO
			 * 客响订单
			 */
  		if ("9".equals(operType)){
				
					
			//保存先工单
			Map troubleTaskMap = new HashMap();
			troubleTaskMap.put("TASK_NO", eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
			troubleTaskMap.put("TASK_NAME", eqpName+ "_" + DateUtil.getDate("yyyyMMdd"));
			troubleTaskMap.put("TASK_TYPE", operType);//FTTH
			troubleTaskMap.put("STATUS_ID", "0".equals(is_bill)?8:4);//需要整改为隐患上报，无需整改直接归档
			troubleTaskMap.put("INSPECTOR", staffId);
			troubleTaskMap.put("CREATE_STAFF", staffId);
			troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
			troubleTaskMap.put("AREA_ID", parentAreaId);
			troubleTaskMap.put("ENABLE", "0".equals(is_bill)?1:0);//如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
			troubleTaskMap.put("REMARK", remarks);
			troubleTaskMap.put("INFO", info);
			troubleTaskMap.put("NO_EQPNO_FLAG", 0);//无编码上报
			troubleTaskMap.put("IS_NEED_ZG", is_bill);//是否需要整改
			troubleTaskMap.put("OLD_TASK_ID", "");//老的task_id
			troubleTaskMap.put("SBID", eqpId);//设备id
			troubleTaskMap.put("COMPANY", "");//设备id

			checkOrderDao.saveTroubleTask(troubleTaskMap);
			String task_id = troubleTaskMap.get("TASK_ID").toString();
			taskid=task_id;
			
		//	JSONArray addressArray =json.getJSONArray("allEqpAddress");
			
			for(int i=0;i<addressArray.size();i++){
				int id = checkOrderDao.geteqpAddressId();
				JSONObject eqp = (JSONObject)addressArray.get(i);
				
				String eqpId_add = null==eqp.get("eqpId")?"":eqp.getString("eqpId");
				String eqpNo_add = null==eqp.get("eqpNo")?"":eqp.getString("eqpNo");						
				String location_id = eqp.getString("locationId");
				String address_id = eqp.getString("addressId");
				String address_name = eqp.getString("addressName");
				String is_check_ok = eqp.getString("is_check_ok");
				String error_reason = null==eqp.get("error_reason")?"":eqp.getString("error_reason");

				Map adddressMap = new HashMap();
				adddressMap.put("id", id);
				adddressMap.put("phy_eqp_id", eqpId_add);
				adddressMap.put("phy_eqp_no", eqpNo_add);
				adddressMap.put("install_eqp_id", eqpId);
				adddressMap.put("location_id", location_id);
				adddressMap.put("address_id", address_id);
				adddressMap.put("address_name", address_name);
				adddressMap.put("is_check_ok", is_check_ok);
				adddressMap.put("error_reason", error_reason);
				adddressMap.put("task_id", task_id);
				adddressMap.put("create_staff", staffId);
				adddressMap.put("area_id", parentAreaId);
				adddressMap.put("son_area_id", eqpAreaId);
				checkOrderDao.insertEqpAddress(adddressMap);
			}

		
				for (int j = 0; j < innerArray.size(); j++){
					JSONObject port = (JSONObject) innerArray.get(j);
					String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
					String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
					String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
					String portId = port.getString("portId");
					String dtsj_id = port.getString("dtsj_id");

					String portNo = null==port.get("portNo")?"":port.getString("portNo");
					String portName = null==port.get("portName")?"":port.getString("portName");
					String portPhotoIds = port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					String glbm = null==port.get("glbm")?"":port.getString("glbm");
					String glmc = null==port.get("glmc")?"":port.getString("glmc");
					String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
					String type = null;
					String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
					if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
						type = "1";//E综维   F装维
						
					}else{
						type = "0";
					}
					//保存工单详细
					int recordId = checkOrderDao.getRecordId();
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", "1");
					taskDetailMap.put("GLBM", glbm);
					taskDetailMap.put("GLMC", glmc);
					taskDetailMap.put("PORT_ID", portId);
					taskDetailMap.put("dtsj_id", dtsj_id);
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					//保存上报来的检查端子记录
					String detail_id = taskDetailMap.get("DETAIL_ID").toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", task_id);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId_port);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo_port);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName_port);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", portId);
					recordMap.put("port_no", portNo);
					recordMap.put("port_name", portName);
					recordMap.put("remark", remarks);
					recordMap.put("info", info);
					recordMap.put("descript", reason);
					resmap.put("descript", reason);
					recordMap.put("isCheckOK", isCheckOK);
					recordMap.put("record_type", operType);//FTTH拆机
					recordMap.put("area_id", parentAreaId);
					recordMap.put("son_area_id", eqpAreaId);
					recordMap.put("isH", isH);
					recordMap.put("type", type);
					checkOrderDao.insertEqpRecord(recordMap);
					//保存端子照片关系
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("OBJECT_TYPE", 4);//0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID",recordId);
					if (!"".equals(portPhotoIds)) {
						String[] photos = portPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}
					
				}
			
			//保存设备信息
			//先获取下一个设备检查记录的ID
			int recordId = checkOrderDao.getRecordId();
			//保存工单详细
			Map taskDetailMap = new HashMap();
			taskDetailMap.put("TASK_ID", task_id);
			taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
			taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
			taskDetailMap.put("CHECK_FLAG", "");
			taskDetailMap.put("GLBM", "");
			taskDetailMap.put("GLMC", "");
			taskDetailMap.put("PORT_ID", "");
			taskDetailMap.put("dtsj_id", "");
			taskDetailMap.put("eqpId_port", "");
			taskDetailMap.put("eqpNo_port", "");
			taskDetailMap.put("eqpName_port", "");
			taskDetailMap.put("orderNo", "");
			taskDetailMap.put("orderMark", "");
			taskDetailMap.put("actionType", "");
			taskDetailMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
			
			//保存上报来的设备记录，端子信息为空
			String detail_id = taskDetailMap.get("DETAIL_ID").toString();
			Map recordMap = new HashMap();
			recordMap.put("recordId", recordId);
			recordMap.put("task_id", task_id);
			recordMap.put("detail_id", detail_id);
			recordMap.put("eqpId", eqpId);
			recordMap.put("eqpAddress", eqpAddress);
			recordMap.put("eqpNo", eqpNo);
			recordMap.put("staffId", staffId);
			recordMap.put("eqpName", eqpName);
			recordMap.put("remarks", remarks);
			recordMap.put("info", info);
			recordMap.put("longitude", longitude);
			recordMap.put("latitude", latitude);
			recordMap.put("comments", comments);
			recordMap.put("port_id", "");
			recordMap.put("port_no", "");
			recordMap.put("port_name", "");
			recordMap.put("remark", remarks);
			recordMap.put("info", info);
			recordMap.put("descript", "");
			recordMap.put("isCheckOK", "1".equals(is_bill)?1:0);//需要整改说明现场检查不通过，有问题
			recordMap.put("record_type", operType);
			recordMap.put("area_id", parentAreaId);
			recordMap.put("son_area_id", eqpAreaId);
			recordMap.put("isH", "");
			recordMap.put("type", "");
			checkOrderDao.insertEqpRecord(recordMap);
			checkOrderDao.updateTaskTime(recordMap);//实际检查完成时间
			checkOrderDao.updateLastUpdateTime(recordMap);
			//插入流程环节
			Map processMap = new HashMap();
			processMap.put("task_id", task_id);
			processMap.put("oper_staff", staffId);
			if("1".equals(is_bill)){
			processMap.put("status", 4);
			processMap.put("remark", "隐患上报");
			}else{
				processMap.put("status", 8);
				processMap.put("remark", "隐患上报无需整改");
			}
			checkProcessDao.addProcess(processMap);
			// 保存设备的照片关系
			if (!"".equals(eqpPhotoIds)) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", task_id);
				photoMap.put("OBJECT_ID",recordId);
				photoMap.put("DETAIL_ID", detail_id);
				photoMap.put("OBJECT_TYPE", 4);//0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("REMARKS", "隐患上报");
				photoMap.put("RECORD_ID", recordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
		
			}
	
  		/*if(is_bill.equals("1") && flag){
  			xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
  			"<IfInfo><sysRoute>"+sysRoute+"</sysRoute>"+
  				"<taskType>"+taskTypes+"</taskType>"+
  				"<gwMan>"+staffName+"</gwMan>"+
  				"<taskId>"+taskid+"</taskId>"+
  				"<gwManAccount>"+id_numberList+"</gwManAccount>"+
  				"<equCbAccount>"+contract_persion_nos+"</equCbAccount>"+
  				"<gwPositionPersons>"+PositionPersonsList+"</gwPositionPersons>"+
  				"<equCode>"+eqpNo+"</equCode>"+
  				"<equName>"+eqpName+"</equName>"+
  				"<equType>"+equType+"</equType>"+
  				"<errorPortList>"+portIdList+"</errorPortList>"+
  				"<gwContent>"+resmap.get("descript")+"</gwContent>"+
  			"</IfInfo>";
  			
  			WfworkitemflowLocator locator =new WfworkitemflowLocator();
  			try {
  				WfworkitemflowSoap11BindingStub stub=(WfworkitemflowSoap11BindingStub)locator.getWfworkitemflowHttpSoap11Endpoint();
  				results=stub.outSysDispatchTask(xml);
  		//	    resultss=stub.outSysDispatchTask(xmls);
  				Document doc = null;
  				doc = DocumentHelper.parseText(results); // 将字符串转为XML
  				Element rootElt = doc.getRootElement(); // 获取根节点
  				Element IfResult = rootElt.element("IfResult");
  				String IfResultinfo=	IfResult.getText(); //0是成功 1是失败
  				Map IfResultMap = new HashMap();
  				IfResultMap.put("Result_Status", IfResultinfo);
  				IfResultMap.put("task_id", taskid);
  				checkOrderDao.updateResultTask(IfResultMap);
  				System.out.println(xml);
  				System.out.println(results);
  				result.put("msg1", results);
  			//	System.out.println(result);
  				//outSysDispatchTask
  			} catch (Exception e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  				result.put("result", "001");
  				result.put("desc", "线路工单推送接口调用失败。");
  			}

  			
  		}else if(is_bill.equals("0") && flag){
  			xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
  			"<IfInfo><sysRoute>"+sysRoute+"</sysRoute>"+
  				"<taskType>"+taskType+"</taskType>"+
  				"<gwMan>"+staffName+"</gwMan>"+
  				"<taskId>"+taskid+"</taskId>"+
  				"<gwManAccount>"+staffNo+"</gwManAccount>"+
  				"<equCode>"+eqpNo+"</equCode>"+
  				"<equName>"+eqpName+"</equName>"+
  				"<chekPortNum>"+allcount+"</chekPortNum>"+
  				"<adressCheckCnt>"+addressCheckCnt+"</adressCheckCnt>"+
  				"<gwContent>"+resmap.get("descript")+"</gwContent>"+
  			"</IfInfo>";
  	  		
  			WfworkitemflowLocator locator =new WfworkitemflowLocator();
  			try {
  				WfworkitemflowSoap11BindingStub stub=(WfworkitemflowSoap11BindingStub)locator.getWfworkitemflowHttpSoap11Endpoint();
  				results=stub.outSysDispatchTask(xml);
  		//	    resultss=stub.outSysDispatchTask(xmls);
  				Document doc = null;
  				doc = DocumentHelper.parseText(results); // 将字符串转为XML
  				Element rootElt = doc.getRootElement(); // 获取根节点
  				Element IfResult = rootElt.element("IfResult");
  				String IfResultinfo=	IfResult.getText(); //0是成功 1是失败
  				Map IfResultMap = new HashMap();
  				IfResultMap.put("Result_Status", IfResultinfo);
  				IfResultMap.put("task_id", taskid);
  				checkOrderDao.updateResultTask(IfResultMap);
  				System.out.println(xml);
  				System.out.println(results);
  				result.put("msg1", results);
  			//	System.out.println(result);
  				//outSysDispatchTask
  			} catch (Exception e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  				result.put("result", "001");
  				result.put("desc", "线路工单推送接口调用失败。");
  			}

  			
  		}*/
		}catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "生成工单失败，请联系管理员。");
		}
		return result.toString();	
	}

	@Override
	public String cancelBill(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);

			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");
			String taskIds = json.getString("taskId").toString();
			String cancelReason = json.getString("cancelReason");// 设备id

			String taskId[] = taskIds.split(",");
			for (int i = 0; i < taskId.length; i++) {
				// 对工单进行调整
				Map cancelBillMap = new HashMap();
				cancelBillMap.put("taskId", taskId[i]);
				checkOrderDao.cancelBill(cancelBillMap);
				// 插入流程环节
				Map processMap = new HashMap();
				processMap.put("task_id", taskId[i]);
				processMap.put("oper_staff", staffId);
				processMap.put("status", 5);
				processMap.put("remark", "退单:" + cancelReason);
				checkProcessDao.addProcess(processMap);
			}
		} catch (Exception e) {

			result.put("result", "001");
			System.out.println(e.toString());

		}
		return result.toString();

	}

	@Override
	public String forwardBill(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");
			String taskIds = json.getString("taskId").toString();
			String cancelReason = json.getString("cancelReason");// 设备id
			String forwarStaffId = json.getString("forwarStaffId");//
			String taskId[] = taskIds.split(",");
			String DATA_SOURCE = json.getString("data_source");
			Map param = new HashMap();
			param.put("staffId", staffId);
			if ("0".equals(DATA_SOURCE)) {
				for (int i = 0; i < taskId.length; i++) {
					// 对工单进行调整
					Map forwardBillMap = new HashMap();
					forwardBillMap.put("taskId", taskId[i]);
					forwardBillMap.put("forwarStaffId", forwarStaffId);
					checkOrderDao.forwardBill(forwardBillMap);
					// 插入流程环节
					Map processMap = new HashMap();
					processMap.put("task_id", taskId[i]);
					processMap.put("oper_staff", staffId);
					processMap.put("status", 6);
					processMap.put("remark", "转派工单:" + cancelReason);
					checkProcessDao.addProcess(processMap);
				}
			} else if ("2".equals(DATA_SOURCE)) {
				for (int i = 0; i < taskId.length; i++) {
					// 对工单进行调整
					Map forwardzgBillMap = new HashMap();
					forwardzgBillMap.put("taskId", taskId[i]);
					forwardzgBillMap.put("forwarStaffId", forwarStaffId);
					checkOrderDao.forwardzgBill(forwardzgBillMap);
					// 插入流程环节
					Map processMap = new HashMap();
					processMap.put("task_id", taskId[i]);
					processMap.put("oper_staff", staffId);
					processMap.put("status", 6);
					processMap.put("remark", "转派工单:" + cancelReason);
					checkProcessDao.addProcess(processMap);
				}
			}
		} catch (Exception e) {

			result.put("result", "001");
			System.out.println(e.toString());

		}
		return result.toString();
	}

	/**
	 * TODO 部分整改
	 */
	@Override
	public String partCommitGd(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		String compare_right="";
		String compare_wrong="";
		String wrong_port_record="";
		String compare_info="";
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");
			String taskId = json.getString("taskId").toString();
			String eqpId = json.getString("eqpId");
			String eqpNo = json.getString("eqpNo");
			String eqpName = json.getString("eqpName");
			String eqpAddress = json.getString("eqpAddress");
			String eqpPhotoIds = json.getString("photoId");// 设备照片
			String forwarStaffId = json.getString("forwarStaffId");//
			String info = json.getString("info");// 回单备注
			String forwarInfo = json.getString("forwarInfo");// 转派备注
			String areaId = json.getString("areaId");// 地市
			String sonAreaId = "";// 区域
			JSONArray innerArray = json.getJSONArray("port");
			JSONObject portJson = new JSONObject();
			/**
			 * 获取当前登录人员的区域信息
			 */
		/*	Map areaMap = checkOrderDao.queryAreaByStaffId(staffId);
			String sonAreaId = null == areaMap.get("SON_AREA_ID") ? "": areaMap.get("SON_AREA_ID").toString();
			String areaId = null == areaMap.get("AREA_ID") ? "" : areaMap.get("AREA_ID").toString();*/
			
			/**
			 * 根据设备获取区域信息
			 */
			Map eqpId_eqpNo_map=new HashMap();
			eqpId_eqpNo_map.put("eqpId", eqpId);
			eqpId_eqpNo_map.put("eqpNo", eqpNo);
			Map eqpareaMap = checkOrderDao.queryAreaByeqpId(eqpId_eqpNo_map);
		    sonAreaId = null ==  eqpareaMap.get("AREA_ID")? "" : eqpareaMap.get("AREA_ID").toString();
		    
			Map taskmap = new HashMap();
			taskmap.put("task_id", taskId);
			String statusIdStr = checkOrderDao.queryTaskByTaskId(taskmap).get("STATUS_ID").toString();
			int curStatusId = Integer.valueOf(statusIdStr);

			Map forwardzgBillMap = new HashMap();
			forwardzgBillMap.put("taskId", taskId);
			forwardzgBillMap.put("forwarStaffId", forwarStaffId);
			checkOrderDao.forwardzgBill(forwardzgBillMap);
			Map paramMap = new HashMap();
			
			/**
			 * 更改任务表相关信息，如状态
			 */
			Map taskMap = new HashMap();
			taskMap.put("staffId", staffId);
			taskMap.put("statusId", "6");
			taskMap.put("task_id", taskId);
			checkOrderDao.updateTaskBack(taskMap);
			/**
			 * 设备检查记录表中记录整改记录 注意record_type
			 */
			paramMap.put("eqpId", eqpId);
			paramMap.put("eqpNo", eqpNo);
			paramMap.put("eqpName", eqpName);
			paramMap.put("eqpAddress", eqpAddress);
			paramMap.put("info", info);//回单备注
			paramMap.put("forwarInfo", forwarInfo);//转派信息
			paramMap.put("staffId", staffId);
			paramMap.put("record_type", "2");
			paramMap.put("area_id", areaId);
			paramMap.put("son_area_id", sonAreaId);
			paramMap.put("taskId", taskId);
			
			Map<String,String> processMap=new HashMap<String,String>();
			Map<String,String> changePortMap=new HashMap<String,String>();
			for (int i = 0; i < innerArray.size(); i++) {
				JSONObject port = (JSONObject) innerArray.get(i);
				String eqpId_port = null == port.get("eqpId") ? "" : port.getString("eqpId");
				String eqpNo_port = null == port.get("eqpNo") ? "" : port.getString("eqpNo");
				String eqpName_port = null == port.get("eqpName") ? "": port.getString("eqpName");
				String eqpAddress_port = null == port.get("eqpAddress") ? "": port.getString("eqpAddress");
				String portId = port.getString("portId");
				String portNo = port.getString("portNo");
				String portName = port.getString("portName");
				String portPhotoIds = null == port.get("photoId") ? "": port.getString("photoId");
				String reason = port.getString("reason");//0 ：已整改 "" 1：无法整改 无法整改原因
				String reason2 = port.getString("reason2");//检查人员检查的错误原因
				String isCheckOK = port.getString("isCheckOK");
				String glbm = null == port.get("glbm") ? "" : port.getString("glbm");
				String glmc = null == port.get("glmc") ? "" : port.getString("glmc");
				String isH = null == port.get("isH") ? "" : port.getString("isH");// 是否H光路，0不是，1是
				String CorrectPortNo = null== port.get("CorrectPortNo")?"":port.getString("CorrectPortNo");//正确端子编码
				String CorrectPortId = null== port.get("CorrectPortId")?"":port.getString("CorrectPortId");//正确端子id
				String rightEqpId = null == port.get("rightEqpId") ? "": port.getString("rightEqpId");//正确设备ID				
				String rightEqpNo = null == port.get("rightEqpNo") ? "": port.getString("rightEqpNo");//正确设备编码
				String changedPortId=port.getString("portId_new");//修改后的端子id
				String changedPortNo=port.getString("localPortNo");//修改后的端子编码
				String changedEqpId=port.getString("sbid_new");//修改后的设备ID
				String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
				int recordId = checkOrderDao.getRecordId();
				paramMap.put("recordId", recordId);
				paramMap.put("port_id", portId);
				paramMap.put("port_no", portNo);
				paramMap.put("port_name", portName);
				//paramMap.put("descript", reason);// 端子情况
				paramMap.put("reason", reason);//检查端子错误描述
				paramMap.put("reason2", reason2);//整改端子错误描述
				paramMap.put("isCheckOK", isCheckOK);
				paramMap.put("CorrectPortNo", CorrectPortNo);
				paramMap.put("CorrectPortId", CorrectPortId);
				paramMap.put("glbm", glbm);

				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", taskId);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
				taskDetailMap.put("CHECK_FLAG", "1");
				taskDetailMap.put("GLBM", glbm);
				taskDetailMap.put("GLMC", glmc);
				taskDetailMap.put("PORT_ID", portId);
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				paramMap.put("detail_id", detail_id);
				paramMap.put("eqpId", eqpId_port);
				paramMap.put("eqpNo", eqpNo_port);
				paramMap.put("eqpName", eqpName_port);
				paramMap.put("eqpAddress", eqpAddress_port);
				paramMap.put("isH", isH);
				paramMap.put("truePortId",CorrectPortId);
				paramMap.put("truePortNo",CorrectPortNo);
				paramMap.put("rightEqpId",rightEqpId);
				paramMap.put("rightEqpNo",rightEqpNo);//正确
				paramMap.put("changedPortId",changedPortId);
				paramMap.put("changedPortNo",changedPortNo);
				paramMap.put("changedEqpId",changedEqpId);
				paramMap.put("changedEqpNo",changedEqpNo);
				checkOrderDao.insertEqpRecordPartZg(paramMap);
				//checkOrderDao.insertEqpRecord_new(paramMap);
				
				//如果changedPortNo不为空，表示整改人员已经对现场进行整改维护
				//将修改后的端子插入流程表 tb_cablecheck_process
				if("0".equals(isCheckOK)){
					if(!"".equals(changedPortNo)){
						String content="";
						if(eqpId_port.equals(changedEqpId)){
							content=glbm+"从"+eqpNo_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
						}else{
							content =glbm+"从"+eqpNo_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
						}
						changePortMap.put("oper_staff", staffId);
						changePortMap.put("task_id", taskId);
						changePortMap.put("status", "66");//一键改
						changePortMap.put("remark", "一键改");
						changePortMap.put("content", content);
						changePortMap.put("receiver", "");
						checkProcessDao.addProcessNew(changePortMap);
					}
				}
				// 保存端子照片关系
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskId);
				photoMap.put("DETAIL_ID", "");
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("REMARKS", "部分工单转派");
				photoMap.put("OBJECT_TYPE", 2);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId);
				if (!"".equals(portPhotoIds)) {
					String[] photos = portPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}
				if("0".equals(isCheckOK)){
					//自动校验端子是否整改正确
					String compare_result= compareWorkOrder_new(paramMap);//"000":自动校验成功   "001":自动校验失败
					if("000".equals(compare_result)){
						if(compare_right.length()>0){
							compare_right=compare_right+","+portNo;
						}else{
							compare_right=portNo;
						}
						//自动校验通过的端子记录流程表
						processMap.put("oper_staff", staffId);
						processMap.put("task_id", taskId);
						processMap.put("status",  "77"); //自动校验
						processMap.put("remark", "自动校验");
						processMap.put("receiver", "");
						processMap.put("content", eqpNo_port+"上的"+portNo+"端子编码自动校验已通过");
						checkProcessDao.addProcessNew(processMap);
					}else{
						if(compare_wrong.length()>0){
							compare_wrong=compare_wrong+","+portNo;
						}else{
							compare_wrong=portNo;
						}
					}
				}else{
					if(wrong_port_record.length()>0){
						wrong_port_record=wrong_port_record+","+portNo;
					}else{
						wrong_port_record=portNo;
					}
					//自动校验未通过的端子记录流程表
					processMap.put("oper_staff", staffId);
					processMap.put("task_id", taskId);
					processMap.put("status",  "77"); //自动校验
					processMap.put("remark", "自动校验");
					processMap.put("receiver", "");
					processMap.put("content", eqpNo_port+"上的"+portNo+"端子编码自动校验未通过");
					checkProcessDao.addProcessNew(processMap);
				}
			}
			// 保存设备信息
			int recordId = checkOrderDao.getRecordId();
			Map recordMap = new HashMap();
			recordMap.put("recordId", recordId);
			recordMap.put("taskId", taskId);
			recordMap.put("detail_id", "");
			recordMap.put("eqpId", eqpId);
			recordMap.put("eqpAddress", eqpAddress);
			recordMap.put("eqpNo", eqpNo);
			recordMap.put("staffId", staffId);
			recordMap.put("eqpName", eqpName);
			recordMap.put("info", info);
			recordMap.put("port_id", "");
			recordMap.put("port_no", "");
			recordMap.put("port_name", "");
			recordMap.put("descript", "");
			recordMap.put("isCheckOK", "1");
			recordMap.put("record_type", "2");
			recordMap.put("area_id", areaId);
			recordMap.put("son_area_id", sonAreaId);
			recordMap.put("CorrectPortNo", "");
			paramMap.put("CorrectPortId", "");
			recordMap.put("isH", "");
			checkOrderDao.insertEqpRecord_new(recordMap);
			// 保存设备照片关系
			if (!"".equals(eqpPhotoIds)) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskId);
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("DETAIL_ID", "");
				photoMap.put("OBJECT_TYPE", 2);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("REMARKS", "部分整改回单");
				photoMap.put("RECORD_ID", recordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
			/**
			 * 插入流程环节
			 */
			paramMap.put("task_id", taskId);
			paramMap.put("remark", "转派工单");
			paramMap.put("status", "6");
			paramMap.put("oper_staff", staffId);
			paramMap.put("receiver", forwarStaffId);
			paramMap.put("content", "维护员将工单转派给其他人整改");
			//checkProcessDao.addProcess(paramMap);
			checkProcessDao.addProcessNew(paramMap);
			String flag="转派";
			compare_info=getResult(compare_right,compare_wrong,wrong_port_record,flag);
			result.put("desc", compare_info);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "工单转派失败");
		}
		return result.toString();
	}

	@Override
	public String query(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {

			JSONObject json = JSONObject.fromObject(jsonStr);
			int checkTaskNum = 0;
			int zgTaskNum = 0;
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");
			Map param = new HashMap();
			JSONObject taskJsonObject = new JSONObject();
			param.put("staffId", staffId);
			param.put("sn", sn);
			param.put("terminalType", terminalType);
			// 通过staffId获取身份证账号
			String idNumber = checkMyworkDao.getIdentify(staffId);
			result.put("idNumber", idNumber);
			int secondCheckTaskNum = 0;
			int checkedEqpNum = checkMyworkDao.countEqpNum(staffId);
			int checkedzgEqpNum = checkMyworkDao.countzgEqpNum(staffId);
			int checkedPortNum = checkMyworkDao.countPortNum(staffId);
			int checkedzgPortNum = checkMyworkDao.countzgPortNum(staffId);
			List<Map> taskList = checkTaskDao.getTaskList(param);
			int toDoTaskNum = taskList.size();
			for (Map dtsj : taskList) {
				if ("0".equals(String.valueOf(dtsj.get("DATA_SOURCE")))||
					"10".equals(String.valueOf(dtsj.get("DATA_SOURCE")))||
					"11".equals(String.valueOf(dtsj.get("DATA_SOURCE")))||
					"12".equals(String.valueOf(dtsj.get("DATA_SOURCE")))||
					"13".equals(String.valueOf(dtsj.get("DATA_SOURCE")))
						) {
					checkTaskNum = checkTaskNum + 1;
				} else if ("4".equals(String.valueOf(dtsj.get("DATA_SOURCE")))) {
					secondCheckTaskNum = secondCheckTaskNum + 1;

				} else {
					zgTaskNum = zgTaskNum + 1;
				}
			}
			result.put("checkedEqpNum", checkedEqpNum);// 已检查设备数,
			result.put("checkedzgEqpNum", checkedzgEqpNum);// /已整改设备数
			result.put("checkedPortNum", checkedPortNum);// 已检查端子数
			result.put("checkedzgPortNum", checkedzgPortNum);// 已整改端子数
			result.put("toDoTaskNum", toDoTaskNum);// 待办任务总数
			result.put("checkTaskNum", checkTaskNum);// 检查任务数
			result.put("zgTaskNum", zgTaskNum);
			result.put("secondCheckTaskNum", secondCheckTaskNum);// 整改待办数

			// 查询当天工作情况
			int checkedTodayEqpNum = checkMyworkDao.countTodayEqpNum(staffId);
			int checkedTodayzgEqpNum = checkMyworkDao
					.countTodayzgEqpNum(staffId);
			int checkedTodayPortNum = checkMyworkDao.countTodayPortNum(staffId);
			int checkedTodayzgPortNum = checkMyworkDao
					.countTodayzgPortNum(staffId);

			result.put("checkedTodayEqpNum", checkedTodayEqpNum);// 当天已检查设备数,
			result.put("checkedTodayzgEqpNum", checkedTodayzgEqpNum);// /当天已整改设备数
			result.put("checkedTodayPortNum", checkedTodayPortNum);// 当天已检查端子数
			result.put("checkedTodayzgPortNum", checkedTodayzgPortNum);
			// 当天已整改端子数
		} catch (Exception e) {
			result.put("result", "001");
			System.out.println(e.toString());

		}
		return result.toString();

	}

	/**
	 * 采集设备经纬度
	 */
	@Override
	public String getLongLati(String jsonStr) {
		JSONObject result = new JSONObject();
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String longitude = json.getString("longitude");// 检察人员当前位置的经度
			String latitude = json.getString("latitude");// 检察人员当前位置的纬度
			String staffId = json.getString("staffID_inspect");// 检查人账号ID
			String eqpID = json.getString("eqpID");// 设备ID，通过设备ID来判断哪条设备的经纬度与实际不符，应该重新采集新的经纬度插入设备表,原先的设备经纬度记录保留
			String eqpNo = json.getString("eqpNo");// 有些设备
													// 设备ID对应着两个不同设备编码，需要设备编码来进行判断,只有当设备ID与设备编码都一致时才行

			Map areaMap = checkOrderDao.queryAreaByStaffId(staffId);// 同一地市同一区域同一设备(将区域限制条件删除)
			String sonAreaId = null == areaMap.get("SON_AREA_ID") ? ""
					: areaMap.get("SON_AREA_ID").toString();
			String areaId = null == areaMap.get("AREA_ID") ? "" : areaMap.get(
					"AREA_ID").toString();

			String times = "1";
			Map map = new HashMap();
			map.put("eqpLongitude_inspect", longitude);// 默认将检查人员的位置作为设备经纬度
			map.put("eqpLatitude_inspect", latitude);// 默认将检查人员的位置作为设备经纬度
			map.put("staffID_inspect", staffId);
			map.put("eqpID", eqpID);
			map.put("eqpNo", eqpNo);
			map.put("times", times);
			// 设备经纬度总共只能采集三次(采集成功才算一次),先从设备表中获取采集次数

			// 由于检查人员采集经纬度的时候，采集次数经常超过三次，一旦超过三次，则需要重置，维护时间则多，故放开次数，同时新建一张设备经纬度表，将每次采集的经纬度全部保存
			String time = checkOrderDao.getLongLatiTimes(map);
			if (time == null || "".equals(time) || " ".equals(time)) {
				checkOrderDao.updateLongLati_times(map);
				checkOrderDao.insertEqpLongLati(map);
				result.put("result", "000");
				result.put("desc", "设备坐标采集成功。");
			} else if ((Integer.valueOf(time)) <= 99) {
				times = String.valueOf((Integer.valueOf(times) + Integer
						.valueOf(time)));// 不是第一次采集，则从设备表将采集次数取出来之后+1，重新插入设备表
				map.put("times", times);
				checkOrderDao.insertEqpLongLati(map);
				checkOrderDao.updateLongLati_times(map);
				result.put("result", "000");
				result.put("desc", "设备坐标采集成功。");
			} else {
				// 通过将检察人员的位置作为设备的位置，那么也就不会存在采集失败的情况，只会存在“设备坐标采集机会已用完。”
				// 那么最后一个检查人去采集的时候，出现还没有采集就提醒说机会已用完的提示
				result.put("result", "112");
				result.put("desc", "设备坐标采集机会已用完。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "设备坐标采集失败。");
		}
		return result.toString();
	}

	/**
	 * 此方法用来判断是否允许提交或者判断设备经纬度是否采集成功，，允许返回true,不允许返回false
	 */
	public boolean getPermission(String eqpLongitude, String eqpLatitude,
			String longitude, String latitude) {
		boolean flag = true;
		try {
			if ("".equals(eqpLongitude) || "".equals(eqpLatitude)) {
				flag = false;// 如果设备没有经纬度，则让采集人员直接采集坐标，无需计算
			} else {
				double eqpLongitude1 = Double.valueOf(eqpLongitude);
				double eqpLatitude1 = Double.valueOf(eqpLatitude);
				double longitude1 = Double.valueOf(longitude);
				double latitude1 = Double.valueOf(latitude);
				double s = getDistance(eqpLongitude1, eqpLatitude1, longitude1,
						latitude1);
				if (s > 300) {
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */

	public static double getDistance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		d = Math.round(d * 10000) / 10000;
		return d;
	}

	// 现场复查提交
	@Override
	public String checkAgainSubmit(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String eqpId = json.getString("eqpId");// 箱子设备ID
			String eqpNo = json.getString("eqpNo");// 箱子设备编
			String eqpName = json.getString("eqpName");// 箱子设备名称
			String reviewStaff = json.getString("staffId");// 复查人
			Map map = new HashMap();
			map.put("eqpId", eqpId);
			map.put("eqpNo", eqpNo);
			// 获取箱子的设备类型
			String res_type_id = checkOrderDao.getEqpType(map);

			if (!"2530".equals(res_type_id)) {
				// 箱子复查记录
				JSONArray eqpRecordsArray = json.getJSONArray("eqpRecords");
				Map eqpMap = new HashMap();
				for (int i = 0; i < eqpRecordsArray.size(); i++) {
					eqpMap.clear();
					JSONObject eqpRecords = (JSONObject) eqpRecordsArray.get(i);
					String port_id = eqpRecords.getString("port_id");
					if ("".equals(port_id)) {
						continue;
					}
					String port_no = eqpRecords.getString("port_no");
					String glbm = eqpRecords.getString("glbm");
					String ossGlbm = eqpRecords.getString("ossGlbm");
					String check_ischeckok = eqpRecords
							.getString("check_ischeckok");// 检查结果
					String descript = eqpRecords.getString("descript");// 端子错误描述
					String checkstaff = eqpRecords.getString("checkstaff");// 检查人
					String create_time = eqpRecords.getString("create_time");// 检查时间
					String review_ischeckok = eqpRecords
							.getString("review_ischeckok");// 复查结果 1表示不合格 0表示合格
					// 如果复查合格就没有原因，如果不合格，则获取不合格原因
					String remark = "";
					/*if ("1".equals(review_ischeckok)) {
						remark = eqpRecords.getString("remark");// 箱子 端子复查不合格原因
					}*/
					String check_review_result = "";
					if ("0".equals(review_ischeckok)) {
						check_review_result = "是";
					} else {
						check_review_result = "否";
						remark = eqpRecords.getString("remark");// 箱子 端子复查不合格原因
					}
					eqpMap.put("port_id", port_id);
					eqpMap.put("port_no", port_no);
					eqpMap.put("glbm", glbm);
					eqpMap.put("ossGlbm", ossGlbm);
					eqpMap.put("check_ischeckok", check_ischeckok);
					eqpMap.put("descript", descript);
					eqpMap.put("checkstaff", checkstaff);
					eqpMap.put("create_time", create_time);
					eqpMap.put("reviewStaff", reviewStaff);
					eqpMap.put("review_ischeckok", review_ischeckok);
					eqpMap.put("review_remark", remark);
					eqpMap.put("check_review_result", check_review_result);
					eqpMap.put("res_type_id", res_type_id);
					eqpMap.put("eqpId", eqpId);
					eqpMap.put("eqpNo", eqpNo);
					eqpMap.put("eqpName", eqpName);
					checkOrderDao.insertCheckEqpRecord(eqpMap);
				}

				/**
				 * 分光器复查记录
				 */
				JSONArray obdRecordsArray = json.getJSONArray("obdRecords");
				Map obdMap = new HashMap();
				for (int i = 0; i < obdRecordsArray.size(); i++) {
					obdMap.clear();
					JSONObject obdMapRecords = (JSONObject) obdRecordsArray
							.get(i);
					String port_id = obdMapRecords.getString("port_id");
					if ("".equals(port_id)) {
						continue;
					}
					String port_no = obdMapRecords.getString("port_no");
					String glbm = obdMapRecords.getString("glbm");
					String ossGlbm = obdMapRecords.getString("ossGlbm");
					String check_ischeckok = obdMapRecords
							.getString("check_ischeckok");// 检查结果
					String descript = obdMapRecords.getString("descript");// 端子错误描述
					String checkstaff = obdMapRecords.getString("checkstaff");// 检查人
					String create_time = obdMapRecords.getString("create_time");// 检查时间
					String review_ischeckok = obdMapRecords
							.getString("review_ischeckok");// 复查结果 1表示不合格 0表示合格
					String remark = "";
					/*if ("1".equals(review_ischeckok)) {
						remark = obdMapRecords.getString("remark");// 箱子  端子复查不合格原因
					}*/
					String obdId = obdMapRecords.getString("obdId");// 分光器id
					String obdNo = obdMapRecords.getString("obdNo");// 分光器编码
					String obdName = obdMapRecords.getString("obdName");// 分光器名称
					String check_review_result = "";
					if ("0".equals(review_ischeckok)) {
						check_review_result = "是";
					} else {
						check_review_result = "否";
						remark = obdMapRecords.getString("remark");// 箱子  端子复查不合格原因
					}
					obdMap.put("port_id", port_id);
					obdMap.put("port_no", port_no);
					obdMap.put("glbm", glbm);
					obdMap.put("ossGlbm", ossGlbm);
					obdMap.put("check_ischeckok", check_ischeckok);
					obdMap.put("descript", descript);
					obdMap.put("checkstaff", checkstaff);
					obdMap.put("create_time", create_time);
					obdMap.put("reviewStaff", reviewStaff);
					obdMap.put("review_ischeckok", review_ischeckok);
					obdMap.put("review_remark", remark);
					obdMap.put("check_review_result", check_review_result);
					obdMap.put("res_type_id", res_type_id);
					obdMap.put("eqpId", eqpId);
					obdMap.put("eqpNo", eqpNo);
					obdMap.put("eqpName", eqpName);
					obdMap.put("obdId", obdId);
					obdMap.put("obdNo", obdNo);
					obdMap.put("obdName", obdName);
					obdMap.put("obd_type_id", "2530");
					checkOrderDao.insertReviewRecord(obdMap);
				}
			} else {
				// 复查人员特地查询分光器 此时会将分光器当做箱子处理，所以先获取分光器所属设备，如果没有，就算了
				JSONArray eqpRecordsArray = json.getJSONArray("obdRecords");
				Map eqpMap = new HashMap();
				List<Map<String, Object>> eqpList = checkOrderDao.getEqp(map);
				if (eqpList != null && eqpList.size() > 0) {
					Map eqpMapByObd = eqpList.get(0);
					String eqp_id = eqpMapByObd.get("EQUIPMENT_ID").toString();// 箱子设备ID
					String eqp_no = eqpMapByObd.get("EQUIPMENT_CODE")
							.toString();// 箱子设备编码
					String eqp_name = eqpMapByObd.get("EQUIPMENT_NAME")
							.toString();// 箱子设备名称
					String RES_TYPE_ID = eqpMapByObd.get("RES_TYPE_ID")
							.toString();// 箱子设备类型
					for (int i = 0; i < eqpRecordsArray.size(); i++) {
						eqpMap.clear();
						JSONObject eqpRecords = (JSONObject) eqpRecordsArray
								.get(i);
						String port_id = eqpRecords.getString("port_id");
						if ("".equals(port_id)) {
							continue;
						}
						String port_no = eqpRecords.getString("port_no");
						String glbm = eqpRecords.getString("glbm");
						String ossGlbm = eqpRecords.getString("ossGlbm");
						String check_ischeckok = eqpRecords
								.getString("check_ischeckok");// 检查结果
						String descript = eqpRecords.getString("descript");// 端子错误描述
						String checkstaff = eqpRecords.getString("checkstaff");// 检查人
						String create_time = eqpRecords
								.getString("create_time");// 检查时间
						String review_ischeckok = eqpRecords
								.getString("review_ischeckok");// 复查结果 1表示不合格
																// 0表示合格
						// 如果复查合格就没有原因，如果不合格，则获取不合格原因
						String remark = "";
						/*if ("1".equals(review_ischeckok)) {
							remark = eqpRecords.getString("remark");// 箱子 端子复查不合格原因
						}*/
						String check_review_result = "";
						if ("0".equals(review_ischeckok)) {
							check_review_result = "是";
						} else {
							check_review_result = "否";
							remark = eqpRecords.getString("remark");// 箱子 端子复查不合格原因
						}
						eqpMap.put("port_id", port_id);
						eqpMap.put("port_no", port_no);
						eqpMap.put("glbm", glbm);
						eqpMap.put("ossGlbm", ossGlbm);
						eqpMap.put("check_ischeckok", check_ischeckok);
						eqpMap.put("descript", descript);
						eqpMap.put("checkstaff", checkstaff);
						eqpMap.put("create_time", create_time);
						eqpMap.put("reviewStaff", reviewStaff);
						eqpMap.put("review_ischeckok", review_ischeckok);
						eqpMap.put("review_remark", remark);
						eqpMap.put("check_review_result", check_review_result);
						eqpMap.put("res_type_id", RES_TYPE_ID);
						eqpMap.put("eqpId", eqp_id);
						eqpMap.put("eqpNo", eqp_no);
						eqpMap.put("eqpName", eqp_name);
						eqpMap.put("obdId", eqpId);
						eqpMap.put("obdNo", eqpNo);
						eqpMap.put("obdName", eqpName);
						eqpMap.put("obd_type_id", "2530");
						checkOrderDao.insertReviewRecord(eqpMap);
					}
				} else {
					// 可能分光器外面箱子并不属于光交，光分，综合箱，ODF，所以在ins库中查不到对应箱子设备
					for (int i = 0; i < eqpRecordsArray.size(); i++) {
						eqpMap.clear();
						JSONObject eqpRecords = (JSONObject) eqpRecordsArray
								.get(i);
						String port_id = eqpRecords.getString("port_id");
						if ("".equals(port_id)) {
							continue;
						}
						String port_no = eqpRecords.getString("port_no");
						String glbm = eqpRecords.getString("glbm");
						String ossGlbm = eqpRecords.getString("ossGlbm");
						String check_ischeckok = eqpRecords
								.getString("check_ischeckok");// 检查结果
						String descript = eqpRecords.getString("descript");// 端子错误描述
						String checkstaff = eqpRecords.getString("checkstaff");// 检查人
						String create_time = eqpRecords
								.getString("create_time");// 检查时间
						String review_ischeckok = eqpRecords
								.getString("review_ischeckok");// 复查结果 1表示不合格
																// 0表示合格
						// 如果复查合格就没有原因，如果不合格，则获取不合格原因
						String remark = "";
						/*if ("1".equals(review_ischeckok)) {
							remark = eqpRecords.getString("remark");// 箱子 端子复查不合格原因
						}*/
						String check_review_result = "";
						if ("0".equals(review_ischeckok)) {
							check_review_result = "是";
						} else {
							check_review_result = "否";
							remark = eqpRecords.getString("remark");// 箱子 端子复查不合格原因
						}
						eqpMap.put("port_id", port_id);
						eqpMap.put("port_no", port_no);
						eqpMap.put("glbm", glbm);
						eqpMap.put("ossGlbm", ossGlbm);
						eqpMap.put("check_ischeckok", check_ischeckok);
						eqpMap.put("descript", descript);
						eqpMap.put("checkstaff", checkstaff);
						eqpMap.put("create_time", create_time);
						eqpMap.put("reviewStaff", reviewStaff);
						eqpMap.put("review_ischeckok", review_ischeckok);
						eqpMap.put("review_remark", remark);
						eqpMap.put("check_review_result", check_review_result);
						eqpMap.put("res_type_id", "");
						eqpMap.put("eqpId", "");
						eqpMap.put("eqpNo", "");
						eqpMap.put("eqpName", "");
						eqpMap.put("obdId", eqpId);
						eqpMap.put("obdNo", eqpNo);
						eqpMap.put("obdName", eqpName);
						eqpMap.put("obd_type_id", "2530");
						checkOrderDao.insertReviewRecord(eqpMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "现场复查提交失败，请联系管理员。");
		}
		return result.toString();
	}

	/**
	 * 集团对标，上报工单，并自动派发
	 */
	@Override
	public String saveJtGd(String jsonStr) {

		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);

			// String queryType = json.getString("queryType");// 查询类型
			// if (!OnlineUserListener.isLogin(staffId, sn)) {
			// result.put("result", "002");
			// result.put("photoId", "");
			// return result.toString();
			// }
			/**
			 * 传入的参数
			 */
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");
			String taskId = json.getString("taskId");
			String rwmxId = null == json.get("rwmxId") ? "" : json
					.getString("rwmxId");
			String eqpId = null == json.get("eqpId") ? "" : json
					.getString("eqpId");// 设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");// 设备名称
			String eqpAddress = null == json.get("eqpAddress") ? "" : json
					.getString("eqpAddress");// 设备地址（临时上报隐患时，必填）
			String eqpPhotoIds = json.getString("photoId");// 设备照片
			String operType = json.getString("operType");// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
															// 4：二次检查

			String remarks = json.getString("remarks");// 现场规范
			String info = null == json.get("info") ? "" : json
					.getString("info");// 备注
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String comments = null == json.get("comments") ? "" : json
					.getString("comments");// 评价
			String is_bill = json.getString("is_bill");// 是否需要整改
			String data_source = json.getString("data_source");// 数据来源
			String constructor = json.getString("constructor");
			String identification = json.getString("identification");

			/**
			 * 获取当前登录人员的区域信息
			 */
			Map areaMap = checkOrderDao.queryAreaByStaffId(staffId);
			String sonAreaId = null == areaMap.get("SON_AREA_ID") ? ""
					: areaMap.get("SON_AREA_ID").toString();
			String areaId = null == areaMap.get("AREA_ID") ? "" : areaMap.get(
					"AREA_ID").toString();
			/**
			 * 根据设备获取区域信息
			 */
			Map eqpId_eqpNo_map = new HashMap();
			eqpId_eqpNo_map.put("eqpId", eqpId);
			eqpId_eqpNo_map.put("eqpNo", eqpNo);
			Map eqpareaMap = checkOrderDao.queryAreaByeqpId(eqpId_eqpNo_map);
			String parentAreaId = null == eqpareaMap.get("PARENT_AREA_ID") ? ""
					: eqpareaMap.get("PARENT_AREA_ID").toString();
			String eqpAreaId = null == eqpareaMap.get("AREA_ID") ? ""
					: eqpareaMap.get("AREA_ID").toString();
			/**
			 * 根据设备id获取设备所在的经纬度--从gis中同步过来的
			 */
			String eqpLongitude = null == eqpareaMap.get("LONGITUDE") ? ""
					: eqpareaMap.get("LONGITUDE").toString();
			String eqpLatitude = null == eqpareaMap.get("LATITUDE") ? ""
					: eqpareaMap.get("LATITUDE").toString();
			/**
			 * 根据设备id获取设备所在的经纬度--检查人员采集的
			 */
			String longitude_inspect = null == eqpareaMap
					.get("LONGITUDE_INSPECT") ? "" : eqpareaMap.get(
					"LONGITUDE_INSPECT").toString();
			String latitude_inspect = null == eqpareaMap
					.get("LATITUDE_INSPECT") ? "" : eqpareaMap.get(
					"LATITUDE_INSPECT").toString();

			/**
			 * 端子信息
			 */
			JSONArray innerArray = json.getJSONArray("port");

			if (!"2".equals(operType) && !"6".equals(operType)) {
				// 一开始将检查人的当前位置与设备表中的设备经纬度计算，如果检查或整改设备与人的距离超过300m，则不允许提交工单，flag默认为true,如果超过300m,flag改为false
				boolean flag = true;// 如果没有采集坐标，就用gis中同步过来的经纬度进行距离计算，否则用检查人员采集过来的经纬度计算
				if ("".equals(longitude_inspect) || "".equals(latitude_inspect)) {
					flag = getPermission(eqpLongitude, eqpLatitude, longitude,
							latitude);
				} else {
					flag = getPermission(longitude_inspect, latitude_inspect,
							longitude, latitude);
				}

				// 一旦不允许提交工单后，让检察人员重新获取设备的经纬度
				if (!flag) {
					result.put("result", "112");
					result.put("desc", "生成工单失败，请采集设备坐标。");
				}
				if (flag) {

					/**
					 * TODO 0、周期任务巡检拍照上传(我的检查任务-->检查任务签到)
					 */
					if ("0".equals(operType) || "4".equals(operType)
							|| "5".equals(operType)) {
						Map map = new HashMap<String, String>();
						map.put("task_id", taskId);
						map.put("eqpId", eqpId);
						map.put("eqpAddress", eqpAddress);
						map.put("eqpNo", eqpNo);
						map.put("staffId", staffId);
						map.put("eqpName", eqpName);
						map.put("remarks", remarks);// 现场规范
						map.put("info", info);
						map.put("longitude", longitude);
						map.put("latitude", latitude);
						map.put("comments", comments);
						map.put("remark", remarks);
						// map.put("record_type",
						// "");//0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
						map.put("area_id", parentAreaId);
						map.put("son_area_id", eqpAreaId);
						map.put("record_type", operType);
						String OBJECT_TYPE = null;
						String REMARKS = null;
						if ("0".equals(operType)) {
							OBJECT_TYPE = "0";
							REMARKS = "周期性任务";
						} else if ("4".equals(operType)) {
							OBJECT_TYPE = "3";
							REMARKS = "二次检查";
						} else {
							OBJECT_TYPE = "4";
							REMARKS = "电子档案库";
						}
						/**
						 * 更新端子表
						 */
						for (int i = 0; i < innerArray.size(); i++) {
							JSONObject port = (JSONObject) innerArray.get(i);
							String eqpId_port = null == port.get("eqpId") ? ""
									: port.getString("eqpId");
							String eqpNo_port = null == port.get("eqpNo") ? ""
									: port.getString("eqpNo");
							String eqpName_port = null == port.get("eqpName") ? ""
									: port.getString("eqpName");
							String portId = port.getString("portId");
							String dtsj_id = port.getString("dtsj_id");
							String portNo = port.getString("portNo");
							String portName = port.getString("portName");
							String photoIds = port.getString("photoId");
							String reason = port.getString("reason");
							String isCheckOK = port.getString("isCheckOK");
							String glbm = null == port.get("glbm") ? "" : port
									.getString("glbm");
							String glmc = null == port.get("glmc") ? "" : port
									.getString("glmc");
							String isH = null == port.get("isH") ? "" : port
									.getString("isH");// 是否H光路，0不是，1是
							String type = null;
							String eqpTypeId_port = null == port
									.get("eqp_type_id") ? "" : port
									.getString("eqp_type_id");
							System.out.println(glbm.startsWith("F"));
							System.out.println("2530".equals(eqpTypeId_port));
							if (glbm.startsWith("F")
									&& "2530".equals(eqpTypeId_port)) {
								type = "1";// 装维

							} else {
								type = "0";// 综维
							}
							map.put("port_no", portNo);
							map.put("port_name", portName);
							map.put("descript", reason);
							map.put("isCheckOK", isCheckOK);
							/**
							 * 修改端子信息tb_cablecheck_dtsj,检查时间
							 */
							map.put("port_id", portId);
							checkOrderDao.updateDtdz(map);
							/**
							 * 任务详情表在创建工单时即创建记录，设备占一条记录，不同端子各占一条记录，变动N次占N次记录(
							 * 检查前的信息) 插入任务详情表(检查后的信息)
							 */
							int recordId = checkOrderDao.getRecordId();
							Map portMap = new HashMap();
							portMap.put("taskid", taskId);
							portMap.put("portNo", portNo);
							map.put("recordId", recordId);
							Map taskDetailMap = new HashMap();
							taskDetailMap.put("TASK_ID", taskId);
							taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
							taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
							taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
							taskDetailMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
							taskDetailMap.put("GLBM", glbm);
							taskDetailMap.put("GLMC", glmc);
							taskDetailMap.put("PORT_ID", portId);
							taskDetailMap.put("dtsj_id", dtsj_id);
							taskDetailMap.put("eqpId_port", "");
							taskDetailMap.put("eqpNo_port", "");
							taskDetailMap.put("eqpName_port", "");
							taskDetailMap.put("orderNo", "");
							taskDetailMap.put("orderMark", "");
							taskDetailMap.put("actionType", "");
							taskDetailMap.put("archive_time", "");
							checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
							/**
							 * 插入检查记录表
							 */
							String detail_id = taskDetailMap.get("DETAIL_ID")
									.toString();
							map.put("detail_id", detail_id);
							map.put("eqpId", eqpId_port);
							map.put("eqpNo", eqpNo_port);
							map.put("eqpName", eqpName_port);
							map.put("isH", isH);
							map.put("type", type);
							checkOrderDao.insertEqpRecord(map);
							/**
							 * 保存图片关系
							 */
							if (!"".equals(photoIds) && photoIds != null) {
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", taskId);
								photoMap.put("DETAIL_ID", detail_id);
								photoMap.put("OBJECT_ID", recordId);
								photoMap.put("REMARKS", REMARKS);
								photoMap.put("OBJECT_TYPE", OBJECT_TYPE);// 3为二次检查
								photoMap.put("RECORD_ID", recordId);
								String[] photos = photoIds.split(",");
								for (String photo : photos) {
									photoMap.put("PHOTO_ID", photo);
									checkOrderDao.insertPhotoRel(photoMap);
								}
							}
						}
						/**
						 * 更新任务表,检查完成时间
						 */
						Map taskMap = new HashMap();
						taskMap.put("task_id", taskId);
						taskMap.put("staffId", staffId);
						taskMap.put("remarks", remarks);
						taskMap.put("is_need_zg", "0");
						checkOrderDao.updateTask(taskMap);
						checkOrderDao.updateTaskTime(taskMap);// 实际检查完成时间
						checkOrderDao.updateLastUpdateTime(taskMap);
						map.put("statusId", "8");
						checkOrderDao.updateStatus(map);// 更新状态
						/**
						 * 增加工单流程流转信息,即回单
						 */
						map.put("oper_staff", staffId);
						// map.put("status", "7");
						map.put("status", "8");
						map.put("remark", "已归档");
						checkProcessDao.addProcess(map);
						/**
						 * 插入任务详情表
						 */
						int eqpRecordId = checkOrderDao.getRecordId();
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", taskId);
						taskDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
						taskDetailMap.put("CHECK_FLAG", 1);
						taskDetailMap.put("GLBM", "");
						taskDetailMap.put("GLMC", "");
						taskDetailMap.put("PORT_ID", "");
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
						/**
						 * 插入检查记录表
						 */
						map.put("recordId", eqpRecordId);
						String eqpdetail_id = taskDetailMap.get("DETAIL_ID")
								.toString();
						map.put("detail_id", eqpdetail_id);
						// 插入设备信息，置空端子信息字段
						map.put("port_no", "");
						map.put("port_id", "");
						map.put("isH", "");
						map.put("type", "");
						checkOrderDao.insertEqpRecord(map);
						/**
						 * 保存设备的照片信息
						 */

						if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
							Map photoMap = new HashMap();
							photoMap.put("TASK_ID", taskId);
							photoMap.put("OBJECT_ID", eqpRecordId);
							photoMap.put("DETAIL_ID", eqpdetail_id);
							photoMap.put("OBJECT_TYPE", OBJECT_TYPE);// 0，周期性任务，1：隐患上报工单，2,回单操作
							photoMap.put("REMARKS", REMARKS);
							photoMap.put("RECORD_ID", eqpRecordId);
							String[] photos = eqpPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}

						JSONArray addressArray = json
								.getJSONArray("allEqpAddress");
						for (int i = 0; i < addressArray.size(); i++) {
							int id = checkOrderDao.geteqpAddressId();
							JSONObject eqp = (JSONObject) addressArray.get(i);

							String eqpId_add = null == eqp.get("eqpId") ? ""
									: eqp.getString("eqpId");
							String eqpNo_add = null == eqp.get("eqpNo") ? ""
									: eqp.getString("eqpNo");
							String location_id = eqp.getString("locationId");
							String address_id = eqp.getString("addressId");
							String address_name = eqp.getString("addressName");
							String is_check_ok = eqp.getString("is_check_ok");
							String error_reason = null == eqp
									.get("error_reason") ? "" : eqp
									.getString("error_reason");

							Map adddressMap = new HashMap();
							adddressMap.put("id", id);
							adddressMap.put("phy_eqp_id", eqpId_add);
							adddressMap.put("phy_eqp_no", eqpNo_add);
							adddressMap.put("install_eqp_id", eqpId);
							adddressMap.put("location_id", location_id);
							adddressMap.put("address_id", address_id);
							adddressMap.put("address_name", address_name);
							adddressMap.put("is_check_ok", is_check_ok);
							adddressMap.put("error_reason", error_reason);
							adddressMap.put("task_id", taskId);
							adddressMap.put("create_staff", staffId);
							adddressMap.put("area_id", parentAreaId);
							adddressMap.put("son_area_id", eqpAreaId);
							checkOrderDao.insertEqpAddress(adddressMap);

						}
						/**
						 * TODO 需要整改
						 */
						if ("1".equals(is_bill)) {
							/**
							 * 插入任务表
							 */
							Map troubleTaskMap = new HashMap();
							troubleTaskMap.put("TASK_NO", eqpNo + "_"
									+ DateUtil.getDate("yyyyMMdd"));
							troubleTaskMap.put("TASK_NAME", eqpName + "_"
									+ DateUtil.getDate("yyyyMMdd"));
							troubleTaskMap.put("TASK_TYPE", 3);// 问题上报
							troubleTaskMap.put("STATUS_ID", 4);// 问题上报
							troubleTaskMap.put("INSPECTOR", staffId);
							troubleTaskMap.put("CREATE_STAFF", staffId);
							troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
							troubleTaskMap.put("AREA_ID", parentAreaId);
							troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用
																// 1不可用（不显示在待办列表）
							troubleTaskMap.put("REMARK", remarks);
							troubleTaskMap.put("INFO", info);
							troubleTaskMap.put("NO_EQPNO_FLAG", "0");// 无编码上报

							troubleTaskMap.put("OLD_TASK_ID", taskId);// 老的task_id
							troubleTaskMap.put("IS_NEED_ZG", "1");// 是否整改,1需要整改
							troubleTaskMap.put("SBID", eqpId);// 设备id
							troubleTaskMap.put("MAINTOR", "");// 设备id

							checkOrderDao.saveTroubleTask(troubleTaskMap);
							String newTaskId = troubleTaskMap.get("TASK_ID")
									.toString();
							logger.info("【需要整改添加一张新的工单taskId】" + newTaskId);
							/**
							 * 更新任务表
							 */
							Map oldTaskMap = new HashMap();
							oldTaskMap.put("task_id", taskId);
							oldTaskMap.put("remarks", remarks);
							oldTaskMap.put("staffId", staffId);
							oldTaskMap.put("is_need_zg", "1");// 原来的任务，需要整改
							checkOrderDao.updateTask(oldTaskMap);
							/**
							 * 端子信息
							 */
							if (innerArray.size() > 0 && innerArray != null) {
								for (int j = 0; j < innerArray.size(); j++) {
									JSONObject port = (JSONObject) innerArray
											.get(j);
									String eqpId_port = null == port
											.get("eqpId") ? "" : port
											.getString("eqpId");
									String eqpNo_port = null == port
											.get("eqpNo") ? "" : port
											.getString("eqpNo");
									String eqpName_port = null == port
											.get("eqpName") ? "" : port
											.getString("eqpName");
									String portId = port.getString("portId");
									String dtsj_id = port.getString("dtsj_id");

									String portNo = port.getString("portNo");
									String portName = port
											.getString("portName");
									String glbm = null == port.get("glbm") ? ""
											: port.getString("glbm");
									String glmc = null == port.get("glmc") ? ""
											: port.getString("glmc");
									String type = null;
									String eqpTypeId_port = null == port
											.get("eqp_type_id") ? "" : port
											.getString("eqp_type_id");
									if (glbm.startsWith("F")
											&& "2530".equals(eqpTypeId_port)) {
										type = "1";

									} else {
										type = "0";
									}
									String isH = null == port.get("isH") ? ""
											: port.getString("isH");// 是否H光路，0不是，1是
									/**
									 * 保存工单详细
									 */
									int portRecordId = checkOrderDao
											.getRecordId();
									Map taskDetailMap1 = new HashMap();
									taskDetailMap1.put("TASK_ID", newTaskId);
									taskDetailMap1.put("INSPECT_OBJECT_ID",
											portRecordId);
									taskDetailMap1.put("INSPECT_OBJECT_NO",
											portNo);
									taskDetailMap1
											.put("INSPECT_OBJECT_TYPE", 1);
									taskDetailMap1.put("CHECK_FLAG", "1");
									taskDetailMap1.put("GLBM", glbm);
									taskDetailMap1.put("GLMC", glmc);
									taskDetailMap1.put("PORT_ID", portId);
									taskDetailMap1.put("dtsj_id", dtsj_id);
									taskDetailMap1.put("eqpId_port", "");
									taskDetailMap1.put("eqpNo_port", "");
									taskDetailMap1.put("eqpName_port", "");
									taskDetailMap1.put("orderNo", "");
									taskDetailMap1.put("orderMark", "");
									taskDetailMap1.put("actionType", "");
									taskDetailMap1.put("archive_time", "");
									checkOrderDao
											.saveTroubleTaskDetail(taskDetailMap1);
									String detail_id1 = taskDetailMap1.get(
											"DETAIL_ID").toString();
									/**
									 * 保存上报来的检查端子记录
									 */
									String portPhotoIds = port
											.getString("photoId");
									String reason = port.getString("reason");
									String isCheckOK = port
											.getString("isCheckOK");
									Map recordMap = new HashMap();
									recordMap.put("recordId", portRecordId);
									recordMap.put("task_id", newTaskId);
									recordMap.put("detail_id", detail_id1);
									recordMap.put("eqpId", eqpId_port);
									recordMap.put("eqpAddress", "");
									recordMap.put("eqpNo", eqpNo_port);
									recordMap.put("staffId", staffId);
									recordMap.put("eqpName", eqpName_port);
									recordMap.put("remarks", remarks);
									recordMap.put("info", info);
									recordMap.put("longitude", longitude);
									recordMap.put("latitude", latitude);
									recordMap.put("comments", comments);
									recordMap.put("port_id", portId);
									recordMap.put("port_no", portNo);
									recordMap.put("port_name", portName);
									recordMap.put("info", info);
									recordMap.put("descript", reason);
									recordMap.put("isCheckOK", isCheckOK);
									recordMap.put("record_type", operType);// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
									recordMap.put("area_id", parentAreaId);
									recordMap.put("son_area_id", eqpAreaId);
									recordMap.put("isH", isH);
									recordMap.put("type", type);
									checkOrderDao.insertEqpRecord(recordMap);
									/**
									 * 保存端子照片关系
									 */
									Map photoMap = new HashMap();
									photoMap.put("TASK_ID", newTaskId);
									photoMap.put("DETAIL_ID", detail_id1);
									photoMap.put("OBJECT_ID", portRecordId);
									photoMap.put("REMARKS", "整改工单");
									photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
									photoMap.put("RECORD_ID", portRecordId);// 0，周期性任务，1：隐患上报工单，2,回单操作
									// photoMap.put("REMARKS", photo);
									if (!"".equals(portPhotoIds)) {
										String[] photos = portPhotoIds
												.split(",");
										for (String photo : photos) {
											photoMap.put("PHOTO_ID", photo);
											checkOrderDao
													.insertPhotoRel(photoMap);
										}
									}

								}
							}

							int eqp_RecordId = checkOrderDao.getRecordId();
							/**
							 * 保存工单详细
							 */
							Map taskDetailMap1 = new HashMap();
							taskDetailMap1.put("TASK_ID", newTaskId);
							taskDetailMap1.put("INSPECT_OBJECT_ID",
									eqp_RecordId);
							taskDetailMap1.put("INSPECT_OBJECT_NO", eqpNo);
							taskDetailMap1.put("INSPECT_OBJECT_TYPE", 0);
							taskDetailMap1.put("CHECK_FLAG", "1");
							taskDetailMap1.put("GLBM", "");
							taskDetailMap1.put("GLMC", "");
							taskDetailMap1.put("PORT_ID", "");
							taskDetailMap1.put("dtsj_id", "");
							taskDetailMap1.put("eqpId_port", "");
							taskDetailMap1.put("eqpNo_port", "");
							taskDetailMap1.put("eqpName_port", "");
							taskDetailMap1.put("orderNo", "");
							taskDetailMap1.put("orderMark", "");
							taskDetailMap1.put("actionType", "");
							taskDetailMap1.put("archive_time", "");
							checkOrderDao.saveTroubleTaskDetail(taskDetailMap1);

							String detail_id1 = taskDetailMap1.get("DETAIL_ID")
									.toString();
							/**
							 * 保存上报来的设备记录，端子信息为空
							 */
							Map recordMap = new HashMap();
							recordMap.put("recordId", eqp_RecordId);
							recordMap.put("task_id", newTaskId);
							recordMap.put("detail_id", detail_id1);
							recordMap.put("eqpId", eqpId);
							recordMap.put("eqpAddress", eqpAddress);
							recordMap.put("eqpNo", eqpNo);
							recordMap.put("staffId", staffId);
							recordMap.put("eqpName", eqpName);
							recordMap.put("remarks", remarks);
							recordMap.put("info", info);
							recordMap.put("longitude", longitude);
							recordMap.put("latitude", latitude);
							recordMap.put("comments", comments);
							recordMap.put("port_id", "");
							recordMap.put("port_no", "");
							recordMap.put("port_name", "");
							recordMap.put("info", info);
							recordMap.put("descript", "");
							recordMap.put("isCheckOK", "1".equals(is_bill) ? 1
									: 0);// 需要整改说明现场检查不通过，有问题
							recordMap.put("record_type", 0);
							recordMap.put("area_id", parentAreaId);
							recordMap.put("son_area_id", eqpAreaId);
							recordMap.put("isH", "");
							recordMap.put("type", "");
							checkOrderDao.insertEqpRecord(recordMap);
							/**
							 * 插入流程环节
							 */
							Map processMap = new HashMap();
							processMap.put("task_id", newTaskId);
							processMap.put("oper_staff", staffId);
							processMap.put("status", 4);
							processMap.put("remark", "新发起工单");
							checkProcessDao.addProcess(processMap);

							if (!"".equals(eqpPhotoIds)) {
								// 保存设备的照片关系
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", newTaskId);
								photoMap.put("OBJECT_ID", eqp_RecordId);
								photoMap.put("DETAIL_ID", detail_id1);
								photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
								photoMap.put("REMARKS", "新发起工单");
								photoMap.put("RECORD_ID", eqp_RecordId);
								String[] photos = eqpPhotoIds.split(",");
								for (String photo : photos) {
									photoMap.put("PHOTO_ID", photo);
									checkOrderDao.insertPhotoRel(photoMap);
								}
							}
							String auditor = checkOrderDao.getAuitor(eqpAreaId);
							String contractor = checkOrderDao
									.getContractor(identification);
							String maintor = null;

							Map<String, Object> params = new HashMap<String, Object>();
							params.put("TASK_ID", newTaskId);
							params.put("STAFF_ID", staffId);
							params.put("auditor", auditor);

							params.put("STATUS_ID", 6);
							params.put("COMPLETE_TIME", "");
							if (!"".equals(constructor) && null != constructor) {
								maintor = checkOrderDao
										.getConstructorId(constructor);
							} else if (!"".equals(contractor)
									&& null != contractor) {
								maintor = contractor;

							} else {
								maintor = auditor;

							}
							params.put("MAINTOR", maintor);
							params.put("REFORM_DEMAND", "");
							Map<String, Object> flowParams = new HashMap<String, Object>();
							/**
							 * 更新审核员
							 */
							cableMyTaskDao.updateAuditor(params);
							flowParams.put("oper_staff", staffId);
							flowParams.put("task_id", newTaskId);
							flowParams.put("status", 6);
							flowParams.put("remark", "自动派发工单");
							checkProcessDao.addProcess(flowParams);

						}
					}
					/**
					 * TODO 1、有端子检查的隐患上报(一键反馈有问题端子上报，不可预告抽查有问题端子上报)
					 * 3、无设备编码上报(一键反馈，无设备编码上报)
					 */
					if ("3".equals(operType) || "1".equals(operType)) {

						// 保存先工单
						Map troubleTaskMap = new HashMap();
						troubleTaskMap.put("TASK_NO",
								"3".equals(operType) ? eqpAddress : eqpNo + "_"
										+ DateUtil.getDate("yyyyMMdd"));
						troubleTaskMap.put("TASK_NAME",
								"3".equals(operType) ? eqpAddress : eqpName
										+ "_" + DateUtil.getDate("yyyyMMdd"));
						troubleTaskMap.put("TASK_TYPE", data_source);// 隐患上报
						troubleTaskMap.put("STATUS_ID", "0".equals(is_bill) ? 8
								: 4);// 需要整改为隐患上报，无需整改直接归档
						troubleTaskMap.put("INSPECTOR", staffId);
						troubleTaskMap.put("CREATE_STAFF", staffId);
						troubleTaskMap.put("SON_AREA_ID",
								"1".equals(operType) ? eqpAreaId : sonAreaId);
						troubleTaskMap.put("AREA_ID",
								"1".equals(operType) ? parentAreaId : areaId);
						troubleTaskMap.put("ENABLE", "0".equals(is_bill) ? 1
								: 0);// 如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
						troubleTaskMap.put("REMARK", remarks);
						troubleTaskMap.put("INFO", info);
						troubleTaskMap.put("NO_EQPNO_FLAG",
								"3".equals(operType) ? 1 : 0);// 无编码上报
						troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
						troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
						troubleTaskMap.put("SBID", eqpId);// 设备id
						troubleTaskMap.put("COMPANY", "");// 设备id
						troubleTaskMap.put("MAINTOR", "");// 设备id

						checkOrderDao.saveTroubleTask(troubleTaskMap);
						String task_id = troubleTaskMap.get("TASK_ID")
								.toString();

						JSONArray addressArray = json
								.getJSONArray("allEqpAddress");

						for (int i = 0; i < addressArray.size(); i++) {
							int id = checkOrderDao.geteqpAddressId();
							JSONObject eqp = (JSONObject) addressArray.get(i);

							String eqpId_add = null == eqp.get("eqpId") ? ""
									: eqp.getString("eqpId");
							String eqpNo_add = null == eqp.get("eqpNo") ? ""
									: eqp.getString("eqpNo");
							String location_id = eqp.getString("locationId");
							String address_id = eqp.getString("addressId");
							String address_name = eqp.getString("addressName");
							String is_check_ok = eqp.getString("is_check_ok");
							String error_reason = null == eqp
									.get("error_reason") ? "" : eqp
									.getString("error_reason");

							Map adddressMap = new HashMap();
							adddressMap.put("id", id);
							adddressMap.put("phy_eqp_id", eqpId_add);
							adddressMap.put("phy_eqp_no", eqpNo_add);
							adddressMap.put("install_eqp_id", eqpId);
							adddressMap.put("location_id", location_id);
							adddressMap.put("address_id", address_id);
							adddressMap.put("address_name", address_name);
							adddressMap.put("is_check_ok", is_check_ok);
							adddressMap.put("error_reason", error_reason);
							adddressMap.put("task_id", task_id);
							adddressMap.put("create_staff", staffId);
							adddressMap.put("area_id", parentAreaId);
							adddressMap.put("son_area_id", eqpAreaId);
							checkOrderDao.insertEqpAddress(adddressMap);
						}

						// 如果是隐患上报，有端子上报
						if ("1".equals(operType)) {
							for (int j = 0; j < innerArray.size(); j++) {
								JSONObject port = (JSONObject) innerArray
										.get(j);
								String eqpId_port = null == port.get("eqpId") ? ""
										: port.getString("eqpId");
								String eqpNo_port = null == port.get("eqpNo") ? ""
										: port.getString("eqpNo");
								String eqpName_port = null == port
										.get("eqpName") ? "" : port
										.getString("eqpName");
								String portId = port.getString("portId");
								String dtsj_id = port.getString("dtsj_id");

								String portNo = null == port.get("portNo") ? ""
										: port.getString("portNo");
								String portName = null == port.get("portName") ? ""
										: port.getString("portName");
								String portPhotoIds = port.getString("photoId");
								String reason = port.getString("reason");
								String isCheckOK = port.getString("isCheckOK");
								String glbm = null == port.get("glbm") ? ""
										: port.getString("glbm");
								String glmc = null == port.get("glmc") ? ""
										: port.getString("glmc");
								String isH = null == port.get("isH") ? ""
										: port.getString("isH");// 是否H光路，0不是，1是
								String type = null;
								String eqpTypeId_port = null == port
										.get("eqp_type_id") ? "" : port
										.getString("eqp_type_id");
								if (glbm.startsWith("F")
										&& "2530".equals(eqpTypeId_port)) {
									type = "1";// E综维 F装维

								} else {
									type = "0";
								}
								// 保存工单详细
								int recordId = checkOrderDao.getRecordId();
								Map taskDetailMap = new HashMap();
								taskDetailMap.put("TASK_ID", task_id);
								taskDetailMap
										.put("INSPECT_OBJECT_ID", recordId);
								taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
								taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
								taskDetailMap.put("CHECK_FLAG", "1");
								taskDetailMap.put("GLBM", glbm);
								taskDetailMap.put("GLMC", glmc);
								taskDetailMap.put("PORT_ID", portId);
								taskDetailMap.put("dtsj_id", dtsj_id);
								taskDetailMap.put("eqpId_port", "");
								taskDetailMap.put("eqpNo_port", "");
								taskDetailMap.put("eqpName_port", "");
								taskDetailMap.put("orderNo", "");
								taskDetailMap.put("orderMark", "");
								taskDetailMap.put("actionType", "");
								taskDetailMap.put("archive_time", "");
								checkOrderDao
										.saveTroubleTaskDetail(taskDetailMap);
								// 保存上报来的检查端子记录
								String detail_id = taskDetailMap.get(
										"DETAIL_ID").toString();
								Map recordMap = new HashMap();
								recordMap.put("recordId", recordId);
								recordMap.put("task_id", task_id);
								recordMap.put("detail_id", detail_id);
								recordMap.put("eqpId", eqpId_port);
								recordMap.put("eqpAddress", eqpAddress);
								recordMap.put("eqpNo", eqpNo_port);
								recordMap.put("staffId", staffId);
								recordMap.put("eqpName", eqpName_port);
								recordMap.put("info", info);
								recordMap.put("longitude", longitude);
								recordMap.put("latitude", latitude);
								recordMap.put("comments", comments);
								recordMap.put("port_id", portId);
								recordMap.put("port_no", portNo);
								recordMap.put("port_name", portName);
								recordMap.put("remark", remarks);
								recordMap.put("info", info);
								recordMap.put("descript", reason);
								recordMap.put("isCheckOK", isCheckOK);
								recordMap.put("record_type", "1");// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
								recordMap.put("area_id", parentAreaId);
								recordMap.put("son_area_id", eqpAreaId);
								recordMap.put("isH", isH);
								recordMap.put("type", type);
								checkOrderDao.insertEqpRecord(recordMap);
								// 保存端子照片关系
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", task_id);
								photoMap.put("DETAIL_ID", detail_id);
								photoMap.put("OBJECT_ID", recordId);
								photoMap.put("REMARKS", "隐患上报");
								photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
								photoMap.put("RECORD_ID", recordId);
								if (!"".equals(portPhotoIds)) {
									String[] photos = portPhotoIds.split(",");
									for (String photo : photos) {
										photoMap.put("PHOTO_ID", photo);
										checkOrderDao.insertPhotoRel(photoMap);
									}
								}

							}
						}
						// 保存设备信息
						// 先获取下一个设备检查记录的ID
						int recordId = checkOrderDao.getRecordId();
						// 保存工单详细
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", task_id);
						taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
						taskDetailMap.put("CHECK_FLAG", "1");
						taskDetailMap.put("GLBM", "");
						taskDetailMap.put("GLMC", "");
						taskDetailMap.put("PORT_ID", "");
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

						// 保存上报来的设备记录，端子信息为空
						String detail_id = taskDetailMap.get("DETAIL_ID")
								.toString();
						Map recordMap = new HashMap();
						recordMap.put("recordId", recordId);
						recordMap.put("task_id", task_id);
						recordMap.put("detail_id", detail_id);
						recordMap.put("eqpId", eqpId);
						recordMap.put("eqpAddress", eqpAddress);
						recordMap.put("eqpNo",
								"3".equals(operType) ? eqpAddress : eqpNo);
						recordMap.put("staffId", staffId);
						recordMap.put("eqpName",
								"3".equals(operType) ? eqpAddress : eqpName);
						recordMap.put("remarks", remarks);
						recordMap.put("info", info);
						recordMap.put("longitude", longitude);
						recordMap.put("latitude", latitude);
						recordMap.put("comments", comments);
						recordMap.put("port_id", "");
						recordMap.put("port_no", "");
						recordMap.put("port_name", "");
						recordMap.put("remark", remarks);
						recordMap.put("info", info);
						recordMap.put("descript", "");
						recordMap.put("isCheckOK", "1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
						recordMap.put("record_type", "3".equals(operType) ? 3
								: 1);
						recordMap.put("area_id",
								"1".equals(operType) ? parentAreaId : areaId);
						recordMap.put("son_area_id",
								"1".equals(operType) ? eqpAreaId : sonAreaId);
						recordMap.put("isH", "");
						recordMap.put("type", "");
						checkOrderDao.insertEqpRecord(recordMap);
						checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
						checkOrderDao.updateLastUpdateTime(recordMap);
						// 插入流程环节
						Map processMap = new HashMap();
						processMap.put("task_id", task_id);
						processMap.put("oper_staff", staffId);
						if ("1".equals(is_bill)) {
							processMap.put("status", 4);
							processMap.put("remark", "隐患上报");
						} else {
							processMap.put("status", 8);
							processMap.put("remark", "隐患上报无需整改");
						}
						checkProcessDao.addProcess(processMap);
						// 保存设备的照片关系
						if (!"".equals(eqpPhotoIds)) {
							Map photoMap = new HashMap();
							photoMap.put("TASK_ID", task_id);
							photoMap.put("OBJECT_ID", recordId);
							photoMap.put("DETAIL_ID", detail_id);
							photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
							photoMap.put("REMARKS", "隐患上报");
							photoMap.put("RECORD_ID", recordId);
							String[] photos = eqpPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}

						if ("1".equals(is_bill)) {

							String auditor = checkOrderDao.getAuitor(eqpAreaId);
							String contractor = checkOrderDao
									.getContractor(identification);
							String maintor = null;

							Map<String, Object> params = new HashMap<String, Object>();
							params.put("TASK_ID", task_id);
							params.put("STAFF_ID", staffId);
							params.put("auditor", auditor);

							params.put("STATUS_ID", 6);
							params.put("COMPLETE_TIME", "");
							if (!"".equals(constructor) && null != constructor) {
								maintor = checkOrderDao
										.getConstructorId(constructor);
							} else if (!"".equals(contractor)
									&& null != contractor) {
								maintor = contractor;

							} else {
								maintor = auditor;

							}
							params.put("MAINTOR", maintor);
							params.put("REFORM_DEMAND", "");
							Map<String, Object> flowParams = new HashMap<String, Object>();
							/**
							 * 更新审核员
							 */
							cableMyTaskDao.updateAuditor(params);
							flowParams.put("oper_staff", staffId);
							flowParams.put("task_id", task_id);
							flowParams.put("status", 6);
							flowParams.put("remark", "自动派发工单");
							checkProcessDao.addProcess(flowParams);

						}

					}

					/**
					 * TODO FTTH拆机工单
					 */

					if ("7".equals(operType) || "8".equals(operType)) {

						// 保存先工单
						Map troubleTaskMap = new HashMap();
						troubleTaskMap.put("TASK_NO",
								eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
						troubleTaskMap.put("TASK_NAME", eqpName + "_"
								+ DateUtil.getDate("yyyyMMdd"));
						troubleTaskMap.put("TASK_TYPE", operType);// FTTH
						troubleTaskMap.put("STATUS_ID", "0".equals(is_bill) ? 8
								: 4);// 需要整改为隐患上报，无需整改直接归档
						troubleTaskMap.put("INSPECTOR", staffId);
						troubleTaskMap.put("CREATE_STAFF", staffId);
						troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
						troubleTaskMap.put("AREA_ID", parentAreaId);
						troubleTaskMap.put("ENABLE", "0".equals(is_bill) ? 1
								: 0);// 如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
						troubleTaskMap.put("REMARK", remarks);
						troubleTaskMap.put("INFO", info);
						troubleTaskMap.put("NO_EQPNO_FLAG", 0);// 无编码上报
						troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
						troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
						troubleTaskMap.put("SBID", eqpId);// 设备id
						troubleTaskMap.put("COMPANY", "");// 设备id
						troubleTaskMap.put("MAINTOR", "");// 设备id

						checkOrderDao.saveTroubleTask(troubleTaskMap);
						String task_id = troubleTaskMap.get("TASK_ID")
								.toString();

						JSONArray addressArray = json
								.getJSONArray("allEqpAddress");

						for (int i = 0; i < addressArray.size(); i++) {
							int id = checkOrderDao.geteqpAddressId();
							JSONObject eqp = (JSONObject) addressArray.get(i);

							String eqpId_add = null == eqp.get("eqpId") ? ""
									: eqp.getString("eqpId");
							String eqpNo_add = null == eqp.get("eqpNo") ? ""
									: eqp.getString("eqpNo");
							String location_id = eqp.getString("locationId");
							String address_id = eqp.getString("addressId");
							String address_name = eqp.getString("addressName");
							String is_check_ok = eqp.getString("is_check_ok");
							String error_reason = null == eqp
									.get("error_reason") ? "" : eqp
									.getString("error_reason");

							Map adddressMap = new HashMap();
							adddressMap.put("id", id);
							adddressMap.put("phy_eqp_id", eqpId_add);
							adddressMap.put("phy_eqp_no", eqpNo_add);
							adddressMap.put("install_eqp_id", eqpId);
							adddressMap.put("location_id", location_id);
							adddressMap.put("address_id", address_id);
							adddressMap.put("address_name", address_name);
							adddressMap.put("is_check_ok", is_check_ok);
							adddressMap.put("error_reason", error_reason);
							adddressMap.put("task_id", task_id);
							adddressMap.put("create_staff", staffId);
							adddressMap.put("area_id", parentAreaId);
							adddressMap.put("son_area_id", eqpAreaId);
							checkOrderDao.insertEqpAddress(adddressMap);
						}

						for (int j = 0; j < innerArray.size(); j++) {
							JSONObject port = (JSONObject) innerArray.get(j);
							String eqpId_port = null == port.get("eqpId") ? ""
									: port.getString("eqpId");
							String eqpNo_port = null == port.get("eqpNo") ? ""
									: port.getString("eqpNo");
							String eqpName_port = null == port.get("eqpName") ? ""
									: port.getString("eqpName");
							String portId = port.getString("portId");
							String dtsj_id = port.getString("dtsj_id");

							String portNo = null == port.get("portNo") ? ""
									: port.getString("portNo");
							String portName = null == port.get("portName") ? ""
									: port.getString("portName");
							String portPhotoIds = port.getString("photoId");
							String reason = port.getString("reason");
							String isCheckOK = port.getString("isCheckOK");
							String glbm = null == port.get("glbm") ? "" : port
									.getString("glbm");
							String glmc = null == port.get("glmc") ? "" : port
									.getString("glmc");
							String isH = null == port.get("isH") ? "" : port
									.getString("isH");// 是否H光路，0不是，1是
							String type = null;
							String eqpTypeId_port = null == port
									.get("eqp_type_id") ? "" : port
									.getString("eqp_type_id");
							if (glbm.startsWith("F")
									&& "2530".equals(eqpTypeId_port)) {
								type = "1";// E综维 F装维

							} else {
								type = "0";
							}
							// 保存工单详细
							int recordId = checkOrderDao.getRecordId();
							Map taskDetailMap = new HashMap();
							taskDetailMap.put("TASK_ID", task_id);
							taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
							taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
							taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
							taskDetailMap.put("CHECK_FLAG", "1");
							taskDetailMap.put("GLBM", glbm);
							taskDetailMap.put("GLMC", glmc);
							taskDetailMap.put("PORT_ID", portId);
							taskDetailMap.put("dtsj_id", dtsj_id);
							taskDetailMap.put("eqpId_port", "");
							taskDetailMap.put("eqpNo_port", "");
							taskDetailMap.put("eqpName_port", "");
							taskDetailMap.put("orderNo", "");
							taskDetailMap.put("orderMark", "");
							taskDetailMap.put("actionType", "");
							taskDetailMap.put("archive_time", "");
							checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
							// 保存上报来的检查端子记录
							String detail_id = taskDetailMap.get("DETAIL_ID")
									.toString();
							Map recordMap = new HashMap();
							recordMap.put("recordId", recordId);
							recordMap.put("task_id", task_id);
							recordMap.put("detail_id", detail_id);
							recordMap.put("eqpId", eqpId_port);
							recordMap.put("eqpAddress", eqpAddress);
							recordMap.put("eqpNo", eqpNo_port);
							recordMap.put("staffId", staffId);
							recordMap.put("eqpName", eqpName_port);
							recordMap.put("info", info);
							recordMap.put("longitude", longitude);
							recordMap.put("latitude", latitude);
							recordMap.put("comments", comments);
							recordMap.put("port_id", portId);
							recordMap.put("port_no", portNo);
							recordMap.put("port_name", portName);
							recordMap.put("remark", remarks);
							recordMap.put("info", info);
							recordMap.put("descript", reason);
							recordMap.put("isCheckOK", isCheckOK);
							recordMap.put("record_type", operType);// FTTH拆机
							recordMap.put("area_id", parentAreaId);
							recordMap.put("son_area_id", eqpAreaId);
							recordMap.put("isH", isH);
							recordMap.put("type", type);
							checkOrderDao.insertEqpRecord(recordMap);
							// 保存端子照片关系
							Map photoMap = new HashMap();
							photoMap.put("TASK_ID", task_id);
							photoMap.put("DETAIL_ID", detail_id);
							photoMap.put("OBJECT_ID", recordId);
							photoMap.put("REMARKS", "隐患上报");
							photoMap.put("OBJECT_TYPE", 4);// 0，周期性任务，1：隐患上报工单，2,回单操作
							photoMap.put("RECORD_ID", recordId);
							if (!"".equals(portPhotoIds)) {
								String[] photos = portPhotoIds.split(",");
								for (String photo : photos) {
									photoMap.put("PHOTO_ID", photo);
									checkOrderDao.insertPhotoRel(photoMap);
								}
							}

						}

						// 保存设备信息
						// 先获取下一个设备检查记录的ID
						int recordId = checkOrderDao.getRecordId();
						// 保存工单详细
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", task_id);
						taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
						taskDetailMap.put("CHECK_FLAG", "");
						taskDetailMap.put("GLBM", "");
						taskDetailMap.put("GLMC", "");
						taskDetailMap.put("PORT_ID", "");
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

						// 保存上报来的设备记录，端子信息为空
						String detail_id = taskDetailMap.get("DETAIL_ID")
								.toString();
						Map recordMap = new HashMap();
						recordMap.put("recordId", recordId);
						recordMap.put("task_id", task_id);
						recordMap.put("detail_id", detail_id);
						recordMap.put("eqpId", eqpId);
						recordMap.put("eqpAddress", eqpAddress);
						recordMap.put("eqpNo", eqpNo);
						recordMap.put("staffId", staffId);
						recordMap.put("eqpName", eqpName);
						recordMap.put("remarks", remarks);
						recordMap.put("info", info);
						recordMap.put("longitude", longitude);
						recordMap.put("latitude", latitude);
						recordMap.put("comments", comments);
						recordMap.put("port_id", "");
						recordMap.put("port_no", "");
						recordMap.put("port_name", "");
						recordMap.put("remark", remarks);
						recordMap.put("info", info);
						recordMap.put("descript", "");
						recordMap.put("isCheckOK", "1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
						recordMap.put("record_type", operType);
						recordMap.put("area_id", parentAreaId);
						recordMap.put("son_area_id", eqpAreaId);
						recordMap.put("isH", "");
						recordMap.put("type", "");
						checkOrderDao.insertEqpRecord(recordMap);
						checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
						checkOrderDao.updateLastUpdateTime(recordMap);
						// 插入流程环节
						Map processMap = new HashMap();
						processMap.put("task_id", task_id);
						processMap.put("oper_staff", staffId);
						if ("1".equals(is_bill)) {
							processMap.put("status", 4);
							processMap.put("remark", "隐患上报");
						} else {
							processMap.put("status", 8);
							processMap.put("remark", "隐患上报无需整改");
						}
						checkProcessDao.addProcess(processMap);
						// 保存设备的照片关系
						if (!"".equals(eqpPhotoIds)) {
							Map photoMap = new HashMap();
							photoMap.put("TASK_ID", task_id);
							photoMap.put("OBJECT_ID", recordId);
							photoMap.put("DETAIL_ID", detail_id);
							photoMap.put("OBJECT_TYPE", 4);// 0，周期性任务，1：隐患上报工单，2,回单操作
							photoMap.put("REMARKS", "隐患上报");
							photoMap.put("RECORD_ID", recordId);
							String[] photos = eqpPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}

						if ("1".equals(is_bill)) {

							String auditor = checkOrderDao.getAuitor(eqpAreaId);
							String contractor = checkOrderDao
									.getContractor(identification);
							String maintor = null;

							Map<String, Object> params = new HashMap<String, Object>();
							params.put("TASK_ID", task_id);
							params.put("STAFF_ID", staffId);
							params.put("auditor", auditor);

							params.put("STATUS_ID", 6);
							params.put("COMPLETE_TIME", "");
							if (!"".equals(constructor) && null != constructor) {
								maintor = checkOrderDao
										.getConstructorId(constructor);
							} else if (!"".equals(contractor)
									&& null != contractor) {
								maintor = contractor;

							} else {
								maintor = auditor;

							}
							params.put("MAINTOR", maintor);
							params.put("REFORM_DEMAND", "");
							Map<String, Object> flowParams = new HashMap<String, Object>();
							/**
							 * 更新审核员
							 */
							cableMyTaskDao.updateAuditor(params);
							flowParams.put("oper_staff", staffId);
							flowParams.put("task_id", task_id);
							flowParams.put("status", 6);
							flowParams.put("remark", "自动派发工单");
							checkProcessDao.addProcess(flowParams);

						}

					}

					// 保证每次检查完设备之后，将检查时间插入到设备表
					Map map = new HashMap();
					map.put("areaId", parentAreaId);
					map.put("sonAreaId", eqpAreaId);
					map.put("eqpId", eqpId);
					map.put("eqpNo", eqpNo);
					checkOrderDao.updateCheckCompleteTime(map);
				}
			}
			/**
			 * TODO 2、回单操作(我的检查任务-->整改工单回单)
			 */
			if ("2".equals(operType)) {
				Map map = new HashMap();
				map.put("task_id", taskId);
				String statusIdStr = checkOrderDao.queryTaskByTaskId(map)
						.get("STATUS_ID").toString();
				int curStatusId = Integer.valueOf(statusIdStr);
				if (curStatusId == 7) {
					result.put("result", "001");
					result.put("desc", "已回单");
				} else {
					Map paramMap = new HashMap();
					paramMap.put("rwmxId", rwmxId);
					// 插入流程环节
					paramMap.put("task_id", taskId);
					paramMap.put("remark", "回单，待审核");
					paramMap.put("status", "7");
					paramMap.put("oper_staff", staffId);
					checkProcessDao.addProcess(paramMap);
					// 更新任务表
					Map taskMap = new HashMap();
					taskMap.put("staffId", staffId);
					taskMap.put("statusId", "7");
					taskMap.put("task_id", taskId);
					checkOrderDao.updateTaskBack(taskMap);
					checkOrderDao.updateLastUpdateTime(taskMap);
					/**
					 * 设备检查记录表中记录整改记录 注意record_type
					 */
					paramMap.put("eqpId", eqpId);
					paramMap.put("eqpNo", eqpNo);
					paramMap.put("eqpName", eqpName);
					paramMap.put("longitude", longitude);
					paramMap.put("latitude", latitude);
					paramMap.put("comments", comments);
					paramMap.put("eqpAddress", eqpAddress);
					paramMap.put("remarks", remarks);
					paramMap.put("info", info);
					paramMap.put("staffId", staffId);
					paramMap.put("record_type", "2");
					paramMap.put("area_id", areaId);
					paramMap.put("son_area_id", sonAreaId);
					paramMap.put("task_id", taskId);
					for (int i = 0; i < innerArray.size(); i++) {
						JSONObject port = (JSONObject) innerArray.get(i);
						String eqpId_port = null == port.get("eqpId") ? ""
								: port.getString("eqpId");
						String eqpNo_port = null == port.get("eqpNo") ? ""
								: port.getString("eqpNo");
						String eqpName_port = null == port.get("eqpName") ? ""
								: port.getString("eqpName");
						String portId = port.getString("portId");
						String portNo = port.getString("portNo");
						String portName = port.getString("portName");
						String portPhotoIds = null == port.get("photoId") ? ""
								: port.getString("photoId");
						String reason = port.getString("reason");
						String isCheckOK = port.getString("isCheckOK");
						String glbm = null == port.get("glbm") ? "" : port
								.getString("glbm");
						String glmc = null == port.get("glmc") ? "" : port
								.getString("glmc");
						String type = null == port.get("type") ? "" : port
								.getString("type");
						String isH = null == port.get("isH") ? "" : port
								.getString("isH");// 是否H光路，0不是，1是
						String port_info = null == port.get("port_info") ? ""
								: port.getString("port_info");
						int recordId = checkOrderDao.getRecordId();
						paramMap.put("recordId", recordId);
						paramMap.put("port_id", portId);
						paramMap.put("port_no", portNo);
						paramMap.put("port_name", portName);
						paramMap.put("descript", reason);// 端子情况
						paramMap.put("isCheckOK", isCheckOK);
						// 插入详情表
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", taskId);
						taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
						taskDetailMap.put("CHECK_FLAG", "2");// 回单
						taskDetailMap.put("GLBM", glbm);
						taskDetailMap.put("GLMC", glmc);
						taskDetailMap.put("PORT_ID", portId);
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
						// 插入记录表
						String detail_id = taskDetailMap.get("DETAIL_ID")
								.toString();
						paramMap.put("detail_id", detail_id);
						paramMap.put("eqpId", eqpId_port);
						paramMap.put("eqpNo", eqpNo_port);
						paramMap.put("eqpName", eqpName_port);
						paramMap.put("isH", isH);
						paramMap.put("port_info", port_info);
						paramMap.put("type", type);
						checkOrderDao.insertEqpRecord(paramMap);
						// 保存端子照片关系
						Map photoMap = new HashMap();
						photoMap.put("TASK_ID", taskId);
						photoMap.put("DETAIL_ID", "");
						photoMap.put("OBJECT_ID", recordId);
						photoMap.put("REMARKS", "已回单，待审核");
						photoMap.put("OBJECT_TYPE", 2);// 0，周期性任务，1：隐患上报工单，2,回单操作
						photoMap.put("RECORD_ID", recordId);
						if (!"".equals(portPhotoIds)) {
							String[] photos = portPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}
					}
					// 保存设备信息
					int recordId = checkOrderDao.getRecordId();
					// 插入详情表
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", taskId);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
					taskDetailMap.put("CHECK_FLAG", "2");// 回单
					taskDetailMap.put("GLBM", "");
					taskDetailMap.put("GLMC", "");
					taskDetailMap.put("PORT_ID", "");
					taskDetailMap.put("dtsj_id", "");
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					// 插入记录表
					String detail_id = taskDetailMap.get("DETAIL_ID")
							.toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", taskId);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName);
					recordMap.put("remarks", remarks);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", "");
					recordMap.put("port_no", "");
					recordMap.put("port_name", "");
					recordMap.put("remark", remarks);
					recordMap.put("descript", "");
					recordMap.put("isCheckOK", "0");
					recordMap.put("record_type", "2");
					recordMap.put("area_id", areaId);
					recordMap.put("son_area_id", sonAreaId);
					recordMap.put("isH", "");
					recordMap.put("type", "");
					checkOrderDao.insertEqpRecord(recordMap);
					// 保存设备照片关系
					if (!"".equals(eqpPhotoIds)) {
						Map photoMap = new HashMap();
						photoMap.put("TASK_ID", taskId);
						photoMap.put("OBJECT_ID", recordId);
						photoMap.put("DETAIL_ID", "");
						photoMap.put("OBJECT_TYPE", 2);// 0，周期性任务，1：隐患上报工单，2,回单操作
						photoMap.put("REMARKS", "回单");
						photoMap.put("RECORD_ID", recordId);
						String[] photos = eqpPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}
				}
				// 保证每次检查完设备之后，将检查时间插入到设备表
				Map map2 = new HashMap();
				map2.put("areaId", parentAreaId);
				map2.put("sonAreaId", eqpAreaId);
				map2.put("eqpId", eqpId);
				map2.put("eqpNo", eqpNo);
				checkOrderDao.updateCheckCompleteTime(map2);
			}

			/**
			 * TODO 2、回单操作(资源整改工单回单)
			 */
			if ("6".equals(operType)) {

				Map troubleTaskMap = new HashMap();
				troubleTaskMap.put("TASK_NO",
						eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_NAME",
						eqpName + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_TYPE", 6);// 隐患上报
				troubleTaskMap.put("STATUS_ID", "0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
				troubleTaskMap.put("INSPECTOR", staffId);
				troubleTaskMap.put("CREATE_STAFF", staffId);
				troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
				troubleTaskMap.put("AREA_ID", parentAreaId);
				troubleTaskMap.put("ENABLE", "0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
				troubleTaskMap.put("REMARK", remarks);
				troubleTaskMap.put("INFO", info);
				troubleTaskMap.put("NO_EQPNO_FLAG", 0);// 无编码上报
				troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
				troubleTaskMap.put("OLD_TASK_ID", taskId);// 老的task_id
				troubleTaskMap.put("SBID", eqpId);// 设备id
				troubleTaskMap.put("MAINTOR", "");// 设备id

				checkOrderDao.saveTroubleTask(troubleTaskMap);
				String task_id = troubleTaskMap.get("TASK_ID").toString();

				for (int i = 0; i < innerArray.size(); i++) {
					JSONObject port = (JSONObject) innerArray.get(i);
					String eqpId_port = null == port.get("eqpId") ? "" : port
							.getString("eqpId");
					String eqpNo_port = null == port.get("eqpNo") ? "" : port
							.getString("eqpNo");
					String eqpName_port = null == port.get("eqpName") ? ""
							: port.getString("eqpName");
					String portId = port.getString("portId");
					String portNo = port.getString("portNo");
					String portName = port.getString("portName");
					String portPhotoIds = null == port.get("photoId") ? ""
							: port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					String glbm = null == port.get("glbm") ? "" : port
							.getString("glbm");
					String glmc = null == port.get("glmc") ? "" : port
							.getString("glmc");
					String type = null == port.get("type") ? "" : port
							.getString("type");
					String isH = null == port.get("isH") ? "" : port
							.getString("isH");// 是否H光路，0不是，1是
					String port_info = null == port.get("port_info") ? ""
							: port.getString("port_info");

					// 保存工单详细
					int recordId = checkOrderDao.getRecordId();
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", "3");
					taskDetailMap.put("GLBM", glbm);
					taskDetailMap.put("GLMC", glmc);
					taskDetailMap.put("PORT_ID", portId);
					taskDetailMap.put("dtsj_id", "");
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					// 保存上报来的检查端子记录
					String detail_id = taskDetailMap.get("DETAIL_ID")
							.toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", task_id);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId_port);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo_port);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName_port);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", portId);
					recordMap.put("port_no", portNo);
					recordMap.put("port_name", portName);
					recordMap.put("remark", remarks);
					recordMap.put("info", info);
					recordMap.put("descript", port_info);
					recordMap.put("isCheckOK", isCheckOK);
					recordMap.put("record_type", "6");// 资源整改
					recordMap.put("area_id", parentAreaId);
					recordMap.put("son_area_id", eqpAreaId);
					recordMap.put("isH", isH);
					recordMap.put("type", type);
					checkOrderDao.insertEqpRecord(recordMap);
					// 保存端子照片关系
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID", recordId);
					if (!"".equals(portPhotoIds)) {
						String[] photos = portPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}

				}
				// 保存设备信息
				// 先获取下一个设备检查记录的ID
				int recordId = checkOrderDao.getRecordId();
				// 保存工单详细
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", task_id);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
				taskDetailMap.put("CHECK_FLAG", "3");// 资源整改
				taskDetailMap.put("GLBM", "");
				taskDetailMap.put("GLMC", "");
				taskDetailMap.put("PORT_ID", "");
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

				// 保存上报来的设备记录，端子信息为空
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				Map recordMap = new HashMap();
				recordMap.put("recordId", recordId);
				recordMap.put("task_id", task_id);
				recordMap.put("detail_id", detail_id);
				recordMap.put("eqpId", eqpId);
				recordMap.put("eqpAddress", eqpAddress);
				recordMap.put("eqpNo", eqpNo);
				recordMap.put("staffId", staffId);
				recordMap.put("eqpName", eqpName);
				recordMap.put("remarks", remarks);
				recordMap.put("info", info);
				recordMap.put("longitude", longitude);
				recordMap.put("latitude", latitude);
				recordMap.put("comments", comments);
				recordMap.put("port_id", "");
				recordMap.put("port_no", "");
				recordMap.put("port_name", "");
				recordMap.put("remark", remarks);
				recordMap.put("info", info);
				recordMap.put("descript", "");
				recordMap.put("isCheckOK", "1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
				recordMap.put("record_type", "6");
				recordMap.put("area_id", parentAreaId);
				recordMap.put("son_area_id", eqpAreaId);
				recordMap.put("isH", "");
				recordMap.put("type", "");
				checkOrderDao.insertEqpRecord(recordMap);
				checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
				checkOrderDao.updateLastUpdateTime(recordMap);
				// 插入流程环节
				Map processMap = new HashMap();
				processMap.put("task_id", task_id);
				processMap.put("oper_staff", staffId);
				if ("1".equals(is_bill)) {
					processMap.put("status", 4);
					processMap.put("remark", "隐患上报");
				} else {
					processMap.put("status", 8);
					processMap.put("remark", "隐患上报无需整改");
				}
				checkProcessDao.addProcess(processMap);
				// 保存设备的照片关系
				if (!"".equals(eqpPhotoIds)) {
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_TYPE", 6);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("RECORD_ID", recordId);
					String[] photos = eqpPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}

				if ("1".equals(is_bill)) {

					String auditor = checkOrderDao.getAuitor(eqpAreaId);
					String contractor = checkOrderDao
							.getContractor(identification);
					String maintor = null;

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("TASK_ID", task_id);
					params.put("STAFF_ID", staffId);
					params.put("auditor", auditor);

					params.put("STATUS_ID", 6);
					params.put("COMPLETE_TIME", "");
					if (!"".equals(constructor) && null != constructor) {
						maintor = checkOrderDao.getConstructorId(constructor);
					} else if (!"".equals(contractor) && null != contractor) {
						maintor = contractor;

					} else {
						maintor = auditor;

					}
					params.put("MAINTOR", maintor);
					params.put("REFORM_DEMAND", "");
					Map<String, Object> flowParams = new HashMap<String, Object>();
					/**
					 * 更新审核员
					 */
					cableMyTaskDao.updateAuditor(params);
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", task_id);
					flowParams.put("status", 6);
					flowParams.put("remark", "自动派发工单");
					checkProcessDao.addProcess(flowParams);

				}

				// 保证每次检查完设备之后，将检查时间插入到设备表
				Map map = new HashMap();
				map.put("areaId", areaId);
				map.put("sonAreaId", sonAreaId);
				map.put("eqpId", eqpId);
				map.put("eqpNo", eqpNo);
				checkOrderDao.updateCheckCompleteTime(map);

			}

			/**
			 * TODO 客响订单
			 */
			if ("9".equals(operType)) {

				// 保存先工单
				Map troubleTaskMap = new HashMap();
				troubleTaskMap.put("TASK_NO",
						eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_NAME",
						eqpName + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_TYPE", operType);// FTTH
				troubleTaskMap.put("STATUS_ID", "0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
				troubleTaskMap.put("INSPECTOR", staffId);
				troubleTaskMap.put("CREATE_STAFF", staffId);
				troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
				troubleTaskMap.put("AREA_ID", parentAreaId);
				troubleTaskMap.put("ENABLE", "0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
				troubleTaskMap.put("REMARK", remarks);
				troubleTaskMap.put("INFO", info);
				troubleTaskMap.put("NO_EQPNO_FLAG", 0);// 无编码上报
				troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
				troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
				troubleTaskMap.put("SBID", eqpId);// 设备id
				troubleTaskMap.put("COMPANY", "");// 设备id
				troubleTaskMap.put("MAINTOR", "");// 设备id

				checkOrderDao.saveTroubleTask(troubleTaskMap);
				String task_id = troubleTaskMap.get("TASK_ID").toString();

				JSONArray addressArray = json.getJSONArray("allEqpAddress");

				for (int i = 0; i < addressArray.size(); i++) {
					int id = checkOrderDao.geteqpAddressId();
					JSONObject eqp = (JSONObject) addressArray.get(i);

					String eqpId_add = null == eqp.get("eqpId") ? "" : eqp
							.getString("eqpId");
					String eqpNo_add = null == eqp.get("eqpNo") ? "" : eqp
							.getString("eqpNo");
					String location_id = eqp.getString("locationId");
					String address_id = eqp.getString("addressId");
					String address_name = eqp.getString("addressName");
					String is_check_ok = eqp.getString("is_check_ok");
					String error_reason = null == eqp.get("error_reason") ? ""
							: eqp.getString("error_reason");

					Map adddressMap = new HashMap();
					adddressMap.put("id", id);
					adddressMap.put("phy_eqp_id", eqpId_add);
					adddressMap.put("phy_eqp_no", eqpNo_add);
					adddressMap.put("install_eqp_id", eqpId);
					adddressMap.put("location_id", location_id);
					adddressMap.put("address_id", address_id);
					adddressMap.put("address_name", address_name);
					adddressMap.put("is_check_ok", is_check_ok);
					adddressMap.put("error_reason", error_reason);
					adddressMap.put("task_id", task_id);
					adddressMap.put("create_staff", staffId);
					adddressMap.put("area_id", parentAreaId);
					adddressMap.put("son_area_id", eqpAreaId);
					checkOrderDao.insertEqpAddress(adddressMap);
				}

				for (int j = 0; j < innerArray.size(); j++) {
					JSONObject port = (JSONObject) innerArray.get(j);
					String eqpId_port = null == port.get("eqpId") ? "" : port
							.getString("eqpId");
					String eqpNo_port = null == port.get("eqpNo") ? "" : port
							.getString("eqpNo");
					String eqpName_port = null == port.get("eqpName") ? ""
							: port.getString("eqpName");
					String portId = port.getString("portId");
					String dtsj_id = port.getString("dtsj_id");

					String portNo = null == port.get("portNo") ? "" : port
							.getString("portNo");
					String portName = null == port.get("portName") ? "" : port
							.getString("portName");
					String portPhotoIds = port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					String glbm = null == port.get("glbm") ? "" : port
							.getString("glbm");
					String glmc = null == port.get("glmc") ? "" : port
							.getString("glmc");
					String isH = null == port.get("isH") ? "" : port
							.getString("isH");// 是否H光路，0不是，1是
					String type = null;
					String eqpTypeId_port = null == port.get("eqp_type_id") ? ""
							: port.getString("eqp_type_id");
					if (glbm.startsWith("F") && "2530".equals(eqpTypeId_port)) {
						type = "1";// E综维 F装维

					} else {
						type = "0";
					}
					// 保存工单详细
					int recordId = checkOrderDao.getRecordId();
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", "1");
					taskDetailMap.put("GLBM", glbm);
					taskDetailMap.put("GLMC", glmc);
					taskDetailMap.put("PORT_ID", portId);
					taskDetailMap.put("dtsj_id", dtsj_id);
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					// 保存上报来的检查端子记录
					String detail_id = taskDetailMap.get("DETAIL_ID")
							.toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", task_id);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId_port);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo_port);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName_port);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", portId);
					recordMap.put("port_no", portNo);
					recordMap.put("port_name", portName);
					recordMap.put("remark", remarks);
					recordMap.put("info", info);
					recordMap.put("descript", reason);
					recordMap.put("isCheckOK", isCheckOK);
					recordMap.put("record_type", operType);// FTTH拆机
					recordMap.put("area_id", parentAreaId);
					recordMap.put("son_area_id", eqpAreaId);
					recordMap.put("isH", isH);
					recordMap.put("type", type);
					checkOrderDao.insertEqpRecord(recordMap);
					// 保存端子照片关系
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("OBJECT_TYPE", 4);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID", recordId);
					if (!"".equals(portPhotoIds)) {
						String[] photos = portPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}

				}

				// 保存设备信息
				// 先获取下一个设备检查记录的ID
				int recordId = checkOrderDao.getRecordId();
				// 保存工单详细
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", task_id);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
				taskDetailMap.put("CHECK_FLAG", "");
				taskDetailMap.put("GLBM", "");
				taskDetailMap.put("GLMC", "");
				taskDetailMap.put("PORT_ID", "");
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

				// 保存上报来的设备记录，端子信息为空
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				Map recordMap = new HashMap();
				recordMap.put("recordId", recordId);
				recordMap.put("task_id", task_id);
				recordMap.put("detail_id", detail_id);
				recordMap.put("eqpId", eqpId);
				recordMap.put("eqpAddress", eqpAddress);
				recordMap.put("eqpNo", eqpNo);
				recordMap.put("staffId", staffId);
				recordMap.put("eqpName", eqpName);
				recordMap.put("remarks", remarks);
				recordMap.put("info", info);
				recordMap.put("longitude", longitude);
				recordMap.put("latitude", latitude);
				recordMap.put("comments", comments);
				recordMap.put("port_id", "");
				recordMap.put("port_no", "");
				recordMap.put("port_name", "");
				recordMap.put("remark", remarks);
				recordMap.put("info", info);
				recordMap.put("descript", "");
				recordMap.put("isCheckOK", "1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
				recordMap.put("record_type", operType);
				recordMap.put("area_id", parentAreaId);
				recordMap.put("son_area_id", eqpAreaId);
				recordMap.put("isH", "");
				recordMap.put("type", "");
				checkOrderDao.insertEqpRecord(recordMap);
				checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
				checkOrderDao.updateLastUpdateTime(recordMap);
				// 插入流程环节
				Map processMap = new HashMap();
				processMap.put("task_id", task_id);
				processMap.put("oper_staff", staffId);
				if ("1".equals(is_bill)) {
					processMap.put("status", 4);
					processMap.put("remark", "隐患上报");
				} else {
					processMap.put("status", 8);
					processMap.put("remark", "隐患上报无需整改");
				}
				checkProcessDao.addProcess(processMap);
				// 保存设备的照片关系
				if (!"".equals(eqpPhotoIds)) {
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_TYPE", 4);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("RECORD_ID", recordId);
					String[] photos = eqpPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}

				if ("1".equals(is_bill)) {

					String auditor = checkOrderDao.getAuitor(eqpAreaId);
					String contractor = checkOrderDao
							.getContractor(identification);
					String maintor = null;

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("TASK_ID", task_id);
					params.put("STAFF_ID", staffId);
					params.put("auditor", auditor);

					params.put("STATUS_ID", 6);
					params.put("COMPLETE_TIME", "");
					if (!"".equals(constructor) && null != constructor) {
						maintor = checkOrderDao.getConstructorId(constructor);
					} else if (!"".equals(contractor) && null != contractor) {
						maintor = contractor;

					} else {
						maintor = auditor;

					}
					params.put("MAINTOR", maintor);
					params.put("REFORM_DEMAND", "");
					Map<String, Object> flowParams = new HashMap<String, Object>();
					/**
					 * 更新审核员
					 */
					cableMyTaskDao.updateAuditor(params);
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", task_id);
					flowParams.put("status", 6);
					flowParams.put("remark", "自动派发工单");
					checkProcessDao.addProcess(flowParams);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "生成工单失败，请联系管理员。");
		}
		return result.toString();

	}


	@Override
	public String commitCheckTask(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		String taskid = "";
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			// String queryType = json.getString("queryType");// 查询类型
			// if (!OnlineUserListener.isLogin(staffId, sn)) {
			// result.put("result", "002");
			// result.put("photoId", "");
			// return result.toString();
			// }
			/**
			 * 传入的参数
			 */
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");
			String taskId = json.getString("taskId");
			String orderId = "";
			if (json.containsKey("OrderId")) {
				orderId = json.getString("OrderId");

			}
			String OrderNo = "";
			if (json.containsKey("OrderNo")) {
				OrderNo = json.getString("OrderNo");

			}
			String rwmxId = null == json.get("rwmxId") ? "" : json
					.getString("rwmxId");
			String eqpId = null == json.get("eqpId") ? "" : json
					.getString("eqpId");// 设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");// 设备名称
			String eqpAddress = null == json.get("eqpAddress") ? "" : json
					.getString("eqpAddress");// 设备地址（临时上报隐患时，必填）
			String eqpPhotoIds = json.getString("photoId");// 设备照片
			String operType = json.getString("operType");// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
															// 4：二次检查

			String remarks = json.getString("remarks");// 现场规范
			String info = null == json.get("info") ? "" : json
					.getString("info");// 备注
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String comments = null == json.get("comments") ? "" : json
					.getString("comments");// 评价
			String is_bill = json.getString("is_bill");// 是否需要整改
			String data_source = json.getString("data_source");// 数据来源
			String allcount = json.getString("allcount");// 全部端子数
			// String addressCheckCnt = json.getString("addressCheckCnt");//
			// 覆盖地址检查数量
			JSONArray addressArray = null;
			int addressCheckCnt = 0;
			if (json.containsKey("allEqpAddress")) {
				addressArray = json.getJSONArray("allEqpAddress");
				addressCheckCnt = addressArray.size();
			}
			String equType = "";
			if (json.containsKey("eqpType")) {
				equType = json.getString("eqpType");
			}
			String PositionPersonsList = "";// 光网助手匹配兜底岗相关人员

			if (equType.equals("光交接箱")) {
				equType = "1";
			} else if (equType.equals("ODF")) {
				equType = "2";
			} else if (equType.equals("光分纤箱")) {
				equType = "3";
			}

			List<Map<String, Object>> glbmMap = null;
			;
			Map portIdMap = new HashMap();
			String portIdList = "";

			/**
			 * 端子信息
			 */
			JSONArray innerArray = json.getJSONArray("port");
			String id_numberList = ""; // 光网检查人匹配施工人员账号
			if (innerArray.size() > 0 && innerArray != null) {
				for (int j = 0; j < innerArray.size(); j++) {
					JSONObject port = (JSONObject) innerArray.get(j);
					String portId = null == port.getString("portId") ? ""
							: port.getString("portId");
					String glbm = null == port.get("glbm") ? "" : port
							.getString("glbm");// 光路编码
					portIdMap.put("portId", portId);
					portIdList += portId + ",";
					if (null != glbm || "" != glbm) {
						glbmMap = checkOrderDao.queryGlbm(glbm);
						if (null != glbmMap) {
							for (Map glbmMap2 : glbmMap) {
								String id_number = glbmMap2.get("ID_NUMBER")
										.toString();
								if (null != id_number || "" != id_number) {
									id_numberList += id_number + ",";
								} else {
									id_numberList += "";
								}
							}
						} else {
							id_numberList = "";
						}
					}
				}
			}

			if (id_numberList.length() > 2) {
				id_numberList = id_numberList.substring(0,
						id_numberList.length() - 1);
			} else {
				id_numberList = id_numberList;
			}

			if (portIdList.length() > 2) {
				portIdList = portIdList.substring(0, portIdList.length() - 1);
			} else {
				portIdList = null;
			}

			String sysRoute = "GWZS";
			String taskType = "5";
			String taskTypes = "6";
			String xml = null;
			String xmls = null;
			String results = "";
			String resultss = "";
			String contract_persion_nos = "";
			Map resmap = new HashMap();

			// 根据staffid获取人员姓名和账号
			Map maps = checkOrderDao.queryByStaffId(staffId);
			String staffName = maps.get("STAFF_NAME").toString();
			String staffNo = maps.get("STAFF_NO").toString();
			/*
			 * String staffName="1"; String staffNo="2";
			 */

			// 整治对应设备承包人员账号
			Map equCbAccountMap = new HashMap();
			equCbAccountMap = checkOrderDao.equCbAccount(eqpNo);
			if (null != equCbAccountMap) {
				for (int i = 0; i < equCbAccountMap.size(); i++) {
					String contract_persion_no = equCbAccountMap.get(
							"CONTRACT_PERSION_NO").toString();
					contract_persion_nos += contract_persion_nos + ",";
				}
			} else {
				contract_persion_nos = contract_persion_nos;
			}
			if (contract_persion_nos.length() > 2) {
				contract_persion_nos = contract_persion_nos.substring(0,
						contract_persion_nos.length() - 1);
			} else {
				contract_persion_nos = contract_persion_nos;
			}

			Map oedMap = new HashMap();
			oedMap.put("eqpId", eqpId);
			oedMap.put("eqpNo", eqpNo);
			String eqpMark = "";
			if (json.containsKey("eqpMark")) {
				eqpMark = json.getString("eqpMark");// 箱子设备标识 80000302:接入层
													// 81538172:网络层
			}
			// String eqpMark=json.getString("eqpMark");
			if ("接入层".equals(eqpMark)) {
				eqpMark = "80000302";
				oedMap.put("eqpMark", eqpMark);
				checkOrderDao.insertEqpMark(oedMap);// 修改设备标识
			} else if ("网络层".equals(eqpMark)) {
				eqpMark = "81538172";
				oedMap.put("eqpMark", eqpMark);
				checkOrderDao.insertEqpMark(oedMap);// 修改设备标识
			}
			String sblx = "";// 用来判断提交时是分光器还是其他类型
			String eqp_id = "";
			String eqp_no = "";

			/**
			 * 获取当前登录人员的区域信息
			 */
			Map areaMap = checkOrderDao.queryAreaByStaffId(staffId);
			String sonAreaId = null == areaMap.get("SON_AREA_ID") ? ""
					: areaMap.get("SON_AREA_ID").toString();
			String areaId = null == areaMap.get("AREA_ID") ? "" : areaMap.get(
					"AREA_ID").toString();

			Map ddgMap = checkOrderDao.queryDdj(eqpNo);
			String ddg = null;
			if (ddgMap != null) {
				String GRID_ID = ddgMap.get("GRID_ID").toString();
				ddg = GRID_ID;
			} else {
				String GRID_ID = null;
				ddg = GRID_ID;
			}
			// String ddg = null == ddgMap.get("GRID_ID")? "" :
			// ddgMap.get("GRID_ID").toString();
			// 光网助手匹配兜底岗相关人员
			Map PositionPersonsMap = new HashMap();
			if (null != ddg) {
				List PositionPersonslsit = checkOrderDao.PositionPersons(ddg);
				if (null != PositionPersonslsit
						|| PositionPersonslsit.size() > 0) {
					for (int i = 0; i < PositionPersonslsit.size(); i++) {
						String id_number = PositionPersonslsit.get(i)
								.toString();
						if (null != id_number || "" != id_number) {
							PositionPersonsList += id_number + ",";
						} else {
							PositionPersonsList += "";
						}
					}
				} else {
					PositionPersonsList = "";
				}

			} else {
				PositionPersonsList = "";
			}
			if (PositionPersonsList.length() > 2) {
				PositionPersonsList = PositionPersonsList.substring(0,
						PositionPersonsList.length() - 1);
			} else {
				PositionPersonsList = PositionPersonsList;
			}

			/**
			 * 根据设备获取区域信息
			 */
			Map eqpId_eqpNo_map = new HashMap();
			eqpId_eqpNo_map.put("eqpId", eqpId);
			eqpId_eqpNo_map.put("eqpNo", eqpNo);
			String eqp_type = checkOrderDao.getEqpType(eqpId_eqpNo_map);
			if("411".equals(eqp_type)){
				equType = "2";
			}
			if("703".equals(eqp_type)){
				equType = "1";			
			}
			if("704".equals(eqp_type)){
				equType = "3";		
			}
			Map eqpareaMap = checkOrderDao.queryAreaByeqpId(eqpId_eqpNo_map);
			String parentAreaId = null == eqpareaMap.get("PARENT_AREA_ID") ? ""
					: eqpareaMap.get("PARENT_AREA_ID").toString();
			String eqpAreaId = null == eqpareaMap.get("AREA_ID") ? ""
					: eqpareaMap.get("AREA_ID").toString();
			/**
			 * 根据设备id获取设备所在的经纬度--从gis中同步过来的
			 */
			String eqpLongitude = null == eqpareaMap.get("LONGITUDE") ? ""
					: eqpareaMap.get("LONGITUDE").toString();
			String eqpLatitude = null == eqpareaMap.get("LATITUDE") ? ""
					: eqpareaMap.get("LATITUDE").toString();
			/**
			 * 根据设备id获取设备所在的经纬度--检查人员采集的
			 */
			String longitude_inspect = null == eqpareaMap
					.get("LONGITUDE_INSPECT") ? "" : eqpareaMap.get(
					"LONGITUDE_INSPECT").toString();
			String latitude_inspect = null == eqpareaMap
					.get("LATITUDE_INSPECT") ? "" : eqpareaMap.get(
					"LATITUDE_INSPECT").toString();
			boolean flag = true;// 如果没有采集坐标，就用gis中同步过来的经纬度进行距离计算，否则用检查人员采集过来的经纬度计算

			/**
			 * 端子信息
			 */
			// JSONArray innerArray =json.getJSONArray("port");

			if (!"2".equals(operType) && !"6".equals(operType)
					&& !"7".equals(operType) && !"8".equals(operType)) {
				// 一开始将检查人的当前位置与设备表中的设备经纬度计算，如果检查或整改设备与人的距离超过300m，则不允许提交工单，flag默认为true,如果超过300m,flag改为false
				if ("".equals(longitude_inspect) || "".equals(latitude_inspect)) {
					flag = getPermission(eqpLongitude, eqpLatitude, longitude,
							latitude);
				} else {
					flag = getPermission(longitude_inspect, latitude_inspect,
							longitude, latitude);
				}

				//2017年9月14日18:15:50 为了测试和演示，将变量设置为常量，以后上线需要注释掉改行代码
				flag = true;
				// 一旦不允许提交工单后，让检察人员重新获取设备的经纬度
				if (!flag) {
					result.put("result", "112");
					result.put("desc", "生成工单失败，请采集设备坐标。");
				}
				if (flag) {

					/**
					 * TODO 0、周期任务巡检拍照上传(我的检查任务-->检查任务签到)
					 */
					if ("0".equals(operType) || "4".equals(operType)
							|| "5".equals(operType)) {
						taskid = taskId;
						Map map = new HashMap<String, String>();
						map.put("task_id", taskId);
						map.put("eqpId", eqpId);
						map.put("eqpAddress", eqpAddress);
						map.put("eqpNo", eqpNo);
						map.put("staffId", staffId);
						map.put("eqpName", eqpName);
						map.put("remarks", remarks);// 现场规范
						map.put("info", info);
						map.put("longitude", longitude);
						map.put("latitude", latitude);
						map.put("comments", comments);
						map.put("remark", remarks);
						// map.put("record_type",
						// "");//0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
						map.put("area_id", parentAreaId);
						map.put("son_area_id", eqpAreaId);
						map.put("record_type", operType);
						String OBJECT_TYPE = null;
						String REMARKS = null;
						if ("0".equals(operType)) {
							OBJECT_TYPE = "0";
							REMARKS = "周期性任务";
						} else if ("4".equals(operType)) {
							OBJECT_TYPE = "3";
							REMARKS = "二次检查";
						} else {
							OBJECT_TYPE = "4";
							REMARKS = "电子档案库";
						}
						/**
						 * 更新端子表
						 */
						Map processMap = new HashMap();
						for (int i = 0; i < innerArray.size(); i++) {
							JSONObject port = (JSONObject) innerArray.get(i);
							String eqpId_port = null == port.get("eqpId") ? ""
									: port.getString("eqpId");
							String eqpNo_port = null == port.get("eqpNo") ? ""
									: port.getString("eqpNo");
							String eqpName_port = null == port.get("eqpName") ? ""
									: port.getString("eqpName");
							String portId = port.getString("portId");
							String dtsj_id = port.getString("dtsj_id");
							String portNo = port.getString("portNo");
							String portName = port.getString("portName");
							String photoIds = port.getString("photoId");
							String reason = port.getString("reason");
							String isCheckOK = port.getString("isCheckOK");
							String glbm = null == port.get("glbm") ? "" : port
									.getString("glbm");
							String glmc = null == port.get("glmc") ? "" : port
									.getString("glmc");
							String isH = null == port.get("isH") ? "" : port
									.getString("isH");// 是否H光路，0不是，1是
							String type = null;
							String eqpTypeId_port = null == port
									.get("eqp_type_id") ? "" : port
									.getString("eqp_type_id");
							if (glbm.startsWith("F")
									&& "2530".equals(eqpTypeId_port)) {
								type = "1";// 装维

							} else {
								type = "0";// 综维
							}
							String truePortId = null == port.get("truePortId") ? ""
									: port.getString("truePortId");//正确端子
							
							String truePortNo = null == port.get("truePortNo") ? ""
									: port.getString("truePortNo");//正确编码
							
							String rightEqpId = null == port.get("rightEqpId") ? ""
									: port.getString("rightEqpId");//
							
							String rightEqpNo = null == port.get("rightEqpNo") ? ""
									: port.getString("rightEqpNo");//正确
							String glbh=port.getString("glbm");
							String changedPortId=port.getString("portId_new");//修改后的端子id
							String changedPortNo=port.getString("localPortNo");//修改后的端子编码
							String changedEqpId=port.getString("sbid_new");//修改后的设备ID
							String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
							
							map.put("port_no", portNo);
							map.put("port_name", portName);
							map.put("descript", reason);
							resmap.put("descript", reason);
							map.put("isCheckOK", isCheckOK);
							/**
							 * 修改端子信息tb_cablecheck_dtsj,检查时间
							 */
							map.put("port_id", portId);
							checkOrderDao.updateDtdz(map);
							/**
							 * 任务详情表在创建工单时即创建记录，设备占一条记录，不同端子各占一条记录，变动N次占N次记录(
							 * 检查前的信息) 插入任务详情表(检查后的信息)
							 */
							int recordId = checkOrderDao.getRecordId();
							Map portMap = new HashMap();
							portMap.put("taskid", taskId);
							portMap.put("portNo", portNo);
							map.put("recordId", recordId);
							Map taskDetailMap = new HashMap();
							taskDetailMap.put("TASK_ID", taskId);
							taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
							taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
							taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
							taskDetailMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
							taskDetailMap.put("GLBM", glbm);
							taskDetailMap.put("GLMC", glmc);
							taskDetailMap.put("PORT_ID", portId);
							taskDetailMap.put("dtsj_id", dtsj_id);
							taskDetailMap.put("eqpId_port", "");
							taskDetailMap.put("eqpNo_port", "");
							taskDetailMap.put("eqpName_port", "");
							taskDetailMap.put("orderNo", "");
							taskDetailMap.put("orderMark", "");
							taskDetailMap.put("actionType", "");
							taskDetailMap.put("archive_time", "");
							checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
							/**
							 * 插入检查记录表
							 */
							String detail_id = taskDetailMap.get("DETAIL_ID")
									.toString();
							map.put("detail_id", detail_id);
							map.put("eqpId", eqpId_port);
							map.put("eqpNo", eqpNo_port);
							map.put("eqpName", eqpName_port);
							map.put("isH", isH);
							map.put("type", type);
							map.put("port_info", "");
							map.put("TASK_NO","");
							map.put("TASK_NAME","");
							map.put("glbm",glbh);
							map.put("orderId",orderId);
							map.put("OrderNo",OrderNo);
							map.put("isFrom","");
							map.put("truePortId",truePortId);
							map.put("truePortNo",truePortNo);
							map.put("rightEqpId",rightEqpId);
							map.put("rightEqpNo",rightEqpNo);
							map.put("changedPortId",changedPortId);
							map.put("changedPortNo",changedPortNo);
							map.put("changedEqpId",changedEqpId);
							map.put("changedEqpNo",changedEqpNo);
							checkOrderDao.insertEqpRecordNew(map);
							//checkOrderDao.insertEqpRecord(map);
							//将修改后的端子插入流程表 tb_cablecheck_process
							//端子合格有两种情况，端子本身合格无需整改；端子已经被现场整改
							if("0".equals(isCheckOK)){
								if(!"".equals(changedPortNo)){
									processMap.put("task_id", taskid);
									processMap.put("oper_staff", staffId);
									String content="";
									if(eqpId_port.equals(changedEqpId)){
										content=glbm+"从"+eqpNo_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
									}else{
										content =glbm+"从"+eqpNo_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
									}
									processMap.put("status", "66");//一键改
									processMap.put("remark", "一键改");
									processMap.put("receiver", "");
									processMap.put("content", content);
									checkProcessDao.addProcessNew(processMap);
								}
							}
							/**
							 * 保存图片关系
							 */
							if (!"".equals(photoIds) && photoIds != null) {
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", taskId);
								photoMap.put("DETAIL_ID", detail_id);
								photoMap.put("OBJECT_ID", recordId);
								photoMap.put("REMARKS", REMARKS);
								photoMap.put("OBJECT_TYPE", OBJECT_TYPE);// 3为二次检查
								photoMap.put("RECORD_ID", recordId);
								String[] photos = photoIds.split(",");
								for (String photo : photos) {
									photoMap.put("PHOTO_ID", photo);
									checkOrderDao.insertPhotoRel(photoMap);
								}
							}
						}
						/**
						 * 更新任务表,检查完成时间
						 */
						Map taskMap = new HashMap();
						taskMap.put("task_id", taskId);
						taskMap.put("staffId", staffId);
						taskMap.put("remarks", remarks);
						taskMap.put("is_need_zg", "0");
						taskMap.put("statusId", "8");
						// checkOrderDao.updateTask(taskMap);
						// checkOrderDao.updateTaskTime(taskMap);//实际检查完成时间
						// checkOrderDao.updateLastUpdateTime(taskMap);
						// map.put("statusId", "8");
						// checkOrderDao.updateStatus(map);//更新状态
						// 一次性更新任务表
						checkOrderDao.updateTask_once(taskMap);
						/**
						 * 增加工单流程流转信息,即回单
						 */
						map.put("oper_staff", staffId);
						// map.put("status", "7");
						map.put("status", "8");
						map.put("remark", "检查提交");
						map.put("content", "端子检查合格或现场已整改，任务直接归档");
						if("0".equals(is_bill)){
							map.put("receiver", "");
							checkProcessDao.addProcessNew(map);//无需整改直接归档
						}
						
						/**
						 * 插入任务详情表
						 */
						int eqpRecordId = checkOrderDao.getRecordId();
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", taskId);
						taskDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
						taskDetailMap.put("CHECK_FLAG", 1);
						taskDetailMap.put("GLBM", "");
						taskDetailMap.put("GLMC", "");
						taskDetailMap.put("PORT_ID", "");
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
						/**
						 * 插入检查记录表
						 */
						map.put("eqpId", eqpId);
						map.put("eqpNo", eqpNo);
						map.put("eqpName", eqpName);
						map.put("recordId", eqpRecordId);
						String eqpdetail_id = taskDetailMap.get("DETAIL_ID")
								.toString();
						map.put("detail_id", eqpdetail_id);
						// 插入设备信息，置空端子信息字段
						map.put("port_no", "");
						map.put("port_id", "");
						map.put("isH", "");
						map.put("type", "");
						checkOrderDao.insertEqpRecord(map);
						/**
						 * 保存设备的照片信息
						 */

						if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
							Map photoMap = new HashMap();
							photoMap.put("TASK_ID", taskId);
							photoMap.put("OBJECT_ID", eqpRecordId);
							photoMap.put("DETAIL_ID", eqpdetail_id);
							photoMap.put("OBJECT_TYPE", OBJECT_TYPE);// 0，周期性任务，1：隐患上报工单，2,回单操作
							photoMap.put("REMARKS", REMARKS);
							photoMap.put("RECORD_ID", eqpRecordId);
							String[] photos = eqpPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}

						for (int i = 0; i < addressArray.size(); i++) {
							int id = checkOrderDao.geteqpAddressId();
							JSONObject eqp = (JSONObject) addressArray.get(i);

							String eqpId_add = null == eqp.get("eqpId") ? ""
									: eqp.getString("eqpId");
							String eqpNo_add = null == eqp.get("eqpNo") ? ""
									: eqp.getString("eqpNo");
							String location_id = eqp.getString("locationId");
							String address_id = eqp.getString("addressId");
							String address_name = eqp.getString("addressName");
							String is_check_ok = eqp.getString("is_check_ok");
							String error_reason = null == eqp
									.get("error_reason") ? "" : eqp
									.getString("error_reason");

							Map adddressMap = new HashMap();
							adddressMap.put("id", id);
							adddressMap.put("phy_eqp_id", eqpId_add);
							adddressMap.put("phy_eqp_no", eqpNo_add);
							adddressMap.put("install_eqp_id", eqpId);
							adddressMap.put("location_id", location_id);
							adddressMap.put("address_id", address_id);
							adddressMap.put("address_name", address_name);
							adddressMap.put("is_check_ok", is_check_ok);
							adddressMap.put("error_reason", error_reason);
							adddressMap.put("task_id", taskId);
							adddressMap.put("create_staff", staffId);
							adddressMap.put("area_id", parentAreaId);
							adddressMap.put("son_area_id", eqpAreaId);
							checkOrderDao.insertEqpAddress(adddressMap);

						}
						/**
						 * TODO 需要整改
						 */
						if ("1".equals(is_bill)) {

							// 获取所有的端子的数据存入临时表
							/**
							 * 端子信息
							 */
							// 获取批次ID
							int batchId = checkOrderDao.getBatchId();
							int newbatchId = batchId + 1;
							List<Double> portIds = new ArrayList<Double>();
							if (innerArray.size() > 0 && innerArray != null) {

								for (int j = 0; j < innerArray.size(); j++) {
									JSONObject port = (JSONObject) innerArray
											.get(j);
									if("1".equals(port.getString("isCheckOK"))){
										Double portId = port.getDouble("portId");
										portIds.add(portId);
									}
								}
								if (!"".equals(portIds)) {
									List<Map> portDetails = checkOrderDao
											.getPortDetails(portIds);
									// 插入临时数据
									if (portDetails.size() > 0
											&& portDetails != null) {
										for (Map port : portDetails) {
											Map obj = new HashMap();
											obj.put("BATCHID", newbatchId);
											obj.put("ORDER_ID",
													port.get("ORDER_ID")
															.toString());
											obj.put("PHY_EQP_ID", eqpId);
											obj.put("PHY_EQP_ID_PORT", port
													.get("PHY_EQP_ID")
													.toString());
											obj.put("PHY_PORT_ID",
													port.get("PHY_PORT_ID")
															.toString());
											if (port.get("REPLY_JOB_CSV") != null) {
												obj.put("REPLY_JOB_CSV", port
														.get("REPLY_JOB_CSV")
														.toString());
											} else {
												obj.put("REPLY_JOB_CSV", "000");
											}
											if (port.get("REPLY_JOB_IOM") != null) {
												obj.put("REPLY_JOB_IOM", port
														.get("REPLY_JOB_IOM")
														.toString());
											} else {
												obj.put("REPLY_JOB_IOM", "000");
											}
											obj.put("MARK", port.get("MARK")
													.toString());
											SimpleDateFormat format = new SimpleDateFormat(
													"yyyy-MM-dd HH:mm:ss");
											obj.put("CREATE_TIME", format
													.parse(port.get(
															"CREATE_TIME")
															.toString()));
											if (port.get("OTHER_SYSTEM_STAFF_ID") != null) {
												obj.put("OTHER_SYSTEM_STAFF_ID",
														port.get(
																"OTHER_SYSTEM_STAFF_ID")
																.toString());
											} else {
												obj.put("OTHER_SYSTEM_STAFF_ID",
														"");
											}

											if (port.get("SGGH") != null) {
												obj.put("SGGH", port
														.get("SGGH").toString());
											} else {
												obj.put("SGGH", "");
											}
											checkOrderDao.insertBatchData(obj);
										}
									}
									// 来自综调的单子，进行分组整合发单。
									List<Map> CSVGroup = checkOrderDao
											.getCSVGroup(newbatchId);
									for (Map CSV : CSVGroup) {
										List<Map> CSVPortDetails = new ArrayList<Map>();
										String otherSysStaffId = CSV.get(
												"OTHER_SYSTEM_STAFF_ID")
												.toString();
										//获取工单所在班组
										String team_id = "";
										String auditor ="";
										Map receiverMap=new HashMap();
										for (Map port : portDetails) {
											if ((port
													.get("OTHER_SYSTEM_STAFF_ID")
													.toString())
													.equals(otherSysStaffId)) {
												CSVPortDetails.add(port);
												team_id= port.get("TEAM_ID").toString();   //获取班组
											}
										}
										//通过班组获取审核员
										if(!"".equals(team_id)){
											receiverMap=checkTaskDao.getOrderReceiverOfBanZu(team_id);
											auditor=receiverMap.get("STAFF_ID").toString();
										}
										
										if (CSVPortDetails.size() > 0
												&& CSVPortDetails != null) {
											// 获取当前的数据进行分发
											/**
											 * 插入任务表
											 */
											String replyId = checkOrderDao
													.getReplyId(otherSysStaffId);
											if ("".equals(replyId)
													|| replyId == null) {
												replyId = "";
											}
											//如果维护员为空，则将审核员作为维护员
											if("".equals(replyId)){
												replyId=auditor;
											}
											//replyId=auditor;
											Map troubleTaskMap = new HashMap();
											String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
											String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
											troubleTaskMap.put("TASK_NO",TASK_NO);
											troubleTaskMap.put("TASK_NAME",TASK_NAME);
											troubleTaskMap.put("TASK_TYPE", 3);// 问题上报
											troubleTaskMap.put("STATUS_ID", 6);// 问题上报
											troubleTaskMap.put("INSPECTOR",
													staffId);
											troubleTaskMap.put("CREATE_STAFF",
													staffId);
											troubleTaskMap.put("SON_AREA_ID",
													eqpAreaId);
											troubleTaskMap.put("AREA_ID",
													parentAreaId);
											troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用
																				// 1不可用（不显示在待办列表）
											troubleTaskMap.put("REMARK",
													remarks);
											troubleTaskMap.put("INFO", info);
											troubleTaskMap.put("NO_EQPNO_FLAG",
													"0");// 无编码上报
											troubleTaskMap.put("NO_EQPNO_FLAG",
													"0");// 无编码上报

											troubleTaskMap.put("OLD_TASK_ID",
													taskId);// 老的task_id
											troubleTaskMap.put("IS_NEED_ZG",
													"1");// 是否整改,1需要整改
											troubleTaskMap.put("SBID", eqpId);// 设备id
											troubleTaskMap.put("MAINTOR",
													replyId);// 装维班组
											troubleTaskMap.put("auditor",
													auditor);// 装维班组
											/*checkOrderDao
													.saveTroubleTask(troubleTaskMap);*/
											checkOrderDao
											.saveTroubleTaskNew(troubleTaskMap);
											String newTaskId = troubleTaskMap
													.get("TASK_ID").toString();
											logger.info("【需要整改添加一张新的工单taskId】"
													+ newTaskId);
											taskid = newTaskId;  
											/**
											 * 更新任务表
											 */
											Map oldTaskMap = new HashMap();
											oldTaskMap.put("task_id", taskId);
											oldTaskMap.put("remarks", remarks);
											oldTaskMap.put("staffId", staffId);
											oldTaskMap.put("is_need_zg", "1");// 原来的任务，需要整改
											checkOrderDao
													.updateTask(oldTaskMap);
											/**
											 * 端子信息
											 */
											for (Map CSVPort : CSVPortDetails) {
												if (innerArray.size() > 0
														&& innerArray != null) {
													for (int j = 0; j < innerArray
															.size(); j++) {
														JSONObject port = (JSONObject) innerArray
																.get(j);
														if (port.getString(
																"portId")
																.equals(CSVPort
																		.get("PHY_PORT_ID")
																		.toString())) {
															String eqpId_port = null == port
																	.get("eqpId") ? ""
																	: port.getString("eqpId");
															String eqpNo_port = null == port
																	.get("eqpNo") ? ""
																	: port.getString("eqpNo");
															String eqpName_port = null == port
																	.get("eqpName") ? ""
																	: port.getString("eqpName");
															String portId = port
																	.getString("portId");
															String dtsj_id = port
																	.getString("dtsj_id");
															String portNo = port
																	.getString("portNo");
															String portName = port
																	.getString("portName");
															String glbm = null == port
																	.get("glbm") ? ""
																	: port.getString("glbm");
															String glmc = null == port
																	.get("glmc") ? ""
																	: port.getString("glmc");
															String type = null;
															String eqpTypeId_port = null == port
																	.get("eqp_type_id") ? ""
																	: port.getString("eqp_type_id");
															if (glbm.startsWith("F")
																	&& "2530"
																			.equals(eqpTypeId_port)) {
																type = "1";

															} else {
																type = "0";
															}
															String truePortId = null == port.get("truePortId") ? ""
																	: port.getString("truePortId");//正确端子
															
															String truePortNo = null == port.get("truePortNo") ? ""
																	: port.getString("truePortNo");//正确编码
															
															String rightEqpId = null == port.get("rightEqpId") ? ""
																	: port.getString("rightEqpId");//
															
															String rightEqpNo = null == port.get("rightEqpNo") ? ""
																	: port.getString("rightEqpNo");//正确
															String glbh=port.getString("glbm");
															String changedPortId=port.getString("portId_new");//修改后的端子id
															String changedPortNo=port.getString("localPortNo");//修改后的端子编码
															String changedEqpId=port.getString("sbid_new");//修改后的设备ID
															String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
															
															String isH = null == port
																	.get("isH") ? ""
																	: port.getString("isH");//
															/**
															 * 保存工单详细
															 */
															int portRecordId = checkOrderDao
																	.getRecordId();
															Map taskDetailMap1 = new HashMap();
															taskDetailMap1.put(
																	"TASK_ID",
																	newTaskId);
															taskDetailMap1
																	.put("INSPECT_OBJECT_ID",
																			portRecordId);
															taskDetailMap1
																	.put("INSPECT_OBJECT_NO",
																			portNo);
															taskDetailMap1
																	.put("INSPECT_OBJECT_TYPE",
																			1);
															taskDetailMap1
																	.put("CHECK_FLAG",
																			"1");
															taskDetailMap1.put(
																	"GLBM",
																	glbm);
															taskDetailMap1.put(
																	"GLMC",
																	glmc);
															taskDetailMap1.put(
																	"PORT_ID",
																	portId);
															taskDetailMap1.put(
																	"dtsj_id",
																	dtsj_id);
															taskDetailMap1.put("eqpId_port", "");
															taskDetailMap1.put("eqpNo_port", "");
															taskDetailMap1.put("eqpName_port", "");
															taskDetailMap1.put("orderNo", "");
															taskDetailMap1.put("orderMark", "");
															taskDetailMap1.put("actionType", "");
															taskDetailMap1.put("archive_time", "");
															checkOrderDao
																	.saveTroubleTaskDetail(taskDetailMap1);
															String detail_id1 = taskDetailMap1
																	.get("DETAIL_ID")
																	.toString();
															/**
															 * 保存上报来的检查端子记录
															 */
															String portPhotoIds = port
																	.getString("photoId");
															String reason = port
																	.getString("reason");
															String isCheckOK = port
																	.getString("isCheckOK");
															Map recordMap = new HashMap();
															recordMap
																	.put("recordId",
																			portRecordId);
															recordMap.put(
																	"task_id",
																	newTaskId);
															recordMap.put(
																	"TASK_NO",
																	TASK_NO);
															recordMap.put(
																	"TASK_NAME",
																	TASK_NAME);
															recordMap
																	.put("detail_id",
																			detail_id1);
															recordMap.put(
																	"eqpId",
																	eqpId_port);
															recordMap
																	.put("eqpAddress",
																			"");
															recordMap.put(
																	"eqpNo",
																	eqpNo_port);
															recordMap.put(
																	"staffId",
																	staffId);
															recordMap
																	.put("eqpName",
																			eqpName_port);
															recordMap.put(
																	"remarks",
																	remarks);
															recordMap.put(
																	"info",
																	info);
															recordMap
																	.put("longitude",
																			longitude);
															recordMap.put(
																	"latitude",
																	latitude);
															recordMap.put(
																	"comments",
																	comments);
															recordMap.put(
																	"port_id",
																	portId);
															recordMap.put(
																	"port_no",
																	portNo);
															recordMap
																	.put("port_name",
																			portName);
															recordMap.put(
																	"info",
																	info);
															recordMap.put(
																	"descript",
																	reason);
															resmap.put(
																	"descript",
																	reason);
															recordMap
																	.put("isCheckOK",
																			isCheckOK);
															recordMap
																	.put("record_type",
																			operType);// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
															recordMap
																	.put("area_id",
																			parentAreaId);
															recordMap
																	.put("son_area_id",
																			eqpAreaId);
															recordMap.put(
																	"isH", isH);
															recordMap.put(
																	"type",
																	type);
															if (!"".equals(orderId)
																	&& orderId != null) {
																recordMap
																		.put("orderId",
																				orderId);
															} else {
																recordMap
																		.put("orderId",
																				"");
															}
															if (!"".equals(OrderNo)
																	&& OrderNo != null) {
																recordMap
																		.put("OrderNo",
																				OrderNo);
															} else {
																recordMap
																		.put("OrderNo",
																				"");
															}
															// 留痕操作
															recordMap.put(
																	"isFrom",
																	"1");
															recordMap.put("TASK_NO",TASK_NO);
															recordMap.put("TASK_NAME",TASK_NAME);
															recordMap.put("glbm",glbh);
															recordMap.put("truePortId",truePortId);
															recordMap.put("truePortNo",truePortNo);
															recordMap.put("rightEqpId",rightEqpId);
															recordMap.put("rightEqpNo",rightEqpNo);
															recordMap.put("changedPortId",changedPortId);
															recordMap.put("changedPortNo",changedPortNo);
															recordMap.put("changedEqpId",changedEqpId);
															recordMap.put("changedEqpNo",changedEqpNo);
															checkOrderDao.insertEqpRecordNew(recordMap);
															/*	checkOrderDao
															.insertEqpRecord(recordMap);*/
															//将修改后的端子记录插入留痕记录表
															/*if(!"".equals(changedPortNo)){
																String content="";
																if(eqpId_port.equals(changedEqpId)){
																	content="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
																}else{
																	content ="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
																}
																recordMap.put("content", content);
																recordMap.put("task_type", "3");
																checkOrderDao.saveTrace(recordMap);
															}*/

															/**
															 * 保存端子照片关系
															 */
															Map photoMap = new HashMap();
															photoMap.put(
																	"TASK_ID",
																	newTaskId);
															photoMap.put(
																	"DETAIL_ID",
																	detail_id1);
															photoMap.put(
																	"OBJECT_ID",
																	portRecordId);
															photoMap.put(
																	"REMARKS",
																	"整改工单");
															photoMap.put(
																	"OBJECT_TYPE",
																	1);// 0，周期性任务，1：隐患上报工单，2,回单操作
															photoMap.put(
																	"RECORD_ID",
																	portRecordId);// 0，周期性任务，1：隐患上报工单，2,回单操作
															// photoMap.put("REMARKS",
															// photo);
															if (!"".equals(portPhotoIds)) {
																String[] photos = portPhotoIds
																		.split(",");
																for (String photo : photos) {
																	photoMap.put(
																			"PHOTO_ID",
																			photo);
																	checkOrderDao
																			.insertPhotoRel(photoMap);
																}
															}
															// 在innerArray中移除该元素。
															innerArray
																	.remove(j);
															j--;
														}
													}
												}
											}

											int eqp_RecordId = checkOrderDao
													.getRecordId();
											/**
											 * 保存工单详细
											 */
											Map taskDetailMap1 = new HashMap();
											taskDetailMap1.put("TASK_ID",
													newTaskId);
											taskDetailMap1.put(
													"INSPECT_OBJECT_ID",
													eqp_RecordId);
											taskDetailMap1.put(
													"INSPECT_OBJECT_NO", eqpNo);
											taskDetailMap1.put(
													"INSPECT_OBJECT_TYPE", 0);
											taskDetailMap1.put("CHECK_FLAG",
													"1");
											taskDetailMap1.put("GLBM", "");
											taskDetailMap1.put("GLMC", "");
											taskDetailMap1.put("PORT_ID", "");
											taskDetailMap1.put("dtsj_id", "");
											taskDetailMap1.put("eqpId_port", "");
											taskDetailMap1.put("eqpNo_port", "");
											taskDetailMap1.put("eqpName_port", "");
											taskDetailMap1.put("orderNo", "");
											taskDetailMap1.put("orderMark", "");
											taskDetailMap1.put("actionType", "");
											taskDetailMap1.put("archive_time", "");
											checkOrderDao
													.saveTroubleTaskDetail(taskDetailMap1);

											String detail_id1 = taskDetailMap1
													.get("DETAIL_ID")
													.toString();
											/**
											 * 保存上报来的设备记录，端子信息为空
											 */
											Map recordMap = new HashMap();
											recordMap.put("recordId",
													eqp_RecordId);
											recordMap.put("task_id", newTaskId);
											recordMap.put("TASK_NO", TASK_NO);
											recordMap.put("TASK_NAME", TASK_NAME);
											recordMap.put("detail_id",
													detail_id1);
											recordMap.put("eqpId", eqpId);
											recordMap.put("eqpAddress",
													eqpAddress);
											recordMap.put("eqpNo", eqpNo);
											recordMap.put("staffId", staffId);
											recordMap.put("eqpName", eqpName);
											recordMap.put("remarks", remarks);
											recordMap.put("info", info);
											recordMap.put("longitude",
													longitude);
											recordMap.put("latitude", latitude);
											recordMap.put("comments", comments);
											recordMap.put("port_id", "");
											recordMap.put("port_no", "");
											recordMap.put("port_name", "");
											recordMap.put("info", info);
											recordMap.put("descript", "");
											recordMap
													.put("isCheckOK",
															"1".equals(is_bill) ? 1
																	: 0);// 需要整改说明现场检查不通过，有问题
											recordMap.put("record_type", 0);
											recordMap.put("area_id",
													parentAreaId);
											recordMap.put("son_area_id",
													eqpAreaId);
											recordMap.put("isH", "");
											recordMap.put("type", "");
											checkOrderDao
													.insertEqpRecord(recordMap);
											
											recordMap.put("task_type", "3");
											recordMap.put("content", "新发起工单");
											/**
											 * 插入流程环节
											 */
											Map processMap2 = new HashMap();
											processMap2
													.put("task_id", newTaskId);
											processMap2.put("oper_staff",
													staffId);
											processMap2.put("status", 6);
											processMap2.put("remark", "检查提交");
											processMap2.put("receiver", replyId);
											processMap2.put("content", "生成整改工单并自动派发至维护员");
											checkProcessDao.addProcessNew(processMap2);
											
											/**
											 * 留痕记录保留
											 */			
											//checkOrderDao.saveEqpTrace(recordMap);

											if (!"".equals(eqpPhotoIds)) {
												// 保存设备的照片关系
												Map photoMap = new HashMap();
												photoMap.put("TASK_ID",
														newTaskId);
												photoMap.put("OBJECT_ID",
														eqp_RecordId);
												photoMap.put("DETAIL_ID",
														detail_id1);
												photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
												photoMap.put("REMARKS", "新发起工单");
												photoMap.put("RECORD_ID",
														eqp_RecordId);
												String[] photos = eqpPhotoIds
														.split(",");
												for (String photo : photos) {
													photoMap.put("PHOTO_ID",
															photo);
													checkOrderDao
															.insertPhotoRel(photoMap);
												}
											}

										}
									}
									// 来自IOM的单子，进行分组整合发单。
									List<Map> IOMGroup = checkOrderDao
											.getIOMGroup(newbatchId);

									for (Map IOM : IOMGroup) {
										List<Map> IOMPortDetails = new ArrayList<Map>();
										String otherSysStaffId = IOM.get(
												"OTHER_SYSTEM_STAFF_ID")
												.toString();
										for (Map port : portDetails) {
											if ((port
													.get("OTHER_SYSTEM_STAFF_ID")
													.toString())
													.equals(otherSysStaffId)) {
												IOMPortDetails.add(port);
											}
										}

										if (IOMPortDetails.size() > 0
												&& IOMPortDetails != null) {
											// 获取当前的数据进行分发
											// 在光网助手上面保存这个任务
											/**
											 * 插入任务表
											 */

											Map troubleTaskMap = new HashMap();
											String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
											String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
											troubleTaskMap
													.put("TASK_NO",TASK_NO);
											troubleTaskMap
													.put("TASK_NAME",TASK_NAME);
											troubleTaskMap.put("TASK_TYPE", 3);// 问题上报
											troubleTaskMap.put("STATUS_ID", 6);// 问题上报
											troubleTaskMap.put("INSPECTOR",
													staffId);
											troubleTaskMap.put("CREATE_STAFF",
													staffId);
											troubleTaskMap.put("SON_AREA_ID",
													eqpAreaId);
											troubleTaskMap.put("AREA_ID",
													parentAreaId);
											troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用
																				// 1不可用（不显示在待办列表）
											troubleTaskMap.put("REMARK",
													remarks);
											troubleTaskMap.put("INFO", info);
											troubleTaskMap.put("NO_EQPNO_FLAG",
													"0");// 无编码上报
											troubleTaskMap.put("NO_EQPNO_FLAG",
													"0");// 无编码上报

											troubleTaskMap.put("OLD_TASK_ID",
													taskId);// 老的task_id
											troubleTaskMap.put("IS_NEED_ZG",
													"1");// 是否整改,1需要整改
											troubleTaskMap.put("SBID", eqpId);// 设备id
											troubleTaskMap.put("MAINTOR", "");// 装维班组
											checkOrderDao
													.saveTroubleTask(troubleTaskMap);
											String newTaskId = troubleTaskMap
													.get("TASK_ID").toString();
											logger.info("【需要整改添加一张新的工单taskId】"
													+ newTaskId);
											taskid = newTaskId;
											/**
											 * 更新任务表
											 */
											Map oldTaskMap = new HashMap();
											oldTaskMap.put("task_id", taskId);
											oldTaskMap.put("remarks", remarks);
											oldTaskMap.put("staffId", staffId);
											oldTaskMap.put("is_need_zg", "1");// 原来的任务，需要整改
											checkOrderDao
													.updateTask(oldTaskMap);
											/**
											 * 端子信息
											 */
											for (Map IOMPort : IOMPortDetails) {
												if (innerArray.size() > 0
														&& innerArray != null) {
													for (int j = 0; j < innerArray
															.size(); j++) {
														JSONObject port = (JSONObject) innerArray
																.get(j);
														if (port.getString(
																"portId")
																.equals(IOMPort
																		.get("PHY_PORT_ID")
																		.toString())) {
															String eqpId_port = null == port
																	.get("eqpId") ? ""
																	: port.getString("eqpId");
															String eqpNo_port = null == port
																	.get("eqpNo") ? ""
																	: port.getString("eqpNo");
															String eqpName_port = null == port
																	.get("eqpName") ? ""
																	: port.getString("eqpName");
															String portId = port
																	.getString("portId");
															String dtsj_id = port
																	.getString("dtsj_id");
															String portNo = port
																	.getString("portNo");
															String portName = port
																	.getString("portName");
															String glbm = null == port
																	.get("glbm") ? ""
																	: port.getString("glbm");
															String glmc = null == port
																	.get("glmc") ? ""
																	: port.getString("glmc");
															String type = null;
															String eqpTypeId_port = null == port
																	.get("eqp_type_id") ? ""
																	: port.getString("eqp_type_id");
															if (glbm.startsWith("F")
																	&& "2530"
																			.equals(eqpTypeId_port)) {
																type = "1";

															} else {
																type = "0";
															}
															String isH = null == port
																	.get("isH") ? ""
																	: port.getString("isH");// 是否H光路，0不是，1是
															
															String truePortId = null == port
																	.get("truePortId") ? ""
																	: port.getString("truePortId");//正确端子
															
															String truePortNo = null == port
																	.get("truePortNo") ? ""
																	: port.getString("truePortNo");//正确编码
															
															String rightEqpId = null == port
																	.get("rightEqpId") ? ""
																	: port.getString("rightEqpId");//
															
															String rightEqpNo = null == port
																	.get("rightEqpNo") ? ""
																	: port.getString("rightEqpNo");//正确
															String glbh=port.getString("glbm");
															String changedPortId=port.getString("portId_new");//修改后的端子id
															String changedPortNo=port.getString("localPortNo");//修改后的端子编码
															String changedEqpId=port.getString("sbid_new");//修改后的设备ID
															String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
															/**
															 * 保存工单详细
															 */
															int portRecordId = checkOrderDao
																	.getRecordId();
															Map taskDetailMap1 = new HashMap();
															taskDetailMap1.put(
																	"TASK_ID",
																	newTaskId);
															taskDetailMap1
																	.put("INSPECT_OBJECT_ID",
																			portRecordId);
															taskDetailMap1
																	.put("INSPECT_OBJECT_NO",
																			portNo);
															taskDetailMap1
																	.put("INSPECT_OBJECT_TYPE",
																			1);
															taskDetailMap1
																	.put("CHECK_FLAG",
																			"1");
															taskDetailMap1.put(
																	"GLBM",
																	glbm);
															taskDetailMap1.put(
																	"GLMC",
																	glmc);
															taskDetailMap1.put(
																	"PORT_ID",
																	portId);
															taskDetailMap1.put(
																	"dtsj_id",
																	dtsj_id);
															taskDetailMap1.put("eqpId_port", "");
															taskDetailMap1.put("eqpNo_port", "");
															taskDetailMap1.put("eqpName_port", "");
															taskDetailMap1.put("orderNo", "");
															taskDetailMap1.put("orderMark", "");
															taskDetailMap1.put("actionType", "");
															taskDetailMap1.put("archive_time", "");
															checkOrderDao
																	.saveTroubleTaskDetail(taskDetailMap1);
															String detail_id1 = taskDetailMap1
																	.get("DETAIL_ID")
																	.toString();
															/**
															 * 保存上报来的检查端子记录
															 */
															String portPhotoIds = port
																	.getString("photoId");
															String reason = port
																	.getString("reason");
															String isCheckOK = port
																	.getString("isCheckOK");
															Map recordMap = new HashMap();
															recordMap
																	.put("recordId",
																			portRecordId);
															recordMap.put(
																	"task_id",
																	newTaskId);
															recordMap.put("TASK_NO",TASK_NO);
															recordMap.put("TASK_NAME",TASK_NAME);
															recordMap
																	.put("detail_id",
																			detail_id1);
															recordMap.put(
																	"eqpId",
																	eqpId_port);
															recordMap
																	.put("eqpAddress",
																			"");
															recordMap.put(
																	"eqpNo",
																	eqpNo_port);
															recordMap.put(
																	"staffId",
																	staffId);
															recordMap
																	.put("eqpName",
																			eqpName_port);
															recordMap.put(
																	"remarks",
																	remarks);
															recordMap.put(
																	"info",
																	info);
															recordMap
																	.put("longitude",
																			longitude);
															recordMap.put(
																	"latitude",
																	latitude);
															recordMap.put(
																	"comments",
																	comments);
															recordMap.put(
																	"port_id",
																	portId);
															recordMap.put(
																	"port_no",
																	portNo);
															recordMap
																	.put("port_name",
																			portName);
															recordMap.put(
																	"info",
																	info);
															recordMap.put(
																	"descript",
																	reason);
															resmap.put(
																	"descript",
																	reason);
															recordMap
																	.put("isCheckOK",
																			isCheckOK);
															recordMap
																	.put("record_type",
																			operType);// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
															recordMap
																	.put("area_id",
																			parentAreaId);
															recordMap
																	.put("son_area_id",
																			eqpAreaId);
															recordMap.put(
																	"isH", isH);
															recordMap.put(
																	"type",
																	type);
															
															if (!"".equals(orderId)
																	&& orderId != null) {
																recordMap
																		.put("orderId",
																				orderId);
															} else {
																recordMap
																		.put("orderId",
																				"");
															}
															if (!"".equals(OrderNo)
																	&& OrderNo != null) {
																recordMap
																		.put("OrderNo",
																				OrderNo);
															} else {
																recordMap
																		.put("OrderNo",
																				"");
															}
															// 留痕操作
															recordMap.put(
																	"isFrom",
																	"1");
															recordMap.put("glbm",glbh);
															recordMap.put("truePortId",truePortId);
															recordMap.put("truePortNo",truePortNo);
															recordMap.put("rightEqpId",rightEqpId);
															recordMap.put("rightEqpNo",rightEqpNo);
															recordMap.put("changedPortId",changedPortId);
															recordMap.put("changedPortNo",changedPortNo);
															recordMap.put("changedEqpId",changedEqpId);
															recordMap.put("changedEqpNo",changedEqpNo);
															checkOrderDao.insertEqpRecordNew(recordMap);
															/*checkOrderDao
																	.insertEqpRecord(recordMap);*/
															
															//将修改后的端子记录插入留痕记录表
															/*if(!"".equals(changedPortNo)){
																String content="";
																if(eqpId_port.equals(changedEqpId)){
																	content="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
																}else{
																	content ="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
																}
																recordMap.put("content", content);
																recordMap.put("task_type", "3");
																checkOrderDao.saveTrace(recordMap);
															}*/
															/**
															 * 保存端子照片关系
															 */
															Map photoMap = new HashMap();
															photoMap.put(
																	"TASK_ID",
																	newTaskId);
															photoMap.put(
																	"DETAIL_ID",
																	detail_id1);
															photoMap.put(
																	"OBJECT_ID",
																	portRecordId);
															photoMap.put(
																	"REMARKS",
																	"整改工单");
															photoMap.put(
																	"OBJECT_TYPE",
																	1);// 0，周期性任务，1：隐患上报工单，2,回单操作
															photoMap.put(
																	"RECORD_ID",
																	portRecordId);// 0，周期性任务，1：隐患上报工单，2,回单操作
															// photoMap.put("REMARKS",
															// photo);
															if (!"".equals(portPhotoIds)) {
																String[] photos = portPhotoIds
																		.split(",");
																for (String photo : photos) {
																	photoMap.put(
																			"PHOTO_ID",
																			photo);
																	checkOrderDao
																			.insertPhotoRel(photoMap);
																}
															}
														}
													}
												}
											}

											int eqp_RecordId = checkOrderDao
													.getRecordId();
											/**
											 * 保存工单详细
											 */
											Map taskDetailMap1 = new HashMap();
											taskDetailMap1.put("TASK_ID",
													newTaskId);
											taskDetailMap1.put(
													"INSPECT_OBJECT_ID",
													eqp_RecordId);
											taskDetailMap1.put(
													"INSPECT_OBJECT_NO", eqpNo);
											taskDetailMap1.put(
													"INSPECT_OBJECT_TYPE", 0);
											taskDetailMap1.put("CHECK_FLAG",
													"1");
											taskDetailMap1.put("GLBM", "");
											taskDetailMap1.put("GLMC", "");
											taskDetailMap1.put("PORT_ID", "");
											taskDetailMap1.put("dtsj_id", "");
											taskDetailMap1.put("eqpId_port", "");
											taskDetailMap1.put("eqpNo_port", "");
											taskDetailMap1.put("eqpName_port", "");
											taskDetailMap1.put("orderNo", "");
											taskDetailMap1.put("orderMark", "");
											taskDetailMap1.put("actionType", "");
											taskDetailMap1.put("archive_time", "");
											checkOrderDao
													.saveTroubleTaskDetail(taskDetailMap1);

											String detail_id1 = taskDetailMap1
													.get("DETAIL_ID")
													.toString();
											/**
											 * 保存上报来的设备记录，端子信息为空
											 */
											Map recordMap = new HashMap();
											recordMap.put("recordId",
													eqp_RecordId);
											recordMap.put("task_id", newTaskId);
											recordMap.put("TASK_NO", TASK_NO);
											recordMap.put("TASK_NAME", TASK_NAME);
											recordMap.put("detail_id",
													detail_id1);
											recordMap.put("eqpId", eqpId);
											recordMap.put("eqpAddress",
													eqpAddress);
											recordMap.put("eqpNo", eqpNo);
											recordMap.put("staffId", staffId);
											recordMap.put("eqpName", eqpName);
											recordMap.put("remarks", remarks);
											recordMap.put("info", info);
											recordMap.put("longitude",
													longitude);
											recordMap.put("latitude", latitude);
											recordMap.put("comments", comments);
											recordMap.put("port_id", "");
											recordMap.put("port_no", "");
											recordMap.put("port_name", "");
											recordMap.put("info", info);
											recordMap.put("descript", "");
											recordMap
													.put("isCheckOK",
															"1".equals(is_bill) ? 1
																	: 0);// 需要整改说明现场检查不通过，有问题
											recordMap.put("record_type", 0);
											recordMap.put("area_id",
													parentAreaId);
											recordMap.put("son_area_id",
													eqpAreaId);
											recordMap.put("isH", "");
											recordMap.put("type", "");
											checkOrderDao
													.insertEqpRecord(recordMap);
											recordMap.put("task_type", "3");
											recordMap.put("content", "新发起工单");
											/**
											 * 插入流程环节
											 */
											Map processMap2 = new HashMap();
											processMap2
													.put("task_id", newTaskId);
											processMap2.put("oper_staff",
													staffId);
											processMap2.put("status", 6);
											processMap2.put("receiver", "");
											processMap2.put("content", "生成整改工单并自动派发至集约化");
											processMap2.put("remark", "检查提交");
											checkProcessDao
													.addProcessNew(processMap);

											if (!"".equals(eqpPhotoIds)) {
												// 保存设备的照片关系
												Map photoMap = new HashMap();
												photoMap.put("TASK_ID",
														newTaskId);
												photoMap.put("OBJECT_ID",
														eqp_RecordId);
												photoMap.put("DETAIL_ID",
														detail_id1);
												photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
												photoMap.put("REMARKS", "新发起工单");
												photoMap.put("RECORD_ID",
														eqp_RecordId);
												String[] photos = eqpPhotoIds
														.split(",");
												for (String photo : photos) {
													photoMap.put("PHOTO_ID",
															photo);
													checkOrderDao
															.insertPhotoRel(photoMap);
												}
											}
											
											//checkOrderDao.saveEqpTrace(recordMap);  
										}
										// 获取IOM的数据分组
										for (Map IOMPort : IOMPortDetails) {
											String IOMportIdList = "";
											String IOMid_numberList = "";
											if (innerArray.size() > 0
													&& innerArray != null) {
												for (int j = 0; j < innerArray
														.size(); j++) {
													JSONObject port = (JSONObject) innerArray
															.get(j);
													if (port.getString("portId")
															.equals(IOMPort
																	.get("PHY_PORT_ID")
																	.toString())) {
														String portId = null == port
																.getString("portId") ? ""
																: port.getString("portId");
														String glbm = null == port
																.get("glbm") ? ""
																: port.getString("glbm");// 光路编码
														IOMportIdList += portId
																+ ",";
														if (null != glbm
																|| "" != glbm) {
															glbmMap = checkOrderDao
																	.queryGlbm(glbm);
															if (null != glbmMap) {
																for (Map glbmMap2 : glbmMap) {
																	String id_number = glbmMap2
																			.get("ID_NUMBER")
																			.toString();
																	if (null != id_number
																			|| "" != id_number) {
																		IOMid_numberList += id_number
																				+ ",";
																	} else {
																		IOMid_numberList += "";
																	}
																}
															} else {
																IOMid_numberList = "";
															}
														}
														// 在端子数据中剔除已经找到的数据
														innerArray.remove(j);
														j--;
													}
												}
											}
											if (IOMid_numberList.length() > 2) {
												IOMid_numberList = IOMid_numberList
														.substring(
																0,
																IOMid_numberList
																		.length() - 1);
											} else {
												IOMid_numberList = IOMid_numberList;
											}

											if (IOMportIdList.length() > 2) {
												IOMportIdList = IOMportIdList
														.substring(
																0,
																IOMportIdList
																		.length() - 1);
											} else {
												IOMportIdList = null;
											}

											// 给IOM推送工单
											xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
													+ "<IfInfo><sysRoute>"
													+ sysRoute
													+ "</sysRoute>"
													+ "<taskType>"
													+ taskTypes
													+ "</taskType>"
													+ "<gwMan>"
													+ staffName
													+ "</gwMan>"
													+ "<taskId>"
													+ taskid
													+ "</taskId>"
													+ "<gwManAccount>"
													+ IOMid_numberList
													+ "</gwManAccount>"
													+ "<equCbAccount>"
													+ contract_persion_nos
													+ "</equCbAccount>"
													+ "<gwPositionPersons>"
													+ PositionPersonsList
													+ "</gwPositionPersons>"
													+ "<equCode>"
													+ eqpNo
													+ "</equCode>"
													+ "<equName>"
													+ eqpName
													+ "</equName>"
													+ "<equType>"
													+ equType
													+ "</equType>"
													+ "<errorPortList>"
													+ IOMportIdList
													+ "</errorPortList>"
													+ "<gwContent>"
													+ resmap.get("descript")
													+ "</gwContent>"
													+ "</IfInfo>";

											WfworkitemflowLocator locator = new WfworkitemflowLocator();
											try {
												WfworkitemflowSoap11BindingStub stub = (WfworkitemflowSoap11BindingStub) locator
														.getWfworkitemflowHttpSoap11Endpoint();
												results = stub
														.outSysDispatchTask(xml); //
												resultss = stub
														.outSysDispatchTask(xmls);
												Document doc = null;
												doc = DocumentHelper
														.parseText(results);
												// 将字符串转为XML
												Element rootElt = doc
														.getRootElement();
												// 获取根节点
												Element IfResult = rootElt
														.element("IfResult");
												String IfResultinfo = IfResult
														.getText();
												// 0是成功 1是失败
												Map IfResultMap = new HashMap();
												IfResultMap.put(
														"Result_Status",
														IfResultinfo);
												IfResultMap.put("task_id",
														taskid);
												checkOrderDao
														.updateResultTask(IfResultMap);
												System.out.println(xml);
												System.out.println(results);
												result.put("msg1", results);
												// System.out.println(result);
												// outSysDispatchTask
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
												result.put("result", "001");
												result.put("desc",
														"线路工单推送接口调用失败。");
											}
										}
									}
									//排除正常端子
									if (innerArray.size() > 0 && innerArray != null) {
										for (int i = 0; i < innerArray.size(); i++) {
											JSONObject checkport = (JSONObject) innerArray.get(i);
											if("0".equals(checkport.getString("isCheckOK"))){
												innerArray.remove(i);
												i--;
											}
											
										}
									}
									// 剩下的端口 统一发给IOM
									if (innerArray.size() > 0
											&& innerArray != null) {
										// 光网助手保存任务

										// 在光网助手上面保存这个任务
										/**
										 * 插入任务表
										 */

										Map troubleTaskMap = new HashMap();
										String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
										String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
										troubleTaskMap.put("TASK_NO",TASK_NO);
										troubleTaskMap.put("TASK_NAME",TASK_NAME);
										troubleTaskMap.put("TASK_TYPE", 3);// 问题上报
										troubleTaskMap.put("STATUS_ID", 6);// 问题上报
										troubleTaskMap
												.put("INSPECTOR", staffId);
										troubleTaskMap.put("CREATE_STAFF",
												staffId);
										troubleTaskMap.put("SON_AREA_ID",
												eqpAreaId);
										troubleTaskMap.put("AREA_ID",
												parentAreaId);
										troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
										troubleTaskMap.put("REMARK", remarks);
										troubleTaskMap.put("INFO", info);
										troubleTaskMap
												.put("NO_EQPNO_FLAG", "0");// 无编码上报
										troubleTaskMap
												.put("NO_EQPNO_FLAG", "0");// 无编码上报

										troubleTaskMap.put("OLD_TASK_ID",
												taskId);// 老的task_id
										troubleTaskMap.put("IS_NEED_ZG", "1");// 是否整改,1需要整改
										troubleTaskMap.put("SBID", eqpId);// 设备id
										troubleTaskMap.put("MAINTOR", "");// 装维班组
										checkOrderDao
												.saveTroubleTask(troubleTaskMap);
										String newTaskId = troubleTaskMap.get(
												"TASK_ID").toString();
										logger.info("【需要整改添加一张新的工单taskId】"
												+ newTaskId);
										taskid = newTaskId;
										/**
										 * 更新任务表
										 */
										Map oldTaskMap = new HashMap();
										oldTaskMap.put("task_id", taskId);
										oldTaskMap.put("remarks", remarks);
										oldTaskMap.put("staffId", staffId);
										oldTaskMap.put("is_need_zg", "1");// 原来的任务，需要整改
										checkOrderDao.updateTask(oldTaskMap);
										/**
										 * 端子信息
										 */

										if (innerArray.size() > 0
												&& innerArray != null) {
											for (int j = 0; j < innerArray
													.size(); j++) {
												JSONObject port = (JSONObject) innerArray
														.get(j);
												String eqpId_port = null == port
														.get("eqpId") ? ""
														: port.getString("eqpId");
												String eqpNo_port = null == port
														.get("eqpNo") ? ""
														: port.getString("eqpNo");
												String eqpName_port = null == port
														.get("eqpName") ? ""
														: port.getString("eqpName");
												String portId = port
														.getString("portId");
												String dtsj_id = port
														.getString("dtsj_id");
												String portNo = port
														.getString("portNo");
												String portName = port
														.getString("portName");
												String glbm = null == port
														.get("glbm") ? ""
														: port.getString("glbm");
												String glmc = null == port
														.get("glmc") ? ""
														: port.getString("glmc");
												String type = null;
												String eqpTypeId_port = null == port
														.get("eqp_type_id") ? ""
														: port.getString("eqp_type_id");
												if (glbm.startsWith("F")
														&& "2530"
																.equals(eqpTypeId_port)) {
													type = "1";

												} else {
													type = "0";
												}
												String isH = null == port
														.get("isH") ? "" : port
														.getString("isH");// 是否H光路，0不是，1是
												
												String truePortId = null == port
														.get("truePortId") ? ""
														: port.getString("truePortId");//正确端子
												
												String truePortNo = null == port
														.get("truePortNo") ? ""
														: port.getString("truePortNo");//正确编码
												
												String rightEqpId = null == port
														.get("rightEqpId") ? ""
														: port.getString("rightEqpId");//
												
												String rightEqpNo = null == port
														.get("rightEqpNo") ? ""
														: port.getString("rightEqpNo");//正确
												String glbh=port.getString("glbm");
												String changedPortId=port.getString("portId_new");//修改后的端子id
												String changedPortNo=port.getString("localPortNo");//修改后的端子编码
												String changedEqpId=port.getString("sbid_new");//修改后的设备ID
												String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
												/**
												 * 保存工单详细
												 */
												int portRecordId = checkOrderDao
														.getRecordId();
												Map taskDetailMap1 = new HashMap();
												taskDetailMap1.put("TASK_ID",
														newTaskId);
												taskDetailMap1.put(
														"INSPECT_OBJECT_ID",
														portRecordId);
												taskDetailMap1.put(
														"INSPECT_OBJECT_NO",
														portNo);
												taskDetailMap1.put(
														"INSPECT_OBJECT_TYPE",
														1);
												taskDetailMap1.put(
														"CHECK_FLAG", "1");
												taskDetailMap1
														.put("GLBM", glbm);
												taskDetailMap1
														.put("GLMC", glmc);
												taskDetailMap1.put("PORT_ID",
														portId);
												taskDetailMap1.put("dtsj_id",
														dtsj_id);
												taskDetailMap1.put("eqpId_port", "");
												taskDetailMap1.put("eqpNo_port", "");
												taskDetailMap1.put("eqpName_port", "");
												taskDetailMap1.put("orderNo", "");
												taskDetailMap1.put("orderMark", "");
												taskDetailMap1.put("actionType", "");
												taskDetailMap1.put("archive_time", "");
												checkOrderDao
														.saveTroubleTaskDetail(taskDetailMap1);
												String detail_id1 = taskDetailMap1
														.get("DETAIL_ID")
														.toString();
												/**
												 * 保存上报来的检查端子记录
												 */
												String portPhotoIds = port
														.getString("photoId");
												String reason = port
														.getString("reason");
												String isCheckOK = port
														.getString("isCheckOK");
												Map recordMap = new HashMap();
												recordMap.put("recordId",
														portRecordId);
												recordMap.put("task_id",
														newTaskId);
												recordMap.put("TASK_NO",
														TASK_NO);
												recordMap.put("TASK_NAME",
														TASK_NAME);
												recordMap.put("detail_id",
														detail_id1);
												recordMap.put("eqpId",
														eqpId_port);
												recordMap.put("eqpAddress", "");
												recordMap.put("eqpNo",
														eqpNo_port);
												recordMap.put("staffId",
														staffId);
												recordMap.put("eqpName",
														eqpName_port);
												recordMap.put("remarks",
														remarks);
												recordMap.put("info", info);
												recordMap.put("longitude",
														longitude);
												recordMap.put("latitude",
														latitude);
												recordMap.put("comments",
														comments);
												recordMap
														.put("port_id", portId);
												recordMap
														.put("port_no", portNo);
												recordMap.put("port_name",
														portName);
												recordMap.put("info", info);
												recordMap.put("descript",
														reason);
												resmap.put("descript", reason);
												recordMap.put("isCheckOK",
														isCheckOK);
												recordMap.put("record_type",
														operType);// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
												recordMap.put("area_id",
														parentAreaId);
												recordMap.put("son_area_id",
														eqpAreaId);
												recordMap.put("isH", isH);
												recordMap.put("type", type);
												if (!"".equals(orderId)
														&& orderId != null) {
													recordMap.put("orderId",
															orderId);
												} else {
													recordMap
															.put("orderId", "");
												}
												if (!"".equals(OrderNo)
														&& OrderNo != null) {
													recordMap.put("OrderNo",
															OrderNo);
												} else {
													recordMap
															.put("OrderNo", "");
												}
												// 留痕操作
												recordMap.put("isFrom", "1");
												recordMap.put("glbm",glbh);
												recordMap.put("truePortId",truePortId);
												recordMap.put("truePortNo",truePortNo);
												recordMap.put("rightEqpId",rightEqpId);
												recordMap.put("rightEqpNo",rightEqpNo);
												recordMap.put("changedPortId",changedPortId);
												recordMap.put("changedPortNo",changedPortNo);
												recordMap.put("changedEqpId",changedEqpId);
												recordMap.put("changedEqpNo",changedEqpNo);
												checkOrderDao.insertEqpRecordNew(recordMap);
												/*checkOrderDao
														.insertEqpRecord(recordMap);*/
												//将修改后的端子记录插入留痕记录表
												/*if(!"".equals(changedPortNo)){
													String content="";
													if(eqpId_port.equals(changedEqpId)){
														content="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
													}else{
														content ="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
													}
													recordMap.put("content", content);
													recordMap.put("task_type", "3");
													checkOrderDao.saveTrace(recordMap);   
												}*/

												/**
												 * 保存端子照片关系
												 */
												Map photoMap = new HashMap();
												photoMap.put("TASK_ID",
														newTaskId);
												photoMap.put("DETAIL_ID",
														detail_id1);
												photoMap.put("OBJECT_ID",
														portRecordId);
												photoMap.put("REMARKS", "整改工单");
												photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
												photoMap.put("RECORD_ID",
														portRecordId);// 0，周期性任务，1：隐患上报工单，2,回单操作
												// photoMap.put("REMARKS",
												// photo);
												if (!"".equals(portPhotoIds)) {
													String[] photos = portPhotoIds
															.split(",");
													for (String photo : photos) {
														photoMap.put(
																"PHOTO_ID",
																photo);
														checkOrderDao
																.insertPhotoRel(photoMap);
													}
												}

											}
										}

										int eqp_RecordId = checkOrderDao
												.getRecordId();
										/**
										 * 保存工单详细
										 */
										Map taskDetailMap1 = new HashMap();
										taskDetailMap1
												.put("TASK_ID", newTaskId);
										taskDetailMap1.put("INSPECT_OBJECT_ID",
												eqp_RecordId);
										taskDetailMap1.put("INSPECT_OBJECT_NO",
												eqpNo);
										taskDetailMap1.put(
												"INSPECT_OBJECT_TYPE", 0);
										taskDetailMap1.put("CHECK_FLAG", "1");
										taskDetailMap1.put("GLBM", "");
										taskDetailMap1.put("GLMC", "");
										taskDetailMap1.put("PORT_ID", "");
										taskDetailMap1.put("dtsj_id", "");
										taskDetailMap1.put("eqpId_port", "");
										taskDetailMap1.put("eqpNo_port", "");
										taskDetailMap1.put("eqpName_port", "");
										taskDetailMap1.put("orderNo", "");
										taskDetailMap1.put("orderMark", "");
										taskDetailMap1.put("actionType", "");
										taskDetailMap1.put("archive_time", "");
										checkOrderDao
												.saveTroubleTaskDetail(taskDetailMap1);

										String detail_id1 = taskDetailMap1.get(
												"DETAIL_ID").toString();
										/**
										 * 保存上报来的设备记录，端子信息为空
										 */
										Map recordMap = new HashMap();
										recordMap.put("recordId", eqp_RecordId);
										recordMap.put("task_id", newTaskId);
										recordMap.put("TASK_NO", TASK_NO);
										recordMap.put("TASK_NAME", TASK_NAME);
										recordMap.put("detail_id", detail_id1);
										recordMap.put("eqpId", eqpId);
										recordMap.put("eqpAddress", eqpAddress);
										recordMap.put("eqpNo", eqpNo);
										recordMap.put("staffId", staffId);
										recordMap.put("eqpName", eqpName);
										recordMap.put("remarks", remarks);
										recordMap.put("info", info);
										recordMap.put("longitude", longitude);
										recordMap.put("latitude", latitude);
										recordMap.put("comments", comments);
										recordMap.put("port_id", "");
										recordMap.put("port_no", "");
										recordMap.put("port_name", "");
										recordMap.put("info", info);
										recordMap.put("descript", "");
										recordMap.put("isCheckOK",
												"1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
										recordMap.put("record_type", 0);
										recordMap.put("area_id", parentAreaId);
										recordMap.put("son_area_id", eqpAreaId);
										recordMap.put("isH", "");
										recordMap.put("type", "");
										checkOrderDao
												.insertEqpRecord(recordMap);
										recordMap.put("task_type", "3");
										recordMap.put("content", "新发起工单");
										/**
										 * 插入流程环节
										 */
										Map processMap2 = new HashMap();
										processMap2.put("task_id", newTaskId);
										processMap2.put("oper_staff", staffId);
										processMap2.put("status", 6);
										processMap2.put("remark", "检查提交");
										processMap2.put("receiver", "");
										processMap2.put("content", "生成整改工单并自动派发至集约化");
										checkProcessDao.addProcessNew(processMap2);

										if (!"".equals(eqpPhotoIds)) {
											// 保存设备的照片关系
											Map photoMap = new HashMap();
											photoMap.put("TASK_ID", newTaskId);
											photoMap.put("OBJECT_ID",
													eqp_RecordId);
											photoMap.put("DETAIL_ID",
													detail_id1);
											photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
											photoMap.put("REMARKS", "新发起工单");
											photoMap.put("RECORD_ID",
													eqp_RecordId);
											String[] photos = eqpPhotoIds
													.split(",");
											for (String photo : photos) {
												photoMap.put("PHOTO_ID", photo);
												checkOrderDao
														.insertPhotoRel(photoMap);
											}
										}
										/**
										 * 留痕记录保留
										 */			
										//checkOrderDao.saveEqpTrace(recordMap);
										
										String OtherportIdList = "";
										String Otherid_numberList = "";
										if (innerArray.size() > 0
												&& innerArray != null) {
											for (int j = 0; j < innerArray
													.size(); j++) {
												JSONObject port = (JSONObject) innerArray
														.get(j);
												String portId = null == port
														.getString("portId") ? ""
														: port.getString("portId");
												String glbm = null == port
														.get("glbm") ? ""
														: port.getString("glbm");// 光路编码
												OtherportIdList += portId + ",";
												if (null != glbm || "" != glbm) {
													glbmMap = checkOrderDao
															.queryGlbm(glbm);
													if (null != glbmMap) {
														for (Map glbmMap2 : glbmMap) {
															String id_number = glbmMap2
																	.get("ID_NUMBER")
																	.toString();
															if (null != id_number
																	|| "" != id_number) {
																Otherid_numberList += id_number
																		+ ",";
															} else {
																Otherid_numberList += "";
															}
														}
													} else {
														Otherid_numberList = "";
													}
												}
											}
										}
										if (Otherid_numberList.length() > 2) {
											Otherid_numberList = Otherid_numberList
													.substring(
															0,
															Otherid_numberList
																	.length() - 1);
										} else {
											Otherid_numberList = Otherid_numberList;
										}

										if (OtherportIdList.length() > 2) {
											OtherportIdList = OtherportIdList
													.substring(
															0,
															OtherportIdList
																	.length() - 1);
										} else {
											OtherportIdList = null;
										}
										// 给IOM推送工单
										xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
												+ "<IfInfo><sysRoute>"
												+ sysRoute
												+ "</sysRoute>"
												+ "<taskType>"
												+ taskTypes
												+ "</taskType>"
												+ "<gwMan>"
												+ staffName
												+ "</gwMan>"
												+ "<taskId>"
												+ taskid
												+ "</taskId>"
												+ "<gwManAccount>"
												+ Otherid_numberList
												+ "</gwManAccount>"
												+ "<equCbAccount>"
												+ contract_persion_nos
												+ "</equCbAccount>"
												+ "<gwPositionPersons>"
												+ PositionPersonsList
												+ "</gwPositionPersons>"
												+ "<equCode>"
												+ eqpNo
												+ "</equCode>"
												+ "<equName>"
												+ eqpName
												+ "</equName>"
												+ "<equType>"
												+ equType
												+ "</equType>"
												+ "<errorPortList>"
												+ OtherportIdList
												+ "</errorPortList>"
												+ "<gwContent>"
												+ resmap.get("descript")
												+ "</gwContent>" + "</IfInfo>";

										WfworkitemflowLocator locator = new WfworkitemflowLocator();
										try {
											WfworkitemflowSoap11BindingStub stub = (WfworkitemflowSoap11BindingStub) locator
													.getWfworkitemflowHttpSoap11Endpoint();
											results = stub
													.outSysDispatchTask(xml); //
											resultss = stub
													.outSysDispatchTask(xmls);
											Document doc = null;
											doc = DocumentHelper
													.parseText(results);
											// 将字符串转为XML
											Element rootElt = doc
													.getRootElement();
											// 获取根节点
											Element IfResult = rootElt
													.element("IfResult");
											String IfResultinfo = IfResult
													.getText();
											// 0是成功 1是失败
											Map IfResultMap = new HashMap();
											IfResultMap.put("Result_Status",
													IfResultinfo);
											IfResultMap.put("task_id", taskid);
											checkOrderDao
													.updateResultTask(IfResultMap);
											System.out.println(xml);
											System.out.println(results);
											result.put("msg1", results);
											// System.out.println(result);
											// outSysDispatchTask
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											result.put("result", "001");
											result.put("desc", "线路工单推送接口调用失败。");

										}
									}
								}
							}
						}
					}
					/**
					 * TODO 1、有端子检查的隐患上报(一键反馈有问题端子上报，不可预告抽查有问题端子上报)
					 * 3、无设备编码上报(一键反馈，无设备编码上报)
					 */
					if ("3".equals(operType) || "1".equals(operType)) {
						// 如果是分光器，则需要获取分光器的所属设备，不能以分光器生成任务，必须以箱子为单位生成检查任务或者整改任务
						String eqp_name = "";
						String task_id = "";
						sblx = checkOrderDao.getEqpType(eqpId_eqpNo_map);
						if ("1".equals(is_bill)) {
							// 首先获取端口数据
							// 整改有端子，需要将端子进行拆分，生成不同的taskId
							if (innerArray.size() > 0 && innerArray != null) {
								int batchId = checkOrderDao.getBatchId();
								int newbatchId = batchId + 1;
								List<Double> portIds = new ArrayList<Double>();
								if (innerArray.size() > 0 && innerArray != null) {
									for (int j = 0; j < innerArray.size(); j++) {
										JSONObject port = (JSONObject) innerArray
												.get(j);
										if("1".equals(port.getString("isCheckOK"))){
											Double portId = port.getDouble("portId");
											portIds.add(portId);
										}
									}
								}
								List<Map> portDetails = checkOrderDao.getPortDetails(portIds);
								for (Map port : portDetails) {
									Map obj = new HashMap();
									obj.put("BATCHID", newbatchId);
									obj.put("ORDER_ID", port.get("ORDER_ID")
											.toString());
									obj.put("PHY_EQP_ID", eqpId);
									obj.put("PHY_EQP_ID_PORT",
											port.get("PHY_EQP_ID").toString());
									obj.put("PHY_PORT_ID",
											port.get("PHY_PORT_ID").toString());
									if (port.get("REPLY_JOB_CSV") != null) {
										obj.put("REPLY_JOB_CSV",
												port.get("REPLY_JOB_CSV")
														.toString());
									} else {
										obj.put("REPLY_JOB_CSV", "000");
									}
									if (port.get("REPLY_JOB_IOM") != null) {
										obj.put("REPLY_JOB_IOM",
												port.get("REPLY_JOB_IOM")
														.toString());
									} else {
										obj.put("REPLY_JOB_IOM", "000");
									}
									obj.put("MARK", port.get("MARK").toString());
									SimpleDateFormat format = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss");
									obj.put("CREATE_TIME", format.parse(port
											.get("CREATE_TIME").toString()));

									if (port.get("OTHER_SYSTEM_STAFF_ID") != null) {
										obj.put("OTHER_SYSTEM_STAFF_ID", port
												.get("OTHER_SYSTEM_STAFF_ID")
												.toString());
									} else {
										obj.put("OTHER_SYSTEM_STAFF_ID", "");
									}

									if (port.get("SGGH") != null) {
										obj.put("SGGH", port.get("SGGH")
												.toString());
									} else {
										obj.put("SGGH", "");
									}
									checkOrderDao.insertBatchData(obj);
								}
								// 来自IOM的单子，进行分组整合发单。
								List<Map> IOMGroup = checkOrderDao
										.getIOMGroup(newbatchId);

								for (Map IOM : IOMGroup) {
									List<Map> IOMPortDetails = new ArrayList<Map>();
									String otherSysStaffId = IOM.get(
											"OTHER_SYSTEM_STAFF_ID").toString();
									for (Map port : portDetails) {
										if ((port.get("OTHER_SYSTEM_STAFF_ID")
												.toString())
												.equals(otherSysStaffId)) {
											IOMPortDetails.add(port);
										}
									}

									if (IOMPortDetails.size() > 0
											&& IOMPortDetails != null) {
										// 获取当前的数据进行分发
										// 在光网助手上面保存这个任务
										/**
										 * 插入任务表
										 */

										Map troubleTaskMap = new HashMap();
										String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
										String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
										troubleTaskMap
												.put("TASK_NO",TASK_NO);
										troubleTaskMap
												.put("TASK_NAME",TASK_NAME);
										troubleTaskMap.put("TASK_TYPE", data_source);// 问题上报
										troubleTaskMap.put("STATUS_ID", 6);// 问题上报
										troubleTaskMap
												.put("INSPECTOR", staffId);
										troubleTaskMap.put("CREATE_STAFF",
												staffId);
										troubleTaskMap.put("SON_AREA_ID",
												eqpAreaId);
										troubleTaskMap.put("AREA_ID",
												parentAreaId);
										troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
										troubleTaskMap.put("REMARK", remarks);
										troubleTaskMap.put("INFO", info);
										troubleTaskMap
												.put("NO_EQPNO_FLAG", "0");// 无编码上报
										troubleTaskMap
												.put("NO_EQPNO_FLAG", "0");// 无编码上报

										troubleTaskMap.put("OLD_TASK_ID",
												taskId);// 老的task_id
										troubleTaskMap.put("IS_NEED_ZG", "1");// 是否整改,1需要整改
										troubleTaskMap.put("SBID", eqpId);// 设备id
										troubleTaskMap.put("MAINTOR", "");// 装维班组
										checkOrderDao
												.saveTroubleTask(troubleTaskMap);
										String newTaskId = troubleTaskMap.get(
												"TASK_ID").toString();
										logger.info("【需要整改添加一张新的工单taskId】"
												+ newTaskId);
										taskid = newTaskId;
										/**
										 * 更新任务表
										 */
										Map oldTaskMap = new HashMap();
										oldTaskMap.put("task_id", taskId);
										oldTaskMap.put("remarks", remarks);
										oldTaskMap.put("staffId", staffId);
										oldTaskMap.put("is_need_zg", "1");// 原来的任务，需要整改
										checkOrderDao.updateTask(oldTaskMap);
										/**
										 * 端子信息
										 */
										for (Map IOMPort : IOMPortDetails) {
											if (innerArray.size() > 0
													&& innerArray != null) {
												for (int j = 0; j < innerArray
														.size(); j++) {
													JSONObject port = (JSONObject) innerArray
															.get(j);
													if (port.getString("portId")
															.equals(IOMPort
																	.get("PHY_PORT_ID")
																	.toString())) {
														String eqpId_port = null == port
																.get("eqpId") ? ""
																: port.getString("eqpId");
														String eqpNo_port = null == port
																.get("eqpNo") ? ""
																: port.getString("eqpNo");
														String eqpName_port = null == port
																.get("eqpName") ? ""
																: port.getString("eqpName");
														String portId = port
																.getString("portId");
														String dtsj_id = port
																.getString("dtsj_id");
														String portNo = port
																.getString("portNo");
														String portName = port
																.getString("portName");
														String glbm = null == port
																.get("glbm") ? ""
																: port.getString("glbm");
														String glmc = null == port
																.get("glmc") ? ""
																: port.getString("glmc");
														String type = null;
														String eqpTypeId_port = null == port
																.get("eqp_type_id") ? ""
																: port.getString("eqp_type_id");
														if (glbm.startsWith("F")
																&& "2530"
																		.equals(eqpTypeId_port)) {
															type = "1";

														} else {
															type = "0";
														}
														String isH = null == port
																.get("isH") ? ""
																: port.getString("isH");// 是否H光路，0不是，1是
														String truePortId = null == port
																.get("truePortId") ? ""
																: port.getString("truePortId");//正确端子
														
														String truePortNo = null == port
																.get("truePortNo") ? ""
																: port.getString("truePortNo");//正确编码
														
														String rightEqpId = null == port
																.get("rightEqpId") ? ""
																: port.getString("rightEqpId");//
														
														String rightEqpNo = null == port
																.get("rightEqpNo") ? ""
																: port.getString("rightEqpNo");//正确
														String glbh=port.getString("glbm");
														String changedPortId=port.getString("portId_new");//修改后的端子id
														String changedPortNo=port.getString("localPortNo");//修改后的端子编码
														String changedEqpId=port.getString("sbid_new");//修改后的设备ID
														String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
														/**
														 * 保存工单详细
														 */
														int portRecordId = checkOrderDao
																.getRecordId();
														Map taskDetailMap1 = new HashMap();
														taskDetailMap1.put(
																"TASK_ID",
																newTaskId);
														taskDetailMap1
																.put("INSPECT_OBJECT_ID",
																		portRecordId);
														taskDetailMap1
																.put("INSPECT_OBJECT_NO",
																		portNo);
														taskDetailMap1
																.put("INSPECT_OBJECT_TYPE",
																		1);
														taskDetailMap1.put(
																"CHECK_FLAG",
																"1");
														taskDetailMap1.put(
																"GLBM", glbm);
														taskDetailMap1.put(
																"GLMC", glmc);
														taskDetailMap1.put(
																"PORT_ID",
																portId);
														taskDetailMap1.put(
																"dtsj_id",
																dtsj_id);
														taskDetailMap1.put("eqpId_port", "");
														taskDetailMap1.put("eqpNo_port", "");
														taskDetailMap1.put("eqpName_port", "");
														taskDetailMap1.put("orderNo", "");
														taskDetailMap1.put("orderMark", "");
														taskDetailMap1.put("actionType", "");
														taskDetailMap1.put("archive_time", "");
														checkOrderDao
																.saveTroubleTaskDetail(taskDetailMap1);
														String detail_id1 = taskDetailMap1
																.get("DETAIL_ID")
																.toString();
														/**
														 * 保存上报来的检查端子记录
														 */
														String portPhotoIds = port
																.getString("photoId");
														String reason = port
																.getString("reason");
														String isCheckOK = port
																.getString("isCheckOK");
														Map recordMap = new HashMap();
														recordMap.put(
																"recordId",
																portRecordId);
														recordMap.put(
																"task_id",
																newTaskId);
														recordMap.put("TASK_NO",
																TASK_NO);
														recordMap.put("TASK_NAME",
																TASK_NAME);
														recordMap.put(
																"detail_id",
																detail_id1);
														recordMap.put("eqpId",
																eqpId_port);
														recordMap.put(
																"eqpAddress",
																"");
														recordMap.put("eqpNo",
																eqpNo_port);
														recordMap.put(
																"staffId",
																staffId);
														recordMap.put(
																"eqpName",
																eqpName_port);
														recordMap.put(
																"remarks",
																remarks);
														recordMap.put("info",
																info);
														recordMap.put(
																"longitude",
																longitude);
														recordMap.put(
																"latitude",
																latitude);
														recordMap.put(
																"comments",
																comments);
														recordMap.put(
																"port_id",
																portId);
														recordMap.put(
																"port_no",
																portNo);
														recordMap.put(
																"port_name",
																portName);
														recordMap.put("info",
																info);
														recordMap.put(
																"descript",
																reason);
														resmap.put("descript",
																reason);
														recordMap.put(
																"isCheckOK",
																isCheckOK);
														recordMap.put(
																"record_type",
																operType);// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
														recordMap.put(
																"area_id",
																parentAreaId);
														recordMap.put(
																"son_area_id",
																eqpAreaId);
														recordMap.put("isH",
																isH);
														recordMap.put("type",
																type);
														if (!"".equals(orderId)
																&& orderId != null) {
															recordMap.put(
																	"orderId",
																	orderId);
														} else {
															recordMap.put(
																	"orderId",
																	"");
														}
														if (!"".equals(OrderNo)
																&& OrderNo != null) {
															recordMap.put(
																	"OrderNo",
																	OrderNo);
														} else {
															recordMap.put(
																	"OrderNo",
																	"");
														}
														// 留痕操作
														recordMap.put("isFrom",
																"1");
														recordMap.put("glbm",glbh);
														recordMap.put("truePortId",truePortId);
														recordMap.put("truePortNo",truePortNo);
														recordMap.put("rightEqpId",rightEqpId);
														recordMap.put("rightEqpNo",rightEqpNo);
														recordMap.put("changedPortId",changedPortId);
														recordMap.put("changedPortNo",changedPortNo);
														recordMap.put("changedEqpId",changedEqpId);
														recordMap.put("changedEqpNo",changedEqpNo);
														checkOrderDao.insertEqpRecordNew(recordMap);
														/*checkOrderDao.insertEqpRecord(recordMap);*/  
														
														//将修改后的端子记录插入留痕记录表
														/*if(!"".equals(changedPortNo)){
															String content="";
															if(eqpId_port.equals(changedEqpId)){
																content="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
															}else{
																content ="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
															}
															recordMap.put("content", content);
															recordMap.put("task_type", "3");
															checkOrderDao.saveTrace(recordMap);  
														}*/
														//将修改后的端子插入流程表 tb_cablecheck_process
														if("0".equals(isCheckOK)){	
															if(!"".equals(changedPortNo)){
																String content="";
																if(eqpId_port.equals(changedEqpId)){
																	content=glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
																}else{
																	content =glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
																}
																recordMap.put("status", "66");//一键改
																recordMap.put("remark", "一键改");
																recordMap.put("content", content);
																recordMap.put("receiver", "");
																checkTaskDao.addProcessNew(recordMap);
															}
														}
														/**
														 * 保存端子照片关系
														 */
														Map photoMap = new HashMap();
														photoMap.put("TASK_ID",
																newTaskId);
														photoMap.put(
																"DETAIL_ID",
																detail_id1);
														photoMap.put(
																"OBJECT_ID",
																portRecordId);
														photoMap.put("REMARKS",
																"整改工单");
														photoMap.put(
																"OBJECT_TYPE",
																1);// 0，周期性任务，1：隐患上报工单，2,回单操作
														photoMap.put(
																"RECORD_ID",
																portRecordId);// 0，周期性任务，1：隐患上报工单，2,回单操作
														// photoMap.put("REMARKS",
														// photo);
														if (!"".equals(portPhotoIds)) {
															String[] photos = portPhotoIds
																	.split(",");
															for (String photo : photos) {
																photoMap.put(
																		"PHOTO_ID",
																		photo);
																checkOrderDao
																		.insertPhotoRel(photoMap);
															}
														}
													}
												}
											}
										}

										int eqp_RecordId = checkOrderDao
												.getRecordId();
										/**
										 * 保存工单详细
										 */
										Map taskDetailMap1 = new HashMap();
										taskDetailMap1
												.put("TASK_ID", newTaskId);
										taskDetailMap1.put("INSPECT_OBJECT_ID",
												eqp_RecordId);
										taskDetailMap1.put("INSPECT_OBJECT_NO",
												eqpNo);
										taskDetailMap1.put(
												"INSPECT_OBJECT_TYPE", 0);
										taskDetailMap1.put("CHECK_FLAG", "1");
										taskDetailMap1.put("GLBM", "");
										taskDetailMap1.put("GLMC", "");
										taskDetailMap1.put("PORT_ID", "");
										taskDetailMap1.put("dtsj_id", "");
										taskDetailMap1.put("eqpId_port", "");
										taskDetailMap1.put("eqpNo_port", "");
										taskDetailMap1.put("eqpName_port", "");
										taskDetailMap1.put("orderNo", "");
										taskDetailMap1.put("orderMark", "");
										taskDetailMap1.put("actionType", "");
										taskDetailMap1.put("archive_time", "");
										checkOrderDao
												.saveTroubleTaskDetail(taskDetailMap1);

										String detail_id1 = taskDetailMap1.get(
												"DETAIL_ID").toString();
										/**
										 * 保存上报来的设备记录，端子信息为空
										 */
										Map recordMap = new HashMap();
										recordMap.put("recordId", eqp_RecordId);
										recordMap.put("task_id", newTaskId);
										recordMap.put("TASK_NO", TASK_NO);
										recordMap.put("TASK_NAME", TASK_NAME);
										recordMap.put("detail_id", detail_id1);
										recordMap.put("eqpId", eqpId);
										recordMap.put("eqpAddress", eqpAddress);
										recordMap.put("eqpNo", eqpNo);
										recordMap.put("staffId", staffId);
										recordMap.put("eqpName", eqpName);
										recordMap.put("remarks", remarks);
										recordMap.put("info", info);
										recordMap.put("longitude", longitude);
										recordMap.put("latitude", latitude);
										recordMap.put("comments", comments);
										recordMap.put("port_id", "");
										recordMap.put("port_no", "");
										recordMap.put("port_name", "");
										recordMap.put("info", info);
										recordMap.put("descript", "");
										recordMap.put("isCheckOK",
												"1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
										recordMap.put("record_type", 0);
										recordMap.put("area_id", parentAreaId);
										recordMap.put("son_area_id", eqpAreaId);
										recordMap.put("isH", "");
										recordMap.put("type", "");
										checkOrderDao
												.insertEqpRecord(recordMap);
										recordMap.put("task_type", "3");
										recordMap.put("content", "新发起工单");
										/**
										 * 插入流程环节
										 */
										Map processMap = new HashMap();
										processMap.put("task_id", newTaskId);
										processMap.put("oper_staff", staffId);
										processMap.put("status", 6);
										processMap.put("remark", "检查提交");
										processMap.put("receiver", "");
										processMap.put("content", "生成整改单并自动派发");
										checkProcessDao.addProcessNew(processMap);

										if (!"".equals(eqpPhotoIds)) {
											// 保存设备的照片关系
											Map photoMap = new HashMap();
											photoMap.put("TASK_ID", newTaskId);
											photoMap.put("OBJECT_ID",
													eqp_RecordId);
											photoMap.put("DETAIL_ID",
													detail_id1);
											photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
											photoMap.put("REMARKS", "新发起工单");
											photoMap.put("RECORD_ID",
													eqp_RecordId);
											String[] photos = eqpPhotoIds
													.split(",");
											for (String photo : photos) {
												photoMap.put("PHOTO_ID", photo);
												checkOrderDao
														.insertPhotoRel(photoMap);
											}
										}
										//checkOrderDao.saveEqpTrace(recordMap);
									}  
									// 获取IOM的数据分组
									for (Map IOMPort : IOMPortDetails) {
										String IOMportIdList = "";
										String IOMid_numberList = "";
										if (innerArray.size() > 0
												&& innerArray != null) {
											for (int j = 0; j < innerArray
													.size(); j++) {
												JSONObject port = (JSONObject) innerArray
														.get(j);
												if (port.getString("portId")
														.equals(IOMPort.get(
																"PHY_PORT_ID")
																.toString())) {
													String portId = null == port
															.getString("portId") ? ""
															: port.getString("portId");
													String glbm = null == port
															.get("glbm") ? ""
															: port.getString("glbm");// 光路编码
													IOMportIdList += portId
															+ ",";
													if (null != glbm
															|| "" != glbm) {
														glbmMap = checkOrderDao
																.queryGlbm(glbm);
														if (null != glbmMap) {
															for (Map glbmMap2 : glbmMap) {
																String id_number = glbmMap2
																		.get("ID_NUMBER")
																		.toString();
																if (null != id_number
																		|| "" != id_number) {
																	IOMid_numberList += id_number
																			+ ",";
																} else {
																	IOMid_numberList += "";
																}
															}
														} else {
															IOMid_numberList = "";
														}
													}
													// 在端子数据中剔除已经找到的数据
													innerArray.remove(j);
													j--;
												}
											}
										}
										if (IOMid_numberList.length() > 2) {
											IOMid_numberList = IOMid_numberList
													.substring(
															0,
															IOMid_numberList
																	.length() - 1);
										} else {
											IOMid_numberList = IOMid_numberList;
										}

										if (IOMportIdList.length() > 2) {
											IOMportIdList = IOMportIdList
													.substring(0, IOMportIdList
															.length() - 1);
										} else {
											IOMportIdList = null;
										}

										// 给IOM推送工单
										xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
												+ "<IfInfo><sysRoute>"
												+ sysRoute
												+ "</sysRoute>"
												+ "<taskType>"
												+ taskTypes
												+ "</taskType>"
												+ "<gwMan>"
												+ staffName
												+ "</gwMan>"
												+ "<taskId>"
												+ taskid
												+ "</taskId>"
												+ "<gwManAccount>"
												+ IOMid_numberList
												+ "</gwManAccount>"
												+ "<equCbAccount>"
												+ contract_persion_nos
												+ "</equCbAccount>"
												+ "<gwPositionPersons>"
												+ PositionPersonsList
												+ "</gwPositionPersons>"
												+ "<equCode>"
												+ eqpNo
												+ "</equCode>"
												+ "<equName>"
												+ eqpName
												+ "</equName>"
												+ "<equType>"
												+ equType
												+ "</equType>"
												+ "<errorPortList>"
												+ IOMportIdList
												+ "</errorPortList>"
												+ "<gwContent>"
												+ resmap.get("descript")
												+ "</gwContent>" + "</IfInfo>";

										WfworkitemflowLocator locator = new WfworkitemflowLocator();
										try {
											WfworkitemflowSoap11BindingStub stub = (WfworkitemflowSoap11BindingStub) locator
													.getWfworkitemflowHttpSoap11Endpoint();
											results = stub
													.outSysDispatchTask(xml); //
											resultss = stub
													.outSysDispatchTask(xmls);
											Document doc = null;
											doc = DocumentHelper
													.parseText(results);
											// 将字符串转为XML
											Element rootElt = doc
													.getRootElement();
											// 获取根节点
											Element IfResult = rootElt
													.element("IfResult");
											String IfResultinfo = IfResult
													.getText();
											// 0是成功 1是失败
											Map IfResultMap = new HashMap();
											IfResultMap.put("Result_Status",
													IfResultinfo);
											IfResultMap.put("task_id", taskid);
											checkOrderDao
													.updateResultTask(IfResultMap);
											System.out.println(xml);
											System.out.println(results);
											result.put("msg1", results);
											// System.out.println(result);
											// outSysDispatchTask
										} catch (Exception e) {
											// TODO Auto-generated catch
											// block
											e.printStackTrace();
											result.put("result", "001");
											result.put("desc", "线路工单推送接口调用失败。");
										}
									}
								}

								// 来自综调的单子，进行分组整合发单。
								List<Map> CSVGroup = checkOrderDao
										.getCSVGroup(newbatchId);
								for (Map CSV : CSVGroup) {
									List<Map> CSVPortDetails = new ArrayList<Map>();
									String otherSysStaffId = CSV.get(
											"OTHER_SYSTEM_STAFF_ID").toString();
									
									//获取工单所在班组
									String team_id = "";
									String auditor ="";
									Map receiverMap=new HashMap();
									
									for (Map port : portDetails) {
										if ((port.get("OTHER_SYSTEM_STAFF_ID")
												.toString())
												.equals(otherSysStaffId)) {
											CSVPortDetails.add(port);
											team_id= port.get("TEAM_ID").toString(); 
										}
									}
									//通过班组获取审核员
									if(!"".equals(team_id)){
										receiverMap=checkTaskDao.getOrderReceiverOfBanZu(team_id);
										auditor=receiverMap.get("STAFF_ID").toString();
									}
									if (CSVPortDetails.size() > 0
											&& CSVPortDetails != null) {
										String replyId = checkOrderDao
												.getReplyId(otherSysStaffId);
										if ("".equals(replyId)
												|| replyId == null) {
											replyId = "";
										}
										if("".equals(replyId)){
											replyId=auditor;
										}
										//replyId=auditor;
										String TASK_NO = "3".equals(operType) ? eqpAddress: eqp_no+ "_"+ DateUtil.getDate("yyyyMMdd");
										String TASK_NAME = "3".equals(operType) ? eqpAddress: eqp_name+ "_"+ DateUtil.getDate("yyyyMMdd");
										if ("2530".equals(sblx)) {
											List<Map<String, Object>> eqpList = checkOrderDao
													.getEqp(eqpId_eqpNo_map);
											// 可能分光器外面箱子并不属于光交，光分，综合箱，ODF，所以在ins库中查不到对应箱子设备
											if (eqpList != null
													&& eqpList.size() > 0) {
												Map eqpMap = eqpList.get(0);
												eqp_id = eqpMap.get(
														"EQUIPMENT_ID")
														.toString();// 箱子设备ID
												eqp_no = eqpMap.get(
														"EQUIPMENT_CODE")
														.toString();// 箱子设备编码
												eqp_name = eqpMap.get(
														"EQUIPMENT_NAME")
														.toString();// 箱子设备名称

												// 保存先工单
												Map troubleTaskMap = new HashMap();
												troubleTaskMap
														.put("TASK_NO",TASK_NO);
												troubleTaskMap
														.put("TASK_NAME",TASK_NAME);
												troubleTaskMap.put("TASK_TYPE",
														data_source);// 隐患上报
												troubleTaskMap.put("STATUS_ID",
														"0".equals(is_bill) ? 8
																: 6);// 需要整改为隐患上报，无需整改直接归档
												troubleTaskMap.put("INSPECTOR",
														staffId);
												troubleTaskMap
														.put("CREATE_STAFF",
																staffId);
												troubleTaskMap
														.put("SON_AREA_ID",
																"1".equals(operType) ? eqpAreaId
																		: sonAreaId);
												troubleTaskMap
														.put("AREA_ID",
																"1".equals(operType) ? parentAreaId
																		: areaId);
												troubleTaskMap.put("ENABLE",
														"0".equals(is_bill) ? 1
																: 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																		// 1不可用（不显示在待办列表）
												troubleTaskMap.put("REMARK",
														remarks);
												troubleTaskMap
														.put("INFO", info);
												troubleTaskMap
														.put("NO_EQPNO_FLAG",
																"3".equals(operType) ? 1
																		: 0);// 无编码上报
												troubleTaskMap.put(
														"IS_NEED_ZG", is_bill);// 是否需要整改
												troubleTaskMap.put(
														"OLD_TASK_ID", "");// 老的task_id
												troubleTaskMap.put("SBID",
														eqp_id);// 设备id
												troubleTaskMap.put("COMPANY",
														"");// 设备id
												troubleTaskMap.put("MAINTOR",
														replyId);// 装维班组接单岗
												troubleTaskMap.put("auditor",
														auditor);// 审核员
											/*	checkOrderDao
														.saveTroubleTask(troubleTaskMap);*/
												checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
												task_id = troubleTaskMap.get(
														"TASK_ID").toString();
												taskid = task_id;
											} else {
												// 分光器没有找到对应的箱子，就以分光器为单位进行提交
												Map troubleTaskMap = new HashMap();
												troubleTaskMap
														.put("TASK_NO",
																"3".equals(operType) ? eqpAddress
																		: eqpNo
																				+ "_"
																				+ DateUtil
																						.getDate("yyyyMMdd"));
												troubleTaskMap
														.put("TASK_NAME",
																"3".equals(operType) ? eqpAddress
																		: eqpName
																				+ "_"
																				+ DateUtil
																						.getDate("yyyyMMdd"));
												troubleTaskMap.put("TASK_TYPE",
														data_source);// 隐患上报
												troubleTaskMap.put("STATUS_ID",
														"0".equals(is_bill) ? 8
																: 6);// 需要整改为隐患上报，无需整改直接归档
												troubleTaskMap.put("INSPECTOR",
														staffId);
												troubleTaskMap
														.put("CREATE_STAFF",
																staffId);
												troubleTaskMap
														.put("SON_AREA_ID",
																"1".equals(operType) ? eqpAreaId
																		: sonAreaId);
												troubleTaskMap
														.put("AREA_ID",
																"1".equals(operType) ? parentAreaId
																		: areaId);
												troubleTaskMap.put("ENABLE",
														"0".equals(is_bill) ? 1
																: 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																		// 1不可用（不显示在待办列表）
												troubleTaskMap.put("REMARK",
														remarks);
												troubleTaskMap
														.put("INFO", info);
												troubleTaskMap
														.put("NO_EQPNO_FLAG",
																"3".equals(operType) ? 1
																		: 0);// 无编码上报
												troubleTaskMap.put(
														"IS_NEED_ZG", is_bill);// 是否需要整改
												troubleTaskMap.put(
														"OLD_TASK_ID", "");// 老的task_id
												troubleTaskMap.put("SBID",
														eqpId);// 设备id
												troubleTaskMap.put("COMPANY",
														"");// 设备id
												troubleTaskMap.put("MAINTOR",
														replyId);// 装维班组接单岗
												troubleTaskMap.put("auditor",auditor);// 审核员
												checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
												/*checkOrderDao
														.saveTroubleTask(troubleTaskMap);*/
												task_id = troubleTaskMap.get(
														"TASK_ID").toString();
												taskid = task_id;
											}
										} else {
											// 保存先工单
											Map troubleTaskMap = new HashMap();
											troubleTaskMap
													.put("TASK_NO",
															"3".equals(operType) ? eqpAddress
																	: eqpNo
																			+ "_"
																			+ DateUtil
																					.getDate("yyyyMMdd"));
											troubleTaskMap
													.put("TASK_NAME",
															"3".equals(operType) ? eqpAddress
																	: eqpName
																			+ "_"
																			+ DateUtil
																					.getDate("yyyyMMdd"));
											troubleTaskMap.put("TASK_TYPE",
													data_source);// 隐患上报
											troubleTaskMap
													.put("STATUS_ID",
															"0".equals(is_bill) ? 8
																	: 6);// 需要整改为隐患上报，无需整改直接归档
											troubleTaskMap.put("INSPECTOR",
													staffId);
											troubleTaskMap.put("CREATE_STAFF",
													staffId);
											troubleTaskMap
													.put("SON_AREA_ID",
															"1".equals(operType) ? eqpAreaId
																	: sonAreaId);
											troubleTaskMap
													.put("AREA_ID",
															"1".equals(operType) ? parentAreaId
																	: areaId);
											troubleTaskMap
													.put("ENABLE",
															"0".equals(is_bill) ? 1
																	: 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
											troubleTaskMap.put("REMARK",
													remarks);
											troubleTaskMap.put("INFO", info);
											troubleTaskMap.put("NO_EQPNO_FLAG",
													"3".equals(operType) ? 1
															: 0);// 无编码上报
											troubleTaskMap.put("IS_NEED_ZG",
													is_bill);// 是否需要整改
											troubleTaskMap.put("OLD_TASK_ID",
													"");// 老的task_id
											troubleTaskMap.put("SBID", eqpId);// 设备id
											troubleTaskMap.put("COMPANY", "");// 设备id
											troubleTaskMap.put("MAINTOR",
													replyId);// 装维班组接单岗
											troubleTaskMap.put("auditor",auditor);// 审核员
											checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
											/*checkOrderDao
													.saveTroubleTask(troubleTaskMap);*/
											task_id = troubleTaskMap.get(
													"TASK_ID").toString();
											taskid = task_id;
										}
										// 保存地址
										for (int i = 0; i < addressArray.size(); i++) {
											int id = checkOrderDao
													.geteqpAddressId();
											JSONObject eqp = (JSONObject) addressArray
													.get(i);

											String eqpId_add = null == eqp
													.get("eqpId") ? "" : eqp
													.getString("eqpId");
											String eqpNo_add = null == eqp
													.get("eqpNo") ? "" : eqp
													.getString("eqpNo");
											String location_id = eqp
													.getString("locationId");
											String address_id = eqp
													.getString("addressId");
											String address_name = eqp
													.getString("addressName");
											String is_check_ok = eqp
													.getString("is_check_ok");
											String error_reason = null == eqp
													.get("error_reason") ? ""
													: eqp.getString("error_reason");

											Map adddressMap = new HashMap();
											adddressMap.put("id", id);
											adddressMap.put("phy_eqp_id",
													eqpId_add);
											adddressMap.put("phy_eqp_no",
													eqpNo_add);
											adddressMap.put("install_eqp_id",
													eqpId);
											adddressMap.put("location_id",
													location_id);
											adddressMap.put("address_id",
													address_id);
											adddressMap.put("address_name",
													address_name);
											adddressMap.put("is_check_ok",
													is_check_ok);
											adddressMap.put("error_reason",
													error_reason);
											adddressMap.put("task_id", task_id);
											adddressMap.put("create_staff",
													staffId);
											adddressMap.put("area_id",
													parentAreaId);
											adddressMap.put("son_area_id",
													eqpAreaId);
											checkOrderDao
													.insertEqpAddress(adddressMap);
										}
										// 保存相关端子信息
										for (Map CSVPort : CSVPortDetails) {
											for (int j = 0; j < innerArray
													.size(); j++) {
												JSONObject port = (JSONObject) innerArray
														.get(j);
												if (port.getString("portId")
														.equals(CSVPort.get(
																"PHY_PORT_ID")
																.toString())) {
													String eqpId_port = null == port
															.get("eqpId") ? ""
															: port.getString("eqpId");
													String eqpNo_port = null == port
															.get("eqpNo") ? ""
															: port.getString("eqpNo");
													String eqpName_port = null == port
															.get("eqpName") ? ""
															: port.getString("eqpName");
													String portId = port
															.getString("portId");
													String dtsj_id = port
															.getString("dtsj_id");

													String portNo = null == port
															.get("portNo") ? ""
															: port.getString("portNo");
													String portName = null == port
															.get("portName") ? ""
															: port.getString("portName");
													String portPhotoIds = port
															.getString("photoId");
													String reason = port
															.getString("reason");
													String isCheckOK = port
															.getString("isCheckOK");
													String glbm = null == port
															.get("glbm") ? ""
															: port.getString("glbm");
													String glmc = null == port
															.get("glmc") ? ""
															: port.getString("glmc");
													String isH = null == port
															.get("isH") ? ""
															: port.getString("isH");// 是否H光路，0不是，1是
													String type = null;
													String eqpTypeId_port = null == port
															.get("eqp_type_id") ? ""
															: port.getString("eqp_type_id");
													if (glbm.startsWith("F")
															&& "2530"
																	.equals(eqpTypeId_port)) {
														type = "1";// E综维 F装维

													} else {
														type = "0";
													}
													
													String truePortId = null == port
															.get("truePortId") ? ""
															: port.getString("truePortId");//正确端子

													String truePortNo = null == port
															.get("truePortNo") ? ""
															: port.getString("truePortNo");//正确编码

													String rightEqpId = null == port
															.get("rightEqpId") ? ""
															: port.getString("rightEqpId");//

													String rightEqpNo = null == port
															.get("rightEqpNo") ? ""
															: port.getString("rightEqpNo");//正确
													String glbh=port.getString("glbm");
													String changedPortId=port.getString("portId_new");//修改后的端子id
													String changedPortNo=port.getString("localPortNo");//修改后的端子编码
													String changedEqpId=port.getString("sbid_new");//修改后的设备ID
													String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
													// 保存工单详细
													int recordId = checkOrderDao
															.getRecordId();
													Map taskDetailMap = new HashMap();
													taskDetailMap.put(
															"TASK_ID", task_id);
													taskDetailMap
															.put("INSPECT_OBJECT_ID",
																	recordId);
													taskDetailMap
															.put("INSPECT_OBJECT_NO",
																	portNo);
													taskDetailMap
															.put("INSPECT_OBJECT_TYPE",
																	1);
													taskDetailMap.put(
															"CHECK_FLAG", "1");
													taskDetailMap.put("GLBM",
															glbm);
													taskDetailMap.put("GLMC",
															glmc);
													taskDetailMap.put(
															"PORT_ID", portId);
													taskDetailMap.put(
															"dtsj_id", dtsj_id);
													taskDetailMap.put("eqpId_port", "");
													taskDetailMap.put("eqpNo_port", "");
													taskDetailMap.put("eqpName_port", "");
													taskDetailMap.put("orderNo", "");
													taskDetailMap.put("orderMark", "");
													taskDetailMap.put("actionType", "");
													taskDetailMap.put("archive_time", "");
													checkOrderDao
															.saveTroubleTaskDetail(taskDetailMap);
													// 保存上报来的检查端子记录
													String detail_id = taskDetailMap
															.get("DETAIL_ID")
															.toString();
													Map recordMap = new HashMap();
													recordMap.put("recordId",
															recordId);
													recordMap.put("task_id",
															task_id);
													recordMap.put("TASK_NO",TASK_NO);
													recordMap.put("TASK_NAME",TASK_NAME);
													recordMap.put("detail_id",
															detail_id);
													recordMap.put("eqpId",
															eqpId_port);
													recordMap.put("eqpAddress",
															eqpAddress);
													recordMap.put("eqpNo",
															eqpNo_port);
													recordMap.put("staffId",
															staffId);
													recordMap.put("eqpName",
															eqpName_port);
													recordMap.put("info", info);
													recordMap.put("longitude",
															longitude);
													recordMap.put("latitude",
															latitude);
													recordMap.put("comments",
															comments);
													recordMap.put("port_id",
															portId);
													recordMap.put("port_no",
															portNo);
													recordMap.put("port_name",
															portName);
													recordMap.put("remark",
															remarks);
													recordMap.put("info", info);
													recordMap.put("descript",
															reason);
													resmap.put("descript",
															reason);
													recordMap.put("isCheckOK",
															isCheckOK);
													recordMap.put(
															"record_type", "1");// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
													recordMap.put("area_id",
															parentAreaId);
													recordMap.put(
															"son_area_id",
															eqpAreaId);
													recordMap.put("isH", isH);
													recordMap.put("type", type);
													checkOrderDao.insertEqpRecord(recordMap);
													// 保存端子照片关系
													Map photoMap = new HashMap();
													photoMap.put("TASK_ID",
															task_id);
													photoMap.put("DETAIL_ID",
															detail_id);
													photoMap.put("OBJECT_ID",
															recordId);
													photoMap.put("REMARKS",
															"隐患上报");
													photoMap.put("OBJECT_TYPE",
															1);// 0，周期性任务，1：隐患上报工单，2,回单操作
													photoMap.put("RECORD_ID",
															recordId);
													if (!"".equals(portPhotoIds)) {
														String[] photos = portPhotoIds
																.split(",");
														for (String photo : photos) {
															photoMap.put(
																	"PHOTO_ID",
																	photo);
															checkOrderDao
																	.insertPhotoRel(photoMap);
														}
													}
												}
											}
										}

										// 保存设备信息
										// 先获取下一个设备检查记录的ID
										int recordId = checkOrderDao
												.getRecordId();
										// 保存工单详细
										Map taskDetailMap = new HashMap();
										taskDetailMap.put("TASK_ID", task_id);
										taskDetailMap.put("INSPECT_OBJECT_ID",
												recordId);
										if ("2530".equals(sblx)
												&& !"".equals(eqp_no)) {
											taskDetailMap
													.put("INSPECT_OBJECT_NO",
															eqp_no);
										} else {
											taskDetailMap.put(
													"INSPECT_OBJECT_NO", eqpNo);
										}
										taskDetailMap.put(
												"INSPECT_OBJECT_TYPE", 0);
										taskDetailMap.put("CHECK_FLAG", "1");
										taskDetailMap.put("GLBM", "");
										taskDetailMap.put("GLMC", "");
										taskDetailMap.put("PORT_ID", "");
										taskDetailMap.put("dtsj_id", "");
										taskDetailMap.put("eqpId_port", "");
										taskDetailMap.put("eqpNo_port", "");
										taskDetailMap.put("eqpName_port", "");
										taskDetailMap.put("orderNo", "");
										taskDetailMap.put("orderMark", "");
										taskDetailMap.put("actionType", "");
										taskDetailMap.put("archive_time", "");
										checkOrderDao
												.saveTroubleTaskDetail(taskDetailMap);

										// 保存上报来的设备记录，端子信息为空
										String detail_id = taskDetailMap.get(
												"DETAIL_ID").toString();
										Map recordMap = new HashMap();
										recordMap.put("recordId", recordId);
										recordMap.put("task_id", task_id);
										recordMap.put("detail_id", detail_id);
										if ("2530".equals(sblx)
												&& !"".equals(eqp_no)) {
											recordMap.put("eqpId", eqp_id);
											recordMap
													.put("eqpNo",
															"3".equals(operType) ? eqpAddress
																	: eqp_no);
											recordMap
													.put("eqpName",
															"3".equals(operType) ? eqpAddress
																	: eqp_name);
										} else {
											recordMap.put("eqpId", eqpId);
											recordMap
													.put("eqpNo",
															"3".equals(operType) ? eqpAddress
																	: eqpNo);
											recordMap
													.put("eqpName",
															"3".equals(operType) ? eqpAddress
																	: eqpName);
										}
										recordMap.put("eqpAddress", eqpAddress);
										recordMap.put("staffId", staffId);
										recordMap.put("remarks", remarks);
										recordMap.put("info", info);
										recordMap.put("longitude", longitude);
										recordMap.put("latitude", latitude);
										recordMap.put("comments", comments);
										recordMap.put("port_id", "");
										recordMap.put("port_no", "");
										recordMap.put("port_name", "");
										recordMap.put("remark", remarks);
										recordMap.put("info", info);
										recordMap.put("descript", "");
										recordMap.put("isCheckOK",
												"1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
										recordMap.put("record_type",
												"3".equals(operType) ? 3 : 1);
										recordMap
												.put("area_id",
														"1".equals(operType) ? parentAreaId
																: areaId);
										recordMap.put("son_area_id", "1"
												.equals(operType) ? eqpAreaId
												: sonAreaId);
										recordMap.put("isH", "");
										recordMap.put("type", "");
										if (!"".equals(orderId)
												&& orderId != null) {
											recordMap.put("orderId", orderId);
										} else {
											recordMap.put("orderId", "");
										}
										if (!"".equals(OrderNo)
												&& OrderNo != null) {
											recordMap.put("OrderNo", OrderNo);
										} else {
											recordMap.put("OrderNo", "");
										}
										// 留痕操作
										recordMap.put("isFrom", "1");
										checkOrderDao
												.insertEqpRecord(recordMap);

										checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
										checkOrderDao
												.updateLastUpdateTime(recordMap);
										// 插入流程环节
										Map processMap = new HashMap();
										processMap.put("task_id", task_id);
										processMap.put("oper_staff", staffId);
										if ("1".equals(is_bill)) {
											processMap.put("status", 4);
											processMap.put("remark", "检查提交");
											processMap.put("receiver", "");
											processMap.put("content", "生成整改工单");
										} else {
											processMap.put("status", 8);
											processMap.put("remark", "检查提交");
											processMap.put("receiver", "");
											processMap.put("content", "无需整改直接归档");
										}
										checkProcessDao.addProcess(processMap);
										// 保存设备的照片关系
										if (!"".equals(eqpPhotoIds)) {
											Map photoMap = new HashMap();
											photoMap.put("TASK_ID", task_id);
											photoMap.put("OBJECT_ID", recordId);
											photoMap.put("DETAIL_ID", detail_id);
											photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
											photoMap.put("REMARKS", "隐患上报");
											photoMap.put("RECORD_ID", recordId);
											String[] photos = eqpPhotoIds
													.split(",");
											for (String photo : photos) {
												photoMap.put("PHOTO_ID", photo);
												checkOrderDao
														.insertPhotoRel(photoMap);
											}
										}
									}
								}
								//排除正常端子
								if (innerArray.size() > 0 && innerArray != null) {
									for (int i = 0; i < innerArray.size(); i++) {
										JSONObject checkport = (JSONObject) innerArray.get(i);
										if("0".equals(checkport.getString("isCheckOK"))){
											innerArray.remove(i);
											i--;
										}
										
									}
								}
								// 剩下的端口 统一发给IOM
								if (innerArray.size() > 0 && innerArray != null) {
									// 光网助手保存任务
									// 在光网助手上面保存这个任务
									/**
									 * 插入任务表
									 */
									Map troubleTaskMap = new HashMap();
									String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
									String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
									troubleTaskMap.put("TASK_NO",TASK_NO );
									troubleTaskMap.put("TASK_NAME",TASK_NAME);
									troubleTaskMap.put("TASK_TYPE", 3);// 问题上报
									troubleTaskMap.put("STATUS_ID", 6);// 问题上报
									troubleTaskMap.put("INSPECTOR", staffId);
									troubleTaskMap.put("CREATE_STAFF", staffId);
									troubleTaskMap
											.put("SON_AREA_ID", eqpAreaId);
									troubleTaskMap.put("AREA_ID", parentAreaId);
									troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用
																		// 1不可用（不显示在待办列表）
									troubleTaskMap.put("REMARK", remarks);
									troubleTaskMap.put("INFO", info);
									troubleTaskMap.put("NO_EQPNO_FLAG", "0");// 无编码上报
									troubleTaskMap.put("OLD_TASK_ID", taskId);// 老的task_id
									troubleTaskMap.put("IS_NEED_ZG", "1");// 是否整改,1需要整改
									troubleTaskMap.put("SBID", eqpId);// 设备id
									troubleTaskMap.put("MAINTOR", "");// 装维班组
									checkOrderDao
											.saveTroubleTask(troubleTaskMap);
									String newTaskId = troubleTaskMap.get(
											"TASK_ID").toString();
									logger.info("【需要整改添加一张新的工单taskId】"
											+ newTaskId);
									taskid = newTaskId;
									/**
									 * 更新任务表
									 */
									Map oldTaskMap = new HashMap();
									oldTaskMap.put("task_id", taskId);
									oldTaskMap.put("remarks", remarks);
									oldTaskMap.put("staffId", staffId);
									oldTaskMap.put("is_need_zg", "1");// 原来的任务，需要整改
									checkOrderDao.updateTask(oldTaskMap);
									/**
									 * 端子信息
									 */

									if (innerArray.size() > 0
											&& innerArray != null) {
										for (int j = 0; j < innerArray.size(); j++) {
											JSONObject port = (JSONObject) innerArray
													.get(j);
											String eqpId_port = null == port
													.get("eqpId") ? "" : port
													.getString("eqpId");
											String eqpNo_port = null == port
													.get("eqpNo") ? "" : port
													.getString("eqpNo");
											String eqpName_port = null == port
													.get("eqpName") ? "" : port
													.getString("eqpName");
											String portId = port
													.getString("portId");
											String dtsj_id = port
													.getString("dtsj_id");
											String portNo = port
													.getString("portNo");
											String portName = port
													.getString("portName");
											String glbm = null == port
													.get("glbm") ? "" : port
													.getString("glbm");
											String glmc = null == port
													.get("glmc") ? "" : port
													.getString("glmc");
											String type = null;
											String eqpTypeId_port = null == port
													.get("eqp_type_id") ? ""
													: port.getString("eqp_type_id");
											if (glbm.startsWith("F")
													&& "2530"
															.equals(eqpTypeId_port)) {
												type = "1";

											} else {
												type = "0";
											}
											String isH = null == port
													.get("isH") ? "" : port
													.getString("isH");// 是否H光路，0不是，1是
											String truePortId = null == port
													.get("truePortId") ? ""
													: port.getString("truePortId");//正确端子

											String truePortNo = null == port
													.get("truePortNo") ? ""
													: port.getString("truePortNo");//正确编码

											String rightEqpId = null == port
													.get("rightEqpId") ? ""
													: port.getString("rightEqpId");//

											String rightEqpNo = null == port
													.get("rightEqpNo") ? ""
													: port.getString("rightEqpNo");//正确
											String glbh=port.getString("glbm");
											String changedPortId=port.getString("portId_new");//修改后的端子id
											String changedPortNo=port.getString("localPortNo");//修改后的端子编码
											String changedEqpId=port.getString("sbid_new");//修改后的设备ID
											String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
											/**
											 * 保存工单详细
											 */
											int portRecordId = checkOrderDao
													.getRecordId();
											Map taskDetailMap1 = new HashMap();
											taskDetailMap1.put("TASK_ID",
													newTaskId);
											taskDetailMap1.put(
													"INSPECT_OBJECT_ID",
													portRecordId);
											taskDetailMap1
													.put("INSPECT_OBJECT_NO",
															portNo);
											taskDetailMap1.put(
													"INSPECT_OBJECT_TYPE", 1);
											taskDetailMap1.put("CHECK_FLAG",
													"1");
											taskDetailMap1.put("GLBM", glbm);
											taskDetailMap1.put("GLMC", glmc);
											taskDetailMap1.put("PORT_ID",
													portId);
											taskDetailMap1.put("dtsj_id",
													dtsj_id);
											taskDetailMap1.put("eqpId_port", "");
											taskDetailMap1.put("eqpNo_port", "");
											taskDetailMap1.put("eqpName_port", "");
											taskDetailMap1.put("orderNo", "");
											taskDetailMap1.put("orderMark", "");
											taskDetailMap1.put("actionType", "");
											taskDetailMap1.put("archive_time", "");
											checkOrderDao
													.saveTroubleTaskDetail(taskDetailMap1);
											String detail_id1 = taskDetailMap1
													.get("DETAIL_ID")
													.toString();
											/**
											 * 保存上报来的检查端子记录
											 */
											String portPhotoIds = port
													.getString("photoId");
											String reason = port
													.getString("reason");
											String isCheckOK = port
													.getString("isCheckOK");
											Map recordMap = new HashMap();
											recordMap.put("recordId",
													portRecordId);
											recordMap.put("task_id", newTaskId);
											recordMap.put("detail_id",
													detail_id1);
											recordMap.put("eqpId", eqpId_port);
											recordMap.put("eqpAddress", "");
											recordMap.put("eqpNo", eqpNo_port);
											recordMap.put("staffId", staffId);
											recordMap.put("eqpName",
													eqpName_port);
											recordMap.put("remarks", remarks);
											recordMap.put("info", info);
											recordMap.put("longitude",
													longitude);
											recordMap.put("latitude", latitude);
											recordMap.put("comments", comments);
											recordMap.put("port_id", portId);
											recordMap.put("port_no", portNo);
											recordMap
													.put("port_name", portName);
											recordMap.put("info", info);
											recordMap.put("descript", reason);
											resmap.put("descript", reason);
											recordMap.put("isCheckOK",
													isCheckOK);
											recordMap.put("record_type",
													operType);// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
											recordMap.put("area_id",
													parentAreaId);
											recordMap.put("son_area_id",
													eqpAreaId);
											recordMap.put("isH", isH);
											recordMap.put("type", type);
											if (!"".equals(orderId)
													&& orderId != null) {
												recordMap.put("orderId",
														orderId);
											} else {
												recordMap.put("orderId", "");
											}
											if (!"".equals(OrderNo)
													&& OrderNo != null) {
												recordMap.put("OrderNo",
														OrderNo);
											} else {
												recordMap.put("OrderNo", "");
											}
											// 留痕操作
											recordMap.put("isFrom", "1");
											recordMap.put("TASK_NO",TASK_NO);
											recordMap.put("TASK_NAME",TASK_NAME);
											recordMap.put("glbm",glbm);
											recordMap.put("truePortId",truePortId);
											recordMap.put("truePortNo",truePortNo);
											recordMap.put("rightEqpId",rightEqpId);
											recordMap.put("rightEqpNo",rightEqpNo);
											recordMap.put("changedPortId",changedPortId);
											recordMap.put("changedPortNo",changedPortNo);
											recordMap.put("changedEqpId",changedEqpId);
											recordMap.put("changedEqpNo",changedEqpNo);
											checkOrderDao.insertEqpRecordNew(recordMap);
											/*checkOrderDao
													.insertEqpRecord(recordMap);*/
											//将修改后的端子记录插入留痕记录表
											/*if(!"".equals(changedPortNo)){
												String content="";
												if(eqpId_port.equals(changedEqpId)){
													content="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
												}else{
													content ="一键改: "+glbh+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
												}
												recordMap.put("content", content);
												recordMap.put("task_type", "3");
												checkOrderDao.saveTrace(recordMap);  
											}*/
											//将修改后的端子插入流程表 tb_cablecheck_process
											if("0".equals(isCheckOK)){	
												if(!"".equals(changedPortNo)){
													String content="";
													if(eqpId_port.equals(changedEqpId)){
														content=glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
													}else{
														content =glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
													}
													recordMap.put("status", "66");//一键改
													recordMap.put("remark", "一键改");
													recordMap.put("receiver", "");
													recordMap.put("content", content);
													checkTaskDao.addProcessNew(recordMap);
												}
											}
											/**
											 * 保存端子照片关系
											 */
											Map photoMap = new HashMap();
											photoMap.put("TASK_ID", newTaskId);
											photoMap.put("DETAIL_ID",
													detail_id1);
											photoMap.put("OBJECT_ID",
													portRecordId);
											photoMap.put("REMARKS", "整改工单");
											photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
											photoMap.put("RECORD_ID",
													portRecordId);// 0，周期性任务，1：隐患上报工单，2,回单操作
											// photoMap.put("REMARKS",
											// photo);
											if (!"".equals(portPhotoIds)) {
												String[] photos = portPhotoIds
														.split(",");
												for (String photo : photos) {
													photoMap.put("PHOTO_ID",
															photo);
													checkOrderDao
															.insertPhotoRel(photoMap);
												}
											}

										}
									}

									int eqp_RecordId = checkOrderDao
											.getRecordId();
									/**
									 * 保存工单详细
									 */
									Map taskDetailMap1 = new HashMap();
									taskDetailMap1.put("TASK_ID", newTaskId);
									taskDetailMap1.put("INSPECT_OBJECT_ID",
											eqp_RecordId);
									taskDetailMap1.put("INSPECT_OBJECT_NO",
											eqpNo);
									taskDetailMap1
											.put("INSPECT_OBJECT_TYPE", 0);
									taskDetailMap1.put("CHECK_FLAG", "1");
									taskDetailMap1.put("GLBM", "");
									taskDetailMap1.put("GLMC", "");
									taskDetailMap1.put("PORT_ID", "");
									taskDetailMap1.put("dtsj_id", "");
									taskDetailMap1.put("eqpId_port", "");
									taskDetailMap1.put("eqpNo_port", "");
									taskDetailMap1.put("eqpName_port", "");
									taskDetailMap1.put("orderNo", "");
									taskDetailMap1.put("orderMark", "");
									taskDetailMap1.put("actionType", "");
									taskDetailMap1.put("archive_time", "");
									checkOrderDao
											.saveTroubleTaskDetail(taskDetailMap1);

									String detail_id1 = taskDetailMap1.get(
											"DETAIL_ID").toString();
									/**
									 * 保存上报来的设备记录，端子信息为空
									 */
									Map recordMap = new HashMap();
									recordMap.put("recordId", eqp_RecordId);
									recordMap.put("task_id", newTaskId);
									recordMap.put("detail_id", detail_id1);
									recordMap.put("eqpId", eqpId);
									recordMap.put("eqpAddress", eqpAddress);
									recordMap.put("eqpNo", eqpNo);
									recordMap.put("staffId", staffId);
									recordMap.put("eqpName", eqpName);
									recordMap.put("remarks", remarks);
									recordMap.put("info", info);
									recordMap.put("longitude", longitude);
									recordMap.put("latitude", latitude);
									recordMap.put("comments", comments);
									recordMap.put("port_id", "");
									recordMap.put("port_no", "");
									recordMap.put("port_name", "");
									recordMap.put("info", info);
									recordMap.put("descript", "");
									recordMap.put("isCheckOK",
											"1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
									recordMap.put("record_type", 0);
									recordMap.put("area_id", parentAreaId);
									recordMap.put("son_area_id", eqpAreaId);
									recordMap.put("isH", "");
									recordMap.put("type", "");
									checkOrderDao.insertEqpRecord(recordMap);
									/**
									 * 插入流程环节
									 */
									Map processMap = new HashMap();
									processMap.put("task_id", newTaskId);
									processMap.put("oper_staff", staffId);
									processMap.put("status", 6);
									processMap.put("remark", "检查提交");
									processMap.put("receiver", "");
									processMap.put("content", "生成整改工单并自动派发");
									checkProcessDao.addProcessNew(processMap);

									if (!"".equals(eqpPhotoIds)) {
										// 保存设备的照片关系
										Map photoMap = new HashMap();
										photoMap.put("TASK_ID", newTaskId);
										photoMap.put("OBJECT_ID", eqp_RecordId);
										photoMap.put("DETAIL_ID", detail_id1);
										photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
										photoMap.put("REMARKS", "新发起工单");
										photoMap.put("RECORD_ID", eqp_RecordId);
										String[] photos = eqpPhotoIds
												.split(",");
										for (String photo : photos) {
											photoMap.put("PHOTO_ID", photo);
											checkOrderDao
													.insertPhotoRel(photoMap);
										}
									}
									String OtherportIdList = "";
									String Otherid_numberList = "";
									if (innerArray.size() > 0
											&& innerArray != null) {
										for (int j = 0; j < innerArray.size(); j++) {
											JSONObject port = (JSONObject) innerArray
													.get(j);
											String portId = null == port
													.getString("portId") ? ""
													: port.getString("portId");
											String glbm = null == port
													.get("glbm") ? "" : port
													.getString("glbm");// 光路编码
											OtherportIdList += portId + ",";
											if (null != glbm || "" != glbm) {
												glbmMap = checkOrderDao
														.queryGlbm(glbm);
												if (null != glbmMap) {
													for (Map glbmMap2 : glbmMap) {
														String id_number = glbmMap2
																.get("ID_NUMBER")
																.toString();
														if (null != id_number
																|| "" != id_number) {
															Otherid_numberList += id_number
																	+ ",";
														} else {
															Otherid_numberList += "";
														}
													}
												} else {
													Otherid_numberList = "";
												}
											}
										}
									}
									if (Otherid_numberList.length() > 2) {
										Otherid_numberList = Otherid_numberList
												.substring(0,
														Otherid_numberList
																.length() - 1);
									} else {
										Otherid_numberList = Otherid_numberList;
									}

									if (OtherportIdList.length() > 2) {
										OtherportIdList = OtherportIdList
												.substring(0, OtherportIdList
														.length() - 1);
									} else {
										OtherportIdList = null;
									}
									// 给IOM推送工单
									xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
											+ "<IfInfo><sysRoute>"
											+ sysRoute
											+ "</sysRoute>"
											+ "<taskType>"
											+ taskTypes
											+ "</taskType>"
											+ "<gwMan>"
											+ staffName
											+ "</gwMan>"
											+ "<taskId>"
											+ taskid
											+ "</taskId>"
											+ "<gwManAccount>"
											+ Otherid_numberList
											+ "</gwManAccount>"
											+ "<equCbAccount>"
											+ contract_persion_nos
											+ "</equCbAccount>"
											+ "<gwPositionPersons>"
											+ PositionPersonsList
											+ "</gwPositionPersons>"
											+ "<equCode>"
											+ eqpNo
											+ "</equCode>"
											+ "<equName>"
											+ eqpName
											+ "</equName>"
											+ "<equType>"
											+ equType
											+ "</equType>"
											+ "<errorPortList>"
											+ OtherportIdList
											+ "</errorPortList>"
											+ "<gwContent>"
											+ resmap.get("descript")
											+ "</gwContent>" + "</IfInfo>";

									WfworkitemflowLocator locator = new WfworkitemflowLocator();
									try {
										WfworkitemflowSoap11BindingStub stub = (WfworkitemflowSoap11BindingStub) locator
												.getWfworkitemflowHttpSoap11Endpoint();
										results = stub.outSysDispatchTask(xml); //
										resultss = stub
												.outSysDispatchTask(xmls);
										Document doc = null;
										doc = DocumentHelper.parseText(results);
										// 将字符串转为XML
										Element rootElt = doc.getRootElement();
										// 获取根节点
										Element IfResult = rootElt
												.element("IfResult");
										String IfResultinfo = IfResult
												.getText();
										// 0是成功 1是失败
										Map IfResultMap = new HashMap();
										IfResultMap.put("Result_Status",
												IfResultinfo);
										IfResultMap.put("task_id", taskid);
										checkOrderDao
												.updateResultTask(IfResultMap);
										System.out.println(xml);
										System.out.println(results);
										result.put("msg1", results);
										// System.out.println(result);
										// outSysDispatchTask
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										result.put("result", "001");
										result.put("desc", "线路工单推送接口调用失败。");

									}
								}

							} else {
								if ("2530".equals(sblx)) {
									List<Map<String, Object>> eqpList = checkOrderDao
											.getEqp(eqpId_eqpNo_map);
									// 可能分光器外面箱子并不属于光交，光分，综合箱，ODF，所以在ins库中查不到对应箱子设备
									if (eqpList != null && eqpList.size() > 0) {
										Map eqpMap = eqpList.get(0);
										eqp_id = eqpMap.get("EQUIPMENT_ID")
												.toString();// 箱子设备ID
										eqp_no = eqpMap.get("EQUIPMENT_CODE")
												.toString();// 箱子设备编码
										eqp_name = eqpMap.get("EQUIPMENT_NAME")
												.toString();// 箱子设备名称

										// 保存先工单
										Map troubleTaskMap = new HashMap();
										troubleTaskMap
												.put("TASK_NO",
														"3".equals(operType) ? eqpAddress
																: eqp_no
																		+ "_"
																		+ DateUtil
																				.getDate("yyyyMMdd"));
										troubleTaskMap
												.put("TASK_NAME",
														"3".equals(operType) ? eqpAddress
																: eqp_name
																		+ "_"
																		+ DateUtil
																				.getDate("yyyyMMdd"));
										troubleTaskMap.put("TASK_TYPE",
												data_source);// 隐患上报
										troubleTaskMap.put("STATUS_ID",
												"0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
										troubleTaskMap
												.put("INSPECTOR", staffId);
										troubleTaskMap.put("CREATE_STAFF",
												staffId);
										troubleTaskMap.put("SON_AREA_ID", "1"
												.equals(operType) ? eqpAreaId
												: sonAreaId);
										troubleTaskMap
												.put("AREA_ID",
														"1".equals(operType) ? parentAreaId
																: areaId);
										troubleTaskMap.put("ENABLE",
												"0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																				// 1不可用（不显示在待办列表）
										troubleTaskMap.put("REMARK", remarks);
										troubleTaskMap.put("INFO", info);
										troubleTaskMap.put("NO_EQPNO_FLAG",
												"3".equals(operType) ? 1 : 0);// 无编码上报
										troubleTaskMap.put("IS_NEED_ZG",
												is_bill);// 是否需要整改
										troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
										troubleTaskMap.put("SBID", eqp_id);// 设备id
										troubleTaskMap.put("COMPANY", "");// 设备id
										troubleTaskMap.put("MAINTOR", "");// 设备id

										checkOrderDao
												.saveTroubleTask(troubleTaskMap);
										task_id = troubleTaskMap.get("TASK_ID")
												.toString();
										taskid = task_id;
									} else {
										// 分光器没有找到对应的箱子，就以分光器为单位进行提交
										Map troubleTaskMap = new HashMap();
										troubleTaskMap
												.put("TASK_NO",
														"3".equals(operType) ? eqpAddress
																: eqpNo
																		+ "_"
																		+ DateUtil
																				.getDate("yyyyMMdd"));
										troubleTaskMap
												.put("TASK_NAME",
														"3".equals(operType) ? eqpAddress
																: eqpName
																		+ "_"
																		+ DateUtil
																				.getDate("yyyyMMdd"));
										troubleTaskMap.put("TASK_TYPE",
												data_source);// 隐患上报
										troubleTaskMap.put("STATUS_ID",
												"0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
										troubleTaskMap
												.put("INSPECTOR", staffId);
										troubleTaskMap.put("CREATE_STAFF",
												staffId);
										troubleTaskMap.put("SON_AREA_ID", "1"
												.equals(operType) ? eqpAreaId
												: sonAreaId);
										troubleTaskMap
												.put("AREA_ID",
														"1".equals(operType) ? parentAreaId
																: areaId);
										troubleTaskMap.put("ENABLE",
												"0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																				// 1不可用（不显示在待办列表）
										troubleTaskMap.put("REMARK", remarks);
										troubleTaskMap.put("INFO", info);
										troubleTaskMap.put("NO_EQPNO_FLAG",
												"3".equals(operType) ? 1 : 0);// 无编码上报
										troubleTaskMap.put("IS_NEED_ZG",
												is_bill);// 是否需要整改
										troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
										troubleTaskMap.put("SBID", eqpId);// 设备id
										troubleTaskMap.put("COMPANY", "");// 设备id
										troubleTaskMap.put("MAINTOR", "");// 设备id

										checkOrderDao
												.saveTroubleTask(troubleTaskMap);
										task_id = troubleTaskMap.get("TASK_ID")
												.toString();
										taskid = task_id;
									}
								} else {
									// 保存先工单
									Map troubleTaskMap = new HashMap();
									troubleTaskMap
											.put("TASK_NO",
													"3".equals(operType) ? eqpAddress
															: eqpNo
																	+ "_"
																	+ DateUtil
																			.getDate("yyyyMMdd"));
									troubleTaskMap
											.put("TASK_NAME",
													"3".equals(operType) ? eqpAddress
															: eqpName
																	+ "_"
																	+ DateUtil
																			.getDate("yyyyMMdd"));
									troubleTaskMap
											.put("TASK_TYPE", data_source);// 隐患上报
									troubleTaskMap.put("STATUS_ID",
											"0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
									troubleTaskMap.put("INSPECTOR", staffId);
									troubleTaskMap.put("CREATE_STAFF", staffId);
									troubleTaskMap.put("SON_AREA_ID", "1"
											.equals(operType) ? eqpAreaId
											: sonAreaId);
									troubleTaskMap.put("AREA_ID", "1"
											.equals(operType) ? parentAreaId
											: areaId);
									troubleTaskMap.put("ENABLE",
											"0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
									troubleTaskMap.put("REMARK", remarks);
									troubleTaskMap.put("INFO", info);
									troubleTaskMap.put("NO_EQPNO_FLAG",
											"3".equals(operType) ? 1 : 0);// 无编码上报
									troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
									troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
									troubleTaskMap.put("SBID", eqpId);// 设备id
									troubleTaskMap.put("COMPANY", "");// 设备id
									troubleTaskMap.put("MAINTOR", "");// 设备id

									checkOrderDao
											.saveTroubleTask(troubleTaskMap);
									task_id = troubleTaskMap.get("TASK_ID")
											.toString();
									taskid = task_id;

								}
								// JSONArray addressArray
								// =json.getJSONArray("allEqpAddress");

								for (int i = 0; i < addressArray.size(); i++) {
									int id = checkOrderDao.geteqpAddressId();
									JSONObject eqp = (JSONObject) addressArray
											.get(i);

									String eqpId_add = null == eqp.get("eqpId") ? ""
											: eqp.getString("eqpId");
									String eqpNo_add = null == eqp.get("eqpNo") ? ""
											: eqp.getString("eqpNo");
									String location_id = eqp
											.getString("locationId");
									String address_id = eqp
											.getString("addressId");
									String address_name = eqp
											.getString("addressName");
									String is_check_ok = eqp
											.getString("is_check_ok");
									String error_reason = null == eqp
											.get("error_reason") ? "" : eqp
											.getString("error_reason");

									Map adddressMap = new HashMap();
									adddressMap.put("id", id);
									adddressMap.put("phy_eqp_id", eqpId_add);
									adddressMap.put("phy_eqp_no", eqpNo_add);
									adddressMap.put("install_eqp_id", eqpId);
									adddressMap.put("location_id", location_id);
									adddressMap.put("address_id", address_id);
									adddressMap.put("address_name",
											address_name);
									adddressMap.put("is_check_ok", is_check_ok);
									adddressMap.put("error_reason",
											error_reason);
									adddressMap.put("task_id", task_id);
									adddressMap.put("create_staff", staffId);
									adddressMap.put("area_id", parentAreaId);
									adddressMap.put("son_area_id", eqpAreaId);
									checkOrderDao.insertEqpAddress(adddressMap);
								}
								int recordId = checkOrderDao.getRecordId();
								// 保存工单详细
								Map taskDetailMap = new HashMap();
								taskDetailMap.put("TASK_ID", task_id);
								taskDetailMap
										.put("INSPECT_OBJECT_ID", recordId);
								if ("2530".equals(sblx) && !"".equals(eqp_no)) {
									taskDetailMap.put("INSPECT_OBJECT_NO",
											eqp_no);
								} else {
									taskDetailMap.put("INSPECT_OBJECT_NO",
											eqpNo);
								}
								taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
								taskDetailMap.put("CHECK_FLAG", "1");
								taskDetailMap.put("GLBM", "");
								taskDetailMap.put("GLMC", "");
								taskDetailMap.put("PORT_ID", "");
								taskDetailMap.put("dtsj_id", "");
								taskDetailMap.put("eqpId_port", "");
								taskDetailMap.put("eqpNo_port", "");
								taskDetailMap.put("eqpName_port", "");
								taskDetailMap.put("orderNo", "");
								taskDetailMap.put("orderMark", "");
								taskDetailMap.put("actionType", "");
								taskDetailMap.put("archive_time", "");
								checkOrderDao
										.saveTroubleTaskDetail(taskDetailMap);

								// 保存上报来的设备记录，端子信息为空
								String detail_id = taskDetailMap.get(
										"DETAIL_ID").toString();
								Map recordMap = new HashMap();
								recordMap.put("recordId", recordId);
								recordMap.put("task_id", task_id);
								recordMap.put("detail_id", detail_id);
								if ("2530".equals(sblx) && !"".equals(eqp_no)) {
									recordMap.put("eqpId", eqp_id);
									recordMap.put("eqpNo",
											"3".equals(operType) ? eqpAddress
													: eqp_no);
									recordMap.put("eqpName", "3"
											.equals(operType) ? eqpAddress
											: eqp_name);
								} else {
									recordMap.put("eqpId", eqpId);
									recordMap.put("eqpNo",
											"3".equals(operType) ? eqpAddress
													: eqpNo);
									recordMap.put("eqpName", "3"
											.equals(operType) ? eqpAddress
											: eqpName);
								}
								recordMap.put("eqpAddress", eqpAddress);
								recordMap.put("staffId", staffId);
								recordMap.put("remarks", remarks);
								recordMap.put("info", info);
								recordMap.put("longitude", longitude);
								recordMap.put("latitude", latitude);
								recordMap.put("comments", comments);
								recordMap.put("port_id", "");
								recordMap.put("port_no", "");
								recordMap.put("port_name", "");
								recordMap.put("remark", remarks);
								recordMap.put("info", info);
								recordMap.put("descript", "");
								recordMap.put("isCheckOK",
										"1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
								recordMap.put("record_type",
										"3".equals(operType) ? 3 : 1);
								recordMap.put("area_id",
										"1".equals(operType) ? parentAreaId
												: areaId);
								recordMap.put("son_area_id", "1"
										.equals(operType) ? eqpAreaId
										: sonAreaId);
								recordMap.put("isH", "");
								recordMap.put("type", "");
								checkOrderDao.insertEqpRecord(recordMap);
								checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
								checkOrderDao.updateLastUpdateTime(recordMap);
								// 插入流程环节
								Map processMap = new HashMap();
								processMap.put("task_id", task_id);
								processMap.put("oper_staff", staffId);
								if ("1".equals(is_bill)) {
									processMap.put("status", 4);
									processMap.put("remark", "检查提交");
									processMap.put("receiver", "");
									processMap.put("content", "生成整改工单");
								} else {
									processMap.put("status", 8);
									processMap.put("remark", "检查提交");
									processMap.put("receiver", "");
									processMap.put("content", "无需整改直接归档");
								}
								checkProcessDao.addProcessNew(processMap);
								// 保存设备的照片关系
								if (!"".equals(eqpPhotoIds)) {
									Map photoMap = new HashMap();
									photoMap.put("TASK_ID", task_id);
									photoMap.put("OBJECT_ID", recordId);
									photoMap.put("DETAIL_ID", detail_id);
									photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
									photoMap.put("REMARKS", "隐患上报");
									photoMap.put("RECORD_ID", recordId);
									String[] photos = eqpPhotoIds.split(",");
									for (String photo : photos) {
										photoMap.put("PHOTO_ID", photo);
										checkOrderDao.insertPhotoRel(photoMap);
									}
								}

							}

						} else {
							if ("2530".equals(sblx)) {
								List<Map<String, Object>> eqpList = checkOrderDao
										.getEqp(eqpId_eqpNo_map);
								// 可能分光器外面箱子并不属于光交，光分，综合箱，ODF，所以在ins库中查不到对应箱子设备
								if (eqpList != null && eqpList.size() > 0) {
									Map eqpMap = eqpList.get(0);
									eqp_id = eqpMap.get("EQUIPMENT_ID")
											.toString();// 箱子设备ID
									eqp_no = eqpMap.get("EQUIPMENT_CODE")
											.toString();// 箱子设备编码
									eqp_name = eqpMap.get("EQUIPMENT_NAME")
											.toString();// 箱子设备名称

									// 保存先工单
									Map troubleTaskMap = new HashMap();
									troubleTaskMap
											.put("TASK_NO",
													"3".equals(operType) ? eqpAddress
															: eqp_no
																	+ "_"
																	+ DateUtil
																			.getDate("yyyyMMdd"));
									troubleTaskMap
											.put("TASK_NAME",
													"3".equals(operType) ? eqpAddress
															: eqp_name
																	+ "_"
																	+ DateUtil
																			.getDate("yyyyMMdd"));
									troubleTaskMap
											.put("TASK_TYPE", data_source);// 隐患上报
									troubleTaskMap.put("STATUS_ID",
											"0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
									troubleTaskMap.put("INSPECTOR", staffId);
									troubleTaskMap.put("CREATE_STAFF", staffId);
									troubleTaskMap.put("SON_AREA_ID", "1"
											.equals(operType) ? eqpAreaId
											: sonAreaId);
									troubleTaskMap.put("AREA_ID", "1"
											.equals(operType) ? parentAreaId
											: areaId);
									troubleTaskMap.put("ENABLE",
											"0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
									troubleTaskMap.put("REMARK", remarks);
									troubleTaskMap.put("INFO", info);
									troubleTaskMap.put("NO_EQPNO_FLAG",
											"3".equals(operType) ? 1 : 0);// 无编码上报
									troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
									troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
									troubleTaskMap.put("SBID", eqp_id);// 设备id
									troubleTaskMap.put("COMPANY", "");// 设备id
									troubleTaskMap.put("MAINTOR", "");// 设备id

									checkOrderDao
											.saveTroubleTask(troubleTaskMap);
									task_id = troubleTaskMap.get("TASK_ID")
											.toString();
									taskid = task_id;
								} else {
									// 分光器没有找到对应的箱子，就以分光器为单位进行提交
									Map troubleTaskMap = new HashMap();
									troubleTaskMap
											.put("TASK_NO",
													"3".equals(operType) ? eqpAddress
															: eqpNo
																	+ "_"
																	+ DateUtil
																			.getDate("yyyyMMdd"));
									troubleTaskMap
											.put("TASK_NAME",
													"3".equals(operType) ? eqpAddress
															: eqpName
																	+ "_"
																	+ DateUtil
																			.getDate("yyyyMMdd"));
									troubleTaskMap
											.put("TASK_TYPE", data_source);// 隐患上报
									troubleTaskMap.put("STATUS_ID",
											"0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
									troubleTaskMap.put("INSPECTOR", staffId);
									troubleTaskMap.put("CREATE_STAFF", staffId);
									troubleTaskMap.put("SON_AREA_ID", "1"
											.equals(operType) ? eqpAreaId
											: sonAreaId);
									troubleTaskMap.put("AREA_ID", "1"
											.equals(operType) ? parentAreaId
											: areaId);
									troubleTaskMap.put("ENABLE",
											"0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
									troubleTaskMap.put("REMARK", remarks);
									troubleTaskMap.put("INFO", info);
									troubleTaskMap.put("NO_EQPNO_FLAG",
											"3".equals(operType) ? 1 : 0);// 无编码上报
									troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
									troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
									troubleTaskMap.put("SBID", eqpId);// 设备id
									troubleTaskMap.put("COMPANY", "");// 设备id
									troubleTaskMap.put("MAINTOR", "");// 设备id
									checkOrderDao
											.saveTroubleTask(troubleTaskMap);
									task_id = troubleTaskMap.get("TASK_ID")
											.toString();
									taskid = task_id;
								}
							} else {
								// 保存先工单
								Map troubleTaskMap = new HashMap();
								troubleTaskMap.put("TASK_NO", "3"
										.equals(operType) ? eqpAddress : eqpNo
										+ "_" + DateUtil.getDate("yyyyMMdd"));
								troubleTaskMap
										.put("TASK_NAME",
												"3".equals(operType) ? eqpAddress
														: eqpName
																+ "_"
																+ DateUtil
																		.getDate("yyyyMMdd"));
								troubleTaskMap.put("TASK_TYPE", data_source);// 隐患上报
								troubleTaskMap.put("STATUS_ID",
										"0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
								troubleTaskMap.put("INSPECTOR", staffId);
								troubleTaskMap.put("CREATE_STAFF", staffId);
								troubleTaskMap.put("SON_AREA_ID", "1"
										.equals(operType) ? eqpAreaId
										: sonAreaId);
								troubleTaskMap.put("AREA_ID", "1"
										.equals(operType) ? parentAreaId
										: areaId);
								troubleTaskMap.put("ENABLE",
										"0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																		// 1不可用（不显示在待办列表）
								troubleTaskMap.put("REMARK", remarks);
								troubleTaskMap.put("INFO", info);
								troubleTaskMap.put("NO_EQPNO_FLAG",
										"3".equals(operType) ? 1 : 0);// 无编码上报
								troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
								troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
								troubleTaskMap.put("SBID", eqpId);// 设备id
								troubleTaskMap.put("COMPANY", "");// 设备id
								troubleTaskMap.put("MAINTOR", "");// 设备id
								checkOrderDao.saveTroubleTask(troubleTaskMap);
								task_id = troubleTaskMap.get("TASK_ID")
										.toString();
								taskid = task_id;

							}
							// JSONArray addressArray
							// =json.getJSONArray("allEqpAddress");

							for (int i = 0; i < addressArray.size(); i++) {
								int id = checkOrderDao.geteqpAddressId();
								JSONObject eqp = (JSONObject) addressArray
										.get(i);

								String eqpId_add = null == eqp.get("eqpId") ? ""
										: eqp.getString("eqpId");
								String eqpNo_add = null == eqp.get("eqpNo") ? ""
										: eqp.getString("eqpNo");
								String location_id = eqp
										.getString("locationId");
								String address_id = eqp.getString("addressId");
								String address_name = eqp
										.getString("addressName");
								String is_check_ok = eqp
										.getString("is_check_ok");
								String error_reason = null == eqp
										.get("error_reason") ? "" : eqp
										.getString("error_reason");

								Map adddressMap = new HashMap();
								adddressMap.put("id", id);
								adddressMap.put("phy_eqp_id", eqpId_add);
								adddressMap.put("phy_eqp_no", eqpNo_add);
								adddressMap.put("install_eqp_id", eqpId);
								adddressMap.put("location_id", location_id);
								adddressMap.put("address_id", address_id);
								adddressMap.put("address_name", address_name);
								adddressMap.put("is_check_ok", is_check_ok);
								adddressMap.put("error_reason", error_reason);
								adddressMap.put("task_id", task_id);
								adddressMap.put("create_staff", staffId);
								adddressMap.put("area_id", parentAreaId);
								adddressMap.put("son_area_id", eqpAreaId);
								checkOrderDao.insertEqpAddress(adddressMap);
							}

							// 如果是隐患上报，有端子上报
							if ("1".equals(operType)) {
								for (int j = 0; j < innerArray.size(); j++) {
									JSONObject port = (JSONObject) innerArray
											.get(j);
									String eqpId_port = null == port
											.get("eqpId") ? "" : port
											.getString("eqpId");
									String eqpNo_port = null == port
											.get("eqpNo") ? "" : port
											.getString("eqpNo");
									String eqpName_port = null == port
											.get("eqpName") ? "" : port
											.getString("eqpName");
									String portId = port.getString("portId");
									String dtsj_id = port.getString("dtsj_id");

									String portNo = null == port.get("portNo") ? ""
											: port.getString("portNo");
									String portName = null == port
											.get("portName") ? "" : port
											.getString("portName");
									String portPhotoIds = port
											.getString("photoId");
									String reason = port.getString("reason");
									String isCheckOK = port
											.getString("isCheckOK");
									String glbm = null == port.get("glbm") ? ""
											: port.getString("glbm");
									String glmc = null == port.get("glmc") ? ""
											: port.getString("glmc");
									String isH = null == port.get("isH") ? ""
											: port.getString("isH");// 是否H光路，0不是，1是
									String type = null;
									String eqpTypeId_port = null == port
											.get("eqp_type_id") ? "" : port
											.getString("eqp_type_id");
									if (glbm.startsWith("F")
											&& "2530".equals(eqpTypeId_port)) {
										type = "1";// E综维 F装维

									} else {
										type = "0";
									}
									// 保存工单详细
									int recordId = checkOrderDao.getRecordId();
									Map taskDetailMap = new HashMap();
									taskDetailMap.put("TASK_ID", task_id);
									taskDetailMap.put("INSPECT_OBJECT_ID",
											recordId);
									taskDetailMap.put("INSPECT_OBJECT_NO",
											portNo);
									taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
									taskDetailMap.put("CHECK_FLAG", "1");
									taskDetailMap.put("GLBM", glbm);
									taskDetailMap.put("GLMC", glmc);
									taskDetailMap.put("PORT_ID", portId);
									taskDetailMap.put("dtsj_id", dtsj_id);
									taskDetailMap.put("eqpId_port", "");
									taskDetailMap.put("eqpNo_port", "");
									taskDetailMap.put("eqpName_port", "");
									taskDetailMap.put("orderNo", "");
									taskDetailMap.put("orderMark", "");
									taskDetailMap.put("actionType", "");
									taskDetailMap.put("archive_time", "");
									checkOrderDao
											.saveTroubleTaskDetail(taskDetailMap);
									// 保存上报来的检查端子记录
									String detail_id = taskDetailMap.get(
											"DETAIL_ID").toString();
									Map recordMap = new HashMap();
									recordMap.put("recordId", recordId);
									recordMap.put("task_id", task_id);
									recordMap.put("detail_id", detail_id);
									recordMap.put("eqpId", eqpId_port);
									recordMap.put("eqpAddress", eqpAddress);
									recordMap.put("eqpNo", eqpNo_port);
									recordMap.put("staffId", staffId);
									recordMap.put("eqpName", eqpName_port);
									recordMap.put("info", info);
									recordMap.put("longitude", longitude);
									recordMap.put("latitude", latitude);
									recordMap.put("comments", comments);
									recordMap.put("port_id", portId);
									recordMap.put("port_no", portNo);
									recordMap.put("port_name", portName);
									recordMap.put("remark", remarks);
									recordMap.put("info", info);
									recordMap.put("descript", reason);
									resmap.put("descript", reason);
									recordMap.put("isCheckOK", isCheckOK);
									recordMap.put("record_type", "1");// 0:周期任务巡检拍照上传；1：隐患上报拍照上传，2：回单操作；，3：无设备编码上报
									recordMap.put("area_id", parentAreaId);
									recordMap.put("son_area_id", eqpAreaId);
									recordMap.put("isH", isH);
									recordMap.put("type", type);
									checkOrderDao.insertEqpRecord(recordMap);
									// 保存端子照片关系
									Map photoMap = new HashMap();
									photoMap.put("TASK_ID", task_id);
									photoMap.put("DETAIL_ID", detail_id);
									photoMap.put("OBJECT_ID", recordId);
									photoMap.put("REMARKS", "隐患上报");
									photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
									photoMap.put("RECORD_ID", recordId);
									if (!"".equals(portPhotoIds)) {
										String[] photos = portPhotoIds
												.split(",");
										for (String photo : photos) {
											photoMap.put("PHOTO_ID", photo);
											checkOrderDao
													.insertPhotoRel(photoMap);
										}
									}

								}
							}
							// 保存设备信息
							// 先获取下一个设备检查记录的ID
							int recordId = checkOrderDao.getRecordId();
							// 保存工单详细
							Map taskDetailMap = new HashMap();
							taskDetailMap.put("TASK_ID", task_id);
							taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
							if ("2530".equals(sblx) && !"".equals(eqp_no)) {
								taskDetailMap.put("INSPECT_OBJECT_NO", eqp_no);
							} else {
								taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
							}
							taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
							taskDetailMap.put("CHECK_FLAG", "1");
							taskDetailMap.put("GLBM", "");
							taskDetailMap.put("GLMC", "");
							taskDetailMap.put("PORT_ID", "");
							taskDetailMap.put("dtsj_id", "");
							taskDetailMap.put("eqpId_port", "");
							taskDetailMap.put("eqpNo_port", "");
							taskDetailMap.put("eqpName_port", "");
							taskDetailMap.put("orderNo", "");
							taskDetailMap.put("orderMark", "");
							taskDetailMap.put("actionType", "");
							taskDetailMap.put("archive_time", "");
							checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

							// 保存上报来的设备记录，端子信息为空
							String detail_id = taskDetailMap.get("DETAIL_ID")
									.toString();
							Map recordMap = new HashMap();
							recordMap.put("recordId", recordId);
							recordMap.put("task_id", task_id);
							recordMap.put("detail_id", detail_id);
							if ("2530".equals(sblx) && !"".equals(eqp_no)) {
								recordMap.put("eqpId", eqp_id);
								recordMap.put("eqpNo",
										"3".equals(operType) ? eqpAddress
												: eqp_no);
								recordMap.put("eqpName",
										"3".equals(operType) ? eqpAddress
												: eqp_name);
							} else {
								recordMap.put("eqpId", eqpId);
								recordMap.put("eqpNo",
										"3".equals(operType) ? eqpAddress
												: eqpNo);
								recordMap.put("eqpName",
										"3".equals(operType) ? eqpAddress
												: eqpName);
							}
							recordMap.put("eqpAddress", eqpAddress);
							recordMap.put("staffId", staffId);
							recordMap.put("remarks", remarks);
							recordMap.put("info", info);
							recordMap.put("longitude", longitude);
							recordMap.put("latitude", latitude);
							recordMap.put("comments", comments);
							recordMap.put("port_id", "");
							recordMap.put("port_no", "");
							recordMap.put("port_name", "");
							recordMap.put("remark", remarks);
							recordMap.put("info", info);
							recordMap.put("descript", "");
							recordMap.put("isCheckOK", "1".equals(is_bill) ? 1
									: 0);// 需要整改说明现场检查不通过，有问题
							recordMap.put("record_type",
									"3".equals(operType) ? 3 : 1);
							recordMap.put("area_id",
									"1".equals(operType) ? parentAreaId
											: areaId);
							recordMap.put("son_area_id",
									"1".equals(operType) ? eqpAreaId
											: sonAreaId);
							recordMap.put("isH", "");
							recordMap.put("type", "");
							checkOrderDao.insertEqpRecord(recordMap);
							checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
							checkOrderDao.updateLastUpdateTime(recordMap);
							// 插入流程环节
							Map processMap = new HashMap();
							processMap.put("task_id", task_id);
							processMap.put("oper_staff", staffId);
							if ("1".equals(is_bill)) {
								processMap.put("status", 4);
								processMap.put("remark", "检查提交");
								processMap.put("receiver", "");
								processMap.put("content", "生成整改工单");
							} else {
								processMap.put("status", 8);
								processMap.put("remark", "检查提交");
								processMap.put("receiver", "");
								processMap.put("content", "无需整改直接归档");
							}
							checkProcessDao.addProcessNew(processMap);
							// 保存设备的照片关系
							if (!"".equals(eqpPhotoIds)) {
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", task_id);
								photoMap.put("OBJECT_ID", recordId);
								photoMap.put("DETAIL_ID", detail_id);
								photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
								photoMap.put("REMARKS", "隐患上报");
								photoMap.put("RECORD_ID", recordId);
								String[] photos = eqpPhotoIds.split(",");
								for (String photo : photos) {
									photoMap.put("PHOTO_ID", photo);
									checkOrderDao.insertPhotoRel(photoMap);
								}
							}
						}

					}
					String truecount = json.getString("truecount");// 全部端子数
					Map portmap = new HashMap();
					portmap.put("areaId", parentAreaId);
					portmap.put("sonAreaId", eqpAreaId);
					portmap.put("allcount", allcount);
					portmap.put("truecount", truecount);
					// 保证每次检查完设备之后，将检查时间插入到设备表
					if ("2530".equals(sblx) && !"".equals(eqp_no)) {
						portmap.put("eqpId", eqp_id);
						portmap.put("eqpNo", eqp_no);
					} else {
						portmap.put("eqpId", eqpId);
						portmap.put("eqpNo", eqpNo);
					}
					checkOrderDao.updateEquipPortNUM(portmap);

					Map map1 = new HashMap();
					map1.put("areaId", parentAreaId);
					map1.put("sonAreaId", eqpAreaId);
					// 保证每次检查完设备之后，将检查时间插入到设备表
					if ("2530".equals(sblx) && !"".equals(eqp_no)) {
						map1.put("eqpId", eqp_id);
						map1.put("eqpNo", eqp_no);
					} else {
						map1.put("eqpId", eqpId);
						map1.put("eqpNo", eqpNo);
					}
					checkOrderDao.updateCheckCompleteTime(map1);
				}
			}

			/**
			 * TODO FTTH拆机工单
			 */

			if ("7".equals(operType) || "8".equals(operType)) {

				// 保存先工单
				Map troubleTaskMap = new HashMap();
				troubleTaskMap.put("TASK_NO",
						eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_NAME",
						eqpName + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_TYPE", operType);// FTTH
				troubleTaskMap.put("STATUS_ID", "0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
				troubleTaskMap.put("INSPECTOR", staffId);
				troubleTaskMap.put("CREATE_STAFF", staffId);
				troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
				troubleTaskMap.put("AREA_ID", parentAreaId);
				troubleTaskMap.put("ENABLE", "0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
				troubleTaskMap.put("REMARK", remarks);
				troubleTaskMap.put("INFO", info);
				troubleTaskMap.put("NO_EQPNO_FLAG", 0);// 无编码上报
				troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
				troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
				troubleTaskMap.put("SBID", eqpId);// 设备id
				troubleTaskMap.put("COMPANY", "");// 设备id
				troubleTaskMap.put("MAINTOR", "");// 设备id

				checkOrderDao.saveTroubleTask(troubleTaskMap);
				String task_id = troubleTaskMap.get("TASK_ID").toString();
				taskid = task_id;

				// JSONArray addressArray =json.getJSONArray("allEqpAddress");

				for (int i = 0; i < addressArray.size(); i++) {
					int id = checkOrderDao.geteqpAddressId();
					JSONObject eqp = (JSONObject) addressArray.get(i);

					String eqpId_add = null == eqp.get("eqpId") ? "" : eqp
							.getString("eqpId");
					String eqpNo_add = null == eqp.get("eqpNo") ? "" : eqp
							.getString("eqpNo");
					String location_id = eqp.getString("locationId");
					String address_id = eqp.getString("addressId");
					String address_name = eqp.getString("addressName");
					String is_check_ok = eqp.getString("is_check_ok");
					String error_reason = null == eqp.get("error_reason") ? ""
							: eqp.getString("error_reason");

					Map adddressMap = new HashMap();
					adddressMap.put("id", id);
					adddressMap.put("phy_eqp_id", eqpId_add);
					adddressMap.put("phy_eqp_no", eqpNo_add);
					adddressMap.put("install_eqp_id", eqpId);
					adddressMap.put("location_id", location_id);
					adddressMap.put("address_id", address_id);
					adddressMap.put("address_name", address_name);
					adddressMap.put("is_check_ok", is_check_ok);
					adddressMap.put("error_reason", error_reason);
					adddressMap.put("task_id", task_id);
					adddressMap.put("create_staff", staffId);
					adddressMap.put("area_id", parentAreaId);
					adddressMap.put("son_area_id", eqpAreaId);
					checkOrderDao.insertEqpAddress(adddressMap);
				}

				for (int j = 0; j < innerArray.size(); j++) {
					JSONObject port = (JSONObject) innerArray.get(j);
					String eqpId_port = null == port.get("eqpId") ? "" : port
							.getString("eqpId");
					String eqpNo_port = null == port.get("eqpNo") ? "" : port
							.getString("eqpNo");
					String eqpName_port = null == port.get("eqpName") ? ""
							: port.getString("eqpName");
					String portId = port.getString("portId");
					String dtsj_id = port.getString("dtsj_id");

					String portNo = null == port.get("portNo") ? "" : port
							.getString("portNo");
					String portName = null == port.get("portName") ? "" : port
							.getString("portName");
					String portPhotoIds = port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					String glbm = null == port.get("glbm") ? "" : port
							.getString("glbm");
					String glmc = null == port.get("glmc") ? "" : port
							.getString("glmc");
					String isH = null == port.get("isH") ? "" : port
							.getString("isH");// 是否H光路，0不是，1是
					String type = null;
					String eqpTypeId_port = null == port.get("eqp_type_id") ? ""
							: port.getString("eqp_type_id");
					if (glbm.startsWith("F") && "2530".equals(eqpTypeId_port)) {
						type = "1";// E综维 F装维

					} else {
						type = "0";
					}
					// 保存工单详细
					int recordId = checkOrderDao.getRecordId();
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", "1");
					taskDetailMap.put("GLBM", glbm);
					taskDetailMap.put("GLMC", glmc);
					taskDetailMap.put("PORT_ID", portId);
					taskDetailMap.put("dtsj_id", dtsj_id);
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					// 保存上报来的检查端子记录
					String detail_id = taskDetailMap.get("DETAIL_ID")
							.toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", task_id);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId_port);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo_port);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName_port);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", portId);
					recordMap.put("port_no", portNo);
					recordMap.put("port_name", portName);
					recordMap.put("remark", remarks);
					recordMap.put("info", info);
					recordMap.put("descript", reason);
					resmap.put("descript", reason);
					recordMap.put("isCheckOK", isCheckOK);
					recordMap.put("record_type", operType);// FTTH拆机
					recordMap.put("area_id", parentAreaId);
					recordMap.put("son_area_id", eqpAreaId);
					recordMap.put("isH", isH);
					recordMap.put("type", type);
					checkOrderDao.insertEqpRecord(recordMap);
					// 保存端子照片关系
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("OBJECT_TYPE", 4);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID", recordId);
					if (!"".equals(portPhotoIds)) {
						String[] photos = portPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}

				}

				// 保存设备信息
				// 先获取下一个设备检查记录的ID
				int recordId = checkOrderDao.getRecordId();
				// 保存工单详细
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", task_id);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
				taskDetailMap.put("CHECK_FLAG", "");
				taskDetailMap.put("GLBM", "");
				taskDetailMap.put("GLMC", "");
				taskDetailMap.put("PORT_ID", "");
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

				// 保存上报来的设备记录，端子信息为空
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				Map recordMap = new HashMap();
				recordMap.put("recordId", recordId);
				recordMap.put("task_id", task_id);
				recordMap.put("detail_id", detail_id);
				recordMap.put("eqpId", eqpId);
				recordMap.put("eqpAddress", eqpAddress);
				recordMap.put("eqpNo", eqpNo);
				recordMap.put("staffId", staffId);
				recordMap.put("eqpName", eqpName);
				recordMap.put("remarks", remarks);
				recordMap.put("info", info);
				recordMap.put("longitude", longitude);
				recordMap.put("latitude", latitude);
				recordMap.put("comments", comments);
				recordMap.put("port_id", "");
				recordMap.put("port_no", "");
				recordMap.put("port_name", "");
				recordMap.put("remark", remarks);
				recordMap.put("info", info);
				recordMap.put("descript", "");
				recordMap.put("isCheckOK", "1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
				recordMap.put("record_type", operType);
				recordMap.put("area_id", parentAreaId);
				recordMap.put("son_area_id", eqpAreaId);
				recordMap.put("isH", "");
				recordMap.put("type", "");
				checkOrderDao.insertEqpRecord(recordMap);
				checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
				checkOrderDao.updateLastUpdateTime(recordMap);
				// 插入流程环节
				Map processMap = new HashMap();
				processMap.put("task_id", task_id);
				processMap.put("oper_staff", staffId);
				if ("1".equals(is_bill)) {
					processMap.put("status", 4);
					processMap.put("remark", "隐患上报");
				} else {
					processMap.put("status", 8);
					processMap.put("remark", "隐患上报无需整改");
				}
				checkProcessDao.addProcess(processMap);
				// 保存设备的照片关系
				if (!"".equals(eqpPhotoIds)) {
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_TYPE", 4);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("RECORD_ID", recordId);
					String[] photos = eqpPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}

			}

			/**
			 * TODO 2、回单操作(我的检查任务-->整改工单回单)
			 */
			if ("2".equals(operType)) {
				Map map = new HashMap();
				map.put("task_id", taskId);
				String statusIdStr = checkOrderDao.queryTaskByTaskId(map)
						.get("STATUS_ID").toString();
				int curStatusId = Integer.valueOf(statusIdStr);
				if (curStatusId == 7) {
					result.put("result", "001");
					result.put("desc", "已回单");
				} else {
					Map paramMap = new HashMap();
					paramMap.put("rwmxId", rwmxId);
					// 插入流程环节
					paramMap.put("task_id", taskId);
					paramMap.put("remark", "回单，待审核");
					paramMap.put("status", "7");
					paramMap.put("oper_staff", staffId);
					checkProcessDao.addProcess(paramMap);
					// 更新任务表
					Map taskMap = new HashMap();
					taskMap.put("staffId", staffId);
					taskMap.put("statusId", "7");
					taskMap.put("task_id", taskId);
					checkOrderDao.updateTaskBack(taskMap);
					checkOrderDao.updateLastUpdateTime(taskMap);
					/**
					 * 设备检查记录表中记录整改记录 注意record_type
					 */
					paramMap.put("eqpId", eqpId);
					paramMap.put("eqpNo", eqpNo);
					paramMap.put("eqpName", eqpName);
					paramMap.put("longitude", longitude);
					paramMap.put("latitude", latitude);
					paramMap.put("comments", comments);
					paramMap.put("eqpAddress", eqpAddress);
					paramMap.put("remarks", remarks);
					paramMap.put("info", info);
					paramMap.put("staffId", staffId);
					paramMap.put("record_type", "2");
					paramMap.put("area_id", areaId);
					paramMap.put("son_area_id", sonAreaId);
					paramMap.put("task_id", taskId);
					for (int i = 0; i < innerArray.size(); i++) {
						JSONObject port = (JSONObject) innerArray.get(i);
						String eqpId_port = null == port.get("eqpId") ? ""
								: port.getString("eqpId");
						String eqpNo_port = null == port.get("eqpNo") ? ""
								: port.getString("eqpNo");
						String eqpName_port = null == port.get("eqpName") ? ""
								: port.getString("eqpName");
						String portId = port.getString("portId");
						String portNo = port.getString("portNo");
						String portName = port.getString("portName");
						String portPhotoIds = null == port.get("photoId") ? ""
								: port.getString("photoId");
						String reason = port.getString("reason");
						String isCheckOK = port.getString("isCheckOK");
						String glbm = null == port.get("glbm") ? "" : port
								.getString("glbm");
						String glmc = null == port.get("glmc") ? "" : port
								.getString("glmc");
						String type = null == port.get("type") ? "" : port
								.getString("type");
						String isH = null == port.get("isH") ? "" : port
								.getString("isH");// 是否H光路，0不是，1是
						String port_info = null == port.get("port_info") ? ""
								: port.getString("port_info");
						int recordId = checkOrderDao.getRecordId();
						paramMap.put("recordId", recordId);
						paramMap.put("port_id", portId);
						paramMap.put("port_no", portNo);
						paramMap.put("port_name", portName);
						paramMap.put("descript", reason);// 端子情况
						resmap.put("descript", reason);
						paramMap.put("isCheckOK", isCheckOK);
						// 插入详情表
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", taskId);
						taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
						taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
						taskDetailMap.put("CHECK_FLAG", "2");// 回单
						taskDetailMap.put("GLBM", glbm);
						taskDetailMap.put("GLMC", glmc);
						taskDetailMap.put("PORT_ID", portId);
						taskDetailMap.put("dtsj_id", "");
						taskDetailMap.put("eqpId_port", "");
						taskDetailMap.put("eqpNo_port", "");
						taskDetailMap.put("eqpName_port", "");
						taskDetailMap.put("orderNo", "");
						taskDetailMap.put("orderMark", "");
						taskDetailMap.put("actionType", "");
						taskDetailMap.put("archive_time", "");
						checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
						// 插入记录表
						String detail_id = taskDetailMap.get("DETAIL_ID")
								.toString();
						paramMap.put("detail_id", detail_id);
						paramMap.put("eqpId", eqpId_port);
						paramMap.put("eqpNo", eqpNo_port);
						paramMap.put("eqpName", eqpName_port);
						paramMap.put("isH", isH);
						paramMap.put("port_info", port_info);
						paramMap.put("type", type);
						checkOrderDao.insertEqpRecord(paramMap);
						// 保存端子照片关系
						Map photoMap = new HashMap();
						photoMap.put("TASK_ID", taskId);
						photoMap.put("DETAIL_ID", "");
						photoMap.put("OBJECT_ID", recordId);
						photoMap.put("REMARKS", "已回单，待审核");
						photoMap.put("OBJECT_TYPE", 2);// 0，周期性任务，1：隐患上报工单，2,回单操作
						photoMap.put("RECORD_ID", recordId);
						if (!"".equals(portPhotoIds)) {
							String[] photos = portPhotoIds.split(",");
							for (String photo : photos) {
								photoMap.put("PHOTO_ID", photo);
								checkOrderDao.insertPhotoRel(photoMap);
							}
						}
					}
					// 保存设备信息
					int recordId = checkOrderDao.getRecordId();
					// 插入详情表
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", taskId);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
					taskDetailMap.put("CHECK_FLAG", "2");// 回单
					taskDetailMap.put("GLBM", "");
					taskDetailMap.put("GLMC", "");
					taskDetailMap.put("PORT_ID", "");
					taskDetailMap.put("dtsj_id", "");
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					// 插入记录表
					String detail_id = taskDetailMap.get("DETAIL_ID")
							.toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", taskId);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName);
					recordMap.put("remarks", remarks);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", "");
					recordMap.put("port_no", "");
					recordMap.put("port_name", "");
					recordMap.put("remark", remarks);
					recordMap.put("descript", "");
					recordMap.put("isCheckOK", "0");
					recordMap.put("record_type", "2");
					recordMap.put("area_id", areaId);
					recordMap.put("son_area_id", sonAreaId);
					recordMap.put("isH", "");
					recordMap.put("type", "");
					checkOrderDao.insertEqpRecord(recordMap);
					// 保存设备照片关系
					if (!"".equals(eqpPhotoIds)) {
						Map photoMap = new HashMap();
						photoMap.put("TASK_ID", taskId);
						photoMap.put("OBJECT_ID", recordId);
						photoMap.put("DETAIL_ID", "");
						photoMap.put("OBJECT_TYPE", 2);// 0，周期性任务，1：隐患上报工单，2,回单操作
						photoMap.put("REMARKS", "回单");
						photoMap.put("RECORD_ID", recordId);
						String[] photos = eqpPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}
				}
				// 保证每次检查完设备之后，将检查时间插入到设备表
				Map map2 = new HashMap();
				map2.put("areaId", parentAreaId);
				map2.put("sonAreaId", eqpAreaId);
				map2.put("eqpId", eqpId);
				map2.put("eqpNo", eqpNo);
				checkOrderDao.updateCheckCompleteTime(map2);
			}

			/**
			 * TODO 2、回单操作(资源整改工单回单)
			 */
			if ("6".equals(operType)) {

				Map troubleTaskMap = new HashMap();
				troubleTaskMap.put("TASK_NO",
						eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_NAME",
						eqpName + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_TYPE", 6);// 隐患上报
				troubleTaskMap.put("STATUS_ID", "0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
				troubleTaskMap.put("INSPECTOR", staffId);
				troubleTaskMap.put("CREATE_STAFF", staffId);
				troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
				troubleTaskMap.put("AREA_ID", parentAreaId);
				troubleTaskMap.put("ENABLE", "0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
				troubleTaskMap.put("REMARK", remarks);
				troubleTaskMap.put("INFO", info);
				troubleTaskMap.put("NO_EQPNO_FLAG", 0);// 无编码上报
				troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
				troubleTaskMap.put("OLD_TASK_ID", taskId);// 老的task_id
				troubleTaskMap.put("SBID", eqpId);// 设备id
				troubleTaskMap.put("MAINTOR", "");// 装维接单员

				checkOrderDao.saveTroubleTask(troubleTaskMap);
				String task_id = troubleTaskMap.get("TASK_ID").toString();
				taskid = task_id;
				for (int i = 0; i < innerArray.size(); i++) {
					JSONObject port = (JSONObject) innerArray.get(i);
					String eqpId_port = null == port.get("eqpId") ? "" : port
							.getString("eqpId");
					String eqpNo_port = null == port.get("eqpNo") ? "" : port
							.getString("eqpNo");
					String eqpName_port = null == port.get("eqpName") ? ""
							: port.getString("eqpName");
					String portId = port.getString("portId");
					String portNo = port.getString("portNo");
					String portName = port.getString("portName");
					String portPhotoIds = null == port.get("photoId") ? ""
							: port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					String glbm = null == port.get("glbm") ? "" : port
							.getString("glbm");
					String glmc = null == port.get("glmc") ? "" : port
							.getString("glmc");
					String type = null == port.get("type") ? "" : port
							.getString("type");
					String isH = null == port.get("isH") ? "" : port
							.getString("isH");// 是否H光路，0不是，1是
					String port_info = null == port.get("port_info") ? ""
							: port.getString("port_info");

					// 保存工单详细
					int recordId = checkOrderDao.getRecordId();
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", "3");
					taskDetailMap.put("GLBM", glbm);
					taskDetailMap.put("GLMC", glmc);
					taskDetailMap.put("PORT_ID", portId);
					taskDetailMap.put("dtsj_id", "");
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					// 保存上报来的检查端子记录
					String detail_id = taskDetailMap.get("DETAIL_ID")
							.toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", task_id);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId_port);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo_port);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName_port);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", portId);
					recordMap.put("port_no", portNo);
					recordMap.put("port_name", portName);
					recordMap.put("remark", remarks);
					recordMap.put("info", info);
					recordMap.put("descript", port_info);
					resmap.put("descript", port_info);
					recordMap.put("isCheckOK", isCheckOK);
					recordMap.put("record_type", "6");// 资源整改
					recordMap.put("area_id", parentAreaId);
					recordMap.put("son_area_id", eqpAreaId);
					recordMap.put("isH", isH);
					recordMap.put("type", type);
					checkOrderDao.insertEqpRecord(recordMap);
					// 保存端子照片关系
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID", recordId);
					if (!"".equals(portPhotoIds)) {
						String[] photos = portPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}

				}
				// 保存设备信息
				// 先获取下一个设备检查记录的ID
				int recordId = checkOrderDao.getRecordId();
				// 保存工单详细
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", task_id);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
				taskDetailMap.put("CHECK_FLAG", "3");// 资源整改
				taskDetailMap.put("GLBM", "");
				taskDetailMap.put("GLMC", "");
				taskDetailMap.put("PORT_ID", "");
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

				// 保存上报来的设备记录，端子信息为空
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				Map recordMap = new HashMap();
				recordMap.put("recordId", recordId);
				recordMap.put("task_id", task_id);
				recordMap.put("detail_id", detail_id);
				recordMap.put("eqpId", eqpId);
				recordMap.put("eqpAddress", eqpAddress);
				recordMap.put("eqpNo", eqpNo);
				recordMap.put("staffId", staffId);
				recordMap.put("eqpName", eqpName);
				recordMap.put("remarks", remarks);
				recordMap.put("info", info);
				recordMap.put("longitude", longitude);
				recordMap.put("latitude", latitude);
				recordMap.put("comments", comments);
				recordMap.put("port_id", "");
				recordMap.put("port_no", "");
				recordMap.put("port_name", "");
				recordMap.put("remark", remarks);
				recordMap.put("info", info);
				recordMap.put("descript", "");
				recordMap.put("isCheckOK", "1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
				recordMap.put("record_type", "6");
				recordMap.put("area_id", parentAreaId);
				recordMap.put("son_area_id", eqpAreaId);
				recordMap.put("isH", "");
				recordMap.put("type", "");
				checkOrderDao.insertEqpRecord(recordMap);
				checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
				checkOrderDao.updateLastUpdateTime(recordMap);
				// 插入流程环节
				Map processMap = new HashMap();
				processMap.put("task_id", task_id);
				processMap.put("oper_staff", staffId);
				if ("1".equals(is_bill)) {
					processMap.put("status", 4);
					processMap.put("remark", "隐患上报");
				} else {
					processMap.put("status", 8);
					processMap.put("remark", "隐患上报无需整改");
				}
				checkProcessDao.addProcess(processMap);
				// 保存设备的照片关系
				if (!"".equals(eqpPhotoIds)) {
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_TYPE", 6);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("RECORD_ID", recordId);
					String[] photos = eqpPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}

				// 保证每次检查完设备之后，将检查时间插入到设备表
				Map map = new HashMap();
				map.put("areaId", areaId);
				map.put("sonAreaId", sonAreaId);
				map.put("eqpId", eqpId);
				map.put("eqpNo", eqpNo);
				checkOrderDao.updateCheckCompleteTime(map);

			}

			/**
			 * TODO 客响订单
			 */
			if ("9".equals(operType)) {

				// 保存先工单
				Map troubleTaskMap = new HashMap();
				troubleTaskMap.put("TASK_NO",
						eqpNo + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_NAME",
						eqpName + "_" + DateUtil.getDate("yyyyMMdd"));
				troubleTaskMap.put("TASK_TYPE", operType);// FTTH
				troubleTaskMap.put("STATUS_ID", "0".equals(is_bill) ? 8 : 4);// 需要整改为隐患上报，无需整改直接归档
				troubleTaskMap.put("INSPECTOR", staffId);
				troubleTaskMap.put("CREATE_STAFF", staffId);
				troubleTaskMap.put("SON_AREA_ID", eqpAreaId);
				troubleTaskMap.put("AREA_ID", parentAreaId);
				troubleTaskMap.put("ENABLE", "0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用
																			// 1不可用（不显示在待办列表）
				troubleTaskMap.put("REMARK", remarks);
				troubleTaskMap.put("INFO", info);
				troubleTaskMap.put("NO_EQPNO_FLAG", 0);// 无编码上报
				troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
				troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
				troubleTaskMap.put("SBID", eqpId);// 设备id
				troubleTaskMap.put("COMPANY", "");// 设备id
				troubleTaskMap.put("MAINTOR", "");// 设备id

				checkOrderDao.saveTroubleTask(troubleTaskMap);
				String task_id = troubleTaskMap.get("TASK_ID").toString();
				taskid = task_id;

				// JSONArray addressArray =json.getJSONArray("allEqpAddress");

				for (int i = 0; i < addressArray.size(); i++) {
					int id = checkOrderDao.geteqpAddressId();
					JSONObject eqp = (JSONObject) addressArray.get(i);

					String eqpId_add = null == eqp.get("eqpId") ? "" : eqp
							.getString("eqpId");
					String eqpNo_add = null == eqp.get("eqpNo") ? "" : eqp
							.getString("eqpNo");
					String location_id = eqp.getString("locationId");
					String address_id = eqp.getString("addressId");
					String address_name = eqp.getString("addressName");
					String is_check_ok = eqp.getString("is_check_ok");
					String error_reason = null == eqp.get("error_reason") ? ""
							: eqp.getString("error_reason");

					Map adddressMap = new HashMap();
					adddressMap.put("id", id);
					adddressMap.put("phy_eqp_id", eqpId_add);
					adddressMap.put("phy_eqp_no", eqpNo_add);
					adddressMap.put("install_eqp_id", eqpId);
					adddressMap.put("location_id", location_id);
					adddressMap.put("address_id", address_id);
					adddressMap.put("address_name", address_name);
					adddressMap.put("is_check_ok", is_check_ok);
					adddressMap.put("error_reason", error_reason);
					adddressMap.put("task_id", task_id);
					adddressMap.put("create_staff", staffId);
					adddressMap.put("area_id", parentAreaId);
					adddressMap.put("son_area_id", eqpAreaId);
					checkOrderDao.insertEqpAddress(adddressMap);
				}

				for (int j = 0; j < innerArray.size(); j++) {
					JSONObject port = (JSONObject) innerArray.get(j);
					String eqpId_port = null == port.get("eqpId") ? "" : port
							.getString("eqpId");
					String eqpNo_port = null == port.get("eqpNo") ? "" : port
							.getString("eqpNo");
					String eqpName_port = null == port.get("eqpName") ? ""
							: port.getString("eqpName");
					String portId = port.getString("portId");
					String dtsj_id = port.getString("dtsj_id");

					String portNo = null == port.get("portNo") ? "" : port
							.getString("portNo");
					String portName = null == port.get("portName") ? "" : port
							.getString("portName");
					String portPhotoIds = port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					String glbm = null == port.get("glbm") ? "" : port
							.getString("glbm");
					String glmc = null == port.get("glmc") ? "" : port
							.getString("glmc");
					String isH = null == port.get("isH") ? "" : port
							.getString("isH");// 是否H光路，0不是，1是
					String type = null;
					String eqpTypeId_port = null == port.get("eqp_type_id") ? ""
							: port.getString("eqp_type_id");
					if (glbm.startsWith("F") && "2530".equals(eqpTypeId_port)) {
						type = "1";// E综维 F装维

					} else {
						type = "0";
					}
					// 保存工单详细
					int recordId = checkOrderDao.getRecordId();
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", "1");
					taskDetailMap.put("GLBM", glbm);
					taskDetailMap.put("GLMC", glmc);
					taskDetailMap.put("PORT_ID", portId);
					taskDetailMap.put("dtsj_id", dtsj_id);
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					// 保存上报来的检查端子记录
					String detail_id = taskDetailMap.get("DETAIL_ID")
							.toString();
					Map recordMap = new HashMap();
					recordMap.put("recordId", recordId);
					recordMap.put("task_id", task_id);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId_port);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("eqpNo", eqpNo_port);
					recordMap.put("staffId", staffId);
					recordMap.put("eqpName", eqpName_port);
					recordMap.put("info", info);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("comments", comments);
					recordMap.put("port_id", portId);
					recordMap.put("port_no", portNo);
					recordMap.put("port_name", portName);
					recordMap.put("remark", remarks);
					recordMap.put("info", info);
					recordMap.put("descript", reason);
					resmap.put("descript", reason);
					recordMap.put("isCheckOK", isCheckOK);
					recordMap.put("record_type", operType);// FTTH拆机
					recordMap.put("area_id", parentAreaId);
					recordMap.put("son_area_id", eqpAreaId);
					recordMap.put("isH", isH);
					recordMap.put("type", type);
					checkOrderDao.insertEqpRecord(recordMap);
					// 保存端子照片关系
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("OBJECT_TYPE", 4);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID", recordId);
					if (!"".equals(portPhotoIds)) {
						String[] photos = portPhotoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}

				}

				// 保存设备信息
				// 先获取下一个设备检查记录的ID
				int recordId = checkOrderDao.getRecordId();
				// 保存工单详细
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", task_id);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
				taskDetailMap.put("CHECK_FLAG", "");
				taskDetailMap.put("GLBM", "");
				taskDetailMap.put("GLMC", "");
				taskDetailMap.put("PORT_ID", "");
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);

				// 保存上报来的设备记录，端子信息为空
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				Map recordMap = new HashMap();
				recordMap.put("recordId", recordId);
				recordMap.put("task_id", task_id);
				recordMap.put("detail_id", detail_id);
				recordMap.put("eqpId", eqpId);
				recordMap.put("eqpAddress", eqpAddress);
				recordMap.put("eqpNo", eqpNo);
				recordMap.put("staffId", staffId);
				recordMap.put("eqpName", eqpName);
				recordMap.put("remarks", remarks);
				recordMap.put("info", info);
				recordMap.put("longitude", longitude);
				recordMap.put("latitude", latitude);
				recordMap.put("comments", comments);
				recordMap.put("port_id", "");
				recordMap.put("port_no", "");
				recordMap.put("port_name", "");
				recordMap.put("remark", remarks);
				recordMap.put("info", info);
				recordMap.put("descript", "");
				recordMap.put("isCheckOK", "1".equals(is_bill) ? 1 : 0);// 需要整改说明现场检查不通过，有问题
				recordMap.put("record_type", operType);
				recordMap.put("area_id", parentAreaId);
				recordMap.put("son_area_id", eqpAreaId);
				recordMap.put("isH", "");
				recordMap.put("type", "");
				checkOrderDao.insertEqpRecord(recordMap);
				checkOrderDao.updateTaskTime(recordMap);// 实际检查完成时间
				checkOrderDao.updateLastUpdateTime(recordMap);
				// 插入流程环节
				Map processMap = new HashMap();
				processMap.put("task_id", task_id);
				processMap.put("oper_staff", staffId);
				if ("1".equals(is_bill)) {
					processMap.put("status", 4);
					processMap.put("remark", "隐患上报");
				} else {
					processMap.put("status", 8);
					processMap.put("remark", "隐患上报无需整改");
				}
				checkProcessDao.addProcess(processMap);
				// 保存设备的照片关系
				if (!"".equals(eqpPhotoIds)) {
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", task_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_TYPE", 4);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("REMARKS", "隐患上报");
					photoMap.put("RECORD_ID", recordId);
					String[] photos = eqpPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}

			}

			/*
			 * if(is_bill.equals("1") && flag){
			 * xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
			 * "<IfInfo><sysRoute>"+sysRoute+"</sysRoute>"+
			 * "<taskType>"+taskTypes+"</taskType>"+
			 * "<gwMan>"+staffName+"</gwMan>"+ "<taskId>"+taskid+"</taskId>"+
			 * "<gwManAccount>"+id_numberList+"</gwManAccount>"+
			 * "<equCbAccount>"+contract_persion_nos+"</equCbAccount>"+
			 * "<gwPositionPersons>"+PositionPersonsList+"</gwPositionPersons>"+
			 * "<equCode>"+eqpNo+"</equCode>"+ "<equName>"+eqpName+"</equName>"+
			 * "<equType>"+equType+"</equType>"+
			 * "<errorPortList>"+portIdList+"</errorPortList>"+
			 * "<gwContent>"+resmap.get("descript")+"</gwContent>"+ "</IfInfo>";
			 * 
			 * WfworkitemflowLocator locator =new WfworkitemflowLocator(); try {
			 * WfworkitemflowSoap11BindingStub
			 * stub=(WfworkitemflowSoap11BindingStub
			 * )locator.getWfworkitemflowHttpSoap11Endpoint();
			 * results=stub.outSysDispatchTask(xml); //
			 * resultss=stub.outSysDispatchTask(xmls); Document doc = null; doc
			 * = DocumentHelper.parseText(results); // 将字符串转为XML Element rootElt
			 * = doc.getRootElement(); // 获取根节点 Element IfResult =
			 * rootElt.element("IfResult"); String IfResultinfo=
			 * IfResult.getText(); //0是成功 1是失败 Map IfResultMap = new HashMap();
			 * IfResultMap.put("Result_Status", IfResultinfo);
			 * IfResultMap.put("task_id", taskid);
			 * checkOrderDao.updateResultTask(IfResultMap);
			 * System.out.println(xml); System.out.println(results);
			 * result.put("msg1", results); // System.out.println(result);
			 * //outSysDispatchTask } catch (Exception e) { // TODO
			 * Auto-generated catch block e.printStackTrace();
			 * result.put("result", "001"); result.put("desc", "线路工单推送接口调用失败。");
			 * }
			 * 
			 * 
			 * }else if(is_bill.equals("0") && flag){
			 * xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
			 * "<IfInfo><sysRoute>"+sysRoute+"</sysRoute>"+
			 * "<taskType>"+taskType+"</taskType>"+
			 * "<gwMan>"+staffName+"</gwMan>"+ "<taskId>"+taskid+"</taskId>"+
			 * "<gwManAccount>"+staffNo+"</gwManAccount>"+
			 * "<equCode>"+eqpNo+"</equCode>"+ "<equName>"+eqpName+"</equName>"+
			 * "<chekPortNum>"+allcount+"</chekPortNum>"+
			 * "<adressCheckCnt>"+addressCheckCnt+"</adressCheckCnt>"+
			 * "<gwContent>"+resmap.get("descript")+"</gwContent>"+ "</IfInfo>";
			 * 
			 * WfworkitemflowLocator locator =new WfworkitemflowLocator(); try {
			 * WfworkitemflowSoap11BindingStub
			 * stub=(WfworkitemflowSoap11BindingStub
			 * )locator.getWfworkitemflowHttpSoap11Endpoint();
			 * results=stub.outSysDispatchTask(xml); //
			 * resultss=stub.outSysDispatchTask(xmls); Document doc = null; doc
			 * = DocumentHelper.parseText(results); // 将字符串转为XML Element rootElt
			 * = doc.getRootElement(); // 获取根节点 Element IfResult =
			 * rootElt.element("IfResult"); String IfResultinfo=
			 * IfResult.getText(); //0是成功 1是失败 Map IfResultMap = new HashMap();
			 * IfResultMap.put("Result_Status", IfResultinfo);
			 * IfResultMap.put("task_id", taskid);
			 * checkOrderDao.updateResultTask(IfResultMap);
			 * System.out.println(xml); System.out.println(results);
			 * result.put("msg1", results); // System.out.println(result);
			 * //outSysDispatchTask } catch (Exception e) { // TODO
			 * Auto-generated catch block e.printStackTrace();
			 * result.put("result", "001"); result.put("desc", "线路工单推送接口调用失败。");
			 * } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "生成工单失败，请联系管理员。");
		}
		return result.toString();
	};
	
	@Override
	public String getUserOreder(String jsonStr){
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		JSONObject json = null;
		if(org.apache.commons.lang.StringUtils.isNotBlank(jsonStr)){
			json = JSONObject.fromObject(jsonStr);
			String staffId = json.getString("staffId");
//			staffId = org.apache.commons.lang.StringUtils.isNotBlank(staffId)?staffId:"nj0201010093";
			if(org.apache.commons.lang.StringUtils.isNotBlank(staffId)){
				List<Map> listM =  new ArrayList<Map>();
				try {
					listM = checkOrderDao.getUserOreder(staffId);
					
					List<String> listMarkOne =  new ArrayList<String>();
					List<String> listMarkTwo =  new ArrayList<String>();
					//根据staffId获取到List<Order>,for循环判断mark
					for(int i=0;i<listM.size();i++){
						Map order = listM.get(i);
						String orderId = order.get("ORDER_ID").toString();
						String mark = order.get("MARK").toString();
						if("1".equals(mark)){
							listMarkOne.add(orderId);
						}else if("2".equals(mark)){
							listMarkTwo.add(orderId);
						}else{
							logger.info(String.format("orderId为 %s 的 工单 的mark字段有问题",orderId));
						}
					}
					List<Map> listResultOne =  new ArrayList<Map>();
					List<Map> listResultTwo =  new ArrayList<Map>();
					Map map_param = new HashMap();
					if(listMarkOne.size()>0){
						map_param.put("list", listMarkOne);
						map_param.put("mark", "1");
						listResultOne = checkOrderDao.doGetOreder(map_param);
					}
					if(listMarkTwo.size()>0){
						map_param.put("list", listMarkTwo);
						map_param.put("mark", "2");
						listResultTwo = checkOrderDao.doGetOreder(map_param);;
					}
					listResultOne.addAll(listResultTwo);
					
					//清空赋值
					listM =  new ArrayList<Map>();
					listM = listResultOne;
				} catch (Exception e) {
					logger.info(String.format("执行getUserOreder方法报错，参数是 %s",staffId));
					result.put("result", "001");
					result.put("desc", "获取个人工单失败，请联系管理员。");
				}
				result.put("list", listM);
			}else{
				logger.info("参数为空");
				result.put("result", "004");
				result.put("desc", "查询的staffId为空");
			}
		}else{
			logger.info("参数为空");
			result.put("result", "003");
			result.put("desc", "查询的参数为空");
		}
		return result.toString();
	}
	
	/**
	 * 整改工单回单接口--流程改造
	 */
	@Override
	public String receiptReformTask(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		String compare_info="";
		String compare_right="";
		String compare_wrong="";
		String wrong_port_record="";
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");// 人员账号id
			String eqpId = json.getString("eqpId");// 设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");// 设备名称
			String eqpAddress = json.getString("eqpAddress");// 设备地址
			String info = json.getString("info");// 回单备注	
			String taskId = json.getString("taskId");// 任务ID	
			/*String order_id = json.getString("order_id");//工单ID
			String order_no = json.getString("order_no");//工单编号*/	
			String areaId=json.getString("areaId");//地市
			String eqpPhotoIds = json.getString("photoId");// 设备照片
			String sonAreaId="";//通过设备编码和设备ID查询设备所在区域
			/**
			 * 根据设备获取区域信息
			 */
			Map eqpId_eqpNo_map=new HashMap();
			eqpId_eqpNo_map.put("eqpId", eqpId);
			eqpId_eqpNo_map.put("eqpNo", eqpNo);
			Map eqpareaMap = checkOrderDao.queryAreaByeqpId(eqpId_eqpNo_map);
		    sonAreaId = null ==  eqpareaMap.get("AREA_ID")? "" : eqpareaMap.get("AREA_ID").toString();
			/**
			 * 端子信息
			 */
			JSONArray innerArray =json.getJSONArray("port");

			Map param = new HashMap();
			
			int rightPortNum=0;//该参数用来统计端子的已整改数量（即整改后合格的端子数量），如果该参数不等于innerArray.size()，则任务状态为退单、待派单，否则已整改回单待审核	
			int compare_right_num=0;//该参数用来统计端子自动校验成功的结果，如果端子自动校验成功的个数等于innerArray.size()，则表明该任务可以直接归档
			
			Map<String,String> processMap=new HashMap<String,String>();
			Map<String,String> changePortMap=new HashMap<String,String>();
			
			for(int i=0;i<innerArray.size();i++){
				JSONObject port = (JSONObject)innerArray.get(i);
				String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
				String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
				String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
				String eqpAddress_port = json.getString("eqpAddress");// 设备地址
				String portId = port.getString("portId");
				String portNo = port.getString("portNo");
				String portName = port.getString("portName");
				String portPhotoIds = null==port.get("photoId")?"":port.getString("photoId");
				String reason = port.getString("reason");//0 ：已整改 "" 1：无法整改 无法整改原因
				String reason2 = port.getString("reason2");//检查人员检查的错误原因
				String isCheckOK = port.getString("isCheckOK");//是否整改 0 ：已整改  1：无法整改
				
				/*String truePortId = null == port.get("truePortId") ? "": port.getString("truePortId");//正确端子id
				String truePortNo = null == port.get("truePortNo") ? "": port.getString("truePortNo");//正确端子编码
*/				
				String rightEqpId = null == port.get("rightEqpId") ? "": port.getString("rightEqpId");//正确设备ID
				
				String rightEqpNo = null == port.get("rightEqpNo") ? "": port.getString("rightEqpNo");//正确设备编码
				String changedPortId=port.getString("portId_new");//修改后的端子id
				String changedPortNo=port.getString("localPortNo");//修改后的端子编码
				String changedEqpId=port.getString("sbid_new");//修改后的设备ID
				String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
				
				String CorrectPortNo = null== port.get("CorrectPortNo")?"":port.getString("CorrectPortNo");//正确端子编码
				String CorrectPortId = null== port.get("CorrectPortId")?"":port.getString("CorrectPortId");//正确端子id
				if("查询".equals(CorrectPortNo)){
					CorrectPortNo="";
					CorrectPortId="";
				}
				String glbm = null==port.get("glbm")?"":port.getString("glbm");
				String glmc = null==port.get("glmc")?"":port.getString("glmc");
				String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
//				String order_id = null==port.get("order_id")?"":port.getString("order_id");//工单id
//				String order_no = null==port.get("order_no")?"":port.getString("order_no");//工单编号
			
				int recordId = checkOrderDao.getRecordId();
				param.put("recordId", recordId);
				param.put("port_id", portId);
				param.put("port_no", portNo);
				param.put("port_name", portName);
				param.put("reason", reason);//检查端子错误描述
				param.put("reason2", reason2);//整改端子错误描述
				param.put("isCheckOK", isCheckOK);
				param.put("CorrectPortId", CorrectPortId);
				param.put("CorrectPortNo", CorrectPortNo);
				param.put("glbm", glbm);
				//插入详情表
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", taskId);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
				taskDetailMap.put("CHECK_FLAG", "2");//回单
				taskDetailMap.put("GLBM", glbm);
				taskDetailMap.put("GLMC", glmc);
				taskDetailMap.put("PORT_ID", portId);
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
			//	插入记录表
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				param.put("detail_id", detail_id);
				param.put("eqpId", eqpId_port);
				param.put("eqpNo", eqpNo_port);
				param.put("eqpName", eqpName_port);
				param.put("eqpAddress", eqpAddress_port);
				param.put("isH", isH);
				param.put("info", info);//回单备注
				param.put("staffId", staffId);//创建人
				param.put("taskId", taskId);
				param.put("area_id", areaId);
				param.put("son_area_id", sonAreaId);
				param.put("record_type", "2");
				param.put("TASK_NO","");
				param.put("TASK_NAME","");
				param.put("glbm",glbm);
				param.put("truePortId",CorrectPortId);
				param.put("truePortNo",CorrectPortNo);
				param.put("rightEqpId",rightEqpId);
				param.put("rightEqpNo",rightEqpNo);//正确
				param.put("changedPortId",changedPortId);
				param.put("changedPortNo",changedPortNo);
				param.put("changedEqpId",changedEqpId);
				param.put("changedEqpNo",changedEqpNo);
				//checkOrderDao.insertEqpRecord_new(param);
				checkOrderDao.insertEqpRecordZg(param);
				//如果changedPortNo不为空，表示整改人员已经对现场进行整改维护
				//将修改后的端子插入流程表 tb_cablecheck_process
				//if("0".equals(isCheckOK)){
					if(!"".equals(changedPortNo)){
						String content="";
						if(eqpId_port.equals(changedEqpId)){
							content=glbm+"从"+eqpNo_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
						}else{
							content =glbm+"从"+eqpNo_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
						}
						changePortMap.put("oper_staff", staffId);
						changePortMap.put("task_id", taskId);
						changePortMap.put("status", "66");//一键改
						changePortMap.put("remark", "一键改");
						changePortMap.put("content", content);
						changePortMap.put("receiver", "");
						checkProcessDao.addProcessNew(changePortMap);
					}
				//}
				//保存端子照片关系
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskId);
				photoMap.put("DETAIL_ID", "");
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("REMARKS", "已回单，待审核");
				photoMap.put("OBJECT_TYPE", 2);//0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID",recordId);
				if (!"".equals(portPhotoIds)) {
					String[] photos = portPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}
				//由于目前的情况是 1.检查员可以对错误端子进行整改，此时正确端子端子编码和正确端子ID已插入记录表
				//2.维护员也可以对生成的错误工单进行整改，此时先将正确端子和编码插入记录表，然后再从记录表中取出
				
				if("0".equals(isCheckOK)){
					rightPortNum++;
					
					//自动校验端子是否整改正确  如果所有端子均自动校验成功，那么该任务会自动归档，需要判断所有端子均自动校验成功的标志
					String compare_result= compareWorkOrder_new(param);//"000":自动校验成功   "001":自动校验失败
					if("000".equals(compare_result)){
						if(compare_right.length()>0){//记录自动校验通过的
							compare_right=compare_right+","+portNo;
						}else{
							compare_right=portNo;
						}
						compare_right_num++;
						//自动校验通过的端子记录流程表
						processMap.put("oper_staff", staffId);
						processMap.put("task_id", taskId);
						processMap.put("status",  "77"); //自动校验
						processMap.put("remark", "自动校验");
						processMap.put("receiver", "");
						processMap.put("content", eqpNo_port+"上的"+portNo+"端子编码自动校验已通过");
						checkProcessDao.addProcessNew(processMap);
					}else{
						if(compare_wrong.length()>0){//记录自动校验未通过的
							compare_wrong=compare_wrong+","+portNo;
						}else{
							compare_wrong=portNo;
						}
						//自动校验未通过的端子记录流程表
						processMap.put("oper_staff", staffId);
						processMap.put("task_id", taskId);
						processMap.put("status",  "77"); //自动校验
						processMap.put("remark", "自动校验");
						processMap.put("receiver", "");
						processMap.put("content", eqpNo_port+"上的"+portNo+"端子编码自动校验未通过");
						checkProcessDao.addProcessNew(processMap);
					}
				}else{
					if(wrong_port_record.length()>0){//记录整改不合格的端子
						wrong_port_record=wrong_port_record+","+portNo;
					}else{
						wrong_port_record=portNo;
					}
				}
				
			}
			
			Map paramMap = new HashMap();
		    //插入流程环节
		    paramMap.put("task_id", taskId);
		    //通过taskid获取审核员
		    String auditor=checkOrderDao.getAuditorByTaskId(paramMap);
		    if(innerArray.size()==rightPortNum){
		    	if(innerArray.size()==compare_right_num){
		    		 paramMap.put("remark", "整改回单");
					 paramMap.put("status", "8");
					 paramMap.put("receiver", "");
					 paramMap.put("content","端子自动校验成功，直接归档" );
		    	}else{
			    	 paramMap.put("remark", "整改回单");
					 paramMap.put("status", "7");
					 paramMap.put("receiver", auditor);
					 paramMap.put("content","端子自动校验未成功，由审核员审核" );
		    	}
		    }else{
		    	 paramMap.put("remark", "整改回单");
				 paramMap.put("status", "5");
				 paramMap.put("receiver", auditor);
				 paramMap.put("content","自动退给审核员，由审核员派单" );
		    }
		    paramMap.put("oper_staff", staffId);
			checkProcessDao.addProcessNew(paramMap);
			//更新任务表   只要有一个端子是无法整改的，如果选择确认，任务为待派单，如果是转派，任务为待回单
			Map taskMap = new HashMap();
			taskMap.put("staffId", staffId);
			if(innerArray.size()==rightPortNum){
				if(innerArray.size()==compare_right_num){
					taskMap.put("statusId", "8");
					taskMap.put("maintor", staffId);
					compare_info="端子自动校验通过，任务已归档";
				}else{
					taskMap.put("statusId", "7");
					taskMap.put("maintor", staffId);
					String flag="整改回单";
					compare_info=getResult(compare_right,compare_wrong,wrong_port_record,flag);
				}
			}else{
				taskMap.put("statusId", "5");
				taskMap.put("maintor", "");
			}
			taskMap.put("task_id", taskId);
			checkOrderDao.updateTaskMaintor_status(taskMap);

			//保存设备信息
			int recordId = checkOrderDao.getRecordId();
			//插入详情表
			Map taskDetailMap = new HashMap();
			taskDetailMap.put("TASK_ID", taskId);
			taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
			taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
			taskDetailMap.put("CHECK_FLAG", "2");//回单
			taskDetailMap.put("GLBM", "");
			taskDetailMap.put("GLMC", "");
			taskDetailMap.put("PORT_ID", "");
			taskDetailMap.put("dtsj_id", "");
			taskDetailMap.put("eqpId_port", "");
			taskDetailMap.put("eqpNo_port", "");
			taskDetailMap.put("eqpName_port", "");
			taskDetailMap.put("orderNo", "");
			taskDetailMap.put("orderMark", "");
			taskDetailMap.put("actionType", "");
			taskDetailMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
			//插入记录表
			String detail_id = taskDetailMap.get("DETAIL_ID").toString();
			Map recordMap = new HashMap();
			recordMap.put("recordId", recordId);
			recordMap.put("taskId", taskId);
			recordMap.put("detail_id", detail_id);
			recordMap.put("eqpId", eqpId);
			recordMap.put("eqpAddress", eqpAddress);
			recordMap.put("eqpNo", eqpNo);
			recordMap.put("staffId", staffId);
			recordMap.put("eqpName", eqpName);
			recordMap.put("info", info);
			recordMap.put("port_id", "");
			recordMap.put("port_no", "");
			recordMap.put("port_name", "");
			recordMap.put("reason", "");
			recordMap.put("CorrectPortId", "");
			recordMap.put("CorrectPortNo", "");
			if(innerArray.size()==rightPortNum){
				recordMap.put("isCheckOK", "0");
			}else{
				recordMap.put("isCheckOK", "1");
			}
			recordMap.put("record_type", "2");
			recordMap.put("area_id", areaId);
			recordMap.put("son_area_id", sonAreaId);
			recordMap.put("isH", "");
			checkOrderDao.insertEqpRecord_new(recordMap);
			//保存设备照片关系
			if (!"".equals(eqpPhotoIds)) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskId);
				photoMap.put("OBJECT_ID",recordId);
				photoMap.put("DETAIL_ID", "");
				photoMap.put("OBJECT_TYPE", 2);//0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("REMARKS", "回单");
				photoMap.put("RECORD_ID", recordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "整改工单回单失败!!!");
		}
		result.put("desc", compare_info);
		return result.toString();
	}
	public String getResult(String compare_right,String compare_wrong,String wrong_port_record,String flag){
		int a =compare_right.length();//要么大于0，要么等于0
		int b =compare_wrong.length();//要么大于0，要么等于0
		int c =wrong_port_record.length();//要么大于0，要么等于0
		String compare_info="";
		if(a==0&&b==0&&c==0){
			compare_info="请将所有端子进行整改！";
		}
		if(a==0&&b==0&&c>0){
			compare_info="请退给审核员或转派给维护员！";
		}
		if(a==0&&b>0&&c==0){
			compare_info=compare_wrong+"自动校验未通过";
		}
		if(a>0&&b==0&&c==0){
			compare_info=compare_right+"自动校验通过！";
		}
		if(a>0&&b>0&&c==0){
			compare_info=compare_right+"自动校验通过"+","+compare_wrong+"自动校验未通过！";
		}
		
		//整改回单    转派
		if("整改回单".equals(flag)){
			if(a==0&&b>0&&c>0){
				compare_info=compare_wrong+"自动校验未通过"+","+wrong_port_record+"整改不合格！";
			}
			if(a>0&&b==0&&c>0){
				compare_info=compare_right+"自动校验通过"+","+wrong_port_record+"整改不合格！";
			}
			if(a>0&&b>0&&c>0){
				compare_info=compare_right+"自动校验通过"+","+compare_wrong+"自动校验未通过"+","+wrong_port_record+"整改不合格！";
			}
		}else{
			if(a==0&&b>0&&c>0){
				compare_info=compare_wrong+"自动校验未通过"+","+wrong_port_record+"转派成功！";
			}
			if(a>0&&b==0&&c>0){
				compare_info=compare_right+"自动校验通过"+","+wrong_port_record+"转派成功！";
			}
			if(a>0&&b>0&&c>0){
				compare_info=compare_right+"自动校验通过"+","+compare_wrong+"自动校验未通过"+","+wrong_port_record+"转派成功！";
			}
		}
		return compare_info;
	}
	//自动校验端子是否整改正确
	public String compareWorkOrder(Map paramMap){
		try{
			String eqpId=paramMap.get("eqpId").toString();//设备ID指的是端子所属的直属设备
			String portidrightposition=paramMap.get("CorrectPortId").toString();;
			String glbh=paramMap.get("glbm").toString();;//光路编号
			String area_id=paramMap.get("area_id").toString();;//地市
			
			Map param= new HashMap();
			param.put("eqpId", eqpId);		
			param.put("glbh", glbh);

			
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			param.put("jndi", jndi);
			
			Map<String,String> ossMap=new HashMap<String, String>();
			String ossPort="";
			if(portidrightposition !=null && !"".equals(portidrightposition)){
				//2.首先通过设备id和光路编号从OSS中取出光路编号对应的端子id(ossPort)
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				ossMap= checkTaskDao.getOssPort(param);//通过设备ID和光路编号到OSS实时查询光路占用端口		
				SwitchDataSourceUtil.clearDataSource();
				ossPort=ossMap.get("PORT_ID");
				//3.通过portRightPosition与ossPort进行对比，判断端子id是否一致，如果一致，则自动校验通过，无需审核员审核，自动归档，
				//不一致，回单到审核员处审核
				if(portidrightposition.equals(ossPort)){
					return "000";//自动校验成功
				}else{
					return "001";//自动校验失败
				}
			}else{
				return "001";//自动校验失败
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "001";//自动校验失败
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
	}
	
	//工单生成错误记录自动派发给维护员维护后，维护员维护完成回单时自动校验
	//eqpId 设备ID  glbh 光路编号   portRightPosition端子正确位置
	public String compareWorkOrder_new(Map paramMap){
		try{
			String eqpId=paramMap.get("eqpId").toString();//设备ID指的是端子所属的直属设备
			String taskId=paramMap.get("taskId").toString();;//任务id  通过taskid查询oldtaskid
			String portId=paramMap.get("port_id").toString();;//端子ID
			String glbh=paramMap.get("glbm").toString();;//光路编号
			String area_id=paramMap.get("area_id").toString();;//地市
			
			Map param= new HashMap();
			param.put("eqpId", eqpId);
			param.put("taskId", taskId);
			param.put("glbh", glbh);
			param.put("portId", portId);
			
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			param.put("jndi", jndi);
			//1.首先通过任务id和端子ID从设备记录表中获取检查时检查人员备注的正确位置（portidrightposition）
			String portidrightposition= checkTaskDao.getportRightPosition(param);
			Map<String,String> ossMap=new HashMap<String, String>();
			String ossPort="";
			if(portidrightposition !=null && !"".equals(portidrightposition)){
				//2.首先通过设备id和光路编号从OSS中取出光路编号对应的端子id(ossPort)
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				ossMap= checkTaskDao.getOssPort(param);//通过设备ID和光路编号到OSS实时查询光路占用端口		
				SwitchDataSourceUtil.clearDataSource();
				if(ossMap!=null&&ossMap.size()>0){
					ossPort=ossMap.get("PORT_ID");
				}
				//3.通过portRightPosition与ossPort进行对比，判断端子id是否一致，如果一致，则自动校验通过，无需审核员审核，自动归档，
				//不一致，回单到审核员处审核
				if(portidrightposition.equals(ossPort)){
					return "000";//自动校验成功
				}else{
					return "001";//自动校验失败
				}
			}else{
				return "001";//自动校验失败
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "001";//自动校验失败
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
	}

	
	public String submitCheckOrder2(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 传入的参数
			 */
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");//检查人
			String taskId = json.getString("taskId");
			String areaId = json.getString("areaId");
			String eqpId = json.getString("eqpId");//设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");//设备名称
			String eqpAddress =json.getString("eqpAddress");
			String eqpPhotoIds = json.getString("photoId");//设备照片
			String remarks = json.getString("remarks");//现场规范
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String is_bill = json.getString("is_bill");//是否需要整改
			String allcount = json.getString("allcount");// 全部端子数		
			String truecount = json.getString("truecount");// 正确端子数	
			String checkWay = json.getString("checkWay");// 检查方式 ：1 工单检查 ，2 设备端子检查
			// 覆盖地址检查数量
			JSONArray addressArray = null;
			int addressCheckCnt = 0;
			if (json.containsKey("allEqpAddress")) {
				addressArray = json.getJSONArray("allEqpAddress");
				addressCheckCnt = addressArray.size();
			}			
			/**
			 * 端子信息
			 */
			JSONArray innerArray =json.getJSONArray("port");
			
			
			//先查询出设备所属网格，设备类型，区域
			 Map param=new HashMap();
			 param.put("areaId", areaId);
			 param.put("eqpId", eqpId);
			 param.put("eqpNo", eqpNo);
			 Map eqpMap=checkOrderDao.getEqpType_Grid(param);
			 String eqpTypeId=eqpMap.get("RES_TYPE_ID").toString();//设备类型
			 String grid= null==eqpMap.get("GRID_ID")?"":eqpMap.get("GRID_ID").toString();//设备所属网格
			 String sonAreaId=eqpMap.get("AREA_ID").toString();//设备所属区域
			
			 String douDiGangNo="";//兜底岗账号
			 String contractPersonNo="";//承包人账号
			 if("1".equals(is_bill)){
				 if(!"".equals(grid)){
					 List<Map<String,String>>  parList=checkOrderDao.queryDouDiGang(grid);				
					 for(int i=0;i<parList.size();i++){
						 if(douDiGangNo.length()>0){
							 douDiGangNo=douDiGangNo+","+parList.get(i).get("STAFF_NO");
						 }else{
							 douDiGangNo=parList.get(i).get("STAFF_NO");
						 }
					 }
				 }else{
					 douDiGangNo="";
				 }
				 Map<String,String>  contractPersonMap=checkOrderDao.getEqpContractNo(param);				
				 if(contractPersonMap!=null && contractPersonMap.size()>0){
					 contractPersonNo=contractPersonMap.get("CONTRACT_PERSION_NO");
				 }
				
			 }
			 //检查人员姓名、账号
			 Map maps=checkOrderDao.queryByStaffId(staffId);
			 String staffName=maps.get("STAFF_NAME").toString();
			 String staffNo=maps.get("STAFF_NO").toString();
			 
			 String equType="";
			 if("703".equals(eqpTypeId)){
				 equType="1";
			 }else if("411".equals(eqpTypeId)){
				 equType="2";
			 }else if("704".equals(eqpTypeId)){
				 equType="3";
			 }else{
				 equType="4";
			 }
			 //错误端子列表 、错误端子描述
			 String errorPortIds="";
			 String errorPortDescript="";
			 
			 //检查端子数量 、检查端子描述
			 int checkedPortIds=0;
			 String checkedPortDescript="";
			 int adressCheckCnt=0;
			 
			 
			 
			Map orderParam = new HashMap();
			JSONArray orderArray=new JSONArray();
			JSONArray csv_orderArray=new JSONArray();//综调
			JSONArray iom_orderArray=new JSONArray();//IOM
			JSONArray no_csv_iom_orderArray=new JSONArray();//两者都未匹配上
			JSONObject orderObject=new JSONObject();
			Map<String, String> orderResult=new HashMap<String, String>();
			Map taskDetailMap = new HashMap();
			Map recordMap=new HashMap();
			for(int i=0;i<innerArray.size();i++){
				JSONObject port = (JSONObject)innerArray.get(i);
				String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
				String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
				String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
				String portId = port.getString("portId");
				String portNo = port.getString("portNo");
				String portName = port.getString("portName");
				String orderId = port.getString("order_id");//工单ID
				String orderNo = port.getString("order_no");//工单编码
				String photoIds = port.getString("photoId");
				String reason = port.getString("reason");
				String isCheckOK = port.getString("isCheckOK");
				//错误端子及其描述
				if("1".equals(isCheckOK)){
					if(errorPortIds.length()>0){
						errorPortIds=errorPortIds+","+portId;
						errorPortDescript=errorPortDescript+","+reason;
					}else{
						errorPortIds=portId;
						errorPortDescript=reason;
					}
				}
				
				String actionType=port.getString("actionType");//工单性质
				String truePortId =  port.getString("truePortId");//正确端子
				String truePortNo = port.getString("truePortNo");//正确编码
				String rightEqpId = port.getString("rightEqpId");//正确设备ID
				String rightEqpNo = port.getString("rightEqpNo");//正确设备编码
				if("查询".equals(truePortNo)){
					truePortId="";
					truePortNo="";
					rightEqpId="";
					rightEqpNo="";
				}
				
				String changedPortId=port.getString("portId_new");//修改后的端子id
				String changedPortNo=port.getString("localPortNo");//修改后的端子编码
				String changedEqpId=port.getString("sbid_new");//修改后的设备ID
				String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
				String glbm = null==port.get("glbm")?"":port.getString("glbm");
				String glmc = null==port.get("glmc")?"":port.getString("glmc");
				String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
				String type = null;
				String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
				if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
					type = "1";//装维
					
				}else{
					type = "0";//综维
				}
				//通过设备id和端子id去找端子对应的工单
				String orderMark="";
				String order_team_id="";
				String order_staff_id="";
				String other_system_staff_id="";
				String auditor="";
				String maintor="";
				orderParam.put("eqpId", eqpId_port);
				orderParam.put("portId", portId);
				
				if("".equals(orderNo)){//只要工单号为空，则通过设备ID和端子去查询端子对应的工单信息（最近一次）
					orderResult=checkOrderDao.getOrderInfoNew(orderParam);
					//orderResult=checkOrderDao.getOrderInfo(orderParam);
				}else{
					orderResult=checkOrderDao.getOrderOfBanZuNew(orderNo);
					//orderResult=checkOrderDao.getOrderOfBanZu(orderNo);//通过工单去查询工单所在班组，及施工人
				}
				//写一个公共方法，对以上查询到的结果进行分析处理
				if(orderResult!=null&&orderResult.size()>0){//能匹配到工单
					Map<String,String> info=this.dealWithOrder(orderResult);
				    orderId=info.get("orderId");
				    orderNo=info.get("orderNo");
				    orderMark=info.get("orderMark");
				    order_team_id=info.get("order_team_id");
				    order_staff_id=info.get("order_staff_id");//维护员账号ID或账号
				    maintor=info.get("maintor");//维护员账号ID或账号
				    auditor=info.get("auditor");//审核员账号ID
				}	
				/*if(orderResult != null && orderResult.size()>0){
					orderId=orderResult.get("ORDER_ID");
					orderNo=orderResult.get("ORDER_NO");
				    orderMark=orderResult.get("MARK");//判断是综调的还是IOM的
				    order_team_id=orderResult.get("ORDER_TEAM_ID");//工单所在班组
				    //order_staff_id=orderResult.get("STAFF_ID");//施工人账号id
				    other_system_staff_id=orderResult.get("OTHER_SYSTEM_STAFF_ID");//外系统人员账号ID
				  //通过other_system_staff_id查询人员账号ID
				    if(other_system_staff_id!=null && !"".equals(other_system_staff_id) && !"null".equals(other_system_staff_id)){
				    	Map<String,String> staffMap=checkOrderDao.getStaffIdByOther(other_system_staff_id);
				    	if(staffMap!=null&&staffMap.size()>0){
				    		order_staff_id=staffMap.get("STAFF_ID");
				    	}
				    }
				}*/
				if("1".equals(is_bill)&&"1".equals(isCheckOK)){//需整改同时要求端子不合格
					orderObject.put("eqpId_port", eqpId_port);
					orderObject.put("eqpNo_port", eqpNo_port);
					orderObject.put("eqpName_port", eqpName_port);
					orderObject.put("eqpAddress", eqpAddress);
					orderObject.put("actionType", actionType);
					orderObject.put("portId", portId);
					orderObject.put("portNo", portNo);
					orderObject.put("portName", portName);
					orderObject.put("photoIds", photoIds);
					orderObject.put("reason", reason);
					orderObject.put("isCheckOK", isCheckOK);
					orderObject.put("glbm", glbm);
					orderObject.put("glmc", glmc);
					orderObject.put("isH", isH);
					orderObject.put("type", type);
					orderObject.put("orderId", orderId);
					orderObject.put("orderNo", orderNo);
					orderObject.put("orderMark", orderMark);
					orderObject.put("order_team_id", order_team_id);					
					orderObject.put("portRightPosition", truePortId);//端子编码正确位置
					orderObject.put("portIdRightPosition", truePortNo);//端子ID正确位置
					orderObject.put("eqpNo_rightPortNo", truePortNo);//设备编码正确位置
					orderObject.put("eqpId_rightPortNo", rightEqpNo);//设备ID正确位置
					orderObject.put("record_type", "0");
					orderObject.put("area_id", areaId);
					orderObject.put("son_area_id", sonAreaId);
					orderObject.put("changedPortId", changedPortId);//修改后端子ID
					orderObject.put("changedPortNo", changedPortNo);//修改后端子编码
					orderObject.put("changedEqpId", changedEqpId);//修改后设备ID
					orderObject.put("changedEqpNo", changedEqpNo);
					/*if(!"".equals(order_team_id)&& null != order_team_id && !"null".equals(order_team_id)){//工单所在班组不为空
						String team_id=order_team_id;
						Map receiverMap=checkTaskDao.getOrderReceiverOfBanZu(team_id);
						if(receiverMap!=null&&receiverMap.size()>0){
							if(!"".equals(order_staff_id)){
								maintor=order_staff_id;
								auditor=receiverMap.get("STAFF_ID").toString();//审核员
							}else{
								maintor=receiverMap.get("STAFF_ID").toString();//维护人  施工人为空，维护员就是审核员
								auditor=receiverMap.get("STAFF_ID").toString();//审核员
							}
						}else{
							if(!"".equals(order_staff_id)){
								maintor=order_staff_id;
							}
							auditor="";
						}
					}else{//工单所在班组为空，表明工单既未匹配上综调，又未匹配上 iom，查找兜底岗和网格审核员
						maintor="";
						auditor="";
					}*/
					orderObject.put("maintor", maintor);
					orderObject.put("auditor", auditor);
					
					if(orderResult==null){
						no_csv_iom_orderArray.add(orderObject);
					}
					if("1".equals(orderMark)){
						csv_orderArray.add(orderObject);
					} 
					if("2".equals(orderMark)){
						iom_orderArray.add(orderObject);
					}
				}
				//将端子记录插入任务详情表
				int recordId = checkOrderDao.getRecordId();
				taskDetailMap.put("TASK_ID", taskId);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
				taskDetailMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
				taskDetailMap.put("GLBM", glbm);
				taskDetailMap.put("GLMC", glmc);
				taskDetailMap.put("PORT_ID", portId);
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
				//将端子记录插入记录表
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				
				recordMap.put("recordId", recordId);	
				recordMap.put("task_id", taskId);
				recordMap.put("glbm", glbm);
				recordMap.put("detail_id", detail_id);
				recordMap.put("eqpId", eqpId_port);
				recordMap.put("eqpNo", eqpNo_port);
				recordMap.put("eqpName", eqpName_port);
				recordMap.put("eqpAddress", eqpAddress);
				recordMap.put("longitude", longitude);
				recordMap.put("latitude", latitude);
				recordMap.put("staffId", staffId);
				recordMap.put("remarks", remarks);//现场规范
				recordMap.put("port_id", portId);
				recordMap.put("port_no", portNo);
				recordMap.put("action_type", actionType);
				recordMap.put("orderId", orderId);
				recordMap.put("orderNo", orderNo);
				if("1".equals(orderMark)){
					recordMap.put("isFrom", "0");//0表示综调  1 表示 iom
				}else if("2".equals(orderMark)){
					recordMap.put("isFrom", "1");
				}else{
					recordMap.put("isFrom", "");
				}
				recordMap.put("port_name", portName);
				recordMap.put("descript", reason);//端子错误描述
				recordMap.put("isCheckOK", isCheckOK);//端子是否合格
				recordMap.put("portRightPosition", truePortId);//端子编码正确位置
				recordMap.put("portIdRightPosition", truePortNo);//端子ID正确位置
				recordMap.put("eqpNo_rightPortNo", truePortNo);//设备编码正确位置
				recordMap.put("eqpId_rightPortNo", rightEqpNo);//设备ID正确位置
				recordMap.put("record_type", "0");
				recordMap.put("area_id", areaId);
				recordMap.put("son_area_id", sonAreaId);
				recordMap.put("changedPortId", changedPortId);//修改后端子ID
				recordMap.put("changedPortNo", changedPortNo);//修改后端子编码
				recordMap.put("changedEqpId", changedEqpId);//修改后设备ID
				recordMap.put("changedEqpNo", changedEqpNo);//修改后设备编码
				recordMap.put("checkWay", checkWay);
				//插入记录表
				checkOrderDao.insertEqpRecordNewYy(recordMap);
				
				// 保存端子照片关系
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskId);
				photoMap.put("DETAIL_ID", detail_id);
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId);
				if (!"".equals(photoIds)) {
					String[] photos = photoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}
				//将修改后的端子插入流程表 tb_cablecheck_process
				if("0".equals(isCheckOK)){
					if(!"".equals(changedPortNo)){
						String content="";
						if(eqpId_port.equals(changedEqpId)){
							content=glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
						}else{
							content =glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
						}
						recordMap.put("status", "66");//一键改
						recordMap.put("remark", "一键改");
						recordMap.put("receiver", "");
						recordMap.put("content", content);
						//checkTaskDao.addProcessNew(recordMap);
						checkProcessDao.addProcessNew_orderNo(recordMap);
					}
				}
				//工单检查时间
				if(!"".equals(orderNo)){
					recordMap.put("order_no", orderNo);
					checkTaskDao.updateOrderCheckTime(recordMap);
				}
			}
			//插入设备详情
			int recordId = checkOrderDao.getRecordId();
			Map taskDetailEqpMap = new HashMap();
			taskDetailMap.put("TASK_ID", taskId);
			taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
			taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
			taskDetailMap.put("CHECK_FLAG", "1");
			taskDetailMap.put("GLBM", "");
			taskDetailMap.put("GLMC", "");
			taskDetailMap.put("PORT_ID", "");
			taskDetailMap.put("dtsj_id", "");
			taskDetailMap.put("eqpId_port", "");
			taskDetailMap.put("eqpNo_port", "");
			taskDetailMap.put("eqpName_port", "");
			taskDetailMap.put("orderNo", "");
			taskDetailMap.put("orderMark", "");
			taskDetailMap.put("actionType", "");
			taskDetailMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
			String detail_id = taskDetailMap.get("DETAIL_ID").toString();
			//插入设备记录
			Map eqpRecordMap = new HashMap();
			eqpRecordMap.put("recordId", recordId);	
			eqpRecordMap.put("task_id", taskId);
			eqpRecordMap.put("glbm", "");
			eqpRecordMap.put("detail_id", detail_id);
			eqpRecordMap.put("eqpId", eqpId);
			eqpRecordMap.put("eqpNo", eqpNo);
			eqpRecordMap.put("eqpName", eqpName);
			eqpRecordMap.put("eqpAddress", eqpAddress);
			eqpRecordMap.put("longitude", longitude);
			eqpRecordMap.put("latitude", latitude);
			eqpRecordMap.put("staffId", staffId);
			eqpRecordMap.put("remarks", remarks);//现场规范
			eqpRecordMap.put("port_id", "");
			eqpRecordMap.put("port_no", "");
			eqpRecordMap.put("action_type", "");
			eqpRecordMap.put("orderId", "");
			eqpRecordMap.put("orderNo", "");
			eqpRecordMap.put("isFrom", "");
			eqpRecordMap.put("port_name", "");
			eqpRecordMap.put("descript", "");//端子错误描述
			eqpRecordMap.put("isCheckOK",is_bill);//端子是否合格
			eqpRecordMap.put("portRightPosition", "");//端子编码正确位置
			eqpRecordMap.put("portIdRightPosition", "");//端子ID正确位置
			eqpRecordMap.put("eqpNo_rightPortNo", "");//设备编码正确位置
			eqpRecordMap.put("eqpId_rightPortNo", "");//设备ID正确位置
			eqpRecordMap.put("record_type", "0");
			eqpRecordMap.put("area_id", areaId);
			eqpRecordMap.put("son_area_id", sonAreaId);
			eqpRecordMap.put("changedPortId", "");//修改后端子ID
			eqpRecordMap.put("changedPortNo", "");//修改后端子编码
			eqpRecordMap.put("changedEqpId", "");//修改后设备ID
			eqpRecordMap.put("changedEqpNo", "");//修改后设备编码
			eqpRecordMap.put("checkWay", checkWay);//修改后设备编码
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(eqpRecordMap);
			//插入流程环节
			Map processMap = new HashMap();
			processMap.put("task_id", taskId);
			processMap.put("oper_staff", staffId);
			processMap.put("status", "8");
			processMap.put("remark", "检查提交");
			processMap.put("content", "端子检查合格或现场已整改，任务直接归档");
			processMap.put("receiver", "");
			checkProcessDao.addProcessNew(processMap);//无需整改直接归档
			/**
			 * 保存设备的照片信息
			 */
			if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskId);
				photoMap.put("DETAIL_ID", detail_id);
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", "0");// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
			//更新任务表
			Map taskMap = new HashMap();
			taskMap.put("task_id", taskId);
			taskMap.put("staffId", staffId);
			taskMap.put("remarks", remarks);
			if("0".equals(is_bill)){
				taskMap.put("is_need_zg", "0");
			}else{
				taskMap.put("is_need_zg", "1");
			}
			taskMap.put("statusId", "8");
			taskMap.put("checkWay", checkWay);
			checkOrderDao.updateTaskOrder(taskMap);
			//将设备检查时间、检查数、正确数插入设备表
			Map portmap = new HashMap();
			portmap.put("areaId", areaId);
			portmap.put("sonAreaId", sonAreaId);
			portmap.put("allcount", allcount);
			portmap.put("truecount", truecount);
			portmap.put("eqpId", eqpId);
			portmap.put("eqpNo", eqpNo);
			checkOrderDao.updateEqu_time_num(portmap);
			
			for (int i = 0; i < addressArray.size(); i++) {
				int id = checkOrderDao.geteqpAddressId();
				JSONObject eqp = (JSONObject) addressArray.get(i);

				String eqpId_add = null == eqp.get("eqpId") ? ""
						: eqp.getString("eqpId");
				String eqpNo_add = null == eqp.get("eqpNo") ? ""
						: eqp.getString("eqpNo");
				String location_id = eqp.getString("locationId");
				String address_id = eqp.getString("addressId");
				String address_name = eqp.getString("addressName");
				String is_check_ok = eqp.getString("is_check_ok");
				String error_reason = null == eqp
						.get("error_reason") ? "" : eqp
						.getString("error_reason");

				Map adddressMap = new HashMap();
				adddressMap.put("id", id);
				adddressMap.put("phy_eqp_id", eqpId_add);
				adddressMap.put("phy_eqp_no", eqpNo_add);
				adddressMap.put("install_eqp_id", eqpId);
				adddressMap.put("location_id", location_id);
				adddressMap.put("address_id", address_id);
				adddressMap.put("address_name", address_name);
				adddressMap.put("is_check_ok", is_check_ok);
				adddressMap.put("error_reason", error_reason);
				adddressMap.put("task_id", taskId);
				adddressMap.put("create_staff", staffId);
				adddressMap.put("area_id", areaId);
				adddressMap.put("son_area_id", sonAreaId);
				checkOrderDao.insertEqpAddress(adddressMap);

			}
			//光网助手检查触发线路工单，先插入 tb_base_gwzs_lineworkorder（光网助手线路工单表），然后通过同步平台发到FTP目录下
			checkedPortIds=innerArray.size();
			checkedPortDescript=errorPortDescript;
			adressCheckCnt=addressArray.size();//覆盖地址数量
			Map lineWork=new HashMap();
			lineWork.put("gwMan", staffName);//光网助手检查人员
			lineWork.put("taskId", taskId);//任务ID
			lineWork.put("gwManAccount", staffNo);//检查人员账号
			lineWork.put("equCode", eqpNo);//设备编码
			lineWork.put("equName", eqpName);//设备名称
			lineWork.put("chekPortNum", checkedPortIds);//检查端子数量
			lineWork.put("adressCheckCnt", adressCheckCnt);//检查端子数量
			lineWork.put("gwContent", checkedPortDescript);//检查内容描述
			lineWork.put("workhours", "0");//检查内容描述
			checkOrderDao.insertLineWorkOrder(lineWork);
			
			/**
			 * 如果需要整改，生成新的任务
			 */
			if("1".equals(is_bill)){
				//匹配上综调工单
				if(csv_orderArray != null && csv_orderArray.size()>0){
					generateNewTaskCsv(staffId,taskId,areaId,eqpId,eqpNo,eqpName,eqpAddress,
							 eqpPhotoIds,remarks,longitude,latitude,sonAreaId,checkWay,csv_orderArray);
				}
				//匹配上iom工单
				if(iom_orderArray != null && iom_orderArray.size()>0){
					generateNewTaskIom(staffId, taskId, areaId, eqpId, eqpNo,
							eqpName, eqpAddress, eqpPhotoIds, remarks, longitude,
							latitude, sonAreaId, douDiGangNo, contractPersonNo,
							staffName, equType, errorPortIds, errorPortDescript,checkWay,
							iom_orderArray);
				}
				//两者都未匹配上（既为匹配上综调，又为匹配上iom）
				if(no_csv_iom_orderArray != null && no_csv_iom_orderArray.size()>0){
					generateNewTaskNoCsvIom(staffId, taskId, areaId, eqpId, eqpNo,
							eqpName, eqpAddress, eqpPhotoIds, remarks, longitude,
							latitude, sonAreaId, douDiGangNo, contractPersonNo,
							staffName, equType, errorPortIds, errorPortDescript,checkWay,
							no_csv_iom_orderArray);
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "工单提交失败");
		}
		return result.toString();
	}

	public void generateNewTaskNoCsvIom(String staffId, String taskId,
			String areaId, String eqpId, String eqpNo, String eqpName,
			String eqpAddress, String eqpPhotoIds, String remarks,
			String longitude, String latitude, String sonAreaId,
			String douDiGangNo, String contractPersonNo, String staffName,
			String equType, String errorPortIds, String errorPortDescript,String checkWay,
			JSONArray no_csv_iom_orderArray) {
		if(no_csv_iom_orderArray!=null && no_csv_iom_orderArray.size()>0){					
			//生成整改任务
			Map troubleTaskMap = new HashMap();
			String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
			String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
			troubleTaskMap.put("TASK_NO",TASK_NO);
			troubleTaskMap.put("TASK_NAME",TASK_NAME);
			troubleTaskMap.put("TASK_TYPE", 3);// 问题上报
			troubleTaskMap.put("STATUS_ID", 6);//待回单状态
			troubleTaskMap.put("INSPECTOR",staffId);
			troubleTaskMap.put("CREATE_STAFF",staffId);
			troubleTaskMap.put("SON_AREA_ID",sonAreaId);
			troubleTaskMap.put("AREA_ID",areaId);
			troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）														
			troubleTaskMap.put("REMARK",remarks);
			troubleTaskMap.put("INFO", "");
			troubleTaskMap.put("NO_EQPNO_FLAG","0");// 无编码上报
			troubleTaskMap.put("OLD_TASK_ID",taskId);// 老的task_id
			troubleTaskMap.put("IS_NEED_ZG","1");// 是否整改,1需要整改
			troubleTaskMap.put("SBID", eqpId);// 设备id
			troubleTaskMap.put("MAINTOR","");// 维护员
			troubleTaskMap.put("auditor","");// 审核员
			troubleTaskMap.put("checkWay",checkWay);
			checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
			String newTaskId = troubleTaskMap.get("TASK_ID").toString();
			logger.info("【需要整改添加一张新的工单taskId】"+ newTaskId);
			//遍历端子信息
			Map portRecordIomMap=new HashMap();
			Map portDetailIomMap=new HashMap();
			for(int m=0;m<no_csv_iom_orderArray.size();m++){
				JSONObject portObject= no_csv_iom_orderArray.getJSONObject(m);
				//将端子记录插入任务详情表
				int recordId_iom = checkOrderDao.getRecordId();
				portDetailIomMap.put("TASK_ID", newTaskId);
				portDetailIomMap.put("INSPECT_OBJECT_ID", recordId_iom);
				portDetailIomMap.put("INSPECT_OBJECT_NO", portObject.get("portNo"));
				portDetailIomMap.put("INSPECT_OBJECT_TYPE", 1);
				portDetailIomMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
				portDetailIomMap.put("GLBM", portObject.get("glbm"));
				portDetailIomMap.put("GLMC", portObject.get("glmc"));
				portDetailIomMap.put("PORT_ID", portObject.get("portId"));
				portDetailIomMap.put("dtsj_id", "");
				portDetailIomMap.put("eqpId_port", "");
				portDetailIomMap.put("eqpNo_port", "");
				portDetailIomMap.put("eqpName_port", "");
				portDetailIomMap.put("orderNo", "");
				portDetailIomMap.put("orderMark", "");
				portDetailIomMap.put("actionType", "");
				portDetailIomMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(portDetailIomMap);
				//将端子记录插入记录表
				String detail_id_iom = portDetailIomMap.get("DETAIL_ID").toString();						
				portRecordIomMap.put("recordId", recordId_iom);	
				portRecordIomMap.put("task_id", newTaskId);
				portRecordIomMap.put("glbm", portObject.get("glbm"));
				portRecordIomMap.put("detail_id", detail_id_iom);
				portRecordIomMap.put("eqpId", portObject.get("eqpId_port"));
				portRecordIomMap.put("eqpNo", portObject.get("eqpNo_port"));
				portRecordIomMap.put("eqpName", portObject.get("eqpName_port"));
				portRecordIomMap.put("eqpAddress", portObject.get("eqpAddress"));
				portRecordIomMap.put("longitude", longitude);
				portRecordIomMap.put("latitude", latitude);
				portRecordIomMap.put("staffId", staffId);
				portRecordIomMap.put("remarks", remarks);//现场规范
				portRecordIomMap.put("port_id", portObject.get("portId"));
				portRecordIomMap.put("port_no", portObject.get("portNo"));
				portRecordIomMap.put("action_type", portObject.get("actionType"));
				portRecordIomMap.put("orderId", portObject.get("orderId"));
				portRecordIomMap.put("orderNo", portObject.get("orderNo"));
				portRecordIomMap.put("isFrom", "");//0表示综调  1 表示 iom					
				portRecordIomMap.put("port_name", portObject.get("portName"));
				portRecordIomMap.put("descript", portObject.get("reason"));//端子错误描述
				portRecordIomMap.put("isCheckOK", portObject.get("isCheckOK"));//端子是否合格
				portRecordIomMap.put("portRightPosition", portObject.get("truePortNo"));//端子编码正确位置
				portRecordIomMap.put("portIdRightPosition", portObject.get("truePortId"));//端子ID正确位置
				portRecordIomMap.put("eqpNo_rightPortNo", portObject.get("rightEqpNo"));//设备编码正确位置
				portRecordIomMap.put("eqpId_rightPortNo", portObject.get("rightEqpId"));//设备ID正确位置
				portRecordIomMap.put("record_type", "0");
				portRecordIomMap.put("area_id", areaId);
				portRecordIomMap.put("son_area_id", sonAreaId);
				portRecordIomMap.put("changedPortId",  portObject.get("portId_new"));//修改后端子ID
				portRecordIomMap.put("changedPortNo",  portObject.get("localPortNo"));//修改后端子编码
				portRecordIomMap.put("changedEqpId",  portObject.get("sbid_new"));//修改后设备ID
				portRecordIomMap.put("changedEqpNo", portObject.get("sbbm_new"));//修改后设备编码
				portRecordIomMap.put("checkWay", checkWay);
				//插入记录表
				checkOrderDao.insertEqpRecordNewYy(portRecordIomMap);
				
				// 保存端子照片关系
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", newTaskId);
				photoMap.put("DETAIL_ID", detail_id_iom);
				photoMap.put("OBJECT_ID", recordId_iom);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", 0);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId_iom);
				if (!"".equals(portObject.getString("photoIds"))) {
					String[] photos = portObject.getString("photoIds").split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}
			}
			//插入设备详情
			int eqpRecordId = checkOrderDao.getRecordId();
			Map eqpDetailMap = new HashMap();
			eqpDetailMap.put("TASK_ID", newTaskId);
			eqpDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
			eqpDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			eqpDetailMap.put("INSPECT_OBJECT_TYPE", 0);
			eqpDetailMap.put("CHECK_FLAG", "1");
			eqpDetailMap.put("GLBM", "");
			eqpDetailMap.put("GLMC", "");
			eqpDetailMap.put("PORT_ID", "");
			eqpDetailMap.put("dtsj_id", "");
			eqpDetailMap.put("eqpId_port", "");
			eqpDetailMap.put("eqpNo_port", "");
			eqpDetailMap.put("eqpName_port", "");
			eqpDetailMap.put("orderNo", "");
			eqpDetailMap.put("orderMark", "");
			eqpDetailMap.put("actionType", "");
			eqpDetailMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(eqpDetailMap);
			String eqp_detail_id = eqpDetailMap.get("DETAIL_ID").toString();
			//插入设备记录
			Map eqpRecordMapNew = new HashMap();
			eqpRecordMapNew.put("recordId", eqpRecordId);	
			eqpRecordMapNew.put("task_id", newTaskId);
			eqpRecordMapNew.put("glbm", "");
			eqpRecordMapNew.put("detail_id", eqp_detail_id);
			eqpRecordMapNew.put("eqpId", eqpId);
			eqpRecordMapNew.put("eqpNo", eqpNo);
			eqpRecordMapNew.put("eqpName", eqpName);
			eqpRecordMapNew.put("eqpAddress", eqpAddress);
			eqpRecordMapNew.put("longitude", longitude);
			eqpRecordMapNew.put("latitude", latitude);
			eqpRecordMapNew.put("staffId", staffId);
			eqpRecordMapNew.put("remarks", remarks);//现场规范
			eqpRecordMapNew.put("port_id", "");
			eqpRecordMapNew.put("port_no", "");
			eqpRecordMapNew.put("action_type", "");
			eqpRecordMapNew.put("orderId", "");
			eqpRecordMapNew.put("orderNo", "");
			eqpRecordMapNew.put("isFrom", "");
			eqpRecordMapNew.put("port_name", "");
			eqpRecordMapNew.put("descript", "");//端子错误描述
			eqpRecordMapNew.put("isCheckOK", "1");//端子是否合格
			eqpRecordMapNew.put("portRightPosition", "");//端子编码正确位置
			eqpRecordMapNew.put("portIdRightPosition", "");//端子ID正确位置
			eqpRecordMapNew.put("eqpNo_rightPortNo", "");//设备编码正确位置
			eqpRecordMapNew.put("eqpId_rightPortNo", "");//设备ID正确位置
			eqpRecordMapNew.put("record_type", "0");
			eqpRecordMapNew.put("area_id", areaId);
			eqpRecordMapNew.put("son_area_id", sonAreaId);
			eqpRecordMapNew.put("changedPortId", "");//修改后端子ID
			eqpRecordMapNew.put("changedPortNo", "");//修改后端子编码
			eqpRecordMapNew.put("changedEqpId", "");//修改后设备ID
			eqpRecordMapNew.put("changedEqpNo", "");//修改后设备编码
			eqpRecordMapNew.put("checkWay", checkWay);
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(eqpRecordMapNew);
			//整改流程在插入记录表之前，先通过taskid找到老的流程，然后将其插入新的流程表
			List<Map<String, String>> processlList=checkOrderDao.queryProcessByOldTaskId(taskId);
			if(processlList !=null && processlList.size()>0){				
				for(Map<String, String> pro:processlList){
					pro.put("TASK_ID", newTaskId);
					checkProcessDao.addProcessNew_orderNo(pro);
				}
			}
			//插入流程环节
			Map processMap = new HashMap();
			processMap.put("task_id", newTaskId);
			processMap.put("oper_staff", staffId);
			processMap.put("status", "6");
			processMap.put("remark", "检查提交");
			processMap.put("content", "生成整改工单并自动推送至集约化");
			processMap.put("receiver", "");
			checkProcessDao.addProcessNew(processMap);
		
			/**
			 * 保存设备的照片信息
			 */
			if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", newTaskId);
				photoMap.put("DETAIL_ID", eqp_detail_id);
				photoMap.put("OBJECT_ID", eqpRecordId);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", "0");// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", eqpRecordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}					
			}
			//String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
			//隐患工单自动推送至集约化
			Map iomMap=new HashMap();
			iomMap.put("sysRoute", "GWZS");
			iomMap.put("taskTypes", "6");
			iomMap.put("staffName", staffName);//检查人员姓名
			iomMap.put("taskid", newTaskId);//任务ID
			iomMap.put("TASK_NAME", TASK_NAME);//任务ID
			iomMap.put("IOMid_numberList", "");//施工人员账号
			iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
			iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
			iomMap.put("eqpNo", eqpNo);
			iomMap.put("eqpName", eqpName);
			iomMap.put("equType", equType);
			iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
			iomMap.put("descript", errorPortDescript);//错误描述
			iomMap.put("taskCatgory", "LINE");//错误描述
			autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
		}
	}

	public void generateNewTaskIom(String staffId, String taskId,
			String areaId, String eqpId, String eqpNo, String eqpName,
			String eqpAddress, String eqpPhotoIds, String remarks,
			String longitude, String latitude, String sonAreaId,
			String douDiGangNo, String contractPersonNo, String staffName,
			String equType, String errorPortIds, String errorPortDescript,String checkWay,
			JSONArray iom_orderArray) {
		JSONObject iom_orderObject1=new JSONObject();
		JSONObject iom_orderObject2=new JSONObject();
		for(int i =0 ;i<iom_orderArray.size();i++){
			JSONArray sameMaintor=new JSONArray();//派发给同一个维护员，审核员暂时默认为一致，暂时不考虑
			iom_orderObject1=iom_orderArray.getJSONObject(i);
			sameMaintor.add(iom_orderObject1);
			String maintor1=iom_orderObject1.getString("maintor");
			for(int j =i+1 ;j<iom_orderArray.size();j++){
				iom_orderObject2=iom_orderArray.getJSONObject(j);
				String maintor2=iom_orderObject2.getString("maintor");
				if(maintor1.equals(maintor2)){//分组派发给同一个维护员
					sameMaintor.add(iom_orderObject2);
					iom_orderArray.remove(j);
					j--;
				}
			}
			//生成整改任务
			Map troubleTaskMap = new HashMap();
			String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
			String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
			troubleTaskMap.put("TASK_NO",TASK_NO);
			troubleTaskMap.put("TASK_NAME",TASK_NAME);
			troubleTaskMap.put("TASK_TYPE", 3);// 问题上报
			troubleTaskMap.put("STATUS_ID", 6);//待回单状态
			troubleTaskMap.put("INSPECTOR",staffId);
			troubleTaskMap.put("CREATE_STAFF",staffId);
			troubleTaskMap.put("SON_AREA_ID",sonAreaId);
			troubleTaskMap.put("AREA_ID",areaId);
			troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）														
			troubleTaskMap.put("REMARK",remarks);
			troubleTaskMap.put("INFO", "");
			troubleTaskMap.put("NO_EQPNO_FLAG","0");// 无编码上报
			troubleTaskMap.put("OLD_TASK_ID",taskId);// 老的task_id
			troubleTaskMap.put("IS_NEED_ZG","1");// 是否整改,1需要整改
			troubleTaskMap.put("SBID", eqpId);// 设备id
			troubleTaskMap.put("MAINTOR","");// 维护员
			troubleTaskMap.put("auditor","");// 审核员
			troubleTaskMap.put("checkWay",checkWay);
			checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
			String newTaskId = troubleTaskMap.get("TASK_ID").toString();
			logger.info("【需要整改添加一张新的工单taskId】"+ newTaskId);
			//遍历端子信息
			Map portRecordIomMap=new HashMap();
			Map portDetailIomMap=new HashMap();
			for(int m=0;m<sameMaintor.size();m++){
				JSONObject portObject= sameMaintor.getJSONObject(m);
				//将端子记录插入任务详情表
				int recordId_iom = checkOrderDao.getRecordId();
				portDetailIomMap.put("TASK_ID", newTaskId);
				portDetailIomMap.put("INSPECT_OBJECT_ID", recordId_iom);
				portDetailIomMap.put("INSPECT_OBJECT_NO", portObject.get("portNo"));
				portDetailIomMap.put("INSPECT_OBJECT_TYPE", 1);
				portDetailIomMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
				portDetailIomMap.put("GLBM", portObject.get("glbm"));
				portDetailIomMap.put("GLMC", portObject.get("glmc"));
				portDetailIomMap.put("PORT_ID", portObject.get("portId"));
				portDetailIomMap.put("dtsj_id", "");
				portDetailIomMap.put("eqpId_port", "");
				portDetailIomMap.put("eqpNo_port", "");
				portDetailIomMap.put("eqpName_port", "");
				portDetailIomMap.put("orderNo", "");
				portDetailIomMap.put("orderMark", "");
				portDetailIomMap.put("actionType", "");
				portDetailIomMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(portDetailIomMap);
				//将端子记录插入记录表
				String detail_id_iom = portDetailIomMap.get("DETAIL_ID").toString();						
				portRecordIomMap.put("recordId", recordId_iom);	
				portRecordIomMap.put("task_id", newTaskId);
				portRecordIomMap.put("glbm", portObject.get("glbm"));
				portRecordIomMap.put("detail_id", detail_id_iom);
				portRecordIomMap.put("eqpId", portObject.get("eqpId_port"));
				portRecordIomMap.put("eqpNo", portObject.get("eqpNo_port"));
				portRecordIomMap.put("eqpName", portObject.get("eqpName_port"));
				portRecordIomMap.put("eqpAddress", portObject.get("eqpAddress"));
				portRecordIomMap.put("longitude", longitude);
				portRecordIomMap.put("latitude", latitude);
				portRecordIomMap.put("staffId", staffId);
				portRecordIomMap.put("remarks", remarks);//现场规范
				portRecordIomMap.put("port_id", portObject.get("portId"));
				portRecordIomMap.put("port_no", portObject.get("portNo"));
				portRecordIomMap.put("action_type", portObject.get("actionType"));
				portRecordIomMap.put("orderId", portObject.get("orderId"));
				portRecordIomMap.put("orderNo", portObject.get("orderNo"));
				portRecordIomMap.put("isFrom", "1");//0表示综调  1 表示 iom					
				portRecordIomMap.put("port_name", portObject.get("portName"));
				portRecordIomMap.put("descript", portObject.get("reason"));//端子错误描述
				portRecordIomMap.put("isCheckOK", portObject.get("isCheckOK"));//端子是否合格
				portRecordIomMap.put("portRightPosition", portObject.get("portRightPosition"));//端子编码正确位置
				portRecordIomMap.put("portIdRightPosition", portObject.get("portIdRightPosition"));//端子ID正确位置
				portRecordIomMap.put("eqpNo_rightPortNo", portObject.get("eqpNo_rightPortNo"));//设备编码正确位置
				portRecordIomMap.put("eqpId_rightPortNo", portObject.get("eqpId_rightPortNo"));//设备ID正确位置
				portRecordIomMap.put("record_type", "0");
				portRecordIomMap.put("area_id", areaId);
				portRecordIomMap.put("son_area_id", sonAreaId);
				portRecordIomMap.put("changedPortId",  portObject.get("changedPortId"));//修改后端子ID
				portRecordIomMap.put("changedPortNo",  portObject.get("changedPortNo"));//修改后端子编码
				portRecordIomMap.put("changedEqpId",  portObject.get("changedEqpId"));//修改后设备ID
				portRecordIomMap.put("changedEqpNo", portObject.get("changedEqpNo"));//修改后设备编码
				portRecordIomMap.put("checkWay", checkWay);
				//插入记录表
				checkOrderDao.insertEqpRecordNewYy(portRecordIomMap);
				
				// 保存端子照片关系
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", newTaskId);
				photoMap.put("DETAIL_ID", detail_id_iom);
				photoMap.put("OBJECT_ID", recordId_iom);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", 0);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId_iom);
				if (!"".equals(portObject.getString("photoIds"))) {
					String[] photos = portObject.getString("photoIds").split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}
			}
			//插入设备详情
			int eqpRecordId = checkOrderDao.getRecordId();
			Map eqpDetailMap = new HashMap();
			eqpDetailMap.put("TASK_ID", newTaskId);
			eqpDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
			eqpDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			eqpDetailMap.put("INSPECT_OBJECT_TYPE", 0);
			eqpDetailMap.put("CHECK_FLAG", "1");
			eqpDetailMap.put("GLBM", "");
			eqpDetailMap.put("GLMC", "");
			eqpDetailMap.put("PORT_ID", "");
			eqpDetailMap.put("dtsj_id", "");
			eqpDetailMap.put("eqpId_port", "");
			eqpDetailMap.put("eqpNo_port", "");
			eqpDetailMap.put("eqpName_port", "");
			eqpDetailMap.put("orderNo", "");
			eqpDetailMap.put("orderMark", "");
			eqpDetailMap.put("actionType", "");
			eqpDetailMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(eqpDetailMap);
			String eqp_detail_id = eqpDetailMap.get("DETAIL_ID").toString();
			//插入设备记录
			Map eqpRecordMapNew = new HashMap();
			eqpRecordMapNew.put("recordId", eqpRecordId);	
			eqpRecordMapNew.put("task_id", newTaskId);
			eqpRecordMapNew.put("glbm", "");
			eqpRecordMapNew.put("detail_id", eqp_detail_id);
			eqpRecordMapNew.put("eqpId", eqpId);
			eqpRecordMapNew.put("eqpNo", eqpNo);
			eqpRecordMapNew.put("eqpName", eqpName);
			eqpRecordMapNew.put("eqpAddress", eqpAddress);
			eqpRecordMapNew.put("longitude", longitude);
			eqpRecordMapNew.put("latitude", latitude);
			eqpRecordMapNew.put("staffId", staffId);
			eqpRecordMapNew.put("remarks", remarks);//现场规范
			eqpRecordMapNew.put("port_id", "");
			eqpRecordMapNew.put("port_no", "");
			eqpRecordMapNew.put("action_type", "");
			eqpRecordMapNew.put("orderId", "");
			eqpRecordMapNew.put("orderNo", "");
			eqpRecordMapNew.put("isFrom", "");
			eqpRecordMapNew.put("port_name", "");
			eqpRecordMapNew.put("descript", "");//端子错误描述
			eqpRecordMapNew.put("isCheckOK", "1");//端子是否合格
			eqpRecordMapNew.put("portRightPosition", "");//端子编码正确位置
			eqpRecordMapNew.put("portIdRightPosition", "");//端子ID正确位置
			eqpRecordMapNew.put("eqpNo_rightPortNo", "");//设备编码正确位置
			eqpRecordMapNew.put("eqpId_rightPortNo", "");//设备ID正确位置
			eqpRecordMapNew.put("record_type", "0");
			eqpRecordMapNew.put("area_id", areaId);
			eqpRecordMapNew.put("son_area_id", sonAreaId);
			eqpRecordMapNew.put("changedPortId", "");//修改后端子ID
			eqpRecordMapNew.put("changedPortNo", "");//修改后端子编码
			eqpRecordMapNew.put("changedEqpId", "");//修改后设备ID
			eqpRecordMapNew.put("changedEqpNo", "");//修改后设备编码
			eqpRecordMapNew.put("checkWay", checkWay);
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(eqpRecordMapNew);
			//整改流程在插入记录表之前，先通过taskid找到老的流程，然后将其插入新的流程表
			List<Map<String, String>> processlList=checkOrderDao.queryProcessByOldTaskId(taskId);
			if(processlList !=null && processlList.size()>0){				
				for(Map<String, String> pro:processlList){
					pro.put("TASK_ID", newTaskId);
					checkProcessDao.addProcessNew_orderNo(pro);
				}
			}
			//插入流程环节
			Map processMap = new HashMap();
			processMap.put("task_id", newTaskId);
			processMap.put("oper_staff", staffId);
			processMap.put("status", "6");
			processMap.put("remark", "检查提交");
			processMap.put("content", "生成整改工单并自动推送至集约化");
			processMap.put("receiver", "");
			checkProcessDao.addProcessNew(processMap);
		
			/**
			 * 保存设备的照片信息
			 */
			if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", newTaskId);
				photoMap.put("DETAIL_ID", eqp_detail_id);
				photoMap.put("OBJECT_ID", eqpRecordId);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", "0");// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", eqpRecordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}					
			}
			//String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
			//隐患工单自动推送至集约化
			Map iomMap=new HashMap();
			iomMap.put("sysRoute", "GWZS");
			iomMap.put("taskTypes", "6");
			iomMap.put("staffName", staffName);//检查人员姓名
			iomMap.put("taskid", newTaskId);//任务ID
			iomMap.put("TaskName", TASK_NAME);//任务ID
			iomMap.put("IOMid_numberList", maintor1);//施工人员账号
			iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
			iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
			iomMap.put("eqpNo", eqpNo);
			iomMap.put("eqpName", eqpName);
			iomMap.put("equType", equType);
			iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
			iomMap.put("descript", errorPortDescript);//错误描述
			iomMap.put("taskCatgory", "IOM");//错误描述
			autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
		}
	}

	public void generateNewTaskCsv(String staffId, String taskId, String areaId,
			String eqpId, String eqpNo, String eqpName, String eqpAddress,
			String eqpPhotoIds, String remarks, String longitude,
			String latitude, String sonAreaId,String checkWay, JSONArray csv_orderArray) {
		JSONObject csv_orderObject1=new JSONObject();
		JSONObject csv_orderObject2=new JSONObject();
		for(int i =0 ;i<csv_orderArray.size();i++){
			JSONArray sameMaintor=new JSONArray();//派发给同一个维护员，审核员暂时默认为一致，暂时不考虑
			csv_orderObject1=csv_orderArray.getJSONObject(i);
			sameMaintor.add(csv_orderObject1);
			String maintor1=csv_orderObject1.getString("maintor");
			String auditor=csv_orderObject1.getString("auditor");
			for(int j =i+1 ;j<csv_orderArray.size();j++){
				csv_orderObject2=csv_orderArray.getJSONObject(j);
				String maintor2=csv_orderObject2.getString("maintor");
				if(maintor1.equals(maintor2)){//同一个维护员
					sameMaintor.add(csv_orderObject2);
					csv_orderArray.remove(j);
					j--;
				}
			}
			String newTaskId = generateNewCheckTask(staffId, taskId, areaId,
					eqpId, eqpNo, eqpName, remarks, checkWay, sonAreaId,
					maintor1, auditor);
			
			//遍历端子信息
			Map portRecordCsvMap=new HashMap();
			Map portDetailCsvMap=new HashMap();
			for(int m=0;m<sameMaintor.size();m++){
				JSONObject portObject= sameMaintor.getJSONObject(m);
				//将端子记录插入任务详情表
				int recordId_csv = checkOrderDao.getRecordId();
				portDetailCsvMap.put("TASK_ID", newTaskId);
				portDetailCsvMap.put("INSPECT_OBJECT_ID", recordId_csv);
				portDetailCsvMap.put("INSPECT_OBJECT_NO", portObject.get("portNo"));
				portDetailCsvMap.put("INSPECT_OBJECT_TYPE", 1);
				portDetailCsvMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
				portDetailCsvMap.put("GLBM", portObject.get("glbm"));
				portDetailCsvMap.put("GLMC", portObject.get("glmc"));
				portDetailCsvMap.put("PORT_ID", portObject.get("portId"));
				portDetailCsvMap.put("dtsj_id", "");
				portDetailCsvMap.put("eqpId_port", "");
				portDetailCsvMap.put("eqpNo_port", "");
				portDetailCsvMap.put("eqpName_port", "");
				portDetailCsvMap.put("orderNo", "");
				portDetailCsvMap.put("orderMark", "");
				portDetailCsvMap.put("actionType", "");
				portDetailCsvMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(portDetailCsvMap);
				//将端子记录插入记录表
				String detail_id_csv = portDetailCsvMap.get("DETAIL_ID").toString();						
				portRecordCsvMap.put("recordId", recordId_csv);	
				portRecordCsvMap.put("task_id", newTaskId);
				portRecordCsvMap.put("glbm", portObject.get("glbm"));
				portRecordCsvMap.put("detail_id", detail_id_csv);
				portRecordCsvMap.put("eqpId", portObject.get("eqpId_port"));
				portRecordCsvMap.put("eqpNo", portObject.get("eqpNo_port"));
				portRecordCsvMap.put("eqpName", portObject.get("eqpName_port"));
				portRecordCsvMap.put("eqpAddress", portObject.get("eqpAddress"));
				portRecordCsvMap.put("longitude", longitude);
				portRecordCsvMap.put("latitude", latitude);
				portRecordCsvMap.put("staffId", staffId);
				portRecordCsvMap.put("remarks", remarks);//现场规范
				portRecordCsvMap.put("port_id", portObject.get("portId"));
				portRecordCsvMap.put("port_no", portObject.get("portNo"));
				portRecordCsvMap.put("action_type", portObject.get("actionType"));
				portRecordCsvMap.put("orderId", portObject.get("orderId"));
				portRecordCsvMap.put("orderNo", portObject.get("orderNo"));
				portRecordCsvMap.put("isFrom", "0");//0表示综调  1 表示 iom					
				portRecordCsvMap.put("port_name", portObject.get("portName"));
				portRecordCsvMap.put("descript", portObject.get("reason"));//端子错误描述
				portRecordCsvMap.put("isCheckOK", portObject.get("isCheckOK"));//端子是否合格
				portRecordCsvMap.put("portRightPosition", portObject.get("truePortNo"));//端子编码正确位置
				portRecordCsvMap.put("portIdRightPosition", portObject.get("truePortId"));//端子ID正确位置
				portRecordCsvMap.put("eqpNo_rightPortNo", portObject.get("rightEqpNo"));//设备编码正确位置
				portRecordCsvMap.put("eqpId_rightPortNo", portObject.get("rightEqpId"));//设备ID正确位置
				portRecordCsvMap.put("record_type", "0");
				portRecordCsvMap.put("area_id", areaId);
				portRecordCsvMap.put("son_area_id", sonAreaId);
				portRecordCsvMap.put("changedPortId",  portObject.get("portId_new"));//修改后端子ID
				portRecordCsvMap.put("changedPortNo",  portObject.get("localPortNo"));//修改后端子编码
				portRecordCsvMap.put("changedEqpId",  portObject.get("sbid_new"));//修改后设备ID
				portRecordCsvMap.put("changedEqpNo", portObject.get("sbbm_new"));//修改后设备编码
				portRecordCsvMap.put("checkWay", checkWay);
				//插入记录表
				checkOrderDao.insertEqpRecordNewYy(portRecordCsvMap);
				
				// 保存端子照片关系
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", newTaskId);
				photoMap.put("DETAIL_ID", detail_id_csv);
				photoMap.put("OBJECT_ID", recordId_csv);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", 0);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId_csv);
				if (!"".equals(portObject.getString("photoIds"))) {
					String[] photos = portObject.getString("photoIds").split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}
			}
			//插入设备详情
			int eqpRecordId = checkOrderDao.getRecordId();
			Map eqpDetailMap = new HashMap();
			eqpDetailMap.put("TASK_ID", newTaskId);
			eqpDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
			eqpDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			eqpDetailMap.put("INSPECT_OBJECT_TYPE", 0);
			eqpDetailMap.put("CHECK_FLAG", "1");
			eqpDetailMap.put("GLBM", "");
			eqpDetailMap.put("GLMC", "");
			eqpDetailMap.put("PORT_ID", "");
			eqpDetailMap.put("dtsj_id", "");
			eqpDetailMap.put("eqpId_port", "");
			eqpDetailMap.put("eqpNo_port", "");
			eqpDetailMap.put("eqpName_port", "");
			eqpDetailMap.put("orderNo", "");
			eqpDetailMap.put("orderMark", "");
			eqpDetailMap.put("actionType", "");
			eqpDetailMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(eqpDetailMap);
			String eqp_detail_id = eqpDetailMap.get("DETAIL_ID").toString();
			//插入设备记录
			Map eqpRecordMapNew = new HashMap();
			eqpRecordMapNew.put("recordId", eqpRecordId);	
			eqpRecordMapNew.put("task_id", newTaskId);
			eqpRecordMapNew.put("glbm", "");
			eqpRecordMapNew.put("detail_id", eqp_detail_id);
			eqpRecordMapNew.put("eqpId", eqpId);
			eqpRecordMapNew.put("eqpNo", eqpNo);
			eqpRecordMapNew.put("eqpName", eqpName);
			eqpRecordMapNew.put("eqpAddress", eqpAddress);
			eqpRecordMapNew.put("longitude", longitude);
			eqpRecordMapNew.put("latitude", latitude);
			eqpRecordMapNew.put("staffId", staffId);
			eqpRecordMapNew.put("remarks", remarks);//现场规范
			eqpRecordMapNew.put("port_id", "");
			eqpRecordMapNew.put("port_no", "");
			eqpRecordMapNew.put("action_type", "");
			eqpRecordMapNew.put("orderId", "");
			eqpRecordMapNew.put("orderNo", "");
			eqpRecordMapNew.put("isFrom", "");
			eqpRecordMapNew.put("port_name", "");
			eqpRecordMapNew.put("descript", "");//端子错误描述
			eqpRecordMapNew.put("isCheckOK", "1");//端子是否合格
			eqpRecordMapNew.put("portRightPosition", "");//端子编码正确位置
			eqpRecordMapNew.put("portIdRightPosition", "");//端子ID正确位置
			eqpRecordMapNew.put("eqpNo_rightPortNo", "");//设备编码正确位置
			eqpRecordMapNew.put("eqpId_rightPortNo", "");//设备ID正确位置
			eqpRecordMapNew.put("record_type", "0");
			eqpRecordMapNew.put("area_id", areaId);
			eqpRecordMapNew.put("son_area_id", sonAreaId);
			eqpRecordMapNew.put("changedPortId", "");//修改后端子ID
			eqpRecordMapNew.put("changedPortNo", "");//修改后端子编码
			eqpRecordMapNew.put("changedEqpId", "");//修改后设备ID
			eqpRecordMapNew.put("changedEqpNo", "");//修改后设备编码
			eqpRecordMapNew.put("checkWay", checkWay);
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(eqpRecordMapNew);
			//整改流程在插入记录表之前，先通过taskid找到老的流程，然后将其插入新的流程表
			List<Map<String, String>> processlList=checkOrderDao.queryProcessByOldTaskId(taskId);
			if(processlList !=null && processlList.size()>0){				
				for(Map<String, String> pro:processlList){
					pro.put("TASK_ID", newTaskId);
					checkProcessDao.addProcessNew_orderNo(pro);
				}
			}
			//插入流程环节
			Map processMap = new HashMap();
			processMap.put("task_id", newTaskId);
			processMap.put("oper_staff", staffId);
			processMap.put("status", "6");
			processMap.put("remark", "检查提交");
			processMap.put("content", "生成整改工单并自动派发至维护员");
			processMap.put("receiver", maintor1);
			checkProcessDao.addProcessNew(processMap);
		
			/**
			 * 保存设备的照片信息
			 */
			if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", newTaskId);
				photoMap.put("DETAIL_ID", eqp_detail_id);
				photoMap.put("OBJECT_ID", eqpRecordId);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", "0");// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", eqpRecordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			
			}
		}
	}
	
	//隐患工单推送至集约化
	public Map<String,String> autoPushDangerTask(Map map){	
		String result="";
		String sysRoute=map.get("sysRoute")==null?"GWZS":map.get("sysRoute").toString();
		String taskTypes=map.get("taskTypes")==null?"6":map.get("taskTypes").toString();
		String staffName=map.get("staffName")==null?"":map.get("staffName").toString();//检查人姓名
		String taskid=map.get("taskid")==null?"":map.get("taskid").toString();//任务ID
		String TaskName=map.get("TaskName")==null?"":map.get("TaskName").toString();//任务名称
		String IOMid_numberList=map.get("IOMid_numberList")==null?"":map.get("IOMid_numberList").toString();//施工人员账号
		String contract_persion_nos=map.get("contract_persion_nos")==null?"":map.get("contract_persion_nos").toString();//承保人员账号
		String PositionPersonsList=map.get("PositionPersonsList")==null?"":map.get("PositionPersonsList").toString();//兜底人账号		
		String eqpNo=map.get("eqpNo")==null?"":map.get("eqpNo").toString();
		String eqpName=map.get("eqpName")==null?"":map.get("eqpName").toString();
		String equType=map.get("equType")==null?"":map.get("equType").toString();
		String IOMportIdList=map.get("IOMportIdList")==null?"":map.get("IOMportIdList").toString();
		String descript=map.get("descript")==null?"":map.get("descript").toString();
		String taskCatgory=map.get("taskCatgory")==null?"":map.get("taskCatgory").toString();//IOM工单：IOM ; 线路工单：LINE ; 三级检查隐患工单：CHECK
		String edw_task_id=map.get("edw_task_id")==null?"":map.get("edw_task_id").toString();
		String isEquPro=map.get("isEquPro")==null?"1":map.get("isEquPro").toString();//是否设备问题
		String qualityInfo=map.get("qualityInfo")==null?"":map.get("qualityInfo").toString();//质量规范
		String eqpPhotoUrl=map.get("eqpPhotoUrl")==null?"":map.get("eqpPhotoUrl").toString();
		String errorPortsArray=map.get("errorPortsArray")==null?"":map.get("errorPortsArray").toString();
		
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
				+ "<IfInfo><sysRoute>"
				+ sysRoute
				+ "</sysRoute>"
				+ "<taskType>"
				+ taskTypes
				+ "</taskType>"
				+ "<gwMan>"
				+ staffName
				+ "</gwMan>"
				+ "<taskId>"
				+ taskid
				+ "</taskId>"
				+ "<TaskName>"
				+ TaskName
				+ "</TaskName>"
				+ "<taskCatgory>"
				+ taskCatgory
				+ "</taskCatgory>"
				+ "<edw_task_id>"
				+ edw_task_id
				+ "</edw_task_id>"
				+ "<gwManAccount>"
				+ IOMid_numberList
				+ "</gwManAccount>"
				+ "<equCbAccount>"
				+ contract_persion_nos
				+ "</equCbAccount>"
				+ "<gwPositionPersons>"
				+ PositionPersonsList
				+ "</gwPositionPersons>"
				+ "<equCode>"
				+ eqpNo
				+ "</equCode>"
				+ "<equName>"
				+ eqpName
				+ "</equName>"
				+ "<equType>"
				+ equType
				+ "</equType>"
				+ "<errorPortList>"
				+ IOMportIdList
				+ "</errorPortList>"
				+ "<gwContent>"
				+ descript
				+ "</gwContent>"
				+ "<errorPortsArray>"
				+ errorPortsArray
				+ "</errorPortsArray>"
				+ "<eqpPhotoUrl>"
				+ eqpPhotoUrl
				+ "</eqpPhotoUrl>"
				+ "<isEquPro>"
				+ isEquPro
				+ "</isEquPro>"
				+ "<qualityInfo>"
				+ qualityInfo
				+ "</qualityInfo>"
				+ "</IfInfo>";
		WfworkitemflowLocator locator = new WfworkitemflowLocator();
		// 0是成功 1是失败
		Map<String,String> IfResultMap = new HashMap<String,String>();
		try {
			WfworkitemflowSoap11BindingStub stub = (WfworkitemflowSoap11BindingStub) locator
					.getWfworkitemflowHttpSoap11Endpoint();
			result = stub.outSysDispatchTask(xml);
			Document doc = null;
			doc = DocumentHelper.parseText(result);
			// 将字符串转为XML
			Element rootElt = doc.getRootElement();
			Element IfResult = rootElt.element("IfResult");
			String IfResultinfo = IfResult.getText();//0表示推送功，1表示失败
			Element dealInfo = rootElt.element("IfResultInfo").element("dealInfo");
			String errorReason=dealInfo.getText();
			
			
			IfResultMap.put("Result_Status",IfResultinfo);
			IfResultMap.put("task_id",taskid);
			IfResultMap.put("errorReason",errorReason);
			if(org.apache.commons.lang.StringUtils.isNotBlank(taskid)){
				checkOrderDao.updateResultTask(IfResultMap);	
			}
			System.out.println(xml);
			System.out.println(result);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return IfResultMap;
	}
	
	//通过不预告方式检查提交
	@Override
	public String submitWorkOrder(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 传入的参数
			 */
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");//检查人
			String taskId = json.getString("taskId");
			String areaId = json.getString("areaId");
			String eqpId = json.getString("eqpId");//设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");//设备名称
			String eqpAddress =json.getString("eqpAddress");
			String eqpPhotoIds = json.getString("photoId");//设备照片
			String remarks = json.getString("remarks");//现场规范
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String is_bill = json.getString("is_bill");//是否需要整改
			String allcount = json.getString("allcount");// 全部端子数		
			String truecount = json.getString("truecount");// 正确端子数	
			String checkWay = json.getString("checkWay");// 检查方式 1：工单检查  2：设备端子检查
			//String oneback = json.getString("oneback");// 0:一键反馈   1 ：不预告
			String oneback="1";
			if(json.containsKey("oneback")){
				 oneback = json.getString("oneback");// 0:一键反馈   1 ：不预告
			}
			// 覆盖地址检查数量
			JSONArray addressArray = null;
			int addressCheckCnt = 0;
			if (json.containsKey("allEqpAddress")) {
				addressArray = json.getJSONArray("allEqpAddress");
				addressCheckCnt = addressArray.size();
			}			
			/**
			 * 端子信息
			 */
			JSONArray innerArray =json.getJSONArray("port");
			//先查询出设备所属网格，设备类型，区域
			 Map param=new HashMap();
			 param.put("areaId", areaId);
			 param.put("eqpId", eqpId);
			 param.put("eqpNo", eqpNo);
			 Map eqpMap=checkOrderDao.getEqpType_Grid(param);
			 String eqpTypeId=eqpMap.get("RES_TYPE_ID").toString();//设备类型
			 String grid= null==eqpMap.get("GRID_ID")?"":eqpMap.get("GRID_ID").toString();//设备所属网格
			 String sonAreaId=eqpMap.get("AREA_ID").toString();//设备所属区域
			 
			 if("2530".equals(eqpTypeId)){
				 //通过分光器查询箱子信息
				eqpMap=checkOrderDao.getOuterEqpInfo(param);
				if(eqpMap!=null&&eqpMap.size()>0){
					eqpId=null==eqpMap.get("EQUIPMENT_ID")?"":eqpMap.get("EQUIPMENT_ID").toString();
					eqpNo=null==eqpMap.get("EQUIPMENT_CODE")?"":eqpMap.get("EQUIPMENT_CODE").toString();
					eqpName=null==eqpMap.get("EQUIPMENT_NAME")?"":eqpMap.get("EQUIPMENT_NAME").toString();
					eqpAddress=null==eqpMap.get("ADDRESS")?"":eqpMap.get("ADDRESS").toString();
					eqpTypeId=null==eqpMap.get("RES_TYPE_ID")?"":eqpMap.get("RES_TYPE_ID").toString();
					grid=null==eqpMap.get("GRID_ID")?"":eqpMap.get("GRID_ID").toString();
				}
			 }
			
			 String douDiGangNo="";//兜底岗账号
			 String contractPersonNo="";//承包人账号
			 if("1".equals(is_bill)){
				 if(!"".equals(grid)){
					 List<Map<String,String>>  parList=checkOrderDao.queryDouDiGang(grid);				
					 for(int i=0;i<parList.size();i++){
						 if(douDiGangNo.length()>0){
							 douDiGangNo=douDiGangNo+","+parList.get(i).get("STAFF_NO");
						 }else{
							 douDiGangNo=parList.get(i).get("STAFF_NO");
						 }
					 }
				 }else{
					 douDiGangNo="";
				 }
				 Map<String,String>  contractPersonMap=checkOrderDao.getEqpContractNo(param);				
				 if(contractPersonMap!=null && contractPersonMap.size()>0){
					 contractPersonNo=contractPersonMap.get("CONTRACT_PERSION_NO");
				 }
				
			 }
			 //检查人员姓名、账号
			 Map maps=checkOrderDao.queryByStaffId(staffId);
			 String staffName=maps.get("STAFF_NAME").toString();
			 String staffNo=maps.get("STAFF_NO").toString();
			 
			 String equType="";
			 if("703".equals(eqpTypeId)){
				 equType="1";
			 }else if("411".equals(eqpTypeId)){
				 equType="2";
			 }else if("704".equals(eqpTypeId)){
				 equType="3";
			 }else{//综合配线箱
				 equType="4";
			 }
			 //错误端子列表 、错误端子描述
			 String errorPortIds="";
			 String errorPortDescript="";
			 String check_remark="";//现场规范
			 
			 //检查端子数量 、检查端子描述
			 int checkedPortIds=0;
			 String checkedPortDescript="";
			 int adressCheckCnt=0;//覆盖地址
			 
			Map orderParam = new HashMap();
			JSONArray orderArray=new JSONArray();
			JSONArray csv_orderArray=new JSONArray();//综调
			JSONArray iom_orderArray=new JSONArray();//IOM
			JSONArray no_csv_iom_orderArray=new JSONArray();//两者都未匹配上
			JSONObject orderObject=new JSONObject();
			Map<String, String> orderResult=new HashMap<String, String>();
			Map taskDetailMap = new HashMap();
			Map recordMap=new HashMap();
			
			//不预告抽查提交无需整改
			if("0".equals(is_bill)){
				//首先生成任务
				String maintor="";
				String auditor="";
				taskId = generateNewTask(staffId, areaId, eqpId, eqpNo,
						eqpName, remarks, is_bill, checkWay, oneback, sonAreaId,maintor,auditor);	
				
				//任务归档后遍历端子信息插入相关表
				for(int i=0;i<innerArray.size();i++){
					JSONObject port = (JSONObject)innerArray.get(i);
					String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
					String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
					String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
					String portId = port.getString("portId");
					String portNo = port.getString("portNo");
					String portName = port.getString("portName");
					String orderId = port.getString("order_id");//工单ID
					String orderNo = port.getString("order_no");//工单编码
					String photoIds = port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					//错误端子及其描述
					if("1".equals(isCheckOK)){
						if(errorPortIds.length()>0){
							errorPortIds=errorPortIds+","+portId;
							errorPortDescript=errorPortDescript+","+reason;
						}else{
							errorPortIds=portId;
							errorPortDescript=reason;
						}
					}
					
					String actionType=port.getString("actionType");//工单性质
					String truePortId =  port.getString("truePortId");//正确端子
					String truePortNo = port.getString("truePortNo");//正确编码
					String rightEqpId = port.getString("rightEqpId");//正确设备ID
					String rightEqpNo = port.getString("rightEqpNo");//正确设备编码
					if("查询".equals(truePortNo)){
						truePortId="";
						truePortNo="";
						rightEqpId="";
						rightEqpNo="";
					}
					
					String changedPortId=port.getString("portId_new");//修改后的端子id
					String changedPortNo=port.getString("localPortNo");//修改后的端子编码
					String changedEqpId=port.getString("sbid_new");//修改后的设备ID
					String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
					String glbm = null==port.get("glbm")?"":port.getString("glbm");
					String glmc = null==port.get("glmc")?"":port.getString("glmc");
					String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
					String type = null;
					String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
					if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
						type = "1";//装维
						
					}else{
						type = "0";//综维
					}
					//通过设备id和端子id去找端子对应的工单
					String orderMark="";
					String order_team_id="";
					String order_staff_id="";
					String other_system_staff_id="";
					orderParam.put("eqpId", eqpId_port);
					orderParam.put("portId", portId);					
					if("".equals(orderNo)){//只要工单号为空，则通过设备ID和端子去查询端子对应的工单信息（最近一次）
						orderResult=checkOrderDao.getOrderInfoNew(orderParam);
						//orderResult=checkOrderDao.getOrderInfo(orderParam);
					}else{
						orderResult=checkOrderDao.getOrderOfBanZuNew(orderNo);//通过工单去查询工单所在班组，及施工人
						//orderResult=checkOrderDao.getOrderOfBanZu(orderNo);
					}					
					if(orderResult != null && orderResult.size()>0){
						orderId=orderResult.get("ORDER_ID");
						orderNo=orderResult.get("ORDER_NO");
					    orderMark=orderResult.get("MARK");//判断是综调的还是IOM的
					    order_team_id=orderResult.get("ORDER_TEAM_ID");//工单所在班组
					   // order_staff_id=orderResult.get("STAFF_ID");//施工人账号id
					   // other_system_staff_id=orderResult.get("OTHER_SYSTEM_STAFF_ID");//外系统人员账号ID
					   //通过other_system_staff_id查询人员账号ID
					    /*if(other_system_staff_id!=null && !"".equals(other_system_staff_id)){
					    	Map staffMap=checkOrderDao.getStaffIdByOther(other_system_staff_id);
					    	if(staffMap!=null&&staffMap.size()>0){
					    		order_staff_id=staffMap.get("STAFF_ID").toString();
					    	}
					    }*/
					}					
					//将端子记录插入任务详情表
					int recordId = checkOrderDao.getRecordId();
					taskDetailMap.put("TASK_ID", taskId);
					taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
					taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
					taskDetailMap.put("GLBM", glbm);
					taskDetailMap.put("GLMC", glmc);
					taskDetailMap.put("PORT_ID", portId);
					taskDetailMap.put("dtsj_id", "");
					taskDetailMap.put("eqpId_port", "");
					taskDetailMap.put("eqpNo_port", "");
					taskDetailMap.put("eqpName_port", "");
					taskDetailMap.put("orderNo", "");
					taskDetailMap.put("orderMark", "");
					taskDetailMap.put("actionType", "");
					taskDetailMap.put("archive_time", "");
					checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
					//将端子记录插入记录表
					String detail_id = taskDetailMap.get("DETAIL_ID").toString();
					
					recordMap.put("recordId", recordId);	
					recordMap.put("task_id", taskId);
					recordMap.put("glbm", glbm);
					recordMap.put("detail_id", detail_id);
					recordMap.put("eqpId", eqpId_port);
					recordMap.put("eqpNo", eqpNo_port);
					recordMap.put("eqpName", eqpName_port);
					recordMap.put("eqpAddress", eqpAddress);
					recordMap.put("longitude", longitude);
					recordMap.put("latitude", latitude);
					recordMap.put("staffId", staffId);
					recordMap.put("remarks", remarks);//现场规范
					recordMap.put("port_id", portId);
					recordMap.put("port_no", portNo);
					recordMap.put("action_type", actionType);
					recordMap.put("orderId", orderId);
					recordMap.put("orderNo", orderNo);
					if("1".equals(orderMark)){
						recordMap.put("isFrom", "0");//1表示综调 2 表示 iom
					}else if("2".equals(orderMark)){
						recordMap.put("isFrom", "1");
					}else{
						recordMap.put("isFrom", "");
					}
					recordMap.put("port_name", portName);
					recordMap.put("descript", reason);//端子错误描述
					recordMap.put("isCheckOK", isCheckOK);//端子是否合格
					recordMap.put("truePortNo", truePortNo);//端子编码正确位置
					recordMap.put("truePortId", truePortId);//端子ID正确位置
					recordMap.put("rightEqpNo", rightEqpNo);//设备编码正确位置
					recordMap.put("rightEqpId", rightEqpId);//设备ID正确位置
					recordMap.put("record_type", "1");
					recordMap.put("area_id", areaId);
					recordMap.put("son_area_id", sonAreaId);
					recordMap.put("changedPortId", changedPortId);//修改后端子ID
					recordMap.put("changedPortNo", changedPortNo);//修改后端子编码
					recordMap.put("changedEqpId", changedEqpId);//修改后设备ID
					recordMap.put("changedEqpNo", changedEqpNo);//修改后设备编码
					recordMap.put("checkWay", checkWay);//检查方式
					//插入记录表
					checkOrderDao.insertEqpRecordNewYy(recordMap);
					
					// 保存端子照片关系
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", taskId);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "不预告抽查");
					photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID", recordId);
					if (!"".equals(photoIds)) {
						String[] photos = photoIds.split(",");
						for (String photo : photos) {
							photoMap.put("PHOTO_ID", photo);
							checkOrderDao.insertPhotoRel(photoMap);
						}
					}
					//将修改后的端子插入流程表 tb_cablecheck_process
					if("0".equals(isCheckOK)){
						if(!"".equals(changedPortNo)){
							String content="";
							if(eqpId_port.equals(changedEqpId)){
								content=glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
							}else{
								content =glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
							}
							recordMap.put("status", "66");//一键改
							recordMap.put("remark", "一键改");
							recordMap.put("receiver", "");
							recordMap.put("content", content);
							//checkTaskDao.addProcessNew(recordMap);
							checkProcessDao.addProcessNew_orderNo(recordMap);
						}
					}
					//工单检查时间
					if(!"".equals(orderNo)){
						recordMap.put("order_no", orderNo);
						checkTaskDao.updateOrderCheckTime(recordMap);
					}
					
				}
				//插入设备详情
				int recordId = checkOrderDao.getRecordId();
				Map taskDetailEqpMap = new HashMap();
				taskDetailMap.put("TASK_ID", taskId);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
				taskDetailMap.put("CHECK_FLAG", "1");
				taskDetailMap.put("GLBM", "");
				taskDetailMap.put("GLMC", "");
				taskDetailMap.put("PORT_ID", "");
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", "");
				taskDetailMap.put("eqpNo_port", "");
				taskDetailMap.put("eqpName_port", "");
				taskDetailMap.put("orderNo", "");
				taskDetailMap.put("orderMark", "");
				taskDetailMap.put("actionType", "");
				taskDetailMap.put("archive_time", "");
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				//插入设备记录
				Map eqpRecordMap = new HashMap();
				eqpRecordMap.put("recordId", recordId);	
				eqpRecordMap.put("task_id", taskId);
				eqpRecordMap.put("glbm", "");
				eqpRecordMap.put("detail_id", detail_id);
				eqpRecordMap.put("eqpId", eqpId);
				eqpRecordMap.put("eqpNo", eqpNo);
				eqpRecordMap.put("eqpName", eqpName);
				eqpRecordMap.put("eqpAddress", eqpAddress);
				eqpRecordMap.put("longitude", longitude);
				eqpRecordMap.put("latitude", latitude);
				eqpRecordMap.put("staffId", staffId);
				eqpRecordMap.put("remarks", remarks);//现场规范
				eqpRecordMap.put("port_id", "");
				eqpRecordMap.put("port_no", "");
				eqpRecordMap.put("action_type", "");
				eqpRecordMap.put("orderId", "");
				eqpRecordMap.put("orderNo", "");
				eqpRecordMap.put("isFrom", "");
				eqpRecordMap.put("port_name", "");
				eqpRecordMap.put("descript", "");//端子错误描述
				eqpRecordMap.put("isCheckOK",is_bill);//端子是否合格
				eqpRecordMap.put("truePortNo", "");//端子编码正确位置
				eqpRecordMap.put("truePortId", "");//端子ID正确位置
				eqpRecordMap.put("rightEqpNo", "");//设备编码正确位置
				eqpRecordMap.put("rightEqpId", "");//设备ID正确位置
				eqpRecordMap.put("record_type", "1");
				eqpRecordMap.put("area_id", areaId);
				eqpRecordMap.put("son_area_id", sonAreaId);
				eqpRecordMap.put("changedPortId", "");//修改后端子ID
				eqpRecordMap.put("changedPortNo", "");//修改后端子编码
				eqpRecordMap.put("changedEqpId", "");//修改后设备ID
				eqpRecordMap.put("changedEqpNo", "");//修改后设备编码
				eqpRecordMap.put("checkWay", checkWay);//检查方式
				//插入记录表
				checkOrderDao.insertEqpRecordNewYy(eqpRecordMap);
				//插入流程环节
				Map processMap = new HashMap();
				processMap.put("task_id", taskId);
				processMap.put("oper_staff", staffId);
				processMap.put("status", "8");
				processMap.put("remark", "检查提交");
				processMap.put("content", "端子检查合格或现场已整改，任务直接归档");
				processMap.put("receiver", "");
				checkProcessDao.addProcessNew(processMap);//无需整改直接归档
				/**
				 * 保存设备的照片信息
				 */
				if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
					Map photoMap = new HashMap();
					photoMap.put("TASK_ID", taskId);
					photoMap.put("DETAIL_ID", detail_id);
					photoMap.put("OBJECT_ID", recordId);
					photoMap.put("REMARKS", "不预告抽查");
					photoMap.put("OBJECT_TYPE", "0");// 0，周期性任务，1：隐患上报工单，2,回单操作
					photoMap.put("RECORD_ID", recordId);
					String[] photos = eqpPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}								
			}else{//不预告抽查上报整改
				for(int i=0;i<innerArray.size();i++){
					JSONObject port = (JSONObject)innerArray.get(i);
					String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
					String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
					String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
					String portId = port.getString("portId");
					String portNo = port.getString("portNo");
					String portName = port.getString("portName");
					String orderId = port.getString("order_id");//工单ID
					String orderNo = port.getString("order_no");//工单编码
					String photoIds = port.getString("photoId");
					String reason = port.getString("reason");
					String isCheckOK = port.getString("isCheckOK");
					//错误端子及其描述
					if("1".equals(isCheckOK)){
						if(errorPortIds.length()>0){
							errorPortIds=errorPortIds+","+portId;
							errorPortDescript=errorPortDescript+","+reason;
						}else{
							errorPortIds=portId;
							errorPortDescript=reason;
						}
					}
					
					String actionType = port.getString("actionType");//工单性质
					String truePortId = port.getString("truePortId");//正确端子
					String truePortNo = port.getString("truePortNo");//正确编码
					String rightEqpId = port.getString("rightEqpId");//正确设备ID
					String rightEqpNo = port.getString("rightEqpNo");//正确设备编码
					if("查询".equals(truePortNo)){
						truePortId="";
						truePortNo="";
						rightEqpId="";
						rightEqpNo="";
					}
					
					String changedPortId=port.getString("portId_new");//修改后的端子id
					String changedPortNo=port.getString("localPortNo");//修改后的端子编码
					String changedEqpId=port.getString("sbid_new");//修改后的设备ID
					String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
					String glbm = null==port.get("glbm")?"":port.getString("glbm");
					String glmc = null==port.get("glmc")?"":port.getString("glmc");
					String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
					String type = null;
					String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
					if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
						type = "1";//装维
						
					}else{
						type = "0";//综维
					}
					
					//通过设备id和端子id去找端子对应的工单
					String orderMark="";
					String order_team_id="";
					String order_staff_id="";
					String other_system_staff_id="";
					String auditor="";
					String maintor="";
					orderParam.put("eqpId", eqpId_port);
					orderParam.put("portId", portId);
					
					if("".equals(orderNo)){//只要工单号为空，则通过设备ID和端子去查询端子对应的工单信息（最近一次）
						orderResult=checkOrderDao.getOrderInfoNew(orderParam);
						//orderResult=checkOrderDao.getOrderInfo(orderParam);
					}else{
						orderResult=checkOrderDao.getOrderOfBanZuNew(orderNo);//通过工单去查询工单所在班组，及施工人
						//orderResult=checkOrderDao.getOrderOfBanZu(orderNo);//通过工单去查询工单所在班组，及施工人
					}
					//写一个公共方法，对以上查询到的结果进行分析处理
					if(orderResult!=null&&orderResult.size()>0){//能匹配到工单
						Map<String,String> info=this.dealWithOrder(orderResult);
					    orderId=info.get("orderId");
					    orderNo=info.get("orderNo");
					    orderMark=info.get("orderMark");
					    order_team_id=info.get("order_team_id");
					    order_staff_id=info.get("order_staff_id");//维护员账号ID或账号
					    maintor=info.get("maintor");//维护员账号ID或账号
					    auditor=info.get("auditor");//审核员账号ID
					}									
					/*if(orderResult != null && orderResult.size()>0){
						orderId=orderResult.get("ORDER_ID");
						orderNo=orderResult.get("ORDER_NO");
					    orderMark=orderResult.get("MARK");//判断是综调的还是IOM的
					    order_team_id=orderResult.get("ORDER_TEAM_ID");//工单所在班组
					    //order_staff_id=orderResult.get("STAFF_ID");//施工人账号id
					    other_system_staff_id=orderResult.get("OTHER_SYSTEM_STAFF_ID");//外系统人员账号ID
					    //通过other_system_staff_id查询人员账号ID
					    if(other_system_staff_id!=null && !"".equals(other_system_staff_id) && !"null".equals(other_system_staff_id)){
					    	Map<String,String> staffMap=checkOrderDao.getStaffIdByOther(other_system_staff_id);
					    	if(staffMap!=null&&staffMap.size()>0){
					    		order_staff_id=staffMap.get("STAFF_ID");
					    	}
					    }
					}*/
					orderObject.put("eqpId_port", eqpId_port);
					orderObject.put("eqpNo_port", eqpNo_port);
					orderObject.put("eqpName_port", eqpName_port);
					orderObject.put("eqpAddress", eqpAddress);
					orderObject.put("actionType", actionType);
					orderObject.put("portId", portId);
					orderObject.put("portNo", portNo);
					orderObject.put("portName", portName);
					orderObject.put("photoIds", photoIds);
					orderObject.put("reason", reason);
					orderObject.put("isCheckOK", isCheckOK);
					orderObject.put("glbm", glbm);
					orderObject.put("glmc", glmc);
					orderObject.put("isH", isH);
					orderObject.put("type", type);
					orderObject.put("orderId", orderId);
					orderObject.put("orderNo", orderNo);
					orderObject.put("orderMark", orderMark);
					orderObject.put("order_team_id", order_team_id);					
					orderObject.put("portRightPosition",truePortNo );//端子编码正确位置
					orderObject.put("portIdRightPosition", truePortId);//端子ID正确位置
					orderObject.put("eqpNo_rightPortNo", rightEqpNo);//设备编码正确位置
					orderObject.put("eqpId_rightPortNo", rightEqpId);//设备ID正确位置
					orderObject.put("record_type", "1");
					orderObject.put("area_id", areaId);
					orderObject.put("son_area_id", sonAreaId);
					orderObject.put("changedPortId", changedPortId);//修改后端子ID
					orderObject.put("changedPortNo", changedPortNo);//修改后端子编码
					orderObject.put("changedEqpId", changedEqpId);//修改后设备ID
					orderObject.put("changedEqpNo", changedEqpNo);
					
					if("0".equals(isCheckOK)){
						orderObject.put("maintor", "");
						orderObject.put("auditor", "");
					}else{
						orderObject.put("maintor", maintor);
						orderObject.put("auditor", auditor);
					}
					
					if(orderResult==null){
						no_csv_iom_orderArray.add(orderObject);
					}
					if("1".equals(orderMark)){
						csv_orderArray.add(orderObject);
					} 
					if("2".equals(orderMark)){
						iom_orderArray.add(orderObject);
					}
					//工单检查时间
					recordMap.put("staffId", staffId);
					if(!"".equals(orderNo)){
						recordMap.put("order_no", orderNo);
						checkTaskDao.updateOrderCheckTime(recordMap);
					}
				}
				
				//不预告--综调
				if(csv_orderArray!=null&&csv_orderArray.size()>0){
					createTaskCsv(staffId, taskId, areaId, eqpId, eqpNo,
							eqpName, eqpAddress, eqpPhotoIds, remarks, longitude,
							latitude, sonAreaId,checkWay,oneback, csv_orderArray);
				}
				
				//不预告--iom
				if(iom_orderArray!=null&&iom_orderArray.size()>0){
					 createTaskIom(staffId, taskId, areaId, eqpId, eqpNo, eqpName,
							eqpAddress, eqpPhotoIds, remarks, longitude, latitude, sonAreaId,
							douDiGangNo, contractPersonNo, staffName, equType, errorPortIds,
							errorPortDescript,checkWay,oneback, iom_orderArray);	
				}
			   		
				//不预告--既未匹配上综调 又未匹配上 iom 
				if(no_csv_iom_orderArray!=null&&no_csv_iom_orderArray.size()>0){
					createTaskNoCsvIom(staffId, taskId, areaId, eqpId,
							eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
							longitude, latitude, sonAreaId, douDiGangNo,
							contractPersonNo, staffName, equType, errorPortIds,
							errorPortDescript, checkWay,oneback, no_csv_iom_orderArray);		
				}
				
			}			
			Map portmap = new HashMap();
			portmap.put("areaId", areaId);
			portmap.put("sonAreaId", sonAreaId);
			portmap.put("allcount", allcount);
			portmap.put("truecount", truecount);
			portmap.put("eqpId", eqpId);
			portmap.put("eqpNo", eqpNo);
			checkOrderDao.updateEqu_time_num(portmap);
			
			for (int i = 0; i < addressArray.size(); i++) {
				int id = checkOrderDao.geteqpAddressId();
				JSONObject eqp = (JSONObject) addressArray.get(i);

				String eqpId_add = null == eqp.get("eqpId") ? ""
						: eqp.getString("eqpId");
				String eqpNo_add = null == eqp.get("eqpNo") ? ""
						: eqp.getString("eqpNo");
				String location_id = eqp.getString("locationId");
				String address_id = eqp.getString("addressId");
				String address_name = eqp.getString("addressName");
				String is_check_ok = eqp.getString("is_check_ok");
				String error_reason = null == eqp
						.get("error_reason") ? "" : eqp
						.getString("error_reason");

				Map adddressMap = new HashMap();
				adddressMap.put("id", id);
				adddressMap.put("phy_eqp_id", eqpId_add);
				adddressMap.put("phy_eqp_no", eqpNo_add);
				adddressMap.put("install_eqp_id", eqpId);
				adddressMap.put("location_id", location_id);
				adddressMap.put("address_id", address_id);
				adddressMap.put("address_name", address_name);
				adddressMap.put("is_check_ok", is_check_ok);
				adddressMap.put("error_reason", error_reason);
				adddressMap.put("task_id", taskId);
				adddressMap.put("create_staff", staffId);
				adddressMap.put("area_id", areaId);
				adddressMap.put("son_area_id", sonAreaId);
				checkOrderDao.insertEqpAddress(adddressMap);

			}
			//光网助手检查触发线路工单，先插入 tb_base_gwzs_lineworkorder（光网助手线路工单表），然后通过同步平台发到FTP目录下
			checkedPortIds=innerArray.size();
			checkedPortDescript=errorPortDescript;
			adressCheckCnt=addressArray.size();//覆盖地址数量
			Map lineWork=new HashMap();
			lineWork.put("gwMan", staffName);//光网助手检查人员
			lineWork.put("taskId", taskId);//任务ID
			lineWork.put("gwManAccount", staffNo);//检查人员账号
			lineWork.put("equCode", eqpNo);//设备编码
			lineWork.put("equName", eqpName);//设备名称
			lineWork.put("chekPortNum", checkedPortIds);//检查端子数量
			lineWork.put("adressCheckCnt", adressCheckCnt);//检查端子数量
			lineWork.put("gwContent", checkedPortDescript);//检查内容描述
			lineWork.put("workhours", "0");//检查内容描述
			checkOrderDao.insertLineWorkOrder(lineWork);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "工单提交失败");
		}
		return result.toString();
	}

	public void createTaskNoCsvIom(String staffId, String taskId,
			String areaId, String eqpId, String eqpNo, String eqpName,
			String eqpAddress, String eqpPhotoIds, String remarks,
			String longitude, String latitude, String sonAreaId,
			String douDiGangNo, String contractPersonNo, String staffName,
			String equType, String errorPortIds, String errorPortDescript,String checkWay,String oneback,
			JSONArray no_csv_iom_orderArray) {
		//生成整改任务
		Map troubleTaskMap = new HashMap();
		String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
		String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
		troubleTaskMap.put("TASK_NO",TASK_NO);
		troubleTaskMap.put("TASK_NAME",TASK_NAME);
		if("0".equals(oneback)){
			troubleTaskMap.put("TASK_TYPE", 1);// 问题上报一键反馈
		}else{
			troubleTaskMap.put("TASK_TYPE", 2);// 问题上报不预告
		}
	
		troubleTaskMap.put("STATUS_ID", 6);//待回单状态
		troubleTaskMap.put("INSPECTOR",staffId);
		troubleTaskMap.put("CREATE_STAFF",staffId);
		troubleTaskMap.put("SON_AREA_ID",sonAreaId);
		troubleTaskMap.put("AREA_ID",areaId);
		troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）														
		troubleTaskMap.put("REMARK",remarks);
		troubleTaskMap.put("INFO", "");
		troubleTaskMap.put("NO_EQPNO_FLAG","0");// 无编码上报
		troubleTaskMap.put("OLD_TASK_ID",taskId);// 老的task_id
		troubleTaskMap.put("IS_NEED_ZG","1");// 是否整改,1需要整改
		troubleTaskMap.put("SBID", eqpId);// 设备id
		troubleTaskMap.put("MAINTOR","");// 维护员
		troubleTaskMap.put("auditor","");// 审核员
		troubleTaskMap.put("checkWay",checkWay);// 审核员
		checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
		taskId = troubleTaskMap.get("TASK_ID").toString();
		logger.info("【需要整改添加一张新的工单taskId】"+ taskId);
		
		//遍历端子信息
		Map portRecordCsvMap=new HashMap();
		Map portDetailCsvMap=new HashMap();
		for(int m=0;m<no_csv_iom_orderArray.size();m++){
			JSONObject portObject= no_csv_iom_orderArray.getJSONObject(m);
			//将端子记录插入任务详情表
			int recordId_csv = checkOrderDao.getRecordId();
			portDetailCsvMap.put("TASK_ID", taskId);
			portDetailCsvMap.put("INSPECT_OBJECT_ID", recordId_csv);
			portDetailCsvMap.put("INSPECT_OBJECT_NO", portObject.get("portNo"));
			portDetailCsvMap.put("INSPECT_OBJECT_TYPE", 1);
			portDetailCsvMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
			portDetailCsvMap.put("GLBM", portObject.get("glbm"));
			portDetailCsvMap.put("GLMC", portObject.get("glmc"));
			portDetailCsvMap.put("PORT_ID", portObject.get("portId"));
			portDetailCsvMap.put("dtsj_id", "");
			portDetailCsvMap.put("eqpId_port", "");
			portDetailCsvMap.put("eqpNo_port", "");
			portDetailCsvMap.put("eqpName_port", "");
			portDetailCsvMap.put("orderNo", "");
			portDetailCsvMap.put("orderMark", "");
			portDetailCsvMap.put("actionType", "");
			portDetailCsvMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(portDetailCsvMap);
			//将端子记录插入记录表
			String detail_id_csv = portDetailCsvMap.get("DETAIL_ID").toString();						
			portRecordCsvMap.put("recordId", recordId_csv);	
			portRecordCsvMap.put("task_id", taskId);
			portRecordCsvMap.put("glbm", portObject.get("glbm"));
			portRecordCsvMap.put("detail_id", detail_id_csv);
			portRecordCsvMap.put("eqpId", portObject.get("eqpId_port"));
			portRecordCsvMap.put("eqpNo", portObject.get("eqpNo_port"));
			portRecordCsvMap.put("eqpName", portObject.get("eqpName_port"));
			portRecordCsvMap.put("eqpAddress", portObject.get("eqpAddress"));
			portRecordCsvMap.put("longitude", longitude);
			portRecordCsvMap.put("latitude", latitude);
			portRecordCsvMap.put("staffId", staffId);
			portRecordCsvMap.put("remarks", remarks);//现场规范
			portRecordCsvMap.put("port_id", portObject.get("portId"));
			portRecordCsvMap.put("port_no", portObject.get("portNo"));
			portRecordCsvMap.put("action_type", portObject.get("actionType"));
			portRecordCsvMap.put("orderId", portObject.get("orderId"));
			portRecordCsvMap.put("orderNo", portObject.get("orderNo"));
			portRecordCsvMap.put("isFrom", "");//0表示综调  1 表示 iom					
			portRecordCsvMap.put("port_name", portObject.get("portName"));
			portRecordCsvMap.put("descript", portObject.get("reason"));//端子错误描述
			portRecordCsvMap.put("isCheckOK", portObject.get("isCheckOK"));//端子是否合格
			portRecordCsvMap.put("truePortNo", portObject.get("portRightPosition"));//端子编码正确位置
			portRecordCsvMap.put("truePortId", portObject.get("portIdRightPosition"));//端子ID正确位置
			portRecordCsvMap.put("rightEqpNo", portObject.get("eqpNo_rightPortNo"));//设备编码正确位置
			portRecordCsvMap.put("rightEqpId", portObject.get("eqpId_rightPortNo"));//设备ID正确位置
			portRecordCsvMap.put("record_type", "1");
			portRecordCsvMap.put("area_id", areaId);
			portRecordCsvMap.put("son_area_id", sonAreaId);
			portRecordCsvMap.put("changedPortId",  portObject.get("changedPortId"));//修改后端子ID
			portRecordCsvMap.put("changedPortNo",  portObject.get("changedPortNo"));//修改后端子编码
			portRecordCsvMap.put("changedEqpId",  portObject.get("changedEqpId"));//修改后设备ID
			portRecordCsvMap.put("changedEqpNo", portObject.get("changedEqpNo"));//修改后设备编码
			portRecordCsvMap.put("checkWay", checkWay);
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(portRecordCsvMap);
			
			// 保存端子照片关系
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", taskId);
			photoMap.put("DETAIL_ID", detail_id_csv);
			photoMap.put("OBJECT_ID", recordId_csv);
			photoMap.put("REMARKS", "不预告检查");
			photoMap.put("OBJECT_TYPE", 0);// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", recordId_csv);
			if (!"".equals(portObject.getString("photoIds"))) {
				String[] photos = portObject.getString("photoIds").split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
		}
		//插入设备详情
		int eqpRecordId = checkOrderDao.getRecordId();
		Map eqpDetailMap = new HashMap();
		eqpDetailMap.put("TASK_ID", taskId);
		eqpDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
		eqpDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
		eqpDetailMap.put("INSPECT_OBJECT_TYPE", 0);
		eqpDetailMap.put("CHECK_FLAG", "1");
		eqpDetailMap.put("GLBM", "");
		eqpDetailMap.put("GLMC", "");
		eqpDetailMap.put("PORT_ID", "");
		eqpDetailMap.put("dtsj_id", "");
		eqpDetailMap.put("eqpId_port", "");
		eqpDetailMap.put("eqpNo_port", "");
		eqpDetailMap.put("eqpName_port", "");
		eqpDetailMap.put("orderNo", "");
		eqpDetailMap.put("orderMark", "");
		eqpDetailMap.put("actionType", "");
		eqpDetailMap.put("archive_time", "");
		checkOrderDao.saveTroubleTaskDetail(eqpDetailMap);
		String eqp_detail_id = eqpDetailMap.get("DETAIL_ID").toString();
		//插入设备记录
		Map eqpRecordMapNew = new HashMap();
		eqpRecordMapNew.put("recordId", eqpRecordId);	
		eqpRecordMapNew.put("task_id", taskId);
		eqpRecordMapNew.put("glbm", "");
		eqpRecordMapNew.put("detail_id", eqp_detail_id);
		eqpRecordMapNew.put("eqpId", eqpId);
		eqpRecordMapNew.put("eqpNo", eqpNo);
		eqpRecordMapNew.put("eqpName", eqpName);
		eqpRecordMapNew.put("eqpAddress", eqpAddress);
		eqpRecordMapNew.put("longitude", longitude);
		eqpRecordMapNew.put("latitude", latitude);
		eqpRecordMapNew.put("staffId", staffId);
		eqpRecordMapNew.put("remarks", remarks);//现场规范
		eqpRecordMapNew.put("port_id", "");
		eqpRecordMapNew.put("port_no", "");
		eqpRecordMapNew.put("action_type", "");
		eqpRecordMapNew.put("orderId", "");
		eqpRecordMapNew.put("orderNo", "");
		eqpRecordMapNew.put("isFrom", "");
		eqpRecordMapNew.put("port_name", "");
		eqpRecordMapNew.put("descript", "");//端子错误描述
		eqpRecordMapNew.put("isCheckOK", "1");//端子是否合格
		eqpRecordMapNew.put("truePortNo", "");//端子编码正确位置
		eqpRecordMapNew.put("truePortId", "");//端子ID正确位置
		eqpRecordMapNew.put("rightEqpNo", "");//设备编码正确位置
		eqpRecordMapNew.put("rightEqpId", "");//设备ID正确位置
		eqpRecordMapNew.put("record_type", "1");
		eqpRecordMapNew.put("area_id", areaId);
		eqpRecordMapNew.put("son_area_id", sonAreaId);
		eqpRecordMapNew.put("changedPortId", "");//修改后端子ID
		eqpRecordMapNew.put("changedPortNo", "");//修改后端子编码
		eqpRecordMapNew.put("changedEqpId", "");//修改后设备ID
		eqpRecordMapNew.put("changedEqpNo", "");//修改后设备编码
		eqpRecordMapNew.put("checkWay", checkWay);//修改后设备编码
		//插入记录表
		checkOrderDao.insertEqpRecordNewYy(eqpRecordMapNew);
		//插入流程环节
		Map processMap = new HashMap();
		processMap.put("task_id", taskId);
		processMap.put("oper_staff", staffId);
		processMap.put("status", "6");
		processMap.put("remark", "检查提交");
		processMap.put("content", "生成整改工单并自动推送至集约化");
		processMap.put("receiver", "");
		checkProcessDao.addProcessNew(processMap);

		/**
		 * 保存设备的照片信息
		 */
		if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", taskId);
			photoMap.put("DETAIL_ID", eqp_detail_id);
			photoMap.put("OBJECT_ID", eqpRecordId);
			photoMap.put("REMARKS", "不预告抽查");
			photoMap.put("OBJECT_TYPE", "1");// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", eqpRecordId);
			String[] photos = eqpPhotoIds.split(",");
			for (String photo : photos) {
				photoMap.put("PHOTO_ID", photo);
				checkOrderDao.insertPhotoRel(photoMap);
			}			
		}
		//String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
		//隐患工单自动推送至集约化
		Map iomMap=new HashMap();
		iomMap.put("sysRoute", "GWZS");
		iomMap.put("taskTypes", "6");
		iomMap.put("staffName", staffName);//检查人员姓名
		iomMap.put("taskid", taskId);//任务ID
		iomMap.put("TaskName", TASK_NAME);//任务ID
		iomMap.put("IOMid_numberList", "");//施工人员账号
		iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
		iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
		iomMap.put("eqpNo", eqpNo);
		iomMap.put("eqpName", eqpName);
		iomMap.put("equType", equType);
		iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
		iomMap.put("descript", errorPortDescript);//错误描述
		iomMap.put("taskCatgory", "LINE");
		autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
	}

	public void createTaskIom(String staffId, String taskId, String areaId,
			String eqpId, String eqpNo, String eqpName, String eqpAddress,
			String eqpPhotoIds, String remarks, String longitude,
			String latitude, String sonAreaId, String douDiGangNo,
			String contractPersonNo, String staffName, String equType,
			String errorPortIds, String errorPortDescript,String checkWay,String oneback,JSONArray iom_orderArray) {
			JSONObject iom_orderObject1=new JSONObject();
			JSONObject iom_orderObject2=new JSONObject();
			//整改任务中的端子记录数量
			int portNum=iom_orderArray.size();
			//公共的合格端子信息
			JSONArray rightPortArray=new JSONArray();
			JSONObject right_portOrderObject=new JSONObject();
			//先将需整改任务中合格端子抽离出来，
			for(int i =0 ;i<iom_orderArray.size();i++){
				right_portOrderObject=iom_orderArray.getJSONObject(i);
				String isCheckOk=right_portOrderObject.getString("isCheckOK");//端子是否合格
				if("0".equals(isCheckOk)){
					rightPortArray.add(right_portOrderObject);
					iom_orderArray.remove(i);//将抽离后的合格端子删除，这样就只保留不合格的端子记录
					i--;
				}
			}
			for(int i =0 ;i<iom_orderArray.size();i++){
				JSONArray sameMaintor=new JSONArray();//派发给同一个维护员，审核员暂时默认为一致，暂时不考虑
				iom_orderObject1=iom_orderArray.getJSONObject(i);
				String maintor1=iom_orderObject1.getString("maintor");
				String auditor=iom_orderObject1.getString("auditor");
				sameMaintor.add(iom_orderObject1);
				for(int j =i+1 ;j<iom_orderArray.size();j++){
					iom_orderObject2=iom_orderArray.getJSONObject(j);
					String maintor2=iom_orderObject2.getString("maintor");
					if(maintor1.equals(maintor2)){//同一个维护员
						sameMaintor.add(iom_orderObject2);
						iom_orderArray.remove(j);
						j--;
					}
				}
				if(sameMaintor!=null&&sameMaintor.size()>0){
					if(rightPortArray!=null&&rightPortArray.size()>0){
						for(int k=0;k<rightPortArray.size();k++){
							sameMaintor.add(rightPortArray.get(k));
						}
					}
					generateTask(staffId, taskId, areaId, eqpId,
							eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
							longitude, latitude, sonAreaId, douDiGangNo,
							contractPersonNo, staffName, equType, errorPortIds,
							errorPortDescript, checkWay, oneback,
							sameMaintor, maintor1);
				}
			}
			if(portNum==rightPortArray.size()){
				generateTask(staffId, taskId, areaId, eqpId,
						eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
						longitude, latitude, sonAreaId, douDiGangNo,
						contractPersonNo, staffName, equType, errorPortIds,
						errorPortDescript, checkWay, oneback,
						rightPortArray, "");
			}
	}

	public void generateTask(String staffId, String taskId, String areaId,
			String eqpId, String eqpNo, String eqpName, String eqpAddress,
			String eqpPhotoIds, String remarks, String longitude,
			String latitude, String sonAreaId, String douDiGangNo,
			String contractPersonNo, String staffName, String equType,
			String errorPortIds, String errorPortDescript, String checkWay,
			String oneback , JSONArray sameMaintor,
			String maintor1) {
		
		//生成整改任务
		Map troubleTaskMap = new HashMap();
		String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
		String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
		troubleTaskMap.put("TASK_NO",TASK_NO);
		troubleTaskMap.put("TASK_NAME",TASK_NAME);
		if("0".equals(oneback)){
			troubleTaskMap.put("TASK_TYPE", 1);// 问题上报一键反馈
		}else{
			troubleTaskMap.put("TASK_TYPE", 2);// 问题上报不预告
		}
		troubleTaskMap.put("STATUS_ID", 6);//待回单状态
		troubleTaskMap.put("INSPECTOR",staffId);
		troubleTaskMap.put("CREATE_STAFF",staffId);
		troubleTaskMap.put("SON_AREA_ID",sonAreaId);
		troubleTaskMap.put("AREA_ID",areaId);
		troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）														
		troubleTaskMap.put("REMARK",remarks);
		troubleTaskMap.put("INFO", "");
		troubleTaskMap.put("NO_EQPNO_FLAG","0");// 无编码上报
		troubleTaskMap.put("OLD_TASK_ID",taskId);// 老的task_id
		troubleTaskMap.put("IS_NEED_ZG","1");// 是否整改,1需要整改
		troubleTaskMap.put("SBID", eqpId);// 设备id
		troubleTaskMap.put("MAINTOR","");// 维护员
		troubleTaskMap.put("auditor","");// 审核员
		troubleTaskMap.put("checkWay",checkWay);// 审核员
		checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
		taskId = troubleTaskMap.get("TASK_ID").toString();
		logger.info("【需要整改添加一张新的工单taskId】"+ taskId);
		
		//遍历端子信息
		Map portRecordCsvMap=new HashMap();
		Map portDetailCsvMap=new HashMap();
		for(int m=0;m<sameMaintor.size();m++){
			JSONObject portObject= sameMaintor.getJSONObject(m);
			//将端子记录插入任务详情表
			int recordId_csv = checkOrderDao.getRecordId();
			portDetailCsvMap.put("TASK_ID", taskId);
			portDetailCsvMap.put("INSPECT_OBJECT_ID", recordId_csv);
			portDetailCsvMap.put("INSPECT_OBJECT_NO", portObject.get("portNo"));
			portDetailCsvMap.put("INSPECT_OBJECT_TYPE", 1);
			portDetailCsvMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
			portDetailCsvMap.put("GLBM", portObject.get("glbm"));
			portDetailCsvMap.put("GLMC", portObject.get("glmc"));
			portDetailCsvMap.put("PORT_ID", portObject.get("portId"));
			portDetailCsvMap.put("dtsj_id", "");
			portDetailCsvMap.put("eqpId_port", "");
			portDetailCsvMap.put("eqpNo_port", "");
			portDetailCsvMap.put("eqpName_port", "");
			portDetailCsvMap.put("orderNo", "");
			portDetailCsvMap.put("orderMark", "");
			portDetailCsvMap.put("actionType", "");
			portDetailCsvMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(portDetailCsvMap);
			//将端子记录插入记录表
			String detail_id_csv = portDetailCsvMap.get("DETAIL_ID").toString();						
			portRecordCsvMap.put("recordId", recordId_csv);	
			portRecordCsvMap.put("task_id", taskId);
			portRecordCsvMap.put("glbm", portObject.get("glbm"));
			portRecordCsvMap.put("detail_id", detail_id_csv);
			portRecordCsvMap.put("eqpId", portObject.get("eqpId_port"));
			portRecordCsvMap.put("eqpNo", portObject.get("eqpNo_port"));
			portRecordCsvMap.put("eqpName", portObject.get("eqpName_port"));
			portRecordCsvMap.put("eqpAddress", portObject.get("eqpAddress"));
			portRecordCsvMap.put("longitude", longitude);
			portRecordCsvMap.put("latitude", latitude);
			portRecordCsvMap.put("staffId", staffId);
			portRecordCsvMap.put("remarks", remarks);//现场规范
			portRecordCsvMap.put("port_id", portObject.get("portId"));
			portRecordCsvMap.put("port_no", portObject.get("portNo"));
			portRecordCsvMap.put("action_type", portObject.get("actionType"));
			portRecordCsvMap.put("orderId", portObject.get("orderId"));
			portRecordCsvMap.put("orderNo", portObject.get("orderNo"));
			portRecordCsvMap.put("isFrom", "1");//0表示综调  1 表示 iom					
			portRecordCsvMap.put("port_name", portObject.get("portName"));
			portRecordCsvMap.put("descript", portObject.get("reason"));//端子错误描述
			portRecordCsvMap.put("isCheckOK", portObject.get("isCheckOK"));//端子是否合格
			portRecordCsvMap.put("truePortNo", portObject.get("portRightPosition"));//端子编码正确位置
			portRecordCsvMap.put("truePortId", portObject.get("portIdRightPosition"));//端子ID正确位置
			portRecordCsvMap.put("rightEqpNo", portObject.get("eqpNo_rightPortNo"));//设备编码正确位置
			portRecordCsvMap.put("rightEqpId", portObject.get("eqpId_rightPortNo"));//设备ID正确位置
			portRecordCsvMap.put("record_type", "1");
			portRecordCsvMap.put("area_id", areaId);
			portRecordCsvMap.put("son_area_id", sonAreaId);
			portRecordCsvMap.put("changedPortId",  portObject.get("changedPortId"));//修改后端子ID
			portRecordCsvMap.put("changedPortNo",  portObject.get("changedPortNo"));//修改后端子编码
			portRecordCsvMap.put("changedEqpId",  portObject.get("changedEqpId"));//修改后设备ID
			portRecordCsvMap.put("changedEqpNo", portObject.get("changedEqpNo"));//修改后设备编码
			portRecordCsvMap.put("checkWay", checkWay);//检查方式
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(portRecordCsvMap);
			
			// 保存端子照片关系
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", taskId);
			photoMap.put("DETAIL_ID", detail_id_csv);
			photoMap.put("OBJECT_ID", recordId_csv);
			photoMap.put("REMARKS", "不预告检查");
			photoMap.put("OBJECT_TYPE", 0);// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", recordId_csv);
			if (!"".equals(portObject.getString("photoIds"))) {
				String[] photos = portObject.getString("photoIds").split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
		}
		//插入设备详情
		int eqpRecordId = checkOrderDao.getRecordId();
		Map eqpDetailMap = new HashMap();
		eqpDetailMap.put("TASK_ID", taskId);
		eqpDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
		eqpDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
		eqpDetailMap.put("INSPECT_OBJECT_TYPE", 0);
		eqpDetailMap.put("CHECK_FLAG", "1");
		eqpDetailMap.put("GLBM", "");
		eqpDetailMap.put("GLMC", "");
		eqpDetailMap.put("PORT_ID", "");
		eqpDetailMap.put("dtsj_id", "");
		eqpDetailMap.put("eqpId_port", "");
		eqpDetailMap.put("eqpNo_port", "");
		eqpDetailMap.put("eqpName_port", "");
		eqpDetailMap.put("orderNo", "");
		eqpDetailMap.put("orderMark", "");
		eqpDetailMap.put("actionType", "");
		eqpDetailMap.put("archive_time", "");
		checkOrderDao.saveTroubleTaskDetail(eqpDetailMap);
		String eqp_detail_id = eqpDetailMap.get("DETAIL_ID").toString();
		//插入设备记录
		Map eqpRecordMapNew = new HashMap();
		eqpRecordMapNew.put("recordId", eqpRecordId);	
		eqpRecordMapNew.put("task_id", taskId);
		eqpRecordMapNew.put("glbm", "");
		eqpRecordMapNew.put("detail_id", eqp_detail_id);
		eqpRecordMapNew.put("eqpId", eqpId);
		eqpRecordMapNew.put("eqpNo", eqpNo);
		eqpRecordMapNew.put("eqpName", eqpName);
		eqpRecordMapNew.put("eqpAddress", eqpAddress);
		eqpRecordMapNew.put("longitude", longitude);
		eqpRecordMapNew.put("latitude", latitude);
		eqpRecordMapNew.put("staffId", staffId);
		eqpRecordMapNew.put("remarks", remarks);//现场规范
		eqpRecordMapNew.put("port_id", "");
		eqpRecordMapNew.put("port_no", "");
		eqpRecordMapNew.put("action_type", "");
		eqpRecordMapNew.put("orderId", "");
		eqpRecordMapNew.put("orderNo", "");
		eqpRecordMapNew.put("isFrom", "");
		eqpRecordMapNew.put("port_name", "");
		eqpRecordMapNew.put("descript", "");//端子错误描述
		eqpRecordMapNew.put("isCheckOK", "1");//端子是否合格
		eqpRecordMapNew.put("truePortNo", "");//端子编码正确位置
		eqpRecordMapNew.put("truePortId", "");//端子ID正确位置
		eqpRecordMapNew.put("rightEqpNo", "");//设备编码正确位置
		eqpRecordMapNew.put("rightEqpId", "");//设备ID正确位置
		eqpRecordMapNew.put("record_type", "1");
		eqpRecordMapNew.put("area_id", areaId);
		eqpRecordMapNew.put("son_area_id", sonAreaId);
		eqpRecordMapNew.put("changedPortId", "");//修改后端子ID
		eqpRecordMapNew.put("changedPortNo", "");//修改后端子编码
		eqpRecordMapNew.put("changedEqpId", "");//修改后设备ID
		eqpRecordMapNew.put("changedEqpNo", "");//修改后设备编码
		eqpRecordMapNew.put("checkWay", checkWay);
		//插入记录表
		checkOrderDao.insertEqpRecordNewYy(eqpRecordMapNew);
		//插入流程环节
		Map processMap = new HashMap();
		processMap.put("task_id", taskId);
		processMap.put("oper_staff", staffId);
		processMap.put("status", "6");
		processMap.put("remark", "检查提交");
		processMap.put("content", "生成整改工单并自动推送至集约化");
		processMap.put("receiver", maintor1);
		checkProcessDao.addProcessNew(processMap);

		/**
		 * 保存设备的照片信息
		 */
		if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", taskId);
			photoMap.put("DETAIL_ID", eqp_detail_id);
			photoMap.put("OBJECT_ID", eqpRecordId);
			photoMap.put("REMARKS", "不预告抽查");
			photoMap.put("OBJECT_TYPE", "1");// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", eqpRecordId);
			String[] photos = eqpPhotoIds.split(",");
			for (String photo : photos) {
				photoMap.put("PHOTO_ID", photo);
				checkOrderDao.insertPhotoRel(photoMap);
			}			
		}
		//String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
		//隐患工单自动推送至集约化
		Map iomMap=new HashMap();
		iomMap.put("sysRoute", "GWZS");
		iomMap.put("taskTypes", "6");
		iomMap.put("staffName", staffName);//检查人员姓名
		iomMap.put("taskid", taskId);//任务ID
		iomMap.put("TaskName", TASK_NAME);//任务ID
		iomMap.put("IOMid_numberList", maintor1);//施工人员账号
		iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
		iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
		iomMap.put("eqpNo", eqpNo);
		iomMap.put("eqpName", eqpName);
		iomMap.put("equType", equType);
		iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
		iomMap.put("descript", errorPortDescript);//错误描述
		iomMap.put("taskCatgory", "IOM");
		autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
	}

	public void createTaskCsv(String staffId, String taskId, String areaId,
			String eqpId, String eqpNo, String eqpName, String eqpAddress,
			String eqpPhotoIds, String remarks, String longitude,
			String latitude, String sonAreaId, String checkWay,String oneback,JSONArray csv_orderArray) {
		JSONObject csv_orderObject1=new JSONObject();
		JSONObject csv_orderObject2=new JSONObject();
		//整改任务中的端子记录数量
		int portNum=csv_orderArray.size();
		//公共的合格端子信息
		JSONArray rightPortArray=new JSONArray();
		JSONObject right_portOrderObject=new JSONObject();
		//先将需整改任务中合格端子抽离出来，
		for(int i =0 ;i<csv_orderArray.size();i++){
			right_portOrderObject=csv_orderArray.getJSONObject(i);
			String isCheckOk=right_portOrderObject.getString("isCheckOK");//端子是否合格
			if("0".equals(isCheckOk)){
				rightPortArray.add(right_portOrderObject);
				csv_orderArray.remove(i);//将抽离后的合格端子删除，这样就只保留不合格的端子记录
				i--;
			}
		}
		for(int i =0 ;i<csv_orderArray.size();i++){//对不合格端子循环分组生成任务，同时将合格的端子记录插入
			JSONArray sameMaintor=new JSONArray();//派发给同一个维护员，审核员暂时默认为一致，暂时不考虑
			csv_orderObject1=csv_orderArray.getJSONObject(i);			
			String maintor1=csv_orderObject1.getString("maintor");
			String auditor=csv_orderObject1.getString("auditor");
			sameMaintor.add(csv_orderObject1);
			for(int j =i+1 ;j<csv_orderArray.size();j++){
				csv_orderObject2=csv_orderArray.getJSONObject(j);
				String maintor2=csv_orderObject2.getString("maintor");
				if(maintor1.equals(maintor2)){//同一个维护员
					sameMaintor.add(csv_orderObject2);
					csv_orderArray.remove(j);
					j--;
				}
			}
			if(sameMaintor!=null&&sameMaintor.size()>0){
				if(rightPortArray!=null&&rightPortArray.size()>0){
					for(int k=0;k<rightPortArray.size();k++){
						sameMaintor.add(rightPortArray.get(k));
					}
				}				
				produceTask(staffId, taskId, areaId, eqpId, eqpNo,
						eqpName, eqpAddress, eqpPhotoIds, remarks, longitude,
						latitude, sonAreaId, checkWay, oneback, sameMaintor,
						maintor1, auditor);	
			}			
		}
		if(portNum==rightPortArray.size()){
			produceTask(staffId, taskId, areaId, eqpId, eqpNo,
					eqpName, eqpAddress, eqpPhotoIds, remarks, longitude,
					latitude, sonAreaId, checkWay, oneback, rightPortArray,
					"", "");	
		}
	}
	
	public void produceTask(String staffId, String taskId, String areaId,
			String eqpId, String eqpNo, String eqpName, String eqpAddress,
			String eqpPhotoIds, String remarks, String longitude,
			String latitude, String sonAreaId, String checkWay, String oneback,
			JSONArray sameMaintor, String maintor1, String auditor) {
		//生成整改任务
		Map troubleTaskMap = new HashMap();
		String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
		String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
		troubleTaskMap.put("TASK_NO",TASK_NO);
		troubleTaskMap.put("TASK_NAME",TASK_NAME);
		if("0".equals(oneback)){
			troubleTaskMap.put("TASK_TYPE", 1);// 问题上报一键反馈
		}else{
			troubleTaskMap.put("TASK_TYPE", 2);// 问题上报不预告
		}
		troubleTaskMap.put("STATUS_ID", 6);//待回单状态
		troubleTaskMap.put("INSPECTOR",staffId);
		troubleTaskMap.put("CREATE_STAFF",staffId);
		troubleTaskMap.put("SON_AREA_ID",sonAreaId);
		troubleTaskMap.put("AREA_ID",areaId);
		troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）														
		troubleTaskMap.put("REMARK",remarks);
		troubleTaskMap.put("INFO", "");
		troubleTaskMap.put("NO_EQPNO_FLAG","0");// 无编码上报
		troubleTaskMap.put("OLD_TASK_ID",taskId);// 老的task_id
		troubleTaskMap.put("IS_NEED_ZG","1");// 是否整改,1需要整改
		troubleTaskMap.put("SBID", eqpId);// 设备id
		troubleTaskMap.put("MAINTOR",maintor1);// 维护员
		troubleTaskMap.put("auditor",auditor);// 审核员
		troubleTaskMap.put("checkWay",checkWay);//检查方式
		checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
		taskId = troubleTaskMap.get("TASK_ID").toString();
		logger.info("【需要整改添加一张新的工单taskId】"+ taskId);
		
		//遍历端子信息
		Map portRecordCsvMap=new HashMap();
		Map portDetailCsvMap=new HashMap();
		for(int m=0;m<sameMaintor.size();m++){
			JSONObject portObject= sameMaintor.getJSONObject(m);
			//将端子记录插入任务详情表
			int recordId_csv = checkOrderDao.getRecordId();
			portDetailCsvMap.put("TASK_ID", taskId);
			portDetailCsvMap.put("INSPECT_OBJECT_ID", recordId_csv);
			portDetailCsvMap.put("INSPECT_OBJECT_NO", portObject.get("portNo"));
			portDetailCsvMap.put("INSPECT_OBJECT_TYPE", 1);
			portDetailCsvMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
			portDetailCsvMap.put("GLBM", portObject.get("glbm"));
			portDetailCsvMap.put("GLMC", portObject.get("glmc"));
			portDetailCsvMap.put("PORT_ID", portObject.get("portId"));
			portDetailCsvMap.put("dtsj_id", "");
			portDetailCsvMap.put("eqpId_port", "");
			portDetailCsvMap.put("eqpNo_port", "");
			portDetailCsvMap.put("eqpName_port", "");
			portDetailCsvMap.put("orderNo", "");
			portDetailCsvMap.put("orderMark", "");
			portDetailCsvMap.put("actionType", "");
			portDetailCsvMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(portDetailCsvMap);
			//将端子记录插入记录表
			String detail_id_csv = portDetailCsvMap.get("DETAIL_ID").toString();						
			portRecordCsvMap.put("recordId", recordId_csv);	
			portRecordCsvMap.put("task_id", taskId);
			portRecordCsvMap.put("glbm", portObject.get("glbm"));
			portRecordCsvMap.put("detail_id", detail_id_csv);
			portRecordCsvMap.put("eqpId", portObject.get("eqpId_port"));
			portRecordCsvMap.put("eqpNo", portObject.get("eqpNo_port"));
			portRecordCsvMap.put("eqpName", portObject.get("eqpName_port"));
			portRecordCsvMap.put("eqpAddress", portObject.get("eqpAddress"));
			portRecordCsvMap.put("longitude", longitude);
			portRecordCsvMap.put("latitude", latitude);
			portRecordCsvMap.put("staffId", staffId);
			portRecordCsvMap.put("remarks", remarks);//现场规范
			portRecordCsvMap.put("port_id", portObject.get("portId"));
			portRecordCsvMap.put("port_no", portObject.get("portNo"));
			portRecordCsvMap.put("action_type", portObject.get("actionType"));
			portRecordCsvMap.put("orderId", portObject.get("orderId"));
			portRecordCsvMap.put("orderNo", portObject.get("orderNo"));
			portRecordCsvMap.put("isFrom", "0");//0表示综调  1 表示 iom					
			portRecordCsvMap.put("port_name", portObject.get("portName"));
			portRecordCsvMap.put("descript", portObject.get("reason"));//端子错误描述
			portRecordCsvMap.put("isCheckOK", portObject.get("isCheckOK"));//端子是否合格
			portRecordCsvMap.put("truePortNo", portObject.get("portRightPosition"));//端子编码正确位置
			portRecordCsvMap.put("truePortId", portObject.get("portIdRightPosition"));//端子ID正确位置
			portRecordCsvMap.put("rightEqpNo", portObject.get("eqpNo_rightPortNo"));//设备编码正确位置
			portRecordCsvMap.put("rightEqpId", portObject.get("eqpId_rightPortNo"));//设备ID正确位置
			portRecordCsvMap.put("record_type", "1");
			portRecordCsvMap.put("area_id", areaId);
			portRecordCsvMap.put("son_area_id", sonAreaId);
			portRecordCsvMap.put("changedPortId",  portObject.get("changedPortId"));//修改后端子ID
			portRecordCsvMap.put("changedPortNo",  portObject.get("changedPortNo"));//修改后端子编码
			portRecordCsvMap.put("changedEqpId",  portObject.get("changedEqpId"));//修改后设备ID
			portRecordCsvMap.put("changedEqpNo", portObject.get("changedEqpNo"));//修改后设备编码
			portRecordCsvMap.put("checkWay", checkWay);//检查方式
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(portRecordCsvMap);
			
			// 保存端子照片关系
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", taskId);
			photoMap.put("DETAIL_ID", detail_id_csv);
			photoMap.put("OBJECT_ID", recordId_csv);
			photoMap.put("REMARKS", "不预告检查");
			photoMap.put("OBJECT_TYPE", 0);// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", recordId_csv);
			if (!"".equals(portObject.getString("photoIds"))) {
				String[] photos = portObject.getString("photoIds").split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
		}
		//插入设备详情
		int eqpRecordId = checkOrderDao.getRecordId();
		Map eqpDetailMap = new HashMap();
		eqpDetailMap.put("TASK_ID", taskId);
		eqpDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
		eqpDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
		eqpDetailMap.put("INSPECT_OBJECT_TYPE", 0);
		eqpDetailMap.put("CHECK_FLAG", "1");
		eqpDetailMap.put("GLBM", "");
		eqpDetailMap.put("GLMC", "");
		eqpDetailMap.put("PORT_ID", "");
		eqpDetailMap.put("dtsj_id", "");
		eqpDetailMap.put("eqpId_port", "");
		eqpDetailMap.put("eqpNo_port", "");
		eqpDetailMap.put("eqpName_port", "");
		eqpDetailMap.put("orderNo", "");
		eqpDetailMap.put("orderMark", "");
		eqpDetailMap.put("actionType", "");
		eqpDetailMap.put("archive_time", "");
		checkOrderDao.saveTroubleTaskDetail(eqpDetailMap);
		String eqp_detail_id = eqpDetailMap.get("DETAIL_ID").toString();
		//插入设备记录
		Map eqpRecordMapNew = new HashMap();
		eqpRecordMapNew.put("recordId", eqpRecordId);	
		eqpRecordMapNew.put("task_id", taskId);
		eqpRecordMapNew.put("glbm", "");
		eqpRecordMapNew.put("detail_id", eqp_detail_id);
		eqpRecordMapNew.put("eqpId", eqpId);
		eqpRecordMapNew.put("eqpNo", eqpNo);
		eqpRecordMapNew.put("eqpName", eqpName);
		eqpRecordMapNew.put("eqpAddress", eqpAddress);
		eqpRecordMapNew.put("longitude", longitude);
		eqpRecordMapNew.put("latitude", latitude);
		eqpRecordMapNew.put("staffId", staffId);
		eqpRecordMapNew.put("remarks", remarks);//现场规范
		eqpRecordMapNew.put("port_id", "");
		eqpRecordMapNew.put("port_no", "");
		eqpRecordMapNew.put("action_type", "");
		eqpRecordMapNew.put("orderId", "");
		eqpRecordMapNew.put("orderNo", "");
		eqpRecordMapNew.put("isFrom", "");
		eqpRecordMapNew.put("port_name", "");
		eqpRecordMapNew.put("descript", "");//端子错误描述
		eqpRecordMapNew.put("isCheckOK", "1");//端子是否合格
		eqpRecordMapNew.put("truePortNo", "");//端子编码正确位置
		eqpRecordMapNew.put("truePortId", "");//端子ID正确位置
		eqpRecordMapNew.put("rightEqpNo", "");//设备编码正确位置
		eqpRecordMapNew.put("rightEqpId", "");//设备ID正确位置
		eqpRecordMapNew.put("record_type", "1");
		eqpRecordMapNew.put("area_id", areaId);
		eqpRecordMapNew.put("son_area_id", sonAreaId);
		eqpRecordMapNew.put("changedPortId", "");//修改后端子ID
		eqpRecordMapNew.put("changedPortNo", "");//修改后端子编码
		eqpRecordMapNew.put("changedEqpId", "");//修改后设备ID
		eqpRecordMapNew.put("changedEqpNo", "");//修改后设备编码
		eqpRecordMapNew.put("checkWay", checkWay);//检查方式
		//插入记录表
		checkOrderDao.insertEqpRecordNewYy(eqpRecordMapNew);
		//插入流程环节
		Map processMap = new HashMap();
		processMap.put("task_id", taskId);
		processMap.put("oper_staff", staffId);
		processMap.put("status", "6");
		processMap.put("remark", "检查提交");
		processMap.put("content", "生成整改工单并自动派发至维护员");
		processMap.put("receiver", maintor1);
		checkProcessDao.addProcessNew(processMap);

		/**
		 * 保存设备的照片信息
		 */
		if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", taskId);
			photoMap.put("DETAIL_ID", eqp_detail_id);
			photoMap.put("OBJECT_ID", eqpRecordId);
			photoMap.put("REMARKS", "不预告抽查");
			photoMap.put("OBJECT_TYPE", "1");// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", eqpRecordId);
			String[] photos = eqpPhotoIds.split(",");
			for (String photo : photos) {
				photoMap.put("PHOTO_ID", photo);
				checkOrderDao.insertPhotoRel(photoMap);
			}			
		}
	}
	
	public Map<String, String> dealWithOrder(Map<String,String> orderResult){
		String order_staff_id="";//施工人账号ID或施工人账号
		String maintor="";
		String auditor="";
		String orderId=orderResult.get("ORDER_ID")==null?"":orderResult.get("ORDER_ID");
		String orderNo=orderResult.get("ORDER_NO")==null?"":orderResult.get("ORDER_NO");
	    String orderMark=orderResult.get("MARK")==null?"":orderResult.get("MARK");//判断是综调的还是IOM的
	    String order_team_id=orderResult.get("ORDER_TEAM_ID")==null?"":orderResult.get("ORDER_TEAM_ID");//工单所在班组
	    String archive_time=orderResult.get("ARCHIVE_TIME")==null?"":orderResult.get("ARCHIVE_TIME");//工单竣工时间
	    String other_system_staff_id=orderResult.get("OTHER_SYSTEM_STAFF_ID")==null?"":orderResult.get("OTHER_SYSTEM_STAFF_ID");//外系统人员账号ID
	    Map param =new HashMap();
	    param.put("order_team_id", order_team_id);//工单所在班组
	    param.put("other_system_staff_id", other_system_staff_id);//施工人账号ID
	    if("1".equals(orderMark)){//综调
	    	//班组ID不为空
	    	if(!"".equals(order_team_id)){
	    		String team_id=order_team_id;
	    		Map receiverMap=checkTaskDao.getOrderReceiverOfBanZu(team_id);//班组接单人，审核人
	    		if(!"".equals(other_system_staff_id)){//施工人账号ID不为空
		    		Map<String,String> staffMap=checkOrderDao.getStaffIdByOther(other_system_staff_id);//维护员
		    		if(staffMap!=null&&staffMap.size()>0){
		    			order_staff_id=staffMap.get("STAFF_ID")==null?"":staffMap.get("STAFF_ID");
		    		}		    		
		    		if(receiverMap!=null&&receiverMap.size()>0){
		    			if(!"".equals(order_staff_id)){
							maintor=order_staff_id;
							auditor=receiverMap.get("STAFF_ID").toString();//审核员
						}else{
							maintor=receiverMap.get("STAFF_ID").toString();//维护人  施工人为空，维护员就是审核员
							auditor=receiverMap.get("STAFF_ID").toString();//审核员
						}
					}else{
						maintor=order_staff_id;
						auditor="";
					}
	    		}
	    		if("".equals(other_system_staff_id)){//施工人账号ID为空
	    			if(receiverMap!=null&&receiverMap.size()>0){
						maintor=receiverMap.get("STAFF_ID")==null?"":receiverMap.get("STAFF_ID").toString();//施工人为空，维护员就是审核员
						auditor=receiverMap.get("STAFF_ID")==null?"":receiverMap.get("STAFF_ID").toString();
					}else{
						maintor="";
						auditor="";
					}
	    		}
		    }
	    }else{//iom
	    	if(!"".equals(order_team_id)){
		    	Map<String,String> staffNo=checkOrderDao.getIomStaffNo(param);
		    	if(staffNo!=null&&staffNo.size()>0){
		    		order_staff_id=staffNo.get("STAFF_NBR");//iom施工人员账号
		    		maintor=order_staff_id;
					auditor="";
		    	}
		    }
	    }
	    //通过工单查询工单性质
	    String actionType="";
	    if(!"".equals(orderNo)){
	    	 Map<String,String> actionTypeMap=checkOrderDao.getOrderActionType(orderNo);
	    	 if(actionTypeMap!=null&&actionTypeMap.size()>0){
	    		 actionType=actionTypeMap.get("ACTION_TYPE");
	    	 }
	    	 
	    }
	   
	    Map infoMap=new HashMap();
	    infoMap.put("orderId", orderId);
	    infoMap.put("orderNo", orderNo);
	    infoMap.put("orderMark", orderMark);
	    infoMap.put("order_team_id", order_team_id);
	    infoMap.put("archive_time", archive_time);
	    infoMap.put("order_staff_id", order_staff_id);
	    infoMap.put("actionType", actionType);
	    infoMap.put("maintor", maintor);
	    infoMap.put("auditor", auditor);
		return infoMap;
		
	}

	@Override
	public String submitWorkOrder2(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 传入的参数
			 */
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");//检查人
			String taskId = json.getString("taskId");
			String areaId = json.getString("areaId");
			String eqpId = json.getString("eqpId");//设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");//设备名称
			String eqpAddress =json.getString("eqpAddress");
			String eqpPhotoIds = json.getString("photoId");//设备照片
			String remarks = json.getString("remarks");//现场规范
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String is_bill = json.getString("is_bill");//是否需要整改
			String allcount = json.getString("allcount");// 全部端子数		
			String truecount = json.getString("truecount");// 正确端子数	
			String checkWay = json.getString("checkWay");// 检查方式 1：工单检查  2：设备端子检查
			String oneback="1";
			if(json.containsKey("oneback")){
				 oneback = json.getString("oneback");// 0:一键反馈   1 ：不预告   默认不预告
			}
			// 覆盖地址检查数量
			JSONArray addressArray = null;
			int addressCheckCnt = 0;
			if (json.containsKey("allEqpAddress")) {
				addressArray = json.getJSONArray("allEqpAddress");
				addressCheckCnt = addressArray.size();
			}			
			/**
			 * 端子信息
			 */
			JSONArray innerArray =json.getJSONArray("port");
			
			//先查询出设备所属网格，设备类型，区域
			 Map param=new HashMap();
			 param.put("areaId", areaId);
			 param.put("eqpId", eqpId);
			 param.put("eqpNo", eqpNo);
			 Map eqpMap=checkOrderDao.getEqpType_Grid(param);
			 String eqpTypeId=eqpMap.get("RES_TYPE_ID").toString();//设备类型
			 String grid= null==eqpMap.get("GRID_ID")?"":eqpMap.get("GRID_ID").toString();//设备所属网格
			 String sonAreaId=eqpMap.get("AREA_ID").toString();//设备所属区域
			 if("2530".equals(eqpTypeId)){
				 //通过分光器查询箱子信息
				eqpMap=checkOrderDao.getOuterEqpInfo(param);
				if(eqpMap!=null&&eqpMap.size()>0){
					eqpId=null==eqpMap.get("EQUIPMENT_ID")?eqpId:eqpMap.get("EQUIPMENT_ID").toString();
					eqpNo=null==eqpMap.get("EQUIPMENT_CODE")?eqpNo:eqpMap.get("EQUIPMENT_CODE").toString();
					eqpName=null==eqpMap.get("EQUIPMENT_NAME")?eqpName:eqpMap.get("EQUIPMENT_NAME").toString();
					eqpAddress=null==eqpMap.get("ADDRESS")?eqpAddress:eqpMap.get("ADDRESS").toString();
					eqpTypeId=null==eqpMap.get("RES_TYPE_ID")?eqpTypeId:eqpMap.get("RES_TYPE_ID").toString();
					grid=null==eqpMap.get("GRID_ID")?grid:eqpMap.get("GRID_ID").toString();
				}
			 }
			
			 String douDiGangNo="";//兜底岗账号
			 String contractPersonNo="";//承包人账号
			 if("1".equals(is_bill)){
				 if(!"".equals(grid)){
					 List<Map<String,String>>  parList=checkOrderDao.queryDouDiGang(grid);				
					 for(int i=0;i<parList.size();i++){
						 if(douDiGangNo.length()>0){
							 douDiGangNo=douDiGangNo+","+parList.get(i).get("STAFF_NO");
						 }else{
							 douDiGangNo=parList.get(i).get("STAFF_NO");
						 }
					 }
				 }else{
					 douDiGangNo="";
				 }
				 Map<String,String>  contractPersonMap=checkOrderDao.getEqpContractNo(param);				
				 if(contractPersonMap!=null && contractPersonMap.size()>0){
					 contractPersonNo=contractPersonMap.get("CONTRACT_PERSION_NO");
				 }
			 }
			 //检查人员姓名、账号
			 Map maps=checkOrderDao.queryByStaffId(staffId);
			 String staffName=maps.get("STAFF_NAME").toString();
			 String staffNo=maps.get("STAFF_NO").toString();
			 
			 String equType="";
			 if("703".equals(eqpTypeId)){
				 equType="1";
			 }else if("411".equals(eqpTypeId)){
				 equType="2";
			 }else if("704".equals(eqpTypeId)){
				 equType="3";
			 }else{//综合配线箱
				 equType="4";
			 }
			 //错误端子列表 、错误端子描述
			 String errorPortIds="";
			 String errorPortDescript="";
			 String check_remark=remarks;//现场规范
			 
			 //检查端子数量 、检查端子描述
			 int checkedPortIds=0;
			 String checkedPortDescript="";
			 int adressCheckCnt=0;//覆盖地址
			 
			 //正确端子数量
			 int rightPortNum=0;
			 
			 Map orderParam = new HashMap();
			 JSONArray orderArray=new JSONArray();
			 JSONArray right_orderArray=new JSONArray();//正确端子
			 JSONArray csv_orderArray=new JSONArray();//综调
			 JSONArray iom_orderArray=new JSONArray();//IOM
			 JSONArray no_csv_iom_orderArray=new JSONArray();//两者都未匹配上
			 JSONObject orderObject=new JSONObject();
			 Map<String, String> orderResult=new HashMap<String, String>();
			 Map taskDetailMap = new HashMap();
			 Map recordMap=new HashMap();
			 JSONArray errorPortsArray=new JSONArray();
			 JSONObject errorPortObject=new JSONObject();
			 
			 //循环端子信息
			 for(int i=0;i<innerArray.size();i++){
				 JSONObject port = (JSONObject)innerArray.get(i);
				 String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
				 String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
				 String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
				 String portId = port.getString("portId");
				 String portNo = port.getString("portNo");
				 String portName = port.getString("portName");
				 String orderId = port.getString("order_id");//工单ID
				 String orderNo = port.getString("order_no");//工单编码
				 String photoIds = port.getString("photoId");
				 String reason = port.getString("reason");
				 String isCheckOK = port.getString("isCheckOK");
				 String actionType=port.getString("actionType");//工单性质
				 String truePortId =  port.getString("truePortId");//正确端子
				 String truePortNo = port.getString("truePortNo");//正确编码
				 String rightEqpId = port.getString("rightEqpId");//正确设备ID
				 String rightEqpNo = port.getString("rightEqpNo");//正确设备编码
				 String changedPortId=port.getString("portId_new");//修改后的端子id
				 String changedPortNo=port.getString("localPortNo");//修改后的端子编码
				 String changedEqpId=port.getString("sbid_new");//修改后的设备ID
				 String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
				 String glbm = null==port.get("glbm")?"":port.getString("glbm");
				 String glmc = null==port.get("glmc")?"":port.getString("glmc");
				 String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
				 String type = null;
				 String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
				 if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
					 type = "1";//装维
					
				 }else{
					 type = "0";//综维
				 }
				 if("查询".equals(truePortNo)){
					 truePortId="";
					 truePortNo="";
					 rightEqpId="";
					 rightEqpNo="";
				 }
				 //通过设备id和端子id去找端子对应的工单
				 String orderMark="";
				 String order_team_id="";
				 //String order_staff_id="";
				 //String other_system_staff_id="";
				 String archive_time="";//工单竣工时间
				 String auditor="";
				 String maintor="";
				 orderParam.put("eqpId", eqpId_port);
				 orderParam.put("portId", portId);					
				 if("".equals(orderNo)){//只要工单号为空，则通过设备ID和端子去查询端子对应的工单信息（最近一次）
					 orderResult=checkOrderDao.getOrderInfoNew(orderParam);
				 }else{
					 orderResult=checkOrderDao.getOrderOfBanZuNew(orderNo);//通过工单去查询工单所在班组，及施工人
				 }					
				 //写一个公共方法，对以上查询到的结果进行分析处理
				 if(orderResult!=null&&orderResult.size()>0){//能匹配到工单
					 Map<String,String> info=this.dealWithOrder(orderResult);
				     orderId=info.get("orderId");
				     orderNo=info.get("orderNo");
				     orderMark=info.get("orderMark");//1:综调; 2:IOM
				     archive_time=info.get("archive_time");//工单竣工时间
				     order_team_id=info.get("order_team_id");//工单所在班组ID
				  // order_staff_id=info.get("order_staff_id");
				     actionType=info.get("actionType");//工单性质
				     maintor=info.get("maintor");//维护员账号ID或账号
				     auditor=info.get("auditor");//审核员账号ID 
				 }
				 //准备工单分组数据
				 orderObject.put("eqpId_port", eqpId_port);
				 orderObject.put("eqpNo_port", eqpNo_port);
				 orderObject.put("eqpName_port", eqpName_port);
				 orderObject.put("eqpAddress", eqpAddress);
				 orderObject.put("eqpTypeId_port", eqpTypeId_port);
				 orderObject.put("actionType", actionType);
				 orderObject.put("portId", portId);
				 orderObject.put("portNo", portNo);
				 orderObject.put("portName", portName);
				 orderObject.put("photoIds", photoIds);
				 orderObject.put("reason", reason);
				 orderObject.put("isCheckOK", isCheckOK);
				 orderObject.put("glbm", glbm);
				 orderObject.put("glmc", glmc);
				 orderObject.put("isH", isH);
				 orderObject.put("type", type);
				 orderObject.put("orderId", orderId);
				 orderObject.put("orderNo", orderNo);
				 orderObject.put("orderMark", orderMark);
				 orderObject.put("archive_time", archive_time);
				 orderObject.put("order_team_id", order_team_id);					
				 orderObject.put("portRightPosition",truePortNo );//端子编码正确位置
				 orderObject.put("portIdRightPosition", truePortId);//端子ID正确位置
				 orderObject.put("eqpNo_rightPortNo", rightEqpNo);//设备编码正确位置
				 orderObject.put("eqpId_rightPortNo", rightEqpId);//设备ID正确位置
				 orderObject.put("record_type", "1");
				 orderObject.put("area_id", areaId);
				 orderObject.put("son_area_id", sonAreaId);
				 orderObject.put("changedPortId", changedPortId);//修改后端子ID
				 orderObject.put("changedPortNo", changedPortNo);//修改后端子编码
				 orderObject.put("changedEqpId", changedEqpId);//修改后设备ID
				 orderObject.put("changedEqpNo", changedEqpNo);//修改后设备编码
				
				if("0".equals(isCheckOK)){
					orderObject.put("maintor", "");
					orderObject.put("auditor", "");
					rightPortNum++;//合格端子数量
					right_orderArray.add(orderObject);
				}else{
					if(errorPortIds.length()>0){
						errorPortIds=errorPortIds+","+portId;
						errorPortDescript=errorPortDescript+reason;
					}else{
						errorPortIds=portId;
						errorPortDescript=reason;
					}
					orderObject.put("maintor", maintor);
					orderObject.put("auditor", auditor);
					 //综调
					 if("1".equals(orderMark)){
						 csv_orderArray.add(orderObject);
					 }
					 //IOM
					 if("2".equals(orderMark)){
						 iom_orderArray.add(orderObject);
					 }
					 //既未匹配到综调又未匹配到IOM
					 if(orderResult==null){
						 no_csv_iom_orderArray.add(orderObject);
					 }
					 //将端子错误信息照插入errorPortObject
					 /*errorPortObject.put("eqpNo_port", eqpNo_port);
					 errorPortObject.put("eqpName_port", eqpName_port);
					 errorPortObject.put("portNo", portNo);
					 errorPortObject.put("glbm", glbm);
					 errorPortObject.put("reason", reason);
					 String photoUrl="";
					 if(photoIds.length()>0){
						 //通过photoIds获取photoUrl
						 int l=photoIds.length();
						 photoIds=photoIds.substring(0, l-1);
						 photoUrl=checkOrderDao.getPhotoUrl(photoIds);
						 if(photoUrl==null){
							 photoUrl="";
						 }
					 }
					 errorPortObject.put("photoUrl", photoUrl);
					 errorPortsArray.add(errorPortObject);*/
				}
				 /*if("1".equals(isCheckOK)){
					 //综调
					 if("1".equals(orderMark)){
						 csv_orderArray.add(orderObject);
					 }
					 //IOM
					 if("2".equals(orderMark)){
						 iom_orderArray.add(orderObject);
					 }
					 //既未匹配到综调又未匹配到IOM
					 if(orderResult!=null&&orderResult.size()>0){
						 no_csv_iom_orderArray.add(orderObject);
					 }
				 }*/
				 //工单检查时间
				//将修改后的端子插入流程表 tb_cablecheck_process
				//(通过一键改后的端子不太确定是否合格，之前是合格，但南京局这边确定了如果改好后 仍然是不合格，但是错误原因为 "检查人员已将现场问题整改;")
				//if("0".equals(isCheckOK)){
					if(!"".equals(changedPortNo)){
						String content="";
						if(eqpId_port.equals(changedEqpId)){
							content=glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
						}else{
							content =glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
						}
						recordMap.put("status", "66");//一键改
						recordMap.put("remark", "一键改");
						recordMap.put("receiver", "");
						recordMap.put("content", content);
						//checkTaskDao.addProcessNew(recordMap);
						checkProcessDao.addProcessNew_orderNo(recordMap);
					}
				// }
				 recordMap.put("staffId", staffId);
				 if(!"".equals(orderNo)){
					 recordMap.put("order_no", orderNo);
					 checkTaskDao.updateOrderCheckTime(recordMap);
				 }
			 }
			 checkedPortDescript=errorPortDescript;//错误内容描述
			 //判断是否需要整改
			 if("0".equals(is_bill)){//无需整改直接归档
				 JSONArray allOrderArray=new JSONArray();
				 //所有端子分为合格和不合格  合格的端子记录放在right_orderArray中，不合格的分别放在csv_orderArray，iom_orderArray，no_csv_iom_orderArray
				 //有时无需整改也会存在不合格的端子，所以统一纳入allOrderArray
				 for(int i=0;i<right_orderArray.size();i++){
					 allOrderArray.add(right_orderArray.get(i));
				 }
				 for(int i=0;i<csv_orderArray.size();i++){
					 allOrderArray.add(csv_orderArray.get(i));
				 }
				 for(int i=0;i<iom_orderArray.size();i++){
					 allOrderArray.add(iom_orderArray.get(i));
				 }
				 for(int i=0;i<no_csv_iom_orderArray.size();i++){
					 allOrderArray.add(no_csv_iom_orderArray.get(i));
				 }
				 //生成任务，插入记录，插入详情，插入设备记录，插入流程，
				 //生成新的任务
				 String maintor="";//无需整改 维护员、审核员为空
				 String auditor="";
				 taskId = generateNewTask(staffId, areaId, eqpId, eqpNo,
						eqpName, remarks, is_bill, checkWay, oneback,
						sonAreaId,maintor,auditor);	
				 //插入新的记录
				 generateNewRecord(staffId, taskId, areaId, remarks,
						longitude, latitude, checkWay, oneback, sonAreaId,
						allOrderArray);
				 //插入设备详情，设备记录，流程详情，设备 照片
				 String content="端子检查合格或现场已整改，任务直接归档";
				 generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
						eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
						longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor);
			 }
			 if("1".equals(is_bill)){//需整改
				 //先获取设备照片地址
				/* String photoIds="";
				 if(eqpPhotoIds.length()>0){
					 photoIds=eqpPhotoIds;
				 }
				 int l=photoIds.length();
				 photoIds=photoIds.substring(0, l-1);
				 String eqpPhotoUrl=checkOrderDao.getPhotoUrl(photoIds);*/
				 //检查端子全部为合格仍需整改，则直接推送给集约化,将 综调、iom、两者都未匹配的工单信息集中到一个对象数组里面
				 if(rightPortNum==innerArray.size()){
					 JSONArray allOrderArray=new JSONArray();
					 for(int i=0;i<right_orderArray.size();i++){
						 allOrderArray.add(right_orderArray.get(i));
					 }
					 for(int i=0;i<csv_orderArray.size();i++){
						 allOrderArray.add(csv_orderArray.get(i));
					 }
					 for(int i=0;i<iom_orderArray.size();i++){
						 allOrderArray.add(iom_orderArray.get(i));
					 }
					 for(int i=0;i<no_csv_iom_orderArray.size();i++){
						 allOrderArray.add(no_csv_iom_orderArray.get(i));
					 }
					 //生成新的任务
					 String maintor="";//需整改派到集约化，则使维护员、审核员为空
					 String auditor="";
					 taskId = generateNewTask(staffId, areaId, eqpId, eqpNo,
							eqpName, remarks, is_bill, checkWay, oneback,
							sonAreaId,maintor,auditor);	
					 //插入新的记录
					 generateNewRecord(staffId, taskId, areaId, remarks,
							longitude, latitude, checkWay, oneback, sonAreaId,
							allOrderArray);
					/* //插入设备详情，设备记录，流程详情，设备 照片
					 String content="生成整改单自动派发至集约化";
					 generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
							eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
							longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor);*/
					 String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
					 //隐患工单自动推送至集约化
					Map iomMap=new HashMap();
					iomMap.put("sysRoute", "GWZS");
					iomMap.put("taskTypes", "6");
					iomMap.put("staffName", staffName);//检查人员姓名
					iomMap.put("TaskName", TASK_NAME);//任务ID
					iomMap.put("taskid", taskId);//任务ID
					iomMap.put("IOMid_numberList", "");//施工人员账号
					iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
					iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
					iomMap.put("eqpNo", eqpNo);
					iomMap.put("eqpName", eqpName);
					//iomMap.put("eqpPhotoUrl", eqpPhotoUrl);
					//iomMap.put("errorPortsArray", errorPortsArray);
					iomMap.put("equType", equType);
					iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
					iomMap.put("descript", errorPortDescript);//错误描述
					iomMap.put("taskCatgory", "LINE");
					iomMap.put("isEquPro", "0");
					iomMap.put("qualityInfo",check_remark );//现场规范
					Map<String,String> resultMap=autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
					 //插入设备详情，设备记录，流程详情，设备 照片
					String result_status=resultMap.get("Result_Status");
					String errorReason=resultMap.get("errorReason");
					String content="";
					if("0".equals(result_status)){
						content="工单已推送至集约化，请至集约化进行处理";
					}else{
						content="触发集约化工单失败，请在光网助手进行处理，失败原因："+errorReason;
					}
					generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
							eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
							longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor);
				 }else{
					 //综调
					 if(csv_orderArray!=null&&csv_orderArray.size()>0){
						 /*for(int i=0;i<right_orderArray.size();i++){
							 csv_orderArray.add(right_orderArray.get(i));//将合格的加入到综调
						 }*/
						 //循环分组生成任务
						 JSONObject  csv_orderObject1=new JSONObject();
						 JSONObject  csv_orderObject2=new JSONObject();
						 for(int i=0;i<csv_orderArray.size();i++){
							 JSONArray sameMaintor=new JSONArray();//派发给同一个维护员，审核员暂时默认为一致，暂时不考虑
							 csv_orderObject1=csv_orderArray.getJSONObject(i);			
							 String maintor1=csv_orderObject1.getString("maintor");
							 String auditor=csv_orderObject1.getString("auditor");
							 sameMaintor.add(csv_orderObject1);
							 for(int j =i+1 ;j<csv_orderArray.size();j++){
								 csv_orderObject2=csv_orderArray.getJSONObject(j);
								 String maintor2=csv_orderObject2.getString("maintor");
								 if(maintor1.equals(maintor2)){//同一个维护员
									 sameMaintor.add(csv_orderObject2);
									 csv_orderArray.remove(j);
									 j--;
								 }
							 }
							 for(int m=0;m<right_orderArray.size();m++){
								 sameMaintor.add(right_orderArray.get(m));//以施工人分完租后 将合格的加入到综调
							 }
							 taskId = generateNewTask(staffId, areaId, eqpId, eqpNo,
									eqpName, remarks, is_bill, checkWay, oneback,
									sonAreaId,maintor1,auditor);	
								 //插入新的记录
							 generateNewRecord(staffId, taskId, areaId, remarks,
									longitude, latitude, checkWay, oneback, sonAreaId,
									sameMaintor);
								 //插入设备详情，设备记录，流程详情，设备 照片 
							 String content="生成整改单自动派发至维护员";
							 generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
									eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
									longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor1);
						 }
					 }
					 //iom
					 if(iom_orderArray!=null&&iom_orderArray.size()>0){
						 /*for(int i=0;i<right_orderArray.size();i++){
							 iom_orderArray.add(right_orderArray.get(i));//将合格的加入到iom
						 }*/
						 //循环分组生成任务
						 JSONObject  iom_orderObject1=new JSONObject();
						 JSONObject  iom_orderObject2=new JSONObject();
						 for(int i =0 ;i<iom_orderArray.size();i++){
							 JSONArray sameMaintor=new JSONArray();//派发给同一个维护员，审核员暂时默认为一致，暂时不考虑
							 iom_orderObject1=iom_orderArray.getJSONObject(i);
							 String maintor1=iom_orderObject1.getString("maintor");
							 String auditor=iom_orderObject1.getString("auditor");
							 sameMaintor.add(iom_orderObject1);
							 for(int j =i+1 ;j<iom_orderArray.size();j++){
								 iom_orderObject2=iom_orderArray.getJSONObject(j);
								 String maintor2=iom_orderObject2.getString("maintor");
								 if(maintor1.equals(maintor2)){//同一个维护员
									 sameMaintor.add(iom_orderObject2);
									 iom_orderArray.remove(j);
									 j--;
								 }
							 }
							 for(int m=0;m<right_orderArray.size();m++){
								 sameMaintor.add(right_orderArray.get(m));//分完组将合格的加入到iom
							 }
							 errorPortIds="";
							 errorPortDescript="";
							 String ischeckok="";
							 String eqpNo_port="";
							 String portNo="";
							 String reason="";
							 String portId="";
							 if(sameMaintor!=null&&sameMaintor.size()>0){
								 for(int m=0;m<sameMaintor.size();m++){
									 ischeckok=sameMaintor.getJSONObject(m).getString("isCheckOK");
									 portId=sameMaintor.getJSONObject(m).getString("portId");
									 portNo=sameMaintor.getJSONObject(m).getString("portNo");
									 reason=sameMaintor.getJSONObject(m).getString("reason");
									 eqpNo_port=sameMaintor.getJSONObject(m).getString("eqpNo_port");
									 if(errorPortIds.length()>0){
										  errorPortIds=errorPortIds+","+portId;
										  errorPortDescript=errorPortDescript+";"+eqpNo_port+","+portNo+","+reason;;
									 }else{
										  errorPortIds=portId;
										  errorPortDescript=eqpNo_port+","+portNo+","+reason;;
									 }
								 }
							 }
							 maintor1="";
							 auditor="";
							 taskId = generateNewTask(staffId, areaId, eqpId, eqpNo,
										eqpName, remarks, is_bill, checkWay, oneback,
										sonAreaId,maintor1,auditor);	
									 //插入新的记录
							 generateNewRecord(staffId, taskId, areaId, remarks,
										longitude, latitude, checkWay, oneback, sonAreaId,
										sameMaintor);
									 //插入设备详情，设备记录，流程详情，设备 照片
							 //String content="生成整改单自动派发至集约化";
							 //maintor1="";
							/* generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
										eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
										longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor1);*/
							String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
							 //隐患工单自动推送至集约化
							Map iomMap=new HashMap();
							iomMap.put("sysRoute", "GWZS");
							iomMap.put("taskTypes", "6");
							iomMap.put("staffName", staffName);//检查人员姓名
							iomMap.put("taskid", taskId);//任务ID
							iomMap.put("TaskName", TASK_NAME);//任务ID
							iomMap.put("IOMid_numberList", maintor1);//施工人员账号
							iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
							iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
							iomMap.put("eqpNo", eqpNo);
							iomMap.put("eqpName", eqpName);
							//iomMap.put("eqpPhotoUrl", eqpPhotoUrl);
							//iomMap.put("errorPortsArray", errorPortsArray);
							iomMap.put("equType", equType);
							iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
							iomMap.put("descript", errorPortDescript);//错误描述
							iomMap.put("taskCatgory", "IOM");
							iomMap.put("isEquPro", "1");
							iomMap.put("qualityInfo",check_remark );//现场规范
							//autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败 
							Map<String,String> resultMap=autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
							 //插入设备详情，设备记录，流程详情，设备 照片
							String result_status=resultMap.get("Result_Status");
							String errorReason=resultMap.get("errorReason");
							String content="";
							if("0".equals(result_status)){
								content="工单已推送至集约化，请至集约化进行处理";
							}else{
								content="触发集约化工单失败，请在光网助手进行处理，失败原因："+errorReason;
							}
							generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
									eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
									longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor1);
						 }
					 }
					 //既未匹配到综调，又未匹配到iom
					 if(no_csv_iom_orderArray!=null&&no_csv_iom_orderArray.size()>0){
						 //既未匹配到综调又未匹配到IOM的端子分别派发，如果端子属于分光器并且是以F开头，就留在光网助手系统，派给区级管理员（默认接单岗）
						 //其他的均派到集约化
						 JSONArray inner_orderArray=new JSONArray();//留在光网助手
						 JSONArray outer_orderArray=new JSONArray();//自动推送到集约化
						 String res_type_id="";
						 String glbm="";
						 JSONObject portObject=null;
						 for(int i=0;i<no_csv_iom_orderArray.size();i++){
							 portObject= no_csv_iom_orderArray.getJSONObject(i);
							 res_type_id=portObject.getString("eqpTypeId_port");
							 glbm=portObject.getString("glbm");
							 if("2530".equals(res_type_id)&&glbm.startsWith("F")){
								 inner_orderArray.add(portObject);
							 }else{
								 outer_orderArray.add(portObject);
							 }
							 //no_csv_iom_orderArray.add(right_orderArray.get(i));//将合格的加入到iom
						 }
						 if(inner_orderArray!=null&&inner_orderArray.size()>0){
							 if(right_orderArray!=null&&right_orderArray.size()>0){
								 for(int i=0;i<right_orderArray.size();i++){
									 inner_orderArray.add(right_orderArray.get(i));//将合格的加入
								 }
							 }
							 //生成新的任务
							 //通过设备所在的区域ID去查询区级管理员，将任务派给区级管理员，维护员为区级管理员，审核员也为区级管理员
							 Map<String, String> sonAreaAdmin= checkOrderDao.getSonAreaAdmin(sonAreaId);
							 String maintor= null==sonAreaAdmin.get("STAFF_ID")?"":sonAreaAdmin.get("STAFF_ID");//维护员
							 String auditor= null==sonAreaAdmin.get("STAFF_ID")?"":sonAreaAdmin.get("STAFF_ID");//审核员
							 taskId = generateNewTask(staffId, areaId, eqpId, eqpNo,
									eqpName, remarks, is_bill, checkWay, oneback,
									sonAreaId,maintor,auditor);	
							 //插入新的记录
							 generateNewRecord(staffId, taskId, areaId, remarks,
									longitude, latitude, checkWay, oneback, sonAreaId,
									inner_orderArray);
							 //插入设备详情，设备记录，流程详情，设备 照片
							 String content="生成整改单自动派发至接单岗";
							 generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
									eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
									longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor);
						 }
						 if(outer_orderArray!=null&&outer_orderArray.size()>0){
							 if(right_orderArray!=null&&right_orderArray.size()>0){
								 for(int i=0;i<right_orderArray.size();i++){
									 outer_orderArray.add(right_orderArray.get(i));//将合格的加入
								 }
							 }
							 errorPortIds="";
							 errorPortDescript="";
							 String ischeckok="";
							 String eqpNo_port="";
							 String portNo="";
							 String reason="";
							 String portId="";
							 if(outer_orderArray!=null&&outer_orderArray.size()>0){
								 for(int m=0;m<outer_orderArray.size();m++){
									 ischeckok=outer_orderArray.getJSONObject(m).getString("isCheckOK");
									 portId=outer_orderArray.getJSONObject(m).getString("portId");
									 portNo=outer_orderArray.getJSONObject(m).getString("portNo");
									 reason=outer_orderArray.getJSONObject(m).getString("reason");
									 eqpNo_port=outer_orderArray.getJSONObject(m).getString("eqpNo_port");
									 if(errorPortIds.length()>0){
										  errorPortIds=errorPortIds+","+portId;
										  errorPortDescript=errorPortDescript+";"+eqpNo_port+","+portNo+","+reason;;
									 }else{
										  errorPortIds=portId;
										  errorPortDescript=eqpNo_port+","+portNo+","+reason;;
									 }
								 }
							 }
							//生成新的任务
							 String maintor="";//需整改派到集约化，则使维护员、审核员为空
							 String auditor=""; 
							 taskId = generateNewTask(staffId, areaId, eqpId, eqpNo,
									eqpName, remarks, is_bill, checkWay, oneback,
									sonAreaId,maintor,auditor);	
							 //插入新的记录
							 generateNewRecord(staffId, taskId, areaId, remarks,
									longitude, latitude, checkWay, oneback, sonAreaId,
									outer_orderArray);
							 //插入设备详情，设备记录，流程详情，设备 照片
							/* String content="生成整改单自动派发至集约化";
							 generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
									eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
									longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor);*/
							String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
							//隐患工单自动推送至集约化
							Map iomMap=new HashMap();
							iomMap.put("sysRoute", "GWZS");
							iomMap.put("taskTypes", "6");
							iomMap.put("staffName", staffName);//检查人员姓名
							iomMap.put("taskid", taskId);//任务ID
							iomMap.put("TaskName", TASK_NAME);//任务ID
							iomMap.put("IOMid_numberList", "");//施工人员账号
							iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
							iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
							iomMap.put("eqpNo", eqpNo);
							iomMap.put("eqpName", eqpName);
							//iomMap.put("eqpPhotoUrl", eqpPhotoUrl);
							//iomMap.put("errorPortsArray", errorPortsArray);
							iomMap.put("equType", equType);
							iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
							iomMap.put("descript", errorPortDescript);//错误描述
							iomMap.put("taskCatgory", "LINE");
							iomMap.put("isEquPro", "1");
							iomMap.put("qualityInfo",check_remark );//现场规范
							//autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
							Map<String,String> resultMap=autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
							 //插入设备详情，设备记录，流程详情，设备 照片
							String result_status=resultMap.get("Result_Status");
							String errorReason=resultMap.get("errorReason");
							String content="";
							if("0".equals(result_status)){
								content="工单已推送至集约化，请至集约化进行处理";
							}else{
								content="触发集约化工单失败，请在光网助手进行处理，失败原因："+errorReason;
							}
							generateNewEqpDeailRecord(staffId, taskId, areaId, eqpId,
									eqpNo, eqpName, eqpAddress, eqpPhotoIds, remarks,
									longitude, latitude, checkWay, oneback, sonAreaId,is_bill,content,maintor);
						 }
					 }
				 }
			 }
			Map portmap = new HashMap();
			portmap.put("areaId", areaId);
			portmap.put("sonAreaId", sonAreaId);
			portmap.put("allcount", allcount);
			portmap.put("truecount", truecount);
			portmap.put("eqpId", eqpId);
			portmap.put("eqpNo", eqpNo);
			checkOrderDao.updateEqu_time_num(portmap);
			
			for (int i = 0; i < addressArray.size(); i++) {
				int id = checkOrderDao.geteqpAddressId();
				JSONObject eqp = (JSONObject) addressArray.get(i);
				String eqpId_add = null == eqp.get("eqpId") ? "": eqp.getString("eqpId");
				String eqpNo_add = null == eqp.get("eqpNo") ? "": eqp.getString("eqpNo");
				String location_id = eqp.getString("locationId");
				String address_id = eqp.getString("addressId");
				String address_name = eqp.getString("addressName");
				String is_check_ok = eqp.getString("is_check_ok");
				String error_reason = null == eqp.get("error_reason") ? "" : eqp.getString("error_reason");
				Map adddressMap = new HashMap();
				adddressMap.put("id", id);
				adddressMap.put("phy_eqp_id", eqpId_add);
				adddressMap.put("phy_eqp_no", eqpNo_add);
				adddressMap.put("install_eqp_id", eqpId);
				adddressMap.put("location_id", location_id);
				adddressMap.put("address_id", address_id);
				adddressMap.put("address_name", address_name);
				adddressMap.put("is_check_ok", is_check_ok);
				adddressMap.put("error_reason", error_reason);
				adddressMap.put("task_id", taskId);
				adddressMap.put("create_staff", staffId);
				adddressMap.put("area_id", areaId);
				adddressMap.put("son_area_id", sonAreaId);
				checkOrderDao.insertEqpAddress(adddressMap);
			}
			//光网助手检查触发线路工单，先插入 tb_base_gwzs_lineworkorder（光网助手线路工单表），然后通过同步平台发到FTP目录下
			checkedPortIds=innerArray.size();

			adressCheckCnt=addressArray.size();//覆盖地址数量
			Map lineWork=new HashMap();
			lineWork.put("gwMan", staffName);//光网助手检查人员
			lineWork.put("taskId", taskId);//任务ID
			lineWork.put("gwManAccount", staffNo);//检查人员账号
			lineWork.put("equCode", eqpNo);//设备编码
			lineWork.put("equName", eqpName);//设备名称
			lineWork.put("chekPortNum", checkedPortIds);//检查端子数量
			lineWork.put("adressCheckCnt", adressCheckCnt);//检查端子数量
			lineWork.put("gwContent", checkedPortDescript);//检查内容描述
			lineWork.put("workhours", "0");//检查内容描述
			checkOrderDao.insertLineWorkOrder(lineWork);
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "工单提交失败");
		}
		return result.toString();
	}

	public void generateNewEqpDeailRecord(String staffId, String taskId,
			String areaId, String eqpId, String eqpNo, String eqpName,
			String eqpAddress, String eqpPhotoIds, String remarks,
			String longitude, String latitude, String checkWay, String oneback,
			String sonAreaId,String is_bill,String content,String maintor) {
		//插入设备详情
		int eqpRecordId = checkOrderDao.getRecordId();
		Map eqpDetailMap = new HashMap();
		eqpDetailMap.put("TASK_ID", taskId);
		eqpDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
		eqpDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
		eqpDetailMap.put("INSPECT_OBJECT_TYPE", 0);
		eqpDetailMap.put("CHECK_FLAG", "1");
		eqpDetailMap.put("GLBM", "");
		eqpDetailMap.put("GLMC", "");
		eqpDetailMap.put("PORT_ID", "");
		eqpDetailMap.put("dtsj_id", "");
		eqpDetailMap.put("eqpId_port", "");
		eqpDetailMap.put("eqpNo_port", "");
		eqpDetailMap.put("eqpName_port", "");
		eqpDetailMap.put("orderNo", "");
		eqpDetailMap.put("orderMark", "");
		eqpDetailMap.put("actionType", "");
		eqpDetailMap.put("archive_time", "");
		checkOrderDao.saveTroubleTaskDetail(eqpDetailMap);
		String eqp_detail_id = eqpDetailMap.get("DETAIL_ID").toString();
		//插入设备记录
		Map eqpRecordMapNew = new HashMap();
		eqpRecordMapNew.put("recordId", eqpRecordId);	
		eqpRecordMapNew.put("task_id", taskId);
		eqpRecordMapNew.put("glbm", "");
		eqpRecordMapNew.put("detail_id", eqp_detail_id);
		eqpRecordMapNew.put("eqpId", eqpId);
		eqpRecordMapNew.put("eqpNo", eqpNo);
		eqpRecordMapNew.put("eqpName", eqpName);
		eqpRecordMapNew.put("eqpAddress", eqpAddress);
		eqpRecordMapNew.put("longitude", longitude);
		eqpRecordMapNew.put("latitude", latitude);
		eqpRecordMapNew.put("staffId", staffId);
		eqpRecordMapNew.put("remarks", remarks);//现场规范
		eqpRecordMapNew.put("port_id", "");
		eqpRecordMapNew.put("port_no", "");
		eqpRecordMapNew.put("action_type", "");
		eqpRecordMapNew.put("orderId", "");
		eqpRecordMapNew.put("orderNo", "");
		eqpRecordMapNew.put("isFrom", "");
		eqpRecordMapNew.put("port_name", "");
		eqpRecordMapNew.put("descript", "");
		if("0".equals(is_bill)){
			eqpRecordMapNew.put("isCheckOK", "0");
		}else{
			eqpRecordMapNew.put("isCheckOK", "1");
		}
		eqpRecordMapNew.put("truePortNo", "");
		eqpRecordMapNew.put("truePortId", "");
		eqpRecordMapNew.put("rightEqpNo", "");
		eqpRecordMapNew.put("rightEqpId", "");
		eqpRecordMapNew.put("record_type", "1");
		eqpRecordMapNew.put("area_id", areaId);
		eqpRecordMapNew.put("son_area_id", sonAreaId);
		eqpRecordMapNew.put("changedPortId", "");
		eqpRecordMapNew.put("changedPortNo", "");
		eqpRecordMapNew.put("changedEqpId", "");
		eqpRecordMapNew.put("changedEqpNo", "");
		eqpRecordMapNew.put("checkWay", checkWay);
		checkOrderDao.insertEqpRecordNewYy(eqpRecordMapNew);
		//插入流程环节
		Map processMap = new HashMap();
		processMap.put("task_id", taskId);
		processMap.put("oper_staff", staffId);
		if("0".equals(is_bill)){
			processMap.put("status", "8");
		}else{
			processMap.put("status", "6");
		}
		processMap.put("remark", "检查提交");
		processMap.put("content", content);
		processMap.put("receiver", maintor);
		checkProcessDao.addProcessNew(processMap);
		 // 保存设备的照片信息
		if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", taskId);
			photoMap.put("DETAIL_ID", eqp_detail_id);
			photoMap.put("OBJECT_ID", eqpRecordId);
			if("0".equals(oneback)){
				photoMap.put("REMARKS", "一键反馈 ");// 一键反馈 1 
			}else{
				photoMap.put("REMARKS", "不预告抽查");// 不预告 2 
			}
			photoMap.put("OBJECT_TYPE", "1");// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", eqpRecordId);
			String[] photos = eqpPhotoIds.split(",");
			for (String photo : photos) {
				photoMap.put("PHOTO_ID", photo);
				checkOrderDao.insertPhotoRel(photoMap);
			}			
		}
	}

	public void generateNewRecord(String staffId, String taskId, String areaId,
			String remarks, String longitude, String latitude, String checkWay,
			String oneback, String sonAreaId, JSONArray allOrderArray) {
		//循环工单，插入详情与记录
		Map portRecordMap=new HashMap();
		Map portDetailMap=new HashMap();
		for(int m=0;m<allOrderArray.size();m++){
			JSONObject portObject= allOrderArray.getJSONObject(m);
			//将端子记录插入任务详情表
			int recordId = checkOrderDao.getRecordId();
			portDetailMap.put("TASK_ID", taskId);
			portDetailMap.put("INSPECT_OBJECT_ID", recordId);
			portDetailMap.put("INSPECT_OBJECT_NO", portObject.get("portNo"));
			portDetailMap.put("INSPECT_OBJECT_TYPE", 1);
			portDetailMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
			portDetailMap.put("GLBM", portObject.get("glbm"));
			portDetailMap.put("GLMC", portObject.get("glmc"));
			portDetailMap.put("PORT_ID", portObject.get("portId"));
			portDetailMap.put("dtsj_id", "");
			portDetailMap.put("eqpId_port", portObject.get("eqpId_port"));
			portDetailMap.put("eqpNo_port", portObject.get("eqpNo_port"));
			portDetailMap.put("eqpName_port", portObject.get("eqpName_port"));
			portDetailMap.put("orderNo", portObject.get("orderNo"));
			portDetailMap.put("orderMark", portObject.get("orderMark"));
			portDetailMap.put("actionType", portObject.get("actionType"));
			portDetailMap.put("archive_time", portObject.get("archive_time"));
			checkOrderDao.saveTroubleTaskDetail(portDetailMap);
			//将端子记录插入记录表
			String detail_id = portDetailMap.get("DETAIL_ID").toString();						
			portRecordMap.put("recordId", recordId);	
			portRecordMap.put("task_id", taskId);
			portRecordMap.put("glbm", portObject.get("glbm"));
			portRecordMap.put("detail_id", detail_id);
			portRecordMap.put("eqpId", portObject.get("eqpId_port"));
			portRecordMap.put("eqpNo", portObject.get("eqpNo_port"));
			portRecordMap.put("eqpName", portObject.get("eqpName_port"));
			portRecordMap.put("eqpAddress", portObject.get("eqpAddress"));
			portRecordMap.put("longitude", longitude);
			portRecordMap.put("latitude", latitude);
			portRecordMap.put("staffId", staffId);
			portRecordMap.put("remarks", remarks);//现场规范
			portRecordMap.put("port_id", portObject.get("portId"));
			portRecordMap.put("port_no", portObject.get("portNo"));
			portRecordMap.put("action_type", portObject.get("actionType"));
			portRecordMap.put("orderId", portObject.get("orderId"));
			portRecordMap.put("orderNo", portObject.get("orderNo"));
			String orderMark=portObject.get("orderMark").toString();
			if("1".equals(orderMark)){
				portRecordMap.put("isFrom", "0");//0表示综调  1 表示 iom			
			}
			if("2".equals(orderMark)){
				portRecordMap.put("isFrom", "1");//0表示综调  1 表示 iom			
			}
			portRecordMap.put("port_name", portObject.get("portName"));
			portRecordMap.put("descript", portObject.get("reason"));//端子错误描述
			portRecordMap.put("isCheckOK", portObject.get("isCheckOK"));//端子是否合格
			portRecordMap.put("truePortNo", portObject.get("portRightPosition"));//端子编码正确位置
			portRecordMap.put("truePortId", portObject.get("portIdRightPosition"));//端子ID正确位置
			portRecordMap.put("rightEqpNo", portObject.get("eqpNo_rightPortNo"));//设备编码正确位置
			portRecordMap.put("rightEqpId", portObject.get("eqpId_rightPortNo"));//设备ID正确位置
			portRecordMap.put("record_type", "1");
			portRecordMap.put("area_id", areaId);
			portRecordMap.put("son_area_id", sonAreaId);
			portRecordMap.put("changedPortId",  portObject.get("changedPortId"));//修改后端子ID
			portRecordMap.put("changedPortNo",  portObject.get("changedPortNo"));//修改后端子编码
			portRecordMap.put("changedEqpId",  portObject.get("changedEqpId"));//修改后设备ID
			portRecordMap.put("changedEqpNo", portObject.get("changedEqpNo"));//修改后设备编码
			portRecordMap.put("checkWay", checkWay);//检查方式
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(portRecordMap);
			// 保存端子照片关系
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", taskId);
			photoMap.put("DETAIL_ID", detail_id);
			photoMap.put("OBJECT_ID", recordId);
			if("0".equals(oneback)){
				photoMap.put("REMARKS", "一键反馈");;// 一键反馈 1 
			}else{
				photoMap.put("REMARKS", "不预告检查");;// 不预告 2 
			}
			photoMap.put("OBJECT_TYPE",1);// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", recordId);
			if (!"".equals(portObject.getString("photoIds"))) {
				String[] photos = portObject.getString("photoIds").split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
		}
	}
	/**
	 * 生成新的任务
	 */
	public String generateNewTask(String staffId, String areaId, String eqpId,
			String eqpNo, String eqpName, String remarks, String is_bill,
			String checkWay, String oneback, String sonAreaId,String maintor,String auditor) {
		String taskId="";
		//生成任务，插入记录+插入详情，插入设备记录，插入流程
		 Map troubleTaskMap = new HashMap();
		 String TASK_NO=eqpNo+ "_" + DateUtil.getDate("yyyyMMdd");
		 String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
		 troubleTaskMap.put("TASK_NO", TASK_NO);
		 troubleTaskMap.put("TASK_NAME",TASK_NAME);
		 if("0".equals(oneback)){
		 	troubleTaskMap.put("TASK_TYPE", "1");// 0:一键反馈   1 ：不预告
		 }else{
			troubleTaskMap.put("TASK_TYPE", "2");
		 }
		 troubleTaskMap.put("INSPECTOR", staffId);
		 troubleTaskMap.put("CREATE_STAFF", staffId);
		 troubleTaskMap.put("SON_AREA_ID",sonAreaId);
		 troubleTaskMap.put("AREA_ID",areaId);
		 troubleTaskMap.put("ENABLE",0);// 如果不需要整改工单，则把此工单只为无效,0可用// 1不可用（不显示在待办列表）
		 troubleTaskMap.put("REMARK", remarks);//现场规范
		 troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
		 troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
		 troubleTaskMap.put("SBID", eqpId);// 设备id								
		 if("0".equals(is_bill)){
			 troubleTaskMap.put("STATUS_ID",8);// 无需整改直接归档
			 troubleTaskMap.put("MAINTOR", "");
			 troubleTaskMap.put("AUDITOR", "");	
		 }else{
			 troubleTaskMap.put("STATUS_ID",6);// 需整改自动派发
			 troubleTaskMap.put("MAINTOR", maintor);
			 troubleTaskMap.put("AUDITOR",auditor);	
		 }
		 troubleTaskMap.put("checkWay",checkWay);	
		 checkTaskDao.saveTroubleTask(troubleTaskMap);
		 taskId = troubleTaskMap.get("TASK_ID").toString();
		 return taskId;
	}
	//待办任务提交接口 代码优化
	@Override
	public String submitCheckOrder(String jsonStr){
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 传入的参数
			 */
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String sn = json.getString("sn");// sn
			String staffId = json.getString("staffId");//检查人
			String taskId = json.getString("taskId");
			String areaId = json.getString("areaId");
			String eqpId = json.getString("eqpId");//设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");//设备名称
			String eqpAddress =json.getString("eqpAddress");
			String eqpPhotoIds = json.getString("photoId");//设备照片
			String remarks = json.getString("remarks");//现场规范
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String is_bill = json.getString("is_bill");//是否需要整改
			String allcount = json.getString("allcount");// 全部端子数		
			String truecount = json.getString("truecount");// 正确端子数	
			String checkWay = json.getString("checkWay");// 检查方式 ：1 工单检查 ，2 设备端子检查
			// 覆盖地址检查数量
			JSONArray addressArray = null;
			int addressCheckCnt = 0;
			if (json.containsKey("allEqpAddress")) {
				addressArray = json.getJSONArray("allEqpAddress");
				addressCheckCnt = addressArray.size();
			}			
			/**
			 * 端子信息
			 */
			JSONArray innerArray =json.getJSONArray("port");
			
			//先查询出设备所属网格，设备类型，区域
			 Map param=new HashMap();
			 param.put("areaId", areaId);
			 param.put("eqpId", eqpId);
			 param.put("eqpNo", eqpNo);
			 Map eqpMap=checkOrderDao.getEqpType_Grid(param);
			 String eqpTypeId=eqpMap.get("RES_TYPE_ID").toString();//设备类型
			 String grid= null==eqpMap.get("GRID_ID")?"":eqpMap.get("GRID_ID").toString();//设备所属网格
			 String sonAreaId=eqpMap.get("AREA_ID").toString();//设备所属区域
			
			 String douDiGangNo="";//兜底岗账号
			 String contractPersonNo="";//承包人账号
			 if("1".equals(is_bill)){
				 if(!"".equals(grid)){
					 List<Map<String,String>>  parList=checkOrderDao.queryDouDiGang(grid);				
					 for(int i=0;i<parList.size();i++){
						 if(douDiGangNo.length()>0){
							 douDiGangNo=douDiGangNo+","+parList.get(i).get("STAFF_NO");
						 }else{
							 douDiGangNo=parList.get(i).get("STAFF_NO");
						 }
					 }
				 }else{
					 douDiGangNo="";
				 }
				 Map<String,String>  contractPersonMap=checkOrderDao.getEqpContractNo(param);				
				 if(contractPersonMap!=null && contractPersonMap.size()>0){
					 contractPersonNo=contractPersonMap.get("CONTRACT_PERSION_NO");
				 }
				
			 }
			 //检查人员姓名、账号
			 Map maps=checkOrderDao.queryByStaffId(staffId);
			 String staffName=maps.get("STAFF_NAME").toString();
			 String staffNo=maps.get("STAFF_NO").toString();
			 
			 String equType="";
			 if("703".equals(eqpTypeId)){
				 equType="1";
			 }else if("411".equals(eqpTypeId)){
				 equType="2";
			 }else if("704".equals(eqpTypeId)){
				 equType="3";
			 }else{
				 equType="4";
			 }
			 //错误端子列表 、错误端子描述
			 String errorPortIds="";
			 String errorPortDescript="";
			 String check_remark=remarks;//现场规范
			 
			 //检查端子数量 、检查端子描述
			 int checkedPortIds=0;
			 //检查工单数量
			 int checkOrderNum=0;
			 String checkedPortDescript="";
			 int adressCheckCnt=0;
			//正确端子数量
			 int rightPortNum=0;
			 Map orderParam = new HashMap();
			JSONArray orderArray=new JSONArray();
			JSONArray csv_orderArray=new JSONArray();//综调
			JSONArray iom_orderArray=new JSONArray();//IOM
			JSONArray no_csv_iom_orderArray=new JSONArray();//两者都未匹配上
			JSONObject orderObject=new JSONObject();
			Map<String, String> orderResult=new HashMap<String, String>();
			Map taskDetailMap = new HashMap();
			Map recordMap=new HashMap();
			JSONArray errorPortsArray=new JSONArray();
			JSONObject errorPortObject=new JSONObject();
			for(int i=0;i<innerArray.size();i++){
				JSONObject port = (JSONObject)innerArray.get(i);
				String eqpId_port = null==port.get("eqpId")?"":port.getString("eqpId");
				String eqpNo_port = null==port.get("eqpNo")?"":port.getString("eqpNo");
				String eqpName_port = null==port.get("eqpName")?"":port.getString("eqpName");
				String portId = port.getString("portId");
				String portNo = port.getString("portNo");
				String portName = port.getString("portName");
				String orderId = port.getString("order_id");//工单ID
				String orderNo = port.getString("order_no");//工单编码
				String photoIds = port.getString("photoId");
				String reason = port.getString("reason");
				String isCheckOK = port.getString("isCheckOK");
				String actionType=port.getString("actionType");//工单性质
				String truePortId =  port.getString("truePortId");//正确端子
				String truePortNo = port.getString("truePortNo");//正确编码
				String rightEqpId = port.getString("rightEqpId");//正确设备ID
				String rightEqpNo = port.getString("rightEqpNo");//正确设备编码
				if("查询".equals(truePortNo)){
					truePortId="";
					truePortNo="";
					rightEqpId="";
					rightEqpNo="";
				}
				String changedPortId=port.getString("portId_new");//修改后的端子id
				String changedPortNo=port.getString("localPortNo");//修改后的端子编码
				String changedEqpId=port.getString("sbid_new");//修改后的设备ID
				String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
				String glbm = null==port.get("glbm")?"":port.getString("glbm");
				String glmc = null==port.get("glmc")?"":port.getString("glmc");
				String isH = null==port.get("isH")?"":port.getString("isH");//是否H光路，0不是，1是
				String type = null;
				String eqpTypeId_port =null==port.get("eqp_type_id")?"":port.getString("eqp_type_id");
				if(glbm.startsWith("F")&&"2530".equals(eqpTypeId_port)){
					type = "1";//装维
					
				}else{
					type = "0";//综维
				}
				//通过设备id和端子id去找端子对应的工单
				String orderMark="";
				String order_team_id="";
				String archive_time="";//工单竣工时间
				//String order_staff_id="";
				String other_system_staff_id="";
				String auditor="";
				String maintor="";
				orderParam.put("eqpId", eqpId_port);
				orderParam.put("portId", portId);
				
				if("".equals(orderNo)){//只要工单号为空，则通过设备ID和端子去查询端子对应的工单信息（最近一次）
					orderResult=checkOrderDao.getOrderInfoNew(orderParam);
				}else{
					orderResult=checkOrderDao.getOrderOfBanZuNew(orderNo);
				}
				//写一个公共方法，对以上查询到的结果进行分析处理
				if(orderResult!=null&&orderResult.size()>0){//能匹配到工单
					Map<String,String> info=this.dealWithOrder(orderResult);
				    orderId=info.get("orderId");
				    orderNo=info.get("orderNo");
				    orderMark=info.get("orderMark");
				    order_team_id=info.get("order_team_id");
				    archive_time=info.get("archive_time");//工单竣工时间
				    //order_staff_id=info.get("order_staff_id");//维护员账号ID或账号
				    actionType=info.get("actionType");//工单性质
				    maintor=info.get("maintor");//维护员账号ID或账号
				    auditor=info.get("auditor");//审核员账号ID
				}	
				orderObject.put("eqpId_port", eqpId_port);
				orderObject.put("eqpNo_port", eqpNo_port);
				orderObject.put("eqpName_port", eqpName_port);
				orderObject.put("eqpTypeId_port", eqpTypeId_port);
				orderObject.put("eqpAddress", eqpAddress);
				orderObject.put("actionType", actionType);
				orderObject.put("portId", portId);
				orderObject.put("portNo", portNo);
				orderObject.put("portName", portName);
				orderObject.put("photoIds", photoIds);
				orderObject.put("reason", reason);
				orderObject.put("isCheckOK", isCheckOK);
				orderObject.put("glbm", glbm);
				orderObject.put("glmc", glmc);
				orderObject.put("isH", isH);
				orderObject.put("type", type);
				orderObject.put("orderId", orderId);
				orderObject.put("orderNo", orderNo);
				orderObject.put("orderMark", orderMark);
				orderObject.put("archive_time", archive_time);
				orderObject.put("order_team_id", order_team_id);					
				orderObject.put("portRightPosition", truePortId);//端子编码正确位置
				orderObject.put("portIdRightPosition", truePortNo);//端子ID正确位置
				orderObject.put("eqpNo_rightPortNo", truePortNo);//设备编码正确位置
				orderObject.put("eqpId_rightPortNo", rightEqpNo);//设备ID正确位置
				orderObject.put("record_type", "0");
				orderObject.put("area_id", areaId);
				orderObject.put("son_area_id", sonAreaId);
				orderObject.put("changedPortId", changedPortId);//修改后端子ID
				orderObject.put("changedPortNo", changedPortNo);//修改后端子编码
				orderObject.put("changedEqpId", changedEqpId);//修改后设备ID
				orderObject.put("changedEqpNo", changedEqpNo);
				if("0".equals(isCheckOK)){
					orderObject.put("maintor", "");
					orderObject.put("auditor", "");
					//正确端子数量
					rightPortNum++;
				}else{
					if(errorPortIds.length()>0){
						errorPortIds=errorPortIds+","+portId;
						/*errorPortDescript=errorPortDescript+";"+eqpNo_port+","+portNo+","+reason;*/
						errorPortDescript=errorPortDescript+reason;;
					}else{
						errorPortIds=portId;
						/*errorPortDescript=eqpNo_port+","+portNo+","+reason;*/
						errorPortDescript=reason;;
					}
					orderObject.put("maintor", maintor);
					orderObject.put("auditor", auditor);
					 //综调
					 if("1".equals(orderMark)){
						 csv_orderArray.add(orderObject);
					 }
					 //IOM
					 if("2".equals(orderMark)){
						 iom_orderArray.add(orderObject);
					 }
					 //既未匹配到综调又未匹配到IOM
					 if(orderResult==null){
						 no_csv_iom_orderArray.add(orderObject);
					 }
					//将端子错误信息照插入errorPortObject
					 /*errorPortObject.put("eqpNo_port", eqpNo_port);
					 errorPortObject.put("eqpName_port", eqpName_port);
					 errorPortObject.put("portNo", portNo);
					 errorPortObject.put("glbm", glbm);
					 errorPortObject.put("reason", reason);
					 String photoUrl="";
					 if(photoIds.length()>0){
						 //通过photoIds获取photoUrl
						 int l=photoIds.length();
						 photoIds=photoIds.substring(0, l-1);
						 photoUrl=checkOrderDao.getPhotoUrl(photoIds);
						 if(photoUrl==null){
							 photoUrl="";
						 }
					 }
					 errorPortObject.put("photoUrl", photoUrl);
					 errorPortsArray.add(errorPortObject);*/
				}
				//将端子记录插入任务详情表
				int recordId = checkOrderDao.getRecordId();
				taskDetailMap.put("TASK_ID", taskId);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
				taskDetailMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
				taskDetailMap.put("GLBM", glbm);
				taskDetailMap.put("GLMC", glmc);
				taskDetailMap.put("PORT_ID", portId);
				taskDetailMap.put("dtsj_id", "");
				taskDetailMap.put("eqpId_port", eqpId_port);
				taskDetailMap.put("eqpNo_port", eqpNo_port);
				taskDetailMap.put("eqpName_port", eqpName_port);
				taskDetailMap.put("orderNo",orderNo);
				taskDetailMap.put("orderMark", orderMark);
				taskDetailMap.put("actionType", actionType);
				taskDetailMap.put("archive_time", archive_time);
				checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
				//将端子记录插入记录表
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				recordMap.put("recordId", recordId);	
				recordMap.put("task_id", taskId);
				recordMap.put("glbm", glbm);
				recordMap.put("detail_id", detail_id);
				recordMap.put("eqpId", eqpId_port);
				recordMap.put("eqpNo", eqpNo_port);
				recordMap.put("eqpName", eqpName_port);
				recordMap.put("eqpAddress", eqpAddress);
				recordMap.put("longitude", longitude);
				recordMap.put("latitude", latitude);
				recordMap.put("staffId", staffId);
				recordMap.put("remarks", remarks);//现场规范
				recordMap.put("port_id", portId);
				recordMap.put("port_no", portNo);
				recordMap.put("action_type", actionType);
				recordMap.put("orderId", orderId);
				recordMap.put("orderNo", orderNo);
				if("1".equals(orderMark)){
					recordMap.put("isFrom", "0");//0表示综调  1 表示 iom
				}else if("2".equals(orderMark)){
					recordMap.put("isFrom", "1");
				}else{
					recordMap.put("isFrom", "");
				}
				recordMap.put("port_name", portName);
				recordMap.put("descript", reason);//端子错误描述
				recordMap.put("isCheckOK", isCheckOK);//端子是否合格
				recordMap.put("portRightPosition", truePortId);//端子编码正确位置
				recordMap.put("portIdRightPosition", truePortNo);//端子ID正确位置
				recordMap.put("eqpNo_rightPortNo", truePortNo);//设备编码正确位置
				recordMap.put("eqpId_rightPortNo", rightEqpNo);//设备ID正确位置
				recordMap.put("record_type", "0");
				recordMap.put("area_id", areaId);
				recordMap.put("son_area_id", sonAreaId);
				recordMap.put("changedPortId", changedPortId);//修改后端子ID
				recordMap.put("changedPortNo", changedPortNo);//修改后端子编码
				recordMap.put("changedEqpId", changedEqpId);//修改后设备ID
				recordMap.put("changedEqpNo", changedEqpNo);//修改后设备编码
				recordMap.put("checkWay", checkWay);
				//插入记录表
				checkOrderDao.insertEqpRecordNewYy(recordMap);				
				// 保存端子照片关系
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskId);
				photoMap.put("DETAIL_ID", detail_id);
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId);
				if (!"".equals(photoIds)) {
					String[] photos = photoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}
				//将修改后的端子插入流程表 tb_cablecheck_process
				//if("0".equals(isCheckOK)){
					if(!"".equals(changedPortNo)){
						String content="";
						if(eqpId_port.equals(changedEqpId)){
							content=glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
						}else{
							content =glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
						}
						recordMap.put("status", "66");//一键改
						recordMap.put("remark", "一键改");
						recordMap.put("receiver", "");
						recordMap.put("content", content);
						checkProcessDao.addProcessNew_orderNo(recordMap);
					}
				//}
				//工单检查时间
				if(!"".equals(orderNo)){
					checkOrderNum++;
					recordMap.put("order_no", orderNo);
					checkTaskDao.updateOrderCheckTime(recordMap);
				}
			}
			//插入设备详情
			int recordId = checkOrderDao.getRecordId();
			Map taskDetailEqpMap = new HashMap();
			taskDetailMap.put("TASK_ID", taskId);
			taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
			taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			taskDetailMap.put("INSPECT_OBJECT_TYPE", 0);
			taskDetailMap.put("CHECK_FLAG", "1");
			taskDetailMap.put("GLBM", "");
			taskDetailMap.put("GLMC", "");
			taskDetailMap.put("PORT_ID", "");
			taskDetailMap.put("dtsj_id", "");
			taskDetailMap.put("eqpId_port", "");
			taskDetailMap.put("eqpNo_port", "");
			taskDetailMap.put("eqpName_port", "");
			taskDetailMap.put("orderNo", "");
			taskDetailMap.put("orderMark", "");
			taskDetailMap.put("actionType", "");
			taskDetailMap.put("archive_time", "");
			checkOrderDao.saveTroubleTaskDetail(taskDetailMap);
			String detail_id = taskDetailMap.get("DETAIL_ID").toString();
			//插入设备记录
			Map eqpRecordMap = new HashMap();
			eqpRecordMap.put("recordId", recordId);	
			eqpRecordMap.put("task_id", taskId);
			eqpRecordMap.put("glbm", "");
			eqpRecordMap.put("detail_id", detail_id);
			eqpRecordMap.put("eqpId", eqpId);
			eqpRecordMap.put("eqpNo", eqpNo);
			eqpRecordMap.put("eqpName", eqpName);
			eqpRecordMap.put("eqpAddress", eqpAddress);
			eqpRecordMap.put("longitude", longitude);
			eqpRecordMap.put("latitude", latitude);
			eqpRecordMap.put("staffId", staffId);
			eqpRecordMap.put("remarks", remarks);//现场规范
			eqpRecordMap.put("port_id", "");
			eqpRecordMap.put("port_no", "");
			eqpRecordMap.put("action_type", "");
			eqpRecordMap.put("orderId", "");
			eqpRecordMap.put("orderNo", "");
			eqpRecordMap.put("isFrom", "");
			eqpRecordMap.put("port_name", "");
			eqpRecordMap.put("descript", "");//端子错误描述
			eqpRecordMap.put("isCheckOK",is_bill);//端子是否合格
			eqpRecordMap.put("portRightPosition", "");//端子编码正确位置
			eqpRecordMap.put("portIdRightPosition", "");//端子ID正确位置
			eqpRecordMap.put("eqpNo_rightPortNo", "");//设备编码正确位置
			eqpRecordMap.put("eqpId_rightPortNo", "");//设备ID正确位置
			eqpRecordMap.put("record_type", "0");
			eqpRecordMap.put("area_id", areaId);
			eqpRecordMap.put("son_area_id", sonAreaId);
			eqpRecordMap.put("changedPortId", "");//修改后端子ID
			eqpRecordMap.put("changedPortNo", "");//修改后端子编码
			eqpRecordMap.put("changedEqpId", "");//修改后设备ID
			eqpRecordMap.put("changedEqpNo", "");//修改后设备编码
			eqpRecordMap.put("checkWay", checkWay);//修改后设备编码
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(eqpRecordMap);
			//插入流程环节
			Map processMap = new HashMap();
			processMap.put("task_id", taskId);
			processMap.put("oper_staff", staffId);
			processMap.put("status", "8");
			processMap.put("remark", "检查提交");
			processMap.put("content", "端子检查合格或现场已整改，任务直接归档");
			processMap.put("receiver", "");
			checkProcessDao.addProcessNew(processMap);//无需整改直接归档
			/**
			 * 保存设备的照片信息
			 */
			if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskId);
				photoMap.put("DETAIL_ID", detail_id);
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("REMARKS", "周期性检查");
				photoMap.put("OBJECT_TYPE", "0");// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
			//更新任务表
			Map taskMap = new HashMap();
			taskMap.put("task_id", taskId);
			taskMap.put("staffId", staffId);
			taskMap.put("remarks", remarks);
			if("0".equals(is_bill)){
				taskMap.put("is_need_zg", "0");
			}else{
				taskMap.put("is_need_zg", "1");
			}
			taskMap.put("statusId", "8");
			taskMap.put("checkWay", checkWay);
			checkOrderDao.updateTaskOrder(taskMap);
			//将设备检查时间、检查数、正确数插入设备表
			Map portmap = new HashMap();
			portmap.put("areaId", areaId);
			portmap.put("sonAreaId", sonAreaId);
			portmap.put("allcount", allcount);
			portmap.put("truecount", truecount);
			portmap.put("eqpId", eqpId);
			portmap.put("eqpNo", eqpNo);
			checkOrderDao.updateEqu_time_num(portmap);
			
			for (int i = 0; i < addressArray.size(); i++) {
				int id = checkOrderDao.geteqpAddressId();
				JSONObject eqp = (JSONObject) addressArray.get(i);
				String eqpId_add = null == eqp.get("eqpId") ? "": eqp.getString("eqpId");
				String eqpNo_add = null == eqp.get("eqpNo") ? "": eqp.getString("eqpNo");
				String location_id = eqp.getString("locationId");
				String address_id = eqp.getString("addressId");
				String address_name = eqp.getString("addressName");
				String is_check_ok = eqp.getString("is_check_ok");
				String error_reason = null == eqp.get("error_reason") ? "" : eqp.getString("error_reason");
				Map adddressMap = new HashMap();
				adddressMap.put("id", id);
				adddressMap.put("phy_eqp_id", eqpId_add);
				adddressMap.put("phy_eqp_no", eqpNo_add);
				adddressMap.put("install_eqp_id", eqpId);
				adddressMap.put("location_id", location_id);
				adddressMap.put("address_id", address_id);
				adddressMap.put("address_name", address_name);
				adddressMap.put("is_check_ok", is_check_ok);
				adddressMap.put("error_reason", error_reason);
				adddressMap.put("task_id", taskId);
				adddressMap.put("create_staff", staffId);
				adddressMap.put("area_id", areaId);
				adddressMap.put("son_area_id", sonAreaId);
				checkOrderDao.insertEqpAddress(adddressMap);

			}
			//光网助手检查触发线路工单，先插入 tb_base_gwzs_lineworkorder（光网助手线路工单表），然后通过同步平台发到FTP目录下
			checkedPortIds=innerArray.size();
			checkedPortDescript=errorPortDescript;
			adressCheckCnt=addressArray.size();//覆盖地址数量
			Map lineWork=new HashMap();
			lineWork.put("gwMan", staffName);//光网助手检查人员
			lineWork.put("taskId", taskId);//任务ID
			lineWork.put("gwManAccount", staffNo);//检查人员账号
			lineWork.put("equCode", eqpNo);//设备编码
			lineWork.put("equName", eqpName);//设备名称
			lineWork.put("chekPortNum", checkedPortIds);//检查端子数量
			lineWork.put("adressCheckCnt", adressCheckCnt);//检查端子数量
			lineWork.put("gwContent", checkedPortDescript);//检查内容描述
			lineWork.put("workhours", "0");//检查内容描述
			checkOrderDao.insertLineWorkOrder(lineWork);
			
			/**
			 * 如果需要整改，生成新的任务   待办
			 */
			if("1".equals(is_bill)){
				//需整改 先获取设备照片地址
				/*String photoIds="";
				if(eqpPhotoIds.length()>0){
					 photoIds=eqpPhotoIds;
				}
				int l=photoIds.length();
				photoIds=photoIds.substring(0, l-1);
				String eqpPhotoUrl=checkOrderDao.getPhotoUrl(photoIds);*/
				//检查端子数量全部合格且为需整改，相当于只检查设备有问题，自动派发到集约化
				if(rightPortNum==innerArray.size()){
					JSONArray allOrderArray=null;
					 //生成新的任务
					 String maintor="";//需整改派到集约化，则使维护员、审核员为空
					 String auditor="";
					 //String content="生成整改工单自动派发至集约化";
					 String newTaskId = generateNewCheckTask(staffId, taskId,
							areaId, eqpId, eqpNo, eqpName, remarks, checkWay,
							sonAreaId, maintor, auditor);
					 //遍历端子信息
					 if(allOrderArray!=null&&allOrderArray.size()>0){
						 generateNewCheckRecord(staffId, areaId, remarks, longitude,
									latitude, checkWay, sonAreaId, allOrderArray,
									newTaskId);
					 }
					 /*generateNewCheckEqpDeailRecord(staffId, taskId, areaId,
							eqpId, eqpNo, eqpName, eqpAddress, eqpPhotoIds,
							remarks, longitude, latitude, checkWay, sonAreaId,
							maintor, content, newTaskId);*/
					String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
					//隐患工单自动推送至集约化
					Map iomMap=new HashMap();
					iomMap.put("sysRoute", "GWZS");
					iomMap.put("taskTypes", "6");
					iomMap.put("staffName", staffName);//检查人员姓名
					iomMap.put("taskid", newTaskId);//任务ID
					iomMap.put("TaskName", TASK_NAME);//任务ID
					iomMap.put("IOMid_numberList", "");//施工人员账号
					//iomMap.put("eqpPhotoUrl", eqpPhotoUrl);//设备照片
					iomMap.put("errorPortsArray", errorPortsArray);//端子错误信息及照片
					iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
					iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
					iomMap.put("eqpNo", eqpNo);
					iomMap.put("eqpName", eqpName);
					iomMap.put("equType", equType);
					iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
					iomMap.put("descript", errorPortDescript);//错误描述
					iomMap.put("taskCatgory", "LINE");
					iomMap.put("isEquPro", "0");
					iomMap.put("qualityInfo", check_remark);
					//autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
					Map<String,String> resultMap=autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
					 //插入设备详情，设备记录，流程详情，设备 照片
					String result_status=resultMap.get("Result_Status");
					String errorReason=resultMap.get("errorReason");
					String content="";
					if("0".equals(result_status)){
						content="工单已推送至集约化，请至集约化进行处理";
					}else{
						content="触发集约化工单失败，请在光网助手进行处理，失败原因："+errorReason;
					}
					generateNewCheckEqpDeailRecord(staffId, taskId, areaId,
							eqpId, eqpNo, eqpName, eqpAddress, eqpPhotoIds,
							remarks, longitude, latitude, checkWay, sonAreaId,
							maintor, content, newTaskId);
				}else{
					//综调
					if(csv_orderArray!=null&&csv_orderArray.size()>0){
						 //循环分组生成任务
						 JSONObject  csv_orderObject1=new JSONObject();
						 JSONObject  csv_orderObject2=new JSONObject();
						 for(int i=0;i<csv_orderArray.size();i++){
							 JSONArray sameMaintor=new JSONArray();//派发给同一个维护员，审核员暂时默认为一致，暂时不考虑
							 csv_orderObject1=csv_orderArray.getJSONObject(i);			
							 String maintor1=csv_orderObject1.getString("maintor");
							 String auditor=csv_orderObject1.getString("auditor");
							 sameMaintor.add(csv_orderObject1);
							 for(int j =i+1 ;j<csv_orderArray.size();j++){
								 csv_orderObject2=csv_orderArray.getJSONObject(j);
								 String maintor2=csv_orderObject2.getString("maintor");
								 if(maintor1.equals(maintor2)){//同一个维护员
									 sameMaintor.add(csv_orderObject2);
									 csv_orderArray.remove(j);
									 j--;
								 }
							 }
							 //生成新的任务
							 String newTaskId = generateNewCheckTask(staffId, taskId,
									areaId, eqpId, eqpNo, eqpName, remarks, checkWay,
									sonAreaId, maintor1, auditor);
							
							 //遍历端子信息
							 if(sameMaintor!=null&&sameMaintor.size()>0){
								 generateNewCheckRecord(staffId, areaId, remarks, longitude,
											latitude, checkWay, sonAreaId, sameMaintor,
											newTaskId);
							 }
							 //插入性情，设备记录，流程，设备照片关系
							 String content="生成整改工单自动派发至维护员";
							 generateNewCheckEqpDeailRecord(staffId, taskId, areaId,
									eqpId, eqpNo, eqpName, eqpAddress, eqpPhotoIds,
									remarks, longitude, latitude, checkWay, sonAreaId,
									maintor1, content, newTaskId);
						 }
					 }
					//iom
					if(iom_orderArray!=null&&iom_orderArray.size()>0){
						 //循环分组生成任务
						 JSONObject  iom_orderObject1=new JSONObject();
						 JSONObject  iom_orderObject2=new JSONObject();
						 for(int i=0;i<iom_orderArray.size();i++){
							 JSONArray sameMaintor=new JSONArray();//派发给同一个维护员，审核员暂时默认为一致，暂时不考虑
							 iom_orderObject1=iom_orderArray.getJSONObject(i);			
							 String maintor1=iom_orderObject1.getString("maintor");
							 String auditor=iom_orderObject1.getString("auditor");
							 sameMaintor.add(iom_orderObject1);
							 for(int j =i+1 ;j<iom_orderArray.size();j++){
								 iom_orderObject2=iom_orderArray.getJSONObject(j);
								 String maintor2=iom_orderObject2.getString("maintor");
								 if(maintor1.equals(maintor2)){//同一个维护员
									 sameMaintor.add(iom_orderObject2);
									 iom_orderArray.remove(j);
									 j--;
								 }
							 }
							 errorPortIds="";
							 errorPortDescript="";
							 String ischeckok="";
							 String eqpNo_port="";
							 String portNo="";
							 String reason="";
							 String portId="";
							 for(int m=0;i<sameMaintor.size();m++){
								 ischeckok=sameMaintor.getJSONObject(i).getString("isCheckOK");
								 portId=sameMaintor.getJSONObject(i).getString("portId");
								 portNo=sameMaintor.getJSONObject(i).getString("portNo");
								 reason=sameMaintor.getJSONObject(i).getString("reason");
								 eqpNo_port=sameMaintor.getJSONObject(i).getString("eqpNo_port");
								 if(errorPortIds.length()>0){
									  errorPortIds=errorPortIds+","+portId;
									  errorPortDescript=errorPortDescript+";"+eqpNo_port+","+portNo+","+reason;;
								 }else{
									  errorPortIds=portId;
									  errorPortDescript=eqpNo_port+","+portNo+","+reason;;
								 }
							 }
							 //生成新的任务
							 maintor1="";
							 auditor="";
							 String newTaskId = generateNewCheckTask(staffId, taskId,
									areaId, eqpId, eqpNo, eqpName, remarks, checkWay,
									sonAreaId, maintor1, auditor);
							 //遍历端子信息
							 if(sameMaintor!=null&&sameMaintor.size()>0){
								 generateNewCheckRecord(staffId, areaId, remarks, longitude,
											latitude, checkWay, sonAreaId, sameMaintor,
											newTaskId);
							 }
							 //插入性情，设备记录，流程，设备照片关系
							 //String content="生成整改工单自动派发至集约化";
							/* generateNewCheckEqpDeailRecord(staffId, taskId, areaId,
									eqpId, eqpNo, eqpName, eqpAddress, eqpPhotoIds,
									remarks, longitude, latitude, checkWay, sonAreaId,
									maintor1, content, newTaskId);*/
							 String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
							//隐患工单自动推送至集约化
							Map iomMap=new HashMap();
							iomMap.put("sysRoute", "GWZS");
							iomMap.put("taskTypes", "6");
							iomMap.put("staffName", staffName);//检查人员姓名
							iomMap.put("taskid", newTaskId);//任务ID
							iomMap.put("TaskName", TASK_NAME);//任务ID
							iomMap.put("IOMid_numberList", "");//施工人员账号
							iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
							iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
							iomMap.put("eqpNo", eqpNo);
							iomMap.put("eqpName", eqpName);
							//iomMap.put("eqpPhotoUrl", eqpPhotoUrl);
							iomMap.put("errorPortsArray", errorPortsArray);
							iomMap.put("equType", equType);
							iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
							iomMap.put("descript", errorPortDescript);//错误描述
							iomMap.put("taskCatgory", "IOM");
							iomMap.put("isEquPro", "1");
							iomMap.put("qualityInfo", check_remark);
							//autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
							Map<String,String> resultMap=autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
							 //插入设备详情，设备记录，流程详情，设备 照片
							String result_status=resultMap.get("Result_Status");
							String errorReason=resultMap.get("errorReason");
							String content="";
							if("0".equals(result_status)){
								content="工单已推送至集约化，请至集约化进行处理";
							}else{
								content="触发集约化工单失败，请在光网助手进行处理，失败原因："+errorReason;
							}
							 generateNewCheckEqpDeailRecord(staffId, taskId, areaId,
										eqpId, eqpNo, eqpName, eqpAddress, eqpPhotoIds,
										remarks, longitude, latitude, checkWay, sonAreaId,
										maintor1, content, newTaskId);
						 }
					 }
					//既未匹配上综调，又未匹配上iom
					 if(no_csv_iom_orderArray!=null&&no_csv_iom_orderArray.size()>0){
						 //既未匹配到综调又未匹配到IOM的端子分别派发，如果端子属于分光器并且是以F开头，就留在光网助手系统，派给区级管理员（默认接单岗）
						 //其他的均派到集约化
						 JSONArray inner_orderArray=new JSONArray();//留在光网助手
						 JSONArray outer_orderArray=new JSONArray();//自动推送到集约化
						 String res_type_id="";
						 String glbm="";
						 JSONObject portObject=null;
						 for(int i=0;i<no_csv_iom_orderArray.size();i++){
							 portObject= no_csv_iom_orderArray.getJSONObject(i);
							 res_type_id=portObject.getString("eqpTypeId_port");
							 glbm=portObject.getString("glbm");
							 if("2530".equals(res_type_id)&&glbm.startsWith("F")){
								 inner_orderArray.add(portObject);
							 }else{
								 outer_orderArray.add(portObject);
							 }
						 }
						 if(inner_orderArray!=null&&inner_orderArray.size()>0){
							//通过设备所在的区域ID去查询区级管理员，将任务派给区级管理员，维护员为区级管理员，审核员也为区级管理员
							 Map<String, String> sonAreaAdmin= checkOrderDao.getSonAreaAdmin(sonAreaId);
							 String maintor= null==sonAreaAdmin.get("STAFF_ID")?"":sonAreaAdmin.get("STAFF_ID");//维护员
							 String auditor= null==sonAreaAdmin.get("STAFF_ID")?"":sonAreaAdmin.get("STAFF_ID");//审核员
							 String content="生成整改工单自动派发至接单岗";
							 String newTaskId = generateNewCheckTask(staffId, taskId,
									areaId, eqpId, eqpNo, eqpName, remarks, checkWay,
									sonAreaId, maintor, auditor);
							 //遍历端子信息
							 generateNewCheckRecord(staffId, areaId, remarks, longitude,
										latitude, checkWay, sonAreaId, inner_orderArray,
										newTaskId);
							 //插入性情，设备记录，流程，设备照片关系
							 generateNewCheckEqpDeailRecord(staffId, taskId, areaId,
									eqpId, eqpNo, eqpName, eqpAddress, eqpPhotoIds,
									remarks, longitude, latitude, checkWay, sonAreaId,
									maintor, content, newTaskId);
						 }
						 if(outer_orderArray!=null&&outer_orderArray.size()>0){
							//生成新的任务
							 errorPortIds="";
							 errorPortDescript="";
							 String ischeckok="";
							 String eqpNo_port="";
							 String portNo="";
							 String reason="";
							 String portId="";
							 for(int i=0;i<outer_orderArray.size();i++){
								 ischeckok=outer_orderArray.getJSONObject(i).getString("isCheckOK");
								 portId=outer_orderArray.getJSONObject(i).getString("portId");
								 portNo=outer_orderArray.getJSONObject(i).getString("portNo");
								 reason=outer_orderArray.getJSONObject(i).getString("reason");
								 eqpNo_port=outer_orderArray.getJSONObject(i).getString("eqpNo_port");
								 if(errorPortIds.length()>0){
									  errorPortIds=errorPortIds+","+portId;
									  errorPortDescript=errorPortDescript+";"+eqpNo_port+","+portNo+","+reason;;
								 }else{
									  errorPortIds=portId;
									  errorPortDescript=eqpNo_port+","+portNo+","+reason;;
								 }
							 }
							 String maintor="";//需整改派到集约化，则使维护员、审核员为空
							 String auditor="";
							 //String content="生成整改工单自动派发至集约化";
							 String newTaskId = generateNewCheckTask(staffId, taskId,
									areaId, eqpId, eqpNo, eqpName, remarks, checkWay,
									sonAreaId, maintor, auditor);
							 //遍历端子信息
							 generateNewCheckRecord(staffId, areaId, remarks, longitude,
										latitude, checkWay, sonAreaId, outer_orderArray,
										newTaskId);
							 //插入性情，设备记录，流程，设备照片关系
							 /*generateNewCheckEqpDeailRecord(staffId, taskId, areaId,
									eqpId, eqpNo, eqpName, eqpAddress, eqpPhotoIds,
									remarks, longitude, latitude, checkWay, sonAreaId,
									maintor, content, newTaskId);*/
							 String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
							//隐患工单自动推送至集约化
							Map iomMap=new HashMap();
							iomMap.put("sysRoute", "GWZS");
							iomMap.put("taskTypes", "6");
							iomMap.put("staffName", staffName);//检查人员姓名
							iomMap.put("taskid", newTaskId);//任务ID
							iomMap.put("TaskName", TASK_NAME);//任务ID
							iomMap.put("IOMid_numberList", "");//施工人员账号
							iomMap.put("contract_persion_nos", contractPersonNo);//承包人账号
							iomMap.put("PositionPersonsList", douDiGangNo);//兜底岗账号
							iomMap.put("eqpNo", eqpNo);
							iomMap.put("eqpName", eqpName);
							//iomMap.put("eqpPhotoUrl", eqpPhotoUrl);
							iomMap.put("errorPortsArray", errorPortsArray);
							iomMap.put("equType", equType);
							iomMap.put("IOMportIdList", errorPortIds);//错误端子列表
							iomMap.put("descript", errorPortDescript);//错误描述
							iomMap.put("taskCatgory", "LINE");
							iomMap.put("isEquPro", "1");
							iomMap.put("qualityInfo", check_remark);
							//autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
							Map<String,String> resultMap=autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
							 //插入设备详情，设备记录，流程详情，设备 照片
							String result_status=resultMap.get("Result_Status");
							String errorReason=resultMap.get("errorReason");
							String content="";
							if("0".equals(result_status)){
								content="工单已推送至集约化，请至集约化进行处理";
							}else{
								content="触发集约化工单失败，请在光网助手进行处理，失败原因："+errorReason;
							}
							generateNewCheckEqpDeailRecord(staffId, taskId, areaId,
									eqpId, eqpNo, eqpName, eqpAddress, eqpPhotoIds,
									remarks, longitude, latitude, checkWay, sonAreaId,
									maintor, content, newTaskId);
						 }
					 }
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "工单提交失败");
		}
		return result.toString();
	
	}

	public void generateNewCheckEqpDeailRecord(String staffId, String taskId,
			String areaId, String eqpId, String eqpNo, String eqpName,
			String eqpAddress, String eqpPhotoIds, String remarks,
			String longitude, String latitude, String checkWay,
			String sonAreaId, String maintor, String content, String newTaskId) {
		//插入设备详情
		int eqpRecordId = checkOrderDao.getRecordId();
		Map eqpDetailMap = new HashMap();
		eqpDetailMap.put("TASK_ID", newTaskId);
		eqpDetailMap.put("INSPECT_OBJECT_ID", eqpRecordId);
		eqpDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
		eqpDetailMap.put("INSPECT_OBJECT_TYPE", 0);
		eqpDetailMap.put("CHECK_FLAG", "1");
		eqpDetailMap.put("GLBM", "");
		eqpDetailMap.put("GLMC", "");
		eqpDetailMap.put("PORT_ID", "");
		eqpDetailMap.put("dtsj_id", "");
		eqpDetailMap.put("eqpId_port", "");
		eqpDetailMap.put("eqpNo_port", "");
		eqpDetailMap.put("eqpName_port", "");
		eqpDetailMap.put("orderNo", "");
		eqpDetailMap.put("orderMark", "");
		eqpDetailMap.put("actionType", "");
		eqpDetailMap.put("archive_time", "");
		checkOrderDao.saveTroubleTaskDetail(eqpDetailMap);
		String eqp_detail_id = eqpDetailMap.get("DETAIL_ID").toString();
		//插入设备记录
		Map eqpRecordMapNew = new HashMap();
		eqpRecordMapNew.put("recordId", eqpRecordId);	
		eqpRecordMapNew.put("task_id", newTaskId);
		eqpRecordMapNew.put("glbm", "");
		eqpRecordMapNew.put("detail_id", eqp_detail_id);
		eqpRecordMapNew.put("eqpId", eqpId);
		eqpRecordMapNew.put("eqpNo", eqpNo);
		eqpRecordMapNew.put("eqpName", eqpName);
		eqpRecordMapNew.put("eqpAddress", eqpAddress);
		eqpRecordMapNew.put("longitude", longitude);
		eqpRecordMapNew.put("latitude", latitude);
		eqpRecordMapNew.put("staffId", staffId);
		eqpRecordMapNew.put("remarks", remarks);//现场规范
		eqpRecordMapNew.put("port_id", "");
		eqpRecordMapNew.put("port_no", "");
		eqpRecordMapNew.put("action_type", "");
		eqpRecordMapNew.put("orderId", "");
		eqpRecordMapNew.put("orderNo", "");
		eqpRecordMapNew.put("isFrom", "");
		eqpRecordMapNew.put("port_name", "");
		eqpRecordMapNew.put("descript", "");//端子错误描述
		eqpRecordMapNew.put("isCheckOK", "1");//端子是否合格
		eqpRecordMapNew.put("portRightPosition", "");//端子编码正确位置
		eqpRecordMapNew.put("portIdRightPosition", "");//端子ID正确位置
		eqpRecordMapNew.put("eqpNo_rightPortNo", "");//设备编码正确位置
		eqpRecordMapNew.put("eqpId_rightPortNo", "");//设备ID正确位置
		eqpRecordMapNew.put("record_type", "0");
		eqpRecordMapNew.put("area_id", areaId);
		eqpRecordMapNew.put("son_area_id", sonAreaId);
		eqpRecordMapNew.put("changedPortId", "");//修改后端子ID
		eqpRecordMapNew.put("changedPortNo", "");//修改后端子编码
		eqpRecordMapNew.put("changedEqpId", "");//修改后设备ID
		eqpRecordMapNew.put("changedEqpNo", "");//修改后设备编码
		eqpRecordMapNew.put("checkWay", checkWay);
		//插入记录表
		checkOrderDao.insertEqpRecordNewYy(eqpRecordMapNew);
		//整改流程在插入记录表之前，先通过taskid找到老的流程，然后将其插入新的流程表
		List<Map<String, String>> processlList=checkOrderDao.queryProcessByOldTaskId(taskId);
		if(processlList !=null && processlList.size()>0){				
			for(Map<String, String> pro:processlList){
				pro.put("TASK_ID", newTaskId);
				checkProcessDao.addProcessNew_orderNo(pro);
			}
		}
		//插入流程环节
		Map processMap2 = new HashMap();
		processMap2.put("task_id", newTaskId);
		processMap2.put("oper_staff", staffId);
		processMap2.put("status", "6");
		processMap2.put("remark", "检查提交");
		processMap2.put("content", content);
		processMap2.put("receiver", maintor);
		checkProcessDao.addProcessNew(processMap2);

		/**
		 * 保存设备的照片信息
		 */
		if (!"".equals(eqpPhotoIds) && eqpPhotoIds != null) {
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", newTaskId);
			photoMap.put("DETAIL_ID", eqp_detail_id);
			photoMap.put("OBJECT_ID", eqpRecordId);
			photoMap.put("REMARKS", "周期性检查");
			photoMap.put("OBJECT_TYPE", "0");// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", eqpRecordId);
			String[] photos = eqpPhotoIds.split(",");
			for (String photo : photos) {
				photoMap.put("PHOTO_ID", photo);
				checkOrderDao.insertPhotoRel(photoMap);
			}
		}
	}

	public void generateNewCheckRecord(String staffId, String areaId,
			String remarks, String longitude, String latitude, String checkWay,
			String sonAreaId, JSONArray allOrderArray, String newTaskId) {
		Map portRecordMap=new HashMap();
		Map portDetailMap=new HashMap();
		for(int m=0;m<allOrderArray.size();m++){
			JSONObject portObject= allOrderArray.getJSONObject(m);
			//将端子记录插入任务详情表
			int newRecordId = checkOrderDao.getRecordId();
			portDetailMap.put("TASK_ID", newTaskId);
			portDetailMap.put("INSPECT_OBJECT_ID", newRecordId);
			portDetailMap.put("INSPECT_OBJECT_NO", portObject.get("portNo"));
			portDetailMap.put("INSPECT_OBJECT_TYPE", 1);
			portDetailMap.put("CHECK_FLAG", 1);// 0派单的，1检查的
			portDetailMap.put("GLBM", portObject.get("glbm"));
			portDetailMap.put("GLMC", portObject.get("glmc"));
			portDetailMap.put("PORT_ID", portObject.get("portId"));
			portDetailMap.put("dtsj_id", "");
			portDetailMap.put("eqpId_port", portObject.get("eqpId_port"));
			portDetailMap.put("eqpNo_port", portObject.get("eqpNo_port"));
			portDetailMap.put("eqpName_port", portObject.get("eqpName_port"));
			portDetailMap.put("orderNo", portObject.get("orderNo"));
			portDetailMap.put("orderMark", portObject.get("orderMark"));
			portDetailMap.put("actionType", portObject.get("actionType"));
			portDetailMap.put("archive_time", portObject.get("archive_time"));
			checkOrderDao.saveTroubleTaskDetail(portDetailMap);
			//将端子记录插入记录表
			String newDetailId = portDetailMap.get("DETAIL_ID").toString();						
			portRecordMap.put("recordId", newRecordId);	
			portRecordMap.put("task_id", newTaskId);
			portRecordMap.put("glbm", portObject.get("glbm"));
			portRecordMap.put("detail_id", newDetailId);
			portRecordMap.put("eqpId", portObject.get("eqpId_port"));
			portRecordMap.put("eqpNo", portObject.get("eqpNo_port"));
			portRecordMap.put("eqpName", portObject.get("eqpName_port"));
			portRecordMap.put("eqpAddress", portObject.get("eqpAddress"));
			portRecordMap.put("longitude", longitude);
			portRecordMap.put("latitude", latitude);
			portRecordMap.put("staffId", staffId);
			portRecordMap.put("remarks", remarks);//现场规范
			portRecordMap.put("port_id", portObject.get("portId"));
			portRecordMap.put("port_no", portObject.get("portNo"));
			portRecordMap.put("action_type", portObject.get("actionType"));
			portRecordMap.put("orderId", portObject.get("orderId"));
			portRecordMap.put("orderNo", portObject.get("orderNo"));
			portRecordMap.put("isFrom", "0");//0表示综调  1 表示 iom					
			portRecordMap.put("port_name", portObject.get("portName"));
			portRecordMap.put("descript", portObject.get("reason"));//端子错误描述
			portRecordMap.put("isCheckOK", portObject.get("isCheckOK"));//端子是否合格
			portRecordMap.put("portRightPosition", portObject.get("portRightPosition"));//端子编码正确位置
			portRecordMap.put("portIdRightPosition", portObject.get("portIdRightPosition"));//端子ID正确位置
			portRecordMap.put("eqpNo_rightPortNo", portObject.get("eqpNo_rightPortNo"));//设备编码正确位置
			portRecordMap.put("eqpId_rightPortNo", portObject.get("eqpId_rightPortNo"));//设备ID正确位置
			portRecordMap.put("record_type", "0");
			portRecordMap.put("area_id", areaId);
			portRecordMap.put("son_area_id", sonAreaId);
			portRecordMap.put("changedPortId",  portObject.get("changedPortId"));//修改后端子ID
			portRecordMap.put("changedPortNo",  portObject.get("changedPortNo"));//修改后端子编码
			portRecordMap.put("changedEqpId",  portObject.get("changedEqpId"));//修改后设备ID
			portRecordMap.put("changedEqpNo", portObject.get("changedEqpNo"));//修改后设备编码
			portRecordMap.put("checkWay", checkWay);
			//插入记录表
			checkOrderDao.insertEqpRecordNewYy(portRecordMap);
			
			// 保存端子照片关系
			Map photoMap = new HashMap();
			photoMap.put("TASK_ID", newTaskId);
			photoMap.put("DETAIL_ID", newDetailId);
			photoMap.put("OBJECT_ID", newRecordId);
			photoMap.put("REMARKS", "周期性检查");
			photoMap.put("OBJECT_TYPE", 0);// 0，周期性任务，1：隐患上报工单，2,回单操作
			photoMap.put("RECORD_ID", newRecordId);
			if (!"".equals(portObject.getString("photoIds"))) {
				String[] photos = portObject.getString("photoIds").split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
		}
	}

	public String generateNewCheckTask(String staffId, String taskId,
			String areaId, String eqpId, String eqpNo, String eqpName,
			String remarks, String checkWay, String sonAreaId, String maintor,
			String auditor) {
		//生成整改任务
		Map troubleTaskMap = new HashMap();
		String TASK_NO=eqpNo+ "_"+ DateUtil.getDate("yyyyMMdd");
		String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
		troubleTaskMap.put("TASK_NO",TASK_NO);
		troubleTaskMap.put("TASK_NAME",TASK_NAME);
		troubleTaskMap.put("TASK_TYPE", 3);// 问题上报
		troubleTaskMap.put("STATUS_ID", 6);//待回单状态
		troubleTaskMap.put("INSPECTOR",staffId);
		troubleTaskMap.put("CREATE_STAFF",staffId);
		troubleTaskMap.put("SON_AREA_ID",sonAreaId);
		troubleTaskMap.put("AREA_ID",areaId);
		troubleTaskMap.put("ENABLE", "0");// 如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）														
		troubleTaskMap.put("REMARK",remarks);
		troubleTaskMap.put("INFO", "");
		troubleTaskMap.put("NO_EQPNO_FLAG","0");// 无编码上报
		troubleTaskMap.put("OLD_TASK_ID",taskId);// 老的task_id
		troubleTaskMap.put("IS_NEED_ZG","1");// 是否整改,1需要整改
		troubleTaskMap.put("SBID", eqpId);// 设备id
		troubleTaskMap.put("MAINTOR",maintor);// 维护员
		troubleTaskMap.put("auditor",auditor);// 审核员
		troubleTaskMap.put("checkWay",checkWay);//检查方式
		checkOrderDao.saveTroubleTaskNew(troubleTaskMap);
		String newTaskId = troubleTaskMap.get("TASK_ID").toString();
		logger.info("【需要整改添加一张新的工单taskId】"+ newTaskId);
		return newTaskId;
	}
}