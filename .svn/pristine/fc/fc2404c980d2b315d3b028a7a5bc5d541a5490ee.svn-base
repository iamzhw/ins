package com.cableCheck.serviceimpl;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CheckQualityReportDao;
import com.cableCheck.dao.SpecialCheckReportDao;
import com.cableCheck.service.SpecialCheckReportService;
import com.system.dao.StaffDao;
@SuppressWarnings("all")
@Transactional
@Service
public class SpecialCheckReportServiceImpl implements SpecialCheckReportService {
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private SpecialCheckReportDao specialCheckReportDao;
	@Resource
	private CheckQualityReportDao checkQualityReportDao;
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map paramMap = new HashMap();
		String staffId = request.getSession().getAttribute("staffId").toString();
		/**
		 * 请求参数
		 */
		
		String area_id = request.getParameter("AREA_ID");
		String son_area = request.getParameter("son_area");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String static_month = request.getParameter("static_month");
		String pcomplete_time = request.getParameter("PCOMPLETE_TIME");
		String pstart_time = request.getParameter("PSTART_TIME");
		String WLJB_ID = request.getParameter("WLJB_ID");
		int ifGly = staffDao.getifGly(staffId);
		/**
		 * 查询参数赋值
		 */
		paramMap.put("WLJB_ID", WLJB_ID);
	
		paramMap.put("staff_id", staffId);
		paramMap.put("AREA_ID", area_id);
		paramMap.put("son_area", son_area);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("PCOMPLETE_TIME", pcomplete_time);
		paramMap.put("PSTART_TIME", pstart_time);
		paramMap.put("static_month", static_month);
		
