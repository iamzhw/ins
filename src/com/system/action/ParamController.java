package com.system.action;

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

import com.system.service.GeneralService;
import com.system.service.ParamService;

/**
 * 
 * @ClassName: ParamController
 * @Description: 参数管理
 * @author: huliubing
 * @date: 2014-1-17
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/Param")
@Controller
public class ParamController extends BaseAction {
	
	@Resource
	private ParamService paramService;
	
	@Resource
	private GeneralService generalService;
	
	@RequestMapping(value ="/index.do")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> map = paramService.query(request);
		List<Map<String,String>> areaList = generalService.getAreaList();
		map.put("areaList", areaList);
		String landmarkCir =paramService.getParamValue("landmarkstep", "2");//查询地标省的步巡频次
		map.put("landmarkCir", landmarkCir);
		return new ModelAndView("system/param/param_index",map);
	}
	
	
	@RequestMapping(value="/save.do")
	@ResponseBody
	public Map save(HttpServletRequest request,HttpServletResponse response){
		
		Map map = new HashMap();
		Boolean status = true;
		try {
			paramService.save(request);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
	}
	
	
	@RequestMapping(value = "/autoTakindex.do")
	public ModelAndView autoTakindex(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("system/param/line_auto_task_index", new HashMap());
	}

	@RequestMapping(value = "/autoLineTask.do")
	@ResponseBody
	public Map autoLineTask(HttpServletRequest request, HttpServletResponse response) {

		Map map = new HashMap();
		boolean status = true;
		try {
			paramService.autoLineTask(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
}
