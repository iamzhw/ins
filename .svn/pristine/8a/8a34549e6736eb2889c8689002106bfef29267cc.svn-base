package com.roomInspection.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.roomInspection.service.JobService;
import com.roomInspection.service.RoomService;
import com.system.service.GeneralService;
import com.util.MapUtil;
import com.util.StringUtil;


@Controller
@RequestMapping(value = "/jfxjRoom")
@SuppressWarnings("all")
public class RoomController extends BaseAction {

	 @Resource
	 private RoomService roomService;
 
	@Resource
	private JobService jobService;
	
	@Resource
	private GeneralService generalService;

	@RequestMapping(value = "/index.do")
	public ModelAndView indexRoom(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		init(map,request);
		return new ModelAndView("roominspection/room/room-index", map);
	}

	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> datas = roomService.roomQuery(request, pager);
		write(response, datas);
	}
	
	@RequestMapping(value = "/detail.do")
	public ModelAndView detailRoom(HttpServletRequest request,
			HttpServletResponse response) {
		
		//获取详情信息
		Map map = roomService.queryRoombyRoomId(request);
		
		return new ModelAndView("roominspection/room/room-detail", map);
	}
	
	@RequestMapping(value = "/toAdd.do")
	public ModelAndView toAddRoom(HttpServletRequest request,
			HttpServletResponse response){
		
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		//信息初始化
		init(map,request);
		map.put("currentDate", StringUtil.getCurrDate());
		
		return new ModelAndView("roominspection/room/room-add", map);
	}
	
	@RequestMapping(value = "/toUpdate.do")
	public ModelAndView toUpdateRoom(HttpServletRequest request,
			HttpServletResponse response){
		
		Map map = MapUtil.getCommonMap(request.getParameterMap());
		
		//信息初始化
		init(map,request);
		map.put("currentDate", StringUtil.getCurrDate());
		
		//查询当个机房信息
		Map roomMap = roomService.queryRoombyRoomId(request);
		map.put("room", roomMap);
		
		//获取区县信息
		List<Map<String, String>> sonAreaList = generalService.getSonAreaList(String.valueOf(roomMap.get("AREA_ID")));
		map.put("sonAreaList", sonAreaList);
		
		return new ModelAndView("roominspection/room/room-update", map);
	}
	
	@RequestMapping(value = "/add.do")
	@ResponseBody
	public Map addRoom(HttpServletRequest request,
			HttpServletResponse response){
		
		Map map = new HashMap();
		Boolean status = true;
		
		try {
			
			roomService.addRoom(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		
		return map;
	}
	
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map updateRoom(HttpServletRequest request,
			HttpServletResponse response){
		
		Map map = new HashMap();
		Boolean status = true;
		
		try {
			
			roomService.updateRoom(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		
		return map;
	}
	
	@RequestMapping(value = "/delete.do")
	public Map deleteRoom(HttpServletRequest request,
			HttpServletResponse response){
		
		Map map = new HashMap();
		Boolean status = true;
		
		try {
			
			roomService.deleteRoom(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		
		return map;
	}
	
	/**
	 * 初始化信息
	 * @param map 初始化信息保存map对象
	 * @param request 页面请求
	 */
	private void init(Map<String,Object> map,HttpServletRequest request)
	{
		// 将地市列表进行返回
		List<Map<String, String>> areaList = generalService.getAreaList();
		map.put("areaList", areaList);
		map.put("areaId", String.valueOf(request.getSession().getAttribute("areaId")));
		
		//获取当前登陆名
		String staffNo = String.valueOf(request.getSession().getAttribute("staffNo"));
		map.put("staffNo", staffNo);
		
		//初始化机房类型信息
		List<Map> roomTypeList = jobService.getRoomTypes();
		map.put("roomTypes", roomTypeList);
	}
}
