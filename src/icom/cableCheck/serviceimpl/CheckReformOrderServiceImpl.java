package icom.cableCheck.serviceimpl;

import icom.cableCheck.dao.CheckPortDao;
import icom.cableCheck.dao.CheckReformOrderDao;
import icom.cableCheck.service.CheckReformOrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import util.page.Query;

import com.cableCheck.dao.CableMyTaskDao;

@Service
@SuppressWarnings("all")
public class CheckReformOrderServiceImpl implements CheckReformOrderService {

	Logger logger = Logger.getLogger(CheckReformOrderServiceImpl.class);

	@Autowired
	private CheckReformOrderDao checkReformOrderDao;
	@Autowired
	private CableMyTaskDao cableMyTaskDao;
	@Autowired
	private CheckPortDao checkPortDao;

	@Override
	public String getReformOrder(String jsonStr) {

		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			/**
			 * 传入的参数
			 */
			JSONObject json = JSONObject.fromObject(jsonStr);
			String staffId = json.getString("staffId");// 用户id
			String terminalType = json.getString("terminalType");// 设备类型
			String sn = json.getString("sn");
			String longitude = json.getString("longitude");// 经度
			String latitude = json.getString("latitude");// 纬度
			String taskId = json.getString("taskId");
			String rwmxId = json.getString("rwmxId");
			String areaId = json.getString("areaId");
			/**
			 * 返回的参数
			 */
			result.put("latitude", latitude);
			result.put("longitude", longitude);
			result.put("rwmxId", "");
			result.put("taskId", taskId);

			Map zgmap = checkReformOrderDao.getzgMessageByTaskId(taskId);//维护人员从第二次去维护
			if (zgmap != null && (!zgmap.isEmpty())) {
				String eqpId = null == zgmap.get("EQP_ID") ? "" : zgmap.get(
						"EQP_ID").toString();
				String eqpNo = null == zgmap.get("EQP_NO") ? "" : zgmap.get(
						"EQP_NO").toString();
				String eqpName = null == zgmap.get("EQP_NAME") ? "" : zgmap
						.get("EQP_NAME").toString();
				String eqpAddress = null == zgmap.get("EQPADDRESS") ? ""
						: zgmap.get("EQPADDRESS").toString();
				String data_source = zgmap.get("TASK_TYPE").toString();
				String remarks = null == zgmap.get("REMARK") ? "" : zgmap.get(
						"REMARK").toString();//维护人员回单备注
				String shyj = null == zgmap.get("SHYJ") ? "" : zgmap
						.get("SHYJ").toString();//审核意见
				String info = null == zgmap.get("INFO") ? "" : zgmap
						.get("INFO").toString();//整改要求
				String comments = null == zgmap.get("COMMENTS") ? "" : zgmap
						.get("COMMENTS").toString();
				String create_staff = null == zgmap.get("CREATE_STAFF") ? ""
						: zgmap.get("CREATE_STAFF").toString();
				String create_time = null == zgmap.get("CREATE_TIME") ? ""
						: zgmap.get("CREATE_TIME").toString();
				result.put("eqpId", eqpId);
				result.put("eqpNo", eqpNo);
				result.put("eqpName", eqpName);
				result.put("eqpAddress", eqpAddress);
				result.put("data_source", data_source);
				result.put("remarks", remarks);
				result.put("info", info);
				result.put("shyj", shyj);
				result.put("create_staff", create_staff);
				result.put("create_time", create_time);
				Map zgparamMap = new HashMap<String, Object>();
				zgparamMap.put("taskId", taskId);
				String zgphotoUrl = "";
				List<Map<String, Object>> eqpzgPhotoList = checkReformOrderDao
						.getzgEqpPhoto(zgparamMap);
				if (eqpzgPhotoList.size() > 0 && eqpzgPhotoList != null) {
					for (Map<String, Object> eqpPhotoMap : eqpzgPhotoList) {
						zgphotoUrl += null == eqpPhotoMap.get("PHOTO_PATH") ? ""
								: eqpPhotoMap.get("PHOTO_PATH").toString()
										+ ",";
					}
					zgphotoUrl = zgphotoUrl.substring(0,
							zgphotoUrl.length() - 1);
					result.put("photoUrl", zgphotoUrl);
				} else {
					result.put("photoUrl", "");
				}

			} else {
				Map map = checkReformOrderDao.getMessageByTaskId(taskId);//维护人员第一次去维护
				String eqpId = null == map.get("EQP_ID") ? "" : map.get(
						"EQP_ID").toString();
				String eqpNo = null == map.get("EQP_NO") ? "" : map.get(
						"EQP_NO").toString();
				String eqpName = null == map.get("EQP_NAME") ? "" : map.get(
						"EQP_NAME").toString();
				String eqpAddress = null == map.get("EQPADDRESS") ? "" : map
						.get("EQPADDRESS").toString();
				String data_source = map.get("TASK_TYPE").toString();
				String remarks = null == map.get("REMARK") ? "" : map.get(
						"REMARK").toString();//检查任务时现场规范
				String info = null == map.get("INFO") ? "" : map.get("INFO")
						.toString();//整改要求
				String comments = null == map.get("COMMENTS") ? "" : map.get(
						"COMMENTS").toString();
				result.put("eqpId", eqpId);
				result.put("eqpNo", eqpNo);
				result.put("eqpName", eqpName);
				result.put("eqpAddress", eqpAddress);
				result.put("data_source", data_source);
				result.put("remarks", remarks);
				result.put("info", info);
				result.put("comments", comments);
				result.put("operType", "1");// 注：暂时写成1
				Map paramMap = new HashMap<String, Object>();
				paramMap.put("inspectObjectType", "0");
				paramMap.put("taskId", taskId);
				String photoUrl = "";
				List<Map<String, Object>> eqpPhotoList = checkReformOrderDao
						.getEqpOrPortPhoto(paramMap);
				if (eqpPhotoList.size() > 0 && eqpPhotoList != null) {
					for (Map<String, Object> eqpPhotoMap : eqpPhotoList) {
						photoUrl += null == eqpPhotoMap.get("PHOTO_PATH") ? ""
								: eqpPhotoMap.get("PHOTO_PATH").toString()
										+ ",";
					}
					photoUrl = photoUrl.substring(0, photoUrl.length() - 1);
					result.put("photoUrl", photoUrl);
				} else {
					result.put("photoUrl", "");
				}
			}
			

			/**
			 * 端子信息[]
			 */
			
				JSONArray jsonArr1 = new JSONArray();
				Map portParamMap = new HashMap<String, String>();
				List<Map<String, Object>>  staffRole =cableMyTaskDao.getRole(staffId);
				
				String company = null ;
				for (Map<String, Object> a : staffRole) {
					String roleId = String.valueOf(a.get("ROLE_ID"));
					if ("369".equals(roleId)) {
						company = "0";
						break;
					}
					if("370".equals(roleId)){
						company = "1";
						break;
					}
					if("387".equals(roleId)){
						company = "2";
						break;
					}
					if("393".equals(roleId)){
						company = "3";
						break;
					}
					if("395".equals(roleId)){
						company = "4";
						break;
					}
					if("392".equals(roleId)){
						company = "5";
						break;
					}
				}
				portParamMap.put("taskId", taskId);
				portParamMap.put("inspectObjectType", "1");// 1端子
				portParamMap.put("areaId", areaId);
				portParamMap.put("longitude", longitude);
				portParamMap.put("latitude", latitude);
				portParamMap.put("company", company);
				JSONArray glPath = new JSONArray();
				JSONObject glObject = new JSONObject();

				List<Map<String, Object>> portlist=new ArrayList<Map<String,Object>>();
				
				if (zgmap != null && (!zgmap.isEmpty())) {//第二次去维护
					portlist = checkReformOrderDao.getPortMessageZg(portParamMap);
				}else{//第一次去维护
					portlist = checkReformOrderDao.getPortMessage(portParamMap);
				}
				
				
				if (portlist.size() > 0 && portlist != null) {
					List<Map> li=null;
					for (Map<String, Object> port : portlist) {
						JSONObject portObject = new JSONObject();
						String eqpId= port.get("EQP_ID").toString();
						String PORT_ID= port.get("PORT_ID").toString();
						portObject.put("eqpId",eqpId);
						portObject.put("record_type", port.get("RECORD_TYPE"));
						portObject.put("eqpNo", port.get("EQP_NO"));
						portObject.put("eqpName", port.get("EQP_NAME"));
						portObject.put("eqp_type_id", port.get("RES_TYPE_ID"));
						portObject.put("eqp_type", port.get("RES_TYPE"));
						portObject.put("type", port.get("COMPANY"));
						portObject.put("portId", PORT_ID);
						portObject.put("portNo", port.get("PORT_NO"));
						portObject.put("portName", null == port
								.get("PORT_NAME") ? "" : port.get("PORT_NAME"));
						// portObject.put("ossglbh", ""); //实时查询OSS系统的光路编号
						portObject.put("reason",
								null == port.get("DESCRIPT") ? "" : port
										.get("DESCRIPT"));
						/*portObject.put("reason2",
								null == port.get("PORT_INFO") ? "" : port
										.get("PORT_INFO"));*/
						portObject.put("isCheckOK", port.get("ISCHECKOK"));
						portObject.put("glbm", null == port.get("GLBM") ? ""
								: port.get("GLBM"));
						portObject.put("glmc", null == port.get("GLMC") ? ""
								: port.get("GLMC"));
						portObject.put("isH", null == port.get("ISH") ? ""
								: port.get("ISH"));
						//正确端子ID和正确端子编码
						String portNo_true= null==port.get("PORTRIGHTPOSITION") ? "": port.get("PORTRIGHTPOSITION").toString();
						String portId_true= null==port.get("PORTIDRIGHTPOSITION")?"":port.get("PORTIDRIGHTPOSITION").toString();
						//修改后端子ID和修改后端子编码
						String changePortId= null==port.get("CHANGEPORTID") ? "": port.get("CHANGEPORTID").toString();
						String changePortNo= null==port.get("CHANGEPORTNO")?"":port.get("CHANGEPORTNO").toString();
						if("".equals(portNo_true)){
							portObject.put("portNo_true","");
							portObject.put("portId_true","");
						}else{
							portObject.put("portNo_true",portNo_true);
							portObject.put("portId_true",portId_true);
							
						}
						if(!"".equals(changePortNo)){
							portObject.put("portId", changePortId);
							portObject.put("portNo", changePortNo);
						}
						
						portObject.put("eqpId_true", null == port.get("EQPID_NEW") ? ""
								: port.get("EQPID_NEW"));//正确设备ID
						portObject.put("eqpNo_true", null == port.get("EQPNO_NEW") ? ""
								: port.get("EQPNO_NEW"));//正确设备编码
						portObject.put("changeEqpId", null == port.get("CHANGEEQPID") ? ""
								: port.get("CHANGEEQPID"));//修改后设备ID
						portObject.put("changeEqpNo", null == port.get("CHANGEEQPNO") ? ""
								: port.get("CHANGEEQPNO"));//修改后设备编码
						
						portObject.put("staff_name", port.get("STAFF_NAME"));// 检查人
						portObject.put("create_time", port.get("CREATE_TIME"));
						portObject.put("record_id", port.get("RECORD_ID"));
						Map GDmap = new HashMap<String, String>();
						GDmap.put("EQP_ID", port.get("EQP_ID"));
						GDmap.put("PORT_ID", port.get("PORT_ID"));
						List<Map> gdList = checkReformOrderDao.getGDList(GDmap);
						glPath.clear();
						for (Map gd : gdList) {
							if(li!=null&&li.size()>0){
								li.clear();
							}
							String glbh=String.valueOf(gd.get("GLBH")).trim();
							glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
							glObject.put("glbh", glbh);
							glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
							glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
						    li=this.getSgGxInfo(areaId, glbh);
							Map map=li.get(0);
							String sggh=(String) map.get("sggh");
							String pzgh=(String) map.get("pzgh");
							String gxgh=(String) map.get("gxgh");
							glObject.put("sggh", sggh);
							glObject.put("pzgh", pzgh);
							glObject.put("gxgh", gxgh);													
//							glObject.put("glmc", "");
//							glObject.put("gdbh", "");
//							glObject.put("sggh", "");
//							glObject.put("pzgh", "");
//							glObject.put("gxgh","");
//							glObject.put("glxz", "");
//							glObject.put("bdsj", "");

							glPath.add(glObject);
						}
						
						
						portObject.put("portInfos", glPath);
						jsonArr1.add(portObject);

					}

				}
				result.put("ports", jsonArr1);
			

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("error", e.toString());
			result.put("desc", "查看整改工单失败，请联系管理员。");
			logger.info(e.toString());
		}
		return result.toString();
	}

	@Override
	public String getReformDzOrder(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			/**
			 * 传入的参数
			 */
			JSONObject json = JSONObject.fromObject(jsonStr);
			String staffId = json.getString("staffId");// 用户id
			String terminalType = json.getString("terminalType");// 设备类型
			String sn = json.getString("sn");
			String longitude = json.getString("longitude");// 经度
			String latitude = json.getString("latitude");// 纬度
			String taskId = json.getString("taskId");
			String rwmxId = json.getString("rwmxId");
			String areaId = json.getString("areaId");
			String portId = json.getString("portId");
			String GLBH = json.getString("glbm");
			
			
			Map param = new HashMap();
			param.put("DZID", portId);
			param.put("GLBH", GLBH);
			param.put("area_id", areaId);

			/**
			 * 返回的参数
			 */
			result.put("latitude", latitude);
			result.put("longitude", longitude);
			result.put("rwmxId", "");
			result.put("taskId", taskId);
			JSONArray jsonArr = new JSONArray();
			
			Map portParamMap = new HashMap<String, String>();

			portParamMap.put("taskId", taskId);
			portParamMap.put("pordId", portId);
			portParamMap.put("inspectObjectType", "1");// 1端子
			portParamMap.put("areaId", areaId);
			portParamMap.put("longitude", longitude);
			portParamMap.put("latitude", latitude);

			List<Map<String, Object>> portlist = checkReformOrderDao
					.getzgPortMessage(portParamMap);
			if (portlist.size() > 0 && portlist != null) {
				for (Map<String, Object> port : portlist) {
					JSONObject portObject1 = new JSONObject();
					portObject1.put("record_type", port.get("RECORD_TYPE"));
					portObject1.put("eqpId", port.get("EQP_ID"));
					portObject1.put("eqpNo", port.get("EQP_NO"));
					portObject1.put("eqpName", port.get("EQP_NAME"));
					portObject1.put("eqp_type_id", port.get("RES_TYPE_ID"));
					portObject1.put("eqp_type", port.get("RES_TYPE"));
					portObject1.put("port_info", null == port.get("PORT_INFO") ? ""
							: port.get("PORT_INFO"));
					portObject1.put("portId", port.get("PORT_ID"));
					portObject1.put("portNo", port.get("PORT_NO"));
					portObject1.put("portName",
							null == port.get("PORT_NAME") ? "" : port
									.get("PORT_NAME"));
					// portObject1.put("ossglbh", ""); //实时查询OSS系统的光路编号
					portObject1.put("reason", null == port.get("DESCRIPT") ? ""
							: port.get("DESCRIPT"));
					portObject1.put("isCheckOK", port.get("ISCHECKOK"));
					portObject1.put("glbm", null == port.get("GLBM") ? ""
							: port.get("GLBM"));
					portObject1.put("isH", null == port.get("ISH") ? "" : port
							.get("ISH"));
					portObject1.put("staff_name", port.get("STAFF_NAME"));// 整改人
					portObject1.put("create_time", port.get("CREATE_TIME"));// 整改时间
					portObject1.put("record_id", port.get("RECORD_ID"));

					/**
					 * 获取端子整改照片
					 */
					String record_id = null == port.get("RECORD_ID") ? ""
							: port.get("RECORD_ID").toString();
					Map phototParamMap = new HashMap<String, Object>();
					phototParamMap.put("taskId", taskId);
					phototParamMap.put("record_id", record_id);
					phototParamMap.put("portId", portId);
					List<Map<String, Object>> portPhotoList = checkReformOrderDao
							.getzgPortPhoto(phototParamMap);
					String portPhotoUrl = "";
					if (portPhotoList.size() > 0 && portPhotoList != null) {
						for (Map<String, Object> portPhoto : portPhotoList) {
							portPhotoUrl += null == portPhoto.get("PHOTO_PATH") ? ""
									: portPhoto.get("PHOTO_PATH").toString()
											+ ",";
						}
						portPhotoUrl = portPhotoUrl.substring(0, portPhotoUrl
								.length() - 1);
						portObject1.put("photoUrl", portPhotoUrl);
					} else {
						portObject1.put("photoUrl", "");
					}
					
					
					jsonArr.add(portObject1);

				}
			}
			result.put("ports", jsonArr);

			JSONArray jsonArr1 = new JSONArray();//整改端子详细信息显示两条流程记录

			List<Map<String, Object>> portlist1 = checkReformOrderDao
					.getcheckPortMessage(portParamMap);

			if (portlist1.size() > 0 && portlist1 != null) {
				for (Map<String, Object> port : portlist1) {
					JSONObject portObject = new JSONObject();
					portObject.put("eqpId", port.get("EQP_ID"));
					portObject.put("record_type", port.get("RECORD_TYPE"));
					portObject.put("eqpNo", port.get("EQP_NO"));
					portObject.put("eqpName", port.get("EQP_NAME"));
					portObject.put("eqp_type_id", port.get("RES_TYPE_ID"));
					portObject.put("eqp_type", port.get("RES_TYPE"));

					portObject.put("portId", port.get("PORT_ID"));
					portObject.put("portNo", port.get("PORT_NO"));
					portObject.put("portName",
							null == port.get("PORT_NAME") ? "" : port
									.get("PORT_NAME"));
					// portObject.put("ossglbh", ""); //实时查询OSS系统的光路编号
					portObject.put("reason", null == port.get("DESCRIPT") ? ""
							: port.get("DESCRIPT"));
					portObject.put("isCheckOK", port.get("ISCHECKOK"));
					portObject.put("glbm", null == port.get("GLBM") ? "" : port
							.get("GLBM"));
					portObject.put("isH", null == port.get("ISH") ? "" : port
							.get("ISH"));
					portObject.put("staff_name", port.get("STAFF_NAME"));// 检查人
					portObject.put("create_time", port.get("CREATE_TIME"));
					portObject.put("record_id", port.get("RECORD_ID"));
					/**
					 * 获取端子照片
					 */
					String record_id = null == port.get("RECORD_ID") ? ""
							: port.get("RECORD_ID").toString();
					Map phototParamMap = new HashMap<String, Object>();
					phototParamMap.put("taskId", taskId);
					phototParamMap.put("record_id", record_id);
					phototParamMap.put("portId", portId);
					List<Map<String, Object>> portPhotoList = checkReformOrderDao
							.getPortPhoto(phototParamMap);
					String portPhotoUrl = "";
					if (portPhotoList.size() > 0 && portPhotoList != null) {
						for (Map<String, Object> portPhoto : portPhotoList) {
							portPhotoUrl += null == portPhoto.get("PHOTO_PATH") ? ""
									: portPhoto.get("PHOTO_PATH").toString()
											+ ",";
						}
						portPhotoUrl = portPhotoUrl.substring(0, portPhotoUrl
								.length() - 1);
						portObject.put("photoUrl", portPhotoUrl);
					} else {
						portObject.put("photoUrl", "");
					}				
					jsonArr1.add(portObject);

				}

			}
			result.put("ports1", jsonArr1);
			//通过光路编号查询施工人信息和更纤信息     
			List li=new ArrayList();
			Map map=new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			if("".equals(GLBH)){				
//				port_sg_gxObject.put("sg_staffNo", "");// 施工人账号
//				port_sg_gxObject.put("sg_staffName", "");// 施工人姓名
//				port_sg_gxObject.put("sg_dealTime", "");// 施工时间
//				port_sg_gxObject.put("gx_staffNo", "");// 更纤人账号
//				port_sg_gxObject.put("gx_staffName", "");// 更纤人姓名
//				port_sg_gxObject.put("gx_dealTime", "");// 更纤时间
				map.put("sg_staffNo", "");
				map.put("sg_staffName", "");
				map.put("sg_dealTime", "");
				map.put("gx_staffNo", "");
				map.put("gx_staffName", "");
				map.put("gx_dealTime", "");
				li.add(map);	
			}else{
				Map port_sg_info = checkPortDao.getPortSgInfo(param);//获取施工信息
				Map port_gx_iom_info = checkPortDao.getPortGxInfoByIOM(param);//获取更纤信息IOM
				Map port_gx_iops_info = checkPortDao.getPortGxInfoByIOPS(param);//获取更纤信息IOPS
				
				if(!(port_sg_info==null||port_sg_info.size()==0)){
					String sg_staffNo= port_sg_info.get("REPLY_ACCT_NO")==null?"":port_sg_info.get("REPLY_ACCT_NO").toString();
					String sg_staffName= port_sg_info.get("REPLY_PTY_NM")==null?"":port_sg_info.get("REPLY_PTY_NM").toString();
					String sg_dealTime= port_sg_info.get("REPLY_DT")==null?"":port_sg_info.get("REPLY_DT").toString();					
					map.put("sg_staffNo", sg_staffNo);
					map.put("sg_staffName", sg_staffName);
					map.put("sg_dealTime", sg_dealTime);	
				}else{
					map.put("sg_staffNo", "");
					map.put("sg_staffName", "");
					map.put("sg_dealTime", "");	
				}
				//如果更纤同时为空
				if((port_gx_iom_info==null||port_gx_iom_info.size()==0)&&(port_gx_iops_info==null||port_gx_iops_info.size()==0)){
					map.put("gx_staffNo", "");
					map.put("gx_staffName", "");
					map.put("gx_dealTime", "");
				}else if(!(port_gx_iom_info==null||port_gx_iom_info.size()==0)&&!(port_gx_iops_info==null||port_gx_iops_info.size()==0)){//同时不为空
					String IOM_MF_DT= port_gx_iom_info.get("IOM_MF_DT")==null?"":port_gx_iom_info.get("IOM_MF_DT").toString();					
					String IOPS_MF_DT= port_gx_iops_info.get("IOPS_MF_DT")==null?"":port_gx_iops_info.get("IOPS_MF_DT").toString();					
					String gx_staffNo="";
					String gx_staffName="";
					String gx_dealTime="";
					if(!"".equals(IOM_MF_DT) && !"".equals(IOPS_MF_DT)){
						Date  t1 =sdf.parse(IOM_MF_DT);
						Date  t2 =sdf.parse(IOPS_MF_DT);						
						if(t1.before(t2)){
							gx_dealTime=IOPS_MF_DT;
							gx_staffNo= port_gx_iops_info.get("IOPS_MF_ACCT_NO")==null?"":port_gx_iops_info.get("IOPS_MF_ACCT_NO").toString();
							gx_staffName= port_gx_iops_info.get("IOPS_MF_PTY_NM")==null?"":port_gx_iops_info.get("IOPS_MF_PTY_NM").toString();							
						}
						map.put("gx_staffNo", gx_staffNo);
						map.put("gx_staffName", gx_staffName);
						map.put("gx_dealTime", gx_dealTime);
					}else if("".equals(IOM_MF_DT) && "".equals(IOPS_MF_DT)){
						map.put("gx_staffNo", "");
						map.put("gx_staffName", "");
						map.put("gx_dealTime", "");
					}else if((!"".equals(IOM_MF_DT))&& "".equals(IOPS_MF_DT)){
					    gx_staffNo= port_gx_iom_info.get("IOM_MF_ACCT_NO")==null?"":port_gx_iom_info.get("IOM_MF_ACCT_NO").toString();
						gx_staffName= port_gx_iom_info.get("IOM_MF_PTY_NM")==null?"":port_gx_iom_info.get("IOM_MF_PTY_NM").toString();
						map.put("gx_staffNo", gx_staffNo);
						map.put("gx_staffName", gx_staffName);
						map.put("gx_dealTime", IOM_MF_DT);
					}else{
						gx_staffNo= port_gx_iops_info.get("IOPS_MF_ACCT_NO")==null?"":port_gx_iops_info.get("IOPS_MF_ACCT_NO").toString();
						gx_staffName= port_gx_iops_info.get("IOPS_MF_PTY_NM")==null?"":port_gx_iops_info.get("IOPS_MF_PTY_NM").toString();							
						map.put("gx_staffNo", gx_staffNo);
						map.put("gx_staffName", gx_staffName);
						map.put("gx_dealTime", gx_dealTime);
					}															
				}else if(!(port_gx_iom_info==null||port_gx_iom_info.size()==0) && port_gx_iops_info==null||port_gx_iops_info.size()==0){
					String gx_dealTime= port_gx_iom_info.get("IOM_MF_DT")==null?"":port_gx_iom_info.get("IOM_MF_DT").toString();					
					String gx_staffNo= port_gx_iom_info.get("IOM_MF_ACCT_NO")==null?"":port_gx_iom_info.get("IOM_MF_ACCT_NO").toString();
					String gx_staffName= port_gx_iom_info.get("IOM_MF_PTY_NM")==null?"":port_gx_iom_info.get("IOM_MF_PTY_NM").toString();
					map.put("gx_staffNo", gx_staffNo);
					map.put("gx_staffName", gx_staffName);
					map.put("gx_dealTime", gx_dealTime);
				}else{
					String gx_dealTime= port_gx_iops_info.get("IOPS_MF_DT")==null?"":port_gx_iops_info.get("IOPS_MF_DT").toString();					
					String gx_staffNo= port_gx_iops_info.get("IOPS_MF_ACCT_NO")==null?"":port_gx_iops_info.get("IOPS_MF_ACCT_NO").toString();
					String gx_staffName= port_gx_iops_info.get("IOPS_MF_PTY_NM")==null?"":port_gx_iops_info.get("IOPS_MF_PTY_NM").toString();							
					map.put("gx_staffNo", gx_staffNo);
					map.put("gx_staffName", gx_staffName);
					map.put("gx_dealTime", gx_dealTime);
				}	
				li.add(map);
			}
			result.put("sggx", li);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("error", e.toString());
			result.put("desc", "查询端子详情失败！");
			logger.info(e.toString());
		}
		return result.toString();
	}
	
	public List<Map> getSgGxInfo(String areaId,String GLBH){
		List li=new ArrayList();
		try{
			Map param = new HashMap();
			param.put("GLBH", GLBH);
			param.put("area_id", areaId);
			Map map=new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			if(GLBH==null||"".equals(GLBH)){
				map.put("sggh","");
				map.put("pzgh", "");
				map.put("gxgh", "");
			}else{
				Map port_sg_info = checkPortDao.getPortSgInfo(param);//获取施工信息
				if(!(port_sg_info==null||port_sg_info.size()==0)){
					String REPLY_PTY_NBR= port_sg_info.get("REPLY_PTY_NBR")==null?"":port_sg_info.get("REPLY_PTY_NBR").toString();//配置工号
					map.put("sggh",REPLY_PTY_NBR);
				}else{
					map.put("sggh","");
				}
				Map port_pz_info = checkPortDao.getPortPzInfo(param);//获取配置工号
				if(!(port_pz_info==null||port_pz_info.size()==0)){
					String DPL_PTY_NBR= port_pz_info.get("DPL_PTY_NBR")==null?"":port_pz_info.get("DPL_PTY_NBR").toString();//配置工号
					map.put("pzgh",DPL_PTY_NBR);
				}else{
					map.put("pzgh","");
				}			
				Map port_gx_iom_info = checkPortDao.getPortGxInfoByIOM(param);//获取更纤信息IOM
				Map port_gx_iops_info = checkPortDao.getPortGxInfoByIOPS(param);//获取更纤信息IOPS
				if((port_gx_iom_info==null||port_gx_iom_info.size()==0)&& (!(port_gx_iops_info==null||port_gx_iops_info.size()==0))){// iom为空，iops不为空
					String IOPS_MF_PTY_NBR= port_gx_iops_info.get("IOPS_MF_PTY_NBR")==null?"":port_gx_iops_info.get("IOPS_MF_PTY_NBR").toString();//iops更纤工号
					map.put("gxgh",IOPS_MF_PTY_NBR);
				}
				if((!(port_gx_iom_info==null||port_gx_iom_info.size()==0))&&(port_gx_iops_info==null||port_gx_iops_info.size()==0)){//IOM不为空，iops为空
					String IOM_MF_PTY_NBR= port_gx_iom_info.get("IOM_MF_PTY_NBR")==null?"":port_gx_iom_info.get("IOM_MF_PTY_NBR").toString();//IOM更纤工号
					map.put("gxgh",IOM_MF_PTY_NBR);
				}
				if((port_gx_iom_info==null||port_gx_iom_info.size()==0)&&(port_gx_iops_info==null||port_gx_iops_info.size()==0)){//iom与iops均为空
					map.put("gxgh","");
				}
				if((!(port_gx_iom_info==null||port_gx_iom_info.size()==0))&&(!(port_gx_iops_info==null||port_gx_iops_info.size()==0))){//IOM与iops均不为空
					String IOM_MF_DT= port_gx_iom_info.get("IOM_MF_DT")==null?"":port_gx_iom_info.get("IOM_MF_DT").toString();					
					String IOPS_MF_DT= port_gx_iops_info.get("IOPS_MF_DT")==null?"":port_gx_iops_info.get("IOPS_MF_DT").toString();
					if("".equals(IOM_MF_DT)&&"".equals(IOPS_MF_DT)){
						map.put("gxgh","");
					}else if("".equals(IOM_MF_DT)&& (!"".equals(IOPS_MF_DT))){
						String IOPS_MF_PTY_NBR= port_gx_iops_info.get("IOPS_MF_PTY_NBR")==null?"":port_gx_iops_info.get("IOPS_MF_PTY_NBR").toString();//iops更纤工号 
						map.put("gxgh",IOPS_MF_PTY_NBR);
					}else if((!"".equals(IOM_MF_DT))&& "".equals(IOPS_MF_DT)){
						String IOM_MF_PTY_NBR= port_gx_iom_info.get("IOM_MF_PTY_NBR")==null?"":port_gx_iom_info.get("IOM_MF_PTY_NBR").toString();//IOM更纤工号
						map.put("gxgh",IOM_MF_PTY_NBR);
					}else{
						Date  t1 =sdf.parse(IOM_MF_DT);
						Date  t2 =sdf.parse(IOPS_MF_DT);
						if(t1.before(t2)){
							String IOPS_MF_PTY_NBR= port_gx_iops_info.get("IOPS_MF_PTY_NBR")==null?"":port_gx_iops_info.get("IOPS_MF_PTY_NBR").toString();
							map.put("gxgh",IOPS_MF_PTY_NBR);							
						}else{
							String IOM_MF_PTY_NBR= port_gx_iom_info.get("IOM_MF_PTY_NBR")==null?"":port_gx_iom_info.get("IOM_MF_PTY_NBR").toString();
							map.put("gxgh",IOM_MF_PTY_NBR);	
						}
					}
				}
			}
			li.add(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return li;
	}
	
	
}
