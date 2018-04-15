package icom.util.sendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import icom.util.DateUtil;



public class SendMessageUtil {
	/**
	 * 向多个手机号码发送短信
	 * 
	 * @param userId
	 * @param phoneNums
	 * @param msg
	 * @return
	 */
 
	public static boolean sendSms(String userId, String[] phoneNums, String msg) {
		if (phoneNums != null && phoneNums.length > 0) {
			// baseMethodService.saveLog("后台发送短信功能——发送短信开始：", "全部号码：" +
			// phoneNums + "，短息内容：" + msg, "0", userId);
			Map pram = new HashMap();
			for (int i = 0; i < phoneNums.length; i++) {
				try {
					Thread.sleep(200);
					pram.put("TEL_NUM", phoneNums[i]);
					pram.put("SMS_STR", msg);
					pram.put("USER_ID", userId);
					// baseMethodService.saveLog("后台发送短信功能——发送短信：", "号码：" +
					// phoneNums[i] + "，短息内容：" + msg, "0", userId);
					/* add by zhangzhl 2012-02-18 将发送的短信内容插入到数据库 */
					// this.basedao.save("MyMessage.insertMessageHistory",
					// pram);

					/*
					 * add by zhangzhl 2012-03-02
					 * 若是中国电信号码，则调用省公司接口发送短信，否则调用OA接口发送
					 */
					if (isChinaTelcomPhoneNum(phoneNums[i])) {
						sendMessageByCallProvinceService(phoneNums[i], msg);
					} else {
						throw new Exception("不是电信号码，使用OA短信接口发送");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					// baseMethodService.saveLog("发送短信异常,第一次异常信息：", "异常信息：" +
					// ex.getMessage(), "0", userId);
					//
					// try {
					// // 等待1000毫秒，在进行短信的第二次插入。
					// Thread.sleep(1000);
					// this.basedao.save("StaffManage.sendSms", pram);
					// baseMethodService.saveLog("发送短信成功，第二次成功：", "短息内容：" + msg,
					// "0", userId);
					// continue;
					// } catch (Exception ex1) {
					// ex1.printStackTrace();
					// baseMethodService.saveLog("发送短信异常,第二次异常信息：", "异常信息：" +
					// ex1.getMessage(), "0", userId);
					//
					// //add by zhangzhl 2012-2-20 若短信发送没有成功，则将信息推送到客户端
					// String staffId =
					// baseMethodService.queryStaffIdByTelNum(phoneNums[i]);
					// this.pushNotification(staffId, "爱运维-推送信息", msg);
					// //向客户端推送消息
					// continue;
					// }
				}
			}
			// baseMethodService.saveLog("后台发送短信功能——发送短信结束：", "", "0", userId);
		}

		return true;
	}
	/**
	 * 调用省公司的短信接口发送短信
	 * @param phoneNums
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static void sendMessageByCallProvinceService(String phoneNum, String sendMsg) throws Exception{
		try{
			/* 读取当天的短信校验码  */
			Map param = new HashMap();
			//String checkNbr = (String)basedao.get("StaffManage.getMessageCheckNbr", param);  
			
			/* 拼装请求参数 */
			SMServiceStub sss =new SMServiceStub();
			SMServiceStub.SmCheckSubmit sm= new SMServiceStub.SmCheckSubmit();
			 
			SMServiceStub.SMCheckSubmitReq sMCheckSubmitReq = new SMServiceStub.SMCheckSubmitReq();
			sMCheckSubmitReq.setQuerySource(0);   //短信来源 0计费
			sMCheckSubmitReq.setServTypeID(121);  //业务类型 
			sMCheckSubmitReq.setAppID(0);		  //目的号码类型 0：表示ACC_NBR字段为单个号码
			sMCheckSubmitReq.setAccNbr(phoneNum); //目的号码	
			sMCheckSubmitReq.setServID("");		  //用户服务号  可以传空
			sMCheckSubmitReq.setSqlText(sendMsg); //短信内容
			sMCheckSubmitReq.setAreaCode("021");  //区号
			sMCheckSubmitReq.setOpenState(0);	  //状态
			sMCheckSubmitReq.setSwitchID(0); 	  //小灵通和c网标示  0-C网  1-小灵通
			//sMCheckSubmitReq.setCheckNbr(checkNbr); //当天的短信校验码 
			
			/* 调用WebService接口 */
			sm.setSMCheckSubmitReq(sMCheckSubmitReq);
			SMServiceStub.SmCheckSubmitResponse rret= sss.smCheckSubmit(sm);
			SMServiceStub.SMCheckSubmitResp sMCheckSubmitResp= rret.getResult();
			
			/* 解析接口返回的结果 */
			int iResult = sMCheckSubmitResp.getIResult();   //处理结果代码，在处理正确情况下为0，否则是错误代码
			if(iResult != 0){
				String smsg = sMCheckSubmitResp.getSmsg();  //错误原因
				System.out.println("Error Msg:" + smsg);
				//baseMethodService.saveLog("调用省公司短信接口发送短信", "短信发送失败，接口返回内容：" + smsg, "1", null);
				throw new Exception("调用省公司接口发送短信失败");  //发送失败则抛出异常，外层捕获异常后使用其他方式发送短信
			}
		}catch(Exception e){
			e.printStackTrace();
			//baseMethodService.saveLog("调用省公司短信接口发送短信", "发生异常,异常内容：" + e.getMessage(), "1", null);
			throw new Exception("调用省公司接口发送短信失败");
		}
	}
	/**
	 * 批量发送短信
	 * 
	 * @param userId
	 * @param phoneNums
	 * @param msg
	 * @return
	 */
	public static boolean sendMessageByCallProvinceService(List phoneNumsList, String msg) {
		if (phoneNumsList != null && phoneNumsList.size() > 0) {
			HashMap pram = new HashMap();
			for (int i = 0; i < phoneNumsList.size(); i++) {
				try {
					Thread.sleep(200);
					pram.put("TEL_NUM", phoneNumsList.get(i));
					pram.put("SMS_STR", msg);
					pram.put("USER_ID", null);
					if (isChinaTelcomPhoneNum(phoneNumsList.get(i).toString())) {
						sendMessageByCallProvinceService(phoneNumsList.get(i).toString(), msg);
					} else {
						throw new Exception("不是电信号码，使用OA短信接口发送");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					// baseMethodService.saveLog("发送短信异常,第一次异常信息：", "异常信息：" +
					// ex.getMessage(), "0", userId);
					//
					// try {
					// // 等待1000毫秒，在进行短信的第二次插入。
					// Thread.sleep(1000);
					// this.basedao.save("StaffManage.sendSms", pram);
					// baseMethodService.saveLog("发送短信成功，第二次成功：", "短息内容：" + msg,
					// "0", userId);
					// continue;
					// } catch (Exception ex1) {
					// ex1.printStackTrace();
					// baseMethodService.saveLog("发送短信异常,第二次异常信息：", "异常信息：" +
					// ex1.getMessage(), "0", userId);
					//
					// //add by zhangzhl 2012-2-20 若短信发送没有成功，则将信息推送到客户端
					// String staffId =
					// baseMethodService.queryStaffIdByTelNum(phoneNums[i]);
					// this.pushNotification(staffId, "爱运维-推送信息", msg);
					// //向客户端推送消息
					// continue;
					// }
				}
			}
			// baseMethodService.saveLog("后台发送短信功能——发送短信结束：", "", "0", userId);
		}

		return true;
	}
	/**
	 * 判断是否为中国电信手机号码
	 * @param phoneNum
	 * @return
	 */
	private static boolean isChinaTelcomPhoneNum(String phoneNum){
		boolean isChinaTelcom = false;
		if(phoneNum != null && phoneNum.length()>0){
			if(phoneNum.startsWith("133") || phoneNum.startsWith("189") || phoneNum.startsWith("153") || phoneNum.startsWith("180")|| phoneNum.startsWith("177")){
				isChinaTelcom = true;
			}
		}
		return isChinaTelcom;
	}
/*	 public static void main(String[] args) {
		 try {
			 //sendMessageByCallProvinceService("15366165868","哈哈");
			 sendMessage("15366165868","测试员，您好。您于"+DateUtil.getCurrentTime()+"收到一条测试任务，请及时执行。【分公司缆线巡检】");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	 }*/

	public static boolean sendMessage(String phoneNum, String sendMsg) {
		try {
			if(PropertiesUtil.getPropertyBoolean("sendSmsFlag")){
				//发送给默认号码,如果是true
				if (PropertiesUtil.getPropertyBoolean("sendSmsToDefaultFlag")) 
				{
					//sendMsg = "测试员，您好。您于"+DateUtil.getCurrentTime()+"收到一条测试任务，请及时执行。【分公司缆线巡检】";
					return sendSameMessageList(null,sendMsg);
				}
				
				// 拼裝xml
				StringBuffer reqInfo = new StringBuffer();
				reqInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				reqInfo.append("<reqInfo>");
				reqInfo.append("<messages>");
				reqInfo.append("<message>");
				reqInfo.append("<tel>").append(phoneNum).append("</tel>");
				reqInfo.append("<content>").append(phoneNum)
						.append("</content>");
				reqInfo.append("</message>");
				reqInfo.append("</messages>");
				reqInfo.append("</reqInfo>");

				//使用线程池发送短信
				new SendMessageByThread(reqInfo.toString(),"sendMessageurl"); 
				
			}
			return true;
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public static boolean sendMessageList(List phoneNumList, List messageContentList) {
		try {
			if(PropertiesUtil.getPropertyBoolean("sendSmsFlag"))
			{
				//发送给默认号码,如果是true
				if (PropertiesUtil.getPropertyBoolean("sendSmsToDefaultFlag")) 
				{
					String sendMsg = "测试员，您好。您于"+DateUtil.getCurrentTime()+"收到一条测试任务，请及时执行。【分公司缆线巡检】";
					phoneNumList = PropertiesUtil.getPropertyToList("defaultNumbers");
					messageContentList.clear();
					for(int j = 0;j<phoneNumList.size();j++)
					{
						messageContentList.add(sendMsg);
					}
					
				}
				// 拼裝xml
				StringBuffer reqInfo = new StringBuffer();
				reqInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				reqInfo.append("<reqInfo>");
				if(null!=phoneNumList && phoneNumList.size()>0&&null!=messageContentList && messageContentList.size()>0&&messageContentList.size()==phoneNumList.size())
				{
					reqInfo.append("<messages>");
					for(int i =0;i<phoneNumList.size();i++)
					{
						reqInfo.append("<message>");
						reqInfo.append("<tel>").append(phoneNumList.get(i)).append("</tel>");
						reqInfo.append("<content>").append(messageContentList.get(i)).append("</content>");
						reqInfo.append("</message>");
					}
					reqInfo.append("</messages>");
				}
				else
				{
					return false;
				}
			
				reqInfo.append("</reqInfo>");
				//使用线程池发送短信
				new SendMessageByThread(reqInfo.toString(),"sendMessageurl"); 
			}
			return true;
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static boolean sendSameMessageList(List phoneNumList, String messageContent) {
		try {
			if(PropertiesUtil.getPropertyBoolean("sendSmsFlag")){
				//发送给默认号码,如果是true
				if (PropertiesUtil.getPropertyBoolean("sendSmsToDefaultFlag")) 
				{
					phoneNumList = PropertiesUtil.getPropertyToList("defaultNumbers");
				}
				// 拼裝xml
				StringBuffer reqInfo = new StringBuffer();
				reqInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				reqInfo.append("<reqInfo>");
				if(null!=phoneNumList && phoneNumList.size()>0)
				{
					reqInfo.append("<messages>");
					for(int i =0;i<phoneNumList.size();i++)
					{
						reqInfo.append("<message>");
						reqInfo.append("<tel>").append(phoneNumList.get(i)).append("</tel>");
						reqInfo.append("<content>").append(messageContent).append("</content>");
						reqInfo.append("</message>");
					}
					reqInfo.append("</messages>");
				}
				else
				{
					return false;
				}
				reqInfo.append("</reqInfo>");
				//使用线程池发送短信
				  
				new SendMessageByThread(reqInfo.toString(),"sendMessageurl"); 
		       
			}
			
			return true;
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 调用综调短信接口
	 * 
	 * @param phoneNum
	 *            要发送的手机号码
	 * @param content
	 *            短信内容
	 * @param areaId
	 *            区域ID
	 */
	public static void sendMessageInfo(String phoneNum, String content, String areaId) {

		StringBuffer pStr = new StringBuffer();
		pStr.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		pStr.append("<SERVICE>");
		pStr.append("<DATASOURCE TYPE=\"Procedure\">");
		pStr.append("<SQL NAME=\"cp_sm_ins_queueex\" CONTENT=\"cp_sm_ins_queueex\">");
		pStr.append("<PARAM NAME=\"i_Telephone\" TYPE=\"12\" TAG=\"0\" VALUE=\"" + phoneNum + "\"/>");
		pStr.append("<PARAM NAME=\"i_BussType\" TYPE=\"12\" TAG=\"0\" VALUE=\"SERVICE_ZTE\"/>");
		pStr.append("<PARAM NAME=\"i_Content\" TYPE=\"12\" TAG=\"0\" VALUE=\"" + content + "\"/>");
		pStr.append("<PARAM NAME=\"i_Areaid\" TYPE=\"12\" TAG=\"0\" VALUE=\"" + areaId + "\"/>");
		pStr.append("<PARAM NAME=\"i_CallPhone\" TYPE=\"12\" TAG=\"0\" VALUE=\"10000\"/>");
		pStr.append("<PARAM NAME=\"o_RetVal\" TYPE=\"4\" TAG=\"1\" VALUE=\"\" />");
		pStr.append("<PARAM NAME=\"o_SerialNum\" TYPE=\"4\" TAG=\"1\" VALUE=\"\" />");
		pStr.append("</SQL>");
		pStr.append("</DATASOURCE>");
		pStr.append("</SERVICE>");

		AsigAxisServiceServiceLocator asigAxisServiceServiceLocator = new AsigAxisServiceServiceLocator();
		AsigAxisServiceSoapBindingStub stub = null;
		try {
			stub = (AsigAxisServiceSoapBindingStub) asigAxisServiceServiceLocator.getAsigAxisService();
			stub.executeXML(pStr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("this is over!");
	}
	
}