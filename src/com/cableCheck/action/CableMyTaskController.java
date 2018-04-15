package com.cableCheck.action;

import icom.util.BaseServletTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.dao.CableMyTaskDao;
import com.cableCheck.service.CableMyTaskService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;

import util.page.BaseAction;
import util.page.Query;
import util.page.UIPage;

@RequestMapping(value = "/CableCheck")
@SuppressWarnings("all")
@Controller
public class CableMyTaskController extends BaseAction{
	
	Logger logger = Logger.getLogger(CableMyTaskController.class);
	
	@Resource
	private CableMyTaskService cableMyTaskService;
	@Resource
	private CableMyTaskDao cableMyTaskDao;
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cablecheck/MyTask-index", null);
	}
	
	/**
	 * TODO 默认显示待办信息
	 * 我的任务-查询
	 * @add by wangxy 20160711
	 */
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = cableMyTaskService.getTask(request, pager);
		write(response, map);
	}

	/**
	 * TODO 默认显示待办信息
	 * 我的任务-导出
	 * @add by lxl 20161129
	 */
	@RequestMapping(value="/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
		//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		cableMyTaskService.getTaskDownload(request, response);
	}
	
	/**
	 * TODO 默认显示待办信息
	 * 南京统一整改单导出
	 * @add by lxl 20161129
	 */
	@RequestMapping(value="/exportExcelNjZg.do")
	public void exportExcelNjZg(HttpServletRequest request, HttpServletResponse response){
		//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		cableMyTaskService.getTaskDownloadByNj(request, response);
	}
	
	/**
	 * 跳转查看详情页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoShowEquip.do")
	public ModelAndView intoShowEquip(HttpServletRequest request,
			HttpServletResponse response){
		String taskId = request.getParameter("TASK_ID");
		String taskType = cableMyTaskService.getTaskByTaskId(taskId);
		
		Map<String, Object> rs = new HashMap<String, Object>();
		 if("0".equals(taskType)||"4".equals(taskType)||"10".equals(taskType)||"11".equals(taskType)||"12".equals(taskType)||"13".equals(taskType)){
			 //0周期性检查
			 rs = cableMyTaskService.getMyTaskEqpPhotoForZq(request);
			// return new ModelAndView("cablecheck/TaskDetail",rs);
			 return new ModelAndView("cablecheck/TaskDetailNew",rs);
		 }else{
			 //问题上报
			 rs = cableMyTaskService.getMyTaskEqpPhoto(request);
			 //return new ModelAndView("cablecheck/EquipDetail",rs);
			 return new ModelAndView("cablecheck/EquipDetailNew",rs);
		 }
	}
	
//	@ResponseBody
//	@RequestMapping("/queryPort.do")
//	public void queryDPort(HttpServletRequest req,HttpServletResponse resp,UIPage pager) throws IOException{
//		String EQP_ID = req.getParameter("EQP_ID");
//		Map conds = new HashMap();
//		conds.put("EQP_ID", EQP_ID);
//		Query query = new Query();
//		query.setPager(pager);
//		query.setQueryParams(conds);
//		List<Map> port = cableMyTaskService.getPort(query);
//		Map<String,Object> pmap = new HashMap<String, Object>();
//		pmap.put("total", query.getPager().getRowcount());
//		pmap.put("rows", port);
//		write(resp,pmap);
//	}
	/**
	 * 审核(audit)，回单(receipt)，派单（distributeBill），退单（return）
	 * @add by wangxy 20160711
	 *
	 */
	@ResponseBody
	@RequestMapping("/updateTask.do")
	public void handleTask(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableMyTaskService.handleTask(request);
		BaseServletTool.sendParam(response, result);
	}
	/**
	 * 派单 
	 * @add by wangxy 20160711
	 */
	@RequestMapping("bill_selectStaff.do")
	public ModelAndView selectBillStaff(HttpServletRequest request, HttpServletResponse response) {
		String operate = request.getParameter("operate");
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("billIds", request.getParameter("billIds"));
		rs.put("operate", operate);
		return new ModelAndView("cablecheck/Staff", rs);
	}
	/**
	 * 派单-选择人员查询
	 * @add by wangxy 20160711
	 */
	@RequestMapping("queryHandler.do")
	public void queryHandler(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		
		Map<String, Object> rs = null;
		
		rs = cableMyTaskService.queryStaffList(request,pager);
		write(response, rs);
	}

	/**
	 * 进入审核页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("intoAudit.do")
	public ModelAndView intoAudit(HttpServletRequest request, 
			HttpServletResponse response){
		Map<String, Object> map = cableMyTaskService.queryTaskDetailForAudit(request);
		return new ModelAndView("cablecheck/intoAudit",map);
	}
	
	/**
	 * 审核
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/taskAudit.do")
	public void taskAudit(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableMyTaskService.taskAudit(request);
		BaseServletTool.sendParam(response, result);
	}
	/**
	 * 根据
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/getTaskByTaskId")
	@ResponseBody 
	public Map getTaskByTaskId(String taskId){
		Map map = cableMyTaskService.getTaskObjByTaskId(taskId);
		return map;
	}
	
	/**
	 * 工单归档
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/intoFinish.do")
	@ResponseBody 
	public void intoFinish(HttpServletRequest request, 
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			cableMyTaskService.intoFinish(request,response);
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		map.put("status", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	@RequestMapping(value = "/receiveReformOrder.do")
	public ModelAndView receiveReformOrder(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cablecheck/getReformOrderByNj/GetReformOrder-index", null);
	}
	
	
	
	/**
	 * TODO 默认显示待办信息
	 * 南京统一接单岗
	 * @add by Lixl 20170220
	 */
	@RequestMapping(value = "/queryReformOrder.do")
	public void queryReformOrder(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = cableMyTaskService.getReformOrder(request, pager);
		write(response, map);
	}
	
	/**
	 * 派单 
	 * @return 
	 * @add by Lixl 20170303
	 */
	@RequestMapping("reformOrder_selectStaff.do")
	public ModelAndView selectReformOrderStaff(HttpServletRequest request, HttpServletResponse response) {
		String operate = request.getParameter("operate");	
		String nowStaff = request.getSession().getAttribute("staffId").toString();
		List<Map<String, Object>>  staffRole =cableMyTaskDao.getRole(nowStaff);
		ModelAndView mav = new ModelAndView();
		boolean flag = false ;
		for (Map<String, Object> a : staffRole) {
			String roleId = String.valueOf(a.get("ROLE_ID"));
			if ("366".equals(roleId)) {
				flag = true;
				// 南京统一接单
				break;
			}
		}
			if (flag == true) {
				if (operate.equals("zhuanfa")){
			    mav.setViewName("cablecheck/getReformOrderByNj/getBillStaff");
				operate="TYJGDZF";
						
				}else{
					 mav.setViewName("cablecheck/getReformOrderByNj/getStaff");
				}
				
			} else {
				mav.setViewName("cablecheck/getReformOrderByNj/reformOrderStaff");
			}

		
        
        mav.addObject("billIds", request.getParameter("billIds"));
        mav.addObject("operate", operate);
		return mav;
	}
	
	@RequestMapping(value = "/getInspactPersonIndex.do")
    public ModelAndView getInspactPersonIndex(HttpServletRequest request,
	    HttpServletResponse response) {
		String type = request.getParameter("type");
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("type", request.getParameter("type"));
	return new ModelAndView("cablecheck/getReformOrderByNj/getStaff", rs);
	}
	
	@RequestMapping("queryHandlerByNj.do")
	public void queryHandlerByNj(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		
		Map<String, Object> rs = null;
		
	   rs = cableMyTaskService.queryStaff_SHY(request,pager);
		
		write(response, rs);
	}
	//通过账号或姓名模糊查询所对应的审核员
	@RequestMapping("searchStaffByNj.do")
	public void searchStaffByNj(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		
		Map<String, Object> rs = null;
		
	   rs = cableMyTaskService.searchStaff_SHY(request,pager);
		
		write(response, rs);
	}
	
	
	
	/**
	 * 跳转查看详情页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoShowEquipByZg.do")
	public ModelAndView intoShowEquipByZg(HttpServletRequest request,
			HttpServletResponse response){
		String taskId = request.getParameter("TASK_ID");
		String taskType = cableMyTaskService.getTaskByTaskId(taskId);
		
		Map<String, Object> rs = new HashMap<String, Object>();
//		 if("0".equals(taskType)||"4".equals(taskType)){
//			 //0周期性检查
//			 rs = cableMyTaskService.getMyTaskEqpPhotoForZq(request);
//			 return new ModelAndView("cablecheck/TaskDetail",rs);
//		 }else{
			 //问题上报
			 rs = cableMyTaskService.getMyTaskEqpPhotoForNj(request);
			 return new ModelAndView("cablecheck/getReformOrderByNj/EquipDetail",rs);
//		 }
	}
	
	/**
	 * TODO 获取端子
	 * @add by Lixl 20170307
	 */
	@RequestMapping(value = "/getPort.do")
	public void signPort(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = cableMyTaskService.getPort(request, pager);
		write(response, map);
	}
	/**
	 * TODO 更改端子所属部门
	 * @add by Lixl 20170308
	 */
	@ResponseBody
	@RequestMapping("/updatePort.do")
	public void updatePort(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableMyTaskService.updatePort(request);
		BaseServletTool.sendParam(response, result);
	}
	
	/**
	 * 部门审核员退单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cancel.do")
	@ResponseBody 
	public void cancel(HttpServletRequest request, 
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			cableMyTaskService.cancel(request,response);
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		map.put("status", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	

	/**
	 *审核员退单，检查员端子检查错误，审核员退单，让检查员重新检查
	 *如果是一键反馈或者不预告问题上报的错误任务，直接将此条任务删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cancelTask.do")
	@ResponseBody 
	public void cancelTask(HttpServletRequest request, 
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			cableMyTaskService.cancelTask(request,response);
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		map.put("status", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 新审核
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/taskNewAudit.do")
	public void taskNewAudit(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableMyTaskService.taskNewAudit(request);
		BaseServletTool.sendParam(response, result);
	}
	/**
	 * 转发 
	 */
	@RequestMapping("selectDouDiStaff.do")
	public ModelAndView selectDouDiStaff(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("billIds", request.getParameter("billIds"));
		rs.put("area", request.getParameter("area"));
		rs.put("son_area", request.getParameter("son_area"));
		return new ModelAndView("cablecheck/DouDiStaff", rs);
	}
	/**
	 * 兜底岗人员账号查询
	 * @add by wangxy 20160711
	 */
	@RequestMapping("queryDouDi.do")
	public void queryDouDi(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> rs = null;
		rs = cableMyTaskService.queryDouDi(request,pager);
		write(response, rs);
	}
	/**
	 * 转发
	 */
	@ResponseBody
	@RequestMapping("/updateTaskNew.do")
	public void updateTaskNew(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableMyTaskService.updateTask(request);
		BaseServletTool.sendParam(response, result);
	}
}
