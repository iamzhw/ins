package icom.system.serviceimpl;

import icom.system.dao.TaskInterfaceDao;
import icom.system.service.PicInterfaceService;
import icom.system.service.TaskInterfaceService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.system.tool.GlobalValue;
import com.system.tool.ImageTool;
import com.util.StringUtil;

/**
 * 提供给客户端查询任务接口
 * 
 * @author huliubing
 * @since 2014-07-23
 *
 */

@Service
@SuppressWarnings("all")
public class TaskInterfaceServiceImpl implements TaskInterfaceService{

	@Resource
	private TaskInterfaceDao taskInterfaceDao;
	
	@Resource
	private PicInterfaceService picInterfaceService;
	
	/**
	 * 获取任务列表
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public String getAllTask(String jsonStr){
		JSONObject json = JSONObject.fromObject(jsonStr);
		
		//用户ID
		String staffId = json.getString("staff_id");
		
		//sn
		String sn = json.getString("sn");
		
		Map param = new HashMap();
		param.put("staffId", staffId);
		JSONObject result = new JSONObject();
		JSONObject body = new JSONObject();
		JSONArray taskArray = new JSONArray();
		try {
			List<Map> taskList = taskInterfaceDao.getAllTask(param);
			
			// 循环每一个任务
			for (Map task : taskList) {
				JSONObject taskObject = new JSONObject();
				String taskId = String.valueOf(task.get("TASK_ID"));
				taskObject.put("task_id", taskId);
				taskObject.put("task_name",String.valueOf(task.get("TASK_NAME")));
				taskObject.put("begin_time",String.valueOf(task.get("START_DATE")));
				taskObject.put("end_time", String.valueOf(task.get("END_DATE")));

				//查询所有待检查机房
				param = new HashMap();
				param.put("taskId", taskId);
				List<Map> roomList = taskInterfaceDao.getRoomsByTaskId(param);
				
				// 机房总数
				taskObject.put("total_count",taskInterfaceDao.getRoomsCountByTaskId(param));
				
				// 待检查机房数组
				JSONArray roomArray = new JSONArray();
				
				// 循环每一个机房
				for (Map room : roomList) {
					JSONObject roomObject = new JSONObject();
					roomObject.put("roomId",String.valueOf(room.get("ROOM_ID")));
					roomObject.put("roomCode",String.valueOf(room.get("ROOM_CODE")));
					roomObject.put("roomName",String.valueOf(room.get("ROOM_NAME")));
					String longitude = room.get("LONGITUDE") == null ? "" : String.valueOf(room.get("LONGITUDE"));
					String latitude = room.get("LATITUDE") == null ? "":String.valueOf(room.get("LATITUDE"));
					roomObject.put("position",longitude + " " + latitude);
					roomArray.add(roomObject);
				}
				taskObject.put("rooms", roomArray);
				taskArray.add(taskObject);
			}
			body.put("tasks", taskArray);
			result.put("body", body);
			result.put("result", "000");

		} catch (Exception e) {
			result.put("result", "001");
			result.put("error", e.toString());
			System.out.println(e.toString());
		}
		return result.toString();
	}
	
	/**
	 * 根据任务获取检查项列表
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String getInspItems(String jsonStr){
		
		//将字符串转换成json对象
		JSONObject json = JSONObject.fromObject(jsonStr);
		
		//任务Id
		String taskId = json.getString("taskId"); 
		
		//用户Id
		String staffId = json.getString("staff_id");
		
		//sn
		String sn = json.getString("sn");
		
		Map param = new HashMap();
		param.put("taskId", taskId);
		param.put("staffId", staffId);
		JSONObject result = new JSONObject();
		JSONObject body = new JSONObject();
		JSONArray checkItemArray = new JSONArray();
		
		try {
			//查询检查项
			List<Map> checkItemList = taskInterfaceDao.getCheckItemByTaskId(param);
			
			// 循环每一个检查项
			for (Map checkItem : checkItemList) {
				JSONObject checkItemObject = new JSONObject();
				String itemId = String.valueOf(checkItem.get("CHECK_ITEM_ID"));
				checkItemObject.put("item_id",itemId);
				checkItemObject.put("item_name",String.valueOf(checkItem.get("CHECK_ITEM_NAME")));
				checkItemObject.put("item_type",String.valueOf(checkItem.get("CHECK_TYPE")));
				
				//获取隐患类型
				param = new HashMap();
				param.put("item_id", itemId);
				List<Map> troubleTypeList = taskInterfaceDao.getTroubleTypeByCheckItemId(param);
				
				//遍历隐患类型,取值
				JSONArray troubleTypeArray = new JSONArray();
				for(Map troubleType : troubleTypeList)
				{
					JSONObject TroubleTypeObject = new JSONObject();
					TroubleTypeObject.put("trouble_id", troubleType.get("TROUBLE_TYPE_ID"));
					TroubleTypeObject.put("trouble_name", troubleType.get("TROUBLE_TYPE_NAME"));
					troubleTypeArray.add(TroubleTypeObject);
				}
				
				//将隐患类型放入检查项中
				checkItemObject.put("troubleTypes", troubleTypeArray);
				checkItemArray.add(checkItemObject);
			}
			
			body.put("inspItems", checkItemArray);
			result.put("body", body);
			result.put("result", "000");

		} catch (Exception e) {
			result.put("result", "001");
			result.put("error", e.toString());
			
			System.out.println(e.toString());
		}
		return result.toString();
	}
	
	/**
	 * 问题工单保存
	 * 
	 * @param jsonStr
	 * @return
	 */
	@Transactional(rollbackFor={Exception.class})
	public String saveTroubles(String jsonStr)
	{
		//将字符串转换成json对象
		JSONObject json = JSONObject.fromObject(jsonStr);
		
		//巡检人Id
		String staffId = json.getString("staff_id"); 
		
		 //sn
		String sn = json.getString("sn");
		
		//任务Id
		String taskId = json.getString("taskId");
		
		//机房Id
		String roomId = json.getString("room_id"); 
		
		 //是否临时上报
		String flag = json.getString("flag");
		
		//开始时间
		String beginTime = json.getString("begin_time");
		
		//结束时间
		String endTime = json.getString("end_time"); 
		
		//检查结果，有无问题
		String type = json.getString("type");
		
		//初始化结果json对象
		JSONObject result = new JSONObject();
		
		try {
			
			JSONArray troublesArray = json.getJSONArray("troubleList");
			for (int i = 0; i < troublesArray.size(); i++) {
				
				//封装参数
				JSONObject trouble = troublesArray.getJSONObject(i);
				HashMap param = new HashMap();
				param.put("staffId", staffId);
				param.put("sn", sn);
				param.put("taskId", taskId);
				param.put("roomId", roomId);
				param.put("isTemp", flag);
				param.put("beginTime", beginTime);
				param.put("endTime", endTime);
				String itemId = String.valueOf(trouble.get("item_id"));
				param.put("itemId", itemId);
				param.put("checkResult", type);
				
				//根据检查项ID获取检查项信息
				Map checkItem = taskInterfaceDao.getcheckItemByItemId(Integer.parseInt(itemId));
				if(null != checkItem)
				{
					//设置检查项类型
					param.put("itemType", checkItem.get("CHECK_TYPE"));
				}
				
				//设置隐患类型和描述
				param.put("troubleId", String.valueOf(trouble.get("trouble_id")));
				param.put("description", String.valueOf(trouble.get("description")));
				
				//通过任务ID和机房ID获取执行详情ID
				Map<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("taskId", taskId);
				queryMap.put("roomId", roomId);
				int action_detail_id = taskInterfaceDao.getActionIdByTaskIdAndRoomId(queryMap);
				param.put("actionId", action_detail_id);
				
				//存储检查记录
				taskInterfaceDao.saveTrouble(param);
			}
			
			//修改执行详情为已执行
			Map<String,Object> acitonMap = new HashMap<String,Object>();
			acitonMap.put("taskId", taskId);
			acitonMap.put("roomId", roomId);
			taskInterfaceDao.updateActionDetail(acitonMap);
			
			
			// 查询所有待检查的机房
			Map roomParam = new HashMap();
			roomParam.put("taskId", taskId);
			List<Map> roomList = taskInterfaceDao.getRoomsByTaskId(roomParam);
			
			//如果不存在待检查的机房列表
			if(StringUtil.isEmpty(roomList))
			{
				//关闭任务
				taskInterfaceDao.closeTaskbyTaskId(roomParam);
			}
			
			// 文字保存成功后，建立检查记录和图片的关联关系
			JSONArray photoIdsArray = json.getJSONArray("photoIdList");
			String photoId = "";
			for(int i = 0; i < photoIdsArray.size(); i++){
				
				photoId +="," + String.valueOf(photoIdsArray.getJSONObject(i).get("photo_id"));
		
			}
			
			//建立检查图片和检查问题关联关系
			if(!photoId.equals(""))
			{
				//过滤掉第一个逗号
				photoId = photoId.substring(1);
				Map<String,Object> photoMap = new HashMap<String,Object>();
				photoMap.put("PIC_ID",photoId );
				taskInterfaceDao.createCheckDetailPic(photoMap);
			}
			
			result.put("result", "000");
		} catch (Exception e) {
			
			//打印堆栈信息
			e.printStackTrace();
			
			//删除图片,定义picInterfactService目的是因为在
			//同一个类中内部调用无法生成新的事务
			picInterfaceService.deleteCheckPic(jsonStr);
			
			//数据回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result.put("result", "001");
			result.put("error", e.toString());
		}
		
		return result.toString();
	}
	
