/**
 * FileName: GeneralServiceImpl.java
 * @Description: TODO(用一句话描述该文件做什么) 
 * All rights Reserved, Designed By ZBITI 
 * Copyright: Copyright(C) 2014-2015
 * Company ZBITI LTD.

 * @author: SongYuanchen
 * @version: V1.0  
 * Createdate: 2014-1-17
 *

 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-1-17      wu.zh         1.0            1.0
 * Why & What is modified: <修改原因描述>
 */
package com.system.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.page.Query;
import util.page.UIPage;
import util.password.MD5;

import com.system.dao.GeneralDao;
import com.system.dao.SoftwareVersionDao;
import com.system.model.SoftwareVersion;
import com.system.model.SoftwareVersionExample;
import com.system.model.SoftwareVersionExample.Criteria;
import com.system.service.BaseMethodService;
import com.system.service.GeneralService;
import com.system.service.SoftwareVersionService;
import com.system.tool.GlobalValue;
import com.system.tool.ImageTool;

/**
 * @ClassName: GeneralServiceImpl
 * @Description:
 * @author: SongYuanchen
 * @date: 2014-1-17
 * 
 */
@SuppressWarnings("all")
@Transactional
@Service
public class SoftwareVersionServiceImpl implements SoftwareVersionService {
	@Resource
	private SoftwareVersionDao softwareVersionDao;
	@Resource
	private BaseMethodService baseMethodService;

	/**
	 * <p>
	 * Title: save
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @see com.zbiti.system.service.interfaces.StaffService#save(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void insert(HttpServletRequest request) throws Exception {
		BigDecimal softwareId = BigDecimal.valueOf(Long.valueOf(request
				.getParameter("software_id_add")));
		String version_no = request.getParameter("version_no_add");
		String version_info = request.getParameter("version_info_add");
		BigDecimal drivate_type = BigDecimal.valueOf(Double.valueOf(request
				.getParameter("drivate_type_add")));
		// String file = "";
		BigDecimal if_force_update = BigDecimal.valueOf(Long.valueOf(request
				.getParameter("if_force_update_add")));
		BigDecimal state = BigDecimal.valueOf(Long.valueOf(request
				.getParameter("state_add")));
		
		String comments = request.getParameter("comments");
		
		SoftwareVersion record = new SoftwareVersion();
		String softwareVersionId = softwareVersionDao.getSoftwareVersionId();
		record.setSoftwareVersionId(BigDecimal.valueOf(Long
				.valueOf(softwareVersionId)));
		record.setSoftwareId(softwareId);
		record.setVersionNum(version_no);
		record.setVersionInfo(version_info);
		record.setDrivateType(drivate_type);
		record.setComments(comments);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile cfile = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		String realFileName = cfile.getOriginalFilename().substring(0,
				cfile.getOriginalFilename().lastIndexOf("."));
		String filePath = this.saveFile(realFileName, cfile.getBytes());

		// 对文件路径要做如下处理
		String fsi = GlobalValue.FILE_SERVER_INTERNET;
		// http://132.232.5.57:8090/files/zhxjAPK/2014-06-24/zhxj_1.1_20140624.apk
		int index = filePath.indexOf("files");
		if(index!=-1){
			filePath = fsi + filePath.substring(index, filePath.length());

		}

		record.setFilePath(filePath);
		record.setFileName(realFileName);
		record.setIfForceUpdate(if_force_update);
		record.setState(state);
		HttpSession session = request.getSession();
		record.setCreateStaff(BigDecimal.valueOf(Long.valueOf((String) session
				.getAttribute("staffId"))));
		record.setCreateDate(new Date());
		record.setModifyStaff(BigDecimal.valueOf(Long.valueOf((String) session
				.getAttribute("staffId"))));
		record.setModifyDate(new Date());
		softwareVersionDao.insertSelective(record);
	}

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @see com.zbiti.system.service.interfaces.StaffService#delete(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void delete(HttpServletRequest request) {

		String selected = request.getParameter("selected");
		HttpSession session = request.getSession();
		Map map = new HashMap();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		String[] ids = selected.split(",");
		for (int i = 0; i < ids.length; i++) {
			BigDecimal softwareVersionId = BigDecimal.valueOf(Long
					.valueOf(ids[i]));
			SoftwareVersion record = new SoftwareVersion();
			record.setSoftwareVersionId(softwareVersionId);
			record.setState(BigDecimal.valueOf(0));
			record.setModifyStaff(BigDecimal.valueOf(Long
					.valueOf((String) session.getAttribute("staffId"))));
			softwareVersionDao.updateByPrimaryKeySelective(record);
		}
	}

	public String saveFile(String fileName, byte[] eByte) {
		Map param = new HashMap();
		// byte[] eByte = ImageTool.getByteFromFile(file);
		HashMap pathMap = baseMethodService.uploadFile(fileName, eByte,"apk");
		String filePath = (String) pathMap.get("path");
		return filePath;
	}

	@Override
	public void downLoadAPK(HttpServletRequest request,
			HttpServletResponse response, String softId) {
		String path = softwareVersionDao.getAppDownLoadPath(softId);
		path=path.replace("61.160.128.47", "132.228.237.107");
		
		InputStream in = null;
		OutputStream out = null;
		HttpURLConnection conn = null;
		String fileName = null;
		try {
			// 初始化连接
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);

			// 获取文件名
			String disposition = conn.getHeaderField("Content-Disposition");
			if (disposition != null && !"".equals(disposition)) {
				// 从头中获取文件名
				fileName = disposition.split(";")[1].split("=")[1].replaceAll(
						"\"", "");
			} else {
				// 从地址中获取文件名
				fileName = path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("."));
			}

			if (fileName != null && !"".equals(fileName)) {
				// 文件名解码
				fileName = URLDecoder.decode(fileName, "utf-8");
			} else {
				// 如果无法获取文件名，则随机生成一个
				fileName = "file_" + (int) (Math.random() * 10);
			}
			String ext=path.substring(path.lastIndexOf(".") + 1).toLowerCase();
			response.setContentType("application/x-msdownload");
		    response.setCharacterEncoding("UTF-8");

		    // 设置response的Header
		    // response.setHeader("Content-Disposition","attachment;filename="+new
		    // String((responseFileName+"."+ext).getBytes("UTF-8"),"iso-8859-1"));
		    // response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(responseFileName+"."+ext,
		    // "UTF-8"));

		    if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0
			    || request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT") > 0) {
			response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(fileName + "."+ext, "UTF-8"));
		    } else {
			response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + "."+ext).getBytes("UTF-8"), "iso-8859-1"));
		    }

		    // 写明要下载的文件的大小
		    response.addHeader("Content-Length", "" + conn.getContentLength());
			
			
			// 读取数据
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				byte[] buffer = new byte[2048];
				in = conn.getInputStream();
				out = response.getOutputStream();
				int count = 0;
				int finished = 0;
				int size = conn.getContentLength();
				while ((count = in.read(buffer)) != -1) {
					if (count != 0) {
						out.write(buffer, 0, count);
//						finished += count;
//						System.out
//								.printf("########################################---->%1$.2f%%\n",
//										(double) finished / size * 100);
					} else {
						break;
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null){
					conn.disconnect();
				}
				if(in != null){
					in.close();
				}
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	
}
