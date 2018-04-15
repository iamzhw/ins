package com.cableCheck.serviceimpl;

import icom.cableCheck.dao.CheckPortDao;
import icom.cableCheck.serviceimpl.CheckTaskServiceImpl;
import icom.system.dao.CableInterfaceDao;
import icom.util.Result;
import icom.webservice.client.text;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CheckProcessDao;
import com.cableCheck.dao.ExcelTeamDao;
import com.cableCheck.dao.MainTainCompanyDao;
import com.cableCheck.dao.TaskMangerDao;
import com.cableCheck.dao.TeamManagerDao;
import com.cableCheck.dao.TeamUserDao;
import com.cableCheck.service.MainTainCompanyService;
import com.cableCheck.service.TaskMangerService;
import com.cableCheck.service.TeamManagerService;
import com.linePatrol.util.DateUtil;
import com.system.model.ZTreeNode;
import com.webservice.client.ElectronArchivesService;

@SuppressWarnings("all")
@Service
public class TeamManagerServiceImpl implements TeamManagerService {
	Logger logger = Logger.getLogger(TeamManagerServiceImpl.class);
	
	@Resource
	private TeamManagerDao teamManagerDao;
	
	@Resource
	private MainTainCompanyDao mainTainCompanyDao;
	
	@Resource
	private TeamUserDao teamUserDao;
	