	/**
	 * 上传检查图片
	 * 
	 * @param request
	 * @return result
	 */
	@Transactional(rollbackFor=Exception.class)
	public String uploadPhoto(HttpServletRequest request) {
		
		JSONObject result = new JSONObject();
		Map<String, Object> photoMap = new HashMap<String, Object>();
		InputStream is = null;
		try {
			
			// 解析参数
			request.setCharacterEncoding("utf-8");
			
			//巡检人ID
			String staffId = request.getHeader("staffId");
			
			//任务ID
			String taskId = request.getHeader("taskId");
			
			//机房ID
			String roomId = request.getHeader("room_id");
			
			//检查项ID
			String itemId = request.getHeader("item_id");
			
			//sn
			String sn = request.getHeader("sn");
			
			//隐患类型ID
			String troubleId = request.getHeader("trouble_id");

			photoMap.put("CHECK_STAFF_ID", staffId);
			photoMap.put("TASK_ID", taskId);
			photoMap.put("ROOM_ID", roomId);
			photoMap.put("ITEM_ID", itemId);
			photoMap.put("SN", sn);
			photoMap.put("TROUBLE_ID", troubleId);

			// 文件流
			byte[] photoByte = ImageTool.getByteFromStream(request);
			String photo = StringUtil.convertByteToHexString(photoByte);
			String currDate = StringUtil.getCurrDate();
			is = request.getInputStream();

			// 获取photo主键
			int photoId = taskInterfaceDao.getPicId();
			photoMap.put("PIC_ID", photoId);

			/* 拼装请求参数 */
			StringBuffer xmlStr = new StringBuffer();
			xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xmlStr.append("<root>");
			xmlStr.append("<type>photo</type>"); // 上传类型
			xmlStr.append("<project>ins</project>"); // 存放照片的目录
			xmlStr.append("<name>").append(photoId).append("</name>"); // 文件名称
			xmlStr.append("<date>").append(currDate).append("</date>"); // 文件日期
			xmlStr.append("<data>").append(photo).append("</data>"); // 文件数据
			xmlStr.append("</root>");
			Client client = new Client(new URL(GlobalValue.FILE_SERVER));
			Object[] resultXmlObj = client.invoke("saveFile",new Object[] { xmlStr.toString() });
			
			// 解析返回值
			if (resultXmlObj != null) {
				Document document = DocumentHelper.parseText((String) resultXmlObj[0]);
				Element root = document.getRootElement();
				String r = root.elementText("result"); // 保存是否成功
				if (r.equalsIgnoreCase("success")) {
					/* 将图片保存的地址返回给调用方 */
					String photoPath = root.elementText("photoPath"); // 原尺寸照片地址
					String microPhotoPath = root.elementText("microPhotoPath"); // 缩微图照片地址
					// 对文件路径要做如下处理
					String fsi = GlobalValue.FILE_SERVER_INTERNET;
					int index = photoPath.indexOf("files");
					if (index != -1) {
						photoPath = fsi + photoPath.substring(index, photoPath.length());

					}
					index = microPhotoPath.indexOf("files");
					if (index != -1) {
						microPhotoPath = fsi+ microPhotoPath.substring(index,microPhotoPath.length());
					}
					photoMap.put("PIC_PATH", photoPath == null ? "": photoPath);
					photoMap.put("MICRO_PIC_PATH",microPhotoPath == null ? "" : microPhotoPath);
				} 
				taskInterfaceDao.insertPic(photoMap);
			}
			
			//没有异常，图片上传成功，返回图片ID
			result.put("result", "000");
			result.put("photo_id", String.valueOf(photoId));
			return result.toString();
			
		} catch (Exception e) {
			
			//打印堆栈信息
			e.printStackTrace();
			
			//数据回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
			result.put("result", "001");
			result.put("photoId", "");
			return result.toString();
		}finally{
			try {
			   if(is != null){
				   is.close();
			   }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
