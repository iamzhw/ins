package com.cableCheck.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface ReviewService {
	
	Map<String, Object> queryReviewRecords(HttpServletRequest request, UIPage pager);
	
	Map<String, Object> queryReviewDetailRecords(HttpServletRequest request, UIPage pager);
	
	void exportExcel(HttpServletRequest request, HttpServletResponse response);
	
	void exportDetailExcel(HttpServletRequest request, HttpServletResponse response);
	

 }
