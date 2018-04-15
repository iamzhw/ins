package icom.cableCheck.action;

import icom.cableCheck.service.CheckOrderService;
import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linePatrol.util.DateUtil;

import util.page.BaseAction;
@Controller
@RequestMapping("mobile/cableCheck")
public class CheckOrderController extends BaseAction{
	
	Logger logger = Logger.getLogger(CheckOrderController.class);
	
	@Resource
	private CheckOrderService checkOrderService;

	 /**
	  * TODO 需要添加字段
	  * 保存工单
	  * 
      */
    @RequestMapping("/saveGd")
    public void uploadPhoto(HttpServletRequest request, HttpServletResponse response)throws Exception {
    	
	   String jsonStr=BaseServletTool.getParam(request);
//String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"\",\"rwmxId\":\"\",\"eqpId\":\"25320000005615\",\"eqpNo\":\"250YH.XH000/GJ005\",\"eqpName\":\"省电信FTTH实训基地GJ001\",\"eqpAddress\":\"中国江苏省南京市雨花台区凤台南路283号\",\"port\":[{\"portId\":\"25300011078131\",\"portNo\":\"4/11\",\"portName\":\"\",\"photoId\":\"\",\"reason\":\"\",\"isCheckOK\":\"1\",\"ischooseok\":\"0\"}],\"photoId\":\"\",\"operType\":\"1\",\"data_source\":\"2\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.748491\",\"latitude\":\"31.986629\"}";
	 //  String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"10506\",\"rwmxId\":\"null\",\"eqpId\":\"25330000005463\",\"eqpNo\":\"250JY.SXMDJ/GF015\",\"eqpName\":\"水西门大街光分纤箱015二机床厂总机\",\"eqpAddress\":\"中国江苏省南京市雨花台区软件大道\",\"port\":[{\"portId\":\"25300000614450\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/1\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"},{\"portId\":\"25300000614451\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/2\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"},{\"portId\":\"25300000614452\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/3\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"}],\"photoId\":\"\",\"operType\":\"2\",\"data_source\":\"\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.751004\",\"latitude\":\"31.985896\"}";


	    logger.info("【生成工单，入参信息】"+jsonStr);
	    System.out.println(DateUtil.getDateAndTime()+":入参信息"+jsonStr);
		String result=checkOrderService.getOrder(jsonStr);
		
		logger.info("【生成工单，出参信息】"+result);
		System.out.println(DateUtil.getDateAndTime()+":出参信息"+result);
		BaseServletTool.sendParam(response, result);
    }
    
    /**
     * 退单接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/cancelBill")
    public void cancelBill(HttpServletRequest request, HttpServletResponse response)throws Exception {
    	
	    String jsonStr=BaseServletTool.getParam(request);
	    logger.info("【退单，入参信息：】"+jsonStr);
	    
		String result=checkOrderService.cancelBill(jsonStr);
		
		logger.info("【退单，出参信息：】"+result);
		
		BaseServletTool.sendParam(response, result);
    }
    
    /**
     * 转派接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/forwardBill.do")
	public void forwardBill(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);

	    logger.info("【转派工单，入参信息】"+jsonStr);
	    
		String result=checkOrderService.forwardBill(jsonStr);
		
		logger.info("【转派工单，出参信息】"+result);
		
		BaseServletTool.sendParam(response, result);
    }
    
    
    @RequestMapping("/partCommitGd.do")
	public void partCommitGd(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);

	    logger.info("【转派工单，入参信息】"+jsonStr);
	    
		String result=checkOrderService.partCommitGd(jsonStr);
		
		logger.info("【转派工单，出参信息】"+result);
		
		BaseServletTool.sendParam(response, result);
    }
    
    @RequestMapping("/queryWorkLoad.do")
    public void query(HttpServletRequest request, HttpServletResponse response)throws Exception {
    	
	    String jsonStr=BaseServletTool.getParam(request);
    	//String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"6285\"}";
	    logger.info("【当月完成情况，入参信息】"+jsonStr);
	    
		String result=checkOrderService.query(jsonStr);
		
		logger.info("【当月完成情况，出参信息】"+result);
		
		BaseServletTool.sendParam(response, result);
    }
    /**
     * 采集设备的经纬度
     */
    @RequestMapping("/getLongLati.do")
  	public void getLongLati(HttpServletRequest request,HttpServletResponse response){
  		String jsonStr=BaseServletTool.getParam(request);

  	    logger.info("【采集坐标，入参信息】"+jsonStr);
  	    
  		String result=checkOrderService.getLongLati(jsonStr);
  		
  		logger.info("【采集坐标，出参信息】"+result);
  		
  		BaseServletTool.sendParam(response, result);
      }
    
    
    
