package com.activemq.common;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class InitComponent implements ServletContextListener,ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		InitComponent.applicationContext=applicationContext;
	}

	/**
	 * 程序运行时即初始化activemq消费组件
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
//		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://132.252.3.22:61616");
//		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
//		Connection connection;
//		Session session;
//		Destination destination;
//		MessageConsumer messageConsumer;
//		try {
//			connection = factory.createConnection();
//			connection.start();
//			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
//			destination = session.createTopic("ZHXJ_QUEUE");  // 创建连接的消息队列
//			messageConsumer = session.createConsumer(destination);// 创建消息消费者
//			messageConsumer.setMessageListener(new StaffMsgListener());
//		} catch (JMSException e) {
//			e.printStackTrace();
//		}
		
		
	}
	/**
	 * activemq监听器中无法使用@autowired注解注入bean
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {  
        checkApplicationContext();  
        return (T) applicationContext.getBean(name);  
    }
	
	private static void checkApplicationContext() {  
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入");  
        }  
    } 

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
