package com.webservice.unifiedno.service.impl; 

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.linePatrol.util.DateUtil;
import com.system.dao.StaffDao;
import com.webservice.unifiedno.dao.UnifiedNoDao;
import com.webservice.unifiedno.service.UnifiedNoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.password.MD5;

/** 
 * @author wangxiangyu
 * @date：2017年9月27日 下午4:00:04 
 * 类说明：
 */
@SuppressWarnings("all")
@WebService(serviceName = "unifiedNoService")
public class UnifiedNoServiceImpl implements UnifiedNoService {
	
	@Autowired
	UnifiedNoDao unifiedNoDao;
	@Autowired
	private StaffDao staffDao;
	
	@Override
	public String operateAccount(String jsonStr) {
		
		System.out.println(DateUtil.getDateAndTime()+":operateAccount入参信息"+jsonStr);
		//请求参数
		JSONObject json = JSONObject.fromObject(jsonStr);
		String signature = json.getString("signature");//签名
		String sn = json.getString("sn");//流水号
		String sys_flag = json.getString("sys_flag");//系统标识
		String city = json.getString("city");//地区
		String opt_type = json.getString("opt_type");//操作类型
		String time = json.getString("time");//请求时间戳
		
		String result = "";
		if("S001".equals(opt_type)) {
			result = addOrUpdateAccount(jsonStr);//员工信息更新操作
		}else if("S002".equals(opt_type)) {
			result = suspendAccount(jsonStr);//员工冻结操作
		}else if("S003".equals(opt_type)) {
			result = thawAccount(jsonStr);//员工解冻操作
		}else if("S004".equals(opt_type)) {
			result = deleteAccount(jsonStr);//员工注销操作
		}
		return result;
	}
	
	
	/**
	 * 更新账号
	 */
	private String addOrUpdateAccount(String jsonStr) {
		
		System.out.println(DateUtil.getDateAndTime()+":addOrUpdateAccount入参信息"+jsonStr);
		
		JSONObject result = new JSONObject();
		//请求参数
		JSONObject json = JSONObject.fromObject(jsonStr);
		String signature = json.getString("signature");//签名
		String sn = json.getString("sn");//流水号
		String sys_flag = json.getString("sys_flag");//系统标识
		String city = json.getString("city");//地区
		String opt_type = json.getString("opt_type");//操作类型
		String time = json.getString("time");//请求时间戳
		
		//返回参数
		result.put("signature", signature);
		result.put("sn", sn);
		result.put("sys_flag", sys_flag);
		result.put("city", city);
		result.put("opt_type", opt_type);
		result.put("time", getTimestamp());
		
		JSONObject resObj = new JSONObject();
		resObj.put("rsid", "000");
		resObj.put("rscode", "操作成功");
		result.put("result", resObj);
		try {
			//请求data信息
			JSONObject data = json.getJSONObject("data");
			//请求个人信息
			JSONObject staff = data.getJSONObject("staffResult");
			String staff_id = null==staff.get("staff_id")?"":staff.getString("staff_id");//员工工号
			String name = null==staff.get("name")?"":staff.getString("name");//姓名
			String birth = null==staff.get("birth")?"":staff.getString("birth");//出生年月
			String ascription_area = null==staff.get("ascription_area")?"":staff.getString("ascription_area");//归属地区
//			String pwd = null==staff.get("pwd")?"":staff.getString("pwd");
//			String password = MD5.encrypt(MD5.md5s(pwd));
			String sex = null==staff.get("sex")?"":staff.getString("sex");//性别
			String telephone = null==staff.get("telephone")?"":staff.getString("telephone");//联系电话
			String area_code = null==staff.get("area_code")?"":staff.getString("area_code");//行政区域
			String oa_user_id = null==staff.get("oa_user_id")?"":staff.getString("oa_user_id");//OA工号
			String card_type = null==staff.get("card_type")?"":staff.getString("card_type");//证件类型1：身份证
			String idcard = null==staff.get("idcard")?"":staff.getString("idcard");//身份证号码
			String email = null==staff.get("email")?"":staff.getString("email");//电子邮件
			String wechat_no = null==staff.get("wechat_no")?"":staff.getString("wechat_no");//微信号
			String bestpay_no = null==staff.get("bestpay_no")?"":staff.getString("bestpay_no");//翼支付账号
			String employment_mode = null==staff.get("employment_mode")?"":staff.getString("employment_mode");//用工方式10：合同制；20：派遣制；30：外包制
			String state = null==staff.get("state")?"":staff.getString("state");//使用状态0：正常；1：冻结；2：注销
			String is_smz = null==staff.get("is_smz")?"":staff.getString("is_smz");
			
			//封装参数
			Map map = new HashMap();
			map.put("signature", signature);
			map.put("sn", sn);
			map.put("sys_flag", sys_flag);
			map.put("city", city);
			map.put("opt_type", opt_type);
			map.put("time", time);
			map.put("staff_id", staff_id);
			map.put("name", name);
			map.put("birth", birth);
			map.put("ascription_area", ascription_area);
//			map.put("pwd", pwd);
//			map.put("password", password);
			map.put("sex", sex);
			map.put("telephone", telephone);
			map.put("area_code", area_code);
			map.put("card_type", card_type);
			map.put("idcard", idcard);
			map.put("email", email);
			map.put("state", state);
			map.put("is_smz", is_smz);
//			map.put("t_staff_id", t_staff_id);
			
			//请求系统信息
			JSONArray systemArr = data.getJSONArray("SystemResult");
			
			if(systemArr.size()>0) {
				for(int i=0; i<systemArr.size(); i++) {
					JSONObject system = (JSONObject)systemArr.get(i);
					String org_id = null==staff.get("org_id")?"":staff.getString("org_id");//组织编码
					String org_name = null==staff.get("org_name")?"":staff.getString("org_name");//组织名称
					String city_id = null==staff.get("city_id")?"":staff.getString("city_id");//业务地区如025,0512
					String supple_info_id = null==staff.get("supple_info_id")?"":staff.getString("supple_info_id");//员工id(组织+系统+统一工号的唯一键)
//					String branch_serial = null==staff.get("branch_serial")?"":staff.getString("branch_serial");
					String zone_id = null==staff.get("zone_id")?"":staff.getString("zone_id");//子区域
					String maintain_type = null==staff.get("maintain_type")?"":staff.getString("maintain_type");//维护员类型
					String org_realtion = null==staff.get("org_realtion")?"":staff.getString("org_realtion");//组织关系
					String roles_id = null==staff.get("roles_id")?"":staff.getString("roles_id");//综合巡检角色集合
					String sys_org_id = null==staff.get("sys_org_id")?"":staff.getString("sys_org_id");//综合巡检组织id

					map.put("org_id", org_id);
					map.put("org_name", org_name);
					map.put("city_id", city_id);
					map.put("supple_info_id", supple_info_id);
//					map.put("branch_serial", branch_serial);
//					map.put("area", area);
					
					//验证工号是否存在
					if(accountIfExist(staff_id)) {
						unifiedNoDao.updateAccount(map);//更新
					}else {
						unifiedNoDao.insertAccount(map);//新增
					}
					//保存操作记录
					unifiedNoDao.saveRecord(map);
				}
			}else {
				//验证工号是否存在
				if(accountIfExist(staff_id)) {
					unifiedNoDao.updateAccount(map);//更新
				}else {
					unifiedNoDao.insertAccount(map);//新增
				}
				//保存操作记录
				unifiedNoDao.saveRecord(map);
			}
		}catch(Exception e) {
			e.printStackTrace();
			resObj.put("rsid", "002");
			resObj.put("rscode", "操作失败");
			result.put("result", resObj);
		}
		return result.toString();
	}
	/**
	 * 注销账号
	 */
	private String deleteAccount(String jsonStr) {
		System.out.println(DateUtil.getDateAndTime()+":deleteAccount入参信息"+jsonStr);
		JSONObject result = new JSONObject();
		//请求参数
		JSONObject json = JSONObject.fromObject(jsonStr);
		String signature = json.getString("signature");//签名
		String sn = json.getString("sn");//流水号
		String sys_flag = json.getString("sys_flag");//系统标识
		String city = json.getString("city");//地区
		String opt_type = json.getString("opt_type");//操作类型
		String time = json.getString("time");//请求时间戳
		String staff_id = json.getString("staff_id");//员工工号
		
		//封装参数
		Map param = new HashMap();
		param.put("signature", signature);
		param.put("sn", sn);
		param.put("sys_flag", sys_flag);
		param.put("city", city);
		param.put("opt_type", opt_type);
		param.put("time", time);
		param.put("staff_id", staff_id);
		//返回参数
		result.put("signature", signature);
		result.put("sn", sn);
		result.put("sys_flag", sys_flag);
		result.put("city", city);
		result.put("opt_type", opt_type);
		result.put("time", getTimestamp());
		
		JSONObject resObj = new JSONObject();
		resObj.put("rsid", "000");
		resObj.put("rscode", "操作成功");
		//验证工号是否存在
		if(accountIfExist(staff_id)) {
			try{
				//冻结账号操作
				unifiedNoDao.deleteAccount(param);
			}catch(Exception e){
				resObj.put("rsid", "002");
				resObj.put("rscode", "操作失败");
				e.printStackTrace();
			}
			
		}else {
			resObj.put("rsid", "000");
			resObj.put("rscode", "员工工号未找到，操作成功");
		}
		result.put("result", resObj);
		//保存操作记录
		unifiedNoDao.saveRecord(param);
		
		return result.toString();
	}
	
