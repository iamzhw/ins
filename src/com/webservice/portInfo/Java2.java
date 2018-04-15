package com.webservice.portInfo;



import org.apache.cxf.tools.java2ws.JavaToWS;

import com.webservice.portInfo.service.GetPortInfoService;

public class Java2 {

	private Class<?> className;
	public String[] args1, args2, args3;

	/**
	 * 构造函数
	 * 
	 * @param args
	 *            要生成WSDL的Java类
	 */
	public Java2(Class<?> className) {
		this.className = className;

		// 根据Hello.class生成Hello.wsdl,生成的wsdl文件放在resource目录下
		args1 = new String[] { "-wsdl", "-d", "./resource",
				this.className.getName() };
		// 根据Hello.class生成Hello.wsdl，生成的文件放在根目录下的.example下
		args2 = new String[] { "-wsdl", "-cp", "./example",
				this.className.getName() };
		// 根据Hello.class生成wsdl文件，文件命名为AppFlowService.wsdl，放在根目录下
		args3 = new String[] { "-o", "GetPortInfoService.wsdl", "-wsdl",
				this.className.getName() };
	}

	/**
	 * 构造函数
	 * 
	 * @param className
	 *            要生成WSDL的Java类
	 * @param wsdlFileName
	 *            要生成WSDL的文件名称（不包含扩展名）
	 */
	public Java2(Class<?> className, String wsdlFileName) {
		this.className = className;

		// 根据Hello.class生成Hello.wsdl,生成的wsdl文件放在resource目录下
		args1 = new String[] { "-wsdl", "-d", "./resource",
				this.className.getName() };
		// 根据Hello.class生成Hello.wsdl，生成的文件放在根目录下的.example下
		args2 = new String[] { "-wsdl", "-cp", "./example",
				this.className.getName() };
		// 根据Hello.class生成wsdl文件，文件命名为myHello.wsdl，放在根目录下
		args3 = new String[] { "-o", wsdlFileName + "GetPortInfoService.wsdl", "-wsdl",
				this.className.getName() };
	}

	public void java2(String[] args) {
		JavaToWS javaToWS = new JavaToWS(args);
		try {
			javaToWS.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Java2 java2 = new Java2(GetPortInfoService.class);
		java2.java2(java2.args3);
	}
}