package com.cableCheck.serviceimpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.cableCheck.dao.QuartzJobDao;
import com.cableCheck.service.QuartzJobService;
import com.cableInspection.model.EquipmentModel;
import com.cableInspection.model.OSSEquipmentModel;
import com.cableInspection.model.PointModel;
import com.cableInspection.serviceimpl.SynchronizePointServiceImpl;
import com.cableInspection.webservice.CoordWebService;
import com.cableInspection.webservice.CoordWebServiceLocator;
import com.cableInspection.webservice.CoordWebServiceSoapBindingStub;
import com.linePatrol.util.DateUtil;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;
import com.sun.xml.internal.bind.v2.TODO;
import com.system.dao.AreaDao;
import com.util.FTPUploadJYH;
import com.util.StringUtil;
import com.util.sendMessage.PropertiesUtil;
import com.util.sendMessage.SendMessageUtil;
import com.webservice.client.ElectronArchivesService;

@SuppressWarnings("all")
@Service
public class QuartzJobServiceImpl implements QuartzJobService{

	
	
	@Resource
	ElectronArchivesService electronArchivesService;
	/**
	 * 13个本地网的jndi信息
	 */
	private static List<String> GWZS_JNDI_LIST = null;
	
	/**
	 * 数据源,该数据源可以直接访问13个本地网的数据
	 */
	private static final String JNDI = "cpf83";
	
	/**
	 * 苏州数据源
	 */
	private static final String JNDISZ = "ressz";
	/**
	 * 苏州数据源
	 */
	private static final String JNDINJ = "resnj";
	/**
	 * 苏州数据源
	 */
	private static final String JNDIZJ = "reszj";
	/**
	 * 苏州数据源
	 */
	private static final String JNDINT = "resnt";
	/**
	 * 苏州数据源
	 */
	private static final String JNDIWX = "reswx";
	/**
	 * 苏州数据源
	 */
	private static final String JNDIYZ = "resyz";
	/**
	 * 苏州数据源
	 */
	private static final String JNDIYC = "resyc";
	/**
	 * 苏州数据源
	 */
	private static final String JNDIHA = "resha";
	/**
	 * 苏州数据源
	 */
	private static final String JNDILYG = "reslyg";
	/**
	 * 苏州数据源
	 */
	private static final String JNDIXZ = "resxz";
	/**
	 * 苏州数据源
	 */
	private static final String JNDISQ = "ressq";
	/**
	 * 苏州数据源
	 */
	private static final String JNDITZ = "restz";
	/**
	 * 苏州数据源
	 */
	private static final String JNDICZ = "rescz";
	/**
	 * OSS更纤率报表数据源
	 */
	private static final String JNDIOSS = "ossgxl";
	
	@Resource
	private QuartzJobDao quartzJobDao;
	
	@Resource
	private AreaDao areaDao;

	/**
	 * 设备类型ID的映射关系(OSS<-->INS)
	 */
	private static Map<String, Object> SPEC_ID_MAP = null;

	/**
	 * 分页查询每页查询数量
	 */
	private static final int PAGE_ROWS = 10000;

	/**
	 * 默认应用Token,标识爱运维系统调用
	 */
	private static final String DEFAULT_TOKEN = "PAD_8SAF77804D2BA1322C33E0122109";
	
	/**
	 * 日志服务
	 */
	private static final Logger LOGGER = Logger.getLogger(SynchronizePointServiceImpl.class);
	
	/**
	 * 初始化
	 */
	private void initParams() {

		// 保存设备类型ID的映射关系(OSS<-->INS)
		SPEC_ID_MAP = new HashMap<String, Object>();
		SPEC_ID_MAP.put("703", 1000);
		SPEC_ID_MAP.put("704", 1001);
		SPEC_ID_MAP.put("411", 1002);
		SPEC_ID_MAP.put("414", 1003);
				
		GWZS_JNDI_LIST = new ArrayList<String>();
		GWZS_JNDI_LIST.add("OSSBC_DEV_SQ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_WX");
		GWZS_JNDI_LIST.add("OSSBC_DEV_CZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_ZJ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_NT");
		GWZS_JNDI_LIST.add("OSSBC_DEV_TZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_YZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_SZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_HA");
		GWZS_JNDI_LIST.add("OSSBC_DEV_YC");
		GWZS_JNDI_LIST.add("OSSBC_DEV_LYG");
		GWZS_JNDI_LIST.add("OSSBC_DEV_XZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_NJ");
		
		
	}
	
