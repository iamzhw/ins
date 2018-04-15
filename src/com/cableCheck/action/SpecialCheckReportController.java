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
import com.cableCheck.service.SpecialCheckReportService;
import com.linePatrol.util.MapUtil;

/** 
 * @author lixy
 * @version 创建时间：2017年7月14日 上午9:05:05 
 * 类说明 :专项检查分析报告
 */
@RequestMapping(value = "/SpecialCheckReport")
@SuppressWarnings("all")
@Controller
public class SpecialCheckReportController extends BaseAction{

	Logger logger = Logger.getLogger(SpecialCheckReportController.class);
	
	@Autowired
	private SpecialCheckReportService specialCheckReportService;
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", specialCheckReportService.selArea());
		return new ModelAndView("cablecheck/specialCheckReport/SpecialCheckReoprt", map);
	}
	
	@RequestMapping(value="/specialCheckReport.do")
	public void specialCheckReport(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = specialCheckReportService.query(request, pager);
		write(response, map);
	}
	@RequestMapping(value="/specialCheckReportByCity.do")
	public void specialCheckReportByCity(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = specialCheckReportService.queryByCity(request, pager);
		write(response, map);
	}
	
	
	@RequestMapping(value="/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
		//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		specialCheckReportService.specialCheckReportDownload(request, response);
	}
	
	@RequestMapping(value="/exportExcelByCity.do")
	public void exportExcelByCity(HttpServletRequest request, HttpServletResponse response){
		//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		specialCheckReportService.specialCheckReportDownloadByCity(request, response);
	}
	
	
	@RequestMapping(value="/queryEqp.do")
	public ModelAndView queryEqp(HttpServletRequest request, HttpServletResponse response){ 
			 String type = request.getParameter("type");
				String areaId = request.getParameter("AREAID");
				String parent_area_id = request.getParameter("parent_area_id");
				String static_month = request.getParameter("static_month");
				String pcomplete_time = request.getParameter("PCOMPLETE_TIME");
				String pstart_time = request.getParameter("PSTART_TIME");
				String completeTime = request.getParameter("COMPLETE_TIME");
				String startTime = request.getParameter("START_TIME");
				String WLJB_ID = request.getParameter("WLJB_ID");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("areaId", areaId);
				map.put("WLJB_ID", WLJB_ID);
				map.put("parent_area_id", parent_area_id);
				map.put("type", type);
				map.put("PCOMPLETE_TIME", pcomplete_time);
				map.put("PSTART_TIME", pstart_time);
				map.put("static_month", static_month);
				map.put("START_TIME", startTime);
				map.put("COMPLETE_TIME", completeTime);
				return new ModelAndView("cablecheck/specialCheckReport/QueryTask",map);
				
	}
	

	 
	 
	 @RequestMapping(value = "/equipmentQuery.do")
		public void equipmentQuery(HttpServletRequest request, HttpServletResponse response,
				UIPage pager) throws IOException {
		 
			Map<String, Object> map =  specialCheckReportService.equipmentQuery(request, pager);
			write(response, map);
		}
	 
		@RequestMapping(value="/equipmentexportExcel.do")
		public void equipmentexportExcel(HttpServletRequest request, HttpServletResponse response){
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			
			specialCheckReportService.equipmentDownload(para, request, response);
			
		}
		 @RequestMapping(value="/queryPort.do")
			public ModelAndView queryPort(HttpServletRequest request, HttpServletResponse response){ 
					 String type = request.getParameter("type");
					 String static_month = request.getParameter("static_month");
					 String WLJB_ID = request.getParameter("WLJB_ID");
						String areaId = request.getParameter("AREAID");
						String completeTime = request.getParameter("COMPLETE_TIME");
						String startTime = request.getParameter("START_TIME");
						String parent_area_id = request.getParameter("parent_area_id");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("areaId", areaId);
						map.put("WLJB_ID", WLJB_ID);
						map.put("type", type);
						map.put("START_TIME", startTime);
						map.put("static_month", static_month);
						map.put("COMPLETE_TIME", completeTime);
						map.put("parent_area_id", parent_area_id);
	                    
						return new ModelAndView("cablecheck/specialCheckReport/QueryPorts",map);
				
			}
		
		
		 @RequestMapping(value = "/portQuery.do")
			public void portQuery(HttpServletRequest request, HttpServletResponse response,
					UIPage pager) throws IOException {
			 
				Map<String, Object> map =  specialCheckReportService.portQuery(request, pager);
				write(response, map);
			}
		 
			@RequestMapping(value="/portexportExcel.do")
			public void portexportExcel(HttpServletRequest request, HttpServletResponse response){
				Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
				
				specialCheckReportService.portDownload(para, request, response);
				
			}
	
	
	
}
