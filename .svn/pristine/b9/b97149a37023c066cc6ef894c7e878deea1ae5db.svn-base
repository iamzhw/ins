/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service.impl;

import icom.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.dao.AutoTrackDao;
import com.linePatrol.dao.LineInfoDao;
import com.linePatrol.dao.SiteDao;
import com.linePatrol.service.AutoTrackService;
import com.linePatrol.util.StringUtil;
import com.system.sys.OnlineUserListener;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class AutoTrackServiceImpl implements AutoTrackService {

    @Resource
    private AutoTrackDao autoTrackDao;
    @Resource
    private SiteDao siteDao;
    @Resource
    private LineInfoDao lineInfoDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.autoTrackService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = autoTrackDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.autoTrackService#save(java.util.Map)
     */
    @Override
    public void autoTrackSave(Map<String, Object> para) {
	// TODO Auto-generated method stub
	autoTrackDao.autoTrackSave(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.autoTrackService#update(java.util.Map)
     */
    @Override
    public void autoTrackUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	autoTrackDao.autoTrackUpdate(para);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.autoTrackService#delete(java.util.Map)
     */
    @Override
    public void autoTrackDelete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    autoTrackDao.autoTrackDelete(idsArray[i]);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.autoTrackService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> findById(String id) {
	// TODO Auto-generated method stub
	return autoTrackDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
	// TODO Auto-generated method stub
	return autoTrackDao.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.AutoTrackService#getTheTrack(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> getTheTrack(Map<String, Object> para) {
	// TODO Auto-generated method stub
	// my97 和oracle时间格式问题
	if (StringUtil.isNOtNullOrEmpty(para.get("start_time").toString())) {
	    String startTime = para.get("start_time").toString();
	    startTime = startTime.substring(0, startTime.indexOf(":"));
	    int st = Integer.parseInt(startTime);
	    if (st < 10) {
	    	startTime = "0" + para.get("start_time").toString();
	    }else{
	    	startTime = para.get("start_time").toString();
	    }
	    para.put("start_time", startTime);
	}
	if (StringUtil.isNOtNullOrEmpty(para.get("end_time").toString())) {
	    String endTime = para.get("end_time").toString();
	    endTime = endTime.substring(0, endTime.indexOf(":"));
	    int et = Integer.parseInt(endTime);
	    if (et < 10) {
	    	endTime = "0" + para.get("end_time").toString();
	    }else{
	    	endTime = para.get("end_time").toString();
	    }
	    para.put("end_time", endTime);
	}

	return autoTrackDao.getTheTrack(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.linePatrol.service.AutoTrackService#getTheveryDayLineInfo(java.util
     * .Map)
     */
    @Override
    public Map<String, Object> getTheveryDayLineInfo(Map<String, Object> para) {
	// TODO Auto-generated method stub
	// my97 和oracle时间格式问题
	if (StringUtil.isNOtNullOrEmpty(para.get("start_time").toString())) {
	    String startTime = para.get("start_time").toString();
	    startTime = startTime.substring(0, startTime.indexOf(":"));
	    int st = Integer.parseInt(startTime);
	    if (st < 10) {
	    	startTime = "0" + para.get("start_time").toString();
	    }else{
	    	startTime = para.get("start_time").toString();
	    }
	    para.put("start_time", startTime);
	}
	if (StringUtil.isNOtNullOrEmpty(para.get("end_time").toString())) {
	    String endTime = para.get("end_time").toString();
	    endTime = endTime.substring(0, endTime.indexOf(":"));
	    int et = Integer.parseInt(endTime);
	    if (et < 10) {
	    	endTime = "0" + para.get("end_time").toString();
	    }else{
	    	endTime = para.get("end_time").toString();
	    }
	    para.put("end_time", endTime);
	}

	// 查询巡线段id
	List<String> ids = autoTrackDao.getTheveryDayLineInfoIds(para);
	// Map<String, Object> para = new HashMap<String, Object>();

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	Map<String, Object> lineColor = new HashMap<String, Object>();

	if(para.get("site_type")!=null && !"".equals(para.get("site_type"))){
		for (int i = 0; i < ids.size(); i++) {
			String line_id = ids.get(i);
			para.put("line_id", line_id);
			List<Map<String, Object>> sitelist = siteDao.findSiteByLine2(para);
			Map<String, Object> res = new HashMap<String, Object>();
			res.put(line_id, sitelist);
			list.add(res);
			
			Map<String, Object> lineInfo = lineInfoDao.findById(line_id);
			if (lineInfo != null) {
				lineColor.put(line_id, lineInfo.get("LINE_COLOR"));
			}
			
		}
	}
	String lineIds = "";
	for (int i = 0; i < ids.size(); i++) {
	    lineIds = lineIds + ids.get(i) + ",";
	}
	if (StringUtil.isNOtNullOrEmpty(lineIds)) {
	    lineIds = lineIds.substring(0, lineIds.length() - 1);
	}

	Map<String, Object> res = new HashMap<String, Object>();
	res.put("theveryDayLineInfo", list);
	res.put("lineIds", lineIds);
	res.put("lineColor", lineColor);

	// 查询巡线员外力点
	String inspact_id = para.get("inspact_id").toString();
	List<Map<String, Object>> outSiteList = autoTrackDao.getOutsiteByInspector(inspact_id);
	res.put("outSiteList", outSiteList);

	return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.linePatrol.service.AutoTrackService#getTheRealTimeTrack(java.util
     * .Map)
     */
    @Override
    public List<Map<String, Object>> getTheRealTimeTrack(Map<String, Object> para) {
	// TODO Auto-generated method stub

	List<String> personList = autoTrackDao.getPersonList(para);
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < personList.size(); i++) {
	    String id = personList.get(i);
	    sb.append(id + ",");
	}
	String personIdS = sb.deleteCharAt(sb.length() - 1).toString();
	para.put("personIdS", personIdS);
	//List<Map<String, Object>> lineSiteByRealTime=autoTrackDao.getLineSiteByRealTime(para);
	//List<Map<String, Object>> realTimeTrack =autoTrackDao.getTheRealTimeTrack(para);

	return autoTrackDao.getTheRealTimeTrack(para);
    }

	@Override
	public List<Map<String, Object>> selTrackForDG(Map<String, Object> map) {
		List<Map<String, Object>> list = autoTrackDao.selTrackForDG(map);		
		return list;
	}

	@SuppressWarnings("finally")
	@Override
	public String getPositionOfTeam(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
//		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		Map<String, Object> paramap=new HashMap<String, Object>();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		paramap.put("user_id", user_id);
		try {
			resultMap.put("teamLists", autoTrackDao.getPositionOfTeam(paramap));
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		}finally{
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String selPersonOfTeam(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
//		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		Map<String, Object> paramap=new HashMap<String, Object>();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		paramap.put("user_id", user_id);
		try {
			resultMap.put("teamLists", autoTrackDao.selPersonOfTeam(paramap));
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		}finally{
			return JSONObject.fromObject(resultMap).toString();
		}
	}
	
	public List<Map<String, Object>> getLineSiteByRealTime(Map<String, Object> para){
		return autoTrackDao.getLineSiteByRealTime(para);
		
	}

	@Override
	public List<Map<String, Object>> getLeastFive(Map<String, Object> para) {
		return  autoTrackDao.getLeastFive(para);
	}

	@Override
	public List<Map<String, Object>> getElsePoints(Map<String, Object> para) {
		return autoTrackDao.getElsePoints(para);
	}

	@Override
	public List<Map<String, Object>> getAlarmPoints(Map<String, Object> map) {		
		return autoTrackDao.getAlarmPoints(map);
	}
}
