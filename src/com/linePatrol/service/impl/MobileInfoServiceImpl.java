/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.dao.MobileInfoDao;
import com.linePatrol.service.MobileInfoService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class MobileInfoServiceImpl implements MobileInfoService {

    @Resource
    private MobileInfoDao mobileInfoDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mobileInfoService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = mobileInfoDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mobileInfoService#save(java.util.Map)
     */
    @Override
    public void mobileInfoSave(Map<String, Object> para) {
	// TODO Auto-generated method stub
	mobileInfoDao.mobileInfoSave(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mobileInfoService#update(java.util.Map)
     */
    @Override
    public void mobileInfoUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	mobileInfoDao.mobileInfoUpdate(para);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mobileInfoService#delete(java.util.Map)
     */
    @Override
    public void mobileInfoDelete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    mobileInfoDao.mobileInfoDelete(idsArray[i]);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mobileInfoService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> findById(String id) {
	// TODO Auto-generated method stub
	return mobileInfoDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
	// TODO Auto-generated method stub
	return mobileInfoDao.findAll();
    }

}
