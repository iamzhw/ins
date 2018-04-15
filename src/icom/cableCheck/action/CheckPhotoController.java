package icom.cableCheck.action;

import icom.cableCheck.service.CheckPhotoService;
import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.BaseAction;
@Controller
@RequestMapping("mobile/cableCheck")
public class CheckPhotoController extends BaseAction {

	Logger logger = Logger.getLogger(CheckPhotoController.class);
	
	@Resource
	private CheckPhotoService checkPhotoService;
	
	 /**
     * 保存图片
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/uploadPhoto")
    public void uploadPhoto(HttpServletRequest request, HttpServletResponse response) {
    	
//    	String jsonStr = BaseServletTool.getParam(request);
    	
//		String parameter ="{\"staffId\":\"\",\"dzid\":\"18300001389986\",\"sbbm\":\"XPQ.TBYC0/GF001\",\"sbmc\":\"昌圩湖花园A-1幢2单元底层分纤箱\",\"dzbm\":\"19\",\"sbid\":\"\","
//		+"\"sblx\":\"704\",\"bdsj\":\"2013-05-01\",\"glbh\":\"F1304240027\",\"gdjgsj\":\"2013-05-01\",\"gxsj\":\"2013-05-01\",\"jclx\":\"0\","
//		+"\"rwmxid\":\"3f3efbc2625c4df6b42c0cc1f0b75f12\",\"pzgh\":\"1103\",\"sggh\":\"1103\",\"gqgh\":\"1103\",\"xz\":\"0\",\"gdbh\":\"18800000070573\","
//		+"\"sfadsg\":\"1\",\"cwlx\":\"234\",\"sggfx\":\"1-2\",\"zqdzbm\":\"2345555\",\"dqglbh\":\"23556666\",\"wtsm\":\"0\",\"demo\":\"0\"}";
    	
		String result = checkPhotoService.uploadPhoto(request);
		
		logger.info("【保存图片，返回参数】"+result);
		
		BaseServletTool.sendParam(response, result);
    	
    }
}
