package com.inspecthelper.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import util.page.Query;
import util.page.UIPage;

import com.inspecthelper.dao.EquipDao;
import com.inspecthelper.service.EquipService;

@SuppressWarnings("all")
@Service
public class EquipServiceImpl implements EquipService {
	
	@Resource
	private EquipDao equipDao;

	@Override
	public Map<String, Object> getEquipList(HttpServletRequest request, UIPage pager) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("equId", request.getParameter("equId"));
		map.put("equCode", request.getParameter("equCode"));
		map.put("equName", request.getParameter("equName"));
		map.put("resType", request.getParameter("resType"));
		map.put("resTypeId", request.getParameter("resTypeId"));
		map.put("state", request.getParameter("state"));
		map.put("manaArea", request.getParameter("manaArea"));
		map.put("areaName", request.getParameter("areaName"));
		map.put("sonAreaName", request.getParameter("sonAreaName"));
		map.put("areaId", request.getParameter("areaId"));
		map.put("staffName", request.getParameter("staffName"));
		map.put("manaType", request.getParameter("manaType"));
		map.put("staff", request.getParameter("staff"));
		map.put("ownCompany", request.getParameter("ownCompany"));
		map.put("equpAddress", request.getParameter("equpAddress"));
		map.put("troubleState", request.getParameter("troubleState"));
		map.put("equpAreaId", request.getParameter("equpAreaId"));
		
		//封装查询条件
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> olists = equipDao.getEquipList(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	public Map<String, Object> getResInsHistory(HttpServletRequest request, UIPage pager){
		
		//封装查询条件
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(getHistoryParamMap(request));
		List<Map> olists = equipDao.getResInsHistory(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	/**
	 * 获取设备巡检记录导出列表
	 * 
	 * @param query
	 * @return
	 */
	public List<Map> getResInsHistoryList(HttpServletRequest request){
		
		//封装查询条件
		Query query = new Query();
		query.setPager(new UIPage());
		query.setQueryParams(getHistoryParamMap(request));
		List<Map> olists = equipDao.getResInsHistory(query);
		return olists;
	}
	
	private Map<String,Object> getHistoryParamMap(HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("equpAddress", request.getParameter("equpAddress"));
		map.put("sn", request.getParameter("sn"));
		map.put("odfCheck", request.getParameter("odfCheck"));
		map.put("resType", request.getParameter("resType"));
		map.put("areaName", request.getParameter("areaName"));
		map.put("sonAreaName", request.getParameter("sonAreaName"));
		map.put("ownCompany", request.getParameter("ownCompany"));
		map.put("state", request.getParameter("state"));
		map.put("type", request.getParameter("type"));
		map.put("staffName", request.getParameter("staffName"));
		map.put("equipmentCode", request.getParameter("equipmentCode"));
		map.put("startDate", request.getParameter("startDate"));
		map.put("endDate", request.getParameter("endDate"));
		map.put("startDate1", request.getParameter("startDate1"));
		map.put("endDate1", request.getParameter("endDate1"));
		map.put("staffOwnCompany", request.getParameter("staffOwnCompany"));
		return map;
	}
	
	@Override
	public Map getEquip(HttpServletRequest request){
		String equipmentId = String.valueOf(request.getParameter("equipIds"));
		return equipDao.getEquip(equipmentId);
	}
	
	@Override
	public void btnhealthy(HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//获取选中的设备ID
		map.put("equipIds", request.getParameter("equipIds"));
		
		//状态为1为健康
		map.put("state","1");
		equipDao.modifyEquip(map);
	}
	
	
	public void modifyEquip(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String equipIds = request.getParameter("equipIds");
		String areaName = request.getParameter("areaName");
		String sonAreaName = request.getParameter("sonAreaName");
		String manaType = request.getParameter("manaType");
		String equpAddress = request.getParameter("equpAddress");
		String state = request.getParameter("state");
		String state_ = request.getParameter("state_");
		map.put("equipIds", equipIds);
		map.put("areaName", areaName);
		map.put("sonAreaName", sonAreaName);
		map.put("manaType", manaType);
		map.put("equpAddress", equpAddress);
		map.put("state", state);
		map.put("state_", state_);
		equipDao.modifyEquip(map);
	}
	
	/**
	 * 分配设备检查人员
	 * 
	 * @param map
	 * @return
	 */
	public void allotEquip(HttpServletRequest request){
		String equipIds = request.getParameter("equipIds");
		String type = request.getParameter("type");
		String staffId = request.getParameter("staff_id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("equipIds", equipIds);
		map.put("type", type);
		map.put("staffId", staffId);
		equipDao.allotEquip(map);
	}
	
	/**
	 * 批量清除检查员
	 * 
	 * @param map
	 * @return
	 */
	public void btnclearallot(HttpServletRequest request){
		String equipIds = request.getParameter("equipIds");
		String type = request.getParameter("type");
		String staffId = request.getParameter("staff_id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("equipIds", equipIds);
		map.put("type", type);
		equipDao.btnclearallot(map);
	}
	
	@Override
	public List<Map> getCompanyList(Map paramMap){
		return equipDao.getCompanyList(paramMap);
	}
	
	@Override
	public Map getFtthCount(Map paramMap){
		List<Map> ftthList = equipDao.getFtthCount(paramMap);
		if(null != ftthList && ftthList.size() > 0){
			return ftthList.get(0);
		}
		
		return null;
	}
}
