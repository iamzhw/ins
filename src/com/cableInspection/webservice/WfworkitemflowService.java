package com.cableInspection.webservice;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class WfworkitemflowService {
	static Service service;
	static Call call;
	public static String work(String param){
		String rs="";
		Element element=null;
		try {
			service = new Service();
			call=(Call) service.createCall();
			URL url = new URL("http://132.228.198.59:8080/zbiti.servicebus/services/Wfworkitemflow");
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName("http://server.webservice.eoms.zbiti.com","outSysDispatchTask"));
			call.addParameter(new QName("infoString"), XMLType.SOAP_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.SOAP_STRING);
			call.setUseSOAPAction(true);
			Object[] obj = new Object[1];
			obj[0]=param.toString();
			rs=(String) call.invoke(obj);
			Document doc = DocumentHelper.parseText(rs);
			Element root = doc.getRootElement();
			boolean flag = root.element("IfResult").toString().equals("0")?true:false;
			if(flag){
				element = root.element("eomsTaskId");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return element.toString();
	}
}
