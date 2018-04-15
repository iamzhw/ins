package icom.axx.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.linePatrol.dao.AutoTrackDao;
import com.linePatrol.dao.DangerOrderDao;
import com.linePatrol.dao.LineInfoDao;
import com.linePatrol.dao.SiteDao;
import com.linePatrol.dao.StepPartDao;
import com.linePatrol.dao.gldManageDao;
import com.linePatrol.dao.relayDao;
import com.linePatrol.util.DateUtil;
import com.system.dao.AreaDao;
import com.system.dao.GeneralDao;
import com.system.dao.ParamDao;
import com.system.dao.StaffDao;
import com.system.sys.OnlineUserListener;
import com.system.tool.GlobalValue;
import com.system.tool.ImageTool;
import com.util.MapDistance;
import com.util.StringUtil;
import com.util.sendMessage.SendMessageUtil;

import icom.axx.dao.AxxInterfaceDao;
import icom.axx.dao.CollectInfoOfRelayDao;
import icom.axx.dao.OutSiteInterfaceDao;
import icom.axx.service.AxxInterfaceService;
import icom.system.dao.TaskInterfaceDao;
import icom.util.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@SuppressWarnings("all")
public class AxxInterfaceServiceImpl implements AxxInterfaceService {

	private static String RETURN_RESULT_SUCCESS = "000";
	
	private static String RETURN_RESULT_FAILURE = "001";
	
	@Resource
	private StaffDao staffDao;

	@Resource
	private DangerOrderDao dangerOrderDao;

	@Resource
	private AxxInterfaceDao axxInterfaceDao;

	@Resource
	private TaskInterfaceDao taskInterfaceDao;

	@Resource
	private SiteDao siteDao;

	@Resource
	private gldManageDao gldManageDao;

	@Resource
	private relayDao relayDao;

	@Resource
	private GeneralDao generalDao;

	@Resource
	private LineInfoDao lineInfoDao;

	@Resource
	private OutSiteInterfaceDao outSiteInterfaceDao;

	@Resource
	private ParamDao paramDao;
	
	@Resource
	private AreaDao areaDao;
	
	@Resource
	private StepPartDao StepPartDao;
	
	@Resource
	private AutoTrackDao autoTrackDao;
	
	@Autowired
	private CollectInfoOfRelayDao collectInfoOfRelayDao;
	
	
	public String uploadWarningMsgs(String param) {
		//获取参数
		JSONObject jsonObject = JSONObject.fromObject(param);
		String user_id = jsonObject.getString("user_id");
		String site_id = jsonObject.getString("site_id");
		String site_longitude = jsonObject.getString("site_longitude");
		String site_latitude = jsonObject.getString("site_latitude");
		String warn_time = jsonObject.getString("warn_time");
		String msg_detail = jsonObject.getString("msg_detail");
		String person_longitude = jsonObject.getString("person_longitude");
		String person_latitude = jsonObject.getString("person_latitude");
		//保存数据
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", user_id);
		paramMap.put("site_id", site_id);
		paramMap.put("site_longitude", site_longitude);
		paramMap.put("site_latitude", site_latitude);
		paramMap.put("warn_time", warn_time);
		paramMap.put("msg_detail", msg_detail);
		paramMap.put("person_longitude", person_longitude);
		paramMap.put("person_latitude", person_latitude);
		//结果json
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//axx_mobile_warn
			axxInterfaceDao.insertMobileWarnMsg(paramMap);
			result.put("result", RETURN_RESULT_SUCCESS);
			result.put("msg", "成功！");
		}catch(Exception e) {
			result.put("result", RETURN_RESULT_FAILURE);
			result.put("msg", "失败！");
			e.printStackTrace();
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@Override
	public String getTaskByUserId(String param) {
		JSONObject jsonObject = JSONObject.fromObject(param);
		String user_id = jsonObject.getString("user_id");
		//封装参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", user_id);
		//结果json
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> taskList = axxInterfaceDao.getTaskByUserId(paramMap);
			result.put("taskList", taskList);
			result.put("result", RETURN_RESULT_SUCCESS);
			result.put("msg", "成功！");
		}catch(Exception e) {
			result.put("result", RETURN_RESULT_FAILURE);
			result.put("msg", "失败！");
			e.printStackTrace();
		}
		return JSONObject.fromObject(result).toString();
		
	}
	public String getStandardRoute(String param) {
		JSONObject jsonObject = JSONObject.fromObject(param);
		String inspact_id = jsonObject.getString("user_id");
		String inspact_date = jsonObject.getString("inspact_date");
		//封装参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("inspact_id", inspact_id);
		paramMap.put("inspact_date", inspact_date);
		//封装json
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//查询巡线段id
			List<String> ids = autoTrackDao.getTheveryDayLineInfoIds(paramMap);
			//巡线点集合
			List<Map<String, Object>> siteList = new ArrayList<Map<String, Object>>();
			for (String line_id : ids) {
				//根据line_id获取巡线点
				paramMap.put("line_id", line_id);
				List<Map<String, Object>> slist = axxInterfaceDao.findSiteByLine(paramMap);
				
				Map<String, Object> res = new HashMap<String, Object>();
				res.put("line_id", slist);
				siteList.add(res);
			}
			
			result.put("siteList", siteList);
			result.put("result", RETURN_RESULT_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("result", RETURN_RESULT_FAILURE);
		}
		
		
		return JSONObject.fromObject(result).toString();
	}
	
