package com.linePatrol.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.linePatrol.service.LineInfoService;
import com.linePatrol.service.SiteService;
import com.linePatrol.service.gldManageService;
import com.linePatrol.service.relayService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.linePatrol.util.StringUtil;
import com.system.service.AreaService;
import com.system.service.ParamService;
import com.system.service.StaffService;
import com.system.sys.SystemListener;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/lineInfoController")
@Controller
public class LineInfoController extends BaseAction {

    @Resource
    private LineInfoService lineInfoService;
    @Resource
    private gldManageService gldService;
    @Resource
    private SiteService siteService;
    @Resource
    private StaffService staffService;
    @Resource
    private gldManageService gldManageService;
    @Resource
    private relayService relayService;
    @Resource
    private ParamService paramService;
    @Resource
    private AreaService areaService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

		// 角色
		Map<String, Object> map = paramService.query(request);
		String isAdmin = map.get("isAdmin").toString();// 0-省 1地市
	
		Map<String, Object> res = new HashMap<String, Object>();
		if ("0".equals(isAdmin)) {
		    List<Map<String, Object>> areaList = areaService.getAllArea();
		    res.put("areaList", areaList);
		} else {
		    // 本地巡線人---改成手输入
		    // Map<String, Object> para = new HashMap<String, Object>();
		    // para.put("localId", StaffUtil.getStaffAreaId(request));
		    // List<Map<String, Object>> localInspectStaff = lineInfoService
		    // .getLocalInspactPerson(para);
		    //
		    // res.put("localInspectStaff", localInspectStaff);
	
		    // 本地光缆
		    List<Map<String, Object>> cableList = gldManageService.getGldByAreaId(StaffUtil
			    .getStaffAreaId(request));
		    res.put("cableList", cableList);
		}
		res.put("isAdmin", isAdmin);
		return new ModelAndView("/linePatrol/xunxianManage/lineInfo/lineInfo_index", res);
    }

    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String area_id = null;
		area_id = request.getParameter("p_area_id");
		if (StringUtil.isNullOrEmpty(area_id)) {
		    area_id = StaffUtil.getStaffAreaId(request);
		}
	
		para.put("area_id", area_id);
		Map<String, Object> map = lineInfoService.query(para, pager);
		write(response, map);
    }
