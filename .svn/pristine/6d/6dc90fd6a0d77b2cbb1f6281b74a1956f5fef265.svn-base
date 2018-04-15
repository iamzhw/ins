/**
 * FileName: GeneralController.java
 * @Description: TODO(用一句话描述该文件做什么) 
 * All rights Reserved, Designed By ZBITI 
 * Copyright: Copyright(C) 2014-2015
 * Company ZBITI LTD.

 * @author: SongYuanchen
 * @version: V1.0  
 * Createdate: 2014-1-17
 *

 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-1-17      wu.zh         1.0            1.0
 * Why & What is modified: <修改原因描述>
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

import com.system.service.GeneralService;

/**
 * @ClassName: GeneralController
 * @Description:
 * @author: SongYuanchen
 * @date: 2014-1-17
 * 
 */
@SuppressWarnings("all")
@RequestMapping("/General")
@Controller
public class GeneralController {
    @Resource
    private GeneralService generalService;

    @RequestMapping(value = "/getSonAreaList.do")
    @ResponseBody
    public Map getSonAreaList(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List<Map<String, String>> sonAreaList = generalService.getSonAreaList((String) request
			.getParameter("areaId"));
		map.put("sonAreaList", sonAreaList);
		return map;
    }
    /**
     * 获取综合化维护网格
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getGridList.do")
    @ResponseBody
    public Map getGridList(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		String areaId = request.getParameter("areaId");
		String sonAreaId = request.getParameter("sonAreaId");
		List<Map<String, String>> gridList = generalService.getGridList(areaId, sonAreaId);
		map.put("gridList", gridList);
		return map;
    }
    
    @RequestMapping(value = "/testDB.do")
    public void testDB(HttpServletRequest request, HttpServletResponse response) throws IOException {
	Map<String, Object> map = generalService.testDB();
	System.out.println("测试数据源切换");
    }

    @RequestMapping(value = "/getLocalCompanys.do")
    @ResponseBody
    public void getLocalCompanys(HttpServletRequest request, HttpServletResponse response) {

	Map map = new HashMap();

	try {
	    List<Map<String, String>> companyList = generalService.getOwnCompany((String) request
		    .getParameter("areaId"));
	    map.put("companyList", companyList);

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
    
    @RequestMapping(value = "/getDeptList.do")
    @ResponseBody
    public Map getDeptList(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List<Map<String, String>> deptList = generalService.getDeptList((String) request
			.getParameter("son_area_id"));
		map.put("deptList", deptList);
		return map;
    }

    
    @RequestMapping(value = "/getMainTainCompany.do")
    @ResponseBody
    public Map getMainTainCompany(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List<Map<String, String>> companyList = generalService.getMainTainCompany();
		map.put("mainTainCompany", companyList);
		return map;
    }
    
    @RequestMapping(value = "/getBanzuByCompanyId.do")
    @ResponseBody
    public Map getBanzuByCompanyId(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List<Map<String, String>> banzuList = generalService.getBanzuByCompanyId((String) request
				.getParameter("companyId"));
		map.put("banzu", banzuList);
		return map;
    }
    
    @RequestMapping(value = "/getBanzuByAreaId.do")
    @ResponseBody
    public Map getBanzuByAreaId(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List<Map<String, String>> banzuList = generalService.getBanzuByAreaId((String) request
				.getParameter("areaId"));
		map.put("banzu", banzuList);
		return map;
    }
}
