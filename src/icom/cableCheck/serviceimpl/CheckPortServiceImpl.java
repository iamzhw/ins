package icom.cableCheck.serviceimpl;

import icom.cableCheck.dao.CheckPortDao;
import icom.cableCheck.dao.CheckTaskDao;
import icom.cableCheck.model.SensitiveLog;
import icom.cableCheck.service.CheckPortService;
import icom.system.dao.CableInterfaceDao;
import icom.util.Result;
import icom.util.SensitiveUtil;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.message.SOAPHeaderElement;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import util.dataSource.SwitchDataSourceUtil;

import com.cableCheck.dao.CheckProcessDao;
import com.webservice.client.ElectronArchivesService;
import com.webservice.oss.IOSSInterfaceForPHONE;
import com.webservice.oss.OSSInterfaceForPHONEServiceLocator;
import com.webservice.oss.OSSInterfaceForPHONEServiceSoapBindingStub;
import com.webservice.yy.IOSSInterfaceForEDBProj;
import com.webservice.yy.OSSInterfaceForEDBProjServiceLocator;
import com.webservice.yy.OSSInterfaceForEDBProjServiceSoapBindingStub;


@Service
@SuppressWarnings("all")
public class CheckPortServiceImpl implements CheckPortService {

	Logger logger = Logger.getLogger(CheckPortServiceImpl.class);
	@Resource
	ElectronArchivesService electronArchivesService;
	@Resource
	private CheckPortDao checkPortDao;
	@Resource
	private CheckTaskDao checkTaskDao;
	@Resource
	private CableInterfaceDao cableInterfaceDao;
	@Resource
	private CheckProcessDao checkProcessDao;

	/**
	 * 当前类的私有属性
	 */
	private String staff_id;
	private String sn;
	
