package icom.system.serviceimpl;

import icom.system.dao.InitDao;
import icom.system.service.InitService;
import icom.util.ExceptionCode;
import icom.util.ProcessJson;
import icom.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.password.MD5;

import com.system.constant.Constants;
import com.system.dao.StaffDao;
import com.system.service.ParamService;
import com.system.sys.OnlineUser;
import com.system.sys.OnlineUserListener;
import com.system.tool.HttpUtil;
import com.util.DESUtil;
import com.util.EncryptUtils;
import com.util.EncryptUtilsAPP;
import com.util.PasswordUtil;

/**
 * @ClassName: GeneralServiceImpl
 * @Description:
 * @author: SongYuanchen
 * @date: 2014-1-17
 * 
 */
@SuppressWarnings("all")
@Service
public class InitServiceImpl implements InitService {
	@Resource
	private InitDao initDao;
	
	@Resource
	private ParamService paramService;
	
	@Resource
	private StaffDao staffDao;
	/**
	 * 智能网管拉起缆线巡检
	 */
	private final String FGSLXXJ_OPERATION_TYPE = "0";
	/**
	 * 智能网管拉起资源巡检
	 */
	private final String ZYXJ_OPERATION_TYPE = "1";
	
	/**
	 * 智能网管拉起光网助手
	 */
	private final String GWZS_OPERATION_TYPE = "2";
	
	@Override
	public String getAppInfo(String json) {
		String appType = "";
		try {
			JSONObject jo = JSONObject.fromObject(json);
			appType = String.valueOf(jo.get("appType"));
		} catch (Exception e) {
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
		}
		Map map = initDao.getAppInfo(appType);
		if (map == null) {
			return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
		} else {
			String[] cols = new String[] { "APPNAME", "PACKAGE_NAME",
					"VERSIONCODE", "FORCEUPDATE", "DOWNLOADURL", "VERSIONNAME",
					"DESCRIPTION", "ICON" };
			String resultJsonStr = ProcessJson.getResultJsonObject(map, cols);
			return resultJsonStr;
		}

		// String str = JSONObject.fromObject(map).toString();
		// return str;
	}

