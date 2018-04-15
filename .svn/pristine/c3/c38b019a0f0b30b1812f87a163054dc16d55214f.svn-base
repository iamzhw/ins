package com.cableCheck.action;

import icom.util.BaseServletTool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.cableCheck.service.TaskMangerNewService;
import com.cableCheck.service.WrongPortReportService;
import com.linePatrol.util.MapUtil;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/CableTaskMangerNew")
@SuppressWarnings("all")
@Controller
public class TaskMangerNewController extends BaseAction{
	@Resource
	private TaskMangerNewService taskMangerNewService;
	@Autowired
	private WrongPortReportService wrongPortReportService;
	
	
	/**
	 * 跳转新增任务页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addTask.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/taskmanger/addTaskNew", null);
	}
	
	/**
	 * 新增任务-查询
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/addTaskQuery.do")
	public void addTaskQuery(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = taskMangerNewService.addTaskQuery(request, pager);
		write(response, map);
	}
	
	/**
	 * 跳转查看设备工单详情页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewDevice.do")
	public ModelAndView viewDevice(HttpServletRequest request,
			HttpServletResponse response){
		String equipmentId = request.getParameter("EQUIPMENT_ID");
		String TEAM_ID = request.getParameter("TEAM_ID");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String dz_end_time = request.getParameter("dz_end_time");
		String dz_start_time = request.getParameter("dz_start_time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQUIPMENT_ID", equipmentId);
		map.put("TEAM_ID", TEAM_ID);
		map.put("area", area);
		map.put("son_area", son_area);
		map.put("dz_end_time", dz_end_time);
		map.put("dz_start_time", dz_start_time);
		Map<String, Object> rs = new HashMap<String, Object>();//点击详情返回结果
		//查询出需要的数据
		rs=taskMangerNewService.queryEqpOrder(map);
		return new ModelAndView("cablecheck/taskmanger/viewDeviceNew", rs);
	}
	
	/**
	 * 进入人员列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectStaff.do")
	public ModelAndView selectStaff(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sbIds", request.getParameter("sbIds"));
		map.put("area", request.getParameter("area"));
		map.put("son_area", request.getParameter("son_area"));
		return new ModelAndView("cablecheck/taskmanger/staffNew", map);
	}
	
	/**
	 * 查询人员列表
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryHandler.do")
	public void queryHandler(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = taskMangerNewService.queryHandler(request, pager);
		write(response, map);
	}
	
	/**
	 * 派发任务
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/saveTask.do")
	public void saveTask(HttpServletRequest request, HttpServletResponse response) {
		String result = taskMangerNewService.saveTask(request);
		BaseServletTool.sendParam(response, result);
	}
	
	/**
	 * 新增任务导出
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/downTaskQuery.do")
	public void downTaskQuery(HttpServletRequest request, HttpServletResponse response
			) throws IOException {
		
		taskMangerNewService.downTaskQuery( request, response);
		
	}
	
	/**
	 * 所有设备所有端子导出
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/downPortQuery.do")
	public void downPortQuery(HttpServletRequest request, HttpServletResponse response
			) throws IOException {
		
		taskMangerNewService.downPortQuery( request, response);
		
	}
}
