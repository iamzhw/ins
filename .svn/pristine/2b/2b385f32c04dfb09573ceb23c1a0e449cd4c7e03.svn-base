package com.cableInspection.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;

import com.cableInspection.service.ChartService;


@RequestMapping(value = "/Chart")
@SuppressWarnings("all")
@Controller
public class ChartController extends BaseAction{

	@Resource
	private ChartService chartService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cableinspection/chart/index",null);
	}
	
	@RequestMapping("queryData")
	@ResponseBody
	public Map CoverRate(HttpServletRequest request,
			HttpServletResponse response){
		return chartService.queryChartData(request);
	}
}
