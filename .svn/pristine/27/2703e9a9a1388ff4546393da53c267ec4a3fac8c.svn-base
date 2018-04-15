package com.linePatrol.action;

import java.io.IOException;
import java.util.ArrayList;
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
import com.linePatrol.service.LineInfoService;
import com.linePatrol.service.impl.AutoTrackServiceImpl;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.linePatrol.util.StringUtil;
import com.system.service.AreaService;
import com.system.service.StaffService;
import com.util.MapDistance;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/autoTrackNewController")
@Controller
public class AutoTrackNewController extends BaseAction {

    @Resource
    private AutoTrackService autoTrackService;
    @Resource
    private AreaService areaService;
    @Resource
    private StaffService staffService;
    @Resource
    private LineInfoService lineInfoService;
    /**
     * http://localhost:8080/ins/autoTrackNewController/index.do
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 要展现的数据，个人信息
		Map<String, Object> staffInfo = staffService.findPersonalInfo(StaffUtil.getStaffId(request));
		map.put("staffInfo", staffInfo);
		return new ModelAndView("/linePatrol/xunxianManage/autoTrack/autoTrack_index_new", map);
    }

    @RequestMapping(value = "/realTimePosition_index.do")
    public ModelAndView realTimePosition_index(HttpServletRequest request, HttpServletResponse response) {
		// 准备数据
		Map<String, Object> map = new HashMap<String, Object>();
		// 要展现的数据，个人信息
		Map<String, Object> staffInfo = staffService.findPersonalInfo(StaffUtil.getStaffId(request));
		map.put("staffInfo", staffInfo);
		// 地市列表
		List<Map<String, Object>> areaList = areaService.getAllArea();
		map.put("areaList", areaList);
		return new ModelAndView("/linePatrol/xunxianManage/autoTrack/realTimePosition_index", map);
    }

    @RequestMapping(value = "/autoTrackAddUI.do")
    public ModelAndView autoTrackAddUI(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 要展现的数据
		// 获得登录ID用户的信息
		// String staffId = BaseUtil.getStaffId(request);
		// map.put("staffId", staffId);
	
		List<Map<String, Object>> list = null;
	
		return new ModelAndView("/linePatrol/xunxianManage/autoTrack/autoTrack_add", map);
    }

    @RequestMapping(value = "/autoTrackSave.do")
    @ResponseBody
    public Map autoTrackSave(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map map = new HashMap();
	Boolean status = true;
	try {
	    autoTrackService.autoTrackSave(para);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/autoTrackEditUI.do")
    public ModelAndView autoTrackEditUI(HttpServletRequest request, HttpServletResponse response,
	    String id) {
	Map<String, Object> rs = null;
	// 准备数据
	List<Map<String, Object>> list = null;

	rs = autoTrackService.findById(id);
	return new ModelAndView("/linePatrol/xunxianManage/autoTrack/autoTrack_edit", rs);

    }

    @RequestMapping(value = "/autoTrackUpdate.do")
    @ResponseBody
    public Map autoTrackUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    autoTrackService.autoTrackUpdate(para);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/autoTrackDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();
	map.put("status", true);
	try {
	    autoTrackService.autoTrackDelete(ids);
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

    @RequestMapping(value = "/getInspactPersonIndex.do")
    public ModelAndView getInspactPersonIndex(HttpServletRequest request,
	    HttpServletResponse response) {

	return new ModelAndView("/linePatrol/xunxianManage/autoTrack/autoTrack_inspact", null);

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
		    .getLocalInspactPerson(para);
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
    /**
     * 获取轨迹上传时间，经纬度，类型，GPS定位结果等
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getTheTrack.do")
    public void getTheTrack(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	
		map.put("status", true);
		try {
			//自动轨迹上传表
		    List<Map<String, Object>> trackList = autoTrackService.getTheTrack(para);
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

    @RequestMapping(value = "/getTheRealTimeTrack.do")
    public void getTheRealTimeTrack(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

	map.put("status", true);
	try {
	    List<Map<String, Object>> trackList = autoTrackService.getTheRealTimeTrack(para);
	    List<Map<String, Object>> outList=autoTrackService.getLineSiteByRealTime(para);
	    map.put("outList", outList);
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
    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getTheveryDayLineInfo.do")
    public void getTheveryDayLineInfo(HttpServletRequest request, HttpServletResponse response) {
		Map res = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	
		try {
	
		    res = autoTrackService.getTheveryDayLineInfo(para);
		    res.put("status", true);
	
		} catch (Exception e) {
	
		    e.printStackTrace();
	
		    res.put("status", false);
		}
	
		try {
		    response.getWriter().write(JSONObject.fromObject(res).toString());
		} catch (IOException e) {
		    e.printStackTrace();
		}

    }
    
    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getLeastFive.do")
    public void getLeastFive(HttpServletRequest request, HttpServletResponse response) {
    	List<Map<String, Object>>  res = new ArrayList<Map<String,Object>>();
    	Map<String,Object> map = new HashMap<String, Object>();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	
		try {
	
		    res = autoTrackService.getLeastFive(para);
		    map.put("status", true);
		    map.put("List", res);
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
    
    
    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getElsePoints.do")
    public void getElsePoints(HttpServletRequest request, HttpServletResponse response) {
    	List<Map<String, Object>>  res = new ArrayList<Map<String,Object>>();
    	Map<String,Object> map = new HashMap<String, Object>();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		try {	
		    res = autoTrackService.getElsePoints(para);
		    map.put("status", true);
		    map.put("List", res);
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
    
    /**
     * TODO 查询告警点
     * @param request
     * @param page
     * @return
     */    
    @RequestMapping("/getAlarmPoints")
	@ResponseBody
	public Map<String, Object> getAlarmPoints(HttpServletRequest request,UIPage page){
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
		List<Map<String, Object>> list = autoTrackService.getAlarmPoints(map);
		List<Map<String, Object>> listNew = new ArrayList<Map<String,Object>>();
		//根据个点之间的距离和速度进行分析
		int a = list.size()/5;
		double	aa;
		for(int i=0;i<a;i++){
			Map<String, Object> maps = new HashMap<String, Object>();
				aa = MapDistance.getDistance(
						Double.parseDouble(list.get(i*5).get("LONGITUDE").toString()),
						Double.parseDouble(list.get(i*5).get("LATITUDE").toString()),
						Double.parseDouble(list.get((i+1)*5).get("LONGITUDE").toString()),
						Double.parseDouble(list.get((i+1)*5).get("LATITUDE").toString())
					);
				if(aa<1000){
					maps.put("LONGITUDE", list.get(i*5).get("LONGITUDE").toString());
					maps.put("LATITUDE", list.get(i*5).get("LATITUDE").toString());
					maps.put("TRACK_ID", list.get(i*5).get("TRACK_ID").toString());
					maps.put("TRACK_TIME", list.get(i*5).get("TRACK_TIME").toString()+
							"-"+list.get((i+1)*5).get("TRACK_TIME").toString());
					maps.put("TRACK_TYPE", list.get(i*5).get("TRACK_TYPE").toString());
					i = i+1;
					listNew.add(maps);
				}
		}
		resultMap.put("rows", listNew);
//		resultMap.put("total",list.size());
		return resultMap;
	}
    
    
    
    /**
     * TODO 查询巡线轨迹
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/selTrackForDG")
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
		List<Map<String, Object>> list = autoTrackService.selTrackForDG(map);
		//计算的点不仅需要判断是否为外力点，还需要进行速度的告警分析
		resultMap.put("rows", list);
//		resultMap.put("total",list.size());
		System.out.println(list.size());
		return resultMap;
	}

}
