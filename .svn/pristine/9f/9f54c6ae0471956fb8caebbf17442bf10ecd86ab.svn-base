package icom.cableCheck.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import icom.cableCheck.service.CheckReformOrderService;
import icom.util.BaseServletTool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.BaseAction;
/**
 * 查看整改工单信息
 * 
 * @author wangxiangyu
 *
 */
@RequestMapping("mobile/cableCheck")
@Controller
public class CheckReformOrderController extends BaseAction {

	Logger logger = Logger.getLogger(CheckReformOrderController.class);
	
	@Autowired
	private CheckReformOrderService checkReformOrderService;
	
	/**
	 * 查看整改工单
	 */
	@RequestMapping("/getBillInfo")
	public void getReformOrder(HttpServletRequest request, HttpServletResponse response) {
		
		String jsonStr = BaseServletTool.getParam(request);
//		String jsonStr = "{\"staffId\":\"15339\",\"terminalType\":\"1\",\"sn\":\"869340026272963\",\"longitude\":\"118.791023\",\"latitude\":\"32.085116\","
//				+ "\"taskId\":\"10089\",\"rwmxId\":\"\",\"areaId\":\"\"}";
		
		logger.info("【查看整改工单，传入参数】"+jsonStr);
		
		String result = checkReformOrderService.getReformOrder(jsonStr);
		
		logger.info("【查看整改工单，返回参数】"+result);
		
		BaseServletTool.sendParam(response, result);
		
		
		
	}
	
	@RequestMapping("/getDzInfo")
	public void getReformDzOrder(HttpServletRequest request, HttpServletResponse response) {
		
		String jsonStr = BaseServletTool.getParam(request);
	//String jsonStr = "{\"taskId\":\"34079\",\"sn\":\"990004489665681\",\"staffId\":\"6285\",\"terminalType\":\"1\",\"longitude\":\"118.748474\",\"latitude\":\"31.986622\",\"rwmxId\":\"null\",\"areaId\":\"3\", \"portId\": \"25300000456764\"}";
		
		logger.info("【查看端子整改信息，传入参数】"+jsonStr);
		
		String result = checkReformOrderService.getReformDzOrder(jsonStr);
		
		logger.info("【查看端子整改信息，返回参数】"+result);
		
		BaseServletTool.sendParam(response, result);
}
}
