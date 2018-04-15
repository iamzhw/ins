package com.webservice.portInfo.serviceimpl;

import icom.cableCheck.serviceimpl.CheckPortServiceImpl;
import icom.util.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.webservice.portInfo.dao.GetPortInfosDao;
import com.webservice.portInfo.service.GetPortInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.dataSource.SwitchDataSourceUtil;

@WebService(serviceName = "GetPortInfoService")
public class GetPortInfosImpl implements GetPortInfoService{

	Logger logger = Logger.getLogger(CheckPortServiceImpl.class);
	@Resource
	private GetPortInfosDao getPortInfoDao;
	
	/**
	 * @param args
	 * 实时查询端子信息
	 */
	
	@SuppressWarnings("all")
	public String getCheckPort(String jsonStr) {
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
			String area_id = getPortInfoDao.getAreaId(areaName);		
			String jndi = getPortInfoDao.getDBblinkName(area_id);
			if (null == jndi || "".equals(jndi)) {
				return Result.returnCode("001");
			}
			
			HashMap param = new HashMap();	
			param.put("eqpNo", eqpNo);
			param.put("eqpId", eqpId);
			param.put("areaId", area_id);
			param.put("jndi", jndi);
			
			/**
			 * 查询分光器信息
			 */
			List<Map> obdsList = getPortInfoDao.getObdsByEqpId(param);
			JSONArray obdArray = new JSONArray();
			JSONArray ports = new JSONArray();
			JSONArray portsinfo = new JSONArray();
			portsinfo=this.getPort(param, jndi);
			result.put("ports", portsinfo);
			HashMap portparam = new HashMap();
			if(obdsList.size()>0 && obdsList != null){
				for (Map obds : obdsList) {
					JSONObject obdObject = new JSONObject();		
					String obdNo = obds.get("SBBM").toString();
					String obdId = obds.get("SBID").toString();
					obdObject.put("eqpNo", obdNo);
					portparam.put("eqpId", obdId);
					portparam.put("eqpNo", obdNo);
					portparam.put("jndi", jndi);
					ports=this.getPort(portparam, jndi);
					obdObject.put("ports", ports);
					obdArray.add(obdObject);
				}
				//按变动端子数排序
				
				result.put("obds", obdArray);
			}else{
				result.put("obds", "[]");
			}

			
		} catch (Exception e) {
			result.put("desc", "查询端子失败");
			logger.info(e.toString());
			SwitchDataSourceUtil.clearDataSource();
		}
		return result.toString();
	}
	
	@SuppressWarnings("all")
	public JSONArray getPort(HashMap param,String jndi) {
		

		JSONArray jsArr = new JSONArray();
		
		JSONObject portObject = new JSONObject();
		
		HashMap portMap = new HashMap();
		
			/**
			 * TODO 获取全部端子（非任务）
			 */
			
				//从OSS系统查实时列表
				List<Map> lightList = null;
				try{
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				lightList = getPortInfoDao.getGlList(param);
				SwitchDataSourceUtil.clearDataSource();
				}catch (Exception e) {
					e.printStackTrace();
				}
				finally
				{
					SwitchDataSourceUtil.clearDataSource();
				}
				String glbh = null;
				if(lightList.size()>0 && lightList != null){
					for (Map light : lightList) {
						    portObject.put("eqpId", String.valueOf(light.get("SBID")).trim());
							portObject.put("eqpNo", String.valueOf(light.get("SBBM")).trim());
							portObject.put("portNo", String.valueOf(light.get("DZBM")).trim());
							glbh=null==light.get("GLBH")?"":light.get("GLBH").toString();
							portObject.put("ossglbh",glbh);
							portObject.put("is_free_status","".equals(glbh)?"1":"0");
							portObject.put("xz_id", null==light.get("XZ_ID")?"":light.get("XZ_ID").toString());
							portObject.put("xz_name", null==light.get("XZ_NAME")?"":light.get("XZ_NAME").toString());
							jsArr.add(portObject);
						}
					
				}
			
		 return jsArr;
	}
	

}
