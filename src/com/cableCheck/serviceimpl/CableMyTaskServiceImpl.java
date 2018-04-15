package com.cableCheck.serviceimpl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CableMyTaskDao;
import com.cableCheck.dao.CheckProcessDao;
import com.cableCheck.dao.DoneTaskDao;
import com.cableCheck.service.CableMyTaskService;
import com.cableInspection.dao.CableDao;
import com.system.constant.RoleNo;
import com.system.dao.GeneralDao;
import com.system.dao.StaffDao;

import icom.cableCheck.dao.CheckOrderDao;
import icom.system.dao.CableInterfaceDao;
import icom.util.Result;
import icom.webservice.client.WfworkitemflowLocator;
import icom.webservice.client.WfworkitemflowSoap11BindingStub;
import util.page.Query;
import util.page.UIPage;

@SuppressWarnings("all")
@Service
public class CableMyTaskServiceImpl implements CableMyTaskService {

	private static final String rollbackFor = null;

	Logger logger = Logger.getLogger(CableMyTaskServiceImpl.class);
	
	@Resource
	private CableMyTaskDao cableMyTaskDao;
	
	@Resource
	private CheckOrderDao checkOrderDao;
	
	@Resource
	private CheckProcessDao checkProcessDao;

	@Resource
	private StaffDao staffDao;
	
	@Resource
	private CableDao cableDao;
	
	@Resource
	private GeneralDao generalDao;
	@Resource
	private DoneTaskDao doneTaskDao;
	@Resource
	private CableInterfaceDao cableInterfaceDao;
	


	/**
	 * 获取待办任务
	 */
	public Map<String, Object> getTask(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String statusId = request.getParameter("STATUS_ID");
		String taskName = request.getParameter("TASK_NAME");
		String taskType = request.getParameter("TASK_TYPE");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		
		String equipmentName = request.getParameter("equipmentName");
		String inspector = request.getParameter("inspector");
		
		String son_area_id = request.getParameter("son_area_id");
		String area_id = request.getParameter("area_id");
		
		map.put("WHWG", whwg);
		map.put("EQUIPMENT_CODE", equipmentCode);
		map.put("EQUIPMENT_NAME", equipmentName);
		map.put("INSPECTOR", inspector);
		map.put("TASK_NAME", taskName);
		map.put("TASK_TYPE", taskType);
		map.put("STATUS_ID", statusId);
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
		map.put("staff_id", staffId);
		
		int ifGly = staffDao.getifGly(staffId);     
		HttpSession session = request.getSession();
		
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省级管理员
			map.put("AREA_ID",area_id);
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "266");
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 是否是市级管理员
			map.put("AREA_ID",area_id);
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "288");
		}else if ((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){
			map.put("AREA_ID",area_id);
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "346");
		}else if ((Boolean) session.getAttribute(RoleNo.CABLE_NJ_SHY_B)){//光网助手南京装维审核员
				//whwg= cableMyTaskDao.getwhwg(staffId);	 
				if("".equals(son_area_id) || son_area_id ==null){
		        	 son_area_id= cableMyTaskDao.getson_area_id(staffId);	 
		         }
			map.put("AREA_ID",area_id);
			map.put("SON_AREA_ID", son_area_id);
			//map.put("WHWG", whwg);
			map.put("ROLE_ID", "368");
		}
		
		//判断是不是装维审核员角色
		else if(session.getAttribute(RoleNo.CABLE_ZW_SHY)==null?false:(Boolean) session.getAttribute(RoleNo.CABLE_ZW_SHY)){
			if("".equals(son_area_id) || son_area_id ==null){
	        	 son_area_id= cableMyTaskDao.getson_area_id(staffId);	 
	         }
			map.put("AREA_ID",area_id);
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "不为空");
			
			map.put("TEAM_ROLE", "true");//装维审核员，查询该审核员班组下的待办任务（只能查看整改任务）
		}
		
		else{
		    if("".equals(son_area_id) || son_area_id ==null){
	        	 son_area_id= cableMyTaskDao.getson_area_id(staffId);	 
	         }
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "");
		}
		map.put("COMPLETE_TIME", completeTime);
		map.put("START_TIME", startTime);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		/**
		 * 获取当前用户角色
		 */
		List<Map<String, Object>> queryEquipment = null;
		
	    queryEquipment = cableMyTaskDao.query(query);

