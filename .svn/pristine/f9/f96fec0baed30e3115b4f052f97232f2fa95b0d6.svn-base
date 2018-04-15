package com.cableCheck.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.service.DispatchRelationService;

import net.sf.json.JSONObject;
import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/dispatchRelation")
@SuppressWarnings("all")
@Controller
public class DispatchRelationController extends BaseAction{
	Logger logger = Logger.getLogger(CheckRecordController.class);

	@Resource
	private DispatchRelationService dispatchRelationService; 

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rs = dispatchRelationService.getAreaList();
		return new ModelAndView("cablecheck/dispatchRelation/relation_index", rs);
	}

	// 获取员工
	@RequestMapping(value = "/check_staff.do")
	public ModelAndView check_staff(HttpServletRequest request,
			HttpServletResponse response,String AREA_ID) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		List<Map<String,Object>> sonAreaList = dispatchRelationService.getSonAreaListByAreaId((String) request.getParameter("AREA_ID"));
		rs.put("sonAreaList", sonAreaList);
		rs.put("AREA_ID", (String) request.getParameter("AREA_ID"));
		rs.put("SON_AREA_ID", (String) request.getParameter("SON_AREA_ID"));
		return new ModelAndView("cablecheck/dispatchRelation/check_staff", rs);
	}

	@RequestMapping(value = "/query_staff.do")
	public void queryStaff(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = dispatchRelationService.queryStaff(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/addDispatchRelation.do")
	public ModelAndView addDispatchRelation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = dispatchRelationService.getAreaList();
		return new ModelAndView("cablecheck/dispatchRelation/dispatchRelationAdd", rs);
	}

	@RequestMapping(value = "/getGridListByAreaId.do")
	@ResponseBody
	public Map getGridListByAreaId(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List<Map<String, String>> gridList = 
				dispatchRelationService.getGridListByAreaId((String) request.getParameter("AREA_ID"));
		map.put("gridList", gridList);
		return map;
	}

	/**
	 * 保存关键点计划
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/saveDispatchRelation.do")
	@ResponseBody
	public void saveDispatchRelation(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = dispatchRelationService.saveDispatchRelation(request);
		write(response, map);
	}
	
	@RequestMapping(value = "/deleteRelation.do")
	public void deleteRelation(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		Boolean status = true;
		try {
			dispatchRelationService.deleteRelation(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	/**
	 * 查询派单关系列表
	 */
	@RequestMapping(value="/queryDispatchRelation.do")
	public void queryDispatchRelation(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		Map<String, Object> map = dispatchRelationService.queryDispatchRelation(request, pager);
		write(response, map);
	}

}