//    /**
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    @RequestMapping(value = "/queryForMap.do")
//    public void queryForMap(HttpServletRequest request, HttpServletResponse response) throws IOException {
//    	String area_id = request.getParameter("p_area_id");
//    	Map param = new HashMap();
//    	param.put("area_id", area_id);
//    	List<Map> list = lineInfoService.queryForMap(param);
//    	Map result = new HashMap();
//    	result.put("rows", list);
//    	write(response, result);
//    }

    @RequestMapping(value = "/lineInfoAddUI.do")
    public ModelAndView lineInfoAddUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据
	// 光缆
	List<Map<String, Object>> gldList = gldService.getGldByAreaId(StaffUtil
		.getStaffAreaId(request));
	map.put("gldList", gldList);
	// g个人信息
	Map<String, Object> staffInfo = staffService
		.findPersonalInfo(StaffUtil.getStaffId(request));

	// 巡线人
	String localId = StaffUtil.getStaffAreaId(request);
	List<Map<String, Object>> localInspactPerson = lineInfoService
		.getLocalInspactPerson(localId);
	map.put("staffInfo", staffInfo);
	map.put("localInspactPerson", localInspactPerson);

	return new ModelAndView("/linePatrol/xunxianManage/lineInfo/lineInfo_add", map);
    }

    @RequestMapping(value = "/lineInfoSave.do")
    @ResponseBody
    public Map lineInfoSave(HttpServletRequest request, HttpServletResponse response,
	    String cableObj) {

	Map map = new HashMap();
	Boolean status = true;
	try {
	    lineInfoService.lineInfoSave(request);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/lineInfoEditUI.do")
    public ModelAndView lineInfoEditUI(HttpServletRequest request, HttpServletResponse response,
	    String line_id) {
	String local_id = StaffUtil.getStaffAreaId(request);
	Map<String, Object> para = new HashMap<String, Object>();
	para.put("line_id", line_id);
	para.put("local_id", local_id);
	para.put("staff_id", StaffUtil.getStaffId(request));
	Map<String, Object> rs = lineInfoService.findLineInfoById(para);

	return new ModelAndView("/linePatrol/xunxianManage/lineInfo/lineInfo_edit", rs);

    }

    @RequestMapping(value = "/lineInfoUpdate.do")
    @ResponseBody
    public Map lineInfoUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	// Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    lineInfoService.lineInfoUpdate(request);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/lineInfoDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    lineInfoService.lineInfoDelete(ids);
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

    @RequestMapping(value = "/getRelayByGl.do")
    public void getRelayByGl(HttpServletRequest request, HttpServletResponse response, String gldId) {
	Map map = new HashMap();

	map.put("status", true);
	List<Map<String, Object>> relayList = null;
	try {
	    String localId = StaffUtil.getStaffAreaId(request);
	    relayList = lineInfoService.getRelayByGl(gldId, localId);
	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}
	map.put("relayList", relayList);
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/getSiteByRelayId.do")
    public void getSiteByRelayId(HttpServletRequest request, HttpServletResponse response,
	    String relay_id) {
	Map map = new HashMap();

	map.put("status", true);
	List<Map<String, Object>> siteList = null;
	try {
	    Map<String, Object> para = new HashMap<String, Object>();

	    String area_id = StaffUtil.getStaffAreaId(request);
	    para.put("area_id", area_id);
	    para.put("relay_id", relay_id);

	    siteList = siteService.getSiteByRelayId(para);

	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}
	map.put("siteList", siteList);
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/getLocalInspactPerson.do")
    public void getLocalInspactPerson(HttpServletRequest request, HttpServletResponse response,
	    String relay_id) {
	Map map = new HashMap();

	map.put("status", true);
	List<Map<String, Object>> localInspactPerson = null;
	try {
	    String localId = StaffUtil.getStaffAreaId(request);
	    localInspactPerson = lineInfoService.getLocalInspactPerson(localId);

	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}
	map.put("localInspactPerson", localInspactPerson);
	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/showTheLineOnMap.do")
    public ModelAndView showTheLineOnMap(HttpServletRequest request, HttpServletResponse response,
	    String line_id) {

	String local_id = StaffUtil.getStaffAreaId(request);
	Map<String, Object> para = new HashMap<String, Object>();
	para.put("line_id", line_id);
	para.put("local_id", local_id);
	para.put("staff_id", StaffUtil.getStaffId(request));
	Map<String, Object> rs = lineInfoService.findLineInfoById(para);

	return new ModelAndView("/linePatrol/xunxianManage/lineInfo/showTheLineOnMap", rs);

    }
    /**
     * TODO 地图展示
     * @param request
     * @param response
     * @param lineIds
     * @return
     */
    @RequestMapping(value = "/mapShow.do")
    public ModelAndView mapShow(HttpServletRequest request, HttpServletResponse response, String lineIds) {

    	String area_id = request.getParameter("p_area_id");
    	if(StringUtil.isNullOrEmpty(area_id)) {
    		area_id = StaffUtil.getStaffAreaId(request);
    	}
    	Map param = new HashMap();
    	param.put("area_id", area_id);
    	Map<String, Object> rs = lineInfoService.queryForMap(param);
    	
		return new ModelAndView("/linePatrol/xunxianManage/lineInfo/mapShow", rs);

    }
    

    @RequestMapping(value = "/getSitesByIdsUI.do")
    public ModelAndView getSitesByIdsUI(HttpServletRequest request, HttpServletResponse response,
	    HttpSession session, String markersIds, String lng, String lat) {

	// List<Map<String, Object>> sites =
	// siteService.getSitesByIds(markersIds);
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("sites", sites);
	session.setAttribute("markersIds", markersIds);
	session.setAttribute("lng", lng);
	session.setAttribute("lat", lat);
	return new ModelAndView("/linePatrol/xunxianManage/lineInfo/selected_sites");
    }

    @RequestMapping(value = "/getSitesByIds.do")
    public void getSitesByIds(HttpServletRequest request, HttpServletResponse response,
	    HttpSession session) {

	String markersIds = session.getAttribute("markersIds").toString();
	String lng = session.getAttribute("lng").toString();
	String lat = session.getAttribute("lat").toString();

	List<Map<String, Object>> sites = lineInfoService.getSitesByIds(markersIds, lng, lat);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("total", sites.size());
	map.put("rows", sites);

	session.removeAttribute("sites");

	try {
	    write(response, map);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/getLineInfoByRelay.do")
    public void getLineInfoByRelay(HttpServletRequest request, HttpServletResponse response,
	    String relay_id) {
	Map map = new HashMap();

	map.put("status", true);
	List<Map<String, Object>> lineInfoList = null;

	Map<String, Object> para = new HashMap<String, Object>();
	para.put("relay_id", relay_id);
	para.put("areaId", StaffUtil.getStaffAreaId(request));

	try {
	    lineInfoList = lineInfoService.getLineInfoByRelay(para);

	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}
	map.put("lineInfoList", lineInfoList);

	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/getRelay.do")
    public void getRelay(HttpServletRequest request, HttpServletResponse response, String cable_id) {
	Map map = new HashMap();
	map.put("status", true);
	List<Map<String, Object>> relayList = null;
	Map<String, Object> para = new HashMap<String, Object>();
	para.put("cable_id", cable_id);
	para.put("areaId", StaffUtil.getStaffAreaId(request));

	try {
	    relayList = lineInfoService.getRelayByGl(cable_id, StaffUtil.getStaffAreaId(request));

	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}
	map.put("relayList", relayList);

	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    
    @RequestMapping(value = "/getRelayById.do")
    public void getRelayById(HttpServletRequest request, HttpServletResponse response, String cable_id, String area_id) {
	Map map = new HashMap();
	map.put("status", true);
	List<Map<String, Object>> relayList = null;
	Map<String, Object> para = new HashMap<String, Object>();
	para.put("cable_id", cable_id);
	para.put("areaId", area_id);

	try {
	    relayList = lineInfoService.getRelayById(cable_id, area_id);

	} catch (Exception e) {

	    e.printStackTrace();

	    map.put("status", false);
	}
	map.put("relayList", relayList);

	try {
	    response.getWriter().write(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    
    @RequestMapping(value = "/getCableAndInsStaff.do")
    public void getCableAndInsStaff(HttpServletRequest request, HttpServletResponse response,
	    String area_id) {
	Map map = new HashMap();
	map.put("status", true);

	try {
	    List<Map<String, Object>> calbeList = gldManageService.getGldByAreaId(area_id);
	    List<Map<String, Object>> insStaffList = lineInfoService.getLocalInspactPerson(area_id);

	    map.put("cableList", calbeList);
	    map.put("insStaffList", insStaffList);
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
    
    @RequestMapping(value = "/addSiteInLine.do")
    public ModelAndView addSiteInLine(HttpServletRequest request, HttpServletResponse response) {
    	String lineId = request.getParameter("lineId");
    	String relayId = request.getParameter("relayId");
    	Map<String,Object> res = new HashMap<String,Object>();
    	res.put("lineId", lineId);
    	res.put("relayId", relayId);
    	return new ModelAndView("/linePatrol/xunxianManage/lineInfo/site_list",res);
    }
    
    @RequestMapping(value = "/querySiteList.do")
	public void querySiteList(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
    	String lineId = request.getParameter("lineId");
    	Map param = new HashMap();
    	param.put("line_id", lineId);
		Map<String, Object> map = siteService.querySiteList(param, pager);
		write(response, map);
	}
    
  //下载模板
    @RequestMapping("downloadExample")
	@ResponseBody
	public void downloadExample(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("巡线点导入样例.xls").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/巡线点导入样例.xls")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
    
    //导入巡线点
    @RequestMapping("import_save")
	@ResponseBody
	public void importDo(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file)
					throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
				siteService.importDo(request, file).toString());
	}
    
    //导出巡线点
	@RequestMapping(value = "/export.do")
	public String export(HttpServletRequest request, ModelMap mm) {
		Map param = new HashMap();
		String line_id = request.getParameter("lineId");
		param.put("line_id", line_id);
		List<Map> dataList = (List<Map>) siteService.querySiteList(param,new UIPage()).get("rows");
		Map line = lineInfoService.findById(line_id);
		String[] cols = new String[] { "SITE_NAME", "LONGITUDE", "LATITUDE",
				"ADDRESS", "AREA_NAME", "SITE_PROPERTY", "SITE_MATCH", "KEYFLAG"};
		mm.addAttribute("cols", cols);
		String[] colsName = new String[] { "巡线点名称", "经度", "纬度",
				"地址", "区域", "点属性", "匹配距离", "是否为关键点"};
		mm.addAttribute("name", line.get("LINE_NAME")+"-巡线点");
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", dataList);
		return "dataListExcel";
	}

}
