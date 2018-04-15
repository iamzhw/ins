package icom.axx.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axxreport.dao.XxdReportDao;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.StringUtil;
import com.system.dao.ParamDao;
import com.system.service.ParamService;
import com.system.sys.OnlineUserListener;
import com.util.MapDistance;

import icom.axx.dao.LineSiteInterfaceDao;
import icom.axx.dao.StepCheckDao;
import icom.axx.service.LineSiteInterfaceService;
import icom.util.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class LineSiteInterfaceServiceImpl implements LineSiteInterfaceService {

	@Resource
	private LineSiteInterfaceDao lineSiteInterfaceDao;

	@Resource
	private ParamService paramService;

	@Resource
	private ParamDao paramDao;

	@Resource
	private XxdReportDao xxdReportDao;
	
	@Resource
	private StepCheckDao stepCheckDao;

//	/**
//	 * 保存用户上传时间,控制重复上传
//	 */
//	public static final Map<String, Long> USER_UPLOADTIME = new Hashtable<String, Long>();
//	
//	/**
//	 * 保存用户上传的高铁轨迹,控制重复上传
//	 */
//	public static final Map<String, Long> USER_GTUPLOADTIME = new Hashtable<String, Long>();

	@Override
	public String saveAutoTrack(String jsonStr) {
		JSONObject json = JSONObject.fromObject(jsonStr);

		String userId = json.getString("userId");// 用户ID
		String sn = json.getString("sn");// 用户ID
		if (OnlineUserListener.isOtherLogin(userId, sn)) {// 账号在其他地方登录
			return Result.returnCode("000");// 不是当前用户登录数据直接返回,这样手机端数据直接清空
		} else {
			if (!isLastLogin(userId, sn)) {
				return Result.returnCode("000");// 内存中没有数据，找最后一次登录信息,和最后一次登录信息一致数据才上传
			}
		}

		//整改 wangdechen 2017-06-06
		Map<String, Object> param =  new HashMap<String, Object>();
		param.put("user_id", userId);
		param.put("type", 0); //上传类型  0-普通   1-高铁轨迹
		
		//获取当前系统前一分钟时间
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MINUTE, -1);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = format.format(calendar.getTime());
	    param.put("time", time);
	    
		//从数据库查询该用户60秒内是否做过上传
	    Map<String, Object> upMap = lineSiteInterfaceDao.selectUserUploadTime(param);
	    
		if (null != upMap && upMap.size() > 0) {
			return Result.returnCode("000");// 防止数据重复上传,60秒内同一用户数据不再接收
		} else {
			//保存上传时间，user_id 
			lineSiteInterfaceDao.saveUserUploadTime(param);
		}
		
//		long time = DateUtil.getTime();
//		if (USER_UPLOADTIME.containsKey(userId) && isHalfMinute(USER_UPLOADTIME.get(userId), time)) {
//			return Result.returnCode("000");// 防止数据重复上传,60秒内同一用户数据不再接收
//		} else {
//			USER_UPLOADTIME.put(userId, time);
//		}

//		paramService.saveLogInfo("LineSiteInterfaceServiceImpl", "saveAutoTrack(String jsonStr)", "DEBUG", jsonStr);// 日志记录
		
		MatchSiteThread thread = new MatchSiteThread(lineSiteInterfaceDao, paramService, jsonStr);
		new Thread(thread).start();// 起线程处理轨迹匹配

		return Result.returnCode("000");
	}
	
	@Override
	public String saveAutoTrack2(String jsonStr) {
		JSONObject json = JSONObject.fromObject(jsonStr);

		String userId = json.getString("userId");// 用户ID
		String sn = json.getString("sn");// 用户ID
		if (OnlineUserListener.isOtherLogin(userId, sn)) {// 账号在其他地方登录
			return Result.returnCode("000");// 不是当前用户登录数据直接返回,这样手机端数据直接清空
		} else {
			if (!isLastLogin(userId, sn)) {
				return Result.returnCode("000");// 内存中没有数据，找最后一次登录信息,和最后一次登录信息一致数据才上传
			}
		}

		//整改 wangdechen 2017-06-06
		Map<String, Object> param =  new HashMap<String, Object>();
		param.put("user_id", userId);
		param.put("type", 0); //上传类型  0-普通   1-高铁轨迹
		
		//获取当前系统前一分钟时间
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MINUTE, -1);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = format.format(calendar.getTime());
	    param.put("time", time);
	    
		//保存上传时间，user_id 
		lineSiteInterfaceDao.saveUserUploadTime(param);
		
		MatchSiteThread thread = new MatchSiteThread(lineSiteInterfaceDao, paramService, jsonStr);
		new Thread(thread).start();// 起线程处理轨迹匹配

		return Result.returnCode("000");
	}
	

	@Override
	public String saveAutoGtTrack(String jsonStr) {
		JSONObject json = JSONObject.fromObject(jsonStr);

		String userId = json.getString("userId");// 用户ID
		String sn = json.getString("sn");// 用户ID
		if (OnlineUserListener.isOtherLogin(userId, sn)) {// 账号在其他地方登录
			return Result.returnCode("000");// 不是当前用户登录数据直接返回,这样手机端数据直接清空
		} else {
			if (!isLastLogin(userId, sn)) {
				return Result.returnCode("000");// 内存中没有数据，找最后一次登录信息,和最后一次登录信息一致数据才上传
			}
		}

		//整改 wangdechen 2017-06-06
		Map<String, Object> param =  new HashMap<String, Object>();
		param.put("user_id", userId);
		param.put("type", 1); //上传类型  0-普通   1-高铁轨迹
		
		//获取当前系统前一分钟时间
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MINUTE, -1);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = format.format(calendar.getTime());
	    param.put("time", time);
	    
		//从数据库查询该用户60秒内是否做过上传
	    Map<String, Object> upMap = lineSiteInterfaceDao.selectUserUploadTime(param);
	    
		if (null != upMap && upMap.size() > 0) {
			return Result.returnCode("000");// 防止数据重复上传,60秒内同一用户数据不再接收
		} else {
			//保存上传时间，user_id 
			lineSiteInterfaceDao.saveUserUploadTime(param);
		}
		
