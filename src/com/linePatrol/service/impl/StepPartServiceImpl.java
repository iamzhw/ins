package com.linePatrol.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.PointManageDao;
import com.cableInspection.serviceimpl.PointManageServiceImpl;
import com.linePatrol.dao.StepPartDao;
import com.linePatrol.service.StepPartService;
import com.util.ExcelUtil;
import com.util.StringUtil;

@Service
public class StepPartServiceImpl implements StepPartService {

	@Resource
	private StepPartDao StepPartDao;

    @Resource
	private PointManageDao pointManageDao;

	@Override
	public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);

		List<Map<String, Object>> olists = StepPartDao.query(query);
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("rows",  olists);
		map.put("total", query.getPager().getRowcount());
		return map;
	}

	@Override
	public Map<String, Object> addStepPart(Map<String, Object> paramap) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> m = StepPartDao.selNameByCRID(paramap);
		resultMap.put("cable_name", m.get("CABLE_NAME"));
		resultMap.put("relay_name", m.get("RELAY_NAME"));
		resultMap.put("is_equip_list",JSONArray.fromObject(StepPartDao.stepPartEquip(paramap)));
		resultMap.put("relay_id", paramap.get("relay_id"));
		resultMap.put("cable_id", paramap.get("cable_id"));
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getGldByAreaId(String area_id) {
		return StepPartDao.getGldByAreaId(area_id);
	}

	@Override
	public List<Map<String, Object>> getRelayByCableId(Map<String, Object> map) {
		return StepPartDao.getRelayByCableId(map);
	}

	@Override
	public List<Map<String, Object>> selOnlyStepPartName(String steppart_name) {
		return StepPartDao.selOnlyStepPartName(steppart_name);
	}

	@Override
	public List<Map<String, Object>> judgeIsTaskEquip(Map<String, Object> paramap) {
		return StepPartDao.judgeIsTaskEquip(paramap);
	}

	@Override
	public String judgeCircle(Map<String, Object> paramap) {
		return StepPartDao.judgeCircle(paramap);
	}

	@Override
	public String selOrderByEquipID(String equip_Id) {
		return StepPartDao.selOrderByEquipID(equip_Id);
	}

	@Override
	public void insertEquipAllot(Map<String, Object> map) {
		StepPartDao.insertEquipAllot(map);
	}

	@Override
	public void upIsPartByMap(Map<String, Object> map) {
		StepPartDao.upIsPartByMap(map);
	}



	@Override
	public String judgeCircleByAllotID(Map<String, Object> map) {
		return StepPartDao.judgeCircleByAllotID(map);
	}

	@Override
	public Map<String, Object> upSelEquip(Map<String, Object> map) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String allot_id = map.get("allot_id").toString();
		Map<String, Object> m=StepPartDao.selCRByAllotID(allot_id);
		resultMap.put("cable_name", m.get("CABLE_NAME"));
		resultMap.put("relay_name", m.get("RELAY_NAME"));
		resultMap.put("relay_id", m.get("RELAY_ID"));
		resultMap.put("cable_id", m.get("CABLE_ID"));
		resultMap.put("allot_id", m.get("ALLOT_ID"));
		resultMap.put("start_equip", m.get("START_EQUIP"));
		resultMap.put("end_equip", m.get("END_EQUIP"));
		resultMap.put("steppart_name", m.get("STEPPART_NAME"));
		resultMap.put("circle_id", m.get("CIRCLE_ID"));
		resultMap.put("staff_name", m.get("STAFF_NAME"));
		resultMap.put("staff_id", m.get("STAFF_ID"));
		resultMap.put("old_inspect_id", m.get("INSPECT_ID"));
		resultMap.put("is_equip_list",JSONArray.fromObject(StepPartDao.upSelEquip(map)));
		return resultMap;
	}

	@Override
	public void upIsPartByAllotID(Map<String, Object> map) {
		StepPartDao.upIsPartByAllotID(map);
	}

	@Override
	public void upStepAllotByAllotID(Map<String, Object> map) {
		StepPartDao.upStepAllotByAllotID(map);
	}

	@Override
	public void delStepPart(String allot_id) {
		StepPartDao.delStepPart(allot_id);
	}

	@Override
	public Map<String, Object> selNameByCRID(Map<String, Object> map) {
		return StepPartDao.selNameByCRID(map);
	}

	@Override
	public Map<String, Object> selCRByAllotID(String allot_id) {
		return StepPartDao.selCRByAllotID(allot_id);
	}

	@Override
	public void intoHisDelAllot(Map<String, Object> map) {
		StepPartDao.intoHisDelAllot(map);
	}

	@Override
	public void delHisDelAllot(Map<String, Object> map) {
	    StepPartDao.delHisDelAllot(map);
	}

	@Override
	public Map<String, Object> queryStepEquipList(Map<String, Object> para,
			UIPage pager) {
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);

		List<Map<String, Object>> olists = StepPartDao.queryStepEquipList(para);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Object importDo(HttpServletRequest request, MultipartFile file) {
		JSONObject result = new JSONObject();
		String repeatRows = "";
		String errorRows = "";
		try {
			String startEquipId  = request.getParameter("startEquipId");
			String endEquipId  = request.getParameter("endEquipId");
			int cableId = Integer.parseInt(request.getParameter("cableId"));
			int relayId = Integer.parseInt(request.getParameter("relayId"));
			
			Map<String,Object> sParam = new HashMap<String,Object>();
			sParam.put("cable_id", cableId);
			sParam.put("relay_id", relayId);
			List<Map<String, Object>> oldSiteList = StepPartDao.queryStepEquipList(sParam);

			ExcelUtil parse = new ExcelUtil(file.getInputStream());
			List<List<String>> datas = parse.getDecimalDatas(14);
			Map<String, Object> params = null;
			List<String> data = null;
			List<Integer> addSiteIds = new ArrayList<Integer>();
			for (int i = 0, j = datas.size(); i < j; i++) {
				//0:经度;1:纬度;2:是否为GPS坐标;3:设施编号;4:区域;5:设施位置;6:设施描述;7:设施类型;8:户主姓名;9:户主电话;
				//10:义务护线员姓名;11:义务护线员电话;12:是否路由;13:深度
				params = new HashMap<String, Object>();
				params.put("cable_id", cableId);
				params.put("relay_id", relayId);
				data = datas.get(i);
				// 验证是否为空数据
				if (StringUtil.isEmpty(data.get(0))) {
					continue;
				}
				// 验证是否位GPS坐标
				if (StringUtil.isEmpty(data.get(2)) || !("是".equals(data.get(2)) || "否".equals(data.get(2)))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				// 验证经度
				if (StringUtil.isEmpty(data.get(0)) || !NumberUtils.isNumber(data.get(0))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				// 验证纬度
				if (StringUtil.isEmpty(data.get(1)) || !NumberUtils.isNumber(data.get(1))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				// 验证区域
				String area_id = getAreaId(data.get(4));
				if (StringUtil.isEmpty(data.get(4)) || StringUtil.isEmpty(area_id)) {
					errorRows += "," + (i + 2);
					continue;
				}
				// 验证设施类型
				String equip_type = getEquipType(data.get(7));
				if (StringUtil.isEmpty(data.get(7)) || StringUtil.isEmpty(equip_type)) {
					errorRows += "," + (i + 2);
					continue;
				}
				// 验证是否路由
				if (StringUtil.isEmpty(data.get(12)) || !("是".equals(data.get(12)) || "否".equals(data.get(12)))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				
				//验证深度
				if(StringUtil.isEmpty(data.get(13))){
				}else if(!NumberUtils.isNumber(data.get(13))){
					errorRows += "," + (i + 2);
					continue;
				}else{
					params.put("depth", Double.parseDouble(data.get(13)));
				}
				
				params.put("longitude", Double.parseDouble(data.get(0)));
				params.put("latitude", Double.parseDouble(data.get(1)));
				if("是".equals(data.get(2))){
					params.put("coordinate_type", 2);
					params.put("longitude_gps", Double.parseDouble(data.get(0)));
					params.put("latitude_gps", Double.parseDouble(data.get(1)));
				}
				params.put("equip_code", data.get(3));
				List<Map<String, Object>> siteList = StepPartDao.queryStepEquipList(params);
				if(siteList.size() > 0){
					repeatRows += "," + (i + 2);
					continue;
				}
				params.put("area_id", Integer.parseInt(area_id));
				params.put("equip_address", data.get(5));
				params.put("description", data.get(6));
				params.put("equip_type", Integer.parseInt(equip_type));
				params.put("owner_name", data.get(8));
				params.put("owner_tel", data.get(9));
				params.put("protecter", data.get(10));
				params.put("protect_tel", data.get(11));
				params.put("is_equip", "是".equals(data.get(12)) ? 1 : 0);
				
				String staffId = request.getSession().getAttribute("staffId").toString();
				params.put("creator", staffId);
				params.put("updator", staffId);
				
				int equip_id = StepPartDao.getStepEquipSeq();
				params.put("equip_id", equip_id);
				// 保存点
				StepPartDao.addStepEquip(params);
				
				//保存步巡段和设施点关联表
				params.put("order", 999);
				StepPartDao.insertEquipOrder(params);
				
				addSiteIds.add(equip_id);
			}
			
			//组装
			List<Integer> siteIds = new ArrayList<Integer>();
			for (Map<String, Object> map : oldSiteList) {
				siteIds.add(Integer.parseInt(map.get("EQUIP_ID").toString()));
			}
			if(StringUtil.isEmpty(startEquipId)){					//只选了第一个
				siteIds.addAll(0, addSiteIds);
			}else{
				siteIds.addAll(siteIds.indexOf(Integer.parseInt(startEquipId))+1, addSiteIds);
			}
			//排序
			Map<String,Object> orderMap = null;
			for(int i=0; i<siteIds.size(); i++){
				orderMap = new HashMap<String,Object>();
				orderMap.put("equip_id", siteIds.get(i));
				orderMap.put("cable_id", cableId);
				orderMap.put("relay_id", relayId);
				orderMap.put("order", i);
				StepPartDao.updateEquipOrder(orderMap);
			}
			
			//修改步巡段起始结束设施点
			Map<String,Object> partParam = new HashMap<String,Object>();
			partParam.put("cable_id", cableId);
			partParam.put("relay_id", relayId);
			if(StringUtil.isEmpty(startEquipId)){					//只选了第一个
				partParam.put("start_equip", siteIds.get(0));
				StepPartDao.updateStepPart(partParam);
			}else if(StringUtil.isEmpty(endEquipId)){				//只选了最后一个
				partParam.put("end_equip", siteIds.get(siteIds.size()-1));
				StepPartDao.updateStepPart(partParam);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", false);
			result.put("message", "文件格式错误！");
			return result;
		}
		if (!"".equals(repeatRows) || !"".equals(errorRows)) {
			result.put("status", false);
			String message = null;
			if (!"".equals(repeatRows)) {
				message = "编号 " + repeatRows.substring(1) + " 的记录重复：经纬度已存在！";
			}
			if (!"".equals(errorRows)) {
				String str = "编号 " + errorRows.substring(1) + " 的记录数据有误：数据为空或数据不符合规范！";
				if (message == null) {
					message = str;
				} else {
					message += str;
				}
			}
			result.put("message", message);
		} else {
			result.put("status", true);
		}
		return result;
	
	}
	//查询区域
	private String getAreaId(String area_name) {
		if (PointManageServiceImpl.AREA.containsKey(area_name)) {
			return PointManageServiceImpl.AREA.get(area_name);
		}
		List<Map<String, String>> result = pointManageDao.getAreaNameById(area_name);
		if (result == null || result.get(0) == null) {
			return null;
		} else {
			String area_id = result.get(0).get("AREA_ID");
			PointManageServiceImpl.AREA.put(area_name, area_id);
			return area_id;
		}
	}
	//查询设施类型
	private String getEquipType(String equipName){
		Map equipTypeParam = new HashMap();
		equipTypeParam.put("equip_type_name", equipName);
		List<Map<String, Object>> equipTypeList = StepPartDao.queryEquipType(equipTypeParam);
		if(null != equipTypeList && equipTypeList.size() > 0){
			return String.valueOf(equipTypeList.get(0).get("EQUIP_TYPE_ID"));
		}
		return null;
	}
}
