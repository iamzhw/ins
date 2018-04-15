package com.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import util.page.UIPage;

@Service
@SuppressWarnings("all")
public interface UnifiedPageService {

	public Map<String, Object> query(HttpServletRequest request, UIPage pager);
	
	public List getRoles(String staffId);
	
	public List getSofts(String staffId);
	
	public Map getOutSitePermissions(String staffId);
	
	public void insert(HttpServletRequest request) throws Exception;
	
	public Map<String, Object> edit(HttpServletRequest request);
	
	public void delete(HttpServletRequest request);
	
	public Map<String, Object> queryRolePermissions(HttpServletRequest request, UIPage pager);
	
	public void update(HttpServletRequest request) throws Exception;
	
	public Map<String, Object> gridManage(HttpServletRequest request);
	
	
}
