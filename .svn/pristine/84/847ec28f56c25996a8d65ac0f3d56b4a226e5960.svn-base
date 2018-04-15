package com.inspecthelper.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.inspecthelper.service.EquipService;
import com.inspecthelper.service.InsTaskService;
import com.system.service.GeneralService;
import com.util.MapUtil;

/**
 * 日常巡检任务控制层类
 *
 * @author lbhu
 * @since 2014-9-9
 *
 */
@SuppressWarnings("all")
@RequestMapping(value = "/insTask")
@Controller
public class InsTaskController  extends BaseAction{

	@Resource
	private GeneralService generalService;
	
	@Resource
	private InsTaskService insTaskServiceImpl;
	
	@Resource
	private EquipService equipService;
	
	/**
	 * 跳转至巡检任务列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		//初始化地区信息
		String areaId = (String) request.getSession().getAttribute("areaId");
		List<Map<String,String>> areaList = generalService.getSonAreaList(areaId);
		map.put("areaList", areaList);
		if (areaId.equals("21")) {
			Map areaMap = new HashMap();
			areaMap.put("AREAID", "20");
			areaMap.put("AREANAME", "苏州市区");
			areaList.add(areaMap);
			areaMap = new HashMap();
			areaMap.put("AREAID", "null");
			areaMap.put("AREANAME", "空白");
			areaList.add(areaMap);
		}
		
		//初始化公司信息
		Map paramMap = new HashMap();
		paramMap.put("areaId", areaId);
		List<Map> companyList = generalService.getCompanyList(paramMap);
		map.put("companyList", companyList);
		
		return new ModelAndView("/inspecthelper/instask/instask_index",map);
	}
	
	/**
	 * 查询巡检任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException{
		
		Map map = insTaskServiceImpl.getTaskList(request, pager);
		write(response,map);
	}
	
	/**
	 * 跳转至分配巡检任务页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addTaskIndex.do")
	public ModelAndView addTaskIndex(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		String areaId = (String) request.getSession().getAttribute("areaId");
		map.put("equpAreaId", areaId);
		
		//初始化地区
		List areaList = generalService.getSonAreaList(areaId);
		map.put("areaList", areaList);
		
		//初始化公司信息,公司根据地域ID获取
		Map paramMap = new HashMap();
		paramMap.put("areaId", areaId);
		List<Map> companyList = generalService.getCompanyList(paramMap);
		map.put("companyList", companyList);
		
		//初始化区域，如果是苏州就获取用户的子区域ID
		if(areaId.equals("20"))
		{
			areaId=(String) request.getSession().getAttribute("sonAreaId");
		}
		List sonAreaList = generalService.getSonAreaList(areaId);
		map.put("sonAreaList", sonAreaList);
		
		return new ModelAndView("/inspecthelper/instask/instask_add_index",map);
	}
	
	/**
	 *  查询设备数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryTask.do")
	public void queryTask(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException{
		Map<String, Object> map = equipService.getEquipList(request, pager);
		write(response, map);
	}
	
	/**
	 * 跳至巡检任务分配页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addTask.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		return new ModelAndView("/inspecthelper/instask/instask_allot",map);
	}
	
	/**
	 * 巡检任务分配
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/allotTask.do")
	@ResponseBody
	public Map allotTask(HttpServletRequest request,
			HttpServletResponse response){
		
		Map map = new HashMap();
		Boolean status = true;
		try {
			insTaskServiceImpl.allotTask(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
		
	}
	
	
	/**
	 * 跳转至调整巡检任务页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/modifyTaskIndex.do")
	public ModelAndView modifyTaskIndex(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		return new ModelAndView("/inspecthelper/instask/instask_modify_index",map);
	}
	
	/**
	 * 巡检任务调整
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/modifyTask.do")
	@ResponseBody
	public Map modifyTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insTaskServiceImpl.modifyTask(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 * 日常巡检任务删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteTask.do")
	public void deleteTask(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			insTaskServiceImpl.deleteTask(request);
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
	 * 跳转至日常巡检任务详情页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/detailTaskIndex.do")
	public ModelAndView detailTaskIndex(HttpServletRequest request,
			HttpServletResponse response){
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		return new ModelAndView("/inspecthelper/instask/instask_detail_index",map);
	}
	
	
	/**
	 * 查询巡检任务详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getTaskDetailList.do")
	public void getTaskDetailList(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException{
		Map map = insTaskServiceImpl.getTaskDetailList(request, pager);
		write(response,map);
	}
	
	
	/**
	 *跳转至巡检任务人员分配页面
	 *
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/staffIndex.do")
	public ModelAndView staffIndex(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		
		map.put("areaList", areaList);
		
		String areaId = (String) request.getSession().getAttribute("areaId");
		map.put("areaId", areaId);
		
		return new ModelAndView("/inspecthelper/instask/lookup-index", map);
	}
	
}