//		List<Map<String, Object>> queryEquipment = cableMyTaskDao.query(query);
		logger.info("【待办任务列表】："+queryEquipment);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", queryEquipment);
		return pmap;
	}
	//查设备信息
	@Override
	public Map<String, Object> getEquip(HttpServletRequest request) {
 		Map<String, Object> rs = new HashMap<String, Object>();
//		String TASK_ID = request.getParameter("TASK_ID");
//		//获取所有设备
//		rs.put("TASK_ID", TASK_ID);
//		
//		List<Map<String, Object>> list = cableMyTaskDao.getMyTaskEqpPhoto(rs);
//		System.out.println();
//		List<Map<String, Object>> detailList = cableMyTaskDao.queryEquip(rs);
//		rs.put("detailList", detailList);
//		Map<String, Object> params = new HashMap<String, Object>();
//		List<Map<String, String>> photoList = null;
//		rs.put("TASK_ID", TASK_ID);
//		rs.put("areaName", cableDao.queryAreaName(request.getSession().getAttribute("areaId").toString()));
//		for(Map<String, Object> detail:detailList){
//			if("1".equals(detail.get("RECORD_TYPE").toString())||
//					"4".equals(detail.get("RECORD_TYPE").toString())){
//				String eqpIdString = detail.get("EQP_ID").toString();
//		        photoList = cableMyTaskDao.queryEqpPhoto(eqpIdString);
//		        rs.put("photoBefore", photoList);
//		        
//		    }else if("2".equals(detail.get("RECORD_TYPE").toString())){
//		    	List<Map<String, String>> photoList1 = cableMyTaskDao
//				.queryEqpPhoto(detail.get("EQP_ID").toString());
//		        rs.put("photoAfter", photoList1);
//		    }
//		}
		 
		return rs;
	}
	
	/**
	 * 审核(audit)，回单(receipt)，派单（distributeBill），退单（return）
	 * @add by wangxy 20160718
	 *
	 */
	@Override
	public String handleTask(HttpServletRequest request) {
		
		String operate = request.getParameter("operate");
		String ids = request.getParameter("ids");
		String receiver = request.getParameter("receiver");
		String receiver_a = request.getParameter("receiver_a");
		String receiver_b = request.getParameter("receiver_b");
		String remarks = request.getParameter("remark");
		String reform_demand = request.getParameter("reform_demand");//整改要求
		/*if(!"".equals(reform_demand) && reform_demand != null){
			try {
				reform_demand = new String(reform_demand.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.info("【编码错误！】");
			}
		}*/
		String complete_time = request.getParameter("complete_time");
		receiver = receiver == null ? "" : receiver;
		receiver_a = receiver_a == null ? "" : receiver_a;
		receiver_b = receiver_b == null ? "" : receiver_b;

		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TASK_ID", ids);
		params.put("STAFF_ID", staffId);
		params.put("auditor", staffId);


		Map<String, Object> flowParams = new HashMap<String, Object>();
		/**
		 * 派单，成功后将任务的审核员调整为当前登录人。
		 */
		if ("distributeBill".equals(operate)) {
			
			if (!isStatusRight(ids, "4, 5")) {
				return Result.returnError("请选择问题上报或退单的工单!").toString();
			} else {
				if("".equals(complete_time)){
					return Result.returnError("请选择整治结束时间").toString();
				}else if("".equals(reform_demand) || reform_demand == null){
					return Result.returnError("请填写整改要求！").toString();
				}
				params.put("STATUS_ID", 6);
				params.put("COMPLETE_TIME", complete_time);
				params.put("MAINTOR", receiver);
				params.put("REFORM_DEMAND", reform_demand);
				cableMyTaskDao.updateTaskHandle(params);//待办任务派单
				/**
				 * 更新审核员
				 */
				cableMyTaskDao.updateAuditor(params);
				for (String id : ids.split(",")) {
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  6);
					flowParams.put("remark", "派发工单");
					checkProcessDao.addProcess(flowParams);
				
				}
			}
		}
		/**
		 * 周期性任务派单，成功后将任务的审核员调整为当前登录人。
		 */
           if ("distributeZqBill".equals(operate)) {
			
			if (!isStatusRight(ids, "5")) {
				return Result.returnError("请选择问题上报或退单的工单!").toString();
			} else {
				params.put("STATUS_ID", 6);
				params.put("INSPECTOR", receiver);
				params.put("COMPLETE_TIME", complete_time);
				cableMyTaskDao.updateTaskHandle1(params);
				/**
				 * 更新审核员
				 */
				cableMyTaskDao.updateAuditor(params);
				for (String id : ids.split(",")) {
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  6);
					flowParams.put("remark", "派发工单");
					checkProcessDao.addProcess(flowParams);
				
				}
			}
		}
		
		
		/**
		 * 审核
		 */
		if ("audit".equals(operate)) {
			if (!isStatusRight(ids, "7")) {
				return Result.returnError("请选择待审核的工单!").toString();
			} else {
				params.put("STATUS_ID", 8);
//				params.put("MAINTOR", receiver);
				
				cableMyTaskDao.updateTaskStatus(params);
				/**
				 * 更新审核人,设置为当前登陆人
				 */
				cableMyTaskDao.updateAuditor(params);
				for (String id : ids.split(",")) {
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status", 8);
					flowParams.put("remark", "审核通过，归档");
					//(new String(remarks.getBytes("iso-8859-1"),"gb2312"))
					checkProcessDao.addProcess(flowParams);
					
					
					
				}                         
				
			}
		} 
		/**
		 * 退单
		 */
		if ("return".equals(operate)) {
			if (!isStatusRight(ids, "6")) {
				return Result.returnError("请选择待回单的工单!").toString();
			} else {
				
				params.put("STATUS_ID", 5);
				params.put("MAINTOR", receiver);
				cableMyTaskDao.updateTaskStatus(params);
				for (String id : ids.split(",")) {
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status", 5);
					flowParams.put("remark", "退单操作，原因："+remarks);
					//(new String(remarks.getBytes("iso-8859-1"),"gb2312"))
					checkProcessDao.addProcess(flowParams);
				}
			}
		}
		/**
		 * 回单
		 */
		if ("receipt".equals(operate)) {
			if(!isStatusRight(ids, "6")){
				return Result.returnError("请选择待回单的工单！").toString();
			}else{
				//插入流程表
				for (String id : ids.split(",")) {
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status","7");//待审核
					flowParams.put("remark", "等待审核");
					checkProcessDao.addProcess(flowParams);
				}
				//更新任务状态
				params.put("STATUS_ID","7");
				cableMyTaskDao.updateTaskStatus(params);
				//更新动态端子表（状态）
			}
			
		}
		
		/**
		 * 转派。
		 */
		if ("transmit".equals(operate)) {
			
			if (!isStatusRight(ids, "6,7")) {
				return Result.returnError("请选择待回单或待审核工单!").toString();
			} else {
				if("".equals(complete_time)){
					return Result.returnError("请选择整治结束时间").toString();
				}else if("".equals(reform_demand) || reform_demand == null){
					return Result.returnError("请填写整改要求！").toString();
				}
				params.put("STATUS_ID", 6);
				params.put("COMPLETE_TIME", complete_time);
				params.put("MAINTOR", receiver);
				params.put("REFORM_DEMAND", reform_demand);
				cableMyTaskDao.updateTaskHandle(params);
				/**
				 * 更新审核员
				 */
				cableMyTaskDao.updateAuditor(params);
				for (String id : ids.split(",")) {
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  6);
					flowParams.put("remark", "转派工单");
					flowParams.put("receiver", receiver);
					flowParams.put("content", "审核员转派工单");
					checkProcessDao.addProcessNew(flowParams);
//					checkProcessDao.addProcess(flowParams);
				
				}
			}
		}
		
		
		/**
		 * 转派。
		 */
		if ("transmitZq".equals(operate)) {
			
			if (!isStatusRight(ids, "6")) {
				return Result.returnError("请选择待回单工单!").toString();
			} else {
				params.put("STATUS_ID", 6);
				params.put("INSPECTOR", receiver);
				params.put("COMPLETE_TIME", complete_time);
				cableMyTaskDao.updateTaskHandle1(params);
				/**
				 * 更新审核员
				 */
				cableMyTaskDao.updateAuditor(params);
				for (String id : ids.split(",")) {
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  6);
					flowParams.put("remark", "派发工单");
					checkProcessDao.addProcess(flowParams);
				
				}
			}
		}
		
		
		
		/**
		 * 统一接单岗转发
		 */
		if ("TYJGDZF".equals(operate)) {
			
			if (!isStatusRight(ids, "4,5")) {
				return Result.returnError("请选择待派单的工单!").toString();
			} else {
				params.put("auditor", receiver_a);
				params.put("receiver_b", receiver_b);
				
				/**
				 * 更新审核员
				 */
				cableMyTaskDao.updateAuditorByTy(params);
				
				
				for (String id : ids.split(",")) {
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  4);
					flowParams.put("remark", "统一接单岗转发工单");
					checkProcessDao.addProcess(flowParams);
				
				}
			}
		}
		
		
         if ("zhuanfa".equals(operate)) {
			
			if (!isStatusRight(ids, "4,5")) {
				return Result.returnError("请选择待派单的工单!").toString();
			} else {
				params.put("auditor", receiver);
				/**
				 * 更新审核员
				 */
				cableMyTaskDao.updateAuditor(params);
				for (String id : ids.split(",")) {
					Map<String, Object> auditorlist = new HashMap<String, Object>();
					auditorlist = cableMyTaskDao.getTaskAuditorlist(id);
					params.put("task_id", id);
					String auditor_a = null ==auditorlist.get("AUDITOR_A")?"":auditorlist.get("AUDITOR_A").toString();
					String auditor_b = null ==auditorlist.get("AUDITOR_B")?"":auditorlist.get("AUDITOR_B").toString();
					if(staffId.equals(auditor_a)){
						cableMyTaskDao.updateAuditor_A(params);
					}else if (staffId.equals(auditor_b)){
						cableMyTaskDao.updateAuditor_B(params);
					}
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  4);
					flowParams.put("remark", "转派工单");
					checkProcessDao.addProcess(flowParams);
				
				}
			}
		}
         
         
         if ("revoke".equals(operate)) {//撤单
 			
 			if (!isStatusRight(ids, "5,6")) {
 				return Result.returnError("请选择待回单或已退单的工单!").toString();
 			} else {
 				for (String id : ids.split(",")) {
 					List<Map<String, Object>> dzList = cableMyTaskDao.terminalQuery(id);
 					if(null==dzList||dzList.size()==0){
 						return Result.returnError("获取端子失败").toString();		
 					}else{
 				
 					for (Map<String, Object> dzMap : dzList) {
 						cableMyTaskDao.updateDtsj(dzMap);  //撤单成功修改端子派发状态
 					}
 					cableMyTaskDao.insertDeleteTask(id);
 					cableMyTaskDao.insertDeleteTaskdetail(id);
 					cableMyTaskDao.revokeOrder(id);
 					cableMyTaskDao.revokeOrderDetail(id);
 					
 				}
 		    	}
 	     	}
         }	
		return Result.returnSuccess("").toString();
	}


	
	public boolean isStatusRight(String ids, String allowedStatus) {
		
		List<Map<String, Object>> statusList = cableMyTaskDao.getStatusIdsByTaskId(ids);
		if (statusList == null || statusList.size() < 1) {
			return false;
		}
		String status = "";
		for (Map<String, Object> map : statusList) {
			status += "," + map.get("STATUS_ID").toString();
		}
		status = status.substring(1);
		if (allowedStatus.indexOf(status) > -1) {
			return true;
		}
		return false;
	}

	
	private boolean isAllowed(String ids, String column, String currStaff,
			String operate) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("IDS", ids);
		params.put("COLUMN", column);
		List<Map<String, Object>> userList = cableMyTaskDao.getUserList(params);
		if (userList == null || userList.size() > 1 || userList.get(0) == null
				|| !currStaff.equals(userList.get(0).get(column).toString())) {
			return false;
		}
		return true;
	}
	/**
	 * 派单-查询
	 * 
	 */
	@Override
    public Map<String, Object> queryStaffList(HttpServletRequest request,UIPage pager) {
		Map map = new HashMap();
		
		String staff_name = request.getParameter("staff_name");
		String staff_id = request.getParameter("staff_id");
		String operate = request.getParameter("operate");
		String billIds = request.getParameter("billIds");
		String type = request.getParameter("type");
		String sonAreaId = request.getParameter("sonAreaId");
		String[] billId = billIds.split(",");
		String nowStaff = request.getSession().getAttribute("staffId").toString();
		
		map.put("staff_name", staff_name);
		map.put("staff_id", staff_id);
		map.put("operate", operate);
		map.put("billIds", billIds);
		map.put("sonAreaId", sonAreaId);
		if(billIds != null && !"".equals(billIds)){
			for(int i = 0;i<billId.length;i++){
				Map paramMap = new HashMap();
				paramMap.put("task_id", billId[i]);
				List<Map<String,Object>> list = cableMyTaskDao.getAreaSonAreaByTaskId(paramMap);
				for(Map areaMap : list){
					String area_id = areaMap.get("AREA_ID").toString();
					String son_area_id = areaMap.get("SON_AREA_ID").toString();
					map.put("area_id", area_id);
					map.put("son_area_id", son_area_id);
				}
				
			}
		}

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map<String, Object>>  staffRole = cableMyTaskDao.getRole(nowStaff);
		List <Map<String, Object>> staffList = new ArrayList<Map<String, Object>>();
        for(Map<String, Object> a:staffRole ){
        	String roleId = String.valueOf(a.get("ROLE_ID"));
			if("288".equals(roleId)|| "266".equals(roleId)){//省级管理员,市级管理员
				if(operate.equals("distributeBill")||operate.equals("transmit")){
				staffList = cableMyTaskDao.getStaffCity(query);//维护员
				break;
			     }else{
			    	 staffList = cableMyTaskDao.getStaffCity1(query);//检查员
			    	 break;
			     }
			}
			if("346".equals(roleId) || "306".equals(roleId)){//区级管理员,审核员
				if(operate.equals("distributeBill")||operate.equals("transmit")){
				staffList = cableMyTaskDao.getStaff(query);
				}
				else{
					staffList = cableMyTaskDao.getStaff1(query);//检查员
				}
			}
			
			
			
			
        }
			Map<String, Object> pmap = new HashMap<String, Object>(0);
			pmap.put("total", query.getPager().getRowcount());
			pmap.put("rows", staffList);
            return pmap;
			
    }
	
		

	public String getTaskByTaskId(String taskId){
		Map<String,Object> map = cableMyTaskDao.getTaskByTaskId(taskId);
		String taskType = map.get("TASK_TYPE").toString();
		return taskType;
	}
	/**
	 * TODO 问题上报详情
	 */
	@Override
	public Map<String, Object> getMyTaskEqpPhoto(HttpServletRequest request) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		String TASK_ID = request.getParameter("TASK_ID");
