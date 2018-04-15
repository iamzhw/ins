package com.linePatrol.task;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cableCheck.service.QuartzJobService;
import com.linePatrol.localwebservice.ChangeCooService;
import com.linePatrol.localwebservice.ChangeCooService_Service;
import com.linePatrol.util.DateUtil;
import com.util.Gps2BaiDu;
import com.util.StringUtil;
import com.util.sendMessage.PropertiesUtil;

/**
 * 巡线点、设施点定时任务
 * 
 * @author lipengfei
 * 
 */
@Component
public class linePatrolTask {
	
	Logger logger = Logger.getLogger(linePatrolTask.class);
	
	/**
	 * 巡线点GPS坐标转换为百度坐标
	 */
	@Scheduled(cron = "0 10 * * * ?")
	public void changeSite() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				if(addr.toString().indexOf("132") == -1){
					ChangeCooService_Service changeCooServices = new ChangeCooService_Service();
					ChangeCooService changeCoo = changeCooServices.getChangeCooServicePort();
					//1.调用后台webservice接口，获取坐标集合
					String returnXml = changeCoo.qrySiteList();
					Document dom = null;
					Element reqInfo = null;
					try {
						dom = DocumentHelper.parseText(returnXml);
						reqInfo = dom.getRootElement();
						Element siteList = reqInfo.element("siteList");
						Iterator iter = siteList.elementIterator();
						List<Map> siteLists = new ArrayList<Map>();
						while(iter.hasNext()){
							Element siteInfo = (Element)iter.next();
							Map site = new HashMap();
							String site_id = siteInfo.elementText("site_id");
							String longitude = siteInfo.elementText("longitude");
							String latitude = siteInfo.elementText("latitude");
							site.put("SITE_ID", site_id);
							site.put("LONGITUDE", longitude);
							site.put("LATITUDE", latitude);
							siteLists.add(site);
						}
						if(siteLists.isEmpty()){
							logger.info("获取到的坐标列表为空");
							return;
						}
						
						List<Map> requestList = new ArrayList<Map>();				//需要发送到后台的坐标集合
						//2.坐标转换
						for(int i=0; i<siteLists.size(); i++){
							Map map = siteLists.get(i);
							Gps2BaiDu.convert(map);
							if("0".equals(map.get("status"))){						//筛选转换成功的坐标
								requestList.add(map);
							}
						}
						
						//3.调用后台webservice接口更改后台坐标数据
						StringBuffer requestStr = new StringBuffer();
						requestStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						requestStr.append("<reqInfo>");
						requestStr.append("<siteList>");
						for(int i=0; i<requestList.size(); i++){
							Map siteInfo = requestList.get(i);
							requestStr.append("<siteInfo>");
							requestStr.append("<site_id>").append(siteInfo.get("SITE_ID")).append("</site_id>");	
							requestStr.append("<longitude>").append(siteInfo.get("LONGITUDE")).append("</longitude>");	
							requestStr.append("<latitude>").append(siteInfo.get("LATITUDE")).append("</latitude>");	
							requestStr.append("</siteInfo>");
						}
						requestStr.append("</siteList>");
						requestStr.append("</reqInfo>");
						logger.info(changeCoo.changeSite(requestStr.toString()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 设施点GPS坐标转换为百度坐标
	 */
	@Scheduled(cron = "0 20 * * * ?")
	public void changeStepEquip() {
		
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				if(addr.toString().indexOf("132") == -1){

					ChangeCooService_Service changeCooServices = new ChangeCooService_Service();
					ChangeCooService changeCoo = changeCooServices.getChangeCooServicePort();
					//1.调用后台webservice接口，获取坐标集合
					String returnXml = changeCoo.qryStepEquipList();
					Document dom = null;
					Element reqInfo = null;
					try {
						dom = DocumentHelper.parseText(returnXml);
						reqInfo = dom.getRootElement();
						Element stepEquipList = reqInfo.element("stepEquipList");
						Iterator iter = stepEquipList.elementIterator();
						List<Map> stepEquipLists = new ArrayList<Map>();
						while(iter.hasNext()){
							Element stepEquipInfo = (Element)iter.next();
							Map site = new HashMap();
							String equip_id = stepEquipInfo.elementText("equip_id");
							String longitude = stepEquipInfo.elementText("longitude");
							String latitude = stepEquipInfo.elementText("latitude");
							site.put("EQUIP_ID", equip_id);
							site.put("LONGITUDE", longitude);
							site.put("LATITUDE", latitude);
							stepEquipLists.add(site);
						}
						if(stepEquipLists.isEmpty()){
							logger.info("获取到的坐标列表为空");
							return;
						}
						
						List<Map> requestList = new ArrayList<Map>();				//需要发送到后台的坐标集合
						//2.坐标转换
						for(int i=0; i<stepEquipLists.size(); i++){
							Map map = stepEquipLists.get(i);
							Gps2BaiDu.convert(map);
							if("0".equals(map.get("status"))){						//筛选转换成功的坐标
								requestList.add(map);
							}
						}
						
						//3.调用后台webservice接口更改后台坐标数据
						StringBuffer requestStr = new StringBuffer();
						requestStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						requestStr.append("<reqInfo>");
						requestStr.append("<stepEquipList>");
						for(int i=0; i<requestList.size(); i++){
							Map stepEquipInfo = requestList.get(i);
							requestStr.append("<stepEquipInfo>");
							requestStr.append("<equip_id>").append(stepEquipInfo.get("EQUIP_ID")).append("</equip_id>");	
							requestStr.append("<longitude>").append(stepEquipInfo.get("LONGITUDE")).append("</longitude>");	
							requestStr.append("<latitude>").append(stepEquipInfo.get("LATITUDE")).append("</latitude>");	
							requestStr.append("</stepEquipInfo>");
						}
						requestStr.append("</stepEquipList>");
						requestStr.append("</reqInfo>");
						logger.info(changeCoo.changeStepEquip(requestStr.toString()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

	}
}
