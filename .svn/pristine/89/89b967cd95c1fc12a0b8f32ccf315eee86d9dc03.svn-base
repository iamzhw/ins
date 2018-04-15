package com.zhxj.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface ZhxjTaskDao {
	public List<Map> queryTask(Query query);
	
	/**
	 * 
	* @Title: addInspectionTask
	* @Description: TODO(添加缆线巡检任务)
	* @param     设定文件
	* @return void    返回类型
	* @date 2017-12-27 下午02:05:45
	* @throws
	 */
	public void addInspectionTask();
	
	/**
	 * 
	* @Title: getButton
	* @Description: TODO(获取任务对应的button)
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @date 2018-1-3 下午03:25:53
	* @throws
	 */
	public List<Map> getButton();
	
	public List<Map> getTaskType();
	
	public void removeTask(String s);
	
	public void editTaskType(Map m);
	
	public void editTaskTypeForlxxj(String s);
}
