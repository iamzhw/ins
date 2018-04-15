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

import com.linePatrol.dao.relayDao;
import com.linePatrol.service.relayService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class relayServiceImpl implements relayService {

    @Resource
    private relayDao relayDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.relayService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = relayDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }

    @Override
    public void relaySave(Map<String, Object> para) {

		String relayId = relayDao.getRelayId();
		para.put("relay_id", relayId);
		relayDao.relaySave(para);
	
		// 保存地区关系
		String area_id = para.get("area_id").toString();
		String[] areaIdArray = area_id.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relay_id", relayId);
		for (int i = 0; i < areaIdArray.length; i++) {
		    map.put("area_id", areaIdArray[i]);
		    relayDao.insertRelay2Area(map);
		}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.relayService#update(java.util.Map)
     */
    @Override
    public void relayUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	relayDao.relayUpdate(para);

	// 删除老关系
	String relayId = para.get("relay_id").toString();
	relayDao.deleteOldRelay2Area(relayId);

	// 保存地区关系
	String area_id = para.get("area_id").toString();
	String[] areaIdArray = area_id.split(",");
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("relay_id", relayId);
	for (int i = 0; i < areaIdArray.length; i++) {
	    map.put("area_id", areaIdArray[i]);
	    relayDao.insertRelay2Area(map);
	}

    }

    @Override
    public void relayDelete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    relayDao.relayDelete(idsArray[i]);

	    // 删除地区关系 数据库级联删除
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.relayService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> relayEditUI(String id) {
	// TODO Auto-generated method stub
	return relayDao.findById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.relayService#findById(java.lang.String)
     */
    @Override
    public Map<String, Object> findById(String id) {
	// TODO Auto-generated method stub
	return relayDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findRelayByAreaId(String area_id) {
	// TODO Auto-generated method stub
	return relayDao.findRelayByAreaId(area_id);
    }

    @Override
    public List<Map<String, Object>> getOwnArea(String cable_id) {

	return relayDao.getOwnArea(cable_id);
    }

    @Override
    public String getRelayArea(String id) {
	// TODO Auto-generated method stub
	return relayDao.getRelayArea(id);
    }

}
