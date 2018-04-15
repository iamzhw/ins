package icom.cableCheck.serviceimpl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.dataSource.SwitchDataSourceUtil;

import com.cableCheck.dao.CheckProcessDao;
import com.cableInspection.dao.CableTaskDao;
import com.linePatrol.util.DateUtil;
import com.util.MapDistance; //import com.zbiti.provice.dblink.domain.Dblink;
import com.util.StringUtil;
//import com.zbiti.provice.util.SwitchDataSourceUtil;







import com.webservice.client.ElectronArchivesService;

import edu.emory.mathcs.backport.java.util.Collections;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import icom.cableCheck.dao.CheckOrderDao;
import icom.cableCheck.dao.CheckSpecialDao;
import icom.cableCheck.dao.CheckPortDao;
import icom.cableCheck.dao.CheckTaskDao;
import icom.cableCheck.model.Dblink;
import icom.cableCheck.service.CheckSpecialService;
import icom.cableCheck.service.CheckTaskService;
import icom.system.dao.CableInterfaceDao;
import icom.system.dao.TaskInterfaceDao;
import icom.util.Result;

@Service
@SuppressWarnings("all")
public class CheckSpecialServiceImpl implements CheckSpecialService {
	Logger logger = Logger.getLogger(CheckSpecialServiceImpl.class);
	@Resource
	ElectronArchivesService electronArchivesService;
	@Resource
	private CheckTaskDao checkTaskDao;
	@Resource
	private CheckSpecialDao checkSpecialDao;
	@Resource
	private CheckOrderDao checkOrderDao;
	@Resource
	private CheckPortDao checkPortDao;
	@Resource
	private CheckProcessDao checkProcessDao;
	
	@Resource
	private CableInterfaceDao cableInterfaceDao;
	private static final String JNDIOSS = "ossgxl";
	
	@Override
	public String getSpecialEqp(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			Map param = new HashMap();
			String eqpNo="";
			String eqpName="";
			String glbh="";
			//判断JSONObject中是否含有某个字段，因为不确定是否传入此字段
			if(json.containsKey("eqpNo")){//判断是否含有eqpNo（设备编码）此字段，有的话直接获取值
				 eqpNo = json.getString("eqpNo");// 设备编码
			 	 param.put("eqpNo", eqpNo);
			}
			if(json.containsKey("eqpName")){//判断是否含有eqpName（设备名称）此字段，有的话直接获取值
				 eqpName = json.getString("eqpName");//设备名称
				 param.put("eqpName", eqpName);
			}
			
			String staffId = json.getString("staffId");// 用户ID
			String sn = json.getString("sn");// sn
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String queryType = json.getString("queryType");// 查询类型
			String operType = json.getString("operType");//接口类型
			String currPage = json.getString("currPage");//当前页
			String pageSize = json.getString("pageSize");//每页显示大小
			String eqpType = json.getString("eqpType");//设备类型
			String distance = json.getString("distance");
			String areaId = json.getString("areaId");
			String orderByEqp = json.getString("orderByEqp");
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			String son_areaId="";
			String num=json.getString("num");
	//		String glbh = json.getString("glbh");//光路编号
//			if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("photoId", "");
//			return result.toString();
//		}
			String jndi = cableInterfaceDao.getDBblinkName(areaId);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}						
			
			List<Map> sonAreaId = checkPortDao.getParentArea(areaId);		
			param.put("staffId", staffId);
			param.put("sn", sn);
			param.put("terminalType", terminalType);
			param.put("longitude", longitude);
			param.put("latitude", latitude);
			param.put("queryType", queryType);
			param.put("operType", operType);
			param.put("currPage", currPage);
			param.put("pageSize", pageSize);
			param.put("distance", distance);
			param.put("areaId", areaId);
			param.put("eqpType", eqpType);
			param.put("sonAreaId", sonAreaId);
			param.put("orderByEqp", orderByEqp);
			param.put("jndi", jndi);
			param.put("START_TIME", startTime);
			param.put("END_TIME", endTime);
			param.put("num", num);
			
			
			JSONObject body = new JSONObject();
			JSONArray jsArr = new JSONArray();
            int totalPage = 0;
			/**
			 * 模糊查询
			 */
			if ("3".equals(operType)&&"0".equals(queryType)) {//资源整治查设备
				List<Map> eqpList = new ArrayList<Map>() ;
					eqpList = checkSpecialDao.getTaskList(param);
			
				int totalSize = eqpList.size();
				if((totalSize%Integer.parseInt(pageSize))==0){
					totalPage= totalSize/(Integer.parseInt(pageSize));
				}else 
					 totalPage=totalSize/(Integer.parseInt(pageSize))+1;
				JSONObject eqpJsonObject = new JSONObject();
				for (Map eqp : eqpList) {
					eqpJsonObject.put("eqpId", String.valueOf(eqp.get("SBID")));
					eqpJsonObject.put("eqpName", String.valueOf(eqp.get("SBMC")).trim());
					
					String sbbm = String.valueOf(eqp.get("SBBM")).trim();
					
					eqpJsonObject.put("eqpNo", sbbm);
					eqpJsonObject.put("eqp_type_id", String.valueOf(eqp.get("SBLX")).trim());
					eqpJsonObject.put("eqp_type", String.valueOf(eqp.get("RES_TYPE")).trim());
					eqpJsonObject.put("address", String.valueOf(eqp.get("ADDRESS")).trim());
					eqpJsonObject.put("WLJB", String.valueOf(eqp.get("WLJB")).trim());
					double lant1 = 0;
					double long1 = 0;
					String lon = (String) eqp.get("LONGITUDE");
					String lat = (String) eqp.get("LATITUDE");
					String lon_inspect=(String) eqp.get("LONGITUDE_INSPECT");
					String lat_inspect=(String) eqp.get("LATITUDE_INSPECT");
					
					if(lon_inspect!=null &&!"".equals(lon_inspect)){
						long1 = Double.parseDouble(lon_inspect);
						lant1 = Double.parseDouble(lat_inspect);
						eqpJsonObject.put("longitude",null==lon_inspect?"":lon_inspect);
						eqpJsonObject.put("latitude",null==lat_inspect?"":lat_inspect);
					}else{
						if(lon!=null &&!"".equals(lon)){
							long1 = Double.parseDouble(lon);
						}
						if(lat!=null &&!"".equals(lat)){
							lant1 = Double.parseDouble(lat);
						}
						eqpJsonObject.put("longitude",null==lon?"":lon);
						eqpJsonObject.put("latitude",null==lat?"":lat);
					}
					double lat2 = 0;
					double lng2 = 0;
					if(latitude!=null && !"".equals(latitude)){
						lat2 = Double.parseDouble((latitude));
					}
					if(longitude!=null&&!"".equals(longitude)){
						lng2 = Double.parseDouble((longitude));
					}
					
					double s1 = MapDistance.getDistance(lant1, long1, lat2, lng2);
					eqpJsonObject.put("distance", s1);
					eqpJsonObject.put("DATA_SOURCE",eqp.get("DATA_SOURCE"));
					eqpJsonObject.put("TASKTYPE",eqp.get("TASKTYPE"));
					String MODIFY_TIME =  null==eqp.get("MODIFY_TIME")?"":eqp.get("MODIFY_TIME").toString();
					eqpJsonObject.put("MODIFY_TIME",MODIFY_TIME);
					eqpJsonObject.put("taskId",eqp.get("TASKID"));
					eqpJsonObject.put("currPage", param.get("currPage"));
					eqpJsonObject.put("pageSize", param.get("pageSize"));
					String END_TIME =  null==eqp.get("END_TIME")?"":eqp.get("END_TIME").toString();
					eqpJsonObject.put("end_time",END_TIME);
					eqpJsonObject.put("shyj",null==eqp.get("SHYJ")?"":eqp.get("SHYJ").toString());
					eqpJsonObject.put("totalSize", String.valueOf(totalSize));
					eqpJsonObject.put("totalPage", String.valueOf(totalPage));
					eqpJsonObject.put("remark", null==eqp.get("REMARK")?"":eqp.get("REMARK").toString());
					eqpJsonObject.put("taskName", String.valueOf(eqp.get("TASKNAME")).trim());
					eqpJsonObject.put("info", null==eqp.get("INFO")?"":eqp.get("INFO").toString());
					eqpJsonObject.put("num", null==eqp.get("NUM")?"":eqp.get("NUM").toString());
					jsArr.add(eqpJsonObject);
				}
			}
			