//		map.put("TASK_ID", TASK_ID);
//		//获取设备信息
//		List<Map<String, Object>> list = cableMyTaskDao.getMyTaskEqpPhoto(map);
//		//获取端子信息
//		List<Map<String, Object>> portList = cableMyTaskDao.queryPort(map);   
//		List<Map<String,Object>> portList1 = new ArrayList<Map<String,Object>>();
//		for (Map<String, Object> port : portList) {
//			String portId = port.get("PORT_ID").toString();
//			map.put("PORT_ID", portId);
//			List<Map<String, String>> portPhotos = cableMyTaskDao.queryPortPhoto(map);
//			List photolist = new ArrayList();
//			String photoUrl = "";
//			String microPhotoUrl= "";
//			if(portPhotos != null && portPhotos.size()>0){
//				for(Map portPhoto : portPhotos){
//					Map paramMap = new HashMap();
//					paramMap.put(portPhoto.get("PHOTO_PATH"),portPhoto.get("MICRO_PHOTO_PATH") );
////					paramMap.put("MICRO_PHOTO_PATH", portPhoto.get("MICRO_PHOTO_PATH"));
//					photolist.add(paramMap);
////					photoUrl+=portPhoto.get("PHOTO_PATH")+",";
////					microPhotoUrl+=portPhoto.get("MICRO_PHOTO_PATH")+",";
//				}
////				photoUrl= photoUrl.substring(0,photoUrl.length()-1);
////				microPhotoUrl= microPhotoUrl.substring(0,photoUrl.length()-1);
////				port.put("PHOTO_PATH", photoUrl);
////				port.put("MICRO_PHOTO_PATH", microPhotoUrl);
//				port.put("photos", photolist);
//			}
//			portList1.add(port);
//		}
//
//		//获取工单流程
//		List<Map<String, Object>> processList = cableMyTaskDao.queryProcess(map);
//		
//		map.put("taskEqpPhotoList", list);
//		map.put("portList", portList1);
//		map.put("processList", processList);
//		return map;
        Map<String, Object> map = new HashMap<String, Object>();
		
		String TASK_ID = request.getParameter("TASK_ID");
		map.put("TASK_ID", TASK_ID);
		//获取设备信息
		List<Map<String, Object>> list = cableMyTaskDao.getMyTaskEqpPhoto(map);
		//获取端子信息
		//List<Map<String, Object>> portList = doneTaskDao.queryTroubleTaskPort(map); 
		List<Map<String, Object>> portList = cableMyTaskDao.queryTroubleTaskOrder(map);
		//对查询的端子信息进行处理
		Map<String,Object> orderMap=new HashMap<String,Object>();
		List<Map<String,Object>> orderCheckedList =new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> orderZgList =new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<portList.size();i++){
			orderMap=portList.get(i);
			orderCheckedList=(List<Map<String, Object>>) orderMap.get("orderCheckedList");
			if(orderCheckedList!=null&&orderCheckedList.size()>0){
				orderZgList=(List<Map<String, Object>>) orderCheckedList.get(0).get("orderZgList");
				orderMap.put("orderZgList", orderZgList);
			}
		}

        /*Map param = new HashMap();
        Map checked = new HashMap();
		for (int i = 0; i < portList.size(); i++) {
			Map<String, Object> port = portList.get(i); 
			param.put("PORT_ID", port.get("PORT_ID"));
			param.put("TASK_ID", port.get("TASK_ID"));
			Map portChecked = doneTaskDao.portChecked(param); 
			if (portChecked!=null){
				port.putAll(portChecked);
			}
		}*/
		//获取工单流程
		Map paramMap = new HashMap();
		paramMap.put("task_id", TASK_ID);
		List<Map<String, Object>> processList = new ArrayList<Map<String,Object>>();
		processList=doneTaskDao.queryProcess(map);
		
		map.put("taskEqpPhotoList", list);
		map.put("portList", portList);
		map.put("processList", processList);
		return map;
	}
	
	/**
	 * 周期性检查详情
	 */
	@Override
	public Map<String, Object> getMyTaskEqpPhotoForZq(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String TASK_ID = request.getParameter("TASK_ID");
		map.put("TASK_ID", TASK_ID);
		
		//String areaId = cableMyTaskDao.getTaskAreaId(TASK_ID);
		//String jndi = cableInterfaceDao.getDBblinkName(areaId);
		
		//获取设备信息
		List<Map<String, Object>> eqpList =cableMyTaskDao.getMyTaskEqpPhotoForZq(map);
		List<Map<String, Object>> eqpList1 = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> eqp : eqpList){
			List<Map<String,Object>> eqpPhotoList = cableMyTaskDao.getEqpPhoto(map);
			eqp.put("eqpPhotoList",eqpPhotoList);
			eqpList1.add(eqp);
		}
		//获取端子信息
		//List<Map<String, Object>> portList = cableMyTaskDao.queryPortDetailForZq(map);
		List<Map<String, Object>> portList = cableMyTaskDao.getOrderDetail(map);
		//List<Map<String, Object>> portList1 = new ArrayList<Map<String,Object>>();
		//获取端子关联的图片信息（周期性检查工单没有照片）
		/*for(Map<String, Object> port : portList){
			map.put("PORT_ID", port.get("DZID"));
			Map<String, Object> portMap = new HashMap<String, Object>();
			portMap.put("jndi", jndi);
			portMap.put("portId", port.get("DZID"));
			List<Map> lightList = null;
			//通过端子查光路号
			try{
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			lightList = cableMyTaskDao.getOssGlList(portMap);
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
					if (String.valueOf(light.get("GLBH")) != null) {
						port.put("OSSGLBH", String.valueOf(light.get("GLBH")));
					}
				}
			}
			List<Map<String, String>> portPhotos = cableMyTaskDao.queryPortPhoto(map);
			List photolist = new ArrayList();
			String photoUrl = "";
			String microPhotoUrl= "";
			if(portPhotos != null && portPhotos.size()>0){
				for(Map portPhoto : portPhotos){
					Map paramMap = new HashMap();
					paramMap.put(portPhoto.get("PHOTO_PATH"),portPhoto.get("MICRO_PHOTO_PATH") );
					photolist.add(paramMap);
				}
				port.put("photos", photolist);
			}
			portList1.add(port);
		}*/
		
		//获取工单流程
		Map paramMap = new HashMap();
		paramMap.put("task_id", TASK_ID);
		//先通过taskid获取oldtaskid
		String oldTaskId=cableMyTaskDao.getOldTaskId_pro(paramMap);
		List<Map<String, Object>> processList = new ArrayList<Map<String,Object>>();
		if(oldTaskId==null||"".equals(oldTaskId)||"null".equals(oldTaskId)){
			processList=cableMyTaskDao.queryProcess(map);
		}else{
			paramMap.put("oldTaskId", oldTaskId);
			processList=cableMyTaskDao.queryProcessInfo(paramMap);
		}
				
		map.put("taskEqpPhotoList", eqpList1);
		map.put("portList", portList);
		map.put("processList", processList);
		return map;
	}
	/**
	 * 进入审核详情
	 */
	@Override
	public Map<String, Object> queryTaskDetailForAudit(
			HttpServletRequest request) {
		String TASK_ID = request.getParameter("TASK_ID");
		String TASK_TYPE = request.getParameter("TASK_TYPE"); 
		
		Map<String, Object> Map = new HashMap<String, Object>();
		Map<String, Object> pMap = new HashMap<String, Object>();
		Map.put("TASK_ID", TASK_ID);
		//获取工单信息
		List<Map<String, Object>> list = cableMyTaskDao.queryTaskDetailForAudit(TASK_ID);
		//获取设备信息
		List<Map<String, Object>> eqpList = new ArrayList<Map<String,Object>>();
		//获取端子信息
		List<Map<String, Object>> portList = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> portList1 = new ArrayList<Map<String,Object>>();
		if ("0".equals(TASK_TYPE)) { // 周期性检查
			
			eqpList = cableMyTaskDao.getMyTaskEqpPhotoForZq(Map);
			portList1 = cableMyTaskDao.queryPortDetailForZq(Map);
			
		} else { // 问题上报
			
//			eqpList = cableMyTaskDao.getMyTaskEqpPhoto(pMap);
//			portList = cableMyTaskDao.queryPort(pMap);   
//			// 获取关联的图片信息
//			// 获取关联的图片信息
//			for (Map<String, Object> port : portList) {
//				String portId = port.get("PORT_ID").toString();
//				pMap.put("PORT_ID", portId);
//				List<Map<String, String>> portPhotos = cableMyTaskDao.queryPortPhoto(pMap);
//				logger.info("【端子照片】：" + portPhotos);
//				List photolist = new ArrayList();
//			String photoUrl = "";
//			String microPhotoUrl= "";
//			if(portPhotos != null && portPhotos.size()>0){
//				for(Map portPhoto : portPhotos){
//					Map paramMap = new HashMap();
//					paramMap.put(portPhoto.get("PHOTO_PATH"),portPhoto.get("MICRO_PHOTO_PATH") );
////					paramMap.put("MICRO_PHOTO_PATH", portPhoto.get("MICRO_PHOTO_PATH"));
//					photolist.add(paramMap);
////					photoUrl+=portPhoto.get("PHOTO_PATH")+",";
////					microPhotoUrl+=portPhoto.get("MICRO_PHOTO_PATH")+",";
//				}
////				photoUrl= photoUrl.substring(0,photoUrl.length()-1);
////				microPhotoUrl= microPhotoUrl.substring(0,photoUrl.length()-1);
////				port.put("PHOTO_PATH", photoUrl);
////				port.put("MICRO_PHOTO_PATH", microPhotoUrl);
//				port.put("photos", photolist);
//			}else{
//					port.put("photos", "");
//					
//				}
//				
//			
//				portList1.add(port);
//			}
			
			//获取设备信息
			eqpList = cableMyTaskDao.getMyTaskEqpPhoto(Map);
			//获取端子信息
			 portList1 = doneTaskDao.queryTroubleTaskPort(Map); 

	        Map param = new HashMap();
	        Map checked = new HashMap();
			for (int i = 0; i < portList1.size(); i++) {
				Map<String, Object> port = portList1.get(i); 
				param.put("PORT_ID", port.get("PORT_ID"));
				param.put("TASK_ID", port.get("TASK_ID"));
				Map portChecked = doneTaskDao.portChecked(param); 
				if (portChecked!=null){
					port.putAll(portChecked);
				}
			}
		}
		
		//获取工单流程
		List<Map<String, Object>> processList = cableMyTaskDao.queryProcess(Map);
		
		pMap.put("taskList", list);
		pMap.put("TASK_ID", TASK_ID);
		pMap.put("taskEqpPhotoList", eqpList);
		pMap.put("portList", portList1);
		pMap.put("processList", processList);
		pMap.put("TASK_TYPE", TASK_TYPE);
		return pMap;
	}
	/**
	 * 审核
	 */
	@Override
	public String taskAudit(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		String status_id = request.getParameter("status_id"); //6不通过，置为待回单(同时要把端子状态改为不合格，再让维护人员在整改页面能看到端子，重新进行维护), 8通过，归档
		String shyj = request.getParameter("shyj");
		String audit_status="";
		String userId = request.getSession().getAttribute("staffId").toString();// 当前用户
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		map.put("status_id", status_id);
		map.put("shyj", shyj);//审核意见
		//审核不通过，在任务表中多增加一个字段audit_status 审核状态 1表示审核不通过 ，0表示审核通过
		if("6".equals(status_id)){
			audit_status="1";
			map.put("audit_status", audit_status);//审核不通过 置为1
			cableMyTaskDao.updateTaskStatusByAudit(map);
		}else{
			audit_status="0";
			map.put("audit_status", audit_status);//审核通过 置为0
			cableMyTaskDao.updateTaskStatusByAudit(map);
		}
		
		
		//审核不通过，将整改端子状态改为不合格（先要获取整改的端子，然后遍历将其状态改为不合格）
		if("6".equals(status_id)){   
			List<Map<String, Object>> zgPortsList=cableMyTaskDao.getDisagrementPorts(taskId);
			Map map1=new HashMap();
			if(zgPortsList!=null&&zgPortsList.size()>0){
				for(int i=0;i<zgPortsList.size();i++){
					Map zgMap=zgPortsList.get(i);				
					String port_id=zgMap.get("PORT_ID").toString();			
					map1.put("port_id", port_id);
					map1.put("taskId", taskId);
					cableMyTaskDao.update_zgdz_status(map1);
				}
			}	
		}
		//保存工单流程
		Map<String, Object> flowParams = new HashMap<String, Object>();
		flowParams.put("oper_staff", userId);
		flowParams.put("task_id", taskId);
		flowParams.put("status",  status_id);
		if("6".equals(status_id)){
			flowParams.put("remark", "审核不通过，改为待回单！");
		}else{
			flowParams.put("remark", "审核通过，归档！");
		}
		checkProcessDao.addProcess(flowParams);
		//只有审核通过才会转发到第二个审核员
		if("8".equals(status_id)){
			Map<String, Object> auditorlist = new HashMap<String, Object>();
			auditorlist = cableMyTaskDao.getTaskAuditorlist(taskId);
			String auditor =null == auditorlist.get("ISSECOND")?"":auditorlist.get("AUDITOR").toString();
			String issecond= null == auditorlist.get("ISSECOND")?"":auditorlist.get("ISSECOND").toString();
			String auditor_a = null ==auditorlist.get("AUDITOR_A")?"":auditorlist.get("AUDITOR_A").toString();
			String auditor_b = null ==auditorlist.get("AUDITOR_B")?"":auditorlist.get("AUDITOR_B").toString();
			System.out.println("0".equals(issecond));
			if (!"".equals(auditor_b)&&null!=auditor_b&&!"0".equals(issecond)) {
				cableMyTaskDao.updateAuditorAndStautus(taskId);
				Map<String, Object> flowParams1 = new HashMap<String, Object>();
				flowParams1.put("oper_staff", auditor_b);
				flowParams1.put("task_id", taskId);
				flowParams1.put("status",  4);
				flowParams1.put("remark", "第二审核员接单待派单");
				
				checkProcessDao.addProcess(flowParams1);				
			}
		}
		String sysRoute ="GWZS";
		String oprType ="";
		if(status_id.equals("8")){
			 oprType ="1";
		}else{
			 oprType ="2";
		}
		String xml="";
		String result="";
		xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
		"<IfInfo><sysRoute>"+sysRoute+"</sysRoute>"+
			"<task_id>"+taskId+"</task_id>"+
			"<oprType>"+oprType+"</oprType>"+
		"</IfInfo>";
		
		WfworkitemflowLocator locator =new WfworkitemflowLocator();
		try {
			WfworkitemflowSoap11BindingStub stub=(WfworkitemflowSoap11BindingStub)locator.getWfworkitemflowHttpSoap11Endpoint();
			result=stub.outSysAppOperating(xml);
			System.out.println(xml);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.returnSuccess(result).toString();
	}
	@Override
	public Map getTaskObjByTaskId(String taskId) {
 		return cableMyTaskDao.getTaskByTaskId(taskId);
	}
	@Override
	public void getTaskDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> paras = new HashMap<String, Object>();
		String statusId = request.getParameter("STATUS_ID");
		String taskName = request.getParameter("TASK_NAME");
		String taskType = request.getParameter("TASK_TYPE");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String equipmentName =request.getParameter("equipmentName");
		String inspector =request.getParameter("inspector");
		
		String son_area_id = request.getParameter("son_area_id");
		String area_id = request.getParameter("area_id");
		
		paras.put("WHWG", whwg);
		paras.put("EQUIPMENT_CODE", equipmentCode);
		paras.put("EQUIPMENT_NAME", equipmentName);
		paras.put("INSPECTOR", inspector);
		paras.put("TASK_NAME", taskName);
		paras.put("TASK_TYPE", taskType);
		paras.put("STATUS_ID", statusId);
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
		paras.put("staff_id", staffId);
		
		int ifGly = staffDao.getifGly(staffId);     
		HttpSession session = request.getSession();
		
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省级管理员
			paras.put("AREA_ID",area_id);
			paras.put("SON_AREA_ID", son_area_id);
			paras.put("ROLE_ID", "266");
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 是否是市级管理员
			paras.put("AREA_ID",area_id);
			paras.put("SON_AREA_ID", son_area_id);
			paras.put("ROLE_ID", "288");
		}else if ((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){
			paras.put("AREA_ID",area_id);
			paras.put("SON_AREA_ID", son_area_id);
			paras.put("ROLE_ID", "346");
		}else{
		    if("".equals(son_area_id) || son_area_id ==null){
	        	 son_area_id= cableMyTaskDao.getson_area_id(staffId);	 
	         }
		    paras.put("SON_AREA_ID", son_area_id);
		    paras.put("ROLE_ID", "");
		}
		paras.put("COMPLETE_TIME", completeTime);
		paras.put("START_TIME", startTime);
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","综合化维护网格",
				"任务名称", "任务来源", "任务状态", "检查员 ","维护员 ","审核员","设备编码","现场规范","整改要求","是否已派单","是否已整改","开始时间","结束时间","上次更新时间"});
		List<String> code = Arrays.asList(new String[] { "AREA_ID","SON_AREA_ID","ZHHWHWG",
				"TASK_NAME", "TASK_TYPE", "STATUS_ID", "INSPECTOR", "MAINTOR" ,"AUDITOR","EQUIPMENT_CODE","REMARK","INFO","SFYPD","SFZG","START_TIME","COMPLETE_TIME","LAST_UPDATE_TIME"});
		
		List<Map<String, Object>> data = cableMyTaskDao.queryDown(paras);
		   
		String fileName = "待办任务";
		String firstLine = "待办任务";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	@Override
	public void intoFinish(HttpServletRequest request,
			HttpServletResponse response) {		
		String TASK_ID= request.getParameter("TASK_ID");
		String userId = request.getSession().getAttribute("staffId").toString();// 当前用户
		//获取审核员的区域信息
	    Map<String, Object> areaMap =cableMyTaskDao.getAreaByUser(userId);
		String areaId = areaMap.get("AREA_ID").toString();
		String sonAreaId = areaMap.get("SON_AREA_ID").toString();
		
		Map map = new HashMap();
		map.put("TASK_ID", TASK_ID);
		//获取设备信息
		List<Map<String, Object>> list = cableMyTaskDao.getMyTaskEqpPhoto(map);
		//获取端子信息
		List<Map<String, Object>> portList = doneTaskDao.queryTroubleTaskPortToFinish(map); 
		Map eqpParam = new HashMap();
		String eqpid="";
		String eqpno="";
		String eqpname="";
		String eqpaddress="";
		String area_id="";
		String son_area_id="";
		for(Map eqpMap:list){
			eqpid=eqpMap.get("eqp_id").toString();
			eqpno=eqpMap.get("eqp_no").toString();
			eqpname=eqpMap.get("eqp_name").toString();
			eqpaddress=eqpMap.get("eqpaddress").toString();
			area_id=eqpMap.get("area_id").toString();
			son_area_id=eqpMap.get("son_area_id").toString();
			eqpParam.put("eqpId", eqpid);
			eqpParam.put("eqpNo", eqpno);
			eqpParam.put("eqpName", eqpname);
			eqpParam.put("longitude", "");
			eqpParam.put("latitude", "");
			eqpParam.put("comments", "");
			eqpParam.put("eqpAddress", eqpaddress);
			eqpParam.put("remarks", "");
			eqpParam.put("info", "审核员直接回单!");
			eqpParam.put("staffId", userId);
			eqpParam.put("record_type", "2");
			eqpParam.put("area_id", area_id);
			eqpParam.put("son_area_id", son_area_id);
			eqpParam.put("task_id", TASK_ID);
		}
		
		for (int i = 0; i < portList.size(); i++) {
			Map<String, Object> port = portList.get(i); 
			String portId=port.get("PORT_ID").toString();
			String portNo=port.get("PORT_NO").toString();
			String portName=port.get("PORT_NAME").toString();
			String isH = port.get("H").toString();		
			int recordId = checkOrderDao.getRecordId();
			eqpParam.put("recordId", recordId);
			eqpParam.put("port_id", portId);
			eqpParam.put("port_no", portNo);
			eqpParam.put("port_name", portName);
			eqpParam.put("descript", "");//端子情况
			eqpParam.put("isCheckOK", "0");			
			eqpParam.put("isH", "");
			eqpParam.put("port_info", "");
			checkOrderDao.insertEqpRecord(eqpParam);		
		}	
		//保存设备信息
		int recordId = checkOrderDao.getRecordId();
		Map recordMap = new HashMap();
		recordMap.put("recordId", recordId);
		recordMap.put("task_id", TASK_ID);
		recordMap.put("detail_id", "");
		recordMap.put("eqpId", eqpid);
		recordMap.put("eqpAddress", eqpaddress);
		recordMap.put("eqpNo", eqpno);
		recordMap.put("staffId", userId);
		recordMap.put("eqpName", eqpname);
		recordMap.put("remarks", "");
		recordMap.put("info", "审核员直接回单!");
		recordMap.put("longitude", "");
		recordMap.put("latitude", "");
		recordMap.put("comments", "");
		recordMap.put("port_id", "");
		recordMap.put("port_no", "");
		recordMap.put("port_name", "");
		recordMap.put("remark", "审核员直接回单!");
		recordMap.put("descript", "");
		recordMap.put("isCheckOK", "0");
		recordMap.put("record_type", "2");
		recordMap.put("area_id", areaId);
		recordMap.put("son_area_id", sonAreaId);
		recordMap.put("isH", "");
		checkOrderDao.insertEqpRecord(recordMap);
	
		
		String status_id="8";
		Map<String, Object> flowParams = new HashMap<String, Object>();
		flowParams.put("oper_staff", userId);
		flowParams.put("task_id", TASK_ID);
		flowParams.put("status",  status_id);
		flowParams.put("remark", "审核员直接归档!");	
		cableMyTaskDao.intoFinish(flowParams);//将问题上报待派单任务状态改为已归档		
		checkProcessDao.addProcess(flowParams);//保存工单流程
	}
	/**
	 * TODO 南京整改单
	 */
	@Override
	public Map<String, Object> getReformOrder(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		String statusId = request.getParameter("STATUS_ID");
		String taskName = request.getParameter("TASK_NAME");
		String taskType = request.getParameter("TASK_TYPE");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String company = request.getParameter("remarks");
		String INSPECTOR = request.getParameter("INSPECTOR");
		String sblx = request.getParameter("sblx");
		

		String son_area_id = request.getParameter("son_area_id");
		String area_id = request.getParameter("area_id");
		
		map.put("WHWG", whwg);
		map.put("INSPECTOR", INSPECTOR);
		map.put("sblx", sblx);
		map.put("EQUIPMENT_CODE", equipmentCode);
		map.put("TASK_NAME", taskName);
		map.put("TASK_TYPE", taskType);
		map.put("STATUS_ID", statusId);
		map.put("company", company);
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
		map.put("staff_id", staffId);
		
		int ifGly = staffDao.getifGly(staffId);     
		HttpSession session = request.getSession();
		
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省级管理员
			map.put("AREA_ID",area_id);
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "266");
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 是否是市级管理员
			map.put("AREA_ID",area_id);
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "288");
		}else if ((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){
			map.put("AREA_ID",area_id);
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "346");
		}else{
		    if("".equals(son_area_id) || son_area_id ==null){
	        	 son_area_id= cableMyTaskDao.getson_area_id(staffId);	 
	         }
			map.put("SON_AREA_ID", son_area_id);
			map.put("ROLE_ID", "");
		}
		
		map.put("COMPLETE_TIME", completeTime);
		map.put("START_TIME", startTime);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		/**
		 * 获取当前用户角色
		 */
		List<Map<String, Object>> queryEquipment = null;
		if ((Boolean) session.getAttribute(RoleNo.CABLE_TYJDG)) {
			queryEquipment = cableMyTaskDao.queryByTYJDG(query);
		} else{
			queryEquipment = cableMyTaskDao.queryBySHY(query);
		}