	@Override
	public String getRepeaters(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		Map<String, Object> userInfo = staffDao.getStaff(userId);
		String areaId = userInfo.get("AREA_ID").toString();
		List<Map<String, Object>> relayList = axxInterfaceDao.getRelayByArea(areaId);

		List<Map<String, Object>> relayList2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < relayList.size(); i++) {
			Map<String, Object> map = relayList.get(i);
			Map<String, Object> map2 = new HashMap<String, Object>();
			// 手机端接口规范 小写
			map2.put("repeaterId", map.get("REPEATERID").toString());
			map2.put("repeaterName", map.get("REPEATERNAME").toString());
			relayList2.add(map2);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("body", JSONArray.fromObject(relayList2));
		map.put("result", "000");

		return JSONObject.fromObject(map).toString();
	}

	@Override
	public String saveInspInfo(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);

		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		String repeaterId = jsonObject.getString("repeaterId");
		String latitude = jsonObject.getString("latitude");
		String longitude = jsonObject.getString("longitude");
		String inspInfo = jsonObject.getString("inspInfo");
		String longitudegps =jsonObject.getString("longitudegps");// 经度
		String latitudegps =jsonObject.getString("latitudegps");// 经度

		Map<String, Object> staffInfo = staffDao.getStaff(userId);
		String area_id = staffInfo.get("AREA_ID").toString();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relay_id", repeaterId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("site_name", inspInfo);
		map.put("update_time", DateUtil.getDateAndTime());
		map.put("site_type", 2);// 2非关键点
		map.put("area_id", area_id);// 2非关键点
		map.put("longitudegps", longitudegps);// 经度
		map.put("latitudegps", latitudegps);// 纬度

		String site_id = axxInterfaceDao.getSiteId();
		map.put("site_id", site_id);

		axxInterfaceDao.saveInspInfo(map);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");
		res.put("site_id", site_id);

