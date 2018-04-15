package com.cableInspection.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cableInspection.model.WellModel;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface CableDao {
	
	public List<Map> query(Query query);
	
	public List<Map> queryPoints(Map map);
	
	public List<Map> queryEquipmentType();
	
	public List<Map> queryDept(Map map);
	
	public List<Map> queryPointId(Map map);
	
	public int insertPoint(Map map);
	
	public void insertLine(Map map);
	
	public void inserLinePoint(Map map);
	
	public void deleteCable(int lineId);
	public void deleteLineCable(int lineId);
	public Map getLineTypeById(int lineId);
	public int getSonLineCount(String s);
	
	public void deletePoint(int pointId);
	
	public List<Map> selectLinePoint(int lineId);
	
	public void deleteLinePoint(int lineId);
	
	public List<Map> getCable(Map map);
	
	public List<Map> queryPoint(Map map);
	
	public List<Map> queryPlanPoint(Map map);
	
	public String queryArea(String staffId);
	
	public String queryAreaName(String areaId);

	public List<Map>  getPointsInCable(Map<String, Object> param);

	public void updateLine(Map map);

	public void deletePointInLine(Map map);
	
	public List<Map> queryNotDistributePoints(Map map);
	
	public List<Map> queryPointsByZone(Map map);

	public List<Map> getAllAreaLines();
	
	public List<Map> getLinePointById(String s);
	
	public List<Map> getPlanPoints(String s);
	
	public int getMaxPointSeq(String s);
	
	public List<Map> getAllZones();
	
	public List<Map> getLineInfosByParentLine(String s);
	
	public int getcountByLevel(Map map);
	
	public void editModify(Map m);
	
	public List<Map> getSartEndTime(Map m);
	
	public void deleteLinePointById(Map m);
	
	public List<Map> queryPointsByPlayKind(Map m);
	
	public void addDistance(String s);
	
	public Map queryPointById(String s);
	
	public void editToNormalPoint(String s);
	
	public List<Map<String, String>> getCableByName(Query query);
	
	public List<Map<String,Object>> exportCableByName(Map map);
	
	public boolean checkCableExists(Map m);

	public List<Map> getEqpByCableId(Map param);
	
	public void addWells(Map m);
	
	public void deleteWells();
	
	public List<Map> exportLinePoint(String s);
	
	public void editLinePoint(Map m);
	
	public void delLinePoint(String s);
	
	public String checkParentLine(Map m);
	
	public List<Map> queryParentLine(Query query);
	
	public void editParentLine(Map m);
	
	/**
	 * @description 根据光缆段查人井
	 */
	public List<Map> getEqpByCblSectionId(Map m);
	
	/**
	 * @description 根据光缆查光缆段
	 */
	public List<Map<String, String>> getCableSectionByCableId(Query query);
	
	public Map getAreaByLineId(String s);
}
