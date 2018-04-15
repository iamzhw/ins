package com.linePatrol.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.dao.LineTaskDao;
import com.linePatrol.service.LineTaskService;

@Service
public class LineTaskServiceImpl implements LineTaskService {

	@Resource
	private LineTaskDao lineTaskDao;
	
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		String task_name = request.getParameter("param_task_name");//任务名称
		String staff_name = request.getParameter("param_staff_name");//巡线人名称
		String task_type = request.getParameter("param_task_type");//任务类型
		String start_date = request.getParameter("param_start_date");//任务开始时间
		String end_date = request.getParameter("param_end_date");//任务结束时间
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("TASK_NAME", task_name);
		params.put("STAFF_NAME", staff_name);
		params.put("TASK_TYPE", task_type);
		params.put("START_DATE", start_date);
		params.put("END_DATE", end_date);
		params.put("AREA_ID", request.getSession().getAttribute("areaId"));
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(params);
		
		List<Map<String,Object>> list = lineTaskDao.query(query);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", list);
		map.put("total", query.getPager().getRowcount());
		return map;
	}
	
	@Override
	public Map<String, Object> getTask(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("TASK_ID", request.getParameter("task_id"));
		
		Map<String,Object> taskMap = lineTaskDao.getTask(params);//查询任务
		
		List<Map<String,Object>> taskItems = lineTaskDao.getTaskItems(params);
		String lineIds="";
		String lineNames="";
		for(Map<String,Object> taskItem : taskItems){
			lineIds+="," + taskItem.get("LINE_ID").toString();
			lineNames+="," + taskItem.get("LINE_NAME").toString();
		}
		taskMap.put("lineIds", lineIds.substring(1));
		taskMap.put("lineNames", lineNames.substring(1));
		
		return taskMap;
	}
	
	@Override
	public List<Map<String, Object>> getInpectStaff(String areaId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("AREA_ID", areaId);
		return lineTaskDao.getInpectStaff(params);
	}

	
	@Transactional
	@Override
	public void insert(HttpServletRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		int taskId = lineTaskDao.getTaskId();
		params.put("TASK_ID", taskId);
		params.put("TASK_NAME", request.getParameter("task_name"));//任务名称
		params.put("START_DATE", request.getParameter("start_date"));//任务开始时间
		params.put("END_DATE", request.getParameter("end_date"));//任务结束时间
		params.put("INSPECT_ID", request.getParameter("inspect_id"));//巡检人
		params.put("AREA_ID", request.getParameter("area_id"));//区域ID
		
		lineTaskDao.insert(params);
		
		String lineIds = request.getParameter("lineIds");
		String[] lineIdArr = lineIds.split(",");
	
		for(String lineId : lineIdArr){
			Map<String,Object> taskItem = new HashMap<String,Object>();
			taskItem.put("TASK_ID", taskId);
			taskItem.put("LINE_ID", lineId);
			lineTaskDao.insertTaskItem(taskItem);
		}
	}

	@Override
	public void update(HttpServletRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("TASK_ID", request.getParameter("task_id"));//任务ID
		params.put("INSPECT_ID", request.getParameter("inspect_id"));//巡检人ID
		params.put("TASK_NAME", request.getParameter("task_name"));//任务名称
		params.put("START_DATE", request.getParameter("start_date"));//任务开始时间
		params.put("END_DATE", request.getParameter("end_date"));//任务结束时间
		
		lineTaskDao.update(params);
	}

	@Override
	public void stop(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("TASK_ID", request.getParameter("selected"));//任务ID
		lineTaskDao.stop(params);
	}

	@Override
	public List<Map<String, Object>> getCableList(HttpServletRequest request) {
	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("AREA_ID", String.valueOf(request.getSession().getAttribute("areaId")));
		return lineTaskDao.getCableList(params);
	}

	@Override
	public List<Map<String, Object>> getRelayList(HttpServletRequest request) {

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("CABLE_ID", request.getParameter("cable_id"));
		return lineTaskDao.getRelayList(params);
	}

	@Override
	public List<Map<String, Object>> getLineList(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("AREA_ID", String.valueOf(request.getSession().getAttribute("areaId")));
		return lineTaskDao.getLineList(params);
	}
	
	@Override
	public List<Map<String,Object>> getTaskDetail(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("TASK_ID", request.getParameter("task_id"));//任务ID
		return lineTaskDao.getTaskDetail(params);
	}
	
	@Override
	public List<Map<String,Object>> getTaskOutSiteDetail(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("TASK_ID", request.getParameter("task_id"));//任务ID
		return lineTaskDao.getTaskOutSiteDetail(params);
	}
}
