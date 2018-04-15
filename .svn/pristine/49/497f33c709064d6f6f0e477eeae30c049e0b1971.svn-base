package com.activemq.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import com.activemq.dao.ConsumerDao;
import com.system.constant.Constants;
import com.system.sys.EventResult;
import com.system.tool.HttpUtil;
import com.util.SHAHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * JMS消息中间件
 * 监听器
 * @author wangxiangyu
 *
 */
@Service
@SuppressWarnings("all")
public class StaffMsgListener implements MessageListener{
    Logger logger = LoggerFactory.getLogger(StaffMsgListener.class);
    
    private static String EVENT_TYPE_ROLE = "0";//角色
    private static String EVENT_TYPE_POST = "1";//岗位
    private static String EVENT_TYPE_STAFF = "2";//工号
    private static String EVENT_TYPE_ORG = "3";//组织
    
    private static String EVENT_ACTION_ADD = "0";//新增
    private static String EVENT_ACTION_UPDATE = "1";//修改
    private static String EVENT_ACTION_DELETE = "2";//删除
    private static String EVENT_ACTION_STATUS_UPDATE = "3";//状态变更
    
    private static String ACTION_STATUS_NORMAL = "0";//正常
    private static String ACTION_STATUS_BLOCKED = "1";//冻结
    private static String ACTION_STATUS_UNBLOCKED = "2";//解冻
    private static String ACTION_STATUS_CANCEL = "3";//注销
    
    private MessageConverter staffMessageConverter;

