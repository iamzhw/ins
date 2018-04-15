package com.inspecthelper.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.Query;

/**
 * 我的工单接口层
 *
 * @author lbhu
 * @since 2014-10-13
 *
 */
@SuppressWarnings("all")
@Repository
public interface InsOrderTaskDao {

	public List<Map> getOrderTaskList(Query query);
	
	public List<Map> getEquTarget(Map map);
	
	public Map getPLZPCheckStaff(Map map);
	
	public void pLZPai(Map map);
	
	public List<Map> getActionHistoryOrderList(Map map);
	
	public void modifyOrderTuidanInfo(Map map);
	
	public void modifyOrderEndInfo(Map map);
	
	public void insertActionHistory(Map map);
	
	public void modifyOrderCheckInfoOk(Map map);
	
	public void modifyOrderCheckInfoNo(Map map);
	
	public Map findTroublecode(Map map);
	
	public void saveResourceFile(Map map);
	
	public void saveResourcePhoto(Map map);
	
	public int getFileId();
	
	public List<Map> getNewStr(Map map);
	
	public int getZGCount(Map map);
	
	public void modifyOrderPostInfo(Map map);
	
	public void modifyOrderPostInfo_(Map map);
	
	public void modifyOrderHuiInfo(Map map);
	
	public Map getStaffByFiberCode(Map map);
	
	public List<Map> getLastTrouPhotoPath(Map map);
	
	public List<Map> getAllStaffByTroubleOrderList(Map map);
	
	public List<Map> findAllPhoto(Map map);
	
	public List<Map> findAllFile(Map map);

	public List<Map> getZGTroubleInfo(Map m);
}
