package com.inspecthelper.action;

import java.io.UnsupportedEncodingException;

import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inspecthelper.service.IInspectHelperService;


import util.page.BaseAction;

@RequestMapping(value = "/mobile/inspect-helper")
@Controller
public class InspectHelperController extends BaseAction{
	
	@Resource
	private IInspectHelperService inspectHelperServiceImpl;
	
	@RequestMapping(value = "/getOrderList.do")
	public void getOrderList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			String reJsonStr = inspectHelperServiceImpl.getOrderList(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@RequestMapping(value = "/saveFile.do")
	public void saveFile(HttpServletRequest request,
			HttpServletResponse response) {
		String jsonStr = "";
		try {
			inspectHelperServiceImpl.saveFileStream(request);
			jsonStr = "{\"result\":\"000\"}";
		} catch (Exception e) {
			jsonStr = "{\"result\":\"001\"}";
			e.printStackTrace();
		} finally {
			BaseServletTool.sendParam(response, jsonStr);

		}
	}
	
	@RequestMapping(value = "/saveDes.do")
	public void saveDes(HttpServletRequest request,
			HttpServletResponse response) {
		String jsonStr = "";
		try {
			jsonStr = BaseServletTool.getParam(request);
			//jsonStr ="{'troubleId':'301333','orderDes':'测试回单','sn':'862199029026496','staffId':'2622','operate':1}";
			// "{\"sn\":\"2535\",\"staffId\":\"123\",\"troubleId\":\"234\",\"orderDes\":\"SDF\",\"operate\":\"1\"}";
			// jsonStr =
			// "{\"sn\":\"C4F12BA0011CE1F\",\"staffId\":\"157\",\"troubleId\":\"632\", \"orderDes\":\"SDF\",\"operate\":\"1\"}";
			inspectHelperServiceImpl.saveDes(jsonStr);
			jsonStr = "{\"result\":\"000\"}";
		} catch (Exception e) {
			jsonStr = "{\"result\":\"001\"}";
			e.printStackTrace();
		} finally {
			BaseServletTool.sendParam(response, jsonStr);

		}
	}
	
	@RequestMapping(value = "/orderDetail.do")
	public void orderDetail(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr = "{\"troubleId\":\"155314\"}";
		jsonStr = inspectHelperServiceImpl.orderDetail(jsonStr);
		BaseServletTool.sendParam(response, jsonStr);

	}

	/*
	 * 上报问题
	 */
	@RequestMapping(value = "/saveResTroubles.do")
	public void saveResTroubles(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			//jsonStr ="{'troubleList':[{'type':'0','checkItemId':'1415','description':'测试01'},{'type':'0','checkItemId':'1416','description':'测试'}],'taskId':'167444','sn':'862199029026496','flag':'temple run','staffNo':'xj110','staffId':'2622','equipmentId':'25320000001917','beginTime':'2015-01-23 10:45:30','terminal_type':'PHONE','endTime':'2015-01-23 11:03:03'}";
			//	 "{\"troubleList\":[{\"type\":\"0\",\"checkItemId\":\"0001\"," +
			//	 "\"description\":\"阿卡丽\"}],\"taskId\":\"141323\"," +
			//	 "\"sn\":\"860310021578416\",\"flag\":\"\",\"staffNo\":\"xj110\"," +
			//	 "\"equipmentId\":\"12320000002903\",\"beginTime\":\"2014-06-19 14:15:14\"," +
			//	 "\"endTime\":\"2014-06-19 14:15:58\"}";
			String reJsonStr = inspectHelperServiceImpl.saveResTrouble(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取用户的任务列表
	 */
	@RequestMapping(value = "/allTask.do")
	public void allTask(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			// jsonStr ="{'staffNo':'xj110','sn':'862199029026496','staffId':'2622'}";
			String reJsonStr = inspectHelperServiceImpl.getTaskList(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取框号
	 */
	@RequestMapping(value = "/getUnoByResNo.do")
	public void getUnoByResNo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			 //jsonStr = "{'resNo':'250JN.KFQ00Z01\\/ODF(GB007001)','sn':'862199029026496','staffId':'2622'}";
			String reJsonStr = inspectHelperServiceImpl.getUnoByResNo(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 根据光交编码获取分光器
	 */
	@RequestMapping(value = "/getOBDByGJNo.do")
	public void getOBDByGJNo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			//jsonStr="{'sn':'862199029026496','no':'250JN.JNZ00\\/GJ006','staffId':'2622'}";
			String reJsonStr = inspectHelperServiceImpl.getOBDByGJNo(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 签到
	 */
	@RequestMapping(value = "/signRes.do")
	public void signRes(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			// jsonStr =
			// "{\"sn\":\"22\",\"staffNo\": \"pad\",\"taskId\":\"1\",\"equipmentId\":\"12320000002090\",\"latitude\":\"33333.333\",\"longitude\":\"6666666.333\"}";
			String reJsonStr = inspectHelperServiceImpl.signRes(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getTroubleId.do")
	public void getTroubleId(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = BaseServletTool.getParam(request);
			//jsonStr = "{'sn':'862199029026496'}";
			String reJsonStr = inspectHelperServiceImpl.getTroubleId(jsonStr);
			BaseServletTool.sendParam(response, reJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 点击上传的时候上传照片
	 */
	@RequestMapping(value = "/savePhoto.do")
	public void savePhoto(HttpServletRequest request,
			HttpServletResponse response) {
		// String jsonStr =
		// "{\"session_id\":\"c384692bde1d467ca8db58d289fbac22\",\"user_id\": \"123\",\"machine\":\"PAD\"}";
		// String jsonStr = BaseServletTool.getParam(request);
		// System.out.println("jsonStr:"+jsonStr);
		String reJsonStr = inspectHelperServiceImpl.saveInsPhoto(request);
		BaseServletTool.sendParam(response, reJsonStr);
	}

}
