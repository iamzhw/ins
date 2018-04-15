package com.cableCheck.action;

import icom.util.BaseServletTool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

import com.axxreport.util.ExcelUtil;
import com.cableCheck.service.CheckRecordService;
import com.cableCheck.service.DoneTaskService;

@RequestMapping(value = "/CheckRecord")
@SuppressWarnings("all")
@Controller
public class CheckRecordController extends BaseAction{

	Logger logger = Logger.getLogger(CheckRecordController.class);
	
	@Resource
	private CheckRecordService checkRecordService;
	
	/**
	 * 端子所在设备记录页面  ports.jsp
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("cablecheck/portsRecord/ports", null);
	}
	
	/**
	 * 查询签到记录列表
	 */
	@RequestMapping(value="/queryCheckRecord.do")
	public void queryDoneTask(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = checkRecordService.queryCheckRecord(request, pager);
		write(response, map);
	}
	/**
	 * 同一端子不同记录页面
	 */
	@RequestMapping(value = "/portsRecord.do")
	public ModelAndView portsRecord(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String whwg = request.getParameter("whwg");
		String static_month = request.getParameter("static_month");
		String dz_start_time = request.getParameter("dz_start_time");
		String dz_end_time = request.getParameter("dz_end_time");
		String check_start_time = request.getParameter("check_start_time");
		String check_end_time = request.getParameter("check_end_time");
		String POTR_ID = request.getParameter("POTR_ID");
		
		map.put("area", area);
		map.put("son_area", son_area);
		map.put("whwg", whwg);
		map.put("static_month", static_month);
		map.put("dz_start_time", dz_start_time);
		map.put("dz_end_time", dz_end_time);
		map.put("check_start_time", check_start_time);
		map.put("check_end_time", check_end_time);
		map.put("PORT_ID", POTR_ID);
		return new ModelAndView("cablecheck/portsRecord/samePort", map);
	}
	
	/**
	 * 同一端子记录列表
	 */
	@RequestMapping(value = "/querySamePortRecord.do")
	public void querySamePortRecord(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = checkRecordService.querySamePortRecord(request, pager);
		write(response, map);
	}
	
	/**
	 * 导出端子所在设备记录列表
	 */
	@RequestMapping(value="/exportPortsRecord.do")
	public String exportPortsRecord(HttpServletRequest request,ModelMap mm) throws IOException{
		
		List<Map<String, Object>>  recordList = checkRecordService.exportPortsRecord(request);
		
		StringBuffer colsStr = new StringBuffer();
		colsStr.append("AREA,SON_AREA,EQP_ID,EQP_NO,EQP_NAME,PORT_ID,PORT_NO,GLBH,BDSJ");

		String[] cols = colsStr.toString().split(",");
		
		StringBuffer colsNameStr = new StringBuffer();
		colsNameStr.append("地区,区域,设备ID,设备编码,设备名称,端子ID,端子编码,光路编号,变动时间");

		String[] colsName = colsNameStr.toString().split(",");
		
		mm.addAttribute("name", "不一致端子记录");
		mm.addAttribute("cols", cols);
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", recordList);	
		return "dataListExcel";
	}
}
