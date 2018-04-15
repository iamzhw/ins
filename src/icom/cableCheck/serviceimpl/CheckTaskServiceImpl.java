package icom.cableCheck.serviceimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.node.BooleanNode;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.util.Log;
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
import sun.util.logging.resources.logging;
import icom.cableCheck.dao.CheckOrderDao;
import icom.cableCheck.dao.CheckPortDao;
import icom.cableCheck.dao.CheckTaskDao;
import icom.cableCheck.model.Dblink;
import icom.cableCheck.model.SensitiveLog;
import icom.cableCheck.service.CheckTaskService;
import icom.system.dao.CableInterfaceDao;
import icom.system.dao.TaskInterfaceDao;
import icom.util.BaseServletTool;
import icom.util.RESUtil;
import icom.util.Result;
import icom.util.SensitiveUtil;
import icom.webservice.client.WfworkitemflowLocator;
import icom.webservice.client.WfworkitemflowSoap11BindingStub;

@Service
@SuppressWarnings("all")
public class CheckTaskServiceImpl implements CheckTaskService {
	Logger logger = Logger.getLogger(CheckTaskServiceImpl.class);
	@Resource
	ElectronArchivesService electronArchivesService;
	@Resource
	private CheckTaskDao checkTaskDao;
	@Resource
	private CheckPortDao checkPortDao;
	@Resource
	private CheckProcessDao checkProcessDao;
	@Resource
	private CableInterfaceDao cableInterfaceDao;
	@Resource
	private CheckOrderDao checkOrderDao;
	
