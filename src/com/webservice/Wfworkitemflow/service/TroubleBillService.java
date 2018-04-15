package com.webservice.Wfworkitemflow.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName = "TroubleBillService")
public interface TroubleBillService {
	@WebMethod(operationName = "troubleBillReceipt")
	public String troubleBillReceipt(String xml);

}
