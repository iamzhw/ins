package com.system.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import util.page.BaseAction;
import util.page.UIPage;
/**
 * @ClassName: SystemResourceController
 * @Description: 资源巡检
 * @author xiazy
 * @date: 2014-6-05
 *
 */
@RequestMapping(value = "/System")
@SuppressWarnings("all")
@Controller
public class SystemResourceController extends BaseAction{
	/*@Resource
	private SystemService systemService;*/
	
	@RequestMapping(value = "/systemIndex.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("system/resource/system_index", null);
	}
	/*@RequestMapping(value = "/openResource.do")
	public ModelAndView Query(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("system/resource/system_index", null);
	}*/
}
