package com.cableInspection.action;

import icom.util.BaseServletTool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableInspection.service.ArrivalService;

@RequestMapping(value = "/ArrivalRate")
@SuppressWarnings("all")
@Controller
public class ArrivalRateController extends BaseAction {
	@Resource
	private ArrivalService arrivalService;

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = arrivalService.index(request);
		return new ModelAndView("cableinspection/arrival/arrival_index", map);
	}

	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = arrivalService.query(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/export.do")
	public String export(HttpServletRequest request, ModelMap mm) {
		List<Map> dataList = arrivalService.queryExl(request);
		String[] cols = new String[] { "巡线员", "区域", "任务计划", "时间段", "计划巡线点数",
				"实际巡线点数", "巡线到位率", "计划关键点数 ", "实际到位关键点数", "关键点到位率", "巡线里程(公里数)","巡线时长","上报隐患点数" };
		mm.addAttribute("name", "日常巡检到位率");
		mm.addAttribute("cols", cols);
		mm.addAttribute("dataList", dataList);
		return "arrivalDataListExcel";
	}

	@RequestMapping("trouble_index")
	public ModelAndView indexTrouble(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = arrivalService.indexTrouble(request);
		return new ModelAndView("cableinspection/arrival/trouble_index", rs);
	}

	@RequestMapping("trouble_query")
	public void queryTrouble(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = arrivalService.queryTrouble(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/trouble_export.do")
	public String exportTrouble(HttpServletRequest request, ModelMap mm) {
		List<Map> dataList = arrivalService.queryTrouble(request);
		String[] cols = new String[] { "巡检员", "区域", "检查时间", "计划隐患点数", "实际隐患点数",
				"检查到位率" };
		mm.addAttribute("name", "检查到位率");
		mm.addAttribute("cols", cols);
		mm.addAttribute("dataList", dataList);
		return "troubleArrivalDataListExcel";
	}

	@RequestMapping("keep_index")
	public ModelAndView indexKeep(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = arrivalService.indexKeep(request);
		return new ModelAndView("cableinspection/arrival/keep_index", rs);
	}

	@RequestMapping("keep_query")
	public void queryKeep(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = arrivalService.queryKeep(request, pager);
		write(response, map);
	}
	
	@RequestMapping("keep_query_photo")
	public ModelAndView keepQueryPhoto(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map> list = arrivalService.getPhotoByKeepPlanId(request);
		Map map = new HashMap();
		map.put("photo",list);
		return new ModelAndView("cableinspection/arrival/keep_info", map);
	}

	@RequestMapping("keep_export")
	public String exportKeep(HttpServletRequest request, ModelMap mm) {
		List<Map> dataList = arrivalService.queryKeep(request);
		String[] cols = new String[] { "地区", "区域", "看护员", "外力点名称", "看护开始日期",
				"看护结束日期", "时间段", "计划看护时间", "实际看护时间", "离开次数", "完成百分比" };
		mm.addAttribute("name", "看护到位率");
		mm.addAttribute("cols", cols);
		mm.addAttribute("dataList", dataList);
		return "keepArrivalDataListExcel";
	}

	/**
	 * 查看任务所有关键点到位率情况
	 */
	@RequestMapping("/keyPointsArrivaledStatistics.do")
	public ModelAndView keyPointsArrivaledStatistics(
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rs = new HashMap<String, Object>();

		List<Map<String, Object>> keyPointsArrivaledList = arrivalService
				.keyPointsArrivaledStatistics(request);

		rs.put("keyPointsArrivaledList", keyPointsArrivaledList);

		return new ModelAndView("cableinspection/arrival/keyPointArrivaled", rs);
	}

	/**
	 * 巡检关键点签到报表
	 */
	@RequestMapping("/exportKeyPointsArrivaledStatistics.do")
	public String exportKeyPointsArrivaledStatistics(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {

		List<Map<String, Object>> dataList = arrivalService
				.keyPointsArrivaledStatistics(request);

		String[] cols = new String[] { "TASK_NAME", "PLAN_START_TIME",
				"PLAN_END_TIME", "TIMES", "AREANAME", "LINE_NAME",
				"POINT_NAME", "ISARRIVALED", "CREATE_TIME", "STAFF_NAME","STAFF_NO", "STAFF_TEL","DEPT_NO","DEPT_NAME"};
		String[] colsName = new String[] { "任务名称", "任务开始时间", "任务结束时间", "时间段",
				"区域", "缆线名称", "关键点名称", "是否已签到", "签到时间", "签到人","签到人工号","签到人电话",
				"维护网格编码","维护网格名称" };
		map.addAttribute("name", "日常巡检关键点签到情况统计报表");
		map.addAttribute("cols", cols);
		map.addAttribute("colsName", colsName);
		map.addAttribute("dataList", dataList);
		return "dataListExcel";
	}
	
	@RequestMapping("/toArrivalRateDate.do")
	public ModelAndView toArrivalRateDate(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = arrivalService.getAreaList();
		return new ModelAndView("cableinspection/arrival/arrival_rate_date", rs);
	} 
	
	@RequestMapping("/queryArrivalRateByDate.do")
	public void queryArrivalRateByDate(HttpServletRequest request,HttpServletResponse response,UIPage pager) throws IOException{
		Map<String, Object> map = arrivalService.queryArrivalRateByDate(request, pager);
		write(response, map);
	}
	
	@RequestMapping("/exportArrivalRateByDate.do")
	public String exportArrivalRateByDate(HttpServletRequest request, HttpServletResponse response,
			ModelMap map){
		List<Map> dataList=arrivalService.exportArrivalRateByDate(request);
		
		String[] cols = new String[] { "AREA","SUM1", "SUM22","SUM25","SUM2","SUM3", "SUM4", "SUM5","SUM8","SUM9",
				"SUM6","SUM7", "SUM10", "SUM18", "SUM17","SUM20", "SUM23", "SUM24", "SUM19", "SUM11","SUM26", "SUM12",
				"SUM14", "SUM21", "SUM15", "SUM13", "SUM16"};

		String[] colsName = new String[] { "地市", "新增人员", "巡线员人数",
				"参与巡线人数", "新增缆线", "巡检任务数", "日常巡检任务数", "关键点总数", "有维护等级的关键点数",
				"已派发有维护等级的关键点数", "已派发关键点数", "应派发的关键点数", "派发率", "应签到次数",
				"已派发巡检任务应签到次数", "缆线任务实际签到次数", "巡检任务签到率", "巡检覆盖率", "实际签到关键点数",
				"隐患工单数", "已归档隐患工单数", "新增隐患点数", "检查任务数", "看护员人数", "看护任务数",
				"新增看护点数", "新增坐标采集数" };
		map.addAttribute("name", "分公司缆线巡检使用情况");
		map.addAttribute("cols", cols);
		map.addAttribute("colsName", colsName);
		map.addAttribute("dataList", dataList);
		return "dataListExcel";
	}
	
	@RequestMapping("getSonAreaById")
	@ResponseBody
	public void getSonAreaById(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = arrivalService.getSonAreaById(request);
		write(response, map);
	}
}
