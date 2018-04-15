package icom.axx.service.impl;

import icom.axx.dao.AxxInterfaceDao;
import icom.axx.dao.OutSiteInterfaceDao;
import icom.axx.service.OutSiteInterfaceService;
import icom.system.dao.TaskInterfaceDao;
import icom.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.linePatrol.dao.FixOrderDao;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.StringUtil;
import com.outsite.dao.MainOutSiteDao;
import com.outsite.dao.OsMaintainSchemeDao;
import com.system.dao.MmsModelDao;
import com.system.dao.StaffDao;
import com.system.service.ParamService;
import com.system.sys.OnlineUserListener;
import com.util.MapDistance;
import com.util.sendMessage.SendMessageUtil;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@Service
public class OutSiteInterfaceServiceImpl implements OutSiteInterfaceService {

	@Resource
	private OutSiteInterfaceDao outSiteInterfaceDao;
	@Resource
	private MainOutSiteDao mainOutSiteDao;
	
	@Resource
	private OsMaintainSchemeDao osMaintainSchemeDao;
	
	@Resource
	private MmsModelDao mmsModelDao;
	
	@Resource
	private StaffDao staffDao;
	
	@Resource
	private ParamService paramService;
	
	@Resource
	private GuardTrackExecutor guardTrackExecutor;
	
	@Resource
	private FixOrderDao fixOrderDao;
	
	@Resource
	private AxxInterfaceDao axxInterfaceDao;
	
	@Resource
	private TaskInterfaceDao taskInterfaceDao;

	@Override
	public String saveBasicOuteSite(String jsonStr) {

		// 1、外力点基本信息增加
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String user_id = jsonObject.getString("user_id");
		String site_name = jsonObject.getString("site_name");// 外力名称
		String x = jsonObject.getString("x");// 外力点经度
		String y = jsonObject.getString("y");// 外力点纬度
		String info_source = jsonObject.getString("info_source");// 信息来源
		String affected_fiber = jsonObject.getString("affected_fiber");// 所属光缆
		String relay_part = jsonObject.getString("relay_part");// 所属中继段
		String fiber_level = jsonObject.getString("fiber_level");// 光缆级别
		String line_part = jsonObject.getString("line_part");// 线务段
		String landmarkno = jsonObject.getString("landmarkno");// 地标
		String con_company = jsonObject.getString("con_company");
		String con_address = jsonObject.getString("con_address");
		String con_content = jsonObject.getString("con_content");
		String is_agreement = jsonObject.getString("is_agreement");// 是否签有协议
		String site_danger_level = jsonObject.getString("site_danger_level");// 外力点等级
		String con_startdate = jsonObject.getString("con_startdate");// 外力开始日期
		String pre_enddate = jsonObject.getString("pre_enddate");// 外力点预计结束时间
		String con_reponsible_by = jsonObject.getString("con_reponsible_by");// 施工方负责人
		String con_reponsible_by_tel = jsonObject.getString("con_reponsible_by_tel");// 施工方负责人电话
		String guardian = jsonObject.getString("guardian");
		String guardian_tel = jsonObject.getString("guardian_tel");
		String scene_measure = jsonObject.getString("scene_measure");// 现场防护措施
		String fiber_eponsible_by = jsonObject.getString("fiber_eponsible_by");// 电信方负责人
		String fiber_uid = jsonObject.getString("fiber_uid");
		String stay_time_part = jsonObject.getString("stay_time_part");
		String is_plan = jsonObject.getString("is_plan");
		String area_id = jsonObject.getString("area_id");// 区域
		String is_machaine = jsonObject.getString("is_machaine");//线路十米范围内是否有机械
		String machaine_name = jsonObject.getString("machaine_name");//机械名称
		String residual_cable = jsonObject.getString("residual_cable");// 是否有直埋余缆
		String effect_service = jsonObject.getString("effect_service");// 光缆中断是否影响业务
		
		// 获取负责人工号 姓名
		Map<String, Object> febInfo = staffDao.getStaff(fiber_eponsible_by);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fiber_uid", febInfo.get("STAFF_NO").toString() + " " + febInfo.get("STAFF_NAME").toString());
		map.put("site_name", site_name);
		map.put("x", x);// 经度
		map.put("y", y);// 纬度
		map.put("info_source", info_source);// 信息来源
		map.put("affected_fiber", affected_fiber);
		map.put("relay_part", relay_part);
		map.put("fiber_level", fiber_level);
		map.put("line_part", line_part);
		map.put("landmarkno", landmarkno);
		map.put("con_company", con_company);
		map.put("con_address", con_address);
		map.put("con_content", con_content);
		map.put("is_agreement", is_agreement);
		map.put("site_danger_level", site_danger_level);
		map.put("con_startdate", con_startdate);
		map.put("pre_enddate", pre_enddate);
		map.put("con_reponsible_by", con_reponsible_by);// 电信方负责人
		map.put("con_reponsible_by_tel", con_reponsible_by_tel);
		map.put("guardian", guardian);
		map.put("guardian_tel", guardian_tel);
		map.put("scene_measure", scene_measure);
		map.put("fiber_eponsible_by", fiber_eponsible_by);
		map.put("user_id", user_id);
		map.put("is_machaine", is_machaine);
		map.put("machaine_name", machaine_name);
		map.put("residual_cable", residual_cable);
		map.put("effect_service", effect_service);

		map.put("stay_time_part", stay_time_part);
		map.put("is_plan", is_plan);
		map.put("area_id", area_id);
		map.put("creation_time", DateUtil.getDateAndTime());
		map.put("flow_status", "1");// 流程到班组长或段长审核阶段

		String out_site_id = mainOutSiteDao.getId();
		map.put("out_site_id", out_site_id);
		mainOutSiteDao.mainOutSiteSave(map);

		// 2、现场防护措施 不空 自动成成维护方案
		if (StringUtil.isNOtNullOrEmpty(scene_measure)) {
			Map<String, Object> shemeMap = new HashMap<String, Object>();
			shemeMap.put("scheme_name", scene_measure);
			shemeMap.put("ms_content", scene_measure);
			shemeMap.put("creation_time", DateUtil.getDateAndTime());
			shemeMap.put("user_id", user_id);
			shemeMap.put("created_by", user_id);
			shemeMap.put("scheme_id", osMaintainSchemeDao.getNextId());
			shemeMap.put("out_site_id", out_site_id);
			shemeMap.put("area_id", area_id);
			osMaintainSchemeDao.osMaintainSchemeSave(shemeMap);
		}

		// 3、添加机械手信息
		String operator_name = jsonObject.getString("operator_name");
		String car_no = jsonObject.getString("car_no");
		String car_type = jsonObject.getString("car_type");
		String mobile = jsonObject.getString("mobile");
		Map<String, Object> jxsMap = new HashMap<String, Object>();
		jxsMap.put("out_site_id", out_site_id);
		jxsMap.put("operator_name", operator_name);
		jxsMap.put("car_no", car_no);
		jxsMap.put("car_type", car_type);
		jxsMap.put("mobile", mobile);
		mainOutSiteDao.saveJxs(jxsMap);

		// 4、外力点流程增加
		Map<String, Object> outsiteFlow = new HashMap<String, Object>();
		outsiteFlow.put("OUT_SITE_ID", out_site_id);
		outsiteFlow.put("REVIEW_STAFF", fiber_eponsible_by);
		outsiteFlow.put("OPINON", "");
		outsiteFlow.put("IS_SCENCE", "");
		outsiteFlow.put("REVIEW_STATUS", "");
		outsiteFlow.put("FLOW_STATUS", "0");// 巡线员上报
		outsiteFlow.put("COMMIT_TIME", DateUtil.getDateAndTime());
		outSiteInterfaceDao.addOutsiteFlow(outsiteFlow);

		// 5、推送给班组长或段长
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("staff_id", fiber_eponsible_by);
		queryMap.put("manage_level", "2");
		queryMap.put("area_id", area_id);
		List<Map<String, Object>> pushUsers = outSiteInterfaceDao.getUsersByLevel(queryMap);

		if (null != pushUsers && pushUsers.size() > 0) {
			
			String title = "外力点确认和维护方案审核";
			String context = "外力点名称：" + site_name + "外力点地址：" + con_address;
			String sendMsg = "标题：" + title + " 内容：" + context;
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < pushUsers.size(); i++) {
				String staff_no = pushUsers.get(i).get("STAFF_ID").toString();
				sb.append(staff_no + ",");
				
				SendMessageUtil.sendMessageInfo(String.valueOf(pushUsers.get(i).get("TEL")), sendMsg, area_id);//短信发送
			}
			sb.deleteCharAt(sb.length() - 1);
			JSONObject message = new JSONObject();
			message.put("entity_id", out_site_id);
			message.put("type", "0");//外力点推送
			message.put("content", context);
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("PUSH_STAFF", sb.toString());
			messageMap.put("PUSH_TITLE", title);
			messageMap.put("PUSH_CONTENT", message.toString());
			messageMap.put("PUSH_RESULT", "0");//待推送
			messageMap.put("PUSH_TYPE", "1");//外力点推送
			paramService.insertPushMessage(messageMap);
		}

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");
		res.put("site_id", out_site_id);
		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String saveAuditOuteSite(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);

