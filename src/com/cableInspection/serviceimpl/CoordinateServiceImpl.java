package com.cableInspection.serviceimpl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.CableDao;
import com.cableInspection.dao.CoordinateDao;
import com.cableInspection.dao.PointManageDao;
import com.cableInspection.service.CoordinateService;
import com.system.constant.RoleNo;
import com.system.dao.RoleDao;
import com.util.StringUtil;

@Service
@Transactional(rollbackFor = { Exception.class})
public class CoordinateServiceImpl implements CoordinateService {
	
	@Resource
	private CoordinateDao coordinateDao;
	
	@Resource
	private CableDao cableDao;
	
	@Resource
	private PointManageDao pointManageDao;

	@Resource
	private RoleDao roleDao;
	@Override
	public Map<String, Object> index(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		//获取所有设备类型
		rs.put("equipTypes", pointManageDao.getEquipTypes());
		//获取区域
		HttpSession session = request.getSession();
		rs.put("areaId", session.getAttribute("areaId"));
		rs.put("sonAreaId", session.getAttribute("sonAreaId"));
		String staffId = request.getSession().getAttribute("staffId").toString();//当前用户
		//获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		String roleNo = null;
		for(Map<String, String> map : roleList){
			roleNo = map.get("ROLE_NO");
			if(RoleNo.GLY.equals(roleNo) 
					|| RoleNo.LXXJ_ADMIN.equals(roleNo) 
					|| RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)){
				rs.put("admin", true);
				rs.put("isSonAreaAdmin", false);
				break;
			}
			if (RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
				rs.put("isSonAreaAdmin", true);
			}
		}
		return rs;
	}

	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		String equip_code = request.getParameter("equip_code");
		String equip_name = request.getParameter("equip_name");
		String equip_type = request.getParameter("equip_type");
		String create_time = request.getParameter("create_time");
		String type = request.getParameter("type");
		//管理员查询条件
		String son_area = request.getParameter("son_area");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQUIP_CODE", equip_code);
		map.put("EQUIP_NAME", equip_name);
		map.put("EQUIP_TYPE", equip_type);
		map.put("type", type);
		if(create_time != null && ! "".equals(create_time)){
			map.put("START_TIME", create_time + " 00:00:00");
			map.put("END_TIME", create_time + " 23:59:59");
		}
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {//省级管理员
			//判断查询条件是否有查询子区域
			if(!StringUtil.isEmpty(son_area))
			{
				map.put("SON_AREA_ID", son_area);
			}
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {//是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				//判断查询条件是否有查询子区域
				if(!StringUtil.isEmpty(son_area))
				{
					map.put("SON_AREA_ID", son_area);
				}
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {//是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} 
				else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {//如果是审核员只能查到本区域的
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} 
				else {
					map.put("AREA_ID", -1);
				}
			}
		}
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map<String, Object>> olists = coordinateDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Map<String, Object> getDetail(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String recordIds = request.getParameter("recordIds");
		if(StringUtils.isBlank(recordIds)){
			return rs;
		}
		//获取所有设备类型
		rs.put("equipTypes", pointManageDao.getEquipTypes());
		//维护等级
		rs.put("mntLevel", pointManageDao.getMntLevel());
		List<Map<String, Object>> detailList = coordinateDao.getDetail(recordIds);
		//获取关联的图片信息
		for(Map<String, Object> detail : detailList){
			detail.put("photos", 
					coordinateDao.getPhotoListByRecordId(detail.get("RECORD_ID").toString()));
		}
		//获得历史档案
		List<Map<String, Object>> checkPointRecordList = new ArrayList<Map<String, Object>>();
		checkPointRecordList = coordinateDao.checkPointRecordList(recordIds);
		//获取关联的图片信息
		if(checkPointRecordList.get(0)!=null){
			for(Map<String, Object> checkPointRecord : checkPointRecordList)
			{
				if(null!=checkPointRecord.get("RECORD_ID") && !"".equals(checkPointRecord.get("RECORD_ID").toString()))
				{
					checkPointRecord.put("checkPointRecordPhotos", coordinateDao.getPhotoListByRecordId(checkPointRecord.get("RECORD_ID").toString()));
				}
			}
		}
		rs.put("detailList", detailList);
		rs.put("checkPointRecordList", checkPointRecordList);
		rs.put("recordIds", recordIds);
		rs.put("areaName", 
				cableDao.queryAreaName(request.getSession().getAttribute("areaId").toString()));
		return rs;
	}

	@Override
	public String update(HttpServletRequest request) {
		try {
			String recordIds = request.getParameter("recordIds");
			String equip_codes = request.getParameter("equip_codes");
			String equip_names = request.getParameter("equip_names");
			equip_names=URLDecoder.decode(equip_names, "utf-8");
			String equip_types = request.getParameter("equip_types");
			String mnt_types = request.getParameter("mnt_types");
			if(StringUtils.isBlank(recordIds)) {
				return "";
			}
			String equip_codesArr[] = equip_codes.split(",");
			String equip_namesArr[] = equip_names.split(",");
			String equip_typesArr[] = equip_types.split(",");
			String mnt_typesArr[] = mnt_types.split(",");
			Map<String, Object> recordInfo = null;
			Map<String, Object> oldRecordInfo = null;
			//修改编码之前的关键点信息 oldPoints
			List<Map<String, Object>> oldPoints = null;
			//修改编码之后的关键点信息 commitPoints
			List<Map<String, Object>> commitPoints = null;
			//历史采集坐标MAP
			List<Map<String, Object>> recordInfoList = null;
			Map<String, Object> params = null;
			String equip_no = null;
			String old_equip_no = null;
			String equip_name = null;
			//删除历史采集坐标MAP
			Map<String, Object> deleteMap = new HashMap<String, Object>();
			//更改审核标志MAP
			Map<String, Object> commitMap = new HashMap<String, Object>();
			String staffId = request.getSession().getAttribute("staffId").toString();//当前用户
			commitMap.put("COMMIT_STAFF_ID", staffId);//操作人
			
			boolean updateAfterIsExistFlag = false;
			boolean updateBoforeIsExistFlag = false;
			String recordIdsArr[] = recordIds.split(",");
			for(int i=0 ; i < recordIdsArr.length; i++)
			//for(String recordId : recordIds.split(",")) 
			{

				updateAfterIsExistFlag = false;
				updateBoforeIsExistFlag = false;
				String recordId = recordIdsArr[i];
				params = new HashMap<String, Object>();
				params.put("RECORD_ID", recordId);
				String mnt_type = mnt_typesArr[i]==""?"0":mnt_typesArr[i];
				commitMap.put("recordIds", recordId);
				commitMap.put("EQUIP_CODE", URLDecoder.decode(equip_codesArr[i], "UTF-8"));
				commitMap.put("EQUIP_NAME", URLDecoder.decode(equip_namesArr[i], "UTF-8"));
				commitMap.put("EQUIP_TYPE", equip_typesArr[i]);
				commitMap.put("MNT_LEVEL_ID", mnt_type);
				//根据记录ID获取老的设备编码
				oldRecordInfo = coordinateDao.getDetail(recordId).get(0);
				old_equip_no = oldRecordInfo.get("EQUIP_CODE").toString();
				params.put("OLD_EQUIP_NO", old_equip_no);
				//更改审核标志及修改的信息
				coordinateDao.commitRecord(commitMap);
				//获取对应的新record信息
				recordInfo = coordinateDao.getDetail(recordId).get(0);
				params.put("SON_AREA_ID", recordInfo.get("SON_AREA_ID"));
				params.put("AREA_ID", recordInfo.get("AREA_ID"));
				params.put("LONGITUDE", recordInfo.get("LONGITUDE"));
				params.put("LATITUDE", recordInfo.get("LATITUDE"));
				equip_no = recordInfo.get("EQUIP_CODE").toString();
				
				params.put("EQUIP_CODE", equip_no);
				equip_name = StringUtil.objectToString(recordInfo.get("EQUIP_NAME"));
				if("".equals(equip_name)){
					equip_name = equip_no;
				}
				//根据修改设备编码之前查询TB_ins_point（关键点）表中对应的设备
				oldPoints = coordinateDao.queryPointByEquipCode(params);
				//根据修改设备编码之后查询TB_ins_point（关键点）表中对应的设备
				params.put("OLD_EQUIP_NO", equip_no);
				commitPoints = coordinateDao.queryPointByEquipCode(params);
				
				//如果修改之后的编码已存在关键点中
				if(commitPoints != null && commitPoints.size() >0)
				{
					updateAfterIsExistFlag = true;
				}
				//如果修改之前的编码已存在关键点中
				if(oldPoints != null && oldPoints.size() >0)
				{
					updateBoforeIsExistFlag = true;
				}
				//如果修改之后的编码与修改之前的编码不一致
				if(!old_equip_no.equals(equip_no))
				{
					if(!updateAfterIsExistFlag&&!updateBoforeIsExistFlag)
					{//关键点表中不存在则新增关键点
						if ("".equals(StringUtil.objectToString(params.get("LONGITUDE")))
								|| "".equals(StringUtil.objectToString(params.get("LATITUDE")))) {
							continue;
						}
						params.put("POINT_NO", equip_no);
						params.put("POINT_NAME", equip_name);
						params.put("POINT_TYPE", 4);
						params.put("POINT_LEVEL", mnt_type);
						params.put("EQP_TYPE_ID", recordInfo.get("EQUIP_TYPE"));
						params.put("CREATE_STAFF", recordInfo.get("INSPECTOR"));
						params.put("ADDRESS", "");
						pointManageDao.insertPoint(params);
					} 
					else if(updateAfterIsExistFlag)
					{
						//更新点坐标
						params.put("POINT_ID", commitPoints.get(0).get("POINT_ID"));
						params.put("POINT_NO", equip_no);
						params.put("POINT_NAME", equip_name);
						params.put("POINT_LEVEL", mnt_type);
						params.put("EQP_TYPE_ID", recordInfo.get("EQUIP_TYPE"));
						params.put("CREATE_STAFF", recordInfo.get("INSPECTOR"));
						coordinateDao.updatePointCoordinate(params);
						
						//删除关键点中修改之前的编码
						if(updateBoforeIsExistFlag){
							pointManageDao.delete(oldPoints.get(0).get("POINT_ID").toString());
						}
					}
					else if(updateBoforeIsExistFlag)
					{
						//更新点坐标
						params.put("POINT_ID", oldPoints.get(0).get("POINT_ID"));
						params.put("POINT_NO", equip_no);
						params.put("POINT_NAME", equip_name);
						params.put("POINT_LEVEL", mnt_type);
						params.put("EQP_TYPE_ID", recordInfo.get("EQUIP_TYPE"));
						params.put("CREATE_STAFF", recordInfo.get("INSPECTOR"));
						coordinateDao.updatePointCoordinate(params);
						
					}
					//直接删除坐标采集中所有修改之前编码的信息
					deleteMap.put("point_no", old_equip_no);
					pointManageDao.deleteRecordByEqpNo(deleteMap);
					recordInfoList = coordinateDao.queryRecordByEquipCode(params);
					for (int j = 0; j < recordInfoList.size(); j++) {
						//如果所要审核的记录ID不等于历史坐标ID
						String RECORD_ID = recordInfoList.get(j).get("RECORD_ID").toString();
						if(!recordId.equals(RECORD_ID))
						{
							//软删除此编码的历史采集坐标，只保留最新坐标
							deleteMap.put("recordIds", RECORD_ID);
							coordinateDao.deleteRecord(deleteMap);
						}
					}
				}
				else
				{
					if(!updateAfterIsExistFlag)
					{//关键点表中不存在则新增关键点
						if ("".equals(StringUtil.objectToString(params.get("LONGITUDE")))
								|| "".equals(StringUtil.objectToString(params.get("LATITUDE")))) {
							continue;
						}
						params.put("POINT_NO", equip_no);
						params.put("POINT_NAME", equip_name);
						params.put("POINT_TYPE", 4);
						params.put("EQP_TYPE_ID", recordInfo.get("EQUIP_TYPE"));
						params.put("POINT_LEVEL", mnt_type);
						params.put("CREATE_STAFF", recordInfo.get("INSPECTOR"));
						params.put("SON_AREA_ID", recordInfo.get("SON_AREA_ID"));
						params.put("ADDRESS", "");
						params.put("AREA_ID", recordInfo.get("AREA_ID"));
						pointManageDao.insertPoint(params);
					}
					else
					{
						//更新点坐标
						params.put("POINT_ID", commitPoints.get(0).get("POINT_ID"));
						params.put("POINT_NO", equip_no);
						params.put("POINT_NAME", equip_name);
						params.put("POINT_LEVEL", mnt_type);
						params.put("EQP_TYPE_ID", recordInfo.get("EQUIP_TYPE"));
						params.put("CREATE_STAFF", recordInfo.get("INSPECTOR"));
						coordinateDao.updatePointCoordinate(params);
					}
					//根据修改之后的设备编码采集点编码获取此编码的历史上传列表
					recordInfoList = coordinateDao.queryRecordByEquipCode(params);
					for (int j = 0; j < recordInfoList.size(); j++) {
						//如果所要审核的记录ID不等于历史坐标ID
						String RECORD_ID = recordInfoList.get(j).get("RECORD_ID").toString();
						if(!recordId.equals(RECORD_ID))
						{
							//软删除此编码的历史采集坐标，只保留最新坐标
							deleteMap.put("recordIds", RECORD_ID);
							coordinateDao.deleteRecord(deleteMap);
						}
					}
				}
			}
			
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public String getPoints(HttpServletRequest request) {
		String recordIds = request.getParameter("recordIds");
		List<Map<String, Object>> datas = coordinateDao.getDetail(recordIds);
		return JSONArray.fromObject(datas).toString();
	}

	@Override
	public void deletePoints(HttpServletRequest request) {
		String recordIds = request.getParameter("recordIds");
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("recordIds", recordIds);
		Map<String, Object> recordInfo = null;
		Map<String, Object> params = new HashMap<String, Object>() ;
		List<Map<String, Object>> points = null;
		String recordIdArr[] = recordIds.split(",");
		for (int i = 0; i < recordIdArr.length; i++) {
			recordInfo = coordinateDao.getDetail(recordIdArr[i]).get(0);
			//String EQUIP_CODE = (String) recordInfo.get("EQUIP_CODE");
			params.put("OLD_EQUIP_NO", (String) recordInfo.get("EQUIP_CODE"));
			//根据老的设备编码查询对应的设备
			points = coordinateDao.queryPointByEquipCode(params);
			for (int j = 0; j < points.size(); j++) {
				pointManageDao.delete(points.get(j).get("POINT_ID").toString());
				
				//要删除的关键点ID
				String ids = points.get(j).get("POINT_ID").toString();
				//删除标识，在发现有看护点关联计划的时候传入
				String delFlag = request.getParameter("delFlag");
				if(delFlag != null && !"".equals(delFlag)){
					//删除关键点
					pointManageDao.delete(ids);
					//根基关键点ID，查询计划明细中的计划ID
					List<Map<String, String>> list = pointManageDao.getPlanIdByPointId(ids);
					//根据关键点ID，删除计划明细
					pointManageDao.deletePlanDetailByPointId(ids);
					//根据关键点ID，删除任务明细
					pointManageDao.deleteTaskDetailByPointId(ids);
					//遍历计划ID集合
					for(Map map:list){
						String planid = map.get("PLAN_ID").toString();
						String pointType = map.get("POINT_TYPE").toString();
						//判断是否为看护点
						if("3".equals(pointType)){
							//根据计划ID，删除计划
							pointManageDao.deletePlanById(planid);
							//根据计划ID，删除任务
							pointManageDao.deleteTaskByPlanId(planid);
						}
					}
				}else{
					//根据关键点ID，查询是否有已生成计划的看护点
					List<Map<String, String>> list = pointManageDao.hasPlan(ids);
					//如果有，则返回关键点名称，前台提示；如果没有，则直接删除这些关键点
					if(list.size() > 0){
						String msg = "";
						for(int k=0;k<list.size();k++){
							Map map = list.get(k);
							msg += "["+map.get("POINT_NAME").toString()+"]";
							if(j!=list.size()-1){
								msg +="、";
							}
						}
					}else{
						//删除关键点
						pointManageDao.delete(ids);
					}
				}
			}
		}
		coordinateDao.deleteRecord(deleteMap);
	}
	@Override
	public List<Map<String, Object>>  exportPointsList(HttpServletRequest request) {
		String equip_code = request.getParameter("equip_code");
		String equip_name = request.getParameter("equip_name");
		String equip_type = request.getParameter("equip_type");
		String create_time = request.getParameter("create_time");
		String type = request.getParameter("type");
		//管理员查询条件
		String son_area = request.getParameter("son_area");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQUIP_CODE", equip_code);
		map.put("EQUIP_NAME", equip_name);
		map.put("EQUIP_TYPE", equip_type);
		map.put("type", type);
		map.put("son_area", son_area);
		if(create_time != null && ! "".equals(create_time)){
			map.put("START_TIME", create_time + " 00:00:00");
			map.put("END_TIME", create_time + " 23:59:59");
		}
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {//省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {//是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {//是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} 
				else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {//如果是审核员只能查到本区域的
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} 
				else {
					map.put("AREA_ID", -1);
				}
			}
		}
		List<Map<String, Object>> olists = coordinateDao.exportPointsList(map);
		return olists;
	}

	@Override
	public Map<String, Object> querySumOfCood(HttpServletRequest request,
			UIPage pager) {
		String area_id=request.getParameter("area_id")==""?"2":request.getParameter("area_id");
		String area_type="";
		String date = request.getParameter("begin_time");
		String check = request.getParameter("check");
		if(area_id.equals("2")){
			area_type="area_id";
		}else{
			area_type="son_area_id";
		}
		Map param = new HashMap();
		param.put("area_id", area_id);
		param.put("area_type", area_type);
		param.put("date", date);
		param.put("check", check);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(param);

		List<Map<String, Object>> olists = coordinateDao.querySumOfCood(param);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public List<Map<String, Object>> exportSumOfCood(HttpServletRequest request) {
		String area_id=request.getParameter("son_area")==""?"2":request.getParameter("son_area");
		String area_type="";
		String date = request.getParameter("begin_time");
		String check = request.getParameter("check")==null?"0":"1";
		if(area_id.equals("2")){
			area_type="area_id";
		}else{
			area_type="son_area_id";
		}
		Map param = new HashMap();
		param.put("area_id", area_id);
		param.put("area_type", area_type);
		param.put("date", date);
		param.put("check", check);
		List<Map<String, Object>> olists = coordinateDao.querySumOfCood(param);
//		List<Map<String, Object>> olists = new ArrayList();
		return olists;
	}
}
