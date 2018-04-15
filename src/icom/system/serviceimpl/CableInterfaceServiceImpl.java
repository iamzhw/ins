package icom.system.serviceimpl;

import icom.system.dao.CableInterfaceDao;
import icom.system.dao.CalculateNormalDao;
import icom.system.service.CableInterfaceService;
import icom.util.BaseServletTool;
import icom.util.Result;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.CableDao;
import com.cableInspection.dao.PointDao;
import com.cableInspection.service.ArrivalService;
import com.cableInspection.serviceimpl.PointServiceImpl;
import com.cableInspection.webservice.Wfworkitemflow;
import com.cableInspection.webservice.WfworkitemflowLocator;
import com.cableInspection.webservice.WfworkitemflowSoap11BindingStub;
import com.system.constant.AppType;
import com.system.constant.SysSet;
import com.system.dao.RoleDao;
import com.system.dao.StaffDao;
import com.system.service.BaseMethodService;
import com.system.service.ParamService;
import com.system.sys.OnlineUserListener;
import com.system.tool.GlobalValue;
import com.system.tool.ImageTool;
import com.util.DateUtil;
import com.util.MapDistance;
import com.util.StringUtil;
import com.util.Triangle;
import com.util.sendMessage.SendMessageUtil;
import com.zhxj.dao.ZhxjTaskDao;

import edu.emory.mathcs.backport.java.util.Collections;

@SuppressWarnings("all")
@Service
public class CableInterfaceServiceImpl implements CableInterfaceService {

	@Resource
	private CableInterfaceDao cableInterfaceDao;

	@Resource
	private BaseMethodService baseMethodService;

	@Resource
	private ParamService paramService;

	@Resource
	private PointDao pointDao;

	@Resource
	private StaffDao staffDao;
	
	@Resource
	private RoleDao roleDao;
	
	@Resource
	private CalculateNormalDao caldao;
	
	@Resource
	private PointServiceImpl pointServiceImpl;
	
	@Resource
	private CableDao cableDao;
	
	@Resource
	private ArrivalService arrivalService;
	
	@Resource
	private ZhxjTaskDao zhxjTaskDao;

	/**
	 * 记录用户最近一次上传坐标时间
	 */
	private static Hashtable keepUploadInfo = new Hashtable();

	@Override
	public String allTask(String json) {
		//System.out.println(json);
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}
		// 获取当天所有巡线任务
		Map<String, String> params = new HashMap<String, String>();
		params.put("STAFF_ID", staffId);
		params.put("CURR_TIME", StringUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
//		params.put("CURR_TIME", "2017-09-20 11:00:00");
		List<Map> taskList = cableInterfaceDao.getAllTask(params);
		if (taskList == null || taskList.size() < 1) {
			result.put("result", "001");
			result.put("tasks", "");
			return result.toString();
		}
		JSONArray taskArray = new JSONArray();
		String taskId = null;
		JSONArray pointArray = null;
		List<Map> allPoint = null;
		JSONObject taskJson = null;
//		JSONObject pointJson = null;
		String plan_kind = null;
		List<Map<String, Object>> lineList = null;
		double longitude = Double.parseDouble(jo.getString("longitude"));
		double latitude = Double.parseDouble(jo.getString("latitude"));
		String dealStatus = "0";
		
		if("4.9E-324".equals(jo.getString("longitude"))){
			longitude = 0;
			latitude = 0;
		}
		List<Map> uploadPoints=null;
		Map taskTime = new HashMap();
		taskTime.put("staffId",staffId);
		if(caldao.getCountStaffIdByDept(taskTime)>0){
			uploadPoints = caldao.getUploadForTaskPointByDept(taskTime);
		}else{
			uploadPoints = caldao.getUploadForTaskPoint(taskTime);
		}
		Map taskParam = new HashMap();
		taskParam.put("LONGITUDE", longitude);
		taskParam.put("LATITUDE", latitude);
		for (Map task : taskList) {
//			if(task.get("STATUS_ID").equals("3")){
//				
//				continue;
//			}
			plan_kind = StringUtil.objectToString(task.get("PLAN_KIND"));// 计划类型
			taskParam.put("TASK_ID", task.get("TASK_ID").toString());
			if ("2".equals(plan_kind)) {// 隐患检查任务
				allPoint = cableInterfaceDao.getAllKeepPoint(task.get("TASK_ID").toString());
			} else{// 日常巡检任务
				allPoint = cableInterfaceDao.pointForDistance(taskParam);
			}
			//Map taskTime = caldao.getStartTimeByTaskId(task.get("TASK_ID").toString());
//			taskTime.put("staffId",staffId);	
			double distance=0;
			int SignInNormalCount = 0;
			//点排序
			for (int i=0;i<allPoint.size();i++ ) {
				if(null != allPoint.get(i).get("POINT_TYPE_ID") && !"-1".equals(allPoint.get(i).get("POINT_TYPE_ID").toString())){
//					double lo=Double.parseDouble((String) allPoint.get(i).get("LONGITUDE"));
//					double la=Double.parseDouble((String) allPoint.get(i).get("LATITUDE"));
//					double dis=MapDistance.getDistance(latitude,longitude,la,lo);
//					dis = Math.round(dis/100)/10.0;
//					if(dis==0.0){
//						dis=0.1;
//					}
//					disList.add(dis);
				}else{
//					double lo=Double.parseDouble((String) allPoint.get(i).get("LONGITUDE"));
//					double la=Double.parseDouble((String) allPoint.get(i).get("LATITUDE"));
//					double dis=MapDistance.getDistance(latitude,longitude,la,lo);
//					dis = Math.round(dis/100)/10.0;
//					if(dis==0.0){
//						dis=0.1;
//					}
					//非关键点计算
					if(allPoint.get(i).get("NORMALCOMPLETED").toString().equals("0")){
						taskTime.put("LONGITUDE", allPoint.get(i).get("LONGITUDE").toString());
						taskTime.put("LATITUDE", allPoint.get(i).get("LATITUDE").toString());
						if( arrivalNormalPoints(taskTime,uploadPoints)){
							SignInNormalCount++;
						}
					}else if(allPoint.get(i).get("NORMALCOMPLETED").toString().equals("1")){
						SignInNormalCount++;
					}
					
				}
			}
			task.put("SignInNormalCount", SignInNormalCount);
			//最近点距离插入
			if(allPoint.size()>0){
				task.put("distance", allPoint.get(0).get("DISTANCE"));
			}else{
				task.put("distance",99.9);
			}
			
		}
		
		Collections.sort(taskList,new Comparator<Map>(){
			@Override
			public int compare(Map o1, Map o2) {
				double dis1 = Double.parseDouble(o1.get("distance").toString());
				double dis2 = Double.parseDouble(o2.get("distance").toString());
				if(dis1>dis2){
					return 1;
				}else{
					return -1;
				}				
			}
		});
		for(int i=0;i<taskList.size();i++){
			taskJson = new JSONObject();
			pointArray = new JSONArray();
			plan_kind = StringUtil.objectToString(taskList.get(i).get("PLAN_KIND"));
			taskId = StringUtil.objectToString(taskList.get(i).get("TASK_ID"));
			taskJson.put("taskId", taskId);
			taskJson.put("taskName",StringUtil.objectToString(taskList.get(i).get("TASK_NAME")));
			taskJson.put("taskState", StringUtil.objectToString(taskList.get(i)
					.get("STATUS_ID")));
			taskJson.put("county",StringUtil.objectToString(taskList.get(i).get("NAME")));
			taskJson.put("completeDate", StringUtil.objectToString(taskList.get(i)
					.get("COMPLETE_TIME")));
			if("2".equals(plan_kind)){
				taskJson.put("mainCount", StringUtil.objectToString(taskList.get(i)
						.get("MAINCOUNTFORCHECK")));
			}else{
				taskJson.put("mainCount", StringUtil.objectToString(taskList.get(i)
						.get("MAINCOUNT")));
			}
			taskJson.put("SignInCount", StringUtil.objectToString(taskList.get(i)
					.get("SIGNINCOUNT")));
			if("2".equals(plan_kind)){
				taskJson.put("NotSignInCount",StringUtil.objectToString(Integer.parseInt(taskList.get(i)
						.get("MAINCOUNTFORCHECK").toString())-Integer.parseInt(taskList.get(i)
								.get("SIGNINCOUNT").toString())));
			}else{
				
				taskJson.put("NotSignInCount",StringUtil.objectToString(Integer.parseInt(taskList.get(i)
						.get("MAINCOUNT").toString())-Integer.parseInt(taskList.get(i)
								.get("SIGNINCOUNT").toString())));
			}
			
			//新增的非关键点
			taskJson.put("normalCount", StringUtil.objectToString(taskList.get(i)
					.get("NORMALCOUNT")));
			taskJson.put("SignInNormalCount", StringUtil.objectToString(taskList.get(i).get("SignInNormalCount")));
			taskJson.put("NotSignInNormalCount",StringUtil.objectToString(Integer.parseInt(taskList.get(i)
					.get("NORMALCOUNT").toString())-Integer.parseInt(taskList.get(i)
							.get("SignInNormalCount").toString())));
			taskJson.put("modifyDate", StringUtil.objectToString(taskList.get(i)
					.get("MODIFYTIME")));
			taskJson.put("lineType", StringUtil.objectToString(taskList.get(i)
					.get("LINETYPE")));
			taskJson.put("deal_status", dealStatus);
			taskJson.put("distance", StringUtil.objectToString(taskList.get(i).get("distance")));
			//计算执行状态
			if("0".equals(StringUtil.objectToString(taskJson.get("NotSignInCount"))) 
					&& "0".equals(StringUtil.objectToString(taskJson.get("NotSignInNormalCount"))))
			{
				taskJson.put("taskState", "3");
			}
			taskArray.add(taskJson);
		}
		
		result.put("result", "000");
		result.put("tasks", taskArray);
		//System.out.println(result);
		return result.toString();
	}
	
	@Override
	public String allPonitsByTask(String json) {
		//System.out.println(json);
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");
		String taskId = jo.getString("taskId");
		double longitude = Double.parseDouble(jo.getString("longitude"));
		double latitude = Double.parseDouble(jo.getString("latitude"));
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}
		// 获取当天所有巡线任务
		Map<String, String> params = new HashMap<String, String>();
		params.put("STAFF_ID", staffId);
		params.put("TASK_ID", taskId);
		params.put("CURR_TIME", StringUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		List<Map> taskList = cableInterfaceDao.getAllTask(params);
		if (taskList == null || taskList.size() < 1) {
			result.put("result", "001");
			result.put("tasks", "");
			return result.toString();
		}
		JSONArray taskArray = new JSONArray();
		JSONArray pointArray = null;
		List<Map> allPoint = null;
		JSONObject taskJson = null;
		JSONObject pointJson = null;
		String plan_kind = null;
		List<Map<String, Object>> lineList = null;
		int normal = 0;
		for (Map task : taskList) {
			Map taskTime = caldao.getStartTimeByTaskId(task.get("TASK_ID").toString());
			taskTime.put("staffId",staffId);
			List<Map> uploadPoints=null;
			int toIndex = 0;
			if ("2".equals(task.get("PLAN_KIND").toString())) {// 隐患检查任务
				allPoint = cableInterfaceDao.getAllKeepPoint(taskId);
			} else {// 日常巡检任务
				if(Integer.parseInt(task.get("LINETYPE").toString())==2){
					allPoint = cableInterfaceDao.getTaskPoint(taskId);
					if(allPoint.size()>400){
						toIndex=400;
					}
				}else{
					allPoint = cableInterfaceDao.getAllPoint2(taskId);
				}
			}
			toIndex = toIndex == 400?400:allPoint.size();
			int SignInNormalCount = 0;
			
			if(caldao.getCountStaffIdByDept(taskTime)>0){
				uploadPoints = caldao.getUploadForTaskPointByDept(taskTime);
			}else{
				uploadPoints = caldao.getUploadForTaskPoint(taskTime);
			}
			
			//点排序
//			for (int i=0;i<allPoint.size();i++ ) {
//				if(null != allPoint.get(i).get("POINT_TYPE_ID") && !"-1".equals(allPoint.get(i).get("POINT_TYPE_ID").toString())){
//				}else{
//					if(allPoint.get(i).get("NORMALCOMPLETED").toString().equals("0")){
//						taskTime.put("LONGITUDE", allPoint.get(i).get("LONGITUDE").toString());
//						taskTime.put("LATITUDE", allPoint.get(i).get("LATITUDE").toString());
//						if( arrivalNormalPoints(taskTime,uploadPoints)){
//							SignInNormalCount++;
//						}
//					}else if(allPoint.get(i).get("NORMALCOMPLETED").toString().equals("1")){
//						SignInNormalCount++;
//					}
//				}
//			}
			taskJson = new JSONObject();
			pointArray = new JSONArray();
			taskId = StringUtil.objectToString(task.get("TASK_ID"));
			taskJson.put("taskId", taskId);
			taskJson.put("taskName", StringUtil.objectToString(task
					.get("TASK_NAME")));
			taskJson.put("taskState", StringUtil.objectToString(task
					.get("STATUS_ID")));
			taskJson.put("county", StringUtil.objectToString(task.get("NAME")));
			taskJson.put("completeDate", StringUtil.objectToString(task
					.get("COMPLETE_TIME")));
			taskJson.put("mainCount", StringUtil.objectToString(task
					.get("MAINCOUNT")));
			taskJson.put("SignInCount", StringUtil.objectToString(task
					.get("SIGNINCOUNT")));
			taskJson.put("NotSignInCount",StringUtil.objectToString(Integer.parseInt(task
					.get("MAINCOUNT").toString())-Integer.parseInt(task
							.get("SIGNINCOUNT").toString())));
			taskJson.put("modifyDate", StringUtil.objectToString(task
					.get("MODIFYTIME")));
			taskJson.put("lineType", StringUtil.objectToString(task
					.get("LINETYPE")));
			normal = Integer.parseInt(task.get("NORMALCOUNT").toString());
			taskJson.put("normalCount", StringUtil.objectToString(normal));
			taskJson.put("SignInNormalCount", SignInNormalCount);
			

			plan_kind = StringUtil.objectToString(task.get("PLAN_KIND"));// 计划类型
			
			List<Map> newAllPoint = allPoint.subList(0, toIndex);
			for (Map point : newAllPoint) {
				pointJson = new JSONObject();
				pointJson.put("point_id", StringUtil.objectToString(point
						.get("POINT_ID")));
//				pointJson.put("point_no", StringUtil.objectToString(point
//						.get("POINT_NO")));
				pointJson.put("task_detail_id", StringUtil.objectToString(point
						.get("TASK_DETAIL_ID")));
				pointJson.put("point_type", StringUtil.objectToString(point
						.get("POINT_TYPE_ID")));
				pointJson.put("point_name", StringUtil.objectToString(point
						.get("POINT_NAME")));
				pointJson.put("point_eqp_type", StringUtil.objectToString(point
						.get("eqp_type")));
				if("-1".equals(point.get("POINT_TYPE_ID").toString())){
					taskTime.put("LONGITUDE", point.get("LONGITUDE").toString());
					taskTime.put("LATITUDE", point.get("LATITUDE").toString());
					if(point.get("NORMALCOMPLETED").toString().equals("0")){
						if(arrivalNormalPoints(taskTime,uploadPoints)){
							pointJson.put("state", "1");
							SignInNormalCount++;
						}else{
							pointJson.put("state", "0");
						}
					}else{
						pointJson.put("state", "1");
					}
				}else{
					pointJson.put("state", StringUtil.objectToString(point
							.get("COMPLETED")));
				}
				pointJson.put("longitude", StringUtil.objectToString(point
						.get("LONGITUDE")));
				pointJson.put("latitude", StringUtil.objectToString(point
						.get("LATITUDE")));
				pointJson.put("inaccuracy", SysSet.CONFIG.get(AppType.LXXJ)
						.get(SysSet.INACCURACY));
				pointJson.put("line_id", StringUtil.objectToString(point
						.get("LINE_ID")));
				pointJson.put("point_level", StringUtil.objectToString(point
						.get("LEVEL_NAME")));
				pointJson.put("line_id", StringUtil.objectToString(point
						.get("LINE_ID")));
				pointJson.put("line_type", StringUtil.objectToString(point
						.get("LINE_TYPE")));
				//TODO:区域性和点型任务关键点签到不显示
				if(pointJson.get("state").equals("1")
						&&!pointJson.get("point_type").equals("-1")
						&&task.containsKey("LINETYPE")
						&&(Integer.parseInt(task.get("LINETYPE").toString())==2
						||Integer.parseInt(task.get("LINETYPE").toString())==3)
						){
				}else{
					pointArray.add(pointJson);
				}
				//pointArray.add(pointJson);
			}
			taskJson.put("deal_status", "0");
			taskJson.put("points", pointArray);
			taskArray.add(taskJson);
			int  NotSignInNormalCount=SignInNormalCount==0?normal:(normal-SignInNormalCount);
			//修改任务状态
			if("0".equals(StringUtil.objectToString(taskJson.get("NotSignInCount"))) 
					&& NotSignInNormalCount==0
					&& !"3".equals(StringUtil.objectToString(task
							.get("STATUS_ID"))))
			{
				cableInterfaceDao.updateTaskComplete(taskId);
				arrivalService.callWfworkitemflowServiceForTask(taskId);
				zhxjTaskDao.editTaskTypeForlxxj(taskId);
			}
		}
		result.put("result", "000");
		result.put("tasks", taskArray);
		//System.out.println(result);
		return result.toString();
	}
	
