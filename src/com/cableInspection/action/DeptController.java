package com.cableInspection.action;

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

import com.cableInspection.service.DeptService;
import com.system.service.GeneralService;

import util.page.BaseAction;
import util.page.UIPage;


@SuppressWarnings("all")
@Controller
@RequestMapping("Dept")
public class DeptController extends BaseAction{
	@Resource
	private GeneralService generalService;
	
	@Resource
	private DeptService deptService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = null;
		return new ModelAndView("cableinspection/dept/index", rs);
	}

	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = deptService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		rs.put("areaList", areaList);
		return new ModelAndView("cableinspection/dept/add", rs);
	}
	
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Map save(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			deptService.insert(request);
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
		rs = deptService.edit(request);
		return new ModelAndView("cableinspection/dept/edit", rs);
	}
	
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			deptService.update(request);
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
			deptService.delete(request);
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
	@RequestMapping(value = "/staffSelected.do")
	public ModelAndView staffSelected(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		rs.put("dept_id", request.getParameter("dept_id"));
		return new ModelAndView("cableinspection/dept/dept_staff", rs);
	}
	@RequestMapping(value = "/query_staff.do")
	public void queryStaff(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = deptService.getStaff(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/distributeStaff.do")
	public void distributeStaff(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		Boolean status = true;
		try {
			deptService.distributeStaff(request);
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
	
	@ResponseBody
	@RequestMapping("/deptSelectTip.do")
	public void deptSelectTip(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String json = deptService.getDeptSelection(request);
		response.getWriter().write(json);
	}
	
	@ResponseBody
	@RequestMapping("/getDeptSelectionForCable.do")
	public void getDeptSelectionForCable(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String json = deptService.getDeptSelectionForCable(request);
		response.getWriter().write(json);
	}
	@ResponseBody
	@RequestMapping("/getDeptByAreaId.do")
	public void getDeptByAreaId(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String json = deptService.getDeptByAreaId(request);
		response.getWriter().write(json);
	}
}
