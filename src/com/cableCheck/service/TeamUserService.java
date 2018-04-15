package com.cableCheck.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface TeamUserService {
	
	public Map<String, Object> listUser(HttpServletRequest request, UIPage pager);
	
}
