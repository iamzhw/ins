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
import com.cableCheck.dao.ReformOrderReportDao;
import com.cableCheck.service.ReformOrderReportService;
import com.system.dao.StaffDao;

@SuppressWarnings("all")
@Transactional
@Service
public class ReformOrderReportServiceImpl implements ReformOrderReportService{
	@Autowired
	private StaffDao staffDao;
	@Resource
	private ReformOrderReportDao reformOrderReportDao;
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
		String mcomplete_time = request.getParameter("MCOMPLETE_TIME");
		String mstart_time = request.getParameter("MSTART_TIME");
		String acomplete_time = request.getParameter("ACOMPLETE_TIME");
		String astart_time = request.getParameter("ASTART_TIME");
		String WLJB_ID = request.getParameter("WLJB_ID");
		int ifGly = staffDao.getifGly(staffId);
		/**
		 * 查询参数赋值
		 */
		
	
		paramMap.put("staff_id", staffId);
		paramMap.put("AREA_ID", area_id);
		paramMap.put("son_area", son_area);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("PCOMPLETE_TIME", pcomplete_time);
		paramMap.put("PSTART_TIME", pstart_time);
		paramMap.put("ACOMPLETE_TIME", acomplete_time);
		paramMap.put("MSTART_TIME", mstart_time);
		paramMap.put("MCOMPLETE_TIME", mcomplete_time);
		paramMap.put("ASTART_TIME", astart_time);
		paramMap.put("static_month", static_month);
		paramMap.put("WLJB_ID", WLJB_ID);
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
		List<Map<String, Object>> reformOrderReportList = reformOrderReportDao.query(query);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", reformOrderReportList);
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selArea() {
		// TODO Auto-generated method stub
		return checkQualityReportDao.selArea();
	}
	
