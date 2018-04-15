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
import com.cableInspection.service.CableService;
import com.cableInspection.service.PersonalWorkService;

@RequestMapping(value = "/PersonalWork")
@SuppressWarnings("all")
@Controller
public class PersonalWorkController extends BaseAction{
	@Resource
	private CableService cableService;
	
	@Resource
	private PersonalWorkService personalWorkService;
	
	@Resource
	private ArrivalService arrivalService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map map = arrivalService.index(request);
		return new ModelAndView("cableinspection/timecount/personalwork", map);
	}
	
	@RequestMapping("query")
	public void query(HttpServletRequest request,HttpServletResponse response,UIPage pager) throws IOException{
		Map<String,Object> map = personalWorkService.query(request, pager);
		write(response,map);
	}
	
	@RequestMapping("captureInfo")
	public ModelAndView captureInfo(HttpServletRequest request,HttpServletResponse response){
		Map map = arrivalService.index(request);
		String areaName = cableService.getAreaName(request);
		map.put("AREA_NAME", areaName);
		return new ModelAndView("cableinspection/timecount/captureInfo", map);
	}
	
	@RequestMapping("queryAreaPoints")
	public void queryAreaPoints(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jsonString = personalWorkService.queryAreaPoints(request);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
	}
}