		/*List<Map<String,Object>> list = staffDao.getRoleList(staffId);
		String flag = "";
		for(Map map : list){
			String role_id = map.get("ROLE_ID").toString();
			if("266".equals(role_id)){
				flag = "266";
				break;
			}
		}
		paramMap.put("ROLE_ID", flag);*/
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> checkQualityReportList = specialCheckReportDao.query(query);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", checkQualityReportList);
		return resultMap;
	}

	@Override
	public Map<String, Object> queryByCity(HttpServletRequest request,
			UIPage pager) {
		Map paramMap = new HashMap();
		String staffId = request.getSession().getAttribute("staffId").toString();
		/**
		 * 请求参数
		 */
		
		String area_id = request.getParameter("AREA_ID");
		String son_area = request.getParameter("son_area");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String static_month = request.getParameter("static_month");
		String pcomplete_time = request.getParameter("PCOMPLETE_TIME");
		String pstart_time = request.getParameter("PSTART_TIME");
		int ifGly = staffDao.getifGly(staffId);
		/**
		 * 查询参数赋值
		 */
		
		String WLJB_ID = request.getParameter("WLJB_ID");
		paramMap.put("WLJB_ID", WLJB_ID);

		paramMap.put("staff_id", staffId);
		paramMap.put("AREA_ID", area_id);
		paramMap.put("son_area", son_area);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("PCOMPLETE_TIME", pcomplete_time);
		paramMap.put("PSTART_TIME", pstart_time);
		paramMap.put("static_month", static_month);
		
		/*List<Map<String,Object>> list = staffDao.getRoleList(staffId);
		String flag = "";
		for(Map map : list){
			String role_id = map.get("ROLE_ID").toString();
			if("266".equals(role_id)){
				flag = "266";
				break;
			}
		}
		paramMap.put("ROLE_ID", flag);*/
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> checkQualityReportList = specialCheckReportDao.queryByCity(query);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", checkQualityReportList);
		return resultMap;
	}

	@Override
	public void specialCheckReportDownload(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "地市 ", "资源整治单已检查","资源整治单发现问题","资源整改单总量","资源整治单检查端子数","资源整治单错误端子数","资源整治单端子准确率","FTTH拆机已检查","FTTH拆机发现问题","FTTH检查端子数","FTTH拆机错误端子数","FTTH端子准确率","IOM拆机已检查","IOM拆机发现问题","IOM拆机检查端子","IOM拆机错误端子","IOM拆机端子准确率","检查端子总量","错误端子总量","正确端子总量","端子准确率总量",
				"客响订单已检查", "客响订单发现问题", "客响订单问题1", "客响订单问题2","客响订单问题3","客响订单问题4","客响订单问题5","客响订单问题6","客响订单问题7"});
		List<String> code = Arrays.asList(new String[] { "CITY","资源整治单已检查","资源整治单发现问题","资源整改单总量","CHECK_PORT_ZY","WRONG_PORT_ZY","RATE_ZY","FTTH拆机已检查","FTTH拆机发现问题","CHECK_PORT_FTTH","WRONG_PORT_FTTH","RATE_FTTH","IOM拆机已检查","IOM拆机发现问题","CHECK_PORT_IOM","WRONG_PORT_IOM","RATE_IOM","CHECK_PORT","WRONG_PORT","RIGHT_PORT","RATE",
				"客响订单已检查", "客响订单发现问题", "客响订单问题1", "客响订单问题2", "客响订单问题3" ,"客响订单问题4","客响订单问题5","客响订单问题6","客响订单问题7"});
	Map<String, Object> paras = new HashMap<String, Object>();
		String AREA_ID = request.getParameter("AREA_ID");
		String son_area = request.getParameter("son_area");
		String START_TIME = request.getParameter("START_TIME");
		String COMPLETE_TIME = request.getParameter("COMPLETE_TIME");
		String static_month = request.getParameter("static_month");
		String PSTART_TIME = request.getParameter("PSTART_TIME");
		String PCOMPLETE_TIME = request.getParameter("PCOMPLETE_TIME");
		String WLJB_ID = request.getParameter("WLJB_ID");
		paras.put("WLJB_ID", WLJB_ID);
		paras.put("AREA_ID",AREA_ID );
		paras.put("son_area",son_area );
		paras.put("START_TIME",START_TIME );
		paras.put("COMPLETE_TIME", COMPLETE_TIME);
		paras.put("static_month",static_month);
		paras.put("PSTART_TIME",PSTART_TIME);
		paras.put("PCOMPLETE_TIME",PCOMPLETE_TIME );
	
		List<Map<String, Object>> data = specialCheckReportDao.queryDown(paras);
		
		int zycheck =0 ;
		int zywrong =0 ;
		int zyall =0 ;
		int port_zy =0 ;
		int wrong_zy =0 ;
		int ftthcheck =0 ;
		int ftthwrong  =0;
		int ftthport  =0;
		int wrong_ftth  =0;
		int iomcheck  =0;
		int iomwrong  =0;
		int iomport  =0;
		int wrong_iom  =0;
		int dz  =0;
		int dzw  =0;
		int dzr  =0;
		int kxcheck  =0;
		int kxwrong  =0;
		int kx0   =0;
		int kx1   =0;
		int kx2   =0;
		int kx3   =0;
		int kx4   =0;
		int kx5   =0;
		int kx6   =0;
		
		String dzwcl;
		String dzwcl_zy;
		String dzwcl_ftth;
		String dzwcl_iom;
				for (int i = 0; i < data.size(); i++) {
					zycheck  += Integer.valueOf(data.get(i).get("资源整治单已检查").toString());
					zywrong  += Integer.valueOf(data.get(i).get("资源整治单发现问题").toString());
					zyall+= Integer.valueOf(data.get(i).get("资源整改单总量").toString());
					port_zy += Integer.valueOf(data.get(i).get("CHECK_PORT_ZY").toString());
					wrong_zy  += Integer.valueOf(data.get(i).get("WRONG_PORT_ZY").toString());

					ftthcheck  += Integer.valueOf(data.get(i).get("FTTH拆机已检查").toString());

					ftthwrong  += Integer.valueOf(data.get(i).get("FTTH拆机发现问题").toString());
					ftthport  += Integer.valueOf(data.get(i).get("CHECK_PORT_FTTH").toString());
					wrong_ftth += Integer.valueOf(data.get(i).get("WRONG_PORT_FTTH").toString());
					iomcheck  += Integer.valueOf(data.get(i).get("IOM拆机已检查").toString());
					iomwrong  += Integer.valueOf(data.get(i).get("IOM拆机发现问题").toString());
					iomport  += Integer.valueOf(data.get(i).get("CHECK_PORT_IOM").toString());
					wrong_iom  += Integer.valueOf(data.get(i).get("WRONG_PORT_IOM").toString());
					 dz += Integer.valueOf(data.get(i).get("CHECK_PORT").toString());
					 dzw += Integer.valueOf(data.get(i).get("WRONG_PORT").toString());
					 dzr  += Integer.valueOf(data.get(i).get("RIGHT_PORT").toString());
					 kxcheck += Integer.valueOf(data.get(i).get("客响订单已检查").toString());
					 kxwrong  += Integer.valueOf(data.get(i).get("客响订单发现问题").toString());
					 kx0 += Integer.valueOf(data.get(i).get("客响订单问题1").toString());
					 kx1 += Integer.valueOf(data.get(i).get("客响订单问题2").toString());
					 kx2+= Integer.valueOf(data.get(i).get("客响订单问题3").toString());
					 kx3 += Integer.valueOf(data.get(i).get("客响订单问题4").toString());
					 kx4+= Integer.valueOf(data.get(i).get("客响订单问题5").toString());
					 kx5 += Integer.valueOf(data.get(i).get("客响订单问题6").toString());
					 kx6 += Integer.valueOf(data.get(i).get("客响订单问题7").toString());
					 
		}
				float dzwc=0;
				if(dz!=0){
				 dzwc=(float)(dz-dzw)/dz*100;
				}
				
				 
				float dzwc_zy=0 ;  
		         	if (port_zy!=0){
		         		dzwc_zy=(port_zy-wrong_zy)/port_zy*100;
		         	}
		         	float dzwc_ftth=0 ;  
		         	if (ftthport!=0){
		         		dzwc_ftth=(ftthport-wrong_ftth)/ftthport*100;
		         	}
		         	float dzwc_iom=0 ;  
		          	if (iomport!=0){
		          		dzwc_iom=(iomport-wrong_iom)/iomport*100;
		          	}
		  

				DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		        String  f1 = df.format(dzwc);
				dzwcl= f1+"%";
		        String  f2 = df.format(dzwc_zy);
		        dzwcl_zy= f2+"%";
		        String  f3 = df.format(dzwc_ftth);
		        dzwcl_ftth= f3+"%";
		        String  f4 = df.format(dzwc_iom);
		        dzwcl_iom= f4+"%";
				
				paras.put("资源整治单已检查", zycheck );
				paras.put("资源整治单发现问题", zywrong );
				paras.put("资源整改单总量", zyall );
		paras.put("CHECK_PORT_ZY", port_zy );
		paras.put("WRONG_PORT_ZY", wrong_zy );
		paras.put("FTTH拆机已检查", ftthcheck );
		paras.put("CITY", "总计");
		paras.put("RATE", dzwcl);
		paras.put("RATE_ZY", dzwcl_zy);
		paras.put("FTTH拆机发现问题", ftthwrong );
		paras.put("CHECK_PORT_FTTH", ftthport );
		paras.put("WRONG_PORT_FTTH",wrong_ftth );