	@Override
	public Map<String, Object> listUserRole(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String teamId = request.getParameter("team_id")==null?"":request.getParameter("team_id");
		map.put("teamId", teamId);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map<String,Object>> olists = new ArrayList<Map<String,Object>>();
		olists = teamManagerDao.listUserRole(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	
	@Override
	public Map<String, Object> listTeam(HttpServletRequest request, UIPage pager) {
		Query query = comParam(request, pager);
		
		String team_id = request.getParameter("clickNode");
		Boolean bool = true;
		Map map = new HashMap();
		if(team_id!=null && StringUtils.isNotBlank(team_id)){
			map.put("id", team_id);
			bool = mainTainCompanyDao.isExistDept(map)>0;
		}
		List<Map<String,Object>> olists = new ArrayList<Map<String,Object>>();
		if(bool){
			olists = teamManagerDao.listTeam(query);
		}else{
			map.put("team_id", team_id);
			String staff_name = request.getParameter("staff_name")==null?"":request.getParameter("staff_name");
			map.put("staff_name", staff_name);
			query.setQueryParams(map);
			olists = teamUserDao.listTeamUser(query);
		}
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	private Query comParam(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String companyName = request.getParameter("company_name");
		String area = request.getParameter("area");
		String option = request.getParameter("option")==null?"0":request.getParameter("option");
//		String option_son = request.getParameter("option_son")==null?"0":request.getParameter("option_son");
		String son_area = request.getParameter("son_area");
		String team_name = request.getParameter("team_name");
		String staff_name = request.getParameter("staff_name");
		String clickNode = request.getParameter("clickNode");
		map.put("clickNode", clickNode);
		map.put("company_name", companyName);
		map.put("team_name", team_name);
		map.put("area", area);
		map.put("son_area", son_area);
		map.put("staff_name", staff_name);
		map.put("option", option);
//		map.put("option_son", option_son);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}

	@Override
	public Map<String, Object> updateTeam(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		String desc = "操作成功";
		Map map = new HashMap();
		String select_teamId = request.getParameter("select_teamId");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		map.put("select_teamId", select_teamId);
		map.put("area", area);
		map.put("son_area", son_area);
		
		try{
			teamManagerDao.updateTeam(map);
		}catch(Exception e){
			e.printStackTrace();
			desc = "操作失败";
			result.put("success", "002");
		}
		result.put("desc", desc);
		return result;
	}

	@Override
	public Map<String, Object> updateTeamCompany(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		String desc = "操作成功";
		Map map = new HashMap();
		String select_teamId = request.getParameter("team_id");
		String company_id = request.getParameter("company_id");
		map.put("select_teamId", select_teamId);
		map.put("companyId", company_id);
		
		try{
			if(StringUtils.isNotBlank(select_teamId)){
				String[] ids = select_teamId.split(",");
				for (int i = 0; i < ids.length; i++) {
					map.put("teamId", ids[i]);
					
					Boolean bool = teamManagerDao.selectRelationCount(map)>0;
					if(bool){
						//如果relation表中有相应的teamid的数据，则更新
						teamManagerDao.updateCompany(map);
					}else{
						//如果没有则insert
						mainTainCompanyDao.saveRelation(map);
					}
				}
			}else{
				System.out.println("teamId参数为空");
				desc = "页面没有选择班组信息";
			}
		}catch(Exception e){
			e.printStackTrace();
			desc = "操作失败";
			result.put("success", "002");
		}
		result.put("desc", desc);
		return result;
	}

	@Override
	public Map<String, Object> updateShenhe(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		String desc = "操作成功";
		Map map = new HashMap();
		String staff_ids = request.getParameter("staff_ids");
		String team_ids = request.getParameter("team_ids");
		map.put("staff_ids", staff_ids);
		map.put("team_ids", team_ids);
		try{
			forParam(staff_ids, team_ids);
		}catch(Exception e){
			desc = "操作失败";
			result.put("success", "002");
		}
		result.put("desc", desc);
		return result;
	}
	
	
	@Override
	public Map<String, Object> updateDoudi(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		String desc = "操作成功";
		Map map = new HashMap();
		String staff_ids = request.getParameter("staff_ids");
		String team_ids = request.getParameter("team_ids");
		map.put("staff_ids", staff_ids);
		map.put("team_ids", team_ids);
		try{
			forParam_doudi(staff_ids, team_ids);
		}catch(Exception e){
			desc = "操作失败";
			result.put("success", "002");
		}
		result.put("desc", desc);
		return result;
	}
	
	@Override
	public Map<String, Object> resetTeamStaff(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		String desc = "操作成功";
		Map map = new HashMap();
		String team_ids = request.getParameter("team_ids");
		String shy = request.getParameter("shy");
		String ddg = request.getParameter("ddg");
		map.put("team_ids", team_ids);
		map.put("shy", shy);
		map.put("ddg", ddg);
		try{
			teamManagerDao.resetTeamStaff(map);
		}catch(Exception e){
			desc = "操作失败";
			result.put("success", "002");
		}
		result.put("desc", desc);
		return result;
	}
	
	
	@Override
	public Map<String, Object> updateTeamStaff(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		String desc = "操作成功";
		Map map = new HashMap();
		try{
			String team_ids = request.getParameter("team_ids");
			String staff_no = request.getParameter("staff_no");
			map.put("team_ids", team_ids);
			String[] str = staff_no.split(",");
			for (int i = 0; i < str.length; i++) {
				String[] str_no = str[i]==null?new String[]{}:str[i].split("@@");
				if(str_no!=null&&str_no.length==2) {
					String staffNo = str_no[0];
					String role_type = str_no[1];
					map.put("staff_no", staffNo);
					map.put("role_type", role_type);
					teamManagerDao.updateTeamStaff(map);	
				}
			}
		}catch(Exception e){
			desc = "操作失败";
			result.put("success", "002");
		}
		result.put("desc", desc);
		return result;
	}

	/**
	 * 准备设置审核员的参数
	 * @param staff_ids
	 * @param team_ids
	 */
	private void forParam(String staff_ids, String team_ids) {
		String[] teams = team_ids.split(",");
		String[] staffs = staff_ids.split(",");
		Map map = new HashMap();
		for (int i = 0; i < teams.length; i++) {
			String teamId = teams[i];
			map.put("teamId", teamId);
			for (int j = 0; j < staffs.length; j++) {
				String staffId = staffs[j];	
				map.put("staffId", staffId);
				updateParam(map);
			}
		}
	}
	
	/**
	 * 数据库层进行操作
	 * @param map
	 */
	public void updateParam(Map map){
		try {
			Boolean bool = teamManagerDao.selectStaffCount(map)>0;
			if(bool){
				//update
				teamManagerDao.updateShenhe(map);
			}else{
				//add
				teamManagerDao.addShenhe(map);
			}
			
			/*光网助手的审核员权限赋值*/
			grantCableRole(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/////////////////////////兜底岗位/////////////////////////////////////////////
	
	/**
	 * 准备设置审核员的参数
	 * @param staff_ids
	 * @param team_ids
	 */
	private void forParam_doudi(String staff_ids, String team_ids) {
		String[] teams = team_ids.split(",");
		String[] staffs = staff_ids.split(",");
		Map map = new HashMap();
		for (int i = 0; i < teams.length; i++) {
			String teamId = teams[i];
			map.put("teamId", teamId);
			for (int j = 0; j < staffs.length; j++) {
				String staffId = staffs[j];	
				map.put("staffId", staffId);
				updateParam_doudi(map);
			}
		}
	}
	
	/**
	 * 数据库层进行操作
	 * @param map
	 */
	public void updateParam_doudi(Map map){
		try {
			Boolean bool = teamManagerDao.selectStaffCount(map)>0;
			if(bool){
				//update
				teamManagerDao.updateDoudi(map);
			}else{
				//add
				teamManagerDao.addDoudi(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////兜底岗位/////////////////////////////////////////////
	
	
	
	/**
     * 光网助手审核员
     * 赋权
     */
    public void grantCableRole(Map map){
    	//先判断该账户是否有"官网助手装维审核员"的权限，没有则新增
    	if(teamManagerDao.isExistCableRole(map)>0){
    		logger.debug("该账户已存在官网助手审核员的权限");
    	}else{
    		teamManagerDao.insertCableRole(map);	
    	}
    }

    
    private Query comParam_export(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String clickNode = request.getParameter("clickNode");
		String selectTeamIds = request.getParameter("select_team_ids");
		map.put("selectTeamIds", selectTeamIds);
		map.put("clickNode", clickNode);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
    
	@Override
	public void exportExcel(Map<String, Object> para, HttpServletRequest request, HttpServletResponse response,
			UIPage pager) {
		List<String> title = Arrays.asList(new String[] { "地市 ","区县", "代维公司 ", "班组名称","审核人/接单人","兜底岗","综调人数"});
		List<String> code = Arrays
				.asList(new String[] { "AREA_NAME", "SON_AREA_NAME","COMPANY_NAME", "TEAM_NAME","SHENHE_NAME","DOUDI_NAME","STAFF_NUM"});
		Map<String, Object> paras = new HashMap<String, Object>();
		
		Map map = new HashMap();
		String clickNode = request.getParameter("clickNode");
		String selectTeamIds = request.getParameter("select_team_ids");
		if(StringUtils.isNotBlank(selectTeamIds)){
			//根据选中的班组id查询相关班组信息
			map.put("teamIds", selectTeamIds);
		}else{
			//为空，则导出树形选中的组织下的班组信息
			map.put("teamIds", clickNode);
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map<String,Object>> data = teamManagerDao.exportTeam(query);
		
		List<String> title_user = Arrays.asList(new String[] { "地市 ","区县", "代维公司 ", "班组名称","人员账户","人员名称","人员角色"});
		List<String> code_user = Arrays
				.asList(new String[] { "AREA_NAME", "SON_AREA_NAME","COMPANY_NAME", "TEAM_NAME","STAFF_NO","STAFF_NAME","ROLE_NAME"});
		List<Map<String,Object>> data_user = teamManagerDao.exportTeamUser(query);
		
		String fileName = "班组管理";
		String firstLine ="班组管理";

		List<List<String>> title_a = new ArrayList<List<String>>(); 
		List<List<String>> code_a= new ArrayList<List<String>>();
	    List<List<Map<String, Object>>> data_a =  new ArrayList<List<Map<String, Object>>>();
	    title_a.add(title);
	    code_a.add(code);
	    data_a.add(data);
	    title_a.add(title_user);
	    code_a.add(code_user);
	    data_a.add(data_user);
		try {
			
			ExcelUtil.generateExcelAndDownload(title_a, code_a, data_a ,request, response, fileName, firstLine,2);
			
//			ExcelUtil.generateExcelAndDownload(title, code, data, request, response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Map<String, Object> queryStaff(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String STAFF_NO = request.getParameter("STAFF_NO");
		String STAFF_NAME = request.getParameter("STAFF_NAME");
		String SON_AREA_ID = request.getParameter("SON_AREA_ID");
		String AREA_ID = request.getParameter("AREA_ID");
		map.put("STAFF_NAME", STAFF_NAME);
		map.put("STAFF_NO", STAFF_NO);

		map.put("SON_AREA_ID", SON_AREA_ID);
		map.put("AREA_ID", AREA_ID);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = teamManagerDao.queryStaff(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		// pmap.put("total", list.size());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public Map<String, Object> queryRolePermissions(HttpServletRequest request, UIPage pager) {
		//1.查询出当前登录人员ID
		String staff_id = String.valueOf(request.getSession().getAttribute("staffId"));
		Map map = new HashMap();//条件map
		List<Map> olists = teamManagerDao.queryRolePermission(map);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return pmap;
	}

	@Override
	public Map<String, Object> queryImportLog(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String staff_id = String.valueOf(request.getSession().getAttribute("staffId"));
		map.put("staff_id", staff_id);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = teamManagerDao.queryImportLog(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	
	@Override
	public void calProcedures(HttpServletRequest request) {
		Map map = new HashMap();
		String team_id_in = request.getParameter("team_id_in");
		map.put("team_id_in", team_id_in);
		teamManagerDao.calBatchUpdateBanzu(map);//执行更新班组
		teamManagerDao.calBatchUpdateZwAreaId(map);//执行更新班组下的人员
	}
	
}
