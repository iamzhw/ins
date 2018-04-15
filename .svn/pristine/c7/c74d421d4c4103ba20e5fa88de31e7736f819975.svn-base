package com.cableInspection.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableInspection.service.CableTaskService;
import com.system.constant.RoleNo;

/**
 * @ClassName: CablePlanController
 * @Description: 寻线任务管理
 * @author xiazy
 * @date: 2014-6-20
 * 
 */
@RequestMapping(value = "/CableTask")
@SuppressWarnings("all")
@Controller
public class CableTaskController extends BaseAction {

	@Resource
	private CableTaskService cableTaskervice;

	@RequestMapping(value = "/task_index.do")
	public ModelAndView indexTask(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = cableTaskervice.getRole(request);
		rs.put("isSonAreaAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN_SON_AREA));
		rs.put("isAreaAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN_AREA));
		return new ModelAndView("cableinspection/cable/task/task_index", rs);
	}

	@RequestMapping(value = "/task_query.do")
	public void queryTask(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = cableTaskervice.taskQuery(request, pager);
		write(response, map);
	}

	@RequestMapping("task_delete")
	public JSONObject deleteTask(HttpServletRequest request) {
		return cableTaskervice.deleteTask(request);
	}

}
