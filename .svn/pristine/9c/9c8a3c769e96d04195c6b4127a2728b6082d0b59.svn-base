package com.inspecthelper.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.page.Query;
import util.page.UIPage;

import com.inspecthelper.dao.InsOrderTaskDao;
import com.inspecthelper.service.InsOrderTaskService;
import com.system.dao.StaffDao;
import com.system.service.BaseMethodService;
import com.system.service.GeneralService;
import com.system.tool.ImageTool;
import com.util.StringUtil;

@SuppressWarnings("all")
@Service
public class InsOrderTaskServiceImpl implements InsOrderTaskService {
	
	@Resource
	private InsOrderTaskDao insOrderTaskDao;
	
	@Resource
	private BaseMethodService baseMethodService;
	
	@Resource
	private GeneralService generalService;
	
	@Resource
	private StaffDao staffDao;

	@Override
	public Map query(HttpServletRequest request, UIPage pager) {
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(getOrderParamMap(request));
		List<Map> orderTaskList = insOrderTaskDao.getOrderTaskList(query);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", orderTaskList);
		map.put("total", query.getPager().getRowcount());
		return map;
	}
	
	@Override
	public List<Map> getOrderTaskList(HttpServletRequest request){
		Query query = new Query();
		query.setPager(new UIPage());
		query.setQueryParams(getOrderParamMap(request));
		List<Map> orderTaskList = insOrderTaskDao.getOrderTaskList(query);
		return orderTaskList;
	}
	
	private Map<String,Object> getOrderParamMap(HttpServletRequest request){
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderNo", request.getParameter("orderNo"));
		paramMap.put("resTypeId", request.getParameter("resTypeId"));
		paramMap.put("targetId", request.getParameter("targetId"));
		paramMap.put("order_equ", request.getParameter("order_equ"));
		paramMap.put("stateId", request.getParameter("stateId"));
		paramMap.put("overTime", request.getParameter("overTime"));
		paramMap.put("ownCompany", request.getParameter("ownCompany"));
		paramMap.put("areaId", request.getParameter("areaId"));
		paramMap.put("overDay", request.getParameter("overDay"));
		paramMap.put("checkStaffNo", request.getParameter("checkStaffNo"));
		paramMap.put("overDayState", request.getParameter("overDayState"));
		paramMap.put("sn", request.getParameter("sn"));
		paramMap.put("repairer", request.getParameter("repairer"));
		paramMap.put("remarks", request.getParameter("remarks"));
		paramMap.put("area", request.getParameter("area"));
		
		return paramMap;
	}
	
	@Override
	public int getUserRoleCount(Map<String,Object> params){
		return staffDao.getUserRoleCount(params);
	}
	
	@Override
	public List<Map> getEquTarget(Map map){
		return insOrderTaskDao.getEquTarget(map);
	}
	
	@Override
	public String getPLZPCheckStaff(Map map){
		Map<String,Object> staffMap = insOrderTaskDao.getPLZPCheckStaff(map);
		if(null != staffMap && null != staffMap.get("STAFF_ID")){
			return staffMap.get("STAFF_ID").toString();
		}
		else{
			return null;
		}
	}
	
	@Override
	public List<Map> getActionHistoryList(HttpServletRequest request){
		String troubleId = request.getParameter("troubleId");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderId", troubleId);
		return insOrderTaskDao.getActionHistoryOrderList(paramMap);
	}
	
	@Override
	@Transactional
	public void completeTask(HttpServletRequest request){
		
		String selected = request.getParameter("allot_troubleIds");
		String[] troubleIds = selected.split(",");
		String companyId = request.getParameter("allot_companyId");
		String staffId = request.getParameter("allot_staffId");
		String projNumber = request.getParameter("allot_projNumber");
		String installName = request.getParameter("allot_installName");
		String installCompany = request.getParameter("allot_installCompany");
		String installDate = request.getParameter("allot_installDate");
		String accessNum = request.getParameter("allot_accessNum");
		String remarks = request.getParameter("allot_remarks");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("projNumber", projNumber);
		paramMap.put("installName", installName);
		paramMap.put("installCompany",installCompany);
		paramMap.put("installDate", installDate);
		paramMap.put("accessNum", accessNum);
		paramMap.put("staffId", staffId);
		paramMap.put("remarks",remarks);
		
		for(String troubleId : troubleIds){
			paramMap.put("troubleId", troubleId);
			
			Map<String,Object> troubleMap = new HashMap<String,Object>();
			troubleMap.put("troubleId", troubleId);
			Map troublecodeMap = insOrderTaskDao.findTroublecode(troubleMap);
			String troublecode = troublecodeMap!=null? troublecodeMap.get("TROUBLE_CODE").toString() : "";
			
			saveAllFile(request,troublecode);//上传文件
			
			insOrderTaskDao.modifyOrderPostInfo(paramMap);//更新工单信息
			
			Map actionMap = new HashMap();
			actionMap.put("actionType", "7");
			actionMap.put("descAction", "派单工单：" + remarks + projNumber);
			actionMap.put("troubleId", troubleId);
			actionMap.put("staffId", staffId);
			actionMap.put("goalStaffId", staffId);
			
			insOrderTaskDao.insertActionHistory(actionMap);//插入流程详情
		}

	}
	