	@Override
	public String getCheckEqp(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			Map param = new HashMap();
			String eqpNo="";
			String eqpName="";
			String glbh="";
			String checkName="";
			String choice="";
			String gridId = "";//网格id
			//判断JSONObject中是否含有某个字段，因为不确定是否传入此字段
			if(json.containsKey("eqpNo")){//判断是否含有eqpNo（设备编码）此字段，有的话直接获取值
				 eqpNo = json.getString("eqpNo");// 设备编码
			 	 param.put("eqpNo", eqpNo);
			}
			if(json.containsKey("eqpName")){//判断是否含有eqpName（设备名称）此字段，有的话直接获取值
				 eqpName = json.getString("eqpName");//设备名称
				 param.put("eqpName", eqpName);
			}
			if(json.containsKey("glbh")){//判断是否含有glbh（光路编号）此字段，有的话直接获取值
				 glbh = json.getString("glbh");// 光路编号
				 param.put("glbh", glbh);
			}
			if(json.containsKey("checkName")){//判断是否含有检查人姓名此字段，有的话直接获取值
				 checkName = json.getString("checkName");// 检查人姓名
				 param.put("checkName", checkName);
			}
			if(json.containsKey("choice")){//判断是否含有choice此字段，有的话直接获取值
				choice = json.getString("choice");// 实占端子数筛选  0表示未进行筛选 1表示筛选  //检查端子数 检查合格率 2
			}
			
			if(json.containsKey("gridId")){//判断是否含有grid_id此字段，有的话直接获取值
				gridId = json.getString("gridId");//网格id
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
			String manageArea = json.getString("manageArea");
			String son_areaId="";
			String isDel =json.getString("isDel");
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
			param.put("manageArea", manageArea);
			param.put("jndi", jndi);
			
			param.put("gridId",gridId);
			
			JSONObject body = new JSONObject();
			JSONArray jsArr = new JSONArray();
            int totalPage = 0;
			/**
			 * 模糊查询
			 */
			if (("0".equals(operType)||"1".equals(operType))&&"0".equals(queryType)) {
				List<Map> eqpList = new ArrayList<Map>() ;
				if("".equals(checkName)){
					if("".equals(glbh)){//此时光路编号为空，利用设备名称或设备编码模糊查询，查询效率高，不涉及动态端子表
						eqpList = checkTaskDao.getEqpByEup(param);
					}else{
						SwitchDataSourceUtil.setCurrentDataSource(jndi);
						eqpList = checkTaskDao.getEqpByLight(param);
						SwitchDataSourceUtil.clearDataSource();
						if(eqpList.size()==0){//首先在OSS实时列表中通过光路查询设备，如果没有就在动态端子表中通过光路查询设备
							eqpList = checkTaskDao.getEqpByDZLight(param);
						}
					}
					
				}else{//检查员姓名不为空，则用检查员姓名来查询
					eqpList = checkTaskDao.getEqpByCheckName(param);
				}
				int totalSize = eqpList.size();
				if((totalSize%Integer.parseInt(pageSize))==0){
					totalPage= totalSize/(Integer.parseInt(pageSize));
				}else 
					 totalPage=totalSize/(Integer.parseInt(pageSize))+1;
				JSONObject eqpJsonObject = new JSONObject();
				for (Map eqp : eqpList) {
					String sbId = eqp.get("SBID").toString();
					
					//筛选实占端子数  获取光路端子数，全部端子数，以及两者之间的比率					
					if("1".equals(choice)){
						List<Map> all_gl_num_list =null;	
						double gl_all_rate=0;
						param.put("sbId", sbId);
						SwitchDataSourceUtil.setCurrentDataSource(jndi);
						all_gl_num_list = checkTaskDao.getRateOfAllGl(param);
						SwitchDataSourceUtil.clearDataSource();
						for(Map map:all_gl_num_list){
							String glNum=map.get("GLBHNUM").toString();//光路占用端子数量
							String allNum=map.get("ALLNUM").toString();//全部端子数量
							double allNums=Double.valueOf(allNum);
							double glNums=Double.valueOf(glNum);						
							if(allNums!=0){						
								gl_all_rate=glNums*100/allNums;
								eqpJsonObject.put("gl_all_rate",String.valueOf(gl_all_rate)+"%");//实占端子数
							}
						}
						if(gl_all_rate/100>=0.5){
							
							eqpJsonObject.put("eqpId", String.valueOf(eqp.get("SBID")));
							eqpJsonObject.put("eqpName", String.valueOf(eqp.get("SBMC")).trim());
							
							String sbbm = String.valueOf(eqp.get("SBBM")).trim();
		//					Map resultMap = this.queryEqpInfo(sbbm);
		//					String isDeal = null==resultMap.get("isDeal")?"":resultMap.get("isDeal").toString();
		//					String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("dealTime").toString();
							eqpJsonObject.put("isDeal", null==eqp.get("ISDEAL")?"":eqp.get("ISDEAL").toString());
							eqpJsonObject.put("dealTime", null==eqp.get("DEALTIME")?"":eqp.get("DEALTIME").toString());
							eqpJsonObject.put("contract_persion_no", null==eqp.get("CONTRACT_PERSION_NO")?"":eqp.get("CONTRACT_PERSION_NO").toString());
							eqpJsonObject.put("WLJB", null==eqp.get("WLJB")?"":eqp.get("WLJB").toString());
							eqpJsonObject.put("contract_persion_name", null==eqp.get("CONTRACT_PERSION_NAME")?"":eqp.get("CONTRACT_PERSION_NAME").toString());
							eqpJsonObject.put("identifyid", null==eqp.get("IDENTIFYID")?"":eqp.get("IDENTIFYID").toString());
							eqpJsonObject.put("eqpNo", sbbm);
							eqpJsonObject.put("eqp_type_id", String.valueOf(eqp.get("SBLX")).trim());
							eqpJsonObject.put("eqp_type", String.valueOf(eqp.get("RES_TYPE")).trim());
							eqpJsonObject.put("address", String.valueOf(eqp.get("ADDRESS")).trim());
							double lant1 = 0;
							double long1 = 0;
							String lon = String.valueOf(eqp.get("LONGITUDE")).trim();
							String lat = String.valueOf(eqp.get("LATITUDE")).trim();
							String lon_inspect=String.valueOf(eqp.get("LONGITUDE_INSPECT")).trim();
							String lat_inspect=String.valueOf(eqp.get("LATITUDE_INSPECT")).trim();
							
							if(lon_inspect!=null &&!"".equals(lon_inspect)){
								long1 = Double.parseDouble(lon_inspect);
								lant1 = Double.parseDouble(lat_inspect);
								eqpJsonObject.put("longitude",lon_inspect);
								eqpJsonObject.put("latitude",lat_inspect);
							}else{
								if(lon!=null &&!"".equals(lon)){
									long1 = Double.parseDouble(lon);
								}
								if(lat!=null &&!"".equals(lat)){
									lant1 = Double.parseDouble(lat);
								}
								eqpJsonObject.put("longitude",lon);
								eqpJsonObject.put("latitude",lat);
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
							
							Map a = new HashMap();
							a.put("sbid", sbId);
							a.put("areaId", areaId);
							List<Map> checkPortList = checkTaskDao.getCheckPort(a);
							for (Map port :checkPortList ) {
								eqpJsonObject.put("checkPort", port.get("NUM").toString());
								eqpJsonObject.put("checkCorrectPort", port.get("NUM2").toString());
								eqpJsonObject.put("checkrate", port.get("CHECKRATE").toString());
							}
							
												
							//int allPort = checkTaskDao.getAllPort(a);
							int allPort = 12;//此字段无效--新需求
							int changePort ;
							if("0".equals(isDel)){
								changePort =checkTaskDao.getDelPort(a);
							}else{
							  changePort = checkTaskDao.getChangePort(a);
							}
							double percentage = (double) changePort / allPort;
							String CHECK_COMPLETE_TIME =  null==eqp.get("CHECK_COMPLETE_TIME")?"":eqp.get("CHECK_COMPLETE_TIME").toString();
							eqpJsonObject.put("change_port_num", String.valueOf(changePort));//变动端子数量
							eqpJsonObject.put("rate", String.valueOf(percentage));				
							eqpJsonObject.put("last_check_time",CHECK_COMPLETE_TIME);					
							eqpJsonObject.put("currPage", param.get("currPage"));
							eqpJsonObject.put("pageSize", param.get("pageSize"));
							eqpJsonObject.put("totalSize", String.valueOf(totalSize));
							eqpJsonObject.put("totalPage", String.valueOf(totalPage));
							eqpJsonObject.put("taskType", "");
							eqpJsonObject.put("taskName", "");
							eqpJsonObject.put("taskId", "");
							eqpJsonObject.put("rwmxId", "");
							eqpJsonObject.put("end_time", "");
							eqpJsonObject.put("taskState", "");
							jsArr.add(eqpJsonObject);
						}
					}else if("2".equals(choice)){//筛选检查端子数>50，检查合格率>95%
						Map a = new HashMap();
						a.put("sbid", sbId);
						a.put("areaId", areaId);
						List<Map> checkPortList = checkTaskDao.getCheckPort(a);
						String checkPort="";//检查端子数
						String checkCorrectPort="";//检查合格端子数
						double checkrate=0;
						for (Map port :checkPortList ) {
							checkPort=port.get("NUM").toString();
							checkCorrectPort=port.get("NUM2").toString();
							eqpJsonObject.put("checkPort", port.get("NUM").toString());
							eqpJsonObject.put("checkCorrectPort", port.get("NUM2").toString());
							eqpJsonObject.put("checkrate", port.get("CHECKRATE").toString());
						}
						double checkPortNum=Double.valueOf(checkPort);
						double checkCorrectPortNum=Double.valueOf(checkCorrectPort);
						if(checkPortNum!=0){						
							checkrate=checkPortNum*100/checkCorrectPortNum;
							//eqpJsonObject.put("gl_all_rate",String.valueOf(checkrate)+"%");//端子检查正确率
						}
						if(checkPortNum>=50&&(checkrate/100>0.95)){
							eqpJsonObject.put("eqpId", String.valueOf(eqp.get("SBID")));
							eqpJsonObject.put("eqpName", String.valueOf(eqp.get("SBMC")).trim());
							
							String sbbm = String.valueOf(eqp.get("SBBM")).trim();
		//					Map resultMap = this.queryEqpInfo(sbbm);
		//					String isDeal = null==resultMap.get("isDeal")?"":resultMap.get("isDeal").toString();
		//					String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("dealTime").toString();
							eqpJsonObject.put("isDeal", null==eqp.get("ISDEAL")?"":eqp.get("ISDEAL").toString());
							eqpJsonObject.put("dealTime", null==eqp.get("DEALTIME")?"":eqp.get("DEALTIME").toString());
							eqpJsonObject.put("contract_persion_no", null==eqp.get("CONTRACT_PERSION_NO")?"":eqp.get("CONTRACT_PERSION_NO").toString());
							eqpJsonObject.put("contract_persion_name", null==eqp.get("CONTRACT_PERSION_NAME")?"":eqp.get("CONTRACT_PERSION_NAME").toString());
							eqpJsonObject.put("identifyid", null==eqp.get("IDENTIFYID")?"":eqp.get("IDENTIFYID").toString());
							eqpJsonObject.put("WLJB", null==eqp.get("WLJB")?"":eqp.get("WLJB").toString());
							eqpJsonObject.put("eqpNo", sbbm);
							eqpJsonObject.put("eqp_type_id", String.valueOf(eqp.get("SBLX")).trim());
							eqpJsonObject.put("eqp_type", String.valueOf(eqp.get("RES_TYPE")).trim());
							eqpJsonObject.put("address", String.valueOf(eqp.get("ADDRESS")).trim());
							double lant1 = 0;
							double long1 = 0;
							String lon = String.valueOf(eqp.get("LONGITUDE")).trim();
							String lat = String.valueOf(eqp.get("LATITUDE")).trim();
							String lon_inspect=String.valueOf(eqp.get("LONGITUDE_INSPECT")).trim();
							String lat_inspect=String.valueOf(eqp.get("LATITUDE_INSPECT")).trim();
							
							if(lon_inspect!=null &&!"".equals(lon_inspect)){
								long1 = Double.parseDouble(lon_inspect);
								lant1 = Double.parseDouble(lat_inspect);
								eqpJsonObject.put("longitude",lon_inspect);
								eqpJsonObject.put("latitude",lat_inspect);
							}else{
								if(lon!=null &&!"".equals(lon)){
									long1 = Double.parseDouble(lon);
								}
								if(lat!=null &&!"".equals(lat)){
									lant1 = Double.parseDouble(lat);
								}
								eqpJsonObject.put("longitude",lon);
								eqpJsonObject.put("latitude",lat);
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
							
							
												
							//int allPort = checkTaskDao.getAllPort(a);
							int allPort = 12;//此字段无效--新需求
							int changePort ;
							if("0".equals(isDel)){
								changePort =checkTaskDao.getDelPort(a);
							}else{
							  changePort = checkTaskDao.getChangePort(a);
							}
							double percentage = (double) changePort / allPort;
							String CHECK_COMPLETE_TIME =  null==eqp.get("CHECK_COMPLETE_TIME")?"":eqp.get("CHECK_COMPLETE_TIME").toString();
							eqpJsonObject.put("change_port_num", String.valueOf(changePort));//变动端子数量
							eqpJsonObject.put("rate", String.valueOf(percentage));				
							eqpJsonObject.put("last_check_time",CHECK_COMPLETE_TIME);					
							eqpJsonObject.put("currPage", param.get("currPage"));
							eqpJsonObject.put("pageSize", param.get("pageSize"));
							eqpJsonObject.put("totalSize", String.valueOf(totalSize));
							eqpJsonObject.put("totalPage", String.valueOf(totalPage));
							eqpJsonObject.put("taskType", "");
							eqpJsonObject.put("taskName", "");
							eqpJsonObject.put("taskId", "");
							eqpJsonObject.put("rwmxId", "");
							eqpJsonObject.put("end_time", "");
							eqpJsonObject.put("taskState", "");
							jsArr.add(eqpJsonObject);							
						}					
					}else{					
						eqpJsonObject.put("eqpId", String.valueOf(eqp.get("SBID")));
						eqpJsonObject.put("eqpName", String.valueOf(eqp.get("SBMC")).trim());
						
						String sbbm = String.valueOf(eqp.get("SBBM")).trim();
	//					Map resultMap = this.queryEqpInfo(sbbm);
	//					String isDeal = null==resultMap.get("isDeal")?"":resultMap.get("isDeal").toString();
	//					String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("dealTime").toString();
						eqpJsonObject.put("isDeal", null==eqp.get("ISDEAL")?"":eqp.get("ISDEAL").toString());
						eqpJsonObject.put("dealTime", null==eqp.get("DEALTIME")?"":eqp.get("DEALTIME").toString());
						eqpJsonObject.put("contract_persion_no", null==eqp.get("CONTRACT_PERSION_NO")?"":eqp.get("CONTRACT_PERSION_NO").toString());
						eqpJsonObject.put("contract_persion_name", null==eqp.get("CONTRACT_PERSION_NAME")?"":eqp.get("CONTRACT_PERSION_NAME").toString());
						eqpJsonObject.put("identifyid", null==eqp.get("IDENTIFYID")?"":eqp.get("IDENTIFYID").toString());
						eqpJsonObject.put("WLJB", null==eqp.get("WLJB")?"":eqp.get("WLJB").toString());
						eqpJsonObject.put("eqpNo", sbbm);
						eqpJsonObject.put("eqp_type_id", String.valueOf(eqp.get("SBLX")).trim());
						eqpJsonObject.put("eqp_type", String.valueOf(eqp.get("RES_TYPE")).trim());
						eqpJsonObject.put("address", String.valueOf(eqp.get("ADDRESS")).trim());
						double lant1 = 0;
						double long1 = 0;
						String lon = String.valueOf(eqp.get("LONGITUDE")).trim();
						String lat = String.valueOf(eqp.get("LATITUDE")).trim();
						String lon_inspect=String.valueOf(eqp.get("LONGITUDE_INSPECT")).trim();
						String lat_inspect=String.valueOf(eqp.get("LATITUDE_INSPECT")).trim();
						
						if(lon_inspect!=null &&!"".equals(lon_inspect)){
							long1 = Double.parseDouble(lon_inspect);
							lant1 = Double.parseDouble(lat_inspect);
							eqpJsonObject.put("longitude",lon_inspect);
							eqpJsonObject.put("latitude",lat_inspect);
						}else{
							if(lon!=null &&!"".equals(lon)){
								long1 = Double.parseDouble(lon);
							}
							if(lat!=null &&!"".equals(lat)){
								lant1 = Double.parseDouble(lat);
							}
							eqpJsonObject.put("longitude",lon);
							eqpJsonObject.put("latitude",lat);
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
						
						Map a = new HashMap();
						a.put("sbid", sbId);
						a.put("areaId", areaId);
						/*List<Map> checkPortList = checkTaskDao.getCheckPort(a);
						for (Map port :checkPortList ) {
							eqpJsonObject.put("checkPort", port.get("NUM").toString());
							eqpJsonObject.put("checkCorrectPort", port.get("NUM2").toString());
							eqpJsonObject.put("checkrate", port.get("CHECKRATE").toString());
						}*/
						eqpJsonObject.put("checkPort","");
						eqpJsonObject.put("checkCorrectPort", "");
						eqpJsonObject.put("checkrate","0.0%");
											
						//int allPort = checkTaskDao.getAllPort(a);
						int allPort = 12;//此字段无效--新需求
						int changePort=0 ;
						/*if("0".equals(isDel)){
							changePort =checkTaskDao.getDelPort(a);
						}else{
						  changePort = checkTaskDao.getChangePort(a);
						}*/
						double percentage = (double) changePort / allPort;
						String CHECK_COMPLETE_TIME =  null==eqp.get("CHECK_COMPLETE_TIME")?"":eqp.get("CHECK_COMPLETE_TIME").toString();
						eqpJsonObject.put("change_port_num", String.valueOf(changePort));//变动端子数量
						eqpJsonObject.put("rate", String.valueOf(percentage));				
						eqpJsonObject.put("last_check_time",CHECK_COMPLETE_TIME);					
						eqpJsonObject.put("currPage", param.get("currPage"));
						eqpJsonObject.put("pageSize", param.get("pageSize"));
						eqpJsonObject.put("totalSize", String.valueOf(totalSize));
						eqpJsonObject.put("totalPage", String.valueOf(totalPage));
						eqpJsonObject.put("taskType", "");
						eqpJsonObject.put("taskName", "");
						eqpJsonObject.put("taskId", "");
						eqpJsonObject.put("rwmxId", "");
						eqpJsonObject.put("end_time", "");
						eqpJsonObject.put("taskState", "");
						jsArr.add(eqpJsonObject);
					}
				}
			}
			/**
			 * operType：0一键反馈，1不可预告抽查，2周期任务
			 * queryType：0,根据设备模糊匹配，1：默认查询1公里以内的所有设备，2:精确匹配oss设备编码
			 */
			if (("0".equals(operType)||"1".equals(operType))&&"2".equals(queryType)) {
				List<Map> eqpList = checkTaskDao.getAccurateEup(param);
				int totalSize = eqpList.size();
				if((totalSize%Integer.parseInt(pageSize))==0){
					totalPage= totalSize/(Integer.parseInt(pageSize));
				}
				else 
					totalPage=totalSize/(Integer.parseInt(pageSize))+1;
				JSONObject eqpJsonObject = new JSONObject();
				for (Map eqp : eqpList) {
					eqpJsonObject.put("eqpId", String.valueOf(eqp.get("SBID")));
					eqpJsonObject.put("eqpName", String.valueOf(eqp.get("SBMC")).trim());
					
					String sbbm = String.valueOf(eqp.get("SBBM")).trim();
					/*Map resultMap = this.queryEqpInfo(eqpNo);*/
//					Map resultMap = this.queryEqpInfo(sbbm);
//					String isDeal = null==resultMap.get("isDeal")?"":resultMap.get("isDeal").toString();
					/*String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("isDeal").toString();*/
//					String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("dealTime").toString();
					eqpJsonObject.put("isDeal", null==eqp.get("ISDEAL")?"":eqp.get("ISDEAL").toString());
					eqpJsonObject.put("dealTime", null==eqp.get("DEALTIME")?"":eqp.get("DEALTIME").toString());
					eqpJsonObject.put("contract_persion_no", null==eqp.get("CONTRACT_PERSION_NO")?"":eqp.get("CONTRACT_PERSION_NO").toString());
					eqpJsonObject.put("contract_persion_name", null==eqp.get("CONTRACT_PERSION_NAME")?"":eqp.get("CONTRACT_PERSION_NAME").toString());
					eqpJsonObject.put("identifyid", null==eqp.get("IDENTIFYID")?"":eqp.get("IDENTIFYID").toString());
					eqpJsonObject.put("WLJB", null==eqp.get("WLJB")?"":eqp.get("WLJB").toString());
					eqpJsonObject.put("eqpNo", sbbm);
					eqpJsonObject.put("eqp_type_id", String.valueOf(eqp.get("SBLX")).trim());
					eqpJsonObject.put("eqp_type", String.valueOf(eqp.get("RES_TYPE")).trim());
					eqpJsonObject.put("address", String.valueOf(eqp.get("ADDRESS")).trim());
					
					double lant1 = 0;
					double long1 = 0;
					String lon = String.valueOf(eqp.get("LONGITUDE")).trim();
					String lat = String.valueOf(eqp.get("LATITUDE")).trim();
					String lon_inspect=String.valueOf(eqp.get("LONGITUDE_INSPECT")).trim();
					String lat_inspect=String.valueOf(eqp.get("LATITUDE_INSPECT")).trim();
					
					if(lon_inspect!=null &&!"".equals(lon_inspect)){
						long1 = Double.parseDouble(lon_inspect);
						lant1 = Double.parseDouble(lat_inspect);
						eqpJsonObject.put("longitude",lon_inspect);
						eqpJsonObject.put("latitude",lat_inspect);
					}else{
						if(lon!=null &&!"".equals(lon)){
							long1 = Double.parseDouble(lon);
						}
						if(lat!=null &&!"".equals(lat)){
							lant1 = Double.parseDouble(lat);
						}
						eqpJsonObject.put("longitude",lon);
						eqpJsonObject.put("latitude",lat);
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
					String sbId = eqp.get("SBID").toString();
					Map a = new HashMap();
					a.put("sbid", sbId);
					a.put("areaId", areaId);
										
					List<Map> checkPortList = checkTaskDao.getCheckPort(a);
					for (Map port :checkPortList ) {
						eqpJsonObject.put("checkPort", port.get("NUM").toString());
						eqpJsonObject.put("checkCorrectPort", port.get("NUM2").toString());
						eqpJsonObject.put("checkrate", port.get("CHECKRATE").toString());
					} 
					
					//int allPort = checkTaskDao.getAllPort(a);
					int allPort = 12;//此字段无效--新需求
					String CHANGEPORTNUM= null== eqp.get("CHANGEPORTNUM")?"0":eqp.get("CHANGEPORTNUM").toString();
					int changePort=Integer.valueOf(CHANGEPORTNUM);
					/*if("0".equals(isDel)){
						changePort =checkTaskDao.getDelPort(a);
					}else{
					  changePort = checkTaskDao.getChangePort(a);
					}*/
					double percentage = (double) changePort / allPort;
					String CHECK_COMPLETE_TIME =  null==eqp.get("CHECK_COMPLETE_TIME")?"":eqp.get("CHECK_COMPLETE_TIME").toString();
					eqpJsonObject.put("change_port_num", String.valueOf(changePort));
					eqpJsonObject.put("rate", String.valueOf(percentage));
					eqpJsonObject.put("last_check_time",CHECK_COMPLETE_TIME);
					eqpJsonObject.put("currPage", param.get("currPage"));
					eqpJsonObject.put("pageSize", param.get("pageSize"));
					eqpJsonObject.put("totalSize", String.valueOf(totalSize));
					eqpJsonObject.put("totalPage", String.valueOf(totalPage));
					eqpJsonObject.put("taskType", "");
					eqpJsonObject.put("taskName", "");
					eqpJsonObject.put("taskId", "");
					eqpJsonObject.put("rwmxId", "");
					eqpJsonObject.put("end_time", "");
					eqpJsonObject.put("taskState", "");
					jsArr.add(eqpJsonObject);
				}
			}

			/**
			 * 查询类型为1(1公里以内设备)
			 */
			if (("0".equals(operType)||"1".equals(operType))&&"1".equals(param.get("queryType"))) {
				result.put("currPage", currPage);
				result.put("pageSize", pageSize);
				result.put("totalPage", "20");
				result.put("totalSize", "200");
				double d = Double.valueOf(distance);
				double degree = d*1000.0 /(2 * Math.PI * 6378137.0) * 360;
				param.put("degree", degree);
				// 查询距离内的设备
				List<Map> distanceList = checkTaskDao.getDistance(param);
				JSONObject distanceJsonObject = new JSONObject();
				int totalSize = distanceList.size();
				if((totalSize%Integer.parseInt(pageSize))==0){
					totalPage= totalSize/(Integer.parseInt(pageSize));
				}else 
					totalPage=totalSize/(Integer.parseInt(pageSize))+1;
				for (Map distance1 :distanceList ) {
					String sbId = distance1.get("SBID").toString();
					
					if("1".equals(choice)){
						List<Map> all_gl_num_list =null;
						double gl_all_rate=0;
						//获取光路端子数，全部端子数，以及两者之间的比率
						param.put("sbId", sbId);
						SwitchDataSourceUtil.setCurrentDataSource(jndi);
						all_gl_num_list = checkTaskDao.getRateOfAllGl(param);
						SwitchDataSourceUtil.clearDataSource();
						for(Map map:all_gl_num_list){
							String glNum=map.get("GLBHNUM").toString();//光路占用端子数量
							String allNum=map.get("ALLNUM").toString();//全部端子数量
							double allNums=Double.valueOf(allNum);
							double glNums=Double.valueOf(glNum);						
							if(allNums!=0){						
								gl_all_rate=glNums*100/allNums;
								distanceJsonObject.put("gl_all_rate",String.valueOf(gl_all_rate)+"%");//实占端子数
							}else{
								distanceJsonObject.put("gl_all_rate", "0.0%");
							}
						}
						if(gl_all_rate/100>=0.5){
							Map a = new HashMap();
							a.put("sbid", sbId);
							a.put("areaId", areaId);
							
							double lng1 = 0;
							double lat1 = 0;
							String lon = String.valueOf(distance1.get("LONGITUDE")).trim();
							String lat = String.valueOf(distance1.get("LATITUDE")).trim();
							String lon_inspect=String.valueOf(distance1.get("LONGITUDE_INSPECT")).trim();
							String lat_inspect=String.valueOf(distance1.get("LATITUDE_INSPECT")).trim();
							
							if(lon_inspect!=null &&!"".equals(lon_inspect)){
								lng1 = Double.parseDouble(lon_inspect);
								lat1 = Double.parseDouble(lat_inspect);
								distanceJsonObject.put("longitude",lon_inspect);
								distanceJsonObject.put("latitude",lat_inspect);
							}else{
								if(lon!=null &&!"".equals(lon)){
									lng1 = Double.parseDouble(lon);
								}
								if(lat!=null &&!"".equals(lat)){
									lat1 = Double.parseDouble(lat);
								}
								distanceJsonObject.put("longitude",lon);
								distanceJsonObject.put("latitude",lat);
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
							int allPort = 12;//此字段无效--新需求
							int changePort ;
							if("0".equals(isDel)){
								changePort =checkTaskDao.getDelPort(a);
							}else{
							  changePort = checkTaskDao.getChangePort(a);
							}
							//List<Map> checkPortList = checkTaskDao.getCheckPort(a);
							//for (Map port :checkPortList ) { 
								distanceJsonObject.put("checkPort", String.valueOf(distance1.get("NUM")));
								distanceJsonObject.put("checkCorrectPort", String.valueOf(distance1.get("NUM2")));
								
							//}
								int checkPort = Integer.parseInt(String.valueOf(distance1.get("NUM")));
								int checkCorrectPort = Integer.parseInt(String.valueOf(distance1.get("NUM2")));
								if(checkPort!=0){
									double checkrate =(double)Math.round(checkCorrectPort*100 / checkPort);
									distanceJsonObject.put("checkrate", String.valueOf(checkrate)+"%");
								}else {
									distanceJsonObject.put("checkrate", "0.0%");
								}
								
							
							distanceJsonObject.put("change_port_num", String.valueOf(changePort));
							distanceJsonObject.put("eqpId", String.valueOf(distance1.get("SBID")));
							
							String sbbm = String.valueOf(distance1.get("SBBM")).trim();
//							Map resultMap = this.queryEqpInfo(sbbm);
//							String isDeal = null==resultMap.get("isDeal")?"":resultMap.get("isDeal").toString();
//							//String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("isDeal").toString();
//							String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("dealTime").toString();
							distanceJsonObject.put("isDeal", null==distance1.get("ISDEAL")?"":distance1.get("ISDEAL").toString());
							distanceJsonObject.put("dealTime", null==distance1.get("DEALTIME")?"":distance1.get("DEALTIME").toString());
							distanceJsonObject.put("contract_persion_no", null==distance1.get("CONTRACT_PERSION_NO")?"":distance1.get("CONTRACT_PERSION_NO").toString());
							distanceJsonObject.put("WLJB", null==distance1.get("WLJB")?"":distance1.get("WLJB").toString());
							distanceJsonObject.put("contract_persion_name", null==distance1.get("CONTRACT_PERSION_NAME")?"":distance1.get("CONTRACT_PERSION_NAME").toString());
							distanceJsonObject.put("identifyid", null==distance1.get("IDENTIFYID")?"":distance1.get("IDENTIFYID").toString());
							distanceJsonObject.put("eqpNo", sbbm);
							distanceJsonObject.put("eqpName", String.valueOf(distance1.get("SBMC")).trim());
							distanceJsonObject.put("eqp_type_id", String.valueOf(distance1.get("SBLX")).trim());
							distanceJsonObject.put("eqp_type", String.valueOf(distance1.get("RES_TYPE")).trim());
							distanceJsonObject.put("address", String.valueOf(distance1.get("ADDRESS")).trim());
//							distanceJsonObject.put("longitude", String.valueOf(distance1.get("LONGITUDE")));
//							distanceJsonObject.put("latitude", String.valueOf(distance1.get("LATITUDE")));
							distanceJsonObject.put("distance", String.valueOf(s));
							if(allPort!=0){
								double percentage =(double) changePort / allPort;
								distanceJsonObject.put("rate", String.valueOf(percentage));
							}else {
								distanceJsonObject.put("rate", "0.0");
							}
							String CHECK_COMPLETE_TIME =  null==distance1.get("CHECK_COMPLETE_TIME")?"":distance1.get("CHECK_COMPLETE_TIME").toString();
							distanceJsonObject.put("last_check_time", CHECK_COMPLETE_TIME);				
							distanceJsonObject.put("taskType", "1");
							distanceJsonObject.put("taskName", "");
							distanceJsonObject.put("taskId", "");
							distanceJsonObject.put("rwmxId", "");
							distanceJsonObject.put("end_time", "");
							distanceJsonObject.put("taskState", "");
							jsArr.add(distanceJsonObject);
						}						
					}else{					
						Map a = new HashMap();
						a.put("sbid", sbId);
						a.put("areaId", areaId);
						
						double lng1 = 0;
						double lat1 = 0;
						String lon = String.valueOf(distance1.get("LONGITUDE")).trim();
						String lat = String.valueOf(distance1.get("LATITUDE")).trim();
						String lon_inspect=String.valueOf(distance1.get("LONGITUDE_INSPECT")).trim();
						String lat_inspect=String.valueOf(distance1.get("LATITUDE_INSPECT")).trim();
						
						if(lon_inspect!=null &&!"".equals(lon_inspect)){
							lng1 = Double.parseDouble(lon_inspect);
							lat1 = Double.parseDouble(lat_inspect);
							distanceJsonObject.put("longitude",lon_inspect);
							distanceJsonObject.put("latitude",lat_inspect);
						}else{
							if(lon!=null &&!"".equals(lon)){
								lng1 = Double.parseDouble(lon);
							}
							if(lat!=null &&!"".equals(lat)){
								lat1 = Double.parseDouble(lat);
							}
							distanceJsonObject.put("longitude",lon);
							distanceJsonObject.put("latitude",lat);
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
						int allPort = 12;//此字段无效--新需求
						int changePort ;
						if("0".equals(isDel)){
							changePort =checkTaskDao.getDelPort(a);
						}else{
						  changePort = checkTaskDao.getChangePort(a);
						}
						//List<Map> checkPortList = checkTaskDao.getCheckPort(a);
						//for (Map port :checkPortList ) { 
							distanceJsonObject.put("checkPort", String.valueOf(distance1.get("NUM")));
							distanceJsonObject.put("checkCorrectPort", String.valueOf(distance1.get("NUM2")));
							
						//}
							int checkPort = Integer.parseInt(String.valueOf(distance1.get("NUM")));
							int checkCorrectPort = Integer.parseInt(String.valueOf(distance1.get("NUM2")));
							if(checkPort!=0){
								double checkrate =(double)Math.round(checkCorrectPort*100 / checkPort);
								distanceJsonObject.put("checkrate", String.valueOf(checkrate)+"%");
							}else {
								distanceJsonObject.put("checkrate", "0.0%");
							}
							
						
						distanceJsonObject.put("change_port_num", String.valueOf(changePort));
						distanceJsonObject.put("eqpId", String.valueOf(distance1.get("SBID")));
						
						String sbbm = String.valueOf(distance1.get("SBBM")).trim();
	//					Map resultMap = this.queryEqpInfo(sbbm);
	//					String isDeal = null==resultMap.get("isDeal")?"":resultMap.get("isDeal").toString();
	//					//String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("isDeal").toString();
	//					String dealTime = null==resultMap.get("dealTime")?"":resultMap.get("dealTime").toString();
						distanceJsonObject.put("isDeal", null==distance1.get("ISDEAL")?"":distance1.get("ISDEAL").toString());
						distanceJsonObject.put("dealTime", null==distance1.get("DEALTIME")?"":distance1.get("DEALTIME").toString());
						distanceJsonObject.put("contract_persion_no", null==distance1.get("CONTRACT_PERSION_NO")?"":distance1.get("CONTRACT_PERSION_NO").toString());
						distanceJsonObject.put("contract_persion_name", null==distance1.get("CONTRACT_PERSION_NAME")?"":distance1.get("CONTRACT_PERSION_NAME").toString());
						distanceJsonObject.put("identifyid", null==distance1.get("IDENTIFYID")?"":distance1.get("IDENTIFYID").toString());
						distanceJsonObject.put("WLJB", null==distance1.get("WLJB")?"":distance1.get("WLJB").toString());
						distanceJsonObject.put("eqpNo", sbbm);
						distanceJsonObject.put("eqpName", String.valueOf(distance1.get("SBMC")).trim());
						distanceJsonObject.put("eqp_type_id", String.valueOf(distance1.get("SBLX")).trim());
						distanceJsonObject.put("eqp_type", String.valueOf(distance1.get("RES_TYPE")).trim());
						distanceJsonObject.put("address", String.valueOf(distance1.get("ADDRESS")).trim());
	//					distanceJsonObject.put("longitude", String.valueOf(distance1.get("LONGITUDE")));
	//					distanceJsonObject.put("latitude", String.valueOf(distance1.get("LATITUDE")));
						distanceJsonObject.put("distance", String.valueOf(s));
						if(allPort!=0){
							double percentage =(double) changePort / allPort;
							distanceJsonObject.put("rate", String.valueOf(percentage));
						}else {
							distanceJsonObject.put("rate", "0.0");
						}
						String CHECK_COMPLETE_TIME =  null==distance1.get("CHECK_COMPLETE_TIME")?"":distance1.get("CHECK_COMPLETE_TIME").toString();
						distanceJsonObject.put("last_check_time", CHECK_COMPLETE_TIME);				
						distanceJsonObject.put("taskType", "1");
						distanceJsonObject.put("taskName", "");
						distanceJsonObject.put("taskId", "");
						distanceJsonObject.put("rwmxId", "");
						distanceJsonObject.put("end_time", "");
						distanceJsonObject.put("taskState", "");
						jsArr.add(distanceJsonObject);
					}
				}
			}
			/**
			 * 查询类型为2(任务查设备)staffid=6623
			 */
			if ("2".equals(queryType)&&"2".equals(operType)) {
				result.put("currPage", currPage);
				result.put("pageSize", pageSize);
				result.put("totalPage", "20");
				result.put("totalSize", "200");
				List<Map> taskList = checkTaskDao.getTaskList(param);
				JSONObject taskJsonObject = new JSONObject();
				
				/*for (int i = 0; i < taskList.size(); i++) {
					// 查询任务列表
					JSONObject taskJsonObject = new JSONObject();
					String rwid = String.valueOf(taskList.get(i).get("TASK_ID"));
					String rwmxid = String.valueOf(taskList.get(i).get("DETAIL_ID"));
//					String jclx = (String) taskList.get(i).get("JCLX");
					HashMap hs = new HashMap();
					hs.put("RWID", rwid);
					hs.put("RWMXID", rwmxid);
					List<Map> dtsjList = checkTaskDao.getDtsjList(hs);*/
					for (Map dtsj : taskList) {
						taskJsonObject.put("eqpId", dtsj.get("EQPID")==null?"":dtsj.get("EQPID").toString());
						son_areaId = dtsj.get("SON_AREAID")==null?"":dtsj.get("SON_AREAID").toString();
						String sbbm = dtsj.get("EQPNO")==null?"":dtsj.get("EQPNO").toString();
						taskJsonObject.put("isDeal", "");
						taskJsonObject.put("dealTime", "");
						taskJsonObject.put("contract_persion_no", "");
						taskJsonObject.put("contract_persion_name", "");
						taskJsonObject.put("identifyid", "");
						taskJsonObject.put("WLJB", null==dtsj.get("WLJB")?"":dtsj.get("WLJB").toString());
						taskJsonObject.put("eqpNo", sbbm);
						taskJsonObject.put("eqp_type_id", dtsj.get("EQP_TYPE_ID")==null?"":dtsj.get("EQP_TYPE_ID").toString());
						taskJsonObject.put("eqpName", dtsj.get("EQPNAME")==null?"":dtsj.get("EQPNAME").toString());
						taskJsonObject.put("eqp_type", dtsj.get("EQP_TYPE")==null?"":dtsj.get("EQP_TYPE").toString());
						taskJsonObject.put("address", dtsj.get("ADDRESS")==null?"":dtsj.get("ADDRESS").toString());
						//OSS中的坐标用来手机端地图显示
						String lon = "";
						String lat = "";
						lon = dtsj.get("LONGITUDE")==null?"":dtsj.get("LONGITUDE").toString();
						lat = dtsj.get("LATITUDE")==null?"":dtsj.get("LATITUDE").toString();
						//检查人员采集的坐标用来手机端地图显示
						String lon_inspect="";
						String lat_inspect="";
						lon_inspect = dtsj.get("LONGITUDE_INSPECT")==null?"":dtsj.get("LONGITUDE_INSPECT").toString();
						lat_inspect = dtsj.get("LATITUDE_INSPECT")==null?"":dtsj.get("LATITUDE_INSPECT").toString();
						//我的检查任务地图显示,如果未采集，直接与以前一样，如果采集了，将检查人员采集的坐标作为地图显示
						//获取距离
						double lant1 = 0;
						double long1 = 0;
						if(lon_inspect!=null && !"".equals(lon_inspect)){
							long1 = Double.parseDouble(lon_inspect);
							lant1 = Double.parseDouble(lat_inspect);
							taskJsonObject.put("longitude", lon_inspect);
							taskJsonObject.put("latitude", lat_inspect);
						}else{
							if(lon!=null &&!"".equals(lon)){
								long1 = Double.parseDouble(lon);
							}
							if(lat!=null &&!"".equals(lat)){
								lant1 = Double.parseDouble(lat);
							}
							taskJsonObject.put("longitude", lon);
							taskJsonObject.put("latitude", lat);
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
						taskJsonObject.put("distance", String.valueOf(s1));
						//获取端子占比
						String sbId = dtsj.get("EQPID").toString();
						Map a = new HashMap();
						a.put("sbid", sbId);
						a.put("areaId", areaId);
						
						String check_port = dtsj.get("CHECK_PORT_NUM")==null?"":dtsj.get("CHECK_PORT_NUM").toString();//上次检查端子数
						String rigth_port = dtsj.get("RIGTH_PORT_NUM")==null?"":dtsj.get("RIGTH_PORT_NUM").toString();//上次检查端子正确数
						int check_port_num=0;
						int rigth_port_num=0;
						if(!"".equals(check_port)){
							check_port_num=Integer.valueOf(check_port);
						}
						if(!"".equals(rigth_port)){
							rigth_port_num=Integer.valueOf(rigth_port);
						}
						if(check_port_num!=0){
							double percentage =(double) rigth_port_num / check_port_num;
							taskJsonObject.put("checkrate", String.valueOf(percentage));
						}else {
							taskJsonObject.put("checkrate", "0.0%");
						}
						taskJsonObject.put("checkPort", check_port_num);
						taskJsonObject.put("checkCorrectPort", rigth_port_num);
						int allPort = 12;
						int changePort=0 ;
						/*if("0".equals(isDel)){
							changePort =checkTaskDao.getDelPort(a);
						}else{
						  changePort = checkTaskDao.getChangePort(a);
						}*/
						double percentage = (double) changePort / allPort;
						taskJsonObject.put("rate", String.valueOf(percentage));
						taskJsonObject.put("change_port_num", changePort);
						taskJsonObject.put("last_check_time", String.valueOf(dtsj.get("LAST_UPDATE_TIME")));
						taskJsonObject.put("start_time", String.valueOf(dtsj.get("START_TIME")));
						taskJsonObject.put("taskId", String.valueOf(dtsj.get("TASKID")));
						taskJsonObject.put("rwmxId", String.valueOf(dtsj.get("RWMXID")));
						taskJsonObject.put("taskName", String.valueOf(dtsj.get("TASKNAME")).trim());
						taskJsonObject.put("taskState", String.valueOf(dtsj.get("TASKSTATE")).trim());
						if("0".equals(String.valueOf(dtsj.get("TASKTYPE")))|| "4".equals(String.valueOf(dtsj.get("TASKTYPE")))||"5".equals(String.valueOf(dtsj.get("TASKTYPE")))
								){
							taskJsonObject.put("taskType","2");
						}else if("1".equals(String.valueOf(dtsj.get("TASKTYPE"))) || "2".equals(String.valueOf(dtsj.get("TASKTYPE"))) || "3".equals(String.valueOf(dtsj.get("TASKTYPE")))){
							taskJsonObject.put("taskType","3");
						}else{
							taskJsonObject.put("taskType","2");
						}
						if("10".equals(String.valueOf(dtsj.get("TASKTYPE")))||"11".equals(String.valueOf(dtsj.get("TASKTYPE")))||"12".equals(String.valueOf(dtsj.get("TASKTYPE")))||"13".equals(String.valueOf(dtsj.get("TASKTYPE")))){
							taskJsonObject.put("data_source","0");
						}else{
							taskJsonObject.put("data_source",String.valueOf(dtsj.get("DATA_SOURCE")));
						}
						taskJsonObject.put("no_eqpNo_flag",(dtsj.get("NO_EQPNO_FLAG")==null ||"".equals(dtsj.get("NO_EQPNO_FLAG")))?"0":dtsj.get("NO_EQPNO_FLAG").toString());
						taskJsonObject.put("end_time", String.valueOf(dtsj.get("END_TIME")).trim());
						jsArr.add(taskJsonObject);
					}
				//}
					
			}
			
			

			  
			/**
			 * 端子最后变动时间，是否全为H光路
			 */
			int tasknum= jsArr.size();
			if(tasknum<=100){
			for(int i=0;i<jsArr.size();i++){
				JSONObject obj =  (JSONObject)jsArr.get(i);
//				if(obj.get("eqpId") != null && !"".equals(obj.get("eqpId"))){
//					String eqpId = obj.getString("eqpId").toString();
//					//最近变动时间
//					String bdsj = null==checkTaskDao.getBDSJByEqpId(eqpId)?"":checkTaskDao.getBDSJByEqpId(eqpId);
//					obj.put("last_change_time", bdsj);
//					//设备端子是否全为H光路
//					String isHList = checkTaskDao.getIsH(eqpId);
//					if("0".equals(isHList)){
//						obj.put("isH", "1");//是
//					}else{
//						obj.put("isH", "0");//否
//					}
//					
//				}else{
					obj.put("last_change_time", "");
					obj.put("isH", "1");
//				}
				jsArr.set(i, obj);
			}
			}else{
				for(int i=0;i<jsArr.size();i++){
				JSONObject obj =  (JSONObject)jsArr.get(i);
//				if(obj.get("eqpId") != null && !"".equals(obj.get("eqpId"))){
//					String eqpId = obj.getString("eqpId").toString();
//					//最近变动时间
//					String bdsj = null==checkTaskDao.getBDSJByEqpId(eqpId)?"":checkTaskDao.getBDSJByEqpId(eqpId);
//					obj.put("last_change_time", bdsj);
//					//设备端子是否全为H光路
//					String isHList = checkTaskDao.getIsH(eqpId);
//					if("0".equals(isHList)){
//						obj.put("isH", "1");//是
//					}else{
//						obj.put("isH", "0");//否
//					}
//					
//				}else{
					obj.put("last_change_time", "");
					obj.put("isH", "1");
//				}
				jsArr.set(i, obj);
			}
				
			}
			
			/**
			 * 排序
			 */
			if("1".equals(orderByEqp)){//1,按照设备编码排序
				for(int i=0;i<jsArr.size()-1;i++){
					for(int j=i+1;j<jsArr.size();j++){
						JSONObject obj1 =  (JSONObject)jsArr.get(i);
						String eqpNo1 = obj1.get("eqpNo").toString();
						JSONObject obj2 =  (JSONObject)jsArr.get(j);
						String eqpNo2 = obj2.get("eqpNo").toString();
						if(eqpNo1.compareTo(eqpNo2)>0){
							String temp = eqpNo1;
							eqpNo1 = eqpNo2;
							eqpNo2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
					}
				}
			}else if("2".equals(orderByEqp)){//2，按照最近一次变动的端子的变动时间排序降序
				for(int i=0;i<jsArr.size()-1;i++){
					for(int j=i+1;j<jsArr.size();j++){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						JSONObject obj1 =  (JSONObject)jsArr.get(i);
						Date last_change_time1 = null;
						if(!"".equals(obj1.get("last_change_time"))){
							last_change_time1 = sdf.parse(obj1.get("last_change_time").toString());
						}
						JSONObject obj2 =  (JSONObject)jsArr.get(j);
						Date last_change_time2 = null;
						if(!"".equals(obj2.get("last_change_time"))){
							last_change_time2 = sdf.parse(obj2.get("last_change_time").toString());
						}
						if(last_change_time1==null){
							Date temp = last_change_time1;
							last_change_time1 = last_change_time2;
							last_change_time2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
						if(last_change_time2==null){
							continue;
						}
						if(last_change_time1.before(last_change_time2)){
							Date temp = last_change_time1;
							last_change_time1 = last_change_time2;
							last_change_time2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
					}
				}
			}else if("3".equals(orderByEqp)){//3,按照变动端子的数量排序
				for(int i=0;i<jsArr.size()-1;i++){
					for(int j=i+1;j<jsArr.size();j++){
						JSONObject obj1 =  (JSONObject)jsArr.get(i);
						double change_port_num1 = Double.parseDouble(obj1.get("change_port_num").toString());
						JSONObject obj2 =  (JSONObject)jsArr.get(j);
						double change_port_num2 = Double.parseDouble(obj2.get("change_port_num").toString());
						if(change_port_num1<change_port_num2){
							double temp = change_port_num1;
							change_port_num1 = change_port_num2;
							change_port_num2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
					}
				}
			}else if("4".equals(orderByEqp)){//2，按照最近一次检查时间排序降序
				for(int i=0;i<jsArr.size()-1;i++){
					for(int j=i+1;j<jsArr.size();j++){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						JSONObject obj1 =  (JSONObject)jsArr.get(i);
						Date last_check_time1 = null;
						if(!"".equals(obj1.get("last_check_time"))){
							last_check_time1 = sdf.parse(obj1.get("last_check_time").toString());
						}
						JSONObject obj2 =  (JSONObject)jsArr.get(j);
						Date last_check_time2 = null;
						if(!"".equals(obj2.get("last_check_time"))){
							last_check_time2 = sdf.parse(obj2.get("last_check_time").toString());
						}
						if(last_check_time1==null){
							Date temp = last_check_time1;
							last_check_time1 = last_check_time2;
							last_check_time2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
						if(last_check_time2==null){
							continue;
						}
						if(last_check_time1.before(last_check_time2)){
							Date temp = last_check_time1;
							last_check_time1 = last_check_time2;
							last_check_time2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
					}
				}
			}else if("5".equals(orderByEqp)){//5,按照上次检查端子的数量排序
				for(int i=0;i<jsArr.size()-1;i++){
					for(int j=i+1;j<jsArr.size();j++){
						JSONObject obj1 =  (JSONObject)jsArr.get(i);
						double checkPort1 = Double.parseDouble(obj1.get("checkPort").toString());
						JSONObject obj2 =  (JSONObject)jsArr.get(j);
						double checkPort2 = Double.parseDouble(obj2.get("checkPort").toString());
						if(checkPort1<checkPort2){
							double temp = checkPort1;
							checkPort1 = checkPort2;
							checkPort2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
					}
				}
			}else if("6".equals(orderByEqp)){//6,按照上次检查端子正确率排序
				for(int i=0;i<jsArr.size()-1;i++){
					for(int j=i+1;j<jsArr.size();j++){
						JSONObject obj1 =  (JSONObject)jsArr.get(i);
						String checkRate1=obj1.get("checkrate").toString();
						checkRate1.substring(0,checkRate1.length()-1);
						double checkrate1 = Double.parseDouble(checkRate1.substring(0,checkRate1.length()-1));
						JSONObject obj2 =  (JSONObject)jsArr.get(j);
						String checkRate2=obj1.get("checkrate").toString();
						double checkrate2 = Double.parseDouble(checkRate2.substring(0,checkRate2.length()-1));
						//double checkrate2 = Double.parseDouble(obj2.get("checkrate").toString());
						if(checkrate1<checkrate2){
							double temp = checkrate1;
							checkrate1 = checkrate2;
							checkrate2 = temp;
							JSONObject objTemp = obj1;
							obj1 = obj2;
							obj2 = objTemp;
							jsArr.set(i, obj1);
							jsArr.set(j,obj2);
						}
					}
				}
			}else{//0按照distance 升序排列，默认排序
				/*for(int i=0;i<jsArr.size()-1;i++){
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
				}*/
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
	public String selectStaff(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONObject json = JSONObject.fromObject(jsonStr);
		try {
			String staffId = json.getString("staffId");
			String terminalType = json.getString("terminalType");// （0代表pad,1代表手机）,必填
			String sn = json.getString("sn");
			String areaId = json.getString("areaId");
			String sonAreaId = json.getString("sonAreaId");
			String selectType = json.getString("selectType");// 必填，0：表示只查询检查人员，1：表示只查询整改人员		
			// 286 检查员
			// 287 整改人员
			Map params = new HashMap();
			params.put("areaId", areaId);
			params.put("sonAreaId", sonAreaId);
			if ("".equals(selectType)) {
				result.put("result", "001");// selectType为空报错
				return result.toString();
			}
			String type = "0".equals(selectType)  ? "286" : "287";
			params.put("role_id", type);
			/*现在南京市整改人员有好多种 
			 *369:光网助手南京综维维护员
			 *370:光网助手南京装维维护员
			 *387:光网助手南京网维维护员
			 *395:光网助手南京政支维护员
			 *392:光网助手南京无线维护员
			 *393:光网助手南京工程维护员
			 *287:光网助手_维护员
			 */			
			/*if("1".equals(selectType)){
				//通过staffId获取角色
				List<Map> roleList=checkTaskDao.getRoleId(staffId);
				for(Map role:roleList){
					String roleId=role.get("ROLE_ID").toString();
					if("369".equals(roleId)){
						params.put("roleId", roleId);
						break;
					}
					if("370".equals(roleId)){
						params.put("roleId", roleId);
						break;
					}
					if("387".equals(roleId)){
						params.put("roleId", roleId);
						break;
					}
					if("395".equals(roleId)){
						params.put("roleId", roleId);
						break;
					}
					if("392".equals(roleId)){
						params.put("roleId", roleId);
						break;
					}
					
					if("393".equals(roleId)){
						params.put("roleId", roleId);
						break;
					}
				}
			}*/
			
			List<Map> rs = checkTaskDao.getStaffByRoleAndArea(params);
			JSONArray jsonArr = new JSONArray();
			for (Map map : rs) {
				JSONObject staff = new JSONObject();
				staff.put("areaId", map.get("AREAID"));
				staff.put("areaName", map.get("AREANAME"));
				staff.put("sonAreaId", map.get("SONAREAID"));
				staff.put("sonAreaName", map.get("SONAREANAME"));
				staff.put("gridId", map.get("GRIDID"));
				staff.put("gridName", map.get("GRIDNAME"));
				staff.put("staffId", map.get("STAFFID"));
				staff.put("staffNo", map.get("STAFFNO"));
				staff.put("staffName", map.get("STAFFNAME"));
				staff.put("staffType", map.get("STAFFTYPE"));
				jsonArr.add(staff);
			}
			result.put("staffs", jsonArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	/**
	 * 根据设备no查询健康电子档案
	 * @param eqpNo
	 * @return
	 */
	public Map queryEqpInfo(String eqpNo){
		
		JSONObject object = new JSONObject();
		object.put("eqpNos", null==eqpNo?"":eqpNo);
		String result = "";
		try {
			result = electronArchivesService.queryEqpInfo(object.toString());
		} catch (Exception e) {
			System.out.println(StringUtil.getCurrDate()+":electronArchivesService is Exception");
			e.printStackTrace();
		}
		//处理webservice返回结果
		Map resultMap = new HashMap();
		JSONObject json = JSONObject.fromObject(result);
		JSONArray dataObject = json.getJSONArray("data");
		if(dataObject.size()>0 && dataObject != null){
			JSONObject data = (JSONObject)dataObject.get(0);
			String isDeal = null==data.get("isDeal")?"":data.getString("isDeal");//0未整治，1已整治
			String dealTime = null==data.get("dealTime")?"":data.getString("dealTime");//整治时间
			resultMap.put("isDeal", isDeal);
			resultMap.put("dealTime", dealTime);
		}else{
			resultMap.put("isDeal", "");
			resultMap.put("dealTime", "");
		}
		return resultMap;
	}

	@Override
	public String selectGrid(String jsonStr) {
		
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(jsonStr);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalType", jo.getString("terminalType"));
		params.put("sn", jo.getString("sn"));
		params.put("staffId", jo.getString("staffId"));
	//	String staffId="15339";//ning11 的staffId
		
		String longitude = jo.getString("longitude");
		String latitude = jo.getString("latitude");
	//	String distance ="0.1";
		String distance = jo.getString("distance");
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		params.put("distance", distance);
		
		double d = Double.valueOf(distance);
		double degree = d*1000.0 /(2 * Math.PI * 6378137.0) * 360;//0.008983152841195214 3479753538
		params.put("degree", degree);
		
		List<Map> rs = checkTaskDao.getDistanceEmp(params);
		
		List<Map> list = checkTaskDao.selectGrid(params);
		if(list.size()>0){
			result.put("result", "000");
			result.put("result_desc", "查询成功");
			result.put("grid", list);
			result.put("rs", rs);
		}else {
			result.put("result", "001");
			result.put("result_desc", "查询失败");
		}
		return result.toString();
		/*JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject eqpJsonObject = new JSONObject();
		JSONArray jsArr = new JSONArray();
		try {
			String longitude = json.getString("longitude");
			String latitude = json.getString("latitude");
			String distance = json.getString("distance");
			Map params = new HashMap();
			params.put("longitude", longitude);
			params.put("latitude", latitude);
			params.put("distance", distance);
			
			double d = Double.valueOf(distance);
			double degree = d*1000.0 /(2 * Math.PI * 6378137.0) * 360;
			params.put("degree", degree);
			// 查询距离内的设备
				List<Map> rs = checkTaskDao.getDistanceEmp(params);
			
			//			List<Map> rs = checkTaskDao.selectGrid(params);
			for (Map map : rs) {
				
				eqpJsonObject.put("eqpId", String.valueOf(map.get("EQUIPMENT_ID")));
				eqpJsonObject.put("eqpName", String.valueOf(map.get("EQUIPMENT_NAME")));
				
				double lant1 = 0;
				double long1 = 0;
				String lon = String.valueOf(map.get("LONGITUDE")).trim();
				String lat = String.valueOf(map.get("LATITUDE")).trim();
				String lon_inspect=String.valueOf(map.get("LONGITUDE_INSPECT")).trim();
				String lat_inspect=String.valueOf(map.get("LATITUDE_INSPECT")).trim();
				
				if(lon_inspect!=null &&!"".equals(lon_inspect)){
					long1 = Double.parseDouble(lon_inspect);
					lant1 = Double.parseDouble(lat_inspect);
					eqpJsonObject.put("longitude",lon_inspect);
					eqpJsonObject.put("latitude",lat_inspect);
				}else{
					if(lon!=null &&!"".equals(lon)){
						long1 = Double.parseDouble(lon);
					}
					if(lat!=null &&!"".equals(lat)){
						lant1 = Double.parseDouble(lat);
					}
					eqpJsonObject.put("longitude",lon);
					eqpJsonObject.put("latitude",lat);
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
				jsArr.add(eqpJsonObject);
			}
			
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
			
			
			
			result.put("staffs", jsArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();*/
	}
	
	
	@Override
	public String selectGridQum(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject eqpJsonObject = new JSONObject();
		JSONArray jsArr = new JSONArray();
		try {
			String gard_id = json.getString("gard_id");//网格id
			String eqpCode = json.getString("eqpCode");//设备编码
			String eqpAddress = json.getString("eqpAddress");//设备地址
			String eqpType = json.getString("eqpType");//设备类型
			String port_start_time = json.getString("port_start_time");//端子变动开始时间
			String port_end_time = json.getString("port_end_time");//端子变动结束时间
			String contract_person = json.getString("contract_person");//我的承包设备 ，若选中，则查我的承包设备,0表示选中 1未选中
			String staffId = json.getString("staffId");//登录人员账号id
			
			String mark = json.getString("mark");//0 变动（默认）  1 全部
			int page =Integer.valueOf(json.getString("page"));
			int size =Integer.valueOf(json.getString("size"));
			
									
			Map params = new HashMap();
			params.put("gard_id", gard_id);	 												
			params.put("eqpCode", eqpCode);				
			params.put("eqpAddress", eqpAddress);				
			params.put("eqpType", eqpType);	
			params.put("port_start_time", port_start_time);	
			params.put("port_end_time", port_end_time);	
			
			String areaId = json.getString("areaId");
			params.put("areaId", areaId);	
			
			
			if("0".equals(contract_person)){//表示选中了承包设备，加入到条件查询
				params.put("staffId", staffId);	
			}else{
				staffId="";
				params.put("staffId", staffId);//未选中承包条件
			}
			setPage(page, size, params);
			int allOrderNum=0;
			//查询网格下的所有设备
			String allEqpNum= "";
			List<Map> rs=new ArrayList<Map>();
			
			if("2530".equals(eqpType)){
				rs= checkTaskDao.getGridQumByOBD(params);//查询分光器的设备
				allEqpNum= rs.size()+"";
			}else{
				if("0".equals(mark)){
					rs= checkTaskDao.getGridQum(params);//查询除分光器外的设备--变动的
					allEqpNum= rs.size()+"";
				}else{
					rs= checkTaskDao.getGridQumAll(params);//查询除分光器外的设备--全部
					allEqpNum= checkTaskDao.getAllEqpNum(params);
				}
			}
			
			if(rs.size()>0 && rs != null){
				for (Map map : rs) {
					String eqpId=String.valueOf(map.get("EQUIPMENT_ID"));
					if (!"".equals(eqpId) && null!=eqpId && !"null".equals(eqpId)){
						eqpJsonObject.put("eqpId", eqpId);
					}else{
						eqpJsonObject.put("eqpId", "");
					}
					String eqpCode1=String.valueOf(map.get("EQUIPMENT_CODE"));
					if(!"".equals(eqpCode1) && null!=eqpCode1 && !"null".equals(eqpCode1)){
						eqpJsonObject.put("eqpCode", eqpCode1);
					}else{
						eqpJsonObject.put("eqpCode", "");
					}
					
					String eqpName=String.valueOf(map.get("EQUIPMENT_NAME"));
					if(!"".equals(eqpName) && null!=eqpName && !"null".equals(eqpName)){
						eqpJsonObject.put("eqpName",eqpName );
					}else{
						eqpJsonObject.put("eqpName","" );
					}	
					String address=String.valueOf(map.get("ADDRESS"));
					if(!"".equals(address) && null!=address && !"null".equals(address)){
						eqpJsonObject.put("address",address );
					}else{
						eqpJsonObject.put("address","" );
					}	
					String res_type=String.valueOf(map.get("RES_TYPE"));
					if(!"".equals(res_type) && null!=res_type && !"null".equals(res_type)){
						eqpJsonObject.put("res_type",res_type );
					}else{
						eqpJsonObject.put("res_type","" );
					}
					String ordernum=String.valueOf(map.get("ORDERNUM"));//该设备下的工单数量
					if(!"".equals(ordernum) && null!=ordernum && !"null".equals(ordernum)){
						allOrderNum=allOrderNum+Integer.valueOf(ordernum);
						eqpJsonObject.put("ordernum",ordernum );
					}else{
						eqpJsonObject.put("ordernum","" );
					}
					String check_complete_time=String.valueOf(map.get("CHECK_COMPLETE_TIME"));//该设备上次检查时间
					if(!"".equals(check_complete_time) && null !=check_complete_time && !"null".equals(check_complete_time)){
						eqpJsonObject.put("check_complete_time",check_complete_time );
					}else{
						eqpJsonObject.put("check_complete_time","" );
					}
					jsArr.add(eqpJsonObject);
				}			
			}								
			result.put("list", jsArr);
			result.put("allOrderNum", allOrderNum);
			result.put("allEqpNum", allEqpNum);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "网格设备清单查询失败！");
		}
		return result.toString();
	}
	
	
	@Override
	public String saveError(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject eqpJsonObject = new JSONObject();
		JSONArray jsArr = new JSONArray();
		try {
			String equipment_id = json.getString("equipment_id");
			Map params = new HashMap();
			params.put("equipment_id", equipment_id);
			
			checkTaskDao.saveError(params);
			
			result.put("msg", "错误信息记录成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	
	@Override
	public String getEquWorkOrder(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		JSONObject eqpJsonObject = new JSONObject();
		List <Map> LightPathPersonList = null;
		List<Map> LightPathList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			String equCode = json.getString("equCode");// 设备编码		
			String equId = json.getString("equId");// 设备id 
			String areaId = json.getString("areaId");
			String port_start_time = json.getString("port_start_time");//端子变动开始时间
			String port_end_time = json.getString("port_end_time");//端子变动结束时间
			HashMap param = new HashMap();	
			Map obdMap=null;
			
			param.put("equId", equId);
			param.put("equCode", equCode);
			param.put("areaId", areaId);
			param.put("port_start_time", port_start_time);
			param.put("port_end_time", port_end_time);
			//通过设备ID和设备编码查询设备类型
			Map<String, String> eqpTypeMap=checkTaskDao.getEquType(param);
			String eqpType = eqpTypeMap.get("RES_TYPE_ID");
			if("2530".equals(eqpType)){
				//通过分光器查询工单id
				LightPathPersonList=checkTaskDao.getEquWorkOrderByObd(param);
			}else{
				//通过箱子设备查询工单id
				LightPathPersonList=checkTaskDao.getEquWorkOrder(param);
			}
			String jndi=RESUtil.getRes(areaId);
			LightPathPersonList= deleteSameOrderByGlbm(jndi,LightPathPersonList);
			
			if(LightPathPersonList.size()>0 && LightPathPersonList != null){
				for (Map light : LightPathPersonList) {
					String ORDER_NO= String.valueOf(light.get("ORDER_NO")).trim();
					String eqp_no =String.valueOf(light.get("EQUIPMENT_CODE")).trim();
					eqpJsonObject.put("ORDER_NO", ORDER_NO);//订单编号	
					String ACTION_TYPE=String.valueOf(light.get("ACTION_TYPE")).trim();
					if(null!=ACTION_TYPE && !"".equals(ACTION_TYPE) && !"null".equals(ACTION_TYPE) ){
						eqpJsonObject.put("ACTION_TYPE", ACTION_TYPE);//光路调整动作
					}else{
						eqpJsonObject.put("ACTION_TYPE", "");//光路调整动作
					}
					
					/*String OPT_OPER_NAME=String.valueOf(light.get("OPT_OPER_NAME")).trim();
					if(null!=OPT_OPER_NAME && !"".equals(OPT_OPER_NAME) && !"null".equals(OPT_OPER_NAME) ){
						eqpJsonObject.put("OPT_OPER_NAME", OPT_OPER_NAME);//光路调整操作人
					}else{
						eqpJsonObject.put("OPT_OPER_NAME", "");//光路调整操作人
					}*/
					
					/*String OPT_OPER_STATE=String.valueOf(light.get("OPT_OPER_STATE")).trim();
					if(null!=OPT_OPER_STATE && !"".equals(OPT_OPER_STATE) && !"null".equals(OPT_OPER_STATE)){
						eqpJsonObject.put("OPT_OPER_STATE", OPT_OPER_STATE);////操作状态
					}else{
						eqpJsonObject.put("OPT_OPER_STATE", "");////操作状态
					}*/

					/*String IOM_MF_PTY_NM=String.valueOf(light.get("CHANGE_FIBER_OPER")).trim();
					if(null!=IOM_MF_PTY_NM && !"".equals(IOM_MF_PTY_NM) && !"null".equals(IOM_MF_PTY_NM)){
						eqpJsonObject.put("IOM_MF_PTY_NM", IOM_MF_PTY_NM);//光路更纤人
					}else{
						eqpJsonObject.put("IOM_MF_PTY_NM", "");//光路更纤人
					}*/
					String order_check_time=String.valueOf(light.get("ORDER_CHECK_TIME")).trim();
					if(order_check_time !=null && !"".equals(order_check_time)&& !"null".equals(order_check_time)){
						eqpJsonObject.put("order_check_time", order_check_time);//工单上次检查时间
					}else{
						eqpJsonObject.put("order_check_time", "未检查");
					}
					
					String order_check_staff=String.valueOf(light.get("STAFF_NAME")).trim();
					if(order_check_staff !=null && !"".equals(order_check_staff)&& !"null".equals(order_check_staff)){
						eqpJsonObject.put("order_check_staff", order_check_staff);//工单检查人
					}else{
						eqpJsonObject.put("order_check_staff", "");
					}
					
					String ARCHIVE_TIME=String.valueOf(light.get("ARCHIVE_TIME")).trim();
					if(ARCHIVE_TIME !=null && !"".equals(ARCHIVE_TIME)&& !"null".equals(ARCHIVE_TIME)){
						eqpJsonObject.put("ARCHIVE_TIME", ARCHIVE_TIME);//工单竣工时间
					}else{
						eqpJsonObject.put("ARCHIVE_TIME", "");
					}
					String OPT_CODE=String.valueOf(light.get("OPT_CODE")).trim();
					eqpJsonObject.put("OPT_CODE",OPT_CODE );
					eqpJsonObject.put("eqp_no",eqp_no );//设备编jsArr.add(eqpJsonObject);
					combineEqp(jsArr, eqpJsonObject);
				}
			}else{
				result.put("list", "");
			}
			result.put("list", jsArr);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "获取网格设备所属工单失败");
			result.put("msg", "获取网格设备所属工单失败");
			logger.info(e.toString());
		}
		return result.toString();
	}

	//查询出设备下的工单后，可能一条工单对应着不同的设备编码，这条工单可能在箱子上，可能在分光器上，
	//而工单列表页面一条工单只能展示一行，故要把在同一条工单上的设备编码进行合并
	public static void combineEqp(JSONArray jsArr,JSONObject eqpJsonObject){
		//1.从jsArr取出工单
		JSONObject orderObject=new JSONObject();
		String orderNo="";
		String eqpNo="";
		String order_no=eqpJsonObject.getString("ORDER_NO");
		String eqp_no=eqpJsonObject.getString("eqp_no");
		boolean flag=true;
		if(jsArr.size()>0){
			for(int i=0;i<jsArr.size();i++){
				orderObject=jsArr.getJSONObject(i);
				orderNo= orderObject.getString("ORDER_NO");
				eqpNo= orderObject.getString("eqp_no");
				if(order_no.equals(orderNo)){
					if(!eqp_no.equals(eqpNo)){
						eqpNo=eqpNo+","+eqp_no;
						orderObject.put("eqp_no", eqpNo);	
						flag=false;
						break;
					}				
				}
			}
			if(flag){
				jsArr.add(eqpJsonObject);
			}
		}else{
			jsArr.add(eqpJsonObject);
		}
		
	}
	@Override
	public String selectWorkOrderList(String jsonStr){
		JSONObject result = new JSONObject();
		List<Map> TaskList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数 
			 */
			//	String task_no="20170623160975";
			
				String task_no = json.getString("task_no");// 工单号
			
			
			HashMap param = new HashMap();	
			param.put("task_no", task_no);
			
			try {
			TaskList=checkTaskDao.selectWorkOrderList(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
					result.put("ORDER_NO", String.valueOf(light.get("ORDER_NO")).trim());//工单号
				//	result.put("ORDER_TYPE", String.valueOf(light.get("ORDER_TYPE")).trim());//工单类型
					result.put("sn", String.valueOf(light.get("SN")).trim());//sn号
					result.put("glbh", String.valueOf(light.get("OPT_CODE")).trim());//光路编码
					//	String oss_oper_name=light.get("OSS_OPER_NAME")== null ? null : String.valueOf(light.get("OSS_OPER_NAME")).trim();
				
					String reply_pty_nm=String.valueOf(light.get("REPLY_PTY_NM")).trim();
					if(null!=reply_pty_nm && !"".equals(reply_pty_nm) && !"null".equals(reply_pty_nm)){
						result.put("reply_pty_nm",reply_pty_nm);//调整工号
						
					}else{
						result.put("reply_pty_nm", "");//调整工号
					}
					
					String action_type=String.valueOf(light.get("ACTION_TYPE")).trim();
					if(null!=action_type && !"".equals(action_type) && !"null".equals(action_type)){
						result.put("action_type",action_type);//调整动作
						
					}else{
						result.put("action_type","");//调整动作
					}
					
					String change_fiber_oper=String.valueOf(light.get("CHANGE_FIBER_OPER")).trim();
					if(null!=change_fiber_oper && !"".equals(change_fiber_oper) && !"null".equals(change_fiber_oper)){
						result.put("opt_oper_name",change_fiber_oper);//更纤人
					}else{
						result.put("opt_oper_name","自动");//更纤人
					}
					
//					String opt_oper_name=String.valueOf(light.get("OSS_OPER_NAME")).trim();
//					if(null!=opt_oper_name && !"".equals(opt_oper_name) && !"null".equals(opt_oper_name)){
//						if(opt_oper_name.equals("中兴软创")){
//							result.put("opt_oper_name","自动");//光路调整操作人若为中兴软创，则 自动
//						}else{
//							result.put("opt_oper_name",opt_oper_name);//光路调整操作人
//						}
//						
//						
//					}else{
//						result.put("opt_oper_name","");//光路调整操作人
//					}
					
					String opt_oper_state=String.valueOf(light.get("OPT_OPER_STATE")).trim();
					if(null!=opt_oper_state && !"".equals(opt_oper_state) && !"null".equals(opt_oper_state)){
						result.put("opt_oper_state",opt_oper_state);//操作状态
					}else{
						result.put("opt_oper_state","");//操作状态
					}
					
					
					
					String userName=String.valueOf(light.get("USER_NAME")).trim();
					if(null!=userName && !"".equals(userName) && !"null".equals(userName)){
						result.put("userName",SensitiveUtil.hideSensitive(userName, ""));//用户名
						
					}else{
						result.put("userName","");//用户名
					}
					
					String install_addr= String.valueOf(light.get("INSTALL_ADDR")).trim();
					if(null!=install_addr && !"".equals(install_addr) && !"null".equals(install_addr)){
						result.put("install_addr",SensitiveUtil.hideSensitive(install_addr, "address"));//装机地址
					}else{
						result.put("install_addr","");//装机地址
					}
					
					String glly= String.valueOf(light.get("OPT_ROUTE")).trim();
					//【路由段】 分离
					if(null!=glly && !"".equals(glly) && !"null".equals(glly)){
						int index=glly.indexOf("【路由段】");
						glly=glly.substring(index);
						String[] gl_route=glly.split("【路由段】");
						String[] gl_route_new=new String[gl_route.length-1];
						for(int i=1;i<gl_route.length;i++){
							gl_route_new[i-1]="【路由段】"+gl_route[i];
						}
						result.put("glly",gl_route_new);//光路路由
					}else{
						result.put("glly","[]");//光路路由
					}
					
					String glmc=String.valueOf(light.get("OPT_NAME")).trim();
					if(null!=glmc && !"".equals(glmc) && !"null".equals(glmc)){
						result.put("glmc",glmc);//光路名称
					}else{
						result.put("glmc","");//光路名称
					}
					
					try {
						/**
						 * 敏感日志对象
						 */
						SensitiveLog s = SensitiveLog.createSensitiveLog("", "", userName, param.toString(), "select", install_addr,
								json.getString("staff_id")==null?"":json.getString("staff_id").trim(),
								json.getString("sn")==null?"":json.getString("sn").trim());
						SensitiveLog.getList().add(s);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					}
			}
		} catch (Exception e) {
			result.put("desc", "查询工单详细信息失败");
			logger.info(e.toString());
		}
		return result.toString();
		
	}
	
	@Override
	public String selectResourcesCheck(String jsonStr){
		JSONObject result = new JSONObject();
		List<Map> TaskList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数 
			 */
			
			String grid_id = json.getString("grid_id");// 网格ID
			String grid_name = json.getString("grid_name");// 网格名称
			
			HashMap param = new HashMap();	
			param.put("grid_id", grid_id);
			param.put("grid_name", grid_name);
			
			try {
			TaskList=checkTaskDao.selectResourcesCheck(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
					result.put("CITYNAME", String.valueOf(light.get("CITYNAME")).trim());//市/县/区分公司
					result.put("countryname", String.valueOf(light.get("countryname")).trim());//市/县/区分公司
					result.put("GRID_NAME", String.valueOf(light.get("GRID_NAME")).trim());//网格单元
				//	result.put("", String.valueOf(light.get("")).trim());//局站
					result.put("RES_TYPE", String.valueOf(light.get("RES_TYPE")).trim());//设施类型
					result.put("EQUIPMENT_NAME", String.valueOf(light.get("EQUIPMENT_NAME")).trim());//光交设施名称
					result.put("EQUIPMENT_CODE", String.valueOf(light.get("EQUIPMENT_CODE")).trim());//光交设施编码
					result.put("COUNTNUM", String.valueOf(light.get("COUNTNUM")).trim());//光交设施容量
					result.put("USENUM", String.valueOf(light.get("USENUM")).trim());//光交设施占用数
					result.put("FREENUM", String.valueOf(light.get("FREENUM")).trim());//光交设施空闲数
					}
			}
		} catch (Exception e) {
			result.put("desc", "现场资源查询失败");
			logger.info(e.toString());
		}
		return result.toString();
		
	}
	
	
	@Override
	public String getWPWorkOrder(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		List<Map> WorkOrderList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			
			String equCode = json.getString("equCode");// 设备编码			
			String areaId = json.getString("areaId");
			HashMap param = new HashMap();	
			List<Map> eqpList = new ArrayList<Map>() ;
			
			param.put("equCode", equCode);
			
			try {
			String jndi = cableInterfaceDao.getDBblinkName(areaId);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}		
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			eqpList = checkTaskDao.getLightPath(param);
			SwitchDataSourceUtil.clearDataSource();
			for (int i = 0; i < eqpList.size(); i++) {
				WorkOrderList=checkTaskDao.getEquWorkOrder(eqpList.get(i));
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(WorkOrderList.size()>0 && WorkOrderList != null){ 
				for (Map light : WorkOrderList) {
					result.put("ORDER_NO", String.valueOf(light.get("ORDER_NO")).trim());//订单编号	
					result.put("ACTION_TYPE", String.valueOf(light.get("ACTION_TYPE")).trim());//光路调整动作
					String OPT_OPER_NAME=String.valueOf(light.get("OPT_OPER_NAME")).trim();
					if(null!=OPT_OPER_NAME || ""!=OPT_OPER_NAME){
						result.put("OPT_OPER_NAME", OPT_OPER_NAME);//光路调整操作人
					}else{
						result.put("OPT_OPER_NAME", "");//光路调整操作人
					}
					
					String OPT_OPER_STATE=String.valueOf(light.get("OPT_OPER_STATE")).trim();
					if(null!=OPT_OPER_STATE || ""!=OPT_OPER_STATE){
						result.put("OPT_OPER_STATE", OPT_OPER_STATE);////操作状态
					}else{
						result.put("OPT_OPER_STATE", "");////操作状态
					}
				//	result.put("zyzy", String.valueOf(light.get("zyzy")).trim());//占用资源信息
				//	result.put("IOM_MF_PTY_NBR", String.valueOf(light.get("OPT_OPER_NBR")).trim());//光路更纤人工号
					String IOM_MF_PTY_NM=String.valueOf(light.get("CHANGE_FIBER_OPER")).trim();
					if(null!=IOM_MF_PTY_NM || ""!=IOM_MF_PTY_NM){
						result.put("IOM_MF_PTY_NM", IOM_MF_PTY_NM);//光路更纤人
					}else{
						result.put("IOM_MF_PTY_NM", "");//光路更纤人
					}
					
					jsArr.add(result);
					}
				}
		} catch (Exception e) {
			result.put("msg", "工单查询失败");
			logger.info(e.toString());
		}
		return jsArr.toString();
	}
	
	
	@Override
	public String selectWPWorkOrderList(String jsonStr){
		JSONObject result = new JSONObject();
		List<Map> WorkOrderList = null;
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
				WorkOrderList=checkTaskDao.selectWorkOrderList(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(WorkOrderList.size()>0 && WorkOrderList != null){
				for (Map light : WorkOrderList) {
					result.put("ORDER_NO", String.valueOf(light.get("ORDER_NO")).trim());//工单号
					//	result.put("ORDER_TYPE", String.valueOf(light.get("ORDER_TYPE")).trim());//工单类型
						result.put("sn", String.valueOf(light.get("TERM_SN")).trim());//sn号
						result.put("glbh", String.valueOf(light.get("OPT_CODE")).trim());//光路编码
						//	String oss_oper_name=light.get("OSS_OPER_NAME")== null ? null : String.valueOf(light.get("OSS_OPER_NAME")).trim();
					
						String reply_pty_nm=String.valueOf(light.get("REPLY_PTY_NM")).trim();
						if(null!=reply_pty_nm || ""!=reply_pty_nm){
							result.put("reply_pty_nm", reply_pty_nm);//调整工号
						}else{
							result.put("reply_pty_nm","");//调整工号
						}
						
						String action_type=String.valueOf(light.get("ACTION_TYPE")).trim();
						if(null !=action_type || ""!=action_type){
							result.put("action_type",action_type);//调整动作
						}else{
							result.put("action_type","");//调整动作
						}
						
						String opt_oper_name=String.valueOf(light.get("OPT_OPER_NAME")).trim();
						if(null!=opt_oper_name || ""!=opt_oper_name){
							result.put("opt_oper_name",opt_oper_name);//光路调整操作人
						}else{
							result.put("opt_oper_name","");//光路调整操作人
						}
						
						String opt_oper_state=String.valueOf(light.get("OPT_OPER_STATE")).trim();
						if(null!=opt_oper_state || ""!=opt_oper_state){
							result.put("opt_oper_state",opt_oper_state);//操作状态k244114531
						}else{
							result.put("opt_oper_state","");//操作状态
						}
						
						String userName=String.valueOf(light.get("USER_NAME ")).trim();
						if(null!=userName || ""!=userName){
							result.put("userName",userName);//用户名
						}else{
							result.put("userName","");//用户名
						}
						
						String install_addr= String.valueOf(light.get("INSTALL_ADDR")).trim();
						if(null !=install_addr ||""!=install_addr){
							result.put("install_addr",install_addr);//装机地址
						}else{
							result.put("install_addr","");//装机地址
						}
						
						String glly= String.valueOf(light.get("OPT_ROUTE ")).trim();
						if(null !=glly ||""!=glly){
							result.put("glly",glly);//光路路由
						}else{
							result.put("glly","");//光路路由
						}
						
						String glmc=String.valueOf(light.get("OPT_NAME")).trim();
						if(null !=glmc || ""!=glmc){
							result.put("glmc",glmc);//光路名称
						}else{
							result.put("glmc","");//光路名称
						}
					
					}
			}
		} catch (Exception e) {
			result.put("desc", "查询工单详细信息失败");
			logger.info(e.toString());
		}
		return result.toString();
		
	}
	
	/**
	 * 已完成的检查任务，将工作量推送给集约化平台(线路工单触发接口)
	 * @param 
	 * @return
	 */
	public String outSysDispatchTask(String jsonStr){
		
		JSONObject object = new JSONObject();
		JSONObject json = JSONObject.fromObject(jsonStr);
		//object.put("eqpNos", null==eqpNo?"":eqpNo);
		String result = "";
		String xml = null;
		HashMap param = new HashMap();	
		 
		
		// 系统路由IOM系统：IOM 巡检系统：XJXT 光网助手提供：GWZS
		String sysRoute =  json.getString("sysRoute");
		/**
		 * 工单类型
		 * 1：IOM开通类工单
			2：IOM整治类工单
			3：巡检任务触发线路工单
			4：巡检系统触发隐患整治工单
			5：光网助手检查触发线路工单
			6：光网助手检查触发隐患整治工单
			光网助手检查触发线路工单 taskType=5
		 */
		String taskType = json.getString("taskType");
		//光网检查人员
		String gwMan =  json.getString("gwMan");
		//光网助手工单号
		String taskId = json.getString("taskId");
		//光网检查人员账号
		String gwManAccount = json.getString("gwManAccount");
		//检查设备编码
		String equCode = json.getString("equCode");
		//检查设备名称
		String equName =  json.getString("equName");
		//检查端子数量
		String chekPortNum = json.getString("chekPortNum");
		//覆盖地址核查数量
		String addressCheckCnt = json.getString("addressCheckCnt");
		//检查内容描述
		String gwContent =  json.getString("gwContent");
		
		if(null == sysRoute || "" == sysRoute){
			String str="系统路由为空";
			param.put("msg", str);
			return str;
		}else if(null == taskType || "" == taskType){
			String str="工单类型为空";
			param.put("msg", str);
			return str;
		}else if(null == gwMan || "" == gwMan){
			String str="光网检查人员为空";
			param.put("msg", str);
			return str;
		}else if(null == taskId || "" == taskId){
			String str="光网助手工单号为空";
			param.put("msg", str);
			return str;
		}else if(null == equCode || "" == equCode){
			String str="检查设备编码为空";
			param.put("msg", str);
			return str;
		}else if(null == equName || "" == equName){
			String str="检查设备名称为空";
			param.put("msg", str);
			return str;
		}else if(null == chekPortNum || "" == chekPortNum){
			String str="检查端子数量为空";
			param.put("msg", str);
			return str;
		}else if(null == addressCheckCnt || "" == addressCheckCnt){
			String str="覆盖地址检查数量为空";
			param.put("msg", str);
			return str;
		}
		
		xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
		"<IfInfo><sysRoute>"+sysRoute+"</ sysRoute >"+
			"<taskType>"+taskType+"</ taskType >"+
			"<gwMan>"+gwMan+"</ gwMan >"+
			"<taskId>"+taskId+"</ taskId >"+
			"<gwManAccount>"+gwManAccount+"</ gwManAccount >"+
			"<equCode>"+equCode+"</ equCode >"+
			"<equName>"+equName+"</ equName >"+
			"<chekPortNum>"+chekPortNum+"</ chekPortNum >"+
			"<addressCheckCnt>"+addressCheckCnt+"</ addressCheckCnt >"+
			"<gwContent>"+gwContent+"</ gwContent >"+
		"</IfInfo>";
		
//		 xml="<?xml version=\"1.0\" encoding=\"gb2312\"?><IfInfo><sysRoute>GWZS</sysRoute><taskType>5</taskType><gwMan>杨勇</gwMan><taskId>10100</taskId><gwManAccount>yy</gwManAccount><equCode>250GL.HLJL0/GJ005</equCode><equName>上林苑GJ001</equName><chekPortNum>10</chekPortNum><addressCheckCnt>10</addressCheckCnt><gwContent>测试</gwContent></IfInfo>";
		
		
		WfworkitemflowLocator locator =new WfworkitemflowLocator();
		try {
			WfworkitemflowSoap11BindingStub stub=(WfworkitemflowSoap11BindingStub)locator.getWfworkitemflowHttpSoap11Endpoint();
			result=stub.outSysDispatchTask(xml);
		//	System.out.println(result);
			//outSysDispatchTask
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}

	
	/**
	 * 隐患整治工单接口
	 * @param 
	 * @return
	 */
	public String dangerRemediationOder(String jsonStr){
		
		JSONObject object = new JSONObject();
		JSONObject json = JSONObject.fromObject(jsonStr);
		//object.put("eqpNos", null==eqpNo?"":eqpNo);
		String result = "";
		String xml = null;
		HashMap param = new HashMap();	
		 
		
		// 系统路由IOM系统：IOM 巡检系统：XJXT 光网助手提供：GWZS
		String sysRoute =  json.getString("sysRoute");
		/**
		 * 工单类型
		 * 1：IOM开通类工单
			2：IOM整治类工单
			3：巡检任务触发线路工单
			4：巡检系统触发隐患整治工单
			5：光网助手检查触发线路工单
			6：光网助手检查触发隐患整治工单
			光网助手检查触发隐患整治工单taskType=6
		 */
		String taskType = json.getString("taskType");
		//光网检查人员
		String gwMan =  json.getString("gwMan");
		//整治工单号
		String taskId = json.getString("taskId");
		//光网检查人员账号
		String gwManAccount = json.getString("gwManAccount");
		//发现隐患设备编码
		String equCode = json.getString("equCode");
		//发现隐患设备名称
		String equName =  json.getString("equName");
		//设备类型 1：光交接箱 2：ODF 3：光分纤箱
		String equType =  json.getString("equType");
		//错误端子列表
		String errorPortList = json.getString("errorPortList");
		//隐含内容描述
		String gwContent =  json.getString("gwContent");
		//整治对应设备承包人员账号
		String equCbAccount =  json.getString("equCbAccount");
		//光网助手匹配兜底岗相关人员
		String gwPositionPersons =  json.getString("gwPositionPersons");
		
		if(null == sysRoute || "" == sysRoute){
			String str="系统路由为空";
			param.put("msg", str);
			return str;
		}else if(null == taskType || "" == taskType){
			String str="工单类型为空";
			param.put("msg", str);
			return str;
		}else if(null == gwMan || "" == gwMan){
			String str="光网检查人员为空";
			param.put("msg", str);
			return str;
		}else if(null == taskId || "" == taskId){
			String str="整治工单号为空";
			param.put("msg", str);
			return str;
		}else if(null == equCode || "" == equCode){
			String str="隐患设备编码为空";
			param.put("msg", str);
			return str;
		}else if(null == equName || "" == equName){
			String str="隐患设备名称为空";
			param.put("msg", str);
			return str;
		}
		else if(null == equType || "" == equType){
			String str="设备类型为空";
			param.put("msg", str);
			return str;
		}else if(null == errorPortList || "" == errorPortList){
			String str="错误端子列表为空";
			param.put("msg", str);
			return str;
		}
		
		xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
		"<IfInfo><sysRoute>"+sysRoute+"</sysRoute>"+
			"<taskType>"+taskType+"</taskType>"+
			"<gwMan>"+gwMan+"</gwMan>"+
			"<taskId>"+taskId+"</taskId>"+
			"<gwManAccount>"+gwManAccount+"</gwManAccount>"+
			"<equCode>"+equCode+"</equCode>"+
			"<equName>"+equName+"</equName>"+
			"<chekPortNum>"+errorPortList+"</chekPortNum>"+
			"<gwContent>"+gwContent+"</gwContent>"+
		"</IfInfo>";
		
//		 xml="<?xml version=\"1.0\" encoding=\"gb2312\"?><IfInfo><sysRoute>GWZS</sysRoute><taskType>5</taskType><gwMan>杨勇</gwMan><taskId>10100</taskId><gwManAccount>yy</gwManAccount><equCode>250GL.HLJL0/GJ005</equCode><equName>上林苑GJ001</equName><errorPortList>10</errorPortList><gwContent>测试</gwContent></IfInfo>";
		
		
		WfworkitemflowLocator locator =new WfworkitemflowLocator();
		try {
			WfworkitemflowSoap11BindingStub stub=(WfworkitemflowSoap11BindingStub)locator.getWfworkitemflowHttpSoap11Endpoint();
			result=stub.outSysDispatchTask(xml);
		//	System.out.println(result);
			//outSysDispatchTask
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	
	/**
	 * 隐患整治工单审核结果接口
	 * @param 
	 * @return
	 */
	public String outSysAppOperating(String jsonStr){
		
		JSONObject object = new JSONObject();
		JSONObject json = JSONObject.fromObject(jsonStr);
		//object.put("eqpNos", null==eqpNo?"":eqpNo);
		String result = "";
		String xml = null;
		HashMap param = new HashMap();	
		 
		
		// 系统路由IOM系统：IOM 巡检系统：XJXT 光网助手提供：GWZS
		String sysRoute =  json.getString("sysRoute");
		/**
		 * 工单类型
		 * 1：IOM开通类工单
			2：IOM整治类工单
			3：巡检任务触发线路工单
			4：巡检系统触发隐患整治工单
			5：光网助手检查触发线路工单
			6：光网助手检查触发隐患整治工单
			光网助手检查触发隐患整治工单taskType=6
		 */
		
	
		//整治工单号
		String taskId = json.getString("taskId");
	
	
		//操作类型
		String oprType =  json.getString("oprType");
		
		if(null == sysRoute || "" == sysRoute){
			String str="系统路由为空";
			param.put("msg", str);
			return str;
		}else if(null == taskId || "" == taskId){
			String str="整治工单号为空";
			param.put("msg", str);
			return str;
		}
		else if(null == oprType || "" == oprType){
			String str="操作类型为空";
			param.put("msg", str);
			return str;
		}
		
		xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
		"<IfInfo><sysRoute>"+sysRoute+"</sysRoute>"+
			"<task_id>"+taskId+"</task_id>"+
			"<oprType>"+oprType+"</oprType>"+
		"</IfInfo>";
		
//		 xml="<?xml version=\"1.0\" encoding=\"gb2312\"?><IfInfo><sysRoute>GWZS</sysRoute><taskType>5</taskType><gwMan>杨勇</gwMan><taskId>10100</taskId><gwManAccount>yy</gwManAccount><equCode>250GL.HLJL0/GJ005</equCode><equName>上林苑GJ001</equName><errorPortList>10</errorPortList><gwContent>测试</gwContent></IfInfo>";
//		 xml="<?xml version=\"1.0\" encoding=\"gb2312\"?><IfInfo><sysRoute>GWZS</sysRoute><taskId>10100</taskId><oprType>1</oprType></IfInfo>";

		
		WfworkitemflowLocator locator =new WfworkitemflowLocator();
		try {
			WfworkitemflowSoap11BindingStub stub=(WfworkitemflowSoap11BindingStub)locator.getWfworkitemflowHttpSoap11Endpoint();
			result=stub.outSysAppOperating(xml);
		//	System.out.println(result);
			//outSysDispatchTask
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}


	
	@Override
	public String ErrorWorkOrder(String jsonStr){
		JSONObject result = new JSONObject();
		List<Map> TaskList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数 
			 */
			
			String staff_no = json.getString("staff_no");// 检查人ID
			String staff_name = json.getString("staff_name");// 检查人
			
			HashMap param = new HashMap();	
			param.put("staff_no", staff_no);
			param.put("staff_name", staff_name);
			
			try {
			TaskList=checkTaskDao.ErrorWorkOrder(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
				
				}
			}
		} catch (Exception e) {
			result.put("desc", "查询工单详细信息失败");
			logger.info(e.toString());
		}
		return result.toString();
		
	}
	
	
	
	@Override
	public String TaskCheck(String jsonStr){
		JSONObject result = new JSONObject();
		List<Map> TaskList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数 
			 */
			
			String task_no = json.getString("staff_id");// 检查人ID
			String task_nos = json.getString("staff_name");// 检查人
			String eqp_id = json.getString("eqp_id");// 设备ID
			String eqp_name = json.getString("eqp_name");// 设备名称
			String flag = json.getString("flag");// 是否合格
			
			HashMap param = new HashMap();	
			param.put("task_no", task_no);
			
			try {
			TaskList=checkTaskDao.ErrorWorkOrder(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
					/*result.put("workOrder_no", String.valueOf(light.get("WORKORDER_NO")).trim());//工单号
					result.put("workOrder_type", String.valueOf(light.get("WORKORDER_TYPE")).trim());//工单类型
					result.put("sn", String.valueOf(light.get("SN")).trim());//sn号
					result.put("glbh", String.valueOf(light.get("GLBH")).trim());//光路编码
					result.put("configurer", String.valueOf(light.get("CONFIGURER")).trim());//配置人
					result.put("revisionNum", String.valueOf(light.get("REVISIONNUM")).trim());//调整工号
					result.put("constructionHill", String.valueOf(light.get("CONSTRUCTIONHILL")).trim());//施工岗
					result.put("modifyFiberSponsor", String.valueOf(light.get("MODIFYFIBERSPONSOR")).trim());//更纤发起人
					result.put("revisionAction", String.valueOf(light.get("REVISIONACTION")).trim());//调整动作
					result.put("glOperator", String.valueOf(light.get("GLOPERATOR")).trim());//光路调整操作人
					result.put("operateType", String.valueOf(light.get("OPERATETYPE")).trim());//操作状态
					result.put("userName", String.valueOf(light.get("USERNAME")).trim());//用户名
					result.put("installedAddress", String.valueOf(light.get("INSTALLEDADDRESS")).trim());//装机地址
					result.put("glly", String.valueOf(light.get("GLLY")).trim());//光路路由
					result.put("glmc", String.valueOf(light.get("GLMC")).trim());//光路名称
					result.put("completedTime", String.valueOf(light.get("COMPLETEDTIME")).trim());//竣工时间
					result.put("modifyTime", String.valueOf(light.get("MODIFYTIME")).trim());//修改时间
*/					}
			}
		} catch (Exception e) {
			result.put("desc", "查询工单详细信息失败");
			logger.info(e.toString());
		}
		return result.toString();
		
	}
	
	
	@Override
	public String updatecheck(String jsonStr){
		JSONObject result = new JSONObject();
		List<Map> TaskList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数 
			 */
			
			String task_no = json.getString("staff_id");// 检查人ID
			String task_nos = json.getString("staff_name");// 检查人
			String eqp_id = json.getString("eqp_id");// 设备ID
			String eqp_name = json.getString("eqp_name");// 设备名称
			String flag = json.getString("flag");// 是否合格
			
			HashMap param = new HashMap();	
			param.put("task_no", task_no);
			
			try {
			TaskList=checkTaskDao.updatecheck(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
					/*result.put("workOrder_no", String.valueOf(light.get("WORKORDER_NO")).trim());//工单号
					result.put("workOrder_type", String.valueOf(light.get("WORKORDER_TYPE")).trim());//工单类型
					result.put("sn", String.valueOf(light.get("SN")).trim());//sn号
					result.put("glbh", String.valueOf(light.get("GLBH")).trim());//光路编码
					result.put("configurer", String.valueOf(light.get("CONFIGURER")).trim());//配置人
					result.put("revisionNum", String.valueOf(light.get("REVISIONNUM")).trim());//调整工号
					result.put("constructionHill", String.valueOf(light.get("CONSTRUCTIONHILL")).trim());//施工岗
					result.put("modifyFiberSponsor", String.valueOf(light.get("MODIFYFIBERSPONSOR")).trim());//更纤发起人
					result.put("revisionAction", String.valueOf(light.get("REVISIONACTION")).trim());//调整动作
					result.put("glOperator", String.valueOf(light.get("GLOPERATOR")).trim());//光路调整操作人
					result.put("operateType", String.valueOf(light.get("OPERATETYPE")).trim());//操作状态
					result.put("userName", String.valueOf(light.get("USERNAME")).trim());//用户名
					result.put("installedAddress", String.valueOf(light.get("INSTALLEDADDRESS")).trim());//装机地址
					result.put("glly", String.valueOf(light.get("GLLY")).trim());//光路路由
					result.put("glmc", String.valueOf(light.get("GLMC")).trim());//光路名称
					result.put("completedTime", String.valueOf(light.get("COMPLETEDTIME")).trim());//竣工时间
					result.put("modifyTime", String.valueOf(light.get("MODIFYTIME")).trim());//修改时间
*/					}
			}
		} catch (Exception e) {
			result.put("desc", "查询工单详细信息失败");
			logger.info(e.toString());
		}
		return result.toString();
		
	}
	
	
	@Override
	public String modifytraces(String jsonStr){
		JSONObject result = new JSONObject();
		List<Map> TaskList = null;
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数 
			 */
			
			String task_no = json.getString("staff_id");// 工单ID
			String task_nos = json.getString("staff_name");// 工单修改时间
			String eqp_id = json.getString("eqp_id");// 修改人
			String eqp_name = json.getString("eqp_name");// 修改项
			
			HashMap param = new HashMap();	
			param.put("task_no", task_no);
			
			try {
			TaskList=checkTaskDao.updatecheck(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
					/*result.put("workOrder_no", String.valueOf(light.get("WORKORDER_NO")).trim());//工单号
					result.put("workOrder_type", String.valueOf(light.get("WORKORDER_TYPE")).trim());//工单类型
					result.put("sn", String.valueOf(light.get("SN")).trim());//sn号
					result.put("glbh", String.valueOf(light.get("GLBH")).trim());//光路编码
					result.put("configurer", String.valueOf(light.get("CONFIGURER")).trim());//配置人
					result.put("revisionNum", String.valueOf(light.get("REVISIONNUM")).trim());//调整工号
					result.put("constructionHill", String.valueOf(light.get("CONSTRUCTIONHILL")).trim());//施工岗
					result.put("modifyFiberSponsor", String.valueOf(light.get("MODIFYFIBERSPONSOR")).trim());//更纤发起人
					result.put("revisionAction", String.valueOf(light.get("REVISIONACTION")).trim());//调整动作
					result.put("glOperator", String.valueOf(light.get("GLOPERATOR")).trim());//光路调整操作人
					result.put("operateType", String.valueOf(light.get("OPERATETYPE")).trim());//操作状态
					result.put("userName", String.valueOf(light.get("USERNAME")).trim());//用户名
					result.put("installedAddress", String.valueOf(light.get("INSTALLEDADDRESS")).trim());//装机地址
					result.put("glly", String.valueOf(light.get("GLLY")).trim());//光路路由
					result.put("glmc", String.valueOf(light.get("GLMC")).trim());//光路名称
					result.put("completedTime", String.valueOf(light.get("COMPLETEDTIME")).trim());//竣工时间
					result.put("modifyTime", String.valueOf(light.get("MODIFYTIME")).trim());//修改时间
*/					}
			}
		} catch (Exception e) {
			result.put("desc", "修改痕迹信息失败");
			logger.info(e.toString());
		}
		return result.toString();
		
	}
	
	
	/**
	 * 判断账户是几级检查人员
	 * @param staff_id
	 * @return
	 */
	public int staffType(String staff_id){
		int type = 1;//1表示一级检查人员，2表示二级检查人员，3表示三级检查人员
		
		Boolean bool1 = checkTaskDao.isOneStaff(staff_id)>0;
		if(bool1){
			type = 1;
		}else {
			Boolean bool2 = checkTaskDao.isTwoStaff(staff_id)>0;
			if(bool2){
				type = 2;
			}else{
				type = 3;
			}
		}
		return type;
	}
	
	
	@Override
	public String intensificationworkoder(String jsonStr){
		JSONArray arrays = new JSONArray();
		JSONObject res = new JSONObject();
		List<Map> TaskList = null;
		
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			
			String check_task_staff = json.getString("check_task_staff");// 检查人
			
			int staffType = staffType(check_task_staff);
			String orderCode = json.getString("orderCode");//工单编号 
			String orderName = json.getString("orderName");//工单名称 
			
			String latitude = json.getString("la");
			String longitude = json.getString("lo");
			if(StringUtils.isBlank(longitude) || StringUtils.isBlank(latitude)){
				latitude = "0.0";
				longitude = "0.0";
			}
			
			HashMap param = new HashMap();	
			
			param.put("latitude", latitude);
			param.put("longitude", longitude);
			
			param.put("check_task_staff", check_task_staff);
			param.put("orderCode", orderCode);
			param.put("orderName", orderName);
			param.put("staffType", staffType);
			if(null == check_task_staff || "" == check_task_staff){
//				TaskList=checkTaskDao.intensificationworkoder(param);
				Log.debug("参数为空");
			}
			try {
			TaskList=checkTaskDao.intensificationworkoder(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
					JSONObject result = new JSONObject();
					result.put("workOrder_no", String.valueOf(light.get("WORKORDER_NO")).trim());//工单编号
					result.put("workorder_name", String.valueOf(light.get("WORKORDER_NAME")).trim());//工单名称
					result.put("longitude", String.valueOf(light.get("LONGITUDE")==null?"":light.get("LONGITUDE")).trim());//经度
					result.put("latitude", String.valueOf(light.get("LATITUDE")==null?"":light.get("LATITUDE")).trim());//纬度
					String epq_name= String.valueOf(light.get("EPQ_NAME")==null?"":light.get("EPQ_NAME")).trim();
					if (epq_name==null || epq_name==""){
						result.put("epq_name", "");//设备名称
					}
					result.put("epq_name", epq_name);//设备名称
				//	result.put("epq_name", String.valueOf(light.get("EPQ_NAME")).trim());//设备名称
//					result.put("epq_code", String.valueOf(light.get("EPQ_CODE")).trim());//设备编码
					String barrierAddr= String.valueOf(light.get("BARRIERADDR")==null?"":light.get("BARRIERADDR")).trim();
					if (barrierAddr==null || barrierAddr==""){
						result.put("barrierAddr", "");//设备名称
					}
					result.put("barrierAddr", barrierAddr);//设备名称
			//		result.put("barrierAddr", String.valueOf(light.get("BARRIERADDR")).trim());//障碍地址
/*					result.put("photo", String.valueOf(light.get("PHOTO")).trim());//施工过程图片
					result.put("workload_name", String.valueOf(light.get("WORKLOAD_NAME")).trim());//名称
					result.put("num", String.valueOf(light.get("NUM")).trim());//数量
					result.put("units", String.valueOf(light.get("UNITS")).trim());//工作量信息单位
					result.put("mechanic", String.valueOf(light.get("MECHANIC")).trim());//技工
					result.put("general_workers", String.valueOf(light.get("GENERAL_WORKERS")).trim());//普工
					result.put("material_name", String.valueOf(light.get("MATERIAL_NAME")).trim());//名称
					result.put("model", String.valueOf(light.get("MODEL")).trim());//型号
					result.put("specification", String.valueOf(light.get("SPECIFICATION")).trim());//规格
					result.put("unit", String.valueOf(light.get("UNIT")).trim());//材料信息单位
					result.put("price", String.valueOf(light.get("PRICE")).trim());//价格
					result.put("new_material", String.valueOf(light.get("NEW_MATERIAL")).trim());//新料
					result.put("backEasy_material", String.valueOf(light.get("BACKEASY_MATERIAL")).trim());//回收堪用料
					result.put("back_material", String.valueOf(light.get("BACK_MATERIAL")).trim());//回收废料
*/					result.put("task_enddate", String.valueOf(light.get("TASK_ENDDATE")).trim());//检查任务结束时间

				    result.put("workOrder_type", String.valueOf(light.get("WORKORDER_TYPE")==null?"":light.get("WORKORDER_TYPE")).trim());//工单类型
				    result.put("equipment_type", String.valueOf(light.get("EQUIPMENT_TYPE")==null?"":light.get("EQUIPMENT_TYPE")).trim());//设备类型
				    
				    result.put("task_id", String.valueOf(light.get("TASK_ID")==null?"":light.get("TASK_ID")).trim());//任务id
					
				    result.put("score", String.valueOf(light.get("SCORE")==null?"":light.get("SCORE")).trim());//工单打分
				    result.put("status", String.valueOf(light.get("STATUS")==null?"":light.get("STATUS")).trim());//工单状态，0表示未打分，1已打分
				    
				    arrays.add(result);
				}
				
			}
			res.put("res", arrays);
			res.put("result", "000");
			res.put("desc", "集约化工单查询成功");
		} catch (Exception e) {
			res.put("result", "001");
			res.put("desc", "集约化工单查询失败");
			logger.info(e.toString());
		}
		return res.toString();
		
	}
	
	
	@Override
	public String workoderScoredetail(String jsonStr){
		JSONArray arrays = new JSONArray();
		JSONObject res = new JSONObject();
		List<Map> TaskList = null;
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			
			String workOrder_no =  json.getString("workOrder_no");// 工单号   //"17103113414957";
			HashMap param = new HashMap();	
			param.put("workOrder_no", workOrder_no);
			
			try {
				TaskList=checkTaskDao.workoderScoredetail(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map taskOrder : TaskList) {
					JSONObject result = new JSONObject();
					result.put("workorder_no", String.valueOf(taskOrder.get("WORKORDER_NO")==null?"":taskOrder.get("WORKORDER_NO")).trim());
					result.put("task_id", String.valueOf(taskOrder.get("TASK_ID")==null?"":taskOrder.get("TASK_ID")).trim());
					result.put("score_detail", String.valueOf(taskOrder.get("SCORE_DETAIL")==null?"":taskOrder.get("SCORE_DETAIL")).trim());
					arrays.add(result);
				}
			}
			res.put("res", arrays);
			res.put("result", "000");
			res.put("desc", "集约化工单打分详情查询成功");
		} catch (Exception e) {
			res.put("result", "001");
			res.put("desc", "集约化工单打分详情查询失败");
			logger.info(e.toString());
		}
		return res.toString();
	}
	
	
	@Override
	public String workoderdetail(String jsonStr){
		JSONArray arrays = new JSONArray();
		JSONObject res = new JSONObject();
		List<Map> TaskList = null;
		try {
			
//			String workOrder_no = "17120715224894";
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			String workOrder_no = json.getString("workOrder_no");// 工单号
			String task_id = json.getString("task_id");// 工单号
			HashMap param = new HashMap();	
			param.put("workOrder_no", workOrder_no);
			param.put("task_id", task_id);
			try {
			TaskList=checkTaskDao.workoderdetail(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TaskList.size()>0 && TaskList != null){
				for (Map light : TaskList) {
					JSONObject result = new JSONObject();
//					result.put("workOrder_no", String.valueOf(light.get("WORKORDER_NO")).trim());//工单编号
					result.put("workorder_name", String.valueOf(light.get("WORKORDER_NAME")).trim());//工单名称
					result.put("longitude", String.valueOf(light.get("LONGITUDE")==null?"":light.get("LONGITUDE")).trim());//经度
					result.put("latitude", String.valueOf(light.get("LATITUDE")==null?"":light.get("LATITUDE")).trim());//纬度
					String epq_name= String.valueOf(light.get("EPQ_NAME")==null?"":light.get("EPQ_NAME")).trim();
					if (epq_name==null || epq_name==""){
						result.put("epq_name", "");//设备名称
					}
					result.put("epq_name", epq_name);//设备名称
					
					String epq_code= String.valueOf(light.get("EPQ_CODE")==null?"":light.get("EPQ_CODE")).trim();
					if (epq_code==null || epq_code==""){
						result.put("epq_code", "");//设备名称
					}
					result.put("epq_code",epq_code);//设备编码
					
					result.put("barrierAddr", String.valueOf(light.get("BARRIERADDR")==null?"":light.get("BARRIERADDR")).trim());//障碍地址

					result.put("photo", String.valueOf(light.get("PHOTO")).trim());//施工过程图片
					
					
					/*************************2017年12月5日09:40:29*****************************/
					
					String [] photoStr = String.valueOf(light.get("PHOTO")==null?"":light.get("PHOTO")).trim().split("@@");
					List list = new ArrayList();
					List list_name = new ArrayList(); 
					for (int i = 0;  i < photoStr.length ; i++) {
						
						String[] photoName = photoStr[i].split("\\|");
						String photoPath = "";
						String photoName_res = "";
						if(photoName.length<=0){
							//说明没有照片
						}else if(photoName.length<=1){
							//格式应该不对,不做处理
							System.out.println(photoName);
						}else if(photoName.length<=2){
							//只有图片路径和名称，没有留痕
							photoPath = photoName[0]+photoName[1];
							photoName_res = photoName[1];
						}else if(photoName.length<=3){
							//有图片路径和名称，也有留痕
							//留痕暂时不处理，不管
							photoPath = photoName[0]+photoName[1];
							photoName_res = photoName[1];
						}
						
						list.add(photoPath);
						list_name.add(photoName_res);
						
//						list.add(photoStr[i].replace("|", ""));
					}
					result.put("photos", list);//施工过程图片
					result.put("photosname", list_name);//施工过程图片名称
					
					/**材料信息**/
					JSONArray arrays_mater = new JSONArray();
					JSONObject result_mater = new JSONObject();
					String [] str_mater = String.valueOf(light.get("MATERIAL_INFO")).trim().split("@@");
					for (int i = 0; i < str_mater.length; i++) {
						String [] str = String.valueOf(str_mater[i]).trim().split("\\|");
						setMaterialInfo(str,result_mater);
						arrays_mater.add(result_mater);
					}
					result.put("array_mater_info", arrays_mater);
					
					/**工作信息**/
					JSONArray arrays_work = new JSONArray();
					JSONObject result_work = new JSONObject();
					String [] str_work = String.valueOf(light.get("WORKLOAD_INFO")).trim().split("@@");
					for (int i = 0; i < str_work.length; i++) {
						String [] str = String.valueOf(str_work[i]).trim().split("\\|");
						setWorkLoadInfo(result_work, str);
						arrays_work.add(result_work);
					}
					result.put("array_workload_info", arrays_work);
					
					/*************************2017年12月5日09:40:29*****************************/
				    result.put("workOrder_type", String.valueOf(light.get("WORKORDER_TYPE")==null?"":light.get("WORKORDER_TYPE")).trim());//工单类型
				    result.put("equipment_type", String.valueOf(light.get("EQUIPMENT_TYPE")==null?"":light.get("EQUIPMENT_TYPE")).trim());//设备类型

				    /***************************2017年12月25日11:21:08***************************/
				    setExtraPara(result,light);
				    
					arrays.add(result);
				}
			}
			res.put("res", arrays);
			res.put("result", "000");
			res.put("desc", "集约化工单详情查询成功");
		} catch (Exception e) {
			res.put("result", "001");
			res.put("desc", "集约化工单详情查询失败");
			logger.info(e.toString());
		}
		return res.toString();
		
	}
	
	
	/**
	 * 2017年12月25日11:06:47
	 * 光网助手集约化新增加的字段，赋值进去
	 * @param result
	 * @param map
	 */
	public void setExtraPara(JSONObject result,Map map){
		String staff_group = map.get("STAFF_GROUP")==null?"":map.get("STAFF_GROUP").toString().trim().replace("@@", ",");//组队人员
		String received_date = map.get("RECEIVED_DATE")==null?"":map.get("RECEIVED_DATE").toString().trim();//接单时间
		String date_group = map.get("DATE_GROUP")==null?"":map.get("DATE_GROUP").toString().trim();//组队时间
		String staff_maintor = map.get("STAFF_MAINTOR")==null?"":map.get("STAFF_MAINTOR").toString().trim().replace("@@", ",");//施工人员信息
		String workload_info_split = map.get("WORKLOAD_INFO_SPLIT")==null?"":map.get("WORKLOAD_INFO_SPLIT").toString().trim().replace("@@", ",").replace("&", ":");//工作量拆分信息
		String construct_content = map.get("CONSTRUCT_CONTENT")==null?"":map.get("CONSTRUCT_CONTENT").toString().trim().replace("@@", ",").replace("&", ":");//施工内容描述
		String construct_map = map.get("CONSTRUCT_MAP")==null?"":map.get("CONSTRUCT_MAP").toString().trim();//施工图信息
		String [] construct_map_list = String.valueOf(map.get("CONSTRUCT_MAP")).trim().split("@@");
		List list = new ArrayList(); 
		for (int i = 0; i < construct_map_list.length; i++) {
			list.add(construct_map_list[i].replace("|", ""));
		}
		result.put("construct_map_list", construct_map_list);//施工过程图片
		String date_back = map.get("DATE_BACK")==null?"":map.get("DATE_BACK").toString().trim();//施工回单时间
		String date_check = map.get("DATE_CHECK")==null?"":map.get("DATE_CHECK").toString().trim();//班组检查时间
		result.put("staff_group", staff_group);
		result.put("received_date", received_date);
		result.put("date_group", date_group);
		result.put("staff_maintor", staff_maintor);
		result.put("workload_info_split", workload_info_split);
		result.put("construct_content", construct_content);
		result.put("construct_map", construct_map);
		result.put("date_back", date_back);
		result.put("date_check", date_check);
	}
	
	

	private void setWorkLoadInfo(JSONObject result, String[] strs) {
		if(strs==null ||strs.length<2){
			result.put("workload_name", "");
			result.put("num", "");
			result.put("units", "");
			result.put("mechanic", "");
			result.put("general_workers", "");
		}else{
			String workload_name = returnValue(strs,1);		
			String num = returnValue(strs,2);		
			String units = returnValue(strs,3);			
			String mechanic = returnValue(strs,4);
			String general_workers = returnValue(strs,5);	
			result.put("workload_name", workload_name);
			result.put("num", num);
			result.put("units", units);
			result.put("mechanic", mechanic);
			result.put("general_workers", general_workers);
		}
	}

	private void setMaterialInfo(String[] str,JSONObject result) {
		if(str==null ||str.length<2){
			result.put("material_name", "");
			result.put("model", "");
			result.put("specification", "");
			result.put("unit", "");
			result.put("price", "");
			result.put("new_material", "");
			result.put("backEasy_material", "");
			result.put("back_material", "");
		}else{
			String material_name = returnValue(str,0);//材料名称
			String model = returnValue(str,1);//型号		
			String specification = returnValue(str,2);//规格		
			String unit = returnValue(str,3);  //单位		
			String price = returnValue(str,4);//价格
			String new_material = returnValue(str,5); //新料
			String backEasy_material = returnValue(str,6);//回收堪用料
			String back_material = returnValue(str,7); //回收废料
			result.put("material_name", material_name);
			result.put("model", model);
			result.put("specification", specification);
			result.put("unit", unit);
			result.put("price", price);
			result.put("new_material", new_material);
			result.put("backEasy_material", backEasy_material);
			result.put("back_material", back_material);
		}
	}
	
	/**
	 * 判断下标是否越界，越界则返回空，否则反正正常值
	 * @param str
	 * @param num
	 * @return
	 */
	private String returnValue(String [] str,int num){
		if(str.length==0 || str == null){
			return "";	
		}else{
			if((num+1) <= str.length){
				return str[num]==null?"":str[num].toString();
			}else{
				return "";		
			}
		}
	}
	
	@Override
	public String workOderResult(String jsonStr){
		JSONArray arrays = new JSONArray();
		JSONObject res = new JSONObject();
		List<Map> TaskList = null;
		
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			
			String workOrder_no = json.getString("workOrder_no");// 工单编号
			String score = json.getString("allcount");// 评分总分
			HashMap param = new HashMap();	
			param.put("workOrder_no", workOrder_no);
			param.put("score", score);
			
			
			try {
			checkTaskDao.workOderResult(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			res.put("result", "000");
			res.put("desc", "提交成功");
		} catch (Exception e) {
			res.put("result", "001");
			res.put("desc", "提交成功失败");
			logger.info(e.toString());
		}
		return res.toString();
		
	}
	
	
	@Override
	public String workOderResult_new(String jsonStr){
		JSONArray arrays = new JSONArray();
		JSONObject res = new JSONObject();
		List<Map> TaskList = null;
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String workOrder_no = json.getString("workOrder_no");// 工单编号
			String workOrder_name = json.getString("ordername");// 工单名称
			String score = json.getString("allcount");// 评分总分
			String needchange = json.getString("needchange");//是否整改：0需整改，1不整改 
			//如果总分为null，则返回提示
			if(StringUtils.isBlank(score)){
				res.put("result", "003");
				res.put("desc", "请先打分");
				return res.toString();
			}
			String scoreDetail = json.getString("score_detail");// 评分明细
			String taskId = json.getString("task_id");// 任务id
			String staffId = json.getString("staff_id");// 打分人id
			HashMap param = new HashMap();	
			param.put("workOrder_no", workOrder_no);
			param.put("score", score);
			param.put("scoreDetail", scoreDetail);
			param.put("taskId", taskId);
			param.put("staffId", staffId);
			try {
				checkTaskDao.workOderResult(param);
				checkTaskDao.workOderResultDetail(param);//更新明细
				
				//上述操作成功以后，执行解析的线程
				CalOrderDetail cal = new CalOrderDetail(workOrder_no,taskId,scoreDetail,checkTaskDao);
				cal.start();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//调用云云的方法，推送相应的数据到集约化
			//隐患工单自动推送至集约化
			if("0".equals(needchange)){
				pushjyh(scoreDetail, workOrder_no,workOrder_name);	
				//集约化接口成功的返回值
				//<?xml version="1.0" encoding="GB2312"?><task>
				//<IfResult Note="接口处理结果">0</IfResult>
				//<IfResultInfo Note="接口返回信息"><dealInfo>操作成功！</dealInfo>
				//<eomsTaskId>17122911170751</eomsTaskId>
				//</IfResultInfo></task>
			}
			
			res.put("result", "000");
			res.put("desc", "提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.put("result", "001");
			res.put("desc", "提交失败");
			logger.info(e.toString());
		}
		return res.toString();
		
	}
	
	
	//调用云云的方法，推送相应的数据到集约化
	//隐患工单自动推送至集约化
	private void pushjyh(String scoreDetail, String workOrder_no, String workOrder_name) {
		Map iomMap=new HashMap();
		iomMap.put("taskCatgory", "CHECK");
		iomMap.put("edw_task_id", workOrder_no.toString());
		iomMap.put("descript", scoreDetail);
		iomMap.put("TaskName", workOrder_name);
		iomMap.put("qualityInfo", "test_jyh");
		CheckOrderServiceImpl cs = new CheckOrderServiceImpl();
		cs.autoPushDangerTask(iomMap);//0 表示成功推送至集约化 1 表示失败
	}
	
	
	@Override
	public String workPlanBackOder(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject eqpJsonObject = new JSONObject();
		JSONArray jsArr = new JSONArray();
		try {
			String equipment_id = json.getString("equipment_id");
			Map params = new HashMap();
			params.put("equipment_id", equipment_id);
			
			
			result.put("msg", "作业计划回单成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "作业计划回单失败！");
		}
		return result.toString();
	}
	@Override
	public String getOrderNumByGrid(String jsonStr) {
		JSONObject result = new JSONObject();
		List<Map> GridList = null;
		result.put("result", "000");
		JSONObject GridObject = new JSONObject();
		JSONArray jsArr = new JSONArray();
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数 
			 */
			
			String staff_id = json.getString("staff_id");// 检查人ID
		
			HashMap param = new HashMap();	
			param.put("staff_id", staff_id);
			
		
			GridList=checkTaskDao.getGrid(param);
			if(GridList.size()>0 && GridList != null){
				for(Map grid:GridList){
					GridObject.put("GRID_NM", String.valueOf(grid.get("GRID_NM")));
					GridObject.put("NUM", String.valueOf(grid.get("NUM")));
					jsArr.add(GridObject);
				}
			}
			result.put("GridList", jsArr);
		} catch (Exception e) {
			result.put("desc", "查询网格工单数据失败");
			logger.info(e.toString());
		}
		return result.toString();
	}
	
	
	@Override
	public String errorDataOder(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject eqpJsonObject = new JSONObject();
		JSONArray jsArr = new JSONArray();
		try {
			String equipment_id = json.getString("equipment_id");
			Map params = new HashMap();
			params.put("equipment_id", equipment_id);
			
			
			result.put("msg", "错误数据修改业务单成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "错误数据修改业务单失败！");
		}
		return result.toString();
	}
	
	@Override
	public String errorDataModify(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject eqpJsonObject = new JSONObject();
		JSONArray jsArr = new JSONArray();
		try {
			String equipment_id = json.getString("equipment_id");
			String task_id = json.getString("task_id");
			String grid_id = json.getString("grid_id");
			String equipment_name = json.getString("equipment_name");
			Map params = new HashMap();
			params.put("equipment_id", equipment_id);
			
			
			result.put("msg", "错误信息修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "错误信息修改失败！");
		}
		return result.toString();
	}

	//点击工单检查按钮，显示工单端子信息（新装、拆机）
	@Override
	public String getEquWorkOrderList(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			String equCode = json.getString("equCode");//光交或光分设备编码(网格下的设备列表)
			String equId = json.getString("equId");// 光交或光分设备id (网格下的设备列表)
			String areaId = json.getString("areaId");//地市
			String orderNo=json.getString("orderNo");//订单编号	
			//变动时间段
			String port_start_time="";
			String port_end_time="";
			/*String eqp_id=json.getString("eqp_id");//订单所在的设备id*/			
			
			/*String equCode = "250QH.HHC00/GJ026";//光交或光分设备编码(网格下的设备列表)
			String equId = "25320000000538";// 光交或光分设备id (网格下的设备列表)
			String areaId ="3" ;//地市
			String orderNo= "20170704024954";//订单编号
			 */		
			
			JSONArray jsonArray=new JSONArray();
			JSONArray eqpRecords=new JSONArray();//存放设备信息
			JSONArray ports=new JSONArray();//存放端子信息
			JSONObject portObject=new JSONObject();
			JSONObject eqpObject=new JSONObject();
			JSONArray portRecords=new JSONArray();//存放设备端子数量
			JSONObject portRecord=new JSONObject();
			Map<String,String> ossMap=new HashMap<String, String>();
			Map<String,String> eqpMap=new HashMap<String, String>();
			Map ossParam =new HashMap();
			Map<String,String> ossResult =new HashMap<String,String>();
			Map param =new HashMap();
			param.put("equCode", equCode);
			param.put("equId", equId);
			param.put("areaId", areaId);
			param.put("orderNo", orderNo);
			/*param.put("eqp_id", eqp_id);*/
			
			//通过网格下设备id和设备编码查询所有分光器
			List<Map<String, String>> obdList=checkTaskDao.getObd(param);
			if(obdList!=null&&obdList.size()>0){
				for(Map obd:obdList){
					eqpObject.put("phy_eqp_id", obd.get("EQUIPMENT_ID").toString());
					eqpObject.put("equipment_code", obd.get("EQUIPMENT_CODE").toString());
					eqpObject.put("equipment_name", obd.get("EQUIPMENT_NAME").toString());
					eqpObject.put("phy_eqp_spec_id", obd.get("RES_TYPE_ID").toString());
					eqpRecords.add(eqpObject);
				}
			}
			//通过订单编号查询出端子所在的设备id
			List<Map> list=checkTaskDao.getPortList(param);
			//目前通过网格下的设备id和设备编码查询出设备下的所有订单编号
			//List<Map> list=checkTaskDao.getPortList(param);
			List<String> eqplist=new ArrayList<String>();
			if(list!=null&&list.size()>0){
				for(Map port:list){
					String opt_code=port.get("OPT_CODE").toString();
					String order_id=port.get("ORDER_ID").toString();
					String phy_eqp_id=port.get("PHY_EQP_ID").toString();//端子所属设备id
					String equipment_code=port.get("EQUIPMENT_CODE").toString();//端子所属设备编码
					String equipment_name=port.get("EQUIPMENT_NAME").toString();
					String phy_eqp_spec_id=port.get("PHY_EQP_SPEC_ID").toString();
					String phy_port_id=port.get("PHY_PORT_ID").toString();
					String phy_port_spec_no=port.get("PHY_PORT_SPEC_NO").toString();
					String action_type=port.get("ACTION_TYPE").toString();//工单类型 例如 17：建设单（拆），18：建设单（新装）
					String work_order_type=port.get("WORK_ORDER_TYPE").toString();// 17,18
					String portnum=port.get("PORTNUM").toString();
					
					ossParam.put("eqpId", phy_eqp_id);
					ossParam.put("glbh", opt_code);
					ossParam.put("areaId", areaId);
					
					//如果是分光器
					if("2530".equals(phy_eqp_spec_id)){
						//先通过分光器获取箱子设备ID和设备编码，在判断通过分光器获取的箱子设备id和设备编码是否与网格下的箱子设备信息是否一致
						//一致的传给手机端展示出来
						//不一致，则将端子编码置为空，点击空的端子编码或光路编码跳转到该工单光路编码的箱子处--不同箱子的工单端子页面去检查(暂时不做)
						eqpMap=checkTaskDao.getOuterEqp(port);
						String outerEquId=eqpMap.get("INSTALL_SBID");
						String outerEquNo=eqpMap.get("INSTALL_SBBM");
						String outerEquName=eqpMap.get("EQUIPMENT_NAME");
						if(equId.equals(outerEquId)&&equCode.equals(outerEquNo)){
							/*eqpObject.put("phy_eqp_id", phy_eqp_id);
							eqpObject.put("equipment_code", equipment_code);
							eqpObject.put("equipment_name", equipment_name);
							eqpObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							if(isNotExist(eqplist,phy_eqp_id)){
								eqpRecords.add(eqpObject);
								eqplist.add(phy_eqp_id);
							}*/
							portRecord.put("phy_eqp_id", phy_eqp_id);
							portRecord.put("equipment_code", equipment_code);
							portRecord.put("portnum", portnum);
							if(isNotExist(eqplist,phy_eqp_id)){
								portRecords.add(portRecord);
								eqplist.add(phy_eqp_id);
							}					
							portObject.put("phy_eqp_id", phy_eqp_id);
							portObject.put("equipment_code", equipment_code);
							portObject.put("equipment_name", equipment_name);
							portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
						//	portObject.put("phy_port_id", phy_port_id);
						//	portObject.put("phy_port_spec_no", phy_port_spec_no);
							portObject.put("action_type", action_type);
							portObject.put("opt_code", opt_code);
							portObject.put("order_id", order_id);
							portObject.put("orderNo", orderNo);
							//如果是拆机的，则不展示实时端口
							String ossPortNo="";
							String ossPortId="";
							if("17".equals(work_order_type)){
								ossPortId=phy_port_id;
								ossPortNo=phy_port_spec_no;
							}else{
								//调用oss查询实时端口
								ossResult=getOssPort(ossParam);
								String oss_result=ossResult.get("result");								
								if("000".equals(oss_result)){
									ossPortId=ossResult.get("ossPortId");
									ossPortNo=ossResult.get("ossPortNo");
								}else{
									ossPortId="空闲";
									ossPortNo="空闲";
								}
							}							
							portObject.put("phy_port_id", ossPortId);
							portObject.put("phy_port_spec_no", ossPortNo);
							ports.add(portObject);
						}/*else{//如果通过分光器查询出来的箱子与网格下的箱子不一致，就不把分光器的记录放入网格下的设备
							portObject.put("phy_eqp_id", phy_eqp_id);
							portObject.put("equipment_code", equipment_code);
							portObject.put("equipment_name", equipment_name);
							portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							portObject.put("phy_port_id", "");
							portObject.put("phy_port_spec_no", "");
							portObject.put("action_type", action_type);
							portObject.put("opt_code", opt_code);
							portObject.put("order_id", order_id);
							portObject.put("orderNo", orderNo);
							ports.add(portObject);
						}*/
					}else{
						//如果是箱子,先判断通过工单查询出来的设备id和设备编码是否与网格下的设备信息是否一致，
						//如果一致，展示，其实并没有一致的情况，做一下判断更好一点
						if(equId.equals(phy_eqp_id)&&equCode.equals(equipment_code)){
							/*eqpObject.put("phy_eqp_id", phy_eqp_id);
							eqpObject.put("equipment_code", equipment_code);
							eqpObject.put("equipment_name", equipment_name);
							eqpObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							eqpRecords.add(eqpObject);	*/
							portRecord.put("phy_eqp_id", phy_eqp_id);
							portRecord.put("equipment_code", equipment_code);
							portRecord.put("portnum", portnum);
							if(isNotExist(eqplist,phy_eqp_id)){
								portRecords.add(portRecord);
								eqplist.add(phy_eqp_id);
							}
							portObject.put("phy_eqp_id", phy_eqp_id);
							portObject.put("equipment_code", equipment_code);
							portObject.put("equipment_name", equipment_name);
							portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							portObject.put("phy_port_id", phy_port_id);
							portObject.put("phy_port_spec_no", phy_port_spec_no);
							portObject.put("action_type", action_type);
							portObject.put("opt_code", opt_code);
							portObject.put("order_id", order_id);
							portObject.put("orderNo", orderNo);
							//如果是拆机的，则不展示实时端口
							String ossPortNo="";
							String ossPortId="";
							if("17".equals(work_order_type)){
								ossPortId=phy_port_id;
								ossPortNo=phy_port_spec_no;
							}else{
								//调用oss查询实时端口
								ossResult=getOssPort(ossParam);
								String oss_result=ossResult.get("result");								
								if("000".equals(oss_result)){
									ossPortId=ossResult.get("ossPortId");
									ossPortNo=ossResult.get("ossPortNo");
								}else{
									ossPortId="空闲";
									ossPortNo="空闲";
								}
							}		
							portObject.put("phy_port_id", ossPortId);
							portObject.put("phy_port_spec_no", ossPortNo);
							ports.add(portObject);
						}
					}					
				}
				//通过eqpRecords和portRecords进行对比，判断出工单设备的端子量
				JSONObject port1=new JSONObject();
				JSONObject eqp1=new JSONObject();
				String eqpId="";
				String eqpNo="";
				String port_eqpId="";
				String port_eqpNo="";
				String portNum="";
				for(int i=0;i<eqpRecords.size();i++){
					eqp1=eqpRecords.getJSONObject(i);
					eqpId=eqp1.getString("phy_eqp_id");
					eqpNo=eqp1.getString("equipment_code");
					for(int j=0;j<portRecords.size();j++){
						port1=portRecords.getJSONObject(j);
						port_eqpId=port1.getString("phy_eqp_id");
						port_eqpNo=port1.getString("equipment_code");
						portNum=port1.getString("portnum");
						if(eqpId.equals(port_eqpId)&&eqpNo.equals(port_eqpNo)){
							eqp1.put("portNum", portNum);
							break;
						}else{
							eqp1.put("portNum", "0");
						}
					}
				}
				//对eqpRecords进行排序，设备中工单不为0的放在最上面
				JSONObject c1=new JSONObject();
				JSONObject c2=new JSONObject();
				Integer c1_num=0;
				Integer c2_num=0;
				for(int i=0;i<eqpRecords.size()-1;i++){
					for(int j=i+1;j<eqpRecords.size();j++){
						c1=eqpRecords.getJSONObject(i);
						c1_num=Integer.valueOf(c1.getString("portNum"));
						c2=eqpRecords.getJSONObject(j);
						c2_num=Integer.valueOf(c2.getString("portNum"));
						if(c1_num<c2_num){
							eqpRecords.set(i, c2);
							eqpRecords.set(j, c1);
						}
					}
				}
				result.put("eqpRecords", eqpRecords);
				result.put("ports", ports);
			}else{
				result.put("eqpRecords", "");
				result.put("ports", "");
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "查询工单端子信息失败");
			logger.info(e.toString());
		}
		return result.toString();
	}
	
	//点击工单检查按钮，显示设备端子信息（新装、拆机）
	@Override
	public String getEquWorkList(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			String equCode = json.getString("equCode");//光交或光分设备编码(网格下的设备列表)
			String equId = json.getString("equId");// 光交或光分设备id (网格下的设备列表)
			String areaId = json.getString("areaId");//地市
			//String orderNo=json.getString("orderNo");//订单编号	
			//变动时间段
			String port_start_time=json.getString("startTime");
			String port_end_time=json.getString("endTime");
			/*String eqp_id=json.getString("eqp_id");//订单所在的设备id*/			
			
			/*String equCode = "250QH.HHC00/GJ026";//光交或光分设备编码(网格下的设备列表)
			String equId = "25320000000538";// 光交或光分设备id (网格下的设备列表)
			String areaId ="3" ;//地市
			String orderNo= "20170704024954";//订单编号
			 */		
			
			JSONArray jsonArray=new JSONArray();
			JSONArray eqpRecords=new JSONArray();//存放设备信息
			JSONArray ports=new JSONArray();//存放端子信息
			JSONObject portObject=new JSONObject();
			JSONObject eqpObject=new JSONObject();
			JSONArray portRecords=new JSONArray();//存放设备端子数量
			JSONObject portRecord=new JSONObject();
			Map<String,String> ossMap=new HashMap<String, String>();
			Map<String,String> eqpMap=new HashMap<String, String>();
			Map ossParam =new HashMap();
			Map<String,String> ossResult =new HashMap<String,String>();
			Map param =new HashMap();
			param.put("equCode", equCode);
			param.put("equId", equId);
			param.put("areaId", areaId);
			//param.put("orderNo", orderNo);
			param.put("port_start_time", port_start_time);
			param.put("port_end_time", port_end_time);
			/*param.put("eqp_id", eqp_id);*/
			
			//通过网格下设备id和设备编码查询所有分光器
			List<Map<String, String>> obdList=checkTaskDao.getObd(param);
			if(obdList!=null&&obdList.size()>0){
				for(Map obd:obdList){
					eqpObject.put("phy_eqp_id", obd.get("EQUIPMENT_ID").toString());
					eqpObject.put("equipment_code", obd.get("EQUIPMENT_CODE").toString());
					eqpObject.put("equipment_name", obd.get("EQUIPMENT_NAME").toString());
					eqpObject.put("phy_eqp_spec_id", obd.get("RES_TYPE_ID").toString());
					eqpObject.put("portNum", "0");
					eqpRecords.add(eqpObject);
				}
			}
			//通过订单编号查询出端子所在的设备id
			//List<Map> list=checkTaskDao.getPortList(param);
			//目前通过网格下的设备id和设备编码查询出设备下的所有订单编号
			List<Map> list=checkTaskDao.getEqpOrderList(param);
			String jndi=RESUtil.getRes(areaId);
			list= deleteSameOrderByGlbm(jndi,list);
			List<String> eqplist=new ArrayList<String>();
			if(list!=null&&list.size()>0){
				for(Map port:list){
					String opt_code=port.get("OPT_CODE").toString();
					String order_id=port.get("ORDER_ID").toString();
					String orderNo=port.get("ORDER_NO").toString();
					String phy_eqp_id=port.get("PHY_EQP_ID").toString();//端子所属设备id
					String equipment_code=port.get("EQUIPMENT_CODE").toString();//端子所属设备编码
					String equipment_name=port.get("EQUIPMENT_NAME").toString();
					String phy_eqp_spec_id=port.get("PHY_EQP_SPEC_ID").toString();
					String phy_port_id=port.get("PHY_PORT_ID").toString();
					String phy_port_spec_no=port.get("PHY_PORT_SPEC_NO").toString();
					String action_type=port.get("ACTION_TYPE").toString();//工单类型 例如 17：建设单（拆），18：建设单（新装）
					String work_order_type=port.get("WORK_ORDER_TYPE").toString();// 17,18
					
					ossParam.put("eqpId", phy_eqp_id);
					ossParam.put("glbh", opt_code);
					ossParam.put("areaId", areaId);
					
					//如果是分光器
					if("2530".equals(phy_eqp_spec_id)){
						//先通过分光器获取箱子设备ID和设备编码，在判断通过分光器获取的箱子设备id和设备编码是否与网格下的箱子设备信息是否一致
						//一致的传给手机端展示出来
						//不一致，则将端子编码置为空，点击空的端子编码或光路编码跳转到该工单光路编码的箱子处--不同箱子的工单端子页面去检查(暂时不做)
						eqpMap=checkTaskDao.getOuterEqp(port);
						String outerEquId=eqpMap.get("INSTALL_SBID");
						String outerEquNo=eqpMap.get("INSTALL_SBBM");
						String outerEquName=eqpMap.get("EQUIPMENT_NAME");
						if(equId.equals(outerEquId)&&equCode.equals(outerEquNo)){
							portRecord.put("phy_eqp_id", phy_eqp_id);
							portRecord.put("equipment_code", equipment_code);
							portRecord.put("portnum", "1");
							portRecords.add(portRecord);
							/*if(isNotExist(eqplist,phy_eqp_id)){
								portRecords.add(portRecord);
								eqplist.add(phy_eqp_id);
							}*/					
							portObject.put("phy_eqp_id", phy_eqp_id);
							portObject.put("equipment_code", equipment_code);
							portObject.put("equipment_name", equipment_name);
							portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							portObject.put("action_type", action_type);
							portObject.put("opt_code", opt_code);
							portObject.put("order_id", order_id);
							portObject.put("orderNo", orderNo);
							//如果是拆机的，则不展示实时端口
							String ossPortNo="";
							String ossPortId="";
							if("17".equals(work_order_type)){
								ossPortId=phy_port_id;
								ossPortNo=phy_port_spec_no;
							}else{
								//调用oss查询实时端口
								ossResult=getOssPort(ossParam);
								String oss_result=ossResult.get("result");								
								if("000".equals(oss_result)){
									ossPortId=ossResult.get("ossPortId");
									ossPortNo=ossResult.get("ossPortNo");
								}else{
									ossPortId="空闲";
									ossPortNo="空闲";
								}
							}							
							portObject.put("phy_port_id", ossPortId);
							portObject.put("phy_port_spec_no", ossPortNo);
							ports.add(portObject);
						}
					}else{
						//如果是箱子,先判断通过工单查询出来的设备id和设备编码是否与网格下的设备信息是否一致，
						//如果一致，展示，其实并没有一致的情况，做一下判断更好一点
						if(equId.equals(phy_eqp_id)&&equCode.equals(equipment_code)){
							portRecord.put("phy_eqp_id", phy_eqp_id);
							portRecord.put("equipment_code", equipment_code);
							portRecord.put("portnum", "1");
							portRecords.add(portRecord);
							/*if(isNotExist(eqplist,phy_eqp_id)){
								portRecords.add(portRecord);
								eqplist.add(phy_eqp_id);
							}*/
							portObject.put("phy_eqp_id", phy_eqp_id);
							portObject.put("equipment_code", equipment_code);
							portObject.put("equipment_name", equipment_name);
							portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							portObject.put("phy_port_id", phy_port_id);
							portObject.put("phy_port_spec_no", phy_port_spec_no);
							portObject.put("action_type", action_type);
							portObject.put("opt_code", opt_code);
							portObject.put("order_id", order_id);
							portObject.put("orderNo", orderNo);
							//如果是拆机的，则不展示实时端口
							String ossPortNo="";
							String ossPortId="";
							if("17".equals(work_order_type)){
								ossPortId=phy_port_id;
								ossPortNo=phy_port_spec_no;
							}else{
								//调用oss查询实时端口
								ossResult=getOssPort(ossParam);
								String oss_result=ossResult.get("result");								
								if("000".equals(oss_result)){
									ossPortId=ossResult.get("ossPortId");
									ossPortNo=ossResult.get("ossPortNo");
								}else{
									ossPortId="空闲";
									ossPortNo="空闲";
								}
							}		
							portObject.put("phy_port_id", ossPortId);
							portObject.put("phy_port_spec_no", ossPortNo);
							ports.add(portObject);
						}
					}					
				}
				//通过eqpRecords和portRecords进行对比，判断出工单设备的端子量
				JSONObject port1=new JSONObject();
				JSONObject eqp1=new JSONObject();
				String eqpId="";
				String eqpNo="";
				String port_eqpId="";
				String port_eqpNo="";
				int portNum=0;
				for(int i=0;i<eqpRecords.size();i++){
					int num=0;
					eqp1=eqpRecords.getJSONObject(i);
					eqpId=eqp1.getString("phy_eqp_id");
					eqpNo=eqp1.getString("equipment_code");
					num=Integer.parseInt(eqp1.getString("portNum"));
					for(int j=0;j<portRecords.size();j++){
						port1=portRecords.getJSONObject(j);
						port_eqpId=port1.getString("phy_eqp_id");
						port_eqpNo=port1.getString("equipment_code");
						portNum=Integer.parseInt(port1.getString("portnum"));
						if(eqpId.equals(port_eqpId)&&eqpNo.equals(port_eqpNo)){
							num=num+portNum;
							eqp1.put("portNum",num);
						}
					}
				}
				//对eqpRecords进行排序，设备中工单不为0的放在最上面
				JSONObject c1=new JSONObject();
				JSONObject c2=new JSONObject();
				Integer c1_num=0;
				Integer c2_num=0;
				for(int i=0;i<eqpRecords.size()-1;i++){
					for(int j=i+1;j<eqpRecords.size();j++){
						c1=eqpRecords.getJSONObject(i);
						c1_num=Integer.valueOf(c1.getString("portNum"));
						c2=eqpRecords.getJSONObject(j);
						c2_num=Integer.valueOf(c2.getString("portNum"));
						if(c1_num<c2_num){
							eqpRecords.set(i, c2);
							eqpRecords.set(j, c1);
						}
					}
				}
				result.put("eqpRecords", eqpRecords);
				result.put("ports", ports);
			}else{
				result.put("eqpRecords", "");
				result.put("ports", "");
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "查询工单端子信息失败");
			logger.info(e.toString());
		}
		return result.toString();
	}
	//通过设备id、地市、光路编码获取光路所在的实时端口
	public  Map<String, String> getOssPort(Map ossParam){
		Map<String, String> param=new HashMap<String, String>();
		try{			
			String area_id=ossParam.get("areaId").toString();;//地市
			String jndi = cableInterfaceDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				param.put("result", "001");
				return param;
			}
			ossParam.put("jndi", jndi);
			Map<String,String> ossMap=new HashMap<String, String>();
			String ossPortId="";
			String ossPortNo="";
			//首先通过设备id和光路编号从OSS中取出光路编号对应的端子id(ossPort)
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			ossMap= checkTaskDao.getOssPort(ossParam);//通过设备ID和光路编号到OSS实时查询光路占用端口		
			SwitchDataSourceUtil.clearDataSource();
			if(ossMap!=null&&ossMap.size()>0){
				ossPortId=ossMap.get("PORT_ID");	
				ossPortNo=ossMap.get("DZBM");
			}else{
				ossPortId="空闲";	
				ossPortNo="空闲";
			}			
			param.put("result", "000");
			param.put("ossPortId", ossPortId);
			param.put("ossPortNo", ossPortNo);
		}catch (Exception e) {
			e.printStackTrace();
			param.put("result", "001");
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
		return param;
	}
	
	@Override
	public String getPortsByAreaEqu(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray portsArray=new JSONArray();
		JSONObject portObject=new JSONObject();
		result.put("result", "000");
		Map portMap=new HashMap<String, String>();
		List<Map<String,String>> portsList=new ArrayList<Map<String,String>>();
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String areaId = json.getString("areaId");// 地市id
			String eqpId = json.getString("eqpId");// 设备id	
			/*String areaId = "3";// 地市id
			String eqpId = "25800005647026";// 设备id*/			
			String jndi = cableInterfaceDao.getDBblinkName(areaId);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			Map param= new HashMap();
			param.put("eqpId", eqpId);
			param.put("jndi", jndi);
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
				result.put("portsList", portsArray);
			}else{
				result.put("portsList", "");
			}	
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "查询所有端子失败");
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}
	


	@Override
	public String submitWorkOrderPort(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put("result", "000");		
		
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String staffId = json.getString("staffId");// 人员账号id
			String eqpId = json.getString("eqpId");// 设备id
			String eqpNo = json.getString("eqpNo");// 设备编码
			String eqpName = json.getString("eqpName");// 设备名称
			String remark = json.getString("remark");// 现场规范
			String is_bill = json.getString("is_bill");// 是否需要整改 0 无需整改 1需要整改
			String order_id = json.getString("order_id");//工单ID
			String order_no = json.getString("order_no");//订单编号
			String areaId=json.getString("areaId");//地市
			String eqpPhotoIds = json.getString("photoId");// 设备照片
			String sonAreaId="";//通过设备编码和设备ID查询设备所在区域
			
			Map eqp_order_map=new HashMap();
			eqp_order_map.put("eqpId", eqpId);
			eqp_order_map.put("eqpNo", eqpNo);
			eqp_order_map.put("order_id", order_id);
			eqp_order_map.put("order_no", order_no);
			eqp_order_map.put("staffId", staffId);
			
			Map eqpId_eqpNo_map = new HashMap();
			eqpId_eqpNo_map.put("eqpId", eqpId);
			eqpId_eqpNo_map.put("eqpNo", eqpNo);
			Map eqpareaMap = checkOrderDao.queryAreaByeqpId(eqpId_eqpNo_map);
			String eqpAreaId = null == eqpareaMap.get("PARENT_AREA_ID") ? ""
					: eqpareaMap.get("PARENT_AREA_ID").toString();//设备所属地市
			String eqpSonAreaId = null == eqpareaMap.get("AREA_ID") ? ""
					: eqpareaMap.get("AREA_ID").toString();//设备所属区县
			sonAreaId=eqpSonAreaId;
			String taskid="";
			//通过 order_no 查询出工单所在班组，并派发给班组的施工人（原工单的施工人）
			Map map_sgr=checkTaskDao.getOrderOfBanZu(order_no);
			String forward_staff_id="";
			String auditor="";
			String maintor="";
			if(map_sgr!=null&&map_sgr.size()>0){
				 forward_staff_id= map_sgr.get("STAFF_ID")==null?"":map_sgr.get("STAFF_ID").toString();
			}
			
			Map map_bz=checkTaskDao.getBanZuByOrderNo(order_no);
			String team_id=map_bz.get("TEAM_ID").toString();//班组
			Map receiverMap=checkTaskDao.getOrderReceiverOfBanZu(team_id);
			
			if("".equals(forward_staff_id)){
				//如果未找到施工人，就通过工单查询工单所在班组，并且发送给工单所在班组的接单人
				forward_staff_id=receiverMap.get("STAFF_ID").toString();
				maintor=forward_staff_id;
				auditor=forward_staff_id;
			}else{
				String forward_staff_id_new=receiverMap.get("STAFF_ID").toString();
				maintor=forward_staff_id;
				auditor=forward_staff_id_new;
			}
			
			/**
			 * 端子信息
			 */
			JSONArray innerArray = json.getJSONArray("port");
			
			// 先保存工单
			Map troubleTaskMap = new HashMap();
			String TASK_NO=eqpNo+ "_" + DateUtil.getDate("yyyyMMdd");
			String TASK_NAME=eqpName+ "_"+ DateUtil.getDate("yyyyMMdd");
			troubleTaskMap.put("TASK_NO", TASK_NO);
			troubleTaskMap.put("TASK_NAME",TASK_NAME);
			troubleTaskMap.put("TASK_TYPE", "2");// 不预告- 智检查检查工单端子信息 ,暂时设置为2 ，
			troubleTaskMap.put("INSPECTOR", staffId);
			troubleTaskMap.put("CREATE_STAFF", staffId);
			troubleTaskMap.put("SON_AREA_ID",sonAreaId);
			troubleTaskMap.put("AREA_ID",areaId);
			troubleTaskMap.put("ENABLE","0".equals(is_bill) ? 1 : 0);// 如果不需要整改工单，则把此工单只为无效,0可用// 1不可用（不显示在待办列表）
			troubleTaskMap.put("REMARK", remark);//现场规范
			troubleTaskMap.put("IS_NEED_ZG", is_bill);// 是否需要整改
			troubleTaskMap.put("OLD_TASK_ID", "");// 老的task_id
			troubleTaskMap.put("SBID", eqpId);// 设备id
			
			
			if("0".equals(is_bill)){//无需整改
				troubleTaskMap.put("is_from", "0");//is_from 网络即时检查与纠错
				troubleTaskMap.put("STATUS_ID",8);// 无需整改直接归档
				troubleTaskMap.put("MAINTOR", "");
				troubleTaskMap.put("AUDITOR", "");
			}else{//需整改	
				troubleTaskMap.put("is_from", "0");
				troubleTaskMap.put("STATUS_ID",6);
				troubleTaskMap.put("MAINTOR", maintor);// 需整改生成修改业务单后自动派发给对应网格班组装维人员--工单施工人
				troubleTaskMap.put("AUDITOR", auditor);// 需整改生成修改业务单后自动派发给对应网格班组的审核人员
			}
			
			//插入任务表  
			checkTaskDao.saveTroubleTask(troubleTaskMap);
			taskid = troubleTaskMap.get("TASK_ID").toString();
			
			for(int i=0;i<innerArray.size();i++){
				JSONObject port = innerArray.getJSONObject(i);
				String eqpId_port = null == port.get("phy_eqp_id") ? "" : port.getString("phy_eqp_id");
				String eqpNo_port = null == port.get("equipment_code") ? "" : port.getString("equipment_code");
				String eqpName_port = null == port.get("equipment_name") ? "" : port.getString("equipment_name");
				String portId = port.getString("phy_port_id");
				String portNo = null == port.get("phy_port_spec_no") ? "": port.getString("phy_port_spec_no");
				String portPhotoIds = port.getString("photoId");
				String reason = port.getString("reason");//端子错误描述				
				String isCheckOK = port.getString("ischeckok");//端子是否合格
				
				//修改后的端子ID和端子编码，设备ID和设备编码
				String changedPortId=port.getString("portId_new");//修改后的端子id
				String changedPortNo=port.getString("localPortNo");//修改后的端子编码
				String changedEqpId=port.getString("sbid_new");//修改后的设备ID
				String changedEqpNo=port.getString("sbbm_new");//修改后的设备编码
				
				String portNoRightPosition = port.getString("remark");//端子正确位置--端子编码
				String portIdRightPosition = port.getString("remarkId");//端子正确位置--端子id
				String eqpNo_rightPortNo=port.getString("rightEqpNo");//正确设备编码
				String eqpId_rightPortNo=port.getString("rightEqpId");//正确设备ID
				if("查询".equals(portNoRightPosition)){
					portNoRightPosition="";
					portIdRightPosition="";
				}
				String glbm = null == port.get("glbm") ? "": port.getString("glbm");
				String action_type = null == port.get("action_type") ? "": port.getString("action_type");//工单类型
				int recordId = checkOrderDao.getRecordId();
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", taskid);
				taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", "1");
				taskDetailMap.put("CHECK_FLAG", "1");
				taskDetailMap.put("PORT_ID", portId);
				taskDetailMap.put("INSPECT_OBJECT_NO", portNo);
				taskDetailMap.put("GLBM", glbm);
				//插入任务详情表
				checkTaskDao.saveTroubleTaskDetail(taskDetailMap);
				String detail_id = taskDetailMap.get("DETAIL_ID").toString();
				Map recordMap=new HashMap();
				recordMap.put("recordId", recordId);	
				recordMap.put("task_id", taskid);
				recordMap.put("TASK_NO", TASK_NO);
				recordMap.put("TASK_NAME", TASK_NAME);
				recordMap.put("glbm", glbm);
				recordMap.put("detail_id", detail_id);
				recordMap.put("eqpId", eqpId_port);
				recordMap.put("eqpNo", eqpNo_port);
				recordMap.put("eqpName", eqpName_port);
				recordMap.put("staffId", staffId);
				recordMap.put("remarks", remark);//现场规范
				recordMap.put("port_id", portId);
				recordMap.put("port_no", portNo);
				recordMap.put("action_type", action_type);
				recordMap.put("order_id", order_id);
				recordMap.put("order_no", order_no);
				recordMap.put("is_from", "0");
				recordMap.put("port_name", "");
				recordMap.put("descript", reason);//端子错误描述
				recordMap.put("isCheckOK", isCheckOK);//端子是否合格
				recordMap.put("portRightPosition", portNoRightPosition);//端子编码正确位置
				recordMap.put("portIdRightPosition", portIdRightPosition);//端子ID正确位置
				recordMap.put("eqpNo_rightPortNo", eqpNo_rightPortNo);//设备编码正确位置
				recordMap.put("eqpId_rightPortNo", eqpId_rightPortNo);//设备ID正确位置
				recordMap.put("record_type", "1");
				recordMap.put("area_id", areaId);
				recordMap.put("son_area_id", sonAreaId);
				
				recordMap.put("changedPortId", changedPortId);//修改后端子ID
				recordMap.put("changedPortNo", changedPortNo);//修改后端子编码
				recordMap.put("changedEqpId", changedEqpId);//修改后设备ID
				recordMap.put("changedEqpNo", changedEqpNo);//修改后设备编码
				//插入记录表
				checkTaskDao.insertEqpRecord(recordMap);
				// 保存端子照片关系
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskid);
				photoMap.put("DETAIL_ID", detail_id);
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("REMARKS", "隐患上报-智检查");
				photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("RECORD_ID", recordId);
				if (!"".equals(portPhotoIds)) {
					String[] photos = portPhotoIds.split(",");
					for (String photo : photos) {
						photoMap.put("PHOTO_ID", photo);
						checkOrderDao.insertPhotoRel(photoMap);
					}
				}
				//将修改后的端子记录插入留痕记录表
				/*if(!"".equals(changedPortNo)){
					String content="";
					if(eqpId_port.equals(changedEqpId)){
						content="一键改: "+glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedPortNo+"端口";
					}else{
						content ="一键改: "+glbm+"从"+eqpId_port+"的"+portNo+"端口成功改至"+changedEqpNo+"的"+changedPortNo+"端口";
					}
					recordMap.put("content", content);
					recordMap.put("task_type", "1");
					checkTaskDao.saveTrace(recordMap);
				}*/
				//将修改后的端子插入流程表 tb_cablecheck_process
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
			//保存设备检查任务详情
			int recordId = checkOrderDao.getRecordId();
			Map taskDetailMap = new HashMap();
			taskDetailMap.put("TASK_ID", taskid);
			taskDetailMap.put("INSPECT_OBJECT_ID", recordId);
			taskDetailMap.put("INSPECT_OBJECT_TYPE", "0");
			taskDetailMap.put("CHECK_FLAG", "1");
			taskDetailMap.put("PORT_ID", "");
			taskDetailMap.put("INSPECT_OBJECT_NO", eqpNo);
			taskDetailMap.put("GLBM", "");
			//插入任务详情表
			checkTaskDao.saveTroubleTaskDetail(taskDetailMap);
			String detail_id = taskDetailMap.get("DETAIL_ID").toString();
			
			Map recordMap=new HashMap();
			recordMap.put("recordId", recordId);	
			recordMap.put("task_id", taskid);
			recordMap.put("TASK_NO", TASK_NO);
			recordMap.put("TASK_NAME", TASK_NAME);
			recordMap.put("detail_id", detail_id);
			recordMap.put("eqpId", eqpId);
			recordMap.put("eqpNo", eqpNo);
			recordMap.put("eqpName", eqpName);
			recordMap.put("staffId", staffId);
			recordMap.put("remarks", remark);//现场规范
			recordMap.put("port_id", "");
			recordMap.put("port_no", "");
			recordMap.put("order_id", order_id);
			recordMap.put("order_no", order_no);
			recordMap.put("port_name", "");
			recordMap.put("is_from", "0");
			recordMap.put("descript", "");//端子错误描述
			recordMap.put("isCheckOK", "1".equals(is_bill) ? 1: 0);//端子是否合格
			recordMap.put("portRightPosition", "");//端子正确位置
			recordMap.put("portIdRightPosition", "");//端子正确位置
			recordMap.put("eqpNo_rightPortNo", "");
			recordMap.put("eqpId_rightPortNo", "");
			recordMap.put("changedPortId", "");//修改后端子ID
			recordMap.put("changedPortNo", "");//修改后端子编码
			recordMap.put("changedEqpId", "");//修改后设备ID
			recordMap.put("changedEqpNo", "");//修改后设备编码
			recordMap.put("record_type", "1");
			recordMap.put("area_id", areaId);
			recordMap.put("task_type", "1");
			recordMap.put("action_type", "");
			recordMap.put("son_area_id", sonAreaId);
			//插入记录表
			checkTaskDao.insertEqpRecord(recordMap);
			// 插入流程环节
			Map processMap = new HashMap();
			processMap.put("task_id", taskid);
			processMap.put("oper_staff", staffId);
			if ("1".equals(is_bill)) {
				processMap.put("status", 6);
				processMap.put("remark", "检查提交");
				processMap.put("receiver", maintor);
				processMap.put("content", "生成整改工单并自动派发至维护员");
				recordMap.put("content", "隐患上报");
			} else {
				processMap.put("status", 8);
				processMap.put("remark", "检查提交");
				processMap.put("receiver", "");
				processMap.put("content", "无需整改直接归档");//这里以后加上自动校验的结果
				recordMap.put("content", "隐患上报无需整改");
			}
			checkProcessDao.addProcessNew(processMap);
			if (!"".equals(eqpPhotoIds)) {
				Map photoMap = new HashMap();
				photoMap.put("TASK_ID", taskid);
				photoMap.put("OBJECT_ID", recordId);
				photoMap.put("DETAIL_ID", detail_id);
				photoMap.put("OBJECT_TYPE", 1);// 0，周期性任务，1：隐患上报工单，2,回单操作
				photoMap.put("REMARKS", "隐患上报-互联网及时检查纠错");
				photoMap.put("RECORD_ID", recordId);
				String[] photos = eqpPhotoIds.split(",");
				for (String photo : photos) {
					photoMap.put("PHOTO_ID", photo);
					checkOrderDao.insertPhotoRel(photoMap);
				}
			}
			//设备检查时间
			checkTaskDao.updateEqpCheckCompleteTime(eqp_order_map);
			//工单检查时间
			checkTaskDao.updateOrderCheckTime(eqp_order_map);
			/**
			 * 留痕记录保留
			 */			
			//checkTaskDao.saveEqpTrace(recordMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "工单端子提交失败");
		}
		return result.toString();
	}
	//工单生成错误记录自动派发给维护员维护后，维护员维护完成回单时自动校验
	//eqpId 设备ID  glbh 光路编号   portRightPosition端子正确位置
	public String compareWorkOrder(Map paramMap){
		try{
			String eqpId=paramMap.get("eqpId").toString();//设备ID指的是端子所属的直属设备
			String taskId=paramMap.get("taskId").toString();;//任务id
			String portId=paramMap.get("portId").toString();;//端子ID
			String glbh=paramMap.get("glbh").toString();;//光路编号
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
	@Override
	public String getEquCSVIOMOrder(String jsonStr){
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		JSONObject eqpJsonObject = new JSONObject();
		List <Map> LightPathPersonList = null;
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String equId = json.getString("equId");// 设备id
			String taskId = json.getString("taskId");//任务id
			String port_start_time = json.getString("port_start_time");
			String port_end_time = json.getString("port_end_time");
			String areaId = json.getString("areaId");//区域id
			
			Map param = new HashMap();	
			param.put("equId", equId);
			param.put("port_start_time", port_start_time);
			param.put("port_end_time", port_end_time);
			param.put("areaId",areaId);
			
//			param.put("equId", "25330000161483");
//			String taskId = "000";
			LightPathPersonList=checkTaskDao.getEquCSVIOMOrder(param);
			String jndi=RESUtil.getRes(areaId);
			if(LightPathPersonList!=null && LightPathPersonList.size()>0){
				LightPathPersonList= deleteSameOrderByGlbm(jndi,LightPathPersonList);	
			}
			if(LightPathPersonList.size()>0 && LightPathPersonList != null){
				for (Map light : LightPathPersonList) {
					eqpJsonObject.put("ORDER_NO", String.valueOf(light.get("ORDER_NO")).trim());//订单编号	
					String ACTION_TYPE=String.valueOf(light.get("ACTION_TYPE")).trim();
					if(null!=ACTION_TYPE && !"".equals(ACTION_TYPE) && !"null".equals(ACTION_TYPE) ){
						eqpJsonObject.put("ACTION_TYPE", ACTION_TYPE);//光路调整动作
					}else{
						eqpJsonObject.put("ACTION_TYPE", "");//光路调整动作
					}
					
					String OPT_OPER_NAME=String.valueOf(light.get("OPT_OPER_NAME")).trim();
					if(null!=OPT_OPER_NAME && !"".equals(OPT_OPER_NAME) && !"null".equals(OPT_OPER_NAME) ){
						eqpJsonObject.put("OPT_OPER_NAME", OPT_OPER_NAME);//光路调整操作人
					}else{
						eqpJsonObject.put("OPT_OPER_NAME", "");//光路调整操作人
					}
					
					String OPT_OPER_STATE=String.valueOf(light.get("OPT_OPER_STATE")).trim();
					if(null!=OPT_OPER_STATE && !"".equals(OPT_OPER_STATE) && !"null".equals(OPT_OPER_STATE)){
						eqpJsonObject.put("OPT_OPER_STATE", OPT_OPER_STATE);////操作状态
					}else{
						eqpJsonObject.put("OPT_OPER_STATE", "");////操作状态
					}

					String IOM_MF_PTY_NM=String.valueOf(light.get("CHANGE_FIBER_OPER")).trim();
					if(null!=IOM_MF_PTY_NM && !"".equals(IOM_MF_PTY_NM) && !"null".equals(IOM_MF_PTY_NM)){
						eqpJsonObject.put("IOM_MF_PTY_NM", IOM_MF_PTY_NM);//光路更纤人
					}else{
						eqpJsonObject.put("IOM_MF_PTY_NM", "");//光路更纤人
					}
					eqpJsonObject.put("eqp_no", String.valueOf(light.get("EQUIPMENT_CODE")).trim());//设备编码	
					//eqpJsonObject.put("ARCHIVE_TIME", String.valueOf(light.get("ARCHIVE_TIME")).trim());//竣工时间	
					eqpJsonObject.put("OPT_CODE", String.valueOf(light.get("OPT_CODE")).trim());//光路编码
					//eqpJsonObject.put("MODIFY_TIME", String.valueOf(light.get("MODIFY_TIME")).trim());//设备编码	
					eqpJsonObject.put("TASK_ID", taskId); //将任务id返回到页面中
					
					
					//2017年9月21日14:16:48 增加几个返回值
					String order_check_time=String.valueOf(light.get("ORDER_CHECK_TIME")).trim();
					if(order_check_time !=null && !"".equals(order_check_time)&& !"null".equals(order_check_time)){
						eqpJsonObject.put("order_check_time", order_check_time);//工单上次检查时间
					}else{
						eqpJsonObject.put("order_check_time", "");
					}
					eqpJsonObject.put("order_check_staff", "");
					String ARCHIVE_TIME=String.valueOf(light.get("ARCHIVE_TIME")).trim();
					if(ARCHIVE_TIME !=null && !"".equals(ARCHIVE_TIME)&& !"null".equals(ARCHIVE_TIME)){
						eqpJsonObject.put("ARCHIVE_TIME", ARCHIVE_TIME);//工单上次检查时间
					}else{
						eqpJsonObject.put("ARCHIVE_TIME", "");
					}
					String MODIFY_TIME=String.valueOf(light.get("MODIFY_TIME")).trim();
					if(MODIFY_TIME !=null && !"".equals(MODIFY_TIME)&& !"null".equals(MODIFY_TIME)){
						eqpJsonObject.put("MODIFY_TIME", MODIFY_TIME);
					}else{
						eqpJsonObject.put("MODIFY_TIME", "");
					}
					combineEqp(jsArr, eqpJsonObject);
//					jsArr.add(eqpJsonObject);
				}
				result.put("list", jsArr);
			}else{
				result.put("list", jsArr);
			}
		} catch (Exception e) {
			result.put("list", jsArr);
			result.put("desc", "获取网格设备所属工单失败");
			result.put("result", "003");
			logger.info(e.toString());
			e.printStackTrace();
		}
		return result.toString();
	}
	
	@Override
	public String getPortsByEqu(String jsonStr){
		return newGetPort(jsonStr);
		//2017年9月21日14:41:02 原获取设备下端子的方法
//		return oldGetPort(jsonStr);
	}

	private String newGetPort(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try {
			
			JSONObject json = JSONObject.fromObject(jsonStr);
			/**
			 * 接收的参数
			 */
			String equCode = json.getString("equCode");//光交或光分设备编码(网格下的设备列表)
			String equId = json.getString("equId");// 光交或光分设备id (网格下的设备列表)
			String areaId = json.getString("areaId");//地市
			//String orderNo=json.getString("orderNo");//订单编号	
			//变动时间段
			String port_start_time=json.getString("startTime");
			String port_end_time=json.getString("endTime");
			/*String eqp_id=json.getString("eqp_id");//订单所在的设备id*/			
			
			/*String equCode = "250QH.HHC00/GJ026";//光交或光分设备编码(网格下的设备列表)
			String equId = "25320000000538";// 光交或光分设备id (网格下的设备列表)
			String areaId ="3" ;//地市
			String orderNo= "20170704024954";//订单编号
			 */		
			
			JSONArray jsonArray=new JSONArray();
			JSONArray eqpRecords=new JSONArray();//存放设备信息
			JSONArray ports=new JSONArray();//存放端子信息
			JSONObject portObject=new JSONObject();
			JSONObject eqpObject=new JSONObject();
			JSONArray portRecords=new JSONArray();//存放设备端子数量
			JSONObject portRecord=new JSONObject();
			Map<String,String> ossMap=new HashMap<String, String>();
			Map<String,String> eqpMap=new HashMap<String, String>();
			Map ossParam =new HashMap();
			Map<String,String> ossResult =new HashMap<String,String>();
			Map param =new HashMap();
			param.put("equCode", equCode);
			param.put("equId", equId);
			param.put("areaId", areaId);
			//param.put("orderNo", orderNo);
			param.put("port_start_time", port_start_time);
			param.put("port_end_time", port_end_time);
			/*param.put("eqp_id", eqp_id);*/
			
			//通过网格下设备id和设备编码查询所有分光器
			List<Map<String, String>> obdList=checkTaskDao.getObd(param);
			if(obdList!=null&&obdList.size()>0){
				for(Map obd:obdList){
					eqpObject.put("phy_eqp_id", obd.get("EQUIPMENT_ID").toString());
					eqpObject.put("equipment_code", obd.get("EQUIPMENT_CODE").toString());
					eqpObject.put("equipment_name", obd.get("EQUIPMENT_NAME").toString());
					eqpObject.put("phy_eqp_spec_id", obd.get("RES_TYPE_ID").toString());
					eqpObject.put("portNum", "0");
					eqpRecords.add(eqpObject);
				}
			}
			//通过订单编号查询出端子所在的设备id
			List<Map> list=checkTaskDao.getPortsByEqu(param);
			//目前通过网格下的设备id和设备编码查询出设备下的所有订单编号
			//List<Map> list=checkTaskDao.getPortList(param);
			List<String> eqplist=new ArrayList<String>();
			String jndi=RESUtil.getRes(areaId);
			list= deleteSameOrderByGlbm(jndi,list);
			if(list!=null&&list.size()>0){
				for(Map port:list){
					String opt_code=port.get("OPT_CODE").toString();
					String order_id=port.get("ORDER_ID").toString();
					String orderNo=port.get("ORDER_NO").toString();
					String phy_eqp_id=port.get("PHY_EQP_ID").toString();//端子所属设备id
					String equipment_code=port.get("EQUIPMENT_CODE").toString();//端子所属设备编码
					String equipment_name=port.get("EQUIPMENT_NAME").toString();
					String phy_eqp_spec_id=port.get("PHY_EQP_SPEC_ID").toString();
					String phy_port_id=port.get("PHY_PORT_ID").toString();
					String phy_port_spec_no=port.get("PHY_PORT_SPEC_NO").toString();
					String action_type=port.get("ACTION_TYPE").toString();//工单类型 例如 17：建设单（拆），18：建设单（新装）
					String work_order_type=port.get("WORK_ORDER_TYPE").toString();// 17,18
					//String portnum=port.get("PORTNUM").toString();
					
					ossParam.put("eqpId", phy_eqp_id);
					ossParam.put("glbh", opt_code);
					ossParam.put("areaId", areaId);
					
					//如果是分光器
					if("2530".equals(phy_eqp_spec_id)){
						//先通过分光器获取箱子设备ID和设备编码，在判断通过分光器获取的箱子设备id和设备编码是否与网格下的箱子设备信息是否一致
						//一致的传给手机端展示出来
						//不一致，则将端子编码置为空，点击空的端子编码或光路编码跳转到该工单光路编码的箱子处--不同箱子的工单端子页面去检查(暂时不做)
						eqpMap=checkTaskDao.getOuterEqp(port);
						String outerEquId=eqpMap.get("INSTALL_SBID");
						String outerEquNo=eqpMap.get("INSTALL_SBBM");
						String outerEquName=eqpMap.get("EQUIPMENT_NAME");
						if(equId.equals(outerEquId)&&equCode.equals(outerEquNo)){
							/*eqpObject.put("phy_eqp_id", phy_eqp_id);
							eqpObject.put("equipment_code", equipment_code);
							eqpObject.put("equipment_name", equipment_name);
							eqpObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							if(isNotExist(eqplist,phy_eqp_id)){
								eqpRecords.add(eqpObject);
								eqplist.add(phy_eqp_id);
							}*/
							portRecord.put("phy_eqp_id", phy_eqp_id);
							portRecord.put("equipment_code", equipment_code);
							portRecord.put("portnum","1");
							portRecords.add(portRecord);
							//portRecord.put("portnum", portnum);
							/*if(isNotExist(eqplist,phy_eqp_id)){
								portRecords.add(portRecord);
								eqplist.add(phy_eqp_id);
							}*/					
							portObject.put("phy_eqp_id", phy_eqp_id);
							portObject.put("equipment_code", equipment_code);
							portObject.put("equipment_name", equipment_name);
							portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
						//	portObject.put("phy_port_id", phy_port_id);
						//	portObject.put("phy_port_spec_no", phy_port_spec_no);
							portObject.put("action_type", action_type);
							portObject.put("opt_code", opt_code);
							portObject.put("order_id", order_id);
							portObject.put("orderNo", orderNo);
							//如果是拆机的，则不展示实时端口
							String ossPortNo="";
							String ossPortId="";
							if("17".equals(work_order_type)){
								ossPortId=phy_port_id;
								ossPortNo=phy_port_spec_no;
							}else{
								//调用oss查询实时端口
								ossResult=getOssPort(ossParam);
								String oss_result=ossResult.get("result");								
								if("000".equals(oss_result)){
									ossPortId=ossResult.get("ossPortId");
									ossPortNo=ossResult.get("ossPortNo");
								}else{
									ossPortId="空闲";
									ossPortNo="空闲";
								}
							}							
							portObject.put("phy_port_id", ossPortId);
							portObject.put("phy_port_spec_no", ossPortNo);
							ports.add(portObject);
						}/*else{//如果通过分光器查询出来的箱子与网格下的箱子不一致，就不把分光器的记录放入网格下的设备
							portObject.put("phy_eqp_id", phy_eqp_id);
							portObject.put("equipment_code", equipment_code);
							portObject.put("equipment_name", equipment_name);
							portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							portObject.put("phy_port_id", "");
							portObject.put("phy_port_spec_no", "");
							portObject.put("action_type", action_type);
							portObject.put("opt_code", opt_code);
							portObject.put("order_id", order_id);
							portObject.put("orderNo", orderNo);
							ports.add(portObject);
						}*/
					}else{
						//如果是箱子,先判断通过工单查询出来的设备id和设备编码是否与网格下的设备信息是否一致，
						//如果一致，展示，其实并没有一致的情况，做一下判断更好一点
						if(equId.equals(phy_eqp_id)&&equCode.equals(equipment_code)){
							/*eqpObject.put("phy_eqp_id", phy_eqp_id);
							eqpObject.put("equipment_code", equipment_code);
							eqpObject.put("equipment_name", equipment_name);
							eqpObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							eqpRecords.add(eqpObject);	*/
							portRecord.put("phy_eqp_id", phy_eqp_id);
							portRecord.put("equipment_code", equipment_code);
							portRecord.put("portnum", "1");
							portRecords.add(portRecord);
							/*if(isNotExist(eqplist,phy_eqp_id)){
								portRecords.add(portRecord);
								eqplist.add(phy_eqp_id);
							}*/
							portObject.put("phy_eqp_id", phy_eqp_id);
							portObject.put("equipment_code", equipment_code);
							portObject.put("equipment_name", equipment_name);
							portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
							portObject.put("phy_port_id", phy_port_id);
							portObject.put("phy_port_spec_no", phy_port_spec_no);
							portObject.put("action_type", action_type);
							portObject.put("opt_code", opt_code);
							portObject.put("order_id", order_id);
							portObject.put("orderNo", orderNo);
							
							//如果是拆机的，则不展示实时端口
							String ossPortNo="";
							String ossPortId="";
							if("17".equals(work_order_type)){
								ossPortId=phy_port_id;
								ossPortNo=phy_port_spec_no;
							}else{
								//调用oss查询实时端口
								ossResult=getOssPort(ossParam);
								String oss_result=ossResult.get("result");								
								if("000".equals(oss_result)){
									ossPortId=ossResult.get("ossPortId");
									ossPortNo=ossResult.get("ossPortNo");
								}else{
									ossPortId="空闲";
									ossPortNo="空闲";
								}
							}							
							portObject.put("phy_port_id", ossPortId);
							portObject.put("phy_port_spec_no", ossPortNo);
							ports.add(portObject);
						}
					}					
				}
				//通过eqpRecords和portRecords进行对比，判断出工单设备的端子量
				JSONObject port1=new JSONObject();
				JSONObject eqp1=new JSONObject();
				String eqpId="";
				String eqpNo="";
				String port_eqpId="";
				String port_eqpNo="";
				//String portNum="";
				int portNum=0;
				for(int i=0;i<eqpRecords.size();i++){
					int num=0;
					eqp1=eqpRecords.getJSONObject(i);
					eqpId=eqp1.getString("phy_eqp_id");
					eqpNo=eqp1.getString("equipment_code");
					num=Integer.parseInt(eqp1.getString("portNum"));
					for(int j=0;j<portRecords.size();j++){
						port1=portRecords.getJSONObject(j);
						port_eqpId=port1.getString("phy_eqp_id");
						port_eqpNo=port1.getString("equipment_code");
						//portNum=port1.getString("portnum");
						portNum=Integer.parseInt(port1.getString("portnum"));
						if(eqpId.equals(port_eqpId)&&eqpNo.equals(port_eqpNo)){
							num=num+portNum;
							eqp1.put("portNum",num);
							//eqp1.put("portNum", portNum);
							//break;
						}
					}
				}
				//对eqpRecords进行排序，设备中工单不为0的放在最上面
				JSONObject c1=new JSONObject();
				JSONObject c2=new JSONObject();
				Integer c1_num=0;
				Integer c2_num=0;
				for(int i=0;i<eqpRecords.size()-1;i++){
					for(int j=i+1;j<eqpRecords.size();j++){
						c1=eqpRecords.getJSONObject(i);
						c1_num=Integer.valueOf(c1.getString("portNum"));
						c2=eqpRecords.getJSONObject(j);
						c2_num=Integer.valueOf(c2.getString("portNum"));
						if(c1_num<c2_num){
							eqpRecords.set(i, c2);
							eqpRecords.set(j, c1);
						}
					}
				}
				result.put("eqpRecords", eqpRecords);
				result.put("ports", ports);
			}else{
				result.put("eqpRecords", "");
				result.put("ports", "");
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "查询工单端子信息失败");
			logger.info(e.toString());
		}
		return result.toString();
	}

	private String oldGetPort(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		JSONObject portJsonObject = new JSONObject();
		JSONArray eqpRecords=new JSONArray();//存放设备信息
		List <Map> portsList = null;
		result.put("result", "000");
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String equId = json.getString("equId");// 设备id
			//String equId = "25330000161483";
			Map param = new HashMap();	
			param.put("equId", equId);
			portsList=checkTaskDao.getPortsByEqu(param);
			
			JSONArray ports=new JSONArray();//存放端子信息
			JSONObject portObject=new JSONObject();
			JSONObject eqpObject=new JSONObject();
			Map<String,String> eqpMap=null;
			
			List list = new ArrayList();
			if(portsList.size()>0 && portsList != null){
				for(Map port:portsList){
					String opt_code=port.get("OPT_CODE").toString();
					String order_id=port.get("ORDER_ID").toString();
					String orderNo=port.get("ORDER_NO").toString();
					String phy_eqp_id=port.get("PHY_EQP_ID").toString();//端子所属设备id
					String equipment_code=port.get("EQUIPMENT_CODE").toString();//端子所属设备编码
					String equipment_name=port.get("EQUIPMENT_NAME").toString();
					String phy_eqp_spec_id=port.get("PHY_EQP_SPEC_ID").toString();
					String phy_port_id=port.get("PHY_PORT_ID").toString();
					String phy_port_spec_no=port.get("PHY_PORT_SPEC_NO").toString();
					String action_type= "";
					if(port.containsKey("ACTION_TYPE")){
						action_type = port.get("ACTION_TYPE").toString();
					}
					
					eqpObject.put("phy_eqp_id", phy_eqp_id);
					eqpObject.put("equipment_code", equipment_code);
					eqpObject.put("equipment_name", equipment_name);
					eqpObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
					
					if(isNotExist(list,phy_eqp_id)){
						eqpRecords.add(eqpObject);
						list.add(phy_eqp_id);
					}
					
					portObject.put("phy_eqp_id", phy_eqp_id);
					portObject.put("equipment_code", equipment_code);
					portObject.put("equipment_name", equipment_name);
					portObject.put("phy_eqp_spec_id", phy_eqp_spec_id);
					portObject.put("phy_port_id", phy_port_id);
					portObject.put("phy_port_spec_no", phy_port_spec_no);
					portObject.put("action_type", action_type);
					portObject.put("opt_code", opt_code);
					portObject.put("order_id", order_id);
					portObject.put("orderNo", orderNo);
					ports.add(portObject);
				}
				result.put("eqpRecords", eqpRecords);
				result.put("ports", ports);
			}else{
				result.put("eqpRecords", eqpRecords);
				result.put("ports", ports);
			}
		} catch (Exception e) {
			result.put("desc", "获取设备下端子信息失败");
			result.put("result", "003");
			logger.info(e.toString());
			e.printStackTrace();
		}
		return result.toString();
	}
	
	//判断list集合中是否存在字符串
	public Boolean isNotExist(List list,String id){
		Boolean bool = true;
		if(list.contains(id)){
			bool = false;
		}
		return bool;
	}
	
	
	@Override
	public String getEqpBySG(String jsonStr){
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		JSONObject portJsonObject = new JSONObject();
		List <Map> eqpList = null;
		result.put("result", "000");
		if(StringUtils.isBlank(jsonStr)){
			result.put("desc", "参数为空");
			result.put("result", "004");
			return result.toString();
		}
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String gridId = json.getString("gridId");
			String staffId = json.getString("staffId");
			
			String eqpType = json.getString("eqpType");
			String eqpCode = json.getString("eqpCode");
			String eqpAddress = json.getString("eqpAddress");//设备地址
			String port_start_time = json.getString("port_start_time");
			String port_end_time = json.getString("port_end_time");
			String contract_person = json.getString("contract_person");//是否我的承包0是 1不是
			
			String latitude = json.getString("latitude");//31.98717
			String longitude = json.getString("longitude");//118.74848

			if(StringUtils.isBlank(longitude) || StringUtils.isBlank(latitude)){
				latitude = "0.0";
				longitude = "0.0";
			}
			
			Map param = new HashMap();	
			
			param.put("gridId", gridId);
			param.put("staffId",staffId);
			param.put("latitude", latitude);
			param.put("longitude", longitude);
			
			//2017年9月21日10:52:49 新增几个入参
			param.put("eqpType", eqpType);
			param.put("eqpCode",eqpCode);
			param.put("eqpAddress", eqpAddress);
			param.put("port_start_time", port_start_time);
			param.put("port_end_time", port_end_time);
			param.put("contract_person", contract_person);
			
			int allOrderNum = 0;
			
			
			
			String select_mark = json.getString("mark");//0:初始  1:全部
			param.put("gard_id", gridId);
			int currentPage = Integer.valueOf(json.getString("page"));
			int pageSize = Integer.valueOf(json.getString("size"));
			setPage(currentPage,pageSize,param);
			
			String allEqpNum = "";
			
			if("1".equals(select_mark)){
				eqpList=checkTaskDao.getGridQumAllTask(param);
				allEqpNum = checkTaskDao.getAllEqpNum(param);
			}else{
				eqpList=checkTaskDao.getEqpBySG(param);	
				allEqpNum = eqpList.size()+"";
			}
			
			if(eqpList.size()>0 && eqpList != null){
				for (Map ports : eqpList) {
					
					portJsonObject.put("plan_end_time",isNull(ports.get("PLAN_END_TIME"))?"":ports.get("PLAN_END_TIME"));
					portJsonObject.put("plan_circle",isNull(ports.get("PLAN_CIRCLE"))?"":ports.get("PLAN_CIRCLE"));
					
					portJsonObject.put("eqpId", isNull(ports.get("EQPID"))?"":ports.get("EQPID").toString());
					String son_areaId = ports.get("SON_AREAID")==null?"":ports.get("SON_AREAID").toString();
					String sbbm = isNull(ports.get("EQPNO"))?"":ports.get("EQPNO").toString();
					portJsonObject.put("eqpNo", sbbm);
					portJsonObject.put("son_areaId", son_areaId);
					portJsonObject.put("eqp_type_id", ports.get("EQP_TYPE_ID")==null?"":ports.get("EQP_TYPE_ID").toString());
					portJsonObject.put("eqpName", ports.get("EQPNAME")==null?"":ports.get("EQPNAME").toString());
					portJsonObject.put("eqp_type", ports.get("EQP_TYPE")==null?"":ports.get("EQP_TYPE").toString());
					portJsonObject.put("address", ports.get("ADDRESS")==null?"":ports.get("ADDRESS").toString());
					
					portJsonObject.put("last_check_time", String.valueOf(ports.get("LAST_UPDATE_TIME")));
					portJsonObject.put("start_time", String.valueOf(ports.get("START_TIME")));
					portJsonObject.put("taskId", String.valueOf(ports.get("TASKID")));
					portJsonObject.put("taskName", String.valueOf(ports.get("TASKNAME")).trim());
					portJsonObject.put("taskState", String.valueOf(ports.get("TASKSTATE")).trim());
					if("0".equals(String.valueOf(ports.get("TASKTYPE")))|| "4".equals(String.valueOf(ports.get("TASKTYPE")))||"5".equals(String.valueOf(ports.get("TASKTYPE")))){
						portJsonObject.put("taskType","2");
					}else if("1".equals(String.valueOf(ports.get("TASKTYPE"))) || "2".equals(String.valueOf(ports.get("TASKTYPE"))) || "3".equals(String.valueOf(ports.get("TASKTYPE")))){
						portJsonObject.put("taskType","3");
					}else{
						portJsonObject.put("taskType","2");
					}
					if("10".equals(String.valueOf(ports.get("TASKTYPE")))||"11".equals(String.valueOf(ports.get("TASKTYPE")))||"12".equals(String.valueOf(ports.get("TASKTYPE")))||"13".equals(String.valueOf(ports.get("TASKTYPE")))){
						portJsonObject.put("data_source","0");
					}else{
						portJsonObject.put("data_source",String.valueOf(ports.get("DATA_SOURCE")));
					}
					portJsonObject.put("no_eqpNo_flag",(ports.get("NO_EQPNO_FLAG")==null ||"".equals(ports.get("NO_EQPNO_FLAG")))?"0":ports.get("NO_EQPNO_FLAG").toString());
					portJsonObject.put("end_time", String.valueOf(ports.get("END_TIME")).trim());
					
//					String s1 = calDistance(portJsonObject, latitude, longitude, ports);
					
					if(ports.containsKey("DISTANCE")){
						String s1 = String.valueOf(ports.get("DISTANCE")).trim();
						if(StringUtils.isNotBlank(s1)){
							Double ss = Double.valueOf(s1).doubleValue();
							portJsonObject.put("distance", isLongDistance(ss));
						}else{
							portJsonObject.put("distance", "0米");
						}
					}else{
						portJsonObject.put("distance",  "0米");
					}
					
					
					
					//2017年9月21日10:52:30 新增三个返回值
					
					
					//2017年9月26日15:00:58 根据工单id，for循环查询工单的数量，暂不明确效率如何？？？
//					Map map = new HashMap();
//					map.put("equId", ports.get("EQPID")==null?"0":ports.get("EQPID").toString());
//					map.put("port_start_time", port_start_time);
//					map.put("port_end_time", port_end_time);
//					Integer ordernum = checkTaskDao.getCountByEquId(map);
//					allOrderNum=allOrderNum+ordernum;
//					portJsonObject.put("ordernum",ordernum);
					
					String ordernum=String.valueOf(ports.get("ORDERNUM"));//该设备下的工单数量
					if(!"".equals(ordernum) || null !=ordernum || !"null".equals(ordernum)){
						allOrderNum=allOrderNum+Integer.valueOf(ordernum);
						portJsonObject.put("ordernum",ordernum );
					}else{
						portJsonObject.put("ordernum","" );
					}
					
					String check_complete_time=String.valueOf(ports.get("CHECK_COMPLETE_TIME"));//该设备上次检查时间
					if(!"".equals(check_complete_time) && null !=check_complete_time && !"null".equals(check_complete_time)){
						portJsonObject.put("check_complete_time",check_complete_time );
					}else{
						portJsonObject.put("check_complete_time","" );
					}
					jsArr.add(portJsonObject);
				}
				result.put("list", jsArr);
				result.put("allOrderNum", allOrderNum);
				result.put("allEqpNum", allEqpNum);
			}else{
				result.put("list", jsArr);
				result.put("allOrderNum", allOrderNum);
				result.put("allEqpNum", allEqpNum);
			}
		} catch (Exception e) {
			result.put("list", jsArr);
			result.put("desc", "获取工单下设备失败");
			result.put("result", "003");
			logger.info(e.toString());
			e.printStackTrace();
		}
		return result.toString();
	}
	
	//根据当前页和页码数，计算起始和结束
	public void setPage(int currentPage,int pageSize,Map map){
		map.put("startLine", ((currentPage-1)*pageSize+1));
		map.put("endLine", (currentPage*pageSize));
	}
	
	public Boolean isNull(Object str){
		if(str==null || "null".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 計算距离
	 * @param portJsonObject
	 * @param latitude
	 * @param longitude
	 * @param ports
	 * @return
	 */
	private String calDistance(JSONObject portJsonObject, String latitude, String longitude, Map ports) {
		double lant1 = 0;
		double long1 = 0;
		String lon = String.valueOf(ports.get("LONGITUDE")).trim();
		String lat = String.valueOf(ports.get("LATITUDE")).trim();
		String lon_inspect=String.valueOf(ports.get("LONGITUDE_INSPECT")).trim();
		String lat_inspect=String.valueOf(ports.get("LATITUDE_INSPECT")).trim();
		if(lon_inspect!=null &&!"".equals(lon_inspect)&&!"null".equals(lon_inspect)){
			long1 = Double.parseDouble(lon_inspect);
			lant1 = Double.parseDouble(lat_inspect);
			portJsonObject.put("longitude",lon_inspect);
			portJsonObject.put("latitude",lat_inspect);
		}else{
			if(lon!=null &&!"".equals(lon)&&!"null".equals(lon)){
				long1 = Double.parseDouble(lon);
			}
			if(lat!=null &&!"".equals(lat)&&!"null".equals(lat)){
				lant1 = Double.parseDouble(lat);
			}
			portJsonObject.put("longitude",lon);
			portJsonObject.put("latitude",lat);
		}
		double lat2 = 0;
		double lng2 = 0;
		if(latitude!=null && !"".equals(latitude)){
			lat2 = Double.parseDouble((latitude));
		}
		if(longitude!=null&&!"".equals(longitude)){
			lng2 = Double.parseDouble((longitude));
		}
		double s1 = 0;
		if(lant1 == 0 || long1 == 0 || lat2 == 0 || lng2 == 0){
			
		}else{
			s1 = MapDistance.getDistance(lant1, long1, lat2, lng2);
		}
		
		String dw = "米";
		if(s1/1000 >= 1){
			dw = "千米";
			s1 = s1 /1000;
		}
		DecimalFormat df = new DecimalFormat("######0.0");   
		return df.format(s1)+dw;
	}
	
	/**
	 * 修改距离的单位
	 * @param s1
	 * @return
	 */
	public String isLongDistance(Double s1){
		String dw = "米";
		if(s1/1000 >= 1){
			dw = "千米";
			s1 = s1 /1000;
			DecimalFormat df = new DecimalFormat("######0.0"); 
			return df.format(s1)+dw;
		}else{
			return s1+dw;
		}
	}

	@Override
	public String getProcessRecord(String jsonStr) {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		result.put("result", "000");
		JSONArray recordArray=new JSONArray();
		JSONObject recordObject=new JSONObject();
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			//String eqpId = json.getString("eqpId");//eqpId   设备ID
			String orderNo = json.getString("orderNo");//orderNo 工单编号
			Map param=new HashMap();
			//param.put("eqpId", eqpId);
			param.put("orderNo", orderNo);
			//通过设备ID和工单编号找出记录
			List<Map<String, String>> recordList=checkTaskDao.getOrderRecords(param);
			if(recordList !=null && recordList.size()>0){
				String oper_staff="";
				String oper_time="";
				String remark="";
				for(Map<String,String> record:recordList){
					oper_staff=record.get("STAFF_NAME");
					oper_time=record.get("OPER_TIME");
					remark=record.get("REMARK");
					recordObject.put("oper_staff", oper_staff);
					recordObject.put("oper_time", oper_time);
					recordObject.put("remark", remark);
					recordArray.add(recordObject);
				}
				result.put("recordArray", recordArray);
			}else{
				result.put("recordArray", "[]");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "查询流程记录失败！");
			// TODO: handle exception
		}
		return result.toString();
	}

	@Override
	public String getEqpDetail(String jsonStr) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject eqpJsonObject = new JSONObject();
		JSONArray jsArr = new JSONArray();
		try {
			String eqpId = json.getString("eqpId");
			String eqpNo = json.getString("eqpNo");
			String areaId = json.getString("areaId");
			String eqpType = json.getString("eqpType");
			Map params = new HashMap();
			params.put("eqpId", eqpId);
			params.put("eqpNo", eqpNo);
			params.put("areaId", areaId);
			String juZhan="";//局站
			if("2530".equals(eqpType)){//如果是分光器，则找到箱子设备
				Map outerMap=checkTaskDao.getEqpOut(params);
				String outerEqpNo=outerMap.get("INSTALL_SBBM").toString();
				String[] eqp=outerEqpNo.split("/");
				juZhan=eqp[0];
			}else{
				String[] eqp=eqpNo.split("/");
				juZhan=eqp[0];
			}
			//通过设备ID和地市ID查询设备属性
		
			Map map=checkTaskDao.getEqpDetail(params);
			if(map !=null && map.size()>0){
				String phy_all_cpy=map.get("PHY_ALL_CPY")==null?"":map.get("PHY_ALL_CPY").toString();
				String phy_free_cpy=map.get("PHY_FREE_CPY").toString();
				String phy_used_cpy=map.get("PHY_USED_CPY").toString();
				result.put("phy_all_cpy", phy_all_cpy);
				result.put("phy_free_cpy", phy_free_cpy);
				result.put("phy_used_cpy", phy_used_cpy);
				result.put("juZhan", juZhan);
			}else{
				result.put("phy_all_cpy", "");
				result.put("phy_free_cpy", "");
				result.put("phy_used_cpy", "");
				result.put("juZhan", juZhan);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("desc", "设备详情查询失败！");
		}
		return result.toString();
	}

	
	public static void main(String[] args) {
//		String cmd="sqlplus ins/ayw_ins1@132.252.7.61:1621/ossmob_ayw @C:\\Users\\Zhufc\\Desktop\\port\\spool2.sql";   
//		Process p = null;
//		BufferedReader in = null;
//		try {
//			
//			p = Runtime.getRuntime().exec(cmd);
//			
//		} catch (IOException e1) {
//			e1.printStackTrace();
//			
//		}
		
//		String str = "[{\"remark\":\"111\",\"photoId\":\"222\",\"isOk\":\"1\",\"name\":\"综调111（触发率）\",\"recordInput\":\"10\"},"
//				+ "{\"remark\":\"\",\"photoId\":\"\",\"isOk\":\"1\",\"name\":\"综调工单触发情况（触发率）\",\"recordInput\":\"10\"}]";
//		
//		if(StringUtils.isNotBlank(str)){
//        	JSONArray json = JSONArray.fromObject(str);
//        	Map<String,String> map = new HashMap<String,String>();
//        	map.put("name", "");
//    	    map.put("photo", "");
//    	    map.put("description", "");
//    	    map.put("is_ok", "");
//    	    map.put("score", "");
//        	for(int i=0;i<json.size();i++){
//        	    JSONObject job = json.getJSONObject(i);
//        	    map.put("name", job.get("name")==null?"":job.get("name").toString());
//        	    map.put("photo", job.get("photoId")==null?"":job.get("photoId").toString());
//        	    map.put("description", job.get("remark")==null?"":job.get("remark").toString());
//        	    map.put("is_ok", job.get("isOk")==null?"":job.get("isOk").toString());
//        	    map.put("score", job.get("recordInput")==null?"":job.get("recordInput").toString());
//        	}
//        	
//        }
	}	
	
	public Boolean getGlInfoByGlbm(String jndi,String glbm){
		//首先通过设备id和光路编号从OSS中取出光路编号对应的端子id(ossPort)
		Boolean flag=false;
		Map ossParam=new HashMap();
		ossParam.put("jndi", jndi);
		ossParam.put("glbm", glbm);
		String ossResult="";
		SwitchDataSourceUtil.setCurrentDataSource(jndi);
		ossResult= checkTaskDao.getOssInfoByGlbm(ossParam);//通过设备ID和光路编号到OSS实时查询光路占用端口		
		SwitchDataSourceUtil.clearDataSource();
		if(ossResult!=null && !"".equals(ossResult)){
			flag=true;
		}
		return flag;
	}
	public List<Map> deleteSameOrderByGlbm(String jndi,List<Map> LightPathPersonList){
		String glbm ="";
		String glbm2 ="";
		String work_order_type="";
		String work_order_type2="";
		Map glMap=new HashMap();
		Map glMap2=new HashMap();
		Boolean flag=false;
		if(LightPathPersonList!=null){
			for(int i=0;i<LightPathPersonList.size();i++){
				glMap=LightPathPersonList.get(i);
				glbm=glMap.get("OPT_CODE").toString();
				work_order_type=glMap.get("WORK_ORDER_TYPE")==null?"":glMap.get("WORK_ORDER_TYPE").toString();
				for(int j=i+1;j<LightPathPersonList.size();j++){
					glMap2=LightPathPersonList.get(j);
					glbm2=glMap2.get("OPT_CODE").toString();
					work_order_type2=glMap2.get("WORK_ORDER_TYPE")==null?"":glMap2.get("WORK_ORDER_TYPE").toString();
					if(glbm.equals(glbm2)){					
						flag=getGlInfoByGlbm(jndi,glbm);//判断光路编码在OSS系统中是否存在，如果存在，则为true,不存在，则为false
						if(flag){//如果为true,则说明光路存在OSS系统，则将新装的保存，拆的不显示
							if("17".equals(work_order_type)){
								LightPathPersonList.remove(i);
								i--;
								break;
							}
							if("17".equals(work_order_type2)){
								LightPathPersonList.remove(j);
								j--;
							}
							
						}
					}
				}
			}
		}
		return LightPathPersonList;
	}
}


class CalOrderDetail extends Thread{
	
	private String orderNo;
	private String taskId;
	private String orderDetail;
	private CheckTaskDao checkTaskDao;
	public CalOrderDetail() {}
	public CalOrderDetail(String orderNo,String taskId,String orderDetail,CheckTaskDao checkTaskDao) {
		this.orderNo = orderNo;
		this.taskId = taskId;
		this.orderDetail = orderDetail;
		this.checkTaskDao = checkTaskDao;
	}
	
	@Override
	public void run(){
        if(StringUtils.isNotBlank(orderDetail)){
        	JSONArray json = JSONArray.fromObject(orderDetail);
        	Map<String,String> map = new HashMap<String,String>();
        	map.put("orderNo", orderNo);
        	map.put("taskId", taskId);
        	map.put("name", "");
    	    map.put("photo", "");
    	    map.put("description", "");
    	    map.put("is_ok", "");
    	    map.put("score", "");
    	    
    	    Map<String,String> map_put_to_jyh = new HashMap<String,String>();
    	    map_put_to_jyh.put("orderNo", orderNo);
        	for(int i=0;i<json.size();i++){
        	    JSONObject job = json.getJSONObject(i);
        	    map.put("name", job.get("name")==null?"":job.get("name").toString());
        	    map.put("photo", job.get("photoId")==null?"":job.get("photoId").toString());
        	    map.put("description", job.get("remark")==null?"":job.get("remark").toString());
        	    map.put("is_ok", job.get("isOk")==null?"":job.get("isOk").toString());
        	    map.put("score", job.get("recordInput")==null?"0":job.get("recordInput").toString());
        	    
        	    //解析Detail的json值，插入到detail表中
            	checkTaskDao.updateTaskDetail(map);
        	    
            	if(i==0){
            		if("1".equals(map.get("is_ok").toString())){
            			map_put_to_jyh.put("PROCESS_CONSTRUCTION", "不规范"+map.get("description").toString());	
            		}else{
            			map_put_to_jyh.put("PROCESS_CONSTRUCTION", "规范");	
            		}
            	}else if (i==1){
            		if("1".equals(map.get("is_ok").toString())){
            			map_put_to_jyh.put("CHECK_RIGHT_RATE", "存在问题未发现");	
            		}else{
            			map_put_to_jyh.put("CHECK_RIGHT_RATE", "审核发现存在的问题");	
            		}
            	}
        	}
        	
        	
        	//将施工质量和审核准确性推送给集约化
        	checkTaskDao.updateOrder(map_put_to_jyh);
        	
        	
        }else{
        	System.out.println(orderNo+ " 的工单，detail详细信息为空！！！");
        }
    }
	
}