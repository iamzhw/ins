package icom.axx.service.impl;

import icom.axx.dao.LineSiteInterfaceDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.linePatrol.util.JSONUtil;
import com.system.service.ParamService;
import com.util.MapDistance;

public class MatchSiteExecutor {

	@Autowired
	private ParamService paramService;

	@Autowired
	private LineSiteInterfaceDao lineSiteInterfaceDao;

	public void saveAutoTrack(String jsonStr) {
		MatchSiteThread t = new MatchSiteThread(jsonStr);
		new Thread(t).start();
	}

	private void saveTrack(String userId, String areaId, String uploadTime, JSONArray trackArray) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USER_ID", userId);
		Map<String, Object> track1 = null;
		Map<String, Object> track2 = null;
		if (trackArray.size() > 0) {
			params.put("FIRST_TIME", trackArray.getJSONObject(0).get("track_time"));
			List<Map<String, Object>> tracks = lineSiteInterfaceDao.getMaxHasFlagTrackByUser(params);// 获取最大有信号轨迹
			if (null != tracks && tracks.size() > 0) {
				params.put("MAX_TRACK_ID", tracks.get(0).get("TRACK_ID"));
			} else {
				params.put("MAX_TRACK_ID", "0");
			}
			List<Map<String, Object>> maxTracks = lineSiteInterfaceDao.getMaxTrackByUser(params);
			if (null != maxTracks && maxTracks.size() > 0) {// 获取最近的有信号轨迹
				track1 = maxTracks.get(0);
			}

			if (null != track1) {
				params.put("START_TIME", track1.get("TRACK_TIME"));
			} else {
				params.put("START_TIME", trackArray.getJSONObject(0).get("track_time"));// 记录计算开始时间
			}
			params.put("END_TIME", trackArray.getJSONObject(trackArray.size() - 1).get("track_time"));// 记录结算结束时间

			for (int i = 0; i < trackArray.size(); i++) {
				track2 = analysisTrack(trackArray.getJSONObject(i), userId, areaId, uploadTime);
				filterTrackMaxDistance(track1, track2);
				if (lineSiteInterfaceDao.getUserTrackTime(track2) == 0) {
					lineSiteInterfaceDao.saveAutoTrack(track2);// 保存自动上传轨迹
				}
				track1 = track2;
			}

			lineSiteInterfaceDao.saveMatchQuartzInfo(params);// 保存轨迹，定时任务扫描匹配
		}
	}

	public Map<String, Object> analysisTrack(JSONObject track, String userId, String areaId, String uploadTime) {

		int trackId = lineSiteInterfaceDao.getTrackId();// 生成轨迹点主键

		Map<String, Object> trackMap = new HashMap<String, Object>();
		trackMap.put("TRACK_ID", trackId);
		trackMap.put("USER_ID", userId);// 用户ID
		trackMap.put("AREA_ID", areaId);// 区域ID
		trackMap.put("UPLOAD_TIME", uploadTime);// 上传时间
		trackMap.put("SPEED", track.get("speed"));// 瞬时速度
		trackMap.put("TRACK_TYPE", "0");// 0自动
		trackMap.put("LONGITUDE", track.get("longitude"));// 经度
		trackMap.put("LATITUDE", track.get("latitude"));// 纬度
		// 自动
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

		return trackMap;
	}

	/**
	 * 过滤偏差较大的点
	 * 
	 * @param track1
	 * @param track2
	 */
	private void filterTrackMaxDistance(Map<String, Object> track1, Map<String, Object> track2) {
		if (null != track1 && "1".equals(track1.get("GPS_SWITCH").toString())
				&& "0".equals(track1.get("GPS_FLAG").toString()) && "1".equals(track1.get("IS_GPS").toString())
				&& "161".equals(track2.get("IS_GPS").toString())) {// 如果track2基站定位，且track1是GPS定位，距离偏差超过1公里直接过滤
			Double track_x2 = Double.parseDouble(String.valueOf(track2.get("LONGITUDE")));
			Double track_y2 = Double.parseDouble(String.valueOf(track2.get("LATITUDE")));
			Double task_x1 = Double.parseDouble(String.valueOf(track1.get("LONGITUDE")));
			Double task_y1 = Double.parseDouble(String.valueOf(track1.get("LATITUDE")));
			double distance = MapDistance.getDistance(track_y2, track_x2, task_y1, task_x1);
			if (distance > 1000) {// 定义该点为无信号
				track2.put("GPS_FLAG", "1");
			}
		}
	}

	class MatchSiteThread implements Runnable {

		String jsonStr;

		public MatchSiteThread(String jsonStr) {
			this.jsonStr = jsonStr;
		};

		@Override
		public void run() {
			try {
				JSONObject json = JSONObject.fromObject(jsonStr);

				String userId = json.getString("userId");// 用户ID
				String areaId = json.getString("area_id");// 区域ID
				String uploadTime = json.getString("upload_time");// 上传时间
				JSONArray trackArray = json.getJSONArray("trackList");// 获取轨迹点集合
				trackArray = JSONUtil.sortArray(trackArray, "track_time");// 数据按轨迹时间排序

				// 初始化结果json对象
				saveTrack(userId, areaId, uploadTime, trackArray);
			} catch (Exception e) {
				paramService.saveLogInfo("MatchSiteThread", "saveAutoTrack(String jsonStr)", "ERROR", jsonStr);
			}
		}
	}
}
