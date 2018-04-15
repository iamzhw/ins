package com.inspecthelper.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.inspecthelper.dao.DblinkDao;
import com.inspecthelper.dao.EquipDao;
import com.inspecthelper.dao.InsOrderTaskDao;
import com.inspecthelper.dao.InspectHelperDao;
import com.inspecthelper.model.Dblink;
import com.inspecthelper.service.IInspectService;
import com.system.service.BaseMethodService;
import com.system.service.GeneralService;
import com.system.tool.ImageTool;
import com.util.DblinkUtil;
import com.util.MyExcelTool;

@Service
@Transactional(rollbackFor = { Exception.class }) 
@SuppressWarnings("all")
public class InspectServiceImpl implements IInspectService {
	
	@Resource
	private InspectHelperDao inspectHelperDao;
	
	@Resource
	private InsOrderTaskDao insOrderTaskDao;
	
	@Resource
	private BaseMethodService baseMethodService;
	
	@Resource
	private GeneralService generalService;
	@Resource
	private EquipDao equipDao;

	@Resource DblinkDao dblinkDao;
	
	@Override
	public String getOrderCount(String staffNo) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("staffNo", staffNo);
		int count = inspectHelperDao.getOrderCount(map);
		int count1=inspectHelperDao.getTskOrderListCount(map);
		map.put("order_count", count);
		map.put("order_list_count", count1);
		String jsonString = JSONArray.fromObject(map).toString();
		return jsonString;
	}

	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskCode", request.getParameter("taskName"));
		map.put("equCode", request.getParameter("equCode"));
		map.put("staffNo", request.getParameter("staffNo"));
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map<String, Object>> olists = inspectHelperDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	public List<Map<String, Object>> getOrderDetail(Map<String, Object> param ) {
		String equipmentId = null;
		List<Map<String, Object>> reSignList = new ArrayList<Map<String, Object>> ();
		try {
			/* 判断是否插入签到点 */
			int flag = inspectHelperDao.getTaskEquCount(param);
			/* 如果没有签到属性，则插入 */
			if (flag == 0) {
				List<Map<String, Object>> taskEquList = inspectHelperDao.getTaskEquList(param);
				for (int j = 0; j < taskEquList.size(); j++) {
					equipmentId = String.valueOf(taskEquList.get(j).get("EQUIPMENT_ID"));
					param.put("equipmentId", equipmentId);
					inspectHelperDao.createTaskOrderSign(param);
					inspectHelperDao.createTaskOrderCheck1(param);
				}
			}
			List<Map<String, Object>> signList = inspectHelperDao.getTaskOrderSign(param);
			for (int i = 0; i < signList.size(); i++) {
				Map<String, Object> sign =signList.get(i);
				Map<String, Object> signO = new HashMap<String, Object>();
				signO.put("equipmentNo", sign.get("EQUIPMENT_CODE"));
				// signO.put("signState", sign.get("SIGN_STATE"));
				signO.put("address", sign.get("ADDRESS") == null ? "暂无地址!"
						: sign.get("ADDRESS"));
				signO.put(
						"equipmentName",
						sign.get("EQUIPMENT_NAME") == null ? "" : sign
								.get("EQUIPMENT_NAME"));
				signO.put("checkState", sign.get("CHECK_STATE"));
				signO.put("equipmentId", sign.get("EQUIPMENT_ID"));
				signO.put("state", sign.get("STATE"));
				signO.put("resTypeId", sign.get("RES_TYPE_ID"));
				reSignList.add(signO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reSignList;
	}
	
	/**
	 * 获得POI中的Workbook对象
	 */

	public HSSFWorkbook getWorkbook(List<Map<String,Object>> list, String[] columnNames,
			String[] columnMethods) {
		HSSFWorkbook workbook = null;

		try {
			// String[] columnNames = new String[] { "序号", "SN号码", "操作功能",
			// "是否成功", "备注","操作时间",
			// "资源编码", "资源名称"};
			//
			// String[] columnMethods = new String[] { "", "SN",
			// "HANDLE_NAME","STATE", "CONTENT","HANDLE_DATE",
			// "RES_NO", "RES_NAME"};

			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("sheet1");

			/* 字体样式 - 标题样式 加粗居中 */
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight((short) 2000);
			titleStyle.setFont(font);

			/* 生成标题 */
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell;
			for (int i = 0; i < columnNames.length; i++) {
				cell = row.createCell(i); // 创建第i列
				cell.setCellStyle(titleStyle);
				cell.setCellValue(new HSSFRichTextString(columnNames[i]));
			}

			/* 生成Excel中的数据 */
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> record = list.get(i);
				row = sheet.createRow(i + 1);

				// /* 生成序号 */
				// cell = row.createCell(0);
				// cell.setCellValue(i + 1);

				/* 生成其他字段数据 */
				for (int j = 0; j < columnMethods.length; j++) {
					// Method method =
					// record.getClass().getMethod(columnMethods[j]);
					Object obj = record.get(columnMethods[j]);
					cell = row.createCell(j);
					cell.setCellValue(new HSSFRichTextString(obj == null ? ""
							: obj.toString()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return workbook;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> resTrouTable(HttpServletRequest request,
			UIPage pager) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("equCode", request.getParameter("equCode"));
		map.put("parentAreaId", request.getParameter("areaId"));
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> olists = equipDao.getEquipList(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Map<String, Object> getEquTarget(HttpServletRequest request) {
		Map<String, Object> querymap=new HashMap<String,Object>();
		querymap.put("equipmentId", request.getParameter("equipmentId"));
		querymap.put("resTypeId", request.getParameter("resTypeId"));
		querymap.put("orderId", request.getParameter("orderId"));
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("rows", inspectHelperDao.getEquTarget(querymap));
		return pmap;
	}

	@Override
	public String uploadPhoto(HttpServletRequest request) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			HttpSession session = request.getSession();
			String orderId = request.getParameter("orderId");
			String equipmentId = request.getParameter("equipmentId");
			String targetId = request.getParameter("targetId");
			String p1 = request.getParameter("p1");
			String p2 = request.getParameter("p2");
			String p3 = request.getParameter("p3");
			String p4 = request.getParameter("p4");
			String staffNo = String.valueOf(session.getAttribute("staffNo"));
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("equipmentId", equipmentId);
			map.put("targetId", targetId);
			map.put("staffNo", staffNo);
			map.put("orderId", orderId);
			boolean b=true;
			if (p1.length() > 0) {
				
				MultipartFile imgFile1  =  multipartRequest.getFile("photo1"); 
				File file = new File(imgFile1.getOriginalFilename());
				imgFile1.transferTo(file);
				b = savePhoto(file, map);
			}
			if (p2.length() > 0) {
				
				MultipartFile imgFile1  =  multipartRequest.getFile("photo2"); 
				File file = new File(imgFile1.getOriginalFilename());
				imgFile1.transferTo(file);
				b = savePhoto(file, map);
			}
			if (p3.length() > 0) {
				MultipartFile imgFile1  =  multipartRequest.getFile("photo3"); 
				File file = new File(imgFile1.getOriginalFilename());
				imgFile1.transferTo(file);
				b = savePhoto(file, map);
			}
			if (p4.length() > 0) {
				MultipartFile imgFile1  =  multipartRequest.getFile("photo4"); 
				File file = new File(imgFile1.getOriginalFilename());
				imgFile1.transferTo(file);
				b = savePhoto(file, map);
			}
			if (b) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	@SuppressWarnings("all")
	public boolean savePhoto(File f,Map<String,Object> map) {
		try {
			String str = inspectHelperDao.getStr(map);
			ImageTool.createMark(f, str, Color.RED, 10);
			byte[] photoByte = ImageTool.compressPic(f);
			if (photoByte.length > 0) {
				String photoId = generalService.getNextSeqVal("PHOTO_ID"); // 获得照片主键
				HashMap<String,Object> pathMap = baseMethodService.uploadFile(photoId,photoByte,"photo");//调用上传图片方法
				f.delete();//删除临时文件
				if (pathMap == null) {
					return false; // 图片上传失败
				}
				/* 向resource_photo表中插入一条数据 */
				Map<String,Object> param = new HashMap<String,Object>();
				String resourceCode = String.valueOf(map.get("orderId")) + "," + String.valueOf(map.get("equipmentId")) + ","
						+ String.valueOf(map.get("targetId"));
				param.put("resourceCode", resourceCode);
				param.put("photoId", photoId);
				param.put("photoPath", pathMap.get("photoPath"));
				param.put("microPhotoPath", pathMap.get("microPhotoPath"));
				param.put("staffNo", String.valueOf(map.get("staffNo")));
				insOrderTaskDao.saveResourcePhoto(param);//向resource_photo表中插入一条数据
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getLastTrouPhotoPath(HttpServletRequest request) {
		String orderId = request.getParameter("orderId");
		String equipmentId = request.getParameter("equipmentId");
		String targetId = request.getParameter("targetId");
		String troubleCode = orderId + "," + equipmentId + "," + targetId;
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("resourceCode", troubleCode);
		param.put("ip", "");
		List<Map<String,Object>> list=inspectHelperDao.getLastTrouPhotoPath(param);
		return String.valueOf(list.size());
	}

	@Override
	public List<Map<String,Object>> getTargetQues(String str) {
		return inspectHelperDao.getTargetQues(str);
	}

	@Override
	public String saveResTrou(HttpServletRequest request) {
		boolean b = true;
		try {
			HttpSession session = request.getSession();
			request.setCharacterEncoding("UTF-8");
			String staffNo = (String) session.getAttribute("staffId");
			String orderId = request.getParameter("orderId");
			String equipmentId = request.getParameter("equipmentId");
			String targetId = request.getParameter("targetId");
			String troubleCode = orderId + "," + equipmentId + "," + targetId;
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("troubleCode", troubleCode);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			int state=Integer.parseInt(request.getParameter("state"));
			String remarks = request.getParameter("remarks");
			remarks = URLDecoder.decode(remarks, "UTF-8");
			String type = request.getParameter("type");
			MultipartFile wordFile  =  multipartRequest.getFile("word"); 
			MultipartFile excelFile =  multipartRequest.getFile("excel"); 
			if (targetId.equals("0001")||targetId.equals("0015") || targetId.equals("0017")|| targetId.equals("0018")
					|| targetId.equals("0034") || targetId.equals("1005")
					|| targetId.equals("1007") || targetId.equals("1016")) {
				String w = wordFile.getOriginalFilename();
				String e = excelFile.getOriginalFilename();
				int[] fileState = new int[] { 0, 0 };
				if (w.length() > 0) {
					fileState[0] = 1;
				}
				if (e.length() > 0) {
					fileState[1] = 1;
				}
				if (state == 1) {
					/* 删除原问题 */
					inspectHelperDao.deleResTrou(troubleCode);
				}
				if(targetId.equals("0001")||targetId.equals("0018"))
				{
					saveResTrou(orderId, equipmentId,
							targetId, type, remarks, staffNo);
				}
				
				File word = null;
				File excel = null;
				if(!wordFile.isEmpty()){
					word = new File(wordFile.getOriginalFilename());
					wordFile.transferTo(word);
				}
				
				if(!excelFile.isEmpty()){
					excel = new File(excelFile.getOriginalFilename());
					excelFile.transferTo(excel);
				}
				
				b = saveExcel(word, excel, targetId, orderId, equipmentId,
								staffNo, remarks, fileState, type);
			} else {
				if (state == 0) {
					b = saveResTrou(orderId, equipmentId,
							targetId, type, remarks, staffNo);
				} else if (state == 1) {
					map.put("remarks", remarks);
					inspectHelperDao.updateResTrou(map);
				}
			}
		} catch (UnsupportedEncodingException e) {
			b = false;
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (b) {
			return "true";
		} else {
			return "false";
			}
	}

	public boolean saveResTrou(String orderId, String equipmentId,
			String targetId, String type, String remarks, String staffNo) {
		String troubleCode = orderId + "," + equipmentId + "," + targetId;
		Map<String,Object> param = new HashMap<String,Object>();
		// List targetL =
		// Arrays.asList("0001","0012","0013","0014","0015","0016","0017","0018","0019","0022","0023","0024","0025","0027","0034","0035");
		List<String> targetL = Arrays.asList("0001", "0012", "0015", "0016", "0017",
				"0018", "0019", "0022", "0023", "0024", "0025", "0034", "0035");
		param.put("equipmentId", equipmentId);
		if (targetL.contains(targetId)) {
			param.put("uploadStaff", staffNo);
		}
		param.put("targetId", targetId);
		param.put("checkStaffNoForNotSpecial", staffNo);
		/* 获取审核人员ID */
		String checkStaff = String.valueOf(getCheckStaff(param).get(0).get("STAFF_ID"));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("seqName", "TROUBLE_ID");
		String troubleId = inspectHelperDao.getNextSeqVal(map);
		param.put("troubleId", troubleId);
		param.put("orderId", orderId);
		param.put("checkStaff", checkStaff);
		param.put("uploadStaff", staffNo);
		// param.put("equipmentId", equipmentId);
		param.put("type", type);
		if (type.equals("0")) {
			param.put("state", "0");
		} else if (type.equals("1")) {
			param.put("state", "3");
		}
		param.put("description", remarks);
		param.put("troubleCode", troubleCode);
		/* 标识是PC上传 */
		param.put("sn", "PC");
		inspectHelperDao.saveResTrouble(param);

		/* add by luyl 2013-1-11 插入流程 */
		Map<String,Object> actionMap = new HashMap<String,Object>();
		actionMap.put("troubleId", troubleId);
		actionMap.put("staffId", staffNo);
		actionMap.put("actionType", "5");
		actionMap.put("goalStaffId", checkStaff);
		actionMap.put("descAction", "上报问题：" + remarks);
		insOrderTaskDao.insertActionHistory(actionMap);
		return true;
	}
	
	public List<Map<String,Object>> getCheckStaff(Map<String,Object> map) {
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		String area = inspectHelperDao.getEquArea(map);
		String areaId = inspectHelperDao.getEquAreaId(map);
		String uploadStaffNo = String.valueOf(map.get("uploadStaff"));
		if ("100491".equals(uploadStaffNo)) {
			l = inspectHelperDao.getCheckStaffZXC(map);
		} else if ("常熟市".equals(area)) {
			l = inspectHelperDao.getCheckStaffCS(map);
		} else if ("太仓市".equals(area)) {
			l =inspectHelperDao.getCheckStaffTC(map);
		} else {
			if (map.get("targetId").equals("0034FTTH")
					|| map.get("targetId").equals("0035FTTH")
					|| map.get("targetId").equals("0015")) {
				l = inspectHelperDao.getCheckStaff2(map);
			} else {
				l = inspectHelperDao.getCheckStaff3(map);// 规则 设备公司
			}
			if (l.size() == 0) {
				l = inspectHelperDao.getCheckStaff(map);// 规则 巡检人公司
				if (l.size() == 0) {
					l = inspectHelperDao.getCheckStaff12(map);// 规则
																			// 巡检人
					if (l.size() == 0 && !"20".equals(areaId)) {
						l = inspectHelperDao.getCheckStaff1(map);// 非规则
																				// 巡检人区域
					} else if (l.size() == 0) {
						l = inspectHelperDao.getCheckStaff11(map);// 非规则
																				// 资源配置中心
					}
				}
			}
		}
		return l;
	}
	
	/**
	 * 解析Excel
	 */

	@SuppressWarnings("unchecked")
	public boolean saveExcel(File word, File excel, String targetId,
			String orderId, String equipmentId, String staffNo, String remarks,
			int[] state, String type) {
		try {
			if (state[1] == 1) {
				/* 解析Excel */
				MyExcelTool parser = new MyExcelTool(excel);
				List<List> datas = parser.getDataInSheet(0);
				for (int j = 0; j < datas.size(); j++) {
					List row = datas.get(j);

					if (j == 1) {
						if (row.size() < 6) {
							return false;
						}
						if (!row.get(0).equals("端子")
								|| !row.get(1).equals("光路编码")
								|| !row.get(2).equals("光路业务状态")
								|| !row.get(3).equals("现场情况")
								|| !row.get(4).equals("整改情况")) {
							return false;
						}
					}
					if (j > 1 && row.size() >= 6) {
						String o = (String) (row.get(4) == null ? "" : row
								.get(4));
						String o1 = (String) (row.get(3) == null ? "" : row
								.get(3));
						String ftth = (String) (row.get(5) == null ? "" : row
								.get(5));
						if (row.get(3) == null && row.get(0) != null) {
							return false;
						}
						String opticalPathName = "";
						if (row.get(1) != null && row.get(1).toString() != ""
								&& row.get(1).toString() != null) {
							opticalPathName = getOpticalPathNameByCode(row
									.get(1).toString(), staffNo);
						}
						String description = "端子" + row.get(0) + ",系统"
								+ row.get(1) + ",光路名称：" + opticalPathName
								+ ",现场" + row.get(3) + remarks;
						if (targetId.equals("0034")) {
							if (o.equals("已整改")) {
								type = "1";
								saveResTrou(orderId, equipmentId, targetId,
										type, description, staffNo, ftth, "");
							} else if (!o1.equals("") && !o.equals("已整改")) {
								type = "0";
								saveResTrou(orderId, equipmentId, targetId,
										type, description, staffNo, ftth, "");
							}
						} else {
							if (o.equals("已整改")) {
								type = "1";
								saveResTrou(orderId, equipmentId, targetId,
										type, description, staffNo);
							} else if (!o1.equals("") && !o.equals("已整改")) {
								type = "0";
								saveResTrou(orderId, equipmentId, targetId,
										type, description, staffNo);
							}
						}
					}
				}
				// uploadFile(excel, orderId, equipmentId, targetId, staffNo);
			}
			if (state[0] == 1) {
				/* 上传Word */
				uploadFile(word, orderId, equipmentId, targetId, staffNo);
				saveResTrou(orderId, equipmentId, targetId, type, "见附件",
						staffNo);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public String getOpticalPathNameByCode(String code, String staffNo) {
		Dblink dblink = DblinkUtil.getDbLinkByStaffNo(dblinkDao, staffNo);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("DBLINKUSERNAME", dblink.getDblinkUsername());
		param.put("code", code);
		String name = "";
		try {
			SwitchDataSourceUtil.setCurrentDataSource(dblink.getJndi());
			name = inspectHelperDao.getOpticalPathNameByCode(param);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		}
		return name;
	}
	
	public boolean saveResTrou(String orderId, String equipmentId,
			String targetId, String type, String remarks, String staffNo,
			String ftth, String id) {
		String troubleCode = orderId + "," + equipmentId + "," + targetId;
		Map<String,Object> param = new HashMap<String,Object>();
		List<String> targetL = Arrays.asList("0001", "0012", "0013", "0014", "0015",
				"0016", "0017", "0018", "0019", "0022", "0023", "0024", "0025",
				"0027", "0034", "0035");
		if (targetL.contains(targetId) && ftth.length() != 4
				&& ftth.length() != 3) {
			// param.put("equipmentId", equipmentId);
			param.put("uploadStaff", staffNo);
		}
		if (ftth.equals("FTTH") || ftth.equals("BBU")) {
			param.put("targetId", targetId + ftth);
		} else {
			param.put("targetId", targetId);
		}
		param.put("equipmentId", equipmentId);
		/* 获取审核人员ID */

		String checkStaff = String.valueOf(getCheckStaff(param).get(0).get("STAFF_ID"));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("seqName", "TROUBLE_ID");
		String troubleId =inspectHelperDao.getNextSeqVal(map);
		//param.put("targetId", targetId);
		param.put("troubleId", troubleId);
		param.put("orderId", orderId);
		param.put("checkStaff", checkStaff);
		param.put("uploadStaff", staffNo);
		param.put("equipmentId", equipmentId);
		param.put("type", type);
		param.put("id", id);
		param.put("ftth", ftth);
		if (type.equals("0")) {
			param.put("state", "0");
		} else if (type.equals("1")) {
			param.put("state", "3");
		}
		if (ftth.length() == 0) {
			param.put("description", remarks);
		} else {
			param.put("description", remarks + "(" + ftth + ")");
		}
		param.put("troubleCode", troubleCode);
		/* 标识是PC上传 */
		param.put("sn", "PC");
		inspectHelperDao.saveResTrouble(param);

		/* add by luyl 2013-1-11 插入流程 */
		Map<String,Object> actionMap = new HashMap<String,Object>();
		actionMap.put("troubleId", troubleId);
		actionMap.put("staffId", staffNo);
		actionMap.put("actionType", "5");
		actionMap.put("goalStaffId", checkStaff);
		actionMap.put("descAction", "上报问题：" + remarks);
		insOrderTaskDao.insertActionHistory(actionMap);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean uploadFile(File file, String orderId, String equipmentId,
			String targetId, String staffNo) {
		if (file.length() > 0) {
			byte[] eByte = ImageTool.getPhotoByteFromFile(file);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("seqName", "FILE_ID");
			String fileId = inspectHelperDao.getNextSeqVal(map);
			HashMap<String,Object> pathMap = baseMethodService.uploadFile(fileId,eByte,"doc");//调用上传图片方法
			if (pathMap == null) {
				return false; // 图片上传失败
			}
			
			/* 向resource_file表中插入一条数据 */
			Map<String,Object> param = new HashMap<String,Object>();
			String resourceCode = orderId + "," + equipmentId + "," + targetId;
			/* 向resource_photo表中插入一条数据 */
			
			param.put("resourceCode", resourceCode);
			param.put("fileId", fileId);
			param.put("path", pathMap.get("path"));
			param.put("staffNo",staffNo);
			insOrderTaskDao.saveResourceFile(param);//向resource_photo表中插入一条数据
			return true;
		}
		return true;
	}
	
	
	/* 判断工单是否竣工，将此设备置为已填报状态，将已签到设备数量+1....... */

	public void checkRes(HttpServletRequest request) {
		Map<String,Object> param1 = new HashMap<String,Object>();
		param1.put("orderId", request.getParameter("orderId"));
		param1.put("equipmentId", request.getParameter("equipmentId"));
		param1.put("staffNo", request.getParameter("staffNo"));
		/* 标识是PC上传 */
		param1.put("sn", "PC");
		int i=inspectHelperDao.checkResCount(param1);
		if (!(i>0)) {
			inspectHelperDao.createTaskOrderCheck(param1);
		}
		 inspectHelperDao.checkRes(param1);
		 inspectHelperDao.updateTaskOrder(param1);
		 inspectHelperDao.updateTaskOrder1(param1);
		 inspectHelperDao.updateTaskOrder2(param1);
	}

	@Override
	public Map<String, Object> getLastTrou(HttpServletRequest request) {
		String targetId = request.getParameter("targetId");
		String equipmentId = request.getParameter("equipmentId");
		String ip = baseMethodService.getValidFileServiceAccessIp(request);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("targetId", targetId);
		param.put("equipmentId", equipmentId);
		param.put("orderId", "");
		List<Map<String,Object>> list1 = inspectHelperDao.getResTrouble(param);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (list1.size() > 0) {
			list.add(list1.get(0));
			String troubleCode = (String) list.get(0).get("TROUBLE_CODE");
			param.put("resourceCode", troubleCode);
			param.put("ip", ip);
			List<Map<String,Object>> photoL = inspectHelperDao.getLastTrouPhotoPath(param);
			param.put("photoL", photoL);
		}
		param.put("rows", list);
		return param;
	}

	
	@Override
	public List<Map<String, Object>> cDuanTable(HttpServletRequest request) {
		Map<String,Object> param = new HashMap<String,Object>();
		String equipmentId=request.getParameter("equipmentId");
		param.put("equipmentId", equipmentId);
		String date=request.getParameter("date");
		param.put("date", date);
		if (date.equals("lastCheck")) {
			String lastCheckDate = inspectHelperDao.getLastCheckDate(param);
			param.put("lastCheckDate", lastCheckDate);
			param.remove("date");
		}
		List<Map<String,Object>> list = inspectHelperDao.getGJCDuanInfo(param);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> dinamicChangeTable(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String,Object> param = new HashMap<String,Object>();
		String equipmentId=request.getParameter("equipmentId");
		param.put("equipmentId", equipmentId);
		String date=request.getParameter("date");
		param.put("date", date);
		String staffNo = (String) session.getAttribute("staffNo");
		String role = inspectHelperDao.getODFStaffRole(staffNo);
		if(null!=role){
			if (role.equals("14")) {
				param.put("state_", "state_a");
			} else if (role.equals("15")) {
				param.put("state_", "state_b");
			} else if (role.equals("16")) {
				param.put("state_", "state_c");
			} else if (role.equals("17")) {
				param.put("state_", "state_d");
			}
		}
		if (date.equals("lastCheck")) {
			String lastCheckDate = inspectHelperDao.getLastCheckDate(param);
			param.put("lastCheckDate", lastCheckDate);
			param.remove("date");
		}
		List<Map<String,Object>> list = inspectHelperDao.getODFDinamicRes(param);
		return list;
	}

	@SuppressWarnings("finally")
	@Override
	public String saveResTrou_(HttpServletRequest request) {
		boolean b = true;
		try {
			HttpSession session = request.getSession();
			request.setCharacterEncoding("UTF-8");
			String staffNo = (String) session.getAttribute("staffNo");
			String staffId = (String) session.getAttribute("staffId");
			String orderId = request.getParameter("orderId");
			String equipmentId = request.getParameter("equipmentId");
			String targetId = request.getParameter("targetId");
			String ftth = request.getParameter("ftth");
			String trueIds = request.getParameter("id");
			if(null!=ftth){
			ftth = URLDecoder.decode(ftth, "UTF-8");
			}
			String remarks = request.getParameter("remarks");
			if(null!=remarks){
			remarks = URLDecoder.decode(remarks, "UTF-8");
			}
			String type = request.getParameter("type");
			if (ftth != null) {
				//b = saveResTrou(orderId, equipmentId,targetId, type, remarks, staffNo, ftth, id);
				b = saveResTrou(orderId, equipmentId,targetId, type, remarks, staffNo, ftth, trueIds);
			} else {
				b = saveResTrou(orderId, equipmentId,
						targetId, type, remarks, staffId);
			}

			/* 将此条动态纤芯记录置为错误 */
			Map<String,Object> param = new HashMap<String,Object>();
			String role = inspectHelperDao.getODFStaffRole(staffNo);
			if (role.equals("14")) {
				param.put("state_a", "2");
			} else if (role.equals("15")) {
				param.put("state_b", "2");
			} else if (role.equals("16")) {
				param.put("state_c", "2");
			} else if (role.equals("17")) {
				param.put("state_d", "2");
			}
			param.put("trueIds", trueIds);
			param.put("staffId", staffId);
			inspectHelperDao.updateDinamicChange(param);
			
			//odf or not odf check
			Map<String,Object> param1 = new HashMap<String,Object>();
			param1.put("orderId", orderId);
			param1.put("equipmentId", equipmentId);
			param1.put("staffNo", staffNo);
			param1.put("sn", "PC");
			param1.put("ckDate", new Date());
			param1.put("odfCheck", 1);
			if (!(inspectHelperDao.checkResCount(param1)>0)) {
				inspectHelperDao.createTaskOrderCheck(param1);
			}
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}finally{
		if (b) {
			return "true";
		} else {
			return "false";
		}
		}
	}
	
	@Override
	public List<Map<String, Object>> exportODFDinamicExcel(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String staffNo = String.valueOf(session.getAttribute("staffNo"));
		String role = inspectHelperDao.getODFStaffRole(staffNo);
		String equCode = request.getParameter("equCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String ischecked = request.getParameter("paras_ischecked");
		Map<String,Object> param = new HashMap<String,Object>();
		String equipmentId=request.getParameter("equipmentId");
		String date=request.getParameter("date");
		param.put("equipmentId", equipmentId);
		param.put("date", date);
		param.put("equCode", equCode);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("ischecked", ischecked);
		if (role.equals("14")) {
			param.put("state_", "state_a");
			param.put("staffNo", staffNo);
		} else if (role.equals("15")) {
			param.put("state_", "state_b");
			param.put("staffNo1", staffNo);
		} else if (role.equals("16")) {
			param.put("state_", "state_c");
		} else if (role.equals("17")) {
			param.put("state_", "state_d");
		}
		if (date.equals("lastCheck")) {
			String lastCheckDate = inspectHelperDao.getLastCheckDate(param);
			param.put("lastCheckDate", lastCheckDate);
			param.remove("date");
		}
		List<Map<String, Object>> list = inspectHelperDao.getODFDinamicRes(param);
		for(Map<String,Object> item:list)
		{
			String portId=String.valueOf(item.get("PHY_PORT_ID_A"));
			Map<String,Object> paramForRealTimeOpticalCode=new HashMap<String,Object>();
			paramForRealTimeOpticalCode.put("staffNo", staffNo);
			paramForRealTimeOpticalCode.put("portId",portId);
			item.remove("NO");
			item.put("NO", getRealTimeOpticalCode(paramForRealTimeOpticalCode));
		}
		return list;
	}
	public String getRealTimeOpticalCode(Map<String,Object> param) {
		String staffNo = (String) param.get("staffNo");
		Dblink dblink = DblinkUtil.getDbLinkByStaffNo(dblinkDao, staffNo);
		String jndi = dblink.getJndi();
		param.put("DBLINKUSERNAME", dblink.getDblinkUsername());
		String code = this.getRealTimeOpticalCode(jndi, param);
		return code;
	}
	
	@SuppressWarnings("finally")
	private String getRealTimeOpticalCode(String jndi, Map<String,Object> param) {
		String l = "";
		try {
			SwitchDataSourceUtil.setCurrentDataSource(jndi);
			l = inspectHelperDao.getRealTimeOpticalCode(param);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		} finally {
			return l;
		}

	}

	@Override
	public String analysisODFDinamicExcel(HttpServletRequest request,HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String orderId = request.getParameter("orderId");
			String equipmentId = request.getParameter("equipmentId");
			String staffNo = String.valueOf(session.getAttribute("staffNo"));
			String staffId = String.valueOf(session.getAttribute("staffId"));
			MultipartFile excelFile =  multipartRequest.getFile("excel");
			File excel = new File(excelFile.getOriginalFilename());
			excelFile.transferTo(excel);
			String targetId = "0035";
			Map<String,Object> param = new HashMap<String,Object>();
			String role = inspectHelperDao.getODFStaffRole(staffNo);
			if (role.equals("14")) {
				param.put("state_a", "1");
			} else if (role.equals("15")) {
				param.put("state_b", "1");
			} else if (role.equals("16")) {
				param.put("state_c", "1");
			} else if (role.equals("17")) {
				param.put("state_d", "1");
			}
			param.put("orderId", orderId);
			if (orderId == null) {
				param.put("seqName", "ORDER_ID");
				orderId = inspectHelperDao.getNextSeqVal(param);
			}
			param.put("equipmentId", equipmentId);
			param.put("targetId", targetId);
			param.put("staffNo", staffNo);
			param.put("staffId", staffId);
			boolean b = analysisODFDinamicExcel(param, excel);
			if (b) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		
	}

	@SuppressWarnings("unchecked")
	public boolean analysisODFDinamicExcel(Map<String,Object> param, File excel) {
		String orderId = (String) param.get("orderId");
		String equipmentId = (String) param.get("equipmentId");
		String targetId = (String) param.get("targetId");
		String staffNo = (String) param.get("staffNo");

		MyExcelTool parser = new MyExcelTool(excel);
		List<List> datas = parser.getDataInSheet(0);
		for (int j = 0; j < datas.size(); j++) {
			List row = datas.get(j);
			if (j >= 0 && row.size() >= 18) {
				String id = (String) (row.get(1) == null ? "" : row.get(1));
				String o5 = (String) (row.get(11) == null ? "" : row.get(11));// 现场情况
				String o6 = (String) (row.get(12) == null ? "" : row.get(12));// 整改情况
				String ftth = (String) (row.get(13) == null ? "" : row.get(13));// 是否FTTH
				if (j == 0) {
					if (!row.get(3).equals("设备编码")
							|| !row.get(4).equals("设备名称")
							|| !row.get(5).equals("工单性质")
							|| !row.get(6).equals("动态数据")
							|| !row.get(8).equals("端子")
							|| !row.get(9).equals("光路状态")
							|| !row.get(11).equals("现场情况（无问题的保留空白）")) {
						return false;
					}
				} else {
					// System.out.println("o2:"+o2+",row.get(0):"+row.get(0)+",j:"+j);
					// if(equipmentId==null){
					// equipmentId = (String) (row.get(2)==null?"":row.get(2));
					// }
					String description = "端子：" + row.get(8) + ",系统"
							+ row.get(7) + ",现场" + o5;
					if (!o5.equals("")) {
						if (o6.equals("已整改")) {
							String type = "1";
							saveResTrou(
									orderId,
									(String) param.get("equipmentId") == null ? (String) row
											.get(2) : equipmentId, targetId,
									type, description, staffNo, ftth, id);
						} else if (!o5.equals("") && !o6.equals("已整改")) {
							String type = "0";
							saveResTrou(
									orderId,
									(String) param.get("equipmentId") == null ? (String) row
											.get(2) : equipmentId, targetId,
									type, description, staffNo, ftth, id);
						}
					}
					/* 更新动态线芯的状态 */
					param.put("trueIds", id);
					inspectHelperDao.updateDinamicChange(param);

					// odf or not odf check
					Map param1 = new HashMap();
					param1.put("orderId", orderId);
					param1.put("equipmentId", (String) row.get(2));
					param1.put("staffNo", staffNo);
					param1.put("sn", "PC");
					param1.put("ckDate", new Date());
					param1.put("odfCheck", 1);
					if (equipmentId == null&&!(inspectHelperDao.checkResCount(param1)>0)) {
						inspectHelperDao.createTaskOrderCheck(param1);
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public String updateDinamicChange(HttpServletRequest request,HttpServletResponse response) {
	boolean b = true;
	try {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		Map<String,Object> param = new HashMap<String,Object>();
		String staffNo = (String) session.getAttribute("staffNo");
		String staffId = (String) session.getAttribute("staffId");
		String role = inspectHelperDao.getODFStaffRole(staffNo);
		if (role.equals("14")) {
			param.put("state_a", "1");
		} else if (role.equals("15")) {
			param.put("state_b", "1");
		} else if (role.equals("16")) {
			param.put("state_c", "1");
		} else if (role.equals("17")) {
			param.put("state_d", "1");
		}
		String trueIds = request.getParameter("trueIds");
		param.put("trueIds", trueIds);
		param.put("staffId", staffId);
		inspectHelperDao.updateDinamicChange(param);
	} catch (UnsupportedEncodingException e) {
		b = false;
		e.printStackTrace();
	}
	if (b) {
		return "true";
	} else {
		return "false";
	}
}

	@Override
	public String getODFStaffRole(String staffNo) {
		try {
			String role = inspectHelperDao.getODFStaffRole(staffNo);
			return role;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public boolean updateDinamicChange(Map param) {
		try {
			inspectHelperDao.updateDinamicChange(param);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
}
