package com.inspecthelper.action;

import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inspecthelper.service.ICheckOdfService;


import util.page.BaseAction;

@RequestMapping(value="/mobile/check-odf")
@SuppressWarnings("all")
@Controller
public class CheckOdfController extends BaseAction{

	@Resource
	ICheckOdfService checkOdfServiceImpl;
	/*
	 * 获取设备信息
	 * 
	 */
	@RequestMapping(value = "/getResInfo.do")
	public void getResInfo (HttpServletRequest request,
			HttpServletResponse response){
		try {
		String jsonStr = BaseServletTool.getParam(request);
//		jsonStr = "{\"key\":\"cx\",\"staffNo\": \"900037\",\"areaId\":\"15\",\"sn\":\"22\",\"startDate\": \"\",\"endDate\":\"\"}";
		String reJsonStr = checkOdfServiceImpl.getResInfo(jsonStr);
		BaseServletTool.sendParam(response, reJsonStr);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}
