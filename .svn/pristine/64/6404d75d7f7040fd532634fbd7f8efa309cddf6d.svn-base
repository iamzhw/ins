package com.linePatrol.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.linePatrol.dao.FixOrderDao;
import com.linePatrol.service.FixOrderService;

@Service
public class FixOrderServiceImpl implements FixOrderService {
	
	@Resource
	private FixOrderDao fixOrderDao;
	
	@Override
    public List<Map<String, Object>> query(Map<String, Object> para)  {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("order_status",para.get("order_status"));
	    map.put("org_id",para.get("org_id"));
	    map.put("fixordercode",para.get("fixordercode"));
	    map.put("area_id", para.get("areaId"));
	    map.put("start_time", para.get("start_time"));
	    map.put("end_time", para.get("end_time"));
	    map.put("dealpeople", para.get("dealpeople"));
		List<Map<String, Object>> olists = fixOrderDao.query(map);
		return olists;
    }
	
	@Override
	public void intoFixOrder(Map<String, Object> map) {
		fixOrderDao.intoFixOrder(map);
	}

	@Override
	public void intoSingelNodeList(Map<String, Object> map) {
		fixOrderDao.intoSingelNodeList(map);
	}

	@Override
	public String getFixOrderNextVal() {
		return fixOrderDao.getFixOrderNextVal();
	}

	@Override
	public String getSingelNodeListNextVal() {
		return fixOrderDao.getSingelNodeListNextVal();
	}

	@Override
	public List<Map<String, Object>> selAreaAdmin(String area_id) {
		return fixOrderDao.selAreaAdmin(area_id);
	}

	@Override
	public void intoFixOrderStaff(Map<String, Object> map) {
		fixOrderDao.intoFixOrderStaff(map);
	}

	@Override
	public List<Map<String, Object>> selFixOrderByPerson(Map<String, Object> map) {
		return fixOrderDao.selFixOrderByPerson(map);
	}

	@Override
	public void upFixOrderStatus(Map<String, Object> map) {
		fixOrderDao.upFixOrderStatus(map);
	}

	@Override
	public List<Map<String, Object>> selAllFixOrder() {
		return fixOrderDao.selAllFixOrder();
	}

	@Override
	public Map<String, Object> findDetail(String fixorder_id) {
		return fixOrderDao.findDetail(fixorder_id);
	}

	@Override
	public void upFixOrderStatusFile(Map<String, Object> map) {
		fixOrderDao.upFixOrderStatusFile(map);
	}

	@Override
	public List<Map<String, Object>> selNoAuditPhotos(String fixorder_id) {
		return fixOrderDao.selNoAuditPhotos(fixorder_id);
	}

	@Override
	public Map<String, Object> selFixOrderByID(String fixorder_id) {
		return fixOrderDao.selFixOrderByID(fixorder_id);
	}

	@Override
	public void upPhotoStatu(String photo_id) {
		fixOrderDao.upPhotoStatu(photo_id);
	}

	@Override
	public List<Map<String, Object>> selFixNodesByFixId(String fixorder_id) {
		return fixOrderDao.selFixNodesByFixId(fixorder_id);
	}

	@Override
	public List<Map<String, Object>> getPhotos(Map<String, Object> map) {
		return fixOrderDao.getPhotos(map);
	}
	
}