    /**
	  * TODO 需要添加字段
	  * 保存工单
	  * 
     */
   @RequestMapping("/saveJtGd")
   public void saveJtGd(HttpServletRequest request, HttpServletResponse response)throws Exception {
   	
	   String jsonStr=BaseServletTool.getParam(request);
//String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"\",\"rwmxId\":\"\",\"eqpId\":\"25320000005615\",\"eqpNo\":\"250YH.XH000/GJ005\",\"eqpName\":\"省电信FTTH实训基地GJ001\",\"eqpAddress\":\"中国江苏省南京市雨花台区凤台南路283号\",\"port\":[{\"portId\":\"25300011078131\",\"portNo\":\"4/11\",\"portName\":\"\",\"photoId\":\"\",\"reason\":\"\",\"isCheckOK\":\"1\",\"ischooseok\":\"0\"}],\"photoId\":\"\",\"operType\":\"1\",\"data_source\":\"2\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.748491\",\"latitude\":\"31.986629\"}";
	 //  String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"10506\",\"rwmxId\":\"null\",\"eqpId\":\"25330000005463\",\"eqpNo\":\"250JY.SXMDJ/GF015\",\"eqpName\":\"水西门大街光分纤箱015二机床厂总机\",\"eqpAddress\":\"中国江苏省南京市雨花台区软件大道\",\"port\":[{\"portId\":\"25300000614450\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/1\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"},{\"portId\":\"25300000614451\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/2\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"},{\"portId\":\"25300000614452\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/3\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"}],\"photoId\":\"\",\"operType\":\"2\",\"data_source\":\"\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.751004\",\"latitude\":\"31.985896\"}";


	    logger.info("【生成工单，入参信息】"+jsonStr);
	    
		String result=checkOrderService.saveJtGd(jsonStr);
		
		logger.info("【生成工单，出参信息】"+result);
		
		BaseServletTool.sendParam(response, result);
   }
   
    
    /**
     * 现场复查提交(不会生成任务)
     */
    @RequestMapping("/checkAgainSubmit.do")
  	public void checkAgainSubmit(HttpServletRequest request,HttpServletResponse response){
  		String jsonStr=BaseServletTool.getParam(request);

  	    logger.info("【采集坐标，入参信息】"+jsonStr);
  	    
  		String result=checkOrderService.checkAgainSubmit(jsonStr);
  		
  		logger.info("【采集坐标，出参信息】"+result);
  		
  		BaseServletTool.sendParam(response, result);
      }
    
 
    
