package com.webservice.changeCoordinate.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName = "changeCooService")
public interface ChangeCooService {
	
	/**
	 *获取巡线点列表
	 */
	@WebMethod(operationName = "qrySiteList")
	public String qrySiteList();
	
	/**
	 *获取设施点列表
	 */
	@WebMethod(operationName = "qryStepEquipList")
	public String qryStepEquipList();
	
	/**
	 *转换巡线点坐标 
	 */
	@WebMethod(operationName = "changeSite")
	public String changeSite(String xml);
	
	/**
	 *转换设施点坐标 
	 */
	@WebMethod(operationName = "changeStepEquip")
	public String changeStepEquip(String xml);

}
