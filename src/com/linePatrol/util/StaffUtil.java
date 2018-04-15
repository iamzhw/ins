/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * 
 */
public class StaffUtil {

    /**
     * @param request
     * @return
     */
    public static String getStaffId(HttpServletRequest request) {
	// TODO Auto-generated method stub
	return request.getSession().getAttribute("staffId").toString();
    }

    public static String getStaffNo(HttpServletRequest request) {
	// TODO Auto-generated method stub
	return request.getSession().getAttribute("staffNo").toString();
    }

    public static String getStaffAreaId(HttpServletRequest request) {
	// TODO Auto-generated method stub
	return request.getSession().getAttribute("areaId").toString();
    }

}