	@Override
	public String uploadPhoto(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		Map<String, Object> photoMap = new HashMap<String, Object>();
		try {
			// 解析参数
			request.setCharacterEncoding("utf-8");
			String staffId = request.getHeader("staffId");
			String taskId = request.getHeader("taskId");
			String pointId = request.getHeader("pointId");
			String terminalType = request.getHeader("terminalType");
			String sn = request.getHeader("sn");
			//System.out.println(sn);
			String type = request.getHeader("type");

//			if (!OnlineUserListener.isLogin(staffId, sn)) {
//				result.put("result", "002");
//				result.put("photoId", "");
//				return result.toString();
//			}

			photoMap.put("CREATE_STAFF", staffId);
			if("null".equals(taskId)||taskId==null){
				taskId="";
			}
			photoMap.put("TASK_ID", taskId);
			photoMap.put("POINT_ID", pointId);
			photoMap.put("TERMINAL_TYPE", terminalType);
			photoMap.put("SN", sn);
			photoMap.put("PHOTO_TYPE", type);

			// 文件流
			byte[] photoByte = ImageTool.getByteFromStream(request);
			//新增如果流异常，则返回异常
			if (null == photoByte || photoByte.length==0)
			{
				result.put("result", "001");
				result.put("photoId", "");
				return result.toString();
			}
			// 获取photo主键
			String photoId = cableInterfaceDao.getPhotoId();
			photoMap.put("PHOTO_ID", photoId);
			Map resultMap = baseMethodService.uploadFile(photoId, photoByte,
					"photo");
			if (null!=resultMap&&"true".equals(StringUtil.objectToString(resultMap
					.get("isSuccess")))) {
				/* 将图片保存的地址返回给调用方 */
				String photoPath = StringUtil.objectToString(resultMap
						.get("photoPath")); // 原尺寸照片地址
				String microPhotoPath = StringUtil.objectToString(resultMap
						.get("microPhotoPath")); // 缩微图照片地址
				// 对文件路径要做如下处理
				String fsi = GlobalValue.FILE_SERVER_INTERNET;
				int index = photoPath.indexOf("files");
				if (index != -1) {
					photoPath = fsi
							+ photoPath.substring(index, photoPath.length());

				}
				index = microPhotoPath.indexOf("files");
				if (index != -1) {
					microPhotoPath = fsi
							+ microPhotoPath.substring(index, microPhotoPath
									.length());

				}
				photoMap.put("PHOTO_PATH", photoPath == null ? "" : photoPath);
				photoMap.put("MICRO_PHOTO_PATH", microPhotoPath == null ? ""
						: microPhotoPath);
			}
			if (cableInterfaceDao.insertPhoto(photoMap) == 1) {
				result.put("result", "000");
				result.put("photoId", photoId);
				return result.toString();
			}
			result.put("result", "001");
			result.put("photoId", "");
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("photoId", "");
			return result.toString();
		}
	}

