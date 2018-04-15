package com.cableInspection.action;

import icom.util.BaseServletTool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableInspection.service.PointService;
import com.system.constant.RoleNo;

@SuppressWarnings("all")
@RequestMapping(value = "/Care")
@Controller
public class PointController extends BaseAction {

	@Resource
	private PointService pointService;

	@RequestMapping(value = "/plan_index.do")
	public ModelAndView indexPlan(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("isSonAreaAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN_SON_AREA));
		rs.put("trouble", pointService.getTroubleType());
		return new ModelAndView("cableinspection/point/plan_index", rs);
	}

	@RequestMapping(value = "/plan_query.do")
	public void queryPlan(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = pointService.planQuery(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/plan_add.do")
	public ModelAndView addPlan(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = pointService.getUserArea(request);
		String plan_kind = request.getParameter("plan_kind");
		// 外力点看护计划
		if (plan_kind != null && "3".equals(plan_kind)) {
			return new ModelAndView("cableinspection/point/plan_add_keep", rs);
		} else {
			// 外力点检查计划
			return new ModelAndView("cableinspection/point/plan_add", rs);
		}

	}

	@RequestMapping(value = "/plan_getPoints.do")
	public void getPointsForPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Map> datas = pointService.getPoints(request);
		response.getWriter().write(JSONArray.fromObject(datas).toString());
	}

	@RequestMapping(value = "/plan_save.do")
	public void savePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean isSuccess = pointService.save(request);
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	@RequestMapping(value = "/plan_delete.do")
	public void deletePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		Boolean status = true;
		try {
			pointService.deletePlan(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	@RequestMapping(value = "/plan_edit.do")
	public ModelAndView editPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = pointService.editPlan(request);
		return new ModelAndView("cableinspection/point/plan_edit", rs);
	}

	@RequestMapping(value = "/planEditTakeCare.do")
	public ModelAndView planEditTakeCare(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = pointService.editKeepPlan(request);
		return new ModelAndView("cableinspection/point/plan_edit_keep", rs);
	}

	@RequestMapping(value = "/plan_getPlanDetail.do")
	public void getPlanSelectedPoints(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> datas = pointService.getPlanDetail(request);
		response.getWriter().write(JSONObject.fromObject(datas).toString());
	}

	@RequestMapping(value = "/plan_update.do")
	public void updatePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean isSuccess = pointService.updatePlan(request);
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	@RequestMapping(value = "/task_add.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("planSelected", request.getParameter("planSelected"));
		return new ModelAndView("cableinspection/point/task_add", rs);
	}

	@RequestMapping(value = "/task_save.do")
	public void saveTask(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean isSuccess = pointService.saveTask(request);
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	/**
	 * 
	 * @Function: com.cableInspection.action.PointController.editPointKeep
	 * @Description:编辑外力点看护操作
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * 
	 * @date:2014-7-31 下午2:00:59
	 * 
	 * @Modification History:
	 * @date:2014-7-31 @author:Administrator create
	 */
	@RequestMapping(value = "/editPointKeep.do")
	public void editPointKeep(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean isSuccess = pointService.updatePointKeep(request);
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	@RequestMapping(value = "/task_index.do")
	public ModelAndView indexTask(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = pointService.getRole(request);
		rs.put("isSonAreaAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN_SON_AREA));
		return new ModelAndView("cableinspection/point/task_index", rs);
	}

	@RequestMapping(value = "/task_query.do")
	public void queryTask(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = pointService.taskQuery(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/plan_check.do")
	public void checkPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean isSuccess = pointService.checkPlan(request);
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Prama", "no-cache");
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	@RequestMapping(value = "/bill_index.do")
	public ModelAndView indexBill(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = pointService.indexBill(request);
		return new ModelAndView("cableinspection/point/bill_index", rs);
	}

	@RequestMapping(value = "/bill_query.do")
	public void queryBill(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = pointService.billQuery(request, pager);
		write(response, map);
	}

	@RequestMapping("bill_handle")
	public void handleBill(HttpServletRequest request,
			HttpServletResponse response) {
		String result = pointService.handleBill(request);
		BaseServletTool.sendParam(response, result);
	}

	@RequestMapping("bill_selectStaff")
	public ModelAndView selectBillStaff(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = pointService.selectBillStaff(request);
		return new ModelAndView("cableinspection/point/bill_handle", rs);
	}

	@RequestMapping("bill_info")
	public ModelAndView billInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = pointService.billInfo(request);
		return new ModelAndView("cableinspection/point/bill_info", rs);
	}

	@RequestMapping("bill_flow")
	public ModelAndView billFlow(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = pointService.billFlow(request);
		return new ModelAndView("cableinspection/point/bill_flow", rs);
	}
	
	@RequestMapping("bill_export")
	public void billExport(HttpServletRequest request,
			HttpServletResponse response) {
		 try {
			pointService.billExport(request,response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("task_queryPlans")
	@ResponseBody
	public void queryPlans(HttpServletRequest request,
			HttpServletResponse response) {
		String result = pointService.queryPlans(request);
		BaseServletTool.sendParam(response, result);
	}

	@RequestMapping("show")
	public ModelAndView show(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("plan_id", request.getParameter("plan_id"));
		return new ModelAndView("cableinspection/point/plan_query", rs);
	}

	@RequestMapping(value = "/task_delete.do")
	public JSONObject deleteTask(HttpServletRequest request,
			HttpServletResponse response) {
		return pointService.deleteTaskById(request);
	}
	@RequestMapping(value = "/query_keep_staff.do")
	public void query_keep_staff(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = pointService.getKeepSpectors(request, pager);
		write(response, map);
	}
}
