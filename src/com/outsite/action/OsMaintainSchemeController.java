/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.outsite.action;

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

import com.linePatrol.util.MapUtil;
import com.outsite.service.OsMaintainSchemeService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/osMaintainSchemeController")
@Controller
public class OsMaintainSchemeController extends BaseAction {

    @Resource
    private OsMaintainSchemeService osMaintainSchemeService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据
	List<Map<String, Object>> list = null;

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("", list);
	map.put("area_id", request.getSession().getAttribute("areaId"));

	return new ModelAndView("/outsite/osMaintainScheme/osMaintainScheme_index", map);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map<String, Object> map = osMaintainSchemeService.query(para, pager);
	write(response, map);
    }

    @RequestMapping(value = "/osMaintainSchemeAddUI.do")
    public ModelAndView osMaintainSchemeAddUI(HttpServletRequest request,
	    HttpServletResponse response) throws IOException {
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据
	// 获得登录ID用户的信息
	// String staffId = BaseUtil.getStaffId(request);
	// map.put("staffId", staffId);

	List<Map<String, Object>> list = null;

	return new ModelAndView("/outsite/osMaintainScheme/osMaintainScheme_add", map);
    }

    @RequestMapping(value = "/osMaintainSchemeSave.do")
    @ResponseBody
    public Map osMaintainSchemeSave(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map map = new HashMap();
	Boolean status = true;
	try {
	    osMaintainSchemeService.osMaintainSchemeSave(para);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/osMaintainSchemeEditUI.do")
    public ModelAndView osMaintainSchemeEditUI(HttpServletRequest request,
	    HttpServletResponse response, String id) {
	Map<String, Object> rs = null;
	// 准备数据
	List<Map<String, Object>> list = null;

	rs = osMaintainSchemeService.findById(id);
	return new ModelAndView("/outsite/osMaintainScheme/osMaintainScheme_edit", rs);

    }

    @RequestMapping(value = "/osMaintainSchemeUpdate.do")
    @ResponseBody
    public Map osMaintainSchemeUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    osMaintainSchemeService.osMaintainSchemeUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/osMaintainSchemeDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    osMaintainSchemeService.osMaintainSchemeDelete(ids);
	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}

	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/detailUI.do")
    public ModelAndView detailUI(HttpServletRequest request, HttpServletResponse response,
	    String scheme_id) {
	Map<String, Object> map = osMaintainSchemeService.findDetail(scheme_id);
	return new ModelAndView("/outsite/osMaintainScheme/osMaintainScheme_detailUI", map);

    }

}
