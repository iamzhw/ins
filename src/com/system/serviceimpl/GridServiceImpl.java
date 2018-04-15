package com.system.serviceimpl;

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

import com.system.constant.RoleNo;
import com.system.dao.GeneralDao;
import com.system.dao.GridDao;
import com.system.service.GridService;
@SuppressWarnings("all")
@Transactional
@Service
public class GridServiceImpl implements GridService {
	  @Resource
	    private GridDao gridDao;
	  @Override
		public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
			Map map = new HashMap();
			String grid_name = request.getParameter("grid_name");
			String grid_no = request.getParameter("grid_no");
			String area = request.getParameter("area");
			String son_area = request.getParameter("son_area");
			map.put("GRID_NAME", grid_name);
			map.put("GRID_NO", grid_no);
			map.put("AREA_ID", area);
			map.put("SON_AREA_ID", son_area);
			Query query = new Query();
			query.setPager(pager);
			query.setQueryParams(map);
			List<Map> olists = gridDao.query(query);
			Map<String, Object> pmap = new HashMap<String, Object>(0);
			pmap.put("total", query.getPager().getRowcount());
			pmap.put("rows", olists);
			return pmap;

		}
	@Override
	public Map<String, Object> queryAduits(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		String grid_id = request.getParameter("grid_id");
		map.put("GRID_ID", grid_id);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> aduits = gridDao.queryAduits(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", aduits);
		return pmap;
	}
	@Override
	public void saveAauits(HttpServletRequest request) {
		String selected_aduits = request.getParameter("selected_aduits");
		String grid_id = request.getParameter("grid_id");
		String[] aduits = selected_aduits.split(",");
		gridDao.delAudits(grid_id);
		for (int i = 0; i < aduits.length; i++) {
			Map map = new HashMap();
			map.put("GRID_ID", grid_id);
			map.put("STAFF_ID", aduits[i]);
			// 执行插入操作
			gridDao.saveAduits(map);

		}
	}
	@Override
	public List getAduits(String grid_id) {
		// TODO Auto-generated method stub
		return gridDao.getAduits(grid_id);
	}
}
