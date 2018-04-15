package com.cableCheck.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableCheck.service.CheckQualityReportService;
import com.cableCheck.service.DoneTaskService;
import com.linePatrol.util.MapUtil;


/** 
 * @author lixy
 * @version 创建时间：2016年9月12日 下午4:05:05 
 * 类说明 :检查质量分析报告
 */
@RequestMapping(value = "/CheckQualityReport")
@SuppressWarnings("all")
@Controller
public class CheckQualityReportController extends BaseAction{

	Logger logger = Logger.getLogger(CheckQualityReportController.class);
	
	@Autowired
	private CheckQualityReportService checkQualityReportService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", checkQualityReportService.selArea());
		return new ModelAndView("cablecheck/CheckQualityReport", map);
	}
	
	@RequestMapping(value="/checkQualityReport.do")
	public void checkQualityReport(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = checkQualityReportService.query(request, pager);
		write(response, map);
	}
	@RequestMapping(value="/checkQualityReportByCity.do")
	public void checkQualityReportByCity(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = checkQualityReportService.queryByCity(request, pager);
		write(response, map);
	}
	
	
	@RequestMapping(value="/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
		//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		checkQualityReportService.checkQualityReportDownload(request, response);
	}
	
	@RequestMapping(value="/exportExcelByCity.do")
	public void exportExcelByCity(HttpServletRequest request, HttpServletResponse response){
		//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		checkQualityReportService.checkQualityReportDownloadByCity(request, response);
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
				if("0".equals(type)||"1".equals(type)||"2".equals(type)||"3".equals(type)||"4".equals(type)){
					    return new ModelAndView("cablecheck/QueryEqpNum",map);
				    }else{
				        return new ModelAndView("cablecheck/QueryEqp",map);
				}
	}
	

	 
	 
	 @RequestMapping(value = "/equipmentQuery.do")
		public void equipmentQuery(HttpServletRequest request, HttpServletResponse response,
				UIPage pager) throws IOException {
		 
			Map<String, Object> map =  checkQualityReportService.equipmentQuery(request, pager);
			write(response, map);
		}
	 
	 
		@RequestMapping(value="/equipmentexportExcel.do")
		public void equipmentexportExcel(HttpServletRequest request, HttpServletResponse response){
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			
			checkQualityReportService.equipmentDownload(para, request, response);
			
		}
		
		@RequestMapping(value = "/equipmentNumQuery.do")
		public void equipmentNumQuery(HttpServletRequest request, HttpServletResponse response,
				UIPage pager) throws IOException {
		 
			Map<String, Object> map =  checkQualityReportService.equipmentNumQuery(request, pager);
			write(response, map);
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
                    
					return new ModelAndView("cablecheck/QueryPort",map);
			
		}
	 @RequestMapping(value="/queryDtsj.do")
		public ModelAndView queryDtsj(HttpServletRequest request, HttpServletResponse response){ 
				 String type = request.getParameter("type");
				 String WLJB_ID = request.getParameter("WLJB_ID");
				 String static_month = request.getParameter("static_month");
					String areaId = request.getParameter("AREAID");
					String completeTime = request.getParameter("COMPLETE_TIME");
					String startTime = request.getParameter("START_TIME");
					String parent_area_id = request.getParameter("parent_area_id");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("areaId", areaId);
					map.put("type", type);
					map.put("WLJB_ID", WLJB_ID);
					map.put("START_TIME", startTime);
					map.put("static_month", static_month);
					map.put("COMPLETE_TIME", completeTime);
					map.put("parent_area_id", parent_area_id);
                 
					return new ModelAndView("cablecheck/QueryDtsj",map);
			
		}
	
	 
	 @RequestMapping(value = "/portQuery.do")
		public void portQuery(HttpServletRequest request, HttpServletResponse response,
				UIPage pager) throws IOException {
		 
			Map<String, Object> map =  checkQualityReportService.portQuery(request, pager);
			write(response, map);
		}
	 
		@RequestMapping(value="/portexportExcel.do")
		public void portexportExcel(HttpServletRequest request, HttpServletResponse response){
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			
			checkQualityReportService.portDownload(para, request, response);
			
		}
		@RequestMapping(value="/dtsjexportExcel.do")
		public void dtsjexportExcel(HttpServletRequest request, HttpServletResponse response){
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			
			checkQualityReportService.dtsjDownload(para, request, response);
			
		}
		
		
		/**
		 * 跳转查看详情页面
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "/intoShowEquip.do")
		public ModelAndView intoShowEquip(HttpServletRequest request, HttpServletResponse response){
			String taskId = request.getParameter("TASK_ID");
			
			Map<String, Object> result = new HashMap<String, Object>();
	
				 result = checkQualityReportService.getMyTaskEqpPhotoForZq(request);
				 return new ModelAndView("cablecheck/Second-taskDetail",result);

		}
	
}
