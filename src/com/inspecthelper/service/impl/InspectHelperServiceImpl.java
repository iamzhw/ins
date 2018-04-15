package com.inspecthelper.service.impl;

import icom.util.ExceptionCode;
import icom.util.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import util.dataSource.SwitchDataSourceUtil;

import com.inspecthelper.dao.DblinkDao;
import com.inspecthelper.dao.EquipDao;
import com.inspecthelper.dao.InsOrderTaskDao;
import com.inspecthelper.dao.InspectHelperDao;
import com.inspecthelper.model.Dblink;
import com.inspecthelper.service.EquipService;
import com.inspecthelper.service.IInspectHelperService;
import com.system.constant.UploadFileType;
import com.system.service.BaseMethodService;
import com.system.service.GeneralService;
import com.system.tool.ImageTool;
import com.util.DblinkUtil;
import com.util.OnlineDesignOperation;
import com.util.SyncGJFromZyws;

@Service
@SuppressWarnings("all")
public class InspectHelperServiceImpl implements IInspectHelperService {

	@Resource
	private InspectHelperDao inspectHelperDaoImpl;
	
	@Resource
	private GeneralService generalService;
	@Resource
	private InsOrderTaskDao insOrderTaskDao;
	
	private static final String ODF_A = "14";// ODF1级检查人员角色ID
	private static final String ODF_B = "15";
	private static final String ODF_C = "16";
	private static final String ODF_D = "17";
	@Resource 
	private DblinkDao dblinkDaoImpl;
	
	@Resource
	private EquipDao equipDao;
	@Resource
	private BaseMethodService baseMethodService;
	
	@Resource
	private EquipService equipServiceImpl;
	
