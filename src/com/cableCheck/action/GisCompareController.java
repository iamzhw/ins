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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.dao.CableMyTaskDao;
import com.cableCheck.service.CableMyTaskService;
import com.cableCheck.service.GisCompareService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;

import util.page.BaseAction;
import util.page.Query;
import util.page.UIPage;

@RequestMapping(value = "/gisCompare")
@SuppressWarnings("all")
@Controller
public class GisCompareController extends BaseAction{
	
	Logger logger = Logger.getLogger(GisCompareController.class);
	
	@Resource
	private GisCompareService gisCompareService;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cablecheck/gis-index", null);
	}
	
	/**
	 * 查询带有检查人员采集设备坐标的设备
	 */
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = gisCompareService.queryGIS(request, pager);
		write(response, map);
	}

	/**
	 *导出带有检查人员采集设备坐标的设备
	 */
	@RequestMapping(value="/exportGis.do")
	public String exportExcel(HttpServletRequest request,ModelMap mm){
		List<Map<String, Object>> exportInfoList =gisCompareService.exportGIS(request);
		StringBuffer colsStr = new StringBuffer();
		colsStr.append("AREA,SON_AREA,ZHHWHWG,SBID,SBBM,SBMC,ADDRESS,RES_TYPE,LONGITUDE,LATITUDE,LONGITUDE_INSPECT,LATITUDE_INSPECT,STAFFID_INSPECTOR,STAFF_LONG_LATI_TIME");
		String[] cols = colsStr.toString().split(",");
		
		StringBuffer colsNameStr = new StringBuffer();
		colsNameStr.append("地区,区域,综合化维护网格,设备ID,设备编码,设备名称,设备地址,设备类型,设备经度,设备纬度,设备采集经度,设备采集纬度,修改人,修改时间");
		String[] colsName = colsNameStr.toString().split(",");
	
		mm.addAttribute("name", "GIS坐标校对");
		mm.addAttribute("cols", cols);
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", exportInfoList);	
		return "dataListExcel";
	
	}
	
}
