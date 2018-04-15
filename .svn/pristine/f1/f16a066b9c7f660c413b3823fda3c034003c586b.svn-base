package com.cableCheck.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface TeamManagerService {
	
	public Map<String, Object> listTeam(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> listUserRole(HttpServletRequest request, UIPage pager);
	
	public Map<String, Object> updateTeam(HttpServletRequest request);
	
	public void calProcedures(HttpServletRequest request);
	
	public Map<String, Object> updateTeamCompany(HttpServletRequest request);
	
	public Map<String, Object> updateShenhe(HttpServletRequest request);
	
	public Map<String, Object> updateDoudi(HttpServletRequest request);
	
	public Map<String, Object> resetTeamStaff(HttpServletRequest request);
	public Map<String, Object> updateTeamStaff(HttpServletRequest request);
	
	Map<String, Object> queryStaff(HttpServletRequest request, UIPage pager);
	
	Map<String, Object> queryImportLog(HttpServletRequest request, UIPage pager);
	
	public void exportExcel(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response, UIPage pager);
	
    public Map<String, Object> queryRolePermissions(HttpServletRequest request, UIPage pager);
	
}
