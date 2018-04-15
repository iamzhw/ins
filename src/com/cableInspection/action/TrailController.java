package com.cableInspection.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cableInspection.service.CableService;
import com.cableInspection.service.TrailService;

@RequestMapping(value = "/Trail")
@SuppressWarnings("all")
@Controller
public class TrailController {
	@Resource
	private CableService cableService;

	@Resource
	private TrailService trailServcie;

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		return new ModelAndView("cableinspection/trail/trail_index", rs);
	}

	/**
	 * 获取员工
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/query_staff.do")
	public ModelAndView staffAdd(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		return new ModelAndView("cableinspection/trail/trail_staff", rs);
	}

	/**
	 * 查询员工轨迹
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/query_trail.do")
	public void queryTrail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean ifsuccess = true;
		Map map = new HashMap();
		try {
			List<Map> list = trailServcie.queryTrail(request);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			ifsuccess = false;
		}
		map.put("ifsuccess", ifsuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	/**
	 * 获取当日任务轨迹
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("query_task")
	public void queryTask(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jsonString = trailServcie.getTaskCables(request);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
	}
}
