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

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.PersonalWorkDao;
import com.cableInspection.service.PersonalWorkService;
import com.system.constant.RoleNo;

@SuppressWarnings("all")
@Transactional
@Service
public class PersonalWorkServiceImpl implements PersonalWorkService {
	@Resource
	private PersonalWorkDao personalWorkDao;

	@Override
	public Map query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String START_TIME = request.getParameter("start_time");
		String END_TIME = request.getParameter("end_time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if("".equals(START_TIME)||null==START_TIME){
			cal.set(Calendar.DAY_OF_MONTH, 1);
			START_TIME=sdf.format(cal.getTime());
		}
		if("".equals(END_TIME)||null==END_TIME){
			cal.add(Calendar.MONTH,1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH,-1);
			END_TIME=sdf.format(cal.getTime());
		}
		//START_TIME+=" 00:00:00";
		END_TIME+=" 23:59:59";
		map.put("AREA_ID", request.getParameter("area_id"));
		map.put("SON_AREA_ID", request.getParameter("son_area_id"));
		map.put("START_TIME", START_TIME);
		map.put("END_TIME", END_TIME);
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
		List<Map> list = personalWorkDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public String queryAreaPoints(HttpServletRequest request) {
		Map params = new HashMap();
		Object son_area_id = request.getSession().getAttribute("sonAreaId");
		String swlng = request.getParameter("swlng");
		String swlat = request.getParameter("swlat");
		String nelng = request.getParameter("nelng");
		String nelat = request.getParameter("nelat");
		String son_area = request.getParameter("son_area_id");
		params.put("son_area_id", son_area!=null?son_area:son_area_id);
		params.put("swlng", swlng);
		params.put("swlat", swlat);
		params.put("nelng", nelng);
		params.put("nelat", nelat);
		
		List<Map> list = personalWorkDao.queryAreaPoints(params);
		JSONArray json = new JSONArray();
		if(list.size()>0){
			json.addAll(list);
		}else{
			json.add("");
		}
		return json.toString();
	}

}
