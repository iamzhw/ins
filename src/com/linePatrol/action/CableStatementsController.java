package com.linePatrol.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.service.CableStatementsService;
import com.linePatrol.service.CutAndConnOfFiberService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.constant.RoleNo;
import com.system.sys.SystemListener;

/**
 * 光缆报表类
 * @author zhai_wanpeng
 *
 */
@Controller
@RequestMapping("/CableStatementsController")
public class CableStatementsController {
	@Autowired
	private CableStatementsService cableStatementsService;
	
	@Autowired
	private CutAndConnOfFiberService cutAndConnOfFiberService;
	
	/**
	 * 跳转至信号曲线检查统计详情页面
	 * @param model
	 * @param request
	 * @param localId
	 * @param yearPart
	 * @return
	 */
	@RequestMapping("/report203Init")
	public ModelAndView report203Init(ModelMap model,HttpServletRequest request,String localId,String yearPart){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("localId", localId);
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
		String year="";
		String upOrDown="";
		if(!("").equals(yearPart)){
			year=yearPart.substring(0,4);
			upOrDown=yearPart.substring(4, 6);
		}
		model.addAttribute("yearPart",year);
		model.addAttribute("upOrDown",upOrDown);
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/report203",model);
	}
	
	/**
	 * 信号曲线检查统计详情页面数据源
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/report203Query")
	@ResponseBody
	public Map<String, Object> report203Query(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String yearPart=(String) map.get("yearPart");
		if(!"".equals(yearPart)){
			yearPart+=(String)map.get("upOrDown");
			map.put("yearPart", yearPart);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cableStatementsService.report203Query(query));
		mapVal.put("total", query.getPager().getRowcount());
		return mapVal;
	}
	
	
	/**
	 * 跳转至光缆金属护套对地绝缘电阻测试记录统计详情页
	 * @param model
	 * @param request
	 * @param city_name
	 * @param cable_name
	 * @param relay_name
	 * @param yearPart
	 * @return
	 */
	@RequestMapping("/report207Init")
	public ModelAndView report207Init(ModelMap model,HttpServletRequest request,String city_name,String cable_name,String relay_name,String yearPart,String testdate){
		Map<String, Object> map=new HashMap<String, Object>();
//		String localId=null;
//		if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
//			localId=StaffUtil.getStaffAreaId(request);
//			map.put("localId", localId);
//		}
		map.put("localId", city_name);
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
//		model.addAttribute("localId",localId);
//		model.addAttribute("city_name",city_name);
		model.addAttribute("cable_name",cable_name);
		model.addAttribute("relay_name",relay_name);
		model.addAttribute("testdate",testdate);
		String year="";
		String upOrDown="";
		if(!("").equals(yearPart)){
			year=yearPart.substring(0,4);
			upOrDown=yearPart.substring(4, 6);
		}
		model.addAttribute("yearPart",year);
		model.addAttribute("upOrDown",upOrDown);
		
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/report207",model);
	}
	
	/**
	 * 光缆金属护套对地绝缘电阻测试记录统计详情页 数据源
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/report207Query")
	@ResponseBody
	public Map<String, Object> report207Query(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String yearPart=(String) map.get("yearPart");
		if(!"".equals(yearPart)){
			yearPart+=(String)map.get("upOrDown");
			map.put("yearPart", yearPart);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cableStatementsService.report207Query(query));
		mapVal.put("total", query.getPager().getRowcount());
		return mapVal;
	}
	
	/**
	 * 跳转至光缆金属护套对地绝缘电阻测试记录统计汇总表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/to207CollectionForm")
	public ModelAndView to207CollectionForm(ModelMap model,HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		String localId=null;
		if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
		model.addAttribute("localId",localId);
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/207collectionForm",model);
	}

	/**
	 * 光缆金属护套对地绝缘电阻测试记录统计汇总数据源
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/get207collection")
	@ResponseBody
	public Map<String, Object> get207collection(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String yearPart=(String) map.get("yearPart");
		if(!"".equals(yearPart)){
			yearPart+=(String)map.get("upOrDown");
			map.put("yearPart", yearPart);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cableStatementsService.get207collection(query));
		mapVal.put("total", query.getPager().getRowcount());
		return mapVal;
	}
	
	/**
	 * 跳转至架空光缆中继段地线电阻测试合格详情页
	 * @param model
	 * @param request
	 * @param city_name
	 * @param yearPart
	 * @return
	 */
	@RequestMapping("/report204Init")
	public ModelAndView report204Init(ModelMap model,HttpServletRequest request,String city_name,String yearPart){
		Map<String, Object> map=new HashMap<String, Object>();
//		String localId=null;
//		if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
//			localId=StaffUtil.getStaffAreaId(request);
//			map.put("localId", localId);
//		}
		map.put("localId", city_name);
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
//		model.addAttribute("localId",localId);
//		model.addAttribute("city_name",city_name);
		String year="";
		String upOrDown="";
		if(!("").equals(yearPart)){
			year=yearPart.substring(0,4);
			upOrDown=yearPart.substring(4, 6);
		}
		model.addAttribute("yearPart",year);
		model.addAttribute("upOrDown",upOrDown);
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/report204",model);
	}
	
