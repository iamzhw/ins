package com.cableCheck.dao;

import java.util.Map;

public interface ExcelTeamDao {
	/**
	 * 根据班组名称或者班组id，判断是否存在
	 * @param teamName
	 * @return
	 */
	public Map getTeamId(Map map_param);
	
	/**
	 * 根据以及存在的班组id,判断班组下的装维人员是否存在
	 * @param teamId
	 * @param staffNo
	 * @return
	 */
	public Map getStaffId(Map map);
	
	/**
	 * 判断装维表的账户和姓名是否一致
	 * @param map
	 * @return
	 */
	public Map ifNoWithName(Map map);
	
	/**
	 * 如果班组下的人员不存在，则在装维人员表中插入一条记录
	 * @param map
	 * @return
	 */
	public Integer addStaffZw(Map map);
	
	/**
	 * 判断装维人员表中是否存在该人员
	 * @param map
	 * @return
	 */
	public Map isZwExists(Map map);
	
	/**
	 * 根据装维人员id，更新成接单岗和审核人
	 * @param staffId
	 */
	public void updateLeader(Map map);
	
	/**
	 * 根据信息，将光网助手账户设置为光网助手审核员
	 * @param map
	 */
	public void insertCableRole(Map map);
	
	/**
	 * 判断账户是否存在官网助手的权限
	 * @param map
	 * @return
	 */
	public int isExistCableRole(Map map);
	
	
	/**
	 * 根据代维公司名称，判断是否存在，返回代维公司id
	 * @param comName
	 * @return
	 */
	public Map getCompanyId(String comName);
	
	
	/**
	 * 如果代维公司不存在，则新建一条，返回该代维公司的id
	 * @param comName
	 * @return
	 */
	public Integer addCompany(String comName);
	
	
	/**
	 * 根据代维公司id和班组id判断关系表中是否存在该记录
	 * @param comId
	 * @param teamId
	 * @return
	 */
	public Integer ifExistRelation(Map map_para);
	
	
	/**
	 * 如果表中不存在记录，则新增一条关系
	 */
	public void addRelation(Map map_para);
	
	
	/**
	 * 根据staffNo判断人员账户是否在人员表中，(下一步是需要判断是否是有光网助手的权限)
	 * @param staffNo
	 * @return
	 */
	public Integer ifExitStaff(String staffNo); 
	
	
	/**
	 * 导入的时候，插入导入log表
	 * @param map
	 */
	public void insertImportLog(Map map);
	
	/**
	 * 导入之前，删除该用户之前插入log记录
	 * @param map
	 */
	public void deleteImportLog(Map map);
	
}
