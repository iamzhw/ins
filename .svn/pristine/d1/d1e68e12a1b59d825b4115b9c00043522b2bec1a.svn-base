package com.linePatrol.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.linePatrol.service.CutAndConnOfFiberService;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;
import com.system.constant.RoleNo;
import com.system.sys.SystemListener;

/**
 * 光缆割接类
 * @author zhai_wanpeng
 *
 */
@Controller
@RequestMapping("/CutAndConnOfFiberController")
@SuppressWarnings("all")
public class CutAndConnOfFiberController {
	@Autowired
	private CutAndConnOfFiberService cutAndConnOfFiberService;
	
	/**
	 * 页面跳转完成初始化操作
	 * @author zhai_wanpeng
	 * @param model
	 * @return
	 */
	@RequestMapping("/testInit")
	public ModelAndView testInit(ModelMap model,HttpServletRequest request){
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		model.put("localId", localId);
		return new ModelAndView ("/linePatrol/xunxianManage/cutAndConnOfFiber/testInfo",model);
	}
	
	
	/**
	 * ajax请求完成datagrid数据源的获取操作
	 * @author zhai_wanpeng
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cutAndConnOfFiberService.query(query));
		mapVal.put("total", query.getPager().getRowcount());
		mapVal.put("localId", localId);
		return mapVal;
	}
	
	/**
	 * 条件查询 获取电缆段信息
	 * @author zhai_wanpeng
	 * @param areaId
	 * @return
	 */
	@RequestMapping("/getCable")
	@ResponseBody
	public List<Map<String , Object>> getCable(String areaId){
		List<Map<String , Object>> list=cutAndConnOfFiberService.getCable(areaId);
		 return list;
	}
	
	/**
	 * 条件查询 获取中继段信息
	 * @author zhai_wanpeng
	 * @param cableId
	 * @return
	 */
	@RequestMapping("/getRelay")
	@ResponseBody
	public List<Map<String , Object>> getRelay(String cableId){
		List<Map<String , Object>> list=cutAndConnOfFiberService.getRelay(cableId);
		return list;
	}
	
	/**
	 * 中继段测试记录详细情况页面跳转
	 * @author zhai_wanpeng
	 * @return
	 */
	@RequestMapping("/relayDetailInfoInit")
	public ModelAndView showRelayDetailInfoInit(String relayinfoId,ModelMap model,UIPage page){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("relayinfoId", relayinfoId);
		Query query =new Query();
		query.setQueryParams(map);
		query.setPager(page);
		model.addAttribute("model", cutAndConnOfFiberService.query(query));
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/showRelayDetailInfo",model);
	}
	
	
	
	/**
	 * 中继段测试导入excel页面跳转
	 * @author liujianxiong
	 * @return
	 */
	@RequestMapping("/addExcel.do")
	public ModelAndView addExcel(HttpServletRequest request,ModelMap model,
			HttpServletResponse response){
//		String RELAYINFOID = request.getParameter("relayinfoId");
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("RELAYINFOID", RELAYINFOID);
//		model.addAttribute("RELAYINFOID", RELAYINFOID);
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/import");
	}
	