			/**
			 * 查询类型为1(1公里以内设备)
			 */
			if ("3".equals(operType)&&"1".equals(param.get("queryType"))) {
				result.put("currPage", currPage);
				result.put("pageSize", pageSize);
				result.put("totalPage", "20");
				result.put("totalSize", "200");
				double d = Double.valueOf(distance);
				double degree = d*1000.0 /(2 * Math.PI * 6378137.0) * 360;
				param.put("degree", degree);
				// 查询距离内的设备
				List<Map> distanceList = checkSpecialDao.getDistance(param);
				JSONObject distanceJsonObject = new JSONObject();
				int totalSize = distanceList.size();
				if((totalSize%Integer.parseInt(pageSize))==0){
					totalPage= totalSize/(Integer.parseInt(pageSize));
				}else 
					totalPage=totalSize/(Integer.parseInt(pageSize))+1;
				for (Map distance1 :distanceList ) {
					
					
					double lng1 = 0;
					double lat1 = 0;
					String lon = (String) distance1.get("LONGITUDE");
					String lat = (String) distance1.get("LATITUDE");
					String lon_inspect=(String) distance1.get("LONGITUDE_INSPECT");
					String lat_inspect=(String) distance1.get("LATITUDE_INSPECT");
					
					
					if(lon_inspect!=null &&!"".equals(lon_inspect)){
						lng1 = Double.parseDouble(lon_inspect);
						lat1 = Double.parseDouble(lat_inspect);
						distanceJsonObject.put("longitude",null==lon_inspect?"":lon_inspect);
						distanceJsonObject.put("latitude",null==lat_inspect?"":lat_inspect);
					}else{
						if(lon!=null &&!"".equals(lon)){
							lng1 = Double.parseDouble(lon);
						}
						if(lat!=null &&!"".equals(lat)){
							lat1 = Double.parseDouble(lat);
						}
						distanceJsonObject.put("longitude",null==lon?"":lon);
						distanceJsonObject.put("latitude",null==lat?"":lat);
					}						
					/*double lng1 = Double.parseDouble(String.valueOf(distance1.get("LONGITUDE")));
					double lat1 = Double.parseDouble(String.valueOf( distance1.get("LATITUDE")));*/
					double lat2 = 0;
					double lng2 = 0;
					if(latitude!=null && !"".equals(latitude)){
						lat2 = Double.parseDouble((latitude));
					}
					if(longitude!=null&&!"".equals(longitude)){
						lng2 = Double.parseDouble((longitude));
					}
					double s = MapDistance.getDistance(lat1, lng1, lat2, lng2);
					// 获取设备信息
					//int allPort = checkTaskDao.getAllPort(a);
					
						
					//}
						
				
					
					distanceJsonObject.put("eqpId", String.valueOf(distance1.get("SBID")));
					
					String sbbm = String.valueOf(distance1.get("SBBM")).trim();
					
					
					distanceJsonObject.put("eqpNo", sbbm);
					distanceJsonObject.put("eqpName", String.valueOf(distance1.get("SBMC")).trim());
					distanceJsonObject.put("eqp_type_id", String.valueOf(distance1.get("SBLX")).trim());
					distanceJsonObject.put("eqp_type", String.valueOf(distance1.get("RES_TYPE")).trim());
					distanceJsonObject.put("address", String.valueOf(distance1.get("ADDRESS")).trim());
//					distanceJsonObject.put("longitude", String.valueOf(distance1.get("LONGITUDE")));
//					distanceJsonObject.put("latitude", String.valueOf(distance1.get("LATITUDE")));
					distanceJsonObject.put("distance", String.valueOf(s));
					distanceJsonObject.put("TASKTYPE",distance1.get("TASKTYPE"));
					distanceJsonObject.put("DATA_SOURCE",distance1.get("DATA_SOURCE"));
					String MODIFY_TIME =  null==distance1.get("MODIFY_TIME")?"":distance1.get("MODIFY_TIME").toString();
					distanceJsonObject.put("MODIFY_TIME",MODIFY_TIME);
					distanceJsonObject.put("taskId",distance1.get("TASKID"));
					String END_TIME =  null==distance1.get("END_TIME")?"":distance1.get("END_TIME").toString();
					distanceJsonObject.put("end_time",END_TIME);
					distanceJsonObject.put("currPage", param.get("currPage"));
					distanceJsonObject.put("shyj",null==distance1.get("SHYJ")?"":distance1.get("SHYJ").toString());
					distanceJsonObject.put("pageSize", param.get("pageSize"));
					distanceJsonObject.put("totalSize", String.valueOf(totalSize));
					distanceJsonObject.put("totalPage", String.valueOf(totalPage));
					distanceJsonObject.put("remark", null==distance1.get("REMARK")?"":distance1.get("REMARK").toString());
					distanceJsonObject.put("taskName", String.valueOf(distance1.get("TASKNAME")).trim());
					distanceJsonObject.put("WLJB", String.valueOf(distance1.get("WLJB")).trim());
					distanceJsonObject.put("info", null==distance1.get("INFO")?"":distance1.get("INFO").toString());
					distanceJsonObject.put("num", null==distance1.get("NUM")?"":distance1.get("NUM").toString());
					jsArr.add(distanceJsonObject);
				}
			}
		  
