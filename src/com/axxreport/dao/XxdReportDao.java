/**
 * @Description: TODO
 * @date 2015-4-7
 * @param
 */
package com.axxreport.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sun.mail.iap.Literal;

import util.page.Query;

/**
 * @author Administrator
 */
@SuppressWarnings("all")
@Repository
public interface XxdReportDao {

    /**
     * 作用： 　　*作者：
     * 
     * @param query
     * @return
     */
    List<Map> getSiteXxdComplete(Query query);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    List<Map<String, Object>> getAllSiteXxdComplete(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    List<Map<String, Object>> getKeysiteComplete(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    List<Map<String, Object>> getProvinceKeysiteComplete(Map<String, Object> para);
    
    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    List<Map<String, Object>> getKeysiteArrate(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    List<Map<String, Object>> getInspectAr(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getInspectTime(Map<String, Object> map);
    
    
    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getExportInspectTime(Map<String, Object> map);
    
    /**
     * 无效时长报表
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getInvalidTime(Query query);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    List<String> getNurseList(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param plan_id
     * @return
     */
    List<Map<String, Object>> getTimeList(String plan_id);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    Map<String, Object> getLeaveInWork(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    Map<String, Object> getLateLEralyTime(Map<String, Object> map);

    /**
     * 作用： 　　*作者：
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> getInspectTimeDaily(Map<String, Object> map);

	public List<Map<String, Object>> qualityReportByPeople(Map<String, Object> map);

	List<Map<String, Object>> getInspectAllTime(Map<String, Object> map);
	
	public List<Map<String, Object>> selArea();

	List<Map<String, Object>> qualityReportByArea(Map<String, Object> map);
	
	Map<String, Object> qualityReportByAllprovince(Map<String, Object> map);
	
	List<Map<String, Object>> invalidTime(Map<String, Object> map);
	
	List<Map<String, Object>> validTime(Map<String, Object> map);
	
	public String timeByInvalid(Map<String, Object> map); 
	
	public String timeByValid(Map<String, Object> map); 
	
	public List<Map<String, Object>> selNextTime(Map<String, Object> map); 
	
	public List<Map<String, Object>> selBaseInfo(Map<String, Object> map);
	
	public  int saveinvalidTime(Map<String, Object> map);
	
	List<Map<String, Object>> getOsNurseDaily(Map<String, Object> map);

	List<Map<String, Object>> selAllTime(Map<String, Object> paramap);
	
	public List<Map<String, Object>> selPlanInfoOfStep(Map<String, Object> map);
	
	public void saveStepTask(Map<String, Object> map);
	
	public void saveStepTaskEquip(String str);
	
	public List<Map<String, Object>> selEquipPart(Map<String, Object> map);
	
	public List<Map<String, Object>> getPlanList(Map<String, Object> map);
	
	public List<Map<String, Object>> selPlanTime(Map<String, Object> map);

	public List<Map<String, Object>> getScopeList(Map<String, Object> para);

	public List<Map<String, Object>> qualityReportByScope(Map<String, Object> map);

	public List<Map<String, Object>> checkOutSiteByLeader(Map<String, Object> para);
	
	public List<Map<String, Object>> selInfoByPlanId(String para);
	
	public List<Map<String, Object>> selAllPlanDate(Map<String, Object> para);

	List<Map<String, Object>> checkOutSiteByArea(Map<String, Object> para);

	List<Map<String, Object>> getProvinceOutSiteComplete(Map<String, Object> para);
	
	List<Map<String, Object>> getProvinceCheckComplete(Map<String, Object> para);

	List<Map<String, Object>> getProvinceDangerOrder(Map<String, Object> para);
	
	Map<String, Object> getProvinceDangerOrderByAll(Map<String, Object> para);

	List<Map<String, Object>> getProvinceRoutingFacility(
			Map<String, Object> para);

	List<Map<String, Object>> getCityRoutingFacility(Map<String, Object> para);

	List<Map<String, Object>> getFrefectureRoutingFacility(
			Map<String, Object> para);

	List<Map<String, Object>> getCableRoutingFacility(Map<String, Object> para);

	List<Map<String, Object>> getFacilityDensity(Map<String, Object> para);

	void saveFacilitydensity(Map<String, Object> paramap);

	List<Map<String, Object>> getrelpyid(Map<String, Object> para);

	List<Map<String, Object>> getequiptype(HashMap<String, Object> hashMap);

	void saveEquipDensity(Map<String, Object> paramap);

	void deleteEquipDensity(HashMap<String, Object> hashMap);

	List<Map<String, Object>> getEquipDensity(Map<String, Object> para);

	List<Map<String, Object>> getDetailUI(Map<String, Object> para);

	List<Map<String, Object>> getStepTourCondition(Map<String, Object> para);
	
	List<Map<String, Object>> getProvinceStepTourCondition(Map<String, Object> para);

	List<Map<String, Object>> getStepDetailUI(Map<String, Object> para);

	Map<String, Object> getEquipDistanceId(Map<String, Object> map);

	void updateEquipDistance(Map map);

	void updateEquipDensity(Map<String, Object> map);
	
	void cancelEquipDensity(Map<String, Object> map);
	
	void cancelEquipDistance(Map<String, Object> map);
	
	List<Map<String, Object>> getVariousStepType(Map<String, Object> para);

	List<Map<String, Object>> getProvienceData(Map<String, Object> para);

	List<Map<String, Object>> getStepTypeTourCondition(Map<String, Object> para);

	void saveguardRate(Map<String, Object> listMap);
	List<Map<String, Object>> getAllGroup(String areaId);

    List<Map> query(Query query);
    

	List<Map<String, Object>> getStepEquipLocation(Map<String, Object> map);
	List<Map<String, Object>> queryDown(Map<String, Object> para);

	List<Map<String, Object>> getAreaList(HashMap<String, Object> hashMap);
	
	List<Map<String, Object>> getProvienceOfStaffData(Map<String, Object> Map);
	
	
	
}
