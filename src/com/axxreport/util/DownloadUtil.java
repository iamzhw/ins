package com.axxreport.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtil {

    public static void downloadExcel(HttpServletRequest request, HttpServletResponse response,
	    String fileName, String gjdIdCode) {
	// TODO Auto-generated method stub
	InputStream inStream = null;
	try {

	    // 读到流中aa
	    String rootPath = request.getSession().getServletContext().getRealPath("/");

	    String fileName1 = request.getParameter(gjdIdCode) + ".xls";
	    String fileName2 = fileName + fileName1;
	    fileName2 = new String(fileName2.getBytes(), "ISO-8859-1");

	    String filePath = rootPath + "ExcelFile" + "/" + fileName1;
	    inStream = new FileInputStream(filePath);// 文件的存放路径
	    // 设置输出的格式
	    response.reset();
	    response.setContentType("application/vnd.ms-excel");
	    response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName2 + "\"");
	    // 循环取出流中的数据
	    byte[] b = new byte[1024];
	    int len;
	    while ((len = inStream.read(b)) > 0) {
		response.getOutputStream().write(b, 0, len);
	    }
	    response.getOutputStream().flush();
	    inStream.close();

	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (null != inStream) {
		try {
		    inStream.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    public static void downloadExcel2(HttpServletRequest request, HttpServletResponse response,
	    String responseFileName, String realFileName) {
	// TODO Auto-generated method stub
	InputStream inStream = null;
	try {

	    // 读到流中aa
	    String rootPath = request.getSession().getServletContext().getRealPath("/");

	    String filePath = rootPath + "ExcelFile" + "/" + realFileName;
	    inStream = new FileInputStream(filePath);// 文件的存放路径
	    // 设置输出的格式
	    response.reset();
	    response.setContentType("application/vnd.ms-excel");
	    response.addHeader("Content-Disposition", "attachment; filename=\""
		    + new String(responseFileName.getBytes(), "ISO8859-1") + "\"");
	    // 循环取出流中的数据
	    byte[] b = new byte[1024];
	    int len;
	    while ((len = inStream.read(b)) > 0) {
		response.getOutputStream().write(b, 0, len);
	    }
	    response.getOutputStream().flush();
	    inStream.close();

	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (null != inStream) {
		try {
		    inStream.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    public static String getRootPath(HttpServletRequest request) {
	return request.getSession().getServletContext().getRealPath("/");
    }

    public static void download(String filePath, String fileName, HttpServletRequest request,
	    HttpServletResponse response) {
	InputStream inputStream = null;
	OutputStream outputStream = null;
	try {
	    // path是指欲下载的文件的路径。
	    File file = new File(filePath);
	    // 取得文件名。
	    String filename = file.getName();
	    // 取得文件的后缀名。
	    String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

	    // 以流的形式下载文件。
	    inputStream = new BufferedInputStream(new FileInputStream(filePath));

	    // 清空response
	    response.reset();
	    // 设置response的编码方式
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
			"attachment;filename=" + URLEncoder.encode(fileName + "." + ext, "UTF-8"));
	    } else {
		response.setHeader("Content-Disposition", "attachment;filename="
			+ new String((fileName + "." + ext).getBytes("UTF-8"), "iso-8859-1"));
	    }

	    // 写明要下载的文件的大小
	    response.addHeader("Content-Length", "" + file.length());

	    outputStream = response.getOutputStream();

	    byte[] buffer = new byte[8192];
	    int i = -1;
	    while ((i = inputStream.read(buffer)) != -1) {
		outputStream.write(buffer, 0, i);
	    }

	    outputStream.flush();

	} catch (IOException ex) {
	    ex.printStackTrace();
	} finally {
	    if (null != inputStream) {
		try {
		    inputStream.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    if (null != outputStream) {
		try {
		    outputStream.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }

}