		String user_id = jsonObject.getString("userId");// 当前用户
		String sn = jsonObject.getString("sn");//手机串号
		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		String out_site_id = jsonObject.getString("site_id");// 外力点ID
		String site_name = jsonObject.getString("site_name");// 外力点名称
		String info_source = jsonObject.getString("info_source");// 信息来源
		String affected_fiber = jsonObject.getString("affected_fiber");// 所属光缆
		String relay_part = jsonObject.getString("relay_part");// 所属中继段
		String fiber_level = jsonObject.getString("fiber_level");// 光缆级别
		String line_part = jsonObject.getString("line_part");// 线务段
		String landmarkno = jsonObject.getString("landmarkno");// 地标
		String con_company = jsonObject.getString("con_company");
		String con_address = jsonObject.getString("con_address");
		String con_content = jsonObject.getString("con_content");
		String is_agreement = jsonObject.getString("is_agreement");// 是否签有协议
		String site_danger_level = jsonObject.getString("site_danger_level");// 外力隐患等级
		String con_startdate = jsonObject.getString("con_startdate");// 外力施工开始日期
		String pre_enddate = jsonObject.getString("pre_enddate");// 外力施工预计结束时间
		String scene_measure = jsonObject.getString("scene_measure");// 现场防护措施
		String area_id = jsonObject.getString("area_id");// 区域ID
		String is_machaine = jsonObject.getString("is_machaine");//线路十米范围内是否有机械
		String machaine_name = jsonObject.getString("machaine_name");//机械名称
		String residual_cable = jsonObject.getString("residual_cable");// 是否有直埋余缆
		String effect_service = jsonObject.getString("effect_service");// 光缆中断是否影响业务
		String operator_name = jsonObject.getString("operator_name");// 机械手信息
		String car_no = jsonObject.getString("car_no");// 机械手车子信息
		String car_type = jsonObject.getString("car_type");// 机械手车子类型
		String mobile = jsonObject.getString("mobile");// 机械手手机号码
		JSONObject schemeinfo = JSONObject.fromObject(jsonObject.getJSONObject("schemeinfo"));// 防护方案信息
		String content = schemeinfo.getString("content");// 方案内容
		String scheme_name = schemeinfo.getString("scheme_name");// 方案名称

		// 1、机械手信息变更
		Map<String, Object> jxsMap = new HashMap<String, Object>();
		jxsMap.put("out_site_id", out_site_id);
		jxsMap.put("operator_name", operator_name);
		jxsMap.put("car_no", car_no);
		jxsMap.put("car_type", car_type);
		jxsMap.put("mobile", mobile);
		List<Map<String, Object>> jxsList = mainOutSiteDao.getCzs(jxsMap);
		if (null != jxsList && jxsList.size() > 0) {
			jxsMap.put("operator_id", jxsList.get(0).get("OPERATOR_ID"));
			mainOutSiteDao.jxsUpdate(jxsMap);
		} else {
			mainOutSiteDao.saveJxs(jxsMap);
		}

		// 2、维护方案变更
		Map<String, Object> shemeMap = new HashMap<String, Object>();
		shemeMap.put("out_site_id", out_site_id);
		List<Map<String, Object>> schemeList = outSiteInterfaceDao.getOutSchemeBySiteId(shemeMap);
		String scheme_id = "";
		if (null != schemeList && schemeList.size() > 0) {
			scheme_id = schemeList.get(0).get("SCHEME_ID").toString();
			shemeMap.put("scheme_name", scheme_name);
			shemeMap.put("scheme_id", scheme_id);
			shemeMap.put("ms_content", content);
			shemeMap.put("out_site_id", out_site_id);
			shemeMap.put("area_id", area_id);
			shemeMap.put("update_time", DateUtil.getDateAndTime());
			shemeMap.put("updated_by", user_id);
			shemeMap.put("user_id", user_id);
			shemeMap.put("created_by", user_id);
			osMaintainSchemeDao.osMaintainSchemeUpdate(shemeMap);
		} else {
			scheme_id = osMaintainSchemeDao.getNextId();
			shemeMap.put("scheme_name", scheme_name);
			shemeMap.put("ms_content", content);
			shemeMap.put("user_id", user_id);
			shemeMap.put("creation_time", DateUtil.getDateAndTime());
			shemeMap.put("created_by", user_id);
			shemeMap.put("scheme_id", scheme_id);
			shemeMap.put("area_id", area_id);
			osMaintainSchemeDao.osMaintainSchemeSave(shemeMap);
		}

		// 3、外力点变更
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("site_name", site_name);
		map.put("info_source", info_source);
		map.put("affected_fiber", affected_fiber);
		map.put("relay_part", relay_part);
		map.put("fiber_level", fiber_level);
		map.put("line_part", line_part);
		map.put("landmarkno", landmarkno);
		map.put("con_company", con_company);
		map.put("con_address", con_address);
		map.put("con_content", con_content);
		map.put("is_agreement", is_agreement);
		map.put("site_danger_level", site_danger_level);
		map.put("con_startdate", con_startdate);
		map.put("pre_enddate", pre_enddate);
		map.put("scene_measure", scene_measure);
		map.put("area_id", area_id);
		map.put("update_time", DateUtil.getDate());
		map.put("out_site_id", out_site_id);
		map.put("is_machaine", is_machaine);
		map.put("machaine_name", machaine_name);
		map.put("residual_cable", residual_cable);
		map.put("effect_service", effect_service);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staff_id", user_id);

		Map<String, Object> userInfo = outSiteInterfaceDao.getUserInfo(param);
		Map<String, Object> queryMap = new HashMap<String, Object>();// 推送人员查询条件
		if ("1".equals(userInfo.get("AREA_LEVEL").toString())) {//分公司级别审批
			if ("1".equals(fiber_level)) {
				map.put("flow_status", "2");// 一干光缆,流程转到县公司分管领导确认
				queryMap.put("staff_id", user_id);
				queryMap.put("manage_level", "0");// 分管领导
				queryMap.put("area_level", "1");// 分公司
			} else {
				map.put("flow_status", "4");// 二干光缆,流程转到县公司主要管理和技术人员确认
				queryMap.put("staff_id", user_id);
				queryMap.put("manage_level", "1");// 主要管理和技术人员确认
				queryMap.put("area_level", "1");// 分公司
			}
		} else {//市级别审批
			if ("1".equals(fiber_level)) {
				map.put("flow_status", "3");// 一干光缆,流程转到市公司分管领导确认
				queryMap.put("manage_level", "0");// 分管领导
				queryMap.put("area_level", "0");// 市公司
			} else {
				map.put("flow_status", "5");// 二干光缆,流程转到市公司主要管理和技术人员确认
				queryMap.put("manage_level", "1");// 主要管理和技术人员确认
				queryMap.put("area_level", "0");// 市公司
			}
		}

		mainOutSiteDao.mainOutSiteUpdate(map);

		// 4、外力点流程增加审核步骤
		Map<String, Object> outsiteFlow = new HashMap<String, Object>();
		outsiteFlow.put("OUT_SITE_ID", out_site_id);
		outsiteFlow.put("REVIEW_STAFF", user_id);
		outsiteFlow.put("OPINON", "");
		outsiteFlow.put("IS_SCENCE", "");
		outsiteFlow.put("REVIEW_STATUS", "1");
		outsiteFlow.put("FLOW_STATUS", "1");// 班组长或段长审核
		outsiteFlow.put("COMMIT_TIME", DateUtil.getDateAndTime());
		outSiteInterfaceDao.addOutsiteFlow(outsiteFlow);

		// 5、消息推送

		queryMap.put("area_id", area_id);
		List<Map<String, Object>> pushUsers = outSiteInterfaceDao.getUsersByLevel(queryMap);
		if (null != pushUsers && pushUsers.size() > 0) {
			
			String title = " 制定防护方案";
			String context ="外力点名称：" + site_name + " 维护方案名称：" + shemeMap.get("scheme_name").toString();
			String userName = "";
			if(null != userInfo && null != userInfo.get("STAFF_NAME")){
				userName = String.valueOf(userInfo.get("STAFF_NAME"));
			}
			String sendMsg = "标题：" + title + " 内容：" + context + " 确认人: " + userName;
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < pushUsers.size(); i++) {
				String staff_no = pushUsers.get(i).get("STAFF_ID").toString();
				sb.append(staff_no + ",");
				
				SendMessageUtil.sendMessageInfo(String.valueOf(pushUsers.get(i).get("TEL")), sendMsg, area_id);//短信发送
			}
			sb.deleteCharAt(sb.length() - 1);
			
