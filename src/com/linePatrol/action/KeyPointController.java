package com.linePatrol.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.linePatrol.service.KeyPointService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;

import util.page.BaseAction;
import util.page.UIPage;

@Controller
@RequestMapping("keyPoint")
public class KeyPointController extends BaseAction {
	
	@Resource
	private KeyPointService keyPointServiceImpl;

	/**
	 * 跳转到对应的页面
	 * @author zhai_wanpeng
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/init.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("/linePatrol/xunxianManage/keyPoint/keyPoint_line");
	}
	
	
	/**
	 *  初始化页面datagrid控件 
	 * @author zhai_wanpeng
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping("/keyPointList.do")
	public void query(HttpServletRequest request,HttpServletResponse response,UIPage pager) throws IOException{
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		 Map<String, Object> map = keyPointServiceImpl.query(para);
		 write(response,map);
	}
	
	/**
	 * 报表导出
	 * @author zhai_wanpeng
	 * @param request
	 * @param response
	 * @param pager
	 */
	 @RequestMapping(value = "/KeyPointInfoDownload.do")
	    public void xxdDownload(HttpServletRequest request, HttpServletResponse response, UIPage pager) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		keyPointServiceImpl.keyPointInfoDownLoad(para, request, response);
	    }
}
