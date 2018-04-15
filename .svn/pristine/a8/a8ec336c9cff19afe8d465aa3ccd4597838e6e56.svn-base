/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.system.action;

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
import com.system.service.MmsModelService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/mmsModelController")
@Controller
public class MmsModelController extends BaseAction {

    @Resource
    private MmsModelService mmsModelService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据
	List<Map<String, Object>> modalTypeList = mmsModelService.getModalTypeList();

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modalTypeList", modalTypeList);

	return new ModelAndView("/system/shortMessage/mmsModel_index", map);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map<String, Object> map = mmsModelService.query(para, pager);
	write(response, map);
    }

    @RequestMapping(value = "/mmsModelAddUI.do")
    public ModelAndView mmsModelAddUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	// 要展现的数据
	// 获得登录ID用户的信息
	// String staffId = BaseUtil.getStaffId(request);
	// map.put("staffId", staffId);

	List<Map<String, Object>> modalTypeList = mmsModelService.getModalTypeList();

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modalTypeList", modalTypeList);

	return new ModelAndView("/system/shortMessage/mmsModel_add", map);
    }

    @RequestMapping(value = "/mmsModelSave.do")
    @ResponseBody
    public Map mmsModelSave(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map map = new HashMap();
	Boolean status = true;
	try {
	    mmsModelService.mmsModelSave(para);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/mmsModelEditUI.do")
    public ModelAndView mmsModelEditUI(HttpServletRequest request, HttpServletResponse response,
	    String id) {
	Map<String, Object> rs = mmsModelService.findById(id);

	List<Map<String, Object>> modalTypeList = mmsModelService.getModalTypeList();

	rs.put("modalTypeList", modalTypeList);

	return new ModelAndView("/system/shortMessage/mmsModel_edit", rs);

    }

    @RequestMapping(value = "/mmsModelUpdate.do")
    @ResponseBody
    public Map mmsModelUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    mmsModelService.mmsModelUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/mmsModelDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    mmsModelService.mmsModelDelete(ids);
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

    /*
     * 短信模板设置
     */
    @RequestMapping(value = "/mmsSetIndex.do")
    public ModelAndView mmsSetIndex(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据
	List<Map<String, Object>> mmsModelList = mmsModelService.findAll();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("mmsModelList", mmsModelList);

	// 本地看护员
	String areaId = StaffUtil.getStaffAreaId(request);
	List<Map<String, Object>> khyList = mmsModelService.getLocalKhy(areaId);

	map.put("khyList", khyList);

	return new ModelAndView("/system/shortMessage/mmsMSet_index", map);
    }

    // 保存设置结果
    @RequestMapping(value = "/saveShortMessageAlarm.do")
    public void saveShortMessageAlarm(HttpServletRequest request, HttpServletResponse response,
	    String ids) {
	Map map = new HashMap();

	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	map.put("status", true);
	try {
	    mmsModelService.saveShortMessageAlarm(para);
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

    // 查询莫个人的设置情况
    @RequestMapping(value = "/getSettings.do")
    public void getSettings(HttpServletRequest request, HttpServletResponse response, String staffId) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    List<Map<String, Object>> settingList = mmsModelService.getSettings(staffId);
	    map.put("settingList", settingList);
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