	/**
	 * 冻结账号
	 */
	private String suspendAccount(String jsonStr) {
		System.out.println(DateUtil.getDateAndTime()+":suspendAccount入参信息"+jsonStr);
		JSONObject result = new JSONObject();
		//请求参数
		JSONObject json = JSONObject.fromObject(jsonStr);
		String signature = json.getString("signature");//签名
		String sn = json.getString("sn");//流水号
		String sys_flag = json.getString("sys_flag");//系统标识
		String city = json.getString("city");//地区
		String opt_type = json.getString("opt_type");//操作类型
		String time = json.getString("time");//请求时间戳
		String staff_id = json.getString("staff_id");//员工工号
		
		//封装参数
		Map param = new HashMap();
		param.put("signature", signature);
		param.put("sn", sn);
		param.put("sys_flag", sys_flag);
		param.put("city", city);
		param.put("opt_type", opt_type);
		param.put("time", time);
		param.put("staff_id", staff_id);
		//返回参数
		result.put("signature", signature);
		result.put("sn", sn);
		result.put("sys_flag", sys_flag);
		result.put("city", city);
		result.put("opt_type", opt_type);
		result.put("time", getTimestamp());
		
		JSONObject resObj = new JSONObject();
		resObj.put("rsid", "000");
		resObj.put("rscode", "操作成功");
		//验证工号是否存在
		if(accountIfExist(staff_id)) {
			try{
				//冻结账号操作
				unifiedNoDao.suspendAccount(param);
			}catch(Exception e){
				resObj.put("rsid", "001");
				resObj.put("rscode", "操作失败");
				e.printStackTrace();
			}
		}else {
			resObj.put("rsid", "001");
			resObj.put("rscode", "员工工号不存在");
		}
		result.put("result", resObj);
		//保存操作记录
		unifiedNoDao.saveRecord(param);
		
		return result.toString();
	}
	/**
	 * 解冻账号
	 */
	private String thawAccount(String jsonStr) {
		System.out.println(DateUtil.getDateAndTime()+":thawAccount入参信息"+jsonStr);
		JSONObject result = new JSONObject();
		//请求参数
		JSONObject json = JSONObject.fromObject(jsonStr);
		String signature = json.getString("signature");//签名
		String sn = json.getString("sn");//流水号
		String sys_flag = json.getString("sys_flag");//系统标识
		String city = json.getString("city");//地区
		String opt_type = json.getString("opt_type");//操作类型
		String time = json.getString("time");//请求时间戳
		String staff_id = json.getString("staff_id");//员工工号
		
		//封装参数
		Map param = new HashMap();
		param.put("signature", signature);
		param.put("sn", sn);
		param.put("sys_flag", sys_flag);
		param.put("city", city);
		param.put("opt_type", opt_type);
		param.put("time", time);
		param.put("staff_id", staff_id);
		//返回参数
		result.put("signature", signature);
		result.put("sn", sn);
		result.put("sys_flag", sys_flag);
		result.put("city", city);
		result.put("opt_type", opt_type);
		result.put("time", getTimestamp());
		
		JSONObject resObj = new JSONObject();
		resObj.put("rsid", "000");
		resObj.put("rscode", "操作成功");
		//验证工号是否存在
		if(accountIfExist(staff_id)) {
			try{
				//冻结账号操作
				unifiedNoDao.thawAccount(param);
			}catch(Exception e){
				resObj.put("rsid", "002");
				resObj.put("rscode", "操作失败");
				e.printStackTrace();
			}
		}else {
			resObj.put("rsid", "001");
			resObj.put("rscode", "员工工号不存在");
		}
		result.put("result", resObj);
		//保存操作记录
		unifiedNoDao.saveRecord(param);
		
		return result.toString();
		
	}
	
	/**
	 * 获取时间戳
	 * @return
	 */
	private String getTimestamp() {
		
		String time = "";
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			time = sdf.format(System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
		
	}
	/**
	 * 验证账号是否存在
	 * @param staffId
	 * @return
	 */
	private boolean accountIfExist(String staffId) {
		//验证工号是否存在
		Map user = unifiedNoDao.findAccount(staffId);
		if(null != user && user.size() > 0) {
			return true;
		}
		return false;
	}

}
