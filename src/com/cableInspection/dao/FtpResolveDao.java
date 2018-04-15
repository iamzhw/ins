package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@SuppressWarnings("all")
@Repository
public interface FtpResolveDao {
	
	public void addFtpData(List<Map> list);
	
	public void deleteAllData();
	
	public void dereplicationData();
	
	public void addNewStaff();
	
	public void updateData();
	
	public void updateStaffId();
	
	public void deleteDeptRelationship();
	
	public void addDeptRelationship();
	
	public void deleteRoleByStaffId();
	
	public void addRoles();
	
	public int ifPointExists(Map map);
	
	public int ifPointExistsForDept(Map map);
	
	public void addNewEqp(Map map);
	
	public void addEqpToDept();
	
	public void addSoftRole();
	
	public String getPointIdByNo(Map map);
	
	public Map getDeptIdByName(Map map);
	
	public void correctSonAreaId();
	
	public void addTest(Map map);
	
	public void addNewPoint(Map map);
	
	public void deletePointForDept(Map map);
	
	public void delete_A_POINT_DEPT();
	
	public void add_A_POINT_DEPT(Map map);
	
	public void updatePointIdAndDeptId();
	
	public void resetSequence(String s);
	
	public void deleteEchoData();
	
	public void deletePointDeptByPointNo();
	
	public void import_point_dept();
}
