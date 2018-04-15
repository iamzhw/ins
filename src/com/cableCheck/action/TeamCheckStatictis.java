package com.cableCheck.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.service.CCStatictisService;
import com.cableCheck.service.TeamCheckStatictisService;
import com.linePatrol.util.MapUtil;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/teamCheckStatictis")
@SuppressWarnings("all")
@Controller
public class TeamCheckStatictis  extends BaseAction{
	@Resource
	private TeamCheckStatictisService teamCheckStatictisService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/teamCheckStatictis/teamCheckStatictis", null);
	}
	
	@RequestMapping(value = "/statictis.do")
	public void listAll(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = teamCheckStatictisService.statictis(request, pager);
		write(response, map);
	}
}
