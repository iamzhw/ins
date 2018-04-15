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

import com.linePatrol.service.LineJobService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.service.GeneralService;

/**
 * 巡线计划控制层管理类
 * 
 * @author huliubing
 * @since 2015-02-28
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/lineJob")
public class LineJobController extends BaseAction{
	
	@Resource
	private LineJobService lineJobService;
	
	@Resource
	private GeneralService generalService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		
		return new ModelAndView("/linePatrol/xunxianManage/job/job_index",map);
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,HttpServletResponse response,
			UIPage pager)throws IOException{
		Map<String,Object> map = lineJobService.query(request, pager);//查询数据
		write(response,map);
	}
	
	@RequestMapping(value = "/toAdd.do")
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		
		//获取当前登陆名
		String staffNo = String.valueOf(request.getSession().getAttribute("staffNo"));
		map.put("staffNo", staffNo);
		
		return new ModelAndView("/linePatrol/xunxianManage/job/job_add",map);
	}
	
	
	@RequestMapping(value = "/add.do")
	@ResponseBody
	public Map add(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		
		Map map = new HashMap();
		Boolean status = true;
		try {
		    lineJobService.insert(request);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
	}
	
	
	@RequestMapping(value = "/toUpdate.do")
	public ModelAndView toUpdate(HttpServletRequest request,
			HttpServletResponse response){
		
		Map<String,Object> map = lineJobService.queryJob(request);//查询单个计划
		
		//获取当前登陆名
		String staffNo = String.valueOf(request.getSession().getAttribute("staffNo"));
		map.put("staffNo", staffNo);
		
		return new ModelAndView("/linePatrol/xunxianManage/job/job_update",map);
	}
	
	
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		
		Map map = new HashMap();
		Boolean status = true;
		try {
		    lineJobService.update(request);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
	}
	
	@RequestMapping(value = "/stop.do")
	@ResponseBody
	public Map stop(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		
		Map map = new HashMap();
		Boolean status = true;
		try {
		    lineJobService.stop(request);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
	}
	

}