paras.put("RATE_FTTH", dzwcl_ftth);
paras.put("IOM拆机已检查", iomcheck );
paras.put("IOM拆机发现问题", iomwrong );
paras.put("CHECK_PORT_IOM", iomport );
paras.put("WRONG_PORT_IOM", wrong_iom );
paras.put("RATE_IOM", dzwcl_iom);
paras.put("CHECK_PORT", dz);
paras.put("WRONG_PORT", dzw );
paras.put("RIGHT_PORT", dzr);
paras.put("客响订单已检查", kxcheck);
paras.put("客响订单发现问题", kxwrong );
paras.put("客响订单问题1", kx0);
paras.put("客响订单问题2", kx1);
paras.put("客响订单问题3", kx2);
paras.put("客响订单问题4", kx3);
paras.put("客响订单问题5", kx4);
paras.put("客响订单问题6", kx5);
paras.put("客响订单问题7", kx6);
		data.add(paras);
		
		String fileName = "专项检查报告";
		String firstLine = "专项检查报告";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void specialCheckReportDownloadByCity(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "地市 ", "资源整治单已检查","资源整治单发现问题","资源整改单总量","资源整治单检查端子数","资源整治单错误端子数","资源整治单端子准确率","FTTH拆机已检查","FTTH拆机发现问题","FTTH检查端子数","FTTH拆机错误端子数","FTTH端子准确率","IOM拆机已检查","IOM拆机发现问题","IOM拆机总量","IOM拆机检查端子","IOM拆机错误端子","IOM拆机端子准确率","检查端子总量","错误端子总量","正确端子总量","端子准确率总量",
				"客响订单已检查", "客响订单发现问题", "客响订单问题1", "客响订单问题2","客响订单问题3","客响订单问题4","客响订单问题5","客响订单问题6","客响订单问题7","客响订单总量"});
		List<String> code = Arrays.asList(new String[] { "CITY","资源整治单已检查","资源整治单发现问题","资源整改单总量","CHECK_PORT_ZY","WRONG_PORT_ZY","RATE_ZY","FTTH拆机已检查","FTTH拆机发现问题","CHECK_PORT_FTTH","WRONG_PORT_FTTH","RATE_FTTH","IOM拆机已检查","IOM拆机发现问题","IOM拆机总量","CHECK_PORT_IOM","WRONG_PORT_IOM","RATE_IOM","CHECK_PORT","WRONG_PORT","RIGHT_PORT","RATE",
				"客响订单已检查", "客响订单发现问题", "客响订单问题1", "客响订单问题2", "客响订单问题3" ,"客响订单问题4","客响订单问题5","客响订单问题6","客响订单问题7","客响订单总量"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String AREA_ID = request.getParameter("AREA_ID");
		String son_area = request.getParameter("son_area");
		String START_TIME = request.getParameter("START_TIME");
		String COMPLETE_TIME = request.getParameter("COMPLETE_TIME");
		String static_month = request.getParameter("static_month");
		String PSTART_TIME = request.getParameter("PSTART_TIME");
		String PCOMPLETE_TIME = request.getParameter("PCOMPLETE_TIME");
		String WLJB_ID = request.getParameter("WLJB_ID");
		paras.put("WLJB_ID", WLJB_ID);

		paras.put("AREA_ID",AREA_ID );
		paras.put("son_area",son_area );
		paras.put("START_TIME",START_TIME );
		paras.put("COMPLETE_TIME", COMPLETE_TIME);
		paras.put("static_month",static_month);
		paras.put("PSTART_TIME",PSTART_TIME);
		paras.put("PCOMPLETE_TIME",PCOMPLETE_TIME );
		List<Map<String, Object>> data = specialCheckReportDao.queryDownByCity(paras);
		int zycheck =0 ;
		int zywrong =0 ;
		int zyall =0 ;
		int port_zy =0 ;
		int wrong_zy =0 ;
		int ftthcheck =0 ;
		int ftthwrong  =0;
		int ftthport  =0;
		int wrong_ftth  =0;
		int iomcheck  =0;
		int iomwrong  =0;
		int iomport  =0;
		int wrong_iom  =0;
		int dz  =0;
		int dzw  =0;
		int dzr  =0;
		int kxcheck  =0;
		int kxwrong  =0;
		int kx0   =0;
		int kx1   =0;
		int kx2   =0;
		int kx3   =0;
		int kx4   =0;
		int kx5   =0;
		int kx6   =0;
		int iom_all = 0;
		int kx_all = 0;
		String dzwcl;
		String dzwcl_zy;
		String dzwcl_ftth;
		String dzwcl_iom;
				for (int i = 0; i < data.size(); i++) {
					zycheck  += Integer.valueOf(data.get(i).get("资源整治单已检查").toString());
					zywrong  += Integer.valueOf(data.get(i).get("资源整治单发现问题").toString());
					zyall+= Integer.valueOf(data.get(i).get("资源整改单总量").toString());
					port_zy += Integer.valueOf(data.get(i).get("CHECK_PORT_ZY").toString());
					wrong_zy  += Integer.valueOf(data.get(i).get("WRONG_PORT_ZY").toString());

					ftthcheck  += Integer.valueOf(data.get(i).get("FTTH拆机已检查").toString());

					ftthwrong  += Integer.valueOf(data.get(i).get("FTTH拆机发现问题").toString());
					ftthport  += Integer.valueOf(data.get(i).get("CHECK_PORT_FTTH").toString());
					wrong_ftth += Integer.valueOf(data.get(i).get("WRONG_PORT_FTTH").toString());
					iomcheck  += Integer.valueOf(data.get(i).get("IOM拆机已检查").toString());
					iomwrong  += Integer.valueOf(data.get(i).get("IOM拆机发现问题").toString());
					iomport  += Integer.valueOf(data.get(i).get("CHECK_PORT_IOM").toString());
					wrong_iom  += Integer.valueOf(data.get(i).get("WRONG_PORT_IOM").toString());
					 dz += Integer.valueOf(data.get(i).get("CHECK_PORT").toString());
					 dzw += Integer.valueOf(data.get(i).get("WRONG_PORT").toString());
					 dzr  += Integer.valueOf(data.get(i).get("RIGHT_PORT").toString());
					 kxcheck += Integer.valueOf(data.get(i).get("客响订单已检查").toString());
					 kxwrong  += Integer.valueOf(data.get(i).get("客响订单发现问题").toString());
					 kx0 += Integer.valueOf(data.get(i).get("客响订单问题1").toString());
					 kx1 += Integer.valueOf(data.get(i).get("客响订单问题2").toString());
					 kx2+= Integer.valueOf(data.get(i).get("客响订单问题3").toString());
					 kx3 += Integer.valueOf(data.get(i).get("客响订单问题4").toString());
					 kx4+= Integer.valueOf(data.get(i).get("客响订单问题5").toString());
					 kx5 += Integer.valueOf(data.get(i).get("客响订单问题6").toString());
					 kx6 += Integer.valueOf(data.get(i).get("客响订单问题7").toString());
					 iom_all += Integer.valueOf(data.get(i).get("IOM拆机总量").toString());
					 kx_all += Integer.valueOf(data.get(i).get("客响订单总量").toString());
				 
		}
				float dzwc=0;
				if(dz!=0){
				 dzwc=(float)(dz-dzw)/dz*100;
				}
				
				 
				float dzwc_zy=0 ;  
		         	if (port_zy!=0){
		         		dzwc_zy=(port_zy-wrong_zy)/port_zy*100;
		         	}
		         	float dzwc_ftth=0 ;  
		         	if (ftthport!=0){
		         		dzwc_ftth=(ftthport-wrong_ftth)/ftthport*100;
		         	}
		         	float dzwc_iom=0 ;  
		          	if (iomport!=0){
		          		dzwc_iom=(iomport-wrong_iom)/iomport*100;
		          	}
		  

				DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		        String  f1 = df.format(dzwc);
				dzwcl= f1+"%";
		        String  f2 = df.format(dzwc_zy);
		        dzwcl_zy= f2+"%";
		        String  f3 = df.format(dzwc_ftth);
		        dzwcl_ftth= f3+"%";
		        String  f4 = df.format(dzwc_iom);
		        dzwcl_iom= f4+"%";
		        paras.put("IOM拆机总量", iom_all );
				paras.put("客响订单总量", kx_all );
				paras.put("资源整治单已检查", zycheck );
				paras.put("资源整治单发现问题", zywrong );
				paras.put("资源整改单总量", zyall );
		paras.put("CHECK_PORT_ZY", port_zy );
		paras.put("WRONG_PORT_ZY", wrong_zy );
		paras.put("FTTH拆机已检查", ftthcheck );
		paras.put("CITY", "总计");
		paras.put("RATE", dzwcl);
		paras.put("RATE_ZY", dzwcl_zy);
		paras.put("FTTH拆机发现问题", ftthwrong );
		paras.put("CHECK_PORT_FTTH", ftthport );
		paras.put("WRONG_PORT_FTTH",wrong_ftth );
paras.put("RATE_FTTH", dzwcl_ftth);
paras.put("IOM拆机已检查", iomcheck );
paras.put("IOM拆机发现问题", iomwrong );
paras.put("CHECK_PORT_IOM", iomport );
paras.put("WRONG_PORT_IOM", wrong_iom );
paras.put("RATE_IOM", dzwcl_iom);
paras.put("CHECK_PORT", dz);
paras.put("WRONG_PORT", dzw );
paras.put("RIGHT_PORT", dzr);
paras.put("客响订单已检查", kxcheck);
paras.put("客响订单发现问题", kxwrong );
paras.put("客响订单问题1", kx0);
paras.put("客响订单问题2", kx1);
paras.put("客响订单问题3", kx2);
paras.put("客响订单问题4", kx3);
paras.put("客响订单问题5", kx4);
paras.put("客响订单问题6", kx5);
paras.put("客响订单问题7", kx6);
		data.add(paras);
		
		String fileName = "专项检查报告";
		String firstLine = "专项检查报告";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> selArea() {
		// TODO Auto-generated method stub
		return checkQualityReportDao.selArea();
	}

	@Override
	public void equipmentDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		Map<String, Object> paras = new HashMap<String, Object>();
		String type = request.getParameter("type");
		paras.put("areaId", para.get("areaId"));
		paras.put("WLJB_ID", para.get("WLJB_ID"));

		paras.put("parent_area_id", para.get("parent_area_id"));
		paras.put("START_TIME", para.get("START_TIME"));
		paras.put("COMPLETE_TIME", para.get("COMPLETE_TIME"));
		paras.put("static_month", para.get("static_month"));
		paras.put("PSTART_TIME", para.get("PSTART_TIME"));
		paras.put("PCOMPLETE_TIME", para.get("PCOMPLETE_TIME"));
		List<String> title = null;
		List<String> code = null;
		if("7".equals(type)||"8".equals(type)||"9".equals(type)||"10".equals(type)||"11".equals(type)||"12".equals(type)||"13".equals(type)||"14".equals(type)||"15".equals(type)||"16".equals(type)){
			 title = Arrays.asList(new String[] {  "区域 ","IOM工单号","工单编码","检查时间" ,"检查人"});
				code = Arrays.asList(new String[] { "NAME", "IOM_ORDER_ID" ,"ORDER_CODE","CREATE_TIME","STAFF_NAME"});
				
			
		}
		else if("27".equals(type)){
			 title = Arrays.asList(new String[] {  "区域 ","定单编码","定单主题","定单类型" ,"产品服务","竣工时间","光路编码(拆机、移机中的拆)","动作类型名称","施工岗","施工人"});
				code = Arrays.asList(new String[] { "AREA_NAME", "ORDER_CODE" ,"ORDER_TITLE","ORDER_TYPE","SERVICE_NAME","FINISH_DATE","DEL_OPT_CODE","ACTION_TYPE_NAME","POST_NAME","PARTY_NAME"});
				
			
		}else if ("38".equals(type)){
			 title = Arrays.asList(new String[] {  "区域","工单ID","工单编码","工单类型" ,"接入时间","'竣工时间","流程ID","完成时间","光路编码","需求名称"});
			 code = Arrays.asList(new String[] {  "AREA_NAME","ID","ORDER_CODE","ORDER_TYPE","ACCEPT_DATE","FINISH_DATE","PSO_ID","COMPLETE_DATE","OPT_CODE","XUQIU"});
			
		}else{
		 title = Arrays.asList(new String[] {  "区域 ","任务名称","任务编码 ","任务状态","检查时间" ,"检查人 "});
		 code = Arrays.asList(new String[] { "NAME", "TASK_NAME" ,"TASK_NO","STATUS_ID","START_TIME","STAFF_NAME"});
		}
		List<Map<String, Object>> data = null;
		if(para.get("parent_area_id")!=null&&!"".equals(para.get("parent_area_id"))){
			if("20".equals(type)){
	        	data = specialCheckReportDao.downZYByCity(paras);
	        	
	        }else if ("21".equals(type)){
	        	data = specialCheckReportDao.downZYWByCity(paras);
		        	
	        }else if ("22".equals(type)){
	        	data = specialCheckReportDao.downZYALLByCity(paras);
		        	
	        }
			 else if ("23".equals(type)){
				 data = specialCheckReportDao.downFTTHByCity(paras);
	        	
			 }else if ("24".equals(type)){
				 data = specialCheckReportDao.downFTTHWByCity(paras);
			 } 	
		     else if ("25".equals(type)){
		    	 data = specialCheckReportDao.downIOMEqpByCity(paras);
		     
	        }else if ("26".equals(type)){
	        	data = specialCheckReportDao.downIOMWByCity(paras);
		        	
	        }else if ("27".equals(type)){
	        	data = specialCheckReportDao.downIOMALLByCity(paras);
		        	
	        } else if ("28".equals(type)){
	        	data = specialCheckReportDao.downKXByCity(paras);
	        	
	        }
	       
			else if ("29".equals(type)){
				data = specialCheckReportDao.downKXWByCity(paras);
		        	
		        }else if ("30".equals(type)){
		        	data = specialCheckReportDao.downKXW1ByCity(paras);
		        	
		        }else if ("31".equals(type)){
		        	data = specialCheckReportDao.downKXW2ByCity(paras);
		        	
		        }else if ("33".equals(type)){
		        	data = specialCheckReportDao.downKXW3ByCity(paras);
		        	
		        }else if ("34".equals(type)){
		        	data = specialCheckReportDao.downKXW4ByCity(paras);
		        	
		        }
		        else if ("35".equals(type)){
		        	data = specialCheckReportDao.downKXW5ByCity(paras);
		        	
		        }
		        else if ("36".equals(type)){
		        	data = specialCheckReportDao.downKXW6ByCity(paras);
		      
		        }
		        else if ("37".equals(type)) {
		        	data = specialCheckReportDao.downKXW7ByCity(paras);
		         
		           }else{
		        	   data = specialCheckReportDao.downKXALLByCity(paras);
 
		           
		           }
	       
		}else{
        if("0".equals(type)){
        	data = specialCheckReportDao.downZY(paras);
        	
        }else if ("1".equals(type)){
        	data = specialCheckReportDao.downZYW(paras);
        	
        }else if ("2".equals(type)){
        	data = specialCheckReportDao.downZYALL(paras);
        	
        }else if ("3".equals(type)){
        	data = specialCheckReportDao.downFTTH(paras);
        	
        }else if ("4".equals(type)){
        	data = specialCheckReportDao.downFTTHW(paras);
        	
        }else if ("5".equals(type)){
        	data = specialCheckReportDao.downIOM(paras);
        	
        }else if ("6".equals(type)){
        	data = specialCheckReportDao.downIOMW(paras);
        	
        }else if ("7".equals(type)){
        	data = specialCheckReportDao.downKX(paras);
        	
        }else if ("8".equals(type)){
        	data = specialCheckReportDao.downKXW(paras);
        	
        }else if ("9".equals(type)){
        	data = specialCheckReportDao.downKXW1(paras);
        	
        }else if ("10".equals(type)){
        	data = specialCheckReportDao.downKXW2(paras);
        	
        }else if ("11".equals(type)){
        	data = specialCheckReportDao.downKXW3(paras);
        	
        }else if ("12".equals(type)){
        	data = specialCheckReportDao.downKXW4(paras);
        	
        }
        else if ("13".equals(type)){
        	data = specialCheckReportDao.downKXW5(paras);
        	
        }
        else if ("14".equals(type)){
        	data = specialCheckReportDao.downKXW6(paras);
      
        }
        else {
        	data = specialCheckReportDao.downKXW7(paras);
         
           }
		}
	   
		String fileName = "设备清单";
		String firstLine = "设备清单";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Map<String, Object> equipmentQuery(HttpServletRequest request,
			UIPage pager) {
		String type = request.getParameter("type");
		String areaId = request.getParameter("AREAID");
		String parent_area_id = request.getParameter("parent_area_id");
		String static_month = request.getParameter("static_month");
		String pcomplete_time = request.getParameter("PCOMPLETE_TIME");
		String pstart_time = request.getParameter("PSTART_TIME");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
		String WLJB_ID = request.getParameter("WLJB_ID");
		Map paramMap = new HashMap();
		paramMap.put("areaId", areaId);
		paramMap.put("WLJB_ID", WLJB_ID);

		paramMap.put("parent_area_id", parent_area_id);
		paramMap.put("PCOMPLETE_TIME", pcomplete_time);
		paramMap.put("PSTART_TIME", pstart_time);
		paramMap.put("static_month", static_month);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("RES_TYPE_ID", RES_TYPE_ID);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> list = null;
		if(parent_area_id!=null&&!"".equals(parent_area_id)){
			if("20".equals(type)){
	        	list = specialCheckReportDao.queryZYByCity(query);
	        	
	        }else if ("21".equals(type)){
	        	 list = specialCheckReportDao.queryZYWByCity(query);
		        	
	        }else if ("22".equals(type)){
	        	 list = specialCheckReportDao.queryZYALLByCity(query);
		        	
	        }
			 else if ("23".equals(type)){
	        	 list = specialCheckReportDao.queryFTTHByCity(query);
	        	
			 }else if ("24".equals(type)){
	        	 list = specialCheckReportDao.queryFTTHWByCity(query);
			 } 	
		     else if ("25".equals(type)){
	        	 list = specialCheckReportDao.queryIOMByCity(query);
		     
	        }else if ("26".equals(type)){
	        	 list = specialCheckReportDao.queryIOMWByCity(query);
		        	
	        }else if ("27".equals(type)){
	        	 list = specialCheckReportDao.queryIOMALLByCity(query);
		        	
	        } else if ("28".equals(type)){
	         list = specialCheckReportDao.queryKXByCity(query);
	        	
	        }
	       
			else if ("29".equals(type)){
		        	 list = specialCheckReportDao.queryKXWByCity(query);
		        	
		        }else if ("30".equals(type)){
		        	 list = specialCheckReportDao.queryKXW1ByCity(query);
		        	
		        }else if ("31".equals(type)){
		        	 list = specialCheckReportDao.queryKXW2ByCity(query);
		        	
		        }else if ("33".equals(type)){
		        	 list = specialCheckReportDao.queryKXW3ByCity(query);
		        	
		        }else if ("34".equals(type)){
		        	 list = specialCheckReportDao.queryKXW4ByCity(query);
		        	
		        }
		        else if ("35".equals(type)){
		        	 list = specialCheckReportDao.queryKXW5ByCity(query);
		        	
		        }
		        else if ("36".equals(type)){
		         list = specialCheckReportDao.queryKXW6ByCity(query);
		      
		        }
		        else if ("37".equals(type)) {
		            list = specialCheckReportDao.queryKXW7ByCity(query);
		         
		           }else{
			            list = specialCheckReportDao.queryKXALLByCity(query);
 
		           
		           }
	       
		}else{
        if("0".equals(type)){
        	list = specialCheckReportDao.queryZY(query);
        	
        }else if ("1".equals(type)){
        	 list = specialCheckReportDao.queryZYW(query);
        	
        }else if ("2".equals(type)){
        	 list = specialCheckReportDao.queryZYALL(query);
        	
        }else if ("3".equals(type)){
        	 list = specialCheckReportDao.queryFTTH(query);
        	
        }else if ("4".equals(type)){
        	 list = specialCheckReportDao.queryFTTHW(query);
        	
        }else if ("5".equals(type)){
        	 list = specialCheckReportDao.queryIOM(query);
        	
        }else if ("6".equals(type)){
        	 list = specialCheckReportDao.queryIOMW(query);
        	
        }else if ("7".equals(type)){
        	 list = specialCheckReportDao.queryKX(query);
        	
        }else if ("8".equals(type)){
        	 list = specialCheckReportDao.queryKXW(query);
        	
        }else if ("9".equals(type)){
        	 list = specialCheckReportDao.queryKXW1(query);
        	
        }else if ("10".equals(type)){
        	 list = specialCheckReportDao.queryKXW2(query);
        	
        }else if ("11".equals(type)){
        	 list = specialCheckReportDao.queryKXW3(query);
        	
        }else if ("12".equals(type)){
        	 list = specialCheckReportDao.queryKXW4(query);
        	
        }
        else if ("13".equals(type)){
        	 list = specialCheckReportDao.queryKXW5(query);
        	
        }
        else if ("14".equals(type)){
         list = specialCheckReportDao.queryKXW6(query);
      
        }
        else {
            list = specialCheckReportDao.queryKXW7(query);
         
           }
		}
        Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
		
	}

	@Override
	public void portDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] {  "区域 ","端子ID","端子编码 ","设备名称","设备编码" ,"任务名称 ","检查人 ","检查时间 ","问题描述 ","是否合格 ","光路名称","光路名编码"});
		List<String> code = Arrays.asList(new String[] { "NAME", "PORT_ID" ,"PORT_NO","EQP_NAME","EQP_NO","TASK_NAME","STAFF_NAME","CHECK_TIME","DESCRIPT","ISCHECKOK","GLMC","GLBM"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String type = request.getParameter("type");
		paras.put("parent_area_id", para.get("parent_area_id"));
		paras.put("areaId", para.get("areaId"));
		paras.put("START_TIME", para.get("START_TIME"));
		paras.put("COMPLETE_TIME", para.get("COMPLETE_TIME"));
		paras.put("WLJB_ID", para.get("WLJB_ID"));

		List<Map<String, Object>> data = null;
		if(para.get("parent_area_id")!=null&&!"".equals(para.get("parent_area_id"))){
			if ("50".equals(type)) {
				data = specialCheckReportDao.downZyPortByCity(paras);

			}else if ("51".equals(type)) {
				data = specialCheckReportDao.downZyWrongPortByCity(paras);

			} else if ("52".equals(type)) {
				data = specialCheckReportDao.downFTTHPortByCity(paras);

			}else if ("53".equals(type)) {
				data = specialCheckReportDao.downFTTHWrongPortByCity(paras);

			}else if ("54".equals(type)) {
				data = specialCheckReportDao.downIOMPortByCity(paras);

			}else if ("55".equals(type)) {
				data = specialCheckReportDao.downIOMWrongPortByCity(paras);

			} else if ("56".equals(type)) {
				data = specialCheckReportDao.downCheckPortByCity(paras);

			} else if ("57".equals(type)){
				data = specialCheckReportDao.downRightPortByCity(paras);
			}else {
				data = specialCheckReportDao.downPromblePortByCity(paras);
			}
		} else {
			if ("40".equals(type)) {
				data = specialCheckReportDao.downZyCheckPort(paras);

			}
			else if ("41".equals(type)) {
				data = specialCheckReportDao.downZyWrongPort(paras);

			}
			else if ("42".equals(type)) {
				data = specialCheckReportDao.downFTTHCheckPort(paras);

			}else if ("43".equals(type)) {
				data = specialCheckReportDao.downFTTHWrongPort(paras);

			}else if ("44".equals(type)) {
				data = specialCheckReportDao.downIOMCheckPort(paras);

			}else if ("45".equals(type)) {
				data = specialCheckReportDao.downIOMWrongPort(paras);

			}
			else if ("46".equals(type)) {
				data = specialCheckReportDao.downCheckPort(paras);

			} else if ("47".equals(type)){
				data = specialCheckReportDao.downRightPort (paras);
			}else {
				data = specialCheckReportDao.downPromblePort(paras);
			}
		}
		
	   
		String fileName = "端子清单";
		String firstLine = "端子清单";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Map<String, Object> portQuery(HttpServletRequest request,
			UIPage pager) {
		String type = request.getParameter("type");
		String areaId = request.getParameter("AREAID");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String static_month = request.getParameter("static_month");
		String parent_area_id = request.getParameter("parent_area_id");
		String WLJB_ID = request.getParameter("WLJB_ID");
		Map paramMap = new HashMap();
		paramMap.put("areaId", areaId);
		paramMap.put("WLJB_ID", WLJB_ID);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("parent_area_id", parent_area_id);
		paramMap.put("static_month", static_month);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> list = null;
		if(parent_area_id!=null&&!"".equals(parent_area_id)){
			if ("50".equals(type)) {
				list = specialCheckReportDao.queryZyPortByCity(query);

			}else if ("51".equals(type)) {
				list = specialCheckReportDao.queryZyWrongPortByCity(query);

			} else if ("52".equals(type)) {
				list = specialCheckReportDao.queryFTTHPortByCity(query);

			}else if ("53".equals(type)) {
				list = specialCheckReportDao.queryFTTHWrongPortByCity(query);

			}else if ("54".equals(type)) {
				list = specialCheckReportDao.queryIOMPortByCity(query);

			}else if ("55".equals(type)) {
				list = specialCheckReportDao.queryIOMWrongPortByCity(query);

			} else if ("56".equals(type)) {
				list = specialCheckReportDao.queryCheckPortByCity(query);

			} else if ("57".equals(type)){
				list = specialCheckReportDao.queryRightPortByCity(query);
			}else {
				list = specialCheckReportDao.queryPromblePortByCity(query);
			}
		} else {
			if ("40".equals(type)) {
				list = specialCheckReportDao.queryZyCheckPort(query);

			}
			else if ("41".equals(type)) {
				list = specialCheckReportDao.queryZyWrongPort(query);

			}
			else if ("42".equals(type)) {
				list = specialCheckReportDao.queryFTTHCheckPort(query);

			}else if ("43".equals(type)) {
				list = specialCheckReportDao.queryFTTHWrongPort(query);

			}else if ("44".equals(type)) {
				list = specialCheckReportDao.queryIOMCheckPort(query);

			}else if ("45".equals(type)) {
				list = specialCheckReportDao.queryIOMWrongPort(query);

			}
			else if ("46".equals(type)) {
				list = specialCheckReportDao.queryCheckPort(query);

			} else if ("47".equals(type)){
				list = specialCheckReportDao.queryRightPort (query);
			}else {
				list = specialCheckReportDao.queryPromblePort(query);
			}
		}
        Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

}
