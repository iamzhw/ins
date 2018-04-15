package com.cableInspection.action;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableInspection.service.CableService;
import com.system.constant.RoleNo;
import com.system.service.GeneralService;

/**
 * @ClassName: CableController
 * @Description: 光缆段管�?
 * @author xiazy
 * @date: 2014-6-05
 * 
 */
@RequestMapping(value = "/Cable")
@SuppressWarnings("all")
@Controller
public class CableController extends BaseAction {
	@Resource
	private CableService cableService;

	@Resource
	private GeneralService generalService;

	@RequestMapping(value = "/index.do")
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) {
		map.put("isSonAreaAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN_SON_AREA));
		return "cableinspection/cable/cable/cable_index";
	}

	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = cableService.query(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		rs.put("LINE_ID", request.getParameter("cableId"));
		List<Map> hh = cableService.queryEquipmentType();
		rs.put("eqpList", hh);
		List<Map> dept = cableService.queryDept(request);
		rs.put("deptList", dept);

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)
				|| (Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 省级或市级管理员
			rs.put("sonAreaList",
					generalService.getSonAreaList(request.getSession()
							.getAttribute("areaId").toString()));
		} else if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 区级管理�?
			rs.put("sonAreaList",
					generalService.getAreaById(request.getSession()
							.getAttribute("sonAreaId").toString()));
		}

		// 多边形型缆线
		if ("polygon".equals(request.getParameter("shape"))) {
			return new ModelAndView(
					"cableinspection/cable/cable/cable_polygon/cable_add", rs);
		}

		return new ModelAndView("cableinspection/cable/cable/cable_add", rs);
	}

	/**
	 * 进入巡线员�?择页�?
	 */
	@RequestMapping(value = "/index_cablePlanInspector.do")
	public ModelAndView getCablePlanInspectorIndex(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"cableinspection/cable/cable/cable_polygon/cable_inspector",
				null);
	}

	@RequestMapping(value = "/show.do")
	public ModelAndView show(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("LINE_ID", request.getParameter("cableId"));
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		return new ModelAndView("cableinspection/cable/cable/cable_query", rs);
	}

	// 新增时显示设备坐标点
	@RequestMapping(value = "/getPoints.do")
	public void getPoints(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jsonString = cableService.queryPoints(request);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
	}

	// 显示已有光缆�?
	@RequestMapping(value = "/getCable.do")
	public void getCable(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String jsonString = cableService.getCable(request);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
	}

	// 保存光缆
	@RequestMapping(value = "/saveCable.do")
	public void saveCable(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean ifsuccess = true;
		Map map = new HashMap();
		try {
			cableService.saveCabel(request);
		} catch (Exception e) {
			e.printStackTrace();
			ifsuccess = false;
		}

		map.put("ifsuccess", ifsuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	// 删除光缆�?
	@RequestMapping(value = "/deleteCable.do")
	public void deleteCable(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Boolean ifsuccess = true;
		Map map = cableService.deleteCable(request);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	@RequestMapping(value = "/edit.do")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("LINE_ID", request.getParameter("cableId"));
		// 获得缆线中所有点的详细信�?
		//rs.put("POINTS", cableService.getPointsInCable(request));
		String areaName = cableService.getAreaName(request);
		rs.put("AREA_NAME", areaName);
		List<Map> hh = cableService.queryEquipmentType();
		rs.put("eqpList", hh);
		List<Map> dept = cableService.queryDept(request);
		rs.put("deptList", dept);
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)
				|| (Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 省级或市级管理员
			rs.put("sonAreaList",
					generalService.getSonAreaList(request.getSession()
							.getAttribute("areaId").toString()));
		} else if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 区级管理�?
			rs.put("sonAreaList",
					generalService.getAreaById(request.getSession()
							.getAttribute("sonAreaId").toString()));
		}
		return new ModelAndView("cableinspection/cable/cable/cable_edit_update", rs);
	}

	// 显示已有光缆�?
		@RequestMapping(value = "/getPointsInCable.do")
		public void getPointsInCable(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String jsonString = cableService.getPointsInCable(request);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonString);
		}
		@RequestMapping(value = "/getSitesByIdsUI.do")
		public ModelAndView getSitesByIdsUI(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
		//	String jsonstring  = URLDecoder.decode(request.getParameter("jsonstring"),"UTF-8");
			//String pointInLine  = URLDecoder.decode(request.getParameter("pointInLine"),"UTF-8");
			//request.setAttribute("jsonstring", jsonstring);
			//request.setAttribute("pointInLine", pointInLine);
			//request.setAttribute("lat", lat);
			return new ModelAndView("cableinspection/cable/cable/selected_sites");
		}

	// 保存编辑完成的光�?
		@RequestMapping(value = "/saveEditedCable.do")
		public void saveEditedCable(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			Boolean ifsuccess = true;
			Map map = new HashMap();
			try {
				cableService.saveEditedCable(request);
			} catch (Exception e) {
				e.printStackTrace();
				ifsuccess = false;
			}

			map.put("ifsuccess", ifsuccess);
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}
		
	//得到区域内的点数
		@RequestMapping(value="/getPontsByZone.do")
		public void getPointsByZone(HttpServletRequest request,
				HttpServletResponse response) throws IOException{
			List list =  new ArrayList();
			list = cableService.getPointsByZone(request);
			response.getWriter().write(JSONArray.fromObject(list).toString());
		}
		
		//关键点非关键点互改
		@RequestMapping(value = "/editPoint.do")
		@ResponseBody
		public void editPoint(HttpServletRequest request,
				HttpServletResponse response) throws IOException{
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(JSONObject.fromObject(cableService.editPoint(request)).toString());
		}
		
		@RequestMapping(value = "/editPointPage.do")
		public ModelAndView editPointPage(HttpServletRequest request,
				HttpServletResponse response){
			Map rs = cableService.editPointPage(request);
			return new ModelAndView("cableinspection/cable/cable/linepoint_update", rs);
		}
		
		@RequestMapping(value = "/cableSelectPage.do")
		public ModelAndView cableSelectPage(HttpServletRequest request,
				HttpServletResponse response){
			Map rs = new HashMap();//恒嘉路新
			return new ModelAndView("cableinspection/cable/cable/cable_select_page", rs);
		}
		
		@RequestMapping(value = "/cableSectionSelectPage.do")
		public ModelAndView cableSectionSelectPage(HttpServletRequest request,
				HttpServletResponse response){
			Map rs = new HashMap();//恒嘉路新
			return new ModelAndView("cableinspection/cable/cable/cableSection_Select_Page", rs);
		}
		
		@RequestMapping(value = "/queryOssCable.do")
		public void queryOssCable(HttpServletRequest request,
				HttpServletResponse response,UIPage pager) throws IOException{
			Map rs = cableService.getCableByName(request, pager);
			write(response, rs);
		}
		
		@RequestMapping(value = "/queryOssCableSection.do")
		public void queryOssCableSection(HttpServletRequest request,
				HttpServletResponse response,UIPage pager) throws IOException{
			Map rs = cableService.getCableSectionById(request, pager);
			write(response, rs);
		}
		
		@RequestMapping("exportOssCable")
		public String exportOssCable(HttpServletRequest request,HttpServletResponse response,ModelMap model){
			List<Map<String,Object>> dataList = cableService.exportCableByName(request);
			String[] cols = new String[] { "ID", "NO", "NAME","STATION","STATUS"};
			String[] colsName = new String[] {"缆线ID", "缆线编码", "缆线名称","所属局站","状态"};
			model.addAttribute("name", "主干光缆信息");
			model.addAttribute("cols", cols);
			model.addAttribute("colsName", colsName);
			model.addAttribute("dataList", dataList);
			return "dataListExcel";
		}
		
		//获取人井坐标
		@RequestMapping(value = "/getWells.do")
		public void getWells(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String jsonString = cableService.getWellLocation(request);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonString);
		}
		
		/*
		 * 导出缆线下的点
		 */
		@RequestMapping("exportLinePoint")
		public String exportLinePoint(HttpServletRequest request,HttpServletResponse response,ModelMap model){
			List<Map> dataList = cableService.exportLinePoint(request);
			String[] cols = new String[] { "POINT_ID", "POINT_NO", "POINT_NAME","POINT_TYPE_NAME",
					"EQUIPMENT_TYPE_NAME","POINT_SEQ","LINE_ID","LINE_NO","LINE_NAME"};
			String[] colsName = new String[] {"点ID", "点编码", "点名称","点类型","设备类型","缆线中的序号","缆线ID",
					"缆线编码","缆线名称"};
			model.addAttribute("name", "缆线所属点信息");
			model.addAttribute("cols", cols);
			model.addAttribute("colsName", colsName);
			model.addAttribute("dataList", dataList);
			return "dataListExcel";
		}
		
		/*
		 * 进入导入页面
		 */
		@RequestMapping("editLinePointPage")
		public String editLinePointPage(HttpServletRequest request,
				HttpServletResponse response, ModelMap map){
			return "cableinspection/cable/cable/import";
		}
		
		/*
		 * 修改或者删除缆线中点的顺序
		 */
		@RequestMapping("editLinePoint")
		public void editLinePoint(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file) throws IOException{
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(cableService.editLinePoint(request, file).toString());
		}
		
		@RequestMapping(value = "/parentLineSelectPage.do")
		public ModelAndView parentLineSelectPage(HttpServletRequest request,
				HttpServletResponse response){
			Map rs = new HashMap();
			return new ModelAndView("cableinspection/cable/cable/parentLine", rs);
		}
		
		@RequestMapping(value = "/addNewParentLinePage.do")
		public ModelAndView addNewParentLinePage(HttpServletRequest request,
				HttpServletResponse response){
			Map rs = new HashMap();
			return new ModelAndView("cableinspection/cable/cable/addParentLine", rs);
		}
		
		@RequestMapping(value = "/queryParentLine.do")
		public void queryParentLine(HttpServletRequest request, HttpServletResponse response,
				UIPage pager) throws IOException {
			Map<String, Object> map = cableService.queryParentLine(request, pager);
			write(response, map);
		}
		
		@RequestMapping(value = "/editParentLine.do")
		public  void editParentLine(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String jsonString = cableService.updateParentLineForLine(request);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonString);
		}
		
		@RequestMapping(value = "/addNewParentLine.do")
		public void addNewParentLine(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String jsonString = cableService.addNewParentLine(request);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonString);
		}
}
