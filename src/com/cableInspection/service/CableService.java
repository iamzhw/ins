package com.cableInspection.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import util.page.UIPage;
@SuppressWarnings("all")
public interface CableService {

	public Map<String, Object> query(HttpServletRequest request, UIPage pager);
	
	public String queryPoints(HttpServletRequest request);
	
	public void saveCabel(HttpServletRequest request);
	
	public Map deleteCable(HttpServletRequest request);
	
	public String getCable(HttpServletRequest request);
	
	public String  getAreaName(HttpServletRequest request);
	
	public List<Map> queryEquipmentType();
	
	public List queryDept(HttpServletRequest request);
	
	public String getPointsInCable(HttpServletRequest request);

	public void saveEditedCable(HttpServletRequest request);
	
	public List getPointsByZone(HttpServletRequest request);
	
	/**
	 * 定时任务，更新计划的签到点数
	 */
	public void updateCablePoint();
	
	public Map editPointPage(HttpServletRequest request);
	
	public Map editPoint(HttpServletRequest request);
	
	public Map<String, Object> getCableByName(HttpServletRequest request,UIPage pager);
	
	public List<Map<String, Object>> exportCableByName(HttpServletRequest request);
	
	public String getWellLocation(HttpServletRequest request);
	
	public List<Map> exportLinePoint(HttpServletRequest request);
	
	public JSONObject editLinePoint(HttpServletRequest request, MultipartFile file);
	
	public Map<String, Object> queryParentLine(HttpServletRequest request,UIPage pager);
	
	public String addNewParentLine(HttpServletRequest request);
	
	public String updateParentLineForLine(HttpServletRequest request);
	
	public Map<String, Object> getCableSectionById(HttpServletRequest request,UIPage pager);
}
