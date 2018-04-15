package com.cableInspection.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.cableInspection.service.PointManageService;
import com.system.sys.SystemListener;

@Controller
@RequestMapping("Lxxj/point/manage")
public class PointManageController extends BaseAction {

	@Resource
	private PointManageService pointManageService;

	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = pointManageService.index(request);
		return new ModelAndView("cableinspection/point/manage/index", rs);
	}

	@RequestMapping("query")
	@ResponseBody
	public void query(HttpServletRequest request, HttpServletResponse response,
			UIPage pager) throws IOException {
		Map<String, Object> map = pointManageService.query(request, pager);
		write(response, map);
	}

	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rs = pointManageService.add(request);
		return new ModelAndView("cableinspection/point/manage/add", rs);
	}
	
	@RequestMapping("toUpdatePage")
	public ModelAndView toUpdatePage(HttpServletRequest request,
			HttpServletResponse response){
		Map rs = pointManageService.queryPointForcoordinate(request);
		return new ModelAndView("cableinspection/point/manage/update", rs);
	}
	
	@RequestMapping("getPointById")
	@ResponseBody
	public void getPointById(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Map rs = pointManageService.queryPointById(request);
		response.getWriter().write(rs.toString());
	}
	
	@RequestMapping("updateCoordinate")
	@ResponseBody
	public void updateCoordinate(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(JSONObject.fromObject(pointManageService.updateCoordinate(request)).toString());
	}

	/**
	 * 
	 * @Function: com.cableInspection.action.PointManageController.updatePoint
	 * @Description:修改关键点、隐患点、外力点的点名称
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * 
	 * @date:2015-6-17 下午3:50:13
	 * 
	 * @Modification History:
	 * @date:2015-6-17 @author:Administrator create
	 */
	@RequestMapping("updatePointName")
	@ResponseBody
	public void updatePointName(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(pointManageService.updatePointName(request).toString());
	}

	@RequestMapping("save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,
			HttpServletResponse response) {
		return pointManageService.save(request);
	}

	@RequestMapping("delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(
				pointManageService.delete(request).toString());
	}

	@RequestMapping("import")
	public ModelAndView importPoint(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("cableinspection/point/manage/import");
	}

	@RequestMapping("import_save")
	@ResponseBody
	public void importDo(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file)
			throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
				pointManageService.importKeyPoint(request, file).toString());
	}

	@RequestMapping("downloadExample")
	@ResponseBody
	public void downloadExample(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("关键点导入表.xls").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/关键点导入表.xls")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping("queryExistsPoint")
	public void queryExistsPoint(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map<String, String>> points = pointManageService
				.queryExistsPoint(request);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(JSONArray.fromObject(points).toString());
	}

	@RequestMapping("export")
	public String export(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) {
		List<Map<String, Object>> dataList = pointManageService
				.queryPoint(request);
		String[] cols = new String[] { "POINT_NO", "POINT_NAME","POINT_LEVEL",
				"POINT_TYPE_NAME", "EQUIPMENT_TYPE_NAME", "LONGITUDE",
				"LATITUDE", "ADDRESS", "AREA", "SON_AREA", "AA","DEPT_NAME","AREA_TYPE" };
		map.addAttribute("cols", cols);
		String[] colsName = new String[] { "点编码", "点名称","维护等级", "点类型", "设备类型（若为设备点）",
				"经度", "纬度", "地址", "地市", "区县", "使用情况","所属网格","设备位置" };
		map.addAttribute("name", "关键点");
		map.addAttribute("colsName", colsName);
		map.addAttribute("dataList", dataList);
		return "dataListExcel";
	}

	/**
	 * 关键点信息Index页面
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return 关键点信息Index页面
	 */
	@RequestMapping("indexKeyPoints")
	public ModelAndView indexKeyPoints(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView("/cableinspection/point/manage/report/index");
	}

	/**
	 * 查询关键点信息
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param pager
	 *            pager
	 * @throws IOException
	 *             IO异常
	 */
	@RequestMapping("queryKeyPoints")
	public void queryKeyPoints(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException {
		Map<String, Object> map = pointManageService.queryKeyPoints(request,
				pager);
		write(response, map);
	}

	/**
	 * 关键点统计报表
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param map
	 *            关键点信息Map
	 * @return bean
	 */
	@RequestMapping("exportKeyPoints")
	public String exportKeyPoints(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) {
		List<Map<String, Object>> dataList = pointManageService
				.queryKeyPointsExl(request);
		String[] cols = new String[] { "POINT_NO", "POINT_NAME", "LONGITUDE",
				"LATITUDE", "POINT_CREATE_TIME", "POINT_CREATOR", "ADDRESS",
				"POINT_SON_AREA", "LINE_ID", "LINE_NO", "LINE_NAME",
				"LINE_CREATE_TIME", "LINE_CREATOR", "LINE_SON_AREA", "COUNT" };
		String[] colsName = new String[] { "关键点编码", "关键点名称", "经度", "纬度",
				"点创建时间", "点创建者", "点地址", "点区所属域", "缆线ID", "缆线编码", "缆线名称",
				"缆线创建时间", "缆线创建者", "缆线所属区域", "同一缆线出现次数" };
		map.addAttribute("name", "关键点统计");
		map.addAttribute("cols", cols);
		map.addAttribute("colsName", colsName);
		map.addAttribute("dataList", dataList);
		return "dataListExcel";
	}
	/**
	 * 
	 * @Function: com.cableInspection.action.PointManageController.getMntList
	 * @Description:获得维护等级
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * @date:2015-11-3 下午2:45:16
	 *
	 * @Modification History:
	 * @date:2015-11-3     @author:Administrator     create
	 */
	@RequestMapping(value = "/getMntList.do")
    @ResponseBody
    public Map getMntList(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	List<Map<String, String>> mntList = pointManageService.getMntList();
	map.put("mntList", mntList);
	return map;
    }
	/**
	 * 
	 * @Function: com.cableInspection.action.PointManageController.v
	 * @Description:获得维护等级
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * @date:2015-11-3 下午2:45:16
	 *
	 * @Modification History:
	 * @date:2015-11-3     @author:Administrator     create
	 */
	@RequestMapping(value = "/getEqpTypeList.do")
    @ResponseBody
    public Map getEqpTypeList(HttpServletRequest request, HttpServletResponse response) {
	Map map = new HashMap();
	List<Map<String, String>> eqpTypeList = pointManageService.getEqpTypeList();
	map.put("eqpTypeList", eqpTypeList);
	return map;
    }
}
