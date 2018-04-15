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

import com.cableInspection.dao.TroubleDao;
import com.cableInspection.service.TroubleService;

@Service
@Transactional
public class TroubleServiceImpl implements
		TroubleService {
	
	@Resource
	private TroubleDao troubleDao;

	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		String point_no = request.getParameter("point_no");
		String point_name = request.getParameter("point_name");
		String point_type = request.getParameter("point_type");
		String create_time = request.getParameter("create_time");
//		HttpSession session = request.getSession();
//		String staffId = session.getAttribute("staffId").toString();//当前用户
//		String areaId = session.getAttribute("areaId").toString();
//		String staffName = session.getAttribute("staffName").toString();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("POINT_NAME", point_name);
		map.put("POINT_NO", point_no);
		map.put("POINT_TYPE", point_type);
		map.put("CREATE_TIME", create_time);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map<String, Object>> olists = troubleDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Boolean check(HttpServletRequest request) {
		String[] ids = request.getParameter("ids").split(",");
		for(String s : ids){
			troubleDao.check(s);
		}
		return true;
	}

}