//		List<Map<String, Object>> queryEquipment = cableMyTaskDao.query(query);
		logger.info("【待办任务列表】："+queryEquipment);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", queryEquipment);
		return pmap;
	}

	@Override
	public Map<String, Object> queryStaff_SHY(HttpServletRequest request,
			UIPage pager) {	
		Map map = new HashMap();

		String staff_name = request.getParameter("staff_name");
		String staff_id = request.getParameter("staff_id");
		String operate = request.getParameter("operate");
		String billIds = request.getParameter("billIds");
		String type = request.getParameter("type");
		String sonAreaId = request.getParameter("sonAreaId");
		String roleid = request.getParameter("role_id");
		String userId = request.getSession().getAttribute("staffId")
				.toString();
		if(billIds != null && !"".equals(billIds)){
			String[] billId = billIds.split(",");
			for(int i = 0;i<billId.length;i++){
				Map paramMap = new HashMap();
				paramMap.put("task_id", billId[i]);
				List<Map<String,Object>> list = cableMyTaskDao.getAreaSonAreaByTaskId(paramMap);
				for(Map areaMap : list){
					String area_id = areaMap.get("AREA_ID").toString();
					String son_area_id = areaMap.get("SON_AREA_ID").toString();
					map.put("area_id", area_id);
					map.put("son_area_id", son_area_id);
				}
				
			}
		}
		
		map.put("staff_name", staff_name);
		map.put("staff_id", staff_id);
		map.put("roleid", roleid);
		map.put("operate", operate);
		map.put("billIds", billIds);
		map.put("sonAreaId", sonAreaId);
		Map<String, Object> area_list = cableMyTaskDao.getAreaByUser(userId);
		String area_id = String.valueOf(area_list.get("AREA_ID"));
		map.put("area_id", area_id);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map<String, Object>> staffRole = cableMyTaskDao.getRole(userId);
		List<Map<String, Object>> staffList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> a : staffRole) {
			String roleId = String.valueOf(a.get("ROLE_ID"));
			if ("366".equals(roleId)) {// 南京统一接单   如果账号ROLE_ID匹配到了，就不要再循环了
				if ("distributeBill".equals(operate)
						|| "transmit".equals(operate)){
					staffList = cableMyTaskDao.getwhyStaff(query);		
				}else {
					staffList = cableMyTaskDao.getAShyStaff(query);		
				}
					
			}

			if("367".equals(roleId)){//南京A审核员	
				if (operate.equals("distributeBill")
						|| operate.equals("transmit")) {
					staffList = cableMyTaskDao.getNjZongZgStaff(query);
				} else if (operate.equals("zhuanfa")) {
					staffList = cableMyTaskDao.getAShyStaff1(query);
				}
			}
		
			if("368".equals(roleId)){//南京B审核员			
				if (operate.equals("distributeBill")
						|| operate.equals("transmit")) {
					staffList = cableMyTaskDao.getNjZhuangZgStaff(query);
				} else if (operate.equals("zhuanfa")) {
					staffList = cableMyTaskDao.getAShyStaff1(query);
				}						
			}
			if("386".equals(roleId)){//南京网维审核员			
				if (operate.equals("distributeBill")
						|| operate.equals("transmit")) {
					staffList = cableMyTaskDao.getNjWangZgStaff(query);
				} else if (operate.equals("zhuanfa")) {
					staffList = cableMyTaskDao.getAShyStaff1(query);
				}						
			}
			if("388".equals(roleId)){//南京工程审核员			
				if (operate.equals("distributeBill")
						|| operate.equals("transmit")) {
					staffList = cableMyTaskDao.getNjGongZgStaff(query);
				} else if (operate.equals("zhuanfa")) {
					staffList = cableMyTaskDao.getAShyStaff1(query);
				}						
			}
			if("394".equals(roleId)){//南京政支审核员			
				if (operate.equals("distributeBill")
						|| operate.equals("transmit")) {
					staffList = cableMyTaskDao.getNjZhengZgStaff(query);
				} else if (operate.equals("zhuanfa")) {
					staffList = cableMyTaskDao.getAShyStaff1(query);
				}						
			}
			if("391".equals(roleId)){//南京无线审核员			
				if (operate.equals("distributeBill")
						|| operate.equals("transmit")) {
					staffList = cableMyTaskDao.getNjWuZgStaff(query);
				} else if (operate.equals("zhuanfa")) {
					staffList = cableMyTaskDao.getAShyStaff1(query);
				}						
			}
			
			
			
			

		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", staffList);
		return pmap;

	}
	
	
	/**
	 * TODO 南京整改单问题上报详情
	 */
	@Override
	public Map<String, Object> getMyTaskEqpPhotoForNj(HttpServletRequest request) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		String TASK_ID = request.getParameter("TASK_ID");
