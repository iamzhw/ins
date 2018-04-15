package com.cableInspection.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.page.BaseAction;
import util.page.UIPage;

import com.cableInspection.service.PhotoRemarkService;
import com.system.constant.RoleNo;
import com.system.sys.SystemListener;

@RequestMapping(value = "/Remark")
@SuppressWarnings("all")
@Controller
public class PhotoRemarkController extends BaseAction {
	@Resource
	PhotoRemarkService photoRemarkService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map =photoRemarkService.remarkIndex(request);
		return new ModelAndView("cableinspection/photoRemark/index", map);
	}
	
	@RequestMapping("query")
	public void query(HttpServletRequest request,
			HttpServletResponse response, UIPage pager) throws IOException{
		Map<String, Object> map =photoRemarkService.query(request, pager);
		write(response, map);
	}
	
	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cableinspection/photoRemark/add", null);
	}
	
	@RequestMapping("save")
	public void save(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		Boolean isSuccess = true;
		try {
			photoRemarkService.addRemark(request);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		map.put("status", isSuccess);
		response.getWriter().write(JSONObject.fromObject(map).toString());
	}
	
	@RequestMapping("querydetail")
	public void querydetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Map<String, Object> map =photoRemarkService.queryRemark(request);
		write(response, map);
	}
	
	@RequestMapping("faq")
	public ModelAndView faq(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("cableinspection/faq/faq");
	}
	
	@RequestMapping("download")
	@ResponseBody
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(("缆线巡检培训操作手册V8.2_20160511.ppt").getBytes("gb2312"),
							"iso8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			out.write(FileUtils.readFileToByteArray(new File(SystemListener
					.getRealPath()
					+ "/excelFiles/缆线巡检培训操作手册V8.2_20160511.ppt")));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
