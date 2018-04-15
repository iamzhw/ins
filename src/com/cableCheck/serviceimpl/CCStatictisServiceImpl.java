package com.cableCheck.serviceimpl;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
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
import com.cableCheck.service.CCStatictisService;

import util.page.Query;
import util.page.UIPage;

@SuppressWarnings("all")
@Transactional
@Service
public class CCStatictisServiceImpl implements CCStatictisService {

	@Resource
	private CCStatictisDao ccStatictisDao;
	
	@Resource
	private QuartzJobDao quartzJobDao;

	@Override
	public Map<String, Object> statictis(HttpServletRequest request, UIPage pager) {
		
		//在原来的统计逻辑前面增加一个判断计算，
		//判断如果页面的结束时间选择了今天，则先执行存储过程，将今天的结果计算出来，再统计
		calNowDayOrder(request,-1);
		
		Query query = comParam(request, pager);
		List<Map<String, Object>> olists = ccStatictisDao.statictis(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		
		Map map = query.getQueryParams();
		//计算出总共的检查数和工单数
		List<Map<String, Object>> olistsAll = ccStatictisDao.sumTotalCount(map);
		List<Map<String, Object>> olistsZcou = ccStatictisDao.sumZCount(map);
		pmap.put("totalCount", olistsAll);
		pmap.put("ZCount", olistsZcou);
		
		return pmap;
	}

	private Query comParam(HttpServletRequest request, UIPage pager) {
		Map<String, String> map = new HashMap<String, String>();
		String areaId = request.getParameter("areaId");// 地市id
		String sonAreaId = request.getParameter("sonAreaId");// 区县id
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		
		String checkStartDate = request.getParameter("checkStartTime");// 开始时间
		String checkEndDate = request.getParameter("checkEndTime");// 结束时间
		map.put("checkStartDate", checkStartDate+" 00:00:00");
		map.put("checkEndDate", checkEndDate+" 23:59:59");
		
		map.put("startDate", startDate+" 00:00:00");
		map.put("endDate", endDate+" 23:59:59");
		map.put("area_id", areaId);
		map.put("son_area_id", sonAreaId);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
	
	
	
	private Boolean isToday(HttpServletRequest request){
		Boolean bool = false;
		String endDate = request.getParameter("endDate");// 结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String now = sdf.format(new Date());
		if(endDate.equals(now)){
			bool = true;
		}
		return bool;
	}
	
	
	/**
	 * 判断页面的查询条件是否选择了今天，
	 * 查询当天的统计结果，将开始时间换成结束时间
	 * @param request
	 * @param pager
	 * @return
	 */
	private Query comParamToday(HttpServletRequest request, UIPage pager){
		Map<String, String> map = new HashMap<String, String>();
		String areaId = request.getParameter("areaId");// 地市id
		String sonAreaId = request.getParameter("sonAreaId");// 区县id
		String startDate = request.getParameter("endDate");// 开始时间 == 结束时间
		String endDate = request.getParameter("endDate");// 结束时间
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("area_id", areaId);
		map.put("son_area_id", sonAreaId);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
	

	@Override
	public void exportExcelDownload(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ", "班组", "现场检查工单数","总工单数","检查占比(%)" });
		List<String> code = Arrays
				.asList(new String[] { "AREANAME", "SONAREANAME", "TEAM_NAME", "ZCOU", "TOTALCOUNT","RATE" });
		Map<String, Object> paras = new HashMap<String, Object>();
		Query query = comParam(request, pager);

		List<Map<String, Object>> data = ccStatictisDao.statictis(query);
		
		Map map_param = query.getQueryParams();
		//计算出总共的检查数和工单数
		List<Map<String, Object>> olistsAll = ccStatictisDao.sumTotalCount(map_param);
		List<Map<String, Object>> olistsZcou = ccStatictisDao.sumZCount(map_param);
		Map<String, Object> map_one = olistsAll.get(0);
		Map<String, Object> map_two = olistsZcou.get(0);
		
		//将新增的总计插入到data中
		Map<String, Object> map_add = new HashMap<String, Object>();
		map_add.put("AREANAME", "总计");
		map_add.put("SONAREANAME", "");
		map_add.put("TEAM_NAME", "");
		map_add.put("ZCOU", map_two.get("ZCOU"));
		map_add.put("TOTALCOUNT", map_one.get("TOTALCOUNT"));
		map_add.put("RATE", "0");
		data.add(map_add);
		
		//对统计出来的数据进行遍历，将rate四舍五入，保留两位小数，并且将rate为空，null的情况全部置为0
		for (Map<String, Object> map : data) {
			Double ZCOU = Double.valueOf(map.get("ZCOU").toString());
			Double TOTALCOUNT = Double.valueOf(map.get("TOTALCOUNT").toString());
			Double rate = ZCOU/TOTALCOUNT;
			String result = "0";
			double d = rate*100;
			result = String.format("%.2f", d);
			map.put("RATE", result);
		}
		
		String fileName = "现场检查工单占比";
		String firstLine = "现场检查工单占比";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request, response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> showStatictisOrder(HttpServletRequest request, UIPage pager) {
		Query query = comParamByOrder(request, pager);
		List<Map<String, Object>> olists = ccStatictisDao.showStatictisOrder(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	private Query comParamByOrder(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String sonAreaId = request.getParameter("sonAreaId");// 区县id
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		// type类型，1：是现场检查数;2.装工单；3.拆工单；4.移工单
		String type = request.getParameter("type");
		//map.put("type", type);
		map.put("son_area_id", sonAreaId);
		map.put("startDate", (startDate + " 00:00:00"));
		map.put("endDate", (endDate + " 23:59:59"));
		if("1".equals(type)){
			map.put("type", type);	
		}else if("2".equals(type) || "3".equals(type)){
			map.put("order_type", request.getParameter("order_type"));
		}else if("4".equals(type) || "5".equals(type)){
			map.put("is_move_order", request.getParameter("order_type"));//表示是移动工单
		}
		String team_id = request.getParameter("team_id");
		map.put("team_id", Integer.valueOf(team_id));
		
		String checkStartDate = request.getParameter("checkStartDate");// 开始时间
		String checkEndDate = request.getParameter("checkEndDate");// 结束时间
		map.put("checkStartDate", checkStartDate);
		map.put("checkEndDate", checkEndDate);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
	
	
	@Override
	public void exportExcelDetail(Map<String, Object> para, HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","班组", "工单编号", "设备编号", "端子编号 " });
		List<String> code = Arrays
				.asList(new String[] { "AREANAME", "SONAREANAME", "TEAMNAME", "ORDER_NO", "OPT_CODE", "PHY_PORT_SPEC_NO"});
		Map<String, Object> paras = new HashMap<String, Object>();
		Query query = comParamByOrder(request, pager);

		List<Map<String, Object>> olists = ccStatictisDao.showStatictisOrder(query);
		
		// type类型，1：是现场检查数;2.装工单；3.拆工单；4.移工单
		String type = request.getParameter("type");
		String fileName = "现场检查工单清单";
		if("1".equals(type)){
			//
		}else if("2".equals(type)){
			fileName="装工单清单";
		}else if("3".equals(type)){
			fileName="拆工单清单";
		}else if("4".equals(type)){
			fileName="移工单清单";
		}
		String firstLine = fileName;

		try {
			ExcelUtil.generateExcelAndDownload(title, code, olists, request, response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 判断是否是今天
	 * 再进行下面的操作
	 */
	public void isExe(HttpServletRequest request, UIPage pager,List<Map<String, Object>> olists){
		if(isToday(request)){
			List<Map<String, Object>> olistsToday = ccStatictisDao.statictis(comParamToday(request, pager));
			for (int i = 0; i < olistsToday.size(); i++) {
				addListTo(olists, olistsToday.get(i), "teamId");
			}
		}
	}
	
	
	/**
	 * 将两个集合中的数据合并，如果key相同，则将value相加
	 * @param target
	 * @param plus
	 * @return
	 */
	public static void addTo(Map<String,Object> target, Map<String,Object>plus,String keyCompare) {
        Object[] os = plus.keySet().toArray();
        String key = "";
        for (int i=0; i<os.length; i++) {
            key = os[i].toString();
            if(!key.equals(keyCompare)){
            	if (target.containsKey(key)) 
                    target.put(key, (Integer)target.get(key) + (Integer)plus.get(key));
                else
                    target.put(key, plus.get(key));	
            }else{
            	//key与keyCompare相同，则不管
            }
        }
    }
    
	/**
	 * 根据list和map进行判断
	 * @param list
	 * @param map
	 * @param key
	 */
    public static void addListTo(List<Map<String, Object>> list,Map<String, Object> map,String key){
    	Object o = map.get(key);
    	int size  = list.size();
    	Boolean bool = true;
    	if(o!=null && StringUtils.isNotBlank(o.toString())){
    		for (int i = 0; i < size; i++) {
    			Map mapList = list.get(i);
				if(mapList.get(key).toString() == o.toString()){
					bool = false;
					addTo(mapList, map, key);
					break;
				}
			}
    		//如果list中的map集合与传递进来的map没有相同的，则直接将map添加到list集合中
    		if(bool){
    			list.add(map);
    		}
    	}else{
    		System.out.println("该map集合没有  "+key+" 的对应值");
    	}
    }
    
    
    /**
     * 判断计算今天的订单数
     * @param request
     */
    public void calNowDayOrder(HttpServletRequest request,int num){
    	String date = request.getParameter("endDate");
		if(isNowDay(date)){
			calDayOrder(date,num);
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
    public void calDayOrder(String date,int num){
    	Map map =new HashMap();
    	map.put("stateTime",date);
		try {
			if(num == 0){
				quartzJobDao.calOrderNum(map);
			}else if(num==1){
				quartzJobDao.calOrderChange(map);
			}else if(num == 2){
				quartzJobDao.calCheckError(map);
			}else if(num == 3){
				quartzJobDao.calTeamOrder(map);
			}else if(num == 4){
				quartzJobDao.calGridOrder(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    
    
    private Query comParamChange(HttpServletRequest request, UIPage pager) {
		Map<String, String> map = new HashMap<String, String>();
		String areaId = request.getParameter("areaId");// 地市id
		String sonAreaId = request.getParameter("sonAreaId");// 区县id
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		
		String checkStartDate = request.getParameter("checkStartTime");// 开始时间
		String checkEndDate = request.getParameter("checkEndTime");// 结束时间
		map.put("checkStartDate", checkStartDate);
		map.put("checkEndDate", checkEndDate);
		
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("area_id", areaId);
		map.put("son_area_id", sonAreaId);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
    
    
    public Map<String, Object> orderChangeAll(HttpServletRequest request, UIPage pager){
    	//在原来的统计逻辑前面增加一个判断计算，
		//判断如果页面的结束时间选择了今天，则先执行存储过程，将今天的结果计算出来，再统计
		calNowDayOrder(request,1);
		
		Query query = comParamChange(request, pager);
		List<Map<String, Object>> olists = ccStatictisDao.orderChangeStatictis(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
    }
    
    public Map<String, Object> checkErrorAll(HttpServletRequest request, UIPage pager){
    	//在原来的统计逻辑前面增加一个判断计算，
		//判断如果页面的结束时间选择了今天，则先执行存储过程，将今天的结果计算出来，再统计
		calNowDayOrder(request,2);
		
		Query query = comParamError(request, pager);
		List<Map<String, Object>> olists = ccStatictisDao.checkErrorStatictis(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
    }
    
    private Query comParamError(HttpServletRequest request, UIPage pager) {
		Map<String, String> map = new HashMap<String, String>();
		String areaId = request.getParameter("areaId");// 地市id
		String sonAreaId = request.getParameter("sonAreaId");// 区县id
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String staffName = request.getParameter("staffName");// 结束时间
		String staffId = request.getParameter("staffId");// 人员id
		String rowType = request.getParameter("rowType");// rowType
		
		String teamName = request.getParameter("teamName");
		String companyName = request.getParameter("companyName");
		map.put("teamName", teamName);
		map.put("companyName", companyName);
		
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("area_id", areaId);
		map.put("son_area_id", sonAreaId);
		map.put("staffName", staffName);
		map.put("rowType", rowType);
		map.put("staffId", staffId);
		map.put("row_name", getErrorName(rowType));
		
		map.put("ERROR_ONE", "光设施无标签或标签手写");
		map.put("ERROR_TWO", "子框无标签或标签手写");
		map.put("ERROR_THREE", "光路无标签或标签手写");
		map.put("ERROR_FOUR", "[新增]现场与系统标签不一致");
		map.put("ERROR_FIVE", "[存量]现场与系统标签不一致");
		map.put("ERROR_SIX", "现场有跳纤，系统无光路");
		map.put("ERROR_SEVEN", "现场无跳纤，系统有光路");
		map.put("ERROR_EIGHT", "检查人员已将现场问题整改");
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
    
    public String getErrorName(String type){
    	String str = "光设施无标签或标签手写";
    	if(StringUtils.isNotBlank(type)){
    		if("ERROR_ONE".equals(type)){
    			str = "光设施无标签或标签手写";
    		}else if("ERROR_TWO".equals(type)){
    			str = "子框无标签或标签手写";
    		}else if("ERROR_THREE".equals(type)){
    			str = "光路无标签或标签手写";
    		}else if("ERROR_FOUR".equals(type)){
    			str = "[新增]现场与系统标签不一致";
    		}else if("ERROR_FIVE".equals(type)){
    			str = "[存量]现场与系统标签不一致";
    		}else if("ERROR_SIX".equals(type)){
    			str = "现场有跳纤，系统无光路";
    		}else if("ERROR_SEVEN".equals(type)){
    			str = "现场无跳纤，系统有光路";
    		}else if("ERROR_EIGHT".equals(type)){
    			str = "检查人员已将现场问题整改";
    		}else if("ERROR_NINE".equals(type)){
    			str = "其他";
    		}	
    	}
    	return str;
    } 
    
    
	@Override
	public Map<String, Object> showCheckErrorOrder(HttpServletRequest request, UIPage pager) {
		Query query = comParamErrorForOrder(request, pager);
		
		String rowType = request.getParameter("rowType");// rowType
		List<Map<String, Object>> olists = null;
		if("ERROR_NINE".equals(rowType)){
			olists = ccStatictisDao.showElseErrorOrder(query);
		}else{
			olists = ccStatictisDao.showCheckErrorOrder(query);
		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	 private Query comParamErrorForOrder(HttpServletRequest request, UIPage pager) {
			Map<String, String> map = new HashMap<String, String>();
			String areaId = request.getParameter("areaId");// 地市id
			String sonAreaId = request.getParameter("sonAreaId");// 区县id
			String startDate = request.getParameter("startDate");// 开始时间
			String endDate = request.getParameter("endDate");// 结束时间
			String staffName = request.getParameter("staffName");// 结束时间
			String staffId = request.getParameter("staffId");// 人员id
			String rowType = request.getParameter("rowType");// rowType
			
			String teamName = request.getParameter("teamName");
			String companyName = request.getParameter("companyName");
			map.put("teamName", teamName);
			map.put("companyName", companyName);
			
			map.put("startDate", startDate+" 00:00:00");
			map.put("endDate", endDate+" 23:59:59");
			map.put("area_id", areaId);
			map.put("son_area_id", sonAreaId);
			map.put("staffName", staffName);
			map.put("rowType", rowType);
			map.put("staffId", staffId);
			map.put("row_name", getErrorName(rowType));
			
			map.put("ERROR_ONE", "光设施无标签或标签手写");
			map.put("ERROR_TWO", "子框无标签或标签手写");
			map.put("ERROR_THREE", "光路无标签或标签手写");
			map.put("ERROR_FOUR", "[新增]现场与系统标签不一致");
			map.put("ERROR_FIVE", "[存量]现场与系统标签不一致");
			map.put("ERROR_SIX", "现场有跳纤，系统无光路");
			map.put("ERROR_SEVEN", "现场无跳纤，系统有光路");
			map.put("ERROR_EIGHT", "检查人员已将现场问题整改");
			
			Query query = new Query();
			query.setPager(pager);
			query.setQueryParams(map);
			return query;
		}
    

	@Override
	public Map<String, Object> teamOrder(HttpServletRequest request, UIPage pager) {
		//在原来的统计逻辑前面增加一个判断计算，
		//判断如果页面的结束时间选择了今天，则先执行存储过程，将今天的结果计算出来，再统计
		calNowDayOrder(request,3);
		
		Query query = comParamTeamOrder(request, pager);
		List<Map<String, Object>> olists = ccStatictisDao.teamOrderStatictis(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	private Query comParamTeamOrder(HttpServletRequest request, UIPage pager) {
		Map<String, String> map = new HashMap<String, String>();
		String areaId = request.getParameter("areaId");// 地市id
		String sonAreaId = request.getParameter("sonAreaId");// 区县id
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String teamName = request.getParameter("teamName");// 班组名称
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("area_id", areaId);
		map.put("son_area_id", sonAreaId);
		map.put("teamName", teamName);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		return query;
	}
    
	
	@Override
	public Map<String, Object> gridOrder(HttpServletRequest request, UIPage pager) {
		//在原来的统计逻辑前面增加一个判断计算，
		//判断如果页面的结束时间选择了今天，则先执行存储过程，将今天的结果计算出来，再统计
		calNowDayOrder(request,4);
		
		Query query = comParamTeamOrder(request, pager);
		List<Map<String, Object>> olists = ccStatictisDao.gridOrderStatictis(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
}
