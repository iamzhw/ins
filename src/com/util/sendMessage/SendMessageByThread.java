package com.util.sendMessage;

import java.net.URL;
import org.codehaus.xfire.client.Client;


public class SendMessageByThread {

	// 线程池
	/*@Autowired
	@Qualifier("taskExecutor")
	public TaskExecutor taskExecutor;

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}*/

	public SendMessageByThread(String reqXml, String propertyName) {

		try {
			// ApplicationContext ctx = new
			// ClassPathXmlApplicationContext("/spring/applicationContext.xml");
			// TaskExecutor taskExecutor =
			// (TaskExecutor)ctx.getBean("taskExecutor");
			// taskExecutor.execute(new SendMessageThread(reqXml,
			// propertyName));
			SendMessageThread thread = new SendMessageThread(reqXml,propertyName);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class SendMessageThread extends Thread {

		private String reqXml;
		private String propertyName;

		public SendMessageThread(String reqXml, String propertyName) {
			super();
			this.reqXml = reqXml;
			this.propertyName = propertyName;

		}

		@Override
		public void run() {
			Client client;
			// 获取地址
			try {
				client = new Client(new URL(
						PropertiesUtil.getProperty(propertyName)));
				Object[] results = client.invoke("getMessage",
						new Object[] { reqXml });
				//System.out.println((String) results[0]);
			} catch (Exception e) {

				e.printStackTrace();

			}
		}
	}
	/*
	 * public static void main(String[] args) { new
	 * SendMessageByThread("1","sendMessageurl"); }
	 */
}
