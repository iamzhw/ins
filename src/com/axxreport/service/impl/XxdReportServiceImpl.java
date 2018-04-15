/**
 * @Description: TODO
 * @date 2015-4-7
 * @param
 */
package com.axxreport.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.axxreport.dao.XxdReportDao;
import com.axxreport.service.XxdReportService;
import com.axxreport.util.ExcelUtil;
import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import com.system.dao.ParamDao;
import com.util.MapDistance;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class XxdReportServiceImpl implements XxdReportService {

	@Resource
	private XxdReportDao xxdReportDao;
	@Resource
	private ParamDao paramDao;

	@Override
	public Map<String, Object> getSiteXxdComplete(Map<String, Object> para,
			UIPage pager) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);

		List<Map> olists = xxdReportDao.getSiteXxdComplete(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public List<Map> query(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void xxdDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {

		List<String> title = Arrays.asList(new String[] { "巡线点名称", "到达时间",
				"离开时间 ", "停留时间 ", "巡线人" });
		List<String> code = Arrays.asList(new String[] { "SITE_NAME",
				"START_TIME", "END_TIME", "STAY_TIME", "INSPACT_NAME" });
		List<Map<String, Object>> data = getAllSiteXxdComplete(para);
		String fileName = "计划完成情况报表";
		String firstLine = "计划完成情况报表";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public List<Map<String, Object>> getAllSiteXxdComplete(
			Map<String, Object> para) {
		List<Map<String, Object>> list = xxdReportDao
				.getAllSiteXxdComplete(para);
		return list;
	}

	@Override
	public Map<String, Object> getKeysiteComplete(Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao
				.getKeysiteComplete(para);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Map<String, Object> getProvinceKeysiteComplete(Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao.getProvinceKeysiteComplete(para);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void keysiteDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "巡线点名称", "到达时间",
				"离开时间 ", "停留时间 ", "巡线人", "是否到位" });
		List<String> code = Arrays.asList(new String[] { "SITE_NAME",
				"START_TIME", "END_TIME", "STAY_TIME", "INSPACT_NAME",
				"IS_ARRIVE" });
		List<Map<String, Object>> data = getAllKeysiteComplete(para);
		String fileName = "关键点完成情况报表";
		String firstLine = "关键点完成情况报表";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 作用： 　　*作者：
	 * 
	 * @param para
	 * @return
	 */
	private List<Map<String, Object>> getAllKeysiteComplete(
			Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao
				.getKeysiteComplete(para);
		return olists;
	}

	@Override
	public Map<String, Object> getKeysiteArrate(Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao.getKeysiteArrate(para);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("data", olists);
		return pmap;
	}

	@Override
	public void keysiteArDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		//
		List<String> code = Arrays.asList(new String[] { "STAFF_NAME",
				"ORG_NAME", "GP1", "GA1", "GP2", "GA2", "ARRIVAL_RATE",
				"AVG_ARRIVAL_RATE" });
		List<Map<String, Object>> data = xxdReportDao.getKeysiteArrate(para);
		String fileName = para.get("start_time").toString() + "到"
				+ para.get("end_time") + "关键点到位率报表";
		String firstLine = para.get("start_time").toString() + "到"
				+ para.get("end_time") + "关键点到位率报表";
		String modalPath = request.getSession().getServletContext()
				.getRealPath("/excelFiles")
				+ File.separator + "keysiteArrivalRate.xlsx";
		int startRow = 3;
		ExcelUtil.writeToModalAndDown(code, data, request, response, fileName,
				modalPath, startRow, firstLine);

	}

	@Override
	public Map<String, Object> getInspectAr(Map<String, Object> para) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", para.get("area_id"));

		// map.put("paramName", "WorkStart");
		// String start_date_m = paramDao.getParamValueByName(map);
		// para.put("start_date_m", start_date_m);
		// map.put("paramName", "WorkEnd");
		// String end_date_m = paramDao.getParamValueByName(map);
		// para.put("end_date_m", end_date_m);
		// map.put("paramName", "WorkStart2");
		// String start_date_a = paramDao.getParamValueByName(map);
		// para.put("start_date_a", start_date_a);
		// map.put("paramName", "WorkEnd2");
		// String end_date_a = paramDao.getParamValueByName(map);
		// para.put("end_date_a", end_date_a);

		List<Map<String, Object>> olists = xxdReportDao.getInspectAr(para);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void getProvinceKeysiteCompleteDown(Map<String, Object> para, HttpServletRequest request, HttpServletResponse response) {

		List<String> code = Arrays.asList(new String[] { "NAME", "GP1", "GP2", "GA", "AVG_ARRIVAL_RATE" });
		List<Map<String, Object>> data = xxdReportDao.getProvinceKeysiteComplete(para);
		String fileName = para.get("start_time").toString() + "到" + para.get("end_time") + "全省关键点到位率报表";
		String firstLine = para.get("start_time").toString() + "到" + para.get("end_time") + "全省关键点到位率报表";
		String modalPath = request.getSession().getServletContext().getRealPath("/excelFiles") + File.separator + "provinceKeysiteRate.xlsx";
		int startRow = 3;
		ExcelUtil.writeToModalAndDown(code, data, request, response, fileName, modalPath, startRow, firstLine);
	}

	@Override
	public void inspectArDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", para.get("area_id"));
		map.put("paramName", "WorkStart");
		String start_date_m = paramDao.getParamValueByName(map);
		para.put("start_date_m", start_date_m);
		map.put("paramName", "WorkEnd");
		String end_date_m = paramDao.getParamValueByName(map);
		para.put("end_date_m", end_date_m);
		map.put("paramName", "WorkStart2");
		String start_date_a = paramDao.getParamValueByName(map);
		para.put("start_date_a", start_date_a);
		map.put("paramName", "WorkEnd2");
		String end_date_a = paramDao.getParamValueByName(map);
		para.put("end_date_a", end_date_a);
		List<String> title = Arrays.asList(new String[] { "巡线员 ", "区域", "时间段",
				"全天计划巡线点数", "全天实际巡线点数", "全天巡线到位率", "全市平均巡线到位率", "上午实际巡线点数",
				"上午巡线到位率", "下午实际巡线点数", "下午巡线到位率" });
		List<String> code = Arrays.asList(new String[] { "STAFF_NAME",
				"ORG_NAME", "TIME_PART", "PLAN_NUM", "ACTUAL_NUM",
				"ACTUAL_RATE", "AVG_ACTUAL_RATE", "MORNINGNUM",
				"MORNINGNUM_RATE", "NIGHTNUM", "NIGHTNUM_RATE" });
		List<Map<String, Object>> data = xxdReportDao.getInspectAr(para);
		String fileName = "巡检到位率报表";
		String firstLine = "巡检到位率报表";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getInspectTime(Map<String, Object> para) {
		// 查询工作时间
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("org_id", para.get("org_id"));
		// map.put("areaId", para.get("area_id"));

		// map.put("paramName", "WorkStart");
		// String start_date_m = paramDao.getParamValueByName(map);
		//
		// map.put("paramName", "WorkEnd");
		// String end_date_m = paramDao.getParamValueByName(map);
		//
		// map.put("paramName", "WorkStart2");
		// String start_date_a = paramDao.getParamValueByName(map);
		//
		// map.put("paramName", "WorkEnd2");
		// String end_date_a = paramDao.getParamValueByName(map);

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		// String start_date_t = start_date + " " + start_date_m + ":00";
		// String end_date_t = end_date + " " + end_date_a + ":00";

		// map.put("start_date_m", start_date_m);
		// map.put("end_date_m", end_date_m);
		// map.put("start_date_a", start_date_a);
		// map.put("end_date_a", end_date_a);

		map.put("start_date", start_date);
		map.put("end_date", end_date);

		// map.put("start_date_t", start_date_t);
		// map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		// String[] end_date_marr = end_date_m.split(":");
		// String[] start_date_marr = start_date_m.split(":");
		// int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
		// + Integer.parseInt(end_date_marr[1])
		// - Integer.parseInt(start_date_marr[0]) * 60
		// - Integer.parseInt(start_date_marr[1]);
		//
		// String[] end_date_aarr = end_date_a.split(":");
		// String[] start_date_aarr = start_date_a.split(":");
		// int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
		// + Integer.parseInt(end_date_aarr[1])
		// - Integer.parseInt(start_date_aarr[0]) * 60
		// - Integer.parseInt(start_date_aarr[1]);

		// map.put("total_min_m", total_min_m);
		// map.put("total_min_a", total_min_a);

		List<Map<String, Object>> olists = xxdReportDao.getInspectTime(map);

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;

	}

	@Override
	public void inspectTimeDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		// 查询工作时间
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("org_id", para.get("org_id"));
		map.put("areaId", para.get("area_id"));

		map.put("paramName", "WorkStart");
		String start_time_m = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkEnd");
		String end_time_m = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkStart2");
		String start_time_a = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkEnd2");
		String end_time_a = paramDao.getParamValueByName(map);

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		String start_date_t = start_date + " " + start_time_m + ":00";
		String end_date_t = end_date + " " + end_time_a + ":00";

		map.put("start_date_m", start_time_m);
		map.put("end_date_m", end_time_m);
		map.put("start_date_a", start_time_a);
		map.put("end_date_a", end_time_a);

		map.put("start_date", start_date);
		map.put("end_date", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		String[] end_date_marr = end_time_m.split(":");
		String[] start_date_marr = start_time_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
				+ Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60
				- Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_time_a.split(":");
		String[] start_date_aarr = start_time_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
				+ Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60
				- Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		List<Map<String, Object>> data = xxdReportDao.getInspectTime(map);
		//
		List<String> title = Arrays.asList(new String[] { "姓名", "区域",
				"全天巡线时长(小时)", "上午巡线时长(小时)", "下午巡线时长(小时)", "外力施工配合时长(小时)",
				"巡线日期", "开始巡线时间", "结束巡线时间 " });
		List<String> code = Arrays.asList(new String[] { "STAFF_NAME",
				"ORG_NAME", "INS_TIME_VALID_ALLDAY", "INS_TIME_VALID_MORNING",
				"INS_TIME_VALID_AFTERNOON", "OS_NURSE_TIME", "INSPECT_DATE",
				"START_TIME", "END_TIME" });

		String fileName = start_date + "~" + end_date + "巡线时长报表";
		String firstLine = start_date + "~" + end_date + "巡线时长(上午 "
				+ start_time_m + "~" + end_time_m + " 下午 " + start_time_a + "~"
				+ end_time_a + ")";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getOsNurseDaily(Map<String, Object> para) throws Exception {
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> planIds = xxdReportDao.getPlanList(para); // 获取planid以及多天该planid的次数
		List<Map<String, Object>> plans = xxdReportDao.getPlanList(para);
		List<Map<String, Object>> list = xxdReportDao.getOsNurseDaily(para); // 无效时间段信息集合
		
		for (Map<String, Object> plan : planIds) {
			List<Map<String, Object>> timeList = xxdReportDao.getTimeList(plan.get("PLAN_ID").toString()); // 获取看护时间
			List<Map<String, Object>> listByPlanId = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> mp : list) { // 获取计划时间段
				String time = "";
				double planT = 0;
				if (mp.get("PLAN_ID").toString().equals(plan.get("PLAN_ID").toString())) {
					for (Map<String, Object> map : timeList) {
						time += map.get("START_TIME").toString() + "~" + map.get("END_TIME").toString() + ",";
						String[] start_time = map.get("START_TIME").toString().split(":");
						String[] end_time = map.get("END_TIME").toString().split(":");
						planT += Double.valueOf(end_time[0]) * 60 * 60 + Double.valueOf(end_time[1]) * 60
								- (Double.valueOf(start_time[0]) * 60 * 60 + Double.valueOf(start_time[1]) * 60);
					}
					mp.put("time", time.substring(0, time.length() - 1)); // 计划时间段
					mp.put("planT", planT * Integer.valueOf(plan.get("COUNT_PLAN_ID").toString())); // 多天的计划总时长 秒
					listByPlanId.add(mp);
				}
			}
			Map<String, Object> paramap = new HashMap<String, Object>();
			if (listByPlanId.size() > 0) {
				String time = listByPlanId.get(0).get("time").toString();
				String staff_name = listByPlanId.get(0).get("STAFF_NAME").toString();
				String site_name = listByPlanId.get(0).get("SITE_NAME").toString();
				String org_name = listByPlanId.get(0).get("ORG_NAME").toString();
				String timePart = "";
				String timePartByValid = "";
				String planId = listByPlanId.get(0).get("PLAN_ID").toString();
				int leaveTimes = 0;//累计离开次数
				double totalT = 0;//累计离开时间
				double planT = Double.valueOf(listByPlanId.get(0).get("planT").toString());//计划看护时间
//				double totalTemp = 0;
				for (Map<String, Object> map : listByPlanId) {
					if (map.get("INVALID_TYPE").toString().equals("1")) {
						leaveTimes++;
						timePart += map.get("START_TIME") + "~" + map.get("END_TIME") + ",";
						String[] start_time = map.get("START_TIME").toString().split(":");
						String[] end_time = map.get("END_TIME").toString().split(":");
						totalT += Double.valueOf(end_time[0]) * 60 * 60 + Double.valueOf(end_time[1]) * 60 + Double.valueOf(end_time[2])
								- (Double.valueOf(start_time[0]) * 60 * 60 + Double.valueOf(start_time[1]) * 60 + Double .valueOf(start_time[2]));
//						String totalTStr = new DecimalFormat("#.00").format(totalT / 3600);
//						System.out.println(Double.valueOf(totalTStr));
//						totalTemp +=Double.valueOf(totalTStr);
					} else {
						timePartByValid += map.get("START_TIME") + "~" + map.get("END_TIME") + ",";
						String[] start_time = map.get("START_TIME").toString().split(":");
						String[] end_time = map.get("END_TIME").toString().split(":");
						totalT += Double.valueOf(end_time[0]) * 60 * 60 + Double.valueOf(end_time[1]) * 60 + Double.valueOf(end_time[2])
								- (Double.valueOf(start_time[0]) * 60 * 60 + Double.valueOf(start_time[1]) * 60 + Double.valueOf(start_time[2]));
//						String totalTStr = new DecimalFormat("#.00").format(totalT / 3600);
//						System.out.println(Double.valueOf(totalTStr));
//						totalTemp +=Double.valueOf(totalTStr);
					}
				}
				paramap.put("planId", planId);
				paramap.put("staff_name", staff_name);
				paramap.put("site_name", site_name);
				paramap.put("org_name", org_name);
				paramap.put("staff_id", listByPlanId.get(0).get("STAFF_ID").toString());
				paramap.put("org_id", listByPlanId.get(0).get("ORG_ID").toString());
				paramap.put("out_site_id", listByPlanId.get(0).get("OUT_SITE_ID").toString());
				paramap.put("time", time);
				paramap.put("leaveTimes", leaveTimes);
				
				/**
				 * 单天四舍五入后相加，多天相加后四舍五入，结果不同，此处有BUG
				 * TODO 已修复 未发布
				 */
				String totalTimes = new DecimalFormat("#.00").format(totalT /3600);
//				paramap.put("totalTimes", totalTemp);
				paramap.put("totalTimes", totalTimes);
				
				String rateOfKanHu = new DecimalFormat("#.00").format(((planT - totalT) / planT) * 100) + "%";
				paramap.put("rateOfKanHu", rateOfKanHu);
				String planTime = new DecimalFormat("#.00").format(planT / 3600);
				paramap.put("planTime", planTime);
				if (timePart.length() > 0) {
					timePart = timePart.substring(0, timePart.length() - 1);
				}
				if (timePartByValid.length() > 0) {
					timePartByValid = timePartByValid.substring(0, timePartByValid.length() - 1);
				}
				paramap.put("timePart", timePart);
				paramap.put("timePartByValid", timePartByValid);
				paramap.put("workTime", new DecimalFormat("#.00").format(Double.valueOf(planTime) - Double.valueOf(totalTimes)));
//				paramap.put("workTime", new DecimalFormat("#.00").format(Double.valueOf(planTime) - totalTemp));
				resultList.add(paramap);
			}
		}
		List<Map<String, Object>> paraList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < plans.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).get("PLAN_ID").toString().equals(plans.get(i).get("PLAN_ID").toString())) {
					paraList.add(plans.get(i));
					break;
				}
			}
		}
		plans.removeAll(paraList);
		Map<String, Object> pm = null;
		for (Map<String, Object> plan : plans) {
			List<Map<String, Object>> timeList = xxdReportDao.getTimeList(plan.get("PLAN_ID").toString()); // 获取看护时间
			pm = new HashMap<String, Object>();
			double planSec = 0;
			String time = "";
			for (Map<String, Object> map : timeList) { // 计算多天计划时长总和
				time += map.get("START_TIME").toString() + "~" + map.get("END_TIME").toString() + ",";
				String[] start_time = map.get("START_TIME").toString().split(":");
				String[] end_time = map.get("END_TIME").toString().split(":");
				planSec += Double.valueOf(end_time[0]) * 60 * 60 + Double.valueOf(end_time[1]) * 60
						- (Double.valueOf(start_time[0]) * 60 * 60 + Double.valueOf(start_time[1]) * 60);
			}
			String planTime = new DecimalFormat("#.00").format(planSec * Integer.valueOf(plan.get("COUNT_PLAN_ID").toString()) / 3600);
			pm.put("planTime", planTime); // 多天的计划总时长 秒
			List<Map<String, Object>> infos = xxdReportDao.selInfoByPlanId(plan.get("PLAN_ID").toString());
			if (infos != null && infos.size() > 0) {
				pm.put("planId", plan.get("PLAN_ID"));
				pm.put("staff_name", infos.get(0).get("STAFF_NAME"));
				pm.put("site_name", infos.get(0).get("SITE_NAME"));
				pm.put("org_name", infos.get(0).get("ORG_NAME"));
				pm.put("staff_id", infos.get(0).get("STAFF_ID"));
				pm.put("org_id", infos.get(0).get("ORG_ID"));
				pm.put("out_site_id", infos.get(0).get("OUT_SITE_ID"));
				pm.put("leaveTimes", "-");
				pm.put("totalTimes", "-");
				pm.put("rateOfKanHu", "100%");
				pm.put("time", time.substring(0, time.length() - 1));
				pm.put("timePart", "-");
				pm.put("timePartByValid", "-");
				pm.put("workTime", planTime);
				resultList.add(pm);
			}

		}

		System.out.println(resultList);
		return resultList;

	}

	public List<Map<String, Object>> osNurseDailySet(List<String> planIds, String queryTime) throws Exception {
		Map<String, Object> paramap = new HashMap<String, Object>();
		paramap.put("query_time", queryTime);
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < planIds.size(); i++) {
			String plan_id = planIds.get(i);
			paramap.put("plan_id", plan_id);
			// 查询计划时间
			List<Map<String, Object>> timeList = xxdReportDao.getTimeList(plan_id);
			StringBuffer sb = new StringBuffer();// 看护时间段
			int leaveTimes = 0; // 离开次数
			double totalTimes = 0; // 累计离开时间
			StringBuffer timePart = new StringBuffer();// 离开时间段
			StringBuffer timePartByValid = new StringBuffer();// 超过20分钟的时间段
			for (Map<String, Object> mapList : timeList) {
				sb.append("," + mapList.get("START_TIME").toString() + "~" + mapList.get("END_TIME").toString());
				List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
				li.add(mapList);
				paramap.put("list", li);
				List<Map<String, Object>> invalidTime = xxdReportDao.invalidTime(paramap);// 根据planid和计划时间段 得到所有无效时间点
				List<Map<String, Object>> validTime = xxdReportDao.validTime(paramap);// 根据planid和计划时间段 得到所有有效时间点
				List<Map<String, Object>> allTime = xxdReportDao.selAllTime(paramap);

				Map<String, Object> firstMap = new HashMap<String, Object>(); // 默人看护计划的每个时间段的开始时间点为有效点
				Map<String, Object> lastMap = new HashMap<String, Object>(); // 默人看护计划的每个时间段的结束时间点为有效点
				firstMap.put("GPS_SWITCH", 1);
				firstMap.put("TRACK_TIME", queryTime + " " + mapList.get("START_TIME").toString() + ":00");
				firstMap.put("PLAN_ID", plan_id);
				firstMap.put("POLYGN_ID", 0);
				firstMap.put("IS_GUARD", 1);
				firstMap.put("TAG", 1);
				validTime.add(0, firstMap);
				allTime.add(0, firstMap);

				lastMap.put("GPS_SWITCH", 1);
				lastMap.put("TRACK_TIME", queryTime + " " + mapList.get("END_TIME").toString() + ":00");
				lastMap.put("PLAN_ID", plan_id);
				lastMap.put("POLYGN_ID", allTime.size() > 0 ? Integer.valueOf(allTime.get(allTime.size() - 1).get("POLYGN_ID").toString()) + 1 : 1);
				lastMap.put("IS_GUARD", 1);
				lastMap.put("TAG", 1);
				validTime.add(lastMap);
				allTime.add(lastMap);
				if (invalidTime.size() > 0) {
					for (Map<String, Object> map : invalidTime) { // 无效时间点求无效时间
						// 在该计划时间段内存在下一个点并求无效时长
						Map<String, Object> nextM = selNextTime(map, allTime);
						if (nextM != null) {
							totalTimes += dateMinus(map.get("TRACK_TIME").toString(), nextM.get("TRACK_TIME").toString());
						}
					}
				}
				if (validTime.size() > 0) {
					for (Map<String, Object> map : validTime) { // 有效时间间隔大于20分钟
						// 做无效时间处理
						if (selNextTime(map, allTime) != null) {
							if (dateMinus(map.get("TRACK_TIME").toString(),selNextTime(map, allTime).get("TRACK_TIME").toString()) > 1200) {
								totalTimes += dateMinus(map.get("TRACK_TIME").toString(), selNextTime(map, allTime).get("TRACK_TIME").toString());
							}
						}
					}
				}

				if (invalidTime.size() > 0) {
					for (Map<String, Object> invalidMap : invalidTime) {
						if (Integer.valueOf(invalidMap.get("TAG").toString()) != 0) {
							String start_time_invalid = invalidMap.get("TRACK_TIME").toString();
							Map<String, Object> nextMap = selNextTime(invalidMap, allTime);
							if (nextMap != null) {
								if (nextMap.get("IS_GUARD").toString().equals("1") && nextMap.get("GPS_SWITCH").toString().equals("1")) {
									timePart.append("," + invalidMap.get("TRACK_TIME").toString() + "~" + nextMap.get("TRACK_TIME").toString());
									leaveTimes++;
									continue;
								}
								for (Map<String, Object> vp : invalidTime) {
									if (vp.get("POLYGN_ID").toString().equals(nextMap.get("POLYGN_ID").toString())) {
										vp.put("TAG", 0);
										break;
									}
								}
								nextMap = selNextTime(nextMap, allTime);
								if (nextMap != null) {
									while (nextMap.get("IS_GUARD").toString().equals("0") || nextMap.get("GPS_SWITCH").toString().equals("0")) {
										for (Map<String, Object> vp : invalidTime) {
											if (vp.get("POLYGN_ID").toString().equals(nextMap.get("POLYGN_ID").toString())) {
												vp.put("TAG", 0);
												break;
											}
										}
										nextMap = selNextTime(nextMap, allTime);
									}
									timePart.append("," + start_time_invalid + "~" + nextMap.get("TRACK_TIME").toString());
									leaveTimes++;
								}
							}
						}
					}
				}
				if (validTime.size() > 0) {
					for (Map<String, Object> validMap : validTime) {
						if (Integer.valueOf(validMap.get("TAG").toString()) != 0) {
							String start_time_valid = validMap.get("TRACK_TIME").toString();
							Map<String, Object> nextMap = selNextTime(validMap, allTime);
							if (nextMap != null) {
								boolean choice = false;
								while (dateMinus(validMap.get("TRACK_TIME").toString(), nextMap.get("TRACK_TIME").toString()) > 1200) {
									if ((nextMap.get("IS_GUARD").toString().equals("0") || nextMap.get("GPS_SWITCH").toString().equals("0"))) {
										timePartByValid.append("," + start_time_valid + "~" + nextMap.get("TRACK_TIME").toString());
										choice = false;
										break;
									}
									choice = true;
									for (Map<String, Object> vp : validTime) {
										if (vp.get("POLYGN_ID").toString().equals(nextMap.get("POLYGN_ID").toString())) {
											vp.put("TAG", 0);
											break;
										}
									}
									validMap = nextMap;
									nextMap = selNextTime(nextMap, allTime);
									if (nextMap == null) {
										choice = false;
										timePartByValid.append("," + start_time_valid + "~" + validMap.get("TRACK_TIME").toString());
										break;
									}
								}
								if (choice) {
									timePartByValid.append("," + start_time_valid + "~" + validMap.get("TRACK_TIME").toString());
								}
							}
						}
					}
				}
			}
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("plan_id", plan_id);
			List<Map<String, Object>> infoList = xxdReportDao.selBaseInfo(pmap);
			Map<String, Object> infoMap = infoList.get(0); // 通过plan_id查询外力点名称和看护员姓名
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("user_id", infoMap.get("USER_ID"));
			mp.put("staff_name", infoMap.get("STAFF_NAME"));
			mp.put("site_name", infoMap.get("SITE_NAME"));
			mp.put("time", sb.toString());
			mp.put("leaveTimes", leaveTimes);
			mp.put("totalTimes", totalTimes);
			mp.put("timePart", timePart.toString());
			mp.put("timePartByValid", timePartByValid.toString());
			mp.put("plan_id", plan_id);
			paramap.put("timePart", "-");
			paramap.put("timePartByValid", "-");
			res.add(mp);

		}
		for (Map<String, Object> map : res) { // 整理数据 用于前台展示
			String totalTimes = new DecimalFormat("#.00").format(Double
					.valueOf(map.get("totalTimes").toString()) / 3600)
					+ "小时";
			map.put("totalTimes", totalTimes);
			String tp = "";
			String timePart = "";
			if (map.get("timePart") != null
					&& map.get("timePart").toString().length() > 1) {
				timePart = map.get("timePart").toString().substring(1,
						map.get("timePart").toString().length());
				String[] timePartModel = timePart.split(",");
				for (String str : timePartModel) {
					String[] strMiddle = str.split("~");
					if (strMiddle[0].length() > 10) {
						strMiddle[0] = strMiddle[0].split(" ")[1];
					}
					if (strMiddle[1].length() > 10) {
						strMiddle[1] = strMiddle[1].split(" ")[1];
					}
					tp += strMiddle[0] + "~" + strMiddle[1] + ",";
				}
				timePart = tp.substring(0, tp.length() - 1);
			}
			map.put("timePart", timePart);

			String tpbv = "";
			String time = "";
			String timePartByValid = "";
			if (map.get("timePartByValid") != null
					&& map.get("timePartByValid").toString().length() > 1) {
				timePartByValid = map.get("timePartByValid").toString()
						.substring(1,
								map.get("timePartByValid").toString().length());
				String[] tpbvModel = timePartByValid.split(",");
				for (String str : tpbvModel) {
					String[] strMiddle = str.split("~");
					if (strMiddle[0].length() > 10) {
						strMiddle[0] = strMiddle[0].split(" ")[1];
					}
					if (strMiddle[1].length() > 10) {
						strMiddle[1] = strMiddle[1].split(" ")[1];
					}
					tpbv += strMiddle[0] + "~" + strMiddle[1] + ",";
				}
				timePartByValid = tpbv.substring(0, tpbv.length() - 1);
			}
			map.put("timePartByValid", timePartByValid);
			time = map.get("time").toString().substring(1,
					map.get("time").toString().length());
			map.put("time", time);
		}
		return res;

	}

	public Map<String, Object> selNextTime(Map<String, Object> map, List<Map<String, Object>> allTime) {
		double polygn_id = Double.valueOf(map.get("POLYGN_ID").toString());
		for (int i = 0; i < allTime.size(); i++) {
			if (Double.valueOf(allTime.get(i).get("POLYGN_ID").toString()) == polygn_id) {
				if (allTime.size() - 1 > i) {
					return allTime.get(i + 1);
				} else {
					break;
				}
			}
		}
		return null;
	}

	public long dateMinus(String str1, String str2) throws ParseException {
		Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str1);
		Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str2);
		long temp = date2.getTime() - date1.getTime();
		long second = temp / 1000;
		return second;
	}

	@Override
	public Map<String, Object> getInvalidTime(Map<String, Object> para, UIPage pager) {
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);

		List<Map<String, Object>> olists = xxdReportDao.getInvalidTime(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void osNurseDailyDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(30);

		data = getOsNurseDaily(para);
		//
		List<String> title = Arrays.asList(new String[] { "外力点名称", "区域名称  ",
				"看护员", "计划看护时间段", "离开时间段", "超过20分钟", "累计离开时间", "离开次数",
				"累计在岗时长", "看护到位率" });
		List<String> code = Arrays.asList(new String[] { "site_name",
				"org_name", "staff_name", "time", "timePart",
				"timePartByValid", "totalTimes", "leaveTimes", "workTime",
				"rateOfKanHu" });

		String fileName = "外力点看护日报（" + para.get("start_query_time").toString()
				+ "至" + para.get("end_query_time").toString() + "）";
		String firstLine = fileName;

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getInspectTimeDaily(Map<String, Object> para) {
		// 查询工作时间
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", 2);// 2全省

		map.put("paramName", "WorkStart");
		String start_date_m = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkEnd");
		String end_date_m = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkStart2");
		String start_date_a = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkEnd2");
		String end_date_a = paramDao.getParamValueByName(map);

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		String start_date_t = start_date + " " + start_date_m + ":00";
		String end_date_t = end_date + " " + end_date_a + ":00";

		map.put("start_date_m", start_date_m);
		map.put("end_date_m", end_date_m);
		map.put("start_date_a", start_date_a);
		map.put("end_date_a", end_date_a);

		map.put("start_date", start_date);
		map.put("end_date", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		String[] end_date_marr = end_date_m.split(":");
		String[] start_date_marr = start_date_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
				+ Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60
				- Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_date_a.split(":");
		String[] start_date_aarr = start_date_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
				+ Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60
				- Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		List<Map<String, Object>> olists = xxdReportDao
				.getInspectTimeDaily(map);

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void inspectTimeDailyDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		// 查询工作时间
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", 2);// 2全省

		map.put("paramName", "WorkStart");
		String start_date_m = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkEnd");
		String end_date_m = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkStart2");
		String start_date_a = paramDao.getParamValueByName(map);

		map.put("paramName", "WorkEnd2");
		String end_date_a = paramDao.getParamValueByName(map);

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		String start_date_t = start_date + " " + start_date_m + ":00";
		String end_date_t = end_date + " " + end_date_a + ":00";

		map.put("start_date_m", start_date_m);
		map.put("end_date_m", end_date_m);
		map.put("start_date_a", start_date_a);
		map.put("end_date_a", end_date_a);

		map.put("start_date", start_date);
		map.put("end_date", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		String[] end_date_marr = end_date_m.split(":");
		String[] start_date_marr = start_date_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
				+ Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60
				- Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_date_a.split(":");
		String[] start_date_aarr = start_date_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
				+ Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60
				- Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		List<Map<String, Object>> data = xxdReportDao.getInspectTimeDaily(map);
		// 备注

		List<String> title = Arrays.asList(new String[] { "分公司", "平均每天巡线时长",
				"平均上午巡线时长", "平均下午巡线时长", "备注" });
		List<String> code = Arrays.asList(new String[] { "AREA_NAME",
				"INS_TIME_VALID_ALLDAY", "INS_TIME_VALID_MORNING",
				"INS_TIME_VALID_AFTERNOON", "REMARK" });

		String fileName = "包线员巡线时长表（" + start_date + "-" + end_date + "）";
		String firstLine = fileName;

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Map<String, Object>> qualityReportByPeople(
			Map<String, Object> para) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", para.get("area_id"));
		map.put("org_id", para.get("org_id"));
		map.put("paramName", "WorkStart");
		String start_date_m = paramDao.getParamValueByName(map);
		para.put("start_date_m", start_date_m);
		map.put("paramName", "WorkEnd");
		String end_date_m = paramDao.getParamValueByName(map);
		para.put("end_date_m", end_date_m);
		map.put("paramName", "WorkStart2");
		String start_date_a = paramDao.getParamValueByName(map);
		para.put("start_date_a", start_date_a);
		map.put("paramName", "WorkEnd2");
		String end_date_a = paramDao.getParamValueByName(map);
		para.put("end_date_a", end_date_a);

		map.put("paramName", "WorkStart");

		map.put("paramName", "WorkEnd");

		map.put("paramName", "WorkStart2");

		map.put("paramName", "WorkEnd2");

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		String start_date_t = start_date + " " + start_date_m + ":00";
		String end_date_t = end_date + " " + end_date_a + ":00";

		map.put("start_date_m", start_date_m);
		map.put("end_date_m", end_date_m);
		map.put("start_date_a", start_date_a);
		map.put("end_date_a", end_date_a);

		map.put("start_time", start_date);
		map.put("end_time", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		String[] end_date_marr = end_date_m.split(":");
		String[] start_date_marr = start_date_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
				+ Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60
				- Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_date_a.split(":");
		String[] start_date_aarr = start_date_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
				+ Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60
				- Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		map.put("paramName", "OutSiteStay");
		String OutSiteStay = paramDao.getParamValueByName(map);

		// 隐患
		map.put("paramName", "UnSafeOutSiteStay");
		String UnSafeOutSiteStay = paramDao.getParamValueByName(map);

		map.put("OutSiteStay", OutSiteStay);
		map.put("UnSafeOutSiteStay", UnSafeOutSiteStay);

		List<Map<String, Object>> list = xxdReportDao
				.qualityReportByPeople(map);

		return list;
	}

	@Override
	public Map<String, Object> getInspectAllTime(Map<String, Object> para) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("org_id", para.get("org_id"));
		map.put("areaId", para.get("area_id"));

		// map.put("paramName", "WorkStart");
		// String start_date_m = paramDao.getParamValueByName(map);

		// map.put("paramName", "WorkEnd");
		// String end_date_m = paramDao.getParamValueByName(map);

		// map.put("paramName", "WorkStart2");
		// String start_date_a = paramDao.getParamValueByName(map);

		// map.put("paramName", "WorkEnd2");
		// String end_date_a = paramDao.getParamValueByName(map);

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		// String start_date_t = start_date + " " + start_date_m + ":00";
		// String end_date_t = end_date + " " + end_date_a + ":00";

		// map.put("start_date_m", start_date_m);
		// map.put("end_date_m", end_date_m);
		// map.put("start_date_a", start_date_a);
		// map.put("end_date_a", end_date_a);

		map.put("start_date", start_date);
		map.put("end_date", end_date);

		// map.put("start_date_t", start_date_t);
		// map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		// String[] end_date_marr = end_date_m.split(":");
		// String[] start_date_marr = start_date_m.split(":");
		// int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
		// + Integer.parseInt(end_date_marr[1])
		// - Integer.parseInt(start_date_marr[0]) * 60
		// - Integer.parseInt(start_date_marr[1]);

		// String[] end_date_aarr = end_date_a.split(":");
		// String[] start_date_aarr = start_date_a.split(":");
		// int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
		// + Integer.parseInt(end_date_aarr[1])
		// - Integer.parseInt(start_date_aarr[0]) * 60
		// - Integer.parseInt(start_date_aarr[1]);

		// map.put("total_min_m", total_min_m);
		// map.put("total_min_a", total_min_a);

		List<Map<String, Object>> olists = xxdReportDao.getInspectAllTime(map);

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void inspectAllTimeDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", para.get("area_id"));
		map.put("org_id", para.get("org_id"));
		map.put("paramName", "WorkStart");
		// String start_time_m = paramDao.getParamValueByName(map);

		// map.put("paramName", "WorkEnd");
		// String end_time_m = paramDao.getParamValueByName(map);

		// map.put("paramName", "WorkStart2");
		// String start_time_a = paramDao.getParamValueByName(map);

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		// String start_date_t = start_date + " " + start_time_m + ":00";
		// String end_date_t = end_date + " " + end_time_a + ":00";

		// map.put("start_date_m", start_time_m);
		// map.put("end_date_m", end_time_m);
		// map.put("start_date_a", start_time_a);
		// map.put("end_date_a", end_time_a);

		map.put("start_date", start_date);
		map.put("end_date", end_date);

		// map.put("start_date_t", start_date_t);
		// map.put("end_date_t", end_date_t);
		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		// String[] end_date_marr = end_time_m.split(":");
		// String[] start_date_marr = start_time_m.split(":");
		// int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
		// + Integer.parseInt(end_date_marr[1])
		// - Integer.parseInt(start_date_marr[0]) * 60
		// - Integer.parseInt(start_date_marr[1]);

		// String[] end_date_aarr = end_time_a.split(":");
		// String[] start_date_aarr = start_time_a.split(":");
		// int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
		// + Integer.parseInt(end_date_aarr[1])
		// - Integer.parseInt(start_date_aarr[0]) * 60
		// - Integer.parseInt(start_date_aarr[1]);

		// map.put("total_min_m", total_min_m);
		// map.put("total_min_a", total_min_a);

		List<Map<String, Object>> data = xxdReportDao.getInspectAllTime(map);
		//
		List<String> title = Arrays.asList(new String[] { "姓名", "区域",
				"巡线总时长(小时)", "平均上午巡线时长", "平均下午巡线时长", "全市平均巡线总时长 (小时)",
				"外力施工配合时长", "全市平均外力施工配合时长", "巡线日期" });
		List<String> code = Arrays.asList(new String[] { "STAFF_NAME",
				"ORG_NAME", "INS_TIME_VALID_ALLDAY", "INS_TIME_VALID_MORNING",
				"INS_TIME_VALID_AFTERNOON", "AVG_INS_TIME_VALID_ALLDAY",
				"OS_NURSE_TIME", "AVG_OS_NURSE_TIME", "INSPECT_DATE" });

		String fileName = start_date + "~" + end_date + "巡线时长报表";
		String firstLine = start_date + "~" + end_date + "巡线时长";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Map<String, Object>> selArea() {
		return xxdReportDao.selArea();
	}
	/**
	 * 地市质量分析
	 */
	@Override
	public List<Map<String, Object>> qualityReportByArea(Map<String, Object> para) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", para.get("localId"));
		map.put("paramName", "WorkStart");
		String start_date_m = paramDao.getParamValueByName(map);
		para.put("start_date_m", start_date_m);
		map.put("paramName", "WorkEnd");
		String end_date_m = paramDao.getParamValueByName(map);
		para.put("end_date_m", end_date_m);
		map.put("paramName", "WorkStart2");
		String start_date_a = paramDao.getParamValueByName(map);
		para.put("start_date_a", start_date_a);
		map.put("paramName", "WorkEnd2");
		String end_date_a = paramDao.getParamValueByName(map);
		para.put("end_date_a", end_date_a);

		map.put("paramName", "WorkStart");
		map.put("paramName", "WorkEnd");
		map.put("paramName", "WorkStart2");
		map.put("paramName", "WorkEnd2");
		

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		String start_date_t = start_date + " " + start_date_m + ":00";
		String end_date_t = end_date + " " + end_date_a + ":00";

		map.put("start_date_m", start_date_m);
		map.put("end_date_m", end_date_m);
		map.put("start_date_a", start_date_a);
		map.put("end_date_a", end_date_a);

		map.put("start_time", start_date);
		map.put("end_time", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		String[] end_date_marr = end_date_m.split(":");
		String[] start_date_marr = start_date_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
				+ Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60
				- Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_date_a.split(":");
		String[] start_date_aarr = start_date_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
				+ Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60
				- Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		// 非隐患
		map.put("paramName", "OutSiteStay");
		String OutSiteStay = paramDao.getParamValueByName(map);

		// 隐患
		map.put("paramName", "UnSafeOutSiteStay");
		String UnSafeOutSiteStay = paramDao.getParamValueByName(map);

		map.put("OutSiteStay", OutSiteStay);
		map.put("UnSafeOutSiteStay", UnSafeOutSiteStay);

		List<Map<String, Object>> list = xxdReportDao.qualityReportByArea(map);
		Map<String, Object> listAll = xxdReportDao.qualityReportByAllprovince(map);
		String name = "全省";
		listAll.put("NAME", name);
		list.add(listAll);
		return list;
	}

	@Override
	public void qualityReportByPeopleDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", para.get("area_id"));
		map.put("org_id", para.get("org_id"));
		map.put("paramName", "WorkStart");
		String start_date_m = paramDao.getParamValueByName(map);
		para.put("start_date_m", start_date_m);
		map.put("paramName", "WorkEnd");
		String end_date_m = paramDao.getParamValueByName(map);
		para.put("end_date_m", end_date_m);
		map.put("paramName", "WorkStart2");
		String start_date_a = paramDao.getParamValueByName(map);
		para.put("start_date_a", start_date_a);
		map.put("paramName", "WorkEnd2");
		String end_date_a = paramDao.getParamValueByName(map);
		para.put("end_date_a", end_date_a);

		map.put("paramName", "WorkStart");

		map.put("paramName", "WorkEnd");

		map.put("paramName", "WorkStart2");

		map.put("paramName", "WorkEnd2");

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		String start_date_t = start_date + " " + start_date_m + ":00";
		String end_date_t = end_date + " " + end_date_a + ":00";

		map.put("start_date_m", start_date_m);
		map.put("end_date_m", end_date_m);
		map.put("start_date_a", start_date_a);
		map.put("end_date_a", end_date_a);

		map.put("start_time", start_date);
		map.put("end_time", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		String[] end_date_marr = end_date_m.split(":");
		String[] start_date_marr = start_date_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
				+ Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60
				- Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_date_a.split(":");
		String[] start_date_aarr = start_date_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
				+ Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60
				- Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		map.put("paramName", "OutSiteStay");
		String OutSiteStay = paramDao.getParamValueByName(map);

		// 隐患
		map.put("paramName", "UnSafeOutSiteStay");
		String UnSafeOutSiteStay = paramDao.getParamValueByName(map);

		map.put("OutSiteStay", OutSiteStay);
		map.put("UnSafeOutSiteStay", UnSafeOutSiteStay);

		List<Map<String, Object>> list = xxdReportDao
				.qualityReportByPeople(map);

		List<String> title = Arrays.asList(new String[] { "巡线员", "区域", "巡检到位率",
				"外力点检查到位率", "关键点检查到位率", "巡线总时长" });
		List<String> code = Arrays.asList(new String[] { "STAFF_NAME",
				"ORG_NAME", "XUNJIAN_RATE", "SITE_RATE", "ARRIVAL_RATE",
				"INS_TIME_VALID_ALLDAY" });

		String fileName = "巡线员质量分析（" + start_date + "-" + end_date + "）";
		String firstLine = fileName;

		try {
			ExcelUtil.generateExcelAndDownload(title, code, list, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void qualityReportByAreaDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();
		List<Map<String, Object>> list = this.qualityReportByArea(para);
		List<String> title = Arrays.asList(new String[] { "地市名称", "包线人数",
				"巡线平均时长", "巡线下午平均时长", "关键点平均巡检到位率", "巡检平均到位率", "外力点平均巡检到位率",
				"外力施工配合平均施工时长", "巡线日期" });
		List<String> code = Arrays.asList(new String[] { "NAME",
				"COUNT_STAFF_ID", "AVG_TIME_VALID_ALLDAY",
				"AVG_TIME_VALID_AFTERNOON", "AVG_ARRIVAL_RATE",
				"AVG_XUNJIAN_RATE", "AVG_SITE_RATE", "AVG_OS_NURSE_TIME",
				"DATE_TIME" });

		String fileName = "地市质量分析（" + start_date + "-" + end_date + "）";
		String firstLine = fileName;

		try {
			ExcelUtil.generateExcelAndDownload(title, code, list, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 步巡任务分配
	 * 
	 * @throws ParseException
	 */
	public void allotTaskOfStepTour() throws Exception {
		List<Map<String, Object>> lists = xxdReportDao.selPlanInfoOfStep(null); // 获取所有步巡的计划信息
		for (Map<String, Object> planMap : lists) {
			Date start_time = new SimpleDateFormat("yyyy-MM-dd").parse(planMap
					.get("START_TIME").toString()); // 计划起始日期
			Date end_time = new SimpleDateFormat("yyyy-MM-dd").parse(planMap
					.get("END_TIME").toString()); // 计划结束日期
			Date current_time = new Date();
			Calendar calendar = Calendar.getInstance();
			Map<String, Object> paramap = null;
			if (current_time.getTime() - start_time.getTime() >= 0
					&& current_time.getTime() - end_time.getTime() < 0) {
				calendar.setTime(current_time);
				calendar.set(calendar.get(Calendar.YEAR), calendar
						.get(Calendar.MONTH), 1);
				calendar.roll(Calendar.DATE, -1);
				String et = new SimpleDateFormat("yyyy-MM-dd").format(calendar
						.getTime()); // 任务结束时间

				calendar.setTime(current_time);
				calendar.set(calendar.get(Calendar.YEAR), calendar
						.get(Calendar.MONTH), 1);
				String st = new SimpleDateFormat("yyyy-MM-dd").format(calendar
						.getTime()); // 任务开始时间

				paramap = new HashMap<String, Object>();
				paramap.put("task_name", st + "至" + et + "步巡任务");
				paramap.put("start_time", st);
				paramap.put("end_time", et);
				paramap.put("cable_id", planMap.get("CABLE_ID"));
				paramap.put("relay_id", planMap.get("RELAY_ID"));
				paramap.put("area_id", planMap.get("AREA_ID"));
				paramap.put("inspect_id", planMap.get("INSPECT_ID"));
				paramap.put("is_del", 0);
				xxdReportDao.saveStepTask(paramap);

				List<Map<String, Object>> equipPartList = xxdReportDao
						.selEquipPart(planMap);

				for (Map<String, Object> mp : equipPartList) {
					xxdReportDao.saveStepTaskEquip(mp.get("START_EQUIP")
							.toString());
					if (mp.get("END_EQUIP").toString().equals(
							planMap.get("END_EQUIP").toString())) {
						xxdReportDao.saveStepTaskEquip(mp.get("END_EQUIP")
								.toString());
						break;
					}
				}
			}
		}
	}

	public List<Map<String, Object>> detailInfo(Map<String, Object> para) {
		List<Map<String, Object>> lists = xxdReportDao.getOsNurseDaily(para); // 无效轨迹点的集合信息
		List<Map<String, Object>> listsByDel = xxdReportDao.selPlanTime(para); // 查询无效时间集合所在日期
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> allPlanDate = xxdReportDao
				.selAllPlanDate(para); // 查询改planid下所有的任务日期

		for (Map<String, Object> map1 : listsByDel) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for (Map<String, Object> map2 : lists) {
				if (map2.get("PLAN_TIME").toString().equals(
						map1.get("PLAN_TIME").toString())) {
					if (resultMap.get("PLAN_TIME") == null) {
						resultMap.put("PLAN_TIME", map2.get("PLAN_TIME")
								.toString());
					}
					if (map2.get("INVALID_TYPE").toString().equals("1")) {
						if (resultMap.get("invalidTime1") == null) {
							resultMap.put("invalidTime1", map2
									.get("START_TIME").toString()
									+ "-" + map2.get("END_TIME").toString());
						} else {
							resultMap.put("invalidTime1", resultMap
									.get("invalidTime1")
									+ ","
									+ map2.get("START_TIME").toString()
									+ "-" + map2.get("END_TIME").toString());
						}
					}
					if (map2.get("INVALID_TYPE").toString().equals("2")) {
						if (resultMap.get("invalidTime2") == null) {
							resultMap.put("invalidTime2", map2
									.get("START_TIME").toString()
									+ "-" + map2.get("END_TIME").toString());
						} else {
							resultMap.put("invalidTime2", resultMap
									.get("invalidTime2")
									+ ","
									+ map2.get("START_TIME").toString()
									+ "-" + map2.get("END_TIME").toString());
						}
					}
				}
			}
			resultList.add(resultMap);
		}

		List<Map<String, Object>> listDel = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m1 : allPlanDate) {
			for (Map<String, Object> m2 : listsByDel) {
				if (m2.get("PLAN_TIME").toString().equals(
						m1.get("PLAN_TIME").toString())) {
					listDel.add(m1);
				}
			}
		}
		allPlanDate.removeAll(listDel);
		resultList.addAll(allPlanDate);

		List<Map<String, Object>> timeList = xxdReportDao.getTimeList(para.get(
				"plan_id").toString()); // 获取看护时间
		String time = "";
		for (Map<String, Object> map : timeList) {
			time += map.get("START_TIME").toString() + "~"
					+ map.get("END_TIME").toString() + ",";
		}
		time = time.substring(0, time.length() - 1);
		for (Map<String, Object> map : resultList) {
			map.put("timePart", time);
		}
		return resultList;

	}

	@Override
	public List<Map<String, Object>> getScopeList(Map<String, Object> para) {
		return xxdReportDao.getScopeList(para);
	}

	@Override
	public List<Map<String, Object>> qualityReportByscope(Map<String, Object> para) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", para.get("area_id"));
		map.put("org_id", para.get("org_id"));
		map.put("paramName", "WorkStart");
		String start_date_m = paramDao.getParamValueByName(map);
		para.put("start_date_m", start_date_m);
		map.put("paramName", "WorkEnd");
		String end_date_m = paramDao.getParamValueByName(map);
		para.put("end_date_m", end_date_m);
		map.put("paramName", "WorkStart2");
		String start_date_a = paramDao.getParamValueByName(map);
		para.put("start_date_a", start_date_a);
		map.put("paramName", "WorkEnd2");
		String end_date_a = paramDao.getParamValueByName(map);
		para.put("end_date_a", end_date_a);

		map.put("paramName", "WorkStart");

		map.put("paramName", "WorkEnd");

		map.put("paramName", "WorkStart2");

		map.put("paramName", "WorkEnd2");

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		String start_date_t = start_date + " " + start_date_m + ":00";
		String end_date_t = end_date + " " + end_date_a + ":00";

		map.put("start_date_m", start_date_m);
		map.put("end_date_m", end_date_m);
		map.put("start_date_a", start_date_a);
		map.put("end_date_a", end_date_a);

		map.put("start_time", start_date);
		map.put("end_time", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		String[] end_date_marr = end_date_m.split(":");
		String[] start_date_marr = start_date_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
				+ Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60
				- Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_date_a.split(":");
		String[] start_date_aarr = start_date_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
				+ Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60
				- Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		// 非隐患
		map.put("paramName", "OutSiteStay");
		String OutSiteStay = paramDao.getParamValueByName(map);

		// 隐患
		map.put("paramName", "UnSafeOutSiteStay");
		String UnSafeOutSiteStay = paramDao.getParamValueByName(map);

		map.put("OutSiteStay", OutSiteStay);
		map.put("UnSafeOutSiteStay", UnSafeOutSiteStay);

		List<Map<String, Object>> list = xxdReportDao.qualityReportByScope(map);

		return list;
	}

	@Override
	public void qualityReportByScopeDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", para.get("area_id"));
		map.put("org_id", para.get("org_id"));
		map.put("paramName", "WorkStart");
		String start_date_m = paramDao.getParamValueByName(map);
		para.put("start_date_m", start_date_m);
		map.put("paramName", "WorkEnd");
		String end_date_m = paramDao.getParamValueByName(map);
		para.put("end_date_m", end_date_m);
		map.put("paramName", "WorkStart2");
		String start_date_a = paramDao.getParamValueByName(map);
		para.put("start_date_a", start_date_a);
		map.put("paramName", "WorkEnd2");
		String end_date_a = paramDao.getParamValueByName(map);
		para.put("end_date_a", end_date_a);

		map.put("paramName", "WorkStart");

		map.put("paramName", "WorkEnd");

		map.put("paramName", "WorkStart2");

		map.put("paramName", "WorkEnd2");

		// ping sql 需要的参数
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();

		String start_date_t = start_date + " " + start_date_m + ":00";
		String end_date_t = end_date + " " + end_date_a + ":00";

		map.put("start_date_m", start_date_m);
		map.put("end_date_m", end_date_m);
		map.put("start_date_a", start_date_a);
		map.put("end_date_a", end_date_a);

		map.put("start_time", start_date);
		map.put("end_time", end_date);

		map.put("start_date_t", start_date_t);
		map.put("end_date_t", end_date_t);

		map.put("area_id", para.get("area_id"));
		map.put("inspect_id", para.get("inspect_id"));

		// 上午 下午总时间
		String[] end_date_marr = end_date_m.split(":");
		String[] start_date_marr = start_date_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60
				+ Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60
				- Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_date_a.split(":");
		String[] start_date_aarr = start_date_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60
				+ Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60
				- Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);

		// 非隐患
		map.put("paramName", "OutSiteStay");
		String OutSiteStay = paramDao.getParamValueByName(map);

		// 隐患
		map.put("paramName", "UnSafeOutSiteStay");
		String UnSafeOutSiteStay = paramDao.getParamValueByName(map);

		map.put("OutSiteStay", OutSiteStay);
		map.put("UnSafeOutSiteStay", UnSafeOutSiteStay);

		List<Map<String, Object>> list = xxdReportDao.qualityReportByScope(map);

		List<String> title = Arrays.asList(new String[] { "区域名称", "包线人数",
				"巡检平均到位率", "外力点平均巡检到位率", "关键点平均巡检到位率", "外力点平均看护到位率", "巡线平均时长",
				"巡线上午平均时长", "巡线下午平均时长", "巡线日期" });
		List<String> code = Arrays.asList(new String[] { "ORG_NAME",
				"COUNT_STAFF_ID", "AVG_XUNJIAN_RATE", "AVG_SITE_RATE",
				"AVG_ARRIVAL_RATE", "AVG_RATEOFKANHU", "AVG_TIME_VALID_ALLDAY",
				"AVG_TIME_VALID_MORNING", "AVG_TIME_VALID_AFTERNOON",
				"DATE_TIME" });

		String fileName = "区域质量分析（" + start_date + "-" + end_date + "）";
		String firstLine = fileName;

		try {
			ExcelUtil.generateExcelAndDownload(title, code, list, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> checkOutSiteByLeader(Map<String, Object> para) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area_id", para.get("area_id"));
		map.put("org_id", para.get("org_id"));
		map.put("paramName", "WorkStart");
		String start_date = para.get("start_time").toString();
		String end_date = para.get("end_time").toString();
		map.put("start_time", start_date);
		map.put("end_time", end_date);
		return xxdReportDao.checkOutSiteByLeader(map);
	}

	@Override
	public void checkOutSiteByLeaderDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
//		String start_date = para.get("start_time").toString();
//		String end_date = para.get("end_time").toString();
		List<Map<String, Object>> list = this.checkOutSiteByLeader(para);
		List<String> title = Arrays.asList(new String[] { "单位", "姓名", "组织关系",
				"检查日期/检查点数", "检查天数" });
		List<String> code = Arrays.asList(new String[] { "NAME", "STAFF_NAME",
				"ORG_NAME", "COUNT_OUT_SITE_ID", "COUNT_DAY" });

		String fileName = "分公司接入中心领导外力点检查记录（" + para.get("start_time") + "-"
				+ para.get("end_time") + "）";
		String firstLine = fileName;

		try {
			ExcelUtil.generateExcelAndDownload(title, code, list, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Map<String, Object>> checkOutSiteByArea(Map<String, Object> para) {
		return xxdReportDao.checkOutSiteByArea(para);
	}

	@Override
	public void checkOutSiteByAreaDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> code = Arrays.asList(new String[] { "NAME",
				"ACTUAL_SITE_ID", "TOTAL_SITE_ID_WITH_USER",
				"TOTAL_OUTSITE_GRADE1", "COUNT_OUT_SITE_ID_ONE",
				"SITE_DATE_GRADE1", "TOTAL_OUTSITE_GRADE2",
				"COUNT_OUT_SITE_ID_TWO", "SITE_DATE_GRADE2", "SITE_RATE" });
		List<Map<String, Object>> data = xxdReportDao.checkOutSiteByArea(para);
		String fileName = para.get("start_time").toString() + "到"
				+ para.get("end_time") + "省外力点检查报表";
		String firstLine = para.get("start_time").toString() + "到"
				+ para.get("end_time") + "省外力点检查报表";
		String modalPath = request.getSession().getServletContext()
				.getRealPath("/excelFiles")
				+ File.separator + "outSiteByArea.xlsx";
		int startRow = 3;
		ExcelUtil.writeToModalAndDown(code, data, request, response, fileName,
				modalPath, startRow, firstLine);
	}

	@Override
	public Map<String, Object> getProvinceOutSiteComplete(
			Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao
				.getProvinceOutSiteComplete(para);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void getProvinceOutSiteCompleteDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "分公司 ", "计划看护时间(小时)",
				"实际看护时间(小时)", "看护到位率" });
		List<String> code = Arrays.asList(new String[] { "NAME", "PLAN_TIME",
				"VALID_TIME", "KANHU_RATE" });
		List<Map<String, Object>> data = xxdReportDao
				.getProvinceOutSiteComplete(para);
		String fileName = "全省外力点看护到位率(" + para.get("start_time") + "至"
				+ para.get("end_time") + ")";
		String firstLine = "全省外力点看护到位率(" + para.get("start_time") + "至"
				+ para.get("end_time") + ")";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getProvinceCheckComplete(
			Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao
				.getProvinceCheckComplete(para);
		return olists;
	}

	@Override
	public void getProvinceCheckCompleteDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "分公司 ", "截止日期",
				"72小时埋深探位率", "72小时内未埋深探位的数量", "未探位外力点数" });
		List<String> code = Arrays.asList(new String[] { "NAME", "END_TIME",
				"CHECK_RATE", "CHECK_WITHOUT_72", "NO_CHECK" });
		List<Map<String, Object>> data = xxdReportDao
				.getProvinceCheckComplete(para);
		String fileName = "分公司72小时外力点埋深探位率统计表";
		String firstLine = "分公司72小时外力点埋深探位率统计表";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 按光缆和中继段查询设施各类型数量页面 getCableRoutingFacility
	 */

	@Override
	public List<Map<String, Object>> getCableRoutingFacility(
			Map<String, Object> para) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("areaId", para.get("localId"));
		// map.put("p_cable_id", para.get("p_cable_id"));
		// map.put("p_relay_id", para.get("p_relay_id"));
		List<Map<String, Object>> olists = xxdReportDao
				.getCableRoutingFacility(para);
		return olists;
	}

	/**
	 * 
	 *按光缆和中继段查询设施各类型数量页面导出 getCableRoutingFacilityDown
	 */
	@Override
	public void getCableRoutingFacilityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays
				.asList(new String[] { "光缆名称", "中继段名称", "标石", "地标", "电杆",
						"非路由标志", "护坎（坡）", "接头盒", "警示牌", "人井", "宣传牌" });
		List<String> code = Arrays
				.asList(new String[] { "CABLE_NAME", "RELAY_NAME", "MARKSTONE",
						"LANDMARK", "POLE", "ROUNTFLAG", "SCARP_PROTECTION",
						"CLOSURE", "CAUTION", "WELLS", "BILLBOARD" });
		List<Map<String, Object>> data = xxdReportDao
				.getCableRoutingFacility(para);
		String fileName = "光缆各类型统计数量表 ";
		String firstLine = "光缆各类型统计数量表";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 省设施总体情况统计表
	 */
	@Override
	public List<Map<String, Object>> getProvinceRoutingFacility(
			Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao
				.getProvinceRoutingFacility(para);
		return olists;

	}

	/**
	 * 
	 * 省设施总体情况统计表导出
	 * 
	 */
	@Override
	public void getProvinceRoutingFacilityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays
				.asList(new String[] { "设施类型 ", "数量（N）", "已检查数量（n）",
						"检查完成率（n/N*100%）", "问题设施数量（t）", "设施完好率（1-t/n）*100%" });
		List<String> code = Arrays.asList(new String[] { "EQUIP_TYPE_NAME",
				"TNUMEBER", "CHECK_NUM", "CHECK_RATE", "TROUBLE_NUM",
				"COMPLETE_RATE" });
		List<Map<String, Object>> data = xxdReportDao
				.getProvinceRoutingFacility(para);
		String fileName = "全省设施总体情况统计表 (" + para.get("min_time") + "至"
				+ para.get("max_time") + ")";
		String firstLine = "全省设施总体情况统计表 (" + para.get("min_time") + "至"
				+ para.get("max_time") + ")";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分公司各类型统计数量表
	 */
	@Override
	public List<Map<String, Object>> getFrefectureRoutingFacility(
			Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao
				.getFrefectureRoutingFacility(para);
		return olists;

	}

	/**
	 * 
	 * 分公司各类型统计数量表导出
	 * 
	 */
	@Override
	public void frefectureRoutingFacilityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "分公司", "标石", "地标",
				"电杆", "非路由标志", "护坎（坡）", "接头盒", "警示牌", "人井", "宣传牌" });
		List<String> code = Arrays.asList(new String[] { "NAME", "MARKSTONE",
				"LANDMARK", "POLE", "ROUNTFLAG", "SCARP_PROTECTION", "CLOSURE",
				"CAUTION", "WELLS", "BILLBOARD" });
		List<Map<String, Object>> data = xxdReportDao
				.getFrefectureRoutingFacility(para);
		String fileName = "分公司各类型统计数量表 (" + para.get("min_time") + "至"
				+ para.get("max_time") + ")";
		String firstLine = "分公司各类型统计数量表(" + para.get("min_time") + "至"
				+ para.get("max_time") + ")";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 市设施总体情况统计表
	 */
	@Override
	public List<Map<String, Object>> getCityRoutingFacility(
			Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao
				.getCityRoutingFacility(para);
		return olists;

	}

	/**
	 * 
	 * 市设施总体情况统计表导出
	 * 
	 */
	@Override
	public void getCityRoutingFacilityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays
				.asList(new String[] { "设施类型 ", "数量（N）", "已检查数量（n）",
						"检查完成率（n/N*100%）", "问题设施数量（t）", "设施完好率（1-t/n）*100%" });
		List<String> code = Arrays.asList(new String[] { "EQUIP_TYPE_NAME",
				"TNUMEBER", "CHECK_NUM", "CHECK_RATE", "TROUBLE_NUM",
				"COMPLETE_RATE" });
		List<Map<String, Object>> data = xxdReportDao
				.getCityRoutingFacility(para);
		String fileName = "市设施总体情况统计表 (" + para.get("min_time") + "至"
				+ para.get("max_time") + ")";
		String firstLine = "市设施总体情况统计表 (" + para.get("min_time") + "至"
				+ para.get("max_time") + ")";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 全省隐患单统计页面
	 * 
	 */
	@Override
	public List<Map<String, Object>> getProvinceDangerOrder(
			Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao
				.getProvinceDangerOrder(para);
		Map<String, Object> listAll = xxdReportDao
				.getProvinceDangerOrderByAll(para);
		String name = "全省";
		listAll.put("NAME", name);
		olists.add(listAll);
		return olists;

	}

	/**
	 * 全省隐患单统计页面导出功能
	 * 
	 */
	@Override
	public void getProvinceDangerOrderDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "分公司 ", "上报隐患个数",
				"已派单隐患个数", "派单比例", "已处理完成隐患单数量", "整治完成比例" });
		List<String> code = Arrays.asList(new String[] { "NAME", "REPORT_NUM",
				"DISTRIBUTE_NUM", "DISTRIBUTE_RATE", "HANDLE_NUM",
				"COMPLETE_RATE" });
		List<Map<String, Object>> data = this.getProvinceDangerOrder(para);
		String fileName = "全省隐患单统计表 (" + para.get("min_time") + "至"
				+ para.get("max_time") + ")";
		String firstLine = "全省隐患单统计表 (" + para.get("min_time") + "至"
				+ para.get("max_time") + ")";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Map<String, Object>> getDetailUI(Map<String, Object> para) {
		Map<String, String> paramap = new HashMap<String, String>();
		if (para.get("equip_type").equals("1")) {
			paramap.put("distances", "50");
		} else {
			if (para.get("equip_type").equals("3")) {
				paramap.put("distances", "7");
			} else {
				paramap.put("distances", "200");
			}
		}
		para.put("distances", paramap.get("distances").toString());

		return xxdReportDao.getDetailUI(para);
	}

	/**
	 * 
	 * 设施密度统计
	 * 
	 */
	@Override
	public List<Map<String, Object>> getFacilityDensity(Map<String, Object> para) {
		List<Map<String, Object>> olists = xxdReportDao.getEquipDensity(para);
		return olists;
	}

	/**
	 * 设施密度统计页面导出功能
	 * 
	 */
	@Override
	public void getFacilityDensityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "分公司 ",
				"标石间距超标（>50米个数） ", "地标间距超（>7米）个数 ", "派宣传牌间距超标（>200米）个数 " });
		List<String> code = Arrays.asList(new String[] { "NAME", "MARKSTONE",
				"LANDMARK", "BILLBOARD" });
		List<Map<String, Object>> data = xxdReportDao.getEquipDensity(para);
		String fileName = "设施密度统计表 ";
		String firstLine = "设施密度统计表  ";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Map<String, Object>> getStepTourCondition(
			Map<String, Object> para) {
		List<Map<String, Object>> resultlist = xxdReportDao
				.getStepTourCondition(para);
		return resultlist;
	}

	@Override
	public List<Map<String, Object>> getStepTypeTourCondition(
			Map<String, Object> para) {
		List<Map<String, Object>> resultlist = xxdReportDao
				.getStepTypeTourCondition(para);
		return resultlist;
	}

	@Override
	public List<Map<String, Object>> getProvinceStepTourCondition(
			Map<String, Object> para) {
		List<Map<String, Object>> resultlist = xxdReportDao
				.getProvinceStepTourCondition(para);
		List<Map<String, Object>> list = xxdReportDao.getProvienceData(para);
		resultlist.addAll(list);
		return resultlist;
	}

	@Override
	public List<Map<String, Object>> getStepDetailUI(Map<String, Object> para) {
		List<Map<String, Object>> resultlist = xxdReportDao
				.getStepDetailUI(para);
		return resultlist;
	}

	@Override
	public void getStepTourConditionDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "步巡人员 ", "区域 ",
				"一干计划步巡点数 ", "二干计划步巡点数 ", "地标计划步巡点数 ", "一干实际步巡点数", "二干实际步巡点数",
				"地标实际步巡点数", "一干完成比例", "二干完成比例", "地标完成比例", "查询时间" });
		List<String> code = Arrays.asList(new String[] { "STAFF_NAME",
				"ORG_NAME", "FIBER_1", "FIBER_2", "LANDMARK", "ACT_FIBER_1",
				"ACT_FIBER_2", "ACT_LANDMARK", "FIBER_1_RATE", "FIBER_2_RATE",
				"LANDMARK_RATE", "SEARCH_DATE" });
		List<Map<String, Object>> data = xxdReportDao
				.getStepTourCondition(para);
		String fileName = "步巡设施完成情况统计表 ";
		String firstLine = "步巡设施完成情况统计表";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getStepTypeTourConditionDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "步巡人员 ", "区域 ",
				"计划步巡点数 ", "实际步巡点数", "完成比例", "查询时间" });
		List<String> code = Arrays.asList(new String[] { "STAFF_NAME",
				"ORG_NAME", "TOL_NUM", "ACT_NUM", "RATE", "SEARCH_DATE" });
		List<Map<String, Object>> data = xxdReportDao
				.getStepTypeTourCondition(para);
		String fileName = "步巡设施单类型完成情况统计表 ";
		String firstLine = "步巡设施单类型完成情况统计表";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getProvinceStepTourConditionDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "区域 ", "一干计划数量",
				"一干已完成数量", "一干问题数量", "一干完成比例", "一干完好比例", "二干计划数量", "二干已完成数量",
				"二干问题数量", "二干完成比例", "二干完好比例", "地标计划数量", "地标已完成数量", "地标问题数量",
				"地标完成比例", "地标完好比例", "查询时间" });
		List<String> code = Arrays.asList(new String[] { "NAME", "FIBER_1_NUM",
				"FIBER_1_ACT", "DANGER_NUM1", "FIBER_1_RATE",
				"DANGER_NUM1_RATE", "FIBER_2_NUM", "FIBER_2_ACT",
				"DANGER_NUM2", "FIBER_2_RATE", "DANGER_NUM2_RATE",
				"LANDMARK_NUM", "LANDMARK_ACT", "DANGER_LANDMARK",
				"LANDMARK_RATE", "DANGER_LANDMARK_RATE", "SEARCH_DATE" });
		List<Map<String, Object>> data = this
				.getProvinceStepTourCondition(para);
		String fileName = "全省步巡设施完成情况统计表 ";
		String firstLine = "全省步巡设施完成情况统计表";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void equipDiatanceDelete(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String selected = request.getParameter("selected");
		String[] euqips = selected.split(",");
		for (int i = 0; i < euqips.length; i++) {
			map.put("EQUIP_ID", euqips[i]);
			Map<String, Object> olists = xxdReportDao.getEquipDistanceId(map);
			xxdReportDao.updateEquipDensity(map);
			xxdReportDao.updateEquipDistance(map);
			xxdReportDao.updateEquipDistance(olists);
		}

	}

	@Override
	public void equipDiatanceCancel(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String selected = request.getParameter("selected");
		String[] euqips = selected.split(",");
		for (int i = 0; i < euqips.length; i++) {
			map.put("EQUIP_ID", euqips[i]);
			Map<String, Object> olists = xxdReportDao.getEquipDistanceId(map);
			xxdReportDao.cancelEquipDensity(map);
			xxdReportDao.cancelEquipDistance(map);
			xxdReportDao.cancelEquipDistance(olists);
		}

	}

	@Override
	public List<Map<String, Object>> getVariousStepType(Map<String, Object> para) {
		
		List<Map<String, Object>> resultlist = xxdReportDao.getVariousStepType(para);

		return resultlist;

	}

	@Override
	public void getVariousStepTypeDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "高标石 ", "矮标石 ",
				"有标石套 ", "无套标石", "普通标石", "接头标石", "余缆标石", "转角标石", "不锈钢地标",
				"橡胶地标", "瓷砖地标", "地砖地标", "圆井盖人井", "方井盖人井", "双井盖人井", "三井盖人井",
				"铸铁", "复合", "长途专用", "非长途专用", "普通井", "接头井", "余缆井",
				"小号(小于60x80)宣传牌", "60x80宣传牌", "中号(介于大小号之间)宣传牌", "120x100宣传牌",
				"大号(大于120x100)宣传牌", "8米", "8-10米", "10米以上", "水泥电杆", "木电杆",
				"普通杆", "引上(下)杆", "角杆", "H杆", "警示牌", "限高牌", "宣传标语", "水线牌", "砖砌",
				"石块砌", "喷漆", "贴搪瓷牌", "贴瓷砖", "卧式", "帽式", "架空", "直埋", "管道", "3M",
				"瑞侃", "5年内", "5-10年", "10-20年", ">=20年", "有托架", "无托架", "标识",
				"宣传牌", "警示牌" });
		List<String> code = Arrays.asList(new String[] { "HIGHREMARK",
				"LOWREMARK", "COVERREMARK", "UNCOVERREMARK", "COMMREMARK",
				"CONTECTREMARK", "CABLEREMARK", "CORNERREMARK", "POLISHED",
				"RUBBER", "CERAMIC", "GROUND", "ROUND", "SQUARE", "DOUBLE",
				"THREE", "IRON", "COMPLEX", "SPECIAL", "UNSPECIAL", "COMM",
				"CONTECT", "CABLE", "SMALL", "NUMBER1", "MIDDLE", "FIGURE",
				"LARGE", "HIGH1", "HIGH2", "HIGH3", "CONCRETE", "WOOD", "COMM",
				"UPPER", "ANGLE", "HPOLE", "WARN", "SLOGAN", "LIMIT", "LINE",
				"BRICK", "MASONRY", "SPARY", "PLASTER", "TITLING",
				"HORIZTONTAL", "CAP", "OVERHEAD", "FIGURE", "TUNNEL", "M",
				"RAYCHEM", "YEAR1", "YEAR2", "YEAR3", "YEAR4", "BRACKET",
				"NOBRACKET", "IDENTIFE", "BRAND", "SIGN" });
		List<Map<String, Object>> data = xxdReportDao.getVariousStepType(para);
		String fileName = "子类型设施情况统计表 ";
		String firstLine = "子类型设施情况统计表";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getAllGroup(String area_id) {
		return xxdReportDao.getAllGroup(area_id);
	}

	@Override
	public Map<String, Object> query(Map<String, Object> para, UIPage pager) {

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);

		List<Map> olists = xxdReportDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public List<Map<String, Object>> getStepEquipLocation(
			Map<String, Object> map) {

		return xxdReportDao.getStepEquipLocation(map);
	}

	@Override
	public void stepequipdDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "步巡设施ID", "步巡设施编号",
				"步巡设施类型 ", "状态 ", "检查人", "最新检查时间 " });
		List<String> code = Arrays.asList(new String[] { "EQUIP_ID",
				"EQUIP_CODE", "EQUIP_TYPE_NAME", "STATUS", "STAFF_NAME",
				"CHECK_TIME" });
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("p_inspect_name", para.get("p_inspect_name"));
		paras.put("org_id", para.get("org_id"));
		paras.put("p_equip_name", para.get("p_equip_name"));
		paras.put("p_start_time", para.get("p_start_time"));
		paras.put("p_end_time", para.get("p_end_time"));
		paras.put("area_id", para.get("area_id"));
		List<Map<String, Object>> data = xxdReportDao.queryDown(paras);

		String fileName = "步巡设施问题清单报表";
		String firstLine = "步巡设施问题清单报表";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getProvienceOfStaffData(
			Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> olists = xxdReportDao
				.getProvienceOfStaffData(para);
		return olists;

	}

	public void getProvienceOfStaffDataDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "地市", "组织关系", "巡线员",
				"上午巡线时长 ", "下午巡线时长", "外力配合时长", "巡线日期", "开始巡线时间", "结束巡线时间" });
		List<String> code = Arrays.asList(new String[] { "NAME", "ORG_NAME",
				"STAFF_NAME", "INS_TIME_VALID_MORNING",
				"INS_TIME_VALID_AFTERNOON", "OS_NURSE_TIME", "INSPECT_DATE",
				"START_TIME", "END_TIME" });
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("start_time", para.get("start_time"));
		paras.put("end_time", para.get("end_time"));
		paras.put("area_id", para.get("area_id"));
		List<Map<String, Object>> data = xxdReportDao.getProvienceOfStaffData(paras);
		String fileName = para.get("start_time").toString() + "到"
		+ para.get("end_time") +"全省巡线员时长详单";
		String firstLine = para.get("start_time").toString() + "到"
		+ para.get("end_time") +"全省巡线员时长详单";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
