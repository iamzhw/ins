package com.cableCheck.serviceimpl;

import icom.cableCheck.dao.CheckPortDao;
import icom.cableCheck.serviceimpl.CheckTaskServiceImpl;
import icom.system.dao.CableInterfaceDao;
import icom.util.Result;

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
import com.cableCheck.service.TeamUserService;
import com.linePatrol.util.DateUtil;
import com.system.model.ZTreeNode;
import com.webservice.client.ElectronArchivesService;

@SuppressWarnings("all")
@Service
public class TeamUserServiceImpl implements TeamUserService {
	Logger logger = Logger.getLogger(TeamUserServiceImpl.class);

	
	@Resource
	public TeamUserDao teamUserDao;
	
	private Query comParam(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String team_id = request.getParameter("team_id");
		String staff_name = request.getParameter("staff_name");
		map.put("team_id", team_id);
		map.put("staff_name", staff_name);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
	
	@Override
	public Map<String, Object> listUser(HttpServletRequest request, UIPage pager) {
		Query query = comParam(request, pager);
		List<Map<String,Object>> olists = teamUserDao.listTeamUser(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
}