	@Override
	public String getOBDByGJNo(String str) {
		String resCode = "";
		String sn = "";
		JSONObject reJson = new JSONObject();
		JSONArray reOBD = new JSONArray();
		JSONObject reO = new JSONObject();
		try {
			/* 解析JSON参数 */
			JSONObject json = JSONObject.fromObject(str);
			Dblink dblink = DblinkUtil.getDbLink(dblinkDaoImpl, str);
			resCode = json.getString("no");
			if (json.has("sn")) {
				sn = json.getString("sn");

				baseMethodService.saveResLog(sn,
						OnlineDesignOperation.INS_GET_OBD, "1", "", resCode,
						"", "", "");

			}

			if (resCode.length() == 0) {
				return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
			}
			Map param = new HashMap();
			param.put("resCode", resCode);
			param.put("dblinkUsername", dblink.getDblinkUsername());
			// param.put("dblinkUrl", dblink.getDblinkUrl());
			List l = this.getOBDByGJNo(dblink.getJndi(), param);
			if (l.size() == 0) {
				reJson.put("obd", reOBD);
				reJson.put("result", "000");
			} else {
				for (int i = 0; i < l.size(); i++) {
					String no = "";
					String name = "";
					no = (String) (((Map) l.get(i)).get("NO") == null ? ""
							: ((Map) l.get(i)).get("NO"));
					name = (String) (((Map) l.get(i)).get("NAME") == null ? ""
							: ((Map) l.get(i)).get("NAME"));
					reO.put("no", no);
					reO.put("name", name);
					reOBD.add(reO);
				}
				reJson.put("obd", reOBD);
				reJson.put("result", "000");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Result.returnCode(ExceptionCode.SERVER_ERROR);
		}
		return reJson.toString();
	}

	private List getOBDByGJNo(String jndi, Map param) {
		List l = new ArrayList();
		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			l = inspectHelperDaoImpl.getOBDByGJNo(param);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		} finally {
			return l;
		}

	}
	@Override
	public String getOrderList(String jsonStr) {
		jsonStr="{'overDayState':'','sn':'862199029026496','area':'','orderNo':'','staffId':'2622','stateId':'','resTypeId':'','overDay':'','targetId':'','order_equ':'','areaId':'3','ownCompany':'','roleId':'63'}";
			String staffId = "";// 员工编号
			String roleId = ""; // 角色编号 维护人员
			String areaId = "";// 地区Id
			String sn = "";// 硬件串号
			String orderNo = "";// 工单号
			String resTypeId = "";// 设备类型
			String targetId = "";// 检查项
			String order_equ = "";// 设备编码
			String stateId = "";// 状态
			String overTime = "";// 超时工单状态
			String ownCompany = "";// 巡检公司
			String area = "";// 子区域
			String overDay = "";// 超时时间

			String returnJson = null;
			HashMap<String, String> map = new HashMap<String, String>();
			JSONObject json = JSONObject.fromObject(jsonStr);
			JSONObject reJson = new JSONObject();
			JSONArray resultList = new JSONArray();
			if (json.getString("staffId") != null) {
				staffId = json.getString("staffId").toString();
			}
			if (json.getString("roleId") != null) {
				roleId = json.getString("roleId").toString();
			}
			if (json.getString("areaId") != null) {
				areaId = json.getString("areaId").toString();
			}
			if (json.getString("sn") != null) {
				sn = json.getString("sn").toString();
			}
			if (json.getString("orderNo") != null) {
				orderNo = json.getString("orderNo").toString();
			}
			if (json.getString("resTypeId") != null) {
				resTypeId = json.getString("resTypeId").toString();
			}
			if (json.getString("targetId") != null) {
				targetId = json.getString("targetId").toString();
			}
			if (json.getString("order_equ") != null) {
				order_equ = json.getString("order_equ").toString();
			}
			if (json.getString("stateId") != null) {
				stateId = json.getString("stateId").toString();
			}
			if (json.getString("overDayState") != null) {
				overTime = json.getString("overDayState").toString();
			}
			if (json.getString("overDayState") != null) {
				ownCompany = json.getString("ownCompany").toString();
			}
			if (json.getString("area") != null) {
				area = json.getString("area").toString();
			}
			if (json.getString("overDay") != null) {
				overDay = json.getString("overDay").toString();
			}
			map.put("staffId_", staffId);
			map.put("roleId", roleId);
			map.put("area", areaId);
			// map.put("sn", sn);
			map.put("orderNo", orderNo);
			map.put("resTypeId", resTypeId);
			map.put("ownCompany", ownCompany);
			map.put("order_equ", order_equ);
			map.put("stateId", stateId);
			map.put("overTime", overTime);
			map.put("ownCompany", ownCompany);
			map.put("sonArea", area);
			map.put("overDay", overDay);

			List<HashMap> list = inspectHelperDaoImpl.getTroubleOrderList(map);
			for (Map item : list) {
				JSONObject jsonObj = new JSONObject();
				String resTroubleId = "";
				String resTaskCode = "";
				String resState = "";
				String resEquipType = "";// 资源类别
				String resEquipCode = "";// 设备编码
				String resEquipName = "";// 设备名称
				String resArea = "";// 区域
				String resInspector = "";// 巡检人员
				String resInspectCom = "";// 巡检公司
				String resMaintainer = "";// 维护人员
				String resJudger = "";// 审核人员
				String resInspectItem = "";// 检查项
				String resQuestion = "";// 问题描述
				String resSubmitType = "";// 上报方式
				String resSubmitDate = "";// 上报日期
				if ((Object) item.get("TROUBLE_ID") != null) {
					resTroubleId = ((Object) item.get("TROUBLE_ID")).toString();// 问题号
				}
				if ((Object) item.get("TASK_CODE") != null) {
					resTaskCode = ((Object) item.get("TASK_CODE")).toString();// 任务编码
				}
				if ((Object) item.get("TRSTATE") != null) {
					resState = ((Object) item.get("TRSTATE")).toString();// 状态
				}
				if ((Object) item.get("RES_TYPE") != null) {
					resEquipType = ((Object) item.get("RES_TYPE")).toString();// 资源类别
				}
				if ((Object) item.get("EQUIPMENT_CODE") != null) {
					resEquipCode = ((Object) item.get("EQUIPMENT_CODE")).toString();// 设备编码
				}
				if ((Object) item.get("EQUIPMENT_NAME") != null) {
					resEquipName = ((Object) item.get("EQUIPMENT_NAME")).toString();// 设备名称
				}
				if ((Object) item.get("SON_AREA") != null) {
					resArea = ((Object) item.get("SON_AREA")).toString();// 区域
				}
				if ((Object) item.get("UPLOAD_STAFF_NAME") != null) {
					resInspector = ((Object) item.get("UPLOAD_STAFF_NAME"))
							.toString();// 巡检人员
				}
				if ((Object) item.get("INS_COMPANY") != null) {
					resInspectCom = ((Object) item.get("INS_COMPANY")).toString();// 巡检公司
				}
				if ((Object) item.get("STAFF_NAME ") != null) {
					resMaintainer = ((Object) item.get("STAFF_NAME ")).toString();// 维护人员
				}
				if ((Object) item.get("CHECK_STAFF_NAME") != null) {
					resJudger = ((Object) item.get("CHECK_STAFF_NAME")).toString();// 审核人员
				}
				if ((Object) item.get("TARGET_NAME") != null) {
					resInspectItem = ((Object) item.get("TARGET_NAME")).toString();// 检查项
				}
				if ((Object) item.get("REMARKS") != null) {
					resQuestion = ((Object) item.get("REMARKS")).toString();// 问题描述
				}
				if ((Object) item.get("SN") != null) {
					resSubmitType = ((Object) item.get("SN")).toString();// 上报方式
				}
				if ((Object) item.get("HAPPEN_DATE") != null) {
					resSubmitDate = ((Object) item.get("HAPPEN_DATE")).toString();// 上报日期
				}
				jsonObj.put("troubleId", resTroubleId);
				jsonObj.put("taskCode", resTaskCode);
				jsonObj.put("state", resState);
				jsonObj.put("equipType", resEquipType);
				jsonObj.put("equipCode", resEquipCode);
				jsonObj.put("equipName", resEquipName);
				jsonObj.put("area", resArea);
				jsonObj.put("inspector", resInspector);
				jsonObj.put("inspectCom", resInspectCom);
				jsonObj.put("maintainer", resMaintainer);
				jsonObj.put("judger", resJudger);
				jsonObj.put("inspectItem", resInspectItem);
				jsonObj.put("question", resQuestion);
				jsonObj.put("submitType", resSubmitType);
				jsonObj.put("submitDate", resSubmitDate);
				resultList.add(jsonObj);

			}
			reJson.put("result", "000");
			reJson.put("orderlist", resultList);
			return reJson.toString();
	}

