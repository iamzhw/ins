package com.cableInspection.action;

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

import util.page.BaseAction;
import util.page.UIPage;

/**
 * @ClassName: CablePlanController
 * @Description: 寻线任务管理
 * @author xiazy
 * @date: 2014-6-20
 * 
 */
@RequestMapping(value = "/CablePlan")
@SuppressWarnings("all")
@Controller
public class CablePlanController extends BaseAction {
	@Resource
	private CablePlanService cablePlanService;
	@Resource
	private CableService cableService;

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("isSonAreaAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN_SON_AREA));
		rs.put("isAreaAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN_AREA));
		rs.put("isAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN));
		return new ModelAndView("cableinspection/cable/plan/plan_index", rs);
	}

	@RequestMapping(value = "/plan_query.do")
	public void queryPlan(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = cablePlanService.planQuery(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/plan_add.do")
	public ModelAndView addPlan(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = cablePlanService.addPlan(request);
		return new ModelAndView("cableinspection/cable/plan/plan_add", rs);
	}
	
	@RequestMapping(value = "/getCable.do")
	public void getCable(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jsonString = cableService.getCable(request);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
	}

	@RequestMapping(value = "/plan_save.do")
	public void savePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		Boolean isSuccess = true;
		try {
			cablePlanService.save(request);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	@RequestMapping(value = "/plan_delete.do")
	public void deletePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		Boolean status = true;
		try {
			cablePlanService.deletePlan(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	@RequestMapping(value = "/show.do")
	public ModelAndView show(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		rs.put("PLAN_ID", request.getParameter("planId"));
		return new ModelAndView("cableinspection/cable/plan/plan_query", rs);
	}

	// 显示已有光缆段
	@RequestMapping(value = "/getPlanCable.do")
	public void getPlanCable(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jsonString = cablePlanService.getPlanCable(request);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
	}

	// 显示计划外光缆段
	@RequestMapping(value = "/getPlanExitCable.do")
	public void getPlanExitCable(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jsonString = cablePlanService.getPlanExitCable(request);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
	}

	@RequestMapping(value = "/plan_edit.do")
	public ModelAndView editPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = cablePlanService.editPlan(request);
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		return new ModelAndView("cableinspection/cable/plan/plan_edit", rs);
	}

	@RequestMapping(value = "/plan_update.do")
	public void updatePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean isSuccess = true;
		try {
			cablePlanService.updatePlan(request);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	// 获取员工
	@RequestMapping(value = "/task_add.do")
	public ModelAndView taskAdd(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		/*
		 * List<Map<String, String>> spectorList = cablePlanService
		 * .getSpectors(request);
		 */
		// rs.put("spectorList", spectorList);
		return new ModelAndView("cableinspection/cable/plan/plan_task", rs);
	}
	
	@RequestMapping(value = "/task_edit.do")
	public ModelAndView taskEdit(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		/*
		 * List<Map<String, String>> spectorList = cablePlanService
		 * .getSpectors(request);
		 */
		// rs.put("spectorList", spectorList);
		return new ModelAndView("cableinspection/cable/plan/plan_task_edit", rs);
	}

	@RequestMapping(value = "/query_staff.do")
	public void queryStaff(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = cablePlanService.getSpectors(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/query_JYHstaff.do")
	public void queryJYHStaff(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = cablePlanService.queryJYHStaff(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/save_task.do")
	public void saveTask(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Boolean isSuccess = false;
		try {
			isSuccess = cablePlanService.saveTask(request);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	@RequestMapping(value = "/edit_inspector.do")
	public void editInspector(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Boolean isSuccess = false;
		try {
			isSuccess = cablePlanService.editInspector(request);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	/**
	 * 显示增加关键点计划页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addPointPlan.do")
	public ModelAndView addPointPlan(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = cablePlanService.showAddPointPlan();
		return new ModelAndView("cableinspection/cable/plan/plan_point_add", rs);
	}
	/**
	 * 查询关键点
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/queryPoint.do")
	@ResponseBody
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = cablePlanService.queryPoint(request, pager);
		write(response, map);
	}
	/**
	 * 保存关键点计划
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/savePointPlan.do")
	@ResponseBody
	public void savePointPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = cablePlanService.savePointPlan(request);
		write(response, map);
	}
	/**
	 * 显示修改关键点计划页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editPointPlan.do")
	public ModelAndView editPointPlan(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = cablePlanService.showEditPointPlan(request);
		return new ModelAndView("cableinspection/cable/plan/plan_point_edit", rs);
	}
	
	/**
	 * 查询已选择的关键点
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/searchPointData.do")
	@ResponseBody
	public void searchPointData(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = cablePlanService.searchPointData(request, pager);
		write(response, map);
	}
	/**
	 * 保存编辑关键点计划
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/saveEditPointPlan.do")
	@ResponseBody
	public void saveEditPointPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = cablePlanService.saveEditPointPlan(request);
		write(response, map);
	}
	
}
