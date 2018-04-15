package com.linePatrol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.PointManageDao;
import com.cableInspection.serviceimpl.PointManageServiceImpl;
import com.linePatrol.dao.LineInfoDao;
import com.linePatrol.dao.SiteDao;
import com.linePatrol.service.SiteService;
import com.util.ExcelUtil;
import com.util.StringUtil;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
@SuppressWarnings("all")
public class SiteServiceImpl implements SiteService {

    @Resource
    private SiteDao siteDao;
    
    @Resource
	private PointManageDao pointManageDao;
    
    @Resource
	private LineInfoDao lineInfoDao;

    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);
	
		List<Map> olists = siteDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
    }

    @Override
    public void siteSave(Map<String, Object> para) {
    	siteDao.siteSave(para);
    }

    @Override
    public void siteUpdate(Map<String, Object> para) {
    	siteDao.siteUpdate(para);
    }

    @Override
    public void siteDelete(String ids) {

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    siteDao.siteDelete(idsArray[i]);
	}

    }

    @Override
    public Map<String, Object> findById(String id) {
    	return siteDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
	return siteDao.findAll();
    }

    @Override
    public List<Map<String, Object>> findSiteByLine(Map<String, Object> para) {

	return siteDao.findSiteByLine(para);
    }
    
    @Override
    public List<Map<String, Object>> getSitesByRelayId(String relayId){
    	
    	return siteDao.getSitesByRelayId(relayId);
    }
    
    @Override
    public String deleteSite(String ids){
    	
    	String msg = "001";//成功
    	Map param = new HashMap();
    	param.put("ids", ids);
    	List<Map<String, Object>> siteList = siteDao.ifSiteInPart(param);
    	if(siteList.size()>0 && siteList != null){
    		msg = "002";//此设施点存于与XXXX巡线段中，请先到此巡线段中解除此设施点
    	}else{
    		try{
    			siteDao.deleteSiteById(param);
    		}catch(Exception e){
    			msg = "003";//失败
    			e.printStackTrace();
    		}
    	}
    	return msg;
    }

    public static void main(String[] args) {
	String a = "1,3,";
	String[] aa = a.split(",");
	System.out.println();
    }

    @Override
    public List<Map<String, Object>> getSiteByRelayId(Map<String, Object> para) {

    	return siteDao.getSiteByRelayId(para);
    }

    @Override
    public void update(Map<String, Object> para) {
    	siteDao.siteUpdate(para);
    }

    @Override
    public List<Map<String, Object>> getSitesByIds(String markersIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("markersIds", markersIds);
		return siteDao.getSitesByIds(map);
    }

	@Override
	public List<Map<String, Object>> getSitePhotoList(String id) {
		return siteDao.getSitePhotoList(id);
	}

	@Override
	public Map<String, Object> querySiteList(Map<String, Object> para,
			UIPage pager) {
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);

		List<Map<String, Object>> olists = siteDao.querySiteList(para);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Object importDo(HttpServletRequest request, MultipartFile file) {
		JSONObject result = new JSONObject();
		String repeatRows = "";
		String errorRows = "";
		try {
			String startSiteId  = request.getParameter("startSiteId");
			String lineId = request.getParameter("lineId");
			String relayId = request.getParameter("relayId");
			
			Map<String,Object> sParam = new HashMap<String,Object>();
			sParam.put("line_id", lineId);
			List<Map<String, Object>> oldSiteList = siteDao.querySiteList(sParam);

			ExcelUtil parse = new ExcelUtil(file.getInputStream());
			List<List<String>> datas = parse.getDecimalDatas(9);
			Map<String, Object> params = null;
			List<String> data = null;
			List<Integer> addSiteIds = new ArrayList<Integer>();
			for (int i = 0, j = datas.size(); i < j; i++) {
				//0:点名称;1:是否为GPS坐标;2:经度;3:纬度;4:地址;5:区域;6:点属性;7:匹配距离;8:是否为关键点
				params = new HashMap<String, Object>();
				params.put("line_id", lineId);
				data = datas.get(i);
				// 验证是否为空数据
				if (StringUtil.isEmpty(data.get(0))) {
					continue;
				}
				// 验证是否为GPS坐标
				if (StringUtil.isEmpty(data.get(1)) || !("是".equals(data.get(1)) || "否".equals(data.get(1)))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				// 验证经度
				if (StringUtil.isEmpty(data.get(2)) || !NumberUtils.isNumber(data.get(2))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				// 验证纬度
				if (StringUtil.isEmpty(data.get(3)) || !NumberUtils.isNumber(data.get(3))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				// 验证区域
				String area_id = getAreaId(data.get(5));
				if (StringUtil.isEmpty(data.get(5)) || StringUtil.isEmpty(area_id)) {
					errorRows += "," + (i + 2);
					continue;
				}
				//验证匹配距离
				if(StringUtil.isEmpty(data.get(7))){
				}else if(!NumberUtils.isNumber(data.get(7))){
					errorRows += "," + (i + 2);
					continue;
				}else{
					params.put("site_match", Double.parseDouble(data.get(7)));
				}
				// 验证是否关键点
				if (StringUtil.isEmpty(data.get(8)) || !("是".equals(data.get(8)) || "否".equals(data.get(8)))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				
				params.put("site_name", data.get(0));
				params.put("longitude", Double.parseDouble(data.get(2)));
				params.put("latitude", Double.parseDouble(data.get(3)));
				if("是".equals(data.get(1))){
					params.put("coordinate_type", 2);
					params.put("longitude_gps", Double.parseDouble(data.get(2)));
					params.put("latitude_gps", Double.parseDouble(data.get(3)));
				}

				List<Map<String, Object>> siteList = siteDao.querySiteList(params);
				if(siteList.size() > 0){
					repeatRows += "," + (i + 2);
					continue;
				}
				params.put("address", data.get(4));
				params.put("area_id", Integer.parseInt(area_id));
				params.put("site_property", data.get(6));
				params.put("site_type", "是".equals(data.get(8)) ? 1 : 2);
				params.put("relay_id", relayId);
				
				int site_id = siteDao.getSiteSeq();
				params.put("site_id", site_id);
				// 保存点
				siteDao.addSite(params);
				
				//保存巡线段和巡线点关联表
				params.put("site_order", 999);
				lineInfoDao.insertSite2LineInfo(params);
				
				addSiteIds.add(site_id);
			}
			
			//组装
			List<Integer> siteIds = new ArrayList<Integer>();
			for (Map<String, Object> map : oldSiteList) {
				siteIds.add(Integer.parseInt(map.get("SITE_ID").toString()));
			}
			if(StringUtil.isEmpty(startSiteId)){					//只选了第一个
				siteIds.addAll(0, addSiteIds);
			}else{
				siteIds.addAll(siteIds.indexOf(Integer.parseInt(startSiteId))+1, addSiteIds);
			}
			//排序
			Map<String,Object> orderMap = null;
			for(int i=0; i<siteIds.size(); i++){
				orderMap = new HashMap<String,Object>();
				orderMap.put("site_id", siteIds.get(i));
				orderMap.put("line_id", lineId);
				orderMap.put("site_order", i);
				siteDao.updateSiteOrder(orderMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", false);
			result.put("message", "文件格式错误！");
			return result;
		}
		if (!"".equals(repeatRows) || !"".equals(errorRows)) {
			result.put("status", false);
			String message = null;
			if (!"".equals(repeatRows)) {
				message = "编号 " + repeatRows.substring(1) + " 的记录重复：经纬度已存在！";
			}
			if (!"".equals(errorRows)) {
				String str = "编号 " + errorRows.substring(1) + " 的记录数据有误：数据为空或数据不符合规范！";
				if (message == null) {
					message = str;
				} else {
					message += str;
				}
			}
			result.put("message", message);
		} else {
			result.put("status", true);
		}
		return result;
	}
	
	private String getAreaId(String area_name) {
		if (PointManageServiceImpl.AREA.containsKey(area_name)) {
			return PointManageServiceImpl.AREA.get(area_name);
		}
		List<Map<String, String>> result = pointManageDao.getAreaNameById(area_name);
		if (result == null || result.get(0) == null) {
			return null;
		} else {
			String area_id = result.get(0).get("AREA_ID");
			PointManageServiceImpl.AREA.put(area_name, area_id);
			return area_id;
		}
	}

}