	@Override
	public String getTaskList(String str) {
		String sn = "";
		String staffNo = "";
		String equipmentId = "";
		String signId = "";
		JSONObject reJson = new JSONObject();
		JSONObject reBody = new JSONObject();
		JSONArray orderReList = new JSONArray();
		JSONObject orderO = new JSONObject();
		try {
			/* 解析JSON参数 */
			JSONObject json = JSONObject.fromObject(str);
			sn = json.getString("sn");
			staffNo = json.getString("staffNo");

			baseMethodService.saveResLog(sn,
					OnlineDesignOperation.INS_ALL_TASK, "1", "staffNo", "", "",
					"", "");

			if (sn.length() == 0 || staffNo.length() == 0) {
				return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
			}
			Map param = new HashMap();
			Map order = new HashMap();
			Map sign = new HashMap();
			param.put("staffNo", staffNo);
			/* 获取工单列表 */
			List<Map>  orderList = inspectHelperDaoImpl.getTskOrderList(param);
			// if(orderList.size() == 0){
			// return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
			// }

			for (int i = 0; i < orderList.size(); i++) {
				List signList = new ArrayList();
				JSONArray signReList = new JSONArray();
				order = (Map) orderList.get(i);
				param.put("orderId", order.get("ORDER_ID"));
				/* 判断是否插入签到点 */
				int flag = inspectHelperDaoImpl.getTaskEquCount(param);
				/* 如果没有签到属性，则插入 */
				if (flag == 0) {
					List taskEquList = inspectHelperDaoImpl
							.getTaskEquList(param);
					if (taskEquList.size() == 0) {
						return Result.returnCode(ExceptionCode.SERVER_ERROR);
					}
					for (int j = 0; j < taskEquList.size(); j++) {
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("seqName", "SIGN_ID");
						signId = inspectHelperDaoImpl.getNextSeqVal(map);
						map=(Map)taskEquList.get(j);
						equipmentId = String.valueOf(map.get("EQUIPMENT_ID"));
						param.put("signId", signId);
						param.put("equipmentId", equipmentId);
						inspectHelperDaoImpl.createTaskOrderSign(param);
					}
				}
				String state = (String) order.get("STATE");
				// if(state.equals("1") || state.equals("0")){
				/* 获取任务关联设备列表 */
				signList = inspectHelperDaoImpl.getTaskOrderSign(param);
				// if(signList.size() == 0){
				// return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
				// }
				for (int j = 0; j < signList.size(); j++) {
					JSONObject signO = new JSONObject();
					sign = (Map) signList.get(j);
					signO.put("equipmentNo", sign.get("EQUIPMENT_CODE"));
					signO.put("signState", sign.get("SIGN_STATE"));
					// String latitude = (String)
					// (sign.get("LATITUDE")==null?"":sign.get("LATITUDE"));
					// String longitude = (String)
					// (sign.get("LONGITUDE")==null?"":sign.get("LONGITUDE"));
					// signO.put("postion", longitude+","+latitude);
					signO.put("address", sign.get("ADDRESS") == null ? ""
							: sign.get("ADDRESS"));
					signO.put("resTypeId", sign.get("RES_TYPE_ID") == null ? ""
							: sign.get("RES_TYPE_ID"));
					signO.put(
							"equipmentName",
							sign.get("EQUIPMENT_NAME") == null ? "" : sign
									.get("EQUIPMENT_NAME"));
					signO.put("checkState", sign.get("CHECK_STATE"));
					/* 未签到时坐标为“” */
					if (sign.get("SIGN_STATE").equals("0")) {
						signO.put("signPosition", "");
					} else {
						signO.put("signPosition", sign.get("LONGITUDE") + ","
								+ sign.get("LATITUDE"));
					}
					signO.put("equipmentId", sign.get("EQUIPMENT_ID"));
					signReList.add(signO);
				}
				// }
				orderO.put("taskId", order.get("ORDER_ID"));
				orderO.put("cycle", order.get("CYCLE"));
				orderO.put("taskCode", order.get("TASK_CODE"));
				orderO.put("beginTime", order.get("START_DATE"));
				orderO.put("endTime", order.get("END_DATE"));
				orderO.put("taskState", order.get("STATE"));
				orderO.put("equpments", signReList);
				orderReList.add(orderO);
			}
			reBody.put("tasks", orderReList);
			reJson.put("result", "000");
			reJson.put("body", reBody);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.returnCode(ExceptionCode.SERVER_ERROR);
		}

		/* 插入安全监控 */
		baseMethodService.saveResLog(sn, OnlineDesignOperation.RES_REGISTER,
				"1", "", "", "", "", "");

		return reJson.toString();
	}

