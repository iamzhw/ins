package com.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.system.constant.RoleNo;
import com.system.dao.GeneralDao;
import com.system.dao.StaffDao;
import com.system.dao.UnifiedPageDao;
import com.system.service.UnifiedPageService;

import util.page.Query;
import util.page.UIPage;
import util.password.MD5;

@SuppressWarnings("all")
@Service
public class UnifiedPageImpl implements UnifiedPageService {

	@Resource
	private UnifiedPageDao unifiedPageDao;
	@Resource
	private GeneralDao generalDao;
	@Resource
	private StaffDao staffDao;
	
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String org_name = request.getParameter("org_name");
		String role_name = request.getParameter("role_name");
		String id_number = request.getParameter("id_number");
		map.put("STAFF_NAME", staff_name);
		map.put("STAFF_NO", staff_no);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		map.put("ORG_NAME", org_name);
		map.put("ROLE_NAME", role_name);
		map.put("ID_NUMBER", id_number);
		
		

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = unifiedPageDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;

	}
	
	@Override
	public List getRoles(String staffId) {
		return unifiedPageDao.getRoles(staffId);
	}
	
	@Override
	public List getSofts(String staffId) {
		return unifiedPageDao.getSofts(staffId);
	}
	
	@Override
	public Map<String, Object> getOutSitePermissions(String staffId){
		return  unifiedPageDao.getOutSitePermissions(staffId);
	}

	@Override
	public void insert(HttpServletRequest request) throws Exception {
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String pwd = request.getParameter("password");
		String password = MD5.encrypt(MD5.md5s(pwd));
		String tel = request.getParameter("tel");
		String id_number = request.getParameter("id_number");
		String email = request.getParameter("email");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String status = request.getParameter("status");
		String own_company = request.getParameter("own_company");
		String maintor_type = request.getParameter("maintor_type");
		Map map = new HashMap();
		map.put("STAFF_NAME", staff_name);
		map.put("STAFF_NO", staff_no);
		map.put("PASSWORD", pwd);
		map.put("PASSWORD_", password);
		map.put("TEL", tel);
		map.put("ID_NUMBER", id_number);
		map.put("EMAIL", email);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		map.put("STATUS", status);
		map.put("OWN_COMPANY", own_company);
		map.put("MAINTOR_TYPE", maintor_type);


		unifiedPageDao.insert(map);
	}
	
	@Override
	public Map<String, Object> edit(HttpServletRequest request) {

		Map<String, Object> rs = new HashMap<String, Object>();
		/**
		 * 1、根据staffId查询员工信息 2、查询地市 3、根据员工的staffId查询区县列表
		 */
		String staff_id = String.valueOf(request.getParameter("staff_id"));

		Map staff = unifiedPageDao.getStaff(staff_id);
		HttpSession session = request.getSession();
		List<Map<String, String>> areaList = generalDao.getAreaList();
		List<Map<String, String>> sonAreaList = generalDao.getSonAreaListByStaffId(staff_id);

		rs.put("staff", staff);
		rs.put("areaList", areaList);
		rs.put("sonAreaList", sonAreaList);

		// 获取公司列表
		String areaId = staff.get("AREA_ID").toString();
		List<Map<String, String>> companyList = generalDao.getOwnCompany(areaId);
		rs.put("companyList", companyList);
		return rs;
	}
	
	@Override
	public void delete(HttpServletRequest request) {

		String selected = request.getParameter("selected");
		HttpSession session = request.getSession();
		Map map = new HashMap();
//		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		String[] staffs = selected.split(",");
		for (int i = 0; i < staffs.length; i++) {
			map.put("STAFF_ID", staffs[i]);
			unifiedPageDao.delete(map);
		}

	}
	
	@Override
	public void update(HttpServletRequest request) throws Exception {
		String staff_id = request.getParameter("staff_id");
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String password = request.getParameter("password");
		String password_ = MD5.encrypt(MD5.md5s(request.getParameter("password")));
		String tel = request.getParameter("tel");
		String id_number = request.getParameter("id_number");
		String email = request.getParameter("email");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String status = request.getParameter("status");
		String own_company = request.getParameter("own_company");
		String maintor_type = request.getParameter("maintor_type");
		
		Map map = new HashMap();
		map.put("STAFF_ID", staff_id);
		map.put("STAFF_NAME", staff_name);
		map.put("STAFF_NO", staff_no);
		map.put("PASSWORD", password);
		map.put("PASSWORD_", password_);
		map.put("TEL", tel);
		map.put("ID_NUMBER", id_number);
		map.put("EMAIL", email);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		map.put("STATUS", status);
		map.put("OWN_COMPANY", own_company);
		map.put("MAINTOR_TYPE", maintor_type);
		
		HttpSession session = request.getSession();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		unifiedPageDao.update(map);
	}
	
	@Override
	public Map<String, Object> gridManage(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String staff_id = String.valueOf(request.getParameter("staff_id"));
		HttpSession session = request.getSession();
		List<Map<String, String>> areaList = generalDao.getAreaList();
		List<Map<String, String>> sonAreaList = generalDao.getSonAreaListByStaffId(staff_id);
		List<String> deptList = generalDao.getDeptListByStaff(staff_id);

		rs.put("asp_staff_id", staff_id);
		rs.put("deptList",deptList);
		return rs;
	}
	
	@Override
	public Map<String, Object> queryRolePermissions(HttpServletRequest request, UIPage pager) {
		//1.查询出当前登录人员ID
		String staff_id = String.valueOf(request.getSession().getAttribute("staffId"));
		Map map = new HashMap();//条件map
		map.put("ifGly","ifGly");
		List<Map> olists = staffDao.queryRolePermission(map);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}
	
}
