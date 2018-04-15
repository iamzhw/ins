package com.cableInspection.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cableInspection.dao.CableDao;
import com.cableInspection.dao.TrailDao;
import com.cableInspection.model.CableModel;
import com.cableInspection.model.PointModel;
import com.cableInspection.service.TrailService;

@SuppressWarnings("all")
@Transactional
@Service
public class TrailServiceImpl implements TrailService {

	@Resource
	private TrailDao trailDao;

	@Resource
	private CableDao cableDao;

	@Override
	public List<Map> queryTrail(HttpServletRequest request) {
		Map map = new HashMap();
		map.put("staff_id", request.getParameter("staff_id"));
		//map.put("query_time", request.getParameter("query_time"));
		map.put("begin_time", request.getParameter("begin_time"));
		map.put("end_time", request.getParameter("end_time"));
		return trailDao.queryPoints(map);
	}

	@Override
	public String getTaskCables(HttpServletRequest request) {
		Map map = new HashMap();
		map.put("staff_id", request.getParameter("staff_id"));
		map.put("query_time", request.getParameter("query_time"));
		// 查询出当天所有巡线任务
		List<Map<String, Object>> taskLines = trailDao.getTaskCables(map);
		List<CableModel> results = new ArrayList<CableModel>();
		if (taskLines != null && taskLines.size() > 0) {
			Map<String, Object> lineMap = null;
			List<Map> pointsMap = null;
			//获得计划的区域和点
			for (Map<String, Object> data : taskLines) {
				CableModel cm = new CableModel();
				cm.setParentId(Integer.parseInt(data.get(
								"PARENT_LINE_ID").toString()));
				cm.setLineNo(data.get("LINE_NO").toString());
				cm.setLineName(data.get("LINE_NAME").toString());
				cm.setCreateStaff(Long.parseLong(data.get("CREATE_STAFF")
						.toString()));
				cm.setCreateTime(data.get("CREATE_TIME").toString());
				cm.setLineType(Integer
						.parseInt(data.get("LINE_TYPE").toString()));
				List<PointModel> pointMode = new ArrayList<PointModel>();
				pointsMap = cableDao.queryPoint(data);
				for (Map point : pointsMap) {
					PointModel pm = new PointModel();
					pm.setLatitude(point.get("LONGITUDE").toString());
					pm.setLongitude(point.get("LATITUDE").toString());
					pointMode.add(pm);
				}
				cm.setPointMode(pointMode);
				results.add(cm);
			}
		}
		return JSONArray.fromObject(results).toString();
	}
}