			if("1".equals(orderByEqp)){
			//0按照distance 升序排列，默认排序
				for(int i=0;i<jsArr.size()-1;i++){
					for(int j=i+1;j<jsArr.size();j++){
						JSONObject obj1 =  (JSONObject)jsArr.get(i);
						double distance1 = Double.parseDouble(obj1.get("distance").toString());
						JSONObject obj2 =  (JSONObject)jsArr.get(j);
						double distance2 = Double.parseDouble(obj2.get("distance").toString());
						if(distance1>distance2){	
							double temp = distance1;
							distance1 = distance2;
							distance2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
					}
				}
			}
			result.put("areaId", areaId);
			result.put("son_areaId", son_areaId);
			result.put("equipments", jsArr);
		} catch (Exception e) {
			result.put("error", e.toString());
			result.put("desc", "查询设备列表失败，请联系管理员。");
			e.printStackTrace();
		}finally {
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}

	@Override
	public String getPort(String jsonStr) {


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
			String areaId = json.getString("areaId");
			/**
			 * 返回的参数
			 */
			result.put("latitude", latitude);
			result.put("longitude", longitude);
			result.put("rwmxId", "");
			result.put("taskId", taskId);

			Map zgmap = checkSpecialDao.getzgMessageByTaskId(taskId);//维护人员从第二次去维护
			
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
				List<Map<String, Object>> eqpzgPhotoList = checkSpecialDao
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

			

			/**
			 * 端子信息[]
			 */
			
				JSONArray jsonArr1 = new JSONArray();
				Map portParamMap = new HashMap<String, String>();
			
				portParamMap.put("taskId", taskId);
				portParamMap.put("inspectObjectType", "1");// 1端子
				portParamMap.put("areaId", areaId);
				portParamMap.put("longitude", longitude);
				portParamMap.put("latitude", latitude);
				JSONArray glPath = new JSONArray();
				JSONObject glObject = new JSONObject();

				List<Map<String, Object>> portlist = checkSpecialDao
						.getPortMessage(portParamMap);

				if (portlist.size() > 0 && portlist != null) {
					for (Map<String, Object> port : portlist) {
						JSONObject portObject = new JSONObject();
						portObject.put("eqpId", port.get("EQP_ID"));
						portObject.put("record_type", port.get("RECORD_TYPE"));
						portObject.put("eqpNo", port.get("EQP_NO"));
						portObject.put("eqpName", port.get("EQP_NAME"));
						portObject.put("eqp_type_id", port.get("RES_TYPE_ID"));
						portObject.put("eqp_type", port.get("RES_TYPE"));
						portObject.put("type", port.get("COMPANY"));
						portObject.put("portId", port.get("PORT_ID"));
						portObject.put("portNo", port.get("PORT_NO"));
						portObject.put("portName", null == port
								.get("PORT_NAME") ? "" : port.get("PORT_NAME"));
						// portObject.put("ossglbh", ""); //实时查询OSS系统的光路编号
						portObject.put("reason",
								null == port.get("DESCRIPT") ? "" : port
										.get("DESCRIPT"));
						portObject.put("isCheckOK", port.get("ISCHECKOK"));
						portObject.put("glbm", null == port.get("GLBM") ? ""
								: port.get("GLBM"));
						portObject.put("glmc", null == port.get("GLMC") ? ""
								: port.get("GLMC"));
						portObject.put("isH", null == port.get("ISH") ? ""
								: port.get("ISH"));
						portObject.put("staff_name", port.get("STAFF_NAME"));// 检查人
						portObject.put("create_time", port.get("CREATE_TIME"));
						portObject.put("record_id", port.get("RECORD_ID"));
						Map GDmap = new HashMap<String, String>();
						GDmap.put("EQP_ID", port.get("EQP_ID"));
						GDmap.put("PORT_ID", port.get("PORT_ID"));
						List<Map> gdList = checkSpecialDao.getGDList(GDmap);
						glPath.clear();
						for (Map gd : gdList) {
							glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
							glObject.put("glbh", String.valueOf(gd.get("GLBH")).trim());
							glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
							glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
							glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
							glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
							glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
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
			result.put("error", e.toString());
			result.put("desc", "查看整改工单失败，请联系管理员。");
			logger.info(e.toString());
		}
		return result.toString();
	
	}

	@Override
	public String getDelLink(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			Map param = new HashMap();
			
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String staffId = json.getString("staffId");// 用户ID
			String sn = json.getString("sn");// sn
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			
			
			String currPage = json.getString("currPage");//当前页
			String pageSize = json.getString("pageSize");//每页显示大小
			String areaId = json.getString("areaId");
			String sonareaname = json.getString("sonareaname");
			String orderByEqp = json.getString("orderByEqp");
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
		
			String glbh = json.getString("glbh");//光路编号
								
			param.put("sonareaname", sonareaname);
			param.put("staffId", staffId);
			param.put("sn", sn);
			param.put("terminalType", terminalType);
			param.put("glbh", glbh);
			param.put("currPage", currPage);
			param.put("pageSize", pageSize);
			param.put("areaId", areaId);
	
			param.put("START_TIME", startTime);
			param.put("END_TIME", endTime);

			
			
			JSONObject body = new JSONObject();
			JSONArray jsArr = new JSONArray();
            int totalPage = 0;
            List<Map> glList = new ArrayList<Map>() ;
            glList = checkSpecialDao.getGlList(param);
            int totalSize = glList.size();
			if((totalSize%Integer.parseInt(pageSize))==0){
				totalPage= totalSize/(Integer.parseInt(pageSize));
			}else 
				 totalPage=totalSize/(Integer.parseInt(pageSize))+1;
			JSONObject glJsonObject = new JSONObject();
			String jndi = "ossgxl";

            for (Map gl : glList) {

				String glbm = String.valueOf(gl.get("DEL_OPT_CODE"));
				String JNDI = null;
				try {
				    int area = Integer.parseInt(areaId);
				    switch(area){
				    case  3 : JNDI = "OSSBC_DEV_NJ" ; break;
				    case 4 : JNDI = "OSSBC_DEV_ZJ" ;break;
				    case  15 : JNDI = "OSSBC_DEV_WX" ;break;
				    case  20 : JNDI = "OSSBC_DEV_SZ" ;break;
				    case  26 : JNDI = "OSSBC_DEV_NT" ;break;
				    case  33 : JNDI = "OSSBC_DEV_YZ" ;break;
				    case  39 : JNDI = "OSSBC_DEV_YC" ;break;
				    case  48 : JNDI = "OSSBC_DEV_XZ" ;break;
				    case  60 : JNDI = "OSSBC_DEV_HA" ;break;
				    case  63 : JNDI = "OSSBC_DEV_LYG" ;break;
				    case  69 : JNDI = "OSSBC_DEV_CZ" ;break;
				    case  79 : JNDI = "OSSBC_DEV_TZ" ;break;
				    case  84 : JNDI = "OSSBC_DEV_SQ" ;break;
					}
				} catch (NumberFormatException e) {
				    e.printStackTrace();
				}
				
				
				
				param.put("glbh", glbm);
				param.put("jndi", jndi);
				param.put("JNDI", JNDI);
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				List<Map> DelNowList = checkSpecialDao.getDelEqpList(param);
				
				SwitchDataSourceUtil.clearDataSource();
				JSONObject taskJsonObject = new JSONObject();
		     		Map eqpmap = new HashMap();
		     		Map eqppara = new HashMap();
						for (Map dtsj : DelNowList) {
							String eqpId = dtsj.get("SBID")==null?"":dtsj.get("SBID").toString();
							taskJsonObject.put("eqpId",eqpId );
							String sbbm = dtsj.get("SBBM")==null?"":dtsj.get("SBBM").toString();
							//Map resultMap = this.queryEqpInfo(sbbm);
//							String isDeal = null==resultMap.get("isDeal")?"":resultMap.get("isDeal").toString();
//							String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("isDeal").toString();
							eqppara.put("eqpId", eqpId);
							eqppara.put("areaId", areaId);
							eqppara.put("sbbm", sbbm);
							eqpmap = checkSpecialDao.getEqpDetail(eqppara);
							if (null!=eqpmap){
							taskJsonObject.put("isDeal", eqpmap.get("ISDEAL")==null?"":eqpmap.get("ISDEAL").toString());	
							taskJsonObject.put("dealTime", eqpmap.get("DEALTIME")==null?"":eqpmap.get("DEALTIME").toString());
							taskJsonObject.put("contract_persion_no",  eqpmap.get("CONTRACT_PERSION_NO")==null?"":eqpmap.get("CONTRACT_PERSION_NO").toString());
							taskJsonObject.put("contract_persion_name", eqpmap.get("CONTRACT_PERSION_NAME")==null?"":eqpmap.get("CONTRACT_PERSION_NAME").toString());
							taskJsonObject.put("WLJB", null==eqpmap.get("WLJB")?"":eqpmap.get("WLJB").toString());
							taskJsonObject.put("eqpName", eqpmap.get("SBMC")==null?"":eqpmap.get("SBMC").toString());
							taskJsonObject.put("eqp_type", eqpmap.get("RES_TYPE")==null?"":eqpmap.get("RES_TYPE").toString());
							taskJsonObject.put("identifyid", null==eqpmap.get("IDENTIFYID")?"":eqpmap.get("IDENTIFYID").toString());
							taskJsonObject.put("area_id", null==eqpmap.get("AREA_ID")?"":eqpmap.get("AREA_ID").toString());
							}else{
								taskJsonObject.put("isDeal", "");	
								taskJsonObject.put("dealTime", "");
								taskJsonObject.put("contract_persion_no",  "");
								taskJsonObject.put("contract_persion_name", "");
								taskJsonObject.put("WLJB", "");
								
								taskJsonObject.put("eqpName", "");
								taskJsonObject.put("eqp_type", "");
								taskJsonObject.put("identifyid","");
								taskJsonObject.put("area_id", "");
								
								
								
							}
							taskJsonObject.put("eqp_type_id", dtsj.get("SBLX")==null?"":dtsj.get("SBLX").toString());
							taskJsonObject.put("eqpNo", sbbm);
							taskJsonObject.put("address", dtsj.get("ADDRESS")==null?"":dtsj.get("ADDRESS").toString());
							
							taskJsonObject.put("parent_area_id", areaId);
							
								
								
								
								
							
							
							//OSS中的坐标用来手机端地图显示
							String lon = "";
							String lat = "";
							if (null!=eqpmap){
								lon = eqpmap.get("LONGITUDE")==null?"":eqpmap.get("LONGITUDE").toString();
								lat = eqpmap.get("LATITUDE")==null?"":eqpmap.get("LATITUDE").toString();
							}else{
								lon = "";
								lat = "";
							}
							
							//检查人员采集的坐标用来手机端地图显示
							String lon_inspect="";
							String lat_inspect="";
							if (null!=eqpmap){
								lon_inspect = eqpmap.get("LONGITUDE_INSPECT")==null?"":eqpmap.get("LONGITUDE_INSPECT").toString();
								lat_inspect = eqpmap.get("LATITUDE_INSPECT")==null?"":eqpmap.get("LATITUDE_INSPECT").toString();
								
							}
							else{
								lon_inspect = "";
								lat_inspect = "";
								
							}
							//我的检查任务地图显示,如果未采集，直接与以前一样，如果采集了，将检查人员采集的坐标作为地图显示
							//获取距离
							double lant1 = 0;
							double long1 = 0;
							if(lon_inspect!=null && !"".equals(lon_inspect)&&!" ".equals(lon_inspect)){
								long1 = Double.parseDouble(lon_inspect);
								lant1 = Double.parseDouble(lat_inspect);
								taskJsonObject.put("longitude", lon_inspect);
								taskJsonObject.put("latitude", lat_inspect);
							}else{
								if(lon!=null &&!"".equals(lon)&&!" ".equals(lon)){
									long1 = Double.parseDouble(lon);
								}
								if(lat!=null &&!"".equals(lat)&&!" ".equals(lat)){
									lant1 = Double.parseDouble(lat);
								}
								taskJsonObject.put("longitude", lon);
								taskJsonObject.put("latitude", lat);
							}						
							double lat2 = 0;
							double lng2 = 0;
							if(latitude!=null && !"".equals(latitude)&& !" ".equals(latitude)){
								lat2 = Double.parseDouble((latitude));
							}
							if(longitude!=null&&!"".equals(longitude)&&!" ".equals(longitude)){
								lng2 = Double.parseDouble((longitude));
							}
							
							double s1 = MapDistance.getDistance(lant1, long1, lat2, lng2);
							taskJsonObject.put("distance", String.valueOf(s1));
							//获取端子占比
							String sbId = dtsj.get("SBID").toString();
							Map a = new HashMap();
							a.put("sbid", sbId);
							a.put("areaId", areaId);
							a.put("startTime", startTime);
							a.put("endTime", endTime);
							int allPort = 12;
							int changePort ;
						
						    changePort =checkSpecialDao.getDelPort(a);
							
							double percentage = (double) changePort / allPort;
							taskJsonObject.put("rate", String.valueOf(percentage));
							taskJsonObject.put("change_port_num", changePort);
							if (null!=eqpmap){
								taskJsonObject.put("last_check_time", String.valueOf(eqpmap.get("LAST_UPDATE_TIME")));

							}else{
								taskJsonObject.put("last_check_time",""); 

								
							}
							taskJsonObject.put("start_time", startTime);
							
							taskJsonObject.put("end_time", endTime);
							
							taskJsonObject.put("taskType", "");
							taskJsonObject.put("taskName", "");
							taskJsonObject.put("taskId", "");
							taskJsonObject.put("rwmxId", "");
							taskJsonObject.put("end_time", "");
							taskJsonObject.put("taskState", "");
							jsArr.add(taskJsonObject);
						}
			}
            
            
            
            result.put("areaId", areaId);
			result.put("equipments", jsArr);
		} catch (Exception e) {
			result.put("error", e.toString());
			result.put("desc", "查询光路列表失败，请联系管理员。");
			e.printStackTrace();
		}finally {
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}

	@Override
	public String getDelEqp(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			Map param = new HashMap();
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			
			String staffId = json.getString("staffId");// 用户ID
			String sn = json.getString("sn");// sn
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			
			String currPage = json.getString("currPage");//当前页
			String pageSize = json.getString("pageSize");//每页显示大小
			
			
			String areaId = json.getString("areaId");
			
			String glbh = json.getString("glbh");
			String son_areaId="";
	//		String glbh = json.getString("glbh");//光路编号
//			if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("photoId", "");
//			return result.toString();
//		}   
			String JNDI = null;
			String jndi = "cpf83";
			try {
			    int area = Integer.parseInt(areaId);
			    switch(area){
			    case  3 : JNDI = "OSSBC_DEV_NJ" ; break;
			    case 4 : JNDI = "OSSBC_DEV_ZJ" ;break;
			    case  15 : JNDI = "OSSBC_DEV_WX" ;break;
			    case  20 : JNDI = "OSSBC_DEV_SZ" ;break;
			    case  26 : JNDI = "OSSBC_DEV_NT" ;break;
			    case  33 : JNDI = "OSSBC_DEV_YZ" ;break;
			    case  39 : JNDI = "OSSBC_DEV_YC" ;break;
			    case  48 : JNDI = "OSSBC_DEV_XZ" ;break;
			    case  60 : JNDI = "OSSBC_DEV_HA" ;break;
			    case  63 : JNDI = "OSSBC_DEV_LYG" ;break;
			    case  69 : JNDI = "OSSBC_DEV_CZ" ;break;
			    case  79 : JNDI = "OSSBC_DEV_TZ" ;break;
			    case  84 : JNDI = "OSSBC_DEV_SQ" ;break;
				}
			} catch (NumberFormatException e) {
			    e.printStackTrace();
			}
			
			
			param.put("staffId", staffId);
			param.put("sn", sn);
			param.put("terminalType", terminalType);
			param.put("longitude", longitude);
			param.put("latitude", latitude);
			param.put("glbh", glbh);
			param.put("currPage", currPage);
			param.put("pageSize", pageSize);
			
			param.put("areaId", areaId);
			
			param.put("jndi", jndi);
			param.put("JNDI", JNDI);
			JSONObject body = new JSONObject();
			JSONArray jsArr = new JSONArray();
            int totalPage = 0;
            
            

			result.put("currPage", currPage);
			result.put("pageSize", pageSize);
			result.put("totalPage", "20");
			result.put("totalSize", "200");
			
			

			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			List<Map> DelNowList = checkSpecialDao.getDelEqpList(param);
			
			SwitchDataSourceUtil.clearDataSource();
     		JSONObject taskJsonObject = new JSONObject();
			
			/*for (int i = 0; i < taskList.size(); i++) {
				// 查询任务列表
				JSONObject taskJsonObject = new JSONObject();
				String rwid = String.valueOf(taskList.get(i).get("TASK_ID"));
				String rwmxid = String.valueOf(taskList.get(i).get("DETAIL_ID"));
//				String jclx = (String) taskList.get(i).get("JCLX");
				HashMap hs = new HashMap();
				hs.put("RWID", rwid);
				hs.put("RWMXID", rwmxid);
				List<Map> dtsjList = checkTaskDao.getDtsjList(hs);*/
     		if (null!=DelNowList&&!DelNowList.equals(null)){
     		Map eqpmap = new HashMap();
     		Map eqppara = new HashMap();
				for (Map dtsj : DelNowList) {
					String eqpId = dtsj.get("SBID")==null?"":dtsj.get("SBID").toString();
					taskJsonObject.put("eqpId",eqpId );
					String sbbm = dtsj.get("SBBM")==null?"":dtsj.get("SBBM").toString();
					//Map resultMap = this.queryEqpInfo(sbbm);
//					String isDeal = null==resultMap.get("isDeal")?"":resultMap.get("isDeal").toString();
//					String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("isDeal").toString();
					eqppara.put("eqpId", eqpId);
					eqppara.put("areaId", areaId);
					eqpmap = checkSpecialDao.getEqpDetail(eqppara);
					taskJsonObject.put("isDeal", eqpmap.get("ISDEAL")==null?"":eqpmap.get("ISDEAL").toString());	
					taskJsonObject.put("dealTime", eqpmap.get("DEALTIME")==null?"":eqpmap.get("DEALTIME").toString());
					taskJsonObject.put("contract_persion_no",  eqpmap.get("CONTRACT_PERSION_NO")==null?"":eqpmap.get("CONTRACT_PERSION_NO").toString());
					taskJsonObject.put("contract_persion_name", eqpmap.get("CONTRACT_PERSION_NAME")==null?"":eqpmap.get("CONTRACT_PERSION_NAME").toString());
					taskJsonObject.put("WLJB", null==eqpmap.get("WLJB")?"":eqpmap.get("WLJB").toString());
					taskJsonObject.put("eqpNo", sbbm);
					taskJsonObject.put("eqp_type_id", dtsj.get("SBLX")==null?"":dtsj.get("SBLX").toString());
					taskJsonObject.put("eqpName", eqpmap.get("SBMC")==null?"":eqpmap.get("SBMC").toString());
					taskJsonObject.put("eqp_type", eqpmap.get("RES_TYPE")==null?"":eqpmap.get("RES_TYPE").toString());
					taskJsonObject.put("address", dtsj.get("ADDRESS")==null?"":dtsj.get("ADDRESS").toString());
					taskJsonObject.put("identifyid", null==eqpmap.get("IDENTIFYID")?"":eqpmap.get("IDENTIFYID").toString());
					taskJsonObject.put("area_id", null==eqpmap.get("AREA_ID")?"":eqpmap.get("AREA_ID").toString());
					
					taskJsonObject.put("parent_area_id", areaId);

					
					//OSS中的坐标用来手机端地图显示
					String lon = "";
					String lat = "";
					lon = eqpmap.get("LONGITUDE")==null?"":eqpmap.get("LONGITUDE").toString();
					lat = eqpmap.get("LATITUDE")==null?"":eqpmap.get("LATITUDE").toString();
					//检查人员采集的坐标用来手机端地图显示
					String lon_inspect="";
					String lat_inspect="";
					lon_inspect = eqpmap.get("LONGITUDE_INSPECT")==null?"":eqpmap.get("LONGITUDE_INSPECT").toString();
					lat_inspect = eqpmap.get("LATITUDE_INSPECT")==null?"":eqpmap.get("LATITUDE_INSPECT").toString();
					//我的检查任务地图显示,如果未采集，直接与以前一样，如果采集了，将检查人员采集的坐标作为地图显示
					//获取距离
					double lant1 = 0;
					double long1 = 0;
					if(lon_inspect!=null && !"".equals(lon_inspect)&&!" ".equals(lon_inspect)){
						long1 = Double.parseDouble(lon_inspect);
						lant1 = Double.parseDouble(lat_inspect);
						taskJsonObject.put("longitude", lon_inspect);
						taskJsonObject.put("latitude", lat_inspect);
					}else{
						if(lon!=null &&!"".equals(lon)&&!" ".equals(lon)){
							long1 = Double.parseDouble(lon);
						}
						if(lat!=null &&!"".equals(lat)&&!" ".equals(lat)){
							lant1 = Double.parseDouble(lat);
						}
						taskJsonObject.put("longitude", lon);
						taskJsonObject.put("latitude", lat);
					}						
					double lat2 = 0;
					double lng2 = 0;
					if(latitude!=null && !"".equals(latitude)&& !" ".equals(latitude)){
						lat2 = Double.parseDouble((latitude));
					}
					if(longitude!=null&&!"".equals(longitude)&&!" ".equals(longitude)){
						lng2 = Double.parseDouble((longitude));
					}
					
					double s1 = MapDistance.getDistance(lant1, long1, lat2, lng2);
					taskJsonObject.put("distance", String.valueOf(s1));
					//获取端子占比
					String sbId = dtsj.get("SBID").toString();
					Map a = new HashMap();
					a.put("sbid", sbId);
					a.put("areaId", areaId);
					a.put("startTime", startTime);
					a.put("endTime", endTime);
					int allPort = 12;
					int changePort ;
				
				    changePort =checkTaskDao.getDelPort(a);
					
					double percentage = (double) changePort / allPort;
					taskJsonObject.put("rate", String.valueOf(percentage));
					taskJsonObject.put("change_port_num", changePort);
					taskJsonObject.put("last_check_time", String.valueOf(eqpmap.get("LAST_UPDATE_TIME")));
					taskJsonObject.put("start_time", startTime);
					
					taskJsonObject.put("end_time", endTime);
					
					taskJsonObject.put("taskType", "");
					taskJsonObject.put("taskName", "");
					taskJsonObject.put("taskId", "");
					taskJsonObject.put("rwmxId", "");
					taskJsonObject.put("end_time", "");
					taskJsonObject.put("taskState", "");
					jsArr.add(taskJsonObject);
				}
			//}
				
		
				result.put("areaId", areaId);
				result.put("equipments", jsArr);
			} else{
				result.put("areaId", areaId);
				result.put("equipments", "");
			}
		}catch (Exception e) {
				result.put("error", e.toString());
				result.put("desc", "查询设备列表失败，请联系管理员。");
				e.printStackTrace();
			}finally {
				SwitchDataSourceUtil.clearDataSource();
			}
			return result.toString();
		}

	public String getKxOrder(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			Map param = new HashMap();
			
			
			String staffId = json.getString("staffId");// 用户ID
			String sn = json.getString("sn");// sn
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机
			
			
			String currPage = json.getString("currPage");//当前页
			String pageSize = json.getString("pageSize");//每页显示大小
			String area_name = json.getString("area_name");
			String areaId = json.getString("areaId");
			
			String orderByEqp = json.getString("orderByEqp");
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			String ORDER_NO = json.getString("ORDER_NO");//光路编号
	
			
			param.put("staffId", staffId);
			param.put("sn", sn);
			param.put("terminalType", terminalType);
			param.put("ORDER_NO", ORDER_NO);
			param.put("currPage", currPage);
			param.put("pageSize", pageSize);
			param.put("area_name", area_name);
			param.put("areaId", areaId);
			param.put("START_TIME", startTime);
			param.put("END_TIME", endTime);

			
			
			JSONObject body = new JSONObject();
			JSONArray jsArr = new JSONArray();
            int totalPage = 0;
            List<Map> IOMList = new ArrayList<Map>() ;
            IOMList = checkSpecialDao.getKxOrder(param);
            int totalSize = IOMList.size();
			if((totalSize%Integer.parseInt(pageSize))==0){
				totalPage= totalSize/(Integer.parseInt(pageSize));
			}else 
				 totalPage=totalSize/(Integer.parseInt(pageSize))+1;
			JSONObject glJsonObject = new JSONObject();
            
            for (Map gl : IOMList) {
            	glJsonObject.put("AREA_NAME", String.valueOf(gl.get("AREA_NAME")));
				glJsonObject.put("ORDER_CODE", String.valueOf(gl.get("ORDER_CODE")).trim());

				
				glJsonObject.put("ORDER_TYPE", String.valueOf(gl.get("ORDER_TYPE")).trim());
				glJsonObject.put("ACCEPT_DATE", String.valueOf(gl.get("ACCEPT_DATE")).trim());

				glJsonObject.put("FINISH_DATE",gl.get("FINISH_DATE")==null?"":String.valueOf(gl.get("FINISH_DATE")).trim());
				
				
				glJsonObject.put("XUQIU",gl.get("XUQIU"));
				glJsonObject.put("ADDR_NAME",gl.get("ADDR_NAME")==null?"":gl.get("ADDR_NAME"));
				
				jsArr.add(glJsonObject);
			}
            

            
            
            result.put("areaId", areaId);
			result.put("glLists", jsArr);
		} catch (Exception e) {
			result.put("error", e.toString());
			result.put("desc", "查询光路列表失败，请联系管理员。");
			e.printStackTrace();
		}finally {
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}

	@Override
	public String getSonarea(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String areaId = json.getString("areaId");
			String type = json.getString("type");
			List<Map> sonarealist = null;
			if("0".equals(type)){
				sonarealist = checkSpecialDao.getIOMSonareaList(areaId);
			}else{
			
				sonarealist = checkSpecialDao.getOSSSonareaList(areaId);
				
			}
			JSONObject body = new JSONObject();
			JSONArray jsArr = new JSONArray();
			for (Map sonarea : sonarealist) {
				body.put("NAME", String.valueOf(sonarea.get("NAME")));
				jsArr.add(body);
			}
			
			    result.put("areaId", areaId);
				result.put("sonarealist", jsArr);
			} catch (Exception e) {
				result.put("error", e.toString());
				result.put("desc", "查询区域列表失败，请联系管理员。");
				e.printStackTrace();
			}finally {
				SwitchDataSourceUtil.clearDataSource();
			}
			return result.toString();
	}

