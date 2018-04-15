package com.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.model.ZTreeNode;
import com.system.service.ResourceService;

/**
 * 
 * @ClassName: ResourceController
 * @Description: 
 * @author: SongYuanchen
 * @date: 2014-1-16
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/Resource")
@Controller
public class ResourceController {
	@Resource
	private ResourceService resourceService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		return new ModelAndView("system/resource/resource-index", rs);
	}
	
	@RequestMapping(value = "/getGns.do")
	@ResponseBody
	public List getGns(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String id = request.getParameter("id");
		int treeId = -2;
		if (id != null && !id.equals("")) {
			treeId = Integer.parseInt(id);
		}
		List<ZTreeNode> nodes = resourceService.getGns(treeId);
		return nodes;
	}
	@RequestMapping(value = "/updateGns.do")
	@ResponseBody
	public Map updateGns(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			resourceService.updateGns(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	@RequestMapping(value = "/removeGns.do")
	@ResponseBody
	public Map removeGns(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			resourceService.removeGns(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	@RequestMapping(value = "/addGns.do")
	@ResponseBody
	public Map addGns(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			resourceService.addGns(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	


}
