package com.system.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.service.GeneralService;
import com.system.service.StaffService;
import com.system.service.UnifiedPageService;

import net.sf.json.JSONObject;
import util.page.BaseAction;
import util.page.UIPage;

@Controller
@RequestMapping("/unifiedPage")
@SuppressWarnings("all")
public class UnifiedPageController extends BaseAction {

	@Resource
	UnifiedPageService unifiedPageService;
	@Resource
	private GeneralService generalService;
	@Resource
	private StaffService staffService;
	
	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = unifiedPageService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/selectSelected.do")
	public void selectSelected(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		String roleNo = staffService.selectSelected(request);
		response.getWriter().write(roleNo);

	}
	
	@RequestMapping(value = "/assignRolePermissions.do")
	public ModelAndView assignRolePermissions(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String staff_id = request.getParameter("staff_id");
		// 根据staff_id去查找角色
		List roles = unifiedPageService.getRoles(staff_id);
		rs.put("asp_staff_id", staff_id);
		rs.put("roles", roles);
		return new ModelAndView("unified/staff-assignrole", rs);
	}
	
	@RequestMapping(value = "/saveRolePermissions.do")
	public void saveRolePermissions(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			staffService.saveRolePermissions(request);
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
	
	@RequestMapping(value = "/assignSoftPermissions.do")
	public ModelAndView assignSoftPermissions(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String staff_id = request.getParameter("staff_id");
		// 根据staff_id去查找角色
		List softs = unifiedPageService.getSofts(staff_id);
		rs.put("asp_staff_id", staff_id);
		rs.put("softs", softs);

		return new ModelAndView("unified/staff-assignsoft", rs);
	}
	
	@RequestMapping(value="/assignOutSitePermissions.do")
	public ModelAndView assignOutSitePermissions (HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> rs = new HashMap<String, Object>();
		String staff_id = request.getParameter("staff_id");
		Map<String, Object>  roles = unifiedPageService.getOutSitePermissions(staff_id);
		rs.put("staff_id", staff_id);
		if(roles != null){
			rs.put("AREA_LEVEL", roles.get("AREA_LEVEL"));
			rs.put("MANAGE_LEVEL", roles.get("MANAGE_LEVEL"));
		}else{
			rs.put("AREA_LEVEL", "");
			rs.put("MANAGE_LEVEL", "");
		}
		return new ModelAndView("unified/staff-asssignOutSite", rs);
	}
	
	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		// 获得登录ID用户的信息
//		String staffId = request.getSession().getAttribute("staffId").toString();
//		Map<String, String> staff = unifiedPageService.addRole(staffId);
		rs.put("areaList", areaList);
//		rs.put("staff", staff);
		return new ModelAndView("unified/staff-add", rs);
	}
	/**
	 * 保存帐号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Map save(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
//			staffService.insert(request);
			unifiedPageService.insert(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 * 编辑帐号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edit.do")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = unifiedPageService.edit(request);
		return new ModelAndView("unified/staff-edit", rs);
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询角色列表
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryRolePermissions.do")
	public void queryRolePermissions(HttpServletRequest request, HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = unifiedPageService.queryRolePermissions(request, pager);
		write(response, map);
	}
	
	/**
	 * 查询应用列表
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/querySoftPermissions.do")
	public void querySoftPermissions(HttpServletRequest request, HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = staffService.querySoftPermissions(request, pager);
		write(response, map);
	}
	
	/**
	 * 保存员工应用
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/saveSoftPermissions.do")
	public void saveSoftPermissions(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			staffService.saveSoftPermissions(request);
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
	/**
	 * 更新帐号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			unifiedPageService.update(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 * 删除帐号
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			unifiedPageService.delete(request);
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
	
	/**
	 * 网格管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("gridManage")
	public ModelAndView gridManage(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = unifiedPageService.gridManage(request);
		return new ModelAndView("unified/staff-dept", rs);
	}
	
	@RequestMapping("queryDept")
	public void queryDept(HttpServletRequest request, HttpServletResponse response, UIPage pager) throws IOException{
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = staffService.queryDept(request,pager);
		write(response, rs);
	}
	
	@RequestMapping("updateDept")
	@ResponseBody
	public void updateDept(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		Boolean status = true;
		try {
			staffService.updateDept(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	
	@RequestMapping(value = "/getSonAreaList.do")
    @ResponseBody
    public Map getSonAreaList(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List<Map<String, String>> sonAreaList = generalService.getSonAreaList((String) request.getParameter("areaId"));
		map.put("sonAreaList", sonAreaList);
		return map;
    }
	
	@RequestMapping(value = "/getLocalCompanys.do")
    @ResponseBody
    public void getLocalCompanys(HttpServletRequest request, HttpServletResponse response) {

		Map map = new HashMap();
		try {
		    List<Map<String, String>> companyList = generalService.getOwnCompany((String) request.getParameter("areaId"));
		    map.put("companyList", companyList);
		} catch (Exception e) {
		    e.printStackTrace();
		    map.put("status", false);
		}
		map.put("status", true);
		try {
		    response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
	/**
	 * 外力点赋权
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveAssignOutSitePermissions.do")
	public void saveAssignOutSitePermissions(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		Boolean status = true;
		try {
			staffService.saveAssignOutSitePermissions(request);
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
	
	/**
	 * 验证唯一性
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/proveUniqueness.do")
	@ResponseBody
	public Map proveUniqueness(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = staffService.proveUniqueness(request);
		map.put("status", status);
		return map;
	}
	/**
	 * 验证身份证号是否唯一
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/validateIdCard.do")
	@ResponseBody
	public Map validateIdCard(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = staffService.validateIdCard(request);
		map.put("status", status);
		return map;
	}
	
}