	@Override
	public void syncOSSChangePorts() {
		// 初始化全局变量
		initParams();
	
		for (String jndi : GWZS_JNDI_LIST) {
			//先创建关系
			Map map =  new HashMap();
			List<Map> list = new ArrayList<Map>();
			List<Map> obdslist = new ArrayList<Map>();
			map.put("jndi", jndi);
			//默认查询前一天的数据
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();// 取当前日期。
			cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
			map.put("endDate", format.format(cal.getTime()));
			cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
			map.put("startDate",format.format(cal.getTime()));
			try{
				//先清理缓存数据
				list.clear();
				//先把OSS变化的端子数据生成好
				SwitchDataSourceUtil.setCurrentDataSource(JNDI);
				quartzJobDao.createOssChangePorts(map);
				SwitchDataSourceUtil.clearDataSource();
				//再查询已经生成好的变动端子的数据
				SwitchDataSourceUtil.setCurrentDataSource(JNDI);
				list = quartzJobDao.queryOssChangePorts();
				//同箱分光器
//				obdslist = quartzJobDao.queryObdsData();
				SwitchDataSourceUtil.clearDataSource();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//			if(obdslist.size()>0 && obdslist != null){
//				for(Map obdsMap:obdslist){
//					if(null != obdsMap.get("PHY_EQP_ID")){
//						quartzJobDao.updateObds(obdsMap);
//					}
//				}
//			}
		}
		
	}
	
	/**
	 * 同步设备
	 */
	@Override
	public void synchronizeEquipmentInfo() {
		// 初始化全局变量
		initParams();
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		int day = Integer.valueOf(sdf.format(new Date()));//取当前时间的日期数字
		//每天执行一次，一次更新一个地市
		Map<Integer,String> jndiMap = new HashMap<Integer,String>();
		jndiMap.put(0, "OSSBC_DEV_NJ");
		jndiMap.put(1, "OSSBC_DEV_SQ");
		jndiMap.put(2, "OSSBC_DEV_WX");
		jndiMap.put(3, "OSSBC_DEV_CZ");
		jndiMap.put(4, "OSSBC_DEV_ZJ");
		jndiMap.put(5, "OSSBC_DEV_NT");
		jndiMap.put(6, "OSSBC_DEV_TZ");
		jndiMap.put(7, "OSSBC_DEV_YZ");
		jndiMap.put(8, "OSSBC_DEV_HA");
		jndiMap.put(9, "OSSBC_DEV_YC");
		jndiMap.put(10, "OSSBC_DEV_LYG");
		jndiMap.put(11, "OSSBC_DEV_XZ");
		jndiMap.put(12, "OSSBC_DEV_SZ");
		String jndi = jndiMap.get(day%13);//取其中一个地市
//		String jndi = jndiMap.get(4);//取其中一个地市
		
		Map map =  new HashMap();
		map.put("jndi", jndi);
		try{//先创建关系
			SwitchDataSourceUtil.setCurrentDataSource(JNDI);
			quartzJobDao.createDynamicOssEquipment(map);
			SwitchDataSourceUtil.clearDataSource();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
		long startTime = System.currentTimeMillis();
		// 构造(分页)请求,首先查出第一页数据
		Query query = fillQuery(jndi, 1);
		// 获取本地网设备表中记录
		List<OSSEquipmentModel> equipmentInfoLst = getEquipmentInfoList(jndi, query, true);
		// 处理设备信息
		dealWithEquipmentInfo(equipmentInfoLst);
		// 计算页数
		int pageSize = (query.getPager().getRowcount() + (PAGE_ROWS - 1)) / PAGE_ROWS;
		// 继续处理其他页设备
		for (int i = 2; i <= pageSize; i++) {
			query.getPager().setPage(i);
			equipmentInfoLst = getEquipmentInfoList(jndi, query, true);
			dealWithEquipmentInfo(equipmentInfoLst);
		}
	}

	/**
	 * 构造分页请求
	 * @param jndi 数据源
	 * @param pageNum 页码
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
		queryParams.put("res_spec_id", sBuffer.substring(0, sBuffer.length() - 1));
		query.setQueryParams(queryParams);
		
		UIPage pager = new UIPage();
		pager.setPage(pageNum);
		pager.setRows(PAGE_ROWS);
		query.setPager(pager);
		return query;
	}

	/**
	 * 分页获取某个本地网设备表中记录
	 * @param jndi 数据源
	 * @param query 请求体
	 * @param fullQuery 是否查询全部字段信息
	 * @return List 设备信息
	 */
	private List<OSSEquipmentModel> getEquipmentInfoList(String jndi, Query query, boolean fullQuery) {

		List<OSSEquipmentModel> equipmentInfoLst = new ArrayList<OSSEquipmentModel>(PAGE_ROWS);
		try {
			// 切换数据源
			//SwitchDataSourceUtil.setCurrentDataSource(JNDI);
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			// 获取分页信息,此时拦截器会返回总记录数到Query对象
//			equipmentInfoLst = quartzJobDao.queryEquipmentList(query);
			equipmentInfoLst = quartzJobDao.queryDynamicOssEquipments(query);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("SynchronizePointServiceImpl.getEquipmentInfoList() got exception.", e);
		} finally {
			SwitchDataSourceUtil.clearDataSource();
		}

		return equipmentInfoLst;
	}

	/**
	 * 将oss设备信息插入/更新到tb_cablecheck_equipment表；
	 * @param equipmentInfoLst 本地网设备信息
	 * @param saveOrUpdate 新增设备点记录或更新设备点记录
	 */
	private void dealWithEquipmentInfo(List<OSSEquipmentModel> equipmentInfoLst) {
		if (CollectionUtils.isEmpty(equipmentInfoLst)) {
			return;
		}
		for (OSSEquipmentModel equipmentModel : equipmentInfoLst) {
			//根据设备编码和id判断设备是否已经存在，若已存在，更新设备；若不存在，新增设备
			Map paras = new HashMap();
			paras.put("equipment_code", equipmentModel.getEquipment_code());
			paras.put("equipment_id", equipmentModel.getEquipment_id());
			paras.put("parent_area_id", equipmentModel.getParent_area_id());
			List<Map> eqps = quartzJobDao.isEquipmentExistNew(paras);
			//更新设备，若30天内已更新过，则不更新
			if (eqps.size()>0) {
				int OVER_SYNC_TIME = null==eqps.get(0).get("OVER_SYNC_TIME")?-1:Integer.valueOf(eqps.get(0).get("OVER_SYNC_TIME").toString());//距上次同步间隔天数
				if(OVER_SYNC_TIME>30 || OVER_SYNC_TIME==-1){//超过30天 或 第一次同步update_time值为空
					paras.put("longitude", "");
					paras.put("latitude", "");
					paras.put("station_id", null==equipmentModel.getStation_id()?"":equipmentModel.getStation_id());
					paras.put("parent_area_id", null==equipmentModel.getParent_area_id()?"":equipmentModel.getParent_area_id());
					paras.put("create_date", equipmentModel.getCreate_date());
					paras.put("address", null==equipmentModel.getAddress()?"":equipmentModel.getAddress());
					paras.put("equipment_name", null==equipmentModel.getEquipment_name()?"":equipmentModel.getEquipment_name());
					paras.put("manage_area_id", null==equipmentModel.getManage_area_id()?"":equipmentModel.getManage_area_id());
					paras.put("manage_area", null==equipmentModel.getManage_area()?"":equipmentModel.getManage_area());
					paras.put("management_mode", null==equipmentModel.getManagement_mode()?"":equipmentModel.getManagement_mode());
					paras.put("install_sbid", null==equipmentModel.getInstall_sbid()?"":equipmentModel.getInstall_sbid());
					paras.put("install_sbbm", null==equipmentModel.getInstall_sbbm()?"":equipmentModel.getInstall_sbbm());
					quartzJobDao.updateEquipmentLevel(paras);
				}
			}else{//新增
				try {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("equipment_id", equipmentModel.getEquipment_id());
					paramsMap.put("equipment_code", equipmentModel.getEquipment_code());
					paramsMap.put("equipment_name", equipmentModel.getEquipment_name());
					paramsMap.put("area_id", equipmentModel.getArea_id());
					paramsMap.put("address", equipmentModel.getAddress());
					paramsMap.put("res_type_id", equipmentModel.getRes_type_id());
					paramsMap.put("res_type", equipmentModel.getRes_type());
					paramsMap.put("manage_area_id", equipmentModel.getManage_area_id());
					paramsMap.put("manage_area", equipmentModel.getManage_area());
					paramsMap.put("station_id", null==equipmentModel.getStation_id()?"":equipmentModel.getStation_id());
					paramsMap.put("management_mode", equipmentModel.getManagement_mode());
					paramsMap.put("isrelated", equipmentModel.getIsrelated());
					paramsMap.put("staff_id", equipmentModel.getStaff_id());
					paramsMap.put("create_date", equipmentModel.getCreate_date());
					paramsMap.put("operate_staff", equipmentModel.getOperate_staff());
					paramsMap.put("parent_area_id", equipmentModel.getParent_area_id());
					paramsMap.put("grid_id", equipmentModel.getGrid_id());
					paramsMap.put("install_sbid", null==equipmentModel.getInstall_sbid()?"":equipmentModel.getInstall_sbid());
					paramsMap.put("install_sbbm", null==equipmentModel.getInstall_sbbm()?"":equipmentModel.getInstall_sbbm());
					paramsMap.put("install_dzbm", equipmentModel.getInstall_dzbm());
					paramsMap.put("longitude", "");
					paramsMap.put("latitude", "");
					quartzJobDao.saveEquipmentInfo(paramsMap);
				} catch (Exception e) {
					LOGGER.error("SynchronizePointServiceImpl.saveEquipmentInfo() got exception. (" + equipmentModel + ")", e);
				}
			}
		}
	}
	/**
	 * 调用webservice接口查询设备经纬度信息
	 * @param equipmentModel设备信息
	 * @return 点坐标信息
	 */
	private PointModel gisObjectQuery(OSSEquipmentModel equipmentModel) {

		PointModel pointModel = new PointModel();
		CoordWebService coordWebServiceLocator = new CoordWebServiceLocator();
		CoordWebServiceSoapBindingStub cSoapBindingStub;
		try {
			cSoapBindingStub = (CoordWebServiceSoapBindingStub) coordWebServiceLocator.getCoordWebService();
			cSoapBindingStub.setTimeout(30 * 1000);

			String JsonRequestStr = fillGisQueryReuqest(equipmentModel);
			// 获取json格式响应信息
			String result = cSoapBindingStub.gisObjectQuery(JsonRequestStr);
			JSONObject resJsonObject = JSONObject.fromObject(result);
			// 1: 查询成功
			if ("1".equals(resJsonObject.getString("status"))) {
				if (StringUtils.isNotEmpty(resJsonObject.getString("x")) && StringUtils.isNotEmpty(resJsonObject.getString("y"))) {
					pointModel.setLongitude(resJsonObject.getString("x"));
					pointModel.setLatitude(resJsonObject.getString("y"));
				}
			} else {
				LOGGER.error("Equipment (" + equipmentModel+ ") doesn't get longitude or latitude! error msg : "+ resJsonObject.getString("msg"));
			}
		} catch (Exception e) {
			LOGGER.error("SynchronizePointServiceImpl.gisObjectQuery() got exception. ",e);
		}
		return pointModel;
	}

	/**
	 * 封装坐标集中查询接口接口
	 * @param equipmentModel 设备信息
	 * @return json格式请求
	 */
	private String fillGisQueryReuqest(OSSEquipmentModel equipmentModel) {

		JSONObject jsonObject = new JSONObject();
		// 应用Token,标识爱运维系统调用
		jsonObject.put("token", PropertiesUtil.getPropertyString("gisObjectQueryToken", DEFAULT_TOKEN));
		// 对象类型：资源
		jsonObject.put("objTypeID", "3");
		// 资源对象唯一ID
		if("411".equals(equipmentModel.getRes_type_id())){//ODF
			String station_id = null==equipmentModel.getStation_id()?"":equipmentModel.getStation_id();
			jsonObject.put("objID", station_id);//所在局站的id
			jsonObject.put("objSubTypeID", "201");//局站的资源类型
		}else{
			jsonObject.put("objID", equipmentModel.getEquipment_id());
			jsonObject.put("objSubTypeID", equipmentModel.getRes_type_id());// 资源规格ID
		}
		// 输出坐标系类型,1：本地默认使用的坐标系（54或本地），2：WGS经纬度坐标系，3：百度09坐标系
		jsonObject.put("coordType", "3");
		// 本地网ID，如南京市：3
		jsonObject.put("bss_area_id", equipmentModel.getParent_area_id());
		// 区县ID，查询苏州(20)时必填
		if ("20".equals(equipmentModel.getParent_area_id())) {
			jsonObject.put("bss_area_id_4", equipmentModel.getArea_id());
		}
		return jsonObject.toString();
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
	
	/**
	 * 同步苏州动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsSZ() {
		  
			//先创建关系
			Map map =  new HashMap();
			List<Map> list = new ArrayList<Map>();
			List<Map> obdslist = new ArrayList<Map>();
			//默认查询四天前的数据
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();// 取当前日期。
			cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
			map.put("endDate", format.format(cal.getTime()));
			cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
			map.put("startDate",format.format(cal.getTime()));
			try{
				//先清理缓存数据
				list.clear();
				//先把OSS变化的端子数据生成好
				SwitchDataSourceUtil.setCurrentDataSource(JNDISZ);
				quartzJobDao.createOssChangePortsSZ(map);
				System.out.println(DateUtil.getDateAndTime()+":生成苏州动态端子数据");
				SwitchDataSourceUtil.clearDataSource();
				//再查询已经生成好的变动端子的数据
				SwitchDataSourceUtil.setCurrentDataSource(JNDISZ);
				list = quartzJobDao.queryOssChangePorts();
				//同箱分光器
//				obdslist = quartzJobDao.queryObdsData();
				SwitchDataSourceUtil.clearDataSource();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			try{
				for(Map portMap:list)
				{
					quartzJobDao.insertDtsj(portMap);
				}
				System.out.println(DateUtil.getDateAndTime()+":苏州端子插入成功");
				}catch (Exception e) {
					System.out.println(DateUtil.getDateAndTime()+":苏州动态端子插入失败");
				}
			//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//			if(obdslist.size()>0 && obdslist != null){
//				for(Map obdsMap:obdslist){
//					if(null != obdsMap.get("PHY_EQP_ID")){
//						quartzJobDao.updateObds(obdsMap);
//					}
//				}
//			}
		
		    
	}
	
	
	/**
	 * 同步南京动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsNJ() {
		List<Map> list = new ArrayList<Map>();
		//先创建关系
		Map map =  new HashMap();
	
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		//map.put("endDate", "2017-07-19");
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		//map.put("startDate","2017-07-18");
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDINJ);
			quartzJobDao.createOssChangePortsNJ(map);
			System.out.println(DateUtil.getDateAndTime()+":生成南京动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDINJ);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}try{
		for(Map portMap:list)
		{
			quartzJobDao.insertDtsj(portMap);
		}
		System.out.println(DateUtil.getDateAndTime()+":南京端子插入成功");
		}catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":南京动态端子插入失败");
		}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    
}
	
	
	/**
	 * 同步镇江动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsZJ() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDIZJ);
			quartzJobDao.createOssChangePortsZJ(map);
			System.out.println(DateUtil.getDateAndTime()+":生成镇江动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDIZJ);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":镇江端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":镇江动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

		    
	}
	
	
	/**
	 * 同步无锡动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsWX() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDIWX);
			quartzJobDao.createOssChangePortsWX(map);
			System.out.println(DateUtil.getDateAndTime()+":生成无锡动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDIWX);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":无锡端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":无锡动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

	}
	
	
	/**
	 * 同步南通动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsNT() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDINT);
			quartzJobDao.createOssChangePortsNT(map);
			System.out.println(DateUtil.getDateAndTime()+":生成南通动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDINT);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":南通端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":南通动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

	}
	
	
	/**
	 * 同步扬州动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsYZ() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDIYZ);
			quartzJobDao.createOssChangePortsYZ(map);
			System.out.println(DateUtil.getDateAndTime()+":生成扬州动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDIYZ);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":扬州端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":扬州动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

		    
	}
	
	
	/**
	 * 同步盐城动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsYC() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDIYC);
			quartzJobDao.createOssChangePortsYC(map);
			System.out.println(DateUtil.getDateAndTime()+":生成盐城动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDIYC);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":盐城端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":盐城动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

		    
	}
	
	/**
	 * 同步徐州动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsXZ() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDIXZ);
			quartzJobDao.createOssChangePortsXZ(map);
			System.out.println(DateUtil.getDateAndTime()+":生成徐州动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDIXZ);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":徐州端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":徐州动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

		    
	}
	
	
	/**
	 * 同步淮安动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsHA() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDIHA);
			quartzJobDao.createOssChangePortsHA(map);
			System.out.println(DateUtil.getDateAndTime()+":生成淮安动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDIHA);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":淮安端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":淮安动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

	}
	
	/**
	 * 同步连云港动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsLYG() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDILYG);
			quartzJobDao.createOssChangePortsLYG(map);
			System.out.println(DateUtil.getDateAndTime()+":生成连云港动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDILYG);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":连云港端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":连云港动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

		    
	}
	
	/**
	 * 同步宿迁动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsSQ() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDISQ);
			quartzJobDao.createOssChangePortsSQ(map);
			System.out.println(DateUtil.getDateAndTime()+":生成宿迁动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDISQ);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":宿迁端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":宿迁动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

		    
	}
	
	
	/**
	 * 同步泰州动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsTZ() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDITZ);
			quartzJobDao.createOssChangePortsTZ(map);
			System.out.println(DateUtil.getDateAndTime()+":生成泰州动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDITZ);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":泰州端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":泰州动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

	}
	
	
	/**
	 * 同步常州动态端子
	 * 
	 * 
	 * @param message
	 *            日志内容
	 */
	@Override
	public void syncOSSChangePortsCZ() {
		  

		  
		//先创建关系
		Map map =  new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> obdslist = new ArrayList<Map>();
		//默认查询四天前的数据
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();// 取当前日期。
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);// 取当前日期的前一天.
		map.put("endDate", format.format(cal.getTime()));
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("startDate",format.format(cal.getTime()));
		try{
			//先清理缓存数据
			list.clear();
			//先把OSS变化的端子数据生成好
			SwitchDataSourceUtil.setCurrentDataSource(JNDICZ);
			quartzJobDao.createOssChangePortsCZ(map);
			System.out.println(DateUtil.getDateAndTime()+":生成常州动态端子数据");
			SwitchDataSourceUtil.clearDataSource();
			//再查询已经生成好的变动端子的数据
			SwitchDataSourceUtil.setCurrentDataSource(JNDICZ);
			list = quartzJobDao.queryOssChangePorts();
			//同箱分光器
//			obdslist = quartzJobDao.queryObdsData();
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		try{
			for(Map portMap:list)
			{
				quartzJobDao.insertDtsj(portMap);
			}
			System.out.println(DateUtil.getDateAndTime()+":常州端子插入成功");
			}catch (Exception e) {
				System.out.println(DateUtil.getDateAndTime()+":常州动态端子插入失败");
			}
		//将oss同箱分光器数据update到osspad.tb_cablecheck_equipment表中
//		if(obdslist.size()>0 && obdslist != null){
//			for(Map obdsMap:obdslist){
//				if(null != obdsMap.get("PHY_EQP_ID")){
//					quartzJobDao.updateObds(obdsMap);
//				}
//			}
//		}
	
	    

	}
	
	
	@Override
	public void synchronizeEqpWljb() {
		System.out.println(DateUtil.getDateAndTime()+":同步网络级别");
		try{
			quartzJobDao.synchronizeEqpWljb();
		}
		catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":网络级别同步失败");
		}finally{
			System.out.println("同步结束");
		}
		
		
	}

	@Override
	public void synchronizeElectronArchives() {
		System.out.println(DateUtil.getDateAndTime()+":同步电子档案库");
		try{
		quartzJobDao.synchronizeElectronArchives();}
		catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":电子档案库失败");
		}finally{
			System.out.println("同步结束");
		}
		
		
	}
	
	@Override
	public void synchronizeEqpContract() {
		System.out.println(DateUtil.getDateAndTime()+":同步承包人");
		try{
			quartzJobDao.synchronizeEqpContract();
		}
		catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":承包人数据同步失败");
		}finally{
			System.out.println("同步结束");
		}
		
		
	}

	


	
	/**
	 * TODO 同步OSS更纤率报表
	 */
	@Override
	public void synchronizeOSSGXL() {
		try{
			//quartzJobDao.backupOSSGxl();
			quartzJobDao.truncateOSSGxl();
			// 构造(分页)请求,首先查出第一页数据
			Query query = fillQuery2(1);
			// 获取本地网设备表中记录
			List<Map> list = getOSSGxlList(query, true);
			// 处理设备信息
			dealWithlist(list);
			// 计算页数
			int pageSize = (query.getPager().getRowcount() + (PAGE_ROWS - 1)) / PAGE_ROWS;
			// 继续处理其他页设备
			for (int i = 2; i <= pageSize; i++) {
				query.getPager().setPage(i);
				list = getOSSGxlList(query, true);
				dealWithlist(list);
			}
			//quartzJobDao.deleteBackup();
			System.out.println(DateUtil.getDateAndTime()+":更新率成功");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(DateUtil.getDateAndTime()+":更纤率失败");
		}
		
	}
	
	private void dealWithlist(List<Map> list) {
		try {
			for (Map eqp : list) {
				quartzJobDao.insertList(eqp);
			}
		} catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":更纤率插入失敗");
			e.printStackTrace();
		}
		
		
	}

	private Query fillQuery2( Integer pageNum) {
		// 构造(分页)请求
		Query query = new Query();
		UIPage pager = new UIPage();
		pager.setPage(pageNum);
		pager.setRows(PAGE_ROWS);
		query.setPager(pager);
		return query;
	}

	private List getOSSGxlList(Query query, boolean fullQuery) {

		List<Map> equipmentInfoLst = new ArrayList(PAGE_ROWS);
		try {
			// 切换数据源
			SwitchDataSourceUtil.setCurrentDataSource(JNDIOSS);
			// 获取分页信息,此时拦截器会返回总记录数到Query对象
			equipmentInfoLst = quartzJobDao.queryOssGxl(query);
		} catch (Exception e) {
			LOGGER.error("synchronizeOSSGXL.getOSSGxlList() got exception.", e);
		} finally {
			SwitchDataSourceUtil.clearDataSource();
		}

		return equipmentInfoLst;
	}
	
	
	
	
	/**
	 * TODO 同步OSS无资源响应工单
	 */
	@Override
	public void synchronizeNoRes() {
		try{
			System.out.println(DateUtil.getDateAndTime()+":开始同步无资源");
			//quartzJobDao.backupNoRes();
			quartzJobDao.truncateNoRes();
			// 构造(分页)请求,首先查出第一页数据
			Query query = fillQuery3(1);
			// 获取本地网设备表中记录
			List<Map> list = getNoResList(query, true);
			// 处理设备信息
			dealWithNoReslist(list);
			// 计算页数
			int pageSize = (query.getPager().getRowcount() + (PAGE_ROWS - 1)) / PAGE_ROWS;
			// 继续处理其他页设备
			for (int i = 2; i <= pageSize; i++) {
				query.getPager().setPage(i);
				list = getNoResList(query, true);
				dealWithNoReslist(list);
			}
			//quartzJobDao.deleteResBackup();
			System.out.println(DateUtil.getDateAndTime()+":无资源成功");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(DateUtil.getDateAndTime()+":无资源失败");
			
		}
		
	}
	
	private void dealWithNoReslist(List<Map> list) {
		try {
			for (Map eqp : list) {
				quartzJobDao.insertNoResList(eqp);
			}
		} catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":无资源响应插入失敗");
			e.printStackTrace();
		}
		
		
	}

	private Query fillQuery3( Integer pageNum) {
		// 构造(分页)请求
		Query query = new Query();
		UIPage pager = new UIPage();
		pager.setPage(pageNum);
		pager.setRows(PAGE_ROWS);
		query.setPager(pager);
		return query;
	}

	private List getNoResList(Query query, boolean fullQuery) {

		List<Map> equipmentInfoLst = new ArrayList(PAGE_ROWS);
		try {
			// 切换数据源
			SwitchDataSourceUtil.setCurrentDataSource(JNDIOSS);
			// 获取分页信息,此时拦截器会返回总记录数到Query对象
			equipmentInfoLst = quartzJobDao.queryNoRes(query);
		} catch (Exception e) {
			LOGGER.error("synchronizeOSSGXL.getOSSNoResList() got exception.", e);
		} finally {
			SwitchDataSourceUtil.clearDataSource();
		}

		return equipmentInfoLst;
	}

	@Override
	public void syncMONTH() {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getDateAndTime()+":同步月度资源数据");
