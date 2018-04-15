package com.roomInspection.action;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.roomInspection.service.JobService;
import com.system.service.GeneralService;
import com.util.MapUtil;
import com.util.StringUtil;

/**
 * @ClassName: JobController
 * @Description: 巡检任务计划管理
 * @author huliubing
 * @date: 2014-07-19
 *
 */
@SuppressWarnings("all")
@RequestMapping(value = "/JfxjJob")
@Controller
public class JobController extends BaseAction{

	@Resource
	private JobService jobService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		//信息初始化
		init(map,request);
		
		ModelAndView mav = new ModelAndView("/roominspection/job/job_index", map);
		return mav;
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = jobService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response)
	{
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		//信息初始化
		init(map,request);
		
		map.put("currentDate", StringUtil.getCurrDate());
		
		ModelAndView mav = new ModelAndView("/roominspection/job/job_add", map);
		return mav;
	}
	
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Map save(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			jobService.insert(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	@RequestMapping(value = "/edit.do")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		//信息初始化
		init(map,request);
		
		//查询单个计划信息
		Map job = jobService.edit(request);
		map.put("job", job);
		
		return new ModelAndView("roominspection/job/job_update", map);
	}
	
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			jobService.update(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	@RequestMapping(value = "/stop.do")
	public void stop(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			jobService.stop(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			jobService.delete(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 初始化信息
	 * @param map 初始化信息保存map对象
	 * @param request 页面请求
	 */
	private void init(Map<String,Object> map,HttpServletRequest request)
	{
		//获取当前登陆名
		String staffNo = String.valueOf(request.getSession().getAttribute("staffNo"));
		map.put("staffNo", staffNo);
		
		// 根据登陆账号的区域ID获取所有子区域
		int areaId = Integer.parseInt(String.valueOf(request.getSession().getAttribute("areaId")));
		List<Map> areaList = jobService.getAreaList(areaId);
		map.put("areaList", areaList);
		
		//初始化周期信息
		List<Map> circleList = jobService.getCircles();
		map.put("circles", circleList);
		
		//初始化机房类型信息
		List<Map> roomTypeList = jobService.getRoomTypes();
		map.put("roomTypes", roomTypeList);
	}
}
