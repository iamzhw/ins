package com.cableCheck.action;

import icom.util.BaseServletTool;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.session.Session;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.service.ExcelTeamService;
import com.cableCheck.service.MainTainCompanyService;
import com.cableCheck.service.TaskMangerService;
import com.cableCheck.service.WrongPortReportService;
import com.linePatrol.util.MapUtil;
import com.system.model.ZTreeNode;
import com.system.service.GeneralService;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/MainTainCompany")
@SuppressWarnings("all")
@Controller
public class MainTainCompanyController extends BaseAction{

	@Resource
	private MainTainCompanyService mainTainService;
	@Resource
	private GeneralService generalService;
	
	
	@Resource
	private ExcelTeamService ExcelTeamService;
	
	/**
	 * 跳转到代维公司页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/mainTainCompany/mainTainCompany", null);
	}
	
	
	@RequestMapping(value = "/editView.do")
	public ModelAndView editView(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		String comId = request.getParameter("COMPANY_ID");
		String comName = request.getParameter("COMPANY_NAME");
		map.put("comId", comId);
		map.put("comName", comName);
		if(StringUtils.isNotBlank(comId)){
			Map<String, Object> mapR = mainTainService.selectCompany(request);
			if(mapR.size()>0){
				String company_name = mapR.get("COMPANY_NAME").toString();
				map.put("comName", company_name);
			}
		}
		return new ModelAndView("cablecheck/mainTainCompany/editCompany", map);
	}
	
	@RequestMapping(value = "/relationCompany.do")
	public ModelAndView relationCompany(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		String comId = request.getParameter("COMPANY_ID");
		String comName = request.getParameter("COMPANY_NAME");
		map.put("comId", comId);
		map.put("comName", comName);
		if(StringUtils.isNotBlank(comId)){
			Map<String, Object> mapR = mainTainService.selectCompany(request);
			if(mapR.size()>0){
				String company_name = mapR.get("COMPANY_NAME").toString();
				map.put("comName", company_name);
			}
		}
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		map.put("areaList", areaList);
		return new ModelAndView("cablecheck/mainTainCompany/relationTeam", map);
	}
	
	
	
	@RequestMapping(value = "/listAll.do")
	public void listAll(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
//		ExcelTeamService.excel("");
		
		
		Map<String, Object> map = mainTainService.listCompany(request, pager);
		write(response, map);
	}
	
	
	@RequestMapping(value = "/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {	
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		mainTainService.exportExcel(para, request, response, pager);
	}
	
	
	
	@RequestMapping(value = "/selectCompany.do")
	public void selectCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = mainTainService.selectCompany(request);
		write(response, map);
	}
	
	@RequestMapping(value = "/editCompany.do")
	public void editCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = mainTainService.editCompany(request);
//		write(response, map);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/deleteCompany.do")
	public void deleteCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = mainTainService.deleteCompany(request);
		write(response, map);
	}
	
	@RequestMapping(value = "/saveRelation.do")
	public void saveRelation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = mainTainService.saveRelation(request);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value = "/gridOrder.do")
	public ModelAndView gridOrder(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/mainTainCompany/gridOrder", null);
	}
	
	@RequestMapping(value = "/gridOrderAll.do")
	public void gridOrderAll(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = mainTainService.listGridOrder(request, pager);
		write(response, map);
	}
	
	
	
	
	
	
	
	
	/****************************************************************************************/
	@RequestMapping(value = "/getDeptTree.do")
	@ResponseBody
	public List getDeptTree(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String actionName = request.getParameter("actionName");
		String treeId = "0000017";
		Map map = new HashMap();
		map.put("id", id);
		map.put("treeId", treeId);
//		map.put("user", "0");
//		if (actionName != null && !actionName.equals("")) {
//			if(actionName.length()>=15){
//				map.put("user", "1");		
//			}
//		}
		List<ZTreeNode> nodes = mainTainService.getDeptTree(map);
		
		
		HttpSession session = request.getSession();
		
		return nodes;
	}
	
	
	/**
	 * 装维班组人员的组织架构
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deptTree.do")
	public ModelAndView deptTree(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/mainTainCompany/dept", null);
	}
	
	/**
	 * 修改组织架构树的显示与隐藏
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateTreeStatus.do")
	public void updateTreeStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Map<String, Object> map = mainTainService.updateTreeStatus(request);
			write(response, map);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
