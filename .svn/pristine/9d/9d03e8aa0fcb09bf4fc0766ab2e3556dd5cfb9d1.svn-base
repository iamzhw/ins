package com.webservice.changeCoordinate.serviceimpl;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.webservice.changeCoordinate.dao.ChangeCooDao;
import com.webservice.changeCoordinate.service.ChangeCooService;

@WebService(serviceName = "changeCooService")
public class ChangeCooServiceImpl implements ChangeCooService {
	
	@Resource
	private ChangeCooDao changeCoodao;

	@Override
	public String qrySiteList() {
		String resultCode = "000";
		String codeDesc = "";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("coordinate_type", 2);				//2:查询坐标标识为GPS坐标的点
		List<Map> siteList = changeCoodao.querySiteList(param);
		StringBuffer responseStr = new StringBuffer();
		responseStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		responseStr.append("<returnInfo>");
		responseStr.append("<resultCode>").append(resultCode).append("</resultCode>");
		responseStr.append("<siteList>");
		for(int i=0; i<siteList.size(); i++){
			Map siteInfo = siteList.get(i);
			responseStr.append("<siteInfo>");
			responseStr.append("<site_id>").append(siteInfo.get("SITE_ID")).append("</site_id>");	
			responseStr.append("<longitude>").append(siteInfo.get("LONGITUDE")).append("</longitude>");	
			responseStr.append("<latitude>").append(siteInfo.get("LATITUDE")).append("</latitude>");	
			responseStr.append("</siteInfo>");
		}
		responseStr.append("</siteList>");
		responseStr.append("<codeDesc>").append(codeDesc).append("</codeDesc>");
		responseStr.append("</returnInfo>");
		return responseStr.toString();
	}

	@Override
	public String qryStepEquipList() {
		String resultCode = "000";
		String codeDesc = "";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("coordinate_type", 2);					//2:查询坐标标识为GPS坐标的点
		List<Map> stepEquipList = changeCoodao.queryStepEquipList(param);
		StringBuffer responseStr = new StringBuffer();
		responseStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		responseStr.append("<returnInfo>");
		responseStr.append("<resultCode>").append(resultCode).append("</resultCode>");
		responseStr.append("<stepEquipList>");
		for(int i=0; i<stepEquipList.size(); i++){
			Map stepEquipInfo = stepEquipList.get(i);
			responseStr.append("<stepEquipInfo>");
			responseStr.append("<equip_id>").append(stepEquipInfo.get("EQUIP_ID")).append("</equip_id>");	
			responseStr.append("<longitude>").append(stepEquipInfo.get("LONGITUDE")).append("</longitude>");	
			responseStr.append("<latitude>").append(stepEquipInfo.get("LATITUDE")).append("</latitude>");	
			responseStr.append("</stepEquipInfo>");
		}
		responseStr.append("</stepEquipList>");
		responseStr.append("<codeDesc>").append(codeDesc).append("</codeDesc>");
		responseStr.append("</returnInfo>");
		return responseStr.toString();
	
	}

	@Override
	public String changeSite(String xml) {
		System.out.println(xml);
		Document dom = null;
		Element reqInfo = null;
		String resultCode = "000";
		String codeDesc = "";
		try {
			dom = DocumentHelper.parseText(xml);
			reqInfo = dom.getRootElement();
			Element siteList = reqInfo.element("siteList");
			Iterator iter = siteList.elementIterator();
			while(iter.hasNext()){
				Element siteInfo = (Element)iter.next();
				Map param = new HashMap();
				String site_id = siteInfo.elementText("site_id");
				String longitude = siteInfo.elementText("longitude");
				String latitude = siteInfo.elementText("latitude");
				param.put("site_id", site_id);
				param.put("longitude", longitude);
				param.put("latitude", latitude);
				param.put("coordinate_type", 1);		//1:坐标标识改为百度坐标
				changeCoodao.updateSite(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			codeDesc = e.getMessage();
			StringBuffer returnInfo = new StringBuffer();
			returnInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			returnInfo.append("<returnInfo>");
			returnInfo.append("<resultCode>").append("001").append("</resultCode>");
			returnInfo.append("<codeDesc>").append("失败了").append("</codeDesc>");
			returnInfo.append("</returnInfo>");
			return returnInfo.toString();
		}
		StringBuffer returnInfo = new StringBuffer();
		returnInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		returnInfo.append("<returnInfo>");
		returnInfo.append("<resultCode>").append(resultCode).append("</resultCode>");
		returnInfo.append("<codeDesc>").append(codeDesc).append("</codeDesc>");
		returnInfo.append("</returnInfo>");
		return returnInfo.toString();
	
	
	}

	@Override
	public String changeStepEquip(String xml) {
		System.out.println(xml);
		Document dom = null;
		Element reqInfo = null;
		String resultCode = "000";
		String codeDesc = "";
		try {
			dom = DocumentHelper.parseText(xml);
			reqInfo = dom.getRootElement();
			Element stepEquipList = reqInfo.element("stepEquipList");
			Iterator iter = stepEquipList.elementIterator();
			while(iter.hasNext()){
				Element stepEquipInfo = (Element)iter.next();
				Map param = new HashMap();
				String equip_id = stepEquipInfo.elementText("equip_id");
				String longitude = stepEquipInfo.elementText("longitude");
				String latitude = stepEquipInfo.elementText("latitude");
				param.put("equip_id", equip_id);
				param.put("longitude", longitude);
				param.put("latitude", latitude);
				param.put("coordinate_type", 1);		//1:坐标标识改为百度坐标
				changeCoodao.updateStepEquip(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			codeDesc = e.getMessage();
			StringBuffer returnInfo = new StringBuffer();
			returnInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			returnInfo.append("<returnInfo>");
			returnInfo.append("<resultCode>").append("001").append("</resultCode>");
			returnInfo.append("<codeDesc>").append("失败了").append("</codeDesc>");
			returnInfo.append("</returnInfo>");
			return returnInfo.toString();
		}
		StringBuffer returnInfo = new StringBuffer();
		returnInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		returnInfo.append("<returnInfo>");
		returnInfo.append("<resultCode>").append(resultCode).append("</resultCode>");
		returnInfo.append("<codeDesc>").append(codeDesc).append("</codeDesc>");
		returnInfo.append("</returnInfo>");
		return returnInfo.toString();
	
	
	}
	
}
