package com.cableInspection.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;

import com.cableInspection.service.StaffLocationService;

@RequestMapping(value = "/StaffLocation")
@SuppressWarnings("all")
@Controller
public class StaffLocationController extends BaseAction {
	
	@Resource
	private StaffLocationService staffLocationService;
	
	@RequestMapping("index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map rs =  new HashMap();
		String areaName = staffLocationService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		return new ModelAndView("cableinspection/StaffLocation/index", rs);
	}
	
	@RequestMapping("query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map rs = staffLocationService.query(request);
		JSONObject json = new JSONObject();
		String jsonString=json.fromObject(rs).toString();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
	}
}
