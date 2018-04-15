package icom.cableCheck.action;

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
public class CheckTaskController extends BaseAction{

	Logger logger = Logger.getLogger(CheckTaskController.class);
	
	@Resource
	private CheckTaskService checkTaskService;
	
	@RequestMapping("/queryEqp")
	public void getAllEquip(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
		
		String result=checkTaskService.getCheckEqp(jsonStr);

		logger.info("【获取设备坐标，出参信息】"+result);
		
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/selectStaff.do")
	public void selectStaff(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
//		String jsonStr = "{"+
//				"\"staffId\":\"3393\","+
//		        "\"terminalType\":\"0\","+
//				"\"sn\":\"1\","+
//				"\"areaId\":\"63\","+
//				"\"sonAreaId\":\"64\","+
//				"\"selectType\":\"0\""+
//				"}";
		BaseServletTool.sendParam(response, checkTaskService.selectStaff(jsonStr));
	}
	
	/**
	 * 查询当前位置的网格单元（集团对标）
	 * 
	 */
	@RequestMapping("/selectGrid.do")
	public void selectGrid(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.selectGrid(jsonStr));
	}
	
	/**
	 * 归属网络设施展示（集团对标）
	 * 
	 */
	@RequestMapping("/selectGridQum")
	public void selectGridQum(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.selectGridQum(jsonStr));
	}
	
	/**
	 * 获取网格设备所属工单（集团对标）
	 * 
	 */
	@RequestMapping("/getEquWorkOrder")
	public void getEquWorkOrder(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.getEquWorkOrder(jsonStr));
	}
	/**
	 * 点击工单检查按钮，显示设备端子信息（集团对标）
	 * 
	 */
	@RequestMapping("/getEquWorkOrderList")
	public void getEquWorkOrderList(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		//BaseServletTool.sendParam(response, checkTaskService.getEquWorkOrderList(jsonStr));
		BaseServletTool.sendParam(response, checkTaskService.getEquWorkList(jsonStr));
	}
	
	
	/**查询出设备下的所有端子
	 * 
	 * 工单端子信息页面-正确端子-使用下拉框展示，端子编码让检查员自己选择	
	 */
	@RequestMapping("/getPortsByAreaEqu")
	public void getPortsByAreaEqu(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.getPortsByAreaEqu(jsonStr));
	}
	/**
	 * 工单端子信息检查提交（集团对标）
	 * 
	 */
	@RequestMapping("/submitWorkOrderPort")
	public void submitWorkOrderPort(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.submitWorkOrderPort(jsonStr));
	}
	
	/**
	 * 查询工单详细信息（集团对标）
	 * 
	 */
	@RequestMapping("/selectWorkOrderList")
	public void selectWorkOrderList(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.selectWorkOrderList(jsonStr));
	}
	
	/**
	 * 作业计划实施-现场资源查询（集团对标）
	 * 
	 */
	@RequestMapping("/selectResourcesCheck")
	public void selectResourcesCheck(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.selectResourcesCheck(jsonStr));
	}
	
	
	/**
	 * 作业计划实施-工单查询（集团对标）
	 * 
	 */
	@RequestMapping("/getWPWorkOrder")
	public void getWPWorkOrder(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.getWPWorkOrder(jsonStr));
	}
	
	/**
	 * 作业计划实施-工单详细信息查询（集团对标）
	 * 
	 */
	@RequestMapping("/selectWPWorkOrderList")
	public void selectWPWorkOrderList(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.selectWPWorkOrderList(jsonStr));
	}
	
	
	/**
	 * 已完成的检查任务，将工作量推送给集约化平台(线路工单触发接口)
	 * 
	 */
	@RequestMapping("/outSysDispatchTask")
	public void outSysDispatchTask(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.outSysDispatchTask(jsonStr));
	}
	
	/**
	 * 隐患整治工单接口
	 * 
	 */
	@RequestMapping("/dangerRemediationOder")
	public void dangerRemediationOder(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.dangerRemediationOder(jsonStr));
	}
	
	/**
	 * 隐患整治工单审核结果接口
	 * 
	 */
	@RequestMapping("/outSysAppOperating")
	public void outSysAppOperating(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.outSysAppOperating(jsonStr));
	}
	
	
	
	/**
	 * 作业计划实施-错误数据修改业务单（集团对标）
	 * 
	 */
	@RequestMapping("/ErrorWorkOrder")
	public void ErrorWorkOrder(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.ErrorWorkOrder(jsonStr));
	}
	
	
	/**
	 * 光网助手系统生成检查任务，主业人员检查(承包人自查)
	 * 
	 */
	@RequestMapping("/TaskCheck")
	public void TaskCheck(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.TaskCheck(jsonStr));
	}
	
	
	/**
	 * 作业计划实施-修改信息校验（集团对标）
	 * 
	 */
	@RequestMapping("/updatecheck")
	public void updatecheck(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.updatecheck(jsonStr));
	}
	
	/**
	 * 作业计划实施-修改痕迹记录（集团对标）
	 * 
	 */
	@RequestMapping("/modifytraces")
	public void modifytraces(HttpServletRequest request,HttpServletResponse response){
	//	String jsonStr=BaseServletTool.getParam(request);
		String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";
		BaseServletTool.sendParam(response, checkTaskService.modifytraces(jsonStr));
	}
	
	/**
	 * 集约化工单列表
	 * 
	 */
	@RequestMapping("/intensificationworkoder")
	public void intensificationworkoder(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.intensificationworkoder(jsonStr));
	}
	
	/**
	 * 集约化工单详情
	 * 
	 */
	@RequestMapping("/workoderdetail")
	public void workoderdetail(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.workoderdetail(jsonStr));
	}
	
	
	/**
	 * 集约化工单查询已打分项
	 * 
	 */
	@RequestMapping("/workoderScoredetail")
	public void workoderScoredetail(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		BaseServletTool.sendParam(response, checkTaskService.workoderScoredetail(jsonStr));
	}
	
	
	/**
	 * 集约化工单检查结果接口
	 * 
	 */
	@RequestMapping("/workOderResult")
	public void workOderResult_new(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.workOderResult(jsonStr));
	}
	
	
	
	/**
	 * 集约化工单检查结果接口
	 * 
	 */
	@RequestMapping("/workOderResult_new")
	public void workOderResult(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.workOderResult_new(jsonStr));
	}
	
	/**
	 * 	错误信息记录(集团对标)
	 */
	@RequestMapping("/saveError")
	public void saveError(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.saveError(jsonStr));
	}
	
	
	/**
	 * 查询网格工单数量，并排序（集团对标）
	 * 
	 */
	@RequestMapping("/getOrderNumByGrid.do")
	public void getOrderNumByGrid(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.getOrderNumByGrid(jsonStr));
	}
	
	/**
	 * 	作业计划回单(集团对标)
	 */
	@RequestMapping("/workPlanBackOder")
	public void workPlanBackOder(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
	/*	String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.workPlanBackOder(jsonStr));
	}
	
	/**
	 * 	作业计划实施-错误数据修改业务单(集团对标)
	 */
	@RequestMapping("/errorDataOder")
	public void errorDataOder(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.errorDataOder(jsonStr));
	}
	
	/**
	 * 	作业计划实施-错误信息修改(集团对标)
	 */
	@RequestMapping("/errorDataModify")
	public void errorDataModify(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		/*String jsonStr = "{\"longitude\":\"118.748524\"," +
		"\"latitude\":\"31.986692\",\"distance\":\"0.5\"}";*/
		BaseServletTool.sendParam(response, checkTaskService.errorDataModify(jsonStr));
	}
	
	
	/**
	 * 获取网格设备所对应的工单
	 */
	@RequestMapping("/getEquCSVIOMOrder")
	public void getEquCSVIOMOrder(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		BaseServletTool.sendParam(response, checkTaskService.getEquCSVIOMOrder(jsonStr));
	}
	
	
	/**
	 * 获取设备下的端子信息
	 */
	@RequestMapping("/getPortsByEqu")
	public void getPortsByEqu(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		BaseServletTool.sendParam(response, checkTaskService.getPortsByEqu(jsonStr));
	}
	
	/**
	 * 获取工单设备
	 */
	@RequestMapping("/getEqpBySG")
	public void getEqpBySG(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		BaseServletTool.sendParam(response, checkTaskService.getEqpBySG(jsonStr));
	}
	
	/**
	 * 点击工单检查时间，显示这条工单的全程操作记录
	 */
	@RequestMapping("/getProcessRecord")
	public void getProcessRecord(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		BaseServletTool.sendParam(response, checkTaskService.getProcessRecord(jsonStr));
	}
	
	/**
	 * 查询设备详情
	 */
	@RequestMapping("/getEqpDetail")
	public void getEqpDetail(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=BaseServletTool.getParam(request);
		BaseServletTool.sendParam(response, checkTaskService.getEqpDetail(jsonStr));
	}
	
}
