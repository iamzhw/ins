package com.roomInspection.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.roomInspection.service.JobService;
import com.roomInspection.service.TaskService;

/**
 * @ClassName: TaskController
 * @Description: 巡检任务管理
 * @author huliubing
 * @date: 2014-07-19
 *
 */
@SuppressWarnings("all")
@RequestMapping(value = "/JfxjTask")
@Controller
public class TaskController extends BaseAction{

	@Resource
	private TaskService taskService;
	
	@Resource
	private JobService jobService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//初始化机房类型信息
		List<Map> roomTypeList = jobService.getRoomTypes();
		map.put("roomTypes", roomTypeList);
		
		return new ModelAndView("roominspection/task/task_index", map);
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException
	{
		Map<String, Object> map = taskService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/detailTask.do")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		//初始化taskId
		map.put("taskId", request.getParameter("task_id"));
		
		return new ModelAndView("roominspection/task/task_detail", map);
	}
	
	@RequestMapping(value = "/queryDetailTask.do")
	public void queryDetail(HttpServletRequest request,
			HttpServletResponse response,UIPage pager) throws IOException {
		
		//查询任务详细信息
		Map<String,Object> map = taskService.getTaskDetails(request,pager);
		write(response, map);
	}
}
