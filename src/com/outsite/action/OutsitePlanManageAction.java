package com.outsite.action;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.linePatrol.util.DateUtil;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.outsite.service.MainOutSiteService;
import com.outsite.service.OutsitePlanManage;
import com.system.constant.RoleNo;
import com.system.service.StaffService;

@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/outsitePlanManage")
public class OutsitePlanManageAction extends BaseAction {
    @Autowired
    OutsitePlanManage outsitePlanManage;

    @Resource
    private MainOutSiteService outSiteService;

	@Resource
	private StaffService staffService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/outsiteplanmanage/job_index");
    }

	/** 外力点看护计划--查询计划列表 **/
    @RequestMapping(value = "/queryPlans.do")
    public void queryPlans(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
		Map<String, Object> map = outsitePlanManage.query_outsite(request,
				pager);// 查询数据
	write(response, map);
    }

	/** 外力点看护计划--查询计划列表--详情列表 **/
    @RequestMapping(value = "/query_detail_plan_data.do")
    public void query_detail_plan(HttpServletRequest request, HttpServletResponse response,
	    UIPage pager) throws IOException {
		Map<String, Object> map = outsitePlanManage.query_detail_plan(request,
				pager);// 查询数据
	write(response, map);
    }

	/** 新增外力点看护计划--弹出框 **/
    @RequestMapping(value = "/toAdd_outsite.do")
    public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> map = new HashMap<String, Object>();
		// 获取当前登陆名
	String staffNo = String.valueOf(request.getSession().getAttribute("staffNo"));
	map.put("staffNo", staffNo);
	map.put("createDate",DateUtil.getDate());
	return new ModelAndView("/outsite/outsiteplanmanage/outsite_add", map);
    }
    
	/** 看护计划校验 **/
    @RequestMapping(value = "/validateJob.do")
    @ResponseBody
    public Map validateJob(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Map map = new HashMap();
	Boolean status = false;
	try {
	   status = outsitePlanManage.validatePlanTime(request);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

	/** 新增外力点看护计划--保存 **/
    @RequestMapping(value = "/add_outsite.do")
    @ResponseBody
    public Map add_outsite(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Map map = new HashMap();
	Boolean status = true;
	try {
	    outsitePlanManage.insert_outsite_plan(request);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

	// 选择外力点弹出框
    @RequestMapping(value = "/get_outsite.do")
    public ModelAndView get_outsite(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/outsiteplanmanage/chose_out_site", null);
    }

	// 选择看护人员弹出框
    @RequestMapping(value = "/get_kan_name.do")
    public ModelAndView get_kan_name(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/outsiteplanmanage/chose_kan_name", null);
    }

	// 选择监管人员弹出框
    @RequestMapping(value = "/get_jianguan_name.do")
    public ModelAndView get_jianguan_name(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/outsiteplanmanage/chose_jianguan_name", null);
    }

	// 选择巡线员
    @RequestMapping(value = "/select_xunxian_name.do")
    public ModelAndView select_xunxian_name(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/outsiteplanmanage/chose_xunxian_name", null);
    }

	// 选择时间模板
    @RequestMapping(value = "/select_kanhu_shijian.do")
    public ModelAndView select_kanhu_shijian(HttpServletRequest request,
	    HttpServletResponse response) {
	return new ModelAndView("/outsite/outsiteplanmanage/select_kanhu_shijian", null);
    }

	// 查看看护时间
    @RequestMapping(value = "/get_shijian.do")
    public ModelAndView get_shijian(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/outsiteplanmanage/look_shijian", null);
    }

	// 巡线报表页面跳转
    @RequestMapping(value = "/get_baobiao.do")
    public ModelAndView get_baobiao(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/move_log/look_baobiao", null);
    }

	// 巡线无效时间图页面跳转
    @RequestMapping(value = "/get_no_picture.do")
    public ModelAndView get_no_picture(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/move_log/look_no_picture", null);
    }

	// 计划详情页面跳转
    @RequestMapping(value = "/detail_plan.do")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> map = new HashMap<String, Object>();
		// 初始化planid
	map.put("plan_id", request.getParameter("plan_id"));
	return new ModelAndView("/outsite/outsiteplanmanage/detail_plan", map);
    }

    @RequestMapping(value = "/showPlanDetail.do")
    public ModelAndView showPlanDetail(HttpServletRequest request, HttpServletResponse response,
	    UIPage pager) throws IOException {
	String PLAN_ID = request.getParameter("PLAN_ID2");
	Map map = new HashMap();
	map.put("PLAN_ID", PLAN_ID);
	return new ModelAndView("/outsite/outsiteplanmanage/plan_detail", map);
    }

	/** 查询任务列表 **/
    @RequestMapping(value = "/queryTaskList.do")
    public void queryTaskList(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> map = outsitePlanManage.queryTaskList(request, pager);
	write(response, map);
    }

	/** 查询时间片段列表 **/
    @RequestMapping(value = "/queryTimeFregment.do")
    public void queryTimeFregment(HttpServletRequest request, HttpServletResponse response,
	    UIPage pager) throws IOException {
	Map<String, Object> map = outsitePlanManage.queryTimeFregment(request, pager);
	write(response, map);
    }

	// 外力点列表
    @RequestMapping(value = "/out_site_data.do")
    public void out_site_data(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	String areaId = String.valueOf(request.getSession().getAttribute("areaId"));

	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", areaId);// 区域ID
	map.put("status", true);
	try {
	    List<Map<String, Object>> localInspactPerson = outsitePlanManage.getout_site(para);
	    map.put("rows", localInspactPerson);
	    map.put("total", localInspactPerson.size());
	} catch (Exception e) {
	    e.printStackTrace();
	    map.put("status", false);
	}
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

	// 选择看护人员列表
    @RequestMapping(value = "/kan_name_data.do")
    public void kan_name_data(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	map.put("status", true);
		para.put("localId", request.getSession().getAttribute("areaId"));// 区域ID
	try {
	    List<Map<String, Object>> localInspactPerson = outsitePlanManage.getkan_name(para);
	    map.put("rows", localInspactPerson);
	    map.put("total", localInspactPerson.size());
	} catch (Exception e) {
	    e.printStackTrace();
	    map.put("status", false);
	}
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    
	// 选择看护人员列表
    @RequestMapping(value = "/getPartTimeList.do")
    public void getPartTimeList(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		map.put("status", true);
		para.put("area_id", request.getSession().getAttribute("areaId"));// 区域ID
		try {
		    List<Map<String, Object>> partTimeList = outsitePlanManage.getPartTimeList(para);
		    map.put("rows", partTimeList);
		    map.put("total", partTimeList.size());
		} catch (Exception e) {
		    e.printStackTrace();
		    map.put("status", false);
		}
		try {
		    response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }

	// 选择巡线人员列表
    @RequestMapping(value = "/xunxian_data.do")
    public void xunxian_data(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	map.put("status", true);
	try {
	    List<Map<String, Object>> localInspactPerson = outsitePlanManage.getxunxian_data(para);
	    map.put("rows", localInspactPerson);
	    map.put("total", localInspactPerson.size());
	    response.getWriter().write(JSONObject.fromObject(map).toString());

	} catch (Exception e) {
	    e.printStackTrace();
	    map.put("status", false);
	}

    }

	// 选择监管人员列表
    @RequestMapping(value = "/jianguan_name_data.do")
    public void jianguan_name_data(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	map.put("status", true);
	try {
	    List<Map<String, Object>> localInspactPerson = outsitePlanManage.getjianguan_name(para);
	    map.put("rows", localInspactPerson);
	    map.put("total", localInspactPerson.size());
	} catch (Exception e) {
	    e.printStackTrace();
	    map.put("status", false);
	}
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

	// 编辑外力点计划
    @RequestMapping(value = "/toUpdate.do")
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = outsitePlanManage
				.query_outsite_single(request);// 查询单个计划

	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	List<Map<String, Object>> localInspactPerson = outsitePlanManage.look_time(para);

	String kanhu_shijian_id = "";
	String kanhu_shijian = "";
	for (int i = 0; i < localInspactPerson.size(); i++) {
	    kanhu_shijian += String.valueOf(localInspactPerson.get(i).get("START_TIME")) + "-"
		    + String.valueOf(localInspactPerson.get(i).get("END_TIME"));
	    
	    if(i < localInspactPerson.size()-1){
	    	kanhu_shijian+= "," + "\n";
	    }

	}
	request.setAttribute("kanhu_shijian", kanhu_shijian);
		// 获取当前登陆名
	String staffNo = String.valueOf(request.getSession().getAttribute("staffNo"));
	map.put("staffNo", staffNo);
	map.put("updateDate",DateUtil.getDate());

	return new ModelAndView("/outsite/outsiteplanmanage/outsite_update", map);
    }

	// 编辑外力点计划
    @RequestMapping(value = "/update_outsite_plan.do")
    @ResponseBody
    public Map update_outsite_plan(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Map map = new HashMap();
	Boolean status = true;
	try {
	    outsitePlanManage.update_outsite_plan(request);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

	// 停止或者启动计划，flag为1代表启动为0代表停止
    @RequestMapping(value = "/start_detail_plan.do")
    @ResponseBody
    public Map start_detail_plan(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	Map map = new HashMap();
	try {
	    outsitePlanManage.stop(request);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return map;
    }

	// 查看看护时间
    @RequestMapping(value = "/look_time.do")
    public void look_time(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	map.put("status", true);
	try {
	    List<Map<String, Object>> localInspactPerson = outsitePlanManage.look_time(para);
	    map.put("rows", localInspactPerson);
	    map.put("total", localInspactPerson.size());
	} catch (Exception e) {
	    e.printStackTrace();
	    map.put("status", false);
	}
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

	/** 外力点--迁移日志 **/
    @RequestMapping(value = "/index_movelog.do")
    public ModelAndView index_movelog(HttpServletRequest request, HttpServletResponse response) {
	return new ModelAndView("/outsite/move_log/index_movelog");
    }

	/** 外力点--查询迁移日志 **/
    @RequestMapping(value = "/query_movelog.do")
    public void query_movelog(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
		Map<String, Object> map = outsitePlanManage.query_movelog(request,
				pager);// 查询数据
	write(response, map);
    }

	// 编辑迁移日志
    @RequestMapping(value = "/toUpdate_movelog.do")
    public ModelAndView toUpdate_movelog(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = outsitePlanManage.select_movelog(request);// 查询单个计划

	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

		// 获取当前登陆名
	String staffNo = String.valueOf(request.getSession().getAttribute("staffNo"));
	map.put("staffNo", staffNo);

	return new ModelAndView("/outsite/move_log/move_log_update", map);
    }

	// 编辑迁移日志
    @RequestMapping(value = "/update_outsite_movelog.do")
    @ResponseBody
    public Map update_outsite_movelog(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Map map = new HashMap();
	Boolean status = true;
	try {
	    outsitePlanManage.update_outsite_movelog(request);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

	// 测试巡线时长
    @RequestMapping(value = "/test.do")
    public void test(HttpServletRequest request, HttpServletResponse response) {

	int ten = 10;
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	String up = request.getParameter("up");
	String down = request.getParameter("down");
	String line_date = request.getParameter("line_date");
	para.put("up", up);
	para.put("down", down);
	para.put("line_date", line_date);

	List<Map<String, Object>> resLs = new ArrayList<Map<String, Object>>();
	Map<String, Object> resMap = null;
		// 获取参数的时间
	List<Map<String, Object>> para_time = outsitePlanManage.get_para_time(para);
	String WorkStart = "";
	String WorkEnd = "";
	String WorkStart2 = "";
	String WorkEnd2 = "";
	String time = "";
	if (para_time.size() > 3) {
	    WorkStart = String.valueOf(para_time.get(0).get("PARAM_VALUE"));
	    WorkEnd = String.valueOf(para_time.get(1).get("PARAM_VALUE"));
	    WorkStart2 = String.valueOf(para_time.get(2).get("PARAM_VALUE"));
	    WorkEnd2 = String.valueOf(para_time.get(3).get("PARAM_VALUE"));
	    time = String.valueOf(para_time.get(0).get("TIME"));
	}
		// 上午
	if ("".equals(down) && !"".equals(up)) {
	    para.put("workstart", line_date + " " + WorkStart + ":00");
	    para.put("workend", line_date + " " + WorkEnd + ":00");
	    // para.put("workstart2","");
	    // para.put("workend2","");
	} else if ("".equals(up) && !"".equals(down)) {
			// 下午
	    para.put("workstart", line_date + " " + WorkStart2 + ":00");
	    para.put("workend", line_date + " " + WorkEnd2 + ":00");
	    // para.put("workstart","");
	    // para.put("workend","");
	} else {
	    // para.put("workstart2","");
	    para.put("workend", line_date + " " + WorkEnd2 + ":00");
	    para.put("workstart", line_date + " " + WorkStart + ":00");
	    // para.put("workend","");
	}

	try {
			// 查询轨迹表中巡线时长所有数据
	    List<Map<String, Object>> xunxianlis = outsitePlanManage.get_alllist(para);
			// 连续标志
	    List<Map<String, Object>> con_labellist = outsitePlanManage.getflag(para);
			// 所有人员
	    List<Map<String, Object>> all_people = outsitePlanManage.get_all_people(para);
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 循环所有人员
	    for (Map<String, Object> map_all_people : all_people) {

		String con_label_user_id = String.valueOf(map_all_people.get("USER_ID"));
				// 重要break
		for (int m = 0; m < xunxianlis.size(); m++) {
		    Map map1_people = xunxianlis.get(m);
		    String map1_people_user_id = String.valueOf(map1_people.get("USER_ID"));

					double no_gps_1 = 0; // 没有信号
					double nomatchtime = 0;// 不在或未匹配到巡检计划点时间
					double con_time = 0; // 连续信号丢失超过10分钟
					double forty = 0; // 在非外力施工（隐患）点的巡检点连续停留超过40分钟；
					// 判断人员是否相同
		    if (con_label_user_id.equals(map1_people_user_id)) {
			resMap = new HashMap<String, Object>();
			para.put("user_id", map1_people_user_id);

						// 没有gps信号
			Map<String, Object> params_time = new HashMap<String, Object>();
			List<Map<String, Object>> get_no_gpslist = outsitePlanManage
				.get_no_gps(para);

			if (get_no_gpslist.size() > 0) {
			    for (int p = 0; p < get_no_gpslist.size(); p++) {
								// 没有信号时minite累加
				no_gps_1 += Double.parseDouble(String.valueOf(get_no_gpslist.get(p)
					.get("MINITE")));

				String judge_time = String.valueOf(
					get_no_gpslist.get(p).get("TRACK_TIME")).substring(0, 10);
				para.put("judge_time", judge_time);
				para.put("user_id", map1_people_user_id);
				para.put("invalid_type", 0);
				para.put("start_time", "");
				para.put("end_time", "");

				List<Map<String, Object>> get_judge_time = outsitePlanManage
					.get_judge_time(para);
				if (get_judge_time.size() > 0) {
									// 说明中间表中已经有数据，则不用插入
				} else {
									if (p == 1) {// 每天总共两条数据
						 // String
					params_time.put("user_id", map1_people_user_id);
					params_time.put(
						"line_date",
						String.valueOf(
							get_no_gpslist.get(p).get("TRACK_TIME"))
							.substring(0, 10));
					params_time.put(
						"start_time",
						String.valueOf(get_no_gpslist.get(p - 1).get(
							"TRACK_TIME")));
					params_time.put(
						"end_time",
						String.valueOf(get_no_gpslist.get(p).get(
							"TRACK_TIME")));
					params_time.put("invalid_type", 0);
										params_time.put("remark", no_gps_1);// 记录总时间，供接口方便取值
										// 0 GPS未打开
					outsitePlanManage.insert_invalid_time(params_time);
				    }
				}
			    }
			}

						// 连续信号丢失超过10分钟

			long diff = 0;
			long min = 0;
						// 循环匹配表中连续的数据
			for (Map<String, Object> map2 : con_labellist) {
			    String con_label_2 = String.valueOf(map2.get("CON_LABEL"));

							// 查询匹配表中所有数据
			    List<Map<String, Object>> xunxianlis_new = outsitePlanManage
				    .test_con(para);
							// 循环匹配表中连续的数据
			    for (int y = 0; y < xunxianlis_new.size(); y++) {

				Map map1 = xunxianlis_new.get(y);
				String con_label_1 = String.valueOf(map1.get("CON_LABEL"));
				String con_user_id = String.valueOf(map1.get("USER_ID"));
								// 判断是否连续
				if (con_label_2.equals(con_label_1)) {

				    resMap.put("STAFF_NAME", map1.get("STAFF_NAME"));
				    resMap.put("SITE_NAME", map1.get("SITE_NAME"));
									// 循环完毕跳出循环
				    int nu = y + 1;
				    if (nu >= xunxianlis_new.size()) {
					break;
				    }
									// 算出前后两个点的时间差
				    Date d1 = df.parse(String.valueOf(xunxianlis_new.get(y).get(
					    "MATCH_TIME")));
				    Date d2 = df.parse(String.valueOf(xunxianlis_new.get(y + 1)
					    .get("MATCH_TIME")));
				    diff = d2.getTime() - d1.getTime();
				    min = (diff / (1000 * 60));

									// 判断gps_flag是否有信号0有1无
				    String gps_flag = String.valueOf(xunxianlis_new.get(y).get(
					    "GPS_FLAG"));
				    String gps_flag_1 = String.valueOf(xunxianlis_new.get(y + 1)
					    .get("GPS_FLAG"));

				    if (("1".equals(gps_flag) && "1".equals(gps_flag_1))
					    || ("1".equals(gps_flag) && "0".equals(gps_flag_1))) {
										// 判断连续是否超过10分钟
					if (min > ten) {
					    con_time += min;
					}

					String judge_time_2 = String.valueOf(
						xunxianlis_new.get(y).get("MATCH_TIME")).substring(
						0, 10);
					para.put("judge_time", judge_time_2);
					para.put("user_id", map1_people_user_id);
					para.put("invalid_type", 1);
					para.put(
						"start_time",
						String.valueOf(xunxianlis_new.get(y).get(
							"MATCH_TIME")));
					para.put("end_time", "");

					List<Map<String, Object>> get_judge_time_2 = outsitePlanManage
						.get_judge_time(para);
					if (get_judge_time_2.size() > 0) {
											// 说明中间表中已经有数据，则不用插入
					} else {
					    params_time.put("user_id", map1_people_user_id);
					    params_time
						    .put("line_date",
							    String.valueOf(
								    xunxianlis_new.get(y).get(
									    "MATCH_TIME"))
								    .substring(0, 10));
					    params_time.put(
						    "start_time",
						    String.valueOf(xunxianlis_new.get(y).get(
							    "MATCH_TIME")));
					    params_time.put(
						    "end_time",
						    String.valueOf(xunxianlis_new.get(y + 1).get(
							    "MATCH_TIME")));
					    params_time.put("invalid_type", 1);
											params_time.put("remark", con_time);// 记录总时间，供接口方便取值
											// 1 GPS连续丢失信号
					    outsitePlanManage.insert_invalid_time(params_time);
					}
				    }
				}

			    }
			}

			long diff_1 = 0;
			long min_1 = 0;
						// 不在或未匹配到巡检计划点时间
			String track_id = String.valueOf(xunxianlis.get(m).get("TRACK_ID"));
			para.put("track_id", track_id);
						// 未匹配所有数据
			List<Map<String, Object>> get_nomatchlist = outsitePlanManage
				.get_nomatch(para);

			para.put("user_id", map1_people_user_id);
						// 根据userid查询所有数据
			List<Map<String, Object>> xunxianlis_three = outsitePlanManage
				.get_alllist(para);

			if (get_nomatchlist.size() == 0) {

			    String start_time_1 = "";
			    String end_time_1 = String.valueOf(xunxianlis.get(m).get("TRACK_TIME"));
							// 取上一个最大数据时间
			    Map<String, Object> max_date_Map = new HashMap<String, Object>();
			    max_date_Map.put("new_date", end_time_1);
			    max_date_Map.put("user_id", map1_people_user_id);
			    String max_date = outsitePlanManage.get_max_date(max_date_Map);

			    if ("".equals(max_date) || max_date == null) {
				start_time_1 = end_time_1;
			    } else {
				start_time_1 = max_date;
			    }

			    params_time.put("user_id", map1_people_user_id);
			    params_time.put("line_date", end_time_1.substring(0, 10));
			    params_time.put("start_time", start_time_1);
			    params_time.put("end_time", end_time_1);
			    params_time.put("invalid_type", 2);

			    Date d3 = df.parse(start_time_1);
			    Date d4 = df.parse(end_time_1);
			    diff_1 = d4.getTime() - d3.getTime();
			    min_1 = (diff_1 / (1000 * 60));

			    nomatchtime += min_1;

			    String judge_time_3 = end_time_1.substring(0, 10);
			    para.put("judge_time", judge_time_3);
			    para.put("user_id", map1_people_user_id);
							para.put("invalid_type", 2);// 2未匹配到巡线点
			    para.put("start_time", start_time_1);
			    para.put("end_time", end_time_1);
			    params_time.put("remark", min_1);

			    List<Map<String, Object>> get_judge_time_2 = outsitePlanManage
				    .get_judge_time(para);
			    if (get_judge_time_2.size() > 0) {
								// 说明中间表中已经有数据，则不用插入
			    } else {
								// 2 未匹配到巡线点
				outsitePlanManage.insert_invalid_time(params_time);
			    }

			}

						// 在非外力施工（隐患）点的巡检点连续停留超过40分钟；
			List<Map<String, Object>> forty_time = outsitePlanManage
				.get_forty_time(para);
			if (forty_time.size() > 0 && forty_time != null) {
			    forty = Double.parseDouble(String.valueOf(forty_time.get(0).get(
				    "FORTY_TIME")));
			}

						// 总时间
			double total = no_gps_1 + nomatchtime + con_time + forty;

			long diff_para_up = 0;
			long diff_para_down = 0;
			long diff_para_down_up = 0;
			long min_up = 0;
			long min_down = 0;
			long min_down_up = 0;

			Date d4 = df.parse(time + " " + WorkStart + ":00");
			Date d5 = df.parse(time + " " + WorkEnd + ":00");
			Date d6 = df.parse(time + " " + WorkStart2 + ":00");
			Date d7 = df.parse(time + " " + WorkEnd2 + ":00");
			diff_para_up = d5.getTime() - d4.getTime();
			diff_para_down = d7.getTime() - d6.getTime();
			diff_para_down_up = d7.getTime() - d4.getTime();
			min_up = (diff_para_up / (1000 * 60));
			min_down = (diff_para_down / (1000 * 60));
			min_down_up = (diff_para_down_up / (1000 * 60));

						// 有效时长
			double yes_total = 0;

						// 上午
			if ("".equals(down) && !"".equals(up)) {
			    yes_total = min_up - total;
			} else if ("".equals(up) && !"".equals(down)) {
							// 下午
			    yes_total = min_down - total;
			} else {
			    yes_total = min_down_up - total;
			}

			if (yes_total < 0) {
			    yes_total = 0.0;
			}
			if (yes_total > 840) {
			    yes_total = 840.0;
			}
			if (total > 840) {
			    total = 840.0;
			}
			if (total < 0) {
			    total = 0.0;
			}
						resMap.put("NO_TOTAL", total + "(分钟)");
						resMap.put("YES_TOTAL", yes_total + "(分钟)");
			resLs.add(resMap);

						// 控制resLs中重复数据
			if (m > 0) {
			    Map map1_people_judge_1 = xunxianlis.get(m);
			    String map1_people_judge_1_user_id = String.valueOf(map1_people_judge_1
				    .get("USER_ID"));

			    Map map1_people_judge_2 = xunxianlis.get(m + 1);
			    String map1_people_judge_2_user_id = String.valueOf(map1_people_judge_2
				    .get("USER_ID"));

			    if (map1_people_judge_1_user_id.equals(map1_people_judge_2_user_id)) {
				break;
			    }
			} else {
			    break;
			}

		    }

		}
	    }

	    map.put("rows", resLs);
	    map.put("total", resLs.size());
	} catch (Exception e) {
	    e.printStackTrace();
	    map.put("status", false);
	}
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

	/** 查询无效时间图 **/
    @RequestMapping(value = "/list_no_picture.do")
    public void list_no_picture(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();

	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    List<Map<String, Object>> localInspactPerson = outsitePlanManage
		    .list_no_picture(request);
	    JSONArray jsonArray = JSONArray.fromObject(localInspactPerson);
	    response.getWriter().write(jsonArray.toString());

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/add_elebarUI.do")
    public ModelAndView add_elebarUI(HttpServletRequest request, HttpServletResponse response,
	    String plan_id, String out_site_id) {
	// Map<String, Object> map = outSiteService.findById(out_site_id);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("plan_id", plan_id);
	map.put("out_site_id", out_site_id);
	map.put("isAdmin", false);
	if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) != null){
		map.put("isAdmin", true);
	}
	return new ModelAndView("/outsite/outsiteplanmanage/add_elebarUI", map);
    }

    @RequestMapping(value = "/edit_elebarUI.do")
    public ModelAndView edit_elebarUI(HttpServletRequest request, HttpServletResponse response,
	    String out_site_id, String plan_id) {
	Map<String, Object> map = outSiteService.findById(out_site_id);
		// 获取电子围栏坐标
	List<Map<String, Object>> elebars = outSiteService.getElebar(plan_id);
	JSONObject jsonObject = new JSONObject();
	jsonObject.put("ebar", JSONArray.fromObject(elebars).toString());

	map.put("elebar", jsonObject.toString());
	String elebar = map.get("elebar").toString();
	elebar = elebar.replaceAll("\"", "\\\\\"");
	map.put("elebar", elebar);

	map.put("out_site_id", out_site_id);
	map.put("plan_id", plan_id);
	map.put("isAdmin", false);
	if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) != null){
		map.put("isAdmin", true);
	}
	return new ModelAndView("/outsite/outsiteplanmanage/edit_elebarUI", map);
    }

	/** 计算矩形长宽 **/
    @RequestMapping(value = "/getElebarlw.do")
    public void getElebarlw(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map<String, Object> map = outsitePlanManage.getElebarlw(para);
	write(response, map);
    }

	@RequestMapping("/index_autoTrack.do")
	public ModelAndView index_autoTrack(HttpServletRequest request,
			HttpServletResponse response) {

		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();

		// 个人信息
		Map<String, Object> staffInfo = staffService.findPersonalInfo(StaffUtil
				.getStaffId(request));

		map.put("staffInfo", staffInfo);

		return new ModelAndView("/outsite/autoTrack/autoTrack_index", map);
	}

	/**
	 * 返回看护上传轨迹点集合列表
	 */
	@RequestMapping("/selTrackForDG.do")
	@ResponseBody
	public Map<String, Object> selTrackForDG(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

		List<Map<String, Object>> list = outsitePlanManage
				.getOutSitePlanTrack(para);
		resultMap.put("rows", list);
		// resultMap.put("total",list.size());
		return resultMap;
	}

	/**
	 * 获取看护上传轨迹点集合
	 */
	@RequestMapping(value = "/getOutSitePlanTrack.do")
	public void getOutSitePlanTrack(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

		map.put("status", true);
		try {
			List<Map<String, Object>> trackList = outsitePlanManage
					.getOutSitePlanTrack(para);
			map.put("trackList", trackList);

			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", false);
		}
	}

	/**
	 * 进入看护员选择页面
	 */
	@RequestMapping(value = "/index_getInspector.do")
	public ModelAndView getInspactPersonIndex(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/outsite/autoTrack/autoTrack_inspector", null);
	}

	/**
	 * 查询爱巡线看护员列表
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/getOutSitePlanInspector.do")
	public void getOutSitePlanInspector(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

		try {
			// 本区域看护员
			para.put("localAreaId", StaffUtil.getStaffAreaId(request));

			List<Map<String, Object>> localInspactPerson = outsitePlanManage
					.getOutSitePlanInspector(para);
			map.put("rows", localInspactPerson);
			map.put("total", localInspactPerson.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

	/**
	 * 根据条件获取外力点计划的电子围栏信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/getElebarUI.do")
	public void getElebarUI(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

		map.put("status", false);

		try {

			List<Map<String, Object>> outSitePlanInfoLst = outsitePlanManage
					.getOutSitePlanInfo(para);

			JSONObject jsonObject = new JSONObject();

			if (CollectionUtils.isNotEmpty(outSitePlanInfoLst)) {

				// 获取外力点坐标信息
				map = outSiteService.findById(String.valueOf(outSitePlanInfoLst
						.get(0).get("OUT_SITE_ID")));

				// 获取电子围栏坐标
				List<Map<String, Object>> elebars = outSiteService
						.getElebar(String.valueOf(outSitePlanInfoLst.get(0)
								.get("PLAN_ID")));

				//jsonObject
				// .put("ebar", JSONArray.fromObject(elebars).toString());

				//map.put("elebar", jsonObject.toString());

				map.put("elebar", elebars);
				map.put("status", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.getWriter().write(JSONObject.fromObject(map).toString());
	}

}
