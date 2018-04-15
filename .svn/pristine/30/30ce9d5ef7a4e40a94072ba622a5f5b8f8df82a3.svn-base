package com.cableInspection.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.TroubleReportDao;
import com.cableInspection.service.TroubleReportService;

@SuppressWarnings("all")
@Transactional
@Service
public class TroubleReportServiceImpl implements TroubleReportService {

	@Resource
	private TroubleReportDao troubleReportDao;
	
	@Override
	public Map<String, Object> query(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		map.put("start_time", request.getParameter("start_time"));
		map.put("end_time", request.getParameter("end_time"));
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = troubleReportDao.query(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public List<Map> query(HttpServletRequest request) {
		Map map = new HashMap();
		map.put("start_time", request.getParameter("Vstart_time"));
		map.put("end_time", request.getParameter("Vend_time"));
		List<Map> list = troubleReportDao.queryExl(map);
		
		return list;
	}

}
