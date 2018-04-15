package com.cableInspection.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.UIPage;

@SuppressWarnings("all")
public interface TimeCountService {
	Map query(HttpServletRequest request, UIPage pager);
	List<Map> export(HttpServletRequest request);
}
