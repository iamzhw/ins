/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.util.DateUtil;
import com.system.dao.MmsModelDao;
import com.system.service.MmsModelService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class MmsModelServiceImpl implements MmsModelService {

    @Resource
    private MmsModelDao mmsModelDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mmsModelService#query(java.util.Map,
     * util.page.UIPage)
     */
    @Override
    public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
	// TODO Auto-generated method stub

	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(para);

	List<Map> olists = mmsModelDao.query(query);
	Map<String, Object> pmap = new HashMap<String, Object>(0);
	pmap.put("total", query.getPager().getRowcount());
	pmap.put("rows", olists);
	return pmap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mmsModelService#save(java.util.Map)
     */
    @Override
    public void mmsModelSave(Map<String, Object> para) {
	// TODO Auto-generated method stub
	mmsModelDao.mmsModelSave(para);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mmsModelService#update(java.util.Map)
     */
    @Override
    public void mmsModelUpdate(Map<String, Object> para) {
	// TODO Auto-generated method stub
	mmsModelDao.mmsModelUpdate(para);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mmsModelService#delete(java.util.Map)
     */
    @Override
    public void mmsModelDelete(String ids) {
	// TODO Auto-generated method stub

	String[] idsArray = ids.split(",");
	for (int i = 0; i < idsArray.length; i++) {
	    mmsModelDao.mmsModelDelete(idsArray[i]);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.mmsModelService#editUI(java.lang.String)
     */
    @Override
    public Map<String, Object> findById(String id) {
	// TODO Auto-generated method stub
	return mmsModelDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
	// TODO Auto-generated method stub
	return mmsModelDao.findAll();
    }

    @Override
    public List<Map<String, Object>> getModalTypeList() {
	// TODO Auto-generated method stub
	return mmsModelDao.getModalTypeList();
    }

    @Override
    public List<Map<String, Object>> getLocalKhy(String areaId) {
	// TODO Auto-generated method stub
	return mmsModelDao.getLocalKhy(areaId);
    }

    @Override
    public void saveShortMessageAlarm(Map<String, Object> para) {
	String khyId = para.get("khyId").toString();
	khyId = khyId.substring(0, khyId.length() - 1);
	// 删除原来关系
	Map<String, Object> map2 = new HashMap<String, Object>();
	map2.put("khyId", khyId);
	mmsModelDao.deleteOldMessageAlarm(map2);

	// 重建关系
	String target = para.get("target").toString();
	String modelId = para.get("modelId").toString();
	target = target.substring(0, target.length() - 1);
	modelId = modelId.substring(0, modelId.length() - 1);

	String[] khyIdArr = khyId.split(",");
	String[] targetArr = target.split(",");
	String[] modelIdArr = modelId.split(",");

	Map<String, Object> map = new HashMap<String, Object>();

	for (int i = 0; i < khyIdArr.length; i++) {
	    map.put("user_id", khyIdArr[i]);
	    for (int j = 0; j < targetArr.length; j++) {
		map.put("mms_id", modelIdArr[j]);// 模板
		map.put("send_type", targetArr[j]);// 模板
		map.put("set_date", DateUtil.getDateAndTime());// 模板
		mmsModelDao.saveShortMessageAlarm(map);
	    }
	}
    }

    @Override
    public List<Map<String, Object>> getSettings(String staffId) {
	// TODO Auto-generated method stub
	return mmsModelDao.getSettings(staffId);
    }

}
