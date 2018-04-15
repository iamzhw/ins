package com.linePatrol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.dao.LineInfoDao;
import com.linePatrol.dao.SiteDao;
import com.linePatrol.service.LineInfoService;
import com.linePatrol.util.DateUtil;
import com.system.dao.StaffDao;
import com.util.MapDistance;

/**
 * @author Administrator
 * 
 */
@Service
@SuppressWarnings("all")
@Transactional
public class LineInfoServiceImpl implements LineInfoService {

    @Resource
    private LineInfoDao lineInfoDao;

    @Resource
    private SiteDao siteDao;
    @Resource
    private StaffDao staffDao;

    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);
	
		List<Map> olists = lineInfoDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
    }
    
    /**
     * TODO
     */
    @Override
    public Map<String, Object> queryForMap(Map map) {
    	
    	//巡线点
    	List<Map<String, Object>> siteList = lineInfoDao.queryForMap(map);
    	//巡线段
    	List<Map<String, Object>> lineList = lineInfoDao.querySitePartForMap(map);
    	
    	JSONObject siteInfo = new JSONObject();
    	siteInfo.put("siteList", JSONArray.fromObject(siteList));
    	siteInfo.put("lineList", JSONArray.fromObject(lineList));
    	
    	Map<String, Object> rs = new HashMap<String, Object>();
    	rs.put("siteInfo", siteInfo);
    	return rs;
    }

    @Override
    public void lineInfoSave(HttpServletRequest request) {

		String jsString = request.getParameter("cableObj");
	
		HttpSession session = request.getSession();
		String staffId = session.getAttribute("staffId").toString();
		String areaId = session.getAttribute("areaId").toString();
	
		JSONArray json = JSONArray.fromObject(jsString);
		for (int k = 0; k < json.size(); k++) {
		    Map<String,Object> map = new HashMap<String,Object>();
		    // map.put("line_no",
		    // json.getJSONObject(k).get("line_no").toString());
		    map.put("line_name", json.getJSONObject(k).get("line_name").toString());
		    map.put("inspect_id", json.getJSONObject(k).getString("inspect_id"));
		    map.put("relay_id", json.getJSONObject(k).getString("relay_id"));
		    map.put("area_id", areaId);
		    map.put("create_person", staffId);
		    map.put("create_time", DateUtil.getDateAndTime());
		    map.put("distance", json.getJSONObject(k).getString("distance"));
		    map.put("line_color", json.getJSONObject(k).getString("line_color"));
	
		    String markersId = json.getJSONObject(k).get("markersId").toString();
		    String[] markersIdArray = markersId.split(",");
		    List list = new ArrayList();
	
		    // 插入巡线段
		    String line_id = lineInfoDao.getSiteInfoNextVal();
		    map.put("line_id", line_id);
		    lineInfoDao.lineInfoSave(map);
	
		    // 插入线和点对应关系
		    for (int h = 0; h < markersIdArray.length; h++) {
			Map site2line = new HashMap();
			site2line.put("line_id", line_id);
			site2line.put("site_id", markersIdArray[h]);
			site2line.put("site_order", h);
			lineInfoDao.insertSite2LineInfo(site2line);
		    }
	
		}

    }

    @Override
    public void lineInfoUpdate(HttpServletRequest request) {
	String jsString = request.getParameter("cableObj");

	HttpSession session = request.getSession();
	String staffId = session.getAttribute("staffId").toString();
	String areaId = session.getAttribute("areaId").toString();

	JSONArray json = JSONArray.fromObject(jsString);
	for (int k = 0; k < json.size(); k++) {
	    Map<String, Object> map = new HashMap();

	    map.put("line_name", json.getJSONObject(k).get("line_name").toString());
	    map.put("inspect_id", json.getJSONObject(k).getString("inspect_id"));
	    // map.put("relay_id", json.getJSONObject(k).getString("relay_id"));
	    // map.put("area_id", areaId);
	    map.put("create_person", staffId);
	    //map.put("create_time", DateUtil.getDateAndTime());
	    map.put("distance", json.getJSONObject(k).getString("distance"));
	    map.put("line_color", json.getJSONObject(k).getString("line_color"));

	    String markersId = json.getJSONObject(k).get("markersId").toString();
	    String[] markersIdArray = markersId.split(",");
	    List list = new ArrayList();

	    // 插入巡线段
	    String line_id = json.getJSONObject(k).getString("line_id");
	    map.put("line_id", line_id);
	    lineInfoDao.lineInfoUpdate(map);

	    // 删除原来对应关系
	    lineInfoDao.deleteLine2Site(line_id);
	    // 插入线和点对应关系
	    Map<String, Comparable> site2line = new HashMap();
	    site2line.put("line_id", line_id);
	    for (int h = 0; h < markersIdArray.length; h++) {

		site2line.put("site_id", markersIdArray[h]);
		site2line.put("site_order", h);
		lineInfoDao.insertSite2LineInfo(site2line);
	    }

	}

    }

    @Override
    public void lineInfoDelete(String ids) {

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    lineInfoDao.lineInfoDelete(idsArray[i]);
	}

    }

    @Override
    public Map<String, Object> findById(String id) {
    	return lineInfoDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
    	return lineInfoDao.findAll();
    }

    @Override
    public List<Map<String, Object>> findLineInfoByAreaId(String staffAreaId) {
    	return lineInfoDao.findLineInfoByAreaId(staffAreaId);
    }

    @Override
    public List<Map<String, Object>> getRelayByGl(String gldId, String localId) {
		// 本地中继段
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gldId", gldId);
		map.put("localId", localId);
		return lineInfoDao.getRelayByGl(map);
    }
    
    @Override
    public List<Map<String, Object>> getRelayById(String cableId, String areaId){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("gldId", cableId);
    	map.put("localId", areaId);
    	return lineInfoDao.getRelayByGl(map);
    	
    }

    @Override
    public List<Map<String, Object>> getLocalInspactPerson(String localId) {
    	return lineInfoDao.getLocalInspactPerson(localId);
    }
    /**
     * TODO
     */
    @Override
    public Map<String, Object> findLineInfoById(Map<String, Object> para) {
		// 准备数据 巡线段信息 光缆断 中继段 中继段下面的点 已经连接的点
		String line_id = para.get("line_id").toString();
		String local_id = para.get("local_id").toString();
		String staffId = para.get("staff_id").toString();
	
		Map<String, Object> staffInfo = staffDao.findPersonalInfo(staffId);
		// 巡线段信息
		Map<String, Object> lineInfo = lineInfoDao.findById(line_id);
		// 光缆断 中继段
		Map<String, Object> gldAndRelay = lineInfoDao.getGldAndRelay(line_id);
	
		// 中继段下面所有的巡线点
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("local_id", local_id);
		map.put("relay_id", gldAndRelay.get("RELAY_ID"));
		map.put("line_id", line_id);
	
		List<Map<String, Object>> allSiteList = siteDao.getLocalSitesByRelayId(map);
	
		// 已经连接的点
		List<Map<String, Object>> selectedSiteList = lineInfoDao.getSelectedSiteList(line_id);
	
		// 巡线人
		List<Map<String, Object>> localInspactPerson = lineInfoDao.getLocalInspactPerson(local_id);
	
		Map<String, Object> res = new HashMap<String, Object>();
	
		res.put("lineInfo", lineInfo);
		res.put("gldAndRelay", gldAndRelay);
		res.put("staffInfo", staffInfo);
		res.put("localInspactPerson", localInspactPerson);
		res.put("allSiteList", allSiteList);
		res.put("selectedSiteList", selectedSiteList);
	
		Map<String, Object> siteInfo = new HashMap<String, Object>();
		siteInfo.put("allSiteList", allSiteList);
		siteInfo.put("selectedSiteList", selectedSiteList);
	
		res.put("siteInfo", JSONObject.fromObject(siteInfo).toString().replaceAll("\"", "\\\\\""));
	
		return res;
    }
    
    @Override
    public Map<String, Object> findLineInfoByIds(Map<String, Object> para) {
    	
		// 准备数据 巡线段信息 光缆断 中继段 中继段下面的点 已经连接的点
    	String local_id = para.get("local_id").toString();
		String staffId = para.get("staff_id").toString();
		Map<String, Object> staffInfo = staffDao.findPersonalInfo(staffId);
		// 巡线人
		List<Map<String, Object>> localInspactPerson = lineInfoDao.getLocalInspactPerson(local_id);
		
		// 巡线段信息
		Map<String, Object> lineInfo = new HashMap<String, Object>();
		// 光缆断 中继段
		Map<String, Object> gldAndRelay = new HashMap<String, Object>();
		// 已经连接的点
		List<Map<String, Object>> selectedSiteList = new ArrayList<Map<String,Object>>();
		// 中继段下面所有的巡线点
		List<Map<String, Object>> allSiteList = new ArrayList<Map<String,Object>>();
		
		String lineIds = para.get("lineIds").toString();
		for(String lineId : lineIds.split(",")){
			
			lineInfo.putAll(lineInfoDao.findById(lineId));
			gldAndRelay.putAll(lineInfoDao.getGldAndRelay(lineId));
			selectedSiteList.addAll(lineInfoDao.getSelectedSiteList(lineId));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("local_id", local_id);
			map.put("relay_id", gldAndRelay.get("RELAY_ID"));
			map.put("line_id", lineId);
			allSiteList.addAll(siteDao.getLocalSitesByRelayId(map));
		}
		
		Map<String, Object> res = new HashMap<String, Object>();
	
		res.put("lineInfo", lineInfo);
		res.put("gldAndRelay", gldAndRelay);
		res.put("staffInfo", staffInfo);
		res.put("localInspactPerson", localInspactPerson);
		res.put("allSiteList", allSiteList);
		res.put("selectedSiteList", selectedSiteList);
	
		Map<String, Object> siteInfo = new HashMap<String, Object>();
		siteInfo.put("allSiteList", allSiteList);
		siteInfo.put("selectedSiteList", selectedSiteList);
	
		res.put("siteInfo", JSONObject.fromObject(siteInfo).toString().replaceAll("\"", "\\\\\""));
	
		return res;
    }
    // @Override
    // public Map<String, Object> findLineInfoById(String line_id) {
    //
    // // 巡线段信息
    // Map<String, Object> lineInfo = lineInfoDao.findById(line_id);
    // // 光缆断 中继段
    // Map<String, Object> gldAndRelay = lineInfoDao.getGldAndRelay(line_id);
    //
    // // 中继段下面所有的巡线点
    // List<Map<String, Object>> allSiteList =
    // siteDao.getSiteByRelayId(gldAndRelay
    // .get("RELAY_ID").toString());
    //
    // // 已经连接的点
    // List<Map<String, Object>> selectedSiteList =
    // lineInfoDao.getSelectedSiteList(line_id);
    //
    // Map<String, Object> res = new HashMap<String, Object>();
    // res.put("lineInfo", lineInfo);
    // res.put("gldAndRelay", gldAndRelay);
    // res.put("allSiteList", JSONArray.fromObject(allSiteList));
    // res.put("selectedSiteList", JSONArray.fromObject(selectedSiteList));
    //
    // return res;
    // }

    @Override
    public List<Map<String, Object>> getLocalInspactPerson(Map<String, Object> para) {
    	return lineInfoDao.getLocalInspactPersonWithCondition(para);
    }

    @Override
    public Map<String, Object> showTheLineOnMap(String line_id) {
		// 巡线段信息
		Map<String, Object> lineInfo = lineInfoDao.findById(line_id);
		// 光缆断 中继段
		Map<String, Object> gldAndRelay = lineInfoDao.getGldAndRelay(line_id);
	
		// 中继段下面所有的巡线点
		// List<Map<String, Object>> allSiteList =
		// siteDao.getSiteByRelayId(gldAndRelay
		// .get("RELAY_ID").toString());
	
		// 已经连接的点
		List<Map<String, Object>> selectedSiteList = lineInfoDao.getSelectedSiteList(line_id);
	
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("lineInfo", lineInfo);
		res.put("gldAndRelay", gldAndRelay);
		// res.put("allSiteList", JSONArray.fromObject(allSiteList));
		res.put("selectedSiteList", JSONArray.fromObject(selectedSiteList));
	
		return res;
    }

    @Override
    public List<Map<String, Object>> getLineInfoByRelay(Map<String, Object> para) {
    	return lineInfoDao.getLineByRelayId(para);
    }

    @Override
    public List<Map<String, Object>> getSitesByIds(String markersIds, String lngs, String lats) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("markersIds", markersIds);
		List<Map<String, Object>> sitesList = siteDao.getSitesByIds(map);
	
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		// 只查看1000以内的点
		double lat = Double.parseDouble(lats);
		double lng = Double.parseDouble(lngs);
	
		for (int i = 0; i < sitesList.size(); i++) {
		    Map<String, Object> map2 = sitesList.get(i);
	
		    double latitude = Double.parseDouble(map2.get("LATITUDE").toString());
		    double longitude = Double.parseDouble(map2.get("LONGITUDE").toString());
	
		    double distance = MapDistance.getDistance(lat, lng, latitude, longitude);
		    if (distance <= 1000) {
		    	res.add(map2);
		    }
		}
	
		return res;
    }

	@Override
	public String getOrgByRole(String staffId) {
		return lineInfoDao.getOrgByRole(staffId);
	}
	
	@Override
	public List<Map<String, Object>> getTimeList(){
		return  lineInfoDao.getTimeList();
	}

	@Override
	public List<Map<String, Object>> getLocalPerson(String localId, String orgId) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("localId", localId);
		map.put("orgId", orgId);
		List<Map<String, Object>> result= lineInfoDao.getLocalPerson(map);
		return result;
	}
	//高铁查询本地巡线员
    @Override
    public List<Map<String, Object>> gaotiegetLocalInspactPerson(Map<String, Object> para) {
    	return lineInfoDao.gaotiegetLocalInspactPersonWithCondition(para);
    }

}
