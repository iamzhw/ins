package com.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.system.model.ZTreeNode;

import util.page.UIPage;

public interface MainTainCompanyService {
	
	
	public Map<String, Object> listCompany(HttpServletRequest request, UIPage pager);
	public Map<String, Object> selectCompany(HttpServletRequest request);
	
	public Map<String, Object> editCompany(HttpServletRequest request);
	Map<String, Object> deleteCompany(HttpServletRequest request);
	
	public Map<String, Object> saveRelation(HttpServletRequest request);
	
	
	public Map<String, Object> listGridOrder(HttpServletRequest request, UIPage pager);
	
	
	public List<ZTreeNode> getDeptTree(Map map);
	
	
	public void exportExcel(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response, UIPage pager);
	
	public Map<String, Object> updateTreeStatus(HttpServletRequest request);
	
}
