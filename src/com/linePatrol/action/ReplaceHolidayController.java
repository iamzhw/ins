/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.linePatrol.service.LineInfoService;
import com.linePatrol.service.ReplaceHolidayService;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/replaceHolidayController")
@Controller
public class ReplaceHolidayController extends BaseAction {

    @Resource
    private ReplaceHolidayService replaceHolidayService;
    @Resource
    private LineInfoService lineInfoService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据

	Map<String, Object> map = new HashMap<String, Object>();
	String localId = StaffUtil.getStaffAreaId(request);
	String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
	List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
//	List<Map<String, Object>> localInspactStaff = lineInfoService
//		.getLocalInspactPerson(localId);
	map.put("localInspactStaff", localInspactStaff);

	return new ModelAndView("/linePatrol/xunxianManage/replaceHoliday/replaceHoliday_index",
		map);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
	para.put("orgId", orgId);
	List<Map<String, Object>> allHoliday = replaceHolidayService.query(para);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("allHoliday", allHoliday);
	map.put("today", para.get("p_query_time").toString() + "-01");
	response.getWriter().write(JSONObject.fromObject(map).toString());
    }

    @RequestMapping(value = "/replaceHolidayAddUI.do")
    public ModelAndView replaceHolidayAddUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据
	String localId = StaffUtil.getStaffAreaId(request);
	String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
	List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
	
//	List<Map<String, Object>> localInspactStaff = lineInfoService
//		.getLocalInspactPerson(localId);
	map.put("localInspactStaff", localInspactStaff);

	return new ModelAndView("/linePatrol/xunxianManage/replaceHoliday/replaceHoliday_add", map);
    }

    @RequestMapping(value = "/replaceHolidaySave.do")
    @ResponseBody
    public void replaceHolidaySave(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map map = new HashMap();
	Boolean status = true;
	try {
	    List<Map<String, Object>> allHoliday = replaceHolidayService.replaceHolidaySave(para);

	    map.put("allHoliday", allHoliday);
	    map.put("today", DateUtil.getDate());
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);

	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/replaceHolidayEditUI.do")
    public ModelAndView replaceHolidayEditUI(HttpServletRequest request,
	    HttpServletResponse response, String id) {
	Map<String, Object> rs = replaceHolidayService.findById(id);
	// 要展现的数据
	String localId = StaffUtil.getStaffAreaId(request);
	String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
	List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
//	List<Map<String, Object>> localInspactStaff = lineInfoService
//		.getLocalInspactPerson(localId);
	rs.put("localInspactStaff", localInspactStaff);
	return new ModelAndView("/linePatrol/xunxianManage/replaceHoliday/replaceHoliday_edit", rs);

    }

    @RequestMapping(value = "/replaceHolidayUpdate.do")
    @ResponseBody
    public Map replaceHolidayUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    List<Map<String, Object>> allHoliday = replaceHolidayService.replaceHolidayUpdate(para);
	    map.put("allHoliday", allHoliday);
	    map.put("today", DateUtil.getDate());
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/replaceHolidayDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String holiday_id) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    List<Map<String, Object>> allHoliday = replaceHolidayService
		    .replaceHolidayDelete(holiday_id);
	    map.put("allHoliday", allHoliday);
	    map.put("today", DateUtil.getDate());
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
    
    
    @RequestMapping("/detailInit")
    public ModelAndView detailInfoInit(){
    	return new ModelAndView("/linePatrol/xunxianManage/replaceHoliday/showDetailInfo");
    }
    
    @RequestMapping("/showDetailInfo")
    @ResponseBody
    public List<Map<String, Object>> showDetailInfo(HttpServletRequest request){
    	String localId = StaffUtil.getStaffAreaId(request);
    	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
    	para.put("localId", localId);
    	List<Map<String, Object>> list=replaceHolidayService.showDetailInfo(para);
    	return list;
    }

}
