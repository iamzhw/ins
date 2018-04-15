/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.Query;
import util.page.UIPage;

import com.linePatrol.service.AutoTrackService;
import com.linePatrol.service.GaotieAutoTrackService;
import com.linePatrol.service.LineInfoService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.linePatrol.util.StringUtil;
import com.system.service.AreaService;
import com.system.service.StaffService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/gaotieautoTrackController")
@Controller
public class GaotieautoTrackController extends BaseAction {

    @Resource
    private GaotieAutoTrackService gaotieautoTrackService;
    @Resource
    private AreaService areaService;

    @Resource
    private StaffService staffService;
    @Resource
    private LineInfoService lineInfoService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

	// 准备数据
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据

	// g个人信息
	Map<String, Object> staffInfo = staffService.findPersonalInfo(StaffUtil.getStaffId(request));

	map.put("staffInfo", staffInfo);

	return new ModelAndView("/linePatrol/xunxianManage/gaotieautoTrack/autoTrack_index", map);
    }

    @RequestMapping(value = "/realTimePosition_index.do")
    public ModelAndView realTimePosition_index(HttpServletRequest request,
	    HttpServletResponse response) {

	// 准备数据
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据

	// g个人信息
	Map<String, Object> staffInfo = staffService
		.findPersonalInfo(StaffUtil.getStaffId(request));
	map.put("staffInfo", staffInfo);

	// 地市列表
	List<Map<String, Object>> areaList = areaService.getAllArea();
	map.put("areaList", areaList);

	return new ModelAndView("/linePatrol/xunxianManage/gaotieautoTrack/realTimePosition_index", map);
    }

    @RequestMapping(value = "/getInspactPersonIndex.do")
    public ModelAndView getInspactPersonIndex(HttpServletRequest request,
	    HttpServletResponse response) {

	return new ModelAndView("/linePatrol/xunxianManage/gaotieautoTrack/autoTrack_inspact", null);

    }

    @RequestMapping(value = "/getInspactPerson.do")
    public void getInspactPerson(HttpServletRequest request, HttpServletResponse response,
	    String ids) {
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

	map.put("status", true);
	try {
	    // 本地巡线员
	    String localId = StaffUtil.getStaffAreaId(request);
	    para.put("localId", localId);
	    List<Map<String, Object>> localInspactPerson = lineInfoService
		    .gaotiegetLocalInspactPerson(para);
	    map.put("rows", localInspactPerson);
	    map.put("total", localInspactPerson.size());
	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}

	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/getTheTrack.do")
    public void getTheTrack(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

	map.put("status", true);
	try {
	    List<Map<String, Object>> trackList = gaotieautoTrackService.getTheTrack(para);
	    map.put("trackList", trackList);
	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}

	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
    
    @RequestMapping(value = "/selTrackPhoto.do")
	public ModelAndView selTrackPhoto(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = gaotieautoTrackService.selTrackPhoto(request);
		return new ModelAndView("/linePatrol/xunxianManage/gaotieautoTrack/gaotie_TrackPhoto", rs);
	}


    @RequestMapping("/selTrackForDG.do")
	@ResponseBody
	public Map<String, Object> selTrackForDG(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		if (StringUtil.isNOtNullOrEmpty(map.get("start_time").toString())) {
		    String startTime = map.get("start_time").toString();
		    startTime = startTime.substring(0, startTime.indexOf(":"));
		    int st = Integer.parseInt(startTime);
		    if (st < 10) {
		    	startTime = "0" + map.get("start_time").toString();
		    }else{
		    	startTime = map.get("start_time").toString();
		    }
		    map.put("start_time", startTime);
		}
		if (StringUtil.isNOtNullOrEmpty(map.get("end_time").toString())) {
		    String endTime = map.get("end_time").toString();
		    endTime = endTime.substring(0, endTime.indexOf(":"));
		    int et = Integer.parseInt(endTime);
		    if (et < 10) {
		    	endTime = "0" + map.get("end_time").toString();
		    }else{
		    	endTime = map.get("end_time").toString();
		    }
		    map.put("end_time", endTime);
		}
//		Query query=new Query();
//		query.setQueryParams(map);
//		query.setPager(page);
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		List<Map<String, Object>> list = gaotieautoTrackService.selTrackForDG(map);
		resultMap.put("rows", list);
//		resultMap.put("total",list.size());
		return resultMap;
	}

}
