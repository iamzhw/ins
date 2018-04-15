package com.cableInspection.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.TimeCountDao;
import com.cableInspection.service.TimeCountService;
import com.system.constant.RoleNo;

@SuppressWarnings("all")
@Transactional
@Service
public class TimeCountServiceImpl implements TimeCountService {
	@Resource
	private TimeCountDao timeCountDao;

	@Override
	public List<Map> export(HttpServletRequest request) {
		Map map = new HashMap();
		map.put("STAFF_NAME", request.getParameter("staff_name"));
		map.put("AREA_ID", request.getParameter("area_id"));
		map.put("SON_AREA_ID", request.getParameter("son_area_id"));
		map.put("DEPT_NAME", request.getParameter("dept_name"));
		map.put("START_TIME", request.getParameter("start_time"));
		map.put("COMPLETE_TIME", request.getParameter("complete_time"));
		
		return timeCountDao.export(map);
	}

	@Override
	public Map query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String START_TIME = request.getParameter("start_time");
		String COMPLETE_TIME = request.getParameter("complete_time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if("".equals(START_TIME)||null==START_TIME){
			cal.set(Calendar.DAY_OF_MONTH, 1);
			START_TIME=sdf.format(cal.getTime());
		}
		if("".equals(COMPLETE_TIME)||null==COMPLETE_TIME){
			cal.add(Calendar.MONTH,1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH,-1);
			COMPLETE_TIME=sdf.format(cal.getTime());
		}
		START_TIME+=" 00:00:00";
		COMPLETE_TIME+=" 23:59:59";
		map.put("STAFF_NAME", request.getParameter("staff_name"));
		map.put("AREA_ID", request.getParameter("area_id"));
		map.put("SON_AREA_ID", request.getParameter("son_area_id"));
		map.put("DEPT_NAME", request.getParameter("dept_name"));
		map.put("START_TIME", START_TIME);
		map.put("COMPLETE_TIME", COMPLETE_TIME);
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {// 如果是审核员只能查到本区域的
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = timeCountDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

}
