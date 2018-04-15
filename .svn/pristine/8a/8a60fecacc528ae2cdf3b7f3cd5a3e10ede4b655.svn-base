package com.zhxj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.system.constant.RoleNo;
import com.zhxj.dao.ZhxjPlanDao;
import com.zhxj.service.ZhxjPlanService;

@Service
@Transactional
public class ZhxjPlanServiceImpl implements ZhxjPlanService {
	
	@Resource
	private ZhxjPlanDao zhxjPlanDao;
	
	@Override
	public Map<String, Object> queryPlan(HttpServletRequest request,
			UIPage pager) {
		String plan_no = request.getParameter("plan_no");
		String plan_name = request.getParameter("plan_name");
		String plan_start_time = request.getParameter("plan_start_time");
		String plan_end_time = request.getParameter("plan_end_time");
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		String son_area_id = request.getParameter("son_area_id");
		String plan_type = request.getParameter("plan_type");
		String plan_orgin = request.getParameter("plan_orgin");
		Map map = new HashMap();

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			// map.put("AREA_ID", session.getAttribute("areaId"));
			map.put("SON_AREA_ID", son_area_id);
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				map.put("SON_AREA_ID", son_area_id);
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}

		map.put("PLAN_NO", plan_no);
		map.put("PLAN_NAME", plan_name);
		map.put("PLAN_START_TIME", plan_start_time);
		map.put("PLAN_END_TIME", plan_end_time);
		map.put("PLAN_TYPE", plan_type);
		map.put("PLAN_ORGIN", plan_orgin);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = zhxjPlanDao.queryPlan(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	@Override
	public String planDelete(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String zhxj_Plan_id=request.getParameter("zhxj_id");
			zhxjPlanDao.removePlan(zhxj_Plan_id);
			result.put("status", true);
			result.put("msg", "删除成功！");
		} catch (Exception e) {
			result.put("status", false);
			result.put("msg", "删除失败！");
		}
		return result.toString();
	}

}
