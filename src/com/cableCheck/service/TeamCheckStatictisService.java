package com.cableCheck.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.page.UIPage;

public interface TeamCheckStatictisService {
	public Map<String, Object> statictis(HttpServletRequest request, UIPage pager);
}
