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


import com.cableCheck.service.TaskMangerService;
import com.cableCheck.service.WrongPortReportService;
import com.linePatrol.util.MapUtil;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/CableTaskManger")
@SuppressWarnings("all")
@Controller
public class TaskMangerController extends BaseAction{
	@Resource
	private TaskMangerService taskMangerService;
	@Autowired
	private WrongPortReportService wrongPortReportService;
	
	/**
	 * 任务管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cablecheck/taskmanger/taskManger", null);
	}
	
	/**
	 * 任务管理-查询
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = taskMangerService.getTask(request, pager);
		write(response, map);
	}
	
	/**
	 * 跳转新增任务页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addTask.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/taskmanger/addTask", null);
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
		Map<String, Object> map = taskMangerService.addTaskQuery(request, pager);
		write(response, map);
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
		return new ModelAndView("cablecheck/taskmanger/staff", map);
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
		Map<String, Object> map = taskMangerService.queryHandler(request, pager);
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
		String result = taskMangerService.saveTask(request);
		BaseServletTool.sendParam(response, result);
	}
	
	/**
	 * 跳转查看设备页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewDevice.do")
	public ModelAndView viewDevice(HttpServletRequest request,
			HttpServletResponse response){
		String equipmentId = request.getParameter("EQUIPMENT_ID");
		String area = request.getParameter("area");
		String dz_end_time = request.getParameter("dz_end_time");
		String dz_start_time = request.getParameter("dz_start_time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQUIPMENT_ID", equipmentId);
		map.put("area", area);
		map.put("dz_end_time", dz_end_time);
		map.put("dz_start_time", dz_start_time);
		return new ModelAndView("cablecheck/taskmanger/viewDevice", map);
	}
	
	/**
	 * 设备信息-查询
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/equipmentQuery.do")
	public void equipmentQuery(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = taskMangerService.equipmentQuery(request, pager);
		write(response, map);
	}
	
	
	/**
	 * 跳转查看端子页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewDuanZi.do")
	public ModelAndView viewDuanzi(HttpServletRequest request,
			HttpServletResponse response){
		String EQUIPMENT_ID = request.getParameter("EQUIPMENT_ID");
		String areaId = request.getParameter("areaId");
		String dz_end_time = request.getParameter("dz_end_time");
		String dz_start_time = request.getParameter("dz_start_time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQUIPMENT_ID", EQUIPMENT_ID);
		map.put("areaId", areaId);
		map.put("dz_end_time", dz_end_time);
		map.put("dz_start_time", dz_start_time);
		return new ModelAndView("cablecheck/taskmanger/viewDuanZi", map);
	}
	
	
	/**
	 * 端子信息-查询
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/terminalQuery.do")
	public void terminalQuery(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = taskMangerService.terminalQuery(request, pager);
		write(response, map);
	}
	
	/**
	 * 进入设备检查记录页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceRecords.do")
	public ModelAndView deviceRecords(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cablecheck/taskmanger/deviceRecords", null);
	}
	
	/**
	 * 设备检查记录查询
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryDeviceRecords.do")
	public void queryDeviceRecords(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = taskMangerService.queryDeviceRecords(request, pager);
		write(response, map);
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
		
		taskMangerService.downTaskQuery( request, response);
		
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
		
		taskMangerService.downPortQuery( request, response);
		
	}
	/**
	 * 设备信息导出
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/downEquipmentQuery.do")
	public void downEquipmentQuery(HttpServletRequest request, HttpServletResponse response 
			) throws IOException {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		taskMangerService.downEquipmentQuery(para, request, response);
		
	}
	/**
	 * 端子信息-导出
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/downTerminalQuery.do")
	public void downTerminalQuery(HttpServletRequest request, HttpServletResponse response
			) throws IOException {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		taskMangerService.downTerminalQuery(para, request, response);
	}

	
	
		
	
		
		@RequestMapping(value = "/indexByElectron.do")
		public ModelAndView indexByElectron(HttpServletRequest request, HttpServletResponse response) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("area", wrongPortReportService.selArea());
			return new ModelAndView("cablecheck/electronArchives/ElectronArchives", map);
		}
		
		@RequestMapping(value="/electronArchives.do")
		public void checkQualityReport(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
			
			Map<String, Object> map = taskMangerService.queryElectron(request, pager);
			write(response, map);
		}
		
		
		@RequestMapping(value="/exportExcelByElectron.do")
		public void exportExcel(HttpServletRequest request, HttpServletResponse response){
			//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			taskMangerService.electronArchivesDownload(request, response);
		}
		/**
		 * 进入人员列表页面
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "/electronArchivesSelectStaff.do")
		public ModelAndView electronArchivesSelectStaff(HttpServletRequest request,
				HttpServletResponse response){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sbIds", request.getParameter("sbIds"));
			map.put("area", request.getParameter("area"));
			map.put("son_area", request.getParameter("son_area"));
			return new ModelAndView("cablecheck/electronArchives/staff", map);
		}
		/**
		 * 派发任务
		 * @param request
		 * @param response
		 */
		@ResponseBody
		@RequestMapping("/saveElectronTask.do")
		public void saveElectronTask(HttpServletRequest request, HttpServletResponse response) {
			String result = taskMangerService.saveElectronTask(request);
			BaseServletTool.sendParam(response, result);
		}
		
		/**
		 * 承包人派发
		 * @param request
		 * @param response
		 */
		@ResponseBody
		@RequestMapping("/send_contract_name.do")
		public void send_contract_name(HttpServletRequest request, HttpServletResponse response) {
			String result = taskMangerService.send_contract_name(request);
			BaseServletTool.sendParam(response, result);
		}
}
