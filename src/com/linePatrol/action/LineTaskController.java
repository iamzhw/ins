package com.linePatrol.action;

import java.io.IOException;
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
import util.page.UIPage;

import com.linePatrol.service.LineTaskService;
import com.system.service.GeneralService;

/**
 * 巡线任务控制层管理类
 * 
 * @author huliubing
 * @since 2015-03-5
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/lineTask")
public class LineTaskController extends BaseAction{
	
	@Resource
	private LineTaskService lineTaskService;
	
	@Resource
	private GeneralService generalService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		
		return new ModelAndView("/linePatrol/xunxianManage/task/task_index",map);
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,HttpServletResponse response,
			UIPage pager)throws IOException{
		Map<String,Object> map = lineTaskService.query(request, pager);//查询数据
		write(response,map);
	}
	
	@RequestMapping(value = "/toAdd.do")
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		map.put("AREA_ID", areaId);//区域ID
		List<Map<String,Object>> inspects = lineTaskService.getInpectStaff(areaId);
		map.put("inspects", inspects);
		return new ModelAndView("/linePatrol/xunxianManage/task/task_add",map);
	}
	
	@RequestMapping(value = "/addTask.do")
	@ResponseBody
	public Map addTask(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		
		Map map = new HashMap();
		Boolean status = true;
		try {
			lineTaskService.insert(request);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
	}
	
	
	@RequestMapping(value = "/getLineList.do")
	public ModelAndView getLineList(HttpServletRequest request,
			HttpServletResponse response){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//获取巡线段
		List<Map<String,Object>> lineList = lineTaskService.getLineList(request);
		map.put("lineList", lineList);
		
		return new ModelAndView("/linePatrol/xunxianManage/task/task_line",map);
	}
	
	@RequestMapping(value = "/toUpdate.do")
	public ModelAndView toUpdate(HttpServletRequest request,
			HttpServletResponse response){
		Map<String,Object> map = lineTaskService.getTask(request);
		
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		map.put("AREA_ID", areaId);//区域ID
		List<Map<String,Object>> inspects = lineTaskService.getInpectStaff(areaId);
		map.put("inspects", inspects);
		
		return new ModelAndView("/linePatrol/xunxianManage/task/task_update",map);
	}
	
	
	@RequestMapping(value = "/updateTask.do")
	@ResponseBody
	public Map updateTask(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		
		Map map = new HashMap();
		Boolean status = true;
		try {
			lineTaskService.update(request);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
	}
	
	@RequestMapping(value = "/stopTask.do")
	@ResponseBody
	public Map stopTask(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		
		Map map = new HashMap();
		Boolean status = true;
		try {
			lineTaskService.stop(request);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 * 任务详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/detail.do")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//获取巡线段
		List<Map<String,Object>> detailList = lineTaskService.getTaskDetail(request);
		map.put("detailList", detailList);
		
		return new ModelAndView("/linePatrol/xunxianManage/task/task_detail",map);
	}
	
	/**
	 * 外力点详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/outSiteDetail.do")
	public ModelAndView outSiteDetail(HttpServletRequest request,
			HttpServletResponse response){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//获取巡线段
		List<Map<String,Object>> outSiteList = lineTaskService.getTaskOutSiteDetail(request);
		map.put("outSiteList", outSiteList);
		
		return new ModelAndView("/linePatrol/xunxianManage/task/task_outsite_detail",map);
	}
}
