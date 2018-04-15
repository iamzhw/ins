package com.cableInspection.action;

import java.io.IOException;
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

import com.cableInspection.service.ArrivalService;
import com.cableInspection.service.CoordinateService;

@Controller
@RequestMapping("Lxxj/coordinate")
public class CoordinateController extends BaseAction {
	
	@Resource
	private CoordinateService coordinateService;
	
	@Resource
	private ArrivalService arrivalService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> rs = coordinateService.index(request);
		return new ModelAndView("cableinspection/coordinate/index", rs);
	}
	
	@RequestMapping("query")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = coordinateService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping("getDetail")
	public ModelAndView getDetail(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> rs = coordinateService.getDetail(request);
		return new ModelAndView("cableinspection/coordinate/detail", rs);
	}
	
	@RequestMapping("update")
	@ResponseBody
	public void update(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
			coordinateService.update(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(coordinateService.update(request));
	}
	
	@RequestMapping("getPoints")
	@ResponseBody
	public void getPoints(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = coordinateService.getPoints(request);
		response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
	}
	@RequestMapping("deletePoints")
	@ResponseBody
	public void deletePoints(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		coordinateService.deletePoints(request);
		response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("1");
	}
	@RequestMapping(value = "/exportPoints.do")
	public String exportPoints(HttpServletRequest request, ModelMap mm) {
		List<Map<String, Object>> mapList = coordinateService.exportPointsList(request);
		String[] cols = new String[] { "AREA_NAME", "SON_AREA_NAME", "EQUIP_CODE",
				"EQUIP_NAME", "EQUIPMENT_TYPE_NAME", "LONGITUDE", "LATITUDE","INSPECTOR",
				"CREATE_TIME","TYPENAME","COMMITNAME","COMMIT_DATE"};
		String[] colsName = new String[] { "地区", "区域", "设备编码",
				"设备名称", "设备类型", "经度", "纬度", "上报人员",
				"上报时间", "审核状态", "审核人","审核时间" };
		mm.addAttribute("name", "采集隐患点");
		mm.addAttribute("cols", cols);
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", mapList);
		return "dataListExcel";
	}
	
	@RequestMapping("toQuerySumOfCoodPage")
	public ModelAndView toQuerySumOfCoodPage(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> rs = arrivalService.getAreaList();
		return new ModelAndView("cableinspection/coordinate/sumOfCoodPage", rs);
	}
	
	@RequestMapping("querySumOfCood")
	public void querySumOfCood(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = coordinateService.querySumOfCood(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/exportSumOfCood.do")
	public String exportSumOfCood(HttpServletRequest request, ModelMap mm) {
		List<Map<String, Object>> mapList = coordinateService.exportSumOfCood(request);
		String[] cols = new String[] { "NAME", "SUM1", "SUM2",
				"SUM3", "SUM4", "SUM5", "SUM7","SUM6",
				"SUM11","SUM9","SUM12","SUM10","SUM13","SUM15","SUM16","SUM17","SUM18","SUM19","SUM20","SUM21","SUM22","SUM23","SUM8"};
		String[] colsName = new String[] { "地市", "光交", "电交",
				"人井", "电杆", "其他", "非关键点", "关键点",
				"设备点", "当月光缆线路的皮长数", "通过PC调整方式采集长度","光缆线路的总皮长数","中继光缆路由总长度","中继光缆采集完成率","非关键点","关键点","设备点","当月采集缆线长度","通过PC调整方式采集长度","采集缆线总长度","主干光缆路由总长度","主干光缆采集完成率","隐患点数"};
		mm.addAttribute("name", "采集完成率表");
		mm.addAttribute("cols", cols);
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", mapList);
		return "dataListExcel";
	}
}
