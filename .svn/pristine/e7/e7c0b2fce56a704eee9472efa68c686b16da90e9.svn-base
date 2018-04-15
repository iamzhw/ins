package com.cableInspection.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableInspection.service.ArrivalService;
import com.cableInspection.service.TimeCountService;

@RequestMapping(value = "/TimeCount")
@SuppressWarnings("all")
@Controller
public class TimeCountController extends BaseAction{

	@Resource
	private TimeCountService timeCountService;
	
	@Resource
	private ArrivalService arrivalService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map map = arrivalService.index(request);
		return new ModelAndView("cableinspection/timecount/index", map);
	}
	
	@RequestMapping("query")
	public void query(HttpServletRequest request,HttpServletResponse response,UIPage pager) throws IOException{
		Map<String,Object> map = timeCountService.query(request, pager);
		write(response,map);
	}
	
	@RequestMapping("/export")
	public String export(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		List<Map> dataList = timeCountService.export(request);
		String[] cols = new String[] { "NAME", "SON_NAME", "DEPT_NAME", "STAFF_NAME",
				"TIMECOUNT", "DISTANCE"};
		String[] colsName = new String[] { "市", "区县", "网格", "巡线员",
				"时长", "巡线里程(单位/米)"};
		model.addAttribute("name", "巡线任务时长统计");
		model.addAttribute("cols", cols);
		model.addAttribute("colsName", colsName);
		model.addAttribute("dataList", dataList);
		return "dataListExcel";
	}
}
