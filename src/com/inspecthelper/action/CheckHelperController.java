package com.inspecthelper.action;

import icom.util.BaseServletTool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inspecthelper.dao.InsOrderTaskDao;
import com.inspecthelper.dao.InspectHelperDao;
import com.inspecthelper.service.ICheckHelperService;
import com.inspecthelper.service.IInspectService;
import com.inspecthelper.service.impl.InspectHelperServiceImpl;



import util.page.BaseAction;

@RequestMapping(value="/mobile/check-helper")
@SuppressWarnings("all")
@Controller
public class CheckHelperController extends BaseAction{
	
	private static final String ODF_A = "14";// ODF1级检查人员角色ID
	private static final String ODF_B = "15";
	private static final String ODF_C = "16";
	private static final String ODF_D = "17";
	
	@Resource
	private IInspectService inspectServiceImpl;
	
	@Resource
	private InspectHelperServiceImpl inspectHelperServiceImpl;
	@Resource
	private InspectHelperDao inspectHelperDao;
	
	@Resource
	ICheckHelperService checkHelperServiceImpl;
	
	@RequestMapping(value = "/setOk.do")
	public void setOk(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			//jsonStr="{'sn':'862199029026496','staffId':'2622','portIds':'12300239300444,','staffNo':'xj110','areaId':'3'}";
			JSONObject json = JSONObject.fromObject(jsonStr);
			String staffId = json.getString("staffId");
			String staffNo = json.getString("staffNo");
			String areaId = json.getString("areaId");
			String sn = json.getString("sn");
			//String portIds = json.getString("portIds");

			Map param = new HashMap();
			String role = inspectServiceImpl.getODFStaffRole(staffNo);
			if (role.equals(ODF_A)) {
				param.put("state_a", "1");
			} else if (role.equals(ODF_B)) {
				param.put("state_b", "1");
			} else if (role.equals(ODF_C)) {
				param.put("state_c", "1");
			} else if (role.equals(ODF_D)) {
				param.put("state_d", "1");
			}
			String trueIds = json.getString("portIds");
			if(trueIds.lastIndexOf(",")==(trueIds.length()-1)){
			//	System.out.print("haha");
				trueIds=trueIds.substring(0, trueIds.lastIndexOf(","));
			}
			param.put("trueIds", trueIds);
			param.put("staffId", staffId);
			inspectServiceImpl.updateDinamicChange(param);
			JSONObject reJson = new JSONObject();
			reJson.put("result", "000");
			BaseServletTool.sendParam(response, reJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject reJson = new JSONObject();
			reJson.put("result", "001");
			BaseServletTool.sendParam(response, reJson.toString());
		}
	}
	
