package com.cableCheck.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.service.CCStatictisService;
import com.linePatrol.util.MapUtil;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/cableCheckStatictis")
@SuppressWarnings("all")
@Controller
public class CableCheckStatictis  extends BaseAction{
	@Resource
	private CCStatictisService ccStatictisService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/cableCheckStatictis/cableCheckStatictis", null);
	}
	
	@RequestMapping(value = "/statictis.do")
	public void listAll(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = ccStatictisService.statictis(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value="/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			UIPage pager){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		ccStatictisService.exportExcelDownload(para, request, response,pager);
	}
	
	
	@RequestMapping(value = "/orderIndex.do")
	public ModelAndView orderIndex(HttpServletRequest request,
			HttpServletResponse response){
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String area = request.getParameter("area");
		String type = request.getParameter("type");
		String order_type = request.getParameter("order_type");
		String teamId = request.getParameter("team_id");
		
		String check_start_time = request.getParameter("check_start_time");
		String check_end_time = request.getParameter("check_end_time");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		map.put("area", area);
		map.put("type", type);
		map.put("order_type", order_type);
		map.put("teamId",teamId);
		
		map.put("check_start_time", check_start_time);
		map.put("check_end_time", check_end_time);
		
		return new ModelAndView("cablecheck/cableCheckStatictis/cableStatictisOrder", map);
	}
	
	@RequestMapping(value="/showStatictisOrder.do")
	public void showStatictisOrder(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = ccStatictisService.showStatictisOrder(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value="/exportExcelByDetail.do")
	public void exportExcelByDetail(HttpServletRequest request, HttpServletResponse response,
			UIPage pager){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		ccStatictisService.exportExcelDetail(para, request, response,pager);
	}
	
	
	
	
	/*****************************START***即时检查纠错数据分析*****************************/
	@RequestMapping(value = "/orderChangeIndex.do")
	public ModelAndView orderChangeIndex(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/cableCheckStatictis/orderChange", null);
	}
	
	@RequestMapping(value = "/orderChange.do")
	public void orderChangeAll(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = ccStatictisService.orderChangeAll(request, pager);
		write(response, map);
	}
	/*****************************END***即时检查纠错数据分析*****************************/
	
	
	
	
	/*****************************START***数据错误责任判定*****************************/
	@RequestMapping(value = "/checkErrorIndex.do")
	public ModelAndView checkErrorIndex(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/cableCheckStatictis/checkError", null);
	}
	
	@RequestMapping(value = "/checkError.do")
	public void checkErrorAll(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = ccStatictisService.checkErrorAll(request, pager);
		write(response, map);
	}
	
	
	@RequestMapping(value = "/checkErrorOrderIndex.do")
	public ModelAndView checkErrorOrderIndex(HttpServletRequest request,
			HttpServletResponse response){
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String area = request.getParameter("area");
		String type = request.getParameter("type");
		String staffId = request.getParameter("staffId");
		String rowType = request.getParameter("rowType");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		map.put("staffId", staffId);
		map.put("rowType", rowType);
		return new ModelAndView("cablecheck/cableCheckStatictis/checkErrorOrder", map);
	}
	
	@RequestMapping(value="/showCheckErrorOrder.do")
	public void showCheckErrorOrder(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = ccStatictisService.showCheckErrorOrder(request, pager);
		write(response, map);
	}
	
	/*****************************END***数据错误责任判定*****************************/
	
	
	/*****************************START***网格班组归属工单查询*****************************/
	@RequestMapping(value = "/teamOrderIndex.do")
	public ModelAndView teamOrderIndex(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/cableCheckStatictis/teamOrder", null);
	}
	
	@RequestMapping(value = "/teamOrder.do")
	public void teamOrder(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = ccStatictisService.teamOrder(request, pager);
		write(response, map);
	}
	
	
	@RequestMapping(value = "/gridOrder.do")
	public void gridOrder(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = ccStatictisService.gridOrder(request, pager);
		write(response, map);
	}
	
	/*****************************END***网格班组归属工单查询*****************************/
	
	
}
