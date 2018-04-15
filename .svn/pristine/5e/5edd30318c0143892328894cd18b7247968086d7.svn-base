package icom.cableCheck.action;

import icom.cableCheck.service.CheckPortService;
import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.BaseAction;
/**
 * 查询设备的端子详细信息
 * 
 */
@Controller
@RequestMapping("mobile/cableCheck")
public class CheckPortController extends BaseAction {

	@Resource
	private CheckPortService checkPortService;

	@RequestMapping("/allPortsInEqp")
	public void getAllEquip(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Logger logger = Logger.getLogger(CheckPortController.class);
		
		String jsonStr=BaseServletTool.getParam(request);
		
//		String jsonStr = "{\"staffId\":\"6233\",\"sn\":\"123\",\"longitude\":\"118.774697\","
//				+ "\"latitude\":\"32.083346\",\"terminalType\":\"1\",\"queryType\":\"1\","
//				+ "\"eqpNo\":\"250BX.GHM00/GJ019\",\"operType\":\"1\",\"eqpId\":\"17350000029569\",\"taskId\":\"\",\"rwmxId\":\"\",\"currPage\":\"1\",\"pageSize\":\"20\"," +
//						"\"areaId\":\"60\",\"query_free_status\":\"\",\"query_change_status\":\"1\",\"orderBy\":\"0\"}";
		
//		String jsonStr = "{\"staffId\":\"6233\",\"sn\":\"123\",\"longitude\":\"118.774697\","
//			+ "\"latitude\":\"32.083346\",\"terminalType\":\"1\",\"queryType\":\"2\","
//			+ "\"eqpNo\":\"A02.GCH00/ODF01B-01\",\"operType\":\"0\",\"eqpId\":\"27800005542729\",\"taskId\":\"\",\"rwmxId\":\"\",\"currPage\":\"1\",\"pageSize\":\"20\"," +
//					"\"areaId\":\"84\",\"query_free_status\":\"2\",\"query_change_status\":\"2\",\"orderBy\":\"0\"}";
		logger.info("【查询设备的端子详细信息，传入参数】" + jsonStr);
		
		String result = checkPortService.getCheckPort(jsonStr);
		
		logger.info("【查询设备的详细信息，返回参数】" + result);
		BaseServletTool.sendParam(response, result);
	}
	
	//特地开发一个接口来查询17年新增光路
	@RequestMapping("/addPortsInEqp")
	public void addPortsInEqp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Logger logger = Logger.getLogger(CheckPortController.class);
		
		String jsonStr=BaseServletTool.getParam(request);
		
		logger.info("【查询设备的端子详细信息，传入参数】" + jsonStr);
		
		String result = checkPortService.addPortsInEqp(jsonStr);
		