	/**
	 * 中继段测试详情导入excel页面跳转
	 * @author liujianxiong
	 * @return
	 */
	@RequestMapping("/addSubExcel")
	public ModelAndView addSubExcel(HttpServletRequest request,ModelMap model,String relayinfoId,
			HttpServletResponse response){
		String RELAYINFOID = request.getParameter("relayinfoId");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("RELAYINFOID", RELAYINFOID);
		model.addAttribute("RELAYINFOID", RELAYINFOID);
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/import_detail",model);
	}
	
	
	
//	@RequestMapping("/toAddTestDetailInfo")
//	public ModelAndView toAddTestDetailInfo(String relayinfoId,ModelMap model){
//		model.put("relayinfoId", relayinfoId);
//		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/addTestDetailInfo",model);
//	}
	@RequestMapping("/addSubExcelFiber.do")
	public ModelAndView addSubExcelFiber(HttpServletRequest request,ModelMap model,
			HttpServletResponse response){
//		String RELAYINFOID = request.getParameter("relayinfoId");
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("RELAYINFOID", RELAYINFOID);
//		model.addAttribute("RELAYINFOID", RELAYINFOID);
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/importFiber");
	}
	@RequestMapping("/addSubExcelStep")
	public ModelAndView addSubExcelStep(HttpServletRequest request,ModelMap model,
			HttpServletResponse response){
//		String RELAYINFOID = request.getParameter("relayinfoId");
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("RELAYINFOID", RELAYINFOID);
//		model.addAttribute("RELAYINFOID", RELAYINFOID);
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/importStep");
	}
	@RequestMapping("downloadExample")
	@ResponseBody
	public void downloadExample(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("用户导入样例.xls").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/中继段测试记录.xlsx")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	
	@RequestMapping("/downloadDataExample")
	@ResponseBody
	public void downloadDataExample(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("中继段测试记录批量导入.xlsx").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/中继段测试记录批量导入.xlsx")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	
	@RequestMapping("downloadExampleSub")
	@ResponseBody
	public void downloadExampleSub(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("中继段测试记录详情.xlsx").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/中继段测试记录详情.xlsx")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	@RequestMapping("downloadExampleSubFiber")
	@ResponseBody
	public void downloadExampleSubFiber(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("光缆割接记录导入模板.xlsx").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/光缆割接记录导入模板.xlsx")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	@RequestMapping("downloadExampleSubStep")
	@ResponseBody
	public void downloadExampleSubStep(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(("大台阶数据记录批量导入.xlsx").getBytes("gb2312"), "iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener.getRealPath() + "/excelFiles/大台阶数据记录批量导入.xlsx")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	/**
	 * 中继段测试详细信息查询
	 * @param relayinfoId
	 * @param page
	 * @return
	 */
	@RequestMapping("/showRelayDetailInfo")
	@ResponseBody
	public Map<String, Object> showRelayDetailInfo(String relayinfoId,String i_index,UIPage page){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("relayinfoId", relayinfoId);
		map.put("i_index", i_index);
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cutAndConnOfFiberService.showRelayDetailInfo(query));
		mapVal.put("total", query.getPager().getRowcount());
		return mapVal;
	}
	
	/**
	 * 删除中继段测试记录行数据
	 * @param request
	 * @param relayinfoId
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/delTestInfo")
	@ResponseBody
	public Map<String, Object> delTestInfo(HttpServletRequest request,String relayinfoId){
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cutAndConnOfFiberService.delTestInfo(relayinfoId);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
	/**
	 * 删除中继段详细信息数据 批量删除
	 * @param request
	 * @param relayinfoId
	 * @return
	 */
	@RequestMapping("/delDetailInfoOfTest")
	@ResponseBody
	public Map<String, Object> delDetailInfoOfTest(HttpServletRequest request,String relayinfoId,String str){
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, Object> map=new HashMap<String, Object>();
		String[] param=str.split(",");
		for (String index : param) {
			map.put("relayinfoId", relayinfoId);
			map.put("allIndex", index);
			try {
				cutAndConnOfFiberService.delTestDetail(map);
				result.put("status", 1);
			} catch (Exception e) {
				result.put("status", 0);
				e.printStackTrace();
			}
		}return result;
	}
	
	
	
	/**
	 * 光缆割接记录表页面跳转初始化
	 * @param request
	 * @return
	 */
	@RequestMapping("/cuttingRecordOfFiberInit")
	public ModelAndView getCuttingRecordOfFiberInit(HttpServletRequest request,ModelMap model){
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		model.put("localId", localId);
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/cuttingRecordOfFiber",model);
	}
	
	/**
	 * 获取光缆割接记录datagrid数据源
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/cuttingRecordOfFiber.do")
	@ResponseBody
	public Map<String, Object> getCuttingRecordOfFiber(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cutAndConnOfFiberService.getCuttingRecordOfFiber(query));
		mapVal.put("total", query.getPager().getRowcount());
		mapVal.put("localId", localId);
		return mapVal;
	}
	
	/**
	 * 光缆割接记录详细信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detailRecordOfFiber")
	public ModelAndView getDetailRecordOfFiber(String id,ModelMap model,UIPage page){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cutAndConnOfFiberService.getCuttingRecordOfFiber(query));
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/detailRecordOfFiber",mapVal);
	}
	
	
	/**
	 * 大台阶页面跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/stepInit")
	public ModelAndView stepInit(ModelMap model,HttpServletRequest request){
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		model.put("localId", localId);
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/stepInit",model);
	}
	
	/**
	 * 大台阶页面datagrid数据源
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/getRecordOfStep")
	@ResponseBody
	public Map<String, Object> getRecordOfStep(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cutAndConnOfFiberService.getRecordOfSteps(query));
		mapVal.put("total", query.getPager().getRowcount());
		mapVal.put("localId", localId);
		return mapVal;
	}
	
	/**
	 * 跳转至中继段测试记录修改表
	 * @param request
	 * @param model
	 * @param relayinfoId
	 * @param page
	 * @return
	 */
	@RequestMapping("/toUpdTestInfo")
	public ModelAndView toUpdTestInfo(HttpServletRequest request,ModelMap model,String relayinfoId,UIPage page){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("relayinfoId", relayinfoId);
		Query query =new Query();
		query.setQueryParams(map);
		query.setPager(page);
		model.addAttribute("model", cutAndConnOfFiberService.query(query));
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/updTestInfo",model);
	}
	@RequestMapping(value = "import_save_tip")
	@ResponseBody
	public void import_save_tip(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file) throws Exception {
		//Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String responseStr = "";
		String info = "";
		//String RELAYINFOID=request.getParameter("relayinfoId");
		info = cutAndConnOfFiberService.importDo(request, file);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(info);

	}
	@RequestMapping(value = "import_save_tip_Fiber.do")
	@ResponseBody
	public void import_save_tip_Fiber(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file) throws Exception {

		String responseStr = "";
		String info = "";
		info = cutAndConnOfFiberService.importDo_Fiber(request, file);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(info);

	}
	// TODO
	@RequestMapping(value = "import_save_tip_Step")
	@ResponseBody
	public void import_save_tip_Step(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file) throws Exception {

		String info = "";
		info = cutAndConnOfFiberService.importDo_Step(request, file);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(info);

	}
	/**
	 * 修改中继段测试信息数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/updTestInfo")
	@ResponseBody
	public Map<String, Object> updTestInfo(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cutAndConnOfFiberService.updTestInfo(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 跳转到详细信息修改页面
	 * @param relayinfoId
	 * @param i_index
	 * @param page
	 * @return
	 */
	
	@RequestMapping("/toUpdTestDetailInfo")
	public ModelAndView toUpdTestDetailInfo(String relayinfoId,String i_index,UIPage page,ModelMap mapVal){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("relayinfoId", relayinfoId);
		map.put("i_index", i_index);
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		mapVal.put("rows", cutAndConnOfFiberService.showRelayDetailInfo(query));
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/updTestDetailInfo",mapVal);
	}
	
	/**
	 * 修改中继段测试信息数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/updTestDetailInfo")
	@ResponseBody
	public Map<String, Object> updTestDetailInfo(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cutAndConnOfFiberService.updTestDetailInfo(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 光缆敏感信息系统页面跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/sensitivelineInit")
	public ModelAndView sensitivelineInit(ModelMap model,HttpServletRequest request){
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		model.put("localId", localId);
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/sensitivelineInit",model);
	}
	
	/**
	 * 光缆敏感信息系统信息数据源
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/getListOfSensitiveline")
	@ResponseBody
	public Map<String, Object> getListOfSensitiveline(HttpServletRequest request,UIPage page){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("localId", localId);
		}
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		Map<String, Object> mapVal = new HashMap<String, Object>();
		mapVal.put("rows", cutAndConnOfFiberService.getListOfSensitiveline(query));
		mapVal.put("total", query.getPager().getRowcount());
		mapVal.put("localId", localId);
		return mapVal;
	}
	
	
	/**
	 * 测试信息新增操作
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddTestInfo")
	public ModelAndView toAddTestInfo(ModelMap model,HttpServletRequest request){
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
			model.put("localId", localId);
		}
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		return new ModelAndView ("/linePatrol/xunxianManage/cutAndConnOfFiber/addTestInfo",model);
	}
	
	/**
	 * 测试信息新增操作
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/addTestInfo")
	public void addTestInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result=new HashMap<String, Object>();
		Object localId = map.get("city_name");
		if(localId == null || "".equals(localId)){
			localId=StaffUtil.getStaffAreaId(request);
			map.put("city_name", localId);
		}
		try {
			cutAndConnOfFiberService.addTestInfo(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		response.getWriter().print(JSONObject.fromObject(result).toString());
	}
	
	/**
	 * 跳转测试详情新增页面  
	 * @param relayinfoId
	 * @return
	 */
	@RequestMapping("/toAddTestDetailInfo")
	public ModelAndView toAddTestDetailInfo(String relayinfoId,ModelMap model){
		model.put("relayinfoId", relayinfoId);
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/addTestDetailInfo",model);
	}
	
	/**
	 * 测试详情信息新增功能
	 * @param request
	 * @return
	 */
	@RequestMapping("/addTestDetailInfo")
	@ResponseBody
	public Map<String, Object> addTestDetailInfo(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cutAndConnOfFiberService.addTestDetailInfo(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 光缆割接记录删除操作
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/delRecordOfFiber")
	@ResponseBody
	public Map<String, Object> delRecordOfFiber(HttpServletRequest request,String id){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cutAndConnOfFiberService.delRecordOfFiber(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 跳转新增光缆割接记录表页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddRecordOfFiber")
	public ModelAndView toAddRecordOfFiber(ModelMap model,HttpServletRequest request){
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		model.put("localId", localId);
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/addRecordOfFiber",model);
	}
	
	/**
	 * 新增光缆割接记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/addRecordOfFiber")
	@ResponseBody
	public Map<String, Object> addRecordOfFiber(HttpServletRequest request){
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		if("".equals(map.get("city_name"))){
			map.put("localId", StaffUtil.getStaffAreaId(request));
		}
		try {
			cutAndConnOfFiberService.addRecordOfFiber(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	} 
	
	/**
	 * 跳转至修改光缆割接记录页面
	 * @param id
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/toUpdRecordOfFiber")
	public ModelAndView toUpdRecordOfFiber(String id,ModelMap model,UIPage page,HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		model.addAttribute("localId", localId);
		map.put("id", id);
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		model.addAttribute("rows", cutAndConnOfFiberService.getCuttingRecordOfFiber(query));
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/updRecordOfFiber",model);
	}
	
	/**
	 * 修改光缆割接记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/updRecordOfFiber")
	@ResponseBody
	public Map<String, Object> updRecordOfFiber(HttpServletRequest request){
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		try {
			cutAndConnOfFiberService.updRecordOfFiber(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	} 
	
	/**
	 * 大台阶数据记录删除操作
	 * @param request
	 * @param str
	 * @return
	 */
	@RequestMapping("/delStepData")
	@ResponseBody
	public Map<String, Object> delStepData(HttpServletRequest request,String str){
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, Object> map=new HashMap<String, Object>();
		String[] param=str.split(",");
		for (String stepId : param) {
			map.put("stepId", stepId);
			try {
				cutAndConnOfFiberService.delStepData(map);
				result.put("status", 1);
			} catch (Exception e) {
				result.put("status", 0);
				e.printStackTrace();
			}
		}return result;
	}
	
	/**
	 * 大台阶数据excel导出
	 * @param request
	 * @param response
	 * @param pager
	 */
	@RequestMapping(value = "/exportExcel.do")
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, UIPage page) {
		Query query = new Query();
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		para.put("localId",localId);
		query.setPager(page);
		query.setQueryParams(para);
		cutAndConnOfFiberService.exportExcel(request, response,query);
	}
	
	/**
	 * 跳转新增大台阶记录表页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddStepData")
	public ModelAndView toAddStepData(ModelMap model,HttpServletRequest request){
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		model.put("localId", localId);
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/addStepData",model);
	}
	
	/**
	 * 新增大台阶记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/addStepData")
	@ResponseBody
	public Map<String, Object> addStepData(HttpServletRequest request){
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		if("".equals(map.get("city_name"))){
			map.put("localId", StaffUtil.getStaffAreaId(request));
		}
		try {
			cutAndConnOfFiberService.addStepData(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	} 
	
	
	/**
	 * 跳转至修改大台阶数据页面
	 * @param id
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/toEditStepData")
	public ModelAndView toEditStepData(String stepId,ModelMap model,UIPage page,HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		model.addAttribute("localId", localId);
		map.put("stepId", stepId);
		Query query =new Query();
		query.setPager(page);
		query.setQueryParams(map);
		model.addAttribute("rows", cutAndConnOfFiberService.getRecordOfSteps(query));
		model.addAttribute("cityModel", cutAndConnOfFiberService.getCityInfo(null));
		return new ModelAndView("/linePatrol/xunxianManage/cutAndConnOfFiber/updStepData",model);
	}
	
	/**
	 * 大台阶数据修改操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/updStepData")
	@ResponseBody
	public Map<String, Object> updStepData(HttpServletRequest request){
		Map<String, Object> map = MapUtil.parameterMapToCommonMap(request);
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			cutAndConnOfFiberService.updStepData(map);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 中继段测试记录表excel导出
	 * @param request
	 * @param response
	 * @param pager
	 */
	@RequestMapping(value = "/exportTestInfoExcel.do")
	public void exportTestInfoExcel(HttpServletRequest request,UIPage page,
			HttpServletResponse response) {
		Query query = new Query();
		query.setPager(page);
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		para.put("localId",localId);
		cutAndConnOfFiberService.exportTextInfoExcel(request, response,query,para);
	}
	@RequestMapping("import_save.do")
	@ResponseBody
	public void import_save(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file)
			throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
				cutAndConnOfFiberService.importCutAndConnOfFiber(request, file).toString());
	}
	
	/**
	 * 光缆割接记录excel导出
	 * @param request
	 * @param response
	 * @param pager
	 */
	@RequestMapping(value = "/exportFiberRecordExcel.do")
	public void exportFiberRecordExcel(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String localId=null;
		if(null ==request.getSession().getAttribute(RoleNo.AXX_ALL_ADMIN)){
			localId=StaffUtil.getStaffAreaId(request);
		}
		para.put("localId",localId);
		cutAndConnOfFiberService.exportFiberRecordExcel(request, response,para);
	}
	
	
}
