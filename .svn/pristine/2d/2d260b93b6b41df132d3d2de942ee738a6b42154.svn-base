package com.system.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.system.service.GeneralService;
import com.system.service.SoftwareVersionService;
import com.system.service.StaffService;

/**
 * 
 * @ClassName: VersionController
 * @Description: 版本管理
 * @author: maxiangyang
 * @date: 2014-5-5
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/Version")
@Controller
public class VersionController extends BaseAction {
	@Resource
	private StaffService staffService;
	@Resource
	private GeneralService generalService;
	@Resource
	private SoftwareVersionService softwareVersionService;	
	private File file;

	/**
	 * 
	 * @Title: index
	 * @Description: 版本管理初始化方法
	 * @param: @param request
	 * @param: @param response
	 * @param: @param versionForm
	 * @param: @return
	 * @return: ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> softwareList = generalService
				.getSoftwareList();
		rs.put("softwareList", softwareList);
		return new ModelAndView("system/version/version-index", rs);
	}

	/**
	 * 
	 * @Title: query
	 * @Description: 版本管理初始化方法
	 * @param: @param request
	 * @param: @param response
	 * @param: @param versionForm
	 * @param: @return
	 * @return: ModelAndView
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/query.do")
	public JSONObject query(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Map<String, Object> softwarePagelist = generalService
				.getSoftwarePageList(request, pager);
		return JSONObject.fromObject(softwarePagelist);
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加版本
	 * @param: @param request
	 * @param: @param response
	 * @param: @param versionForm
	 * @param: @return
	 * @return: ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> softwareList = generalService
				.getSoftwareList();
		rs.put("softwareList", softwareList);
		return new ModelAndView("system/version/version-add", rs);
	}

	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Map save(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = false;
		try {
			softwareVersionService.insert(request);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map map = new HashMap();
		Boolean status = true;
		try {
			softwareVersionService.delete(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	
	@RequestMapping(value = "/downLoadAPK.do")
	@ResponseBody
	public void downLoadAPK(HttpServletRequest request, HttpServletResponse response,String softId)
			throws IOException, ServletException {
		 softwareVersionService.downLoadAPK(request,response,softId);
	}
}
