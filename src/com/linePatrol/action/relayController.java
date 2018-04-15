/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
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
import com.linePatrol.service.relayService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.service.AreaService;
import com.system.service.ParamService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/relayController")
@Controller
public class relayController extends BaseAction {

    @Resource
    private relayService relayService;
    @Resource
    private gldManageService gldManageService;
    @Resource
    private AreaService areaService;
    @Resource
    private ParamService paramService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备光缆列表
	Map<String, Object> param = paramService.query(request);// 0
	String isAdmin = param.get("isAdmin").toString();
	List<Map<String, Object>> cableList = null;
	if (isAdmin.equals("0")) {
	    cableList = gldManageService.findAll();
	} else {
	    String area_id = StaffUtil.getStaffAreaId(request);
	    cableList = gldManageService.getGldByAreaId(area_id);
	}

	Map<String, Object> map = paramService.query(request);
	map.put("cableList", cableList);

	return new ModelAndView("/linePatrol/xunxianManage/relay/relay_index", map);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map<String, Object> map = relayService.query(para, pager);
	write(response, map);
    }

    @RequestMapping(value = "/relayAddUI.do")
    public ModelAndView relayAddUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 要展现的数据
		// 准备光缆列表
		List<Map<String, Object>> cableList = gldManageService.findAll();
		rs.put("cableList", cableList);
	
		// List<Map<String, Object>> areaList = areaService.getAllArea();
		// rs.put("areaList", areaList);
	
		return new ModelAndView("/linePatrol/xunxianManage/relay/relay_add", rs);
    }
    /**
     * 新增中继段
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/relaySave.do")
    @ResponseBody
    public Map relaySave(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		Map map = new HashMap();
		Boolean status = true;
		try {
		    relayService.relaySave(para);
		} catch (Exception e) {
		    e.printStackTrace();
		    status = false;
		}
		map.put("status", status);
		return map;
    }

    @RequestMapping(value = "/relayEditUI.do")
    public ModelAndView relayEditUI(HttpServletRequest request, HttpServletResponse response,
	    String id) {

	Map<String, Object> map = relayService.findById(id);

	List<Map<String, Object>> cableList = gldManageService.findAll();
	map.put("cableList", cableList);

	// 光缆段所在区域
	String cable_id = map.get("CABLE_ID").toString();
	List<Map<String, Object>> cableAreaList = gldManageService.getCableAreaList(cable_id);
	map.put("cableAreaList", cableAreaList);

	// 中继段区域
	String relayArea = relayService.getRelayArea(id);
	map.put("relayArea", relayArea);

	return new ModelAndView("/linePatrol/xunxianManage/relay/relay_edit", map);

    }

    @RequestMapping(value = "/relayUpdate.do")
    @ResponseBody
    public Map relayUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    relayService.relayUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/relayDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	try {
	    relayService.relayDelete(ids);
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

    @RequestMapping(value = "/getOwnArea.do")
    public void getOwnArea(HttpServletRequest request, HttpServletResponse response, String cable_id) {
	Map map = new HashMap();

	try {
	    List<Map<String, Object>> ownArea = relayService.getOwnArea(cable_id);
	    map.put("ownArea", ownArea);
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

}
