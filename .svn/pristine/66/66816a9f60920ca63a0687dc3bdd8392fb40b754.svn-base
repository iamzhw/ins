package com.roomInspection.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.roomInspection.dao.JfxjTroubleDao;
import com.roomInspection.service.JfxjTroubleService;

/**
 * 
 * @ClassName: JfxjTroubleServiceImpl
 * @Description: 巡检工单业务层实现类 
 * 
 * @author huliubing
 * @since: 2014-8-5
 *
 */
@SuppressWarnings("all")
@Service
@Transactional
public class JfxjTroubleServiceImpl implements JfxjTroubleService {

	@Resource
	private JfxjTroubleDao jfxjTroubleDao;
	
	@Override
	public Map<String,Object> query(HttpServletRequest request, UIPage pager) {
		
		//封装查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("TASK_NAME", request.getParameter("task_name"));
		map.put("ROOM_NAME", request.getParameter("room_name"));
		map.put("CHECK_STAFF_NAME", request.getParameter("check_staff_name"));
		Query query  = new Query();
		query.setQueryParams(map);
		query.setPager(pager);
		
		List<Map> trobuleList = jfxjTroubleDao.query(query);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("rows", trobuleList);
		paramMap.put("total", query.getPager().getRowcount());
		
		return paramMap;
	}
	
	@Override
	public Map<String,Object> detail(HttpServletRequest request)
	{
		//获取工单ID
		int checkDetailId = Integer.parseInt(request.getParameter("check_detail_id"));
		
		
		 Map<String,Object> map = jfxjTroubleDao.queryDetail(checkDetailId);
		
		 //如果工单信息存在，查询工单关联的图片
		if(null != map)
		{
			List<Map> pictures = jfxjTroubleDao.queryPicByDetailId(checkDetailId);
			map.put("pictures", pictures);
		}
		else
		{
			map = new HashMap<String,Object>();
		}
		
		return map;
	}

}
