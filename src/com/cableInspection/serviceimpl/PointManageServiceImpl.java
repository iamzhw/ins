package com.cableInspection.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.PointManageDao;
import com.cableInspection.service.CableService;
import com.cableInspection.service.PointManageService;
import com.system.constant.RoleNo;
import com.util.ExcelUtil;
import com.util.StringUtil;

@SuppressWarnings("all")
@Service
@Transactional(rollbackFor = { Exception.class })
public class PointManageServiceImpl implements PointManageService {

	@Resource
	private PointManageDao pointManageDao;
	
	@Resource
	private CableService cableService;

	public static Map<String, Integer> POINT_TYPE = new HashMap<String, Integer>();

	public static Map<String, Integer> EQUIP_TYPE = new HashMap<String, Integer>();

	public static Map<String, String> AREA = new HashMap<String, String>();

	@Override
	public Map<String, Object> index(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("pointTypeList", pointManageDao.getPointTypes());
		rs.put("isAreaAdmin",
				(Boolean) request.getSession().getAttribute(
						RoleNo.LXXJ_ADMIN_AREA));
		return rs;
	}

	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		String point_no = request.getParameter("point_no");
		String point_name = request.getParameter("point_name");
		String address = request.getParameter("address");
		String point_type = request.getParameter("point_type");
		String point_level = request.getParameter("point_level");
		String dept_name = request.getParameter("dept_name");
		// 管理员查询条件
		String son_area = request.getParameter("son_area_id");
		Map map = new HashMap();
		map.put("POINT_NO", point_no);
		map.put("POINT_NAME", point_name);
		map.put("ADDRESS", address);
		map.put("POINT_TYPE", point_type);
		map.put("POINT_LEVEL", point_level);
		map.put("DEPT_NAME", dept_name);
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			// 判断查询条件是否有查询子区域
			if (!StringUtil.isEmpty(son_area)) {
				map.put("SON_AREA_ID", son_area);
			}
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				// 判断查询条件是否有查询子区域
				if (!StringUtil.isEmpty(son_area)) {
					map.put("SON_AREA_ID", son_area);
				}
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = pointManageDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Map<String, Object> add(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("pointTypeList", pointManageDao.getPointTypes());// 点类型集合
		rs.put("equipTypeList", pointManageDao.getEquipTypes());// 设备类型集合
		rs.put("mntLevel", pointManageDao.getMntLevel());
		int areaId = Integer.parseInt(request.getSession().getAttribute(
				"areaId").toString());
		// 获取对应的区县
		List<Map<String, Object>> sonAreaList = pointManageDao
				.getSonAreas(areaId);
		rs.put("area", sonAreaList.get(0).get("NAME"));
		rs.put("sonAreaList", sonAreaList);// 当前用户所在区县集合
		return rs;
	}

	@Override
	public JSONObject save(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String point_type = request.getParameter("point_type");
		String son_area = request.getParameter("son_area");
		String point_no = request.getParameter("point_no");
		String point_name = request.getParameter("point_name");
		String equip_type = request.getParameter("equip_type");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String address = request.getParameter("address");
		String point_level = request.getParameter("point_level");
		String POINT_LEVEL = request.getParameter("mnt_type");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("POINT_NO", point_no);
		params.put("POINT_NAME", point_name);
		params.put("POINT_TYPE", point_type);
		params.put("EQP_TYPE_ID", equip_type);
		params.put("LONGITUDE", longitude);
		params.put("LATITUDE", latitude);
		params.put("ADDRESS", address);
		params.put("POINT_LEVEL", point_level);
		params.put("SON_AREA_ID", son_area);
		params.put("POINT_LEVEL", POINT_LEVEL);
		params.put("AREA_ID", request.getSession().getAttribute("areaId"));
		params
				.put("CREATE_STAFF", request.getSession().getAttribute(
						"staffId"));
		// 验证编码是否重复
		int rows = pointManageDao.isRepeat(params);
		if (rows > 0) {
			result.put("status", false);
			result.put("message", "已存在相同编码的点");
		} else {
			pointManageDao.insertPoint(params);
			result.put("status", true);
		}
		return result;
	}

