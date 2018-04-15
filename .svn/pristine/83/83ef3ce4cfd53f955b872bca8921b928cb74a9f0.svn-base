/**
 * FileName: GeneralServiceImpl.java
 * @Description: TODO(用一句话描述该文件做什么) 
 * All rights Reserved, Designed By ZBITI 
 * Copyright: Copyright(C) 2014-2015
 * Company ZBITI LTD.

 * @author: SongYuanchen
 * @version: V1.0  
 * Createdate: 2014-1-17
 *

 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-1-17      wu.zh         1.0            1.0
 * Why & What is modified: <修改原因描述>
 */
package com.system.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.system.dao.GeneralDao;
import com.system.dao.SoftwareVersionDao;
import com.system.service.GeneralService;

/**
 * @ClassName: GeneralServiceImpl
 * @Description:
 * @author: SongYuanchen
 * @date: 2014-1-17
 * 
 */
@SuppressWarnings("all")
@Service
public class GeneralServiceImpl implements GeneralService {
    @Resource
    private GeneralDao generalDao;
    @Resource
    private SoftwareVersionDao softwareVersionDao;

    /**
     * <p>
     * Title: getAreaList
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @return
     * @see com.InitService.system.service.interfaces.GeneralService#getAreaList()
     */
    @Override
    public List<Map<String, String>> getAreaList() {

	return generalDao.getAreaList();
    }

    /**
     * <p>
     * Title: getAreaList
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @return
     * @see com.InitService.system.service.interfaces.GeneralService#getAreaList()
     */
    @Override
    public List<Map<String, String>> getSoftwareList() {

	return generalDao.getSoftwareList();
    }

    /**
     * <p>
     * Title: getAreaList
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @return
     * @see com.InitService.system.service.interfaces.GeneralService#getAreaList()
     */
    @Override
    public Map<String, Object> getSoftwarePageList(HttpServletRequest request, UIPage pager) {
	Map map = new HashMap();
	String versionNo = request.getParameter("version_no");
	String softwareId = request.getParameter("software_id");
	map.put("versionNo", versionNo);
	map.put("softwareId", softwareId);
	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(map);
	List<Map> softwarePagelist = generalDao.getSoftwarePageList(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", softwarePagelist);
	return pmap;
    }

    /**
     * <p>
     * Title: getSonAreaList
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param parameter
     * @return
     * @see com.InitService.system.service.interfaces.GeneralService#getSonAreaList(java.lang.String)
     */
    @Override
    public List<Map<String, String>> getSonAreaList(String area_id) {

    	return generalDao.getSonAreaList(area_id);
    }
    /**
     * 获取综合化维护网格
     */
    @Override
    public List<Map<String, String>> getGridList(String areaId, String sonAreaId) {
    	
    	Map param = new HashMap();
    	param.put("areaId", areaId);
    	param.put("sonAreaId", sonAreaId);
    	return generalDao.getGridList(param);
    }

    @Transactional
    private void switchDB() {
    try{
	SwitchDataSourceUtil.setCurrentDataSource("ressz");
	String eqpNo = generalDao.getEqpNo();
	System.out.println(eqpNo);
	SwitchDataSourceUtil.clearDataSource();
    }catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		SwitchDataSourceUtil.clearDataSource();
	}
    }

    @Override
    public Map<String, Object> testDB() {
	switchDB();
	return null;
    }

    @Override
    public List<Map> getCompanyList(Map map) {
	return generalDao.getCompanyList(map);
    }

    @Override
    public String getNextSeqVal(String seq) {
	Map paramMap = new HashMap();
	paramMap.put("SEQ", seq);
	int sequence = generalDao.getSeq(paramMap);
	return String.valueOf(sequence);
    }

    @Override
    public List<Map<String, Object>> getAreaById(String areaId) {
	return generalDao.getAreaById(areaId);
    }

    @Override
    public List<Map<String, String>> getOwnCompany(String parameter) {
	// TODO Auto-generated method stub
	return generalDao.getOwnCompany(parameter);
    }

    @Override
    public List<Map<String, Object>> getPhoto(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return generalDao.getPhoto(para);
    }
    @Override
    public List<Map<String, Object>> getPhotoByType(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return generalDao.getPhotoByType(para);
    }
    @Override
    public List<Map<String, Object>> getStaffByRole(Map<String, Object> map) {
	// TODO Auto-generated method stub
	return generalDao.getStaffByRole(map);
    }

	@Override
	public List<Map<String, String>> getDeptList(String parameter) {
		return generalDao.getDeptList(parameter);
	}
	
	@Override
	public List<Map<String, String>> getMainTainCompany(){
		return generalDao.getMainTainCompany();
	}
	
	@Override
	public List<Map<String, String>> getBanzuByCompanyId(String parameter){
		return generalDao.getBanzuByCompanyId(parameter);
	}
	
	@Override
	public List<Map<String, String>> getBanzuByAreaId(String parameter){
		return generalDao.getBanzuByAreaId(parameter);
	}
	
}
