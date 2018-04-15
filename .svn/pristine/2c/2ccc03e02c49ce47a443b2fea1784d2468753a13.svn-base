package com.linePatrol.service.impl;

import icom.axx.service.LineSiteInterfaceService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.axxreport.dao.XxdReportDao;
import com.axxreport.service.XxdReportService;
import com.linePatrol.dao.LineJobDao;
import com.linePatrol.util.DateUtil;
import com.system.dao.ParamDao;
import com.system.service.ParamService;
import com.util.MapDistance;

/**
 * 巡线计划任务生成
 * 
 * @author huliubing
 * @since 2015-3-3
 */
@SuppressWarnings("all")
public class InspectHelperJob {

	@Resource
	private LineJobDao lineJobDao;

	@Resource
	private ParamService paramService;

	@Resource
	private ParamDao paramDao;

	@Resource
	private LineSiteInterfaceService lineSiteInterfaceService;

	@Autowired
	private XxdReportService xxdReportService;

	@Autowired
	private XxdReportDao xxdReportDao;

	public void execute() {

		// 查询周期巡线计划
		autoTask();// 自动生成任务

		// 自动看护任务
		autoGuardJobs();
	}
	/**
	 * 配置文件：/ins/src/com/linePatrol/xml/line-quartz.xml
	 * 执行时间：每天下午2：30执行
	 */
	public void calTime() {
		// 计算巡线时长
		calInvalidTime();

		// 计算巡检到位率
		calInpectArrayRate();

		// 计算看护到位率
		baseGuardRate();
	}

	public void guardLine() {

		// 由上传的轨迹得到所有的无效轨迹数据 入库 看护无效时长
		baseKanHuInfo();
	}

	public void facilityDensity() {
		// 计算设施密度
		countFacilitydensity();
	}

