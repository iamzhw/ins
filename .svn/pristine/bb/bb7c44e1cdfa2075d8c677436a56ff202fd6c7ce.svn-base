package com.linePatrol.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import icom.axx.service.CollectInfoOfRelayService;
import icom.axx.service.StepCheckService;
import icom.util.BaseServletTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linePatrol.service.CutAndConnOfFiberService;
import com.linePatrol.service.StepPartService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.constant.RoleNo;

import util.page.Query;
import util.page.UIPage;

@Controller
@SuppressWarnings("all")
@RequestMapping("/StepEquip")
public class StepEquipController {

	@Autowired
	private CollectInfoOfRelayService collectInfoOfRelayService;
	
	@Autowired
	private StepCheckService stepCheckService;
	
	@Autowired
	private CutAndConnOfFiberService cutAndConnOfFiberService; 
	
	@Resource
	private StepPartService stepPartService;
	
	@RequestMapping("/init")
	public ModelAndView init(ModelMap model,HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		String localId=null;
		if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
		model.addAttribute("localId",localId);
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/stepCheck",model);
	}
	
	/**
	 * 步巡设施查询
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/selEquipListForWEB")
	@ResponseBody
	public Map<String, Object> selEquipListForWEB(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Query query=new Query();
		query.setQueryParams(map);
		query.setPager(page);
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		List<Map<String, Object>> list = stepCheckService.selEquipListForWEB(query);
		resultMap.put("rows", list);
		resultMap.put("total",query.getPager().getRowcount());
		return resultMap;
	}
	/**
	 * TODO 删除步巡点设施
	 * @param relay_id
	 * @param area_id
	 * @param page
	 * @param cable_id
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/delStepEquip")
	public String delStepEquip(String relay_id,String area_id,String cable_id,Model model){
		Map<String, Object> paramap=new HashMap<String, Object>();
		paramap.put("area_id", area_id);
		paramap.put("relay_id", relay_id);
		paramap.put("cable_id", cable_id);
		
		List<Map<String, String>> result = stepCheckService.getStepEquip(paramap);
		if(result.size()>0 && result != null){
			model.addAttribute("cable_name", result.get(0).get("CABLE_NAME").toString());
			model.addAttribute("relay_name", result.get(0).get("RELAY_NAME").toString());
		}
		model.addAttribute("stepEquipList", result);
		return "/linePatrol/xunxianManage/collectInfoOfRelay/delEquip";	
	}
	
	@ResponseBody
	@RequestMapping("/doDelete.do")
	public Map doDelete(String ids){
		
		String msg = stepCheckService.deleteStepEquip(ids);
		Map map = new HashMap();
		map.put("msg", msg);
		return map;
	}
	
	
	@RequestMapping("/changeEquipInit")
	public ModelAndView changeEquipInit(String relay_id,String area_id,String page,String cable_id,String pageSize){
		Map<String, Object> paramap=new HashMap<String, Object>();
		paramap.put("area_id", area_id);
		paramap.put("relay_id", relay_id);
		paramap.put("cable_id", cable_id);
		if("".equals(page) || null==page){
			page="1";
		}
		paramap.put("page", page);
		if("".equals(pageSize) || null==pageSize){
			pageSize="200";
		}
		paramap.put("pageSize", pageSize);
		Map<String, Object> model = stepCheckService.stepEquipPart(paramap);
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/changeEquip",model);	
	}
	
	
	
	@RequestMapping("/saveEquipByChange")
	public void saveEquipByChange(String ids,String cable_id,String relay_id,String area_id,String pageSize,String page,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", 1);
		try {
			stepCheckService.saveEquipByChange(ids,cable_id,relay_id,area_id,page,pageSize,request);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		} finally{
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}
	}
	
	@RequestMapping("changeRelationEquipInit")
	public ModelAndView changeRelationEquipInit(HttpServletRequest request,ModelMap map,String relay_id,String area_id,String page,String cable_id){
		Map<String, Object> paramap=new HashMap<String, Object>();
		paramap.put("area_id", area_id);
		paramap.put("relay_id", relay_id);
		paramap.put("cable_id", cable_id);
		Map<String, Object> m = stepPartService.selNameByCRID(paramap);
		map.put("cable_name", m.get("CABLE_NAME"));
		map.put("relay_name", m.get("RELAY_NAME"));
		if("".equals(page) || null==page){
			page="1";
		}
		paramap.put("page", page);
		int pageSize = 200;
		paramap.put("pageSize", pageSize);
		paramap.put("count", stepCheckService.selEquipCount(paramap)%pageSize==0?
				stepCheckService.selEquipCount(paramap)/pageSize:stepCheckService.selEquipCount(paramap)/pageSize+1);
		map.put("el", JSONArray.fromObject(stepCheckService.changeRelationEquipInit(request, paramap)).toString());
		map.put("model", paramap);
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/changeRelationEquip",map);
	}
	
	@RequestMapping("changeRelationEquip")
	public void changeRelationEquip(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String para = BaseServletTool.getParam(request);
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			stepCheckService.changeRelationEquip(request, null);
			resultMap.put("status", 1);
		} catch (Exception e) {
			resultMap.put("status", 0);
			e.printStackTrace();
		}finally{
			response.getWriter().write(JSONObject.fromObject(resultMap).toString());
		}
	}
	
	/**
	 * 所有路由展示 供顺序链接
	 * @param relay_id
	 * @param area_id
	 * @param page
	 * @param cable_id
	 * @return
	 */
	@RequestMapping("/changeAllEquipInit")
	public ModelAndView changeAllEquipInit(String relay_id,String area_id,String cable_id){
		Map<String, Object> paramap=new HashMap<String, Object>();
		paramap.put("area_id", area_id);
		paramap.put("relay_id", relay_id);
		paramap.put("cable_id", cable_id);
		Map<String, Object> model = stepCheckService.stepAllEquipPart(paramap);
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/changeAllList",model);	
	}
	
	
	@RequestMapping("/saveAllEquipByChange")
	public void saveAllEquipByChange(String ids,String cable_id,String relay_id,String area_id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", 1);
		try {
			stepCheckService.saveAllEquipByChange(ids,cable_id,relay_id,area_id, request);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		} finally{
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}
	}
	
	@RequestMapping(value = "/edit.do")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs = stepCheckService.edit(request);
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/step_edit", rs);
	}
	
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		try {
			stepCheckService.update(request);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 * 孙敏
	 * 2016/6/12
	 * 界面显示未关联线路段落的步巡段
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selNoRelationPoint")
	public ModelAndView selNoRelationPoint(HttpServletRequest request,HttpServletResponse response){
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		Map<String, Object> model = stepCheckService.selNoRelationPoint(areaId);
		
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/selNoRelationPoint",model);	
	}
	
	/**
	 * 查看所有该区域段落的非路由点
	 * @param relay_id
	 * @param area_id
	 * @param cable_id
	 * @return
	 */
	@RequestMapping("/selNoRouthEquip")
	public ModelAndView selNoRouthEquip(String relay_id,String area_id,String cable_id){
		Map<String, Object> paramap=new HashMap<String, Object>();
		paramap.put("area_id", area_id);
		paramap.put("relay_id", relay_id);
		paramap.put("cable_id", cable_id);
		Map<String, Object> model = stepCheckService.selNoRouthEquip(paramap);
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/noRouthEquip",model);	
	}
}
