package com.cableInspection.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("all")
public interface StaffLocationService {
	Map query(HttpServletRequest request);
	public String getAreaName(HttpServletRequest request);
}
