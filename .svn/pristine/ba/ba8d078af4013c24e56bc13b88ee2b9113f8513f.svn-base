package com.cableInspection.serviceimpl;


import icom.system.dao.CableInterfaceDao;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.CableDao;
import com.cableInspection.dao.CablePlanDao;
import com.cableInspection.dao.PointManageDao;
import com.cableInspection.model.CableModel;
import com.cableInspection.model.PointModel;
import com.cableInspection.model.WellModel;
import com.cableInspection.service.CableService;
import com.cableInspection.webservice.CoordWebService;
import com.cableInspection.webservice.CoordWebServiceLocator;
import com.cableInspection.webservice.CoordWebServiceSoapBindingStub;
import com.system.constant.RoleNo;
import com.system.dao.RoleDao;
import com.system.dao.StaffDao;
import com.util.DateUtil;
import com.util.ExcelUtil;
import com.util.Rule;
import com.util.StringUtil;

@SuppressWarnings("all")
@Service
public class CableServiceImpl implements CableService {
	@Resource
	private CableDao cableDao;

	@Resource
	private StaffDao staffDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	private CablePlanDao cablePlanDao;
	
	@Resource
	private PointManageDao pointManageDao;
	
	@Resource
	private  CableInterfaceDao cableInterfaceDao;

	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String cable_no = request.getParameter("cable_no");
		String cable_name = request.getParameter("cable_name");
		String area_id = request.getParameter("area_id");
		String son_area_id = request.getParameter("son_area_id");
		String parent_cable_name = request.getParameter("parent_cable_name");
		String parent_cable_no = request.getParameter("parent_cable_no");
		String LINE_TYPE = request.getParameter("LINE_TYPE");
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理�?
			// map.put("AREA_ID", session.getAttribute("areaId"));
			map.put("SON_AREA_ID", son_area_id);
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				map.put("SON_AREA_ID", son_area_id);
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}
		map.put("CABLE_NO", cable_no);
		map.put("CABLE_NAME", cable_name);
		map.put("PARENT_CABLE_NAME", parent_cable_name);
		map.put("PARENT_CABLE_NO", parent_cable_no);
		map.put("LINE_TYPE", LINE_TYPE);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> olists = cableDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;

	}

	@Override
	public String queryPoints(HttpServletRequest request) {
		Map map = new HashMap();
		map.put("roadName", request.getParameter("roadName"));
		map.put("eqpName", request.getParameter("eqpName"));
		map.put("eqpType", request.getParameter("eqpType"));
		map.put("isUsed", request.getParameter("isUsed"));
		map.put("SON_AREA_ID", request.getParameter("sonArea"));
		map.put("dept_no", request.getParameter("dept_no"));
		map.put("area_type", request.getParameter("area_type"));
		String[] levels=request.getParameterValues("point_level[]");
		String point_level="";
		if(null!=levels&&levels.length>0){
			for (int i = 0; i < levels.length; i++) {
				point_level+=","+levels[i];
			}
			point_level=point_level.substring(1,point_level.length());
			map.put("point_levels", point_level);
		}
		String play_kind = request.getParameter("play_kind");
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理�?
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
		}
		List<Map> list = new ArrayList();
		/*
		 * 不查询C3,B3
		 */
		if(!"".equals(play_kind)&&null!=play_kind&&"1".equals(play_kind)){
			list = cableDao.queryPointsByPlayKind(map);
		}else{
			list = cableDao.queryPoints(map);
		}
		JSONArray json = new JSONArray();
		String jsonString = json.fromObject(list).toString();
		return jsonString;
	}

	public List<Map> queryEquipmentType() {
		List<Map> list = cableDao.queryEquipmentType();
		return list;
	}
	
	public List queryDept(HttpServletRequest request) {
		Map map = new HashMap();
		//map.put("LINE_ID", request.getParameter("line_id"));
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("STAFF_ID", staffId);
		params.put("ROLE_NO", "LXXJ_ADMIN");
		int hasRole = staffDao.isHasRole(params);

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理�?
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
		}
	
		List<Map> listDept = cableDao.queryDept(map);
		return listDept;
	}

	@Override
	public String getCable(HttpServletRequest request) {
		Map map = new HashMap();
		Map requestName=request.getParameterMap();
		String cable_name="";
		String cable_no="";
		String dept_no="";
		String son_area_id="";
		if(requestName.containsKey("cable_no")){
			cable_no=URLDecoder.decode(request.getParameter("cable_no").toString());
		}
		if(requestName.containsKey("cable_name")){
			cable_name=URLDecoder.decode(request.getParameter("cable_name").toString());
		}
		if(requestName.containsKey("dept_no")){
			dept_no=URLDecoder.decode(request.getParameter("dept_no").toString());
		}
		if(requestName.containsKey("son_area_id")){
			son_area_id=URLDecoder.decode(request.getParameter("son_area_id").toString());
		}
		map.put("LINE_ID", request.getParameter("line_id"));
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("STAFF_ID", staffId);
		params.put("ROLE_NO", "LXXJ_ADMIN");
		int hasRole = staffDao.isHasRole(params);

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理�?
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
		}
		map.put("cable_name", cable_name);
		map.put("cable_no", cable_no);
		map.put("dept_no", dept_no);
		if(!son_area_id.equals("")){
			map.put("SON_AREA_ID", son_area_id);
		}
		
		List<Map> list = cableDao.getCable(map);
		List<CableModel> list1 = new ArrayList<CableModel>();
		for (Map listMap : list) {
			CableModel cm = new CableModel();
			cm.setLineId(Integer.parseInt(listMap.get("LINE_ID").toString()));
			cm.setLineNo(listMap.get("LINE_NO").toString());
			cm.setLineName(listMap.get("LINE_NAME").toString());
			cm.setLineLevel(Integer.parseInt(listMap.get("LINE_LEVEL")
					.toString()));
			cm.setCreateStaff(Long.parseLong(listMap.get("CREATE_STAFF")
					.toString()));
			cm.setCreateTime(listMap.get("CREATE_TIME").toString());
			cm.setIsExistPlan(Integer.parseInt(listMap.get("ISEXISTPLAN")
					.toString()));
			// cm.setModifyStaff(modifyStaff)
			// cm.setModifyTime(modifyTime);
			List<Map> queryPoint = cableDao.queryPoint(listMap);
			List<PointModel> pointMode = new ArrayList<PointModel>();
			for (int i = 0; i < queryPoint.size(); i++) {
				PointModel pm = new PointModel();
				pm.setLatitude(queryPoint.get(i).get("LONGITUDE").toString());
				pm.setLongitude(queryPoint.get(i).get("LATITUDE").toString());
				pm.setPoint_type(queryPoint.get(i).get("POINT_TYPE").toString());
				pointMode.add(pm);
			}
			cm.setPointMode(pointMode);
			list1.add(cm);
		}
		JSONArray json = new JSONArray();
		String jsonString = json.fromObject(list1).toString();
		return jsonString;
	}

	@Override
	public void saveCabel(HttpServletRequest request) {
		String jsString = request.getParameter("cableObj");
		HttpSession session = request.getSession();
		String staffId = session.getAttribute("staffId").toString();
		String areaId = session.getAttribute("areaId").toString();
		String sonAreaId = session.getAttribute("sonAreaId").toString();
		String dept_no = request.getParameter("dept_no");
		String mainLineId = "";
		String parentId="";
		Map parentMap=new HashMap();
		JSONArray json = JSONArray.fromObject(jsString);
		for (int k = 0; k < json.size(); k++) {
			Map map = new HashMap();
			map.put("LINE_NO", json.getJSONObject(k).get("line_no").toString());
			map.put("LINE_NAME", json.getJSONObject(k).get("line_name")
					.toString());
			map.put("LINE_LEVEL", json.getJSONObject(k).getString("line_level"));
			if (!"polygon".equals(request.getParameter("shape"))) {
				map.put("PARENT_LINE_NO", json.getJSONObject(k).get("parent_line_no").toString());
				map.put("PARENT_LINE_NAME", json.getJSONObject(k).get("parent_line_name").toString());
				map.put("DEPT_NO", json.getJSONObject(k).get("dept_no").toString());
				parentId=cableDao.checkParentLine(map);
				//缆线新增
				if(null==parentId||parentId.equals("")){
					parentMap.put("LINE_LEVEL", 3);
					parentMap.put("LINE_NO", json.getJSONObject(k).get("parent_line_no").toString());
					parentMap.put("LINE_NAME", json.getJSONObject(k).get("parent_line_name").toString());
					parentMap.put("DEPT_NO", json.getJSONObject(k).get("dept_no").toString());
					parentMap.put("CREATE_STAFF", staffId);
					parentMap.put("AREA_ID", areaId);
					parentMap.put("SON_AREA_ID", sonAreaId);
					parentMap.put("LINE_TYPE", 4);//切片的边缘缆线为1�
					parentMap.put("PARENT_LINE_ID", "");
					cableDao.insertLine(parentMap);
					map.put("PARENT_LINE_ID", parentMap.get("line_id"));
				}else{
					map.put("PARENT_LINE_ID", "");
					map.put("DEPT_NO", "");
				}
			}else{
				map.put("PARENT_LINE_ID", "");
			}
			map.put("CREATE_STAFF", staffId);
			map.put("AREA_ID", areaId);
			map.put("SON_AREA_ID", sonAreaId);
			map.put("LINE_TYPE", "polygon".equals(request.getParameter("shape"))?1:0);//切片的边缘缆线为1�?
			
			String points = json.getJSONObject(k).get("linepoint").toString();
			List list = new ArrayList();
			// 截取线的坐标成数�?
			String[] p = points.split(",");
			String[] temp = null;
			for (int i = 0, j = p.length; i < j; i++) {
				// planDetail = new HashMap();
				// 判断坐标点是否具有ID
				Map pointMap = new HashMap();
				temp = p[i].split(":");
				pointMap.put("LONGITUDE", temp[0]);
				pointMap.put("LATITUDE", temp[1]);
				List<Map> resultMap = cableDao.queryPointId(pointMap);
				pointMap.put("CREATE_STAFF", staffId);
				if (resultMap.size() > 0) {
					// 标注物点的ID
					list.add(resultMap.get(0).get("COUNTSUM"));
				} else {
					// 新产生的�?
					// 查询序列，获取ID
					// int seqId=cableDao.querySeqId();
					// list.add(seqId);
					// pointMap.put("POINT_ID", seqId);
					// 插入点到数据�?
					cableDao.insertPoint(pointMap);
					// System.out.println(pointMap);
					// System.out.println(seqId);
					list.add(pointMap.get("point_id"));
					// pointMap.clear();
				}

				// careDao.insertDetail(planDetail);
			}
			// 取线ID
			// 插入�?
			//map.put("dept_no", dept_no);
			cableDao.insertLine(map);
			mainLineId = map.get("line_id").toString();
			// 插入线和点对应关�?
			for (int h = 0; h < list.size(); h++) {
				Map linePointMap = new HashMap();
				linePointMap.put("LINE_ID", map.get("line_id"));
				linePointMap.put("POINT_ID", list.get(h));
				// linePointMap.put("modify_time", list.get(h));
				linePointMap.put("MODIFY_STAFF", staffId);
				linePointMap.put("POINT_SEQ", h + 1);
				cableDao.inserLinePoint(linePointMap);
			}
			if(!"polygon".equals(request.getParameter("shape"))){
				//新增插入缆线长度
				cableDao.addDistance(map.get("line_id").toString());
				//删除多余的人井
				cableDao.deleteWells();
			}
			
		}

		// 多边形型缆线
		if ("polygon".equals(request.getParameter("shape"))) {

			// 获取缆线计划相关字段
			String planJsonStr = request.getParameter("planObj");
			JSONObject planJsonObj = JSONObject.fromObject(planJsonStr);

			// 获取区域内所有关键点
			Map map = new HashMap();
			map.put("SON_AREA_ID", sonAreaId);
			map.put("LINE_ID", mainLineId);
			List<Map> keyPointLst = getKeyPointLst(session, map);

			// 存放区域内按设备等级(A/B/C)分类的设备点集合
			Map<String, List<Map>> pointMapByLevel = null;

			for (int k = 0; k < json.size(); k++) {

				String polygonPoints = json.getJSONObject(k).get("linepoint")
						.toString();

				// 获取按设备等�?A/B/C)分类的设备点集合
				pointMapByLevel = getPointMapByLevel(keyPointLst, polygonPoints);

				if (CollectionUtils.isEmpty(pointMapByLevel)) {
					continue;
				}

				// 将分类后设备点按其等级生成缆线及缆线与设备点对应关系
				Map lineMap = new HashMap();
				lineMap.put("LINE_NO", json.getJSONObject(k).get("line_no")
						.toString());
				lineMap.put("LINE_NAME", json.getJSONObject(k).get("line_name")
						.toString());
				lineMap.put("LINE_LEVEL", 1);
				lineMap.put("CREATE_STAFF", staffId);
				lineMap.put("AREA_ID", areaId);
				lineMap.put("SON_AREA_ID", sonAreaId);
				lineMap.put("PARENT_LINE_ID", map.get("LINE_ID"));
				// 缆线计划相关字段
				Map cablePlanMap = new HashMap();
				cablePlanMap.put("PLAN_NAME", json.getJSONObject(k).get(
						"line_no").toString());
				cablePlanMap.put("PLAN_NO", json.getJSONObject(k)
						.get("line_no").toString());
				cablePlanMap.put("PLAN_START_TIME", planJsonObj
						.getString("start_date"));
				cablePlanMap.put("PLAN_END_TIME", planJsonObj
						.getString("end_date"));
				cablePlanMap.put("CREATE_STAFF", staffId);
				cablePlanMap.put("AREA_ID", areaId);
				cablePlanMap.put("SON_AREA_ID", sonAreaId);
				// 巡检人员ID,用户生成巡检任务
				cablePlanMap.put("INSPECTOR", planJsonObj
						.getString("inspact_id"));
				cablePlanMap.put("INSPECTOR_TYPE", planJsonObj
						.getString("inspact_type"));
				for (Map.Entry<String, List<Map>> pointEntry : pointMapByLevel
						.entrySet()) {

					List<Map> pointLstByLevel = pointEntry.getValue();

					// 如果某等级对应关键点关键点不存在，则不建立对应缆�?
					if (CollectionUtils.isEmpty(pointLstByLevel))
						continue;

					// 创建缆线及与关键点的对应关系
					createCable(pointLstByLevel, lineMap, pointEntry.getKey());

					// 获取缆线ID
					String lineId = ObjectUtils
							.toString(lineMap.get("line_id"));

					// 生成缆线计划
					createCablePlan(cablePlanMap, pointEntry.getKey(), lineId);

					// 生成缆线任务
					createCableTask(cablePlanMap);
				}
			}
		}

	}

	/**
	 * 查询出区域内�?��关键�?
	 */
	private List<Map> getKeyPointLst(HttpSession session, Map map) {

		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理�?
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
		}

		return cableDao.queryNotDistributePoints(map);
	}

	/**
	 * 构建多边�?
	 */
	private java.awt.geom.GeneralPath buildPolygon(List<Point2D.Double> polygon) {

		java.awt.geom.GeneralPath generalPath = new java.awt.geom.GeneralPath();

		Point2D.Double first = polygon.get(0);
		generalPath.moveTo(first.x, first.y);

		for (Point2D.Double d : polygon) {
			generalPath.lineTo(d.x, d.y);
		}

		generalPath.lineTo(first.x, first.y);

		generalPath.closePath();

		return generalPath;
	}

	/**
	 * 获取多边形区域内的关键点
	 * 
	 * @param keyPointLst
	 *            关键点集�?
	 * @param polygonPoints
	 *            多边形顶点集�?
	 * @return List 在多边形区域内的关键�?
	 */
	private List<Map> getPointInPolygonLst(List<Map> keyPointLst,
			String polygonPoints) {

		// 存放多边形各顶点经纬度坐�?
		List<Point2D.Double> polygonPointLst = new ArrayList<Point2D.Double>();
		// 存放多边形内关键�?
		List<Map> pointInPolygonLst = new ArrayList<Map>();

		// 截取线的坐标成数�?
		String[] pointArr = polygonPoints.split(",");
		String[] tempPoint = null;
		for (int i = 0; i < pointArr.length; i++) {
			tempPoint = pointArr[i].split(":");
			polygonPointLst
					.add(new Point2D.Double(Double.valueOf(tempPoint[0]),
							Double.valueOf(tempPoint[1])));
		}

		// 构建多边�?
		java.awt.geom.GeneralPath polygon = buildPolygon(polygonPointLst);

		for (Map point : keyPointLst) {

			if (polygon.contains(new Point2D.Double(Double.valueOf(ObjectUtils
					.toString(point.get("LONGITUDE"))), Double
					.valueOf(ObjectUtils.toString(point.get("LATITUDE")))))) {
				pointInPolygonLst.add(point);
			}
		}

		return pointInPolygonLst;
	}

	/**
	 * 
	 * 将区域内设备点按其等级分类存储到Map�?
	 * 
	 * @param pointInPolygonLst
	 *            区域内设备点集合
	 * @return Map key-->设备点等级A/B/C value-->List<point>
	 */
	private Map<String, List<Map>> getPointMapByLevel(List<Map> keyPointLst,
			String polygonPoints) {

		// 将设备点按等级A/B/C存储到Map�?
		Map<String, List<Map>> pointMapByLevel = new HashMap<String, List<Map>>();

		// 获取多边形内设备�?
		List<Map> pointInPolygonLst = getPointInPolygonLst(keyPointLst,
				polygonPoints);

		// 多边形内设备点不存在,直接向上层返回空Map
		if (CollectionUtils.isEmpty(pointInPolygonLst)) {
			return pointMapByLevel;
		}

		// 保存不同等级的设备点集合
		List<Map> pointLstByLevelA = new ArrayList<Map>();
		List<Map> pointLstByLevelB = new ArrayList<Map>();
		List<Map> pointLstByLevelC = new ArrayList<Map>();

		pointMapByLevel.put("A", pointLstByLevelA);
		pointMapByLevel.put("B", pointLstByLevelB);
		pointMapByLevel.put("C", pointLstByLevelC);

		for (Map point : pointInPolygonLst) {

			// 获取设备点等�?
			String pointLevel = ObjectUtils.toString(point.get("POINT_LEVEL"));

			// 设备点等级A1-A3/1-3 B1-B3/4-6 C1-C3/7-9,不需要巡�?3,C3等级的设备点
			if (matches(pointLevel, "1", "2", "3")) {

				pointLstByLevelA.add(point);
			} else if (matches(pointLevel, "4", "5")) {

				pointLstByLevelB.add(point);
			} else if (matches(pointLevel, "7", "8")) {

				pointLstByLevelC.add(point);
			}

		}

		return pointMapByLevel;
	}

	/**
	 * 判断对象的�?是否匹配数组中任意�?
	 * 
	 * @param t
	 *            待匹配的对象
	 * @param values
	 *            对象数组
	 * @return boolean
	 */
	private <T> boolean matches(T t, T... values) {

		boolean isMatch = false;

		for (T value : values) {

			if (t == value || (t != null && value.equals(t))) {

				isMatch = true;
				break;
			}
		}

		return isMatch;
	}

	/**
	 * // 将分类后设备点按其等级生成缆线及缆线与设备点对应关系
	 * 
	 * @param pointLstByLevel
	 *            某等级的�?��设备点集�?
	 * @param cableMap
	 *            缆线相关字段
	 * @param cableNameSuffix
	 *            光缆段后�?��,以关键点等级标注
	 */
	private void createCable(List<Map> pointLstByLevel, Map cableMap,
			String cableNameSuffix) {
		
		String LINE_NAME = cableMap.get("LINE_NAME").toString();
		String LINE_NO =  cableMap.get("LINE_NO").toString();
		if (LINE_NO.contains("__"))
		{
			LINE_NAME = LINE_NAME.substring(0,LINE_NAME.length()-3);
			LINE_NO = LINE_NO.substring(0,LINE_NO.length()-3);
		}
		if(!"A".equals(cableNameSuffix))
		{
			
			cableMap.put("LINE_LEVEL", "B".equals(cableNameSuffix)?2:3);//维护等A�?等级，维护等级B�?等级
		}
		cableMap.put("LINE_NO", LINE_NO + "__" + cableNameSuffix);
		cableMap.put("LINE_NAME",LINE_NAME + "__" + cableNameSuffix);
		cableMap.put("LINE_TYPE",2);//区域内的点模拟成缆线
		cableDao.insertLine(cableMap);

		// 插入线和点对应关�?
		Map linePointMap = null;

		for (int i = 0; i < pointLstByLevel.size(); i++) {

			linePointMap = new HashMap();
			linePointMap.put("LINE_ID", cableMap.get("line_id"));
			linePointMap
					.put("POINT_ID", pointLstByLevel.get(i).get("POINT_ID"));
			linePointMap.put("POINT_SEQ", i + 1);
			linePointMap.put("MODIFY_STAFF", cableMap.get("CREATE_STAFF"));
			cableDao.inserLinePoint(linePointMap);
		}

	}

	/**
	 * 生成缆线计划
	 * 
	 * @param map
	 *            缆线计划相关字段
	 * @param cablePointLevel
	 *            缆线对应关键点等�?
	 * @param lineId
	 *            缆线ID
	 */
	private void createCablePlan(Map map, String cablePointLevel, String lineId) {

		String PLAN_NAME = map.get("PLAN_NAME").toString();
		String PLAN_NO =  map.get("PLAN_NO").toString();
		if (PLAN_NO.contains("__"))
		{
			PLAN_NAME = PLAN_NAME.substring(0,PLAN_NAME.length()-3);
			PLAN_NO = PLAN_NO.substring(0,PLAN_NO.length()-3);
		}
		/*if(!"A".equals(cablePointLevel))
		{
			PLAN_NAME = PLAN_NAME.substring(0,PLAN_NAME.length()-2);
			PLAN_NO = PLAN_NO.substring(0,PLAN_NO.length()-2);
		}*/
		map.put("PLAN_NAME", PLAN_NAME + "__" + cablePointLevel);
		map.put("PLAN_NO", PLAN_NO + "__" + cablePointLevel);
		map.put("PLAN_TYPE", 0);

		
		// 计划次数
		map.put("PLAN_FREQUENCY", 1);
		// 计划周期
		String planCircle = null;
		if ("A".equals(cablePointLevel)) {
			//planCircle = Rule.WEEK;
			planCircle = Rule.MONTH;//周计划，类似一月4次计划
			map.put("PLAN_FREQUENCY", 4);
		} else if ("B".equals(cablePointLevel)) {
			planCircle = Rule.MONTH;//半月计划
			map.put("PLAN_FREQUENCY", 2);
		} else if ("C".equals(cablePointLevel)) {
			planCircle = Rule.MONTH;
		}

		map.put("PLAN_CIRCLE", planCircle);
		
		map.put("PLAN_KIND", 1);
		map.put("CUSTOM_TIME", "");

		cablePlanDao.insertPlan(map);

		// 保存的计划id
		Integer plan_id = (Integer) map.get("plan_id");

		Map planDetail = new HashMap();
		planDetail.put("PLAN_ID", plan_id);
		planDetail.put("INSPECT_OBJECT_ID", lineId);
		planDetail.put("INSPECT_OBJECT_TYPE", 2);

		cablePlanDao.insertDetail(planDetail);
	}

	/**
	 * 生成缆线任务
	 * 
	 * @param cablePlanMap
	 *            缆线任务�?��相关擦桉�?
	 */
	private void createCableTask(Map cablePlanMap) {

		// 更新计划分配状�?map
		Map updatePlanMap = new HashMap();

		updatePlanMap.put("PLAN_ID", cablePlanMap.get("plan_id"));
		updatePlanMap.put("TASK_INSPECTOR", cablePlanMap.get("INSPECTOR"));
		updatePlanMap.put("TASK_CREATOR", cablePlanMap.get("CREATE_STAFF"));
		updatePlanMap.put("ISDISTRIBUTED", 1);
		updatePlanMap.put("INSPECTOR_TYPE",  cablePlanMap.get("INSPECTOR_TYPE"));
		// 更新计划分配状�?
		cablePlanDao.updatePlanIsDistributed(updatePlanMap);

		List<Map> ruleData = null;// 生成的规�?
		Map ruleMap = new HashMap();// 查询任务生成规则参数map
		
		// 巡检周期
		String plan_circle = cablePlanMap.get("PLAN_CIRCLE").toString();

		// �?��日期
		String plan_start_time = cablePlanMap.get("PLAN_START_TIME").toString();

		// 结束日期
		String plan_end_time = cablePlanMap.get("PLAN_END_TIME").toString();
		
		if (Rule.DAY.equals(plan_circle) || Rule.WEEK.equals(plan_circle)) {
			// 获取本周的最后一�?
			Date sundayOfCurrWeek = DateUtil.getSundayOfCurrWeek();
			if (StringUtil.stringToDate(plan_start_time, "yyyy-MM-dd").after(
					sundayOfCurrWeek)) {// 计划�?��时间在本周日�?
			} else {
				ruleMap.put("startDate", plan_start_time);
			}
			if (StringUtil.stringToDate(plan_end_time, "yyyy-MM-dd").before(
					sundayOfCurrWeek)) {// 计划结束时间在本周日�?
				ruleMap.put("endDate", plan_end_time);
			} else {
				ruleMap.put("endDate", StringUtil.dateToString(
						sundayOfCurrWeek, "yyyy-MM-dd"));
			}
		} else if (Rule.HALF_MONTH.equals(plan_circle)
				|| Rule.MONTH.equals(plan_circle)) {
			// 获取本月的最后一�?
			Date lastDayOfCurrMonth = DateUtil.getLastDayOfCurrMonth();
			Date firstDayOfCurrMonth = DateUtil.getFirstDayOfCurrMonth();
			if (StringUtil.stringToDate(plan_start_time, "yyyy-MM-dd").after(
					lastDayOfCurrMonth)) {// 计划�?��时间在本月最后一天后
				ruleMap.put("startDate", StringUtil.dateToString(
						firstDayOfCurrMonth, "yyyy-MM-dd"));
			} else {
				ruleMap.put("startDate", plan_start_time);
			}
			if (StringUtil.stringToDate(plan_end_time, "yyyy-MM-dd").before(
					lastDayOfCurrMonth)) {// 计划结束时间在本月最后一天前
				ruleMap.put("endDate", plan_end_time);
			} else {
				ruleMap.put("endDate", StringUtil.dateToString(
						lastDayOfCurrMonth, "yyyy-MM-dd"));
			}
		} else {
			ruleMap.put("startDate", plan_start_time);
			ruleMap.put("endDate", plan_end_time);
		}

		ruleMap.put("frequency", cablePlanMap.get("PLAN_FREQUENCY"));
		ruleMap.put("custom_time", StringUtil.objectToString(cablePlanMap
				.get("CUSTOM_TIME")));

		ruleData = Rule.createTaskOrder(ruleMap, plan_circle);// 生成任务的开始和结束时间

		// 任务参数map
		Map taskMap = new HashMap();
		taskMap.put("PLAN_ID", cablePlanMap.get("plan_id"));
		taskMap.put("PLAN_NO", cablePlanMap.get("PLAN_NO"));
		taskMap.put("CREATE_STAFF", cablePlanMap.get("CREATE_STAFF"));
		taskMap.put("INSPECTOR", cablePlanMap.get("INSPECTOR"));
		taskMap.put("AREA_ID", cablePlanMap.get("AREA_ID"));
		taskMap.put("SON_AREA_ID", cablePlanMap.get("SON_AREA_ID"));

		Integer TASK_ID = null;// 任务id
		String INSPECT_OBJECT_ID = null;// 巡检对象id
		String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
		Map taskDetailMap = null;
		List<Map> planDetailList = null;// 计划对应外力点信息集�?

		for (Map rule : ruleData) {
			taskMap.put("COMPLETE_TIME", rule.get("endDate"));
			taskMap.put("START_TIME", rule.get("startDate"));
			cablePlanDao.saveTask(taskMap);// 保存任务信息
			TASK_ID = Integer.valueOf(taskMap.get("TASK_ID").toString());
			// 查询出计划对应的外力点信�?
			planDetailList = cablePlanDao.getPlanDetail(NumberUtils
					.toInt(ObjectUtils.toString(cablePlanMap.get("plan_id"))));
			// 保存任务详细信息
			taskDetailMap = new HashMap();
			taskDetailMap.put("TASK_ID", TASK_ID);
			taskDetailMap.put("INSPECTOR", cablePlanMap.get("INSPECTOR"));
			for (Map planDetail : planDetailList) {
				INSPECT_OBJECT_ID = planDetail.get("LINE_ID").toString();
				INSPECT_OBJECT_TYPE = "2";
				taskDetailMap.put("INSPECT_OBJECT_ID", INSPECT_OBJECT_ID);
				taskDetailMap.put("INSPECT_OBJECT_TYPE", INSPECT_OBJECT_TYPE);
				cablePlanDao.saveTaskDetail(taskDetailMap);
			}
		}
	}

	@Override
	public Map deleteCable(HttpServletRequest request) {
		String selected = request.getParameter("selected");
		// HttpSession session = request.getSession();
		// Map map = new HashMap();
		// map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		String[] cableIds = selected.split(",");
		Map line=new HashMap();
		Map rs = new HashMap();
		int flag=0;
		for (int i = 0; i < cableIds.length; i++) {
			// map.put("LINE_ID", cableIds[i]);
			line=cableDao.getLineTypeById(Integer.parseInt(cableIds[i]));
			if(line.get("LINE_TYPE").toString().equals("4")){
				flag=cableDao.getSonLineCount(cableIds[i]);
				if(flag>0){
					rs.put("desc", "该缆线有子缆线，无法删除");
					return rs;
				}
			}
			List<Map> list = cableDao.selectLinePoint(Integer
					.parseInt(cableIds[i]));
			for (Map ma : list) {
				cableDao.deletePoint(Integer.parseInt(ma.get("POINT_ID")
						.toString()));
			}
			cableDao.deleteLinePoint(Integer.parseInt(cableIds[i]));
			if(line.get("LINE_TYPE").toString().equals("0")){
				cableDao.deleteLineCable(Integer.parseInt(cableIds[i]));
			}else{
				cableDao.deleteCable(Integer.parseInt(cableIds[i]));
			}
		}
		rs.put("desc", "成功删除缆线!");
		return rs;

	}

	@Override
	public String getAreaName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String areaId = session.getAttribute("areaId").toString();
		return cableDao.queryAreaName(areaId);
	}

	@Override
	public String getPointsInCable(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("LINE_ID", request.getParameter("lineId"));
		List<Map> list1 = cableDao.getPointsInCable(param);
		JSONArray json = new JSONArray();
		String jsonString = json.fromObject(list1).toString();
		return jsonString;

	}

	@Override
	public void saveEditedCable(HttpServletRequest request) {

		String jsString = request.getParameter("cableObj");
		HttpSession session = request.getSession();
		String staffId = session.getAttribute("staffId").toString();
		String areaId = session.getAttribute("areaId").toString();
		String sonAreaId = session.getAttribute("sonAreaId").toString();
		JSONArray json = JSONArray.fromObject(jsString);
		for (int k = 0; k < json.size(); k++) {
			Map map = new HashMap();
			map.put("LINE_NO", json.getJSONObject(k).get("line_no").toString());
			map.put("LINE_ID", json.getJSONObject(k).get("line_id").toString());
			map.put("LINE_NAME", json.getJSONObject(k).get("line_name")
					.toString());
			map.put("LINE_LEVEL", json.getJSONObject(k).getString(
							"line_level"));
			map.put("CREATE_STAFF", staffId);
			map.put("AREA_ID", areaId);
			map.put("SON_AREA_ID", sonAreaId);
			String points = json.getJSONObject(k).get("linepoint").toString();
			List list = new ArrayList();
			// 截取线的坐标成数�?
			String[] p = points.split(",");
			String[] temp = null;
			for (int i = 0, j = p.length; i < j; i++) {
				// planDetail = new HashMap();
				// 判断坐标点是否具有ID
				Map pointMap = new HashMap();
				temp = p[i].split(":");
				pointMap.put("LONGITUDE", temp[0]);
				pointMap.put("LATITUDE", temp[1]);
				List<Map> resultMap = cableDao.queryPointId(pointMap);
				pointMap.put("CREATE_STAFF", staffId);
				if (resultMap.size() > 0) {
					// 标注物点的ID
					list.add(resultMap.get(0).get("COUNTSUM"));
				} else {
					// 新产生的�?
					// 查询序列，获取ID
					// int seqId=cableDao.querySeqId();
					// list.add(seqId);
					// pointMap.put("POINT_ID", seqId);
					// 插入点到数据�?
					cableDao.insertPoint(pointMap);
					// System.out.println(pointMap);
					// System.out.println(seqId);
					list.add(pointMap.get("point_id"));
					// pointMap.clear();
				}

				// careDao.insertDetail(planDetail);
			}
			// 取线ID
			// 插入�?
			cableDao.updateLine(map);
			cableDao.deletePointInLine(map);
			// 插入线和点对应关�?
			for (int h = 0; h < list.size(); h++) {
				Map linePointMap = new HashMap();
				linePointMap.put("LINE_ID", map.get("LINE_ID"));
				linePointMap.put("POINT_ID", list.get(h));
				// linePointMap.put("modify_time", list.get(h));
				linePointMap.put("MODIFY_STAFF", staffId);
				linePointMap.put("POINT_SEQ", h + 1);
				cableDao.inserLinePoint(linePointMap);
			}
			//新增插入缆线长度
			cableDao.addDistance(map.get("LINE_ID").toString());
		}

	}

	@Override
	public List getPointsByZone(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String areaId = session.getAttribute("areaId").toString();
		String sonAreaId = request.getParameter("sonArea");
		Map map = new HashMap();
		map.put("SON_AREA_ID", sonAreaId);
		map.put("AREA_ID", areaId);
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理�?
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
		}
		//获得区域�?��设备�?
		List<Map> keyPointLst = cableDao.queryPointsByZone(map);
		String jsString = request.getParameter("ponintsObj"); 
		JSONArray json = JSONArray.fromObject(jsString);
		List<Map> pointInPolygonLst = null;
		for (int i = 0; i < json.size(); i++) {
			String polygonPoints = json.getJSONObject(i).get("linepoint")
			.toString();
			// 获取多边形内设备�?
			pointInPolygonLst = getPointInPolygonLst(keyPointLst,
					polygonPoints);
		}
		
		return pointInPolygonLst;
	}

	@Override
	public void updateCablePoint() {
		// List<Map> areaLins = cableDao.getAllAreaLines();
		Map parmas = new HashMap();
		List<Map> keyPointLst = new ArrayList();
		List<Map> linePoints = new ArrayList();
		List<Point2D.Double> polygonPointLst = new ArrayList();
		// 区域内点
		List<Map> pointInPolygonLst = new ArrayList();
		// 保存不同等级的设备点集合
		List<Map> pointLstByLevelA = new ArrayList<Map>();
		List<Map> pointLstByLevelB = new ArrayList<Map>();
		List<Map> pointLstByLevelC = new ArrayList<Map>();
		// 将设备点按等级A/B/C存储到Map�?
		Map<String, List<Map>> pointMapByLevel = new HashMap<String, List<Map>>();
		// 计划原有点数
		List<Map> planPoints = new ArrayList();
		// 所有区域
		List<Map> zones = cableDao.getAllZones();
		// 区域下所有计划
		List<Map> plans = new ArrayList();
		Map conds = new HashMap();
		Map cableMap = new HashMap();
		// 开始结束时间
		Map timeMap = new HashMap();
		int seq = 0;
		for (int i = 0; i < zones.size(); i++) {
			parmas.put("SON_AREA_ID", zones.get(i).get("SON_AREA_ID"));
			parmas.put("LINE_ID", zones.get(i).get("LINE_ID"));
			//新增的班组分组
			parmas.put("dept_no", zones.get(i).get("DEPT_NO"));
			// 获取区域内所有点
			keyPointLst = cableDao.queryNotDistributePoints(parmas);
			// 获得区域
			linePoints = cableDao.getLinePointById(zones.get(i).get("LINE_ID")
					.toString());
			if (CollectionUtils.isEmpty(linePoints)) {
				continue;
			}
			//初始化多边形
			polygonPointLst.clear();
			for (int j = 0; j < linePoints.size(); j++) {
				polygonPointLst.add(new Point2D.Double(
						Double.valueOf(linePoints.get(j).get("LONGITUDE")
								.toString()), Double.valueOf(linePoints.get(j)
								.get("LATITUDE").toString())));
			}

			// 构建多边�?
			java.awt.geom.GeneralPath polygon = buildPolygon(polygonPointLst);
			//初始化区域内的点
			pointInPolygonLst.clear();
			for (Map point : keyPointLst) {
				if (polygon.contains(new Point2D.Double(Double
						.valueOf(ObjectUtils.toString(point.get("LONGITUDE"))),
						Double.valueOf(ObjectUtils.toString(point
								.get("LATITUDE")))))) {
					pointInPolygonLst.add(point);
				}
			}

			// 多边形内设备点不存在,直接向上层返回空Map
			if (CollectionUtils.isEmpty(pointInPolygonLst)) {
				continue;
			}
			pointLstByLevelA.clear();
			pointLstByLevelB.clear();
			pointLstByLevelC.clear();
			pointMapByLevel.put("A", pointLstByLevelA);
			pointMapByLevel.put("B", pointLstByLevelB);
			pointMapByLevel.put("C", pointLstByLevelC);
			
			for (Map point : pointInPolygonLst) {

				// 获取设备点等�?
				String pointLevel = ObjectUtils.toString(point
						.get("POINT_LEVEL"));

				// 设备点等级A1-A3/1-3 B1-B3/4-6 C1-C3/7-9,不需要巡�?3,C3等级的设备点
				if (matches(pointLevel, "1", "2", "3")) {
					pointLstByLevelA.add(point);
				} else if (matches(pointLevel, "4", "5")) {
					pointLstByLevelB.add(point);
				} else if (matches(pointLevel, "7", "8")) {
					pointLstByLevelC.add(point);
				}

			}
			String LINE_NAME = zones.get(i).get("LINE_NAME").toString();
			String LINE_NO = zones.get(i).get("LINE_NO").toString();
			cableMap.clear();
			cableMap.put("LINE_TYPE", 2);// 区域内的点模拟成缆线
			cableMap.put("CREATE_STAFF", zones.get(i).get("CREATE_STAFF"));
			cableMap.put("AREA_ID", zones.get(i).get("AREA_ID").toString());
			cableMap.put("SON_AREA_ID", zones.get(i).get("SON_AREA_ID")
					.toString());
			cableMap.put("PARENT_LINE_ID", zones.get(i).get("LINE_ID")
					.toString());
			cableMap.put("LINE_NO", LINE_NO);
			cableMap.put("LINE_NAME", LINE_NAME);
			timeMap = cableDao.getSartEndTime(cableMap).size()==0?null:cableDao.getSartEndTime(cableMap).get(0);

			try {
				if (timeMap != null) {

					cableMap.put("PLAN_START_TIME", timeMap.get(
							"PLAN_START_TIME").toString());
					cableMap.put("PLAN_END_TIME", timeMap.get("PLAN_END_TIME")
							.toString());
					cableMap.put("TASK_INSPECTOR", zones.get(i)
							.get("INSPECTOR"));
					cableMap.put("TASK_CREATOR", zones.get(i).get(
							"CREATE_STAFF"));
					cableMap.put("INSPECTOR_TYPE", zones.get(i).get(
							"INSPECTOR_TYPE"));
					// 增加A计划
					conds.put("LINE_LEVEL", 1);
					conds.put("PARENT_LINE_ID", zones.get(i).get("LINE_ID")
							.toString());
					if (!pointMapByLevel.get("A").isEmpty()
							&& 0 == cableDao.getcountByLevel(conds)) {
						createLevelPlan("A", pointMapByLevel.get("A"),
								cableMap, LINE_NAME, LINE_NO);
					}
					// 增加B计划
					conds.put("LINE_LEVEL", 2);
					if (!pointMapByLevel.get("B").isEmpty()
							&& 0 == cableDao.getcountByLevel(conds)) {
						createLevelPlan("B", pointMapByLevel.get("B"),
								cableMap, LINE_NAME, LINE_NO);
					}
					// 增加C计划
					conds.put("LINE_LEVEL", 3);
					if (!pointMapByLevel.get("C").isEmpty()
							&& 0 == cableDao.getcountByLevel(conds)) {
						createLevelPlan("C", pointMapByLevel.get("C"),
								cableMap, LINE_NAME, LINE_NO);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			plans = cableDao.getLineInfosByParentLine(zones.get(i).get("LINE_ID").toString());
			if (CollectionUtils.isEmpty(plans)) {
				continue;
			}
			List<Map> pointsMap = new ArrayList();
			//给计划增加点
			for (int j = 0; j < plans.size(); j++) {
				planPoints = cableDao.getPlanPoints(plans.get(j).get("LINE_ID")
						.toString());
				Map linePointMap = new HashMap();
				linePointMap.put("LINE_ID", plans.get(j).get("LINE_ID"));
				linePointMap.put("MODIFY_STAFF", plans.get(j).get(
						"CREATE_STAFF"));
				linePointMap.put("PLAN_ID", plans.get(j).get("PLAN_ID"));
				String lineLevel = plans.get(j).get("LINE_LEVEL").toString();
				String word = lineLevel.equals("1") ? "A" : (lineLevel
						.equals("2") ? "B" : "C");
				pointsMap = pointMapByLevel.get(word);
				//如果区域内的点为空删除缆线内的点关系和计划
				if (CollectionUtils.isEmpty(pointsMap)) {
					cableDao.deleteLinePointById(linePointMap);
					cablePlanDao.deletePlan(linePointMap);
					continue;
				}
				if (pointsMap.size() != planPoints.size()) {
					//移除点关系
					cableDao.deleteLinePointById(linePointMap);
					seq = 0;
					for (Map map : pointsMap) {
						linePointMap.put("POINT_ID", map.get("POINT_ID"));
						linePointMap.put("POINT_SEQ", seq++);
						cableDao.inserLinePoint(linePointMap);
					}
					linePointMap.put("MODIFY_STAFF", "3329");
					linePointMap.put("PLAN_ID", plans.get(j).get("PLAN_ID"));
					cableDao.editModify(linePointMap);
				}
				pointsMap.clear();
			}
		}
	 }
	
	private void createLevelPlan(String level,List<Map> pointMapByLevel,Map cableMap,String LINE_NAME,String LINE_NO){
		//生成缆线和缆线对应的点
		cableMap.put("LINE_LEVEL", level.equals("A")?1:(level.equals("B")?2:3));//维护等A�?等级，维护等级B�?等级
		cableMap.put("LINE_NO", LINE_NO + "__"+level);
		cableMap.put("LINE_NAME",LINE_NAME+"__"+level);
		cableDao.insertLine(cableMap);
		
		for (int i = 0; i < pointMapByLevel.size(); i++) {
			Map linePointMap = new HashMap();
			linePointMap.put("LINE_ID", cableMap.get("line_id"));
			linePointMap
					.put("POINT_ID", pointMapByLevel.get(i).get("POINT_ID"));
			linePointMap.put("POINT_SEQ", i + 1);
			linePointMap.put("MODIFY_STAFF", cableMap.get("CREATE_STAFF"));
			cableDao.inserLinePoint(linePointMap);
		}
		// 生成缆线计划
		Map map = new HashMap();
		map.put("PLAN_NAME", LINE_NAME + "__" + level);
		map.put("PLAN_NO", LINE_NO + "__" + level);
		map.put("PLAN_TYPE", 0);
		map.put("PLAN_FREQUENCY", 1);
		map.put("PLAN_KIND", 1);
		map.put("CUSTOM_TIME", "");
		map.put("PLAN_START_TIME", cableMap.get("PLAN_START_TIME"));
		map.put("PLAN_END_TIME", cableMap.get("PLAN_END_TIME"));
		map.put("CREATE_STAFF", cableMap.get("CREATE_STAFF").toString());
		map.put("AREA_ID", cableMap.get("AREA_ID").toString());
		map.put("SON_AREA_ID", cableMap.get("SON_AREA_ID").toString());
		String planCircle = null;
		if ("A".equals(level)) {
			// planCircle = Rule.WEEK;
			planCircle = Rule.MONTH;// 周计划，类似一月4次计划
			map.put("PLAN_FREQUENCY", 4);
		} else if ("B".equals(level)) {
			planCircle = Rule.MONTH;// 半月计划
			map.put("PLAN_FREQUENCY", 2);
		} else if ("C".equals(level)) {
			planCircle = Rule.MONTH;
		}
		map.put("PLAN_CIRCLE", planCircle);
		cablePlanDao.insertPlan(map);
		
		//更新计划
		try{
			map.put("TASK_INSPECTOR", cableMap.get("INSPECTOR"));
			map.put("TASK_CREATOR", cableMap.get("CREATE_STAFF"));
			map.put("ISDISTRIBUTED", 1);
			map.put("INSPECTOR_TYPE", cableMap.get("INSPECTOR_TYPE"));
			map.put("PLAN_ID", map.get("plan_id"));
			cablePlanDao.updatePlanIsDistributed(map);
		} catch (Exception e) {
			
		}
		
		// 保存的计划id
		Integer plan_id = (Integer) map.get("plan_id");
		Map planDetail = new HashMap();
		planDetail.put("PLAN_ID", plan_id);
		planDetail.put("INSPECT_OBJECT_ID", cableMap.get("line_id"));
		planDetail.put("INSPECT_OBJECT_TYPE", 2);

		cablePlanDao.insertDetail(planDetail);
	}

	@Override
	public Map editPoint(HttpServletRequest request) {
		  Map rs = new HashMap();
		  String flag = "false";
		  String POINT_TYPE = request.getParameter("POINT_TYPE");
		  String POINT_ID = request.getParameter("POINT_ID");
		  if("-1".equals(POINT_TYPE)){
			try {
				cableDao.editToNormalPoint(POINT_ID);
				rs.put("flag", true);
			} catch (Exception e) {
				rs.put("flag", false);
			}
			return rs;
		  }else{
			  String POINT_NO= request.getParameter("POINT_NO");
			  String POINT_NAME=request.getParameter("POINT_NAME");
			  String EQP_TYPE_ID=request.getParameter("EQP_TYPE_ID");
			  String POINT_LEVEL=request.getParameter("POINT_LEVEL");
			  String SON_AREA_ID = request.getParameter("SON_AREA_ID");
			  String DEPT_NO = request.getParameter("DEPT");
			  String AREA_TYPE = request.getParameter("AREA_TYPE");
			  if(POINT_NAME.equals("")||
					  POINT_ID.equals("")){
				  rs.put("flag", false);
				  return rs;
			  }
			  Map params = new HashMap();
			  params.put("POINT_NAME", POINT_NAME);
			  params.put("EQP_TYPE_ID", EQP_TYPE_ID);
			  params.put("POINT_LEVEL", POINT_LEVEL);
			  params.put("POINT_ID", POINT_ID);
			  params.put("POINT_TYPE", POINT_TYPE);
			  params.put("SON_AREA_ID", SON_AREA_ID);
			  params.put("AREA_TYPE", AREA_TYPE);
			  params.put("POINT_NO", POINT_NO);
			  params.put("MODIFY_STAFF", request.getSession().getAttribute(
							"staffId"));
				try {
					  pointManageDao.updateNormalPoint(params);
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
		  }
		return rs;
	}

	@Override
	public Map editPointPage(HttpServletRequest request) {
		String point_id = request.getParameter("point_id");
		Map rs = cableDao.queryPointById(point_id);
		String son_area_id=request.getSession().getAttribute("sonAreaId").toString();
		rs.put("area", pointManageDao.getAreaByParentId(son_area_id));
		//获取所有设备类型
		rs.put("equipTypes", pointManageDao.getEquipTypes());
		//维护等级
		rs.put("mntLevel", pointManageDao.getMntLevel());
		//点类型
		rs.put("pointTypeList", pointManageDao.getPointTypes());
		//网格
		List<Map> dept = queryDept(request);
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
	public Map<String, Object> getCableByName(HttpServletRequest request,UIPage pager) {
		String name = request.getParameter("name");
		String queryType = request.getParameter("queryType");
		String parentCableName = request.getParameter("parentCableName");
		String area_id=request.getSession().getAttribute("areaId").toString();
		String jndi=cableInterfaceDao.getDBblinkName(area_id);
		Map param=new HashMap();
		param.put("name", name);
		param.put("jndi",jndi );
		if(null!=queryType
				&&!queryType.equals("")
				&&queryType.equals("3")){
			param.put("GRADE_ID",80000382);
			param.put("name", parentCableName);
		}else{
			param.put("GRADE_ID",80000381);
			param.put("name", name);
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(param);
		List<Map<String, String>> olists = new ArrayList();
		try{
		SwitchDataSourceUtil.setCurrentDataSource(jndi);
		 olists = cableDao.getCableByName(query);
		SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			SwitchDataSourceUtil.clearDataSource();
		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public String getWellLocation(HttpServletRequest request) {
		Map req=request.getParameterMap();
		String cable_id="";
		String cbl_sect_id="";
		if(req.containsKey("cable_id")){
			cable_id = request.getParameter("cable_id");
		}else if(req.containsKey("cbl_sect_id")){
			cbl_sect_id = request.getParameter("cbl_sect_id");
		}
		String area_id=request.getSession().getAttribute("areaId").toString();
		String staff_id=request.getSession().getAttribute("staffId").toString();
		String staff_name=request.getSession().getAttribute("staffName").toString();
//		String cable_id = "23360000001028";
//		String area_id="79";
//		String staff_id="19671";
//		String staff_name="泰州市管理员";
		String son_area_id;
		if(area_id.equals("20")){
			son_area_id="\"bss_area_id_4\":\""+request.getSession().getAttribute("sonAreaId").toString()+"\"}";
		}else{
			son_area_id="\"bss_area_id_4\":\"\"}";
		}
			
		String jndi=cableInterfaceDao.getDBblinkName(area_id);
		Map param=new HashMap();
		param.put("cable_id", cable_id);
		param.put("cbl_sect_id", cbl_sect_id);
		param.put("jndi", jndi);
		List<Map> list = new ArrayList();
		SwitchDataSourceUtil.setCurrentDataSource(jndi);
		if(req.containsKey("cable_id")){
			list= cableDao.getEqpByCableId(param);
		}else{
			list = cableDao.getEqpByCblSectionId(param);
		}
		SwitchDataSourceUtil.clearDataSource();
		CoordWebServiceSoapBindingStub cSoapBindingStub;
		CoordWebService coordWebServiceLocator = new CoordWebServiceLocator();
		List<Map> plist = new ArrayList();
		try {
			cSoapBindingStub = (CoordWebServiceSoapBindingStub) coordWebServiceLocator.getCoordWebService();
			cSoapBindingStub.setTimeout(30 * 1000);
			String result="";
			JSONObject resJsonObject;
			for (Map map : list) {
				result=cSoapBindingStub.gisObjectQuery("{\"objTypeID\":\"3\","
				         +"\"token\":\"PAD_8SAF77804D2BA1322C33E0122109\","
				         +"\"objSubTypeID\":\"508\","
				         +"\"coordType\":\"3\","
				         +"\"bss_area_id\":\""+area_id+"\","
				         +"\"objID\":\""+map.get("BSE_EQP_ID").toString()+"\","
				         +son_area_id
						);
				resJsonObject = JSONObject.fromObject(result);
				if(resJsonObject.containsKey("y")&&!map.get("NO").equals("")&&null!=map.get("NAME")){
					map.put("LATITUDE",resJsonObject.getString("y"));
					map.put("LONGITUDE",resJsonObject.getString("x"));
					map.put("CREATE_STAFF", staff_id);
					map.put("SON_AREA_ID", map.get("AREA_ID")==""?son_area_id:map.get("AREA_ID"));
					map.put("AREA_ID", area_id);
					map.put("STAFF_NAME", staff_name);
					map.put("CREATE_TIME2", "");
					map.put("IS_USED", 0);
					map.put("POINT_TYPE", 4);
					plist.add(map);
				}
				resJsonObject.clear();
			}
			if(plist.size()>0){
				for (Map map : plist) {
					cableDao.addWells(map);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray json = new JSONArray();
		String jsonString = json.fromObject(plist).toString();
		return jsonString;
	}

	@Override
	public JSONObject editLinePoint(HttpServletRequest request, MultipartFile file) {
		JSONObject result = new JSONObject();
		try {
			String staff_id = request.getSession().getAttribute("staffId").toString();
			ExcelUtil parse = new ExcelUtil(file.getInputStream());
		    List<List<String>> linepoints = parse.getDatas(10);
		    int rownum=1;
		    String line_id=null;
		    List<String> point;
		    List<Map> paramMap = new ArrayList();
		    for (int i = 0; i < linepoints.size(); i++) {
		    	point=linepoints.get(i);
		    	line_id=point.get(7);
		    	Map param = new HashMap();
				param.put("line_id", point.get(7));
				param.put("point_id", point.get(1));
				param.put("point_seq", rownum++);
				param.put("staff_id", staff_id);
				paramMap.add(param);
			}
		    cableDao.delLinePoint(line_id);
		    for (int i = 0; i < paramMap.size(); i++) {
		    	cableDao.editLinePoint(paramMap.get(i));
			}
		    paramMap.clear();
		} catch (Exception e) {
		    e.printStackTrace();
		    result.put("status", false);
		    result.put("message", "文件格式错误！");
		    return result;
		}
		result.put("status", true);
		result.put("message", "导入成功！");
		return result;
	}

	@Override
	public List<Map> exportLinePoint(HttpServletRequest request) {
		String line_id=request.getParameter("line_id").toString();
		return cableDao.exportLinePoint(line_id);
	}

	@Override
	public String addNewParentLine(HttpServletRequest request) {
		Map map = new HashMap();
		JSONObject result = new JSONObject();
		result.put("status", true);
		result.put("message", "添加成功！");
		try {
			String cable_no = request.getParameter("p_cable_no");
			String cable_name = request.getParameter("p_cable_name");
			String staff_id = request.getSession().getAttribute("staffId").toString();
			String line_id = request.getParameter("selectedLineForAdd");
			Map area = cableDao.getAreaByLineId(line_id);
			map.put("LINE_LEVEL", 3);
			map.put("LINE_NO", cable_no);
			map.put("LINE_NAME", cable_name);
			map.put("CREATE_STAFF", staff_id);
			map.put("AREA_ID", area.get("AREA_ID"));
			map.put("SON_AREA_ID", area.get("SON_AREA_ID"));
			map.put("LINE_TYPE", 4);//切片的边缘缆线为1�
			map.put("PARENT_LINE_ID", "");
			cableDao.insertLine(map);
			map.put("parent_line_id", map.get("line_id"));
			map.put("line_id", line_id);
			cableDao.editParentLine(map);	
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", "添加失败！");
		}
		return result.toString();
	}

	@Override
	public Map<String, Object> queryParentLine(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		String cable_no = request.getParameter("line_no");
		String cable_name = request.getParameter("line_name");
		String area_id = request.getParameter("area_id");
		String son_area_id = request.getParameter("son_area_id");
		
		map.put("CABLE_NO", cable_no);
		map.put("CABLE_NAME", cable_name);
		map.put("AREA_ID", area_id);
		if(null==cable_no&&null==cable_name){
			map.put("SON_AREA_ID", son_area_id);
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> olists = cableDao.queryParentLine(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public String updateParentLineForLine(HttpServletRequest request) {
		Map map = new HashMap();
		JSONObject result = new JSONObject();
		result.put("status", true);
		result.put("message", "修改成功！");
		try {
			String line_id = request.getParameter("selected_line");
			String parent_line_id = request.getParameter("selected_parent_line");
			map.put("parent_line_id", parent_line_id);
			map.put("line_id", line_id);
			cableDao.editParentLine(map);
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", "修改失败！");
		}
		return result.toString();
	}

	@Override
	public List<Map<String, Object>> exportCableByName(HttpServletRequest request) {
		String name = request.getParameter("name");
		String queryType = request.getParameter("queryType");
		String parentCableName = request.getParameter("parentCableName");
		String area_id=request.getSession().getAttribute("areaId").toString();
		String jndi=cableInterfaceDao.getDBblinkName(area_id);
		Map param=new HashMap();
		param.put("name", name);
		param.put("jndi",jndi );
		if(null!=queryType
				&&!queryType.equals("")
				&&queryType.equals("3")){
			param.put("GRADE_ID",80000382);
			param.put("name", parentCableName);
		}else{
			param.put("GRADE_ID",80000381);
			param.put("name", name);
		}
		SwitchDataSourceUtil.setCurrentDataSource(jndi);
		List<Map<String, Object>> olists = cableDao.exportCableByName(param);
		SwitchDataSourceUtil.clearDataSource();
		for (int i = 0; i < olists.size(); i++) {
			if(cableDao.checkCableExists(olists.get(i))){
				olists.get(i).put("status", "已收录");
			}else{
				olists.get(i).put("status", "未收录");
			}
		}
		return olists;
	}

	@Override
	public Map<String, Object> getCableSectionById(HttpServletRequest request,
			UIPage pager) {
		String cable_id = request.getParameter("cable_id");
		String area_id=request.getSession().getAttribute("areaId").toString();
		String jndi=cableInterfaceDao.getDBblinkName(area_id);
		Map param=new HashMap();
		param.put("cable_id", cable_id);
		param.put("jndi",jndi );
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(param);
		SwitchDataSourceUtil.setCurrentDataSource(jndi);
		List<Map<String, String>> olists = cableDao.getCableSectionByCableId(query);
		SwitchDataSourceUtil.clearDataSource();
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}
}
