package com.cableCheck.serviceimpl;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CCStatictisDao;
import com.cableCheck.dao.QuartzJobDao;
import com.cableCheck.dao.TeamCheckStatictisDao;
import com.cableCheck.service.CCStatictisService;
import com.cableCheck.service.TeamCheckStatictisService;

import util.page.Query;
import util.page.UIPage;

@SuppressWarnings("all")
@Transactional
@Service
public class TeamCheckStatictisServiceImpl implements TeamCheckStatictisService {

	@Resource
	private TeamCheckStatictisDao teamCheckStatictisDao;
	
	@Resource
	private QuartzJobDao quartzJobDao;
	
	@Override
	public Map<String, Object> statictis(HttpServletRequest request, UIPage pager) {
		
		//在原来的统计逻辑前面增加一个判断计算，
		//判断如果页面的结束时间选择了今天，则先执行存储过程，将今天的结果计算出来，再统计
		calNowDayOrder(request,-1);
		
		Query query = comParam(request, pager);
		List<Map<String, Object>> olists = teamCheckStatictisDao.statictis(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	private Query comParam(HttpServletRequest request, UIPage pager) {
		Map<String, String> map = new HashMap<String, String>();
		String areaId = request.getParameter("areaId");// 地市id
		String sonAreaId = request.getParameter("sonAreaId");// 区县id
		map.put("area_id", areaId);
		map.put("son_area_id", sonAreaId);
		
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		//计算已整改的数，将计算时间往后推7天
		map.put("endDate_expand", getExpandDay((endDate+" 23:59:59"),7));
		
		
		String rate_start = request.getParameter("rate_start");// 准确率起始
		String rate_end = request.getParameter("rate_end");// 准确率终止
		map.put("rate_start", rate_start);
		map.put("rate_end", rate_end);

		String team_name = request.getParameter("team_name");// 班组名称
		map.put("team_name", team_name);
		
		String company = request.getParameter("company");// 代维公司名称
		map.put("company", company);
		
		String action_type = request.getParameter("action_type");// 工单性质
		map.put("action_type", action_type);
		
		String mark = request.getParameter("mark");//工单来源 
		map.put("mark", mark);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
    
    /**
     * 判断计算今天的订单数
     * @param request
     */
    public void calNowDayOrder(HttpServletRequest request,int num){
    	String date = request.getParameter("endDate");
		if(isNowDay(date)){
			calDayOrder(0);
		}
    }
    
    
    /**
     * 判断传递的参数是否是今天
     * @param date
     * @return
     */
    public Boolean isNowDay(String date){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	if(format.format(new Date()).equals(date)){
    		return true; 
    	}
    	return false;
    }
    
    /**
     * 计算传递进来的时间的订单数量
     * @param date
     */
    public void calDayOrder(int num){
    	Map map =new HashMap();
    	map.put("daynum",num);
		try {
			//执行计算今天的班组检查整改统计
			quartzJobDao.calTeamCheck(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * 根据strDate自动获取num天之后的时间
     * @param strDate
     * @param num
     * @return
     */
    public String getExpandDay(String strDate,int num){
    	String str = strDate;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DAY_OF_MONTH, num);
		return sdf.format(cal.getTime());
    }
    
}
