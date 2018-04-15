/**
 * @Description: 
 * @date 2015-2-6
 * @param
 */
package com.outsite.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.axxreport.service.XxdReportService;
import com.linePatrol.service.CutAndConnOfFiberService;
import com.linePatrol.service.LineInfoService;
import com.linePatrol.service.gldManageService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.outsite.service.MainOutSiteService;
import com.system.constant.RoleNo;
import com.system.service.GeneralService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/mainOutSiteController")
@Controller
public class MainOutSiteController extends BaseAction {
	@Resource
    private LineInfoService lineInfoService;

    @Resource
    private MainOutSiteService mainOutSiteService;
    @Resource
    private gldManageService gldManageService;
    @Resource
    private GeneralService generalService;
    @Autowired
    private CutAndConnOfFiberService cutAndConnOfFiberService;
    @Autowired
    private XxdReportService xxdReportService;

    @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response,ModelMap model) {

	// 准备数据

	Map<String, Object> map = new HashMap<String, Object>();
	String localId=null;
	if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
		localId=StaffUtil.getStaffAreaId(request);
		map.put("localId", localId);
	}
	model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
	model.addAttribute("localId",localId);
	return new ModelAndView("/outsite/mainOutSite/mainOutSite_index", model);
    }
    
    @RequestMapping(value = "/record_index.do")
    public ModelAndView record_index(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
	Map<String, Object> map = new HashMap<String, Object>();
	String localId=null;
//	if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
		localId=StaffUtil.getStaffAreaId(request);
		map.put("area_id", localId);
		List<Map<String, Object>> orgList=xxdReportService.getScopeList(map);
		model.addAttribute("orgList", orgList);
//	}
//	model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
//	model.addAttribute("localId",localId);
	return new ModelAndView("/outsite/mainOutSite/mainOutSite_recordindex", model);
    }
    
    
    /**
     * TODO 检查记录导出功能
     * @param request
     * @param response
     * @param pager
     * @throws IOException
     */
    @RequestMapping(value="/record_indexDown.do")
    public  void recordIndexDown(HttpServletRequest request, HttpServletResponse response, UIPage pager)
    throws IOException {
    	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
    	
    	String p_check_name = request.getParameter("p_check_name");
    	p_check_name = new String(p_check_name.getBytes("iso-8859-1"),"utf-8");
    	para.put("p_check_name", p_check_name);
    	
    	para.put("area_id", StaffUtil.getStaffAreaId(request));
    	mainOutSiteService.recordIndexDown(para,request, response);
    	
    }
    
    
    
    
    
    @RequestMapping(value = "/getAllGroup")
    @ResponseBody
    public List<Map<String, Object>> getAllGroup(HttpServletRequest request, HttpServletResponse response,String areaId) {
    	return mainOutSiteService.getAllGroup(areaId);
    }

    
    @RequestMapping(value = "/query.do")
    public void query(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

//	String area_id = StaffUtil.getStaffAreaId(request);
//	para.put("area_id", area_id);

	Map<String, Object> map = mainOutSiteService.query(para, pager);
	write(response, map);
    }
    
    /**
     * 外力点导出功能
     * @param request
     * @param response
     * @param pager
     * @throws IOException
     */
    
    @RequestMapping(value = "/queryByDown.do")
    public void queryByDown(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	mainOutSiteService.queryByDown(para,request, response);
	
    }
    
    
    
    /**
     * TODO 查询
     * @param request
     * @param response
     * @param pager
     * @throws IOException
     */
    @RequestMapping(value = "/query_recordindex.do")
    public void query_recordindex(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId=null;
		localId=StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		Map<String, Object> map = mainOutSiteService.query_recordindex(para, pager);
		write(response, map);
    }
    @RequestMapping(value = "/mainOutSiteAddUI.do")
    public ModelAndView mainOutSiteAddUI(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	Map<String, Object> map = new HashMap<String, Object>();
	// 要展现的数据
	// 获得登录ID用户的信息
	// String staffId = BaseUtil.getStaffId(request);
	// map.put("staffId", staffId);

	List<Map<String, Object>> list = null;

	return new ModelAndView("/outsite/mainOutSite/mainOutSite_add", map);
    }

    @RequestMapping(value = "/mainOutSiteSave.do")
    @ResponseBody
    public Map mainOutSiteSave(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	Map map = new HashMap();
	Boolean status = true;
	try {
	    mainOutSiteService.mainOutSiteSave(para);
	} catch (Exception e) {
	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/mainOutSiteEditUI.do")
    public ModelAndView mainOutSiteEditUI(HttpServletRequest request, HttpServletResponse response,
	    String id) {
	Map<String, Object> rs = null;
	// 准备数据
	List<Map<String, Object>> list = null;

	rs = mainOutSiteService.findById(id);

	String staffNo = StaffUtil.getStaffNo(request);
	rs.put("staffNo", staffNo);
	Object fiber_eponsible_by = rs.get("FIBER_EPONSIBLE_BY");
	if(fiber_eponsible_by != null && !"".equals(fiber_eponsible_by)){
		String orgName = mainOutSiteService.selOrgName(fiber_eponsible_by);
		rs.put("orgName", orgName);
	}
	Map<String, Object> map_img = new HashMap<String, Object>();
	map_img.put("site_id", id);
	map_img.put("photo_type", 3);// 外力点
	List<Map<String, Object>> photoList = generalService.getPhoto(map_img);

	rs.put("photoList", photoList);
	return new ModelAndView("/outsite/mainOutSite/mainOutSite_edit", rs);

    }

    @RequestMapping(value = "/mainOutSiteUpdate.do")
    @ResponseBody
    public Map mainOutSiteUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    mainOutSiteService.mainOutSiteUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	return map;
    }

    @RequestMapping(value = "/mainOutSiteDelete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
	Map map = new HashMap();

	map.put("status", true);
	try {
	    mainOutSiteService.mainOutSiteDelete(ids);
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

    @RequestMapping(value = "/getCableByAreaId.do")
    public ModelAndView getCableByAreaId(HttpServletRequest request, HttpServletResponse response,
	    String affected_fiber) {
	String localId = StaffUtil.getStaffAreaId(request);
	List<Map<String, Object>> cableList = gldManageService.getGldByAreaId(localId);

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("cableList", cableList);
	map.put("affected_fiber", affected_fiber);

	return new ModelAndView("/outsite/mainOutSite/affactedCableUI", map);
		
    }
    
    /***
     * 孙敏
     * 2016/3/15
     * 根据光缆id获取到所有的中继段
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getRelayByCableId.do")
    public ModelAndView getRelayByCableId(HttpServletRequest request, HttpServletResponse response) {
    	String cable_id=request.getParameter("cid");
    	List<Map<String, Object>> RelayList = gldManageService.getRelayByCableId(cable_id);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("RelayList", RelayList);
    	return new ModelAndView("/outsite/mainOutSite/affactedRelay", map);
    }

    @RequestMapping(value = "/getCzs.do")
    public void getCzs(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	List<Map<String, Object>> czsList = mainOutSiteService.getCzs(para);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", czsList);
	map.put("total", czsList.size());

	write(response, map);
    }

    @RequestMapping(value = "/jxsEditUI.do")
    public ModelAndView jxsEditUI(HttpServletRequest request, HttpServletResponse response,
	    String operator_id) {

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("operator_id", operator_id);
	Map<String, Object> res = mainOutSiteService.findJxsByid(map);

	return new ModelAndView("/outsite/mainOutSite/jxs_edit", res);
    }

    @RequestMapping(value = "/jxsUpdate.do")
    public void jxsUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    mainOutSiteService.jxsUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	try {
	    write(response, map);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/getCheckRecord.do")
    public void getCheckRecord(HttpServletRequest request, HttpServletResponse response,
	    UIPage pager) throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	List<Map<String, Object>> checkRecord = mainOutSiteService.getCheckRecord(para);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", checkRecord);
	map.put("total", checkRecord.size());

	write(response, map);
    }

    @RequestMapping(value = "/getSignInfo.do")
    public void getSignInfo(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	List<Map<String, Object>> signList = mainOutSiteService.getSignInfo(para);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", signList);
	map.put("total", signList.size());

	write(response, map);
    }

    @RequestMapping(value = "/getStayInfo.do")
    public void getStayInfo(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	List<Map<String, Object>> stayInfo = mainOutSiteService.getStayInfo(para);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", stayInfo);
	map.put("total", stayInfo.size());

	write(response, map);
    }

    @RequestMapping(value = "/getMstwInfo.do")
    public void getMstwInfo(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	List<Map<String, Object>> mstwInfo = mainOutSiteService.getMstwInfo(para);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("rows", mstwInfo);
	map.put("total", mstwInfo.size());

	write(response, map);
    }

    @RequestMapping(value = "/mstwUpdate.do")
    public void mstwUpdate(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	try {
	    mainOutSiteService.mstwUpdate(para);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	try {
	    write(response, map);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/mstwEditUI.do")
    public ModelAndView mstwEditUI(HttpServletRequest request, HttpServletResponse response,
	    String probe_id) {

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("probe_id", probe_id);
	Map<String, Object> res = mainOutSiteService.findMstwByid(map);

	return new ModelAndView("/outsite/mainOutSite/mstw_edit", res);
    }

    @RequestMapping(value = "/getOuteSiteDetailUI.do")
    public ModelAndView getOuteSiteDetailUI(HttpServletRequest request,
	    HttpServletResponse response, String out_site_id) {
	Map<String, Object> rs = null;
	// 准备数据
	List<Map<String, Object>> list = null;

	rs = mainOutSiteService.findById(out_site_id);

	String staffNo = StaffUtil.getStaffNo(request);
	rs.put("staffNo", staffNo);
	Map<String, Object> map_img = new HashMap<String, Object>();
	map_img.put("site_id", out_site_id);
	map_img.put("photo_type", 3);// 外力点
	List<Map<String, Object>> photoList = generalService.getPhoto(map_img);

	rs.put("photoList", photoList);
	return new ModelAndView("/outsite/mainOutSite/mainOutSite_detail", rs);

    }
    @RequestMapping(value = "/getOutsitePhoto.do")
	public void getKeysiteArrate(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("photo_type", 4);// 外力点
		List<Map<String, Object>> photoList = generalService.getPhotoByType(para);

		try {
			response.getWriter().write(
					JSONArray.fromObject(photoList).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    @RequestMapping(value = "/checkDetailUI.do")
    public ModelAndView checkDetailUI(HttpServletRequest request, HttpServletResponse response,
	    String out_site_id) {

	Map<String, Object> rs = new HashMap<String, Object>();
	rs.put("out_site_id", out_site_id);
	return new ModelAndView("/outsite/mainOutSite/mainOutSite_checkRecord", rs);

    }
    
    
    @RequestMapping(value = "/flowDetailUI.do")
    public ModelAndView flowDetailUI(HttpServletRequest request, HttpServletResponse response,
	    String out_site_id) {

	Map<String, Object> rs = new HashMap<String, Object>();
	rs.put("out_site_id", out_site_id);
	return new ModelAndView("/outsite/mainOutSite/mainOutSite_flow", rs);

    }
    
    
    @RequestMapping(value = "/getFlowDetail.do")
    public void getFlowDetail(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> checkRecord = mainOutSiteService.getFlowDetail(para);
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("rows", checkRecord);
		rs.put("total", checkRecord.size());
	
		try {
		    write(response, rs);
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }

    @RequestMapping(value = "/getCheckDetail.do")
    public void getCheckDetail(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	List<Map<String, Object>> checkRecord = mainOutSiteService.getCheckRecord(para);
	Map<String, Object> rs = new HashMap<String, Object>();
	rs.put("rows", checkRecord);
	rs.put("total", checkRecord.size());

	try {
	    write(response, rs);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/getPhoto.do")
    public ModelAndView getPhoto(HttpServletRequest request, HttpServletResponse response,
	    String out_site_id) {
	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	List<Map<String, Object>> photoList = generalService.getPhoto(para);

	Map<String, Object> map = new HashMap<String, Object>();
	System.out.println(photoList);
	map.put("photoList", photoList);
	return new ModelAndView("/outsite/mainOutSite/checkRecord_photo", map);

    }

    @RequestMapping(value = "/findById.do")
    public void findById(HttpServletRequest request, HttpServletResponse response,
	    String out_site_id) {
	Map map = new HashMap();
	Boolean status = true;
	try {
	    map = mainOutSiteService.findById(out_site_id);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	try {
	    write(response, map);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * 电子围栏坐标保存
     */
    @RequestMapping(value = "/saveElebar.do")
    public void saveElebar(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	String pointsJsons = request.getParameter("res");
	String plan_id = request.getParameter("plan_id");
	String ebWidth = request.getParameter("ebWidth");
	String ebLength = request.getParameter("ebLength");
	String out_site_id = request.getParameter("out_site_id");

	String staffId = StaffUtil.getStaffId(request);
	try {
	    mainOutSiteService.saveElebar(pointsJsons, plan_id, out_site_id, staffId, ebWidth,
		    ebLength);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	try {
	    write(response, map);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * 电子围栏坐标更新
     */
    @RequestMapping(value = "/updateElebar.do")
    public void updateElebar(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	Boolean status = true;
	String pointsJsons = request.getParameter("res");
	String plan_id = request.getParameter("plan_id");
	String ebWidth = request.getParameter("ebWidth");
	String ebLength = request.getParameter("ebLength");
	String out_site_id = request.getParameter("out_site_id");

	String staffId = StaffUtil.getStaffId(request);
	try {
	    mainOutSiteService.updateElebar(pointsJsons, plan_id, out_site_id, staffId, ebWidth,
		    ebLength);
	} catch (Exception e) {

	    e.printStackTrace();
	    status = false;
	}
	map.put("status", status);
	try {
	    write(response, map);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * 防护方案确认表格页面
     * @author zhai_wanpeng
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "/showConfirmTbl.do")
    public ModelAndView showConfirmTbl(HttpServletRequest request, HttpServletResponse response,
	    String id,ModelMap model) {
	List<Map<String, Object>> rs = mainOutSiteService.findConfirmTbl(id);
	model.addAttribute("model", rs);
	return new ModelAndView("/outsite/mainOutSite/confirmTbl", model);
    }
    
    
    /**
     * 跳转至外力点到位率查询页面
     * @return
     */
    @RequestMapping("/outSiteRate")
    public ModelAndView outSiteRate(HttpServletRequest request){
    	Map<String, Object> map = new HashMap<String, Object>();
    	// 本地巡线员
    	String localId = StaffUtil.getStaffAreaId(request);
    	String orgId=lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
    	List<Map<String, Object>> localInspactStaff=lineInfoService.getLocalPerson(localId, orgId);
    	Map<String, Object> para=new HashMap<String, Object>();
		para.put("area_id", localId);
    	List<Map<String, Object>> orgList=xxdReportService.getScopeList(para);
    	map.put("localInspactStaff", localInspactStaff);
    	map.put("orgList", orgList);
    	return new ModelAndView("/outsite/mainOutSite/outSiteRate",map);
    }
    
    /**
     * 外力点到位率数据源
     * @param request
     * @return
     */
    @RequestMapping("/queryOutSiteRate")
    @ResponseBody
    public List<Map<String, Object>> queryOutSiteRate(HttpServletRequest request){
    	Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
    	String localId = StaffUtil.getStaffAreaId(request);
    	map.put("AREA_ID", localId);
    	List<Map<String, Object>>  resultList = mainOutSiteService.queryOutSiteRate(map);
    	return resultList;
    } 
    
    /**
     * 外力点到位率报表导出
     * @param request
     */
    @RequestMapping("/outSiteRateDownLoad")
    public void outSiteRateDownLoad(HttpServletRequest request,HttpServletResponse response){
    	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
    	para.put("AREA_ID", StaffUtil.getStaffAreaId(request));
    	mainOutSiteService.outSiteRateDownLoad(para, request, response);
    }
    
    /**
     * 外力点埋深探位页面跳转
     * @param request
     * @param response
     */
    @RequestMapping("/depthProbeInit")
    public ModelAndView depthProbeInit(HttpServletRequest request,HttpServletResponse response){
    	return new ModelAndView("/outsite/mainOutSite/depthOfProbe_index");
    }
    
    
    /**
     * 外力点埋深探位查询
     * @param request
     * @param response
     */
    @RequestMapping("/depthProbeQuery")
    @ResponseBody
    public List<Map<String, Object>> depthProbeQuery(HttpServletRequest request,HttpServletResponse response){
    	Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
    	para.put("area_id", StaffUtil.getStaffAreaId(request));
    	return mainOutSiteService.depthProbeQuery(para, request, response);
    }
    
    /**
     * 外力点位置在地图上显示
     * 
     */
    @RequestMapping("/getOuteSiteLocation.do")
    public  ModelAndView getOuteSiteLocation(HttpServletRequest request, HttpServletResponse response,
    		String out_site_id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("out_site_id", out_site_id);
    	List<Map<String, Object>> list=mainOutSiteService.getOuteSiteLocation(map);
    	map.put("list", list);
    	return new ModelAndView("/outsite/mainOutSite/mainOutSite_location", map);
    	 
        }
    
    
}
