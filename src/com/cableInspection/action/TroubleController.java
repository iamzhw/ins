package com.cableInspection.action;

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

import com.cableInspection.service.TroubleService;

@SuppressWarnings("all")
@Controller
@RequestMapping("Trouble")
public class TroubleController extends BaseAction{
	
	@Resource
	private TroubleService troubleService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> rs = new HashMap<String, Object>();
		return new ModelAndView("cableinspection/trouble/trouble_index", rs);
	}
	
	@RequestMapping("query")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = troubleService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping("check")
	public void check(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Boolean isSuccess = troubleService.check(request);
		Map map = new HashMap();
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
}
