package com.system.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inspecthelper.dao.InspectHelperDao;
import com.system.constant.UploadFileType;
import com.system.tool.CharConvertTool;
import com.system.tool.GlobalValue;



/**
 * 获取数据方法的基础服务类
 * 
 * @author laolu
 * 
 */
@SuppressWarnings("all")
@Transactional
@Service
public class BaseMethodService {
	
	@Resource
	private InspectHelperDao inspectHelperDao;
	/**
	 * 向文件服务器上传文件
	 * 
	 * @param name
	 * @param photo
	 * @return
	 */
	public HashMap uploadFile(String name,
			byte[] fileByte,String fileType) {
		HashMap map = new HashMap();
		Object[] resultXmlObj1 = null;
		try {
			String fileHexDate=null;
			if(fileByte.length>0){
				fileHexDate= CharConvertTool
				.convertByteToHexString(fileByte); // 将字节转成十六进制字符串
			}else{
				return null;
			}

			/* 获得当前日期 */
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = sdf.format(new Date()); // 当前日期

			/* 拼装请求参数 */
			StringBuffer xmlStr = new StringBuffer();
			xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xmlStr.append("<root>");
			xmlStr.append("<type>").append(fileType).append("</type>"); // 上传类型
			xmlStr.append("<project>zhxjAPK</project>"); // 存放照片的目录
			xmlStr.append("<name>").append(name).append("</name>"); // 文件名称
			xmlStr.append("<date>").append(curDate).append("</date>"); // 文件日期
			xmlStr.append("<data>").append(fileHexDate).append("</data>"); // 文件数据
			xmlStr.append("</root>");

			/* 调用文件服务器的服务 */
			try {
				Client client = new Client(new URL(GlobalValue.FILE_SERVER));
				resultXmlObj1 = client.invoke("saveFile", new Object[] { xmlStr
						.toString() });
			} catch (Exception e) {
				e.printStackTrace();
			}

			/* 解析返回的结果,先解析文件服务器1返回的结果再解析文件服务器2的结果,其中一台上传成功即认为文件上传成功 */
			// analyzeResultXml(resultXmlObj1, map, name, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("photo".equals(fileType)){
			this.analyzeResultXml(resultXmlObj1, map, name);
		} else {
			this.analyzeResultXml1(resultXmlObj1, map, name);
		}
			
		/* 若两次上传都失败,则返回null */
		if (map.get("isSuccess") == null) {
			return null;
		}
		return map;
	}
	
	/**
	 * 解析接口返回的结果,如果调用成功,将图片访问地址塞入到Map
	 * 
	 * @param resultXmlObj
	 * @param map
	 * @param name
	 * @param userId
	 */
	private void analyzeResultXml(Object[] resultXmlObj, HashMap map,
			String name) {
		try {
			if (resultXmlObj != null) {
				Document document = DocumentHelper
						.parseText((String) resultXmlObj[0]);
				Element root = document.getRootElement();
				String result = root.elementText("result"); // 保存是否成功
				if (result.equalsIgnoreCase("success")) {
					/* 将图片保存的地址返回给调用方 */
					String photoPath = root.elementText("photoPath"); // 原尺寸照片地址
					String microPhotoPath = root.elementText("microPhotoPath"); // 缩微图照片地址
					map.put("isSuccess", "true"); // 标志位:上传成功
					map.put("photoPath", photoPath);
					map.put("microPhotoPath", microPhotoPath);
				} else {
					String msg = root.elementText("msg");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void analyzeResultXml1(Object[] resultXmlObj, HashMap map,
			String name) {
		try {
			if (resultXmlObj != null) {
				Document document = DocumentHelper
						.parseText((String) resultXmlObj[0]);
				Element root = document.getRootElement();
				String result = root.elementText("result"); // 保存是否成功
				if (result.equalsIgnoreCase("success")) {
					/* 将图片保存的地址返回给调用方 */
					String path = root.elementText("path"); // 原尺寸照片地址
					String microPhotoPath = root.elementText("microPhotoPath"); // 缩微图照片地址
					map.put("isSuccess", "true"); // 标志位:上传成功
					map.put("path", path);
				} else {
					String msg = root.elementText("msg");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 返回用户访问文件服务器的ip（判断是内网地址还是外网地址）
	 * 
	 * @param filePath
	 * @param ips
	 * @return
	 */
	public String getValidFileServiceAccessIp(HttpServletRequest request) {
		String photoLanAccessIp = "132.228.237.107"; // 获得默认访问的图片服务器内网IP
		String photoInternetAccessIp = "61.160.128.47"; // 获得默认访问的图片服务器外网IP
		String requestUrl = request.getRequestURL().toString(); // 用户请求地址
		/* 如果用户请求地址中包含外网地址，则返回外网地址，否则返回内网地址 */
		if (requestUrl.indexOf(photoInternetAccessIp) >= 0) {
			return photoInternetAccessIp + ":8080";
		} else {
			return photoLanAccessIp + ":8080";
		}
	}

	/**
	 * 插入监控资源日志
	 * 
	 * @param sn
	 *            ：sn号码，hadle_name：操作功能内容，state_id：是否成功(0:失败，1：成功)，content：备注
	 * @return
	 * 
	 * */

	public boolean saveResLog(String sn, String hadle_name, String state_id,
			String content, String resNo, String resName, String resArea,
			String resIn) {
		try {
			
			HashMap param = new HashMap();
			param.put("sn", sn);
			// 备注内容不超过500个字符
			if (content.length() > 500) {
				content = content.substring(0, 500);
			}
			param.put("content", content);
			param.put("state_id", state_id);
			param.put("hadle_name", hadle_name);
			param.put("res_no", resNo);
			param.put("res_name", resName);
			param.put("res_area", resArea);
			param.put("res_in", resIn);
			inspectHelperDao.insertResLog(param);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 插入监控日志
	 * 
	 * @param sn
	 *            ：sn号码，hadle_name：操作功能内容，state_id：是否成功(0:失败，1：成功)，content：备注
	 * @return
	 * 
	 * */

	public boolean saveRegisterLog(String sn, String hadle_name,
			String state_id, String content, String staff_no) {
		HashMap param = new HashMap();
		param.put("sn", sn);
		// 备注内容不超过500个字符
		if (content.length() > 500) {
			content = content.substring(0, 500);
		}
		param.put("content", content);
		param.put("state_id", state_id);
		param.put("hadle_name", hadle_name);
		param.put("staff_no", staff_no);
		try {
			inspectHelperDao.insertRegisterLog(param);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
