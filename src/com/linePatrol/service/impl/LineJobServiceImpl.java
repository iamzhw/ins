package com.linePatrol.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.dao.LineJobDao;
import com.linePatrol.service.LineJobService;
import com.linePatrol.util.DateUtil;

@Service
public class LineJobServiceImpl implements LineJobService {
	
	@Resource
	private LineJobDao lineJobDao;

	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		
		String job_name = request.getParameter("param_job_name");//计划名称
		String start_date = request.getParameter("param_start_date");//计划开始时间
		String end_date = request.getParameter("param_end_date");//计划结束时间
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("JOB_NAME", job_name);
		params.put("START_DATE", start_date);
		params.put("END_DATE", end_date);
		params.put("AREA_ID", request.getSession().getAttribute("areaId"));
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(params);
		
		List<Map<String,Object>> list = lineJobDao.query(query);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", list);
		map.put("total", query.getPager().getRowcount());
		return map;
	}
	
	@Override
	public Map<String, Object> queryJob(HttpServletRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("JOB_ID", request.getParameter("job_id"));//计划ID
		
		return lineJobDao.queryJob(params);
	}

	@Override
	public void insert(HttpServletRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("JOB_NAME", request.getParameter("job_name"));//计划名称
		params.put("START_DATE", request.getParameter("start_date"));//计划开始时间
		params.put("END_DATE", request.getParameter("end_date"));//计划结束时间
		params.put("CIRCLE_ID", request.getParameter("circle_id"));//计划周期
		params.put("FIBER_GRADE", request.getParameter("fiber_grade"));//干线等级
		params.put("CREATOR", request.getParameter("creator"));//创建者
		params.put("CREATE_DATE", DateUtil.getDate());//创建时间
		params.put("AREA_ID", request.getSession().getAttribute("areaId"));//区域ID
		
		lineJobDao.insert(params);
	}

	@Override
	public void update(HttpServletRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("JOB_ID", request.getParameter("job_id"));//计划名称
		params.put("JOB_NAME", request.getParameter("job_name"));//计划名称
		params.put("START_DATE", request.getParameter("start_date"));//计划开始时间
		params.put("END_DATE", request.getParameter("end_date"));//计划结束时间
		params.put("CIRCLE_ID", request.getParameter("circle_id"));//计划周期
		params.put("FIBER_GRADE", request.getParameter("fiber_grade"));//干线等级
		params.put("UPDATE", request.getParameter("update"));//修改者
		params.put("UPDATE_DATE", DateUtil.getDate());//修改时间
		
		lineJobDao.update(params);
	}

	@Override
	public void stop(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("JOB_ID", request.getParameter("ids"));//计划名称
		lineJobDao.stop(params);
	}

	@Override
	public List<Map<String, Object>> queryJobsByCycle(Map<String, Object> params) {
		return lineJobDao.queryJobsByCycle(params);
	}
	
	@Override
	public List<Map<String,Object>> queryStaffsByJob(Map<String,Object> params){
		return lineJobDao.queryStaffsByJob(params);
	}

	@Override
	public void inserTask(Map<String, Object> map) {
		
		map.put("TASK_ID", lineJobDao.getTaskId());//获取任务ID
		
//		if("1".equals(map.get("CIRCLE_ID"))){
//			lineJobDao.inserTaskByDay(map);//插入天任务
//		}
//		else if("2".equals(map.get("CIRCLE_ID"))){
//			lineJobDao.inserTaskByTwoDay(map);//插入天任务
//		}
		
		lineJobDao.inserTaskByCycle(map);//插入周期任务
		lineJobDao.insertTaskItem(map);//插入任务项
		lineJobDao.insertTaskOutSite(map);//插入任务关联的外力点
	}
	
	@Override
	public void insertGuardJobs(Map<String, Object> map){
		lineJobDao.insertGuardJobs(map);
	}
}