	@Override
	public void reformOrderReportDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","周期性检查总数","周期性检查派发",
				"周期性检查已整治 ","周期性检查未通过审核", "周期性检查通过审核", "一键反馈总数","一键反馈派发", "一键反馈已整治 ","一键反馈未通过审核","一键反馈通过审核 ","不可预告抽查总数","不可预告抽查派发","不可预告抽查已整治","不可预告抽查未通过审核","不可预告抽查通过审核","发现错误端子数"," 检查人员一键改端子数","上报整改端子数","派发整改端子数","整改回单端子数","整改成功端子数","整治派发率(按设备)","整治回单率(按设备)","整治完成率(按设备)","整治派发率(按端子)","整治回单率(按端子)","整治完成率(按端子)","2018年整改率"});
		List<String> code = Arrays.asList(new String[] { "CITYNAME","NAME","周期性检查总数",
				"周期性检查派发", "周期性检查已整治","周期性检查未通过审核", "周期性检查通过审核","一键反馈总数","一键反馈派发", "一键反馈已整治" ,"一键反馈未通过审核","一键反馈通过审核","不可预告抽查总数","不可预告抽查派发","不可预告抽查已整治","不可预告抽查未通过审核","不可预告抽查通过审核","发现错误端子数","检查人员一键改端子数","上报整改端子数","派发整改端子数","整改回单端子数","整改成功端子数","整治派发率按设备","整治回单率按设备","整治完成率按设备","整治派发率按端子","整治回单率按端子","整治完成率按端子","2018年整改率"});
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("AREA_ID", para.get("area_id"));
		paras.put("son_area", para.get("SON_AREA_ID"));
		paras.put("START_TIME", para.get("START_TIME"));
		paras.put("COMPLETE_TIME", para.get("COMPLETE_TIME"));
		paras.put("static_month", para.get("static_month"));
		paras.put("PSTART_TIME", para.get("PSTART_TIME"));
		paras.put("PCOMPLETE_TIME", para.get("PCOMPLETE_TIME"));
		paras.put("ASTART_TIME", para.get("ASTART_TIME"));
		paras.put("ACOMPLETE_TIME", para.get("ACOMPLETE_TIME"));
		paras.put("MSTART_TIME", para.get("MSTART_TIME"));
		paras.put("MCOMPLETE_TIME", para.get("MCOMPLETE_TIME"));
		paras.put("WLJB_ID", para.get("WLJB_ID"));
		
		List<Map<String, Object>> data = reformOrderReportDao.queryDown(paras);
		int ZQSUM=0 ;
		int ZQPF=0 ;
		int ZQZZ=0 ;
		int ZQSHWTG=0 ;
		int ZQSH=0 ;
		int YJSUM=0 ;
		int YJPF=0 ;
		int YJZZ=0 ;
		int YJSHWTG=0 ;
		int YJSH=0 ;
		int BKSUM=0 ;
		int BKPF=0 ;
		int BKZZ=0 ;
		int BKSHWTG=0 ;
		int BKSH=0 ;
		int FXDZ=0;
		int YJGDZ=0;
		int SBDZ=0;
		int PFDZ=0;
		int HDDZ=0;
		int SHDZ=0;
				for (int i = 0; i < data.size(); i++) {
					ZQSUM += Integer.valueOf(data.get(i).get("周期性检查总数").toString());
					ZQPF += Integer.valueOf(data.get(i).get("周期性检查派发").toString());
					ZQZZ += Integer.valueOf(data.get(i).get("周期性检查已整治").toString());
					ZQSHWTG += Integer.valueOf(data.get(i).get("周期性检查未通过审核").toString());
					ZQSH += Integer.valueOf(data.get(i).get("周期性检查通过审核").toString());
					YJPF += Integer.valueOf(data.get(i).get("一键反馈派发").toString());
					YJZZ += Integer.valueOf(data.get(i).get("一键反馈已整治").toString());
					YJSHWTG += Integer.valueOf(data.get(i).get("一键反馈未通过审核").toString());
					YJSH += Integer.valueOf(data.get(i).get("一键反馈通过审核").toString());
					YJSUM += Integer.valueOf(data.get(i).get("一键反馈总数").toString());
					BKSUM += Integer.valueOf(data.get(i).get("不可预告抽查总数").toString());
					BKPF += Integer.valueOf(data.get(i).get("不可预告抽查派发").toString());
					BKZZ += Integer.valueOf(data.get(i).get("不可预告抽查已整治").toString());
					BKSHWTG += Integer.valueOf(data.get(i).get("不可预告抽查未通过审核").toString());
					BKSH += Integer.valueOf(data.get(i).get("不可预告抽查通过审核").toString());
					FXDZ += Integer.valueOf(data.get(i).get("发现错误端子数").toString());
					YJGDZ += Integer.valueOf(data.get(i).get("检查人员一键改端子数").toString());
					SBDZ += Integer.valueOf(data.get(i).get("上报整改端子数").toString());
					PFDZ += Integer.valueOf(data.get(i).get("派发整改端子数").toString());
					HDDZ += Integer.valueOf(data.get(i).get("整改回单端子数").toString());
					SHDZ += Integer.valueOf(data.get(i).get("整改成功端子数").toString());
					
		}
				float PFLSB;
				float HDLSB;
				float SHLSB;
				float PFLDZ;
				float HDLDZ;
				float SHLDZ;
				float YJGLDZ;
				if(ZQSUM+YJSUM+BKSUM==0){
					 PFLSB= 0;
				}else{
					
					 PFLSB=(float)(ZQPF+YJPF+BKPF)/(ZQSUM+YJSUM+BKSUM)*100;
				}
				
				if(ZQPF+YJPF+BKPF==0){
					 HDLSB= 0;
					 SHLSB= 0 ;
				}else{
					
					 HDLSB=(float)(ZQZZ+YJZZ+BKZZ)/(ZQPF+YJPF+BKPF)*100;
					SHLSB=(float)(ZQSH+YJSH+BKSH)/(ZQPF+YJPF+BKPF)*100;
				}
				
				if(SBDZ==0){
					 PFLDZ= 0;
				}else{
					
					PFLDZ=(float)(PFDZ)/SBDZ*100;
				}
				
				if(PFDZ==0){
					 HDLDZ= 0;
					SHLDZ= 0 ;
				}else{
					
					 HDLDZ=(float)(HDDZ)/PFDZ*100;
					 SHLDZ=(float)(SHDZ)/PFDZ*100;
				}
				if(FXDZ==0){
					YJGLDZ=0;
				}else{
					YJGLDZ=(float)(SHDZ+YJGDZ)/FXDZ*100;
				}


				DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		        String  f1 = df.format(PFLSB);
		        String  f2 = df.format(HDLSB);
		        String  f3 = df.format(SHLSB);
		        String  f4 = df.format(PFLDZ);
		        String  f5 = df.format(HDLDZ);
		        String  f6 = df.format(SHLDZ);//返回的是String类型的
		        String  f7 = df.format(YJGLDZ);
		        String PFLSBWCL= f1+"%";
		        String HDLSBWCL= f2+"%";
		        String SHLSBWCL= f3+"%";
		        String PFLDZWCL= f4+"%";
		        String HDLDZWCL= f5+"%";
		        String SHLDZWCL= f6+"%";
		        String YJGLDZWCL= f7+"%";
				paras.put("周期性检查总数", ZQSUM);		
				paras.put("周期性检查派发", ZQPF);
				paras.put("周期性检查已整治", ZQZZ);
				paras.put("周期性检查未通过审核", ZQSHWTG);
				paras.put("周期性检查通过审核", ZQSH);
				paras.put("一键反馈派发", YJPF);
				paras.put("一键反馈已整治", YJZZ);
				paras.put("一键反馈未通过审核", YJSHWTG);
				paras.put("一键反馈通过审核", YJSH);
				paras.put("一键反馈总数", YJSUM);
				paras.put("不可预告抽查总数", BKSUM);
				paras.put("不可预告抽查派发", BKPF);
				
				paras.put("不可预告抽查已整治", BKZZ);
				paras.put("不可预告抽查未通过审核", BKSHWTG);
				paras.put("不可预告抽查通过审核", BKSH);
				paras.put("发现错误端子数", FXDZ);
				paras.put("检查人员一键改端子数", YJGDZ);
				paras.put("上报整改端子数", SBDZ);
				paras.put("派发整改端子数", PFDZ);
				paras.put("整改回单端子数", HDDZ);
				paras.put("整改成功端子数", SHDZ);
				paras.put("整治派发率按设备", PFLSBWCL);
				paras.put("整治回单率按设备", HDLSBWCL);
				
		paras.put("整治派发率按端子", PFLDZWCL);
		paras.put("整治回单率按端子", HDLDZWCL);
		paras.put("整治完成率按端子", SHLDZWCL);
		paras.put("整治完成率按端子", SHLDZWCL);
		paras.put("2018年整改率", YJGLDZWCL);
		paras.put("CITYNAME", "总计");
		data.add(paras);
		
		String fileName = "整治单各区域统计报告";
		String firstLine = "整治单各区域统计报告";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Map<String, Object> queryByCity(HttpServletRequest request,
			UIPage pager) {
		Map paramMap = new HashMap();
		String staffId = request.getSession().getAttribute("staffId").toString();
		/**
		 * 请求参数
		 */
		
		String area_id = request.getParameter("AREA_ID");//地市
		String son_area = request.getParameter("son_area");//区域
		String completeTime = request.getParameter("COMPLETE_TIME");//上报结束时间
		String startTime = request.getParameter("START_TIME");//上报开始时间
		String pcomplete_time = request.getParameter("PCOMPLETE_TIME");//派发结束时间
		String pstart_time = request.getParameter("PSTART_TIME");//派发开始时间
		String mcomplete_time = request.getParameter("MCOMPLETE_TIME");//回单结束时间
		String mstart_time = request.getParameter("MSTART_TIME");//回单开始时间
		String acomplete_time = request.getParameter("ACOMPLETE_TIME");//审核结束时间
		String astart_time = request.getParameter("ASTART_TIME");//审核开始时间
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
		paramMap.put("ACOMPLETE_TIME", acomplete_time);
		paramMap.put("MSTART_TIME", mstart_time);
		paramMap.put("MCOMPLETE_TIME", mcomplete_time);
		paramMap.put("ASTART_TIME", astart_time);
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
		List<Map<String, Object>> reformOrderReportList = reformOrderReportDao.queryByCity(query);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", reformOrderReportList);
		return resultMap;
	}

	@Override
	public void reformOrderReportDownloadByCity(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "地市 ","周期性检查总数","周期性检查派发",
				"周期性检查已整治","周期性检查未通过审核","周期性检查通过审核", "一键反馈总数","一键反馈派发", "一键反馈已整治 ","一键反馈未通过审核 ","一键反馈通过审核 ","不可预告抽查总数","不可预告抽查派发","不可预告抽查已整治","不可预告抽查未通过审核","不可预告抽查通过审核","发现错误端子数","检查人员一键改端子数","上报整改端子数","派发整改端子数","整改回单端子数","整改成功端子数","整治派发率(按设备)","整治回单率(按设备)","整治完成率(按设备)","整治派发率(按端子)","整治回单率(按端子)","整治完成率(按端子)","2018年整改率"});
		List<String> code = Arrays.asList(new String[] { "NAME","周期性检查总数",
				"周期性检查派发", "周期性检查已整治","周期性检查未通过审核", "周期性检查通过审核", "一键反馈总数","一键反馈派发", "一键反馈已整治","一键反馈未通过审核","一键反馈通过审核","不可预告抽查总数","不可预告抽查派发","不可预告抽查已整治","不可预告抽查未通过审核","不可预告抽查通过审核","发现错误端子数","检查人员一键改端子数","上报整改端子数","派发整改端子数","整改回单端子数","整改成功端子数","整治派发率按设备","整治回单率按设备","整治完成率按设备","整治派发率按端子","整治回单率按端子","整治完成率按端子","2018年整改率"});
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("AREA_ID", para.get("area_id"));
		paras.put("son_area", para.get("SON_AREA_ID"));
		paras.put("START_TIME", para.get("START_TIME"));
		paras.put("COMPLETE_TIME", para.get("COMPLETE_TIME"));
		paras.put("static_month", para.get("static_month"));
		paras.put("PSTART_TIME", para.get("PSTART_TIME"));
		paras.put("PCOMPLETE_TIME", para.get("PCOMPLETE_TIME"));
		paras.put("ASTART_TIME", para.get("ASTART_TIME"));
		paras.put("ACOMPLETE_TIME", para.get("ACOMPLETE_TIME"));
		paras.put("MSTART_TIME", para.get("MSTART_TIME"));
		paras.put("MCOMPLETE_TIME", para.get("MCOMPLETE_TIME"));
		paras.put("WLJB_ID", para.get("WLJB_ID"));
		List<Map<String, Object>> data = reformOrderReportDao.queryDownByCity(paras);
		int ZQSUM=0 ;
		int ZQPF=0 ;
		int ZQZZ=0 ;
		int ZQSHWTG=0 ;
		int ZQSH=0 ;
		int YJSUM=0 ;
		int YJPF=0 ;
		int YJZZ=0 ;
		int YJSHWTG=0 ;
		int YJSH=0 ;
		int BKSUM=0 ;
		int BKPF=0 ;
		int BKZZ=0 ;
		int BKSHWTG=0 ;
		int BKSH=0 ;
		int FXDZ=0;
		int YJGDZ=0;
		int SBDZ=0;
		int PFDZ=0;
		int HDDZ=0;
		int SHDZ=0;
				for (int i = 0; i < data.size(); i++) {
					ZQSUM += Integer.valueOf(data.get(i).get("周期性检查总数").toString());
					ZQPF += Integer.valueOf(data.get(i).get("周期性检查派发").toString());
					ZQZZ += Integer.valueOf(data.get(i).get("周期性检查已整治").toString());
					ZQSHWTG += Integer.valueOf(data.get(i).get("周期性检查未通过审核").toString());
					ZQSH += Integer.valueOf(data.get(i).get("周期性检查通过审核").toString());
					YJPF += Integer.valueOf(data.get(i).get("一键反馈派发").toString());
					YJZZ += Integer.valueOf(data.get(i).get("一键反馈已整治").toString());
					YJSHWTG += Integer.valueOf(data.get(i).get("一键反馈未通过审核").toString());
					YJSH += Integer.valueOf(data.get(i).get("一键反馈通过审核").toString());
					YJSUM += Integer.valueOf(data.get(i).get("一键反馈总数").toString());
					BKSUM += Integer.valueOf(data.get(i).get("不可预告抽查总数").toString());
					BKPF += Integer.valueOf(data.get(i).get("不可预告抽查派发").toString());
					BKZZ += Integer.valueOf(data.get(i).get("不可预告抽查已整治").toString());
					BKSHWTG += Integer.valueOf(data.get(i).get("不可预告抽查未通过审核").toString());
					BKSH += Integer.valueOf(data.get(i).get("不可预告抽查通过审核").toString());
					FXDZ += Integer.valueOf(data.get(i).get("发现错误端子数").toString());
					YJGDZ += Integer.valueOf(data.get(i).get("检查人员一键改端子数").toString());
					SBDZ += Integer.valueOf(data.get(i).get("上报整改端子数").toString());
					PFDZ += Integer.valueOf(data.get(i).get("派发整改端子数").toString());
					HDDZ += Integer.valueOf(data.get(i).get("整改回单端子数").toString());
					SHDZ += Integer.valueOf(data.get(i).get("整改成功端子数").toString());
					
		}       
				float PFLSB;
				float HDLSB;
				float SHLSB;
				float PFLDZ;
				float HDLDZ;
				float SHLDZ;
				float YJGLDZ;
				if(ZQSUM+YJSUM+BKSUM==0){
					 PFLSB= 0;
				}else{
					
					 PFLSB=(float)(ZQPF+YJPF+BKPF)/(ZQSUM+YJSUM+BKSUM)*100;
				}
				
				if(ZQPF+YJPF+BKPF==0){
					 HDLSB= 0;
					 SHLSB= 0 ;
				}else{
					
					 HDLSB=(float)(ZQZZ+YJZZ+BKZZ)/(ZQPF+YJPF+BKPF)*100;
					SHLSB=(float)(ZQSH+YJSH+BKSH)/(ZQPF+YJPF+BKPF)*100;
				}
				
				if(SBDZ==0){
					 PFLDZ= 0;
				}else{
					
					PFLDZ=(float)(PFDZ)/SBDZ*100;
				}
				
				if(PFDZ==0){
					 HDLDZ= 0;
					SHLDZ= 0 ;
				}else{
					
					 HDLDZ=(float)(HDDZ)/PFDZ*100;
					 SHLDZ=(float)(SHDZ)/PFDZ*100;
				}
				if(FXDZ==0){
					YJGLDZ=0;
				}else{
					YJGLDZ=(float)(YJGDZ+SHDZ)/FXDZ*100;
				}
				
				


				DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		        String  f1 = df.format(PFLSB);
		        String  f2 = df.format(HDLSB);
		        String  f3 = df.format(SHLSB);
		        String  f4 = df.format(PFLDZ);
		        String  f5 = df.format(HDLDZ);
		        String  f6 = df.format(SHLDZ);//返回的是String类型的
		        String  f7 = df.format(YJGLDZ);
		        String PFLSBWCL= f1+"%";
		        String HDLSBWCL= f2+"%";
		        String SHLSBWCL= f3+"%";
		        String PFLDZWCL= f4+"%";
		        String HDLDZWCL= f5+"%";
		        String SHLDZWCL= f6+"%";
		        String YJGLDZWCL= f7+"%";
				paras.put("周期性检查总数", ZQSUM);		
				paras.put("周期性检查派发", ZQPF);
				paras.put("周期性检查已整治", ZQZZ);
				paras.put("周期性检查未通过审核", ZQSHWTG);
				paras.put("周期性检查通过审核", ZQSH);
				paras.put("一键反馈派发", YJPF);
				paras.put("一键反馈已整治", YJZZ);
				paras.put("一键反馈未通过审核", YJSHWTG);
				paras.put("一键反馈通过审核", YJSH);
				paras.put("一键反馈总数", YJSUM);
				paras.put("不可预告抽查总数", BKSUM);
				paras.put("不可预告抽查派发", BKPF);
				
				paras.put("不可预告抽查已整治", BKZZ);
				paras.put("不可预告抽查未通过审核", BKSHWTG);
				paras.put("不可预告抽查通过审核", BKSH);
				paras.put("发现错误端子数", FXDZ);
				paras.put("检查人员一键改端子数", YJGDZ);
				paras.put("上报整改端子数", SBDZ);
				paras.put("派发整改端子数", PFDZ);
				paras.put("整改回单端子数", HDDZ);
				paras.put("整改成功端子数", SHDZ);
				paras.put("整治派发率按设备", PFLSBWCL);
				paras.put("整治回单率按设备", HDLSBWCL);
				paras.put("整治完成率按设备", SHLSBWCL);
		paras.put("整治派发率按端子", PFLDZWCL);
		paras.put("整治回单率按端子", HDLDZWCL);
		paras.put("整治完成率按端子", SHLDZWCL);
		paras.put("2018年整改率", YJGLDZWCL);
		paras.put("NAME", "总计");
		data.add(paras);
	   
		String fileName = "整治单地市汇总统计报告";
		String firstLine = "整治单地市汇总统计报告";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> zgEquipmentQuery(HttpServletRequest request,
			UIPage pager) {
		    String type = request.getParameter("type");
			String areaId = request.getParameter("AREAID");//
			String task_start_time = request.getParameter("task_start_time");//任务开始时间
			String task_end_time = request.getParameter("task_end_time");//任务结束时间
			String task_pstart_time = request.getParameter("task_pstart_time");//派发开始时间
			String task_pend_time = request.getParameter("task_pend_time");//派发结束时间
			String task_mstart_time = request.getParameter("task_mstart_time");//回单开始时间
			String task_mend_time = request.getParameter("task_mend_time");//回单结束时间
			String task_astart_time = request.getParameter("task_astart_time");//审核开始时间
			String task_aend_time = request.getParameter("task_aend_time");//审核结束时间
			String WLJB_ID = request.getParameter("WLJB_ID");//网络级别
			
			//判断areaId是地市还是区域
			Map map=reformOrderReportDao.isArea(areaId);
			String area_level=map.get("AREA_LEVEL").toString();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("areaId", areaId);
			paramMap.put("type", type);
			paramMap.put("task_start_time", task_start_time);
			paramMap.put("task_end_time", task_end_time);
			paramMap.put("PSTART_TIME", task_pstart_time);
			paramMap.put("PCOMPLETE_TIME", task_pend_time);
		
			paramMap.put("MSTART_TIME", task_mstart_time);
			paramMap.put("MCOMPLETE_TIME", task_mend_time);
			paramMap.put("ASTART_TIME", task_astart_time);
			paramMap.put("ACOMPLETE_TIME", task_aend_time);
			
			paramMap.put("area_level", area_level);
			paramMap.put("WLJB_ID", WLJB_ID);
		
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> reformOrderReportList =null;
		if("0".equals(type)){		
			reformOrderReportList=reformOrderReportDao.zgEquipmentQuery(query);//周期性检查上报
		}else if("1".equals(type)){			
			reformOrderReportList=reformOrderReportDao.zgPfEquipmentQuery(query);//周期性检查上报派发		
		}else if("2".equals(type)){			
			reformOrderReportList=reformOrderReportDao.zgHdEquipmentQuery(query);//周期性检查上报回单			
		}else if("3".equals(type)){								
			reformOrderReportList=reformOrderReportDao.zgShEquipmentQuery(query);//周期性检查上报审核		
		}else if("4".equals(type)){
			reformOrderReportList=reformOrderReportDao.yjEquipmentQuery(query);//一键反馈检查上报
		}else if("5".equals(type)){
			reformOrderReportList=reformOrderReportDao.yjPfEquipmentQuery(query);//一键反馈检查上报派发
		}else if("6".equals(type)){
			reformOrderReportList=reformOrderReportDao.yjHdEquipmentQuery(query);//一键反馈检查上报回单
		}else if("7".equals(type)){
			reformOrderReportList=reformOrderReportDao.yjShEquipmentQuery(query);//一键反馈检查上报审核
		}else if("8".equals(type)){
			reformOrderReportList=reformOrderReportDao.bygEquipmentQuery(query);//不预告检查上报
		}else if("9".equals(type)){
			reformOrderReportList=reformOrderReportDao.bygPfEquipmentQuery(query);//不预告检查上报派发
		}else if("10".equals(type)){
			reformOrderReportList=reformOrderReportDao.bygHdEquipmentQuery(query);//不预告检查上报回单
		}else if("11".equals(type)){
			reformOrderReportList=reformOrderReportDao.bygShEquipmentQuery(query);//不预告检查上报审核
		}else if("16".equals(type)){
			reformOrderReportList=reformOrderReportDao.zgShAgainstEquipmentQuery(query);//周期新检查上报审核不通过
		}else if("17".equals(type)){
			reformOrderReportList=reformOrderReportDao.yjShAgainstEquipmentQuery(query);//一键反馈上报审核不通过
		}else if("18".equals(type)){
			reformOrderReportList=reformOrderReportDao.bygShAgainstEquipmentQuery(query);//不预告检查上报审核不通过
		}
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", reformOrderReportList);
		return resultMap;
	}

	@Override
	public void equipmentDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] {  "区域 ","设备名称","设备编码 ","设备地址","设备类型" ,"管理区域名称 "});
		List<String> code = Arrays.asList(new String[] { "NAME", "EQUIPMENT_NAME" ,"EQUIPMENT_CODE","ADDRESS","RES_TYPE","MANAGE_AREA"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String type = para.get("type").toString();
		String areaId = para.get("areaId").toString();		
		//判断areaId是地市还是区域
		Map map=reformOrderReportDao.isArea(areaId);
		String area_level=map.get("AREA_LEVEL").toString();
		paras.put("areaId", areaId);
		paras.put("task_start_time", para.get("task_start_time"));
		paras.put("task_end_time", para.get("task_end_time"));
		String task_pstart_time=(String) para.get("task_pstart_time");
		String task_pend_time=(String) para.get("task_pend_time");
		String task_mstart_time=(String) para.get("task_mstart_time");
		String task_mend_time=(String) para.get("task_mend_time");
		String task_astart_time=(String) para.get("task_astart_time");
		String task_aend_time=(String) para.get("task_aend_time");
		if("undefined".equals(task_pstart_time)){
			task_pstart_time="";
		}
		if("undefined".equals(task_pend_time)){
			task_pend_time="";
		}
		if("undefined".equals(task_mstart_time)){
			task_mstart_time="";
		}
		if("undefined".equals(task_mend_time)){
			task_mend_time="";
		}
		if("undefined".equals(task_astart_time)){
			task_astart_time="";
		}
		if("undefined".equals(task_aend_time)){
			task_aend_time="";
		}		
		paras.put("PSTART_TIME",task_pstart_time );
		paras.put("PCOMPLETE_TIME",task_pend_time );
		paras.put("MSTART_TIME",task_mstart_time );
		paras.put("MCOMPLETE_TIME",task_mend_time );
		paras.put("ASTART_TIME",task_astart_time );
		paras.put("ACOMPLETE_TIME",task_aend_time );
		paras.put("WLJB_ID", para.get("WLJB_ID"));
		paras.put("area_level", area_level);

		List<Map<String, Object>> data = null;
		
		if("0".equals(type)){
			data=reformOrderReportDao.exportZgEquipmentQuery(paras);//周期性检查上报
		}else if("1".equals(type)){
			data=reformOrderReportDao.exportZgPfEquipmentQuery(paras);//周期性检查上报派发
		}else if("2".equals(type)){
			data=reformOrderReportDao.exportZgHdEquipmentQuery(paras);//周期性检查上报回单
		}else if("3".equals(type)){
			data=reformOrderReportDao.exportZgShEquipmentQuery(paras);//周期性检查上报审核
		}else if("4".equals(type)){
			data=reformOrderReportDao.exportYjEquipmentQuery(paras);//一键反馈检查上报
		}else if("5".equals(type)){
			data=reformOrderReportDao.exportYjPfEquipmentQuery(paras);//一键反馈检查上报派发
		}else if("6".equals(type)){
			data=reformOrderReportDao.exportYjHdEquipmentQuery(paras);//一键反馈检查上报回单
		}else if("7".equals(type)){
			data=reformOrderReportDao.exportYjShEquipmentQuery(paras);//一键反馈检查上报审核
		}else if("8".equals(type)){
			data=reformOrderReportDao.exportBygEquipmentQuery(paras);//不预告检查上报
		}else if("9".equals(type)){
			data=reformOrderReportDao.exportBygPfEquipmentQuery(paras);//不预告检查上报派发
		}else if("10".equals(type)){
			data=reformOrderReportDao.exportBygHdEquipmentQuery(paras);//不预告检查上报回单
		}else if("11".equals(type)){
			data=reformOrderReportDao.exportBygShEquipmentQuery(paras);//不预告检查上报审核
		}else if("16".equals(type)){
			data=reformOrderReportDao.exportZgShAgainstEquipmentQuery(paras);//周期性检查上报审核不通过
		}else if("17".equals(type)){
			data=reformOrderReportDao.exportYjShAgainstEquipmentQuery(paras);//一键反馈检查上报审核不通过
		}else if("18".equals(type)){
			data=reformOrderReportDao.exportBygShAgainstEquipmentQuery(paras);//不预告检查上报审核不通过
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
	public Map<String, Object> portQuery(HttpServletRequest request,
			UIPage pager) {
		 String type = request.getParameter("type");
		String areaId = request.getParameter("AREAID");//
		String task_start_time = request.getParameter("task_start_time");//任务开始时间
		String task_end_time = request.getParameter("task_end_time");//任务结束时间
		String task_pstart_time = request.getParameter("task_pstart_time");//派发开始时间
		String task_pend_time = request.getParameter("task_pend_time");//派发结束时间
		String task_mstart_time = request.getParameter("task_mstart_time");//回单开始时间
		String task_mend_time = request.getParameter("task_mend_time");//回单结束时间
		String task_astart_time = request.getParameter("task_astart_time");//审核开始时间
		String task_aend_time = request.getParameter("task_aend_time");//审核结束时间
		String WLJB_ID = request.getParameter("WLJB_ID");//审核结束时间
		
		//判断areaId是地市还是区域
		Map map=reformOrderReportDao.isArea(areaId);
		String area_level=map.get("AREA_LEVEL").toString();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("areaId", areaId);
		paramMap.put("type", type);
		paramMap.put("task_start_time", task_start_time);
		paramMap.put("task_end_time", task_end_time);
		paramMap.put("PSTART_TIME", task_pstart_time);
		paramMap.put("PCOMPLETE_TIME", task_pend_time);
	
		paramMap.put("MSTART_TIME", task_mstart_time);
		paramMap.put("MCOMPLETE_TIME", task_mend_time);
		paramMap.put("ASTART_TIME", task_astart_time);
		paramMap.put("ACOMPLETE_TIME", task_aend_time);
		paramMap.put("WLJB_ID", WLJB_ID);
		
		paramMap.put("area_level", area_level);
			
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> reformOrderReportList =null;
		if("12".equals(type)){		
			reformOrderReportList=reformOrderReportDao.zgEquipmentPortQuery(query);//上报整改端子数
		}else if("13".equals(type)){			
			reformOrderReportList=reformOrderReportDao.zgPfEquipmentPortQuery(query);//派发整改端子数 		
		}else if("14".equals(type)){			
			reformOrderReportList=reformOrderReportDao.zgHdEquipmentPortQuery(query);//整改回单端子数 		
		}else if("15".equals(type)){								
			reformOrderReportList=reformOrderReportDao.zgCgEquipmentPortQuery(query);//整改成功端子数		
		}else if("22".equals(type)){								
			reformOrderReportList=reformOrderReportDao.zgFxEquipmentPortQuery(query);//发现错误端子数		
		}else if("23".equals(type)){								
			reformOrderReportList=reformOrderReportDao.zgYjgEquipmentPortQuery(query);//一键改端子数		
		}

		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", reformOrderReportList);
		return resultMap;
	}

	@Override
	public void portDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] {  "区域 ","设备ID","设备编码 ","设备名称","端子ID" ,"端子编码 ","是否整改"});
		List<String> code = Arrays.asList(new String[] { "NAME", "EQP_ID" ,"EQP_NO","EQP_NAME","PORT_ID","PORT_NO","ISCHECKOK"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String type = para.get("type").toString();
		String areaId = para.get("areaId").toString();
		//判断areaId是地市还是区域
		Map map=reformOrderReportDao.isArea(areaId);
		String area_level=map.get("AREA_LEVEL").toString();
		paras.put("areaId", areaId);
		paras.put("task_start_time", para.get("task_start_time"));
		paras.put("task_end_time", para.get("task_end_time"));
		String task_pstart_time=(String) para.get("task_pstart_time");
		String task_pend_time=(String) para.get("task_pend_time");
		String task_mstart_time=(String) para.get("task_mstart_time");
		String task_mend_time=(String) para.get("task_mend_time");
		String task_astart_time=(String) para.get("task_astart_time");
		String task_aend_time=(String) para.get("task_aend_time");
		if("undefined".equals(task_pstart_time)){
			task_pstart_time="";
		}
		if("undefined".equals(task_pend_time)){
			task_pend_time="";
		}
		if("undefined".equals(task_mstart_time)){
			task_mstart_time="";
		}
		if("undefined".equals(task_mend_time)){
			task_mend_time="";
		}
		if("undefined".equals(task_astart_time)){
			task_astart_time="";
		}
		if("undefined".equals(task_aend_time)){
			task_aend_time="";
		}		
		paras.put("PSTART_TIME",task_pstart_time );
		paras.put("PCOMPLETE_TIME",task_pend_time );
		paras.put("MSTART_TIME",task_mstart_time );
		paras.put("MCOMPLETE_TIME",task_mend_time );
		paras.put("ASTART_TIME",task_astart_time );
		paras.put("ACOMPLETE_TIME",task_aend_time );
		paras.put("WLJB_ID", para.get("WLJB_ID"));
		paras.put("area_level", area_level);

		List<Map<String, Object>> data = null;
		
		if("12".equals(type)){
			data=reformOrderReportDao.exportZgEquipmentPortQuery(paras);//上报整改端子数
		}else if("13".equals(type)){
			data=reformOrderReportDao.exportZgPfEquipmentPortQuery(paras);//派发整改端子数 	
		}else if("14".equals(type)){
			data=reformOrderReportDao.exportZgHdEquipmentPortQuery(paras);//整改回单端子数 
		}else if("15".equals(type)){
			data=reformOrderReportDao.exportZgCgEquipmentPortQuery(paras);//整改成功端子数
		}else if("22".equals(type)){
			data=reformOrderReportDao.exportZgFxEquipmentPortQuery(paras);//发现错误端子数
		}else if("23".equals(type)){
			data=reformOrderReportDao.exportZgYjgEquipmentPortQuery(paras);//一键改端子数
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
}
