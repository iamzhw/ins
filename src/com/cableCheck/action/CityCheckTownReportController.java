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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableCheck.service.CheckQualityReportService;
import com.cableCheck.service.CityCheckTownReportService;
import com.cableCheck.service.WrongPortReportService;
import com.linePatrol.util.MapUtil;



	/** 
	 * @author lixy
	 * @version 创建时间：2017年4月5日 下午14:05:05 
	 * 类说明 :市对县报告
	 */
	@RequestMapping(value = "/CityCheckTownReport")
	@SuppressWarnings("all")
	@Controller
	public class CityCheckTownReportController extends BaseAction{

		Logger logger = Logger.getLogger(CityCheckTownReportController.class);
		
		@Autowired
		private CityCheckTownReportService cityCheckTownReportService;
		@Autowired
		private WrongPortReportService wrongPortReportService;
		
		@RequestMapping(value = "/index.do")
		public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("area", wrongPortReportService.selArea());
			return new ModelAndView("cablecheck/CityCheckTownReport/CityCheckTownReport", map);
		}
		
		@RequestMapping(value="/cityCheckTownReport.do")
		public void checkQualityReport(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
			
			Map<String, Object> map = cityCheckTownReportService.query(request, pager);
			write(response, map);
		}
		
		
		@RequestMapping(value="/exportExcel.do")
		public void exportExcel(HttpServletRequest request, HttpServletResponse response){
			//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			cityCheckTownReportService.cityCheckTownReportDownload(request, response);
		}
		
		@RequestMapping(value = "/personalQualityIndex.do")
		public ModelAndView personalqualityindex(HttpServletRequest request, HttpServletResponse response) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("area", wrongPortReportService.selArea());
			return new ModelAndView("cablecheck/PersonalQuality-index", map);
		}
		
		@RequestMapping(value="/personalQuality.do")
		public void personalQuality(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
			
			Map<String, Object> map = wrongPortReportService.queryPersonalQuality(request, pager);
			write(response, map);
		}
		
		
			
			@RequestMapping(value="/exportExcelPersonalCheck.do")
			public void exportExcelPersonalCheck(HttpServletRequest request, HttpServletResponse response){
				//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
				wrongPortReportService.exportExcelPersonalCheckDownload(request, response);
			}
        

			
			 @RequestMapping(value = "/getSonAreaList.do")
			    @ResponseBody
			    public Map getSonAreaList(HttpServletRequest request, HttpServletResponse response) {
					Map map = new HashMap();
					List<Map<String, String>> sonAreaList = cityCheckTownReportService.getSonAreaList((String) request
						.getParameter("areaId"));
					map.put("sonAreaList", sonAreaList);
					return map;
			    }
}
