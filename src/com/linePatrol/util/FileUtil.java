package com.linePatrol.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 时间工具包
 * 
 * @author wangyan
 * 
 */
public class FileUtil {

    /**
     * 
     * @param request
     *            页面上传的File
     * @param inputName
     *            元素名称
     * @param realPath
     *            保存文件地址 (默认 文件保存至项目所在目录下)
     * @param imageName
     *            文件名称 (默认时间戳作为名称)
     */
    public static String imageFile(HttpServletRequest request, String inputName, String realPath,
	    String fileName) {

	String result = "";
	InputStream input = null;
	try {
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    MultipartFile file = multipartRequest.getFile(inputName);

	    String path = request.getSession().getServletContext().getRealPath("/");
	    if (!StringUtil.isNotNull(realPath)) {
		realPath = path + "\\uploadfile\\" + DateUtil.getDate("yyyyMMdd");
	    }
	    // String id = UUID.randomUUID().toString();

	    if (StringUtil.isNotNull(fileName)) {
		fileName = fileName.replaceAll(".", "");
	    } else {
		fileName = file.getOriginalFilename();

	    }
	    int begin = file.getOriginalFilename().lastIndexOf(".");
	    String fileType = file.getOriginalFilename().substring(begin,
		    file.getOriginalFilename().length());
	    String realName = DateUtil.getTime() + "" + fileType;

	    input = file.getInputStream();

	    File files = new File(realPath, realName);
	    if (!files.exists()) {
		files.mkdirs();
	    }
	    file.transferTo(files);
	    result = realPath + "\\" + realName;
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
		try {
			if(input != null){
				input.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return result;
	}
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     * 
     * @param sPath
     *            要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolderandFile(String sPath) {
	boolean flag = false;
	File file = new File(sPath);
	// 判断目录或文件是否存在
	if (!file.exists()) { // 不存在返回 false
	    return flag;
	} else {
	    // 判断是否为文件
	    if (file.isFile()) { // 为文件时调用删除文件方法
		return deleteFile(sPath);
	    } else { // 为目录时调用删除目录方法
		return deleteDirectory(sPath);
	    }
	}
    }

    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
	boolean flag = false;
	File file = new File(sPath);
	// 路径为文件且不为空则进行删除
	if (file.isFile() && file.exists()) {
	    file.delete();
	    flag = true;
	}
	return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * 
     * @param sPath
     *            被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
	// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
	if (!sPath.endsWith(File.separator)) {
	    sPath = sPath + File.separator;
	}
	File dirFile = new File(sPath);
	// 如果dir对应的文件不存在，或者不是一个目录，则退出
	if (!dirFile.exists() || !dirFile.isDirectory()) {
	    return false;
	}
	boolean flag = true;
	// 删除文件夹下的所有文件(包括子目录)
	File[] files = dirFile.listFiles();
	for (int i = 0; i < files.length; i++) {
	    // 删除子文件
	    if (files[i].isFile()) {
		flag = deleteFile(files[i].getAbsolutePath());
		if (!flag)
		    break;
	    } // 删除子目录
	    else {
		flag = deleteDirectory(files[i].getAbsolutePath());
		if (!flag)
		    break;
	    }
	}
	if (!flag)
	    return false;
	// 删除当前目录
	if (dirFile.delete()) {
	    return true;
	} else {
	    return false;
	}
    }

}
