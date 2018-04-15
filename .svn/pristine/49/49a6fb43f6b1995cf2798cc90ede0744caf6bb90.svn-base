package com.roomInspection.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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

import com.roomInspection.service.CheckItemService;
import com.roomInspection.service.JobService;
import com.util.MapUtil;
import com.util.StringUtil;

/**
 * @ClassName: JobController
 * @Description: 巡检检查项管理
 * @author huliubing
 * @date: 2014-08-15
 *
 */
@SuppressWarnings("all")
@RequestMapping(value = "/checkItem")
@Controller
public class CheckItemController extends BaseAction{

	@Resource
	private CheckItemService checkItemService;
	
	@Resource
	private JobService jobService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		//信息初始化
		init(map,request);
		
		ModelAndView mav = new ModelAndView("/roominspection/checkitem/checkitem_index", map);
		return mav;
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = checkItemService.queryCheckItem(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/toAdd.do")
	public ModelAndView toAdd(HttpServletRequest request,HttpServletResponse response)
	{
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		//信息初始化
		init(map,request);
		
		map.put("currentDate", StringUtil.getCurrDate());
		map.put("troubleTypes", "");
		
		ModelAndView mav = new ModelAndView("/roominspection/checkitem/checkitem_add", map);
		return mav;
	}
	
	@RequestMapping(value = "/add.do")
	@ResponseBody
	public Map add(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		
		try {
			
			checkItemService.insertCheckItem(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		
		map.put("status", status);
		return map;
	}
	
	@RequestMapping(value = "/toUpdate.do")
	public ModelAndView toUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		//信息初始化
		init(map,request);
		
		//查询单个计划信息
		Map item = checkItemService.queryCheckItemById(request);
		map.put("item", item);
		
		//设置修改时间
		map.put("currentDate", StringUtil.getCurrDate());
		
		return new ModelAndView("roominspection/checkitem/checkitem_update", map);
	}
	
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		
		try {
			
			checkItemService.updateCheckItem(request);
			
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		
		map.put("status", status);
		return map;
	}
	
	
	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		
		try {
			
			checkItemService.deleteCheckItem(request);
			
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
	
	@RequestMapping(value = "/addTroubleType.do")
	public ModelAndView addTroubleType(HttpServletRequest request)
	{
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		ModelAndView mav = new ModelAndView("/roominspection/checkitem/trouble_type_add", map);
		return mav;
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
		
		//初始化机房类型信息
		List<Map> roomTypeList = jobService.getRoomTypes();
		map.put("roomTypes", roomTypeList);
	}
}
