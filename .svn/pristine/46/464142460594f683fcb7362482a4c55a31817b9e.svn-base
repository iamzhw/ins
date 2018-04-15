package icom.cableCheck.action;

import icom.cableCheck.service.CheckTaskProcessService;
import icom.util.BaseServletTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.BaseAction;

/** 
 * @author wangxy
 * @version 创建时间：2016年7月27日 下午5:23:08 
 * 类说明 
 */
@RequestMapping("mobile/cableCheck")
@Controller
public class CheckTaskProcessController extends BaseAction {

	Logger logger = Logger.getLogger(CheckTaskProcessController.class);
	@Autowired
	private CheckTaskProcessService checkProcessService;
	
	@RequestMapping("/getProcessInfo")
	public void getProcessInfo(HttpServletRequest request, HttpServletResponse response) {
		
//		String jsonStr = "{\"taskId\":\"10377\",\"staffId\"=\"\",\"terminalType\"=\"\",\"sn\"=\"\"}";
		String jsonStr = BaseServletTool.getParam(request);
		logger.info("【查看流程信息，传入参数】"+jsonStr);
		
		String result = checkProcessService.getProcess(jsonStr);
		
		BaseServletTool.sendParam(response, result);
		logger.info("【查看流程信息，返回参数】"+result);
		
		
		
	}
	
}
