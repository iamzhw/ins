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

import com.linePatrol.service.MobileInfoService;
import com.linePatrol.util.MapUtil;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/mobileInfoController")
@Controller
public class MobileInfoController extends BaseAction {

    @Resource
    private MobileInfoService mobileInfoService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据
	List<Map<String, Object>> list = null;

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("", list);

	return new ModelAndView("/linePatrol/xunxianManage/mobileInfo/mobileInfo_index", map);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map<String, Object> map = mobileInfoService.query(para, pager);
	write(response, map);
    }

    @RequestMapping(value = "/mobileInfoAddUI.do")
    public ModelAndView mobileInfoAddUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据
	// 获得登录ID用户的信息
	// String staffId = BaseUtil.getStaffId(request);
	// map.put("staffId", staffId);

	List<Map<String, Object>> list = null;

	return new ModelAndView("/linePatrol/xunxianManage/mobileInfo/mobileInfo_add", map);
    }

    @RequestMapping(value = "/mobileInfoSave.do")
    @ResponseBody
    public Map mobileInfoSave(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map map = new HashMap();
	Boolean status = true;
	try {
	    mobileInfoService.mobileInfoSave(para);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/mobileInfoEditUI.do")
    public ModelAndView mobileInfoEditUI(HttpServletRequest request, HttpServletResponse response,
	    String id) {
	Map<String, Object> rs = null;
	// 准备数据
	List<Map<String, Object>> list = null;

	rs = mobileInfoService.findById(id);
	return new ModelAndView("/linePatrol/xunxianManage/mobileInfo/mobileInfo_edit", rs);

    }

    @RequestMapping(value = "/mobileInfoUpdate.do")
    @ResponseBody
    public Map mobileInfoUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    mobileInfoService.mobileInfoUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/mobileInfoDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    mobileInfoService.mobileInfoDelete(ids);
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

}
