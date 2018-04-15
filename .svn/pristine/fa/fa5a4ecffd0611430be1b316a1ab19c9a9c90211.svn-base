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

import com.linePatrol.dao.gldManageDao;
import com.linePatrol.service.gldManageService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class gldManageServiceImpl implements gldManageService {

    @Resource
    private gldManageDao gldManageDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.gldManageService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = gldManageDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }

    @Override
    public void save(Map<String, Object> para) {

		String cable_id = gldManageDao.getCableId();
		para.put("cable_id", cable_id);
		gldManageDao.save(para);
	
		// 保存地区关系
		String area_id = para.get("area_id").toString();
		String[] areaIdArray = area_id.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cable_id", cable_id);
		for (int i = 0; i < areaIdArray.length; i++) {
		    map.put("area_id", areaIdArray[i]);
		    gldManageDao.insertCable2Area(map);
		}
    }

    @Override
    public void update(Map<String, Object> para) {

	gldManageDao.update(para);

	String cable_id = para.get("cable_id").toString();
	// 删除原来关系
	gldManageDao.deleteOldCable2Area(cable_id);

	// 保存地区关系
	String area_id = para.get("area_id").toString();
	String[] areaIdArray = area_id.split(",");
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("cable_id", cable_id);
	for (int i = 0; i < areaIdArray.length; i++) {
	    map.put("area_id", areaIdArray[i]);
	    gldManageDao.insertCable2Area(map);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.gldManageService#delete(java.util.Map)
     */
    @Override
    public void delete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    gldManageDao.delete(idsArray[i]);

	    // 删除光缆 地区对应关系---数据库级联删除

	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.linePatrol.service.gldManageService#validateCblColor(java.lang.String
     * )
     */
    @Override
    public int validateCblColor(String cable_color) {
		int biggerNum = Integer.parseInt(cable_color, 16) + 20;
		int smallerNum = Integer.parseInt(cable_color, 16) - 20;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("biggerNum", biggerNum);
		map.put("smallerNum", smallerNum);
		return gldManageDao.validateCblColor(map);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.gldManageService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> editUI(String id) {
	// TODO Auto-generated method stub
	String ownAreaList = gldManageDao.getOwnAreaList(id);
	Map<String, Object> map = gldManageDao.findById(id);
	map.put("ownAreaList", ownAreaList);
	return map;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.gldManageService#findAll()
     */
    @Override
    public List<Map<String, Object>> findAll() {
	// TODO Auto-generated method stub
	return gldManageDao.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.linePatrol.service.gldManageService#getGldByAreaId(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> getGldByAreaId(String staffAreaId) {
	// TODO Auto-generated method stub
	return gldManageDao.getGldByAreaId(staffAreaId);
    }
   

    @Override
    public List<Map<String, Object>> getRelayByCableId(String cable_id) {
	// TODO Auto-generated method stub
	return gldManageDao.getRelayByCableId(cable_id);
    }
    

    @Override
    public List<Map<String, Object>> getCableAreaList(String cable_id) {
	// TODO Auto-generated method stub
	return gldManageDao.getCableAreaList(cable_id);
    }

}
