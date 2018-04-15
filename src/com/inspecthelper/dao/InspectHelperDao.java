package com.inspecthelper.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
@SuppressWarnings("all")
public interface InspectHelperDao {
	
	int getTskOrderListCount(Map<String,Object> map);
	
	int getOrderCount(Map<String,Object> map);

	List<Map<String,Object>> query(Query query);
	
	int getTaskEquCount(Map<String,Object> map);

	List<Map<String,Object>> getTaskEquList(Map<String,Object> map);
	
	void createTaskOrderSign(Map<String,Object> map);
	
	void createTaskOrderCheck1(Map<String,Object> map);
	
	void createTaskOrderCheck(Map<String,Object> map);
	
	List<Map<String,Object>> getTaskOrderSign(Map<String,Object> map);
	
	List<Map<String,Object>> getEquTarget(Map<String,Object> map);
	
	String getStr(Map<String,Object> map);
	
	List<Map<String,Object>> getLastTrouPhotoPath(Map<String,Object> map);

	List<Map<String,Object>> getTargetQues(String troubleCode);
	
	void deleResTrou(String troubleCode);
	
	String getEquArea(Map<String,Object> map);
	
	String getEquAreaId(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaffZXC(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaffCS(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaffTC(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaff2(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaff3(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaff(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaff1(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaff12(Map<String,Object> map);
	
	List<Map<String,Object>> getCheckStaff11(Map<String,Object> map);
	
	String getNextSeqVal(Map<String,Object> map);
	
	void saveResTrouble(Map<String,Object> map);
	
	String getOpticalPathNameByCode(Map<String,Object> map);
	
	void updateResTrou(Map<String,Object> map);

	int checkResCount(Map<String, Object> param1);

	//void createTaskCheck(Map<String, Object> param1);

	void checkRes(Map<String, Object> param1);
	
	void updateTaskOrder(Map<String, Object> param1);
	
	void updateTaskOrder1(Map<String, Object> param1);
	
	void updateTaskOrder2(Map<String, Object> param1);
	
	List<Map<String,Object>> getResTrouble(Map<String,Object> map);

	String getLastCheckDate(Map<String, Object> param);

	List<Map<String, Object>> getGJCDuanInfo(Map<String, Object> param);

	String getODFStaffRole(String staffNo);

	void updateDinamicChange(Map<String, Object> param);

	List<Map<String, Object>> getODFDinamicRes(Map<String, Object> param);

	String getRealTimeOpticalCode(Map<String, Object> param);

	List getContentByUno(Map param);

	List getJumpPortNo(Map param);

	List getOBDContentByUno(Map param);

	void insertResLog(HashMap param);

	void insertRegisterLog(HashMap param);

	List getOBDByGJNo(Map param);

	List<HashMap> getTroubleOrderList(HashMap<String, String> map);

	List<Map> getTskOrderList(Map param);

	List<Map> getUnoByResNo(Map param);

	String getStaffNo(String staffId);

	List getCheckedTimes(Map checkParam);

	List getCheckedTimesNotForSZ(Map checkParam);

	Map getEqu(String equipmentId);

	void signRes(Map param);

	List getRes(Map param);
	
	
}
