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
import com.cableCheck.dao.TaskMangerNewDao;
import com.cableCheck.service.TaskMangerNewService;
import com.cableCheck.service.TaskMangerService;
import com.linePatrol.util.DateUtil;
import com.webservice.client.ElectronArchivesService;

@SuppressWarnings("all")
@Service
public class TaskMangerNewServiceImpl implements TaskMangerNewService {
	Logger logger = Logger.getLogger(TaskMangerNewServiceImpl.class);
	
	@Resource
	private TaskMangerNewDao taskMangerNewDao;
	
	@Resource
	private CheckProcessDao checkProcessDao;
	
	@Resource
	private CableInterfaceDao cableInterfaceDao;
	
	@Resource
	private CheckPortDao checkPortDao;
	
	@Resource
	ElectronArchivesService electronArchivesService;
	

	/**
	 * 上月变动的工单设备-查询
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
		String equipmentCode = request.getParameter("equipmentCode");
		String equipmentName = request.getParameter("equipmentName");
		String equipmentAddress = request.getParameter("equipmentAddress");
		String wljb= request.getParameter("wljb");//网络级别
		String RES_TYPE_ID= request.getParameter("RES_TYPE_ID");
		String contract_person_name= request.getParameter("contract_person_name");//承包人
		String show_company= request.getParameter("show_company");//代维公司
		String show_banzu= request.getParameter("show_banzu");//代维班组
		String order_type= request.getParameter("order_type");//工单性质 17 拆 18 新装
		String order_from= request.getParameter("order_from");//1表示综调 2表示iom
	
		map.put("DZ_START_TIME", dzStartTime);
		map.put("DZ_END_TIME", dzEndTime);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", sonArea);
		map.put("WHWG", whwg);
		map.put("MANAGE_AREA_ID", mange_id);//管理区名称
		map.put("EQUIPMENT_CODE", equipmentCode);
		map.put("EQUIPMENT_NAME", equipmentName);
		map.put("EQUIPMENT_ADDRESS", equipmentAddress);
		map.put("WLJB", wljb);
		map.put("RES_TYPE_ID", RES_TYPE_ID);
		map.put("CONTRACT_PERSON_NAME", contract_person_name);
		map.put("show_company", show_company);
		map.put("show_banzu", show_banzu);
		map.put("order_type", order_type);
		map.put("order_from", order_from);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = taskMangerNewDao.addTaskQueryNotGroup(query);		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}
	/**
	 * 上月变动的工单设备的详细信息
	 */
	@Override
	public Map<String, Object> queryEqpOrder(Map param) {
		// 查询设备箱体信息
		List<Map<String, String>> eqpInfo =taskMangerNewDao.queryEqp(param);
		//查询工单信息
		List<Map<String, String>> orderInfo=taskMangerNewDao.queryOrder(param);
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("eqpInfo", eqpInfo);
		result.put("orderInfo", orderInfo);
		return result;
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
		
		List<Map<String, Object>> list = taskMangerNewDao.queryHandler(query);
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
		String area = request.getParameter("area");//设备所属地市
		String son_area = request.getParameter("son_area");//设备所属区域
		String dz_start_time = request.getParameter("dz_start_time");
		String dz_end_time = request.getParameter("dz_end_time");
		String complete_time = request.getParameter("complete_time");
		String userId = request.getSession().getAttribute("staffId").toString();// 当前用户
		map.put("EQUIPMENT_ID", sbIds);
		map.put("area", area);
		map.put("son_area", son_area);
		//获取设备各个信息
		List<Map<String, Object>> list = taskMangerNewDao.equipmentQuery1(map);
		//新增任务
		for (Map<String, Object> sbMap : list) {
			Map<String, Object> newMap =new HashMap<String, Object>();
			sbMap.put("DZ_START_TIME", dz_start_time);
			sbMap.put("DZ_END_TIME", dz_end_time);
			sbMap.put("area", area);
			sbMap.put("son_area", son_area);
			
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
				
			//根据设备id获取端子
	    	List<Map<String, Object>> dzList = taskMangerNewDao.terminalQuery1(sbMap);
			taskMangerNewDao.doSaveTask(newMap);
			String task_id = newMap.get("TASK_ID").toString();
			//保存任务详细
			for (Map<String, Object> dzMap : dzList) {
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", task_id);
				taskDetailMap.put("INSPECT_OBJECT_ID", dzMap.get("ID"));//端子表主键ID
				taskDetailMap.put("DTSJ_ID", dzMap.get("ID"));
				taskDetailMap.put("SBID", dzMap.get("SBID")==null?"":dzMap.get("SBID"));//端子所在设备ID
				taskDetailMap.put("SBBM", dzMap.get("SBBM")==null?"":dzMap.get("SBBM"));//端子所在设备编码
				taskDetailMap.put("SBMC", dzMap.get("SBMC")==null?"":dzMap.get("SBMC"));//端子所在设备名称
				taskDetailMap.put("INSPECT_OBJECT_NO", dzMap.get("DZBM")==null?"":dzMap.get("DZBM"));//端子编码
				taskDetailMap.put("DZID", dzMap.get("DZID")==null?"":dzMap.get("DZID"));//端子id
				taskDetailMap.put("GLBH", dzMap.get("GLBH")==null?"":dzMap.get("GLBH"));
				taskDetailMap.put("GLMC", dzMap.get("GLMC")==null?"":dzMap.get("GLMC"));
				taskDetailMap.put("ORDER_NO", dzMap.get("ORDER_NO")==null?"":dzMap.get("ORDER_NO"));//工单号
				taskDetailMap.put("MARK", dzMap.get("MARK")==null?"":dzMap.get("MARK"));//工单来源
				taskDetailMap.put("ACTION_TYPE", dzMap.get("ACTION_TYPE")==null?"":dzMap.get("ACTION_TYPE"));//工单性质
				taskDetailMap.put("ARCHIVE_TIME", dzMap.get("ARCHIVE_TIME")==null?"":dzMap.get("ARCHIVE_TIME"));//工单竣工时间
				taskDetailMap.put("BDSJ", dzMap.get("BDSJ")==null?"":dzMap.get("BDSJ"));//端子变动时间			
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
				taskDetailMap.put("CHECK_FLAG", "0");//0派发的端子，1检查的端子
				taskMangerNewDao.doSaveTaskDetail(taskDetailMap);
				taskMangerNewDao.updateDtsj(dzMap);  //派发成功修改端子派发状态
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
		}
		return Result.returnSuccess("").toString();
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
		String equipmentName=request.getParameter("equipmentName");
		String equipmentAddress=request.getParameter("equipmentAddress");
		String contract_person_name=request.getParameter("contract_person_name");
		String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
		String wljb= request.getParameter("wljb");//网络级别
		String show_company= request.getParameter("show_company");//代维公司
		String show_banzu= request.getParameter("show_banzu");//代维班组
		
		map.put("DZ_START_TIME", dzStartTime);
		map.put("DZ_END_TIME", dzEndTime);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", sonArea);
		map.put("WHWG", whwg);
		map.put("MANAGE_AREA_ID", mange_id);
		map.put("EQUIPMENT_CODE", equipmentCode);
		map.put("EQUIPMENT_NAME", equipmentName);
		map.put("EQUIPMENT_ADDRESS", equipmentAddress);
		map.put("CONTRACT_PERSON_NAME", contract_person_name);
		map.put("RES_TYPE_ID", RES_TYPE_ID);
		map.put("WLJB", wljb);
		map.put("show_company", show_company);
		map.put("show_banzu", show_banzu);

		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = taskMangerNewDao.downTaskQueryNotGroup(map);
		String fileName = "新增任务";
		String firstLine = "新增任务";
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域","综合化维护网格","设备编码",
				"设备名称","设备类型", "设备地址","网络级别",  "管理区名称 ","承包人","代维公司","代维班组 ","工单数","端子数" });
		List<String> code = Arrays.asList(new String[] { "AREA","SON_AREA","GRID_NAME","EQUIPMENT_CODE",
				"EQUIPMENT_NAME", "RES_TYPE","ADDRESS", "WLJB", "MANAGE_AREA" ,"CONTRACT_PERSON_NAME","TEAM_NAME","COMPANY_NAME","ORDERNUM","PORTNUM"});
		try {
			ExcelUtil.generateExcelAndDownload(title, code, list, request,
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
			String equipmentName=request.getParameter("equipmentName");
			String equipmentAddress=request.getParameter("equipmentAddress");
			String contract_person_name=request.getParameter("contract_person_name");
			String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
			String wljb= request.getParameter("wljb");//网络级别
			String show_company= request.getParameter("show_company");//代维公司
			String show_banzu= request.getParameter("show_banzu");//代维班组
			String order_type= request.getParameter("order_type");//工单性质
			String order_from= request.getParameter("order_from");//工单来源
			
			map.put("DZ_START_TIME", dzStartTime);
			map.put("DZ_END_TIME", dzEndTime);
			map.put("AREA_ID", area);
			map.put("SON_AREA_ID", sonArea);
			map.put("WHWG", whwg);
			map.put("MANAGE_AREA_ID", mange_id);
			map.put("EQUIPMENT_CODE", equipmentCode);
			map.put("EQUIPMENT_NAME", equipmentName);
			map.put("EQUIPMENT_ADDRESS", equipmentAddress);
			map.put("CONTRACT_PERSON_NAME", contract_person_name);
			map.put("RES_TYPE_ID", RES_TYPE_ID);
			map.put("WLJB", wljb);
			map.put("show_company", show_company);
			map.put("show_banzu", show_banzu);
			map.put("order_type", order_type);
			map.put("order_from", order_from);
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = taskMangerNewDao.downPortQueryNotGroup(map);		

			String fileName = "工单信息";
			String firstLine = "工单信息";
			
			List<String> title = Arrays.asList(new String[] {"地市","区域","箱子编码","箱子名称","箱子地址","网络级别","综合化维护网格","箱子类型" ,"管理区编码","设备承包人","代维公司","代维班组","端子编码","设备编码 ","设备名称","光路编码","光路名称 ","工单号 ",
					 "工单类型","工单来源","取数时间","工单竣工时间"});
			List<String> code = Arrays.asList(new String[] { "AREA","SON_AREA","EQUIPMENT_CODE" ,"EQUIPMENT_NAME","ADDRESS","WLJB","GRID_NAME","RES_TYPE","MANAGE_AREA","CONTRACT_PERSON_NAME","COMPANY_NAME","TEAM_NAME",
					"DZBM", "SBBM","SBMC","GLBH","GLMC","ORDER_NO","ACTION_TYPE","MARK","BDSJ","ARCHIVE_TIME"});
			try {   
				ExcelUtil.generateExcelAndDownload(title, code, list, request,
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
}
