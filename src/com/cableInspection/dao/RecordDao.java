package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface RecordDao {
	List<Map> query(Query query);

	List<Map> queryPhoto(Map map);

	List<Map> getListByPage(Query query);

	List<Map> getAreaList();

	List<Map> getSonAreaListByAreaId(Map areaSearchMap);

	List<Map> queryRecordStaff(Query query);

	List<Map> getRecordForExport(Map map);

	List<Map> getRecordStaffForExport(Map map);
}