	@Override
	public JSONObject delete(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		// 要删除的关键点ID
		String ids = request.getParameter("selected");
		// 先删除坐标采集的点
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pointId", ids);
		List<Map<String, Object>> pointNoList = pointManageDao
				.getPointNoByPointId(params);
		for (int i = 0; i < pointNoList.size(); i++) {
			params.put("point_no", pointNoList.get(i).get("POINT_NO"));
			pointManageDao.deleteRecordByEqpNo(params);
		}
		// 删除标识，在发现有看护点关联计划的时候传入
		String delFlag = request.getParameter("delFlag");
		if (delFlag != null && !"".equals(delFlag)) {
			// 删除关键点
			pointManageDao.delete(ids);
			// 根基关键点ID，查询计划明细中的计划ID
			List<Map<String, String>> list = pointManageDao
					.getPlanIdByPointId(ids);
			// 根据关键点ID，删除计划明细
			pointManageDao.deletePlanDetailByPointId(ids);
			// 根据关键点ID，删除任务明细
			pointManageDao.deleteTaskDetailByPointId(ids);
			// 遍历计划ID集合
			for (Map map : list) {
				String planid = map.get("PLAN_ID").toString();
				String pointType = map.get("POINT_TYPE").toString();
				// 判断是否为看护点
				if ("3".equals(pointType)) {
					// 根据计划ID，删除计划
					pointManageDao.deletePlanById(planid);
					// 根据计划ID，删除任务
					pointManageDao.deleteTaskByPlanId(planid);
				}
			}
			result.put("status", true);
		} else {
			// 根据关键点ID，查询是否有已生成计划的看护点
			List<Map<String, String>> list = pointManageDao.hasPlan(ids);
			// 如果有，则返回关键点名称，前台提示；如果没有，则直接删除这些关键点
			if (list.size() > 0) {
				String msg = "";
				for (int i = 0; i < list.size(); i++) {
					Map map = list.get(i);
					msg += "[" + map.get("POINT_NAME").toString() + "]";
					if (i != list.size() - 1) {
						msg += "、";
					}
				}
				result.put("status", false);
				result.put("message", msg);
			} else {
				// 删除关键点
				pointManageDao.delete(ids);
				result.put("status", true);
			}
		}
		return result;
	}