	@Override
	public void completeHuidanTask(HttpServletRequest request){
		
		HttpSession sessionPad = request.getSession();
		
		String staffId = (String) sessionPad.getAttribute("staffId");
		String staffNo = (String) sessionPad.getAttribute("staffNo");
		String troubleId = request.getParameter("hui_troubleIds");
		String remarks = request.getParameter("hui_remarks");
		
		Map paramMap = new HashMap();
		paramMap.put("troubleId", troubleId);
		paramMap.put("remarks", remarks);
		
		saveAllFile(request,troubleId);//上传文件
		
		insOrderTaskDao.modifyOrderHuiInfo(paramMap);
		
		Map actionMap = new HashMap();
		actionMap.put("troubleId", troubleId);
		actionMap.put("staffId", staffId);
		actionMap.put("actionType", "1");
		actionMap.put("descAction", "回单操作：" + remarks);
		insOrderTaskDao.insertActionHistory(actionMap);
	}
	
	@Override
	@Transactional
	public void completeCheckTask(HttpServletRequest request){
		String selected = request.getParameter("check_troubleIds");
		String companyId = request.getParameter("check_companyId");
		String projNumber = request.getParameter("check_projNumber");
		String installName = request.getParameter("check_installName");
		String installCompany = request.getParameter("check_installCompany");
		String installDate = request.getParameter("check_installDate");
		String accessNum = request.getParameter("check_accessNum");
		String money = request.getParameter("check_money");
		String remarks = request.getParameter("check_remarks");
		String flag = request.getParameter("check_flag"); 
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("troubleId",selected);
		paramMap.put("companyId", companyId);
		paramMap.put("money", money);
		paramMap.put("projNumber", projNumber);
		paramMap.put("installCompany", installCompany);
		paramMap.put("installName", installName);
		paramMap.put("installDate", installDate);
		paramMap.put("accessNum", accessNum);
		
		String staffId = request.getSession().getAttribute("staffId").toString();//获取登录用户ID
		String[] troubleIds = selected.split(",");
		if("0".equals(flag)){
			insOrderTaskDao.modifyOrderCheckInfoOk(paramMap);//修改工单记录为归档
			
			Map actionMap = new HashMap();
			for(String troubleId : troubleIds){
				actionMap.put("troubleId", troubleId);
				actionMap.put("staffId", staffId);
				actionMap.put("actionType", "2");
				actionMap.put("descAction", "审核通过,公司：" + companyId + ",审核金额：" + money + "," + remarks);
				insOrderTaskDao.insertActionHistory(actionMap);//插入流程详情
			}
		}
		else{
			
			insOrderTaskDao.modifyOrderCheckInfoNo(paramMap);//修改工单记录为归档
			
			Map actionMap = new HashMap();
			for(String troubleId : troubleIds){
				actionMap.put("troubleId", troubleId);
				actionMap.put("staffId", staffId);
				actionMap.put("actionType", "2");
				actionMap.put("descAction", "审核不通过:" + remarks);
				insOrderTaskDao.insertActionHistory(actionMap);//插入流程详情
			}
		}
	}
	
