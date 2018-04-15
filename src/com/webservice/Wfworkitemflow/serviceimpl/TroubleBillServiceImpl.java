package com.webservice.Wfworkitemflow.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import icom.system.dao.CableInterfaceDao;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cableInspection.dao.PointDao;
import com.webservice.Wfworkitemflow.dao.TroubleBillDao;
import com.webservice.Wfworkitemflow.service.TroubleBillService;

@WebService(serviceName = "TroubleBillService")
public class TroubleBillServiceImpl implements TroubleBillService {
	
	@Resource
	private CableInterfaceDao cableInterfaceDao;
	
	@Resource
	private PointDao pointDao;
	
	@Resource
	private TroubleBillDao troubleBillDao;
	
	@Override
	public String troubleBillReceipt(String xml) {
		Document doc;
		String info="";
		String IfResult="0";
		try {
			xml=xml.replace("<?xml version=\"1.0\" encoding=\"gb2312\"?>", "");
			doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String task_id=root.element("task_id").getText();
			String workManAccount=root.element("workManAccount").getText();
			String workMan=root.element("workMan").getText();
			String content=root.element("content").getText();
			String createDate=root.element("createDate").getText();
			String photoUrlForInter=root.element("photoUrlForInter").getText();
			String photoUrlForOuter=root.element("photoUrlForOuter").getText();
			
			Map<String, Object> flowParams = new HashMap<String, Object>();
			flowParams.put("BILL_IDS", task_id);
			flowParams.put("STATUS_ID", 4);
			flowParams.put("TYPE", 2);
			flowParams.put("OPERATE_INFO", "归档");
			if(troubleBillDao.getStaffId(workManAccount)!=null){
				flowParams.put("MAINTOR", troubleBillDao.getStaffId(workManAccount));
			}
			flowParams.put("AUDITOR", troubleBillDao.getAUDITOR(task_id));
			pointDao.updateBillHandle(flowParams);
			flowParams.put("BILL_ID", task_id);
			flowParams.put("STATUS", 4);
			flowParams.put("TYPE", 2);
			flowParams.put("OPERATOR", troubleBillDao.getStaffId(workManAccount));
			flowParams.put("RECEIVOR", troubleBillDao.getAUDITOR(task_id));
			pointDao.insertBillFlow(flowParams);
			//插入照片
			if(!photoUrlForInter.equals("")){
				Map<String, Object> photoMap = new HashMap<String, Object>();
				photoMap.put("TASK_ID", task_id);
				photoMap.put("POINT_ID", "");
				photoMap.put("TERMINAL_TYPE", 1);
				photoMap.put("SN", "");
				photoMap.put("PHOTO_TYPE", 1);
				String photoId="";
				String[] photourl=photoUrlForInter.split(",");
				photoMap.put("CREATE_STAFF", 3326);
				for (String string : photourl) {
					photoMap.put("PHOTO_PATH", string);
					photoMap.put("MICRO_PHOTO_PATH", string);
					photoId = cableInterfaceDao.getPhotoId();
					photoMap.put("PHOTO_ID", photoId);
					cableInterfaceDao.insertPhoto(photoMap);
				}
				photoMap.put("RECORD_ID", "0");
				photoMap.put("BILL_ID", task_id);
				cableInterfaceDao.insertPhotoRel(photoMap);
			}
			IfResult="1";
		} catch (DocumentException e) {
			//System.out.println(task_id+","+workManAccount+","+workMan+","+content+","+createDate+","+photoUrlForInter+","+photoUrlForOuter);
			e.printStackTrace();
			info="入参格式或者空错误";
		}catch (NullPointerException e) {
			e.printStackTrace();
			info="入参名称错误";
		}catch (Exception e) {
			e.printStackTrace();
			info="其他错误";
		}
		String rs="<?xml version=\"1.0\" encoding=\"GB2312\" ?>"
					+"<task>"
					+"<IfResult>"+IfResult+"</IfResult>"
					+"<IfResultInfo>"
					+"<ResultMsg>"+info+"</ ResultMsg >"
					+"</IfResultInfo>"
					+"</task>";
		return rs;
	}

}
