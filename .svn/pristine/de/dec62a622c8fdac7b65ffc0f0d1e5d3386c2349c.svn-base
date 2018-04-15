package com.inspecthelper.model;



import java.io.StringReader;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis.message.SOAPHeaderElement;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import com.inspecthelper.service.OIPServiceSoapBindingStub;


/**
 * OssAbitiyAction调用方法
 * 软创提供
 * @author chendingqiang
 * @Modify Fanjiwei
 * @create_date 2012-04-16
 *
 */

public class CallEsb {
	private static OIPServiceSoapBindingStub stubService = null;
	/*public static final String ESB_SERVICE_TEST_ADDRESS="http://132.228.183.2:9000/proxy";//ESB测试环境
	public static final String ESB_SERVICE_ADDRESS="http://132.228.224.35:80/proxy";//ESb正式环境
	public static final String GIS_namespace="http://ResGraphInterface.gis.ztesoft.com/";//GIS环境
*/	
	Logger logger = Logger.getLogger(CallEsb.class);
	//http://webservice.rme.module.resmaster.ztesoft.com/
	/**
	 * @author Chendingqiang
	 * @modify Fanjiwei
	 * @param obj入参信息
	 * @param method OSS提供的接口方法名称
	 * @param isXML isXML入参是否XML
	 * @return 接口结果
	 * @throws Exception
	 */
	public String getCallEsb(Object[] obj,String method,String namespace,boolean isXML) throws Exception{
		URL url = new URL(PadUrl.ESB_SERVICE_ADDRESS);
		stubService = new OIPServiceSoapBindingStub(method,url,namespace,null);
		stubService.setHeader(new SOAPHeaderElement(buildHeaderInfo(method)));
		String[] xml=new String[]{"reqXML"};
		if(!isXML){
		xml=null;	
		}
		if(method.equals("autoNaming")){
			xml= new String[] {"param","xmlParam","partitionId"};
		}
		String resultXML = stubService.methodInvoke(obj,xml);

		logger.debug("esb returnXML --- "+resultXML);
		return resultXML;
	}
	/**
	 * @author Chendingqiang
	 * @modify Fanjiwei
	 * @param obj入参信息
	 * @param method OSS Gis提供的接口方法名称
	 * @param isXML isXML入参是否XML
	 * @return 接口结果
	 * @throws Exception
	 */
	public String getCallEsbForGis(Object[] obj,String method) throws Exception{
		URL url = new URL(PadUrl.ESB_SERVICE_ADDRESS);
		String namespace=PadUrl.GIS_namespace;
		stubService = new OIPServiceSoapBindingStub(method,url,namespace,null);
		stubService.setHeader(new SOAPHeaderElement(buildHeaderInfo(method)));
		String resultXML = stubService.methodInvoke(obj,null);
		logger.debug(resultXML);
		return resultXML;
	}

	/**
	 * 
	 * 获取soap头，同步使用
	 * @author Ever
	 * 
	 * @param methodCode
	 * @return
	 * @throws Exception
	 */
	public static org.w3c.dom.Element buildHeaderInfo(String method)
			throws Exception {

		//以下四个变量值最好存储在调用者的某张表中，方便管理
		String operFlag=method;//服务编码,esb提供
		String sender="32.1121";//消费者系统编码
		String servCode="32.1103";//服务提供者系统编码
		String servTestFlag="0";//是否测试标志，保留字段默认请提供0

		String str = "<Esb>" + " <Route>" + " <Sender>" + sender + "</Sender>"
				+ " <ServCode>" + servCode + "." + operFlag + "</ServCode>"
				+ " <MsgId>" + servCode + "_" + System.currentTimeMillis()
				+ "</MsgId>" + " <TransId/>" + " <Version/>" + " <AuthCode/>"
				+ " <AuthType/>" + " <Flag>1</Flag>" + " <MsgType/>"
				+ " <CarryType>0</CarryType>" + " <ServTestFlag>"
				+ servTestFlag + "</ServTestFlag>" + " </Route>"
				+ " <Business>" + " <BizDomain></BizDomain>"
				+ " <BizFirstType></BizFirstType>"
				+ " <BizSecType></BizSecType>" + " </Business>" + "</Esb>";

		StringReader sr = new StringReader(str);
		InputSource is = new InputSource(sr);
		return (org.w3c.dom.Element) DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is).getElementsByTagName("Esb")
				.item(0);
	}

	/**
	 * 异步拼取soap头的方式，soap头必须和body体里的消息要一致
	 */
	public static org.w3c.dom.Element buildHeaderInfo2(String methodCode,
			Map esbRouteMap) throws Exception {
		String sender = String.valueOf(esbRouteMap.get("SENDER"));
		String servCode = String.valueOf(esbRouteMap.get("SERVCODE"));
		String msgId = String.valueOf(esbRouteMap.get("MSGID"));
		String servTestFlag = String.valueOf(esbRouteMap.get("SERVTESTFLAG"));
		String time = String.valueOf(esbRouteMap.get("TIME"));
		String str = "<Esb>" + " <Route>" + " <Sender>" + sender + "</Sender>"
				+ " <ServCode>" + servCode + "</ServCode>" + " <MsgId>" + msgId
				+ "</MsgId>" + "<TIME>" + time + "</TIME>" + " <TransId/>"
				+ " <Version/>" + " <AuthCode/>" + " <AuthType/>"
				+ " <Flag>1</Flag>" + " <MsgType/>"
				+ " <CarryType>0</CarryType>" + " <ServTestFlag>"
				+ servTestFlag + "</ServTestFlag>" + " </Route>"
				+ " <Business>" + " <BizDomain></BizDomain>"
				+ " <BizFirstType></BizFirstType>"
				+ " <BizSecType></BizSecType>" + " </Business>" + "</Esb>";

		StringReader sr = new StringReader(str);
		InputSource is = new InputSource(sr);
		return (org.w3c.dom.Element) DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is).getElementsByTagName("Esb")
				.item(0);
	}
}