try{
		quartzJobDao.createMONTH();
}catch (Exception e) {
	System.out.println(DateUtil.getDateAndTime()+":月度资源失敗");
	e.printStackTrace();
}
	}
	@Override
	public void syncQJST() {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getDateAndTime()+":同步全景视图数据");
try{
		quartzJobDao.createQJST();
}catch (Exception e) {
	System.out.println(DateUtil.getDateAndTime()+":全景视图失敗");
	e.printStackTrace();
}
	}
	
	
	
	@Override
	public void syncODSO() {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getDateAndTime()+":同步ODSO数据");
		try{
			quartzJobDao.createSYNC_APP_IOM_ORDER_OSS_INFO();
			quartzJobDao.createSYNC_odso_eqp_daily();
			quartzJobDao.createSYNC_APP_PROD_ACCSNO_EQP();
		quartzJobDao.createQJODSO();
		quartzJobDao.createWANGGE();
		}catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":ODSO失敗");
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
	public void syncOSSOBD() {
		// 初始化全局变量
		System.out.println(DateUtil.getDateAndTime()+":同步末级分光器数据");
	
		initParams();
		Map map =  new HashMap();
		quartzJobDao.truncateOBD();
	
			List<Map> list = new ArrayList<Map>();
			
			try{
				//先清理缓存数据
				list.clear();
				SwitchDataSourceUtil.setCurrentDataSource(JNDIOSS);
				quartzJobDao.createOssOBD();
				SwitchDataSourceUtil.clearDataSource();
				
				Query query = fillQuery4( 1);
				// 获取本地网设备表中记录
				list = queryOssOBD( query, true);
				// 处理设备信息
				dealWithOBDInfo(list);
				// 计算页数
				int pageSize = (query.getPager().getRowcount() + (PAGE_ROWS - 1)) / PAGE_ROWS;
				// 继续处理其他页设备
				for (int i = 2; i <= pageSize; i++) {
					query.getPager().setPage(i);
					list = queryOssOBD( query, true);
					dealWithOBDInfo(list);
				}
			}
				
				

				
			
			catch (Exception e) {
				e.printStackTrace();
				System.out.println(DateUtil.getDateAndTime()+":末级分光器数据失敗");
				
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			
			
			
		
		
	}
	/**
	 * 构造分页请求
	 * @param jndi 数据源
	 * @param pageNum 页码
	 * @return Query 请求体
	 */
	private Query fillQuery4(Integer pageNum) {

		// 构造(分页)请求
		Query query = new Query();

		Map<String, String> queryParams = new HashMap<String, String>();
		query.setQueryParams(queryParams);	
		UIPage pager = new UIPage();
		pager.setPage(pageNum);
		pager.setRows(PAGE_ROWS);
		query.setPager(pager);
		return query;
	}
	private List queryOssOBD(Query query, boolean fullQuery) {

		List<Map> equipmentInfoLst = new ArrayList(PAGE_ROWS);
		try {
			// 切换数据源
		
			SwitchDataSourceUtil.setCurrentDataSource(JNDIOSS);
			// 获取分页信息,此时拦截器会返回总记录数到Query对象
//			equipmentInfoLst = quartzJobDao.queryEquipmentList(query);
			equipmentInfoLst = quartzJobDao.queryOssOBD(query);
			SwitchDataSourceUtil.clearDataSource();

		} catch (Exception e) {
			LOGGER.error("SynchronizeQuartJobImpl.getOBDList() got exception.", e);
		} finally {
			SwitchDataSourceUtil.clearDataSource();
		}

		return equipmentInfoLst;
	}

	
	
	private void dealWithOBDInfo(List<Map> list) {
		try{
			for(Map obdMap:list)
		{
			quartzJobDao.insertBOD(obdMap);
		}
		
	} catch (Exception e) {
		System.out.println(DateUtil.getDateAndTime()+":OBD插入失敗");
		e.printStackTrace();
	}
	}
	
	
	@Override
	public void syncResRat() {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getDateAndTime()+":定时资源准确率数据");
		try{
		quartzJobDao.syncResRat();
		}catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":定时资源准确率失敗");
			e.printStackTrace();
		}
	}
	
	@Override
	public void syncCvs() {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getDateAndTime()+":定时综调13地市数据");
		try{
		quartzJobDao.syncCvs();
		}catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":定时综调13地市数据失敗");
			e.printStackTrace();
		}
	}
	
	@Override
	public void syncIom() {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getDateAndTime()+":定时Iom13地市数据");
		try{
		quartzJobDao.syncIom();
		}catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":定时Iom13地市数据失敗");
			e.printStackTrace();
		}
	}
	/*
	 * 新的同步设备方式
	 */
	@Override
	public void synchronizeEquipment() {
		// TODO Auto-generated method stub
		initParams();
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		int day = Integer.valueOf(sdf.format(new Date()));//取当前时间的日期数字
		//每天执行一次，一次更新一个地市
		Map<Integer,String> jndiMap = new HashMap<Integer,String>();
		jndiMap.put(0, "resnj");
		jndiMap.put(1, "ressq");
		jndiMap.put(2, "reswx");
		jndiMap.put(3, "rescz");
		jndiMap.put(4, "reszj");
		jndiMap.put(5, "resnt");
		jndiMap.put(6, "restz");
		jndiMap.put(7, "resyz");
		jndiMap.put(8, "resha");
		jndiMap.put(9, "resyc");
		jndiMap.put(10, "reslyg");
		jndiMap.put(11, "resxz");
		jndiMap.put(12, "ressz");
		String jndi = jndiMap.get(day%13);//取其中一个地市
		//String jndi = jndiMap.get(5);//取其中一个地市 南通
		
		Map map =  new HashMap();
		map.put("jndi", jndi);
		System.out.println("准备数据开始");
		try{//先创建关系
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			quartzJobDao.createDynamicEquipment();
			SwitchDataSourceUtil.clearDataSource();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			SwitchDataSourceUtil.clearDataSource();
		}
		System.out.println("准备数据结束");
		
		long startTime = System.currentTimeMillis();
		// 构造(分页)请求,首先查出第一页数据
		Query query = fillQuery(jndi, 1);
		// 获取本地网设备表中记录
		List<OSSEquipmentModel> equipmentInfoLst = getEquipmentInfoList(jndi, query, true);
		// 处理设备信息
		dealWithEquipmentInfo(equipmentInfoLst);
		// 计算页数
		int pageSize = (query.getPager().getRowcount() + (PAGE_ROWS - 1)) / PAGE_ROWS;
		// 继续处理其他页设备
		for (int i = 2; i <= pageSize; i++) {
			query.getPager().setPage(i);
			equipmentInfoLst = getEquipmentInfoList(jndi, query, true);
			dealWithEquipmentInfo(equipmentInfoLst);
		}
		System.out.println("设备更新完成或插入完成");
	}

	
	@Override
	public void reviewTaskToStaff() {
		System.out.println(DateUtil.getDateAndTime()+":任务超时预警，催促执行");
		try{
			int dayNum = 2;//表示查询2天后过期的task
			List<Map> list = quartzJobDao.selectExpireTask(dayNum);
			for (Map map : list) {
				String taskName = isValue(map,"TASK_NAME");//任务名称
				String tel = isValue(map,"TEL"); //联系方式
				String inspector = isValue(map,"INSPECTOR");//检查人
				String staffName = isValue(map,"STAFF_NAME");//检查人姓名
				String planStartTime = isValue(map,"PLAN_START_TIME");//计划开始时间
				String planEndTime = isValue(map,"PLAN_START_TIME");//计划结束时间
				
				tel = "15850522050";//測試手機使用
				sendMessage(taskName, tel, staffName);
			}
			if(list.size()==0){
				sendMessage("selectExpireTask方法没有取到任何值", "15850522050", "测试账号");
			}
		}catch (Exception e) {
			System.out.println(DateUtil.getDateAndTime()+":任务超时预警，催促执行 ,失败");
			e.printStackTrace();
		}
	}

	private void sendMessage(String taskName, String tel, String staffName) {
		try {
			//调用发短信的方法即可,判断返回值是否成功或者失败
			String messageText = staffName + "，您好。您的 " + taskName + " 任务即将结束，请及时执行。【光网助手】";
			
			//2017年9月25日09:31:42 这个单个的发送短信的方法 有问题
			//SendMessageUtil.sendMessage(tel,messageText);
			
			List phoneNumList = new ArrayList();
			phoneNumList.add(tel);
			List messageContentList = new ArrayList();
			messageContentList.add(messageText);
			SendMessageUtil.sendMessageList(phoneNumList, messageContentList);
			
			
		} catch (Exception e) {
			System.out.println(
					String.format("调用发短信的方法报错，参数是   手机号：%s ; 人员姓名: %s ; 任务名称：%s",
							tel,staffName,taskName));
			e.printStackTrace();
		}
	}
	
	public String isValue(Map map,String key){
		String str = "";
		if(map.containsKey(key) && map.get(key)!=null 
				&& StringUtils.isNotBlank(map.get(key).toString())){
			str = map.get(key).toString();
		}
		return str;
	}
	
	@Override
	public void calOrderNum(){
		Map map =new HashMap();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("stateTime",format.format(cal.getTime()));
		try {
			quartzJobDao.calOrderNum(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		int size = 120;
//		for (int i = 1; i <= size; i++) {
//			cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
//			map.put("stateTime",format.format(cal.getTime()));
//			try {
//				quartzJobDao.calOrderNum(map);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		
	}
	
	
	@Override
	public void calOrderChangeNum(){
		Map map =new HashMap();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("stateTime",format.format(cal.getTime()));
		try {
			quartzJobDao.calOrderChange(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		int size = 120;
//		for (int i = 1; i <= size; i++) {
//			cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
//			map.put("stateTime",format.format(cal.getTime()));
//			try {
//				quartzJobDao.calOrderChange(map);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	@Override
	public void calCheckError(){
		Map map =new HashMap();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("stateTime",format.format(cal.getTime()));
		try {
			quartzJobDao.calCheckError(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		int size = 120;
//		for (int i = 1; i <= size; i++) {
//			cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
//			map.put("stateTime",format.format(cal.getTime()));
//			try {
//				quartzJobDao.calCheckError(map);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	
	@Override
	public void calTeamOrder(){
		Map map =new HashMap();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("stateTime",format.format(cal.getTime()));
		try {
			quartzJobDao.calTeamOrder(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		int size = 120;
//		for (int i = 1; i <= size; i++) {
//			cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
//			map.put("stateTime",format.format(cal.getTime()));
//			try {
//				quartzJobDao.calTeamOrder(map);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	@Override
	public void calTeamCheck(){
		Map map =new HashMap();
		map.put("daynum",1);
		try {
			quartzJobDao.calTeamCheck(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		int size = 120;
//		for (int i = 1; i <= size; i++) {
//			map.put("daynum",i);
//			try {
//				quartzJobDao.calTeamCheck(map);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	
	@Override
	public void calGridOrder(){
		Map map =new HashMap();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		map.put("stateTime",format.format(cal.getTime()));
		try {
			quartzJobDao.calGridOrder(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		int size = 120;
//		for (int i = 1; i <= size; i++) {
//			cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
//			map.put("stateTime",format.format(cal.getTime()));
//			try {
//				quartzJobDao.calGridOrder(map);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	/* (non-Javadoc)
	 * @see com.cableCheck.service.QuartzJobService#queryAreaPortNum()
	 * 统计前一天变动端子的情况
	 */
	@Override
	public void queryAreaPortNum() {
		Map map = new HashMap();
		String areaName = "";
		String areaPortNum = "";
		//查询前一天全省每个地市变动端子情况
		List<Map> numList=  quartzJobDao.queryAreaPortNum(map);
		String sendTXT = "";
		for (int i = 0; i < numList.size(); i++) {
			map = numList.get(i);
			if(null== map.get("NAME") || "".equals(map.get("NAME").toString()))
			{
				return;
			}
			areaName = map.get("NAME").toString(); 
			areaPortNum = null ==map.get("NUM")?"0":map.get("NUM").toString(); 
			sendTXT+= areaName +":"+areaPortNum+";";
		}
		 //对配置的接收短信人员发送短信
		List phoneList= PropertiesUtil.getPropertyToList("areaPortNumber");
		for (int i = 0; i < phoneList.size(); i++) 
		{
			try {
				SendMessageUtil.sendMessageInfo(phoneList.get(i).toString(),sendTXT,"20");
			} catch (Exception e) {
	//			System.out.println(String.format("调用发短信的方法报错，参数是   手机号：%s ; 人员姓名: %s ; 任务名称：%s",tel,staffName,taskName));
				e.printStackTrace();
			}
		}
	}

	@Override
	public void queryFtpDir() {
		// TODO Auto-generated method stub
		List<Map<String,String>> dirList=  quartzJobDao.getFtpDir();
		String path="";
		FTPUploadJYH f = new FTPUploadJYH(null,null,0,null,null);
		try {
			f.connect();
			for(Map<String,String> dirMap:dirList){
				path=dirMap.get("DIR_NAME");
				f.makeDirectory(3, path);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 将oss设备信息插入/更新到tb_cablecheck_equipment表；
	 * @param equipmentInfoLst 本地网设备信息
	 * @param saveOrUpdate 新增设备点记录或更新设备点记录
	 */
	private void dealWithEquipmentInfoNew(List<OSSEquipmentModel> equipmentInfoLst) {
		if (CollectionUtils.isEmpty(equipmentInfoLst)) {
			return;
		}
		for (OSSEquipmentModel equipmentModel : equipmentInfoLst) {
			//根据设备编码和id判断设备是否已经存在，若已存在，更新设备；若不存在，新增设备
			Map paras = new HashMap();
			paras.put("parent_area_id", equipmentModel.getParent_area_id());//地市
			paras.put("equipment_code", equipmentModel.getEquipment_code());//设备编码
			paras.put("equipment_id", equipmentModel.getEquipment_id());//设备ID
			List<Map> eqps = quartzJobDao.isEquipmentExistNew(paras);
			//更新设备，若30天内已更新过，则不更新
			if (eqps.size()>0) {
				int OVER_SYNC_TIME = null==eqps.get(0).get("OVER_SYNC_TIME")?-1:Integer.valueOf(eqps.get(0).get("OVER_SYNC_TIME").toString());//距上次同步间隔天数
				if(OVER_SYNC_TIME>30 || OVER_SYNC_TIME==-1){//超过30天 或 第一次同步update_time值为空
					paras.put("longitude", "");
					paras.put("latitude", "");
					paras.put("station_id", null==equipmentModel.getStation_id()?"":equipmentModel.getStation_id());
					paras.put("parent_area_id", null==equipmentModel.getParent_area_id()?"":equipmentModel.getParent_area_id());
					paras.put("create_date", equipmentModel.getCreate_date());
					paras.put("address", null==equipmentModel.getAddress()?"":equipmentModel.getAddress());
					paras.put("equipment_name", null==equipmentModel.getEquipment_name()?"":equipmentModel.getEquipment_name());
					
					paras.put("manage_area_id", null==equipmentModel.getManage_area_id()?"":equipmentModel.getManage_area_id());
					paras.put("manage_area", null==equipmentModel.getManage_area()?"":equipmentModel.getManage_area());
					paras.put("management_mode", null==equipmentModel.getManagement_mode()?"":equipmentModel.getManagement_mode());
					paras.put("install_sbid", null==equipmentModel.getInstall_sbid()?"":equipmentModel.getInstall_sbid());
					paras.put("install_sbbm", null==equipmentModel.getInstall_sbbm()?"":equipmentModel.getInstall_sbbm());
				
					quartzJobDao.updateEquipmentLevel(paras);
				}
			}else{//新增
				try {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("equipment_id", equipmentModel.getEquipment_id());
					paramsMap.put("equipment_code", equipmentModel.getEquipment_code());
					paramsMap.put("equipment_name", equipmentModel.getEquipment_name());
					paramsMap.put("area_id", equipmentModel.getArea_id());
					paramsMap.put("address", equipmentModel.getAddress());
					paramsMap.put("res_type_id", equipmentModel.getRes_type_id());
					paramsMap.put("res_type", equipmentModel.getRes_type());
					paramsMap.put("manage_area_id", equipmentModel.getManage_area_id());
					paramsMap.put("manage_area", equipmentModel.getManage_area());
					paramsMap.put("station_id", null==equipmentModel.getStation_id()?"":equipmentModel.getStation_id());
					paramsMap.put("management_mode", equipmentModel.getManagement_mode());
					paramsMap.put("isrelated", equipmentModel.getIsrelated());
					paramsMap.put("staff_id", equipmentModel.getStaff_id());
					paramsMap.put("create_date", equipmentModel.getCreate_date());
					paramsMap.put("operate_staff", equipmentModel.getOperate_staff());
					paramsMap.put("parent_area_id", equipmentModel.getParent_area_id());
					paramsMap.put("grid_id", equipmentModel.getGrid_id());
					paramsMap.put("install_sbid", null==equipmentModel.getInstall_sbid()?"":equipmentModel.getInstall_sbid());
					paramsMap.put("install_sbbm", null==equipmentModel.getInstall_sbbm()?"":equipmentModel.getInstall_sbbm());
					paramsMap.put("install_dzbm", equipmentModel.getInstall_dzbm());
					paramsMap.put("longitude", "");
					paramsMap.put("latitude", "");
					quartzJobDao.saveEquipmentInfo(paramsMap);
				} catch (Exception e) {
					LOGGER.error("SynchronizePointServiceImpl.saveEquipmentInfo() got exception. (" + equipmentModel + ")", e);
				}
			}
		}
	}
}