	@Override
	public String getTroubleId(String jsonStr) {
		String sn = "";
		String staffNo = "";
		JSONObject reJson = new JSONObject();
		try {
			/* 解析JSON参数 */
			JSONObject json = JSONObject.fromObject(jsonStr);
			sn = json.getString("sn");
			if (sn.equals("")) {
				return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
			}

			baseMethodService.saveResLog(sn, OnlineDesignOperation.INS_GET_OBD,
					"1", staffNo, "", "", "", "");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("seqName", "ORDER_ID");
			String troubleId = inspectHelperDaoImpl.getNextSeqVal(map);
			if (troubleId != null) {
				reJson.put("troubleId", troubleId);
				reJson.put("result", "000");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Result.returnCode(ExceptionCode.SERVER_ERROR);
		}
		return reJson.toString();
	}

	@Override
	public String getUnoByResNo(String str) {
		String resNo = "";
		String uNo = "";
		String sn = "";
		JSONObject reJson = new JSONObject();
		/* 解析JSON参数 */
		JSONObject json = JSONObject.fromObject(str);
		resNo = json.getString("resNo");
		if (json.has("sn")) {
			sn = json.getString("sn");
			baseMethodService.saveResLog(sn, OnlineDesignOperation.INS_GET_UNO,
					"1", uNo, resNo, "", "", "");
		}
		if (resNo.length() == 0) {
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
		}
		Map param = new HashMap();
		param.put("resNo", resNo);
		Dblink dblink = DblinkUtil.getDbLink(dblinkDaoImpl, str);
		param.put("dblinkUsername", dblink.getDblinkUsername());

		List l = getUnoByResNo(dblink.getJndi(), param);
		if (l.size() == 0) {
			return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
		}
		for (int i = 0; i < l.size(); i++) {
			if(null!=l.get(i)){
			if (i == 0) {
				uNo = String.valueOf(((Map) l.get(i)).get("UNO"));
			} else {
				uNo += "," + String.valueOf(((Map) l.get(i)).get("UNO"));
			}
			}
		}
		reJson.put("result", "000");
		reJson.put("uNo", uNo);
		return reJson.toString();
	}

	private List getUnoByResNo(String jndi, Map param) {

		List<Map> l = new ArrayList();

		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			l = inspectHelperDaoImpl.getUnoByResNo(param);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();

		}
		return l;

	}
	@Override
	public String orderDetail(String jsonStr) {
		JSONObject json = JSONObject.fromObject(jsonStr);
		String troubleId = "";
		try {
			troubleId = json.getString("troubleId");
		} catch (Exception e) {
			return Result.returnCode(ExceptionCode.JSON_ERROR);
		}
		HashMap<String, String> hashMap = new HashMap<String, String>();

		hashMap.put("orderId", troubleId);
		List<Map> result = insOrderTaskDao.getActionHistoryOrderList(hashMap);

		if (result == null || result.size() == 0) {
			return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
		} else {
			String[] cols = new String[] { "staff_name", "own_company",
					"description", "goal_staff_name", "goal_own_company",
					"action_date" };
			String resultJsonStr = getResultJsonStr(result, cols);
			return resultJsonStr;
		}
	}

