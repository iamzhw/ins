package com.linePatrol.action;

import icom.axx.service.StepCheckService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.linePatrol.service.LineInfoService;
import com.linePatrol.service.LineTaskService;
import com.linePatrol.service.StepPartService;
import com.linePatrol.service.StepPartTaskService;
import com.linePatrol.service.gldManageService;
import com.linePatrol.service.impl.GeneratePartTaskJob;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.linePatrol.util.StepPartTaskUtil;
import com.linePatrol.util.StringUtil;
import com.system.constant.RoleNo;
import com.system.service.AreaService;
import com.system.service.ParamService;
import com.system.sys.SystemListener;

import util.page.BaseAction;
import util.page.UIPage;

import com.linePatrol.util.StepPartTaskUtil;

/**
 * 步巡段控制层管理类
 * 
 * @author sunmin
 * @since 2016-05-01
 * 
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/StepPart")
public class StepPartController extends BaseAction {
	
	@Resource
	private LineTaskService lineTaskService;

	@Resource
	private StepPartService StepPartService;

	@Resource
	private gldManageService gldManageService;

	@Resource
	private LineInfoService lineInfoService;

	@Autowired
	private StepCheckService stepCheckService;
	
	@Resource
    private AreaService areaService;
	
	 @Resource
	 private ParamService paramService;
	 
	 @Resource
	 private StepPartTaskService stepparttaskservice;
	
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) {
		// 角色
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, Object> res = paramService.query(request);
		String isAdmin = res.get("isAdmin").toString();// 0-省 1地市
		if ("0".equals(isAdmin)) {
		    List<Map<String, Object>> areaList = areaService.getAllArea();
		    map.put("areaList", areaList);
		}else {
			// 本地光缆
		    List<Map<String, Object>> cableList = StepPartService.getGldByAreaId(StaffUtil.getStaffAreaId(request));
		    map.put("cableList", cableList);
		}
		map.put("isAdmin", isAdmin);
		return new ModelAndView("/linePatrol/xunxianManage/steppart/steppart_index", map);
	}

	@RequestMapping(value = "/query.do")
	public void query(HttpServletRequest request, HttpServletResponse response,UIPage pager) throws IOException {
		
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String area_id = request.getParameter("p_area_id");
		if (StringUtil.isNullOrEmpty(area_id)) {
		    area_id = StaffUtil.getStaffAreaId(request);
		}
		para.put("area_id", area_id);
		Map<String, Object> map = StepPartService.query(para, pager);// 查询数据
		write(response, map);
	}

	/**
	 * 获取当前区域下的线路段落
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/SelCableAndRelay.do")
	public ModelAndView SelCableAndRelay(HttpServletRequest request,HttpServletResponse response,String area_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNullOrEmpty(area_id)) {
		    area_id = StaffUtil.getStaffAreaId(request);
		}
		map.put("area_id", area_id);
		List<Map<String, Object>> cableList = StepPartService.getGldByAreaId(area_id);
		map.put("cableList", cableList);
		return new ModelAndView("/linePatrol/xunxianManage/steppart/steppart_seacher", map);
	}

	/**
	 * 根据光缆id获取到底下的线路id
	 * 
	 * @param request
	 * @param response
	 * @param cable_id
	 */
	@RequestMapping(value = "/getRelay.do")
	public void getRelay(HttpServletRequest request,HttpServletResponse response, String cable_id,String area_id) {
		Map map = new HashMap();
		map.put("status", true);
		List<Map<String, Object>> relayList = null;
		if (StringUtil.isNullOrEmpty(area_id)) {
		    area_id = StaffUtil.getStaffAreaId(request);
		}
		map.put("cable_id", cable_id);
		map.put("area_id", area_id);
		try {
			relayList = StepPartService.getRelayByCableId(map);

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

	/**
	 * 所有路由展示 供顺序链接，展示已经连线的步巡设施页面
	 * 
	 * @param relay_id
	 * @param page
	 * @param cable_id
	 * @return
	 */
	@RequestMapping("/addStepPart")
	public ModelAndView addStepPart(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> paramap = new HashMap<String, Object>();
		String relay_id = request.getParameter("relay_id");
		String cable_id = request.getParameter("cable_id");
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		// 查询本地的巡线员
		List<Map<String, Object>> inspects = lineTaskService.getInpectStaff(areaId);
		paramap.put("relay_id", relay_id);
		paramap.put("cable_id", cable_id);
		paramap.put("areaId", areaId);
		Map<String, Object> model = StepPartService.addStepPart(paramap);
		model.put("inspects", inspects);
		// 根据区域id和当前光缆id查询这条干线的频次
		String trunkcircle = StepPartService.judgeCircle(paramap);
		model.put("trunkcircle", trunkcircle);
		return new ModelAndView("/linePatrol/xunxianManage/steppart/addStepPart", model);
	}

	@RequestMapping("/judgeOnlyStepPartName")
	public void judgeOnlyStepPartName(String steppart_name,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		try {
			List<Map<String, Object>> steppart_names = StepPartService.selOnlyStepPartName(steppart_name);
			if (steppart_names.size() > 0) {
				map.put("status", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 1);
		} finally {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}
	}

	@RequestMapping("/judgeIsTaskEquip")
	public void judgeIsTaskEquip(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		String relay_id = request.getParameter("relay_id");
		String cable_id = request.getParameter("cable_id");
		String strat_equip = request.getParameter("strat_equip");// 查找起点的order
		String new_start_order = StepPartService.selOrderByEquipID(strat_equip);
		String end_equip = request.getParameter("end_equip");// 查找终点的order
		String new_end_order = StepPartService.selOrderByEquipID(end_equip);
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		
		Map<String, Object> paramap = new HashMap<String, Object>();
		// 判断步巡段id是否为空，以便于判断是修改还是增加时候判断
		String allot_id = request.getParameter("allot_id");
		if (allot_id != null && allot_id != "") {
			paramap.put("allot_id", allot_id);
		}
	 
	 if (Double.valueOf(new_start_order) < Double.valueOf(new_end_order)) {
			paramap.put("new_start_order", new_start_order);
			paramap.put("new_end_order", new_end_order);
		} else {
			paramap.put("new_start_order", new_end_order);
			paramap.put("new_end_order", new_start_order);
		}
		paramap.put("relay_id", relay_id);
		paramap.put("cable_id", cable_id);
		paramap.put("areaId", areaId);
		List<Map<String, Object>> lists = StepPartService.judgeIsTaskEquip(paramap);

		if (lists.size() > 0) {
			map.put("status", 0);
		}
		try {

		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		} finally {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}
	}

	@RequestMapping("/saveStepPart")
	public void saveStepPart(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);

		String steppart_name = request.getParameter("steppart_name");
		String start_equip = request.getParameter("start_equip");
		String end_equip = request.getParameter("end_equip");
	
		String inspect_id = request.getParameter("inspect_id");
		String cable_id = request.getParameter("cable_id");
		String relay_id = request.getParameter("relay_id");
		String circle_id = request.getParameter("circle_id");
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		String creator = request.getSession().getAttribute("staffId").toString();
		String new_start_order = StepPartService.selOrderByEquipID(start_equip);
		String new_end_order = StepPartService.selOrderByEquipID(end_equip);
		if (Double.valueOf(new_start_order) < Double.valueOf(new_end_order)) {
			map.put("start_equip", start_equip);
			map.put("end_equip", end_equip);
		} else {
			map.put("start_equip", end_equip);
			map.put("end_equip", start_equip);
		}
		map.put("steppart_name", steppart_name);
		map.put("inspect_id", inspect_id);
		map.put("cable_id", cable_id);
		map.put("relay_id", relay_id);
		map.put("circle_id", circle_id);
		map.put("areaId", areaId);
		map.put("creator", creator);
		map.put("areaId", areaId);
		try {
			StepPartService.insertEquipAllot(map);
		    StepPartService.upIsPartByMap(map);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		} finally {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}
	}

	@RequestMapping(value = "/toUpdate.do")
	public ModelAndView toUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		String allot_id = request.getParameter("ALLOT_ID").toString();

		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		List<Map<String, Object>> inspects = lineTaskService.getInpectStaff(areaId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", areaId);// 区域ID
		map.put("allot_id", allot_id);
		// 根据步巡段id查询出来频次
		String trunkcircle = StepPartService.judgeCircleByAllotID(map);

		// 传值的map
		Map<String, Object> model = StepPartService.upSelEquip(map);

		model.put("inspects", inspects);
		model.put("trunkcircle", trunkcircle);
		return new ModelAndView("/linePatrol/xunxianManage/steppart/toUpdate", model);
	}

	@RequestMapping("/upSaveStepPart")
	public void upSaveStepPart(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);

		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		map.put("areaId", areaId);
		
		String allot_id = request.getParameter("allot_id");
		map.put("allot_id", allot_id);
		// 首先先跟据步巡段id去把与该步巡段相关联的中间表和数据都给回归,即is_part字段全部设置为0
		StepPartService.upIsPartByAllotID(map);

		String steppart_name = request.getParameter("steppart_name");
		String start_equip = request.getParameter("start_equip");
		String end_equip = request.getParameter("end_equip");
		String inspect_id = request.getParameter("inspect_id");
		String cable_id = request.getParameter("cable_id");
		String relay_id = request.getParameter("relay_id");
		String circle_id = request.getParameter("circle_id");
		int is_change_persion = Integer.parseInt(request.getParameter("is_change_persion"));
		
		String updator = request.getSession().getAttribute("staffId").toString();
		String new_start_order = StepPartService.selOrderByEquipID(start_equip);
		String new_end_order = StepPartService.selOrderByEquipID(end_equip);
		if (Double.valueOf(new_start_order) < Double.valueOf(new_end_order)) {
			map.put("start_equip", start_equip);
			map.put("end_equip", end_equip);
		} else {
			map.put("start_equip", end_equip);
			map.put("end_equip", start_equip);
		}
		map.put("steppart_name", steppart_name);
		map.put("inspect_id", inspect_id);
		map.put("cable_id", cable_id);
		map.put("relay_id", relay_id);
		map.put("circle_id", circle_id);

		map.put("updator", updator);
//		StepPartController spc = new StepPartController();
		try {
			StepPartService.upStepAllotByAllotID(map);
			StepPartService.upIsPartByMap(map);
			
			String lmid=stepparttaskservice.selLMTypeId();//查询地标id
			String landMarkCircle=stepparttaskservice.selCircleByLandMark();//获取地标周期
			Boolean isNoLandMark = hasNoLandMarkByAllot(allot_id, lmid);//判断当前步巡段和未修改之前是否包含非地标步巡点
			Boolean isLandMark = hasLandMarkByAllot(allot_id, lmid);//判断当前步巡段和未修改之前是否包含地标步巡点
			
			Map<String, Object> allotMap=StepPartService.selCRByAllotID(allot_id);//查询该步巡段修改完成之后相关信息
			
			//当人员不一致的时候就先把当前人员底下的任务都给创建了并且将任务点的任务Id给替换为新的任务id
			if(is_change_persion == 1){
			   //先为变更的用户创建任务(如果没有这种任务),然后将之前未改变时人员的任务点移动到
				if(isNoLandMark){//先判断是否有非地标点
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("INSPECT_ID",inspect_id);
					param.put("CIRCLE_ID",circle_id);
					//判断当前人员是否有未过期该频次任务
					Map<String, Object> taskMap= stepparttaskservice.selTaskByPersionAndCircle(param);
					if(taskMap != null){//如果map不为空,获取到任务的id,并且将目前相关步巡段任务点的id全给替换为当前id
						String task_id = taskMap.get("TASK_ID").toString();
						 //将当前任务点表中的该步巡段相关频次的任务id给替换为新生成任务id
					    Map<String,Object> paramTask = new HashMap<String, Object>();
					    paramTask.put("circle_id", circle_id);
					    paramTask.put("allot_id", allot_id);
					    paramTask.put("task_id", task_id);
					    stepparttaskservice.upTaskIdFromTaskEquip(paramTask);
					    
					}else{//为空就新生成任务,然后把相关步巡段的所有该周期任务点id给替换了
						String task_id = stepparttaskservice.selTaskId();
						int currentMonth = DateUtil.getCurrentMonth();//获取当前月
						int[] lineMonths = null;
						if(Integer.parseInt(circle_id) == 2){//一干开始月份数组
							lineMonths = StepPartTaskUtil.oneLevelGroundline;
						}else if(Integer.parseInt(circle_id) == 3){//二干开始月份数组
							lineMonths = StepPartTaskUtil.twoLevelGroundline;
						}
						//根据当前月份,和当前月份数组获取开始月份
						int begin_month= DateUtil.getBeginMOnth(lineMonths, currentMonth);
						String start_time=DateUtil.getFirstDayOfMonth(begin_month);//获取开始月的1号
						String end_time=DateUtil.getTimeByCycle(begin_month, Integer.parseInt(circle_id));//获取结束时间
					    String staff_name=allotMap.get("STAFF_NAME").toString();
					    String task_name=staff_name+"_编号:"+inspect_id+": "+String.valueOf(circle_id)+"月任务"+
								   start_time+"至"+end_time;
					    param.put("START_TIME", start_time);
					    param.put("END_TIME", end_time);
					    param.put("TASK_NAME", task_name);
					    param.put("TASK_ID", task_id);
					    param.put("AREA_ID", areaId);
					    stepparttaskservice.insertStepPartTask(param);//插值到步巡任务表非地标
					    //将当前任务点表中的该步巡段相关频次的任务id给替换为新生成任务id
					    Map<String,Object> paramTask = new HashMap<String, Object>();
					    paramTask.put("circle_id", circle_id);
					    paramTask.put("allot_id", allot_id);
					    paramTask.put("task_id", task_id);
					    stepparttaskservice.upTaskIdFromTaskEquip(paramTask);
					}
					
				}
				
				//判断地标任务了
				if(isLandMark){
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("INSPECT_ID",inspect_id);
					param.put("CIRCLE_ID",landMarkCircle);//地标6个月周期放上去
					Map<String, Object> taskMap= stepparttaskservice.selTaskByPersionAndCircle(param);//查询当前人员有无地标任务
					
					if(taskMap != null){//如果map不为空,获取到任务的id,并且将目前相关步巡段任务点的id全给替换为当前id
						String task_id = taskMap.get("TASK_ID").toString();
						 //将当前任务点表中的该步巡段相关频次的任务id给替换为新生成任务id
					    Map<String,Object> paramTask = new HashMap<String, Object>();
					    paramTask.put("circle_id", landMarkCircle);
					    paramTask.put("allot_id", allot_id);
					    paramTask.put("task_id", task_id);
					    stepparttaskservice.upTaskIdFromTaskEquip(paramTask);
					    
					}else{//为空就新生成任务,然后把相关步巡段的所有任务点id给替换了
						String task_id = stepparttaskservice.selTaskId();
						int currentMonth = DateUtil.getCurrentMonth();//获取当前月
						int[] lineMonths = StepPartTaskUtil.landMarkGroundline;

						//根据当前月份,和当前月份数组获取开始月份
						int begin_month= DateUtil.getBeginMOnth(lineMonths, currentMonth);
						String start_time=DateUtil.getFirstDayOfMonth(begin_month);//获取开始月的1号
						String end_time=DateUtil.getTimeByCycle(begin_month, Integer.parseInt(landMarkCircle));//获取结束时间
					    String staff_name=allotMap.get("STAFF_NAME").toString();
					    String task_name=staff_name+"_编号:"+inspect_id+": "+String.valueOf(landMarkCircle)+"月地标任务"+
								   start_time+"至"+end_time;
					    param.put("START_TIME", start_time);
					    param.put("END_TIME", end_time);
					    param.put("TASK_NAME", task_name);
					    param.put("TASK_ID", task_id);
					    param.put("AREA_ID", areaId);
					    param.put("EQUIP_TYPE", lmid);
					    stepparttaskservice.insertLMStepPartTask(param);//插值到步巡任务表非地标
					    //将当前任务点表中的该步巡段相关频次的任务id给替换为新生成任务id
					    Map<String,Object> paramTask = new HashMap<String, Object>();
					    paramTask.put("circle_id", landMarkCircle);//地标6个月周期的
					    paramTask.put("allot_id", allot_id);
					    paramTask.put("task_id", task_id);
					    stepparttaskservice.upTaskIdFromTaskEquip(paramTask);
					}
				}
			}
			
			//增加或者删除任务点
			Map<String,Object> upRouthParam = new HashMap<String, Object>();//修改任务点参数
			upRouthParam.put("EQUIP_TYPE", lmid);
			upRouthParam.put("ALLOT_ID", allot_id);
			//先将多余的路由非地标任务点给移除到历史表中,然后删除多余点。再新增新的设施点
			stepparttaskservice.intoTaskHisNoLM(upRouthParam);
			stepparttaskservice.delTaskEquipNoLM(upRouthParam);
			stepparttaskservice.intoTaskEquipAddNoLM(upRouthParam);
			
			//处理路由地标任务点
			upRouthParam.put("CIRCLE_ID", landMarkCircle);//插入地标时候使用的
			stepparttaskservice.insertToTaskHis(upRouthParam);//保存要删除的记录到历史表
			stepparttaskservice.delTaskEquipLM(upRouthParam);//删除设施任务表多余的记录
			stepparttaskservice.intoTaskEquipAddLM(upRouthParam);//插入步巡段新加的设施点
			
			//插入新增的非地标非路由非地标设施点
			stepparttaskservice.intoHisTaskEquipNoRouthLM(upRouthParam);//先将多余的非路由非地标任务点给备份到任务表中
			stepparttaskservice.delHisTaskEquipNoRouthLM(upRouthParam);//将刚刚备份到历史表的非路由非地标任务点全部删除
			stepparttaskservice.intoTaskHisNoLMRoute(upRouthParam);//插入新增的非路由非地标任务点
			
			//处理非路由地标任务点
			stepparttaskservice.intoHisTaskLMEquipNoRouth(upRouthParam);//先将多余的非路由地标任务点给备份到任务表中
			stepparttaskservice.delHisTaskLMEquipNoRouth(upRouthParam);//将刚刚备份到历史表的非路由地标任务点全部删除
			stepparttaskservice.intoTaskEquipAddLMRoute(upRouthParam);//插入新增的非路由地标点
			
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		} finally {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}
	}

	@RequestMapping("/delStepPart")
	public void delStepPart(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		String allot_id = request.getParameter("allot_id");
		
		String areaId = String.valueOf(request.getSession().getAttribute("areaId"));
		map.put("allot_id", allot_id);
		map.put("areaId", areaId);
		try {
			// 先设置中间表axx_line_equip中is_part字段为0，再去执行删除操作
			StepPartService.upIsPartByAllotID(map);
			StepPartService.delStepPart(allot_id);
			
			//将该步巡段上所有已有任务点先备份到历史表中，然后删除
			String user_id = request.getSession().getAttribute("staffId").toString();// 当前用户
			map.put("user_id",user_id);
			StepPartService.intoHisDelAllot(map);//将已有该步巡段任务点备份到历史表
			StepPartService.delHisDelAllot(map);//将已有该步巡段任务点全部删除
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		} finally {
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}

	}
	
	@RequestMapping("/getGldByAreaId")
    public void getGldByAreaId(HttpServletRequest request, HttpServletResponse response,String area_id) {
	Map map = new HashMap();
	map.put("status", true);
	try {
	    List<Map<String, Object>> calbeList = StepPartService.getGldByAreaId(area_id);
	    map.put("cableList", calbeList);
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
	* 判断这条步巡段和未修改之前是否包含非地标步巡点
	* @param allot_id
	* @param equip_type
	* @return
	*/
	public boolean hasNoLandMarkByAllot(String allot_id,String equip_type){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ALLOT_ID", allot_id);
		param.put("EQUIP_TYPE", equip_type);
		//当前是否包含路由非地标点
		List<Map<String,Object>> isNoLandMark =  stepparttaskservice.selIsContainNoLandMark(param);
		//当前是否包含非路由非地标
		List<Map<String,Object>> isNoRouthLandMark = stepparttaskservice.selIsContainNoRouthLandMark(param);
		//判断之前任务点中的步巡点是否包含非地标点
		List<Map<String,Object>> isNoLandMarkByTaskEquip = stepparttaskservice.selTaskEquipHasNoLM(param);
		
		if(isNoLandMark.size()>0 || isNoRouthLandMark.size()>0 || isNoLandMarkByTaskEquip.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	* 判断这条步巡段和未修改之前是否包含地标步巡点
	* @param allot_id
	* @param equip_type
	* @return
	*/
	public boolean hasLandMarkByAllot(String allot_id,String equip_type){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ALLOT_ID", allot_id);
		param.put("EQUIP_TYPE", equip_type);
		//当前是否包含路由地标点
		List<Map<String,Object>> isLandMark =  stepparttaskservice.selIsContainLandMark(param);
		//当前是否包含非路由地标点
		List<Map<String,Object>> isRouthLandMark = stepparttaskservice.selIsContainRouthLandMark(param);
		//判断之前任务点中的步巡点是否包含地标点
		List<Map<String,Object>> isLandMarkByTaskEquip = stepparttaskservice.selTaskEquipHasLM(param);
		
		if(isLandMark.size()>0 || isRouthLandMark.size()>0 || isLandMarkByTaskEquip.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	@RequestMapping(value = "/addStepEquip.do")
    public ModelAndView addStepEquip(HttpServletRequest request, HttpServletResponse response) {
    	String cableId = request.getParameter("cableId");
    	String relayId = request.getParameter("relayId");
    	String allotId = request.getParameter("allotId");
    	Map<String,Object> res = new HashMap<String,Object>();
    	res.put("cableId", cableId);
    	res.put("relayId", relayId);
    	res.put("allotId", allotId);
    	return new ModelAndView("/linePatrol/xunxianManage/steppart/stepEquip_list",res);
    }
	
	@RequestMapping(value = "/queryStepEquipList.do")
	public void queryStepEquipList(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		String cableId = request.getParameter("cableId");
    	String relayId = request.getParameter("relayId");
    	Map param = new HashMap();
    	param.put("cable_id", cableId);
    	param.put("relay_id", relayId);
		Map<String, Object> map = StepPartService.queryStepEquipList(param, pager);
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
					+ new String(("设施点导入样例.xls").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/设施点导入样例.xls")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
    
    //导入设施点
    @RequestMapping("import_save")
	@ResponseBody
	public void importDo(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file)
					throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
				StepPartService.importDo(request, file).toString());
	}
    
    //导出设施点
	@RequestMapping(value = "/export.do")
	public String export(HttpServletRequest request, ModelMap mm) {
		String cableId = request.getParameter("cableId");
    	String relayId = request.getParameter("relayId");
    	String allotId = request.getParameter("allotId");
    	Map param = new HashMap();
    	param.put("cable_id", cableId);
    	param.put("relay_id", relayId);
		List<Map> dataList = (List<Map>) StepPartService.queryStepEquipList(param,new UIPage()).get("rows");
		Map stepPart = StepPartService.selCRByAllotID(allotId);
		String[] cols = new String[] { "EQUIP_CODE", "LONGITUDE", "LATITUDE",
				"EQUIP_ADDRESS","DESCRIPTION","EQUIP_TYPE_NAME","OWNER_NAME","OWNER_TEL","PROTECTER","PROTECT_TEL","IS_EQUIPDES","AREA_NAME","DEPTH"};
		mm.addAttribute("cols", cols);
		String[] colsName = new String[] { "设施编号", "经度", "纬度",
				"设施位置", "设施描述", "设施类型", "户主姓名", "户主电话","义务护线员姓名", "义务护线员电话", "是否路由", "区域", "深度"};
		mm.addAttribute("name", stepPart.get("STEPPART_NAME")+"-设施点");
		mm.addAttribute("colsName", colsName);
		mm.addAttribute("dataList", dataList);
		return "dataListExcel";
	}
		
}
