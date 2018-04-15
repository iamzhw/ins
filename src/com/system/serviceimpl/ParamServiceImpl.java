package com.system.serviceimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linePatrol.dao.LineJobDao;
import com.linePatrol.util.DateUtil;
import com.linePatrol.webservice.PushServiceHttpBindingStub;
import com.linePatrol.webservice.PushServiceLocator;
import com.system.constant.RoleNo;
import com.system.dao.ParamDao;
import com.system.service.ParamService;

@Service
public class ParamServiceImpl implements ParamService {
	
	@Resource
	private ParamDao paramDao;
	
	@Resource
	private LineJobDao lineJobDao;
	
	@Override
	public Map<String, Object> query(HttpServletRequest request) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		String areaId="2";//默认查询全省
		String isAdmin="0";//默认是省管理员
		if (null != session.getAttribute(RoleNo.AXX_ALL_ADMIN)) {
			if(null != request.getParameter("param_areaId") && "" != request.getParameter("param_areaId")){
				areaId = request.getParameter("param_areaId");
			}
		}
		else{
			areaId = String.valueOf(request.getSession().getAttribute("areaId"));
			isAdmin="1";
		}
		map.put("AREA_ID", areaId);
		List<Map<String,Object>> paramList = paramDao.query(map);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		for(Map<String,Object> param : paramList){
			resultMap.put(String.valueOf(param.get("PARAM_NAME")), param.get("PARAM_VALUE"));
		}
		resultMap.put("param_areaId", areaId);
		resultMap.put("isAdmin", isAdmin);
		return resultMap;
	}

	@Transactional
	@Override
	public void save(HttpServletRequest request) {
		
		String areaId = request.getParameter("pAreaId");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("AREA_ID", areaId);//设置区域ID
		
//		if(areaId.equals("2")){
//		map.put("PARAM_NAME", "asteptour");
//		map.put("PARAM_VALUE",request.getParameter("asteptour"));
//		paramDao.saveAll(map);//修改一干全省的频次,
//		paramDao.upPartCirByCityCir(map);//底下一干步巡段频次比当前一干高的修改为省一干频次
//		
//		map.put("PARAM_NAME", "twodrysteptour");
//		map.put("PARAM_VALUE",request.getParameter("twodrysteptour"));
//		paramDao.saveAll(map);//修改步巡二干全省的频次,
//		paramDao.upPartCirByCityCir(map);//底下二干步巡段频次比当前二干高的修改为省二干频次
//		
//		map.put("PARAM_NAME", "landmarkstep");
//		map.put("PARAM_VALUE",request.getParameter("landmarkstep"));
//		paramDao.saveAll(map);//修改地标频次
//		paramDao.upPartCirByCityCir(map);//底下二干步巡段频次比当前二干高的修改为省二干频次
//		}
		
		map.put("PARAM_NAME", "WorkStart");
		map.put("PARAM_VALUE", request.getParameter("WorkStart"));
		paramDao.saveAll(map);//上午开始时间
		map.put("PARAM_NAME", "WorkEnd");
		map.put("PARAM_VALUE", request.getParameter("WorkEnd"));
		paramDao.saveAll(map);//上午结束时间
		map.put("PARAM_NAME", "WorkStart2");
		map.put("PARAM_VALUE", request.getParameter("WorkStart2"));
		paramDao.saveAll(map);//下午开始时间
		map.put("PARAM_NAME", "WorkEnd2");
		map.put("PARAM_VALUE", request.getParameter("WorkEnd2"));
		paramDao.saveAll(map);//下午结束时间
		map.put("PARAM_NAME", "siterange");
		map.put("PARAM_VALUE", request.getParameter("siterange"));
		paramDao.save(map);//巡检点匹配半径
		map.put("PARAM_NAME", "OutSiteRange");
		map.put("PARAM_VALUE", request.getParameter("OutSiteRange"));
		paramDao.save(map);//外力施工点匹配半径
		map.put("PARAM_NAME", "OutSiteStay");
		map.put("PARAM_VALUE", request.getParameter("OutSiteStay"));
		paramDao.save(map);//非“隐患”外力点停留时间
		map.put("PARAM_NAME", "OutSiteFile");
		map.put("PARAM_VALUE", "Y".equals(request.getParameter("OutSiteFile"))?"Y":"N");
		paramDao.save(map);//非“隐患”外力点是否需要上传图片
		map.put("PARAM_NAME", "UnSafeOutSiteStay");
		map.put("PARAM_VALUE", request.getParameter("UnSafeOutSiteStay"));
		paramDao.save(map);//“隐患”外力点停留时间
		map.put("PARAM_NAME", "UnSafeOutSiteFile");
		map.put("PARAM_VALUE", "Y".equals(request.getParameter("UnSafeOutSiteFile"))?"Y":"N");
		paramDao.save(map);//“隐患”外力点停留时间是否需要上传图片
		map.put("PARAM_NAME", "LandMarkRange");
		map.put("PARAM_VALUE", request.getParameter("LandMarkRange"));
		paramDao.save(map);//地标匹配半径
		map.put("PARAM_NAME", "secondsforwalk");
		map.put("PARAM_VALUE", request.getParameter("secondsforwalk"));
		paramDao.saveAll(map);//时速20公里以下采集时间间隔
		map.put("PARAM_NAME", "secondsfordriving");
		map.put("PARAM_VALUE", request.getParameter("secondsfordriving"));
		paramDao.saveAll(map);//时速20公里以上采集时间间隔
	}

	@Override
	public String getParamValue(String paramName, String areaId) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("PARAM_NAME", paramName);
		params.put("AREA_ID", areaId);
		Map<String,Object> resultMap = paramDao.getParamValue(params);
		
		if(null != resultMap && resultMap.size() > 0){
			return String.valueOf(resultMap.get("PARAM_VALUE"));
		}
		return null;
	}
	
	@Override
	public void calLineTime(String queryDate) {

		List<Map<String, Object>> areaList = paramDao.getAllAreaByJS(new HashMap<String, Object>());
		for (Map<String, Object> area : areaList) {
			
			List<Map<String, Object>> lineStaffs = paramDao.getLineStaffs(area);
			
			for (Map<String, Object> lineStaff : lineStaffs) {
				String areaId = String.valueOf(lineStaff.get("AREA_ID"));
				/**
				 * 查询巡线有效时间
				 */
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("QUERY_DATE", queryDate);
				param.put("USER_ID", lineStaff.get("STAFF_ID"));
				List<Map<String, Object>> inspectTimes = paramDao.getInspectTimes(param);
				/**
				 * 插入无效时间
				 */
				if (null == inspectTimes || inspectTimes.size() <= 0) {// 没有记录有效时长才重新计算
					param.put("AREA_ID", areaId);
					param.put("START_TIME", queryDate + " " + getParamValue("WorkStart", areaId) + ":00");
					param.put("END_TIME", queryDate + " " + getParamValue("WorkEnd2", areaId) + ":00");

					try {
						List<Map<String, Object>> validTimeList = getValidTime(param);
						for (Map<String, Object> validTime : validTimeList) {
							paramDao.saveInValidTime(validTime);// 无效时间入库
						}
					} catch (Exception e) {// 任何一个人时长错误不影响其他人
						saveLogInfo("ParamServiceImpl", "calLineTime(String queryDate)", "ERROR", e.getMessage());
					}
				}
			}
			/**
			 * 按地区保存有效时长
			 */
			try {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("QUERY_DATE", queryDate);
				param.put("AREA_ID", area.get("AREA_ID"));
				paramDao.deleteInpsectTime(param);//先删除当天区域的有效时长，防止重复计算
				saveValidTime(String.valueOf(area.get("AREA_ID")), queryDate);
			} catch (Exception e) {
				saveLogInfo("ParamServiceImpl", "calLineTime(String queryDate)", "ERROR", e.getMessage());
			}
		}
	}
	
	/**
	 * 保存上下午有效时长及外来施工配合时长
	 * 
	 * @param areaId
	 * @param queryTime
	 * @return
	 */
	private void saveValidTime(String areaId, String queryTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		String start_time_m = getParamValue("WorkStart", areaId);
		String end_time_m = getParamValue("WorkEnd", areaId);
		String start_time_a = getParamValue("WorkStart2", areaId);
		String end_time_a = getParamValue("WorkEnd2", areaId);

		map.put("start_date_m", start_time_m);
		map.put("end_date_m", end_time_m);
		map.put("start_date_a", start_time_a);
		map.put("end_date_a", end_time_a);

		map.put("start_date", queryTime);
		map.put("end_date", queryTime);
		map.put("area_id", areaId);

		String[] end_date_marr = end_time_m.split(":");
		String[] start_date_marr = start_time_m.split(":");
		int total_min_m = Integer.parseInt(end_date_marr[0]) * 60 + Integer.parseInt(end_date_marr[1])
				- Integer.parseInt(start_date_marr[0]) * 60 - Integer.parseInt(start_date_marr[1]);

		String[] end_date_aarr = end_time_a.split(":");
		String[] start_date_aarr = start_time_a.split(":");
		int total_min_a = Integer.parseInt(end_date_aarr[0]) * 60 + Integer.parseInt(end_date_aarr[1])
				- Integer.parseInt(start_date_aarr[0]) * 60 - Integer.parseInt(start_date_aarr[1]);

		map.put("total_min_m", total_min_m);
		map.put("total_min_a", total_min_a);
		paramDao.saveValidTime(map); // 全天及上下午有效时长结果集
	}
	
	/**
	 * 计算两个时间差
	 * 
	 * @param time1  时间1
	 * @param time2  时间2
	 * @param baseTime 时间基数:1秒，60分钟，1440小时
	 * @return
	 */
	private long getDifferTime(String time1,String time2,int baseTime){
	    
		long diff = 0;
		long min = 0;
		
		Date d1;
	    Date d2;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			d1 = df.parse(time1);
			d2 = df.parse(time2);
		    diff = d2.getTime() - d1.getTime();
		    min = (diff / (1000 * baseTime));
		} catch (ParseException e) {
			e.printStackTrace();
			min =0;
		}
		
		return min;
	}
	
	@Override
	public List<Map<String, Object>> getValidTime(Map<String,Object> param){
		
		List<Map<String,Object>> validTimeList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> firstMap = null;
		String gpsFlag="0";
		String gpsSwitch="1";
		String isMatch="0";
		
		//1、获取用户前一天轨迹时间
		List<Map<String,Object>> allTrackTime = paramDao.getAllTrackTime(param);
		
		if(allTrackTime.size() <= 0){//数据不足，无法计算，直接返回
			return validTimeList;
		}
		
		//2、记录不在计划中的开始时间
		Map<String,Object> startMap = allTrackTime.get(0);//开始时间
		long start = getDifferTime(param.get("START_TIME").toString(),String.valueOf(startMap.get("TRACK_TIME")),1);
		if(start > 0){//计划开始时间小于轨迹起始时间，那么从计划开始时间到轨迹开始上报这段为GPS未打开
			Map<String,Object> validTimeMap = new HashMap<String,Object>();
			validTimeMap.put("USER_ID", startMap.get("USER_ID"));
			validTimeMap.put("LINE_DATE", startMap.get("TRACK_TIME"));
			validTimeMap.put("START_TIME", param.get("START_TIME").toString());
			validTimeMap.put("END_TIME", startMap.get("TRACK_TIME"));
			validTimeMap.put("INVALID_TYPE", 4);//无任务数据上传
			validTimeList.add(validTimeMap);//保存无任何数据上传
		}
		
		//记录不在计划中的结束时间
		Map<String,Object> endMap = allTrackTime.get(allTrackTime.size()-1);//结束时间
		long end = getDifferTime(String.valueOf(endMap.get("TRACK_TIME")),param.get("END_TIME").toString(),1);
		if(end > 0){//计划结束时间大于轨迹结束时间，那么从轨迹结束上报到计划结束这段为GPS未打开
			Map<String,Object> validTimeMap = new HashMap<String,Object>();
			validTimeMap.put("USER_ID", endMap.get("USER_ID"));
			validTimeMap.put("LINE_DATE", endMap.get("TRACK_TIME"));
			validTimeMap.put("START_TIME", endMap.get("TRACK_TIME"));
			validTimeMap.put("END_TIME", param.get("END_TIME").toString());
			validTimeMap.put("INVALID_TYPE", 4);//无任务数据上传
			validTimeList.add(validTimeMap);//保存无任何数据上传
		}
		
		//3、记录停留超过40分钟时间
		List<Map<String,Object>> invalidStayList = paramDao.getInvalidSiteStayTime(param);//查询数据中的停留时间;
		for(Map<String,Object> invalidStay : invalidStayList){
			Map<String,Object> validTimeMap = new HashMap<String,Object>();
			validTimeMap.put("USER_ID", invalidStay.get("USER_ID"));
			validTimeMap.put("LINE_DATE", invalidStay.get("START_TIME"));
			validTimeMap.put("START_TIME", invalidStay.get("START_TIME"));
			validTimeMap.put("END_TIME", invalidStay.get("END_TIME"));
			validTimeMap.put("INVALID_TYPE", 3);//保存停留超过40分钟
			validTimeList.add(validTimeMap);
		}
		
		//4、计算GPS未打开、GPS无信号和未匹配到巡检点时间
		for(int i=0;i<allTrackTime.size()-1;){

			firstMap = allTrackTime.get(i);
			gpsFlag = firstMap.get("GPS_FLAG").toString();
			gpsSwitch = firstMap.get("GPS_SWITCH").toString();
			isMatch = firstMap.get("IS_MATCH").toString();
			if("0".equals(gpsSwitch)){//判断是否打开
				int j = getCloseGPSInfo(allTrackTime,validTimeList,i++);
				packageValidTime2(validTimeList,firstMap,allTrackTime.get(j),0);//保存GPS未打开时长
				i=j;
			}else if("1".equals(gpsFlag)){//判断是否无信号
				int j = getNoGPSInfo(allTrackTime,validTimeList,i++);//获取GPS连续无信号最大值
				long time = getDifferTime(firstMap.get("TRACK_TIME").toString(),allTrackTime.get(j).get("TRACK_TIME").toString(),60);
				if(time > 20){//无效时长大于20分钟才计算无效时间
					packageValidTime2(validTimeList,firstMap,allTrackTime.get(j),1);//保存GPS无信号时长
				}
				i=j;
			}else if("0".equals(isMatch)){
				int j = getNoMatchSiteInfo(allTrackTime,validTimeList,i++);
				packageValidTime2(validTimeList,firstMap,allTrackTime.get(j),2);//保存未匹配到巡检点
				i=j;
			}else{
				i++;
			}
		}
		
		//5、记录连续两点时间间隔大于20分钟，视为点丢失
		List<Map<String,Object>> noDataTimeList = paramDao.getNoDataUploadTime(param);//查询数据中的停留时间;
		for(Map<String,Object> noDataTime : noDataTimeList){
			Map<String,Object> validTimeMap = new HashMap<String,Object>();
			validTimeMap.put("USER_ID", noDataTime.get("USER_ID"));
			validTimeMap.put("LINE_DATE", noDataTime.get("START_TIME"));
			validTimeMap.put("START_TIME", noDataTime.get("START_TIME"));
			validTimeMap.put("END_TIME", noDataTime.get("END_TIME"));
			validTimeMap.put("INVALID_TYPE", 4);//无数据上传
			validTimeList.add(validTimeMap);
		}
		validTimeList = filterRepeatTime(validTimeList,param);
		return validTimeList;
	}
	
	private List<Map<String,Object>> filterRepeatTime(List<Map<String,Object>> list,Map<String,Object> param){
		for(int i=0;i<list.size();i++){
			for(int j=i+1;j<list.size();j++){
				long time = getDifferTime(list.get(i).get("START_TIME").toString(),list.get(j).get("START_TIME").toString(),1);
				if(time < 0){
					Map<String,Object> temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		}
		
		List<Map<String,Object>> filterList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> trackMap = list.get(i);
			String endTime = list.get(i).get("END_TIME").toString();
			for(int j=i+1;j<list.size();j++){
				long time = getDifferTime(list.get(j).get("START_TIME").toString(),endTime,1);
				if(time > 0){
					time = getDifferTime(endTime,list.get(j).get("END_TIME").toString(),1);
					if(time > 0){
						endTime = list.get(j).get("END_TIME").toString();
					}
					i=j;
				}else{
					i=j-1;
					break;
				}
			}
			
			trackMap.put("END_TIME", endTime);
			filterList.add(trackMap);
		}
		return filterList;
	}
	
	private int getCloseGPSInfo(List<Map<String,Object>> allTrackTime,
			List<Map<String,Object>> validTimeList,int i){
		if(allTrackTime.size()-1 <=i){//已达到终点，不再增加
			return i;
		}
		
		String gpsSwitch = allTrackTime.get(i).get("GPS_SWITCH").toString();
		if("0".equals(gpsSwitch)){//连续GPS未打开记录,继续查找
			i++;
			i=getCloseGPSInfo(allTrackTime,validTimeList,i);
		}
		
		return i;
	}
	
	
	private int getNoGPSInfo(List<Map<String,Object>> allTrackTime,
			List<Map<String,Object>> validTimeList,int i){
		
		if(allTrackTime.size()-1 <=i){//已达到终点，不再增加
			return i;
		}
		
		if("0".equals(allTrackTime.get(i).get("GPS_SWITCH").toString())){//如果下一个点GPS未打开，直接返回
			return i;
		}

		String gpsFlag = allTrackTime.get(i).get("GPS_FLAG").toString();
		if("1".equals(gpsFlag)){//连续无信号记录,继续查找
			i++;
			i=getNoGPSInfo(allTrackTime,validTimeList,i);
		}
		
		return i;
	}
	
	private int getNoMatchSiteInfo(List<Map<String,Object>> allTrackTime,
			List<Map<String,Object>> validTimeList,int i){
		
		if(allTrackTime.size()-1 <=i){//已达到终点，不再增加
			return i;
		}
		
		if("0".equals(allTrackTime.get(i).get("GPS_SWITCH").toString()) 
				|| "1".equals(allTrackTime.get(i).get("GPS_FLAG").toString())){//如果GPS无信号或GPS未打开
			return i;
		}
	
		if("0".equals(allTrackTime.get(i).get("IS_MATCH").toString())){//连续未匹配到记录,继续查找
			i++;
			i=getNoMatchSiteInfo(allTrackTime,validTimeList,i);
		}
		return i;
	}
	
	private void packageValidTime2(List<Map<String,Object>> validTimeList,Map<String,Object> startMap,Map<String,Object> endMap,int type){
		Map<String,Object> validTimeMap = new HashMap<String,Object>();
		validTimeMap.put("START_TIME", startMap.get("TRACK_TIME"));
		validTimeMap.put("END_TIME", endMap.get("TRACK_TIME"));
		validTimeMap.put("USER_ID", startMap.get("USER_ID"));
		validTimeMap.put("LINE_DATE", startMap.get("TRACK_TIME"));
		validTimeMap.put("INVALID_TYPE", type);//无效时长类型
		validTimeList.add(validTimeMap);
		
	}
	
	
	@Override
	public boolean isRepeatTime(List<Map<String,Object>> invalidStayList,String startTime,String endTime) {
		
		for(Map<String,Object> invalidStay : invalidStayList){
			long start = getDifferTime(String.valueOf(invalidStay.get("START_TIME")),startTime,1);
			long end = getDifferTime(String.valueOf(invalidStay.get("END_TIME")),endTime,1);
			if((start >= 0) && (end <=0)){
				return true;
			}
		}
		return false;
	}
	
	public void calGuardTime(String queryDate){
		//1、获取看护时间段
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("USER_ID", "");
		List<Map<String,Object>> guardTimes = paramDao.getGuardPlanTime(param);
		
		long invalidTime =0;
		int count=0;
		String timePart="";
		//2、根据查询看护轨迹
		for(Map<String,Object> guardTime : guardTimes){
			String startTime1 = queryDate +" " + guardTime.get("start_time").toString()+":00";
			String endTime1 = queryDate +" " + guardTime.get("start_time").toString()+":00";
			param.put("start_time", startTime1);
			param.put("end_time", endTime1);
			List<Map<String,Object>> trackTimes = paramDao.getGuardTrackTime(param);
			
			String startTime = startTime1;
			String endTime = null;		
			for(int i=0;i<trackTimes.size();i++){
				endTime = trackTimes.get(i).get("START_TIME").toString();
				long num = getDifferTime(startTime,endTime,60);
				if(num > 0){
					invalidTime+=num;
					count++;
					timePart+= startTime.substring(startTime.indexOf(" ")+1,startTime.lastIndexOf(":")) + "-" + endTime.substring(endTime.indexOf(" ")+1,endTime.lastIndexOf(":"));
					startTime = trackTimes.get(i).get("END_TIME").toString();
				}
				
				if(i == trackTimes.size()-1){
					num = getDifferTime(startTime,endTime1,60);
					if(num > 0){
						invalidTime+=num;
						count++;
						timePart+= startTime.substring(startTime.indexOf(" ")+1,startTime.lastIndexOf(":")) + "-" + endTime1.substring(endTime1.indexOf(" ")+1,endTime1.lastIndexOf(":"));
					}
				}
			}
		}
		
		System.out.println(invalidTime+":"+count+":"+timePart);
	}
	
	@Override
    public List<Map<String,Object>> getSitesByUser(Map<String, Object> map){
    	return paramDao.getSitesByUser(map);
    }

	@Override
	public void updateSites(Map<String, Object> map) {
		paramDao.updateSites(map);
	}

	@Override
	public void saveInvalidSites(Map<String, Object> map) {
		paramDao.saveInvalidSites(map);
	}

	@SuppressWarnings("all")
	@Override
	public void pushMessage(){
		List<Map<String,Object>> pushMessageList = paramDao.getPushMessageList(new HashMap<String,Object>());
		
		String returnCodes = "001";
		if(null != pushMessageList && pushMessageList.size() > 0){
			for(Map<String,Object> pushMessage : pushMessageList){
				//要推送的人
				String usernames = String.valueOf(pushMessage.get("PUSH_STAFF"));
				String title = String.valueOf(pushMessage.get("PUSH_TITLE"));
				String message = String.valueOf(pushMessage.get("PUSH_CONTENT"));
				
				// 拼装参数
				StringBuffer para = new StringBuffer();
				para.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				para.append("<pushInfo>");
				para.append("<appkey>fFzUQwCCyVfbQWP</appkey>");
				para.append("<usernames>" + usernames + "</usernames>");

				para.append("<single>1</single>");
				para.append("<title>" + title + "</title>");
				para.append("<area></area>");
				para.append("<message>" + message + "</message>");
				para.append("<uri></uri>");
				para.append("</pushInfo>");

				try {
					
					PushServiceLocator pServiceLocator = new PushServiceLocator();
					PushServiceHttpBindingStub pStub = (PushServiceHttpBindingStub) pServiceLocator
							.getPushServiceHttpPort();
					String result = pStub.pushNotification(para.toString());

					Document doc = DocumentHelper.parseText(result);
					Element returnInfo = doc.getRootElement();
					returnCodes = returnInfo.elementText("resultCode");
				} catch (Exception e) {
				    returnCodes ="001";
				}
				
				if("000".equals(returnCodes)){
					pushMessage.put("PUSH_RESULT", "1");//推送成功
					paramDao.inserPushMessageHis(pushMessage);
					paramDao.deletePushMessage(pushMessage);//推送消息装历史
				}else{
					pushMessage.put("PUSH_RESULT", "2");//推送失败
					paramDao.updatePushMessage(pushMessage);
				}
			}
		}
	}
	
	@Override
	public void insertPushMessage(Map<String,Object> param){
		paramDao.insertPushMessage(param);
	}

	@Override
	public void saveLogInfo(String className, String methodParam, String logType, String logInfo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("CLASS_NAME", className);
		param.put("METHOD_PARAM", methodParam);
		param.put("LOG_TYPE", logType);
		param.put("LOG_INFO", logInfo);
		
		try{
			
			paramDao.saveLogInfo(param);
		}catch(Exception e){
			System.out.println("日志打印错误，类名称:" + className + "方法名称:" + methodParam);
		}
		
	}

	@Override
	public void insertLogin(String userId, String sn, String isLogin, String releaseId) {

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("USER_ID", userId);
		param.put("SN", sn);
		param.put("IS_LOGIN", isLogin);
		param.put("RELEASE_ID", releaseId);
		
		try{
			
			paramDao.insertLogin(param);
		}catch(Exception e){
			System.out.println("日志打印错误，用户ID:" + userId + " SN:" + sn);
		}
		
	}

	@Override
	public Map<String, Object> getBaseLoginInfo(Map<String, Object> param) {
		List<Map<String, Object>> loings = paramDao.getBaseLoginInfo(param);

		if(null != loings && loings.size() > 0){
			return loings.get(0);
		}
		
		return null;
	}

	@Override
	public void insertInspectArrate(String executeDate) {

		List<Map<String, Object>> areas = paramDao.getInspectTaskAreas(new HashMap<String, Object>());
		if (null != areas && areas.size() > 0) {
			for (Map<String, Object> area : areas) {
				Map<String, Object> para = new HashMap<String, Object>();
				para.put("executeDate", executeDate);// 执行日期
				para.put("areaId", area.get("AREA_ID"));// 区域ID
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("areaId", area.get("AREA_ID"));// 区域ID

				map.put("paramName", "WorkStart");// 上午开始时间
				String start_date_m = paramDao.getParamValueByName(map);
				para.put("start_date_m", start_date_m);

				map.put("paramName", "WorkEnd");// 上午结束时间
				String end_date_m = paramDao.getParamValueByName(map);
				para.put("end_date_m", end_date_m);

				map.put("paramName", "WorkStart2");// 下午开始时间
				String start_date_a = paramDao.getParamValueByName(map);
				para.put("start_date_a", start_date_a);

				map.put("paramName", "WorkEnd2");// 下午结束时间
				String end_date_a = paramDao.getParamValueByName(map);
				para.put("end_date_a", end_date_a);

				map.put("paramName", "OutSiteStay");// 非隐患
				String OutSiteStay = paramDao.getParamValueByName(map);
				para.put("OutSiteStay", OutSiteStay);

				map.put("paramName", "UnSafeOutSiteStay");// 隐患
				String UnSafeOutSiteStay = paramDao.getParamValueByName(map);
				para.put("UnSafeOutSiteStay", UnSafeOutSiteStay);

				paramDao.deleteInspectArrate(para);//删除已存在任务到位率，防止数据重复
				paramDao.insertInspectArrate(para);// 插入巡检到位率报表
			}
		}
	}
	
	@Override
	public void autoLineTask(HttpServletRequest request) {
		List<Map<String, Object>> jobList = lineJobDao.queryJobsByCycle(new HashMap<String, Object>());

		for (Map<String, Object> job : jobList) {
			// 查询巡线人员（1.根据区域、干线等级和计划查询所有当天没有被分配巡线任务的人，2.根据巡线段获取所有的巡线人员）
			List<Map<String, Object>> staffList = lineJobDao.queryStaffsByJob(job);
			for (Map<String, Object> staff : staffList) {
				Map<String, Object> taskMap = new HashMap<String, Object>();
				taskMap.put("INSPECT_ID", staff.get("INSPECT_ID"));
				taskMap.put("JOB_ID", job.get("JOB_ID"));
				taskMap.put("TASK_NAME", job.get("JOB_NAME") + DateUtil.getDate());
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
					List<Map<String, Object>> outsiteTasks = lineJobDao.queryOutsiteTaskByUser(taskMap);
					if (null == outsiteTasks || outsiteTasks.size() == 0) {
						taskMap.put("INSPECT_DATE", DateUtil.getDate());
						lineJobDao.insertTaskOutSite(taskMap);// 插入任务关联的外力点
					}
				} else if ("2".equals(circle)) {
					List<Map<String, Object>> outsiteTasks = lineJobDao.queryOutsiteTaskByUser(taskMap);
					if (null == outsiteTasks || outsiteTasks.size() == 0) {
						taskMap.put("INSPECT_DATE", DateUtil.getDate());
						lineJobDao.insertTaskOutSite(taskMap);// 插入任务关联的外力点

						taskMap.put("INSPECT_DATE", DateUtil.getNextDate());
						lineJobDao.insertTaskOutSite(taskMap);// 插入任务关联的外力点
					}
				}
			}
		}
	}

	@Override
	public Map<String, Object> getOtherLoginInfo(Map<String, Object> param) {
		List<Map<String, Object>> loings = paramDao.getOtherLoginInfo(param);

		if(null != loings && loings.size() > 0){
			return loings.get(0);
		}
		
		return null;
	}

	@Override
	public Map<String, Object> getIsLoginInfo(Map<String, Object> param) {
		List<Map<String, Object>> loings = paramDao.getIsLoginInfo(param);

		if(null != loings && loings.size() > 0){
			return loings.get(0);
		}
		
		return null;
	}
	
	
	
	@Override
	public Map<String, Object> getOnlineLoginInfo(Map<String, Object> param) {
		List<Map<String, Object>> loings = paramDao.getOnlineLoginInfo(param);

		if(null != loings && loings.size() > 0){
			return loings.get(0);
		}
		
		return null;
	}
	
}
