package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
public interface LxxjCityDao {
	public void del_lxxj_city();
	public void del_lxxj_signed_rate_area();
	public void del_lxxj_key_point_detail();
	public void del_lxxj_key_task_distri_rate_grid();
	public void del_lxxj_key_task_distri_rate_area();
	public void del_lxxj_signed_rate_grid();
	public void del_lxxj_signed_detail();
	public void add_lxxj_city();
	public void add_lxxj_signed_rate_area();
	public void add_lxxj_key_point_detail();
	public void add_lxxj_key_task_distri_rate_grid();
	public void add_lxxj_key_task_distri_rate_area();
	public void add_lxxj_signed_rate_grid();
	public void add_lxxj_signed_detail();
	
	public void updateLineLength();
	public List<Map> querywork();
	public Map queryWorkById(String s);
	public Map getStaffInfo(String s);
	public Map getstaffByDept(String s);
	
	public String getTimeCount(String s);
	public String getTroubleCount(String s);
	public String getDistance(String s);
	
	public List<Map> queryStaffFromJYH();
	public void insertStaff(Map m);
	public int checkStaffExists(Map map);
	public void addRole(Map map);
	
	/**
	 * 
	* @Title: queryjyh1
	* @Description: TODO:查询集约化线路代维人员
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2017-12-19 上午10:22:14
	* @throws
	 */
	public List<Map<String,String>> queryjyh1();
	
	/**
	 * 
	* @Title: queryjyh2
	* @Description: 查询集约化班组长
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2017-12-19 上午10:22:14
	* @throws
	 */
	public List<Map<String,String>> queryjyh2();
	
	/**
	 * 
	* @Title: queryjyh3
	* @Description: 查集约化班组长所在班组人员
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2017-12-19 上午10:22:14
	* @throws
	 */
	public List<Map<String,String>> queryjyh3(String s);
	
	/**
	 * 
	* @Title: queryjyh4
	* @Description: 查询集约化代维公司管理员
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2017-12-19 上午10:22:14
	* @throws
	 */
	public List<Map<String,String>> queryjyh4();
	
	/**
	 * 
	* @Title: queryjyh5
	* @Description: 查询代维公司所辖员工
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2017-12-19 上午10:22:14
	* @throws
	 */
	public List<Map<String,String>> queryjyh5(String s);
	
	/**
	 * 
	* @Title: queryjyh6
	* @Description: 查询网格主页管理员
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2017-12-19 上午10:22:14
	* @throws
	 */
	public List<Map<String,String>> queryjyh6();
	
	/**
	 * 
	* @Title: queryjyh7
	* @Description: 根据网格管理员查询网格人员
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2017-12-19 上午10:22:14
	* @throws
	 */
	public List<Map<String,String>> queryjyh7(String s);
	
	/**
	 * 
	* @Title: getTaskByJYHNo
	* @Description: 根据集约化帐号获得任务
	* @param @param m
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2017-12-19 上午10:45:35
	* @throws
	 */
	public List<Map<String,Object>> getTaskByJYHNo(Map m);
	
	public List<Map> allPoints(Map m);
	
}