	@Override
	public String saveGd(HttpServletRequest request) {
		try {
			// 解析参数
			String json = BaseServletTool.getParam(request);
			//String json = "{\"terminalType\":\"1\",\"sn\":\"355905073944507\",\"staffId\":\"50968\",\"grid_id\":\"10864\",\"type\":\"1\",\"taskId\":\"\",\"taskDetailId\":\"196913\",\"pointId\":\"275817\",\"longitude\":\"118.748519\",\"latitude\":\"31.986667\",\"zoneid\":\"100\",\"troubleIds\":\"5\",\"description\":\"测试\",\"cableName\":\"\",\"consUnit\":\"\",\"consContent\":\"道路施工\",\"cableLevel\":\"\",\"isKeep\":\"0\",\"isOrder\":\"0\",\"photoIds\":\"1178438,\",\"afterChangePhotoIds\":\"\",\"equipCode\":\"\",\"equipType\":\"\",\"is_fix\":\"\",\"equipName\":\"\",\"keepRadius\":\"\"}";
			JSONObject jo = JSONObject.fromObject(json);
			String terminalType = jo.getString("terminalType");
			String sn = jo.getString("sn");
			String staffId = jo.getString("staffId");

//			if (!OnlineUserListener.isLogin(staffId, sn)) {
//				return Result.returnCode("002");
//			}

			String taskId = jo.getString("taskId");
			String taskDetailId = jo.getString("taskDetailId");
			String pointId = jo.getString("pointId");
			String longitude = jo.getString("longitude");
			String latitude = jo.getString("latitude");
			String zoneid = jo.getString("zoneid");
			String troubleIds = jo.getString("troubleIds");
			String description = jo.getString("description");
			String cableName = jo.getString("cableName");
			String consUnit = jo.getString("consUnit");
			String consContent = jo.getString("consContent");
			String cableLevel = jo.getString("cableLevel");
			String type = jo.getString("type");
			String isKeep = jo.getString("isKeep");
			String photoIds = jo.getString("photoIds");
			String equipCode = jo.getString("equipCode");
			String equipName = jo.getString("equipName");
			String equipType = jo.getString("equipType");
			String is_fix = jo.getString("is_fix");
			String keepRadius = jo.getString("keepRadius");
			String afterChangePhotoIds = "";
			String isOrder = "";
			String mnt_level_id = "";
			String grid_id = "";
			//新增参数
			if(jo.containsKey("afterChangePhotoIds")){
				afterChangePhotoIds = jo.getString("afterChangePhotoIds");//整改后照片ID
			}
			if(jo.containsKey("isOrder")){
				isOrder = jo.getString("isOrder");//是否现场处理;1:是,0:不是
			}
			if(jo.containsKey("mnt_leve_id")){
				mnt_level_id = jo.getString("mnt_leve_id");//维护等级
			}
			if(jo.containsKey("grid_id")){
				grid_id = jo.getString("grid_id");//维护等级
			}
			
			//处理流程分辨0不可以，1可以
//			String handleable = jo.getString("handleable");
			// 保存工单
			Map<String, Object> billMap = new HashMap<String, Object>();
			billMap.put("POINT_ID", pointId);
			billMap.put("TERMINAL_TYPE", terminalType);
			billMap.put("TASK_ID", taskId);
			billMap.put("INSPECTOR", staffId);
			billMap.put("TASK_DETAIL_ID", taskDetailId);
			billMap.put("SON_AREA_ID", zoneid);
			billMap.put("LONGITUDE", longitude);
			billMap.put("LATITUDE", latitude);
			billMap.put("TROUBLE_IDS", troubleIds);
			billMap.put("DESCRP", description);
			billMap.put("CABLE_NAME", cableName);
			billMap.put("CONS_UNIT", consUnit);
			billMap.put("CONS_CONTENT", consContent);
			billMap.put("CABLE_LEVEL", cableLevel);
			billMap.put("ISKEEP", isKeep);
			billMap.put("EQUIP_CODE", equipCode);
			billMap.put("EQUIP_NAME", equipName);
			billMap.put("EQUIP_TYPE", equipType);
			billMap.put("IS_FIX", is_fix);
			billMap.put("RECORD_TYPE", type);
			billMap.put("KEEPRADIUS", keepRadius);
			billMap.put("MNT_LEVEL_ID", mnt_level_id);
			billMap.put("MAINTOR", "");
			billMap.put("AUDITOR", "");
			billMap.put("GRID_ID", grid_id);
			String areaId = "";
			// 4为临时上报，临时上报
			if (!"2".equals(type)) {
				// 临时上报需要把上报人员的son_area_id作为上报的区域
				if ("4".equals(type)) {
					Map<String, Object> staffInfoMap = staffDao
							.findPersonalInfo(staffId);
					zoneid = staffInfoMap.get("SON_AREA_ID").toString();
					billMap.put("SON_AREA_ID", zoneid);
					billMap.put("TROUBLE_IDS", 22);// 临时上报专用
				}
				// 父地区id
				areaId = cableInterfaceDao.getAreaIdBySonAreaId(zoneid);
				billMap.put("AREA_ID", areaId);
			}
			// 获取关联属性
			if (taskId != null && !"".equals(taskId)) {
				String planId = cableInterfaceDao.getPlanIdByTaskId(taskId);
				billMap.put("PLAN_ID", planId);
			} else {
				billMap.put("PLAN_ID", "");
			}
			if ("1".equals(type) || "4".equals(type)) {// 隐患上报拍照上传、临时上报走闭环流程
				// 获取关联的审核员
				List<Map<String, Object>> auditor = cableInterfaceDao
						.getAuditorBySonAreaId(zoneid);
				if (auditor == null || auditor.size() < 1) {
					auditor = cableInterfaceDao.getAuditorByAreaId(areaId);
				}
				//上报改审核员为空
				billMap.put("AUDITOR", null);
				billMap.put("type", type);
				// 获取关联的子地区名称和隐患类型名称
				List<Map<String, String>> list = cableInterfaceDao
						.getRelInfo(billMap);
				if (StringUtils.isBlank(description)) {
					billMap.put("NAME", (list.get(0).get("NAME") + "_"
							+ list.get(0).get("TYPE_NAME") + "_"
							+ StringUtil.getCurrDate()));
				} else {
					billMap.put("NAME",(list.get(0).get("NAME") + "_"
							+ description));
				}
				if ("1".equals(isKeep)) {
					billMap.put("POINT_TYPE", "3");
				} else {
					billMap.put("POINT_TYPE", "2");
				}
				if (pointId != null && !"".equals(pointId)) {
					cableInterfaceDao.updatePoint(billMap);
				} else {
					if(billMap.get("LONGITUDE")==null||("").equals(billMap.get("LONGITUDE"))){
						return Result.returnCode("001", "上报失败，请重新定位");
					}
					cableInterfaceDao.insertPoint(billMap);
				}
				// 生成工单
				if (pointId != null && !"".equals(pointId)) {// 更新工单
					// 根据pointId查询出对应的工单id
					Map<String, Object> billTemp = cableInterfaceDao
							.getBillByPointId(pointId);
					if (billTemp == null || billTemp.get("BILL_ID") == null) {
						//隐患上报已处理不派单时
						billMap.put("MAINTOR", staffId);
						cableInterfaceDao.saveBill(billMap);
						cableInterfaceDao.saveBillDetail(billMap);
					} else {
						billMap.put("BILL_ID", billTemp.get("BILL_ID"));
						//billMap.put("MAINTOR", staffId);
						cableInterfaceDao.updateBill(billMap);
						cableInterfaceDao.updateBillDetail(billMap);
					}
				} else {// 生成新工单
					try {
						cableInterfaceDao.saveBill(billMap);
						cableInterfaceDao.saveBillDetail(billMap);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				Map<String, Object> flowParams = new HashMap<String, Object>();
				flowParams.put("BILL_ID", billMap.get("BILL_ID"));
				flowParams.put("STATUS", 1);
				flowParams.put("OPERATE_INFO", "上报问题");
				flowParams.put("OPERATOR", staffId);
				flowParams.put("TYPE", type);
				flowParams.put("RECEIVOR", auditor.get(0).get("STAFF_ID"));
				pointDao.insertBillFlow(flowParams);
				
				if(!"".equals(isOrder)&&"1".equals(isOrder)){
//					billMap.put("LONGITUDE", 118.686766);//测试坐标
//					billMap.put("LATITUDE", 31.997227);
					//插入现场回单的坐标
					billMap.put("ISKEEP", 2);
					billMap.put("RECORD_TYPE", 2);
					cableInterfaceDao.saveRecord(billMap);
					//还原map数据
					billMap.put("RECORD_TYPE", type);
					billMap.put("ISKEEP", isKeep);
					//修改工单状态
					billMap.put("MAINTOR", staffId);
					cableInterfaceDao.updateBillAudit(billMap);
					//添加流程
					flowParams.put("BILL_ID", billMap.get("BILL_ID"));
					flowParams.put("STATUS", 3);
					flowParams.put("TYPE", type);
					flowParams.put("OPERATE_INFO", "回单");
					flowParams.put("OPERATOR", staffId);
					pointDao.insertBillFlow(flowParams);
					if(!"".equals(afterChangePhotoIds)){
						String[] changePhotoIds = afterChangePhotoIds.split(",");
						for (String photo : changePhotoIds) {
							billMap.put("PHOTO_ID", photo);
							cableInterfaceDao.insertPhotoRel(billMap);
						}
					}
				}
				try {
					// 获取关联的审核员
					List<Map<String, Object>> auditorNameList = cableInterfaceDao
							.getAuditorNameBySonAreaId(zoneid);
					// 发送短信内容
					String messageText = "";
					List phoneNumList = new ArrayList();
					List messageContentList = new ArrayList();
					for (int i = 0; i < auditorNameList.size(); i++) {
						if (auditorNameList.get(i).get("STAFF_NAME") != null
								&& null != auditorNameList.get(i).get("TEL")
								&& !"".equals(auditorNameList.get(i).get("TEL"))) {
							messageText = auditorNameList.get(i).get("STAFF_NAME")
									+ "，您好。您于"
									+ DateUtil.getCurrentTime()
									+ "收到"
									+ equipCode
									+ "隐患单，请及时审核派发。【分公司缆线巡检】";
							icom.util.sendMessage.SendMessageUtil
							.sendMessageInfo(auditorNameList.get(i).get("TEL").toString(),											
									         messageText,
									         auditorNameList.get(i)	.get("AREA_ID").toString());
						}	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if ("2".equals(type)) {// 隐患整治
				// 根据pointId查询出对应的工单id
				Map<String, Object> billDetail = cableInterfaceDao
						.getBillByPointId(pointId);
				billMap.put("BILL_ID", billDetail.get("BILL_ID"));
				cableInterfaceDao.updateBillAudit(billMap);
				Map<String, Object> flowParams = new HashMap<String, Object>();
				flowParams.put("BILL_ID", billDetail.get("BILL_ID"));
				flowParams.put("STATUS", 3);
				flowParams.put("TYPE", type);
				flowParams.put("OPERATE_INFO", "回单");
				flowParams.put("OPERATOR", staffId);
				flowParams.put("RECEIVOR", billDetail.get("AUDITOR"));
				pointDao.insertBillFlow(flowParams);
				
			}
			if ("5".equals(type)) {// 坐标采集
				/*try {
					// 获取关联的审核员
					List<Map<String, Object>> auditorNameList = cableInterfaceDao
							.getAuditorNameBySonAreaId(zoneid);
					// 发送短信内容
					String messageText = "";
					// 发短信开关开启
					List phoneNumList = new ArrayList();
					List messageContentList = new ArrayList();
					for (int i = 0; i < auditorNameList.size(); i++) {
						if (auditorNameList.get(i).get("STAFF_NAME") != null
								&& null != auditorNameList.get(i).get("TEL")
								&& !""
										.equals(auditorNameList.get(i).get(
												"TEL"))) {
							messageText = auditorNameList.get(i).get(
									"STAFF_NAME")
									+ "，您好。您于"
									+ DateUtil.getCurrentTime()
									+ "收到" + equipCode + "采集点，请及时审核。【分公司缆线巡检】";
							phoneNumList.add(auditorNameList.get(i).get("TEL"));
							messageContentList.add(messageText);
						}
					}
					SendMessageUtil.sendMessageList(phoneNumList,
							messageContentList);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
			//日常巡检判断是否已签到
			if ("0".equals(type) && !"".equals(taskId) && !"".equals(taskDetailId) && !"".equals(pointId)) 
			{
				int SignPointFlag = cableInterfaceDao.findSignPointById(billMap);
				if(SignPointFlag>0)
				{
					return Result.returnCode("001");
				}
			}
			try {
				//1020错误代码,此设备编码不存在
				if("5".equals(type)&& (equipType.equals("1000") || equipType.equals("1001"))){
					//查询OSS是否有此设备
					Map JNDI_MAP = new HashMap();
					JNDI_MAP.put("20","OSSBC_DEV_SZ");
					JNDI_MAP.put("84","OSSBC_DEV_SQ");
					JNDI_MAP.put("15","OSSBC_DEV_WX");
					JNDI_MAP.put("69","OSSBC_DEV_CZ");
					JNDI_MAP.put("4","OSSBC_DEV_ZJ");
					JNDI_MAP.put("26","OSSBC_DEV_NT");
					JNDI_MAP.put("79","OSSBC_DEV_TZ");
					JNDI_MAP.put("33","OSSBC_DEV_YZ");
					JNDI_MAP.put("60","OSSBC_DEV_HA");
					JNDI_MAP.put("39","OSSBC_DEV_YC");
					JNDI_MAP.put("63","OSSBC_DEV_LYG");
					JNDI_MAP.put("48","OSSBC_DEV_XZ");
					JNDI_MAP.put("3","OSSBC_DEV_NJ");
					int sum=0;
					try{
					SwitchDataSourceUtil.setCurrentDataSource("cpf83");
					JNDI_MAP.put("equipCode", equipCode);
					JNDI_MAP.put("jndi", JNDI_MAP.get(areaId));
					sum = cableInterfaceDao.ifEqpExistOss(JNDI_MAP);
					SwitchDataSourceUtil.clearDataSource();
				} catch (Exception e) {
					SwitchDataSourceUtil.clearDataSource();
				}
					if(sum==0){
						JSONObject resultJson = new JSONObject();
						resultJson.put("result", "1020");
						resultJson.put("desc", "OSS不存在此设备，请在OSS系统增加后再采集");
						return resultJson.toString();
					}
				}
			} catch (Exception e) {
				SwitchDataSourceUtil.clearDataSource();
			}
			
			cableInterfaceDao.saveRecord(billMap);
			try {
				// 修改关键点信息和自动审核
				if ("5".equals(type)&&(equipType.equals("1000") || equipType.equals("1001"))) {
					areaId = cableInterfaceDao.getAreaIdBySonAreaId(zoneid);
					billMap.put("AREA_ID", areaId);
					billMap.put("POINT_TYPE", "4");
					billMap.put("POINT_NO", equipCode);
					billMap.put("POINT_NAME", equipName);
					billMap.put("MNT_LEVEL_ID", mnt_level_id);
					Map point = cableInterfaceDao.ifPointExist(billMap);
					if (point.get("COUNT").toString().equals("0")) {
						cableInterfaceDao.insertEqpPoint(billMap);
					} else {
						billMap.put("POINT_ID", point.get("POINT_ID"));
						cableInterfaceDao.updateEqpPoint(billMap);
					}
					// 修改record的type
					cableInterfaceDao.updateRecordType(billMap);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			// 保存照片关系
			if (!"".equals(photoIds)) {
				String[] photos = photoIds.split(",");
				for (String photo : photos) {
					billMap.put("PHOTO_ID", photo);
					cableInterfaceDao.insertPhotoRel(billMap);
				}
			}
			// 修改任务状态
			if (!StringUtil.isEmpty(taskDetailId)) {
				// 计划的类型
				int planKind = cableInterfaceDao
						.findPlanKidByTaskDetailId(taskDetailId);
				if (planKind > 1) {// 非缆线计划
					cableInterfaceDao.updateDetailComplete(taskDetailId);
					// 判断任务明细是否全部完成
					List<Map<String, Object>> detailList = cableInterfaceDao
							.findAllDetail(taskId);
					if (detailList != null && detailList.size() == 1) {
						cableInterfaceDao.updateTaskComplete(taskId);
					} else {
						cableInterfaceDao.updateTaskBegin(taskId);
					}
				} else {
					// 判断该明细是否已经完成
					Integer isFinish = cableInterfaceDao
							.queryDetailFinish(taskDetailId);
					//判断非关键点
					List<Map> normalPoint = caldao.getPointsLocation(taskId);
					int num = 0;
					Map conds = caldao.getStartTimeByTaskId(taskId);
					conds.put("staffId",staffId);
					if(normalPoint!=null){
						List<Map> uploadPoints = caldao.getUploadForTaskPoint(conds);
						for (Map map : normalPoint) {
							conds.put("LONGITUDE", map.get("LONGITUDE"));
							conds.put("LATITUDE", map.get("LATITUDE"));
							if(arrivalNormalPoints(conds, uploadPoints)){
								num++;
							}
						}
					}
					if (isFinish != null && isFinish == 1) {
						cableInterfaceDao.updateDetailComplete(taskDetailId);
						// 判断任务明细是否全部完成
						List<Map<String, Object>> detailList = cableInterfaceDao
								.findAllDetail(taskId);
						if (detailList != null && detailList.size() == 1 && num==normalPoint.size()) {
							cableInterfaceDao.updateTaskComplete(taskId);
						} else {
							cableInterfaceDao.updateTaskBegin(taskId);
						}
					} else {
						cableInterfaceDao.updateTaskBegin(taskId);
					}
				}
			}
			return Result.returnCode("000");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.returnCode("001");
		}
	}

	@Override
	public String getZones(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		// 解析参数
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String staffId = jo.getString("staffId");

//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("zones", "");
//			return result.toString();
//		}
		// 查询地区
		List<Map<String, Object>> areaList = cableInterfaceDao
				.getZones(staffId);
		if (areaList == null || areaList.size() < 1) {
			result.put("result", "001");
			result.put("zones", "");
			return result.toString();
		}
		JSONArray arr = new JSONArray();
		JSONObject areaJson = null;
		for (Map<String, Object> area : areaList) {
			areaJson = new JSONObject();
			areaJson.put("zone_id", StringUtil.objectToString(area
					.get("AREA_ID")));
			areaJson.put("zone_name", StringUtil.objectToString(area
					.get("NAME")));
			arr.add(areaJson);
		}
		result.put("result", "000");
		result.put("zones", arr);
		return result.toString();
	}

	@Override
	public String getTroubleTypes(HttpServletRequest request) {
		JSONObject result = new JSONObject();

		// 解析参数
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String staffId = jo.getString("staffId");

//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("troubleTypes", "");
//			return result.toString();
//		}
		// 查询所有隐患类型
		List<Map<String, Object>> troubleList = cableInterfaceDao
				.getTroubleTypes();
		if (troubleList == null || troubleList.size() < 1) {
			return Result.returnCode("001");
		}
		JSONArray arr = new JSONArray();
		JSONArray sonArr = null;
		JSONObject typeJson = null;
		JSONObject sonJson = null;
		String type_id = null;
		for (Map<String, Object> trouble : troubleList) {
			if ("0".equals(StringUtil.objectToString(trouble
					.get("PARENT_TYPE_ID")))) {
				sonArr = new JSONArray();
				typeJson = new JSONObject();
				type_id = StringUtil.objectToString(trouble.get("TYPE_ID"));
				typeJson.put("type_id", type_id);
				typeJson.put("type_name", StringUtil.objectToString(trouble
						.get("TYPE_NAME")));
				for (Map<String, Object> son : troubleList) {
					if (type_id.equals(StringUtil.objectToString(son
							.get("PARENT_TYPE_ID")))) {
						sonJson = new JSONObject();
						sonJson.put("type_id", StringUtil.objectToString(son
								.get("TYPE_ID")));
						sonJson.put("type_name", StringUtil.objectToString(son
								.get("TYPE_NAME")));
						sonArr.add(sonJson);
					}
				}
				typeJson.put("typeJson", sonArr);
				arr.add(typeJson);
			}
		}
		result.put("result", "000");
		result.put("troubleTypes", arr);
		return result.toString();
	}

	@Override
	public String getConsPoints(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("result", "000");
		// 解析参数
	    String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String staffId = jo.getString("staffId");

//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("beginTime", "");
//			result.put("endTime", "");
//			result.put("points", "");
//			return result.toString();
//		}
		// 查询当天的看护任务
		Map<String, String> params = new HashMap<String, String>();
		params.put("STAFF_ID", staffId);
		params.put("START_TIME", StringUtil.getCurrDate() + " 00:00:00");
		params.put("END_TIME", StringUtil.getCurrDate() + " 23:59:59");
		List<Map<String, Object>> taskList = cableInterfaceDao
				.getConsTask(params);
		if (taskList == null || taskList.size() < 1) {
			result.put("points", "");
			return result.toString();
		}
		String taskId = null;
		JSONArray array = new JSONArray();
		JSONArray keep = new JSONArray();
		JSONObject pointJson = null;
		JSONObject keepPointJson = null;
		List<Map> allPoint = null;
		List<Map> keepLine = null;
		Date startTime = null;
		Date startTimeTemp = null;
		Date endTime = null;
		Date endTimeTemp = null;
		String inaccuracy = null;
		for (Map<String, Object> task : taskList) {
			taskId = StringUtil.objectToString(task.get("TASK_ID"));
			inaccuracy = StringUtil.objectToString(task.get("INACCURACY"));
			startTimeTemp = StringUtil.stringToDate(StringUtil
					.objectToString(task.get("START_TIME")));
			endTimeTemp = StringUtil.stringToDate(StringUtil
					.objectToString(task.get("COMPLETE_TIME")));

			if (StringUtils.isBlank(inaccuracy)) {
				inaccuracy = "50";
			}

			startTime = (startTime == null ? startTimeTemp : (startTime
					.before(startTimeTemp) ? startTime : startTimeTemp));
			endTime = (endTime == null ? endTimeTemp : (endTime
					.before(endTimeTemp) ? startTimeTemp : endTime));

			// 获取任务对应的点信息
			allPoint = cableInterfaceDao.getAllKeepPoint(taskId);
			params.put("plan_id", pointDao.getKeepPlanIDByTask(taskId));
			keepLine=pointDao.queryKeepLinePoints(params);
			for (Map point : allPoint) {
				pointJson = new JSONObject();
				pointJson.put("point_id", StringUtil.objectToString(point
						.get("POINT_ID")));
				pointJson.put("task_id", taskId);
				pointJson.put("point_name", StringUtil.objectToString(point
						.get("POINT_NAME")));
				pointJson.put("state", StringUtil.objectToString(point
						.get("COMPLETED")));
				pointJson.put("longitude", StringUtil.objectToString(point
						.get("LONGITUDE")));
				pointJson.put("latitude", StringUtil.objectToString(point
						.get("LATITUDE")));
				pointJson.put("inaccuracy", inaccuracy);
				//获得看护区域
				if(keepLine!=null&&keepLine.size()>0){
					for (Map map : keepLine) {
						keepPointJson = new JSONObject();
						keepPointJson.put("PLAN_ID", StringUtil.objectToString(map.get("PLAN_ID")));
						keepPointJson.put("LONGITUDE", StringUtil.objectToString(map.get("LONGITUDE")));
						keepPointJson.put("LATITUDE", StringUtil.objectToString(map.get("LATITUDE")));
						keepPointJson.put("POINT_ID", StringUtil.objectToString(map.get("POINT_ID")));
						keepPointJson.put("POINT_SEQ", StringUtil.objectToString(map.get("POINT_SEQ")));
						keep.add(keepPointJson);
					}
					pointJson.put("keep_line", keep);
				}else{
					pointJson.put("keep_line", "");
				}
				
				array.add(pointJson);
			}
		}
		result.put("beginTime", StringUtil.dateToString(startTimeTemp));
		result.put("endTime", StringUtil.dateToString(endTime));
		result.put("points", array);
		return result.toString();
	}

	@Override
	public String getBills(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		// 解析参数
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String staffId = jo.getString("staffId");

//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("bills", "");
//			return result.toString();
//		}
		// 获取所有待回单工单
		List<Map<String, Object>> billList = cableInterfaceDao
				.getBills(staffId);
		JSONArray billsArr = new JSONArray();
		JSONObject billJson = null;
		if (billList != null && billList.size() > 0) {
			for (Map<String, Object> bill : billList) {
				billJson = new JSONObject();
				billJson.put("bill_id", StringUtil.objectToString(bill
						.get("BILL_ID")));
				billJson.put("point_id", StringUtil.objectToString(bill
						.get("POINT_ID")));
				billJson.put("point_name", StringUtil.objectToString(bill
						.get("POINT_NAME")));
				billJson.put("longitude", StringUtil.objectToString(bill
						.get("LONGITUDE")));
				billJson.put("latitude", StringUtil.objectToString(bill
						.get("LATITUDE")));
				billJson.put("zone_name", StringUtil.objectToString(bill
						.get("NAME")));
				billJson.put("inaccuracy", SysSet.CONFIG.get(AppType.LXXJ).get(
						SysSet.INACCURACY));
				billJson.put("trouble", StringUtil.objectToString(bill
						.get("TYPE_NAME")));
				billJson.put("demo", StringUtil.objectToString(bill
						.get("DESCRP")));
				billsArr.add(billJson);
			}
			result.put("result", "000");
			result.put("bills", billsArr);
		} else {
			result.put("result", "001");
			result.put("bills", "");
		}
		return result.toString();
	}
	
	@Override
	public String getTroubles(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		// 解析参数
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		System.out.println(jo);
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String staffId = jo.getString("staffId");
		String areaId = jo.getString("areaId");

//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("points", "");
//			System.out.println(result);
//			return result.toString();
//		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("AREA_ID", areaId);
		List<Map<String, Object>> troubleList = cableInterfaceDao
				.getTroubles(params);
		if (troubleList == null || troubleList.size() < 1) {
			result.put("result", "001");
			result.put("points", "");
			result.put("desc", "区域内没有隐患点");
		} else {
			JSONArray arr = new JSONArray();
			JSONObject troubleJson = null;
			for (Map<String, Object> trouble : troubleList) {
				troubleJson = new JSONObject();
				troubleJson.put("point_id", StringUtil.objectToString(trouble
						.get("POINT_ID")));
				troubleJson.put("point_name", StringUtil.objectToString(trouble
						.get("POINT_NAME")));
				troubleJson.put("zone_name", StringUtil.objectToString(trouble
						.get("NAME")));
				troubleJson.put("state", "0");
				troubleJson.put("longitude", StringUtil.objectToString(trouble
						.get("LONGITUDE")));
				troubleJson.put("latitude", StringUtil.objectToString(trouble
						.get("LATITUDE")));
				troubleJson.put("inaccuracy", SysSet.CONFIG.get(AppType.LXXJ)
						.get(SysSet.INACCURACY));
				arr.add(troubleJson);
			}
			result.put("result", "000");
			result.put("points", arr);
		}
		System.out.println(result);
		return result.toString();
	}

	@Override
	public String getSetting(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		// 解析参数
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String staffId = jo.getString("staffId");
		String appType = jo.getString("appType");

//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("locateSpan", "");
//			result.put("uploadSpan", "");
//			return result.toString();
//		}

		if (SysSet.CONFIG.get(appType) != null) {
			result.put("result", "000");
			result.put("locateSpan", SysSet.CONFIG.get(appType).get(
					SysSet.LOCATE_SPAN));
			result.put("uploadSpan", SysSet.CONFIG.get(appType).get(
					SysSet.UPLOAD_SPAN));
		} else {
			result.put("result", "001");
			result.put("locateSpan", "");
			result.put("uploadSpan", "");
		}
		return result.toString();
	}

	@Override
	public String uploadPoints(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		// 解析参数
		String json = BaseServletTool.getParam(request);
//		String json = "{staffId:18989,terminalType:1,uploadType:0," +
//				"taskId:'',locateSpan:120000,sn:867389021080725,points:[{longitude:118.74712,latitude:31.986317}," +
//				"{longitude:118.74712,latitude:31.986317}," +
//				"{longitude:118.74712,latitude:31.986317}," +
//				"{longitude:118.74712,latitude:31.986317}," +
//				"{longitude:118.74712,latitude:31.986317}," +
//				"{longitude:118.74712,latitude:31.986317}," +
//				"{longitude:118.74712,latitude:31.986317}," +
//				"{longitude:118.74712,latitude:31.986317}]}";
		JSONObject jo = JSONObject.fromObject(json);
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String staffId = jo.getString("staffId");
		String uploadType = jo.getString("uploadType");
//		if (StringUtils.isBlank(staffId)) {
//			return Result.returnCode("001");
//		}

//		 if (!OnlineUserListener.isLogin(staffId, sn)) {
//		 return Result.returnCode("002");
//		 }

		long currDate = new Date().getTime();
		Map<String, Object> param = new HashMap<String, Object>();
		if(!staffId.equals("")||null!=staffId){
			param.put("USER_ID", staffId);
			Map<String, Object> login = paramService.getBaseLoginInfo(param);
			if (login == null || !"1".equals(String.valueOf(login.get("IS_LOGIN")))
					|| !sn.equals(String.valueOf(login.get("SN")))) {// 登录信息不用重复记录
				return Result.returnCode("000");
			}
		}
		
		
		if (null != keepUploadInfo.get(staffId)
				&& !"".equals(keepUploadInfo.get(staffId))) {
			long lastUploadDate = (Long) keepUploadInfo.get(staffId);
			if (currDate - lastUploadDate < 2 * 60 * 1000) {
				return Result.returnCode("000");
			}
		}
		
		// 记录上传时间,供下次比对上传时间间隔使用
		keepUploadInfo.put(staffId, currDate);
		
		String taskId = jo.getString("taskId");
		int locateSpan = jo.getInt("locateSpan");
		String points = jo.getString("points");
		String[] pointArray = points.split(",");
		if (pointArray.length < 1) {
			return Result.returnCode("001");
		}
		//新增未知规则
		if("0".equals(uploadType)){
			//批量插入
			JSONObject jsonObj = null;
			JSONArray arrays = jo.getJSONArray("points");
			List<Map> uploadArray = new ArrayList();
			for(int i=0;i<arrays.size();i++){
				jsonObj = arrays.getJSONObject(i);
				Map<String, Object> pointMap = new HashMap<String, Object>();
				pointMap.put("LONGITUDE", arrays.getJSONObject(i).get("longitude"));
				pointMap.put("LATITUDE", arrays.getJSONObject(i).get("latitude"));
				pointMap.put("UPLOAD_STAFF", staffId);
				pointMap.put("TASK_ID", taskId);
				pointMap.put("UPLOAD_TYPE", uploadType);
				arrays.getJSONObject(i).put("DISTANCE", "DISTANCE"+i);
				pointMap.put("UPLOAD_TIME", new Date(currDate - locateSpan		
						* (arrays.size() - i)));
				cableInterfaceDao.insertUploadPoint(pointMap);
				uploadArray.add(pointMap);
			}
			/*
			 * TODO: 非关键点到位时长计算
			 */
			Map params = new HashMap();
			params.put("staff_id", staffId);
			params.put("location", arrays);
			try {
				List<Map> list = new ArrayList();
				list = caldao.getSignNormalPoint(params);
				Map record= new HashMap();
				if(list.size()>0){
					for (int i = 0; i<list.size(); i++) {
						for (int j = 0; j <uploadArray.size(); j++) {
							if(list.get(i).get("DISTANCE"+j).toString().equals("1")){
								record.put("TIMECOUNT", 2);
								record.put("POINT_ID", list.get(i).get("POINT_ID"));
								record.put("TASK_ID", list.get(i).get("TASK_ID"));
								record.put("UPLOAD_STAFF", staffId);
								record.put("LINE_ID", list.get(i).get("LINE_ID"));
								record.put("DISTANCE",caldao.getDistance(record));
								caldao.addSignNormal(record);
								record.clear();
								break;
							}
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Map<String, Object> pointMap = null;
			String[] point = null;
			String longitude = null;
			String latitude = null;
			int length = pointArray.length;
			for (int i = length; i > 0; i--) {
				point = pointArray[i - 1].split(" ");
				pointMap = new HashMap<String, Object>();
				pointMap.put("LONGITUDE", point[0]);
				pointMap.put("LATITUDE", point[1]);
				pointMap.put("UPLOAD_STAFF", staffId);
				pointMap.put("TASK_ID", taskId);
				pointMap.put("UPLOAD_TIME", new Date(currDate - locateSpan * 1000
						* (length - i)));
				cableInterfaceDao.insertUploadPoint(pointMap);
			}
		}
		return Result.returnCode("000");
	}

	@Override
	public String searchEquipInfoService(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		// 解析参数
		String json = BaseServletTool.getParam(request);
		//String json ="{\"terminalType\":\"1\",\"equipNo\":\"\",\"area_id\":\"3\",\"sn\":\"866550020038086\",\"equipName\":\"小行\",\"staffId\":\"15903\",\"equipType\":\"703\"}";
		JSONObject jo = JSONObject.fromObject(json);
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String staffId = jo.getString("staffId");
		/*
		 * if (StringUtils.isBlank(staffId)) { return Result.returnCode("001");
		 * }
		 * 
		 * if (!OnlineUserListener.isLogin(staffId, sn)) { return
		 * Result.returnCode("002"); }
		 */

		String area_id = jo.get("area_id") == null ? "" : jo
				.getString("area_id");
		if (null == area_id || "".equals(area_id)) {
			return Result.returnCode("001");
		}
		// String area_id = "20";
		String equipType = jo.get("equipType") == null ? "" : jo
				.getString("equipType");
		String equipNo = jo.get("equipNo") == null ? "" : jo
				.getString("equipNo");
		// String equipNo="512CS.MAOWM/GJ001";
		String equipName = jo.get("equipName") == null ? "" : jo
				.getString("equipName");

		String jndi = cableInterfaceDao.getDBblinkName(area_id);
		if (null == jndi || "".equals(jndi)) {
			return Result.returnCode("001");
		}
		Map map = new HashMap();
		map.put("jndi", jndi);
		map.put("equipType", equipType);
		map.put("equipNo", equipNo);
		map.put("equipName", equipName);
		List<Map> resultmapList = new ArrayList<Map>();

		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			String s = SwitchDataSourceUtil.getCurrentDataSource();
			if ("703".equals(equipType) || "803".equals(equipType)) {
				resultmapList = cableInterfaceDao.getOSSEqpmentInfoByPhy(map);
			} else {
				resultmapList = cableInterfaceDao.getOSSEqpmentInfoByBse(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SwitchDataSourceUtil.clearDataSource();
		}

		JSONArray eqpInfoArray = new JSONArray();
		JSONObject eqpInfo = new JSONObject();
		for (Map resultmap : resultmapList) {
			eqpInfo.put("equipNo", resultmap.get("NO") == null ? "" : resultmap
					.get("NO"));
			eqpInfo.put("equipName", resultmap.get("NAME") == null ? ""
					: resultmap.get("NAME"));
			eqpInfo.put("adreess", resultmap.get("ADDRESS") == null ? ""
					: resultmap.get("ADDRESS"));
			eqpInfo.put("equipType", resultmap.get("EQUIPTYPE") == null ? ""
					: resultmap.get("EQUIPTYPE"));
			eqpInfo.put("area_id", resultmap.get("AREA_ID") == null ? ""
					: resultmap.get("AREA_ID"));
			eqpInfo.put("area_name", resultmap.get("AREA_NAME") == null ? ""
					: resultmap.get("AREA_NAME"));
			eqpInfo.put("tml_name", resultmap.get("TML_NAME") == null ? ""
					: resultmap.get("TML_NAME"));
			eqpInfoArray.add(eqpInfo);
		}
		result.put("result", "000");
		result.put("desc", "查询成功");
		result.put("equipInfos", eqpInfoArray);
		return result.toString();
	}

	@Override
	public String getNocTask(HttpServletRequest request) {

		JSONObject result = new JSONObject();

		String json = BaseServletTool.getParam(request);
		if (StringUtils.isEmpty(json)) {
			result.put("result", "001");
			result.put("tasks", "");
			return result.toString();
		}

		JSONObject jo = JSONObject.fromObject(json);
		String sn = jo.getString("sn");

		// 获取待办巡检任务ID
		String taskId = jo.getString("taskId");

		// 获取代办巡检任务下所有点信息及签到情况
		List<Map> allPoint = cableInterfaceDao.getAllPoint2(taskId);

		if (CollectionUtils.isEmpty(allPoint)) {
			result.put("result", "001");
			result.put("tasks", "");
			return result.toString();
		}

		JSONArray pointArray = new JSONArray();
		JSONObject pointJson = null;

		for (Map point : allPoint) {
			pointJson = new JSONObject();
			pointJson.put("point_id", StringUtil.objectToString(point
					.get("POINT_ID")));
			pointJson.put("task_detail_id", StringUtil.objectToString(point
					.get("TASK_DETAIL_ID")));
			pointJson.put("point_type", StringUtil.objectToString(point
					.get("POINT_TYPE_ID")));
			pointJson.put("point_name", StringUtil.objectToString(point
					.get("POINT_NAME")));
			pointJson.put("state", StringUtil.objectToString(point
					.get("COMPLETED")));
			pointJson.put("longitude", StringUtil.objectToString(point
					.get("LONGITUDE")));
			pointJson.put("latitude", StringUtil.objectToString(point
					.get("LATITUDE")));
			pointJson.put("inaccuracy", SysSet.CONFIG.get(AppType.LXXJ).get(
					SysSet.INACCURACY));
			pointJson.put("line_id", StringUtil.objectToString(point
					.get("LINE_ID")));
			pointArray.add(pointJson);
		}

		JSONObject taskJson = new JSONObject();
		taskJson.put("taskId", "");
		taskJson.put("taskName", "");
		taskJson.put("taskState", "");
		taskJson.put("county", "");
		taskJson.put("completeDate", "");
		taskJson.put("points", pointArray);

		result.put("result", "000");
		result.put("tasks", taskJson);
		return result.toString();
	}
	//非关键点到位计算
	public boolean arrivalNormalPoints(Map normal,List<Map> list2) {
		// 到位数量
		boolean flag = false;
		// 应到设备
		// int sums1=list1.size();
			Double px1 = Double.parseDouble(normal.get("LATITUDE").toString());
			Double py1 = Double.parseDouble(normal.get("LONGITUDE").toString());
			
			// 设备距离轨迹起点距离
			Double aa1 = 0d;
			// 设备距离轨迹终点距离
			Double aa2 = 0d;
			// 4分钟轨迹线段长度
			Double aa3 = 0d;
			// 轨迹距离关键点距离
			Double jul = 0d;
			//
			int inaccuracy = 50;// 签到误差距离
			int locate_span = Integer.valueOf(SysSet.CONFIG.get(AppType.LXXJ)
					.get(SysSet.LOCATE_SPAN));// 定位时间间隔
			int mid=0;
			
			Double px2 = 0.0;
			Double py2 = 0.0;
			Double px3 = 0.0;
			Double py3 = 0.0;
			for (int i = 0; i < list2.size() - 1; i++) {
				px2=Double.parseDouble(list2.get(i).get("LATITUDE").toString());
				py2=Double.parseDouble(list2.get(i).get("LONGITUDE").toString());
				px3=Double.parseDouble(list2.get(i + 1).get("LATITUDE").toString());
				py3=Double.parseDouble(list2.get(i + 1).get("LONGITUDE").toString());
				//如果上传两个连续相同的点，则继续遍历下一个点
				if(px2==px3&&py2==py3)
				{
					continue;
				}
				aa1 = MapDistance.getDistance(px1, py1,px2,py2);
				aa2 = MapDistance.getDistance(px1, py1,px3,py3);
				aa3 = MapDistance.getDistance(px2,py2,px3,py3);

				// 判断时速有没有超过20
				if ((aa3 / 1000) / (locate_span / 3600) > 20) {
					//continue;
				}
				
				// 若其中有一边小于50米，则改点到位
				if (aa1 <= inaccuracy || aa2 <= inaccuracy) {
					flag = true;
					break;
				}
				/*if ((aa1 > aa2 && aa1 > aa3) || (aa2 > aa1 && aa2 > aa3)) {
					// 距离关键点距离jul为最短边 aa2或aa1上面已判断过都大于50米
				} */
				if(aa3 < 0000000.1 || (aa2 * aa2 + aa3 * aa3 - aa1 * aa1)/(2*aa2*aa3)<0 ||
						(aa1 * aa1 + aa3 * aa3 - aa2 * aa2)/(2*aa1*aa3)<0)
				{
					continue;
				}else {
					jul = new Triangle(aa1, aa2, aa3).getArea() * 2 / aa3;
					if (jul < inaccuracy) {
						flag = true;
						break;
					}
				}
			}
		return flag;
	}

	@Override
	public String queryTroubleBills(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String terminalType = jo.getString("terminalType");
		String staffId = jo.getString("staff_id");
//		String longitude = jo.getString("longitude");
//		String latitude = jo.getString("latitude");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}
		try {
			Map params = new HashMap();
			Map staff = cableInterfaceDao.getAreaByStaffId(staffId);
			List<Map<String,String>> list=roleDao.getAllByStaffId(staffId);
			for (Map<String, String> map : list) {
				if(map.get("ROLE_NO").equals("LXXJ_ADMIN_SON_AREA")||
						map.get("ROLE_NO").equals("LXXJ_AUDITOR")){
					params.put("SON_AREA_ID", staff.get("SON_AREA_ID"));
				}else if(map.get("ROLE_NO").equals("LXXJ_ADMIN_AREA")||
						map.get("ROLE_NO").equals("LXXJ_ADMIN")){
					params.put("AREA_ID", staff.get("AREA_ID"));
				}else{
					params.put("SON_AREA_ID", staff.get("SON_AREA_ID"));
				}
			}
			if(params.containsKey("SON_AREA_ID")&&params.containsKey("AREA_ID")){
				params.remove("SON_AREA_ID");
			}
			List<Map> bills = cableInterfaceDao.billQuery(params);
			List<Map> tasks = new ArrayList();
			for (int i = 0; i < bills.size(); i++) {
				Map map = new HashMap();
				map.put("bill_id", bills.get(i).get("BILL_ID"));
				map.put("point_no", bills.get(i).get("POINT_NO"));
				map.put("point_name", bills.get(i).get("POINT_NAME"));
				map.put("status", bills.get(i).get("STATUS_NAME"));
				map.put("descrp", bills.get(i).get("DESCRP"));
				map.put("staff_id", bills.get(i).get("INSPECTOR")==null?"":bills.get(i).get("INSPECTOR"));
//				map.put("inspector", bills.get(i).get("INSPECTOR")==null?"":bills.get(i).get("INSPECTOR"));
				map.put("maintor", bills.get(i).get("MAINTOR")==null?"":bills.get(i).get("MAINTOR"));
				map.put("auditor", bills.get(i).get("AUDITOR")==null?"":bills.get(i).get("AUDITOR"));
				map.put("create_time", bills.get(i).get("CREATE_TIME"));
				map.put("zone_name", bills.get(i).get("SON_AREA")==null?"":bills.get(i).get("SON_AREA"));
				tasks.add(map);
			}
			result.put("tasks", tasks);
			result.put("result", "000");
		} catch (Exception e) {
			result.put("tasks", "");
			result.put("result", "001");
		}	
		return result.toString();
	}

	@Override
	public String queryTroubleBillDetail(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staff_id");
		String terminalType = jo.getString("terminalType");
//		String longitude = jo.getString("longitude");
//		String latitude = jo.getString("latitude");
		String bill_id = jo.getString("bill_id");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}

		Map<String, String> billMap = pointDao.billInfo(bill_id);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("BILL_ID", bill_id);
		// 获取工单关联隐患上报记录id
		params.put("RECORD_TYPE", 1);
		List<Map<String, Object>> recordList = pointDao
				.getRecordByBillId(params);
		//临时上报也带出来
		params.put("RECORD_TYPE", 4);
		recordList.addAll(pointDao.getRecordByBillId(params));
		List<Map<String, String>> photoList = new ArrayList();
		List<Map<String, String>> photoList1 =new ArrayList();
		int num = recordList.size();
		if(recordList.size()!=0){
			params.put("url", "61.160.128.47");
			params.put("RECORD_ID", StringUtil.objectToString(recordList
					.get(0).get("RECORD_ID")));
			photoList = pointDao.billPhoto(params);
			for (Map<String, String> map : photoList) {
				map.put("photo_path", map.get("PHOTO_PATH")==null?"":map.get("PHOTO_PATH"));
				map.put("micro_photo_path", map.get("MICRO_PHOTO_PATH")==null?"":map.get("MICRO_PHOTO_PATH"));
				map.remove("PHOTO_PATH");
				map.remove("MICRO_PHOTO_PATH");
			}
			result.put("before", photoList);
			// 获取隐患政治的上报记录id
			params.put("RECORD_TYPE", 2);
			params.put("url", "61.160.128.47");
			recordList = pointDao.getRecordByBillId(params);
			if (recordList != null && recordList.size() > 0
					&& recordList.get(0) != null
					&& recordList.get(0).get("RECORD_ID") != null) {
				params.put("RECORD_ID", recordList.get(0).get("RECORD_ID").toString());
				photoList1 = pointDao
						.billPhoto(params);
				for (Map<String, String> map : photoList1) {
					map.put("photo_path", map.get("PHOTO_PATH")==null?"":map.get("PHOTO_PATH"));
					map.put("micro_photo_path", map.get("MICRO_PHOTO_PATH")==null?"":map.get("MICRO_PHOTO_PATH"));
					map.remove("PHOTO_PATH");
					map.remove("MICRO_PHOTO_PATH");
				}
				result.put("after", photoList1);
			}
		}else{
//			Map m = new HashMap();
//			m.put("photo_path", "");
//			m.put("micro_photo_path", "");
//			photoList1.add(m);
//			photoList.add(m);
			result.put("before", photoList);
			result.put("after", photoList1);
		}
		
		result.put("bill_id", bill_id);
		result.put("point_no", billMap.get("POINT_NO")==null?"":billMap.get("POINT_NO"));
		result.put("point_name", billMap.get("POINT_NAME")==null?"":billMap.get("POINT_NAME"));
		result.put("type", billMap.get("TYPE_NAME")==null?"":billMap.get("TYPE_NAME"));
		result.put("descrp", billMap.get("DESCRP")==null?"":billMap.get("DESCRP"));
		result.put("staff_name", billMap.get("STAFF_NAME")==null?"":billMap.get("STAFF_NAME"));
		result.put("maintor", billMap.get("MAINTOR")==null?"":billMap.get("MAINTOR"));
		result.put("auditor", billMap.get("AUDITOR")==null?"":billMap.get("AUDITOR"));
		result.put("create_time", billMap.get("CREATE_TIME")==null?"":billMap.get("CREATE_TIME"));
		result.put("area_name", billMap.get("AREA")==null?"":billMap.get("AREA"));
		result.put("zone_name", billMap.get("SON_AREA")==null?""	:billMap.get("SON_AREA"));
		result.put("cable_name", billMap.get("CABLE_NAME")==null?"":billMap.get("CABLE_NAME"));
		result.put("cable_level", billMap.get("CABLE_LEVEL")==null?"":billMap.get("CABLE_LEVEL"));
		result.put("cons_unit", billMap.get("CONS_UNIT")==null?"":billMap.get("CONS_UNIT"));
		result.put("cons_content", billMap.get("CONS_CONTENT")==null?"":billMap.get("CONS_CONTENT"));
		result.put("status_name", billMap.get("STATUS_NAME")==null?"":billMap.get("STATUS_NAME"));
		result.put("longitude", billMap.get("LONGITUDE")==null?"":billMap.get("LONGITUDE"));
		result.put("latitude", billMap.get("LATITUDE")==null?"":billMap.get("LATITUDE"));
		result.put("point_id", String.valueOf(billMap.get("POINT_ID")));
		result.put("task_id", String.valueOf(billMap.get("TASK_ID"))==null?"":String.valueOf(billMap.get("TASK_ID")));
		
		result.put("result", "000");
		return result.toString();
	}

	@Override
	public String troubleBillOperation(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staff_id");
		String terminalType = jo.getString("terminalType");
		String bill_id = jo.getString("bill_id");
		String work_type = jo.getString("work_type");
		String complete_time = jo.getString("complete_time");
		String inspector = jo.getString("inspector");
		String area_id = jo.getString("area_id");
		String son_area_id = jo.getString("sonareaid");
		String point_id = jo.getString("pointid");
		List<Map<String,String>> list=roleDao.getAllByStaffId(staffId);
		String eqpId="";
		String remarks="";
//		String ids = jo.getString("ids");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}
		boolean role=true;
		result.put("work_type", work_type);
		for (Map<String, String> map : list) {
			if(map.get("ROLE_NO").equals("LXXJ_ADMIN_SON_AREA")||
					map.get("ROLE_NO").equals("LXXJ_ADMIN_AREA")||
					map.get("ROLE_NO").equals("LXXJ_ADMIN")||
					map.get("ROLE_NO").equals("LXXJ_AUDITOR")){
				role=false;
			}
		}
		if(!("").equals(work_type)||null!=work_type){
			int type = Integer.parseInt(work_type);
			//1.派发,2.审核,3.派发时选择获得人员列表,4.回单,5.退单,6.审核不通过,7.转派,8.归档
			Map params = new HashMap();
			params.put("BILL_IDS", bill_id);
			params.put("STAFF_ID", staffId);
			Map<String, Object> flowParams = new HashMap<String, Object>();
			result.put("staffs", "");
			try {
			switch (type) {
			case 1:
				String name = jo.getString("name");
				flowParams.put("OPERATOR", staffId);
				flowParams.put("RECEIVOR", inspector);
				if(pointDao.getBillStatus(bill_id)!=1){
					result.put("result", "001");
					result.put("desc", "工单不可派发");
					return result.toString();
				}
				if(role){
					result.put("result", "001");
					result.put("desc", "您不是审核员");
					return result.toString();
				}
				Map<String,String> workMap = new HashMap<String,String>();
				Map staffinfo = new HashMap();
				if(null!=inspector&&!("").equals(inspector)){
					staffinfo=pointDao.workStaffInfo(inspector);
				}
				WfworkitemflowSoap11BindingStub  wfworkitemflowSoap11BindingStub;
				Wfworkitemflow wfworkitemflowLocator = new WfworkitemflowLocator();
				
				try {
				wfworkitemflowSoap11BindingStub=(WfworkitemflowSoap11BindingStub) wfworkitemflowLocator.getWfworkitemflowHttpSoap11Endpoint();
				wfworkitemflowSoap11BindingStub.setTimeout(30 * 1000);
				String interresult="";
				String param="";
				Document doc;
				Element root = null;
				Map post = new HashMap();
				Map<String,String> pointCood = new HashMap<String,String>();
					eqpId=pointDao.decodeEqyTypeByBill(bill_id);
					if(!eqpId.equals("0")){
						pointCood = pointDao.getCoodByBillId(bill_id);
						remarks=pointDao.getRemarksById(bill_id)==null?"":pointDao.getRemarksById(bill_id);
						params.put("url", "61.160.128.47");
						params.put("BILL_ID", bill_id);
						// 获取工单关联隐患上报记录id
						params.put("RECORD_TYPE", 1);
						List<Map<String, Object>> recordList = pointDao
						.getRecordByBillId(params);
						params.put("RECORD_TYPE", 4);
						recordList.addAll(pointDao.getRecordByBillId(params));
						params.put("RECORD_ID", StringUtil.objectToString(recordList
								.get(0).get("RECORD_ID")));
						List<Map<String, String>> photoList = pointDao.billPhoto(params);
						String photo_in="";
						String photo_out="";
						if(photoList!=null&&photoList.size()!=0){
							for (Map<String, String> map : photoList) {
								photo_out=photo_out+","+map.get("PHOTO_PATH");
							}
							photo_in=photo_out.replace("61.160.128.47", "132.228.237.107");
							photo_in=photo_in.substring(1,photo_in.length());
							photo_out=photo_out.substring(1,photo_out.length());
						}
						param="<?xml version=\"1.0\" encoding=\"gb2312\"?>"
							   +"<IfInfo>"
							   +"<sysRoute>XJXT</sysRoute>"
							   +"<taskType>4</taskType>"
							   +"<xjMan>"+name+"</xjMan>"
							   +"<taskId>"+bill_id+"</taskId>"
							   +"<TaskType>"+eqpId+"</TaskType>"
							   +"<xjManAccount>"+inspector+"</xjManAccount>"
							   +"<XjContent>"+remarks+"</XjContent>"
							   +"<picture_outSys>"+photo_out+"</picture_outSys>"
							   +"<picture_inSys>"+photo_in+"</picture_inSys>"
							   +"<longitude>"+pointCood.get("LONGITUDE")+"</longitude>"
							   +"<latitude>"+pointCood.get("LATITUDE")+"</latitude>"
							   +"</IfInfo>";
						interresult=wfworkitemflowSoap11BindingStub.outSysDispatchTask(param);
						post.put("get", interresult);
						post.put("post", param);
						post.put("type", 1);
						if(interresult.length()>1){
							interresult=interresult.replace("<?xml version=\"1.0\" encoding=\"gb2312\"?>", "");
							doc= DocumentHelper.parseText(interresult);
							root = doc.getRootElement();
							post.put("flag", root.element("IfResult").getText());
						}else{
							post.put("flag", 1);
						}
						params.put("STATUS_ID", 4);
						pointDao.add_a_post(post);
						//修改工单状态
							params.put("STATUS_ID", 9);
							params.put("MAINTOR", inspector);
							params.put("AUDITOR", staffId);
							params.put("COMPLETE_TIME", complete_time);
							pointDao.updateBillHandle(params);
						result.put("result", "000");
						result.put("desc", "已经派发至集约化平台");
					}else{//正常工单派发
//						params.put("STATUS_ID", 2);
//						params.put("MAINTOR", inspector);
//						params.put("AUDITOR", staffId);
//						params.put("COMPLETE_TIME", complete_time);
//						pointDao.updateBillHandle(params);
//						result.put("result", "000");
//						result.put("desc", "已经派发至缆线巡检");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}catch (ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				List phoneNumList = new ArrayList();
				List messageContentList = new ArrayList();
				if(eqpId.equals("0")){
					flowParams.put("BILL_ID", bill_id);
					flowParams.put("STATUS", 2);
					flowParams.put("OPERATE_INFO", "派发工单");
				}else{
					flowParams.put("BILL_ID", bill_id);
					flowParams.put("STATUS", 9);
					flowParams.put("OPERATE_INFO", "集约工单");
				}
				pointDao.insertBillFlow(flowParams);

				// 隐患工单派发发送短信
				try {
					// 获取人员的信息
					Map<String, Object> staffInfo = staffDao.getStaff(inspector);
					Map<String, String> taskInfo = pointDao.billInfo(bill_id);
					String equipCode = taskInfo.get("POINT_NO").toString();
					// 发送短信内容
					String messageText = "";
					if (staffInfo.get("STAFF_NAME") != null
							&& null != staffInfo.get("TEL")
							&& !"".equals(staffInfo.get("TEL"))) {
						messageText = staffInfo.get("STAFF_NAME") + "，您好。您于"
								+ DateUtil.getCurrentTime() + "收到" + equipCode
								+ "任务，任务截止时间:" + complete_time
								+ "，请及时执行。【分公司缆线巡检】";
						phoneNumList.add(staffInfo.get("TEL"));
						messageContentList.add(messageText);
					}
				}

				catch (Exception e) {
					//e.printStackTrace();
				}
				// 发送短信
				//SendMessageUtil.sendMessageList(phoneNumList,
				//		messageContentList);
				
			break;
			case 2:
				int stat=pointDao.getBillStatus(bill_id);
				if(role){
					result.put("result", "001");
					result.put("desc", "您不是审核员");
					return result.toString();
				}
				if(stat==3||stat==1){
					params.put("STATUS_ID", 4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					params.put("COMPLETE_TIME", sdf.format(new Date()));
					pointDao.updateBillHandle(params);

					Map<String, Object> ptype = pointDao.getPointTypeByids(bill_id);
					String eqpType = String.valueOf(ptype.get("eqp_type_id"));
					if (eqpType != null && !"".equals(eqpType)) {
						pointDao.updatePointType(ptype);
					}
					flowParams.put("BILL_ID", bill_id);
					flowParams.put("STATUS", 4);
					flowParams.put("OPERATE_INFO", "审核通过，归档");
					flowParams.put("OPERATOR", staffId);
					flowParams.put("RECEIVOR", "");
					pointDao.insertBillFlow(flowParams);
				}else{
					result.put("result", "001");
					result.put("desc", "工单无法审核");
					return result.toString();
				}
			break;
			case 3:
				if(pointDao.getBillStatus(bill_id)!=1){
					result.put("result", "001");
					result.put("desc", "工单不可派发");
				}
				Map map = pointDao.getAreaByBillId(bill_id);
				eqpId=pointDao.decodeEqyTypeByBill(bill_id);
				List<Map> staffs = new ArrayList();
				map.put("ROLE_NO", "LXXJ_MAINTOR");
				map.put("GRID_NAME", jo.getString("grid_name"));
				if(null!=eqpId&&!eqpId.equals("0")){
					map.put("AREA_ID", pointDao.getSonAreaByBillId(bill_id));
					try{
					SwitchDataSourceUtil.setCurrentDataSource("orcl153");
					staffs = pointDao.queryHandlerFromJYH(map);
					SwitchDataSourceUtil.clearDataSource();
					} catch (Exception e) {
						SwitchDataSourceUtil.clearDataSource();
					}
				}else{
					staffs = pointDao.queryHandler(map);
				}
				for (Map map2 : staffs) {
					map2.put("staff_id", map2.get("STAFF_ID"));
					map2.put("staff_name", map2.get("STAFF_NAME"));
					map2.put("grid_name", map2.get("GRID_NAME"));
					map2.remove("STAFF_ID");
					map2.remove("STAFF_NAME");
					map2.remove("GRID_NAME");
				}
				result.put("staffs", staffs);
			break;
			case 4:
				String longitude = jo.getString("longitude");
				String latitude = jo.getString("latitude");
				String afterChangePhotoIds = jo.getString("photoids");
				params.put("STATUS_ID", 3);
				pointDao.updateBillHandle(params);
				flowParams.put("BILL_ID", bill_id);
				flowParams.put("STATUS", 3);
				flowParams.put("OPERATE_INFO", "回单操作");
				flowParams.put("RECEIVOR", pointDao.getAuditorByBillId(bill_id));
				flowParams.put("OPERATOR", staffId);
				pointDao.insertBillFlow(flowParams);
				flowParams.put("ISKEEP", 2);
				flowParams.put("RECORD_TYPE", 2);
				flowParams.put("POINT_ID", point_id);
				flowParams.put("TASK_ID", "");
				flowParams.put("TASK_DETAIL_ID", "");
				flowParams.put("PLAN_ID", "");
				flowParams.put("INSPECTOR", inspector);
				flowParams.put("TROUBLE_IDS", "");
				flowParams.put("DESCRP","");
				flowParams.put("LONGITUDE", longitude);
				flowParams.put("LATITUDE", latitude);
				flowParams.put("CABLE_NAME", "");
				flowParams.put("CABLE_LEVEL", "");
				flowParams.put("CONS_UNIT", "");
				flowParams.put("CONS_CONTENT", "");
				flowParams.put("AREA_ID", area_id);
				flowParams.put("SON_AREA_ID", son_area_id);
				flowParams.put("EQUIP_NAME", "");
				flowParams.put("EQUIP_CODE", "");
				flowParams.put("EQUIP_TYPE", "");
				flowParams.put("IS_FIX", "");
				flowParams.put("TERMINAL_TYPE", 1);
				flowParams.put("KEEPRADIUS", "");
				flowParams.put("MNT_LEVEL_ID", "");
				cableInterfaceDao.saveRecord(flowParams);
				if(!"".equals(afterChangePhotoIds)){
					JSONArray arr = JSONArray.fromObject(jo.get("photoids"));
					for (int i = 0; i < arr.size(); i++) {
						flowParams.put("PHOTO_ID", arr.get(i).toString());
						cableInterfaceDao.insertPhotoRel(flowParams);
					}
				}
			break;
			case 5:
				if(role){
					result.put("result", "001");
					result.put("desc", "您不是审核员");
					return result.toString();
				}
				params.put("STATUS_ID", 5);
				pointDao.updateBillHandle(params);
				flowParams.put("BILL_ID", bill_id);
				flowParams.put("STATUS", 5);
				flowParams.put("OPERATE_INFO", "退单操作，原因：");
				//(new String(remarks.getBytes("iso-8859-1"),"gb2312"))
				flowParams.put("RECEIVOR", pointDao.getAuditorByBillId(bill_id));
				flowParams.put("OPERATOR", staffId);
				pointDao.insertBillFlow(flowParams);
			break;
			case 6:
				if(role){
					result.put("result", "001");
					result.put("desc", "您不是审核员");
					return result.toString();
				}
				if(pointDao.getBillStatus(bill_id)!=2){
					result.put("result", "001");
					result.put("desc", "工单无法审核");
				}
				params.put("STATUS_ID", 2);
				pointDao.updateBillHandle(params);
				flowParams.put("BILL_ID", bill_id);
				flowParams.put("STATUS", 2);
				flowParams.put("OPERATE_INFO", "审核不通过，重新维护");
				flowParams.put("RECEIVOR", pointDao.getAuditorByBillId(bill_id));
				flowParams.put("OPERATOR", staffId);
				pointDao.insertBillFlow(flowParams);
			break;
			case 7:
				if(role){
					result.put("result", "001");
					result.put("desc", "您不是审核员");
					return result.toString();
				}
				params.put("STATUS_ID", 1);
				params.put("AUDITOR", inspector);
				pointDao.updateBillHandle(params);
				flowParams.put("BILL_ID", bill_id);
				flowParams.put("STATUS", 1);
				flowParams.put("OPERATE_INFO", "转发工单");
				flowParams.put("RECEIVOR", pointDao.getAuditorByBillId(bill_id));
				flowParams.put("OPERATOR", staffId);
				pointDao.insertBillFlow(flowParams);
			break;
			case 8:
				if(role){
					result.put("result", "001");
					result.put("desc", "您不是审核员");
					return result.toString();
				}
				params.put("STATUS_ID", 4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				params.put("COMPLETE_TIME", sdf.format(new Date()));
				pointDao.updateBillHandle(params);
				flowParams.put("BILL_ID", bill_id);
				flowParams.put("STATUS", 4);
				flowParams.put("OPERATE_INFO", "归档");
				flowParams.put("RECEIVOR", pointDao.getAuditorByBillId(bill_id));
				flowParams.put("OPERATOR", staffId);
				cableInterfaceDao.updatePointTypeByBillId(bill_id);
				pointDao.insertBillFlow(flowParams);
			break;
			}
			result.put("result", "000");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("result", "001");
			}
		}
		return result.toString();
	}

	@Override
	public String queryOnLineMembers(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staff_id");
		String terminalType = jo.getString("terminalType");
		String longitude = jo.getString("longitude");
		String latitude = jo.getString("latitude");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}
		Map map = new HashMap();
		map.put("staff_id", staffId);
		try {
			List<Map> staffs = cableInterfaceDao.queryOnLineMembers(map);
			for (Map map2 : staffs) {
				map2.put("staff_id", map2.get("UPLOAD_STAFF"));
				map2.put("staff_name", map2.get("STAFF_NAME"));
				map2.put("longitude", map2.get("LONGITUDE"));
				map2.put("latitude", map2.get("LATITUDE"));
				map2.put("dept_name", map2.get("DEPT_NAME"));
				map2.remove("STAFF_ID");
				map2.remove("STAFF_NAME");
				map2.remove("LONGITUDE");
				map2.remove("LATITUDE");
				map2.remove("DEPT_NAME");
			}
			result.put("staffs",staffs );
			result.put("result", "000");
		} catch (Exception e) {
			result.put("result", "001");
		}
		return result.toString();
	}

	@Override
	public String signKeepPoint(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staff_id");
		String task_id = jo.getString("task_id");
		String terminalType = jo.getString("terminalType");
		String longitude = jo.getString("longitude");
		String latitude = jo.getString("latitude");
		String sign_type = jo.getString("sign_type");//1.开始看护;2.结束看护
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}
		Map params = new HashMap();
		params.put("staff_id", staffId);
		params.put("task_id", task_id);
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		params.put("sign_type", sign_type);
		params.put("locate_span", "");
		Double distance=0.00;
		try {
			List<Map> PlanPoints=cableInterfaceDao.getKeepPoints(params);
			boolean flag = true;
			for (Map map : PlanPoints) {
				distance =MapDistance.getDistance(Double.valueOf(map.get("LATITUDE").toString()),
						Double.valueOf(map.get("LONGITUDE").toString()),
						Double.valueOf(latitude),
						Double.valueOf(longitude));
				if(distance<800){
					flag=false;
				}		
			}
			if(flag){
				result.put("result", "001");
				result.put("desc","不在看护范围，无法签到");
				return result.toString();
			}
			String keep = new String();
			keep = cableInterfaceDao.ifPointCanInsert(params);
			if(keep==null&&sign_type.equals("1")){
				cableInterfaceDao.addKeepPoint(params);
				result.put("result", "000");
				result.put("desc","签到成功");
			}else if(keep!=null&&sign_type.equals("2")&&keep.equals("1")){
				String locate_span = cableInterfaceDao.calculateTimeCost(params);
				params.put("locate_span", locate_span);
				cableInterfaceDao.addKeepPoint(params);
				result.put("result", "000");
				result.put("desc","签到成功");
			}else if(sign_type.equals("1")&&keep.equals("2")){
				cableInterfaceDao.addKeepPoint(params);
				result.put("result", "000");
				result.put("desc","签到成功");
			}else{
				result.put("result", "001");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
		}
		return result.toString();
	}
	
	/**
	 * 获取检查项接口
	 */
	@Override
	public String getCheckCondition(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			return result.toString();
//		}
		try {
			
			List<Map<String, Object>> item = cableInterfaceDao.getCheckCondition();
			List items = new ArrayList<Map<String, Object>>();
			if(item.size()>0){
				for (int i = 0; i < item.size(); i++) {
					Map<String, Object> itemMap = item.get(i);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("item_id", itemMap.get("ITEM_ID").toString());
					params.put("word", itemMap.get("ITEM_NAME").toString());
					items.add(params);
				}
			}
			result.put("items", items);
			result.put("result", "000");
		} catch (Exception e) {
			result.put("result", "001");
		}
		return result.toString();
	}
	
	/**
	 * 获取隐患类型接口
	 */
	@Override
	public String getTroubleType(HttpServletRequest request) {
//		String para= "{\"terminalType\":\"1\",\"sn\":\"11111\",\"staffId\":\"11111\"}";
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		if(null!=staffId&&!("").equals(staffId)){
//			if (!OnlineUserListener.isLogin(staffId, sn)) {
//				result.put("result", "002");
//				return result.toString();
//			}
		}
		try {
			String son_area_id = "";
			String son_area_name= "";
			//获取当前登录人所在子区域id
			if(null!=staffId&&!("").equals(staffId)){
			Map<String,Object> son_area = cableInterfaceDao.getStaffSonAreaID(staffId);
			if(!son_area.isEmpty()){
				son_area_id = son_area.get("SON_AREA_ID").toString();
				son_area_name = son_area.get("NAME").toString();
			}
			}
			List<Map<String, Object>> type = cableInterfaceDao.getTroubleType();
			List types = new ArrayList<Map<String, Object>>();
			if(type.size()>0){
				for (int i = 0; i < type.size(); i++) {
					Map<String, Object> typeMap = type.get(i);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("type_id", typeMap.get("TYPE_ID").toString());
					params.put("type_name", typeMap.get("TYPE_NAME").toString());
					params.put("son_area_id", son_area_id);
					params.put("son_area_name", son_area_name);
					types.add(params);
				}
			}
			result.put("types", types);
			result.put("result", "000");
		} catch (Exception e) {
			result.put("result", "001");
		}
		return result.toString();
	}

	/**
	 * 查询光缆或光缆段是否存在
	 */
	@Override
	public String queryCableExits(HttpServletRequest request) {
    	//String json= "{\"queryNo\":\"0\",\"queryType\":\"0\",\"son_area_id\":\"21\"}";
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");//登录者ID
	    String terminalType  = jo.getString("terminalType");//终端类型
		String sn = jo.getString("sn");//手机识别码
		String queryNo = jo.getString("queryNo");//光缆编码
		String queryType = jo.getString("queryType");//0:光缆，1，光缆段（中继段）
		String son_area_id = jo.getString("son_area_id");//子区域ID
		
		/*//网格ID,编码,名称,等级
		String grid_id = jo.getString("grid_id");
		String grid_no = jo.getString("grid_no");
		String grid_name = jo.getString("grid_name");
		String level = jo.getString("level");*/
	
		String line_id = "";
		if(jo.containsKey("line_id")){
			line_id = jo.getString("line_id");//光缆ID，查询光缆段时候传来的
		}
//	    if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			return result.toString();
//		}
		try {
			Map<String, Object> param = new HashMap<String, Object>();	
			param.put("queryNo",queryNo);
		    int queryTypeNum = Integer.parseInt(queryType);
		    if(queryTypeNum == 0){
		    	param.put("queryType","");
		    }else{
		    	param.put("queryType",queryType);
		    }
			param.put("son_area_id",son_area_id);
			param.put("parent_line_id",line_id);

			/*param.put("grid_id",grid_id);
			param.put("grid_no",grid_no);
			param.put("grid_name",grid_name);
			param.put("level",level);*/			
			//查询是否存在
			List<Map<String, Object>> type = cableInterfaceDao.queryCableExits(param);
			
			/*if("0".equals(queryType)){
				type = cableInterfaceDao.queryCableExits(param);
			}else{
				type = cableInterfaceDao.queryCablesExits(param);
			}*/
			
			//如果查询系统的光缆不存在，则取OSS查询中继光缆
			if(queryTypeNum==0 && type.size() == 0  && !"".equals(queryNo))
			{
				//根据son_area_id查询出对于的area_id
				String area_id = cableInterfaceDao.getAreaIdBySonAreaId(son_area_id);
				if (null == area_id || "".equals(area_id)) {
					result.put("result", "001");
					result.put("desc", "查询参数出错");
				}
				String jndi = cableInterfaceDao.getDBblinkName(area_id);
				if (null == jndi || "".equals(jndi)) {
					result.put("result", "001");
					result.put("desc", "查询参数出错");
				}
				Map map = new HashMap();
				map.put("jndi", jndi);
				map.put("queryNo", queryNo);
				List<Map> resultmapList = new ArrayList<Map>();

				try {
					SwitchDataSourceUtil.setCurrentDataSource(jndi);
					//根据编码去OSS模糊匹配中继光缆编码
						resultmapList = cableInterfaceDao.queryOSSCableByNo(map);
						SwitchDataSourceUtil.clearDataSource();
						List types = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < resultmapList.size(); i++) {
							Map<String, Object> resultmap = resultmapList.get(i);
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("id", "");
							params.put("no", resultmap.get("LINE_NO").toString());
							params.put("name", resultmap.get("LINE_NAME").toString());
							params.put("son_area_id", "");
							params.put("grid_id","");
							params.put("grid_no","");
							params.put("grid_name","");
							params.put("level","");	
							types.add(params);
						}
						result.put("lines", types);
						result.put("result", "000");
						result.put("desc", "查询成功");
				} catch (Exception e) {
					e.printStackTrace();
					result.put("result", "001");
					result.put("desc", "查询出错");
				} finally {
					SwitchDataSourceUtil.clearDataSource();
				}
				
			}
			//从系统获取
			else{
					List types = new ArrayList<Map<String, Object>>();
					if(type.size()>0){
						for (int i = 0; i < type.size(); i++) {
							Map<String, Object> typeMap = type.get(i);
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("id", typeMap.get("LINE_ID").toString());
							params.put("no", typeMap.get("LINE_NO").toString());
							params.put("name", typeMap.get("LINE_NAME").toString());
							params.put("son_area_id", typeMap.get("SON_AREA_ID").toString());
							
							params.put("grid_id",null!=typeMap.get("DEPT_ID")?typeMap.get("DEPT_ID").toString():"");
							params.put("grid_no",null!=typeMap.get("DEPT_NO")?typeMap.get("DEPT_NO").toString():"");
							params.put("grid_name",null!=typeMap.get("DEPT_NAME")?typeMap.get("DEPT_NAME").toString():"");
							params.put("level",null!=typeMap.get("LINE_LEVEL")?typeMap.get("LINE_LEVEL").toString():"");	
							
							types.add(params);
						}
					}
					result.put("lines", types);
					result.put("result", "000");
					result.put("desc", "查询成功");
			}
		} catch (Exception e) {
			result.put("result", "001");
			result.put("desc", "查询出错");
		}
		
		return result.toString();
	}

	 //采集光缆坐标  新增光缆等级默认选3  保存时候光缆类型都选0
	@Override
	public String cableCoordinateRecord(HttpServletRequest request) {
//		String para= "{\"staffId\":\"15339\",\"cableId\":\"9397\",\"cableNo\":\"方俊松\",\"sn\":\"867389021080725\"" +
//				",\"cableName\":\"方俊松\",\"sectId\":\"9398\",\"terminalType\":\"1\"" +
//				",\"sectNo\":\"方俊松段\",\"sectName\":\"方俊松段\"" +
//				",\"grid_id\":\"10006\",\"grid_no\":\"厂家测试\",\"grid_name\":\"厂家班组\",\"level\":\"1\""+
//				",\"son_area_id\":\"9\",\"area_id\":\"3\"" +
//				",\"pointNo\":\"方俊松_方俊松段_20177201733\",\"pointName\":\"方俊松_方俊松段_20177201733\",\"pointType\":\"-1\"" +
//				",\"eqpType\":\"1000\",\"longitude\":\"118.74836\",\"latitude\":\"31.986635\"}";
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
//		JSONObject jo = JSONObject.fromObject(para);
		String staffId = jo.getString("staffId");//登录者ID
		String terminalType  = jo.getString("terminalType");//终端类型
		String sn = jo.getString("sn");//手机识别码
		
		String cableId = jo.getString("cableId");//光缆ID
		String cableNo = jo.getString("cableNo");//光缆编号
		String cableName = jo.getString("cableName");//光缆名称
		//713新增
		String line_level=jo.getString("level");//光缆类型:1.中继,2.主干
		String dept_no=jo.getString("grid_no");
		
		String sectId = jo.getString("sectId");//中继段ID
		String sectNo = jo.getString("sectNo");//中继段编码
		String sectName = jo.getString("sectName");//中继段名称
		String son_area_id = jo.getString("son_area_id");//子区域ID
		String area_id = jo.getString("area_id");//区域ID
		
		String pointNo = jo.getString("pointNo");//点编码
		String pointName = jo.getString("pointName");//点名称
		String pointType = jo.getString("pointType");//-1、非关键点，1，关键点，4，设备点(必填)
		String eqpType = jo.getString("eqpType");//pointType当为设备点时，需要填写设备类型
		String longitude = jo.getString("longitude");//经度
		String latitude = jo.getString("latitude");//纬度
		String line="";
		Map<String,String> dmap = new HashMap();
		dmap.put("normalD", "200");
		dmap.put("keyD", "500");
		dmap.put("longitude", longitude);
		dmap.put("latitude", latitude);
		dmap.put("pointType", pointType);
		String pointIdForupdate="";
		if(null!=pointNo&&!("").equals(pointNo)){
			pointIdForupdate=cableInterfaceDao.checkPointExists(pointNo);
		}
		dmap.put("point_id", pointIdForupdate);
		if (!OnlineUserListener.isLogin(staffId, sn)) {
			result.put("result", "002");
			result.put("desc", "重复登录");
			return result.toString();
		}
		
		try {
	//	   1.有光缆ID，无光缆段ID  需要根据光缆段编号去查询光缆段(精确查询)，查询到两条数据返回该光缆下提示有两条
	//	        相同名称光缆段，没有就新建光缆段。然后在光缆段ID下面插入新增点以及新增连线关系
			if(!StringUtil.isEmpty(cableId) && StringUtil.isEmpty(sectId)){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("cableId",cableId);
				param.put("line_no",sectNo);
				param.put("son_area_id",son_area_id);
				List<Map<String, Object>> paramList = cableInterfaceDao.selIsHasLine(param);//查询是否有光缆段
				if(paramList.size()>1){//查出多条光缆段
					result.put("result","112");
					result.put("desc","出现多条重复光缆段编号,请重新输入光缆段编号或者联系支撑人员调整");
					return result.toString();
				}else if(paramList.size() == 1){//有就以这条为基准去进行插入操作
					Map<String, Object> passageMap = paramList.get(0);
					String passage_id = passageMap.get("LINE_ID").toString();
					line=passage_id;
					String maxOder = cableInterfaceDao.getMaxOrder(passage_id);//查询中间表最大的连线顺序+1
					String point_id = cableInterfaceDao.queryPointId();
					
					Map<String, Object> pointLineMap = new HashMap<String, Object>();//插入中间表
					checkDistance(dmap,line,result);
					if(!result.isEmpty()){
						return result.toString();
					}
					pointLineMap.put("line_id", passage_id);
					if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
						pointLineMap.put("point_id", pointIdForupdate);
					}else{
						pointLineMap.put("point_id", point_id);
					}
					pointLineMap.put("point_seq", maxOder);
					
					int linePointNum = cableInterfaceDao.intoLinePoint(pointLineMap);
					
					pointLineMap.put("pointNo", pointNo);
					pointLineMap.put("pointName", pointName);
					pointLineMap.put("pointType", pointType);
					pointLineMap.put("eqpType", eqpType);
					pointLineMap.put("longitude", longitude);
					pointLineMap.put("latitude", latitude);
					pointLineMap.put("staffId", staffId);
					pointLineMap.put("area_id", area_id);
					pointLineMap.put("son_area_id", son_area_id);
					if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
						cableInterfaceDao.updateCoordinate(dmap);
					}else{
						int intoPointNum = cableInterfaceDao.intoPoint(pointLineMap);
					}
					
					
				}else{//没有就新增
					String line_id = cableInterfaceDao.queryLineId();//光缆段ID
					Map<String, Object> passageMap = new HashMap<String, Object>();//生成光缆段
					passageMap.put("line_id", line_id);
					passageMap.put("line_no", sectNo);
					passageMap.put("line_name", sectName);
					passageMap.put("staff_id", staffId);
					passageMap.put("area_id", area_id);
					passageMap.put("son_area_id", son_area_id);
					passageMap.put("line_level", line_level);
					passageMap.put("dept_no", dept_no);
					passageMap.put("line_type", "0");
					passageMap.put("parent_line_id", cableId);
					int passageNum = cableInterfaceDao.intoLine(passageMap);
					line=line_id;
					String maxOder = "1";//新增顺序都为1
					String point_id = cableInterfaceDao.queryPointId();
					
					Map<String, Object> pointLineMap = new HashMap<String, Object>();//插入中间表
					pointLineMap.put("line_id", line_id);
					if(!pointIdForupdate.equals("")){
						pointLineMap.put("point_id", pointIdForupdate);
					}else{
						pointLineMap.put("point_id", point_id);
					}
					pointLineMap.put("point_seq", maxOder);
					checkDistance(dmap,line_id,result);
					if(!result.isEmpty()){
						return result.toString();
					}
					int linePointNum = cableInterfaceDao.intoLinePoint(pointLineMap);
					
					pointLineMap.put("pointNo", pointNo);
					pointLineMap.put("pointName", pointName);
					pointLineMap.put("pointType", pointType);
					pointLineMap.put("eqpType", eqpType);
					pointLineMap.put("longitude", longitude);
					pointLineMap.put("latitude", latitude);
					pointLineMap.put("staffId", staffId);
					pointLineMap.put("area_id", area_id);
					pointLineMap.put("son_area_id", son_area_id);
					
					if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
						cableInterfaceDao.updateCoordinate(dmap);
					}else{
						int intoPointNum = cableInterfaceDao.intoPoint(pointLineMap);
					}
					
					
				}
			  	//根据光缆id和光缆段编号去进行查询看看是否有
   //	  2.有光缆段ID，直接绑定到光缆段上面去,新增点并且新增中间表数据	  4.都有，以光缆段ID为主		
			}else if(!StringUtil.isEmpty(sectId)){
				String maxOder = cableInterfaceDao.getMaxOrder(sectId);//查询中间表最大的连线顺序+1
				String point_id = cableInterfaceDao.queryPointId();
				
				Map<String, Object> pointLineMap = new HashMap<String, Object>();//插入中间表
				pointLineMap.put("line_id", sectId);
				if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
					pointLineMap.put("point_id", pointIdForupdate);
				}else{
					pointLineMap.put("point_id", point_id);
				}
				pointLineMap.put("point_seq", maxOder);
				checkDistance(dmap,sectId,result);
				if(!result.isEmpty()){
					return result.toString();
				}
				int linePointNum = cableInterfaceDao.intoLinePoint(pointLineMap);
				
				pointLineMap.put("pointNo", pointNo);
				pointLineMap.put("pointName", pointName);
				pointLineMap.put("pointType", pointType);
				pointLineMap.put("eqpType", eqpType);
				pointLineMap.put("longitude", longitude);
				pointLineMap.put("latitude", latitude);
				pointLineMap.put("staffId", staffId);
				pointLineMap.put("area_id", area_id);
				pointLineMap.put("son_area_id", son_area_id);
				
				if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
					cableInterfaceDao.updateCoordinate(dmap);
				}else{
					int intoPointNum = cableInterfaceDao.intoPoint(pointLineMap);
				}
				line=sectId;
   //	  3.都没有，先生成光缆，然后生成光缆段，最后步骤差不多				
			}else if(StringUtil.isEmpty(sectId) && StringUtil.isEmpty(cableId)){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("line_no",cableNo);
				param.put("son_area_id",son_area_id);
				param.put("is_cable","1");//查询光缆时候也查询为4的
				List<Map<String, Object>> lineList = cableInterfaceDao.selIsHasLine(param);//查询是否有光缆
				if(lineList.size()>1){//查出多条光缆
					result.put("result","112");
					result.put("desc","出现多条重复光缆编号,请重新输入光缆编号或者联系支撑人员调整");
					return result.toString();
				}else if(lineList.size() == 1){//有就以这条光缆去新建光缆段
					Map<String, Object> lineMap = lineList.get(0);
					String parent_line_id = lineMap.get("LINE_ID").toString();
					
					Map<String, Object> relayMap = new HashMap<String, Object>();
					relayMap.put("cableId",parent_line_id);
					relayMap.put("line_no",sectNo);
					relayMap.put("son_area_id",son_area_id);
					List<Map<String, Object>> relayList = cableInterfaceDao.selIsHasLine(relayMap);//查询是否有光缆段
					String line_id = "";
					String maxOder = "";
					if(relayList.size()>1){
						result.put("result","112");
						result.put("desc","出现多条重复光缆段编号,请重新输入光缆段编号或者联系支撑人员调整");
					}else if(relayList.size() == 1){
						Map<String, Object> relay = relayList.get(0);
						line_id = relay.get("LINE_ID").toString();
						maxOder = cableInterfaceDao.getMaxOrder(line_id);//查询中间表最大的连线顺序+1
					}else{
						line_id = cableInterfaceDao.queryLineId();//光缆段ID
						Map<String, Object> passageMap = new HashMap<String, Object>();//生成光缆段
						passageMap.put("line_id", line_id);
						passageMap.put("line_no", sectNo);
						passageMap.put("line_name", sectName);
						passageMap.put("staff_id", staffId);
						passageMap.put("area_id", area_id);
						passageMap.put("son_area_id", son_area_id);
						passageMap.put("line_level", line_level);
						passageMap.put("dept_no", dept_no);
						passageMap.put("line_type", "0");
						passageMap.put("parent_line_id", parent_line_id);
						int passageNum = cableInterfaceDao.intoLine(passageMap);
						
						maxOder = "1";//新增顺序都为1
					}
					//两个点之间不能超过200米，两个关键点之前不能超过500米
					Map<String, Object> pointLineMap = new HashMap<String, Object>();//插入中间表
					pointLineMap.put("normalD", 200);
					pointLineMap.put("keyD", 500);
					pointLineMap.put("longitude", longitude);
					pointLineMap.put("latitude", latitude);
					pointLineMap.put("line_id", line_id);
					
					
					String point_id = cableInterfaceDao.queryPointId();
					line=line_id;
					pointLineMap.put("line_id", line_id);
					if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
						pointLineMap.put("point_id", pointIdForupdate);
					}else{
						pointLineMap.put("point_id", point_id);
					}
					pointLineMap.put("point_seq", maxOder);
					checkDistance(dmap,line,result);
					if(!result.isEmpty()){
						return result.toString();
					}
					int linePointNum = cableInterfaceDao.intoLinePoint(pointLineMap);

					pointLineMap.put("pointNo", pointNo);
					pointLineMap.put("pointName", pointName);
					pointLineMap.put("pointType", pointType);
					pointLineMap.put("eqpType", eqpType);
					pointLineMap.put("longitude", longitude);
					pointLineMap.put("latitude", latitude);
					pointLineMap.put("staffId", staffId);
					pointLineMap.put("area_id", area_id);
					pointLineMap.put("son_area_id", son_area_id);
					
					if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
						cableInterfaceDao.updateCoordinate(dmap);
					}else{
						int intoPointNum = cableInterfaceDao.intoPoint(pointLineMap);
					}
					
				}else{//生成一条光缆
					String line_id = cableInterfaceDao.queryLineId();//光缆ID
					Map<String, Object> lineMap = new HashMap<String, Object>();//生成光缆
					lineMap.put("line_id", line_id);
					lineMap.put("line_no", cableNo);
					lineMap.put("line_name", cableName);
					lineMap.put("staff_id", staffId);
					lineMap.put("area_id", area_id);
					lineMap.put("son_area_id", son_area_id);
					lineMap.put("line_level", line_level);
					lineMap.put("line_type", "4");
					int lineNum = cableInterfaceDao.intoLine(lineMap);
					
					String passage_id = cableInterfaceDao.queryLineId();//光缆段ID
					Map<String, Object> passageMap = new HashMap<String, Object>();//生成光缆段
					passageMap.put("line_id", passage_id);
					passageMap.put("line_no", sectNo);
					passageMap.put("line_name", sectName);
					passageMap.put("staff_id", staffId);
					passageMap.put("area_id", area_id);
					passageMap.put("son_area_id", son_area_id);
					passageMap.put("line_level", line_level);
					passageMap.put("dept_no", dept_no);
					passageMap.put("line_type", "0");
					passageMap.put("parent_line_id", line_id);
					int passageNum = cableInterfaceDao.intoLine(passageMap);
					line=passage_id;
					String maxOder = "1";//新增顺序都为1
					String point_id = cableInterfaceDao.queryPointId();
					
					Map<String, Object> pointLineMap = new HashMap<String, Object>();//插入中间表
					pointLineMap.put("line_id", line);
					if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
						pointLineMap.put("point_id", pointIdForupdate);
					}else{
						pointLineMap.put("point_id", point_id);
					}
					pointLineMap.put("point_seq", maxOder);
					checkDistance(dmap,passage_id,result);
					if(!result.isEmpty()){
						return result.toString();
					}
					int linePointNum = cableInterfaceDao.intoLinePoint(pointLineMap);
					
					pointLineMap.put("pointNo", pointNo);
					pointLineMap.put("pointName", pointName);
					pointLineMap.put("pointType", pointType);
					pointLineMap.put("eqpType", eqpType);
					pointLineMap.put("longitude", longitude);
					pointLineMap.put("latitude", latitude);
					pointLineMap.put("staffId", staffId);
					pointLineMap.put("area_id", area_id);
					pointLineMap.put("son_area_id", son_area_id);
					
					if(null!=pointIdForupdate&&!pointIdForupdate.equals("")){
						cableInterfaceDao.updateCoordinate(dmap);
					}else{
						int intoPointNum = cableInterfaceDao.intoPoint(pointLineMap);
					}
				}
			}
			//计算缆线长度
//			if(!"".equals(line)&&cableInterfaceDao.ifLinePointGreaterTwo(line)>1){
//				cableDao.addDistance(line);
//			}
		} catch (Exception e) {
			result.put("result", "001");
			result.put("desc", "采集出错");
			e.printStackTrace();
			return result.toString();
		}
		result.put("result", "000");
		result.put("desc", "采集成功");
		return result.toString();
	}
	

	@Override
	public String keepPoints(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}
		try {
			List<Map<String, String>> roles=roleDao.getAllByStaffId(staffId);
			Map rs = staffDao.getStaff(staffId);
			Map params=new HashMap();
			boolean LXXJ_ADMIN=false;
			boolean LXXJ_ADMIN_AREA=false;
			boolean LXXJ_ADMIN_SON_AREA=false;
			for (int i = 0; i < roles.size(); i++) {
				if (roles.get(i).get("ROLE_NO").equals("LXXJ_ADMIN")) {// 省级管理员
					LXXJ_ADMIN=true;
				} else if(roles.get(i).get("ROLE_NO").equals("LXXJ_ADMIN_AREA")){
					LXXJ_ADMIN_AREA=true;
				}else if (roles.get(i).get("ROLE_NO").equals("LXXJ_ADMIN_SON_AREA")){
					LXXJ_ADMIN_SON_AREA=true;
				}
			}if(LXXJ_ADMIN){
				
			}else if(LXXJ_ADMIN_AREA){
				params.put("AREA_ID", rs.get("AREA_ID"));
			}else if(LXXJ_ADMIN_SON_AREA){
				params.put("SON_AREA_ID", rs.get("SON_AREA_ID"));
			}else{
				params.put("AREA_ID", -1);
			}
			params.put("POINT_TYPE", 3);
			List<Map> points=pointDao.getPoints(params);
			JSONArray array = new JSONArray();
			for (int i = 0; i < points.size(); i++) {
				JSONObject point= new JSONObject();
				point.put("point_id", points.get(i).get("POINT_ID")==null?"":points.get(i).get("POINT_ID"));
				point.put("point_no", points.get(i).get("POINT_NO")==null?"":points.get(i).get("POINT_NO"));
				point.put("point_name", points.get(i).get("POINT_NAME")==null?"":points.get(i).get("POINT_NAME"));
				point.put("point_level", points.get(i).get("POINTLEVEL")==null?"":points.get(i).get("POINTLEVEL"));
				point.put("point_type", "看护点");
				point.put("longitude", points.get(i).get("LONGITUDE")==null?"":points.get(i).get("LONGITUDE"));
				point.put("latitude", points.get(i).get("LATITUDE")==null?"":points.get(i).get("LATITUDE"));
				point.put("address", points.get(i).get("ADDRESS")==null?"":points.get(i).get("ADDRESS"));
				point.put("create_time", points.get(i).get("CREATE_TIME")==null?"":points.get(i).get("CREATE_TIME"));
				point.put("staff_name", points.get(i).get("STAFF_NAME")==null?"":points.get(i).get("STAFF_NAME"));
				point.put("son_area_name", points.get(i).get("SON_AREA_NAME")==null?"":points.get(i).get("SON_AREA_NAME"));
				point.put("son_area_id", points.get(i).get("SON_AREA_ID")==null?"":points.get(i).get("SON_AREA_ID"));
				if(Integer.parseInt(points.get(i).get("USERD_COUNT").toString())>0){
						point.put("status","已使用");
				}else{
						point.put("status","未使用");
				}
				array.add(point);
			}
			result.put("keep_points", array);
			result.put("result", "000");
			}
		catch(Exception e){
			result.put("result", "001");
			result.put("keep_points", "");
		}
		return result.toString();
	}

	@Override
	public String getStaffList(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");
		String type = jo.getString("type");
		String son_area_id = jo.getString("son_area_id");
		String sn = jo.getString("sn");
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			result.put("tasks", "");
//			return result.toString();
//		}
		Map params = new HashMap();
		if(type.equals("1")){//看护
			params.put("ROLE_NO", "LXXJ_KHY");
		}
		params.put("SON_AREA_ID", son_area_id);
		List<Map> list= new ArrayList();
		try {
			list= pointDao.queryHandler(params);
			for (Map map2 : list) {
				map2.put("staff_id", map2.get("STAFF_ID"));
				map2.put("staff_name", map2.get("STAFF_NAME"));
				map2.remove("STAFF_ID");
				map2.remove("STAFF_NAME");
			}
			result.put("result", "000");
		} catch (Exception e) {
			result.put("result", "001");
			e.printStackTrace();
		}
		result.put("staffs", list);
		return result.toString();
	}

	@Override
	public String keepPlanBuild(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staff_id");
		String sn = jo.getString("sn");
		if (!OnlineUserListener.isLogin(staffId, sn)) {
			result.put("result", "002");
			result.put("tasks", "");
			return result.toString();
		}
		String points = jo.getString("points");
		String plan_name = jo.getString("plan_name");
		String plan_start_time = jo.getString("plan_start_time");
		String plan_end_time = jo.getString("plan_end_time");
		String plan_type = jo.getString("plan_type");
		String day_time = jo.getString("day_time");
		String inaccuracy = jo.getString("inaccuracy");
		String keep_id = jo.getString("keep_id");
		try {
		Map map = new HashMap();
		map.put("PLAN_NAME", plan_name);
		map.put("PLAN_NO", plan_name);
		map.put("PLAN_TYPE", plan_type);
		map.put("PLAN_START_TIME", plan_start_time);
		map.put("PLAN_END_TIME", plan_end_time);
		map.put("PLAN_CIRCLE", "");
		map.put("PLAN_FREQUENCY", "");
		map.put("CREATE_STAFF", staffId);
		map.put("PLAN_KIND", 3);
		JSONArray p = JSONArray.fromObject(points);
		if(!"".equals(p.get(0))&&null!=p.get(0)){
			JSONObject pointid = JSONObject.fromObject(p.get(0));
			Map point = pointDao.getPointArea(pointid.get("point_id").toString());
			map.put("AREA_ID", point.get("AREA_ID"));
			map.put("SON_AREA_ID",point.get("SON_AREA_ID"));
		}else{
			result.put("result", "001");
			result.put("desc", "未选择看护点");
			return result.toString();
		}
		
		map.put("CUSTOM_TIME", "");
		map.put("INACCURACY", inaccuracy);
		pointDao.insertPlan(map);
		// 保存的计划id
		Integer plan_id = (Integer) map.get("plan_id");
		Map planDetail = new HashMap();
		String[] temp = null;
		planDetail.put("PLAN_ID", plan_id);
		planDetail.put("INSPECT_OBJECT_TYPE", 3);
		for (int i = 0, j = p.size(); i < j; i++) {
			JSONObject pointid = JSONObject.fromObject(p.get(i));
			planDetail.put("INSPECT_OBJECT_ID", pointid.get("point_id"));
			pointDao.insertDetail(planDetail);
		}
		// 看护计划单独保存时间段
			JSONArray day_times = JSONArray.fromObject(day_time);
			Map<String, Object> keepTimeParams = new HashMap<String, Object>();
			keepTimeParams.put("PLAN_ID", plan_id);
			for (int i = 0, j = day_times.size(); i < j; i++) {
				if ("".equals(day_times.get(i))) {
					continue;
				}
				JSONObject time = JSONObject.fromObject(day_times.get(i));
				keepTimeParams.put("START_TIME", time.get("start_time"));
				keepTimeParams.put("END_TIME", time.get("end_time"));
				pointDao.saveKeepTime(keepTimeParams);
			}
		//生成任务
			String planSelected = map.get("plan_id").toString();
			pointServiceImpl.createTask(planSelected, keep_id, staffId);
			result.put("result", "000");
		} catch (Exception e) {
			result.put("result", "001");
			e.printStackTrace();
		}
		return result.toString();
	}
	//查询采集点信息等等
	@Override
	public String queryCableCoordinate(HttpServletRequest request) {
//		String para= "{\"sectId\":\"9013\"}";
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");//登录者ID
		String terminalType  = jo.getString("terminalType");//终端类型
		String sn = jo.getString("sn");//手机识别码
		String sectId = jo.getString("sectId");//光缆段ID
//		if (!OnlineUserListener.isLogin(staffId, sn)) {
//			result.put("result", "002");
//			return result.toString();
//		}
		try {
			List<Map<String, Object>> infoList = cableInterfaceDao.selCableRelay(sectId);
			if(infoList.size() == 0){
				result.put("result", "012");
				result.put("desc", "查询不出相关光缆光缆段信息");
			}else{
				Map<String, Object> infoMap = infoList.get(0);
				result.put("cableId", infoMap.get("CABLE_ID"));
				result.put("cableNo", infoMap.get("CABLENO"));
				result.put("cableName",infoMap.get("CABLENAME"));
				result.put("sectId", infoMap.get("SECTID"));
				result.put("sectNo", infoMap.get("SECTNO"));
				result.put("sectName", infoMap.get("SECTNAME"));
				result.put("son_area_id", infoMap.get("SON_AREA_ID"));
				List<Map<String, Object>> pointList = cableInterfaceDao.selPointInfo(sectId);
				List<Map<String, Object>> point = new ArrayList<Map<String,Object>>();
				if(pointList.size()>0){
					for (Map<String, Object> map : pointList) {
						Map<String, Object> pointMap = new HashMap<String, Object>();
						pointMap.put("pointId",map.get("POINT_ID"));
						pointMap.put("pointNo",map.get("POINT_NO"));
						pointMap.put("pointName",map.get("POINT_NAME"));
						pointMap.put("seq",map.get("POINT_SEQ"));
						pointMap.put("pointType",map.get("POINT_TYPE"));
						pointMap.put("eqpType",map.get("EQP_TYPE_ID"));
						pointMap.put("longitude",map.get("LONGITUDE"));
						pointMap.put("latitude",map.get("LATITUDE"));
						point.add(pointMap);
					}
				}
				
				result.put("point", point);
				result.put("result", "000");
				result.put("desc", "查询成功");
			}
		} catch (Exception e) {
			result.put("result", "001");
			result.put("desc", "查询出错");
		}
		return result.toString();
	}

	@Override
	public String queryOwnEqpByDis(String json) {
		JSONObject result = new JSONObject();
		JSONObject jo = JSONObject.fromObject(json);
		Map params = new HashMap();
		params.put("staff_id", jo.getString("staffId").toString());
		params.put("longitude", jo.getString("longitude").toString());
		params.put("latitude", jo.getString("latitude").toString());
		params.put("eqp_type", jo.getString("eqp_type").toString());
		params.put("distance", jo.getString("distance").toString());
		List<Map<String,Object>> rs = new ArrayList();
		try {
			List<Map<String,Object>> list = cableInterfaceDao.queryOwnEqpByDis(params);
			for (Map<String, Object> map : list) {
				if(map.get("DISTANCE").toString().equals("0")){
					map.put("point_no", map.get("POINT_NO"));
					map.put("point_name", map.get("POINT_NAME"));
					map.put("longitude", map.get("LONGITUDE"));
					map.put("latitude", map.get("LATITUDE"));
					map.put("eqp_type", map.get("EQP_TYPE"));
					map.remove("POINT_NO");
					map.remove("POINT_NAME");
					map.remove("LONGITUDE");
					map.remove("LATITUDE");
					map.remove("EQP_TYPE");
					rs.add(map);
				}
			}
			result.put("result", "000");
		} catch (Exception e) {
			result.put("result", "001");
			e.printStackTrace();
		}
		result.put("points", rs);
		return result.toString();
	}

	@Override
	public String modifyPointInSect(HttpServletRequest request) {
		//String json= "{\"terminalType\":\"1\",\"sn\":\"\",\"staffId\":\"\",\"sectId\":\"\",\"pointId\":\"27245\",\"pointNo\":\"test\",\"pointName\":\"test\",\"pointType\":\"-1\",\"eqpType\":\"\",\"longitude\":\"118.826355\",\"latitude\":\"32.131671\"}";
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalType", jo.getString("terminalType"));
		params.put("sn", jo.getString("sn"));
		params.put("staffId", jo.getString("staffId"));
		params.put("sectId", jo.getString("sectId"));
		params.put("lineId", jo.getString("sectId"));
		params.put("pointId", jo.getString("pointId"));
		params.put("pointNo", jo.getString("pointNo"));
		params.put("pointName", jo.getString("pointName"));
		params.put("pointType", jo.getString("pointType"));
		params.put("eqpType", jo.getString("eqpType"));
		params.put("longitude", jo.getString("longitude"));
		params.put("latitude", jo.getString("latitude"));
		String flag=checkCableLinePoint(params,2);
		if(!flag.equals("")){
			result.put("result", "121");
			result.put("desc", flag);
			return result.toString();
		}
		int effect = 0;
		effect=cableInterfaceDao.modifyPointInSect(params);
		if(effect>0){
			result.put("result", "000");
			result.put("result_desc", "修改成功");
		}else {
			result.put("result", "001");
			result.put("result_desc", "修改失败");
		}
		return result.toString();
	}

	@Override
	public String deletePointInSect(HttpServletRequest request) {
		//String json= "{\"terminalType\":\"1\",\"sn\":\"\",\"staffId\":\"\",\"sectId\":\"9398\",\"pointId\":\"330786\"}";
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> paramsNew = new HashMap<String, Object>();
		params.put("terminalType", jo.getString("terminalType"));
		params.put("sn", jo.getString("sn"));
		params.put("staffId", jo.getString("staffId"));
		String MODIFY_STAFF =  jo.getString("staffId");
		params.put("lineId", jo.getString("sectId"));
		params.put("pointId", jo.getString("pointId"));
		String flag=checkCableLinePoint(params,3);
		if(!flag.equals("")){
			result.put("result", "121");
			result.put("desc", flag);
			return result.toString();
		}
		List<Map<String,Object>> list=cableInterfaceDao.queryLinePoint(params);
		int effect = 0;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("POINT_ID").toString().equals(jo.getString("pointId"))){
				list.remove(list.get(i));
				break;
			}
		}
		cableInterfaceDao.deletePointInSect(params);
		for (int i = 0; i < list.size(); i++) {
				paramsNew.put("lineId", list.get(i).get("LINE_ID"));
				paramsNew.put("pointId", list.get(i).get("POINT_ID"));
				paramsNew.put("pointSeq", i+1);
				paramsNew.put("modifyStaff", MODIFY_STAFF);
				effect = cableInterfaceDao.saveLinePoint(paramsNew);
			}
			if(effect>0){
				result.put("result", "000");
				result.put("result_desc", "删除成功");
			}else {
				result.put("result", "001");
				result.put("result_desc", "删除失败");
			}
			return result.toString();
	}

	@Override
	public String queryGridList(HttpServletRequest request) {
		//String json= "{\"terminalType\":\"1\",\"sn\":\"\",\"staffId\":\"15339\"}";
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalType", jo.getString("terminalType"));
		//params.put("sn", jo.getString("sn"));
		params.put("staffId", jo.getString("staffId"));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(jo.containsKey("sonareaid")){
			params.put("son_area_id", jo.getString("sonareaid"));
			list = cableInterfaceDao.queryGridListForTrouble(params);
		}else{
			list = cableInterfaceDao.queryGridList(params);
		}
		if(list.size()>0){
			result.put("result", "000");
			result.put("result_desc", "查询成功");
			result.put("grid", list);
		}else {
			result.put("result", "001");
			result.put("result_desc", "查询失败");
		}
		return result.toString();
	}
	
	@Override
	public String addPointInSect(HttpServletRequest request) {
		//String json= "{\"terminalType\":\"1\",\"sn\":\"\",\"staffId\":\"\",\"sectId\":\"9398\",\"startPointId\":\"295501\",\"endPointId\":\"295503\",\"pointNo\":\"test\",\"pointName\":\"test\",\"pointType\":\"-1\",\"eqpType\":\"\",\"longitude\":\"118.826355\",\"latitude\":\"32.131671\"}";
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> paramsNew = new HashMap<String, Object>();
		String point_id = cableInterfaceDao.queryPointId();
		params.put("point_id", point_id);
		params.put("terminalType", jo.getString("terminalType"));
		params.put("sn", jo.getString("sn"));
		params.put("staffId", jo.getString("staffId"));
		String MODIFY_STAFF = jo.getString("staffId");
		params.put("lineId", jo.getString("sectId"));
		params.put("startPointId", jo.getString("startPointId"));
		params.put("endPointId", jo.getString("endPointId"));
		params.put("pointNo", jo.getString("pointNo"));
		params.put("pointName", jo.getString("pointName"));
		params.put("pointType", jo.getString("pointType"));
		params.put("eqpType", jo.getString("eqpType"));
		params.put("longitude", jo.getString("longitude"));
		params.put("latitude", jo.getString("latitude"));
		params.put("area_id", "");
		params.put("son_area_id", "");
		String flag=checkCableLinePoint(params,1);
		if(!flag.equals("")){
			result.put("result", "121");
			result.put("desc", flag);
			return result.toString();
		}
		int intoPointNum = cableInterfaceDao.intoPoint(params);
		List<Map<String,Object>> list=cableInterfaceDao.queryLinePoint(params);
		List<Map<String,Object>> pointSeqS=cableInterfaceDao.queryLinePoint2(params);
		List<Map<String,Object>> pointSeqE=cableInterfaceDao.queryLinePoint3(params);
		BigDecimal startPoint = new BigDecimal(pointSeqS.get(0).get("POINT_SEQ").toString());
        BigDecimal endPoint = new BigDecimal(pointSeqE.get(0).get("POINT_SEQ").toString());
        int pointSeq=(int) endPoint.subtract(startPoint).doubleValue();
        int effect = 0;
        if(pointSeq==1){
        	for (int i = 0; i < list.size(); i++) {
    			if(list.get(i).get("POINT_ID").toString().equals(jo.getString("startPointId"))){
    				Map<String, Object> params2 = new HashMap<String, Object>();
    				params2.put("LINE_ID", jo.getString("sectId"));
    				params2.put("POINT_ID", point_id);
    				params2.put("POINT_SEQ", i+2);
        			params2.put("MODIFY_STAFF", MODIFY_STAFF);
    				list.add(i+1, params2);
    				break;
    			}
    		}
        	cableInterfaceDao.deletePointInSect(params);
        	for (int i = 0; i < list.size(); i++) {
				paramsNew.put("lineId", list.get(i).get("LINE_ID"));
				paramsNew.put("pointId", list.get(i).get("POINT_ID"));
				paramsNew.put("pointSeq", i+1);
				/*String MODIFY_STAFF=(String) list.get(i).get("MODIFY_STAFF");
				if(MODIFY_STAFF==null){
					MODIFY_STAFF="";
				}*/
				paramsNew.put("modifyStaff", MODIFY_STAFF);
			/*	String MODIFY_TIME=(String)list.get(i).get("MODIFY_TIME");
				if(MODIFY_TIME==null){
					MODIFY_TIME="";
				}
				paramsNew.put("modifyTime",MODIFY_TIME );*/
				effect = cableInterfaceDao.saveLinePoint(paramsNew);
			}	
        }
        if(effect>0){
			result.put("result", "000");
			result.put("result_desc", "新增成功");
		}else {
			result.put("result", "001");
			result.put("result_desc", "新增失败");
		}
		return result.toString();
	}
	
	private JSONObject checkDistance(Map<String,String> pointLineMap,String lineId,JSONObject result){
		try {
			if(null==lineId
					||null==pointLineMap
					||lineId.equals("")
					||null==pointLineMap
					||pointLineMap.isEmpty()
					){
				return result;
			}
			pointLineMap.put("line_id", lineId);
			Map dMap = caldao.getDistanceForLinePoint(pointLineMap);
			if(dMap.get("LAST_POINT").toString().equals("0")){
				result.put("result", "021");
				result.put("desc", "距离上个点:"+dMap.get("N_DIS").toString()+"米,两个非关键点之间不能超过200米");
			}else if(pointLineMap.get("pointType").equals("-1")
					&&dMap.get("LAST_KEY_POINT").toString().equals("0")){
				result.put("result", "022");
				result.put("desc", "距离上个点:"+dMap.get("K_DIS").toString()+"米,500米内必须有一个关键点");
			}else if(!pointLineMap.get("pointType").equals("-1")
					&&dMap.get("LAST_KEY_POINT").toString().equals("0")){
				result.put("result", "023");
				result.put("desc", "距离上个点:"+dMap.get("K_DIS").toString()+"米,关键点之间不能超过500米");
			}
			return result;
		} catch (Exception e) {
			//e.printStackTrace();
			return result;
		}
	}
	
	//光缆修改时距离判断
	private String checkCableLinePoint(Map<String,Object> pointMap,int type){
		try {
		List<Map> list = caldao.getCablePoints(pointMap);
		double dis;
		double dis2;
		if(type==1){//新增
			String startPointId=pointMap.get("startPointId").toString();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).get("POINT_ID").toString().equals(startPointId)){
					dis = MapDistance.getDistance(Double.parseDouble(pointMap.get("latitude").toString())
							, Double.parseDouble(pointMap.get("longitude").toString())
							, Double.parseDouble(list.get(i).get("LATITUDE").toString()),
							Double.parseDouble(list.get(i).get("LONGITUDE").toString())
							);
					if(!list.get(i).get("POINT_TYPE").toString().equals("-1")
							&&!pointMap.get("pointType").toString().equals("-1")
							&&dis>500){
						return "两个关键点之间不能超过500米";
					}else if(dis>200){
						return "两点之间不能超过200米";
					}else if(pointMap.get("pointType").toString().equals("-1")){
						for (int j = i; j >0; j--) {
							if(list.get(j).get("POINT_TYPE").toString().equals("-1")){
							dis+=MapDistance.getDistance(Double.parseDouble(list.get(j).get("LATITUDE").toString())
									, Double.parseDouble(list.get(j).get("LONGITUDE").toString())
									, Double.parseDouble(list.get(j-1).get("LATITUDE").toString()),
									Double.parseDouble(list.get(j-1).get("LONGITUDE").toString())
									);
							}
						}
						if(dis>500){
							return "500米内必须有一个关键点";
						}
					}
					if(list.size()>i+1){
						dis2 = MapDistance.getDistance(Double.parseDouble(pointMap.get("latitude").toString())
								, Double.parseDouble(pointMap.get("longitude").toString())
								, Double.parseDouble(list.get(i+1).get("LATITUDE").toString()),
								Double.parseDouble(list.get(i+1).get("LONGITUDE").toString())
								);
						if(!list.get(i+1).get("POINT_TYPE").toString().equals("-1")
								&&!pointMap.get("pointType").toString().equals("-1")
								&&dis2>500){
							return "两个关键点之间不能超过500米";
						}else if(dis2>200){
							return "两点之间不能超过200米";
						}else if(pointMap.get("pointType").toString().equals("-1")){
							for (int j = i; j <list.size()-1; j++) {
								if(list.get(j).get("POINT_TYPE").toString().equals("-1")){
								dis+=MapDistance.getDistance(Double.parseDouble(list.get(j).get("LATITUDE").toString())
										, Double.parseDouble(list.get(j).get("LONGITUDE").toString())
										, Double.parseDouble(list.get(j+1).get("LATITUDE").toString()),
										Double.parseDouble(list.get(j+1).get("LONGITUDE").toString())
										);
								}
							}
							if(dis>500){
								return "500米内必须有一个关键点";
							}
						}
					}				
				}
			}
		}else if(type==2){//修改
			String pointId=pointMap.get("pointId").toString();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).get("POINT_ID").toString().equals(pointId)){
					if(i>-1){
						dis = MapDistance.getDistance(Double.parseDouble(pointMap.get("latitude").toString())
								, Double.parseDouble(pointMap.get("longitude").toString())
								, Double.parseDouble(list.get(i-1).get("LATITUDE").toString()),
								Double.parseDouble(list.get(i-1).get("LONGITUDE").toString())
								);
						if(!list.get(i-1).get("POINT_TYPE").toString().equals("-1")
								&&!pointMap.get("pointType").toString().equals("-1")
								&&dis>500){
							return "两个关键点之间不能超过500米";
						}else if(dis>200){
							return "两点之间不能超过200米";
						}else if(pointMap.get("pointType").toString().equals("-1")){
							for (int j = i; j >0; j--) {
								if(list.get(j).get("POINT_TYPE").toString().equals("-1")){
								dis+=MapDistance.getDistance(Double.parseDouble(list.get(j).get("LATITUDE").toString())
										, Double.parseDouble(list.get(j).get("LONGITUDE").toString())
										, Double.parseDouble(list.get(j-1).get("LATITUDE").toString()),
										Double.parseDouble(list.get(j-1).get("LONGITUDE").toString())
										);
								}
							}
							if(list.size()>i+1){
								for (int j = i; j <list.size()-1; j++) {
									if(list.get(j).get("POINT_TYPE").toString().equals("-1")){
									dis+=MapDistance.getDistance(Double.parseDouble(list.get(j).get("LATITUDE").toString())
											, Double.parseDouble(list.get(j).get("LONGITUDE").toString())
											, Double.parseDouble(list.get(j+1).get("LATITUDE").toString()),
											Double.parseDouble(list.get(j+1).get("LONGITUDE").toString())
											);
									}
								}
							}
							if(dis>500){
								return "500米内必须有一个关键点";
							}
						}
					}
					if(list.size()>i+1){
						dis2 = MapDistance.getDistance(Double.parseDouble(pointMap.get("latitude").toString())
								, Double.parseDouble(pointMap.get("longitude").toString())
								, Double.parseDouble(list.get(i+1).get("LATITUDE").toString()),
								Double.parseDouble(list.get(i+1).get("LONGITUDE").toString())
								);
						if(!list.get(i+1).get("POINT_TYPE").toString().equals("-1")
								&&!pointMap.get("pointType").toString().equals("-1")
								&&dis2>500){
							return "两个关键点之间不能超过500米";
						}else if(dis2>200){
							return "两点之间不能超过200米";
						}
					}				
				}
			}
		}else if(type==3){//删除
			String pointId=pointMap.get("pointId").toString();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).get("POINT_ID").toString().equals(pointId)){
					if(i-1>0&&list.size()>i+1){
						dis = MapDistance.getDistance(Double.parseDouble(list.get(i+1).get("LATITUDE").toString())
								, Double.parseDouble(list.get(i+1).get("LONGITUDE").toString())
								, Double.parseDouble(list.get(i-1).get("LATITUDE").toString())
								, Double.parseDouble(list.get(i-1).get("LONGITUDE").toString())
								);
						if(!list.get(i-1).get("POINT_TYPE").toString().equals("-1")
								&&!list.get(i+1).get("POINT_TYPE").toString().equals("-1")
								&&dis>500){
							return "两个关键点之间不能超过500米";
						}else if(dis>200){
							return "两点之间不能超过200米";
						}else if(list.get(i-1).get("POINT_TYPE").equals("-1")){
							for (int j = i-1; j >0; j--) {
								if(list.get(j).get("POINT_TYPE").toString().equals("-1")){
								dis+=MapDistance.getDistance(Double.parseDouble(list.get(j).get("LATITUDE").toString())
										, Double.parseDouble(list.get(j).get("LONGITUDE").toString())
										, Double.parseDouble(list.get(j-1).get("LATITUDE").toString()),
										Double.parseDouble(list.get(j-1).get("LONGITUDE").toString())
										);
								}
							}
							if(list.size()>i-1){
								for (int j = i-1; j <list.size()-1; j++) {
									if(list.get(j).get("POINT_TYPE").toString().equals("-1")){
									dis+=MapDistance.getDistance(Double.parseDouble(list.get(j).get("LATITUDE").toString())
											, Double.parseDouble(list.get(j).get("LONGITUDE").toString())
											, Double.parseDouble(list.get(j+1).get("LATITUDE").toString()),
											Double.parseDouble(list.get(j+1).get("LONGITUDE").toString())
											);
									}
								}
							}
							if(dis>500){
								return "500米内必须有一个关键点";
							}
						}else if(list.get(i+1).get("POINT_TYPE").equals("-1")){
							for (int j = i-1; j >0; j--) {
								if(list.get(j).get("POINT_TYPE").toString().equals("-1")){
								dis+=MapDistance.getDistance(Double.parseDouble(list.get(j).get("LATITUDE").toString())
										, Double.parseDouble(list.get(j).get("LONGITUDE").toString())
										, Double.parseDouble(list.get(j-1).get("LATITUDE").toString()),
										Double.parseDouble(list.get(j-1).get("LONGITUDE").toString())
										);
								}
							}
							if(list.size()>i+1){
								for (int j = i+1; j <list.size()-1; j++) {
									if(list.get(j).get("POINT_TYPE").toString().equals("-1")){
									dis+=MapDistance.getDistance(Double.parseDouble(list.get(j).get("LATITUDE").toString())
											, Double.parseDouble(list.get(j).get("LONGITUDE").toString())
											, Double.parseDouble(list.get(j+1).get("LATITUDE").toString()),
											Double.parseDouble(list.get(j+1).get("LONGITUDE").toString())
											);
									}
								}
							}
							if(dis>500){
								return "500米内必须有一个关键点";
							}
						}
					}
					}				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String signNormalPoint(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("STAFF_ID", jo.getString("staffId"));
			params.put("TASK_ID", jo.getString("taskId"));
			params.put("POINT_ID", jo.getString("pointId"));
			params.put("DISTANCE", caldao.getDistanceForNorSign(params));
			params.put("LONGITUDE", jo.getString("longitude"));
			params.put("LATITUDE", jo.getString("latitude"));
			caldao.addSignNorPoint(params);
		} catch (Exception e) {
			return Result.returnCode("001", "签到失败，请重试");
		}
		return Result.returnCode("000", "签到成功");
	}

	@Override
	public String zhxjTask(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String json = BaseServletTool.getParam(request);
		JSONObject jo = JSONObject.fromObject(json);
		String staffId = jo.getString("staffId");
		String terminalType = jo.getString("terminalType");
		String sn = jo.getString("sn");
		String longitude = jo.getString("longitude");
		String latitude = jo.getString("latitude");
		String taskType = jo.getString("taskType");
		String taskOrgin = jo.getString("taskOrgin");
		Map map = new HashMap();
		map.put("TASK_ORGIN", taskOrgin);
		map.put("TASK_TYPE", taskType);
		map.put("STAFF_ID", staffId);
		map.put("LONGITUDE", longitude);
		map.put("LATITUDE", latitude);
		map.put("num1", (Integer.parseInt(jo.getString("page"))-1)*Integer.parseInt(jo.getString("rows")));
		map.put("num2", Integer.parseInt(jo.getString("page"))*Integer.parseInt(jo.getString("rows")));
		List<Map> list= new ArrayList<Map>();
		JSONObject taskJson = null;
		JSONArray taskArray = new JSONArray();
		try {
			list=cableInterfaceDao.zhxjTask(map);
			for (Map task : list) {
				taskJson = new JSONObject();
				taskJson.put("taskId", task.get("TASK_ID"));
				taskJson.put("taskName", task.get("TASK_NAME"));
				taskJson.put("taskState", task.get("TASK_STATUS")==null?"":task.get("TASK_STATUS"));
				taskJson.put("startTime", task.get("TASK_START_TIME"));
				taskJson.put("endTime", task.get("TASK_END_TIME"));
				taskJson.put("sonArea", task.get("SON_AREA")==null?"":task.get("SON_AREA"));
				taskJson.put("taskType", task.get("TASK_TYPE"));
				taskJson.put("taskTypeName", task.get("TYPE_NAME"));
				taskJson.put("taskOrgin", task.get("TASK_ORGIN_ID"));
				taskJson.put("taskOrginName", task.get("TASK_ORGIN_NAME"));
				taskArray.add(taskJson);
			}
		} catch (Exception e) {
			result.put("result", "001");
			result.put("desc", "查询失败");
		}
		result.put("tasks", taskArray);
		result.put("result", "000");
		result.put("desc", "");
		return result.toString();
	}
}
