package icom.axx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linePatrol.util.DateUtil;
import com.outsite.dao.MainOutSiteDao;
import com.system.service.ParamService;

@Repository
public class GuardTrackExecutor {

	@Autowired
	private ParamService paramService;

	@Resource
	private MainOutSiteDao mainOutSiteDao;

	public void saveGuardTrack(String jsonStr) {
		GuardTrackThread t = new GuardTrackThread(jsonStr);
		new Thread(t).start();
	}
	
	
	/**
	 * 
	 * @param latLngs:电子围栏的坐标集合
	 * @param latLng:坐标点
	 * @return:坐标点在电子围栏内，返回true；反之，返回false
	 */
	public static Boolean contains(List<Map<String, Double>> latLngs, Map<String, Double> latLng) {
		Boolean result = false;
		int size = latLngs.size();
		for (int i = 0, j = size - 1; i < size; j = i++) {
			Map<String, Double> beforeLatLng = latLngs.get(i);
			Map<String, Double> afterLatLng = latLngs.get(j);
			if ((beforeLatLng.get("longitude") < latLng.get("longitude") && afterLatLng.get("longitude") > latLng.get("longitude"))
					|| afterLatLng.get("longitude") < latLng.get("longitude")
					&& beforeLatLng.get("longitude") >= latLng.get("longitude")) {
				if (beforeLatLng.get("latitude") + (latLng.get("longitude") - beforeLatLng.get("longitude"))
						/ (afterLatLng.get("longitude") - beforeLatLng.get("longitude"))
						* (afterLatLng.get("latitude") - beforeLatLng.get("latitude")) < latLng.get("latitude")) {
					result=!result;
				}
			}
		}
		return result;
	}
	
	


	class GuardTrackThread implements Runnable {

		String jsonStr;

		public GuardTrackThread(String jsonStr) {
			this.jsonStr = jsonStr;
		};

		@Override
		public void run() {
			try {
				JSONObject jb=JSONObject.fromObject(jsonStr);
				String userId = jb.getString("userId");
				String sn = jb.getString("sn");
				String parent_city = jb.getString("parent_city");
				JSONArray job = JSONArray.fromObject(jb.get("planList"));
				for (Object object : job) {
					JSONObject jsonObject = JSONObject.fromObject(object);
					String plan_id = jsonObject.getString("plan_id");
					String is_gps = jsonObject.getString("is_gps");
					String gps_switch = jsonObject.getString("gps_switch");
					String x = jsonObject.getString("x");
					String y = jsonObject.getString("y");
					
					String is_guard="0";
					List<Map<String, Double>> latLngs = (List<Map<String, Double>>) jsonObject.get("is_guard");
					Map<String, Double> latLng =new HashMap<String, Double>();
					latLng.put("longitude", Double.valueOf(x));
					latLng.put("latitude", Double.valueOf(y));
					if(GuardTrackExecutor.contains(latLngs, latLng)){
						is_guard="1";
					}
					
					if ("4.9E-324".equals(x) || "4.9E-324".equals(y)) {//过滤无信号数据
						x = "0";
						y = "0";
					}
					String plan_time = jsonObject.getString("plan_time");
//					String is_guard = jsonObject.getString("is_guard");
					String track_time = jsonObject.getString("track_time");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("plan_id", plan_id);
					map.put("x", x);
					map.put("y", y);
					map.put("is_gps", is_gps);
					map.put("gps_switch", gps_switch);
					map.put("plan_time", plan_time);
					map.put("parent_city", parent_city);
					map.put("creation_time", DateUtil.getDateAndTime());
					map.put("created_by", userId);
					map.put("sn", sn);
					map.put("is_guard", is_guard);
					map.put("track_time", track_time);
					mainOutSiteDao.saveOsProtectCoordinate(map);
				}
			} catch (Exception e) {
				paramService.saveLogInfo("GuardTrackThread", "saveOsProtectCoordinate(String jsonStr)", "ERROR", jsonStr);
			}
		}
	}
}
