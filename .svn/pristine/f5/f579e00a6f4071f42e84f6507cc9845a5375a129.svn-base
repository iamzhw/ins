package com.cableInspection.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.ArrivalDao;
import com.cableInspection.dao.LxxjCityDao;
import com.cableInspection.dao.PointDao;
import com.cableInspection.model.PointModel;
import com.cableInspection.service.ArrivalService;
import com.cableInspection.webservice.Wfworkitemflow;
import com.cableInspection.webservice.WfworkitemflowLocator;
import com.cableInspection.webservice.WfworkitemflowSoap11BindingStub;
import com.system.constant.AppType;
import com.system.constant.RoleNo;
import com.system.constant.SysSet;
import com.system.dao.RoleDao;
import com.util.DateUtil;
import com.util.MapDistance;
import com.util.StringUtil;
import com.util.Triangle;

@SuppressWarnings("all")
@Service
public class ArrivalServiceImpl implements ArrivalService {

	@Resource
	private ArrivalDao arrivalDao;

	@Resource
	private RoleDao roleDao;
	
	@Resource
	private LxxjCityDao lxxjCityDao;
	
	@Resource
	private PointDao pointDao;

	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {

		Map map = new HashMap();
		map.put("staff_name", request.getParameter("staff_name"));
		map.put("task_name", request.getParameter("task_name"));
		map.put("start_time", request.getParameter("start_time"));
		map.put("end_time", request.getParameter("end_time"));
		map.put("TYPE", request.getParameter("TYPE"));
		//新增地市和区域查询
		String area_id=request.getParameter("area_id");
		String son_area_id=request.getParameter("son_area_id");

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {// 如果是审核员只能查到本区域的
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}
		if(null!=area_id&&!("").equals(area_id)){
			map.put("AREA_ID", area_id);
		}
		if(null!=son_area_id&&!("").equals(son_area_id)){
			map.put("SON_AREA_ID", son_area_id);
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<PointModel> list3 = new ArrayList<PointModel>();
		List<PointModel> list1 = new ArrayList<PointModel>();
		List<PointModel> gjPointList = new ArrayList<PointModel>();
		List<PointModel> notSignNormalPoints = new ArrayList<PointModel>();
		List<Double> pointList =new ArrayList<Double>();
		List<Map> list = arrivalDao.query(query);
		for (Map map1 : list) {
			Map map2 = new HashMap();
			map2.put("PLAN_ID", map1.get("PLANID"));
			map2.put("TASK_ID", map1.get("TASK_ID"));
			// 获取任务签到的关键点
			list3 = arrivalDao.queryPoint2(map2);
			// 获取任务中的非关键点
			list1 = arrivalDao.queryPoint(map2);
			// 获取任务中的关键点
			gjPointList = arrivalDao.queryPoint11(map2);
			// 获取计划巡线点数
			//String planPointsNum = arrivalDao.getCountPoint(map1.get("PLANID").toString());
			String planPointsNum = list1.size()+gjPointList.size()+"";
			map1.put("PLANPOINTS", planPointsNum);
			map2.put("staff_id", map1.get("STAFF_ID"));
			map2.put("plan_start_time", map1.get("PLAN_START_TIME"));
			map2.put("plan_end_time", map1.get("PLAN_END_TIME"));
			// 不断上传的点
			int sum=0;

			List<PointModel> list2 = arrivalDao.queryPoint1(map2);
			if(arrivalDao.getCountStaffIdByDept(map2)>0){
				list2 = arrivalDao.queryDeptUpLoadPoint(map2);
			}else{
				list2 = arrivalDao.queryPoint1(map2);
			}
				// 实际到非关键点数量
			notSignNormalPoints=arrivalDao.getNotSignNormalByTaskId(map2);
			if(!notSignNormalPoints.isEmpty()){
				pointList = new Triangle().arrivalPointsList(notSignNormalPoints,
						list2);
			}		
			
			sum = pointList.size()+(list1.size()-notSignNormalPoints.size());
			// 应该到位点数量
			// int sum1 =Integer.parseInt(map1.get("PLANPOINTS").toString());
			int sum1 = gjPointList.size() + list1.size();
			// 到位率
			Double sh = 0d;
			
			// String kime = new Triangle().gj(pointList);
			// Double time = 0.00000000000;
			// for (Double point : pointList) {
			// time += point;
			// }
			// map1.put("KILOMETRE", time / 1000);
			

			// 计算关键点统计
			// 获取任务轨迹点
			// list3 = arrivalDao.queryPoint22(map2);
			// 获取任务计划点
			// list1 = arrivalDao.queryPoint11(map2);
			// 实际到位点数量
			// pointList = new Triangle().arrivalPointsList(list1, list2);
			int sumGj1 = gjPointList.size();

			// sumGj = sumGj + list3.size();
			// 应该到位点数量
			// int sumGj1 = list1.size() + list3.size();
			// 到位率
			Double shGj = 0d;
			if (sumGj1 > 0) {
				shGj = (double) list3.size() / sumGj1;
			}

			// 查询出任务的所有点
			List<PointModel> planPointList = arrivalDao.queryPlanPoints(map2);

			// 巡线公里数
			double distance = 0d;

			// 按照线路ID将任务点分类存入Map
			Map<String, List<PointModel>> lineMap = getLineMap(planPointList);
			if (MapUtils.isNotEmpty(lineMap)) {
				for (Map.Entry<String, List<PointModel>> lineList : lineMap
						.entrySet()) {
					distance += new Triangle().gj2(lineList.getValue());
				}
			}

			DecimalFormat df = new DecimalFormat("0.00");
			map1.put("KILOMETRE", distance>0?String.valueOf(df.format(distance)):lxxjCityDao.getDistance(map1.get("TASK_ID").toString()));
			map1.put("PLANPOINTS2", sumGj1);
			map1.put("REALPOINTS2", list3.size());
			map1.put("REALPOINTS", sum+list3.size());
			if (sum1 > 0) {
				sh = (sum+list3.size()) / Double.parseDouble(planPointsNum);
			}
			map1.put("ARRIVALRATE", new Triangle().bfb(sh));
			map1.put("ARRIVALRATE2", new Triangle().bfb(shGj));
			map1.put("TIMECOUNT", lxxjCityDao.getTimeCount(map1.get("TASK_ID").toString()));
			map1.put("TROUBLEPOINTS", lxxjCityDao.getTroubleCount(map1.get("TASK_ID").toString()));

		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public List<Map> queryExl(HttpServletRequest request) {
		Map map = new HashMap();
		map.put("staff_name", request.getParameter("staff_name"));
		map.put("task_name", request.getParameter("task_name"));
		map.put("start_time", request.getParameter("start_time"));
		map.put("end_time", request.getParameter("end_time"));
		map.put("TYPE", request.getParameter("TYPE"));
		//新增地市和区域查询
		String area_id=request.getParameter("area_id");
		String son_area_id=request.getParameter("son_area_id");

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {// 如果是审核员只能查到本区域的
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}
		if(null!=area_id&&!("").equals(area_id)){
			map.put("AREA_ID", area_id);
		}
		if(null!=son_area_id&&!("").equals(son_area_id)){
			map.put("SON_AREA_ID", son_area_id);
		}
		List<PointModel> list3 = new ArrayList<PointModel>();
		List<PointModel> list1 = new ArrayList<PointModel>();
		List<PointModel> gjPointList = new ArrayList<PointModel>();
		List<PointModel> notSignNormalPoints = new ArrayList<PointModel>();
		List<Double> pointList =new ArrayList<Double>();
		List<Map> list = arrivalDao.queryExl(map);
		for (Map map1 : list) {
			Map map2 = new HashMap();
			map2.put("PLAN_ID", map1.get("PLANID"));
			map2.put("TASK_ID", map1.get("TASK_ID"));
			// 获取任务签到的关键点
			list3 = arrivalDao.queryPoint2(map2);
			// 获取任务中的非关键点
			list1 = arrivalDao.queryPoint(map2);
			// 获取任务中的关键点
			gjPointList = arrivalDao.queryPoint11(map2);
			// 获取计划巡线点数
			//String planPointsNum = arrivalDao.getCountPoint(map1.get("PLANID").toString());
			String planPointsNum = list1.size()+gjPointList.size()+"";
			map1.put("PLANPOINTS", planPointsNum);
			map2.put("staff_id", map1.get("STAFF_ID"));
			map2.put("plan_start_time", map1.get("PLAN_START_TIME"));
			map2.put("plan_end_time", map1.get("PLAN_END_TIME"));
			// 不断上传的点
			int sum=0;

			List<PointModel> list2 = arrivalDao.queryPoint1(map2);
			if(arrivalDao.getCountStaffIdByDept(map2)>0){
				list2 = arrivalDao.queryDeptUpLoadPoint(map2);
			}else{
				list2 = arrivalDao.queryPoint1(map2);
			}
				// 实际到非关键点数量
			notSignNormalPoints=arrivalDao.getNotSignNormalByTaskId(map2);
			if(!notSignNormalPoints.isEmpty()){
				pointList = new Triangle().arrivalPointsList(notSignNormalPoints,
						list2);
			}		
			
			sum = pointList.size()+(list1.size()-notSignNormalPoints.size());
			// 应该到位点数量
			// int sum1 =Integer.parseInt(map1.get("PLANPOINTS").toString());
			int sum1 = gjPointList.size() + list1.size();
			// 到位率
			Double sh = 0d;
			
			// String kime = new Triangle().gj(pointList);
			// Double time = 0.00000000000;
			// for (Double point : pointList) {
			// time += point;
			// }
			// map1.put("KILOMETRE", time / 1000);
			

			// 计算关键点统计
			// 获取任务轨迹点
			// list3 = arrivalDao.queryPoint22(map2);
			// 获取任务计划点
			// list1 = arrivalDao.queryPoint11(map2);
			// 实际到位点数量
			// pointList = new Triangle().arrivalPointsList(list1, list2);
			int sumGj1 = gjPointList.size();

			// sumGj = sumGj + list3.size();
			// 应该到位点数量
			// int sumGj1 = list1.size() + list3.size();
			// 到位率
			Double shGj = 0d;
			if (sumGj1 > 0) {
				shGj = (double) list3.size() / sumGj1;
			}

			// 查询出任务的所有点
			List<PointModel> planPointList = arrivalDao.queryPlanPoints(map2);

			// 巡线公里数
			double distance = 0d;

			// 按照线路ID将任务点分类存入Map
			Map<String, List<PointModel>> lineMap = getLineMap(planPointList);
			if (MapUtils.isNotEmpty(lineMap)) {
				for (Map.Entry<String, List<PointModel>> lineList : lineMap
						.entrySet()) {
					distance += new Triangle().gj2(lineList.getValue());
				}
			}

			DecimalFormat df = new DecimalFormat("0.00");
			map1.put("KILOMETRE", distance>0?String.valueOf(df.format(distance)):lxxjCityDao.getDistance(map1.get("TASK_ID").toString()));
			map1.put("PLANPOINTS2", sumGj1);
			map1.put("PLANPOINTS", sum1);
			map1.put("REALPOINTS2", list3.size());
			map1.put("REALPOINTS", sum+list3.size());
			if (sum1 > 0) {
				sh = (sum+list3.size()) / Double.parseDouble(planPointsNum);
			}
			map1.put("ARRIVALRATE", new Triangle().bfb(sh));
			map1.put("ARRIVALRATE2", new Triangle().bfb(shGj));
			map1.put("TIMECOUNT", lxxjCityDao.getTimeCount(map1.get("TASK_ID").toString()));
			map1.put("TROUBLEPOINTS", lxxjCityDao.getTroubleCount(map1.get("TASK_ID").toString()));

		}
		return list;
	}
	
	//定时任务生成到位率表
	@Override
	public List<Map> query() {
		Map map = new HashMap();
		// 本月的第一天
		Date firstDateOfCurrMonth = DateUtil.getFirstDayOfCurrMonth();
		String str_firstDateOfCurrMonth = StringUtil.dateToString(
				firstDateOfCurrMonth, "yyyy-MM-dd");
		// 本月的最后一天
		Date lastDateOfCurrMonth = DateUtil.getLastDayOfCurrMonth();
		String str_lastDateOfCurrMonth = StringUtil.dateToString(
				lastDateOfCurrMonth, "yyyy-MM-dd");
		//今天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			firstDateOfCurrMonth = sdf.parse(str_firstDateOfCurrMonth);
			lastDateOfCurrMonth = sdf.parse(str_lastDateOfCurrMonth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(firstDateOfCurrMonth.compareTo(DateUtil.getCurrentDate())==0){
			Date date = new Date();
			map.put("start_time", DateUtil.getFirstDayOfLastMonth(date));
			map.put("end_time", DateUtil.getLastDayOfLastMonth(date));
			
		}else{
			map.put("start_time", str_firstDateOfCurrMonth);
			map.put("end_time", str_lastDateOfCurrMonth);
		}
		/*//上个月第一天和最后一天
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = sdf.format(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, 0);
		String lastDay  =sdf.format(cal.getTime());
		if(str_firstDateOfCurrMonth.split("-")[2].equals("01")){
			map.put("start_time", firstDay);
			map.put("end_time", lastDay);
		}else{
			map.put("start_time", str_firstDateOfCurrMonth);
			map.put("end_time", str_lastDateOfCurrMonth);
		}*/
		List<Map> list = arrivalDao.queryArrivalNOC(map);
		List<PointModel> list1 = new ArrayList();
		List<PointModel> list2 = new ArrayList();
		List<PointModel> list3 = new ArrayList();
		List<Double> pointList = new ArrayList();
		List<PointModel> gjPointList = new ArrayList();
		List<PointModel> planPointList  = new ArrayList();
		for (Map map1 : list) {
			Map map2 = new HashMap();
			map2.put("PLAN_ID", map1.get("PLANID"));
			map2.put("TASK_ID", map1.get("TASK_ID"));
			// 获取任务签到的关键点
			list3 = arrivalDao.queryPoint2(map2);
			// 获取任务中的非关键点
			list1 = arrivalDao.queryPoint(map2);
			// 获取任务中的关键点
			gjPointList = arrivalDao.queryPoint11(map2);
			//获取总点数
			String pointNum = arrivalDao.getCountPoint(map1.get("PLANID").toString());
			map1.put("PLANPOINTS", pointNum);
			map2.put("staff_id", map1.get("STAFF_ID"));
			map2.put("plan_start_time", map1.get("PLAN_START_TIME"));
			map2.put("plan_end_time", map1.get("PLAN_END_TIME"));
			// 不断上传的点
			list2 = arrivalDao.queryPoint1(map2);
			// 实际到非关键点数量
			pointList = new Triangle().arrivalPointsList(list1,
					list2);
			int sum = pointList.size();

			sum = sum + list3.size();
			// 应该到位点数量
			// int sum1 =Integer.parseInt(map1.get("PLANPOINTS").toString());
			int sum1 = gjPointList.size() + list1.size();
			// 到位率
			Double sh = 0d;
			if (sum1 > 0) {
				sh = (double) sum / sum1;
			}
			// String kime = new Triangle().gj(pointList);
			// Double time = 0.00000000000;
			// for (Double point : pointList) {
			// time += point;
			// }
			// map1.put("KILOMETRE", time / 1000);
			map1.put("REALPOINTS", sum);
			map1.put("ARRIVALRATE", new Triangle().bfb(sh));

			// 计算关键点统计
			// 获取任务轨迹点
			// list3 = arrivalDao.queryPoint22(map2);
			// 获取任务计划点
			// list1 = arrivalDao.queryPoint11(map2);
			// 实际到位点数量
			// pointList = new Triangle().arrivalPointsList(list1, list2);
			int sumGj1 = gjPointList.size();

			// sumGj = sumGj + list3.size();
			// 应该到位点数量
			// int sumGj1 = list1.size() + list3.size();
			// 到位率
			Double shGj = 0d;
			if (sumGj1 > 0) {
				shGj = (double) list3.size() / sumGj1;
			}

			// 查询出任务的所有点
			planPointList = arrivalDao.queryPlanPoints(map2);

			// 巡线公里数
			double distance = 0d;

			// 按照线路ID将任务点分类存入Map
			Map<String, List<PointModel>> lineMap = getLineMap(planPointList);
			if (MapUtils.isNotEmpty(lineMap)) {
				for (Map.Entry<String, List<PointModel>> lineList : lineMap
						.entrySet()) {
					distance += new Triangle().gj2(lineList.getValue());
				}
			}

			DecimalFormat df = new DecimalFormat("0.00");
			map1.put("KILOMETRE", String.valueOf(df.format(distance)));
			map1.put("PLANPOINTS2", sumGj1);
			map1.put("REALPOINTS2", list3.size());
			map1.put("ARRIVALRATE2", new Triangle().bfb(shGj));
			list1.clear();
			list2.clear();
			list3.clear();
			pointList.clear();
			gjPointList.clear();
			planPointList.clear();
		}
		return list;
	}
	
	//NOC到位率
	public String NOCQuery(HttpServletRequest request){
		Map map = new HashMap();
		map.put("son_area_id",request.getParameter("son_area_id"));
		List<Map> rs = arrivalDao.queryArrival(map);
		JSONArray arrays = JSONArray.fromObject(rs);
		JSONObject arrivals = new JSONObject();
		arrivals.put("arrivals", arrays);
		return arrivals.toString();
	}
	
	//定时生成到位率写入静态表
	public void addNewArrivalRates(){
		List<Map> rs = query();
		// 本月的第一天
		Date firstDateOfCurrMonth = DateUtil.getFirstDayOfCurrMonth();
		String str_firstDateOfCurrMonth = StringUtil.dateToString(
				firstDateOfCurrMonth, "yyyy-MM-dd");
		// 本月的最后一天
		Date lastDateOfCurrMonth = DateUtil.getLastDayOfCurrMonth();
		String str_lastDateOfCurrMonth = StringUtil.dateToString(
				lastDateOfCurrMonth, "yyyy-MM-dd");
		Map conds = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			firstDateOfCurrMonth = sdf.parse(str_firstDateOfCurrMonth);
			lastDateOfCurrMonth = sdf.parse(str_lastDateOfCurrMonth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(firstDateOfCurrMonth.compareTo(DateUtil.getCurrentDate())==0){
			//如果今天是1号，删除上个月2号到月底创建的数据
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, 2);
			String secondDay = sdf.format(cal.getTime());
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, -1);
			String lastDay  =sdf.format(cal.getTime());
			conds.put("start_time", secondDay);
			conds.put("end_time", lastDay);
			
		}else{
			//删除本月月2号到月底创建的数据
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 2);
			String secondDay = sdf.format(cal.getTime());
			conds.put("start_time", secondDay);
			conds.put("end_time", str_lastDateOfCurrMonth);
		}
//		if(arrivalDao.checkArrival(conds)>0){
			arrivalDao.deleteArrival(conds);
//		}
//			System.out.println(rs.size());
			for (Map map : rs) {
				arrivalDao.addArrivalSingle(map);
			}
//		arrivalDao.addArrival(rs);
	}

	private Map<String, List<PointModel>> getLineMap(
			List<PointModel> planPointList) {

		// 按照线路ID将任务点存入Map
		Map<String, List<PointModel>> lineMap = new HashMap<String, List<PointModel>>();

		// 该List存放相同线路ID的任务点
		List<PointModel> lineLst = null;

		if (CollectionUtils.isNotEmpty(planPointList)) {
			for (PointModel pointModel : planPointList) {

				lineLst = lineMap.get(pointModel.getLine());
				if (CollectionUtils.isEmpty(lineLst)) {
					lineLst = new ArrayList<PointModel>();
					lineMap.put(pointModel.getLine(), lineLst);
				}
				lineLst.add(pointModel);
			}
		}
		return lineMap;
	}

	@Override
	public Map<String, Object> indexTrouble(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 所有地市
		List<Map<String, Object>> areaList = arrivalDao.getArea();
		rs.put("areaList", areaList);
		// 当前用户所在地市
		rs.put("curr_area", request.getSession().getAttribute("areaId"));
		rs.put("sonAreaId", request.getSession().getAttribute("sonAreaId"));
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		// 获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		String roleNo = null;
		for (Map<String, String> map : roleList) {
			roleNo = map.get("ROLE_NO");
			if (RoleNo.GLY.equals(roleNo) || RoleNo.LXXJ_ADMIN.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
				rs.put("admin", true);
				break;
			}
		}
		// 昨天的时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime());
		rs.put("yesterday", yesterday);
		return rs;
	}

	@Override
	public Map<String, Object> queryTrouble(HttpServletRequest request,
			UIPage pager) {
		String areaId = request.getParameter("area");// 地市
		String date = request.getParameter("date");
		String sonAreaId = request.getParameter("sonAreaId");// 区域
		Map map = new HashMap();
		map.put("AREA_ID", areaId);
		map.put("SON_AREA_ID", sonAreaId);
		map.put("BEGIN_TIME", date + " 00:00:00");
		map.put("COMPLETE_TIME", date + " 23:59:59");

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = arrivalDao.queryTrouble(query);

		Map<String, Object> temp = null;
		for (Map map1 : list) {
			map.put("STAFF_ID", map1.get("STAFF_ID"));
			// 获取检查任务情况
			temp = arrivalDao.queryTroubleArrival(map);
			// 到位率
			int completed = Integer.valueOf(temp.get("COMPLETED").toString());
			int total = Integer.valueOf(temp.get("TOTAL").toString());
			if (total == 0) {
				map1.put("COMPLETED", "-");
				map1.put("TOTAL", "-");
				map1.put("RATE", "-");
				map1.put("DATE", date);
			} else {
				map1.put("COMPLETED", completed);
				map1.put("TOTAL", total);
				map1.put("DATE", date);
				map1.put("RATE", new Triangle().bfb(((double) completed)
						/ total));
			}
		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public List<Map> queryTrouble(HttpServletRequest request) {
		String areaId = request.getParameter("area");
		String sonAreaId = request.getParameter("sonAreaId");
		String date = request.getParameter("date");
		Map map = new HashMap();
		map.put("AREA_ID", areaId);
		map.put("SON_AREA_ID", sonAreaId);
		map.put("BEGIN_TIME", date + " 00:00:00");
		map.put("COMPLETE_TIME", date + " 23:59:59");
		List<Map> list = arrivalDao.queryTroubleForExcel(map);
		Map<String, Object> temp = null;
		for (Map map1 : list) {
			map.put("STAFF_ID", map1.get("STAFF_ID"));
			// 获取检查任务情况
			temp = arrivalDao.queryTroubleArrival(map);
			// 到位率
			int completed = Integer.valueOf(temp.get("COMPLETED").toString());
			int total = Integer.valueOf(temp.get("TOTAL").toString());
			if (total == 0) {
				map1.put("COMPLETED", "-");
				map1.put("TOTAL", "-");
				map1.put("RATE", "-");
				map1.put("DATE", date);
			} else {
				map1.put("COMPLETED", completed);
				map1.put("TOTAL", total);
				map1.put("DATE", date);
				map1.put("RATE", new Triangle().bfb(((double) completed)
						/ total));
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> indexKeep(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 所有地市
		List<Map<String, Object>> areaList = arrivalDao.getArea();
		rs.put("areaList", areaList);
		// 当前用户所在地市
		rs.put("curr_area", request.getSession().getAttribute("areaId"));
		rs.put("sonAreaId", request.getSession().getAttribute("sonAreaId"));
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		// 获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		String roleNo = null;
		for (Map<String, String> map : roleList) {
			roleNo = map.get("ROLE_NO");
			if (RoleNo.GLY.equals(roleNo) || RoleNo.LXXJ_ADMIN.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
				rs.put("admin", true);
				break;
			}
		}
		// 昨天的时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd")
				.format(cal
				.getTime());
		rs.put("yesterday_begin", yesterday + " 00:00");
		rs.put("yesterday_end", yesterday + " 23:59");
		return rs;
	}

	@Override
	public Map<String, Object> queryKeep(HttpServletRequest request,
			UIPage pager) {
		String areaId = request.getParameter("area");
		String sonAreaId = request.getParameter("sonAreaId");
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		String staff_name = request.getParameter("staff_name");
		Map map = new HashMap();
		map.put("AREA_ID", areaId);
		map.put("SON_AREA_ID", sonAreaId);
		map.put("BEGIN_TIME", start_date + ":00");
		map.put("COMPLETE_TIME", end_date + ":59");
		map.put("STAFF_NAME", staff_name);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = arrivalDao.queryKeep(query);// 以计划为单位

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String planId = null;
		List<PointModel> temp = null;
		List<PointModel> allArrivalPoint = new ArrayList<PointModel>();
		List<PointModel> isNotExistsPonits = null;// 还没到位点
		List<Map<String, String>> timePeriodList = null;// 计划时间段
		String start_time = null;
		String current_day_end_time = null;
		String end_time = null;
		String[] ss=null;
		int costTime;
		double rate;
		long arrivaled_plan;// 计划需要上传次数
		Map param = new HashMap();//条件map
		try {
			for (Map map1 : list) {
				map.put("STAFF_ID", map1.get("STAFF_ID"));
				map.put("AREANAME", map1.get("AREANAME"));
				map.put("SONAREANAME", map1.get("SONAREANAME"));
				String sql = "";
				String time_period = "";
				long timePeriod = 0;
				planId = StringUtil.objectToString(map1.get("PLAN_ID"));
				timePeriodList = arrivalDao.getTimePeriod(planId);// 时间段上传的数据
				for (Map<String, String> s : timePeriodList) {
					start_time = map1.get("START_TIME") + " "
							+ s.get("START_TIME") + ":00";
					current_day_end_time = map1.get("START_TIME") + " "
							+ s.get("END_TIME") + ":59";
					end_time = map1.get("COMPLETE_TIME") + " "
							+ s.get("END_TIME") + ":59";
					time_period += "<br/>" + s.get("START_TIME") + " --- "
							+ s.get("END_TIME");
					sql += " or (up.UPLOAD_TIME >= to_date('"
							+ start_time
							+ "', 'yyyy-mm-dd hh24:mi:ss') and up.UPLOAD_TIME <= to_date('"
							+ end_time + "', 'yyyy-mm-dd hh24:mi:ss'))";
					sql += "and substr(to_char(up.upload_time, 'yyyy-mm-dd hh24:mi:ss'),12)>='"
							+ s.get("START_TIME")
							+ ":00'"
							+ "and substr(to_char(up.upload_time, 'yyyy-mm-dd hh24:mi:ss'),12)<='"
							+ s.get("END_TIME") + ":59'";
					timePeriod += (df.parse(end_time.substring(0,17)+"00").getTime() - df
							.parse(start_time).getTime());
				}
				NumberFormat bf = NumberFormat.getInstance();
				bf.setMaximumIntegerDigits(2);
				bf.setMaximumFractionDigits(2);
				sql = sql.substring(3);
				map.put("SQL", sql);

				map1.put("TIME_PERIOD", time_period.substring(5));
				int dayCount = (int) ((df.parse(end_time).getTime() - df.parse(
						start_time).getTime()) / 86400000 + 1);
				double value = (double) timePeriod / (1000 * 60 * 60);
				double hours = Double.valueOf(bf.format(value));
				map1.put("TIME_TOTAL", dayCount * hours+"小时");
				// 统计到位率
				param.put("staff_id", map1.get("STAFF_ID"));
				param.put("start_time", start_date);
				param.put("end_time", end_date);
				Map<String,Object> complete=arrivalDao.ifArrivalPointExist(param);
				temp = arrivalDao.queryForKeep(map);
				// 已经到位的点不再匹配到位率
				isNotExistsPonits = getDistinctKeepPoints(allArrivalPoint, temp);
				if(!String.valueOf(complete.get("COUNT_LEAVE")).equals("0")){
					//新看护时间算法20170224
					
					ss=String.valueOf(complete.get("TIME_COMPLETED")).split("\\.");
					if(ss.length==1&&ss[0].equals("0")){
						costTime=0;
					}else{
						costTime = (int)Math.rint(Double.parseDouble("0."+ ss[1]) * 60);
					}
					String context = ss[0]+"小时"+costTime+"分钟";
//					String context = s[0].equals("0") ? Math
//							.round(Double.parseDouble(complete.get("TIME_COMPLETED")) * 60)
//							+ "min" 
//							: s[0] + "h"
//							+ Math.round(Double.parseDouble("0."+ s[1]) * 60) + "min";
					map1.put("TIME_COMPLETED", context);
					String LOCATE_SPAN = SysSet.CONFIG.get(AppType.KEEP).get(
							SysSet.LOCATE_SPAN);
					long locateSpan = Long.valueOf(LOCATE_SPAN) * 1000;
					// 计划所有应该上传的点
					arrivaled_plan = timePeriod * dayCount / locateSpan;
					rate = Math.rint(Double.parseDouble(complete.get("COUNT_LEAVE").toString())* 10 / arrivaled_plan * 100);
					map1.put("RATE", rate > 100 ? 100 + "%"
							: rate + "%");
					map1.put("COUNT_LEAVE", complete.get("COUNT_LEAVE"));
				}else if(temp.size() > 0){
					// 看护到位率算法
					List<Map> PlanPoints = arrivalDao.getKeepPlanPointById(map1.get("PLAN_ID").toString());
					List<PointModel> arrivalRatePoints = new ArrayList();
					arrivalRatePoints = new Triangle()
							.keepArrivalRate(PlanPoints, Integer
											.valueOf(map1.get("INACCURACY")
													.toString()),
									isNotExistsPonits);
					allArrivalPoint.addAll(arrivalRatePoints);
					int total = temp.size();
					int keep_arrivaled = arrivalRatePoints.size();
					// 看护间隔时间
					String LOCATE_SPAN = SysSet.CONFIG.get(AppType.KEEP).get(
							SysSet.LOCATE_SPAN);
					long locateSpan = Long.valueOf(LOCATE_SPAN) * 1000;
					// 计划所有应该上传的点
					arrivaled_plan = timePeriod * dayCount / locateSpan;
					// map1.put("TIME_COMPLETED", bf.format(((double)
					// keep_arrivaled / arrivaled_plan)* (timePeriod / (1000 *
					// 60 * 60))));
					map1.put("TIME_COMPLETED", new Triangle()
							.changTimeFormat(keep_arrivaled * 5));
					map1.put("RATE", new Triangle()
							.bfb(((double) keep_arrivaled) / arrivaled_plan));

					map1.put("COUNT_LEAVE", total - keep_arrivaled);

				} else {
					map1.put("TIME_COMPLETED", "-");
					map1.put("COUNT_LEAVE", "-");
					map1.put("RATE", "-");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	/**
	 * 
	 * @Function: 
	 *            com.cableInspection.serviceimpl.ArrivalServiceImpl.getDistinctKeepPoints
	 * @Description:判断pram2中的不存在pram1中的点
	 * 
	 * @param allArrivalPoints
	 * @param allUploadPoints
	 * @return
	 * 
	 * @date:2015-9-10 上午10:27:59
	 * 
	 * @Modification History:
	 * @date:2015-9-10 @author:Administrator create
	 */
	private List<PointModel> getDistinctKeepPoints(
			List<PointModel> allArrivalPoints, List<PointModel> allUploadPoints) {

		if (null == allArrivalPoints || allArrivalPoints.size() < 1) {
			return allUploadPoints;
		}
		List<PointModel> distinctKeepPoints = new ArrayList<PointModel>();
		for (PointModel uploadPoint : allUploadPoints) {
			for (int i = 0; i < allArrivalPoints.size(); i++) {
				PointModel arrivalPoint = allArrivalPoints.get(i);
				if (uploadPoint.getLatitude()
						.equals(arrivalPoint.getLatitude())
						&& uploadPoint.getLongitude().equals(
								arrivalPoint.getLongitude())) {
					break;
				}
			}
			distinctKeepPoints.add(uploadPoint);
		}
		return distinctKeepPoints;
	}

	@Override
	public List<Map> queryKeep(HttpServletRequest request) {
		String areaId = request.getParameter("area");
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		String sonAreaId = request.getParameter("son_area");
		String staff_name = request.getParameter("staff_name");
		Map map = new HashMap();
		map.put("AREA_ID", areaId);
		map.put("SON_AREA_ID", sonAreaId);
		map.put("STAFF_NAME", staff_name);
		map.put("BEGIN_TIME", start_date + ":00");
		map.put("COMPLETE_TIME", end_date + ":59");
		List<Map> list = arrivalDao.queryKeepForExcel(map);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String planId = null;
		List<PointModel> temp = null;
		List<PointModel> allArrivalPoint = new ArrayList<PointModel>();
		List<Map<String, String>> timePeriodList = null;// 计划时间段
		List<PointModel> isNotExistsPonits = null;// 还没到位点
		String start_time = null;
		String current_day_end_time = null;
		String end_time = null;
		double rate;
		String[] ss=null;
		int costTime;
		long arrivaled_plan;// 计划需要上传次数
		Map param = new HashMap();//条件map
		try {
			for (Map map1 : list) {
				map.put("STAFF_ID", map1.get("STAFF_ID"));
				String sql = "";
				String time_period = "";
				long timePeriod = 0l;
				planId = StringUtil.objectToString(map1.get("PLAN_ID"));
				timePeriodList = arrivalDao.getTimePeriod(planId);
				for (Map<String, String> s : timePeriodList) {
					start_time = map1.get("START_TIME") + " "
					+ s.get("START_TIME") + ":00";
			current_day_end_time = map1.get("START_TIME") + " "
					+ s.get("END_TIME") + ":59";
			end_time = map1.get("COMPLETE_TIME") + " "
					+ s.get("END_TIME") + ":59";
			time_period += "<br/>" + s.get("START_TIME") + " --- "
					+ s.get("END_TIME");
			sql += " or (up.UPLOAD_TIME >= to_date('"
					+ start_time
					+ "', 'yyyy-mm-dd hh24:mi:ss') and up.UPLOAD_TIME <= to_date('"
					+ end_time + "', 'yyyy-mm-dd hh24:mi:ss'))";
			sql += "and substr(to_char(up.upload_time, 'yyyy-mm-dd hh24:mi:ss'),12)>='"
					+ s.get("START_TIME")
					+ ":00'"
					+ "and substr(to_char(up.upload_time, 'yyyy-mm-dd hh24:mi:ss'),12)<='"
					+ s.get("END_TIME") + ":59'";
			timePeriod += (df.parse(end_time.substring(0,17)+"00").getTime() - df
					.parse(start_time).getTime());
		}
		NumberFormat bf = NumberFormat.getInstance();
		bf.setMaximumIntegerDigits(2);
		bf.setMaximumFractionDigits(2);
		sql = sql.substring(3);
		map.put("SQL", sql);

		map1.put("TIME_PERIOD", time_period.substring(5));
		int dayCount = (int) ((df.parse(end_time).getTime() - df.parse(
				start_time).getTime()) / 86400000 + 1);
		double value = (double) timePeriod / (1000 * 60 * 60);
		double hours = Double.valueOf(bf.format(value));
		map1.put("TIME_TOTAL", dayCount * hours+"小时");
		// 统计到位率
		param.put("staff_id", map1.get("STAFF_ID"));
		param.put("start_time", start_date);
		param.put("end_time", end_date);
		Map<String,Object> complete=arrivalDao.ifArrivalPointExist(param);
		temp = arrivalDao.queryForKeep(map);
		// 已经到位的点不再匹配到位率
		isNotExistsPonits = getDistinctKeepPoints(allArrivalPoint, temp);
		if(!String.valueOf(complete.get("COUNT_LEAVE")).equals("0")){
			//新看护时间算法20170224
			
			ss=String.valueOf(complete.get("TIME_COMPLETED")).split("\\.");
			if(ss.length==1&&ss[0].equals("0")){
				costTime=0;
			}else{
				costTime = (int)Math.rint(Double.parseDouble("0."+ ss[1]) * 60);
			}
			String context = ss[0]+"小时"+costTime+"分钟";
//			String context = s[0].equals("0") ? Math
//					.round(Double.parseDouble(complete.get("TIME_COMPLETED")) * 60)
//					+ "min" 
//					: s[0] + "h"
//					+ Math.round(Double.parseDouble("0."+ s[1]) * 60) + "min";
			map1.put("TIME_COMPLETED", context);
			String LOCATE_SPAN = SysSet.CONFIG.get(AppType.KEEP).get(
					SysSet.LOCATE_SPAN);
			long locateSpan = Long.valueOf(LOCATE_SPAN) * 1000;
			// 计划所有应该上传的点
			arrivaled_plan = timePeriod * dayCount / locateSpan;
			rate = Math.rint(Double.parseDouble(complete.get("COUNT_LEAVE").toString())* 10 / arrivaled_plan * 100);
			map1.put("RATE", rate > 100 ? 100 + "%"
					: rate + "%");
			map1.put("COUNT_LEAVE", complete.get("COUNT_LEAVE"));
		}else if (temp.size() > 0) {
					List<Map> PlanPoints = arrivalDao.getKeepPlanPointById(map1.get("PLAN_ID").toString());
					List<PointModel> arrivalRatePoints = new ArrayList();
					 arrivalRatePoints = new Triangle()
							.keepArrivalRate(PlanPoints, Integer
											.valueOf(map1.get("INACCURACY")
													.toString()),
									isNotExistsPonits);
					allArrivalPoint.addAll(arrivalRatePoints);
					int total = temp.size();
					int keep_arrivaled = arrivalRatePoints.size();
					// 看护间隔时间
					String LOCATE_SPAN = SysSet.CONFIG.get(AppType.KEEP).get(
							SysSet.LOCATE_SPAN);
					long locateSpan = Long.valueOf(LOCATE_SPAN) * 1000;
					arrivaled_plan = timePeriod * dayCount / locateSpan;
					/*
					 * map1.put("TIME_COMPLETED", bf .format(((double)
					 * keep_arrivaled / arrivaled_plan) (timePeriod / (1000 * 60
					 * * 60))));
					 */
					map1.put("TIME_COMPLETED", new Triangle()
							.changTimeFormat(keep_arrivaled * 5));
					map1.put("RATE", new Triangle()
							.bfb(((double) keep_arrivaled) / arrivaled_plan));

					map1.put("COUNT_LEAVE", total - keep_arrivaled);

				} else {
					map1.put("TIME_COMPLETED", "-");
					map1.put("COUNT_LEAVE", "-");
					map1.put("RATE", "-");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void keepArrivalCheckTask() {
		// 获取昨日所有看护任务
		Map<String, Object> params = new HashMap<String, Object>();
		String yesterday = DateUtil.getDate(-1);
		params.put("START_TIME", yesterday + " 00:00:00");
		params.put("END_TIME", yesterday + " 23:59:59");
		List<Map<String, Object>> planList = arrivalDao.getPlanList(params);

		List<Map<String, String>> timePeriodList = null;
		String start_time = null;
		String end_time = null;
		double longitude;
		double latitude;
		List<Map<String, Object>> pointList = null;
		String pointId = null;
		double longitude_temp;
		double latitude_temp;
		double distance;
		double maxDistance;
		if (planList != null && planList.size() > 0) {
			for (Map<String, Object> map : planList) {
				longitude = Double.parseDouble(map.get("LONGITUDE").toString());
				latitude = Double.parseDouble(map.get("LATITUDE").toString());
				maxDistance = Double.parseDouble(map.get("INACCURACY")
						.toString());

				timePeriodList = arrivalDao.getTimePeriod(StringUtil
						.objectToString(map.get("PLAN_ID")));

				String sql = "";
				for (Map<String, String> s : timePeriodList) {
					start_time = yesterday + " " + s.get("START_TIME");
					end_time = yesterday + " " + s.get("END_TIME");
					sql += " or (up.UPLOAD_TIME >= to_date('"
							+ start_time
							+ "', 'yyyy-mm-dd hh24:mi:ss') and up.UPLOAD_TIME <= to_date('"
							+ end_time + "', 'yyyy-mm-dd hh24:mi:ss'))";
				}
				sql = sql.substring(3);
				params.put("SQL", sql);
				params.put("INSPECTOR", map.get("INSPECTOR"));
				pointList = arrivalDao.getUploadPointList(params);
				for (Map<String, Object> point : pointList) {
					longitude_temp = Double.parseDouble(point.get("LONGITUDE")
							.toString());
					latitude_temp = Double.parseDouble(point.get("LATITUDE")
							.toString());
					distance = MapDistance.getDistance(latitude, longitude,
							latitude_temp, longitude_temp);
					if (distance <= maxDistance) {
						arrivalDao.updatePointArrivaled(point);
					}
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> keyPointsArrivaledStatistics(
			HttpServletRequest request) {

		Map paramsMap = new HashMap<String, Object>();

		// 获取任务ID(针对单个缆线任务签到情况)
		paramsMap.put("TASK_ID", request.getParameter("taskId"));

		// 所有缆线任务关键点签到情况
		// 获取巡检员
		paramsMap.put("STAFF_NAME", request.getParameter("staff_name"));

		// 获取任务名称
		paramsMap.put("TASK_NAME", request.getParameter("task_name"));

		// 获取任务开始日期
		paramsMap.put("START_TIME", request.getParameter("start_time"));

		// 获取任务结束日期
		paramsMap.put("COMPLETE_TIME", request.getParameter("end_time"));
		//新增地市和区域查询
		String area_id=request.getParameter("area_id");
		String son_area_id=request.getParameter("son_area_id");

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				paramsMap.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					paramsMap.put("SON_AREA_ID", session
							.getAttribute("sonAreaId"));
				} else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {// 如果是审核员只能查到本区域的
					paramsMap.put("SON_AREA_ID", session
							.getAttribute("sonAreaId"));
				} else {
					paramsMap.put("AREA_ID", -1);
				}
			}
		}
		
		if(null!=area_id&&!("").equals(area_id)){
			paramsMap.put("AREA_ID", area_id);
		}
		if(null!=son_area_id&&!("").equals(son_area_id)){
			paramsMap.put("SON_AREA_ID", son_area_id);
		}
		// 根据任务ID获取所有关键点签到情况
		List<Map<String, Object>> keyPointsArrivaledList = arrivalDao
				.queryKeyPointsArrivaledByTaskId(paramsMap);

		return keyPointsArrivaledList;
	}
	
	//将关键点签到情况写到静态表
	@Override
	public void addKeyPoints(){
		Map map = new HashMap();
		
		// 本月的第一天
		Date firstDateOfCurrMonth = DateUtil.getFirstDayOfCurrMonth();
		String str_firstDateOfCurrMonth = StringUtil.dateToString(
				firstDateOfCurrMonth, "yyyy-MM-dd");
		// 本月的最后一天
		Date lastDateOfCurrMonth = DateUtil.getLastDayOfCurrMonth();
		String str_lastDateOfCurrMonth = StringUtil.dateToString(
				lastDateOfCurrMonth, "yyyy-MM-dd");
		Map conds = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			firstDateOfCurrMonth = sdf.parse(str_firstDateOfCurrMonth);
			lastDateOfCurrMonth = sdf.parse(str_lastDateOfCurrMonth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//由于同步时间调整为23:50分，所以只需同步本月的完成情况
	/*	if(firstDateOfCurrMonth.compareTo(DateUtil.getCurrentDate())==0){
			Date date = new Date();
			conds.put("start_time", DateUtil.getFirstDayOfLastMonth(date));
			conds.put("end_time", DateUtil.getLastDayOfLastMonth(date));
			
		}else{*/
			conds.put("start_time", str_firstDateOfCurrMonth);
			conds.put("end_time", str_lastDateOfCurrMonth);
	//	}
		
		List<Map> rs = arrivalDao.queryKeyPointsArrivaled(conds);
//		if(arrivalDao.checkArrival(conds)>0){
			arrivalDao.deleteKeyPoints(conds);
//		}
			for(int i=0;i<rs.size();i++){
				arrivalDao.addKeyPointsdByTaskId(rs.get(i));
			}
			
			
		
	}

	@Override
	public Map queryArrivalRateByDate(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		map.put("area_id", request.getParameter("area_id"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			map.put("begin_time", request.getParameter("begin_time"));
			map.put("end_time", request.getParameter("end_time")+" 23:59:59");
//			Date date = sdf.parse(request.getParameter("end_time"));
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//			cal.add(Calendar.MONTH, 1);
//			map.put("end_time", sdf.format(cal.getTime()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = new ArrayList();
		if("".equals(map.get("area_id"))||null==map.get("area_id")){
			map.put("area_id", 2);
			list=arrivalDao.queryArrivalRateTable(map);
			if(list.isEmpty()){
				list = arrivalDao.queryArrivalRateByDate(query);
			}	
		}else{
			list=arrivalDao.queryArrivalRateTable(map);
			if(list.isEmpty()){
				list = arrivalDao.queryArrivalRateBySonArea(query);
			}
		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public List<Map> exportArrivalRateByDate(HttpServletRequest request) {
		Map map = new HashMap();
		map.put("area_id", request.getParameter("son_area"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			map.put("begin_time", request.getParameter("begin_time"));
			map.put("end_time", request.getParameter("end_time")+" 23:59:59");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map> list = new ArrayList();
		if("".equals(map.get("area_id"))||null==map.get("area_id")){
			map.put("area_id", 2);
			list=arrivalDao.queryArrivalRateTable(map);
			if(list.isEmpty()){
				list = arrivalDao.exportArrivalRateByDate(map);
			}
		}else{
			list=arrivalDao.queryArrivalRateTable(map);
			if(list.isEmpty()){
				list = arrivalDao.exportArrivalRateBySonArea(map);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> getAreaList() {
		Map map = new HashMap();
		map.put("areaList", arrivalDao.getAreaList());
		return map;
	}

	@Override
	public List<Map> getPhotoByKeepPlanId(HttpServletRequest request) {
		String plan_id = request.getParameter("plan_id");
		return arrivalDao.getPhotoByKeepPlanId(plan_id);
	}

	@Override
	public Map index(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 当前用户所在地市
		String staff_id = request.getSession().getAttribute("staffId").toString();
		String area_id = request.getSession().getAttribute("areaId").toString();
		String son_area_id = request.getSession().getAttribute("sonAreaId").toString();
		// 获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staff_id);
		String roleNo = null;
		for (Map<String, String> map : roleList) {
			roleNo = map.get("ROLE_NO");
			if (RoleNo.GLY.equals(roleNo) || RoleNo.LXXJ_ADMIN.equals(roleNo)
					) {
				params.put("area_id", "");
				params.put("son_area_id", "");
				break;
			}else if(RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)){
				params.put("area_id", area_id);
				params.put("son_area_id", "");
				break;
			}else{
				params.put("area_id", area_id);
				params.put("son_area_id", son_area_id);
			}
		}
		Map rs = new HashMap();
		rs.put("areaList", arrivalDao.getAreaById(params));
		rs.put("sonAreaList", arrivalDao.getSonAreaById(params));
		return rs;
	}
	
	@Override
	public Map getSonAreaById(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		String area_id = request.getParameter("area_id").toString();
		Map rs = new HashMap();
		params.put("area_id", area_id);
		params.put("son_area_id", "");
		rs.put("sonAreaList", arrivalDao.getSonAreaById(params));
		return rs;
	}

	@Override
	public void saveArrivalRateTable() {
		String[] area_id={"3","4","15","20","26","33","39","48","60","63","69","79","84"};
		// 本月的第一天
		try {
		String firstDateOfLastMonth = DateUtil.getFirstDayOfLastMonth(DateUtil.getCurrentDate());
		String lasttDateOfLastMonth = DateUtil.getLastDayOfLastMonth(DateUtil.getCurrentDate())+" 23:59:59";
		Map map = new HashMap();
		map.put("begin_time", firstDateOfLastMonth);
		map.put("end_time", lasttDateOfLastMonth);
		List<Map> list = new ArrayList();
		arrivalDao.delArrivalRateTable();
		list = arrivalDao.exportArrivalRateByDate(map);
		for (Map map2 : list) {
			map2.put("PARENT_AREA_ID", 2);
			arrivalDao.saveArrivalRateTable(map2);
		}
		list.clear();
		for (int i = 0; i < area_id.length; i++) {
			map.put("area_id", area_id[i]);
			list = arrivalDao.exportArrivalRateBySonArea(map);
			for (Map map2 : list) {
				map2.put("PARENT_AREA_ID", area_id[i]);
				arrivalDao.saveArrivalRateTable(map2);
			}
			list.clear();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveLxxjCity() {
		try {
		lxxjCityDao.del_lxxj_city();
		lxxjCityDao.add_lxxj_city();
		lxxjCityDao.del_lxxj_key_point_detail();
		lxxjCityDao.add_lxxj_key_point_detail();
		lxxjCityDao.del_lxxj_key_task_distri_rate_area();
		lxxjCityDao.add_lxxj_key_task_distri_rate_area();
		lxxjCityDao.del_lxxj_key_task_distri_rate_grid();
		lxxjCityDao.add_lxxj_key_task_distri_rate_grid();
		lxxjCityDao.del_lxxj_signed_detail();
		lxxjCityDao.add_lxxj_signed_detail();
		lxxjCityDao.del_lxxj_signed_rate_area();
		lxxjCityDao.add_lxxj_signed_rate_area();
		lxxjCityDao.del_lxxj_signed_rate_grid();
		lxxjCityDao.add_lxxj_signed_rate_grid();
		//更新缆线长度
		lxxjCityDao.updateLineLength();
		//更新集约化帐号
		List<Map> staffs = new ArrayList();
		try {
			SwitchDataSourceUtil.setCurrentDataSource("orcl153");
			staffs = lxxjCityDao.queryStaffFromJYH();
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			SwitchDataSourceUtil.clearDataSource();
		}
		for (Map map : staffs) {
			if(lxxjCityDao.checkStaffExists(map)==0){
				try {
					lxxjCityDao.insertStaff(map);
					lxxjCityDao.addRole(map);
				} catch (Exception e) {

				}

			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callWfworkitemflowServiceForTask(String s) {
		try {
		if(s.equals("0")){
			List<Map> list = new ArrayList();
			list=lxxjCityDao.querywork();
			if(list.size()>0){
				for (Map task : list) {
					WfworkitemflowSoap11BindingStub  wfworkitemflowSoap11BindingStub;
					Wfworkitemflow wfworkitemflowLocator = new WfworkitemflowLocator();
				
						Map staff = new HashMap();
						String result="";
						String param="";
						staff=lxxjCityDao.getStaffInfo(task.get("INSPECTOR").toString());
//						if(null==staff||staff.isEmpty()){
//							staff=lxxjCityDao.getstaffByDept(task.get("INSPECTOR").toString());
//						}
						if(null!=staff&&!staff.isEmpty()){
							wfworkitemflowSoap11BindingStub=(WfworkitemflowSoap11BindingStub) wfworkitemflowLocator.getWfworkitemflowHttpSoap11Endpoint();
							wfworkitemflowSoap11BindingStub.setTimeout(30 * 1000);
							param="<?xml version=\"1.0\" encoding=\"gb2312\"?>"
										   +"<IfInfo>"
										   +"<sysRoute>XJXT</sysRoute>"
										   +"<taskType>3</taskType>"
										   +"<xjMan>"+staff.get("STAFF_NAME").toString()+"</xjMan>"
										   +"<taskId>"+task.get("TASK_ID").toString()+"</taskId>"
										   +"<TaskName>"+task.get("TASK_NAME").toString()+"</TaskName>"
										   //+"<xjManAccount>ning22</xjManAccount>"
										   +"<xjManAccount>"+staff.get("STAFF_NO").toString()+"</xjManAccount>"
										   +"<pointCnt>"+task.get("SIGNPOINT").toString()+"</pointCnt>"
										   +"<kilometre>"+task.get("DIS").toString()+"</kilometre>"
										   +"<XjContent></XjContent>"
										   +"<workhours>"+task.get("WORKTIME").toString()+"</workhours>"
										   +"</IfInfo>";
							result=wfworkitemflowSoap11BindingStub.outSysDispatchTask(param);
							Document doc;
							Element root = null;
							Map post = new HashMap();
							result=wfworkitemflowSoap11BindingStub.outSysDispatchTask(param);
							post.put("get", result);
							post.put("post", param);
							post.put("type", 2);
							if(result.length()>1){
								result=result.replace("<?xml version=\"1.0\" encoding=\"gb2312\"?>", "");
								doc= DocumentHelper.parseText(result);
								root = doc.getRootElement();
								post.put("flag", root.element("IfResult").getText());
							}else{
								post.put("flag", 1);
							}
							pointDao.add_a_post(post);
						//	System.out.println(result);
						}
				}
			}
		}else{
			Map map = new HashMap();
			map=lxxjCityDao.queryWorkById(s);
			if(!map.isEmpty()&&!"1".equals(map.get("SEND_TYPE"))){
				WfworkitemflowSoap11BindingStub  wfworkitemflowSoap11BindingStub;
				Wfworkitemflow wfworkitemflowLocator = new WfworkitemflowLocator();
			
					Map staff = new HashMap();
					String result="";
					String param="";
					staff=lxxjCityDao.getStaffInfo(map.get("INSPECTOR").toString());
//					if(null==staff||staff.isEmpty()){
//						staff=lxxjCityDao.getstaffByDept(map.get("INSPECTOR").toString());
//					}
					if(null!=staff&&!staff.isEmpty()){
						wfworkitemflowSoap11BindingStub=(WfworkitemflowSoap11BindingStub) wfworkitemflowLocator.getWfworkitemflowHttpSoap11Endpoint();
						wfworkitemflowSoap11BindingStub.setTimeout(30 * 1000);
						param="<?xml version=\"1.0\" encoding=\"gb2312\"?>"
									   +"<IfInfo>"
									   +"<sysRoute>XJXT</sysRoute>"
									   +"<taskType>3</taskType>"
									   +"<xjMan>"+staff.get("STAFF_NAME").toString()+"</xjMan>"
									   +"<taskId>"+map.get("TASK_ID").toString()+"</taskId>"
									   +"<TaskName>"+map.get("TASK_NAME").toString()+"</TaskName>"
									   //+"<xjManAccount>ning22</xjManAccount>"
									   +"<xjManAccount>"+staff.get("STAFF_NO").toString()+"</xjManAccount>"
									   +"<pointCnt>"+map.get("SIGNPOINT").toString()+"</pointCnt>"
									   +"<kilometre>"+map.get("DIS").toString()+"</kilometre>"
									   +"<XjContent></XjContent>"
									   +"<workhours>"+map.get("WORKTIME").toString()+"</workhours>"
									   +"</IfInfo>";
						result=wfworkitemflowSoap11BindingStub.outSysDispatchTask(param);
						Document doc;
						Element root = null;
						Map post = new HashMap();
						result=wfworkitemflowSoap11BindingStub.outSysDispatchTask(param);
						post.put("get", result);
						post.put("post", param);
						post.put("type", 2);
						if(result.length()>1){
							result=result.replace("<?xml version=\"1.0\" encoding=\"gb2312\"?>", "");
							doc= DocumentHelper.parseText(result);
							root = doc.getRootElement();
							post.put("flag", root.element("IfResult").getText());
						}else{
							post.put("flag", 1);
						}
						pointDao.add_a_post(post);
					//	System.out.println(result);
					}
			}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