	@Override
	public String login(String json, HttpServletRequest request) {
		
		String user = "";
		String pwd = "";
		String sn = "";
		String userIdNumber = "";
		String userType = "";
		String telNumber = "";
		String realName = "";
		// json = "{\"user\":\"xj110\",\"pwd\":\"1\"}";

		Map map = new HashMap();
		HttpSession session = request.getSession();
		try {
			JSONObject jo = JSONObject.fromObject(json);
			user = String.valueOf(jo.get("user"));
			pwd = String.valueOf(jo.get("pwd"));
			
//			//des解密后账号
//			user=DESUtil.decryption(user, "kEHrDooxWHCWtfeSxvDvgqZq");
//			//des解密后密码
//			pwd=DESUtil.decryption(pwd, "kEHrDooxWHCWtfeSxvDvgqZq");
			
			  
	    	user = EncryptUtilsAPP.decrypt("1234567812345678", user);
	    	pwd = EncryptUtilsAPP.decrypt("1234567812345678", pwd);
			
			sn = String.valueOf(jo.get("sn"));
			userIdNumber = null!=jo.get("useridnum")?String.valueOf(jo.get("useridnum")):"";
			telNumber = null!=jo.get("telNum")?String.valueOf(jo.get("telNum")):"";
			realName = null!=jo.get("username")?String.valueOf(jo.get("username")):"";
			
			userType = null!=jo.get("usertype")?(String.valueOf(jo.get("usertype"))=="自维"?"0":"1"):"";
			if(isOtherLogin(user,sn)){//账号在其他手机登录，不允许登录
				return Result.returnCode("004","此账号已登录，请退出后重试。");
			}
			
			map.put("user", user);
			map.put("pwd", MD5.encrypt(MD5.md5s(pwd)).trim());

		} catch (Exception e) {
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR,"客户端参数错误或不完整");
		}
		Map userInfo = initDao.validate(map);
		JSONArray ja = null;
		if (userInfo == null) {
			return Result.returnCode(ExceptionCode.NULL_DATA_ERROR,"账号或密码错误");
		} else {
			// String resultJsonStr = ProcessJson.getResultJsonObject(map,
			// cols);
			// return resultJsonStr;

			// 查询用户具有的应用信息
			List<Map> apps = initDao.getAllAppInfo(user);
			String[] cols = new String[] { "APPNAME", "PACKAGE_NAME",
					"VERSIONCODE", "FORCEUPDATE", "DOWNLOADURL", "VERSIONNAME",
					"DESCRIPTION", "ICON", "APPTYPE" };
			ja = ProcessJson.getResultJsonArray(apps, cols);

			String userid = String.valueOf(userInfo.get("USERID"));
			String areaid = String.valueOf(userInfo.get("AREAID"));
			String sonareaid = String.valueOf(userInfo.get("SONAREAID"));
			String areacode = String.valueOf(userInfo.get("AREACODE"));
			String name = String.valueOf(userInfo.get("NAME"));
			String privilege = String.valueOf(userInfo.get("PRIVILEGE"));
			String idNumber = String.valueOf(userInfo.get("ID_NUMBER"));
			String tel = String.valueOf(userInfo.get("TEL"));
			String real_name = String.valueOf(userInfo.get("REAL_NAME"));
			
			session.setAttribute("staffId", userid);
			session.setAttribute("sn", sn);
			
			JSONObject res = new JSONObject();
			res.put("result", ExceptionCode.SUCCESS);
			if(PasswordUtil.checkPassword(pwd))
			{
				res.put("isSimplePwd", true);
			}
			else
			{
				res.put("isSimplePwd", false);
			}
			List<Map<String, Object>> staffSoftList = staffDao.getStaffSoftList(userid);
			
			//第二遍手机端传入了身份证号码
			if(null!=userIdNumber&&"null"!=userIdNumber&&!"".equals(userIdNumber)){
				Map userMap = new HashMap();
				userMap.put("STAFF_NO", user);
				userMap.put("ID_NUMBER", userIdNumber);
				userMap.put("MAINTOR_TYPE", userType);
				try {
					staffDao.modifyIdNumber(userMap);
					res.put("isTrueName", 1);
				} catch (Exception e) {
					res.put("desc", "身份证账号修改失败");
					e.printStackTrace();
				}
			}else{//第一遍
				//是否实名认证
				if((null!=idNumber&&"null"!=idNumber)){
					res.put("isTrueName", 1);
				}else{
					res.put("isTrueName", 0);
				}
			}
			//第二遍手机端传入了手机号
			if(null!=telNumber&&"null"!=telNumber&&!"".equals(telNumber)){
				Map userMap = new HashMap();
				userMap.put("staff_id", userid);
				userMap.put("mobileNumber", telNumber);
				try {
					staffDao.modifyMobileNumber(userMap);
					res.put("hasTel", 1);
				} catch (Exception e) {
					res.put("desc", "手机号码修改失败");
					e.printStackTrace();
				}
			}else{//第一遍
				//是否有手机号
				if((null!=tel&&"null"!=tel)){
					res.put("hasTel", 1);//有
				}else{
					res.put("hasTel", 0);
				}
			}
			// TODO 第二遍手机端传入了真实姓名
			if(null!=realName&&"null"!=realName&&!"".equals(realName)){
				Map userMap = new HashMap();
				userMap.put("staff_id", userid);
				userMap.put("real_name", realName);
				try {
					if(checkIdAndName(userIdNumber, realName)) {
						staffDao.modifyRealName(userMap);
						res.put("hasRealName", 1);
					}else {
						res.put("hasRealName", 0);
						res.put("desc", "姓名与身份证号不匹配");
					}
				} catch (Exception e) {
					res.put("desc", "真实姓名修改失败");
					res.put("hasRealName", 0);
					e.printStackTrace();
				}
			}else{//第一遍
				//是否有真实姓名
				if((null!=real_name&&"null"!=real_name)){
					res.put("hasRealName", 1);//有
				}else{
					res.put("hasRealName", 0);
				}
			}
			
			//更新登录时间
			staffDao.updateLoginDate(userid);
			List<Map<String, Object>> lmap=staffDao.getRoleList(userid);
			res.put("userid", userid);
			res.put("areaid", areaid);
			res.put("sonareaid", sonareaid);
			res.put("areacode", areacode);
			res.put("name", name);
			res.put("privilege", privilege);
			res.put("apps", ja);
			res.put("role", lmap);
			
			//孙敏 2016/6/6 判断这个用户是否包含省管理员如果包含就返回1否则返回0
			List<Map> judgeIsAdmin = initDao.judgeIsAdmin(userid);
			if(judgeIsAdmin.size()>0){
				res.put("isAdmin", "1");
			}else{
				res.put("isAdmin", "0");
			}
			/**
			 * 限制手机端登录，已登录帐号不能重复登录
			 */
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("USER_ID", userid);
			Map<String,Object> login = paramService.getIsLoginInfo(param);
			if (login != null && "1".equals(String.valueOf(login.get("IS_LOGIN")))
					&& sn.equals(String.valueOf(login.get("SN")))) {//登录信息不用重复记录
				return res.toString();
			} else {
				paramService.insertLogin(userid, sn, "1", null);
				return res.toString();
			}
		}
	}

	@Override
	public String changePwd(String json) {
		// json = "{\"staffId\":\"2622\",\"oldPwd\":\"2\",\"newPwd\":\"1\"}";

		String staffId = "";
		String oldPwd = "";
		String newPwd = "";

		Map map = new HashMap();
		try {
			JSONObject jo = JSONObject.fromObject(json);
			staffId = String.valueOf(jo.get("staffId"));
			oldPwd = String.valueOf(jo.get("oldPwd"));
			newPwd = String.valueOf(jo.get("newPwd"));
 
			map.put("staffId", staffId);
			map.put("oldPwd", MD5.encrypt(MD5.md5s(oldPwd)).trim());

			map.put("newPwd", newPwd);
			map.put("newPwd_", MD5.encrypt(MD5.md5s(newPwd)).trim());

		} catch (Exception e) {
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
		}

		int validate = initDao.validateByStaffId(map);
		if (validate == 1) {
			// 验证成功，去修改用户的密码
			initDao.changePwd(map);
			return Result.returnCode(ExceptionCode.SUCCESS);

		} else {
			return Result.returnCode(ExceptionCode.USERNAME_PASSWORD_ERROR);

		}
	}

	@Override
	public String feedbackAdvice(String json) {
		// json =
		// "{\"staffId\":\"2622\",\"content\":\"1侧hi\",\"apptype\":\"8\"}";
		String apptype = "";
		String content = "";
		String staffId = "";
		Map map = new HashMap();
		try {
			JSONObject jo = JSONObject.fromObject(json);
			apptype = String.valueOf(jo.get("apptype"));
			content = String.valueOf(jo.get("content"));
			staffId = String.valueOf(jo.get("staffId"));

			map.put("apptype", apptype);
			map.put("content", content);
			map.put("staffId", staffId);

		} catch (Exception e) {
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
		}
		initDao.feedbackAdvice(map);

		return Result.returnCode(ExceptionCode.SUCCESS);
	}

	@Override
	public String getUrl(String json) {
		// json = "{\"staffId\":\"2622\",\"isWww\":\"1\"}";

		String staffId = "";
		String isWww = "";
		Map map = new HashMap();
		try {
			JSONObject jo = JSONObject.fromObject(json);
			staffId = String.valueOf(jo.get("staffId"));
			isWww = String.valueOf(jo.get("isWww"));
			map.put("staffId", staffId);
			map.put("isWww", isWww);

		} catch (Exception e) {
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
		}

		List<Map> urls = initDao.getUrl(map);
		if (urls.size() == 0) {
			return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
		} else {
			String[] cols = new String[] { "URL", "URL_TYPE" };
			JSONArray ja = ProcessJson.getResultJsonArray(urls, cols);
			JSONObject res = new JSONObject();
			res.put("result", ExceptionCode.SUCCESS);

			res.put("urls", ja);
			return res.toString();
		}
	}

	@Override
	public String getSjxjUrl(HttpServletRequest request) {
		String url=initDao.getSjxjUrl();
		JSONObject res = new JSONObject();
		res.put("sjxjUrl", url);
		return res.toString();
	}
	

	@Override
	public String logoutUser(String jsonStr, HttpServletRequest request) {
		JSONObject json = JSONObject.fromObject(jsonStr);

		String userId = json.getString("userId");// 用户ID
		String sn = json.getString("sn");// 用户ID
		if (json.containsKey("personId") && (null != json.getString("personId") || "" != json.getString("personId"))) {
			paramService.insertLogin(json.getString("personId"), sn, "0", userId);
			userId = json.getString("personId");
			OnlineUserListener.logoutUser(userId, null);
		} else {
			paramService.insertLogin(userId, sn, "0", null);
			OnlineUserListener.logoutUser(userId, sn);

			HttpSession session = request.getSession();
			session.invalidate();// session销毁
		}

		JSONObject res = new JSONObject();
		res.put("result", "000");
		return res.toString();
	}

	@Override
	public String getLoginUser(String jsonStr) {
		
		JSONObject json = JSONObject.fromObject(jsonStr);
		String userId = json.getString("userId");// 用户ID
		String sn = json.getString("sn");// 用户ID
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Map param = new HashMap();
			param.put("bzz_id", userId);
			List<Map> staffs = initDao.getLoginStaff(param);
			List<Map> userList = new ArrayList<Map>();
			if(null != staffs && staffs.size() > 0){
				for(Map staff : staffs){
					String staffId = String.valueOf(staff.get("STAFF_ID"));//获取巡线员ID
					OnlineUser onLineUser = OnlineUserListener.getOnlineUser(staffId);
					if(null != onLineUser){
						Map userMap = new HashMap();
						userMap.put("staff_id", onLineUser.getStaffId());
						userMap.put("staff_name", String.valueOf(staff.get("STAFF_NAME")));
						userMap.put("sn", onLineUser.getSn());
						userMap.put("login_time", onLineUser.getLoginTime());
						userList.add(userMap);
					}
				}
			}
			
			map.put("staffList", JSONArray.fromObject(userList));
			map.put("result", "000");
		}catch(Exception e){
			map.put("result", "001");
		}
		
		return JSONObject.fromObject(map).toString();
	}
	
	
	/**
	 * 如果是巡线员，需要判断账号是否在其他地方登录，如果已经登录不允许登录
	 * 
	 * @param jo
	 * @return
	 */
	private boolean isOtherLogin(String user,String sn){
		Map param = new HashMap();
		param.put("staff_no", user);
		List<Map> staffs = initDao.getLoginStaff(param);//判断是否为巡线员
		if(null != staffs && staffs.size() > 0){
			String staffId = String.valueOf(staffs.get(0).get("STAFF_ID"));//获取巡线员ID
			if (OnlineUserListener.isOtherLogin(staffId, sn)) {// 账号在其他地方登录
				return true;
			}
		}
		return false;
	}	
	@Override
	public String singleLogin(String json, HttpServletRequest request) {
		
		String user = "";
		String pwd = "";
		String key = "";
		String sn = "";
		//json = "{\"user\":\"15897\",\"terminalType\":\"1\",\"sn\":\"355309070559535\",\"key\":\"c02c8f24849a635a556bb32bb34ab82a\",\"operationType\":\"0\"}";
		//json = "{\"terminalType\":\"1\",\"sn\":\"865267029806907\",\"operationType\":\"0\",\"user\":\"201.\",\"key\":\"1894c35afebcc864feea944b1f7e8aba\"}";
		Map map = new HashMap();
		HttpSession session = request.getSession();
		try {
			JSONObject jo = JSONObject.fromObject(json);
			user = String.valueOf(jo.get("user"));
			key = String.valueOf(jo.get("key"));
			String operationType =  String.valueOf(jo.get("operationType"));
			sn = String.valueOf(jo.get("sn"));
			pwd = PasswordUtil.encryption("portal" + user);
			//智能网管拉起缆线巡检去校验staffId
			if(FGSLXXJ_OPERATION_TYPE.equals(operationType))
			{
				//校验staffId是否符合加密规则
				/*if(!key.equals(pwd))
				{
					return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
				}*/
			}if(GWZS_OPERATION_TYPE.equals(operationType)){
				if(!key.equals(pwd))
				{
					return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
				}
			}
			if(isOtherLogin(user,sn)){//账号在其他手机登录，不允许登录
				return Result.returnCode("004");
			}
			map.put("user", user);
			map.put("pwd", pwd);

		} catch (Exception e) {
			return Result.returnCode(ExceptionCode.CLIENT_PARAM_ERROR);
		}
		Map	userInfo =null;
		JSONArray ja = null;
		//user是否是纯数字的ID，如果不是纯数字的ID，则直接先去校验工号
		if (!user.matches("\\d*"))
		{
			userInfo = initDao.singleLoginValidateByStaffNo(map);
			if (userInfo == null || null==userInfo.get("STAFF_NO") || "".equals(userInfo.get("STAFF_NO"))) 
			{
				return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
			}
		}
		else{
			userInfo = initDao.singleLoginValidate(map);
			
			if (userInfo == null || null==userInfo.get("STAFF_NO") || "".equals(userInfo.get("STAFF_NO"))) 
			{
				userInfo = initDao.singleLoginValidateByStaffNo(map);
				if (userInfo == null || null==userInfo.get("STAFF_NO") || "".equals(userInfo.get("STAFF_NO"))) 
				{
					return Result.returnCode(ExceptionCode.NULL_DATA_ERROR);
				}
			}
		}
		// String resultJsonStr = ProcessJson.getResultJsonObject(map,
		// cols);
		// return resultJsonStr;

		// 查询用户具有的应用信息
		List<Map> apps = initDao.getAllAppInfo(userInfo.get("STAFF_NO").toString());
		String[] cols = new String[] { "APPNAME", "PACKAGE_NAME",
				"VERSIONCODE", "FORCEUPDATE", "DOWNLOADURL", "VERSIONNAME",
				"DESCRIPTION", "ICON", "APPTYPE" };
		ja = ProcessJson.getResultJsonArray(apps, cols);

		String userid = String.valueOf(userInfo.get("USERID"));
		String areaid = String.valueOf(userInfo.get("AREAID"));
		String sonareaid = String.valueOf(userInfo.get("SONAREAID"));
		String areacode = String.valueOf(userInfo.get("AREACODE"));
		String name = String.valueOf(userInfo.get("NAME"));
		String privilege = String.valueOf(userInfo.get("PRIVILEGE"));
		
		session.setAttribute("staffId", userid);
		session.setAttribute("sn", sn);
		
		JSONObject res = new JSONObject();
		res.put("result", ExceptionCode.SUCCESS);
		res.put("isSimplePwd", true);
		//更新登录时间
		staffDao.updateLoginDate(userid);
		res.put("userid", userid);
		res.put("areaid", areaid);
		res.put("sonareaid", sonareaid);
		res.put("areacode", areacode);
		res.put("name", name);
		res.put("privilege", privilege);
		res.put("apps", ja);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("USER_ID", userid);
		Map<String,Object> login = paramService.getBaseLoginInfo(param);
		if (login != null && "1".equals(String.valueOf(login.get("IS_LOGIN")))
				&& sn.equals(String.valueOf(login.get("SN")))) 
		{//登录信息不用重复记录
			return res.toString();
		} 
		else 
		{
			paramService.insertLogin(userid, sn, "1", null);
			return res.toString();
		}
	}
	
	private boolean checkIdAndName(String idNumber, String name) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("ID", idNumber);
		param.put("Name", name);
		param.put("SeqNo", "1");//流水号
		param.put("ChannelID", "1003");//渠道号
		String result = HttpUtil.sendPost(Constants.DATA_SYNC, param, "utf-8");
		JSONObject resObj = JSONObject.fromObject(result);
		String checkResult = resObj.get("result").toString();
		String checkMsg = resObj.get("smsg").toString();
		if("00".equalsIgnoreCase(checkResult)) {
			return true;
		}
		return false;
	}
}