    @Override
    public void onMessage(Message message) {
    	
    	String messageStr = "";
    	try {
    		messageStr = ((TextMessage)message).getText();
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
    	System.out.println("收到的文本消息："+messageStr);

    	//获取消息内容
    	JSONObject jsonMsg = JSONObject.fromObject(messageStr);
    	String signature = jsonMsg.getString("signature");
    	String sn = jsonMsg.getString("sn");
    	String event_id = jsonMsg.getString("event_id");
    	String city = jsonMsg.getString("city");
    	String system_code = jsonMsg.getString("system_code");
    	String ref_system = jsonMsg.getString("ref_system");
    	String event_type = jsonMsg.getString("event_type");
    	String event_action = jsonMsg.getString("event_action");
    	String action_status = null==jsonMsg.get("action_status")?"":jsonMsg.getString("action_status");
    	String event_dt = jsonMsg.getString("event_dt");
    	String event_value = jsonMsg.getString("event_value");
    	//封装参数
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("signature", signature);//签名
    	param.put("sn", sn);//流水号
    	param.put("event_id", event_id);//事件编码
    	param.put("city", city);//事件发生的地区
    	param.put("system_code", system_code);//事件发生的系统
    	param.put("ref_system", ref_system);//事件目标系统
    	param.put("event_type", event_type);//事件类型：0角色，1岗位，2工号，3组织
    	param.put("event_action", event_action);//新增，修改，删除，状态变更：0，1，2，3
    	param.put("action_status", action_status);//event_action=3时，此标志位必传：正常默认为0,冻结为1、解冻为2、注销为3
    	param.put("event_dt", event_dt);//时间:20170101 22:11:22
    	param.put("event_value", event_value);//角色、岗位、工号、组织对应编码
    	/**
    	 * 插入数据库
    	 * activemq的监听器中不能使用注解方式获取到bean！
    	 * 所以在web.xml中配置spring的listener，提供getBean方法
    	 */
    	ConsumerDao consumerDao = InitComponent.getBean("ConsumerDao");
    	consumerDao.saveMessage(param);
    	//调用接口
    	if(EVENT_TYPE_ROLE.equals(event_type)) {//角色
    		queryRole(param);
    	}
    	if(EVENT_TYPE_STAFF.equals(event_type)) {//工号
    		queryStaff(param);
    	}
    	if(EVENT_TYPE_ORG.equals(event_type)) {//行政组织
    		queryOrg(param);
    	}
    	
        
        
    }
    
    
    //工号查询
    public void queryStaff(Map<String, String> param) {
    	
    	ConsumerDao consumerDao = InitComponent.getBean("ConsumerDao");
    	
    	Map<String, Object> queryParam = new HashMap<String, Object>();
    	
    	
    	queryParam.put("signature", getSignature(param));
    	queryParam.put("system_code", param.get("system_code"));
    	queryParam.put("city", param.get("city"));
    	queryParam.put("staff_code", param.get("event_value"));
    	queryParam.put("event_id", param.get("event_id"));
    	queryParam.put("sn", param.get("sn"));
    	String result = HttpUtil.sendPost(Constants.UNIFIED_INTERFACE_STAFF_QUERY, queryParam);
    	JSONObject resObj = JSONObject.fromObject(result);
    	
    	//获取数据
    	JSONObject dataObject = resObj.getJSONObject("data");
    	
    	//封装参数
    	Map<String, String> resultParam = new HashMap<String, String>();
    	resultParam.put("staff_code", null==dataObject.get("staff_code")?"":dataObject.getString("staff_code"));
    	resultParam.put("staff_account", null==dataObject.get("staff_account")?"":dataObject.getString("staff_account"));
    	resultParam.put("name", null==dataObject.get("name")?"":dataObject.getString("name"));
    	resultParam.put("pwd", null==dataObject.get("pwd")?"":dataObject.getString("pwd"));
    	resultParam.put("sex", null==dataObject.get("sex")?"":dataObject.getString("sex"));
    	resultParam.put("telephone", null==dataObject.get("telephone")?"":dataObject.getString("telephone"));
    	resultParam.put("card_type", null==dataObject.get("card_type")?"":dataObject.getString("card_type"));
    	resultParam.put("idcard", null==dataObject.get("idcard")?"":dataObject.getString("idcard"));
    	resultParam.put("email", null==dataObject.get("email")?"":dataObject.getString("email"));
    	
    	String state = null==dataObject.get("state")?"":dataObject.getString("state");
    	
    	
    	if("冻结".equals(state)) {
    		state = ACTION_STATUS_BLOCKED;//冻结
    	}else if("注销".equals(state)) {
    		state = ACTION_STATUS_CANCEL;//注销
    	}else {
    		state = ACTION_STATUS_NORMAL;//正常
    	}
    	
    	resultParam.put("state", state);
    	resultParam.put("is_smz", null==dataObject.get("is_smz")?"":dataObject.getString("is_smz"));
    	resultParam.put("t_staff_id", null==dataObject.get("t_staff_id")?"":dataObject.getString("t_staff_id"));
    	
    	String city = null==dataObject.get("city")?"":dataObject.getString("city");
    	if(!"".equals(city)) {//根据地市编码查地市id，新增字段TYGH_CODE
    		Map<String, String> cityMap = consumerDao.queryAreaByCity(city);
        	resultParam.put("city", cityMap.get("AREA_ID"));
    	}else {
    		resultParam.put("city", "");
    	}
    	
    	resultParam.put("system_code", null==dataObject.get("system_code")?"":dataObject.getString("system_code"));
    	resultParam.put("bloc_code", null==dataObject.get("bloc_code")?"":dataObject.getString("bloc_code"));
    	resultParam.put("oa_code", null==dataObject.get("oa_code")?"":dataObject.getString("oa_code"));
    	resultParam.put("wechat_code", null==dataObject.get("wechat_code")?"":dataObject.getString("wechat_code"));
    	resultParam.put("bestpay_code", null==dataObject.get("bestpay_code")?"":dataObject.getString("bestpay_code"));
    	resultParam.put("employment_mode", null==dataObject.get("employment_mode")?"":dataObject.getString("employment_mode"));
    	//岗位和组织做一次叉乘摊平
    	JSONArray postOrgArray = dataObject.getJSONArray("post_org_list");
    	String adm_org_code = "";
    	String adm_org_name = "";
    	String post_type_code = "";
    	String post_type_name = "";
    	String busi_org_tree_code = "";
    	String busi_org_code = "";
    	
    	String role_code = "";
    	String priv_code = "";
    	String staff_priv_id = "";
    	String action = "";
    	
    	for(int i=0;i<postOrgArray.size();i++) {
    		JSONObject postOrgObj = (JSONObject) postOrgArray.get(i);
    		adm_org_code += null==postOrgObj.get("adm_org_code")?"":postOrgObj.getString("adm_org_code")+",";
    		adm_org_name += null==postOrgObj.get("adm_org_name")?"":postOrgObj.getString("adm_org_name")+",";
    		post_type_code += null==postOrgObj.get("post_type_code")?"":postOrgObj.getString("post_type_code")+",";
    		post_type_name += null==postOrgObj.get("post_type_name")?"":postOrgObj.getString("post_type_name")+",";
    		busi_org_tree_code += null==postOrgObj.get("busi_org_tree_code")?"":postOrgObj.getString("busi_org_tree_code")+",";
    		busi_org_code += null==postOrgObj.get("busi_org_code")?"":postOrgObj.getString("busi_org_code")+",";
    		//角色权限
    		JSONArray rolePrivArray = postOrgObj.getJSONArray("role_priv_list");
    		for(int j=0;j>rolePrivArray.size();j++) {
    			JSONObject rolePrivObj = (JSONObject) rolePrivArray.get(j);
    			role_code = null==postOrgObj.get("role_code")?"":rolePrivObj.getString("role_code")+",";
    			priv_code = null==postOrgObj.get("priv_code")?"":rolePrivObj.getString("priv_code")+",";
    			staff_priv_id = null==postOrgObj.get("staff_priv_id")?"":rolePrivObj.getString("staff_priv_id")+",";
    			action = null==postOrgObj.get("action")?"":rolePrivObj.getString("action")+",";
    		}
    	}
    	//扩展字段
    	JSONArray extendInfoArray = dataObject.getJSONArray("extend_info");
    	for(int i=0;i<extendInfoArray.size();i++) {
    		//字段未知
    	}
    	
    	resultParam.put("adm_org_code", adm_org_code.substring(0, adm_org_code.length()-1));
    	resultParam.put("adm_org_name", adm_org_name.substring(0, adm_org_name.length()-1));
    	resultParam.put("post_type_code", post_type_code.substring(0, post_type_code.length()-1));
    	resultParam.put("post_type_name", post_type_name.substring(0, post_type_name.length()-1));
    	resultParam.put("busi_org_tree_code", busi_org_tree_code.substring(0, busi_org_tree_code.length()-1));
    	resultParam.put("busi_org_code", busi_org_code.substring(0, busi_org_code.length()-1));
    	resultParam.put("role_code", role_code.substring(0, role_code.length()-1));
    	resultParam.put("priv_code", priv_code.substring(0, priv_code.length()-1));
    	resultParam.put("staff_priv_id", staff_priv_id.substring(0, staff_priv_id.length()-1));
    	resultParam.put("action", action.substring(0, action.length()-1));
    	
    	//判断身份证是否已存在
    	List<Map<String, String>> isExist = consumerDao.isExistByIdcard(resultParam);
    	if(null == isExist || isExist.size()<1) {
    		consumerDao.saveStaff(resultParam);
    	}else {
    		consumerDao.updateStaff(resultParam);
    	}
    	
    }
    
    
    /**
     * 行政组织查询
     * @param param
     */
    public void queryOrg(Map<String, String> param) {
    	param.put("org_city", param.get("city"));
    	param.put("org_code", param.get("event_value"));
    	param.put("direction", param.get("-1"));//查询方向：1向上，0向下，-1仅查询当前节点信息，默认-1
    	param.put("post_type_code", "");//岗位类型
    	param.put("has_humen", "0");//是否包含人员信息:1:是 0：否,默认0
    	param.put("isfile", "0");//是否文件下发:1:是 0：否,默认0
//    	String result = HttpUtil.sendPost(Constants.UNIFIED_INTERFACE_ADMORG_QUERY, param, "utf-8");
//    	System.out.println("queryOrg");
    }
    
    /**
     * 角色查询
     * @param param
     */
    public void queryRole(Map<String, String> param) {
    	param.put("role_code", param.get("event_value"));
    	param.put("system", param.get("system_code"));
    	param.put("area", param.get("city"));
//    	String result = HttpUtil.sendPost(Constants.UNIFIED_INTERFACE_ROLE_QUERY, param, "utf-8");
//    	System.out.println("queryRole");
    }
    
    
    
    
    
    
    
    


    
    /**
     * 根据规则获取签名串
     * @param param
     * @return
     */
    public String getSignature(Map<String, String> param) {
    	//签名串
    	String signature = "";
    	//流水号
    	String sn = param.get("sn").toString();
    	//参数列表
    	StringBuffer params = new StringBuffer();
    	params.append(param.get("sn"));
    	params.append(param.get("event_id"));
    	params.append(param.get("system_code"));
    	params.append(param.get("city"));
    	params.append(param.get("event_value"));
    	//综合巡检key
    	String key = "ZHXJ3512F72A53C94D8CB06B0A9434525211";
    	
    	try {
    		signature = SHAHelper.SHADigest(sn+params.toString()+key);
		} catch (Exception e) {
			System.out.println("获取签名串失败！");
			e.printStackTrace();
		}
    	return signature;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public void setStaffMessageConverter(MessageConverter staffMessageConverter) {
        this.staffMessageConverter = staffMessageConverter;
    }
    
}
