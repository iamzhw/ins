package com.cableInspection.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.SynchronizePointDao;
import com.cableInspection.model.EquipmentModel;
import com.cableInspection.model.PointModel;
import com.cableInspection.service.SynchronizePointService;
import com.cableInspection.webservice.CoordWebService;
import com.cableInspection.webservice.CoordWebServiceLocator;
import com.cableInspection.webservice.CoordWebServiceSoapBindingStub;
import com.system.dao.AreaDao;
import com.util.sendMessage.PropertiesUtil;

@Service
public class SynchronizePointServiceImpl implements SynchronizePointService {

	@Resource
	private SynchronizePointDao synchronizePointDao;
	
	@Resource
	private AreaDao areaDao;

	/**
	 * 设备类型ID的映射关系(OSS<-->INS)
	 */
	private static Map<String, Object> SPEC_ID_MAP = null;

	/**
	 * 13个本地网的jndi信息
	 */
	private static Map<String,String> JNDI_LIST = null;

	/**
	 * 分页查询每页查询数量
	 */
	private static final int PAGE_ROWS = 1000;

	/**
	 * 数据源,该数据源可以直接访问13个本地网的数据
	 */
	private static final String JNDI = "cpf83";

	/**
	 * 默认应用Token,标识爱运维系统调用
	 */
	private static final String DEFAULT_TOKEN = "PAD_8SAF77804D2BA1322C33E0122109";

	/**
	 * 日志服务
	 */
	private static final Logger LOGGER = Logger
			.getLogger(SynchronizePointServiceImpl.class);
	
	private static String jndi;
	
