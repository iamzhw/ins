package com.roomInspection.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.roomInspection.dao.TaskDao;
import com.roomInspection.service.TaskService;

/**
 * 
 * @ClassName: TaskServiceImpl
 * @Description: 巡检任务业务层实现类
 * 
 * @author huliubing
 * @since: 2014-8-5
 *
 */
@SuppressWarnings("all")
@Transactional
@Service
public class TaskServiceImpl implements TaskService {

	@Resource
	private TaskDao taskDao;
	
	@Override
	public Map<String, Object> query(HttpServletRequest request,UIPage pager) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		String task_name = request.getParameter("task_name");
		String room_type_id = request.getParameter("room_type_id");
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		map.put("TASK_NAME", task_name);
		map.put("ROOM_TYPE_ID", room_type_id);
		map.put("START_DATE", start_date);
		map.put("END_DATE", end_date);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map> taskList = taskDao.getTaskList(query);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("total", query.getPager().getRowcount());
		paramMap.put("rows", taskList);
		
		return paramMap;
	}
	
	@Override
	public Map<String, Object> getTaskDetails(HttpServletRequest request,UIPage pager)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("TASK_ID",request.getParameter("task_id"));
		map.put("ROOM_NAME", request.getParameter("room_name"));
		map.put("STATUS", request.getParameter("status"));
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map> taskDetailList = taskDao.getTaskDetails(query);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("total", query.getPager().getRowcount());
		paramMap.put("rows", taskDetailList);
		return paramMap;
	}

	@Override
	public List<Map> getDayTaskByJobId(Map map) {
		return taskDao.getDayTaskByJobId(map);
	}

	@Override
	public List<Map> getMonthTaskByJobId(Map map) {
		return taskDao.getMonthTaskByJobId(map);
	}

	@Override
	public List<Map> getWeekTaskByJobId(Map map) {
		return taskDao.getWeekTaskByJobId(map);
	}
	
	@Override
	public List<Map> getHalfYearTaskByJobId(Map map) {
		return taskDao.getHalfYearTaskByJobId(map);
	}
	
	@Override
	public List<Map> getYearTaskByJobId(Map map) {
		return taskDao.getYearTaskByJobId(map);
	}

	@Override
	public void insertIntoDayTask(Map map) {
		taskDao.insertIntoDayTask(map);
		
	}

	@Override
	public void insertIntoMonthTask(Map map) {
		taskDao.insertIntoMonthTask(map);
		
	}

	@Override
	public void insertIntoWeekTask(Map map) {
		taskDao.insertIntoWeekTask(map);
	}
	
	@Override
	public void insertIntoHalfYearTask(Map map) {
		taskDao.insertIntoHalfYearTask(map);
	}
	
	@Override
	public void insertIntoYearTask(Map map) {
		taskDao.insertIntoYearTask(map);
	}

	@Override
	public void insertIntoTaskDetail(Map map) {
		taskDao.insertIntoTaskDetail(map);
		
	}

}
