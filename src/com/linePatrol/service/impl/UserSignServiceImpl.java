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

import com.linePatrol.dao.UserSignDao;
import com.linePatrol.service.UserSignService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class UserSignServiceImpl implements UserSignService {

    @Resource
    private UserSignDao userSignDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.userSignService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = userSignDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.userSignService#save(java.util.Map)
     */
    @Override
    public void userSignSave(Map<String, Object> para) {
	// TODO Auto-generated method stub
	userSignDao.userSignSave(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.userSignService#update(java.util.Map)
     */
    @Override
    public void userSignUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	userSignDao.userSignUpdate(para);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.userSignService#delete(java.util.Map)
     */
    @Override
    public void userSignDelete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    userSignDao.userSignDelete(idsArray[i]);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.userSignService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> findById(String id) {
	// TODO Auto-generated method stub
	return userSignDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
	// TODO Auto-generated method stub
	return userSignDao.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.linePatrol.service.UserSignService#getUserSignByUserid(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> getUserSignByUserid(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return userSignDao.getUserSignByUserid(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.linePatrol.service.UserSignService#getTheLineInfoForSign(java.util
     * .Map)
     */
    @Override
    public Map getTheLineInfoForSign(Map<String, Object> para) {

	return null;
    }

}
