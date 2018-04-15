package com.roomInspection.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.roomInspection.service.JobService;
import com.roomInspection.service.TaskService;

/** 
 * 
 * @author huliubing
 * @version 1.0 
 * @since 2014-07-22 
 * 
 */
@SuppressWarnings("all")
public class InspectHelperJob{
	
	@Resource
	private JobService jobService;
	
	@Resource
	private TaskService taskService;
	
	public void  execute(){
		try{
			//遍历所有的计划为天的计划
			List<Map> dayJobList=jobService.getJobsByCircleId(1);
			for(Map job:dayJobList)
			{
				List<Map> tasks = taskService.getDayTaskByJobId(job);
				
				//如果任务不存在,查询任务
				if(tasks.size()==0)
				{
					//插入任务
					taskService.insertIntoDayTask(job);
					
					//插入执行任务详情
					tasks = taskService.getDayTaskByJobId(job);
					
					for(Map task : tasks)
					{
						taskService.insertIntoTaskDetail(task);
					}
				}
			}
			
			//遍历所有的计划为周的计划
			List<Map> weekJobList=jobService.getJobsByCircleId(2);
			for(Map job:weekJobList)
			{
				List<Map> tasks = taskService.getWeekTaskByJobId(job);
				
				//如果任务不存在,查询任务
				if(tasks.size()==0)
				{
					//插入任务
					taskService.insertIntoWeekTask(job);
					
					//插入执行任务详情
					tasks = taskService.getWeekTaskByJobId(job);
					
					for(Map task : tasks)
					{
						taskService.insertIntoTaskDetail(task);
					}
				}
			}			
			
			//遍历所有的计划为月的计划
			List<Map> monthJobList=jobService.getJobsByCircleId(3);
			for(Map job:monthJobList)
			{
				List<Map> tasks = taskService.getMonthTaskByJobId(job);
				
				//如果任务不存在,查询任务
				if(tasks.size()==0)
				{
					taskService.insertIntoMonthTask(job);
					
					//插入执行任务详情
					tasks = taskService.getMonthTaskByJobId(job);
					
					for(Map task : tasks)
					{
						taskService.insertIntoTaskDetail(task);
					}
				}
			}
			
			
			//遍历所有的计划为 半年的计划
			List<Map> halfYearJobList=jobService.getJobsByCircleId(4);
			for(Map job:halfYearJobList)
			{
				List<Map> tasks = taskService.getHalfYearTaskByJobId(job);
				
				//如果任务不存在,查询任务
				if(tasks.size()==0)
				{
					taskService.insertIntoHalfYearTask(job);
					
					//插入执行任务详情
					tasks = taskService.getHalfYearTaskByJobId(job);
					
					for(Map task : tasks)
					{
						taskService.insertIntoTaskDetail(task);
					}
				}
			}
			
			//遍历所有的计划为年的计划
			List<Map> yearJobList=jobService.getJobsByCircleId(5);
			for(Map job:yearJobList)
			{
				List<Map> tasks = taskService.getYearTaskByJobId(job);
				
				//如果任务不存在,查询任务
				if(tasks.size()==0)
				{
					taskService.insertIntoYearTask(job);
					
					//插入执行任务详情
					tasks = taskService.getYearTaskByJobId(job);
					
					for(Map task : tasks)
					{
						taskService.insertIntoTaskDetail(task);
					}
				}
			}
			
		}catch(Exception ex){
			   ex.printStackTrace();
		  }
	}
}
