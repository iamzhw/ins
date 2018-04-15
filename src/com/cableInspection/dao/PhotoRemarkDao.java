package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface PhotoRemarkDao {
	public void insertRemark(Map map);
	public List<Map> queryRemark(String s);
	public List<Map> queryPhotoRemark(Query query);
}