		return JSONObject.fromObject(res).toString();

	}
	
	@Transactional(rollbackFor = Exception.class)
	public String uploadPhoto(HttpServletRequest request) {

		JSONObject result = new JSONObject();
		Map<String, Object> photoMap = new HashMap<String, Object>();

		
		// if (!OnlineUserListener.isLogin(staffId, sn)) {//登录校验
		// return Result.returnCode("002");
		// }
		InputStream is = null;
		try {
			request.setCharacterEncoding("utf-8");// 解析参数
			
			String staffId = request.getHeader("staffId");// 巡检人ID
			String sn = request.getHeader("sn");
			String site_id = request.getHeader("site_id");// 巡线点ID
			String photo_type = request.getHeader("photo_type");
			
			System.out.println("【----------uploadPhoto------】"+"sn:"+sn+";staffId:"+staffId);
			System.out.println("【----------uploadPhoto------】"+"site_id:"+site_id+";photo_type:"+photo_type);
			
			photoMap.put("staff_id", staffId);
			photoMap.put("sn", sn);
			photoMap.put("site_id", site_id);
			photoMap.put("upload_time", DateUtil.getDateAndTime());
			photoMap.put("photo_type", photo_type);

			// 文件流
			byte[] photoByte = ImageTool.getByteFromStream(request);
			String photo = StringUtil.convertByteToHexString(photoByte);
			String currDate = StringUtil.getCurrDate();
			is = request.getInputStream();

			// 获取photo主键
			int photoId = taskInterfaceDao.getPicId();
			photoMap.put("pic_id", photoId);

			/* 拼装请求参数 */
			StringBuffer xmlStr = new StringBuffer();
			xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xmlStr.append("<root>");
			xmlStr.append("<type>photo</type>"); // 上传类型
			xmlStr.append("<project>ins</project>"); // 存放照片的目录
			xmlStr.append("<name>").append(photoId).append("</name>"); // 文件名称
			xmlStr.append("<date>").append(currDate).append("</date>"); // 文件日期
			xmlStr.append("<data>").append(photo).append("</data>"); // 文件数据
			xmlStr.append("</root>");
			Client client = new Client(new URL(GlobalValue.FILE_SERVER));
			Object[] resultXmlObj = client.invoke("saveFile", new Object[] { xmlStr.toString() });

			// 解析返回值
			if (resultXmlObj != null) {
				Document document = DocumentHelper.parseText((String) resultXmlObj[0]);
				Element root = document.getRootElement();
				String r = root.elementText("result"); // 保存是否成功
				if (r.equalsIgnoreCase("success")) {
					/* 将图片保存的地址返回给调用方 */
					String photoPath = root.elementText("photoPath"); // 原尺寸照片地址
					String microPhotoPath = root.elementText("microPhotoPath"); // 缩微图照片地址
					// 对文件路径要做如下处理
					String fsi = GlobalValue.FILE_SERVER_INTERNET;
					int index = photoPath.indexOf("files");
					if (index != -1) {
						photoPath = fsi + photoPath.substring(index, photoPath.length());

					}
					index = microPhotoPath.indexOf("files");
					if (index != -1) {
						microPhotoPath = fsi + microPhotoPath.substring(index, microPhotoPath.length());
					}
					photoMap.put("pic_path", photoPath == null ? "" : photoPath);
					photoMap.put("micro_pic_path", microPhotoPath == null ? "" : microPhotoPath);
				}
				axxInterfaceDao.insertPic(photoMap);
			}

			// 没有异常，图片上传成功，返回图片ID
			result.put("result", "000");
			// result.put("photo_id", String.valueOf(photoId));

		} catch (Exception e) {

			// 打印堆栈信息
			e.printStackTrace();

			// 数据回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			result.put("result", "001");
			// result.put("photoId", "");

		}finally{
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return JSONObject.fromObject(result).toString();
	}

	public String getArrivalRate(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
		// return Result.returnCode("002");
		// }
		String query_time = jsonObject.getString("query_time");
		if (jsonObject.getString("personId") != null && !"".equals(jsonObject.getString("personId"))) {
			userId = jsonObject.getString("personId");
		}
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("query_time", query_time);

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查巡当天任务
			List<Map<String, Object>> taskList = axxInterfaceDao.getTodayTask(p);
			List<Map<String, Object>> resTaskList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < taskList.size(); i++) {
				Map<String, Object> task = taskList.get(i);
				String task_id = task.get("TASK_ID").toString();
				Map<String, Object> count = axxInterfaceDao.getArrivalRate(task_id);

				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("task_id", task.get("TASK_ID") == null ? "" : task.get("TASK_ID").toString());
				map2.put("task_name", task.get("TASK_NAME") == null ? "" : task.get("TASK_NAME").toString());
				map2.put("begin_date", task.get("START_DATE") == null ? "" : task.get("START_DATE").toString());
				map2.put("end_date", task.get("END_DATE") == null ? "" : task.get("END_DATE").toString());
				map2.put("actual_count", count.get("ACTUAL_COUNT") == null ? "" : count.get("ACTUAL_COUNT").toString());
				map2.put("total_count", count.get("TOTAL_COUNT") == null ? "" : count.get("TOTAL_COUNT").toString());
				map2.put("query_date", query_time);
				resTaskList.add(map2);

			}

			map.put("tasks", JSONArray.fromObject(resTaskList));
			map.put("result", "000");

		} catch (Exception e) {
			map.put("result", "001");
			e.printStackTrace();

		} finally {
			System.out.println(map.toString());
			return JSONObject.fromObject(map).toString();
		}
	}

	@Override
	public String uploadDanger(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);

		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
		// return Result.returnCode("002");
		// }
		String danger_name = jsonObject.getString("danger_name");
		String latitude = jsonObject.getString("latitude");
		String longitude = jsonObject.getString("longitude");
		String highRisk = jsonObject.getString("high_risk");

		// 用户信息
		Map<String, Object> userInfo = staffDao.findPersonalInfo(userId);

		Map<String, Object> map = new HashMap<String, Object>();
		if (jsonObject.containsKey("danger_type")) {
			String danger_type = jsonObject.getString("danger_type");
			map.put("danger_type", danger_type);
		}

		map.put("danger_question", danger_name);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		// map.put("update_time", DateUtil.getDateAndTime());
		map.put("created_by", userId);
		map.put("founder_uid", userId);
		map.put("high_risk", highRisk);
		map.put("found_time", DateUtil.getDateAndTime());
		map.put("parent_city", userInfo.get("AREA_ID"));

		// 保存隐患点
		String creation_time = DateUtil.getDateAndTime();
		map.put("creation_time", creation_time);

		String danger_id = dangerOrderDao.getDangerId();
		map.put("danger_id", danger_id);
		dangerOrderDao.saveDanger(map);

		// 保存隐患单
		dangerOrderDao.dangerOrderSave(map);

		if ("1".equals(highRisk)) {// 高危隐患推送消息给班组长
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("staff_id", userId);
			queryMap.put("manage_level", "2");
			List<Map<String, Object>> pushUsers = outSiteInterfaceDao.getUsersByLevel(queryMap);
			String title = "高危隐患上报";
			String sendMsg = "标题：" + title + " 内容：" + danger_name;

			if (null != pushUsers && pushUsers.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < pushUsers.size(); i++) {
					String staff_no = pushUsers.get(i).get("STAFF_ID").toString();
					sb.append(staff_no + ",");

					SendMessageUtil.sendMessageInfo(String.valueOf(pushUsers.get(i).get("TEL")), sendMsg, "3");// 短信发送
				}
				sb.deleteCharAt(sb.length() - 1);

				JSONObject message = new JSONObject();
				message.put("entity_id", danger_id);
				message.put("type", "1");// 隐患点推送
				message.put("content", danger_name);
				Map<String, Object> messageMap = new HashMap<String, Object>();
				messageMap.put("PUSH_STAFF", sb.toString());
				messageMap.put("PUSH_TITLE", title);
				messageMap.put("PUSH_CONTENT", message.toString());
				messageMap.put("PUSH_RESULT", "0");// 待推送
				messageMap.put("PUSH_TYPE", "2");// 高危隐患推送推送
				paramDao.insertPushMessage(messageMap);
			}
		}

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");
		res.put("site_id", danger_id);

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String dealDanger(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);

		String userId = jsonObject.getString("userId");
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
		// return Result.returnCode("002");
		// }
		String deal_result = jsonObject.getString("deal_result");
		String order_id = jsonObject.getString("order_id");
		String deal_time = jsonObject.getString("deal_time");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repair_remark", deal_result);
		// map.put("userId",userId );
		map.put("order_id", order_id);
		map.put("repair_time", deal_time);
		map.put("order_status", 2);// 2为已经处理 待审核

		// 更新隐患单
		dangerOrderDao.dangerOrderUpdate(map);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result", "000");
		res.put("site_id", order_id);

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getOrderList(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
		// return Result.returnCode("002");
		// }

		List<Map<String, Object>> dangerOrderList = dangerOrderDao.getDangerOrderToBeDeal(userId);

		List<Map<String, Object>> dangerOrderList2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dangerOrderList.size(); i++) {
			Map<String, Object> map = dangerOrderList.get(i);
			Map<String, Object> map2 = new HashMap<String, Object>();
			// 手机端接口规范 小写
			map2.put("order_id", map.get("ORDER_ID"));
			map2.put("danger_name", map.get("DANGER_QUESTION") == null ? "" : map.get("DANGER_QUESTION").toString());// map.get("danger_question")
			map2.put("distribute_remark",
					map.get("DISTRIBUTE_REMARK") == null ? "" : map.get("DISTRIBUTE_REMARK").toString());
			map2.put("limit_time", map.get("LIMIT_TIME") == null ? "" : map.get("LIMIT_TIME").toString());
			map2.put("danger_type", map.get("DANGER_TYPE"));
			map2.put("equip_id", map.get("EQUIP_ID"));
			map2.put("equip_type", map.get("EQUIP_TYPE"));
			dangerOrderList2.add(map2);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderlist", JSONArray.fromObject(dangerOrderList2));
		map.put("result", "000");

		return JSONObject.fromObject(map).toString();
	}

	@Override
	public String getCableInfos(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		String areaId = jsonObject.getString("area_id");

		// //查询本地光缆
		List<Map<String, Object>> localCableList = gldManageDao.getLocalCableList(areaId);

		// 查询中继段
		for (int i = 0; i < localCableList.size(); i++) {
			Map<String, Object> cable = localCableList.get(i);
			String cableId = cable.get("CABLE_ID").toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cableId", cableId);
			map.put("areaId", areaId);
			List<Map<String, Object>> relayList = relayDao.getRelaysByCableId(map);// 本地
			// relaylist中map的key变成小写
			for (int j = 0; j < relayList.size(); j++) {
				Map<String, Object> relay = relayList.get(j);
				String relay_id = relay.get("RELAY_ID").toString();
				relay.put("relay_id", relay_id);
				relay.put("realy_name", relay.get("RELAY_NAME"));
				relay.put("protect_grade", relay.get("PROTECT_GRADE"));

				// 查询巡线段
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("relay_id", relay_id);
				map2.put("areaId", areaId);
				List<Map<String, Object>> lineList = lineInfoDao.getLineByRelayId(map2);
				List<Map<String, Object>> lineList2 = new ArrayList<Map<String, Object>>();
				for (int k = 0; k < lineList.size(); k++) {
					Map<String, Object> line = lineList.get(k);
					Map<String, Object> sline = new HashMap<String, Object>();
					sline.put("line_id", line.get("LINE_ID"));
					sline.put("line_name", line.get("LINE_NAME"));
					sline.put("inspect_id", line.get("INSPECT_ID"));
					lineList2.add(sline);
				}
				relay.put("lineList", JSONArray.fromObject(lineList2).toString());
			}

			cable.put("cable_id", cable.get("CABLE_ID"));
			cable.put("cable_name", cable.get("CABLE_NAME"));
			cable.put("fiber_grade", cable.get("FIBER_GRADE"));

			cable.put("relayList", relayList);
		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("cableList", JSONArray.fromObject(localCableList));

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getStaffInfos(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);

		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		String area_id = jsonObject.getString("area_id");
		String role_id = jsonObject.getString("role_id");

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("area_id", area_id);
		map1.put("role_id", role_id);

		List<Map<String, Object>> axxStaffList = axxInterfaceDao.getStaffInfos(map1);
		for (int i = 0; i < axxStaffList.size(); i++) {
			Map<String, Object> map = axxStaffList.get(i);
			map.put("staff_id", map.get("STAFF_ID"));
			map.put("staff_name", map.get("STAFF_NAME"));
		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("staffList", JSONArray.fromObject(axxStaffList).toString());
		return JSONObject.fromObject(res).toString();
	}

	// 巡线时长
	@Override
	public String getLineTimes(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String area_id = jsonObject.getString("area_id");
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");

		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}
		String query_time = jsonObject.getString("query_time");
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("area_id", area_id);
		map1.put("user_id", userId);
		map1.put("query_time", query_time);

		List<Map<String, Object>> axx_xunxianlist = axxInterfaceDao.getLineTimes(map1);
		for (int i = 0; i < axx_xunxianlist.size(); i++) {
			Map<String, Object> map = axx_xunxianlist.get(i);
			map.put("query_time", map.get("QUERY_TIME"));
			map.put("effective_time", map.get("EFFECTIVE_TIME"));
			map.put("invalid_time", map.get("INVALID_TIME"));
		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("query_time", "000");
		res.put("effective_time", "000");
		res.put("invalid_time", "000");
		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getKeyArrivalRate(String para) {

		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		// String sn = jsonObject.getString("sn");

		// if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
		// return Result.returnCode("002");
		// }
		String query_time = jsonObject.getString("query_time");
		if (jsonObject.getString("personId") != null && !"".equals(jsonObject.getString("personId"))) {
			userId = jsonObject.getString("personId");
		}
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("query_time", query_time);

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查巡当天任务
			List<Map<String, Object>> taskList = axxInterfaceDao.getTodayTask(p);
			List<Map<String, Object>> resTaskList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < taskList.size(); i++) {
				Map<String, Object> task = taskList.get(i);
				String task_id = task.get("TASK_ID").toString();
				Map<String, Object> count = axxInterfaceDao.getKeyArrivalRate(task_id);
				if(count !=null ){
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("task_id", task.get("TASK_ID"));
				map2.put("task_name", task.get("TASK_NAME"));
				map2.put("query_date", query_time);
			
				map2.put("actual_count", count.get("ACTUAL_COUNT"));
				map2.put("total_count", count.get("TOTAL_COUNT"));
				resTaskList.add(map2);
				}
			}

			map.put("tasks", JSONArray.fromObject(resTaskList));
			map.put("result", "000");

		} catch (Exception e) {
			map.put("result", "001");
			e.printStackTrace();

		} finally {
			System.out.println(map.toString());
			return JSONObject.fromObject(map).toString();
		}

	}

	@Override
	public String uploadHoleCheck(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		String check_time = jsonObject.getString("check_time");
		String load_name = jsonObject.getString("load_name");
		String hole_no = jsonObject.getString("hole_no");
		String cable_id = jsonObject.getString("cable_id");
		String memo1 = jsonObject.getString("memo1");
		String memo2 = jsonObject.getString("memo2");
		String memo3 = jsonObject.getString("memo3");
		String memo4 = jsonObject.getString("memo4");
		String memo5 = jsonObject.getString("memo5");
		String memo6 = jsonObject.getString("memo6");
		String memo7 = jsonObject.getString("memo7");
		String memo8 = jsonObject.getString("memo8");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("sn", sn);
		map.put("check_time", check_time);
		map.put("load_name", load_name);
		map.put("hole_no", hole_no);
		map.put("cable_id", cable_id);
		map.put("memo1", memo1);
		map.put("memo2", memo2);
		map.put("memo3", memo3);
		map.put("memo4", memo4);
		map.put("memo5", memo5);
		map.put("memo6", memo6);
		map.put("memo7", memo7);
		map.put("memo8", memo8);

		String check_id = axxInterfaceDao.getNextval();

		map.put("check_id", check_id);

		axxInterfaceDao.saveHoleopencheck(map);

		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("site_id", check_id);

		return res.toString();
	}

	@Override
	public String getLineTasks(String para) {

		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
			return Result.returnCode("002");
		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("query_time", DateUtil.getDate());

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查巡当天任务

			List<Map<String, Object>> taskList = axxInterfaceDao.getTodayTask(p);

			List<Map<String, Object>> resTaskList = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> restaskOutSite = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> stepTaskList = new ArrayList<Map<String, Object>>();
			
			for (int i = 0; i < taskList.size(); i++) {
				Map<String, Object> task = taskList.get(i);
				String task_id = task.get("TASK_ID").toString();
				List<Map<String, Object>> line = axxInterfaceDao.getLineByTaskId(task_id); // 任务巡线集合
				List<Map<String, Object>> lineList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("task_id", task.get("TASK_ID"));
				map2.put("task_name", task.get("TASK_NAME"));
				map2.put("start_date", task.get("START_DATE"));
				map2.put("end_date", task.get("END_DATE"));

				for (int j = 0; j < line.size(); j++) {
					Map<String, Object> sline = line.get(j);
					Map<String, Object> map3 = new HashMap<String, Object>();
					map3.put("line_id", sline.get("LINE_ID"));
					map3.put("line_name", sline.get("LINE_NAME"));
					lineList.add(map3);
				}

				map2.put("lineList", lineList);
				resTaskList.add(map2);
			}

			for (int i = 0; i < taskList.size(); i++) {
				Map<String, Object> task = taskList.get(i);
				String task_id = task.get("TASK_ID").toString();
				List<Map<String, Object>> main_out_site = axxInterfaceDao.getOutSiteByTaskId(task_id); // 任务外力点集合
				List<Map<String, Object>> lineList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("task_id", task.get("TASK_ID"));
				map2.put("task_name", "外力点任务");
				map2.put("start_date", task.get("START_DATE"));
				map2.put("end_date", task.get("END_DATE"));

				for (int j = 0; j < main_out_site.size(); j++) {
					Map<String, Object> sline = main_out_site.get(j);
					Map<String, Object> map3 = new HashMap<String, Object>();
					map3.put("line_id", sline.get("OUT_SITE_ID"));
					map3.put("line_name", sline.get("SITE_NAME"));
					lineList.add(map3);
				}

				map2.put("lineList", lineList);
				restaskOutSite.add(map2);
			}

			//获取人员的任务
			List<Map<String, Object>> stepInspectTaskList = axxInterfaceDao.getStepTaskByInspectId(userId);
			for (int i = 0; i < stepInspectTaskList.size(); i++) {
				Map<String, Object> task = stepInspectTaskList.get(i);
				String task_id = task.get("TASK_ID").toString();
				List<Map<String, Object>> stepEquipAllot = axxInterfaceDao.getStepEquipAllotByTaskId(task_id);
				List<Map<String, Object>> allotList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("task_id", task.get("TASK_ID"));
				map2.put("task_name", task.get("TASK_NAME"));
				map2.put("start_date", task.get("START_DATE"));
				map2.put("end_date", task.get("END_DATE"));
				
				for (int j = 0; j < stepEquipAllot.size(); j++) {
					Map<String, Object> stepEquipAllotInfo = stepEquipAllot.get(j);
					Map<String, Object> map3 = new HashMap<String, Object>();
					map3.put("allot_id", stepEquipAllotInfo.get("ALLOT_ID"));
					map3.put("steppart_name", stepEquipAllotInfo.get("STEPPART_NAME"));
					
					allotList.add(map3);
				}
				map2.put("allotList", allotList);
				stepTaskList.add(map2);
			}
			
			map.put("tasks", JSONArray.fromObject(resTaskList));
			map.put("outSiteTasks", JSONArray.fromObject(restaskOutSite));
			map.put("stepTourTask", JSONArray.fromObject(stepTaskList));
			map.put("result", "000");

		} catch (Exception e) {
			map.put("result", "001");
			e.printStackTrace();

		} finally {

			return JSONObject.fromObject(map).toString();
		}

	}

	@Override
	public String getStepArrivalRate(String para) {
		
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("userId");
		 String sn = jsonObject.getString("sn");

		 if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
		 return Result.returnCode("002");
		 }
		 String query_time = jsonObject.getString("query_time");
		if (jsonObject.getString("personId") != null && !"".equals
				(jsonObject.getString("personId"))) {
			userId = jsonObject.getString("personId");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查巡当天任务
			List<Map<String, Object>> taskList = axxInterfaceDao.getStepTaskByInspectId(userId);
			List<Map<String, Object>> stepTaskList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < taskList.size(); i++) {
				Map<String, Object> task = taskList.get(i);
				String task_id = task.get("TASK_ID").toString();
				Map<String, Object> count = axxInterfaceDao.getStepAllotArrivalRate(task_id);

				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("task_id", task.get("TASK_ID"));
				map2.put("task_name", task.get("TASK_NAME"));
				map2.put("query_date", query_time);
				map2.put("actual_count", count.get("ACTUAL_COUNT"));
				map2.put("total_count", count.get("TOTAL_COUNT"));
				stepTaskList.add(map2);

			}

			map.put("tasks", JSONArray.fromObject(stepTaskList));
			map.put("result", "000");

		} catch (Exception e) {
			map.put("result", "001");
			e.printStackTrace();

		} finally {
			System.out.println(map.toString());
			return JSONObject.fromObject(map).toString();
		}
	}

	@Override
	public String getAreaInfos(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("user_id");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}
		List<Map<String, Object>> areaLists = areaDao.getAllArea();//查询区域列表13个地市
		
		List<Map<String, Object>> arealist = new ArrayList<Map<String, Object>>();//传值
		
		for (Map<String, Object> area : areaLists) {
			Map<String, Object> areas = new HashMap<String, Object>();//来接收返回id和name
			areas.put("area_id",area.get("AREA_ID"));
			areas.put("name",area.get("NAME"));
			arealist.add(areas);
		}
		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("arealist", JSONArray.fromObject(arealist));
		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getCRByAreaId(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String userId = jsonObject.getString("user_id");
		String sn = jsonObject.getString("sn");
		if (!OnlineUserListener.isLogin(userId, sn)) {// 登录校验
			return Result.returnCode("002");
		}

		String areaId = jsonObject.getString("area_id");
		
		List<Map<String, Object>> localCableList = StepPartDao.getGldByAreaId(areaId);//查询区域下的光缆
		
		List<Map<String, Object>> cableList = new ArrayList<Map<String, Object>>();//传值
		
		for (Map<String, Object> cableinfo : localCableList) {
			Map<String, Object> cable  = new HashMap<String, Object>();

			String cableId = cableinfo.get("CABLE_ID").toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cable_id", cableId);
			map.put("area_id", areaId);
			
			List<Map<String, Object>> relayLists = StepPartDao.getRelayByCableId(map);// 中继段
			List<Map<String, Object>> relayList = new ArrayList<Map<String, Object>>();// 传值
			
			for (Map<String, Object> relayinfo : relayLists) {
				Map<String, Object> relay = new HashMap<String, Object>();
				String relay_id = relayinfo.get("RELAY_ID").toString();
				map.put("relay_id", relay_id);
				String out_site_count = axxInterfaceDao.getOutSiteCount(map);
				relay.put("relay_id", relay_id);
				relay.put("realy_name", relayinfo.get("RELAY_NAME"));
				relay.put("protect_grade", relayinfo.get("PROTECT_GRADE"));
				relay.put("out_site_count", out_site_count);
				relayList.add(relay);
			}
			cable.put("cable_id", cableinfo.get("CABLE_ID"));
			cable.put("cable_name", cableinfo.get("CABLE_NAME"));
			cable.put("fiber_grade", cableinfo.get("FIBER_GRADE"));

			cable.put("relayList", relayList);
			cableList.add(cable);
		}
		
		JSONObject res = new JSONObject();
		res.put("result", "000");
		res.put("cableList", JSONArray.fromObject(cableList));

		return JSONObject.fromObject(res).toString();
	}

	@Override
	public String getLatelyStepPoint(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
		String area_id=jsonObject.getString("area_id");
		String cable_id= jsonObject.getString("cable_id");
		String relay_id= jsonObject.getString("relay_id");
		 String sn = jsonObject.getString("sn");
		 if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
		 return Result.returnCode("002");
		 }
		
		Map<String, Object> paramap=new HashMap<String, Object>();
		paramap.put("area_id", area_id);
		paramap.put("cable_id", cable_id);
		paramap.put("relay_id", relay_id);
		
		String latitude = jsonObject.getString("latitude");// 获取用户的经纬度
		String longitude = jsonObject.getString("longitude");
		//用这个BigDecimal这个类来做计算，double型会丢失精度
		BigDecimal lonB=BigDecimal.valueOf(Double.valueOf(longitude));
	    BigDecimal latB=BigDecimal.valueOf(Double.valueOf(latitude));
	    BigDecimal maxlonB=lonB.add(BigDecimal.valueOf(0.005));
	    BigDecimal maxlatB=latB.add(BigDecimal.valueOf(0.005));
	    BigDecimal minlonB=lonB.subtract(BigDecimal.valueOf(0.005));
	    BigDecimal minlatB=latB.subtract(BigDecimal.valueOf(0.005));
	    paramap.put("maxlonB", maxlonB.toString());
	    paramap.put("maxlatB", maxlatB.toString());
	    paramap.put("minlonB", minlonB.toString());
	    paramap.put("minlatB", minlatB.toString());
	    // 按1000平方米查询所有所在线路段落上的路由设施点,关联任务点的
