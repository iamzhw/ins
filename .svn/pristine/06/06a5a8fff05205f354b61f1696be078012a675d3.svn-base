package com.inspecthelper.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inspecthelper.dao.InspectHelperDao;
import com.inspecthelper.service.IInspectService;

import util.page.BaseAction;
import util.page.UIPage;

/**
 * 日常巡检
 * @author Administrator
 *
 */
@RequestMapping(value = "/inspect")
@Controller
public class InspectController extends BaseAction{
	
	@Resource
	private IInspectService inspectService;
	
	@Resource
	private InspectHelperDao inspectHelperDao;
	

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/inspecthelper/inspect/inspect_index");
	}
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = inspectService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/getOrderCount.do")
	public void getOrderCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String staffNo = (String) request.getSession().getAttribute("staffNo");
		String count = inspectService.getOrderCount(staffNo);
		response.getWriter().write(count);
	}
	
	@RequestMapping(value = "/exportOrderDetailExcel.do")
	public String exportOrderDetailExcel(HttpServletRequest request, ModelMap mm) throws IOException{
		String orderId = request.getParameter("order_id");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		List<Map<String,Object>> detailList = inspectService.getOrderDetail(params);
		String[] cols = new String[] {"资源ID", "资源编码", "资源名称", "资源地址"};
		String[] columnMethods = new String[] { "equipmentId", "equipmentNo",
				"equipmentName", "address" };
		mm.addAttribute("name", "日常巡检任务");
		mm.addAttribute("cols", cols);
		mm.addAttribute("dataList", detailList);
		mm.addAttribute("columnMethods",columnMethods);
		return "insOrderDataListExcel";
	}
	
	@RequestMapping(value = "/resTrouIndex.do")
	public ModelAndView resTrouIndex() {
		/*HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String staffNo = (String) session.getAttribute("staffNo");*/
		// System.out.println("staffNo:"+staffNo);
		//list = inspectServiceImpl.getEquTarget(new HashMap());
		return new ModelAndView("/inspecthelper/inspect/inspect_res_trou_index");
		//return new DefaultHttpHeaders("inspect-res-trou-index");
	}
	
	
	@RequestMapping(value = "/resTrouTable.do")
	public void resTrouTable(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = inspectService.resTrouTable(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/oneEquTarget.do")
	public ModelAndView oneEquTarget(HttpServletRequest request) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		//map.put("staffNo", staffNo);
		map.put("equipmentId", request.getParameter("equipmentId"));
		map.put("resTypeId", request.getParameter("resTypeId"));
		map.put("orderId", request.getParameter("orderId"));
		return new ModelAndView("/inspecthelper/inspect/one_inspect_equ_target",map);
	}
	
	@RequestMapping(value = "/queryOneEquTarget.do")
	public void queryOneEquTarget(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String,Object> map = inspectService.getEquTarget(request);
		write(response, map);
	}
	
	@RequestMapping(value = "/uploadPhoto.do")
	public ModelAndView uploadPhotoIndex(HttpServletRequest request) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("equipmentId", request.getParameter("equipmentId"));
		map.put("targetId", request.getParameter("targetId"));
		map.put("orderId", request.getParameter("orderId"));
		return new ModelAndView("/inspecthelper/inspect/upload-photo",map);
	}
	
	@RequestMapping(value = "/uploadPhoto1.do")
	public void uploadPhoto1(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String ifsucess = inspectService.uploadPhoto(request);;
		response.getWriter().write(ifsucess);
	}
	
	@RequestMapping(value = "/updatePhotoButtonV.do")
	public void updatePhotoButtonV(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String l = inspectService.getLastTrouPhotoPath(request);
		response.getWriter().print(l);
	}
	
	@RequestMapping(value = "/saveResTrouIndex.do")
	public ModelAndView saveResTrouIndex(HttpServletRequest request) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		String orderId=request.getParameter("orderId");
		String equipmentId=request.getParameter("equipmentId");
		String targetId=request.getParameter("targetId");
		map.put("equipmentId", equipmentId);
		map.put("targetId", targetId);
		map.put("orderId", orderId);
		String state="0";
		String troubleCode = orderId + "," + equipmentId + "," + targetId;
		String remarks="";
		List<Map<String,Object>> l = inspectService.getTargetQues(troubleCode);
		if (l.size() > 0) {
			state = "1";
			remarks = String.valueOf (l.get(0).get("REMARKS"));

		} else {
			state = "0";
			remarks = "";
		}
		map.put("state", state);
		map.put("remarks", remarks);
		return new ModelAndView("/inspecthelper/inspect/inspect-res-trou",map);
	}
	
	@RequestMapping(value = "/cDuanIndex.do")
	public ModelAndView cDuanIndex(HttpServletRequest request) {
		// l = inspectHelperDaoImpl.getGJCDuanInfo(param);
		HashMap<String,Object> map = new HashMap<String,Object>();
		String orderId=request.getParameter("orderId");
		String equipmentId=request.getParameter("equipmentId");
		map.put("equipmentId", equipmentId);
		map.put("orderId", orderId);
		return new ModelAndView("/inspecthelper/inspect/cduan-index",map);
	}
	
	@RequestMapping(value = "/exportCDuanExcel.do")
	public String exportCDuanExcel(HttpServletRequest request,HttpServletResponse response,ModelMap mm) {
		
		List<Map<String,Object>> list = inspectService.cDuanTable(request);

		String[] columnNames = new String[] { "工程属性", "设备编码", "项目编号",
				"项目名称", "施工公司", "竣工日期", "录入日期", "变动情况" };
		String[] columnMethods = new String[] {"PROJECT_TYPE", "RES_NO",
				"PROJECT_NO", "PROJECT_NAME", "MANAGE_COMPANY", "END_DATE",
				"EXPORT_DATE", "CHANGE_STATUS" };
		mm.addAttribute("name", "导出信息");
		mm.addAttribute("cols", columnNames);
		mm.addAttribute("dataList", list);
		mm.addAttribute("columnMethods",columnMethods);
		return "insOrderDataListExcel";
	}
	
	@RequestMapping(value = "/saveResTrou.do")
	public void saveResTrou(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String ifsucess = inspectService.saveResTrou(request);;
		response.getWriter().write(ifsucess);
	}
	
	
	@RequestMapping(value = "/updateButtonV.do")
	public void updateButtonV(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String orderId = request.getParameter("orderId");
		String equipmentId = request.getParameter("equipmentId");
		String targetId = request.getParameter("targetId");
		String troubleCode = orderId + "," + equipmentId + "," + targetId;
		List<Map<String,Object>> l = inspectService.getTargetQues(troubleCode);
		String state="0";
		if (l.size() > 0) {
			state = "1";
		} 
		response.getWriter().print(state);
	}
	
	@RequestMapping(value = "/checkOneEqup.do")
	public void checkOneEqup(HttpServletRequest request,HttpServletResponse response) throws IOException{
		inspectService.checkRes(request);
		response.getWriter().print("问题上报成功");
	}
	
	@RequestMapping(value = "/getLastTrou.do")
	public ModelAndView getLastTrou(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("equipmentId", request.getParameter("equipmentId"));
		map.put("targetId", request.getParameter("targetId"));
		map.put("orderId", request.getParameter("orderId"));
		return new ModelAndView("/inspecthelper/inspect/inspect-last-trou",inspectService.getLastTrou(request));
	}
	
	@RequestMapping(value = "/orderDetail.do")
	public ModelAndView orderDetail(HttpServletRequest request) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("orderId", request.getParameter("orderId"));
		map.put("equCode", request.getParameter("equCode"));
		return new ModelAndView("/inspecthelper/inspect/inspect_res_trou_detail",map);
	}
	
	@RequestMapping(value = "/getOrderDetail.do")
	public void getOrderDetail(HttpServletRequest request,HttpServletResponse response,UIPage pager) throws IOException {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("orderId", request.getParameter("orderId"));
		map.put("equCode", request.getParameter("equCode"));
		List<Map<String, Object>> remap= inspectService.getOrderDetail(map);
		map.put("total",pager.getRowcount());
		map.put("rows", remap);
		write(response, map);
	}
	
	@RequestMapping(value = "/cduanTrou.do")
	public ModelAndView cduanTrou(HttpServletRequest request) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("changeStatus",request.getParameter("changeStatus"));
		map.put("orderId",request.getParameter("orderId"));
		map.put("equipmentId",request.getParameter("equipmentId"));
		return new ModelAndView("/inspecthelper/inspect/inspect-cduan-res-trou",map);
	}
	
	@RequestMapping(value = "/saveResTrou_.do")
	public void saveResTrou_(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String ifsucess = inspectService.saveResTrou_(request);;
		response.getWriter().write(ifsucess);
	}
	
	@RequestMapping(value = "/dinamicChangeIndex.do")
	public ModelAndView dinamicChangeIndex(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("orderId",request.getParameter("orderId"));
		map.put("equipmentId",request.getParameter("equipmentId"));
		map.put("areaId",session.getAttribute("areaId"));
		// l = inspectHelperDaoImpl.getGJCDuanInfo(param);
		return new ModelAndView("/inspecthelper/inspect/dinamic-change-index",map);
	}
	
	@RequestMapping(value = "/cDuanTable.do")
	public void cDuanTable(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> map =new HashMap<String,Object>();
		map.put("rows", inspectService.cDuanTable(request));
		write(response, map);
	}
	
	@RequestMapping(value = "/dinamicChangeTable.do")
	public void dinamicChangeTable(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> map =new HashMap<String,Object>();
		map.put("rows", inspectService.dinamicChangeTable(request));
		write(response, map);
	}
	
	
	/**
	 * 导出ODF动态纤芯内容
	 * 
	 * @return
	 */
	public String exportODFDinamicExcel(HttpServletRequest request,HttpServletResponse response,ModelMap mm) {
		List<Map<String,Object>> list = inspectService.exportODFDinamicExcel(request);
		String[] columnNames = new String[] { "序号", "ID", "设备ID", "设备编码",
				"设备名称", "工单性质", "动态数据", "实时光路", "端子", "光路状态", "更芯日期",
				"现场情况（无问题的保留空白）", "整改情况", "是否FTTH/BBU(FTTH/BBU)", "1级状态",
				"2级状态", "3级状态", "4级状态", "巡检公司", "一级巡检人" };
		String[] columnMethods = new String[] { "", "ID", "EQUIPMENT_ID",
				"设备编码_B", "设备名称_B", "TCS_NAME", "光路编码_B", "NO", "端口_B", "NAME",
				"INSERTDATE", "", "", "", "STATE_A", "STATE_B", "STATE_C",
				"STATE_D", "OWN_COMPANY", "STAFF_NAME" };
		mm.addAttribute("name", "导出信息");
		mm.addAttribute("cols", columnNames);
		mm.addAttribute("dataList", list);
		mm.addAttribute("columnMethods",columnMethods);
		return "insOrderDataListExcel";
	}
	
	@RequestMapping(value = "/analysisODFDinamicExcel.do")
	public void analysisODFDinamicExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String ifsucess = inspectService.analysisODFDinamicExcel(request,response);;
		response.getWriter().write(ifsucess);
	}
	
	public void updateDinamicChange(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String ifsucess=inspectService.updateDinamicChange(request, response);
		response.getWriter().write(ifsucess);
	}
	
	
	@RequestMapping(value = "/dinamicTrou.do")
	public ModelAndView dinamicTrou(HttpServletRequest request) {
		Map<String,Object> map =new HashMap<String,Object>();
		String id = request.getParameter("id");
		String changeStatus = request.getParameter("changeStatus");
		String equCode = request.getParameter("equCode");
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("seqName", "ORDER_ID");
		String orderId = inspectHelperDao.getNextSeqVal(map1);
		String equipmentId = request.getParameter("equipmentId");
		map.put("id", id);
		map.put("changeStatus", changeStatus);
		map.put("equCode", equCode);
		map.put("orderId", orderId);
		map.put("equipmentId", equipmentId);
		return new ModelAndView("/inspecthelper/inspect/inspect-dinamic-change-trou1",map);
	}
	
}
