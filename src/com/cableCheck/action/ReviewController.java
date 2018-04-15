package com.cableCheck.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableCheck.service.CheckQualityReportService;
import com.cableCheck.service.ReformOrderReportService;
import com.cableCheck.service.ReviewService;
import com.linePatrol.util.MapUtil;

@RequestMapping(value = "/Review")
@SuppressWarnings("all")
@Controller
public class ReviewController extends BaseAction{
	Logger logger = Logger.getLogger(ReviewController.class);
	
	@Autowired
	private ReviewService reviewService;

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("cablecheck/Review", null);
	}
	
	@RequestMapping(value="/reviewRecords.do")
	public void reviewRecords(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = reviewService.queryReviewRecords(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value="/reviewDetailRecords.do")
	public void reviewDetailRecords(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException{
		
		Map<String, Object> map = reviewService.queryReviewDetailRecords(request, pager);
		write(response, map);
	}
	
	@RequestMapping(value="/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
		reviewService.exportExcel(request, response);
	}
	
	@RequestMapping(value="/exportDetailExcel.do")
	public void exportDetailExcel(HttpServletRequest request, HttpServletResponse response){
		reviewService.exportDetailExcel(request, response);
	}

}
