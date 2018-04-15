package icom.webservice.dao;

import icom.webservice.model.TaskModel;

import java.util.List;

import com.cableInspection.model.PointModel;

@SuppressWarnings("all")
public interface CableTaskInterfaceDao {

	/**
	 * 根据用户账号获取待办缆线任务
	 * 
	 * @param staffId
	 *            业务员ID
	 * @return 待办缆线任务列表
	 */
	public List<TaskModel> getAllTaskByStaffNo(String staffNo);
	
	public List<TaskModel> getAllTaskByDeptNo(String staffNo);
	
	/**
	 * 根据planId获取缆线计划下所有缆线点信息(包括关键点和非关键点)
	 * 
	 * @param planId
	 *            缆线计划ID
	 * @return 缆线计划所有缆线点信息
	 */
	public List<PointModel> getAllPointsByPlanId(String planId);
}
