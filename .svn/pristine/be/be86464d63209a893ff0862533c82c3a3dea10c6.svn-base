package com.roomInspection.serviceimpl;

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
import util.password.MD5;

import com.roomInspection.dao.ClassDao;
import com.roomInspection.service.ClassService;
import com.system.dao.GeneralDao;
import com.system.dao.StaffDao;

@SuppressWarnings("all")
@Transactional
@Service
public class ClassServiceImpl implements ClassService {
	@Resource
	private ClassDao classDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private GeneralDao generalDao;
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String class_name = request.getParameter("class_name");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		map.put("CLASS_NAME", class_name);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> olists = classDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void delete(HttpServletRequest request) {
		String selected = request.getParameter("selected");
		HttpSession session = request.getSession();
		String[] classs = selected.split(",");
		for (int i = 0; i < classs.length; i++) {
			int id = Integer.parseInt(classs[i]);
			classDao.delete(id);
		}

	}

	@Override
	public Map<String, Object> edit(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		/**
		 * 1、根据staffId查询员工信息 2、查询地市 3、根据员工的staffId查询区县列表
		 */
		String class_id = String.valueOf(request.getParameter("class_id"));
		String staffId = request.getSession().getAttribute("staffId").toString();
		int ifGly=staffDao.getifGly(staffId);
		rs.put("ifGly", ifGly);
		Map classModel = classDao.getClassModel(Integer.parseInt(class_id));
		HttpSession session = request.getSession();
		List<Map<String, String>> areaList = generalDao.getAreaList();
		List<Map<String, String>> sonAreaList = classDao.getSonAreaListByClassId(Integer.parseInt(class_id));
		
		rs.put("class", classModel);
		rs.put("areaList", areaList);
		rs.put("sonAreaList", sonAreaList);
		return rs;
	}

	@Override
	public void insert(HttpServletRequest request) throws Exception {
		String class_name = request.getParameter("class_name");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		Map map = new HashMap();
		map.put("CLASS_NAME", class_name);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		HttpSession session = request.getSession();
		map.put("CREATE_STAFF", session.getAttribute("staffId"));
		classDao.insert(map);

	}

	@Override
	public void update(HttpServletRequest request) throws Exception {
		String class_id=request.getParameter("class_id");
		String class_name = request.getParameter("class_name");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		Map map = new HashMap();
		map.put("CLASS_ID", class_id);
		map.put("CLASS_NAME", class_name);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);;
		HttpSession session = request.getSession();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		classDao.update(map);

	}

}
