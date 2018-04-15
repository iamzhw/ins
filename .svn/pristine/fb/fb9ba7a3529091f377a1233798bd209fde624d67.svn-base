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

import com.linePatrol.service.LineInfoService;
import com.linePatrol.service.UserSignService;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.service.StaffService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/userSignController")
@Controller
public class UserSignController extends BaseAction {

    @Resource
    private UserSignService userSignService;
    @Resource
    private StaffService staffService;
    @Resource
    private LineInfoService lineInfoService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据

	// g个人信息
	Map<String, Object> staffInfo = staffService
		.findPersonalInfo(StaffUtil.getStaffId(request));

	map.put("staffInfo", staffInfo);
	// 本地签到人
	String localId = StaffUtil.getStaffAreaId(request);
	String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
	List<Map<String, Object>> localInspactPerson=lineInfoService.getLocalPerson(localId, orgId);
	map.put("localInspactPerson", localInspactPerson);

	String today = DateUtil.getDate();
	map.put("today", today);

	return new ModelAndView("/linePatrol/xunxianManage/userSign/userSign_index", map);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {

    }

    @RequestMapping(value = "/userSignAddUI.do")
    public ModelAndView userSignAddUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据
	// 获得登录ID用户的信息
	// String staffId = BaseUtil.getStaffId(request);
	// map.put("staffId", staffId);

	List<Map<String, Object>> list = null;

	return new ModelAndView("/linePatrol/xunxianManage/userSign/userSign_add", map);
    }

    @RequestMapping(value = "/userSignSave.do")
    @ResponseBody
    public Map userSignSave(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map map = new HashMap();
	Boolean status = true;
	try {
	    userSignService.userSignSave(para);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/userSignEditUI.do")
    public ModelAndView userSignEditUI(HttpServletRequest request, HttpServletResponse response,
	    String id) {
	Map<String, Object> rs = null;
	// 准备数据
	List<Map<String, Object>> list = null;

	rs = userSignService.findById(id);
	return new ModelAndView("/linePatrol/xunxianManage/userSign/userSign_edit", rs);

    }

    @RequestMapping(value = "/userSignUpdate.do")
    @ResponseBody
    public Map userSignUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    userSignService.userSignUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/userSignDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    userSignService.userSignDelete(ids);
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

    @RequestMapping(value = "/getUserSignByUserid.do")
    public void getUserSignByUserid(HttpServletRequest request, HttpServletResponse response,
	    String ids) {
	Map map = new HashMap();
	map.put("status", true);
	try {
	    Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	    para.put("area_id", StaffUtil.getStaffAreaId(request));
	    List<Map<String, Object>> userSignList = userSignService.getUserSignByUserid(para);
	    map.put("userSignList", userSignList);
	    // map.put("total", userSignList.size());
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

    @RequestMapping(value = "/getTheLineInfoForSign.do")
    public void getTheLineInfoForSign(HttpServletRequest request, HttpServletResponse response) {
	Map res = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

	try {

	    res = userSignService.getTheLineInfoForSign(para);
	    res.put("status", true);

	} catch (Exception e) {

	    e.printStackTrace();

	    res.put("status", false);
	}

	try {
	    response.getWriter().write(JSONObject.fromObject(res).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
