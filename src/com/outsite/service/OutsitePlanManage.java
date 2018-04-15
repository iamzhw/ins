package com.outsite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.util.StringUtil;
import com.util.DateUtil;
import com.util.MapDistance;

@Service
@SuppressWarnings("all")
public class OutsitePlanManage implements IOutsitePlanManageService {
    @Autowired
    com.outsite.dao.IOutsitePlanManageDao outsitePlanManageDao;

	/** 外力点看护计划--查询计划列表 **/
    @Override
    public Map<String, Object> query_outsite(HttpServletRequest request, UIPage pager) {
		String start_date = request.getParameter("param_start_date");// 计划开始时间
		String end_date = request.getParameter("param_end_date");// 计划结束时间
	String site_name = request.getParameter("site_name");
	String guard_name = request.getParameter("guard_name");
	String guard_no = request.getParameter("guard_no");
	Map params = new HashMap();
	params.put("site_name", site_name);
	params.put("guard_name", guard_name);
	params.put("guard_no", guard_no);
	params.put("start_date", start_date);
	params.put("end_date", end_date);
		params.put("area_id", request.getSession().getAttribute("areaId"));// 区域ID
	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(params);
	List<Map<String, Object>> list = outsitePlanManageDao.query_outsite(query);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", list);
	map.put("total", query.getPager().getRowcount());
	return map;
    }

	/** 外力点看护计划--查询计划列表--详情列表 **/
    @Override
    public Map<String, Object> query_detail_plan(HttpServletRequest request, UIPage pager) {
	Map<String, Object> params = new HashMap<String, Object>();
		params.put("plan_id", request.getParameter("plan_id"));// 计划ID
	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(params);
	List<Map<String, Object>> list = outsitePlanManageDao.query_detail_plan(query);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", list);
	map.put("total", query.getPager().getRowcount());
	return map;
    }

	// 选择外力点列表
    @Override
    public List<Map<String, Object>> getout_site(Map<String, Object> para) {

	// TODO Auto-generated method stub
	return outsitePlanManageDao.getout_site(para);
    }

	// 选择看护人员列表
    @Override
    public List<Map<String, Object>> getkan_name(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.getkan_name(para);
    }

	// 查看看护时间
    @Override
    public List<Map<String, Object>> look_time(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.look_time(para);
    }

	// 巡线时长
    @Override
    public List<Map<String, Object>> test(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.test(para);
    }

	// 巡线时长
    @Override
    public List<Map<String, Object>> test_con(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.test_con(para);
    }

	// 巡线时长
    @Override
    public List<Map<String, Object>> get_alllist(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.get_alllist(para);
    }

	// 巡线时长
    @Override
    public String get_max_date(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.get_max_date(para);
    }

	// 巡线时长
    @Override
    public List<Map<String, Object>> getflag(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.getflag(para);
    }

	// 巡线时长
    @Override
    public List<Map<String, Object>> get_para_time(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.get_para_time(para);
    }

	// 巡线时长
    @Override
    public List<Map<String, Object>> get_all_people(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.get_all_people(para);
    }

	// 巡线时长
    @Override
    public List<Map<String, Object>> get_no_gps(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.get_no_gps(para);
    }

	// 巡线时长
    @Override
    public List<Map<String, Object>> get_judge_time(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.get_judge_time(para);
    }

	// 未匹配
    @Override
    public List<Map<String, Object>> get_nomatch(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.get_nomatch(para);
    }

	// 在非外力施工（隐患）点的巡检点连续停留超过40分钟；
    @Override
    public List<Map<String, Object>> get_forty_time(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.get_forty_time(para);
    }

	// 选择监管人员列表
    @Override
    public List<Map<String, Object>> getjianguan_name(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.getjianguan_name(para);
    }

	// 选择巡线人员列表
    @Override
    public List<Map<String, Object>> getxunxian_data(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.getxunxian_data(para);
    }
    
	// 选择看护时间列表
    @Override
    public List<Map<String, Object>> getPartTimeList(Map<String, Object> para) {
	// TODO Auto-generated method stub
	return outsitePlanManageDao.getPartTimeList(para);
    }

	// 外力点计划新增
    @Override
    public void insert_outsite_plan(HttpServletRequest request) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("out_site_id", request.getParameter("out_site_id"));// 计划名称
		params.put("outsite_name", request.getParameter("outsite_name"));// 计划名称
		params.put("start_date", request.getParameter("start_date"));// 计划结束时间
		params.put("end_date", request.getParameter("end_date"));
		params.put("kan_name_id", request.getParameter("kan_name_id"));
		params.put("kan_name", request.getParameter("kan_name"));
		params.put("creation_time", request.getParameter("creation_time"));
		params.put("creator", request.getParameter("creator"));
		params.put("update_time", request.getParameter("creation_time"));
		params.put("update_name", request.getParameter("creator"));// 创建者
		//params.put("jianguan_name_id", request.getParameter("jianguan_name_id"));
		//params.put("jianguan_name", request.getParameter("jianguan_name"));
		//params.put("plan_create_date", request.getParameter("plan_create_date"));
		// params.put("jianguan_name_id", DateUtil.getDate());
		// params.put("kanhu_shijian_id",
		// request.getParameter("kanhu_shijian_id"));
	