	// cols中的字符串必须为大写
	private String getResultJsonStr(List<Map> result, String[] cols) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "000");
		JSONArray jsArray = new JSONArray();
		for (int i = 0; i < result.size(); i++) {
			JSONObject detailItem = new JSONObject();
			for (int j = 0; j < cols.length; j++) {
				Object value = result.get(i).get(cols[j].toUpperCase());
				if (value == null) {
					value = "";
				}
				detailItem.put(cols[j], value);
			}
			jsArray.add(detailItem);
		}
		resultJson.put("data", jsArray);
		return resultJson.toString();
	}
	
	@Override
	public void saveDes(String jsonStr) {
		JSONObject json = JSONObject.fromObject(jsonStr);
		String sn = json.getString("sn").toString();
		String staffId = json.getString("staffId").toString();
		String troubleId = json.getString("troubleId").toString();
		String orderDes = json.getString("orderDes").toString();
		String operate = json.getString("operate").toString();
		Map m = new HashMap();
		m.put("troubleId", troubleId);
		m.put("remarks", orderDes);

		m.put("staffId", staffId);
		m.put("actionType", "1");

		// insert into
		// ins_action_history(action_id,trouble_id,action_staff_id,description,action_date,action_type,goal_staff_id)
		// values(ACTION_ID.Nextval,#troubleId#,#staffId#,#descAction#,sysdate,#actionType#,#goalStaffId#)

		if ("1".equals(operate)) {
			// 回单操作

			m.put("descAction", "回单操作：" + orderDes);

			insOrderTaskDao.modifyOrderHuiInfo(m);
			insOrderTaskDao.insertActionHistory(m);
			//发送给资源卫士
			SyncGJFromZyws.returnZgResult(insOrderTaskDao.getZGTroubleInfo(m));
		} else if ("2".equals(operate)) {
			// 退单操作
			m.put("descAction", "退单,原因是：" + orderDes);
			insOrderTaskDao.modifyOrderTuidanInfo(m);
			insOrderTaskDao.insertActionHistory(m);
		}

	}

	@Override
	public void saveFileStream(HttpServletRequest request) {
		/* 解析json属性 */
		String sn = request.getHeader("sn");
		String staffId = request.getHeader("staffId");
		String troubleId = request.getHeader("troubleId");
		String fileType = request.getHeader("fileType");
		// 获得文件的字节流
		byte[] file = ImageTool.getPhotoByteFromStream(request);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("seqName", "ORDER_ID");
		String fileId = inspectHelperDaoImpl.getNextSeqVal(map);
		// 通过staffId找到staffNo

		String staffNo = inspectHelperDaoImpl.getStaffNo(staffId);
		HashMap pathMap = new HashMap();
		if ("1".equals(fileType)) {
			// 照片
			savePhotoPad(file, troubleId, staffNo);
		} else if ("2".equals(fileType)) {
			// word
			pathMap = baseMethodService.uploadFile(fileId,file,UploadFileType.UPLOAD_FILE_TYPE_WORD);
		} else if ("3".equals(fileType)) {
			// excel
			pathMap = baseMethodService.uploadFile(fileId,file,UploadFileType.UPLOAD_FILE_TYPE_EXCEL);
		}
		Map param = new HashMap();
		param.put("resourceCode", troubleId);
		param.put("fileId", fileId);
		param.put("path", pathMap.get("path"));
		param.put("staffNo", staffNo);
		insOrderTaskDao.saveResourceFile(param);
	}

	public boolean savePhotoPad(byte[] photoByte, String troublecode,
			String staffNo) {
		/* 获取水印文字 */
		Map map = new HashMap();
		map.put("staffNo", staffNo);
		map.put("troubleId", troublecode);
		//String str = inspectHelperDaoImpl.getStr(map);
		/* 获得整改次数 */
		//Integer c = inspectHelperDaoImpl.getZGCount(map) + 1;
		/* 给照片加水印 */
		// MyImageTool.createMark(f, str+"/"+c+"次整改", Color.RED, 10);
		// byte[] photoByte = ImageTool.getPhotoByteFromFile(f);
		String photoId =  generalService.getNextSeqVal("PHOTO_ID"); // 获得照片主键
		/* 调用上传图片方法 */
		HashMap<String,Object> pathMap = baseMethodService.uploadFile(photoId,photoByte,"photo");//调用上传图片方法
		if (pathMap == null) {
			return false; // 图片上传失败
		}
		/* 向resource_photo表中插入一条数据 */
		Map param = new HashMap();
		String resourceCode = troublecode;
		param.put("resourceCode", resourceCode);
		param.put("photoId", photoId);
		param.put("photoPath", pathMap.get("photoPath"));
		param.put("microPhotoPath", pathMap.get("microPhotoPath"));
		param.put("staffNo", staffNo);
		insOrderTaskDao.saveResourcePhoto(param);
		return true;

	}
	@Override
	public String saveInsPhoto(HttpServletRequest request) {
		String userId = "";
		String orderId = "";
		String equipmentId = "";
		JSONObject reJson = new JSONObject();
		try {
			userId = request.getHeader("staffId");
			orderId = request.getHeader("taskId");
			equipmentId=request.getHeader("equipmentId");
			Map checkParam = new HashMap();
			// Modified by ma_xianyyang start
			checkParam.put("orderId", orderId);
			checkParam.put("equipmentId", equipmentId);
			List list=inspectHelperDaoImpl.getCheckedTimes(checkParam);
			Integer count = (Integer) list.get(0);
			if ((boolean) (count > 0)) {
				reJson.put("result", "000");
				return reJson.toString();
			}
			// Modified by ma_xianyyang end
			//equipmentId = request.getHeader("equipmentId");
			if (userId == null) {
				// this.baseMethodService.saveLog("获取json数据", "获取失败", "0",
				// userId);
			}
			String resultJson = this.saveInsPhotoUseStream(request);
			if (resultJson.equals("success")) {
				reJson.put("result", "000");
				return reJson.toString();
			} else {
				// this.changeState(pointId, orderId, equipmentId);
				return Result.returnCode(ExceptionCode.SAVE_PHOTO_FAIL); // 图片上传失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			//baseMethodService.saveLog("巡检-上传照片", "错误内容：" + e.getMessage(), "1",
				//	userId);
			return Result.returnCode(ExceptionCode.SERVER_ERROR);
		}
	}
	
	/**
	 * 使用流保存照片
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String saveInsPhotoUseStream(HttpServletRequest request) {
		/* 解析json属性 */
		String staffNo = null; // 用户id
		String orderId = null;// 任务单
		String equipmentId = null;// 设备
		String targetId = null;// 检查项
		String uNo = "";
		String uPort = null;
		String id = "";
		byte[] photoByte = null;
		try {
			staffNo = request.getHeader("staffId");
			orderId = request.getHeader("taskId");
			equipmentId = request.getHeader("equipmentId");
			targetId = request.getHeader("checkItemId");
			uNo = request.getHeader("uNo");
			uPort = request.getHeader("uPort");
			id = request.getHeader("id");
			// String areaId = baseMethodService.queryAreaID(userId);

			/* 从流中读取原图信息，并上传图片 */
			photoByte = ImageTool.getPhotoByteFromStream(request);
			String photoId = generalService.getNextSeqVal("PHOTO_ID"); // 获得照片主键
			HashMap pathMap = baseMethodService.uploadFile(photoId,photoByte,"photo");
			if (pathMap == null) {
				return Result.returnCode(ExceptionCode.SAVE_PHOTO_FAIL); // 图片上传失败
			}

			/* 保存照片信息到resource_photo表中 */
			Map param = new HashMap();
			param.put("staffNo", staffNo);
			if (id.length() != 0) {
				param.put("resourceCode", orderId + "," + equipmentId + ","
						+ targetId + "," + id);
			} else if (uNo.length() == 0) {
				param.put("resourceCode", orderId + "," + equipmentId + ","
						+ targetId);
			} else {
				param.put("resourceCode", orderId + "," + equipmentId + ","
						+ targetId + "," + uNo + "," + uPort);
			}
			param.put("photoId", photoId);
			param.put("photoPath", pathMap.get("photoPath"));
			param.put("microPhotoPath", pathMap.get("microPhotoPath"));
			insOrderTaskDao.saveResourcePhoto(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR); // 客户端参数错误
		}
		return "success";
	}


	@Override
	/*
	 * 对设备具体的问题进行填报
	 */
	public String saveResTrouble(String str) {
		String orderId = "";
		String equipmentId = "";
		String targetId = "";
		String type = "";
		String description = "";
		String staffNo = "";
		String staffId = "";
		String uNo = "";
		String uPort = "";
		String uploadStaff = "";
		String checkStaff = "";
		String sn = "";
		String troubleType = "";
		String idS = "";
		String beginTime = "";
		String endTime = "";
		/* 区别手机和PAD变更 */
		String terminalType = "PAD";
		/* 区别手机和PAD变更 */
		boolean flag = true;
		JSONObject reJson = new JSONObject();
		try {
			/* 解析JSON参数 */
			JSONObject json = JSONObject.fromObject(str);
			orderId = json.getString("taskId");
			equipmentId = json.getString("equipmentId");
			staffNo = json.getString("staffNo");
			/* 区别开PAD和手机变更 */
			if (json.containsKey("terminal_type")) {
				terminalType = json.getString("terminal_type");
			}
			/* 区别开PAD和手机变更 */
			Map checkParam = new HashMap();
			checkParam.put("staffNo", staffNo);
			checkParam.put("equipmentId", equipmentId);
			checkParam.put("orderId", orderId);
			List list1=inspectHelperDaoImpl.getCheckedTimesNotForSZ(checkParam);
			Integer count1 = (Integer) list1.get(0);
			if ((boolean) (count1>0)) {
				reJson.put("result", "000");
				return reJson.toString();
			}
			List list=inspectHelperDaoImpl.getCheckedTimes(checkParam);
			Integer count = (Integer) list.get(0);
			if ((boolean) (count>0)) {
				reJson.put("result", "000");
				return reJson.toString();
			}
			uploadStaff = json.getString("staffNo");
			if (json.has("staffId")) {
				staffId = json.getString("staffId");
			}
			if (json.has("idS")) {
				idS = json.getString("idS");
				/* 更新ODF动态数据状态 */
				Map param = new HashMap();
				param.put("uploadStaff", uploadStaff);
				param.put("id", idS);
				param.put("state", "1");
				param.put("staffId", staffId);
				this.updateDinamicChange(param);
			}
			if (json.has("flag")) {
				String f = json.getString("flag");
				if (f.equals("temple run")) {
					flag = false;
				}
			}
			JSONArray troubleL = json.getJSONArray("troubleList");
			for (int i = 0; i < troubleL.size(); i++) {
				JSONObject jsonT = (JSONObject) troubleL.get(i);
				String id = "";
				if (jsonT.has("sn")) {
					sn = jsonT.getString("sn");
				}
				if (jsonT.has("id")) {
					id = jsonT.getString("id");

				}
				if (jsonT.has("troubleType")) {
					troubleType = jsonT.getString("troubleType");
				}
				targetId = jsonT.getString("checkItemId");
				type = jsonT.getString("type");
				description = jsonT.getString("description");
				if (jsonT.has("uNo")) {
					uNo = jsonT.getString("uNo");
					uPort = jsonT.getString("port");
				}
				if (equipmentId.length() == 0 || targetId.length() == 0
						|| type.length() == 0) {
					return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
				}
				/* 将这一问题工单分配给相应的审核人员 add-by luyl 2013-1-7 */
				Map param = new HashMap();
				param.put("targetId", targetId + troubleType);
				List targetL = Arrays.asList("0001", "0012", "0013", "0014",
						"0015", "0016", "0017", "0018", "0019", "0022", "0023",
						"0024", "0025", "0027", "0034", "0035");
				if (targetL.contains(targetId + troubleType)) {
					// param.put("equipmentId", equipmentId);
					param.put("uploadStaff", uploadStaff);
				}
				param.put("equipmentId", equipmentId);
				param.put("checkStaffNoForNotSpecial",
						json.getString("staffNo"));
				List<Map<String,Object>> getlist=inspectHelperDaoImpl.getCheckStaff(param);
				if(getlist.size()>0){
				checkStaff = (String) getlist.get(0).get("STAFF_ID");
				}
				String troubleId = generalService.getNextSeqVal("TROUBLE_ID");
				param.put("targetId", targetId);
				param.put("troubleId", troubleId);
				param.put("orderId", orderId);
				param.put("uploadStaff", uploadStaff);
				param.put("checkStaff", checkStaff);
				param.put("equipmentId", equipmentId);
				param.put("description", description);
				param.put("type", type);
				/* 区别开手机还是PAD变更 */
				param.put("sn", terminalType);
				/* 区别开手机还是PAD变更 */
				param.put("ftth", troubleType);
				param.put("id", id);
				param.put("staffId", staffId);
				if (type.equals("0")) {
					param.put("state", "0");
				} else if (type.equals("1")) {
					param.put("state", "3");
				}
				if (id.length() > 0) {
					param.put("troubleCode", orderId + "," + equipmentId + ","
							+ targetId + "," + id);
					/* 更新ODF动态数据状态 */
					this.updateDinamicChange(param);
				} else if (uNo.length() == 0) {
					param.put("troubleCode", orderId + "," + equipmentId + ","
							+ targetId);
				} else {
					param.put("troubleCode", orderId + "," + equipmentId + ","
							+ targetId + "," + uNo + "," + uPort);
				}
				try{
					inspectHelperDaoImpl.saveResTrouble(param);
					reJson.put("result", "000");
				} catch(Exception e) {
					reJson.put("result", ExceptionCode.SAVE_TROUBLE_FAIL);
				}

				/* add by luyl 2013-1-11 插入流程 */
				Map actionMap = new HashMap();
				actionMap.put("troubleId", troubleId);
				actionMap.put("staffNo", uploadStaff);
				actionMap.put("staffId", staffId);
				actionMap.put("goalStaffId", checkStaff);
				actionMap.put("actionType", "5");
				actionMap.put("descAction", "上报问题：" + description);
				insOrderTaskDao.insertActionHistory(actionMap);
			}
			if (!flag) {
				Map param1 = new HashMap();
				param1.put("orderId", orderId);
				param1.put("equipmentId", equipmentId);
				param1.put("staffNo", uploadStaff);
				/* 区别开手机还是PAD变更 */
				param1.put("sn", terminalType);
				/* 区别开手机还是PAD变更 */
				if (json.containsKey("beginTime")) {
					beginTime = String.valueOf(json.get("beginTime"));
					param1.put("beginTime", beginTime);
				}
				if (json.containsKey("endTime")) {
					endTime = String.valueOf(json.get("endTime"));
					param1.put("endTime", endTime);
				}
				int i=inspectHelperDaoImpl.checkResCount(param1);
				if (!(i>0)) {
					inspectHelperDaoImpl.createTaskOrderCheck(param1);
				}
				inspectHelperDaoImpl.checkRes(param1);
			}
			if (flag) {
				/* 判断工单是否竣工，将此设备置为已填报状态，将已签到设备数量+1....... */
				Map param1 = new HashMap();
				param1.put("orderId", orderId);
				param1.put("equipmentId", equipmentId);
				param1.put("staffNo", uploadStaff);
				/* 区别开手机还是PAD变更 */
				param1.put("sn", terminalType);
				/* 区别开手机还是PAD变更 */
				if (json.containsKey("beginTime")) {
					beginTime = String.valueOf(json.get("beginTime"));
					param1.put("beginTime", beginTime);
				}
				if (json.containsKey("endTime")) {
					endTime = String.valueOf(json.get("endTime"));
					param1.put("endTime", endTime);
				}
				int j=inspectHelperDaoImpl.checkResCount(param1);
				if (!(j>0)) {
					inspectHelperDaoImpl.createTaskOrderCheck(param1);
				}
				boolean b1=true;
				try{
				 inspectHelperDaoImpl.checkRes(param1);
				 inspectHelperDaoImpl.updateTaskOrder(param1);
				 inspectHelperDaoImpl.updateTaskOrder1(param1);
				 inspectHelperDaoImpl.updateTaskOrder2(param1);
				}catch(Exception e){
					b1=false;
				}
				if (b1) {
					reJson.put("result", "000");
					/* 插入安全监控 */
					Map equ = inspectHelperDaoImpl.getEqu(equipmentId);
					baseMethodService.saveResLog(sn,
							OnlineDesignOperation.INS_CHECK, "1",
							String.valueOf(equ.get("EQUIPMENT_CODE")),
							String.valueOf(equ.get("EQUIPMENT_NAME")), "", "",
							"");
				} else {
					reJson.put("result", ExceptionCode.CHECK_FAIL);
				}
			} else {
				reJson.put("result", "000");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// reJson.put("result", ExceptionCode.SERVER_ERROR);
			reJson.put("result", "11");
			// return Result.returnCode(ExceptionCode.SERVER_ERROR);
			return reJson.toString();
		}
		return reJson.toString();
	}
	public void updateDinamicChange(Map param) {
		Map m = new HashMap();
		String staffNo = (String) param.get("uploadStaff");
		String state = (String) param.get("state");
		if (state.equals("0") || state.equals("3")) {
			state = "2";
		} else if (state.equals("1")) {
			state = "1";
		}
		String role = inspectHelperDaoImpl.getODFStaffRole(staffNo);
		if (role.equals(ODF_A)) {
			m.put("state_a", state);
		} else if (role.equals(ODF_B)) {
			m.put("state_b", state);
		} else if (role.equals(ODF_C)) {
			m.put("state_c", state);
		} else if (role.equals(ODF_D)) {
			m.put("state_d", state);
		}
		m.put("staffId", param.get("staffId"));
		m.put("trueIds", param.get("id"));
		// m.put("state", value)
		inspectHelperDaoImpl.updateDinamicChange(m);
	}


	@Override
	/*
	 * 签到设备
	 */
	public String signRes(String str) {
		String orderId = "";
		String equipmentId = "";
		String longitude = "";
		String latitude = "";
		String sn = "";
		JSONObject reJson = new JSONObject();
		try {
			/* 解析JSON参数 */
			JSONObject json = JSONObject.fromObject(str);
			orderId = json.getString("taskId");
			equipmentId = json.getString("equipmentId");
			longitude = json.getString("longitude");
			latitude = json.getString("latitude");
			if (json.has("sn")) {
				sn = json.getString("sn");
			}
			if (orderId.length() == 0 || equipmentId.length() == 0
					|| latitude.length() == 0 || longitude.length() == 0) {
				return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
			}
			Map param = new HashMap();
			param.put("orderId", orderId);
			param.put("equipmentId", equipmentId);
			param.put("longitude", longitude);
			param.put("latitude", latitude);
			boolean b = true;
			try{
				inspectHelperDaoImpl.signRes(param);
			}catch(Exception e){
				b=false;
			}
			if (b) {
				reJson.put("result", "000");
			} else {
				reJson.put("result", ExceptionCode.SIGN_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.returnCode(ExceptionCode.SERVER_ERROR);
		}

		/* 插入安全监控 */
		Map equ = inspectHelperDaoImpl.getEqu(equipmentId);
		baseMethodService.saveResLog(sn, OnlineDesignOperation.INS_SIGN, "1",
				String.valueOf(equ.get("EQUIPMENT_CODE")),
				String.valueOf(equ.get("EQUIPMENT_NAME")), "", "", "");
		return reJson.toString();
	}

	@Override
	public String getRes(String jsonStr) {
		String sn = "";
		String resId = "";
		String resNo = "";
		String resName = "";
		String resAddress = "";
		String lastPanelModyfiedTime = "";
		String lastCardModyfiedTime = "";
		String role = "";
		String startDate = "";
		String endDate = "";
		JSONArray reArray = new JSONArray();
		JSONObject reO = new JSONObject();
		JSONObject reJson = new JSONObject();
		try {
			/* 解析JSON参数 */
			JSONObject json = JSONObject.fromObject(jsonStr);
			String equipmentCode = json.getString("key");
			if (json.has("sn")) {
				sn = json.getString("sn");
			}
			if (equipmentCode.equals("")) {
				return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
			}
			Map param = new HashMap();
			param.put("equipmentCode", equipmentCode);
			List l = inspectHelperDaoImpl.getRes(param);
			for (int i = 0; i < l.size(); i++) {
				Map map = (Map) l.get(i);
				reO.put("resId", map.get("EQUIPMENT_ID"));
				reO.put("resName", map.get("EQUIPMENT_NAME"));
				reO.put("resNo", map.get("EQUIPMENT_CODE"));
				reO.put("resAddress", map.get("ADDRESS"));
				reO.put("resTypeId", map.get("RES_TYPE_ID"));
				reO.put("lastPanelModyfiedTime", "2012-12-26");
				reO.put("lastCardModyfiedTime", "2012-12-26");
				reArray.add(reO);
			}
			reJson.put("result", "000");
			reJson.put("res", reArray);
		} catch (Exception e) {
			/* 插入安全监控 */
			baseMethodService.saveResLog(sn, OnlineDesignOperation.FILT_RES,
					"0", "", "", "", "", "");

			e.printStackTrace();
			return Result.returnCode(ExceptionCode.SERVER_ERROR);

		}

		/* 插入安全监控 */
		baseMethodService.saveResLog(sn, OnlineDesignOperation.FILT_RES, "1",
				"", "", "", "", "");

		return reJson.toString();
	}
	
	
}
