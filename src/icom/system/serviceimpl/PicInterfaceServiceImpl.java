package icom.system.serviceimpl;

import icom.system.dao.TaskInterfaceDao;
import icom.system.service.PicInterfaceService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 图片操作实现类
 * @author huliubing
 *
 */
@Service
@SuppressWarnings("all")
public class PicInterfaceServiceImpl implements PicInterfaceService {

	@Resource
	private TaskInterfaceDao taskInterfaceDao;
	
	/**
	 * 删除图片
	 * @param jsonStr
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteCheckPic(String jsonStr)
	{
		//将字符串转换成json对象
		JSONObject json = JSONObject.fromObject(jsonStr);
		
		try
		{
			//删除关联检查记录的所有图片
			JSONArray photoIdsArray = json.getJSONArray("photoIdList");
			String picId = "";
			Map<String,Object> deleteMap = new HashMap<String,Object>();
			
			//建立检查图片和检查问题管理关系
			for(int i = 0; i < photoIdsArray.size(); i++){
				
				picId += "," + String.valueOf(photoIdsArray.getJSONObject(i).get("photo_id"));
			}
			
			if(picId != "")
			{
				picId = picId.substring(1);
				deleteMap.put("PIC_ID", picId);
				taskInterfaceDao.deleteCheckPic(deleteMap);
			}
		}
		catch(Exception e)
		{
			//数据回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
			//数据回滚
			System.out.println(e.getStackTrace());
		}
	}

}