	@Override
	public String getCheckPort(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			String staffId = json.getString("staffId");// 用户ID
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机（必填）
			String sn = json.getString("sn");//客户端sn
			String longitude = json.getString("longitude");// 当前位置的经度
			String latitude = json.getString("latitude");// 当前位置的纬度
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpId = json.getString("eqpId");// 设备ID
			String operType = json.getString("operType");// 功能类型
			String taskId = json.getString("taskId");// 任务ID
			String rwmxId = json.getString("rwmxId");// 任务明细ID
			String currPage = json.getString("currPage");// 当前页
			String pageSize = json.getString("pageSize");// 每页显示
			String query_free_status = json.getString("query_free_status");//是否查询空闲端子，0:占用，1：空闲端子,2全部，默认值：2
			String query_change_status = json.getString("query_change_status");//是否查询上月变动端子,0:未变动,1:变动端子，2全部。默认值：1
			String orderBy = json.getString("orderBy");//s0,按照端子编码排序，1，按照变动时间排序
			String area_id = json.getString("areaId");//地市id
//			String queryType = json.getString("queryType");// 查询类型
			String areaName = checkPortDao.getAreaNameByAreaId(area_id);//地市名
			String isDel =json.getString("isDel");

			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			/**
			 * 查询参数
			 */
			Map param = new HashMap();
			param.put("staffId", staffId);
			param.put("terminalType", terminalType);
			param.put("sn", sn);
			param.put("longitude", longitude);
			param.put("latitude", latitude);
			param.put("eqpNo", eqpNo);
			param.put("eqpId", eqpId);
			param.put("operType", operType);
//			param.put("queryType", queryType);
			param.put("taskId", taskId);
			param.put("rwmxId", rwmxId);
			param.put("currPage", currPage);
			param.put("pageSize", pageSize);
			param.put("jndi", jndi);
			param.put("areaId", area_id);
			param.put("query_change_status", query_change_status);
			param.put("query_free_status", query_free_status);
			param.put("orderBy", orderBy);
			/**
			 * 查询分光器信息
			 */
			List<Map> obdsList = checkPortDao.getObdsByEqpId(param);
			JSONArray obdArray = new JSONArray();
			Map changePortNumMap =  new HashMap();
			if(obdsList.size()>0 && obdsList != null){
				for (Map obds : obdsList) {
					JSONObject obdObject = new JSONObject();
					String obdId = obds.get("SBID").toString();
					String obdNo = obds.get("SBBM").toString();
					String obdName = obds.get("SBMC").toString();
					String obdTypeName = obds.get("SBLX").toString();
					
					changePortNumMap.put("obdId", obdId);
					List<Map> changePortList = null;
					if("0".equals(isDel)){
						changePortList = checkPortDao.getDelPortNum(changePortNumMap);
					}else if ("1".equals(isDel)){
						String startTime = json.getString("startTime");// 开始时间
						String endTime = json.getString("endTime");// 结束时间
						changePortNumMap.put("startTime", startTime);
						changePortNumMap.put("endTime", endTime);
					   changePortList = checkPortDao.getIOMPortNum(changePortNumMap);
					
					}else{
						 changePortList = checkPortDao.getChangePortNum(changePortNumMap);

						
					}
					String portChangeNum = "0";//有变动的端子数
					if(changePortList.size()>0 && changePortList != null){
						portChangeNum = changePortList.get(0).get("PORT_CHANGE_NUM").toString();
					}
					obdObject.put("obdId", obdId);
					obdObject.put("obdNo", obdNo);
					obdObject.put("obdName", obdName);
					obdObject.put("obdTypeName", "分光器");
					obdObject.put("portChangeNum", portChangeNum);
					obdArray.add(obdObject);
				}
				//按变动端子数排序
				for(int i=0;i<obdArray.size()-1;i++){
					for(int j=i+1;j<obdArray.size();j++){
						JSONObject obj1 =  (JSONObject)obdArray.get(i);
						int portChangeNum1 = Integer.valueOf(obj1.get("portChangeNum").toString());
						JSONObject obj2 =  (JSONObject)obdArray.get(j);
						int portChangeNum2 = Integer.valueOf(obj2.get("portChangeNum").toString());
						if(portChangeNum1<portChangeNum2){
							int temp = portChangeNum1;
							portChangeNum1 = portChangeNum2;
							portChangeNum2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							obdArray.set(i, obj1);
							obdArray.set(j,obj2);
						}
					}
				}
				result.put("obds", obdArray);
			}else{
				result.put("obds", "[]");
			}

			int totalPage = 0;
			JSONArray jsArr = new JSONArray();
			JSONArray glPath = new JSONArray();
			JSONObject portObject = new JSONObject();
			JSONObject glObject = new JSONObject();
			Map portMap = new HashMap();
			/**
			 * TODO 查询变动端子	（非任务）0一键反馈，1不可预告抽查 *
			 */
			if (("1".equals(operType)|| "0".equals(operType))) {
				
				if("".equals(query_free_status)){

					//从OSS系统查实时列表
					List<Map> lightList  = null;
					try{
					SwitchDataSourceUtil.setCurrentDataSource(jndi);
					lightList = checkPortDao.getGlList(param);
					SwitchDataSourceUtil.clearDataSource();
					}catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						SwitchDataSourceUtil.clearDataSource();
					}
					//从动态端子表查端子信息
					List<Map> portList = checkPortDao.getPortList(param);
					for (Map light : lightList) {
						String portString = null==light.get("DZID")?"":light.get("DZID").toString();
					
						int flog = 0;
						//获取变动端子信息
						for (Map port : portList) {
							String portString1 = null==port.get("DZID")?"":port.get("DZID").toString();
							if (portString.equals(portString1)) {
								flog++;
								if("1".equals(query_change_status)){//query_change_status 1,变动端子
									portObject.put("eqpId", String.valueOf(port.get("SBID")).trim());
									portObject.put("eqpNo", String.valueOf(port.get("SBBM")).trim());
									portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
									portObject.put("eqp_type_id", String.valueOf(port.get("SBLX")).trim());
									portObject.put("eqp_type", String.valueOf(port.get("RES_TYPE")).trim());
									portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
									portObject.put("portId", String.valueOf(light.get("DZID")).trim());
									portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
									portObject.put("glbh", String.valueOf(port.get("GLBH")).trim());
									portObject.put("isH", String.valueOf(port.get("H")).trim());
									portObject.put("dtsj_id", String.valueOf(port.get("ID")).trim());
									portObject.put("address", String.valueOf(port.get("ADDRESS")).trim());
//									portObject.put("ossglmc", "");
									if((String.valueOf(light.get("GLBH"))!=null)&&(!" ".equals(String.valueOf(light.get("GLBH"))))){
										String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
										portObject.put("ossglbh", glbh);
										portObject.put("is_free_status", "0");
//										/**
//										 * 通过光路编号查询FTTH装机照片详情
//										 */
//										//String areaName = null==port.get("AREA_NAME")?"":port.get("AREA_NAME").toString();
//										Map photoMap = new HashMap();
//										photoMap.put("areaName", areaName);
//										photoMap.put("glbh", glbh);
//										Map resultMap = this.queryPhotoDetail(photoMap);
//										portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//										portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//										portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
									} else {
										portObject.put("ossglbh", "");
										portObject.put("is_free_status", "1");
										portObject.put("staffNo", "");
										portObject.put("staffName", "");
										portObject.put("dealTime", "");
									}
									
									if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
										portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
									} else {
										portObject.put("ossglmc", "");
									}
									
									
									portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
									portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
		                            /**
		                             * 获取工单等信息
		                             */
									//List<Map> gdList = checkPortDao.getGDList(dtsj);
									glPath.clear();
									//for (Map gd : gdList) {
//										glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
//										glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
//										glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
//										glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
//										glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
//										glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
//										glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
										glObject.put("glmc", String.valueOf(port.get("GLMC")).trim());
										glObject.put("gdbh", String.valueOf(port.get("GDBH")).trim());
										glObject.put("sggh", String.valueOf(port.get("SGGH")).trim());
										glObject.put("pzgh", String.valueOf(port.get("PZGH")).trim());
										glObject.put("gxgh", String.valueOf(port.get("GQGH")).trim());
										glObject.put("glxz", String.valueOf(port.get("XZ")).trim());
										glObject.put("bdsj", String.valueOf(port.get("BDSJ")).trim());
										glPath.add(glObject);
									//}
									portObject.put("taskId", "");
									portObject.put("rwmxId", "");
									portObject.put("end_time", "");
									portObject.put("is_change_status", "1");
									portObject.put("portInfos", glPath);
									jsArr.add(portObject);
									break;
								} 
							}
						}
	                    /**
	                     * 如果从dtsj中没找到变动信息直接从oss中获取信息
	                     */
						if (flog == 0&&"0".equals(query_change_status)) {
							portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
							portObject.put("eqp_type_id", String.valueOf(light.get("SBLX")).trim());
							portObject.put("eqp_type", String.valueOf(light.get("NAME")).trim());
							portObject.put("eqpName", String.valueOf(light.get("SBMC")).trim());
							portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
							portObject.put("portId", String.valueOf(light.get("DZID")).trim());
							portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
							portObject.put("address", String.valueOf(light.get("ADDRESS")).trim());
							portObject.put("dtsj_id", "");
//							portObject.put("ossglmc", "");
							if((String.valueOf(light.get("GLBH"))!=null)&&(!"".equals(String.valueOf(light.get("GLBH"))))){
								/**
								 * 通过光路编号查询FTTH装机照片详情
								 */
								String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
//								Map photoMap = new HashMap();
//								photoMap.put("areaName", areaName);
//								photoMap.put("glbh", glbh);
//								Map resultMap = this.queryPhotoDetail(photoMap);
//								portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//								portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//								portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
//								
								portObject.put("ossglbh", glbh);
								portObject.put("is_free_status", "0");
							} else {
								portObject.put("staffNo", "");
								portObject.put("staffName", "");
								portObject.put("dealTime", "");
								
								portObject.put("ossglbh", "");
								portObject.put("is_free_status", "1");
							}
							if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
								portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
							} else {
								portObject.put("ossglmc", "");
							}
							
						
							portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
							portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
							portObject.put("glbh", "");
							portObject.put("isH", "");
							portObject.put("is_change_status", "0");
							portObject.put("taskId", "");
							portObject.put("rwmxId", "");
							portObject.put("end_time", "");
							glPath.clear();
							glObject.put("glmc", "");
							glObject.put("gdbh", "");
							glObject.put("sggh", "");
							glObject.put("pzgh", "");
							glObject.put("gxgh", "");
							glObject.put("glxz", "");
							glObject.put("bdsj", "");
							glPath.add(glObject);
							portObject.put("portInfos", glPath);
							jsArr.add(portObject);
						}
					}
					
					
					
					
					
					
					
				}
				
				/**
				 * TODO 查询变动端子中的拆机端子
				 */
				
				
				if("3".equals(query_free_status)){
					//从OSS系统查实时列表
					List<Map> lightList  = null;
					try{
					SwitchDataSourceUtil.setCurrentDataSource(jndi);
					lightList = checkPortDao.getGlList(param);
					SwitchDataSourceUtil.clearDataSource();
					}catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						SwitchDataSourceUtil.clearDataSource();
					}
					//从动态端子表查端子信息
					List<Map> portList = null ;
					if("0".equals(isDel)){
					 portList = checkPortDao.getDelPortList(param);
					}else{
						String startTime = json.getString("startTime");// 开始时间
						String endTime = json.getString("endTime");// 结束时间
						param.put("startTime", startTime);
						param.put("endTime", endTime);
						
						 portList = checkPortDao.getIOMDelPortList(param);
					}
					for (Map light : lightList) {
						String portString = null==light.get("DZID")?"":light.get("DZID").toString();
					
						int flog = 0;
						//获取变动端子信息
						for (Map port : portList) {
							String portString1 = null==port.get("DZID")?"":port.get("DZID").toString();
							if (portString.equals(portString1)) {
								flog++;
								if("3".equals(query_change_status)){//query_change_status 1,变动端子
									portObject.put("eqpId", String.valueOf(port.get("SBID")).trim());
									portObject.put("eqpNo", String.valueOf(port.get("SBBM")).trim());
									portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
									portObject.put("eqp_type_id", String.valueOf(port.get("SBLX")).trim());
									portObject.put("eqp_type", String.valueOf(port.get("RES_TYPE")).trim());
									portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
									portObject.put("portId", String.valueOf(light.get("DZID")).trim());
									portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
									portObject.put("glbh", String.valueOf(port.get("GLBH")).trim());
									portObject.put("isH", String.valueOf(port.get("H")).trim());
									portObject.put("dtsj_id", String.valueOf(port.get("ID")).trim());
									portObject.put("address", String.valueOf(port.get("ADDRESS")).trim());
//									portObject.put("ossglmc", "");
									if((String.valueOf(light.get("GLBH"))!=null)&&(!" ".equals(String.valueOf(light.get("GLBH"))))){
										String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
										portObject.put("ossglbh", glbh);
										portObject.put("is_free_status", "0");
//										/**
//										 * 通过光路编号查询FTTH装机照片详情
//										 */
//										//String areaName = null==port.get("AREA_NAME")?"":port.get("AREA_NAME").toString();
//										Map photoMap = new HashMap();
//										photoMap.put("areaName", areaName);
//										photoMap.put("glbh", glbh);
//										Map resultMap = this.queryPhotoDetail(photoMap);
//										portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//										portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//										portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
									} else {
										portObject.put("ossglbh", "");
										portObject.put("is_free_status", "1");
										portObject.put("staffNo", "");
										portObject.put("staffName", "");
										portObject.put("dealTime", "");
									}
									
									if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
										portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
									} else {
										portObject.put("ossglmc", "");
									}
									
									
									portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
									portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
		                            /**
		                             * 获取工单等信息
		                             */
									//List<Map> gdList = checkPortDao.getGDList(dtsj);
									glPath.clear();
									//for (Map gd : gdList) {
//										glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
//										glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
//										glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
//										glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
//										glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
//										glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
//										glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
										glObject.put("glmc", String.valueOf(port.get("GLMC")).trim());
										glObject.put("gdbh", String.valueOf(port.get("GDBH")).trim());
										glObject.put("sggh", String.valueOf(port.get("SGGH")).trim());
										glObject.put("pzgh", String.valueOf(port.get("PZGH")).trim());
										glObject.put("gxgh", String.valueOf(port.get("GQGH")).trim());
										glObject.put("glxz", String.valueOf(port.get("XZ")).trim());
										glObject.put("bdsj", String.valueOf(port.get("BDSJ")).trim());
										glPath.add(glObject);
									//}
									portObject.put("taskId", "");
									portObject.put("rwmxId", "");
									portObject.put("end_time", "");
									portObject.put("is_change_status", "1");
									portObject.put("portInfos", glPath);
									jsArr.add(portObject);
									break;
								} 
							}
						}
	                    /**
	                     * 如果从dtsj中没找到变动信息直接从oss中获取信息
	                     */
						if (flog == 0&&"0".equals(query_change_status)) {
							portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
							portObject.put("eqp_type_id", String.valueOf(light.get("SBLX")).trim());
							portObject.put("eqp_type", String.valueOf(light.get("NAME")).trim());
							portObject.put("eqpName", String.valueOf(light.get("SBMC")).trim());
							portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
							portObject.put("portId", String.valueOf(light.get("DZID")).trim());
							portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
							portObject.put("address", String.valueOf(light.get("ADDRESS")).trim());
							portObject.put("dtsj_id", "");
//							portObject.put("ossglmc", "");
							if((String.valueOf(light.get("GLBH"))!=null)&&(!"".equals(String.valueOf(light.get("GLBH"))))){
								/**
								 * 通过光路编号查询FTTH装机照片详情
								 */
								String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
//								Map photoMap = new HashMap();
//								photoMap.put("areaName", areaName);
//								photoMap.put("glbh", glbh);
//								Map resultMap = this.queryPhotoDetail(photoMap);
//								portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//								portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//								portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
//								
								portObject.put("ossglbh", glbh);
								portObject.put("is_free_status", "0");
							} else { 
								portObject.put("staffNo", "");
								portObject.put("staffName", "");
								portObject.put("dealTime", "");
								
								portObject.put("ossglbh", "");
								portObject.put("is_free_status", "1");
							}
							if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
								portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
							} else {
								portObject.put("ossglmc", "");
							}
							
						
							portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
							portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
							portObject.put("glbh", "");
							portObject.put("isH", "");
							portObject.put("is_change_status", "0");
							portObject.put("taskId", "");
							portObject.put("rwmxId", "");
							portObject.put("end_time", "");
							glPath.clear();
							glObject.put("glmc", "");
							glObject.put("gdbh", "");
							glObject.put("sggh", "");
							glObject.put("pzgh", "");
							glObject.put("gxgh", "");
							glObject.put("glxz", "");
							glObject.put("bdsj", "");
							glPath.add(glObject);
							portObject.put("portInfos", glPath);
							jsArr.add(portObject);
						}
					}
				}
				/**
				 * TODO 查询占用端子（非任务）
				 */
				if("".equals(query_change_status)&&(!"2".equals(operType))){
					if("0".equals(query_free_status)){
						//从动态端子表查端子信息
//						List<Map> portList = checkPortDao.getPortList(param);
						//从OSS系统查实时列表
						List<Map> lightList = null;
						try{
						SwitchDataSourceUtil.setCurrentDataSource(jndi);
						lightList = checkPortDao.getGlList(param);
						SwitchDataSourceUtil.clearDataSource();
						}catch (Exception e) {
							e.printStackTrace();
						}
						finally
						{
							SwitchDataSourceUtil.clearDataSource();
						}
						/**
						for(Map light : lightList){
							System.out.println(light.get("DZID").toString()+"---"+light.get("DZBM").toString()+"---"+light.get("GLBH").toString());
						}
						*/
						if(lightList.size()>0 && lightList != null){
							for (Map light : lightList) {
								String lightNo = null==light.get("GLBH")?"":light.get("GLBH").toString();
								String portString = null==light.get("DZID")?"":light.get("DZID").toString();
								int flog = 0;
								if(lightNo != null && ! "".equals(lightNo) && ! " ".equals(lightNo)){
//									for (Map port : portList) {
//										String portString1 = null==port.get("DZID")?"":port.get("DZID").toString();
//										if (portString.equals(portString1)) {
//											flog++;
//											portObject.put("eqpId", String.valueOf(port.get("SBID")).trim());
//											portObject.put("eqpNo", String.valueOf(port.get("SBBM")).trim());
//											portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
//											portObject.put("eqp_type_id", String.valueOf(port.get("SBLX")).trim());
//											portObject.put("eqp_type", String.valueOf(port.get("RES_TYPE")).trim());
//											portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
//											portObject.put("portId", String.valueOf(light.get("DZID")).trim());
//											portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
//											portObject.put("glbh", String.valueOf(port.get("GLBH")).trim());
//											portObject.put("isH", String.valueOf(port.get("H")).trim());
//											portObject.put("address", String.valueOf(port.get("ADDRESS")).trim());
//											String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
//											portObject.put("ossglbh", null==glbh?"":glbh);
//											/**
//											 * 通过光路编号查询FTTH装机照片详情
//											 */
//											Map photoMap = new HashMap();
//											photoMap.put("areaName", areaName);
//											photoMap.put("glbh", glbh);
//											Map resultMap = this.queryPhotoDetail(photoMap);
//											portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//											portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//											portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
////											portObject.put("staffNo", "");
////											portObject.put("staffName", "");
////											portObject.put("dealTime", "");
//											
//											portObject.put("cdno", null==light.get("CDNO")?"":light.get("CDNO").toString());
//											portObject.put("cdname", null==light.get("CDNAME")?"":light.get("CDNAME").toString());
//											portObject.put("jumpPortNo", null==light.get("JUMPPORTNO")?"":light.get("JUMPPORTNO").toString());
//											portObject.put("jumpPortSeqNo", null==light.get("JUMPPORTSEQNO")?"":light.get("JUMPPORTSEQNO").toString());
//											portObject.put("jumpUnitNo", null==light.get("JUMPUNITNO")?"":light.get("JUMPUNITNO").toString());
//											portObject.put("jumpUnitSeq", null==light.get("JUMPUNITSEQ")?"":light.get("JUMPUNITSEQ").toString());
//											portObject.put("jumpEqpName", null==light.get("JUMPEQPNAME")?"":light.get("JUMPEQPNAME").toString());
//											portObject.put("jumpDZBM", null==light.get("JUMPDZBM")?"":light.get("JUMPDZBM").toString());
//											
//											if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
//												portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
//											} else {
//												portObject.put("ossglmc", "");
//											}
//											portObject.put("is_free_status", "0");
//							                /**
//							                 * 获取工单等信息portInfos
//							                 */
//											//List<Map> gdList = checkPortDao.getGDList(dtsj);
//											glPath.clear();
//											//for (Map gd : gdList) {
////												glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
////												glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
////												glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
////												glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
////												glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
////												glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
////												glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
//												glObject.put("glmc", String.valueOf(port.get("GLMC")).trim());
//												glObject.put("gdbh", String.valueOf(port.get("GDBH")).trim());
//												glObject.put("sggh", String.valueOf(port.get("SGGH")).trim());
//												glObject.put("pzgh", String.valueOf(port.get("PZGH")).trim());
//												glObject.put("gxgh", String.valueOf(port.get("GQGH")).trim());
//												glObject.put("glxz", String.valueOf(port.get("XZ")).trim());
//												glObject.put("bdsj", String.valueOf(port.get("BDSJ")).trim());
//												glPath.add(glObject);
//											//}
////											if((String.valueOf(light.get("GLBH"))!=null)&&(!"".equals(String.valueOf(light.get("GLBH"))))){
////												portObject.put("ossglbh", String.valueOf(light.get("GLBH")));
////											} else {//未变动也要给出
////												portObject.put("ossglbh", "");
////											}
//											portObject.put("is_free_status", "0");
//											portObject.put("glbh", "");
//											portObject.put("isH", "");
//											portObject.put("is_change_status", "0");
//											portObject.put("taskId", "");
//											portObject.put("rwmxId", "");
//											portObject.put("end_time", "");
//											portObject.put("is_change_status", "1");
//											portObject.put("portInfos", glPath);
//											jsArr.add(portObject);
//										}
//									}
							        //如果从dtsj中没找到变动信息直接从oss中获取信息
									if (flog == 0) {
										portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
										portObject.put("eqp_type_id", String.valueOf(light.get("SBLX")).trim());
										portObject.put("eqp_type", String.valueOf(light.get("NAME")).trim());
										portObject.put("eqpName", String.valueOf(light.get("SBMC")).trim());
										portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
										portObject.put("portId", String.valueOf(light.get("DZID")).trim());
										portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
										portObject.put("address", String.valueOf(light.get("ADDRESS")).trim());
										portObject.put("dtsj_id", "");
										String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
										portObject.put("ossglbh", null==glbh?"":glbh);
//										/**
//										 * 通过光路编号查询FTTH装机照片详情
//										 */
//										Map photoMap = new HashMap();
//										photoMap.put("areaName", areaName);
//										photoMap.put("glbh", glbh);
//										Map resultMap = this.queryPhotoDetail(photoMap);
//										portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//										portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//										portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
//										portObject.put("staffNo", "");
//										portObject.put("staffName", "");
//										portObject.put("dealTime", "");
										
										
										portObject.put("jumpEqpName", null==light.get("JUMPEQPNAME")?"":light.get("JUMPEQPNAME").toString());
										portObject.put("jumpDZBM", null==light.get("JUMPDZBM")?"":light.get("JUMPDZBM").toString());
										portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
										portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
										if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
											portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
										} else {
											portObject.put("ossglmc", "");
										}
//										portObject.put("ossglmc", "");
										portObject.put("is_free_status", "0");
										portObject.put("glbh", "");
										portObject.put("isH", "");
										portObject.put("is_change_status", "0");
										portObject.put("taskId", "");
										portObject.put("rwmxId", "");
										portObject.put("end_time", "");
										glPath.clear();
										glObject.put("glmc", "");
										glObject.put("gdbh", "");
										glObject.put("sggh", "");
										glObject.put("pzgh", "");
										glObject.put("gxgh", "");
										glObject.put("glxz", "");
										glObject.put("bdsj", "");
										glPath.add(glObject);
										portObject.put("portInfos", glPath);
										jsArr.add(portObject);
									}
								}
							}
						}
					}
				}
				/**
				 * TODO 获取全部端子（非任务）
				 */
				if("2".equals(query_free_status)&&"2".equals(query_change_status)&&(!"2".equals(operType))){
					//从OSS系统查实时列表
					List<Map> lightList = null;
					try{
					SwitchDataSourceUtil.setCurrentDataSource(jndi);
					lightList = checkPortDao.getGlList(param);
					SwitchDataSourceUtil.clearDataSource();
					}catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						SwitchDataSourceUtil.clearDataSource();
					}
					//从动态端子表查端子信息
		//			List<Map> portList = checkPortDao.getPortList(param);
					/*
					for(Map list : lightList){
						System.out.println(list.get("DZID").toString()+"---"+list.get("DZBM").toString()+"---"+list.get("GLBH").toString());
					}
					*/
			//		int totalSize = portList.size();
			//		if ((totalSize % Integer.parseInt(pageSize)) == 0) {
				//		totalPage = totalSize / (Integer.parseInt(pageSize));
				//	} else
				//		totalPage = totalSize / (Integer.parseInt(pageSize)) + 1;
					if(lightList.size()>0 && lightList != null){
						for (Map light : lightList) {
							String portString = null==light.get("DZID")?"":light.get("DZID").toString();
							int flog = 0;
//							for (Map port : portList) {
//								String portString1 = null==port.get("DZID")?"":port.get("DZID").toString();
//								if (portString.equals(portString1)) {
//									flog++;
//									portObject.put("eqpId", String.valueOf(port.get("SBID")).trim());
//									portObject.put("eqpNo", String.valueOf(port.get("SBBM")).trim());
//									portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
//									portObject.put("eqp_type_id", String.valueOf(port.get("SBLX")).trim());
//									portObject.put("eqp_type", String.valueOf(port.get("RES_TYPE")).trim());
//									portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
//									portObject.put("portId", String.valueOf(light.get("DZID")).trim());
//									portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
//									portObject.put("glbh", String.valueOf(port.get("GLBH")).trim());
//									portObject.put("isH", String.valueOf(port.get("H")).trim());
//									portObject.put("address", String.valueOf(port.get("ADDRESS")).trim());
////									String glbh = String.valueOf(light.get("GLBH")).trim();
//									String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
//									portObject.put("ossglbh", null==glbh?"":glbh);
//									/**
//									 * 通过光路编号查询FTTH装机照片详情
//									 */
//									Map photoMap = new HashMap();
//									photoMap.put("areaName", areaName);
//									photoMap.put("glbh", null==glbh?"":glbh);
//									Map resultMap = this.queryPhotoDetail(photoMap);
//									portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//									portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//									portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
//									
//									portObject.put("cdno", null==light.get("CDNO")?"":light.get("CDNO").toString());
//									portObject.put("cdname", null==light.get("CDNAME")?"":light.get("CDNAME").toString());
//									portObject.put("jumpPortNo", null==light.get("JUMPPORTNO")?"":light.get("JUMPPORTNO").toString());
//									portObject.put("jumpPortSeqNo", null==light.get("JUMPPORTSEQNO")?"":light.get("JUMPPORTSEQNO").toString());
//									portObject.put("jumpUnitNo", null==light.get("JUMPUNITNO")?"":light.get("JUMPUNITNO").toString());
//									portObject.put("jumpUnitSeq", null==light.get("JUMPUNITSEQ")?"":light.get("JUMPUNITSEQ").toString());
//									portObject.put("jumpEqpName", null==light.get("JUMPEQPNAME")?"":light.get("JUMPEQPNAME").toString());
//									portObject.put("jumpDZBM", null==light.get("JUMPDZBM")?"":light.get("JUMPDZBM").toString());
//									
//									portObject.put("is_free_status", "0");
////									portObject.put("ossglmc", "");
//									if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
//										portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
//									} else {
//										portObject.put("ossglmc", "");
//									}
//									
//					                //获取工单等信息
//									//List<Map> gdList = checkPortDao.getGDList(dtsj);
//									glPath.clear();
//									//for (Map gd : gdList) {
////										glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
////										glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
////										glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
////										glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
////										glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
////										glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
////										glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
//										glObject.put("glmc", String.valueOf(port.get("GLMC")).trim());
//										glObject.put("gdbh", String.valueOf(port.get("GDBH")).trim());
//										glObject.put("sggh", String.valueOf(port.get("SGGH")).trim());
//										glObject.put("pzgh", String.valueOf(port.get("PZGH")).trim());
//										glObject.put("gxgh", String.valueOf(port.get("GQGH")).trim());
//										glObject.put("glxz", String.valueOf(port.get("XZ")).trim());
//										glObject.put("bdsj", String.valueOf(port.get("BDSJ")).trim());
//										glPath.add(glObject);
//									//}
//									portObject.put("taskId", "");
//									portObject.put("rwmxId", "");
//									portObject.put("end_time", "");
//									portObject.put("is_change_status", "1");
//									portObject.put("portInfos", glPath);
//									jsArr.add(portObject);
//								}
//							}
					        //如果从dtsj中没找到变动信息直接从oss中获取信息
							if (flog == 0) {
								portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
								portObject.put("eqp_type_id", String.valueOf(light.get("SBLX")).trim());
								portObject.put("eqp_type", String.valueOf(light.get("NAME")).trim());
								portObject.put("eqpName", String.valueOf(light.get("SBMC")).trim());
								portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
								portObject.put("portId", String.valueOf(light.get("DZID")).trim());
								portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
								portObject.put("address", String.valueOf(light.get("ADDRESS")).trim());
								portObject.put("dtsj_id", "");
//								String glbh = String.valueOf(light.get("GLBH")).trim();
								String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
								/**
								 * 通过光路编号查询FTTH装机照片详情
								 */
								Map photoMap = new HashMap();
								photoMap.put("areaName", areaName);
								photoMap.put("glbh", glbh);
//								Map resultMap = this.queryPhotoDetail(photoMap);
//								portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//								portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//								portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
								
								portObject.put("ossglbh", null==glbh?"":glbh);
								portObject.put("is_free_status", "0");
								portObject.put("glbh", "");
								portObject.put("isH", "");
								portObject.put("is_change_status", "0");
								portObject.put("taskId", "");
								portObject.put("rwmxId", "");
								portObject.put("end_time", "");
//								portObject.put("ossglmc", "");
								if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
									portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
								} else {
									portObject.put("ossglmc", "");
								}
								
								
								portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
								portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
								glPath.clear();
								glObject.put("glmc", "");
								glObject.put("gdbh", "");
								glObject.put("sggh", "");
								glObject.put("pzgh", "");
								glObject.put("gxgh", "");
								glObject.put("glxz", "");
								glObject.put("bdsj", "");
								glPath.add(glObject);
								portObject.put("portInfos", glPath);
								jsArr.add(portObject);
							}
						}
					}
				}
			}
			/**
			 * TODO 任务中的端子(查所有端子)
			 */
			if (!"".equals(taskId)&&!"".equals(rwmxId)&&"2".equals(query_free_status)&&"2".equals(operType)) {
	            JSONObject dtPortObject = new JSONObject();
	            int taskType = checkPortDao.getTaskType(taskId);
	            if(taskType==0||taskType==4||taskType==10||taskType==11||taskType==12||taskType==13){
	            	//从OSS系统查实时列表
	            	List<Map> lightList = null;
	            	try{
					SwitchDataSourceUtil.setCurrentDataSource(jndi);
					lightList = checkPortDao.getOssGlList(param);
					SwitchDataSourceUtil.clearDataSource();
	            	}catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						SwitchDataSourceUtil.clearDataSource();
					}
					
//					for(Map list : lightList){
//						System.out.println(list.get("DZID").toString()+"---"+list.get("DZBM").toString()+"---"+list.get("GLBH").toString());
//					}
//					
					//从动态端子表查端子信息
	//				List<Map> dtsjList = checkPortDao.getAllTaskDtsjList(param);
					if(lightList.size()>0 && lightList != null){
						for (Map light : lightList) {
							String portString = null==light.get("DZID")?"":light.get("DZID").toString();
							int flog = 0;
//							for(Map dtsj : dtsjList){
//								String portString1 = null==dtsj.get("DZID")?"":dtsj.get("DZID").toString();
//								if(portString.equals(portString1)){
//									flog++;
//									if (String.valueOf(light.get("GLBH")) != null) {
//										String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
//										dtPortObject.put("ossglbh", null==glbh?"":glbh);
////										dtPortObject.put("ossglbh", String.valueOf(light.get("GLBH")));
//										dtPortObject.put("is_free_status", "0");//占用
//										/**
//										 * 通过光路编号查询FTTH装机照片详情
//										 */
//										Map photoMap = new HashMap();
//										photoMap.put("areaName", areaName);
//										photoMap.put("glbh", glbh);
//										Map resultMap = this.queryPhotoDetail(photoMap);
//										dtPortObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//										dtPortObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//										dtPortObject.put("dealTime", resultMap.get("dealTime"));//施工时间
//									}else{
//										dtPortObject.put("ossglbh", "");
//										dtPortObject.put("is_free_status", "1");//空闲
//										dtPortObject.put("staffNo", "");
//										dtPortObject.put("staffName", "");
//										dtPortObject.put("dealTime", "");
//									}
//									if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
//										dtPortObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
//									} else {
//										dtPortObject.put("ossglmc", "");
//									}
//									dtPortObject.put("eqpId", String.valueOf(dtsj.get("SBID")));
//									dtPortObject.put("eqpNo", String.valueOf(dtsj.get("SBBM")));
//									dtPortObject.put("eqpName", String.valueOf(dtsj.get("SBMC")));
//									dtPortObject.put("address", String.valueOf(dtsj.get("ADDRESS")));
//									dtPortObject.put("eqp_type_id", String.valueOf(dtsj.get("SBLX")));
//									dtPortObject.put("eqp_type", String.valueOf(dtsj.get("RES_TYPE")));
//									dtPortObject.put("portId", String.valueOf(light.get("DZID")));
//									dtPortObject.put("portNo", String.valueOf(light.get("DZBM")));
//									dtPortObject.put("glbh", String.valueOf(dtsj.get("GLBH")));
//									dtPortObject.put("isH", String.valueOf(dtsj.get("H")));
//									dtPortObject.put("rwmxId", String.valueOf(dtsj.get("DETAIL_ID")));
//									dtPortObject.put("taskId", taskId);
//									dtPortObject.put("end_time","");
//									dtPortObject.put("is_change_status", "");
////									dtPortObject.put("ossglmc", "");
//									
//									dtPortObject.put("cdno", null==light.get("CDNO")?"":light.get("CDNO").toString());
//									dtPortObject.put("cdname", null==light.get("CDNAME")?"":light.get("CDNAME").toString());
//									dtPortObject.put("jumpPortNo", null==light.get("JUMPPORTSEQNO")?"":light.get("JUMPPORTNO").toString());
//									dtPortObject.put("jumpportseqno", null==light.get("JUMPPORTSEQNO")?"":light.get("JUMPPORTSEQNO").toString());
//									dtPortObject.put("jumpUnitNo", null==light.get("JUMPUNITNO")?"":light.get("JUMPUNITNO").toString());
//									dtPortObject.put("jumpUnitSeq", null==light.get("JUMPUNITSEQ")?"":light.get("JUMPUNITSEQ").toString());
//									dtPortObject.put("jumpEqpName", null==light.get("JUMPEQPNAME")?"":light.get("JUMPEQPNAME").toString());
//									dtPortObject.put("jumpDZBM", null==light.get("JUMPDZBM")?"":light.get("JUMPDZBM").toString());
//									
//									//List<Map> gdList = checkPortDao.getGDList(dtsj);
//									glPath.clear();
//									//for (Map gd : gdList) {
////										glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
////										glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
////										glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
////										glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
////										glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
////										glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
////										glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
//										glObject.put("glmc", String.valueOf(dtsj.get("GLMC")).trim());
//										glObject.put("gdbh", String.valueOf(dtsj.get("GDBH")).trim());
//										glObject.put("sggh", String.valueOf(dtsj.get("SGGH")).trim());
//										glObject.put("pzgh", String.valueOf(dtsj.get("PZGH")).trim());
//										glObject.put("gxgh", String.valueOf(dtsj.get("GQGH")).trim());
//										glObject.put("glxz", String.valueOf(dtsj.get("XZ")).trim());
//										glObject.put("bdsj", String.valueOf(dtsj.get("BDSJ")).trim());
//										glPath.add(glObject);
//									//}
//									dtPortObject.put("portInfos", glPath);
//									jsArr.add(dtPortObject);
//								}
//							}
							if(flog == 0){
								portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
								portObject.put("eqp_type_id", String.valueOf(light.get("SBLX")).trim());
								portObject.put("eqp_type", String.valueOf(light.get("NAME")).trim());
								portObject.put("eqpName", String.valueOf(light.get("SBMC")).trim());
								portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
								portObject.put("portId", String.valueOf(light.get("DZID")).trim());
								portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
								portObject.put("address", String.valueOf(light.get("ADDRESS")).trim());
//								portObject.put("ossglbh", String.valueOf(light.get("GLBH")).trim());
//								portObject.put("ossglmc", "");
								portObject.put("dtsj_id", "");
								if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
									portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
								} else {
									portObject.put("ossglmc", "");
								}
								if((String.valueOf(light.get("GLBH"))!=null)&&(!"".equals(String.valueOf(light.get("GLBH"))))){
									
									String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
//									/**
//									 * 通过光路编号查询FTTH装机照片详情
//									 */
//									Map photoMap = new HashMap();
//									photoMap.put("areaName", areaName);
//									photoMap.put("glbh", glbh);
//									Map resultMap = this.queryPhotoDetail(photoMap);
//									portObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//									portObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//									portObject.put("dealTime", resultMap.get("dealTime"));//施工时间
									
									portObject.put("ossglbh", glbh);
									portObject.put("is_free_status", "0");
								} else {
									portObject.put("ossglbh", "");
									portObject.put("is_free_status", "1");
									portObject.put("staffNo", "");
									portObject.put("staffName", "");
									portObject.put("dealTime", "");
								}
								
								
								portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
								portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
								portObject.put("glbh", "");
								portObject.put("isH", "");
								portObject.put("is_change_status", "");
								portObject.put("taskId", taskId);
								portObject.put("rwmxId", "");
								portObject.put("end_time", "");
								glPath.clear();
								glObject.put("glmc", "");
								glObject.put("gdbh", "");
								glObject.put("sggh", "");
								glObject.put("pzgh", "");
								glObject.put("gxgh", "");
								glObject.put("glxz", "");
								glObject.put("bdsj", "");
								glPath.add(glObject);
								portObject.put("portInfos", glPath);
								jsArr.add(portObject);
							}
						}
					}
	            } else if (taskType==1){
	            	List<Map> dtsjList = checkPortDao.getTroubleList(param);
	            	for (Map dtsj : dtsjList) {
						String dzId = null==dtsj.get("DZID")?"":dtsj.get("DZID").toString();
						Map a = new HashMap();
						a.put("portId", dzId);
						a.put("jndi",jndi);
						List<Map> lightList = null;
						try{
						SwitchDataSourceUtil.setCurrentDataSource(jndi);
						lightList = checkPortDao.getOssGlList(a);
						SwitchDataSourceUtil.clearDataSource();
						}catch (Exception e) {
							e.printStackTrace();
						}
						finally
						{
							SwitchDataSourceUtil.clearDataSource();
						}
						if(lightList.size()>0 && lightList != null){
							for (Map light : lightList) {
								if (String.valueOf(light.get("GLBH")) != null) {
									String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
									/**
									 * 通过光路编号查询FTTH装机照片详情
									 */
									Map photoMap = new HashMap();
									photoMap.put("areaName", areaName);
									photoMap.put("glbh", glbh);
									Map resultMap = this.queryPhotoDetail(photoMap);
									dtPortObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
									dtPortObject.put("staffName", resultMap.get("staffName"));//施工人姓名
									dtPortObject.put("dealTime", resultMap.get("dealTime"));//施工时间
									
									dtPortObject.put("ossglbh", null==glbh?"":glbh);
								} else {
									dtPortObject.put("staffNo", "");
									dtPortObject.put("staffName", "");
									dtPortObject.put("dealTime", "");
									dtPortObject.put("ossglbh", "");
								}
								if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
									dtPortObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
								} else {
									dtPortObject.put("ossglmc", "");
								}
								
								dtPortObject.put("cdno", null==light.get("CDNO")?"":light.get("CDNO").toString());
								dtPortObject.put("cdname", null==light.get("CDNAME")?"":light.get("CDNAME").toString());
								dtPortObject.put("jumpPortNo", null==light.get("JUMPPORTNO")?"":light.get("JUMPPORTNO").toString());
								dtPortObject.put("jumpPortSeqNo", null==light.get("JUMPPORTSEQNO")?"":light.get("JUMPPORTSEQNO").toString());
								dtPortObject.put("jumpUnitNo", null==light.get("JUMPUNITNO")?"":light.get("JUMPUNITNO").toString());
								dtPortObject.put("jumpUnitSeq", null==light.get("JUMPUNITSEQ")?"":light.get("JUMPUNITSEQ").toString());
								dtPortObject.put("jumpEqpName", null==light.get("JUMPEQPNAME")?"":light.get("JUMPEQPNAME").toString());
								dtPortObject.put("jumpDZBM", null==light.get("JUMPDZBM")?"":light.get("JUMPDZBM").toString());
								
								dtPortObject.put("eqpId", String.valueOf(dtsj.get("SBID")));
								dtPortObject.put("eqpNo", String.valueOf(dtsj.get("SBBM")));
								dtPortObject.put("eqpName", String.valueOf(dtsj.get("SBMC")));
								dtPortObject.put("address", String.valueOf(dtsj.get("ADDRESS")));
								dtPortObject.put("eqp_type_id", String.valueOf(dtsj.get("SBLX")));
								dtPortObject.put("eqp_type", String.valueOf(dtsj.get("RES_TYPE")));
								dtPortObject.put("portId", String.valueOf(dtsj.get("DZID")));
								dtPortObject.put("portNo", String.valueOf(dtsj.get("DZBM")));
								dtPortObject.put("glbh", String.valueOf(dtsj.get("GLBH")));
								dtPortObject.put("isH", String.valueOf(dtsj.get("H")));
								dtPortObject.put("rwmxId", String.valueOf(dtsj.get("DETAIL_ID")));
								dtPortObject.put("taskId", taskId);
								dtPortObject.put("end_time", "");//
								dtPortObject.put("is_free_status", "");
								dtPortObject.put("is_change_status", "");
								
								List<Map> gdList = checkPortDao.getGDList(dtsj);
								glPath.clear();
								for (Map gd : gdList) {
									glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
									glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
									glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
									glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
									glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
									glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
									glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
									glPath.add(glObject);
								}
								dtPortObject.put("portInfos", glPath);
								jsArr.add(dtPortObject);
							}
						}else{
							dtPortObject.put("ossglbh", "");
							dtPortObject.put("portInfos", glPath);
							jsArr.add(dtPortObject);
						}
	            	}
				}
			}
			/**
			 * TODO 查询任务中变动端子
			 */
			if (!"".equals(taskId)&&!"".equals(rwmxId)&&"1".equals(query_change_status)&&"".equals(query_free_status)) {
				int taskType = checkPortDao.getTaskType(taskId);
				if(taskType==0||taskType==4||taskType==10||taskType==11||taskType==12||taskType==13){
					//从OSS系统查实时列表
					List<Map> lightList = null;
					try{
					SwitchDataSourceUtil.setCurrentDataSource(jndi);
					lightList = checkPortDao.getOssGlList(param);
					SwitchDataSourceUtil.clearDataSource();
					}catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						SwitchDataSourceUtil.clearDataSource();
					}
					//从动态端子表查端子信息
					List<Map> dtsjList = checkPortDao.getAllTaskDtsjList(param);
					for(Map light : lightList){
						String portString = null==light.get("DZID")?"":light.get("DZID").toString();
				
						int flog = 0;
						for(Map dtsj : dtsjList){
							String portString1 = null==dtsj.get("DZID")?"":dtsj.get("DZID").toString();
							if(portString.equals(portString1)){
								flog++;
								JSONObject dtPortObject = new JSONObject();
								if (String.valueOf(light.get("GLBH")) != null) {
									String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
									dtPortObject.put("ossglbh", null==glbh?"":glbh);
//									/**
//									 * 通过光路编号查询FTTH装机照片详情
//									 */
//									Map photoMap = new HashMap();
//									photoMap.put("areaName", areaName);
//									photoMap.put("glbh", glbh);
//									Map resultMap = this.queryPhotoDetail(photoMap);
//									dtPortObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//									dtPortObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//									dtPortObject.put("dealTime", resultMap.get("dealTime"));//施工时间
//									dtPortObject.put("ossglbh", String.valueOf(light.get("GLBH")));
									dtPortObject.put("is_free_status", "0");
								} else{
									dtPortObject.put("ossglbh", "");
									dtPortObject.put("is_free_status", "1");
									dtPortObject.put("staffNo", "");
									dtPortObject.put("staffName", "");
									dtPortObject.put("dealTime", "");
								}
//								dtPortObject.put("ossglmc", "");
								if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
									dtPortObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
								} else {
									dtPortObject.put("ossglmc", "");
								}
								
								
								dtPortObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
								dtPortObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
								dtPortObject.put("eqpId", String.valueOf(dtsj.get("SBID")));
								dtPortObject.put("eqpNo", String.valueOf(dtsj.get("SBBM")));
								dtPortObject.put("eqpName", String.valueOf(dtsj.get("SBMC")));
								dtPortObject.put("address", String.valueOf(dtsj.get("ADDRESS")));
								dtPortObject.put("eqp_type_id", String.valueOf(dtsj.get("SBLX")));
								dtPortObject.put("eqp_type", String.valueOf(dtsj.get("RES_TYPE")));
								dtPortObject.put("portId", String.valueOf(light.get("DZID")));
								dtPortObject.put("portNo", String.valueOf(light.get("DZBM")));
								dtPortObject.put("glbh", String.valueOf(dtsj.get("GLBH")));
								dtPortObject.put("isH", String.valueOf(dtsj.get("H")));
								dtPortObject.put("rwmxId", String.valueOf(dtsj.get("DETAIL_ID")));
								dtPortObject.put("dtsj_id", String.valueOf(dtsj.get("ID")));
								dtPortObject.put("taskId", taskId);
								dtPortObject.put("end_time", "");
								dtPortObject.put("is_change_status", "1");
								//List<Map> gdList = checkPortDao.getGDList(dtsj);
								glPath.clear();
								//for (Map gd : gdList) {
//									glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
//									glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
//									glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
//									glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
//									glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
//									glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
//									glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
									glObject.put("glmc", String.valueOf(dtsj.get("GLMC")).trim());
									glObject.put("gdbh", String.valueOf(dtsj.get("GDBH")).trim());
									glObject.put("sggh", String.valueOf(dtsj.get("SGGH")).trim());
									glObject.put("pzgh", String.valueOf(dtsj.get("PZGH")).trim());
									glObject.put("gxgh", String.valueOf(dtsj.get("GQGH")).trim());
									glObject.put("glxz", String.valueOf(dtsj.get("XZ")).trim());
									glObject.put("bdsj", String.valueOf(dtsj.get("BDSJ")).trim());
									glPath.add(glObject);
								//}
								dtPortObject.put("portInfos", glPath);
								jsArr.add(dtPortObject);
								break;
							}
						}
						/*if(flog==0){
							JSONObject dtPortObject = new JSONObject();
							if (String.valueOf(light.get("GLBH")) != null) {
								dtPortObject.put("ossglbh", String.valueOf(light.get("GLBH")));
								dtPortObject.put("is_free_status", "0");
							} else{
								dtPortObject.put("ossglbh", "");
								dtPortObject.put("is_free_status", "1");
							}
							dtPortObject.put("eqpId", String.valueOf(light.get("SBID")));
							dtPortObject.put("eqpNo", String.valueOf(light.get("SBBM")));
							dtPortObject.put("eqpName", String.valueOf(light.get("SBMC")));
							dtPortObject.put("address", String.valueOf(light.get("ADDRESS")));
							dtPortObject.put("eqp_type_id", String.valueOf(light.get("SBLX")));
							dtPortObject.put("eqp_type", String.valueOf(light.get("RES_TYPE")));
							dtPortObject.put("portId", String.valueOf(light.get("DZID")));
							dtPortObject.put("portNo", String.valueOf(light.get("DZBM")));
							dtPortObject.put("glbh", String.valueOf(light.get("GLBH")));
							dtPortObject.put("isH", String.valueOf(light.get("H")));
							dtPortObject.put("rwmxId", String.valueOf(light.get("DETAIL_ID")));
							dtPortObject.put("taskId", String.valueOf(light.get("TASK_ID")));
							dtPortObject.put("end_time", "");
							dtPortObject.put("is_change_status", "1");
							glPath.clear();
							glObject.put("glmc", "");
							glObject.put("gdbh", "");
							glObject.put("sggh", "");
							glObject.put("pzgh", "");
							glObject.put("gxgh", "");
							glObject.put("glxz", "");
							glObject.put("bdsj", "");
							glPath.add(glObject);
							dtPortObject.put("portInfos", glPath);
							jsArr.add(dtPortObject);
						}*/
					}
				}else if(taskType==1){
					List<Map> dtsjList = checkPortDao.getTroubleBdList(param);
					JSONObject dtPortObject = new JSONObject();
					int flog = 0; 
					for (Map dtsj : dtsjList) {
						String dzId = null==dtsj.get("DZID")?"":dtsj.get("DZID").toString();
						Map a = new HashMap();
						a.put("portId", dzId);
						a.put("jndi",jndi);
						List<Map> lightList = null;
						try{
						SwitchDataSourceUtil.setCurrentDataSource(jndi);
						lightList = checkPortDao.getOssGlList(a);
						SwitchDataSourceUtil.clearDataSource();
						}catch (Exception e) {
							e.printStackTrace();
						}
						finally
						{
							SwitchDataSourceUtil.clearDataSource();
						}
						
						if(lightList.size()>0 && lightList != null){
							for (Map light : lightList) {
								if (String.valueOf(light.get("GLBH")) != null) {
									String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
									dtPortObject.put("ossglbh", null==glbh?"":glbh);
									/**
									 * 通过光路编号查询FTTH装机照片详情
									 */
									Map photoMap = new HashMap();
									photoMap.put("areaName", areaName);
									photoMap.put("glbh", glbh);
									Map resultMap = this.queryPhotoDetail(photoMap);
									dtPortObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
									dtPortObject.put("staffName", resultMap.get("staffName"));//施工人姓名
									dtPortObject.put("dealTime", resultMap.get("dealTime"));//施工时间
								} else{
									dtPortObject.put("ossglbh", "");
									dtPortObject.put("staffNo", "");
									dtPortObject.put("staffName", "");
									dtPortObject.put("dealTime", "");
								}
//								dtPortObject.put("ossglmc", "");
								if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
									dtPortObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
								} else {
									dtPortObject.put("ossglmc", "");
								}
								
								dtPortObject.put("cdno", null==light.get("CDNO")?"":light.get("CDNO").toString());
								dtPortObject.put("cdname", null==light.get("CDNAME")?"":light.get("CDNAME").toString());
								dtPortObject.put("jumpPortNo", null==light.get("JUMPPORTNO")?"":light.get("JUMPPORTNO").toString());
								dtPortObject.put("jumpPortSeqNo", null==light.get("JUMPPORTSEQNO")?"":light.get("JUMPPORTSEQNO").toString());
								dtPortObject.put("jumpUnitNo", null==light.get("JUMPUNITNO")?"":light.get("JUMPUNITNO").toString());
								dtPortObject.put("jumpUnitSeq", null==light.get("JUMPUNITSEQ")?"":light.get("JUMPUNITSEQ").toString());
								dtPortObject.put("jumpEqpName", null==light.get("JUMPEQPNAME")?"":light.get("JUMPEQPNAME").toString());
								dtPortObject.put("jumpDZBM", null==light.get("JUMPDZBM")?"":light.get("JUMPDZBM").toString());
								
								dtPortObject.put("eqpId", String.valueOf(dtsj.get("SBID")));
								dtPortObject.put("eqpNo", String.valueOf(dtsj.get("SBBM")));
								dtPortObject.put("eqpName", String.valueOf(dtsj.get("SBMC")));
								dtPortObject.put("address", String.valueOf(dtsj.get("ADDRESS")));
								dtPortObject.put("eqp_type_id", String.valueOf(dtsj.get("SBLX")));
								dtPortObject.put("eqp_type", String.valueOf(dtsj.get("RES_TYPE")));
								dtPortObject.put("portId", String.valueOf(dtsj.get("DZID")));
								dtPortObject.put("portNo", String.valueOf(dtsj.get("DZBM")));
								dtPortObject.put("glbh", String.valueOf(dtsj.get("GLBH")));
								dtPortObject.put("isH", String.valueOf(dtsj.get("H")));
								dtPortObject.put("rwmxId", String.valueOf(dtsj.get("DETAIL_ID")));
								dtPortObject.put("taskId", taskId);
								dtPortObject.put("end_time", "");
								dtPortObject.put("is_free_status", "");
								dtPortObject.put("is_change_status", "1");
								
								//List<Map> gdList = checkPortDao.getGDList(dtsj);
								glPath.clear();
								//for (Map gd : gdList) {
//									glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
//									glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
//									glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
//									glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
//									glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
//									glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
//									glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
									glObject.put("glmc", String.valueOf(dtsj.get("GLMC")).trim());
									glObject.put("gdbh", String.valueOf(dtsj.get("GDBH")).trim());
									glObject.put("sggh", String.valueOf(dtsj.get("SGGH")).trim());
									glObject.put("pzgh", String.valueOf(dtsj.get("PZGH")).trim());
									glObject.put("gxgh", String.valueOf(dtsj.get("GQGH")).trim());
									glObject.put("glxz", String.valueOf(dtsj.get("XZ")).trim());
									glObject.put("bdsj", String.valueOf(dtsj.get("BDSJ")).trim());
									glPath.add(glObject);
								//}
								dtPortObject.put("portInfos", glPath);
								jsArr.add(dtPortObject);
							}
						}else{
							dtPortObject.put("ossglbh", "");
							dtPortObject.put("portInfos", glPath);
							jsArr.add(dtPortObject);
						}
					}
				}
			}
			/**
			 * TODO 查询任务中占用端子
			 */
			if (!"".equals(taskId)&&!"".equals(rwmxId)&&"".equals(query_change_status)&&"0".equals(query_free_status)) {
				int taskType = checkPortDao.getTaskType(taskId);
				if(taskType==0||taskType==4||taskType==10||taskType==11||taskType==12||taskType==13){
					//从OSS系统查实时列表
					List<Map> lightList = null;
					try{
					SwitchDataSourceUtil.setCurrentDataSource(jndi);
					lightList = checkPortDao.getOssGlList(param);
					SwitchDataSourceUtil.clearDataSource();
					}catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						SwitchDataSourceUtil.clearDataSource();
					}
					/*
					for(Map list : lightList){
						System.out.println(list.get("DZID").toString()+"---"+list.get("DZBM").toString()+"---"+list.get("GLBH").toString());
					}
					*/
					//从动态端子表查端子信息
		//			List<Map> dtsjList = checkPortDao.getAllTaskDtsjList(param);
					if(lightList.size()>0 && lightList != null){
						for (Map light : lightList) {
							Object lightNo = light.get("GLBH");
							String portString = null==light.get("DZID")?"":light.get("DZID").toString();
							int flog = 0;
							if (lightNo != null && ! "".equals(lightNo) && ! " ".equals(lightNo)){
								JSONObject dtPortObject = new JSONObject();
//								for(Map dtsj : dtsjList){
//									String portString1 = null==dtsj.get("DZID")?"":dtsj.get("DZID").toString();
//									if(portString.equals(portString1)){
//										flog++;
//										String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
//										dtPortObject.put("ossglbh", null==glbh?"":glbh);
//										/**
//										 * 通过光路编号查询FTTH装机照片详情
//										 */
//										Map photoMap = new HashMap();
//										photoMap.put("areaName", areaName);
//										photoMap.put("glbh", glbh);
//										Map resultMap = this.queryPhotoDetail(photoMap);
//										dtPortObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//										dtPortObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//										dtPortObject.put("dealTime", resultMap.get("dealTime"));//施工时间
//										
//										if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
//											dtPortObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
//										} else {
//											dtPortObject.put("ossglmc", "");
//										}
//										
//										dtPortObject.put("cdno", null==light.get("CDNO")?"":light.get("CDNO").toString());
//										dtPortObject.put("cdname", null==light.get("CDNAME")?"":light.get("CDNAME").toString());
//										dtPortObject.put("jumpPortNo", null==light.get("JUMPPORTNO")?"":light.get("JUMPPORTNO").toString());
//										dtPortObject.put("jumpPortSeqNo", null==light.get("JUMPPORTSEQNO")?"":light.get("JUMPPORTSEQNO").toString());
//										dtPortObject.put("jumpUnitNo", null==light.get("JUMPUNITNO")?"":light.get("JUMPUNITNO").toString());
//										dtPortObject.put("jumpUnitSeq", null==light.get("JUMPUNITSEQ")?"":light.get("JUMPUNITSEQ").toString());
//										dtPortObject.put("jumpEqpName", null==light.get("JUMPEQPNAME")?"":light.get("JUMPEQPNAME").toString());
//										dtPortObject.put("jumpDZBM", null==light.get("JUMPDZBM")?"":light.get("JUMPDZBM").toString());
//										
//										dtPortObject.put("eqpId", String.valueOf(dtsj.get("SBID")));
//										dtPortObject.put("eqpNo", String.valueOf(dtsj.get("SBBM")));
//										dtPortObject.put("eqpName", String.valueOf(dtsj.get("SBMC")));
//										dtPortObject.put("address", String.valueOf(dtsj.get("ADDRESS")));
//										dtPortObject.put("eqp_type_id", String.valueOf(dtsj.get("SBLX")));
//										dtPortObject.put("eqp_type", String.valueOf(dtsj.get("RES_TYPE")));
//										dtPortObject.put("portId", String.valueOf(light.get("DZID")));
//										dtPortObject.put("portNo", String.valueOf(light.get("DZBM")));
//										dtPortObject.put("glbh", String.valueOf(dtsj.get("GLBH")));
//										dtPortObject.put("isH", String.valueOf(dtsj.get("H")));
//										dtPortObject.put("rwmxId", String.valueOf(dtsj.get("DETAIL_ID")));
//										dtPortObject.put("taskId", taskId);
//										dtPortObject.put("end_time", String.valueOf(dtsj.get("COMPLETE_TIME")));
//										dtPortObject.put("is_free_status", "0");
//										dtPortObject.put("is_change_status", "");
//										//List<Map> gdList = checkPortDao.getGDList(dtsj);
//										glPath.clear();
//										//for (Map gd : gdList) {
////											glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
////											glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
////											glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
////											glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
////											glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
////											glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
////											glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
//											glObject.put("glmc", String.valueOf(dtsj.get("GLMC")).trim());
//											glObject.put("gdbh", String.valueOf(dtsj.get("GDBH")).trim());
//											glObject.put("sggh", String.valueOf(dtsj.get("SGGH")).trim());
//											glObject.put("pzgh", String.valueOf(dtsj.get("PZGH")).trim());
//											glObject.put("gxgh", String.valueOf(dtsj.get("GQGH")).trim());
//											glObject.put("glxz", String.valueOf(dtsj.get("XZ")).trim());
//											glObject.put("bdsj", String.valueOf(dtsj.get("BDSJ")).trim());
//											glPath.add(glObject);
//										//}
//										dtPortObject.put("portInfos", glPath);
//										jsArr.add(dtPortObject);
//									}
//								}
								if(flog == 0){
//									dtPortObject.put("ossglmc", "");
									String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
									dtPortObject.put("ossglbh", null==glbh?"":glbh);
//									/**
//									 * 通过光路编号查询FTTH装机照片详情
//									 */
//									Map photoMap = new HashMap();
//									photoMap.put("areaName", areaName);
//									photoMap.put("glbh", glbh);
//									Map resultMap = this.queryPhotoDetail(photoMap);
//									dtPortObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
//									dtPortObject.put("staffName", resultMap.get("staffName"));//施工人姓名
//									dtPortObject.put("dealTime", resultMap.get("dealTime"));//施工时间
									
									if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
										dtPortObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
									} else {
										dtPortObject.put("ossglmc", "");
									}
									
									dtPortObject.put("dtsj_id", "");
									dtPortObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
									dtPortObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
									dtPortObject.put("eqpId", String.valueOf(light.get("SBID")));
									dtPortObject.put("eqpNo", String.valueOf(light.get("SBBM")));
									dtPortObject.put("eqpName", String.valueOf(light.get("SBMC")));
									dtPortObject.put("address", String.valueOf(light.get("ADDRESS")));
									dtPortObject.put("eqp_type_id", String.valueOf(light.get("SBLX")));
									dtPortObject.put("eqp_type", String.valueOf(light.get("RES_TYPE")));
									dtPortObject.put("portId", String.valueOf(light.get("DZID")));
									dtPortObject.put("portNo", String.valueOf(light.get("DZBM")));
									dtPortObject.put("glbh", String.valueOf(light.get("GLBH")));
									dtPortObject.put("isH", String.valueOf(light.get("H")));
									dtPortObject.put("rwmxId", String.valueOf(light.get("DETAIL_ID")));
									dtPortObject.put("taskId", taskId);
									dtPortObject.put("end_time", String.valueOf(light.get("COMPLETE_TIME")));
									dtPortObject.put("is_free_status", "0");
									dtPortObject.put("is_change_status", "");
									glPath.clear();
									glObject.put("glmc", "");
									glObject.put("gdbh", "");
									glObject.put("sggh", "");
									glObject.put("pzgh", "");
									glObject.put("gxgh", "");
									glObject.put("glxz", "");
									glObject.put("bdsj", "");
									glPath.add(glObject);
									dtPortObject.put("portInfos", glPath);
									jsArr.add(dtPortObject);
								}
							}
						}
					}
				}
				if (taskType==1){
					List<Map> dtsjList = checkPortDao.getTroubleList(param);
					JSONObject dtPortObject = new JSONObject();
					for (Map dtsj : dtsjList) {
						String dzId = dtsj.get("DZID").toString();
						Map a = new HashMap();
						a.put("portId", dzId);
						a.put("jndi",jndi);
						List<Map> lightList = null;
						try{
						SwitchDataSourceUtil.setCurrentDataSource(jndi);
						lightList = checkPortDao.getOssGlList(a);
						SwitchDataSourceUtil.clearDataSource();
					}catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						SwitchDataSourceUtil.clearDataSource();
					}
						if(lightList.size()>0 && lightList != null){
							for (Map light : lightList) {
								if (!"".equals(String.valueOf(light.get("GLBH")))&&!" ".equals(String.valueOf(light.get("GLBH")))&&String.valueOf(light.get("GLBH"))!=null){
//									dtPortObject.put("ossglmc", "");
									String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
									/**
									 * 通过光路编号查询FTTH装机照片详情
									 */
									Map photoMap = new HashMap();
									photoMap.put("areaName", areaName);
									photoMap.put("glbh", glbh);
									Map resultMap = this.queryPhotoDetail(photoMap);
									dtPortObject.put("staffNo", resultMap.get("staffNo"));//施工人账号
									dtPortObject.put("staffName", resultMap.get("staffName"));//施工人姓名
									dtPortObject.put("dealTime", resultMap.get("dealTime"));//施工时间
									
									dtPortObject.put("ossglbh", null==glbh?"":glbh);
									if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
										dtPortObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
									} else {
										dtPortObject.put("ossglmc", "");
									}
									
									dtPortObject.put("cdno", null==light.get("CDNO")?"":light.get("CDNO").toString());
									dtPortObject.put("cdname", null==light.get("CDNAME")?"":light.get("CDNAME").toString());
									dtPortObject.put("jumpPortNo", null==light.get("JUMPPORTNO")?"":light.get("JUMPPORTNO").toString());
									dtPortObject.put("jumpPortSeqNo", null==light.get("JUMPPORTSEQNO")?"":light.get("JUMPPORTSEQNO").toString());
									dtPortObject.put("jumpUnitNo", null==light.get("JUMPUNITNO")?"":light.get("JUMPUNITNO").toString());
									dtPortObject.put("jumpUnitSeq", null==light.get("JUMPUNITSEQ")?"":light.get("JUMPUNITSEQ").toString());
									dtPortObject.put("jumpEqpName", null==light.get("JUMPEQPNAME")?"":light.get("JUMPEQPNAME").toString());
									dtPortObject.put("jumpDZBM", null==light.get("JUMPDZBM")?"":light.get("JUMPDZBM").toString());
									
									dtPortObject.put("eqpId", String.valueOf(dtsj.get("SBID")));
									dtPortObject.put("eqpNo", String.valueOf(dtsj.get("SBBM")));
									dtPortObject.put("eqpName", String.valueOf(dtsj.get("SBMC")));
									dtPortObject.put("address", String.valueOf(dtsj.get("ADDRESS")));
									dtPortObject.put("eqp_type_id", String.valueOf(dtsj.get("SBLX")));
									dtPortObject.put("eqp_type", String.valueOf(dtsj.get("RES_TYPE")));
									dtPortObject.put("portId", String.valueOf(dtsj.get("DZID")));
									dtPortObject.put("portNo", String.valueOf(dtsj.get("DZBM")));
									dtPortObject.put("glbh", String.valueOf(dtsj.get("GLBH")));
									dtPortObject.put("isH", String.valueOf(dtsj.get("H")));
									dtPortObject.put("rwmxId", String.valueOf(dtsj.get("DETAIL_ID")));
									dtPortObject.put("taskId", taskId);
									dtPortObject.put("end_time", String.valueOf(dtsj.get("COMPLETE_TIME")));
									dtPortObject.put("is_free_status", "0");
									dtPortObject.put("is_change_status", "");
									List<Map> gdList = checkPortDao.getGDList(dtsj);
									glPath.clear();
									for (Map gd : gdList) {
										glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
										glObject.put("gdbh", String.valueOf(gd.get("GDBH")).trim());
										glObject.put("sggh", String.valueOf(gd.get("SGGH")).trim());
										glObject.put("pzgh", String.valueOf(gd.get("PZGH")).trim());
										glObject.put("gxgh", String.valueOf(gd.get("GQGH")).trim());
										glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
										glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
										glPath.add(glObject);
									}
									
									dtPortObject.put("portInfos", glPath);
									jsArr.add(dtPortObject);
								}
							}
						}
					}
				}
			}
            result.put("totalPage", "20");
	        result.put("totalSize", "200");
			result.put("currPage", currPage);
			result.put("pageSize", pageSize);
			result.put("ports", jsArr);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("error", e.toString());
			result.put("desc", "查询端子失败，请联系管理员。");
			logger.info(e.toString());
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}
	
	/**
	 * 通过光路编号查询FTTH装机照片详情
	 * @param map
	 * @return
	 */
	public Map queryPhotoDetail(Map map){
		//封装json参数
		JSONArray photoArr = new JSONArray();
		JSONObject object = new JSONObject();
		object.put("areaName", null==map.get("areaName")?"":map.get("areaName"));
		object.put("glbh", null==map.get("glbh")?"":map.get("glbh"));
		photoArr.add(object);
		
		JSONObject photoObject = new JSONObject();
		photoObject.put("param", photoArr);
		//执行webservice服务
		String result = "";
		try {
			result = electronArchivesService.queryPhotoDetail(photoObject.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//处理webservice返回结果
		Map resultMap = new HashMap();
		JSONObject json = JSONObject.fromObject(result);
		JSONArray dataObject = json.getJSONArray("data");
		if(dataObject.size()>0 && dataObject != null){
			JSONObject data = (JSONObject)dataObject.get(0);
			String glbh = null==data.get("glbh")?"":data.getString("glbh");
			String areaName = null==data.get("areaName")?"":data.getString("areaName");
			String staffNo = null==data.get("staffNo")?"":data.getString("staffNo");
			String staffName = null==data.get("staffName")?"":data.getString("staffName");//施工人
			String dealTime = null==data.get("dealTime")?"":data.getString("dealTime");//施工时间
			resultMap.put("glbh", glbh);
			resultMap.put("areaName", areaName);
			resultMap.put("staffNo", staffNo);
			resultMap.put("staffName", staffName);
			resultMap.put("dealTime", dealTime);
		}else{
			resultMap.put("glbh", "");
			resultMap.put("areaName", "");
			resultMap.put("staffNo", "");
			resultMap.put("staffName", "");
			resultMap.put("dealTime", "");
		}
		return resultMap;
	}
	/**
	 * 获取设备的成端信息
	 */
	public String getEqpCDInfo(String jsonStr){
		
		JSONObject result = new JSONObject();
		result.put("result", "000");
		
		try{
			JSONObject json = JSONObject.fromObject(jsonStr);
			//接收参数
			String eqpId = json.getString("eqpId");// 设备ID
			String area_id = json.getString("areaId");//地市id
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			//查询参数
			Map param = new HashMap();
			param.put("jndi", jndi);
			param.put("eqpId", eqpId);
			//封装查询结果
			result.put("eqpId", eqpId);
			
			JSONArray cdsArray = new JSONArray();
			
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			List<Map> eqpCDList = checkPortDao.getEqpCDInfo(param);
			SwitchDataSourceUtil.clearDataSource();
			//封装数据
			if(eqpCDList.size()>0 && eqpCDList != null){
				//上一个端子信息
				String lastDZID = "";
				String lastDZBM = "";
				String lastCDNO = "";
				String lastCDNAME = "";
				
				JSONArray portArray = new JSONArray();
				JSONObject cdObject = new JSONObject();
				
				for(Map cdMap : eqpCDList){
					//当前端子信息
					String currentDZID = null==cdMap.get("DZID")?"":cdMap.get("DZID").toString();
					String currentDZBM = null==cdMap.get("DZBM")?"":cdMap.get("DZBM").toString();
					String currentCDNO = null==cdMap.get("CDNO")?"":cdMap.get("CDNO").toString();
					String currentCDNAME = null==cdMap.get("CDNAME")?"":cdMap.get("CDNAME").toString();
					//第一个端子
					if("".equals(lastCDNO) && "".equals(lastCDNAME)){
						lastCDNO = currentCDNO;
						lastCDNAME = currentCDNAME;
						lastDZID = currentDZID;
						lastDZBM = currentDZBM;
					}
					//光缆编号相同，认为是同一框端子
					if(lastCDNO.equals(currentCDNO)){
						JSONObject portObject = new JSONObject();
						portObject.put("portId", currentDZID);
						portObject.put("portNo", currentDZBM);
						portArray.add(portObject);
					}else{
						//同框最后一个端子
						JSONObject port = (JSONObject)portArray.get(portArray.size()-1);
						String dzbm = null==port.get("portNo")?"":port.get("portNo").toString();
						//封装数据
						cdObject.put("cdNo", lastCDNO);
						cdObject.put("cdName", lastCDNAME);
						//cdObject.put("uInfo", "U" + lastDZBM.split("/")[0] + "框" + lastDZBM.split("/")[1] + "-" + dzbm.split("/")[1] + "端子");
						//根据端子编码拆分端子两边（"/"）,有些端子编码奇特,暂时置空
						String[] lastDZBMArr = lastDZBM.split("/");
						String[] dzbmArr = dzbm.split("/");
						//重组uInfo
						StringBuffer sb = new StringBuffer();      
						if(lastDZBMArr.length>1 && dzbmArr.length>1){
							sb.append("U").append(lastDZBMArr[0]).append("框").append(lastDZBMArr[1]).append("-").append(dzbmArr[1]).append("端子");
							cdObject.put("uInfo", sb.toString());
						}else{
							cdObject.put("uInfo", "");
						}
						cdObject.put("ports", portArray);
						//清空端子集合
						portArray.clear();
						//非同框第一个端子
						JSONObject portObject = new JSONObject();
						portObject.put("portId", currentDZID);
						portObject.put("portNo", currentDZBM);
						portArray.add(portObject);
						//切换非同框端子第一个端子
						lastCDNO = currentCDNO;
						lastCDNAME = currentCDNAME;
						lastDZID = currentDZID;
						lastDZBM = currentDZBM;
						
						cdsArray.add(cdObject);
					}
				}
				
				result.put("cds", cdsArray);
			}else{
				result.put("cds", "[]");
			}
			
		}catch(Exception e){
			result.put("error", e.toString());
			logger.info(e.toString());
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}

	@Override
	public String getPortInfos(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			String DZID = json.getString("DZID");
			String SBID = json.getString("SBID");
			String areaId = json.getString("areaId");

			
			/**
			 * 查询参数
			 */
			Map param = new HashMap();
			
			param.put("DZID", DZID);
			param.put("SBID", SBID);
			param.put("area_id", areaId);
			JSONArray glPath = new JSONArray();
			JSONObject glObject = new JSONObject();
			 /**
             * 获取工单等信息
             */
			List<Map> gdList = checkPortDao.getGDList(param);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			glPath.clear();
			for (Map gd : gdList) {
				String glbh=String.valueOf(gd.get("GLBH")).trim();
				param.put("GLBH", glbh);
				glObject.put("glbh",glbh);
			
				Map paramMap=getThreeJobNumber(param);
				glObject.put("sggh",paramMap.get("sg_staffNo").toString());
				glObject.put("pzgh", paramMap.get("pz_staffNo").toString());
				glObject.put("gxgh", paramMap.get("gx_staffNo").toString());
				glObject.put("gdjgsj", String.valueOf(gd.get("GDJGSJ")).trim());
				glObject.put("glxz", String.valueOf(gd.get("XZ")).trim());
				glObject.put("bdsj", String.valueOf(gd.get("BDSJ")).trim());
				glObject.put("glmc", String.valueOf(gd.get("GLMC")).trim());
				glPath.add(glObject);
			}
			//对GDJGSJ（工单竣工时间排序，最新的在最上面），如果工单竣工时间为空，则忽略，不考虑
			for(int i=0;i<glPath.size()-1;i++){
				for(int j=i+1;j<glPath.size();j++){
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					JSONObject obj1 =  (JSONObject)glPath.get(i);
					Date last_change_time1 = null;
					if(!"".equals(obj1.get("gdjgsj"))){
						last_change_time1 = sdf.parse(obj1.get("gdjgsj").toString());
					}
					JSONObject obj2 =  (JSONObject)glPath.get(j);
					Date last_change_time2 = null;
					if(!"".equals(obj2.get("gdjgsj"))){
						last_change_time2 = sdf.parse(obj2.get("gdjgsj").toString());
					}
					if(last_change_time1==null){					
						JSONObject objTemp = obj1;
						obj1 = obj2;
						obj2 = objTemp;
						glPath.set(i, obj1);
						glPath.set(j,obj2);
					}
					if(last_change_time2==null){
						continue;
					}
					if(last_change_time1.before(last_change_time2)){
						JSONObject objTemp = obj1;
						obj1 = obj2;
						obj2 = objTemp;
						glPath.set(i, obj1);
						glPath.set(j,obj2);
					}
				}
			}		
			
			result.put("portInfos", glPath);
		}catch (Exception e) {
				e.printStackTrace();
				result.put("error","光路信息查询失败");
				logger.info(e.toString());
				SwitchDataSourceUtil.clearDataSource();
			}
			return result.toString();
	}

	@Override
	public String getPortDetail(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONArray jsArr = new JSONArray();
		JSONArray jsArr1 = new JSONArray();

		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			// 接收参数
			String area_id = json.getString("areaId");// 地市id
			String GLBH="";
			if(json.containsKey("GLBH")){//判断是否含有GLBH（光路编号）此字段，有的话直接获取值
				GLBH = json.getString("GLBH");//OSS光路编号
			}			
		//	String GLBH = json.getString("GLBH");// 地市id
			/**
			 * 接收的参数
			 */
			String areaName = checkPortDao.getAreaNameByAreaId(area_id);// 地市名

			String DZID = json.getString("DZID");
			String SBID = json.getString("SBID");
			String jndi = cableInterfaceDao.getDBblinkName(area_id);

			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			// 查询参数
			Map param = new HashMap();
			param.put("jndi", jndi);
			param.put("DZID", DZID);
			param.put("SBID", SBID);
			param.put("GLBH", GLBH);
			param.put("area_id", area_id);

			JSONArray cdsArray = new JSONArray();
			//List<Map> lightList = null;
			List<Map> eqpDDList =null;
			List<Map> eqpJumpList =null;
			try {
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				//lightList = checkPortDao.getOssGlDetailList(param);
				eqpDDList = checkPortDao.getEqpDDInfo(param);//对端设备信息
			    eqpJumpList = checkPortDao.getEqpJumpInfo(param);//跳接设备信息
				SwitchDataSourceUtil.clearDataSource();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				SwitchDataSourceUtil.clearDataSource();
			}
			/*
			 * for(Map list : lightList){
			 * System.out.println(list.get("DZID").toString
			 * ()+"---"+list.get("DZBM"
			 * ).toString()+"---"+list.get("GLBH").toString()); }
			 */
			// 从动态端子表查端子信息
			// List<Map> dtsjList = checkPortDao.getAllTaskDtsjList(param);
			
			if (eqpJumpList.size() > 0 && eqpJumpList != null) {
				for (Map eqpJump : eqpJumpList) {
					Object lightNo = eqpJump.get("GLBH");
					String portString = null == eqpJump.get("DZID") ? "" : eqpJump
							.get("DZID").toString();
					JSONObject dtPortObject = new JSONObject();
					if (lightNo != null && !"".equals(lightNo)
							&& !" ".equals(lightNo)) {
						String glbh = null == eqpJump.get("GLBH") ? "" : eqpJump
								.get("GLBH").toString();
						dtPortObject.put("ossglbh", null == glbh ? "" : glbh);
						/**
						 * 通过光路编号查询FTTH装机照片详情
						 */
						/*Map photoMap = new HashMap();
						photoMap.put("areaName", areaName);
						photoMap.put("glbh", glbh);
						Map resultMap = this.queryPhotoDetail(photoMap);
						dtPortObject.put("staffNo", resultMap.get("staffNo"));// 施工人账号
						dtPortObject.put("staffName", resultMap
								.get("staffName"));// 施工人姓名
						dtPortObject.put("dealTime", resultMap.get("dealTime"));// 施工时间*/
						dtPortObject.put("jumpEqpName", null == eqpJump
								.get("JUMP_EQPNAME") ? "" : eqpJump.get(
								"JUMP_EQPNAME").toString());
						dtPortObject.put("jumpDZBM", null == eqpJump
								.get("JUMP_PORT_NO") ? "" : eqpJump.get("JUMP_PORT_NO")
								.toString());
						jsArr.add(dtPortObject);
					}
				}

			}
			if(eqpDDList!=null && eqpDDList.size()>0){
				for(Map eqpDD : eqpDDList){
					JSONObject eqpDDObject = new JSONObject();
					String dd_sbbm = null==eqpDD.get("DD_SBBM") ? "": eqpDD.get("DD_SBBM").toString();
					String dd_name = null==eqpDD.get("DD_NAME") ? "": eqpDD.get("DD_NAME").toString();
					String dd_port_no = null==eqpDD.get("DD_PORT_NO") ? "": eqpDD.get("DD_PORT_NO").toString();
					eqpDDObject.put("dd_sbbm", dd_sbbm);
					eqpDDObject.put("dd_name", dd_name);
					eqpDDObject.put("dd_port_no", dd_port_no);
					jsArr1.add(eqpDDObject);
				}
			}
			result.put("ports", jsArr);
			result.put("dd_eqp", jsArr1);
	 
			List li=new ArrayList();
			Map map=new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			Map paramMap=getThreeJobNumber(param);
			map.put("sg_staffNo", paramMap.get("sg_staffNo").toString());
			map.put("sg_staffName", paramMap.get("sg_staffName").toString());
			map.put("sg_dealTime", paramMap.get("sg_dealTime").toString());
			map.put("gx_staffNo", paramMap.get("gx_staffNo").toString());
			map.put("gx_staffName",paramMap.get("gx_staffName").toString());
			map.put("gx_dealTime", paramMap.get("gx_dealTime").toString());
			li.add(map);
			result.put("sggx", li);
		
			//获取端子最新记录
			JSONArray jsonArr1 = new JSONArray();
			List<Map> lastChangePortsList = new ArrayList<Map>();
			lastChangePortsList = checkPortDao.getLastChangePortsList(param);
			if(lastChangePortsList.size() > 0 && lastChangePortsList != null){	
				for(Map lastChangePorts:lastChangePortsList){
					JSONObject lastChangePortsObject = new JSONObject();
					String port_no = null == lastChangePorts.get("PORT_NO")?"":lastChangePorts.get("PORT_NO").toString();
					String record_type = null == lastChangePorts.get("RECORD_TYPE")?"":lastChangePorts.get("RECORD_TYPE").toString();
					String descript = null == lastChangePorts.get("DESCRIPT")?"":lastChangePorts.get("DESCRIPT").toString();
					String create_time = null == lastChangePorts.get("CREATE_TIME")?"":lastChangePorts.get("CREATE_TIME").toString();
					String remark = null == lastChangePorts.get("REMARK")?"":lastChangePorts.get("REMARK").toString();
					String photo_path = null == lastChangePorts.get("PHOTO_PATH")?"":lastChangePorts.get("PHOTO_PATH").toString();
					String staff_name = null == lastChangePorts.get("STAFF_NAME")?"":lastChangePorts.get("STAFF_NAME").toString();
					lastChangePortsObject.put("staff_name", staff_name);
					lastChangePortsObject.put("port_no", port_no);
					lastChangePortsObject.put("record_type", record_type);
					lastChangePortsObject.put("descript", descript);
					lastChangePortsObject.put("create_time", create_time);
					lastChangePortsObject.put("remark", remark);
					lastChangePortsObject.put("photo_path", photo_path);
					jsonArr1.add(lastChangePortsObject);
				}
			}
			result.put("lastPortRecord", jsonArr1);
			String areaCode=checkPortDao.getAreaCodeByAreaId(area_id);
			String ossAreaCode=areaCode.toLowerCase().concat(".js.cn");

			try {
				this.staff_id = json.getString("staffId")==null?"":json.getString("staffId").trim();
				this.sn = json.getString("sn")==null?"":json.getString("sn").trim();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String frouteInfo=this.qryFOptRoute(GLBH,ossAreaCode);
			result.put("ForuteInfo", frouteInfo);	
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", e.toString());
			logger.info(e.toString());
			SwitchDataSourceUtil.clearDataSource();
		} finally {
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}
	//获取三个工号 配置工号 施工工号  更纤工号 先通过设备id和端子id获取
	public Map getThreeJobNumber(Map param){
		//通过设备id和设备编码到动态端子表获取上月的主键id，配置工号 通过更新时间排序
		Map<String, String> resultMap=checkPortDao.getThreeJobNumber(param);
		String staff_id="";//施工人账号id
		String pzgh="";//配置工号
		String orderId="";//工单ID
		if(resultMap!=null&&resultMap.size()>0){
			 staff_id=resultMap.get("OTHER_SYSTEM_STAFF_ID");//施工人账号id
			 pzgh=resultMap.get("PZGH");//配置工号
			 orderId=resultMap.get("ORDER_ID");//工单ID
		}

		Map map=new HashMap();
		map.put("staff_id", staff_id);
		map.put("pzgh", pzgh);
		map.put("orderId", orderId);
		//施工人账号id可能为空（OTHER_SYSTEM_STAFF_ID）》》staff_id
		//获取施工信息和配置信息
		Map map2=new HashMap();
		List<Map<String, String>> sg_pz=checkPortDao.getSgPzInfo(map);	
		if(sg_pz!=null&&sg_pz.size()>0){
			if("".equals(staff_id)||staff_id==null){
				map2.put("sg_staffNo", "");
				map2.put("sg_staffName", "");
				map2.put("sg_dealTime", "");
				map2.put("pz_staffNo", sg_pz.get(0).get("STAFF_NBR"));
				map2.put("pz_staffName", sg_pz.get(0).get("STAFF_NAME"));
				map2.put("pz_dealTime", "");
			}else{
				map2.put("sg_staffNo", sg_pz.get(0).get("STAFF_NBR"));
				map2.put("sg_staffName", sg_pz.get(0).get("STAFF_NAME"));
				map2.put("sg_dealTime", "");
				map2.put("pz_staffNo", sg_pz.get(1).get("STAFF_NBR"));
				map2.put("pz_staffName", sg_pz.get(1).get("STAFF_NAME"));
				map2.put("pz_dealTime", "");
			}
		}else{
			map2.put("sg_staffNo", "");
			map2.put("sg_staffName", "");
			map2.put("sg_dealTime", "");	
			map2.put("pz_staffNo", "");
			map2.put("pz_staffName", "");
			map2.put("pz_dealTime", "");
		}
		//通过工单id获取更纤信息--更纤人
		Map<String, String> gxMap=checkPortDao.getGxInfo(map);
		String gx_staffName="";
		if(gxMap!=null&&gxMap.size()>0){
			gx_staffName=gxMap.get("CHANGE_FIBER_OPER");
		}
		map2.put("gx_staffNo", "");
		map2.put("gx_staffName", gx_staffName);
		map2.put("gx_dealTime", "");
		return map2;
	}
	
	
	
	
	
	public String qryFOptRoute(String optRoadNo,String ossAreaCode){
		StringBuffer pStr=new StringBuffer();
		pStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pStr.append("<reqInfo>");
		pStr.append("<optRoadNo>").append(optRoadNo).append("</optRoadNo>");
		pStr.append("<areaCode>").append(ossAreaCode).append("</areaCode>");
		pStr.append("</reqInfo>");
		String resutlStr=null;
		try {
			String esbHeader=this.getEsbHeader("qryFOptRoute");
			OSSInterfaceForPHONEServiceLocator ossLocator = null;
			ossLocator = new OSSInterfaceForPHONEServiceLocator();
			IOSSInterfaceForPHONE oSSInterfaceForPHONE=ossLocator.getOSSInterfaceForPHONEPort();
			OSSInterfaceForPHONEServiceSoapBindingStub stub=(OSSInterfaceForPHONEServiceSoapBindingStub)ossLocator.getOSSInterfaceForPHONEPort();
			StringReader sr = new StringReader(esbHeader);
			InputSource is = new InputSource(sr);
			org.w3c.dom.Element element =  (org.w3c.dom.Element) DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(is).getElementsByTagName("Esb")
					.item(0);
			stub.setHeader(new SOAPHeaderElement(element));
			stub.setTimeout(30 * 1000);
			resutlStr=stub.qryFOptRoute(pStr.toString());
			System.out.println(resutlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		/**解析获得XML结果**/
		Document doc=null;
		try {
			doc=DocumentHelper.parseText(resutlStr);
		} catch (DocumentException e) {
			return null;
		}
		Element returnInfoE= doc.getRootElement();
		Element resultInfoE=returnInfoE.element("resultInfo");
		String returnCode  =resultInfoE.elementText("returnCode");//OSS光路查询结果码
		String codeDesc    =resultInfoE.elementText("codeDesc");//描述信息
		if(!returnCode.equals("000")){
			return null;
		}
		//新增界面显示客户信息
	    String clientName = null;//客户名称
	    String clientAddr = null;//客户地址
	    String routeName = null;//客户地址
	    Element basicInfo=returnInfoE.element("basicInfo");
	    clientName=basicInfo.elementText("clientName");//客户名称
	    clientAddr=basicInfo.elementText("clientAddr");//客户地址		   
	    routeName=basicInfo.elementText("routeName");//路由名称
	    JSONObject rejson=new JSONObject();
	    JSONObject rebody=new JSONObject();
	    rebody.put("clientName", SensitiveUtil.hideSensitive(clientName,""));
	    rebody.put("clientAddr", SensitiveUtil.hideSensitive(clientAddr,"address"));
	    rebody.put("routeName", routeName);
	    JSONArray jsonArr = new JSONArray();
	    JSONObject nodes=new JSONObject();

	    
	    
		Element routeInfoE = returnInfoE.element("routeInfo");
		Iterator ite = routeInfoE.elementIterator("res");
		Element resE=null;
		String seq=null;//序号
		String parentResId=null;//资源ID
		String parentResNo=null;//资源的编码
		String parentResName=null;//资源的名称
		String parentResTypeId=null;//的资源类型ID
		String resId=null;//端子ID
		String resType=null;//端子的类型
		String resTypeId=null;//端子类型ID
		String resName=null;//端子名称
		String resNo=null;//端子编码
		String optRoadId=null;//光路的ID
		
		optRoadId=routeInfoE.attributeValue("optRoadId");
		optRoadNo=routeInfoE.attributeValue("optRoadNo");
	    rebody.put("optRoadId", optRoadId);
	    rebody.put("optRoadNo", optRoadNo);
		while(ite.hasNext()){
			resE=(Element)ite.next();
			seq=resE.attributeValue("seq");
			parentResTypeId=resE.attributeValue("parentResTypeId");
			parentResId=resE.attributeValue("parentResId");
			parentResNo=resE.attributeValue("parentResNo");
			parentResName=resE.attributeValue("parentResName");
			resId=resE.attributeValue("resId");
			resType=resE.attributeValue("resType");
			resTypeId=resE.attributeValue("resTypeId");
			resName=resE.attributeValue("resName");
			resNo=resE.attributeValue("resNo");
			nodes.put("seq", seq);
			nodes.put("parentResId", parentResId);
			nodes.put("parentResNo", parentResNo);
			nodes.put("parentResTypeId", parentResTypeId);
			if(parentResTypeId.equals("2350")){	
			 nodes.put("parentResType", "分光器");
			}
			
			nodes.put("resId", resId);
			nodes.put("resName", resName);
			nodes.put("resTypeId", resTypeId);
			nodes.put("parentResName", parentResName);
			nodes.put("resNo", resNo);
			nodes.put("resType", resType);
			jsonArr.add(nodes);
		}
		//rejson.put("body", rebody);
		rebody.put("NODES", jsonArr);

	    
	    try {
	    	/**
			 * 敏感日志对象
			 */
			SensitiveLog s = SensitiveLog.createSensitiveLog("", "", clientName, jsonArr.toString(), "select", clientAddr,
					this.staff_id,this.sn);
			SensitiveLog.getList().add(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return rebody.toString();
	}
	
	/**
	 * 生成服务总线接口的报头
	 * @param servCode  服务名称
	 * @return 服务总线报头xml字符串
	 */
	public String getEsbHeader(String servCode){
		Date d=new Date();
		StringBuffer h=new StringBuffer();
		h.append("<soapenv:Header>");
		h.append("<Esb>");
		h.append("<Route>");
		h.append("<Sender>32.1121</Sender>");
		h.append("<Time>").append(d.toLocaleString()).append("</Time>");
		h.append("<ServCode>32.1103.").append(servCode).append("</ServCode>");
		h.append("<MsgId>").append(d.getTime()).append("</MsgId>");
		h.append("<AuthCode>").append("</AuthCode>");
		h.append("<TransId>").append(d.getTime()).append("</TransId>");
		h.append("<CarryType>0</CarryType>");
		h.append("<ServTestFlag>0</ServTestFlag>");
		h.append("</Route>");
		h.append("</Esb>");
		h.append("</soapenv:Header>");
		return h.toString();
	}

	@Override
	public String getEqpAddress(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONArray jsArr = new JSONArray();
		JSONArray jsArr1 = new JSONArray();

		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			// 接收参数
			String area_id = json.getString("areaId");// 地市id
						
			/**
			 * 接收的参数
			 */
			String areaName = checkPortDao.getAreaNameByAreaId(area_id);// 地市名

			String SBID = json.getString("SBID");
			String jndi = cableInterfaceDao.getDBblinkName(area_id);

			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			// 查询参数
			Map param = new HashMap();
			param.put("jndi", jndi);
			param.put("SBID", SBID);

			JSONArray cdsArray = new JSONArray();
			List<Map> eqpAddList =null;
			try {
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				//lightList = checkPortDao.getOssGlDetailList(param);
				eqpAddList = checkPortDao.getEqpAdd(param);//对端设备信息
		
				SwitchDataSourceUtil.clearDataSource();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				SwitchDataSourceUtil.clearDataSource();
			}
			/*
			 * for(Map list : lightList){
			 * System.out.println(list.get("DZID").toString
			 * ()+"---"+list.get("DZBM"
			 * ).toString()+"---"+list.get("GLBH").toString()); }
			 */
			// 从动态端子表查端子信息
			// List<Map> dtsjList = checkPortDao.getAllTaskDtsjList(param);
			
			if (eqpAddList.size() > 0 && eqpAddList != null) {
				for (Map eqpadd : eqpAddList) {					
							JSONObject dtPortObject = new JSONObject();
						dtPortObject.put("LOCATION_ID", null == eqpadd
								.get("LOCATION_ID") ? "" : eqpadd.get(
								"LOCATION_ID").toString());
						dtPortObject.put("ADD1", null == eqpadd
								.get("ADD1") ? "" : eqpadd.get(
								"ADD1").toString());
						dtPortObject.put("FULL_NAME", null == eqpadd
								.get("FULL_NAME") ? "" : eqpadd.get(
								"FULL_NAME").toString());
						jsArr.add(dtPortObject);	
				}

			}
			
			
			result.put("EqpAddress", jsArr);
		
		} catch (Exception e) {
			result.put("error", e.toString());
			logger.info(e.toString());
			SwitchDataSourceUtil.clearDataSource();
		} finally {
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}

	@Override
	public String getFOptRoute(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			// 接收参数
			String area_id = json.getString("areaId");// 地市id
			String GLBH="";
			if(json.containsKey("GLBH")){//判断是否含有GLBH（光路编号）此字段，有的话直接获取值
				GLBH = json.getString("GLBH");//光路编号
			}			
		//	String GLBH = json.getString("GLBH");// 地市id
			/**
			 * 接收的参数
			 */
			String areaCode=checkPortDao.getAreaCodeByAreaId(area_id);
			String ossAreaCode=areaCode.toLowerCase().concat(".js.cn");
			String frouteInfo=this.qryFOptRoute(GLBH,ossAreaCode);
			result.put("ForuteInfo", frouteInfo);	
		} catch (Exception e) {
			result.put("error", e.toString());
			logger.info(e.toString());		
		} 
		return result.toString();
	}
	
	//17年新增光路查询
	@Override
	public String addPortsInEqp(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			String staffId = json.getString("staffId");// 用户ID
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机（必填）
			String sn = json.getString("sn");//客户端sn
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpId = json.getString("eqpId");// 设备ID	
			String area_id = json.getString("areaId");//地市id
			String currPage = json.getString("currPage");// 当前页
			String pageSize = json.getString("pageSize");// 每页显示
			/**
			 * 查询参数
			 */
			Map param = new HashMap();
			param.put("staffId", staffId);
			param.put("terminalType", terminalType);
			param.put("sn", sn);
			param.put("eqpNo", eqpNo);
			param.put("eqpId", eqpId);
			param.put("areaId", area_id);
			
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			param.put("jndi", jndi);
			//从OSS系统查实时列表
			List<Map> lightList  = null;
			try{
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			lightList = checkPortDao.getGlList(param);
			SwitchDataSourceUtil.clearDataSource();
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			
			JSONArray jsArr = new JSONArray();
			JSONObject portObject = new JSONObject();
			JSONObject glObject = new JSONObject();
			JSONArray glPath = new JSONArray();
			//从动态端子表查17年新增光路端子信息
			List<Map> portList = checkPortDao.getAddPortList(param);
			for (Map light : lightList) {
				String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
				if(!"".equals(glbh)&&(glbh.startsWith("E17")||glbh.startsWith("F17"))){
					portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
					portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
					portObject.put("eqpName", String.valueOf(light.get("SBMC")).trim());
					portObject.put("eqp_type_id", String.valueOf(light.get("SBLX")).trim());//设备类型ID
					portObject.put("eqp_type", String.valueOf(light.get("NAME")).trim());//设备类型名称
					portObject.put("portId", String.valueOf(light.get("DZID")).trim());
					portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
					portObject.put("glbh", glbh);
					portObject.put("glmc", String.valueOf(light.get("GLMC")).trim());
					portObject.put("isH", "");
					portObject.put("dtsj_id", "");
					portObject.put("address", String.valueOf(light.get("ADDRESS")).trim());	
					portObject.put("ossglbh", glbh);
					portObject.put("ossglmc", String.valueOf(light.get("GLMC")).trim());
					portObject.put("is_free_status", "");
					portObject.put("staffNo", "");
					portObject.put("staffName", "");
					portObject.put("dealTime", "");
					glObject.put("glmc", "");
					glObject.put("gdbh", "");
					glObject.put("sggh", "");
					glObject.put("pzgh", "");
					glObject.put("gxgh", "");
					glObject.put("glxz", "");
					glObject.put("bdsj", "");
					glPath.add(glObject);
					portObject.put("taskId", "");
					portObject.put("rwmxId", "");
					portObject.put("end_time", "");
					portObject.put("portInfos", glPath);
					jsArr.add(portObject);
				}
				/*String portString = null==light.get("DZID")?"":light.get("DZID").toString();				
				for (Map port : portList) {
					String portString1 = null==port.get("DZID")?"":port.get("DZID").toString();
					if (portString.equals(portString1)) {
						portObject.put("eqpId", String.valueOf(port.get("SBID")).trim());
						portObject.put("eqpNo", String.valueOf(port.get("SBBM")).trim());
						portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
						portObject.put("eqp_type_id", String.valueOf(port.get("SBLX")).trim());
						portObject.put("eqp_type", String.valueOf(port.get("RES_TYPE")).trim());
						portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
						portObject.put("portId", String.valueOf(port.get("DZID")).trim());
						portObject.put("portNo", String.valueOf(port.get("DZBM")).trim());
						portObject.put("glbh", String.valueOf(port.get("GLBH")).trim());
						portObject.put("isH", String.valueOf(port.get("H")).trim());
						portObject.put("dtsj_id", String.valueOf(port.get("ID")).trim());
						portObject.put("address", String.valueOf(port.get("ADDRESS")).trim());	
						
						if((String.valueOf(light.get("GLBH"))!=null)&&(!" ".equals(String.valueOf(light.get("GLBH"))))){
							String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
							portObject.put("ossglbh", glbh);
							portObject.put("is_free_status", "");

						} else {
							portObject.put("ossglbh", "");
							portObject.put("is_free_status", "");
							portObject.put("staffNo", "");
							portObject.put("staffName", "");
							portObject.put("dealTime", "");
						}
						
						if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
							portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
						} else {
							portObject.put("ossglmc", "");
						}
						glPath.clear();
						glObject.put("glmc", String.valueOf(port.get("GLMC")).trim());
						glObject.put("gdbh", String.valueOf(port.get("GDBH")).trim());
						glObject.put("sggh", String.valueOf(port.get("SGGH")).trim());
						glObject.put("pzgh", String.valueOf(port.get("PZGH")).trim());
						glObject.put("gxgh", String.valueOf(port.get("GQGH")).trim());
						glObject.put("glxz", String.valueOf(port.get("XZ")).trim());
						glObject.put("bdsj", String.valueOf(port.get("BDSJ")).trim());
						glPath.add(glObject);
						portObject.put("taskId", "");
						portObject.put("rwmxId", "");
						portObject.put("end_time", "");
						portObject.put("portInfos", glPath);
						jsArr.add(portObject);
						break;
					}		
				}*/
			}
			result.put("totalPage", "20");
	        result.put("totalSize", "200");
			result.put("currPage", currPage);
			result.put("pageSize", pageSize);
			result.put("ports", jsArr);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("error", e.toString());
			result.put("desc", "查询端子失败，请联系管理员。");
			logger.info(e.toString());
		}
		return result.toString();
	}

	@Override
	public String checkAgain(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {			
			JSONObject json = JSONObject.fromObject(jsonStr);		
			String eqpID = json.getString("eqpID");
			String eqpNo = json.getString("eqpNo");
			String eqpType="";
			String area_id = json.getString("areaId");//地市id
			if(json.containsKey("eqpType")){
				 eqpType = json.getString("eqpType");//设备类型id
			}
									
			Map param = new HashMap();
			param.put("eqpId", eqpID);
			param.put("eqpNo", eqpNo);
			param.put("area_id", area_id);
						
			//现场复查显示记录页面改造,每次切换分光器调用一次接口，同时显示OSS光路编码
			Map eqpParam=new HashMap();
			eqpParam.put("eqp_obd_Id", eqpID);
			eqpParam.put("eqp_obd_No", eqpNo);
			//
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			param.put("jndi", jndi);
			//从OSS系统查实时列表
			List<Map> lightList  = null;
			try{
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				lightList = checkPortDao.getGlList(param);
				SwitchDataSourceUtil.clearDataSource();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				SwitchDataSourceUtil.clearDataSource();
			}
			
			String checkedPortNum=checkPortDao.getCheckedPortNum(eqpParam);//端子检查个数
			JSONArray eqpArray = new JSONArray();
			if(checkedPortNum != null && !"".equals(checkedPortNum) && !" ".equals(checkedPortNum) && !"0".equals(checkedPortNum)){
				List<Map<String,Object>> eqpRecords=checkPortDao.getRecordByEqpId(eqpParam);
				for(Map eqp:eqpRecords){
					JSONObject eqpObject = new JSONObject();
					String port_id= eqp.get("PORT_ID").toString();
					//通过端子ID（port_id）获取oss中的实时光路编码
					String ossGlbm=this.getOSSGlbm(port_id, lightList);
					String port_no= eqp.get("PORT_NO").toString();
					String glbm= eqp.get("GLBM").toString();
					String ischeckok= eqp.get("ISCHECKOK").toString();
					String check_ischeckok= eqp.get("CHECK_ISCHECKOK").toString();
					String descript= eqp.get("DESCRIPT").toString();
					String staff_name= eqp.get("STAFF_NAME").toString();
					String checkstaff= eqp.get("CHECKSTAFF").toString();
					String create_time= eqp.get("CREATE_TIME").toString();
					eqpObject.put("port_id", port_id);
					eqpObject.put("port_no", port_no);
					eqpObject.put("glbm", glbm);
					eqpObject.put("ossGlbm", ossGlbm);
					eqpObject.put("ischeckok", ischeckok);
					eqpObject.put("descript", descript);
					eqpObject.put("staff_name", staff_name);
					eqpObject.put("create_time", create_time);
					eqpObject.put("checkstaff", checkstaff);
					eqpObject.put("check_ischeckok", check_ischeckok);
					eqpArray.add(eqpObject);
				}
				result.put("eqpRecords", eqpArray);//设备检查端子记录
				result.put("eqpPortNum", checkedPortNum);//设备检查端子个数
			}else{
				result.put("eqpRecords", "[]");
				result.put("eqpPortNum", "0");
			}
			//如果设备类型为分光器
			if(!"2530".equals(eqpType)){							
				//查询分光器以及分光器端子检查个数
				List<Map> obdsList = checkPortDao.getObdsByEqpId(param);//查询分光器个数
				JSONArray obdArray = new JSONArray();
				if(obdsList != null && obdsList.size()>0){
					for(Map obd:obdsList){
						JSONObject obdObject = new JSONObject();
						String obdId = obd.get("SBID").toString();
						String obdNo = obd.get("SBBM").toString();
						String obdName = obd.get("SBMC").toString();
						String obdTypeName = obd.get("SBLX").toString();
						Map obdParam = new HashMap();
						obdParam.put("eqp_obd_Id", obdId);
						obdParam.put("eqp_obd_No", obdNo);
						String checkedPortNum_obd=checkPortDao.getCheckedPortNum(obdParam);//获取分光器端子检查记录个数
						obdObject.put("obdId", obdId);
						obdObject.put("obdNo", obdNo);
						obdObject.put("obdName", obdName);
						obdObject.put("obdTypeName", obdTypeName);
						obdObject.put("checkedPortNum", checkedPortNum_obd);
						obdArray.add(obdObject);
					}
					result.put("obdRecords", obdArray);//分光器记录
				}else{
					result.put("obdRecords", "[]");
				}	
			}else{
				result.put("obdRecords", "[]");
			}
		}catch(Exception e){
			result.put("result", "001");
			result.put("desc", "复查-查询端子记录失败！");
			e.printStackTrace();
		}
		return result.toString();
	}
	
	//传入端子ID和OSS系统中的信息，通过端子id进行匹配，查询找出端子ID上的的OSS光路编码
	public static String getOSSGlbm(String portId,List<Map> list){
		String ossGlbm="";
		for(Map map:list){
			String ossDzid=map.get("DZID").toString();
			if(portId.equals(ossDzid)){
				ossGlbm=map.get("GLBH").toString();
				break;
			}		
		}
		return ossGlbm;
	}
	
	
	
	public String checkAgainTest(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {			
			JSONObject json = JSONObject.fromObject(jsonStr);
			String eqpID = json.getString("eqpID");
			String eqpNo = json.getString("eqpNo");
			String area_id = json.getString("areaId");//地市id
			Map param = new HashMap();
			param.put("eqpId", eqpID);
			param.put("eqpNo", eqpNo);
			param.put("area_id", area_id);
			//查询分光器信息，如果有，再查询分光器中的端子检查记录个数和检查记录
			List<Map> obdsList = checkPortDao.getObdsByEqpId(param);
			JSONArray obdArray = new JSONArray();
			if(obdsList != null && obdsList.size()>0){
				for(Map obd:obdsList){
					JSONObject obdObject = new JSONObject();
					String obdId = obd.get("SBID").toString();
					String obdNo = obd.get("SBBM").toString();
					String obdName = obd.get("SBMC").toString();
					String obdTypeName = obd.get("SBLX").toString();
					Map obdParam = new HashMap();
					obdParam.put("eqp_obd_Id", obdId);
					obdParam.put("eqp_obd_No", obdNo);
					String checkedPortNum=checkPortDao.getCheckedPortNum(obdParam);//获取分光器端子检查记录个数
					/*obdObject.put("obdId", obdId);
					obdObject.put("obdNo", obdNo);
					obdObject.put("obdName", obdName);
					obdObject.put("obdTypeName", "分光器");
					obdObject.put("checkedPortNum", checkedPortNum);
					obdArray.add(obdObject);	*/	
					if(checkedPortNum != null && !"".equals(checkedPortNum) && !" ".equals(checkedPortNum) && !"0".equals(checkedPortNum)){
						List<Map<String,Object>> obdRecords=checkPortDao.getRecordByEqpId(obdParam);
						for(Map obdRe:obdRecords){
							JSONObject obdReObject = new JSONObject();
							String port_id= obdRe.get("PORT_ID").toString();
							String port_no= obdRe.get("PORT_NO").toString();
							String glbm= obdRe.get("GLBM").toString();
							String ischeckok= obdRe.get("ISCHECKOK").toString();
							String check_ischeckok= obdRe.get("CHECK_ISCHECKOK").toString();// 0 合格  1 不合格
							String descript= obdRe.get("DESCRIPT").toString();
							String staff_name= obdRe.get("STAFF_NAME").toString();
							String checkstaff= obdRe.get("CHECKSTAFF").toString();//检查人员id
							String create_time= obdRe.get("CREATE_TIME").toString();
							obdReObject.put("port_id", port_id);
							obdReObject.put("port_no", port_no);
							obdReObject.put("glbm", glbm);
							obdReObject.put("ischeckok", ischeckok);
							obdReObject.put("descript", descript);
							obdReObject.put("staff_name", staff_name);
							obdReObject.put("create_time", create_time);
							obdReObject.put("checkstaff", checkstaff);
							obdReObject.put("check_ischeckok", check_ischeckok);
							obdReObject.put("obdId", obdId);
							obdReObject.put("obdNo", obdNo);
							obdReObject.put("obdName", obdName);
							obdReObject.put("checkedPortNum", checkedPortNum);
							obdArray.add(obdReObject);
						}
						result.put("obdRecords", obdArray);
					}else{
						JSONObject obdReObject = new JSONObject();
						obdReObject.put("port_id", "");
						obdReObject.put("port_no", "");
						obdReObject.put("glbm", "");
						obdReObject.put("ischeckok", "");
						obdReObject.put("descript", "");
						obdReObject.put("staff_name", "");
						obdReObject.put("create_time", "");
						obdReObject.put("checkstaff", "");
						obdReObject.put("check_ischeckok", "");
						obdReObject.put("obdId", obdId);
						obdReObject.put("obdNo", obdNo);
						obdReObject.put("obdName", obdName);
						obdReObject.put("checkedPortNum", "0");
						obdArray.add(obdReObject);
						result.put("obdRecords", obdArray);
					
					}
				}
			}else{
				result.put("obdRecords", "[]");
			}
			//查询箱子端子检查记录
			Map eqpParam=new HashMap();
			eqpParam.put("eqp_obd_Id", eqpID);
			eqpParam.put("eqp_obd_No", eqpNo);
			String checkedPortNum=checkPortDao.getCheckedPortNum(eqpParam);
			JSONArray eqpArray = new JSONArray();
			if(checkedPortNum != null && !"".equals(checkedPortNum) && !" ".equals(checkedPortNum) && !"0".equals(checkedPortNum)){
				List<Map<String,Object>> eqpRecords=checkPortDao.getRecordByEqpId(eqpParam);
				for(Map eqp:eqpRecords){
					JSONObject eqpObject = new JSONObject();
					String port_id= eqp.get("PORT_ID").toString();
					String port_no= eqp.get("PORT_NO").toString();
					String glbm= eqp.get("GLBM").toString();
					String ischeckok= eqp.get("ISCHECKOK").toString();
					String check_ischeckok= eqp.get("CHECK_ISCHECKOK").toString();
					String descript= eqp.get("DESCRIPT").toString();
					String staff_name= eqp.get("STAFF_NAME").toString();
					String checkstaff= eqp.get("CHECKSTAFF").toString();
					String create_time= eqp.get("CREATE_TIME").toString();
					eqpObject.put("port_id", port_id);
					eqpObject.put("port_no", port_no);
					eqpObject.put("glbm", glbm);
					eqpObject.put("ischeckok", ischeckok);
					eqpObject.put("descript", descript);
					eqpObject.put("staff_name", staff_name);
					eqpObject.put("create_time", create_time);
					eqpObject.put("checkstaff", checkstaff);
					eqpObject.put("check_ischeckok", check_ischeckok);
					eqpArray.add(eqpObject);
				}
				result.put("eqpRecords", eqpArray);//设备检查端子记录
				result.put("eqpPortNum", checkedPortNum);//设备检查端子个数
			}else{
				result.put("eqpRecords", "[]");
				result.put("eqpPortNum", "0");
			}						
		}catch(Exception e){
			result.put("result", "001");
			result.put("desc", "复查查询端子记录失败！");
			e.printStackTrace();
		}
		return result.toString();
	}

	@Override
	public String changePort(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String area_id = json.getString("area_id");
			String glbm = json.getString("glbm");
			String staffId = json.getString("staffId");
			
			String sbid = json.getString("sbid");
			String sbbm = json.getString("sbbm");
			String eqpSpecId = "";//设备规格id
			String dzid = json.getString("dzid");
			String dzbm = json.getString("dzbm");			
			
			String sbid_new=json.getString("sbid_new");
			String sbbm_new=json.getString("sbbm_new");
			String eqpSpecId_new="";
			String dzid_new = json.getString("dzid_new");
			String dzbm_new = json.getString("dzbm_new");//ossPortNo	端子编码形式  阿拉伯数字
			String localPortNo = json.getString("localPortNo");//localPortNo	端子编码形式  m/n 用来留作记录
			String portSpec_new = "";//端口规格
			String portSpec = "";//端口规格（新旧端口规格要一致）
			
			//task_id 用来记录一键改流程
			String task_id = json.getString("taskId");//任务ID
						
			Map map=new HashMap();
			map.put("sbid", sbid_new);
			if(!"空闲".equals(dzid_new)){
				map.put("dzid", dzid_new);
			}
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			map.put("sbid_old", sbid);//通过旧的设备ID和端子ID去查询设备类型和端子规格
			map.put("dzid_old", dzid);
			map.put("jndi", jndi);
		
			SwitchDataSourceUtil.setCurrentDataSource(jndi);			
			Map map2=checkPortDao.getEqpPortType(map);
			Map map3=checkPortDao.getEqpPortType2(map);
			SwitchDataSourceUtil.clearDataSource();
			eqpSpecId_new=map2.get("EQPSPECID").toString();//新的设备规格id
			eqpSpecId=map3.get("EQPSPECID").toString();//旧的设备规格ID
			portSpec=map3.get("NAME").toString();//旧的端子规格ID
			portSpec_new=portSpec;
			
			Map portRecordMap=new HashMap();
			portRecordMap.put("task_id", task_id);
			portRecordMap.put("area_id", area_id);
			portRecordMap.put("glbm", glbm);
			portRecordMap.put("sbid", sbid);
			portRecordMap.put("sbbm", sbbm);
			portRecordMap.put("eqpSpecId", eqpSpecId);
			portRecordMap.put("dzid", dzid);
			portRecordMap.put("dzbm", dzbm);
			portRecordMap.put("portSpec", portSpec);
			portRecordMap.put("dzid_new", dzid_new);
			portRecordMap.put("dzbm_new", dzbm_new);
			portRecordMap.put("portSpec_new", portSpec_new);
			portRecordMap.put("staffId", staffId);
			
			portRecordMap.put("sbid_new", sbid_new);
			portRecordMap.put("sbbm_new", sbbm_new);
			portRecordMap.put("localPortNo", localPortNo);
			portRecordMap.put("eqpSpecId_new", eqpSpecId_new);
			
			/*String area_id = "3";// 地市id
			String glbm="F1611180746";
			//旧的端子信息
			String sbid="25800010353301";
			String sbbm="PT-POS-38405722";
			String eqpSpecId="2530";
			String dzid="25800082768478";
			String dzbm="OUT006";
			String portSpec="光缆端子";
			//新的端子信息
			String sbid_new="25800010353301";
			String sbbm_new="PT-POS-38405722";
			String eqpSpecId_new="2530";
			String dzid_new="25800082768478";
			String dzbm_new="OUT007";//
			String portSpec_new="光缆端子";*/
						
			String areaCode=checkPortDao.getAreaCodeByAreaId(area_id);
			String ossAreaCode=areaCode.toLowerCase().concat(".js.cn");
			
			StringBuffer pStr=new StringBuffer();
			pStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");	
			pStr.append("<eqpInfo>");
			pStr.append("<areaCode>").append(ossAreaCode).append("</areaCode>");
			pStr.append("<busiNo>").append(glbm).append("</busiNo>");
			pStr.append("<busiType>").append("光路").append("</busiType>");
			pStr.append("<ports>");
			pStr.append("<port>");
			pStr.append("<oldPort>");
			pStr.append("<eqpId>").append(sbid).append("</eqpId>");
			pStr.append("<eqpNo>").append(sbbm).append("</eqpNo>");
			pStr.append("<eqpSpecId>").append(eqpSpecId).append("</eqpSpecId>");
			pStr.append("<portId>").append(dzid).append("</portId>");
			pStr.append("<portNo>").append(dzbm).append("</portNo>");
			pStr.append("<portSpec>").append(portSpec).append("</portSpec>");
			pStr.append("</oldPort>");
			pStr.append("<newPort>");
			pStr.append("<eqpId>").append(sbid_new).append("</eqpId>");
			pStr.append("<eqpNo>").append(sbbm_new).append("</eqpNo>");
			pStr.append("<eqpSpecId>").append(eqpSpecId_new).append("</eqpSpecId>");
			pStr.append("<portId>").append(dzid_new).append("</portId>");
			pStr.append("<portNo>").append(dzbm_new).append("</portNo>");
			pStr.append("<portSpec>").append(portSpec_new).append("</portSpec>");
			pStr.append("</newPort>");	
			pStr.append("</port>");	
			pStr.append("</ports>");
			pStr.append("</eqpInfo>");
			System.out.println(pStr);
			Map<String,String> portInfo=this.changePortInfo(pStr.toString());
			String returnCode  =portInfo.get("returnCode");//OSS光路查询结果码
			String codeDesc    =portInfo.get("codeDesc");//描述信息	
			if("10010".equals(returnCode)){
				result.put("result", "001");
				result.put("desc", codeDesc);
			}else{
				result.put("desc", "修改端子成功");
				//端子修改成功后，插入修改端子记录表（tb_base_changeport_record）
				checkPortDao.insertChangePortRecords(portRecordMap);
				//将修改动作及信息内容插入流程表
				//插入流程环节
				/*Map processMap = new HashMap();
				processMap.put("task_id", task_id);
				processMap.put("oper_staff", staffId);
				processMap.put("status", 66);//状态为66表示一键改
				if(sbid_new.equals(sbid)){
					processMap.put("content",glbm+"从"+sbbm+"的"+dzbm+"端口成功改至"+localPortNo+"端口");
				}else{
					processMap.put("content",glbm+"从"+sbbm+"的"+dzbm+"端口成功改至"+sbbm_new+"的"+localPortNo+"端口");
				}
				//如果task_id不为空，插入流程表和记录表
				if(!"".equals(task_id)){
					processMap.put("receiver", "");
					processMap.put("remark", "一键改");
					checkProcessDao.addProcessNew(processMap);
				}*/
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "修改端子失败");
			result.put("error", e.toString());
			logger.info(e.toString());		
		} 
		return result.toString();
		
	}
	
	public Map<String, String> changePortInfo(String pStr){		
		String resutlStr=null;
		try {		
			OSSInterfaceForEDBProjServiceLocator ossLocator = null;
			ossLocator = new OSSInterfaceForEDBProjServiceLocator();
			OSSInterfaceForEDBProjServiceSoapBindingStub stub=(OSSInterfaceForEDBProjServiceSoapBindingStub)ossLocator.getOSSInterfaceForEDBProjPort();
			resutlStr=stub.changePort(pStr);
			System.out.println(resutlStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		/**解析获得XML结果**/
		Document doc=null;
		try {
			doc=DocumentHelper.parseText(resutlStr);
		} catch (DocumentException e) {
			return null;
		}
		Element returnInfoE= doc.getRootElement();
		Element resultInfoE=returnInfoE.element("resultInfo");
		String returnCode  =resultInfoE.elementText("returnCode");//OSS光路查询结果码
		String codeDesc    =resultInfoE.elementText("codeDesc");//描述信息	
		Map<String,String> map =new HashMap<String,String>();
		map.put("returnCode", returnCode);
		map.put("codeDesc", codeDesc);
		return map;
		
	}
	
	@Override
	public String queryFreePort(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String area_id = json.getString("area_id");
			String sbid = json.getString("sbid");
			String sbbm = json.getString("sbbm");
			String dzid = json.getString("dzid");
			String dzbm = json.getString("dzbm");
			String glbm = json.getString("glbm");
			String sbid_new = json.getString("sbid_new");
			String sbbm_new = json.getString("sbbm_new");
			//String currentPage = json.getString("currentPage");
			//String pageSize = json.getString("pageSize");
									
			// 接收参数
			/*String area_id = "3";// 地市id
			String glbm="F1611180746";					
			String sbid="25320000000936";
			String sbbm="250JN.KXY00/GJ010";			
			String dzid="25800058791911";
			String dzbm="35/4";
			*/
			Map map=new HashMap();
			map.put("sbid_new", sbid_new);//新的设备ID
			map.put("sbid", sbid);//旧的设备ID
			if(!"空闲".equals(dzid)){
				map.put("dzid", dzid);
			}
			int currentPage=1;
			int pageSize=10000;
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			map.put("jndi", jndi);
			//通过新的设备ID去查询设备规格ID  老的设备ID和端子ID去查询端子规格ID  还未开始做
			SwitchDataSourceUtil.setCurrentDataSource(jndi);			
			//Map map2=checkPortDao.getEqpPortType(map);//通过旧的设备ID和端子ID查询端子规格ID
			Map map3=checkPortDao.getEqpPortTypeByEqpId(map);//通过新的设备ID查询设备规格ID
			SwitchDataSourceUtil.clearDataSource();
			String eqpSpecId="";
			String portSpec="";
//			if(map2 != null){
//				 portSpec=map2.get("PORTSPEC").toString();
//			}
			if(map3 != null){
				 eqpSpecId=map3.get("EQPSPECID").toString();
			}
			/**
			 * 接收的参数
			 */						
			String areaCode=checkPortDao.getAreaCodeByAreaId(area_id);
			String ossAreaCode=areaCode.toLowerCase().concat(".js.cn");
			
			StringBuffer pStr=new StringBuffer();
			pStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");	
			pStr.append("<reqInfo>");
			pStr.append("<eqpNo>").append(sbbm_new).append("</eqpNo>");
			pStr.append("<eqpTypeId>").append(eqpSpecId).append("</eqpTypeId>");
			pStr.append("<eqpId>").append(sbid_new).append("</eqpId>");
			pStr.append("<areaCode>").append(ossAreaCode).append("</areaCode>");
			pStr.append("</reqInfo>");	
//			pStr.append("<eqpInfo>");
//			pStr.append("<eqpId>").append(sbid_new).append("</eqpId>");
//			pStr.append("<areaCode>").append(ossAreaCode).append("</areaCode>");
//			pStr.append("<eqpNo>").append(sbbm_new).append("</eqpNo>");
//			pStr.append("<eqpSpecId>").append(eqpSpecId).append("</eqpSpecId>");
//			pStr.append("<portSpecId >").append(portSpec).append("</portSpecId >");
//			pStr.append("<currentPage>").append(currentPage).append("</currentPage>");
//			pStr.append("<pageSize>").append(pageSize).append("</pageSize>");
//			pStr.append("</eqpInfo>");	
			
			JSONObject eqp_port=new JSONObject();
			eqp_port.put("sbid", sbid);
			eqp_port.put("sbbm", sbbm);
			eqp_port.put("dzid", dzid);
			eqp_port.put("dzbm", dzbm);
			eqp_port.put("glbm", glbm);
			eqp_port.put("portSpec", portSpec);
			eqp_port.put("eqpSpecId", eqpSpecId);
			result.put("eqp_port", eqp_port);
			
			JSONObject portsArray=this.queryFreePortInfo(pStr.toString());	
			if(portsArray.size()>0){					
				String returnCode=portsArray.getString("returnCode");// 000为成功，10010为失败
				String codeDesc=portsArray.getString("codeDesc");
				JSONArray portArray=new JSONArray();
				if("000".equals(returnCode)){
					portArray=portsArray.getJSONArray("portsArray");
					Map paraMap=new HashMap();
					paraMap.put("jndi",jndi);
					paraMap.put("eqpId",sbid_new);
					interChange(paraMap, portArray);
					result.put("portArray", portArray);
				}else{
					result.put("result", "001");
					result.put("desc", codeDesc);
				}
			}else{
				result.put("result", "001");
				result.put("desc", "查询空闲端子失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "查询空闲端子失败");
			logger.info(e.toString());		
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}
	
	public JSONObject queryFreePortInfo(String pStr){		
		String resutlStr=null;
		JSONObject json=new JSONObject();
		JSONArray portsArray = new JSONArray();
		try {		
//			OSSInterfaceForEDBProjServiceLocator ossLocator = null;
//			ossLocator = new OSSInterfaceForEDBProjServiceLocator();
//			OSSInterfaceForEDBProjServiceSoapBindingStub stub=(OSSInterfaceForEDBProjServiceSoapBindingStub)ossLocator.getOSSInterfaceForEDBProjPort();		
//			resutlStr=stub.qryPortListForOptRoad(pStr);		
		
			/**解析获得XML结果**/
//			Document doc=null;
//			doc=DocumentHelper.parseText(resutlStr);
//			Element returnInfo= doc.getRootElement();
//			Element resultInfo=returnInfo.element("resultInfo");
//			String returnCode  =resultInfo.elementText("returnCode");//OSS光路查询结果码
//			String codeDesc    =resultInfo.elementText("codeDesc");//描述信息		
//			String allSize    =resultInfo.elementText("allSize");//总记录数	
//			String currentPage    =resultInfo.elementText("currentPage");//查询当前页		
//			String allPage    =resultInfo.elementText("allPage");//总页数	
//			json.put("returnCode", returnCode);// 000为成功，10010为失败
//			json.put("codeDesc", codeDesc);// 000为成功，10010为失败
			
//			if("000".equals(returnCode)){
//				json.put("allSize", allSize);
//				json.put("currentPage", currentPage);
//				json.put("allPage", allPage);
//				JSONObject port = new JSONObject();			
//				Element portLists=returnInfo.element("portLists");
//				List<Element>list=portLists.elements();
//				String portId="";
//				String portNo="";
//				String portSpec="";
//				for(Element element:list){
//					portId=element.elementText("portId");
//					portNo=element.elementText("portNo");
//					portSpec=element.elementText("portSpec");
//					port.put("portId", portId);
//					port.put("portNo", portNo);
//					port.put("portSpec", portSpec);
//					portsArray.add(port);
//				}
//				json.put("portsArray", portsArray);
//			}else{
//				json.put("portsArray", "[]");
//			}
			
			OSSInterfaceForPHONEServiceLocator ossLocator = null;
			ossLocator = new OSSInterfaceForPHONEServiceLocator();
			OSSInterfaceForPHONEServiceSoapBindingStub stub=(OSSInterfaceForPHONEServiceSoapBindingStub)ossLocator.getOSSInterfaceForPHONEPort();
			resutlStr=stub.deviceFreePort(pStr);	
			/**解析获得XML结果**/
			Document doc=null;
			doc=DocumentHelper.parseText(resutlStr);
			Element returnInfo= doc.getRootElement();
			Element resultInfo=returnInfo.element("resultInfo");
			String returnCode  =resultInfo.elementText("returnCode");//OSS光路查询结果码
			String codeDesc    =resultInfo.elementText("codeDesc");//描述信息		
			json.put("returnCode", returnCode);// 000为成功，10010为失败
			json.put("codeDesc", codeDesc);// 000为成功，10010为失败
			
			if("000".equals(returnCode)){
				JSONObject port = new JSONObject();			
				Element resInfo = returnInfo.element("resInfo");
				List<Element> listElement=resInfo.elements();
				List<Attribute> listAttr=null;
				String value="";
				String portId ="";
				String portNo ="";
				for(Element ele:listElement){
					listAttr=ele.attributes();//当前节点的所有属性的list  
				    /*for(Attribute attr:listAttr){//遍历当前节点的所有属性  			        
				    	value=attr.getValue();//属性的值
				    	portsArray.add(port);
				    } */
					for(int i=1;i<listAttr.size();i++){
						Attribute attr=listAttr.get(i);
						if(i==1){
							portId=attr.getValue();//端子ID
							port.put("portId", portId);
						}
						if(i==2){
							portNo=attr.getValue();//端子编码
							port.put("portNo", portNo);
						}
					}
					portsArray.add(port);
				}
				 json.put("portsArray", portsArray);
			}else{
				json.put("portsArray", "[]");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		return json;		
	}
	//通过设备id在本地网中查询出所有的端子id，端子编码，通过与oss中查询出来的端子id和端子编码进行匹配，将OSS中的端子编码展示为光网助手系统一样形式的
	//编码格式           将OSS端子编码与光网助手互换（展示），但是入参为OSS中的端口,将本地网中的端口编码也加入OSS中去
	public void interChange(Map paraMap,JSONArray portsArray){
		JSONObject ossPort =new JSONObject();
		JSONObject localPort =new JSONObject();
		String ossPortId="";
		String localPortId="";
		String localPortNo="";
		JSONArray localPortsArray=getLocalPorts(paraMap);
		if(portsArray!=null&&portsArray.size()>0){
			for(int i=0;i<portsArray.size();i++){//遍历循环OSS接口查询出来的所有空闲端子
				 ossPort =portsArray.getJSONObject(i);
				 ossPortId=ossPort.getString("portId");
				 if(localPortsArray!=null&&localPortsArray.size()>0){
					 for(int j=0;j<localPortsArray.size();j++){
						 localPort=localPortsArray.getJSONObject(j);
						 localPortId=localPort.getString("portId");
						 localPortNo=localPort.getString("portNo");
						 if(ossPortId.equals(localPortId)){
							 ossPort.put("localPortNo", localPortNo);
							 ossPort.put("portSpec", "");
							 break;
						 }else{
							 ossPort.put("localPortNo", "");
							 ossPort.put("portSpec", "");
						 }
					 }
				 }
			}
		}
	}
	
	public JSONArray getLocalPorts(Map param) {
		// TODO Auto-generated method stub
		JSONArray portsArray=new JSONArray();
		JSONObject portObject=new JSONObject();
		String jndi=param.get("jndi").toString();
		
		Map portMap=new HashMap<String, String>();
		List<Map<String,String>> portsList=new ArrayList<Map<String,String>>();
		try {		
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			portsList=checkTaskDao.getPortsByAreaEqu(param);
			SwitchDataSourceUtil.clearDataSource();
			
			if(portsList!=null&&portsList.size()>0){
				for(int i=0;i<portsList.size();i++){
					portMap=portsList.get(i);
					portObject.put("portId", portMap.get("DZID"));
					portObject.put("portNo", portMap.get("DZBM"));
					portsArray.add(portObject);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
		return portsArray;
	}
	
	
	@Override
	public String getPortByOrder(String jsonStr){
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		JSONObject json = null;
		if(org.apache.commons.lang.StringUtils.isNotBlank(jsonStr)){
			json = JSONObject.fromObject(jsonStr);
			String portId = json.getString("portId");
//			String portId = "";
//			portId = org.apache.commons.lang.StringUtils.isNotBlank(portId)?portId:"27740504";
			if(org.apache.commons.lang.StringUtils.isNotBlank(portId)){
				Map map = new HashMap<String, String>();
				map.put("portId", portId);
				Map map_result = new HashMap<String, String>();
				try {
					map_result = checkPortDao.getPortByOrder(map);
				} catch (Exception e) {
					logger.info(String.format("执行getPortByOrder方法报错，参数是 %s",portId));
					result.put("result", "001");
					result.put("desc", "获取工单下的端子信息失败，请联系管理员。");
				}
				result.put("portInfo", map_result);
			}else{
				logger.info("参数为空");
				result.put("result", "004");
				result.put("desc", "查询的portId为空");
			}
		}else{
			logger.info("参数为空");
			result.put("result", "003");
			result.put("desc", "查询的参数为空");
		}
		return result.toString();
	}

	@Override
	public String checkEqpPorts(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			String staffId = json.getString("staffId");// 用户ID
			String terminalType = json.getString("terminalType");// 0代表pad,1代表手机（必填）
			String sn = json.getString("sn");//客户端sn
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpId = json.getString("eqpId");// 设备ID
			String area_id = json.getString("areaId");//地市id	
			String searchType= json.getString("searchType");//查询类型 0 上月变动端子 ，1 光路占用，2所有，3 17年新增光路

			/*String eqpNo ="250JN.KFQ00/GF078011" ;// 设备编码
			String eqpId ="25800002395381" ;// 设备ID
			String area_id = "3";//地市id			
			String searchType= "0";*/			
			
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			//先通过设备ID和设备编码查询该设备下的所有分光器
			Map obdParam=new HashMap();
			obdParam.put("equId", eqpId);	
			obdParam.put("equCode", eqpNo);
			List<Map<String, String>> obdList=checkTaskDao.getObd(obdParam);
			JSONArray eqpRecords=new JSONArray();//存放设备信息
			JSONObject eqpObject=new JSONObject();
			Map changePortNumMap=new HashMap();
			List<Map> changePortList = null;
			if(obdList!=null&&obdList.size()>0){
				for(Map obd:obdList){
					String equId=obd.get("EQUIPMENT_ID").toString();
					eqpObject.put("obdId", equId);
					eqpObject.put("obdNo", obd.get("EQUIPMENT_CODE").toString());
					eqpObject.put("obdName", obd.get("EQUIPMENT_NAME").toString());
					//eqpObject.put("res_type_id", obd.get("RES_TYPE_ID").toString());
					eqpObject.put("obdTypeName", obd.get("RES_TYPE").toString());
					changePortNumMap.put("obdId", equId);
					changePortList = checkPortDao.getChangePortNum(changePortNumMap);
					eqpObject.put("portChangeNum", changePortList.get(0).get("PORT_CHANGE_NUM").toString());
					eqpRecords.add(eqpObject);
				}
				//按变动端子数排序
				for(int i=0;i<eqpRecords.size()-1;i++){
					for(int j=i+1;j<eqpRecords.size();j++){
						JSONObject obj1 =  (JSONObject)eqpRecords.get(i);
						int portChangeNum1 = Integer.valueOf(obj1.get("portChangeNum").toString());
						JSONObject obj2 =  (JSONObject)eqpRecords.get(j);
						int portChangeNum2 = Integer.valueOf(obj2.get("portChangeNum").toString());
						if(portChangeNum1<portChangeNum2){
							int temp = portChangeNum1;
							portChangeNum1 = portChangeNum2;
							portChangeNum2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							eqpRecords.set(i, obj1);
							eqpRecords.set(j,obj2);
						}
					}
				}
				result.put("obds", eqpRecords);
			}
		
			Map param=new HashMap();
			param.put("eqpId", eqpId);	
			param.put("eqpNo", eqpNo);
			param.put("areaId", area_id);
			param.put("jndi", jndi);
			//从OSS系统查实时列表
			List<Map> lightList  = null;
			try{
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				lightList = checkPortDao.getGlList(param);
				SwitchDataSourceUtil.clearDataSource();
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			JSONArray jsArr = new JSONArray();
			JSONObject portObject=new JSONObject();
			String portId="";
			String lightPortId="";
			String lightPortNo="";
			if("0".equals(searchType)){//上月变动端子				
				//从动态端子表查端子信息
				List<Map> portList = checkPortDao.getPortList(param);		
				for(Map port :portList){
					int flag=0;
					portId= null==port.get("DZID")?"":port.get("DZID").toString();
					for(Map light: lightList){
						flag++;
						lightPortId= null==light.get("DZID")?"":light.get("DZID").toString();
						
						if(portId.equals(lightPortId)){//通过端子id去获取实时光路编码	
							portObject.put("eqpId", String.valueOf(port.get("SBID")).trim());
							portObject.put("eqpNo", String.valueOf(port.get("SBBM")).trim());
							portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
							portObject.put("eqp_type_id", String.valueOf(port.get("SBLX")).trim());
							portObject.put("eqp_type", String.valueOf(port.get("RES_TYPE")).trim());
							portObject.put("portId", String.valueOf(light.get("DZID")).trim());
							portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
							portObject.put("glbh", String.valueOf(port.get("GLBH")).trim());
							portObject.put("isH", String.valueOf(port.get("H")).trim());
							portObject.put("dtsj_id", String.valueOf(port.get("ID")).trim());
							portObject.put("address", String.valueOf(port.get("ADDRESS")).trim());
							portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
							portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
							if((String.valueOf(light.get("GLBH"))!=null)&&(!" ".equals(String.valueOf(light.get("GLBH"))))){
								String glbh = null==light.get("GLBH")?"":light.get("GLBH").toString();
								portObject.put("ossglbh", glbh);
							} else {
								portObject.put("ossglbh", "");
							}
							
							if((String.valueOf(light.get("GLMC"))!=null)&&(!" ".equals(String.valueOf(light.get("GLMC"))))){
								portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());
							} else {
								portObject.put("ossglmc", "");
							}
							jsArr.add(portObject);
							break;
						}else{
							if(flag==lightList.size()){//如果遍历到最后一次，说明还没有遍历上，就将OSS光路编码和光路名称设置为空
								portObject.put("eqpId", String.valueOf(port.get("SBID")).trim());
								portObject.put("eqpNo", String.valueOf(port.get("SBBM")).trim());
								portObject.put("eqpName", String.valueOf(port.get("SBMC")).trim());
								portObject.put("eqp_type_id", String.valueOf(port.get("SBLX")).trim());
								portObject.put("eqp_type", String.valueOf(port.get("RES_TYPE")).trim());
								portObject.put("portId", String.valueOf(light.get("DZID")).trim());
								portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
								portObject.put("glbh", String.valueOf(port.get("GLBH")).trim());
								portObject.put("isH", String.valueOf(port.get("H")).trim());
								portObject.put("dtsj_id", String.valueOf(port.get("ID")).trim());
								portObject.put("address", String.valueOf(port.get("ADDRESS")).trim());
								portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
								portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
								portObject.put("ossglbh", "");
								portObject.put("ossglmc", "");
								jsArr.add(portObject);
							}
						}
					}
				}
			}else if("1".equals(searchType)){//光路占用端子--如果端子在oss中没有对应的OSS光路编码，就任务该端口空闲，否则为占用
				for(Map light:lightList){
					lightPortNo= null==light.get("GLBH")?"":light.get("GLBH").toString();
					if(!"".equals(lightPortNo)){
						portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
						portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
						portObject.put("eqpName", String.valueOf(light.get("SBMC")).trim());
						portObject.put("eqp_type_id", String.valueOf(light.get("SBLX")).trim());
						portObject.put("eqp_type", String.valueOf(light.get("NAME")).trim());
						portObject.put("portId", String.valueOf(light.get("DZID")).trim());
						portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
						portObject.put("glbh", String.valueOf(light.get("GLBH")).trim());
						portObject.put("isH", "");
						portObject.put("dtsj_id", "");
						portObject.put("address", String.valueOf(light.get("ADDRESS")).trim());
						portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
						portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
						portObject.put("ossglbh",lightPortNo);
						portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());					
						jsArr.add(portObject);
					}
				}
			}else if("2".equals(searchType)){//所有端子
				for(Map light:lightList){
					portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
					portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
					portObject.put("eqpName", String.valueOf(light.get("SBMC")).trim());
					portObject.put("eqp_type_id", String.valueOf(light.get("SBLX")).trim());
					portObject.put("eqp_type", String.valueOf(light.get("NAME")).trim());
					portObject.put("portId", String.valueOf(light.get("DZID")).trim());
					portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
					portObject.put("glbh", String.valueOf(light.get("GLBH")).trim());
					portObject.put("isH", "");
					portObject.put("dtsj_id", "");
					portObject.put("address", String.valueOf(light.get("ADDRESS")).trim());
					portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
					portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
					portObject.put("ossglbh", null==light.get("GLBH")?"":light.get("GLBH").toString());
					portObject.put("ossglmc", null==light.get("GLMC")?"":light.get("GLMC").toString());					
					jsArr.add(portObject);
				}
			}
			result.put("ports", jsArr);
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("resule", "001");
			result.put("desc", "查询端子失败,请联系管理员");
		}
		return result.toString();
	}
	
}
