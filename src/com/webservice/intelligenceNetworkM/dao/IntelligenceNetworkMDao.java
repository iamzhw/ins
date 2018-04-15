package com.webservice.intelligenceNetworkM.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

@Repository
public interface IntelligenceNetworkMDao {
	
   //单点登录
	List<Map> singleLogin(Map map);
	
	//获取检查任务代办任务列表
	List<Map> getAgencyTaskList(Map map);
	
	//跳转任务列表
	List<Map> getTaskList(Map map);
	
	//隐患整治工单归档接
	void saveHiddenDangerList(Map map);
	
	//隐患整改单代办任务列表
	List<Map> getHiddenDangerTaskList(Map map);
	
	//远程检查结果保存
	void saveRemoteCheck(Map map);
	
	//查询工单详细信息
	List<Map> selectWorkOrderList(Map map);
	
	//获取检查任务代办任务数量
	List<Map> getAgencyTaskListCount(Map map);
	
	//隐患整改单代办任务数量getstaff_id
	List<Map> getHiddenDangerTaskListCount(Map map);
	
	//根据人员登录名查询人员ID
	List<Map> getstaff_id(Map map);
	
	//获取光路施工人接口
	List<Map> getLightPathPerson(Map map);
	
	//获取网格设备所属工单
	List<Map> getEquWorkOrder(Map map);
	
	//整治工单回单操作接口
	List<Map> remediationBackOder(Map map);
	
	void chargeback(String taskId);
	
}
