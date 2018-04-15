package icom.cableCheck.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import icom.cableCheck.dao.CheckBanzuDao;
import icom.cableCheck.dao.CheckMyworkDao;
import icom.cableCheck.dao.CheckOrderDao;
import icom.cableCheck.dao.CheckTaskDao;
import icom.cableCheck.service.CheckBanzuService;
import icom.cableCheck.service.CheckOrderService;
import icom.webservice.client.WfworkitemflowLocator;
import icom.webservice.client.WfworkitemflowSoap11BindingStub;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.cableCheck.dao.CableMyTaskDao;
import com.cableCheck.dao.CheckProcessDao;
import com.ctc.wstx.util.StringUtil;
import com.linePatrol.util.DateUtil;
/**
 * 班组
 * 
 */
@Service
@SuppressWarnings("all")
public class CheckbanzuServiceImpl implements CheckBanzuService {

	Logger logger = Logger.getLogger(CheckbanzuServiceImpl.class);

	@Resource
	private CheckBanzuDao checkBanzuDao;
	
	@Override
	public String getBanzuByAreaId(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		List<Map> list = new ArrayList<Map>();
		Map<String,String> map = new HashMap<String,String>();
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String son_area_id = json.getString("son_area_id");
			String staffId = json.getString("staffId");
			String sn = json.getString("sn");
			
			map.put("son_area_id", son_area_id);
			map.put("sn", sn);
			map.put("staffId", staffId);
			
			list = checkBanzuDao.getBanzuByAreaId(map);
			
			Map mapBz = new HashMap();
			//2017年9月27日09:37:03 如果staffId不为空，查询该人员所在班组id
			if(org.apache.commons.lang.StringUtils.isNotBlank(staffId)){
				List<Map> listBZOne = checkBanzuDao.getStaffTeamId(map);	
				if(listBZOne.size()>0){
					//将该人员班组放置于第一位
					list.add(0,listBZOne.get(0));
				}
			}
			result.put("banzu", list);
			
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", "003");
			result.put("desc", "获取班组信息失败");
			result.put("banzu", list);
		}
		return result.toString();
	}

	@Override
	public String getStaffByTeamId(String jsonStr) {
		JSONObject result = new JSONObject();
		JSONArray jsArr = new JSONArray();
		result.put("result", "000");
		List<Map> list = new ArrayList<Map>();
		Map<String,String> map = new HashMap<String,String>();
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			String teamId = json.getString("teamId");
			String sn = json.getString("sn");
			
			map.put("teamId", teamId);
			map.put("sn", sn);
			
			list = checkBanzuDao.getStaffByTeamId(map);
			result.put("staff", list);
			
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", "003");
			result.put("desc", "获取班组人员失败");
			result.put("staff", list);
		}
		return result.toString();
	}


	
}