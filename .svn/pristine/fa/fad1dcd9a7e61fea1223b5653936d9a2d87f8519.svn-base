package com.system.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.system.service.GeneralService;
import com.system.service.GridService;

import util.page.BaseAction;
import util.page.UIPage;

/**
 * 网格管理
 * @author xiekui
 *
 */

@SuppressWarnings("all")
@RequestMapping(value = "/Grid")
@Controller
public class GridControloer extends BaseAction{
	@Resource
	GridService gridService; 
	@Resource
	private GeneralService generalService;

   /**
	 * 
	 * @Title: query
	 * @Description: 网格查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param staffForm
	 * @param: @throws IOException
	 * @return: void
	 * @throws
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		rs.put("areaList", areaList);
		return new ModelAndView("system/grid/grid-index", rs);
	}
	
	/**
	 * 
	 * @Title: query
	 * @Description: 员工查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param staffForm
	 * @param: @throws IOException
	 * @return: void
	 * @throws
	 */
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = gridService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/assignGridPermissions.do")
	public ModelAndView assignGridPermissions(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String grid_id = request.getParameter("grid_id");
		// 根据staff_id去查找角色
	
		//根据gird查询管理员
		List aduits = gridService.getAduits(grid_id);
		rs.put("aduits", aduits);
		rs.put("grid_id", grid_id);
		return new ModelAndView("system/grid/grid-assignadmin", rs);
	}
	
	/**
	 * 
	 * @Title: query
	 * @Description: 员工查询
	 * @param: @param request
	 * @param: @param response
	 * @param: @param staffForm
	 * @param: @throws IOException
	 * @return: void
	 * @throws
	 */
	@RequestMapping(value = "/queryAduits.do")
	public void queryAduits(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = gridService.queryAduits(request, pager);
		write(response, map);
	}
	/**
	 * 
	 * @Title: query
	 * @Description: 保存网格管理员权限
	 * @param: @param request
	 * @param: @param response
	 * @param: @param staffForm
	 * @param: @throws IOException
	 * @return: void
	 * @throws
	 */
	@RequestMapping(value = "/saveAduits.do")
	public void saveRolePermissions(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			gridService.saveAauits(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("success", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
