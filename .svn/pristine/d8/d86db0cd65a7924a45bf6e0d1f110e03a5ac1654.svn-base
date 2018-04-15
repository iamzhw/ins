package com.cableCheck.action;

import icom.util.BaseServletTool;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.service.ExcelTeamService;
import com.cableCheck.service.MainTainCompanyService;
import com.cableCheck.service.TaskMangerService;
import com.cableCheck.service.TeamManagerService;
import com.cableCheck.service.TeamUserService;
import com.cableCheck.service.WrongPortReportService;
import com.linePatrol.util.MapUtil;
import com.system.model.ZTreeNode;
import com.system.service.GeneralService;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/teamUser")
@SuppressWarnings("all")
@Controller
public class TeamUserController extends BaseAction{


	@Resource
	public TeamUserService teamUserService;
	
	
	/**
	 * 跳转到装维班组人员界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response){
		String team_id = request.getParameter("TEAM_ID");
		Map map = new HashMap();
		map.put("team_id", team_id);
		return new ModelAndView("cablecheck/teamUser/teamUser", map);
	}
	
	@RequestMapping(value = "/listAll.do")
	public void listAll(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = teamUserService.listUser(request, pager);
		write(response, map);
	}
}