	@Override
	@Transactional
	public void completeArchiveTask(HttpServletRequest request){
		HttpSession sessionPad = request.getSession();
		String staffId = (String) sessionPad.getAttribute("staffId");
		
		String selected = request.getParameter("allot_troubleIds");
		String[] troubleIds = selected.split(",");
		String projNumber = request.getParameter("archive_projNumber");
		String installName = request.getParameter("archive_installName");
		String installCompany = request.getParameter("archive_installCompany");
		String installDate = request.getParameter("archive_installDate");
		String accessNum = request.getParameter("archive_accessNum");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("projNumber", projNumber);
		paramMap.put("installCompany", installCompany);
		paramMap.put("installName", installName);
		paramMap.put("installDate", installDate);
		paramMap.put("accessNum", accessNum);
		
		for(String troubleId : troubleIds){
			paramMap.put("troubleId", troubleId);
			insOrderTaskDao.modifyOrderEndInfo(paramMap);//修改工单记录为归档
			
			Map actionMap = new HashMap();
			actionMap.put("troubleId", troubleId);
			actionMap.put("staffId", staffId);
			actionMap.put("actionType", "3");
			actionMap.put("descAction", "归档：");
			insOrderTaskDao.insertActionHistory(actionMap);//插入流程详情
		}
	}
	
	@Override
	@Transactional
	public void completeChargebackTask(HttpServletRequest request){
		HttpSession sessionPad = request.getSession();
		String staffId = (String) sessionPad.getAttribute("staffId");
		
		String troubleId = request.getParameter("chargeback_troubleIds");
		String remarks = request.getParameter("chargeback_remarks");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("troubleId",troubleId);
		paramMap.put("remarks", remarks);
		
		insOrderTaskDao.modifyOrderTuidanInfo(paramMap);//修改工单记录为退单
		
		
		Map actionMap = new HashMap();
		actionMap.put("troubleId", troubleId);
		actionMap.put("staffId", staffId);
		actionMap.put("actionType", "4");
		actionMap.put("descAction", "退单,原因是：" + remarks);
		insOrderTaskDao.insertActionHistory(actionMap);
		
	}
	
	@Override
	@Transactional
	public void completeReassignmentTask(HttpServletRequest request){
		String selected = request.getParameter("reassignment_troubleIds");
		String[] troubleIds = selected.split(",");
		String companyId = request.getParameter("reassignment_companyId");
		String staffId = request.getParameter("reassignment_staffId");
		String projNumber = request.getParameter("reassignment_projNumber");
		String installName = request.getParameter("reassignment_installName");
		String installCompany = request.getParameter("reassignment_installCompany");
		String installDate = request.getParameter("reassignment_installDate");
		String accessNum = request.getParameter("reassignment_accessNum");
		String remarks = request.getParameter("reassignment_remarks");
		String ftth =  request.getParameter("reassignment_ftth");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("projNumber", projNumber);
		paramMap.put("installName", installName);
		paramMap.put("installCompany",installCompany);
		paramMap.put("installDate", installDate);
		paramMap.put("accessNum", accessNum);
		paramMap.put("staffId", staffId);
		paramMap.put("remarks",remarks);
		paramMap.put("ftth", ftth);
		
		for(String troubleId : troubleIds){
			paramMap.put("troubleId", troubleId);
			Map troublecodeMap = insOrderTaskDao.findTroublecode(paramMap);
			String troublecode = troublecodeMap!=null? troublecodeMap.get("TROUBLE_CODE").toString() : "";

			saveAllFile(request,troublecode);//上传文件
			
			insOrderTaskDao.modifyOrderPostInfo_(paramMap);//更新工单信息
			
			Map actionMap = new HashMap();
			actionMap.put("actionType", "0");
			actionMap.put("descAction", "转发工单：" + remarks + projNumber);
			actionMap.put("troubleId", troubleId);
			actionMap.put("staffId", request.getSession().getAttribute("staffId"));
			actionMap.put("goalStaffId", staffId);
			
			insOrderTaskDao.insertActionHistory(actionMap);//插入流程详情
		}

	}
	
	@Override
	public void completeBtnReassign(HttpServletRequest request){
		HttpSession sessionPad = request.getSession();
		String staffId = (String) sessionPad.getAttribute("staffId");
		
		String selected = request.getParameter("selected");
		String[] troubleIds = selected.split(",");
		
		for(String troubleId : troubleIds){
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("troubleId", troubleId);
			Map checkMap = insOrderTaskDao.getPLZPCheckStaff(paramMap);
			paramMap.put("checkStaff", checkMap.get("STAFF_ID"));
			insOrderTaskDao.pLZPai(paramMap);
			
			Map<String,Object> actionMap = new HashMap<String,Object>();
			actionMap.put("troubleId", troubleId);
			actionMap.put("staffId", staffId);
			actionMap.put("actionType", "6");
			actionMap.put("goalStaffId", checkMap.get("STAFF_ID"));
			actionMap.put("descAction", "超时工单重新转派");
			insOrderTaskDao.insertActionHistory(actionMap);//插入流程详情
		}
	}
	
