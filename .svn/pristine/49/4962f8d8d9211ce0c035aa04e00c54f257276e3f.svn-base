package com.cableCheck.serviceimpl;

import icom.cableCheck.dao.CheckPortDao;
import icom.cableCheck.serviceimpl.CheckTaskServiceImpl;
import icom.system.dao.CableInterfaceDao;
import icom.util.Result;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
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
import com.cableCheck.dao.MainTainCompanyDao;
import com.cableCheck.dao.TaskMangerDao;
import com.cableCheck.service.MainTainCompanyService;
import com.cableCheck.service.TaskMangerService;
import com.linePatrol.util.DateUtil;
import com.system.model.ZTreeNode;
import com.webservice.client.ElectronArchivesService;

@SuppressWarnings("all")
@Service
public class MainTainCompanyServiceImpl implements MainTainCompanyService {
	Logger logger = Logger.getLogger(MainTainCompanyServiceImpl.class);
	
	@Resource
	private MainTainCompanyDao mainTainCompanyDao;
	
	@Override
	public void exportExcel(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		List<String> title = Arrays.asList(new String[] { "地市 ", "代维公司 ", "班组名称"});
		List<String> code = Arrays
				.asList(new String[] { "AREA_NAME", "COMPANY_NAME", "BANZU_NAME"});
		Query query = comParam_select_export(request, pager);

		List<Map<String,Object>> data = mainTainCompanyDao.listCompany(query);
		
		String fileName = "代维公司";
		String firstLine = "代维公司";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request, response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Map<String, Object> listCompany(HttpServletRequest request, UIPage pager) {
		Query query = comParam_select_export(request, pager);
		List<Map<String,Object>> olists = mainTainCompanyDao.listCompany(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	private Query comParam_select_export(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String companyName = request.getParameter("company_name");
		String areaId = request.getParameter("area_id")==null?"":request.getParameter("area_id");
		map.put("company_name", companyName);
		map.put("areaId", areaId);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}

	@Override
	public Map<String, Object> selectCompany(HttpServletRequest request) {
		Map map = new HashMap();
		String id = request.getParameter("COMPANY_ID");
		map.put("id", id);
		List<Map> data = mainTainCompanyDao.selectCompany(map);
		if(data.size()>0){
			return data.get(0);
		}else{
			return new HashMap<String, Object>();
		}
	}
	
	@Override
	public Map<String, Object> editCompany(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		
		Map mapParam = new HashMap();
		Boolean isAdd = comParam(request,mapParam);
		String desc = "新增成功";
		try {
			if(isAdd){
				//add
				mainTainCompanyDao.addCompany(mapParam);
			}else{
				//update
				mainTainCompanyDao.updateCompany(mapParam);
				desc = "更新成功";
			}
		} catch (Exception e) {
			result.put("success", "003");
			result.put("desc", "新增或更新代维公司失败");
			e.printStackTrace();
		}
		result.put("desc", desc);
		return result;
	}
	
	
	@Override
	public Map<String, Object> saveRelation(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		String companyId = request.getParameter("company_id");
		String teamId = request.getParameter("team_id");
		Map mapParam = new HashMap();
		String desc = "关联成功";
		Map map = new HashMap();
		map.put("companyId", companyId);
		try {

			//更新插入之前，先清空相关的关联关系:根据companyId清除
			mainTainCompanyDao.deleteRelation(map);
			
			if(StringUtils.isNotBlank(teamId)){
				String[] ids = teamId.split(",");
				for (int i = 0; i < ids.length; i++) {
					map.put("teamId", ids[i]);
					mainTainCompanyDao.saveRelation(map);
				}
			}else{
				System.out.println("teamId参数为空");
				desc = "页面没有选择班组信息";
			}
		} catch (Exception e) {
			result.put("success", "003");
			result.put("desc", "班组和代维公司的关联操作失败");
			e.printStackTrace();
		}
		result.put("desc", desc);
		return result;
	}
	
	public Boolean comParam(HttpServletRequest request,Map map){
		String comId = request.getParameter("COMPANY_ID");
		String comName = request.getParameter("COMPANY_NAME");
		String areaId = request.getParameter("area_id");
		String areaName = request.getParameter("area_name");
		HttpSession session = request.getSession();
		String staff = session.getAttribute("staffId").toString();
		Boolean isAdd = false;
		if(StringUtils.isBlank(comId)){
			isAdd = true;
		}
		map.put("staff", staff);
		map.put("comId", comId);
		map.put("comName", comName);
		map.put("areaId", areaId);
		map.put("areaName", areaName);
		map.put("isAdd",isAdd);
		return isAdd;
	}
	
	
	@Override
	public Map<String, Object> deleteCompany(HttpServletRequest request) {
		Map map = new HashMap();
//		String id = request.getParameter("COMPANY_ID");
		String id = request.getParameter("selected");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				map.clear();
				map.put("comId", ids[i]);
				mainTainCompanyDao.deleteCompany(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "003");
			result.put("desc", "删除失败");
			return result;
		}
		result.put("code", "001");
		result.put("desc", "删除成功");
		return result;
	}
	
	public Map<String, Object> listGridOrder(HttpServletRequest request, UIPage pager){
		Map map = new HashMap();
		String gridId = request.getParameter("grid_id");
		String teamId = request.getParameter("team_Id");
		String startDate = request.getParameter("start_date");
		String endDate = request.getParameter("end_date");
		map.put("gridId", gridId);
		map.put("teamId", teamId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> olists = mainTainCompanyDao.listGridOrder(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	@Override
	public List<ZTreeNode> getDeptTree(Map map){
		
		List<Map> l = null;
		Boolean bool = true;
		Boolean open = true;
		if(map.get("id")!=null && StringUtils.isNotBlank(map.get("id").toString())){
			bool = mainTainCompanyDao.isExistDept(map)>0;
			open = false;
		}
		
		//if("0".equals(map.get("user").toString())){
		if(bool){
			map.put("is_look","0");
//			Boolean bool_2 = mainTainCompanyDao.lookStatus(map)==0;
//			if(bool_2){
//				map.remove("is_look");
//			}
			l = mainTainCompanyDao.getDeptTree(map);	
		}else{
			l = mainTainCompanyDao.getUserTree(map);
		}
		
		List<ZTreeNode> nodes = new ArrayList();
		// 循环数据，拼接返回数据
		for (int i = 0; i < l.size(); i++) {
			Map gns = l.get(i);
			// true需要判断
			nodes.add(new ZTreeNode(gns.get("ID").toString(), (String) gns
					.get("NAME"), gns.get("PARENTID").toString(), true,
					((String) gns.get("ISPARENT")).equals("1") ? true : false,
					((BigDecimal) gns.get("ACTIONNAME")).toString()));
		}
		return nodes;
	}

	@Override
	public Map<String, Object> updateTreeStatus(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "001");
		String node_id = request.getParameter("node_id");
		String parent_id = request.getParameter("parent_id");
		String isParent = request.getParameter("isParent");
		Map mapParam = new HashMap();
		String desc = "操作成功";
		Map map = new HashMap();
		try {
			if("true".equals(isParent)){
				//删除班组,先判断班组下是否有人员，如果有，则返回有人员的班组名称
				map.put("teamId", node_id);
				
				String team_name = "-1";//mainTainCompanyDao.selectTeamName(map);
				if("-1".equals(team_name)){
					//表示班组下无人员，则直接删除
					mainTainCompanyDao.updateTreeStatus(map);
				}else {
					//表示班组下有人员，不可删除，提示
					desc = "以下班组中存在人员，不可删除该班组。班组："+team_name;
				}
			}else if("false".equals(isParent)){
				//删除人员
				String[] node_ids = node_id.split(",");
				for (int i = 0; i < node_ids.length; i++) {
					delUser(node_ids[i], parent_id, map);	
				}
			}else{
				//不合法
				logger.debug("参数不合法");
			}
		} catch (Exception e) {
			result.put("success", "003");
			result.put("desc", "删除操作失败");
			e.printStackTrace();
		}
		result.put("desc", desc);
		return result;
	}

	private void delUser(String node_id, String parent_id, Map map) {
		//删除人员
		map.put("teamId", parent_id);
		map.put("zw_id", node_id);
		mainTainCompanyDao.updateTreeStaffStatus(map);
	}
}
