package com.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import net.sf.json.JSONArray;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.inspecthelper.model.ZgResult;

public class SyncGJFromZyws {
	public final static String ZWYS_URL = "http://" + "132.228.24.6:8083"
			+ "/SZ_ZYWS/services/zywsService?wsdl";
	
	/**
	 * 
	 * @Function: com.zbiti.module.inspecthelper.webService.SyncGJFromZyws.returnZgResult
	 * @Description:光交动态整改结果返回给资源卫士
	 *
	 * @param list
	 *
	 * @date:2014-10-24 下午2:00:56
	 *
	 * @Modification History:
	 * @date:2014-10-24     @author:Administrator     create
	 */
	public static void returnZgResult(List<Map> list) {
		try {
			
			if(list ==null ||list.size() <1) return;
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(ZWYS_URL);

			// 使用RPC方式调用WebService
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			// 确定目标服务地址
			options.setTo(targetEPR);
			// 确定调用方法
			options.setAction("urn:returnZgResult");

			QName qname = new QName("http://serviceimpl.system.com", "x");
			// 指定方法的参数值
			ZgResult zgResult = null;
			List<ZgResult> paramList = new ArrayList<ZgResult>();
			Map map = null;
			for (int i = 0; i < list.size(); i++) {
				zgResult = new ZgResult();
				map = list.get(0);
				
				zgResult.setEqpId(map.get("EQPID").toString());
				zgResult.setGroupId(map.get("GROUPID").toString());
				zgResult.setPortId(map.get("PORTID").toString());
				zgResult.setDescription(map.get("REMARKS").toString() == null?"":map.get("REMARKS").toString());
				zgResult.setFinishTime(map.get("END_TIME").toString());
				paramList.add(zgResult);
			}
			
			String data = JSONArray.fromObject(paramList).toString();

			Object[] parameters = new Object[] { data };

			// 指定方法返回值的数据类型的Class对象
			Class[] returnTypes = new Class[] { String.class };

			Object[] response = serviceClient.invokeBlocking(qname, parameters,
					returnTypes);

			String res = (String) response[0];

			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
