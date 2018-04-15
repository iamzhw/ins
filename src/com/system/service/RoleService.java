package com.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

import com.system.model.ZTreeNode;

@SuppressWarnings("all")
public interface RoleService {

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
	 * @Title: proveUniqueness
	 * @Description:
	 * @param: @param request
	 * @param: @return
	 * @return: Boolean
	 * @throws
	 */
	Boolean proveUniqueness(HttpServletRequest request);

	/**
	 * @Title: insert
	 * @Description:
	 * @param: @param request
	 * @return: void
	 * @throws
	 */
	void insert(HttpServletRequest request);

	/**
	 * @Title: edit
	 * @Description:
	 * @param: @param request
	 * @param: @return
	 * @return: Map<String,Object>
	 * @throws
	 */
	Map<String, Object> edit(HttpServletRequest request);

	/**
	 * @Title: update
	 * @Description:
	 * @param: @param request
	 * @return: void
	 * @throws
	 */
	void update(HttpServletRequest request);

	/**
	 * @Title: delete
	 * @Description:
	 * @param: @param request
	 * @return: void
	 * @throws
	 */
	void delete(HttpServletRequest request);

	/**
	 * @Title: getAllGns
	 * @Description:
	 * @param: @param treeId
	 * @param: @param roleId
	 * @param: @return
	 * @return: List<ZTreeNode>
	 * @throws
	 */
	List<ZTreeNode> getAllGns(int treeId, String roleId);

	/**
	 * @Title: assignPermissions
	 * @Description:
	 * @param: @param roleId
	 * @param: @param resources
	 * @return: void
	 * @throws
	 */
	void assignPermissions(String roleId, String resources);

	public List<String> getRoleList(Map<String, Object> params);

}
