package com.system.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.system.service.GeneralService;
import com.system.service.StaffService;
import com.system.sys.SystemListener;
import com.util.PasswordUtil;

/**
 * 
 * @ClassName: StaffController
 * @Description: 员工管理
 * @author: SongYuanchen
 * @date: 2014-1-16
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/Staff")
@Controller
public class StaffController extends BaseAction {
	@Resource
	private StaffService staffService;
	@Resource
	private GeneralService generalService;

	/**
	 * 
	 * @Title: index
	 * @Description: 员工管理初始化方法
	 * @param: @param request
	 * @param: @param response
	 * @param: @param staffForm
	 * @param: @return
	 * @return: ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		rs.put("areaList", areaList);
		return new ModelAndView("system/staff/staff-index", rs);
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
		Map<String, Object> map = staffService.query(request, pager);
		write(response, map);
	}

	@RequestMapping("queryHandler")
	public void queryHandler(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException{
		Map<String, Object> map = staffService.queryHandler(request, pager);
		write(response, map);
	}

	@RequestMapping(value = "/assignSoftPermissions.do")
	public ModelAndView assignSoftPermissions(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String staff_id = request.getParameter("staff_id");
		// 根据staff_id去查找角色
		List softs = staffService.getSofts(staff_id);
		rs.put("asp_staff_id", staff_id);
		rs.put("softs", softs);

		return new ModelAndView("system/staff/staff-assignsoft", rs);
	}

	@RequestMapping(value = "/querySoftPermissions.do")
	public void querySoftPermissions(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = staffService.querySoftPermissions(request,
				pager);
		write(response, map);
	}

	@RequestMapping(value = "/saveSoftPermissions.do")
	public void saveSoftPermissions(HttpServletRequest request,
			HttpServletResponse response) {
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

	@RequestMapping(value = "/assignRolePermissions.do")
	public ModelAndView assignRolePermissions(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String staff_id = request.getParameter("staff_id");
		// 根据staff_id去查找角色
		List roles = staffService.getRoles(staff_id);
		rs.put("asp_staff_id", staff_id);
		rs.put("roles", roles);
		return new ModelAndView("system/staff/staff-assignrole", rs);
	}

	@RequestMapping(value="/assignOutSitePermissions.do")
	public ModelAndView assignOutSitePermissions (HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> rs = new HashMap<String, Object>();
		String staff_id = request.getParameter("staff_id");
		Map<String, Object>  roles = staffService.getOutSitePermissions(staff_id);
		rs.put("staff_id", staff_id);
		if(roles != null){
			rs.put("AREA_LEVEL", roles.get("AREA_LEVEL"));
			rs.put("MANAGE_LEVEL", roles.get("MANAGE_LEVEL"));
		}else{
			rs.put("AREA_LEVEL", "");
			rs.put("MANAGE_LEVEL", "");
		}
		return new ModelAndView("system/staff/staff-asssignOutSite", rs);
	}

	@RequestMapping(value="/saveAssignOutSitePermissions.do")
	public void saveAssignOutSitePermissions(HttpServletRequest request,
			HttpServletResponse response) {
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

	@RequestMapping(value = "/queryRolePermissions.do")
	public void queryRolePermissions(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = staffService.queryRolePermissions(request,
				pager);
		write(response, map);
	}

	@RequestMapping(value = "/saveRolePermissions.do")
	public void saveRolePermissions(HttpServletRequest request,
			HttpServletResponse response) {
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

	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		// 获得登录ID用户的信息
		String staffId = request.getSession()
				.getAttribute("staffId").toString();
		Map<String, String> staff = staffService.addRole(staffId);
		rs.put("areaList", areaList);
		rs.put("staff", staff);
		return new ModelAndView("system/staff/staff-add", rs);
	}

	@RequestMapping(value = "/proveUniqueness.do")
	@ResponseBody
	public Map proveUniqueness(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = staffService.proveUniqueness(request);
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/validateIdCard.do")
	@ResponseBody
	public Map validateIdCard(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = staffService.validateIdCard(request);
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Map save(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			staffService.insert(request);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/edit.do")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = staffService.edit(request);
		return new ModelAndView("system/staff/staff-edit", rs);
	}

	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			staffService.update(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			staffService.delete(request);
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

	@RequestMapping(value = "/export.do")
	public String export(HttpServletRequest request, ModelMap mm) {
		List<Map> dataList = staffService.queryStaffList(request);
		String[] cols = new String[] { "STAFF_ID", "STAFF_NAME", "STAFF_NO",
				"ROLE_NAME", "SOFTWARE_NAME", "TEL", "EMAIL", "ID_NUMBER",
				"AREA", "SON_AREA", "STATUS" };
		mm.addAttribute("cols", cols);
		String[] colsName = new String[] { "员工编号", "员工姓名", "员工账号",
				"角色", "应用", "联系方式", "邮箱", "证件号码",
				"地市", "区县", "状态" };
		mm.addAttribute("name", "员工");
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", dataList);
		return "dataListExcel";
	}

	@RequestMapping(value = "/selectSelected.do")
	public void selectSelected(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map map = new HashMap();
		String roleNo = staffService.selectSelected(request);
		response.getWriter().write(roleNo);

	}

	/**
	 * 
	 * @Title: lookup
	 * @Description: 提供给其他功能查询列表
	 * @param: @param request
	 * @param: @param response
	 * 
	 * @return: ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "lookup.do")
	public ModelAndView lookup(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		rs.put("areaList", areaList);
		return new ModelAndView("system/staff/lookup-index", rs);
	}

	@RequestMapping("import")
	public ModelAndView importStaff() {
		return new ModelAndView("system/staff/import");
	}

	@RequestMapping("downloadExample")
	@ResponseBody
	public void downloadExample(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("用户导入样例.xls").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/用户导入样例.xls")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping("import_save")
	@ResponseBody
	public void importDo(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file)
					throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
				staffService.importDo(request, file).toString());
	}

	/**
	 * 修改密码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "modifyPassword.do")
	public void modifyPassword(HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			String password = request.getParameter("password");
			if(!PasswordUtil.checkPassword(password))
			{
				status = false;
				map.put("info","请确认密码是否至少8位，且包含数字、大小写字母、特殊符号！");
			}
			else{
				staffService.modifyPassword(request);
				//写进缓存
				HttpSession session = request.getSession();
				session.setAttribute("passWord", password);
			}
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
			map.put("info","密码修改失败！");
		}
		map.put("status", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 修改身份证号码、手机号码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "modifyIdNumber.do")
	public void modifyIdNumber(HttpServletRequest request, HttpServletResponse response){
		Map map = new HashMap();
		Boolean status = true;
		try {
			staffService.modifyIdNumber(request);
//			//写进缓存
//			HttpSession session = request.getSession();

		} catch (Exception e) {

			e.printStackTrace();
			status = false;
			map.put("info","实名认证失败！");
		}
		map.put("status", status);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("gridManage")
	public ModelAndView gridManage(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = staffService.gridManage(request);
		return new ModelAndView("system/staff/staff-dept", rs);
	}

	@RequestMapping("queryDept")
	public void queryDept(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException{
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = staffService.queryDept(request,pager);
		write(response, rs);
	}

	@RequestMapping("updateDept")
	@ResponseBody
	public void updateDept(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
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
	/**
	 * 验证身份证、手机号是否注册，用于回现
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/queryByStaffId.do")
	@ResponseBody
	public void queryByStaffId(HttpServletRequest request,HttpServletResponse response, Model model){
		
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
		Map map = staffService.queryByStaffId(staffId);
		model.addAttribute("tel", null==map.get("TEL")?"":map.get("TEL").toString());
		model.addAttribute("id_number", null==map.get("ID_NUMBER")?"":map.get("ID_NUMBER").toString());
		
	}
}
