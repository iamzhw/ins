/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.outsite.service.impl;

import icom.axx.dao.OutSiteInterfaceDao;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.ExcelUtil;
import com.linePatrol.dao.gldManageDao;
import com.linePatrol.util.DateUtil;
import com.outsite.dao.MainOutSiteDao;
import com.outsite.service.MainOutSiteService;
import com.system.dao.ParamDao;
import com.system.dao.StaffDao;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class MainOutSiteServiceImpl implements MainOutSiteService {

    @Autowired
    private MainOutSiteDao mainOutSiteDao;
    @Resource
    private gldManageDao gldManageDao;
    
    @Resource
	private OutSiteInterfaceDao outSiteInterfaceDao;
    
    @Resource
	private ParamDao paramDao;
    
    @Resource
	private StaffDao staffDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mainOutSiteService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = mainOutSiteDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }
    
    @Override
    public void queryByDown(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response){
    	List<String> title = Arrays.asList(new String[] { "外力点名称", "外力点状态",
				"受影响光缆 ", "外力影响等级 ", "干线等级", "创建时间 ","开始时间 ","预计结束时间","上报人员 ","流程状态 "});
		List<String> code = Arrays.asList(new String[] { "SITE_NAME","CON_STATUS", "AFFECTED_FIBER", "SITE_DANGER_LEVEL", "FIBER_LEVEL",
				"CREATION_TIME" ,"CON_STARTDATE","PRE_ENDDATE","USER_NAME","FLOW_STATUS"});
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("area_id", para.get("area_id"));
		map.put("p_con_status", para.get("p_con_status"));
		map.put("p_end_time", para.get("p_end_time"));
		map.put("p_start_time", para.get("p_start_time"));
		map.put("org_id", para.get("org_id"));
		map.put("p_site_name", para.get("p_site_name"));
		List<Map<String, Object>> data = mainOutSiteDao.queryDown(para);
		String fileName = "外力点情况报表";
		String firstLine = "外力点情况报表";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    @Override
    public Map<String, Object> query_recordindex(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = mainOutSiteDao.query_recordindex(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }
    
    
    
    /**
     * 
     *检查记录导出功能
     */
    @Override
    public void recordIndexDown(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response){
    	List<String> title = Arrays.asList(new String[] { "外力点名称 ", "检查人",
				"组织名称  ", "检查时间  ", "发现问题 "});
		List<String> code = Arrays.asList(new String[] { "OUT_SITE_NAME","STAFF_NAME", "ORG_NAME", "CHECK_TIME","PROBLEM_MES"});
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("area_id", para.get("area_id"));
		map.put("p_check_name", para.get("p_check_name"));
		map.put("p_end_time", para.get("p_end_time"));
		map.put("p_start_time", para.get("p_start_time"));
		map.put("org_id", para.get("org_id"));
		map.put("p_site_name", para.get("p_site_name"));
		List<Map<String, Object>> data = mainOutSiteDao.query_recordindexDown(para);
		String fileName = "外力点检查记录情况报表";
		String firstLine = "外力点检查记录情况报表";
		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    
    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mainOutSiteService#save(java.util.Map)
     */
    @Override
    public void mainOutSiteSave(Map<String, Object> para) {
	// TODO Auto-generated method stub
	mainOutSiteDao.mainOutSiteSave(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mainOutSiteService#update(java.util.Map)
     */
    @Override
    public void mainOutSiteUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub

	String pre_enddate = para.get("pre_enddate").toString();
	pre_enddate = pre_enddate.substring(0, 10);
	String con_startdate = para.get("con_startdate").toString();
	con_startdate = con_startdate.substring(0, 10);
	para.put("con_startdate", con_startdate);
	para.put("pre_enddate", pre_enddate);

	mainOutSiteDao.mainOutSiteUpdate(para);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mainOutSiteService#delete(java.util.Map)
     */
    @Override
    public void mainOutSiteDelete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    mainOutSiteDao.mainOutSiteDelete(idsArray[i]);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mainOutSiteService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> findById(String id) {
	// TODO Auto-generated method stub
	Map<String, Object> map = mainOutSiteDao.findById(id);
	String idss = map.get("AFFECTED_FIBER").toString();

	Map<String, Object> para = new HashMap<String, Object>();
	para.put("idss", idss);
	List<Map<String, Object>> cableList = gldManageDao.getCableByids(para);
	String affected_fiber_names = "";
	for (int i = 0; i < cableList.size(); i++) {
	    String cable_name = cableList.get(i).get("CABLE_NAME").toString();
	    affected_fiber_names += cable_name + ",";
	}
	map.put("AFFECTED_FIBER_NAMES", affected_fiber_names);

	return map;
    }

    @Override
    public List<Map<String, Object>> findAll() {
	// TODO Auto-generated method stub
	return mainOutSiteDao.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#getCzs(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> getCzs(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return mainOutSiteDao.getCzs(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#findJxsByid(java.util.Map)
     */
    @Override
    public Map<String, Object> findJxsByid(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return mainOutSiteDao.findJxsByid(map);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#jxsUpdate(java.util.Map)
     */
    @Override
    public void jxsUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	mainOutSiteDao.jxsUpdate(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#getCheckRecord(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> getCheckRecord(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return mainOutSiteDao.getCheckRecord(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#getSignInfo(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> getSignInfo(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return mainOutSiteDao.getSignInfo(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#getStayInfo(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> getStayInfo(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return mainOutSiteDao.getStayInfo(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#getMstwInfo(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> getMstwInfo(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return mainOutSiteDao.getMstwInfo(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#mstwUpdate(java.util.Map)
     */
    @Override
    public void mstwUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	mainOutSiteDao.mstwUpdate(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.outsite.service.MainOutSiteService#findMstwByid(java.util.Map)
     */
    @Override
    public Map<String, Object> findMstwByid(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return mainOutSiteDao.findMstwByid(map);
    }

    @Override
    public void saveElebar(String pointsJsons, String plan_id, String out_site_id, String staffId,
	    String ebWidth, String ebLength) {

	JSONObject jsonObject = JSONObject.fromObject(pointsJsons);

	JSONArray jsonArray = jsonObject.getJSONArray("points");
	Map<String, Object> map = new HashMap<String, Object>();

	for (int i = 0; i < jsonArray.size(); i++) {
	    String lng = jsonArray.getJSONObject(i).getString("lng");
	    String lat = jsonArray.getJSONObject(i).getString("lat");
	    map.put("plan_id", plan_id);
	    map.put("x", lng);
	    map.put("y", lat);
	    map.put("input_userid", staffId);
	    map.put("input_time", DateUtil.getDateAndTime());
	    map.put("site_order", i);

	    mainOutSiteDao.saveOsInputPoints(map);

	}
	// 更新长度 宽度

	Map<String, Object> map2 = new HashMap<String, Object>();
	map2.put("ebWidth", ebWidth);
	map2.put("ebLength", ebLength);
	map2.put("out_site_id", out_site_id);
	map2.put("is_add_elebar", 1);// 已经添加围栏
	mainOutSiteDao.mainOutSiteUpdate(map2);

    }

    @Override
    public List<Map<String, Object>> getElebar(String out_site_id) {
	// TODO Auto-generated method stub
	return mainOutSiteDao.getElebar(out_site_id);
    }
    
    @Transactional
    @Override
    public synchronized void updateElebar(String pointsJsons, String plan_id, String out_site_id,
	    String staffId, String ebWidth, String ebLength) {
	JSONObject jsonObject = JSONObject.fromObject(pointsJsons);

	JSONArray jsonArray = jsonObject.getJSONArray("points");
	Map<String, Object> map = new HashMap<String, Object>();
	
	// 删除原来的围栏
	int count = mainOutSiteDao.delOldElebar(plan_id);
	String courrentDate = DateUtil.getDate();
	String records = courrentDate + "#deleteCount:" + count+"#planid:"+plan_id;
	mainOutSiteDao.intoRecordTable(records);
	
	map.put("plan_id", plan_id);
	for (int i = 0; i < jsonArray.size(); i++) {
	    String lng = jsonArray.getJSONObject(i).getString("lng");
	    String lat = jsonArray.getJSONObject(i).getString("lat");

	    map.put("x", lng);
	    map.put("y", lat);
	    map.put("input_userid", staffId);
	    map.put("input_time", DateUtil.getDateAndTime());
	    map.put("site_order", i);
	    mainOutSiteDao.saveOsInputPoints(map);

	}
	// 更新长度 宽度
	Map<String, Object> map2 = new HashMap<String, Object>();
	map2.put("ebWidth", ebWidth);
	map2.put("ebLength", ebLength);
	map2.put("out_site_id", out_site_id);

	mainOutSiteDao.mainOutSiteUpdate(map2);

    }

	@Override
	public List<Map<String, Object>> getFlowDetail(Map<String, Object> map) {
		return mainOutSiteDao.getFlowDetail(map);
	}

	@Override
	public List<Map<String, Object>> findConfirmTbl(String id) {
		return mainOutSiteDao.findConfirmTbl(id);
	}

	@Override
	public String selOrgName(Object fiber_eponsible_by) {
		return mainOutSiteDao.selOrgName(fiber_eponsible_by);
	}

	@Override
	public List<Map<String, Object>> queryOutSiteRate(Map<String, Object> map) {
		map.put("areaId", map.get("AREA_ID"));
		// 非隐患
		map.put("paramName", "OutSiteStay");
		String OutSiteStay = paramDao.getParamValueByName(map);

		// 隐患
		map.put("paramName", "UnSafeOutSiteStay");
		String UnSafeOutSiteStay = paramDao.getParamValueByName(map);

		map.put("OutSiteStay", OutSiteStay);
		map.put("UnSafeOutSiteStay", UnSafeOutSiteStay);

		List<Map<String, Object>> resultList = mainOutSiteDao.queryOutSiteRate(map);
		return resultList;
	}

	@Override
	public void outSiteRateDownLoad(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> data = queryOutSiteRate(para);
		List<String> title = Arrays.asList(new String[] { "巡线员 ", "区域", "时间", "计划外力点数", "实际外力点数",
				"检查到位率","全市外力点平均到位率"});
			List<String> code = Arrays.asList(new String[] { "STAFF_NAME", "ORG_NAME", "START_DATE",
				"OUT_SITE_ID", "ACTUAL_COUNT", "SITE_RATE","AVG_SITE_RATE"});
			String fileName = "外力点巡检到位率报表";
			String firstLine = "外力点巡检到位率报表";

			try {
			    ExcelUtil.generateExcelAndDownload(title, code, data, request, response, fileName,
				    firstLine);
			} catch (FileNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
	}

	@Override
	public List<Map<String, Object>> getAllGroup(String area_id) {
		return mainOutSiteDao.getAllGroup(area_id);
	}

	@Override
	public List<Map<String, Object>> depthProbeQuery(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		return mainOutSiteDao.depthProbeQuery(para);
		
	}
	@Override
	public List<Map<String, Object>> getOuteSiteLocation(Map<String, Object> map) {
		
		
		return mainOutSiteDao.getOuteSiteLocation(map);
	}
	

}
