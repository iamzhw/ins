package com.inspecthelper.service.impl;

import icom.util.ExceptionCode;
import icom.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.dataSource.SwitchDataSourceUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.inspecthelper.dao.DblinkDao;
import com.inspecthelper.dao.DesignOrderDaoImpl;
import com.inspecthelper.model.Dblink;
import com.inspecthelper.service.IDesignOrderService;
import com.system.service.BaseMethodService;
import com.util.DblinkUtil;
import com.util.OnlineDesignOperation;

@Service
@SuppressWarnings("all")
public class DesignOrderServiceImpl implements IDesignOrderService{

	@Resource
	private DblinkDao dblinkDaoImpl;
	
	@Resource
	private OssAbitiyServiceImpl ossAbitiyServiceImpl;
	@Resource
	private DesignOrderDaoImpl designOrderDaoImpl; // 查询实现类
	@Resource
	private BaseMethodService baseMethodService;
	@Override
	public String getPhyEqpAndCblSectionInfo(String param) {
		// param =
		// "{\"areaCode\":\"区域编码\",\"cblSectId\":\"123\",\"cblSecType\":\"123\",\"user_id\":\"10\",\"resCode\":\"10\",\"resSpecId\":\"10\",\"sn\":\"10\"}";
		// syc
		Dblink dblink = DblinkUtil.getDbLink(dblinkDaoImpl, param);
		String jndi = dblink.getJndi();
		// String param =
		// "{\"areaCode\":\"sq.sz.js.cn\",\"resSpecId\":\"703\",\"resCode\":\"512SZ.GBY\"}";
		JSONObject json = JSONObject.fromObject(param);
		// 测试使
		String resCode = json.getString("resCode");
		String resSpecId = json.getString("resSpecId");
		String areaCode = json.getString("areaCode");
		String sn = json.getString("sn");
		String staffNo = "";
		if (json.has("staffNo")) {
			staffNo = json.getString("staffNo");
		}

		JSONObject resultJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if (resCode == null || areaCode == null || resSpecId == null) {
			resultJson.put("resInfo", jsonArray);
			resultJson.put("result", "000");
			resultJson.put("codeDesc", "成功");
			return resultJson.toString();
		}
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("areaCode", areaCode);
		hm.put("resCode", resCode);
		hm.put("resSpecId", resSpecId);
		// syc
		hm.put("dblinkUsername", dblink.getDblinkUsername());
		// hm.put("dblinkUrl", dblink.getDblinkUrl());
		List<HashMap> resultList = new ArrayList<HashMap>();
		if (resSpecId.equals("701") || resSpecId.equals("801")) {
			resultList = getCblSectionInfo(hm, jndi);
		} else if (resSpecId.equals("-701")) {
			hm.put("resSpecId", "701");
			resultList = getCblSectionInfo(hm, jndi);
		} else if (resSpecId.equals("-801")) {
			hm.put("resSpecId", "801");
			resultList = getCblSectionInfo(hm, jndi);
		} else {
			resultList = getPhyEqpInfo(hm, jndi);
		}
		if (resultList != null && resultList.size() > 0) {
			int size = resultList.size();
			for (int i = 0; i < size; i++) {
				JSONObject optAttri = new JSONObject();
				if (resultList.get(i).get("RESID") != null) {
					optAttri.put("resId", resultList.get(i).get("RESID")
							.toString());
				} else {
					optAttri.put("resId", "");
				}
				if (resultList.get(i).get("RESSPECID") != null) {
					optAttri.put("resSpecId", resultList.get(i)
							.get("RESSPECID").toString());
				} else {
					optAttri.put("resSpecId", "");
				}
				if (resultList.get(i).get("RESSPECNAME") != null) {
					optAttri.put("resSpecName",
							resultList.get(i).get("RESSPECNAME").toString());
				} else {
					optAttri.put("resSpecName", "");
				}
				if (resultList.get(i).get("RESNO") != null) {
					optAttri.put("resNo", resultList.get(i).get("RESNO")
							.toString());
				} else {
					optAttri.put("resNo", "");
				}
				if (resultList.get(i).get("RESNAME") != null) {
					optAttri.put("resName", resultList.get(i).get("RESNAME")
							.toString());
				} else {
					optAttri.put("resName", "");
				}
				if (resultList.get(i).containsKey("TMLNAME")
						&& resultList.get(i).get("TMLNAME") != null) {
					optAttri.put("tmlName", resultList.get(i).get("TMLNAME")
							.toString());
				} else {
					optAttri.put("tmlName", "");
				}
				if (resultList.get(i).containsKey("LENGTH")
						&& resultList.get(i).get("LENGTH") != null) {
					optAttri.put("length", resultList.get(i).get("LENGTH")
							.toString());
				} else {
					optAttri.put("length", "");
				}
				if (resultList.get(i).containsKey("CAPACITY")
						&& resultList.get(i).get("CAPACITY") != null) {
					optAttri.put("capacity", resultList.get(i).get("CAPACITY")
							.toString());
				} else {
					optAttri.put("capacity", "");
				}
				if (resultList.get(i).get("RESMODEL") != null) {
					optAttri.put("resModel", resultList.get(i).get("RESMODEL")
							.toString());
				} else {
					optAttri.put("resModel", "");
				}
				if (resultList.get(i).get("CREATETIME") != null) {
					optAttri.put("createTime",
							resultList.get(i).get("CREATETIME").toString());
				} else {
					optAttri.put("createTime", "");
				}
				if (resultList.get(i).get("RESOPRSTATE") != null) {
					optAttri.put("resOprState",
							resultList.get(i).get("RESOPRSTATE").toString());
				} else {
					optAttri.put("resOprState", "");
				}
				if (resultList.get(i).get("RESMNTSTATE") != null) {
					optAttri.put("resMntState",
							resultList.get(i).get("RESMNTSTATE").toString());
				} else {
					optAttri.put("resMntState", "");
				}
				if (resultList.get(i).get("ADDRESSNAME") != null) {
					optAttri.put("addressName",
							resultList.get(i).get("ADDRESSNAME").toString());
				} else {
					optAttri.put("addressName", "");
				}
				if (resultList.get(i).get("AREANAME") != null) {
					optAttri.put("areaName", resultList.get(i).get("AREANAME")
							.toString());
				} else {
					optAttri.put("areaName", "");
				}
				if (resultList.get(i).get("CREATOR") != null) {
					optAttri.put("creator", resultList.get(i).get("CREATOR")
							.toString());
				} else {
					optAttri.put("creator", "");
				}
				if (resultList.get(i).get("MODIFYPERSON") != null) {
					optAttri.put("modifyPerson",
							resultList.get(i).get("MODIFYPERSON").toString());
				} else {
					optAttri.put("modifyPerson", "");
				}
				if (resultList.get(i).get("MODIFYTIME") != null) {
					optAttri.put("modityTime",
							resultList.get(i).get("MODIFYTIME").toString());
				} else {
					optAttri.put("modityTime", "");
				}
				if (resultList.get(i).get("WORKORDERID") != null) {
					optAttri.put("workOrderId",
							resultList.get(i).get("WORKORDERID").toString());
				} else {
					optAttri.put("workOrderId", "");
				}
				String pointsA = ossAbitiyServiceImpl.getResPointsFromESB(
						areaCode, optAttri.get("resId").toString(), optAttri
								.get("resSpecId").toString());
				optAttri.put("points", pointsA);
				jsonArray.add(optAttri);

				try {
					/* 资源监控 */
					baseMethodService.saveResLog(sn,
							OnlineDesignOperation.RES_REGISTER, "1", "",
							resultList.get(i).get("RESNO").toString(),
							resultList.get(i).get("RESNAME").toString(), "",
							resCode);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}
		resultJson.put("resInfo", jsonArray);
		resultJson.put("result", "000");
		resultJson.put("codeDesc", "成功");

		if ("703".equals(resSpecId)) {
			baseMethodService.saveRegisterLog(sn,
					OnlineDesignOperation.SEARCH_GJJX, "1", "", staffNo);
		} else if ("704".equals(resSpecId)) {
			baseMethodService.saveRegisterLog(sn,
					OnlineDesignOperation.SEARCH_GFXX, "1", "", staffNo);
		} else if ("744".equals(resSpecId)) {
			baseMethodService.saveRegisterLog(sn,
					OnlineDesignOperation.SEARCH_GZDH, "1", "", staffNo);
		} else if ("803".equals(resSpecId)) {
			baseMethodService.saveRegisterLog(sn,
					OnlineDesignOperation.SEARCH_DJJX, "1", "", staffNo);
		} else if ("804".equals(resSpecId)) {
			baseMethodService.saveRegisterLog(sn,
					OnlineDesignOperation.SEARCH_DFXH, "1", "", staffNo);
		} else if ("414".equals(resSpecId)) {
			baseMethodService.saveRegisterLog(sn,
					OnlineDesignOperation.SEARCH_ZHX, "1", "", staffNo);
		} else if ("201".equals(resSpecId)) {
			baseMethodService.saveRegisterLog(sn,
					OnlineDesignOperation.SEARCH_STATION, "1", "", staffNo);
		}

		return resultJson.toString();
	}

	@Override
	public String getPhyEqpUnitList(String param) {
		// syc
		// resNo
		Dblink dblink = DblinkUtil.getDbLinkContainsPro(dblinkDaoImpl, param);
		String resSpecId = "";
		String resCode = "";
		String sn = "";
		String staffNo = "";
		String returnJson = null;
		Map<String, String> map = new HashMap<String, String>();
		JSONObject json = JSONObject.fromObject(param);
		JSONObject reJson = new JSONObject();
		JSONArray resultList = new JSONArray();
		resSpecId = json.getString("resSpecId").toString();
		resCode = json.getString("resCode").toString();
		if (json.has("staffNo")) {
			staffNo = json.getString("staffNo");
		}
		sn = json.getString("sn");
		if (resCode.equals("") || resSpecId.equals("")) {
			// "1002"; // 客户端参数错误,或客户端参数错误或不完整
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
		}
		map.put("AREACODE", "");
		map.put("RESCODE", resCode);
		map.put("RESSPECID", resSpecId);
		// syc
		map.put("DBLINKUSERNAME", dblink.getDblinkUsername());
		// map.put("DBLINKURL", dblink.getDblinkUrl());
		// 查找dblink的时候把调用哪个存储过程也查出来

		List<HashMap> list = getGjPanel(dblink.getJndi(), map);

		//
		// String procedure = designOrderDaoImpl.getPadPanelProcedure(dblink
		// .getProcedureName(), map);
		// if (procedure.equals("success")) {
		// list = designOrderDaoImpl.getPhyEqpUnitList(map);
		// } else {
		// reJson.put("result", "000");
		// reJson.put("body", resultList);
		// return reJson.toString();
		// }
		// list = designOrderDaoImpl.getPhyEqpUnitList(map);
		HashMap<String, String> reMap = new HashMap<String, String>();
		JSONObject result = new JSONObject();
		String unitId = "";
		if (list != null && list.size() > 0) {
			int size = list.size();
			// 标记一下unitId 下面好删除
			unitId = list.get(0).get("UNITID").toString();
			for (int i = 0; i < size; i++) {
				reMap = list.get(i);

				if (reMap.get("UNO") != null) {
					result.put("uNo", reMap.get("UNO"));
				} else {
					result.put("uNo", "");
				}
				if (reMap.get("UNITID") != null) {
					result.put("unitId", reMap.get("UNITID"));
					unitId = unitId + "," + String.valueOf(reMap.get("UNITID"));
				} else {
					result.put("unitId", "");
				}

				if (reMap.get("UPORT") != null) {
					result.put("uPort", reMap.get("UPORT"));
				} else {
					result.put("uPort", "");
				}
				if (reMap.get("CABLEID") != null) {
					result.put("cableId", reMap.get("CABLEID"));
				} else {
					result.put("cableId", "");
				}
				if (reMap.get("CABLENAME") != null) {
					result.put("cableName",
							reMap.get("CABLENAME").replace("[]", ""));
				} else {
					result.put("cableName", "");
				}
				if (reMap.get("QPORT") != null) {
					if (reMap.get("QPORT").equals("-")) {
						result.put("qPort", "");
					} else {
						result.put("qPort", reMap.get("QPORT"));
					}
				} else {
					result.put("qPort", "");
				}
				resultList.add(result);
			}
		}
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("unitId", unitId);
		// designOrderDaoImpl.deletePhyEqpUnitList(hm);
		reJson.put("result", "000");
		reJson.put("body", resultList);
		baseMethodService.saveRegisterLog(sn,
				OnlineDesignOperation.CHECK_GJJX_LIST, "1", "", staffNo);
		return reJson.toString();
	}

	/**
	 * dbLink 查询数据中光电缆段信息 根据设备编码 设备类型以及区域编码 封装成XML格式 获取OSS设备信息 包括到dblink
	 * 数据库去查光/电缆段信息 提供方：OSS资源配置方
	 * 
	 * @author Fanjiwei
	 * @create_date 2012-07-01
	 *              json===={"resultInfo":{"returnCode":"000","codeDesc"
	 *              :"执行成功"},
	 *              "resInfo":[{"resId":"12320000000556","resName":"平望实验小学光交"
	 *              ,"resOprState":[],
	 *              "resMntState":"正常","resNo":"512WJ.PWJMJ/GJ011"
	 *              ,"resSpecId":"703",
	 *              "createTime":"Mon May 07 11:27:53 CST 2012"
	 *              ,"tmlName":"平望局平望局",
	 *              "resModel":"光交机架模板(此型号非标准型号或被归并，下次删除)","resXPoint"
	 *              :"0.0","resYPoint":"0.0",
	 *              "creator":[],"modifyPerson":"1101",
	 *              "modityTime":"Fri May 18 09:52:11 CST 2012"
	 *              ,"workOrderId":[]}]}
	 * 
	 */
	private List getCblSectionInfo(HashMap hm, String jndi) {

		List l = new ArrayList();
		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			l = designOrderDaoImpl.getCblSectionInfo(hm);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		} finally {
			return l;
		}

	}
	
	private List getPhyEqpInfo(HashMap hm, String jndi) {

		List l = new ArrayList();
		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			l = designOrderDaoImpl.getPhyEqpInfo(hm);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		} finally {
			return l;
		}

	}
	private List getGjPanel(String jndi, Map param) {
		List l = new ArrayList();
		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			/*l = designOrderDaoImpl.getPadPanelProcedure(
					"DesignOrder.getPadPanelProcedure", param);*/
			designOrderDaoImpl.getPadPanelProcedure(param);
			l=designOrderDaoImpl.getPhyEqpUnitList(param);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		} finally {
			return l;
		}

	}
}
