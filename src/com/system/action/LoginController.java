package com.system.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.password.MD5;

import com.system.constant.Constants;
import com.system.constant.RoleNo;
import com.system.dao.RoleDao;
import com.system.dao.StaffDao;
import com.system.model.ZTreeNode;
import com.system.service.LoginService;
import com.system.service.RoleService;
import com.system.tool.HttpUtil;
import com.util.DESUtil;
import com.util.EncryptUtils;
import com.util.PasswordUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: LoginController
 * @Description: 登录、注销初始化主界面
 * @author: SongYuanchen
 * @date: 2014-1-16
 * 
 */
@SuppressWarnings("all")
@Controller
public class LoginController {

	@Resource
	private LoginService loginService;

	@Resource
	private RoleService roleService;

	@Resource
	private StaffDao staffDao;
	/**
	 * @throws Exception
	 * 
	 * @Title: login
	 * @Description: 登录方法
	 * @param: @param request
	 * @param: @param response
	 * @param: @return
	 * @return: Map
	 * @throws
	 */
	@RequestMapping(value = "/Login/login.do")
	@ResponseBody
	public Map login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		Map map = new HashMap();
		rs = loginService.login(request);
		if (rs == null) {
			map.put("status", false);
			map.put("message", "登录失败");
			return map;
		} else {
			String loginStatus = rs.get("STATUS").toString();
			if (null == loginStatus || "".equals(loginStatus)
					|| "1".equals(loginStatus)) {
				map.put("status", false);
				map.put("message", "此用户已停用");
				return map;
			}
			String staffNo = request.getParameter("username");
			String password = request.getParameter("password");
			/**
			 * aes解密
			 */
			staffNo=EncryptUtils.aesDecrypt(staffNo, "abcdefgabcdefg12");
			password=EncryptUtils.aesDecrypt(password, "abcdefgabcdefg12");
			
			List nodes = loginService.getGns(-1, staffNo);
			if (nodes == null || nodes.size() == 0) {
				map.put("status", false);
				map.put("message", "此用户没有权限，请联系管理员");
				return map;
			}
			session.setAttribute("staffId", String.valueOf(rs.get("STAFF_ID")));
			session.setAttribute("staffNo", String.valueOf(rs.get("STAFF_NO")));
			session.setAttribute("staffName",String.valueOf(rs.get("STAFF_NAME")));
			session.setAttribute("passWord", request.getParameter("password"));
			session.setAttribute("areaId", String.valueOf(rs.get("AREA_ID")));
			session.setAttribute("staffNo", String.valueOf(rs.get("STAFF_NO")));
			session.setAttribute("id_number", String.valueOf(rs.get("ID_NUMBER")));
			// session.setAttribute("areaId",76);
			// session.setAttribute("staffNo", "710003");
			session.setAttribute("sonAreaId",String.valueOf(rs.get("SON_AREA_ID")));
			session.setAttribute("tel",String.valueOf(rs.get("TEL")));
			// 获取用户角色
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("STAFF_ID", rs.get("STAFF_ID"));
			List<String> roleList = roleService.getRoleList(params);
			boolean hasLXXJ = false;// 是否有缆线巡检的权限
			boolean hasAXX = false;// 是否有爱巡线的权限
			boolean hasZYXJ = false;// 是否有资源巡检的权限
			boolean hasJFXJ = false;// 是否有机房巡检的权限
			boolean hasCABLE = false;// 是否有光网助手权限
			if (roleList != null && roleList.size() > 0) {
				for (String roleNo : roleList) {
					if (roleNo.startsWith("LXXJ")) {
						hasLXXJ = true;
					} else if (roleNo.startsWith("AXX")) {
						hasAXX = true;
					} else if (roleNo.startsWith("ZYXJ")) {
						hasZYXJ = true;
					} else if (roleNo.startsWith("JFXJ")) {
						hasJFXJ = true;
					}
					else if (roleNo.startsWith("CABLE")) {
						hasCABLE = true;
					}
					if (RoleNo.GLY.equals(roleNo)) {
						session.setAttribute(RoleNo.GLY, true);
						continue;
					}
					if (RoleNo.LXXJ_ADMIN.equals(roleNo)) {
						session.setAttribute(RoleNo.LXXJ_ADMIN, true);
						continue;
					}
					if (RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)) {
						session.setAttribute(RoleNo.LXXJ_ADMIN_AREA, true);
						continue;
					}
					if (RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
						session.setAttribute(RoleNo.LXXJ_ADMIN_SON_AREA, true);
						continue;
					}
					session.setAttribute(roleNo, true);
				}
			}
			session.setAttribute(RoleNo.HAS_LXXJ, hasLXXJ);
			session.setAttribute(RoleNo.HAS_AXX, hasAXX);
			session.setAttribute(RoleNo.HAS_ZYXJ, hasZYXJ);
			session.setAttribute(RoleNo.HAS_JFXJ, hasJFXJ);
			session.setAttribute(RoleNo.HAS_CABLE, hasCABLE);
			if (session.getAttribute(RoleNo.GLY) == null) {
				session.setAttribute(RoleNo.GLY, false);
			}
			if (session.getAttribute(RoleNo.LXXJ_ADMIN) == null) {
				session.setAttribute(RoleNo.LXXJ_ADMIN, false);
			}
			if (session.getAttribute(RoleNo.LXXJ_ADMIN_AREA) == null) {
				session.setAttribute(RoleNo.LXXJ_ADMIN_AREA, false);
			}
			if (session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA) == null) {
				session.setAttribute(RoleNo.LXXJ_ADMIN_SON_AREA, false);
			}
			if (session.getAttribute(RoleNo.CABLE_ADMIN) == null) {//光网助手_省级管理员
				session.setAttribute(RoleNo.CABLE_ADMIN, false);
			}
			if (session.getAttribute(RoleNo.CABLE_ADMIN_AREA) == null) {//光网助手_市级管理员
				session.setAttribute(RoleNo.CABLE_ADMIN_AREA, false);
			}
			if (session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA) == null) {//光网助手_区级管理员
				session.setAttribute(RoleNo.CABLE_ADMIN_SONAREA, false);
			}
			if (session.getAttribute(RoleNo.CABLE_WHY) == null) {//光网助手_维护员
				session.setAttribute(RoleNo.CABLE_WHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_XJY) == null) {//光网助手_巡检员
				session.setAttribute(RoleNo.CABLE_XJY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_SHY) == null) {//光网助手_审核员
				session.setAttribute(RoleNo.CABLE_SHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_TYJDG) == null) {//光网助手_南京统一接单岗
				session.setAttribute(RoleNo.CABLE_TYJDG, false);
			}
			if (session.getAttribute(RoleNo.CABLE_NJ_SHY_A) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_NJ_SHY_A, false);
			}
			if (session.getAttribute(RoleNo.CABLE_NJ_SHY_B) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_NJ_SHY_B, false);
			}
			if (session.getAttribute(RoleNo.CABLE_ZONG_WHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_ZONG_WHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_ZHUANG_WHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_ZHUANG_WHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_WANG_SHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_WANG_SHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_GONG_SHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_GONG_SHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_ZHENG_SHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_ZHENG_SHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_WU_SHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_WU_SHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_WANG_WHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_WANG_WHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_ZHENG_WHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_ZHENG_WHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_WU_WHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_WU_WHY, false);
			}
			if (session.getAttribute(RoleNo.CABLE_GONG_WHY) == null) {//光网助手_南京审核员
				session.setAttribute(RoleNo.CABLE_GONG_WHY, false);
			}
			
			map.put("status", true);
			if(PasswordUtil.checkPassword(password))
			{
				map.put("isSimplePwd", true);
			}
			else
			{
				map.put("isSimplePwd", false);
			}
			//TODO 验证姓名和身份证号是否一致
			String id_number = null!=rs.get("ID_NUMBER")?rs.get("ID_NUMBER").toString():"";
			String user_name = null!=rs.get("STAFF_NAME")?rs.get("STAFF_NAME").toString():"";
			Map<String, String> param = new HashMap<String, String>();
			param.put("ID", id_number);
			param.put("Name", user_name);
			param.put("SeqNo", "1");//流水号
			param.put("ChannelID", "1003");//渠道号
			String result = HttpUtil.sendPost(Constants.DATA_SYNC, param, "utf-8");
			JSONObject resObj = JSONObject.fromObject(result);
			String checkResult = resObj.get("result").toString();
			String checkMsg = resObj.get("smsg").toString();
			if("00".equalsIgnoreCase(checkResult)) {
				map.put("isCheckOk", true);
			}else {
				map.put("isCheckOk", false);
			}
			//是否已保留真实姓名
			String real_name = null!=rs.get("REAL_NAME")?rs.get("REAL_NAME").toString():"";
			if(null==real_name||""==real_name){
				map.put("hasRealName", false);
			}else{
				map.put("hasRealName", true);
			}
			//是否已保留身份证号码
			if(null==id_number||""==id_number){
				map.put("isTrueName", false);
			}else{
				map.put("isTrueName", true);
			}
			//是否已保留手机号码
			String tel = null==rs.get("TEL")?"":rs.get("TEL").toString();
			if(null==tel||""==tel){
				map.put("hasTel", false);
			}else{
				map.put("hasTel", true);
			}
			//更新登录时间
			staffDao.updateLoginDate( String.valueOf(rs.get("STAFF_ID")));
			return map;
		}
	}

	/**
	 * 
	 * @Title: logout
	 * @Description: 注销
	 * @param: @param request
	 * @param: @param response
	 * @param: @throws IOException
	 * @return: void
	 * @throws
	 */
	@RequestMapping(value = "/logout.do")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("");
	}

	/**
	 * 
	 * @Title: getGns
	 * @Description: 获取功能树
	 * @param: @param request
	 * @param: @param response
	 * @param: @return
	 * @return: List
	 * @throws
	 */
	@RequestMapping(value = "/Login/getGns.do")
	@ResponseBody
	@Transactional
	public List getGns(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		response.setHeader("Cache-Control", "no-cache");
		String staffNo = (String) session.getAttribute("staffNo");
		if (staffNo != null) {
			String id = request.getParameter("id");
			int treeId = -1;
			if (id != null && !id.equals("")) {
				treeId = Integer.parseInt(id);
			}
			List<ZTreeNode> nodes = loginService.getGns(treeId, staffNo);
			return nodes;
		} else {
			return null;
		}
	}
	@RequestMapping(value = "/Login/isSimplePwd.do")
	@ResponseBody
	@Transactional
	public Map isSimplePwd(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		response.setHeader("Cache-Control", "no-cache");
		//session.getAttribute("passWord");
		//String staffNo = (String) session.getAttribute("staffNo");
		if(PasswordUtil.checkPassword(session.getAttribute("passWord").toString()))
		{
			map.put("isSimplePwd", true);
		}
		else
		{
			map.put("isSimplePwd", false);
		}
		return map;
	}
	
	
	/**
	 * 单点登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Login/singleLogin.do")
	@ResponseBody
	public Map singleLogin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		Map map = new HashMap();
		Map parms = new HashMap();
		HttpSession session = request.getSession();
		
		String account_no=request.getParameter("account_no");//用户账号
		String account_no_pwd=request.getParameter("account_no_pwd");//加密后用户账号
		String id_card=request.getParameter("id_card");//身份证号码
		String id_card_pwd=request.getParameter("id_card_pwd");//加密后身份证号码
		String token=request.getParameter("token");//令牌
		String req_system=request.getParameter("req_system");//请求方系统 集约化平台：3
		
		
		if(null ==account_no || "" ==  account_no){
			map.put("message", "用户账号为空");
			return map;
		}else if( null ==account_no_pwd  ||"" ==  account_no_pwd ){
			map.put("message", "加密后用户账号为空");
			return map;
		}else if(null == token || ""==token){
			map.put("message", "令牌为空");
			return map;
		}else if(null == req_system || ""== req_system){
			map.put("message", "请求方系统参数为空");
			return map;
		}/*else if(req_system.equals(3)){
			
		}*/
		
		/**
		 * 解析用户账号account_no是否按照Md532位小写方式加密，编码为utf-8的加密方式加密
		 * 如果巡检系统不存在此用户账号account_no，则校验身份证号是否按照Md532位小写方式加密，编码为utf-8的加密方式加密。
		 * 如果巡检系统不存在此用户账号account_no，身份证号已按照Md532位小写方式加密，编码为utf-8的加密方式加密，则根据身份证号获取此身份证账号进行单点登录
		 */
		
		String a = PasswordUtil.encryption("portal" + account_no);
		if(a.equals(account_no_pwd)){
			parms.put("STAFF_NO", account_no);
			rs = loginService.singleLogin(parms);
			if (rs == null) {
				String b = PasswordUtil.encryption("portal" + id_card);
				if(b.equals(id_card_pwd)){
					parms.put("ID_NUMBER", id_card);
					rs = loginService.singleLogin(parms);
					if (rs == null) {
						map.put("status", false);
						map.put("message", "此用户不存在");
						return map;
					} else {
						String loginStatus = rs.get("STATUS").toString();
						if (null == loginStatus || "".equals(loginStatus)
								|| "1".equals(loginStatus)) {
							map.put("status", false);
							map.put("message", "此用户已停用");
							return map;
						}
						List nodes = loginService.getGns(-1, account_no);
						if (nodes == null || nodes.size() == 0) {
							map.put("status", false);
							map.put("message", "此用户没有权限，请联系管理员");
							return map;
						}
						session.setAttribute("staffId", String.valueOf(rs.get("STAFF_ID")));
						session.setAttribute("staffNo", String.valueOf(rs.get("STAFF_NO")));
						session.setAttribute("staffName",String.valueOf(rs.get("STAFF_NAME")));
						session.setAttribute("passWord", request.getParameter("password"));
						session.setAttribute("areaId", String.valueOf(rs.get("AREA_ID")));
						session.setAttribute("staffNo", String.valueOf(rs.get("STAFF_NO")));
						session.setAttribute("id_number", String.valueOf(rs.get("ID_NUMBER")));
						// session.setAttribute("areaId",76);
						// session.setAttribute("staffNo", "710003");
						session.setAttribute("sonAreaId",String.valueOf(rs.get("SON_AREA_ID")));
						// 获取用户角色
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("STAFF_ID", rs.get("STAFF_ID"));
						List<String> roleList = roleService.getRoleList(params);
						boolean hasLXXJ = false;// 是否有缆线巡检的权限
						boolean hasAXX = false;// 是否有爱巡线的权限
						boolean hasZYXJ = false;// 是否有资源巡检的权限
						boolean hasJFXJ = false;// 是否有机房巡检的权限
						boolean hasCABLE = false;// 是否有光网助手权限
						if (roleList != null && roleList.size() > 0) {
							for (String roleNo : roleList) {
								if (roleNo.startsWith("LXXJ")) {
									hasLXXJ = true;
								} else if (roleNo.startsWith("AXX")) {
									hasAXX = true;
								} else if (roleNo.startsWith("ZYXJ")) {
									hasZYXJ = true;
								} else if (roleNo.startsWith("JFXJ")) {
									hasJFXJ = true;
								}
								else if (roleNo.startsWith("CABLE")) {
									hasCABLE = true;
								}
								if (RoleNo.GLY.equals(roleNo)) {
									session.setAttribute(RoleNo.GLY, true);
									continue;
								}
								if (RoleNo.LXXJ_ADMIN.equals(roleNo)) {
									session.setAttribute(RoleNo.LXXJ_ADMIN, true);
									continue;
								}
								if (RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)) {
									session.setAttribute(RoleNo.LXXJ_ADMIN_AREA, true);
									continue;
								}
								if (RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
									session.setAttribute(RoleNo.LXXJ_ADMIN_SON_AREA, true);
									continue;
								}
								session.setAttribute(roleNo, true);
							}
						}
						session.setAttribute(RoleNo.HAS_LXXJ, hasLXXJ);
						session.setAttribute(RoleNo.HAS_AXX, hasAXX);
						session.setAttribute(RoleNo.HAS_ZYXJ, hasZYXJ);
						session.setAttribute(RoleNo.HAS_JFXJ, hasJFXJ);
						session.setAttribute(RoleNo.HAS_CABLE, hasCABLE);
						if (session.getAttribute(RoleNo.GLY) == null) {
							session.setAttribute(RoleNo.GLY, false);
						}
						if (session.getAttribute(RoleNo.LXXJ_ADMIN) == null) {
							session.setAttribute(RoleNo.LXXJ_ADMIN, false);
						}
						if (session.getAttribute(RoleNo.LXXJ_ADMIN_AREA) == null) {
							session.setAttribute(RoleNo.LXXJ_ADMIN_AREA, false);
						}
						if (session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA) == null) {
							session.setAttribute(RoleNo.LXXJ_ADMIN_SON_AREA, false);
						}
						if (session.getAttribute(RoleNo.CABLE_ADMIN) == null) {//光网助手_省级管理员
							session.setAttribute(RoleNo.CABLE_ADMIN, false);
						}
						if (session.getAttribute(RoleNo.CABLE_ADMIN_AREA) == null) {//光网助手_市级管理员
							session.setAttribute(RoleNo.CABLE_ADMIN_AREA, false);
						}
						if (session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA) == null) {//光网助手_区级管理员
							session.setAttribute(RoleNo.CABLE_ADMIN_SONAREA, false);
						}
						if (session.getAttribute(RoleNo.CABLE_WHY) == null) {//光网助手_维护员
							session.setAttribute(RoleNo.CABLE_WHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_XJY) == null) {//光网助手_巡检员
							session.setAttribute(RoleNo.CABLE_XJY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_SHY) == null) {//光网助手_审核员
							session.setAttribute(RoleNo.CABLE_SHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_TYJDG) == null) {//光网助手_南京统一接单岗
							session.setAttribute(RoleNo.CABLE_TYJDG, false);
						}
						if (session.getAttribute(RoleNo.CABLE_NJ_SHY_A) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_NJ_SHY_A, false);
						}
						if (session.getAttribute(RoleNo.CABLE_NJ_SHY_B) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_NJ_SHY_B, false);
						}
						if (session.getAttribute(RoleNo.CABLE_ZONG_WHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_ZONG_WHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_ZHUANG_WHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_ZHUANG_WHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_WANG_SHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_WANG_SHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_GONG_SHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_GONG_SHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_ZHENG_SHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_ZHENG_SHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_WU_SHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_WU_SHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_WANG_WHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_WANG_WHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_ZHENG_WHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_ZHENG_WHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_WU_WHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_WU_WHY, false);
						}
						if (session.getAttribute(RoleNo.CABLE_GONG_WHY) == null) {//光网助手_南京审核员
							session.setAttribute(RoleNo.CABLE_GONG_WHY, false);
						}
						
						map.put("status", true);
						if(PasswordUtil.checkPassword(request.getParameter("id_card_pwd")))
						{
							map.put("isSimplePwd", true);
						}
						else
						{
							map.put("isSimplePwd", false);
						}
						String id_number = null!=rs.get("ID_NUMBER")?rs.get("ID_NUMBER").toString():"";
						if(null==id_number||""==id_number){
							map.put("isTrueName", false);
						}else{
							map.put("isTrueName", true);
						}
						//更新登录时间
						staffDao.updateLoginDate( String.valueOf(rs.get("STAFF_ID")));
						return map;
					}
				}
				
			} else {
				String loginStatus = rs.get("STATUS").toString();
				if (null == loginStatus || "".equals(loginStatus)
						|| "1".equals(loginStatus)) {
					map.put("status", false);
					map.put("message", "此用户已停用");
					return map;
				}
				List nodes = loginService.getGns(-1, account_no);
				if (nodes == null || nodes.size() == 0) {
					map.put("status", false);
					map.put("message", "此用户没有权限，请联系管理员");
					return map;
				}
				session.setAttribute("staffId", String.valueOf(rs.get("STAFF_ID")));
				session.setAttribute("staffNo", String.valueOf(rs.get("STAFF_NO")));
				session.setAttribute("staffName",String.valueOf(rs.get("STAFF_NAME")));
				session.setAttribute("passWord", request.getParameter("password"));
				session.setAttribute("areaId", String.valueOf(rs.get("AREA_ID")));
				session.setAttribute("staffNo", String.valueOf(rs.get("STAFF_NO")));
				session.setAttribute("id_number", String.valueOf(rs.get("ID_NUMBER")));
				// session.setAttribute("areaId",76);
				// session.setAttribute("staffNo", "710003");
				session.setAttribute("sonAreaId",String.valueOf(rs.get("SON_AREA_ID")));
				// 获取用户角色
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("STAFF_ID", rs.get("STAFF_ID"));
				List<String> roleList = roleService.getRoleList(params);
				boolean hasLXXJ = false;// 是否有缆线巡检的权限
				boolean hasAXX = false;// 是否有爱巡线的权限
				boolean hasZYXJ = false;// 是否有资源巡检的权限
				boolean hasJFXJ = false;// 是否有机房巡检的权限
				boolean hasCABLE = false;// 是否有光网助手权限
				if (roleList != null && roleList.size() > 0) {
					for (String roleNo : roleList) {
						if (roleNo.startsWith("LXXJ")) {
							hasLXXJ = true;
						} else if (roleNo.startsWith("AXX")) {
							hasAXX = true;
						} else if (roleNo.startsWith("ZYXJ")) {
							hasZYXJ = true;
						} else if (roleNo.startsWith("JFXJ")) {
							hasJFXJ = true;
						}
						else if (roleNo.startsWith("CABLE")) {
							hasCABLE = true;
						}
						if (RoleNo.GLY.equals(roleNo)) {
							session.setAttribute(RoleNo.GLY, true);
							continue;
						}
						if (RoleNo.LXXJ_ADMIN.equals(roleNo)) {
							session.setAttribute(RoleNo.LXXJ_ADMIN, true);
							continue;
						}
						if (RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)) {
							session.setAttribute(RoleNo.LXXJ_ADMIN_AREA, true);
							continue;
						}
						if (RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
							session.setAttribute(RoleNo.LXXJ_ADMIN_SON_AREA, true);
							continue;
						}
						session.setAttribute(roleNo, true);
					}
				}
				session.setAttribute(RoleNo.HAS_LXXJ, hasLXXJ);
				session.setAttribute(RoleNo.HAS_AXX, hasAXX);
				session.setAttribute(RoleNo.HAS_ZYXJ, hasZYXJ);
				session.setAttribute(RoleNo.HAS_JFXJ, hasJFXJ);
				session.setAttribute(RoleNo.HAS_CABLE, hasCABLE);
				if (session.getAttribute(RoleNo.GLY) == null) {
					session.setAttribute(RoleNo.GLY, false);
				}
				if (session.getAttribute(RoleNo.LXXJ_ADMIN) == null) {
					session.setAttribute(RoleNo.LXXJ_ADMIN, false);
				}
				if (session.getAttribute(RoleNo.LXXJ_ADMIN_AREA) == null) {
					session.setAttribute(RoleNo.LXXJ_ADMIN_AREA, false);
				}
				if (session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA) == null) {
					session.setAttribute(RoleNo.LXXJ_ADMIN_SON_AREA, false);
				}
				if (session.getAttribute(RoleNo.CABLE_ADMIN) == null) {//光网助手_省级管理员
					session.setAttribute(RoleNo.CABLE_ADMIN, false);
				}
				if (session.getAttribute(RoleNo.CABLE_ADMIN_AREA) == null) {//光网助手_市级管理员
					session.setAttribute(RoleNo.CABLE_ADMIN_AREA, false);
				}
				if (session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA) == null) {//光网助手_区级管理员
					session.setAttribute(RoleNo.CABLE_ADMIN_SONAREA, false);
				}
				if (session.getAttribute(RoleNo.CABLE_WHY) == null) {//光网助手_维护员
					session.setAttribute(RoleNo.CABLE_WHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_XJY) == null) {//光网助手_巡检员
					session.setAttribute(RoleNo.CABLE_XJY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_SHY) == null) {//光网助手_审核员
					session.setAttribute(RoleNo.CABLE_SHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_TYJDG) == null) {//光网助手_南京统一接单岗
					session.setAttribute(RoleNo.CABLE_TYJDG, false);
				}
				if (session.getAttribute(RoleNo.CABLE_NJ_SHY_A) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_NJ_SHY_A, false);
				}
				if (session.getAttribute(RoleNo.CABLE_NJ_SHY_B) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_NJ_SHY_B, false);
				}
				if (session.getAttribute(RoleNo.CABLE_ZONG_WHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_ZONG_WHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_ZHUANG_WHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_ZHUANG_WHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_WANG_SHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_WANG_SHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_GONG_SHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_GONG_SHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_ZHENG_SHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_ZHENG_SHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_WU_SHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_WU_SHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_WANG_WHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_WANG_WHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_ZHENG_WHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_ZHENG_WHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_WU_WHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_WU_WHY, false);
				}
				if (session.getAttribute(RoleNo.CABLE_GONG_WHY) == null) {//光网助手_南京审核员
					session.setAttribute(RoleNo.CABLE_GONG_WHY, false);
				}
				
				map.put("status", true);
				if(PasswordUtil.checkPassword(account_no))
				{
					map.put("isSimplePwd", true);
				}
				else
				{
					map.put("isSimplePwd", false);
				}
				String id_number = null!=rs.get("ID_NUMBER")?rs.get("ID_NUMBER").toString():"";
				if(null==id_number||""==id_number){
					map.put("isTrueName", false);
				}else{
					map.put("isTrueName", true);
				}
				//更新登录时间
				staffDao.updateLoginDate( String.valueOf(rs.get("STAFF_ID")));
				return map;
			}
				
		}
		return map;
	}
}