	@Override
	public JSONObject importDo(HttpServletRequest request, MultipartFile file) {
		JSONObject result = new JSONObject();
		String repeatRows = "";
		String errorRows = "";
		try {

			// 获取所有点类型
			List<Map<String, Object>> list = pointManageDao.getPointTypes();
			for (Map<String, Object> map : list) {
				POINT_TYPE.put(map.get("POINT_TYPE_NAME").toString(), Integer
						.parseInt(map.get("POINT_TYPE_ID").toString()));
			}
			// 获取所有设备类型
			List<Map<String, Object>> list1 = pointManageDao.getEquipTypes();
			for (Map<String, Object> map : list1) {
				EQUIP_TYPE.put(map.get("EQUIPMENT_TYPE_NAME").toString(),
						Integer.parseInt(map.get("EQUIPMENT_TYPE_ID")
								.toString()));
			}

			String staffId = request.getSession().getAttribute("staffId")
					.toString();
			ExcelUtil parse = new ExcelUtil(file.getInputStream());
			List<List<String>> datas = parse.getDatas(9);
			Map<String, Object> params = null;
			List<String> data = null;
			Integer point_type;
			Integer equip_type;
			for (int i = 0, j = datas.size(); i < j; i++) {
				params = new HashMap<String, Object>();
				data = datas.get(i);
				// 验证编码
				params.put("POINT_NO", data.get(0));
				if (StringUtil.isEmpty(data.get(0))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					int rows = pointManageDao.isRepeat(params);
					if (rows > 0) {
						repeatRows += "," + (i + 2);
						continue;
					}
				}
				// 验证名称
				if (StringUtil.isEmpty(data.get(1))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					params.put("POINT_NAME", data.get(2));
				}
				// 验证点类型
				if (StringUtil.isEmpty(data.get(2))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					point_type = POINT_TYPE.get(data.get(2));
					if (point_type == null) {
						errorRows += "," + (i + 2);
						continue;
					}
					params.put("POINT_TYPE", point_type);
				}
				// 验证设备类型
				if (StringUtil.isEmpty(data.get(3))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					equip_type = EQUIP_TYPE.get(data.get(3));
					if (equip_type == null) {
						errorRows += "," + (i + 2);
						continue;
					}
					params.put("EQP_TYPE_ID", equip_type);
				}
				// 验证经度
				if (StringUtil.isEmpty(data.get(4))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					params.put("LONGITUDE", data.get(4));
				}
				// 验证纬度
				if (StringUtil.isEmpty(data.get(5))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					params.put("LATITUDE", data.get(5));
				}
				// 验证地址
				if (StringUtil.isEmpty(data.get(6))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					params.put("ADDRESS", data.get(6));
				}
				params.put("SON_AREA_ID", getAreaId(data.get(7)));
				params.put("AREA_ID", getAreaId(data.get(8)));
				params.put("CREATE_STAFF", staffId);
				// 保存点
				pointManageDao.insertPoint(params);
			}
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", "文件格式错误！");
			return result;
		}
		if (!"".equals(repeatRows) || !"".equals(errorRows)) {
			result.put("status", false);
			String message = null;
			if (!"".equals(repeatRows)) {
				message = "编号 " + repeatRows.substring(1) + " 的记录重复：存在相同编码的点！";
			}
			if (!"".equals(errorRows)) {
				String str = "编号 " + errorRows.substring(1)
						+ " 的记录数据有误：数据为空或数据不符合规范！";
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

	private String getAreaId(String area_name) {
		if (AREA.containsKey(area_name)) {
			return AREA.get(area_name);
		}
		List<Map<String, String>> result = pointManageDao
				.getAreaNameById(area_name);
		if (result == null || result.get(0) == null) {
			return null;
		} else {
			String area_id = result.get(0).get("AREA_ID");
			AREA.put(area_name, area_id);
			return area_id;
		}
	}

	@Override
	public List<Map<String, String>> queryExistsPoint(HttpServletRequest request) {
		String sonAreaId = request.getSession().getAttribute("sonAreaId")
				.toString();
		return pointManageDao.queryExistsPoint(sonAreaId);
	}

	@Override
	public List<Map<String, Object>> queryPoint(HttpServletRequest request) {
		String point_no = request.getParameter("point_no");
		String point_name = request.getParameter("point_name");
		String address = request.getParameter("address");
		String point_type = request.getParameter("point_type");
		String point_level = request.getParameter("point_level");
		String dept_name = request.getParameter("dept_name");
		// 管理员查询条件
		String son_area = request.getParameter("son_area_id");
		Map map = new HashMap();
		map.put("POINT_NO", point_no);
		map.put("POINT_NAME", point_name);
		map.put("ADDRESS", address);
		map.put("POINT_TYPE", point_type);
		map.put("POINT_LEVEL", point_level);
		map.put("DEPT_NAME", dept_name);
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			// 判断查询条件是否有查询子区域
			if (!StringUtil.isEmpty(son_area)) {
				map.put("SON_AREA_ID", son_area);
				map.put("AREA_ID", "");
			}
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				// 判断查询条件是否有查询子区域
				if (!StringUtil.isEmpty(son_area)) {
					map.put("SON_AREA_ID", son_area);
				}
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}
		if(!map.containsKey("AREA_ID")){
			map.put("AREA_ID", session.getAttribute("areaId"));
		}
		return pointManageDao.queryPoint(map);
	}

	@Override
	public JSONObject updatePointName(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		// 要删除的关键点ID
		String pointId = request.getParameter("pointId");
		String pointName = request.getParameter("pointName");
		// 先删除坐标采集的点
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pointId", pointId);
		params.put("pointName", pointName);
		pointManageDao.updatePointName(params);
		result.put("status", true);
		return result;
	}

	@Override
	public Map<String, Object> queryKeyPoints(HttpServletRequest request,
			UIPage pager) {

		// 封装查询参数
		Map paramsMap = getParamMap(request);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramsMap);

		List<Map> pointLst = pointManageDao.queryKeyPoints(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", pointLst);
		return pmap;
	}

	@Override
	public List<Map<String, Object>> queryKeyPointsExl(
			HttpServletRequest request) {

		// 封装查询参数
		Map paramsMap = getParamMap(request);
		return pointManageDao.queryKeyPointsExl(paramsMap);
	}

	/**
	 * 封装查询参数到Map
	 * 
	 * @param request
	 *            请求参数
	 * 
	 * @return 查询集Map
	 */
	private Map<String, Object> getParamMap(HttpServletRequest request) {

		Map<String, Object> paramsMap = new HashMap<String, Object>();

		String point_no = request.getParameter("point_no");
		String point_name = request.getParameter("point_name");
		String point_creator = request.getParameter("point_creator");
		String line_no = request.getParameter("line_no");
		String line_name = request.getParameter("line_name");

		// 管理员查询条件
		String son_area = request.getParameter("son_area");

		paramsMap.put("POINT_NO", point_no);
		paramsMap.put("POINT_NAME", point_name);
		paramsMap.put("POINT_CREATOR", point_creator);
		paramsMap.put("LINE_NO", line_no);
		paramsMap.put("LINE_NAME", line_name);

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			// 判断查询条件是否有查询子区域
			if (!StringUtil.isEmpty(son_area)) {
				paramsMap.put("SON_AREA_ID", son_area);
			}
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				paramsMap.put("AREA_ID", session.getAttribute("areaId"));
				// 判断查询条件是否有查询子区域
				if (!StringUtil.isEmpty(son_area)) {
					paramsMap.put("SON_AREA_ID", son_area);
				}
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					paramsMap.put("SON_AREA_ID", session
							.getAttribute("sonAreaId"));
				} else {
					paramsMap.put("AREA_ID", -1);
				}
			}
		}

