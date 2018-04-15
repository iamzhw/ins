package icom.cableCheck.serviceimpl;

import icom.cableCheck.dao.CheckPhotoDao;
import icom.cableCheck.service.CheckPhotoService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.system.service.BaseMethodService;
import com.system.tool.GlobalValue;
import com.system.tool.ImageTool;
import com.util.StringUtil;

/**
 * 上传照片
 * 
 */
@Service
@SuppressWarnings("all")
public class CheckPhotoServiceImpl implements CheckPhotoService{

	Logger logger = Logger.getLogger(CheckPhotoServiceImpl.class);
	
	@Resource
	private CheckPhotoDao checkPhotoDao;
	
	@Resource
	private BaseMethodService baseMethodService;
	
	
	public String uploadPhoto(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			
			request.setCharacterEncoding("utf-8");
			/**
			 * 传入的参数
			 */
			String terminalType = request.getHeader("terminalType");//终端 terminalType
			String sn = request.getHeader("sn");//终端sn
			String staffId = request.getHeader("staffId");// 巡检人ID
			String taskId = request.getHeader("taskId");
			String rwmxId = request.getHeader("rwmxId");
			String eqpId = request.getHeader("eqpId");
			String portId = request.getHeader("portId");
			String operType = request.getHeader("operType");//0:周期任务巡检拍照上传；1：隐患上报拍照上传，2整改回单 ，3无设备编码上报
			
//			if (!OnlineUserListener.isLogin(staffId, sn)) {
//				result.put("result", "002");
//				result.put("photoId", "");
//				return result.toString();
//			}
			
			Map<String, Object> photoMap = new HashMap<String, Object>();
			
			photoMap.put("terminalType", terminalType);
			photoMap.put("sn", sn);
			photoMap.put("staff_id", staffId);
			photoMap.put("taskId", taskId);
			photoMap.put("rwmxId", rwmxId);
			photoMap.put("eqpId", eqpId);
			photoMap.put("portId", portId);
			photoMap.put("operType", operType);
			
			int photo = checkPhotoDao.getPicId();// 获取photo主键
			String photoId=String.valueOf(photo);
			photoMap.put("PHOTO_ID",photoId);
			
//			photoMap.put("site_id", site_id);
//			photoMap.put("upload_time", DateUtil.getDateAndTime());
//			photoMap.put("photo_type", photo_type);

			/**
			 * 文件流
			 */
			byte[] photoByte = ImageTool.getByteFromStream(request);
			
			Map resultMap = baseMethodService.uploadFile(photoId, photoByte,"photo");
			
			if ("true".equals(StringUtil.objectToString(resultMap.get("isSuccess")))) {
				/* 
				 * 将图片保存的地址返回给调用方
				 */
				String photoPath = StringUtil.objectToString(resultMap.get("photoPath")); // 原尺寸照片地址
				String microPhotoPath = StringUtil.objectToString(resultMap.get("microPhotoPath")); // 缩微图照片地址
				/*
				 * 对文件路径要做如下处理
				 */
				String fsi = GlobalValue.FILE_SERVER_INTERNET;
				int index = photoPath.indexOf("files");
				if (index != -1) {
					photoPath = fsi + photoPath.substring(index, photoPath.length());
				}
				index = microPhotoPath.indexOf("files");
				if (index != -1) {
					microPhotoPath = fsi + microPhotoPath.substring(index, microPhotoPath.length());
				}
				photoMap.put("PHOTO_PATH", photoPath == null ? "" : photoPath);
				photoMap.put("MICRO_PHOTO_PATH", microPhotoPath == null ? "" : microPhotoPath);
			}
			if (checkPhotoDao.insertPic(photoMap) == 1) {
				result.put("result", "000");
				result.put("photoId", photoId);
				return result.toString();
			} else {
				result.put("result", "001");
				result.put("photoId", "");
				return result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "001");
			result.put("photoId", "");
			return result.toString();
		}
		
		
	}


}
