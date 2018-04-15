/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.outsite.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.outsite.dao.PartTimeModelDao;
import com.outsite.service.PartTimeModelService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class PartTimeModelServiceImpl implements PartTimeModelService {

    @Resource
    private PartTimeModelDao partTimeModelDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.partTimeModelService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = partTimeModelDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.partTimeModelService#save(java.util.Map)
     */
    @Override
    public void partTimeModelSave(Map<String, Object> para) {

	partTimeModelDao.partTimeModelSave(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.partTimeModelService#update(java.util.Map)
     */
    @Override
    public void partTimeModelUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	partTimeModelDao.partTimeModelUpdate(para);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.partTimeModelService#delete(java.util.Map)
     */
    @Override
    public void partTimeModelDelete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    partTimeModelDao.partTimeModelDelete(idsArray[i]);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.partTimeModelService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> findById(String id) {
	// TODO Auto-generated method stub
	return partTimeModelDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
	// TODO Auto-generated method stub
	return partTimeModelDao.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.PartTimeModelService#getCity()
     */
    @Override
    public List<Map<String, Object>> getCity() {
	// TODO Auto-generated method stub
	return partTimeModelDao.getCity();
    }

}
