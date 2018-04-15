package com.cableInspection.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

public interface DeptService {
	/**
	 * @Title: query
	 * @Description:
	 * @param: @param request
	 * @param: @return
	 * @return: Page
	 * @throws
	 */

	public Map<String, Object> query(HttpServletRequest request, UIPage pager);
	
	/**
	 * @throws Exception
	 * @Title: save
	 * @Description:
	 * @param: @param request
	 * @param: @return
	 * @return: Boolean
	 * @throws
	 */
	public void insert(HttpServletRequest request) throws Exception;

	/**
	 * @Title: edit
	 * @Description:
	 * @param: @param staffId
	 * @param: @return
	 * @return: Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> edit(HttpServletRequest request);

	/**
	 * @throws Exception
	 * @Title: update
	 * @Description:
	 * @param: @param request
	 * @return: void
	 * @throws
	 */
	public void update(HttpServletRequest request) throws Exception;

	/**
	 * @Title: delete
	 * @Description:
	 * @param: @param request
	 * @return: void
	 * @throws
	 */
	public void delete(HttpServletRequest request);

	public Map<String, Object> index(HttpServletRequest request);

	public Map<String, Object> getStaff(HttpServletRequest request,
			UIPage pager);

	public void distributeStaff(HttpServletRequest request);
	
	public String getDeptSelection(HttpServletRequest request);
	
	public String getDeptSelectionForCable(HttpServletRequest request);

	String getDeptByAreaId(HttpServletRequest request);
}
