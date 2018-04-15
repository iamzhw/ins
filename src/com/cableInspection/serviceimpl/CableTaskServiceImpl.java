package com.cableInspection.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.CableTaskDao;
import com.cableInspection.service.CableTaskService;
import com.system.constant.RoleNo;
import com.system.dao.RoleDao;
import com.system.dao.StaffDao;

@SuppressWarnings("all")
@Transactional
@Service
public class CableTaskServiceImpl implements CableTaskService {
	
	@Resource
	private CableTaskDao cableTaskDao;
	
	@Resource
	private StaffDao staffDao;
	
	@Resource
	private RoleDao roleDao;
	@Override
	public Map<String, Object> taskQuery(HttpServletRequest request,
			UIPage pager) {
		String task_no = request.getParameter("task_no");
		String task_name = request.getParameter("task_name");
		String start_time = request.getParameter("start_time");
		String complete_time = request.getParameter("complete_time");
		if(start_time != null && !"".equals(start_time)){
			start_time += " 00:00:00";
		}
		String plan_type = request.getParameter("plan_type");
		if(complete_time != null && !"".equals(complete_time)){
			complete_time += " 23:59:59";
		}
		String staffId = request.getSession().getAttribute("staffId").toString();//当前用户
		
		Map map = new HashMap();
		map.put("TASK_NO", task_no);
		map.put("TASK_NAME", task_name);
		map.put("START_TIME", start_time);
		map.put("COMPLETE_TIME", complete_time);
		map.put("PLAN_TYPE", plan_type);

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {//省级管理员
			map.put("SON_AREA_ID", request.getParameter("son_area_id"));
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {//是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				map.put("SON_AREA_ID", request.getParameter("son_area_id"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {//是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				}
				/*else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {//如果是审核员只能查到本区域的
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} */
				//查询自己的任务
				else {
					map.put("INSPECTOR", staffId);
				}
			}
		}
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = cableTaskDao.taskQuery(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public JSONObject deleteTask(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String taskIds = request.getParameter("selected");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskIds", taskIds);
		try {
			cableTaskDao.deleteTask(params);
			cableTaskDao.deleteTaskDetail(params);
			result.put("status", true);
			result.put("msg", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", false);
			result.put("msg", "删除失败！");
		}
		return result;
	}

	@Override
	public Map<String, Object> getRole(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String roleNo = null;
		String staffId = request.getSession().getAttribute("staffId").toString();//当前用户
		//获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		for(Map<String, String> map : roleList){
			roleNo = map.get("ROLE_NO");
			if(RoleNo.GLY.equals(roleNo) 
					|| RoleNo.LXXJ_ADMIN.equals(roleNo) 
					|| RoleNo.LXXJ_ADMIN_AREA.equals(roleNo) 
					|| RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)){
				rs.put("admin", true);
				continue;
			}
			if(RoleNo.LXXJ_MAINTOR.equals(roleNo)){
				rs.put("maintor", true);
				continue;
			}
			if(RoleNo.LXXJ_AUDITOR.equals(roleNo)){
				rs.put("auditor", true);
				continue;
			}
		}
		return rs;
			
	}

}
