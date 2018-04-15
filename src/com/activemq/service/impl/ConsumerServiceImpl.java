package com.activemq.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.activemq.common.StaffMsgListener;
import com.activemq.service.ConsumerService;
import com.system.constant.Constants;
import com.system.tool.HttpUtil;

import net.sf.json.JSONObject;
/**
 * JMS消息中间件
 * 消费者Service
 * @author wangxiangyu
 *
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {
	Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);
	
	@Resource
	JmsTemplate jmsTemplate;
	//消费者，单例
	private static MessageConsumer messageConsumer = null; 
	
	@Override
	public void test(String name, String age) {
		
    	String messageStr = 
    	"{\"system_code\": \"USM\", "
    	+ "\"sn\": \"S00110000000784\","
    	+ "\"ref_system\": \"ZHXJ\", "
    	+ "\"event_value\": \"ZHOURUI\","
    	+ "\"event_action\": \"0\","
    	+ "\"event_dt\": \"20180119 11:46:35\", "
    	+ "\"action_status\": \"0\", "
    	+ "\"event_type\": \"2\", "
    	+ "\"event_id\": \"10000000114\", "
    	+ "\"signature\": \"54E8EFCBC4CC461C9D45C479D5D4C4341C80B47B\", "
    	+ "\"city\": \"0511\"}";
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
    	
    	Map<String, Object> queryParam = new HashMap<String, Object>();
    	queryParam.put("signature", param.get("signature"));
    	queryParam.put("system_code", param.get("system_code"));
    	queryParam.put("city", param.get("city"));
    	queryParam.put("staff_code", param.get("event_value"));
    	queryParam.put("event_id", param.get("event_id"));
    	queryParam.put("sn", param.get("sn"));
//    	String result = HttpUtil.sendPost(Constants.UNIFIED_INTERFACE_STAFF_QUERY, queryParam);
//    	JSONObject resObj = JSONObject.fromObject(result);
//    	System.out.println(resObj.toString());
    	
	}
	
	@Override
	public String receive() {
		
		String result = "0";//成功
		
		if(null != messageConsumer) {
			return result;
		}else {
			//创建消息工厂
			ConnectionFactory factory = jmsTemplate.getConnectionFactory();
			Connection connection;
			Session session;
			Destination destination;
			try {
				connection = factory.createConnection();
				connection.start();
				session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
				destination = session.createTopic(jmsTemplate.getDefaultDestinationName());  // 创建连接的消息队列
				messageConsumer = session.createConsumer(destination);// 创建消息消费者
				messageConsumer.setMessageListener(new StaffMsgListener());
			} catch (JMSException e) {
				result = "1";
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
