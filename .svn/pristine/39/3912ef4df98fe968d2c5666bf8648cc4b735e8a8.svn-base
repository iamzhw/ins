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

import com.cableInspection.dao.ArrivalDao;
import com.cableInspection.dao.PhotoRemarkDao;
import com.cableInspection.service.PhotoRemarkService;
import com.system.constant.RoleNo;
import com.system.dao.RoleDao;
import com.system.service.BaseMethodService;
import com.util.sendMessage.PropertiesUtil;

@SuppressWarnings("all")
@Transactional
@Service
public class PhotoRemarkServiceimpl implements PhotoRemarkService {
	
	@Resource
	PhotoRemarkDao photoRemarkDao;	
	@Resource
	ArrivalDao arrivalDao;
	@Resource
	RoleDao roleDao;
	
	@Override
	public Map remarkIndex(HttpServletRequest request){
		Map<String, Object> rs = new HashMap<String, Object>();
		// 所有地市
		List<Map<String, Object>> areaList = arrivalDao.getArea();
		rs.put("areaList", areaList);
		// 当前用户所在地市
		rs.put("curr_area", request.getSession().getAttribute("areaId"));
		rs.put("sonAreaId", request.getSession().getAttribute("sonAreaId"));
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		// 获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		String roleNo = null;
		for (Map<String, String> map : roleList) {
			roleNo = map.get("ROLE_NO");
			if (RoleNo.GLY.equals(roleNo) || RoleNo.LXXJ_ADMIN.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
				rs.put("admin", true);
				break;
			}
		}
		return rs;
	}
	
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map params = new HashMap();
		String url = request.getServerName();
		params.put("url", getUrl(url));
		params.put("area_id", request.getParameter("area_id"));
		params.put("son_area_id", request.getParameter("son_area_id"));
		params.put("start_time", request.getParameter("start_time"));
		params.put("end_time", request.getParameter("end_time"));
		params.put("task_name", request.getParameter("task_name"));
		params.put("line_name", request.getParameter("line_name"));
		params.put("point_name", request.getParameter("point_name"));
		params.put("staff_name", request.getParameter("staff_name"));
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(params);
		
		List<Map> list = photoRemarkDao.queryPhotoRemark(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}
	
	@Override
	public Map<String, Object> queryRemark(HttpServletRequest request) {
		List<Map> list = photoRemarkDao.queryRemark(request.getParameter("photo_id"));
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", list.size());
		pmap.put("rows", list);
		return pmap;
	}
	
	@Override
	public void addRemark(HttpServletRequest request) {
		Map params = new HashMap();
		params.put("photo_id", request.getParameter("photo_id"));
		params.put("staff_id", request.getSession().getAttribute("staffId"));
		params.put("remark", request.getParameter("remark"));
		params.put("status", request.getParameter("status"));
		photoRemarkDao.insertRemark(params);
	}
	
	private String getUrl(String url){
		List outside=PropertiesUtil.getPropertyToList("outside");
		List inside=PropertiesUtil.getPropertyToList("inside");
		String trueUrl="";
		for (int i = 0; i < inside.size(); i++) {
			if(url.indexOf(inside.get(i).toString())>=0){
				trueUrl= inside.get(i).toString();
				break;
			}else{
				trueUrl=outside.get(0).toString();
				break;
			}
		}
		return trueUrl;
	}
}
