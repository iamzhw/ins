package com.cableCheck.action;

import icom.util.BaseServletTool;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cableCheck.service.ExcelTeamService;
import com.cableCheck.service.MainTainCompanyService;
import com.cableCheck.service.TaskMangerService;
import com.cableCheck.service.TeamManagerService;
import com.cableCheck.service.WrongPortReportService;
import com.linePatrol.util.MapUtil;
import com.system.model.ZTreeNode;
import com.system.service.GeneralService;
import com.system.sys.SystemListener;

import util.page.BaseAction;
import util.page.UIPage;

@RequestMapping(value = "/teamManager")
@SuppressWarnings("all")
@Controller
public class TeamManagerController extends BaseAction{

	@Resource
	private TeamManagerService teamManagerService;
	@Resource
	private ExcelTeamService excelTeamService;
	
	/**
	 * 跳转到代维公司页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list.do")
	public ModelAndView addTask(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cablecheck/teamManager/teamManager", null);
	}
	
	@RequestMapping(value = "/listAll.do")
	public void listAll(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = teamManagerService.listTeam(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/listUserRole.do")
	public void listUserRole(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = teamManagerService.listUserRole(request, pager);
		write(response, map);
	}
	
	
	
	@RequestMapping(value = "/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {	
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		teamManagerService.exportExcel(para, request, response, pager);
	}
	
	
	@RequestMapping(value = "/updateTeamArea.do")
	public void updateTeamArea(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = teamManagerService.updateTeam(request);
		write(response, map);
	}
	
	
	@RequestMapping(value = "/relationWin.do")
	public ModelAndView relationWin(HttpServletRequest request,
			HttpServletResponse response){
		String type = request.getParameter("type");
		if("1".equals(type)){
			return new ModelAndView("cablecheck/teamManager/updateArea");	
		}else if("2".equals(type)){
			return new ModelAndView("cablecheck/teamManager/relationCompany");
		}else if("3".equals(type)){
			return new ModelAndView("cablecheck/teamManager/staffRole");
		}else{
			return new ModelAndView("cablecheck/teamManager/relationTeam");
		}
	}
	
	@RequestMapping(value = "/updateTeamCompany.do")
	public void updateTeamCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = teamManagerService.updateTeamCompany(request);
		write(response, map);
	}
	
	// 获取员工
	@RequestMapping(value = "/check_staff.do")
	public ModelAndView check_staff(HttpServletRequest request,
			HttpServletResponse response,String AREA_ID) throws IOException {
		Map<String, Object> rs = new HashMap<String, Object>();
		return new ModelAndView("cablecheck/teamManager/check_staff");
	}
	
	/**
	 * 更新审核人和接单人
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateShenhe.do")
	public void updateShenhe(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = teamManagerService.updateShenhe(request);
		write(response, map);
	}
	
	/**
	 * 更新兜底岗
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateDoudi.do")
	public void updateDoudi(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = teamManagerService.updateDoudi(request);
		write(response, map);
	}
	
	
	
	@RequestMapping("import")
	public ModelAndView importStaff() {
		return new ModelAndView("cablecheck/teamManager/import");
	}
	
	
	@RequestMapping("reset")
	public ModelAndView reset(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cablecheck/teamManager/reset");
	}
	
	
	/**
	 * 清除审核人/接单人/兜底岗
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/resetTeamStaff.do")
	public void resetTeamStaff(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = teamManagerService.resetTeamStaff(request);
		write(response, map);
	}

	
	/**
	 * 更新审核人/接单人/兜底岗
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateTeamStaff.do")
	public void updateTeamStaff(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = teamManagerService.updateTeamStaff(request);
		write(response, map);
	}
	
	
	@RequestMapping("import_save")
	@ResponseBody
	public void importDo(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file)
					throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
				excelTeamService.importExcel(request,file).toString());
	}
	
	/**
	 * 查询导入的记录，包括成功与否
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@RequestMapping(value = "/query_import_log.do")
	public void queryImportLog(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = teamManagerService.queryImportLog(request, pager);
		write(response, map);
	}
	

	@RequestMapping(value = "/query_staff.do")
	public void queryStaff(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = teamManagerService.queryStaff(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/assignRolePermissions.do")
	public ModelAndView assignRolePermissions(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
//		String staff_id = request.getParameter("staff_id");
//		// 根据staff_id去查找角色
//		List roles = staffService.getRoles(staff_id);
//		rs.put("asp_staff_id", staff_id);
//		rs.put("roles", roles);
		return new ModelAndView("cablecheck/teamManager/staff-assignrole", rs);
	}
	
	
	@RequestMapping(value = "/queryRolePermissions.do")
	public void queryRolePermissions(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = teamManagerService.queryRolePermissions(request,
				pager);
		write(response, map);
	}
	
	@RequestMapping(value = "/openImportRecord.do")
	public ModelAndView openImportRecord(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cablecheck/teamManager/importRecord",null);
	}
	
	
	public static void main(String[] args) {
		List list = new ArrayList();
//		Map map = new HashMap();
		JSONObject map = new JSONObject();
		for (int i = 0; i < 3; i++) {
			map.put("key",i);
			list.add(map);
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	
	@RequestMapping("downloadExample")
	@ResponseBody
	public void downloadExample(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("装维班组与代为公司及审核接单岗批量导入.xlsx").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/装维班组与代为公司及审核接单岗批量导入.xlsx")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	
	@RequestMapping(value = "/calProcedures.do")
	public void calProcedures(HttpServletRequest request, HttpServletResponse response) throws IOException {
		teamManagerService.calProcedures(request);
	}
	
	
}
