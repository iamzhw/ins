package com.zhxj.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cableInspection.service.CablePlanService;
import com.cableInspection.service.CableService;
import com.system.constant.RoleNo;
import com.zhxj.service.ZhxjPlanService;
import com.zhxj.service.ZhxjTaskService;

import util.page.BaseAction;
import util.page.UIPage;

/**
 * @ClassName: zhxjPlanController
 * @Description: 综合巡检计划融合
 * @author ningruofan
 * @date: 2017-12-13
 * 
 */
@RequestMapping(value = "/ZhxjPlan")
@SuppressWarnings("all")
@Controller
public class ZhxjPlanController extends BaseAction {
	@Resource
	private ZhxjTaskService zhxjTaskService;
	
	@Resource
	private ZhxjPlanService zhxjPlanService;
	
	@RequestMapping(value = "/task_index.do")
	public ModelAndView indexTask(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = zhxjTaskService.getRole(request);
		rs.put("buttons", zhxjTaskService.getButton(request));
		rs.put("types", zhxjTaskService.getTaskType(request));
		return new ModelAndView("zhxj/zhxj_task", rs);
	}
	
	@RequestMapping(value = "/task_query.do")
	public void queryTask(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = zhxjTaskService.queryTask(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/getButtons.do")
	public void getButtons(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String rs=zhxjTaskService.getButton(request);
		response.getWriter().write(rs);
	}
	
	@RequestMapping(value = "/taskDelete.do")
	public void taskDelete(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String rs=zhxjTaskService.taskDelete(request);
		response.getWriter().write(rs);
	}
	
	@RequestMapping(value = "/taskReceipt.do")
	public void taskReceipt(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		zhxjTaskService.taskReceipt(request);
	}
	
	@RequestMapping(value = "/plan_index.do")
	public ModelAndView indexplan(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = zhxjTaskService.getRole(request);
		rs.put("buttons", zhxjTaskService.getButton(request));
		rs.put("types", zhxjTaskService.getTaskType(request));
		return new ModelAndView("zhxj/plan/plan_index", rs);
	}
	
	@RequestMapping(value = "/plan_query.do")
	public void queryplan(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = zhxjPlanService.queryPlan(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/planAddSelected.do")
	public ModelAndView planAddSelected(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap();
		return new ModelAndView("zhxj/plan/plan_add_selected", rs);
	}
	
	@RequestMapping(value = "/planDelete.do")
	public void planDelete(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String rs=zhxjPlanService.planDelete(request);
		response.getWriter().write(rs);
	}
}