		logger.info("【查询设备的详细信息，返回参数】" + result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getEqpCDInfo")
	public void getEqpCDInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Logger logger = Logger.getLogger(CheckPortController.class);
		
		String jsonStr=BaseServletTool.getParam(request);
//		String jsonStr = "{\"eqpId\":\"12310000006414\",\"areaId\":\"20\"}";
		logger.info("【查询设备的成端信息，传入参数】" + jsonStr);
		
		String result = checkPortService.getEqpCDInfo(jsonStr);
		
		logger.info("【查询设备的成端信息，返回参数】" + result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getPortInfos")
	public void getPortInfos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Logger logger = Logger.getLogger(CheckPortController.class);
		
		String jsonStr=BaseServletTool.getParam(request);
		//String jsonStr = "{\"SBID\":\"19800007691078\",\"DZID\":\"19800031248517\"}";
		logger.info("【查询端子具体信息，传入参数】" + jsonStr);
		
		String result = checkPortService.getPortInfos(jsonStr);
		
		logger.info("【查询端子具体信息，返回参数】" + result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getPortDetail")
	public void getPortDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Logger logger = Logger.getLogger(CheckPortController.class);
		
		String jsonStr=BaseServletTool.getParam(request);
		//String jsonStr = "{\"SBID\":\"19800007691078\",\"DZID\":\"19800031248517\"}";
		logger.info("【查询端子具体信息，传入参数】" + jsonStr);
		
		String result = checkPortService.getPortDetail(jsonStr);
		
		logger.info("【查询端子具体信息，返回参数】" + result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getEqpAddress")
	public void getEqpAddress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Logger logger = Logger.getLogger(CheckPortController.class);
		
		String jsonStr=BaseServletTool.getParam(request);
		//String jsonStr = "{\"SBID\":\"19800007691078\",\"DZID\":\"19800031248517\"}";
		logger.info("【查询端子具体信息，传入参数】" + jsonStr);
		
		String result = checkPortService.getEqpAddress(jsonStr);
		
		logger.info("【查询端子具体信息，返回参数】" + result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/qryFOptRoute")
	public void qryFOptRoute(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		String jsonStr = "{"+
//				"\"staffId\":\"3393\","+
//		        "\"terminalType\":\"0\","+
//				"\"sn\":\"1\","+
//				"\"areaId\":\"63\","+
//				"\"sonAreaId\":\"64\","+	
//				"\"selectType\":\"0\""+
//				"}";
		BaseServletTool.sendParam(response, checkPortService.getFOptRoute(jsonStr));
	}
	
	//查询空闲端口
	@RequestMapping("/queryFreePort")
	public void queryFreePort(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{"+
				"\"staffId\":\"3393\","+
		        "\"terminalType\":\"0\","+
				"\"sn\":\"1\","+
				"\"areaId\":\"3\","+
				"\"sonAreaId\":\"9\","+	
				"\"selectType\":\"0\""+
				"}";*/
		BaseServletTool.sendParam(response, checkPortService.queryFreePort(jsonStr));
	}
	//改变OSS端子
	@RequestMapping("/changePort")
	public void changePort(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{"+
				"\"staffId\":\"3393\","+
		        "\"terminalType\":\"0\","+
				"\"sn\":\"1\","+
				"\"areaId\":\"63\","+
				"\"sonAreaId\":\"64\","+	
				"\"selectType\":\"0\""+
				"}";*/
		BaseServletTool.sendParam(response, checkPortService.changePort(jsonStr));
	}
	
	 /**
     * 现场复查查看端子记录
     */
	@RequestMapping("/checkAgain")
	public void checkAgain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Logger logger = Logger.getLogger(CheckPortController.class);
		
		String jsonStr=BaseServletTool.getParam(request);

		logger.info("【查询端子具体信息，传入参数】" + jsonStr);
		
		String result = checkPortService.checkAgain(jsonStr);
		
		logger.info("【查询端子具体信息，返回参数】" + result);
		BaseServletTool.sendParam(response, result);
	}
	
	
	/**
     * 获取个人工单下的端子信息
     */
    @RequestMapping("/getPortByOrder.do")
  	public void getPortByOrder(HttpServletRequest request,HttpServletResponse response){
  		String jsonStr=BaseServletTool.getParam(request);
		Logger logger = Logger.getLogger(CheckPortController.class);
  		logger.info("【查询端子具体信息，传入参数】" + jsonStr);
  		String result=checkPortService.getPortByOrder(jsonStr);
  		logger.info("【查询端子具体信息，返回参数】" + result);
  		BaseServletTool.sendParam(response, result);
      }
    
    /**
     * 工单端子检查页面通过切换到设备端子检查页面--还没有完全开发好，暂时不用，沿用老的接口
     */
    @RequestMapping("/checkEqpPorts.do")
  	public void checkEqpPorts(HttpServletRequest request,HttpServletResponse response){
  		String jsonStr=BaseServletTool.getParam(request);
  		/*String jsonStr = "{"+
		"\"staffId\":\"3393\","+
        "\"terminalType\":\"0\","+
		"\"sn\":\"1\","+
		"\"areaId\":\"63\","+
		"\"sonAreaId\":\"64\","+	
		"\"selectType\":\"0\""+
		"}";*/
		Logger logger = Logger.getLogger(CheckPortController.class);
  		logger.info("【查询端子具体信息，传入参数】" + jsonStr);
  		String result=checkPortService.checkEqpPorts(jsonStr);
  		logger.info("【查询端子具体信息，返回参数】" + result);
  		BaseServletTool.sendParam(response, result);
    }
    
    
	
}
