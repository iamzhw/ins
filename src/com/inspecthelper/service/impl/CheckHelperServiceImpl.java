package com.inspecthelper.service.impl;

import icom.util.ExceptionCode;
import icom.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import util.dataSource.SwitchDataSourceUtil;

import com.inspecthelper.dao.DblinkDao;
import com.inspecthelper.dao.InspectHelperDao;
import com.inspecthelper.model.Dblink;
import com.inspecthelper.service.ICheckHelperService;
import com.system.service.BaseMethodService;
import com.util.DblinkUtil;
import com.util.OnlineDesignOperation;

@SuppressWarnings("all")
@Service
public class CheckHelperServiceImpl implements ICheckHelperService{

	@Resource
	private DblinkDao dblinkDaoImpl;
	
	@Resource
	private InspectHelperDao inspectHelperDao;
	
	@Resource
	private BaseMethodService baseMethodService;
	@Override
	public String getIntellCardInfo(String jsonStr) {
		String resNo = "";
		String resCode = "";
		String uNos = "";
		String no = "";
		String gName = "";
		String gNo = "";
		String linkId = "";
		String lName = "";
		String lNo = "";
		String state = "";
		String tqport = "";
		String sn = "";
		String obdCode = "";
		JSONObject reJson = new JSONObject();
		JSONArray reInfo = new JSONArray();
		JSONObject reO = new JSONObject();
		JSONObject reUInfo = new JSONObject();
		try {
			/* 解析JSON参数 */
			JSONObject json = JSONObject.fromObject(jsonStr);
			Dblink dblink = DblinkUtil.getDbLink(dblinkDaoImpl, jsonStr);
			String jndi = dblink.getJndi();
			if (json.has("sn")) {
				sn = json.getString("sn");
			}
			resNo = json.getString("resNo");
			uNos = json.getString("uNos");
			if (json.has("obdCode")) {
				obdCode = json.getString("obdCode");
			}
			// if(uNos.length() == 0){
			// return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
			// }
			if (uNos.length() > 0) {
				String[] uNoS = uNos.split(",");
				for (int j = 0; j < uNoS.length; j++) {
					String uNo = uNoS[j];
					Map param = new HashMap();
					param.put("uNo", uNo);
					param.put("resCode", resNo);
					param.put("dblinkUsername", dblink.getDblinkUsername());
					// param.put("dblinkUrl", dblink.getDblinkUrl());
					List l = new ArrayList();
					l = getContentByUno(param, jndi);
					JSONArray reContent = new JSONArray();
					for (int i = 0; i < l.size(); i++) {
						Map content = new HashMap();
						content = (Map) l.get(i);
						content.put("dblinkUsername", dblink
								.getDblinkUsername());
						// content.put("dblinkUrl", dblink.getDblinkUrl());

						List l1 = getJumpPortNo(content, jndi);

						if (l1.size() > 0) {
							tqport = (String) (((Map) l1.get(0))
									.get("JUMPPORTNO") == null ? "" : ((Map) l1
									.get(0)).get("JUMPPORTNO"));
							// if(tqport.contains("U")){
							// tqport = tqport.substring(2);
							// }
						} else {
							tqport = "";
						}
						uNo = (String) (content.get("UNO") == null ? ""
								: content.get("UNO"));
						no = (String) (content.get("NO") == null ? "" : content
								.get("NO"));
						gName = (String) (content.get("GNAME") == null ? ""
								: content.get("GNAME"));
						gNo = (String) (content.get("GNO") == null ? ""
								: content.get("GNO"));
						linkId = (String) (content.get("LINK_ID") == null ? ""
								: content.get("LINK_ID"));
						lName = (String) (content.get("LNAME") == null ? ""
								: content.get("LNAME"));
						lNo = (String) (content.get("LNO") == null ? ""
								: content.get("LNO"));
						// tqport =
						// (String)(content.get("TQPORT")==null?"":content.get("TQPORT"));
						state = (String) (content.get("STATE") == null ? ""
								: content.get("STATE"));
						reO.put("uNo", uNo);
						reO.put("port", no);
						reO.put("gName", gName);
						reO.put("gNo", gNo);
						reO.put("linkId", linkId);
						reO.put("lName", lName);
						reO.put("lNo", lNo);
						reO.put("tqport", tqport);
						reO.put("state", state);
						reContent.add(reO);
					}
					reUInfo.put("uNo", uNo);
					reUInfo.put("detail", reContent);
					reInfo.add(reUInfo);
				}
			} else if (obdCode.length() > 0) {
				String[] obdCodes = obdCode.split(",");
				for (int j = 0; j < obdCodes.length; j++) {
					String uNo = obdCodes[j];
					reUInfo.put("uNo", uNo);
					Map param = new HashMap();
					param.put("resNo", obdCodes[j]);
					param.put("dblinkUsername", dblink.getDblinkUsername());
					// param.put("dblinkUrl", dblink.getDblinkUrl());
					List l = new ArrayList();
					l = getOBDContentByUno(param, jndi);
					JSONArray reContent = new JSONArray();
					for (int i = 0; i < l.size(); i++) {
						Map content = new HashMap();
						content = (Map) l.get(i);
						content.put("dblinkUsername", dblink
								.getDblinkUsername());
						List l1 = getJumpPortNo(content, jndi);
						if (l1.size() > 0) {
							tqport = (String) (((Map) l1.get(0))
									.get("JUMPPORTNO") == null ? "" : ((Map) l1
									.get(0)).get("JUMPPORTNO"));
							if (tqport.contains("U")) {
								tqport = tqport.substring(2);
							}
						} else {
							tqport = "";
						}
						uNo = (String) (content.get("UNO") == null ? ""
								: content.get("UNO"));
						no = (String) (content.get("NO") == null ? "" : content
								.get("NO"));
						gName = (String) (content.get("GNAME") == null ? ""
								: content.get("GNAME"));
						gNo = (String) (content.get("GNO") == null ? ""
								: content.get("GNO"));
						linkId = (String) (content.get("LINK_ID") == null ? ""
								: content.get("LINK_ID"));
						lName = (String) (content.get("LNAME") == null ? ""
								: content.get("LNAME"));
						lNo = (String) (content.get("LNO") == null ? ""
								: content.get("LNO"));
						// tqport =
						// (String)(content.get("TQPORT")==null?"":content.get("TQPORT"));
						state = (String) (content.get("STATE") == null ? ""
								: content.get("STATE"));
						reO.put("uNo", uNo);
						reO.put("port", no);
						reO.put("gName", gName);
						reO.put("gNo", gNo);
						reO.put("linkId", linkId);
						reO.put("lName", lName);
						reO.put("lNo", lNo);
						reO.put("tqport", tqport);
						reO.put("state", state);
						reContent.add(reO);
					}
					// reUInfo.put("uNo", uNo);
					reUInfo.put("detail", reContent);
					reInfo.add(reUInfo);
				}
			}
			reJson.put("result", "000");
			reJson.put("info", reInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.returnCode(ExceptionCode.SERVER_ERROR);
		}

		/* 插入安全监控 */
		baseMethodService.saveResLog(sn,
				OnlineDesignOperation.INS_INTELLIGENCE_CARD, "1", "", "", "",
				"", resNo);

		return reJson.toString();
	}

	private List getContentByUno(Map param, String jndi) {

		List l = new ArrayList();
		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			l = inspectHelperDao.getContentByUno(param);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();

		}
		return l;
	}
	
	private List getJumpPortNo(Map param, String jndi) {
		List l = new ArrayList();

		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			l = inspectHelperDao.getJumpPortNo(param);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();

		}

		return l;
	}
	
	private List getOBDContentByUno(Map param, String jndi) {

		List l = new ArrayList();

		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			l = inspectHelperDao.getOBDContentByUno(param);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();

		}
		return l;

	}
}
