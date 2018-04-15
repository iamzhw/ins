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
import org.springframework.web.servlet.ModelAndView;

import util.page.UIPage;
import util.page.BaseAction;

import com.linePatrol.service.StepPartTaskService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.linePatrol.util.StringUtil;
import com.system.service.AreaService;
import com.system.service.ParamService;


/**
 * 步巡任务控制层管理类
 * 
 * @author sunmin
 * @since 2016-05-30
 * 
 */

@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/StepPartTask")
public class StepPartTaskController extends BaseAction{
	
	@Resource
    private AreaService areaService;
	
	@Resource
	private ParamService paramService;
	
	@Resource
	private StepPartTaskService stepparttaskservice;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) {
		// 角色
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		Map<String, Object> res = paramService.query(request);
		String isAdmin = res.get("isAdmin").toString();// 0-省 1地市
		if ("0".equals(isAdmin)) {
		    List<Map<String, Object>> areaList = areaService.getAllArea();
		    map.put("areaList", areaList);
		}
		map.put("isAdmin", isAdmin);
		return new ModelAndView("/linePatrol/xunxianManage/stepPartTask/stepPartTask_index", map);
	}
	
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException {
		
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String area_id = request.getParameter("area_id");
		if (StringUtil.isNullOrEmpty(area_id)) {
		    area_id = StaffUtil.getStaffAreaId(request);
		}
		para.put("area_id", area_id);
		Map<String, Object> map=stepparttaskservice.query(para,pager);
		write(response, map);
	}
	
	/**
	 * 步巡段详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selStepPartByTaskID.do")
	public ModelAndView detail(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		String task_id = request.getParameter("task_id");
		param.put("task_id", task_id);
		List<Map<String,Object>> detailList = stepparttaskservice.selPartNameByTaskID(task_id);
		Map<String,Object> map = new HashMap<String,Object>();
//		//获取步巡段
		map.put("detailList", detailList);
		return new ModelAndView("/linePatrol/xunxianManage/stepPartTask/task_detail",map);
	}
	
}