//		map.put("TASK_ID", TASK_ID);
//		//获取设备信息
//		List<Map<String, Object>> list = cableMyTaskDao.getMyTaskEqpPhoto(map);
//		//获取端子信息
//		List<Map<String, Object>> portList = cableMyTaskDao.queryPort(map);   
//		List<Map<String,Object>> portList1 = new ArrayList<Map<String,Object>>();
//		for (Map<String, Object> port : portList) {
//			String portId = port.get("PORT_ID").toString();
//			map.put("PORT_ID", portId);
//			List<Map<String, String>> portPhotos = cableMyTaskDao.queryPortPhoto(map);
//			List photolist = new ArrayList();
//			String photoUrl = "";
//			String microPhotoUrl= "";
//			if(portPhotos != null && portPhotos.size()>0){
//				for(Map portPhoto : portPhotos){
//					Map paramMap = new HashMap();
//					paramMap.put(portPhoto.get("PHOTO_PATH"),portPhoto.get("MICRO_PHOTO_PATH") );
////					paramMap.put("MICRO_PHOTO_PATH", portPhoto.get("MICRO_PHOTO_PATH"));
//					photolist.add(paramMap);
////					photoUrl+=portPhoto.get("PHOTO_PATH")+",";
////					microPhotoUrl+=portPhoto.get("MICRO_PHOTO_PATH")+",";
//				}
////				photoUrl= photoUrl.substring(0,photoUrl.length()-1);
////				microPhotoUrl= microPhotoUrl.substring(0,photoUrl.length()-1);
////				port.put("PHOTO_PATH", photoUrl);
////				port.put("MICRO_PHOTO_PATH", microPhotoUrl);
//				port.put("photos", photolist);
//			}
//			portList1.add(port);
//		}
//
//		//获取工单流程
//		List<Map<String, Object>> processList = cableMyTaskDao.queryProcess(map);
//		
//		map.put("taskEqpPhotoList", list);
//		map.put("portList", portList1);
//		map.put("processList", processList);
//		return map;
        Map<String, Object> map = new HashMap<String, Object>();
		
		String TASK_ID = request.getParameter("TASK_ID");
		map.put("TASK_ID", TASK_ID);
		//获取设备信息
		List<Map<String, Object>> list = cableMyTaskDao.getMyTaskEqpPhoto(map);
		//获取端子信息
		List<Map<String, Object>> portList = cableMyTaskDao.queryTroubleTaskPort(map); 

        Map param = new HashMap();
        Map checked = new HashMap();
		for (int i = 0; i < portList.size(); i++) {
			Map<String, Object> port = portList.get(i); 
			param.put("PORT_ID", port.get("PORT_ID"));
			param.put("TASK_ID", port.get("TASK_ID"));
			Map portChecked = doneTaskDao.portChecked(param); 
			if (portChecked!=null){
				port.putAll(portChecked);
			}
		}
		//获取工单流程
		List<Map<String, Object>> processList = doneTaskDao.queryProcess(map);
		
		map.put("taskEqpPhotoList", list);
		map.put("portList", portList);
		map.put("processList", processList);
		return map;
	}
	@Override
	public Map<String, Object> getPort(HttpServletRequest request, UIPage pager) {
		String taskId = request.getParameter("taskId");
		Map portParamMap = new HashMap<String, String>();

		portParamMap.put("taskId", taskId);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(portParamMap);
		List<Map<String, Object>> portlist = cableMyTaskDao.getPortMessage(query);

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", portlist);
		return pmap;
	}
	@Override
	public String updatePort(HttpServletRequest request) {

		
		String operate = request.getParameter("operate");
		String ids = request.getParameter("billIds");
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("record_id", ids);
		


		Map<String, Object> flowParams = new HashMap<String, Object>();
		
		String company = null ;
		if ("zongwei".equals(operate)) {
				company = "0";
		}else if ("zhuangwei".equals(operate)) {			
			company = "1";
       }else if ("wangwei".equals(operate)) {			
			company = "2";
       }else if ("gongcheng".equals(operate)) {			
			company = "3";
       }else if ("zhengzhi".equals(operate)) {			
			company = "4";
       }else if ("wuxian".equals(operate)) {			
			company = "5";
       }
		params.put("company", company);	
		cableMyTaskDao.updateDz(params);
		return Result.returnSuccess("").toString();
	
	}
	
	/*
	 * 部门审核员退单  加入退单原因 （暂时不加），加入退单流程   谁派退谁  通过流程来追踪上一个转发者  *现加入维护员退单后再退单情况
	 * 通过STATUS 与 OPER_STAFF 的角色共同判断，可能一个账号有多个角色，可以知道退给谁，但是不确定任务状态，退单流程，所以两个字段共同判断
	 * */
	@Override
	public void cancel(HttpServletRequest request,
			HttpServletResponse response) {		
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户				
		String arr = request.getParameter("arr");
		String content = request.getParameter("content");
		Map map =new HashMap();
		map.put("content", content);
		Map<String, Object> flowParams = new HashMap<String, Object>();
		for(String id : arr.split(",")){			
			flowParams.clear();			
			map.put("taskId", id);		
			//找出流程记录
			List<Map<String,Object>> staffList=checkProcessDao.queryProcess_oper_staff_ByTaskId(id);
			Map oper_staff=staffList.get(0);
			String operator=oper_staff.get("OPER_STAFF").toString();
			String status=oper_staff.get("STATUS").toString();
			map.put("operator", operator);
			//获取上一个操作者的角色
			List<Map<String,Object>> roleList=cableMyTaskDao.getOperStaffRole(operator);
			for(Map role:roleList){//366 部门审核员退单到南京市统一接单岗
				String roleId=role.get("ROLE_ID").toString();
				if("366".equals(roleId)&&"4".equals(status)){
					cableMyTaskDao.cancel(map);
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  4);
					flowParams.put("remark", "审核员退单,退单原因: "+content);
					checkProcessDao.addProcess(flowParams);	
					break;
				}
				if("4".equals(status)&&("367".equals(roleId)||"394".equals(roleId)||"388".equals(roleId)//
						||"391".equals(roleId)||"386".equals(roleId))){//367  394  388  368  391   386 退给部门审核员
					cableMyTaskDao.cancelZg(map);
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  4);
					flowParams.put("remark", "审核员退单,退单原因: "+content);
					checkProcessDao.addProcess(flowParams);						
					break;
				}if("5".equals(status)){//部门审核员退给维护员
					cableMyTaskDao.cancelWhy(map);
					flowParams.put("oper_staff", staffId);
					flowParams.put("task_id", id);
					flowParams.put("status",  6);
					flowParams.put("remark", "审核员退单,退单原因: "+content);
					checkProcessDao.addProcess(flowParams);	
					break;
				}
			}			
		}
		
		/*Map<String, Object> flowParams = new HashMap<String, Object>();
		for (String id : arr.split(",")) {			
			flowParams.put("oper_staff", staffId);
			flowParams.put("task_id", id);
			flowParams.put("status",  4);
			flowParams.put("remark", "审核员退单,退单原因: "+content);
			checkProcessDao.addProcess(flowParams);		
		}*/
		
	}
	@Override
	public Map<String, Object> searchStaff_SHY(HttpServletRequest request,
			UIPage pager) {
		String staff_name=request.getParameter("staff_name");
		String staff_no=request.getParameter("staff_no");
		String role_id=request.getParameter("roleid");
		String userId = request.getSession().getAttribute("staffId").toString();
		
		Map<String, Object> area_list = cableMyTaskDao.getAreaByUser(userId);
		String area_id = String.valueOf(area_list.get("AREA_ID"));
		
		Map auditorParamMap = new HashMap<String, String>();

		auditorParamMap.put("staff_name", staff_name);
		auditorParamMap.put("staff_no", staff_no);
		auditorParamMap.put("role_id", role_id);
		auditorParamMap.put("area_id", area_id);//转发给不同部门审核员时，可能会跨区域，所以只给了地市限制，区域就不作条件限制了

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(auditorParamMap);
		List<Map<String, Object>> auditorlist = cableMyTaskDao.searchStaff_SHY(query);

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", auditorlist);
		return pmap;
	}
	@Override
	public void cancelTask(HttpServletRequest request,
			HttpServletResponse response) {
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
		String arr = request.getParameter("ids");
		String content = request.getParameter("content");
		if(content==null){
			content="";
		}
		Map map =new HashMap();
		map.put("TASK_ID", arr);
		map.put("content", content);
		
		for(String id:arr.split(",")){
			
			String old_task_id=cableMyTaskDao.getOldTaskId(id);
			map.put("old_task_id", old_task_id);
			if(!(old_task_id==null)){
				/*
				 *周期性任务处理方式 
				 */
				//通过 old_task_id 查询出周期性任务的检查人
			    String inspector=cableMyTaskDao.queryInspectorByOldTaskId(old_task_id);
				//审核员退单，检查员检查错误，修改周期性任务状态，置为待回单
				cableMyTaskDao.updateCheckTasks(map);
				//删除记录表中的数据
				cableMyTaskDao.deleteCheckRecords(old_task_id);
				//删除周期性任务归档流程
				//cableMyTaskDao.deleteCheckProcess(old_task_id);
				/*//修改任务详情表中的数据 check_flag=0
				cableMyTaskDao.updateCheckTaskDetail(old_task_id);
				//删除任务详情表中的设备信息 
				cableMyTaskDao.deleteCheckTaskDetail(old_task_id);*/
				cableMyTaskDao.deleteCheckTaskDetails_records(old_task_id);
				//删除任务详情表中的检查记录
				//删除检查任务上报后的流程后，再将审核员退单给检查员的流程加上去
				Map<String, Object> flowParams = new HashMap<String, Object>();
				flowParams.put("oper_staff", staffId);
				flowParams.put("task_id", old_task_id);
				flowParams.put("status",  6);
				flowParams.put("remark", "审核员退单");
				flowParams.put("receiver", inspector );
				flowParams.put("content", "审核员发现错误、退单,原因："+content);
				checkProcessDao.addProcessNew(flowParams);			
				/*
				 * 整改任务处理方式（问题上报任务检查）
				 */
				//先保存检查员上报上来的整改任务
				cableMyTaskDao.insertDeleteTask(id);
				//审核员退单，检查员检查错误，删除上报上来的整改任务		
				cableMyTaskDao.deleteCheckTasks(id);	
				//审核员退单，检查员检查错误，删除任务详情中的数据
				cableMyTaskDao.deleteCheckTaskDetails(id);
				//删除检查记录表长得数据
				cableMyTaskDao.deleteRecords(id);
				//审核员退单，检查员检查错误，删除流程表中数据
				cableMyTaskDao.deleteProcesss(id);
												
			}else{//隐患上报  一键反馈  不预告抽查
				//先保存检查员上报上来的隐患任务
				cableMyTaskDao.insertDeleteTask(id);
				//审核员退单，检查员检查错误，删除上报上来的整隐患任务		
				cableMyTaskDao.deleteCheckTasks(id);	
				//审核员退单，检查员检查错误，删除任务详情中的数据
				cableMyTaskDao.deleteCheckTaskDetails(id);
				//删除检查记录表长得数据
				cableMyTaskDao.deleteRecords(id);
				//审核员退单，检查员检查错误，删除流程表中数据
				cableMyTaskDao.deleteProcesss(id);
			}
			
			
		}
	}
	
	@Override
	public void getTaskDownloadByNj(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> paras = new HashMap<String, Object>();
		String statusId = request.getParameter("STATUS_ID");
		String taskName = request.getParameter("TASK_NAME");
		String taskType = request.getParameter("TASK_TYPE");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String equipmentName =request.getParameter("equipmentName");
		String inspector =request.getParameter("inspector");
		
		String son_area_id = request.getParameter("son_area_id");
		String area_id = request.getParameter("area_id");
		
		paras.put("WHWG", whwg);
		paras.put("EQUIPMENT_CODE", equipmentCode);
		paras.put("EQUIPMENT_NAME", equipmentName);
		paras.put("INSPECTOR", inspector);
		paras.put("TASK_NAME", taskName);
		paras.put("TASK_TYPE", taskType);
		paras.put("STATUS_ID", statusId);
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
		paras.put("staff_id", staffId);
		
		int ifGly = staffDao.getifGly(staffId);     
		HttpSession session = request.getSession();
		
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省级管理员
			paras.put("AREA_ID",area_id);
			paras.put("SON_AREA_ID", son_area_id);
			paras.put("ROLE_ID", "266");
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 是否是市级管理员
			paras.put("AREA_ID",area_id);
			paras.put("SON_AREA_ID", son_area_id);
			paras.put("ROLE_ID", "288");
		}else if ((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){
			paras.put("AREA_ID",area_id);
			paras.put("SON_AREA_ID", son_area_id);
			paras.put("ROLE_ID", "346");
		}else{
		    if("".equals(son_area_id) || son_area_id ==null){
	        	 son_area_id= cableMyTaskDao.getson_area_id(staffId);	 
	         }
		    paras.put("SON_AREA_ID", son_area_id);
		    paras.put("ROLE_ID", "");
		}
		paras.put("COMPLETE_TIME", completeTime);
		paras.put("START_TIME", startTime);
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","综合化维护网格",
				"任务名称", "任务来源", "任务状态", "检查员 ","维护员 ","审核员","设备编码","现场规范","整改要求","是否已派单","是否已整改","开始时间","结束时间","上次更新时间"});
		List<String> code = Arrays.asList(new String[] { "AREA_ID","SON_AREA_ID","ZHHWHWG",
				"TASK_NAME", "TASK_TYPE", "STATUS_ID", "INSPECTOR", "MAINTOR" ,"AUDITOR","EQUIPMENT_CODE","REMARK","INFO","SFYPD","SFZG","START_TIME","COMPLETE_TIME","LAST_UPDATE_TIME"});
		
		
		
		
		/**
		 * 获取当前用户角色
		 */
		List<Map<String, Object>> data = null;
		if ((Boolean) session.getAttribute(RoleNo.CABLE_TYJDG)) {
			data = cableMyTaskDao.downByTYJDG(paras);
		} else{
			data = cableMyTaskDao.downBySHY(paras);
		}
		   
		String fileName = "南京市统一整改单";
		String firstLine = "南京市统一整改单";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 新审核
	 */
	@Override
	public String taskNewAudit(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		String status_id = request.getParameter("status_id"); //6不通过，置为待回单(同时要把端子状态改为不合格，再让维护人员在整改页面能看到端子，重新进行维护), 8通过，归档
		String shyj = request.getParameter("shyj");
		String audit_status="";
		String userId = request.getSession().getAttribute("staffId").toString();// 当前用户
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		map.put("status_id", status_id);
		map.put("shyj", shyj);//审核意见
		//审核不通过，在任务表中多增加一个字段audit_status 审核状态 1表示审核不通过 ，0表示审核通过
		if("6".equals(status_id)){
			audit_status="1";
			map.put("audit_status", audit_status);//审核不通过 置为1
			cableMyTaskDao.updateTaskStatusByAudit(map);
		}else{
			audit_status="0";
			map.put("audit_status", audit_status);//审核通过 置为0
			cableMyTaskDao.updateTaskStatusByAudit(map);
		}
		
		
		//审核不通过，将整改端子状态改为不合格（先要获取整改的端子，然后遍历将其状态改为不合格）
		if("6".equals(status_id)){   
			List<Map<String, Object>> zgPortsList=cableMyTaskDao.getDisagrementPorts(taskId);
			Map map1=new HashMap();
			if(zgPortsList!=null&&zgPortsList.size()>0){
				for(int i=0;i<zgPortsList.size();i++){
					Map zgMap=zgPortsList.get(i);				
					String port_id=zgMap.get("PORT_ID").toString();			
					map1.put("port_id", port_id);
					map1.put("taskId", taskId);
					cableMyTaskDao.update_zgdz_status(map1);
				}
			}	
		}
		//保存工单流程
		Map<String, Object> flowParams = new HashMap<String, Object>();
		flowParams.put("oper_staff", userId);
		flowParams.put("task_id", taskId);
		flowParams.put("status",  status_id);
		
		if("6".equals(status_id)){
			//审核不通过，通过taskid获取维护员
			String maintor=cableMyTaskDao.getMaintorByTaskId(taskId);
			flowParams.put("receiver", maintor);
			flowParams.put("content", "端子审核不通过，退回给维护员");
			flowParams.put("remark", "审核");
			checkProcessDao.addProcessNew(flowParams);
		}
		
		//审核通过
		if("8".equals(status_id)){
			/*Map<String, Object> auditorlist = new HashMap<String, Object>();
			auditorlist = cableMyTaskDao.getTaskAuditorlist(taskId);
			String auditor =null == auditorlist.get("ISSECOND")?"":auditorlist.get("AUDITOR").toString();
			String issecond= null == auditorlist.get("ISSECOND")?"":auditorlist.get("ISSECOND").toString();
			String auditor_a = null ==auditorlist.get("AUDITOR_A")?"":auditorlist.get("AUDITOR_A").toString();
			String auditor_b = null ==auditorlist.get("AUDITOR_B")?"":auditorlist.get("AUDITOR_B").toString();
			System.out.println("0".equals(issecond));*/
		//	if (!"".equals(auditor_b)&&null!=auditor_b&&!"0".equals(issecond)) {
		//		cableMyTaskDao.updateAuditorAndStautus(taskId);
				Map<String, Object> flowParams1 = new HashMap<String, Object>();
				flowParams1.put("oper_staff", userId);
				flowParams1.put("task_id", taskId);
				flowParams1.put("status",  8);
				flowParams1.put("remark", "审核");
				flowParams1.put("receiver", "");
				flowParams1.put("content", "工单审核通过，任务已归档");
				checkProcessDao.updateTask(flowParams1);
				checkProcessDao.addProcessNew(flowParams1);		
	//		}
		}
		String sysRoute ="GWZS";
		String oprType ="";
		if(status_id.equals("8")){
			 oprType ="1";
		}else{
			 oprType ="2";
		}
		String xml="";
		String result="";
		xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
		"<IfInfo><sysRoute>"+sysRoute+"</sysRoute>"+
			"<task_id>"+taskId+"</task_id>"+
			"<oprType>"+oprType+"</oprType>"+
		"</IfInfo>";
		
		WfworkitemflowLocator locator =new WfworkitemflowLocator();
		try {
			WfworkitemflowSoap11BindingStub stub=(WfworkitemflowSoap11BindingStub)locator.getWfworkitemflowHttpSoap11Endpoint();
			result=stub.outSysAppOperating(xml);
			System.out.println(xml);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.returnSuccess(result).toString();
	}
	@Override
	public Map<String, Object> queryDouDi(HttpServletRequest request,
			UIPage pager) {
		// TODO Auto-generated method stub
		String role_id = request.getParameter("role_id");//1.兜底岗 2.维护员
		String staff_name = request.getParameter("staff_name").trim();
		String staff_no = request.getParameter("staff_no").trim();
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		Map map=new HashMap();
		map.put("staff_name", staff_name);
		map.put("staff_no", staff_no);
		//通过登录人员的账号ID查询登录人员所在的地市区域
		//获取登录人员的角色
		Boolean admin=(Boolean)request.getSession().getAttribute(RoleNo.CABLE_ADMIN);//省级管理员
		Boolean admin_area=(Boolean)request.getSession().getAttribute(RoleNo.CABLE_ADMIN_AREA);//市级管理员
		Boolean admin_sonarea=(Boolean)request.getSession().getAttribute(RoleNo.CABLE_ADMIN_SONAREA);//区级管理员
		if(admin){
			if("".equals(area)){
				 area = request.getSession().getAttribute("areaId").toString();		
			}
			 map.put("area", area);//避免省级管理员误操作，将任务转移到其他地市
		}else if(admin_area){
			if("".equals(area)){
				 area = request.getSession().getAttribute("areaId").toString();		
			}
			if("".equals(son_area)){
				 son_area = request.getSession().getAttribute("sonAreaId").toString(); 
			}
			 map.put("area", area);
		}else if(admin_sonarea){
			if("".equals(area)){
				 area = request.getSession().getAttribute("areaId").toString();		
			}
			if("".equals(son_area)){
				 son_area = request.getSession().getAttribute("sonAreaId").toString(); 
			}
			 map.put("area", area);
			 map.put("son_area", son_area);
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map<String, Object>> staffList=null;
		if("1".equals(role_id)){
			staffList = cableMyTaskDao.queryDouDi(query);//兜底岗账号
		}
		if("2".equals(role_id)){
			staffList = cableMyTaskDao.queryWhy(query);//维护员
		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", staffList);
        return pmap;
	}
	
	@Transactional
	@Override
	public String updateTask(HttpServletRequest request) {
		//rollbackFor ={Exception.class}
		boolean flag=true;
		try {
			String role_id = request.getParameter("role_id");//1.兜底岗 2.维护员
			String ids = request.getParameter("ids");//任务ID
			String receiver = request.getParameter("receiver");//维护员或兜底岗
			String complete_time = request.getParameter("complete_time");//整改时间
			String reform_demand = request.getParameter("reform_demand");//整改要求
			String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
			//如果角色为兜底岗或维护员，则将任务的维护员转派给兜底岗或维护员，审核员不变
			Map params=new HashMap();
			Map flowParams=new HashMap();
			params.put("TASK_ID", ids);
			params.put("COMPLETE_TIME", complete_time);
			params.put("MAINTOR", receiver);
			params.put("REFORM_DEMAND", reform_demand);
			cableMyTaskDao.updateTaskNew(params);
			String content="";
			if("1".equals(role_id)){
				content="审核员将工单转发至兜底岗";
			}else{
				content="审核员将工单转发至维护员";
			}
//			int i=1/0;
			for (String id : ids.split(",")) {
				flowParams.put("oper_staff", staffId);
				flowParams.put("task_id", id);
				flowParams.put("status",  6);
				flowParams.put("remark", "转发工单");
				flowParams.put("receiver", receiver);
				flowParams.put("content", content);
				checkProcessDao.addProcessNew(flowParams);		
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag=false;
			e.printStackTrace();
		}finally{
			if(flag){
				return Result.returnSuccess("").toString();
			}else{
				return Result.returnError("转发失败").toString();
			}
		}		
	}
}