//	    List<Map<String, Object>> allEquip = collectInfoOfRelayDao.selEquipPartByXYTaskId(paramap);
	    
	    //不关联任务点的
	    List<Map<String, Object>> allEquip = axxInterfaceDao.selEquipPartByXY(paramap); 
	    
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    try {   
		    if(allEquip.size()>0){
		    	List<Map<String, Object>> latelyPoint=new ArrayList<Map<String,Object>>();//最近的点
		    	Map<String, Object> minEquip = sort(allEquip, latitude, longitude);//排序得到离用户最近的路由点
		    	latelyPoint.add(minEquip);
		    	resultMap.put("point", latelyPoint);
		    }
			resultMap.put("result", "000");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
		    
	}
	
	/**
	 * 找到距离最近的点，在list中标示出该点，用于在移动端高亮显示
	 * 
	 * @param lists
	 */
	public Map<String, Object> sort(List<Map<String, Object>> lists,
			String latitude, String longitude) {
		Map<String, Object> min = lists.get(0);
		for (int i = 1; i < lists.size(); i++) {
			Double distance1 = MapDistance.getDistance(
					Double.valueOf(latitude), Double.valueOf(longitude),
					Double.valueOf(lists.get(i).get("LATITUDE").toString()),
					Double.valueOf(lists.get(i).get("LONGITUDE").toString()));
			Double distance2 = MapDistance.getDistance(
					Double.valueOf(latitude), Double.valueOf(longitude),
					Double.valueOf(min.get("LATITUDE").toString()),
					Double.valueOf(min.get("LONGITUDE").toString()));
			if (distance1 < distance2) {
				min = lists.get(i);
			}
		}
		return min;
	}
	
}
