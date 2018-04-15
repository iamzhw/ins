package com.zhxj.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface ZhxjPlanDao {
	public List<Map> queryPlan(Query query);
	
	/**
	 * 
	* @Title: addInspectionPlan
	* @Description: TODO(添加缆线巡检任务)
	* @param     设定文件
	* @return void    返回类型
	* @date 2017-12-27 下午02:05:45
	* @throws
	 */
	public void addInspectionPlan();
	
	public void removePlan(String s);
}
