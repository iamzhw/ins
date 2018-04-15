/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.Query;
import util.page.UIPage;

import com.linePatrol.service.DangerOrderService;
import com.linePatrol.service.LineInfoService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.service.AreaService;
import com.system.service.GeneralService;
import com.system.service.ParamService;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/dangerOrderController")
@Controller
public class DangerOrderControler extends BaseAction {

	@Resource
	private DangerOrderService dangerOrderService;
	@Resource
	private LineInfoService lineInfoService;
	@Resource
	private GeneralService generalService;
	@Resource
	private ParamService paramService;
	@Resource
	private AreaService areaService;

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		Map<String, Object> para = new HashMap<String, Object>();
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		String orgId = lineInfoService.getOrgByRole(StaffUtil.getStaffId(request));
		List<Map<String, Object>> localInspactStaff = lineInfoService.getLocalPerson(localId, orgId);
		List<Map<String, Object>> scopeList = dangerOrderService.getScopeList(para);
		model.put("localInspactStaff", localInspactStaff);
		model.put("scopeList", scopeList);
		// 准备数据
		List<Map<String, Object>> dangerOrderState = dangerOrderService
				.getAllDangerOrderState();

		// Map<String, Object> map = new HashMap<String, Object>();
		model.put("dangerOrderState", dangerOrderState);

