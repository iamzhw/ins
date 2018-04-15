package com.linePatrol.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
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
import com.linePatrol.dao.KeyPointDao;
import com.linePatrol.service.KeyPointService;

@Service
@Transactional
public class KeyPointServiceImpl implements KeyPointService {

	@Resource
	private KeyPointDao keyPointDao; 
	
	@Override
	public Map<String, Object> query(Map<String, Object> para) {
		List<Map<String,Object>> lists=keyPointDao.query(para);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("rows", lists);
		return pmap;
	}

	@Override
	public void keyPointInfoDownLoad(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> code = Arrays.asList(new String[] { "NAME", "GRADEONE", "GRADETWO", "MATH"});
			Query query = new Query();
			query.setQueryParams(para);
			List<Map<String,Object>> lists=keyPointDao.query(para);
			String fileName = para.get("param_date").toString() + "一周关键点到位率报表";
			String firstLine = para.get("param_date").toString() + "一周关键点到位率报表";
			String modalPath = request.getSession().getServletContext().getRealPath("/excelFiles")
				+ File.separator + "keyPointInfo.xlsx";
			int startRow = 3;
			ExcelUtil.writeToModalAndDown(code, lists, request, response, fileName, modalPath, startRow,
				firstLine);

	}

}
