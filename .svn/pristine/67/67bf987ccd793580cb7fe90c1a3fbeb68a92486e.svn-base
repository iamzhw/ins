package com.system.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.dao.ResourceDao;
import com.system.dao.RoleDao;
import com.system.model.ZTreeNode;
import com.system.service.ResourceService;

@SuppressWarnings("all")
@Transactional
@Service
public class ResourceServiceImpl implements ResourceService {
	@Resource
	private ResourceDao resourceDao;

	@Resource
	private RoleDao roleDao;

	public List<ZTreeNode> getGns(int id) {
		Map hm = new HashMap();
		hm.put("ID", id);
		List<HashMap> l = roleDao.getAllGns(hm);
		List<ZTreeNode> nodes = new ArrayList();
		// 循环数据，拼接返回数据
		for (int i = 0; i < l.size(); i++) {
			HashMap gns = l.get(i);
			// true需要判断
			nodes.add(new ZTreeNode(gns.get("ID").toString(), (String) gns
					.get("NAME"), gns.get("PARENTID").toString(), false,
					((String) gns.get("ISPARENT")).equals("1") ? true : false,
					(String) gns.get("ACTIONNAME")));
		}
		return nodes;
	}

	/**
	 * <p>
	 * Title: updateGns
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @see com.zbiti.system.service.interfaces.ResourceService#updateGns(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void updateGns(HttpServletRequest request) {

		String name = request.getParameter("name");
		String actionName = request.getParameter("actionName");
		String isParent = request.getParameter("isParent");
		String id = request.getParameter("id");
		Map map = new HashMap();
		map.put("NAME", name);
		map.put("ACTIONNAME", actionName);
		map.put("ISPARENT", isParent);
		map.put("ID", id);
		// 更新本节点
		resourceDao.updateGns(map);
		// 如果isParent是0，那么需要删除其下的所有子节点
		if ("0".equals(isParent)) {
			resourceDao.delChildGns(map);
		}

	}

	/**
	 * <p>
	 * Title: removeGns
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @see com.zbiti.system.service.interfaces.ResourceService#removeGns(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void removeGns(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map map = new HashMap();
		map.put("ID", id);
		resourceDao.delGns(map);
	}

	/**
	 * <p>
	 * Title: addGns
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @see com.zbiti.system.service.interfaces.ResourceService#addGns(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void addGns(HttpServletRequest request) {

		String name = request.getParameter("name");
		String actionName = request.getParameter("actionName");
		String isParent = request.getParameter("isParent");
		String parentId = request.getParameter("parentId");
		Map map = new HashMap();
		map.put("NAME", name);
		map.put("ACTIONNAME", actionName);
		map.put("ISPARENT", isParent);
		map.put("PARENTID", parentId);
		resourceDao.addGns(map);
		// 如果父节点是叶子节点那么还要将父节点的isParent置为1
		resourceDao.updateParentPoint(map);
	}

}
