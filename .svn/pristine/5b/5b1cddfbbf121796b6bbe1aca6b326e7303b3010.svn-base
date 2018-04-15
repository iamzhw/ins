package com.cableCheck.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.cableCheck.dao.GisCompareDao;
import com.cableCheck.service.GisCompareService;

@SuppressWarnings("all")
@Transactional
@Service
public class GisCompareServiceImpl implements GisCompareService{
	@Resource
	private GisCompareDao gisCompareDao;

	@Override
	public Map<String, Object> queryGIS(HttpServletRequest request, UIPage pager) {
		String area_id = request.getParameter("area_id");
		String son_area_id = request.getParameter("son_area_id");
		String whwg = request.getParameter("whwg");
		String sblx = request.getParameter("sblx");
		String equipmentCode = request.getParameter("equipmentCode");
		String EQUIPMENT_NAME = request.getParameter("EQUIPMENT_NAME");
		Map map =new HashMap();
		map.put("area_id", area_id);
		map.put("son_area_id", son_area_id);
		map.put("whwg", whwg);
		map.put("sblx", sblx);
		map.put("equipmentCode", equipmentCode);
		map.put("EQUIPMENT_NAME", EQUIPMENT_NAME);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map<String, Object>> gisEqpList = gisCompareDao.query(query);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", gisEqpList);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> exportGIS(HttpServletRequest request) {
		String area_id = request.getParameter("AREA_ID");
		String son_area_id = request.getParameter("SON_AREA_ID");
		String whwg = request.getParameter("whwg");
		String sblx = request.getParameter("sblx");
		String equipmentCode = request.getParameter("equipmentCode");
		String EQUIPMENT_NAME = request.getParameter("EQUIPMENT_NAME");
		Map map =new HashMap();
		map.put("area_id", area_id);
		map.put("son_area_id", son_area_id);
		map.put("whwg", whwg);
		map.put("sblx", sblx);
		map.put("equipmentCode", equipmentCode);
		map.put("EQUIPMENT_NAME", EQUIPMENT_NAME);
		List<Map<String, Object>> gisEqpList = gisCompareDao.exportGis(map);
		return gisEqpList;
	}

}
