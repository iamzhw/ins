package com.system.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.page.BaseAction;

import com.system.constant.Constants;
import com.system.dao.StaffDao;
import com.system.service.StaffService;
import com.system.tool.HttpUtil;

/**
 * 跨域请求，校验身份信息是否合法
 * @author wangxiangyu
 *
 */
@Controller
@RequestMapping("/check")
@SuppressWarnings("all")
public class CheckController extends BaseAction {
	
	@Autowired
	StaffService staffService;
	@Autowired
	StaffDao staffDao;
	/**
	 * 验证身份证号码与姓名是否一致
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/checkIdNumber.do")
	@ResponseBody
	public void checkIdNumber(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String id_number = request.getParameter("id_number");
		String user_name = request.getParameter("real_name");
		if("".equals(user_name) || user_name == null) {
			user_name = request.getParameter("user_name");
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("ID", id_number);
		param.put("Name", user_name);
		param.put("SeqNo", "1");//流水号
		param.put("ChannelID", "1003");//渠道号
		
		String result = HttpUtil.sendPost(Constants.DATA_SYNC, param, "utf-8");
		JSONObject resObj = JSONObject.fromObject(result);
		
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("result", resObj.get("result").toString());
		rs.put("smsg", resObj.get("smsg").toString());
		
		write(response, rs);
	}
	/**
	 * 用于将符合统一工号规范的staff_name赋值给real_name
	 */
	@RequestMapping("/update.do")
	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取数据库全部帐号
		List<Map> staffs = staffService.findAllStaff();
		//验证姓名与身份证号是否一致
		Map<String, String> param = new HashMap<String, String>();
		param.put("SeqNo", "1");//流水号
		param.put("ChannelID", "1003");//渠道号
		for(Map staff : staffs) {
			param.put("ID", staff.get("ID_NUMBER").toString());
			param.put("Name", staff.get("STAFF_NAME").toString());
			String result = HttpUtil.sendPost(Constants.DATA_SYNC, param, "utf-8");
			JSONObject resObj = JSONObject.fromObject(result);
			String code = resObj.get("result").toString();
			//一致，将staff_name更新到real_name
			if("00".equals(code)) {
				param.put("real_name", staff.get("STAFF_NAME").toString());
				param.put("staff_id", staff.get("STAFF_ID").toString());
				staffDao.modifyRealName(param);
				//System.out.println("--------【一致】--------"+staff.get("STAFF_NAME").toString());
			}else {
				System.out.println("--------【不一致】--------"+staff.get("STAFF_NAME").toString());
			}
			
		}
		
	}

}
