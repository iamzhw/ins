package com.inspecthelper.service.impl;

import icom.util.ExceptionCode;
import icom.util.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inspecthelper.dao.CheckOdfDaoImpl;
import com.inspecthelper.dao.DblinkDao;
import com.inspecthelper.dao.InspectHelperDao;
import com.inspecthelper.service.ICheckOdfService;
import com.inspecthelper.service.IInspectService;
import com.system.service.BaseMethodService;


/**
 * @author sswang
 * 
 */

@SuppressWarnings("all")
@Transactional
@Service
public class CheckOdfServiceImpl implements ICheckOdfService {

	@Resource
	private InspectHelperDao inspectHelperDaoImpl;
	@Resource
	private CheckOdfDaoImpl checkOdfDaoImpl;
	@Resource
	private BaseMethodService baseMethodService;
	@Resource
	private DblinkDao dblinkDaoImpl;
	@Resource
	private IInspectService inspectServiceImpl;
	private static final String ODF_A = "14";// ODF1级检查人员角色ID
	private static final String ODF_B = "15";
	private static final String ODF_C = "16";
	private static final String ODF_D = "17";
	private JSONObject getJSONObject(HashMap obj, String[] cols) {
		try {
			JSONObject item = new JSONObject();
			for (int j = 0; j < cols.length; j++) {
				Object value = obj.get(cols[j]);
				if (value == null) {
					value = "";
				}
				if (value instanceof Date) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String dateStr = sdf.format(value);
					item.put(cols[j].toLowerCase(), dateStr);
				} else {
					item.put(cols[j].toLowerCase(), value);
				}
			}
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	
	public String getResInfo(String jsonStr) {
		try {
//			Dblink dblink = DblinkUtil.getDbLink(dblinkDaoImpl, jsonStr);
			JSONObject json = new JSONObject();
			json=json.fromObject(jsonStr);
			String staffId = json.getString("staffId");
			String key = json.getString("key");
//			String staffNo = json.getString("staffNo");
//			String areaId = json.getString("areaId");
//			String sn = json.getString("sn");
			String startDate = json.getString("startDate");
			String endDate = json.getString("endDate");
			String staffNo = json.getString("staffNo");
			HashMap<String, String> hashMap = new HashMap<String, String>();
//			hashMap.put("DBLINKUSERNAME", dblink.getDblinkUsername());
//			hashMap.put("DBLINKURL", dblink.getDblinkUrl());
			hashMap.put("key", key.toUpperCase());
			hashMap.put("startDate", startDate);
			hashMap.put("endDate", endDate);
			hashMap.put("staffId", staffId);
			String role = inspectServiceImpl.getODFStaffRole(staffNo);
			if(role.equals(ODF_A)){
				hashMap.put("state_", "state_a");
				hashMap.put("staff_a", staffId);
			}else if(role.equals(ODF_B)){
				hashMap.put("state_", "state_b");
				hashMap.put("staff_b", staffId);
			}else if(role.equals(ODF_C)){
				hashMap.put("state_", "state_c");
			}else if(role.equals(ODF_D)){
				hashMap.put("state_", "state_d");
			}
			List<HashMap> result = checkOdfDaoImpl.getResInfo(hashMap);
			if (result == null || result.size() == 0) {
				return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
			}else {
				String resId = result.get(0).get("RESID").toString();
				String resNo = (String) result.get(0).get("RESNO");
				String resName = result.get(0).get("RESNAME").toString();
				String resTypeId = (String) result.get(0).get("RESTYPEID");
				String[] cols = new String[] { "ID", "DK", "YGLBM", "DTSJ","GLSTATE","SSGL","COMPAREDATE" };
				JSONObject resultJson = new JSONObject();
				resultJson.put("result", "000");
				JSONArray body = new JSONArray();
				int count = 0;
				JSONArray innerArray = new JSONArray();
				JSONObject innerJson = new JSONObject();
				for (int i = 0; i < result.size(); i++) {
					String vsbid = result.get(i).get("RESID").toString();
				
					if (resId.equals(vsbid)) {
						if (count == 0) {
							innerJson.put("resId", resId);
							innerJson.put("resNo", resNo);
							innerJson.put("resName", resName);
							innerJson.put("resTypeId", resTypeId);
							count = 1;
						}
						JSONObject jo = getJSONObject(result.get(i), cols);
						innerArray.add(jo);
						if (i == result.size() - 1) {
							innerJson.put("data", innerArray);
							body.add(innerJson);
						}
					}else {
						innerJson.put("data", innerArray);
						body.add(innerJson);
						resId = vsbid;
						resNo = (String) result.get(i).get("RESNO");
						resName = result.get(i).get("RESNAME").toString();
						resTypeId = (String) result.get(i).get("RESTYPEID");
						count = 0;
						innerJson = new JSONObject();
						innerArray = new JSONArray();
						if (count == 0) {
							innerJson.put("resId", resId);
							innerJson.put("resNo", resNo);
							innerJson.put("resName", resName);
							innerJson.put("resTypeId", resTypeId);
							count = 1;
						}
						JSONObject jo = getJSONObject(result.get(i), cols);
						innerArray.add(jo);
						if (i == result.size() - 1) {
							innerJson.put("data", innerArray);
							body.add(innerJson);
						}
					}
				}
				resultJson.put("body", body);
				return resultJson.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
