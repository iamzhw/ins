package com.cableCheck.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.dao.CableMyTaskDao;
import com.cableCheck.service.DispatchRelationService;
import com.cableCheck.service.PatrolPlanService;
import com.cableInspection.service.CableService;

import net.sf.json.JSONObject;
import util.page.BaseAction;
import util.page.UIPage;

/**
 * 任务计划控制层
 * @author linzhengcheng
 *
 */
@RequestMapping(value = "/patrolPlan")
@SuppressWarnings("all")
@Controller
public class PatrolPlanController extends BaseAction{

	Logger logger = Logger.getLogger(PatrolPlanController.class);

	@Resource
	private CableMyTaskDao cableMyTaskDao;

	@Resource
	private CableService cableService;

	@Resource 
	private PatrolPlanService patrolPlanService;
	
	@Resource
	private DispatchRelationService dispatchRelationService; 

	/**
	 * 巡检计划列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cablecheck/patrol/patrolPlan", null);
	}

	@RequestMapping(value = "/getContractorList.do")
	@ResponseBody
	public Map getContractorList(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List<Map<String, String>> contractorList = 
				patrolPlanService.getContractorListBySonAreaId((String) request.getParameter("sonAreaId"));
		map.put("contractList", contractorList);
		return map;
	}
	

	@RequestMapping(value = "/addPatrolPlan.do")
	public ModelAndView addPlan(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = dispatchRelationService.getAreaList();
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		return new ModelAndView("cablecheck/patrol/patrolPlanAdd", rs);
	}

	/**
	 * 保存关键点计划
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/savePatrolPlan.do")
	@ResponseBody
	public void savePatrolPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = patrolPlanService.savePatrolPlan(request);
		write(response, map);
	}

	@RequestMapping(value = "/plan_query.do")
	public void queryPlan(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = patrolPlanService.planQuery(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/plan_delete.do")
	public void deletePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		Boolean status = true;
		try {
			patrolPlanService.deletePlan(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	@RequestMapping(value = "/query_staff.do")
	public void queryStaff(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = patrolPlanService.getSpectors(request, pager);
		write(response, map);
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
		return new ModelAndView("cablecheck/patrol/plan_task", rs);
	}
	
	@RequestMapping(value = "/save_task.do")
	public void saveTask(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Boolean isSuccess = false;
		try {
			isSuccess = patrolPlanService.saveTask(request);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	@RequestMapping(value = "/createTask.do")
	public void createTask(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Boolean isSuccess = false;
		try {
			isSuccess = patrolPlanService.createTask(request);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	/**
	 * 显示修改计划页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/patrolPlanEdit.do")
	public ModelAndView patrolPlanEdit(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = patrolPlanService.showEditPatrolPlan(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map = dispatchRelationService.getAreaList();
		String areaName = cableService.getAreaName(request);
		map.put("AREA_NAME", areaName);
		map.putAll(rs);
		return new ModelAndView("cablecheck/patrol/patrolPlanEdit", map);
	}
	
	/**
	 * 显示编辑计划规则页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editPatrolPlanRule.do")
	public ModelAndView editPatrolPlanRule(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = patrolPlanService.showEditPatrolPlan(request);
		return new ModelAndView("cablecheck/patrol/editPatrolPlanRule",rs);
	}
	
	/**
	 * 保存编辑关键点计划
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/saveEditPatrolPlanRule.do")
	@ResponseBody
	public void saveEditPatrolPlanRule(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = patrolPlanService.saveEditPatrolPlanRule(request);
		write(response, map);
	}
	
	/**
	 * 保存编辑关键点计划
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/saveEditPatrolPlan.do")
	@ResponseBody
	public void saveEditPointPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = patrolPlanService.saveEditPatrolPlan(request);
		write(response, map);
	}

	/**
	 * 根据网格获取相应的检查人
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getStaffListByWHWGId.do")
	@ResponseBody
	public Map getStaffListByWHWGId(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		String whwgId = request.getParameter("whwgId");
		List<Map<String, Object>> staffList = patrolPlanService.getStaffListByWHWGId(whwgId);
		map.put("staffList", staffList);
		return map;
	}
	
	/**
	 * 根据地市、区县、检查类型定位到相应规则
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getRule.do")
	@ResponseBody
	public Map getRule(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		Map map = new HashMap();
		String areaId = request.getParameter("areaId");
		String sonAreaId = request.getParameter("sonAreaId");
		String plan_type = request.getParameter("plan_type");
		
		map.put("AREA_ID", areaId);
		map.put("SON_AREA_ID", sonAreaId);
		map.put("PLAN_TYPE", plan_type);
		
		List<Map<String, Object>> ruleList = patrolPlanService.getRule(map);
		
		map.put("ruleList", ruleList);
		return map;
	}
	
	/**
	 * 根据规则 生成任务
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/createNewTask.do")
	public ModelAndView createNewTask(HttpServletRequest request,
			HttpServletResponse response){
		patrolPlanService.createNewTask(request);
		String plan_id = request.getParameter("plan_id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PLAN_ID", plan_id);
		return new ModelAndView("cablecheck/patrol/patrolPlanCreateTask",map);
	}
	
	/**
	 * 获取生成任务页面数据
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryNewTask.do")
	public void queryNewTask(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = patrolPlanService.getEqByPlanAndRuleForNewTask(request, pager);
		write(response, map);
	}
	
	/**
	 * 派发任务
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/distributeTask.do")
	public void distributeTask(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String result = null;
		try {
			result = patrolPlanService.distributeTask(request);
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		Map map = new HashMap();
		map.put("result", result);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	/**
	 * 点击设备数量弹出设备详情页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showEqForUpdate.do")
	public ModelAndView showEqForUpdate(HttpServletRequest request,
			HttpServletResponse response){
		String plan_id = request.getParameter("plan_id");
		String check_staff = request.getParameter("check_staff");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CHECK_STAFF", check_staff);
		map.put("PLAN_ID", plan_id);
		return new ModelAndView("cablecheck/patrol/showEqForUpdate",map);
	}
	
	/**
	 * 查询设备
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryEq.do")
	public void queryEq(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = patrolPlanService.queryEq(request, pager);
		write(response, map);
	}
	
	/**
	 * 删除设备
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteEq.do")
	public void deleteEq(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String result = null;
		try {
			result = patrolPlanService.deleteEq(request);
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		Map map = new HashMap();
		map.put("result", result);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	/**
	 * 新增任务计划页面，预览设备
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveEqByPlanRule.do")
	@ResponseBody
	public Map queryEqByPlanRule(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = patrolPlanService.saveEqByPlanRule(request);
		return map;
	}
	
	/**
	 * 查询预览设备
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryEqByPlanRule.do")
	public void queryEqByPlanRule(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		
		Map<String, Object> map = patrolPlanService.queryEqByPlanRule(request, pager);
		
		//2017年10月24日12:44:08  查询网格下的设备数
		//页面不再展示设备信息，展示网格设备的比例
//		Map<String, Object> map = patrolPlanService.queryEqpGroupGrid(request, pager);
		
		
		
		write(response, map);
	}
	
	/**
	 * 删除设备(预览)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteEqForPreview.do")
	public void deleteEqForPreview(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String result = null;
		try {
			result = patrolPlanService.deleteEqForPreview(request);
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		Map map = new HashMap();
		map.put("result", result);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	
	
	/**
	 * 确认选择设备(预览)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addSureSelect.do")
	public void addSureSelect(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String result = null;
		try {
			result = patrolPlanService.addSureSelect(request);
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		Map map = new HashMap();
		map.put("result", result);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	
	
	/**
	 * 切换到网格占比页面
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/getGridNum.do")
	public void getGridNum(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException{
		Map<String, Object> map = patrolPlanService.getGridNum(request, pager);
		write(response, map);
	}
	
	
	/**
	 * 弹出窗口用于预览设备新增设备
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showEqForUpdateForPreview.do")
	public ModelAndView showEqForUpdateForPreview(HttpServletRequest request,
			HttpServletResponse response){
		String area_id = request.getParameter("area_id");
		String son_area_id = request.getParameter("son_area_id");
		String whwg = request.getParameter("whwg");
		String plan_type = request.getParameter("plan_type");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AREA_ID", area_id);
		map.put("SON_AREA_ID",son_area_id);
		map.put("WHWG",whwg);
		map.put("PLAN_TYPE", plan_type);
		
		return new ModelAndView("cablecheck/patrol/showEqForUpdateForPreview",map);
	}
	

	/**
	 * 查询设备
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryEqForPreview.do")
	public void queryEqForPreview(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = patrolPlanService.queryEqForPreview(request, pager);
		write(response, map);
	}
	
	/**
	 * 预览设备新增
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addEqForPreview.do")
	public void addEqForPreview(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String result = null;
		try {
			result = patrolPlanService.addEqForPreview(request);
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		Map map = new HashMap();
		map.put("result", result);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	/**
	 * 跳转规则配置页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ruleIndex.do")
	public ModelAndView ruleIndex(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = dispatchRelationService.getAreaList();
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		return new ModelAndView("cablecheck/patrol/ruleIndex", rs);
	}
	
	/**
	 * 查询规则用于页面展示
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryRule.do")
	public void queryRule(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = patrolPlanService.queryRule(request, pager);
		write(response, map);
	}
	
	/**
	 * 跳转新增规则页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addRule.do")
	public ModelAndView addRule(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = dispatchRelationService.getAreaList();
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		return new ModelAndView("cablecheck/patrol/ruleAdd", rs);
	}
	
	
	/**
	 * 获取规则键值
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getRuleForAdd.do")
	@ResponseBody
	public Map getRuleForAdd(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		Map map = new HashMap();
		String plan_type = request.getParameter("plan_type");
		
		map.put("PLAN_TYPE", plan_type);
		
		List<Map<String, Object>> ruleList = patrolPlanService.getRuleForAdd(map);
		
		map.put("ruleList", ruleList);
		return map;
	}
	
	/**
	 * 更新规则
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/updateRule.do")
	@ResponseBody
	public void updateRule(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = null;
		try {
			result = patrolPlanService.updateRule(request);
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		Map map = new HashMap();
		map.put("result", result);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	
	/**
	 * 查询计划是否已生成任务
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/queryIfHavaTask.do")
	@ResponseBody
	public void queryIfHavaTask(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = null;
		try {
			result = patrolPlanService.queryIfHavaTask(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map map = new HashMap();
		map.put("result", result);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
}