	/*
	 * ODF巡检
	 */
	@RequestMapping(value = "/commitDescription.do")
	public void commitDescription(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			//jsonStr="{'id':'12300237881424','sn':'862199029026496','staffId':'2622','staffNo':'xj110','description':'测试光路编号为F1501100864，无标签','equipmentId':'12310000009306','descriptionType':'FTTH','terminal_type':'PHONE','type':'0','areaId':'3'}";
			JSONObject json = JSONObject.fromObject(jsonStr);
			String staffId = json.getString("staffId");
			String staffNo = json.getString("staffNo");
			String areaId = json.getString("areaId");
			String sn = json.getString("sn");
			/* 区别开手机和PAD变更 */
			String terminalType = "PAD";
			if (json.containsKey("terminal_type")) {
				terminalType = json.getString("terminal_type");
			}
			/* 区别开手机和PAD变更 */
			String id = json.getString("id");
			Map<String,Object> map1=new HashMap<String,Object>();
			map1.put("seqName", "ORDER_ID");
			String orderId = inspectHelperDao.getNextSeqVal(map1);
			String equipmentId = json.getString("equipmentId");
			String targetId = "0035";
			String ftth = json.getString("descriptionType");
			String trueIds = id;
			String troubleCode = orderId + "," + equipmentId + "," + targetId;
			ftth = URLDecoder.decode(ftth, "UTF-8");
			String remarks = json.getString("description");
			remarks = URLDecoder.decode(remarks, "UTF-8");
			String type = json.getString("type");
			if (ftth != null) {
				inspectServiceImpl.saveResTrou(orderId, equipmentId, targetId,
						type, remarks, staffId, ftth, id);
			} else {
				inspectServiceImpl.saveResTrou(orderId, equipmentId, targetId,
						type, remarks, staffNo);
			}

			/* 将此条动态纤芯记录置为错误 */
			Map param = new HashMap();
			String role = inspectServiceImpl.getODFStaffRole(staffNo);
			if (role.equals(ODF_A)) {
				param.put("state_a", "2");
			} else if (role.equals(ODF_B)) {
				param.put("state_b", "2");
			} else if (role.equals(ODF_C)) {
				param.put("state_c", "2");
			} else if (role.equals(ODF_D)) {
				param.put("state_d", "2");
			}
			param.put("trueIds", trueIds);
			param.put("staffId", staffId);
			inspectServiceImpl.updateDinamicChange(param);
			// odf or not odf check
			Map param1 = new HashMap();
			param1.put("orderId", orderId);
			param1.put("equipmentId", equipmentId);
			param1.put("staffNo", staffNo);
			/* 区别开手机和PAD变更 */
			// param1.put("sn", "PAD");
			param1.put("sn", terminalType);
			/* 区别开手机和PAD变更 */
			param1.put("ckDate", new Date());
			param1.put("odfCheck", 1);
			int k=inspectHelperDao.checkResCount(param1);
			if (!(k>0)) {
				inspectHelperDao.createTaskOrderCheck(param1);
			}
			// return
			JSONObject reJson = new JSONObject();
			reJson.put("result", "000");
			BaseServletTool.sendParam(response, reJson.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			JSONObject reJson = new JSONObject();
			reJson.put("result", "001");
			BaseServletTool.sendParam(response, reJson.toString());
		}
	}
	
