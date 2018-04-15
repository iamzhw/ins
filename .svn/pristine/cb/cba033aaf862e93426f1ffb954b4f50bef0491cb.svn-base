package com.cableInspection.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cableInspection.service.RecordService;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/Record")
@SuppressWarnings("all")
@Controller
public class RecordController extends BaseAction{
	@Resource
	private RecordService recordServer;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cableinspection/record/record_index", null);
	}
	
	@RequestMapping(value = "/main.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = recordServer.getAreaList();
		return new ModelAndView("cableinspection/record/record_main", rs);
	}
	
	@RequestMapping(value = "/getListByPage.do")
	public void getListByPage(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> rs = recordServer.getListByPage(request, pager);
		write(response, rs);
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = recordServer.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/queryPhoto.do")
	public ModelAndView show(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		List<Map> map = recordServer.queryPhoto(request);
		rs.put("photoList", map);
		return new ModelAndView("cableinspection/record/record_show", rs);
	}
	
	
	/**
	 * 进入人员列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectRecordStaff.do")
	public ModelAndView selectRecordStaff(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("AREA_ID", request.getParameter("AREA_ID"));
		map.put("RECORD_DAY", request.getParameter("RECORD_DAY"));
		map.put("RECORD_MONTH", request.getParameter("RECORD_MONTH"));
		map.put("RECORD_YEAR", request.getParameter("RECORD_YEAR"));
		map.put("SEARCH_AREA_ID", request.getParameter("SEARCH_AREA_ID"));
		return new ModelAndView("cableinspection/record/record_staff", map);
	}
	
	@RequestMapping(value = "/queryRecordStaff.do")
	public void queryRecordStaff(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = recordServer.queryRecordStaff(request, pager);
		write(response, map);
	}
	
	@RequestMapping("/exportRecord.do")
	public String exportRecord(HttpServletRequest request, HttpServletResponse response,
			ModelMap map){
		List<Map> dataList=recordServer.getRecordForExport(request);
		
		String[] cols = new String[] { "AREA_NAME","RECORD_1", "RECORD_2","RECORD_3","RECORD_4","RECORD_5", "RECORD_6", "RECORD_7","RECORD_8","RECORD_9",
				"RECORD_10","RECORD_11", "RECORD_12", "RECORD_13", "RECORD_14","RECORD_15", "RECORD_16", "RECORD_17", "RECORD_18", "RECORD_19","RECORD_20", 
				"RECORD_21", "RECORD_22", "RECORD_23", "RECORD_24","RECORD_25", "RECORD_26", "RECORD_27", "RECORD_28", "RECORD_29","RECORD_30",
				"RECORD_31"};

		String[] colsName = new String[] { "地市", 
				"1日", "2日","3日", "4日", "5日", "6日", "7日", "8日","9日", "10日",
				"11日", "12日","13日", "14日", "15日", "16日", "17日", "18日","19日", "20日",
				"21日", "22日","23日", "24日", "25日", "26日", "27日", "28日","29日", "30日",
				"31日"};
		
		map.addAttribute("name", "关键点签到分布表");
		map.addAttribute("cols", cols);
		map.addAttribute("colsName", colsName);
		map.addAttribute("dataList", dataList);
		return "dataListExcel";
	}
	

	@RequestMapping("/exportRecordStaff.do")
	public String exportRecordStaff(HttpServletRequest request, HttpServletResponse response,
			ModelMap map){
		List<Map> dataList=recordServer.getRecordStaffForExport(request);
		
		String[] cols = new String[] { "RECORD_TIME","DESCRP","POINT_NAME","TASK_NAME", "AREA_NAME","STAFF_NO","STAFF_NAME"};

		String[] colsName = new String[] { "签到时间","签到描述", "关键点名称","任务名称","区域","工号", "姓名"};
		
		map.addAttribute("name", "关键点部分人员签到表");
		map.addAttribute("cols", cols);
		map.addAttribute("colsName", colsName);
		map.addAttribute("dataList", dataList);
		return "dataListExcel";
	}
}
