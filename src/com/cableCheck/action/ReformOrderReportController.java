package com.cableCheck.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableCheck.service.CheckQualityReportService;
import com.cableCheck.service.ReformOrderReportService;
import com.linePatrol.util.MapUtil;

@RequestMapping(value = "/ReformOrderReport")
@SuppressWarnings("all")
@Controller
public class ReformOrderReportController extends BaseAction{
	Logger logger = Logger.getLogger(ReformOrderReportController.class);
	
	@Autowired
	private ReformOrderReportService reformOrderReportService;
	private CheckQualityReportService checkQualityReportService;

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", reformOrderReportService.selArea());
		return new ModelAndView("cablecheck/ReformOrderReport", map);
	}
	
	@RequestMapping(value="/ReformOrderReport.do")
	public void ReformOrderReport(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = reformOrderReportService.query(request, pager);
		write(response, map);
	}
	@RequestMapping(value="/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		reformOrderReportService.reformOrderReportDownload(para, request, response);
		
	}
	@RequestMapping(value="/ReformOrderReportByCity.do")
	public void ReformOrderReportByCity(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		Map<String, Object> map = reformOrderReportService.queryByCity(request, pager);
		write(response, map);
	}
	@RequestMapping(value="/exportExcelByCity.do")
	public void exportExcelByCity(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		reformOrderReportService.reformOrderReportDownloadByCity(para, request, response);
		
	}
	
	@RequestMapping(value="/queryZgEqp.do")
	public ModelAndView queryEqp(HttpServletRequest request, HttpServletResponse response){ 
	    String type = request.getParameter("type");
		String areaId = request.getParameter("AREAID");
		String task_start_time = request.getParameter("task_start_time");//任务开始时间
		String task_end_time = request.getParameter("task_end_time");//任务结束时间
		String task_pstart_time = request.getParameter("task_pstart_time");//派发开始时间
		String task_pend_time = request.getParameter("task_pend_time");//派发结束时间
		String task_mstart_time = request.getParameter("task_mstart_time");//回单开始时间
		String task_mend_time = request.getParameter("task_mend_time");//回单结束时间
		String task_astart_time = request.getParameter("task_astart_time");//审核开始时间
		String task_aend_time = request.getParameter("task_aend_time");//审核结束时间
		String WLJB_ID = request.getParameter("WLJB_ID");//网络级别
	
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", areaId);
		map.put("type", type);
		map.put("task_start_time", task_start_time);
		map.put("task_end_time", task_end_time);
		map.put("task_pstart_time", task_pstart_time);
		map.put("task_pend_time", task_pend_time);
	
		map.put("task_mstart_time", task_mstart_time);
		map.put("task_mend_time", task_mend_time);
		map.put("task_astart_time", task_astart_time);
		map.put("task_aend_time", task_aend_time);
		map.put("WLJB_ID", WLJB_ID);
		// type 12,13,14,15为端子页面
		if("12".equals(type)||"13".equals(type)||"14".equals(type)||"15".equals(type)||"22".equals(type)||"23".equals(type)){
			 return new ModelAndView("cablecheck/QueryZgPort",map);
			
		}else{
			 return new ModelAndView("cablecheck/QueryZgEqpNum",map);			
		}	   
	}
	
	@RequestMapping(value = "/zgEquipmentQuery.do")
	public void equipmentNumQuery(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
	 
		Map<String, Object> map =  reformOrderReportService.zgEquipmentQuery(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value="/equipmentexportExcel.do")
	public void equipmentexportExcel(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		
		reformOrderReportService.equipmentDownload(para, request, response);
		
	}
	
	 
	 @RequestMapping(value = "/portQuery.do")
		public void portQuery(HttpServletRequest request, HttpServletResponse response,
				UIPage pager) throws IOException {
		 
			Map<String, Object> map =  reformOrderReportService.portQuery(request, pager);
			write(response, map);
		}
	 
	 @RequestMapping(value="/portexportExcel.do")
		public void portexportExcel(HttpServletRequest request, HttpServletResponse response){
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			
			reformOrderReportService.portDownload(para, request, response);
			
		}
}