	/*
	 * 获取资源表
	 */
	@RequestMapping(value = "/getRes.do")
	public void getRes(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			//jsonStr ="{'staffNo':'xj110','sn':'862199029026496','key':'GJ'}";
			String reJsonStr = inspectHelperServiceImpl.getRes(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 获取ODF资源表
	 */
	@RequestMapping(value = "/getOdfPortsList.do")
	public void getOdfPortsList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			//jsonStr="{'port':'','checkLevel':'state_a','startDate':'2015-01-01','sn':'862199029026496','staffId':'2622','endDate':'2015-02-10','checkCompany':'','areaId':'3','time':'30','staffNo':'xj110','optCode':'','checkerNo':'','resCode':'','checkState':''}";
			JSONObject json = JSONObject.fromObject(jsonStr);
			String staffId = json.getString("staffId");
			String staffNo = json.getString("staffNo");
			String areaId = json.getString("areaId");
			String sn = json.getString("sn");
			String time = json.getString("time");
			String checkLevel = json.getString("checkLevel");
			String checkCompany = json.getString("checkCompany");
			String checkState = json.getString("checkState");
			String resCode = json.getString("resCode");
			String port = json.getString("port");
			String checkerNo = json.getString("checkerNo");
			String startDate = json.getString("startDate");
			String endDate = json.getString("endDate");
			String optCode = json.getString("optCode");
			Map param = new HashMap();
			// param.put("staffId", staffId);
			// param.put("staffNo", staffNo);
			// param.put("areaId", areaId);
			// param.put("sn", sn);
			param.put("date", time);
			// param.put("checkLevel", checkLevel);
			// param.put("checkCompany", checkCompany);
			param.put("ischecked", checkState);
			param.put("equCode", resCode);
			// param.put("port", port);
			// param.put("checkerNo", checkerNo);
			param.put("startDate", startDate);
			param.put("endDate", endDate);
			// param.put("optCode", optCode);
			String role = inspectServiceImpl.getODFStaffRole(staffNo);
			if (role.equals(ODF_A)) {
				param.put("state_", "state_a");
				param.put("staffNo", staffNo);
			} else if (role.equals(ODF_B)) {
				param.put("state_", "state_b");
				param.put("staffNo1", staffNo);
			} else if (role.equals(ODF_C)) {
				param.put("state_", "state_c");
			} else if (role.equals(ODF_D)) {
				param.put("state_", "state_d");
			}
			if (time.equals("lastCheck")) {
				String lastCheckDate = inspectHelperDao
						.getLastCheckDate(param);
				param.put("lastCheckDate", lastCheckDate);
				param.remove("date");
			}
			List<Map<String, Object>> list = inspectHelperDao.getODFDinamicRes(param);

			// String[] columnNames = new String[] { "序号", "ID", "设备ID", "设备编码",
			// "设备名称", "原光路编码", "动态数据", "实时光路", "端子", "光路状态", "更芯日期",
			// "现场情况（无问题的保留空白）", "整改情况", "是否FTTH/BBU(FTTH/BBU)", "1级状态",
			// "2级状态", "3级状态", "4级状态", "巡检公司", "一级巡检人" };
			// String[] columnMethods = new String[] { "", "ID", "EQUIPMENT_ID",
			// "设备编码_B", "设备名称_B", "光路编码_A", "光路编码_B", "NO", "端口_B",
			// "NAME", "INSERTDATE", "", "", "", "STATE_A", "STATE_B",
			// "STATE_C", "STATE_D", "OWN_COMPANY", "STAFF_NAME" };

			JSONObject reJson = new JSONObject();
			JSONArray info = new JSONArray();
			JSONObject odfItem = new JSONObject();
			JSONObject bodyObject = new JSONObject();
			for (Map item : list) {
				String portId = String.valueOf(item.get("PHY_PORT_ID_A"));
				Map paramForRealTimeOpticalCode = new HashMap();
				paramForRealTimeOpticalCode.put("staffNo", staffNo);
				paramForRealTimeOpticalCode.put("portId", portId);
				// item.remove("端口_B");
				// item.put("端口_B", inspectHelperService
				// .getRealTimeOpticalCode(paramForRealTimeOpticalCode));
				odfItem.put("id", portId);
				odfItem.put("equipCode", item.get("设备编码_B"));
				odfItem.put("equipName", item.get("设备名称_B"));
				odfItem.put("dynamicCode", item.get("光路编码_B") == null ? ""
						: item.get("光路编码_B"));
				odfItem.put("port", item.get("端口_B"));
				odfItem.put("compareTime", item.get("INSERTDATE"));
				odfItem.put("statea", item.get("STATE_A"));
				odfItem.put("stateb", item.get("STATE_B"));
				odfItem.put("statec", item.get("STATE_C"));
				odfItem.put("stated", item.get("STATE_D"));
				odfItem.put("checkCompany", item.get("OWN_COMPANY"));
				odfItem.put("firstChecker", item.get("STAFF_NAME"));
				odfItem.put("equipmentId", item.get("EQUIPMENT_ID"));
				info.add(odfItem);
			}
			bodyObject.put("ports", info);
			reJson.put("result", "000");
			reJson.put("body", bodyObject);
			BaseServletTool.sendParam(response, reJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject reJson = new JSONObject();
			reJson.put("result", "001");
			BaseServletTool.sendParam(response, reJson.toString());
		}
	}

	/*
	 * 获取智能卡片
	 */
	 @RequestMapping(value = "/getIntellCardInfo.do")
	public void getIntellCardInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			jsonStr = "{'sn':'862199029026496','staffId':'2622','obdCode':'','resNo':'250JN.KFQ00Z01\\/ODF(GB007001)','uNos':'1'}";
			// jsonStr =
			// "{\"sn\":\"C4F12BA0011CE1F\",\"uNos\":\"1\",\"resNo\": \"512SZ.ZHJMJ/GJ012\",\"obdCode\": \"DT-POS-001611,DT-POS-020513\",\"staffId\": \"522\"}";
			String reJsonStr = checkHelperServiceImpl
					.getIntellCardInfo(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
