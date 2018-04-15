package com.inspecthelper.action;

import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inspecthelper.service.IDesignOrderService;

import util.page.BaseAction;

@RequestMapping(value="/mobile/design-order")
@Controller
public class DesignOrderController extends BaseAction{
	@Resource
	private IDesignOrderService designOrderService;
	
	
	/**
	 * 根据编码获取光交设备板卡模块以及端子信息（与大O的光交成端信息一致（框号/U端子/光缆名称/局向纤芯））
	 * 
	 * @author Fanjiwei
	 * @provider
	 * @create-date 2012-10-17
	 */
	@RequestMapping(value = "/getPhyEqpUnitList.do")
	public void getPhyEqpUnitList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String param = BaseServletTool.getParam(request);
			 //param = "{'resSpecId':'703','resCode':'250JN.JNZ00\\/GJ006','sn':'862199029026496','staffId':'2622','staffNo':'xj110'}";

			// param =
			// "{\"resSpecId\":\"703\",\"resCode\":\"250QX.YHM00/GJ078\",\"sn\":\"c16075b0cff186f\",\"staffId\":\"6019\"}";

			// 测试使用
			// param =
			// "{\"resCode\":\"512SZ.XYJMJ/GJ004\",\"resSpecId\":\"703\",\"sn\":\"703\",\"staffId\":\"2535\"}";
			// param =
			// "{\"areaCode\":\"sq.sz.js.cn\",\"resNo\":\"512SZ.GJSYE/GF0475\",\"resCode\":\"512SZ.PMJMJ/GJ047\",\"resSpecId\":\"703\",\"sn\":\"703\",\"staffId\":\"2535\",\"cableId\":\"10\",\"code\":\"10\"}";
			// param =
			// "{\"areaCode\":\"nt.js.cn\",\"resNo\":\"NT.GPS00/GJ001\",\"resCode\":\"NT.GPS00/GJ001\",\"resSpecId\":\"703\",\"sn\":\"703\",\"staffId\":\"2624\",\"cableId\":\"10\",\"code\":\"10\"}";
			// param
			// ="{\"areaCode\":\"sq.sz.js.cn\",\"resNo\":\"512SZ.CXJMJ/GJ001\",\"resCode\":\"512SZ.CXJMJ/GJ001\",\"resSpecId\":\"703\",\"sn\":\"703\",\"staffId\":\"2535\",\"cableId\":\"10\",\"code\":\"10\"}";
			// param =
			// "{\"resSpecId\":\"703\",\"resCode\":\"AQP.HZJRW/GJ008\",\"sn\":\"c16075b0cff186f\",\"staffId\":\"5997\"}";

			String result = "";
			result = designOrderService.getPhyEqpUnitList(param);
			// MyDebug.info("reesult:"+result);
			BaseServletTool.sendParam(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
