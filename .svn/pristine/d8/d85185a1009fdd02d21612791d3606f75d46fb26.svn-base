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

import com.roomInspection.dao.JobDao;
import com.roomInspection.service.JobService;

/**
 * 
 * @ClassName: JobServiceImpl
 * @Description:巡检计划业务层实现类
 * 
 * @author huliubing
 * @since: 2014-8-5
 *
 */
@SuppressWarnings("all")
@Transactional
@Service
public class JobServiceImpl implements JobService {

	@Resource
	private JobDao jobDao;
	
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("JOB_NAME", request.getParameter("job_name"));
		map.put("CIRCLE_ID", request.getParameter("circle_id"));
		map.put("ROOM_TYPE_ID", request.getParameter("room_type_id"));
		map.put("AREA_ID", request.getParameter("area_id"));
		map.put("CREATOR", request.getParameter("creator"));
		map.put("CREATE_DATE", request.getParameter("create_date"));
		
		//封装查询条件
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> olists = jobDao.query(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}


	@Override
	public void delete(HttpServletRequest request) {
		
		String selected = request.getParameter("selected");
		HttpSession session = request.getSession();
		Map map = new HashMap();
		String[] jobs = selected.split(",");
		String job_ids = "";
		for (int i = 0; i < jobs.length; i++) {
			job_ids +=jobs[i]+",";
		}
		if(job_ids.endsWith(",")){
			job_ids=job_ids.substring(0,job_ids.length()-1);
		}
		map.put("JOB_ID", job_ids);
		
		jobDao.delete(map);
	}
	
	@Override
	public void stop(HttpServletRequest request)
	{
		String selected = request.getParameter("selected");
		HttpSession session = request.getSession();
		Map map = new HashMap();
		String[] jobs = selected.split(",");
		String job_ids = "";
		for (int i = 0; i < jobs.length; i++) {
			job_ids +=jobs[i]+",";
		}
		if(job_ids.endsWith(",")){
			job_ids=job_ids.substring(0,job_ids.length()-1);
		}
		map.put("JOB_ID", job_ids);
		
		jobDao.stop(map);
	}

	@Override
	public Map<String, Object> edit(HttpServletRequest request) {
		
		int id = Integer.parseInt(String.valueOf(request.getParameter("job_id")));
		Map job = jobDao.getJob(id);
		return job;
	}
	
	@Override
	public void insert(HttpServletRequest request) throws Exception {
		String job_name = request.getParameter("job_name");
		String circle_id = request.getParameter("circle_id");
		String room_type_id = request.getParameter("room_type_id");
		String area_id = request.getParameter("area_id");
		String content = request.getParameter("content");
		String creator = request.getParameter("creator");
		String create_date = request.getParameter("create_date");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("JOB_NAME", job_name);
		map.put("CIRCLE_ID", circle_id);
		map.put("ROOM_TYPE_ID", room_type_id);
		map.put("AREA_ID", area_id);
		map.put("CONTENT", content);
		map.put("CREATOR", creator);
		map.put("CREATE_DATE", create_date);
		jobDao.insert(map);
		
	}

	@Override
	public void update(HttpServletRequest request) throws Exception {
		String job_id = request.getParameter("job_id");
		String job_name = request.getParameter("job_name");
		String circle_id = request.getParameter("circle_id");
		String room_type_id = request.getParameter("room_type_id");
		String area_id = request.getParameter("area_id");
		String content = request.getParameter("content");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("JOB_ID", job_id);
		map.put("JOB_NAME", job_name);
		map.put("CIRCLE_ID", circle_id);
		map.put("ROOM_TYPE_ID", room_type_id);
		map.put("AREA_ID", area_id);
		map.put("CONTENT", content);
		jobDao.update(map);
		
	}


	@Override
	public List<Map> getCircles() {
		List<Map> circleList = jobDao.getCircles();
		return circleList;
	}


	@Override
	public List<Map> getRoomTypes() {
		List<Map> roomTypeList = jobDao.getRoomTypes();
		return roomTypeList;
	}
	
	
	@Override
	public List<Map> getAreaList(int areaId)
	{
		return jobDao.getAreaList(areaId);
	}


	@Override
	public List<Map> getJobsByCircleId(int circleId) {
		return jobDao.getJobsByCircleId(circleId);
	}

}