		params.put("area_id", request.getSession().getAttribute("areaId"));// 区域ID
		params.put("plan_id", outsitePlanManageDao.getOutsitPlanId());
		
		// 外力点新增
		outsitePlanManageDao.insert_outsite_plan(params);
	
		params
				.put("kanhu_shijian_id", request
						.getParameter("kanhu_shijian_id"));// 看护时间段
		outsitePlanManageDao.insert_time_model(params);// 新增看护时间段

    }

	// 外力点计划修该
    @Override
    public void update_outsite_plan(HttpServletRequest request) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("plan_id", request.getParameter("plan_id"));// 计划名称
	
		params.put("out_site_id", request.getParameter("out_site_id"));// 计划名称
		params.put("outsite_name", request.getParameter("outsite_name"));// 计划开始时间
		params.put("start_date", request.getParameter("start_date"));// 计划结束时间
		params.put("end_date", request.getParameter("end_date"));// 计划周期
		params.put("kan_name_id", request.getParameter("kan_name_id"));// 看护人员
		params.put("kan_name", request.getParameter("kan_name"));
		params.put("creation_time", request.getParameter("update_time"));
		params.put("creator", request.getParameter("update_name"));// 创建者
		params.put("update_time", request.getParameter("update_time"));
		params.put("update_name", request.getParameter("update_name"));// 修改者
		params.put("area_id", request.getSession().getAttribute("areaId"));// 区域ID
		
		//params.put("jianguan_name_id", request.getParameter("jianguan_name_id"));
		//params.put("jianguan_name", request.getParameter("jianguan_name"));
		//params.put("plan_create_date", request.getParameter("plan_create_date"));
		// params.put("jianguan_name_id", DateUtil.getDate());//创建时间
	
		// 外力点详情新增
		Map<String, Object> pmaxid = new HashMap<String, Object>();
	
		// 查询经纬度X,Y
		outsitePlanManageDao.update_outsite_plan(params);
		
		String kanhu_shijian = request.getParameter("kanhu_shijian_id");
		if(null != kanhu_shijian && !"".equals(kanhu_shijian.trim())){
			outsitePlanManageDao.update_time_model(params);// 删除旧时间段
			params.put("kanhu_shijian_id", request
					.getParameter("kanhu_shijian_id"));// 看护时间段
			outsitePlanManageDao.insert_time_model(params);
		}

    }

	// 查询单个外力点计划
    @Override
    public Map<String, Object> query_outsite_single(HttpServletRequest request) {

	Map<String, Object> params = new HashMap<String, Object>();
		params.put("plan_id", request.getParameter("plan_id"));// 计划ID

	return outsitePlanManageDao.query_outsite_single(params);
    }

	// 暂停计划
    @Override
    public void stop(HttpServletRequest request) {
	Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", request.getParameter("flag"));// 标志
		params.put("plan_id", request.getParameter("plan_id"));// 标志
	String str = request.getParameter("time_detail");
	String[] strArray = null;
	strArray = str.split(",");
	    for (int i = 0; i < strArray.length; i++) {
				params.put("time_detail", strArray[i]);// 标志
		outsitePlanManageDao.changePlan(params);
	    }
    }

    public Map queryTaskList(HttpServletRequest request, UIPage pager) {
	String PLAN_ID = request.getParameter("PLAN_ID");
	Map params = new HashMap();
	params.put("PLAN_ID", PLAN_ID);
	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(params);
	List<Map<String, Object>> list = outsitePlanManageDao.queryTaskList(query);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", list);
	map.put("total", query.getPager().getRowcount());
	return map;
    }

    public Map queryTimeFregment(HttpServletRequest request, UIPage pager) {
	String PLAN_ID = request.getParameter("PLAN_ID");
	Map params = new HashMap();
	params.put("PLAN_ID", PLAN_ID);
	Query query = new Query();
	query.setPager(pager);
	query.setQueryParams(params);
	List<Map<String, Object>> list = outsitePlanManageDao.queryTimeFregment(query);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", list);
	map.put("total", query.getPager().getRowcount());
	return map;
    }

	/** 外力点--迁移日志 **/
    @Override
    public Map<String, Object> query_movelog(HttpServletRequest request, UIPage pager) {
		String old_longitude = request.getParameter("old_longitude");// 计划开始时间
		String old_latitude = request.getParameter("old_latitude");// 计划结束时间
		String site_name = request.getParameter("site_name");
		Map params = new HashMap();
		params.put("site_name", site_name);
		params.put("old_longitude", old_longitude);
		params.put("old_latitude", old_latitude);
		params.put("area_id", request.getSession().getAttribute("areaId"));// 区域ID
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(params);
		List<Map<String, Object>> list = outsitePlanManageDao.query_movelog(query);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", query.getPager().getRowcount());
		return map;
    }

	// 查询单个迁移日志
    @Override
    public Map<String, Object> select_movelog(HttpServletRequest request) {

	Map<String, Object> params = new HashMap<String, Object>();
		params.put("move_id", request.getParameter("move_id"));// 计划ID

	return outsitePlanManageDao.select_movelog(params);
    }

	// 迁移日志修该
    @Override
    public void update_outsite_movelog(HttpServletRequest request) {

	Map<String, Object> params = new HashMap<String, Object>();
		params.put("move_id", request.getParameter("move_id"));// 计划名称

		params.put("out_site_id", request.getParameter("out_site_id"));// 计划名称
		params.put("old_longitude", request.getParameter("old_longitude"));// 看护人员
	params.put("old_latitude", request.getParameter("old_latitude"));
	params.put("new_longitude", request.getParameter("new_longitude"));
	params.put("new_latitude", request.getParameter("new_latitude"));

	outsitePlanManageDao.update_outsite_movelog(params);

    }

	// 外力点计划新增
    @Override
    public void insert_invalid_time(Map<String, Object> params_time) {
	outsitePlanManageDao.insert_invalid_time(params_time);
    }

	// 查询无效时间图
    @Override
    public List<Map<String, Object>> list_no_picture(HttpServletRequest request) {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("line_date", request.getParameter("line_date"));
	params.put("user_id", request.getParameter("user_id"));
	return outsitePlanManageDao.list_no_picture(params);
    }

	/**
	 * 作用： 　　*作者：
	 * 
	 * @param para
	 * @return
	 */
    public Map<String, Object> getElebarlw(Map<String, Object> para) {
	// TODO Auto-generated method stub
	double p0lng = Double.parseDouble(para.get("p0lng").toString());
	double p0lat = Double.parseDouble(para.get("p0lat").toString());

	double p1lng = Double.parseDouble(para.get("p1lng").toString());
	double p1lat = Double.parseDouble(para.get("p1lat").toString());

	double p2lng = Double.parseDouble(para.get("p2lng").toString());
	double p2lat = Double.parseDouble(para.get("p2lat").toString());

	double d1 = MapDistance.getDistance(p0lat, p0lng, p1lat, p1lng);
	double d2 = MapDistance.getDistance(p1lat, p1lng, p2lat, p2lng);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("d1", d1);
	map.put("d2", d2);
	return map;
    }

	@Override
	public boolean validatePlanTime(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("plan_id", request.getParameter("plan_id"));// 计划名称
		params.put("kan_name_id", request.getParameter("kan_name_id"));
		//编辑时，校验开始时间从当前时间开始，新增时无plan_id
		if(!"".equals(request.getParameter("plan_id"))){
			params.put("start_date", DateUtil.getDate(1));
		}
		else{
			params.put("start_date", request.getParameter("start_date"));// 计划结束时间
		}
		params.put("end_date", request.getParameter("end_date"));
		int count = outsitePlanManageDao.queryOutsitePlanCount(params);
		if (count > 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> getOutSitePlanInspector(
			Map<String, Object> map) {
		return outsitePlanManageDao.getOutSitePlanInspector(map);
	}

	@Override
	public List<Map<String, Object>> getOutSitePlanTrack(
			Map<String, Object> para) {

		if (StringUtil.isNOtNullOrEmpty(para.get("start_time").toString())) {
			String startTime = para.get("start_time").toString();
			startTime = startTime.substring(0, startTime.indexOf(":"));
			int st = Integer.parseInt(startTime);
			if (st < 10) {
				startTime = "0" + para.get("start_time").toString();
			} else {
				startTime = para.get("start_time").toString();
			}
			para.put("start_time", startTime);
		}

		if (StringUtil.isNOtNullOrEmpty(para.get("end_time").toString())) {
			String endTime = para.get("end_time").toString();
			endTime = endTime.substring(0, endTime.indexOf(":"));
			int et = Integer.parseInt(endTime);
			if (et < 10) {
				endTime = "0" + para.get("end_time").toString();
			} else {
				endTime = para.get("end_time").toString();
			}
			para.put("end_time", endTime);
		}

		return outsitePlanManageDao.getOutSitePlanTrack(para);
	}

	@Override
	public List<Map<String, Object>> getOutSitePlanInfo(
			Map<String, Object> params) {
		return outsitePlanManageDao.getOutSitePlanInfo(params);
	}

}