		return paramsMap;
	}

	@Override
	public List<Map<String, String>> getMntList() {
		
		return pointManageDao.getMntLevel();
	}

	@Override
	public Map queryPointForcoordinate(HttpServletRequest request) {
		String point_id = request.getParameter("POINT_ID");
		Map rs = pointManageDao.queryPointById(point_id);
		rs.put("area", pointManageDao.getAreaByParentId(rs.get("SON_AREA_ID").toString()));
		//获取所有设备类型
		rs.put("equipTypes", pointManageDao.getEquipTypes());
		//维护等级
		rs.put("mntLevel", pointManageDao.getMntLevel());
		//点类型
		rs.put("pointTypeList", pointManageDao.getPointTypes());
		//网格
		List<Map> dept = cableService.queryDept(request);
		rs.put("deptList", dept);
		List<Map> areaType = new ArrayList();
		Map map1 = new HashMap();
		map1.put("id", 1);
		map1.put("name", "小区");
		Map map2 = new HashMap();
		map2.put("id", 2);
		map2.put("name", "路边");
		areaType.add(map1);
		areaType.add(map2);
		rs.put("areaType", areaType);
		return rs;
	}
	
	@Override
	public Map queryPointById(HttpServletRequest request) {
		String point_id = request.getParameter("POINT_ID");
		Map rs = pointManageDao.queryPointById(point_id);
		return rs;
	}


	@Override
	public Map updateCoordinate(HttpServletRequest request) {
		  Map rs = new HashMap();
		  String flag = "false";
//		  String LONGITUDE=request.getParameter("LONGITUDE");
//		  String LATITUDE=request.getParameter("LATITUDE");
		  String POINT_NAME=request.getParameter("POINT_NAME");
		  String EQP_TYPE_ID=request.getParameter("EQP_TYPE_ID");
		  String POINT_LEVEL=request.getParameter("POINT_LEVEL");
		  String POINT_ID=request.getParameter("POINT_ID");
		  String POINT_TYPE = request.getParameter("POINT_TYPE");
		  String SON_AREA_ID = request.getParameter("SON_AREA_ID");
		  String DEPT_NO = request.getParameter("DEPT");
		  String AREA_TYPE = request.getParameter("AREA_TYPE");
		  if(POINT_NAME.equals("")||
				  POINT_TYPE.equals("")||
				  POINT_ID.equals("")){
			  rs.put("flag", false);
			  return rs;
		  }
		  Map params = new HashMap();
//		  params.put("LONGITUDE", LONGITUDE);
//		  params.put("LATITUDE", LATITUDE);
		  params.put("POINT_NAME", POINT_NAME);
		  params.put("EQP_TYPE_ID", EQP_TYPE_ID);
		  params.put("POINT_LEVEL", POINT_LEVEL);
		  params.put("POINT_ID", POINT_ID);
		  params.put("POINT_TYPE", POINT_TYPE);
		  params.put("SON_AREA_ID", SON_AREA_ID);
		  params.put("AREA_TYPE", AREA_TYPE);
		  params.put("MODIFY_STAFF", request.getSession().getAttribute(
						"staffId"));
		try {
			  pointManageDao.updateCoordinate(params);
			  if(!"".equals(DEPT_NO)&&null!=DEPT_NO){
				  Map conds = pointManageDao.getDeptInfo(DEPT_NO);
				  conds.put("POINT_ID", POINT_ID);
				  if(pointManageDao.ifPointDeptExists(POINT_ID)>0){
					  pointManageDao.editPointDept(conds);
				  }else{
					  Map point = pointManageDao.getPointInfo(POINT_ID);
					  conds.put("POINT_NO",point.get("POINT_NO"));
					  conds.put("POINT_NAME", point.get("POINT_NAME"));
					  pointManageDao.addPointRelationship(conds);
				  }
			  }
			  
			  rs.put("flag", true);
		} catch (Exception e) {
			 rs.put("flag", false);
		}
		return rs;
	}

	@Override
	public JSONObject importKeyPoint(HttpServletRequest request,
			MultipartFile file) {
		JSONObject result = new JSONObject();
		Map<String,String> pointTypes= new HashMap<String,String>();
		pointTypes.put("关键点", "1");
		pointTypes.put("普通隐患点", "2");
		pointTypes.put("看护点", "3");
		pointTypes.put("设备点", "4");
		Map<String,String> equipTypes= new HashMap<String,String>();
		equipTypes.put("光交", "1000");
		equipTypes.put("电交", "1001");
		equipTypes.put("局点", "1002");
		equipTypes.put("关键人井", "1005");
		equipTypes.put("电杆", "1006");
		equipTypes.put("其他", "1007");
		Map<String,String> pointLevels= new HashMap<String,String>();
		pointLevels.put("A1", "1");
		pointLevels.put("A2", "2");
		pointLevels.put("A3", "3");
		pointLevels.put("B1", "4");
		pointLevels.put("B2", "5");
		pointLevels.put("B3", "6");
		pointLevels.put("C1", "7");
		pointLevels.put("C2", "8");
		pointLevels.put("C3", "9");
		try {
			String staff_id = request.getSession().getAttribute("staffId").toString();
			ExcelUtil parse= new ExcelUtil(file.getInputStream());
			List<List<String>> linepoints = parse.getDatas(10);
			Map params = new HashMap();
			List<String> point;
			 for (int i = 0; i < linepoints.size(); i++) {
			    	point=linepoints.get(i);
			    	if(!point.get(0).equals("")){
			    		params.put("POINT_NO", point.get(0));
						params.put("POINT_NAME", point.get(1));
						params.put("POINT_TYPE", pointTypes.containsKey(point.get(2))?pointTypes.get(point.get(2)):"关键点");
						params.put("EQP_TYPE_ID", equipTypes.containsKey(point.get(3))?equipTypes.get(point.get(3)):"光交");
						params.put("LONGITUDE", point.get(4));
						params.put("LATITUDE", point.get(5));
						params.put("ADDRESS", "");
						params.put("POINT_LEVEL", pointLevels.containsKey(point.get(6))?pointLevels.get(point.get(6)):"8");
						params.put("SON_AREA_ID", pointManageDao.getAreaIdByName(
								point.get(7)).equals("") ? request.getSession()
								.getAttribute("sonAreaId") : pointManageDao
								.getAreaIdByName(point.get(7)));
						params.put("POINT_LEVEL", pointLevels.containsKey(point.get(6))?pointLevels.get(point.get(6)):"8");
						params.put("AREA_ID", request.getSession().getAttribute("areaId"));
						params.put("CREATE_STAFF",staff_id);
						// 验证编码是否重复
						int rows = pointManageDao.isRepeat(params);
						if (rows == 0) {
							pointManageDao.insertPoint(params);
						}
			    	}
					
			}
			 result.put("status", true);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("status", false);
		}catch (Exception e) {
			e.printStackTrace();
			result.put("status", false);
		}
		pointTypes.clear();
		equipTypes.clear();
		pointLevels.clear();
	    return result;
	}
	@Override
	public List<Map<String, String>> getEqpTypeList() {
		
		return pointManageDao.getEqpTypeList();
	}
}
