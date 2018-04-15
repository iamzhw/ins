package com.cableInspection.serviceimpl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;
import util.password.MD5;

import com.cableInspection.dao.DeptDao;
import com.cableInspection.service.DeptService;
import com.roomInspection.dao.ClassDao;
import com.roomInspection.service.ClassService;
import com.system.constant.RoleNo;
import com.system.dao.GeneralDao;
import com.system.dao.RoleDao;
import com.system.dao.StaffDao;

@SuppressWarnings("all")
@Transactional
@Service
public class DeptServiceImpl implements DeptService {
	@Resource
	private DeptDao deptDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private GeneralDao generalDao;
	
	@Resource
	private RoleDao roleDao;
	
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String dept_name = request.getParameter("dept_name");
		String dept_no = request.getParameter("dept_no");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		map.put("DEPT_NAME", dept_name);
		map.put("DEPT_NO", dept_no);
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					//map.put("AREA_ID", area);
					map.put("SON_AREA_ID", -1);
				}
			}
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> olists = deptDao.query(query);
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
			deptDao.delete(id);
		}

	}

	@Override
	public Map<String, Object> edit(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		/**
		 * 1、根据staffId查询员工信息 2、查询地市 3、根据员工的staffId查询区县列表
		 */
		String dept_id = String.valueOf(request.getParameter("dept_id"));
		Map deptInfo = deptDao.getDeptInfo(Integer.parseInt(dept_id));
		rs.put("dept", deptInfo);
		return rs;
	}

	@Override
	public void insert(HttpServletRequest request) throws Exception {
		String dept_name = request.getParameter("dept_name");
		String dept_no = request.getParameter("dept_no");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		Map map = new HashMap();
		map.put("DEPT_NAME", dept_name);
		map.put("DEPT_NO", dept_no);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		HttpSession session = request.getSession();
		if("".equals(son_area))
		{
			map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
		}
		
		map.put("CREATE_STAFF", session.getAttribute("staffId"));
		deptDao.insert(map);

	}

	@Override
	public void update(HttpServletRequest request) throws Exception {
		String dept_id = request.getParameter("dept_id");
		String dept_no = request.getParameter("dept_no");
		String dept_name = request.getParameter("dept_name");
		String son_area = request.getParameter("son_area");
		Map map = new HashMap();
		map.put("DEPT_ID", dept_id);
		map.put("DEPT_NO", dept_no);
		map.put("DEPT_NAME", dept_name);
		map.put("SON_AREA_ID", son_area);;
		HttpSession session = request.getSession();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		deptDao.update(map);

	}

	@Override
	public Map<String, Object> index(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		//获取区域
		HttpSession session = request.getSession();
		rs.put("areaId", session.getAttribute("areaId"));
		rs.put("sonAreaId", session.getAttribute("sonAreaId"));
		String staffId = request.getSession().getAttribute("staffId").toString();//当前用户
		//获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		String roleNo = null;
		for(Map<String, String> map : roleList){
			roleNo = map.get("ROLE_NO");
			if(RoleNo.GLY.equals(roleNo) 
					|| RoleNo.LXXJ_ADMIN.equals(roleNo) 
					|| RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)){
				rs.put("admin", true);
				rs.put("isSonAreaAdmin", false);
				break;
			}
			if (RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
				rs.put("isSonAreaAdmin", true);
			}
		}
		return rs;
	}
	@Override
	public Map<String, Object> getStaff(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		String staffNo = request.getParameter("staff_no");
		String staffName = request.getParameter("staff_name");
		String dept_id = request.getParameter("dept_id");
		map.put("STAFF_NAME", staffName);
		map.put("STAFF_NO", staffNo);
		map.put("DEPT_ID", dept_id);
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = deptDao.getStaffs(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		// pmap.put("total", list.size());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public void distributeStaff(HttpServletRequest request) {
		String dept_id = request.getParameter("dept_id");
		String staffId = request.getSession().getAttribute("staffId").toString();//当前用户
		deptDao.deleteStaffByDeptId(dept_id);
		String staffArr = request.getParameter("staffArr");
		String staffs[] = new String[]{};
		if(StringUtils.isNotEmpty(staffArr))
		{
			staffs= staffArr.replace(" ", "").split(",");
		}
		Map map = new HashMap();
		for (int i = 0; i < staffs.length; i++) {
			map.put("DEPT_ID", dept_id);
			map.put("STAFF_ID", staffs[i]);
			map.put("CREATE_STAFF", staffId);
			deptDao.insertStaffByDeptId(map);
		}
	}

	@Override
	public String getDeptSelection(HttpServletRequest request) {
		String area_id= request.getSession().getAttribute("areaId").toString();
		String dept_name="";
		try {
			dept_name = new String(request.getParameter("dept_name").toString().getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map params = new HashMap();
		params.put("area_id", area_id);
		params.put("dept_name", dept_name);
		JSONArray jsonArray = new JSONArray();
		List list = deptDao.getDeptSelection(params);
		return jsonArray.fromObject(list).toString();
	}
	
	@Override
	public String getDeptSelectionForCable(HttpServletRequest request) {
		String area_id= request.getSession().getAttribute("areaId").toString();
		Map params = new HashMap();
		params.put("area_id", area_id);
		JSONArray jsonArray = new JSONArray();
		List list = deptDao.getDeptSelectionForCable(params);
		return jsonArray.fromObject(list).toString();
	}
	
	@Override
	public String getDeptByAreaId(HttpServletRequest request) {
		String area_id= request.getSession().getAttribute("areaId").toString();
		String dept_name="";
		try {
			dept_name = new String(request.getParameter("dept_name").toString().getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map params = new HashMap();
		params.put("area_id", area_id);
		params.put("dept_name", dept_name);
		JSONArray jsonArray = new JSONArray();
		List list = deptDao.getDeptByAreaId(params);
		return jsonArray.fromObject(list).toString();
	}
}