	private void countFacilitydensity() {
		try {
			xxdReportDao.deleteEquipDensity(new HashMap<String, Object>());
			List<Map<String, Object>> relayidList = xxdReportDao.getrelpyid(new HashMap<String, Object>());
			for (Map<String, Object> relayid : relayidList) {
				String relay_id = String.valueOf(relayid.get("RELAY_ID"));
				List<Map<String, Object>> areaList = xxdReportDao.getAreaList(new HashMap<String, Object>());
				for (Map<String, Object> areaid : areaList) {
					String area_id = String.valueOf(areaid.get("AREA_ID"));
					List<Map<String, Object>> typeList = xxdReportDao
							.getequiptype(new HashMap<String, Object>());
					for (Map<String, Object> type : typeList) {
						String equip_type = String.valueOf(type
								.get("EQUIP_TYPE"));
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("RELAY_ID", relay_id);
						param.put("EQUIP_TYPE", equip_type);
						param.put("AREA_ID", area_id);
						List<Map<String, Object>> list = xxdReportDao
								.getFacilityDensity(param);
						String start_equip = "";
						String end_equip = "";
						String start_equip_longitude = "";
						String start_equip_latitude = "";
						String end_equip_longitude = "";
						String end_equip_latitude = "";
						String reaply_id = "";
						String is_over = "";
						for (int i = 0; i < list.size() - 1; i++) {
							start_equip = list.get(i).get("EQUIP_CODE")
									.toString();
							end_equip = list.get(i + 1).get("EQUIP_CODE")
									.toString();
							start_equip_longitude = list.get(i)
									.get("LONGITUDE").toString();
							start_equip_latitude = list.get(i).get("LATITUDE")
									.toString();
							end_equip_longitude = list.get(i + 1).get(
									"LONGITUDE").toString();
							end_equip_latitude = list.get(i + 1)
									.get("LATITUDE").toString();
							double distance = MapDistance.getDistance(Double
									.valueOf(start_equip_longitude), Double
									.valueOf(start_equip_latitude), Double
									.valueOf(end_equip_longitude), Double
									.valueOf(end_equip_latitude));
							Map<String, Object> paramap = new HashMap<String, Object>();
							String sign_1 = list.get(i).get("IS_OVER")
									.toString();
							String sign_2 = list.get(i + 1).get("IS_OVER")
									.toString();
							String sign_3 = "1";
							if (sign_1.equals(sign_3) && sign_2.equals(sign_3)) {
								paramap.put("REMARK", 1);
							} else {
								paramap.put("REMARK", 0);
							}
							paramap.put("START_EQUIP_ID", list.get(i).get(
									"EQUIP_ID"));
							paramap.put("END_EQUIP_ID", list.get(i + 1).get(
									"EQUIP_ID"));
							paramap.put("START_EQUIP_CODE", start_equip);
							paramap.put("END_EQUIP_CODE", end_equip);
							paramap.put("EQUIP_TYPE", equip_type);
							paramap.put("DISTANCE", distance);
							paramap
									.put("CABLE_ID", list.get(i)
											.get("CABLE_ID"));
							paramap.put("AREA_ID", list.get(i).get("AREA_ID"));
							paramap
									.put("RELAY_ID", list.get(i)
											.get("RELAY_ID"));
							xxdReportDao.saveEquipDensity(paramap);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void baseKanHuInfo() {
		try {
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("query_time", getBeforeTime());
			List<String> planIds = xxdReportDao.getNurseList(mp);
			String query_time = getBeforeTime();
			List<Map<String, Object>> list = xxdReportService.osNurseDailySet(planIds, query_time);
			for (Map<String, Object> map : list) {
				Map<String, Object> paramap = new HashMap<String, Object>();
				paramap.put("user_id", map.get("user_id"));
				paramap.put("plan_time", getBeforeTime());
				paramap.put("plan_id", map.get("plan_id"));
				if (map.get("timePart") != null && !("").equals(map.get("timePart").toString())) {
					String[] timePart = map.get("timePart").toString().split(",");
					for (String str : timePart) {
						paramap.put("start_time", query_time + " " + str.split("~")[0]);
						paramap.put("end_time", query_time + " " + str.split("~")[1]);
						paramap.put("invalid_type", 1);
						xxdReportDao.saveinvalidTime(paramap);
					}
				}
				if (map.get("timePartByValid") != null && !("").equals(map.get("timePartByValid").toString())) {
					String[] timePart = map.get("timePartByValid").toString().split(",");
					for (String str : timePart) {
						paramap.put("start_time", query_time + " " + str.split("~")[0]);
						paramap.put("end_time", query_time + " " + str.split("~")[1]);
						paramap.put("invalid_type", 2);
						xxdReportDao.saveinvalidTime(paramap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * TODO 计算看护到位率
	 */
	private void baseGuardRate() {
		try {
			Map<String, Object> para = new HashMap<String, Object>();
			String lastDay = getBeforeTime();
//			String lastDay = "2018-01-05";
			para.put("start_query_time", lastDay);
			para.put("end_query_time", lastDay);
			List<Map<String, Object>> list = xxdReportService.getOsNurseDaily(para);
			for (int i = 0; i < list.size(); i++) {
				String org_id = list.get(i).get("org_id").toString();
//				if("2340".equals(org_id)){//test
					Map<String, Object> listMap = new HashMap<String, Object>();
					String query_time = lastDay;
					listMap.put("date", query_time);
					listMap.put("org_id", org_id);
					String rate = list.get(i).get("rateOfKanHu").toString();
					if (rate.contains("%")) {
						float NUM = new Float(rate.substring(0, rate.indexOf("%"))) / 100;
						float num = ((float) Math.round(NUM * 10000) / 10000);
						String rateOfKanHu = num + "";
						listMap.put("rateOfKanHu", rateOfKanHu);
					}
					listMap.put("planTime", list.get(i).get("planTime").toString());
					listMap.put("out_site_id", list.get(i).get("out_site_id").toString());
					listMap.put("plan_id", list.get(i).get("planId").toString());
					listMap.put("staff_id", list.get(i).get("staff_id").toString());
					listMap.put("workTime", list.get(i).get("workTime").toString());
					xxdReportDao.saveguardRate(listMap);
				}
				
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("this is over");

	}

	private void autoTask() {
		List<Map<String, Object>> jobList = lineJobDao
				.queryJobsByCycle(new HashMap<String, Object>());

		for (Map<String, Object> job : jobList) {
			autoTaskByArea(job);
		}
	}

	private void autoTaskByArea(Map<String, Object> job) {
		try {
			// 查询巡线人员（1.根据区域、干线等级和计划查询所有当天没有被分配巡线任务的人，2.根据巡线段获取所有的巡线人员）
			List<Map<String, Object>> staffList = lineJobDao
					.queryStaffsByJob(job);
			for (Map<String, Object> staff : staffList) {
				Map<String, Object> taskMap = new HashMap<String, Object>();
				taskMap.put("INSPECT_ID", staff.get("INSPECT_ID"));
				taskMap.put("JOB_ID", job.get("JOB_ID"));
				taskMap.put("TASK_NAME", job.get("JOB_NAME")
						+ DateUtil.getDate());
				taskMap.put("FIBER_GRADE", job.get("FIBER_GRADE"));
				taskMap.put("AREA_ID", job.get("AREA_ID"));
				String circle = job.get("CIRCLE_ID").toString();
				taskMap.put("CIRCLE_ID", circle);
				if ("1".equals(circle)) {
					taskMap.put("END_DAY", "1");
				} else if ("2".equals(circle)) {
					taskMap.put("END_DAY", "2");
				} else {
					taskMap.put("END_DAY", "1");
				}

				taskMap.put("TASK_ID", lineJobDao.getTaskId());// 获取任务ID
				lineJobDao.inserTaskByCycle(taskMap);// 插入周期任务
				lineJobDao.insertTaskItem(taskMap);// 插入任务项
				if ("1".equals(circle)) {// 每天巡线任务增加外力点检查
					List<Map<String, Object>> outsiteTasks = lineJobDao
							.queryOutsiteTaskByUser(taskMap);
					if (null == outsiteTasks || outsiteTasks.size() == 0) {
						taskMap.put("INSPECT_DATE", DateUtil.getDate());
						lineJobDao.insertTaskOutSite(taskMap);// 插入任务关联的外力点
					}
				} else if ("2".equals(circle)) {
					List<Map<String, Object>> outsiteTasks = lineJobDao
							.queryOutsiteTaskByUser(taskMap);
					if (null == outsiteTasks || outsiteTasks.size() == 0) {
						taskMap.put("INSPECT_DATE", DateUtil.getDate());
						lineJobDao.insertTaskOutSite(taskMap);// 插入任务关联的外力点

						taskMap.put("INSPECT_DATE", DateUtil.getNextDate());
						lineJobDao.insertTaskOutSite(taskMap);// 插入任务关联的外力点
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void autoGuardJobs() {
		try {
			// 生成看护任务详情
			lineJobDao.insertGuardJobs(new HashMap<String, Object>());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void calInvalidTime() {
		try {
			// 计算巡线时长
			paramService.calLineTime(DateUtil.getYesterDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void calInpectArrayRate() {
		try {
			// 计算巡检到位率
			paramService.insertInspectArrate(DateUtil.getYesterDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("this is over");
	}

	/**
	 * 推送定时任务
	 */
	public void pushMessage() {
		//paramService.pushMessage();// 消息推送
	}

	public void handCalMatchSites() {
		System.out.println("this is start");
		String[] dateStr = { "2015-08-20" };
		for (String date : dateStr) {
			List<Map<String, Object>> areaList = paramDao
					.getAllAreaByJS(new HashMap<String, Object>());
			for (Map<String, Object> area : areaList) {
				List<Map<String, Object>> lineStaffs = paramDao
						.getLineStaffs(area);
				for (Map<String, Object> lineStaff : lineStaffs) {
					lineStaff.put("USER_ID", lineStaff.get("STAFF_ID"));
					lineStaff.put("queryDate", date);
					try {
						lineSiteInterfaceService.calAllMatchSites(lineStaff);
						System.out.println("this is success:"
								+ String.valueOf(lineStaff.get("STAFF_ID"))
								+ "&date=" + date);
					} catch (Exception e) {
						System.out.println("this is faild:"
								+ String.valueOf(lineStaff.get("STAFF_ID"))
								+ "&date=" + date);
					}
				}
			}
		}

		System.out.println("this is over");
	}

	public void calMatchSites() {
		System.out
				.println("---------------------"
						+ DateUtil.getDateAndTime()
						+ " calMatchSites--------------------------------------------------");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("AREA_ID", "0,3,20,60");// 处理南京、苏州、淮安
		List<Map<String, Object>> matchSites = lineSiteInterfaceService
				.queryMatchQuartzInfos(param);
		if (null != matchSites && matchSites.size() > 0) {
			for (Map<String, Object> matchSite : matchSites) {
				try {
					lineSiteInterfaceService.calSaveMatchSites(matchSite);
				} catch (Exception e) {
					try {
						lineSiteInterfaceService
								.updateMatchQuartzInfo(matchSite);
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		}
	}

	public void calMatchSitesTwo() {
		System.out
				.println("---------------------"
						+ DateUtil.getDateAndTime()
						+ " calMatchSitesTwo--------------------------------------------------");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("AREA_ID", "48,79,84");// 处理徐州、泰州、宿迁
		List<Map<String, Object>> matchSites = lineSiteInterfaceService
				.queryMatchQuartzInfos(param);
		if (null != matchSites && matchSites.size() > 0) {
			for (Map<String, Object> matchSite : matchSites) {
				try {
					lineSiteInterfaceService.calSaveMatchSites(matchSite);
				} catch (Exception e) {
					try {
						lineSiteInterfaceService
								.updateMatchQuartzInfo(matchSite);
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		}
	}

	public void calMatchSitesThree() {
		System.out
				.println("---------------------"
						+ DateUtil.getDateAndTime()
						+ " calMatchSitesThree--------------------------------------------------");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("AREA_ID", "63,39,26");// 处理连云港、盐城、南通
		List<Map<String, Object>> matchSites = lineSiteInterfaceService
				.queryMatchQuartzInfos(param);
		if (null != matchSites && matchSites.size() > 0) {
			for (Map<String, Object> matchSite : matchSites) {
				try {
					lineSiteInterfaceService.calSaveMatchSites(matchSite);
				} catch (Exception e) {
					try {
						lineSiteInterfaceService
								.updateMatchQuartzInfo(matchSite);
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		}
	}

	public void calMatchSitesFour() {
		System.out.println("---------------------" + DateUtil.getDateAndTime() + " calMatchSitesFour--------------------------------------------------");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("AREA_ID", "69,33,15,4");// 处理常州、扬州、无锡、镇江
		List<Map<String, Object>> matchSites = lineSiteInterfaceService.queryMatchQuartzInfos(param);
		if (null != matchSites && matchSites.size() > 0) {
			for (Map<String, Object> matchSite : matchSites) {
				try {
					lineSiteInterfaceService.calSaveMatchSites(matchSite);
				} catch (Exception e) {
					try {
						lineSiteInterfaceService.updateMatchQuartzInfo(matchSite);
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		}
	}

	/**
	 * 获取前一天的时间
	 * 
	 * @param args
	 * @return
	 */
	private String getBeforeTime() {

		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();

		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		String defaultStartDate = sdf.format(dBefore); // 格式化前一天

		return defaultStartDate;

	}
	
	public static void main(String[] args) {
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();

		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		String defaultStartDate = sdf.format(dBefore); // 格式化前一天
		System.out.println(defaultStartDate);
	}

	/**
	 * 步巡任务分配
	 */
	public void allotTaskOfStepTour() {
		try {
			xxdReportService.allotTaskOfStepTour();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
