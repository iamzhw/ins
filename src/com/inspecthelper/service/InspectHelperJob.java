package com.inspecthelper.service;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.inspecthelper.dao.InsTaskDao;

/** 
 * 
 * @author lbhu
 * @version 1.0 
 * @since 2014-11-13 
 * 
 */
@SuppressWarnings("all")
public class InspectHelperJob {
	
	@Autowired
	InsTaskDao insTaskDao;
	
	public void  execute(){
	
		try{
			//将到期的任务置为等待执
			insTaskDao.updateTaskOrderActiveState(new HashMap());
			
			//将过期未完成的工单置为已过期
			insTaskDao.updateTaskOrderOverdueState(new HashMap());
			
			//将过期的任务置为无效
			insTaskDao.updateTaskOverdueState(new HashMap());
	       
		}catch(Exception ex){
			   ex.printStackTrace();
		  }
	}
}