	@Override
	public List<Map> getAllStaffByTroubleOrderList(Map map){
		return insOrderTaskDao.getAllStaffByTroubleOrderList(map);
	}
	
	@Override
	public List<Map> getLastTrouPhotoPath(Map map){
		return insOrderTaskDao.getLastTrouPhotoPath(map);
	}
	
	
	@Override
	public List<Map> findAllPhoto(Map map){
		return insOrderTaskDao.findAllPhoto(map);
	}
	
	@Override
	public List<Map> findAllFile(Map map){
		return insOrderTaskDao.findAllFile(map);
	}
	
	@Override
	public String findTroublecode(Map map){
		Map troublecodeMap = insOrderTaskDao.findTroublecode(map);
		String troublecode = troublecodeMap!=null? troublecodeMap.get("TROUBLE_CODE").toString() : "";
		if(null != troublecodeMap){
			return null;
		}
		return troublecodeMap.get("TROUBLE_CODE").toString();
	}
	
	@Transactional
	public void saveAllFile(HttpServletRequest request,String troublecode){
		try{
		savePhoto(request,"photo1",troublecode);//上传图片1
		savePhoto(request,"photo2",troublecode);//上传图片2
		saveFile(request,"excel",troublecode,"xls");//上传Excel
		saveFile(request,"word",troublecode,"doc");//上传Word
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveFile(HttpServletRequest request,String fileName,String troublecode,String type){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		byte[] eByte = getFileByte(multipartRequest,fileName);//获取文件字节流
		
		if(null != eByte && eByte.length > 0){
			String fileId = String.valueOf(insOrderTaskDao.getFileId());
			HashMap pathMap = baseMethodService.uploadFile(fileId, eByte,type);
			
			if(null != pathMap){
				Map<String,Object> resourceMap = new HashMap<String,Object>();
				resourceMap.put("resourceCode", troublecode);
				resourceMap.put("fileId", fileId);
				resourceMap.put("path", pathMap.get("path"));
				resourceMap.put("staffNo", request.getSession().getAttribute("staffNo"));
				insOrderTaskDao.saveResourceFile(resourceMap);
			}
		}
	}
	
	public void savePhoto(HttpServletRequest request,String fileName,
			String troublecode) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		byte[] eByte = getFileByte(multipartRequest,fileName);//获取文件字节流
		
		if(null != eByte && eByte.length > 0){
			CommonsMultipartFile commonCfile = (CommonsMultipartFile)multipartRequest.getFile(fileName);
			File file = convertMultipartFileToFile(commonCfile,fileName);//将spring MVC的文件对象转换成File
		
			Map map = new HashMap();
			map.put("staffNo", request.getSession().getAttribute("staffNo"));
			map.put("troubleId", troublecode);
			List<Map> strMap = insOrderTaskDao.getNewStr(map);//获取水印文字
			String str = "";
			if(null != strMap && strMap.size() > 0){
				str = strMap.get(0).get("STR").toString();
			}
			
			Integer c = insOrderTaskDao.getZGCount(map)+1;//获取整改次数	
			
			ImageTool.createMark(file, str+"/"+c+"次整改", Color.RED, 10);//给照片加水印
			
			byte[] photoByte = ImageTool.compressPic(file);//压缩图片
			
			String photoId = generalService.getNextSeqVal("PHOTO_ID"); // 获得照片主键
			
			HashMap pathMap = baseMethodService.uploadFile(photoId,photoByte,"photo");//调用上传图片方法
			
			file.delete();//删除临时文件
			
			if (pathMap == null) {
				return; // 图片上传失败
			}
			
			Map param = new HashMap();
			String resourceCode = troublecode;
			param.put("resourceCode", resourceCode);
			param.put("photoId", photoId);
			param.put("photoPath", pathMap.get("photoPath"));
			param.put("microPhotoPath", pathMap.get("microPhotoPath"));
			param.put("staffNo", request.getSession().getAttribute("staffNo"));
			insOrderTaskDao.saveResourcePhoto(param);//向resource_photo表中插入一条数据
		}
	}

	public File convertMultipartFileToFile(CommonsMultipartFile commonCfile,String filePath){
		File file = new File(filePath);
		try {
			commonCfile.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public byte[] getFileByte(MultipartHttpServletRequest multipartRequest,String fileName){
		MultipartFile cfile = multipartRequest.getFile(fileName);
		if(null != cfile){
			CommonsMultipartFile commonCfile = (CommonsMultipartFile)cfile;
			return commonCfile.getBytes();
		}
		else{
			return null;
		}
	}
}
