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
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.linePatrol.service.LineInfoService;
import com.linePatrol.service.SiteService;
import com.linePatrol.service.gldManageService;
import com.linePatrol.service.relayService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.service.ParamService;
import com.system.service.StaffService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/siteController")
@Controller
public class SiteControler extends BaseAction {

    @Resource
    private SiteService siteService;
    @Resource
    private StaffService staffService;
    @Resource
    private LineInfoService lineInfoService;
    @Resource
    private ParamService paramService;
    @Resource
    private relayService relayService;
    @Resource
    private gldManageService gldManageService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据
	Map<String, Object> map = staffService.findPersonalInfo(StaffUtil.getStaffId(request));

	// 光缆段
	String localId = StaffUtil.getStaffAreaId(request);
	List<Map<String, Object>> calbeList = gldManageService.getGldByAreaId(localId);

	map.put("calbeList", calbeList);
	return new ModelAndView("/linePatrol/xunxianManage/site/site_index", map);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map<String, Object> para2 = MapUtil.parameterMapToCommonMap(request);

	// 去掉参数前面p add index页面参数名字冲突
	Set<String> set = para.keySet();// set 仅仅指针引用 非独立对象 不能再遍历的时候crud
					// ConcurrentModificationException
	for (String s : set) {

	    if (s.startsWith("p_")) {

		String key = s.substring(2, s.length());
		para2.put(key, para.get(s));
	    }

	}

	Map<String, Object> map = siteService.query(para2, pager);
	write(response, map);
    }
    
    /**
     * TODO 删除巡线点页面
     * @param relayId
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteSite.do")
    public String deleteSite(String relayId, Model model){
    	
    	List<Map<String, Object>> siteList = siteService.getSitesByRelayId(relayId);
    	if(siteList.size()>0 && siteList != null){
    		model.addAttribute("relayName", siteList.get(0).get("RELAY_NAME").toString());
    		model.addAttribute("cableName", siteList.get(0).get("CABLE_NAME").toString());
    	}
    	model.addAttribute("siteList", siteList);
    	return "/linePatrol/xunxianManage/site/delSite";
    }
    
	@RequestMapping("/doDelete.do")
	@ResponseBody
	public Map doDelete(HttpServletRequest request, HttpServletResponse response, String ids){
		
		Map map = new HashMap();
		String msg = siteService.deleteSite(ids);
		map.put("msg", msg);
		
		return map;
	}
    
    
    @RequestMapping(value = "/findSiteByLine.do")
    public void findSiteByLine(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

	map.put("status", true);
	List<Map<String, Object>> res = null;
	try {
	    // 获取默认距离
	    String areaId = StaffUtil.getStaffAreaId(request);
	    para.put("areaId", areaId);
	    res = siteService.findSiteByLine(para);

	    String defaultDis = paramService.getParamValue("siterange", areaId);
	    map.put("defaultDis", defaultDis);
	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}

	map.put("res", res);
	System.out.println(res);
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/siteEditUI.do")
    public ModelAndView siteEditUI(HttpServletRequest request, HttpServletResponse response,
	    String site_id) {

		// 准备数据
		String id = site_id;
		Map<String, Object> map = siteService.findById(id);
	
		// 获取默认距离
		String areaId = StaffUtil.getStaffAreaId(request);
		String defaultDis = paramService.getParamValue("signrange", areaId);
		map.put("defaultDis", defaultDis);
		System.out.println(defaultDis);
		
		List<Map<String, Object>> photoList = siteService.getSitePhotoList(id);
		int photosize=0;
		if(photoList.size()>0){
			photosize=photoList.size();
			Map photo=photoList.get(0);
			map.put("photo", photo);
		}
		map.put("photosize", photosize);
		map.put("photoList", photoList);
	
		return new ModelAndView("/linePatrol/xunxianManage/site/site_edit", map);
    }

    @RequestMapping(value = "/update.do")
    @ResponseBody
    public Map update(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    siteService.update(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }
    
    
    

}
