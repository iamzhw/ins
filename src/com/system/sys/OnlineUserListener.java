package com.system.sys;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.system.service.ParamService;
import com.util.DateUtil;
import com.util.sendMessage.PropertiesUtil;
@Component
public class OnlineUserListener implements HttpSessionListener, HttpSessionAttributeListener {

	public static final Map<String, OnlineUser> ONLINE_USERS = new Hashtable<String, OnlineUser>();
	
	@Autowired
	private ParamService paramService;
	   
	 private static OnlineUserListener onlineUserListener;
/*	 public void setParamService(ParamService paramService) {  
	        this.paramService = paramService;  
	    }  */
	      
	    @PostConstruct  
	    public void init() {  
	    	onlineUserListener = this;  
	    	onlineUserListener.paramService = this.paramService;  
	  
	    }
	 
	public static boolean isLogin(String staffId, String sn){
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("USER_ID", staffId);
		try{
			Map<String,Object> login = onlineUserListener.paramService.getBaseLoginInfo(param);
			//先判断数据库已登录
				if (login != null && "1".equals(String.valueOf(login.get("IS_LOGIN")))
						&& sn.equals(String.valueOf(login.get("SN")))) {
					return true;
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}//再判断缓存中是否存在此账号，
		for(String userId : ONLINE_USERS.keySet()){
			if(userId.equals(staffId)){
				if(ONLINE_USERS.get(userId).getSn().equals(sn.trim()))
					return true;
			}
		}
		
		return false;
	}

	/**
	 * 当前账号在其他手机登录
	 * 
	 * @param staffId
	 * @param sn
	 * @return
	 */
	public static boolean isOtherLogin(String staffId, String sn) {
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("USER_ID", staffId);
		try{
			Map<String,Object> login = onlineUserListener.paramService.getOtherLoginInfo(param);
			//先判断数据库已登录
				if (login != null && "1".equals(String.valueOf(login.get("IS_LOGIN")))
						&& !sn.equals(String.valueOf(login.get("SN")))) {
					return true;
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}//再判断缓存中是否存在此账号，
		/*if (ONLINE_USERS.containsKey(staffId) 
				&& !ONLINE_USERS.get(staffId).getSn().equals(sn.trim())) {
			return true;
		}*/
		return false;
	}

	/**
	 * 手机端用户注销
	 * 
	 * @param userId
	 */
	public static boolean logoutUser(String staffId,String sn){
		if (ONLINE_USERS.containsKey(staffId)) {
			if(null != sn){
				if(ONLINE_USERS.get(staffId).getSn().equals(sn.trim())){
					ONLINE_USERS.remove(staffId);	
				}
			}else{
				ONLINE_USERS.remove(staffId);
			}
			
			return true;
		}
		return false;
	}

	/**
	 * 获取当前用户登录信息
	 * 2017/05/31确定爱巡线已经调用了，但是爱巡线对应获取不到做了从数据库表里判断
	 * @param staffId
	 * @param sn
	 * @return
	 */
	public static OnlineUser getOnlineUser(String staffId) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("USER_ID", staffId);
		Map<String, Object> login = null;

		if (ONLINE_USERS.containsKey(staffId)) {
			return ONLINE_USERS.get(staffId);
		} else {
			try {
				login = onlineUserListener.paramService.getOnlineLoginInfo(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (login != null && "1".equals(String.valueOf(login.get("IS_LOGIN")))) {
				String staffid = String.valueOf(login.get("USER_ID"));
				String OPERATOR_TIME = String.valueOf(login.get("OPERATOR_TIME"));
				String SN = String.valueOf(login.get("SN"));
				OnlineUser onlineuser = new OnlineUser();
				onlineuser.setStaffId(staffid);
				onlineuser.setLoginTime(OPERATOR_TIME);
				onlineuser.setSn(SN);
				return onlineuser;
			}
			return null;

		}

		

	}
	

	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		String sessionId = se.getSession().getId();
		for(String userId : ONLINE_USERS.keySet()){
			if(ONLINE_USERS.get(userId).getSessionId().equals(sessionId)){
				ONLINE_USERS.remove(userId);
				System.out.println("session destroyed! sessionId:" + sessionId
						+ " userId:" + userId);
				break;
			}
		}
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		if("sn".equals(se.getName())){
			HttpSession session = se.getSession();

			// 临时解决手机端Session默认30分钟失效的问题
			Integer inactiveInterval = PropertiesUtil
					.getPropertyInt("inactiveInterval");
			if (inactiveInterval != null) {
				System.out.println("get config property inactiveInterval:"
						+ inactiveInterval);
				session.setMaxInactiveInterval(inactiveInterval);
			}

			String sn = String.valueOf(se.getValue());
			String staffId = String.valueOf(session.getAttribute("staffId"));
			if(ONLINE_USERS.containsKey(staffId)){
				ONLINE_USERS.remove(staffId);
			} 
			OnlineUser onlineUser = new OnlineUser();
			onlineUser.setStaffId(staffId);
			onlineUser.setSn(sn);
			onlineUser.setLoginTime(DateUtil.getCurrentTime());
			onlineUser.setSession(session);
			onlineUser.setSessionId(session.getId());
			ONLINE_USERS.put(staffId, onlineUser);
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
	}
}
