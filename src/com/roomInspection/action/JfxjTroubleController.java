package com.roomInspection.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.roomInspection.service.JfxjTroubleService;
import com.roomInspection.service.JobService;

/**
 * @ClassName: JfxjTroubleController
 * @Description: 巡检工单管理
 * @author huliubing
 * @date: 2014-07-23
 *
 */
@SuppressWarnings("all")
@RequestMapping(value = "/JfxjTrouble")
@Controller
public class JfxjTroubleController extends BaseAction{

	@Resource
	private JfxjTroubleService jfxjTroubleService;
	
	@Resource
	private JobService jobService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//初始化机房类型信息
		List<Map> roomTypeList = jobService.getRoomTypes();
		map.put("roomTypes", roomTypeList);
		
		return new ModelAndView("roominspection/trouble/trouble_index", map);
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String,Object> map = jfxjTroubleService.query(request, pager);
		write(response,map);
	}
	
	@RequestMapping(value = "/detail.do")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		Map<String,Object> map = jfxjTroubleService.detail(request);
		return new ModelAndView("roominspection/trouble/trouble_detail", map);
	}
}
