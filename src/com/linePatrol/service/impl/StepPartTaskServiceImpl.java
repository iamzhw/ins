package com.linePatrol.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.dao.StepPartTaskDao;
import com.linePatrol.service.StepPartTaskService;

@Service
public class StepPartTaskServiceImpl implements StepPartTaskService{
   @Resource
   private StepPartTaskDao stepparttaskdao; 
	

	@Override
	public List<Map<String, Object>> selIsContainLandMark(Map<String,Object> map) {
		return stepparttaskdao.selIsContainLandMark(map);
	}

	@Override
	public void insertStepPartTask(Map<String, Object> map) {
		stepparttaskdao.insertStepPartTask(map);
	}

	@Override
	public List<Map<String, Object>> selIsContainNoLandMark(Map<String,Object> map) {
		return stepparttaskdao.selIsContainNoLandMark(map);
	}

	@Override
	public String selCircleByLandMark() {
		return stepparttaskdao.selCircleByLandMark();
	}

	@Override
	public void insertToTaskHis(Map<String, Object> map) {
		stepparttaskdao.insertToTaskHis(map);
	}

	@Override
	public void delTaskEquipLM(Map<String, Object> map) {
		stepparttaskdao.delTaskEquipLM(map);
	}

	@Override
	public void intoTaskEquipAddLM(Map<String, Object> map) {
		stepparttaskdao.intoTaskEquipAddLM(map);
	}

	@Override
	public List<Map<String, Object>> selNoLMTaskPeople(Map<String,Object> map) {
		return stepparttaskdao.selNoLMTaskPeople(map);
	}

	@Override
	public List<Map<String, Object>> selLMTaskPeople(Map<String, Object> map) {
		return stepparttaskdao.selLMTaskPeople(map);
	}

	@Override
	public String selLMTypeId() {
		return stepparttaskdao.selLMTypeId();
	}

	@Override
	public void intoTaskHisNoLM(Map<String, Object> map) {
		stepparttaskdao.intoTaskHisNoLM(map);
	}

	@Override
	public void delTaskEquipNoLM(Map<String, Object> map) {
		stepparttaskdao.delTaskEquipNoLM(map);
	}

	@Override
	public void intoTaskEquipAddNoLM(Map<String, Object> map) {
		stepparttaskdao.intoTaskEquipAddNoLM(map);
	}

	@Override
	public void insertLMStepPartTask(Map<String, Object> map) {
		stepparttaskdao.insertLMStepPartTask(map);
	}

	@Override
	public void removeStepPartRecord() {
		stepparttaskdao.removeStepPartRecord();
	}

	@Override
	public List<Map<String, Object>> selNoLMStepPartBysTask(Map<String,Object> map) {
		return stepparttaskdao.selNoLMStepPartBysTask(map);
	}

	@Override
	public List<Map<String, Object>> selLMStepPartTask(Map<String, Object> map) {
		return stepparttaskdao.selLMStepPartTask(map);
	}

	@Override
	public List<Map<String, Object>> selIsTaskByAllotID(Map<String, Object> map) {
		return stepparttaskdao.selIsTaskByAllotID(map);
	}

	@Override
	public List<Map<String, Object>> selLMTaskByMap(Map<String, Object> map) {
		return stepparttaskdao.selLMTaskByMap(map);
	}

	@Override
	public Map<String, Object> query(Map<String, Object> para, UIPage pager) {
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(para);

		List<Map<String, Object>> olists = stepparttaskdao.query(query);
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("rows",  olists);
		map.put("total", query.getPager().getRowcount());
		return map;
	}

	@Override
	public List<Map<String, Object>> selPartNameByTaskID(String task_id) {
		return stepparttaskdao.selPartNameByTaskID(task_id);
	}

	@Override
	public List<Map<String, Object>> selEffectiveTaskByStaffID(String staff_id) {
		return stepparttaskdao.selEffectiveTaskByStaffID(staff_id);
	}

	@Override
	public List<Map<String, Object>> selFacType() {
		return stepparttaskdao.selFacType();
	}

	@Override
	public List selErrorTypesByTypeID(String equip_type_id) {
		return stepparttaskdao.selErrorTypesByTypeID(equip_type_id);
	}

	@Override
	public Map<String, Object> selErrorNumber(Map<String, Object> map) {
		return stepparttaskdao.selErrorNumber(map);
	}

	@Override
	public void intoTaskNoRouthLmEquipByAllotID(Map<String, Object> map) {
		stepparttaskdao.intoTaskNoRouthLmEquipByAllotID(map);
	}

	@Override
	public void intoTaskRouthLmEquipByAllotID(Map<String, Object> map) {
		stepparttaskdao.intoTaskRouthLmEquipByAllotID(map);
	}
	
	@Override
	public List<Map<String,Object>> getTaskTime(Map<String,Object> map){
		return  stepparttaskdao.getTaskTime(map);
	}

	@Override
	public List<Map<String, Object>> selIsContainNoRouthLandMark(Map<String, Object> map){
		return stepparttaskdao.selIsContainNoRouthLandMark(map);
	}

	@Override
	public List<Map<String, Object>> selIsContainRouthLandMark(Map<String, Object> map){
		return stepparttaskdao.selIsContainRouthLandMark(map);
	}

	@Override
	public String selTaskId() {
		return stepparttaskdao.selTaskId();
	}

	@Override
	public List<Map<String, Object>> selTaskEquipHasNoLM(Map<String, Object> map) {
		return stepparttaskdao.selTaskEquipHasNoLM(map);
	}

	@Override
	public List<Map<String, Object>> selTaskEquipHasLM(Map<String, Object> map) {
		return stepparttaskdao.selTaskEquipHasLM(map);
	}

	@Override
	public Map<String, Object> selTaskByPersionAndCircle(Map<String, Object> map) {
		return stepparttaskdao.selTaskByPersionAndCircle(map);
	}

	@Override
	public void upTaskIdFromTaskEquip(Map<String, Object> map) {
		stepparttaskdao.upTaskIdFromTaskEquip(map);
	}

	@Override
	public void intoHisNoRouteTaskEquip() {
		stepparttaskdao.intoHisNoRouteTaskEquip();
	}

	@Override
	public void delNoRouteTaskEquip() {
		stepparttaskdao.delNoRouteTaskEquip();
	}

	@Override
	public void intoTaskHisNoLMRoute(Map<String, Object> map) {
		stepparttaskdao.intoTaskHisNoLMRoute(map);
	}

	@Override
	public void intoTaskEquipAddLMRoute(Map<String, Object> map) {
		stepparttaskdao.intoTaskEquipAddLMRoute(map);
	}

	@Override
	public void intoHisTaskLMEquipNoRouth(Map<String, Object> map) {
		stepparttaskdao.intoHisTaskLMEquipNoRouth(map);
	}

	@Override
	public void delHisTaskLMEquipNoRouth(Map<String, Object> map) {
		stepparttaskdao.delHisTaskLMEquipNoRouth(map);
	}

	@Override
	public void intoHisTaskEquipNoRouthLM(Map<String, Object> map) {
		stepparttaskdao.intoHisTaskEquipNoRouthLM(map);
	}

	@Override
	public void delHisTaskEquipNoRouthLM(Map<String, Object> map) {
		stepparttaskdao.delHisTaskEquipNoRouthLM(map);
	}

}