	/**
	 * 架空光缆中继段地线电阻测试合格详情页 数据源
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/report204Query")
	@ResponseBody
	public Map<String, Object> report204Query(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String yearPart=(String) map.get("yearPart");
		if(!"".equals(yearPart)){
			yearPart+=(String)map.get("upOrDown");
			map.put("yearPart", yearPart);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cableStatementsService.report204Query(query));
		mapVal.put("total", query.getPager().getRowcount());
		return mapVal;
	}
	
	
	/**
	 * 跳转至架空光缆中继段地线电阻测试合格详情汇总表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/to204CollectionForm")
	public ModelAndView to204CollectionForm(ModelMap model,HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		String localId=null;
		if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
		model.addAttribute("localId",localId);
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/204collectionForm",model);
	}

	/**
	 * 架空光缆中继段地线电阻测试合格详情汇总表数据源
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/get204collection")
	@ResponseBody
	public Map<String, Object> get204collection(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String yearPart=(String) map.get("yearPart");
		if(!"".equals(yearPart)){
			yearPart+=(String)map.get("upOrDown");
			map.put("yearPart", yearPart);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cableStatementsService.get204collection(query));
		mapVal.put("total", query.getPager().getRowcount());
		return mapVal;
	}
	
	/**
	 * 信号曲线检查统计详情页行数据删除操作
	 * @param request
	 * @param str
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/delReport203")
	@ResponseBody
	public Map<String, Object> delReport203(HttpServletRequest request,String str){
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cableStatementsService.delReport203(str);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
	
	/**
	 * 架空光缆中继段地线电阻测试合格详情页行数据删除
	 * @param request
	 * @param str
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/delReport204")
	@ResponseBody
	public Map<String, Object> delReport204(HttpServletRequest request,String str){
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cableStatementsService.delReport204(str);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
	/**
	 * 光缆金属护套对地绝缘电阻测试记录统计汇总表行数据删除
	 * @param request
	 * @param str
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/delReport207")
	@ResponseBody
	public Map<String, Object> delReport207(HttpServletRequest request,String str){
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cableStatementsService.delReport207(str);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
	/**
	 * 跳转至信号曲线检查行数据修改页面
	 * @param request
	 * @param str
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("/toUpdReport203")
	public ModelAndView toUpdReport203(HttpServletRequest request,String str,ModelMap model,UIPage page){
		JSONObject json=JSONObject.fromObject(JSONArray.fromObject(str).get(0));
		Map<String, Object> map=new HashMap<String, Object>();
		Query query=new Query();
		map.put("city_name", json.get("city_name"));
		map.put("rowIndex", json.get("rowIndex"));
		map.put("yearPart", json.get("yearPart"));
		query.setQueryParams(map);
		query.setPager(page);
		model.addAttribute("rows",cableStatementsService.report203Query(query));
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/updReport203Form",model);
	}
	
	/**
	 * 信号曲线检查行数据修改操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/updReport203Info")
	@ResponseBody
	public Map<String, Object> updReport203Info(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cableStatementsService.updReport203Info(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 跳转至架空光缆中继段地线电阻测试修改页面
	 * @param request
	 * @param r_id
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("/toUpdReport204")
	public ModelAndView toUpdReport204(HttpServletRequest request,String r_id,ModelMap model,UIPage page){
		Map<String, Object> map=new HashMap<String, Object>();
		Query query=new Query();
		map.put("r_id", r_id);
		query.setQueryParams(map);
		query.setPager(page);
		model.addAttribute("rows",cableStatementsService.report204Query(query));
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/updReport204Form",model);
	}
	
	/**
	 * 架空光缆中继段地线电阻测试修改操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/updReport204Info")
	@ResponseBody
	public Map<String, Object> updReport204Info(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cableStatementsService.updReport204Info(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 跳转至光缆金属护套对地绝缘电阻测试记录统计修改页面
	 * @param request
	 * @param r_id
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("/toUpdReport207")
	public ModelAndView toUpdReport207(HttpServletRequest request,String r_id,ModelMap model,UIPage page){
		Map<String, Object> map=new HashMap<String, Object>();
		Query query=new Query();
		map.put("r_id", r_id);
		query.setQueryParams(map);
		query.setPager(page);
		model.addAttribute("rows",cableStatementsService.report207Query(query));
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/updReport207Form",model);
	}
	
	/**
	 * 光缆金属护套对地绝缘电阻测试记录统计修改操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/updReport207Info")
	@ResponseBody
	public Map<String, Object> updReport207Info(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cableStatementsService.updReport207Info(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 跳转至中继段光纤通道后向散射信号曲线检查记录统计表页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/FYPbyPartForm")
	public ModelAndView FYPbyPartForm(ModelMap model,HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		String localId=null;
		if(request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN) == null){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
		model.addAttribute("localId",localId);
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/FYPbyPartForm",model);
	}
	
	/**
	 * 中继段光纤通道后向散射信号曲线检查记录统计表页面数据源
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/getFYPbyPart")
	@ResponseBody
	public Map<String, Object> getFYPbyPart(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		if(!("").equals(map.get("yearPart"))){
			map.put("yearPart", map.get("yearPart")+""+map.get("upOrDown"));
		}
		List<Map<String, Object>> lists=cableStatementsService.getFYPbyPart(map);
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(lists.size()==0){
			result.put("rows", lists);
			return result;
		}
		resultMap.put("NAME", lists.get(0).get("NAME"));
		int part1=0;
		int part2=0;
		for (Map<String, Object> mp : lists) {
			if(mp.get("FIBER_GRADE").toString().equals("1")){
				resultMap.put("TOTALOFLINE1", mp.get("TOTAL"));
				resultMap.put("PART1OFLINE1", mp.get("PART1"));
				resultMap.put("PART2OFLINE1", mp.get("PART2"));
				resultMap.put("PEROFLINE1", mp.get("PER"));
				part1+=Integer.parseInt(mp.get("PART1").toString());
				part2+=Integer.parseInt(mp.get("PART2").toString());
			}else{
				resultMap.put("TOTALOFLINE2", mp.get("TOTAL"));
				resultMap.put("PART1OFLINE2", mp.get("PART1"));
				resultMap.put("PART2OFLINE2", mp.get("PART2"));
				resultMap.put("PEROFLINE2", mp.get("PER"));
				part1+=Integer.parseInt(mp.get("PART1").toString());
				part2+=Integer.parseInt(mp.get("PART2").toString());
			}
		}
		int total=part1+part2;
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
	    String per = df.format(part1*100/(float)total);//返回的是String类型的
	    resultMap.put("TOTAL",total);
	    resultMap.put("PART1", part1);
	    resultMap.put("PART2", part2);
	    resultMap.put("PER",per);
	    List<Map<String, Object>> all= new ArrayList<Map<String,Object>>();
	    all.add(resultMap);
	    result.put("rows", all);
		return result;
	}
	
	@RequestMapping("/toAddReport203")
	public ModelAndView toAddReport203Form(HttpServletRequest request,String localId,ModelMap model){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("localId", localId);
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/addReport203Form",model);
	}
	
	@RequestMapping("/addReport203Info")
	@ResponseBody
	public Map<String, Object> addReport203Info(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		map.put("yearpart", map.get("yearpart")+""+map.get("upOrDown"));
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cableStatementsService.addReport203(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/toAddReport204")
	public ModelAndView toAddReport204Form(HttpServletRequest request,String localId,ModelMap model){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("localId", localId);
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(map));
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/addReport204Form",model);
	}
	
	@RequestMapping("/addReport204Info")
	@ResponseBody
	public Map<String, Object> addReport204Info(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		map.put("yearpart", map.get("yearpart")+""+map.get("upOrDown"));
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cableStatementsService.addReport204(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/addSubExcelFiber")
	public ModelAndView addSubExcelFiber(HttpServletRequest request,ModelMap model,
			HttpServletResponse response){
		return new ModelAndView("/linePatrol/xunxianManage/cableStatements/importFiber");
	}
	
	
	@RequestMapping("/downloadExampleSubFiber.do")
	@ResponseBody
	public void addSubExcelFiber(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("信号检查曲线记录批量导入.xlsx").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/信号检查曲线记录批量导入.xlsx")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	@RequestMapping(value = "import_Date")
	@ResponseBody
	public void import_Date(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file) throws Exception {

		String responseStr = "";
		String info = "";
		info = cableStatementsService.importDo_Fiber(request, file);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(info);

	}
}
