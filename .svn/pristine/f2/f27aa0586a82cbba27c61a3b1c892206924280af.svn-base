package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface CableTaskDao {

	public List<Map> taskQuery(Query query);

	public void deleteTask(Map<String, Object> params);

	public void deleteTaskDetail(Map<String, Object> params);
	
	public Map getTaskInfo(int TASK_ID);

	public List<Map<String, Object>> getTaskCable(String parameter);
}
