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

import com.cableInspection.service.TroubleReportService;
import com.cableInspection.serviceimpl.TroubleReportServiceImpl;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/Trouble")
@SuppressWarnings("all")
@Controller
public class TroubleReportController extends BaseAction{
	@Resource
	private TroubleReportService troubleReportServer;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cableinspection/troubleReport/troubleReport_index", null);
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = troubleReportServer.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/export.do")
	public String export(HttpServletRequest request, ModelMap mm) {
		List<Map> dataList = troubleReportServer.query(request);
		String[] cols = new String[] { "地区", "巡线人员总数", "上报数量",
				"已完成", "已派单", "未派单", "未上报隐患点人数（要求每个巡线人员每周最少上传一个隐患点）"};
		mm.addAttribute("name", "全省隐患上报情况统计表");
		mm.addAttribute("cols", cols);
		mm.addAttribute("dataList", dataList);
		return "troubleReportExcel";
	}

}
