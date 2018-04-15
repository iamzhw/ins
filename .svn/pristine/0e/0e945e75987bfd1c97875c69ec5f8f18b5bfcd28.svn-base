package com.cableInspection.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cableInspection.dao.CableDao;
import com.cableInspection.dao.StaffLocationDao;
import com.cableInspection.service.StaffLocationService;

@SuppressWarnings("all")
@Transactional
@Service
public class StaffLocationServiceImpl implements StaffLocationService {
	
	@Resource
	private StaffLocationDao staffLocationDao;
	
	@Resource
	private CableDao cableDao;
	
	@Override
	public Map query(HttpServletRequest request) {
		String area_id=request.getSession().getAttribute("areaId").toString();
		String distance=request.getParameter("distance");
		String staff_id=request.getSession().getAttribute("staffId").toString();
		Map params = new HashMap();
		params.put("area_id", area_id);
		params.put("distance", distance);
		params.put("staff_id", staff_id);
		List<Map> staffList = staffLocationDao.queryStaff(params);
		List<Map> eqpList = new ArrayList();
		
		if(staffList.size()>0){
			for (Map map : staffList) {
				params.put("longitude", map.get("LONGITUDE"));
				params.put("latitude", map.get("LATITUDE"));
				List<Map> part = new ArrayList();
				part=staffLocationDao.queryEqp(params);
				eqpList.addAll(part);
			}
		}
		
		Map rs = new HashMap();
		rs.put("staffList", staffList);
		rs.put("eqpList", eqpList);
		return rs;
	}

	@Override
	public String getAreaName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String areaId = session.getAttribute("areaId").toString();
		return cableDao.queryAreaName(areaId);
	}

}