    /**
     * 获取我的个人工单
     */
    @RequestMapping("/getUserOreder.do")
  	public void getUserOreder(HttpServletRequest request,HttpServletResponse response){
  		String jsonStr=BaseServletTool.getParam(request);

  	    logger.info("【获取我的个人工单，入参信息】"+jsonStr);
  	    
  		String result=checkOrderService.getUserOreder(jsonStr);
  		
  		logger.info("【获取我的个人工单，出参信息】"+result);
  		
  		BaseServletTool.sendParam(response, result);
      }
    /**
	  * TODO 需要添加字段
	  * 修改新的保存工单
	  * 
     */
   @RequestMapping("/commitCheckTask")
   public void commiteCheckTask(HttpServletRequest request, HttpServletResponse response)throws Exception {
   	
	   String jsonStr=BaseServletTool.getParam(request);
//String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"\",\"rwmxId\":\"\",\"eqpId\":\"25320000005615\",\"eqpNo\":\"250YH.XH000/GJ005\",\"eqpName\":\"省电信FTTH实训基地GJ001\",\"eqpAddress\":\"中国江苏省南京市雨花台区凤台南路283号\",\"port\":[{\"portId\":\"25300011078131\",\"portNo\":\"4/11\",\"portName\":\"\",\"photoId\":\"\",\"reason\":\"\",\"isCheckOK\":\"1\",\"ischooseok\":\"0\"}],\"photoId\":\"\",\"operType\":\"1\",\"data_source\":\"2\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.748491\",\"latitude\":\"31.986629\"}";
	 //  String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"10506\",\"rwmxId\":\"null\",\"eqpId\":\"25330000005463\",\"eqpNo\":\"250JY.SXMDJ/GF015\",\"eqpName\":\"水西门大街光分纤箱015二机床厂总机\",\"eqpAddress\":\"中国江苏省南京市雨花台区软件大道\",\"port\":[{\"portId\":\"25300000614450\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/1\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"},{\"portId\":\"25300000614451\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/2\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"},{\"portId\":\"25300000614452\",\"portNo\":\"250JY.SXMDJ/GF015/1/1/3\",\"portName\":\"\",\"reason\":\"\",\"isCheckOK\":\"0\"}],\"photoId\":\"\",\"operType\":\"2\",\"data_source\":\"\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.751004\",\"latitude\":\"31.985896\"}";


	    logger.info("【生成工单，入参信息】"+jsonStr);
	    System.out.println(DateUtil.getDateAndTime()+":入参信息"+jsonStr);
		String result=checkOrderService.commitCheckTask(jsonStr);
		
		logger.info("【生成工单，出参信息】"+result);
		System.out.println(DateUtil.getDateAndTime()+":出参信息"+result);
		BaseServletTool.sendParam(response, result);
   }
   
   /**
    * 整改工单回单接口-流程改造
    */
   @RequestMapping("/receiptReformTask")
   public void receiptReformTask(HttpServletRequest request, HttpServletResponse response)throws Exception {
   	
	    String jsonStr=BaseServletTool.getParam(request);
	   //String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"\",\"rwmxId\":\"\",\"eqpId\":\"25320000005615\",\"eqpNo\":\"250YH.XH000/GJ005\",\"eqpName\":\"省电信FTTH实训基地GJ001\",\"eqpAddress\":\"中国江苏省南京市雨花台区凤台南路283号\",\"port\":[{\"portId\":\"25300011078131\",\"portNo\":\"4/11\",\"portName\":\"\",\"photoId\":\"\",\"reason\":\"\",\"isCheckOK\":\"1\",\"ischooseok\":\"0\"}],\"photoId\":\"\",\"operType\":\"1\",\"data_source\":\"2\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.748491\",\"latitude\":\"31.986629\"}";

	    logger.info("【生成工单，入参信息】"+jsonStr);
	    System.out.println(DateUtil.getDateAndTime()+":入参信息"+jsonStr);
		String result=checkOrderService.receiptReformTask(jsonStr);
		
		logger.info("【生成工单，出参信息】"+result);
		System.out.println(DateUtil.getDateAndTime()+":出参信息"+result);
		BaseServletTool.sendParam(response, result);
   }
    
