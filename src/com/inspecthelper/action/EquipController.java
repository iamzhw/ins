package com.inspecthelper.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.inspecthelper.service.EquipService;
import com.system.service.GeneralService;
import com.util.MapUtil;

/**
 * 巡检设备控制层
 *
 * @author lbhu
 * @since 2014-10-31
 *
 */
@SuppressWarnings("all")
@RequestMapping(value="/equip")
@Controller
public class EquipController extends BaseAction{

	@Resource
	private EquipService equipService;
	
	@Resource
	private GeneralService generalService;
	
	/**
	 * 跳转至巡检设备维护列表页面
	 * 
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		map.put("equpAreaId", areaId);
		
		//根据区域ID获取所有子区域
		List<Map<String,String>> areaList = generalService.getSonAreaList(areaId);
		
		if (areaId.equals("20")) {
			areaId = String.valueOf(request.getSession().getAttribute("sonAreaId"));
		}
		
		//再根据新的区域ID获取所有子区域
		List<Map<String,String>> sonAreaList = generalService.getSonAreaList(areaId);
		
		if (areaId.equals("21")) {
			Map paramMap = new HashMap();
			paramMap.put("AREAID", "20");
			paramMap.put("AREANAME", "苏州市区");
			sonAreaList.add(paramMap);
			
			paramMap = new HashMap();
			paramMap.put("AREAID", "null");
			paramMap.put("AREANAME", "空白");
			sonAreaList.add(paramMap);
			areaList.add(paramMap);
		}
		//存放区域数据
		map.put("areaList", areaList);
		map.put("sonAreaList", sonAreaList);
		
		String sonAreaId = String.valueOf(request.getSession().getAttribute("sonAreaId"));
		if (sonAreaId.equals("75")) {
			map.put("initAreaName", String.valueOf(request.getSession().getAttribute("sonAreaName")));
		} else {
			map.put("initAreaName", String.valueOf(request.getSession().getAttribute("areaName")));
		}
		
		Map<String,Object> companyMap = new HashMap<String,Object>();
		companyMap.put("areaId", request.getSession().getAttribute("areaId"));
		List<Map> companyList = equipService.getCompanyList(companyMap);
		map.put("companyList", companyList);
		
		ModelAndView mav = new ModelAndView("/inspecthelper/equip/equip_index", map);
		return mav;
	}
	
	/**
	 * 查询设备数据
	 * 
	 * @param request 请求消息
	 * @param response 响应消息
	 * @param pager 分页对象
	 * @throws IOException
	 */
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = equipService.getEquipList(request, pager);
		write(response, map);
	}
	
	/**
	 *巡检设备修改
	 *
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/equipModifyIndex.do")
	public ModelAndView equipModifyIndex(HttpServletRequest request,
			HttpServletResponse response) {
		
		//获取参数对象
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		map.put("equpAreaId", areaId);
		
		//根据区域ID获取所有子区域
		List<Map<String,String>> areaList = generalService.getSonAreaList(areaId);
		
		if (areaId.equals("20")) {
			areaId = String.valueOf(request.getSession().getAttribute("sonAreaId"));
		}
		
		//再根据新的区域ID获取所有子区域
		List<Map<String,String>> sonAreaList = generalService.getSonAreaList(areaId);
		
		if (areaId.equals("21")) {
			Map paramMap = new HashMap();
			paramMap.put("AREAID", "20");
			paramMap.put("AREANAME", "苏州市区");
			sonAreaList.add(paramMap);
			areaList.add(paramMap);
		}
		
		//存放区域数据
		map.put("areaList", areaList);
		map.put("sonAreaList", sonAreaList);

		//获取公司信息
		//TODO
		
		map.put("equipIds", request.getParameter("equipIds"));
		String count = request.getParameter("count");
		if (Integer.parseInt(count) == 1) {
			Map equ = equipService.getEquip(request);
			map.put("equ", equ);
		}
		
		ModelAndView mav = new ModelAndView("/inspecthelper/equip/equipModifyIndex", map);
		return mav;
	}
	
	/**
	 *修改设备信息
	 *
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/modifyEquip.do")
	@ResponseBody
	public Map modifyEquip(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map map = new HashMap();
		Boolean status = true;
		try {
			equipService.modifyEquip(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 *批量设置健康
	 *
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/btnhealthy.do")
	public Map btnhealthy(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map map = new HashMap();
		Boolean status = true;
		try {
			equipService.btnhealthy(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 *清除设备检查员
	 *
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/btnclearallot.do")
	public Map btnclearallot(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map map = new HashMap();
		Boolean status = true;
		try {
			equipService.btnclearallot(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 *跳转至设备维护人员分配页面
	 *
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/staffIndex.do")
	public ModelAndView staffIndex(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		map.put("areaList", areaList);
		map.put("equipIds", request.getParameter("equipIds"));
		map.put("type", request.getParameter("type"));
		
		return new ModelAndView("/inspecthelper/equip/lookup-index", map);
	}
	
	/**
	 *设备维护人员分配
	 *
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/allotEquip.do")
	@ResponseBody
	public Map allotEquip(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			equipService.allotEquip(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 *	跳转至设备巡检记录页面
	 *
	 * @param request 请求消息
	 * @param response 响应消息
	 * @return
	 */
	@RequestMapping(value = "/equipHisIndex.do")
	public ModelAndView equipHisIndex(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("areaId", areaId);
		List<Map> companyList = generalService.getCompanyList(params);//初始化公司信息
		map.put("companyList", companyList);
		
		//根据区域ID获取所有子区域
		List<Map<String,String>> areaList = generalService.getSonAreaList(areaId);
		map.put("areaList", areaList);
		
		if (areaId.equals("20")) {
			areaId = String.valueOf(request.getSession().getAttribute("sonAreaId"));
		}
		
		//再根据新的区域ID获取所有子区域
		List<Map<String,String>> sonAreaList = generalService.getSonAreaList(areaId);
		map.put("sonAreaList", sonAreaList);
		
		return new ModelAndView("/inspecthelper/equip/equip_his_index", map);
	}
	
	/**
	 * 查询设备数据
	 * 
	 * @param request 请求消息
	 * @param response 响应消息
	 * @param pager 分页对象
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryHis.do")
	public void queryHis(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = equipService.getResInsHistory(request, pager);
		write(response, map);
	}
	
	
	/**
	 * 导出设备巡检记录
	 * 
	 * @param request
	 * @param mm 模型对象
	 * @return
	 */
	@RequestMapping(value = "/exportHis.do")
	public String exportHis(HttpServletRequest request, ModelMap mm) {
		
		String[] cols = new String[] {"资源编码", "资源名称", "资源类别",
				"区域", "设备位置", "健康状态", "健康状态更新时间", "巡检人员", "巡检公司", "巡检时间","填报方式","任务类别",
				"是否动态巡检","端子数","占用端子数","错误数","FTTH","BBU","设备","其它","准确率" };
		
		String[] columnMethods = new String[] {"EQUIPMENT_CODE",
				"EQUIPMENT_NAME", "RES_TYPE", "SON_AREA", "EQUP_ADDRESS",
				"STATE", "STATE_CHANGE_DATE", "STAFF_NAME", "OWN_COMPANY",
				"CHECK_DATE","SN","TYPE","ODF_CHECK","PORT_COUNT","PORT_ZY","WRONG_COUNT",
				"FTTH","BBU","EQU","OTHER","RATE"};
		
		List<Map> orderList = equipService.getResInsHistoryList(request);
		
		for(Map item:orderList)
		{		
			int count=Integer.valueOf(item.get("PORT").toString());
			if(count>0)
			{
				Map paramMap = new HashMap();
				paramMap.put("equipmentId", item.get("EQUIPMENT_ID"));
				paramMap.put("orderId", item.get("ORDER_ID"));
				paramMap.put("count", item.get("PORT"));
				Map result=equipService.getFtthCount(paramMap);			
				String portCount=String.valueOf(count);
				String wrongCount=result.get("WRONG_COUNT").toString();
				String ftth=result.get("FTTH").toString();
				String bbu=result.get("BBU").toString();
				String equ=result.get("设备").toString();
				String other=result.get("其它").toString();
				String  rate=result.get("RIGHT_RATE").toString();
				item.put("PORT_COUNT", portCount);
				item.put("WRONG_COUNT", wrongCount);
				item.put("FTTH", ftth);
				item.put("BBU", bbu);
				item.put("EQU", equ);
				item.put("OTHER", other);
				item.put("RATE", rate);	
			}
			else
			{
				item.put("PORT_COUNT", 0);
				item.put("WRONG_COUNT", 0);
				item.put("FTTH", 0);
				item.put("BBU", 0);
				item.put("EQU", 0);
				item.put("OTHER", item.get("COUNT").toString());
				item.put("RATE", 0);	
			}
		}
		
		mm.addAttribute("name", "设备巡检记录");
		mm.addAttribute("cols", cols);
		mm.addAttribute("dataList", orderList);
		mm.addAttribute("columnMethods",columnMethods);
		return "insOrderDataListExcel";
	}
	
}