		return new ModelAndView(
				"linePatrol/xunxianManage/dangerOrder/dangerOrder_index", model);
	}

	@RequestMapping(value = "/query.do")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,UIPage pager) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> para2 = MapUtil.parameterMapToCommonMap(request);

		para2.put("areaId", StaffUtil.getStaffAreaId(request));
		// 去掉参数前面p add index页面参数名字冲突
		Set<String> set = para.keySet();// set 仅仅指针引用 非独立对象 不能再遍历的时候crud
		// ConcurrentModificationException
		for (String s : set) {
			if (s.startsWith("p_")) {
				String key = s.substring(2, s.length());
				para2.put(key, para.get(s));
			}
		}
		
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		Query query=new Query();
		query.setQueryParams(para2);
		query.setPager(pager);
		List<Map<String, Object>> olists = dangerOrderService.query(query);
		resultMap.put("rows", olists);
		resultMap.put("total",query.getPager().getRowcount());
		return resultMap;
	}

	@RequestMapping(value = "/dataDownload.do")
	public void dataDownload(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		Map<String, Object> para = MapUtil
				.parameterMapToCommonMapForChinese(request);
		Map<String, Object> para2 = MapUtil
				.parameterMapToCommonMapForChinese(request);
		para2.put("areaId", StaffUtil.getStaffAreaId(request));
		// 去掉参数前面p add index页面参数名字冲突
		Set<String> set = para.keySet();// set 仅仅指针引用 非独立对象 不能再遍历的时候crud
		// ConcurrentModificationException

		for (String s : set) {
			if (s.startsWith("p_")) {
				String key = s.substring(2, s.length());
				para2.put(key, para.get(s));
			}
		}
		dangerOrderService.dataDownload(para2, request, response);
	}

	@RequestMapping(value = "/dangerOrderAddUI.do")
	public ModelAndView dangerOrderAddUI(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 要展现的数据
		// 获得登录ID用户的信息
		// String staffId = BaseUtil.getStaffId(request);
		// map.put("staffId", staffId);
		// 隐患编号
		String danger_id = dangerOrderService.getDangerId();
		map.put("danger_id", danger_id);

		return new ModelAndView(
				"linePatrol/xunxianManage/dangerOrder/dangerOrder_add", map);
	}

	@RequestMapping(value = "/dangerOrderSave.do")
	@ResponseBody
	public Map dangerOrderSave(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		Map map = new HashMap();
		Boolean status = true;
		try {
			String created_by = StaffUtil.getStaffId(request);
			para.put("created_by", created_by);
			dangerOrderService.dangerOrderSave(para);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/dangerOrderEditUI.do")
	public ModelAndView dangerOrderEditUI(HttpServletRequest request,
			HttpServletResponse response, String id) {
		Map<String, Object> rs = null;
		// 准备数据
		List<Map<String, Object>> list = null;

		rs = dangerOrderService.findById(id);
		return new ModelAndView(
				"linePatrol/xunxianManage/dangerOrder/dangerOrder_edit", rs);

	}

	@RequestMapping(value = "/dangerOrderUpdate.do")
	@ResponseBody
	public Map dangerOrderUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		Boolean status = true;
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		try {
			dangerOrderService.dangerOrderUpdate(para);
		} catch (Exception e) {

			e.printStackTrace();
			status = false;
		}
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/dangerOrderDelete.do")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, String ids) {
		Map map = new HashMap();

		map.put("status", true);
		try {
			dangerOrderService.dangerOrderDelete(ids);
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

	@RequestMapping(value = "/distributeUI.do")
	public ModelAndView distributeUI(HttpServletRequest request,
			HttpServletResponse response, String order_id, String founder_uid,
			String danger_question) throws UnsupportedEncodingException {
		// Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_id", order_id);
		map.put("founder_uid", founder_uid);
		map.put("danger_question", new String(danger_question
				.getBytes("iso-8859-1"), "utf-8"));

		// 接单人 --巡检人员
		String localId = StaffUtil.getStaffAreaId(request);
		List<Map<String, Object>> handlePerson = lineInfoService
				.getLocalInspactPerson(localId);
		map.put("handlePerson", handlePerson);

		return new ModelAndView(
				"linePatrol/xunxianManage/dangerOrder/dangerOrder_distributeUI",
				map);

	}

	@RequestMapping(value = "/findHandlePerson.do")
	public void findHandlePerson(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {

		Map<String, Object> map = dangerOrderService.findHandlePerson();
		write(response, map);
	}

	@RequestMapping(value = "/dangerOrderDistribute.do")
	public void dangerOrderDistribute(HttpServletRequest request,
			HttpServletResponse response, String ids) {

		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("distribute_person", StaffUtil.getStaffId(request));

		Map map = new HashMap();
		map.put("status", true);
		try {
			dangerOrderService.dangerOrderDistribute(para);
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

	@RequestMapping(value = "/receipt.do")
	public void receipt(HttpServletRequest request,
			HttpServletResponse response, String ids) {

		Map map = new HashMap();
		map.put("status", true);
		try {
			dangerOrderService.receipt(ids);
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

	@RequestMapping(value = "/auditUI.do")
	public ModelAndView auditUI(HttpServletRequest request,
			HttpServletResponse response, String order_id) {
		Map<String, Object> map = dangerOrderService.findDetail(order_id);
		return new ModelAndView(
				"linePatrol/xunxianManage/dangerOrder/dangerOrder_auditUI", map);

	}

	@RequestMapping(value = "/audit.do")
	public void audit(HttpServletRequest request, HttpServletResponse response) {

		Map map = new HashMap();
		map.put("status", true);
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("audit_person", StaffUtil.getStaffId(request));
		try {
			dangerOrderService.audit(para);
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

	@RequestMapping(value = "/detailUI.do")
	public ModelAndView detailUI(HttpServletRequest request,
			HttpServletResponse response, String order_id) {

		Map<String, Object> map = dangerOrderService.findDetail(order_id);

		// 获取隐患id site_id photo_type
		String dangerID = dangerOrderService.getDangerIdByOrder(order_id);
		Map<String, Object> map_zzq = new HashMap<String, Object>();
		map_zzq.put("site_id", dangerID);
		map_zzq.put("photo_type", 2);// 整改前
		List<Map<String, Object>> photoList_zzq = generalService
				.getPhoto(map_zzq);

		Map<String, Object> map_zzh = new HashMap<String, Object>();
		map_zzq.put("site_id", order_id);
		map_zzq.put("photo_type", 6);// 整改后
		List<Map<String, Object>> photoList_zzh = generalService
				.getPhoto(map_zzq);

		map.put("photoList_zzq", photoList_zzq);
		map.put("photoList_zzh", photoList_zzh);
		return new ModelAndView(
				"linePatrol/xunxianManage/dangerOrder/dangerOrder_detailUI",
				map);

	}

	@RequestMapping(value = "/findDetail.do")
	public void findDetail(HttpServletRequest request,
			HttpServletResponse response, String order_id) {

		// //Map<String, Object> map = dangerOrderService.findDetail(order_id);
		// try {
		// // write(response, map);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@RequestMapping(value = "/doStatisticIndex.do")
	public ModelAndView doStatisticIndex(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		// 准备数据
		// Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map = paramService.query(request);
		String isAdmin = map.get("isAdmin").toString();

		if ("0".equals(isAdmin)) {// 全省
			List<Map<String, Object>> areaList = areaService.getAllArea();
			map.put("areaList", areaList);

		} else {// 地市
			String localId = StaffUtil.getStaffAreaId(request);
			List<Map<String, Object>> localInspacPeople = lineInfoService
					.getLocalInspactPerson(localId);
			map.put("localInspacPeople", localInspacPeople);
		}

		return new ModelAndView(
				"linePatrol/xunxianManage/dangerOrder/dangerOrder_statistic",
				map);
	}

	@RequestMapping(value = "/doStatistic.do")
	public void doStatistic(HttpServletRequest request,
			HttpServletResponse response, UIPage uiPage) {

		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);

		String area_id = StaffUtil.getStaffAreaId(request);
		map.put("area_id", area_id);

		Map<String, Object> res = dangerOrderService.doStatistic(map, uiPage);
		try {
			write(response, res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getPhoto.do")
	public ModelAndView getPhoto(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		// 准备数据
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		List<Map<String, Object>> photoList = generalService.getPhoto(para);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("photoList", photoList);
		return new ModelAndView(
				"linePatrol/xunxianManage/dangerOrder/danger_photo", map);
	}

	@RequestMapping(value = "/paidan.do")
	public void paidan(HttpServletRequest request,
			HttpServletResponse response, String ids) {

		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("distribute_person", StaffUtil.getStaffId(request));
		Map map = new HashMap();
		map.put("status", true);
		try {
			dangerOrderService.paidan(para);
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

	@RequestMapping(value = "/doDownload.do")
	public void doDownload(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		para.put("area_id", StaffUtil.getStaffAreaId(request));
		dangerOrderService.doDownload(para, request, response);
	}

	@RequestMapping(value = "/searchByAdmin.do")
	public void searchByAdmin(HttpServletRequest request,
			HttpServletResponse response, UIPage uiPage) {

		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

		Map<String, Object> res = dangerOrderService.searchByAdmin(para);
		try {
			write(response, res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/downloadByAdmin.do")
	public void downloadByAdmin(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);

		dangerOrderService.downloadByAdmin(para, request, response);
	}

	@RequestMapping(value = "/deleteDanger.do")
	public void deleteDanger(HttpServletRequest request,
			HttpServletResponse response, int ids) {
		Map map = new HashMap();

		try {
			dangerOrderService.deleteDanger(ids);
		} catch (Exception e) {

			e.printStackTrace();

			map.put("status", false);
		}
		map.put("status", true);
		try {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
