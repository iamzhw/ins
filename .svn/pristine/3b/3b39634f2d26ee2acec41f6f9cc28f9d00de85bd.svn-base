/**
 * @Description: TODO
 * @date 2015-4-7
 * @param
 */
package com.axxreport.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.Query;
import util.page.UIPage;

/**
 * @author Administrator
 * 
 */
public interface XxdReportService {

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param uiPage
     * @return
     */
    Map<String, Object> getSiteXxdComplete(Map<String, Object> para, UIPage pager);

    /**
     * 作用： 　　*作者：
     * 
     * @param query
     * @return
     */
    List<Map> query(Query query);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param response
     * @param request
     */
    void xxdDownload(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    Map<String, Object> getKeysiteComplete(Map<String, Object> para);
    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    Map<String, Object> getProvinceKeysiteComplete(Map<String, Object> para);
    
    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param request
     * @param response
     */
    void keysiteDownload(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    Map<String, Object> getKeysiteArrate(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param request
     * @param response
     */
    void keysiteArDownload(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    Map<String, Object> getInspectAr(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param request
     * @param response
     */
    void inspectArDownload(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    Map<String, Object> getInspectTime(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param request
     * @param response
     */
    void inspectTimeDownload(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     * @throws Exception 
     */
    List<Map<String, Object>> getOsNurseDaily(Map<String, Object> para) throws Exception;

    /**
     * 无效时长报表
     * 
     * @param para
     * @param uiPage
     * @return
     */
    Map<String, Object> getInvalidTime(Map<String, Object> para, UIPage pager);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param request
     * @param response
     * @throws Exception 
     */
    void osNurseDailyDownload(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response) throws Exception;

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    Map<String, Object> getInspectTimeDaily(Map<String, Object> para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @param request
     * @param response
     */
    void inspectTimeDailyDownload(Map<String, Object> para, HttpServletRequest request,
	    HttpServletResponse response);

	public List<Map<String, Object>> qualityReportByPeople(Map<String, Object> para);

	public Map<String, Object> getInspectAllTime(Map<String, Object> para);

	void inspectAllTimeDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	
	public List<Map<String, Object>> selArea();

	List<Map<String, Object>> qualityReportByArea(Map<String, Object> para);

	void qualityReportByPeopleDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	void qualityReportByAreaDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	
	public List<Map<String, Object>> osNurseDailySet(List<String> planIds,String queryTime) throws Exception ;
	
	public void allotTaskOfStepTour() throws Exception;
	
	public List<Map<String, Object>> detailInfo(Map<String, Object> para);

	public List<Map<String, Object>> getScopeList(Map<String, Object> para);

	public List<Map<String, Object>> qualityReportByscope(Map<String, Object> para);

	public void qualityReportByScopeDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	public List<Map<String, Object>> checkOutSiteByLeader(Map<String, Object> para);

	public void checkOutSiteByLeaderDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	void getProvinceKeysiteCompleteDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	List<Map<String, Object>> checkOutSiteByArea(Map<String, Object> para);

	void checkOutSiteByAreaDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getProvinceOutSiteComplete(Map<String, Object> para);

	void getProvinceOutSiteCompleteDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	
	List<Map<String, Object>> getProvinceCheckComplete(Map<String, Object> para);

	void getProvinceCheckCompleteDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	List<Map<String, Object>> getProvinceDangerOrder(Map<String, Object> para);

	void getProvinceDangerOrderDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	void getProvinceRoutingFacilityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	List<Map<String, Object>> getProvinceRoutingFacility(
			Map<String, Object> para);

	void getCityRoutingFacilityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	void frefectureRoutingFacilityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	List<Map<String, Object>> getFrefectureRoutingFacility(
			Map<String, Object> para);

	List<Map<String, Object>> getCityRoutingFacility(Map<String, Object> para);

	List<Map<String, Object>> getCableRoutingFacility(Map<String, Object> para);

	void getCableRoutingFacilityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	List<Map<String, Object>> getFacilityDensity(Map<String, Object> para) ;

	List<Map<String, Object>> getDetailUI(Map<String, Object> para);

	void getFacilityDensityDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	List<Map<String, Object>> getStepTourCondition(Map<String, Object> para);
	
	List<Map<String, Object>> getProvinceStepTourCondition(Map<String, Object> para);

	List<Map<String, Object>> getStepDetailUI(Map<String, Object> para);

	void getStepTourConditionDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	void getProvinceStepTourConditionDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	
	void equipDiatanceDelete(HttpServletRequest request);
	
	void equipDiatanceCancel(HttpServletRequest request);
	
	List<Map<String, Object>> getVariousStepType(Map<String, Object> para);

	void getVariousStepTypeDown (Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	List<Map<String, Object>> getStepTypeTourCondition(Map<String, Object> para);

	void getStepTypeTourConditionDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	 List<Map<String, Object>> getAllGroup(String area_id);



	Map<String, Object> query(Map<String, Object> para, UIPage pager);

	List<Map<String, Object>> getStepEquipLocation(Map<String, Object> map);

	void stepequipdDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
	
	List<Map<String, Object>> getProvienceOfStaffData(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);

	void getProvienceOfStaffDataDown(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response);
}