	@Override
	public void synchronizeEquipmentInfo() {

		InfoLogger("Enter SynchronizePointServiceImpl.synchronizeEquipmentInfo().");

		// 初始化全局变量
		initParams();
           try{
			SwitchDataSourceUtil.setCurrentDataSource(JNDI);
			//先创建关系
			Map map =  new HashMap();
			map.put("jndi", jndi);
			map.put("res_spec_id", "703");
			synchronizePointDao.createOssEquipment(map);
			SwitchDataSourceUtil.clearDataSource();
           }catch (Exception e) {
				e.printStackTrace();
				return;
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			long startTime = System.currentTimeMillis();

			// 构造(分页)请求,首先查出第一页数据
			Query query = fillQuery(jndi, 1);

			// 获取本地网设备表中记录
			List<EquipmentModel> equipmentInfoLst = getEquipmentInfoList(jndi,
					query, true);

			// 处理设备信息
			dealWithEquipmentInfo(equipmentInfoLst);

			// 计算页数
			int pageSize = (query.getPager().getRowcount() + (PAGE_ROWS - 1))
					/ PAGE_ROWS;

			// 继续处理其他页设备
			for (int i = 2; i <= pageSize; i++) {
				query.getPager().setPage(i);
				equipmentInfoLst = getEquipmentInfoList(jndi, query, true);
				dealWithEquipmentInfo(equipmentInfoLst);
			}

			InfoLogger("finish synchronized all data for jndi : " + jndi
					+ "! total cost time : "
					+ (System.currentTimeMillis() - startTime) / 1000 + "s");

		InfoLogger("Exit SynchronizePointServiceImpl.synchronizeEquipmentInfo().");
	}

	/**
	 * 初始化
	 */
	private void initParams() {

		// 保存设备类型ID的映射关系(OSS<-->INS)
		SPEC_ID_MAP = new HashMap<String, Object>();
		SPEC_ID_MAP.put("703", 1000);
		SPEC_ID_MAP.put("803", 1001);

		// 保存13个本地网的jndi信息(不包括南京)
		/*List<Map<String, Object>> jndiLst = synchronizePointDao.queryJndiList();
		if (CollectionUtils.isEmpty(jndiLst)) {
			throw new IllegalArgumentException(
					"query jndi list from table sys_dblink is null,please check it.");
		}*/

		JNDI_LIST = new HashMap();
		// for (Map<String, Object> map : jndiLst) {
		// JNDI_LIST.add(ObjectUtils.toString("OSSBC_DEV_"
		// + map.get("AREA_CODE")));
		// }
		
		JNDI_LIST.put("1","OSSBC_DEV_SZ");
		JNDI_LIST.put("2","OSSBC_DEV_SQ");
		JNDI_LIST.put("3","OSSBC_DEV_WX");
		JNDI_LIST.put("4","OSSBC_DEV_CZ");
		JNDI_LIST.put("5","OSSBC_DEV_ZJ");
		JNDI_LIST.put("6","OSSBC_DEV_NT");
		JNDI_LIST.put("7","OSSBC_DEV_TZ");
		JNDI_LIST.put("8","OSSBC_DEV_YZ");
		JNDI_LIST.put("9","OSSBC_DEV_HA");
		JNDI_LIST.put("10","OSSBC_DEV_YC");
		JNDI_LIST.put("11","OSSBC_DEV_LYG");
		JNDI_LIST.put("0","OSSBC_DEV_XZ");
//		JNDI_LIST.add("OSSBC_DEV_NJ");
		jndi=JNDI_LIST.get(synchronizePointDao.getAreaId());
	}

	/**
	 * 构造分页请求
	 * 
	 * @param jndi
	 *            数据源
	 * @param pageNum
	 *            页码
	 * @return Query 请求体
	 */
	private Query fillQuery(String jndi, Integer pageNum) {

		// 构造(分页)请求
		Query query = new Query();

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("jndi", jndi);

		StringBuffer sBuffer = new StringBuffer();
		for (Map.Entry<String, Object> sepc : SPEC_ID_MAP.entrySet()) {
			sBuffer.append(sepc.getKey()).append(",");
		}
		queryParams.put("res_spec_id", sBuffer.substring(0,
				sBuffer.length() - 1));

		query.setQueryParams(queryParams);

		UIPage pager = new UIPage();
		pager.setPage(pageNum);
		pager.setRows(PAGE_ROWS);

		query.setPager(pager);

		return query;
	}

	/**
	 * 分页获取某个本地网设备表中记录
	 * 
	 * @param jndi
	 *            数据源
	 * @param query
	 *            请求体
	 * @param fullQuery
	 *            是否查询全部字段信息
	 * 
	 * @return List 设备信息
	 */
	private List<EquipmentModel> getEquipmentInfoList(String jndi, Query query,
			boolean fullQuery) {

		List<EquipmentModel> equipmentInfoLst = new ArrayList<EquipmentModel>(
				PAGE_ROWS);

		try {
			// 切换数据源
			SwitchDataSourceUtil.setCurrentDataSource(JNDI);

			// 获取分页信息,此时拦截器会返回总记录数到Query对象
			if (fullQuery) {
				equipmentInfoLst = synchronizePointDao
						.queryEquipmentList(query);
			} else {
				equipmentInfoLst = synchronizePointDao
						.queryEquipmentLevelList(query);
			}

		} catch (Exception e) {
			LOGGER
					.error(
							"SynchronizePointServiceImpl.getEquipmentInfoList() got exception.",
							e);
		} finally {
			SwitchDataSourceUtil.clearDataSource();
		}

		return equipmentInfoLst;
	}

	/**
	 * 过滤ins库中已有数据,调用webservice接口查询设备经纬度,插入到数据库中
	 * 或更新ins数据库中设备点等级字段
	 * 
	 * @param equipmentInfoLst
	 *            本地网设备信息
	 * @param saveOrUpdate
	 *            新增设备点记录或更新设备点记录
	 */
	private void dealWithEquipmentInfo(List<EquipmentModel> equipmentInfoLst) {
		
		if (CollectionUtils.isEmpty(equipmentInfoLst)) {
			LOGGER
					.error("SynchronizePointServiceImpl.dealWithEquipmentInfo(). deal equipmentInfoLst is empty. ");
			return;
		}
		for (EquipmentModel equipmentModel : equipmentInfoLst) {
		// 判断设备是否已经存在,若已存在,则不做处理
			if (!isEquipmentExist(equipmentModel)) {
				saveEquipmentInfoLst(equipmentModel);
			}
			else
			{
				LOGGER.info("Equipment (" + equipmentModel+ ") has already exist! ");
				updateEquipmentInfo(equipmentModel);
			}
		}
	}

	/**
	 * 过滤ins库中已有数据,调用webservice接口查询设备经纬度,插入到数据库中
	 * 
	 * @param equipmentInfoLst
	 *            设备列表
	 */
	private void saveEquipmentInfoLst(EquipmentModel equipmentModel) {

		

		// 调用webservice接口查询设备经纬度信息
		PointModel pointModel = gisObjectQuery(equipmentModel);

		if (null == pointModel
				|| StringUtils.isEmpty(pointModel.getLongitude())
				|| StringUtils.isEmpty(pointModel.getLatitude())) {
			return;
		}

		// 将设备及其经纬度信息保存到tb_ins_point表中
		saveEquipmentInfo(equipmentModel, pointModel);
	}

	/**
	 * 更新设备点等级字段
	 * 
	 * @param equipmentLevelLst
	 *            设备列表
	 */
	private void updateEquipmentInfo(EquipmentModel equipmentModel) {

			// 更新设备点等级字段
		Map<String, Object> paras = new HashMap<String, Object>();
		if(StringUtils.isEmpty(equipmentModel.getPoint_level()) &&  StringUtils.isEmpty(equipmentModel.getAddress()))
		{
			return;
		}
		paras.put("equipmentLevel", StringUtils.isEmpty(equipmentModel.getPoint_level())?"":equipmentModel.getPoint_level());
		paras.put("point_no", equipmentModel.getNo());
		paras.put("address",  StringUtils.isEmpty(equipmentModel.getAddress())?"":equipmentModel.getPoint_level());
		paras.put("area_id", equipmentModel.getParent_area_id());
		
		/**
		 * 新增坐标同步 2016/6/23
		 */
		try {
			PointModel pointModel = gisObjectQuery(equipmentModel);
			paras.put("longitude", Double.parseDouble(pointModel
					.getLongitude()));
			paras.put("latitude", Double.parseDouble(pointModel
					.getLatitude()));	
		} catch (NullPointerException e) {
			paras.put("longitude", "");
			paras.put("latitude","");
		}
		synchronizePointDao.updateEquipmentLevel(paras);	
	}

	/**
	 * 判断设备是否已经存在
	 * 
	 * @param equipmentModel
	 *            设备基本信息
	 * @return boolean 已存在：true
	 */
	private boolean isEquipmentExist(EquipmentModel equipmentModel) {

		Map paramMap = new HashMap();
		paramMap.put("equipmentNo", equipmentModel.getNo());
		paramMap.put("area_id", equipmentModel.getParent_area_id());
		// 查询表中是否已存在该设备
		int rows = synchronizePointDao.isEquipmentExist(paramMap);
		if (rows > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 调用webservice接口查询设备经纬度信息
	 * 
	 * @param equipmentModel
	 *            设备信息
	 * @return 点坐标信息
	 */
	private PointModel gisObjectQuery(EquipmentModel equipmentModel) {

		PointModel pointModel = new PointModel();

		CoordWebService coordWebServiceLocator = new CoordWebServiceLocator();
		CoordWebServiceSoapBindingStub cSoapBindingStub;

		try {

			cSoapBindingStub = (CoordWebServiceSoapBindingStub) coordWebServiceLocator
					.getCoordWebService();
			cSoapBindingStub.setTimeout(30 * 1000);

			String JsonRequestStr = fillGisQueryReuqest(equipmentModel);

			// 获取json格式响应信息
			String result = cSoapBindingStub.gisObjectQuery(JsonRequestStr);

			JSONObject resJsonObject = JSONObject.fromObject(result);

			// 1: 查询成功
			if ("1".equals(resJsonObject.getString("status"))) {
				if (StringUtils.isNotEmpty(resJsonObject.getString("x"))
						&& StringUtils.isNotEmpty(resJsonObject.getString("y"))) {
					pointModel.setLongitude(resJsonObject.getString("x"));
					pointModel.setLatitude(resJsonObject.getString("y"));
				}
			} else {
				LOGGER.error("Equipment (" + equipmentModel
						+ ") doesn't get longitude or latitude! error msg : "
						+ resJsonObject.getString("msg"));
			}
		} catch (Exception e) {
			LOGGER
					.error(
							"SynchronizePointServiceImpl.gisObjectQuery() got exception. ",
							e);
		}
		return pointModel;
	}

	/**
	 * 封装坐标集中查询接口接口
	 * 
	 * @param equipmentModel
	 *            设备信息
	 * @return json格式请求
	 */
	private String fillGisQueryReuqest(EquipmentModel equipmentModel) {

		JSONObject jsonObject = new JSONObject();
		// 应用Token,标识爱运维系统调用
		jsonObject.put("token", PropertiesUtil.getPropertyString(
				"gisObjectQueryToken", DEFAULT_TOKEN));
		// 对象类型：资源
		jsonObject.put("objTypeID", "3");
		// 资源规格ID
		jsonObject.put("objSubTypeID", equipmentModel.getRes_spec_id());
		// 资源对象唯一ID
		jsonObject.put("objID", equipmentModel.getPhy_eqp_id());
		// 输出坐标系类型,1：本地默认使用的坐标系（54或本地），2：WGS经纬度坐标系，3：百度09坐标系
		jsonObject.put("coordType", "3");
		// 本地网ID，如南京市：3
		jsonObject.put("bss_area_id", equipmentModel.getParent_area_id());
		// 区县ID，查询苏州(20)时必填
		if (equipmentModel.getParent_area_id() == 20) {
			jsonObject.put("bss_area_id_4", equipmentModel.getArea_id());
		}

		return jsonObject.toString();
	}

	/**
	 * 将设备信息保存到INS库tb_ins_point表中
	 * 
	 * @param equipmentModel
	 *            资源设备基本信息
	 * @param pointModel
	 *            资源设备经纬度信息
	 */
	private void saveEquipmentInfo(EquipmentModel equipmentModel,
			PointModel pointModel) {

		try {

			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("phy_eqp_id", equipmentModel.getPhy_eqp_id());
			paramsMap.put("point_no", equipmentModel.getNo());
			paramsMap.put("point_name", equipmentModel.getName());
			// 预留,暂不赋值
			paramsMap.put("point_level", StringUtils.isNotEmpty(equipmentModel.getPoint_level())?equipmentModel.getPoint_level():"");
			// 设备点：4
			paramsMap.put("point_type", 4);
			paramsMap.put("longitude", Double.parseDouble(pointModel
					.getLongitude()));
			paramsMap.put("latitude", Double.parseDouble(pointModel
					.getLatitude()));
			paramsMap.put("area_id", equipmentModel.getParent_area_id());
			
			paramsMap.put("address", StringUtils.isNotEmpty(equipmentModel
					.getAddress()) ? equipmentModel.getAddress() : "");
			paramsMap.put("eqp_type_id", SPEC_ID_MAP.get(equipmentModel
					.getRes_spec_id()));
			paramsMap.put("origin_type", 1);
			paramsMap.put("son_zone", equipmentModel.getSon_zone());
			//默认区域ID
			paramsMap.put("son_area_id", equipmentModel.getArea_id());
			//根据区域名称查找对于的区域ID，子区域转换
			if (null!=equipmentModel.getSon_zone() &&  !"".equals(equipmentModel.getSon_zone()))
			{
				String  sonAreaId =  areaDao.getSonAreaId(paramsMap);
				if(null!=sonAreaId &&  !"".equals(sonAreaId))
					paramsMap.put("son_area_id", sonAreaId);
			}
			synchronizePointDao.saveEquipmentInfo(paramsMap);

		} catch (Exception e) {
			LOGGER.error(
					"SynchronizePointServiceImpl.saveEquipmentInfo() got exception. ("
							+ equipmentModel + " , " + pointModel + ")", e);
		}
	}

	/**
	 * 打印Info级别日志
	 * 
	 * @param message
	 *            日志内容
	 */
	private void InfoLogger(String message) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(message);
		}
	}

	@Override
	public void synchronizedPointLevel() {
		
		InfoLogger("Enter SynchronizePointServiceImpl.synchronizedPointLevel().");

		// 初始化全局变量
		initParams();

			long startTime = System.currentTimeMillis();

			// 构造(分页)请求,首先查出第一页数据
			Query query = fillQuery(jndi, 1);

			List<EquipmentModel> equipmentLevelLst = new ArrayList<EquipmentModel>(
					PAGE_ROWS);

			// 获取本地网设备的设备等级字段
			equipmentLevelLst = getEquipmentInfoList(jndi, query, false);

			// 更新设备信息
			//dealWithEquipmentInfo(equipmentLevelLst, false);

			// 计算页数
			int pageSize = (query.getPager().getRowcount() + (PAGE_ROWS - 1))
					/ PAGE_ROWS;

			// 继续处理其他页设备
			for (int i = 2; i <= pageSize; i++) {
				query.getPager().setPage(i);
				equipmentLevelLst = getEquipmentInfoList(jndi, query, false);
				//dealWithEquipmentInfo(equipmentLevelLst, false);
			}

			InfoLogger("finish synchronized all data for jndi : " + jndi
					+ "! total cost time : "
					+ (System.currentTimeMillis() - startTime) / 1000 + "s");

		InfoLogger("Exit SynchronizePointServiceImpl.synchronizedPointLevel().");
	}
}
