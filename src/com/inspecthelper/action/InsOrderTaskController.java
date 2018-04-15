package com.inspecthelper.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.junit.runners.Parameterized.Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.inspecthelper.service.InsOrderTaskService;
import com.system.service.BaseMethodService;
import com.system.service.GeneralService;
import com.util.MapUtil;

/**
 * 我的工单控制层类
 *
 * @author lbhu
 * @since 2014-10-13
 *
 */
@SuppressWarnings("all")
@RequestMapping(value = "/insOrderTask")
@Controller
public class InsOrderTaskController extends BaseAction {
	
	@Resource
	private InsOrderTaskService insOrderTaskService;
	
	@Resource
	private GeneralService generalService;
	
	@Resource
	private BaseMethodService baseMethodService;
	
	/**
	 * 跳转至我的工单页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		HttpSession sessionPad = request.getSession();
		String staffNo = (String) sessionPad.getAttribute("staffNo");
		String areaId = (String) sessionPad.getAttribute("areaId");
		map.put("area", areaId);
		
		if(areaId.equals("20"))
		{
			areaId=(String) sessionPad.getAttribute("sonAreaId");
		}
		
		List<Map<String,String>> areaList = generalService.getSonAreaList(areaId);//获取所有子区域
		map.put("areaList", areaList);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffNo", staffNo);
		
		params.put("roleId", "12");//审核人员角色
		map.put("count1", insOrderTaskService.getUserRoleCount(params));//查询是否为审核人员
		
		params.put("roleId", "13");//维护人员角色
		map.put("count2", insOrderTaskService.getUserRoleCount(params));//查询是否为维护人员
		
		params.put("areaId", (String) sessionPad.getAttribute("areaId"));
		List<Map> targetList = insOrderTaskService.getEquTarget(params);//获取检查项
		map.put("targetList", targetList);
		
		List<Map> companyList = generalService.getCompanyList(params);//初始化公司信息
		map.put("companyList", companyList);
		
		return new ModelAndView("/inspecthelper/insorder/insorder_index",map);
	}
	
	/**
	 * 查询数据
	 * 
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value="/query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response,UIPage pager) throws IOException{
		Map map = insOrderTaskService.query(request, pager);
		write(response,map);
	}
	
	
	/**
	 * 跳转至工单派发页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toAllot.do")
	public ModelAndView toAllot(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		HttpSession sessionPad = request.getSession();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("areaId", (String) sessionPad.getAttribute("areaId"));
		List<Map> companyList = generalService.getCompanyList(params);//初始化公司信息
		map.put("companyList", companyList);
		return new ModelAndView("/inspecthelper/insorder/insorder_allot",map);
	}
	
	/**
	 * 完成工单派发
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/completeTask.do")
	public void completeTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insOrderTaskService.completeTask(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转至回单页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toHuidan.do")
	public ModelAndView toHuidan(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		HttpSession sessionPad = request.getSession();
		return new ModelAndView("/inspecthelper/insorder/insorder_hui",map);
	}
	
	/**
	 * 完成回单任务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/completeHuidanTask.do")
	public void completeHuidanTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insOrderTaskService.completeHuidanTask(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转至审核页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toCheck.do")
	public ModelAndView toCheck(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		String ip = baseMethodService.getValidFileServiceAccessIp(request);
		
		Map paramMap = new HashMap();
		String selected = request.getParameter("selected");
		paramMap.put("troubleId", selected);
		paramMap.put("ip", ip);
	
		List<Map> photoList = insOrderTaskService.findAllPhoto(paramMap);//获取图片
		map.put("photoList", photoList);
		
		List<Map> fileList = insOrderTaskService.findAllFile(paramMap);//获取文件
		map.put("fileList", fileList);
		
		HttpSession sessionPad = request.getSession();
		
		paramMap.put("areaId", (String) sessionPad.getAttribute("areaId"));
		List<Map> companyList = generalService.getCompanyList(paramMap);//初始化公司信息
		map.put("companyList", companyList);
		
		return new ModelAndView("/inspecthelper/insorder/insorder_check",map);
	}
	
	/**
	 * 完成审核工单任务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/completeCheckTask.do")
	public void completeCheckTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insOrderTaskService.completeCheckTask(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转至归档页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toArchive.do")
	public ModelAndView toArchive(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		return new ModelAndView("/inspecthelper/insorder/insorder_archive",map);
	}
	
	/**
	 * 完成工单归档任务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/completeArchiveTask.do")
	public void completeArchiveTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insOrderTaskService.completeArchiveTask(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转至退单页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toChargeback.do")
	public ModelAndView toChargeback(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		return new ModelAndView("/inspecthelper/insorder/insorder_chargeback",map);
	}
	
	/**
	 * 完成工单退单任务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/completeChargebackTask.do")
	public void completeChargebackTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insOrderTaskService.completeChargebackTask(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 跳转至转派页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toReassignment.do")
	public ModelAndView toReassignment(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		HttpSession sessionPad = request.getSession();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("areaId", (String) sessionPad.getAttribute("areaId"));
		List<Map> companyList = generalService.getCompanyList(params);//初始化公司信息
		map.put("companyList", companyList);
		return new ModelAndView("/inspecthelper/insorder/insorder_reassignment",map);
	}
	
	/**
	 * 完成工单转派任务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/completeReassignmentTask.do")
	public void completeReassignmentTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insOrderTaskService.completeReassignmentTask(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 完成批量转派工单任务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/completeBtnReassign.do")
	@ResponseBody
	public void completeBtnReassign(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insOrderTaskService.completeBtnReassign(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转至流程详情页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toShowDetail.do")
	public ModelAndView toShowDetail(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		List<Map> historyList = insOrderTaskService.getActionHistoryList(request);
		map.put("historyList", historyList);
		return new ModelAndView("/inspecthelper/insorder/insorder_history",map);
	}
	
	/**
	 * 跳转至工单详情页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toOrderDetail.do")
	public ModelAndView toOrderDetail(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		String ip = baseMethodService.getValidFileServiceAccessIp(request);
		
		Map paramMap = new HashMap();
		String selected = request.getParameter("selected");
		paramMap.put("troubleId", selected);
		paramMap.put("ip", ip);
		String troublecode = insOrderTaskService.findTroublecode(paramMap);
		
		List<Map> photoList = null;
		if(null != troublecode && !"".equals(troublecode)){
			paramMap.put("resourceCode", troublecode);
			photoList = insOrderTaskService.getLastTrouPhotoPath(paramMap);//获取整改前照片
		}
		
		map.put("photoList", photoList);
		
		List<Map> photoListNew = insOrderTaskService.findAllPhoto(paramMap);//获取整改后图片
		map.put("photoListNew", photoListNew);
		
		List<Map> fileList = insOrderTaskService.findAllFile(paramMap);//获取文件
		map.put("fileList", fileList);
		
		return new ModelAndView("/inspecthelper/insorder/insorder_detail",map);
	}
	
	/**
	 * 我的工单导出
	 * 
	 * @param request
	 * @param mm 模型对象
	 * @return
	 */
	@RequestMapping(value = "/export.do")
	public String export(HttpServletRequest request, ModelMap mm) {
		List<Map> dataList = insOrderTaskService.getOrderTaskList(request);
		String[] cols = new String[] {"OrderId","任务编码","状态","资源类别","资源编码","设备名称", "区域", "巡检人员",
				"巡检公司", "维护人员", "审核人员", "检查项", "问题描述", "上报方式", "上报日期"};
		
		String[] columnMethods = new String[] {"ORDER_ID",
				"TASK_CODE", "TRSTATE", "RES_TYPE", "EQUIPMENT_CODE", "EQUIPMENT_NAME",
				"SON_AREA", "UPLOAD_STAFF_NAME", "INS_COMPANY", "STAFF_NAME", "CHECK_STAFF_NAME",
				"TARGET_NAME", "REMARKS","SN","HAPPEN_DATE" };
		mm.addAttribute("name", "我的工单");
		mm.addAttribute("cols", cols);
		mm.addAttribute("dataList", dataList);
		mm.addAttribute("columnMethods",columnMethods);
		return "insOrderDataListExcel";
	}
	
	/**
	 * 根据公司信息获取员工ID
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStaffByCompany.do")
	@ResponseBody
	public Map getStaffByCompany(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		String companyId = request.getParameter("companyId");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("companyId", companyId);
		paramMap.put("staffType", "repaire");
		List<Map> staffList = insOrderTaskService.getAllStaffByTroubleOrderList(paramMap);
		map.put("staffList", staffList);
		return map;
	}
}
