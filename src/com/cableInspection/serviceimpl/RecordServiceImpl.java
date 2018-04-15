package com.cableInspection.serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cableInspection.dao.RecordDao;
import com.cableInspection.service.RecordService;
import com.system.constant.RoleNo;

import util.page.Query;
import util.page.UIPage;

@SuppressWarnings("all")
@Transactional
@Service
public class RecordServiceImpl implements RecordService {

	@Resource
	private RecordDao recordDao;
	
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		map.put("start_time", request.getParameter("start_time"));
		map.put("end_time", request.getParameter("end_time"));
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {//省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {//是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {//是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} 
				else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {//如果是审核员只能查到本区域的
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} 
				else {
					map.put("AREA_ID", -1);
				}
			}
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = recordDao.query(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public List<Map> queryPhoto(HttpServletRequest request) {
		Map map = new HashMap();
		map.put("record_id", request.getParameter("record_id"));
		List<Map> list=recordDao.queryPhoto(map);
		return list;
	}

	@Override
	public Map<String, Object> getListByPage(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		
		List<Map> areaList = new ArrayList<Map>();
//		String areaId =  request.getParameter("AREA_ID");
//		String month =  request.getParameter("RECORD_MONTH");
		map.put("AREA_ID", request.getParameter("AREA_ID"));
		map.put("RECORD_MONTH", request.getParameter("RECORD_MONTH"));

		map.put("RECORD_YEAR", request.getParameter("RECORD_YEAR"));
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> recordList = recordDao.getListByPage(query);
		
		/*if(null==areaId||"".equals(areaId)){
			areaList = recordDao.getAreaList();
		}else{
			Map areaSearchMap = new HashMap();
			areaSearchMap.put("AREA_ID", areaId);
			areaList = recordDao.getSonAreaListByAreaId(areaSearchMap);
		}*/

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", recordList);
		return pmap;
	}

	@Override
	public Map<String, Object> getAreaList() {
		Map map = new HashMap();
		map.put("areaList", recordDao.getAreaList());
		return map;
	}

	@Override
	public Map<String, Object> queryRecordStaff(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String AREA_ID = request.getParameter("AREA_ID");
		String RECORD_DAY = request.getParameter("RECORD_DAY");
		String RECORD_MONTH = request.getParameter("RECORD_MONTH");
		String SEARCH_AREA_ID = request.getParameter("SEARCH_AREA_ID");
		String RECORD_YEAR = request.getParameter("RECORD_YEAR");
		map.put("AREA_ID", AREA_ID);
		map.put("RECORD_DAY", RECORD_DAY);
		map.put("RECORD_YEAR", RECORD_YEAR);
		map.put("RECORD_MONTH", RECORD_MONTH);
		map.put("SEARCH_AREA_ID", SEARCH_AREA_ID);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = recordDao.queryRecordStaff(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public List<Map> getRecordForExport(HttpServletRequest request) {
		Map map = new HashMap();

		List<Map> areaList = new ArrayList<Map>();
		//		String areaId =  request.getParameter("AREA_ID");
		//		String month =  request.getParameter("RECORD_MONTH");
		map.put("AREA_ID", request.getParameter("AREA_ID"));
		map.put("RECORD_YEAR", request.getParameter("RECORD_YEAR"));
		map.put("RECORD_MONTH", request.getParameter("RECORD_MONTH"));
		
		List<Map> recordList = recordDao.getRecordForExport(map);
		return recordList;
	}

	@Override
	public List<Map> getRecordStaffForExport(HttpServletRequest request) {
		Map map = new HashMap();
		String AREA_ID = request.getParameter("AREA_ID");
		String RECORD_DAY = request.getParameter("RECORD_DAY");
		String RECORD_MONTH = request.getParameter("RECORD_MONTH");
		String RECORD_YEAR = request.getParameter("RECORD_YEAR");
		String SEARCH_AREA_ID = request.getParameter("SEARCH_AREA_ID");
		
		map.put("AREA_ID", AREA_ID);
		map.put("RECORD_DAY", RECORD_DAY);
		map.put("RECORD_YEAR", RECORD_YEAR);
		map.put("RECORD_MONTH", RECORD_MONTH);
		map.put("SEARCH_AREA_ID", SEARCH_AREA_ID);

		List<Map> list = recordDao.getRecordStaffForExport(map);
		
		return list;
	}

}
