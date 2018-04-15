package com.cableInspection.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cableInspection.dao.ChartDao;
import com.cableInspection.service.ChartService;
import com.system.constant.RoleNo;

@SuppressWarnings("all")
@Transactional
@Service
public class ChartServiceImpl implements ChartService {
	
	@Resource
	private ChartDao chartDao;
	
	@Override
	public Map queryChartData(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map params = new HashMap();
		List<Map> coverRateData = new ArrayList();
		List<Map> eqpRateData = new ArrayList();
		List<Map> troubleBillData = new ArrayList();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {
			params.put("area_type", "AREA_ID");
			params.put("area_id", 2);
		}else if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)){
			params.put("area_type", "SON_AREA_ID");
			params.put("area_id", session.getAttribute("areaId"));
		}else if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)){
			params.put("area_type", "SON_AREA_ID");
			params.put("area_id", session.getAttribute("areaId"));
		}else{
			params.put("area_type", "SON_AREA_ID");
			params.put("area_id", -1);
		}
		coverRateData=chartDao.queryCoverRate(params);
		eqpRateData=chartDao.queryEqpRate(params);
		troubleBillData=chartDao.queryTroubleBill(params);
		Map rs = new HashMap();
		rs.put("coverRateData", coverRateData);
		rs.put("eqpRateData", eqpRateData);
		rs.put("troubleBillData", troubleBillData);
		return rs;
	}

}
