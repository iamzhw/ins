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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CheckProcessDao;
import com.cableCheck.dao.CheckRecordDao;
import com.cableCheck.dao.DispatchRelationDao;
import com.cableCheck.dao.TaskMangerDao;
import com.cableCheck.service.CheckRecordService;
import com.cableCheck.service.DispatchRelationService;
import com.cableCheck.service.TaskMangerService;
import com.linePatrol.util.DateUtil;
import com.system.constant.RoleNo;
import com.webservice.client.ElectronArchivesService;

@SuppressWarnings("all")
@Service
public class DispatchRelationServiceImpl implements DispatchRelationService {
	Logger logger = Logger.getLogger(DispatchRelationServiceImpl.class);
	
	@Resource
	private DispatchRelationDao dispatchRelationDao;
	
	/**
	 * 查询端子所在设备记录列表
	 */
	@Override
	public Map<String, Object> queryDispatchRelation(HttpServletRequest request, UIPage pager) {
		Map<String, Object> map = new HashMap<String, Object>();

		String AREA_ID = request.getParameter("AREA_ID");//地市
		
		String checkType = request.getParameter("check_type");//实施人类型
		String qx_area_id = request.getParameter("qx_area_id");  //区县
		String whwgId = request.getParameter("whwg_id");  //网格
		String checkName = request.getParameter("check_name");//检查人
		String companyId = request.getParameter("company_id"); //代维公司
		String teamId = request.getParameter("team_id");  //检查人班组权限
		
		map.put("AREA_ID", AREA_ID);
		map.put("checkType", checkType);
		map.put("qx_area_id", qx_area_id);
		map.put("whwgId", whwgId);
		map.put("checkName", checkName);
		map.put("companyId", companyId);
		map.put("teamId", teamId);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = dispatchRelationDao.queryDispatchRelation(query);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}
	
	@Override
	public Map<String, Object> getAreaList() {
		Map map = new HashMap();
		map.put("areaList", dispatchRelationDao.getAreaList());
		return map;
	}
	
	@Override
	public List<Map<String, Object>> exportPortsRecord(
			HttpServletRequest request) {
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String whwg = request.getParameter("whwg");
		String static_month = request.getParameter("static_month");//端子变动月份
		String dz_start_time = request.getParameter("dz_start_time");//端子动态开始时间
		String dz_end_time = request.getParameter("dz_end_time");//端子动态结束时间
		String check_start_time = request.getParameter("check_start_time");
		String check_end_time = request.getParameter("check_end_time");
		String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
		
		Map map =new HashMap();
		map.put("area", area);
		map.put("son_area", son_area);
		map.put("whwg", whwg);
		map.put("static_month", static_month);
		map.put("dz_start_time", dz_start_time);
		map.put("dz_end_time", dz_end_time);
		map.put("check_start_time", check_start_time);
		map.put("check_end_time", check_end_time);
		map.put("RES_TYPE_ID", RES_TYPE_ID);
		
		List<Map<String, Object>> exportInfoList = dispatchRelationDao.exportPortsRecord(map);
		
		return exportInfoList;
	}

	@Override
	public List<Map<String, String>> getGridListByAreaId(String AREA_ID) {
		return dispatchRelationDao.getGridListByAreaId(AREA_ID);
	}

	@Override
	public List<Map<String, Object>> getSonAreaListByAreaId(String AREA_ID) {
		return dispatchRelationDao.getSonAreaListByAreaId(AREA_ID);
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
		
		/*HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}*/

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = dispatchRelationDao.queryStaff(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		// pmap.put("total", list.size());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public Map<String, Object> saveDispatchRelation(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			
			String AREA_ID = request.getParameter("AREA_ID");
			String GRID_ID = request.getParameter("GRID_ID");
			String STAFF_ID = request.getParameter("STAFF_ID");
			String IS_ORDER = request.getParameter("IS_ORDER");
			String CHECK_TYPE = request.getParameter("CHECK_TYPE");
			
			//获取代维公司id和班组id
			String companyId = request.getParameter("company_id")==null?"":request.getParameter("company_id");
			String teamId = request.getParameter("team_id")==null?"":request.getParameter("team_id");
			
			//将传入后台的teamId根据逗号分隔，查询出相关teamName
			String[] str = teamId.split(",");
			List<String> list = Arrays.asList(str);
			Map map = new HashMap();
			map.put("list", list);
			String teamName = dispatchRelationDao.getTeamName(map);
			
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("AREA_ID", AREA_ID);
			param.put("GRID_ID", GRID_ID);
			param.put("STAFF_ID", STAFF_ID);
			param.put("IS_ORDER", IS_ORDER);
			param.put("CHECK_TYPE", CHECK_TYPE);
			
			param.put("companyId", companyId);
			param.put("teamId", teamId);
			param.put("teamName", teamName);
			
			if("1".equals(IS_ORDER)){
				List<Map<String,Object>> relationList = dispatchRelationDao.validateRelation(param);
//				List<Map<String,Object>> relationList = new ArrayList<Map<String,Object>>();
				/*if(relationList.size()>0){
					rs.put("resultCode", "001");
					rs.put("resultDesc", "该单位已有接单负责人!");
					return rs;
					if("2".equals(CHECK_TYPE)){
						rs.put("resultCode", "001");
						rs.put("resultDesc", "该网格已有接单负责人!");
						return rs;
					}else if("3".equals(CHECK_TYPE)){
					
						rs.put("resultCode", "001");
						rs.put("resultDesc", "该单位已有接单负责人!");
						return rs;
					}else{
						rs.put("resultCode", "001");
						rs.put("resultDesc", "派单关系增加失败!");
						return rs;
					}
				}*/
			}
			
			//添加计划
			dispatchRelationDao.saveDispatchRelation(param);
			//添加计划详情
			//cablePlanDao.insertPointPlanDetail(param);
		} catch (Exception e) {
			e.printStackTrace();
			rs.put("resultCode", "001");
			rs.put("resultDesc", "派单关系增加失败!");
			return rs;
		}
		rs.put("resultCode", "000");
		rs.put("resultDesc", "派单关系增加成功!");
		return rs;
	}

	@Override
	public void deleteRelation(HttpServletRequest request) {
		String selected = request.getParameter("selected_relation_ids");
		HttpSession session = request.getSession();
		Map map = new HashMap();
		String[] relations = selected.split(",");
		String relation_ids = "";
		for (int i = 0; i < relations.length; i++) {
			relation_ids += relations[i] + ",";
		}
		if (relation_ids.endsWith(",")) {
			relation_ids = relation_ids.substring(0, relation_ids.length() - 1);
		}
		map.put("RELATION_ID", relation_ids);
		dispatchRelationDao.deleteRelation(map);
	}
	
}
