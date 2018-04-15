package icom.cableCheck.serviceimpl;

import icom.cableCheck.dao.CheckTaskProcessDao;
import icom.cableCheck.service.CheckTaskProcessService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/** 
 * @author wangxy
 * @version 创建时间：2016年7月27日 下午5:26:32 
 * 类说明 
 */
@Service
@SuppressWarnings("all")
public class CheckTaskProcessServiceImpl implements CheckTaskProcessService {

	Logger logger = Logger.getLogger(CheckTaskProcessServiceImpl.class);
	@Autowired
	private CheckTaskProcessDao checkTaskProcessDao;
	
	@Override
	public String getProcess(String jsonStr){
		
		JSONObject result = new JSONObject();
		result.put("result", "000");
		try{
			/**
			 * 传入的参数
			 */
			JSONObject json = JSONObject.fromObject(jsonStr);
			String taskId = json.getString("taskId");
			String staffId = json.getString("staffId");
			String terminalType = json.getString("terminalType");
			String sn = json.getString("sn");
			
			Map paramMap = new HashMap();
			paramMap.put("task_id", taskId);
			//先通过taskid获取oldtaskid
			String oldTaskId=checkTaskProcessDao.getOldTaskId(paramMap);
			JSONArray jsonArr = new JSONArray();
			List<Map> processList = new ArrayList<Map>();
			if(oldTaskId==null||"".equals(oldTaskId)||"null".equals(oldTaskId)){
				processList=checkTaskProcessDao.getProcess(paramMap);
			}else{
				paramMap.put("oldTaskId", oldTaskId);
				processList=checkTaskProcessDao.getProcessInfo(paramMap);
			}
					
			
			for(Map process : processList){
				JSONObject jsonObj = new JSONObject();
//				jsonObj.put("task_id", process.get("TASK_ID"));
				jsonObj.put("operTime", process.get("OPER_TIME"));
				jsonObj.put("operStaff", process.get("OPER_STAFF"));
				jsonObj.put("operStaffName", process.get("STAFF_NAME"));
				jsonObj.put("status", process.get("STATUS_ID"));
				jsonObj.put("statusName", process.get("STATUS"));
				jsonObj.put("remark", process.get("REMARK"));
				jsonArr.add(jsonObj);
			}			
			result.put("taskId", taskId);
			result.put("process", jsonArr);
	
		}catch(Exception e) {
			result.put("error", e.toString());
			result.put("desc", "查看流程失败，请联系管理员。");
			logger.info(e.toString());
		}
		
		return result.toString();
		
	}
	
}