			JSONObject message = new JSONObject();
			message.put("entity_id", out_site_id);
			message.put("type", "0");//外力点推送
			message.put("content", context);
			
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("PUSH_STAFF", sb.toString());
			messageMap.put("PUSH_TITLE", title);
			messageMap.put("PUSH_CONTENT", message.toString());
			messageMap.put("PUSH_RESULT", "0");//待推送
			messageMap.put("PUSH_TYPE", "1");//外力点推送
			paramService.insertPushMessage(messageMap);
		}

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");
		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String saveOperatorInfo(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);

		String user_id = jsonObject.getString("user_id");
		String out_site_id = jsonObject.getString("out_site_id");
		String operator_name = jsonObject.getString("operator_name");
		String car_no = jsonObject.getString("car_no");
		String car_type = jsonObject.getString("car_type");
		String mobile = jsonObject.getString("mobile");
		String tel1 = jsonObject.getString("tel1");
		String tel2 = jsonObject.getString("tel2");
		String info = jsonObject.getString("info");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("out_site_id", out_site_id);
		map.put("operator_name", operator_name);
		map.put("car_no", car_no);
		map.put("car_type", car_type);
		map.put("mobile", mobile);
		map.put("tel1", tel1);
		map.put("tel2", tel2);
		map.put("info", info);

		map.put("user_id", user_id);

		mainOutSiteDao.saveJxs(map);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String saveOsMaintainScheme(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);

		String user_id = jsonObject.getString("user_id");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		String scheme_name = jsonObject.getString("scheme_name");
		String out_site_id = jsonObject.getString("out_site_id");
		String out_site_type = jsonObject.getString("out_site_type");
		String content = jsonObject.getString("content");
		String area_id = jsonObject.getString("area_id");
		// String creation_time = jsonObject.getString("creation_time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scheme_name", scheme_name);
		map.put("out_site_id", out_site_id);
		map.put("out_site_type", out_site_type);
		map.put("content", content);
		map.put("user_id", user_id);
		map.put("area_id", area_id);
		map.put("creation_time", DateUtil.getDateAndTime());

		final String scheme_id = osMaintainSchemeDao.getNextId();
		map.put("scheme_id", scheme_id);
		osMaintainSchemeDao.osMaintainSchemeSave(map);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");

		// 5、推送给班组长或段长的上一级
		List<Map<String, Object>> pushUsers = outSiteInterfaceDao.getLocalQrry(user_id);
		String title = "外力点防护方案";
		String message = "方案名：" + scheme_name;
		String sendMsg = "标题：" + title + " 内容：" + message;
		
		if (null != pushUsers && pushUsers.size() > 0) {
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < pushUsers.size(); i++) {
				String staff_no = pushUsers.get(i).get("STAFF_ID").toString();
				sb.append(staff_no + ",");
				
				SendMessageUtil.sendMessageInfo(String.valueOf(pushUsers.get(i).get("TEL")), sendMsg, area_id);//短信发送
			}
			sb.deleteCharAt(sb.length() - 1);
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("PUSH_STAFF", sb.toString());
			messageMap.put("PUSH_TITLE", title);
			messageMap.put("PUSH_CONTENT", message);
			messageMap.put("PUSH_RESULT", "0");//待推送
			messageMap.put("PUSH_TYPE", "2");//维护方案确认
			paramService.insertPushMessage(messageMap);
		}
		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String makesureScheme(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		
		String commit_uid = jsonObject.getString("commit_uid");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(commit_uid, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String scheme_id = jsonObject.getString("scheme_id");
		String commit_x = jsonObject.getString("commit_x");
		String commit_y = jsonObject.getString("commit_y");

		String is_timeout = jsonObject.getString("is_timeout");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scheme_id", scheme_id);
		map.put("commit_uid", commit_uid);
		map.put("commit_x", commit_x);
		map.put("commit_y", commit_y);

		map.put("commit_date", DateUtil.getDateAndTime());
		map.put("is_timeout", is_timeout);

		mainOutSiteDao.makesureScheme(map);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String saveOsInputPoints(String jsonStr, String areaId) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		
		String input_userid = jsonObject.getString("input_userid");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(input_userid, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String plan_id = jsonObject.getString("plan_id");
		String parent_city = jsonObject.getString("parent_city");
		double x = jsonObject.getDouble("x");
		double y = jsonObject.getDouble("y");
		// 取出中心坐标
		Map<String, Object> res = outSiteInterfaceDao.getOsPlanCenter(plan_id);
		double center_x = Double.parseDouble(res.get("CENTER_X").toString());
		double center_y = Double.parseDouble(res.get("CENTER_Y").toString());

		// 距离
		String siterange = paramService.getParamValue("siterange",parent_city);// 获取站点匹配距离
		Double range = Double.parseDouble(siterange);

		Double distance = MapDistance.getDistance(center_y, center_x, y, x);

		Map<String, Object> res2 = new HashMap<String, Object>();
		if (distance <= range) {
			String out_site_id = jsonObject.getString("out_site_id");
			String out_site_tyle = jsonObject.getString("out_site_tyle");

			String input_time = jsonObject.getString("input_time");

			String created_by = jsonObject.getString("created_by");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("out_site_id", out_site_id);
			map.put("out_site_tyle", out_site_tyle);
			map.put("x", x);
			map.put("y", y);
			map.put("input_userid", input_userid);
			map.put("input_time", input_time);
			map.put("parent_city", parent_city);
			map.put("creation_time", DateUtil.getDateAndTime());
			map.put("created_by", created_by);
			map.put("sn", sn);

			mainOutSiteDao.saveOsInputPoints(map);

			res2.put("result", "000");

			return JSONObject.fromObject(res2).toString();
		} else {

			res2.put("result", "001");

			return JSONObject.fromObject(res2).toString();
		}

	}

	@Override
	public String saveDepthProbe(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		
		String user_id = jsonObject.getString("user_id");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(user_id, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String out_site_id = jsonObject.getString("out_site_id");
		String markstone = jsonObject.getString("markstone");
		String mstw_depth = jsonObject.getString("mstw_depth");
		String longitude = jsonObject.getString("longitude");
		String latitude = jsonObject.getString("latitude");
		String remark = jsonObject.getString("remark");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("out_site_id", out_site_id);
		map.put("markstone", markstone);
		map.put("mstw_depth", mstw_depth);
		map.put("user_id", user_id);
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		map.put("remark", remark);
		map.put("sn", sn);
		map.put("upload_time", DateUtil.getDateAndTime());

		mainOutSiteDao.saveDepthProbe(map);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getDepthProbe(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String out_site_id = jsonObject.getString("out_site_id");
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("out_site_id", out_site_id);

		List<Map<String, Object>> depthProbeList = mainOutSiteDao.getMstwInfo(para);// getMstwInfo
		// getDepthProbe
		List<Map<String, Object>> depthProbeList2 = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < depthProbeList.size(); i++) {
			map = depthProbeList.get(i);

			Map<String, Object> nmap = new HashMap<String, Object>();
			nmap.put("markstone", map.get("MARKSTONE") == null ? "" : map.get("MARKSTONE").toString());
			nmap.put("depth", map.get("MSTW_DEPTH") == null ? "" : map.get("MSTW_DEPTH").toString());
			// nmap.put("user_name", map.get("USER_NAME"));
			nmap.put("longitude", map.get("LONGITUDE") == null ? "" : map.get("LONGITUDE").toString());
			nmap.put("latitude", map.get("LATITUDE") == null ? "" : map.get("LATITUDE").toString());
			nmap.put("remark", map.get("REMARK") == null ? "" : map.get("MARKSTONE").toString());

			depthProbeList2.add(nmap);

		}

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("mstwlist", JSONArray.fromObject(depthProbeList2));
		res.put("result", "000");
		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String saveOsCheckRecord(String jsonStr) {

		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String user_id = jsonObject.getString("user_id");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(user_id, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String out_site_id = jsonObject.getString("out_site_id");
		String out_site_name = jsonObject.getString("out_site_name");
		String check_time = jsonObject.getString("check_time");
		String is_on_out = jsonObject.getString("is_on_out");
		String is_construction = jsonObject.getString("is_construction");
		String is_complete_equip = jsonObject.getString("is_complete_equip");
		String is_complete_look = jsonObject.getString("is_complete_look");
		String is_know_con = jsonObject.getString("is_know_con");
		String is_know = jsonObject.getString("is_know");
		String is_single = jsonObject.getString("is_single");
		String is_send_info = jsonObject.getString("is_send_info");
		String problem_mes = jsonObject.getString("problem_mes");
		String side_progress = jsonObject.getString("side_progress");
		String rectification = jsonObject.getString("rectification");
		String assess = jsonObject.getString("assess");
		String area_id = jsonObject.getString("area_id");
		String created_by = jsonObject.getString("created_by");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("out_site_id", out_site_id);
		map.put("out_site_name", out_site_name);
		map.put("check_time", check_time);
		map.put("is_on_out", is_on_out);
		map.put("is_construction", is_construction);
		map.put("is_complete_equip", is_complete_equip);
		map.put("is_complete_look", is_complete_look);
		map.put("is_know_con", is_know_con);
		map.put("is_know", is_know);
		map.put("is_single", is_single);
		map.put("is_send_info", is_send_info);
		map.put("problem_mes", problem_mes);
		map.put("side_progress", side_progress);
		map.put("rectification", rectification);
		map.put("assess", assess);
		map.put("area_id", area_id);
		map.put("created_by", created_by);
		map.put("sn", sn);

		map.put("creation_time", DateUtil.getDateAndTime());

		String out_record_id = mainOutSiteDao.getOscheckRecordId();
		map.put("out_record_id", out_record_id);
		mainOutSiteDao.saveOsCheckRecord(map);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");
		res.put("site_id", out_record_id);

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getOsProtectTaskList(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}
		
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("userId", userId);

		List<Map<String, Object>> taskList = mainOutSiteDao.getOsProtectTaskList(para);
		List<Map<String, Object>> taskList2 = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < taskList.size(); i++) {
			map = taskList.get(i);
			Map<String, Object> nmap = new HashMap<String, Object>();
			nmap.put("site_name", map.get("SITE_NAME"));
			nmap.put("start_date", map.get("START_DATE"));
			nmap.put("end_date", map.get("END_DATE"));
			taskList2.add(nmap);
		}

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("taskList", JSONArray.fromObject(taskList2));
		res.put("result", "000");
		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String saveOsProtectCoordinate(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
//		paramService.saveLogInfo("OutSiteInterfaceServiceImpl", "saveOsProtectCoordinate(String jsonStr)", "DEBUG", jsonStr);// 日志记录
		String sn = jsonObject.getString("sn");
		String userId = jsonObject.getString("userId");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String plan_id = jsonObject.getString("plan_id");
		double x = jsonObject.getDouble("x");
		double y = jsonObject.getDouble("y");
		String is_gps = jsonObject.getString("is_gps");
		String gps_switch = jsonObject.getString("gps_switch");//GPS是否打开，1打开 0未打开
		String plan_time = jsonObject.getString("plan_time");
		String parent_city = jsonObject.getString("parent_city");
		String created_by = jsonObject.getString("created_by");
//		String is_guard = jsonObject.getString("is_guard");
		
		String is_guard="0";//是否在看护范围 1在 0不在
		Object object =jsonObject.get("is_guard");
		JSONArray latLngs = JSONArray.fromObject(object);
		Map<String, Double> latLng =new HashMap<String, Double>();
		latLng.put("longitude", x);
		latLng.put("latitude", y);
		if(guardTrackExecutor.contains(latLngs, latLng)){
			is_guard="1";
		}
		if ("4.9E-324".equals(x) || "4.9E-324".equals(y)) {//过滤无信号数据
			x = 0;
			y = 0;
		}
		String track_time = jsonObject.getString("track_time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("plan_id", plan_id);
		map.put("x", x);
		map.put("y", y);
		map.put("is_gps", is_gps);
		map.put("gps_switch", gps_switch);
		map.put("plan_time", plan_time);
		map.put("parent_city", parent_city);
		map.put("creation_time", DateUtil.getDateAndTime());
		map.put("created_by", created_by);
		map.put("sn", sn);
		map.put("is_guard", is_guard);
		map.put("track_time", track_time);

		// 查询上次是否在电子围栏内 group_flag分组标志
//		map.put("today", DateUtil.getDate());
//		Map<String, Object> res = outSiteInterfaceDao.isInGuardLast(map);
//
//		int group_flag = -1;
//		if (null == res) {
//			group_flag = 0;
//		} else if (is_guard.equals(res.get("IS_GUARD").toString())) {
//			group_flag = Integer.parseInt(res.get("GROUP_FLAG").toString());
//		} else {
//			group_flag = Integer.parseInt(res.get("GROUP_FLAG").toString()) + 1;
//		}
//		map.put("group_flag", group_flag);
		/**
		 * 插入外力点看护轨迹表
		 */
		mainOutSiteDao.saveOsProtectCoordinate(map);

		if ("0".equals(is_guard)) {// 不在监控范围之类
			// 发短信
			// 1看护员信息--姓名 位置 电话
			Map<String, Object> khyInfo = staffDao.getStaff(created_by);
			String khyName = khyInfo.get("STAFF_NAME").toString();
			String sms_no = khyInfo.get("TEL") == null ? "" : khyInfo.get("TEL").toString();
			// 外力点类型 外力点名称 外力点地址
			Map<String, Object> outSiteInfo = outSiteInterfaceDao.getOutSiteByPlanId(plan_id);
			String outsiteName = "";
			String con_address = "";
			if(outSiteInfo != null && outSiteInfo.size()>0){
				outsiteName = outSiteInfo.get("SITE_NAME").toString();
				con_address = outSiteInfo.get("CON_ADDRESS").toString();
			}

			// 原因
			String reason = "超出看护距离";

			// 2查出对象 模板
			List<Map<String, Object>> targetModelList = mmsModelDao.getSettings(created_by);

			for (int i = 0; i < targetModelList.size(); i++) {
				Map<String, Object> map2 = targetModelList.get(i);
				String send_type = map2.get("SEND_TYPE").toString();
				String send_phoneNmber = "";
				String send_name = "";
				String mm_content = map2.get("MM_CONTENT").toString();
				// 3查出对象 对象手机 0自己，1上级，2相关人员，3管理员,4外力点负责人--姓名
				if ("0".equals(send_type)) {
					send_phoneNmber = sms_no;
				} else if ("1".equals(send_type)) {
					// 获取上级号码 姓名

				} else if ("2".equals(send_type)) {
					// 获取相关人员号码 姓名

				} else if ("3".equals(send_type)) {
					// 获取管理员号码 姓名

				} else if ("4".equals(send_type)) {
					// 获取外力点负责人号码 姓名
					send_phoneNmber = outSiteInfo.get("FIBER_EPONSIBLE_TEL").toString();
					send_name = outSiteInfo.get("FIBER_EPONSIBLE_BY").toString();
				}

				// 4 替换模板
				mm_content = mm_content.replaceAll("\\{收信人姓名\\}", send_name);
				mm_content = mm_content.replaceAll("\\{看护员姓名\\}", khyName);
				mm_content = mm_content.replaceAll("\\{看护员最新位置\\}", "经度：" + x + "纬度：" + y + "");
				mm_content = mm_content.replaceAll("\\{看护员电话\\}", sms_no);
				mm_content = mm_content.replaceAll("\\{外力点类型\\}", null);
				mm_content = mm_content.replaceAll("\\{外力点名称\\}", outsiteName);
				mm_content = mm_content.replaceAll("\\{外力点地址\\}", con_address);
				mm_content = mm_content.replaceAll("\\{原因\\}", reason);
				mm_content = mm_content.replaceAll("\\{当前时间\\}", DateUtil.getDateAndTime());
				System.out.println(mm_content);
				// 5发送

			}

		}

		Map<String, Object> resmap = new HashMap<String, Object>();
		resmap.put("result", "000");

		return JSONObject.fromObject(resmap).toString();
	}

	@Override
	public String getOutSites(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		double longitude = jsonObject.getDouble("longitude");
		double latitude = jsonObject.getDouble("latitude");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		String areaId = jsonObject.getString("area_id");
		List<Map<String, Object>> resultList= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> outSitesList = mainOutSiteDao.getOutSitesByAreaId(areaId);
		for (Map<String, Object> map : outSitesList) { //得到距离用户1公里以内的所有外力点信息
			double distance = MapDistance.getDistance(latitude,longitude,Double.valueOf(map.get("Y").toString()),Double.valueOf(map.get("X").toString()));
			if(distance<=1000){
				resultList.add(map);
			}
		}
		
		for (int i = 0; i < resultList.size(); i++) {  //key转小写
			Map<String, Object> map = resultList.get(i);
			map.put("outsite_id", map.get("OUT_SITE_ID"));
			map.put("outsite_name", map.get("SITE_NAME"));
			map.put("is_add_scheme", map.get("IS_ADD_SCHEME"));
		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("outsiteList", JSONArray.fromObject(resultList));

		return JSONObject.fromObject(res).toString();
	}

	// 外力点迁移
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveOutSiteMoveInfo(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String outsite_id = jsonObject.getString("outsite_id");
		String old_longitude = jsonObject.getString("old_longitude");
		String old_latitude = jsonObject.getString("old_latitude");
		String new_longitude = jsonObject.getString("new_longitude");
		String new_latitude = jsonObject.getString("new_latitude");
		String move_time = jsonObject.getString("move_time");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", userId);
		map.put("out_site_id", outsite_id);
		map.put("old_longitude", old_longitude);
		map.put("old_latitude", old_latitude);
		map.put("new_longitude", new_longitude);
		map.put("new_latitude", new_latitude);
		map.put("move_time", move_time);

		map.put("sn", sn);

		try {
			mainOutSiteDao.saveOutSiteMoveInfo(map);

			map.put("x", new_longitude);
			map.put("y", new_latitude);
			mainOutSiteDao.mainOutSiteUpdate(map);

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");

		return JSONObject.fromObject(res).toString();
	}

	/*
	 * 确认维护方案--查询
	 */
	@Override
	public String getOutSchemes(String p) {
		JSONObject jsonObject = JSONObject.fromObject(p);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String areaId = jsonObject.getString("area_id");
		double x = jsonObject.getDouble("longitude");
		double y = jsonObject.getDouble("latitude");

		// 外力点签到距离
		String siterange = paramService.getParamValue("siterange", areaId);// 获取站点匹配距离
		Double range = Double.parseDouble(siterange);

		// 根据管理员 技术员id查询方案
		List<Map<String, Object>> outSchemes = mainOutSiteDao.getOutSchemesByJsyId(userId);//
		//
		for (int i = 0; i < outSchemes.size(); i++) {
			Map<String, Object> map = outSchemes.get(i);

			String outsiteId = map.get("OUT_SITE_ID").toString();
			map.put("outsite_id", outsiteId);
			map.put("scheme_name", map.get("SCHEME_NAME"));
			map.put("scheme_id", map.get("SCHEME_ID"));
			map.put("content", map.get("MS_CONTENT") == null ? "" : map.get("MS_CONTENT").toString());
			map.put("sign_distance", range);
			map.put("outside_name", map.get("SITE_NAME"));
			map.put("upload_name", map.get("UPLOAD_NAME"));
			map.put("upload_date", map.get("CREATION_TIME").toString());

			// 获取外力点
			Map<String, Object> outSiteInfo = mainOutSiteDao.findById(outsiteId);
			double os_x = Double.parseDouble(outSiteInfo.get("X").toString());
			double os_y = Double.parseDouble(outSiteInfo.get("Y").toString());
			Double distance = MapDistance.getDistance(os_y, os_x, y, x);
			map.put("distance", distance);

		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("schemeList", JSONArray.fromObject(outSchemes));

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getOutCheckArrivalRate(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String query_time = jsonObject.getString("query_time");

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("query_time", query_time);
		
		if(jsonObject.getString("personId")!=null && !"".equals(jsonObject.getString("personId"))){
			userId=jsonObject.getString("personId");
		}
		
		p.put("userId", userId);

		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject res = new JSONObject();
		List<Map<String, Object>> resTaskList = new ArrayList<Map<String, Object>>();
		try {

			// 获取计划任务 对于外力点检查 每天只有一个任务
			List<Map<String, Object>> task_outsite = outSiteInterfaceDao.getTask2Outsite(p);
			if(task_outsite.size() > 0){
				// 获取外力点匹配时间
				Map<String, Object> staffInfo = staffDao.getStaff(userId);
				String areaId = staffInfo.get("AREA_ID").toString();

				map.put("areaId", areaId);
				map.put("paramName", "OutSiteStay");

				// 非隐患
				String OutSiteStay = paramService.getParamValue("OutSiteStay", areaId);
				
				// 隐患
				map.put("paramName", "UnSafeOutSiteStay");
				String UnSafeOutSiteStay =  paramService.getParamValue("UnSafeOutSiteStay", areaId);

				p.put("OutSiteStay", OutSiteStay);
				p.put("UnSafeOutSiteStay", UnSafeOutSiteStay);

				String task_id = task_outsite.get(0).get("TASK_ID").toString();
				p.put("task_id", task_id);

				String actual_count = outSiteInterfaceDao.getActualCount(p);

				// 任务信息
				Map<String, Object> taskInfo = outSiteInterfaceDao.getTaskInfo(task_id);
				
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("task_id", task_id);
				map2.put("task_name", taskInfo.get("TASK_NAME"));
				map2.put("task_id", task_id);
				map2.put("actual_count", actual_count);
				map2.put("query_date", query_time);
				map2.put("total_count", task_outsite.size());
				resTaskList.add(map2);
				res.put("tasks", JSONArray.fromObject(resTaskList));
				res.put("result", "000");
			}else{
				res.put("tasks", JSONArray.fromObject(resTaskList));
				res.put("result", "000");
			}
		} catch (Exception e) {
			res.put("tasks", JSONArray.fromObject(resTaskList));
			res.put("result", "000");

		} 
		
		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getOutsiteMoveInfo(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		double longitude = jsonObject.getDouble("longitude");
		double latitude = jsonObject.getDouble("latitude");
		Map<String, Object> staffInfo = staffDao.getStaff(userId);
		String areaId = staffInfo.get("AREA_ID").toString();

		List<Map<String, Object>> outSitesList = mainOutSiteDao.getOutSitesByAreaId(areaId);
		List<Map<String, Object>> outSitesList2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < outSitesList.size(); i++) {
			Map<String, Object> map = outSitesList.get(i);
			double x = Double.parseDouble(map.get("X").toString());
			double y = Double.parseDouble(map.get("Y").toString());

			Double distance = MapDistance.getDistance(latitude, longitude, y, x);
			// 1000米范围之类
			if (distance <= 1000) {
				Map<String, Object> map2 = new HashMap<String, Object>();

				map2.put("outsite_id", map.get("OUT_SITE_ID"));
				map2.put("outsite_name", map.get("SITE_NAME"));
				map2.put("longitude", x);
				map2.put("latitude", y);
				map2.put("distance", distance);
				outSitesList2.add(map2);
			}
		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("outsitelist", JSONArray.fromObject(outSitesList2));

		return JSONObject.fromObject(res).toString();
	}

	/*
	 * 获取300米之类没有确认的外力点
	 */
	@Override
	public String getBasicOuteSites(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}
		
		double longitude = jsonObject.getDouble("longitude");
		double latitude = jsonObject.getDouble("latitude");

		Map<String, Object> staffInfo = staffDao.getStaff(userId);
		String areaId = staffInfo.get("AREA_ID").toString();

		String flow_status = getUserInfo(userId);// 根据用户获取该用户可以查询对应流程的外力点

		Map<String, Object> outsiteMap = new HashMap<String, Object>();
		outsiteMap.put("areaId", areaId);
		outsiteMap.put("flow_status", flow_status);
		if ("1".equals(flow_status) || "2".equals(flow_status) || "4".equals(flow_status)) {// 班组长和县公司领导只能审核自己分公司的外力点
			outsiteMap.put("staff_id", userId);
		}
		List<Map<String, Object>> outSitesList = outSiteInterfaceDao.getOutSitesByFlow(outsiteMap);
		List<Map<String, Object>> outSitesList2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < outSitesList.size(); i++) {
			Map<String, Object> map = outSitesList.get(i);
			Double distance = 0.0;
			if ("1".equals(flow_status)) {// 班组长需要到现场审核,其他确认人员不限制
				double x = Double.parseDouble(map.get("X").toString());
				double y = Double.parseDouble(map.get("Y").toString());
				distance = MapDistance.getDistance(latitude, longitude, y, x);
			}
			// 300米范围之类
			if (distance <= 300) {
				Map<String, Object> map2 = new HashMap<String, Object>();

				map2.put("site_id", map.get("OUT_SITE_ID").toString());
				map2.put("site_name", map.get("SITE_NAME").toString());
				map2.put("x", map.get("X").toString());
				map2.put("y", map.get("Y").toString());
				map2.put("distance", distance);
				map2.put("info_source", map.get("INFO_SOURCE").toString());
				map2.put("affected_fiber", fiterNullStr(map.get("AFFECTED_FIBER")));
				map2.put("relay_part", fiterNullStr(map.get("RELAY_PART")));
				map2.put("fiber_level", fiterNullStr(map.get("FIBER_LEVEL")));
				map2.put("line_part", fiterNullStr(map.get("LINE_PART")));
				map2.put("landmarkno", fiterNullStr(map.get("LANDMARKNO")));
				map2.put("con_company", fiterNullStr(map.get("CON_COMPANY")));
				map2.put("con_address", fiterNullStr(map.get("CON_ADDRESS")));
				map2.put("con_content", fiterNullStr(map.get("CON_CONTENT")));
				map2.put("is_agreement", fiterNullStr(map.get("IS_AGREEMENT")));
				map2.put("site_danger_level", fiterNullStr(map.get("SITE_DANGER_LEVEL")));
				map2.put("con_startdate", fiterNullStr(map.get("CON_STARTDATE")));
				map2.put("pre_enddate", fiterNullStr(map.get("PRE_ENDDATE")));
				map2.put("con_reponsible_by", fiterNullStr(map.get("CON_REPONSIBLE_BY")));
				map2.put("con_reponsible_by_tel", fiterNullStr(map.get("CON_REPONSIBLE_BY_TEL")));
				map2.put("guardian", fiterNullStr(map.get("GUARDIAN")));
				map2.put("guardian_tel", fiterNullStr(map.get("GUARDIAN_TEL")));
				map2.put("scene_measure", fiterNullStr(map.get("SCENE_MEASURE")));
				map2.put("fiber_eponsible_by", fiterNullStr(map.get("FIBER_EPONSIBLE_BY")));
				map2.put("user_id", fiterNullStr(map.get("USER_ID")));
				map2.put("fiber_uid", fiterNullStr(map.get("FIBER_UID")));
				map2.put("stay_time_part", fiterNullStr(map.get("STAY_TIME_PART")));
				map2.put("is_machaine", fiterNullStr(map.get("IS_MACHAINE")));
				map2.put("machaine_name", fiterNullStr(map.get("MACHAINE_NAME")));
				map2.put("residual_cable", fiterNullStr(map.get("RESIDUAL_CABLE")));
				map2.put("effect_service", fiterNullStr(map.get("EFFECT_SERVICE")));
				map2.put("is_plan", fiterNullStr(map.get("IS_PLAN")));
				map2.put("flow_status", fiterNullStr(map.get("FLOW_STATUS")));

				// 查询机械手信息
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("out_site_id", map.get("OUT_SITE_ID").toString());
				List<Map<String, Object>> jxsList = mainOutSiteDao.getCzs(queryMap);
				if (null != jxsList && jxsList.size() > 0) {
					map2.put("operator_name", fiterNullStr(jxsList.get(0).get("OPERATOR_NAME")));
					map2.put("car_no", fiterNullStr(jxsList.get(0).get("CAR_NO")));
					map2.put("car_type", fiterNullStr(jxsList.get(0).get("CAR_TYPE")));
					map2.put("mobile", fiterNullStr(jxsList.get(0).get("MOBILE")));
				}

				// 查询维护方案
				List<Map<String, Object>> schemes = outSiteInterfaceDao.getOutSchemeBySiteId(queryMap);
				if (null != schemes && schemes.size() > 0) {
					JSONObject schemeMap = new JSONObject();
					schemeMap.put("content", fiterNullStr(schemes.get(0).get("MS_CONTENT")));
					schemeMap.put("user_id", fiterNullStr(schemes.get(0).get("USER_ID")));
					schemeMap.put("scheme_name", fiterNullStr(schemes.get(0).get("SCHEME_NAME")));
					schemeMap.put("user_name", fiterNullStr(schemes.get(0).get("STAFF_NAME")));
					map2.put("schemeinfo", schemeMap);
				}
				
				//查看该外力点是否有埋深探位属性
				if(map.get("OUT_SITE_ID").toString() != null && map.get("OUT_SITE_ID").toString() !=""){
					int depthProbe = outSiteInterfaceDao.isDepthProbe(map.get("OUT_SITE_ID").toString());
					map2.put("isDepth", "0");//默认没有
					if(depthProbe>0){
						map2.put("isDepth", "1");//有埋深探位
					}
				}
				
				outSitesList2.add(map2);
			}
		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("sites", JSONArray.fromObject(outSitesList2));

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String confirmOutsite(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String site_id = jsonObject.getString("site_id");
		Double x = Double.parseDouble(jsonObject.getString("x"));
		Double y = Double.parseDouble(jsonObject.getString("y"));
		Double longitude = Double.parseDouble(jsonObject.getString("longitude"));
		Double latitude = Double.parseDouble(jsonObject.getString("latitude"));
		String commit_time = jsonObject.getString("commit_time");
		String commit_opnion = jsonObject.getString("commit_opnion");
		String commit_status = jsonObject.getString("commit_status");// 1确认，0驳回
		String flow_status = jsonObject.getString("flow_status");// 1确认，0驳回

		String outsite_flow = "";
		List<Map<String, Object>> pushUsers = null;
		Map<String, Object> outSiteInfo = mainOutSiteDao.findById(site_id);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		String area_id= String.valueOf(outSiteInfo.get("AREA_ID"));
		queryMap.put("area_id", area_id);
		
		if ("1".equals(commit_status)) {
			if ("2".equals(flow_status)) {
				outsite_flow = "3";// 市分管领导确认
				queryMap.put("manage_level", "0");// 分管领导
				queryMap.put("area_level", "0");// 市公司
				pushUsers = outSiteInterfaceDao.getUsersByLevel(queryMap);
			} else if ("4".equals(flow_status)) {
				outsite_flow = "5";// 市主要管理和技术人员确认
				queryMap.put("manage_level", "1");// 主要管理和技术人员确认
				queryMap.put("area_level", "0");// 市公司
				pushUsers = outSiteInterfaceDao.getUsersByLevel(queryMap);
			} else {
				outsite_flow = "0";// 确认完成
			}

			// 如果有上级确认需要推送消息给上级
			if (null != pushUsers && pushUsers.size() > 0) {

				queryMap.put("staff_id", userId);
				Map<String, Object> userInfo = outSiteInterfaceDao.getUserInfo(queryMap);
				
				String userName = "";
				if(null != userInfo && null != userInfo.get("STAFF_NAME")){
					userName = String.valueOf(userInfo.get("STAFF_NAME"));
				}
				String title = "外力点防护方案";
				String context = "外力点名称：" + outSiteInfo.get("SITE_NAME").toString() + " 意见: " + commit_opnion;
				String sendMsg = "标题：" + title + " 内容：" + context + " 确认人: " + userName;
				
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < pushUsers.size(); i++) {
					String staff_no = pushUsers.get(i).get("STAFF_ID").toString();
					sb.append(staff_no + ",");
					
					SendMessageUtil.sendMessageInfo(String.valueOf(pushUsers.get(i).get("TEL")), sendMsg, area_id);//短信发送
				}
				sb.deleteCharAt(sb.length() - 1);

				JSONObject message = new JSONObject();
				message.put("entity_id", site_id);
				message.put("type", "0");//外力点推送
				message.put("content",  context);
				Map<String, Object> messageMap = new HashMap<String, Object>();
				messageMap.put("PUSH_STAFF", sb.toString());
				messageMap.put("PUSH_TITLE", title);
				messageMap.put("PUSH_CONTENT", message.toString());
				messageMap.put("PUSH_RESULT", "0");//待推送
				messageMap.put("PUSH_TYPE", "1");//外力点推送
				paramService.insertPushMessage(messageMap);
			}
		} else {// 驳回,返回班组长审核
			outsite_flow = "1";

			// 推送给班组长或段长
			queryMap.put("staff_id", outSiteInfo.get("FIBER_EPONSIBLE_BY"));
			queryMap.put("manage_level", "2");
			pushUsers = outSiteInterfaceDao.getUsersByLevel(queryMap);
			
			String title = "外力点驳回";
			String context =  "外力点名称：" + outSiteInfo.get("SITE_NAME").toString() + " 意见: " + commit_opnion;
			
			if (null != pushUsers && pushUsers.size() > 0) {

				queryMap.put("staff_id", userId);
				Map<String, Object> userInfo = outSiteInterfaceDao.getUserInfo(queryMap);
				String userName = "";
				if(null != userInfo && null != userInfo.get("STAFF_NAME")){
					userName = String.valueOf(userInfo.get("STAFF_NAME"));
				}
				String sendMsg = "标题：" + title + " 内容：" + context + " 确认人: " + userName;
				
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < pushUsers.size(); i++) {
					String staff_no = pushUsers.get(i).get("STAFF_ID").toString();
					sb.append(staff_no + ",");
					
					SendMessageUtil.sendMessageInfo(String.valueOf(pushUsers.get(i).get("TEL")), sendMsg, area_id);//短信发送
				}
				sb.deleteCharAt(sb.length() - 1);

				
				JSONObject message = new JSONObject();
				message.put("entity_id", site_id);
				message.put("type", "0");//外力点推送
				message.put("content",  context);
				Map<String, Object> messageMap = new HashMap<String, Object>();
				messageMap.put("PUSH_STAFF", sb.toString());
				messageMap.put("PUSH_TITLE", title);
				messageMap.put("PUSH_CONTENT", message.toString());
				messageMap.put("PUSH_RESULT", "0");//待推送
				messageMap.put("PUSH_TYPE", "1");//外力点推送
				paramService.insertPushMessage(messageMap);
			}
		}

		String is_scence = "0";
		Double distance = MapDistance.getDistance(latitude, longitude, y, x);
		if (500 > distance) {
			is_scence = "1";
		}

		// 外力点流程增加审核步骤
		Map<String, Object> outsiteFlow = new HashMap<String, Object>();
		outsiteFlow.put("OUT_SITE_ID", site_id);
		outsiteFlow.put("REVIEW_STAFF", userId);
		outsiteFlow.put("OPINON", commit_opnion);
		outsiteFlow.put("IS_SCENCE", is_scence);
		outsiteFlow.put("REVIEW_STATUS", commit_status);
		outsiteFlow.put("FLOW_STATUS", flow_status);
		outsiteFlow.put("COMMIT_TIME", DateUtil.getDateAndTime());
		outSiteInterfaceDao.addOutsiteFlow(outsiteFlow);

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("out_site_id", site_id);
		p.put("flow_status", outsite_flow);
		mainOutSiteDao.mainOutSiteUpdate(p);

		JSONObject res = new JSONObject();
		res.put("result", "000");

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getElebarCoordinate(String para) {

		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
//		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("query_date", DateUtil.getDate());
		String plan_id = outSiteInterfaceDao.getTodayOustSite(p);

		JSONObject res = new JSONObject();
		if (plan_id == null) {
			res.put("result", "000");
			res.put("plan_id", "");
			return JSONObject.fromObject(res).toString();
		}

		List<Map<String, Object>> elebarList = outSiteInterfaceDao.getElebar(plan_id);
		List<Map<String, Object>> elebarList2 = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < elebarList.size(); i++) {
			Map<String, Object> map = elebarList.get(i);
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("longitude", map.get("X"));
			map2.put("latitude", map.get("Y"));
			elebarList2.add(map2);
		}
		res.put("result", "000");
		res.put("plan_id", plan_id);
		res.put("coordinateList", elebarList2);

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getNurseTasks(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//			return Result.returnCode("002");
//		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		List<Map<String, Object>> tasks = outSiteInterfaceDao.getNurseTasks(p);
		List<Map<String, Object>> tasks2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < tasks.size(); i++) {
			Map<String, Object> map = tasks.get(i);
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("taskName", map.get("TASK_NAME"));
			map2.put("taskDate", map.get("TASK_DATE"));
			map2.put("plan_id", map.get("PLAN_ID"));
			tasks2.add(map2);
		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("tasks", tasks2);

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String uploadRemark(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String remark = jsonObject.getString("remark");
		String plan_id = jsonObject.getString("plan_id");

		Map<String, Object> p = new HashMap<String, Object>();

		String outsite_id = outSiteInterfaceDao.getosIdByPlanid(plan_id);

		p.put("upload_user", userId);
		p.put("remark", remark);
		p.put("out_site_id", outsite_id);
		p.put("upload_time", DateUtil.getDateAndTime());

		String guardInfoId = outSiteInterfaceDao.getGuardInfoId(); //获取主键序列号
		p.put("guardInfoId", guardInfoId);

		outSiteInterfaceDao.insertGuardInfo(p);

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("site_id", guardInfoId);

		return JSONObject.fromObject(res).toString();
	}

	private String getUserInfo(String staff_id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staff_id", staff_id);
		Map<String, Object> userInfo = outSiteInterfaceDao.getUserInfo(param);
		String flow_status = "10";
		if (null != userInfo) {
			if (null != userInfo.get("MANAGE_LEVEL") && null != userInfo.get("AREA_LEVEL")) {
				String manageLevel = userInfo.get("MANAGE_LEVEL").toString();
				String areaLevel = userInfo.get("AREA_LEVEL").toString();
				if ("0".equals(manageLevel) && "0".equals(areaLevel)) {// 市级别分管领导确认
					flow_status = "3";
				} else if ("1".equals(manageLevel) && "0".equals(areaLevel)) {// 市级别主要管理和技术人员确认
					flow_status = "5";
				} else if ("1".equals(manageLevel) && "1".equals(areaLevel)) {// 分公司主要管理和技术人员确认
					flow_status = "4";
				} else if ("0".equals(manageLevel) && "1".equals(areaLevel)) {// 分公司分管领导确认
					flow_status = "2";
				} else {// 班组长或段长审核
					flow_status = "1";
				}
			}
		}
		return flow_status;
	}

	/**
	 * 将null转换成空字符串
	 * 
	 * @param object
	 * @return
	 */
	private String fiterNullStr(Object object) {
		if (null != object) {
			return object.toString();
		}

		return "";
	}

	@Override
	public String getBasicOuteSiteInfo(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		double longitude = jsonObject.getDouble("longitude");
		double latitude = jsonObject.getDouble("latitude");

		Map<String, Object> staffInfo = staffDao.getStaff(userId);
		String areaId = staffInfo.get("AREA_ID").toString();

		String flow_status = getUserInfo(userId);// 根据用户获取该用户可以查询对应流程的外力点

		Map<String, Object> outsiteMap = new HashMap<String, Object>();
		outsiteMap.put("areaId", areaId);
		outsiteMap.put("flow_status", flow_status);
		if ("1".equals(flow_status) || "2".equals(flow_status) || "4".equals(flow_status)) {// 班组长和县公司领导只能审核自己分公司的外力点
			outsiteMap.put("staff_id", userId);
		}
		outsiteMap.put("outsite_id", jsonObject.getString("site_id"));
		List<Map<String, Object>> outSitesList = outSiteInterfaceDao.getOutSitesByFlow(outsiteMap);
		
		JSONObject map2 = new JSONObject();
		if(outSitesList.size() <=0 || null == outSitesList.get(0)){
			map2.put("flow_result", "2");//外力点已经被审核
			map2.put("result", "000");
			return map2.toString();
		}
		
		Map<String, Object> map = outSitesList.get(0);
		Double distance = 0.0;
		if ("1".equals(flow_status)) {// 班组长需要到现场审核,其他确认人员不限制
			double x = Double.parseDouble(map.get("X").toString());
			double y = Double.parseDouble(map.get("Y").toString());
			distance = MapDistance.getDistance(latitude, longitude, y, x);
		}
		
		// 班组长需要在300米范围内审批
		if (distance <= 300) {
			map2.put("site_id", map.get("OUT_SITE_ID").toString());
			map2.put("site_name", map.get("SITE_NAME").toString());
			map2.put("x", map.get("X").toString());
			map2.put("y", map.get("Y").toString());
			map2.put("distance", distance);
			map2.put("info_source", map.get("INFO_SOURCE").toString());
			map2.put("affected_fiber", fiterNullStr(map.get("AFFECTED_FIBER")));
			map2.put("relay_part", fiterNullStr(map.get("RELAY_PART")));
			map2.put("fiber_level", fiterNullStr(map.get("FIBER_LEVEL")));
			map2.put("line_part", fiterNullStr(map.get("LINE_PART")));
			map2.put("landmarkno", fiterNullStr(map.get("LANDMARKNO")));
			map2.put("con_company", fiterNullStr(map.get("CON_COMPANY")));
			map2.put("con_address", fiterNullStr(map.get("CON_ADDRESS")));
			map2.put("con_content", fiterNullStr(map.get("CON_CONTENT")));
			map2.put("is_agreement", fiterNullStr(map.get("IS_AGREEMENT")));
			map2.put("site_danger_level", fiterNullStr(map.get("SITE_DANGER_LEVEL")));
			map2.put("con_startdate", fiterNullStr(map.get("CON_STARTDATE")));
			map2.put("pre_enddate", fiterNullStr(map.get("PRE_ENDDATE")));
			map2.put("con_reponsible_by", fiterNullStr(map.get("CON_REPONSIBLE_BY")));
			map2.put("con_reponsible_by_tel", fiterNullStr(map.get("CON_REPONSIBLE_BY_TEL")));
			map2.put("guardian", fiterNullStr(map.get("GUARDIAN")));
			map2.put("guardian_tel", fiterNullStr(map.get("GUARDIAN_TEL")));
			map2.put("scene_measure", fiterNullStr(map.get("SCENE_MEASURE")));
			map2.put("fiber_eponsible_by", fiterNullStr(map.get("FIBER_EPONSIBLE_BY")));
			map2.put("user_id", fiterNullStr(map.get("USER_ID")));
			map2.put("fiber_uid", fiterNullStr(map.get("FIBER_UID")));
			map2.put("stay_time_part", fiterNullStr(map.get("STAY_TIME_PART")));
			map2.put("is_machaine", fiterNullStr(map.get("IS_MACHAINE")));
			map2.put("machaine_name", fiterNullStr(map.get("MACHAINE_NAME")));
			map2.put("residual_cable", fiterNullStr(map.get("RESIDUAL_CABLE")));
			map2.put("effect_service", fiterNullStr(map.get("EFFECT_SERVICE")));
			map2.put("is_plan", fiterNullStr(map.get("IS_PLAN")));
			map2.put("flow_status", fiterNullStr(map.get("FLOW_STATUS")));

			// 查询机械手信息
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("out_site_id", map.get("OUT_SITE_ID").toString());
			List<Map<String, Object>> jxsList = mainOutSiteDao.getCzs(queryMap);
			if (null != jxsList && jxsList.size() > 0) {
				map2.put("operator_name", fiterNullStr(jxsList.get(0).get("OPERATOR_NAME")));
				map2.put("car_no", fiterNullStr(jxsList.get(0).get("CAR_NO")));
				map2.put("car_type", fiterNullStr(jxsList.get(0).get("CAR_TYPE")));
				map2.put("mobile", fiterNullStr(jxsList.get(0).get("MOBILE")));
			}

			// 查询维护方案
			List<Map<String, Object>> schemes = outSiteInterfaceDao.getOutSchemeBySiteId(queryMap);
			if (null != schemes && schemes.size() > 0) {
				JSONObject schemeMap = new JSONObject();
				schemeMap.put("content", fiterNullStr(schemes.get(0).get("MS_CONTENT")));
				schemeMap.put("user_id", fiterNullStr(schemes.get(0).get("USER_ID")));
				schemeMap.put("scheme_name", fiterNullStr(schemes.get(0).get("SCHEME_NAME")));
				schemeMap.put("user_name", fiterNullStr(schemes.get(0).get("STAFF_NAME")));
				map2.put("schemeinfo", schemeMap);
			}
			
			map2.put("flow_result", "1");//正常审批
			map2.put("result", "000");
		}else{
			map2.put("flow_result", "3");//不在外力点附近
			map2.put("result", "000");
		}
		return map2.toString();
	}
	
	public String saveOsProtectCoordinateByAuto2(String jsonStr) {
		JSONObject jb = JSONObject.fromObject(jsonStr);
		String sn = jb.getString("sn");
		String userId = jb.getString("userId");
		if (OnlineUserListener.isOtherLogin(userId, sn)) {//账号在其他地方登录
			return Result.returnCode("000");//不是当前用户登录数据直接返回,这样手机端数据直接清空
		}else{
			if(!isLastLogin(userId,sn)){
				return Result.returnCode("000");//内存中没有数据，找最后一次登录信息,和最后一次登录信息一致数据才上传
			}
		}
		guardTrackExecutor.saveGuardTrack(jsonStr);
		return Result.returnCode("000");
	}

	public String saveOsProtectCoordinateByAuto(String jsonStr) {
		JSONObject jb=JSONObject.fromObject(jsonStr);
//		paramService.saveLogInfo("OutSiteInterfaceServiceImpl", "saveOsProtectCoordinate(String jsonStr)", "DEBUG", jsonStr);// 日志记录
		String sn = jb.getString("sn");
		String userId = jb.getString("userId");
		if (!isLastLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}
		
		String upload_time=jb.getString("upload_time");
		String parent_city = jb.getString("parent_city");
		JSONArray job = JSONArray.fromObject(jb.get("planList"));
		Map<String, Object> resmap = new HashMap<String, Object>();
		try {
			for (Object object : job) {
				JSONObject jsonObject = JSONObject.fromObject(object);
				String plan_id = jsonObject.getString("plan_id");
				String is_gps = jsonObject.getString("is_gps");
				String gps_switch = jsonObject.getString("gps_switch");
				double x = jsonObject.getDouble("x");
				double y = jsonObject.getDouble("y");
				String plan_time = jsonObject.getString("plan_time");
				String is_guard = jsonObject.getString("is_guard");
				String track_time = jsonObject.getString("track_time");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("plan_id", plan_id);
				map.put("x", x);
				map.put("y", y);
				map.put("is_gps", is_gps);
				map.put("gps_switch", gps_switch);
				map.put("plan_time", plan_time);
				map.put("parent_city", parent_city);
				map.put("creation_time", DateUtil.getDateAndTime());
				map.put("created_by", userId);
				map.put("sn", sn);
				map.put("is_guard", is_guard);
				map.put("track_time", track_time);
				// 查询上次是否在电子围栏内 group_flag分组标志
//				map.put("today", DateUtil.getDate());
//				Map<String, Object> res = outSiteInterfaceDao.isInGuardLast(map);
//				int group_flag = -1;
//				if (null == res) {
//					group_flag = 0;
//				} else if (is_guard.equals(res.get("IS_GUARD").toString())) {
//					group_flag = Integer.parseInt(res.get("GROUP_FLAG").toString());
//				} else {
//					group_flag = Integer.parseInt(res.get("GROUP_FLAG").toString()) + 1;
//				}
//				map.put("group_flag", group_flag);

				mainOutSiteDao.saveOsProtectCoordinate(map);
			}
			resmap.put("result", "000");
		} catch (NumberFormatException e) {
			resmap.put("result", "001");
		}finally{
			return JSONObject.fromObject(resmap).toString();
		}
	}
	
	/**
	 * 最后一次登录的手机号
	 * @param userId
	 * @param sn
	 * @return
	 */
	private boolean isLastLogin(String userId,String sn){
		if(null != OnlineUserListener.getOnlineUser(userId)){
			return true;
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("USER_ID", userId);
		Map<String,Object> login = paramService.getBaseLoginInfo(param);
		if(null != login && sn.equals(String.valueOf(login.get("SN")))){
			return true;
		}
		return false;
	}

	@Override
	public String getOutSitesAndPhoto(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			String user_id = jsonObject.getString("user_id");
			String sn = jsonObject.getString("sn");
			if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			 return Result.returnCode("002");
			}
			int page = jsonObject.getInt("page");
			String area_id = jsonObject.getString("area_id");
			String out_site_name = null;
			String org_id = null;
			Map<String, Object> paramap=new HashMap<String, Object>();
			//接收组织关系id和外力点名称
			if(jsonObject.has("out_site_name")){
				out_site_name = jsonObject.getString("out_site_name");
				paramap.put("out_site_name", out_site_name);
			}
			if(jsonObject.has("org_id")){
				org_id = jsonObject.getString("org_id");
				paramap.put("org_id", org_id);
			}
			paramap.put("area_id", area_id);
			//根据区域查询有多少页的外力点
			int pageCount = outSiteInterfaceDao.selOutSiteCount(paramap)%12==0?
					outSiteInterfaceDao.selOutSiteCount(paramap)/12:outSiteInterfaceDao.selOutSiteCount(paramap)/12+1;
			int outSiteCount = outSiteInterfaceDao.selOutSiteCount(paramap);
			paramap.put("page", page);
			List<Map<String, Object>> outSites1 = outSiteInterfaceDao.getOutSitesAndPhoto(paramap);		
			List<Map<String, Object>> outSites = new ArrayList<Map<String,Object>>();
			if(outSites1!=null&&outSites1.size()>0){
				for (Map<String, Object> map : outSites1) {
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("SITE_NAME", map.get("SITE_NAME"));
					map2.put("OUT_SITE_ID", map.get("OUT_SITE_ID").toString());
					map2.put("RN",map.get("RN").toString());
					if(map.get("MICRO_PHOTO") != null && !"".equals(map.get("MICRO_PHOTO").toString())){
						map2.put("MICRO_PHOTO",map.get("MICRO_PHOTO"));
					}else{
						map2.put("MICRO_PHOTO","");
					}
					map2.put("Y",map.get("Y").toString());
					map2.put("X",map.get("X").toString());
					map2.put("IS_FILE",map.get("IS_FILE").toString());
					outSites.add(map2);
				}
			}
			
			//获取该区域下的组织关系
			List<Map<String, Object>> orgs = outSiteInterfaceDao.selOrgByAreaIDs(area_id);
			resultMap.put("pageCount", pageCount);
			resultMap.put("outSiteCount", outSiteCount);
			resultMap.put("orgs", orgs);
			resultMap.put("outSites", outSites);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@Override
	public String getOutSitePhotos(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			String user_id = jsonObject.getString("user_id");
			String sn = jsonObject.getString("sn");
			if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			 return Result.returnCode("002");
			}
			String out_site_id = jsonObject.getString("out_site_id");
			Map<String, Object> paramap=new HashMap<String, Object>();
			paramap.put("out_site_id", out_site_id);
			List<Map<String, Object>> outSitePhotos = outSiteInterfaceDao.getOutSitePhotos(paramap);
			resultMap.put("outSitePhotos", outSitePhotos);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@Override
	public String remotePhotoComment(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			String user_id = jsonObject.getString("user_id");
			String sn = jsonObject.getString("sn");
			if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			 return Result.returnCode("002");
			}
			Map<String, Object> paramap=new HashMap<String, Object>();
			String is_praise = jsonObject.getString("is_praise");
			String out_site_id = jsonObject.getString("out_site_id");
			String photo_id = jsonObject.getString("photo_id");

            if(Integer.parseInt(is_praise)==1){
            	String questionType = jsonObject.getString("questionType");
            	String other_trouble = jsonObject.getString("other_trouble");
            	paramap.put("questionType", questionType);
            	paramap.put("other_trouble", other_trouble);
            	
            	//派发整治单
                String sendRenovation = jsonObject.getString("sendRenovation");
                if(Integer.parseInt(sendRenovation) == 1){
                	//生成整治单并且生成发起流程状态的流程数据
                	Map<String,Object> fixOrderMap = new HashMap<String, Object>();//创建Map集合方便插值生成整治单
                	String fixorder_id = fixOrderDao.getFixOrderNextVal();//获取整治单的id,顺便把这个id插入到结点当中
                	Map<String,Object> photo = outSiteInterfaceDao.selPhotolByID(photo_id);//把当前照片的拍摄人作为整治单的处理者,方便生成流程图片
                	fixOrderMap.put("fixorder_id",fixorder_id);//整治单id
                	fixOrderMap.put("questionType", questionType);//两个拼接起来作为整治单的内容
                	fixOrderMap.put("other_trouble", other_trouble);
                	fixOrderMap.put("creator", user_id);//创建人
                	fixOrderMap.put("status",1);//整治单状态默认为1，已派发,待处理，下面会生成结点实现自动派发
                	fixOrderMap.put("dealing_with_people", photo.get("STAFF_ID"));//处理人
                	fixOrderMap.put("photo_id", photo_id);
                	fixOrderMap.put("spot_id",out_site_id);
                	fixOrderMap.put("area_id", photo.get("AREA_ID"));
                	fixOrderDao.intoFixOrder(fixOrderMap);//插入到整治单表
                	
                	StringBuffer sb = new StringBuffer(questionType);
                	sb.append(" ").append(other_trouble);
                	String repair_remark =sb.toString();//处理意见下面传递处理意见用这个值传递进去
                	fixOrderMap.put("repair_remark",repair_remark);
                	
                	//在结点中生成并且获取结点方便生成图片数据， 图片表照片类型为14整治单类型，设施ID为结点ID，并且结点生成两条数据
                	String node_id = fixOrderDao.getSingelNodeListNextVal();//获取流程ID,这是作为发起流程的状态0
                	fixOrderMap.put("node_id",node_id);
                	fixOrderMap.put("handle_person",user_id);
                	fixOrderMap.put("status",1);//流程状态置为已派发
                	fixOrderDao.intoSingelNodeList(fixOrderMap);//生成已派发结点，状态处理人为发起者意见为隐患描述
                	
                	//生成已派发时候的照片,设施点id变更为结点id,类型变为14,整治单类型
                	Map<String,Object> photoMap = new HashMap<String, Object>();//创建结点id所对应的图片集合
        			int photoId = taskInterfaceDao.getPicId();// 获取photo主键
        			photoMap.put("pic_id", photoId);
        			photoMap.put("staff_id", user_id);
        			photoMap.put("site_id", node_id);
        			photoMap.put("upload_time", DateUtil.getDateAndTime());
        			photoMap.put("photo_type", 14);
        			photoMap.put("pic_path",photo.get("PHOTO_PATH"));
					photoMap.put("micro_pic_path",photo.get("MICRO_PHOTO"));
                	axxInterfaceDao.insertPic(photoMap);//插入第一次流程发起时候的图片为当前图片下面还得再次插入一张待处理时候的图片
                	
//        			//查询该地市下所有管理员的人员,插入数据到人员整治单关联表中去
//        			List<Map<String,Object>> admins = fixOrderDao.selAreaAdmin(photo.get("AREA_ID").toString());
//        			if(admins.size()>0){
//        				for(Map<String,Object> admin : admins){
//        					admin.put("FIXORDER_ID",fixorder_id);
//        					fixOrderDao.intoFixOrderStaff(admin);
//        				}
//        			}
        			//消息推送,暂时不管
                }
            }
            paramap.put("is_praise", is_praise);
            paramap.put("out_site_id", out_site_id);
            paramap.put("photo_id", photo_id);
            paramap.put("user_id", user_id);
            outSiteInterfaceDao.intoPhotoComment(paramap);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@Override
	public String getHistoryPhotoComment(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			String user_id = jsonObject.getString("user_id");
			String sn = jsonObject.getString("sn");
			if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			 return Result.returnCode("002");
			}
			String out_site_id = jsonObject.getString("out_site_id");
			String photo_id = jsonObject.getString("photo_id");
			Map<String, Object> paramap=new HashMap<String, Object>();
			paramap.put("out_site_id", out_site_id);
			paramap.put("photo_id", photo_id);
			List<Map<String, Object>> outSitePhotoComments = outSiteInterfaceDao.getHistoryPhotoComments(paramap);
			resultMap.put("outSitePhotoComments", outSitePhotoComments);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}
	
	@Override
	public String getRepairOrder(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			String user_id = jsonObject.getString("user_id");
			String sn = jsonObject.getString("sn");
			if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			 return Result.returnCode("002");
			}
			String area_id = jsonObject.getString("area_id");
			Map<String, Object> paramap=new HashMap<String, Object>();
			paramap.put("area_id", area_id);
			paramap.put("dealing_with_people", user_id);
			String node_id = fixOrderDao.getSingelNodeListNextVal();//获取流程ID,作为处理之后照片,把照片的设施点id给变为流程id
			List<Map<String, Object>> repairOrders = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> fixOrders = fixOrderDao.selFixOrderByPerson(paramap);
			if(fixOrders.size()>0){
				for(Map<String, Object> fixOrder : fixOrders){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("process_id",node_id);
					map.put("fixorder_id",fixOrder.get("FIXORDER_ID"));
					map.put("outsite_id",fixOrder.get("OUTSITE_ID"));
					map.put("photo_id",fixOrder.get("PHOTO_ID"));
					map.put("deal_person",fixOrder.get("DEAL_PERSON"));
					map.put("create_time",fixOrder.get("CREATE_TIME"));
					map.put("repairOrderName",fixOrder.get("REPAIRORDERNAME"));
					map.put("site_name",fixOrder.get("SITE_NAME"));
					map.put("creator",fixOrder.get("CREATE_NAME"));
					map.put("photo_path",fixOrder.get("PHOTO_PATH"));
					map.put("micro_photo",fixOrder.get("MICRO_PHOTO"));
					repairOrders.add(map);
				}
			}
			resultMap.put("repairOrders", repairOrders);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}
	
	@Override
	public String dealRepairOrder(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			String user_id = jsonObject.getString("user_id");
			String sn = jsonObject.getString("sn");
			if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			 return Result.returnCode("002");
			}
			String node_id = jsonObject.getString("process_id");//结点id
			String repair_remark = jsonObject.getString("deal_result");
			String fixorder_id = jsonObject.getString("fixorder_id");
			Map<String, Object> paramap=new HashMap<String, Object>();
			//先插入流程结点根据结点id来插入
            paramap.put("node_id", node_id);
            paramap.put("repair_remark", repair_remark);
            paramap.put("handle_person", user_id);
            paramap.put("fixorder_id", fixorder_id);
            paramap.put("status", 2);
            fixOrderDao.intoSingelNodeList(paramap);
            
            //将整治单状态更改为已处理,待审核
            fixOrderDao.upFixOrderStatus(paramap);
            
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}
	
}
