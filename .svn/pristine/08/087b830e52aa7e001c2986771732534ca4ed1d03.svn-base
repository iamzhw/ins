package com.system.action;

import java.io.IOException;
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

import com.system.model.ZTreeNode;
import com.system.service.RoleService;

/**
 * 
 * @ClassName: RoleController
 * @Description: 角色管理
 * @author: SongYuanchen
 * @date: 2014-1-17
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/Role")
@Controller
public class RoleController extends BaseAction {
	@Resource
	private RoleService roleService;

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		return new ModelAndView("system/role/role-index", rs);
	}

	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = roleService.query(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		return new ModelAndView("system/role/role-add", rs);
	}

	@RequestMapping(value = "/proveUniqueness.do")
	@ResponseBody
	public Map proveUniqueness(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = roleService.proveUniqueness(request);
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Map save(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			roleService.insert(request);
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
		rs = roleService.edit(request);
		return new ModelAndView("system/role/role-edit", rs);
	}

	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			roleService.update(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map map = new HashMap();
		Boolean status = true;
		try {
			roleService.delete(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		response.getWriter().write(JSONObject.fromObject(map).toString());

	}

	@RequestMapping(value = "/getAllGns.do")
	@ResponseBody
	public List getAllGns(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String role_id = request.getParameter("role_id");
		int treeId = -1;
		if (id != null && !id.equals("")) {
			treeId = Integer.parseInt(id);
		}
		List<ZTreeNode> nodes = roleService.getAllGns(treeId, role_id);
		return nodes;
	}

	@RequestMapping(value = "/assignPermissions.do")
	@ResponseBody
	public Map assignPermissions(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String role_id = request.getParameter("role_id");
		String resources = request.getParameter("resources");
		roleService.assignPermissions(role_id, resources);
		Map map = new HashMap();
		Boolean flag = true;
		if (flag) {
			map.put("flag", "1");
			return map;
		} else {
			map.put("flag", "0");
			return map;
		}

	}

}
