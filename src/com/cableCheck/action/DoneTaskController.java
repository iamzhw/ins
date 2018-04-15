package com.cableCheck.action;

import icom.util.BaseServletTool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.service.DoneTaskService;

/** 
 * @author wangxy
 * @version 创建时间：2016年7月26日 下午4:05:05 
 * 类说明 :已办任务
 */
@RequestMapping(value = "/DoneTask")
@SuppressWarnings("all")
@Controller
public class DoneTaskController extends BaseAction{

	Logger logger = Logger.getLogger(DoneTaskController.class);
	
	@Autowired
	private DoneTaskService doneTaskService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("cablecheck/DoneTask-index", null);
	}
	
	@RequestMapping(value="/queryDoneTask.do")
	public void queryDoneTask(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = doneTaskService.queryDoneTask(request, pager);
		write(response, map);
	}
	/**
	 * 进入人员列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectStaff.do")
	public ModelAndView selectStaff(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskIds", request.getParameter("taskIds"));
		map.put("area", request.getParameter("area"));
		map.put("son_area", request.getParameter("son_area"));
		return new ModelAndView("cablecheck/DoneTaskStaff", map);
	}
	
	/**
	 * 查询人员列表
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryHandler.do")
	public void queryHandler(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = doneTaskService.queryHandler(request, pager);
		write(response, map);
	}
	
	
	/**
	 * 派发任务
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/saveTask.do")
	public void saveTask(HttpServletRequest request, HttpServletResponse response) {
		String result = doneTaskService.saveTask(request);
		BaseServletTool.sendParam(response, result);
	}
	/**
	 * 跳转查看详情页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoShowEquip.do")
	public ModelAndView intoShowEquip(HttpServletRequest request, HttpServletResponse response){
		String taskId = request.getParameter("TASK_ID");
		String taskType = doneTaskService.getTaskByTaskId(taskId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		 if("0".equals(taskType)||"4".equals(taskType)){
			 //0周期性检查,4二次检查
			 result = doneTaskService.getMyTaskEqpPhotoForZq(request);
			 //return new ModelAndView("cablecheck/TaskDetail-done",result);
			 return new ModelAndView("cablecheck/TaskDetailNew-done",result);
		 }else{
			 //问题上报
			 result = doneTaskService.getMyTaskEqpPhoto(request);
			 //return new ModelAndView("cablecheck/EquipDetail-done",result);
			 return new ModelAndView("cablecheck/EquipDetailNew-done",result);
		 }
	}
	
	@RequestMapping(value="/exportExcel.do")
	public String exportExcel(HttpServletRequest request,ModelMap mm){
		List<Map<String, Object>> exportInfoList = doneTaskService.exportExcel(request);
		
		StringBuffer colsStr = new StringBuffer();
		colsStr.append("AREA_ID,SON_AREA_ID,ZHHWHWG,TASK_NAME,TASK_TYPE,STATUS_ID,INSPECTOR,MAINTOR,AUDITOR,PORT_NO,XZ,GDJGSJ,INSTALL_CODE,RES_TYPE,EQUIPMENT_NAME,EQUIPMENT_CODE,EQP_NAME,");//检查任务
		colsStr.append("DESCRIPT,REMARK,INFO,SFZG,START_TIME,COMPLETE_TIME,CHECK_COMPLETE_TIME,LAST_UPDATE_TIME,GLBH,GLMC,GLBM1,GLMC1,ISCHECKOK,PHOTO_PATH");//设备信息

		//colsStr.append("EQP_NO,EQP_NAME,EQPADDRESS,DESCRIPT,");//设备信息
		//colsStr.append("DZBM,GLBH,GLMC,");//端子信息
		//colsStr.append("IS_CHECK_OK,IS_REFORM");//是否合格，是否整改
		String[] cols = colsStr.toString().split(",");
		
		StringBuffer colsNameStr = new StringBuffer();
		colsNameStr.append("地区,区域,综合化维护网格,任务名称,任务来源,任务状态,检查员,维护员,审核员,端子编码,光路性质,工单竣工时间,所属箱子设备编码,设备类型,箱子名称,所属设备设备编码,设备名称,");//检查任务
		colsNameStr.append("问题描述,现场规范,回单备注,是否已整改,开始时间,结束时间,检查完成时间,上次更新时间,最近变动光路编号,最近变动光路名称,提交时光路编号,提交时光路名称,是否合格,照片地址");

		//colsNameStr.append("设备编号,设备名称,设备地址,问题描述,");//设备信息
		//colsNameStr.append("端子编号,光路编号,光路名称,");//端子信息
		//colsNameStr.append("是否合格,是否整改");
		String[] colsName = colsNameStr.toString().split(",");
		
		mm.addAttribute("name", "检查任务按端子");
		mm.addAttribute("cols", cols);
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", exportInfoList);	
		return "dataListExcel";
	}
	
	@RequestMapping(value="/exportExcelByPOrder.do")
	public String exportExcelByPOrder(HttpServletRequest request,ModelMap mm){
		List<Map<String, Object>> exportInfoList = doneTaskService.exportExcelByPOrder(request);
		
		StringBuffer colsStr = new StringBuffer();
		colsStr.append("NAME,SONNAME,GRID_NAME,TASK_NO,TASK_NAME,TASKTYPE,STATUS,INSPECTOR,MAINTOR,AUDITOR,EQUIPMENT_CODE,EQUIPMENT_NAME,RES_TYPE,ADDRESS,EQP_NO,EQP_NAME,PORT_NO,GLBM,RECORD_TYPE,ISCHECKOK,DESCRIPT,ORDER_NO,ACTION_TYPE,ARCHIVE_TIME,COMPANY_NAME,TEAM_NAME,REMARK,");//检查任务
		colsStr.append("PORT_INFO,INFO,START_TIME,ACTUAL_COMPLETE_TIME,P_PATH");//设备信息

		//colsStr.append("EQP_NO,EQP_NAME,EQPADDRESS,DESCRIPT,");//设备信息
		//colsStr.append("DZBM,GLBH,GLMC,");//端子信息
		//colsStr.append("IS_CHECK_OK,IS_REFORM");//是否合格，是否整改
		String[] cols = colsStr.toString().split(",");
		
		StringBuffer colsNameStr = new StringBuffer();
		colsNameStr.append("地市,区域,综合化维护网格,任务编码,任务名称,任务来源,任务状态,检查员,维护员,审核员,箱子编码,箱子名称,箱子类型,箱子地址,设备编码,设备名称,端子编码,光路编码,记录类型,是否合格,检查描述,工单号,工单类型,工单竣工时间,代维公司,代维班组,现场规范,");//检查任务
		colsNameStr.append("整改描述,回单备注,开始时间,检查完成时间,照片地址");

		//colsNameStr.append("设备编号,设备名称,设备地址,问题描述,");//设备信息
		//colsNameStr.append("端子编号,光路编号,光路名称,");//端子信息
		//colsNameStr.append("是否合格,是否整改");
		String[] colsName = colsNameStr.toString().split(",");
		
		mm.addAttribute("name", "检查任务按工单");
		mm.addAttribute("cols", cols);
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", exportInfoList);	
		return "dataListExcel";
	}
	
	
	@RequestMapping(value="/exportExcelByEqp.do")
	public String exportExcelByEqp(HttpServletRequest request, ModelMap mm){
		List<Map<String, Object>> exportInfoList = doneTaskService.exportExcelByEqp(request);

		StringBuffer colsStr = new StringBuffer();
		colsStr.append("STATUS_ID,AREA_ID,SON_AREA_ID,TASK_NAME,EQUIPMENT_CODE,RES_TYPE,ZHHWHWG,TASK_TYPE,BEFORE_CHECK,CHECKED,ERROE_CHECK,CHECKED_ERROE_CHECK,INSPECTOR,MAINTOR,AUDITOR,REMARK,SFZG,START_TIME,COMPLETE_TIME,LAST_UPDATE_TIME,CHECK_COMPLETE_TIME");//检查任务
		//colsStr.append("OPER_STAFF,OPER_TIME,ACTUAL_COMPLETE_TIME,CHECK_TIME,ENABLE,EQP_ID,EQP_NO,EQP_NAME,");//设备信息
		//colsStr.append("PORT_ID,PORT_NO,DESCRIPT,ISCHECKOK,ZG_TIME,INFO");//设备信息
		//colsStr.append("EQP_NO,EQP_NAME,EQPADDRESS,DESCRIPT,");//设备信息
		//colsStr.append("DZBM,GLBH,GLMC,");//端子信息
		//colsStr.append("IS_CHECK_OK,IS_REFORM");//是否合格，是否整改
		String[] cols = colsStr.toString().split(",");
		
		StringBuffer colsNameStr = new StringBuffer();
		colsNameStr.append("任务状态,地区,区域,任务名称,设备编码,设备类型,综合化维护网格,任务来源,派发端子数,检查端子数,错误端子数,端子检查准确率,检查员,维护员,审核员,现场规范,是否完成整改,开始时间,结束时间,上次更新时间,检查完成时间");//检查任务
		//colsNameStr.append("任务创建人,任务创建时间,实际完成时间,检查时间,任务类型,设备ID,设备编码,设备名称,");
		//colsNameStr.append("端子ID,端子编码,问题描述,是否合格,整改时间,整改描述");

		//colsNameStr.append("设备编号,设备名称,设备地址,问题描述,");//设备信息
		//colsNameStr.append("端子编号,光路编号,光路名称,");//端子信息
		//colsNameStr.append("是否合格,是否整改");
		String[] colsName = colsNameStr.toString().split(",");
		
		mm.addAttribute("name", "检查任务按设备");
		mm.addAttribute("cols", cols);
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", exportInfoList);
		return "dataListExcel";
	}
	
	@RequestMapping(value = "/secondTaskManager.do")
	public ModelAndView secondTaskManager(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("cablecheck/secondTaskManager/SecondTaskManger-index", null);
	}
	
	@RequestMapping(value="/querySecondTask.do")
	public void querySecondTask(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = doneTaskService.querySecondTask(request, pager);
		write(response, map);
	}
	@RequestMapping(value="/exportSecondTaskExcelByEqp.do")
	public String exportSecondTaskExcelByEqp(HttpServletRequest request, ModelMap mm){
		List<Map<String, Object>> exportInfoList = doneTaskService.exportSecondTaskExcelByEqp(request);

		StringBuffer colsStr = new StringBuffer();
		colsStr.append("NAME,SONNAME,GRID_NAME,TASK_NO,TASK_NAME,TASKTYPE,STATUS,INSPECTOR,MAINTOR,AUDITOR,EQUIPMENT_CODE,EQUIPMENT_NAME,RES_TYPE,ADDRESS,EQP_NO,EQP_NAME,PORT_NO,GLBM,INFO,RECORD_TYPE,ISCHECKOK,");//检查任务
		//colsStr.append("OPER_STAFF,OPER_TIME,ACTUAL_COMPLETE_TIME,CHECK_TIME,ENABLE,EQP_ID,EQP_NO,EQP_NAME,");//设备信息
		//colsStr.append("PORT_ID,PORT_NO,DESCRIPT,ISCHECKOK,ZG_TIME,INFO");//设备信息
		//colsStr.append("EQP_NO,EQP_NAME,EQPADDRESS,DESCRIPT,");//设备信息
		//colsStr.append("DZBM,GLBH,GLMC,");//端子信息
		//colsStr.append("IS_CHECK_OK,IS_REFORM");//是否合格，是否整改
		String[] cols = colsStr.toString().split(",");
		
		StringBuffer colsNameStr = new StringBuffer();
		colsNameStr.append("地区,区域,综合化维护网格,任务编码,任务名称,任务来源,任务状态,检查员,维护员,审核员,设备编码,设备名称,设备类型,地址,glbm,回单备注,记录类型,是否合格");//检查任务
		//colsNameStr.append("任务创建人,任务创建时间,实际完成时间,检查时间,任务类型,设备ID,设备编码,设备名称,");
		//colsNameStr.append("端子ID,端子编码,问题描述,是否合格,整改时间,整改描述");

		//colsNameStr.append("设备编号,设备名称,设备地址,问题描述,");//设备信息
		//colsNameStr.append("端子编号,光路编号,光路名称,");//端子信息
		//colsNameStr.append("是否合格,是否整改");
		String[] colsName = colsNameStr.toString().split(",");
		
		mm.addAttribute("name", "周期性任务筛选");
		mm.addAttribute("cols", cols);
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", exportInfoList);
		return "dataListExcel";
	}
	
	
	
}
