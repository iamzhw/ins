package icom.webservice.client;

import javax.xml.rpc.ServiceException;

public class text {
public static void main(String[] args) {
//	String xml="<?xml version=\"1.0\" encoding=\"gb2312\"?>"+"<IfInfo><sysRoute>GWZS</ sysRoute>"+"<taskType>5</ taskType>"+"<gwMan>杨勇</gwMan>"+"<taskId>10100</taskId>"+"<gwManAccount>yy</gwManAccount>"+"<equCode>250GL.HLJL0/GJ005</equCode>"+"<equName>上林苑GJ001</equName>"+"<chekPortNum>10</chekPortNum>"+"<gwContent>测试</gwContent>"+"</IfInfo>";
	String xml="<?xml version=\"1.0\" encoding=\"gb2312\"?><IfInfo><sysRoute>GWZS</sysRoute><taskType>5</taskType><gwMan>杨勇</gwMan><taskId>10101</taskId><gwManAccount>yy</gwManAccount><equCode>250GL.HLJL0/GJ005</equCode><equName>上林苑GJ001</equName><chekPortNum>10</chekPortNum><gwContent>测试</gwContent></IfInfo>";
	String result="";
	WfworkitemflowLocator locator =new WfworkitemflowLocator();
	try {
		WfworkitemflowSoap11BindingStub stub=(WfworkitemflowSoap11BindingStub)locator.getWfworkitemflowHttpSoap11Endpoint();
		result=stub.outSysDispatchTask(xml);
		System.out.println(xml);
		System.out.println(result);
		//outSysDispatchTask
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
		
}
}
