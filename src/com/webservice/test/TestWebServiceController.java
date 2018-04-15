package com.webservice.test;

import java.rmi.RemoteException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.webservice.client.ElectronArchivesService;

/** 
 * @author wangxy 
 * @version 创建时间：2016年10月20日 上午11:25:21 
 * 类说明 
 */
@Controller
@RequestMapping(value = "/testWebService")
public class TestWebServiceController {

	Logger logger = Logger.getLogger(TestWebServiceController.class);
	@Autowired
	ElectronArchivesService electronArchivesService;
	
	/**
	 * 根据设备no查询健康电子档案WebService
	 */
	@RequestMapping("/test")
	public void test(){
		
		JSONObject object = new JSONObject();
		object.put("eqpNos", "250BX.MXY00/GF019,250XW.XLW00/GF013,250JB.GX000/GF006033");
		String param = object.toString();
		
		String result;
		try {
			result = electronArchivesService.queryEqpInfo(param);
			logger.info("【根据设备no查询健康电子档案WebService】"+result);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过光路编号查询FTTH装机照片详情WebService
	 */
	@RequestMapping("/test1")
	public void test1(){
		
		JSONObject object = new JSONObject();
		
		JSONArray jsArr = new JSONArray();
		
		JSONObject object1 = new JSONObject();
		object1.put("areaName", "南京市区");
		object1.put("glbh", "E1607110558");
		jsArr.add(object1);
		JSONObject object2 = new JSONObject();
		object2.put("areaName", "南京市区");
		object2.put("glbh", "F1607113911");
		jsArr.add(object2);
		JSONObject object3 = new JSONObject();
		object3.put("areaName", "南京市区");
		object3.put("glbh", "F1605173385");
		jsArr.add(object3);
		object.put("param", jsArr);
		
		String result;
		try {
			result = electronArchivesService.queryPhotoDetail(object.toString());
			logger.info("【通过光路编号查询FTTH装机照片详情WebService】"+result);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
