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
import com.linePatrol.util.StaffUtil;
import com.outsite.service.PartTimeModelService;
import com.system.service.ParamService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/partTimeModelController")
@Controller
public class PartTimeModelController extends BaseAction {

    @Resource
    private PartTimeModelService partTimeModelService;
    @Resource
    private ParamService paramService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据

	Map<String, Object> res = paramService.query(request);
	return new ModelAndView("/outsite/partTimeModel/partTimeModel_index", res);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

	Map<String, Object> res = paramService.query(request);
	String isAdmin = res.get("isAdmin").toString();
	if ("1".equals(isAdmin)) {
	    para.put("area_id", res.get("param_areaId").toString());
	}

	Map<String, Object> map = partTimeModelService.query(para, pager);
	write(response, map);
    }

    @RequestMapping(value = "/partTimeModelAddUI.do")
    public ModelAndView partTimeModelAddUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据
	// 获得登录ID用户的信息
	// String staffId = BaseUtil.getStaffId(request);
	// map.put("staffId", staffId);

	List<Map<String, Object>> cityList = partTimeModelService.getCity();
	map.put("cityList", cityList);

	return new ModelAndView("/outsite/partTimeModel/partTimeModel_add", map);
    }

    @RequestMapping(value = "/partTimeModelSave.do")
    @ResponseBody
    public Map partTimeModelSave(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map map = new HashMap();
	Boolean status = true;
	try {
	    String parent_city = StaffUtil.getStaffAreaId(request);
	    para.put("parent_city", parent_city);
	    partTimeModelService.partTimeModelSave(para);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/partTimeModelEditUI.do")
    public ModelAndView partTimeModelEditUI(HttpServletRequest request,
	    HttpServletResponse response, String id) {
	Map<String, Object> rs = null;
	// 准备数据
	List<Map<String, Object>> list = null;

	rs = partTimeModelService.findById(id);

	List<Map<String, Object>> cityList = partTimeModelService.getCity();
	rs.put("cityList", cityList);

	return new ModelAndView("/outsite/partTimeModel/partTimeModel_edit", rs);

    }

    @RequestMapping(value = "/partTimeModelUpdate.do")
    @ResponseBody
    public Map partTimeModelUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    partTimeModelService.partTimeModelUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/partTimeModelDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    partTimeModelService.partTimeModelDelete(ids);
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
