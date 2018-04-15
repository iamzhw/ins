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

import com.outsite.dao.OsMaintainSchemeDao;
import com.outsite.service.OsMaintainSchemeService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class OsMaintainSchemeServiceImpl implements OsMaintainSchemeService {

    @Resource
    private OsMaintainSchemeDao osMaintainSchemeDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.osMaintainSchemeService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = osMaintainSchemeDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.osMaintainSchemeService#save(java.util.Map)
     */
    @Override
    public void osMaintainSchemeSave(Map<String, Object> para) {
	// TODO Auto-generated method stub
	osMaintainSchemeDao.osMaintainSchemeSave(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.osMaintainSchemeService#update(java.util.Map)
     */
    @Override
    public void osMaintainSchemeUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	osMaintainSchemeDao.osMaintainSchemeUpdate(para);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.osMaintainSchemeService#delete(java.util.Map)
     */
    @Override
    public void osMaintainSchemeDelete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    osMaintainSchemeDao.osMaintainSchemeDelete(idsArray[i]);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.linePatrol.service.osMaintainSchemeService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> findById(String id) {
	// TODO Auto-generated method stub
	return osMaintainSchemeDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
	// TODO Auto-generated method stub
	return osMaintainSchemeDao.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.outsite.service.OsMaintainSchemeService#findDetail(java.lang.String)
     */
    @Override
    public Map<String, Object> findDetail(String scheme_id) {
	// TODO Auto-generated method stub

	Map<String, Object> map = osMaintainSchemeDao.findById(scheme_id);

	return map;

    }

}
