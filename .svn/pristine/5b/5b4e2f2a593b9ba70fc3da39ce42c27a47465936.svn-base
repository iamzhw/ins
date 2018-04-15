package com.zhxj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.CableTaskDao;
import com.cableInspection.dao.PointDao;
import com.system.constant.RoleNo;
import com.system.dao.RoleDao;
import com.zhxj.dao.ZhxjTaskDao;
import com.zhxj.service.ZhxjTaskService;

@Service
@Transactional
public class ZhxjTaskServiceImpl implements ZhxjTaskService {
	
	@Resource
	private ZhxjTaskDao zhxjTaskDao;
	
	@Resource
	private RoleDao roleDao;
	
	@Resource
	private CableTaskDao cableTaskDao;
	
	@Resource
	private PointDao pointDao;
	
	@Override
	public Map<String, Object> queryTask(HttpServletRequest request,
			UIPage pager) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("TASK_NO", request.getParameter("task_no"));
		map.put("TASK_NAME", request.getParameter("task_name"));
		map.put("AREA_ID", request.getParameter("area_id"));
		map.put("SON_AREA_ID", request.getParameter("son_area_id"));
		map.put("TASK_START_TIME", request.getParameter("task_start_time"));
		map.put("TASK_END_TIME", request.getParameter("task_end_time"));
		map.put("TASK_ORGIN", request.getParameter("task_orgin"));
		map.put("TASK_TYPE", request.getParameter("task_type"));
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = zhxjTaskDao.queryTask(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public Map<String, Object> getRole(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String roleNo = null;
		String staffId = request.getSession().getAttribute("staffId").toString();//当前用户
		//获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		//缆线巡检角色判断
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
		//干线巡检角色
		//光网助手
		return rs;
	}

	@Override
	public String getButton(HttpServletRequest request) {
		List<Map> list= zhxjTaskDao.getButton();
		JSONArray ja = new JSONArray();
		String json=ja.fromObject(list).toString();
		return json;
	}

	@Override
	public List<Map> getTaskType(HttpServletRequest request) {
		return zhxjTaskDao.getTaskType();
	}

	@Override
	public String taskDelete(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String taskId=request.getParameter("taskId");
		String taskType=request.getParameter("taskType");
		String zhxj_id=request.getParameter("taskType");
		try {
			//缆线巡检任务
			if(taskType.equals("201")||taskType.equals("202")||taskType.equals("203")){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("taskIds", taskId);
				cableTaskDao.deleteTask(params);
				cableTaskDao.deleteTaskDetail(params);
				zhxjTaskDao.removeTask(zhxj_id);
			}else if(taskType.equals("204")){//缆线看护任务
				pointDao.deleteTaskDetailByTaskId(taskId);
				pointDao.deleteTaskById(taskId);
				zhxjTaskDao.removeTask(zhxj_id);
			}else{
				result.put("status", false);
				result.put("msg", "该任务没有删除！");
				return result.toString();
			}
			result.put("status", true);
			result.put("msg", "删除成功！");
		} catch (Exception e) {
			result.put("status", false);
			result.put("msg", "删除失败！");
		}
		return result.toString();
	}

	@Override
	public void taskReceipt(HttpServletRequest request) {
		String taskType=request.getParameter("taskType");
		String zhxjId=request.getParameter("zhxjId");
		String operate= request.getParameter("operate");
		Map map = new HashMap();
		map.put("task_type", taskType);
		map.put("zhxj_id", zhxjId);
		//缆线巡检隐患工单
		if(taskType.equals("205")){
			int num=0;
			if(operate.equals("distributeBill")){
				num=9;
			}else if(operate.equals("audit")){
				num=4;
			}else if(operate.equals("forward")){
				num=2;
			}else if(operate.equals("audit_error")){
				num=5;
			}else if(operate.equals("archive")){
				num=4;
			}
			map.put("status", num);
			zhxjTaskDao.editTaskType(map);
		}

	}

}
