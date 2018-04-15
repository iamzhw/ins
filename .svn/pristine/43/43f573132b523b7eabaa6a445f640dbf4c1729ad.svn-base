package icom.axx.service.impl;

import icom.axx.dao.LineSiteInterfaceDao;
import icom.axx.service.LineSiteInterfaceService;
import icom.util.Result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.linePatrol.util.DateUtil;
import com.system.service.ParamService;
import com.system.sys.OnlineUserListener;
import com.util.MapDistance;

@Service
public class CustomLineSiteInterfaceServiceImpl {
//
//	@Resource
//	private LineSiteInterfaceDao lineSiteInterfaceDao;
//
//	@Resource
//	private ParamService paramService;
//	
//	@Override
//	public String saveAutoTrack(String jsonStr) {
//
//		JSONObject result = new JSONObject();
//		try{
//			
//			JSONObject json = JSONObject.fromObject(jsonStr);
//			
//			String userId = json.getString("userId");// 用户ID
//			String areaId = json.getString("area_id");// 区域ID
//			String uploadTime = json.getString("upload_time");// 上传时间
//			JSONArray trackArray = json.getJSONArray("trackList");// 获取轨迹点集合
//			
//			Set<JSONObject> set = new TreeSet<JSONObject>();
//			for(int i=0;i<trackArray.size();i++){
//				set.add(trackArray.getJSONObject(i));
//			}
//			
//			for(Iterator<JSONObject> iterator = set.iterator();iterator.hasNext();){
//				Map<String, Object> track = analysisTrack(iterator.next(), userId, areaId, uploadTime, "0");
//				lineSiteInterfaceDao.saveAutoTrack(track);// 保存自动上传轨迹
//			}
//			
//		}catch(Exception e){
//			result.put("result", "001");
//			result.put("error", e.toString());
//		}
//
//		result.put("result", "000");
//		return result.toString();
//
//	}
//
//	@Override
//	public String saveRegisterSite(String jsonStr) {
//
//		JSONObject json = JSONObject.fromObject(jsonStr);
//		
//		String userId = json.getString("userId");// 用户ID
//		String sn = json.getString("sn");// 用户ID
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}
//
//		// 初始化结果json对象
//		JSONObject result = new JSONObject();
//
//		try {
//			String areaId = json.getString("area_id");// 区域ID
//			String site_id = json.getString("site_id");// 关键点
//			String site_name = json.getString("site_name");// 关键点名称
//			String distance = json.getString("distance");// 到关键点距离
//			String longitude = json.getString("longitude");// 经度
//			String latitude = json.getString("latitude");// 纬度
//			String sign_time = json.getString("sign_time");// 签到时间
//			String remark = json.getString("remark");// 文字说明
//
//			int registerId = lineSiteInterfaceDao.getRegisterId();// 生成签到点ID
//
//			Map<String, Object> registerMap = new HashMap<String, Object>();
//			registerMap.put("SIGN_ID", registerId);// 签到点ID
//			registerMap.put("USER_ID", userId);// 用户ID
//			registerMap.put("AREA_ID", areaId);// 区域ID
//			registerMap.put("LONGITUDE", longitude);// 经度
//			registerMap.put("LATITUDE", latitude);// 纬度
//			registerMap.put("SIGN_TIME", sign_time);// 签到时间
//			lineSiteInterfaceDao.saveRegisterSite(registerMap);// 保存签到信息
//
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("USER_ID", userId);
//			params.put("SITE_ID", site_id);
//			List<Map<String, Object>> taskSiteList = lineSiteInterfaceDao.getTaskSitesByUserAndSite(params);// 根据人员查询当天所有任务关联的未签到关键点
//
//			for (Map<String, Object> taskSite : taskSiteList) {
//				Map<String, Object> matchSite = new HashMap<String, Object>();
//				matchSite.put("MATCH_ID", lineSiteInterfaceDao.getSiteMatchId());// 站点匹配ID
//				matchSite.put("SIGN_ID", registerId);
//				matchSite.put("MATCH_TIME", sign_time);// 匹配时间
//				matchSite.put("SITE_TYPE", "1");// 点类型：关键点，非关键点
//				matchSite.put("SITE_ID", site_id);// 点ID
//				matchSite.put("SITE_NAME", site_name);// 点名称
//				matchSite.put("SITE_DISTANCE", distance);// 匹配距离
//				matchSite.put("TASK_ID", taskSite.get("TASK_ID"));// 任务ID
//				matchSite.put("REMARK", remark);// 备注信息
//
//				lineSiteInterfaceDao.saveMatchSite(matchSite);// 保存匹配的到站点信息
//			}
//
//			result.put("site_id", site_id);
//			result.put("result", "000");
//		} catch (Exception e) {
//			result.put("result", "001");
//			result.put("error", e.toString());
//
//			System.out.println(e.toString());
//		}
//
//		return result.toString();
//	}
//
//	public Map<String, Object> analysisTrack(JSONObject track, String userId, String areaId, String uploadTime,
//			String trackType) {
//
//		int trackId = lineSiteInterfaceDao.getTrackId();// 生成轨迹点主键
//
//		Map<String, Object> trackMap = new HashMap<String, Object>();
//		trackMap.put("TRACK_ID", trackId);
//		trackMap.put("USER_ID", userId);// 用户ID
//		trackMap.put("AREA_ID", areaId);// 区域ID
//		trackMap.put("UPLOAD_TIME", uploadTime);// 上传时间
//		trackMap.put("SPEED", track.get("speed"));// 瞬时速度
//		trackMap.put("TRACK_TYPE", trackType);// 0自动
//		trackMap.put("LONGITUDE", track.get("longitude"));// 经度
//		trackMap.put("LATITUDE", track.get("latitude"));// 纬度
//		if ("0".equals(trackType)) {// 自动
//			trackMap.put("TRACK_TIME", track.get("track_time"));// 采集时间
//			trackMap.put("IS_GPS", track.get("is_gps"));// 是否GPS上传，1是0否
//			if ("4.9E-324".equals(track.getString("longitude"))) {//GPS无信号
//				trackMap.put("GPS_FLAG", 1);// 无信号
//				trackMap.put("LONGITUDE", 0);
//				trackMap.put("LATITUDE", 0);
//			} else{
//				if ("68".equals(track.getString("is_gps"))) {// 缓存定位作无信号处理
//					trackMap.put("GPS_FLAG", 1);// 无信号
//				} else {
//					trackMap.put("GPS_FLAG", 0);// 有信号
//				}
//			}
//			trackMap.put("GPS_SWITCH", track.get("gps_flag"));// GPS是否打开，1是0否
//		} else {// 手动上传必须是GPS
//			trackMap.put("TRACK_TIME", track.get("upload_time"));// 采集时间
//			trackMap.put("GPS_FLAG", 0);// 有信号
//			trackMap.put("IS_GPS", "1");// 是否GPS上传，1是0否
//			trackMap.put("GPS_SWITCH", "1");// GPS是否打开，1是0否
//		}
//
//		return trackMap;
//	}
//
//	@Override
//	public String getLineTask(String jsonStr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getSignSites(String jsonStr) {
//
//		JSONObject json = JSONObject.fromObject(jsonStr);
//
//		String userId = json.getString("userId");// 用户ID
//		String sn = json.getString("sn");// 手机串号
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}
//		
//		String areaId = json.getString("area_id");// 区域ID
//		String longitude = json.getString("longitude");// 人员所在点经度
//		String latitude = json.getString("latitude");// 人员所在点纬度
//
//		JSONObject result = new JSONObject();
//		JSONArray siteArry = new JSONArray();
//		try {
//			String siterange = paramService.getParamValue("siterange", areaId);// 获取默认站点匹配距离
//			Double range = Double.parseDouble(siterange);
//
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("USER_ID", userId);
//			List<Map<String, Object>> taskSiteList = lineSiteInterfaceDao.getUnfinishSignSitesByUser(params);// 根据人员查询当天所有任务关联的未签到关键点
//
//			for (Map<String, Object> taskSite : taskSiteList) {
//				Double track_x = Double.parseDouble(longitude);
//				Double track_y = Double.parseDouble(latitude);
//				Double task_x = Double.parseDouble(String.valueOf(taskSite.get("LONGITUDE")));
//				Double task_y = Double.parseDouble(String.valueOf(taskSite.get("LATITUDE")));
//
//				Double distance = MapDistance.getDistance(track_x, track_y, task_x, task_y);
//				if (range >= distance) {
//					JSONObject site = new JSONObject();
//					String siteType = taskSite.get("SITE_TYPE").toString();
//					site.put("task_id", "0");// 无效字段
//					site.put("site_id", taskSite.get("SITE_ID"));// 站点ID
//					site.put("site_name", taskSite.get("SITE_NAME"));// 站点名称
//					site.put("site_type", siteType);// 站点类型
//					site.put("longitude", taskSite.get("LONGITUDE").toString());// 站点经度
//					site.put("latitude", taskSite.get("LATITUDE").toString());// 站点纬度
//					site.put("address", taskSite.get("ADDRESS"));// 站点地址
//					site.put("range", range);// 签到距离
//					site.put("distance", distance);
//					siteArry.add(site);
//				}
//			}
//			result.put("sites", siteArry);
//			result.put("result", "000");
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.put("result", "001");
//			result.put("error", e.toString());
//			System.out.println(e.toString());
//		}
//
//		return result.toString();
//	}
//
//	@Override
//	public String getTaskCondition(String jsonStr) {
//		JSONObject json = JSONObject.fromObject(jsonStr);
//		String userId = json.getString("userId");
//		String sn = json.getString("sn");// 手机串号
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}
//
//		JSONObject result = new JSONObject();
//		try {
//
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("USER_ID", userId);
//			List<Map<String, Object>> taskSiteList = lineSiteInterfaceDao.getUnfinishSitesByUser(param);// 根据人员查询当天所有任务关联的未完成巡线点
//
//			JSONArray siteArry = new JSONArray();
//			for (Map<String, Object> taskSite : taskSiteList) {
//				JSONObject site = new JSONObject();
//				site.put("task_id", taskSite.get("TASK_ID"));// 任务ID
//				site.put("site_id", taskSite.get("SITE_ID"));// 站点ID
//				site.put("site_name", taskSite.get("SITE_NAME"));// 站点名称
//				site.put("site_type", taskSite.get("SITE_TYPE"));// 站点类型
//				site.put("longitude", taskSite.get("LONGITUDE"));// 站点经度
//				site.put("latitude", taskSite.get("LATITUDE"));// 站点纬度
//				site.put("address", taskSite.get("ADDRESS"));// 站点地址
//				site.put("is_arrive", taskSite.get("IS_ARRIVE"));// 站点地址
//				siteArry.add(site);
//			}
//
//			result.put("sites", siteArry);
//			result.put("result", "000");
//		} catch (Exception e) {
//			result.put("result", "001");
//			result.put("error", e.toString());
//			System.out.println(e.toString());
//		}
//
//		return result.toString();
//	}
//
//	@Override
//	public String handUploadTrack(String jsonStr) {
//
//		JSONObject track = JSONObject.fromObject(jsonStr);
//		
//		String userId = track.getString("userId");// 用户ID
//		String sn = track.getString("sn");// 手机串号
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}
//
//		// 初始化结果json对象
//		JSONObject result = new JSONObject();
//
//		try {
//			
//			String areaId = track.getString("area_id");// 区域ID
//			String uploadTime = track.getString("upload_time");// 上传时间
//			Map<String, Object> track2 = analysisTrack(track, userId, areaId, uploadTime, "1");// 手动上传
//			lineSiteInterfaceDao.saveAutoTrack(track2);// 保存手动上传轨迹
//			result.put("result", "000");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.put("result", "001");
//			result.put("error", e.toString());
//		}
//		
//		return result.toString();
//	}
//
//	public void saveMatchSiteInfo(String userId, List<Map<String, Object>> taskSiteList, Map<String, Object> track1,
//			Map<String, Object> track2, String maxTrackId) {
//		Double distance = 0.0;
//		String trackId = "";
//		String trackTime = "";
//		int trackFlag = getTrackFalg(track1, track2);
//		label: for (Map<String, Object> taskSite : taskSiteList) {
//			Double range = Double.parseDouble(String.valueOf(taskSite.get("SITE_MATCH")));
//			switch (trackFlag) {
//			case 0:// 轨迹2无信号或GPS未打开,无需匹配，因为轨迹1上一次已经匹配过
//				continue label;
//			case 1:// 只有轨迹1无信号或GPS未打开,轨迹2有信号
//				if ("0".equals(track2.get("IS_GPS").toString())) {
//					range += 50.0;// 非GPS上传坐标增加一个误差距离
//				}
//				Double track_x2 = Double.parseDouble(String.valueOf(track2.get("LONGITUDE")));
//				Double track_y2 = Double.parseDouble(String.valueOf(track2.get("LATITUDE")));
//				Double task_x = Double.parseDouble(String.valueOf(taskSite.get("LONGITUDE")));
//				Double task_y = Double.parseDouble(String.valueOf(taskSite.get("LATITUDE")));
//				distance = MapDistance.getDistance(track_x2, track_y2, task_x, task_y);
//				trackId = track2.get("TRACK_ID").toString();
//				trackTime = track2.get("TRACK_TIME").toString();
//				break;
//			case 2:
//				if ("0".equals(track2.get("IS_GPS").toString()) || "0".equals(track2.get("IS_GPS").toString())) {
//					range += 50.0;// 非GPS上传坐标增加一个误差距离
//				}
//				Double track_x3 = Double.parseDouble(String.valueOf(track2.get("LONGITUDE")));
//				Double track_y3 = Double.parseDouble(String.valueOf(track2.get("LATITUDE")));
//				Double task_xx = Double.parseDouble(String.valueOf(taskSite.get("LONGITUDE")));
//				Double task_yy = Double.parseDouble(String.valueOf(taskSite.get("LATITUDE")));
//				Double track_x1 = Double.parseDouble(String.valueOf(track1.get("LONGITUDE")));
//				Double track_y1 = Double.parseDouble(String.valueOf(track1.get("LATITUDE")));
//				distance = MapDistance.getPointLineDistance(track_x1, track_y1, track_x3, track_y3, task_xx, task_yy);
//				trackId = track2.get("TRACK_ID").toString();
//				trackTime = track2.get("TRACK_TIME").toString();
//				break;
//			case 3://轨迹1和轨迹2速度均超过20码,按400半径圆匹配
//				range = 400.0;
//				Double track_x4 = Double.parseDouble(String.valueOf(track2.get("LONGITUDE")));
//				Double track_y4 = Double.parseDouble(String.valueOf(track2.get("LATITUDE")));
//				Double task_2x = Double.parseDouble(String.valueOf(taskSite.get("LONGITUDE")));
//				Double task_2y = Double.parseDouble(String.valueOf(taskSite.get("LATITUDE")));
//				distance = MapDistance.getDistance(track_x4, track_y4, task_2x, task_2y);
//				trackId = track2.get("TRACK_ID").toString();
//				trackTime = track2.get("TRACK_TIME").toString();
//				break;
//			}
//
//			if (range >= distance) {
//				Map<String, Object> matchSite = new HashMap<String, Object>();
//				int matchId = lineSiteInterfaceDao.getSiteMatchId();
//				matchSite.put("MATCH_ID", matchId);// 站点匹配ID
//				matchSite.put("TRACK_ID", trackId);// 轨迹ID
//				matchSite.put("MATCH_TYPE", "1");// 匹配类型：自动轨迹上传
//				matchSite.put("MATCH_TIME", trackTime);// 匹配时间
//				matchSite.put("SITE_TYPE", taskSite.get("SITE_TYPE"));// 点类型：1关键点，2非关键点,3外力点
//				matchSite.put("SITE_ID", taskSite.get("SITE_ID"));// 点ID
//				matchSite.put("SITE_NAME", taskSite.get("SITE_NAME"));// 点名称
//				matchSite.put("SITE_DISTANCE", distance);// 匹配距离
//				matchSite.put("TASK_ID", taskSite.get("TASK_ID"));// 任务ID
//
//				// 判断是否连续，获取最近一次坐标上传是否匹配到当前点，如果存在表示该两点是连续的
//				Map<String, Object> matchParam = new HashMap<String, Object>();
//				matchParam.put("USER_ID", userId);
//				matchParam.put("SITE_ID", taskSite.get("SITE_ID"));
//				matchParam.put("SITE_TYPE", taskSite.get("SITE_TYPE"));
//				matchParam.put("TASK_ID", taskSite.get("TASK_ID"));// 任务ID
//				matchParam.put("TRACK_ID", maxTrackId);
//				Map<String, Object> recentMatch = lineSiteInterfaceDao.getMaxMatchSite(matchParam);
//				if (null != recentMatch) {
//					matchSite.put("CON_LABEL", recentMatch.get("CON_LABEL"));
//				} else {
//					matchSite.put("CON_LABEL", matchId);
//				}
//
//				lineSiteInterfaceDao.saveMatchSite(matchSite);// 保存匹配的到站点信息
//			}
//		}
//	}
//
//	/**
//	 * 获取轨迹匹配类型
//	 * 
//	 * @param track1
//	 * @param track2
//	 * @return
//	 */
//	private int getTrackFalg(Map<String, Object> track1, Map<String, Object> track2) {
//
//		String gps_flag2 = track2.get("GPS_FLAG").toString();
//		String gps_switch2 = track2.get("GPS_SWITCH").toString();
//
//		if ("1".equals(gps_flag2) || "0".equals(gps_switch2)) {// 轨迹2无信号或GPS未打开,无需匹配，因为轨迹1上一次已经匹配过
//			return 0;
//		} else if (null == track1 || "1".equals(track1.get("GPS_FLAG").toString())
//				|| "0".equals(track1.get("GPS_SWITCH").toString())) {// 只有轨迹1无信号或GPS未打开,轨迹2有信号
//			return 1;
//		} else {
//			Double speed1 = Double.parseDouble(track1.get("SPEED").toString());
//			Double speed2 = Double.parseDouble(track2.get("SPEED").toString());
//			if ((speed2 > 20) && (speed1 > 20)) {// 速度均大于20码，只匹配轨迹2，因为轨迹1
//				// 上一轮已经匹配过
//				return 3;
//			} else {
//				return 2;
//			}
//
//		}
//
//	}
//
//	@Override
//	public String getInvalidLineTime(String jsonStr) {
//
//		JSONObject json = JSONObject.fromObject(jsonStr);
//		String userId = json.getString("userId");// 用户ID
//		String sn = json.getString("sn");// 手机串号
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}
//		
//		String areaId = json.getString("area_id");// 区域ID
//		String queryTime = json.getString("query_time");// 区域ID
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("USER_ID", userId);
//		param.put("AREA_ID", areaId);
//		param.put("START_TIME", queryTime + " " + paramService.getParamValue("WorkStart", areaId) + ":00");
//		param.put("END_TIME", queryTime + " " + paramService.getParamValue("WorkEnd2", areaId) + ":00");
//		param.put("QUERY_DATE", queryTime);
//		List<Map<String, Object>> invalidTimeList = null;
//		if (queryTime.equals(DateUtil.getDate())) {
//			invalidTimeList = paramService.getValidTime(param);// 如果是当天日期，需要计算后返回结果
//		} else {
//			invalidTimeList = lineSiteInterfaceDao.getInvalidTime(param);// 如果非当天查询，从无效时长表中查询结果
//		}
//
//		JSONArray invalidArry = new JSONArray();
//		for (Map<String, Object> invalidTime : invalidTimeList) {
//			JSONObject invalid = new JSONObject();
//			invalid.put("start_time", invalidTime.get("START_TIME"));
//			invalid.put("end_time", invalidTime.get("END_TIME"));
//			invalid.put("invalidType", invalidTime.get("INVALID_TYPE"));
//			invalidArry.add(invalid);
//		}
//
//		// 初始化结果json对象
//		JSONObject result = new JSONObject();
//		result.put("query_time", queryTime);
//		result.put("invalidTimeList", invalidArry);
//		result.put("result", "000");
//
//		return result.toString();
//	}
}
