package icom.cableCheck.action;

import icom.cableCheck.service.CheckBanzuService;
import icom.cableCheck.service.CheckTaskService;
import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.BaseAction;

@Controller
@RequestMapping("mobile/cableCheck")
public class CheckBanzuController extends BaseAction{

	Logger logger = Logger.getLogger(CheckBanzuController.class);
	
	@Resource
	private CheckBanzuService checkBanzuService;
	
	@RequestMapping("/getBanzuByAraeId")
	public void getBanzuByAraeId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String jsonStr=BaseServletTool.getParam(request);
		logger.info("【获取班组信息，入参信息】"+jsonStr);
		
		String result=checkBanzuService.getBanzuByAreaId(jsonStr);

		logger.info("【获取班组信息，出参信息】"+result);
		
		BaseServletTool.sendParam(response, result);
	}
	
	
	@RequestMapping("/getStaffByTeamId")
	public void getStaffByTeamId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String jsonStr=BaseServletTool.getParam(request);
		logger.info("【获取班组人员信息，入参信息】"+jsonStr);
		
		String result=checkBanzuService.getStaffByTeamId(jsonStr);

		logger.info("【获取班组人员信息，出参信息】"+result);
		
		BaseServletTool.sendParam(response, result);
	}
	
}
