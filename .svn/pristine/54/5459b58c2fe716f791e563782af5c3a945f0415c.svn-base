package com.system.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.system.dao.RoleDao;
import com.system.model.ZTreeNode;
import com.system.service.RoleService;

@SuppressWarnings("all")
@Transactional
@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao roleDao;

	/**
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @see com.zbiti.system.service.interfaces.RoleService#query(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String role_name = request.getParameter("role_name");
		String role_no = request.getParameter("role_no");
		map.put("ROLE_NAME", role_name);
		map.put("ROLE_NO", role_no);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = roleDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	/**
	 * <p>
	 * Title: proveUniqueness
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @see com.zbiti.system.service.interfaces.RoleService#proveUniqueness(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Boolean proveUniqueness(HttpServletRequest request) {
		String role_no = (String) request.getParameter("role_no");
		String notrole_no = (String) request.getParameter("notrole_no");
		Map map = new HashMap();
		map.put("ROLE_NO", role_no);
		map.put("NOTROLE_NO", notrole_no);

		Integer count = roleDao.proveUniqueness(map);
		if (count == 0)
			return true;
		else
			return false;
	}

	/**
	 * <p>
	 * Title: insert
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @see com.zbiti.system.service.interfaces.RoleService#insert(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void insert(HttpServletRequest request) {
		String role_name = request.getParameter("role_name");
		String role_no = request.getParameter("role_no");
		String status = request.getParameter("status");
		Map map = new HashMap();
		map.put("ROLE_NAME", role_name);
		map.put("ROLE_NO", role_no);
		map.put("STATUS", status);
		HttpSession session = request.getSession();
		map.put("CREATE_STAFF", session.getAttribute("staffId"));
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));

		roleDao.insert(map);
	}

	/**
	 * <p>
	 * Title: edit
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @see com.zbiti.system.service.interfaces.RoleService#edit(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Map<String, Object> edit(HttpServletRequest request) {

		Map<String, Object> rs = new HashMap<String, Object>();
		/**
		 * 1、根据roleId查询角色
		 */
		String role_id = String.valueOf(request.getParameter("role_id"));
		Map role = roleDao.getRole(role_id);
		rs.put("role", role);
		return rs;
	}

	/**
	 * <p>
	 * Title: update
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @see com.zbiti.system.service.interfaces.RoleService#update(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void update(HttpServletRequest request) {
		String role_id = request.getParameter("role_id");
		String role_name = request.getParameter("role_name");
		String role_no = request.getParameter("role_no");

		String status = request.getParameter("status");
		Map map = new HashMap();
		map.put("ROLE_ID", role_id);
		map.put("ROLE_NAME", role_name);
		map.put("ROLE_NO", role_no);

		map.put("STATUS", status);
		HttpSession session = request.getSession();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		roleDao.update(map);
	}

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @see com.zbiti.system.service.interfaces.RoleService#delete(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void delete(HttpServletRequest request) {

		String selected = request.getParameter("selected");
		HttpSession session = request.getSession();
		Map map = new HashMap();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		String[] roles = selected.split(",");
		for (int i = 0; i < roles.length; i++) {
			map.put("ROLE_ID", roles[i]);
			roleDao.deleteRole(map);
		}

	}

	/**
	 * <p>
	 * Title: getAllGns
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param treeId
	 * @param roleId
	 * @return
	 * @see com.zbiti.system.service.interfaces.RoleService#getAllGns(int,
	 *      java.lang.String)
	 */
	@Override
	public List<ZTreeNode> getAllGns(int treeId, String roleId) {

		Map hm = new HashMap();
		hm.put("ID", treeId);
		hm.put("ROLE_ID", roleId);
		List<HashMap> l = roleDao.getAllGns(hm);
		List<HashMap> resources = roleDao.getResources(hm);
		List<ZTreeNode> nodes = new ArrayList();
		// 循环数据，拼接返回数据
		for (int i = 0; i < l.size(); i++) {
			HashMap gns = l.get(i);
			Boolean checked = false;
			for (int j = 0; j < resources.size(); j++) {
				if (resources.get(j).get("ID").toString().equals(
						gns.get("ID").toString())) {
					checked = true;
					break;
				}
			}
			// true需要判断
			nodes.add(new ZTreeNode(gns.get("ID").toString(), (String) gns
					.get("NAME"), gns.get("PARENTID").toString(), false,
					((String) gns.get("ISPARENT")).equals("1") ? true : false,
					(String) gns.get("ACTIONNAME"), checked));
		}
		return nodes;
	}

	/**
	 * <p>
	 * Title: assignPermissions
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param roleId
	 * @param resources
	 * @see com.zbiti.system.service.interfaces.RoleService#assignPermissions(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public void assignPermissions(String roleId, String resources) {
		// 1、删除角色对应的所有资源；
		roleDao.delResource(roleId);
		// 2、给角色重新赋予资源；		
		if (!"".equals(resources) && resources != null) {
			String[] res = resources.split("=");
			for (int i = 0; i < res.length; i++) {
				String resource = res[i];
				Map map = new HashMap();
				map.put("roleId", roleId);
				map.put("resource", resource);
				roleDao.giveResource(map);
			}
		}
	}

	@Override
	public List<String> getRoleList(Map<String, Object> params) {
		return roleDao.getRoleList(params);
	}
}
