package com.axxreport.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import oracle.net.aso.a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.axxreport.service.XxdReportService;
import com.linePatrol.service.CutAndConnOfFiberService;
import com.linePatrol.service.LineInfoService;
import com.linePatrol.service.StepPartTaskService;
import com.linePatrol.service.gldManageService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.constant.RoleNo;
import com.system.service.GeneralService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/xxdReportController")
@Controller
public class XxdReportController extends BaseAction{

	@Resource
	private LineInfoService lineInfoService;
	@Resource
	private XxdReportService xxdReportService;
	@Resource
	private GeneralService generalService;
	@Resource
	private gldManageService gldManageService;
	@Resource
	private StepPartTaskService stepparttaskservice;
	 @Autowired
    private CutAndConnOfFiberService cutAndConnOfFiberService;
	/*
	 * 所有点 计划完情况
	 */
	@RequestMapping(value = "/siteIndex.do")
	public ModelAndView siteIndex(HttpServletRequest request, HttpServletResponse response) {

		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId = lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff = lineInfoService.getLocalPerson(localId, orgId);
		map.put("localInspactStaff", localInspactStaff);
		return new ModelAndView("/axxreport/siteReport_index", map);
	}

	@RequestMapping(value = "/getSiteXxdComplete.do")
	public void getSiteXxdComplete(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		Map<String, Object> siteXxdCompleteList = xxdReportService
				.getSiteXxdComplete(para, pager);
	

		try {
			response.getWriter().write(
					JSONObject.fromObject(siteXxdCompleteList).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/xxdDownload.do")
	public void xxdDownload(HttpServletRequest request,
			HttpServletResponse response, UIPage pager)throws Exception {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.xxdDownload(para, request, response);
	}

	/*
	 * 关键点
	 */
	@RequestMapping(value = "/keysiteReport_index.do")
	public ModelAndView keysiteReport_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
		map.put("localInspactStaff", localInspactStaff);
		return new ModelAndView("/axxreport/keysiteReport_index", map);
	}

	@RequestMapping(value = "/getKeysiteComplete.do")
	public void getKeysiteComplete(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		Map<String, Object> keysiteCompleteList = xxdReportService
				.getKeysiteComplete(para);

		try {
			response.getWriter().write(
					JSONObject.fromObject(keysiteCompleteList).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 省关键点到位率数据源
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProvinceKeysiteComplete.do")
	public void getProvinceKeysiteComplete(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> keysiteCompleteList = xxdReportService.getProvinceKeysiteComplete(para);

		try {
			response.getWriter().write(
					JSONObject.fromObject(keysiteCompleteList).toString());
//			write(response, keysiteCompleteList);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/keysiteDownload.do")
	public void keysiteDownload(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.keysiteDownload(para, request, response);
	}

	/*
	 * 关键点到位率
	 */
	@RequestMapping(value = "/keysiteArrate_index.do")
	public ModelAndView keysiteArrate_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("area_id", localId);
		List<Map<String, Object>> orgList=xxdReportService.getScopeList(para);
		map.put("localInspactStaff", localInspactStaff);
		map.put("orgList", orgList);
		return new ModelAndView("/axxreport/keysiteArrivalRate_index", map);
	}

	/*
	 * 省关键点到位率页面跳转
	 */
	@RequestMapping(value = "/provinceKeysiteArrate_index.do")
	public ModelAndView provinceKeysiteArrate_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/provinceKeysiteReport_index", map);
	}
	
	@RequestMapping(value = "/getKeysiteArrate.do")
	public void getKeysiteArrate(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		Map<String, Object> keysiteArrateList = xxdReportService
				.getKeysiteArrate(para);

		try {
			response.getWriter().write(
					JSONObject.fromObject(keysiteArrateList).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/keysiteArDownload.do")
	public void keysiteArDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.keysiteArDownload(para, request, response);
	}

	/*
	 * 所有巡线点到位率
	 */
	@RequestMapping(value = "/inspectArrivalRate_index.do")
	public ModelAndView inspectArrivalRate_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("area_id", localId);
		List<Map<String, Object>> orgList = xxdReportService.getScopeList(para);
		map.put("localInspactStaff", localInspactStaff);
		map.put("orgList", orgList);
		return new ModelAndView("/axxreport/inspectArrivalRate_index", map);
	}

	@RequestMapping(value = "/getInspectAr.do")
	public void getInspectAr(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		Map<String, Object> inspectAr = xxdReportService.getInspectAr(para);
		try {
			response.getWriter().write(
					JSONObject.fromObject(inspectAr).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 全省到位率统计报表导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProvinceKeysiteCompleteDown.do")
	public void getProvinceKeysiteCompleteDown(HttpServletRequest request,
			HttpServletResponse response) { 
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.getProvinceKeysiteCompleteDown(para, request, response);
	}

	@RequestMapping(value = "/inspectArDownload.do")
	public void inspectArDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.inspectArDownload(para, request, response);
	}

	/*
	 * 巡线时长
	 */
	@RequestMapping(value = "/inspectTime_index.do")
	public ModelAndView inspectTime_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("area_id", localId);
		List<Map<String, Object>> orgList=xxdReportService.getScopeList(para); 
		map.put("orgList", orgList);
		map.put("localInspactStaff", localInspactStaff);
		return new ModelAndView("/axxreport/inspectTime_index", map);
	}

	@RequestMapping(value = "/getInspectTime.do")
	public void getInspectTime(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		Map<String, Object> inspectTime = xxdReportService.getInspectTime(para);

		try {
			response.getWriter().write(
					JSONObject.fromObject(inspectTime).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	/*
	 * 多天巡线时长
	 */
	@RequestMapping(value = "/inspectAllTime_index.do")
	public ModelAndView inspectAllTime_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("area_id", localId);
		List<Map<String, Object>> orgList = xxdReportService.getScopeList(para);
		map.put("localInspactStaff", localInspactStaff);
		map.put("orgList", orgList);
		return new ModelAndView("/axxreport/inspectAllTime_index", map);
	}

	@RequestMapping(value = "/getInspectAllTime.do")
	public void getInspectAllTime(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		Map<String, Object> inspectTime = xxdReportService.getInspectAllTime(para);

		try {
			response.getWriter().write(
					JSONObject.fromObject(inspectTime).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	@RequestMapping(value = "/inspectTimeDownload.do")
	public void inspectTimeDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.inspectTimeDownload(para, request, response);
	}
	
	@RequestMapping(value = "/inspectAllTimeDownload.do")
	public void inspectAllTimeDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.inspectAllTimeDownload(para, request, response);
	}

	/*
	 * 外力看护报表，页面
	 */
	@RequestMapping(value = "/osNurseDaily_index.do")
	public ModelAndView osNurseDaily_index(HttpServletRequest request, HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();

		// 本地看护员
		String localId = StaffUtil.getStaffAreaId(request);
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("area_id", localId);
		para.put("role", "AXX_KHY");
		List<Map<String, Object>> localKhyStaff = generalService.getStaffByRole(para);
		List<Map<String, Object>> scopeList =  xxdReportService.getScopeList(para);
		map.put("scopeList", scopeList);
		map.put("localKhyStaff", localKhyStaff);
		return new ModelAndView("/axxreport/outsiteNurseDaily_index", map);
	}

	/**
	 * 外力看护报表，查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOsNurseDaily.do")
	@ResponseBody
	public List<Map<String, Object>> getOsNurseDaily(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		List<Map<String, Object>> osNurseDailyData = xxdReportService.getOsNurseDaily(para);

		return osNurseDailyData;

	}

	/**
	 * 无效时长报表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/invalidTimeIndex.do")
	public ModelAndView invalidTimeIndex(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
		map.put("localInspactStaff", localInspactStaff);
		return new ModelAndView("/axxreport/invalidTime_index", map);
	}

	@RequestMapping(value = "/osNurseDailyDownload.do")
	public void osNurseDailyDownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> para = MapUtil.parameterMapToCommonMapForChinese(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.osNurseDailyDownload(para, request, response);
	}
	/**
	 * 无效时长报表
	 * @Title: getInvalidTime
	 * @Description:
	 * @author wangxiangyu
	 */
	@RequestMapping(value = "/getInvalidTime.do")
	public void getInvalidTime(HttpServletRequest request, HttpServletResponse response, UIPage pager) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			para.put("area_id", StaffUtil.getStaffAreaId(request));
			Map<String, Object> siteXxdCompleteList = xxdReportService.getInvalidTime(para, pager);
			response.getWriter().write(JSONObject.fromObject(siteXxdCompleteList).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 巡线时长周报
	 */
	@RequestMapping(value = "/inspectTimeDaily_index.do")
	public ModelAndView inspectTimeDaily_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();

		return new ModelAndView("/axxreport/inspectTimeDaily_index", map);
	}

	@RequestMapping(value = "/getInspectTimeDaily.do")
	public void getInspectTimeDaily(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> inspectTimeDaily = xxdReportService
				.getInspectTimeDaily(para);

		try {
			response.getWriter().write(
					JSONObject.fromObject(inspectTimeDaily).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/inspectTimeDailyDownload.do")
	public void inspectTimeDailyDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

		xxdReportService.inspectTimeDailyDownload(para, request, response);
	}

	
	/**
	 * 巡线员巡检质量分析页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/qualityReportByPeopleInit.do")
	public ModelAndView qualityReportByPeopleInit(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("area_id", localId);
		List<Map<String, Object>> orgList=xxdReportService.getScopeList(para);
		map.put("localInspactStaff", localInspactStaff);
		map.put("orgList", orgList);
		return new ModelAndView("/axxreport/qualityReportByPeople", map);
	}
	
	@RequestMapping(value = "/qualityReportByPeople.do")
	@ResponseBody
	public List<Map<String, Object>> qualityReportByPeople(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		List<Map<String, Object>> list = xxdReportService.qualityReportByPeople(para);
	    return list;
	}
	
	@RequestMapping(value = "/qualityReportByPeopleDownload.do")
	public void qualityReportByPeopleDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.qualityReportByPeopleDownload(para, request, response);
	}
	
	/**
	 * 全省质量分析
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/qualityReportByAreaInit.do")
	public ModelAndView qualityReportByAreaInit(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/qualityReportByArea", map);
	}
	/**
	 * 地市质量分析报表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/qualityReportByArea.do")
	@ResponseBody
	public List<Map<String, Object>> qualityReportByArea(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("localId", localId);
		List<Map<String, Object>> list = xxdReportService.qualityReportByArea(para);
	    return list;
	}
	
	@RequestMapping(value = "/qualityReportByAreaDownload.do")
	public void qualityReportByAreaDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("localId", localId);
		xxdReportService.qualityReportByAreaDownload(para, request, response);
	}
	
	@RequestMapping(value = "/showDetailInfo.do")
	public ModelAndView showDetailInfo(HttpServletRequest request,String plan_id,String start_query_time,String end_query_time,
			HttpServletResponse response,ModelMap model) {
		model.addAttribute("plan_id", plan_id);
		model.addAttribute("start_query_time", start_query_time);
		model.addAttribute("end_query_time", end_query_time);
		return new ModelAndView("/axxreport/showDetailInfo",model);
	}
	
	@RequestMapping(value = "/detailInfo.do")
	@ResponseBody
	public List<Map<String, Object>> detailInfo(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		List<Map<String, Object>> resultList =  xxdReportService.detailInfo(para);
		return resultList;
	}
	
	@RequestMapping(value = "/qualityReportByscopeInit.do")
	public ModelAndView qualityReportByscopeInit(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		Map<String, Object> para = new HashMap<String, Object>();
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		List<Map<String, Object>> scopeList = xxdReportService.getScopeList(para);
		model.put("scopeList", scopeList);
		return new ModelAndView("/axxreport/qualityReportByScope", model);
	}
	/**
	 * 区域质量分析
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/qualityReportByscope.do")
	@ResponseBody
	public List<Map<String, Object>> qualityReportByscope(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		List<Map<String, Object>> resultList =  xxdReportService.qualityReportByscope(para);
		return resultList;
	}
	
	@RequestMapping(value = "/qualityReportByScopeDownload.do")
	public void qualityReportByScopeDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		xxdReportService.qualityReportByScopeDownload(para, request, response);
	}
	
	/**
	 * 各地市领导检查外力点信息报表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkOutSiteInitByLeader.do")
	public ModelAndView checkOutSiteInitByLeader(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) {
		Map<String, Object> para=new HashMap<String, Object>();
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		List<Map<String, Object>> scopeList =  xxdReportService.getScopeList(para);
		model.put("scopeList", scopeList);
		model.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/checkOutSiteInitByLeader",model);
	}
	
	
	/**
	 * 领导检查外力点信息数据源
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkOutSiteByLeader.do")
	@ResponseBody
	public List<Map<String, Object>> checkOutSiteByLeader(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> resultList =  xxdReportService.checkOutSiteByLeader(para);
		return resultList;
	}
	
	@RequestMapping(value = "/checkOutSiteByLeaderDownload.do")
	public void checkOutSiteByLeaderDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.checkOutSiteByLeaderDownload(para, request, response);
	}
	
	
	/**
	 * 全省各地市领导检查外力点报表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkOutSiteByAreaInit.do")
	public ModelAndView checkOutSiteByAreaInit(HttpServletRequest request, HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/outSiteCheckByArea", map);
	}
	
	/**
	 * 全省各地市领导检查外力点报表数据源
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkOutSiteByArea.do")
	@ResponseBody
	public List<Map<String, Object>> checkOutSiteByArea(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> resultList =  xxdReportService.checkOutSiteByArea(para);
		return resultList;
	}
	
	/**
	 * 全省外力点检查统计报表导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkOutSiteByAreaDown.do")
	public void checkOutSiteByAreaDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.checkOutSiteByAreaDown(para, request, response);
	}
	
	
	/*
	 * 省外力点看护到位率页面跳转
	 */
	@RequestMapping(value = "/provinceOutsiteArrate_index.do")
	public ModelAndView provinceOutsiteArrate_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/provinceOutsiteReport_index", map);
	}
	
	/**
	 * 省外力点看护到位率数据源
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProvinceOutsiteComplete.do")
	public void getProvinceOutsiteComplete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> outSiteCompleteList = xxdReportService
				.getProvinceOutSiteComplete(para);

		try {
			response.getWriter().write(
					JSONObject.fromObject(outSiteCompleteList).toString());
//			write(response, keysiteCompleteList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 全省外力点看护到位率统计报表导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProvinceOutSiteCompleteDown.do")
	public void getProvinceOutSiteCompleteDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.getProvinceOutSiteCompleteDown(para, request, response);
	}
	
	
	/**
	 * 省外力点埋深探位页面跳转 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/provinceCheckArrate_index.do")
	public ModelAndView provinceCheckArrate_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/provinceCheckArrate_index", map);
	}
	
	/**
	 * 省外力点埋深探位数据源
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProvinceCheckComplete.do")
	@ResponseBody
	public List<Map<String, Object>> getProvinceCheckComplete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> checkCompleteList = xxdReportService
				.getProvinceCheckComplete(para);
		return checkCompleteList;
	}
	
	/**
	 * 全省外力点探位统计报表导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProvinceCheckCompleteDown.do")
	public void getProvinceCheckCompleteDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.getProvinceCheckCompleteDown(para, request, response);
	}
	
	/**
	 * 通过area_id后去区域信息
	 */
	@RequestMapping(value = "/getScopeList.do")
	@ResponseBody
	public List<Map<String, Object>> getScopeList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> orgList =  xxdReportService.getScopeList(para);
		return orgList;
	}
	
	
	
	/**
	 * 全省隐患单统计页面跳转
	 */
	@RequestMapping(value = "/provinceDangerOrder_index.do")
	public ModelAndView provinceDangerOrder_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/provinceDangerOrder_index", map);
	}
	/**
	 * 全省隐患单统计页面
	 */
	@RequestMapping(value = "/getProvinceDangerOrder.do")
	@ResponseBody
	public List<Map<String, Object>> getProvinceDangerOrder(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> ProvinceDangerOrderList = xxdReportService
				.getProvinceDangerOrder(para);
		return ProvinceDangerOrderList;
	}
	
	/**
	 * 全省隐患单统计页面导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProvinceDangerOrderDown.do")
	public void getProvinceDangerOrderDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.getProvinceDangerOrderDown(para, request, response);
	}
	/**
	 * 分公司各类型统计数量表页面跳转
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/frefectureRoutingFacility_index.do")
	public ModelAndView frefectureRoutingFacility_index(
			HttpServletRequest request, HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/frefectureRoutingFacility_index",
				map);
	}

	/**
	 * 分公司各类型统计数量表
	 */
	@RequestMapping(value = "/frefectureRoutingFacility.do")
	@ResponseBody
	public List<Map<String, Object>> frefectureRoutingFacility(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> routingFacilityList = xxdReportService
				.getFrefectureRoutingFacility(para);
		return routingFacilityList;
	}

	/**
	 * 分公司各类型统计数量表导出
	 */
	@RequestMapping(value = "/frefectureRoutingFacilityDown.do")
	@ResponseBody
	public void frefectureRoutingFacilityDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.frefectureRoutingFacilityDown(para, request, response);

	}

	/**
	 * 市设施总体情况统计表页面跳转
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/cityRoutingFacility_index.do")
	public ModelAndView cityRoutingFacility_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		// Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		map.put("area_id", StaffUtil.getStaffAreaId(request));
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/cityRoutingFacility_index", map);
	}

	/**
	 * 市设施总体情况统计表
	 */
	@RequestMapping(value = "/getCityRoutingFacility.do")
	@ResponseBody
	public List<Map<String, Object>> getCityRoutingFacility(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		List<Map<String, Object>> routingCityList = xxdReportService
				.getCityRoutingFacility(para);
		return routingCityList;
	}

	/**
	 * 市设施总体情况统计表导出
	 */
	@RequestMapping(value = "/getCityRoutingFacilityDown.do")
	@ResponseBody
	public void getCityRoutingFacilityDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.getCityRoutingFacilityDown(para, request, response);

	}

	/**
	 * 省设施总体情况统计表页面跳转
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/provinceRoutingFacility_index.do")
	public ModelAndView provinceRoutingFacility_index(
			HttpServletRequest request, HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/provinceRoutingFacility_index", map);
	}

	/**
	 * 省设施总体情况统计表
	 */
	@RequestMapping(value = "/getProvinceRoutingFacility.do")
	@ResponseBody
	public List<Map<String, Object>> getProvinceRoutingFacility(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> routingFacilityList = xxdReportService
				.getProvinceRoutingFacility(para);
		return routingFacilityList;
	}

	/**
	 * 省设施总体情况统计表导出
	 */
	@RequestMapping(value = "/getProvinceRoutingFacilityDown.do")
	@ResponseBody
	public void getProvinceRoutingFacilityDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService
				.getProvinceRoutingFacilityDown(para, request, response);

	}

	/**
	 * 按光缆和中继段查询设施各类型数量页面跳转
	 */
	@RequestMapping(value = "/cableRoutingFacility_index.do")
	public ModelAndView cableRoutingFacility_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> areaList = generalService.getAreaList();
		map.put("areaList", areaList);
		map.put("area", xxdReportService.selArea());
		List<Map<String, Object>> cableList = gldManageService
				.getGldByAreaId(StaffUtil.getStaffAreaId(request));
		map.put("cableList", cableList);
		return new ModelAndView("/axxreport/cableRoutingFacility_index", map);
	}

	/**
	 * 按光缆和中继段查询设施各类型数量页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCableRoutingFacility.do")
	@ResponseBody
	public List<Map<String, Object>> getCableRoutingFacility(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		map.put("localId", StaffUtil.getStaffAreaId(request));
		List<Map<String, Object>> routingFacilityList = xxdReportService
				.getCableRoutingFacility(map);
		return routingFacilityList;
	}

	/**
	 * 按光缆和中继段查询设施各类型数量页面导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCableRoutingFacilityDown.do")
	public void getCableRoutingFacilityDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		map.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.getCableRoutingFacilityDown(map, request, response);
	}

	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param area_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDetailUI_index.do")
	public ModelAndView getDetailUI_index(HttpServletRequest request,
			HttpServletResponse response, String area_id) {
		// 准备数据
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
	
		//map.put("area_id",area_id);
		//List<Map<String, String>> areaList = generalService.getAreaList();
		//map.put("areaList", areaList);
		//map.put("area", xxdReportService.selArea());
		String staffAreaId=map.get("area_id").toString();
		List<Map<String, Object>> cableList = gldManageService
				.getGldByAreaId(staffAreaId);
		map.put("cableList", cableList);
		return new ModelAndView("/axxreport/getDetailUI_index", map);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDetailUI.do")
	@ResponseBody
	public List<Map<String, Object>> getDetailUI(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> getDetailUIList =  xxdReportService
		.getDetailUI(map);
		return getDetailUIList;		
	}
	
	/**
	 * 
	 * 设施密度页面跳转
	 * 
	 */
	@RequestMapping(value = "/getFacilityDensity_index.do")
	public ModelAndView getFacilityDensity_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> areaList = generalService.getAreaList();
		map.put("area", xxdReportService.selArea());
		map.put("areaList", areaList);
		return new ModelAndView("/axxreport/getFacilityDensity_index", map);
	}

	/**
	 * 设施密度统计页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping(value = "/getFacilityDensity.do")
	@ResponseBody
	public List<Map<String, Object>> getFacilityDensity(
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> getFacilityDensityList =  xxdReportService
					.getFacilityDensity(para);
		return getFacilityDensityList;
	}
	
	
	/**
	 * 设施密度统计页面导出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFacilityDensityDown.do")
	@ResponseBody
	public void getFacilityDensityDown(
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.getFacilityDensityDown(para, request, response);
	}
	
	
	/**
	 * 步巡检查情况页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStepTourCondition_index.do")
	public ModelAndView getStepTourCondition_index(HttpServletRequest request,
			HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 本地巡线员
		String localId = StaffUtil.getStaffAreaId(request);
		String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
		List<Map<String, Object>> timeList=lineInfoService.getTimeList();
		//获取当前周期时间
		List<String> temp=new ArrayList<String>(3);
		for (int i = 0; i < timeList.size(); i++) {
     	map.put("startDate" + i,timeList.get(i).get("START_TIME").toString());
     	map.put("endDate" + i,timeList.get(i).get("END_TIME").toString());
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/axxreport/getStepTourCondition_index");
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("area_id", localId);
		List<Map<String, Object>> orgList = xxdReportService.getScopeList(para);
		map.put("localInspactStaff", localInspactStaff);
		map.put("orgList", orgList);
		map.put("timelist", temp);
		mav.addObject("map", map);
		return mav;
	}
	
	
	
	/**
	 * 全省步巡检查情况页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getProvinceStepTourCondition_index.do")
	public ModelAndView getProvinceStepTourCondition_index(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) {
		Map<String, Object> para=new HashMap<String, Object>();
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		List<Map<String, Object>> scopeList =  xxdReportService.getScopeList(para);
		model.put("scopeList", scopeList);
		model.put("area", xxdReportService.selArea());
		return new ModelAndView("/axxreport/provinceStepTourCondition", model);
	}
	
	
	/**
	 * 步巡检查情况页面-全量
	 * @param request
	 * @param resqonse
	 * @return
	 */
	@RequestMapping(value="/getStepTourCondition.do")
	@ResponseBody
	public List<Map<String, Object>> getStepTourCondition(HttpServletRequest request,HttpServletResponse resqonse ){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		List<Map<String, Object>> getStepTourConditionList =  xxdReportService
					.getStepTourCondition(para);
		return getStepTourConditionList;
	}
	
	
	/**
	 * 步巡检查情况页面-单设施
	 * @param request
	 * @param resqonse
	 * @return
	 */
	@RequestMapping(value="/getStepTypeTourCondition.do")
	@ResponseBody
	public  List<Map<String, Object>> getStepTypeTourCondition(HttpServletRequest request,HttpServletResponse resqonse ){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		List<Map<String, Object>> getStepTourConditionList =  xxdReportService
					.getStepTypeTourCondition(para);
		return getStepTourConditionList;
	}
	
	/**
	 * 全省步巡检查情况页面
	 * @param request
	 * @param resqonse
	 * @return
	 */
	@RequestMapping(value="/getProvinceStepTourCondition.do")
	@ResponseBody
	public List<Map<String, Object>> getProvinceStepTourCondition(HttpServletRequest request,HttpServletResponse resqonse ){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> getStepTourConditionList =  xxdReportService
					.getProvinceStepTourCondition(para);
		return getStepTourConditionList;
	}
	
	
	/**
	 * 全省步巡检查情况页面导出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/getProvinceStepTourConditionDown.do")
	@ResponseBody
	public void getProvinceStepTourConditionDown(
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		xxdReportService.getProvinceStepTourConditionDown(para, request, response);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param staff_id
	 * @return
	 */
	@RequestMapping(value="/getStepDetailUI_index.do")
	public ModelAndView getStepDetailUI_index(HttpServletRequest request,String staff_id,
			HttpServletResponse response,ModelMap model){
		Map<String, Object> map = new HashMap<String, Object>();
		//查询这个人底下的所有未过期的任务
		List<Map<String, Object>> effectiveTasks = stepparttaskservice.selEffectiveTaskByStaffID(staff_id);
		map.put("effectiveTasks", effectiveTasks);
		//查询有多少种设施类型
		List<Map<String, Object>> facTypes = stepparttaskservice.selFacType();
		map.put("facTypes", facTypes);
		map.put("staff_id", staff_id);
		return new ModelAndView("/axxreport/getStepDetailUI_index",map);
		
	}
	
	/**
	 * 步巡检查情况页面导出---全量
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/getStepTourConditionDown.do")
	@ResponseBody
	public void getStepTourConditionDown(
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.getStepTourConditionDown(para, request, response);
	}
	
	/**
	 * 步巡检查情况页面导出---单设施
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/getStepTypeTourConditionDown.do")
	@ResponseBody
	public void getStepTypeTourConditionDown(
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.getStepTypeTourConditionDown(para, request, response);
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param resqonse
	 * @return
	 */
	@RequestMapping(value="/getStepDetailUI.do")
	@ResponseBody
	public void getStepDetailUI(HttpServletRequest request, HttpServletResponse response){
		List<Map<String, Object>> allrecords=new ArrayList<Map<String,Object>>();
		Map<String, Object> parammap = new HashMap<String, Object>();
		String staff_id = request.getParameter("staff_id");
		String equip_type_id = request.getParameter("equip_type_id");
		String task_id = request.getParameter("task_id");
		if(task_id != "" && task_id != null ){
			parammap.put("task_id", task_id);
			//获取当前开悟的开始时间和结束时间
			List<Map<String,Object>> listTime=stepparttaskservice.getTaskTime(parammap);
			//取出开始时间和结束时间
			parammap.put("start_time", listTime.get(0).get("START_TIME")); 
			parammap.put("end_time", listTime.get(0).get("END_TIME"));
		}
		parammap.put("staff_id", staff_id);
		parammap.put("equip_type_id", equip_type_id);
		try {
			//根据设施类型查询出它对应的错误类型,for循环遍历去查询点评表中的值
		    List errorTypes = stepparttaskservice.selErrorTypesByTypeID(equip_type_id);
		    errorTypes.add(0,"全部");
		    errorTypes.add(1,"已完成");
		    errorTypes.add(2,"问题数");
		    errorTypes.add(3,"其他问题");
		    Map<String,Object> resultCount = stepparttaskservice.selErrorNumber(parammap);
		    for(int i=0;i<errorTypes.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
		    	map.put("PropertyName",errorTypes.get(i).toString());
		    	map.put("PropertyValue",resultCount.get(("TEST"+(i+1))));
		    	allrecords.add(map);
		    }
		    JSONArray jsonArray = JSONArray.fromObject(allrecords);
		    response.getWriter().write(jsonArray.toString());

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/equipDiatanceDelete.do")
	public void equipDiatanceDelete(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			xxdReportService.equipDiatanceDelete(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	@RequestMapping(value = "/equipDiatanceCancel.do")
	public void equipDiatanceCancel(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			xxdReportService.equipDiatanceCancel(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 子类型设施统计
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getVariousStepType_index.do")
	public ModelAndView getVariousStepType_index(HttpServletRequest request ,HttpServletResponse response){
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> areaList = generalService.getAreaList();
		map.put("areaList", areaList);
		map.put("area", xxdReportService.selArea());
		List<Map<String, Object>> cableList = gldManageService
				.getGldByAreaId(StaffUtil.getStaffAreaId(request));
		map.put("cableList", cableList);
		return new  ModelAndView("/axxreport/getVariousStepType_index",map);
		
	}
	
	
	@RequestMapping(value="/getVariousStepType.do")
	@ResponseBody
	public List<Map<String, Object>>  getVariousStepType (HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String type=(String) para.get("equip_type");
		para.put("localId", StaffUtil.getStaffAreaId(request));
		List<Map<String ,Object>> getStepDetailUIList=xxdReportService.getVariousStepType(para);
		return getStepDetailUIList;	
	}
	
	
	@RequestMapping(value="/getVariousStepTypeDown.do")
	public void getVariousStepTypeDown(
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		xxdReportService.getVariousStepTypeDown(para, request, response);
	}
	
	@RequestMapping(value = "/getAllGroup.do")
    @ResponseBody
    public List<Map<String, Object>> getAllGroup(HttpServletRequest request, HttpServletResponse response,String areaId) {
    	return xxdReportService.getAllGroup(areaId);
    }
	
	 @RequestMapping(value = "/query.do")
	    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
		    throws IOException {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

//		String area_id = StaffUtil.getStaffAreaId(request);
//		para.put("area_id", area_id);

		Map<String, Object> map = xxdReportService.query(para, pager);
		write(response, map);
	    }
	 
	 @RequestMapping(value = "/problemStepTour_index.do")
	    public ModelAndView index(HttpServletRequest request, HttpServletResponse response,ModelMap model) {

		// 准备数据

		Map<String, Object> map = new HashMap<String, Object>();
		String localId=null;
		if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
		model.addAttribute("localId",localId);
		return new ModelAndView("/axxreport/qstepequip", model);
	    }
	 
	 /**
	     * 步巡设施位置在地图上显示
	     * 
	     */
	    @RequestMapping("/getStepEquipLocation.do")
	    public  ModelAndView getOuteSiteLocation(HttpServletRequest request, HttpServletResponse response,
	    		String equip_id) {
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("equip_id", equip_id);
	    	List<Map<String, Object>> list=xxdReportService.getStepEquipLocation(map);
	    	map.put("list", list);
	    	return new ModelAndView("/axxreport/qstepequip_location", map);
	    	 
	        }
	    
	    @RequestMapping(value = "/stepequipdDownload.do")
		public void stepequipdDownload(HttpServletRequest request,
				HttpServletResponse response)throws Exception {
			
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			
			xxdReportService.stepequipdDownload(para, request, response);
		}
	
	    /**
	     * 全省巡线员时长详单
	     * @param request
	     * @param response
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "/provinceOfStaffDate_index.do")
	    public ModelAndView provinceOfStaffDate_index(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
	    	Map<String, Object> map = new HashMap<String, Object>();
			// 本地巡线员
			map.put("area", xxdReportService.selArea());
			return new ModelAndView("/axxreport/provinceOfStaffDate_index", map);
		//return new ModelAndView("/axxreport/ProvinceOfStaffDate", model);
	    }
	 
	    
	    @RequestMapping(value = "/getProvienceOfStaffData.do")
		@ResponseBody
		public List<Map<String, Object>> getProvienceOfStaffData(HttpServletRequest request,
				HttpServletResponse response) {
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			String localId = StaffUtil.getStaffAreaId(request);
			para.put("localId", localId);
			List<Map<String, Object>> list = xxdReportService.getProvienceOfStaffData(para, request, response);
		    return list;
		}
	    /**
	     * 全省巡线员时长详单
	     * @param request
	     * @param response
	     */
	    @RequestMapping(value = "/getProvienceOfStaffDataDown.do")
		public void getProvienceOfStaffDataDown(HttpServletRequest request,
				HttpServletResponse response) {
			Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
			para.put("localId", StaffUtil.getStaffAreaId(request));
			xxdReportService.getProvienceOfStaffDataDown(para, request, response);
		}
	
}
