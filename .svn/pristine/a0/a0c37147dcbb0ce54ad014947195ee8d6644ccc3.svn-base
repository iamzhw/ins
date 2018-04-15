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
import com.cableCheck.dao.TaskMangerDao;
import com.cableCheck.service.TaskMangerService;
import com.linePatrol.util.DateUtil;
import com.webservice.client.ElectronArchivesService;

@SuppressWarnings("all")
@Service
public class TaskMangerServiceImpl implements TaskMangerService {
	Logger logger = Logger.getLogger(TaskMangerServiceImpl.class);
	
	@Resource
	private TaskMangerDao taskMangerDao;
	
	@Resource
	private CheckProcessDao checkProcessDao;
	
	@Resource
	private CableInterfaceDao cableInterfaceDao;
	
	@Resource
	private CheckPortDao checkPortDao;
	
	@Resource
	ElectronArchivesService electronArchivesService;
	
	/**
	 * 任务管理-查询
	 */
	@Override
	public Map<String, Object> getTask(HttpServletRequest request, UIPage pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		String taskName = request.getParameter("task_name");
		String taskType = request.getParameter("task_type");
		String taskStartTime = request.getParameter("task_start_time");
		String taskEndTime = request.getParameter("task_end_time");
		String finshStartTime = request.getParameter("finsh_start_time");
		String finshEndTime = request.getParameter("finsh_end_time");
		String ifFinshed = request.getParameter("if_finshed");
		String ifFiled = request.getParameter("if_filed");
		
		map.put("TASK_NAME", taskName);
		map.put("TASK_TYPE", taskType);
		map.put("TASK_START_TIME", taskStartTime);
		map.put("TASK_END_TIME", taskEndTime);
		map.put("FINSH_START_TIME", finshStartTime);
		map.put("FINSH_END_TIME", finshEndTime);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = taskMangerDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	/**
	 * 新增任务-查询
	 */
	@Override
	public Map<String, Object> addTaskQuery(HttpServletRequest request,
			UIPage pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String dzStartTime = request.getParameter("dz_start_time");
		String dzEndTime = request.getParameter("dz_end_time");
		String area = request.getParameter("area");
		String sonArea = request.getParameter("son_area");
		String whwg = request.getParameter("whwg");
		String mange_id = request.getParameter("mange_id");
		String sblx = request.getParameter("sblx");
		String equipmentCode = request.getParameter("equipmentCode");
		String equipmentName = request.getParameter("equipmentName");
		String equipmentAddress = request.getParameter("equipmentAddress");
		String selectType1=request.getParameter("selectType1");
		String selectType2=request.getParameter("selectType2");
		String selectType3=request.getParameter("selectType3");
		String ish = request.getParameter("ish");
		String isxz= request.getParameter("isxz");
		String xz= request.getParameter("xz");//光路性质
		String wljb= request.getParameter("wljb");//网络级别
		String RES_TYPE_ID= request.getParameter("RES_TYPE_ID");
		String contract_person_name= request.getParameter("contract_person_name");//承包人
	
		/*String RES_TYPE_ID1=request.getParameter("RES_TYPE_ID1");
		String RES_TYPE_ID2=request.getParameter("RES_TYPE_ID2");
		String RES_TYPE_ID3=request.getParameter("RES_TYPE_ID3");
		String RES_TYPE_ID4=request.getParameter("RES_TYPE_ID4");
		String RES_TYPE_ID ="";
		
		List<String> typelist = new ArrayList<String>();
		typelist.add(RES_TYPE_ID1);
		typelist.add(RES_TYPE_ID2);
		typelist.add(RES_TYPE_ID3);
		typelist.add(RES_TYPE_ID4);
		for(int i = 0; i<typelist.size(); i++){
		    if(typelist.get(i)!=null && typelist.get(i) !="" ){
		    	RES_TYPE_ID=RES_TYPE_ID+typelist.get(i)+",";
		    };
		}
		if(RES_TYPE_ID!=null&&RES_TYPE_ID!=""){
			RES_TYPE_ID = RES_TYPE_ID.substring(0, RES_TYPE_ID.length()-1);
		}*/
		map.put("DZ_START_TIME", dzStartTime);
		map.put("DZ_END_TIME", dzEndTime);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", sonArea);
		map.put("WHWG", whwg);
		map.put("MANAGE_AREA_ID", mange_id);
		map.put("EQUIPMENT_CODE", equipmentCode);
		map.put("EQUIPMENT_NAME", equipmentName);
		map.put("EQUIPMENT_ADDRESS", equipmentAddress);
		map.put("selectType1", selectType1);
		map.put("selectType2", selectType2);
		map.put("selectType3", selectType3);
		map.put("ish", ish);
		map.put("isxz", isxz);
		map.put("xz", xz);
		map.put("wljb", wljb);
		map.put("RES_TYPE_ID", RES_TYPE_ID);
		map.put("contract_person_name", contract_person_name);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		if ((null == selectType1 || selectType1 == "" || "".equals(selectType1))
				&& (null == selectType2 || selectType2 == ""
				|| "".equals(selectType2)) && (null == selectType3
				|| selectType3 == "" || "".equals(selectType3))) {
			// 无分组
			list = taskMangerDao.addTaskQueryNotGroup(query);
		} else {
			// 有分组
			List<Map<String, Object>> oldList = taskMangerDao.addTaskQuery(query);
			
			//查询端子,H光路数，涉及光路的数量
			if(null != oldList & oldList.size() > 0){
				for (Map<String, Object> map2 : oldList) {
					
					map2.put("DZ_START_TIME", dzStartTime);
					map2.put("DZ_END_TIME", dzEndTime);
					if("所有".equals(map2.get("MANAGE_AREA_ID"))){
						map2.put("MANAGE_AREA", "");
					}else{
						map2.put("MANAGE_AREA", map2.get("MANAGE_AREA_ID"));
					}
	 				List<Map<String, Object>> newList = taskMangerDao.queryNumAndDeviceIdByGroup(map2);//根据条件查询出相应的设备
					String ids = "";
					int DZNUM = 0;
					int GLNUM = 0;
					int HNUM = 0;
					if(null != newList && newList.size() > 0){
						for (Map<String, Object> map3 : newList) {
							ids += map3.get("EQUIPMENT_ID")+",";
							DZNUM += Integer.parseInt(map3.get("DZNUM").toString());
							GLNUM += Integer.parseInt(map3.get("GLNUM").toString());
							HNUM += Integer.parseInt(map3.get("HNUM").toString());
						}
						ids = ids.substring(0,ids.length()-1);
					}
					
					map2.put("SBIDS", ids);
					//查询数量
//					int DZNUM = taskMangerDao.queryDzNum(map2);
//					int GLNUM = taskMangerDao.queryGlNum(map2);
//					int HNUM = taskMangerDao.queryHNum(map2);
					map2.put("DZNUM", DZNUM);
					map2.put("GLNUM", GLNUM);
					map2.put("HNUM", HNUM);
					list.add(map2);
				}
			}
		}
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	/**
	 * 设备信息-查询
	 */
	@Override
	public Map<String, Object> equipmentQuery(HttpServletRequest request,
			UIPage pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String EQUIPMENT_ID = request.getParameter("EQUIPMENT_ID");
		String dz_start_time = request.getParameter("dz_start_time");
		String dz_end_time = request.getParameter("dz_end_time");
		String sfpf = request.getParameter("sfpf");
		String area = request.getParameter("area");
		String sonArea = request.getParameter("son_area");
		String whwg = request.getParameter("whwg");
		String mange_id = request.getParameter("mange_id");
		String sblx = request.getParameter("sblx");
		String equipmentCode = request.getParameter("equipmentCode");
		
		map.put("EQUIPMENT_ID", EQUIPMENT_ID);
		map.put("DZ_START_TIME", dz_start_time);
		map.put("DZ_END_TIME", dz_end_time);
		map.put("SFPF", sfpf);
		map.put("AREA", area);
		map.put("SON_AREA", sonArea);
		map.put("WHWG", whwg);
		map.put("MANAGE_AREA_ID", mange_id);
		map.put("RES_TYPE_ID", sblx);
		map.put("EQUIPMENT_CODE", equipmentCode);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = taskMangerDao.equipmentQuery(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	/**
	 * 端子信息-查询
	 */
	@Override
	public Map<String, Object> terminalQuery(HttpServletRequest request,
			UIPage pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		String EQUIPMENT_ID = request.getParameter("EQUIPMENT_ID");
		String glmc = request.getParameter("GLMC");
		String glbh = request.getParameter("GLBH");
		String areaId = request.getParameter("areaId");
		String dz_start_time = request.getParameter("dz_start_time");
		String dz_end_time = request.getParameter("dz_end_time");
		
		String jndi = cableInterfaceDao.getDBblinkName(areaId);
		map.put("EQUIPMENT_ID", EQUIPMENT_ID);
		map.put("jndi", jndi);
		map.put("DZ_END_TIME", dz_end_time);
		map.put("DZ_START_TIME", dz_start_time);
	
		//根据设备id获取端子
//		List<Map<String, Object>> dzList = taskMangerDao.terminalQuery1(map);
//		String ids = "";
//		
//		if(null != dzList && dzList.size()>0){
//			for (Map<String, Object> map2 : dzList) {
//				ids += map2.get("ID")+",";
//			}
//			ids = ids.substring(0,ids.length()-1);
//		}
//		map.put("IDS", ids);
		map.put("GLMC", glmc);
		map.put("GLBH", glbh);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		//获取地市名称   getAreaName
		String areaName = taskMangerDao.getAreaName(areaId);
		
		List<Map<String, Object>> duziList = new ArrayList<Map<String,Object>>();
		//获取端子信息
		List<Map<String, Object>> list = taskMangerDao.terminalQuery(query);
		//获取oss光路编号
		for (Map<String, Object> map2 : list) {
			String portId = map2.get("DZID").toString();
		//	String glbh_id = map2.get("GLBH").toString();
			map.put("portId", portId);
		//	map.put("glbh_id", glbh_id);
			List<Map> lightList = null;
			List<Map> iomList = null;
			//通过端子查光路号
			try{
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			lightList = taskMangerDao.getOssGlList(map);
			//通过光路编码查询IOM定单号
	//		iomList = taskMangerDao.getIomOderIdList(map);
			SwitchDataSourceUtil.clearDataSource();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			if(lightList.size()>0 && lightList != null){
				for (Map light : lightList) {
					String glbh_ = String.valueOf(light.get("GLBH"));
					if (glbh_ != null) {
						String sggh=queryPhotoDetail(areaName, glbh_);
						map2.put("OSSGLBH", glbh_);
						map2.put("SGGH", sggh);
						map2.put("OSSGLMC", light.get("GLMC"));
					}
				}
			}
			/*if(iomList.size()>0 && iomList != null){
				for (Map light : iomList) {
					String glbh_ = String.valueOf(light.get("IOM_ORDER_NO"));
					if (glbh_ != null) {
						String sggh=queryPhotoDetail(areaName, glbh_);
						map2.put("OSSGLBH", glbh_);
					}
				}
			}*/
			duziList.add(map2);
		}
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", duziList);
		return pmap;
	}

	/**
	 * 查询人员列表
	 */
	@Override
	public Map<String, Object> queryHandler(HttpServletRequest request,
			UIPage pager) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String areaId = request.getParameter("areaId");
		String sonAreaId = request.getParameter("sonAreaId");
		
		map.put("staff_name", staff_name);
		map.put("staff_no", staff_no);
		map.put("areaId", areaId);
		map.put("sonAreaId", sonAreaId);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = taskMangerDao.queryHandler(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	/**
	 * 派发任务
	 */
	@Override
	public String saveTask(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String staffId = request.getParameter("staffId");  //检查人id
		String sbIds = request.getParameter("sbIds");
		String dz_start_time = request.getParameter("dz_start_time");
		String dz_end_time = request.getParameter("dz_end_time");
		String complete_time = request.getParameter("complete_time");
		String gltype = request.getParameter("gltype");
		String remarks = request.getParameter("remarks");

		String userId = request.getSession().getAttribute("staffId").toString();// 当前用户
		map.put("EQUIPMENT_ID", sbIds);
		//获取设备各个信息
		List<Map<String, Object>> list = taskMangerDao.equipmentQuery1(map);
		//新增任务
		for (Map<String, Object> sbMap : list) {
			Map<String, Object> newMap =new HashMap<String, Object>();
			sbMap.put("DZ_START_TIME", dz_start_time);
			sbMap.put("DZ_END_TIME", dz_end_time);
			
			newMap.put("TASK_NO", sbMap.get("EQUIPMENT_CODE") + "_" + DateUtil.getDate("yyyyMMdd"));
			newMap.put("TASK_NAME", sbMap.get("EQUIPMENT_NAME")+ "_" + DateUtil.getDate("yyyyMMdd"));
			newMap.put("TASK_TYPE", 0);//周期性检查
			newMap.put("STATUS_ID", 6);//待回单
			newMap.put("INSPECTOR", staffId);
			newMap.put("CREATE_STAFF", userId);
			newMap.put("SON_AREA_ID", sbMap.get("SON_AREA_ID"));
			newMap.put("AREA_ID", sbMap.get("AREA_ID"));
			newMap.put("ENABLE", 0);
			newMap.put("REMARK", "");
			newMap.put("INFO", "");
			newMap.put("SBID", sbMap.get("EQUIPMENT_ID"));
			newMap.put("AUDITOR", userId);  //审核员
			newMap.put("complete_time", complete_time); 
			newMap.put("REMARKS", remarks); 
			sbMap.put("gltype", gltype); 
			
			
		
			//根据设备id获取端子
	    	List<Map<String, Object>> dzList = taskMangerDao.terminalQuery1(sbMap);
//			if(null==dzList||dzList.size()==0){
//				return Result.returnError("已派发").toString();		
//			}else{
				//插入任务表
				taskMangerDao.doSaveTask(newMap);
				String task_id = newMap.get("TASK_ID").toString();
			//保存任务详细
			for (Map<String, Object> dzMap : dzList) {
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", task_id);
				taskDetailMap.put("INSPECT_OBJECT_ID", dzMap.get("ID"));//id
				taskDetailMap.put("DTSJ_ID", dzMap.get("ID"));
				taskDetailMap.put("INSPECT_OBJECT_NO", dzMap.get("DZBM")==null?"":dzMap.get("DZBM"));//端子编码
				taskDetailMap.put("DZID", dzMap.get("DZID")==null?"":dzMap.get("DZID"));//端子id
				taskDetailMap.put("SBID", dzMap.get("SBID")==null?"":dzMap.get("SBID"));//设备id
				taskDetailMap.put("SBBM", dzMap.get("SBBM")==null?"":dzMap.get("SBBM"));//设备编码
				taskDetailMap.put("SBMC", dzMap.get("SBMC")==null?"":dzMap.get("SBMC"));//设备名称
				taskDetailMap.put("XZ", dzMap.get("XZ")==null?"":dzMap.get("XZ"));//端子光路性质
				taskDetailMap.put("ACTION_TYPE", dzMap.get("ACTION_TYPE")==null?"":dzMap.get("ACTION_TYPE"));//端子光路性质
				taskDetailMap.put("BDSJ", dzMap.get("BDSJ")==null?"":dzMap.get("BDSJ"));//端子变动时间
				taskDetailMap.put("GLBH", dzMap.get("GLBH")==null?"":dzMap.get("GLBH"));
				taskDetailMap.put("GLMC", dzMap.get("GLMC")==null?"":dzMap.get("GLMC"));
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
				taskDetailMap.put("CHECK_FLAG", "0");//0派发的端子，1检查的端子
				taskMangerDao.doSaveTaskDetail(taskDetailMap);
				taskMangerDao.updateDtsj(dzMap);  //派发成功修改端子派发状态
			}
			//保存工单流程
			Map<String, Object> flowParams = new HashMap<String, Object>();
			flowParams.put("oper_staff", userId);
			flowParams.put("task_id", task_id);
			flowParams.put("status",  6); //待回单
			flowParams.put("remark", "派发工单");
			flowParams.put("receiver", staffId);
			flowParams.put("content", "管理员派发工单，生成任务");
			checkProcessDao.addProcessNew(flowParams);
		//}
		}
		
		return Result.returnSuccess("").toString();
		
	}

	/**
	 * 设备检查记录查询
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String, Object> queryDeviceRecords(HttpServletRequest request,
			UIPage pager){
		Map<String, Object> map = new HashMap<String, Object>();
		
		String cStartTime = request.getParameter("cStartTime");
		String cEndTime = request.getParameter("cEndTime");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String staff = request.getParameter("staff");
		String equipmentCode = request.getParameter("equipmentCode");
		String recordType = request.getParameter("recordType");
		
		map.put("cStartTime", cStartTime);
		map.put("cEndTime", cEndTime);
		map.put("area", area);
		map.put("son_area", son_area);
		map.put("staff", staff);
		map.put("equipmentCode", equipmentCode);
		map.put("recordType", recordType);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = taskMangerDao.queryDeviceRecords(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}
	/**
	 * 新增任务导出
	 * @param request
	 * @return
	 */
	@Override
	public void downTaskQuery(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		String dzStartTime = request.getParameter("dz_start_time");
		String dzEndTime = request.getParameter("dz_end_time");
		String area = request.getParameter("area");
		String sonArea = request.getParameter("son_area");
		String whwg = request.getParameter("whwg");
		String mange_id = request.getParameter("mange_id");
		String equipmentCode = request.getParameter("equipmentCode");
		String selectType1=request.getParameter("selectType1");
		String selectType2=request.getParameter("selectType2");
		String selectType3=request.getParameter("selectType3");
		String ish = request.getParameter("ish");
		String isxz = request.getParameter("isxz");
		String xz = request.getParameter("xz");
		String wljb = request.getParameter("wljb");
		String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
		/*String RES_TYPE_ID1=request.getParameter("RES_TYPE_ID1");
		String RES_TYPE_ID2=request.getParameter("RES_TYPE_ID2");
		String RES_TYPE_ID3=request.getParameter("RES_TYPE_ID3");
		String RES_TYPE_ID4=request.getParameter("RES_TYPE_ID4");*/
		/*String RES_TYPE_ID ="";
		List<String> typelist = new ArrayList<String>();
		typelist.add(RES_TYPE_ID1);
		typelist.add(RES_TYPE_ID2);
		typelist.add(RES_TYPE_ID3);
		typelist.add(RES_TYPE_ID4);
		for(int i = 0; i<typelist.size(); i++){
		    if(typelist.get(i)!=null && typelist.get(i) !="" ){
		    	RES_TYPE_ID=RES_TYPE_ID+typelist.get(i)+",";
		    };
		}
		if(RES_TYPE_ID!=null&&RES_TYPE_ID!=""){
			RES_TYPE_ID = RES_TYPE_ID.substring(0, RES_TYPE_ID.length()-1);
		}*/
		
		map.put("DZ_START_TIME", dzStartTime);
		map.put("DZ_END_TIME", dzEndTime);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", sonArea);
		map.put("WHWG", whwg);
		map.put("MANAGE_AREA_ID", mange_id);
		map.put("EQUIPMENT_CODE", equipmentCode);
		map.put("selectType1", selectType1);
		map.put("selectType2", selectType2);
		map.put("selectType3", selectType3);
		map.put("ish", ish);
		map.put("isxz", isxz);
		map.put("xz", xz);
		map.put("wljb", wljb);
		map.put("RES_TYPE_ID", RES_TYPE_ID);

		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		if ((null == selectType1 || selectType1 == "" || "".equals(selectType1))
				&& (null == selectType2 || selectType2 == ""
				|| "".equals(selectType2)) && (null == selectType3
				|| selectType3 == "" || "".equals(selectType3))) {
			// 无分组
			list = taskMangerDao.downTaskQueryNotGroup(map);
			String fileName = "新增任务";
			String firstLine = "新增任务";
			List<String> title = Arrays.asList(new String[] { "地市 ", "区域",
					"设备名称", "设备编码","设备地址","网络级别", "综合化维护网格", "管理区编码 ","设备类型","检查员","上次检查时间","端子变化数 ","H光路数量","涉及光路数" });
			List<String> code = Arrays.asList(new String[] { "AREA","SON_AREA",
					"EQUIPMENT_NAME", "EQUIPMENT_CODE","EQUIPMENT_ADDRESS","WLJB", "ZHHWHWG", "MANAGE_AREA_ID" ,"RES_TYPE","STAFF_NAME","CREATE_TIME","DZNUM","HNUM","GLNUM"});
			try {
				ExcelUtil.generateExcelAndDownload(title, code, list, request,
						response, fileName, firstLine);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// 有分组
			List<Map<String, Object>> oldList = taskMangerDao.downTaskQuery(map);
			
			//查询端子,H光路数，涉及光路的数量
			if(null != oldList & oldList.size() > 0){
				for (Map<String, Object> map2 : oldList) {
					
					map2.put("DZ_START_TIME", dzStartTime);
					map2.put("DZ_END_TIME", dzEndTime);
					if("所有".equals(map2.get("MANAGE_AREA_ID"))){
						map2.put("MANAGE_AREA", "");
					}else{
						map2.put("MANAGE_AREA", map2.get("MANAGE_AREA_ID"));
					}
	 				List<Map<String, Object>> newList = taskMangerDao.queryNumAndDeviceIdByGroup(map2);//根据条件查询出相应的设备
					String ids = "";
					int DZNUM = 0;
					int GLNUM = 0;
					int HNUM = 0;
					if(null != newList && newList.size() > 0){
						for (Map<String, Object> map3 : newList) {
							ids += map3.get("EQUIPMENT_ID")+",";
							DZNUM += Integer.parseInt(map3.get("DZNUM").toString());
							GLNUM += Integer.parseInt(map3.get("GLNUM").toString());
							HNUM += Integer.parseInt(map3.get("HNUM").toString());
						}
						ids = ids.substring(0,ids.length()-1);
					}
					
					map2.put("SBIDS", ids);
					//查询数量
//					int DZNUM = taskMangerDao.queryDzNum(map2);
//					int GLNUM = taskMangerDao.queryGlNum(map2);
//					int HNUM = taskMangerDao.queryHNum(map2);
					map2.put("DZNUM", DZNUM);
					map2.put("GLNUM", GLNUM);
					map2.put("HNUM", HNUM);
					list.add(map2);
					
				}
			}
			
			String fileName = "新增任务";
			String firstLine = "新增任务";
			List<String> title = Arrays.asList(new String[] { "地市 ", "区域",
					"综合化维护网格","管理区编码 ",   "设备类型","端子变化数 ","H光路数量","涉及光路数" ,"派发规则"});
			List<String> code = Arrays.asList(new String[] { "AREA","SON_AREA",
					 "ZHHWHWG", "MANAGE_AREA_ID" ,"RES_TYPE","DZNUM","HNUM","GLNUM","PFGZ"});
			try {
				ExcelUtil.generateExcelAndDownload(title, code, list, request,
						response, fileName, firstLine);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	/**
	 * 设备信息导出
	 * @param request
	 * @return
	 */
	@Override
	public void downEquipmentQuery(Map<String, Object> para,HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		
		
		
		List<Map<String, Object>> list = taskMangerDao.downEquipmentQuery(para);
		
		String fileName = "设备信息";
		String firstLine = "设备信息";
		List<String> title = Arrays.asList(new String[] { "设备名称 ", "设备编码",
				"设备类型 ", "网络级别","管理区名称 ", "管理区编码 ","检查员","上次检查时间","最近一次更新时间","是否派发","端子变动数" });
		List<String> code = Arrays.asList(new String[] { "EQUIPMENT_NAME","EQUIPMENT_CODE",
				"RES_TYPE", "WLJB","MANAGE_AREA", "MANAGE_AREA_ID", "STAFF_NAME" ,"CREATE_TIME","LAST_UPDATE_TIME","SFPF","DZNUM"});
		try {
			ExcelUtil.generateExcelAndDownload(title, code, list, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void downTerminalQuery(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paras = new HashMap<String, Object>();
		
		String areaId =(String) para.get("AREAID");
		
		
		String jndi = cableInterfaceDao.getDBblinkName(areaId);
		paras.put("EQUIPMENT_ID", para.get("EQUIPMENT_ID"));
		paras.put("jndi", jndi);
		paras.put("DZ_END_TIME", para.get("DZ_END_TIME"));
		paras.put("DZ_START_TIME", para.get("DZ_START_TIME"));
		paras.put("GLMC", para.get("GLMC"));
		paras.put("GLBH", para.get("GLBH"));
		
	
		
		List<Map<String, Object>> duziList = new ArrayList<Map<String,Object>>();
		//获取端子信息
		List<Map<String, Object>> list = taskMangerDao.downTerminalQuery(paras);
		//获取oss光路编号
		for (Map<String, Object> map2 : list) {
			String portId = map2.get("DZID").toString();
			paras.put("portId", portId);
			//通过端子查光路号
			List<Map> lightList = null;
			try{
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			 lightList = taskMangerDao.getOssGlList(paras);
			SwitchDataSourceUtil.clearDataSource();
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			
			if(lightList.size()>0 && lightList != null){
				for (Map light : lightList) {
					if (String.valueOf(light.get("GLBH")) != null) {
						map2.put("OSSGLBH", String.valueOf(light.get("GLBH")));
						map2.put("OSSGLMC", light.get("GLMC"));
					}
				}
			}
			duziList.add(map2);
		}
		
		String fileName = "端子信息";
		String firstLine = "端子信息";
		List<String> title = Arrays.asList(new String[] {  "设备编码","设备名称 ",
				"端子 ", "OSS光路编码 ", "光路编码 ","OSS光路名称","光路名称","是否H光路","涉及节点设备编码","工单竣工时间","最近一次更新时间","工单编号","工单性质" ,"配置工号","施工工号","更纤工号"});
		List<String> code = Arrays.asList(new String[] { "SBBM","SBMC",
				"DZBM", "OSSGLBH", "GLBH", "OSSGLMC" ,"GLMC" ,"H" ,"INSTALL_SBBM","GDJGSJ","GXSJ","GDBH","XZ","PZGH","SGGH","GQGH"});
		try {
			ExcelUtil.generateExcelAndDownload(title, code, duziList, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//导出所有设备所有端子
		@Override
		public void downPortQuery(HttpServletRequest request,
				HttpServletResponse response) {
			Map<String, Object> map = new HashMap<String, Object>();

			String dzStartTime = request.getParameter("dz_start_time");
			String dzEndTime = request.getParameter("dz_end_time");
			String area = request.getParameter("area");
			String sonArea = request.getParameter("son_area");
			String whwg = request.getParameter("whwg");
			String mange_id = request.getParameter("mange_id");
			String equipmentCode = request.getParameter("equipmentCode");
			String selectType1=request.getParameter("selectType1");
			String selectType2=request.getParameter("selectType2");
			String selectType3=request.getParameter("selectType3");
			String ish = request.getParameter("ish");
			String isxz = request.getParameter("isxz");
			String wljb = request.getParameter("wljb");
			String xz = request.getParameter("xz");
			String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
			
			map.put("DZ_START_TIME", dzStartTime);
			map.put("DZ_END_TIME", dzEndTime);
			map.put("AREA_ID", area);
			map.put("SON_AREA_ID", sonArea);
			map.put("WHWG", whwg);
			map.put("MANAGE_AREA_ID", mange_id);
			map.put("EQUIPMENT_CODE", equipmentCode);
			map.put("selectType1", selectType1);
			map.put("selectType2", selectType2);
			map.put("selectType3", selectType3);
			map.put("ish", ish);
			map.put("wljb", wljb);
			map.put("isxz", isxz);
			map.put("xz", xz);
			map.put("RES_TYPE_ID", RES_TYPE_ID);
			
			String areaName = taskMangerDao.getAreaName(area);
			
			List<Map<String, Object>> newList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = taskMangerDao.downPortQueryNotGroup(map);
	
			
			String jndi = cableInterfaceDao.getDBblinkName(area);
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("jndi", jndi);
			
			for (Map<String, Object> map2 : list) {
				String portId = map2.get("DZID").toString();
				paras.put("portId", portId);
				//通过端子查光路号
				List<Map> lightList = null;
				try{
				SwitchDataSourceUtil.setCurrentDataSource(jndi);
				 lightList = taskMangerDao.getOssGlList(paras);
				SwitchDataSourceUtil.clearDataSource();
				}catch (Exception e) {
					e.printStackTrace();
				}
				finally
				{
					SwitchDataSourceUtil.clearDataSource();
				}
				
				if(lightList.size()>0 && lightList != null){
					for (Map light : lightList) {
						String glbh_ = String.valueOf(light.get("GLBH"));
						String glmc_ = String.valueOf(light.get("GLMC"));
						map2.put("OSSGLBH", glbh_);
						map2.put("OSSGLMC", glmc_);
						//通过OSS光路编号和地市获取施工工号
						/*if (glbh_ != null) {
							String sggh=queryPhotoDetail(areaName, glbh_);
							map2.put("OSSGLBH", glbh_);
							map2.put("SGGH", sggh);
						}*/						
					}
				}
				newList.add(map2);
			}

			String fileName = "端子信息";
			String firstLine = "端子信息";
			
			List<String> title = Arrays.asList(new String[] {"设备编码","设备名称 ","端子编码 ","光路编码 ",
					 "光路名称","OSS光路编码","OSS光路名称","是否H光路","涉及节点设备编码","工单竣工时间","最近一次更新时间","工单编号","工单性质" ,"配置工号","施工工号","更纤工号","IOM定单号"});
			List<String> code = Arrays.asList(new String[] { "SBBM","SBMC","DZBM","GLBH" ,
					"GLMC", "OSSGLBH","OSSGLMC","H","INSTALL_SBBM","GDJGSJ","GXSJ","GDBH","XZ","PZGH","SGGH","GQGH","IOM_ORDER_NO"});
			try {   
				ExcelUtil.generateExcelAndDownload(title, code, newList, request,
						response, fileName, firstLine);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//通过光路编号查询FTTH装机照片详情获取施工工号
		public String queryPhotoDetail(String areaName,String glbh){
			JSONObject object = new JSONObject();
			JSONArray jsArr = new JSONArray();
			JSONObject object1 = new JSONObject();
			object1.put("areaName", areaName);
			object1.put("glbh", glbh);
			jsArr.add(object1);
			object.put("param", jsArr);
			String result = "";
			try {
				result = electronArchivesService.queryPhotoDetail(object.toString());
				logger.info("【通过光路编号查询FTTH装机照片详情WebService】"+result);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			JSONObject json = JSONObject.fromObject(result);
			JSONArray dataObject = json.getJSONArray("data");
			String staffNo="";
			if(dataObject.size()>0 && dataObject != null){
				JSONObject data = (JSONObject)dataObject.get(0);
				staffNo = null==data.get("staffNo")?"":data.getString("staffNo");//施工人员账号
			}
			return staffNo;
			
		}

		@Override
		public Map<String, Object> queryElectron(HttpServletRequest request,
				UIPage pager) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			
			String area = request.getParameter("area");
			String sonArea = request.getParameter("son_area");
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String is_deal = request.getParameter("is_deal");
			String whwg = request.getParameter("whwg");
			String mange_id = request.getParameter("mange_id");
			String sblx = request.getParameter("sblx");
			String equipmentCode = request.getParameter("equipmentCode");
			String equipmentName = request.getParameter("equipmentName");
			String equipmentAddress = request.getParameter("equipmentAddress");
			
			String RES_TYPE_ID= request.getParameter("RES_TYPE_ID");
		
		
			map.put("AREA_ID", area);
			map.put("SON_AREA_ID", sonArea);
			map.put("WHWG", whwg);
			map.put("MANAGE_AREA_ID", mange_id);
			map.put("EQUIPMENT_CODE", equipmentCode);
			map.put("EQUIPMENT_NAME", equipmentName);
			map.put("EQUIPMENT_ADDRESS", equipmentAddress);
			map.put("RES_TYPE_ID", RES_TYPE_ID);
			map.put("isdeal", is_deal);
			map.put("start_time", start_time);
			map.put("end_time", end_time);
			
			Query query = new Query();
			query.setPager(pager);
			query.setQueryParams(map);
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = taskMangerDao.queryElectron(query);
			Map<String, Object> pmap = new HashMap<String, Object>(0);
			pmap.put("total", query.getPager().getRowcount());
			pmap.put("rows", list);
			return pmap;
		}

		@Override
		public void electronArchivesDownload(HttpServletRequest request,
				HttpServletResponse response) {
			Map<String, Object> map = new HashMap<String, Object>();

			String area = request.getParameter("area");
			String sonArea = request.getParameter("son_area");
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String is_deal = request.getParameter("is_deal");
			String whwg = request.getParameter("whwg");
			String mange_id = request.getParameter("mange_id");
			String sblx = request.getParameter("sblx");
			String equipmentCode = request.getParameter("equipmentCode");
			String equipmentName = request.getParameter("equipmentName");
			String equipmentAddress = request.getParameter("equipmentAddress");
			
			String RES_TYPE_ID= request.getParameter("RES_TYPE_ID");
		
		
			map.put("AREA_ID", area);
			map.put("SON_AREA_ID", sonArea);
			map.put("WHWG", whwg);
			map.put("MANAGE_AREA_ID", mange_id);
			map.put("EQUIPMENT_CODE", equipmentCode);
			map.put("EQUIPMENT_NAME", equipmentName);
			map.put("EQUIPMENT_ADDRESS", equipmentAddress);
			map.put("RES_TYPE_ID", RES_TYPE_ID);
			map.put("isdeal", is_deal);
			map.put("start_time", start_time);
			map.put("end_time", end_time);

			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

			
				list = taskMangerDao.downElectron(map);
				String fileName = "电子档案库设备信息";
				String firstLine = "电子档案库设备信息";
				List<String> title = Arrays.asList(new String[] { "地市 ", "区域",
						"设备名称 ", "设备编码 ", "综合化维护网格", "管理区编码 ","设备类型","是否整治","整治时间" });
				List<String> code = Arrays.asList(new String[] { "AREA","SON_AREA",
						"EQUIPMENT_NAME", "EQUIPMENT_CODE", "ZHHWHWG", "MANAGE_AREA_ID" ,"RES_TYPE","ISDEAL","DEAL_TIME"});
				try {
					ExcelUtil.generateExcelAndDownload(title, code, list, request,
							response, fileName, firstLine);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			
			}
			
		@Override
		public String saveElectronTask(HttpServletRequest request) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			String staffId = request.getParameter("staffId");  //检查人id
			String sbIds = request.getParameter("sbIds");
			String dz_start_time = request.getParameter("dz_start_time");
			String dz_end_time = request.getParameter("dz_end_time");
			String complete_time = request.getParameter("complete_time");
			String gltype = request.getParameter("gltype");
			String remarks = request.getParameter("remarks");

			String userId = request.getSession().getAttribute("staffId").toString();// 当前用户
			map.put("EQUIPMENT_ID", sbIds);
			//获取设备各个信息
			List<Map<String, Object>> list = taskMangerDao.equipmentQuery1(map);
			//新增任务
			for (Map<String, Object> sbMap : list) {
				Map<String, Object> newMap =new HashMap<String, Object>();
				sbMap.put("DZ_START_TIME", dz_start_time);
				sbMap.put("DZ_END_TIME", dz_end_time);
				
				newMap.put("TASK_NO", sbMap.get("EQUIPMENT_CODE") + "_" + DateUtil.getDate("yyyyMMdd"));
				newMap.put("TASK_NAME", sbMap.get("EQUIPMENT_NAME")+ "_" + DateUtil.getDate("yyyyMMdd"));
				newMap.put("TASK_TYPE", 5);//电子档案库拍单
				newMap.put("STATUS_ID", 6);//待回单
				newMap.put("INSPECTOR", staffId);
				newMap.put("CREATE_STAFF", userId);
				newMap.put("SON_AREA_ID", sbMap.get("SON_AREA_ID"));
				newMap.put("AREA_ID", sbMap.get("AREA_ID"));
				newMap.put("ENABLE", 0);
				newMap.put("REMARK", "");
				newMap.put("INFO", "");
				newMap.put("SBID", sbMap.get("EQUIPMENT_ID"));
				newMap.put("AUDITOR", userId);  //审核员
				newMap.put("complete_time", complete_time); 
				newMap.put("REMARKS", remarks); 
				sbMap.put("gltype", gltype); 
				
				
			
				//根据设备id获取端子
				List<Map<String, Object>> dzList = taskMangerDao.terminalQuery2(sbMap);
				
					
					//插入任务表
					taskMangerDao.doSaveTask(newMap);
					String task_id = newMap.get("TASK_ID").toString();
				//保存任务详细
					if(null!=dzList&&dzList.size()!=0){
				for (Map<String, Object> dzMap : dzList) {
					Map taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", task_id);
					taskDetailMap.put("INSPECT_OBJECT_ID", dzMap.get("ID"));//id
					taskDetailMap.put("DTSJ_ID", dzMap.get("ID"));
					taskDetailMap.put("INSPECT_OBJECT_NO", dzMap.get("DZBM")==null?"":dzMap.get("DZBM"));//端子编码
					taskDetailMap.put("DZID", dzMap.get("DZID")==null?"":dzMap.get("DZID"));//端子id
					taskDetailMap.put("SBID", dzMap.get("SBID")==null?"":dzMap.get("SBID"));//设备id
					taskDetailMap.put("SBBM", dzMap.get("SBBM")==null?"":dzMap.get("SBBM"));//设备编码
					taskDetailMap.put("SBMC", dzMap.get("SBMC")==null?"":dzMap.get("SBMC"));//设备名称
					taskDetailMap.put("XZ", dzMap.get("XZ")==null?"":dzMap.get("XZ"));//端子光路性质
					taskDetailMap.put("ACTION_TYPE", dzMap.get("ACTION_TYPE")==null?"":dzMap.get("ACTION_TYPE"));//端子光路性质
					taskDetailMap.put("BDSJ", dzMap.get("BDSJ")==null?"":dzMap.get("BDSJ"));//端子变动时间
					taskDetailMap.put("GLBH", dzMap.get("GLBH")==null?"":dzMap.get("GLBH"));
					taskDetailMap.put("GLMC", dzMap.get("GLMC")==null?"":dzMap.get("GLMC"));
					taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
					taskDetailMap.put("CHECK_FLAG", "0");//0派发的端子，1检查的端子
					taskMangerDao.doSaveTaskDetail(taskDetailMap);
					taskMangerDao.updateDtsj(dzMap);  //派发成功修改端子派发状态
				}
					}
				//保存工单流程
				Map<String, Object> flowParams = new HashMap<String, Object>();
				flowParams.put("oper_staff", userId);
				flowParams.put("task_id", task_id);
				flowParams.put("status",  6); //待回单
				flowParams.put("remark", "派发工单");
				checkProcessDao.addProcess(flowParams);
			}
			
			
			return Result.returnSuccess("").toString();
		}
		
		
		/**
		 * 承包人一键派发任务
		 */
		@Override
		public String send_contract_name(HttpServletRequest request) {
			Map<String, Object> map = new HashMap<String, Object>();
					
			String sbIds = request.getParameter("sbIds");
			String dz_start_time = request.getParameter("dz_start_time");
			String dz_end_time = request.getParameter("dz_end_time");			

			String userId = request.getSession().getAttribute("staffId").toString();// 当前用户
			map.put("EQUIPMENT_ID", sbIds);
			//获取设备各个信息
			List<Map<String, Object>> list = taskMangerDao.queryContractEqp(map);
			if(list.size()==0){//承包人无角色权限
				return Result.returnError("承包人无光网助手检查员角色权限!!!").toString();
			}
			//既选了无角色权限的承包人，又选了有角色权限的承包人
			String[] sbidArr=sbIds.split(",");
			if(list.size()<sbidArr.length){
				return Result.returnError("承包人不全为光网助手检查员!!!").toString();
			}
			//新增任务
			for (Map<String, Object> sbMap : list) {
				Map<String, Object> newMap =new HashMap<String, Object>();
				sbMap.put("DZ_START_TIME", dz_start_time);
				sbMap.put("DZ_END_TIME", dz_end_time);
				
				newMap.put("TASK_NO", sbMap.get("EQUIPMENT_CODE") + "_" + DateUtil.getDate("yyyyMMdd"));
				newMap.put("TASK_NAME", sbMap.get("EQUIPMENT_NAME")+ "_" + DateUtil.getDate("yyyyMMdd"));
				newMap.put("TASK_TYPE", 0);//周期性检查
				newMap.put("STATUS_ID", 6);//待回单
				newMap.put("INSPECTOR", sbMap.get("STAFF_ID").toString());
				newMap.put("CREATE_STAFF", userId);
				newMap.put("SON_AREA_ID", sbMap.get("SON_AREA_ID"));
				newMap.put("AREA_ID", sbMap.get("AREA_ID"));
				newMap.put("ENABLE", 0);
				newMap.put("REMARK", "");
				newMap.put("INFO", "");
				newMap.put("SBID", sbMap.get("EQUIPMENT_ID"));
				newMap.put("AUDITOR", userId);  //审核员		
											
				//根据设备id获取端子
				List<Map<String, Object>> dzList = taskMangerDao.terminalQuery1(sbMap);
				if(null==dzList||dzList.size()==0){
					return Result.returnError("已派发").toString();		
				}else{
					//插入任务表
					taskMangerDao.doSaveTaskByContract(newMap);
					String task_id = newMap.get("TASK_ID").toString();
					//保存任务详细
					for (Map<String, Object> dzMap : dzList) {
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", task_id);
						taskDetailMap.put("INSPECT_OBJECT_ID", dzMap.get("ID"));//id
						taskDetailMap.put("DTSJ_ID", dzMap.get("ID"));
						taskDetailMap.put("INSPECT_OBJECT_NO", dzMap.get("DZBM")==null?"":dzMap.get("DZBM"));//端子编码
						taskDetailMap.put("DZID", dzMap.get("DZID")==null?"":dzMap.get("DZID"));//端子id
						taskDetailMap.put("SBID", dzMap.get("SBID")==null?"":dzMap.get("SBID"));//设备id
						taskDetailMap.put("SBBM", dzMap.get("SBBM")==null?"":dzMap.get("SBBM"));//设备编码
						taskDetailMap.put("SBMC", dzMap.get("SBMC")==null?"":dzMap.get("SBMC"));//设备名称
						taskDetailMap.put("XZ", dzMap.get("XZ")==null?"":dzMap.get("XZ"));//端子光路性质
						taskDetailMap.put("ACTION_TYPE", dzMap.get("ACTION_TYPE")==null?"":dzMap.get("ACTION_TYPE"));//端子光路性质
						taskDetailMap.put("BDSJ", dzMap.get("BDSJ")==null?"":dzMap.get("BDSJ"));//端子变动时间
						taskDetailMap.put("GLBH", dzMap.get("GLBH")==null?"":dzMap.get("GLBH"));
						taskDetailMap.put("GLMC", dzMap.get("GLMC")==null?"":dzMap.get("GLMC"));
						taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
						taskDetailMap.put("CHECK_FLAG", "0");//0派发的端子，1检查的端子
						taskMangerDao.doSaveTaskDetail(taskDetailMap);
						taskMangerDao.updateDtsj(dzMap);  //派发成功修改端子派发状态
					}
					//保存工单流程
					Map<String, Object> flowParams = new HashMap<String, Object>();
					flowParams.put("oper_staff", userId);
					flowParams.put("task_id", task_id);
					flowParams.put("status",  6); //待回单
					flowParams.put("remark", "派发承包工单");
					checkProcessDao.addProcess(flowParams);
				}
			}
			
			return Result.returnSuccess("").toString();
			
		}

		
}