   /**
    * 检查任务工单提交--代办
    */
   @RequestMapping("/submitCheckOrder")
   public void submitCheckOrder(HttpServletRequest request, HttpServletResponse response)throws Exception {
   	
	    String jsonStr=BaseServletTool.getParam(request);
	   //String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"\",\"rwmxId\":\"\",\"eqpId\":\"25320000005615\",\"eqpNo\":\"250YH.XH000/GJ005\",\"eqpName\":\"省电信FTTH实训基地GJ001\",\"eqpAddress\":\"中国江苏省南京市雨花台区凤台南路283号\",\"port\":[{\"portId\":\"25300011078131\",\"portNo\":\"4/11\",\"portName\":\"\",\"photoId\":\"\",\"reason\":\"\",\"isCheckOK\":\"1\",\"ischooseok\":\"0\"}],\"photoId\":\"\",\"operType\":\"1\",\"data_source\":\"2\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.748491\",\"latitude\":\"31.986629\"}";

	    logger.info("【生成工单，入参信息】"+jsonStr);
	    System.out.println(DateUtil.getDateAndTime()+":submitCheckOrder 入参信息"+jsonStr);
		String result=checkOrderService.submitCheckOrder(jsonStr);
		
		logger.info("【生成工单，出参信息】"+result);
		System.out.println(DateUtil.getDateAndTime()+":出参信息"+result);
		BaseServletTool.sendParam(response, result);
   }
   
   /**
    * 检查任务工单提交--不预告
    */
   /*@RequestMapping("/submitWorkOrder2")
   public void submitWorkOrder(HttpServletRequest request, HttpServletResponse response)throws Exception {
   	
	    String jsonStr=BaseServletTool.getParam(request);
	   //String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"\",\"rwmxId\":\"\",\"eqpId\":\"25320000005615\",\"eqpNo\":\"250YH.XH000/GJ005\",\"eqpName\":\"省电信FTTH实训基地GJ001\",\"eqpAddress\":\"中国江苏省南京市雨花台区凤台南路283号\",\"port\":[{\"portId\":\"25300011078131\",\"portNo\":\"4/11\",\"portName\":\"\",\"photoId\":\"\",\"reason\":\"\",\"isCheckOK\":\"1\",\"ischooseok\":\"0\"}],\"photoId\":\"\",\"operType\":\"1\",\"data_source\":\"2\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.748491\",\"latitude\":\"31.986629\"}";

	    logger.info("【生成工单，入参信息】"+jsonStr);
	    System.out.println(DateUtil.getDateAndTime()+":入参信息"+jsonStr);
		String result=checkOrderService.submitWorkOrder(jsonStr);
		
		logger.info("【生成工单，出参信息】"+result);
		System.out.println(DateUtil.getDateAndTime()+":出参信息"+result);
		BaseServletTool.sendParam(response, result);
   }*/
   
   /**
    * 检查任务工单提交--一键反馈、不预告  代码优化后
    */
   @RequestMapping("/submitWorkOrder")
   public void submitWorkOrder2(HttpServletRequest request, HttpServletResponse response)throws Exception {
   	
	    String jsonStr=BaseServletTool.getParam(request);
	   //String jsonStr= "{\"terminalType\":\"1\",\"sn\":\"868013020713586\",\"staffId\":\"15339\",\"taskId\":\"\",\"rwmxId\":\"\",\"eqpId\":\"25320000005615\",\"eqpNo\":\"250YH.XH000/GJ005\",\"eqpName\":\"省电信FTTH实训基地GJ001\",\"eqpAddress\":\"中国江苏省南京市雨花台区凤台南路283号\",\"port\":[{\"portId\":\"25300011078131\",\"portNo\":\"4/11\",\"portName\":\"\",\"photoId\":\"\",\"reason\":\"\",\"isCheckOK\":\"1\",\"ischooseok\":\"0\"}],\"photoId\":\"\",\"operType\":\"1\",\"data_source\":\"2\",\"is_bill\":\"0\",\"remarks\":\"\",\"comments\":\"\",\"info\":\"\",\"longitude\":\"118.748491\",\"latitude\":\"31.986629\"}";

	    logger.info("【生成工单，入参信息】"+jsonStr);
	    System.out.println(DateUtil.getDateAndTime()+":submitWorkOrder 入参信息"+jsonStr);
		String result=checkOrderService.submitWorkOrder2(jsonStr);
		
		logger.info("【生成工单，出参信息】"+result);
		System.out.println(DateUtil.getDateAndTime()+":出参信息"+result);
		BaseServletTool.sendParam(response, result);
   }
    
    
    
}
