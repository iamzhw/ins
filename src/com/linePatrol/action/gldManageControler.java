package com.linePatrol.action;

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

import com.linePatrol.service.gldManageService;
import com.linePatrol.util.MapUtil;
import com.system.service.AreaService;
import com.system.service.ParamService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/gldManage")
@Controller
public class gldManageControler extends BaseAction {

    @Resource
    private gldManageService gldManageService;
    @Resource
    private AreaService areaService;
    @Resource
    private ParamService paramService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	Map<String, Object> map = paramService.query(request);
	return new ModelAndView("linePatrol/xunxianManage/gldManage/gld_index", map);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map<String, Object> map = gldManageService.query(para, pager);
	write(response, map);
    }
    /**
     * 打开新增窗口
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addUI.do")
    public ModelAndView addUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	Map<String, Object> rs = new HashMap<String, Object>();
	// 要展现的数据
	List<Map<String, Object>> areaList = areaService.getAllArea();
	rs.put("areaList", areaList);

	return new ModelAndView("linePatrol/xunxianManage/gldManage/gld_add", rs);
    }
    /**
     * 新增光缆
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Map save(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		Map map = new HashMap();
		Boolean status = true;
		try {
		    gldManageService.save(para);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
    }

    @RequestMapping(value = "/editUI.do")
    public ModelAndView editUI(HttpServletRequest request, HttpServletResponse response, String id) {
	Map<String, Object> rs = null;

	rs = gldManageService.editUI(id);
	List<Map<String, Object>> areaList = areaService.getAllArea();
	rs.put("areaList", areaList);
	return new ModelAndView("linePatrol/xunxianManage/gldManage/gld_edit", rs);

    }

    @RequestMapping(value = "/update.do")
    @ResponseBody
    public Map update(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    gldManageService.update(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/delete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	try {
	    gldManageService.delete(ids);
	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}
	map.put("status", true);
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/getCableByAreaid.do")
    public void getCableByAreaid(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();

	String area_id = request.getParameter("area_id");
	try {
	    List<Map<String, Object>> cableList = gldManageService.getGldByAreaId(area_id);
	    map.put("cableList", cableList);
	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}
	map.put("status", true);
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
    /**
     * 验证颜色是否可用
     * @param request
     * @param response
     * @param cable_color
     */
    @RequestMapping(value = "/validateCblColor.do")
    public void validateCblColor(HttpServletRequest request, HttpServletResponse response,
	    String cable_color) {
		int res = 0;
		boolean status = false;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		    // 1不可用 0可用
		    res = gldManageService.validateCblColor(cable_color);
		} catch (Exception e) {
		    e.printStackTrace();
		    map.put("status", false);
		}
		map.put("status", true);
		map.put("res", res);
		try {
		    response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }

}