	@Override
	public String getKxOrderDetail(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String ORDER_NO = json.getString("ORDER_NO");
			Map map = new HashMap() ;
			map.put("ORDER_NO", ORDER_NO);
			List<Map> tasklist = checkSpecialDao.getIOMList(map);
			
			

			JSONObject glJsonObject = new JSONObject();

			
			JSONObject body = new JSONObject();
			JSONArray jsArr = new JSONArray();
			for (Map task : tasklist) {
				glJsonObject.put("IOM_ORDER_ID", String.valueOf(task.get("IOM_ORDER_ID")));
				glJsonObject.put("ORDER_ST", String.valueOf(task.get("ORDER_ST")));
				glJsonObject.put("ORDER_ACTION_TP", String.valueOf(task.get("ORDER_ACTION_TP")).trim());
				glJsonObject.put("ORDER_NO", String.valueOf(task.get("ORDER_NO")).trim());
				glJsonObject.put("CRTD_DT", String.valueOf(task.get("CRTD_DT")).trim());
				glJsonObject.put("ST_DT", String.valueOf(task.get("ST_DT")).trim());
				glJsonObject.put("CMPLTED_DT", String.valueOf(task.get("CMPLTED_DT")).trim());
				glJsonObject.put("ACC_NBR", String.valueOf(task.get("ACC_NBR")).trim());
				glJsonObject.put("ORDER_TITLE", String.valueOf(task.get("ORDER_TITLE")).trim());
				glJsonObject.put("CNTCT", String.valueOf(task.get("CNTCT")).trim());
				glJsonObject.put("ADDR_NAME", String.valueOf(task.get("ADDR_NAME")).trim());
				glJsonObject.put("CMMNTS", String.valueOf(task.get("CMMNTS")).trim());
				glJsonObject.put("WORK_ORDER_ID", String.valueOf(task.get("WORK_ORDER_ID")).trim());
				glJsonObject.put("WORK_ORDER_ST", String.valueOf(task.get("WORK_ORDER_ST")).trim());
				glJsonObject.put("WORK_ORDER_STATE_NAME", String.valueOf(task.get("WORK_ORDER_STATE_NAME")).trim());
				glJsonObject.put("TACHE_ID", String.valueOf(task.get("TACHE_ID")).trim());
				glJsonObject.put("TACHE_NAME", task.get("TACHE_NAME"));
				glJsonObject.put("WORK_STATION_ID", String.valueOf(task.get("WORK_STATION_ID")).trim());
				glJsonObject.put("WORK_STATION_NAME", String.valueOf(task.get("WORK_STATION_NAME")).trim());
				glJsonObject.put("PARTY_ID", String.valueOf(task.get("PARTY_ID")).trim());
				glJsonObject.put("PARTY_NAME",task.get("PARTY_NAME"));
				glJsonObject.put("WORK_RESULT", task.get("WORK_RESULT"));
				glJsonObject.put("CRM_PROD_ID", String.valueOf(task.get("CRM_PROD_ID")).trim());
				glJsonObject.put("PHY_EQP_ID", String.valueOf(task.get("PHY_EQP_ID")).trim());
				glJsonObject.put("PHY_EQP_NO", String.valueOf(task.get("PHY_EQP_NO")).trim());
				glJsonObject.put("RES_ORDER_ID",task.get("RES_ORDER_ID"));
				glJsonObject.put("RES_WORK_ID",task.get("RES_WORK_ID"));
				glJsonObject.put("UNDO_NOTES",task.get("UNDO_NOTES"));
			
				

				jsArr.add(glJsonObject);
				
			}
			
			
			   
				result.put("tasklist", jsArr);
			} catch (Exception e) {
				result.put("error", e.toString());
				result.put("desc", "查询客响订单详细失败，请联系管理员。");
				e.printStackTrace();
			}finally {
				SwitchDataSourceUtil.clearDataSource();
			}
			return result.toString();
	}

	@Override
	public String submitKxContent(String jsonStr) {

		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
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
			
			String staffId = json.getString("staffId");
			String son_area_id = json.getString("son_area_id");
			String areaId = json.getString("areaId");//现场规范
			String IOM_ORDER_ID = null==json.get("IOM_ORDER_ID")?"":json.getString("IOM_ORDER_ID");//评价
			String kxRecord = json.getString("kxRecord");//是否需要整改
			String ORDER_CODE = json.getString("ORDER_CODE");//是否需要整改
			String ALL_CHECK = json.getString("allCheck");//是否需要整改

			
			/**
			 * 端子信息
			 */
			JSONArray innerArray =json.getJSONArray("kxContents");
		
			
			
						Map map = new HashMap<String, String>();
						map.put("AREA_ID", areaId);
						map.put("INSPECTOR", staffId);
						map.put("IOM_ORDER_ID", IOM_ORDER_ID);//现场规范
						map.put("KXRECORD", kxRecord);
						map.put("STATUS_ID", 1);
						map.put("SON_AREA_ID", son_area_id);
						map.put("ORDER_CODE", ORDER_CODE);
						map.put("ALL_CHECK", ALL_CHECK);
						checkSpecialDao.saveKxTask(map);
						String task_id = map.get("TASK_ID").toString();

						/**
						 * 更新端子表
						 */
						for(int i=0;i<innerArray.size();i++){
							JSONObject port = (JSONObject)innerArray.get(i);
							String tag = null==port.get("tag")?"":port.getString("tag");
							String kxResult = null==port.get("KXRESULT")?"":port.getString("kxResult");
							String isOk1 = null==port.get("isOk1")?"":port.getString("isOk1");
							String isOk2 = port.getString("isOk2");
							String photoIds = port.getString("photoId");
							int DetailId = checkSpecialDao.getDetalId();
							int recordId = checkOrderDao.getRecordId();
							map.put("recordId", recordId);
							map.put("TAG", tag);
							map.put("KXRESULT", kxResult);
							map.put("ISOK1", isOk1);
							map.put("ISOK2", isOk2);
							map.put("recordId", recordId);
							
							map.put("DETAILID", DetailId);
							map.put("CREATE_STAFF", staffId);
							map.put("RECORD_TYPE", 9);
							checkSpecialDao.updateKxRecord(map);

							/**
							 * 保存图片关系
							 */
							if(!"".equals(photoIds) && photoIds != null){
								Map photoMap = new HashMap();
								photoMap.put("TASK_ID", task_id);
								photoMap.put("DETAIL_ID", DetailId);
								photoMap.put("OBJECT_ID", recordId);
								photoMap.put("REMARKS", kxResult);
								photoMap.put("OBJECT_TYPE", 9);//3为二次检查
								photoMap.put("RECORD_ID", recordId);
								String[] photos = photoIds.split(",");
								for(String photo : photos){
									photoMap.put("PHOTO_ID", photo);
									checkOrderDao.insertPhotoRel(photoMap);
								}
							}
						}
						checkSpecialDao.updateKxTask(map);
						/**
						 * 增加工单流程流转信息,即回单
						 */
						map.put("oper_staff", staffId);
		//				map.put("status", "7");
						map.put("status", "4");
						map.put("remark", "客响工单");
						map.put("task_id", task_id);
						checkProcessDao.addProcess(map);

		}catch (Exception e) {
			result.put("result", "001");
			result.put("desc", "生成工单失败，请联系管理员。");
			e.printStackTrace();
		}
		return result.toString();	
	
	}

	@Override
	public String getKxOrderEqpInfo(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String ACC_NBR = json.getString("ACC_NBR");
			
			List<Map>	epqlist = checkSpecialDao.getEQPInfo(ACC_NBR);
			
			JSONObject body = new JSONObject();
			JSONArray jsArr = new JSONArray();
			for (Map eqp : epqlist) {
				body.put("CRM_PROD_ID", String.valueOf(eqp.get("CRM_PROD_ID")));
				body.put("PHY_EQP_ID", String.valueOf(eqp.get("PHY_EQP_ID")));
				body.put("PHY_EQP_NO", String.valueOf(eqp.get("PHY_EQP_NO")));
				jsArr.add(body);
			}
			
				result.put("epqlist", jsArr);
			} catch (Exception e) {
				result.put("error", e.toString());
				result.put("desc", "查询设备信息失败，请联系管理员。");
				e.printStackTrace();
			}finally {
				SwitchDataSourceUtil.clearDataSource();
			}
			return result.toString();
	}
	

}
	