package icom.cableCheck.action;

import icom.cableCheck.service.CheckSpecialService;
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
public class CheckSpecialController extends BaseAction{

	Logger logger = Logger.getLogger(CheckSpecialController.class);
	
	@Resource
	private CheckSpecialService checkSpecialService;
	private CheckTaskService checkTaskService;
	
	@RequestMapping("/querySpecialEqp")
	public void getFieldEqp(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    String jsonStr=BaseServletTool.getParam(request);
/*		String jsonStr = "{\"staffId\":\"6233\",\"sn\":\"869340026272963\",\"longitude\":\"119.59518\"," +
				"\"latitude\":\"31.991314\",\"terminalType\":\"1\",\"queryType\":\"1\"," +
				"\"eqpNo\":\"A02.GCH00/ODF01B-01\",\"operType\":\"0\",\"currPage\":\"1\",\"pageSize\":\"20\"," +
				"\"eqpType\":\"\",\"distance\":\"1\",\"areaId\":\"\",\"orderByEqp\":\"3\",\"manageArea\":\"\"}";*/
		/*String jsonStr = "{\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"terminalType\":1," +
				"\"longitude\":\"118.748487\",\"latitude\":\"31.986607\",\"operType\":\"1\",\"queryType\":\"0\"," +
				"\"eqpNo\":\"TX.ZXJ/GJ011\",\"eqpType\":\"703\",\"distance\":\"1\",\"areaId\":\"79\",\"orderByEqp\":\"0\"," +
						"\"manageArea\":\"\",\"currPage\":\"1\",\"pageSize\":20}";*/
	    //String jsonStr =  "{\"sn\":\"860954039321481\",\"staffId\":\"20143\",\"terminalType\":1,\"longitude\":\"118.748506\",\"latitude\":\"31.986663\",\"operType\":\"2\",\"queryType\":\"2\",\"eqpNo\":\"\",\"eqpType\":\"\",\"eqpName\":\"\",\"distance\":\"2\",\"areaId\":\"79\",\"orderByEqp\":\"0\",\"manageArea\":\"\",\"currPage\":\"1\",\"pageSize\":\"20\"}";
	    
	    logger.info("【获取设备坐标，入参信息】"+jsonStr);
			
		String result=checkSpecialService.getSpecialEqp(jsonStr);

		logger.info("【获取设备坐标，出参信息】"+result);
		
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getPort.do")
	public void getPort(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		logger.info("【查看端子整改信息，传入参数】"+jsonStr);
		
		String result = checkSpecialService.getPort(jsonStr);
		
		logger.info("【查看端子整改信息，返回参数】"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	
	@RequestMapping("/getDelLink.do")
	public void getDelLink(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		logger.info("【查看端子整改信息，传入参数】"+jsonStr);
		
		String result = checkSpecialService.getDelLink(jsonStr);
		
		logger.info("【查看拆机光路信息，返回参数】"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getDelEqp.do")
	public void getDelEqp(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		logger.info("【查看端子整改信息，传入参数】"+jsonStr);
		
		String result = checkSpecialService.getDelEqp(jsonStr);
		
		logger.info("【查看拆机设备信息，返回参数】"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	
	@RequestMapping("/getKxOrder.do")
	public void getKxEqp(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		logger.info("【查看端子整改信息，传入参数】"+jsonStr);
		
		String result = checkSpecialService.getKxOrder(jsonStr);
		
		logger.info("【查看客响工单信息，返回参数】"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getKxOrderDetail.do")
	public void getKxOrderDetail(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		logger.info("【查看端子整改信息，传入参数】"+jsonStr);
		
		String result = checkSpecialService.getKxOrderDetail(jsonStr);
		
		logger.info("【查看客响工单详细信息，返回参数】"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getKxOrderEqpInfo.do")
	public void getKxOrderEqpInfo(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		logger.info("【查看端子整改信息，传入参数】"+jsonStr);
		
		String result = checkSpecialService.getKxOrderEqpInfo(jsonStr);
		
		logger.info("【查看客响工单详细信息，返回参数】"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getSonarea.do")
	public void getSonarea(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		logger.info("【查看端子整改信息，传入参数】"+jsonStr);
		
		String result = checkSpecialService.getSonarea(jsonStr);
		
		logger.info("【查看拆机设备信息，返回参数】"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	
		@RequestMapping("/SubmitKxContent.do")
		public void submitKxContent(HttpServletRequest request,HttpServletResponse response){
			String jsonStr=BaseServletTool.getParam(request);
//			logger.info("【查看端子整改信息，传入参数】"+jsonStr);
			
			String result = checkSpecialService.submitKxContent(jsonStr);
			
			logger.info("【查看客响信息，返回参数】"+result);
			BaseServletTool.sendParam(response, result);
		}
	
	
}
