package com.inspecthelper.action;

import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inspecthelper.service.IDesignOrderService;


import util.page.BaseAction;

@RequestMapping(value = "/mobile/oss-abitiy")
@Controller
public class OssAbitiyController extends BaseAction{

	@Resource
	private IDesignOrderService designOrderService;
	/**
	 * 
	 * 根据设备编码 设备类型以及区域编码 封装成XML格式 获取OSS设备信息 包括到dblink 数据库去查光/电缆段信息 提供方：OSS资源配置方
	 * 
	 * @author Fanjiwei
	 * @create_date 2012-07-01
	 *              json===={"resultInfo":{"returnCode":"000","codeDesc"
	 *              :"执行成功"},
	 *              "resInfo":[{"resId":"12320000000556","resName":"平望实验小学光交"
	 *              ,"resOprState":[],
	 *              "resMntState":"正常","resNo":"512WJ.PWJMJ/GJ011"
	 *              ,"resSpecId":"703",
	 *              "createTime":"Mon May 07 11:27:53 CST 2012"
	 *              ,"tmlName":"平望局平望局",
	 *              "resModel":"光交机架模板(此型号非标准型号或被归并，下次删除)","resXPoint"
	 *              :"0.0","resYPoint":"0.0",
	 *              "creator":[],"modifyPerson":"1101",
	 *              "modityTime":"Fri May 18 09:52:11 CST 2012"
	 *              ,"workOrderId":[]}]}
	 * 
	 */
	@RequestMapping(value = "/getCallEsbForQryEqpInfo.do")
	public void getCallEsbForQryEqpInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String param = BaseServletTool.getParam(request);
			//param ="{'resSpecId':'703','resCode':'250JN.JNZ00\\/GJ006','sn':'862199029026496','staffId':'2622','staffNo':'xj110','areaCode':'NJ'}";
//			JSONObject json = JSONObject.fromObject(param);
//			// 测试使
//			String resCode = json.getString("resCode");
//			String resSpecId = json.getString("resSpecId");
//			String areaCode = json.getString("areaCode");
//			String sn = json.getString("sn");
			// String param =
			// "{\"areaCode\":\"区域编码\",\"cblSectId\":\"123\",\"cblSecType\":\"123\"}";
			// /String resCode="548ADSL0-01";//512SZ.CXJMJ/GJ017/PXG09/01
			// 光：512SZ.LGANG/GJ002 电：548ADSL0-01
			// String resSpecId="801";//701 703 电：801
			// String areaCode="sq.sz.js.cn";
			String result = "";
			// dblink查询数据中光/电缆段信息
			/*
			 * if(resSpecId.equals("701")||resSpecId.equals("-701")||resSpecId.equals
			 * ("-801")||resSpecId.equals("801")){ result
			 * =designOrderService.getCblSectionInfo
			 * (areaCode,resCode,resSpecId); }else{ //到ESB查询设备信息 String
			 * reqXML="<reqInfo><eqpNo>"
			 * +resCode+"</eqpNo><eqpTypeId>"+resSpecId+
			 * "</eqpTypeId><areaCode>"+areaCode+"</areaCode></reqInfo>";
			 * result=
			 * ossAbitiyServiceImpl.getCallEsbForQryEqpInfo(reqXML,areaCode); }
			 */
			result = designOrderService.getPhyEqpAndCblSectionInfo(param);
			BaseServletTool.sendParam(response, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
