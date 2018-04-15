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

import com.roomInspection.service.ClassService;
import com.system.service.GeneralService;

import util.page.BaseAction;
import util.page.UIPage;

/**
 * @ClassName: ClassController
 * @Description: 代维公司管理
 * @author xiazy
 * @date: 2014-7-03
 *
 */
@SuppressWarnings("all")
@RequestMapping(value = "/Class")
@Controller
public class ClassController extends BaseAction{
	
	@Resource
	private GeneralService generalService;
	
	@Resource
	private ClassService classService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		rs.put("areaList", areaList);
		return new ModelAndView("roominspection/class/class_index", rs);
	}

	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = classService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		rs.put("areaList", areaList);
		return new ModelAndView("roominspection/class/class_add", rs);
	}
	
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Map save(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			classService.insert(request);
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
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = classService.edit(request);
		return new ModelAndView("roominspection/class/class_edit", rs);
	}
	
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			classService.update(request);
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
			classService.delete(request);
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
}
