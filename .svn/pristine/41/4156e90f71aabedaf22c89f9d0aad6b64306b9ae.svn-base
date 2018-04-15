package com.smartcover.action; 

import icom.util.BaseServletTool;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.page.BaseAction;

import com.smartcover.service.OperateDataService;

/** 
 * @author wangxiangyu
 * @date：2017年10月10日 下午6:13:07 
 * 类说明：智能井盖数据操作接口
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/operateData")
public class OperateDataController extends BaseAction {
	
	@Autowired
	OperateDataService operateDataService;
	
	/**
	 * 
	 * @Title: auth
	 * @Description:鉴权接口，认证API
	 * @author wangxiangyu
	 */
	@RequestMapping("/auth.do")
	@ResponseBody
	public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> rs = operateDataService.getAuthResult();
		write(response, rs);
	}
	
	/**
	 * 
	 * @Title: getAllDevices
	 * @Description:获取全部设备信息
	 * @author wangxiangyu
	 */
	@RequestMapping("/getAll.do")
	@ResponseBody
	public void getAllDevices(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, Object> rs = operateDataService.getAllDevices("10",null,null,null);
		write(response, rs);
	}
	
	/**
	 * 
	 * @Title: getOne
	 * @Description:获取单个设备信息
	 * @author wangxiangyu
	 */
	@RequestMapping("/getOne.do")
	@ResponseBody
	public void getOne(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> rs = operateDataService.getOne("c08f8980-a346-11e7-a827-a7bf5de4b19f");
		write(response, rs);
	}
	
	/**
	 * 
	 * @Title: getKeyAndValue
	 * @Description:设备属性key-value查询
	 * @author wangxiangyu
	 */
	@RequestMapping("/getKeyAndValue.do")
	@ResponseBody
	public void getKey(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		List<Map<String, String>> rs = operateDataService.getKeyAndValue("c08f8980-a346-11e7-a827-a7bf5de4b19f");
		
		write(response, rs);
	}
	
}