//		long time = DateUtil.getTime();
//		if (USER_GTUPLOADTIME.containsKey(userId) && isHalfMinute(USER_GTUPLOADTIME.get(userId), time)) {
//			return Result.returnCode("000");// 防止数据重复上传,60秒内同一用户数据不再接收
//		} else {
//			USER_GTUPLOADTIME.put(userId, time);
//		}
//		paramService.saveLogInfo("LineSiteInterfaceServiceImpl", "saveAutoGtTrack(String jsonStr)", "DEBUG", jsonStr);// 日志记录
		MatchSiteGtThread gtThread = new MatchSiteGtThread(lineSiteInterfaceDao, paramService, jsonStr);
		new Thread(gtThread).start();// 起线程处理轨迹匹配

		return Result.returnCode("000");
	}
	
	
	/**
	 * 最后一次登录的手机号
	 * 
	 * @param userId
	 * @param sn
	 * @return
	 */
	private boolean isLastLogin(String userId, String sn) {
		if (null != OnlineUserListener.getOnlineUser(userId)) {
			return true;
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("USER_ID", userId);
		Map<String, Object> login = paramService.getBaseLoginInfo(param);
		if (null != login && sn.equals(String.valueOf(login.get("SN")))) {
			return true;
		}
		return false;
	}

	private boolean isHalfMinute(long time1, long time2) {
		long diff = time2 - time1;
		long min = (diff / (1000 * 1));
		if (min < 60) {
			return true;
		}
		return false;
	}

	@Override
	public String saveRegisterSite(String jsonStr) {

		JSONObject json = JSONObject.fromObject(jsonStr);

		String userId = json.getString("userId");// 用户ID
		String sn = json.getString("sn");// 用户ID
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		// 初始化结果json对象
		JSONObject result = new JSONObject();

		try {
			String areaId = json.getString("area_id");// 区域ID
			String site_id = json.getString("site_id");// 关键点
			String site_name = json.getString("site_name");// 关键点名称
			String distance = json.getString("distance");// 到关键点距离
			String longitude = json.getString("longitude");// 经度
			String latitude = json.getString("latitude");// 纬度
			String sign_time = json.getString("sign_time");// 签到时间
			String remark = json.getString("remark");// 文字说明
			String task_id = json.getString("task_id");// 任务ID

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("USER_ID", userId);
			params.put("TASK_ID", task_id);
			params.put("SITE_ID", site_id);

			
			/**
			 * 关键点可重复签到
			 */
//			List<Map<String, Object>> taskSiteList = lineSiteInterfaceDao.queryKeysiteByUser(params);
//			if (taskSiteList != null && taskSiteList.size() > 0) {// 关键点已签到，无需重复签到
//				result.put("result", "001");
//				result.put("error", "关键点已签到");
//				return result.toString();
//			}

			int registerId = lineSiteInterfaceDao.getRegisterId();// 生成签到点ID

			Map<String, Object> registerMap = new HashMap<String, Object>();
			registerMap.put("SIGN_ID", registerId);// 签到点ID
			registerMap.put("USER_ID", userId);// 用户ID
			registerMap.put("AREA_ID", areaId);// 区域ID
			registerMap.put("LONGITUDE", longitude);// 经度
			registerMap.put("LATITUDE", latitude);// 纬度
			registerMap.put("SIGN_TIME", sign_time);// 签到时间
			lineSiteInterfaceDao.saveRegisterSite(registerMap);// 保存签到信息

			Map<String, Object> matchSite = new HashMap<String, Object>();
			matchSite.put("MATCH_ID", lineSiteInterfaceDao.getSiteMatchId());// 站点匹配ID
			matchSite.put("SIGN_ID", registerId);
			matchSite.put("MATCH_TIME", sign_time);// 匹配时间
			matchSite.put("SITE_TYPE", "1");// 点类型：关键点，非关键点
			matchSite.put("SITE_ID", site_id);// 点ID
			matchSite.put("SITE_NAME", site_name);// 点名称
			matchSite.put("SITE_DISTANCE", distance);// 匹配距离
			matchSite.put("TASK_ID", task_id);// 任务ID
			matchSite.put("REMARK", remark);// 备注信息

			lineSiteInterfaceDao.saveMatchSite(matchSite);// 保存匹配的到站点信息

			result.put("site_id", registerId);
			result.put("result", "000");
		} catch (Exception e) {
			result.put("result", "001");
			result.put("error", e.toString());
		}

		return result.toString();
	}

	public Map<String, Object> analysisTrack(JSONObject track, String userId, String areaId, String uploadTime,
			String trackType) {

		int trackId = lineSiteInterfaceDao.getTrackId();// 生成轨迹点主键

		Map<String, Object> trackMap = new HashMap<String, Object>();
		trackMap.put("TRACK_ID", trackId);
		trackMap.put("USER_ID", userId);// 用户ID
		trackMap.put("AREA_ID", areaId);// 区域ID
		trackMap.put("UPLOAD_TIME", uploadTime);// 上传时间
		trackMap.put("SPEED", track.get("speed"));// 瞬时速度
		trackMap.put("TRACK_TYPE", trackType);// 0自动
		trackMap.put("LONGITUDE", track.get("longitude"));// 经度
		trackMap.put("LATITUDE", track.get("latitude"));// 纬度
		if ("0".equals(trackType)) {// 自动
			String isGps = track.containsKey("is_gps") ? track.getString("is_gps") : "1";
			trackMap.put("TRACK_TIME", track.get("track_time"));// 采集时间
			trackMap.put("IS_GPS", isGps);// 是否GPS上传，1是0否
			if ("4.9E-324".equals(track.getString("longitude"))) {// GPS无信号
				trackMap.put("GPS_FLAG", 1);// 无信号
				trackMap.put("LONGITUDE", 0);
				trackMap.put("LATITUDE", 0);
			} else {
				if ("68".equals(isGps)) {// 缓存定位作无信号处理
					trackMap.put("GPS_FLAG", 1);// 无信号
				} else {
					trackMap.put("GPS_FLAG", 0);// 有信号
				}
			}
			trackMap.put("GPS_SWITCH", track.containsKey("gps_flag") ? track.getString("gps_flag") : "1");// GPS是否打开，1是0否
		} else {// 手动上传必须是GPS
			trackMap.put("TRACK_TIME", track.get("upload_time"));// 采集时间
			trackMap.put("GPS_FLAG", 0);// 有信号
			trackMap.put("IS_GPS", "1");// 是否GPS上传，1是0否
			trackMap.put("GPS_SWITCH", "1");// GPS是否打开，1是0否
		}

		return trackMap;
	}

	@Override
	public String getSignSites(String jsonStr) {

		JSONObject json = JSONObject.fromObject(jsonStr);

		String userId = json.getString("userId");// 用户ID
		String sn = json.getString("sn");// 手机串号
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		String areaId = json.getString("area_id");// 区域ID
		String longitude = json.getString("longitude");// 人员所在点经度
		String latitude = json.getString("latitude");// 人员所在点纬度
		//如果获取不到人员经纬再取0点
		longitude = !"".equals(longitude)?longitude:"0.0";
		latitude = !"".equals(latitude)?latitude:"0.0";
		//end by ningruofan
		JSONObject result = new JSONObject();
		JSONArray siteArry = new JSONArray();
		try {
			String siterange = paramService.getParamValue("siterange", areaId);// 获取默认站点匹配距离
			Double range = Double.parseDouble(siterange);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("USER_ID", userId);
			List<Map<String, Object>> taskSiteList = lineSiteInterfaceDao.getUnfinishSignSitesByUser(params);// 根据人员查询当天所有任务关联的关键点(可重复签到，不过滤已经签到的关键点)

			for (Map<String, Object> taskSite : taskSiteList) {
				Double track_x = Double.parseDouble(longitude);
				Double track_y = Double.parseDouble(latitude);
				Double task_x = Double.parseDouble(String.valueOf(taskSite.get("LONGITUDE")));
				Double task_y = Double.parseDouble(String.valueOf(taskSite.get("LATITUDE")));

				Double distance = MapDistance.getDistance(track_y, track_x, task_y, task_x);
				if (range >= distance) {
					JSONObject site = new JSONObject();
					site.put("task_id", taskSite.get("TASK_ID"));// 无效字段
					site.put("site_id", taskSite.get("SITE_ID"));// 站点ID
					site.put("site_name", taskSite.get("SITE_NAME"));// 站点名称
					site.put("site_type", String.valueOf(taskSite.get("SITE_TYPE")));// 站点类型
					site.put("longitude", String.valueOf(taskSite.get("LONGITUDE")));// 站点经度
					site.put("latitude", String.valueOf(taskSite.get("LATITUDE")));// 站点纬度
					site.put("address", taskSite.get("ADDRESS"));// 站点地址
					site.put("range", String.valueOf(taskSite.get("SITE_MATCH")));// 签到距离
					site.put("distance", distance);
					siteArry.add(site);
				}
			}
			result.put("sites", siteArry);
			result.put("result", "000");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("error", e.toString());
			System.out.println(e.toString());
		}

		return result.toString();
	}

	@Override
	public String getTaskCondition(String jsonStr) {
		JSONObject json = JSONObject.fromObject(jsonStr);
		String userId = json.getString("userId");
		String sn = json.getString("sn");// 手机串号
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		JSONObject result = new JSONObject();
		try {

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("USER_ID", userId);
			List<Map<String, Object>> taskSiteList = lineSiteInterfaceDao.getUnfinishSitesByUser(param);// 根据人员查询当天所有任务关联的未完成巡线点

			JSONArray siteArry = new JSONArray();
			for (Map<String, Object> taskSite : taskSiteList) {
				JSONObject site = new JSONObject();
				site.put("task_id", taskSite.get("TASK_ID"));// 任务ID
				site.put("site_id", taskSite.get("SITE_ID"));// 站点ID
				site.put("site_name", taskSite.get("SITE_NAME"));// 站点名称
				site.put("site_type", taskSite.get("SITE_TYPE"));// 站点类型
				site.put("longitude", taskSite.get("LONGITUDE"));// 站点经度
				site.put("latitude", taskSite.get("LATITUDE"));// 站点纬度
				site.put("address", taskSite.get("ADDRESS"));// 站点地址
				site.put("is_arrive", taskSite.get("IS_ARRIVE"));// 站点地址
				siteArry.add(site);
			}
			
			List<Map<String, Object>> taskStepSiteList = stepCheckDao.getUnfinishStepSitesByUser(param);

			JSONArray stepsiteArry = new JSONArray();
			for (Map<String, Object> taskSite : taskStepSiteList) {
				JSONObject stepsite = new JSONObject();
				stepsite.put("task_id", taskSite.get("TASK_ID"));
				stepsite.put("task_name", taskSite.get("TASK_NAME"));
				stepsite.put("equip_id", taskSite.get("EQUIP_ID"));
				stepsite.put("equip_code", taskSite.get("EQUIP_CODE"));
				stepsite.put("longitude", taskSite.get("LONGITUDE"));
				stepsite.put("latitude", taskSite.get("LATITUDE"));
				stepsite.put("equip_type_name", taskSite.get("EQUIP_TYPE_NAME"));
				stepsite.put("area_id", taskSite.get("AREA_ID"));
				stepsite.put("relay_name", taskSite.get("RELAY_NAME"));
				stepsite.put("cable_name", taskSite.get("CABLE_NAME"));
				stepsite.put("steppart_name", taskSite.get("STEPPART_NAME"));
				stepsite.put("inspect_name", taskSite.get("INSPECT_NAME"));
				stepsiteArry.add(stepsite);
			}
			
			result.put("sites", siteArry);
			result.put("stepsites", stepsiteArry);
			result.put("result", "000");
		} catch (Exception e) {
			result.put("result", "001");
			result.put("error", e.toString());
			System.out.println(e.toString());
		}

		return result.toString();
	}

	@Override
	public String handUploadTrack(String jsonStr) {

		JSONObject track = JSONObject.fromObject(jsonStr);

		String userId = track.getString("userId");// 用户ID
		String sn = track.getString("sn");// 手机串号
		if (OnlineUserListener.isOtherLogin(userId, sn)) {// 登录校验
			return Result.returnCode("000");
		}

		// 初始化结果json对象
		JSONObject result = new JSONObject();

		try {
			String areaId = track.getString("area_id");// 区域ID
			String uploadTime = track.getString("upload_time");// 上传时间

			if (isEmpty(track.get("longitude")) || isEmpty(track.get("latitude"))) {
				result.put("result", "001");
				return result.toString();
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("USER_ID", userId);
			Map<String, Object> track2 = analysisTrack(track, userId, areaId, uploadTime, "1");// 手动上传
			lineSiteInterfaceDao.saveAutoTrack(track2);// 保存手动上传轨迹

			params.put("MAX_TRACK_ID", "0");
			params.put("START_TIME", track2.get("TRACK_TIME"));
			params.put("END_TIME", track2.get("TRACK_TIME"));// 记录结算结束时间
			params.put("AREA_ID", areaId);
			result.put("result", "000");
			lineSiteInterfaceDao.saveMatchQuartzInfo(params);// 保存轨迹，定时任务扫描匹配
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("error", e.toString());
		}
		return result.toString();
	}

	public void saveMatchSiteInfo(String userId, List<Map<String, Object>> taskSiteList, Map<String, Object> track1,
			Map<String, Object> track2, String maxTrackId1, String maxTrackId2) {
		Double distance = 0.0;
		int trackFlag = getTrackFalg(track1, track2);
		if (trackFlag == 0) {// 轨迹1和轨迹2均无信号，无需匹配
			return;
		}
		Map<String, String> matchLabel = new HashMap<String, String>();
		label: for (Map<String, Object> taskSite : taskSiteList) {
			matchLabel.put("site", "3");// 默认设备点在轨迹点之间
			Double range = Double.parseDouble(String.valueOf(taskSite.get("SITE_MATCH")));
			Double task_x = Double.parseDouble(String.valueOf(taskSite.get("LONGITUDE")));
			Double task_y = Double.parseDouble(String.valueOf(taskSite.get("LATITUDE")));
			switch (trackFlag) {
			case 1:// 只有轨迹1有信号，轨迹2无信号，匹配轨迹1
				Double track_x1 = Double.parseDouble(String.valueOf(track1.get("LONGITUDE")));
				Double track_y1 = Double.parseDouble(String.valueOf(track1.get("LATITUDE")));
				distance = MapDistance.getDistance(track_y1, track_x1, task_y, task_x);
				matchLabel.put("site", "1");// 设备点靠近轨迹1
				break;
			case 2:// 只有轨迹2有信号，轨迹1无信号
				Double track_x2 = Double.parseDouble(String.valueOf(track2.get("LONGITUDE")));
				Double track_y2 = Double.parseDouble(String.valueOf(track2.get("LATITUDE")));
				distance = MapDistance.getDistance(track_y2, track_x2, task_y, task_x);
				matchLabel.put("site", "2");// 设备点靠近轨迹1
				break;
			case 3:
				Double track_x22 = Double.parseDouble(String.valueOf(track2.get("LONGITUDE")));
				Double track_y22 = Double.parseDouble(String.valueOf(track2.get("LATITUDE")));
				Double track_x11 = Double.parseDouble(String.valueOf(track1.get("LONGITUDE")));
				Double track_y11 = Double.parseDouble(String.valueOf(track1.get("LATITUDE")));
				distance = MapDistance.getPointLineDistance(track_y11, track_x11, track_y22, track_x22, task_y, task_x,
						matchLabel);
				break;
			case 4:// 轨迹1和轨迹2速度均超过20码,按400半径圆匹配
				range = 400.0;
				Double track_x111 = Double.parseDouble(String.valueOf(track1.get("LONGITUDE")));
				Double track_y111 = Double.parseDouble(String.valueOf(track1.get("LATITUDE")));
				distance = MapDistance.getDistance(track_y111, track_x111, task_y, task_x);
				String conLabel = null;
				if (range > distance) {
					conLabel = insertMatchSiteInfo(taskSite, track1.get("TRACK_ID").toString(), track1
							.get("TRACK_TIME").toString(), distance, userId, maxTrackId1, "1", conLabel);// 匹配轨迹1
				}

				Double track_x222 = Double.parseDouble(String.valueOf(track2.get("LONGITUDE")));
				Double track_y222 = Double.parseDouble(String.valueOf(track2.get("LATITUDE")));
				distance = MapDistance.getDistance(track_y222, track_x222, task_y, task_x);
				if (range > distance) {
					conLabel = insertMatchSiteInfo(taskSite, track2.get("TRACK_ID").toString(), track2
							.get("TRACK_TIME").toString(), distance, userId, maxTrackId2, "2", conLabel);// 匹配轨迹2
				}
				continue label;
			}

			if (range >= distance) {
				String conLabel = null;
				if ("1".equals(matchLabel.get("site"))) {// 轨迹1离设备点近，轨迹2离设备点距离超过300米
					conLabel = insertMatchSiteInfo(taskSite, track1.get("TRACK_ID").toString(), track1
							.get("TRACK_TIME").toString(), distance, userId, maxTrackId1, "1", conLabel);// 匹配轨迹1

				} else if ("2".equals(matchLabel.get("site"))) {// 轨迹2离设备点近，轨迹1离设备点距离超过300米
					conLabel = insertMatchSiteInfo(taskSite, track2.get("TRACK_ID").toString(), track2
							.get("TRACK_TIME").toString(), distance, userId, maxTrackId2, "2", conLabel);// 匹配轨迹2
				} else {// 设备点在轨迹1和轨迹2之间
					conLabel = insertMatchSiteInfo(taskSite, track1.get("TRACK_ID").toString(), track1
							.get("TRACK_TIME").toString(), distance, userId, maxTrackId1, "1", conLabel);// 匹配轨迹1

					conLabel = insertMatchSiteInfo(taskSite, track2.get("TRACK_ID").toString(), track2
							.get("TRACK_TIME").toString(), distance, userId, maxTrackId2, "2", conLabel);// 匹配轨迹2
				}
			}
		}
	}

	private String insertMatchSiteInfo(Map<String, Object> taskSite, String trackId, String trackTime, double distance,
			String userId, String maxTrackId, String trackType, String conLabel) {

		if ("1".equals(trackType)) {// 保存的是轨迹1
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TRACK_ID", trackId);
			param.put("SITE_ID", taskSite.get("SITE_ID"));
			List<Map<String, Object>> matchSites = lineSiteInterfaceDao.queryMatchSiteByTrack(param);
			if (null != matchSites && matchSites.size() > 0) {// 如果未匹配重新匹配，否则无需匹配
				return String.valueOf(matchSites.get(0).get("CON_LABEL"));
			}
		}

		Map<String, Object> matchSite = new HashMap<String, Object>();
		int matchId = lineSiteInterfaceDao.getSiteMatchId();
		matchSite.put("MATCH_ID", matchId);// 站点匹配ID
		matchSite.put("TRACK_ID", trackId);// 轨迹ID
		matchSite.put("MATCH_TYPE", "1");// 匹配类型：自动轨迹上传
		matchSite.put("MATCH_TIME", trackTime);// 匹配时间
		matchSite.put("SITE_TYPE", taskSite.get("SITE_TYPE"));// 点类型：1关键点，2非关键点,3外力点
		matchSite.put("SITE_ID", taskSite.get("SITE_ID"));// 点ID
		matchSite.put("SITE_NAME", taskSite.get("SITE_NAME"));// 点名称
		matchSite.put("SITE_DISTANCE", distance);// 匹配距离
		matchSite.put("TASK_ID", taskSite.get("TASK_ID"));// 任务ID

		if (conLabel != null) {
			matchSite.put("CON_LABEL", conLabel);
		} else {
			// 判断是否连续，获取最近一次坐标上传是否匹配到当前点，如果存在表示该两点是连续的
			Map<String, Object> matchParam = new HashMap<String, Object>();
			matchParam.put("USER_ID", userId);
			matchParam.put("SITE_ID", taskSite.get("SITE_ID"));
			matchParam.put("SITE_TYPE", taskSite.get("SITE_TYPE"));
			matchParam.put("TASK_ID", taskSite.get("TASK_ID"));// 任务ID
			matchParam.put("TRACK_ID", maxTrackId);
			List<Map<String, Object>> recentMatch = lineSiteInterfaceDao.getMaxMatchSite(matchParam);
			if (null != recentMatch && recentMatch.size() > 0) {
				matchSite.put("CON_LABEL", recentMatch.get(0).get("CON_LABEL"));
			} else {
				matchSite.put("CON_LABEL", matchId);// 连续标识
			}
		}

		lineSiteInterfaceDao.saveMatchSite(matchSite);// 保存匹配的到站点信息

		return String.valueOf(matchSite.get("CON_LABEL"));
	}

	/**
	 * 获取轨迹匹配类型
	 * 
	 * @param track1
	 * @param track2
	 * @return
	 */
	private int getTrackFalg(Map<String, Object> track1, Map<String, Object> track2) {

		String gps_flag2 = track2.get("GPS_FLAG").toString();
		String gps_switch2 = track2.get("GPS_SWITCH").toString();

		boolean isGps2 = "0".equals(gps_flag2) && "1".equals(gps_switch2);// 轨迹2有信号且GPS打开

		boolean isGps1 = null != track1 && "0".equals(track1.get("GPS_FLAG").toString())
				&& "1".equals(track1.get("GPS_SWITCH").toString());// 轨迹1有信号且GPS未打开

		if (!isGps2 && !isGps1) {// 轨迹1和轨迹2均无信号或GPS未打开
			return 0;
		} else if (isGps1 && !isGps2) {// 只有轨迹2无信号或GPS未打开,轨迹1有信号
			return 1;
		} else if (isGps2 && !isGps1) {// 只有轨迹1信号或GPS未打开,轨迹2有信号
			return 2;
		} else {
			String track_time1 = track1.get("TRACK_TIME").toString();
			String track_time2 = track2.get("TRACK_TIME").toString();
			long time = DateUtil.getDifferTime(track_time1, track_time2, 60);
			if (time > 10) {// 两个轨迹点时间间隔大于10分钟，轨迹2 按圆匹配
				return 2;
			} else {
				Double speed1 = Double.parseDouble(track1.get("SPEED").toString());
				Double speed2 = Double.parseDouble(track2.get("SPEED").toString());
				if ((speed2 > 20) && (speed1 > 20)) {// 速度均大于20码,同时按400米圆匹配
					return 4;
				} else {
					return 3;
				}
			}
		}
	}

	@Override
	public String getInvalidLineTime(String jsonStr) {

		JSONObject json = JSONObject.fromObject(jsonStr);
		String userId = json.getString("userId");// 用户ID
		String sn = json.getString("sn");// 手机串号
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}
		if (json.getString("personId") != null && !"".equals(json.getString("personId"))) {
			userId = json.getString("personId");
		}
		String areaId = json.getString("area_id");// 区域ID
		String queryTime = json.getString("query_time");// 区域ID

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("USER_ID", userId);
		param.put("AREA_ID", areaId);
		param.put("START_TIME", queryTime + " " + paramService.getParamValue("WorkStart", areaId) + ":00");
		param.put("END_TIME", queryTime + " " + paramService.getParamValue("WorkEnd2", areaId) + ":00");
		param.put("QUERY_DATE", queryTime);
		List<Map<String, Object>> invalidTimeList = null;
		JSONObject effectJson = new JSONObject();
		if (queryTime.equals(DateUtil.getDate())) {
			invalidTimeList = paramService.getValidTime(param);// 如果是当天日期，需要计算后返回结果
			effectJson.put("time_status", 0);
			effectJson.put("allday_time", "");
			effectJson.put("morn_time", "");
			effectJson.put("after_time", "");
		} else {
			effectJson = effect_time(userId, areaId, queryTime);// 查询全天及上下午有效时长
			invalidTimeList = lineSiteInterfaceDao.getInvalidTime(param);// 如果非当天查询，从无效时长表中查询结果
		}

		JSONArray invalidArry = new JSONArray();
		for (Map<String, Object> invalidTime : invalidTimeList) {
			JSONObject invalid = new JSONObject();
			invalid.put("start_time", invalidTime.get("START_TIME"));
			invalid.put("end_time", invalidTime.get("END_TIME"));
			invalid.put("invalidType", invalidTime.get("INVALID_TYPE"));
			invalidArry.add(invalid);
		}

		// 初始化结果json对象
		JSONObject result = new JSONObject();
		result.put("query_time", queryTime);
		result.put("effect_time", effectJson);
		result.put("invalidTimeList", invalidArry);
		result.put("result", "000");

		return result.toString();
	}

	/**
	 * 查询全天及上下午有效时长
	 * 
	 * @param json
	 * @return
	 */
	private JSONObject effect_time(String userId, String areaId, String queryTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", areaId);
		map.put("paramName", "WorkStart");
		String start_time_m = paramDao.getParamValueByName(map);
		map.put("paramName", "WorkEnd");
		String end_time_m = paramDao.getParamValueByName(map);
		map.put("paramName", "WorkStart2");
		String start_time_a = paramDao.getParamValueByName(map);
		map.put("paramName", "WorkEnd2");
		String end_time_a = paramDao.getParamValueByName(map);

		String start_date = queryTime;
		String end_date = queryTime;

		String start_date_t = start_date + " " + start_time_m + ":00";
		String end_date_t = end_date + " " + end_time_a + ":00";

		map.put("start_date_m", start_time_m);
		map.put("end_date_m", end_time_m);
		map.put("start_date_a", start_time_a);
		map.put("end_date_a", end_time_a);

		map.put("start_date", start_date);
		map.put("end_date", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", areaId);
		map.put("inspect_id", userId);

		String[] end_date_marr = end_time_m.split(":");
		String[] start_date_marr = start_time_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60 + Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60 - Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_time_a.split(":");
		String[] start_date_aarr = start_time_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60 + Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60 - Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		JSONObject effectJson = new JSONObject();
		effectJson.put("time_status", 0);// 无有效时长
		effectJson.put("allday_time", "");
		effectJson.put("morn_time", "");
		effectJson.put("after_time", "");

		List<Map<String, Object>> data = xxdReportDao.getExportInspectTime(map); // 全天及上下午有效时长结果集
		if (data.size() > 0) {
			effectJson.put("time_status", 1);// 有有效时长
			effectJson.put("allday_time", data.get(0).get("INS_TIME_VALID_ALLDAY"));
			effectJson.put("morn_time", data.get(0).get("INS_TIME_VALID_MORNING"));
			effectJson.put("after_time", data.get(0).get("INS_TIME_VALID_AFTERNOON"));
		}
		return effectJson;
	}

	private boolean isEmpty(Object obj) {
		if (null == obj) {
			return true;
		} else {
			return StringUtil.isNullOrEmpty(obj.toString());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void calSaveMatchSites(Map<String, Object> params) {
		List<Map<String, Object>> taskSiteList = paramDao.getTaskSitesByUser(params);// 根据人员查询当天所有任务关联的巡线点
		List<Map<String, Object>> calTrackList = lineSiteInterfaceDao.getCalTrackList(params);// 获取需要计算匹配的轨迹
		if (null != calTrackList) {

			if (calTrackList.size() > 1) {
				String maxTrackId1 = String.valueOf(params.get("MAX_TRACK_ID"));
				String maxTrackId2 = maxTrackId1;
				for (int i = 0; i < calTrackList.size() - 1; i++) {
					Map<String, Object> track1 = calTrackList.get(i);
					if (null != track1 && "0".equals(track1.get("GPS_FLAG").toString())
							&& "1".equals(track1.get("GPS_SWITCH").toString())) {// 轨迹1有信号且GPS未打开
						maxTrackId2 = track1.get("TRACK_ID").toString();
					}
					saveMatchSiteInfo(params.get("USER_ID").toString(), taskSiteList, calTrackList.get(i),
							calTrackList.get(i + 1), maxTrackId1, maxTrackId2);// 保存匹配信息
					maxTrackId1 = maxTrackId2;
				}
			} else if (calTrackList.size() == 1) {

				Map<String, Object> track1 = null;
				params.put("FIRST_TIME", params.get("END_TIME"));
				List<Map<String, Object>> tracks = lineSiteInterfaceDao.getMaxHasFlagTrackByUser(params);// 获取最大有信号轨迹

				String maxTrackId1 = "0";
				if (null != tracks && tracks.size() > 0) {
					maxTrackId1 = String.valueOf(tracks.get(0).get("TRACK_ID"));
				}

				List<Map<String, Object>> maxTracks = lineSiteInterfaceDao.getMaxTrackByUser(params);
				String maxTrackId2 = maxTrackId1;
				if (null != maxTracks && maxTracks.size() > 0) {
					track1 = maxTracks.get(0);
					if (null != track1 && "0".equals(track1.get("GPS_FLAG").toString())
							&& "1".equals(track1.get("GPS_SWITCH").toString())) {// 轨迹1有信号且GPS未打开
						maxTrackId2 = track1.get("TRACK_ID").toString();
					}
				}
				saveMatchSiteInfo(params.get("USER_ID").toString(), taskSiteList, track1, calTrackList.get(0),
						maxTrackId1, maxTrackId2);// 保存匹配信息
			}
		}
		lineSiteInterfaceDao.deleteMatchQuartzInfo(params);// 删除巡线匹配定时任务
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void calAllMatchSites(Map<String, Object> param) {
		List<Map<String, Object>> taskSiteList = paramDao.getTaskSitesByUser(param);// 根据人员查询当天所有任务关联的巡线点
		List<Map<String, Object>> calTrackList = paramDao.getCalTrackList(param);// 获取需要计算匹配的轨迹

		if (null != calTrackList && calTrackList.size() > 1) {

			if (null != taskSiteList && taskSiteList.size() > 0) {
				paramDao.deleteMatchSitebyTaskId(taskSiteList.get(0));

				String maxTrackId = "0";
				for (int i = 0; i < calTrackList.size() - 1; i++) {
					saveMatchSiteInfo(param.get("USER_ID").toString(), taskSiteList, calTrackList.get(i),
							calTrackList.get(i + 1), maxTrackId, "0");// 保存匹配信息
					if ("0".equals(calTrackList.get(i).get("GPS_FLAG").toString())) {// 取最近有信号值
						maxTrackId = String.valueOf(calTrackList.get(i).get("TRACK_ID"));
					}
				}
			}
		}
	}
	
	@Override
	public String saveHandUploadGtTrack(String jsonStr) {

		JSONObject track = JSONObject.fromObject(jsonStr);

		String userId = track.getString("userId");// 用户ID
		String sn = track.getString("sn");// 手机串号
		if (OnlineUserListener.isOtherLogin(userId, sn)) {// 登录校验
			return Result.returnCode("000");
		}

		// 初始化结果json对象
		JSONObject result = new JSONObject();

		try {
			
			if (isEmpty(track.get("longitude")) || isEmpty(track.get("latitude"))) {
				result.put("result", "001");
				return result.toString();
			}

			int trackId = lineSiteInterfaceDao.getGtTrackId();// 生成高铁轨迹点主键

			Map<String, Object> trackMap = new HashMap<String, Object>();
			trackMap.put("TRACK_ID", trackId);
			trackMap.put("USER_ID", userId);// 用户ID
			trackMap.put("UPLOAD_TIME", track.getString("upload_time"));// 上传时间
			trackMap.put("SPEED", track.getString("speed"));// 瞬时速度
			trackMap.put("TRACK_TYPE", 1);// 0自动
			trackMap.put("LONGITUDE", track.getString("longitude"));// 经度
			trackMap.put("LATITUDE", track.getString("latitude"));// 纬度
		    trackMap.put("TRACK_TIME", track.getString("upload_time"));// 采集时间
			trackMap.put("GPS_FLAG", 0);// 有信号
			trackMap.put("IS_GPS", "1");// 是否GPS上传，1是0否
			trackMap.put("GPS_SWITCH", "1");// GPS是否打开，1是0否
			lineSiteInterfaceDao.saveAutoGtTrack(trackMap);// 保存自动上传轨迹
			
			result.put("result", "000");
			result.put("track_id", String.valueOf(trackId));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("error", e.toString());
		}
		return result.toString();
	}
	
	@Override
	public List<Map<String, Object>> queryMatchQuartzInfos(Map<String, Object> param) {
		return lineSiteInterfaceDao.queryMatchQuartzInfos(param);
	}

	@Override
	public void updateMatchQuartzInfo(Map<String, Object> param) {
		lineSiteInterfaceDao.updateMatchQuartzInfo(param);
	}

}
