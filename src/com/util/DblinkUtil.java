package com.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.inspecthelper.dao.DblinkDao;
import com.inspecthelper.model.Dblink;

//no-use
@SuppressWarnings("all")
public class DblinkUtil {

	public static Dblink getDbLink(DblinkDao dblinkDaoImpl, String jsonStr) {
		// 通过jsonStr查找dblink
		JSONObject orgJson = new JSONObject().fromObject(jsonStr);
		//JSONObject json_ = new JSONObject(parameter);
		//JSONObject json = new JSONObject(parameter);
		//rwmxid = json.getString("rwmxid");
		HashMap<String, Object> hm = new HashMap<String, Object>();
		// 根据用户id查找dblink
		String user_id = "";
		if(orgJson.has("staffId")){
			user_id = orgJson.get("staffId").toString();
		}else{
			user_id = orgJson.get("staff_id").toString();
		}
		hm.put("USER_ID", user_id);
		List<Map<String, Object>> dblinkResult = dblinkDaoImpl.getDblinkContainsPro(hm);
		//得到一行值63连云港的
		Dblink dblink = new Dblink();
		dblink.setDblinkUsername(dblinkResult.get(0).get("DBLINKUSERNAME")
				.toString());//reslyg
		dblink.setDblinkUrl(dblinkResult.get(0).get("DBLINKURL").toString());//reslyg
		dblink.setJndi(dblinkResult.get(0).get("JNDI").toString());//reslyg

		return dblink;
	}
	
	
	public static Dblink getDbLinkByAreaId(DblinkDao dblinkDaoImpl, String jsonStr) {
		// 通过jsonStr查找dblink
		JSONObject orgJson = new JSONObject().fromObject(jsonStr);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		// 根据用户id查找dblink
		hm.put("AREA_ID", orgJson.get("areaId").toString());
		List<Map<String, Object>> dblinkResult = dblinkDaoImpl.getDbLinkByAreaId(hm);
		if(dblinkResult.size() > 0){
			Dblink dblink = new Dblink();
			dblink.setDblinkUsername(dblinkResult.get(0).get("DBLINKUSERNAME")
					.toString());
			dblink.setDblinkUrl(dblinkResult.get(0).get("DBLINKURL").toString());
			dblink.setJndi(dblinkResult.get(0).get("JNDI").toString());
			return dblink;
		}
		return null;
	}

//	public static String getJNDI(DblinkDaoImpl dblinkDaoImpl, String jsonStr) {
//		// 通过jsonStr查找dblink
//		JSONObject orgJson = new JSONObject().fromObject(jsonStr);
//		HashMap<String, String> hm = new HashMap<String, String>();
//		// 根据用户id查找dblink
//		hm.put("USER_ID", orgJson.get("staffId").toString());
//		List<HashMap> res = dblinkDaoImpl.getJNDI(hm);
//		return res.get(0).get("JNDI").toString();
//	}

	// 含有面板图调用的那个存储过程的sqlkey
	public static Dblink getDbLinkContainsPro(DblinkDao dblinkDaoImpl,
			String jsonStr) {
		// 通过jsonStr查找dblink
		JSONObject orgJson = new JSONObject().fromObject(jsonStr);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		// 根据用户id查找dblink
		hm.put("USER_ID", orgJson.get("staffId").toString());
		List<Map<String, Object>> dblinkResult = dblinkDaoImpl.getDblinkContainsPro(hm);
		Dblink dblink = new Dblink();
		dblink.setDblinkUsername(dblinkResult.get(0).get("DBLINKUSERNAME")
				.toString());
		dblink.setDblinkUrl(dblinkResult.get(0).get("DBLINKURL").toString());
		Object name = dblinkResult.get(0).get("PROCEDURENAME");
		String proName = "";
		if(name!=null){
			proName= name.toString();
		}
		dblink.setProcedureName(proName);
		dblink.setJndi(dblinkResult.get(0).get("JNDI").toString());

		return dblink;
	}

	// 含有面板图调用的那个存储过程的sqlkey
	public static Dblink getDbLinkByStaffId(DblinkDao dblinkDaoImpl,
			String staffId) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		// 根据用户id查找dblink
		hm.put("USER_ID", staffId);
		List<Map<String, Object>> dblinkResult = dblinkDaoImpl.getDblinkContainsPro(hm);
		Dblink dblink = new Dblink();
		dblink.setDblinkUsername(dblinkResult.get(0).get("DBLINKUSERNAME")
				.toString());
		dblink.setDblinkUrl(dblinkResult.get(0).get("DBLINKURL").toString());
		
		dblink.setJndi(dblinkResult.get(0).get("JNDI").toString());

		// dblink.setProcedureName(dblinkResult.get(0).get("PROCEDURENAME").toString());
		return dblink;
	}

	public static Dblink getDbLinkByStaffNo(DblinkDao dblinkDaoImpl,
			String staffNo) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		// 根据用户id查找dblink
		hm.put("USER_ID", staffNo);
		List<Map<String, Object>> dblinkResult = dblinkDaoImpl.getDbLinkByStaffNo(hm);
		if(dblinkResult.size()>0){
			Dblink dblink = new Dblink();
			dblink.setDblinkUsername(dblinkResult.get(0).get("DBLINKUSERNAME")
					.toString());
			dblink.setDblinkUrl(dblinkResult.get(0).get("DBLINKURL").toString());
			dblink.setJndi(dblinkResult.get(0).get("JNDI").toString());

			// dblink.setProcedureName(dblinkResult.get(0).get("PROCEDURENAME").toString());
			return dblink;
		}else{
			return null;
		}
		
	}

}
