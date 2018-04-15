package com.linePatrol.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import icom.axx.service.CollectInfoOfRelayService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linePatrol.service.CutAndConnOfFiberService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.constant.RoleNo;

import util.page.Query;
import util.page.UIPage;

@Controller
@RequestMapping("/CollectInfoOfRelay")
public class CollectInfoOfRelayController {

	@Autowired
	private CollectInfoOfRelayService collectInfoOfRelayService;
	
	@Autowired
	private CutAndConnOfFiberService cutAndConnOfFiberService; 
	
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
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/collectEquipOfRelay",model);
	}
	
	@RequestMapping("/selEquipListForWEB")
	@ResponseBody
	public Map<String, Object> selEquipListForWEB(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Query query=new Query();
		query.setQueryParams(map);
		query.setPager(page);
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		List<Map<String, Object>> list = collectInfoOfRelayService.selEquipListForWEB(query);
		resultMap.put("rows", list);
		resultMap.put("total",query.getPager().getRowcount());
		return resultMap;
	}
	
	@RequestMapping("/showDetail")
	public ModelAndView showDetail(String equip_id,ModelMap model,UIPage page){
		Query query=new Query();
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("equip_id", equip_id);
		query.setQueryParams(paraMap);
		query.setPager(page);
		List<Map<String, Object>> list = collectInfoOfRelayService.selEquipListForWEB(query);
		model.addAttribute("model", list);
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/detailInfoOfEquip",model);
		
	}
	
	@RequestMapping("/showByMap")
	public ModelAndView showByMap(String jsonStr,ModelMap model){
		JSONObject jb = JSONObject.fromObject(jsonStr);
		Map<String, Object> paramap=new HashMap<String, Object>();
		if(jb.containsKey("city_name")){
			paramap.put("area_id", jb.get("city_name"));
		}
		if (jb.containsKey("cable_id")) {
			paramap.put("cable_id", jb.get("cable_id"));
		}
		if (jb.containsKey("relay_id")) {
			paramap.put("relay_id", jb.get("relay_id"));
		}
		if(jb.containsKey("equip_type")){
			paramap.put("equip_type", jb.get("equip_type"));
		}
		String str = collectInfoOfRelayService.showByMap(paramap);
		model.put("jbStr", str);
		return new ModelAndView("/linePatrol/xunxianManage/collectInfoOfRelay/showByMap",model);	
	}
	
}
